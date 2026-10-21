package summon;
import behaviour.GEntity;
import summon.create.EntityCreateStrategy;
/** 实体生命周期由宿主实现，需在移除实体时向该实体发布 EventObjectLeaveMap。 */
public interface SummonHost {
    long getNowMills();
    SummonConfig getSummonConfig(int id);
    float getDirection(GEntity entity);
    EntityCreateStrategy getEntityCreateStrategy(int objectType);
    void enterMap(GEntity entity);
    void leaveMap(GEntity entity);
    /** 设置 owner、召唤标记及 AOI 属性；正数生命截止时间交宿主定时执行。 */
    void configureSummon(GEntity entity, GEntity owner, boolean aoiPublic, long lifeDeadline);
    /** owner=null 时必须使 GEntity.getOwnerInstId() 返回 0。 */
    void setOwner(GEntity entity, GEntity owner);
    /** 与生命截止时间独立；宿主负责到期销毁。 */
    void setOwnerLeaveDeadline(GEntity entity, long deadline);
}
