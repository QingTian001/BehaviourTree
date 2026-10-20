package behaviour.node.action.actions;

import behaviour.node.action.Action;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zyao on 2025/5/21 17:51
 */
public class ActFindNonePlayerEntity extends Action<config.behaviour.node.ActFindNonePlayerEntity> {
    public static final long ENTER_CD = 2000L;

    private float radiusSquare;
    private final Set<Long> cfgIdSet = new HashSet<>();

    public ActFindNonePlayerEntity(config.behaviour.node.ActFindNonePlayerEntity atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        radiusSquare = getAtomCfg().getRadius() * getAtomCfg().getRadius();
        for (int cfgId : getAtomCfg().getCfgIdList()) {
            cfgIdSet.add((long) cfgId);
        }
    }

    public float getRadiusSquare() {
        return radiusSquare;
    }

    public Set<Long> getCfgIdSet() {
        return cfgIdSet;
    }
}
