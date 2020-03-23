import org.apache.commons.io.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            String sep = File.separator;

            final String FROM_URL = "https://lenta.ru";
//            final String FROM_FILE = "data/lenta_ru.html";
            final String TO_DIR = "data/downloads";

            Document doc = Jsoup.connect(FROM_URL).get();
//            Document doc = Jsoup.parse(readFile(FROM_FILE));
            Elements media = doc.select("[src]");

            String dirFileUtils = TO_DIR + sep + "FileUtils" + sep;
            String dirNIO = TO_DIR + sep + "NIO" + sep;
            String dirStream = TO_DIR + sep + "Stream" + sep;

            createDir(dirFileUtils);
            createDir(dirNIO);
            createDir(dirStream);

            for (Element src : media) {

                String link = src.attr("abs:src");
                if (link.contains("jpg") || link.contains("png")) {

                    downloadUsingFileUtils(link, dirFileUtils);
                    downloadUsingNIO(link, dirNIO);
                    downloadUsingStream(link, dirStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get(file));
        lines.forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }

    private static void downloadUsingFileUtils(String link, String dir) throws IOException {
        FileUtils.copyURLToFile(new URL(link), new File(pathName(dir, link)));
    }

    private static void downloadUsingNIO(String link, String dir) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(new URL(link).openStream());
        FileOutputStream fos = new FileOutputStream(new File(pathName(dir, link)));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        fos.close();
        rbc.close();
    }

    private static void downloadUsingStream(String link, String dir) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new URL(link).openStream());
        FileOutputStream fos = new FileOutputStream(new File(pathName(dir, link)));
        byte[] buffer = new byte[1024];
        int count;

        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fos.write(buffer, 0, count);
        }

        fos.close();
        bis.close();
    }

    private static void createDir(String dir) {
        File path = new File(dir);
        if (!path.exists()) {
            if (!path.mkdirs()) System.err.println("Error creating directory");
        }
    }

    private static String pathName(String dir, String link) {
        String fileName = link.replaceAll("(.+)(/.+)$", "$2");
        return dir + fileName;
    }
}
