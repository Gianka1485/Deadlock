package com.adrianka.deadlock.data;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.mcenhancedlib.data.MCELDataState;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class DeathEventsData extends SavedData {
    private static final String PATH = "death_events_state";

    public static final SavedDataType<DeathEventsData> SAVED_DATA_TYPE =
            new MCELDataState<>(
                    Deadlock.MODID,
                    PATH,
                    DeathEventsData::new,
                    instance -> instance.group(
                            Codec.BOOL.fieldOf("all_death_events_enabled").forGetter(DeathEventsData::isAllDeathEventsEnabled),
                            Codec.BOOL.fieldOf("death_events_active").forGetter(DeathEventsData::isDeathEventsActive),
                            Codec.INT.fieldOf("death_events_duration").forGetter(DeathEventsData::getDeathEventsDuration),
                            Codec.INT.fieldOf("death_events_counter").forGetter(DeathEventsData::getDeathEventsCounter),
                            Codec.BOOL.fieldOf("death_events_stack_enabled").forGetter(DeathEventsData::isDeathEventsStackEnabled),
                            Codec.BOOL.fieldOf("death_rain_enabled").forGetter(DeathEventsData::isDeathRainEnabled),
                            Codec.BOOL.fieldOf("enemy_effects_enabled").forGetter(DeathEventsData::isEnemyEffectsEnabled),
                            Codec.BOOL.fieldOf("enemy_speed_enabled").forGetter(DeathEventsData::isEnemySpeedEnabled),
                            Codec.BOOL.fieldOf("enemy_strength_enabled").forGetter(DeathEventsData::isEnemyStrengthEnabled),
                            Codec.BOOL.fieldOf("enemy_resistance_enabled").forGetter(DeathEventsData::isEnemyResistanceEnabled),
                            Codec.BOOL.fieldOf("reduce_player_max_health").forGetter(DeathEventsData::isReducePlayerMaxHealthEnabled)
                    ).apply(instance, DeathEventsData::new)
            ).savedData();

    public static DeathEventsData getInstance(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(SAVED_DATA_TYPE);
    }

    public DeathEventsData() {
        this(
                true,
                false,
                3600 * 20,
                0,
                true,
                true,
                true,
                true,
                true,
                true,
                true
        );
    }

    public DeathEventsData(
            boolean isAllDeathEventsEnabled,
            boolean isDeathEventsActive,
            int ticksTime,
            int timer,
            boolean isDeathEventsStackEnabled,
            boolean isDeathRainEnabled,
            boolean isEnemyBuffEffectsEnabled,
            boolean isEnemySpeedEnabled,
            boolean isEnemyStrengthEnabled,
            boolean isEnemyResistanceEnabled,
            boolean isReducePlayerMaxHealthEnabled
    ) {
        allDeathEventsEnabled = isAllDeathEventsEnabled;
        deathEventsActive = isDeathEventsActive;
        deathEventsDuration = ticksTime;
        deathEventsCounter = timer;
        deathEventsStackEnabled = isDeathEventsStackEnabled;
        deathRainEnabled = isDeathRainEnabled;
        enemyEffectsEnabled = isEnemyBuffEffectsEnabled;
        enemySpeedEnabled = isEnemySpeedEnabled;
        enemyStrengthEnabled = isEnemyStrengthEnabled;
        enemyResistanceEnabled = isEnemyResistanceEnabled;
        reducePlayerMaxHealthEnabled = isReducePlayerMaxHealthEnabled;
    }

    private boolean allDeathEventsEnabled;
    public boolean isAllDeathEventsEnabled() { return allDeathEventsEnabled; }
    public void setAllDeathEventsEnabled(boolean isEnabled) {
        allDeathEventsEnabled = isEnabled;
        setDirty();
    }

    private boolean deathEventsActive;
    public boolean isDeathEventsActive() { return deathEventsActive; }
    public void activeDeathEvents(boolean isActive) {
        deathEventsActive = isActive;
        setDirty();
    }

    private int deathEventsDuration;
    public int getDeathEventsDuration() { return deathEventsDuration; }
    public void setDeathEventsDuration(int totalTicks) {
        deathEventsDuration = totalTicks;
        setDirty();
    }

    private int deathEventsCounter = getDeathEventsDuration();
    public int getDeathEventsCounter() { return deathEventsCounter; }
    public void setDeathEventsCounter(int counter) {
        deathEventsCounter = counter;
        setDirty();
    }

    private boolean deathEventsStackEnabled;
    public boolean isDeathEventsStackEnabled() { return deathEventsStackEnabled; }
    public void setDeathEventsStackEnabled(boolean isEnabled) {
        deathEventsStackEnabled = isEnabled;
        setDirty();
    }

    private boolean deathRainEnabled;
    public boolean isDeathRainEnabled() { return deathRainEnabled; }
    public void setDeathRainEnabled(boolean isEnabled) {
        deathRainEnabled = isEnabled;
        setDirty();
    }

    private boolean enemyEffectsEnabled;
    public boolean isEnemyEffectsEnabled() { return enemyEffectsEnabled; }
    public void setEnemyEffectsEnabled(boolean isEnabled) {
        enemyEffectsEnabled = isEnabled;
        setDirty();
    }

    private boolean enemySpeedEnabled;
    public boolean isEnemySpeedEnabled() { return enemySpeedEnabled; }
    public void setEnemySpeedEnabled(boolean isEnabled) {
        enemySpeedEnabled = isEnabled;
        setDirty();
    }

    private boolean enemyStrengthEnabled;
    public boolean isEnemyStrengthEnabled() { return enemyStrengthEnabled; }
    public void setEnemyStrengthEnabled(boolean isEnabled) {
        enemyStrengthEnabled = isEnabled;
        setDirty();
    }

    private boolean enemyResistanceEnabled;
    public boolean isEnemyResistanceEnabled() { return enemyResistanceEnabled; }
    public void setEnemyResistanceEnabled(boolean isEnabled) {
        enemyResistanceEnabled = isEnabled;
        setDirty();
    }

    private boolean reducePlayerMaxHealthEnabled;
    public boolean isReducePlayerMaxHealthEnabled() { return reducePlayerMaxHealthEnabled; }
    public void setReducePlayerMaxHealthEnabled(boolean isEnabled) {
        reducePlayerMaxHealthEnabled = isEnabled;
        setDirty();
    }


}
