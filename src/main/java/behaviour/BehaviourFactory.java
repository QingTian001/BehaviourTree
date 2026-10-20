package behaviour;

import behaviour.condition.Condition;
import behaviour.executor.Executor;
import behaviour.expression.Expression;
import behaviour.node.Node;
import behaviour.runtime.*;
import behaviour.runtime.action.ActDebugMsgRuntime;
import behaviour.node.action.ActDebugMsg;
import behaviour.task.*;
import behaviour.trace.BehaviourTraceContext;
import config.behaviour.Behaviour;
import util.TypeScanner;
import java.lang.reflect.*;
import java.util.*;

/** 独立工程的配置、执行器和任务注册入口。 */
public final class BehaviourFactory {
    private static final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();
    private static volatile Map<Integer, BehaviourTree> trees = Map.of();
    private static boolean started;

    public static synchronized void start() {
        if (started) { return; }
        for (Class<?> type : TypeScanner.scan("behaviour", true)) {
            if (!Atom.class.isAssignableFrom(type) || Modifier.isAbstract(type.getModifiers())
                    || !Modifier.isPublic(type.getModifiers())) { continue; }
            if (!(type.getGenericSuperclass() instanceof ParameterizedType parent)
                    || !(parent.getActualTypeArguments()[0] instanceof Class<?> cfg)
                    || !cfg.getName().startsWith("config.behaviour.")) { continue; }
            try {
                Constructor<?> constructor = type.getConstructor(cfg);
                Constructor<?> previous = constructors.putIfAbsent(cfg, constructor);
                if (previous != null && previous.getDeclaringClass() != type) {
                    throw new IllegalStateException("重复配置注册: " + cfg.getName());
                }
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("缺少配置构造函数: " + type.getName(), e);
            }
        }
        started = true;
    }

