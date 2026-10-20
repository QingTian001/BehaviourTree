package util;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
public final class MathUtil {
    private MathUtil() { }
    public static int randBetween(int min, int max) {
        return (int) ThreadLocalRandom.current().nextLong(Math.min(min, max), (long) Math.max(min, max) + 1);
    }
    public static int randomByWeight(List<Integer> weights) {
        long total = 0;
        for (int weight : weights) {
            if (weight < 0) { throw new IllegalArgumentException("权重不能为负数"); }
            total += weight;
        }
        if (total == 0) { throw new IllegalArgumentException("总权重必须大于零"); }
        long choice = ThreadLocalRandom.current().nextLong(total);
        for (int i = 0; i < weights.size(); i++) {
            choice -= weights.get(i);
            if (choice < 0) { return i; }
        }
        throw new IllegalStateException("权重选择失败");
    }
    public static int randomByWeight(int[] weights) {
        return randomByWeight(java.util.Arrays.stream(weights).boxed().toList());
    }
}
