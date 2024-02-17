package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Missions.DefeatMission;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class DefeatListener {

    @SubscribeEvent
    public void onDefeat (BeatTrainerEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = event.player;
        UUID uuid = player.getUniqueID();
        String worldName = WorldMap.getWorldName(player);
        BlockPos pos = event.trainer.getPosition();
        String location = worldName + "," + pos.getX() + "," + pos.getY() + "," + pos.getZ();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            DefeatMission mission = null;
            String id = AccountHandler.getCurrentMission(account);
            for (DefeatMission missions : MissionsHandler.defeatMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            MissionsHandler.runMissionProgressCheck(player, null, location, mission, 1);

        }

    }

}
