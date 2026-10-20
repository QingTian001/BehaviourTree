package behaviour;

import behaviour.task.BehaviourTreeTask;

/** 每个树运行时独立的实体、黑板与宿主上下文。 */
public class BehaviourContext {
    private final GEntity self;
    private final BlackBoard blackBoard;
    private final BehaviourTreeTask behaviourTreeTask;
    private final BehaviourHost host;
    public BehaviourContext(GEntity self, BlackBoard blackBoard, BehaviourTreeTask task, BehaviourHost host) {
        this.self = self;
        this.blackBoard = blackBoard;
        this.behaviourTreeTask = task;
        this.host = host;
    }
    public GEntity getSelf() { return self; }
    public BlackBoard getBlackBoard() { return blackBoard; }
    public BehaviourTreeTask getBehaviourTreeTask() { return behaviourTreeTask; }
    public BehaviourHost getHost() { return host; }
}
