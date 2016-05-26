/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

/**
 *
 * @author ASUS
 */
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
private static final SessionFactory sessionFactory;

static {
    try {
        Configuration configuration = new Configuration();
        configuration.configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
        applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

    } catch (Throwable ex) {
        System.err.println("Initial SessionFactorycreation failed." + ex);
        throw new ExceptionInInitializerError(ex);
    }
    }
public static SessionFactory getSessionFactory() {
return sessionFactory;
}
}