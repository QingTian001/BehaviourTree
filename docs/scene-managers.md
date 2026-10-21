# 场景状态、召唤和触发器接入

来源为 vtworld `wm.Map.entity.manager.state`、`summon`、`trigger`。包名分别改为 `state`、`summon`、`trigger`，共用几何在 `area`，生命周期接口在 `manager`。复用现有 `behaviour.GEntity`、`event` 和不可变 `math.Vector3`，不要求修改现有 `BehaviourHost`。

保留迁入文件的作者、类说明、方法和行内注释。`trigger` 五个来源文件的全部 34 段注释原文保留，包括 `TriggerManager` 的完整算法说明与 `GridTriggerManager` 的九种跨格转换表。适配说明另外添加；原注释中的服务器类型名仅用于来源对照。

## 宿主边界

| 接口 | 接入项目实现的能力 |
| --- | --- |
| `StateHost` | 状态配置快照、逻辑时钟、动作主动/被动分类、中断订阅、状态变化通知 |
| `SummonHost` | 召唤配置、朝向、按对象类型创建、实体进出地图、owner/AOI 属性、两个独立的到期时间 |
| `TriggerHost` | 逻辑时钟、实体类型、观察者视野集合，可选的格子查询接口 |
| `aoi.GridMapAoiManager` | 层级、格子长度、指定格子、实体当前格子、格子内实体查询 |
| `aoi.Grid` | 格子坐标、层级及四角；同一层级/坐标具有稳定的 equals/hashCode |

所有调用和事件均在同一场景线程执行；时间使用宿主单调递增的逻辑毫秒。生产环境负责真实实体、移动、AOI 索引与计时器。测试宿主 `src/test/java/testsupport/SceneFixture.java` 不进入正式 JAR。

## 状态

```java
var states = new state.StateManager(entity, stateHost);
var id = new state.StateId();
boolean added = states.addState(stateCfgId, (e, s, normal) -> {
    // 释放这项状态对应的业务占位。
}, 1000, id);
states.update(deltaMills);
states.cancelState(id.value); // long 实例 ID；int 重载按配置 ID 取消单实例状态。
states.onLeaveMap();
```

`StateConfig` 将原状态表字段转换为不可变 List 快照，动作/状态 ID 继续使用宿主配置。普通状态的禁用动作、保护动作和保护状态取并集；全阻塞状态的允许动作/状态取交集，并覆盖普通限制。`isMultiple=false` 拒绝重复加入。

`StateHost.listenInterrupt` 注册订阅并返回 `ListenerHandle`，可接实体事件或地图事件；注册本身不触发中断，释放应幂等。到期、取消、中断都会释放订阅。中断的 finish 参数为 `false`，到期和正常取消为 `true`。`-1` 表示无限期；其他有效时长换算成 `now + duration`，到截止时刻结束。

`EventStateAdded` 在两个索引写入后发布。结束时先执行 finish，再发布 `EventStateRemoved`，此时监听方仍可查到该状态；随后删除索引、重算约束，最后调用宿主变化通知。同一状态的 finish/Removed 回调重入不会再次结束它。非法动作分类在修改约束和订阅前被拒绝。

`onLeaveMap` 保留来源语义：只释放中断订阅，不清除状态或触发 finish。需要再次入场继续监听时，接入方应明确重建状态管理器和业务状态；它没有自动恢复订阅的入场方法。

`StateParam<D,U>` 保留 `checkParam`、`tryUpdate`、`updated`、`isEffect` 等扩展点，去掉 Zeze Data 约束。`NormalStateParam` 用 Object 表示无业务载荷；`toProtocolData` 的原方法名保留，具体协议转换由宿主负责。直接构建状态时使用 `StateBuilder.setStateConfig(...)`，检查有效期时显式传入 `nowMills`，状态对象不持有场景宿主。

## 召唤

```java
var summons = new summon.NonePlayerSummonManager(owner, summonHost);
var param = new summon.param.NormalSummonEntityParam();
param.setOverrideSummonCount(3);
summons.summonAndEnterMap(summonCfgId, param, created -> {
    // 已登记 owner、索引并进入地图，可在这里启动行为树。
});
summons.activeRemoveSummonEntities(summonCfgId);
summons.ownerLeaveMap();
```

`SummonConfig` 对应原召唤表的保留字段；`SummonUnit(objectId, aiParam, weight)` 保留创建对象 ID、AI 参数 ID 和权重。`EntityCreateStrategy` 由宿主按 `objType` 提供；返回实际新实体或 null（本次未创建），不应自行重复进入地图。实体进入地图由管理器执行，完成后调用回调。

