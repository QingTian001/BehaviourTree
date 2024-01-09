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

    public void popRunningNodeUtil(Node<? extends config.behaviour.Node> node) {
        Node<? extends config.behaviour.Node> popNode = null;
        while (true) {
            popNode = popRunningNode();
            if (popNode == node) {
                return;
            }
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

    // TODO 有重复代码需要精简
    public Statusenum updateRunningNode(Node<? extends config.behaviour.Node> endParentNode) {
        OutObject<behaviour.node.Node<? extends config.behaviour.Node>> obj = new OutObject<>();
        boolean ret = this.checkStackPreConditions(obj);

        if (!ret) {
            var node = obj.value;
            this.popRunningNodeUtil(node);
            node.reset(true);

            if (node.getParent() != null) {
                var nodeStatus = Statusenum.BTFAILURE;

                while (true) {
                    node = node.getParent();
                    if (node == endParentNode) {
                        return nodeStatus;

                    }
                    nodeStatus = node.update(this, nodeStatus);
                    if (nodeStatus == Statusenum.BTRUNNING) {
                        return Statusenum.BTRUNNING;
                    }
                }
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
                while (true) {
                    node = node.getParent();
                    if (node == endParentNode) {
                        return nodeStatus;

                    }
                    nodeStatus = node.update(this, nodeStatus);
                    if (nodeStatus == Statusenum.BTRUNNING) {
                        return Statusenum.BTRUNNING;
                    }
                }
            }

        }
    }



}
