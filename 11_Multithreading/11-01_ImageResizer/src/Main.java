
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String SRC_FOLDER = "images/src";
    private static final String DST_FOLDER = "images/dst";

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {

        convertAllImages(1);
        convertAllImages(PROCESSORS);
    }

    private static void convertAllImages(int threadsCount) throws InterruptedException {
        System.out.println("Threads count: " + threadsCount + "\n----------");

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        File[] files = new File(SRC_FOLDER).listFiles();
        long start = System.currentTimeMillis();

        if (files != null) {
            for (File file : files) {
                executorService.submit(() -> {
                    try {
                        convertImage(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        System.out.println("Duration before shutdown: " + (System.currentTimeMillis() - start) + " ms");

        executorService.shutdown();
        System.out.println("Duration after shutdown: " + (System.currentTimeMillis() - start) + " ms");

        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Duration after all threads termination: " +
                (System.currentTimeMillis() - start) + " ms\n");
    }

    private static void convertImage(File file) throws IOException {

        long convertStart = System.currentTimeMillis();

        BufferedImage image = ImageIO.read(file);

        int newWidth = 300;
        int newHeight = (int) Math.round(
                image.getHeight() / (image.getWidth() / (double) newWidth)
        );
        BufferedImage newImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        int widthStep = image.getWidth() / newWidth;
        int heightStep = image.getHeight() / newHeight;

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int rgb = image.getRGB(x * widthStep, y * heightStep);
                newImage.setRGB(x, y, rgb);
            }
        }

        File newFile = new File(DST_FOLDER + "/" + formatFileName(file));
        ImageIO.write(newImage, "jpg", newFile);

        long duration = System.currentTimeMillis() - convertStart;
        print(file, image, newFile, newImage, duration);
    }

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
