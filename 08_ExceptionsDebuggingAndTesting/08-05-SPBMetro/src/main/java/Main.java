import core.Line;
import core.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Logger logger;
    private static final Marker INPUT_OK = MarkerManager.getMarker("INPUT_OK");
    private static final Marker INPUT_ERROR = MarkerManager.getMarker("INPUT_ERROR");
    private static final Marker EXCEPTION = MarkerManager.getMarker("EXCEPTION");

    private static String dataFile = "08_ExceptionsDebuggingAndTesting/08-05-SPBMetro/src/main/resources/map.json";
    private static Scanner scanner;

    private static StationIndex stationIndex;

    public static void main(String[] args) {
        logger = LogManager.getRootLogger();
        logger.info("Запуск приложения" );

        RouteCalculator calculator = getRouteCalculator();

        System.out.println("Программа расчёта маршрутов метрополитена Санкт-Петербурга\n");

        try {
            scanner = new Scanner(System.in);
            for (; ; ) {
                Station from = takeStation("Введите станцию отправления:");
                logger.info(INPUT_OK, "Станция отправления: " + from.getName());

                Station to = takeStation("Введите станцию назначения:");
                logger.info(INPUT_OK, "Станция назначения: " + to.getName());

                List<Station> route = calculator.getShortestRoute(from, to);

                System.out.println("Маршрут:");
                logger.info("Проложен маршрут:" );
                printRoute(route);

                System.out.println("Длительность: " +
                        RouteCalculator.calculateDuration(route) + " минут");
                logger.info("Длительность маршрута: " +
                        RouteCalculator.calculateDuration(route) + " минут");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(EXCEPTION, "Ошибка: " + Arrays.toString(ex.getStackTrace()));
        }
    }

    private static RouteCalculator getRouteCalculator() {
        createStationIndex();
        return new RouteCalculator(stationIndex);
    }

    private static void printRoute(List<Station> route) {
        Station previousStation = null;
        for (Station station : route) {
            if (previousStation != null) {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if (!prevLine.equals(nextLine)) {
                    System.out.println("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                    logger.info("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());
            logger.info("\t" + station.getName());

            previousStation = station;
        }
    }

    private static Station takeStation(String message) {
        for (; ; ) {
            System.out.println(message);
            String line = scanner.nextLine().trim();
            Station station = stationIndex.getStation(line);
            if (station != null) {
                return station;
            }
            System.out.println("Станция не найдена :(");
            logger.info(INPUT_ERROR, "Станция не найдена: " + line);
        }
    }

    private static void createStationIndex() {
        stationIndex = new StationIndex();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);
        } catch (Exception ex) {
            logger.error(EXCEPTION, "Ошибка в JSON-файле: \n" +
                    Arrays.toString(ex.getStackTrace()).replaceAll(",", "\n"));
//            ex.printStackTrace();
        }
    }

    private static void parseConnections(JSONArray connectionsArray) {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                int lineNumber = ((Long) itemObject.get("line")).intValue();
                String stationName = (String) itemObject.get("station");

                Station station = stationIndex.getStation(stationName, lineNumber);
                if (station == null) {
                    String errMsg = "Ошибка: core.Station " + stationName + " on line " + lineNumber + " not found";
                    logger.error(EXCEPTION, errMsg);
                    throw new IllegalArgumentException(errMsg);
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations);
        });
    }

    private static void parseStations(JSONObject stationsObject) {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            int lineNumber = Integer.parseInt((String) lineNumberObject);
            Line line = stationIndex.getLine(lineNumber);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);
                line.addStation(station);
            });
        });
    }

    private static void parseLines(JSONArray linesArray) {
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((Long) lineJsonObject.get("number")).intValue(),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);
        });
    }

    private static String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            logger.error(EXCEPTION, "Не найден JSON-файл: " + ex.getMessage() + "\n" +
                    Arrays.toString(ex.getStackTrace()).replaceAll(",", "\n"));
//            ex.printStackTrace();
        }
        return builder.toString();
    }
}