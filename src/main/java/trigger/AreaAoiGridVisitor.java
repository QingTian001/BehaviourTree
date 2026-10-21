package trigger;

import aoi.Grid;
import aoi.GridMapAoiManager;
import aoi.GridRange;
import area.*;
import math.Vector3;
import java.util.*;

/**
 * Created by zyao on 2025/2/8 12:05
 */
public final class AreaAoiGridVisitor implements IAreaVisitor {
    private final GridMapAoiManager map;
    private final List<Grid> innerGrids = new ArrayList<>();
    private final List<Grid> interactGrids = new ArrayList<>();
    private final int aoiLevel;

    public AreaAoiGridVisitor(GridMapAoiManager map, int aoiLevel) {
        this.map = map;
        this.aoiLevel = aoiLevel;
    }

    public List<Grid> getInnerGrids() {
        return innerGrids;
    }

    public List<Grid> getInteractGrids() {
        return interactGrids;
    }
    @Override
    public void visit(AreaCircle areaCircle) {
        innerGrids.clear();
        interactGrids.clear();

        float radius = areaCircle.getRadius();
        Vector3 center = areaCircle.getCenter();

        // 定义一个网格范围，考虑圆形半径 + 格子长度的范围

        int gridLength = map.getGridLength(aoiLevel);

        // 计算圆形区域的边界网格范围
        int startX = Grid.getAoiGridAxis(center.x - radius, gridLength);
        int endX = Grid.getAoiGridAxis(center.x + radius, gridLength);
        int startZ = Grid.getAoiGridAxis(center.z - radius, gridLength);
        int endZ = Grid.getAoiGridAxis(center.z + radius, gridLength);

        // 创建 GridRange 并遍历
        GridRange gridRange = new GridRange(startX, endX, startZ, endZ, aoiLevel, map);
        for (Grid grid : gridRange.toGridList()) {
            if (isCircleGridContained(areaCircle, grid)) {
                innerGrids.add(grid);
            } else {
                interactGrids.add(grid);
            }
        }
    }

    @Override
    public void visit(AreaRectangle areaRectangle) {
        innerGrids.clear();
        interactGrids.clear();
        if (areaRectangle.isRotation()) {
            throw new RuntimeException("not supported rotation rectangle");
        }
        Vector3 relocateCenter = areaRectangle.getRelocateCenter();
        float xWidth = areaRectangle.getxWidth();
        float zWidth = areaRectangle.getzWidth();

        // 定义一个网格范围，考虑矩形的宽度 + 格子长度的范围
        int gridLength = map.getGridLength(aoiLevel);

        // 计算矩形区域的边界网格范围
        int startX = Grid.getAoiGridAxis(relocateCenter.x - xWidth / 2, gridLength);
        int endX = Grid.getAoiGridAxis(relocateCenter.x + xWidth / 2, gridLength);
        int startZ = Grid.getAoiGridAxis(relocateCenter.z - zWidth / 2, gridLength);
        int endZ = Grid.getAoiGridAxis(relocateCenter.z + zWidth / 2, gridLength);

        // 创建 GridRange 并遍历
        GridRange gridRange = new GridRange(startX, endX, startZ, endZ, aoiLevel, map);
        for (Grid grid : gridRange.toGridList()) {
            if (isRectangleGridContained(areaRectangle, grid)) {
                innerGrids.add(grid);
            } else {
                interactGrids.add(grid);
            }
        }
    }

    private boolean isCircleGridContained(AreaCircle areaCircle, Grid grid) {
        Vector3[] corners = new Vector3[4];
        corners[0] = grid.getLeftBottom();
        corners[1] = grid.getLeftTop();
        corners[2] = grid.getRightTop();
        corners[3] = grid.getRightBottom();

        // 检查所有角是否都在圆形内
        for (Vector3 corner : corners) {
            if (!areaCircle.isInArea(corner)) {
                return false;  // 如果有一个角不在圆形内，则说明格子不完全包含
            }
        }
        return true;
    }

    private boolean isRectangleGridContained(AreaRectangle areaRectangle, Grid grid) {
        Vector3[] corners = new Vector3[4];
        corners[0] = grid.getLeftBottom();
        corners[1] = grid.getLeftTop();
        corners[2] = grid.getRightTop();
        corners[3] = grid.getRightBottom();

        // 检查所有角是否都在矩形内
        for (Vector3 corner : corners) {
            if (!areaRectangle.isInArea(corner)) {
                return false;  // 如果有一个角不在，则说明格子不完全包含
            }
        }
        return true;
    }
}

