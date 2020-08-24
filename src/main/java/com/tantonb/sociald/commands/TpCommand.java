package com.tantonb.sociald.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tantonb.sociald.dimensions.SocialdDimensions;
import com.tantonb.sociald.tools.TeleportationTools;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TpCommand implements Command<CommandSource> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final TpCommand CMD = new TpCommand();

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("tp")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(CMD);
    }


    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        int x = player.getPosition().getX();
        int z = player.getPosition().getZ();
        if (player.dimension.equals(SocialdDimensions.SOCD_DIM_SINCOS)) {
            TeleportationTools.teleport(player, DimensionType.OVERWORLD, new BlockPos(x, 200, z));
        } else {
            TeleportationTools.teleport(player, SocialdDimensions.SOCD_DIM_SINCOS, new BlockPos(x, 200, z));
        }
        return 0;
    }
}
