package config.behaviour.node;

// ActInteractionEnter
public final class ActInteractionEnter implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTINTERACTIONENTER;
    }

    private config.behaviour.expobj.ExpObj entity;
    private config.behaviour.expnumber.ExpNumber abilityId;
    private config.behaviour.expnumber.ExpNumber interactionId;
    private config.behaviour.expnumber.ExpNumber dataInteractionId;
    private String note;

    private ActInteractionEnter() {
    }

    public ActInteractionEnter(config.behaviour.expobj.ExpObj entity, config.behaviour.expnumber.ExpNumber abilityId, config.behaviour.expnumber.ExpNumber interactionId, config.behaviour.expnumber.ExpNumber dataInteractionId, String note) {
        this.entity = entity;
        this.abilityId = abilityId;
        this.interactionId = interactionId;
        this.dataInteractionId = dataInteractionId;
        this.note = note;
    }

    public static ActInteractionEnter _create(configgen.genjava.ConfigInput input) {
        ActInteractionEnter self = new ActInteractionEnter();
        self.entity = config.behaviour.expobj.ExpObj._create(input);
        self.abilityId = config.behaviour.expnumber.ExpNumber._create(input);
        self.interactionId = config.behaviour.expnumber.ExpNumber._create(input);
        self.dataInteractionId = config.behaviour.expnumber.ExpNumber._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public config.behaviour.expobj.ExpObj getEntity() {
        return entity;
    }

    public config.behaviour.expnumber.ExpNumber getAbilityId() {
        return abilityId;
    }

    public config.behaviour.expnumber.ExpNumber getInteractionId() {
        return interactionId;
    }

    public config.behaviour.expnumber.ExpNumber getDataInteractionId() {
        return dataInteractionId;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(entity, abilityId, interactionId, dataInteractionId, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActInteractionEnter))
            return false;
        ActInteractionEnter o = (ActInteractionEnter) other;
        return entity.equals(o.entity) && abilityId.equals(o.abilityId) && interactionId.equals(o.interactionId) && dataInteractionId.equals(o.dataInteractionId) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActInteractionEnter(" + entity + "," + abilityId + "," + interactionId + "," + dataInteractionId + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
        abilityId._resolve(mgr);
        interactionId._resolve(mgr);
        dataInteractionId._resolve(mgr);
    }

}
