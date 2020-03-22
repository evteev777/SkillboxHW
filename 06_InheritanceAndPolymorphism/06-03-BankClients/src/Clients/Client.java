package Clients;

abstract class Client {

    private double balance;

    Client(double balance) {
        this.balance = balance;
    }

    public abstract void moneyToAccount (double amount);
    public abstract void moneyFromAccount(double amount);

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}