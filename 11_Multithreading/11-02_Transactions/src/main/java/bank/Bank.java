package bank;

import service.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {

    private static final Random RANDOM = new Random();
    private static final String ACCOUNT_NUM_FORMAT = "%04d";

    private final long fraudAmountLimit;
    private final double fraudPercent; // of all transfers count
    private final int fraudCheckDuration;
    private final int extFraudCheckDuration;

    public final AtomicInteger extFraudCheckCount = new AtomicInteger();
    public final AtomicInteger callPoliceCount = new AtomicInteger();

    private final String name;
    private final HashMap<String, Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();

        this.fraudAmountLimit = 50_000;
        this.fraudPercent = 0.05;
        this.fraudCheckDuration = 100; // ms
        this.extFraudCheckDuration = 300; // ms

        Log.created(this);
    }

    public void createAccount(long money) {
        String accountNum = String.format(ACCOUNT_NUM_FORMAT, accounts.size() + 1);
        accounts.put(accountNum, new Account(accountNum, money));
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);

        Log.transfer(fromAccount.getNumber(), toAccount.getNumber(), amount);

        if (amount == 0) {
            Log.info("ZERO-SUM TRANSFERS ARE NOT ALLOWED !!!");

        } else if (fromAccount.isBlocked() || toAccount.isBlocked()) {
            Log.info("TRANSFER BETWEEN BLOCKED ACCOUNTS DENIED !!!");
            if (fromAccount.isBlocked())
                Log.info(fromAccount.toString());
            if (toAccount.isBlocked())
                Log.info(toAccount.toString());

        } else {
            fromAccount.setMoney(fromAccount.getMoney() - amount);
            toAccount.setMoney(toAccount.getMoney() + amount);

            if (amount > fraudAmountLimit)
                fraudCheck(fromAccount, toAccount);
        }
    }

    private void fraudCheck(Account fromAccount, Account toAccount) {
        boolean isFraud = RANDOM.nextDouble() < fraudPercent;

        if (isFraud) {
            try {
                Thread.sleep(fraudCheckDuration);
                Log.info("FRAUD TRANSFER!!!");

                fromAccount.block();
                toAccount.block();

                Thread extFraudCheckFromAccount = new Thread(() -> extFraudCheck(fromAccount));
                Thread extFraudCheckToAccount = new Thread(() -> extFraudCheck(toAccount));

                extFraudCheckFromAccount.start();
                extFraudCheckToAccount.start();

                extFraudCheckFromAccount.join();
                extFraudCheckToAccount.join();

            } catch (InterruptedException e) {
                Log.error(e);
            }
        }
    }

    private void extFraudCheck(Account account) {
        Log.threadStart();
        Log.info("Ext. check start:  \tAccount " + account.getNumber());
        extFraudCheckCount.incrementAndGet();

        try {
            Thread.sleep(extFraudCheckDuration);
        } catch (InterruptedException e) {
            Log.error(e);
        }

        boolean isTransferValid = RANDOM.nextBoolean();

        if (isTransferValid) {
            account.unBlock();
        } else {
            callPoliceCount.incrementAndGet();
            account.callPolice();
        }

        extFraudCheckCount.decrementAndGet();
        Log.info("Ext.verify finish: \tAccount " + account.getNumber());
        Log.threadFinish();
    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public String getRandomAccountNum() {
        List<String> numbers = new ArrayList<>(accounts.keySet());
        return numbers.get(RANDOM.nextInt(numbers.size()));
    }

    public int getAccountsCount() {
        return accounts.size();
    }

    public BigInteger getBankBalance() {

        BigInteger bankBalance = new BigInteger(String.valueOf(0));

        Log.info(String.format("%nACCOUNTS LIST%n%s%n-----", this.toString()));

        Set<String> numbers = new TreeSet<>(accounts.keySet());

        for (String number : numbers) {
            Account account = accounts.get(number);
            bankBalance = bankBalance.add(BigInteger.valueOf(account.getMoney()));
            Log.info(accounts.get(number).toString());
        }
        Log.info(String.format("-----%nBank balance:\t%d rub", bankBalance));

        return bankBalance;
    }

    @Override
    public String toString() {
        return "Bank \"" + name + "\"";
    }
}
