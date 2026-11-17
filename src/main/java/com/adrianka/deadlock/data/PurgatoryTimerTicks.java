package com.adrianka.deadlock.data;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.network.purgatory.PurgatoryTimerTicksSyncPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class PurgatoryTimerTicks {
    public static void init(PurgatoryEventsData purgatoryEventsData) {
        int ticks = purgatoryEventsData.getPurgatoryEventsTimerTicks();
        int eventDuration = purgatoryEventsData.getPurgatoryEventsDuration();

        purgatoryEventsData.setPurgatoryEventsTimerTicks((!purgatoryEventsData.isPurgatoryEventsStackEnabled() || ticks == 0) ? eventDuration : ticks + eventDuration);

        Deadlock.LOGGER.debug("Purgatory time ticks = {}", ticks);
    }

    public static void reduceEventTimerTicks(PurgatoryEventsData purgatoryEventsData) {
        int ticks = purgatoryEventsData.getPurgatoryEventsTimerTicks();

        if (ticks == 0) return;

        purgatoryEventsData.setPurgatoryEventsTimerTicks(--ticks);

        if (ticks % 20 == 0) PacketDistributor.sendToAllPlayers(new PurgatoryTimerTicksSyncPayload(ticks));

        if (!(ticks % 200 == 0)) return;

        if (ticks == 0) purgatoryEventsData.setPurgatoryEventsActive(false);

        purgatoryEventsData.setDirty();

        Deadlock.LOGGER.debug("Saved purgatory ticks: {}", ticks);
    }

    public static void saveData(PurgatoryEventsData purgatoryEventsData) {
        int ticks = purgatoryEventsData.getPurgatoryEventsTimerTicks();

        Deadlock.LOGGER.info("LEVEL SAVE: SavedData counter = {}, static counter = {}", purgatoryEventsData.getPurgatoryEventsTimerTicks(), ticks);

        purgatoryEventsData.setPurgatoryEventsTimerTicks(ticks);
    }
}