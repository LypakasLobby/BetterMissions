package com.lypaka.bettermissions.Requirements;

import java.util.List;
import java.util.Map;

public class MissionRequirement {

    private final Map<String, Map<String, String>> itemRequirements;
    private final Map<String, Map<String, String>> partyRequirements;
    private final List<String> pokedexRequirements;
    private final List<String> doesNotHavePermissionRequirements;
    private final List<String> hasPermissionRequirements;
    private final List<String> weatherRequirements;

    public MissionRequirement (Map<String, Map<String, String>> itemRequirements,
                               Map<String, Map<String, String>> partyRequirements,
                               List<String> pokedexRequirements,
                               List<String> doesNotHavePermissionRequirements,
                               List<String> hasPermissionRequirements,
                               List<String> weatherRequirements
                               ) {

        this.itemRequirements = itemRequirements;
        this.partyRequirements = partyRequirements;
        this.pokedexRequirements = pokedexRequirements;
        this.doesNotHavePermissionRequirements = doesNotHavePermissionRequirements;
        this.hasPermissionRequirements = hasPermissionRequirements;
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

    public List<String> getDoesNotHavePermissionRequirements() {

        return this.doesNotHavePermissionRequirements;

    }

    public List<String> getHasPermissionRequirements() {

        return this.hasPermissionRequirements;

    }

    public List<String> getWeatherRequirements() {

        return weatherRequirements;

    }

}
