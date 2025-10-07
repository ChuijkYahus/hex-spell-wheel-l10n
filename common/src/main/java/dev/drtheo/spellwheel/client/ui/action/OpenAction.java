package dev.drtheo.spellwheel.client.ui.action;

import dev.drtheo.spellwheel.client.ui.WheelScreen;
import dev.drtheo.spellwheel.client.ui.Widget;
import dev.drtheo.spellwheel.client.ui.WidgetSet;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public record OpenAction(Supplier<WidgetSet> func) implements Action {

    public static OpenAction create(Supplier<Widget[]> supplier) {
        return new OpenAction(() -> WidgetSet.create(supplier.get()));
    }

    @Override
    public void run(Minecraft client, Widget widget) {
        client.setScreen(new WheelScreen(func.get()));
    }
}