import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    Line[] line;
    Station[][] station;

    StationIndex testStationIndex;
    RouteCalculator testCalculator;
    List<Station> testRoute;

    @Override
    protected void setUp() {

        line = new Line[3];
        station = new Station[3][3]; // [N линии] [N станции на этой линии]

        testStationIndex = new StationIndex();
        testCalculator = new RouteCalculator(testStationIndex);
        testRoute = new ArrayList<>();

        for (int l = 0; l < 3; l++) {

            line[l] = new Line(l + 1, "Line " + (l + 1));
            testStationIndex.addLine(line[l]); // добавление линии в индекс

            for (int s = 0; s < 3; s++) {

                station[l][s] = new Station("Station " + (s + 1) + " on line " + (l + 1), line[l]);
                line[l].addStation(station[l][s]); // привязка станции к линии
                testStationIndex.addStation(station[l][s]); // добавление станции в индекс
            }
        }
        // добавление переходов в индекс
        testStationIndex.addConnection(new ArrayList<>(Arrays.asList(station[0][1], station[1][0])));
        testStationIndex.addConnection(new ArrayList<>(Arrays.asList(station[1][2], station[2][1])));
    }

    // тест метода расчета времени поездки
    public void testCalculateDuration() {

        // Самый длинный маршрут с 2 пересадками
        testRoute.add(testStationIndex.getStation("Station 1 on line 1"));
        testRoute.add(testStationIndex.getStation("Station 2 on line 1"));
        testRoute.add(testStationIndex.getStation("Station 1 on line 2"));
        testRoute.add(testStationIndex.getStation("Station 2 on line 2"));
        testRoute.add(testStationIndex.getStation("Station 3 on line 2"));
        testRoute.add(testStationIndex.getStation("Station 2 on line 3"));
        testRoute.add(testStationIndex.getStation("Station 3 on line 3"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 17.0;

        assertEquals(expected, actual);
    }

    // тест метода построения маршрута - по времени поездки - маршрут без переходов
    public void testTimeOfRouteOnTheLine() throws NullPointerException {

        List<Station> testRoute = testCalculator.getShortestRoute(
                testStationIndex.getStation("Station 1 on line 1"),
                testStationIndex.getStation("Station 3 on line 1"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 2 * 2.5; // 2 прогона

        assertEquals(expected, actual);
    }

    // тест метода построения маршрута - по времени поездки - маршрут  с 1 переходом
    public void testTimeOfRouteWithOneConnections() throws NullPointerException {

        List<Station> testRoute = testCalculator.getShortestRoute(
                testStationIndex.getStation("Station 1 on line 1"),
                testStationIndex.getStation("Station 2 on line 2"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 2 * 2.5 + 3.5; // 2 прогона, 1 переход

        assertEquals(expected, actual);
    }

    // тест метода построения маршрута - по времени поездки - маршрут  с 2 переходами
    public void testTimeOfRouteWithTwoConnections() throws NullPointerException {

        List<Station> testRoute = testCalculator.getShortestRoute(
                testStationIndex.getStation("Station 1 on line 1"),
                testStationIndex.getStation("Station 2 on line 3"));

        double actual = RouteCalculator.calculateDuration(testRoute);
        double expected = 3 * 2.5 + 2 * 3.5; // 3 прогона, 2 перехода

        assertEquals(expected, actual);
    }

    public void testGetShortestRouteShouldNoNull() throws NullPointerException {

        assertNotNull(testCalculator.getShortestRoute(
                testStationIndex.getStation("Station 1 on line 1"),
                testStationIndex.getStation("Station 3 on line 3")));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        // Удаление ссылок на массивы и объекты

        Line[] line = null;
        Station[][] station = null;

        StationIndex testStationIndex = null;
        RouteCalculator testCalculator = null;
        List<Station> testRoute = null;
    }
}
