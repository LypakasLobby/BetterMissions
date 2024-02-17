package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.HatchMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class HatchListener {

    @SubscribeEvent
    public void onEggHatch (EggHatchEvent.Post event) throws ObjectMappingException {

        ServerPlayerEntity player = event.getPlayer();
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Pokemon pokemon = event.getPokemon();
            Account account = AccountHandler.accountMap.get(uuid);
            HatchMission mission = null;
            String id = AccountHandler.getCurrentMission(account);

            // Checking temporary missions first
            for (HatchMission missions : MissionsHandler.hatchMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, pokemon, null, mission, 1);

        }

    }

}
