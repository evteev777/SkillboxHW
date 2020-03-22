package Staff;

import Company.Company;

public class TopManager extends Staff {

    public TopManager(int id, String category) {
        super(id, category);
    }

    private static double fixSalary = 75000.0;

    @Override
    public double salary() {
        this.bonus = 15.0; // в процентах от общей прибыли
        this.bonusAmount = 0;
        if (Company.getCompanyProfit() >= 300001.0) {bonusAmount = fixSalary * bonus * super.getExperience() / 100;}

        return Math.round(fixSalary + bonusAmount);
    }

    public static double getFixSalary() {
        return fixSalary;
    }
}