package com.lypaka.bettermissions.Config;

import com.lypaka.bettermissions.BetterMissions;

public class ConfigUpdater {

    public static void updateConfig() {

        boolean save = false;

        // Version 2.0.0
        if (BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Breeding").isVirtual()) {

            save = true;
            BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Breeding").setValue("pixelmon:white_day_care");
            BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Hatching").setValue("minecraft:egg");
            BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Photographing").setValue("pixelmon:camera");
            BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Raiding").setValue("pixelmon:wishing_star");
            BetterMissions.configManager.getConfigNode(0, "GUI", "Mission-Representative", "Smelting").setValue("minecraft:furnace");

        }

        // Version 2.0.2
        if (BetterMissions.configManager.getConfigNode(0, "Registry-Interval").isVirtual()) {

            if (!save) save = true;
            BetterMissions.configManager.getConfigNode(0, "Registry-Interval").setValue(100);

        }

        if (save) {

            BetterMissions.configManager.save();

        }

    }

}
