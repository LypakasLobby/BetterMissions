package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class RemoveCommand {

    public RemoveCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("remove")
                                            .then(
                                                    Commands.argument("player", EntityArgument.players())
                                                            .then(
                                                                    Commands.argument("mission", StringArgumentType.word())
                                                                            .executes(c -> {

                                                                                if (BetterMissions.disabled) return 0;
                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }

                                                                                }

                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                String commandID = StringArgumentType.getString(c, "mission");
                                                                                String missionID = Utils.getMissionIDFromCommandID(commandID);
                                                                                if (missionID == null) {

                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid mission ID!"));
                                                                                    return 0;

                                                                                } else {

                                                                                    if (AccountHandler.accountMap.containsKey(target.getUniqueID())) {

                                                                                        Account account = AccountHandler.accountMap.get(target.getUniqueID());
                                                                                        if (account.getInProgressPermanentMissions().contains(missionID)) {

                                                                                            AccountHandler.removeMission(account, missionID);
                                                                                            AccountHandler.savePermanentMissionProgress(account);
                                                                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully removed " + missionID + " from " + target.getName().getString()), true);
                                                                                            return 1;

                                                                                        } else if (account.getCompletedPermanentMissions().contains(missionID)) {

                                                                                            account.getCompletedPermanentMissions().removeIf(e -> e.equalsIgnoreCase(missionID));
                                                                                            AccountHandler.savePermanentMissionProgress(account);
                                                                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully removed " + missionID + " from " + target.getName().getString()), true);
                                                                                            return 1;

                                                                                        } else {

                                                                                            c.getSource().sendErrorMessage(FancyText.getFormattedText("&c" + target.getName().getString() + " currently does not have this mission!"));
                                                                                            return 0;

                                                                                        }

                                                                                    }

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
