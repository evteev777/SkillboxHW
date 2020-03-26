import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger("appLog");
        logger.info("Program start");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry)
                .getMetadataBuilder()
                .build();
        SessionFactory sessionFactory = metadata
                .getSessionFactoryBuilder()
                .build();

        Session session = sessionFactory.openSession();

        Course course1 = session.get(Course.class, 1);
        Course course2 = session.get(Course.class, 2);
        Course course3 = session.get(Course.class, 3);
        Course course4 = session.get(Course.class, 4);
        Course course5 = session.get(Course.class, 5);

        logger.info(String.format("course: %-30s\tteacher: %-20s",
                course1.getName(), course1.getTeacher().getName()));

//        logger.warn(String.format("\ncourse: %-30s\nteacher: %-20s\nstudents: %s",
//                course1.getName(), course1.getTeacher().getName(), course1.getStudents().size()));
//        course1.getStudents().forEach(student -> {
//            System.out.println("- " + student.getName());
//            student.getPurchases().forEach(purchase -> System.out.println("  - " + purchase.getCourse().getName()));
//        });
//
//        logger.warn(String.format("\ncourse: %-30s\bteacher: %-20s\nstudents: %s",
//                course2.getName(), course2.getTeacher().getName(), + course2.getStudents().size()));
//        course2.getStudents().forEach(s -> System.out.println("- " + s.getName()));
//
//        logger.warn(String.format("\ncourse: %-30s\nteacher: %-20s\nstudents: %s",
//                course3.getName(), course3.getTeacher().getName(), course3.getStudents().size()));
//        course3.getStudents().forEach(s -> System.out.println("- " + s.getName()));
//
//        logger.warn(String.format("\ncourse: %-30s\nteacher: %-20s\nstudents: %s",
//                course4.getName(), course4.getTeacher().getName(), course4.getStudents().size()));
//        course4.getStudents().forEach(s -> System.out.println("- " + s.getName()));
//
//        logger.warn(String.format("\ncourse: %-30s\nteacher: %-20s\nstudents: %s",
//                course5.getName(), course5.getTeacher().getName(), course5.getStudents().size()));
//        course5.getStudents().forEach(s -> System.out.println("- " + s.getName()));

        session.close();
        sessionFactory.close();
        logger.info( "Program completed");
    }
}
