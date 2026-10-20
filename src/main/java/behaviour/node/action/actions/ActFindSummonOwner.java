package behaviour.node.action.actions;

import behaviour.node.action.Action;

/**
 * Created by zyao on 2025/4/27 16:23
 */
public class ActFindSummonOwner extends Action<config.behaviour.node.ActFindSummonOwner> {
    private float radiusSquare;

    public ActFindSummonOwner(config.behaviour.node.ActFindSummonOwner atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        radiusSquare = getAtomCfg().getRadius() * getAtomCfg().getRadius();
    }

    public float getRadiusSquare() {
        return radiusSquare;
    }
}
