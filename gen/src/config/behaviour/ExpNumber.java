package config.behaviour;

public interface ExpNumber {
    config.behaviour.Expnumberenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static ExpNumber _create(configgen.genjava.ConfigInput input) {
        switch(input.readStr()) {
            case "ConstFloat":
                return config.behaviour.expnumber.ConstFloat._create(input);
            case "ConstInteger":
                return config.behaviour.expnumber.ConstInteger._create(input);
            case "ConstLong":
                return config.behaviour.expnumber.ConstLong._create(input);
            case "getBBNumber":
                return config.behaviour.expnumber.GetBBNumber._create(input);
            case "Calculate":
                return config.behaviour.expnumber.Calculate._create(input);
        }
        throw new IllegalArgumentException();
    }
}
