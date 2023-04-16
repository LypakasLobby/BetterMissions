package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;
import java.util.Map;

public class RaidMission extends Mission {

    private List<String> rewardCommands;
    private int reward;
    private final Map<String, String> specs;

    public RaidMission (String id, int amount, double chance, String displayName, String commandID,
                              List<String> displayLore, String rewardType, List<String> rewardCommands,
                              MissionRequirement requirements, Map<String, String> specs, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.rewardCommands = rewardCommands;
        this.specs = specs;

    }

    public RaidMission (String id, int amount, double chance, String displayName, String commandID,
                              List<String> displayLore, String rewardType, int reward,
                              MissionRequirement requirements, Map<String, String> specs, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.reward = reward;
        this.specs = specs;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.raidMissions.add(this);

        } else {

            MissionRegistry.permanentRaidMissions.add(this);

        }

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

    public Map<String, String> getSpecs() {

        return this.specs;

    }
}
