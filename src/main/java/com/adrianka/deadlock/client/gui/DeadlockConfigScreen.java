package com.adrianka.deadlock.client.gui;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.client.data.PurgatoryClientData;
import com.adrianka.deadlock.client.gui.components.DeadlockCategoryList;
import com.adrianka.deadlock.client.gui.components.DeadlockSaveButton;
import com.adrianka.deadlock.client.gui.purgatory.PurgatoryConfigWidgets;
import com.adrianka.deadlock.client.input.DeadlockKeyMappings;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataSync;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DeadlockConfigScreen extends Screen {
    private static final String CONFIG_TRANSLATABLE_KEY = "screen." + Deadlock.MODID + ".config";

    private static final String PURGATORY_TRANSLATABLE_KEY = CONFIG_TRANSLATABLE_KEY + ".purgatory";

    private static final int LEFT_PANEL_WIDTH = 150;
    private static final int RIGHT_PANEL_WIDTH = 250;
    private static final int PANEL_GAP = 10;

    private static final int PANEL_HORIZONTAL_PADDING = 10;
    private static final int PANEL_VERTICAL_PADDING = 10;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 40;

    private static final int SAVE_BUTTON_WIDTH = 150;
    private static final int SAVE_BUTTON_HEIGHT = 20;
    private static final int SAVE_BUTTON_BOTTOM_MARGIN = 10;

    private static final int HEADER_TEXT_Y = 15;

    private int leftPanelX;
    private int rightPanelX;
    private int panelY;
    private int panelHeight;

    private final List<AbstractWidget> rightPanelWidgets = new ArrayList<>();

    private DeadlockCategoryList.Category selectedCategory;

    private PurgatoryEventsDataSync purgatoryClientData;
    private PurgatoryEventsDataSync purgatoryDataSync;

    private DeadlockCategoryList categoryList;
    private DeadlockSaveButton saveButton;
    private PurgatoryConfigWidgets purgatoryConfigWidgets;

    public DeadlockConfigScreen(Component title) { super(title); }

    @Override
    protected void init() {
        updatePanelPositions();

        createCategoryList();
        createSaveButton();

        purgatoryDataSync = PurgatoryClientData.getCurrent();
        purgatoryClientData = new PurgatoryEventsDataSync(
                purgatoryDataSync.isPurgatoryEventsEnable(),
                purgatoryDataSync.getPurgatoryEventsDuration(),
                purgatoryDataSync.isPurgatoryEventsStackEnable(),
                purgatoryDataSync.isPurgatoryRainEnable(),
                purgatoryDataSync.isEnemyBuffsEnable(),
                purgatoryDataSync.getEnemyBuffAmplifier(),
                purgatoryDataSync.isEnemySpeedEnable(),
                purgatoryDataSync.isEnemyStrengthEnable(),
                purgatoryDataSync.isEnemyResistanceEnable(),
                purgatoryDataSync.isReducePlayerMaxHealthEnabled(),
                purgatoryDataSync.getHealthReducerValue(),
                purgatoryDataSync.getMinHealthValue()
        );
    }

    private void createCategoryList() {
        categoryList = new DeadlockCategoryList(this.minecraft, PURGATORY_TRANSLATABLE_KEY, leftPanelX, panelY, LEFT_PANEL_WIDTH, panelHeight, this::updateRightPanel);

        addRenderableWidget(categoryList);
    }

    private void createSaveButton() {
        int x = (width - SAVE_BUTTON_WIDTH) / 2;
        int y = height - SAVE_BUTTON_HEIGHT - SAVE_BUTTON_BOTTOM_MARGIN;

        saveButton = new DeadlockSaveButton(this, CONFIG_TRANSLATABLE_KEY, x, y, SAVE_BUTTON_WIDTH, SAVE_BUTTON_HEIGHT);

        addRenderableWidget(saveButton.getButton());
    }

    private void updatePanelPositions() {
        int totalWidth = LEFT_PANEL_WIDTH + PANEL_GAP + RIGHT_PANEL_WIDTH;

        int left = (width - totalWidth) / 2;

        leftPanelX = left;
        rightPanelX = left + LEFT_PANEL_WIDTH + PANEL_GAP;

        panelY = HEADER_HEIGHT;
        panelHeight = height - HEADER_HEIGHT - FOOTER_HEIGHT;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        renderPanel(graphics, leftPanelX, panelY, LEFT_PANEL_WIDTH, panelHeight);
        renderPanel(graphics, rightPanelX, panelY, RIGHT_PANEL_WIDTH, panelHeight);

        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        renderHeader(graphics);
    }

    private void renderPanel(GuiGraphicsExtractor graphics, int x, int y, int panelWidth, int panelHeight) {
        boolean inWorld = minecraft.level != null;

        Identifier background = inWorld
                ? AbstractSelectionList.INWORLD_MENU_LIST_BACKGROUND
                : Identifier.withDefaultNamespace("textures/gui/menu_list_background.png");

        Identifier topBorder = inWorld
                ? Screen.INWORLD_HEADER_SEPARATOR
                : Screen.HEADER_SEPARATOR;

        Identifier bottomBorder = inWorld
                ? Screen.INWORLD_FOOTER_SEPARATOR
                : Screen.FOOTER_SEPARATOR;

        graphics.blit(RenderPipelines.GUI_TEXTURED, background, x, y, (float) x, (float) (y + panelHeight), panelWidth, panelHeight, 32, 32);

        graphics.blit(RenderPipelines.GUI_TEXTURED, topBorder, x, y - 2, 0.0F, 0.0F, panelWidth, 2, 32, 2);

        graphics.blit(RenderPipelines.GUI_TEXTURED, bottomBorder, x, y + panelHeight, 0.0F, 0.0F, panelWidth, 2, 32, 2);
    }

    private void renderHeader(GuiGraphicsExtractor graphics) {
        Component header = Component.translatable(CONFIG_TRANSLATABLE_KEY);

        int textWidth = font.width(header);
        int x = (width - textWidth) / 2;

        graphics.text(font, header, x, HEADER_TEXT_Y, 0xFFFFFFFF);
    }

    private void updateRightPanel(DeadlockCategoryList.Category category) {
        selectedCategory = category;

        clearRightPanel();

        if (category == DeadlockCategoryList.Category.PURGATORY) createPurgatoryWidgets();
    }

    private void createPurgatoryWidgets() {
        int x = rightPanelX + PANEL_HORIZONTAL_PADDING;
        int y = panelY + PANEL_VERTICAL_PADDING;

        int widgetWidth = RIGHT_PANEL_WIDTH - PANEL_HORIZONTAL_PADDING * 2;
        int widgetHeight = 20;

        purgatoryConfigWidgets = new PurgatoryConfigWidgets(this, purgatoryClientData, PURGATORY_TRANSLATABLE_KEY, x, y, widgetWidth, widgetHeight);
    }

    private void clearRightPanel() {
        for (AbstractWidget widget : rightPanelWidgets) removeWidget(widget);

        rightPanelWidgets.clear();
        purgatoryConfigWidgets = null;
    }

    public void addRightPanelWidget(AbstractWidget widget) {
        addRenderableWidget(widget);
        rightPanelWidgets.add(widget);
    }

    @Override
    public void tick() {
        super.tick();

        updateReceivedData();
    }

    private void updateReceivedData() {
        if (purgatoryConfigWidgets != null && !purgatoryDataSync.equals(PurgatoryClientData.getCurrent())) {
            purgatoryDataSync = PurgatoryClientData.getCurrent();
            purgatoryConfigWidgets.update(PurgatoryClientData.getCurrent(), selectedCategory == DeadlockCategoryList.Category.PURGATORY);
        }
    }

    public void updateSaveButtonState() { saveButton.updateState(isPurgatoryDataNotEquals()); }

    public ClientPacketListener getConnection() { return minecraft.getConnection(); }

    public PurgatoryEventsDataSync getPurgatoryClientData() { return purgatoryClientData; }

    public boolean isPurgatoryDataNotEquals() { return !purgatoryClientData.equals(purgatoryDataSync); }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (DeadlockKeyMappings.OPEN_OPTIONS.matches(event)) {
            onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean isPauseScreen() { return true; }
}