package behaviour.expression;

import behaviour.GEntity;
import event.MapObjectEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import behaviour.BehaviourListenerManager;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;



/**
 * Created by zyao on 2023/12/20 17:52
 */
public abstract class Expression<T, V> {
    private static final Logger logger = LogManager.getLogger(Expression.class);
    private final T exprCfg;
    // 由Condition使用的时候会设置Condition
    private final Condition<? extends config.behaviour.Condition> condition;
    private final BehaviourListenerManager listenerManager;
    public final BehaviourTree behaviourTree;

    public Expression(BehaviourTree behaviourTree, T exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        this.exprCfg = exprCfg;
        this.condition = condition;
        this.behaviourTree = behaviourTree;
        this.listenerManager = BehaviourListenerManager.createBehaviourListenerManager(condition);
    }

    public final V calculateExpression() {
        V value = internalCalculateExpression();
        logger.debug("calculateExpression. class:{}, ret = {}", this.getClass().getSimpleName(), value);
        return value;
    }

    public final V calculateExpressionAndListenEvent() {
        V value = internalCalculateExpressionAndListenEvent();
        logger.debug("calculateExpressionAndListenEvent. class:{}, ret = {}", this.getClass().getSimpleName(), value);
        return value;
    }

    protected abstract V internalCalculateExpression();

    protected abstract V internalCalculateExpressionAndListenEvent();

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }

    public final T getExprCfg() {
        return exprCfg;
    }

    public final Condition<? extends config.behaviour.Condition> getCondition() {
        return condition;
    }
    protected final <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz) {
        if (listenerManager == null) {
            throw new RuntimeException("condtion must be not null");
        }
        logger.info("registerMapObjectEvent. class:{}, entity:{}, eventClazz:{}", this.getClass().getSimpleName(), entity, eventClazz);
        listenerManager.registerMapObjectEvent(entity, eventClazz);
    }

    @SuppressWarnings("unchecked")
    public void reset() {
        listenerManager.onReset();
    }

    public void loadFromCfg() {

    }
}
