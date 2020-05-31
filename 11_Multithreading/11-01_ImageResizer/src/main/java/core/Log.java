package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LogManager.getLogger("Log");

    public static void convertInfo(
            File file, BufferedImage image, File newFile, BufferedImage newImage, long duration) {

        long threadId = Thread.currentThread().getId();

        String name = Main.formatFileName(file);
        long size = file.length() / 1024;
        int width = image.getWidth();
        int height = image.getHeight();

        String newName = Main.formatFileName(newFile);
        long newSize = newFile.length() / 1024;
        int newWidth = newImage.getWidth();
        int newHeight = newImage.getHeight();

        String message = String.format(
                "Image converted:\tThread %s:\t%-13s\t%6s kb\t%s x %s px\t-> \t%-12s\t%6s kb\t%s x %s px\t %-6d ms",
                threadId, name, size, width, height, newName, newSize, newWidth, newHeight, duration);

        LOGGER.info(message);
    }

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
