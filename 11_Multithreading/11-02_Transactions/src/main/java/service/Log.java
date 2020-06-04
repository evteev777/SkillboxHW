package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LogManager.getLogger("Log");

    public static void stackTrace() {
        LOGGER.debug(Thread.currentThread().getStackTrace()[2]);
    }

    public static void trace(String message) {
        LOGGER.trace(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(Exception e) {
        LOGGER.error(e);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(int message) {
        LOGGER.error(message);
    }
}
