package dev.drtheo.spellwheel.client.ui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractWheelScreen extends Screen implements WheelScreen {

    protected AbstractWheelScreen() {
        super(Component.empty());
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (super.keyPressed(i, j, k))
            return true;

        if (minecraft.options.keyInventory.matches(i, j)) {
            super.onClose();
            return true;
        }

        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void click(Widget widget) {
        if (!widget.keepOpened() && !Screen.hasShiftDown())
            this.onClose();

        widget.run(minecraft);
    }

    @Override
    public void altClick(Widget widget) {
        widget.runAlt(minecraft);
    }
}
