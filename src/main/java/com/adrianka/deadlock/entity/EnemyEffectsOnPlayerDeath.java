package com.adrianka.deadlock.entity;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.mcenhancedlib.entity.MCELModifyEntityProperties;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;

import java.util.ArrayList;
import java.util.List;

public final class EnemyEffectsOnPlayerDeath {
    private static List<Holder<MobEffect>> getEnabledEffects(DeathEventsData deathEventsData) {
        List<Holder<MobEffect>> enabledEffects = new ArrayList<>();

        if (deathEventsData.isEnemySpeedEnabled()) enabledEffects.add(MobEffects.SPEED);
        if (deathEventsData.isEnemyStrengthEnabled()) enabledEffects.add(MobEffects.STRENGTH);
        if (deathEventsData.isEnemyResistanceEnabled()) enabledEffects.add(MobEffects.RESISTANCE);

        return enabledEffects;
    }

    public static void addMobEffects(Mob mob, DeathEventsData deathEventsData) {
        MCELModifyEntityProperties.addMobEffects(mob, deathEventsData.getDeathEventsTimerTicks(), 1, getEnabledEffects(deathEventsData));
    }

    public static void onEnemyMobSpawn(DeathEventsData deathEventsData, Mob mob) {
        if (!deathEventsData.isAllDeathEventsEnabled() || !deathEventsData.isEnemyEffectsEnabled() || !deathEventsData.isDeathEventsActive()) return;

        EnemyEffectsOnPlayerDeath.addMobEffects(mob, deathEventsData);

        Deadlock.LOGGER.info("Generated enemy entity, purgatory and effects time = {}", deathEventsData.getDeathEventsTimerTicks());
    }

    public static void init(DeathEventsData deathEventsData, MinecraftServer server) {
        if (!deathEventsData.isEnemyEffectsEnabled()) return;

        List<Holder<MobEffect>> enabledEffects = getEnabledEffects(deathEventsData);

        if (enabledEffects.isEmpty()) return;

        int entitiesCount = 0;

        for (ServerLevel level : server.getAllLevels())
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof Mob mob) || !(mob instanceof Enemy)) continue;

                addMobEffects(mob, deathEventsData);
                entitiesCount++;
            }

        Deadlock.LOGGER.info("Number of entities affected by player death effects = {}", entitiesCount);
    }

    private EnemyEffectsOnPlayerDeath() {}
}
