package com.adrianka.deadlock.event;

import com.adrianka.deadlock.data.PurgatoryEventsData;
import com.adrianka.deadlock.data.PurgatoryTimerTicks;
import com.adrianka.deadlock.entity.PurgatoryEnemyEffects;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataPayload;
import com.adrianka.deadlock.player.ModifyPlayerMaxHealth;
import com.adrianka.deadlock.network.purgatory.PurgatoryTimerTicksSyncPayload;
import com.adrianka.deadlock.world.PurgatoryWeather;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class DeadlockEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(player.level().getServer());

        PacketDistributor.sendToPlayer(player, new PurgatoryTimerTicksSyncPayload(purgatoryEventsData.getPurgatoryEventsTimerTicks()));

        PacketDistributor.sendToPlayer(player, PurgatoryEventsDataPayload.from(purgatoryEventsData));
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent deathEvent) {
        if (!(deathEvent.getEntity() instanceof ServerPlayer player)) return;

        MinecraftServer server = player.level().getServer();

        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(server);

        if (!purgatoryEventsData.isPurgatoryEventsEnabled()) return;

        purgatoryEventsData.setPurgatoryEventsActive(true);

        PurgatoryTimerTicks.init(purgatoryEventsData);

        PurgatoryWeather.init(purgatoryEventsData, server);

        PurgatoryEnemyEffects.init(purgatoryEventsData, server);

        ModifyPlayerMaxHealth.reduceMaxHealthAttachment(player, purgatoryEventsData);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ModifyPlayerMaxHealth.reduceMaxHealth(player);
    }

    @SubscribeEvent
    public static void onEnemyMobSpawn(FinalizeSpawnEvent event) {
        if (!(event.getEntity() instanceof Enemy)) return;

        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(event.getLevel().getServer());

        PurgatoryEnemyEffects.onEnemyMobSpawn(purgatoryEventsData, event.getEntity());
}

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(event.getServer());

        PurgatoryTimerTicks.reduceEventTimerTicks(purgatoryEventsData);
    }

    /*@SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();

        DeathEventsData deathEventsData = DeathEventsData.getInstance(server);
    }*/

    @SubscribeEvent
    public static void onLevelSave(LevelEvent.Save event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        MinecraftServer server = level.getServer();

        if (level != server.overworld()) return;

        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(level.getServer());

        PurgatoryTimerTicks.saveData(purgatoryEventsData);
    }
}
