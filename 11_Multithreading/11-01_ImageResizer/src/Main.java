
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
            Arrays.sort(allFiles, Comparator.comparing(File::length).reversed());
            List<List<File>> fileLists = splitFileList(allFiles, threadsCount);

            long start = System.currentTimeMillis();

            startThreads(fileLists, threadsCount, newWidth);

            System.out.println("Duration after all threads termination: " +
                    (System.currentTimeMillis() - start) + " ms");
        } else {
            System.out.println(SRC_FOLDER + " is empty");
        }
    }

    private static List<List<File>> splitFileList(File[] files, int partsCount) {
        List<List<File>> fileLists = new ArrayList<>();

        for (int i = 0; i < partsCount; i++) {
            fileLists.add(new ArrayList<>());
        }
        int partNum = 0;
        for (File file : files) {
            fileLists.get(partNum).add(file);
            partNum++;
            if (partNum == partsCount)
                partNum = 0;
        }
        return fileLists;
    }

    private static void startThreads(List<List<File>> fileLists, int threadsCount, int newWidth)
            throws InterruptedException {

        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < threadsCount; i++) {
            int threadNumber = i;
            threads.add(new Thread(() -> {
                List<File> fileList = fileLists.get(threadNumber);
                for (File file : fileList) {
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
            long convertStart = System.currentTimeMillis();

            BufferedImage newImage = scalrComverter(image, newWidth);
//            BufferedImage newImage = habrArticleConverter(image, newWidth);

            File newFile = new File(DST_FOLDER + "/" + formatFileName(file));
            ImageIO.write(newImage, "jpg", newFile);

            long duration = System.currentTimeMillis() - convertStart;
            print(file, image, newFile, newImage, duration);

        }
    }

    private static BufferedImage scalrComverter(BufferedImage image, int imageSize) {
        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY,
                imageSize, Scalr.OP_ANTIALIAS);
    }

//    private static BufferedImage habrArticleConverter(BufferedImage image, int newWidth) {
//        int newHeight = (int) Math.round(
//                image.getHeight() / (image.getWidth() / (double) newWidth)
//        );
//        BufferedImage newImage = new BufferedImage(
//                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
//        );
//
//        int widthStep = image.getWidth() / newWidth;
//        int heightStep = image.getHeight() / newHeight;
//
//        for (int x = 0; x < newWidth; x++) {
//            for (int y = 0; y < newHeight; y++) {
//                int rgb = image.getRGB(x * widthStep, y * heightStep);
//                newImage.setRGB(x, y, rgb);
//            }
//        }
//        return newImage;
//    }

    private static String formatFileName(File file) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        return (fileName.length() <= 8 ? fileName : fileName.substring(0, 8) + "~") + ".jpg";
    }

    private static void print(File file, BufferedImage image, File newFile, BufferedImage newImage, long duration) {
        System.out.printf("Converted:\tThread %s\t%-13s\t%6s kb\t%s x %s px\t-> \t%-12s\t%6s kb\t%s x %s px\t%3s ms%n",
                Thread.currentThread().getId(),
                formatFileName(file), file.length() / 1024, image.getWidth(), image.getHeight(),
                formatFileName(newFile), newFile.length() / 1024, newImage.getWidth(), newImage.getHeight(),
                duration);
    }
}
