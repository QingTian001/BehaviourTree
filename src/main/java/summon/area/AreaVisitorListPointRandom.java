package summon.area;

import area.AreaCircle;
import area.AreaFan;
import math.Vector3;

/**
 * Created by zyao on 2025/3/1 17:57
 */
public class AreaVisitorListPointRandom extends AreaVisitorListPoint {
    public AreaVisitorListPointRandom(int genNum) {
        super(genNum);
    }

    @Override
    protected void genPoints(AreaFan area) {
        // 多次随机, 然后存入points
        for (int i = 0; i < getGenNum(); i++) {
            Vector3 randomPoint = area.genRandomPoint();
            getPoints().add(randomPoint);
        }
    }
}
