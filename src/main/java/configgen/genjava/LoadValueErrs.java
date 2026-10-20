package configgen.genjava;

import java.util.ArrayList;
import java.util.List;


/// 不支持多线程
public final class LoadValueErrs {
    public record Error(String desc, Object key) {
    }

    private static boolean isThrowOnError = true; // 默认throw
    private static List<Error> errors;

    public static void start(boolean throwOnError) {
        isThrowOnError = throwOnError;
        errors = new ArrayList<>();
    }

    public static List<Error> getErrors() {
        return errors;
    }

    public static void requireNonNull(Object value, String desc, Object key) {
        if (value == null) {
            if (isThrowOnError) {
                throw new NullPointerException(desc + ": key=" + key);
            } else {
                errors.add(new Error(desc, key));
            }
        }
    }
}
