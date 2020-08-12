package com.tantonb.sociald.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<String> GENERAL_MOTD;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        GENERAL_MOTD = SERVER_BUILDER.comment("Message of the day")
                .define("motd", "Have yourself a socially distant day");
        SERVER_BUILDER.pop();
        SERVER_CONFIG = SERVER_BUILDER.build();

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
