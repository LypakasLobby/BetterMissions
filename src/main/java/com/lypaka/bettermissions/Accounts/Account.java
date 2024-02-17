package com.lypaka.bettermissions.Accounts;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Account {

    private final UUID uuid;
    private final ArrayList<String> claimedMissions;
    private final ArrayList<String> completedPermanentMissions;
    private final ArrayList<String> inProgressPermanentMissions;
    private final Map<String, Map<String, String>> missionMap;

    public Account (UUID uuid, ArrayList<String> claimedMissions, ArrayList<String> completedPermanentMissions, ArrayList<String> inProgressPermanentMissions, Map<String, Map<String, String>> missionMap) {

        this.uuid = uuid;
        this.claimedMissions = claimedMissions;
        this.completedPermanentMissions = completedPermanentMissions;
        this.inProgressPermanentMissions = inProgressPermanentMissions;
        this.missionMap = missionMap;

    }

    public void create() {

        AccountHandler.accountMap.put(this.uuid, this);

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public ArrayList<String> getClaimedMissions() {

        return this.claimedMissions;

    }

    public ArrayList<String> getCompletedPermanentMissions() {

        return this.completedPermanentMissions;

    }

    public ArrayList<String> getInProgressPermanentMissions() {

        return this.inProgressPermanentMissions;

    }

    public Map<String, Map<String, String>> getMissionMap() {

        return this.missionMap;

    }

}
