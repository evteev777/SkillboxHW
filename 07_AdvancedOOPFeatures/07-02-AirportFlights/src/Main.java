import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Airport airport = Airport.getInstance();

        Map<Flight, Terminal> flightsArrival = new TreeMap<>((o1, o2) -> { // компаратор по дате и времени рейса
            if (dateToLocalDateTime(o1.getDate()).isAfter(dateToLocalDateTime(o2.getDate()))) {return 1;}
            else if (dateToLocalDateTime(o1.getDate()).isBefore(dateToLocalDateTime(o2.getDate()))) {return -1;}
            else return 0;
        });

        Map<Flight, Terminal> flightsDeparture = new TreeMap<>((o1, o2) -> { // компаратор по дате и времени рейса
            if (dateToLocalDateTime(o1.getDate()).isAfter(dateToLocalDateTime(o2.getDate()))) {return 1;}
            else if (dateToLocalDateTime(o1.getDate()).isBefore(dateToLocalDateTime(o2.getDate()))) {return -1;}
            else return 0;
        });

        int period = 2; // время в часах, начиная с текущего момента, на которое выводится список рейсов

        LocalDateTime startPeriod = LocalDateTime.now(); // текущее время, начиная с которого выводится список рейсов

        LocalDateTime endPeriod = startPeriod.plusHours(period); // время, по которое выводится список рейсов

        airport.getTerminals().forEach(
                t -> t.getFlights().stream()

                        .filter(fa -> dateToLocalDateTime(fa.getDate()).isAfter(startPeriod))
                        .filter(fa -> dateToLocalDateTime(fa.getDate()).isBefore(endPeriod))
                        .filter(fa -> fa.getType().toString().equals("ARRIVAL"))

                        .forEach(fa -> flightsArrival.put(fa, t))
        );

        airport.getTerminals().forEach(
                t -> t.getFlights().stream()

                        .filter(fd -> dateToLocalDateTime(fd.getDate()).isAfter(startPeriod))
                        .filter(fd -> dateToLocalDateTime(fd.getDate()).isBefore(endPeriod))
                        .filter(fd -> fd.getType().toString().equals("DEPARTURE"))

                        .forEach(fd -> flightsDeparture.put(fd, t))
        );

        System.out.println("\nARRIVAL");
        for (Map.Entry<Flight, Terminal> fa : flightsArrival.entrySet()) {
            System.out.println(
                    dateToLocalDateTime(fa.getKey().getDate()).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
                    " / " + fa.getKey().toString() +
                    " / " + fa.getKey().getAircraft().toString() +
                    " / TERMINAL " + fa.getValue().getName());
        }

        System.out.println("\nDEPARTURE");
        for (Map.Entry<Flight, Terminal> fd : flightsDeparture.entrySet()) {
            System.out.println(
                    dateToLocalDateTime(fd.getKey().getDate()).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
                    " / " + fd.getKey().toString() +
                    " / " + fd.getKey().getAircraft().toString() +
                    " / TERMINAL " + fd.getValue().getName());
        }
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
