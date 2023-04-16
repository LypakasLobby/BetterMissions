package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;

public class DefeatMission extends Mission {

    private final List<String> locations;
    private List<String> rewardCommands;
    private int reward;

    public DefeatMission (String id, int amount, double chance, String displayName, String commandID,
                          List<String> locations, List<String> displayLore, String rewardType, List<String> commands,
                          MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.locations = locations;
        this.rewardCommands = commands;

    }

    public DefeatMission (String id, int amount, double chance, String displayName, String commandID,
                          List<String> locations, List<String> displayLore, String rewardType, int reward,
                          MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.locations = locations;
        this.reward = reward;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.defeatMissions.add(this);

        } else {

            MissionRegistry.permanentDefeatMissions.add(this);

        }

    }

    public List<String> getLocations() {

        return this.locations;

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

}
