package com.alpha30811.economy;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.UuidArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayer;
import net.minecraft.text.Text;

import java.util.UUID;

public class EconomyCommands {
    private static final EconomyManager economyManager = new EconomyManager();

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("balance")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayer();
                if (player != null) {
                    double balance = economyManager.getBalance(player.getUuid());
                    context.getSource().sendFeedback(() -> Text.literal("Your balance: $" + balance), false);
                }
                return Command.SINGLE_SUCCESS;
            })
        );

        dispatcher.register(CommandManager.literal("pay")
            .then(CommandManager.argument("target", UuidArgumentType.uuid())
            .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
            .executes(context -> {
                ServerPlayer sender = context.getSource().getPlayer();
                if (sender != null) {
                    UUID targetUuid = UuidArgumentType.getUuid(context, "target");
                    double amount = DoubleArgumentType.getDouble(context, "amount");

                    if (economyManager.transfer(sender.getUuid(), targetUuid, amount)) {
                        context.getSource().sendFeedback(() -> Text.literal("You sent $" + amount), false);
                    } else {
                        context.getSource().sendFeedback(() -> Text.literal("Transaction failed!"), false);
                    }
                }
                return Command.SINGLE_SUCCESS;
            })))
        );

        dispatcher.register(CommandManager.literal("addbalance")
            .requires(source -> source.hasPermissionLevel(4))
            .then(CommandManager.argument("target", UuidArgumentType.uuid())
            .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
            .executes(context -> {
                UUID targetUuid = UuidArgumentType.getUuid(context, "target");
                double amount = DoubleArgumentType.getDouble(context, "amount");
                economyManager.addBalance(targetUuid, amount);
                context.getSource().sendFeedback(() -> Text.literal("Added $" + amount), true);
                return Command.SINGLE_SUCCESS;
            })))
        );

        dispatcher.register(CommandManager.literal("removebalance")
            .requires(source -> source.hasPermissionLevel(4))
            .then(CommandManager.argument("target", UuidArgumentType.uuid())
            .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
            .executes(context -> {
                UUID targetUuid = UuidArgumentType.getUuid(context, "target");
                double amount = DoubleArgumentType.getDouble(context, "amount");

                if (economyManager.removeBalance(targetUuid, amount)) {
                    context.getSource().sendFeedback(() -> Text.literal("Removed $" + amount), true);
                } else {
                    context.getSource().sendFeedback(() -> Text.literal("Failed to remove funds."), true);
                }
                return Command.SINGLE_SUCCESS;
            })))
        );

        dispatcher.register(CommandManager.literal("setbalance")
            .requires(source -> source.hasPermissionLevel(4))
            .then(CommandManager.argument("target", UuidArgumentType.uuid())
            .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0))
            .executes(context -> {
                UUID targetUuid = UuidArgumentType.getUuid(context, "target");
                double amount = DoubleArgumentType.getDouble(context, "amount");
                economyManager.setBalance(targetUuid, amount);
                context.getSource().sendFeedback(() -> Text.literal("Set balance to $" + amount), true);
                return Command.SINGLE_SUCCESS;
            })))
        );
    }
}
