import lombok.Cleanup;
import entities.Course;
import entities.Student;
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

//        Subscription subscription = session.get(Subscription.class, 3);
//        appLogger.info(subscription.getCourseId());
//        appLogger.info(subscription.getStudentId());

        studentMappingLog(appLogger, session, 3);
        courseMappingLog(appLogger, session, 3);
        totalMappingLog(appLogger, session, 10);

        appLogger.debug( "Program completed");
    }

    private static void studentMappingLog(Logger appLogger, Session session, int studentNumber) {
        Student student = session.get(Student.class, studentNumber);
        appLogger.info("Student: \t" + student.getName());
        student.getCourses().forEach(c -> appLogger.info("— Course: \t- " + c.getName()));
        System.out.println();
    }

    private static void courseMappingLog(Logger appLogger, Session session, int courseNumber) {
        Course course = session.get(Course.class, courseNumber);
        appLogger.info("Course: \t" + course.getName());
        course.getStudents().forEach(s -> appLogger.info("— Student: \t- " + s.getName()));
        System.out.println();
    }

    private static void totalMappingLog(Logger appLogger, Session session, int courseNumber) {
        Course course = session.get(Course.class, courseNumber);

        appLogger.info("Course: \t" + course.getName());
        appLogger.info("- Teacher:\t\t- " + course.getTeacher().getName());

        List<Course> teacherCourses = course.getTeacher().getCourses();
        for (int i = 0; i < teacherCourses.size(); i++) {
            Course teacherCourse = teacherCourses.get(i);
            appLogger.info("  — Course " + (i + 1) + ": \t  - " + teacherCourse.getName());

            List<Course> studentsOnCourse = teacherCourse.getStudents();
            for (int j = 0; j < studentsOnCourse.size(); j++) {
                Course std = studentsOnCourse.get(j);
                appLogger.info("    — Student " + (j + 1) + ": \t    — " + std.getName());
            }
        }
        System.out.println();
    }
}
