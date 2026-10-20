import behaviour.Atom;
import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.NodePath;
import behaviour.task.BehaviourTreeTask;
import config.behaviour.Behaviour;
import config.behaviour.Statusenum;
import config.behaviour.node.ActReturnStatus;
import org.junit.jupiter.api.*;
import testsupport.TreeFixture;
import testsupport.TreeFixture.*;
import java.lang.reflect.Constructor;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationExamplesTest {
    @BeforeAll static void load() { BehaviourManager.start(); }

    @Test void everySelectedConfigurationBindsItsExecutorAndCreatesItsRuntime() throws Exception {
        var tree = TreeFixture.tree(new ActReturnStatus(1, ""), new Entity(1), new Host());
        List<Class<?>> families = List.of(config.behaviour.node.Node.class, config.behaviour.condition.Condition.class,
                config.behaviour.expnumber.ExpNumber.class, config.behaviour.expstr.ExpStr.class,
                config.behaviour.expobj.ExpObj.class, config.behaviour.expvector3.ExpVector3.class);
        int count = 0;
        for (Class<?> family : families) {
            for (Class<?> type : family.getPermittedSubclasses()) {
                Object cfg = fixture(type);
                Atom<?> atom;
                if (cfg instanceof config.behaviour.node.Node node) { atom = BehaviourFactory.createNode(node); }
                else if (cfg instanceof config.behaviour.condition.Condition condition) { atom = BehaviourFactory.createCondition(condition); }
                else { atom = BehaviourFactory.createExpression(cfg); }
                NodePath.assignTree(atom);
                var runtime = atom.createRuntime(null, tree.getContext());
                assertNotNull(runtime, type.getName());
                runtime.reset(true);
                count++;
            }
        }
        assertEquals(76, count);
    }

    @Test void allSevenLegacyExamplesKeepTheirResultsIncludingRecursiveSubtreeReturnValue() {
        var self = new Entity(1); var host = new Host();
        for (int id = 1; id <= 7; id++) {
            List<Object> params = id == 7 ? List.of(6) : List.of();
            BehaviourTreeTask task = BehaviourFactory.createBehaviourTreeTask(self, host, Behaviour.get(id), params);
            if (id == 5) { task.getBlackBoard().putValue("abc", 0); }
            if (id == 6) { task.getBlackBoard().putValue("n", 0); }
            Statusenum status = run(task, host);
            assertEquals(id == 5 ? Statusenum.BTFAILURE : Statusenum.BTSUCCESS, status, "旧示例 " + id);
            if (id == 7) { assertEquals(13, task.getBlackBoard().getIntValue("ret")); }
        }
    }

    @Test void generatedFloatAndOwnerMoveInteractionExampleRunAgainstHost() {
        var host = new Host(); var self = new Entity(1); var owner = new Entity(2);
        self.ownerId = 2; host.entities.put(2L, owner);
        owner.position = new math.Vector3(2, 0, 3);
        var task = BehaviourFactory.createBehaviourTreeTask(self, host, Behaviour.get(8), List.of());
        Statusenum status = task.update();
        assertEquals(Statusenum.BTRUNNING, status);
        assertEquals(2, host.destination.x);
        host.operations.getFirst().status = Statusenum.BTSUCCESS;
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertSame(owner, host.interactionTarget);
        host.operations.getLast().status = Statusenum.BTSUCCESS;
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertTrue(host.operations.stream().allMatch(op -> op.cancelCalls == 0));
    }

    @Test void resettingDecoratedActiveSubtreeCancelsItsOperationAndAllowsFreshReentry() {
        var host = new Host(); var self = new Entity(1); var owner = new Entity(2);
        self.ownerId = 2; host.entities.put(2L, owner);
        var child = new config.behaviour.node.SubTree(8, List.of(), "", "");
        child._resolve(config.ConfigMgr.getMgr());
        var task = TreeFixture.tree(new config.behaviour.node.DecSuccess(true, child, ""), self, host);
        assertEquals(Statusenum.BTRUNNING, task.update());
        long oldInstance = task.getSubBehaviourTreeTasks().iterator().next().getInstanceId();
        task.reset(false);
        assertEquals(1, host.operations.getFirst().cancelCalls);
        assertTrue(task.getSubBehaviourTreeTasks().isEmpty());
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertNotEquals(oldInstance, task.getSubBehaviourTreeTasks().iterator().next().getInstanceId());
        task.reset(true);
        assertEquals(List.of(1, 1), host.operations.stream().map(operation -> operation.cancelCalls).toList());
        assertTrue(task.getSubBehaviourTreeTasks().isEmpty());
    }

    private static Statusenum run(BehaviourTreeTask task, Host host) {
        for (int tick = 0; tick < 100; tick++) {
            Statusenum status = task.update();
            if (BehaviourHelper.isNodeFinished(status)) { return status; }
            host.now += 50;
        }
        fail("示例未在逻辑时间内结束"); return null;
    }

    private static Object fixture(Class<?> type) throws Exception {
        Constructor<?> constructor = type.getConstructors()[0];
        Class<?>[] params = constructor.getParameterTypes();
        Object[] values = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Class<?> param = params[i];
            if (param == int.class) { values[i] = 1; }
            else if (param == long.class) { values[i] = 1L; }
            else if (param == float.class) { values[i] = 1.5f; }
            else if (param == boolean.class) { values[i] = false; }
            else if (param == String.class) { values[i] = "key"; }
            else if (param == List.class) { values[i] = List.of(); }
            else if (param == config.behaviour.node.Node.class) { values[i] = new ActReturnStatus(1, ""); }
            else if (param == config.behaviour.condition.Condition.class) { values[i] = new config.behaviour.condition.Success(); }
            else if (param == config.behaviour.expnumber.ExpNumber.class) { values[i] = new config.behaviour.expnumber.ConstInteger(1); }
            else if (param == config.behaviour.expstr.ExpStr.class) { values[i] = new config.behaviour.expstr.Const("value"); }
            else if (param == config.behaviour.expobj.ExpObj.class) { values[i] = new config.behaviour.expobj.GetEntitySelf(); }
            else if (param == config.behaviour.expvector3.ExpVector3.class) { values[i] = new config.behaviour.expvector3.Const(0, 0, 0); }
            else { throw new AssertionError("测试缺少配置参数: " + param.getName()); }
        }
        return constructor.newInstance(values);
    }
}
