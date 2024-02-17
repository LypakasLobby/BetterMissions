package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class AddMissionCommand {

    public AddMissionCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("add")
                                            .then(
                                                    Commands.argument("type", StringArgumentType.word())
                                                            .suggests(BetterMissionsCommand.MISSION_TYPES)
                                                            .then(
                                                                    Commands.argument("missionID", StringArgumentType.string())
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }

                                                                                }

                                                                                String type = StringArgumentType.getString(c, "type");
                                                                                String missionID = StringArgumentType.getString(c, "missionID");
                                                                                if (missionID.contains(".conf")) missionID = missionID.replace(".conf", "");

                                                                                for (String s : MissionsHandler.allMissionIDs) {

                                                                                    if (s.equalsIgnoreCase(missionID.replace(" ", ""))) {

                                                                                        c.getSource().sendErrorMessage(FancyText.getFormattedText("&cA mission with this ID already exists!"));
                                                                                        return 0;

                                                                                    }

                                                                                }

                                                                                try {

                                                                                    Utils.createMission(type, missionID);
                                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully created a new &e" + type + " &amission: &e" + missionID + "&a!"), true);
                                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aA new, blank file should exist for you to copy-paste mission data into!"), true);

                                                                                } catch (ObjectMappingException e) {

                                                                                    e.printStackTrace();

                                                                                }
                                                                                return 1;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
