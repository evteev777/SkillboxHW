public class Main {

    public static void main(String[] args) {

        double[] temp = new double[30];

        double minTemp = 32.0;
        double maxTemp = 40.0;

        double avgTemp;
        double sum = 0;

        int healthyPatients = 0;

        System.out.print("Temperature list: ");
        for (int i = 0; i < temp.length; i++) {

            temp[i] = (double) (Math.round((minTemp + Math.random() * (maxTemp - minTemp)) * 10)) /10;
            System.out.print(temp[i] + "  ");

            sum = sum + temp[i];

            if (temp[i] >=36.2 && temp[i] <= 36.9) {healthyPatients++;}
        }

        avgTemp = Math.round((sum / temp.length) * 10) / 10.0;

        System.out.println("\nAverage temp: " + avgTemp + "\u00b0C");
        System.out.println("Healthy Patients: " + healthyPatients);
    }
}
