package summon.generator;

import summon.SummonUnit;
import summon.Util;
import util.MathUtil;

import java.util.List;

/**
 * Created by zyao on 2025/2/28 12:03
 */
public class EGRandomByNum implements IEntityIdGenerator{
    @Override
    public List<Integer> generateIndexes(List<SummonUnit> summonunitListCfg, int num) {
        int[] weights = Util.getSummonWeights(summonunitListCfg);
        List<Integer> resultList = new java.util.ArrayList<>();
        for (int i = 0; i < num; i++) {
            int idx = MathUtil.randomByWeight(weights);
            resultList.add(idx);
        }
        return resultList;
    }
}
