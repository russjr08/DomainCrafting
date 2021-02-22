package com.kronosad.minecraft.domaincrafting.listeners;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class EntityListener implements Listener {

    private Logger logger;

    public EntityListener(Logger logger) {
        this.logger = logger;
        logger.info("Registered Entity Listener!");
    }

    @EventHandler
    public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        PotionEffectType[] beaconEffectTypes = {PotionEffectType.REGENERATION, PotionEffectType.SPEED,
                PotionEffectType.FAST_DIGGING, PotionEffectType.JUMP, PotionEffectType.INCREASE_DAMAGE};


        if(event.getTarget() == null || !(event.getTarget() instanceof Player)) {
            return;
        }

        boolean isPlayerUnderBeaconEffect = false;
        List<EntityType> blacklistedEnemyTypes = Lists.newArrayList(EntityType.WITHER, EntityType.ENDER_DRAGON,
                EntityType.ELDER_GUARDIAN);

        List<EntityType> animalButNotAnimalTypes = Lists.newArrayList(EntityType.HOGLIN, EntityType.SLIME,
                EntityType.MAGMA_CUBE);

        if((event.getEntity() instanceof Animals) && !animalButNotAnimalTypes.contains(event.getEntityType())) {
            return; // Needs to be a monster (or an "animal" that is not an "animal"); If it is applied across all enemies, it breaks leads.
        }

        if((event.getTarget().getWorld().getEnvironment() != World.Environment.NORMAL
                && event.getTarget().getWorld().getEnvironment() != World.Environment.NETHER)) {
            return; // Stop checking conditions and proceed as normal
        }

        if(blacklistedEnemyTypes.contains(event.getEntityType())) {
            return; // Stop checking conditions and proceed as normal
        }

        Player player = (Player) event.getTarget();

        if(event.getTarget() instanceof Player) {
            for(PotionEffect potion : player.getActivePotionEffects()) {
                if(Arrays.asList(beaconEffectTypes).contains(potion.getType())) {
                    isPlayerUnderBeaconEffect = true;
                }
            }
        }

        if(event.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY && isPlayerUnderBeaconEffect) {
            if(event.getTarget() instanceof Player) {
                TextComponent message = new TextComponent("This enemy's sense of revenge breaks through your ancient artifact's protection");
//                message.setBold(true);
                message.setColor(ChatColor.DARK_RED);
                player.sendActionBar();
                player.sendMessage(message);
            }
            return; // You brought this upon yourself.
        }

        event.setCancelled(isPlayerUnderBeaconEffect);
    }

}
