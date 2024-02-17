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

            if (MissionsHandler.catchMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Catching")) {

                    if (MissionsHandler.catchMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Catching");

                }

            }
            if (MissionsHandler.killMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Killing")) {

                    if (MissionsHandler.killMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Killing");

                }

            }
            if (MissionsHandler.defeatMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Defeating")) {

                    if (MissionsHandler.defeatMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Defeating");

                }

            }
            if (MissionsHandler.evolveMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Evolving")) {

                    if (MissionsHandler.evolveMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Evolving");

                }

            }
            if (MissionsHandler.fishMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Fishing")) {

                    if (MissionsHandler.fishMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Fishing");

                }

            }
            if (MissionsHandler.mineMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Mining")) {

                    if (MissionsHandler.mineMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Mining");

                }

            }
            if (MissionsHandler.craftMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Crafting")) {

                    if (MissionsHandler.craftMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Crafting");

                }

            }
            if (MissionsHandler.meleeMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Meleeing")) {

                    if (MissionsHandler.meleeMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Meleeing");

                }

            }
            if (MissionsHandler.fishMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Fishing")) {

                    if (MissionsHandler.fishMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Fishing");

                }

            }
            if (MissionsHandler.releaseMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Releasing")) {

                    if (MissionsHandler.releaseMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Releasing");

                }

            }
            if (MissionsHandler.loseMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Losing")) {

                    if (MissionsHandler.loseMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Losing");

                }

            }
            if (MissionsHandler.breedMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Breeding")) {

                    if (MissionsHandler.breedMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Breeding");

                }

            }
            if (MissionsHandler.hatchMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Hatching")) {

                    if (MissionsHandler.hatchMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Hatching");

                }

            }
            if (MissionsHandler.photographMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Photographing")) {

                    if (MissionsHandler.photographMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Photographing");

                }

            }
            if (MissionsHandler.raidMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Raiding")) {

                    if (MissionsHandler.raidMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Raiding");

                }

            }
            if (MissionsHandler.smeltMissions.size() > 0) {

                if (repID.equalsIgnoreCase("Smelting")) {

                    if (MissionsHandler.smeltMissions.size() > 1) {

                        types.add(repID);

                    }

                } else {

                    types.add("Smelting");

                }

            }

        } else {

            if (MissionsHandler.catchMissions.size() > 0) types.add("catching");
            if (MissionsHandler.killMissions.size() > 0) types.add("killing");
            if (MissionsHandler.defeatMissions.size() > 0) types.add("defeating");
            if (MissionsHandler.evolveMissions.size() > 0) types.add("evolving");
            if (MissionsHandler.mineMissions.size() > 0) types.add("mining");
            if (MissionsHandler.craftMissions.size() > 0) types.add("crafting");
            if (MissionsHandler.meleeMissions.size() > 0) types.add("meleeing");
            if (MissionsHandler.fishMissions.size() > 0) types.add("fishing");
            if (MissionsHandler.releaseMissions.size() > 0) types.add("releasing");
            if (MissionsHandler.loseMissions.size() > 0) types.add("losing");
            if (MissionsHandler.breedMissions.size() > 0) types.add("breeding");
            if (MissionsHandler.hatchMissions.size() > 0) types.add("hatching");
            if (MissionsHandler.photographMissions.size() > 0) types.add("photographing");
            if (MissionsHandler.raidMissions.size() > 0) types.add("raiding");
            if (MissionsHandler.smeltMissions.size() > 0) types.add("smelting");

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
                if (MissionsHandler.catchMissions.size() > 1) {

                    for (CatchMission mission : MissionsHandler.catchMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleCatches.add(mission);

                        }

                    }

                } else {

                    possibleCatches.add(MissionsHandler.catchMissions.get(0));

                }
                CatchMission selectedCatch = RandomHelper.getRandomElementFromList(possibleCatches);
                then = now.plusSeconds(selectedCatch.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedCatch.getID(), map);
                break;

            case "killing":
                List<KillMission> possibleKills = new ArrayList<>();
                if (MissionsHandler.killMissions.size() > 1) {

                    for (KillMission mission : MissionsHandler.killMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleKills.add(mission);

                        }

                    }

                } else {

                    possibleKills.add(MissionsHandler.killMissions.get(0));

                }
                KillMission selectedKill = RandomHelper.getRandomElementFromList(possibleKills);
                then = now.plusSeconds(selectedKill.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedKill.getID(), map);
                break;

            case "defeating":
                List<DefeatMission> possibleDefeats = new ArrayList<>();
                if (MissionsHandler.defeatMissions.size() > 1) {

                    for (DefeatMission mission : MissionsHandler.defeatMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleDefeats.add(mission);

                        }

                    }

                } else {

                    possibleDefeats.add(MissionsHandler.defeatMissions.get(0));

                }
                DefeatMission selectedDefeat = RandomHelper.getRandomElementFromList(possibleDefeats);
                then = now.plusSeconds(selectedDefeat.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedDefeat.getID(), map);
                break;

            case "evolving":
                List<EvolveMission> possibleEvolves = new ArrayList<>();
                if (MissionsHandler.evolveMissions.size() > 1) {

                    for (EvolveMission mission : MissionsHandler.evolveMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleEvolves.add(mission);

                        }

                    }

                } else {

                    possibleEvolves.add(MissionsHandler.evolveMissions.get(0));

                }
                EvolveMission selectedEvolve = RandomHelper.getRandomElementFromList(possibleEvolves);
                then = now.plusSeconds(selectedEvolve.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedEvolve.getID(), map);
                break;

            case "mining":
                List<MineMission> possibleMines = new ArrayList<>();
                if (MissionsHandler.mineMissions.size() > 1) {

                    for (MineMission mission : MissionsHandler.mineMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleMines.add(mission);

                        }

                    }

                } else {

                    possibleMines.add(MissionsHandler.mineMissions.get(0));

                }
                MineMission selectedMine = RandomHelper.getRandomElementFromList(possibleMines);
                then = now.plusSeconds(selectedMine.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedMine.getID(), map);
                break;

            case "crafting":
                List<CraftMission> possibleCrafts = new ArrayList<>();
                if (MissionsHandler.craftMissions.size() > 1) {

                    for (CraftMission mission : MissionsHandler.craftMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleCrafts.add(mission);

                        }

                    }

                } else {

                    possibleCrafts.add(MissionsHandler.craftMissions.get(0));

                }
                CraftMission selectedCraft = RandomHelper.getRandomElementFromList(possibleCrafts);
                then = now.plusSeconds(selectedCraft.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedCraft.getID(), map);
                break;

            case "meleeing":
                List<MeleeMission> possibleMelee = new ArrayList<>();
                if (MissionsHandler.meleeMissions.size() > 1) {

                    for (MeleeMission mission : MissionsHandler.meleeMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleMelee.add(mission);

                        }

                    }

                } else {

                    possibleMelee.add(MissionsHandler.meleeMissions.get(0));

                }
                MeleeMission selectedMelee = RandomHelper.getRandomElementFromList(possibleMelee);
                then = now.plusSeconds(selectedMelee.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedMelee.getID(), map);
                break;

            case "fishing":
                List<FishMission> possibleFishing = new ArrayList<>();
                if (MissionsHandler.fishMissions.size() > 1) {

                    for (FishMission mission : MissionsHandler.fishMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleFishing.add(mission);

                        }

                    }

                } else {

                    possibleFishing.add(MissionsHandler.fishMissions.get(0));

                }
                FishMission selectedFishing = RandomHelper.getRandomElementFromList(possibleFishing);
                then = now.plusSeconds(selectedFishing.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedFishing.getID(), map);
                break;

            case "releasing":
                List<ReleaseMission> possibleReleasing = new ArrayList<>();
                if (MissionsHandler.releaseMissions.size() > 1) {

                    for (ReleaseMission mission : MissionsHandler.releaseMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleReleasing.add(mission);

                        }

                    }

                } else {

                    possibleReleasing.add(MissionsHandler.releaseMissions.get(0));

                }
                ReleaseMission selectedRelease = RandomHelper.getRandomElementFromList(possibleReleasing);
                then = now.plusSeconds(selectedRelease.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedRelease.getID(), map);
                break;

            case "losing":
                List<LoseMission> possibleLosing = new ArrayList<>();
                if (MissionsHandler.loseMissions.size() > 1) {

                    for (LoseMission mission : MissionsHandler.loseMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleLosing.add(mission);

                        }

                    }

                } else {

                    possibleLosing.add(MissionsHandler.loseMissions.get(0));

                }
                LoseMission selectedLose = RandomHelper.getRandomElementFromList(possibleLosing);
                then = now.plusSeconds(selectedLose.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedLose.getID(), map);
                break;

            case "breeding":
                List<BreedMission> possibleBreeding = new ArrayList<>();
                if (MissionsHandler.breedMissions.size() > 1) {

                    for (BreedMission mission : MissionsHandler.breedMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleBreeding.add(mission);

                        }

                    }

                } else {

                    possibleBreeding.add(MissionsHandler.breedMissions.get(0));

                }
                BreedMission selectedBreed = RandomHelper.getRandomElementFromList(possibleBreeding);
                then = now.plusSeconds(selectedBreed.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedBreed.getID(), map);
                break;

            case "hatching":
                List<HatchMission> possibleHatching = new ArrayList<>();
                if (MissionsHandler.hatchMissions.size() > 1) {

                    for (HatchMission mission : MissionsHandler.hatchMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleHatching.add(mission);

                        }

                    }

                } else {

                    possibleHatching.add(MissionsHandler.hatchMissions.get(0));

                }
                HatchMission selectedHatch = RandomHelper.getRandomElementFromList(possibleHatching);
                then = now.plusSeconds(selectedHatch.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedHatch.getID(), map);
                break;

            case "photographing":
                List<PhotographMission> possiblePhotograph = new ArrayList<>();
                if (MissionsHandler.photographMissions.size() > 1) {

                    for (PhotographMission mission : MissionsHandler.photographMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possiblePhotograph.add(mission);

                        }

                    }

                } else {

                    possiblePhotograph.add(MissionsHandler.photographMissions.get(0));

                }
                PhotographMission selectedPhotograph = RandomHelper.getRandomElementFromList(possiblePhotograph);
                then = now.plusSeconds(selectedPhotograph.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedPhotograph.getID(), map);
                break;

            case "raiding":
                List<RaidMission> possibleRaid = new ArrayList<>();
                if (MissionsHandler.raidMissions.size() > 1) {

                    for (RaidMission mission : MissionsHandler.raidMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleRaid.add(mission);

                        }

                    }

                } else {

                    possibleRaid.add(MissionsHandler.raidMissions.get(0));

                }
                RaidMission selectedRaid = RandomHelper.getRandomElementFromList(possibleRaid);
                then = now.plusSeconds(selectedRaid.getTimer());
                map.put("Expires", then.toString());
                account.getMissionMap().put(selectedRaid.getID(), map);
                break;

            case "smelting":
                List<SmeltMission> possibleSmelt = new ArrayList<>();
                if (MissionsHandler.smeltMissions.size() > 1) {

                    for (SmeltMission mission : MissionsHandler.smeltMissions) {

                        if (!mission.getID().equalsIgnoreCase(currentMission)) {

                            possibleSmelt.add(mission);

                        }

                    }

                } else {

                    possibleSmelt.add(MissionsHandler.smeltMissions.get(0));

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
