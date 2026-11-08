package com.adrianka.deadlock.client.gui.purgatory;

public final class PurgatoryClientData {

    private static int purgatoryTicks = 0;

    public static int getPurgatoryTicks() {
        return purgatoryTicks;
    }

    public static void setPurgatoryTicks(int ticks) {
        purgatoryTicks = Math.max(0, ticks);
    }

    public static void reset() { purgatoryTicks = 0; }

    private PurgatoryClientData() {}
}