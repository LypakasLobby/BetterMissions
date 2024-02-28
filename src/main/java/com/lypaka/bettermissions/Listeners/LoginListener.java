package com.lypaka.bettermissions.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class LoginListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        BetterMissions.playerConfigManager.loadPlayer(uuid);
        if (BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "Claimed-Missions").isVirtual()) {

            ArrayList<String> emptyList = new ArrayList<>();
            BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "Claimed-Missions").setValue(emptyList);
            BetterMissions.playerConfigManager.savePlayer(uuid);

        }
        Account account;
        if (!AccountHandler.accountMap.containsKey(uuid)) {

            account = new Account(uuid,
                    new ArrayList<>(BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "Claimed-Missions").getList(TypeToken.of(String.class))),
                    new ArrayList<>(BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "Completed-Missions").getList(TypeToken.of(String.class))),
                    new ArrayList<>(BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "In-Progress-Permanent-Missions").getList(TypeToken.of(String.class))),
                    BetterMissions.playerConfigManager.getPlayerConfigNode(uuid, "Mission-Storage").getValue(new TypeToken<Map<String, Map<String, String>>>() {}));
            account.create();

        } else {

            account = AccountHandler.accountMap.get(uuid);

        }

        if (account.getMissionMap().size() == 0) {

            AccountHandler.assignRandomMission(account);
            AccountHandler.saveProgress(account);
            player.sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), uuid);

        }

    }

    @SubscribeEvent
    public void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        Account account = AccountHandler.accountMap.get(event.getPlayer().getUniqueID());
        AccountHandler.saveProgress(account);
        AccountHandler.accountMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(event.getPlayer().getUniqueID().toString()));

    }

}
