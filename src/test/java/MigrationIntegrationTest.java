import behaviour.*;
import behaviour.task.*;
import config.behaviour.*;
import config.behaviour.node.*;
import config.behaviour.expnumber.ConstInteger;
import config.behaviour.expnumber.ConstLong;
import event.object.*;
import math.Vector3;
import org.junit.jupiter.api.*;
import testsupport.TreeFixture;
import testsupport.TreeFixture.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MigrationIntegrationTest {
    final Entity self = new Entity(1);
    final Host host = new Host();
    static ActWalkToPos move() { return new ActWalkToPos(new config.behaviour.expvector3.Const(2, 0, 3), ""); }
    static config.behaviour.condition.Condition distanceCondition(String key) {
        return new config.behaviour.condition.Compare(new config.behaviour.expnumber.CalcDist(
                new config.behaviour.expvector3.GetPosListenMove(new config.behaviour.expobj.GetBBEntity(key)),
                new config.behaviour.expvector3.Const(0, 0, 0)), new ConstInteger(10), 2);
    }

    @Test void sharedConfigurationKeepsRuntimeClocksAndBlackboardsIndependent() {
        var tree = new behaviour.BehaviourTree(TreeFixture.config(new ActWaitTimeMs(new ConstInteger(100))));
        var first = tree.createRuntime(self, host);
        var second = tree.createRuntime(new Entity(2), host);
        first.getBlackBoard().putValue("value", 42);
        assertNull(second.getBlackBoard().getValue("value"));
        assertEquals(Statusenum.BTRUNNING, first.update());
        host.now = 50;
        assertEquals(Statusenum.BTRUNNING, second.update());
        host.now = 100;
        assertEquals(Statusenum.BTSUCCESS, first.update());
        assertEquals(Statusenum.BTRUNNING, second.update());
    }

    @Test void longConstantAndBlackboardPreserveAllBits() {
        long exact = 9_007_199_254_740_993L;
        var task = TreeFixture.tree(new ActSetBBNumber("number", new ConstLong(exact), ""), self, host);
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertEquals(exact, task.getBlackBoard().getLongValue("number"));
    }

    @Test void movementCompletesAndReentryStartsANewOperation() {
        var task = TreeFixture.tree(move(), self, host);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertSame(self, host.movedEntity);
        assertEquals(2, host.destination.x);
        host.operations.getFirst().status = Statusenum.BTSUCCESS;
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertEquals(0, host.operations.getFirst().cancelCalls);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(2, host.operations.size());
        task.interrupt(); task.interrupt();
        assertEquals(1, host.operations.getLast().cancelCalls);
    }

    @Test void rejectedMovementFailsWithoutStartingAnOperation() {
        host.reject = true;
        assertEquals(Statusenum.BTFAILURE, TreeFixture.tree(move(), self, host).update());
        assertTrue(host.operations.isEmpty());
    }

    @Test void runningResetFalseCancelsParallelBranchesInsideDecoratorAndPreservesBlackboard() {
        var parallel = new CompParallel(false, true, List.of(move(), move()), List.of(), List.of(), "");
        var task = TreeFixture.tree(new DecSuccess(true, parallel, ""), self, host);
        task.getBlackBoard().putValue("keep", 7);
        assertEquals(Statusenum.BTRUNNING, task.update());
        task.reset(false);
        assertEquals(List.of(1, 1), host.operations.stream().map(op -> op.cancelCalls).toList());
        assertEquals(7, task.getBlackBoard().getIntValue("keep"));
        task.reset(false);
        assertEquals(List.of(1, 1), host.operations.stream().map(op -> op.cancelCalls).toList());
    }

    @Test void dirtyTargetRebindsMoveListenersBeforeContinuing() {
        Entity a = new Entity(2), b = new Entity(3);
        a.position = new Vector3(1, 0, 0); b.position = new Vector3(2, 0, 0);
        var task = TreeFixture.tree(new DecCondition(distanceCondition("target"), move(), ""), self, host);
        task.getBlackBoard().putValue("target", a);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(1, a.listenerCount());
        task.getBlackBoard().putValue("target", b);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(0, a.listenerCount());
        assertEquals(1, b.listenerCount());
        b.position = new Vector3(20, 0, 0); b.triggerEvent(new EventMove(b));
        assertEquals(Statusenum.BTFAILURE, task.update());
        assertEquals(1, host.operations.getFirst().cancelCalls);
        assertEquals(0, b.listenerCount());
    }

    @Test void shortCircuitBranchActivationSubscribesNewDependencies() {
        Entity b = new Entity(3); b.position = new Vector3(2, 0, 0);
        var flag = new config.behaviour.condition.Compare(new config.behaviour.expnumber.GetBBNumber("flag"), new ConstInteger(1), 1);
        var guard = new config.behaviour.condition.Or(flag, distanceCondition("target"));
        var task = TreeFixture.tree(new DecCondition(guard, move(), ""), self, host);
        task.getBlackBoard().putValue("target", b); task.getBlackBoard().putValue("flag", 1);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(0, b.listenerCount());
        task.getBlackBoard().putValue("flag", 0);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(1, b.listenerCount());
        task.getBlackBoard().putValue("flag", 1);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertEquals(0, b.listenerCount());
        task.interrupt();
        assertEquals(1, host.operations.getFirst().cancelCalls);
    }

    @Test void ownerLookupRejectsMissingDestroyedAndBoundaryTargetsAndClearsStaleValue() {
        Entity owner = new Entity(2); host.entities.put(2L, owner); self.ownerId = 2;
        var task = TreeFixture.tree(new ActFindSummonOwner(5, "owner"), self, host);
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertSame(owner, task.getBlackBoard().getValue("owner"));
        owner.position = new Vector3(5, 0, 0);
        assertEquals(Statusenum.BTFAILURE, task.update()); assertNull(task.getBlackBoard().getValue("owner"));
        owner.position = new Vector3(1, 0, 0); owner.destroyed = true;
        assertEquals(Statusenum.BTFAILURE, task.update());
        self.ownerId = 0; task.getBlackBoard().putValue("owner", owner);
        assertEquals(Statusenum.BTFAILURE, task.update()); assertNull(task.getBlackBoard().getValue("owner"));
    }

    @Test void entityQueryPreservesFilterParametersAndCooldownFromClockZero() {
        Entity candidate = new Entity(2); candidate.position = new Vector3(1, 0, 0);
        host.candidates = List.of(candidate);
        var task = TreeFixture.tree(new ActFindNonePlayerEntity(9, List.of(11, 12), 5, "found"), self, host);
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertEquals(9, host.objectType); assertEquals(List.of(11, 12), host.configIds);
        assertSame(candidate, task.getBlackBoard().getValue("found"));
        host.now = 1000;
        assertEquals(Statusenum.BTFAILURE, task.update()); assertNull(task.getBlackBoard().getValue("found"));
        host.now = 2000;
        assertEquals(Statusenum.BTSUCCESS, task.update());
    }

    @Test void interactionPassesIdsAndCancelsOnTargetDestruction() {
        Entity target = new Entity(2);
        var node = new ActInteractionEnter(new config.behaviour.expobj.GetBBEntity("target"), new ConstInteger(4), new ConstInteger(5), new ConstLong(6), "");
        var task = TreeFixture.tree(node, self, host); task.getBlackBoard().putValue("target", target);
        assertEquals(Statusenum.BTRUNNING, task.update());
        assertSame(target, host.interactionTarget); assertEquals(4, host.abilityId); assertEquals(5, host.interactionId);
        assertEquals(6, host.dataInteractionId); assertTrue(host.enter);
        target.destroyed = true;
        assertEquals(Statusenum.BTFAILURE, task.update()); assertEquals(1, host.operations.getFirst().cancelCalls);
    }

    @Test void interactionStateEventInvalidatesGuardAndReleasesAllListeners() {
        Entity target = new Entity(2); host.interacting = true;
        var guard = new config.behaviour.condition.CheckIsInInteraction(new config.behaviour.expobj.GetBBEntity("target"), new ConstInteger(1), new ConstInteger(2));
        var task = TreeFixture.tree(new DecCondition(guard, move(), ""), self, host);
        task.getBlackBoard().putValue("target", target);
        assertEquals(Statusenum.BTRUNNING, task.update());
        host.interacting = false; self.triggerEvent(new EventInteractionChanged(self));
        assertEquals(Statusenum.BTFAILURE, task.update());
        assertEquals(0, self.listenerCount()); assertEquals(0, target.listenerCount());
        assertEquals(1, host.operations.getFirst().cancelCalls);
    }

    @Test void missingOrDestroyedTargetFailsGuardWithoutLeakingDependencies() {
        Entity target = new Entity(2); target.position = new Vector3(1, 0, 0);
        var exists = new config.behaviour.condition.Not(new config.behaviour.condition.IsEntityDestroyed(
                new config.behaviour.expobj.GetBBEntity("target")));
        var guard = new config.behaviour.condition.And(exists, distanceCondition("target"));
        var task = TreeFixture.tree(new DecCondition(guard, move(), ""), self, host);
        assertEquals(Statusenum.BTFAILURE, task.update());
        assertTrue(host.operations.isEmpty());
        task.getBlackBoard().putValue("target", target);
        assertEquals(Statusenum.BTRUNNING, task.update());
        target.destroyed = true; target.triggerEvent(new EventObjectLeaveMap(target));
        assertEquals(Statusenum.BTFAILURE, task.update());
        assertEquals(0, target.listenerCount());
        assertEquals(1, host.operations.getFirst().cancelCalls);
    }

    @Test void vectorStringExpressionKeepsCoordinateValues() {
        var expression = new config.behaviour.expstr.ToStringForVector3(new config.behaviour.expvector3.Const(1, 2, 3));
        var task = TreeFixture.tree(new ActSetBBStr("position", expression, ""), self, host);
        assertEquals(Statusenum.BTSUCCESS, task.update());
        assertEquals("v3:{1.0,2.0,3.0}", task.getBlackBoard().getValue("position"));
    }
}
