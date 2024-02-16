package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.MissionTimer;
import com.lypaka.bettermissions.Missions.MissionsHandler;
import com.lypaka.bettermissions.Utils;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Mod.EventBusSubscriber(modid = BetterMissions.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) throws ObjectMappingException {

        MissionsHandler.loadMissions();
        MinecraftForge.EVENT_BUS.register(new LoginListener());
        Pixelmon.EVENT_BUS.register(new CatchingListener());
        Pixelmon.EVENT_BUS.register(new KillingListener());
        Pixelmon.EVENT_BUS.register(new DefeatListener());
        Pixelmon.EVENT_BUS.register(new EvolveListener());
        Pixelmon.EVENT_BUS.register(new FishingListener());
        Pixelmon.EVENT_BUS.register(new ReleaseListener());
        Pixelmon.EVENT_BUS.register(new LoseListeners());
        Pixelmon.EVENT_BUS.register(new BreedListener());
        Pixelmon.EVENT_BUS.register(new HatchListener());
        Pixelmon.EVENT_BUS.register(new PhotographListener());
        Pixelmon.EVENT_BUS.register(new RaidListener());
        MinecraftForge.EVENT_BUS.register(new MiningListener());
        MinecraftForge.EVENT_BUS.register(new CraftingListener());
        MinecraftForge.EVENT_BUS.register(new MeleeListener());
        MinecraftForge.EVENT_BUS.register(new SmeltListener());

    }

}
