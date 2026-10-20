package behaviour.node.decorator;

/**
 * 根据被装饰节点状态进行监督或状态变换的装饰节点基类。
 */
public abstract class StatusDecorator<T extends config.behaviour.node.Node> extends Decorator<T> {

    protected StatusDecorator(T atomCfg) {
        super(atomCfg);
    }

    public abstract boolean isApplyOnFinishOnly();
}
