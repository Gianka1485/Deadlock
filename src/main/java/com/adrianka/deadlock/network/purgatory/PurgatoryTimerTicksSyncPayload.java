package com.adrianka.deadlock.network.purgatory;

import com.adrianka.deadlock.Deadlock;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PurgatoryTimerTicksSyncPayload(int ticks) implements CustomPacketPayload {
    public static final Type<PurgatoryTimerTicksSyncPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Deadlock.MODID, "purgatory_timer_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PurgatoryTimerTicksSyncPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.VAR_INT, PurgatoryTimerTicksSyncPayload::ticks, PurgatoryTimerTicksSyncPayload::new);

    @Override
    public Type<PurgatoryTimerTicksSyncPayload> type() { return TYPE; }
}