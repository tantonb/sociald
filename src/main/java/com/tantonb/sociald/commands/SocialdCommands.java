package com.tantonb.sociald.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class SocialdCommands {
    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        // register sociald command set
        LiteralCommandNode<CommandSource> socdCommand = dispatcher.register(
                Commands.literal("socd")
                        .then(TpCommand.register(dispatcher))
        );
    }
}
