package com.lypaka.bettermissions.API;

import com.lypaka.bettermissions.Missions.Mission;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class MissionCompletedEvent extends Event {

    private final ServerPlayerEntity player;
    private final Mission mission;
    private List<String> commands;
    private int reward;

    public MissionCompletedEvent (ServerPlayerEntity player, Mission mission, int reward) {

        this.player = player;
        this.mission = mission;
        this.reward = reward;

    }

    public MissionCompletedEvent (ServerPlayerEntity player, Mission mission, List<String> commands) {

        this.player = player;
        this.mission = mission;
        this.commands = commands;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Mission getMission() {

        return this.mission;

    }

    public List<String> getRewardCommands() {

        return this.commands;

    }

    public void setRewardCommands (List<String> commands) {

        this.commands = commands;

    }

    public int getRewardMoney() {

        return this.reward;

    }

    public void setRewardMoney (int reward) {

        this.reward = reward;

    }

    public boolean isPermanent() {

        return this.mission.getTimer() > 0;

    }

}
