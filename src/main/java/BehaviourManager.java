import behaviour.BehaviourFactory;
import config.ConfigCodeSchema;
import config.ConfigMgr;
import config.ConfigMgrLoader;
import configgen.genjava.ConfigInput;
import java.nio.file.Path;

public class BehaviourManager {
    public static void start() {
        System.getProperties().putIfAbsent("log4j.configurationFile", "log4j2.xml");
        load("config.data", new ConfigMgr());
    }

    public static void load(String configData, ConfigMgr configMgr) {
        try (ConfigInput input = new ConfigInput(Path.of(configData))) {
            var dataSchema = ConfigMgrLoader.loadSchema(input);
            if (dataSchema == null || !ConfigCodeSchema.getCodeSchema().compatible(dataSchema)) {
                throw new IllegalArgumentException("配置数据与代码的 schema 不兼容，请重新运行 genconfig.bat");
            }
            ConfigMgr mgr = ConfigMgrLoader.load(configMgr, input);
            ConfigMgr.setMgr(mgr);
            BehaviourFactory.start();
            BehaviourFactory.preloadBehaviourTrees(mgr.allBehaviourBehaviour());
        } catch (java.io.IOException e) {
            throw new RuntimeException("加载行为树配置失败: " + configData, e);
        }
    }

    public static void main(String[] args) { start(); }
}
