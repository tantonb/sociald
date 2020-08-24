package com.tantonb.sociald.dimensions.depths;

import com.tantonb.sociald.SimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DepthsBiomeProvider extends BiomeProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Layer biomeChooser;
    private static final List<Biome> SPAWN = Collections.singletonList(Biomes.DESERT);
    private static final List<Biome> BIOMES = new ArrayList<>(Arrays.asList(
                    Biomes.MOUNTAINS,
                    Biomes.BADLANDS,
                    Biomes.DESERT,
                    Biomes.DESERT_HILLS
            ));

    public DepthsBiomeProvider(WorldInfo worldInfo) {
        super(new HashSet<>(SPAWN));
        this.biomeChooser = LayerUtil.func_227474_a_(
                worldInfo.getSeed(),
                worldInfo.getGenerator(),
                new OverworldGenSettings()
        );
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        double noise = (SimplexNoise.noise(x >> 4,z >> 4) + 1) / 2;
        int biomeIdx = (int)(noise * 4);
        //LOGGER.info("noise: {}, biomeIdx: {}", noise, biomeIdx);
        return BIOMES.get(biomeIdx);
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return SPAWN;
    }

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return false;
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (topBlocksCache.isEmpty()) {
            for(Biome biome : this.biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return topBlocksCache;
    }
}
