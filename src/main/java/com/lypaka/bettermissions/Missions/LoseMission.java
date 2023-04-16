package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;

public class LoseMission extends Mission {

    private final String entityType;
    private List<String> rewardCommands;
    private int reward;

    public LoseMission (String id, int amount, double chance, String displayName, String entityType, String commandID,
                        List<String> displayLore, String rewardType, List<String> rewardCommands,
                        MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.entityType = entityType;
        this.rewardCommands = rewardCommands;

    }

    public LoseMission (String id, int amount, double chance, String displayName, String entityType, String commandID,
                        List<String> displayLore, String rewardType, int reward,
                        MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.entityType = entityType;
        this.reward = reward;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.loseMissions.add(this);

        } else {

            MissionRegistry.permanentLoseMissions.add(this);

        }

    }

    public String getEntityType() {

        return this.entityType;

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

}
