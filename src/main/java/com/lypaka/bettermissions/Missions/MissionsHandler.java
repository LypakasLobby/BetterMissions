package com.lypaka.bettermissions.Missions;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.Requirements.MissionRequirement;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionsHandler {

    private static Map<String, List<Mission>> missionsMap;

    /*
    Craft, Smelt, Mine need lists of item IDs

    Breed, Catch, Evolve, Fish, Hatch, Kill, Photograph, Raid, and Release need Pokemon spec maps
     */

    public static void loadMissions() throws ObjectMappingException {

        BetterMissions.logger.info("Loading missions, please wait...");
        missionsMap = new HashMap<>();

        boolean generateDefault = BetterMissions.configManager.getConfigNode(2, "Enabled").getBoolean();
        if (generateDefault) {

            generateDefaultMissions();

        }

        ComplexConfigManager breedingConfigManager = new ComplexConfigManager(ConfigGetters.breedMissions, "breed-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        breedingConfigManager.init();
        BetterMissions.missionConfigManager.put("Breed", breedingConfigManager);

        for (int i = 0; i < ConfigGetters.breedMissions.size(); i++) {



        }

        ComplexConfigManager catchingConfigManager = new ComplexConfigManager(ConfigGetters.catchMissions, "catch-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        catchingConfigManager.init();
        BetterMissions.missionConfigManager.put("Catch", catchingConfigManager);

        ComplexConfigManager craftingConfigManager = new ComplexConfigManager(ConfigGetters.craftMissions, "craft-missions", "mission-template-items.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        craftingConfigManager.init();
        BetterMissions.missionConfigManager.put("Craft", craftingConfigManager);

        ComplexConfigManager defeatingConfigManager = new ComplexConfigManager(ConfigGetters.defeatMissions, "defeat-missions", "mission-template-locations.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        defeatingConfigManager.init();
        BetterMissions.missionConfigManager.put("Defeat", defeatingConfigManager);

        ComplexConfigManager evolvingConfigManager = new ComplexConfigManager(ConfigGetters.evolveMissions, "evolve-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        evolvingConfigManager.init();
        BetterMissions.missionConfigManager.put("Evolve", evolvingConfigManager);

        ComplexConfigManager fishingConfigManager = new ComplexConfigManager(ConfigGetters.fishMissions, "fish-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        fishingConfigManager.init();
        BetterMissions.missionConfigManager.put("Fish", fishingConfigManager);

        ComplexConfigManager hatchingConfigManager = new ComplexConfigManager(ConfigGetters.hatchMissions, "hatch-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        hatchingConfigManager.init();
        BetterMissions.missionConfigManager.put("Hatch", hatchingConfigManager);

        ComplexConfigManager killingConfigManager = new ComplexConfigManager(ConfigGetters.killMissions, "kill-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        killingConfigManager.init();
        BetterMissions.missionConfigManager.put("Kill", killingConfigManager);

        ComplexConfigManager losingConfigManager = new ComplexConfigManager(ConfigGetters.loseMissions, "lose-missions", "mission-template-losing.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        losingConfigManager.init();
        BetterMissions.missionConfigManager.put("Lose", losingConfigManager);

        ComplexConfigManager meleeConfigManager = new ComplexConfigManager(ConfigGetters.meleeMissions, "melee-missions", "mission-template-melee.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        meleeConfigManager.init();
        BetterMissions.missionConfigManager.put("Melee", meleeConfigManager);

        ComplexConfigManager miningConfigManager = new ComplexConfigManager(ConfigGetters.mineMissions, "mine-missions", "mission-template-blocks.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        miningConfigManager.init();
        BetterMissions.missionConfigManager.put("Mine", miningConfigManager);

        ComplexConfigManager photographingConfigManager = new ComplexConfigManager(ConfigGetters.photographMissions, "photograph-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        photographingConfigManager.init();
        BetterMissions.missionConfigManager.put("Photograph", photographingConfigManager);

        ComplexConfigManager raidingConfigManager = new ComplexConfigManager(ConfigGetters.raidMissions, "raid-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        raidingConfigManager.init();
        BetterMissions.missionConfigManager.put("Raid", raidingConfigManager);

        ComplexConfigManager releasingConfigManager = new ComplexConfigManager(ConfigGetters.releaseMissions, "release-missions", "mission-template-specs.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        releasingConfigManager.init();
        BetterMissions.missionConfigManager.put("Release", releasingConfigManager);

        ComplexConfigManager smeltingConfigManager = new ComplexConfigManager(ConfigGetters.smeltMissions, "smelt-missions", "mission-template-items.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        smeltingConfigManager.init();
        BetterMissions.missionConfigManager.put("Smelt", smeltingConfigManager);



    }

    private static void generateDefaultMissions() throws ObjectMappingException {

        /** BREED */
        Map<String, Map<String, String>> breedMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : breedMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Timer").getInt();
            BreedMission breedMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Reward", "Value").getInt();
                breedMission = new BreedMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Breed", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                breedMission = new BreedMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Breed")) {

                missions = missionsMap.get("Breed");

            }
            missions.add(breedMission);
            missionsMap.put("Breed", missions);

        }

        /** CATCH */
        Map<String, Map<String, String>> catchMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : catchMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Timer").getInt();
            CatchMission catchMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Reward", "Value").getInt();
                catchMission = new CatchMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Catch", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                catchMission = new CatchMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Catch")) {

                missions = missionsMap.get("Catch");

            }
            missions.add(catchMission);
            missionsMap.put("Catch", missions);

        }

        /** CRAFT */
        Map<String, Map<String, String>> craftMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : craftMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Reward", "Type").getString();
            List<String> ids = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Items").getList(TypeToken.of(String.class));
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Timer").getInt();
            CraftMission craftMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Reward", "Value").getInt();
                craftMission = new CraftMission(missionEntry, amount, chance, displayName, commandID, ids, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Craft", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                craftMission = new CraftMission(missionEntry, amount, chance, displayName, commandID, ids, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Craft")) {

                missions = missionsMap.get("Craft");

            }
            missions.add(craftMission);
            missionsMap.put("Craft", missions);

        }

        /** DEFEAT */
        Map<String, Map<String, String>> defeatMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : defeatMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Reward", "Type").getString();
            List<String> locations = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Locations").getList(TypeToken.of(String.class));
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Timer").getInt();
            DefeatMission defeatMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Reward", "Value").getInt();
                defeatMission = new DefeatMission(missionEntry, amount, chance, displayName, commandID, locations, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Defeat", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                defeatMission = new DefeatMission(missionEntry, amount, chance, displayName, commandID, locations, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Defeat")) {

                missions = missionsMap.get("Defeat");

            }
            missions.add(defeatMission);
            missionsMap.put("Defeat", missions);

        }

        /** EVOLVE */
        Map<String, Map<String, String>> evolveMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : evolveMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Timer").getInt();
            EvolveMission evolveMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Reward", "Value").getInt();
                evolveMission = new EvolveMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Evolve", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                evolveMission = new EvolveMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Evolve")) {

                missions = missionsMap.get("Evolve");

            }
            missions.add(evolveMission);
            missionsMap.put("Evolve", missions);

        }

        /** FISH */
        Map<String, Map<String, String>> fishMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : fishMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Timer").getInt();
            FishMission fishMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Reward", "Value").getInt();
                fishMission = new FishMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Fish", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                fishMission = new FishMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Fish")) {

                missions = missionsMap.get("Fish");

            }
            missions.add(fishMission);
            missionsMap.put("Fish", missions);

        }

        /** HATCH */
        Map<String, Map<String, String>> hatchMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : hatchMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Timer").getInt();
            HatchMission hatchMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Reward", "Value").getInt();
                hatchMission = new HatchMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Hatch", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                hatchMission = new HatchMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Hatch")) {

                missions = missionsMap.get("Hatch");

            }
            missions.add(hatchMission);
            missionsMap.put("Hatch", missions);

        }

        /** KILL */
        Map<String, Map<String, String>> killMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : killMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Timer").getInt();
            KillMission killMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Reward", "Value").getInt();
                killMission = new KillMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Kill", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                killMission = new KillMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Kill")) {

                missions = missionsMap.get("Kill");

            }
            missions.add(killMission);
            missionsMap.put("Kill", missions);

        }

        /** LOSE */
        Map<String, Map<String, String>> loseMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : loseMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Reward", "Type").getString();
            String entityType = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Entity-Type").getString();
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Timer").getInt();
            LoseMission loseMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Reward", "Value").getInt();
                loseMission = new LoseMission(missionEntry, amount, chance, displayName, entityType, commandID, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Lose", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                loseMission = new LoseMission(missionEntry, amount, chance, displayName, entityType, commandID, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Lose")) {

                missions = missionsMap.get("Lose");

            }
            missions.add(loseMission);
            missionsMap.put("Lose", missions);

        }

        /** MELEE */
        Map<String, Map<String, String>> meleeMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : meleeMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> entities = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Entities").getList(TypeToken.of(String.class));
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Reward", "Type").getString();
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Timer").getInt();
            MeleeMission meleeMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Reward", "Value").getInt();
                meleeMission = new MeleeMission(missionEntry, amount, chance, displayName, commandID, entities, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Melee", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                meleeMission = new MeleeMission(missionEntry, amount, chance, displayName, commandID, entities, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Melee")) {

                missions = missionsMap.get("Melee");

            }
            missions.add(meleeMission);
            missionsMap.put("Melee", missions);

        }

        /** MINE */
        Map<String, Map<String, String>> mineMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : mineMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> blockTypes = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Blocks").getList(TypeToken.of(String.class));
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Reward", "Type").getString();
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Timer").getInt();
            MineMission mineMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Reward", "Value").getInt();
                mineMission = new MineMission(missionEntry, amount, chance, blockTypes, displayName, commandID, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Mine", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                mineMission = new MineMission(missionEntry, amount, chance, blockTypes, displayName, commandID, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Mine")) {

                missions = missionsMap.get("Mine");

            }
            missions.add(mineMission);
            missionsMap.put("Mine", missions);

        }

        /** PHOTOGRAPH */
        Map<String, Map<String, String>> photographMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : photographMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Timer").getInt();
            PhotographMission photographMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Reward", "Value").getInt();
                photographMission = new PhotographMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Photograph", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                photographMission = new PhotographMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Photograph")) {

                missions = missionsMap.get("Photograph");

            }
            missions.add(photographMission);
            missionsMap.put("Photograph", missions);

        }

        /** RAID */
        Map<String, Map<String, String>> raidMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : raidMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Timer").getInt();
            RaidMission raidMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Reward", "Value").getInt();
                raidMission = new RaidMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Raid", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                raidMission = new RaidMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Raid")) {

                missions = missionsMap.get("Raid");

            }
            missions.add(raidMission);
            missionsMap.put("Raid", missions);

        }

        /** RELEASE */
        Map<String, Map<String, String>> releaseMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Release").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : releaseMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Reward", "Type").getString();
            Map<String, String> specs = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Timer").getInt();
            ReleaseMission releaseMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Reward", "Value").getInt();
                releaseMission = new ReleaseMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Release", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                releaseMission = new ReleaseMission(missionEntry, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Release")) {

                missions = missionsMap.get("Release");

            }
            missions.add(releaseMission);
            missionsMap.put("Release", missions);

        }

        /** SMELT */
        Map<String, Map<String, String>> smeltMap = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : smeltMap.entrySet()) {

            String missionEntry = entry.getKey();
            Map<String, String> data = entry.getValue();
            int amount = Integer.parseInt(data.get("Amount"));
            double chance = Double.parseDouble(data.get("Chance"));
            String displayName = data.get("Display-Name");
            String commandID = data.get("ID");
            List<String> items = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Items").getList(TypeToken.of(String.class));
            List<String> displayLore = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Lore").getList(TypeToken.of(String.class));
            Map<String, Map<String, String>> itemRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            Map<String, List<String>> permissionRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});
            Map<String, List<String>> timeRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});
            List<String> weatherRequirements = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
            String rewardType = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Reward", "Type").getString();
            int timer = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Timer").getInt();
            SmeltMission smeltMission;
            if (!BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Reward", "Value").isVirtual()) {

                int reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Reward", "Value").getInt();
                smeltMission = new SmeltMission(missionEntry, amount, chance, displayName, commandID, items, displayLore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = BetterMissions.configManager.getConfigNode(2, "Missions", "Smelt", missionEntry, "Reward", "Commands").getList(TypeToken.of(String.class));
                smeltMission = new SmeltMission(missionEntry, amount, chance, displayName, commandID, items, displayLore, rewardType, reward, requirements, timer);

            }
            List<Mission> missions = new ArrayList<>();
            if (missionsMap.containsKey("Smelt")) {

                missions = missionsMap.get("Smelt");

            }
            missions.add(smeltMission);
            missionsMap.put("Smelt", missions);

        }

    }

}
