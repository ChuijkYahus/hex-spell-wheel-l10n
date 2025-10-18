package dev.drtheo.spellwheel.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.drtheo.spellwheel.client.config.WheelClientHandler;

public class SpellWheelMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return WheelClientHandler::getConfigScreen;
    }
}
