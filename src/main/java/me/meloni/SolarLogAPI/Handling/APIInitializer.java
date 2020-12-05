package me.meloni.SolarLogAPI.Handling;

import me.meloni.SolarLogAPI.Interface.BasicUI.BasicConversion;
import me.meloni.SolarLogAPI.Interface.BasicUI.GraphCustomizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class is called when the jar package is executed via runtime.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    /**
     * This function is called, when the jar package is executed via runtime.
     * @param args arguments passed by runtime
     * @throws IOException exception caused by implemented ui
     */
    public static void main(String[] args) throws IOException {
        List<String> argsAsList = new ArrayList<>(Arrays.asList(args));

        Logger.log("API starts via Runtime Environment. Used Arguments: " + argsAsList);

        if(argsAsList.contains("simplegui")) {
            GraphCustomizer.run();
        }

        if(argsAsList.contains("convert")) {
            BasicConversion.run();
        }
    }

}
