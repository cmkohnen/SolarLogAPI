package Handling;


/**
 * This Class includes functions to log objects to some form of output.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Logger {

    private static boolean booleanMissing = false;

    public Logger() {
    }

    public static void logWithIncomingBoolean(Object o) {
        booleanMissing = true;
        System.out.print(o);
    }
    public static void logTheBoolean(Boolean b) {
        if(booleanMissing) {
            booleanMissing = false;
            if(b) {
                System.out.print("    yes\n");
            } else {
                System.out.print("    no\n");
            }
        }
    }

    public static void log(Object o){
        System.out.println(o);
    }
}
