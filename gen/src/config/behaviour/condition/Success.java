package config.behaviour.condition;

public class Success implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.SUCCESS;
    }


    public Success() {
    }

    public static Success _create(configgen.genjava.ConfigInput input) {
        Success self = new Success();
        return self;
    }

    @Override
    public int hashCode() {
        return Success.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Success;
    }

    @Override
    public String toString() {
        return "Success";
    }

}
