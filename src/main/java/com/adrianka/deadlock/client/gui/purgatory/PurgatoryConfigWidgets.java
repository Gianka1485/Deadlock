package com.adrianka.deadlock.client.gui.purgatory;

import com.adrianka.deadlock.client.gui.DeadlockConfigScreen;
import com.adrianka.deadlock.client.gui.components.DeadlockSlider;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataSync;
import com.adrianka.mcenhancedlib.client.gui.MCELWidgetList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;

public class PurgatoryConfigWidgets {
    private static final int WIDGETS_MARGIN = 5;

    private static final int MIN_PURGATORY_DURATION = 1;
    private static final int MAX_PURGATORY_DURATION = 60;

    private static final int MIN_ENEMY_BUFF_AMPLIFIER = 0;
    private static final int MAX_ENEMY_BUFF_AMPLIFIER = 5;

    private static final int MIN_HEALTH_REDUCER_VALUE = -20;
    private static final int MAX_HEALTH_REDUCER_VALUE = -2;

    private static final int MIN_HEALTH_VALUE = -18;
    private static final int MAX_HEALTH_VALUE = 0;

    private final DeadlockConfigScreen screen;
    private final MCELWidgetList widgetList;

    private final PurgatoryEventsDataSync purgatoryClientData;
    private final String translatablePrefix;

    private final int widgetWidth;
    private final int widgetHeight;

    public final CycleButton<Boolean> purgatoryEventsEnableButton;
    public final DeadlockSlider purgatoryEventsDurationSlider;
    public final CycleButton<Boolean> purgatoryEventsStackEnableButton;

    public final CycleButton<Boolean> purgatoryRainEnabledButton;
    public final CycleButton<Boolean> purgatoryRainSkipButton;

    public final CycleButton<Boolean> enemyEffectsEnabledButton;
    public final DeadlockSlider enemyBuffAmplifierSlider;
    public final CycleButton<Boolean> enemySpeedEnabledButton;
    public final CycleButton<Boolean> enemyStrengthEnabledButton;
    public final CycleButton<Boolean> enemyResistanceEnabledButton;

    public final CycleButton<Boolean> reducePlayerMaxHealthEnabledButton;
    public final DeadlockSlider healthReducerValue;
    public final DeadlockSlider minHealthValue;

    public PurgatoryConfigWidgets(
            DeadlockConfigScreen screen,
            MCELWidgetList widgetList,
            PurgatoryEventsDataSync purgatoryClientData,
            String translatablePrefix,
            int widgetWidth,
            int widgetHeight
    ) {
        this.screen = screen;
        this.widgetList = widgetList;
        this.purgatoryClientData = purgatoryClientData;
        this.translatablePrefix = translatablePrefix;
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;

        purgatoryEventsEnableButton = createBooleanButton(
                ".events",
                purgatoryClientData.isPurgatoryEventsEnable(),
                purgatoryClientData::setPurgatoryEventsEnable
        );

        purgatoryEventsDurationSlider = createSlider(
                ".duration",
                MIN_PURGATORY_DURATION,
                MAX_PURGATORY_DURATION,
                purgatoryClientData.getPurgatoryEventsDuration() / 60,
                value -> purgatoryClientData.setPurgatoryEventsDuration(value * 60)
        );

        purgatoryEventsStackEnableButton = createBooleanButton(
                ".stackable",
                purgatoryClientData.isPurgatoryEventsStackEnable(),
                purgatoryClientData::setPurgatoryEventsStackEnable
        );

        purgatoryRainEnabledButton = createBooleanButton(
                ".weather",
                purgatoryClientData.isPurgatoryRainEnable(),
                purgatoryClientData::setPurgatoryRainEnable
        );

        purgatoryRainSkipButton = createBooleanButton(
                ".skip",
                purgatoryClientData.isPurgatoryRainSkip(),
                purgatoryClientData::setPurgatoryRainSkip
        );

        enemyEffectsEnabledButton = createBooleanButton(
                ".buffs",
                purgatoryClientData.isEnemyBuffsEnable(),
                purgatoryClientData::setEnemyBuffsEnable
        );

        enemyBuffAmplifierSlider = createSlider(
                ".amplifier",
                MIN_ENEMY_BUFF_AMPLIFIER,
                MAX_ENEMY_BUFF_AMPLIFIER,
                purgatoryClientData.getEnemyBuffAmplifier(),
                purgatoryClientData::setEnemyBuffAmplifier
        );

        enemySpeedEnabledButton = createBooleanButton(
                ".speed",
                purgatoryClientData.isEnemySpeedEnable(),
                purgatoryClientData::setEnemySpeedEnable
        );

        enemyStrengthEnabledButton = createBooleanButton(
                ".strength",
                purgatoryClientData.isEnemyStrengthEnable(),
                purgatoryClientData::setEnemyStrengthEnable
        );

        enemyResistanceEnabledButton = createBooleanButton(
                ".resistance",
                purgatoryClientData.isEnemyResistanceEnable(),
                purgatoryClientData::setEnemyResistanceEnable
        );

        reducePlayerMaxHealthEnabledButton = createBooleanButton(
                ".health",
                purgatoryClientData.isReducePlayerMaxHealthEnabled(),
                purgatoryClientData::setReducePlayerMaxHealthEnabled
        );

        healthReducerValue = createSlider(
                ".reductor",
                MIN_HEALTH_REDUCER_VALUE / 2,
                MAX_HEALTH_REDUCER_VALUE / 2,
                (int) (purgatoryClientData.getHealthReducerValue() / 2),
                value -> purgatoryClientData.setHealthReducerValue(value * 2)
        );

        minHealthValue = createSlider(
                ".min",
                MIN_HEALTH_VALUE / 2,
                MAX_HEALTH_VALUE / 2,
                (int) (purgatoryClientData.getMinHealthValue() / 2),
                value -> purgatoryClientData.setMinHealthValue(value * 2)
        );

        registerWidgets();
    }

