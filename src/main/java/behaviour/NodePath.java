package behaviour;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * 行为树节点路径与稳定 id。
 * <p>
 * 路径从 rootNode 起，每段格式为 字段名[数组下标]-实际类型，如
 * node-CompParallel/nodes[2]-ActWaitTimeMs/preCondConfig-Not。
 * 由 {@link #assignTree(Atom)} 在建树完成后统一反射遍历分配；
 * pathId 为路径字符串的 FNV-1a 64 位哈希，同一棵树内唯一且跨重启稳定。
 */
public final class NodePath {
    private static final long FNV1A64_OFFSET = 0xcbf29ce484222325L;
    private static final long FNV1A64_PRIME = 0x100000001b3L;

    private final String path;
    private final long pathId;

    private NodePath(String path) {
        this.path = path;
        this.pathId = fnv1a64(path);
    }

    /** root 段，字段名固定为 node，如 node-CompParallel */
    public static NodePath root(Atom<?> rootAtom) {
        return new NodePath("node-" + rootAtom.getClass().getSimpleName());
    }

    /** 单子节点字段，如 preCondConfig-Not */
    public NodePath child(String fieldName, Atom<?> child) {
        return new NodePath(path + "/" + fieldName + "-" + child.getClass().getSimpleName());
    }

    /** 集合/数组元素字段，如 nodes[2]-ActWaitTimeMs */
    public NodePath child(String fieldName, int index, Atom<?> child) {
        return new NodePath(path + "/" + fieldName + "[" + index + "]-" + child.getClass().getSimpleName());
    }

    public String getPath() {
        return path;
    }

    public long getPathId() {
        return pathId;
    }

    /** 16 位小写十六进制表示 */
    public String getPathIdHex() {
        return String.format("%016x", pathId);
    }

    /** FNV-1a 64 位哈希，输入为字符串的 UTF-8 字节 */
    public static long fnv1a64(String value) {
        long hash = FNV1A64_OFFSET;
        for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
            hash ^= (b & 0xffL);
            hash *= FNV1A64_PRIME;
        }
        return hash;
    }

    /**
     * 从 rootAtom 出发反射遍历整棵树，为每个 Atom 分配路径与 pathId。
     * 子节点关系通过 Atom 子类的实例字段发现：字段值为 Atom、
     * 或 Iterable/数组元素为 Atom 时递归下钻，空值跳过。
     * 同一棵树内出现重复路径或重复 pathId 视为配置异常直接抛出。
     */
    public static void assignTree(Atom<?> rootAtom) {
        Set<String> paths = new HashSet<>();
        Set<Long> pathIds = new HashSet<>();
        assignAtom(rootAtom, root(rootAtom), paths, pathIds);
    }

    private static void assignAtom(Atom<?> atom, NodePath nodePath, Set<String> paths, Set<Long> pathIds) {
        if (!paths.add(nodePath.getPath())) {
            throw new RuntimeException("行为树节点路径重复: " + nodePath.getPath());
        }
        if (!pathIds.add(nodePath.getPathId())) {
            throw new RuntimeException("行为树节点 pathId 碰撞: " + nodePath.getPath() + " -> " + nodePath.getPathIdHex());
        }
        atom.setPath(nodePath.getPath(), nodePath.getPathId());

        for (Class<?> clazz = atom.getClass(); clazz != null && clazz != Atom.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(atom);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("读取行为树节点字段失败: " + atom.getClass().getName() + "." + field.getName(), e);
                }
                if (value == null) {
                    continue;
                }
                if (value instanceof Atom) {
                    assignAtom((Atom<?>) value, nodePath.child(field.getName(), (Atom<?>) value), paths, pathIds);
                } else if (value instanceof Iterable) {
                    int index = 0;
                    for (Object item : (Iterable<?>) value) {
                        if (item instanceof Atom) {
                            assignAtom((Atom<?>) item, nodePath.child(field.getName(), index, (Atom<?>) item), paths, pathIds);
                        }
                        index++;
                    }
                } else if (value.getClass().isArray()) {
                    int length = Array.getLength(value);
                    for (int index = 0; index < length; index++) {
                        Object item = Array.get(value, index);
                        if (item instanceof Atom) {
                            assignAtom((Atom<?>) item, nodePath.child(field.getName(), index, (Atom<?>) item), paths, pathIds);
                        }
                    }
                }
            }
        }
    }
}
