package com.adrianka.deadlock.client.input;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.client.gui.DeadlockOptionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Deadlock.MODID, value = Dist.CLIENT)
public class DeadlockKeyEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (DeadlockKeyMappings.OPEN_OPTIONS.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.gui.screen() == null)
                minecraft.setScreenAndShow(
                        new DeadlockOptionsScreen(Component.translatable("screen." + Deadlock.MODID + ".options"))
                );
        }
    }
}
