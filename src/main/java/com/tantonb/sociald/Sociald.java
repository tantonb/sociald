package com.tantonb.sociald;

import com.tantonb.sociald.setup.ClientSetup;
import com.tantonb.sociald.setup.Config;
import com.tantonb.sociald.setup.Registration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Sociald.MOD_ID)
public class Sociald
{
    public static final String MOD_ID = "sociald";

    private static final Logger LOGGER = LogManager.getLogger();

    public Sociald() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        Registration.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
