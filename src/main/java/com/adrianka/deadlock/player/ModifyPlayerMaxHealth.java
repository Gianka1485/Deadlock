package com.adrianka.deadlock.player;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.PurgatoryEventsData;
import com.adrianka.mcenhancedlib.server.attachment.MCELAttachments;
import com.adrianka.mcenhancedlib.server.entity.MCELModifyPlayerProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModifyPlayerMaxHealth {
    public static final String HEALTH_ID = "health_count";

    public static void reduceMaxHealthAttachment(ServerPlayer player, PurgatoryEventsData purgatoryEventsData) {
        MCELModifyPlayerProperties.updatePlayerStatsAttachments(
                player,
                MCELAttachments.HEALTH_MODIFIED.get(),
                purgatoryEventsData.getHealthReducerValue(),
                purgatoryEventsData.getMinHealthValue(),
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
