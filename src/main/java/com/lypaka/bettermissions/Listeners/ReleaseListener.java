package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.API.MissionCompletedEvent;
import com.lypaka.bettermissions.API.MissionRequirementsEvent;
import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.ConfigGetters;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Missions.ReleaseMission;
import com.lypaka.bettermissions.Requirements.*;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import com.pixelmonmod.pixelmon.api.enums.DeleteType;
import com.pixelmonmod.pixelmon.api.events.PixelmonDeletedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ReleaseListener {

    @SubscribeEvent
    public void onRelease (PixelmonDeletedEvent event) throws ObjectMappingException {

        if (event.deleteType == DeleteType.PC) {

            ServerPlayerEntity player = event.player;
            UUID uuid = player.getUniqueID();
            if (AccountHandler.accountMap.containsKey(uuid)) {

                Account account = AccountHandler.accountMap.get(uuid);
                Pokemon pokemon = event.pokemon;
                ReleaseMission mission = null;
                String id = AccountHandler.getCurrentMission(account);
                for (ReleaseMission missions : MissionsHandler.releaseMissions) {

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
