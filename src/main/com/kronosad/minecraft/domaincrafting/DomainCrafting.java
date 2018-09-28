package com.kronosad.minecraft.domaincrafting;

import com.kronosad.minecraft.domaincrafting.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DomainCrafting extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("DomainCrafting is starting up!");
        getServer().getPluginManager().registerEvents(new PlayerListener(getLogger()), this);
    }
}
