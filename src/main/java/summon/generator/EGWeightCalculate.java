package summon.generator;

import summon.SummonUnit;
import summon.Util;
import java.util.AbstractMap.SimpleEntry;

import java.util.List;

/**
 * Created by zyao on 2025/2/28 12:11
 */
public class EGWeightCalculate implements IEntityIdGenerator{
    @Override
    public List<Integer> generateIndexes(List<SummonUnit> summonunitListCfg, int num) {
        int[] weights = Util.getSummonWeights(summonunitListCfg);
        long weightSum = 0;
        for (int i = 0; i < weights.length; i++) {
            weightSum += weights[i];
        }
        List<SimpleEntry<Integer, Integer>> numList = new java.util.ArrayList<>();
        int tmpNum = num;
        for (int i = 0; i < summonunitListCfg.size(); i++) {
            int calNum = (int) ((long) summonunitListCfg.get(i).getWeight() * num / weightSum);
            if (tmpNum < calNum) {
                calNum = tmpNum;
            }
            tmpNum -= calNum;
            if (i != summonunitListCfg.size() - 1) {
                numList.add(new SimpleEntry<>(i, calNum));
            } else {
                numList.add(new SimpleEntry<>(i, calNum + tmpNum));
            }
        }
        List<Integer> result = new java.util.ArrayList<>();
        for (int i = 0; i < numList.size(); i++) {
            for (int j = 0; j < numList.get(i).getValue(); j++) {
                result.add(numList.get(i).getKey());
            }
        }
        return result;
    }
}
