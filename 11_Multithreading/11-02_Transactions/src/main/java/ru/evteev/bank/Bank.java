package ru.evteev.bank;

import ru.evteev.utils.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {

    private static final Random RANDOM = new Random();
    private static final String ACCOUNT_NUM_FORMAT = "%04d";

    private final int transferDuration;

    private final long fraudAmountLimit;
    private final double fraudPercent; // of all transfers count
    private final int fraudCheckDuration;
    private final int extFraudCheckDuration;

    public final AtomicInteger extFraudCheckCount = new AtomicInteger();
    public final AtomicInteger unBlockedAccountsCount = new AtomicInteger();
    public final AtomicInteger callPoliceCount = new AtomicInteger();

    private final Object fraudCheckLock = new Object();
    private final Object extFraudCheckLock = new Object();

    private final String name;
    private final Map<String, Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ConcurrentHashMap<>();

        this.transferDuration = 10;

        this.fraudAmountLimit = 50_000; // should be positive
        this.fraudPercent = 0.05; // should be between 0 and 1
        this.fraudCheckDuration = 1000; // ms
        this.extFraudCheckDuration = 2000; // ms

        Log.created(this);
    }

    public void createAccount(long money) {
        String number = String.format(ACCOUNT_NUM_FORMAT, accounts.size() + 1);
        accounts.put(number, new Account(this, number, money));
        unBlockedAccountsCount.incrementAndGet();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        try {
            Account from = accounts.get(fromAccountNum);
            Account to = accounts.get(toAccountNum);

            if (isNotCorrectTransfer(from, to, amount)) {
                return;
            }

            Thread.sleep(transferDuration);
            from.writeOff(amount);
            to.writeOn(amount);
            Log.transfer(fromAccountNum, toAccountNum, amount);

            if (amount > fraudAmountLimit) {
                Thread thread = new Thread(() -> fraudCheck(from, to), "fraudCheck");
                thread.start();
                thread.join();
            }
        } catch (InterruptedException e) {
            Log.error(e);
            Thread.currentThread().interrupt();
        }
    }

    private boolean isNotCorrectTransfer(Account from, Account to, long amount) {
        return isZeroAmount(amount)
                || isNotEnoughMoney(from, amount)
                || isSameAccount(from, to)
                || isAccountBlocked(from) || isAccountBlocked(to);
    }

    private boolean isZeroAmount(long amount) {
        if (amount == 0) {
            Log.debug("ZERO-AMOUNT TRANSFERS ARE NOT ALLOWED !!!");
            return true;
        }
        return false;
    }

    private synchronized boolean isNotEnoughMoney(Account account, long amount) {
        if (amount > account.getMoney()) {
            Log.debug("NOT ENOUGH MONEY IN THE ACCOUNT !!!");
            return true;
        }
        return false;
    }

    private boolean isSameAccount(Account from, Account to) {
        if (from == to) {
            Log.debug("TRANSFER BETWEEN THE SAME ACCOUNT IS NOT ALLOWED !!!");
            Log.debug("From: " + from.toString());
            Log.debug("To:   " + to.toString());
            return true;
        }
        return false;
    }

    private synchronized boolean isAccountBlocked(Account account) {
        if (account.isBlocked()) {
            Log.debug("TRANSFER BETWEEN BLOCKED ACCOUNTS DENIED !!!");
            if (account.isBlocked()) {
                Log.debug(account.toString());
            }
            return true;
        }
        return false;
    }

    private void fraudCheck(Account from, Account to) {
        synchronized (fraudCheckLock) {
            long startTime = System.currentTimeMillis();
            Log.timerStart();

            try {
                Thread.sleep(fraudCheckDuration);
                boolean isFraud = RANDOM.nextDouble() < fraudPercent;

                if (isFraud) {
                    Log.debug("FRAUD TRANSFER!!!");

                    from.block();
                    unBlockedAccountsCount.decrementAndGet();
                    Log.unBlockedAccountsCount(this);
                    to.block();
                    unBlockedAccountsCount.decrementAndGet();
                    Log.unBlockedAccountsCount(this);

                    Thread extFraudCheckFrom = new Thread(() -> extFraudCheck(from), "extFraudCheckFrom");
                    Thread extFraudCheckTo = new Thread(() -> extFraudCheck(to), "extFraudCheckTo");

                    extFraudCheckFrom.start();
                    extFraudCheckTo.start();

                    extFraudCheckFrom.join();
                    extFraudCheckTo.join();
                }
            } catch (InterruptedException e) {
                Log.error(e);
                Thread.currentThread().interrupt();
            }
            Log.timerFinish(System.currentTimeMillis() - startTime);
        }
    }

    private void extFraudCheck(Account account) {
        synchronized (extFraudCheckLock) {
            long startTime = System.currentTimeMillis();
            Log.timerStart();

            Log.debug("Ext. check start:  \tAccount " + account.getNumber());
            extFraudCheckCount.incrementAndGet();

            try {
                Thread.sleep(extFraudCheckDuration);
            } catch (InterruptedException e) {
                Log.error(e);
                Thread.currentThread().interrupt();
            }

            boolean isTransferValid = RANDOM.nextBoolean();

            if (isTransferValid) {
                account.unBlock();
                unBlockedAccountsCount.incrementAndGet();
                Log.unBlockedAccountsCount(this);
            } else {
                account.callPolice();
                callPoliceCount.incrementAndGet();
            }

            extFraudCheckCount.decrementAndGet();
            Log.debug("Ext.verify finish: \tAccount " + account.getNumber());
            Log.timerFinish(System.currentTimeMillis() - startTime);
        }
    }

    public synchronized long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public String getRandomAccountNum() {
        List<String> accountNums = new ArrayList<>(accounts.keySet());
        int randomIndex = RANDOM.nextInt(accountNums.size());
        return accountNums.get(randomIndex);
    }

    public synchronized int getAccountsCount() {
        return accounts.size();
    }

    public synchronized int getUnblockedAccountsCount() {
        return unBlockedAccountsCount.intValue();
    }

    public synchronized BigInteger getBankBalance() {
        BigInteger bankBalance = new BigInteger(String.valueOf(0));
        Set<String> accountNums = new TreeSet<>(accounts.keySet());

        Log.debug(String.format("%nACCOUNTS LIST%n%s%n-----", this.toString()));
        for (String num : accountNums) {
            Account account = accounts.get(num);
            bankBalance = bankBalance.add(BigInteger.valueOf(account.getMoney()));
            Log.debug(accounts.get(num).toString());
        }
        Log.info(String.format("-----%nBank balance:\t%d rub", bankBalance));

        return bankBalance;
    }

    @Override
    public String toString() {
        return "Bank \"" + name + "\"";
    }
}
