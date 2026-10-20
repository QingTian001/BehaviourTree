package config.behaviour.node;

public final class ActFindNonePlayerEntity implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTFINDNONEPLAYERENTITY;
    }

    private int objType;
    private java.util.List<Integer> cfgIdList;
    private float radius;
    private String wbbKeyEntity;

    private ActFindNonePlayerEntity() {
    }

    public ActFindNonePlayerEntity(int objType, java.util.List<Integer> cfgIdList, float radius, String wbbKeyEntity) {
        this.objType = objType;
        this.cfgIdList = cfgIdList;
        this.radius = radius;
        this.wbbKeyEntity = wbbKeyEntity;
    }

    public static ActFindNonePlayerEntity _create(configgen.genjava.ConfigInput input) {
        ActFindNonePlayerEntity self = new ActFindNonePlayerEntity();
        self.objType = input.readInt();
        {
            int c = input.readInt();
            if (c == 0) {
                self.cfgIdList = java.util.Collections.emptyList();
            } else {
                self.cfgIdList = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.cfgIdList.add(input.readInt());
                }
            }
        }
        self.radius = input.readFloat();
        self.wbbKeyEntity = input.readStringInPool();
        return self;
    }

    /**
     * 不要填Role类型
     */
    public int getObjType() {
        return objType;
    }

    /**
     * 配置id列表
     */
    public java.util.List<Integer> getCfgIdList() {
        return cfgIdList;
    }

    /**
     * 半径
     */
    public float getRadius() {
        return radius;
    }

    /**
     * wbbKeyMineEntity
     */
    public String getWbbKeyEntity() {
        return wbbKeyEntity;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(objType, cfgIdList, radius, wbbKeyEntity);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActFindNonePlayerEntity))
            return false;
        ActFindNonePlayerEntity o = (ActFindNonePlayerEntity) other;
        return objType == o.objType && cfgIdList.equals(o.cfgIdList) && radius == o.radius && wbbKeyEntity.equals(o.wbbKeyEntity);
    }

    @Override
    public String toString() {
        return "ActFindNonePlayerEntity(" + objType + "," + cfgIdList + "," + radius + "," + wbbKeyEntity + ")";
    }

}
