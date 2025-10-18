package dev.drtheo.spellwheel.client;

import dev.drtheo.spellwheel.client.config.WheelClientConfig;
import dev.drtheo.spellwheel.client.config.WheelIconsClientConfig;

import java.nio.file.Path;

public final class SpellWheelClient {

    public static void init(Path config) {
        WheelClientConfig.init(config);
        WheelIconsClientConfig.init(config);

        WheelKeybinds.init();
    }
}