    private CycleButton<Boolean> createBooleanButton(String translationKey, boolean initialValue, Consumer<Boolean> onValueChanged) {
        Component enable = Component.translatable(translatablePrefix + ".enable");
        Component disable = Component.translatable(translatablePrefix + ".disable");

        return CycleButton.booleanBuilder(enable, disable, initialValue)
                .create(
                        0, 0, widgetWidth, widgetHeight,
                        Component.translatable(translatablePrefix + translationKey),
                        (_, value) -> {
                            onValueChanged.accept(value);
                            screen.updateSaveButtonState();
                        }
                );
    }

    private DeadlockSlider createSlider(String translationKey, int minValue, int maxValue, int initialValue, IntConsumer onValueChanged) {
        return new DeadlockSlider(
                translatablePrefix + translationKey,
                minValue,
                maxValue,
                initialValue,
                0, 0, widgetWidth, widgetHeight,
                value -> {
                    onValueChanged.accept(value);
                    screen.updateSaveButtonState();
                }
        );
    }

    private void registerWidgets() {
        widgetList.addWidget(purgatoryEventsEnableButton, WIDGETS_MARGIN);
        widgetList.addWidget(purgatoryEventsDurationSlider, WIDGETS_MARGIN);
        widgetList.addWidget(purgatoryEventsStackEnableButton, WIDGETS_MARGIN);

        widgetList.addWidget(purgatoryRainEnabledButton, WIDGETS_MARGIN);
        widgetList.addWidget(purgatoryRainSkipButton, WIDGETS_MARGIN);

        widgetList.addWidget(enemyEffectsEnabledButton, WIDGETS_MARGIN);
        widgetList.addWidget(enemyBuffAmplifierSlider, WIDGETS_MARGIN);
        widgetList.addWidget(enemySpeedEnabledButton, WIDGETS_MARGIN);
        widgetList.addWidget(enemyStrengthEnabledButton, WIDGETS_MARGIN);
        widgetList.addWidget(enemyResistanceEnabledButton, WIDGETS_MARGIN);

        widgetList.addWidget(reducePlayerMaxHealthEnabledButton, WIDGETS_MARGIN);
        widgetList.addWidget(healthReducerValue, WIDGETS_MARGIN);
        widgetList.addWidget(minHealthValue, 0);
    }

