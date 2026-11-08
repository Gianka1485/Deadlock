package com.adrianka.deadlock.entity;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.deadlock.world.PurgatoryTimer;
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

    private static final List<Holder<MobEffect>> ENABLED_EFFECTS = new ArrayList<>();

    public static List<Holder<MobEffect>> getEnabledEffects() { return ENABLED_EFFECTS; }

    public static void setEnabledEffects(DeathEventsData deathEventsData) {
        if (!ENABLED_EFFECTS.isEmpty()) return;

        if (deathEventsData.isEnemySpeedEnabled()) ENABLED_EFFECTS.add(MobEffects.SPEED);
        if (deathEventsData.isEnemyStrengthEnabled()) ENABLED_EFFECTS.add(MobEffects.STRENGTH);
        if (deathEventsData.isEnemyResistanceEnabled()) ENABLED_EFFECTS.add(MobEffects.RESISTANCE);
    }

    public static void resetEnabledEffects() { ENABLED_EFFECTS.clear(); }

    public static void addMobEffects(Mob mob) {
        MCELModifyEntityProperties.addMobEffects(
                mob,
                PurgatoryTimer.getPurgatoryTicks(),
                1,
                getEnabledEffects()
        );
    }

    public static void onEnemyMobSpawn(DeathEventsData deathEventsData, Mob mob) {
        if (
                !deathEventsData.isAllDeathEventsEnabled() ||
                !deathEventsData.isEnemyEffectsEnabled() ||
                !deathEventsData.isDeathEventsActive()
        ) return;

        EnemyEffectsOnPlayerDeath.addMobEffects(mob);

        Deadlock.LOGGER.info("Generated enemy entity, purgatory and effects time = {}", PurgatoryTimer.getPurgatoryTicks());
    }

    public static void init(DeathEventsData deathEventsData, MinecraftServer server) {
        if (!deathEventsData.isEnemyEffectsEnabled()) return;

        int entitiesCount = 0;

        EnemyEffectsOnPlayerDeath.setEnabledEffects(deathEventsData);

        for (ServerLevel level : server.getAllLevels())
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof Mob mob) || !(mob instanceof Enemy)) continue;

                addMobEffects(mob);
                entitiesCount++;
            }

        Deadlock.LOGGER.info("Number of entities affected by player death effects = {}", entitiesCount);
    }

    private EnemyEffectsOnPlayerDeath() {}
}
