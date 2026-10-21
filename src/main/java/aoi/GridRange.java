package aoi;
import java.util.*;
/** 枚举宿主格子，不保存或更新任何 AOI 索引。 */
public record GridRange(int startX, int endX, int startZ, int endZ, int aoiLevel, GridMapAoiManager provider) {
    public List<Grid> toGridList() {
        List<Grid> grids = new ArrayList<>();
        for (int x=startX; x<=endX; x++) {
            for (int z=startZ; z<=endZ; z++) { grids.add(provider.getGrid(x,z,aoiLevel)); }
        }
        return grids;
    }
}
