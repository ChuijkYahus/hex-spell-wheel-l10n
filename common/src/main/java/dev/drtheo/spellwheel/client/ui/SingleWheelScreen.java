package dev.drtheo.spellwheel.client.ui;

import dev.drtheo.spellwheel.client.config.WheelClientConfig;
import dev.drtheo.spellwheel.client.ui.action.OpenAction;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;

public class SingleWheelScreen extends AbstractWheelScreen {

    private final WidgetSet widgets;

    public SingleWheelScreen(WidgetSet set) {
        this.widgets = set;
    }

    @Override
    protected void init() {
        WheelClientConfig config = WheelClientConfig.get();

        widgets.forEach((slot, widget) -> addRenderableWidget(new WheelOptionWidget(
                slot.getX(width), slot.getY(height), widget, slot.getXOffset(), slot.getYOffset(), config.outerRingSize, config.outerRingSpread, true)));
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (super.keyPressed(i, j, k))
            return true;

        KeyMapping[] hotbar = minecraft.options.keyHotbarSlots;

        for (int l = 0; l < hotbar.length; l++) {
            if (hotbar[l].matches(i, j)) {
                this.simulateClick(l);
                return true;
            }
        }

        return false;
    }

    public void simulateClick(int index) {
        Widget widget = this.widgets.get(index);

        if (widget == null)
            return;

        this.click(widget);
    }

    public static AbstractWheelScreen tryCreate(CompoundTag iotas, MutableComponent[] names, int maxPage, int page) {
        Widget[] widgets = maxPage > WidgetSet.SET_SIZE ? WheelScreen.buildChapters(
                (i,n,o,m) -> OpenAction.single(()
                        -> WheelScreen.buildPages(i,n,o,m)), iotas, names, maxPage)
                : WheelScreen.buildPages(iotas, names, /*WheelClientConfig.get().openLast ? page - 1 :*/ 0, maxPage);

        return new SingleWheelScreen(WidgetSet.create(widgets));
    }
}