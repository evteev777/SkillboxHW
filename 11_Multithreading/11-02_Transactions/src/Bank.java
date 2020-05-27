import java.util.HashMap;
import java.util.Random;

public class Bank {
    private HashMap<String, Account> accounts;

    private final Random random = new Random();
    private final long FRAUD_LIMIT = 50_000;

    public Bank() {
        this(new HashMap<>());
    }

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public void createAccount(String accNumber, long money) {
        accounts.put(accNumber, new Account(money, accNumber));
    }

    public synchronized boolean isFraud(
            String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {

        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {

        if (isFraud(fromAccountNum, toAccountNum, amount)) {
            long fromAccountMoney = accounts.get(fromAccountNum).getMoney();
            accounts.get(fromAccountNum).setMoney(fromAccountMoney - amount);

            long toAccountMoney = accounts.get(toAccountNum).getMoney();
            accounts.get(toAccountNum).setMoney(toAccountMoney - amount);
        } else {
            // TODO block account
        }
    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }
}
