package area;

/**
 * Created by zyao on 2025/2/8 11:55
 */
public interface IAreaVisitor {
    default void visit(AreaCircle areaCircle){
        throw new RuntimeException("not support");
    }
    default void visit(AreaRectangle areaRectangle){
        throw new RuntimeException("not support");
    }
    default void visit(AreaPoints areaPoints) {
        throw new RuntimeException("not support");
    }
    default void visit(AreaOnePoint areaOnePoint) {
        throw new RuntimeException("not support");
    }
    default void visit(AreaFan area) {
        throw new RuntimeException("not support");
    }
}
