package com.adrianka.deadlock.client.input;

import com.adrianka.deadlock.Deadlock;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class DeadlockKeyMappings {
    public static final KeyMapping.Category CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(Deadlock.MODID, "inputs"));

    public static final KeyMapping OPEN_OPTIONS = new KeyMapping(
            "key." + Deadlock.MODID + ".open_options",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_F10,
            CATEGORY
    );

    public static void register(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(OPEN_OPTIONS);
    }

    private DeadlockKeyMappings() {}
}
