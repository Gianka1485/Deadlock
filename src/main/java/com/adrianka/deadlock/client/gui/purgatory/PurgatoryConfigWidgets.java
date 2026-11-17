package com.adrianka.deadlock.client.gui.purgatory;

import com.adrianka.deadlock.client.gui.DeadlockConfigScreen;
import com.adrianka.deadlock.client.gui.components.DeadlockSlider;
import com.adrianka.deadlock.network.purgatory.PurgatoryEventsDataSync;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;

public class PurgatoryConfigWidgets {
    public static final int MIN_PURGATORY_DURATION = 1;
    public static final int MAX_PURGATORY_DURATION = 60;

    private final DeadlockConfigScreen screen;

    public final CycleButton<Boolean> purgatoryEventsEnableButton;
    public final DeadlockSlider purgatoryEventsDurationSlider;
    public final CycleButton<Boolean> purgatoryEventsStackEnableButton;

    public PurgatoryConfigWidgets(
            DeadlockConfigScreen screen,
            PurgatoryEventsDataSync purgatoryClientData,
            String translatablePrefix,
            int x, int y, int width, int height
    ) {
        this.screen = screen;

        String enableKey = translatablePrefix + ".enable";
        String disableKey = translatablePrefix + ".disable";

        purgatoryEventsEnableButton = CycleButton
                .booleanBuilder(Component.translatable(enableKey), Component.translatable(disableKey), purgatoryClientData.isPurgatoryEventsEnable())
                .create(
                        x, y, width, height,
                        Component.translatable(translatablePrefix + ".events"),
                        (_, value) -> {
                            screen.getPurgatoryClientData().setPurgatoryEventsEnable(value);
                            screen.updateSaveButtonState();
                        }
                );

        purgatoryEventsDurationSlider = new DeadlockSlider(
                translatablePrefix + ".duration",
                MIN_PURGATORY_DURATION,
                MAX_PURGATORY_DURATION,
                purgatoryClientData.getPurgatoryEventsDuration() / 60,
                x, y + height + 5, width, height,
                value -> {
                    screen.getPurgatoryClientData().setPurgatoryEventsDuration(value * 60);
                    screen.updateSaveButtonState();
                }
        );

        purgatoryEventsStackEnableButton = CycleButton
                .booleanBuilder(Component.translatable(enableKey), Component.translatable(disableKey), purgatoryClientData.isPurgatoryEventsStackEnable())
                .create(
                        x, y + height * 2 + 5 * 2, width, height,
                        Component.translatable(translatablePrefix + ".stackable"),
                        (_, value) -> {
                            screen.getPurgatoryClientData().setPurgatoryEventsStackEnable(value);
                            screen.updateSaveButtonState();
                        }
                );

        screen.addRightPanelWidget(purgatoryEventsEnableButton);
        screen.addRightPanelWidget(purgatoryEventsDurationSlider);
        screen.addRightPanelWidget(purgatoryEventsStackEnableButton);
    }

    public void update(PurgatoryEventsDataSync dataUpdated, boolean isWidgetSelected) {
        if (screen.getPurgatoryClientData().isPurgatoryEventsEnable() != dataUpdated.isPurgatoryEventsEnable()) {
            if (isWidgetSelected) purgatoryEventsEnableButton.setValue(dataUpdated.isPurgatoryEventsEnable());

            screen.getPurgatoryClientData().setPurgatoryEventsEnable(dataUpdated.isPurgatoryEventsEnable());
        }

        if (screen.getPurgatoryClientData().getPurgatoryEventsDuration() != dataUpdated.getPurgatoryEventsDuration()) {
            if (isWidgetSelected) purgatoryEventsDurationSlider.setActualValue(dataUpdated.getPurgatoryEventsDuration() / 60);

            screen.getPurgatoryClientData().setPurgatoryEventsDuration(dataUpdated.getPurgatoryEventsDuration());
        }

        if (screen.getPurgatoryClientData().isPurgatoryEventsStackEnable() != dataUpdated.isPurgatoryEventsStackEnable()) {
            if (isWidgetSelected) purgatoryEventsStackEnableButton.setValue(dataUpdated.isPurgatoryEventsStackEnable());

            screen.getPurgatoryClientData().setPurgatoryEventsStackEnable(dataUpdated.isPurgatoryEventsStackEnable());
        }
    }
}