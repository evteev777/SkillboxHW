package Accounts;

public class CreditCard extends Account {

    public CreditCard(double balance) {
        super(balance);
    }

    @Override
    public void moneyFromAccount(double amount) {
        double bankFee = amount * 0.01;
        if (getBalance() < amount + bankFee) {
            System.out.println("Остатка на счете " + getBalance() + " недостаточно для списания " + amount);
            System.out.println("         с учетом комиссии банка 1%: " + bankFee);
        }
        else {
            System.out.println("Со счета списана сумма: " + amount);
            System.out.println("Комиссия банка 1%: " + bankFee);
            setBalance(getBalance() - (amount + bankFee));
        }
    }
}
