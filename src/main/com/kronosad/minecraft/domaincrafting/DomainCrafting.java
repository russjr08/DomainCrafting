package com.kronosad.minecraft.domaincrafting;

import com.kronosad.minecraft.domaincrafting.listeners.PlayerListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class DomainCrafting extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("DomainCrafting is starting up!");
        getServer().getPluginManager().registerEvents(new PlayerListener(getLogger()), this);

        addRecipes();
    }


    private void addRecipes() {

        // Construct and build recipes

        getLogger().info("Registering String to Wool recipe...");
        getServer().addRecipe(new ShapelessRecipe(new NamespacedKey(this, "string_to_wool"),
                new ItemStack(Material.WHITE_WOOL, 1)).addIngredient(4, Material.STRING));

        getLogger().info("Constructing Nametag recipe...");
        ItemStack nametag = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta nametagMeta = nametag.getItemMeta();
        nametagMeta.setDisplayName("Forged Nametag");
        nametag.setItemMeta(nametagMeta);
        ShapelessRecipe nametagRecipe = new ShapelessRecipe(new NamespacedKey(this, "nametag"),
                nametag).addIngredient(3, Material.PAPER).addIngredient(Material.IRON_INGOT);


        ShapelessRecipe cobwebRecipe = new ShapelessRecipe(new NamespacedKey(this, "cobwebs"),
                new ItemStack(Material.COBWEB)).addIngredient(9, Material.STRING);



        // Register recipes
        getServer().addRecipe(nametagRecipe);
        getServer().addRecipe(cobwebRecipe);

        constructMusicRecipes();


    }

    private void constructMusicRecipes() {

        HashMap<Material, Material> listOfDiscRecipes = new HashMap<>();
        listOfDiscRecipes.put(Material.DANDELION_YELLOW, Material.MUSIC_DISC_13);
        listOfDiscRecipes.put(Material.CACTUS_GREEN, Material.MUSIC_DISC_CAT);
        listOfDiscRecipes.put(Material.ORANGE_DYE, Material.MUSIC_DISC_BLOCKS);
        listOfDiscRecipes.put(Material.ROSE_RED, Material.MUSIC_DISC_CHIRP);
        listOfDiscRecipes.put(Material.LIME_DYE, Material.MUSIC_DISC_FAR);
        listOfDiscRecipes.put(Material.MAGENTA_DYE, Material.MUSIC_DISC_MALL);
        listOfDiscRecipes.put(Material.PURPLE_DYE, Material.MUSIC_DISC_MELLOHI);
        listOfDiscRecipes.put(Material.INK_SAC, Material.MUSIC_DISC_STAL);
        listOfDiscRecipes.put(Material.BONE_MEAL, Material.MUSIC_DISC_STRAD);
        listOfDiscRecipes.put(Material.CYAN_DYE, Material.MUSIC_DISC_WARD);
        listOfDiscRecipes.put(Material.LAPIS_LAZULI, Material.MUSIC_DISC_WAIT);



        for(Map.Entry<Material, Material> entry : listOfDiscRecipes.entrySet()) {
            Material dye = entry.getKey();
            Material disc = entry.getValue();

            ShapedRecipe musicRecipe = new ShapedRecipe(new NamespacedKey(this, disc.toString()), new ItemStack(disc));

            musicRecipe.shape("wew", "ede", "wew").setIngredient('w', Material.OAK_PLANKS).setIngredient('e', Material.EMERALD).setIngredient('d', dye);

            getLogger().info("Registering music recipe: " + disc.toString());
            getServer().addRecipe(musicRecipe);

            FurnaceRecipe discSmeltRecipe = new FurnaceRecipe(new NamespacedKey(this, String.format("smelt_%s", disc.toString())),
                    new ItemStack(Material.MUSIC_DISC_11), disc, 1F, 600);

            getLogger().info("Registering disc smelting recipe for: " + disc.toString());

            getServer().addRecipe(discSmeltRecipe);
    }

        getLogger().info("^^^ Over-engineered solution brought to you by yours truly, Russ. You're welcome Michael... :P");
    }

}
