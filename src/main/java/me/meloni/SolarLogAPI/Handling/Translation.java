package me.meloni.SolarLogAPI.Handling;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Translation {
    private static Map<String, String> translation = null;

    private static void init() {
        setLanguage(Runtime.class.getClassLoader().getResourceAsStream("en.yml"));
    }

    public static void setLanguage(InputStream inputStream) {
        Yaml yaml = new Yaml();
        translation = yaml.load(inputStream);
    }

    public static String get(String key) {
        if(translation == null) {
            init();
        }
        return translation.get(key.toLowerCase());
    }

    public static Map<String, String> getTranslation() {
        if(translation == null) {
            init();
        }
        return translation;
    }
}
