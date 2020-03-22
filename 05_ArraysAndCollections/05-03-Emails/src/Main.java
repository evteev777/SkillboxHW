import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static HashSet<String> emailsHashSet = new HashSet<>();
    private static TreeSet<String> emailsTreeSet = new TreeSet<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        for (; ; ) {
            System.out.println("Введите команду (для подсказки введите HELP):");
            String answer = scanner.nextLine();

            answer = answer.replaceAll("\\s\\s", " ").trim();
            Pattern pattern = Pattern.compile("(?i)(^ADD|^LIST|^HELP|^END)\\s*([\\w\\.-]+@[\\w\\.-]+)*");
            Matcher matcher = pattern.matcher(answer);

            while (matcher.find()) {
                String command = matcher.group(1).toUpperCase();
                String email = matcher.group(2);

                if (!command.equals("END")) {

                    if (command.equals("ADD")) {
                        add(email);
                    }

                    if (command.equals("LIST")) {
                        list();
                    }

                    if (command.equals("HELP")) {
                        help();
                    }
                }
                else {return;}
            }
        }
    }

    private static void add(String email) {

        if (email != null) {
            emailsHashSet.add(email);
            System.out.println("E-mail " + email + " добавлен в HashSet");

            emailsTreeSet.add(email);
            System.out.println("E-mail " + email + " добавлен в TreeSet");

            list();
        }
        else {
            System.out.println("ОШИБКА! Указан неправильный E-mail!");
        }
    }

    private static void list() {
        System.out.println("\nСПИСОК E-MAIL HashSet");
        if (emailsHashSet.size() == 0) {
            System.out.println("Список HashSet пуст! Добавьте первый e-mail командой 'ADD E-mail'");
        }
        else {
            for (String record : emailsHashSet) {
                System.out.println(record);
            }
        }

        System.out.println("\nСПИСОК E-MAIL TreeSet");
        if (emailsTreeSet.size() == 0) {
            System.out.println("Список TreeSet пуст! Добавьте первый e-mail командой 'ADD E-mail'");
        }
        else {
            for (String record : emailsTreeSet) {
                System.out.println(record);
            }
        }
    }

    private static void help() {System.out.println("\nКОМАНДЫ:\n" +
                "Добавить e-mai:    ADD e-mail\n" +
                "Список e-mai:      LIST\n" +
                "Завершение работы: END");
    }
}