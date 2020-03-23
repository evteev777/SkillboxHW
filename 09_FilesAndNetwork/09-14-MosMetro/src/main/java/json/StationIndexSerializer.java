package json;

import com.google.gson.*;
import core.Line;
import core.Station;
import core.StationIndex;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class StationIndexSerializer implements JsonSerializer<StationIndex> {

    @Override
    public JsonElement serialize(StationIndex src, Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();

        stationsToJSON(src, result);
        linesToJSON(src, result);
        connectionsToJSON(src, result);
        nodesToJSON(src, result);
        return result;
    }

    private void stationsToJSON(StationIndex src, JsonObject result) {
        JsonArray stations = new JsonArray();
        result.add("stations", stations);

        for (Line line : src.getLines().values()) {
            for (Station station : line.getStations()) {
                JsonObject stationJson = new JsonObject();

                stationJson.addProperty("name", station.getName());
                stationJson.addProperty("lineNumber", station.getLineNumber());
                stationJson.addProperty("isClosed", station.getIsClosed());
                if (station.getIsClosed()) {
                    stationJson.add("closingDate", new JsonPrimitive(station
                            .getClosingDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
                } else {
                    stationJson.add("closingDate", JsonNull.INSTANCE);
                }
                stations.add(stationJson);
            }
        }
    }

    private void linesToJSON(StationIndex src, JsonObject result) {
        JsonArray lines = new JsonArray();
        result.add("lines", lines);

        for (Line line : src.getLines().values()) {
            JsonArray stations = new JsonArray();

            JsonObject lineJson = new JsonObject();
            lineJson.addProperty("name", line.getName());
            lineJson.addProperty("number", line.getNumber());
            lines.add(lineJson);
        }
    }

    private void connectionsToJSON(StationIndex src, JsonObject result) {
        JsonArray connections = new JsonArray();
        result.add("connections", connections);

        for (Map.Entry<Station, TreeSet<Station>> conn : src.getConnections().entrySet()) {
            JsonObject stationFrom = new JsonObject();
            JsonArray stationsTo = new JsonArray();

            for (Station station : conn.getValue()) {
                JsonObject stationTo = new JsonObject();
                stationTo.addProperty("name", station.getName());
                stationTo.addProperty("lineNumber", station.getLineNumber());
                stationsTo.add(stationTo);
            }
            stationFrom.addProperty("name", conn.getKey().getName());
            stationFrom.addProperty("lineNumber", conn.getKey().getLineNumber());
            stationFrom.add("connected_stations", stationsTo);

            connections.add(stationFrom);
        }
    }

    private void nodesToJSON(StationIndex src, JsonObject result) {
        JsonArray nodes = new JsonArray();
        result.add("nodes", nodes);

        for (Set<Station> node : src.getNodes()) {
            JsonArray conn = new JsonArray();

            for (Station station : node) {
                JsonObject stationJson = new JsonObject();
                stationJson.addProperty("name", station.getName());
                stationJson.addProperty("lineNumber", station.getLineNumber());
                conn.add(stationJson);
            }
            nodes.add(conn);
        }
    }
}
