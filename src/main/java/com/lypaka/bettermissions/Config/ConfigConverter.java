package com.lypaka.bettermissions.Config;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Missions.MissionRegistry;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.*;

public class ConfigConverter {

    private static int index = 0;

    public static void convertOldToNew() throws ObjectMappingException {

        BetterMissions.logger.warn("-------------------------------BETTER MISSIONS-------------------------------");
        BetterMissions.logger.warn("Running one-time conversion of your mission configs to the new system... Please wait.");
        BetterMissions.logger.warn("The server is going to start up as normal while this process runs, but commands and event listeners will be temporarily disabled until this process finishes!");
        BetterMissions.logger.warn("You will see a message in your console and in-game - if you are online and have permission bettermissions.command.admin - when this is done.");
        BetterMissions.logger.warn("-------------------------------BETTER MISSIONS-------------------------------");
        Timer timer = new Timer();
        Map<String, Map<String, String>> catching = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : catching.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager catchingConfigManager = new ComplexConfigManager(missionFiles, "catch-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        catchingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all catch missions!");
                    // Puts the mission's configuration manager in a convenient map so I can grab them when needed
                    BetterMissions.missionConfigManager.put("Catch", catchingConfigManager);
                    // Puts all the catch mission IDs in the ConfigGetters list, to be saved to the config file for use later
                    ConfigGetters.catchMissions.addAll(missionFiles);
                    try {

                        convertCraftConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    catchingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    catchingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Chance").getDouble();

                    }
                    catchingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    catchingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "ID").getString();

                    }
                    catchingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        catchingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    catchingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    catchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    catchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    catchingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Reward", "Type").getString();
                    catchingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        catchingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            catchingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    Map<String, String> specsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Specs").isVirtual()) {

                        try {

                            specsMap = BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    catchingConfigManager.getConfigNode(index, "Specs").setValue(specsMap);
                    catchingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(0, "Missions", missionID, "Timer").getInt());
                    catchingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertCraftConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> crafting = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : crafting.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager craftingConfigManager = new ComplexConfigManager(missionFiles, "craft-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        craftingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all craft missions!");
                    BetterMissions.missionConfigManager.put("Craft", craftingConfigManager);
                    ConfigGetters.craftMissions.addAll(missionFiles);
                    try {

                        convertDefeatConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    craftingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    craftingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Chance").getDouble();

                    }
                    craftingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    craftingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "ID").getString();

                    }
                    craftingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    List<String> itemIDs = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Items").isVirtual()) {

                        try {

                            itemIDs = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Items").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Items").setValue(itemIDs);
                    try {

                        craftingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Reward", "Type").getString();
                    craftingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        craftingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            craftingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    craftingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(1, "Missions", missionID, "Timer").getInt());
                    craftingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertDefeatConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> defeating = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : defeating.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager defeatingConfigManager = new ComplexConfigManager(missionFiles, "defeat-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        defeatingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all defeat missions!");
                    BetterMissions.missionConfigManager.put("Defeat", defeatingConfigManager);
                    ConfigGetters.defeatMissions.addAll(missionFiles);
                    try {

                        convertEvolveConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    defeatingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    defeatingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Chance").getDouble();

                    }
                    defeatingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    defeatingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "ID").getString();

                    }
                    defeatingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    List<String> locations = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Locations").isVirtual()) {

                        try {

                            locations = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Locations").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Locations").setValue(locations);
                    try {

                        defeatingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Reward", "Type").getString();
                    defeatingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        defeatingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            defeatingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    defeatingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(2, "Missions", missionID, "Timer").getInt());
                    defeatingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertEvolveConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> evolving = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : evolving.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager evolvingConfigManager = new ComplexConfigManager(missionFiles, "evolve-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        evolvingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all evolve missions!");
                    BetterMissions.missionConfigManager.put("Evolve", evolvingConfigManager);
                    ConfigGetters.evolveMissions.addAll(missionFiles);
                    try {

                        convertFishConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    evolvingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    evolvingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Chance").getDouble();

                    }
                    evolvingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    evolvingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "ID").getString();

                    }
                    evolvingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        evolvingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    evolvingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    evolvingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    evolvingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    evolvingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Reward", "Type").getString();
                    evolvingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        evolvingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            evolvingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    Map<String, String> specsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Specs").isVirtual()) {

                        try {

                            specsMap = BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    evolvingConfigManager.getConfigNode(index, "Specs").setValue(specsMap);
                    evolvingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(3, "Missions", missionID, "Timer").getInt());
                    evolvingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertFishConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> fishing = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : fishing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager fishingConfigManager = new ComplexConfigManager(missionFiles, "fish-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        fishingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all fish missions!");
                    BetterMissions.missionConfigManager.put("Fish", fishingConfigManager);
                    ConfigGetters.fishMissions.addAll(missionFiles);
                    try {

                        convertKillConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    fishingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    fishingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Chance").getDouble();

                    }
                    fishingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    fishingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "ID").getString();

                    }
                    fishingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        fishingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    fishingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    fishingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    fishingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    fishingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Reward", "Type").getString();
                    fishingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        fishingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            fishingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    Map<String, String> specsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Specs").isVirtual()) {

                        try {

                            specsMap = BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    fishingConfigManager.getConfigNode(index, "Specs").setValue(specsMap);
                    fishingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(4, "Missions", missionID, "Timer").getInt());
                    fishingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertKillConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> killing = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : killing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager killingConfigManager = new ComplexConfigManager(missionFiles, "kill-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        killingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all kill missions!");
                    BetterMissions.missionConfigManager.put("Kill", killingConfigManager);
                    ConfigGetters.killMissions.addAll(missionFiles);
                    try {

                        convertLoseConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    killingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    killingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Chance").getDouble();

                    }
                    killingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    killingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "ID").getString();

                    }
                    killingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        killingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    killingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    killingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    killingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    killingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Reward", "Type").getString();
                    killingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        killingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            killingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    Map<String, String> specsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Specs").isVirtual()) {

                        try {

                            specsMap = BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    killingConfigManager.getConfigNode(index, "Specs").setValue(specsMap);
                    killingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(5, "Missions", missionID, "Timer").getInt());
                    killingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertLoseConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> losing = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : losing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager losingConfigManager = new ComplexConfigManager(missionFiles, "lose-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        losingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all lose missions!");
                    BetterMissions.missionConfigManager.put("Lose", losingConfigManager);
                    ConfigGetters.loseMissions.addAll(missionFiles);
                    try {

                        convertMeleeConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    losingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    losingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Chance").getDouble();

                    }
                    losingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    losingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Display-Name").getString());
                    String entityType = "both";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Entity-Type").isVirtual()) {

                        entityType = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Entity-Type").getString();

                    }
                    losingConfigManager.getConfigNode(index, "Entity-Type").setValue(entityType);
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "ID").getString();

                    }
                    losingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        losingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    losingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    losingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    losingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    losingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Reward", "Type").getString();
                    losingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        losingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            losingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    losingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(6, "Missions", missionID, "Timer").getInt());
                    losingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertMeleeConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> meleeing = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : meleeing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager meleeConfigManager = new ComplexConfigManager(missionFiles, "melee-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        meleeConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all melee missions!");
                    BetterMissions.missionConfigManager.put("Melee", meleeConfigManager);
                    ConfigGetters.meleeMissions.addAll(missionFiles);
                    try {

                        convertMiningConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    meleeConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    meleeConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Chance").getDouble();

                    }
                    meleeConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    meleeConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Display-Name").getString());
                    List<String> entities = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Entities").isVirtual()) {

                        try {

                            entities = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Entities").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Entities").setValue(entities);
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "ID").getString();

                    }
                    meleeConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        meleeConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Reward", "Type").getString();
                    meleeConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        meleeConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            meleeConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    meleeConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(7, "Missions", missionID, "Timer").getInt());
                    meleeConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertMiningConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> mining = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : mining.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager miningConfigManager = new ComplexConfigManager(missionFiles, "mine-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        miningConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all mine missions!");
                    BetterMissions.missionConfigManager.put("Mine", miningConfigManager);
                    ConfigGetters.mineMissions.addAll(missionFiles);
                    try {

                        convertReleasingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    miningConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    miningConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Amount").getInt());
                    List<String> blocks = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Blocks").isVirtual()) {

                        try {

                            blocks = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Blocks").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Blocks").setValue(blocks);
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Chance").getDouble();

                    }
                    miningConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    miningConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "ID").getString();

                    }
                    miningConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        miningConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Reward", "Type").getString();
                    miningConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        miningConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            miningConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    Map<String, String> specsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Specs").isVirtual()) {

                        try {

                            specsMap = BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    miningConfigManager.getConfigNode(index, "Specs").setValue(specsMap);
                    miningConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(8, "Missions", missionID, "Timer").getInt());
                    miningConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void convertReleasingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> releasing = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : releasing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager releasingConfigManager = new ComplexConfigManager(missionFiles, "release-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        releasingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully converted all release missions!");
                    BetterMissions.missionConfigManager.put("Release", releasingConfigManager);
                    ConfigGetters.releaseMissions.addAll(missionFiles);
                    try {

                        createBreedingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    releasingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    releasingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Chance").getDouble();

                    }
                    releasingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    releasingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "ID").getString();

                    }
                    releasingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        releasingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    releasingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    releasingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    releasingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    releasingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Reward", "Type").getString();
                    releasingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        releasingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            releasingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    releasingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(9, "Missions", missionID, "Timer").getInt());
                    releasingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void createBreedingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> breeding = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : breeding.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager breedingConfigManager = new ComplexConfigManager(missionFiles, "breed-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        breedingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully created new breed missions!");
                    BetterMissions.missionConfigManager.put("Breed", breedingConfigManager);
                    ConfigGetters.breedMissions.addAll(missionFiles);
                    try {

                        createHatchingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    breedingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    breedingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Chance").getDouble();

                    }
                    breedingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    breedingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "ID").getString();

                    }
                    breedingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        breedingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    breedingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    breedingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    breedingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    breedingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Reward", "Type").getString();
                    breedingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        breedingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            breedingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    breedingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(10, "Missions", missionID, "Timer").getInt());
                    breedingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void createHatchingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> hatching = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : hatching.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager hatchingConfigManager = new ComplexConfigManager(missionFiles, "hatch-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        hatchingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully created new hatch missions!");
                    BetterMissions.missionConfigManager.put("Hatch", hatchingConfigManager);
                    ConfigGetters.hatchMissions.addAll(missionFiles);
                    try {

                        createPhotographingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    hatchingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    hatchingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Chance").getDouble();

                    }
                    hatchingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    hatchingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "ID").getString();

                    }
                    hatchingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        hatchingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    hatchingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    hatchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    hatchingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    hatchingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Reward", "Type").getString();
                    hatchingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        hatchingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            hatchingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    hatchingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(11, "Missions", missionID, "Timer").getInt());
                    hatchingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void createPhotographingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> photographing = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : photographing.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager photographingConfigManager = new ComplexConfigManager(missionFiles, "photograph-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        photographingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully created new photograph missions!");
                    BetterMissions.missionConfigManager.put("Photograph", photographingConfigManager);
                    ConfigGetters.photographMissions.addAll(missionFiles);
                    try {

                        createRaidingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    photographingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    photographingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Chance").getDouble();

                    }
                    photographingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    photographingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "ID").getString();

                    }
                    photographingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        photographingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    photographingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    photographingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    photographingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    photographingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Reward", "Type").getString();
                    photographingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        photographingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            photographingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    photographingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(12, "Missions", missionID, "Timer").getInt());
                    photographingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void createRaidingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> raiding = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : raiding.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager raidingConfigManager = new ComplexConfigManager(missionFiles, "raid-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        raidingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully created new raid missions!");
                    BetterMissions.missionConfigManager.put("Raid", raidingConfigManager);
                    ConfigGetters.raidMissions.addAll(missionFiles);
                    try {

                        createSmeltingConfigs();

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                } else {

                    String missionID = missionIDs.get(index);
                    raidingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    raidingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Chance").getDouble();

                    }
                    raidingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    raidingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "ID").getString();

                    }
                    raidingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        raidingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    raidingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    raidingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    raidingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    raidingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Reward", "Type").getString();
                    raidingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        raidingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            raidingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    raidingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(13, "Missions", missionID, "Timer").getInt());
                    raidingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

    private static void createSmeltingConfigs() throws ObjectMappingException {

        Timer timer = new Timer();
        index = 0;
        Map<String, Map<String, String>> smelting = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        List<String> missionFiles = new ArrayList<>();
        List<String> missionIDs = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : smelting.entrySet()) {

            missionFiles.add(entry.getKey().replace(" ", "") + ".conf");
            missionIDs.add(entry.getKey());

        }
        ComplexConfigManager smeltingConfigManager = new ComplexConfigManager(missionFiles, "smelt-missions", "mission-template-normal.conf", BetterMissions.dir, BetterMissions.class, BetterMissions.MOD_NAME, BetterMissions.MOD_ID, BetterMissions.logger);
        smeltingConfigManager.init();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= missionFiles.size()) {

                    this.cancel();
                    BetterMissions.logger.info("Successfully created new smelt missions!");
                    BetterMissions.missionConfigManager.put("Smelt", smeltingConfigManager);
                    ConfigGetters.smeltMissions.addAll(missionFiles);
                    BetterMissions.configManager.getConfigNode(0, "Converted").setValue(true);
                    BetterMissions.configManager.getConfigNode(1, "Catch-Missions").setValue(ConfigGetters.catchMissions);
                    BetterMissions.configManager.getConfigNode(1, "Craft-Missions").setValue(ConfigGetters.craftMissions);
                    BetterMissions.configManager.getConfigNode(1, "Defeat-Missions").setValue(ConfigGetters.defeatMissions);
                    BetterMissions.configManager.getConfigNode(1, "Evolve-Missions").setValue(ConfigGetters.evolveMissions);
                    BetterMissions.configManager.getConfigNode(1, "Fish-Missions").setValue(ConfigGetters.fishMissions);
                    BetterMissions.configManager.getConfigNode(1, "Kill-Missions").setValue(ConfigGetters.killMissions);
                    BetterMissions.configManager.getConfigNode(1, "Lose-Missions").setValue(ConfigGetters.loseMissions);
                    BetterMissions.configManager.getConfigNode(1, "Melee-Missions").setValue(ConfigGetters.meleeMissions);
                    BetterMissions.configManager.getConfigNode(1, "Mine-Missions").setValue(ConfigGetters.mineMissions);
                    BetterMissions.configManager.getConfigNode(1, "Release-Missions").setValue(ConfigGetters.releaseMissions);
                    BetterMissions.configManager.getConfigNode(1, "Breed-Missions").setValue(ConfigGetters.breedMissions);
                    BetterMissions.configManager.getConfigNode(1, "Hatch-Missions").setValue(ConfigGetters.hatchMissions);
                    BetterMissions.configManager.getConfigNode(1, "Photograph-Missions").setValue(ConfigGetters.photographMissions);
                    BetterMissions.configManager.getConfigNode(1, "Raid-Missions").setValue(ConfigGetters.raidMissions);
                    BetterMissions.configManager.getConfigNode(1, "Smelt-Missions").setValue(ConfigGetters.smeltMissions);
                    BetterMissions.configManager.save();
                    BetterMissions.logger.warn("-------------------------------BETTER MISSIONS-------------------------------");
                    BetterMissions.logger.warn("BetterMissions has successfully converted all old configs into the new system.");
                    BetterMissions.logger.warn("-------------------------------BETTER MISSIONS-------------------------------");
                    for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                        if (PermissionHandler.hasPermission(entry.getValue(), "bettermissions.command.admin")) {

                            entry.getValue().sendMessage(FancyText.getFormattedText("&aBetterMissions has successfully converted all old configs into the new system."), entry.getValue().getUniqueID());

                        }

                    }

                    MissionRegistry.loadBreedMissions(true);


                } else {

                    String missionID = missionIDs.get(index);
                    smeltingConfigManager.getConfigNode(index, "Mission-ID").setValue(missionID);
                    smeltingConfigManager.getConfigNode(index, "Amount").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Amount").getInt());
                    double chance = 1.0;
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Chance").isVirtual()) {

                        chance = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Chance").getDouble();

                    }
                    smeltingConfigManager.getConfigNode(index, "Chance").setValue(chance);
                    smeltingConfigManager.getConfigNode(index, "Display-Name").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Display-Name").getString());
                    String commandID = "";
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "ID").isVirtual()) {

                        commandID = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "ID").getString();

                    }
                    smeltingConfigManager.getConfigNode(index, "Command-ID").setValue(commandID);
                    try {

                        smeltingConfigManager.getConfigNode(index, "Lore").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Lore").getList(TypeToken.of(String.class)));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    List<String> itemIDs = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Items").isVirtual()) {

                        try {

                            itemIDs = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Items").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            throw new RuntimeException(e);

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Items").setValue(itemIDs);
                    Map<String, String> itemRequirementsMap = new HashMap<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Items").isVirtual()) {

                        try {

                            itemRequirementsMap = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Items").getValue(new TypeToken<Map<String, String>>() {});

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Requirements", "Items").setValue(itemRequirementsMap);
                    List<String> doesNotHave = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").isVirtual()) {

                        try {

                            doesNotHave = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Permission", "Does-Not-Have").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Requirements", "Permission", "Does-Not-Have").setValue(doesNotHave);
                    List<String> has = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Permission", "Has").isVirtual()) {

                        try {

                            has = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Permission", "Has").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Requirements", "Permission", "Has").setValue(has);
                    List<String> days = new ArrayList<>();
                    if (!BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Time", "Days").isVirtual()) {

                        try {

                            days = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Requirements", "Time", "Days").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Requirements", "Time", "Days").setValue(days);
                    String rewardType = BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Reward", "Type").getString();
                    smeltingConfigManager.getConfigNode(index, "Reward", "Type").setValue(rewardType);
                    if (rewardType.equalsIgnoreCase("money")) {

                        smeltingConfigManager.getConfigNode(index, "Reward", "Value").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Reward", "Value").getInt());

                    } else {

                        try {

                            smeltingConfigManager.getConfigNode(index, "Reward", "Commands").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Reward", "Commands").getList(TypeToken.of(String.class)));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    }
                    smeltingConfigManager.getConfigNode(index, "Timer").setValue(BetterMissions.dummyConfigManager.getConfigNode(14, "Missions", missionID, "Timer").getInt());
                    smeltingConfigManager.save();
                    BetterMissions.logger.info("Successfully converted mission: " + missionID + " into its own file!");
                    index++;

                }

            }

        }, 0, 100);

    }

}
