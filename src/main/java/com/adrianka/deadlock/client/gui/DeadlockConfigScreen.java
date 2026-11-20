package com.adrianka.deadlock.client.gui;

import com.adrianka.deadlock.Deadlock;
import com.adrianka.deadlock.client.data.PurgatoryClientData;
import com.adrianka.deadlock.client.gui.components.DeadlockSaveButton;
import com.adrianka.deadlock.client.gui.purgatory.PurgatoryConfigWidgets;
import com.adrianka.deadlock.client.input.DeadlockKeyMappings;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataSync;
import com.adrianka.mcenhancedlib.client.gui.MCELCategoryList;
import com.adrianka.mcenhancedlib.client.gui.MCELWidgetList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;

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

    private ConfigCategory selectedCategory;

    private PurgatoryEventsDataSync purgatoryClientData;
    private PurgatoryEventsDataSync purgatoryDataSync;

    private MCELCategoryList<ConfigCategory> categoryList;
    private MCELWidgetList widgetList;

    private DeadlockSaveButton saveButton;
    private PurgatoryConfigWidgets purgatoryConfigWidgets;

    public DeadlockConfigScreen(Component title) { super(title); }

    @Override
    protected void init() {
        updatePanelPositions();
        initializePurgatoryData();

        createCategoryList();
        createWidgetList();
        createSaveButton();
    }

    private void initializePurgatoryData() {
        if (purgatoryClientData != null) return;

        purgatoryDataSync = PurgatoryClientData.getCurrent();

        purgatoryClientData = new PurgatoryEventsDataSync(
                purgatoryDataSync.isPurgatoryEventsEnable(),
                purgatoryDataSync.getPurgatoryEventsDuration(),
                purgatoryDataSync.isPurgatoryEventsStackEnable(),
                purgatoryDataSync.isPurgatoryRainEnable(),
                purgatoryDataSync.isPurgatoryRainSkip(),
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
        Font font = this.minecraft.font;

        Integer yPadding = null;
        int xPadding = 5;

        categoryList = new MCELCategoryList<>(minecraft, leftPanelX, panelY, LEFT_PANEL_WIDTH, panelHeight, 20, this::updateRightPanel);

        categoryList.addCategory(PURGATORY_TRANSLATABLE_KEY, ConfigCategory.PURGATORY, xPadding, yPadding, font);

        categoryList.addCategory("screen.deadlock.config.empty", ConfigCategory.EMPTY, xPadding, yPadding, font);

        addRenderableWidget(categoryList);
    }

    private void createWidgetList() {
        widgetList = new MCELWidgetList(minecraft, rightPanelX, panelY, RIGHT_PANEL_WIDTH, panelHeight, 20, 10);

        addRenderableWidget(widgetList);
    }

    private void createSaveButton() {
        saveButton = new DeadlockSaveButton(
                this,
                CONFIG_TRANSLATABLE_KEY,
                (width - SAVE_BUTTON_WIDTH) / 2,
                height - SAVE_BUTTON_HEIGHT - SAVE_BUTTON_BOTTOM_MARGIN,
                SAVE_BUTTON_WIDTH,
                SAVE_BUTTON_HEIGHT
        );

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

    private void updateRightPanel(ConfigCategory category) {
        selectedCategory = category;

        widgetList.clearWidgets();
        purgatoryConfigWidgets = null;

        switch (category) {
            case PURGATORY -> createPurgatoryWidgets();
            case EMPTY -> {
                // Esta categoría no tiene controles por ahora.
            }
        }

        updateSaveButtonState();
    }

    private void createPurgatoryWidgets() {
        int widgetWidth = RIGHT_PANEL_WIDTH - PANEL_HORIZONTAL_PADDING;
        int widgetHeight = 20;

        purgatoryConfigWidgets = new PurgatoryConfigWidgets(
                this,
                widgetList,
                purgatoryClientData,
                PURGATORY_TRANSLATABLE_KEY,
                widgetWidth,
                widgetHeight
        );
    }

    @Override
    public void tick() {
        super.tick();
        updateReceivedData();
    }

    private void updateReceivedData() {
        PurgatoryEventsDataSync currentData = PurgatoryClientData.getCurrent();

        if (purgatoryDataSync.equals(currentData)) return;

        purgatoryDataSync = currentData;

        if (purgatoryConfigWidgets != null) purgatoryConfigWidgets.update(currentData, selectedCategory == ConfigCategory.PURGATORY);

        updateSaveButtonState();
    }

    public void updateSaveButtonState() { if (saveButton != null) saveButton.updateState(isPurgatoryDataNotEquals()); }

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
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        Component header = Component.translatable(CONFIG_TRANSLATABLE_KEY);
        int x = (width - font.width(header)) / 2;

        graphics.text(font, header, x, HEADER_TEXT_Y, 0xFFFFFFFF);
    }

    @Override
    public boolean isPauseScreen() { return true; }
}