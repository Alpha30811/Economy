package com.alpha30811.economy;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class EconomyCommands {

    public static void registerCommands() {
        CommandManager.literal("economy")
            .requires(source -> EconomyMod.hasPermission(source.getEntity().getScoreboard().getPlayerTeam(source.getEntity().getName()).getScore()))
            .then(CommandManager.literal("balance")
                .executes(context -> {
                    // Your balance command logic here
                    return 1;
                })
            ).build();
    }
}
