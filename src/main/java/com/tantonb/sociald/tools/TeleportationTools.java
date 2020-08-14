package com.tantonb.sociald.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TeleportationTools {

    private static final Logger LOGGER = LogManager.getLogger();

    private static boolean isValidPlayerPos(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {

        // don't teleport player into solid or liquid blocks
        if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
            return false;
        }

        // make sure player arrives on a solid block or is flying
        BlockState bs = world.getBlockState(pos.down());
        if (!bs.isSolid() && !player.abilities.isFlying) {
            return false;
        }

        return true;
    }

    @Nullable
    private static BlockPos findValidPosVertically(ServerPlayerEntity player, ServerWorld world, BlockPos startPos) {

        // see if they can arrive at same position in new dimension
        if (isValidPlayerPos(player, world, startPos)) {
            return startPos;
        }

        // search up and down y axis for valid player pos
        for (int i = 1; i < 256; ++i) {
            if (isValidPlayerPos(player, world, startPos.up(i))) {
                return startPos.up(i);
            }
            if (isValidPlayerPos(player, world, startPos.down(i))) {
                return startPos.down(i);
            }
        }
        return null;
    }

    static final int MAX_POS_SEARCH_RANGE = 20;

    @Nullable
    private static BlockPos findValidNearbyPos(ServerPlayerEntity player, ServerWorld world, BlockPos startPos) {

        // look near starting position for safe destination position
        BlockPos validPos = findValidPosVertically(player, world, startPos);
        if (validPos == null) {
            // search in expanding x and z directions for valid position
            for (int i = 1; validPos == null && i <= MAX_POS_SEARCH_RANGE; ++i) {
                validPos = findValidPosVertically(player, world, startPos.north(i));
                if (validPos == null) {
                    validPos = findValidPosVertically(player, world, startPos.east(i));
                    if (validPos == null) {
                        validPos = findValidPosVertically(player, world, startPos.south(i));
                        if (validPos == null) {
                            validPos = findValidPosVertically(player, world, startPos.west(i));
                        }
                    }
                }
            }
        }
        return validPos;
    }

    public static boolean teleportNearDimPos(ServerPlayerEntity player, DimensionType dim, BlockPos pos) {
        BlockPos currentPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        ServerWorld world = player.server.getWorld(dim);
        LOGGER.info("Searching for valid position near player location {}, flying is {}", currentPos, player.isAirBorne);
        BlockPos validPos = findValidNearbyPos(player, world, currentPos);
        if (validPos == null) {
            return false;
        }
        player.changeDimension(dim, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPositionAndUpdate(validPos.getX(), validPos.getY(), validPos.getZ());
                return entity;
            }
        });
        return true;
    }

    public static void teleport(ServerPlayerEntity entity, DimensionType destination, BlockPos pos) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                BlockPos surface = null;
                for(surface = new BlockPos(pos.getX(), 255, pos.getZ()); destWorld.isAirBlock(surface.down()); surface = surface.down()) {
                    ;
                }
                entity = repositionEntity.apply(false);
                entity.setPositionAndUpdate(surface.getX(), surface.getY(), surface.getZ());
                return entity;
            }
        });
    }
}
