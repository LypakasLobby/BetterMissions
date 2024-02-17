package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.EvolveMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.EvolveEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class EvolveListener {

    @SubscribeEvent
    public void onEvolve (EvolveEvent.Post event) throws ObjectMappingException {

        ServerPlayerEntity player = event.getPlayer();
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Pokemon pokemon = event.getPokemon();
            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            EvolveMission mission = null;
            for (EvolveMission missions : MissionsHandler.evolveMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

        }

    }

}
