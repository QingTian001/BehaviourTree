package behaviour.node.action;

import config.behaviour.expstr.ExpStr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyao on 2024/5/15 19:57
 */
public class ActDebugMsg extends Action<config.behaviour.node.ActDebugMsg> {
    private ExpressionString<? extends ExpStr> msgExpression;
    private final List<ExpressionString<? extends ExpStr>> paramsList = new ArrayList<>();

    public static final Logger logger = LogManager.getLogger(ActDebugMsg.class);

    public ActDebugMsg(config.behaviour.node.ActDebugMsg atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        msgExpression = BehaviourFactory.createExpression(getAtomCfg().getMsg());
        for (ExpStr param : getAtomCfg().getParams()) {
            paramsList.add(BehaviourFactory.createExpression(param));
        }
    }

    public ExpressionString<? extends ExpStr> getMsgExpression() {
        return msgExpression;
    }

    public List<ExpressionString<? extends ExpStr>> getParamsList() {
        return paramsList;
    }
}
