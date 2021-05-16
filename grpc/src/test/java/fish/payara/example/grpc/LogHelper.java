package fish.payara.example.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {

    private static final Logger logger = Logger.getLogger(RouteGuideClient.class.getName());

    public static void info(String msg, Object... params) {
        logger.log(Level.INFO, msg, params);
    }

    public static void warning(String msg, Object... params) {
        logger.log(Level.WARNING, msg, params);
    }
}