    public void update(PurgatoryEventsDataSync dataUpdated, boolean isWidgetSelected) {
        updateBoolean(
                purgatoryClientData.isPurgatoryEventsEnable(),
                dataUpdated.isPurgatoryEventsEnable(),
                isWidgetSelected,
                purgatoryEventsEnableButton,
                purgatoryClientData::setPurgatoryEventsEnable
        );

        updateSlider(
                purgatoryClientData.getPurgatoryEventsDuration(),
                dataUpdated.getPurgatoryEventsDuration(),
                isWidgetSelected,
                purgatoryEventsDurationSlider,
                value -> value / 60,
                purgatoryClientData::setPurgatoryEventsDuration
        );

        updateBoolean(
                purgatoryClientData.isPurgatoryEventsStackEnable(),
                dataUpdated.isPurgatoryEventsStackEnable(),
                isWidgetSelected,
                purgatoryEventsStackEnableButton,
                purgatoryClientData::setPurgatoryEventsStackEnable
        );

        updateBoolean(
                purgatoryClientData.isPurgatoryRainEnable(),
                dataUpdated.isPurgatoryRainEnable(),
                isWidgetSelected,
                purgatoryRainEnabledButton,
                purgatoryClientData::setPurgatoryRainEnable
        );

        updateBoolean(
                purgatoryClientData.isPurgatoryRainSkip(),
                dataUpdated.isPurgatoryRainSkip(),
                isWidgetSelected,
                purgatoryRainSkipButton,
                purgatoryClientData::setPurgatoryRainSkip
        );

        updateBoolean(
                purgatoryClientData.isEnemyBuffsEnable(),
                dataUpdated.isEnemyBuffsEnable(),
                isWidgetSelected,
                enemyEffectsEnabledButton,
                purgatoryClientData::setEnemyBuffsEnable
        );

        updateSlider(
                purgatoryClientData.getEnemyBuffAmplifier(),
                dataUpdated.getEnemyBuffAmplifier(),
                isWidgetSelected,
                enemyBuffAmplifierSlider,
                value -> value,
                purgatoryClientData::setEnemyBuffAmplifier
        );

        updateBoolean(
                purgatoryClientData.isEnemySpeedEnable(),
                dataUpdated.isEnemySpeedEnable(),
                isWidgetSelected,
                enemySpeedEnabledButton,
                purgatoryClientData::setEnemySpeedEnable
        );

        updateBoolean(
                purgatoryClientData.isEnemyStrengthEnable(),
                dataUpdated.isEnemyStrengthEnable(),
                isWidgetSelected,
                enemyStrengthEnabledButton,
                purgatoryClientData::setEnemyStrengthEnable
        );

        updateBoolean(
                purgatoryClientData.isEnemyResistanceEnable(),
                dataUpdated.isEnemyResistanceEnable(),
                isWidgetSelected,
                enemyResistanceEnabledButton,
                purgatoryClientData::setEnemyResistanceEnable
        );

        updateBoolean(
                purgatoryClientData.isReducePlayerMaxHealthEnabled(),
                dataUpdated.isReducePlayerMaxHealthEnabled(),
                isWidgetSelected,
                reducePlayerMaxHealthEnabledButton,
                purgatoryClientData::setReducePlayerMaxHealthEnabled
        );

        updateSlider(
                (int) purgatoryClientData.getHealthReducerValue(),
                (int) dataUpdated.getHealthReducerValue(),
                isWidgetSelected,
                healthReducerValue,
                value -> value / 2,
                value -> purgatoryClientData.setHealthReducerValue(value)
        );

        updateSlider(
                (int) purgatoryClientData.getMinHealthValue(),
                (int) dataUpdated.getMinHealthValue(),
                isWidgetSelected,
                minHealthValue,
                value -> value / 2,
                value -> purgatoryClientData.setMinHealthValue(value)
        );
    }

    private void updateBoolean(boolean currentValue, boolean updatedValue, boolean isWidgetSelected, CycleButton<Boolean> button, Consumer<Boolean> setter) {
        if (currentValue == updatedValue) return;

        if (isWidgetSelected) button.setValue(updatedValue);

        setter.accept(updatedValue);
    }

    private void updateSlider(int currentValue, int updatedValue, boolean isWidgetSelected, DeadlockSlider slider, IntUnaryOperator displayValue, IntConsumer setter) {
        if (currentValue == updatedValue) return;

        if (isWidgetSelected) slider.setActualValue(displayValue.applyAsInt(updatedValue));

        setter.accept(updatedValue);
    }
}