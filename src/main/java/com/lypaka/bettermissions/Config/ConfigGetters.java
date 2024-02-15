package com.lypaka.bettermissions.Config;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.BetterMissions;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean autoCycleMissions;
    public static String completionBroadcast;
    public static boolean converted;
    public static int rerollCost;
    public static String guiBorderID;
    public static String missionsMenuDisplayName;
    public static boolean menuEnabled;
    public static Map<String, String> missionItemRepresentativeMap;
    public static String missionRerollDisplayName;
    public static boolean rerollsEnabled;
    public static String missionRerollItemID;
    public static List<String> rerollLore;
    public static String newMissionNotification;
    public static int updateInterval;

    public static List<String> breedMissions;
    public static List<String> catchMissions;
    public static List<String> craftMissions;
    public static List<String> defeatMissions;
    public static List<String> evolveMissions;
    public static List<String> fishMissions;
    public static List<String> hatchMissions;
    public static List<String> killMissions;
    public static List<String> loseMissions;
    public static List<String> meleeMissions;
    public static List<String> mineMissions;
    public static List<String> photographMissions;
    public static List<String> raidMissions;
    public static List<String> releaseMissions;
    public static List<String> smeltMissions;

    public static void load() throws ObjectMappingException {

        boolean save = false;
        if (BetterMissions.configManager.getConfigNode(0, "Auto-Cycle-Missions").isVirtual()) {

            save = true;
            BetterMissions.configManager.getConfigNode(0, "Auto-Cycle-Missions").setValue(true);

        }
        if (BetterMissions.configManager.getConfigNode(0, "Converted").isVirtual()) {

            if (!save) save = true;
            BetterMissions.configManager.getConfigNode(0, "Converted").setValue(false);
            BetterMissions.configManager.getConfigNode(0, "Converted").setComment("DON'T touch this, if you do, your shit could get wiped.");

        }
        if (BetterMissions.configManager.getConfigNode(0, "Show-Loading-Messages").isVirtual()) {

            if (!save) save = true;
            BetterMissions.configManager.getConfigNode(0, "Show-Loading-Messages").setValue(false);
            BetterMissions.configManager.getConfigNode(0, "Show-Loading-Messages").setComment("Set this to true to be able to see console successfully loading mission files");

        }
        if (save) {

            BetterMissions.configManager.save();

        }
        autoCycleMissions = BetterMissions.configManager.getConfigNode(0, "Auto-Cycle-Missions").getBoolean();
        completionBroadcast = BetterMissions.configManager.getConfigNode(0, "Completion-Broadcast").getString();
        converted = BetterMissions.configManager.getConfigNode(0, "Converted").getBoolean();
        rerollCost = BetterMissions.configManager.getConfigNode(0, "Cost").getInt();
        guiBorderID = BetterMissions.configManager.getConfigNode(0, "GUI", "Border").getString();
        missionsMenuDisplayName = BetterMissions.configManager.getConfigNode(0, "GUI", "Display-Name").getString();
        menuEnabled = BetterMissions.configManager.getConfigNode(0, "GUI", "Enabled").getBoolean();
        missionItemRepresentativeMap = BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative").getValue(new TypeToken<Map<String, String>>() {});
        missionRerollDisplayName = BetterMissions.configManager.getConfigNode(0, "GUI", "Reroll", "Display-Name").getString();
        rerollsEnabled = BetterMissions.configManager.getConfigNode(0, "GUI", "Reroll", "Enabled").getBoolean();
        missionRerollItemID = BetterMissions.configManager.getConfigNode(0, "GUI", "Reroll", "ID").getString();
        rerollLore = BetterMissions.configManager.getConfigNode(0, "GUI", "Reroll", "Lore").getList(TypeToken.of(String.class));
        newMissionNotification = BetterMissions.configManager.getConfigNode(0, "New-Mission").getString();
        updateInterval = BetterMissions.configManager.getConfigNode(0, "Update").getInt();

        breedMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Breed-Missions").getList(TypeToken.of(String.class)));
        catchMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Catch-Missions").getList(TypeToken.of(String.class)));
        craftMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Craft-Missions").getList(TypeToken.of(String.class)));
        defeatMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Defeat-Missions").getList(TypeToken.of(String.class)));
        evolveMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Evolve-Missions").getList(TypeToken.of(String.class)));
        fishMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Fish-Missions").getList(TypeToken.of(String.class)));
        hatchMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Hatch-Missions").getList(TypeToken.of(String.class)));
        killMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Kill-Missions").getList(TypeToken.of(String.class)));
        loseMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Lose-Missions").getList(TypeToken.of(String.class)));
        meleeMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Melee-Missions").getList(TypeToken.of(String.class)));
        mineMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Mine-Missions").getList(TypeToken.of(String.class)));
        photographMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Photograph-Missions").getList(TypeToken.of(String.class)));
        raidMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Raid-Missions").getList(TypeToken.of(String.class)));
        releaseMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Release-Missions").getList(TypeToken.of(String.class)));
        smeltMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Smelt-Missions").getList(TypeToken.of(String.class)));

    }

    public static void reloadMissions() throws ObjectMappingException {

        breedMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Breed-Missions").getList(TypeToken.of(String.class)));
        catchMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Catch-Missions").getList(TypeToken.of(String.class)));
        craftMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Craft-Missions").getList(TypeToken.of(String.class)));
        defeatMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Defeat-Missions").getList(TypeToken.of(String.class)));
        evolveMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Evolve-Missions").getList(TypeToken.of(String.class)));
        fishMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Fish-Missions").getList(TypeToken.of(String.class)));
        hatchMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Hatch-Missions").getList(TypeToken.of(String.class)));
        killMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Kill-Missions").getList(TypeToken.of(String.class)));
        loseMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Lose-Missions").getList(TypeToken.of(String.class)));
        meleeMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Melee-Missions").getList(TypeToken.of(String.class)));
        mineMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Mine-Missions").getList(TypeToken.of(String.class)));
        photographMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Photograph-Missions").getList(TypeToken.of(String.class)));
        raidMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Raid-Missions").getList(TypeToken.of(String.class)));
        releaseMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Release-Missions").getList(TypeToken.of(String.class)));
        smeltMissions = new ArrayList<>(BetterMissions.configManager.getConfigNode(1, "Smelt-Missions").getList(TypeToken.of(String.class)));

    }

}
