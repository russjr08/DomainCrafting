package com.kronosad.minecraft.domaincrafting.listeners;

import com.kronosad.minecraft.domaincrafting.DomainCrafting;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.logging.Logger;

public class PlayerListener implements Listener {

    private final DomainCrafting plugin;

    public PlayerListener(Logger logger, DomainCrafting plugin) {
        this.plugin = plugin;
        logger.info("Player Listener Registered!");
    }

    @EventHandler
    public void onPlayerBlockInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SMOOTH_STONE_SLAB) {
            if(((Slab) event.getClickedBlock().getBlockData()).getType() == Slab.Type.DOUBLE) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")) {
                    event.getClickedBlock().setType(Material.SMOOTH_STONE);
                    event.getClickedBlock().getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, 17);

                    damageHeldItem(event.getPlayer());
                }
            }
        }

        // In-World Smelting Recipes
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FLINT_AND_STEEL) {
            if(event.getClickedBlock().getType() == Material.COBBLESTONE) {
                event.getClickedBlock().setType(Material.STONE);
                damageHeldItem(event.getPlayer());
                event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10F, 1F);
                event.setCancelled(true);
            } else if (event.getClickedBlock().getType() == Material.SAND || event.getClickedBlock().getType() == Material.RED_SAND) {
                event.getClickedBlock().setType(Material.GLASS);
                damageHeldItem(event.getPlayer());
                event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10F, 1F);
                event.setCancelled(true);
            }

        }

        // In-World Shearing Glass <-> Glass Pane <-> Glass
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            if(event.getClickedBlock().getType() == Material.GLASS) {
                event.getClickedBlock().setType(Material.GLASS_PANE);
                event.getClickedBlock().getState().update(true, true);
                damageHeldItem(event.getPlayer());
                event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_GLASS_BREAK, 10F, 1F);
            }
        }

    }

    private void damageHeldItem(Player player) {
        if(player.getGameMode() != GameMode.CREATIVE) {

            Damageable itemMeta = (Damageable) player.getInventory().getItemInMainHand().getItemMeta();

            itemMeta.setDamage(itemMeta.getDamage() + 5);

            // Check to see if the player's pickaxe will be past the max durability, and delete it if so.
            if (itemMeta.getDamage() > player.getInventory().getItemInMainHand().getType().getMaxDurability()) {
                player.getInventory().getItemInMainHand().setAmount(0);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
            } else {
                player.getInventory().getItemInMainHand().setItemMeta((ItemMeta) itemMeta);
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

                if(stand.getEquipment().getChestplate() != null && stand.getEquipment().getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                    stand.getEquipment().setChestplate(null);
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
                    if(plugin.isZombieArrivalPresent()) {
                        TextComponent message = new TextComponent("A curse impacts your ability to create this ward...");
                        message.setItalic(true);
                        message.setBold(true);
                        message.setColor(ChatColor.DARK_RED.asBungee());
                        Player player = (Player) event.getPlayer();
                        player.sendActionBar(message);
                        event.getPlayer().playEffect(EntityEffect.SHIELD_BREAK);

                        return;
                    }
                    if(event.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL
                            || event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
                        TextComponent message = new TextComponent("You've been protected by an ancient artifact.");
                        message.setItalic(true);
                        message.setColor(ChatColor.GOLD.asBungee());
                        Player player = (Player) event.getPlayer();
                        player.sendActionBar(message);
                        event.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
                    } else {
                        TextComponent message = new TextComponent("You do not feel the same sense of protection in this dimension.");
                        message.setItalic(true);
                        message.setBold(true);
                        message.setColor(ChatColor.DARK_RED.asBungee());
                        Player player = (Player) event.getPlayer();
                        player.sendActionBar(message);
                        event.getPlayer().playEffect(EntityEffect.SHIELD_BREAK);
                    }

                }
            }
        }
    }

    @EventHandler
    public void potionConsumeEvent(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.POTION) {
            if(event.getItem().getItemMeta() instanceof PotionMeta) {
                final PotionMeta pm = (PotionMeta) event.getItem().getItemMeta();
                if(pm.getBasePotionData().getType() == PotionType.AWKWARD) {
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2400,
                            5, true, true));
                }
            }
        }
    }
}
