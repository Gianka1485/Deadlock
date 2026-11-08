package com.adrianka.deadlock.server.network;

import com.adrianka.deadlock.Deadlock;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PurgatorySyncPayload(int ticks) implements CustomPacketPayload {
    public static final Type<PurgatorySyncPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    Deadlock.MODID,
                    "purgatory_timer_sync"
            ));

    public static final StreamCodec<RegistryFriendlyByteBuf, PurgatorySyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    PurgatorySyncPayload::ticks,
                    PurgatorySyncPayload::new
            );

    @Override
    public Type<PurgatorySyncPayload> type() {
        return TYPE;
    }
}
