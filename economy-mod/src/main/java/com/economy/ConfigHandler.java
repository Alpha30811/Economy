package com.alpha30811.economy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class ConfigHandler {
    private static final String FILE_PATH = "config/economy_balances.json";
    private static final Gson GSON = new Gson();
    
    public static void saveBalances(HashMap<UUID, Double> balances) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            GSON.toJson(balances, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<UUID, Double> loadBalances() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<HashMap<UUID, Double>>() {}.getType();
            return GSON.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}