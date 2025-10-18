package dev.drtheo.spellwheel.client.config;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.drtheo.spellwheel.SpellWheel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class WheelClientConfig {

    private static final String FILENAME = "hex-spell-wheel.properties";
    private static Path CONFIG_PATH;

    private static WheelClientConfig INSTANCE;

    public static void init(Path config) {
        CONFIG_PATH = config.resolve(FILENAME);

        ClientLifecycleEvent.CLIENT_STOPPING.register(instance -> {
            if (INSTANCE != null) INSTANCE.save();
        });

        ClientLifecycleEvent.CLIENT_STARTED.register(instance -> get());
    }

    private static float getFloat(Properties properties, String path, float def) {
        try {
            String res = (String) properties.get(path);
            return res == null ? def : Float.parseFloat(res);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static void setFloat(Properties properties, String path, float val) {
        properties.setProperty(path, String.valueOf(val));
    }

    private static int getInt(Properties properties, String path, int def) {
        try {
            String res = (String) properties.get(path);
            return res == null ? def : Integer.parseInt(res);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static void setInt(Properties properties, String path, int val) {
        properties.setProperty(path, String.valueOf(val));
    }

    private static boolean getBool(Properties properties, String path, boolean def) {
        try {
            String res = (String) properties.get(path);
            return res == null ? def : Boolean.parseBoolean(res);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static void setBool(Properties properties, String path, boolean val) {
        properties.setProperty(path, String.valueOf(val));
    }

    public static final boolean COMPACT_DEFAULT = true;
    public static final boolean OPEN_LAST_DEFAULT = true;

    public static final float OUTER_RING_SPREAD_DEFAULT = 1.87f;
    public static final int OUTER_RING_SIZE_DEFAULT = 32;

    public static final float INNER_RING_SPREAD_DEFAULT = -1.63f;
    public static final int INNER_RING_SIZE_DEFAULT = 24;

    public boolean compact = COMPACT_DEFAULT;
    public boolean openLast = OPEN_LAST_DEFAULT;

    public float outerRingSpread = OUTER_RING_SPREAD_DEFAULT;
    public int outerRingSize = OUTER_RING_SIZE_DEFAULT;

    public float innerRingSpread = INNER_RING_SPREAD_DEFAULT;
    public int innerRingSize = INNER_RING_SIZE_DEFAULT;

    private WheelClientConfig() { }

    private WheelClientConfig(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);

        this.fromProps(properties);
    }

    private void fromProps(Properties properties) {
        this.compact = getBool(properties, "compact", COMPACT_DEFAULT);
        this.openLast = getBool(properties, "open_last", OPEN_LAST_DEFAULT);

        this.outerRingSpread = getFloat(properties, "outerRingSpread", OUTER_RING_SPREAD_DEFAULT);
        this.outerRingSize = getInt(properties, "outerRingSize", OUTER_RING_SIZE_DEFAULT);

        this.innerRingSpread = getFloat(properties, "innerRingSpread", INNER_RING_SPREAD_DEFAULT);
        this.innerRingSize = getInt(properties, "innerRingSize", INNER_RING_SIZE_DEFAULT);
    }

    private Properties toProps() {
        Properties properties = new Properties();

        setBool(properties, "compact", compact);
        setBool(properties, "open_last", openLast);

        setFloat(properties, "outerRingSpread", outerRingSpread);
        setInt(properties, "outerRingSize", outerRingSize);

        setFloat(properties, "innerRingSpread", innerRingSpread);
        setInt(properties, "innerRingSize", innerRingSize);

        return properties;
    }

    public static WheelClientConfig get() {
        return INSTANCE == null ? INSTANCE = read() : INSTANCE;
    }

    private static WheelClientConfig read() {
        try (InputStream is = Files.newInputStream(CONFIG_PATH)) {
            return new WheelClientConfig(is);
        } catch (Exception e) {
            SpellWheel.LOGGER.error("Failed to read config", e);
            return new WheelClientConfig();
        }
    }

    public void save() {
        try {
            if (Files.notExists(CONFIG_PATH))
                Files.createFile(CONFIG_PATH);

            try (OutputStream os = Files.newOutputStream(CONFIG_PATH)) {
                this.toProps().store(os, null);
            }
        } catch (Exception e) {
            SpellWheel.LOGGER.error("Failed to save config", e);
        }
    }
}
