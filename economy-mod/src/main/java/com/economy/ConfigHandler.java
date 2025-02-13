package com.alpha30811.economy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigHandler {

    private static final Path CONFIG_PATH = Paths.get(FabricLoader.getInstance().getConfigDirectory().toString(), "economy/config.txt");
    private Properties config = new Properties();

    public ConfigHandler() {
        loadConfig();
    }

    // Load config values from the server's config folder
    public void loadConfig() {
        File configFile = CONFIG_PATH.toFile();
        
        // Check if the config file exists
        if (configFile.exists()) {
            try (InputStream input = new FileInputStream(configFile)) {
                config.load(input);
            } catch (IOException e) {
                e.printStackTrace();
                setDefaultConfig();
            }
        } else {
            // If config file doesn't exist, create a new one with default values
            setDefaultConfig();
            saveConfig();
        }
    }

    // Set default configuration values
    private void setDefaultConfig() {
        config.setProperty("starting_balance", "100.0");
        config.setProperty("op_level_required", "4");
    }

    // Save config values to the file in the server's config folder
    public void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_PATH.toFile())) {
            config.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the starting balance (defaults to 100.0 if not set)
    public double getStartingBalance() {
        return Double.parseDouble(config.getProperty("starting_balance", "100.0"));
    }

    // Get the required OP level (defaults to 4 if not set)
    public int getOpLevelRequired() {
        return Integer.parseInt(config.getProperty("op_level_required", "4"));
    }
}
