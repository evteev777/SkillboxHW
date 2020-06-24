package ru.evteev.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

    Account account;

    @Before
    public void setup() {
        Bank bank = new Bank("Test bank");
        String number = "1000";
        long money = 1000;
        account = new Account(bank, number, money);
    }

    @Test
    public void newAccountShouldNotBeBlocked() {
        Assert.assertFalse(account.isBlocked());
    }

    @Test
    public void accountAfterBlockShouldBeBlocked() {
        account.block();
        Assert.assertTrue(account.isBlocked());
    }

    @Test
    public void accountAfterUnlockShouldNotBeBlocked() {
        account.unBlock();
        Assert.assertFalse(account.isBlocked());
    }
}
