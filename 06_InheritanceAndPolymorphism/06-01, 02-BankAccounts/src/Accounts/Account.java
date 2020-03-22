package Accounts;

public class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public void moneyToAccount (double amount) {
        balance = getBalance() + amount;
        System.out.println("На счет поступила сумма: " + amount);
    }

    public void moneyFromAccount(double amount) {
        if (getBalance() < amount) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно  для списания " + amount);
        }
        else {
            balance = getBalance() - amount;
            System.out.println("Со счета списана сумма: " + amount);
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}
