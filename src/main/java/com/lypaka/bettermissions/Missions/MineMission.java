package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;

public class MineMission extends Mission {

    private final List<String> blockTypes;
    private List<String> rewardCommands;
    private int reward;

    public MineMission (String id, int amount, double chance, List<String> blockTypes, String displayName, String commandID,
                        List<String> displayLore, String rewardType, List<String> rewardCommands,
                        MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.blockTypes = blockTypes;
        this.rewardCommands = rewardCommands;

    }

    public MineMission (String id, int amount, double chance, List<String> blockTypes, String displayName, String commandID,
                        List<String> displayLore, String rewardType, int reward,
                        MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.blockTypes = blockTypes;
        this.reward = reward;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.mineMissions.add(this);

        } else {

            MissionRegistry.permanentMineMissions.add(this);

        }

    }

    public List<String> getBlockTypes() {

        return this.blockTypes;

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

}
