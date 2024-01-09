package config.behaviour;

public interface ExpStr {
    config.behaviour.Expstrenum type();

    static ExpStr _create(configgen.genjava.ConfigInput input) {
        switch(input.readStr()) {
            case "Const":
                return config.behaviour.expstr.Const._create(input);
            case "getBBStr":
                return config.behaviour.expstr.GetBBStr._create(input);
        }
        throw new IllegalArgumentException();
    }
}
