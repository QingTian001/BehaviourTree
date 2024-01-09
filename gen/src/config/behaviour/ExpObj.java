package config.behaviour;

public interface ExpObj {
    config.behaviour.Expobjenum type();

    static ExpObj _create(configgen.genjava.ConfigInput input) {
        switch(input.readStr()) {
            case "getBBObj":
                return config.behaviour.expobj.GetBBObj._create(input);
        }
        throw new IllegalArgumentException();
    }
}
