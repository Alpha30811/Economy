package com.alpha30811.economy;

import net.fabricmc.api.ModInitializer;

public class EconomyMod implements ModInitializer {
    @Override
    public void onInitialize() {
        EconomyCommands.register();
    }
}
