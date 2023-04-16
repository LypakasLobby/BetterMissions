package com.lypaka.bettermissions.Requirements;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemRequirement {

    private final Map<String, Map<String, String>> requirements;
    private final Map<String, Integer> itemsToRemove;
    private final ServerPlayerEntity player;

    public ItemRequirement (Map<String, Map<String, String>> requirements, ServerPlayerEntity player) {

        this.requirements = requirements;
        this.itemsToRemove = new HashMap<>();
        this.player = player;

    }

    public Map<String, Integer> getItemsToRemove() {

        return this.itemsToRemove;

    }

    public boolean passes() {

        Map<String, Boolean> passesMap = new HashMap<>();
        boolean passes = true;
        if (!this.requirements.isEmpty()) {

            for (Map.Entry<String, Map<String, String>> entry : this.requirements.entrySet()) {

                String itemID = entry.getKey();
                passesMap.put(itemID, false);
                int amount = Integer.parseInt(entry.getValue().get("Amount"));
                String displayName = "";
                if (entry.getValue().containsKey("Display-Name")) {

                    displayName = entry.getValue().get("Display-Name");

                }
                boolean sacrifice = false;
                if (entry.getValue().containsKey("Consumes")) {

                    sacrifice = Boolean.parseBoolean(entry.getValue().get("Consumes"));

                }

                for (ItemStack item : this.player.inventory.mainInventory) {

                    String id = item.getItem().getRegistryName().toString();
                    if (itemID.equalsIgnoreCase(id)) {

                        if (item.getCount() >= amount) {

                            String name = item.getDisplayName().getString().replace("§0", "").replace("§1", "").replace("§2", "").replace("§3", "")
                                    .replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "")
                                    .replace("§8", "").replace("§9", "").replace("§a", "").replace("§b", "")
                                    .replace("§c", "").replace("§d", "").replace("§e", "").replace("§f", "")
                                    .replace("§k", "").replace("§l", "").replace("§m", "").replace("§n", "")
                                    .replace("§o", "").replace("§r", "");
                            String needed = displayName.replace("§0", "").replace("§1", "").replace("§2", "").replace("§3", "")
                                    .replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "")
                                    .replace("§8", "").replace("§9", "").replace("§a", "").replace("§b", "")
                                    .replace("§c", "").replace("§d", "").replace("§e", "").replace("§f", "")
                                    .replace("§k", "").replace("§l", "").replace("§m", "").replace("§n", "")
                                    .replace("§o", "").replace("§r", "");
                            if (name.equalsIgnoreCase(needed)) {

                                passesMap.put(itemID, true);
                                if (sacrifice) {

                                    this.itemsToRemove.put(id, amount);

                                }

                            }

                        }

                    }

                }

            }

            for (Map.Entry<String, Boolean> entry : passesMap.entrySet()) {

                if (!entry.getValue()) {

                    passes = false;
                    break;

                }

            }

            return passes;

        }

        return true;

    }

}
