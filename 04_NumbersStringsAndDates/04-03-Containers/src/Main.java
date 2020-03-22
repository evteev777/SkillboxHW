import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Введите количество ящиков:");
        int boxCount = sc.nextInt();

        Loading todayLoading = new Loading(boxCount);

        System.out.println("Погрузка закончена");
    }
}
