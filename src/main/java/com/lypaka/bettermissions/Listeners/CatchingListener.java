package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.CatchMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class CatchingListener {

    @SubscribeEvent
    public void onCapture (CaptureEvent.SuccessfulCapture event) throws ObjectMappingException {

        ServerPlayerEntity player = event.getPlayer();
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Pokemon pokemon = event.getPokemon().getPokemon();
            Account account = AccountHandler.accountMap.get(uuid);
            CatchMission mission = null;
            String id = AccountHandler.getCurrentMission(account);

            // Checking temporary missions first
            for (CatchMission missions : MissionsHandler.catchMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

        }

    }

}
