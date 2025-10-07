package dev.drtheo.spellwheel.client.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class WheelOptionWidget extends Button {

    public final Widget widget;
    private final int xOffset;
    private final int yOffset;
    private float animationProgress = 0;

    protected WheelOptionWidget(int x, int y, Widget widget, int xOffset, int yOffset) {
        super(x, y, 32, 32, Component.empty(), button -> { }, DEFAULT_NARRATION);

        this.widget = widget;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.setTooltip(Tooltip.create(widget.label()));
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        PoseStack matrices = context.pose();
        matrices.pushPose();

        if (animationProgress < 1) animationProgress += (float) 0.25 * delta;
        matrices.translate(xOffset * animationProgress, yOffset * animationProgress, 0);
        isHovered = isMouseOver(mouseX, mouseY);
        renderButton(context);
        matrices.popPose();
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        float xOff = xOffset * animationProgress;
        float yOff = yOffset * animationProgress;
        return this.active && this.visible && mouseX >= (double) this.getX() + xOff && mouseY >= (double) this.getY() + yOff && mouseX < (double) (this.getX() + this.width + xOff) && mouseY < (double) (this.getY() + this.height + yOff);
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return isMouseOver(mouseX, mouseY);
    }

    protected void renderButton(GuiGraphics context) {
        boolean displayHovered = isHovered() && widget.actions() != null;
        int color = (180 << 24) | (displayHovered ? widget.hoverColor() : widget.currentColor());

        int x = getX();
        int y = getY();
        int contentX = x + 8;
        int contentY = y + 8;

        context.fill(x, y, x + width, y + height, color);
        ItemStack preview = widget.preview();

        context.renderItem(preview, contentX, contentY);
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        if (this.active && this.visible) {
            if (i == 0 || i == 1) {
                if (this.clicked(d, e)) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());

                    if (i == 0) this.onPress();
                    else this.onRightPress();

                    return true;
                }
            }
        }

        return false;
    }

    public void onRightPress() {
        Minecraft client = Minecraft.getInstance();

        if (client.screen instanceof WheelScreen wheelScreen)
            wheelScreen.rightClick(widget);
    }

    @Override
    public void onPress() {
        Minecraft client = Minecraft.getInstance();

        if (client.screen instanceof WheelScreen wheelScreen)
            wheelScreen.click(widget);
    }
}