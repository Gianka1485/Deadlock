package com.adrianka.deadlock.data;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.server.network.PurgatorySyncPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class PurgatoryTimerTicks {
    public static void reduceEventTimerTicks(DeathEventsData deathEventsData) {
        int ticks = deathEventsData.getDeathEventsTimerTicks();

        if (ticks == 0) return;

        deathEventsData.setDeathEventsTimerTicks(--ticks);

        if (ticks % 20 == 0) PacketDistributor.sendToAllPlayers(new PurgatorySyncPayload(ticks));

        if (!(ticks % 200 == 0)) return;

        if (ticks == 0) deathEventsData.activeDeathEvents(false);

        deathEventsData.setDirty();

        Deadlock.LOGGER.debug("Saved purgatory ticks: {}", ticks);
    }

    public static void saveData(DeathEventsData deathEventsData) {
        int ticks = deathEventsData.getDeathEventsTimerTicks();

        Deadlock.LOGGER.info("LEVEL SAVE: SavedData counter = {}, static counter = {}", deathEventsData.getDeathEventsTimerTicks(), ticks);

        deathEventsData.setDeathEventsTimerTicks(ticks);
    }

    public static void init(DeathEventsData deathEventsData) {
        int ticks = deathEventsData.getDeathEventsTimerTicks();
        int eventDuration = deathEventsData.getDeathEventsDuration();

        deathEventsData.setDeathEventsTimerTicks((!deathEventsData.isDeathEventsStackEnabled() || ticks == 0) ? eventDuration : ticks + eventDuration);

        Deadlock.LOGGER.debug("Purgatory time ticks = {}", ticks);
    }
}