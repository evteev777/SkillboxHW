package core;

import bank.Bank;
import service.Log;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Log.timerStart();

        AtomicInteger transfersCount = new AtomicInteger();

        Bank bank = new Bank("New Bank");

        int accountsCount = 9;
        createRandomAccounts(bank, accountsCount);

        BigInteger startBankBalance = bank.getBankBalance();

        // To make a transfer, at least two accounts must be not blocked
        while (bank.getAccountsCount() - bank.callPoliceCount.intValue() >= 2) {
            randomTransfer(bank);
            transfersCount.incrementAndGet();
            Log.counters(transfersCount, bank);
        }

        Log.info("Difference with start bank balance: " +
                bank.getBankBalance().subtract(startBankBalance));
        Log.counters(transfersCount, bank);
        Log.timerFinish(System.currentTimeMillis() - startTime);
    }

    private static void createRandomAccounts(Bank bank, int count) {
        for (int i = 0; i < count; i++) {
            int maxBalance = 2_000_000;
            long money = RANDOM.nextInt(maxBalance);

            bank.createAccount(money);
        }
    }

    private static void randomTransfer(Bank bank) {
        String fromAccountNum = bank.getRandomAccountNum();
        String toAccountNum = bank.getRandomAccountNum();
        long amount = (long) (bank.getBalance(fromAccountNum) * RANDOM.nextDouble());

        bank.transfer(fromAccountNum, toAccountNum, amount);
    }
}