package com.thepwo.light;

import org.bukkit.plugin.java.JavaPlugin;

public final class Light extends JavaPlugin {
    private static Light instance;

    public static Light getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}
