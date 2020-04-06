import entities.Course;
import entities.Student;
import entities.Subscription;

import java.util.List;
import java.util.Arrays;

import entities.Teacher;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    public static void main(String[] args) {

        Logger appLogger = LogManager.getLogger("appLog");
        appLogger.debug("Program start");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata()
                .getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        final int TEACHER_ID = 1;
        teacherMapping(TEACHER_ID, session, appLogger);

        final int COURSE_ID = 1;
        courseMapping(COURSE_ID, session, appLogger);

        final int STUDENT_ID = 1;
        studentMapping(STUDENT_ID, session, appLogger);

        final int COURSE_ID_2 = 1;
        final int SUBSCRIPTION_NUMBER = 1;
        subscriptionMapping(COURSE_ID_2, SUBSCRIPTION_NUMBER, session, appLogger);

        final int COURSE_ID_3 = 10;
        totalMapping(COURSE_ID_3, session, appLogger);

        closeAll(session, sessionFactory, registry, appLogger);
        appLogger.debug( "Program completed");
    }

    private static void teacherMapping(int TEACHER_ID, Session session, Logger appLogger) {
        Teacher teacher = session.get(Teacher.class, TEACHER_ID);
        appLogger.info(String.format("Teacher ID_%d: %s", TEACHER_ID, teacher.getName()));
        System.out.println();
    }

    private static void courseMapping(int COURSE_ID, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_ID);
        appLogger.info(String.format("Course  ID_%d: %s", COURSE_ID, course.getName()));
        System.out.println();
    }

    private static void studentMapping(int STUDENT_ID, Session session, Logger appLogger) {
        Student student = session.get(Student.class, STUDENT_ID);
        appLogger.info(String.format("Student ID_%d: %s", STUDENT_ID, student.getName()));
        System.out.println();
    }

    private static void subscriptionMapping(int COURSE_ID, int SUBSCRIPTION_NUMBER,
                                            Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_ID);
        Subscription.SubscriptionKey key = course.getSubscriptions().get(SUBSCRIPTION_NUMBER).getKey();
        Subscription subscription = session.get(Subscription.class, key);
        appLogger.info("Subscription number: \t" + SUBSCRIPTION_NUMBER);
        appLogger.info("Subscription date:   \t" + subscription.getSubscriptionDate());
        appLogger.info("Subscription course: \t" + subscription.getKey().getCourse().getName());
        appLogger.info("Subscription student:\t" + subscription.getKey().getStudent().getName());
        System.out.println();
    }

    private static void totalMapping(int COURSE_NUMBER, Session session, Logger appLogger) {
        Course course = session.get(Course.class, COURSE_NUMBER);

        appLogger.info("Course: " + course.getName());
        appLogger.info("Course teacher: " + course.getTeacher().getName());
        System.out.println();

        List<Course> teacherCourses = course.getTeacher().getCourses();
        for (int i = 0; i < teacherCourses.size(); i++) {
            Course teacherCourse = teacherCourses.get(i);

            appLogger.info(String.format("\t%d. Teacher: %s, Course: %s",
                    i + 1, teacherCourse.getTeacher().getName(), teacherCourse.getName()));
            System.out.println();

            courseSubscriptions(teacherCourse, appLogger);
            studentsOnCourse(teacherCourse, appLogger);
        }
    }

    private static void courseSubscriptions(Course course, Logger appLogger) {
        List<Subscription> courseSubscriptions = course.getSubscriptions();
        for (int i = 0; i < courseSubscriptions.size(); i++) {
            Subscription subscription = courseSubscriptions.get(i);

            appLogger.info(String.format("\t\t%d. Subscription: %s %s", i + 1,
                    subscription.getSubscriptionDate(),
                    subscription.getKey().getStudent().getName()));
        }
        System.out.println();
    }

    private static void studentsOnCourse(Course teacherCourse, Logger appLogger) {
        List<Student> studentsOnCourse = teacherCourse.getStudents();
        for (int i = 0; i < studentsOnCourse.size(); i++) {
            Student student = studentsOnCourse.get(i);

            appLogger.info(String.format("\t\t%d. Student: %s", (i + 1), student.getName()));

            List<Subscription> studentSubscriptions = student.getSubscriptions();
            for (int j = 0; j < studentSubscriptions.size(); j++) {
                Subscription subscription = studentSubscriptions.get(j);

                appLogger.info(String.format("\t\t\t%d. Subscription: %s %s", j + 1,
                        subscription.getSubscriptionDate(),
                        subscription.getKey().getCourse().getName()));
            }
            System.out.println();
        }
    }

    private static void closeAll(Session session, SessionFactory sessionFactory,
                                 StandardServiceRegistry registry, Logger appLogger) {
        try {
            session.close();
        } catch (HibernateException e) {
            appLogger.warn(Arrays.toString(e.getStackTrace()));
        } finally {
            appLogger.debug( "Session closed");
        }

        try {
            sessionFactory.close();
        } catch (HibernateException e) {
            appLogger.warn(Arrays.toString(e.getStackTrace()));
        } finally {
            appLogger.debug( "SessionFactory closed");
        }

        try {
            registry.close();
        } catch (Exception e) {
            appLogger.warn(Arrays.toString(e.getStackTrace()));
        } finally {
            appLogger.debug( "Registry closed");
        }
    }
}
