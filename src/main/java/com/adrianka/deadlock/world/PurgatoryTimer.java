package com.adrianka.deadlock.world;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import net.minecraft.server.MinecraftServer;

public class PurgatoryTimer {
    private static int purgatoryTicks = 0;

    public static int getPurgatoryTicks() { return purgatoryTicks; }

    public static void setPurgatoryTicks(DeathEventsData deathEventsData) {
        purgatoryTicks = deathEventsData.getDeathEventsCounter();

        Deadlock.LOGGER.debug("Purgatory Ticks = {}", getPurgatoryTicks());
    }

    public static void reducePurgatoryTicks(DeathEventsData deathEventsData) {
        if (purgatoryTicks > 0) purgatoryTicks--;
        if (purgatoryTicks % 200 == 0) {
            deathEventsData.setDeathEventsCounter(purgatoryTicks);

            Deadlock.LOGGER.debug(
                    "Saved purgatory ticks: {}",
                    purgatoryTicks
            );
        }
    }

    public static void init(DeathEventsData deathEventsData, MinecraftServer server) {
        if (!deathEventsData.isDeathEventsStackEnabled() || getPurgatoryTicks() == 0)
            deathEventsData.setDeathEventsCounter(deathEventsData.getDeathEventsDuration());
        else
            deathEventsData.setDeathEventsCounter(
                    getPurgatoryTicks() + deathEventsData.getDeathEventsDuration()
            );

        Deadlock.LOGGER.debug(
                "Purgatory time = {}, deathEventsData.getDeathEventsCounter = {}, weather = {}",
                PurgatoryTimer.getPurgatoryTicks(),
                deathEventsData.getDeathEventsCounter() - deathEventsData.getDeathEventsDuration(),
                server.getWeatherData().getRainTime()
        );

        setPurgatoryTicks(deathEventsData);
    }

    private PurgatoryTimer() {}
}
