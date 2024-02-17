package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.CraftMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import com.pixelmonmod.pixelmon.api.storage.NbtKeys;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class CraftingListeners {

    @SubscribeEvent
    public void onBrew (PlayerBrewedPotionEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        String itemID = event.getStack().getItem().getRegistryName().toString();
        int quantity = event.getStack().getCount();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            CraftMission mission = null;
            for (CraftMission missions : MissionsHandler.craftMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, null, itemID, mission, quantity);

        }

    }

    @SubscribeEvent
    public void onCraft (PlayerEvent.ItemCraftedEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        String itemID = event.getCrafting().getItem().getRegistryName().toString();
        int quantity = event.getCrafting().getCount();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            CraftMission mission = null;
            for (CraftMission missions : MissionsHandler.craftMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            if (itemID.equalsIgnoreCase("pixelmon:poke_ball")) {

                itemID = getActualPokeBallNameBecausePixelmonChangedThisForLiterallyNoReasonLOL(event.getCrafting());

            }
            MissionsHandler.runMissionProgressCheck(player, null, itemID, mission, quantity);

        }

    }

    private static String getActualPokeBallNameBecausePixelmonChangedThisForLiterallyNoReasonLOL (ItemStack item) {

        String ball = "poke_ball";
        if (item.hasTag()) {

            ball = PokeBallRegistry.getPokeBall(item.getTag().getString(NbtKeys.POKE_BALL_ID)).getValue().get().getName();

        }

        return "pixelmon:" + ball.replace(" ", "_").toLowerCase();

    }

}
