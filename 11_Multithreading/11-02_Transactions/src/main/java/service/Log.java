package service;

import bank.Account;
import bank.Bank;
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
        LOGGER.debug(s);
    }

    public static void timerFinish(long time) {
        String s = String.format("Finish %s - %s", Thread.currentThread().getName(), time);
        LOGGER.debug(s);
    }

    public static void created(Object obj) {
        String msg = String.format("Created:        \t%s", obj.toString());
        LOGGER.info(msg);
    }

    public static void blocked(Account account) {
        String msg = String.format("Blocked:        \t%s", account.toString());
        LOGGER.info(msg);
    }

    public static void unBlocked(Account account) {
        String msg = String.format("Unblocked:      \t%s", account.toString());
        LOGGER.info(msg);
    }

    public static void callPolice(Account account) {
        String msg = String.format("Call police !!! \t%s", account.toString());
        LOGGER.info(msg);
    }

    public static void transfer(
            String fromAccountNum, String toAccountNum, long amount) {
        info(String.format("%nTRANSFER        \t%s -> %s  %10d rub",
                fromAccountNum, toAccountNum, amount));
    }

    public static void counters(AtomicInteger transfersCount, Bank bank) {
        String tc   = String.format("Transfers count:      \t%d",
                transfersCount.intValue());
        LOGGER.debug(tc);

        String efcc = String.format("Ext.fraud check count:\t%d",
                bank.extFraudCheckCount.intValue());
        LOGGER.debug(efcc);

        String cpc  = String.format("Call Police count:    \t%d",
                bank.callPoliceCount.intValue());
        LOGGER.debug(cpc);
    }

}
