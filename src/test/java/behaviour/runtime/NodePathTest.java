package behaviour.runtime;

import behaviour.*;

import config.behaviour.condition.Success;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodePathTest {
    @Test
    @DisplayName("FNV-1a 哈希结果稳定")
    void fnv1a64ReturnsStableHash() {
        assertEquals("e71fa2190541574b", String.format("%016x", NodePath.fnv1a64("abc")));
    }

    @Test
    @DisplayName("根节点路径包含节点类型和稳定 id")
    void rootPathContainsNodeTypeAndStableId() {
        Condition<?> success = new behaviour.condition.Success(new Success());

        NodePath rootPath = NodePath.root(success);

        assertEquals("node-Success", rootPath.getPath());
        assertEquals(NodePath.fnv1a64(rootPath.getPath()), rootPath.getPathId());
    }

    @Test
    @DisplayName("子节点路径包含字段名、下标和节点类型")
    void childPathContainsFieldNameIndexAndNodeType() {
        Condition<?> parent = new behaviour.condition.Success(new Success());
        Condition<?> child = new behaviour.condition.Success(new Success());

        NodePath childPath = NodePath.root(parent).child("nodes", 2, child);

        assertEquals("node-Success/nodes[2]-Success", childPath.getPath());
        assertEquals(NodePath.fnv1a64(childPath.getPath()), childPath.getPathId());
    }

    @Test
    @DisplayName("为行为树分配路径后节点可以读取稳定 id")
    void assignTreeSetsPathAndIdOnAtom() {
        behaviour.condition.Success success =
                new behaviour.condition.Success(new Success());

        NodePath.assignTree(success);

        assertEquals("node-Success", success.getPath());
        assertEquals(NodePath.fnv1a64("node-Success"), success.getId());
        assertEquals(success.getPathIdHex(), String.format("%016x", success.getId()));
    }
}
