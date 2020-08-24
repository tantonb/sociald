package com.tantonb.sociald.dimensions.depths;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class DepthsDimension extends Dimension {

    private static final Logger LOGGER = LogManager.getLogger();

    private long lastLogTs = 0;

    public DepthsDimension(World world, DimensionType type) { super(world, type, 0.0f); }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return new DepthsChunkGenerator(world, new DepthsBiomeProvider(world.getWorldInfo()));
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos  chunkPosIn, boolean checkValid) { return null; }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) { return null; }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        double d0 = MathHelper.frac((double)worldTime / 24000.0D - 0.25D);
        double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
        float angle = (float)(d0 * 2.0D + d1) / 3.0F;
        /*
        long curTs = ZonedDateTime.now().toInstant().toEpochMilli();
        if (curTs - lastLogTs > 2000) {
            LOGGER.info("worldTime: {}, d0: {}, d1: {}, angle: {}", worldTime, d0, d1, angle);
            lastLogTs = curTs;
        }
        */
        return angle;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        float f = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.7529412F;
        float f2 = 0.84705883F;
        float f3 = 1.0F;
        f1 = f1 * (f * 0.94F + 0.06F);
        f2 = f2 * (f * 0.94F + 0.06F);
        f3 = f3 * (f * 0.91F + 0.09F);
        return new Vec3d((double)f1, (double)f2, (double)f3);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

}
