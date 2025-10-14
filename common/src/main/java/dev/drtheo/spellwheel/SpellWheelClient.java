package dev.drtheo.spellwheel;

import dev.drtheo.spellwheel.client.WheelKeybinds;
import dev.drtheo.spellwheel.client.config.WheelClientConfig;

import java.nio.file.Path;

public final class SpellWheelClient {

    public static void init(Path config) {
        WheelClientConfig.init(config);
        WheelKeybinds.init();
    }
}
