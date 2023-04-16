package com.lypaka.bettermissions.Requirements;

import com.google.common.reflect.TypeToken;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyRequirement {

    private final ComplexConfigManager configManager;
    private final int index;
    private final Map<String, Map<String, String>> requirements;
    private final Map<Integer, Pokemon> pokemonToRemove;
    private final ServerPlayerEntity player;

    public PartyRequirement (ComplexConfigManager configManager, int index, Map<String, Map<String, String>> requirements, ServerPlayerEntity player) {

        this.configManager = configManager;
        this.index = index;
        this.requirements = requirements;
        this.pokemonToRemove = new HashMap<>();
        this.player = player;

    }

    public Map<Integer, Pokemon> getPokemonToRemove() {

        return this.pokemonToRemove;

    }

    public boolean passes() throws ObjectMappingException {

        if (this.requirements.isEmpty()) return true;
        PlayerPartyStorage storage = StorageProxy.getParty(this.player);
        if (!this.requirements.isEmpty()) {

            for (Map.Entry<String, Map<String, String>> entry : this.requirements.entrySet()) {

                String species = entry.getKey();
                // first we can check for a slot to potentially be able to avoid checking everything else
                Map<String, String> specs = entry.getValue();
                int slot = Integer.parseInt(specs.get("Slot"));
                Pokemon inSlot = storage.get(slot);
                if (inSlot != null) {

                    if (!inSlot.getSpecies().getName().equalsIgnoreCase(species)) {

                        return false;

                    }

                } else {

                    return false;

                }
                for (Map.Entry<String, String> specEntry : specs.entrySet()) {

                    String spec = specEntry.getKey();
                    String value = specEntry.getValue();
                    switch (spec.toLowerCase()) {

                        case "ability":
                            List<String> abilityValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Ability").isVirtual()) {

                                abilityValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Ability").getList(TypeToken.of(String.class));

                            }
                            if (!abilityValues.isEmpty()) {

                                boolean contains = false;
                                for (String a : abilityValues) {

                                    if (inSlot.getAbilityName().replace(" ", "").equalsIgnoreCase(a.replace(" ", ""))) {

                                        contains = true;
                                        break;

                                    }

                                }

                                if (!contains) {

                                    return false;

                                }

                            }
                            break;

                        case "form":
                            if (!value.equalsIgnoreCase("")) {

                                if (!inSlot.getForm().getName().equalsIgnoreCase(value)) {

                                    return false;

                                }

                            }
                            break;

                        case "friendship":
                            List<String> friendshipValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Friendship").isVirtual()) {

                                friendshipValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Friendship").getList(TypeToken.of(String.class));

                            }
                            if (!friendshipValues.isEmpty()) {

                                int friendship = inSlot.getFriendship();
                                boolean qualifies = false;
                                for (String s : friendshipValues) {

                                    String[] split = s.split(" ");
                                    String operator = split[0];
                                    int requiredFriendship = Integer.parseInt(split[1]);
                                    switch (operator) {

                                        case ">=":
                                            if (friendship >= requiredFriendship) {

                                                qualifies = true;

                                            }
                                            break;

                                        case ">":
                                            if (!qualifies) {

                                                if (friendship > requiredFriendship) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "<":
                                            if (!qualifies) {

                                                if (friendship < requiredFriendship) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "<=":
                                            if (!qualifies) {

                                                if (friendship <= requiredFriendship) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "==":
                                            if (!qualifies) {

                                                if (friendship == requiredFriendship) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                    }

                                    if (!qualifies) return false;

                                }

                            }
                            break;

                        case "growth":
                            List<String> growthValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Growth").isVirtual()) {

                                growthValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Growth").getList(TypeToken.of(String.class));

                            }
                            if (!growthValues.isEmpty()) {

                                boolean contains = false;
                                for (String g : growthValues) {

                                    if (inSlot.getGrowth().getLocalizedName().equalsIgnoreCase(g)) {

                                        contains = true;
                                        break;

                                    }

                                }

                                if (!contains) return false;

                            }
                            break;

                        case "held-item":
                            if (!inSlot.getHeldItem().getItem().getRegistryName().toString().equalsIgnoreCase(value)) return false;
                            break;

                        case "level":
                            List<String> levelValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Level").isVirtual()) {

                                levelValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Level").getList(TypeToken.of(String.class));

                            }
                            if (!levelValues.isEmpty()) {

                                boolean qualifies = false;
                                for (String s : levelValues) {

                                    String[] split = s.split(" ");
                                    String operator = split[0];
                                    int requiredLevel = Integer.parseInt(split[1]);
                                    int pokemonLevel = inSlot.getPokemonLevel();
                                    switch (operator) {

                                        case ">=":
                                            if (pokemonLevel >= requiredLevel) {

                                                qualifies = true;

                                            }
                                            break;

                                        case ">":
                                            if (!qualifies) {

                                                if (pokemonLevel > requiredLevel) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "<=":
                                            if (!qualifies) {

                                                if (pokemonLevel <= requiredLevel) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "<":
                                            if (!qualifies) {

                                                if (pokemonLevel < requiredLevel) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                        case "==":
                                            if (!qualifies) {

                                                if (pokemonLevel == requiredLevel) {

                                                    qualifies = true;

                                                }

                                            }
                                            break;

                                    }

                                    if (!qualifies) return false;

                                }

                            }
                            break;

                        case "moves":
                            List<String> moveValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Moves").isVirtual()) {

                                moveValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Moves").getList(TypeToken.of(String.class));

                            }
                            Map<String, Boolean> passesMap = new HashMap<>();
                            for (int i = 0; i < moveValues.size(); i++) {

                                passesMap.put(moveValues.get(i), false);

                            }
                            List<String> pokemonMoves = new ArrayList<>();
                            for (Attack attack : inSlot.getMoveset().attacks) {

                                if (attack != null) {

                                    pokemonMoves.add(attack.getMove().getLocalizedName());

                                }

                            }
                            for (String a : pokemonMoves) {

                                for (Map.Entry<String, Boolean> passesEntry : passesMap.entrySet()) {

                                    if (!passesEntry.getValue()) {

                                        if (a.equalsIgnoreCase(passesEntry.getKey())) {

                                            passesEntry.setValue(true);

                                        }

                                    }

                                }

                            }
                            for (Map.Entry<String, Boolean> p : passesMap.entrySet()) {

                                if (!p.getValue()) return false;

                            }
                            break;

                        case "nature":
                            List<String> natureValues = new ArrayList<>();
                            if (!this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Nature").isVirtual()) {

                                natureValues = this.configManager.getConfigNode(this.index, "Requirements", "Party", species, "Nature").getList(TypeToken.of(String.class));

                            }
                            if (!natureValues.isEmpty()) {

                                boolean contains = false;
                                for (String n : natureValues) {

                                    if (inSlot.getNature().getLocalizedName().equalsIgnoreCase(n)) {

                                        contains = true;
                                        break;

                                    }

                                }

                                if (!contains) return false;

                            }
                            break;

                        case "otuuid":
                            boolean mustMatch = Boolean.parseBoolean(value);
                            String pokemonOT = inSlot.getOriginalTrainerUUID() == null ? "" : inSlot.getOriginalTrainerUUID().toString();
                            if (!pokemonOT.equalsIgnoreCase("")) {

                                if (mustMatch) {

                                    if (!pokemonOT.equalsIgnoreCase(this.player.getUniqueID().toString())) return false;

                                }

                            }
                            break;

                        case "palette":
                            if (!value.equalsIgnoreCase("")) {

                                if (!inSlot.getPalette().getName().equalsIgnoreCase(value)) return false;

                            }
                            break;

                        case "shiny":
                            boolean mustBeShiny = Boolean.parseBoolean(value);
                            if (mustBeShiny) {

                                if (!inSlot.isShiny()) return false;

                            } else {

                                if (inSlot.isShiny()) return false;

                            }
                            break;

                    }

                }

                // If we get to here that means the Pokemon passes all the requirements, now check for if we need to remove it if the mission gets marked complete
                if (specs.containsKey("Sacrifice")) {

                    boolean sacrifice = Boolean.parseBoolean(specs.get("Sacrifice"));
                    if (sacrifice) {

                        this.pokemonToRemove.put(slot, inSlot);

                    }

                }

            }

        }
        return true;

    }

}
