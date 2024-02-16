package com.lypaka.bettermissions;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.ConfigurationLoaders.PlayerConfigManager;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("bettermissions")
public class BetterMissions {

    public static final String MOD_ID = "bettermissions";
    public static final String MOD_NAME = "BetterMissions";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static PlayerConfigManager playerConfigManager;
    public static Map<String, ComplexConfigManager> missionConfigManager = new HashMap<>();
    public static Path dir;
    public static boolean disabled = false;

    public BetterMissions() throws IOException, ObjectMappingException {

        dir = ConfigUtils.checkDir(Paths.get("./config/bettermissions"));
        String[] files = new String[]{"bettermissions.conf", "missions-list.conf", "exampleMissions.conf", "itemRequirementExample.conf", "partyRequirementExample.conf"};
        configManager = new BasicConfigManager(files, dir, BetterMissions.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "player-accounts", dir, BetterMissions.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigGetters.load();

    }

}
