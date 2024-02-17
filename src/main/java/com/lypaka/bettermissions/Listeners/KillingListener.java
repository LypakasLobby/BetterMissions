package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.KillMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class KillingListener {

    @SubscribeEvent
    public void onKill (BeatWildPixelmonEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = event.player;
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Pokemon pokemon = event.wpp.controlledPokemon.get(0).pokemon;
            Account account = AccountHandler.accountMap.get(uuid);
            KillMission mission = null;
            String id = AccountHandler.getCurrentMission(account);
            for (KillMission missions : MissionsHandler.killMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

        }

    }

}
