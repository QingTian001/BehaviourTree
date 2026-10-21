package aoi;
import behaviour.GEntity;
/** 仅提供宿主已有 AOI 索引的查询，不负责维护索引。 */
public interface GridMapAoiManager {
    int getDefaultAoiLevel();
    int getGridLength(int aoiLevel);
    Grid getGrid(int gridX, int gridZ, int aoiLevel);
    Grid getCurrentGrid(GEntity entity, int aoiLevel);
    Iterable<? extends GEntity> getGridEntities(Grid grid);
}
