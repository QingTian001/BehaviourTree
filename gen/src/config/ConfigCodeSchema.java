package config;

import configgen.genjava.*;

public class ConfigCodeSchema {

    public static Schema getCodeSchema() {
        SchemaInterface schema = new SchemaInterface();
        schema.addImp("behaviour.BBParam", behaviour_BBParam());
        schema.addImp("behaviour.Condition", behaviour_Condition());
        schema.addImp("behaviour.ExpNumber", behaviour_ExpNumber());
        schema.addImp("behaviour.ExpObj", behaviour_ExpObj());
        schema.addImp("behaviour.ExpStr", behaviour_ExpStr());
        schema.addImp("behaviour.Node", behaviour_Node());
        schema.addImp("behaviour.behaviour", behaviour_behaviour());
        schema.addImp("behaviour.calculatorenum", behaviour_calculatorenum());
        schema.addImp("behaviour.compareenum", behaviour_compareenum());
        schema.addImp("behaviour.conditionenum", behaviour_conditionenum());
        schema.addImp("behaviour.expnumberenum", behaviour_expnumberenum());
        schema.addImp("behaviour.expobjenum", behaviour_expobjenum());
        schema.addImp("behaviour.expstrenum", behaviour_expstrenum());
        schema.addImp("behaviour.nodeenum", behaviour_nodeenum());
        schema.addImp("behaviour.nodeenum_Detail", behaviour_nodeenum_Detail());
        schema.addImp("behaviour.nodetypeenum", behaviour_nodetypeenum());
        schema.addImp("behaviour.statusenum", behaviour_statusenum());
        schema.addImp("behaviour.typeenum", behaviour_typeenum());
        return schema;
    }

    static Schema behaviour_BBParam() {
        SchemaBean s2 = new SchemaBean(false);
        s2.addColumn("bbType", SchemaPrimitive.SInt);
        s2.addColumn("bbKey", SchemaPrimitive.SStr);
        return s2;
    }

