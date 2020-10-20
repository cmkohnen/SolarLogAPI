package Handling;

/**
 * This Class includes functions to log objects to some form of output.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class CustomLogger {

    static boolean debug = false;
    public static void log(Object o){
        System.out.println(o);
    }
    public static void logdebug(Object o) {
        if(debug) {
            log(o);
        }
    }

    public static void setDebug(boolean b) {
        debug = b;
    }
}
