package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Missions.SmeltMission;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class SmeltListener {

    @SubscribeEvent
    public void onSmelt (PlayerEvent.ItemSmeltedEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        String itemID = event.getSmelting().getItem().getRegistryName().toString();
        int quantity = event.getSmelting().getCount();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            SmeltMission mission = null;
            for (SmeltMission missions : MissionsHandler.smeltMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, null, itemID, mission, quantity);

        }

    }

}
