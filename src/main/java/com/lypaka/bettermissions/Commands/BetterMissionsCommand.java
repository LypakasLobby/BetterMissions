package com.lypaka.bettermissions.Commands;

import com.lypaka.bettermissions.BetterMissions;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

/**
 * FUCK Brigadier
 */
@Mod.EventBusSubscriber(modid = BetterMissions.MOD_ID)
public class BetterMissionsCommand {

    public static List<String> ALIASES = Arrays.asList("bettermissions", "bmissions", "missions");
    public static SuggestionProvider<CommandSource> MISSION_TYPES = ((context, builder) -> ISuggestionProvider.suggest(BetterMissions.missionConfigManager.keySet(), builder));

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new AddMissionCommand(event.getDispatcher());
        new AssignCommand(event.getDispatcher());
        new MarkCompleteCommand(event.getDispatcher());
        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());
        new ReloadMissionCommand(event.getDispatcher());
        new RemoveCommand(event.getDispatcher());
        new RerollCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
