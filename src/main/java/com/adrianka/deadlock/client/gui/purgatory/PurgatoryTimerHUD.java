package com.adrianka.deadlock.client.gui.purgatory;

import com.adrianka.deadlock.Deadlock;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.Locale;

@EventBusSubscriber(modid = Deadlock.MODID, value = Dist.CLIENT)
public class PurgatoryTimerHUD {
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Identifier.fromNamespaceAndPath(Deadlock.MODID, "purgatory_timer"), PurgatoryTimerHUD::render);
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || minecraft.gui.hud.isHidden()) return;

        int ticks = PurgatoryClientData.getPurgatoryTicks();

        if (ticks <= 0) return;

        int totalSeconds = (int) Math.ceil(ticks / 20.0);

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        String time = String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds);

        Component message = Component.literal("Purgatory: ")
                .withStyle(style -> style.withColor(0xFFDC143C))
                .append(Component.literal(time).withStyle(style -> style.withColor(0xFFFFFFFF)));

        float scale = 0.85f;

        int textWidth = minecraft.font.width(message);
        int textHeight = minecraft.font.lineHeight;

        int panelWidth = textWidth + 24;
        int panelHeight = textHeight + 24;

        int marginRight = 8;
        int marginBottom = 8;

        int x = (int) (graphics.guiWidth() - marginRight - panelWidth * scale);

        int y = (int) (
                graphics.guiHeight() - marginBottom - panelHeight * scale
        );

        graphics.pose().pushMatrix();

        graphics.pose().translate(x, y);
        graphics.pose().scale(scale, scale);

        TooltipRenderUtil.extractTooltipBackground(graphics, 12, 12, textWidth, textHeight, null);

        graphics.text(minecraft.font, message, 12, 12, 0xFFFFFFFF, true);

        graphics.pose().popMatrix();
    }

    private PurgatoryTimerHUD() {}
}