package com.lypaka.bettermissions.Accounts;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.GUIs.MissionsMenu;
import com.lypaka.bettermissions.Missions.*;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;

import java.time.LocalDateTime;
import java.util.*;

public class AccountHandler {

    public static Map<UUID, Account> accountMap = new HashMap<>();

    public static void updateProgress (Account account, String id, int amount) {

        if (!account.getMissionMap().containsKey(id)) {

            BetterMissions.logger.error("Tried to update progress for mission ID: " + id + " but that ID was not found in " + account.getUUID() + " 's account!");
            return;

        }
        account.getMissionMap().get(id).put("Progress", String.valueOf(amount));

    }

    public static int getMissionProgress (Account account, String id) {

        int amount = 0;
        if (account.getMissionMap().containsKey(id)) {

            amount = Integer.parseInt(account.getMissionMap().get(id).get("Progress"));

        }

        return amount;

    }

    public static boolean completed (int needed, int progress) {

        return progress >= needed;

    }

    public static String getCurrentMission (Account account) {

        String id = "";
        Map<String, Map<String, String>> map = account.getMissionMap();
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {

            if (entry.getValue().containsKey("IsPermanent")) {

                boolean permanent = Boolean.parseBoolean(entry.getValue().get("IsPermanent"));
                if (!permanent) {

                    id = entry.getKey();
                    break;

                }

            } else {

                id = entry.getKey();
                break;

            }

        }

        return id;

    }

    public static ArrayList<String> getCurrentPermanentMissionsList (Account account) {

        return new ArrayList<>(account.getInProgressPermanentMissions());

    }

    public static void assignPermanentMission (Account account, String id) {

        account.getInProgressPermanentMissions().add(id);
        Map<String, String> map = new HashMap<>();
        map.put("Progress", "0");
        map.put("IsPermanent", "true");
        account.getMissionMap().put(id, map);
        savePermanentMissionProgress(account);

    }

    public static void movePermanentMissionToCompleted (Account account, String id) {

        removeMission(account, id);
        account.getInProgressPermanentMissions().removeIf(entry -> entry.equalsIgnoreCase(id));
        account.getCompletedPermanentMissions().add(id);
        savePermanentMissionProgress(account);

    }

    public static ArrayList<String> getClaimedMissions (Account account) {

        return account.getClaimedMissions();

    }

    public static LocalDateTime getExpiration (Account account, String id) {

        String expires = account.getMissionMap().get(id).get("Expires");
        return LocalDateTime.parse(expires);

    }

