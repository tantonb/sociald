package com.tantonb.sociald.setup;

import com.tantonb.sociald.Sociald;
import com.tantonb.sociald.commands.SocialdCommands;
import com.tantonb.sociald.dimensions.SocialdDimensions;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = Sociald.MOD_ID, bus = Bus.MOD)
public class ModSetup {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info(" ### ModSetup.onCommonSetup()");
    }


}
