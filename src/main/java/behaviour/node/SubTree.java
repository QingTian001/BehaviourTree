package behaviour.node;

import config.behaviour.Behaviour;
import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;

import java.util.ArrayList;
import java.util.List;


public class SubTree extends Node<config.behaviour.node.SubTree> {

    private BehaviourTree subTree;
    private Behaviour subBehaviourCfg;

    public SubTree(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
        subBehaviourCfg = getNodeCfg().refSubTreeId();

//        if (subBehaviourCfg.getObjType() != behaviourTree.getEntity().getEntityType()) {
//            throw new RuntimeException("subTree entity type is not equal to mainTree. subTreeCfgId:" + subBehaviourCfg.getId() + ", mainTreeCfgId:" + behaviourTree.getBehaviourCfg().getId());
//        }
    }

    @Override
    public void loadFromCfg() {

    }

    @Override
    protected boolean enter() {

        subTree = BehaviourFactory.createBehaviourTree(getBehaviourTree().getEntity(), subBehaviourCfg, false);
        BehaviourTree behaviourTree = getBehaviourTree();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < subBehaviourCfg.getParams().size(); i++) {
            params.add(behaviourTree.getBlackBoard().getValue(getNodeCfg().getParamBBKeys().get(i)));
        }
        subTree.initParams(params);
        return super.enter();
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {

        Statusenum status = subTree.update();
        if (BehaviourHelper.isNodeFinished(status)) {
            if (status == Statusenum.BTSUCCESS) {
                if (!BehaviourHelper.isStringNullOrEmpty(subBehaviourCfg.getReturnValue().getBbKey())
                && !BehaviourHelper.isStringNullOrEmpty(getNodeCfg().getReturnValueBBKey())) {
                    getBehaviourTree().getBlackBoard().putValue(getNodeCfg().getReturnValueBBKey(), (Object)subTree.getBlackBoard().getValue(subBehaviourCfg.getReturnValue().getBbKey()));
                }
            } else {
                BehaviourHelper.logger.error("subTree should not return failure");
            }
            subTree.reset(false);
        }


        return status;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        subTree = null;
    }
}
