package com.lypaka.bettermissions.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.Missions.*;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.ItemStackBuilder;
import com.lypaka.lypakautils.LogicalPixelmonMoneyHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MissionsMenu {

    public static void open (ServerPlayerEntity player) {

        ChestTemplate template = ChestTemplate.builder(3).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.missionsMenuDisplayName))
                .build();

        int[] borderSlots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int i : borderSlots) {

            page.getTemplate().getSlot(i).setButton(getBorderButton());

        }

        // 12, 14
        Account account = AccountHandler.accountMap.get(player.getUniqueID());
        String missionID = AccountHandler.getCurrentMission(account);
        String repID = getRepresentationIDFromMissionID(missionID);
        if (repID != null) {

            String itemID = ConfigGetters.missionItemRepresentativeMap.get(repID);
            ItemStack icon = ItemStackBuilder.buildFromStringID(itemID);
            icon.setDisplayName(FancyText.getFormattedText(getMissionNameFromID(missionID)));
            List<String> lore = getMissionDisplayLore(missionID);
            ListNBT listNBT = new ListNBT();
            LocalDateTime expires = AccountHandler.getExpiration(account, missionID);
            LocalDateTime now = LocalDateTime.now();
            long seconds = ChronoUnit.SECONDS.between(now, expires);
            int progress = AccountHandler.getMissionProgress(account, missionID);
            int required = getMissionRequiredAmount(missionID);
            listNBT.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eProgress:&a " + progress + "&e/&a" + required))));
            listNBT.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eExpires:&a " + seconds + " &eseconds"))));
            listNBT.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
            if (lore != null) {

                for (String s : lore) {

                    listNBT.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

                }

                icon.getOrCreateChildTag("display").put("Lore", listNBT);

            }

            GooeyButton repButton = GooeyButton.builder().display(icon).build();
            page.getTemplate().getSlot(12).setButton(repButton);

            ItemStack rerollIcon = ItemStackBuilder.buildFromStringID(ConfigGetters.missionRerollItemID);
            rerollIcon.setDisplayName(FancyText.getFormattedText(ConfigGetters.missionRerollDisplayName));
            List<String> rerollLoreString = ConfigGetters.rerollLore;
            ListNBT rerollLore = new ListNBT();
            for (String s : rerollLoreString) {

                rerollLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s.replace("%cost%", String.valueOf(ConfigGetters.rerollCost))))));

            }
            rerollIcon.getOrCreateChildTag("display").put("Lore", rerollLore);
            Button rerollButton;
            if (ConfigGetters.rerollsEnabled) {

                double balance = LogicalPixelmonMoneyHandler.getBalance(player.getUniqueID());
                if (balance >= ConfigGetters.rerollCost || ConfigGetters.rerollCost == 0) {

                    rerollButton = GooeyButton.builder()
                            .display(rerollIcon)
                            .onClick(() -> {

                                LogicalPixelmonMoneyHandler.remove(player.getUniqueID(), ConfigGetters.rerollCost);
                                AccountHandler.assignRandomMission(account);
                                AccountHandler.removeMission(account, missionID);
                                AccountHandler.saveProgress(account);
                                player.sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), player.getUniqueID());
                                open(player);

                            })
                            .build();

                } else {

                    rerollButton = GooeyButton.builder().display(rerollIcon).build();

                }

            } else {

                rerollButton = GooeyButton.builder().display(rerollIcon).build();

            }

            page.getTemplate().getSlot(14).setButton(rerollButton);
            UIManager.openUIForcefully(player, page);

        } else {

            if (missionID.equalsIgnoreCase("")) {

                player.sendMessage(FancyText.getFormattedText("&cYou don't currently have a mission to display!"), player.getUniqueID());

            } else {

                player.sendMessage(FancyText.getFormattedText("&cAn error occurred when trying to open the menu! Please report this message to staff!"), player.getUniqueID());

            }

        }

    }

    private static int getMissionRequiredAmount (String id) {

        int required = -1;
        for (CatchMission catchMissions : MissionRegistry.catchMissions) {

            if (catchMissions.getID().equalsIgnoreCase(id)) {

                required = catchMissions.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (CraftMission craftMission : MissionRegistry.craftMissions) {

            if (craftMission.getID().equalsIgnoreCase(id)) {

                required = craftMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (DefeatMission defeatMission : MissionRegistry.defeatMissions) {

            if (defeatMission.getID().equalsIgnoreCase(id)) {

                required = defeatMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (EvolveMission evolveMission : MissionRegistry.evolveMissions) {

            if (evolveMission.getID().equalsIgnoreCase(id)) {

                required = evolveMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (KillMission killMission : MissionRegistry.killMissions) {

            if (killMission.getID().equalsIgnoreCase(id)) {

                required = killMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (MineMission mineMission : MissionRegistry.mineMissions) {

            if (mineMission.getID().equalsIgnoreCase(id)) {

                required = mineMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (FishMission fishMission : MissionRegistry.fishMissions) {

            if (fishMission.getID().equalsIgnoreCase(id)) {

                required = fishMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (LoseMission loseMission : MissionRegistry.loseMissions) {

            if (loseMission.getID().equalsIgnoreCase(id)) {

                required = loseMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (MeleeMission meleeMission : MissionRegistry.meleeMissions) {

            if (meleeMission.getID().equalsIgnoreCase(id)) {

                required = meleeMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (ReleaseMission releaseMission : MissionRegistry.releaseMissions) {

            if (releaseMission.getID().equalsIgnoreCase(id)) {

                required = releaseMission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (BreedMission mission : MissionRegistry.breedMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                required = mission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (HatchMission mission : MissionRegistry.hatchMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                required = mission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (PhotographMission mission : MissionRegistry.photographMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                required = mission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (RaidMission mission : MissionRegistry.raidMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                required = mission.getAmount();
                break;

            }

        }
        if (required > -1) return required;
        for (SmeltMission mission : MissionRegistry.smeltMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                required = mission.getAmount();
                break;

            }

        }

        return required;


    }

    private static List<String> getMissionDisplayLore (String id) {

        List<String> lore = null;
        for (CatchMission catchMissions : MissionRegistry.catchMissions) {

            if (catchMissions.getID().equalsIgnoreCase(id)) {

                lore = catchMissions.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (CraftMission craftMission : MissionRegistry.craftMissions) {

            if (craftMission.getID().equalsIgnoreCase(id)) {

                lore = craftMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (DefeatMission defeatMission : MissionRegistry.defeatMissions) {

            if (defeatMission.getID().equalsIgnoreCase(id)) {

                lore = defeatMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (EvolveMission evolveMission : MissionRegistry.evolveMissions) {

            if (evolveMission.getID().equalsIgnoreCase(id)) {

                lore = evolveMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (KillMission killMission : MissionRegistry.killMissions) {

            if (killMission.getID().equalsIgnoreCase(id)) {

                lore = killMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (MineMission mineMission : MissionRegistry.mineMissions) {

            if (mineMission.getID().equalsIgnoreCase(id)) {

                lore = mineMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (FishMission fishMission : MissionRegistry.fishMissions) {

            if (fishMission.getID().equalsIgnoreCase(id)) {

                lore = fishMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (LoseMission loseMission : MissionRegistry.loseMissions) {

            if (loseMission.getID().equalsIgnoreCase(id)) {

                lore = loseMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (MeleeMission meleeMission : MissionRegistry.meleeMissions) {

            if (meleeMission.getID().equalsIgnoreCase(id)) {

                lore = meleeMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (ReleaseMission releaseMission : MissionRegistry.releaseMissions) {

            if (releaseMission.getID().equalsIgnoreCase(id)) {

                lore = releaseMission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (BreedMission mission : MissionRegistry.breedMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                lore = mission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (HatchMission mission : MissionRegistry.hatchMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                lore = mission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (PhotographMission mission : MissionRegistry.photographMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                lore = mission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (RaidMission mission : MissionRegistry.raidMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                lore = mission.getDisplayLore();
                break;

            }

        }
        if (lore != null) return lore;
        for (SmeltMission mission : MissionRegistry.smeltMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                lore = mission.getDisplayLore();
                break;

            }

        }

        return lore;

    }

    private static String getMissionNameFromID (String id) {

        String name = null;
        for (CatchMission catchMissions : MissionRegistry.catchMissions) {

            if (catchMissions.getID().equalsIgnoreCase(id)) {

                name = catchMissions.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (CraftMission craftMission : MissionRegistry.craftMissions) {

            if (craftMission.getID().equalsIgnoreCase(id)) {

                name = craftMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (DefeatMission defeatMission : MissionRegistry.defeatMissions) {

            if (defeatMission.getID().equalsIgnoreCase(id)) {

                name = defeatMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (EvolveMission evolveMission : MissionRegistry.evolveMissions) {

            if (evolveMission.getID().equalsIgnoreCase(id)) {

                name = evolveMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (KillMission killMission : MissionRegistry.killMissions) {

            if (killMission.getID().equalsIgnoreCase(id)) {

                name = killMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (MineMission mineMission : MissionRegistry.mineMissions) {

            if (mineMission.getID().equalsIgnoreCase(id)) {

                name = mineMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (FishMission fishMission : MissionRegistry.fishMissions) {

            if (fishMission.getID().equalsIgnoreCase(id)) {

                name = fishMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (LoseMission loseMission : MissionRegistry.loseMissions) {

            if (loseMission.getID().equalsIgnoreCase(id)) {

                name = loseMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (MeleeMission meleeMission : MissionRegistry.meleeMissions) {

            if (meleeMission.getID().equalsIgnoreCase(id)) {

                name = meleeMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (ReleaseMission releaseMission : MissionRegistry.releaseMissions) {

            if (releaseMission.getID().equalsIgnoreCase(id)) {

                name = releaseMission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (BreedMission mission : MissionRegistry.breedMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                name = mission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (HatchMission mission : MissionRegistry.hatchMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                name = mission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (PhotographMission mission : MissionRegistry.photographMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                name = mission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (RaidMission mission : MissionRegistry.raidMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                name = mission.getDisplayName();
                break;

            }

        }
        if (name != null) return name;
        for (SmeltMission mission : MissionRegistry.smeltMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                name = mission.getDisplayName();
                break;

            }

        }

        return name;

    }

    public static String getRepresentationIDFromMissionID (String id) {

        String repID = null;
        for (CatchMission catchMissions : MissionRegistry.catchMissions) {

            if (catchMissions.getID().equalsIgnoreCase(id)) {

                repID = "Catching";
                break;

            }

        }
        if (repID != null) return repID;
        for (CraftMission craftMission : MissionRegistry.craftMissions) {

            if (craftMission.getID().equalsIgnoreCase(id)) {

                repID = "Crafting";
                break;

            }

        }
        if (repID != null) return repID;
        for (DefeatMission defeatMission : MissionRegistry.defeatMissions) {

            if (defeatMission.getID().equalsIgnoreCase(id)) {

                repID = "Defeating";
                break;

            }

        }
        if (repID != null) return repID;
        for (EvolveMission evolveMission : MissionRegistry.evolveMissions) {

            if (evolveMission.getID().equalsIgnoreCase(id)) {

                repID = "Evolving";
                break;

            }

        }
        if (repID != null) return repID;
        for (KillMission killMission : MissionRegistry.killMissions) {

            if (killMission.getID().equalsIgnoreCase(id)) {

                repID = "Killing";
                break;

            }

        }
        if (repID != null) return repID;
        for (MineMission mineMission : MissionRegistry.mineMissions) {

            if (mineMission.getID().equalsIgnoreCase(id)) {

                repID = "Mining";
                break;

            }

        }
        if (repID != null) return repID;
        for (MeleeMission meleeMission : MissionRegistry.meleeMissions) {

            if (meleeMission.getID().equalsIgnoreCase(id)) {

                repID = "Meleeing";
                break;

            }

        }
        if (repID != null) return repID;
        for (FishMission fishMission : MissionRegistry.fishMissions) {

            if (fishMission.getID().equalsIgnoreCase(id)) {

                repID = "Fishing";
                break;

            }

        }
        if (repID != null) return repID;
        for (ReleaseMission releaseMission : MissionRegistry.releaseMissions) {

            if (releaseMission.getID().equalsIgnoreCase(id)) {

                repID = "Releasing";
                break;

            }

        }
        if (repID != null) return repID;
        for (LoseMission loseMission : MissionRegistry.loseMissions) {

            if (loseMission.getID().equalsIgnoreCase(id)) {

                repID = "Losing";
                break;

            }

        }
        if (repID != null) return repID;
        for (BreedMission breedMission : MissionRegistry.breedMissions) {

            if (breedMission.getID().equalsIgnoreCase(id)) {

                repID = "Breeding";
                break;

            }

        }
        if (repID != null) return repID;
        for (HatchMission mission : MissionRegistry.hatchMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                repID = "Hatching";
                break;

            }

        }
        if (repID != null) return repID;
        for (PhotographMission mission : MissionRegistry.photographMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                repID = "Photographing";
                break;

            }

        }
        if (repID != null) return repID;
        for (RaidMission mission : MissionRegistry.raidMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                repID = "Raiding";
                break;

            }

        }
        if (repID != null) return repID;
        for (SmeltMission mission : MissionRegistry.smeltMissions) {

            if (mission.getID().equalsIgnoreCase(id)) {

                repID = "Smelting";
                break;

            }

        }

        return repID;

    }

    private static Button getBorderButton() {

        ItemStack item = ItemStackBuilder.buildFromStringID(ConfigGetters.guiBorderID);
        item.setDisplayName(FancyText.getFormattedText(""));
        return GooeyButton.builder().display(item).build();

    }

}
