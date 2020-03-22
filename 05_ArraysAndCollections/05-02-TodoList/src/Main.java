import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<String> todoList = new ArrayList<>();

    public static void main(String[] args) {

        boolean firstLap = true;

        for(;;) {

            System.out.println(firstLap ? "Введите команду (для подсказки введите HELP):" :
                    "\nВведите команду, или END для завершения:");
            firstLap = false;

            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine() + " ";

            String command = answer.substring(0, 4).toUpperCase().trim();
            String idAndValue = answer.substring(command.length()).trim();
            String value = idAndValue.replaceAll("^[\\d]*","").trim();
            int id = id(idAndValue.substring(0,idAndValue.lastIndexOf(value)).trim());

            if (command.equals("ADD")) {
                add(id, value);
            }

            if (command.equals("EDIT")) {
                edit(id, value);
            }

            if (command.equals("DEL")) {
                del(id);
            }

            if (command.equals("LIST")) {
                list();
            }

            if (command.equals("HELP")) {
                help();
            }

            if (command.equals("END")||command.equals("EXIT")) {
                return;
            }
        }
    }

    // Если id не указан вообще - устанавливаем id в конец списка
    private static int id(String input) {
        int id;

        if (input.length() == 0) {
            id = todoList.size();
        }
        else {
            id = Integer.parseInt(input);
        }
        return id;
    }

    // Проверякм, что id в пределах размера списка
    private static boolean idOutOfListSize(int id) {
        boolean idOutOfListSize = false;
        if (id > todoList.size()) {
            idOutOfListSize = true;
        }
        return idOutOfListSize;
    }

    private static void add(int id, String value) {
        if (idOutOfListSize(id)) {
            System.out.println("Введен слишком большой номер: " + id +"\nЗадача будет добавлена в конец списка\n");
            id = todoList.size();
        }
        todoList.add(id, value);
        System.out.println("Задача " + id + " добавлена");
        list();
    }

    private static void edit(int id, String value) {
        if (idOutOfListSize(id)) {
            System.out.println(todoList.size() == 0 ?
                    "\"Список пуст! Добавьте свою первую задачу командой 'ADD Описание задачи'\"" :
                    "\nОШИБКА!\nВведите номер задачи  от 0 до " + (todoList.size() - 1));
        }
        else {
            todoList.remove(id);
            todoList.add(id, value);
            System.out.println("Задача " + id + " изменена");
            list();
        }
    }

    private static void del(int id) {
        if (idOutOfListSize(id)) {
            System.out.println(todoList.size() == 0 ?
                    "\"Список пуст! Добавьте свою первую задачу командой 'ADD Описание задачи'\"" :
                    "\nОШИБКА!\nВведите номер задачи  от 0 до " + (todoList.size() - 1));
        }
        else {
            todoList.remove(id);
            System.out.println("Задача " + id + " удалена");
            list();
        }
    }

    private static void list() {
        System.out.println("\nСПИСОК ЗАДАЧ");
        if (todoList.size() == 0) {
            System.out.println("Список пуст! Добавьте свою первую задачу командой 'ADD Описание задачи'");
        }
        else {
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println("Задача " + i + ": " + todoList.get(i));
            }
        }
    }

    private static void help() {
        System.out.println("\nКОМАНДЫ:\n" +
                "Добавить зазачу:   ADD Номер Описание задачи (номер указывать не обязательно) \n" +
                "Изменить задачу:   EDIT Номер задачи Обновленное описание задачи \n" +
                "Удалить задачу:    DEL Номер задачи\n" +
                "Список задач:      LIST\n" +
                "Завершение работы: END bkb EXIT"
        );
    }
}