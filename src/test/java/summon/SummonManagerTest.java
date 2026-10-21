package summon;

import area.AreaFan;
import behaviour.GEntity;
import config.behaviour.Statusenum;
import config.behaviour.node.ActFindSummonOwner;
import event.object.EventObjectLeaveMap;
import math.Vector3;
import org.junit.jupiter.api.Test;
import summon.area.*;
import summon.param.NormalSummonEntityParam;
import testsupport.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static testsupport.TreeFixture.Entity;

class SummonManagerTest {
    @Test void skippedCreationDoesNotLeaveEmptyConfigurationIndexes() {
        var host=new SceneFixture(); host.skipCreation=true; var owner=new Entity(1);
        host.summons.put(1,SceneFixture.summon(1,false,0,10)); var manager=new NonePlayerSummonManager(owner,host);
        List<GEntity> callback=new ArrayList<>(); manager.summonAndEnterMap(1,callback::addAll);
        assertTrue(callback.isEmpty()); assertTrue(manager.getSummonCfgId2SummonEntities().isEmpty());
        assertTrue(manager.getSummonCfgId2SummonType().isEmpty()); assertTrue(manager.getSummonEntity2SummonCfgId().isEmpty());
    }
    @Test void ownerLeaveCallbackMayReenterWithoutRepeatingCleanup() {
        var host=new SceneFixture(); var owner=new Entity(1); int[] calls={0};
        var manager=new SummonManager(owner,host) {
            protected void onOwnerLeaveMap() { if (++calls[0]==1) { ownerLeaveMap(); } }
        };
        manager.ownerLeaveMap(); assertEquals(1,calls[0]); assertNull(manager.getSelf());
        assertThrows(IllegalStateException.class,()->manager.addSummonEntity(new Entity(2),new NormalSummonEntityParam()));
    }
    @Test void creationCallbackSeesEnteredOwnedEntitiesAndIndexesAndFindOwnerWorks() {
        SceneFixture host = new SceneFixture(); Entity owner = new Entity(1); host.enterMap(owner);
        host.summons.put(1,SceneFixture.summon(1,false,100,30)); host.behaviour.now=20;
        var manager = new NonePlayerSummonManager(owner,host); var param = new NormalSummonEntityParam();
        param.setOverrideSummonCount(4);
        manager.summonAndEnterMap(1,param,created -> {
            assertEquals(4,created.size());
            for (GEntity entity:created) {
                assertSame(entity,host.behaviour.entities.get(entity.getInstanceId()));
                assertEquals(owner.id,entity.getOwnerInstId());
                assertEquals(120,host.lifeDeadlines.get(entity)); assertTrue(host.publicSummons.contains(entity));
                assertTrue(manager.getSummonEntities().contains(entity));
                assertEquals(1,manager.getSummonEntity2SummonCfgId().get(entity));
                var task=TreeFixture.tree(new ActFindSummonOwner(10,"owner"),(Entity)entity,host.behaviour);
                assertEquals(Statusenum.BTSUCCESS,task.update()); assertSame(owner,task.getBlackBoard().getValue("owner"));
            }
        });
        assertEquals(List.of(7,7,8,8),host.createdAiParams);
        assertEquals(SummonType.NORMAL,manager.getSummonCfgId2SummonType().get(1));
        host.advanceTo(120); assertTrue(manager.getSummonEntities().isEmpty());
        assertTrue(manager.getLeaveMapListeners().isEmpty()); assertTrue(manager.getSummonCfgId2SummonEntities().isEmpty());
        assertTrue(manager.getSummonCfgId2SummonType().isEmpty());
    }
    @Test void zeroConfiguredLifetimePreservesParameterDeadlineAndActiveRemovalCallsHook() {
        var host=new SceneFixture(); var owner=new Entity(1); host.summons.put(1,SceneFixture.summon(1,false,0,50));
        List<Integer> removedTypes=new ArrayList<>();
        var manager=new SummonManager(owner,host) {
            protected void onActiveRemoveSummonEntity(GEntity entity,int type) { removedTypes.add(type); }
        };
        var param=new NormalSummonEntityParam(); param.setLeaveMapTimeStampMills(99); param.setOverrideSummonCount(1);
        manager.summonAndEnterMap(1,param,null);
        Entity child=(Entity)manager.getSummonEntities().iterator().next(); assertEquals(99,host.lifeDeadlines.get(child));
        manager.activeRemoveSummonEntities(1); manager.activeRemoveSummonEntities(1);
        assertEquals(List.of(SummonType.NORMAL),removedTypes); assertTrue(manager.getSummonEntities().isEmpty());
        assertEquals(0,child.listenerCount()); assertTrue(manager.getSummonEntity2SummonCfgId().isEmpty());
    }
    @Test void ownerLeaveDestroysOrDetachesWithIndependentTimeoutAndReleasesEveryListener() {
        var host=new SceneFixture(); var owner=new Entity(1); host.behaviour.now=100;
        host.summons.put(1,SceneFixture.summon(1,true,500,30)); host.summons.put(2,SceneFixture.summon(2,false,500,10));
        var manager=new NonePlayerSummonManager(owner,host); manager.summonAndEnterMap(1); manager.summonAndEnterMap(2);
        var destroyed=List.copyOf(manager.getSummonCfgId2SummonEntities().get(1));
        var survivors=List.copyOf(manager.getSummonCfgId2SummonEntities().get(2));
        manager.ownerLeaveMap(); manager.ownerLeaveMap();
        for(GEntity e:destroyed) { assertTrue(e.isDestroyed()); assertEquals(0,((Entity)e).listenerCount()); }
        for(GEntity e:survivors) {
            assertFalse(e.isDestroyed()); assertEquals(0,e.getOwnerInstId()); assertEquals(600,host.lifeDeadlines.get(e));
            assertEquals(110,host.ownerDeadlines.get(e)); assertEquals(0,((Entity)e).listenerCount());
            var task=TreeFixture.tree(new ActFindSummonOwner(10,"owner"),(Entity)e,host.behaviour);
            assertEquals(Statusenum.BTFAILURE,task.update());
        }
        assertNull(manager.getSelf()); assertTrue(manager.getSummonEntities().isEmpty());
        assertTrue(manager.getSummonCfgId2SummonEntities().isEmpty()); assertTrue(manager.getSummonCfgId2SummonType().isEmpty());
        host.advanceTo(109); assertTrue(survivors.stream().noneMatch(GEntity::isDestroyed));
        host.advanceTo(110); assertTrue(survivors.stream().allMatch(GEntity::isDestroyed));
        assertThrows(IllegalStateException.class,()->manager.summonAndEnterMap(1));
    }
    @Test void manuallyAddedChildIsNotDoubleSubscribedAndDetachesOnOwnerLeave() {
        var host=new SceneFixture(); var owner=new Entity(1); var child=new Entity(2);
        var manager=new NonePlayerSummonManager(owner,host); var param=new NormalSummonEntityParam();
        manager.addSummonEntity(child,param); manager.addSummonEntity(child,param); assertEquals(1,child.listenerCount());
        manager.ownerLeaveMap(); assertEquals(0,child.ownerId); assertEquals(0,child.listenerCount());
    }
    @Test void passiveRemovalReleasesIndexesAndLeaveHookRunsOnce() {
        var host=new SceneFixture(); var owner=new Entity(1); host.summons.put(1,SceneFixture.summon(1,false,0,10));
        List<GEntity> removed=new ArrayList<>();
        var manager=new SummonManager(owner,host) { protected void onSummonEntityLeaveMap(GEntity entity) { removed.add(entity); } };
        manager.summonAndEnterMap(1); var children=List.copyOf(manager.getSummonEntities());
        for(GEntity child:children) { host.leaveMap(child); child.triggerEvent(new EventObjectLeaveMap(child)); }
        assertEquals(new HashSet<>(children),new HashSet<>(removed)); assertEquals(children.size(),removed.size());
        assertTrue(manager.getSummonEntities().isEmpty()); assertTrue(manager.getSummonCfgId2SummonType().isEmpty());
    }
    @Test void setPositionUsesOwnerDirectionAndHeightAndUnsupportedPositionFailsBeforeCreation() {
        var host=new SceneFixture(); var owner=new Entity(1); host.directions.put(owner,90f);
        host.summons.put(1,new SummonConfig(1,1,true,0,3,1,List.of(new SummonUnit(10,0,1)),1,
                new Vector3(10,3,20),180,2,0,2,4,Util.ROTATION_OWNER_ROTATION,15,false,10));
        var manager=new NonePlayerSummonManager(owner,host); manager.summonAndEnterMap(1);
        var child=manager.getSummonEntities().iterator().next();
        assertEquals(12,child.getPos().x,0.00001); assertEquals(7,child.getPos().y); assertEquals(20,child.getPos().z,0.00001);
        assertEquals(105,host.getDirection(child));
        host.summons.put(2,new SummonConfig(2,1,true,0,3,1,List.of(new SummonUnit(10,0,1)),3,
                new Vector3(0,0,0),180,2,0,2,0,1,0,false,10));
        assertThrows(IllegalArgumentException.class,()->manager.summonAndEnterMap(2));
        assertEquals(1,manager.getSummonEntities().size());
    }
    @Test void edgeDistributionRetainsCoincidentFullCircleEndpointsAndRandomPointsStayInFan() {
        var full=new AreaFan(new Vector3(2,3,4),10,0,180);
        var edge=AreaVisitorListPoint.create(EDistributeType.EDGE,3); edge.visit(full);
        assertTrue(edge.getPoints().getFirst().Equal(edge.getPoints().getLast()));
        var fan=new AreaFan(new Vector3(2,3,4),10,10,30);
        var random=AreaVisitorListPoint.create(EDistributeType.RANDOM,100); random.visit(fan);
        assertEquals(100,random.getPoints().size());
        for(Vector3 point:random.getPoints()) { assertTrue(fan.isInArea(point)); assertEquals(3,point.y); }
    }
}
