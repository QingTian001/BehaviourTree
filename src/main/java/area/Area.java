package area;

import math.Vector3;
import util.MathUtil;

import java.util.*;

/**
 * Created by zyao on 2022/2/14 14:43
 */
public abstract class Area {

    public abstract boolean isInArea(Vector3 center, float radius);

    public abstract boolean isInArea(Vector3 center);

    public abstract Vector3 genRandomPoint();

    public float genRandomDir() {
        return MathUtil.randomFloat(360.0f);
    }

    public List<Vector3> genNotRepeatRandomPoints(int num, Collection<Vector3> usedPoints) {
        throw new RuntimeException("not supported");
    }

    public boolean canBatchGenNotRepeatRandomPoints() {
        return false;
    }

    public abstract void accept(IAreaVisitor visitor);
}
