
//import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String SRC_FOLDER = "images/src";
    private static final String DST_FOLDER = "images/dst";

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {

        convertAllImages(300, 1);
//        convertAllImages(300, PROCESSORS);
    }

    private static void convertAllImages(int newWidth, int threadsCount)
            throws InterruptedException {

        threadsCount = Math.min(threadsCount, PROCESSORS);
        System.out.println("\nThreads count: " + threadsCount + "\n-----");

        File[] allFiles = new File(SRC_FOLDER)
                .listFiles();

        if (allFiles != null) {
            // Sorting - for distribution files of different sizes by threads
            Arrays.sort(allFiles, Comparator.comparing(File::length));

            List<List<File>> files = splitAllFiles(allFiles, threadsCount);

            long start = System.currentTimeMillis();

            convertFilesInThreads(files, threadsCount, newWidth);

            System.out.println("Duration after all threads termination: " +
                    (System.currentTimeMillis() - start) + " ms");

        } else {
            System.out.println("Directory " + SRC_FOLDER + " is empty");
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

    private static void convertFilesInThreads(
            List<List<File>> files, int threadsCount, int newWidth)
            throws InterruptedException {

        List<Thread> threads = new LinkedList<>();

        for (int i = 0; i < threadsCount; i++) {
            int finalI = i;

            threads.add(new Thread(() -> {

                List<File> list = files.get(finalI);

                for (File file : list) {

                    try {
                        convertImage(file, newWidth);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
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

    private static void convertImage(File file, int newWidth) throws IOException {

        BufferedImage image = ImageIO.read(file);

        if (image != null) {

//            BufferedImage newImageScalr = comverterScalr(image, newWidth);
//            File newFileScalr = new File(DST_FOLDER + "/S-" + formatFileName(file));
//            ImageIO.write(newImageScalr, "jpg", newFileScalr);

            BufferedImage newImageHabr = converterHabr(image, newWidth);
            File newFileHabr = new File(DST_FOLDER + "/H-" + formatFileName(file));
            ImageIO.write(newImageHabr, "jpg", newFileHabr);

//            print(file, image, newFileScalr, newImageScalr);
            print(file, image, newFileHabr, newImageHabr);
        }
    }

//    private static BufferedImage comverterScalr(BufferedImage image, int imageSize) {
//        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, imageSize, Scalr.OP_ANTIALIAS);
//    }

    private static BufferedImage converterHabr(BufferedImage image, int newWidth) {


        int width = image.getWidth();

        if (width < newWidth) {
            System.out.println("Original width " + width + " px is lower " + newWidth + " px");
        } else if (width == newWidth) {
            System.out.println("Image width is already " + width + " px, no need to convert");
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
                System.out.printf("Resize - prepare:\t%5s px\t->\t %5s px%n", width, image.getWidth());
            } else System.out.printf("Resize - prepare:\t%5s px in set %s, no need to prepare %n",
                    width, availableWidths);

            for (int i = 0; i < scale; i++) {
                tempWidth = image.getWidth();
                image = resizeHabr(image, newWidth, (scale - 1) - i);
                System.out.printf("Resize - step %s:  \t%5s px\t->\t %5s px%n", i, tempWidth, image.getWidth());
            }
        }
        return image;
    }

    private static BufferedImage resizeHabr(BufferedImage image, int newWidth, int scale) {

        double multiply = newWidth * Math.pow(2, scale) / image.getWidth();
        System.out.println("Multiply: " + multiply);

        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(multiply, multiply),
                AffineTransformOp.TYPE_BILINEAR);

        return op.filter(image, null);
    }

    private static String formatFileName(File file) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        return (fileName.length() <= 8 ? fileName : fileName.substring(0, 8) + "~") + ".jpg";
    }

    private static void print(File file, BufferedImage image, File newFile, BufferedImage newImage) {
        System.out.printf(
                "Converted:\tThread %s\t%-13s\t%6s kb\t%s x %s px\t-> \t%-12s\t%6s kb\t%s x %s px%n-----%n",
                Thread.currentThread().getId(),
                formatFileName(file), file.length() / 1024,
                image.getWidth(), image.getHeight(),
                formatFileName(newFile), newFile.length() / 1024,
                newImage.getWidth(), newImage.getHeight());
    }
}
