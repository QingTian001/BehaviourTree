package behaviour.node.composite;

import config.behaviour.Node;
import config.behaviour.Statusenum;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parallel extends Composite<config.behaviour.node.Parallel>{

    private final Set<Integer> finishedIndexes = new HashSet<>();
    private int failureNodeNum = 0;
    private int successNodeNum = 0;

    private final List<BehaviourStack> behaviourStackList = new ArrayList<>();

    public Parallel(BehaviourTree behaviourTree, Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        config.behaviour.node.Parallel cfg = getNodeCfg();

        for (int i = 0 ; i < getNodeList().size(); i++) {
            if (finishedIndexes.contains(i)) {
                continue;
            }

            var node = getNodeList().get(i);

            Statusenum status;
            if (node.getStatus() == Statusenum.BTRUNNING) {
                status = behaviourStackList.get(i).updateRunningNode(this);
            } else {
                status = node.update(behaviourStackList.get(i));
            }
            if (status == Statusenum.BTFAILURE) {
                if (cfg.getOneFailAllFail()) {
                    return Statusenum.BTFAILURE;
                }
                failureNodeNum++;
            } else if (status == Statusenum.BTSUCCESS) {
                if (cfg.getOneSuccAllSucc()) {
                    return Statusenum.BTSUCCESS;
                }
                successNodeNum++;
            }

            if (BehaviourHelper.isNodeFinished(status)) {
                finishedIndexes.add(i);
            }
        }

        if (finishedIndexes.size() == getNodeList().size()) {
            if (successNodeNum == getNodeList().size()) {
                return Statusenum.BTSUCCESS;
            } else { // 只要successNodeNum < 总的节点数量, 就返回FAiLURE
                return Statusenum.BTFAILURE;
            }
        }
        return Statusenum.BTRUNNING;
    }





    @Override
    protected List<? extends Node> getConfigNodeList() {
        return getNodeCfg().getNodes();
    }

    @Override
    public void reset(boolean recursive) {
        finishedIndexes.clear();
        for (var bs : behaviourStackList) {
            bs.reset();
        }
        super.reset(recursive);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        for (var node : getNodeList()) {
            behaviourStackList.add(new BehaviourStack());
        }
    }

}
