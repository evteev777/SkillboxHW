import java.math.RoundingMode;
import java.util.Scanner;

public class Main
{

    // объявляем перееменные нижнего и верхнего дохода компании
    private static int minIncome = 200000;
    private static int maxIncome = 900000;

    // объявляем перееменные оплаты за аренду, телефон и интернет
    private static int officeRentCharge = 140000;
    private static int telephonyCharge = 12000;
    private static int internetAccessCharge = 7200;

    // объявляем перееменные зарплаты асистентов и фин. менеджеров
    private static int assistantSalary = 45000;
    private static int financeManagerSalary = 90000;

    // объявляем перееменные налога и процента менеджеров
    private static double mainTaxPercent = 0.24;
    private static double managerPercent = 0.15;

    // объявляем перееменную минимальной суммы инвестиций
    private static double minInvestmentsAmount = 100000;

    public static void main(String[] args)
    {
        // бесконечный цикл
        while(true)
        {
            // Из имеющихся формул рассчитываем минимальный доход, при котором можно инвестировать:
            // pureIncomeAfterTax = minInvestmentsAmount
            // pureIncome - taxAmount = minInvestmentsAmount
            // pureIncome - mainTaxPercent * pureIncome = minInvestmentsAmount
            // pureIncome * (1 - mainTaxPercent) = minInvestmentsAmount
            // pureIncome = minInvestmentsAmount / (1 - mainTaxPercent)
            // income - managerSalary - calculateFixedCharges() = minInvestmentsAmount / (1 - mainTaxPercent)
            // income - income * managerPercent = calculateFixedCharges() + minInvestmentsAmount / (1 - mainTaxPercent)
            // income * (1 - managerPercent) = calculateFixedCharges() + minInvestmentsAmount / (1 - mainTaxPercent)
            // income = (calculateFixedCharges() + minInvestmentsAmount / (1 - mainTaxPercent)) / (1 - managerPercent)

            // иокругляем вверх до целого числа, и  записываем результат в переменную incomeInvest:
            double incomeInvest = Math.ceil((calculateFixedCharges() + minInvestmentsAmount / (1 - mainTaxPercent)) / (1 - managerPercent));

            // Запрашиваем и получаем вводом с клавиатуры
            // сумму реализации за месяц (income)
            System.out.println("Введите сумму доходов компании за месяц " + "(от 200000 до 900000 рублей),");
            // выводим сумму, при которой можно инвестировать
            System.out.println("но не менее " + incomeInvest + " рублей, чтобы инвестировать");
            int income = (new Scanner(System.in)).nextInt();

            // если не-checkIncomeRange = true
            // (то есть checkIncomeRange = false) -
            // обрабатывается метод checkIncomeRange в конце программы,
            // и выводится сообщение, что введенные данные
            // не в диапазоне minIncome-maxIncome
            if(!checkIncomeRange(income)) {
                // и возвращаемся к началу бесконечного цикла
                continue;
            }

            // если checkIncomeRange = true -
            // производятся следующие вычисления:

            // рассчитываем зарплату менеджеров (процент от реализации)
            double managerSalary = income * managerPercent;
            // рассчитываем чистый доход:
            // реализация за вычетом зарплаты менеджеров
            // и постоянных расходов (метод calculateFixedCharges в конце программы)
            double pureIncome = income - managerSalary -
                calculateFixedCharges();
            // рассчитываем сумму налога
            // как доход за вычетом зарплаты менеджеров
            // умноженный на ставку налога
            double taxAmount = mainTaxPercent * pureIncome;
            // рассчитываем чистый доход после налогообложения
            double pureIncomeAfterTax = pureIncome - taxAmount;

            // объявляем переменную возможости инвестирования:
            // присваиваем ей значение true, если доход после налогообложения
            // больше или равен минимальной сумме инвестиций,
            // иначе false
            boolean canMakeInvestments = pureIncomeAfterTax >=
                minInvestmentsAmount;

            // Выводим сообщения в консоль:
            // Зарплата менеджера
            System.out.println("Зарплата менеджера: " + managerSalary);
            // Тернарный оператор:
            // Общая сумма налогов, если она больше 0,
            // иначе выводим 0
            System.out.println("Общая сумма налогов: " +
                (taxAmount > 0 ? taxAmount : 0));
            // Возможность инвестиций  - да/нет
            // тернарный оператор в зависимости от canMakeInvestments
            // Добавил вывод суммы возможных инвестиций вместо "да"
            System.out.println("Компания может инвестировать: " +
                (canMakeInvestments ? pureIncomeAfterTax + " рублей" : "нет"));
            // Если получили убыток (отрицательный доход) - сообщаем об этом
            if(pureIncome < 0) {
                System.out.println("Бюджет в минусе! Нужно срочно зарабатывать!");
            }
            // добавил выход из бесконечного цикла при успешном расчете
            break;
        //
        }
    }

    // проверка введенного с клавиатуы значения,
    // чтобы оно находилось в диапазоне minIncome-maxIncome,
    // если это не так - возвращаемся к началу цикла while
    //
    private static boolean checkIncomeRange(int income)
    {
        if(income < minIncome)
        {
            System.out.println("Доход меньше нижней границы");
            return false;
        }
        if(income > maxIncome)
        {
            System.out.println("Доход выше верхней границы");
            return false;
        }
        return true;
    }

    // расчет постоянных расходов:
    // расходы на аренду, телефон, интернет
    // плюс зарплата ассистентов и фин менеджеров
    private static int calculateFixedCharges()
    {
        return officeRentCharge +
                telephonyCharge +
                internetAccessCharge +
                assistantSalary +
                financeManagerSalary;
    }
}