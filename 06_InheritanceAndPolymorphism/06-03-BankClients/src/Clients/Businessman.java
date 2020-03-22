package Clients;

public class Businessman extends Clients.Client {

    public Businessman(double balance) {
        super(balance);
    }

    @Override
    public void moneyToAccount(double amount) {

        System.out.println("На счет поступила сумма: " + amount);
        if(amount < 1000) {
            double bankFeeLess1000 = amount * 0.01;
            System.out.println("Комиссия банка за пополнение счета\n" +
                    "      на сумму менее 1000.0 равна 1%: " + bankFeeLess1000);
            setBalance(getBalance() + amount - bankFeeLess1000);
        } else {
            double bankFeeMore1000 = amount * 0.005;
            System.out.println("Комиссия банка за пополнение счета\n" +
                    "      на сумму более 1000.0 равна 0.5%: " + bankFeeMore1000);
            setBalance(getBalance() + amount - bankFeeMore1000);
        }
    }

    @Override
    public void moneyFromAccount(double amount) {
        if (getBalance() < amount) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно для списания " + amount);
        }
        else {
            setBalance(getBalance() - amount);
            System.out.println("Со счета списана сумма: " + amount);
        }
    }
}