package dev.drtheo.spellwheel.fabric.client;

import dev.drtheo.spellwheel.SpellWheelClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class SpellWheelFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpellWheelClient.init(FabricLoader.getInstance().getConfigDir());
    }
}
