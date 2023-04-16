package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;

public class SmeltMission extends Mission {

    private final List<String> itemIDs;
    private List<String> rewardCommands;
    private int reward;

    public SmeltMission (String id, int amount, double chance, String displayName, String commandID,
                         List<String> itemIDs, List<String> displayLore, String rewardType, List<String> rewardCommands,
                         MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.itemIDs = itemIDs;
        this.rewardCommands = rewardCommands;

    }

    public SmeltMission (String id, int amount, double chance, String displayName, String commandID,
                         List<String> itemIDs, List<String> displayLore, String rewardType, int reward,
                         MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.itemIDs = itemIDs;
        this.reward = reward;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.smeltMissions.add(this);

        } else {

            MissionRegistry.permanentSmeltMissions.add(this);

        }

    }

    public List<String> getItemIDs() {

        return this.itemIDs;

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

}