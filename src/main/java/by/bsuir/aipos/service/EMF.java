package by.bsuir.aipos.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EMF implements ServletContextListener {

    /**
     * EntityManagerFactory will be created when the web application initialization process is starting.
     */
    private static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        emf = Persistence.createEntityManagerFactory("STUDENT_DB");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
    }

    /**
     * Create EntityManager from EntityManagerFactory
     *
     * @return EntityManager
     */
    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }

}
