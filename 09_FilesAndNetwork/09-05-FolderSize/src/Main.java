import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {

    public static void main(String[] args) {

        try {
            File folderName;

            for (; ; ) {
                System.out.println("Enter folder path :");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String inputPath = reader.readLine().replace("\\", File.separator);

                folderName = new File(inputPath);
                if (folderName.isDirectory()) {
                    break;
                }
                System.out.println("Enter correct folder path!");
            }
            String folderSize = sizeConverter(getFoldersSize(folderName));
            System.out.println("\nFolder size " + folderName + "\t:\t" + folderSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getFoldersSize(File folder) {

        final long[] foldersSize = {0};

        try {
            Files.walkFileTree(folder.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                      foldersSize[0] += file.toFile().length();
//                    System.out.printf("Visiting file %s\n", file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    System.err.printf("Visiting failed, skip: %9s (???) bytes\t- %s\n", file.toFile().length(), file);
                    return FileVisitResult.SKIP_SUBTREE;
                }

//                @Override
//                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
//                    System.out.printf("About to visit directory %s\n", dir);
//                    return FileVisitResult.CONTINUE;
//                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return foldersSize[0];
    }

    static String sizeConverter(long originalSize) {

        // Находим индекс единиц измерения: 0-bytes, 1-Kb, 2-Mb, 3-Gb, 4-Tb, 5-Pb
        final String[] units = {" bytes", " Kb", " Mb", " Gb", " Tb", " Pb"};
        int unitIndex = 0;

        double tempSize = (double) originalSize / 1024;

        while (tempSize >= 1) {
            tempSize = tempSize / 1024;
            unitIndex++;
        }

        if (unitIndex == 0) { // Байты возвращаем без изменения
            return originalSize + units[unitIndex];
        }
        else { // более крупные единицы возвращаем в виде дробных величин c одним знаком после запятой
            return (Math.round(10 * originalSize / (Math.pow(1024, (unitIndex)))) / 10.0) + units[unitIndex];
        }
    }
}
