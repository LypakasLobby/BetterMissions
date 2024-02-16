package com.lypaka.bettermissions.Missions;

import java.util.*;

public class MissionRegistry {

    private static int index;
    public static List<String> allMissionIDs = new ArrayList<>();

    public static List<BreedMission> breedMissions = new ArrayList<>();
    public static List<CatchMission> catchMissions = new ArrayList<>();
    public static List<CraftMission> craftMissions = new ArrayList<>();
    public static List<DefeatMission> defeatMissions = new ArrayList<>();
    public static List<EvolveMission> evolveMissions = new ArrayList<>();
    public static List<FishMission> fishMissions = new ArrayList<>();
    public static List<HatchMission> hatchMissions = new ArrayList<>();
    public static List<KillMission> killMissions = new ArrayList<>();
    public static List<LoseMission> loseMissions = new ArrayList<>();
    public static List<MeleeMission> meleeMissions = new ArrayList<>();
    public static List<MineMission> mineMissions = new ArrayList<>();
    public static List<PhotographMission> photographMissions = new ArrayList<>();
    public static List<RaidMission> raidMissions = new ArrayList<>();
    public static List<ReleaseMission> releaseMissions = new ArrayList<>();
    public static List<SmeltMission> smeltMissions = new ArrayList<>();

    /** Permanent missions, do not run on a timer and do not automatically expire, needs to be given with a command */
    public static List<BreedMission> permanentBreedMissions = new ArrayList<>();
    public static List<CatchMission> permanentCatchMissions = new ArrayList<>();
    public static List<CraftMission> permanentCraftMissions = new ArrayList<>();
    public static List<DefeatMission> permanentDefeatMissions = new ArrayList<>();
    public static List<EvolveMission> permanentEvolveMissions = new ArrayList<>();
    public static List<FishMission> permanentFishMissions = new ArrayList<>();
    public static List<HatchMission> permanentHatchMissions = new ArrayList<>();
    public static List<KillMission> permanentKillMissions = new ArrayList<>();
    public static List<LoseMission> permanentLoseMissions = new ArrayList<>();
    public static List<MeleeMission> permanentMeleeMissions = new ArrayList<>();
    public static List<MineMission> permanentMineMissions = new ArrayList<>();
    public static List<PhotographMission> permanentPhotographMissions = new ArrayList<>();
    public static List<RaidMission> permanentRaidMissions = new ArrayList<>();
    public static List<ReleaseMission> permanentReleaseMissions = new ArrayList<>();
    public static List<SmeltMission> permanentSmeltMissions = new ArrayList<>();

    //private static final long interval = ConfigGetters.registryInterval;

