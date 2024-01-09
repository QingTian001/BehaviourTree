package config.behaviour.condition;

public class NULL implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.NULL;
    }


    public NULL() {
    }

    public static NULL _create(configgen.genjava.ConfigInput input) {
        NULL self = new NULL();
        return self;
    }

    @Override
    public int hashCode() {
        return NULL.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof NULL;
    }

    @Override
    public String toString() {
        return "NULL";
    }

}
