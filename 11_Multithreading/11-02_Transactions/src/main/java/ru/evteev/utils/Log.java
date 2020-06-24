package ru.evteev.utils;

import ru.evteev.bank.Account;
import ru.evteev.bank.Bank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LogManager.getLogger("Log");

    public static void debug(String msg) {
        LOGGER.debug(msg);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void error(Exception e) {
        LOGGER.error(e);
        LOGGER.error(e.getStackTrace());
    }

    public static void timerStart() {
        String s = String.format("Start %s", Thread.currentThread().getName());
        debug(s);
    }

    public static void timerFinish(long time) {
        String s = String.format("Finish %s - %s", Thread.currentThread().getName(), time);
        debug(s);
    }

    public static void created(Object obj) {
        String msg = String.format("Created:        \t%s", obj.toString());
        info(msg);
    }

    public static void accountBlocked(Account account) {
        String msg = String.format("Blocked:        \t%s", account.toString());
        info(msg);
    }

    public static void accountUnBlocked(Account account) {
        String msg = String.format("Unblocked:      \t%s", account.toString());
        info(msg);
    }

    public static void callPolice(Account account) {
        String msg = String.format("Call police !!! \t%s", account.toString());
        info(msg);
    }

    public static void transfer(
            String fromAccountNum, String toAccountNum, long amount) {
        String thread = Thread.currentThread().getName();
        info(String.format("TRANSFER        \t%s -> %s  %10d rub\t%s",
                fromAccountNum, toAccountNum, amount, thread));
    }

    public static void accountState(Bank bank, String accountNum) {
        String accountState = String.format("Account %s\t%s rub",
                accountNum, bank.getBalance(accountNum));
        LOGGER.info(accountState);
    }

    public static void counters(AtomicInteger transfersCount, Bank bank) {
        debug(String.format("Transfers count:      \t%d",
                transfersCount.intValue()));

        debug(String.format("Ext.fraud check count:\t%d",
                bank.extFraudCheckCount.intValue()));

        debug(String.format("Call Police count:    \t%d",
                bank.callPoliceCount.intValue()));
    }

    public static void unBlockedAccountsCount(Bank bank) {
        info("Unblocked accounts:\t" + (bank.getUnblockedAccountsCount()));
    }
}
