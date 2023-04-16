package com.lypaka.bettermissions.Requirements;

import java.util.List;
import java.util.Map;

public class MissionRequirement {

    private final Map<String, Map<String, String>> itemRequirements;
    private final Map<String, Map<String, String>> partyRequirements;
    private final List<String> pokedexRequirements;
    private final Map<String, List<String>> permissionRequirements;
    private final Map<String, List<String>> timeRequirements;
    private final List<String> weatherRequirements;

    public MissionRequirement (Map<String, Map<String, String>> itemRequirements,
                               Map<String, Map<String, String>> partyRequirements,
                               List<String> pokedexRequirements,
                               Map<String, List<String>> permissionRequirements,
                               Map<String, List<String>> timeRequirements,
                               List<String> weatherRequirements
                               ) {

        this.itemRequirements = itemRequirements;
        this.partyRequirements = partyRequirements;
        this.pokedexRequirements = pokedexRequirements;
        this.permissionRequirements = permissionRequirements;
        this.timeRequirements = timeRequirements;
        this.weatherRequirements = weatherRequirements;

    }

    public Map<String, Map<String, String>> getItemRequirements() {

        return this.itemRequirements;

    }

    public Map<String, Map<String, String>> getPartyRequirements() {

        return partyRequirements;

    }

    public List<String> getPokedexRequirements() {

        return pokedexRequirements;

    }

    public Map<String, List<String>> getPermissionRequirements() {

        return permissionRequirements;

    }

    public Map<String, List<String>> getTimeRequirements() {

        return timeRequirements;

    }

    public List<String> getWeatherRequirements() {

        return weatherRequirements;

    }

}
