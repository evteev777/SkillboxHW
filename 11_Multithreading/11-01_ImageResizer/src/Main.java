
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
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
        convertAllImages(300, PROCESSORS);
    }

    private static void convertAllImages(int newWidth, int threadsCount)
            throws InterruptedException {

        threadsCount = Math.min(threadsCount, PROCESSORS);
        System.out.println("\nThreads count: " + threadsCount + "\n-----");

        File[] allFiles = new File(SRC_FOLDER).listFiles();

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

            BufferedImage newImageScalr = comverterScalr(image, newWidth);
            File newFileScalr = new File(DST_FOLDER + "/S-" + formatFileName(file));
            ImageIO.write(newImageScalr, "jpg", newFileScalr);

            BufferedImage newImageHabr = converterHabr(image, newWidth);
            File newFileHabr = new File(DST_FOLDER + "/H-" + formatFileName(file));
            ImageIO.write(newImageHabr, "jpg", newFileHabr);

            print(file, image, newFileScalr, newImageScalr);
            print(file, image, newFileHabr, newImageHabr);
        }
    }

    private static BufferedImage comverterScalr(BufferedImage image, int imageSize) {
        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, imageSize, Scalr.OP_ANTIALIAS);
    }

    private static BufferedImage converterHabr(BufferedImage image, int newWidth) {

        int scale = 0;

        if (image.getWidth() != newWidth) {

            int width = image.getWidth();

            while (width / newWidth > 1) {
                width /= 2;
                scale++;
            }

            image = resizeHabr(image, newWidth, scale);
        }

        for (int i = 0; i < scale; i++) {
            image = resizeHabr(image, newWidth, (scale - 1) - i);
        }

        return image;
    }

    private static BufferedImage resizeHabr(BufferedImage image, int newWidth, int scale) {

        int width = newWidth * (int) Math.pow(2, scale);
        int height = (int) Math.round((double) width / image.getWidth() * image.getHeight());

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        double widthStep = (double) image.getWidth() / width;
        double heightStep = (double) image.getHeight() / height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB((int) (x * widthStep), (int) (y * heightStep));
                resizedImage.setRGB(x, y, rgb);
            }
        }

        return resizedImage;
    }

    private static String formatFileName(File file) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        return (fileName.length() <= 8 ? fileName : fileName.substring(0, 8) + "~") + ".jpg";
    }

    private static void print(File file, BufferedImage image, File newFile, BufferedImage newImage) {
        System.out.printf(
                "Converted:\tThread %s\t%-13s\t%6s kb\t%s x %s px\t-> \t%-12s\t%6s kb\t%s x %s px%n",
                Thread.currentThread().getId(),
                formatFileName(file), file.length() / 1024,
                image.getWidth(), image.getHeight(),
                formatFileName(newFile), newFile.length() / 1024,
                newImage.getWidth(), newImage.getHeight());
    }
}
