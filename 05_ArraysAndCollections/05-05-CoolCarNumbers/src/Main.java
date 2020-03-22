import java.util.*;

public class Main {

    private static ArrayList<String> numbersBase = new ArrayList<>();
    private static HashSet<String> numbersBaseHS = new HashSet<>();
    private static TreeSet<String> numbersBaseTS = new TreeSet<>();

    public static void main(String[] args) {

        numbersGeneration();
        Collections.sort(numbersBase);

        numbersBaseHS.addAll(numbersBase); // HashSet
        numbersBaseTS.addAll(numbersBase); // TreeSet

        for(;;) {

            String number = input();

            foreachSearch(number);
            binarySearch(number);
            hashSetSearch(number);
            treeSetSearch(number);
        }
    }

    private static void numbersGeneration() {

        String[] letter = {"А","В","Е","К","М","Н","О","Р","С","Т","У","Х"};
        String digits;
        int region;

        for (region = 1; region <= 199; region++) {
            for (String l : letter) {
                for (int d = 1; d <= 999; d++) {

                    // Добавляем ведущие нули к 1- и 2-значным номерам
                    if (d < 10) {digits = "00" + d;}
                    else if (d < 100) {digits = "0" + d;}
                    else {digits = Integer.toString(d);}

                    // Генерация номеров: одинаковые буквы, цифры 001-999, регионы 01-199
                    // Добавляем ведущие нули к 1-значным регионам
                    numbersBase.add(l + digits + l + l + (region < 10 ? "0" + region : region));
                }
            }
        }
        System.out.println("Всего в базе " + (numbersBase.size() - 1) +" номеров");
    }

    private static String input() {

        Scanner scanner = new Scanner(System.in);

        for (; ; ) {
            System.out.println("\nВведите номер для проверки:");
            String input = scanner.nextLine().replaceAll("[^а-яА-Я\\d]*", "").toUpperCase().trim();

            String pattern = "[АВЕКМНОРСТУХ]"; // русские буквы, совпадающие по начертанию с английскими
            if (input.matches(pattern + "\\d{3}" + pattern + "{2}\\d{2,3}")) {
                return input;
            }

            System.out.println("Ошибка! Номер " + input + " введен неправильно!");
        }
    }

    private static void foreachSearch(String number) {

        long start = System.nanoTime();
        for (String result : numbersBase) {
            if (result.equals(number)) {
                long duration = System.nanoTime() - start;
                System.out.println("Номер " + number + " найден прямым перебором   - за " + duration + " нс ");
            }
        }
    }

    private static void binarySearch(String number) {

        long start = System.nanoTime();
        Collections.binarySearch(numbersBase, number);
        long duration = System.nanoTime() - start;
        System.out.println("Номер " + number + " найден бинарным поиском   - за " + duration + " нс ");
    }

    private static void hashSetSearch(String number) {

        long start = System.nanoTime();
        if (numbersBaseHS.contains(number)) {
            long duration = System.nanoTime() - start;
            System.out.println("Номер " + number + " найден поиском по HashSet - за " + duration + " нс ");
        }
    }

    private static void treeSetSearch(String number) {

        long start = System.nanoTime();
        if (numbersBaseTS.contains(number)) {
            long duration = System.nanoTime() - start;
            System.out.println("Номер " + number + " найден поиском по TreeSet - за " + duration + " нс ");
        }
    }
}