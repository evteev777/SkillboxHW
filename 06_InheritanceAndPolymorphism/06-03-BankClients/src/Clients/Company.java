package Clients;

public class Company extends Client {

    public Company(double balance) {
        super(balance);
    }

    @Override
    public void moneyToAccount(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("На счет поступила сумма: " + amount);
    }

    @Override
    public void moneyFromAccount(double amount) {
        double bankFee = amount * 0.01;
        if (getBalance() < amount + bankFee) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно для списания " + amount);
            System.out.println("         с учетом комиссии банка 1%: " + bankFee);
        }
        else {
            setBalance(getBalance() - amount - bankFee);
            System.out.println("Со счета списана сумма: " + amount);
            System.out.println("Комиссия банка за вывод средств 1%: " + bankFee);
        }
    }
}