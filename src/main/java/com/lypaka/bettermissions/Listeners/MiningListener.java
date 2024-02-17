package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MineMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class MiningListener {

    @SubscribeEvent
    public void onMine (BlockEvent.BreakEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            MineMission mission = null;
            for (MineMission missions : MissionsHandler.mineMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            String blockID = event.getState().getBlock().getRegistryName().toString();
            MissionsHandler.runMissionProgressCheck(player, null, blockID, mission, 1);

        }

    }

}
