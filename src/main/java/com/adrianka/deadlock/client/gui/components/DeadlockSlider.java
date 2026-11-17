package com.adrianka.deadlock.client.gui.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.IntConsumer;

public class DeadlockSlider extends AbstractSliderButton {
    private final String textTranslatableKey;

    private final int minValue;
    private final int maxValue;

    private final IntConsumer applyValue;

    private boolean updatingRemotely;

    public DeadlockSlider(String textKey, int minValue, int maxValue, int initialValue, int x, int y, int width, int height, IntConsumer applyValue) {
        super(x, y, width, height, Component.empty(), normalizeValue(initialValue, minValue, maxValue));

        if (minValue >= maxValue) throw new IllegalArgumentException("minValue must be less than maxValue");

        this.textTranslatableKey = textKey;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.applyValue = applyValue;

        updateMessage();
    }

    private static double normalizeValue(int actualValue, int minValue, int maxValue) {
        if (minValue >= maxValue) throw new IllegalArgumentException("Invalid slider range");

        return (double) (Mth.clamp(actualValue, minValue, maxValue) - minValue) / (maxValue - minValue);
    }

    public int getActualValue() { return minValue + (int) Math.round(value * (maxValue - minValue)); }

    public void setActualValue(int actualValue) {
        updatingRemotely = true;

        try {
            setValue(normalizeValue(actualValue, minValue, maxValue));
        } finally {
            updatingRemotely = false;
        }
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.translatable(textTranslatableKey, getActualValue()));
    }

    @Override
    protected void applyValue() {
        if (!updatingRemotely) applyValue.accept(getActualValue());
    }
}