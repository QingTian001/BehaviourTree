package summon.generator;

import java.util.*;
/**
 * Created by zyao on 2025/2/28 11:51
 */
public interface IEntityIdGenerator {
    List<Integer> generateIndexes(List<summon.SummonUnit> summonunitListCfg, int num);

    static IEntityIdGenerator create(int summonType) {
        switch (summonType) {
            case 1:
                return new EGRandomOne();
            case 2:
                return new EGRandomByNum();
            case 3:
                return new EGWeightCalculate();
            default:
                throw new RuntimeException();
        }
    }
}
