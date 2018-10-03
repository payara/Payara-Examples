package webinar.misc;

import java.util.logging.Logger;

public class Log {
    public static void info(String message) {
        String callingClassName = Thread.currentThread().getStackTrace()[1].getClassName();
        Logger.getLogger(callingClassName).info(message);
    }
}
