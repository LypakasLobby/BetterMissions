package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.API.MissionCompletedEvent;
import com.lypaka.bettermissions.API.MissionRequirementsEvent;
import com.lypaka.bettermissions.Accounts.Account;
import com.lypaka.bettermissions.Accounts.AccountHandler;
import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Config.ConfigGetters;
import com.lypaka.bettermissions.Missions.MineMission;
import com.lypaka.bettermissions.Missions.MissionRegistry;
import com.lypaka.bettermissions.Requirements.*;
import com.lypaka.bettermissions.Utils;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.JoinListener;
import com.lypaka.lypakautils.LogicalPixelmonMoneyHandler;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MiningListener {

    @SubscribeEvent
    public void onMine (BlockEvent.BreakEvent event) throws ObjectMappingException {

        if (BetterMissions.disabled) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        UUID uuid = player.getUniqueID();
        if (AccountHandler.accountMap.containsKey(uuid)) {

            Account account = AccountHandler.accountMap.get(uuid);
            String id = AccountHandler.getCurrentMission(account);
            MineMission mission = null;
            for (MineMission missions : MissionRegistry.mineMissions) {

                if (missions.getID().equalsIgnoreCase(id)) {

                    mission = missions;
                    break;

                }

            }
            if (mission != null) {

                String blockID = event.getState().getBlock().getRegistryName().toString();
                if (mission.getBlockTypes().contains(blockID)) {

                    double chance = mission.getChance();
                    if (chance < 1.0) {

                        if (!RandomHelper.getRandomChance(mission.getChance())) return;

                    }
                    MissionRequirementsEvent requirementsEvent = new MissionRequirementsEvent(player, mission.getID(), mission.getRequirements());
                    MinecraftForge.EVENT_BUS.post(requirementsEvent);
                    if (!requirementsEvent.isCanceled()) {

                        ItemRequirement itemRequirement = new ItemRequirement(mission.getRequirements().getItemRequirements(), player);
                        ComplexConfigManager configManager = BetterMissions.missionConfigManager.get("Mine");
                        int index = Utils.getIndexFromMissionID(mission.getCommandID());
                        PartyRequirement partyRequirement = new PartyRequirement(configManager, index, mission.getRequirements().getPartyRequirements(), player);
                        PokedexRequirement pokedexRequirement = new PokedexRequirement(mission.getRequirements().getPokedexRequirements(), player);
                        PermissionRequirement permissionRequirement = new PermissionRequirement(mission.getRequirements().getPermissionRequirements(), player);
                        TimeRequirement timeRequirement = new TimeRequirement(mission.getRequirements().getTimeRequirements(), player);
                        WeatherRequirement weatherRequirement = new WeatherRequirement(mission.getRequirements().getWeatherRequirements(), player);
                        if (!Utils.passesRequirements(itemRequirement, partyRequirement, pokedexRequirement, permissionRequirement, timeRequirement, weatherRequirement)) return;
                        if (partyRequirement.getPokemonToRemove().size() > 0) {

                            PlayerPartyStorage storage = StorageProxy.getParty(player);
                            for (Map.Entry<Integer, Pokemon> pokemonEntry : partyRequirement.getPokemonToRemove().entrySet()) {

                                storage.set(pokemonEntry.getKey(), null);

                            }

                        }
                        if (itemRequirement.getItemsToRemove().size() > 0) {

                            for (Map.Entry<String, Integer> itemEntry : itemRequirement.getItemsToRemove().entrySet()) {

                                for (ItemStack item : player.inventory.mainInventory) {

                                    if (item.getItem().getRegistryName().toString().equalsIgnoreCase(itemEntry.getKey())) {

                                        item.setCount(item.getCount() - itemEntry.getValue());

                                    }

                                }

                            }

                        }

                    }
                    int progress = AccountHandler.getMissionProgress(account, mission.getID());
                    int updated = progress + 1;
                    AccountHandler.updateProgress(account, mission.getID(), updated);
                    if (AccountHandler.completed(mission.getAmount(), updated)) {

                        if (ConfigGetters.autoCycleMissions) {

                            AccountHandler.assignRandomMission(account);
                            if (!ConfigGetters.newMissionNotification.equals("")) {

                                player.sendMessage(FancyText.getFormattedText(ConfigGetters.newMissionNotification), player.getUniqueID());

                            }

                        }
                        AccountHandler.removeMission(account, mission.getID());
                        MissionCompletedEvent completedEvent;
                        if (mission.getRewardType().equalsIgnoreCase("money")) {

                            completedEvent = new MissionCompletedEvent(player, mission, mission.getReward());
                            MinecraftForge.EVENT_BUS.post(completedEvent);
                            LogicalPixelmonMoneyHandler.add(uuid, completedEvent.getRewardMoney());

                        } else {

                            completedEvent = new MissionCompletedEvent(player, mission, mission.getRewardCommands());
                            MinecraftForge.EVENT_BUS.post(completedEvent);
                            for (String c : completedEvent.getRewardCommands()) {

                                player.getServer().getCommandManager().handleCommand(
                                        player.getServer().getCommandSource(),
                                        c.replace("%player%", player.getName().getString())
                                );

                            }

                        }

                        if (!ConfigGetters.completionBroadcast.equalsIgnoreCase("")) {

                            for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                                entry.getValue().sendMessage(FancyText.getFormattedText(ConfigGetters.completionBroadcast
                                        .replace("%player%", player.getName().getString())
                                        .replace("%mission%", mission.getID())
                                ), entry.getValue().getUniqueID());

                            }

                        }

                    }

                    AccountHandler.saveProgress(account);

                }

            }
            if (mission == null) {

                ArrayList<String> permanentMissions = AccountHandler.getCurrentPermanentMissionsList(account);
                for (MineMission missions : MissionRegistry.permanentMineMissions) {

                    permanentMissions.removeIf(m -> {

                        if (m.equalsIgnoreCase(missions.getID())) {

                            String blockID = event.getState().getBlock().getRegistryName().toString();
                            if (missions.getBlockTypes().contains(blockID)) {

                                double chance = missions.getChance();
                                if (chance < 1.0) {

                                    if (!RandomHelper.getRandomChance(missions.getChance())) {

                                        return false;

                                    }

                                }
                                MissionRequirementsEvent requirementsEvent = new MissionRequirementsEvent(player, missions.getID(), missions.getRequirements());
                                MinecraftForge.EVENT_BUS.post(requirementsEvent);
                                if (!requirementsEvent.isCanceled()) {

                                    ItemRequirement itemRequirement = new ItemRequirement(missions.getRequirements().getItemRequirements(), player);
                                    ComplexConfigManager configManager = BetterMissions.missionConfigManager.get("Mine");
                                    int index = Utils.getIndexFromMissionID(missions.getCommandID());
                                    PartyRequirement partyRequirement = new PartyRequirement(configManager, index, missions.getRequirements().getPartyRequirements(), player);
                                    PokedexRequirement pokedexRequirement = new PokedexRequirement(missions.getRequirements().getPokedexRequirements(), player);
                                    PermissionRequirement permissionRequirement = new PermissionRequirement(missions.getRequirements().getPermissionRequirements(), player);
                                    TimeRequirement timeRequirement = new TimeRequirement(missions.getRequirements().getTimeRequirements(), player);
                                    WeatherRequirement weatherRequirement = new WeatherRequirement(missions.getRequirements().getWeatherRequirements(), player);
                                    try {

                                        if (!Utils.passesRequirements(itemRequirement, partyRequirement, pokedexRequirement, permissionRequirement, timeRequirement, weatherRequirement)) return false;

                                    } catch (ObjectMappingException e) {

                                        e.printStackTrace();

                                    }
                                    if (partyRequirement.getPokemonToRemove().size() > 0) {

                                        PlayerPartyStorage storage = StorageProxy.getParty(player);
                                        for (Map.Entry<Integer, Pokemon> pokemonEntry : partyRequirement.getPokemonToRemove().entrySet()) {

                                            storage.set(pokemonEntry.getKey(), null);

                                        }

                                    }
                                    if (itemRequirement.getItemsToRemove().size() > 0) {

                                        for (Map.Entry<String, Integer> itemEntry : itemRequirement.getItemsToRemove().entrySet()) {

                                            for (ItemStack item : player.inventory.mainInventory) {

                                                if (item.getItem().getRegistryName().toString().equalsIgnoreCase(itemEntry.getKey())) {

                                                    item.setCount(item.getCount() - itemEntry.getValue());

                                                }

                                            }

                                        }

                                    }

                                }
                                int progress = AccountHandler.getMissionProgress(account, missions.getID());
                                int updated = progress + 1;
                                AccountHandler.updateProgress(account, missions.getID(), updated);
                                if (AccountHandler.completed(missions.getAmount(), updated)) {

                                    player.getServer().deferTask(() -> {

                                        AccountHandler.movePermanentMissionToCompleted(account, missions.getID());

                                    });
                                    MissionCompletedEvent completedEvent;
                                    if (missions.getRewardType().equalsIgnoreCase("money")) {

                                        completedEvent = new MissionCompletedEvent(player, missions, missions.getReward());
                                        MinecraftForge.EVENT_BUS.post(completedEvent);
                                        LogicalPixelmonMoneyHandler.add(uuid, completedEvent.getRewardMoney());

                                    } else {

                                        completedEvent = new MissionCompletedEvent(player, missions, missions.getRewardCommands());
                                        MinecraftForge.EVENT_BUS.post(completedEvent);
                                        for (String c : completedEvent.getRewardCommands()) {

                                            player.getServer().getCommandManager().handleCommand(
                                                    player.getServer().getCommandSource(),
                                                    c.replace("%player%", player.getName().getString())
                                            );

                                        }

                                    }

                                    if (!ConfigGetters.completionBroadcast.equalsIgnoreCase("")) {

                                        for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                                            entry.getValue().sendMessage(FancyText.getFormattedText(ConfigGetters.completionBroadcast
                                                    .replace("%player%", player.getName().getString())
                                                    .replace("%mission%", missions.getID())
                                            ), entry.getValue().getUniqueID());

                                        }

                                    }

                                    return true;

                                }

                            }

                        }

                        return false;

                    });

                }

                AccountHandler.saveProgress(account);

            }

        }

    }

}
