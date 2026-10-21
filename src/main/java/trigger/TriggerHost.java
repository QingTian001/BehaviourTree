package trigger;
import behaviour.GEntity;
import aoi.GridMapAoiManager;
/** 先更新视野/格子数据，再在场景线程发布 AOI、跨格和移动事件。 */
public interface TriggerHost {
    long getNowMills();
    Iterable<? extends GEntity> getSeeingEntities(GEntity observer);
    int getEntityType(GEntity entity);
    default GridMapAoiManager getGridMapAoiManager() { return null; }
}
