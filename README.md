# BehaviourTree

独立 Java 行为树。共享的 Atom / Executor 保存配置与逻辑，每个实体使用独立的 Task / Runtime、黑板、执行栈和监听资源。

需要 **JDK 25**。使用工程自带的 Gradle 9.5.1 Wrapper；配置生成器和配套加载代码也已同步升级，支持普通 JSON 浮点参数。运行命令前进入工程根目录，并确保 JAVA_HOME 或 PATH 指向 JDK 25。

```powershell
.\genconfig.bat /S
.\gradlew.bat test jar
.\gradlew.bat runExample
.\gradlew.bat verifyJarExample
```

`runExample` 使用内存测试宿主运行 findOwner → 移动 → 交互、查找对象和递归子树；`verifyJarExample` 从打包后的 JAR 加载框架并运行同一示例。示例和测试宿主位于 `src/test/java/BehaviourExample.java`、`src/test/java/testsupport/TreeFixture.java`，不包含在正式库中。

配置在 `config/behaviour/behaviour.cfg`、`config/behaviour/behaviourEnum.xlsx` 和 `config/_behaviour_behaviour/*.json`。修改后运行 `genconfig.bat`，由同一生成器更新 `gen/src`、配置加载代码和 `config.data`。不要混用旧生成器产生的数据；保留的 `tools/configgen.jar` 是旧工具，当前脚本使用 `tools/cfggen.jar`。编辑器服务启动脚本也使用 JDK 25。

## 接入宿主

实现 `behaviour.GEntity` 和 `behaviour.BehaviourHost`，然后创建运行时。原有默认包入口 `BehaviourManager.start()` 加载配置、注册节点并预加载树；接入项目如果已有自己的配置加载流程，在 `ConfigMgr.setMgr(...)` 后调用 `BehaviourFactory.start()`、`BehaviourFactory.preloadBehaviourTrees(...)`。

```java
BehaviourManager.start();
var task = behaviour.BehaviourFactory.createBehaviourTreeTask(
        entity, host, config.behaviour.Behaviour.get(8), java.util.List.of());
var status = task.update();
// 随宿主逻辑帧继续调用 update；不再运行时释放资源。
task.interrupt();
```

同一个 `BehaviourTree` 可创建多个运行时；可变状态和监听不会共享。`update()` 返回本次真实终态，成功或失败后自动重置本轮状态，下次调用会重新进入；黑板值保留。`reset(false)`、`reset(true)` 和 `interrupt()` 都会先取消活动操作并释放活动分支、子树及监听，`clearBlackBoard()` 才清空黑板。

`GEntity` 提供实例 ID、不可变位置快照、主人实体实例 ID（0 表示无主人）、销毁状态和事件注册/注销。默认事件实现由 `GEntityEventManager` 承接；宿主也可以覆盖接口，接入自己的事件总线。

`BehaviourHost` 提供当前场景的逻辑时间、实体查询、移动、停止/朝向和交互接口。`findEntities` 必须按传入对象类型、配置 ID 列表筛选，返回当前场景的候选实体；节点再检查实体存活和距离，按宿主给定顺序选择首个合法结果。查询不是等待对象出生的订阅。

移动和交互返回 `BehaviourOperation`，`update()` 只返回 `BTRUNNING`、`BTSUCCESS` 或 `BTFAILURE`，拒绝操作返回 null。`cancel()` 必须幂等，操作结束或取消后不能继续产生副作用。正常终态不重复调用 cancel；运行中重置、中断和目标失效会取消操作，重入会创建新操作。

所有树更新和事件必须位于宿主同一执行线程，时间使用单调推进的逻辑毫秒。位置使用独立不可变的 `math.Vector3`，朝向使用与世界 +Z 轴的顺时针角度（度）。框架不提供真实场景、寻路或家具交互系统。

## 事件与响应式条件

宿主在实际状态变更之后发送事件：

