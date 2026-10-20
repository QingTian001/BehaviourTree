package behaviour.condition;

/**
 * Success condition.
 */
public class Success extends Condition<config.behaviour.condition.Success> {

    public Success(config.behaviour.condition.Success atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No configuration needed.
    }
}
