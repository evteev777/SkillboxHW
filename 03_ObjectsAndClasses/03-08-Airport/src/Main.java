
import com.skillbox.airport.Airport;

public class Main
{
    public static void main(String[] args)
    {
        Airport airport = Airport.getInstance();
        airport.getAllAircrafts().forEach(System.out::println);
        System.out.println("\n В данный момент общее количество самолетов в "
            + airport.getTerminals().size() + " терминалах: "
            + airport.getAllAircrafts().size());
    }
}
