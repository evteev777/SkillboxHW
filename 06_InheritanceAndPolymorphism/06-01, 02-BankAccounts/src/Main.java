import Accounts.Account;
import Accounts.CreditCard;
import Accounts.Deposit;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nБАНКОВСКИЙ СЧЕТ");
        Account account = new Account(0);
        account.setBalance(2000);
        System.out.println("Остаток на счете: " + account.getBalance());
        account.moneyToAccount(200);
        account.moneyFromAccount(2201);
        System.out.println("Остаток на счете: " + account.getBalance());

        System.out.println("\nСЧЕТ КРЕДИТНОЙ КАРТЫ");
        CreditCard creditCard = new CreditCard(200.0);
        System.out.println("Остаток на счете: " + creditCard.getBalance());
        creditCard.moneyFromAccount(199);
        System.out.println("Остаток на счете: " + creditCard.getBalance());

        System.out.println("\nДЕПОЗИТНЫЙ СЧЕТ");
        Deposit deposit = new Deposit(3000.0);
        System.out.println("Остаток на счете: " + deposit.getBalance());
        deposit.moneyToAccount(1000);
        deposit.moneyFromAccount(3001, Deposit.getEndDepositDate());
        System.out.println("Остаток на счете: " + deposit.getBalance());

    }
}
