package behaviour.node.action.actions.time;

import behaviour.node.action.Action;

/**
 * Created by zyao on 2025/11/25 10:45
 */
public class ActWaitRandomTimeMs extends Action<config.behaviour.node.ActWaitRandomTimeMs> {
    public ActWaitRandomTimeMs(config.behaviour.node.ActWaitRandomTimeMs atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
    }
}
