package config.behaviour.expnumber;

public sealed interface ExpNumber permits ConstInteger, ConstFloat, ConstLong, GetBBNumber, RandInteger, Calculate, Max, Min, GetInstanceId, GetCurrentTimeMills, CalcDist {
    config.behaviour.Expnumberenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static ExpNumber _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "ConstInteger":
                return config.behaviour.expnumber.ConstInteger._create(input);
            case "ConstFloat":
                return config.behaviour.expnumber.ConstFloat._create(input);
            case "ConstLong":
                return config.behaviour.expnumber.ConstLong._create(input);
            case "getBBNumber":
                return config.behaviour.expnumber.GetBBNumber._create(input);
            case "RandInteger":
                return config.behaviour.expnumber.RandInteger._create(input);
            case "Calculate":
                return config.behaviour.expnumber.Calculate._create(input);
            case "Max":
                return config.behaviour.expnumber.Max._create(input);
            case "Min":
                return config.behaviour.expnumber.Min._create(input);
            case "GetInstanceId":
                return config.behaviour.expnumber.GetInstanceId._create(input);
            case "GetCurrentTimeMills":
                return config.behaviour.expnumber.GetCurrentTimeMills._create(input);
            case "CalcDist":
                return config.behaviour.expnumber.CalcDist._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
