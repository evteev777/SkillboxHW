import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите рахмер X:");

        int size = scanner.nextInt();
        int x = size;
        int y = size;

        String[][] cross = new String[x][y];

        for (y = 0; y < size; y++) {
            for (x = 0; x < size; x++) {

                if (x == y || (x + y +1 == size)) {
                    cross[x][y] = "X";
                }
                else {
                    cross[x][y] = " ";
                }

                System.out.print(cross[x][y]);
            }
            System.out.print("\n");
        }
    }
}