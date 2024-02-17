package com.lypaka.bettermissions;

import com.lypaka.bettermissions.Missions.*;
import com.lypaka.bettermissions.Requirements.*;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static List<String> permanentMissionIDs = new ArrayList<>();

    public static boolean applies (Map<String, String> specs, Pokemon pokemon) {

        boolean applies = false;
        for (Map.Entry<String, String> entry : specs.entrySet()) {

            String spec = entry.getKey();
            String value = entry.getValue();
            if (spec.equalsIgnoreCase("species")) {

                if (pokemon.getSpecies().getName().equalsIgnoreCase(value) || value.equalsIgnoreCase("any")) {

                    if (!applies) applies = true;

                }

            }
            if (spec.equalsIgnoreCase("boss")) {

                boolean isBoss = Boolean.parseBoolean(value);
                if (isBoss && pokemon.getOrCreatePixelmon().isBossPokemon()) {

                    if (!applies) {

                        applies = true;

                    }

                } else if (!isBoss && !pokemon.getOrCreatePixelmon().isBossPokemon()) {

                    if (!applies) {

                        applies = true;

                    }

                }

            }
            if (spec.equalsIgnoreCase("shiny")) {

                boolean shiny = Boolean.parseBoolean(value);
                if (shiny && pokemon.isShiny()) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("ability")) {

                if (pokemon.getAbilityName().replace(" ", "").equalsIgnoreCase(value.replace(" ", ""))) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("nature")) {

                if (pokemon.getNature().getLocalizedName().equalsIgnoreCase(value)) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("level")) {

                int level = Integer.parseInt(value);
                if (pokemon.getPokemonLevel() == level) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("gender")) {

                Gender g = Gender.valueOf(value);
                if (pokemon.getGender() == g) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("growth")) {

                EnumGrowth g = EnumGrowth.valueOf(value);
                if (pokemon.getGrowth() == g) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("form")) {

                if (pokemon.getForm().getName().equalsIgnoreCase(value)) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("legendary")) {

                boolean isLegendary = Boolean.parseBoolean(value);
                if (isLegendary && pokemon.isLegendary(true)) {

                    if (!applies) {

                        applies = true;

                    }

                } else if (!isLegendary && !pokemon.isLegendary(true)) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("mythical")) {

                boolean isMythical = Boolean.parseBoolean(value);
                if (isMythical && pokemon.isMythical()) {

                    if (!applies) {

                        applies = true;

                    }

                } else if (!isMythical && !pokemon.isMythical()) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("ultrabeast")) {

                boolean isUltraBeast = Boolean.parseBoolean(value);
                if (isUltraBeast && pokemon.isUltraBeast()) {

                    if (!applies) {

                        applies = true;

                    }

                } else if (!isUltraBeast && !pokemon.isUltraBeast()) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("IsFromFishing")) {

                boolean isFromFishing = Boolean.parseBoolean(value);
                if (pokemon.getPersistentData().contains("IsFromFishing")) {

                    boolean fromFishing = Boolean.parseBoolean(pokemon.getPersistentData().getString("IsFromFishing"));
                    if (fromFishing && isFromFishing) {

                        if (!applies) {

                            applies = true;

                        }

                    } else if (!fromFishing && !isFromFishing) {

                        if (!applies) {

                            applies = true;

                        }

                    } else {

                        if (applies) {

                            applies = false;
                            break;

                        }

                    }

                } else {

                    if (applies) {

                        applies = false;
                        break;

                    }

                }

            }
            if (spec.equalsIgnoreCase("type")) {

                Element needed = Element.parseType(value);
                if (pokemon.getForm().getTypes().contains(needed)) {

                    if (!applies) {

                        applies = true;

                    }

                } else {

                    if (applies) {

                        applies = false;

                    }

                }

            }

        }

        return applies;

    }

    public static void putAllPermanentShitsInOneList() {

        permanentMissionIDs = new ArrayList<>();
        for (CatchMission mission : MissionRegistry.permanentCatchMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (CraftMission mission : MissionRegistry.permanentCraftMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (DefeatMission mission : MissionRegistry.permanentDefeatMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (EvolveMission mission : MissionRegistry.permanentEvolveMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (FishMission mission : MissionRegistry.permanentFishMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (KillMission mission : MissionRegistry.permanentKillMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (MeleeMission mission : MissionRegistry.permanentMeleeMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (MineMission mission : MissionRegistry.permanentMineMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (ReleaseMission mission : MissionRegistry.permanentReleaseMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }
        for (LoseMission mission : MissionRegistry.permanentLoseMissions) {

            permanentMissionIDs.add(mission.getCommandID());

        }

    }

    public static int getIndexFromMissionID (String type, String missionID) {

        int index = -1;
        List<String> files = new ArrayList<>();
        type = type.toLowerCase();
        ComplexConfigManager ccm = null;
        for (Map.Entry<String, ComplexConfigManager> entry : BetterMissions.missionConfigManager.entrySet()) {

            if (ccm != null) break;
            if (type.equalsIgnoreCase(entry.getKey())) {

                ccm = entry.getValue();
                switch (entry.getKey().toLowerCase()) {

                    case "catch":
                        files = ConfigGetters.catchMissions;
                        break;

                    case "craft":
                        files = ConfigGetters.craftMissions;
                        break;

                    case "defeat":
                        files = ConfigGetters.defeatMissions;
                        break;

                    case "evolve":
                        files = ConfigGetters.evolveMissions;
                        break;

                    case "fish":
                        files = ConfigGetters.fishMissions;
                        break;

                    case "kill":
                        files = ConfigGetters.killMissions;
                        break;

                    case "lose":
                        files = ConfigGetters.loseMissions;
                        break;

                    case "melee":
                        files = ConfigGetters.meleeMissions;
                        break;

                    case "mine":
                        files = ConfigGetters.mineMissions;
                        break;

                    case "photograph":
                        files = ConfigGetters.photographMissions;
                        break;

                    case "raid":
                        files = ConfigGetters.raidMissions;
                        break;

                    case "release":
                        files = ConfigGetters.releaseMissions;
                        break;

                    default:
                        files = ConfigGetters.smeltMissions;
                        break;

                }

            }

        }
        if (ccm == null) return index;
        for (int i = 0; i < files.size(); i++) {

            if (ccm.getConfigNode(i, "Mission-ID").getString().equalsIgnoreCase(missionID)) {

                index = i;
                break;

            }

        }

        return index;

    }

    public static void reloadAllMissionTypes() throws ObjectMappingException {

        MissionsHandler.loadMissions();

    }

    public static void reloadMissionType (String missionType) throws ObjectMappingException {

        BetterMissions.configManager.load();
        ConfigGetters.reloadMissions();
        ComplexConfigManager configManager = null;
        List<String> files = new ArrayList<>();
        switch (missionType.toLowerCase()) {

            case "breed":
                configManager = BetterMissions.missionConfigManager.get("Breed");
                files = ConfigGetters.breedMissions;
                break;

            case "catch":
                configManager = BetterMissions.missionConfigManager.get("Catch");
                files = ConfigGetters.catchMissions;
                break;

            case "craft":
                configManager = BetterMissions.missionConfigManager.get("Craft");
                files = ConfigGetters.craftMissions;
                break;

            case "defeat":
                configManager = BetterMissions.missionConfigManager.get("Defeat");
                files = ConfigGetters.defeatMissions;
                break;

            case "evolve":
                configManager = BetterMissions.missionConfigManager.get("Evolve");
                files = ConfigGetters.evolveMissions;
                break;

            case "fish":
                configManager = BetterMissions.missionConfigManager.get("Fish");
                files = ConfigGetters.fishMissions;
                break;

            case "hatch":
                configManager = BetterMissions.missionConfigManager.get("Hatch");
                files = ConfigGetters.hatchMissions;
                break;

            case "kill":
                configManager = BetterMissions.missionConfigManager.get("Kill");
                files = ConfigGetters.killMissions;
                break;

            case "lose":
                configManager = BetterMissions.missionConfigManager.get("Lose");
                files = ConfigGetters.loseMissions;
                break;

            case "melee":
                configManager = BetterMissions.missionConfigManager.get("Melee");
                files = ConfigGetters.meleeMissions;
                break;

            case "mine":
                configManager = BetterMissions.missionConfigManager.get("Mine");
                files = ConfigGetters.mineMissions;
                break;

            case "photograph":
                configManager = BetterMissions.missionConfigManager.get("Photograph");
                files = ConfigGetters.photographMissions;
                break;

            case "raid":
                configManager = BetterMissions.missionConfigManager.get("Raid");
                files = ConfigGetters.raidMissions;
                break;

            case "release":
                configManager = BetterMissions.missionConfigManager.get("Release");
                files = ConfigGetters.releaseMissions;
                break;

            case "smelt":
                configManager = BetterMissions.missionConfigManager.get("Smelt");
                files = ConfigGetters.smeltMissions;
                break;

        }

        if (configManager == null) return;

        configManager.setFileNames(files);
        configManager.load();
        /*switch (missionType.toLowerCase()) {

            case "breed":
                MissionRegistry.loadBreedMissions(false);
                break;

            case "catch":
                MissionRegistry.loadCatchMissions(false);
                break;

            case "craft":
                MissionRegistry.loadCraftMissions(false);
                break;

            case "defeat":
                MissionRegistry.loadDefeatMissions(false);
                break;

            case "evolve":
                MissionRegistry.loadEvolveMissions(false);
                break;

            case "fish":
                MissionRegistry.loadFishMissions(false);
                break;

            case "hatch":
                MissionRegistry.loadHatchMissions(false);
                break;

            case "kill":
                MissionRegistry.loadKillMissions(false);
                break;

            case "lose":
                MissionRegistry.loadLoseMissions(false);
                break;

            case "melee":
                MissionRegistry.loadMeleeMissions(false);
                break;

            case "mine":
                MissionRegistry.loadMineMissions(false);
                break;

            case "photograph":
                MissionRegistry.loadPhotographyMissions(false);
                break;

            case "raid":
                MissionRegistry.loadRaidMissions(false);
                break;

            case "release":
                MissionRegistry.loadReleaseMissions(false);
                break;

            case "smelt":
                MissionRegistry.loadSmeltMissions(false);

        }*/
        refreshMissionIDsList(missionType);

    }

    /** Used on reload of mission type to refresh list of mission IDs */
    public static void refreshMissionIDsList (String missionType) {

        ComplexConfigManager configManager = null;
        List<String> files = new ArrayList<>();
        switch (missionType.toLowerCase()) {

            case "breed":
                configManager = BetterMissions.missionConfigManager.get("Breed");
                files = ConfigGetters.breedMissions;
                break;

            case "catch":
                configManager = BetterMissions.missionConfigManager.get("Catch");
                files = ConfigGetters.catchMissions;
                break;

            case "craft":
                configManager = BetterMissions.missionConfigManager.get("Craft");
                files = ConfigGetters.craftMissions;
                break;

            case "defeat":
                configManager = BetterMissions.missionConfigManager.get("Defeat");
                files = ConfigGetters.defeatMissions;
                break;

            case "evolve":
                configManager = BetterMissions.missionConfigManager.get("Evolve");
                files = ConfigGetters.evolveMissions;
                break;

            case "fish":
                configManager = BetterMissions.missionConfigManager.get("Fish");
                files = ConfigGetters.fishMissions;
                break;

            case "hatch":
                configManager = BetterMissions.missionConfigManager.get("Hatch");
                files = ConfigGetters.hatchMissions;
                break;

            case "kill":
                configManager = BetterMissions.missionConfigManager.get("Kill");
                files = ConfigGetters.killMissions;
                break;

            case "lose":
                configManager = BetterMissions.missionConfigManager.get("Lose");
                files = ConfigGetters.loseMissions;
                break;

            case "melee":
                configManager = BetterMissions.missionConfigManager.get("Melee");
                files = ConfigGetters.meleeMissions;
                break;

            case "mine":
                configManager = BetterMissions.missionConfigManager.get("Mine");
                files = ConfigGetters.mineMissions;
                break;

            case "photograph":
                configManager = BetterMissions.missionConfigManager.get("Photograph");
                files = ConfigGetters.photographMissions;
                break;

            case "raid":
                configManager = BetterMissions.missionConfigManager.get("Raid");
                files = ConfigGetters.raidMissions;
                break;

            case "release":
                configManager = BetterMissions.missionConfigManager.get("Release");
                files = ConfigGetters.releaseMissions;
                break;

            case "smelt":
                configManager = BetterMissions.missionConfigManager.get("Smelt");
                files = ConfigGetters.smeltMissions;
                break;

        }

        if (configManager == null) return;

        List<String> currentMissionIDs = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {

            String commandID = configManager.getConfigNode(i, "Command-ID").getString();
            currentMissionIDs.add(commandID);

        }

        for (String id : currentMissionIDs) {

            if (!MissionRegistry.allMissionIDs.contains(id)) {

                MissionRegistry.allMissionIDs.add(id);

            }

        }

    }

    public static void createMission (String type, String missionID) throws ObjectMappingException {

        List<String> files;
        missionID = missionID.replace(" ", "");
        switch (type.toLowerCase()) {

            case "breed":
                files = ConfigGetters.breedMissions;
                break;

            case "catch":
                files = ConfigGetters.catchMissions;
                break;

            case "craft":
                files = ConfigGetters.craftMissions;
                break;

            case "defeat":
                files = ConfigGetters.defeatMissions;
                break;

            case "evolve":
                files = ConfigGetters.evolveMissions;
                break;

            case "fish":
                files = ConfigGetters.fishMissions;
                break;

            case "hatch":
                files = ConfigGetters.hatchMissions;
                break;

            case "kill":
                files = ConfigGetters.killMissions;
                break;

            case "lose":
                files = ConfigGetters.loseMissions;
                break;

            case "melee":
                files = ConfigGetters.meleeMissions;
                break;

            case "mine":
                files = ConfigGetters.mineMissions;
                break;

            case "photograph":
                files = ConfigGetters.photographMissions;
                break;

            case "raid":
                files = ConfigGetters.raidMissions;
                break;

            case "release":
                files = ConfigGetters.releaseMissions;
                break;

            case "smelt":
                files = ConfigGetters.smeltMissions;
                break;

            default:
                return;

        }

        if (!missionID.contains(".conf")) missionID = missionID + ".conf";
        files.add(missionID);
        reloadMissionType(type);

    }

    public static String getMissionIDFromCommandID (String commandID) {

        String missionID = null;
        for (BreedMission mission : MissionRegistry.permanentBreedMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (CatchMission mission : MissionRegistry.permanentCatchMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (CraftMission mission : MissionRegistry.permanentCraftMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (DefeatMission mission : MissionRegistry.permanentDefeatMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (EvolveMission mission : MissionRegistry.permanentEvolveMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (FishMission mission : MissionRegistry.permanentFishMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (HatchMission mission : MissionRegistry.permanentHatchMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (KillMission mission : MissionRegistry.permanentKillMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (LoseMission mission : MissionRegistry.permanentLoseMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (MeleeMission mission : MissionRegistry.permanentMeleeMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (MineMission mission : MissionRegistry.permanentMineMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (PhotographMission mission : MissionRegistry.permanentPhotographMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (RaidMission mission : MissionRegistry.permanentRaidMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (ReleaseMission mission : MissionRegistry.permanentReleaseMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }
        for (SmeltMission mission : MissionRegistry.permanentSmeltMissions) {

            if (mission.getCommandID().equalsIgnoreCase(commandID)) {

                missionID = mission.getID();
                break;

            }

        }

        return missionID;

    }

    public static boolean passesRequirements (ItemRequirement itemRequirement, PartyRequirement partyRequirement, PokedexRequirement pokedexRequirement, PermissionRequirement permissionRequirement,
                                              WeatherRequirement weatherRequirement) throws ObjectMappingException {

        if (!itemRequirement.passes()) return false;
        if (!partyRequirement.passes()) return false;
        if (!pokedexRequirement.passes()) return false;
        if (!permissionRequirement.passes()) return false;
        return weatherRequirement.passes();

    }

}
