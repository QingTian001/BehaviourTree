package summon.area;

import area.AreaCircle;
import area.AreaFan;
import area.IAreaVisitor;
import math.Vector3;
import java.util.*;

/**
 * Created by zyao on 2025/3/1 17:47
 */
public abstract class AreaVisitorListPoint implements IAreaVisitor {
    private final List<Vector3> points = new ArrayList<>();
    private final int genNum;

    public AreaVisitorListPoint(int genNum) {
        this.genNum = genNum;
    }

    public final List<Vector3> getPoints() {
        return points;
    }

    public final int getGenNum() {
        return genNum;
    }
    protected abstract void genPoints(AreaFan area);

    @Override
    public void visit(AreaFan area) {
        genPoints(area);
    }

    public static AreaVisitorListPoint create(EDistributeType distributeType, int genNum) {
        switch (distributeType) {
            case RANDOM:
                return new AreaVisitorListPointRandom(genNum);
            case EDGE:
                return new AreaVisitorListPointEdge(genNum);
            default:
                throw new RuntimeException();
        }
    }
}
