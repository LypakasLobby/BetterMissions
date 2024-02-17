package com.lypaka.bettermissions.Listeners;

import com.lypaka.bettermissions.BetterMissions;
import com.lypaka.bettermissions.Missions.MissionsHandler;
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
        Pixelmon.EVENT_BUS.register(new BreedListener());
        Pixelmon.EVENT_BUS.register(new CatchingListener());
        MinecraftForge.EVENT_BUS.register(new CraftingListeners());
        Pixelmon.EVENT_BUS.register(new DefeatListener());
        Pixelmon.EVENT_BUS.register(new EvolveListener());
        Pixelmon.EVENT_BUS.register(new FishingListener());
        Pixelmon.EVENT_BUS.register(new HatchListener());
        Pixelmon.EVENT_BUS.register(new KillingListener());
        Pixelmon.EVENT_BUS.register(new LoseListeners());
        MinecraftForge.EVENT_BUS.register(new MeleeListener());
        MinecraftForge.EVENT_BUS.register(new MiningListener());
        Pixelmon.EVENT_BUS.register(new PhotographListener());
        Pixelmon.EVENT_BUS.register(new RaidListener());
        Pixelmon.EVENT_BUS.register(new ReleaseListener());
        Pixelmon.EVENT_BUS.register(new ReviveListener());
        MinecraftForge.EVENT_BUS.register(new SmeltListener());

        MinecraftForge.EVENT_BUS.register(new LoginListener());

    }

}
