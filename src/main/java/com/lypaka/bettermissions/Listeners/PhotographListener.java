package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Missions.PhotographMission;
import com.pixelmonmod.pixelmon.api.events.CameraEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class PhotographListener {

    @SubscribeEvent
    public void onPhotograph (CameraEvent.TakePhoto event) throws ObjectMappingException {

        ServerPlayerEntity player = event.player;
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Pokemon pokemon = event.pixelmon.getPokemon();
            Account account = AccountHandler.accountMap.get(uuid);
            PhotographMission mission = null;
            String id = AccountHandler.getCurrentMission(account);

            // Checking temporary missions first
            for (PhotographMission missions : MissionsHandler.photographMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

        }

    }

}
