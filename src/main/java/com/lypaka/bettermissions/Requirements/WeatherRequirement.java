package com.lypaka.bettermissions.Requirements;

import com.pixelmonmod.pixelmon.battles.status.Weather;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

public class WeatherRequirement {

    private final List<String> requirements;
    private final ServerPlayerEntity player;

    public WeatherRequirement (List<String> requirements, ServerPlayerEntity player) {

        this.requirements = requirements;
        this.player = player;

    }

    public boolean passes() {

        if (this.requirements.isEmpty()) return true;
        String currentWeather;
        if (this.player.world.isRaining()) {

            currentWeather = "rain";

        } else if (this.player.world.isThundering()) {

            currentWeather = "storm";

        } else {

            currentWeather = "clear";

        }

        for (String w : this.requirements) {

            if (w.equalsIgnoreCase(currentWeather)) {

                return true;

            }

        }

        return false;

    }

}
