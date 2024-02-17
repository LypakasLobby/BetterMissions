package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Missions.ReviveMission;
import com.pixelmonmod.pixelmon.api.events.PokemonReceivedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class ReviveListener {

    @SubscribeEvent
    public void onRevive (PokemonReceivedEvent event) throws ObjectMappingException {

        if (event.getCause().equalsIgnoreCase("fossil")) {

            ServerPlayerEntity player = event.getPlayer();
            UUID uuid = player.getUniqueID();
            if (AccountHandler.accountMap.containsKey(uuid)) {

                Account account = AccountHandler.accountMap.get(uuid);
                Pokemon pokemon = event.getPokemon();
                ReviveMission mission = null;
                String id = AccountHandler.getCurrentMission(account);
                for (ReviveMission missions : MissionsHandler.reviveMissions) {

                    if (missions.getID().equalsIgnoreCase(id)) {

                        mission = missions;
                        break;

                    }

                }
                MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

            }

        }

    }

}
