package com.lypaka.bettermissions.Requirements;

import com.pixelmonmod.pixelmon.api.world.WorldTime;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeRequirement {

    private final Map<String, List<String>> requirements;
    private final ServerPlayerEntity player;

    public TimeRequirement (Map<String, List<String>> requirements, ServerPlayerEntity player) {

        this.requirements = requirements;
        this.player = player;

    }

    public boolean passes() {

        if (this.requirements.isEmpty()) return true;
        if (this.requirements.containsKey("Days")) {

            List<String> days = this.requirements.get("Days");
            LocalDateTime now = LocalDateTime.now();
            String day = now.getDayOfWeek().name();
            boolean contains = false;
            for (String d : days) {

                if (d.equalsIgnoreCase(day)) {

                    contains = true;
                    break;

                }

            }

            if (!contains) return false;

        }
        if (this.requirements.containsKey("Times-Of-Day")) {

            List<WorldTime> currentTimes = WorldTime.getCurrent(this.player.getEntityWorld());
            List<String> times = this.requirements.get("Times-Of-Day");
            boolean contains = false;
            for (String t : times) {

                for (WorldTime wt : currentTimes) {

                    if (wt.getLocalizedName().equalsIgnoreCase(t)) {

                        contains = true;
                        break;

                    }

                }

            }
            return contains;

        }

        return true;

    }

}
