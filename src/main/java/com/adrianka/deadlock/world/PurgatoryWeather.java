package com.adrianka.deadlock.world;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.PurgatoryEventsData;
import com.adrianka.mcenhancedlib.server.world.MCELModifyWorldServer;
import net.minecraft.server.MinecraftServer;

public class PurgatoryWeather {
    public static void init(PurgatoryEventsData purgatoryEventsData, MinecraftServer server) {
        if (purgatoryEventsData.isPurgatoryRainEnabled())
            PurgatoryWeather.deathWeather(server, purgatoryEventsData.getPurgatoryEventsTimerTicks(), true, true);
    }

    public static void deathWeather(MinecraftServer server, int purgatoryTime, boolean raining, boolean thundering) {
        MCELModifyWorldServer.setWeather(server, purgatoryTime, raining, thundering);

        Deadlock.LOGGER.info("The death weather has been activated. Duration {}", purgatoryTime);
    }
}
