package com.adrianka.deadlock.network.purgatory;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class PurgatoryEventsDataSync {
    private boolean purgatoryEventsEnable;
    private int purgatoryEventsDuration;
    private boolean purgatoryEventsStackEnable;

    private boolean purgatoryRainEnable;
    private boolean purgatoryRainSkip;

    private boolean enemyBuffsEnable;
    private int enemyBuffAmplifier;
    private boolean enemySpeedEnable;
    private boolean enemyStrengthEnable;
    private boolean enemyResistanceEnable;

    private boolean reducePlayerMaxHealthEnabled;
    private double healthReducerValue;
    private double minHealthValue;

    public static final StreamCodec<RegistryFriendlyByteBuf, PurgatoryEventsDataSync> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isPurgatoryEventsEnable,
                    ByteBufCodecs.VAR_INT, PurgatoryEventsDataSync::getPurgatoryEventsDuration,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isPurgatoryEventsStackEnable,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isPurgatoryRainEnable,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isPurgatoryRainSkip,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isEnemyBuffsEnable,
                    ByteBufCodecs.VAR_INT, PurgatoryEventsDataSync::getEnemyBuffAmplifier,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isEnemySpeedEnable,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isEnemyStrengthEnable,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isEnemyResistanceEnable,
                    ByteBufCodecs.BOOL, PurgatoryEventsDataSync::isReducePlayerMaxHealthEnabled,
                    ByteBufCodecs.DOUBLE, PurgatoryEventsDataSync::getHealthReducerValue,
                    ByteBufCodecs.DOUBLE, PurgatoryEventsDataSync::getMinHealthValue,
                    PurgatoryEventsDataSync::new
            );

    public PurgatoryEventsDataSync(
            boolean purgatoryEventsEnabled,
            int purgatoryEventsDuration,
            boolean purgatoryEventsStackEnabled,
            boolean purgatoryRainEnabled,
            boolean purgatoryRainSkip,
            boolean enemyBuffEffectsEnabled,
            int enemyBuffAmplifier,
            boolean enemySpeedEnabled,
            boolean enemyStrengthEnabled,
            boolean enemyResistanceEnabled,
            boolean reducePlayerMaxHealthEnabled,
            double healthReducerValue,
            double minHealthValue
    ) {
        this.purgatoryEventsEnable = purgatoryEventsEnabled;
        this.purgatoryEventsDuration = purgatoryEventsDuration;
        this.purgatoryEventsStackEnable = purgatoryEventsStackEnabled;
        this.purgatoryRainEnable = purgatoryRainEnabled;
        this.purgatoryRainSkip = purgatoryRainSkip;
        this.enemyBuffsEnable = enemyBuffEffectsEnabled;
        this.enemyBuffAmplifier = enemyBuffAmplifier;
        this.enemySpeedEnable = enemySpeedEnabled;
        this.enemyStrengthEnable = enemyStrengthEnabled;
        this.enemyResistanceEnable = enemyResistanceEnabled;
        this.reducePlayerMaxHealthEnabled = reducePlayerMaxHealthEnabled;
        this.healthReducerValue = healthReducerValue;
        this.minHealthValue = minHealthValue;
    }

    public boolean isPurgatoryEventsEnable() { return purgatoryEventsEnable; }
    public void setPurgatoryEventsEnable(boolean isEnable) { purgatoryEventsEnable = isEnable; }

    public int getPurgatoryEventsDuration() { return purgatoryEventsDuration; }
    public void setPurgatoryEventsDuration(int duration) { purgatoryEventsDuration = duration; }

    public boolean isPurgatoryEventsStackEnable() { return purgatoryEventsStackEnable; }
    public void setPurgatoryEventsStackEnable(boolean isEnable) { purgatoryEventsStackEnable = isEnable; }

    public boolean isPurgatoryRainEnable() { return purgatoryRainEnable; }
    public void setPurgatoryRainEnable(boolean isEnable) { purgatoryRainEnable = isEnable; }

    public boolean isPurgatoryRainSkip() { return purgatoryRainSkip; }
    public void setPurgatoryRainSkip(boolean isEnable) { purgatoryRainSkip = isEnable; }

    public boolean isEnemyBuffsEnable() { return enemyBuffsEnable; }
    public void setEnemyBuffsEnable(boolean isEnable) { enemyBuffsEnable = isEnable; }

    public int getEnemyBuffAmplifier() { return enemyBuffAmplifier; }
    public void setEnemyBuffAmplifier(int amplifier) { enemyBuffAmplifier = amplifier; }

    public boolean isEnemySpeedEnable() { return enemySpeedEnable; }
    public void setEnemySpeedEnable(boolean isEnable) { enemySpeedEnable = isEnable; }

    public boolean isEnemyStrengthEnable() { return enemyStrengthEnable; }
    public void setEnemyStrengthEnable(boolean isEnable) { enemyStrengthEnable = isEnable; }

    public boolean isEnemyResistanceEnable() { return enemyResistanceEnable; }
    public void setEnemyResistanceEnable(boolean isEnable) { enemyResistanceEnable = isEnable; }

    public boolean isReducePlayerMaxHealthEnabled() { return reducePlayerMaxHealthEnabled; }
    public void setReducePlayerMaxHealthEnabled(boolean isEnable) { reducePlayerMaxHealthEnabled = isEnable; }

    public double getHealthReducerValue() { return healthReducerValue; }
    public void setHealthReducerValue(double value) { healthReducerValue = value; }

    public double getMinHealthValue() { return minHealthValue; }
    public void setMinHealthValue(double value) { minHealthValue = value; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PurgatoryEventsDataSync other)) return false;

        return purgatoryEventsEnable == other.purgatoryEventsEnable
                && purgatoryEventsDuration == other.purgatoryEventsDuration
                && purgatoryEventsStackEnable == other.purgatoryEventsStackEnable
                && purgatoryRainEnable == other.purgatoryRainEnable
                && purgatoryRainSkip == other.purgatoryRainSkip
                && enemyBuffsEnable == other.enemyBuffsEnable
                && enemyBuffAmplifier == other.enemyBuffAmplifier
                && enemySpeedEnable == other.enemySpeedEnable
                && enemyStrengthEnable == other.enemyStrengthEnable
                && enemyResistanceEnable == other.enemyResistanceEnable
                && reducePlayerMaxHealthEnabled == other.reducePlayerMaxHealthEnabled
                && Double.compare(healthReducerValue, other.healthReducerValue) == 0
                && Double.compare(minHealthValue, other.minHealthValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                purgatoryEventsEnable,
                purgatoryEventsDuration,
                purgatoryEventsStackEnable,
                purgatoryRainEnable,
                purgatoryRainSkip,
                enemyBuffsEnable,
                enemyBuffAmplifier,
                enemySpeedEnable,
                enemyStrengthEnable,
                enemyResistanceEnable,
                reducePlayerMaxHealthEnabled,
                healthReducerValue,
                minHealthValue
        );
    }
}