package core;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String SRC_FOLDER = "src/main/resources/images/src";
    private static final String DST_FOLDER = "src/main/resources/images/dst";

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {

        convertAllImages(300, 1);
        convertAllImages(300, PROCESSORS);
    }

    private static void convertAllImages(int newWidth, int threadsCount)
            throws InterruptedException {

        threadsCount = Math.min(threadsCount, PROCESSORS);

        Log.debug("Conversion start");
        Log.info("\nImage conversion in progress. Please wait...");
        Log.info(String.format("%nTHREADS COUNT: %d", threadsCount));

        File[] allFiles = new File(SRC_FOLDER).listFiles();

        if (allFiles != null) {
            // Sorting - for even distribution files of different sizes by threads
            Arrays.sort(allFiles, Comparator.comparing(File::length));

            List<List<File>> files = splitAllFiles(allFiles, threadsCount);

            long start = System.currentTimeMillis();

            convertFilesInThreads(files, newWidth);

            Log.info(String.format("Duration after all threads termination: %d ms",
                    (System.currentTimeMillis() - start)));
            Log.debug("Conversion finish\n");
        } else {
            Log.warn(String.format("Directory %s is empty", SRC_FOLDER));
        }
    }

    private static List<List<File>> splitAllFiles(File[] files, int partsCount) {

        List<List<File>> lists = new ArrayList<>();

        for (int i = 0; i < partsCount; i++) {
            lists.add(new ArrayList<>());
        }

        int partNumber = 0;

        for (File file : files) {
            lists.get(partNumber).add(file);

            partNumber++;
            if (partNumber == partsCount)
                partNumber = 0;
        }
        return lists;
    }

    private static void convertFilesInThreads(List<List<File>> files, int newWidth)
            throws InterruptedException {

        List<Thread> threads = new LinkedList<>();
        for(List<File> fileList : files) {

            threads.add(new Thread(() -> {
                for (File file : fileList) {
                    try {
                        convertImageScalr(file, newWidth);
                        convertImageHabr(file, newWidth);
                    } catch (IOException e) {
                        Log.error(e);
                    }
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void convertImageScalr(File file, int newWidth) throws IOException {

        BufferedImage image = ImageIO.read(file);

        if (image != null) {
            long startTime = System.currentTimeMillis();

            BufferedImage newImageScalr =
                    Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, newWidth, Scalr.OP_ANTIALIAS);

            File newFileScalr = new File(DST_FOLDER + "/S-" + formatFileName(file));
            ImageIO.write(newImageScalr, "jpg", newFileScalr);

            long duration = System.currentTimeMillis() - startTime;
            Log.convertInfo(file, image, newFileScalr, newImageScalr, duration);
        }
    }

    private static void convertImageHabr(File file, int newWidth) throws IOException {
        long startTime = System.currentTimeMillis();

        BufferedImage image = ImageIO.read(file);

        if (image != null) {

            BufferedImage newImageHabr = converterHabr(image, newWidth);

            File newFileHabr = new File(DST_FOLDER + "/H-" + formatFileName(file));
            String formatName = (newImageHabr.getType() == 5) ? "jpg" : "png";
            ImageIO.write(newImageHabr, formatName, newFileHabr);

            long duration = System.currentTimeMillis() - startTime;
            Log.convertInfo(file, image, newFileHabr, newImageHabr, duration);
        }
    }

    private static BufferedImage converterHabr(BufferedImage image, int newWidth) {


        int width = image.getWidth();

        if (width < newWidth) {
            Log.debug(String.format("Original width %d px is lower %d px", width, newWidth));
        } else if (width == newWidth) {
            Log.debug(String.format("Image width is already %d px, no need to convert", width));
        } else {

            int scale = 0;
            int tempWidth = width;
            while (tempWidth / newWidth > 1) {
                tempWidth /= 2;
                scale++;
            }

            HashSet<Integer> availableWidths = new HashSet<>();
            for (int i = 1; i < 8; i++) {
                availableWidths.add(newWidth * (int) Math.pow(2, i));
            }

            if (!availableWidths.contains(width)) {
                image = resizeHabr(image, newWidth, scale);
                Log.debug(String.format("Resize - prepare:\t%5s px\t->\t %5s px",
                        width, image.getWidth()));
            } else Log.debug(String.format("Resize - prepare:\t%5s px in set %s, no need to prepare",
                    width, availableWidths));

            for (int i = 0; i < scale; i++) {
                tempWidth = image.getWidth();
                image = resizeHabr(image, newWidth, (scale - 1) - i);
                Log.debug(String.format("Resize - step %s:  \t%5s px\t->\t %5s px",
                        i, tempWidth, image.getWidth()));
            }
        }
        return image;
    }

    private static BufferedImage resizeHabr(BufferedImage image, int newWidth, int scale) {

        double multiply = newWidth * Math.pow(2, scale) / image.getWidth();
        Log.debug(String.format("Multiply: %s", multiply));

        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(multiply, multiply),
                AffineTransformOp.TYPE_BILINEAR);

        return op.filter(image, null);
    }

    static String formatFileName(File file) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
        return (fileName.length() <= 8 ? fileName : fileName.substring(0, 8) + "~") + ".jpg";
    }
}
