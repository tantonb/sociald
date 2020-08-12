package com.tantonb.sociald.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class TeleportationTools {

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