package com.kronosad.minecraft.domaincrafting.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
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
