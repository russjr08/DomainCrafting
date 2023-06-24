package com.kronosad.minecraft.domaincrafting;

import com.kronosad.minecraft.domaincrafting.listeners.CraftingListener;
import com.kronosad.minecraft.domaincrafting.listeners.EntityListener;
import com.kronosad.minecraft.domaincrafting.listeners.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainCrafting extends JavaPlugin {
    private boolean isZombieArrivalPresent = false;

    @Override
    public void onEnable() {
        getLogger().info(String.format("DomainCrafting version %s is now starting!", this.getDescription().getVersion()));

        if(getServer().getPluginManager().isPluginEnabled("ZombieArrival")) {
            getLogger().info("Hello there, ZombieArrival!");
            getLogger().info("Note: Some features might be disabled for balancing purposes around ZombieArrival.");
            isZombieArrivalPresent = true;
        } else {
            getLogger().info("My friend, ZombieArrival, was not detected.");
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(getLogger(), this), this);
        getServer().getPluginManager().registerEvents(new EntityListener(getLogger(), this), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(this), this);

        addRecipes();
    }


    private void addRecipes() {

        // Construct and build recipes

        getLogger().info("Registering DomainCrafting Recipes...");
        getServer().addRecipe(new ShapelessRecipe(new NamespacedKey(this, "string_to_wool"),
                applyWatermarkToItem(new ItemStack(Material.WHITE_WOOL, 1))).addIngredient(4, Material.STRING));

        ItemStack nametag = new ItemStack(Material.NAME_TAG, 1);
        if(nametag.getItemMeta() != null) {
            ItemMeta nametagMeta = nametag.getItemMeta();
            nametagMeta.setDisplayName("Forged Nametag");
            nametag.setItemMeta(nametagMeta);
        }
        applyWatermarkToItem(nametag);
        ShapelessRecipe nametagRecipe = new ShapelessRecipe(new NamespacedKey(this, "nametag"),
                nametag).addIngredient(3, Material.PAPER).addIngredient(Material.IRON_INGOT);


        ShapelessRecipe cobwebRecipe = new ShapelessRecipe(new NamespacedKey(this, "cobwebs"),
                applyWatermarkToItem(new ItemStack(Material.COBWEB))).addIngredient(9, Material.STRING);

        ShapelessRecipe saddleToLeather = new ShapelessRecipe(new NamespacedKey(this, "saddle_to_leather"),
                applyWatermarkToItem(new ItemStack(Material.LEATHER, 4))).addIngredient(Material.SADDLE);

        ShapedRecipe bellRecipe = new ShapedRecipe(new NamespacedKey(this, "bell"), applyWatermarkToItem(new ItemStack(Material.BELL)));


        bellRecipe.shape("sss", "sgs", "sns");
        bellRecipe.setIngredient('s', Material.STICK).setIngredient('n', Material.GOLD_NUGGET).setIngredient('g', Material.GOLD_BLOCK);

        ShapedRecipe tridentRecipe = new ShapedRecipe(new NamespacedKey(this, "trident"), applyWatermarkToItem(new ItemStack(Material.TRIDENT)));

        tridentRecipe.shape("ddd", " s ", " s ");
        tridentRecipe.setIngredient('d', Material.DIAMOND_SWORD);
        tridentRecipe.setIngredient('s', Material.STICK);

        ShapedRecipe saddleRecipe = new ShapedRecipe(new NamespacedKey(this, "saddle"), applyWatermarkToItem(new ItemStack(Material.SADDLE)));

        saddleRecipe.shape("l l", "l l", "lll");
        saddleRecipe.setIngredient('l', Material.LEATHER);

        ShapelessRecipe pigBannerRecipe = new ShapelessRecipe(new NamespacedKey(this, "pigBanner"),
         applyWatermarkToItem(new ItemStack(Material.PIGLIN_BANNER_PATTERN)))
         .addIngredient(Material.MUSIC_DISC_PIGSTEP)
         .addIngredient(Material.WHITE_BANNER);

        ShapedRecipe totemOfUndyingRecipe = new ShapedRecipe(new NamespacedKey(this, "totemOfUndying"), applyWatermarkToItem(Material.TOTEM_OF_UNDYING));
        totemOfUndyingRecipe.shape("ese", "sds", "gsg");
        totemOfUndyingRecipe.setIngredient('e', Material.EMERALD);
        totemOfUndyingRecipe.setIngredient('s', Material.SHIELD);
        totemOfUndyingRecipe.setIngredient('d', Material.DIAMOND_BLOCK);
        totemOfUndyingRecipe.setIngredient('g', Material.GOLD_INGOT);

        ItemStack railResult = applyWatermarkToItem(Material.RAIL);
        railResult.setAmount(16);

        ShapedRecipe copperRailsRecipe = new ShapedRecipe(new NamespacedKey(this, "copperRails"), railResult);
        copperRailsRecipe.shape("c c", "csc", "c c");
        copperRailsRecipe.setIngredient('c', Material.COPPER_INGOT);
        copperRailsRecipe.setIngredient('s', Material.STICK);

        ItemStack detectorRailResult = applyWatermarkToItem(Material.DETECTOR_RAIL);
        detectorRailResult.setAmount(6);

        ShapedRecipe copperDetectorRailRecipe = new ShapedRecipe(new NamespacedKey(this, "copperDetectorRail"), detectorRailResult);
        copperDetectorRailRecipe.shape("c c", "cpc", "crc");
        copperDetectorRailRecipe.setIngredient('c', Material.COPPER_INGOT);
        copperDetectorRailRecipe.setIngredient('p', Material.STONE_PRESSURE_PLATE);
        copperDetectorRailRecipe.setIngredient('r', Material.REDSTONE);

        ItemStack activatorRailResult = applyWatermarkToItem(Material.ACTIVATOR_RAIL);
        activatorRailResult.setAmount(6);

        ShapedRecipe copperActivatorRailRecipe = new ShapedRecipe(new NamespacedKey(this, "copperActivatorRail"), activatorRailResult);
        copperActivatorRailRecipe.shape("csc", "crc", "csc");
        copperActivatorRailRecipe.setIngredient('c', Material.COPPER_INGOT);
        copperActivatorRailRecipe.setIngredient('r', Material.REDSTONE_TORCH);
        copperActivatorRailRecipe.setIngredient('s', Material.STICK);

        ShapedRecipe beeNestRecipe = new ShapedRecipe(new NamespacedKey(this, "beenest"), applyWatermarkToItem(Material.BEE_NEST));
        beeNestRecipe.shape("ccc", "chc", "ccc");
        beeNestRecipe.setIngredient('c', Material.HONEYCOMB);
        beeNestRecipe.setIngredient('h', Material.HONEY_BOTTLE);

        ShapedRecipe bundleRecipe = new ShapedRecipe(new NamespacedKey(this, "bundle"), applyWatermarkToItem(Material.BUNDLE));
        bundleRecipe.shape("shs", "h h", "hhh");
        bundleRecipe.setIngredient('s', Material.STRING);
        bundleRecipe.setIngredient('h', Material.RABBIT_HIDE);
        getLogger().info("WARNING: Bundle is an experimental item. Use at your own risk!");
        // Remove this once Mojang figures it out

        ItemStack lilypadResult = applyWatermarkToItem(Material.LILY_PAD);
        lilypadResult.setAmount(3);

        ShapedRecipe lilypadRecipe = new ShapedRecipe(new NamespacedKey(this, "lilypad"), lilypadResult);
        lilypadRecipe.shape("sss", "sws", "sss");
        lilypadRecipe.setIngredient('s', Material.WHEAT_SEEDS);
        lilypadRecipe.setIngredient('w', Material.WATER_BUCKET);

        RecipeChoice.MaterialChoice plankMaterials = new RecipeChoice.MaterialChoice(
            Material.ACACIA_PLANKS,
            Material.BIRCH_PLANKS,
            Material.CRIMSON_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.OAK_PLANKS,
            Material.SPRUCE_PLANKS,
            Material.WARPED_PLANKS,
            Material.MANGROVE_PLANKS,
            Material.CHERRY_PLANKS
        );

        ShapedRecipe copperPistonRecipe = new ShapedRecipe(new NamespacedKey(this, "piston"), applyWatermarkToItem(Material.PISTON));
        copperPistonRecipe.shape("ppp", "cic", "crc");
        copperPistonRecipe.setIngredient('p', plankMaterials);
        copperPistonRecipe.setIngredient('c', Material.COBBLESTONE);
        copperPistonRecipe.setIngredient('i', Material.COPPER_INGOT);
        copperPistonRecipe.setIngredient('r', Material.REDSTONE);

        ShapedRecipe copperHopperRecipe = new ShapedRecipe(new NamespacedKey(this, "hopper"), applyWatermarkToItem(Material.HOPPER));
        copperHopperRecipe.shape("c c", "cbc", " c ");
        copperHopperRecipe.setIngredient('c', Material.COPPER_INGOT);
        copperHopperRecipe.setIngredient('b', Material.CHEST);

        ItemStack cryingObsidianResult = applyWatermarkToItem(Material.CRYING_OBSIDIAN);
        cryingObsidianResult.setAmount(8);

        ShapedRecipe cryingObsidianRecipe = new ShapedRecipe(new NamespacedKey(this, "crying_obsidian"), cryingObsidianResult);
        cryingObsidianRecipe.shape("ooo", "oto", "ooo");
        cryingObsidianRecipe.setIngredient('o', Material.OBSIDIAN);
        cryingObsidianRecipe.setIngredient('t', Material.GHAST_TEAR);

        addChainMailRecipes();

        ShapelessRecipe anglerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "anglerSherd"),
                applyWatermarkToItem(new ItemStack(Material.ANGLER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.ANGLER_POTTERY_SHERD);

        ShapelessRecipe archerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "archerySherd"),
                applyWatermarkToItem(new ItemStack(Material.ARCHER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.ARCHER_POTTERY_SHERD);

        ShapelessRecipe armsUpSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "armsUpSherd"),
                applyWatermarkToItem(new ItemStack(Material.ARMS_UP_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.ARMS_UP_POTTERY_SHERD);

        ShapelessRecipe bladeSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "bladeSherd"),
                applyWatermarkToItem(new ItemStack(Material.BLADE_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.BLADE_POTTERY_SHERD);

        ShapelessRecipe brewerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "brewerSherd"),
                applyWatermarkToItem(new ItemStack(Material.BREWER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.BREWER_POTTERY_SHERD);

        ShapelessRecipe burnSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "burnSherd"),
                applyWatermarkToItem(new ItemStack(Material.BURN_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.BURN_POTTERY_SHERD);

        ShapelessRecipe dangerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "dangerSherd"),
                applyWatermarkToItem(new ItemStack(Material.DANGER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.DANGER_POTTERY_SHERD);

        ShapelessRecipe explorerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "explorerSherd"),
                applyWatermarkToItem(new ItemStack(Material.EXPLORER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.EXPLORER_POTTERY_SHERD);

        ShapelessRecipe friendSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "friendSherd"),
                applyWatermarkToItem(new ItemStack(Material.FRIEND_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.FRIEND_POTTERY_SHERD);

        ShapelessRecipe heartSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "heartSherd"),
                applyWatermarkToItem(new ItemStack(Material.HEART_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.HEART_POTTERY_SHERD);

        ShapelessRecipe heartbreakSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "heartbreakSherd"),
                applyWatermarkToItem(new ItemStack(Material.HEARTBREAK_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.HEARTBREAK_POTTERY_SHERD);

        ShapelessRecipe howlSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "howlSherd"),
                applyWatermarkToItem(new ItemStack(Material.HOWL_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.HOWL_POTTERY_SHERD);

        ShapelessRecipe minerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "minerSherd"),
                applyWatermarkToItem(new ItemStack(Material.MINER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.MINER_POTTERY_SHERD);

        ShapelessRecipe mournerSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "mournerSherd"),
                applyWatermarkToItem(new ItemStack(Material.MOURNER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.MOURNER_POTTERY_SHERD);

        ShapelessRecipe plentySherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "plentySherd"),
                applyWatermarkToItem(new ItemStack(Material.PLENTY_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.PLENTY_POTTERY_SHERD);

        ShapelessRecipe prizeSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "prizeSherd"),
                applyWatermarkToItem(new ItemStack(Material.PRIZE_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.PRIZE_POTTERY_SHERD);

        ShapelessRecipe sheafSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "sheafSherd"),
                applyWatermarkToItem(new ItemStack(Material.SHEAF_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                .addIngredient(Material.SHEAF_POTTERY_SHERD);

        ShapelessRecipe shelterSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "shelterSherd"),
                applyWatermarkToItem(new ItemStack(Material.SHELTER_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                        .addIngredient(Material.SHELTER_POTTERY_SHERD);

        ShapelessRecipe skullSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "skullSherd"),
                applyWatermarkToItem(new ItemStack(Material.SKULL_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                        .addIngredient(Material.SKULL_POTTERY_SHERD);

        ShapelessRecipe snortSherdRecipe = new ShapelessRecipe(new NamespacedKey(this, "snortSherd"),
                applyWatermarkToItem(new ItemStack(Material.SNORT_POTTERY_SHERD, 2)))
                .addIngredient(Material.BRICK)
                        .addIngredient(Material.SNORT_POTTERY_SHERD);

        getLogger().info("Painstakingly adding Pottery Sherd Duplication...");

        ShapedRecipe upgradeTemplateRecipe = new ShapedRecipe(new NamespacedKey(this, "upgradeTemplate"), applyWatermarkToItem(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
        upgradeTemplateRecipe.shape("dxd", "dbd", "dmd");
        upgradeTemplateRecipe.setIngredient('d', Material.DIAMOND);
        upgradeTemplateRecipe.setIngredient('b', Material.BOOK);
        upgradeTemplateRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        upgradeTemplateRecipe.setIngredient('x', Material.ANCIENT_DEBRIS);

        ShapedRecipe raiserTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "raiserTrim"), applyWatermarkToItem(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE));
        raiserTrimRecipe.shape("dxd", "dbd", "dmd");
        raiserTrimRecipe.setIngredient('d', Material.DIAMOND);
        raiserTrimRecipe.setIngredient('b', Material.BOOK);
        raiserTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        raiserTrimRecipe.setIngredient('x', Material.WITHER_ROSE);

        ShapedRecipe silenceTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "silenceTrim"), applyWatermarkToItem(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE));
        silenceTrimRecipe.shape("dxd", "dbd", "dmd");
        silenceTrimRecipe.setIngredient('d', Material.DIAMOND);
        silenceTrimRecipe.setIngredient('b', Material.BOOK);
        silenceTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        silenceTrimRecipe.setIngredient('x', Material.SCULK);

        ShapedRecipe vexTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "vexTrim"), applyWatermarkToItem(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE));
        vexTrimRecipe.shape("dxd", "dbd", "dmd");
        vexTrimRecipe.setIngredient('d', Material.DIAMOND);
        vexTrimRecipe.setIngredient('b', Material.BOOK);
        vexTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        vexTrimRecipe.setIngredient('x', Material.EMERALD_BLOCK);

        ShapedRecipe duneTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "duneTrim"), applyWatermarkToItem(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE));
        duneTrimRecipe.shape("dxd", "dbd", "dmd");
        duneTrimRecipe.setIngredient('d', Material.DIAMOND);
        duneTrimRecipe.setIngredient('b', Material.BOOK);
        duneTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        duneTrimRecipe.setIngredient('x', Material.CHISELED_SANDSTONE);

        ShapedRecipe coastTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "coastTrim"), applyWatermarkToItem(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE));
        coastTrimRecipe.shape("dxd", "dbd", "dmd");
        coastTrimRecipe.setIngredient('d', Material.DIAMOND);
        coastTrimRecipe.setIngredient('b', Material.BOOK);
        coastTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        coastTrimRecipe.setIngredient('x', Material.DRIED_KELP_BLOCK);

        ShapedRecipe ribTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "ribTrim"), applyWatermarkToItem(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE));
        ribTrimRecipe.shape("dxd", "dbd", "dmd");
        ribTrimRecipe.setIngredient('d', Material.DIAMOND);
        ribTrimRecipe.setIngredient('b', Material.BOOK);
        ribTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        ribTrimRecipe.setIngredient('x', Material.BONE_BLOCK);

        ShapedRecipe sentryTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "sentryTrim"), applyWatermarkToItem(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE));
        sentryTrimRecipe.shape("dxd", "dbd", "dmd");
        sentryTrimRecipe.setIngredient('d', Material.DIAMOND);
        sentryTrimRecipe.setIngredient('b', Material.BOOK);
        sentryTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        sentryTrimRecipe.setIngredient('x', Material.SHIELD);

        ShapedRecipe wayfinderTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "wayfinderTrim"), applyWatermarkToItem(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE));
        wayfinderTrimRecipe.shape("dxd", "dbd", "dmd");
        wayfinderTrimRecipe.setIngredient('d', Material.DIAMOND);
        wayfinderTrimRecipe.setIngredient('b', Material.BOOK);
        wayfinderTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        wayfinderTrimRecipe.setIngredient('x', Material.COARSE_DIRT);

        ShapedRecipe tideTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "tideTrim"), applyWatermarkToItem(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE));
        tideTrimRecipe.shape("dxd", "dbd", "dmd");
        tideTrimRecipe.setIngredient('d', Material.DIAMOND);
        tideTrimRecipe.setIngredient('b', Material.BOOK);
        tideTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        tideTrimRecipe.setIngredient('x', Material.DARK_PRISMARINE);

        ShapedRecipe eyeTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "eyeTrim"), applyWatermarkToItem(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE));
        eyeTrimRecipe.shape("dxd", "dbd", "dmd");
        eyeTrimRecipe.setIngredient('d', Material.DIAMOND);
        eyeTrimRecipe.setIngredient('b', Material.BOOK);
        eyeTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        eyeTrimRecipe.setIngredient('x', Material.ENDER_EYE);

        ShapedRecipe hostTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "hostTrim"), applyWatermarkToItem(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE));
        hostTrimRecipe.shape("dxd", "dbd", "dmd");
        hostTrimRecipe.setIngredient('d', Material.DIAMOND);
        hostTrimRecipe.setIngredient('b', Material.BOOK);
        hostTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        hostTrimRecipe.setIngredient('x', Material.MYCELIUM);

        ShapedRecipe spireTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "spireTrim"), applyWatermarkToItem(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE));
        spireTrimRecipe.shape("dxd", "dbd", "dmd");
        spireTrimRecipe.setIngredient('d', Material.DIAMOND);
        spireTrimRecipe.setIngredient('b', Material.BOOK);
        spireTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        spireTrimRecipe.setIngredient('x', Material.CHORUS_FRUIT);

        ShapedRecipe wardTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "wardTrim"), applyWatermarkToItem(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE));
        wardTrimRecipe.shape("dxd", "dbd", "dmd");
        wardTrimRecipe.setIngredient('d', Material.DIAMOND);
        wardTrimRecipe.setIngredient('b', Material.BOOK);
        wardTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        wardTrimRecipe.setIngredient('x', Material.ECHO_SHARD);

        ShapedRecipe wildTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "wildTrim"), applyWatermarkToItem(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE));
        wildTrimRecipe.shape("dxd", "dbd", "dmd");
        wildTrimRecipe.setIngredient('d', Material.DIAMOND);
        wildTrimRecipe.setIngredient('b', Material.BOOK);
        wildTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        wildTrimRecipe.setIngredient('x', Material.COOKIE);

        ShapedRecipe snoutTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "snoutTrim"), applyWatermarkToItem(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE));
        snoutTrimRecipe.shape("dxd", "dbd", "dmd");
        snoutTrimRecipe.setIngredient('d', Material.DIAMOND);
        snoutTrimRecipe.setIngredient('b', Material.BOOK);
        snoutTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        snoutTrimRecipe.setIngredient('x', Material.PIGLIN_BANNER_PATTERN);

        ShapedRecipe shaperTrimRecipe = new ShapedRecipe(new NamespacedKey(this, "shaperTrim"), applyWatermarkToItem(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE));
        shaperTrimRecipe.shape("dxd", "dbd", "dmd");
        shaperTrimRecipe.setIngredient('d', Material.DIAMOND);
        shaperTrimRecipe.setIngredient('b', Material.BOOK);
        shaperTrimRecipe.setIngredient('m', Material.DIAMOND_BLOCK);
        shaperTrimRecipe.setIngredient('x', Material.BLUE_ICE);

        ItemStack backpack = new ItemStack(Material.CHEST, 1);
        if(backpack.getItemMeta() != null) {
            ItemMeta backpackMeta = backpack.getItemMeta();
            backpackMeta.setDisplayName("Backpack");
            backpack.setItemMeta(backpackMeta);
        }
        applyWatermarkToItem(backpack);
        ShapedRecipe backpackRecipe = new ShapedRecipe(new NamespacedKey(this, "backpack"), backpack);
        backpackRecipe.shape("s s", "scs", "s s");
        backpackRecipe.setIngredient('s', Material.STRING);
        backpackRecipe.setIngredient('c', Material.CHEST);

        // Register recipes
        getServer().addRecipe(nametagRecipe);
        getServer().addRecipe(cobwebRecipe);
        getServer().addRecipe(saddleToLeather);
        getServer().addRecipe(bellRecipe);
        getServer().addRecipe(tridentRecipe);
        getServer().addRecipe(saddleRecipe);
        getServer().addRecipe(pigBannerRecipe);
        getServer().addRecipe(totemOfUndyingRecipe);
        getServer().addRecipe(copperRailsRecipe);
        getServer().addRecipe(copperDetectorRailRecipe);
        getServer().addRecipe(copperActivatorRailRecipe);
        getServer().addRecipe(beeNestRecipe);
        getServer().addRecipe(copperPistonRecipe);
        getServer().addRecipe(cryingObsidianRecipe);
        getServer().addRecipe(getFishInBucketRecipe(Material.COD, Material.COD_SPAWN_EGG));
        getServer().addRecipe(getFishInBucketRecipe(Material.TROPICAL_FISH, Material.TROPICAL_FISH_SPAWN_EGG));
        getServer().addRecipe(getFishInBucketRecipe(Material.SALMON, Material.SALMON_SPAWN_EGG));
        getServer().addRecipe(getFishInBucketRecipe(Material.PUFFERFISH, Material.PUFFERFISH_SPAWN_EGG));
        getServer().addRecipe(copperHopperRecipe);
        getServer().addRecipe(bundleRecipe);
        getServer().addRecipe(lilypadRecipe);
        getServer().addRecipe(anglerSherdRecipe);
        getServer().addRecipe(archerSherdRecipe);
        getServer().addRecipe(armsUpSherdRecipe);
        getServer().addRecipe(bladeSherdRecipe);
        getServer().addRecipe(brewerSherdRecipe);
        getServer().addRecipe(burnSherdRecipe);
        getServer().addRecipe(dangerSherdRecipe);
        getServer().addRecipe(explorerSherdRecipe);
        getServer().addRecipe(friendSherdRecipe);
        getServer().addRecipe(heartSherdRecipe);
        getServer().addRecipe(heartbreakSherdRecipe);
        getServer().addRecipe(howlSherdRecipe);
        getServer().addRecipe(minerSherdRecipe);
        getServer().addRecipe(mournerSherdRecipe);
        getServer().addRecipe(plentySherdRecipe);
        getServer().addRecipe(prizeSherdRecipe);
        getServer().addRecipe(sheafSherdRecipe);
        getServer().addRecipe(shelterSherdRecipe);
        getServer().addRecipe(skullSherdRecipe);
        getServer().addRecipe(snortSherdRecipe);
        getServer().addRecipe(upgradeTemplateRecipe);
        getServer().addRecipe(raiserTrimRecipe);
        getServer().addRecipe(silenceTrimRecipe);
        getServer().addRecipe(vexTrimRecipe);
        getServer().addRecipe(duneTrimRecipe);
        getServer().addRecipe(coastTrimRecipe);
        getServer().addRecipe(ribTrimRecipe);
        getServer().addRecipe(sentryTrimRecipe);
        getServer().addRecipe(wayfinderTrimRecipe);
        getServer().addRecipe(tideTrimRecipe);
        getServer().addRecipe(eyeTrimRecipe);
        getServer().addRecipe(hostTrimRecipe);
        getServer().addRecipe(spireTrimRecipe);
        getServer().addRecipe(wardTrimRecipe);
        getServer().addRecipe(wildTrimRecipe);
        getServer().addRecipe(snoutTrimRecipe);
        getServer().addRecipe(shaperTrimRecipe);
        getServer().addRecipe(backpackRecipe);

        constructMusicRecipes();

        getLogger().info("🚧 Recipe construction complete!");



    }

    private ItemStack applyWatermarkToItem(ItemStack item) {
        List<String> watermark = Collections.singletonList(ChatColor.GOLD + "Forged with DomainCrafting");
        if(item.getItemMeta() != null) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(watermark);
            item.setItemMeta(itemMeta);
        }

        return item;
    }

    private ItemStack applyWatermarkToItem(Material material) {
        return applyWatermarkToItem(new ItemStack(material));
    }

    private ShapelessRecipe getFishInBucketRecipe(Material fishType, Material output) {
        ShapelessRecipe fishRecipe = new ShapelessRecipe(new NamespacedKey(this, fishType.name() + "Bucket"), applyWatermarkToItem(new ItemStack(output)));
        fishRecipe.addIngredient(Material.WATER_BUCKET);
        fishRecipe.addIngredient(Material.GLISTERING_MELON_SLICE);
        fishRecipe.addIngredient(fishType);

        getLogger().info(String.format("Constructed %s => %s recipe", fishType.name(), output.name()));

        return fishRecipe;
    }

    private void addChainMailRecipes() {
        getLogger().info("Constructing chainmail armor recipes");

        ShapedRecipe chainmailHelmet = new ShapedRecipe(new NamespacedKey(this, "chainmailHelmet"), applyWatermarkToItem(Material.CHAINMAIL_HELMET));
        chainmailHelmet.shape("ccc", "c c");
        chainmailHelmet.setIngredient('c', Material.CHAIN);

        ShapedRecipe chainmailChest = new ShapedRecipe(new NamespacedKey(this, "chainmailChest"), applyWatermarkToItem(Material.CHAINMAIL_CHESTPLATE));
        chainmailChest.shape("c c", "ccc", "ccc");
        chainmailChest.setIngredient('c', Material.CHAIN);

        ShapedRecipe chainmailLeggings = new ShapedRecipe(new NamespacedKey(this, "chainmailLeggings"), applyWatermarkToItem(Material.CHAINMAIL_LEGGINGS));
        chainmailLeggings.shape("ccc", "c c", "c c");
        chainmailLeggings.setIngredient('c', Material.CHAIN);

        ShapedRecipe chainmailBoots = new ShapedRecipe(new NamespacedKey(this, "chainmailBoots"), applyWatermarkToItem(Material.CHAINMAIL_BOOTS));
        chainmailBoots.shape("c c", "c c");
        chainmailBoots.setIngredient('c', Material.CHAIN);

        getServer().addRecipe(chainmailHelmet);
        getServer().addRecipe(chainmailChest);
        getServer().addRecipe(chainmailLeggings);
        getServer().addRecipe(chainmailBoots);
    }

    private void constructMusicRecipes() {

        HashMap<Material, Material> listOfDiscRecipes = new HashMap<>();
        listOfDiscRecipes.put(Material.YELLOW_DYE, Material.MUSIC_DISC_13);
        listOfDiscRecipes.put(Material.GREEN_DYE, Material.MUSIC_DISC_CAT);
        listOfDiscRecipes.put(Material.ORANGE_DYE, Material.MUSIC_DISC_BLOCKS);
        listOfDiscRecipes.put(Material.RED_DYE, Material.MUSIC_DISC_CHIRP);
        listOfDiscRecipes.put(Material.LIME_DYE, Material.MUSIC_DISC_FAR);
        listOfDiscRecipes.put(Material.MAGENTA_DYE, Material.MUSIC_DISC_MALL);
        listOfDiscRecipes.put(Material.PURPLE_DYE, Material.MUSIC_DISC_MELLOHI);
        listOfDiscRecipes.put(Material.BLACK_DYE, Material.MUSIC_DISC_STAL);
        listOfDiscRecipes.put(Material.WHITE_DYE, Material.MUSIC_DISC_STRAD);
        listOfDiscRecipes.put(Material.CYAN_DYE, Material.MUSIC_DISC_WARD);
        listOfDiscRecipes.put(Material.BLUE_DYE, Material.MUSIC_DISC_WAIT);
        listOfDiscRecipes.put(Material.PORKCHOP, Material.MUSIC_DISC_PIGSTEP);
        listOfDiscRecipes.put(Material.ENDER_EYE, Material.MUSIC_DISC_OTHERSIDE);
        listOfDiscRecipes.put(Material.BRUSH, Material.MUSIC_DISC_RELIC);



        for(Map.Entry<Material, Material> entry : listOfDiscRecipes.entrySet()) {
            Material dye = entry.getKey();
            Material disc = entry.getValue();

            ShapedRecipe musicRecipe = new ShapedRecipe(new NamespacedKey(this, disc.toString()), applyWatermarkToItem(new ItemStack(disc)));

            musicRecipe.shape("wew", "ede", "wew").setIngredient('w', Material.OAK_PLANKS).setIngredient('e', Material.EMERALD).setIngredient('d', dye);

            getLogger().info("Registering music recipe: " + disc);
            getServer().addRecipe(musicRecipe);

            FurnaceRecipe discSmeltRecipe = new FurnaceRecipe(new NamespacedKey(this, String.format("smelt_%s", disc)),
                    new ItemStack(Material.MUSIC_DISC_11), disc, 1F, 600);

            getLogger().info("Registering disc smelting recipe for: " + disc);

            getServer().addRecipe(discSmeltRecipe);
    }

        getLogger().info("^^^ Over-engineered solution brought to you by yours truly, Russ. You're welcome Michael... :P");
    }

    public boolean isZombieArrivalPresent() {
        return isZombieArrivalPresent;
    }

}
