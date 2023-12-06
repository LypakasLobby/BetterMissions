package com.lypaka.bettermissions.API;

import com.lypaka.bettermissions.Requirements.MissionRequirement;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;
import java.util.Map;

/**
 * Called when a mission's requirements are processed. Canceling the event will return "passed".
 * I'm not sure why anyone would ever want to do this, perhaps a donor rank would bypass certain mission requirements or something...
 * But the option is there regardless
 */
@Cancelable
public class MissionRequirementsEvent extends Event {

    private final ServerPlayerEntity player;
    private final String missionID;
    private final MissionRequirement requirements;

    public MissionRequirementsEvent (ServerPlayerEntity player, String missionID, MissionRequirement requirements) {

        this.player = player;
        this.missionID = missionID;
        this.requirements = requirements;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getMissionID() {

        return this.missionID;

    }

    public MissionRequirement getRequirements() {

        return this.requirements;

    }

}
