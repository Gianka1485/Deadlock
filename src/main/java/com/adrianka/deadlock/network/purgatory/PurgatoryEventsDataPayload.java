package com.adrianka.deadlock.network.purgatory;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.data.PurgatoryEventsData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PurgatoryEventsDataPayload(PurgatoryEventsDataSync dataSync) implements CustomPacketPayload {
    public static final Type<PurgatoryEventsDataPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Deadlock.MODID, "purgatory_events_data_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PurgatoryEventsDataPayload> STREAM_CODEC =
            PurgatoryEventsDataSync.STREAM_CODEC.map(PurgatoryEventsDataPayload::new, PurgatoryEventsDataPayload::dataSync);

    public static PurgatoryEventsDataPayload from(PurgatoryEventsData purgatoryEventsData) {
        return new PurgatoryEventsDataPayload(
                new PurgatoryEventsDataSync(
                        purgatoryEventsData.isPurgatoryEventsEnabled(),
                        purgatoryEventsData.getPurgatoryEventsDuration() / 20,
                        purgatoryEventsData.isPurgatoryEventsStackEnabled(),
                        purgatoryEventsData.isPurgatoryRainEnabled(),
                        purgatoryEventsData.isEnemyEffectsEnabled(),
                        purgatoryEventsData.getEnemyBuffAmplifier(),
                        purgatoryEventsData.isEnemySpeedEnabled(),
                        purgatoryEventsData.isEnemyStrengthEnabled(),
                        purgatoryEventsData.isEnemyResistanceEnabled(),
                        purgatoryEventsData.isReducePlayerMaxHealthEnabled(),
                        purgatoryEventsData.getHealthReducerValue(),
                        purgatoryEventsData.getMinHealthValue()
                )
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}