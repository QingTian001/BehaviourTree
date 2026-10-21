package area;
import math.Vector3;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class AreaGeometryTest {
    @Test void zeroVectorHasNoDirectionAndCardinalAnglesMatchSource() {
        assertThrows(UnsupportedOperationException.class, () -> new Vector3(0,0,0).toXZAngle());
        assertEquals(90,new Vector3(1,0,0).toXZAngle());
        assertEquals(180,new Vector3(0,0,-1).toXZAngle());
        assertEquals(270,new Vector3(-1,0,0).toXZAngle());
    }
    @Test void circleUsesHorizontalDistanceAndStrictBoundary() {
        var a = new AreaCircle(new Vector3(0, 10, 0), 2);
        assertTrue(a.isInArea(new Vector3(1, -100, 0)));
        assertFalse(a.isInArea(new Vector3(2, 10, 0)));
    }
    @Test void fanWrapsZeroAndIncludesCenterAndRadius() {
        var a = new AreaFan(new Vector3(0, 0, 0), 2, 0, 30);
        assertTrue(a.isInArea(new Vector3(0, 0, 0)));
        assertTrue(a.isInArea(new Vector3(0, 0, 2)));
        assertTrue(a.isInArea(new Vector3(-0.2f, 0, 1)));
        assertFalse(a.isInArea(new Vector3(1, 0, 0)));
    }
    @Test void rectangleKeepsImmutableInputsAndRecognizesRotation() {
        var p = new Vector3(4, 2, 3);
        var axis = AreaRectangle.dirToNewVectorX(90);
        assertTrue(p.Equal(p.relocateReverseXZ(axis).relocateXZ(axis)));
        assertEquals(4, p.x);
        var a = new AreaRectangle(p, axis, 2, 6);
        assertTrue(a.isInArea(p));
        assertTrue(a.isRotation());
        var aligned = new AreaRectangle(new Vector3(-1, 0, -1), new Vector3(1, 0, 1));
        assertFalse(aligned.isRotation());
        assertTrue(aligned.isInArea(new Vector3(1, 8, 1)));
    }
    @Test void pointsExcludeUsedAndDoNotRepeat() {
        var a = new Vector3(0,0,0); var b = new Vector3(1,0,0); var c = new Vector3(2,0,0);
        var points = new AreaPoints(List.of(a,b,c));
        var result = points.genNotRepeatRandomPoints(2, List.of(a));
        assertEquals(Set.of(b,c), new HashSet<>(result));
        assertThrows(UnsupportedOperationException.class, () -> result.clear());
        assertTrue(new AreaOnePoint(a, 45).isInArea(new Vector3(.05f,0,0)));
        assertFalse(new AreaOnePoint(a, 45).isInArea(new Vector3(.2f,0,0)));
    }
}
