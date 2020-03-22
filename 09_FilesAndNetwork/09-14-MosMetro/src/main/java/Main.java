import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.Line;
import core.Station;
import core.StationIndex;
import json.StationIndexSerializer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            final String LINK = "https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена";
            final String PATH = "09_FilesAndNetwork/09-14-MosMetro/src/main/resources/mosmetro.json";
            final File file = new File(PATH);

            StationIndex mosMetro = parseHTML(LINK);
            stationIndexToJSON(file, mosMetro);

            StationIndex mosMetroNEW = new StationIndex();
            stationIndexFromJSON(PATH, mosMetroNEW);

            System.out.println("БЫЛО:");
            mosMetro.getLines().forEach((s, line) ->
                            System.out.printf("%-36s \t - %2s станций\n", line, line.getStations().size()));

            System.out.println("\nСТАЛО:");
            mosMetroNEW.getLines().forEach((s, line) ->
                    System.out.printf("%-36s \t - %2s станций\n", line, line.getStations().size()));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static StationIndex parseHTML(String link) throws IOException {
        StationIndex sIndex;
        Document fromLink = Jsoup.connect(link).userAgent("Mozilla").maxBodySize(0).get();
        sIndex = new StationIndex(fromLink);
        return sIndex;
    }

    private static void stationIndexToJSON(File file, StationIndex mosMetro) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(StationIndex.class, new StationIndexSerializer())
                .create();
        FileWriter writer = new FileWriter(file, false);
        writer.write(gson.toJson(mosMetro));
        writer.flush();
        writer.close();
    }

    private static void stationIndexFromJSON(String path, StationIndex stationIndex2)
            throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));

        JSONArray linesArray = (JSONArray) jsonData.get("lines");
        parseLinesFromJSON(linesArray, stationIndex2);

        JSONArray stationsArray = (JSONArray) jsonData.get("stations");
        parseStationsFromJSON(stationsArray, stationIndex2);
    }

    private static String getJsonFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get(path));
        lines.forEach(builder::append);
        return builder.toString();
    }

    private static void parseLinesFromJSON(JSONArray linesArray, StationIndex stationIndex2) {
        for(Object lineObject : linesArray) {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    (String) lineJsonObject.get("number"),
                    (String) lineJsonObject.get("name")
            );
            stationIndex2.addLine(line);
        }
    }

    private static void parseStationsFromJSON(JSONArray stationsArray, StationIndex stationIndex2) {
        for(Object stationObject : stationsArray) {
            JSONObject stationJsonObject = (JSONObject) stationObject;

            LocalDate closingDate = null;
            if(stationJsonObject.get("closingDate") != null) {
                closingDate = LocalDate.parse(stationJsonObject.get("closingDate").toString());
            }
            Station station = new Station(
                    (String) stationJsonObject.get("name"),
                    (String) stationJsonObject.get("lineNumber"),
                    (boolean) stationJsonObject.get("isClosed"),
                    closingDate
            );
            stationIndex2.addStation(station);
        }
    }
}
