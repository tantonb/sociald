package com.tantonb.sociald.dimensions;

import com.tantonb.sociald.Sociald;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.tantonb.sociald.Sociald.MOD_ID;


public class SocialdDimensions {

    /*
    public class RiftDimensionEntry extends ModDimension {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() { return RiftDimension::new; }
    }
    */

    // creates a dimensions registry
    private static final DeferredRegister<ModDimension> SOCD_DIM_REGISTRY =
            DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, MOD_ID);

    // register mod dimension factory factories in the registry
    // (java suppliers that provide factory factory constructor)
    public static final RegistryObject<RiftDimensionEntry> SOCD_DIM_ENTRY_RIFT =
            SOCD_DIM_REGISTRY.register("the_rift", RiftDimensionEntry::new);

    public static void init() {
        // add the dim factory entries in the dim registry to the
        // ...forge mod loader context's mod event bus?...
        // this allows instances of the dims to be created later as needed?
        // unclear why register is overloaded to do two completely unrelated things...
        SOCD_DIM_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // these are used during register dimensions event handling...
    public static final ResourceLocation SOCD_DIM_NAME_RIFT =
            new ResourceLocation(Sociald.MOD_ID, "the_rift");

    // dimension "types" that will be returned from forge's DimensionManager.registerOrGetDimension(...)
    public static DimensionType SOCD_DIM_RIFT;

    // called by ModSetup.onRegisterDimensions()
    public static void registerDimensions(RegisterDimensionsEvent event) {
        SOCD_DIM_RIFT = DimensionManager.registerOrGetDimension(
                SOCD_DIM_NAME_RIFT,
                SOCD_DIM_ENTRY_RIFT.get(),
                null,
                true
            );
    }

}
