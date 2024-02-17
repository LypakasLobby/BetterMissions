package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.MeleeMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class MeleeListener {

    @SubscribeEvent
    public void onKill (LivingDeathEvent event) throws ObjectMappingException {

        if (event.getSource().getTrueSource() instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getTrueSource();
            if (!player.world.isRemote) {

                UUID uuid = player.getUniqueID();
                if (AccountHandler.accountMap.containsKey(uuid)) {

                    Account account = AccountHandler.accountMap.get(uuid);
                    String entityID;
                    if (event.getEntityLiving() instanceof PixelmonEntity) {

                        PixelmonEntity pokemon = (PixelmonEntity) event.getEntityLiving();
                        String shiny = pokemon.getPokemon().isShiny() ? "-shiny" : "";
                        entityID = "pixelmon:pixelmon/" + pokemon.getLocalizedName() + shiny;

                    } else {

                        entityID = event.getEntityLiving().getEntityString();

                    }
                    MeleeMission mission = null;
                    String id = AccountHandler.getCurrentMission(account);
                    for (MeleeMission missions : MissionsHandler.meleeMissions) {

                        if (missions.getID().equalsIgnoreCase(id)) {

                            mission = missions;
                            break;

                        }

                    }
                    MissionsHandler.runMissionProgressCheck(player, null, entityID, mission, 1);

                }

            }

        }

    }

}
