import lombok.Cleanup;
import entities.Course;
import entities.Student;
import entities.Subscription;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger appLogger = LogManager.getLogger("appLog");
        appLogger.debug("Program start");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        @Cleanup SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata()
                .getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Subscription subscription = session.get(Subscription.class, 1);
//        appLogger.info(subscription.getSubscriptionDate());
//        appLogger.info(subscription.getCourse());

        totalMappingLog(appLogger, session, 10);

        appLogger.debug( "Program completed");
    }

    private static void totalMappingLog(Logger appLogger, Session session, int courseNumber) {
        Course course = session.get(Course.class, courseNumber);

        appLogger.info("Course: \t" + course.getName());
        appLogger.info("- Teacher:\t\t- " + course.getTeacher().getName());

        List<Course> teacherCourses = course.getTeacher().getCourses();
        for (int i = 0; i < teacherCourses.size(); i++) {
            Course teacherCourse = teacherCourses.get(i);
            appLogger.info("  — Course " + (i + 1) + ": \t  - " + teacherCourse.getName());

            List<Subscription> courseSubscriptions = teacherCourse.getSubscriptions();
            for (int j = 0; j < courseSubscriptions.size(); j++) {
                Subscription subscr1 = courseSubscriptions.get(j);
                appLogger.info("    — Course subscription " + (j + 1) + ": \t    — "
                        + subscr1.getStudent().getName());
            }

            List<Student> studentsOnCourse = teacherCourse.getStudents();
            for (int j = 0; j < studentsOnCourse.size(); j++) {
                Student std = studentsOnCourse.get(j);
                appLogger.info("    — Student " + (j + 1) + ": \t    — " + std.getName());

                List<Subscription> studentSubscriptions = std.getSubscriptions();
                for (int k = 0; k < studentSubscriptions.size(); k++) {
                    Subscription subscr2 = studentSubscriptions.get(k);
                    appLogger.info("      — Student subscription " + (k + 1) + ": \t    — "
                            + subscr2.getCourse().getName());
                }
            }
        }
        System.out.println();
    }
}
