package com.adrianka.deadlock.network.purgatory;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.PurgatoryEventsData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetPurgatoryEventsDataPayload(PurgatoryEventsDataSync dataSync) implements CustomPacketPayload {
    private static final int MIN_PURGATORY_DURATION = 20 * 60;
    private static final int MAX_PURGATORY_DURATION = 20 * 3600;

    private static final int MIN_BUFF_AMPLIFIER = 0;
    private static final int MAX_BUFF_AMPLIFIER = 5;

    private static final double MIN_HEALTH_REDUCER_VALUE = -20.0;
    private static final double MAX_HEALTH_REDUCER_VALUE = -2.0;

    private static final double MIN_HEALTH_VALUE = -18.0;
    private static final double MAX_HEALTH_VALUE = -2.0;

    public static final Type<SetPurgatoryEventsDataPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Deadlock.MODID, "set_purgatory_events_data_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetPurgatoryEventsDataPayload> STREAM_CODEC =
            PurgatoryEventsDataSync.STREAM_CODEC.map(SetPurgatoryEventsDataPayload::new, SetPurgatoryEventsDataPayload::dataSync);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void setData(PurgatoryEventsDataSync payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player) || !player.permissions().hasPermission(Permissions.COMMANDS_ADMIN)) return;

        PurgatoryEventsData purgatoryEventsData = PurgatoryEventsData.getInstance(player.level().getServer());

        purgatoryEventsData.setPurgatoryEventsEnabled(payload.isPurgatoryEventsEnable());
        purgatoryEventsData.setPurgatoryEventsDuration(Math.clamp(payload.getPurgatoryEventsDuration() * 20, MIN_PURGATORY_DURATION, MAX_PURGATORY_DURATION));
        purgatoryEventsData.setPurgatoryEventsStackEnabled(payload.isPurgatoryEventsStackEnable());
        purgatoryEventsData.setPurgatoryRainEnabled(payload.isPurgatoryRainEnable());
        purgatoryEventsData.setEnemyEffectsEnabled(payload.isEnemyBuffsEnable());
        purgatoryEventsData.setEnemyBuffAmplifier(Math.clamp(payload.getEnemyBuffAmplifier(), MIN_BUFF_AMPLIFIER, MAX_BUFF_AMPLIFIER));
        purgatoryEventsData.setEnemySpeedEnabled(payload.isEnemySpeedEnable());
        purgatoryEventsData.setEnemyStrengthEnabled(payload.isEnemyStrengthEnable());
        purgatoryEventsData.setEnemyResistanceEnabled(payload.isEnemyResistanceEnable());
        purgatoryEventsData.setReducePlayerMaxHealthEnabled(payload.isReducePlayerMaxHealthEnabled());
        purgatoryEventsData.setHealthReducerValue(Math.clamp(payload.getHealthReducerValue(), MIN_HEALTH_REDUCER_VALUE, MAX_HEALTH_REDUCER_VALUE));
        purgatoryEventsData.setMinHealthValue(Math.clamp(payload.getMinHealthValue(), MIN_HEALTH_VALUE, MAX_HEALTH_VALUE));

        PacketDistributor.sendToAllPlayers(PurgatoryEventsDataPayload.from(purgatoryEventsData));
    }
}