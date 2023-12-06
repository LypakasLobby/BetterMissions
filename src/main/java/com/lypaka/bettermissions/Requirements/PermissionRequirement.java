package com.lypaka.bettermissions.Requirements;

import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;
import java.util.Map;

public class PermissionRequirement {

    private final Map<String, List<String>> requirements;
    private final ServerPlayerEntity player;

    public PermissionRequirement (Map<String, List<String>> requirements, ServerPlayerEntity player) {

        this.requirements = requirements;
        this.player = player;

    }

    public boolean passes() {

        if (this.requirements.isEmpty()) return true;
        if (this.requirements.containsKey("Does-Not-Have")) {

            List<String> permissions = this.requirements.get("Does-Not-Have");
            for (String p : permissions) {

                if (PermissionHandler.hasPermission(this.player, p)) return false;

            }

        }
        if (this.requirements.containsKey("Has")) {

            List<String> permissions = this.requirements.get("Has");
            for (String p : permissions) {

                if (!PermissionHandler.hasPermission(this.player, p)) return false;

            }

        }

        return true;

    }

}
