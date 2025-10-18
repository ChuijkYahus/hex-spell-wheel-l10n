package dev.drtheo.spellwheel.client.config;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.drtheo.spellwheel.SpellWheel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WheelIconsClientConfig {

    private static final String FILENAME = "hex-spell-wheel.txt";
    private static Path CONFIG_PATH;

    private static WheelIconsClientConfig INSTANCE;

    private final Map<String, Item> icons;

    public static void init(Path config) {
        CONFIG_PATH = config.resolve(FILENAME);

        ClientLifecycleEvent.CLIENT_STOPPING.register(instance -> {
            if (INSTANCE != null) INSTANCE.save();
        });

        ClientLifecycleEvent.CLIENT_STARTED.register(instance -> get());
    }

    private WheelIconsClientConfig() {
        this(new HashMap<>());
    }

    private WheelIconsClientConfig(Map<String, Item> map) {
        this.icons = map;
    }

    public static WheelIconsClientConfig get() {
        return INSTANCE == null ? INSTANCE = read() : INSTANCE;
    }

    public static WheelIconsClientConfig read() {
        try {
            Map<String, Item> icons = new HashMap<>();
            String prev = null;

            for (String s : Files.readString(CONFIG_PATH).lines().toArray(String[]::new)) {
                if (prev != null) {
                    icons.put(prev, BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s)));
                    prev = null;
                } else {
                    prev = s;
                }
            }

            return new WheelIconsClientConfig(icons);
        } catch (Exception e) {
            SpellWheel.LOGGER.error("Failed to read config", e);
            return new WheelIconsClientConfig();
        }
    }

    public void save() {
        try {
            if (Files.notExists(CONFIG_PATH))
                Files.createFile(CONFIG_PATH);

            StringBuilder result = new StringBuilder();

            icons.forEach((s, item) -> result.append(s).append('\n')
                    .append(item.arch$registryName()).append('\n'));

            Files.writeString(CONFIG_PATH, result.toString());
        } catch (Exception e) {
            SpellWheel.LOGGER.error("Failed to save config", e);
        }
    }

    public Item getIconOr(@Nullable Component name, Item def) {
        return Optional.ofNullable(name).map(Component::getString)
                .flatMap(s -> Optional.ofNullable(icons.get(s)))
                .orElse(def);
    }

    public void setIcon(Component name, Item item) {
        icons.put(name.getString(), item);
    }
}
