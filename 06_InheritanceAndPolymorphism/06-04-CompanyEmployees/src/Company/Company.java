package Company;

import Staff.Clerk;
import Staff.SalesManager;
import Staff.Staff;
import Staff.TopManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company implements Comparator {

    private static double companyProfit = 0.0;
    private static int dismissCount = 0; // количество уволенных сотрудников (для корректного присвоения id)

    private List<Staff> staff = new ArrayList<>();

    public Company() {

        // Первоначальный набор персонала
        for (int id =   0; id <=  4; id++) {recruitClerk(id);}
        for (int id =  5; id <= 9; id++) {recruitSalesManager(id);}
        for (int id = 10; id <= 14; id++) {recruitTopManager(id);}

        // Донабор и увольнение персонала
//        dismissStuff(2);
        recruitSalesManager();
//        recruitClerk();
        dismissStuff(7);
        dismissStuff(8);
//        recruitTopManager();
//        recruitSalesManager();
//        dismissStuff(12);
//        recruitClerk();
//        recruitTopManager();
//        dismissStuff(15);
//        recruitClerk();
//        recruitSalesManager();
//        dismissStuff(17);

//        print(staff, "all");
        print(staff, "Операционист");
        print(staff, "Менеджер по продажам");
        print(staff, "Топ-менеджер");

        getTopSalaryStaff(staff, 3);
        getLowestSalaryStaff(staff, 3);

        System.out.println("\nПрибыль компании: " + Math.round(companyProfit));
        checkPayroll(staff);
    }

    // Первоначальный набор сотрудников (id присваивается в цикле)
    private void recruitClerk(int id) {
        staff.add(new Clerk(id, "Операционист"));
    }

    private void recruitSalesManager(int id) {
//        double companyProfitBeforeRecruit = companyProfit;

        staff.add(new SalesManager(id, "Менеджер по продажам"));

        if (Company.companyProfit >= 300000.0) {
            for (Staff employee : staff) {
                if (employee.getCategory().equals("Топ-менеджер")) {
                    employee.setSalary(employee.salary());
                    System.out.println("После приема на работу менеджера по продажам " +
                            "прибыль компании увеличилась и стала больше 10 млн \n" +
                            "Топ-менеджеру " + employee.getId() + " - " + employee.getFullName() +
                            " - увеличена з/п: " + employee.getSalary() + "\n");
                }
            }
        }
    }

    private void recruitTopManager(int id) {
        staff.add(new TopManager(id, "Топ-менеджер"));
    }

    // Донабор сотрудников (id присваивается расчетным путем)
    private void recruitClerk() {
        recruitClerk(staff.size() + dismissCount);
    }

    private void recruitSalesManager() {
        recruitSalesManager(staff.size() + dismissCount);
    }

    private void recruitTopManager() {
        recruitTopManager(staff.size() + dismissCount);
    }

    // Увольнение сотрудников
    private void dismissStuff(int id) {

        int index = id - dismissCount;
        setCompanyProfit(getCompanyProfit() - staff.get(index).getManagerProfit());

        staff.remove(staff.get(index));
        dismissCount++;

        if (Company.companyProfit < 300000.0) {
            for (Staff employee : staff) {
                if (employee.getCategory().equals("Топ-менеджер")) {
                    employee.setSalary(TopManager.getFixSalary());
                    System.out.println("После увольнения менеджера по продажам " +
                            "прибыль компании уменьшилась и стала меньше 10 млн \n" +
                            "Топ-менеджеру " + employee.getId() + " - " + employee.getFullName() +
                            " - уменьшена з/п: " + employee.getSalary() + "\n");
                }
            }
        }
    }

    // Поиск максимальных и минимальных зарплат
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    private static void getTopSalaryStaff(List<Staff> staff, int count) {
        staff.sort((o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary()));
        System.out.println("\nТРИ МАКСИМАЛНЫЕ З/П:");
        for (int i = 0; i < count; i++) {
            System.out.println(staff.get(i));
        }
    }

    private static void getLowestSalaryStaff(List staff, int count) {
        Collections.reverse(staff);
        System.out.println("\nТРИ МИНИМАЛНЫЕ З/П:");
        for (int i = 0; i < count; i++) {
            System.out.println(staff.get(i));
        }
    }

    // Проверка, что прибыль больше общего фонда з/п
    private static void checkPayroll(List<Staff> staff) {
        double payroll = 0.0;
        for (Staff staff1 : staff) {payroll += staff1.getSalary();}
        System.out.println("Фонд з/п: " + Math.round(payroll));
        if (payroll > companyProfit) {
            System.out.println("!!! Фонд з/п превышает прибыль !!!");
        }
    }

    // Вывод в консоль
    private static void print(List<Staff> staff, String category) {

        if(category.equals("all")) {System.out.println("\nВЕСЬ ПЕРСОНАЛ КОМПАНИИ:");}
        if(category.equals("Операционист")) {System.out.println("\nОПЕРАЦИОНИСТЫ:");}
        if(category.equals("Менеджер по продажам")) {System.out.println("\nМЕНЕДЖЕРЫ ПО ПРОДАЖАМ:");}
        if(category.equals("Топ-менеджер")) {System.out.println("\nТОП-МЕНЕДЖЕРЫ:");}

        for (Staff staff1 : staff) {
            if (staff1.getCategory().equals(category)) {
                System.out.println((staff1.getId()) + ". " + staff1.getCategory() + ": " + staff1.getFullName() +
                        " - Опыт " + staff1.getExperience() + " - з/п " + staff1.getSalary());
            }
            if (category.equals("all")) {
                System.out.println((staff1.getId()) + ". " + staff1.getCategory() + ": " + staff1.getFullName() +
                        " - Опыт " + staff1.getExperience() + " - з/п " + staff1.getSalary());
            }
        }
    }

    // Геттеры, сеттеры
    public static double getCompanyProfit() {
        return companyProfit;
    }

    public static void setCompanyProfit(double companyProfit) {
        Company.companyProfit = companyProfit;
    }
}