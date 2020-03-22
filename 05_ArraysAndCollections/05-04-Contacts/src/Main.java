import java.util.*;

public class Main {

    public static void main(String[] args) {

        TreeMap<String, String> name2phone = new TreeMap<>();

        for (; ; ) {

            String phone = "";
            String name = "";

            System.out.println("\nВведите или имя. или номер телефона: ");
            String input = input();

            if (input.matches("\\d+")) {phone = input;}
            else {name = input;}

            if (name.toUpperCase().equals("EXIT")||name.toUpperCase().equals("END")) {
                return;
            }

            if (name.toUpperCase().equals("LIST")) {
                list(name2phone);
                continue;
            }

            // Если запись уже есть - выводим ее в консоль:
            if (name2phone.containsKey(name) || name2phone.containsValue(phone)) {
                if (!name.isEmpty()) {
                    phone = name2phone.get(name);
                }
                else {
                    name = getPhoneFromName(name2phone, phone);
                }
                System.out.println("Такая запись уже есть: " + name + " +" + phone);
                continue;
            }

            // Если записи еще нет - запрашиваем недостающую информацию, и добавляем запись в TreeMap
            if (name.isEmpty()) {
                System.out.println("Введите имя: ");
                name = input();
            } else {
                System.out.println("Введите номер телефона: ");
                phone = input();
            }
            name2phone.put(name, phone);
            System.out.println("\nЗапись добавлена: " + name + " +" + name2phone.get(name));
            list(name2phone);
        }
    }

    private static String input() {

        Scanner scanner = new Scanner(System.in);

        for (;;) {

            String input = scanner.nextLine();

            String phone = input.replaceAll("[^\\d]+", "");
            String name = input.replaceAll("[^a-zA-Zа-яА-Я\\s]+", "").trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                System.out.println("Ошибка! Введите ИЛИ имя, ИЛИ номер телефона: ");
                continue;
            }

            if (!phone.isEmpty() && (phone.length() < 10 || phone.length() > 12)) {
                System.out.println("Ошибка! Введите номер телефона от 10 до 12 цифр! ");
                continue;
            }

            return name.concat(phone);
        }
    }

    private static void list(Map<String, String> map) {

        System.out.println("\nСПИСОК КОНТАКТОВ ");
        map.forEach((name, phone) -> System.out.println(name + " +" + phone));
    }

    private static String getPhoneFromName(Map phone2name, String name) {
        for (Object phone : phone2name.keySet()) {
            if (phone2name.get(phone).equals(name)) {
                return (String) phone;
            }
        }
        return null;
    }
}