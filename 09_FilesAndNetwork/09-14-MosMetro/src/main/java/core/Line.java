package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line implements Comparable<Line> {

    private final String number;
    private final String name;
    private final List<Station> stations;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public double stringToDouble(String number) {
        if (number.matches("\\d+[А-Я]$")) {
            int endChar = number.charAt(number.length() - 1);
            String integerPart = number.substring(0, number.length() - 1);
            String fractionPart = String.valueOf((endChar - 1000) / 100.0).substring(1);
            number = integerPart + fractionPart;
        }
        return Double.parseDouble(number);
    }

    @Override
    public int compareTo(Line line) {
        return Double.compare(stringToDouble(number), stringToDouble(line.getNumber()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line lineO = (Line) o;
        return number.equals(lineO.number) && name.equals(lineO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }

    @Override
    public String toString() {
        return number + ":" + name;
    }
}