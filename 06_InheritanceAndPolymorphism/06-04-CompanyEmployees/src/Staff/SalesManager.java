package Staff;

import Company.Company;

public class SalesManager extends Staff {

    public SalesManager(int id, String category) {
        super(id, category);
    }

    @Override
    public double salary() {
//        this.managerProfit = 20000 + 80000 * Math.random();
        this.managerProfit = 60000;

        Company.setCompanyProfit(Company.getCompanyProfit() + managerProfit);

        this.fixSalary = 25000.0;
        this.bonus = 25.0; // в процентах от заработанной им прибыли
        this.bonusAmount = (managerProfit * (bonus + super.getExperience()) / 100);

        return Math.round(fixSalary + bonusAmount);
    }
}