package com.adrianka.deadlock.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class DeadlockCategoryList extends AbstractSelectionList<DeadlockCategoryList.CategoryEntry> {
    public enum Category {
        PURGATORY,
        EMPTY
    }

    private final Consumer<Category> onCategorySelected;

    public DeadlockCategoryList(Minecraft minecraft, String translatableKey, int x, int y, int width, int height, Consumer<Category> onCategorySelected) {
        super(minecraft, width, height, y, 18);

        this.onCategorySelected = onCategorySelected;

        this.setX(x);
        this.centerListVertically = false;

        this.addEntry(new CategoryEntry(Component.translatable(translatableKey), Category.PURGATORY));

        this.addEntry(new CategoryEntry(Component.literal("Test"), Category.EMPTY));
    }

    @Override
    protected void extractListBackground(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractListSeparators(GuiGraphicsExtractor graphics) {
    }

    @Override
    public int getRowWidth() { return this.width - 4;}

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    public class CategoryEntry extends AbstractSelectionList.Entry<CategoryEntry> {

        private final Component title;
        private final Category category;

        public CategoryEntry(Component title, Category category) {
            this.title = title;
            this.category = category;
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int color = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;

            graphics.text(DeadlockCategoryList.this.minecraft.font, this.title, this.getContentX() + 4, this.getContentY() + 3, color);
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            DeadlockCategoryList.this.setSelected(this);
            DeadlockCategoryList.this.onCategorySelected.accept(this.category);
            return true;
        }
    }
}