```java
entity.triggerEvent(new event.object.EventMove(entity));
entity.triggerEvent(new event.object.EventInteractionChanged(entity));
entity.triggerEvent(new event.object.EventObjectLeaveMap(entity));
```

离场前先更新 `isDestroyed()` 对应状态；交互变化时通知相关实体，尤其是发起者。事件回调只标记条件 dirty，树在下一次更新中重算；不要在回调里递归 tick。监听者拥有幂等释放句柄，回调内注册、注销和嵌套分派不会破坏集合迭代。

`DecCondition` 和 Composite.precond 可保护运行中的子树。黑板目标变化或 And/Or 短路分支切换后，会释放旧订阅并重建当前依赖。`GetPosListenMove` 监听位置变化，`IsEntityDestroyed` 监听离场，`CheckIsInInteraction` 监听交互变化。读取位置或实例 ID 的表达式要求对象有效；可能为空的目标应先使用 `Not(IsEntityDestroyed(...))` 守卫。

状态装饰器拥有独立子栈，`DecCondition` 使用父栈，不能互相替换。`SubTree` 与父树共享宿主和 Trace context，拥有独立运行时 ID、黑板与操作资源。本地 `BehaviourTraceListener` 可接收树、节点和黑板变化；不包含服务器协议发送。

## 保留范围与迁移

- 控制流：顺序、选择、并行、整数分支、三种权重选择、RepeatUtil、SubTree；成功/失败/取反/日志/循环/定时装饰器和 DecCondition。
- 底层 Act：等待、状态与条件、日志、黑板写入；移动/带朝向移动/停止/改朝向/面向实体；交互进入/退出；ActFindNonePlayerEntity、ActFindSummonOwner。
- Condition / Expression：逻辑、比较、实体销毁及交互状态；保留常量、随机、算术、字符串转换、黑板读写、Self、按 ID 查实体、位置/监听位置、距离和当前逻辑时间。
- 不包含 AIC、公寓、LLM、Buff、矿物、家具占位、店铺接待和服务器任务业务；IsEntityMoved 脉冲条件未迁入，可使用监听位置和距离比较表达空间约束。

findOwner 使用 **主人实体实例 ID**，不使用 AIC 的角色 ID。主人缺失、销毁或 `distance² >= radius²` 时失败并清空旧输出；找对象节点采用 `distance² <= radius²`，保留 2000ms 查询冷却。

旧 1—7 号 JSON 示例已迁移，保留用途；5 号需要黑板初值 `abc=0`，6 号需要 `n=0`，7 号通过参数传入 n，返回黑板 `ret`（例如 n=6 返回 13）。8、9 号为新增宿主示例。数值黑板不再统一转换为 double，long 整数精度保持。

旧 Java 扩展需要迁入 Atom / Executor / Task 分层；配置基接口由 `config.behaviour.Node/Condition/Exp*` 变为对应 `config.behaviour.node.Node`、`condition.Condition`、`expnumber.ExpNumber` 等子包。旧节点名迁为 Comp*、Dec*、Act*（例如 Sequence → CompSequence、WaitTimeMs → ActWaitTimeMs、NodeCondition → ActCondition），日志参数改为字符串表达式。工程内示例已更新，外部旧 JSON 或 Java 调用不会自动兼容。

## 场景状态、召唤与触发器

已迁入 `state`、`summon`、`trigger` 关键代码，保留原注释。格子 AOI 通过 `aoi.GridMapAoiManager`、`aoi.Grid` 接口连接宿主；触发器保留 Common/Grid 算法与跨格事件处理。实现 `StateHost`、`SummonHost`、`TriggerHost` 即可接入，详见 [场景管理器接入说明](docs/scene-managers.md)。

运行 `gradlew.bat runSceneExample` 验证状态中断、召唤/findOwner、区域触发、主人离场计时及监听清理；`verifyJarSceneExample` 验证打包后运行。
