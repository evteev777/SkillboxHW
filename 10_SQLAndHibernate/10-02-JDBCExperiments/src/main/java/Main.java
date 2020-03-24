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

        Logger logger = LogManager.getRootLogger();
        logger.warn("Program start");

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
        logger.warn(String.format("course: %-30s\tteacher: %-20s\t%s students",
                course1.getName(), course1.getTeacher().getName(), course1.getStudents().size()));
        logger.warn(String.format("course: %-30s\tteacher: %-20s\t%s students",
                course2.getName(), course2.getTeacher().getName(), + course2.getStudents().size()));
        logger.warn(String.format("course: %-30s\tteacher: %-20s\t%s students",
                course3.getName(), course3.getTeacher().getName(), course3.getStudents().size()));
        logger.warn(String.format("course: %-30s\tteacher: %-20s\t%s students",
                course4.getName(), course4.getTeacher().getName(), course4.getStudents().size()));
        logger.warn(String.format("course: %-30s\tteacher: %-20s\t%s students",
                course5.getName(), course5.getTeacher().getName(), course5.getStudents().size()));

        session.close();
        sessionFactory.close();
        logger.warn( "Program completed");
    }
}
