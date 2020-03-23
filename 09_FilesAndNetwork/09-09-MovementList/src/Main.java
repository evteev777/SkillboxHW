import Bank.Record;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        final String movementListFile = "data/movementList.csv";

        List<Record> bankStatement = Record.loadFromMovementList(movementListFile);

//        System.out.println("Общая выписка после обработки: \n");
//        for (Record record : bankStatement) {
//            System.out.println(record.toString());
//        }

        System.out.println("Общий приход по категориям: \n");
        Record.getIncomeByCategories(bankStatement);
        System.out.println("Общий приход " + Record.getTotalIncome(bankStatement) + "\n\n");

        System.out.println("Общий расод по категориям: \n");
        Record.getOutcomeByCategories(bankStatement);
        System.out.println("Общий расход " + Record.getTotalOutcome(bankStatement) + "\n\n");
    }
}
