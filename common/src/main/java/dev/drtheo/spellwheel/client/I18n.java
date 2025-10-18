package dev.drtheo.spellwheel.client;

import dev.drtheo.spellwheel.SpellWheel;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public class I18n {

    public static MutableComponent page(@Nullable MutableComponent name, int index) {
        if (name == null)
            return Component.translatable("widget." + SpellWheel.MOD_ID + ".page", index);

        return name.copy().append(Component.translatable("widget." + SpellWheel.MOD_ID + ".page.suffix", index).withStyle(ChatFormatting.GRAY));
    }

    public static MutableComponent chapter(int index) {
        return Component.translatable("widget." + SpellWheel.MOD_ID + ".chapter", index + 1);
    }

    public static String key(String name) {
        return "key." + SpellWheel.MOD_ID + "." + name;
    }

    public static String keyCategory(String name) {
        return "category." + SpellWheel.MOD_ID + "." + name;
    }

    public static Component emptyWidget() {
        return Component.translatable("widget." + SpellWheel.MOD_ID + ".empty")
                .append(Component.literal("\n"))
                .append(Component.translatable("widget." + SpellWheel.MOD_ID + ".empty.desc").withStyle(ChatFormatting.GRAY));
    }

    public static MutableComponent numbered(int num, Component text) {
        return Component.empty().append(Component.literal(num + ". ").withStyle(ChatFormatting.GRAY)).append(text);
    }

    public static MutableComponent noIcon() {
        return Component.translatable("text." + SpellWheel.MOD_ID + ".no_icon").withStyle(ChatFormatting.RED);
    }

    public static MutableComponent youSure() {
        return Component.translatable("text." + SpellWheel.MOD_ID + ".you_sure").withStyle(ChatFormatting.YELLOW);
    }

    public static MutableComponent notArtifact(Component name) {
        return Component.translatable("text." + SpellWheel.MOD_ID + ".not_artifact", name).withStyle(ChatFormatting.RED);
    }

    public static class Config {

        public static MutableComponent title() {
            return Component.translatable("config." + SpellWheel.MOD_ID + ".title");
        }

        public static MutableComponent category() {
            return Component.translatable("config." + SpellWheel.MOD_ID + ".category");
        }

        public static MutableComponent entry(String path) {
            return Component.translatable("config." + SpellWheel.MOD_ID + "." + path);
        }

        public static MutableComponent entry(String category, String path) {
            return Component.translatable("config." + SpellWheel.MOD_ID + "." + category + "." + path);
        }
    }
}
