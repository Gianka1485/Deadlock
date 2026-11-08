package com.adrianka.deadlock.world;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.mcenhancedlib.world.MCELModifyWorldServer;
import net.minecraft.server.MinecraftServer;

public class DeathWeather {
    private static int deathWeatherTotalTime = 0;

    public static int getDeathWeatherTotalTime() { return deathWeatherTotalTime; }

    public static void deathWeather(MinecraftServer server, int purgatoryTime, boolean raining, boolean thundering) {
        deathWeatherTotalTime = purgatoryTime;

        MCELModifyWorldServer.setWeather(server, getDeathWeatherTotalTime(), raining, thundering);

        Deadlock.LOGGER.info(
                "The death weather has been activated. Duration {}",
                getDeathWeatherTotalTime()
        );
    }

    public static void init(DeathEventsData deathEventsData, MinecraftServer server) {
        if (deathEventsData.isDeathRainEnabled())
            DeathWeather.deathWeather(server, PurgatoryTimer.getPurgatoryTicks(), true, true);
    }

    private DeathWeather() {}
}
