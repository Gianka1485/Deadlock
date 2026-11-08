package com.adrianka.deadlock.client.gui;

import com.adrianka.deadlock.client.input.DeadlockKeyMappings;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public class DeadlockOptionsScreen extends Screen {
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 180;

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        // Panel background
        graphics.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xC0000000);

        // Panel border
        graphics.fill(left, top, left + PANEL_WIDTH, top + 1, 0xFFFFFFFF);

        // Screen title
        graphics.text(this.font, this.title, left + 10, top + 10, 0xFFFFFFFF);

        // Render widgets
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (DeadlockKeyMappings.OPEN_OPTIONS.matches(event)) {
            onClose();
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    public DeadlockOptionsScreen(Component title) {
        super(title);
    }
}
