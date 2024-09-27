package behaviour;

import util.OutObject;
import config.behaviour.Statusenum;
import behaviour.condition.Condition;
import behaviour.node.IPreCondition;
import behaviour.node.Node;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public final class BehaviourStack {
    private final ArrayDeque<Condition<? extends config.behaviour.Condition>> preCondList = new ArrayDeque<>();
    private final Map<Condition<? extends config.behaviour.Condition>, Node<? extends config.behaviour.Node>> cond2NodeMap = new HashMap<>();
    private final ArrayDeque<Node<? extends config.behaviour.Node>> stack = new ArrayDeque<>();

    public void pushRunningNode(Node<? extends config.behaviour.Node> node) {
        stack.push(node);
        if (node instanceof IPreCondition ) {
            var cond = ((IPreCondition)node).getPreCondition();
            if (cond != null) {
                preCondList.push(cond);
                cond2NodeMap.put(cond, node);
            }
        }
    }

    public Node<? extends config.behaviour.Node> popRunningNode() {
        Node<? extends config.behaviour.Node> node = stack.pop();
        if (node instanceof IPreCondition ) {
            var cond = ((IPreCondition)node).getPreCondition();
            if (cond != null) {
                preCondList.pop();
                cond2NodeMap.remove(cond);
            }
        }
        return node;
    }

    public void popAndInterruptRunningNodeUtil(Node<? extends config.behaviour.Node> node) {
        Node<? extends config.behaviour.Node> popNode;
        while (true) {
            popNode = popRunningNode();
            popNode.interrupt();
            if (popNode == node) {
                return;
            }
        }
    }

    public void popAndInterruptRunningNodeAll() {
        Node<? extends config.behaviour.Node> popNode;
        while (!stack.isEmpty()) {
            popNode = popRunningNode();
            popNode.interrupt();
        }
    }

    public Node<? extends config.behaviour.Node> peekRunningNode() {
        return stack.peek();
    }

    public void reset() {
        stack.clear();
        preCondList.clear();
    }

    public boolean checkStackPreConditions(OutObject<Node<? extends config.behaviour.Node>> obj) {
        // 验证前置条件是否可以执行
        for (var cond : preCondList) {
            if (cond.isDirty()) {
                boolean ret = cond.calculateConditionResult();
                cond.clearDirty();
                if (!ret) {
                    obj.value = cond2NodeMap.get(cond);
                    return false;
                }
            }
        }
        return true;
    }

    private Statusenum backTraceParentNode(Node<? extends config.behaviour.Node> curNode, Node<? extends config.behaviour.Node> endParentNode, Statusenum childStatus) {
        Statusenum nodeStatus = childStatus;
        while (true) {
            curNode = curNode.getParent();
            if (curNode == endParentNode) {
                return nodeStatus;
            }
            nodeStatus = curNode.update(this, nodeStatus);
            if (nodeStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            }
        }
    }
    public Statusenum updateRunningNode(Node<? extends config.behaviour.Node> endParentNode) {
        OutObject<Node<? extends config.behaviour.Node>> obj = new OutObject<>();
        boolean ret = this.checkStackPreConditions(obj);

        if (!ret) {
            var node = obj.value;
            this.popAndInterruptRunningNodeUtil(node);
            node.reset(true);

            if (node.getParent() != null) {
                return this.backTraceParentNode(node, endParentNode, Statusenum.BTFAILURE);
            } else {
                return Statusenum.BTFAILURE;
            }
        }
        else {
            var node = this.peekRunningNode();
            Statusenum nodeStatus = node.update(this);
            if (nodeStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            }
            else {
                return this.backTraceParentNode(node, endParentNode, nodeStatus);
            }
        }
    }
}
