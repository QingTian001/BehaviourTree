package behaviour;

import config.behaviour.Behaviour;
import util.ReflectionUtil;
import util.TypeScanner;
import behaviour.condition.Condition;
import behaviour.expression.Expression;
import behaviour.node.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;


public class BehaviourFactory {

    @SuppressWarnings("unchecked")

    private static final Map<Class<? extends config.behaviour.Node>, Constructor<? extends Node<? extends config.behaviour.Node>>> nodeConstuctorMap = new HashMap<>();
    private static final Map<Class<?  extends config.behaviour.Condition>, Constructor<? extends Condition<?>>> conditionConstuctorMap = new HashMap<>();

    private static final Map<Class<?>, Constructor<? extends Expression<?, ?>>> expressionConstuctorMap = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static void start() {
        for (Class<?> clazz : TypeScanner.scan("behaviour", true)) {
            try {
                if (Node.class.isAssignableFrom(clazz) && clazz != Node.class && !Modifier.isAbstract(clazz.getModifiers())){
                    Class<? extends config.behaviour.Node> cfgClz = ReflectionUtil.getSuperGenericType(clazz);
                    nodeConstuctorMap.put(cfgClz, (Constructor<? extends Node<? extends config.behaviour.Node>>) clazz.getDeclaredConstructor(BehaviourTree.class, config.behaviour.Node.class));
                }
                if (Condition.class.isAssignableFrom(clazz) && clazz != Condition.class && !Modifier.isAbstract(clazz.getModifiers())) {
                    Class<? extends config.behaviour.Condition> cfgClz = ReflectionUtil.getSuperGenericType(clazz);
                    conditionConstuctorMap.put(cfgClz, (Constructor<? extends Condition<?>>) clazz.getDeclaredConstructor(BehaviourTree.class, config.behaviour.Condition.class, Condition.class));
                }
                if (Expression.class.isAssignableFrom(clazz) && clazz != Expression.class && !Modifier.isAbstract(clazz.getModifiers())) {
                    Class<?> cfgClz = ReflectionUtil.getSuperGenericType(clazz);
                    expressionConstuctorMap.put(cfgClz, (Constructor<? extends Expression<?, ?>>) clazz.getDeclaredConstructor(BehaviourTree.class, cfgClz.getInterfaces()[0], Condition.class));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends config.behaviour.Node> Node<T> createNode(BehaviourTree behaviourTree, T nodeCfg) {
        Constructor<? extends Node<T>> nodeConstructor =  (Constructor<? extends Node<T>>)nodeConstuctorMap.get(nodeCfg.getClass());

        Node<T> node = null;
        try {
            node =  nodeConstructor.newInstance(behaviourTree, nodeCfg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        node.loadFromCfg();
        return node;
    }

    @SuppressWarnings("unchecked")
    public static <T extends config.behaviour.Condition> Condition<T> createCondition(BehaviourTree behaviourTree, T condCfg, Condition<? extends config.behaviour.Condition> parentCondition) {
        Constructor<? extends Condition<T>> condConstructor =  (Constructor<? extends Condition<T>>)conditionConstuctorMap.get(condCfg.getClass());

        Condition<T> cond = null;
        try {
            cond =  condConstructor.newInstance(behaviourTree, condCfg, parentCondition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cond.loadFromCfg();
        return cond;
    }

    @SuppressWarnings("unchecked")
    public static <T, V, E extends Expression<? extends T, V>> E createExpressionWithoutCondition(BehaviourTree behaviourTree, T exprCfg) {
        return createExpression(behaviourTree, exprCfg, null);
    }

    @SuppressWarnings("unchecked")
    public static <T, V, E extends Expression<? extends T, V>> E createExpression(BehaviourTree behaviourTree, T exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        Constructor<? extends Expression<T, V>> exprConstructor =  (Constructor<? extends Expression<T, V>>)expressionConstuctorMap.get(exprCfg.getClass());

        Expression<T, V> expr = null;
        try {
            expr =  exprConstructor.newInstance(behaviourTree, exprCfg, condition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        expr.loadFromCfg();

        return (E)expr;
    }


    public static BehaviourTree createBehaviourTree(GEntity entity, Behaviour behaviourCfg, boolean autoReset) {
        return new BehaviourTree(entity, behaviourCfg, autoReset);
    }


}
