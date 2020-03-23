import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        logger.info("Program start");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

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

        Course course = session.get(Course.class, 1);
        logger.info(course.getName());

        sessionFactory.close();
        logger.info("Program completed");
    }
}