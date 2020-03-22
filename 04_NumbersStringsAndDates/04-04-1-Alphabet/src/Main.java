public class Main {

    public static void main(String[] args) {

        char YO = 'Ё', yo = 'ё';

        for(char i = 'A'; i <= 'я'; i++){
            if (i == ('Z' + 1)) {i = 'a';}

            if (i == ('z' + 1)) {i = 'А';}

            System.out.print(i == 'Ж' ? YO + " - " + (int) YO + "\n" : "");
            System.out.print(i == 'ж' ? yo + " - " + (int) yo + "\n" : "");

            System.out.println(i + " - " + (int) i);
        }
    }
}