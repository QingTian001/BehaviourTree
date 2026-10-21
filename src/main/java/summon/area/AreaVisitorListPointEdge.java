package summon.area;

import area.AreaCircle;
import area.AreaFan;
import math.Vector3;

/**
 * Created by zyao on 2025/3/1 17:58
 */
public class AreaVisitorListPointEdge extends AreaVisitorListPoint{
    public AreaVisitorListPointEdge(int genNum) {
        super(genNum);
    }

    @Override
    protected void genPoints(AreaFan area) {
        float angle = area.getFanAngle();
        if (getGenNum() == 1) {
            double radian = area.getStartAngle() * Math.PI / 180;
            float x = area.getCenter().x + area.getRadius() * (float) Math.sin(radian);
            float z = area.getCenter().z + area.getRadius() * (float) Math.cos(radian);
            getPoints().add(new Vector3(x, area.getCenter().y, z));
        } else {
            float step = angle / (getGenNum() - 1);
            float startAngle = area.getStartAngle() - area.getHalfAngleRange();
            for (int i = 0; i < getGenNum(); i++) {
                double radian = (startAngle + i * step) * Math.PI / 180;

                float x = area.getCenter().x + area.getRadius() * (float) Math.sin(radian);
                float z = area.getCenter().z + area.getRadius() * (float) Math.cos(radian);
                getPoints().add(new Vector3(x, area.getCenter().y, z));
            }
        }
    }
}
