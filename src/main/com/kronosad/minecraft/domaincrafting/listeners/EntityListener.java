package com.kronosad.minecraft.domaincrafting.listeners;

import com.google.common.collect.Lists;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

        boolean isPlayerUnderBeaconEffect = false;
        List<EntityType> blacklistedEnemyTypes = Lists.newArrayList(EntityType.WITHER, EntityType.ENDER_DRAGON,
                EntityType.ELDER_GUARDIAN);

        if(!(event.getEntity() instanceof Monster)) {
            return; // Needs to be a monster; If it is applied across all enemies, it breaks leads.
        }

        if(event.getTarget() != null && (event.getTarget().getWorld().getEnvironment() != World.Environment.NORMAL
                && event.getTarget().getWorld().getEnvironment() != World.Environment.NETHER)) {
            return; // Stop checking conditions and proceed as normal
        }

        if(blacklistedEnemyTypes.contains(event.getEntityType())) {
            return; // Stop checking conditions and proceed as normal
        }

        if(event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            for(PotionEffect potion : player.getActivePotionEffects()) {
                if(Arrays.asList(beaconEffectTypes).contains(potion.getType())) {
                    isPlayerUnderBeaconEffect = true;
                }
            }
        }

        event.setCancelled(isPlayerUnderBeaconEffect);
    }

}
