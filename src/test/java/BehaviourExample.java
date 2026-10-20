import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import config.behaviour.Behaviour;
import config.behaviour.Statusenum;
import testsupport.TreeFixture;
import java.util.List;

/** 可运行的内存测试宿主示例，真实项目应实现 BehaviourHost。 */
public final class BehaviourExample {
    public static void main(String[] args) {
        BehaviourManager.start();
        var self = new TreeFixture.Entity(1);
        var owner = new TreeFixture.Entity(2);
        var host = new TreeFixture.Host();
        self.ownerId = 2;
        owner.position = new math.Vector3(2, 0, 3);
        host.entities.put(2L, owner);
        host.candidates = List.of(owner);

        var task = BehaviourFactory.createBehaviourTreeTask(self, host, Behaviour.get(8), List.of());
        Statusenum status = Statusenum.BTRUNNING;
        for (int tick = 0; tick < 10 && status == Statusenum.BTRUNNING; tick++) {
            status = task.update();
            // 测试宿主模拟异步操作在下一帧完成，不依赖真实时间或场景服务。
            for (var operation : host.operations) { operation.status = Statusenum.BTSUCCESS; }
            host.now += 50;
        }
        if (status != Statusenum.BTSUCCESS || host.operations.size() != 2) {
            throw new IllegalStateException("findOwner、移动和交互示例未完成");
        }
        var query = BehaviourFactory.createBehaviourTreeTask(self, host, Behaviour.get(9), List.of());
        if (query.update() != Statusenum.BTSUCCESS || query.getBlackBoard().getValue("target") != owner) {
            throw new IllegalStateException("查找对象示例未完成");
        }
        var fibonacci = BehaviourFactory.createBehaviourTreeTask(self, host, Behaviour.get(7), List.of(6));
        Statusenum result = fibonacci.update();
        if (!BehaviourHelper.isNodeFinished(result) || fibonacci.getBlackBoard().getIntValue("ret") != 13) {
            throw new IllegalStateException("递归子树示例返回值错误");
        }
        System.out.println("示例通过：findOwner、移动、交互、查找对象；递归子树 F(6)=13。");
    }
}
