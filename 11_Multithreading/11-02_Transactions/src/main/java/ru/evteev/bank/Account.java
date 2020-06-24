package ru.evteev.bank;

import ru.evteev.utils.Log;

public class Account {

    private final Bank bank;
    private final String number;
    private long money;
    private boolean isBlocked;

    Account(Bank bank, String number, long money) {
        this.bank = bank;
        this.number = number;
        this.money = money;
        this.isBlocked = false;
        Log.created(this);
    }

    synchronized boolean isBlocked() {
        return isBlocked;
    }

    synchronized void block() {
        isBlocked = true;
        Log.accountBlocked(this);
    }

    synchronized void unBlock() {
        isBlocked = false;
        Log.accountUnBlocked(this);
    }

    void callPolice() {
        // Any code for call to police about a fraud transfer
        Log.callPolice(this);
        Log.unBlockedAccountsCount(this.bank);
    }

    synchronized String getNumber() {
        return number;
    }

    synchronized void writeOff(long amount) {
        money -= amount;
    }

    synchronized void writeOn(long amount) {
        money += amount;
    }

    synchronized long getMoney() {
        return money;
    }

    synchronized void setMoney(long money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return String.format("Account %4s\t%8d rub\t%s",
                number, money, (isBlocked ? "Blocked" : "Not blocked"));
    }
}
