package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.FishMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class FishingListener {

    @SubscribeEvent
    public void onFishingReel (FishingEvent.Reel event) throws ObjectMappingException {

        if (event.optEntity.isPresent()) {

            if (event.isPokemon()) {

                PixelmonEntity pokemon = (PixelmonEntity) event.optEntity.get();
                pokemon.getPokemon().getPersistentData().put("IsFromFishing", StringNBT.valueOf("true")); // used for checking missions for fish Pokemon
                ServerPlayerEntity player = event.player;
                UUID uuid = player.getUniqueID();
                if (AccountHandler.accountMap.containsKey(uuid)) {

                    Account account = AccountHandler.accountMap.get(uuid);
                    FishMission mission = null;
                    String id = AccountHandler.getCurrentMission(account);
                    for (FishMission missions : MissionsHandler.fishMissions) {

                        if (missions.getID().equalsIgnoreCase(id)) {

                            mission = missions;
                            break;

                        }

                    }
                    MissionsHandler.runMissionProgressCheck(player, pokemon.getPokemon(), null, mission, 1);

                }

            }

        }

    }

}
