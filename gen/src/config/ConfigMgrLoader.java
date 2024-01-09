package config;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigMgrLoader {

    public static ConfigMgr load(configgen.genjava.ConfigInput input) {
        ConfigMgr mgr = new ConfigMgr();
        return load(mgr, input);
    }

    public static ConfigMgr load(ConfigMgr mgr, configgen.genjava.ConfigInput input) {
        int c = input.readInt();
        if (c < 3) {
            throw new IllegalArgumentException();
        }

        Map<String, ConfigLoader> allConfigLoaders = getAllConfigLoaders();
        for (int i = 0; i < c; i++) {
            String tableName = input.readStr();
            int tableSize = input.readInt();
            ConfigLoader configLoader = allConfigLoaders.get(tableName);
            if (configLoader != null) {
                configLoader.createAll(mgr, input);
            } else {
                input.skipBytes(tableSize);
            }
        }

        for (ConfigLoader configLoader : allConfigLoaders.values()) {
            configLoader.resolveAll(mgr);
        }

        return mgr;
    }

    private static Map<String, ConfigLoader> getAllConfigLoaders() {
        Map<String, ConfigLoader> allConfigLoaders = new LinkedHashMap<>();
        allConfigLoaders.put("behaviour.behaviour", new config.behaviour.Behaviour._ConfigLoader());
        allConfigLoaders.put("behaviour.nodeenum", new config.behaviour.Nodeenum_Detail._ConfigLoader());
        allConfigLoaders.put("behaviour.typeenum", new config.behaviour.Typeenum._ConfigLoader());

        return allConfigLoaders;
    }
}
