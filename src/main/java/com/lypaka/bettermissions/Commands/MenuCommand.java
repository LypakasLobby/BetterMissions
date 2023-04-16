package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.GUIs.MissionsMenu;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String s : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(s)
                            .then(
                                    Commands.literal("menu")
                                            .executes(c -> {

                                                if (BetterMissions.disabled) return 0;
                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.menu")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                    if (ConfigGetters.menuEnabled) {

                                                        MissionsMenu.open(player);

                                                    }
                                                    return 1;

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
