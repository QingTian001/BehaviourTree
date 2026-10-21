package summon.create;
import behaviour.GEntity;
import math.Vector3;
import summon.SummonUnit;
import summon.param.SummonEntityParam;
/**
 * Created by zyao on 2025/9/10 11:03
 */
/** 宿主按对象类型创建实体；返回 null 表示本次未创建，进入地图由管理器统一执行。 */
@FunctionalInterface
public interface EntityCreateStrategy {
    // 迁移说明：下列来源注释的对象类型由宿主映射，工程不引入服务器 Objecttype。
    // Objecttype.NPC.getId()
    // Objecttype.MINE.getId()
    // Objecttype.FURNITURE.getId()
    GEntity createIfNecessary(SummonUnit unit, Vector3 position, float direction,
            int summonCfgId, SummonEntityParam param);
}
