package com.adrianka.deadlock.event;

import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.deadlock.data.PurgatoryTimerTicks;
import com.adrianka.deadlock.entity.EnemyEffectsOnPlayerDeath;
import com.adrianka.deadlock.player.ModifyPlayerMaxHealth;
import com.adrianka.deadlock.server.network.PurgatorySyncPayload;
import com.adrianka.deadlock.world.DeathWeather;
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

        DeathEventsData deathEventsData = DeathEventsData.getInstance(player.level().getServer());

        PacketDistributor.sendToPlayer(player, new PurgatorySyncPayload(deathEventsData.getDeathEventsTimerTicks()));
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent deathEvent) {
        if (!(deathEvent.getEntity() instanceof ServerPlayer player)) return;

        MinecraftServer server = player.level().getServer();

        DeathEventsData deathEventsData = DeathEventsData.getInstance(server);

        if (!deathEventsData.isAllDeathEventsEnabled()) return;

        deathEventsData.activeDeathEvents(true);

        PurgatoryTimerTicks.init(deathEventsData);

        DeathWeather.init(deathEventsData, server);

        EnemyEffectsOnPlayerDeath.init(deathEventsData, server);

        ModifyPlayerMaxHealth.reduceMaxHealthAttachment(player, deathEventsData);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ModifyPlayerMaxHealth.reduceMaxHealth(player);
    }

    @SubscribeEvent
    public static void onEnemyMobSpawn(FinalizeSpawnEvent event) {
        if (!(event.getEntity() instanceof Enemy)) return;

        DeathEventsData deathEventsData = DeathEventsData.getInstance(event.getLevel().getServer());

        EnemyEffectsOnPlayerDeath.onEnemyMobSpawn(deathEventsData, event.getEntity());
}

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        DeathEventsData deathEventsData = DeathEventsData.getInstance(event.getServer());

        PurgatoryTimerTicks.reduceEventTimerTicks(deathEventsData);
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

        DeathEventsData deathEventsData = DeathEventsData.getInstance(level.getServer());

        PurgatoryTimerTicks.saveData(deathEventsData);
    }
}
