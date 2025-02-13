package com.alpha30811.economy;

import net.minecraft.server.network.ServerPlayerEntity;

public class EconomyMod {

    private static ConfigHandler configHandler = new ConfigHandler();

    public static void giveStartingBalance(ServerPlayerEntity player) {
        double startingBalance = configHandler.getStartingBalance();
        EconomyManager economyManager = new EconomyManager();
        
        // Give starting balance if the player has none
        if (economyManager.getBalance(player.getUuid()) == 0.0) {
            economyManager.setBalance(player.getUuid(), startingBalance);
            player.sendMessage("Welcome! You have received " + startingBalance + " coins.");
        }
    }

    public static boolean hasPermission(int opLevel) {
        return opLevel >= configHandler.getOpLevelRequired();
    }
}
