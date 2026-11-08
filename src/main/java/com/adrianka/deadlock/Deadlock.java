package com.adrianka.deadlock;

import com.adrianka.deadlock.client.input.DeadlockKeyMappings;
import com.adrianka.deadlock.event.DeadlockEvents;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Deadlock.MODID)
public class Deadlock {
    public static final String MODID = "deadlock";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Deadlock(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(DeadlockEvents.class);

        modEventBus.addListener(DeadlockKeyMappings::register);
        //NeoForge.EVENT_BUS.register(DeadlockCommands.class);

        //DeadlockAttachments.ATTACHMENTS.register(modEventBus);

        //modContainer.registerConfig(ModConfig.Type.SYNCED, DeadlockConfig.MOD_CONFIG_SPEC);
    }
}