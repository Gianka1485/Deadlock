package com.adrianka.deadlock.data;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.mcenhancedlib.data.MCELDataState;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class PurgatoryEventsData extends SavedData {
    private static final String PATH = "death_events_state";

    public static final SavedDataType<PurgatoryEventsData> SAVED_DATA_TYPE =
            new MCELDataState<>(
                    Deadlock.MODID,
                    PATH,
                    PurgatoryEventsData::new,
                    instance ->instance.group(
                            Codec.BOOL.fieldOf("purgatory_events_enabled").forGetter(PurgatoryEventsData::isPurgatoryEventsEnabled),
                            Codec.BOOL.fieldOf("purgatory_events_active").forGetter(PurgatoryEventsData::isPurgatoryEventsActive),
                            Codec.INT.fieldOf("purgatory_events_duration").forGetter(PurgatoryEventsData::getPurgatoryEventsDuration),
                            Codec.INT.fieldOf("purgatory_events_counter").forGetter(PurgatoryEventsData::getPurgatoryEventsTimerTicks),
                            Codec.BOOL.fieldOf("purgatory_events_stack_enabled").forGetter(PurgatoryEventsData::isPurgatoryEventsStackEnabled),
                            Codec.BOOL.fieldOf("purgatory_rain_enabled").forGetter(PurgatoryEventsData::isPurgatoryRainEnabled),
                            Codec.BOOL.fieldOf("enemy_effects_enabled").forGetter(PurgatoryEventsData::isEnemyEffectsEnabled),
                            Codec.INT.fieldOf("enemy_buff_amplifier").forGetter(PurgatoryEventsData::getEnemyBuffAmplifier),
                            Codec.BOOL.fieldOf("enemy_speed_enabled").forGetter(PurgatoryEventsData::isEnemySpeedEnabled),
                            Codec.BOOL.fieldOf("enemy_strength_enabled").forGetter(PurgatoryEventsData::isEnemyStrengthEnabled),
                            Codec.BOOL.fieldOf("enemy_resistance_enabled").forGetter(PurgatoryEventsData::isEnemyResistanceEnabled),
                            Codec.BOOL.fieldOf("reduce_player_max_health").forGetter(PurgatoryEventsData::isReducePlayerMaxHealthEnabled),
                            Codec.DOUBLE.fieldOf("health_reducer_value").forGetter(PurgatoryEventsData::getHealthReducerValue),
                            Codec.DOUBLE.fieldOf("min_health_value").forGetter(PurgatoryEventsData::getMinHealthValue)
                    ).apply(instance, PurgatoryEventsData::new)
            ).savedData();

    private boolean purgatoryEventsEnabled;
    private boolean purgatoryEventsActive;
    private int purgatoryEventsDuration;
    private int purgatoryEventsTimerTicks;
    private boolean purgatoryEventsStackEnabled;
    private boolean purgatoryRainEnabled;

    private boolean enemyEffectsEnabled;
    private int enemyBuffAmplifier;
    private boolean enemySpeedEnabled;
    private boolean enemyStrengthEnabled;
    private boolean enemyResistanceEnabled;

    private boolean reducePlayerMaxHealthEnabled;
    private double healthReducerValue;
    private double minHealthValue;

    public PurgatoryEventsData() {
        this(
                true,
                false,
                3600 * 20,
                0,
                true,
                true,
                true,
                0,
                true,
                true,
                true,
                true,
                -2.0,
                -18
        );
    }

    public PurgatoryEventsData(
            boolean purgatoryEventsEnabled,
            boolean purgatoryEventsActive,
            int purgatoryEventsDuration,
            int purgatoryEventsTimerTicks,
            boolean purgatoryEventsStackEnabled,
            boolean purgatoryRainEnabled,
            boolean enemyEffectsEnabled,
            int enemyBuffAmplifier,
            boolean enemySpeedEnabled,
            boolean enemyStrengthEnabled,
            boolean enemyResistanceEnabled,
            boolean reducePlayerMaxHealthEnabled,
            double healthReducerValue,
            double minHealthValue
    ) {
        this.purgatoryEventsEnabled = purgatoryEventsEnabled;
        this.purgatoryEventsActive = purgatoryEventsActive;
        this.purgatoryEventsDuration = purgatoryEventsDuration;
        this.purgatoryEventsTimerTicks = purgatoryEventsTimerTicks;
        this.purgatoryEventsStackEnabled = purgatoryEventsStackEnabled;
        this.purgatoryRainEnabled = purgatoryRainEnabled;

        this.enemyEffectsEnabled = enemyEffectsEnabled;
        this.enemyBuffAmplifier = enemyBuffAmplifier;
        this.enemySpeedEnabled = enemySpeedEnabled;
        this.enemyStrengthEnabled = enemyStrengthEnabled;
        this.enemyResistanceEnabled = enemyResistanceEnabled;

        this.reducePlayerMaxHealthEnabled = reducePlayerMaxHealthEnabled;
        this.healthReducerValue = healthReducerValue;
        this.minHealthValue = minHealthValue;
    }

    public static PurgatoryEventsData getInstance(MinecraftServer server) { return server.getDataStorage().computeIfAbsent(SAVED_DATA_TYPE); }

    public boolean isPurgatoryEventsEnabled() { return purgatoryEventsEnabled;}
    public void setPurgatoryEventsEnabled(boolean enabled) {
        purgatoryEventsEnabled = enabled;
        setDirty();
    }

    public boolean isPurgatoryEventsActive() { return purgatoryEventsActive;}
    public void setPurgatoryEventsActive(boolean active) {
        purgatoryEventsActive = active;
        setDirty();
    }

    public int getPurgatoryEventsDuration() { return purgatoryEventsDuration; }
    public void setPurgatoryEventsDuration(int totalTicks) {
        purgatoryEventsDuration = totalTicks;
        setDirty();
    }

    public int getPurgatoryEventsTimerTicks() { return purgatoryEventsTimerTicks; }
    public void setPurgatoryEventsTimerTicks(int counter) { purgatoryEventsTimerTicks = counter; }

    public boolean isPurgatoryEventsStackEnabled() { return purgatoryEventsStackEnabled; }
    public void setPurgatoryEventsStackEnabled(boolean enabled) {
        purgatoryEventsStackEnabled = enabled;
        setDirty();
    }

    public boolean isPurgatoryRainEnabled() { return purgatoryRainEnabled;}
    public void setPurgatoryRainEnabled(boolean enabled) {
        purgatoryRainEnabled = enabled;
        setDirty();
    }

    public boolean isEnemyEffectsEnabled() { return enemyEffectsEnabled; }
    public void setEnemyEffectsEnabled(boolean enabled) {
        enemyEffectsEnabled = enabled;
        setDirty();
    }

    public int getEnemyBuffAmplifier() { return enemyBuffAmplifier; }
    public void setEnemyBuffAmplifier(int amplifier) {
        enemyBuffAmplifier = amplifier;
        setDirty();
    }

    public boolean isEnemySpeedEnabled() { return enemySpeedEnabled; }
    public void setEnemySpeedEnabled(boolean enabled) {
        enemySpeedEnabled = enabled;
        setDirty();
    }

    public boolean isEnemyStrengthEnabled() { return enemyStrengthEnabled; }
    public void setEnemyStrengthEnabled(boolean enabled) {
        enemyStrengthEnabled = enabled;
        setDirty();
    }

    public boolean isEnemyResistanceEnabled() { return enemyResistanceEnabled; }
    public void setEnemyResistanceEnabled(boolean enabled) {
        enemyResistanceEnabled = enabled;
        setDirty();
    }

    public boolean isReducePlayerMaxHealthEnabled() { return reducePlayerMaxHealthEnabled; }
    public void setReducePlayerMaxHealthEnabled(boolean enabled) {
        reducePlayerMaxHealthEnabled = enabled;
        setDirty();
    }

    public double getHealthReducerValue() { return healthReducerValue; }
    public void setHealthReducerValue(double reducerValue) {
        healthReducerValue = reducerValue;
        setDirty();
    }

    public double getMinHealthValue() { return minHealthValue; }
    public void setMinHealthValue(double minValue) {
        minHealthValue = minValue;
        setDirty();
    }
}