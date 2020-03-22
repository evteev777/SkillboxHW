package Bank;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Record {

    String accountType;
    String accountNumber;
    String recCurrency;
    LocalDate recDate;
    String recReference;
    String payCardNumber;
    String payDescription;
    LocalDate payDate1;
    LocalDate payDate2;
    double payAmount;
    String payCurrency;
    String payReference;
    double income;
    double outcome;

    static double totalIncome;
    static double totalOutcome;
    static Set<String> categories = new TreeSet<>();

    public Record(String accountType, String accountNumber, String recCurrency, LocalDate recDate, String recReference,
                  String payCardNumber, String payDescription, LocalDate payDate1, LocalDate payDate2,
                  double payAmount, String payCurrency, String payReference, double income, double outcome) {

        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.recCurrency = recCurrency;
        this.recDate = recDate;
        this.recReference = recReference;
        this.payCardNumber = payCardNumber;
        this.payDescription = payDescription;
        this.payDate1 = payDate1;
        this.payDate2 = payDate2;
        this.payAmount = payAmount;
        this.payCurrency = payCurrency;
        this.payReference = payReference;
        this.income = income;
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return String.format("%10s | %s | %s | %td.%tm.%tY | %-16s | %15s | " +
                        "%-50s | %td.%tm.%tY | %td.%tm.%tY | %12s | %s | %25s | %12s | %12s |",
                accountType, accountNumber, recCurrency, recDate, recDate, recDate, recReference,
                payCardNumber, payDescription, payDate1, payDate1, payDate1, payDate1, payDate2, payDate2,
                payAmount, payCurrency, payReference, income, outcome);
    }

    public static ArrayList<Record> loadFromMovementList(String movementListFile) {

        // Record Indexes:
        final int ACCOUNT_TYPE = 0;
        final int ACCOUNT_NUMBER = 1;
        final int REC_CURRENCY = 2;
        final int REC_DATE = 3;
        final int REC_REFERENCE = 4;
        final int PAY = 5;
        final int INCOME = 6;
        final int OUTCOME = 7;

        // Pay Details Indexes:
        final int CARD_NUMBER = 0;
        final int PAY_DESCRIPTION = 1;
        final int PAY_DATE_1 = 2;
        final int PAY_DATE_2 = 3;
        final int PAY_AMOUNT = 4;
        final int PAY_CURRENCY = 5;
        final int PAY_REFERENCE = 6;

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy");
        ArrayList<Record> bankStatement = new ArrayList<>();

        try {
            List<String> records = Files.readAllLines(Paths.get(movementListFile));
            for (int i = 1; i < records.size(); i++) {

                String rec = records.get(i);
                rec = removeQuotes(rec);
                String[] recParts = rec.split("\\s*,\\s*");
//                Arrays.setAll(recParts, p -> recParts[p].trim());
                if (recParts.length != 8) {
                    System.err.println("Wrong record: \t" + recParts.length + "\t" + rec);
                    continue;
                }

                String pay = recParts[PAY];
                pay = pay.replaceAll("(\\d{6}\\+{6}\\d{4})( +)([\\w /\\\\>]+ )([\\d{2}.]+)( +)" +
                                "([\\d{2}.]+)( +)(\\d+.\\d{2})( +)(\\w{3})( +)([()\\w+ -]+)",
                        "$1,$3,$4,$6,$8,$10,$12");
                String[] payParts = pay.split("\\s*,\\s*");
//                Arrays.setAll(payParts, p -> payParts[p].trim());
                if (payParts.length != 7) {
                    System.err.println("Wrong record: \t" + payParts.length + "\t" + pay);
                    continue;
                }

                bankStatement.add(new Record(
                        recParts[ACCOUNT_TYPE],
                        recParts[ACCOUNT_NUMBER],
                        recParts[REC_CURRENCY],
                        LocalDate.parse(recParts[REC_DATE], dtf),
                        recParts[REC_REFERENCE],
                        payParts[CARD_NUMBER],
                        payParts[PAY_DESCRIPTION],
                        LocalDate.parse(payParts[PAY_DATE_1], dtf),
                        LocalDate.parse(payParts[PAY_DATE_2], dtf),
                        Double.parseDouble(payParts[PAY_AMOUNT]),
                        payParts[PAY_CURRENCY],
                        payParts[PAY_REFERENCE],
                        Double.parseDouble(recParts[INCOME]),
                        Double.parseDouble(recParts[OUTCOME])
                ));

                categories.add(payParts[PAY_DESCRIPTION]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankStatement;
    }

    private static String removeQuotes(String line) {

        if (line.contains("\"")) {
            String[] parts = line.split("\"");
            StringBuilder sb = new StringBuilder(parts[0]);

            for (int i = 1; i < parts.length - 1; i = i + 2) {
                sb.append(parts[i].replaceAll(",", ".")).append(",");
            }
            sb.append(parts[parts.length - 1].replaceAll(",", "."));
            return sb.toString();
        } else {
            return line;
        }
    }

    public static double getTotalIncome(List<Record> bankStatement) {
        for (Record rec : bankStatement) {
            totalIncome += rec.income;
        }
        return totalIncome;
    }

    public static double getTotalOutcome(List<Record> bankStatement) {
        for (Record rec : bankStatement) {
            totalOutcome += rec.outcome;
        }
        return totalOutcome;
    }

    public static void getIncomeByCategories(List<Record> bankStatement) {

        for (String category : categories) {
            double sum = 0.0;
            for (Record rec : bankStatement) {
                if (rec.income != 0.0 && rec.payDescription.equals(category)) {
                    System.out.println(rec.toString());
                    sum += rec.income;
                }
            }
            System.out.print(sum != 0 ? "ИТОГО приход по категории \"" + category + "\":\t" + sum + " RUB\n\n" : "");
        }
    }

    public static void getOutcomeByCategories(List<Record> bankStatement) {

        for (String category : categories) {
            double sum = 0.0;
            for (Record rec : bankStatement) {
                if (rec.outcome != 0.0 && rec.payDescription.equals(category)) {
                    System.out.println(rec.toString());
                    sum += rec.outcome;
                }
            }
            System.out.print(sum != 0 ? "ИТОГО расход по категории \"" + category + "\":\t" + sum + " RUB\n\n" : "");
        }
    }
}

