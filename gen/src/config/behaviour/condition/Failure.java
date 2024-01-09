package config.behaviour.condition;

public class Failure implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.FAILURE;
    }


    public Failure() {
    }

    public static Failure _create(configgen.genjava.ConfigInput input) {
        Failure self = new Failure();
        return self;
    }

    @Override
    public int hashCode() {
        return Failure.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Failure;
    }

    @Override
    public String toString() {
        return "Failure";
    }

}
