package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.UUID;

public class AssignCommand {

    public AssignCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterMissionsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("assign")
                                            .then(
                                                    Commands.argument("player", EntityArgument.players())
                                                            .then(
                                                                    Commands.argument("mission", StringArgumentType.word())
                                                                            .suggests(((context, builder) -> ISuggestionProvider.suggest(Utils.permanentMissionIDs, builder)))
                                                                            .executes(c -> {

                                                                                if (BetterMissions.disabled) return 0;
                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "bettermissions.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 1;

                                                                                    }

                                                                                }

                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                String commandID = StringArgumentType.getString(c, "mission");
                                                                                UUID uuid = target.getUniqueID();
                                                                                if (AccountHandler.accountMap.containsKey(uuid)) {

                                                                                    Account account = AccountHandler.accountMap.get(uuid);
                                                                                    String missionID = Utils.getMissionIDFromCommandID(commandID);
                                                                                    if (missionID == null) {

                                                                                        c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid mission command ID!"));
                                                                                        return 1;

                                                                                    }
                                                                                    if (!AccountHandler.getCurrentPermanentMissionsList(account).contains(missionID)) {

                                                                                        if (!account.getCompletedPermanentMissions().contains(missionID)) {

                                                                                            AccountHandler.assignPermanentMission(account, missionID);
                                                                                            AccountHandler.saveProgress(account);
                                                                                            c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully assigned mission: " + missionID + " to " + target.getName().getString()), true);
                                                                                            target.sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), target.getUniqueID());
                                                                                            return 0;

                                                                                        } else {

                                                                                            c.getSource().sendErrorMessage(FancyText.getFormattedText("&c" + target.getName().getString() + " has already completed this mission!"));
                                                                                            return 1;

                                                                                        }

                                                                                    } else {

                                                                                        c.getSource().sendErrorMessage(FancyText.getFormattedText("&c" + target.getName().getString() + " already has this mission!"));
                                                                                        return 1;

                                                                                    }

                                                                                }

                                                                                return 0;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
