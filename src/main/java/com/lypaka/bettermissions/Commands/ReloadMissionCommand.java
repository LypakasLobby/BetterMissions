package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.MissionTimer;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadMissionCommand {

    public ReloadMissionCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reloadtype")
                                            .then(
                                                    Commands.argument("missionType", StringArgumentType.word())
                                                            .suggests(BetterMissionsCommand.MISSION_TYPES)
                                                            .executes(c -> {

                                                                if (BetterMissions.disabled) return 0;
                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 0;

                                                                    }

                                                                }

                                                                try {

                                                                    String missionType = StringArgumentType.getString(c, "missionType");
                                                                    Utils.reloadMissionType(missionType);
                                                                    MissionTimer.start();
                                                                    Utils.putAllPermanentShitsInOneList();
                                                                    AssignCommand.updateSuggestions();
                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded the " + missionType + " Missions!"), true);

                                                                } catch (ObjectMappingException e) {

                                                                    e.printStackTrace();

                                                                }
                                                                return 1;

                                                            })
                                            )
                            )
            );

        }

    }

}
