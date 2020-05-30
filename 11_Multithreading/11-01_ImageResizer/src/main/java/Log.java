
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;

public class Log {

    private static final Logger LOGGER = LogManager.getLogger("Log");

    static void convertInfo(File file, BufferedImage image, File newFile, BufferedImage newImage) {
        LOGGER.info(String.format(
                "Image converted:\tThread %s\t%-13s\t%6s kb\t%s x %s px\t-> \t%-12s\t%6s kb\t%s x %s px",
                Thread.currentThread().getId(),
                Main.formatFileName(file), file.length() / 1024,
                image.getWidth(), image.getHeight(),
                Main.formatFileName(newFile), newFile.length() / 1024,
                newImage.getWidth(), newImage.getHeight()));
    }

    static void debug() {
        LOGGER.debug("DEBUG POINT: \n" + Thread.currentThread().getStackTrace()[2]);
    }

    static void debug(String message) {
        LOGGER.debug(message);
    }

    static void debug(int message) {
        LOGGER.debug(message);
    }

    static void info() {
        LOGGER.info("INFO POINT: \n" + Thread.currentThread().getStackTrace()[2]);
    }

    static void info(String message) {
        LOGGER.info(message);
    }

    static void info(int message) {
        LOGGER.info(message);
    }

    static void warn() {
        LOGGER.warn("WARN POINT: \n" + Thread.currentThread().getStackTrace()[2]);
    }

    static void warn(String message) {
        LOGGER.warn(message);
    }

    static void warn(int message) {
        LOGGER.warn(message);
    }

    static void error() {
        LOGGER.error("ERROR POINT: \n" + Thread.currentThread().getStackTrace()[2]);
    }

    static void error(String message) {
        LOGGER.error(message);
    }

    static void error(int message) {
        LOGGER.error(message);
    }
}