    public static void assignRandomMission (Account account) {

        String currentMission = getCurrentMission(account);
        String repID = MissionsMenu.getRepresentationIDFromMissionID(currentMission);
        List<String> types = new ArrayList<>();
        // only add mission types that have missions to give the player
        if (repID != null) {

            if (MissionRegistry.catchMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Catching")) {

                    if (MissionRegistry.catchMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Catching");

                }

            }
            if (MissionRegistry.killMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Killing")) {

                    if (MissionRegistry.killMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Killing");

                }

            }
            if (MissionRegistry.defeatMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Defeating")) {

                    if (MissionRegistry.defeatMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Defeating");

                }

            }
            if (MissionRegistry.evolveMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Evolving")) {

                    if (MissionRegistry.evolveMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Evolving");

                }

            }
            if (MissionRegistry.fishMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Fishing")) {

                    if (MissionRegistry.fishMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Fishing");

                }

            }
            if (MissionRegistry.mineMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Mining")) {

                    if (MissionRegistry.mineMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Mining");

                }

            }
            if (MissionRegistry.craftMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Crafting")) {

                    if (MissionRegistry.craftMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Crafting");

                }

            }
            if (MissionRegistry.meleeMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Meleeing")) {

                    if (MissionRegistry.meleeMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Meleeing");

                }

            }
            if (MissionRegistry.fishMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Fishing")) {

                    if (MissionRegistry.fishMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Fishing");

                }

            }
            if (MissionRegistry.releaseMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Releasing")) {

                    if (MissionRegistry.releaseMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Releasing");

                }

            }
            if (MissionRegistry.loseMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Losing")) {

                    if (MissionRegistry.loseMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Losing");

                }

            }
            if (MissionRegistry.breedMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Breeding")) {

                    if (MissionRegistry.breedMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Breeding");

                }

            }
            if (MissionRegistry.hatchMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Hatching")) {

                    if (MissionRegistry.hatchMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Hatching");

                }

            }
            if (MissionRegistry.photographMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Photographing")) {

                    if (MissionRegistry.photographMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Photographing");

                }

            }
            if (MissionRegistry.raidMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Raiding")) {

                    if (MissionRegistry.raidMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Raiding");

                }

            }
            if (MissionRegistry.smeltMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Smelting")) {

                    if (MissionRegistry.smeltMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Smelting");

                }

            }

        } else {

            if (MissionRegistry.catchMissions.size() > 0) types.add("catching");
            if (MissionRegistry.killMissions.size() > 0) types.add("killing");
            if (MissionRegistry.defeatMissions.size() > 0) types.add("defeating");
            if (MissionRegistry.evolveMissions.size() > 0) types.add("evolving");
            if (MissionRegistry.mineMissions.size() > 0) types.add("mining");
            if (MissionRegistry.craftMissions.size() > 0) types.add("crafting");
            if (MissionRegistry.meleeMissions.size() > 0) types.add("meleeing");
            if (MissionRegistry.fishMissions.size() > 0) types.add("fishing");
            if (MissionRegistry.releaseMissions.size() > 0) types.add("releasing");
            if (MissionRegistry.loseMissions.size() > 0) types.add("losing");
            if (MissionRegistry.breedMissions.size() > 0) types.add("breeding");
            if (MissionRegistry.hatchMissions.size() > 0) types.add("hatching");
            if (MissionRegistry.photographMissions.size() > 0) types.add("photographing");
            if (MissionRegistry.raidMissions.size() > 0) types.add("raiding");
            if (MissionRegistry.smeltMissions.size() > 0) types.add("smelting");

        }
        if (types.size() == 0) {

            BetterMissions.logger.error("No missions at all to give players! This is probably not a good thing and should be reported to me if you ever see this message!");
            return;

        }
        String selectedType = RandomHelper.getRandomElementFromList(types);
        Map<String, String> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then;
        map.put("Progress", "0");
        switch (selectedType.toLowerCase()) {

            case "catching":
                List<CatchMission> possibleCatches = new ArrayList<>();
                if (MissionRegistry.catchMissions.size() > 1) {

                    for (CatchMission mission : MissionRegistry.catchMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleCatches.add(mission);

                        }

                    }

                } else {

                    possibleCatches.add(MissionRegistry.catchMissions.get(0));

                }
                CatchMission selectedCatch = RandomHelper.getRandomElementFromList(possibleCatches);
                then = now.plusSeconds(selectedCatch.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedCatch.getID(), map);
                break;

            case "killing":
                List<KillMission> possibleKills = new ArrayList<>();
                if (MissionRegistry.killMissions.size() > 1) {

                    for (KillMission mission : MissionRegistry.killMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleKills.add(mission);

                        }

                    }

                } else {

                    possibleKills.add(MissionRegistry.killMissions.get(0));

                }
                KillMission selectedKill = RandomHelper.getRandomElementFromList(possibleKills);
                then = now.plusSeconds(selectedKill.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedKill.getID(), map);
                break;

