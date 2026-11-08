package com.adrianka.deadlock.world;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.mcenhancedlib.world.MCELModifyWorldServer;
import net.minecraft.server.MinecraftServer;

public class DeathWeather {
    public static void deathWeather(MinecraftServer server, int purgatoryTime, boolean raining, boolean thundering) {
        MCELModifyWorldServer.setWeather(server, purgatoryTime, raining, thundering);

        Deadlock.LOGGER.info("The death weather has been activated. Duration {}", purgatoryTime);
    }

    public static void init(DeathEventsData deathEventsData, MinecraftServer server) {
        if (deathEventsData.isDeathRainEnabled()) DeathWeather.deathWeather(server, deathEventsData.getDeathEventsTimerTicks(), true, true);
    }

}
