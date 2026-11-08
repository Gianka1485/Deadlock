package com.adrianka.deadlock.player;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.DeathEventsData;
import com.adrianka.mcenhancedlib.attachment.MCELAttachments;
import com.adrianka.mcenhancedlib.entity.MCELModifyPlayerProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModifyPlayerMaxHealth {
    public static final String HEALTH_ID = "health_count";

    public static void reduceMaxHealthAttachment(ServerPlayer player, DeathEventsData deathEventsData) {
        MCELModifyPlayerProperties.updatePlayerStatsAttachments(
                player,
                MCELAttachments.HEALTH_MODIFIED.get(),
                deathEventsData.getHealthReducerValue(),
                deathEventsData.getMinHealthValue(),
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
