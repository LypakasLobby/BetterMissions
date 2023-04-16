package com.lypaka.bettermissions.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.ItemStackBuilder;
import com.lypaka.lypakautils.PermissionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class MainLogMenu {

    public static void openMenu (ServerPlayerEntity player) {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.logMainMenuRows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedText(ConfigGetters.logMenuTitle))
                .build();

        if (!ConfigGetters.logBorderSlots.equalsIgnoreCase("")) {

            String[] borderSlots = ConfigGetters.logBorderSlots.split(", ");
            String borderID = ConfigGetters.logBorderID;
            ItemStack stack = ItemStackBuilder.buildFromStringID(borderID);
            stack.setDisplayName(FancyText.getFormattedText(""));
            for (String s : borderSlots) {

                page.getTemplate().getSlot(Integer.parseInt(s)).setButton(GooeyButton.builder().display(stack).build());

            }

        }

        for (Map.Entry<String, Map<String, String>> entry : ConfigGetters.logMainSlotMap.entrySet()) {

            int slot = Integer.parseInt(entry.getKey().replace("Slot-", ""));
            Map<String, String> info = entry.getValue();
            String permission = info.get("Permission");
            if (!permission.equalsIgnoreCase("")) {

                if (PermissionHandler.hasPermission(player, permission)) {

                    String category = info.get("Category");
                    String id = ConfigGetters.missionLogMap.get(category).get("Display-ID");
                    String displayName = ConfigGetters.missionLogMap.get(category).get("Display-Name");
                    ItemStack categoryItem = ItemStackBuilder.buildFromStringID(id);
                    categoryItem.setDisplayName(FancyText.getFormattedText(displayName));
                    page.getTemplate().getSlot(slot).setButton(
                            GooeyButton.builder()
                                    .display(categoryItem)
                                    .onClick(() -> {

                                        try {

                                            CategoryMenu.open(player, category);

                                        } catch (ObjectMappingException e) {

                                            e.printStackTrace();

                                        }

                                    })
                                    .build()
                    );

                }

            } else {

                String category = info.get("Category");
                String id = ConfigGetters.missionLogMap.get(category).get("Display-ID");
                String displayName = ConfigGetters.missionLogMap.get(category).get("Display-Name");
                ItemStack categoryItem = ItemStackBuilder.buildFromStringID(id);
                categoryItem.setDisplayName(FancyText.getFormattedText(displayName));
                page.getTemplate().getSlot(slot).setButton(
                        GooeyButton.builder()
                                .display(categoryItem)
                                .onClick(() -> {

                                    try {

                                        CategoryMenu.open(player, category);

                                    } catch (ObjectMappingException e) {

                                        e.printStackTrace();

                                    }

                                })
                                .build()
                );

            }

        }

        UIManager.openUIForcefully(player, page);

    }

}
