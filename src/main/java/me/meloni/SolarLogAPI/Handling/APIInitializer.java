package me.meloni.SolarLogAPI.Handling;

import me.meloni.SolarLogAPI.Interface.CLI.CMDInterface;

/**
 * This Class is called when the jar package is executed via runtime.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    /**
     * This function is called, when the jar package is executed via runtime.
     * @param args arguments passed by runtime
     */
    public static void main(String[] args) {
        CMDInterface.run(args);
    }
}
