package config;

import configgen.genjava.*;

public class ConfigCodeSchema {

    public static SchemaInterface getCodeSchema() {
        SchemaInterface schema = new SchemaInterface();
        schema.addImp("behaviour.BBParam", behaviour_BBParam());
        schema.addImp("behaviour.CondWeightNode", behaviour_CondWeightNode());
        schema.addImp("behaviour.Condition", behaviour_Condition());
        schema.addImp("behaviour.ExpNumber", behaviour_ExpNumber());
        schema.addImp("behaviour.ExpObj", behaviour_ExpObj());
        schema.addImp("behaviour.ExpStr", behaviour_ExpStr());
        schema.addImp("behaviour.ExpVector3", behaviour_ExpVector3());
        schema.addImp("behaviour.IntSwitchNode", behaviour_IntSwitchNode());
        schema.addImp("behaviour.Node", behaviour_Node());
        schema.addImp("behaviour.ParamWeightNode", behaviour_ParamWeightNode());
        schema.addImp("behaviour.WeightNode", behaviour_WeightNode());
        schema.addImp("behaviour.behaviour", behaviour_behaviour());
        schema.addImp("behaviour.calculatorenum", behaviour_calculatorenum());
        schema.addImp("behaviour.compareenum", behaviour_compareenum());
        schema.addImp("behaviour.conditionenum", behaviour_conditionenum());
        schema.addImp("behaviour.expnumberenum", behaviour_expnumberenum());
        schema.addImp("behaviour.expobjenum", behaviour_expobjenum());
        schema.addImp("behaviour.expstrenum", behaviour_expstrenum());
        schema.addImp("behaviour.expvector3enum", behaviour_expvector3enum());
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

    static Schema behaviour_CondWeightNode() {
        SchemaBean s2 = new SchemaBean(false);
        s2.addColumn("cond", new SchemaRef("behaviour.Condition"));
        s2.addColumn("weight", SchemaPrimitive.SInt);
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
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
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("IsEntityDestroyed", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("abilityId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("interactionId", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("checkIsInInteraction", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpNumber() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SInt);
            s2.addImp("ConstInteger", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SFloat);
            s2.addImp("ConstFloat", s3);
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
            s3.addColumn("min", SchemaPrimitive.SInt);
            s3.addColumn("max", SchemaPrimitive.SInt);
            s2.addImp("RandInteger", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("calculator", SchemaPrimitive.SInt);
            s2.addImp("Calculate", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("Max", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("Min", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("GetInstanceId", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("GetCurrentTimeMills", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("left", new SchemaRef("behaviour.ExpVector3"));
            s3.addColumn("right", new SchemaRef("behaviour.ExpVector3"));
            s2.addImp("CalcDist", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpObj() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("GetEntitySelf", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s2.addImp("ObjNull", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("GetBBEntity", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("instId", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("GetEntityByInstId", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("GetBBObj", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("value", SchemaPrimitive.SBool);
            s2.addImp("ConstBoolean", s3);
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
            s2.addImp("GetBBStr", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("num", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ToStringForNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("num", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ToIntStringForNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("num", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ToLongStringForNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("num", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ToFloatStringForNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("num", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ToDoubleStringForNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("v3", new SchemaRef("behaviour.ExpVector3"));
            s2.addImp("ToStringForVector3", s3);
        }
        return s2;
    }

    static Schema behaviour_ExpVector3() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("x", SchemaPrimitive.SFloat);
            s3.addColumn("y", SchemaPrimitive.SFloat);
            s3.addColumn("z", SchemaPrimitive.SFloat);
            s2.addImp("Const", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s2.addImp("GetBBVector3", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("GetPos", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("GetPosListenMove", s3);
        }
        return s2;
    }

    static Schema behaviour_IntSwitchNode() {
        SchemaBean s2 = new SchemaBean(false);
        s2.addColumn("caseInt", SchemaPrimitive.SInt);
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
        s2.addColumn("note", SchemaPrimitive.SStr);
        return s2;
    }

    static Schema behaviour_Node() {
        SchemaInterface s2 = new SchemaInterface();
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("timeMs", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ActWaitTimeMs", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("minTimeMs", SchemaPrimitive.SInt);
            s3.addColumn("maxTimeMs", SchemaPrimitive.SInt);
            s2.addImp("ActWaitRandomTimeMs", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecSuccess", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecFailure", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecNot", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("msg", SchemaPrimitive.SStr);
            s2.addImp("DecLog", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("loopNum", SchemaPrimitive.SInt);
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecLoop", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecLoopUtil", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("validOnFinish", SchemaPrimitive.SBool);
            s3.addColumn("timeMs", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecTimed", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("DecCondition", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompSequence", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompSelector", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("oneSuccAllSucc", SchemaPrimitive.SBool);
            s3.addColumn("oneFailAllFail", SchemaPrimitive.SBool);
            s3.addColumn("nodes", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompParallel", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("caseValue", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("defaultCase", new SchemaRef("behaviour.Node"));
            s3.addColumn("cases", new SchemaList(new SchemaRef("behaviour.IntSwitchNode")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompIntSwitchCase", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("weightNodes", new SchemaList(new SchemaRef("behaviour.WeightNode")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompWeightSelector", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("weightNodes", new SchemaList(new SchemaRef("behaviour.CondWeightNode")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompCondWeightSelector", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("precond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("weightNodes", new SchemaList(new SchemaRef("behaviour.ParamWeightNode")));
            s3.addColumn("enterActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("cleanActions", new SchemaList(new SchemaRef("behaviour.Node")));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("CompParamWeightSelector", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("node", new SchemaRef("behaviour.Node"));
            s2.addImp("CompRepeatUtil", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("subTreeId", SchemaPrimitive.SInt);
            s3.addColumn("paramBBKeys", new SchemaList(SchemaPrimitive.SStr));
            s3.addColumn("returnValueBBKey", SchemaPrimitive.SStr);
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("SubTree", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("msg", new SchemaRef("behaviour.ExpStr"));
            s3.addColumn("params", new SchemaList(new SchemaRef("behaviour.ExpStr")));
            s2.addImp("ActDebugMsg", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("cond", new SchemaRef("behaviour.Condition"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActCondition", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("status", SchemaPrimitive.SInt);
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActReturnStatus", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActSetBBNumber", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActSetBBNumberIfNull", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActSetBBObj", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpStr"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActSetBBStr", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("bbKey", SchemaPrimitive.SStr);
            s3.addColumn("value", new SchemaRef("behaviour.ExpVector3"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActSetBBVector3", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("pos", new SchemaRef("behaviour.ExpVector3"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActWalkToPos", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("pos", new SchemaRef("behaviour.ExpVector3"));
            s3.addColumn("dir", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActWalkToPosWithDir", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("ActMoveStop", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("dir", new SchemaRef("behaviour.ExpNumber"));
            s2.addImp("ActChangeDir", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s2.addImp("ActFaceToEntity", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("abilityId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("interactionId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("dataInteractionId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActInteractionEnter", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("entity", new SchemaRef("behaviour.ExpObj"));
            s3.addColumn("abilityId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("interactionId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("dataInteractionId", new SchemaRef("behaviour.ExpNumber"));
            s3.addColumn("note", SchemaPrimitive.SStr);
            s2.addImp("ActInteractionExit", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("objType", SchemaPrimitive.SInt);
            s3.addColumn("cfgIdList", new SchemaList(SchemaPrimitive.SInt));
            s3.addColumn("radius", SchemaPrimitive.SFloat);
            s3.addColumn("wbbKeyEntity", SchemaPrimitive.SStr);
            s2.addImp("ActFindNonePlayerEntity", s3);
        }
        {
            SchemaBean s3 = new SchemaBean(false);
            s3.addColumn("radius", SchemaPrimitive.SFloat);
            s3.addColumn("wbbKey", SchemaPrimitive.SStr);
            s2.addImp("ActFindSummonOwner", s3);
        }
        return s2;
    }

    static Schema behaviour_ParamWeightNode() {
        SchemaBean s2 = new SchemaBean(false);
        s2.addColumn("weight", new SchemaRef("behaviour.ExpNumber"));
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
        return s2;
    }

    static Schema behaviour_WeightNode() {
        SchemaBean s2 = new SchemaBean(false);
        s2.addColumn("weight", SchemaPrimitive.SInt);
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
        return s2;
    }

    static Schema behaviour_behaviour() {
        SchemaBean s2 = new SchemaBean(true);
        s2.addColumn("id", SchemaPrimitive.SInt);
        s2.addColumn("name", SchemaPrimitive.SStr);
        s2.addColumn("objType", SchemaPrimitive.SInt);
        s2.addColumn("debug", SchemaPrimitive.SBool);
        s2.addColumn("params", new SchemaList(new SchemaRef("behaviour.BBParam")));
        s2.addColumn("returnValue", new SchemaRef("behaviour.BBParam"));
        s2.addColumn("node", new SchemaRef("behaviour.Node"));
        s2.addColumn("note", SchemaPrimitive.SStr);
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
        s2.addValue("IsEntityDestroyed", 10);
        s2.addValue("checkIsInInteraction", 11);
        return s2;
    }

    static Schema behaviour_expnumberenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("ConstFloat", 1);
        s2.addValue("getBBNumber", 2);
        s2.addValue("ConstLong", 3);
        s2.addValue("ConstInteger", 4);
        s2.addValue("Calculate", 5);
        s2.addValue("RandInteger", 6);
        s2.addValue("Max", 7);
        s2.addValue("Min", 8);
        s2.addValue("GetInstanceId", 9);
        s2.addValue("GetCurrentTimeMills", 10);
        s2.addValue("CalcDist", 11);
        return s2;
    }

    static Schema behaviour_expobjenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("GetBBObj", 1);
        s2.addValue("GetEntitySelf", 2);
        s2.addValue("ObjNull", 3);
        s2.addValue("GetBBEntity", 4);
        s2.addValue("GetEntityByInstId", 5);
        s2.addValue("ConstBoolean", 6);
        return s2;
    }

    static Schema behaviour_expstrenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Const", 1);
        s2.addValue("GetBBStr", 2);
        s2.addValue("ToStringForNumber", 3);
        s2.addValue("ToIntStringForNumber", 4);
        s2.addValue("ToLongStringForNumber", 5);
        s2.addValue("ToFloatStringForNumber", 6);
        s2.addValue("ToDoubleStringForNumber", 7);
        s2.addValue("ToStringForVector3", 8);
        return s2;
    }

    static Schema behaviour_expvector3enum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("Const", 1);
        s2.addValue("GetBBVector3", 2);
        s2.addValue("GetPos", 3);
        s2.addValue("GetPosListenMove", 4);
        return s2;
    }

    static Schema behaviour_nodeenum() {
        SchemaEnum s2 = new SchemaEnum(false, true);
        s2.addValue("SubTree", 1);
        s2.addValue("DecSuccess", 2);
        s2.addValue("DecFailure", 3);
        s2.addValue("ActReturnStatus", 4);
        s2.addValue("ActWaitTimeMs", 5);
        s2.addValue("DecNot", 6);
        s2.addValue("DecLog", 7);
        s2.addValue("DecLoop", 8);
        s2.addValue("DecLoopUtil", 9);
        s2.addValue("ActCondition", 10);
        s2.addValue("CompSequence", 11);
        s2.addValue("CompSelector", 12);
        s2.addValue("CompParallel", 13);
        s2.addValue("DecTimed", 14);
        s2.addValue("ActDebugMsg", 15);
        s2.addValue("ActSetBBNumber", 16);
        s2.addValue("ActSetBBObj", 17);
        s2.addValue("ActSetBBStr", 18);
        s2.addValue("ActWaitRandomTimeMs", 19);
        s2.addValue("DecCondition", 20);
        s2.addValue("CompIntSwitchCase", 21);
        s2.addValue("CompWeightSelector", 22);
        s2.addValue("CompCondWeightSelector", 23);
        s2.addValue("CompParamWeightSelector", 24);
        s2.addValue("CompRepeatUtil", 25);
        s2.addValue("ActSetBBNumberIfNull", 26);
        s2.addValue("ActSetBBVector3", 27);
        s2.addValue("ActWalkToPos", 28);
        s2.addValue("ActWalkToPosWithDir", 29);
        s2.addValue("ActMoveStop", 30);
        s2.addValue("ActChangeDir", 31);
        s2.addValue("ActFaceToEntity", 32);
        s2.addValue("ActInteractionEnter", 33);
        s2.addValue("ActInteractionExit", 34);
        s2.addValue("ActFindNonePlayerEntity", 35);
        s2.addValue("ActFindSummonOwner", 36);
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
