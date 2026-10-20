package behaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TypedNumber {
    private final Object number;
    private final Class<?> type;

    private TypedNumber(Object number) {
        if (!(number instanceof Integer || number instanceof Long ||
                number instanceof Float || number instanceof Double)) {
            throw new IllegalArgumentException("Only int, long, float, double are supported. Got: " + number.getClass().getSimpleName());
        }
        this.number = Objects.requireNonNull(number, "Value cannot be null");
        this.type = number.getClass();
    }

    // 缓存机制
    private static final int INT_LOW = -128;
    private static final int INT_HIGH = 127;
    private static final TypedNumber[] INT_CACHE = new TypedNumber[INT_HIGH - INT_LOW + 1];
    private static final TypedNumber[] LONG_CACHE = new TypedNumber[INT_HIGH - INT_LOW + 1];

    private static final Map<Float, TypedNumber> FLOAT_CACHE = new HashMap<>();
    private static final Map<Double, TypedNumber> DOUBLE_CACHE = new HashMap<>();

    static {
        for (int i = INT_LOW; i <= INT_HIGH; i++) {
            INT_CACHE[i - INT_LOW] = new TypedNumber(i);
            LONG_CACHE[i - INT_LOW] = new TypedNumber((long) i);
        }

        FLOAT_CACHE.put(0.0f, new TypedNumber(0.0f));
        FLOAT_CACHE.put(1.0f, new TypedNumber(1.0f));
        FLOAT_CACHE.put(-1.0f, new TypedNumber(-1.0f));
        FLOAT_CACHE.put(0.5f, new TypedNumber(0.5f));

        DOUBLE_CACHE.put(0.0, new TypedNumber(0.0));
        DOUBLE_CACHE.put(1.0, new TypedNumber(1.0));
        DOUBLE_CACHE.put(-1.0, new TypedNumber(-1.0));
        DOUBLE_CACHE.put(0.5, new TypedNumber(0.5));
    }

    public static TypedNumber of(int value) {
        if (value >= -128 && value <= 127) {
            return INT_CACHE[value + 128];
        }
        return new TypedNumber(value);
    }

    public static TypedNumber of(long value) {
        if (value >= -128 && value <= 127) {
            return LONG_CACHE[(int) value + 128];
        }
        return new TypedNumber(value);
    }

    public static TypedNumber of(float value) {
        TypedNumber cached = FLOAT_CACHE.get(value);
        if (cached != null) return cached;
        return new TypedNumber(value);
    }

    public static TypedNumber of(double value) {
        TypedNumber cached = DOUBLE_CACHE.get(value);
        if (cached != null) return cached;
        return new TypedNumber(value);
    }

    public static TypedNumber of(Object value) {
        Objects.requireNonNull(value, "Value cannot be null");
        if (value instanceof Integer) return of((int) value);
        if (value instanceof Long) return of((long) value);
        if (value instanceof Float) return of((float) value);
        if (value instanceof Double) return of((double) value);
        throw new IllegalArgumentException("Unsupported type: " + value.getClass().getSimpleName());
    }



    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> expectedType) {
        if (!expectedType.isAssignableFrom(type)) {
            throw new BadTypeException(
                    String.format("Cannot cast %s to %s", type.getSimpleName(), expectedType.getSimpleName())
            );
        }
        return (T) number;
    }

    public boolean is(Class<?> clazz) {
        return clazz.isAssignableFrom(type);
    }

    public Class<?> getType() {
        return type;
    }

    public Object origin() {
        return number;
    }

    public int intValue() {
        return get(Number.class).intValue();
    }

    public long longValue() {
        return get(Number.class).longValue();
    }

    public float floatValue() {
        return get(Number.class).floatValue();
    }

    public double doubleValue() {
        return get(Number.class).doubleValue();
    }

    public TypedNumber add(TypedNumber other) {
        return arithmeticOperation(other, Operation.ADD);
    }

    public TypedNumber subtract(TypedNumber other) {
        return arithmeticOperation(other, Operation.SUBTRACT);
    }

    public TypedNumber multiply(TypedNumber other) {
        return arithmeticOperation(other, Operation.MULTIPLY);
    }

    public TypedNumber divide(TypedNumber other) {
        return arithmeticOperation(other, Operation.DIVIDE);
    }

    private enum Operation { ADD, SUBTRACT, MULTIPLY, DIVIDE }

    private TypedNumber arithmeticOperation(TypedNumber other, Operation op) {
        Number n1 = get(Number.class);
        Number n2 = other.get(Number.class);

        Class<?> resultType = determineResultType(n1, n2);

        try {
            if (resultType == Double.class) {
                return handleDouble(n1.doubleValue(), n2.doubleValue(), op);
            } else if (resultType == Float.class) {
                return handleFloat(n1.floatValue(), n2.floatValue(), op);
            } else if (resultType == Long.class) {
                return handleLong(n1.longValue(), n2.longValue(), op);
            } else {
                return handleInt(n1.intValue(), n2.intValue(), op);
            }
        } catch (ArithmeticException e) {
            throw new ArithmeticException(op + " error: " + e.getMessage());
        }
    }

    public static TypedNumber max(TypedNumber a, TypedNumber b) {
        Number n1 = a.get(Number.class);
        Number n2 = b.get(Number.class);
        Class<?> resultType = determineResultType(n1, n2);

        if (resultType == Double.class) return of(Math.max(n1.doubleValue(), n2.doubleValue()));
        if (resultType == Float.class) return of(Math.max(n1.floatValue(), n2.floatValue()));
        if (resultType == Long.class) return of(Math.max(n1.longValue(), n2.longValue()));
        return of(Math.max(n1.intValue(), n2.intValue()));
    }

    public static TypedNumber min(TypedNumber a, TypedNumber b) {
        Number n1 = a.get(Number.class);
        Number n2 = b.get(Number.class);
        Class<?> resultType = determineResultType(n1, n2);

        if (resultType == Double.class) return of(Math.min(n1.doubleValue(), n2.doubleValue()));
        if (resultType == Float.class) return of(Math.min(n1.floatValue(), n2.floatValue()));
        if (resultType == Long.class) return of(Math.min(n1.longValue(), n2.longValue()));
        return of(Math.min(n1.intValue(), n2.intValue()));
    }

    public int compareTo(TypedNumber other) {
        Number n1 = this.get(Number.class);
        Number n2 = other.get(Number.class);
        Class<?> resultType = determineResultType(n1, n2);

        if (resultType == Double.class) return Double.compare(n1.doubleValue(), n2.doubleValue());
        if (resultType == Float.class) return Float.compare(n1.floatValue(), n2.floatValue());
        if (resultType == Long.class) return Long.compare(n1.longValue(), n2.longValue());
        return Integer.compare(n1.intValue(), n2.intValue());
    }

    private static Class<?> determineResultType(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) return Double.class;
        if (a instanceof Float || b instanceof Float) return Float.class;
        if (a instanceof Long || b instanceof Long) return Long.class;
        return Integer.class;
    }

    private TypedNumber handleDouble(double a, double b, Operation op) {
        switch (op) {
            case ADD: return of(a + b);
            case SUBTRACT: return of(a - b);
            case MULTIPLY: return of(a * b);
            case DIVIDE: return safeDivide(a, b);
            default: throw new IllegalArgumentException("Unknown operation");
        }
    }

    private TypedNumber handleFloat(float a, float b, Operation op) {
        switch (op) {
            case ADD: return of(a + b);
            case SUBTRACT: return of(a - b);
            case MULTIPLY: return of(a * b);
            case DIVIDE: return safeDivide(a, b);
            default: throw new IllegalArgumentException("Unknown operation");
        }
    }

    private TypedNumber handleLong(long a, long b, Operation op) {
        switch (op) {
            case ADD: return of(Math.addExact(a, b));
            case SUBTRACT: return of(Math.subtractExact(a, b));
            case MULTIPLY: return of(Math.multiplyExact(a, b));
            case DIVIDE: return safeDivide(a, b);
            default: throw new IllegalArgumentException("Unknown operation");
        }
    }

    private TypedNumber handleInt(int a, int b, Operation op) {
        switch (op) {
            case ADD: return of(Math.addExact(a, b));
            case SUBTRACT: return of(Math.subtractExact(a, b));
            case MULTIPLY: return of(Math.multiplyExact(a, b));
            case DIVIDE: return safeDivide(a, b);
            default: throw new IllegalArgumentException("Unknown operation");
        }
    }

    private TypedNumber safeDivide(double a, double b) {
        if (b == 0.0) throw new ArithmeticException("Division by zero");
        return of(a / b);
    }

    private TypedNumber safeDivide(float a, float b) {
        if (b == 0.0f) throw new ArithmeticException("Division by zero");
        return of(a / b);
    }

    private TypedNumber safeDivide(long a, long b) {
        if (b == 0L) throw new ArithmeticException("Division by zero");
        return of(a / b);
    }

    private TypedNumber safeDivide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return of(a / b);
    }

    public static class BadTypeException extends RuntimeException {
        public BadTypeException(String message) {
            super(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypedNumber)) return false;
        TypedNumber that = (TypedNumber) o;
        return this.compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.doubleValue());
    }

    @Override
    public String toString() {
        return String.format("TypedNumber{type=%s, number=%s}", type.getSimpleName(), number);
    }

    public String toNumberString() {
        return String.format("number=%s", number);
    }
}
