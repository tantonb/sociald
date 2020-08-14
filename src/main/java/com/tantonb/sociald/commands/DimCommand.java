package com.tantonb.sociald.commands;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.tantonb.sociald.tools.TeleportationTools;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DimCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("dim")
                .requires(cs -> cs.hasPermissionLevel(0))
                .then(Commands.argument("name", StringArgumentType.string())
                    .executes(context -> execute(context, StringArgumentType.getString(context, "name"))));
    }

    private static Map<String, DimensionType> getKnownDimensions() {
        HashMap<String, DimensionType> dims = new HashMap<>();
        for (DimensionType dim: DimensionType.getAll()) {
            String dimStr = ""+dim;
            int i = dimStr.indexOf(":");
            i = i >= 0 ? i + 1 : 0;
            int k = dimStr.indexOf("}");
            k = k > 0 ? k : dimStr.length();
            String dimKey = dimStr.substring(i,k);
            if (!dims.containsKey(dimKey)) {
                dims.put(dimKey, dim);
            }
            else {
                LOGGER.warn("Duplicate dimension name found: '{}'", dimKey);
            }
        }
        return dims;
    }

    private static int execute(CommandContext<CommandSource> context, String dimName) throws CommandSyntaxException {

        LOGGER.info("DimCommand.execute({})", dimName);

        Map<String, DimensionType> knownDims = getKnownDimensions();
        if (!knownDims.containsKey(dimName)) {
            String dimStr = String.join(", ", knownDims.keySet());
            Message msg = new LiteralMessage(
                    "Unknown dimension: " + dimName + "\nKnown dimensions: " + dimStr);
            throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
        }

        ServerPlayerEntity player = context.getSource().asPlayer();
        if (!TeleportationTools.teleportNearDimPos(player, knownDims.get(dimName), player.getPosition())) {
            Message msg = new LiteralMessage(
                    "Unable to teleport to dimension " + dimName + " near this location");
            throw new CommandSyntaxException(new SimpleCommandExceptionType(msg), msg);
        };

        return 0;
    }
}
