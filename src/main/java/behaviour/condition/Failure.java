package behaviour.condition;

/**
 * Failure condition.
 */
public class Failure extends Condition<config.behaviour.condition.Failure> {

    public Failure(config.behaviour.condition.Failure atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No configuration needed.
    }
}
