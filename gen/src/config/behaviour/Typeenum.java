package config.behaviour;

public class Typeenum {
    private int id;
    private String ename;

    private Typeenum() {
    }

    public static Typeenum _create(configgen.genjava.ConfigInput input) {
        Typeenum self = new Typeenum();
        self.id = input.readInt();
        self.ename = input.readStr();
        return self;
    }

    public int getId() {
        return id;
    }

    /**
     * 枚举名
     */
    public String getEname() {
        return ename;
    }

    @Override
    public String toString() {
        return "(" + id + "," + ename + ")";
    }

    public static Typeenum get(int id) {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.getBehaviourTypeenum(id);
    }

    public static java.util.Collection<Typeenum> all() {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.allBehaviourTypeenum();
    }

    public static class _ConfigLoader implements config.ConfigLoader {

        @Override
        public void createAll(config.ConfigMgr mgr, configgen.genjava.ConfigInput input) {
            for (int c = input.readInt(); c > 0; c--) {
                Typeenum self = Typeenum._create(input);
                mgr.behaviour_typeenum_All.put(self.id, self);
            }
        }

        @Override
        public void resolveAll(config.ConfigMgr mgr) {
            // no resolve
        }

    }

}
