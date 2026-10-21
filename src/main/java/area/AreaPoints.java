package area;

import math.Vector3;
import util.MathUtil;

import java.util.*;

/**
 * Created by zyao on 2024/1/19 18:45
 */
public class AreaPoints extends Area{

    private List<Vector3> areaPoints;
    public AreaPoints(List<Vector3> areaPoints) {
        this.areaPoints = areaPoints;
    }
    @Override
    public boolean isInArea(Vector3 center, float radius) {
        return false;
    }

    @Override
    public boolean isInArea(Vector3 center) {
        return false;
    }

    @Override
    public Vector3 genRandomPoint() {
        return areaPoints.get(MathUtil.randomInt(areaPoints.size()));
    }

    @Override
    public List<Vector3> genNotRepeatRandomPoints(int num, Collection<Vector3> usedPoints) {
        List<Vector3> randomPoints = new ArrayList<>();
        for (Vector3 point : areaPoints) {
            if (!usedPoints.contains(point)) {
                randomPoints.add(point);
            }
        }
        if (num >= randomPoints.size()) {
            return Collections.unmodifiableList(randomPoints);
        }
        Collections.shuffle(randomPoints);
        return Collections.unmodifiableList(new ArrayList<>(randomPoints.subList(0, num)));
    }

    @Override
    public boolean canBatchGenNotRepeatRandomPoints() {
        return true;
    }

    @Override
    public void accept(IAreaVisitor visitor) {
        visitor.visit(this);
    }
}
