public class Main {

    public static void main(String[] args) throws InterruptedException {

        Bank bank = new Bank();

        String fromAccountNum = "407028101000000000001";
        String toAccountNum = "407028102000000000002";

        bank.createAccount(fromAccountNum, 1_000_000);
        bank.createAccount(toAccountNum, 2_000_000);

        bank.transfer(fromAccountNum, toAccountNum, 100_000);

        System.out.println(bank.getBalance(fromAccountNum));
        System.out.println(bank.getBalance(toAccountNum));
    }
}