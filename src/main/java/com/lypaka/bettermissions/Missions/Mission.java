package com.lypaka.bettermissions.Missions;

import com.lypaka.bettermissions.Requirements.MissionRequirement;

import java.util.List;

public abstract class Mission {

    private final String missionID;
    private final int amount;
    private final double chance;
    private final String displayName;
    private final List<String> displayLore;
    private final String commandID;
    private final String rewardType;
    private final MissionRequirement requirements;
    private final int timer;

    public Mission (String missionID, int amount, double chance, String displayName, List<String> displayLore,
                    String commandID, String rewardType, MissionRequirement requirements, int timer) {

        this.missionID = missionID;
        this.amount = amount;
        this.chance = chance;
        this.displayName = displayName;
        this.displayLore = displayLore;
        this.commandID = commandID;
        this.rewardType = rewardType;
        this.requirements = requirements;
        this.timer = timer;

    }

    public String getID() {

        return this.missionID;

    }

    public int getAmount() {

        return this.amount;

    }

    public double getChance() {

        return this.chance;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public String getCommandID() {

        return this.commandID;

    }

    public List<String> getDisplayLore() {

        return this.displayLore;

    }

    public String getRewardType() {

        return this.rewardType;

    }

    public MissionRequirement getRequirements() {

        return this.requirements;

    }

    public int getTimer() {

        return this.timer;

    }

}
