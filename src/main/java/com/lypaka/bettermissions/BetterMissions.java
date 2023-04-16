package com.lypaka.bettermissions;

import com.lypaka.bettermissions.Config.ConfigConverter;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.Config.ConfigUpdater;
import com.lypaka.bettermissions.Missions.MissionRegistry;
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

    public static final String VERSION = "2.0.2";
    public static final String MOD_ID = "bettermissions";
    public static final String MOD_NAME = "BetterMissions";
    public static final Logger logger = LogManager.getLogger("BetterMissions");
    public static BasicConfigManager configManager;
    public static BasicConfigManager dummyConfigManager; /** Only used once for the auto-conversion between old system to new */
    public static PlayerConfigManager playerConfigManager;
    public static Map<String, ComplexConfigManager> missionConfigManager = new HashMap<>();
    public static Path dir;
    public static boolean disabled = false;

    public BetterMissions() throws IOException, ObjectMappingException {

        dir = ConfigUtils.checkDir(Paths.get("./config/bettermissions"));
        String[] files = new String[]{"bettermissions.conf", "missions-list.conf", "progression-log.conf", "itemRequirementExample.conf", "partyRequirementExample.conf"};
        configManager = new BasicConfigManager(files, dir, BetterMissions.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "player-accounts", dir, BetterMissions.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigUpdater.updateConfig();
        ConfigGetters.load();
        if (!ConfigGetters.converted) {

            disabled = true;
            String[] dummyFiles = new String[]{"catching.conf", "crafting.conf", "defeating.conf",
                    "evolving.conf", "fishing.conf", "killing.conf", "losing.conf", "melee.conf",
                    "mining.conf", "releasing.conf", "breeding.conf", "hatching.conf", "photographing.conf", "raiding.conf", "smelting.conf"};
            dummyConfigManager = new BasicConfigManager(dummyFiles, dir, BetterMissions.class, MOD_NAME, MOD_ID, logger);
            dummyConfigManager.init();
            ConfigConverter.convertOldToNew();

        } else {

            disabled = true; // disabling the mod until all missions are loaded
            ComplexConfigManager breedingConfigManager = new ComplexConfigManager(ConfigGetters.breedMissions, "breed-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            breedingConfigManager.init();
            missionConfigManager.put("Breed", breedingConfigManager);

            ComplexConfigManager hatchingConfigManager = new ComplexConfigManager(ConfigGetters.hatchMissions, "hatch-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            hatchingConfigManager.init();
            missionConfigManager.put("Hatch", hatchingConfigManager);

            ComplexConfigManager raidingConfigManager = new ComplexConfigManager(ConfigGetters.raidMissions, "raid-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            raidingConfigManager.init();
            missionConfigManager.put("Raid", raidingConfigManager);

            ComplexConfigManager photographingConfigManager = new ComplexConfigManager(ConfigGetters.photographMissions, "photograph-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            photographingConfigManager.init();
            missionConfigManager.put("Photograph", photographingConfigManager);

            ComplexConfigManager smeltingConfigManager = new ComplexConfigManager(ConfigGetters.smeltMissions, "smelt-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            smeltingConfigManager.init();
            missionConfigManager.put("Smelt", smeltingConfigManager);

            ComplexConfigManager catchingConfigManager = new ComplexConfigManager(ConfigGetters.catchMissions, "catch-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            catchingConfigManager.init();
            missionConfigManager.put("Catch", catchingConfigManager);

            ComplexConfigManager craftingConfigManager = new ComplexConfigManager(ConfigGetters.craftMissions, "craft-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            craftingConfigManager.init();
            missionConfigManager.put("Craft", craftingConfigManager);

            ComplexConfigManager defeatingConfigManager = new ComplexConfigManager(ConfigGetters.defeatMissions, "defeat-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            defeatingConfigManager.init();
            missionConfigManager.put("Defeat", defeatingConfigManager);

            ComplexConfigManager evolvingConfigManager = new ComplexConfigManager(ConfigGetters.evolveMissions, "evolve-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            evolvingConfigManager.init();
            missionConfigManager.put("Evolve", evolvingConfigManager);

            ComplexConfigManager fishingConfigManager = new ComplexConfigManager(ConfigGetters.fishMissions, "fish-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            fishingConfigManager.init();
            missionConfigManager.put("Fish", fishingConfigManager);

            ComplexConfigManager killingConfigManager = new ComplexConfigManager(ConfigGetters.killMissions, "kill-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            killingConfigManager.init();
            missionConfigManager.put("Kill", killingConfigManager);

            ComplexConfigManager losingConfigManager = new ComplexConfigManager(ConfigGetters.loseMissions, "lose-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            losingConfigManager.init();
            missionConfigManager.put("Lose", losingConfigManager);

            ComplexConfigManager meleeConfigManager = new ComplexConfigManager(ConfigGetters.meleeMissions, "melee-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            meleeConfigManager.init();
            missionConfigManager.put("Melee", meleeConfigManager);

            ComplexConfigManager miningConfigManager = new ComplexConfigManager(ConfigGetters.mineMissions, "mine-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            miningConfigManager.init();
            missionConfigManager.put("Mine", miningConfigManager);

            ComplexConfigManager releasingConfigManager = new ComplexConfigManager(ConfigGetters.releaseMissions, "release-missions", "mission-template.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
            releasingConfigManager.init();
            missionConfigManager.put("Release", releasingConfigManager);

            MissionRegistry.loadBreedMissions(true);

        }

    }

}
