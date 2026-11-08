package com.adrianka.deadlock.server.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class DeadlockNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(
                PurgatorySyncPayload.TYPE,
                PurgatorySyncPayload.STREAM_CODEC
        );
    }

    private DeadlockNetworking() {}
}
