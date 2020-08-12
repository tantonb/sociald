package com.tantonb.sociald.setup;

import com.tantonb.sociald.Sociald;
import com.tantonb.sociald.commands.SocialdCommands;
import com.tantonb.sociald.dimensions.SocialdDimensions;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = Sociald.MOD_ID, bus = Bus.FORGE)
public class Registration {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        LOGGER.info(" ### Registration.init()");
        SocialdDimensions.init();
    }

    @SubscribeEvent
    public static void onServerLoad(FMLServerStartingEvent event) {
        LOGGER.info(" ### Registration.onServerLoad()");
        SocialdCommands.registerCommands(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onRegisterDimensions(RegisterDimensionsEvent event) {
        LOGGER.info(" ### Registration.onRegisterDimensions()");
        SocialdDimensions.registerDimensions(event);
    }
}