    private static Atom<?> createAtom(Object cfg) {
        if (cfg == null) { throw new IllegalArgumentException("配置为空"); }
        start();
        Constructor<?> constructor = constructors.get(cfg.getClass());
        if (constructor == null) { throw new IllegalArgumentException("配置未注册: " + cfg.getClass().getName()); }
        try {
            Atom<?> atom = (Atom<?>) constructor.newInstance(cfg);
            atom.loadFromCfg();
            Class<?> executorType = Class.forName(atom.getClass().getName().replace("behaviour.", "behaviour.executor.") + "Executor");
            atom.bindExecutor((Executor<?>) executorType.getConstructor(atom.getClass()).newInstance(atom));
            return atom;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("创建节点失败: " + cfg.getClass().getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends config.behaviour.node.Node> Node<T> createNode(T cfg) { return (Node<T>) createAtom(cfg); }
    @SuppressWarnings("unchecked")
    public static <T extends config.behaviour.condition.Condition> Condition<T> createCondition(T cfg) { return (Condition<T>) createAtom(cfg); }
    public static <T, V, E extends Expression<? extends T, V>> E createExpression(T cfg) { return createExpression(cfg, null); }
    @SuppressWarnings("unchecked")
    public static <T, V, E extends Expression<? extends T, V>> E createExpression(T cfg, Condition<?> condition) { return (E) createAtom(cfg); }

    private static BehaviourTask<?> instantiate(Atom<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        String suffix = atom.getClass().getName().substring("behaviour.".length());
        String taskSuffix = suffix.startsWith("node.action.") ? suffix.substring("node.".length()) : suffix;
        for (String candidate : List.of("behaviour.task.impl." + taskSuffix + "Task", "behaviour.task." + atom.getClass().getSimpleName() + "Task")) {
            try {
                Class<?> taskType = Class.forName(candidate);
                for (Constructor<?> constructor : taskType.getConstructors()) {
                    Class<?>[] args = constructor.getParameterTypes();
                    if (args.length == 3 && args[0].isInstance(atom)
                            && (parent == null || args[1].isInstance(parent)) && args[2] == BehaviourContext.class) {
                        BehaviourTask<?> task = (BehaviourTask<?>) constructor.newInstance(atom, parent, context);
                        task.init();
                        return task;
                    }
                }
                throw new IllegalStateException("缺少任务构造函数: " + candidate);
            } catch (ClassNotFoundException ignored) {
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("创建任务失败: " + candidate, e);
            }
        }
        throw new IllegalStateException("任务未注册: " + atom.getClass().getName());
    }
    @SuppressWarnings("unchecked")
    public static NodeTask createNodeRuntime(Node<?> atom, NodeTask parent, BehaviourContext context) {
        return (NodeTask) instantiate(atom, parent, context);
    }
    public static <R extends NodeRuntime> R createNodeRuntime(Node<?> atom, NodeTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    public static <R extends NodeRuntime> R instantiateNodeRuntime(Node<?> atom, NodeTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    @SuppressWarnings("unchecked")
    public static ConditionTask createConditionRuntime(Condition<?> atom, ConditionTask parent, BehaviourContext context) {
        return (ConditionTask) instantiate(atom, parent, context);
    }
    public static <R extends ConditionRuntime> R createConditionRuntime(Condition<?> atom, ConditionTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    public static <R extends ConditionRuntime> R instantiateConditionRuntime(Condition<?> atom, ConditionTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    @SuppressWarnings("unchecked")
    public static <V> ExpressionTask<V> createExpressionRuntime(Expression<?, V> atom, ConditionTask parent, BehaviourContext context) {
        return (ExpressionTask<V>) instantiate(atom, parent, context);
    }
    public static <V, R extends ExpressionRuntime<V>> R createExpressionRuntime(Expression<?, V> atom, ConditionTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    public static <V, R extends ExpressionRuntime<V>> R instantiateExpressionRuntime(Expression<?, V> atom, ConditionTask parent, BehaviourContext context, Class<R> type) {
        return type.cast(instantiate(atom, parent, context));
    }
    public static <R extends ActionRuntime> R createActionRuntime(Node<?> atom, NodeTask parent, BehaviourContext context, Class<R> type) {
        return instantiateNodeRuntime(atom, parent, context, type);
    }
    public static ActDebugMsgRuntime createActDebugMsgRuntime(ActDebugMsg atom, NodeTask parent, BehaviourContext context) {
        return instantiateNodeRuntime(atom, parent, context, ActDebugMsgRuntime.class);
    }
    public static void preloadBehaviourTrees(Collection<Behaviour> configs) {
        Map<Integer, BehaviourTree> loaded = new LinkedHashMap<>();
        for (Behaviour cfg : configs) { loaded.put(cfg.getId(), new BehaviourTree(cfg)); }
        trees = Map.copyOf(loaded);
    }
    public static BehaviourTree getBehaviourTree(Behaviour cfg) { return trees.get(cfg.getId()); }
    public static int getCachedBehaviourTreeCount() { return trees.size(); }
    public static BehaviourTreeTask createBehaviourTreeTask(GEntity entity, BehaviourHost host, Behaviour cfg, List<Object> params) {
        return createBehaviourTreeTask(entity, host, cfg, params, new BehaviourTraceContext(), 0, 0);
    }
    public static BehaviourTreeTask createBehaviourTreeTask(GEntity entity, BehaviourHost host, Behaviour cfg, List<Object> params,
                                                            BehaviourTraceContext trace, long parentTreeId, long parentNodeId) {
        BehaviourTree tree = getBehaviourTree(cfg);
        if (tree == null) { throw new IllegalStateException("请先预加载行为树: " + cfg.getId()); }
        BehaviourTreeTask task = tree.createRuntime(entity, host, new BehaviourTraceContext(), parentTreeId, parentNodeId);
        if (params != null) { task.initParams(params); }
        task.setTraceContext(trace);
        return task;
    }
}
