package config.behaviour.node;

// ActMoveStop
public final class ActMoveStop implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTMOVESTOP;
    }

    private config.behaviour.expobj.ExpObj entity;

    private ActMoveStop() {
    }

    public ActMoveStop(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static ActMoveStop _create(configgen.genjava.ConfigInput input) {
        ActMoveStop self = new ActMoveStop();
        self.entity = config.behaviour.expobj.ExpObj._create(input);
        return self;
    }

    public config.behaviour.expobj.ExpObj getEntity() {
        return entity;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(entity);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActMoveStop))
            return false;
        ActMoveStop o = (ActMoveStop) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "ActMoveStop(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
