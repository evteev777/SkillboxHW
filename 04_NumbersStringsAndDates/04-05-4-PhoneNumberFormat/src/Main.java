import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        for(;;) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите номер телефона в любом формате:");
            String phoneInput = scanner.nextLine();

            String phone = phoneInput.replaceAll("[^0-9]", "");

            if (phone.length() >= 10 && phone.length() <= 11) {
                System.out.println("Номер телефона " + phone);
                return;
            } else {
                System.out.println("ОШИБКА!\nВ номере должно быть 10-11 цифр");
            }
        }
    }
}