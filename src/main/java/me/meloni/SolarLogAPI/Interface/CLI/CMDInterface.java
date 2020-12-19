package me.meloni.SolarLogAPI.Interface.CLI;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Interface.BasicUI.BasicConversion;
import me.meloni.SolarLogAPI.Interface.BasicUI.GraphCustomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CMDInterface {
    private static final String PREFIX = "[SolarLog] ";
    private static final String DEBUG_PREFIX = "[DEBUG]     ";

    public static void run(String[] args) {
        long start = System.currentTimeMillis();
        List<String> argsAsList = new ArrayList<>(Arrays.asList(args));
        if(argsAsList.contains("--debug")) {
            Logger.setEnabled(true);
        }

        info("-----------------------< SolarLogAPI >------------------------");
        log("Used arguments: " + Arrays.toString(args));

        cli(args);

        //END
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        info("--------------------------------------------------------------");
        info("Total time: " + timeElapsed);
        info("Finished at: " + new Date(finish).toString());
        info("--------------------------------------------------------------");
    }

    private static void cli(String[] args) {
        if(args[0].equalsIgnoreCase("convert")) {
            if(args[1].equalsIgnoreCase("gui")) {
                info("converting from GUI");
                BasicConversion.run();
            }
        } else if(args[0].equalsIgnoreCase("visualize")) {
            if(args[1].equalsIgnoreCase("gui")) {
                info("visualizing from GUI");
                GraphCustomizer.run();
            }
        }
    }

    private static void info(Object o) {
        Logger.info(PREFIX + o);
    }

    private static void log(Object o) {
        Logger.log(DEBUG_PREFIX + o);
    }
}
