package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;
import java.util.Map;

public class MeleeMission extends Mission {

    private final List<String> entityTypes;
    private List<String> rewardCommands;
    private int reward;

    public MeleeMission (String id, int amount, double chance, String displayName, String commandID,
                         List<String> entityTypes, List<String> displayLore, String rewardType, List<String> rewardCommands,
                         MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.entityTypes = entityTypes;
        this.rewardCommands = rewardCommands;

    }

    public MeleeMission (String id, int amount, double chance, String displayName, String commandID,
                         List<String> entityTypes, List<String> displayLore, String rewardType, int reward,
                         MissionRequirement requirements, int timer) {

        super(id, amount, chance, displayName, displayLore, commandID, rewardType, requirements, timer);
        this.entityTypes = entityTypes;
        this.reward = reward;

    }

    public void register() {

        if (this.getTimer() > 0) {

            MissionRegistry.meleeMissions.add(this);

        } else {

            MissionRegistry.permanentMeleeMissions.add(this);

        }

    }

    public List<String> getEntityTypes() {

        return this.entityTypes;

    }

    public List<String> getRewardCommands() {

        return this.rewardCommands;

    }

    public int getReward() {

        return this.reward;

    }

}
