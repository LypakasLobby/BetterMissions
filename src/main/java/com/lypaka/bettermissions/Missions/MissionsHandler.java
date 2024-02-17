package com.lypaka.bettermissions.Missions;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.ConfigGetters;
import com.lypaka.bettermissions.Requirements.MissionRequirement;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionsHandler {

    /*
    Craft, Smelt, Mine need lists of item IDs

    Breed, Catch, Evolve, Fish, Hatch, Kill, Photograph, Raid, and Release need Pokemon spec maps
     */

    public static void loadMissions() throws ObjectMappingException {

        Map<String, Integer> missionCountMap = new HashMap<>();
        Path dir = ConfigUtils.checkDir(Paths.get("./config/bettermissions/missions"));
        BetterMissions.logger.info("Loading missions, please wait...");

        ComplexConfigManager breedingConfigManager = new ComplexConfigManager(ConfigGetters.breedMissions, "breed-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        breedingConfigManager.init();
        BetterMissions.missionConfigManager.put("Breed", breedingConfigManager);
        int count = 0;
        for (int index = 0; index < ConfigGetters.breedMissions.size(); index++) {

            int amount = breedingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = breedingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = breedingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = breedingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = breedingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = breedingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = breedingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = breedingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = breedingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = breedingConfigManager.getConfigNode(index, "Timer").getInt();
            BreedMission breedMission;
            if (!breedingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = breedingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                breedMission = new BreedMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = breedingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                breedMission = new BreedMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            breedMission.register();
            count++;

        }
        missionCountMap.put("Breed", count);
        count = 0;

        ComplexConfigManager catchingConfigManager = new ComplexConfigManager(ConfigGetters.catchMissions, "catch-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        catchingConfigManager.init();
        BetterMissions.missionConfigManager.put("Catch", catchingConfigManager);
        for (int index = 0; index < ConfigGetters.catchMissions.size(); index++) {

            int amount = catchingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = catchingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = catchingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = catchingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = catchingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = catchingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = catchingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = catchingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = catchingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = catchingConfigManager.getConfigNode(index, "Timer").getInt();
            CatchMission catchMission;
            if (!catchingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = catchingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                catchMission = new CatchMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = catchingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                catchMission = new CatchMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            catchMission.register();
            count++;

        }
        missionCountMap.put("Catch", count);
        count = 0;

        ComplexConfigManager craftingConfigManager = new ComplexConfigManager(ConfigGetters.craftMissions, "craft-missions", "mission-template-items.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        craftingConfigManager.init();
        BetterMissions.missionConfigManager.put("Craft", craftingConfigManager);
        for (int index = 0; index < ConfigGetters.craftMissions.size(); index++) {

            int amount = craftingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = craftingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = craftingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = craftingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = craftingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            List<String> itemIDs = craftingConfigManager.getConfigNode(index, "Items").getList(TypeToken.of(String.class));
            String missionID = craftingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = craftingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = craftingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = craftingConfigManager.getConfigNode(index, "Timer").getInt();
            CraftMission craftMission;
            if (!craftingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = craftingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                craftMission = new CraftMission(missionID, amount, chance, displayName, commandID, lore, itemIDs, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = craftingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                craftMission = new CraftMission(missionID, amount, chance, displayName, commandID, lore, itemIDs, rewardType, reward, requirements, timer);

            }
            craftMission.register();
            count++;

        }
        missionCountMap.put("Craft", count);
        count = 0;

        ComplexConfigManager defeatingConfigManager = new ComplexConfigManager(ConfigGetters.defeatMissions, "defeat-missions", "mission-template-locations.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        defeatingConfigManager.init();
        BetterMissions.missionConfigManager.put("Defeat", defeatingConfigManager);
        for (int index = 0; index < ConfigGetters.defeatMissions.size(); index++) {

            int amount = defeatingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = defeatingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = defeatingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = defeatingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = defeatingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            List<String> locations = defeatingConfigManager.getConfigNode(index, "Locations").getList(TypeToken.of(String.class));
            String missionID = defeatingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = defeatingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = defeatingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = defeatingConfigManager.getConfigNode(index, "Timer").getInt();
            DefeatMission defeatMission;
            if (!defeatingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = defeatingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                defeatMission = new DefeatMission(missionID, amount, chance, displayName, commandID, locations, lore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = defeatingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                defeatMission = new DefeatMission(missionID, amount, chance, displayName, commandID, locations, lore, rewardType, reward, requirements, timer);

            }
            defeatMission.register();
            count++;

        }
        missionCountMap.put("Defeat", count);
        count = 0;

        ComplexConfigManager evolvingConfigManager = new ComplexConfigManager(ConfigGetters.evolveMissions, "evolve-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        evolvingConfigManager.init();
        BetterMissions.missionConfigManager.put("Evolve", evolvingConfigManager);
        for (int index = 0; index < ConfigGetters.evolveMissions.size(); index++) {

            int amount = evolvingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = evolvingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = evolvingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = evolvingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = evolvingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = evolvingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = evolvingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = evolvingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = evolvingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = evolvingConfigManager.getConfigNode(index, "Timer").getInt();
            EvolveMission evolveMission;
            if (!evolvingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = evolvingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                evolveMission = new EvolveMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = evolvingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                evolveMission = new EvolveMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            evolveMission.register();
            count++;

        }
        missionCountMap.put("Evolve", count);
        count = 0;

        ComplexConfigManager fishingConfigManager = new ComplexConfigManager(ConfigGetters.fishMissions, "fish-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        fishingConfigManager.init();
        BetterMissions.missionConfigManager.put("Fish", fishingConfigManager);
        for (int index = 0; index < ConfigGetters.fishMissions.size(); index++) {

            int amount = fishingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = fishingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = fishingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = fishingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = fishingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = fishingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = fishingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = fishingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = fishingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = fishingConfigManager.getConfigNode(index, "Timer").getInt();
            FishMission fishMission;
            if (!fishingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = fishingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                fishMission = new FishMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = fishingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                fishMission = new FishMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            fishMission.register();
            count++;

        }
        missionCountMap.put("Fish", count);
        count = 0;

        ComplexConfigManager hatchingConfigManager = new ComplexConfigManager(ConfigGetters.hatchMissions, "hatch-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        hatchingConfigManager.init();
        BetterMissions.missionConfigManager.put("Hatch", hatchingConfigManager);
        for (int index = 0; index < ConfigGetters.hatchMissions.size(); index++) {

            int amount = hatchingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = hatchingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = hatchingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = hatchingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = hatchingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = hatchingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = hatchingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = hatchingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = hatchingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = hatchingConfigManager.getConfigNode(index, "Timer").getInt();
            HatchMission hatchMission;
            if (!hatchingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = hatchingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                hatchMission = new HatchMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = hatchingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                hatchMission = new HatchMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            hatchMission.register();
            count++;

        }
        missionCountMap.put("Hatch", count);
        count = 0;

        ComplexConfigManager killingConfigManager = new ComplexConfigManager(ConfigGetters.killMissions, "kill-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        killingConfigManager.init();
        BetterMissions.missionConfigManager.put("Kill", killingConfigManager);
        for (int index = 0; index < ConfigGetters.killMissions.size(); index++) {

            int amount = killingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = killingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = killingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = killingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = killingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = killingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = killingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = killingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = killingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = killingConfigManager.getConfigNode(index, "Timer").getInt();
            KillMission killMission;
            if (!killingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = killingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                killMission = new KillMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = killingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                killMission = new KillMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            killMission.register();
            count++;

        }
        missionCountMap.put("Kill", count);
        count = 0;

        ComplexConfigManager losingConfigManager = new ComplexConfigManager(ConfigGetters.loseMissions, "lose-missions", "mission-template-losing.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        losingConfigManager.init();
        BetterMissions.missionConfigManager.put("Lose", losingConfigManager);
        for (int index = 0; index < ConfigGetters.loseMissions.size(); index++) {

            int amount = losingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = losingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = losingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = losingConfigManager.getConfigNode(index, "Display-Name").getString();
            String entityType = losingConfigManager.getConfigNode(index, "Entity-Type").getString();
            List<String> lore = losingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = losingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = losingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = losingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = losingConfigManager.getConfigNode(index, "Timer").getInt();
            LoseMission loseMission;
            if (!losingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = losingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                loseMission = new LoseMission(missionID, amount, chance, displayName, entityType, commandID, lore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = losingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                loseMission = new LoseMission(missionID, amount, chance, displayName, entityType, commandID, lore, rewardType, reward, requirements, timer);

            }
            loseMission.register();
            count++;

        }
        missionCountMap.put("Lose", count);
        count = 0;

        ComplexConfigManager meleeConfigManager = new ComplexConfigManager(ConfigGetters.meleeMissions, "melee-missions", "mission-template-melee.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        meleeConfigManager.init();
        BetterMissions.missionConfigManager.put("Melee", meleeConfigManager);
        for (int index = 0; index < ConfigGetters.meleeMissions.size(); index++) {

            int amount = meleeConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = meleeConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = meleeConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = meleeConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> entityIDs = meleeConfigManager.getConfigNode(index, "Entities").getList(TypeToken.of(String.class));
            List<String> lore = meleeConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = meleeConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = meleeConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = meleeConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = meleeConfigManager.getConfigNode(index, "Timer").getInt();
            MeleeMission meleeMission;
            if (!meleeConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = meleeConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                meleeMission = new MeleeMission(missionID, amount, chance, displayName, commandID, entityIDs, lore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = meleeConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                meleeMission = new MeleeMission(missionID, amount, chance, displayName, commandID, entityIDs, lore, rewardType, reward, requirements, timer);

            }
            meleeMission.register();
            count++;

        }
        missionCountMap.put("Melee", count);
        count = 0;

        ComplexConfigManager miningConfigManager = new ComplexConfigManager(ConfigGetters.mineMissions, "mine-missions", "mission-template-blocks.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        miningConfigManager.init();
        BetterMissions.missionConfigManager.put("Mine", miningConfigManager);
        for (int index = 0; index < ConfigGetters.mineMissions.size(); index++) {

            int amount = miningConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = miningConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = miningConfigManager.getConfigNode(index, "Command-ID").getString();
            List<String> blockIDs = miningConfigManager.getConfigNode(index, "Blocks").getList(TypeToken.of(String.class));
            String displayName = miningConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = miningConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = miningConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = miningConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = miningConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = miningConfigManager.getConfigNode(index, "Timer").getInt();
            MineMission mineMission;
            if (!miningConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = miningConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                mineMission = new MineMission(missionID, amount, chance, blockIDs, displayName, commandID, lore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = miningConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                mineMission = new MineMission(missionID, amount, chance, blockIDs, displayName, commandID, lore, rewardType, reward, requirements, timer);

            }
            mineMission.register();
            count++;

        }
        missionCountMap.put("Mine", count);
        count = 0;

        ComplexConfigManager photographingConfigManager = new ComplexConfigManager(ConfigGetters.photographMissions, "photograph-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        photographingConfigManager.init();
        BetterMissions.missionConfigManager.put("Photograph", photographingConfigManager);
        for (int index = 0; index < ConfigGetters.photographMissions.size(); index++) {

            int amount = photographingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = photographingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = photographingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = photographingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = photographingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = photographingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = photographingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = photographingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = photographingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = photographingConfigManager.getConfigNode(index, "Timer").getInt();
            PhotographMission photographMission;
            if (!photographingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = photographingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                photographMission = new PhotographMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = photographingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                photographMission = new PhotographMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            photographMission.register();
            count++;

        }
        missionCountMap.put("Photograph", count);
        count = 0;

        ComplexConfigManager raidingConfigManager = new ComplexConfigManager(ConfigGetters.raidMissions, "raid-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        raidingConfigManager.init();
        BetterMissions.missionConfigManager.put("Raid", raidingConfigManager);
        for (int index = 0; index < ConfigGetters.raidMissions.size(); index++) {

            int amount = raidingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = raidingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = raidingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = raidingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = raidingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = raidingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = raidingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = raidingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = raidingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = raidingConfigManager.getConfigNode(index, "Timer").getInt();
            RaidMission raidMission;
            if (!raidingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = raidingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                raidMission = new RaidMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = raidingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                raidMission = new RaidMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            raidMission.register();
            count++;

        }
        missionCountMap.put("Raid", count);
        count = 0;

        ComplexConfigManager releasingConfigManager = new ComplexConfigManager(ConfigGetters.releaseMissions, "release-missions", "mission-template-specs.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        releasingConfigManager.init();
        BetterMissions.missionConfigManager.put("Release", releasingConfigManager);
        for (int index = 0; index < ConfigGetters.releaseMissions.size(); index++) {

            int amount = releasingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = releasingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = releasingConfigManager.getConfigNode(index, "Command-ID").getString();
            String displayName = releasingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = releasingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = releasingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = releasingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = releasingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            Map<String, String> specs = releasingConfigManager.getConfigNode(index, "Specs").getValue(new TypeToken<Map<String, String>>() {});
            int timer = releasingConfigManager.getConfigNode(index, "Timer").getInt();
            ReleaseMission releaseMission;
            if (!releasingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = releasingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                releaseMission = new ReleaseMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            } else {

                List<String> reward = releasingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                releaseMission = new ReleaseMission(missionID, amount, chance, displayName, commandID, lore, rewardType, reward, requirements, specs, timer);

            }
            releaseMission.register();
            count++;

        }
        missionCountMap.put("Release", count);
        count = 0;

        ComplexConfigManager smeltingConfigManager = new ComplexConfigManager(ConfigGetters.smeltMissions, "smelt-missions", "mission-template-items.conf", dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        smeltingConfigManager.init();
        BetterMissions.missionConfigManager.put("Smelt", smeltingConfigManager);
        for (int index = 0; index < ConfigGetters.smeltMissions.size(); index++) {

            int amount = smeltingConfigManager.getConfigNode(index, "Amount").getInt();
            double chance = smeltingConfigManager.getConfigNode(index, "Chance").getDouble();
            String commandID = smeltingConfigManager.getConfigNode(index, "Command-ID").getString();
            List<String> itemIDs = smeltingConfigManager.getConfigNode(index, "Items").getList(TypeToken.of(String.class));
            String displayName = smeltingConfigManager.getConfigNode(index, "Display-Name").getString();
            List<String> lore = smeltingConfigManager.getConfigNode(index, "Lore").getList(TypeToken.of(String.class));
            String missionID = smeltingConfigManager.getConfigNode(index, "Mission-ID").getString();
            Map<String, Map<String, String>> itemRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            Map<String, Map<String, String>> partyRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            List<String> pokedexRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Pokedex").getList(TypeToken.of(String.class));
            List<String> doesNotHavePermissionRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));
            List<String> hasPermissionRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));
            List<String> weatherRequirements = smeltingConfigManager.getConfigNode(index, "Requirements", "Weather").getList(TypeToken.of(String.class));
            MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, doesNotHavePermissionRequirements, hasPermissionRequirements, weatherRequirements);
            String rewardType = smeltingConfigManager.getConfigNode(index, "Reward", "Type").getString();
            int timer = smeltingConfigManager.getConfigNode(index, "Timer").getInt();
            SmeltMission smeltMission;
            if (!smeltingConfigManager.getConfigNode(index, "Reward", "Value").isVirtual()) {

                int reward = smeltingConfigManager.getConfigNode(index, "Reward", "Value").getInt();
                smeltMission = new SmeltMission(missionID, amount, chance, displayName, commandID, itemIDs, lore, rewardType, reward, requirements, timer);

            } else {

                List<String> reward = smeltingConfigManager.getConfigNode(index, "Reward", "Commands").getList(TypeToken.of(String.class));
                smeltMission = new SmeltMission(missionID, amount, chance, displayName, commandID, itemIDs, lore, rewardType, reward, requirements, timer);

            }
            smeltMission.register();
            count++;

        }
        missionCountMap.put("Smelt", count);


        for (Map.Entry<String, Integer> entry : missionCountMap.entrySet()) {

            BetterMissions.logger.info("Successfully loaded " + entry.getValue() + " " + entry.getKey() + " mission(s).");

        }
        BetterMissions.logger.info("Finished loading all missions!");

    }

}
