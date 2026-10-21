package summon;
/** 宿主将原 Summonunit 配置转换为此快照。 */
public record SummonUnit(int objectId, int aiParam, int weight) {
    public int getObjectId() { return objectId; }
    /**
     * 关联到aiparams表, 填了会报错, 即使加上nullable, 大概率是配表工具bug, 所以没有关联
     */
    public int getAiParam() { return aiParam; }
    public int getWeight() { return weight; }
}
