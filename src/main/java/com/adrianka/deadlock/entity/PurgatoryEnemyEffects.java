package com.adrianka.deadlock.entity;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.PurgatoryEventsData;
import com.adrianka.mcenhancedlib.server.entity.MCELModifyEntityProperties;
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

public final class PurgatoryEnemyEffects {
    private static List<Holder<MobEffect>> getEnabledEffects(PurgatoryEventsData purgatoryEventsData) {
        List<Holder<MobEffect>> enabledEffects = new ArrayList<>();

        if (purgatoryEventsData.isEnemySpeedEnabled()) enabledEffects.add(MobEffects.SPEED);
        if (purgatoryEventsData.isEnemyStrengthEnabled()) enabledEffects.add(MobEffects.STRENGTH);
        if (purgatoryEventsData.isEnemyResistanceEnabled()) enabledEffects.add(MobEffects.RESISTANCE);

        return enabledEffects;
    }

    public static void addMobEffects(Mob mob, PurgatoryEventsData purgatoryEventsData) {
        MCELModifyEntityProperties.addMobEffects(
                mob,
                purgatoryEventsData.getPurgatoryEventsTimerTicks(),
                purgatoryEventsData.getEnemyBuffAmplifier(),
                getEnabledEffects(purgatoryEventsData)
        );
    }

    public static void onEnemyMobSpawn(PurgatoryEventsData purgatoryEventsData, Mob mob) {
        if (!purgatoryEventsData.isPurgatoryEventsEnabled() || !purgatoryEventsData.isEnemyEffectsEnabled() || !purgatoryEventsData.isPurgatoryEventsActive()) return;

        PurgatoryEnemyEffects.addMobEffects(mob, purgatoryEventsData);

        Deadlock.LOGGER.info("Generated enemy entity, purgatory and effects time = {}", purgatoryEventsData.getPurgatoryEventsTimerTicks());
    }

    public static void init(PurgatoryEventsData purgatoryEventsData, MinecraftServer server) {
        if (!purgatoryEventsData.isEnemyEffectsEnabled()) return;

        List<Holder<MobEffect>> enabledEffects = getEnabledEffects(purgatoryEventsData);

        if (enabledEffects.isEmpty()) return;

        int entitiesCount = 0;

        for (ServerLevel level : server.getAllLevels())
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof Mob mob) || !(mob instanceof Enemy)) continue;

                addMobEffects(mob, purgatoryEventsData);
                entitiesCount++;
            }

        Deadlock.LOGGER.info("Number of entities affected by player death effects = {}", entitiesCount);
    }

    private PurgatoryEnemyEffects() {}
}
