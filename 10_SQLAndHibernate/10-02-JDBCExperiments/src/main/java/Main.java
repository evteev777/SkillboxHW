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
        Marker CONSOLE = MarkerManager.getMarker("Console");
//        logger.warn(CONSOLE, "Program start");

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
        logger.warn(CONSOLE, course1.getName());
        logger.warn(CONSOLE, course2.getName());
        logger.warn(CONSOLE, course3.getName());
        logger.warn(CONSOLE, course4.getName());
        logger.warn(CONSOLE, course5.getName());

        sessionFactory.close();
//        logger.warn(CONSOLE, "Program completed");
    }
}
