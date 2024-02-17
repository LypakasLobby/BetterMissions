package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.ConfigGetters;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String s : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(s)
                            .then(
                                    Commands.literal("reload")
                                            .then(
                                                    Commands.argument("reloadMissions", StringArgumentType.word())
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 1;

                                                                    }

                                                                }

                                                                try {

                                                                    boolean reloadMissions = Boolean.parseBoolean(StringArgumentType.getString(c, "reloadMissions"));
                                                                    BetterMissions.configManager.load();
                                                                    ConfigGetters.load();

                                                                    if (reloadMissions) {

                                                                        Utils.reloadAllMissionTypes();
                                                                        c.getSource().sendFeedback(FancyText.getFormattedText("&aSending reload to all missions..."), true);

                                                                    }

                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterMissions configuration!"), true);

                                                                } catch (ObjectMappingException e) {

                                                                    e.printStackTrace();

                                                                }

                                                                return 0;

                                                            })
                                            )
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 1;

                                                    }

                                                }

                                                try {

                                                    BetterMissions.configManager.load();
                                                    ConfigGetters.load();

                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterMissions configuration!"), true);

                                                } catch (ObjectMappingException e) {

                                                    e.printStackTrace();

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
