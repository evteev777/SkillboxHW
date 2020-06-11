package bank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

    Account account;

    @BeforeClass
    public static void globalSetup() {
    }


    @Before
    public void setup() {
        String number = "1000";
        long money = 1000;
        account = new Account(number, money);
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

    @After
    public void afterMethod() {
        account = null;
    }

    @AfterClass
    public static void tearDown() {
    }
}
