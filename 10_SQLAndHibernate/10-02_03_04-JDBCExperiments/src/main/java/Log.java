import entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

public class Log {

    private static final Logger LOGGER = LogManager.getLogger("appLog");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");


    public static void debug(String s) {
        LOGGER.debug(s);
    }

    public static void info(String s) {
        LOGGER.info(s);
    }


    static void teacherMapping(int TEACHER_ID, Teacher teacher) {
        info(String.format("- Teacher ID_%d: %s", TEACHER_ID, teacher.getName()));
        System.out.println();
    }
    
    static void courseMapping(int COURSE_ID, Course course) {
        info(String.format("- Course  ID_%d: %s", COURSE_ID, course.getName()));
        System.out.println();
    }
    
    static void studentMapping(int STUDENT_ID, Student student) {
        info(String.format("- Student ID_%d: %s", STUDENT_ID, student.getName()));
        System.out.println();
    }
    
    static void subscriptionMapping(int SUBSCRIPTION_NUMBER, Subscription subscription) {
        info("- Subscription number: \t" + SUBSCRIPTION_NUMBER);
        info("- Subscription date:   \t" + DATE_FORMAT.format(subscription.getSubscriptionDate()));
        info("- Subscription course: \t" + subscription.getSbsKey().getCourse().getName());
        info("- Subscription student:\t" + subscription.getSbsKey().getStudent().getName());
        System.out.println();
    }
    
    static void purchaseMapping(int PURCHASE_NUMBER, Purchase purchase) {
        info("- Purchase number: \t" + PURCHASE_NUMBER);
        info("- Purchase date:   \t" + DATE_FORMAT.format(purchase.getSubscriptionDate()));
        info("- Purchase course: \t" + purchase.getPKey().getCourse().getName());
        info("- Purchase student:\t" + purchase.getPKey().getStudent().getName());
        info("- Purchase price:\t" + purchase.getPrice());
        System.out.println();
    }
    
    static void totalMapping(Course course) {
        info("- Course: " + course.getName());
        info("- Course teacher: " + course.getTeacher().getName());
        System.out.println();
    }
    
    static void teacherCourse(int i, Course teacherCourse) {
        info(String.format("\t%d. Teacher: %s,\tCourse: %s",
                i + 1, teacherCourse.getTeacher().getName(), teacherCourse.getName()));
        System.out.println();
    }


    static void courseSubscription(int i, Subscription subscription) {
        info(String.format("\t\t%d. Subscription:\t%s %s", i + 1,
                DATE_FORMAT.format(subscription.getSubscriptionDate()),
                subscription.getSbsKey().getStudent().getName()));
    }


    static void studentOnCourse(int j, Subscription subscription) {
        info(String.format("\t\t\t%d. Subscription:\t%s %s", j + 1,
                DATE_FORMAT.format(subscription.getSubscriptionDate()),
                subscription.getSbsKey().getCourse().getName()));
    }
}
