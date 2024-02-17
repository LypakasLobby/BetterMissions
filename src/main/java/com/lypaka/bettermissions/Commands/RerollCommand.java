package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class RerollCommand {

    public RerollCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reroll")
                                            .then(
                                                    Commands.argument("player", EntityArgument.player())
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.reroll")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 0;

                                                                    }

                                                                }

                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                if (AccountHandler.accountMap.containsKey(target.getUniqueID())) {

                                                                    Account account = AccountHandler.accountMap.get(target.getUniqueID());
                                                                    AccountHandler.assignRandomMission(account);
                                                                    AccountHandler.removeMission(account, AccountHandler.getCurrentMission(account));
                                                                    AccountHandler.saveProgress(account);
                                                                    target.sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), target.getUniqueID());
                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully rerolled " + target.getName().getString() + "'s mission!"), true);
                                                                    return 1;

                                                                } else {

                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&c" + target.getName().getString() + " does not have an account, and this is not a good thing!"));
                                                                    return 0;

                                                                }

                                                            })
                                            )
                            )
            );

        }

    }

}
