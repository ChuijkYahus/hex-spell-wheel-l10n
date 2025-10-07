package dev.drtheo.spellwheel.client.ui.action;

import dev.drtheo.spellwheel.client.I18n;
import dev.drtheo.spellwheel.client.config.WheelClientConfig;
import dev.drtheo.spellwheel.client.ui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IconChangeAction implements Action {

    private final Component name;
    private final Item defIcon;
    private final int confirmColor;

    private boolean primed;

    public IconChangeAction(Component name, Item defIcon, int confirmColor) {
        this.name = name;
        this.defIcon = defIcon;
        this.confirmColor = confirmColor;
    }

    @Override
    public void runAlt(Minecraft client, Widget widget) {
        if (name == null) {
            client.player.displayClientMessage(I18n.noIcon(), true);
            return;
        }

        if (primed) {
            ItemStack stack = client.player.getMainHandItem();

            if (stack.isEmpty())
                stack = new ItemStack(defIcon);

            WheelClientConfig.get().setIcon(name, stack.getItem());
            widget.setPreview(stack);

            widget.setHoverColor(Widget.HOVER_COLOR);
            primed = false;
        } else {
            client.player.displayClientMessage(I18n.youSure(), true);

            widget.setHoverColor(confirmColor);
            primed = true;
        }
    }
}
