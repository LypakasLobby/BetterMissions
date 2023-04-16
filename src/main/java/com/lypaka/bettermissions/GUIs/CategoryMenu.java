package com.lypaka.bettermissions.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.ItemStackBuilder;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// I know I'm doing this in a very dumb way, but players won't be opening these menus quickly enough for it to really matter...and I'm lazy
public class CategoryMenu {

    public static void open (ServerPlayerEntity player, String category) throws ObjectMappingException {

        Account account = AccountHandler.accountMap.get(player.getUniqueID());
        int rows = Integer.parseInt(ConfigGetters.missionLogMap.get(category).get("GUI-Rows"));
        String title = ConfigGetters.missionLogMap.get(category).get("Display-Title");
        ChestTemplate template = ChestTemplate.builder(rows).build();
        GooeyPage page = GooeyPage.builder()
                .title(FancyText.getFormattedText(title))
                .template(template)
                .build();

        Map<String, Map<String, String>> map = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {

            int slot = Integer.parseInt(entry.getKey().replace("Slot-", ""));
            Map<String, Map<String, String>> actualMap = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey()).getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            for (Map.Entry<String, Map<String, String>> aEntry : actualMap.entrySet()) {

                String missionID = aEntry.getKey();
                String displayID = aEntry.getValue().get("Display-ID");
                ItemStack stack;
                if (displayID.contains("pixelmon:pixelmon_sprite")) {

                    String species = displayID.replace("pixelmon:pixelmon_sprite/", "");
                    stack = SpriteItemHelper.getPhoto(PokemonBuilder.builder().species(species).build());

                } else {

                    stack = ItemStackBuilder.buildFromStringID(displayID);

                }
                stack.setDisplayName(FancyText.getFormattedText(aEntry.getValue().get("Display-Name")));
                List<String> loreToUse;
                if (account.getCompletedPermanentMissions().contains(missionID)) {

                    loreToUse = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Lore-Completed").getList(TypeToken.of(String.class));

                } else {

                    loreToUse = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Lore-Not-Completed").getList(TypeToken.of(String.class));

                }
                ListNBT lore = new ListNBT();
                for (String s : loreToUse) {

                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

                }
                stack.getOrCreateChildTag("display").put("Lore", lore);
                Button gooeyButton;
                if (aEntry.getValue().containsKey("Button")) {

                    if (!BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Commands").isVirtual()) {

                        List<String> commands = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Commands").getList(TypeToken.of(String.class));
                        List<String> missions = new ArrayList<>();
                        if (!BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Missions").isVirtual()) {

                            missions = BetterMissions.configManager.getConfigNode(2, "Missions-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Missions").getList(TypeToken.of(String.class));

                        }
                        List<String> permissions = new ArrayList<>();
                        if (!BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Permissions").isVirtual()) {

                            permissions = BetterMissions.configManager.getConfigNode(2, "Mission-Logs", category, "Missions", entry.getKey(), missionID, "Button", "Permissions").getList(TypeToken.of(String.class));

                        }
                        boolean canClick = true;
                        for (String s : missions) {

                            if (!account.getCompletedPermanentMissions().contains(s)) {

                                canClick = false;
                                break;

                            }

                        }
                        if (canClick) {

                            for (String s : permissions) {

                                if (!PermissionHandler.hasPermission(player, s)) {

                                    canClick = false;
                                    break;

                                }

                            }

                        }
                        if (canClick) {

                            if (!AccountHandler.getClaimedMissions(account).contains(missionID)) {

                                gooeyButton = GooeyButton.builder()
                                        .display(stack)
                                        .onClick(() -> {

                                            AccountHandler.getClaimedMissions(account).add(missionID);
                                            for (String s : commands) {

                                                player.getServer().getCommandManager().handleCommand(
                                                        player.getServer().getCommandSource(),
                                                        s.replace("%player%", player.getName().getString())
                                                );

                                            }
                                            try {

                                                open(player, category);

                                            } catch (ObjectMappingException e) {

                                                e.printStackTrace();

                                            }

                                        })
                                        .build();

                            } else {

                                gooeyButton = GooeyButton.builder()
                                        .display(stack)
                                        .build();

                            }

                        } else {

                            gooeyButton = GooeyButton.builder()
                                    .display(stack)
                                    .build();

                        }

                    } else {

                        gooeyButton = GooeyButton.builder()
                                .display(stack)
                                .build();

                    }

                } else {

                    gooeyButton = GooeyButton.builder()
                            .display(stack)
                            .build();

                }

                page.getTemplate().getSlot(slot).setButton(gooeyButton);

            }

        }

        UIManager.openUIForcefully(player, page);

    }

}
