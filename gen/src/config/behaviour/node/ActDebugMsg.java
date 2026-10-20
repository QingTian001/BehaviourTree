package config.behaviour.node;

// ActDebugMsg
public final class ActDebugMsg implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTDEBUGMSG;
    }

    private config.behaviour.expstr.ExpStr msg;
    private java.util.List<config.behaviour.expstr.ExpStr> params;

    private ActDebugMsg() {
    }

    public ActDebugMsg(config.behaviour.expstr.ExpStr msg, java.util.List<config.behaviour.expstr.ExpStr> params) {
        this.msg = msg;
        this.params = params;
    }

    public static ActDebugMsg _create(configgen.genjava.ConfigInput input) {
        ActDebugMsg self = new ActDebugMsg();
        self.msg = config.behaviour.expstr.ExpStr._create(input);
        {
            int c = input.readInt();
            if (c == 0) {
                self.params = java.util.Collections.emptyList();
            } else {
                self.params = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.params.add(config.behaviour.expstr.ExpStr._create(input));
                }
            }
        }
        return self;
    }

    public config.behaviour.expstr.ExpStr getMsg() {
        return msg;
    }

    public java.util.List<config.behaviour.expstr.ExpStr> getParams() {
        return params;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(msg, params);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActDebugMsg))
            return false;
        ActDebugMsg o = (ActDebugMsg) other;
        return msg.equals(o.msg) && params.equals(o.params);
    }

    @Override
    public String toString() {
        return "ActDebugMsg(" + msg + "," + params + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        msg._resolve(mgr);
        for (config.behaviour.expstr.ExpStr e : params) {
            e._resolve(mgr);
        }
    }

}
