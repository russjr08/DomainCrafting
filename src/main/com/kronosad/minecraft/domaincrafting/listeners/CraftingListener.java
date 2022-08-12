package com.kronosad.minecraft.domaincrafting.listeners;


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

    public static HashMap<ItemStack, String> specialLore = new HashMap<ItemStack, String>();
    public static HashMap<ItemStack, String> specialName = new HashMap<ItemStack, String>();

    private final String pluginNamespace;

    public CraftingListener(String pluginNamespace) {
        this.pluginNamespace = pluginNamespace;
    }


    @EventHandler
    public void craftingEvent(PrepareItemCraftEvent event){
        if(event.getRecipe() != null) {
            ItemStack crafted = event.getRecipe().getResult();

            event.getInventory().setResult(getCustomItem(crafted));
        }
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
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
        }

    }

    public ItemStack getCustomItem(ItemStack result){
        for(Map.Entry<ItemStack, String> entry : specialLore.entrySet()){

            ItemStack thisStack = entry.getKey();

            if(result.getType() == thisStack.getType()){
                ItemMeta meta = result.getItemMeta();
                ArrayList<String> lores = new ArrayList<String>();
                String[] linesOfLores = entry.getValue().split("\n");
                Collections.addAll(lores, linesOfLores);


                meta.setLore(lores);
                result.setItemMeta(meta);
            }
        }

        for(Map.Entry<ItemStack, String> entry : specialName.entrySet()){
            ItemStack thisStack = entry.getKey();
            if(result.getType() == thisStack.getType()){
                ItemMeta meta = result.getItemMeta();
                meta.setDisplayName(entry.getValue());
                result.setItemMeta(meta);

            }
        }
        return result;
    }

}
