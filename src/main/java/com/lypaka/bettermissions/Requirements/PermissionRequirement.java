package com.lypaka.bettermissions.Requirements;

import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

public class PermissionRequirement {

    private final List<String> doesNotHavePermissions;
    private final List<String> hasPermissions;
    private final ServerPlayerEntity player;

    public PermissionRequirement (List<String> doesNotHavePermissions, List<String> hasPermissions, ServerPlayerEntity player) {

        this.doesNotHavePermissions = doesNotHavePermissions;
        this.hasPermissions = hasPermissions;
        this.player = player;

    }

    public boolean passes() {

        if (this.doesNotHavePermissions.isEmpty() && this.hasPermissions.isEmpty()) return true;
        if (!this.doesNotHavePermissions.isEmpty()) {

            for (String p : this.doesNotHavePermissions) {

                if (PermissionHandler.hasPermission(this.player, p)) return false;

            }

        }
        if (!this.hasPermissions.isEmpty()) {

            for (String p : this.hasPermissions) {

                if (!PermissionHandler.hasPermission(this.player, p)) return false;

            }

        }

        return true;

    }

}
