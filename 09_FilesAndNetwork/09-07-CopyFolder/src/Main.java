import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Enter source directory path :");
            File sourceDir = checkSourceDir(inputPath());

            System.out.println("Enter target directory path :");
            File targetDir = checkTargetDir(inputPath());

            FileUtils.copyDirectory(sourceDir, targetDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File inputPath() {

        String inputPath = new Scanner(System.in).nextLine().
                replace("\\", File.separator);
        return new File(inputPath);
    }

    private static File checkSourceDir(File sourceDir) {

        for (; ; ) {
            if (!sourceDir.isDirectory()) {
                System.out.println("It's not a directory. Enter correct path: ");
                sourceDir = inputPath();
            }
            else {
                return sourceDir;
            }
        }
    }

    private static File checkTargetDir(File targetDir) {

        for (; ; ) {
            if (targetDir.exists()) {
                System.out.println("Directory already exists. Enter another path: ");
                targetDir = inputPath();
            }
            else {
                return targetDir;
            }
        }
    }
}
