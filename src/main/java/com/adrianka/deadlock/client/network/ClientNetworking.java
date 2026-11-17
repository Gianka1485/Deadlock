package com.adrianka.deadlock.client.network;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.client.data.PurgatoryClientData;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataPayload;
import com.adrianka.deadlock.network.purgatory.PurgatoryTimerTicksSyncPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = Deadlock.MODID, value = Dist.CLIENT)
public class ClientNetworking {
    @SubscribeEvent
    public static void registerClientPayloads(RegisterClientPayloadHandlersEvent event) {
        event.register(
                PurgatoryTimerTicksSyncPayload.TYPE,
                (payload, _) -> PurgatoryClientData.setPurgatoryTicks(payload.ticks())
        );

        event.register(
                PurgatoryEventsDataPayload.TYPE,
                ((payload, _) -> PurgatoryClientData.applySync(payload.dataSync()))
        );
    }

    private ClientNetworking() {}
}