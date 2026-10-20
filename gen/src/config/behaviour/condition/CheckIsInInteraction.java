package config.behaviour.condition;

// 检查self是否在和entity的交互中
public final class CheckIsInInteraction implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.CHECKISININTERACTION;
    }

    private config.behaviour.expobj.ExpObj entity;
    private config.behaviour.expnumber.ExpNumber abilityId;
    private config.behaviour.expnumber.ExpNumber interactionId;

    private CheckIsInInteraction() {
    }

    public CheckIsInInteraction(config.behaviour.expobj.ExpObj entity, config.behaviour.expnumber.ExpNumber abilityId, config.behaviour.expnumber.ExpNumber interactionId) {
        this.entity = entity;
        this.abilityId = abilityId;
        this.interactionId = interactionId;
    }

    public static CheckIsInInteraction _create(configgen.genjava.ConfigInput input) {
        CheckIsInInteraction self = new CheckIsInInteraction();
        self.entity = config.behaviour.expobj.ExpObj._create(input);
        self.abilityId = config.behaviour.expnumber.ExpNumber._create(input);
        self.interactionId = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    /**
     * 被操作的对象, 比如家具
     */
    public config.behaviour.expobj.ExpObj getEntity() {
        return entity;
    }

    /**
     * abilityId
     */
    public config.behaviour.expnumber.ExpNumber getAbilityId() {
        return abilityId;
    }

    /**
     * interactionDataId
     */
    public config.behaviour.expnumber.ExpNumber getInteractionId() {
        return interactionId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(entity, abilityId, interactionId);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CheckIsInInteraction))
            return false;
        CheckIsInInteraction o = (CheckIsInInteraction) other;
        return entity.equals(o.entity) && abilityId.equals(o.abilityId) && interactionId.equals(o.interactionId);
    }

    @Override
    public String toString() {
        return "CheckIsInInteraction(" + entity + "," + abilityId + "," + interactionId + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
        abilityId._resolve(mgr);
        interactionId._resolve(mgr);
    }

}
