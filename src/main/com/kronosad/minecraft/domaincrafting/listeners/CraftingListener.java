package com.kronosad.minecraft.domaincrafting.listeners;


import com.kronosad.minecraft.domaincrafting.DomainCrafting;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CraftingListener implements Listener {

    private final String pluginNamespace;
    private final DomainCrafting plugin;

    public CraftingListener(DomainCrafting plugin) {
        this.pluginNamespace = plugin.getName().toLowerCase();
        this.plugin = plugin;
    }

    @EventHandler
    public void craftCompletedEvent(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();

        // The following checks to see if the Player crafted an item using a DomainCrafting Recipe
        // If they did, it will play a special sound effect

        boolean shouldPlaySound = false;

        if(event.getRecipe() instanceof ShapedRecipe recipe) {
            if(recipe.getKey().getNamespace().equalsIgnoreCase(this.pluginNamespace)) {
                shouldPlaySound = true;
            }
        }

        if(event.getRecipe() instanceof ShapelessRecipe recipe) {
            if(recipe.getKey().getNamespace().equalsIgnoreCase(this.pluginNamespace)) {
                shouldPlaySound = true;
            }
        }

        if(shouldPlaySound) {

            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, () ->
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1F, 1F), 5);
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, () ->
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1F, 1F), 10);
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, () ->
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F), 35);
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, () ->
                    player.getLocation().getWorld().playEffect(player.getLocation(), Effect.EXTINGUISH, null), 45);

        }

    }

}
