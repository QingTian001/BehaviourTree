package summon.param;

import summon.SummonType;

/**
 * Created by zyao on 2025/9/9 10:58
 */
public class NormalSummonEntityParam extends SummonEntityParam {
    @Override
    public int getSummonType() {
        return SummonType.NORMAL;
    }
}
