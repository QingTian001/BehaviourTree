package config.behaviour.node;

public sealed interface Node permits ActWaitTimeMs, ActWaitRandomTimeMs, DecSuccess, DecFailure, DecNot, DecLog, DecLoop, DecLoopUtil, DecTimed, DecCondition, CompSequence, CompSelector, CompParallel, CompIntSwitchCase, CompWeightSelector, CompCondWeightSelector, CompParamWeightSelector, CompRepeatUtil, SubTree, ActDebugMsg, ActCondition, ActReturnStatus, ActSetBBNumber, ActSetBBNumberIfNull, ActSetBBObj, ActSetBBStr, ActSetBBVector3, ActWalkToPos, ActWalkToPosWithDir, ActMoveStop, ActChangeDir, ActFaceToEntity, ActInteractionEnter, ActInteractionExit, ActFindNonePlayerEntity, ActFindSummonOwner {
    config.behaviour.Nodeenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static Node _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "ActWaitTimeMs":
                return config.behaviour.node.ActWaitTimeMs._create(input);
            case "ActWaitRandomTimeMs":
                return config.behaviour.node.ActWaitRandomTimeMs._create(input);
            case "DecSuccess":
                return config.behaviour.node.DecSuccess._create(input);
            case "DecFailure":
                return config.behaviour.node.DecFailure._create(input);
            case "DecNot":
                return config.behaviour.node.DecNot._create(input);
            case "DecLog":
                return config.behaviour.node.DecLog._create(input);
            case "DecLoop":
                return config.behaviour.node.DecLoop._create(input);
            case "DecLoopUtil":
                return config.behaviour.node.DecLoopUtil._create(input);
            case "DecTimed":
                return config.behaviour.node.DecTimed._create(input);
            case "DecCondition":
                return config.behaviour.node.DecCondition._create(input);
            case "CompSequence":
                return config.behaviour.node.CompSequence._create(input);
            case "CompSelector":
                return config.behaviour.node.CompSelector._create(input);
            case "CompParallel":
                return config.behaviour.node.CompParallel._create(input);
            case "CompIntSwitchCase":
                return config.behaviour.node.CompIntSwitchCase._create(input);
            case "CompWeightSelector":
                return config.behaviour.node.CompWeightSelector._create(input);
            case "CompCondWeightSelector":
                return config.behaviour.node.CompCondWeightSelector._create(input);
            case "CompParamWeightSelector":
                return config.behaviour.node.CompParamWeightSelector._create(input);
            case "CompRepeatUtil":
                return config.behaviour.node.CompRepeatUtil._create(input);
            case "SubTree":
                return config.behaviour.node.SubTree._create(input);
            case "ActDebugMsg":
                return config.behaviour.node.ActDebugMsg._create(input);
            case "ActCondition":
                return config.behaviour.node.ActCondition._create(input);
            case "ActReturnStatus":
                return config.behaviour.node.ActReturnStatus._create(input);
            case "ActSetBBNumber":
                return config.behaviour.node.ActSetBBNumber._create(input);
            case "ActSetBBNumberIfNull":
                return config.behaviour.node.ActSetBBNumberIfNull._create(input);
            case "ActSetBBObj":
                return config.behaviour.node.ActSetBBObj._create(input);
            case "ActSetBBStr":
                return config.behaviour.node.ActSetBBStr._create(input);
            case "ActSetBBVector3":
                return config.behaviour.node.ActSetBBVector3._create(input);
            case "ActWalkToPos":
                return config.behaviour.node.ActWalkToPos._create(input);
            case "ActWalkToPosWithDir":
                return config.behaviour.node.ActWalkToPosWithDir._create(input);
            case "ActMoveStop":
                return config.behaviour.node.ActMoveStop._create(input);
            case "ActChangeDir":
                return config.behaviour.node.ActChangeDir._create(input);
            case "ActFaceToEntity":
                return config.behaviour.node.ActFaceToEntity._create(input);
            case "ActInteractionEnter":
                return config.behaviour.node.ActInteractionEnter._create(input);
            case "ActInteractionExit":
                return config.behaviour.node.ActInteractionExit._create(input);
            case "ActFindNonePlayerEntity":
                return config.behaviour.node.ActFindNonePlayerEntity._create(input);
            case "ActFindSummonOwner":
                return config.behaviour.node.ActFindSummonOwner._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
