package me.meloni.SolarLogAPI.Interface.BasicUI;

/**
 * This Class includes a function to call a simple gui capable of transforming any data into persistent storage.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class BasicConversion {
    public static void run() {
        BasicSaveOptions.save(BasicSolarMapCustomizer.solarMap());
    }
}
