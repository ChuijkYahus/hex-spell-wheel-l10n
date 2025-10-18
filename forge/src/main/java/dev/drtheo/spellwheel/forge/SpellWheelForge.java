package dev.drtheo.spellwheel.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.drtheo.spellwheel.SpellWheel;
import dev.drtheo.spellwheel.client.SpellWheelClient;
import dev.drtheo.spellwheel.client.config.WheelClientHandler;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SpellWheel.MOD_ID)
public final class SpellWheelForge {

    public SpellWheelForge(FMLJavaModLoadingContext context) {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(SpellWheel.MOD_ID, context.getModEventBus());

        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> WheelClientHandler.getConfigScreen(parent)));

        context.getModEventBus().addListener(this::onClient);

        // Run our common setup.
        SpellWheel.init();
    }

    private void onClient(FMLClientSetupEvent event) {
        SpellWheelClient.init(FMLPaths.CONFIGDIR.get());
    }
}
