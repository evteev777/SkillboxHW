import Clients.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nСЧЕТ ЧАСТНОГО ЛИЦА");
        Person person = new Person(0.0);
        person.setBalance(200.0);
        System.out.println("Остаток на счете: " + person.getBalance());
        person.moneyToAccount(20.0);
        person.moneyFromAccount(220.1);
        System.out.println("Остаток на счете: " + person.getBalance());

        System.out.println("\nСЧЕТ ИП");
        Businessman businessman = new Businessman(2000.0);
        System.out.println("Остаток на счете: " + businessman.getBalance());
        businessman.moneyToAccount(1000.0); // проверка 900.0 или 1000.0
        businessman.moneyFromAccount(2995.1);
        System.out.println("Остаток на счете: " + businessman.getBalance());

        System.out.println("\nСЧЕТ ОРГАНИЗАЦИИ");
        Company company = new Company(20000.0);
        System.out.println("Остаток на счете: " + company.getBalance());
        company.moneyToAccount(2000.0);
        company.moneyFromAccount(21782.2);
        System.out.println("Остаток на счете: " + company.getBalance());
    }
}