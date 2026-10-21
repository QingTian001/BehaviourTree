import area.AreaCircle;
import behaviour.GEntity;
import config.behaviour.Statusenum;
import config.behaviour.node.ActFindSummonOwner;
import event.object.*;
import math.Vector3;
import state.*;
import summon.NonePlayerSummonManager;
import testsupport.*;
import trigger.*;
import java.util.*;

/** 演示状态中断、召唤 owner、触发器和宿主计时器的联动。 */
public final class SceneManagersExample {
    public static void main(String[] args) {
        var host = new SceneFixture();
        var owner = new TreeFixture.Entity(1);
        host.enterMap(owner);
        host.states.put(1, new StateConfig(1,false,false,List.of(10),List.of(),List.of(),List.of(),List.of(),List.of(50)));
        host.summons.put(1, SceneFixture.summon(1,false,1000,50));
        var states = new StateManager(owner,host);
        var summons = new NonePlayerSummonManager(owner,host);
        var trigger = TriggerManager.create(host,owner,new AreaCircle(owner.getPos(),5),List.of(1));
        List<String> transitions = new ArrayList<>();
        trigger.addTriggerListener(new ITriggerListener() {
            public void triggerIn(GEntity entity) { transitions.add("in"); }
            public void triggerOut(GEntity entity) { transitions.add("out"); }
        });
        trigger.onEnterMap();
        summons.summonAndEnterMap(1, created -> {
            for (GEntity entity : created) {
                host.seeing.add(entity);
                owner.triggerEvent(new EventEnterAoi(owner,entity));
                var findOwner = TreeFixture.tree(new ActFindSummonOwner(10,"owner"),(TreeFixture.Entity)entity,host.behaviour);
                check(findOwner.update() == Statusenum.BTSUCCESS && findOwner.getBlackBoard().getValue("owner") == owner);
            }
        });
        host.advanceTo(300); trigger.tick(300);
        check(transitions.equals(List.of("in","in")));
        check(states.addState(1) && !states.checkCanAction(10));
        owner.triggerEvent(new EventMove(owner));
        check(!states.hasState(1) && states.checkCanAction(10));
        for (GEntity entity : summons.getSummonEntities()) {
            ((TreeFixture.Entity)entity).position = new Vector3(20,0,0);
            entity.triggerEvent(new EventMove(entity));
        }
        host.advanceTo(600); trigger.tick(300);
        check(transitions.equals(List.of("in","in","out","out")));
        var children = List.copyOf(summons.getSummonEntities());
        summons.ownerLeaveMap();
        check(children.stream().allMatch(entity -> entity.getOwnerInstId() == 0));
        host.advanceTo(650);
        check(children.stream().allMatch(GEntity::isDestroyed));
        trigger.onLeaveMap(); states.onLeaveMap();
        check(owner.listenerCount() == 0 && children.stream().allMatch(entity -> ((TreeFixture.Entity)entity).listenerCount() == 0));
        System.out.println("场景示例通过：状态中断、召唤/findOwner、区域进出、主人离场超时和监听释放。");
    }
    private static void check(boolean result) {
        if (!result) { throw new IllegalStateException("场景管理器示例验证失败"); }
    }
}
