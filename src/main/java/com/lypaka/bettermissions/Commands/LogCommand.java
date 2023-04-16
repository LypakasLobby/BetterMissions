package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.GUIs.MainLogMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class LogCommand {

    public LogCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("log")
                                            .executes(c -> {

                                                if (BetterMissions.disabled) return 0;
                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    MainLogMenu.openMenu(player);
                                                    return 1;

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