    /*public static void loadBreedMissions (boolean doNext) {

        BetterMissions.logger.info("Starting mission registry...");
        breedMissions = new ArrayList<>();
        permanentBreedMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager breeding = BetterMissions.missionConfigManager.get("Breed");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.breedMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadCatchMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = breeding.getConfigNode(i, "Mission-ID").getString();
                    int amount = breeding.getConfigNode(i, "Amount").getInt();
                    double chance = breeding.getConfigNode(i, "Chance").getDouble();
                    String displayName = breeding.getConfigNode(i, "Display-Name").getString();
                    String commandID = breeding.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = breeding.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!breeding.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!breeding.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = breeding.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!breeding.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = breeding.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!breeding.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = breeding.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!breeding.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = breeding.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!breeding.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = breeding.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!breeding.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = breeding.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = breeding.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = breeding.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = breeding.getConfigNode(i, "Timer").getInt();
                    BreedMission breedMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = breeding.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        breedMission = new BreedMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        breedMission = new BreedMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    breedMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Breed Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadCatchMissions (boolean doNext) {

        catchMissions = new ArrayList<>();
        permanentCatchMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager catching = BetterMissions.missionConfigManager.get("Catch");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.catchMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadCraftMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = catching.getConfigNode(i, "Mission-ID").getString();
                    int amount = catching.getConfigNode(i, "Amount").getInt();
                    double chance = catching.getConfigNode(i, "Chance").getDouble();
                    String displayName = catching.getConfigNode(i, "Display-Name").getString();
                    String commandID = catching.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = catching.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!catching.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!catching.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = catching.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!catching.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = catching.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!catching.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = catching.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!catching.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = catching.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!catching.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = catching.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!catching.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = catching.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = catching.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = catching.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = catching.getConfigNode(i, "Timer").getInt();
                    CatchMission catchMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = catching.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        catchMission = new CatchMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        catchMission = new CatchMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    catchMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Catch Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadCraftMissions (boolean doNext) {

        index = 0;
        craftMissions = new ArrayList<>();
        permanentCraftMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager crafting = BetterMissions.missionConfigManager.get("Craft");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.craftMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadDefeatMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = crafting.getConfigNode(i, "Mission-ID").getString();
                    int amount = crafting.getConfigNode(i, "Amount").getInt();
                    double chance = crafting.getConfigNode(i, "Chance").getDouble();
                    String displayName = crafting.getConfigNode(i, "Display-Name").getString();
                    String commandID = crafting.getConfigNode(i, "Command-ID").getString();
                    List<String> itemIDs = new ArrayList<>();
                    try {

                        itemIDs = crafting.getConfigNode(i, "Items").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException er) {

                        er.printStackTrace();

                    }
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = crafting.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!crafting.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!crafting.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = crafting.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!crafting.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = crafting.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!crafting.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = crafting.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!crafting.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = crafting.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!crafting.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = crafting.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!crafting.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = crafting.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = crafting.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = crafting.getConfigNode(i, "Timer").getInt();
                    CraftMission craftMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = crafting.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        craftMission = new CraftMission(missionID, amount, chance, displayName, commandID, itemIDs, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        craftMission = new CraftMission(missionID, amount, chance, displayName, commandID, itemIDs, displayLore, rewardType, reward, requirements, timer);

                    }
                    craftMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Craft Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadDefeatMissions (boolean doNext) {

        index = 0;
        defeatMissions = new ArrayList<>();
        permanentDefeatMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager defeating = BetterMissions.missionConfigManager.get("Defeat");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.defeatMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadEvolveMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = defeating.getConfigNode(i, "Mission-ID").getString();
                    int amount = defeating.getConfigNode(i, "Amount").getInt();
                    double chance = defeating.getConfigNode(i, "Chance").getDouble();
                    String displayName = defeating.getConfigNode(i, "Display-Name").getString();
                    String commandID = defeating.getConfigNode(i, "Command-ID").getString();
                    List<String> locations = new ArrayList<>();
                    try {

                        locations = defeating.getConfigNode(i, "Locations").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException er) {

                        er.printStackTrace();

                    }
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = defeating.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!defeating.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!defeating.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = defeating.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!defeating.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = defeating.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!defeating.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = defeating.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!defeating.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = defeating.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!defeating.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = defeating.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!defeating.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = defeating.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = defeating.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = defeating.getConfigNode(i, "Timer").getInt();
                    DefeatMission defeatMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = defeating.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        defeatMission = new DefeatMission(missionID, amount, chance, displayName, commandID, locations, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        defeatMission = new DefeatMission(missionID, amount, chance, displayName, commandID, locations, displayLore, rewardType, reward, requirements, timer);

                    }
                    defeatMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Defeat Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadEvolveMissions (boolean doNext) {

        index = 0;
        evolveMissions = new ArrayList<>();
        permanentEvolveMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager evolving = BetterMissions.missionConfigManager.get("Evolve");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.evolveMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadFishMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = evolving.getConfigNode(i, "Mission-ID").getString();
                    int amount = evolving.getConfigNode(i, "Amount").getInt();
                    double chance = evolving.getConfigNode(i, "Chance").getDouble();
                    String displayName = evolving.getConfigNode(i, "Display-Name").getString();
                    String commandID = evolving.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = evolving.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!evolving.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!evolving.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = evolving.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!evolving.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = evolving.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!evolving.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = evolving.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!evolving.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = evolving.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!evolving.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = evolving.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!evolving.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = evolving.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = evolving.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = evolving.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = evolving.getConfigNode(i, "Timer").getInt();
                    EvolveMission evolveMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = evolving.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        evolveMission = new EvolveMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        evolveMission = new EvolveMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    evolveMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Evolve Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadFishMissions (boolean doNext) {

        index = 0;
        fishMissions = new ArrayList<>();
        permanentFishMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager fishing = BetterMissions.missionConfigManager.get("Fish");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.fishMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadHatchMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = fishing.getConfigNode(i, "Mission-ID").getString();
                    int amount = fishing.getConfigNode(i, "Amount").getInt();
                    double chance = fishing.getConfigNode(i, "Chance").getDouble();
                    String displayName = fishing.getConfigNode(i, "Display-Name").getString();
                    String commandID = fishing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = fishing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!fishing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!fishing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = fishing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!fishing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = fishing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!fishing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = fishing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!fishing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = fishing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!fishing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = fishing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!fishing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = fishing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = fishing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = fishing.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = fishing.getConfigNode(i, "Timer").getInt();
                    FishMission fishMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = fishing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        fishMission = new FishMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        fishMission = new FishMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    fishMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Fish Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadHatchMissions (boolean doNext) {

        index = 0;
        hatchMissions = new ArrayList<>();
        permanentHatchMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager hatching = BetterMissions.missionConfigManager.get("Hatch");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.hatchMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadKillMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = hatching.getConfigNode(i, "Mission-ID").getString();
                    int amount = hatching.getConfigNode(i, "Amount").getInt();
                    double chance = hatching.getConfigNode(i, "Chance").getDouble();
                    String displayName = hatching.getConfigNode(i, "Display-Name").getString();
                    String commandID = hatching.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = hatching.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!hatching.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!hatching.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = hatching.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!hatching.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = hatching.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!hatching.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = hatching.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!hatching.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = hatching.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!hatching.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = hatching.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!hatching.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = hatching.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = hatching.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = hatching.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = hatching.getConfigNode(i, "Timer").getInt();
                    HatchMission hatchMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = hatching.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        hatchMission = new HatchMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        hatchMission = new HatchMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    hatchMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Hatch Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadKillMissions (boolean doNext) {

        index = 0;
        killMissions = new ArrayList<>();
        permanentKillMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager killing = BetterMissions.missionConfigManager.get("Kill");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.killMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadLoseMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = killing.getConfigNode(i, "Mission-ID").getString();
                    int amount = killing.getConfigNode(i, "Amount").getInt();
                    double chance = killing.getConfigNode(i, "Chance").getDouble();
                    String displayName = killing.getConfigNode(i, "Display-Name").getString();
                    String commandID = killing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = killing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!killing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!killing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = killing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!killing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = killing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!killing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = killing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!killing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = killing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!killing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = killing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!killing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = killing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = killing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = killing.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = killing.getConfigNode(i, "Timer").getInt();
                    KillMission killMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = killing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        killMission = new KillMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        killMission = new KillMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    killMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Kill Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadLoseMissions (boolean doNext) {

        index = 0;
        loseMissions = new ArrayList<>();
        permanentLoseMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager losing = BetterMissions.missionConfigManager.get("Lose");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.loseMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadMeleeMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = losing.getConfigNode(i, "Mission-ID").getString();
                    int amount = losing.getConfigNode(i, "Amount").getInt();
                    double chance = losing.getConfigNode(i, "Chance").getDouble();
                    String displayName = losing.getConfigNode(i, "Display-Name").getString();
                    String entityType = losing.getConfigNode(i, "Entity-Type").getString();
                    String commandID = losing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = losing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!losing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!losing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = losing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!losing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = losing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!losing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = losing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!losing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = losing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!losing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = losing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!losing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = losing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = losing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = losing.getConfigNode(i, "Timer").getInt();
                    LoseMission loseMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = losing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        loseMission = new LoseMission(missionID, amount, chance, displayName, entityType, commandID, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        loseMission = new LoseMission(missionID, amount, chance, displayName, entityType, commandID, displayLore, rewardType, reward, requirements, timer);

                    }
                    loseMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Lose Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadMeleeMissions (boolean doNext) {

        index = 0;
        meleeMissions = new ArrayList<>();
        permanentMeleeMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager meleeing = BetterMissions.missionConfigManager.get("Melee");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.meleeMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadMineMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = meleeing.getConfigNode(i, "Mission-ID").getString();
                    int amount = meleeing.getConfigNode(i, "Amount").getInt();
                    double chance = meleeing.getConfigNode(i, "Chance").getDouble();
                    String displayName = meleeing.getConfigNode(i, "Display-Name").getString();
                    List<String> entities = new ArrayList<>();
                    try {

                        entities = meleeing.getConfigNode(i, "Entities").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String commandID = meleeing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = meleeing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!meleeing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!meleeing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = meleeing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!meleeing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = meleeing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!meleeing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = meleeing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!meleeing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = meleeing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!meleeing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = meleeing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!meleeing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = meleeing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = meleeing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = meleeing.getConfigNode(i, "Timer").getInt();
                    MeleeMission meleeMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = meleeing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        meleeMission = new MeleeMission(missionID, amount, chance, displayName, commandID, entities, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        meleeMission = new MeleeMission(missionID, amount, chance, displayName, commandID, entities, displayLore, rewardType, reward, requirements, timer);

                    }
                    meleeMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Melee Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadMineMissions (boolean doNext) {

        index = 0;
        mineMissions = new ArrayList<>();
        permanentMineMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager mining = BetterMissions.missionConfigManager.get("Mine");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.mineMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadPhotographyMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = mining.getConfigNode(i, "Mission-ID").getString();
                    int amount = mining.getConfigNode(i, "Amount").getInt();
                    double chance = mining.getConfigNode(i, "Chance").getDouble();
                    String displayName = mining.getConfigNode(i, "Display-Name").getString();
                    List<String> blocks = new ArrayList<>();
                    try {

                        blocks = mining.getConfigNode(i, "Blocks").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String commandID = mining.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = mining.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!mining.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!mining.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = mining.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!mining.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = mining.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!mining.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = mining.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!mining.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = mining.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!mining.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = mining.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!mining.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = mining.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = mining.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = mining.getConfigNode(i, "Timer").getInt();
                    MineMission mineMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = mining.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        mineMission = new MineMission(missionID, amount, chance, blocks, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        mineMission = new MineMission(missionID, amount, chance, blocks, displayName, commandID, displayLore, rewardType, reward, requirements, timer);

                    }
                    mineMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Mine Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadPhotographyMissions (boolean doNext) {

        index = 0;
        photographMissions = new ArrayList<>();
        permanentPhotographMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager photographing = BetterMissions.missionConfigManager.get("Photograph");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.photographMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadRaidMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = photographing.getConfigNode(i, "Mission-ID").getString();
                    int amount = photographing.getConfigNode(i, "Amount").getInt();
                    double chance = photographing.getConfigNode(i, "Chance").getDouble();
                    String displayName = photographing.getConfigNode(i, "Display-Name").getString();
                    String commandID = photographing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = photographing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!photographing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!photographing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = photographing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!photographing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = photographing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!photographing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = photographing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!photographing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = photographing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!photographing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = photographing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!photographing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = photographing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = photographing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = photographing.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = photographing.getConfigNode(i, "Timer").getInt();
                    PhotographMission photographMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = photographing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        photographMission = new PhotographMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        photographMission = new PhotographMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    photographMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Photograph Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadRaidMissions (boolean doNext) {

        index = 0;
        raidMissions = new ArrayList<>();
        permanentRaidMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager raiding = BetterMissions.missionConfigManager.get("Raid");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.raidMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadReleaseMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = raiding.getConfigNode(i, "Mission-ID").getString();
                    int amount = raiding.getConfigNode(i, "Amount").getInt();
                    double chance = raiding.getConfigNode(i, "Chance").getDouble();
                    String displayName = raiding.getConfigNode(i, "Display-Name").getString();
                    String commandID = raiding.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = raiding.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!raiding.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!raiding.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = raiding.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!raiding.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = raiding.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!raiding.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = raiding.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!raiding.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = raiding.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!raiding.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = raiding.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!raiding.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = raiding.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = raiding.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = raiding.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = raiding.getConfigNode(i, "Timer").getInt();
                    RaidMission raidMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = raiding.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        raidMission = new RaidMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        raidMission = new RaidMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    raidMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Raid Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadReleaseMissions (boolean doNext) {

        index = 0;
        releaseMissions = new ArrayList<>();
        permanentReleaseMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager releasing = BetterMissions.missionConfigManager.get("Release");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.releaseMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        loadSmeltMissions(doNext);

                    }

                } else {

                    int i = index;
                    String missionID = releasing.getConfigNode(i, "Mission-ID").getString();
                    int amount = releasing.getConfigNode(i, "Amount").getInt();
                    double chance = releasing.getConfigNode(i, "Chance").getDouble();
                    String displayName = releasing.getConfigNode(i, "Display-Name").getString();
                    String commandID = releasing.getConfigNode(i, "Command-ID").getString();
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = releasing.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!releasing.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!releasing.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = releasing.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!releasing.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = releasing.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!releasing.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = releasing.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!releasing.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = releasing.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!releasing.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = releasing.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!releasing.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = releasing.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = releasing.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    Map<String, String> specs = new HashMap<>();
                    try {

                        specs = releasing.getConfigNode(i, "Specs").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    int timer = releasing.getConfigNode(i, "Timer").getInt();
                    ReleaseMission releaseMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = releasing.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        releaseMission = new ReleaseMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, rewardCommands, requirements, specs, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        releaseMission = new ReleaseMission(missionID, amount, chance, displayName, commandID, displayLore, rewardType, reward, requirements, specs, timer);

                    }
                    releaseMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Release Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }

    public static void loadSmeltMissions (boolean doNext) {

        index = 0;
        smeltMissions = new ArrayList<>();
        permanentSmeltMissions = new ArrayList<>();
        Timer timer = new Timer();
        ComplexConfigManager smelting = BetterMissions.missionConfigManager.get("Smelt");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (index >= ConfigGetters.smeltMissions.size()) {

                    this.cancel();
                    if (doNext) {

                        BetterMissions.disabled = false;
                        BetterMissions.logger.info("Successfully loaded all Missions!");
                        LoginListener.accountsToUpdate.removeIf(a -> {

                            if (a.getMissionMap().size() == 0) {

                                AccountHandler.assignRandomMission(a);
                                AccountHandler.saveProgress(a);

                            }

                            return true;

                        });
                        MissionTimer.start();
                        Utils.putAllPermanentShitsInOneList();

                    }

                } else {

                    int i = index;
                    String missionID = smelting.getConfigNode(i, "Mission-ID").getString();
                    int amount = smelting.getConfigNode(i, "Amount").getInt();
                    double chance = smelting.getConfigNode(i, "Chance").getDouble();
                    String displayName = smelting.getConfigNode(i, "Display-Name").getString();
                    String commandID = smelting.getConfigNode(i, "Command-ID").getString();
                    List<String> itemIDs = new ArrayList<>();
                    try {

                        itemIDs = smelting.getConfigNode(i, "Items").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException er) {

                        er.printStackTrace();

                    }
                    List<String> displayLore = new ArrayList<>();
                    try {

                        displayLore = smelting.getConfigNode(i, "Lore").getList(TypeToken.of(String.class));

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    Map<String, Map<String, String>> itemRequirements = new HashMap<>();
                    Map<String, Map<String, String>> partyRequirements = new HashMap<>();
                    List<String> pokedexRequirements = new ArrayList<>();
                    Map<String, List<String>> permissionRequirements = new HashMap<>();
                    Map<String, List<String>> timeRequirements = new HashMap<>();
                    List<String> weatherRequirements = new ArrayList<>();
                    if (!smelting.getConfigNode(i, "Requirements").isVirtual()) {

                        if (!smelting.getConfigNode(i, "Requirements", "Items").isVirtual()) {

                            try {

                                itemRequirements = smelting.getConfigNode(i, "Requirements", "Items").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!smelting.getConfigNode(i, "Requirements", "Party").isVirtual()) {

                            try {

                                partyRequirements = smelting.getConfigNode(i, "Requirements", "Party").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!smelting.getConfigNode(i, "Requirements", "Pokedex").isVirtual()) {

                            try {

                                pokedexRequirements = smelting.getConfigNode(i, "Requirements", "Pokedex").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!smelting.getConfigNode(i, "Requirements", "Permission").isVirtual()) {

                            try {

                                permissionRequirements = smelting.getConfigNode(i, "Requirements", "Permission").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!smelting.getConfigNode(i, "Requirements", "Time").isVirtual()) {

                            try {

                                timeRequirements = smelting.getConfigNode(i, "Requirements", "Time").getValue(new TypeToken<Map<String, List<String>>>() {});

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                        if (!smelting.getConfigNode(i, "Requirements", "Weather").isVirtual()) {

                            try {

                                weatherRequirements = smelting.getConfigNode(i, "Requirements", "Weather").getList(TypeToken.of(String.class));

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    MissionRequirement requirements = new MissionRequirement(itemRequirements, partyRequirements, pokedexRequirements, permissionRequirements, timeRequirements, weatherRequirements);
                    Map<String, String> rewardMap = new HashMap<>();
                    try {

                        rewardMap = smelting.getConfigNode(i, "Reward").getValue(new TypeToken<Map<String, String>>() {});

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }
                    String rewardType = rewardMap.get("Type");
                    int timer = smelting.getConfigNode(i, "Timer").getInt();
                    SmeltMission smeltMission;
                    if (rewardMap.containsKey("Commands")) {

                        List<String> rewardCommands = new ArrayList<>();
                        try {

                            rewardCommands = smelting.getConfigNode(i, "Reward", "Commands").getList(TypeToken.of(String.class));

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }
                        smeltMission = new SmeltMission(missionID, amount, chance, displayName, commandID, itemIDs, displayLore, rewardType, rewardCommands, requirements, timer);

                    } else {

                        int reward = Integer.parseInt(rewardMap.get("Value"));
                        smeltMission = new SmeltMission(missionID, amount, chance, displayName, commandID, itemIDs, displayLore, rewardType, reward, requirements, timer);

                    }
                    smeltMission.register();
                    if (ConfigGetters.showLoadingMessages) {

                        BetterMissions.logger.info("Successfully loaded Smelt Mission: " + missionID);

                    }
                    allMissionIDs.add(commandID);
                    index++;

                }

            }

        }, 0, interval);

    }*/

}
