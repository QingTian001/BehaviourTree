package config.behaviour.condition;

// IsEntityDestroyed
public final class IsEntityDestroyed implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.ISENTITYDESTROYED;
    }

    private config.behaviour.expobj.ExpObj entity;

    private IsEntityDestroyed() {
    }

    public IsEntityDestroyed(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static IsEntityDestroyed _create(configgen.genjava.ConfigInput input) {
        IsEntityDestroyed self = new IsEntityDestroyed();
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
        if (!(other instanceof IsEntityDestroyed))
            return false;
        IsEntityDestroyed o = (IsEntityDestroyed) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "IsEntityDestroyed(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