            case "defeating":
                List<DefeatMission> possibleDefeats = new ArrayList<>();
                if (MissionRegistry.defeatMissions.size() > 1) {

                    for (DefeatMission mission : MissionRegistry.defeatMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleDefeats.add(mission);

                        }

                    }

                } else {

                    possibleDefeats.add(MissionRegistry.defeatMissions.get(0));

                }
                DefeatMission selectedDefeat = RandomHelper.getRandomElementFromList(possibleDefeats);
                then = now.plusSeconds(selectedDefeat.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedDefeat.getID(), map);
                break;

            case "evolving":
                List<EvolveMission> possibleEvolves = new ArrayList<>();
                if (MissionRegistry.evolveMissions.size() > 1) {

                    for (EvolveMission mission : MissionRegistry.evolveMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleEvolves.add(mission);

                        }

                    }

                } else {

                    possibleEvolves.add(MissionRegistry.evolveMissions.get(0));

                }
                EvolveMission selectedEvolve = RandomHelper.getRandomElementFromList(possibleEvolves);
                then = now.plusSeconds(selectedEvolve.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedEvolve.getID(), map);
                break;

            case "mining":
                List<MineMission> possibleMines = new ArrayList<>();
                if (MissionRegistry.mineMissions.size() > 1) {

                    for (MineMission mission : MissionRegistry.mineMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleMines.add(mission);

                        }

                    }

                } else {

                    possibleMines.add(MissionRegistry.mineMissions.get(0));

                }
                MineMission selectedMine = RandomHelper.getRandomElementFromList(possibleMines);
                then = now.plusSeconds(selectedMine.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedMine.getID(), map);
                break;

            case "crafting":
                List<CraftMission> possibleCrafts = new ArrayList<>();
                if (MissionRegistry.craftMissions.size() > 1) {

                    for (CraftMission mission : MissionRegistry.craftMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleCrafts.add(mission);

                        }

                    }

                } else {

                    possibleCrafts.add(MissionRegistry.craftMissions.get(0));

                }
                CraftMission selectedCraft = RandomHelper.getRandomElementFromList(possibleCrafts);
                then = now.plusSeconds(selectedCraft.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedCraft.getID(), map);
                break;

            case "meleeing":
                List<MeleeMission> possibleMelee = new ArrayList<>();
                if (MissionRegistry.meleeMissions.size() > 1) {

                    for (MeleeMission mission : MissionRegistry.meleeMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleMelee.add(mission);

                        }

                    }

                } else {

                    possibleMelee.add(MissionRegistry.meleeMissions.get(0));

                }
                MeleeMission selectedMelee = RandomHelper.getRandomElementFromList(possibleMelee);
                then = now.plusSeconds(selectedMelee.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedMelee.getID(), map);
                break;

            case "fishing":
                List<FishMission> possibleFishing = new ArrayList<>();
                if (MissionRegistry.fishMissions.size() > 1) {

                    for (FishMission mission : MissionRegistry.fishMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleFishing.add(mission);

                        }

                    }

                } else {

                    possibleFishing.add(MissionRegistry.fishMissions.get(0));

                }
                FishMission selectedFishing = RandomHelper.getRandomElementFromList(possibleFishing);
                then = now.plusSeconds(selectedFishing.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedFishing.getID(), map);
                break;

            case "releasing":
                List<ReleaseMission> possibleReleasing = new ArrayList<>();
                if (MissionRegistry.releaseMissions.size() > 1) {

                    for (ReleaseMission mission : MissionRegistry.releaseMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleReleasing.add(mission);

                        }

                    }

                } else {

                    possibleReleasing.add(MissionRegistry.releaseMissions.get(0));

                }
                ReleaseMission selectedRelease = RandomHelper.getRandomElementFromList(possibleReleasing);
                then = now.plusSeconds(selectedRelease.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedRelease.getID(), map);
                break;

            case "losing":
                List<LoseMission> possibleLosing = new ArrayList<>();
                if (MissionRegistry.loseMissions.size() > 1) {

                    for (LoseMission mission : MissionRegistry.loseMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleLosing.add(mission);

                        }

                    }

                } else {

                    possibleLosing.add(MissionRegistry.loseMissions.get(0));

                }
                LoseMission selectedLose = RandomHelper.getRandomElementFromList(possibleLosing);
                then = now.plusSeconds(selectedLose.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedLose.getID(), map);
                break;

            case "breeding":
                List<BreedMission> possibleBreeding = new ArrayList<>();
                if (MissionRegistry.breedMissions.size() > 1) {

                    for (BreedMission mission : MissionRegistry.breedMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleBreeding.add(mission);

                        }

                    }

                } else {

                    possibleBreeding.add(MissionRegistry.breedMissions.get(0));

                }
                BreedMission selectedBreed = RandomHelper.getRandomElementFromList(possibleBreeding);
                then = now.plusSeconds(selectedBreed.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedBreed.getID(), map);
                break;

            case "hatching":
                List<HatchMission> possibleHatching = new ArrayList<>();
                if (MissionRegistry.hatchMissions.size() > 1) {

                    for (HatchMission mission : MissionRegistry.hatchMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleHatching.add(mission);

                        }

                    }

                } else {

                    possibleHatching.add(MissionRegistry.hatchMissions.get(0));

                }
                HatchMission selectedHatch = RandomHelper.getRandomElementFromList(possibleHatching);
                then = now.plusSeconds(selectedHatch.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedHatch.getID(), map);
                break;

            case "photographing":
                List<PhotographMission> possiblePhotograph = new ArrayList<>();
                if (MissionRegistry.photographMissions.size() > 1) {

                    for (PhotographMission mission : MissionRegistry.photographMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possiblePhotograph.add(mission);

                        }

                    }

                } else {

                    possiblePhotograph.add(MissionRegistry.photographMissions.get(0));

                }
                PhotographMission selectedPhotograph = RandomHelper.getRandomElementFromList(possiblePhotograph);
                then = now.plusSeconds(selectedPhotograph.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedPhotograph.getID(), map);
                break;

            case "raiding":
                List<RaidMission> possibleRaid = new ArrayList<>();
                if (MissionRegistry.raidMissions.size() > 1) {

                    for (RaidMission mission : MissionRegistry.raidMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleRaid.add(mission);

                        }

                    }

                } else {

                    possibleRaid.add(MissionRegistry.raidMissions.get(0));

                }
                RaidMission selectedRaid = RandomHelper.getRandomElementFromList(possibleRaid);
                then = now.plusSeconds(selectedRaid.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedRaid.getID(), map);
                break;

            case "smelting":
                List<SmeltMission> possibleSmelt = new ArrayList<>();
                if (MissionRegistry.smeltMissions.size() > 1) {

                    for (SmeltMission mission : MissionRegistry.smeltMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleSmelt.add(mission);

                        }

                    }

                } else {

                    possibleSmelt.add(MissionRegistry.smeltMissions.get(0));

                }
                SmeltMission selectedSmelt = RandomHelper.getRandomElementFromList(possibleSmelt);
                then = now.plusSeconds(selectedSmelt.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedSmelt.getID(), map);
                break;

        }

    }

    public static void removeMission (Account account, String id) {

        account.getMissionMap().entrySet().removeIf(e -> e.getKey().equalsIgnoreCase(id));

    }

    public static void saveProgress (Account account) {

        BetterMissions.playerConfigManager.getPlayerConfigNode(account.getUUID(), "Mission-Storage").setValue(account.getMissionMap());
        BetterMissions.playerConfigManager.savePlayer(account.getUUID());

    }

    public static void savePermanentMissionProgress (Account account) {

        BetterMissions.playerConfigManager.getPlayerConfigNode(account.getUUID(), "Completed-Missions").setValue(account.getCompletedPermanentMissions());
        BetterMissions.playerConfigManager.getPlayerConfigNode(account.getUUID(), "In-Progress-Permanent-Missions").setValue(account.getInProgressPermanentMissions());
        BetterMissions.playerConfigManager.savePlayer(account.getUUID());

    }

    public static void saveClaimedMissions (Account account) {

        BetterMissions.playerConfigManager.getPlayerConfigNode(account.getUUID(), "Claimed-Missions").setValue(account.getClaimedMissions());
        BetterMissions.playerConfigManager.savePlayer(account.getUUID());

    }

}
