package dev.drtheo.spellwheel.client.util;

import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.storage.ItemSpellbook;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpellbookUtil {

    public static ItemStack getSpellbook(Player player) {
        ItemStack offStack = player.getOffhandItem();
        if (offStack.getItem() instanceof ItemSpellbook) return offStack;

        ItemStack mainStack = player.getMainHandItem();
        if (mainStack.getItem() instanceof ItemSpellbook) return mainStack;

        return null;
    }

    public static MutableComponent[] getPageNames(ItemStack spellBook) {
        CompoundTag names = NBTHelper.getCompound(spellBook, ItemSpellbook.TAG_PAGE_NAMES);
        int shiftedIdx = Math.max(1, ItemSpellbook.highestPage(spellBook));

        MutableComponent[] components = new MutableComponent[shiftedIdx];

        for (int i = 1; i < shiftedIdx + 1; i++) {
            String nameKey = String.valueOf(i);
            String name = NBTHelper.getString(names, nameKey);

            components[i - 1] = name == null ? null : Component.Serializer.fromJson(name);
        }

        return components;
    }
}
