package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionUtil {
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperGenericType(Class<?> clazz) {
        return getSuperGenericType(clazz, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperGenericType(Class<?> clazz, int index) {
        var superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            var superClazz = (ParameterizedType) superclass;
            return (Class<T>) superClazz.getActualTypeArguments()[index];
        } else {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> Class<T> getTargetSuperGenericType(Class<?> clazz, Class<?> superClazz) {
        return getTargetSuperGenericType(clazz, superClazz, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getTargetSuperGenericType(Class<?> clazz, Class<?> superClazz, int index) {

        if (!superClazz.isAssignableFrom(clazz) || clazz == superClazz) {
            throw new IllegalArgumentException("");
        }

        Class<?> tmpClz = clazz;
        Type t = null;
        while (tmpClz != superClazz) {
            t = tmpClz.getGenericSuperclass();

            if (t instanceof ParameterizedType) {
                tmpClz = (Class<?>)((ParameterizedType)t).getRawType();
            } else {
                tmpClz = (Class<?>)t;
            }
        }

        if (!(t instanceof ParameterizedType)) {
            throw new RuntimeException("t must be ParameterizedType. t:" + t);
        }

        return (Class<T>)((ParameterizedType)t).getActualTypeArguments()[index];

    }

}
