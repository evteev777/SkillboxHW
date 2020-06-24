package ru.evteev.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.evteev.utils.Log;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankTest {

    private Bank bank;

    private long accountBalance;

    // TRANSFER:
    private String from; // Account "from" number
    private String to;   // Account "To" number
    private long amount;

    @Before
    public void setup() {
        bank = new Bank("Test Bank");

        accountBalance = 50_000; // equals fraud limit
        bank.createAccount(accountBalance);
        bank.createAccount(accountBalance);

        from = bank.getRandomAccountNum();
        to = getAnotherRandomAccountNum(from);

        amount = getRandomTransferAmount(from);
    }

    @Test
    public void createAccountTest() {
        amount = 2_000_000;
        int before = bank.getAccountsCount();
        bank.createAccount(amount);
        int after = bank.getAccountsCount();
        Assert.assertEquals(1, (long) after - before);
    }

    @Test
    public void noFraudTransferShouldBeCompleted() {
        bank.transfer(from, to, amount);
        Log.info("After transfer:");
        Log.accountState(bank, from);
        Log.accountState(bank, to);
        Assert.assertEquals(amount * 2,
                bank.getBalance(to) - bank.getBalance(from));
    }

    @Test
    public void transferWithZeroAmountShouldNotBeCompleted() {
        amount = 0;
        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferWithZeroFromAccBalanceShouldNotBeCompleted() {
        long balance = 0;
        setBalance(bank, from, balance);
        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferBetweenSameAccountShouldNotBeCompleted() {
        to = from;
        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferFromBlockedAccountShouldNotBeCompleted() {
        blockAccount(bank, from);
        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void transferToBlockedAccountShouldNotBeCompleted() {
        blockAccount(bank, to);
        Assert.assertFalse(isTransferChangedBalances(from, to, amount));
    }

    @Test
    public void randomAccountNumShouldNotBeNull() {
        Assert.assertNotNull(bank.getRandomAccountNum());
    }

    @Test
    public void getAccountsCountTest() {
        int count = 10;
        int bankAccountsCount = bank.getAccountsCount();
        for (int i = 0; i < count - bankAccountsCount; i++) {
            bank.createAccount(accountBalance);
        }
        Assert.assertEquals(count, bank.getAccountsCount());
    }

    @Test
    public void getBalanceTest() {
        Assert.assertEquals(accountBalance, bank.getBalance(from));
    }

    @Test
    public void getBankBalanceTest() {
        BigInteger bankBalance =
                BigInteger.valueOf(accountBalance * bank.getAccountsCount());
        Assert.assertEquals(bankBalance, bank.getBankBalance());
    }

    // SERVICE METHODS

    private String getAnotherRandomAccountNum(String from) {
        do {
            to = bank.getRandomAccountNum();
        } while (to.equals(from));
        return to;
    }

    private long getRandomTransferAmount(String from) {
        return (long) (bank.getBalance(from) * Math.random());
    }

    private boolean isTransferChangedBalances(String from, String to, long amount) {
        long fromBalanceBefore = bank.getBalance(from);
        long toBalanceBefore = bank.getBalance(to);

        bank.transfer(from, to, amount);

        boolean iaFromBalanceChanged = bank.getBalance(from) != fromBalanceBefore;
        boolean isToBalanceChanged = bank.getBalance(to) != toBalanceBefore;

        return iaFromBalanceChanged || isToBalanceChanged;
    }

    private void blockAccount(Bank bank, String from) {
        try {
            Field accountsField = Bank.class.getDeclaredField("accounts");
            accountsField.setAccessible(true);
            Map<String, Account> accounts =
                    (ConcurrentHashMap<String, Account>) accountsField.get(bank);
            Account account = accounts.get(from);
            account.block();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.error(e);
            Assert.fail("Account can't be blocked");
        }
    }

    private void setBalance(Bank bank, String from, long balance) {
        try {
            Field accountsField = Bank.class.getDeclaredField("accounts");
            accountsField.setAccessible(true);
            Map<String, Account> accounts =
                    (ConcurrentHashMap<String, Account>) accountsField.get(bank);
            Account account = accounts.get(from);
            account.setMoney(balance);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.error(e);
            Assert.fail("Can't set account balance");
        }
    }
}
