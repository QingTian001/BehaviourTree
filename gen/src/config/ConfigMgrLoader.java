package config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConfigMgrLoader {

    public static configgen.genjava.SchemaInterface loadSchema(configgen.genjava.ConfigInput input) {
        // 1. 读取 Schema（如果有）
        int schemaLength = input.readInt();
        if (schemaLength <= 0) {
            return null;
        } else {
            return (configgen.genjava.SchemaInterface) configgen.genjava.SchemaDeserializer.deserialize(input);
        }
    }

    public static ConfigMgr load(configgen.genjava.ConfigInput input) {
        ConfigMgr mgr = new ConfigMgr();
        return load(mgr, input);
    }

    public static ConfigMgr load(ConfigMgr mgr, configgen.genjava.ConfigInput input) {
        // 2. 初始化 StringPool 和 LangTextPool
        input.readStringPool();
        input.readLangTextPool();

        // 3. 读取表数据
        int c = input.readInt();
        if (c < 3) {
            throw new IllegalArgumentException();
        }

        Map<String, ConfigLoader> allConfigLoaders = getAllConfigLoaders();
        for (int i = 0; i < c; i++) {
            String tableName = input.readString();
            int tableSize = input.readInt();
            ConfigLoader configLoader = allConfigLoaders.get(tableName);
            if (configLoader != null) {
                configLoader.createAll(mgr, input);
            } else {
                input.skipBytes(tableSize);
            }
        }

        for (var configLoader : allConfigLoaders.values()) {
            configLoader.resolveAll(mgr);
        }

        return mgr;
    }

    public static void loadPartialAndSetMgr(configgen.genjava.ConfigInput input, ConfigMgr oldMgr, Set<String> tableNames) {
        ConfigMgr newMgr = new ConfigMgr();
        newMgr.copyFrom(oldMgr);

        input.readStringPool();
        input.readLangTextPool();

        int c = input.readInt();

        Map<String, ConfigLoader> allConfigLoaders = getAllConfigLoaders();
        for (int i = 0; i < c; i++) {
            String tableName = input.readString();
            int tableSize = input.readInt();
            if (tableNames.contains(tableName)) {
                ConfigLoader configLoader = allConfigLoaders.get(tableName);
                if (configLoader != null) {
                    configLoader.createAll(newMgr, input);
                } else {
                    input.skipBytes(tableSize);
                }
            } else {
                input.skipBytes(tableSize);
            }
        }

        for (var configLoader : allConfigLoaders.values()) {
            configLoader.resolveAll(newMgr);
        }

        ConfigMgr.setMgr(newMgr);
    }

    public static void applySetAllRefs(ConfigMgr mgr) {
        config.behaviour.Nodeenum.setAllRefs(mgr);
    }

    public static Map<String, ConfigLoader> getAllConfigLoaders() {
        Map<String, ConfigLoader> allConfigLoaders = new LinkedHashMap<>();
        allConfigLoaders.put("behaviour.behaviour", new config.behaviour.Behaviour._ConfigLoader());
        allConfigLoaders.put("behaviour.nodeenum", new config.behaviour.Nodeenum_Detail._ConfigLoader());
        allConfigLoaders.put("behaviour.typeenum", new config.behaviour.Typeenum._ConfigLoader());
        return allConfigLoaders;
    }
}
