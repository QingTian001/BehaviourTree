package config.behaviour.node;

public class ActDebugLog implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTDEBUGLOG;
    }

    private String msg;

    private ActDebugLog() {
    }

    public ActDebugLog(String msg) {
        this.msg = msg;
    }

    public static ActDebugLog _create(configgen.genjava.ConfigInput input) {
        ActDebugLog self = new ActDebugLog();
        self.msg = input.readStr();
        return self;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(msg);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActDebugLog))
            return false;
        ActDebugLog o = (ActDebugLog) other;
        return msg.equals(o.msg);
    }

    @Override
    public String toString() {
        return "ActDebugLog(" + msg + ")";
    }

}
