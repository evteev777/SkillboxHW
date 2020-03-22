package Staff;

import Names.FullName;

public class Staff implements Employee {

    private int id;
    private String fullName;
    private String category;
    private double experience;

    double fixSalary;
    double managerProfit;
    double bonus;
    double bonusAmount;

    private double salary;

    Staff(int id, String category) {
        this.id = id;
        this.fullName = FullName.create();
        this.category = category;
        this.experience = 1 + Math.random() * 4.9;
        this.salary = salary();
    }

    public double salary() {
        this.managerProfit = 0.0;
        this.fixSalary = 0.0;
        this.bonus = 0.0;
        this.bonusAmount = (bonus + experience) / 100;
        return Math.round(fixSalary + bonusAmount);
    }

    public String toString() {
        return (id + ". " + category + " - " + fullName + " - з/п " + salary);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public double getExperience() {
        return experience;
    }

    public double getManagerProfit() {
        return managerProfit;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }
}