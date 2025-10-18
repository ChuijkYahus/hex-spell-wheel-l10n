package dev.drtheo.spellwheel.client.config;

import dev.drtheo.spellwheel.client.I18n;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import net.minecraft.client.gui.screens.Screen;

public class WheelClientHandler {

    public static Screen getConfigScreen(Screen parent) {
        WheelClientConfig config = WheelClientConfig.get();

        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
                .setTitle(I18n.Config.title());

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory category = builder.getOrCreateCategory(I18n.Config.category());

        FieldBuilder<?,?, ?> entry;

        entry = entryBuilder.startBooleanToggle(I18n.Config.entry("compact"), config.compact)
                .setDefaultValue(WheelClientConfig.COMPACT_DEFAULT)
                .setSaveConsumer(b -> config.compact = b);
        category.addEntry(entry.build());

        entry = entryBuilder.startBooleanToggle(I18n.Config.entry("open_last"), config.openLast)
                .setDefaultValue(WheelClientConfig.OPEN_LAST_DEFAULT)
                .setSaveConsumer(b -> config.openLast = b);
        category.addEntry(entry.build());

        entry = entryBuilder.startFloatField(I18n.Config.entry("outer", "spread"), config.outerRingSpread)
                .setDefaultValue(WheelClientConfig.OUTER_RING_SPREAD_DEFAULT)
                .setSaveConsumer(f -> config.outerRingSpread = f);
        category.addEntry(entry.build());

        entry = entryBuilder.startIntSlider(I18n.Config.entry("outer", "size"), config.outerRingSize, 0, 256)
                .setDefaultValue(WheelClientConfig.OUTER_RING_SIZE_DEFAULT)
                .setSaveConsumer(i -> config.outerRingSize = i);
        category.addEntry(entry.build());


        entry = entryBuilder.startFloatField(I18n.Config.entry("inner", "spread"), config.innerRingSpread)
                .setDefaultValue(WheelClientConfig.INNER_RING_SPREAD_DEFAULT)
                .setSaveConsumer(f -> config.innerRingSpread = f);
        category.addEntry(entry.build());

        entry = entryBuilder.startIntSlider(I18n.Config.entry("inner", "size"), config.innerRingSize, 0, 256)
                .setDefaultValue(WheelClientConfig.INNER_RING_SIZE_DEFAULT)
                .setSaveConsumer(i -> config.innerRingSize = i);
        category.addEntry(entry.build());

        WheelClientConfig.get().save();
        return builder.build();
    }
}