    static Schema behaviour_Condition() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("NULL", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("Success", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("Failure", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("comarator", SchemaPrimitive.SInt);
            s2.addImp("Compare", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpStr"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpStr"));
            s2.addImp("StrEqual", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("ObjEqual", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s2.addImp("Not", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond1", new SchemaRef("behaviour.Condition"));
            s3.addColumn("cond2", new SchemaRef("behaviour.Condition"));
            s2.addImp("And", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond1", new SchemaRef("behaviour.Condition"));
            s3.addColumn("cond2", new SchemaRef("behaviour.Condition"));
            s2.addImp("Or", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpNumber() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SFloat);
            s2.addImp("ConstFloat", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SInt);
            s2.addImp("ConstInteger", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SLong);
            s2.addImp("ConstLong", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("getBBNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("calculator", SchemaPrimitive.SInt);
            s2.addImp("Calculate", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpObj() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("getBBObj", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpStr() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SStr);
            s2.addImp("Const", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("getBBStr", s3);
        }
        return s2;
    }

    static Schema behaviour_Node() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("subTreeId", SchemaPrimitive.SInt);
            s3.addColumn("paramBBKeys", new SchemaList(SchemaPrimitive.SStr));
            s3.addColumn("returnValueBBKey", SchemaPrimitive.SStr);
            s2.addImp("SubTree", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("Success", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("Failure", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("status", SchemaPrimitive.SInt);
            s2.addImp("EndTree", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("timeMs", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("WaitTimeMs", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("NodeNot", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("msg", SchemaPrimitive.SStr);
            s2.addImp("Log", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("loopNum", SchemaPrimitive.SInt);
            s2.addImp("Loop", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("LoopUtil", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("timeMs", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("Timed", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s2.addImp("NodeCondition", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s2.addImp("Sequence", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s2.addImp("Selector", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("oneSuccAllSucc", SchemaPrimitive.SBool);
            s3.addColumn("oneFailAllFail", SchemaPrimitive.SBool);
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s2.addImp("Parallel", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("msg", SchemaPrimitive.SStr);
            s2.addImp("ActDebugLog", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ActSetBBNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("ActSetBBObj", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpStr"));
            s2.addImp("ActSetBBStr", s3);
        }
        return s2;
    }

    static Schema behaviour_behaviour() {
        SchemaBean s2 = new SchemaBean(true);
        s2.addColumn("id", SchemaPrimitive.SInt);
        s2.addColumn("name", SchemaPrimitive.SStr);
        s2.addColumn("objType", SchemaPrimitive.SInt);
        s2.addColumn("params", new SchemaList(new SchemaRef("behaviour.BBParam")));
        s2.addColumn("returnValue", new SchemaRef("behaviour.BBParam"));
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
        return s2;
    }

    static Schema behaviour_calculatorenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Plus", 1);
        s2.addValue("Sub", 2);
        s2.addValue("Multi", 3);
        s2.addValue("Divide", 4);
        return s2;
    }

    static Schema behaviour_compareenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Equal", 1);
        s2.addValue("Less", 2);
        s2.addValue("Greater", 3);
        s2.addValue("LessEqual", 4);
        s2.addValue("GreaterEqual", 5);
        return s2;
    }

    static Schema behaviour_conditionenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("NULL", 1);
        s2.addValue("StrEqual", 2);
        s2.addValue("ObjEqual", 3);
        s2.addValue("Not", 4);
        s2.addValue("And", 5);
        s2.addValue("Or", 6);
        s2.addValue("Compare", 7);
        s2.addValue("Success", 8);
        s2.addValue("Failure", 9);
        return s2;
    }

    static Schema behaviour_expnumberenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("ConstFloat", 1);
        s2.addValue("getBBNumber", 2);
        s2.addValue("ConstLong", 3);
        s2.addValue("ConstInteger", 4);
        s2.addValue("Calculate", 5);
        return s2;
    }

    static Schema behaviour_expobjenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("getBBObj", 1);
        return s2;
    }

    static Schema behaviour_expstrenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Const", 1);
        s2.addValue("getBBStr", 2);
        return s2;
    }

    static Schema behaviour_nodeenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("SubTree", 1);
        s2.addValue("Success", 2);
        s2.addValue("Failure", 3);
        s2.addValue("EndTree", 4);
        s2.addValue("WaitTimeMs", 5);
        s2.addValue("NodeNot", 6);
        s2.addValue("Log", 7);
        s2.addValue("Loop", 8);
        s2.addValue("LoopUtil", 9);
        s2.addValue("NodeCondition", 10);
        s2.addValue("Sequence", 11);
        s2.addValue("Selector", 12);
        s2.addValue("Parallel", 13);
        s2.addValue("Timed", 14);
        s2.addValue("ActDebugLog", 15);
        s2.addValue("ActSetBBNumber", 16);
        s2.addValue("ActSetBBObj", 17);
        s2.addValue("ActSetBBStr", 18);
        return s2;
    }

    static Schema behaviour_nodeenum_Detail() {
        SchemaBean s2 = new SchemaBean(true);
        s2.addColumn("id", SchemaPrimitive.SInt);
        s2.addColumn("ename", SchemaPrimitive.SStr);
        s2.addColumn("nodeType", SchemaPrimitive.SInt);
        return s2;
    }

    static Schema behaviour_nodetypeenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Action", 1);
        s2.addValue("Composite", 2);
        s2.addValue("Decorator", 3);
        s2.addValue("Condition", 4);
        return s2;
    }

    static Schema behaviour_statusenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("BTSuccess", 1);
        s2.addValue("BTFailure", 2);
        s2.addValue("BTRunning", 3);
        s2.addValue("BTInvalid", 4);
        return s2;
    }

    static Schema behaviour_typeenum() {
        SchemaBean s2 = new SchemaBean(true);
        s2.addColumn("id", SchemaPrimitive.SInt);
        s2.addColumn("ename", SchemaPrimitive.SStr);
        return s2;
    }

}
