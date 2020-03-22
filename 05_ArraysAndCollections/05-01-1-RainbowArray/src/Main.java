public class Main {

    public static void main(String[] args) {

        String text = "Каждый охотник желает знать, где сидит фазан";

        String[] colors = text.split(",?\\s+");

        for (String color : colors) {
            System.out.println(color);
        }

        String temp;
        int size = colors.length - 1;

        for (int i = 0; i < size + 1; i++) {

            if (i < size/2 + 1) {
                temp = colors[i];
                colors[i] = colors[size - i];
                colors[size - i] = temp;
            }
            System.out.println(colors[i]);
        }
    }
}