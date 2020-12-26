package me.meloni.SolarLogAPI.Handling;


/**
 * This Class includes functions to log objects to some form of output.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String INFO_LEVEL_1 = ANSI_PURPLE + " ===> ";
    public static final String INFO_LEVEL_2 = ANSI_GREEN + " >>> ";
    public static final String INFO_LEVEL_3 = ANSI_WHITE + "> ";

    private static boolean enabled = false;

    public Logger() {
    }

    public static void logWithoutBreakup(Object o) {
        System.out.print(o);
    }

    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }

    public static void log(Object o){
        if(enabled) {
            System.out.println(o);
        }
    }

    public static void info(Object o) {
        System.out.println(o);
    }
}
