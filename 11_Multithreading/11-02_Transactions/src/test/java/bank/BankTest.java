package bank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankTest {

    long fraudAmountLimit;

    Bank bank;

    @BeforeClass
    public static void globalSetup() {
    }

    @Before
    public void setup() {
        bank = new Bank("Test Bank");
        fraudAmountLimit = 50_000;
    }

    @Test
    public void createAccountTest() {
        long amount = 2_000_000;
        int before = bank.getAccountsCount();
        bank.createAccount(amount);
        int after = bank.getAccountsCount();
        Assert.assertEquals(1, after - before);
    }

    @Test
    public void noFraudTransferShouldBeCompleted() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        bank.createAccount(balance);

        String from = bank.getRandomAccountNum();

        String to;
        do {
            to = bank.getRandomAccountNum();
        } while (to.equals(from));

        bank.transfer(from, to, balance);

        Assert.assertEquals(balance * 2, bank.getBalance(to) - bank.getBalance(from));
    }

    @Test
    public void fraudTransferShouldNotBeCompleted() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        bank.createAccount(balance);

        String from = bank.getRandomAccountNum();

        String to;
        do {
            to = bank.getRandomAccountNum();
        } while (to.equals(from));

        bank.transfer(from, to, balance);

        Assert.assertEquals(balance * 2, bank.getBalance(to) - bank.getBalance(from));
    }

    @Test
    public void transferWithZeroAmountShouldNotBeCompleted() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        bank.createAccount(balance);

        String from = bank.getRandomAccountNum();
        String to;
        do {
            to = bank.getRandomAccountNum();
        } while (to.equals(from));

        long amount = 0;

        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferWithZeroFromAccBalanceShouldNotBeCompleted() {
        long balance = 0;
        bank.createAccount(balance);
        bank.createAccount(balance);

        String from = bank.getRandomAccountNum();
        String to = bank.getRandomAccountNum();
        long amount = 100;

        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferBetweenSameAccountShouldNotBeCompleted() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        bank.createAccount(balance);

        String accountNum = bank.getRandomAccountNum();
        long amount = 100;

        Assert.assertFalse(isTransferChangedBalances(accountNum, accountNum, amount));
    }

    private boolean isTransferChangedBalances(String from, String to, long amount) {
        long fromBalanceBefore = bank.getBalance(from);
        long toBalanceBefore = bank.getBalance(to);

        bank.transfer(from, to, amount);

        boolean iaFromBalanceChanged = bank.getBalance(from) != fromBalanceBefore;
        boolean isToBalanceChanged = bank.getBalance(to) != toBalanceBefore;

        return iaFromBalanceChanged | isToBalanceChanged;
    }

    @Test
    public void getBalanceTest() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        String accountNum = bank.getRandomAccountNum();
        Assert.assertEquals(balance, bank.getBalance(accountNum));
    }

    @Test
    public void randomAccountNumShouldNotBeNull() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        Assert.assertNotNull(bank.getRandomAccountNum());
    }

    @Test
    public void getAccountsCountTest() {
        int count = 100;
        long balance = fraudAmountLimit;
        for (int i = 0; i < count; i++) {
            bank.createAccount(balance);
        }
        Assert.assertEquals(count, bank.getAccountsCount());
    }

    @Test
    public void getBankBalanceTest() {
        long balance = fraudAmountLimit;
        bank.createAccount(balance);
        String accountNum = bank.getRandomAccountNum();
        Assert.assertEquals(balance, bank.getBalance(accountNum));
    }

    @After
    public void afterMethod() {
        bank = null;
    }

    @AfterClass
    public static void tearDown() {
    }
}
