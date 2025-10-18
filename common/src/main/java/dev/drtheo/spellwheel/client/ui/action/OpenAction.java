package dev.drtheo.spellwheel.client.ui.action;

import dev.drtheo.spellwheel.client.ui.DoubleWheelScreen;
import dev.drtheo.spellwheel.client.ui.SingleWheelScreen;
import dev.drtheo.spellwheel.client.ui.Widget;
import dev.drtheo.spellwheel.client.ui.WidgetSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Supplier;

public record OpenAction(Supplier<Screen> func) implements Action {

    public static OpenAction single(Supplier<Widget[]> supplier) {
        return new OpenAction(() -> new SingleWheelScreen(WidgetSet.create(supplier.get())));
    }

    public static OpenAction double0(Supplier<Widget[]> outer, Supplier<Widget[]> inner) {
        return new OpenAction(() -> new DoubleWheelScreen(WidgetSet.create(outer.get()), WidgetSet.create(inner.get())));
    }

    @Override
    public void run(Minecraft client, Widget widget) {
        client.setScreen(func.get());
    }
}