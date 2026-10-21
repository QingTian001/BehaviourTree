package summon.generator;

import summon.SummonUnit;
import summon.Util;
import util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyao on 2025/2/28 11:56
 */
public class EGRandomOne implements IEntityIdGenerator {
    @Override
    public List<Integer> generateIndexes(List<SummonUnit> summonunitListCfg, int num) {
        int[] weights = Util.getSummonWeights(summonunitListCfg);
        int idx = MathUtil.randomByWeight(weights);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            result.add(idx);
        }
        return result;
    }
}
