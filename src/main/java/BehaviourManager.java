import config.ConfigCodeSchema;
import config.ConfigMgr;
import config.ConfigMgrLoader;
import configgen.genjava.ConfigInput;
import configgen.genjava.Schema;
import configgen.genjava.SchemaCompatibleException;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BehaviourManager {

    public static void start() {
        // start log4j
        System.getProperties().putIfAbsent("log4j.configurationFile", "log4j2.xml");
        // load cfg
        load("config.data", new ConfigMgr());
    }

    public static void load(String configData, ConfigMgr configMgr) {
        try {
            Schema codeSchema = ConfigCodeSchema.getCodeSchema();
            try (ConfigInput input = new ConfigInput(new DataInputStream(new BufferedInputStream(new FileInputStream(configData))))) {
                Schema dataSchema = Schema.create(input);
                boolean compatible = codeSchema.compatible(dataSchema);
                if (compatible) {
                    ConfigMgr mgr = ConfigMgrLoader.load(configMgr, input);
                    ConfigMgr.setMgr(mgr);
                } else {
                    throw new RuntimeException("schema not compatible, ignore load configdata");
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        BehaviourManager.start();
    }
}
