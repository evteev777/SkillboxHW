package Clients;

public class Person extends Client {

    public Person(double balance) {
        super(balance);
    }

    @Override
    public void moneyToAccount(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("На счет поступила сумма: " + amount);
    }

    @Override
    public void moneyFromAccount(double amount) {
        if (getBalance() < amount) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно для списания " + amount);
        } else {
            setBalance(getBalance() - amount);
            System.out.println("Со счета списана сумма: " + amount);
        }
    }
}