package behaviour.runtime;

import behaviour.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TypedNumberTest {
    @Test
    @DisplayName("创建数值时保留原始数值类型")
    void factoryPreservesNumberType() {
        assertAll(
                () -> assertEquals(Integer.class, TypedNumber.of(1).getType()),
                () -> assertEquals(Long.class, TypedNumber.of(1L).getType()),
                () -> assertEquals(Float.class, TypedNumber.of(1.0f).getType()),
                () -> assertEquals(Double.class, TypedNumber.of(1.0).getType())
        );
    }

    @Test
    @DisplayName("混合运算结果使用更宽的数值类型")
    void arithmeticUsesWidestNumberType() {
        TypedNumber longResult = TypedNumber.of(2).add(TypedNumber.of(3L));
        TypedNumber doubleResult = TypedNumber.of(1.5f).multiply(TypedNumber.of(2.0));

        assertEquals(Long.class, longResult.getType());
        assertEquals(5L, longResult.longValue());
        assertEquals(Double.class, doubleResult.getType());
        assertEquals(3.0, doubleResult.doubleValue());
    }

    @Test
    @DisplayName("不同数值包装类型的相同数值可以比较相等")
    void equalValuesWithDifferentNumberTypesAreEqual() {
        TypedNumber integer = TypedNumber.of(1);
        TypedNumber longValue = TypedNumber.of(1L);

        assertEquals(integer, longValue);
        assertEquals(integer.hashCode(), longValue.hashCode());
        assertEquals(0, integer.compareTo(longValue));
    }

    @Test
    @DisplayName("min 和 max 保留运算结果类型")
    void minAndMaxPreserveResultType() {
        TypedNumber min = TypedNumber.min(TypedNumber.of(2), TypedNumber.of(3L));
        TypedNumber max = TypedNumber.max(TypedNumber.of(2), TypedNumber.of(3L));

        assertEquals(Long.class, min.getType());
        assertEquals(2L, min.longValue());
        assertEquals(Long.class, max.getType());
        assertEquals(3L, max.longValue());
    }

    @Test
    @DisplayName("整数溢出和除零会抛出算术异常")
    void overflowAndDivisionByZeroThrowArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> TypedNumber.of(Integer.MAX_VALUE).add(TypedNumber.of(1)));
        assertThrows(ArithmeticException.class,
                () -> TypedNumber.of(1).divide(TypedNumber.of(0)));
    }

    @Test
    @DisplayName("读取错误类型会抛出类型异常")
    void getWrongTypeThrowsBadTypeException() {
        assertThrows(TypedNumber.BadTypeException.class,
                () -> TypedNumber.of(1).get(String.class));
    }
}
