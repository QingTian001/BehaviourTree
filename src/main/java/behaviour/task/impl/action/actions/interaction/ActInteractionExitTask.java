package behaviour.task.impl.action.actions.interaction;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.node.action.actions.interaction.ActInteractionExit;
import behaviour.runtime.action.actions.interaction.ActInteractionExitRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.BehaviourOperation;

public class ActInteractionExitTask extends ActionTask implements ActInteractionExitRuntime {
    private GEntity target = null;
    private long abilityCfgId = 0;
    private long interactionCfgId = 0;
    private long dataInteractionCfgId = 0;
    private BehaviourOperation jobInteraction;

    public ActInteractionExitTask(ActInteractionExit atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActInteractionExit getAtom() {
        return (ActInteractionExit) super.getAtom();
    }

    public GEntity getTarget() {
        return target;
    }

    public void setTarget(GEntity target) {
        this.target = target;
    }

    public long getAbilityCfgId() {
        return abilityCfgId;
    }

    public void setAbilityCfgId(long abilityCfgId) {
        this.abilityCfgId = abilityCfgId;
    }

    public long getInteractionCfgId() {
        return interactionCfgId;
    }

    public void setInteractionCfgId(long interactionCfgId) {
        this.interactionCfgId = interactionCfgId;
    }

    public long getDataInteractionCfgId() {
        return dataInteractionCfgId;
    }

    public void setDataInteractionCfgId(long dataInteractionCfgId) {
        this.dataInteractionCfgId = dataInteractionCfgId;
    }

    public BehaviourOperation getBehaviourOperation() {
        return jobInteraction;
    }

    public void setBehaviourOperation(BehaviourOperation jobInteraction) {
        this.jobInteraction = jobInteraction;
    }
}