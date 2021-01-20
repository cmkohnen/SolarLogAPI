package me.meloni.SolarLogAPI.Handling;

/**
 * This Class is called when the jar package is executed via runtime.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class Runtime {
    /**
     * This function is called, when the jar package is executed via runtime.
     * @param args arguments passed by runtime
     */
    public static void main(String[] args) {
        Logger.info(Translation.get("runtime_error_message"));
    }
}
