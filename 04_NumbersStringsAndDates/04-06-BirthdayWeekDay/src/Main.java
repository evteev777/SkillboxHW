import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {

        LocalDate birthday = LocalDate.of(1975, 7, 18);
        LocalDate currentBirthday = birthday;

        Period p = Period.between(birthday, LocalDate.now());

        System.out.println("Я родился " + birthday.format(DateTimeFormatter.ofPattern("dd MMMM yyyy года - EEEE")));
        System.out.printf("Мне %d года\n\n", p.getYears());

        for (int i = 0; i <= p.getYears() - 1; i++) {

            currentBirthday = currentBirthday.plusYears(1);
            System.out.println("Мой " + (i + 1) + " день рождения: " + currentBirthday.format(DateTimeFormatter.ofPattern("dd MMMM yyyy года - EEEE")));
        }
    }
}

