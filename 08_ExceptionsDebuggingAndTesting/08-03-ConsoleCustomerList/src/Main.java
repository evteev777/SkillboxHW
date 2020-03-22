import java.util.Scanner;

public class Main {
    private static String addCommand = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static String commandExamples = "\t" + addCommand + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров\n\texit"; // добавил команду для выхода из программы exit
    private static String commandError = "Wrong command! Available command examples: \n" +
            commandExamples;
    private static String helpText = "Command examples:\n" + commandExamples;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        // Приглашение ввести первую команду
        System.out.println(helpText + "\nType your command here:");

        for (; ; ) {
            try { // общий try-catch
                String command = scanner.nextLine();

                String[] tokens = command.split("\\s+", 2);
                if (tokens[0].equals("add")) {
                    if (tokens.length < 2 || tokens[1].equals("")) {
                        throw new IllegalArgumentException(
                                "Ошибка! Для добавления ввведите через пробел: add Имя Фамилия E-mail Телефон");
                    }
                    executor.addCustomer(tokens[1]);
                } else if (tokens[0].equals("list")) {
                    executor.listCustomers();
                } else if (tokens[0].equals("remove")) {
                    if (tokens.length < 2 || tokens[1].equals("")) {
                        throw new IllegalArgumentException(
                                "Ошибка! Для удаления ведите через пробел: remove Имя Фамилия");
                    }
                    executor.removeCustomer(tokens[1]);
                } else if (tokens[0].equals("count")) {
                    System.out.println("There are " + executor.getCount() + " customers");
                } else if (tokens[0].equals("help")) {
                    System.out.println(helpText);
                } else if (tokens[0].equals("exit")) { // добавил команду для выхода из программы exit
                    return;
                }
                else {
                    System.out.println(commandError);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
