package com.lypaka.bettermissions;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.Listeners.JoinListener;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MissionTimer {

    public static Timer timer = null;

    public static void start() {

        if (timer != null) {

            timer.cancel();

        }

        timer = new Timer();
        long interval = ConfigGetters.updateInterval * 1000L;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                    if (AccountHandler.accountMap.containsKey(entry.getKey())) {

                        Account account = AccountHandler.accountMap.get(entry.getKey());
                        Map<String, Map<String, String>> map = account.getMissionMap();
                        if (map.size() > 0) {

                            entry.getValue().getServer().deferTask(() -> {

                                for (Map.Entry<String, Map<String, String>> e2 : map.entrySet()) {

                                    if (e2.getValue().containsKey("IsPermanent")) {

                                        boolean permanent = Boolean.parseBoolean(e2.getValue().get("IsPermanent"));
                                        if (!permanent) {

                                            String id = e2.getKey();
                                            Map<String, String> data = e2.getValue();
                                            String expires = data.get("Expires");
                                            LocalDateTime time = LocalDateTime.parse(expires);
                                            if (LocalDateTime.now().isAfter(time)) {

                                                AccountHandler.assignRandomMission(account);
                                                AccountHandler.removeMission(account, id);
                                                entry.getValue().sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), entry.getValue().getUniqueID());
                                                AccountHandler.saveProgress(account);

                                            }

                                        }

                                    } else {

                                        String id = e2.getKey();
                                        Map<String, String> data = e2.getValue();
                                        String expires = data.get("Expires");
                                        LocalDateTime time = LocalDateTime.parse(expires);
                                        if (LocalDateTime.now().isAfter(time)) {

                                            AccountHandler.assignRandomMission(account);
                                            AccountHandler.removeMission(account, id);
                                            entry.getValue().sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), entry.getValue().getUniqueID());
                                            AccountHandler.saveProgress(account);

                                        }

                                    }

                                }

                            });

                        }

                    }

                }

            }

        }, interval, interval);

    }

}
