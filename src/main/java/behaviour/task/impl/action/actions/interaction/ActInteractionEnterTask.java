package behaviour.task.impl.action.actions.interaction;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.node.action.actions.interaction.ActInteractionEnter;
import behaviour.runtime.action.actions.interaction.ActInteractionEnterRuntime;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;
import behaviour.GEntity;
import behaviour.BehaviourOperation;

/**
 * ActInteractionEnter 的运行时任务实现
 * 由 migrate_actions.py 自动生成
 */
public class ActInteractionEnterTask extends ActionTask implements ActInteractionEnterRuntime {

    // 运行时状态
    private GEntity target = null;
    private long abilityCfgId = 0;
    private long interactionCfgId = 0;
    private BehaviourOperation jobInteraction;

    public ActInteractionEnterTask(ActInteractionEnter atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActInteractionEnter getAtom() {
        return (ActInteractionEnter) super.getAtom();
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

    public BehaviourOperation getBehaviourOperation() {
        return jobInteraction;
    }

    public void setBehaviourOperation(BehaviourOperation jobInteraction) {
        this.jobInteraction = jobInteraction;
    }
}
