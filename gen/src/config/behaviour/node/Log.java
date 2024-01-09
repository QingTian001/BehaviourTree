package config.behaviour.node;

public class Log implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.LOG;
    }

    private boolean validOnFinish;
    private config.behaviour.Node node;
    private String msg;

    private Log() {
    }

    public Log(boolean validOnFinish, config.behaviour.Node node, String msg) {
        this.validOnFinish = validOnFinish;
        this.node = node;
        this.msg = msg;
    }

    public static Log _create(configgen.genjava.ConfigInput input) {
        Log self = new Log();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.Node._create(input);
        self.msg = input.readStr();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.Node getNode() {
        return node;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node, msg);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Log))
            return false;
        Log o = (Log) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node) && msg.equals(o.msg);
    }

    @Override
    public String toString() {
        return "Log(" + validOnFinish + "," + node + "," + msg + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
