package com.alpha30811.economy;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {
    private final HashMap<UUID, Double> balances = new HashMap<>();

    public double getBalance(UUID player) {
        return balances.getOrDefault(player, 0.0);
    }

    public void setBalance(UUID player, double amount) {
        balances.put(player, amount);
    }

    public void addBalance(UUID player, double amount) {
        balances.put(player, getBalance(player) + amount);
    }

    public boolean removeBalance(UUID player, double amount) {
        double currentBalance = getBalance(player);
        if (currentBalance >= amount) {
            balances.put(player, currentBalance - amount);
            return true;
        }
        return false;
    }

    public boolean transfer(UUID sender, UUID receiver, double amount) {
        if (removeBalance(sender, amount)) {
            addBalance(receiver, amount);
            return true;
        }
        return false;
    }
}
