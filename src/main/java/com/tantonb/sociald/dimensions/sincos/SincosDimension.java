package com.tantonb.sociald.dimensions.sincos;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

import javax.annotation.Nullable;

public class SincosDimension extends Dimension {

    public SincosDimension(World world, DimensionType type) { super(world, type, 0.0f); }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return new SincosChunkGenerator(world, new SincosBiomeProvider());
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos  chunkPosIn, boolean checkValid) { return null; }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) { return null; }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        int j = 6000;
        float f1 = (j + partialTicks) / 24000.0F - 0.25F;

        if (f1 < 0.0F) {
            f1 += 1.0F;
        }

        if (f1 > 1.0F) {
            f1 -= 1.0F;
        }

        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
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
        return new Vec3d(0, 0, 0);
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
