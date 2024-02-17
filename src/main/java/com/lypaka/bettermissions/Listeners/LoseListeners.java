package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.LoseMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.LostToTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.LostToWildPixelmonEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class LoseListeners {

    @SubscribeEvent
    public void onLoseToTrainer (LostToTrainerEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = event.player;
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            LoseMission mission = null;
            String id = AccountHandler.getCurrentMission(account);
            for (LoseMission missions : MissionsHandler.loseMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, null, "npc", mission, 1);

        }

    }

    @SubscribeEvent
    public void onLoseToPokemon (LostToWildPixelmonEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = event.player;
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            LoseMission mission = null;
            String id = AccountHandler.getCurrentMission(account);
            for (LoseMission missions : MissionsHandler.loseMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, null, "pokemon", mission, 1);

        }

    }

}
