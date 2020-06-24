package ru.evteev;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.evteev.bank.Bank;
import ru.evteev.utils.Log;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private static final Random RANDOM = new Random();
    private static Bank bank;

    @Before
    public void serUp() {
        bank = new Bank("Test Bank");
    }

    @Test
    public void bankBalanceShouldNotBeChanged() {
        int accountsCount = 3;
        createRandomAccounts(bank, accountsCount);

        BigInteger startBankBalance = bank.getBankBalance();

        while (bank.getUnblockedAccountsCount() >= 2) {
            randomTransfer(bank);
        }
        BigInteger diff = bank.getBankBalance().subtract(startBankBalance);
        Log.info("Difference with start bank balance: " + diff);
        Assert.assertEquals(BigInteger.valueOf(0), diff);
    }

    // TODO the test takes a lot of time, need to use unblocked accounts only
    @Test
    public void bankBalanceShouldNotBeChangedInThreads() {
        Log.info("TEST START");

        int accountsCount = 5;
        createRandomAccounts(bank, accountsCount);
        BigInteger startBankBalance = bank.getBankBalance();
        Log.unBlockedAccountsCount(bank);

        Log.info("TRANSFERS START");
        startTransfersPool();
        Log.info("TRANSFERS END");

        Log.unBlockedAccountsCount(bank);
        Log.info("TEST END");

        BigInteger bankBalanceDiff = bank.getBankBalance().subtract(startBankBalance);
        Log.info("Difference with start bank balance: " + bankBalanceDiff);

        Assert.assertEquals(BigInteger.valueOf(0), bankBalanceDiff);
        Log.info("ASSERT END");
    }

    @After
    public void afterMethod() {
        bank = null;
    }

    private static void createRandomAccounts(Bank bank, int count) {
        int maxBalance = 2_000_000;
        for (int i = 0; i < count; i++) {
            long money = RANDOM.nextInt(maxBalance);
            bank.createAccount(money);
        }
    }

    private void startTransfersPool() {
        try {
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService es = Executors.newFixedThreadPool(processors);

            int poolSize = 1000;
            for (int i = 0; i < poolSize && bank.getUnblockedAccountsCount() >= 2; i++) {
            Log.debug("SUBMIT START " + i);
                es.submit(() -> randomTransfer(bank));
            Log.debug("SUBMIT END " + i);
            }
            Log.debug("---SHUTDOWN START");
            es.shutdown();
            Log.debug("---SHUTDOWN END");
            Log.debug("------AWAIT START");
            es.awaitTermination(1, TimeUnit.DAYS);
            Log.debug("------AWAIT END");

        } catch (InterruptedException e) {
            Log.error(e);
            Thread.currentThread().interrupt();
        }
    }

    private static void randomTransfer(Bank bank) {
        String msg = Thread.currentThread().getName();
        Log.debug("Transfer start:\t" + msg);

        // TODO the test takes a lot of time, need to use unblocked accounts only
//        String fromNum = getRandomUnblockedAccountNum(bank);
        String fromNum = bank.getRandomAccountNum();
        String toNum;
        do {
            // TODO the test takes a lot of time, need to use unblocked accounts only
//            toNum = getRandomUnblockedAccountNum(bank);
            toNum = bank.getRandomAccountNum();
        } while (toNum.equals(fromNum));
        long amount = (long) (bank.getBalance(fromNum) * RANDOM.nextDouble());

        bank.transfer(fromNum, toNum, amount);
        Log.debug("Transfer end:\t" + msg);
    }

    // TODO the test takes a lot of time, need to use unblocked accounts only
//    private synchronized static String getRandomUnblockedAccountNum(Bank bank) {
//        Account account = null;
//        try {
//
//            Field accountsField = Bank.class.getDeclaredField("accounts");
//            accountsField.setAccessible(true);
//            Map<String, Account> accounts =
//                    (ConcurrentHashMap<String, Account>) accountsField.get(bank);
//            do {
//                account = accounts.get(bank.getRandomAccountNum());
//            } while (account.isBlocked());
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            Log.error(e);
//            Assert.fail("Can't set account balance");
//        }
//        return account.getNumber();
//    }
}