package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Missions.RaidMission;
import com.pixelmonmod.pixelmon.api.events.raids.EndRaidEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class RaidListener {

    @SubscribeEvent
    public void onRaidWin (EndRaidEvent event) throws ObjectMappingException {

        for (BattleParticipant bp : event.getAllyParticipants()) {

            if (bp instanceof PlayerParticipant) {

                ServerPlayerEntity player = ((PlayerParticipant) bp).player;
                UUID uuid = player.getUniqueID();
                if (AccountHandler.accountMap.containsKey(uuid)) {

                    Pokemon pokemon = PokemonBuilder.builder().species(event.getRaid().getSpecies()).form(event.getRaid().getForm()).build();
                    Account account = AccountHandler.accountMap.get(uuid);
                    RaidMission mission = null;
                    String id = AccountHandler.getCurrentMission(account);

                    // Checking temporary missions first
                    for (RaidMission missions : MissionsHandler.raidMissions) {

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

}
