package config.behaviour;

public interface Node {
    config.behaviour.Nodeenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static Node _create(configgen.genjava.ConfigInput input) {
        switch(input.readStr()) {
            case "SubTree":
                return config.behaviour.node.SubTree._create(input);
            case "Success":
                return config.behaviour.node.Success._create(input);
            case "Failure":
                return config.behaviour.node.Failure._create(input);
            case "EndTree":
                return config.behaviour.node.EndTree._create(input);
            case "WaitTimeMs":
                return config.behaviour.node.WaitTimeMs._create(input);
            case "NodeNot":
                return config.behaviour.node.NodeNot._create(input);
            case "Log":
                return config.behaviour.node.Log._create(input);
            case "Loop":
                return config.behaviour.node.Loop._create(input);
            case "LoopUtil":
                return config.behaviour.node.LoopUtil._create(input);
            case "Timed":
                return config.behaviour.node.Timed._create(input);
            case "NodeCondition":
                return config.behaviour.node.NodeCondition._create(input);
            case "Sequence":
                return config.behaviour.node.Sequence._create(input);
            case "Selector":
                return config.behaviour.node.Selector._create(input);
            case "Parallel":
                return config.behaviour.node.Parallel._create(input);
            case "ActDebugLog":
                return config.behaviour.node.ActDebugLog._create(input);
            case "ActSetBBNumber":
                return config.behaviour.node.ActSetBBNumber._create(input);
            case "ActSetBBObj":
                return config.behaviour.node.ActSetBBObj._create(input);
            case "ActSetBBStr":
                return config.behaviour.node.ActSetBBStr._create(input);
        }
        throw new IllegalArgumentException();
    }
}
