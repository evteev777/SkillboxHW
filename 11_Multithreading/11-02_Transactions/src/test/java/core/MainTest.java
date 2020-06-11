package core;

import bank.Bank;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class MainTest {

    private static final Random RANDOM = new Random();
    Bank bank;

    @Before
    public void serUp() {
        bank = new Bank("Test Bank");

        int accountsCount = 3;
        int maxBalance = 1_000_000;
        for (int i = 0; i < accountsCount; i++) {
            long money = RANDOM.nextInt(maxBalance);
            bank.createAccount(money);
        }
    }

    @Test
    public void bankBalanceShouldNotBeChanged() {
        BigInteger startBankBalance = bank.getBankBalance();

        // To make a transfer, at least two accounts must be not blocked
        while (bank.getAccountsCount() - bank.callPoliceCount.intValue() >= 2) {
            String from = bank.getRandomAccountNum();
            String to;
            do {
                to = bank.getRandomAccountNum();
            } while (to.equals(from));
            long amount = (long) (bank.getBalance(from) * RANDOM.nextDouble());

            bank.transfer(from, to, amount);
        }
        BigInteger diff = bank.getBankBalance().subtract(startBankBalance);
        Assert.assertEquals(BigInteger.valueOf(0), diff);
    }

    @After
    public void afterMethod() {
        bank = null;
    }
}