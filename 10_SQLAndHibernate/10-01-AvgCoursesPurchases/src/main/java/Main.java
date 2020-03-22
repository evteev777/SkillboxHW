import java.sql.*;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox?serverTimezone=Europe/Moscow";
        String user = "root";
        String pass = "password";
        // Среднее количество продаж в месяц по каждому курсу:
        // за период - с первой до последней продажи, и за год - с первой продажи до конца года
        final String SQL_QUERY_AVG = "SELECT PurchaseList.course_name, Grp.count AS purchase_count, " +
                "Grp.min_date AS first_purchase_date, Grp.max_date AS last_purchase_date, " +
                "Grp.count / (MONTH (Grp.max_date) - MONTH (Grp.min_date) + 1) AS period_avg_purchases_count, " +
                "Grp.count / (12 - MONTH (Grp.min_date) + 1) AS year_avg_purchases_count " +
                "FROM PurchaseList JOIN(SELECT course_name, " +
                "MIN(subscription_date) min_date, MAX(subscription_date) max_date, COUNT(*) count " +
                "FROM PurchaseList GROUP BY course_name) Grp " +
                "ON PurchaseList.course_name = Grp.course_name AND PurchaseList.subscription_date = Grp.max_date " +
                "ORDER BY course_name;";
        // Среднее количество продаж каждого курса с группировкой по месяцам
        final String SQL_QUERY_MONTH = "SELECT name AS course_name, " +
                "Grp.p_date AS purchase_month, Grp.p_count AS purchase_count FROM Courses " +
                "JOIN (SELECT course_name, MONTH(subscription_date) p_date, COUNT(*) p_count " +
                "FROM PurchaseList GROUP BY course_name, p_date) Grp " +
                "ON name = course_name " +
                "ORDER BY name , purchase_month;";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            getAvgPurchases(statement, SQL_QUERY_AVG);
            getPurchasesPerMonth(statement, SQL_QUERY_MONTH);

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getAvgPurchases(Statement statement, String SQL_QUERY_AVG) throws SQLException {
        ResultSet resultSet_avg = statement.executeQuery(SQL_QUERY_AVG);

        System.out.printf("%-35s | %14s | %21s | %21s | %17s | %14s |\n",
                "НАЗВАНИЕ КУРСА", "ПРОДАНО КУРСОВ", "ДАТА  НАЧАЛА  ПРОДАЖ",
                "ДАТА ОКОНЧАНИЯ ПРОДАЖ", "СРЕДНЕЕ ЗА ПЕРИОД", "СРЕДНЕЕ ЗА ГОД");

        while (resultSet_avg.next()) {
            System.out.printf("%-35s | %14s | %21s | %21s | %17s | %14s |\n",
                    resultSet_avg.getString("course_name"),
                    resultSet_avg.getString("purchase_count"),
                    resultSet_avg.getString("first_purchase_date"),
                    resultSet_avg.getString("last_purchase_date"),
                    resultSet_avg.getString("period_avg_purchases_count"),
                    resultSet_avg.getString("year_avg_purchases_count"));
        }
        resultSet_avg.close();
    }

    private static void getPurchasesPerMonth(Statement statement, String SQL_QUERY_MONTH) throws SQLException {
        ResultSet resultSet_month = statement.executeQuery(SQL_QUERY_MONTH);

        System.out.printf("\n%-35s | %5s | %14s |\n",
                "НАЗВАНИЕ КУРСА", "МЕСЯЦ", "ПРОДАНО КУРСОВ");

        while (resultSet_month.next()) {
            System.out.printf("%-35s | %5s | %14s |\n",
                    resultSet_month.getString("course_name"),
                    resultSet_month.getString("purchase_month"),
                    resultSet_month.getString("purchase_count"));
        }
        resultSet_month.close();
    }
}