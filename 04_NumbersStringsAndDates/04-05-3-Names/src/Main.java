import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        for(;;) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Фамилия Имя Отчество:");
            String fullName = scanner.nextLine();

            System.out.print(checkFullName(fullName) ? "" :
                    "ОШИБКА!\nВведите данные русскими буквами,\nкаждое слово с большой буквы через пробел!\n\n");

            if (checkFullName(fullName)) {

                String[] list = {"Фамилия:  ", "Имя:      ","Отчество: "};
                String[] part = fullName.split(" ");

                for (int i = 0; i < part.length; i++) {
                    System.out.println(list[i] + part[i]);
                }

                return;
            }
        }
    }

    private static boolean checkFullName(String s) {
        Pattern pattern = Pattern.compile("([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)(\\s*-*[А-Я]*[а-я]+)*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}