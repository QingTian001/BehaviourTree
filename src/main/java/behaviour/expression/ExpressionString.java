package behaviour.expression;

/**
 * Expression that returns a String.
 * Flyweight pattern - logic here, no runtime state.
 */
public abstract class ExpressionString<T extends config.behaviour.expstr.ExpStr> extends Expression<T, String> {

    public ExpressionString(T atomCfg) {
        super(atomCfg);
    }

}
