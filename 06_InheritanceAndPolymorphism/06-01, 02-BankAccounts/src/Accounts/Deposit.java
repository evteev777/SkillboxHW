package Accounts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Deposit extends Account {

    private static LocalDate endDepositDate;

    public Deposit(double balance) {
        super(balance);
    }

    @Override
    public void moneyToAccount (double amount) {
        setBalance(getBalance() + amount);
        endDepositDate = LocalDate.now().plusMonths(1).minusDays(31); // для проверки: .minusDays(30-31)
        System.out.println("На счет поступила сумма: " + amount);
    }

    public void moneyFromAccount(double amount, LocalDate endDepositDate) {
        if (getBalance() < amount) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно для списания " + amount);
        }
        else {
            long period = ChronoUnit.DAYS.between(LocalDate.now(), endDepositDate);
            if (LocalDate.now().isBefore(endDepositDate)) {
                System.out.println("Снять деньги можно через " + period + " дн.");
            } else {
                setBalance(getBalance() - amount);
                System.out.println("Со счета списана сумма: " + amount);
            }
        }
    }

    public static LocalDate getEndDepositDate() {
        return endDepositDate;
    }
}