package com.adrianka.deadlock.client.data;

import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataSync;

public final class PurgatoryClientData {
    private static int purgatoryTicks = 0;

    private static PurgatoryEventsDataSync current;

    private PurgatoryClientData() {}

    public static int getPurgatoryTicks() { return purgatoryTicks; }
    public static void setPurgatoryTicks(int ticks) { purgatoryTicks = Math.max(0, ticks); }

    public static PurgatoryEventsDataSync getCurrent() { return current; }
    public static void applySync(PurgatoryEventsDataSync dataSync) {
        current = new PurgatoryEventsDataSync(
                dataSync.isPurgatoryEventsEnable(),
                dataSync.getPurgatoryEventsDuration(),
                dataSync.isPurgatoryEventsStackEnable(),
                dataSync.isPurgatoryRainEnable(),
                dataSync.isEnemyBuffsEnable(),
                dataSync.getEnemyBuffAmplifier(),
                dataSync.isEnemySpeedEnable(),
                dataSync.isEnemyStrengthEnable(),
                dataSync.isEnemyResistanceEnable(),
                dataSync.isReducePlayerMaxHealthEnabled(),
                dataSync.getHealthReducerValue(),
                dataSync.getMinHealthValue()
        );
    }

    public static void clear() {
        purgatoryTicks = 0;
        current = null;
    }
}