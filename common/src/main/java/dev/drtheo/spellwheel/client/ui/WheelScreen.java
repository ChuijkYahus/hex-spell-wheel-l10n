package dev.drtheo.spellwheel.client.ui;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.storage.ItemSpellbook;
import dev.drtheo.spellwheel.client.I18n;
import dev.drtheo.spellwheel.client.config.WheelClientConfig;
import dev.drtheo.spellwheel.client.config.WheelIconsClientConfig;
import dev.drtheo.spellwheel.client.ui.action.IconChangeAction;
import dev.drtheo.spellwheel.client.ui.action.OpenAction;
import dev.drtheo.spellwheel.client.ui.action.SwitchPageAction;
import dev.drtheo.spellwheel.client.util.SpellbookUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface WheelScreen {

    void click(Widget widget);
    void altClick(Widget widget);

    static Optional<Screen> tryCreate(Minecraft client) {
        ItemStack spellBook = SpellbookUtil.getSpellbook(client.player);
        if (spellBook == null) return Optional.empty();

        int maxPage = ItemSpellbook.highestPage(spellBook);
        if (maxPage == 0) return Optional.empty();

        CompoundTag iotas = NBTHelper.getCompound(spellBook, ItemSpellbook.TAG_PAGES);
        MutableComponent[] names = SpellbookUtil.getPageNames(spellBook);

        int page = ItemSpellbook.getPage(spellBook, -1);

        return Optional.of(WheelClientConfig.get().compact
                ? DoubleWheelScreen.tryCreate(iotas, names, maxPage, page)
                : SingleWheelScreen.tryCreate(iotas, names, maxPage, page));
    }

    static Widget[] buildChapters(PageBuilder builder, CompoundTag iotas, MutableComponent[] names, int maxPages) {
        int chapters = Math.min((int) Math.ceil(maxPages / (double) WidgetSet.SET_SIZE), WidgetSet.SET_SIZE);
        Widget[] widgets = new Widget[chapters];

        for (int i = 0; i < chapters; i++) {
            int pageOffset = i * WidgetSet.SET_SIZE;
            int maxChapterPage = Math.min(pageOffset + WidgetSet.SET_SIZE, maxPages);

            MutableComponent label = I18n.chapter(i);

            for (int j = pageOffset; j < maxChapterPage; j++) {
                int page = j + 1;

                if (!iotas.contains(String.valueOf(page)))
                    continue;

                label = label.append(Component.literal("\n - ")
                        .append(I18n.page(names[j], page))
                        .withStyle(ChatFormatting.GRAY));
            }

            label = I18n.numbered(i + 1, label);
            widgets[i] = new Widget(label, Items.BOOK, builder.createPages(iotas, names, pageOffset, maxPages));
        }

        return widgets;
    }

    @FunctionalInterface
    interface PageBuilder {
        OpenAction createPages(CompoundTag iotas, MutableComponent[] names, int offset, int maxPages);
    }

    static Widget[] buildPages(CompoundTag iotas, MutableComponent[] names, int start, int maxPages) {
        Widget[] widgets = new Widget[maxPages - start];

        for (int i = start; i < maxPages; i++) {
            int page = i + 1;
            CompoundTag tag = (CompoundTag) iotas.get(String.valueOf(page));

            if (tag == null)
                continue;

            widgets[i - start] = createPageWidget(names[i], tag, page, start);
        }

        return widgets;
    }

    Item DEFAULT_ICON = Items.PAPER;

    private static Widget createPageWidget(@Nullable MutableComponent name, @Nullable CompoundTag iotas, int page, int offset) {
        MutableComponent label = I18n.numbered(page - offset, I18n.page(name, page));
        Item icon = WheelIconsClientConfig.get().getIconOr(name, DEFAULT_ICON);

        if (iotas != null) {
            Component displayIota = IotaType.getDisplay(iotas);
            label.append("\n").append(Component.translatable("hexcasting.spelldata.onitem", displayIota));
        }

        return new Widget(label, icon, new SwitchPageAction(page), new IconChangeAction(name, DEFAULT_ICON, 0x7f0000));
    }
}
