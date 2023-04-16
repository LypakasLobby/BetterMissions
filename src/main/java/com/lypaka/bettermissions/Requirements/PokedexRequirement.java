package com.lypaka.bettermissions.Requirements;

import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokedexRequirement {

    private final List<String> values;
    private final ServerPlayerEntity player;

    public PokedexRequirement (List<String> values, ServerPlayerEntity player) {

        this.values = values;
        this.player = player;

    }

    public boolean passes() {

        if (this.values.isEmpty()) return true;
        PlayerPartyStorage storage = StorageProxy.getParty(this.player);
        double percentage = storage.playerPokedex.getCaughtCompletionPercentage();
        Map<String, Boolean> passes = new HashMap<>();
        for (String s : this.values) {

            passes.put(s, false);
            String[] split = s.split(" ");
            String operator = split[0];
            double value = Double.parseDouble(split[1]);

            switch (operator) {

                case ">=":
                    if (percentage >= value) {

                        passes.put(s, true);

                    }
                    break;

                case "<=":
                    if (percentage <= value) {

                        passes.put(s, true);

                    }
                    break;

                case ">":
                    if (percentage > value) {

                        passes.put(s, true);

                    }
                    break;

                case "<":
                    if (percentage < value) {

                        passes.put(s, true);

                    }
                    break;

                case "==":
                    if (percentage == value) {

                        passes.put(s, true);

                    }
                    break;

            }

        }

        for (Map.Entry<String, Boolean> entry : passes.entrySet()) {

            if (!entry.getValue()) return false;

        }

        return true;

    }

}
