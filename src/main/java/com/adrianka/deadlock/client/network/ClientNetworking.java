package com.adrianka.deadlock.client.network;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.client.gui.purgatory.PurgatoryClientData;
import com.adrianka.deadlock.server.network.PurgatorySyncPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = Deadlock.MODID, value = Dist.CLIENT)
public class ClientNetworking {
    @SubscribeEvent
    public static void registerClientPayloads(RegisterClientPayloadHandlersEvent event) {
        event.register(
                PurgatorySyncPayload.TYPE,
                (payload, context) -> {
                    Deadlock.LOGGER.info(
                            "Received PurgatorySyncPayload: {}",
                            payload.ticks()
                    );

                    PurgatoryClientData.setPurgatoryTicks(payload.ticks());
                }
        );
    }

    private ClientNetworking() {}
}