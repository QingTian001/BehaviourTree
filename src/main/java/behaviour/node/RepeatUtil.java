package behaviour.node;

import behaviour.BehaviourFactory;
import behaviour.condition.Condition;

/**
 * Created by zyao on 2025/2/18 11:18 <p>
 *
 * RepeatUtil 无论node返回Success/failure, 只要cond为true, 就在一帧内, 继续执行node, node不允许返回Running<p>
 * 配置关联的是CompRepeatUtil, 算归到Composite大类, 但在实际程序实现上是单独实现的<p>
 *
 * 重构后：RepeatUtil 为纯静态配置类 (Flyweight)
 */
public class RepeatUtil extends Node<config.behaviour.node.CompRepeatUtil> {
    private Condition<?> condition = null;
    private Node<?> decoratedNode = null;

    public RepeatUtil(config.behaviour.node.CompRepeatUtil atomCfg) {
        super(atomCfg);
    }

    public final Condition<?> getCondition() {
        return condition;
    }

    public final Node<?> getDecoratedNode() {
        return decoratedNode;
    }

    @Override
    public void loadFromCfg() {
        condition = BehaviourFactory.createCondition(getAtomCfg().getCond());
        decoratedNode = BehaviourFactory.createNode(getAtomCfg().getNode());
    }
}
