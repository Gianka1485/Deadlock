package com.adrianka.deadlock.client.gui.components;

import com.adrianka.deadlock.client.gui.DeadlockConfigScreen;
import com.adrianka.deadlock.network.purgatory.SetPurgatoryEventsDataPayload;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;

public class DeadlockSaveButton {
    private final DeadlockConfigScreen screen;
    private final Button button;

    public DeadlockSaveButton(DeadlockConfigScreen screen, String translatableKey, int x, int y, int width, int height) {
        this.screen = screen;

        this.button = Button
                .builder(Component.translatable(translatableKey + ".save"), _ -> save())
                .bounds(x, y, width, height).build();

        this.button.active = false;
    }

    public Button getButton() { return button; }

    public void updateState(boolean state) { button.active = state; }

    private void save() {
        ClientPacketListener connection = screen.getConnection();

        if (connection == null) return;

        connection.send(new SetPurgatoryEventsDataPayload(screen.getPurgatoryClientData()));

        screen.onClose();
    }
}