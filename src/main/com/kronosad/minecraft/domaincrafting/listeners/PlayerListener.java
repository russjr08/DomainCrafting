package com.kronosad.minecraft.domaincrafting.listeners;

import org.bukkit.*;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Logger;

public class PlayerListener implements Listener {

    private Logger logger;

    public PlayerListener(Logger logger) {
        this.logger = logger;

        logger.info("Player Listener Registered!");
    }

    @EventHandler
    public void onPlayerBlockInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SMOOTH_STONE_SLAB) {
            if(((Slab) event.getClickedBlock().getBlockData()).getType() == Slab.Type.DOUBLE) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")) {
                    event.getClickedBlock().setType(Material.SMOOTH_STONE);
                    event.getClickedBlock().getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, 17);

                    if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {

                        Damageable itemMeta = (Damageable) event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                        itemMeta.setDamage(itemMeta.getDamage() + 5);

                        // Check to see if the player's pickaxe will be past the max durability, and delete it if so.
                        if (itemMeta.getDamage() > event.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability()) {
                            event.getPlayer().getInventory().getItemInMainHand().setAmount(0);
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
                        } else {
                            event.getPlayer().getInventory().getItemInMainHand().setItemMeta((ItemMeta) itemMeta);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.SMOOTH_STONE && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if(!event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.SMOOTH_STONE_SLAB, 2));
            }
        }
    }

    @EventHandler
    public void playerEntityInteractEvent(PlayerInteractAtEntityEvent event) {

        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            if(event.getRightClicked() instanceof ArmorStand) {
                ArmorStand stand = (ArmorStand) event.getRightClicked();

                if(stand.getChestplate() != null && stand.getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                    stand.setChestplate(null);
                    stand.getWorld().dropItemNaturally(stand.getLocation(), new ItemStack(Material.SADDLE));
                    event.getPlayer().playSound(stand.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1F, 1F);
                }

                if(event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                    ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                    ((Damageable) meta).setDamage(((Damageable)meta).getDamage() + 5);
                    event.getPlayer().getInventory().getItemInMainHand().setItemMeta(meta);
                }
            }
        }
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        if(event.getInventory().getType() == InventoryType.BEACON) {
            Block lookingAt = event.getPlayer().getTargetBlock(null, 10);
            if(lookingAt.getState() instanceof Beacon) {
                Beacon beacon = (Beacon) lookingAt.getState();
                if(beacon.getPrimaryEffect() != null || beacon.getSecondaryEffect() != null) {
                    if(event.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL
                            || event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
                        event.getPlayer().sendMessage(ChatColor.GOLD.toString() + ChatColor.ITALIC + "You've been protected by an ancient artifact.");
                        event.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.DARK_RED.toString() + ChatColor.ITALIC + "You do not feel the same sense of protection in this dimension");
                        event.getPlayer().playEffect(EntityEffect.SHIELD_BREAK);
                    }

                }
            }
        }
    }
}
