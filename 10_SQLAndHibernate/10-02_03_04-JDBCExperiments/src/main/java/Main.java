import entities.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    public static void main(String[] args) {

        Logger appLogger = LogManager.getLogger("appLog");
        appLogger.debug("Program start");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(registry).buildMetadata();

        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
             Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();

            appLogger.info( "TEACHER MAPPING:");
            final int TEACHER_ID = 1;
            teacherMapping(TEACHER_ID, session, appLogger);

            appLogger.info( "COURSE MAPPING:");
            final int COURSE_ID = 1;
            courseMapping(COURSE_ID, session, appLogger);

            appLogger.info( "STUDENT MAPPING:");
            final int STUDENT_ID = 1;
            studentMapping(STUDENT_ID, session, appLogger);

            appLogger.info( "SUBSCRIPTION MAPPING:");
            final int COURSE_ID_2 = 1;
            final int SUBSCRIPTION_NUMBER = 1;
            subscriptionMapping(COURSE_ID_2, SUBSCRIPTION_NUMBER ,dateFormat, session, appLogger);

            appLogger.info( "PURCHASE MAPPING:");
            final int COURSE_ID_3 = 1;
            final int PURCHASE_NUMBER = 0;
            purchaseMapping(COURSE_ID_3, PURCHASE_NUMBER ,dateFormat, session, appLogger);

            appLogger.info( "TOTAL MAPPING:");
            final int COURSE_ID_4 = 10;
            totalMapping(COURSE_ID_4 ,dateFormat, session, appLogger);

            appLogger.info( "ADD NEW TABLE:");

        } catch (Exception e) {
            e.printStackTrace();
        }
        appLogger.debug( "Program completed");
    }

    private static void teacherMapping(int TEACHER_ID, Session session, Logger appLogger) {
        Teacher teacher = session.get(Teacher.class, TEACHER_ID);
        appLogger.info(String.format("- Teacher ID_%d: %s", TEACHER_ID, teacher.getName()));
        System.out.println();
    }

    private static void courseMapping(int COURSE_ID, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_ID);
        appLogger.info(String.format("- Course  ID_%d: %s", COURSE_ID, course.getName()));
        System.out.println();
    }

    private static void studentMapping(int STUDENT_ID, Session session, Logger appLogger) {
        Student student = session.get(Student.class, STUDENT_ID);
        appLogger.info(String.format("- Student ID_%d: %s", STUDENT_ID, student.getName()));
        System.out.println();
    }

    private static void subscriptionMapping(int COURSE_ID, int SUBSCRIPTION_NUMBER,
                                            SimpleDateFormat dateFormat, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_ID);
        Subscription.Key key = course.getSubscriptions().get(SUBSCRIPTION_NUMBER).getSubscriptionKey();
        Subscription subscription = session.get(Subscription.class, key);
        appLogger.info("- Subscription number: \t" + SUBSCRIPTION_NUMBER);
        appLogger.info("- Subscription date:   \t" + dateFormat.format(subscription.getSubscriptionDate()));
        appLogger.info("- Subscription course: \t" + subscription.getSubscriptionKey().getCourse().getName());
        appLogger.info("- Subscription student:\t" + subscription.getSubscriptionKey().getStudent().getName());
        System.out.println();
    }

    private static void purchaseMapping(int COURSE_ID, int PURCHASE_NUMBER,
                                            SimpleDateFormat dateFormat, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_ID);
        Purchase.Key key = course.getPurchases().get(PURCHASE_NUMBER).getPurchaseKey();
        Purchase purchase = session.get(Purchase.class, key);
        appLogger.info("- Purchase number: \t" + PURCHASE_NUMBER);
        appLogger.info("- Purchase date:   \t" + dateFormat.format(purchase.getSubscriptionDate()));
        appLogger.info("- Purchase course: \t" + purchase.getPurchaseKey().getCourse().getName());
        appLogger.info("- Purchase student:\t" + purchase.getPurchaseKey().getStudent().getName());
        appLogger.info("- Purchase price:\t" + purchase.getPrice());
        System.out.println();
    }

    private static void totalMapping(int COURSE_NUMBER,
                                     SimpleDateFormat dateFormat, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_NUMBER);

        appLogger.info("- Course: " + course.getName());
        appLogger.info("- Course teacher: " + course.getTeacher().getName());
        System.out.println();

        List<Course> teacherCourses = course.getTeacher().getCourses();
        for (int i = 0; i < teacherCourses.size(); i++) {
            Course teacherCourse = teacherCourses.get(i);

            appLogger.info(String.format("\t%d. Teacher: %s,\tCourse: %s",
                    i + 1, teacherCourse.getTeacher().getName(), teacherCourse.getName()));
            System.out.println();

            courseSubscriptions(teacherCourse, dateFormat, appLogger);
            studentsOnCourse(teacherCourse, dateFormat, appLogger);
        }
    }

    private static void courseSubscriptions(Course course, SimpleDateFormat dateFormat, Logger appLogger) {
        List<Subscription> courseSubscriptions = course.getSubscriptions();
        courseSubscriptions.sort(Comparator.comparing(Subscription::getSubscriptionDate));
        for (int i = 0; i < courseSubscriptions.size(); i++) {
            Subscription subscription = courseSubscriptions.get(i);

            appLogger.info(String.format("\t\t%d. Subscription:\t%s %s", i + 1,
                    dateFormat.format(subscription.getSubscriptionDate()),
                    subscription.getSubscriptionKey().getStudent().getName()));
        }
        System.out.println();
    }

    private static void studentsOnCourse(Course teacherCourse, SimpleDateFormat dateFormat, Logger appLogger) {
        List<Student> studentsOnCourse = teacherCourse.getStudents();
        for (int i = 0; i < studentsOnCourse.size(); i++) {
            Student student = studentsOnCourse.get(i);

            appLogger.info(String.format("\t\t%d. Student:\t%s", (i + 1), student.getName()));

            List<Subscription> studentSubscriptions = student.getSubscriptions();
            studentSubscriptions.sort(Comparator.comparing(Subscription::getSubscriptionDate));
            for (int j = 0; j < studentSubscriptions.size(); j++) {
                Subscription subscription = studentSubscriptions.get(j);

                appLogger.info(String.format("\t\t\t%d. Subscription:\t%s %s", j + 1,
                        dateFormat.format(subscription.getSubscriptionDate()),
                        subscription.getSubscriptionKey().getCourse().getName()));
            }
            System.out.println();
        }
    }
}
