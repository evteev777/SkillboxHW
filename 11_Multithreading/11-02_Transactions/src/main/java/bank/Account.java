package bank;

import service.Log;

public class Account {

    private final String number;
    private long money;
    private boolean isBlocked;

    public Account(String number, long money) {
        this.number = number;
        this.money = money;
        this.isBlocked = false;
        Log.created(this);
    }

    boolean isBlocked() {
        return isBlocked;
    }

    void block() {
        isBlocked = true;
        Log.blocked(this);
    }

    void unBlock() {
        isBlocked = false;
        Log.unBlocked(this);
    }

    void callPolice() {
        // Some code for call to police about a fraud transfer
        Log.callPolice(this);
    }

    String getNumber() {
        return number;
    }

    long getMoney() {
        return money;
    }

    void setMoney(long money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return String.format("Account %4s\t%8d rub\t%s",
                number, money, (isBlocked ? "Blocked" : "Not blocked"));
    }
}
