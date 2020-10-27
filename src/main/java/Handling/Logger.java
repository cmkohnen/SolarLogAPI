package Handling;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;


/**
 * This Class includes functions to log objects to some form of output.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Logger {


    protected Logger() {
    }

    public static void log(Object o){
        System.out.println(o);
    }
}
