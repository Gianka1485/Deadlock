package com.adrianka.deadlock.player;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.mcenhancedlib.attachment.MCELAttachments;
import com.adrianka.mcenhancedlib.entity.MCELModifyPlayerProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModifyPlayerMaxHealth {
    public static final String HEALTH_ID = "health_count";

    private static double healthReducerValue = -2.0;

    public static double getHealthReducerValue() { return healthReducerValue; }

    public static void setHealthReducerValue(double healthReducerValue) {
        ModifyPlayerMaxHealth.healthReducerValue = healthReducerValue;
    }

    private static double minHealth = -18.0;

    public static double getMinHealth() { return minHealth; }

    public static void setMinHealth(double minHealth) { ModifyPlayerMaxHealth.minHealth = minHealth; }

    public static void reduceMaxHealthAttachment(ServerPlayer player) {
        MCELModifyPlayerProperties.updatePlayerStatsAttachments(
                player,
                MCELAttachments.HEALTH_MODIFIED.get(),
                getHealthReducerValue(),
                getMinHealth(),
                1024.0
        );
    }

    public static void reduceMaxHealth(ServerPlayer player) {
        MCELModifyPlayerProperties.applyPlayerPermanentModifier(
                player,
                MCELAttachments.HEALTH_MODIFIED.get(),
                Attributes.MAX_HEALTH,
                Deadlock.MODID,
                HEALTH_ID
        );
    }

    private ModifyPlayerMaxHealth() {}
}
