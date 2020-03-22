public class Main
{
    public static void main(String[] args) {

        int ticketNumber;

        System.out.println("Цикл 'for'");

        for (ticketNumber = 200000; ticketNumber <= 235000; ticketNumber++) {

            if (ticketNumber == 210001) {ticketNumber = 220000;}
            System.out.println("Ticket №" + ticketNumber);
        }

        System.out.println("\n\n Цикл 'while'");

        ticketNumber = 200000;
        while (ticketNumber <= 235000) {
            System.out.println("Ticket №" + ticketNumber);
            if (ticketNumber == 210000) {ticketNumber = 220000;}
            else {ticketNumber++;}
        }
    }
}