配置 `summonType` 是生成算法编号：1 整批同一种加权随机、2 每个独立加权随机、3 按权重比例分配。它与 `SummonEntityParam.getSummonType()` 返回的 NORMAL/TASK/INTERACT 位掩码含义不同。比例分配修正了来源尾项漏算问题，例如权重 `[1,1]`、数量 10 生成 5+5；整数余数仍分给最后一项。

位置保留 Self=2 与 Set=1，两者方向均取 owner 朝向；保留半径、扇形角、偏移角、随机/边缘分布、高度以及四种朝向策略。随机半径沿用来源的线性抽样；360° 边缘分布也保留首尾点重合的语义。家具交互位置=3、岸边位置=4 不在此次移植范围，使用时明确抛错。

宿主的 `configureSummon` 必须设置 `GEntity.getOwnerInstId()` 对应的实体实例 ID，使原 `ActFindSummonOwner` 可直接使用。配置 lifetime>0 覆盖参数中的生命截止时间；lifetime=0 保留参数给定时间。实体生命截止时间与主人离场截止时间是两个独立的计时约束，均由宿主实际执行。

`leaveMap` 先更新宿主实体状态，再向被移除实体同步发布 `EventObjectLeaveMap`，使管理器释放索引与订阅。最后一个对象离场后移除空配置分组及类型索引。主动移除与自然离场保留各自业务 hook。

主人离场时，配置要求随主人销毁的实体立即移除；其他实体解绑 owner，并设置 `now + destroyTimeMsAfterOwner` 的截止时间。手动 `addSummonEntity` 加入、没有召唤配置分组的实体只解绑 owner，不额外推定超时策略。全部监听和管理引用释放，管理器进入终态，重复离场幂等，不能继续添加或召唤。

## 触发器与格子 AOI

```java
var triggers = trigger.TriggerManager.create(triggerHost, observer, area, caredObjectTypes);
triggers.addTriggerListener(listener);
triggers.onEnterMap();
triggers.tick(deltaMills);
triggers.onLeaveMap();
```

宿主先更新位置/视野/格子索引，然后发布事件：

```java
observer.triggerEvent(new event.object.EventEnterAoi(observer, entering));
moving.triggerEvent(new event.object.EventMove(moving));
moving.triggerEvent(new event.object.EventCrossAoiGrid(moving, leaveGrid, enterGrid));
observer.triggerEvent(new event.object.EventLeaveAoi(observer, leaving));
leaving.triggerEvent(new event.object.EventObjectLeaveMap(leaving));
```

没有格子提供者时工厂创建 `CommonTriggerManager`。存在提供者时，圆和未旋转矩形使用 `GridTriggerManager`；扇形、点集、单点及旋转矩形回退 Common。`AreaAoiGridVisitor` 保留按四角分类完全覆盖格与相交格的算法；实际 AOI 存储、索引更新、视野计算均未迁入。

格子为左闭右开区间，`Grid.getAoiGridAxis` 向下取整：长度 10 时 0 属于格 0，-10 属于格 -1。宿主的查询、四角和跨格事件必须采用同一坐标约定。提供者的默认层级会贯穿区域遍历和实体格子查询，不使用硬编码配置枚举。

初次入场只建立初始成员集，不发送 In；后续静止进入视野或仅跨格也能在下个计算周期触发 In/Out。计算周期保留 300ms。离开 AOI/地图会立即为原区域成员发送一次 Out，并释放相关监听。观察者自身离场调用 `onLeaveMap`，清理全部监听（含外围格对象的跨格监听）和待计算集合，不为所有成员批量补发 Out。

监听回调允许增删监听、移除观察对象或让触发器离场重入；使用快照迭代，新的脏事件留给下轮计算。Area 使用固定世界坐标，观察者移动不会自动平移区域，沿用来源语义。

`area` 保留来源各形状方法：圆的点包含判定为严格 `< radius`，矩形为包含边界，扇形包含圆心及半径边界；矩形、扇形、点集的带半径重叠方法仍不支持（返回 false）。`Vector3` 新增变换方法返回新对象，不能照搬来源的原地修改调用方式。

## 运行与验证

在工程根目录使用 JDK 25：

```powershell
.\gradlew.bat test jar runExample verifyJarExample runSceneExample verifyJarSceneExample
```

`SceneManagersExample` 演示状态中断、召唤对象 findOwner、区域进入/离开、主人离场后的超时销毁和监听释放；`verifyJarSceneExample` 使用正式 JAR 加载生产代码。

此次未修改协议、数据库、生成配置、config.data 或现有行为树节点。生产宿主的真实格子索引、协议通知、寻路/Navmesh、家具/玩家任务业务需由接入工程实现。
