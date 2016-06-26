package database;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import factory.*;

/**
 * Application Lifecycle Listener implementation class DatabaseListener
 *
 */
@WebListener
public class DatabaseListener implements ServletContextListener {
	
	public static final String ATTRIBUTE_NAME = "database";

    /**
     * Default constructor. 
     */
    public DatabaseListener() {
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         ServletContext contex =  arg0.getServletContext();
         contex.setAttribute(ATTRIBUTE_NAME, DefaultDatabaseFactory.getFactoryInstance().getDatabaseGrabber());
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         arg0.getServletContext().removeAttribute(ATTRIBUTE_NAME);
    }
	
}
