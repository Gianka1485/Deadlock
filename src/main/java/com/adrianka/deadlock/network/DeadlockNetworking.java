package com.adrianka.deadlock.network;

import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataPayload;
import com.adrianka.deadlock.network.purgatory.PurgatoryTimerTicksSyncPayload;
import com.adrianka.deadlock.network.purgatory.SetPurgatoryEventsDataPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class DeadlockNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(PurgatoryTimerTicksSyncPayload.TYPE, PurgatoryTimerTicksSyncPayload.STREAM_CODEC);
        event.registrar("1").playToClient(PurgatoryEventsDataPayload.TYPE, PurgatoryEventsDataPayload.STREAM_CODEC);

        event.registrar("1").playToServer(
                SetPurgatoryEventsDataPayload.TYPE,
                SetPurgatoryEventsDataPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() -> SetPurgatoryEventsDataPayload.setData(payload.dataSync(), context))
        );
    }

    private DeadlockNetworking() {}
}