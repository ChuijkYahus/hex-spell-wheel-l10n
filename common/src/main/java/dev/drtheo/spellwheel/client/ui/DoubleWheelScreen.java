package dev.drtheo.spellwheel.client.ui;

import dev.drtheo.spellwheel.client.config.WheelClientConfig;
import dev.drtheo.spellwheel.client.ui.action.OpenAction;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;

public class DoubleWheelScreen extends AbstractWheelScreen {

    private final WidgetSet outer;
    private final WidgetSet inner;

    public DoubleWheelScreen(WidgetSet outer, WidgetSet inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    protected void init() {
        WheelClientConfig config = WheelClientConfig.get();

        outer.forEach((slot, widget) -> addRenderableWidget(new WheelOptionWidget(
                slot.getX(width), slot.getY(height), widget, slot.getXOffset(), slot.getYOffset(), config.outerRingSize, config.outerRingSpread, true)));

        inner.forEach((slot, widget) -> addRenderableWidget(new WheelOptionWidget(
                slot.getX(width), slot.getY(height), widget, slot.getXOffset(), slot.getYOffset(), config.innerRingSize, config.innerRingSpread, false)));
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (super.keyPressed(i, j, k))
            return true;

        KeyMapping[] hotbar = minecraft.options.keyHotbarSlots;

        for (int l = 0; l < hotbar.length; l++) {
            if (hotbar[l].matches(i, j)) {
//                this.simulateClick(l);
                return true;
            }
        }

        return false;
    }

//    public void simulateClick(int index) {
//        Widget widget = this.widgets.get(index);
//
//        if (widget == null)
//            return;
//
//        this.click(widget);
//    }

    public static AbstractWheelScreen tryCreate(CompoundTag iotas, MutableComponent[] names, int maxPage, int page) {
        if (maxPage <= WidgetSet.SET_SIZE)
            return SingleWheelScreen.tryCreate(iotas, names, maxPage, page);

        Widget[] outer = WheelScreen.buildPages(iotas, names, WheelClientConfig.get().openLast ? page - 1 : 0, maxPage);
        Widget[] inner = buildChapters(iotas, names, maxPage);

        return new DoubleWheelScreen(WidgetSet.create(outer), WidgetSet.create(inner));
    }

    private static Widget[] buildChapters(CompoundTag iotas, MutableComponent[] names, int maxPage) {
        return WheelScreen.buildChapters((i,n,o,m)
                -> OpenAction.double0(() -> WheelScreen.buildPages(i,n,o,m),
                () -> buildChapters(i,n,m)), iotas, names, maxPage
        );
    }
}