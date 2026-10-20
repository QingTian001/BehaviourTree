package behaviour.expression;

/**
 * Expression that returns an object.
 */
public abstract class ExpressionObject<T, OBJ> extends Expression<T, OBJ> {

    public ExpressionObject(T atomCfg) {
        super(atomCfg);
    }
}
