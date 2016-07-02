package application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import database.DatabaseGrabber;
import factory.DatabaseFactory;
import factory.DefaultDatabaseFactory;

@WebListener
public class ContextListener implements ServletContextListener, ServletConstants{
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

    // Prepare database object in particular
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		DatabaseFactory dbFactory = DefaultDatabaseFactory.getFactoryInstance();
		DatabaseGrabber dbGrabber = dbFactory.getDatabaseGrabber();
		arg0.getServletContext().setAttribute(DATABASE_ATTRIBUTE, dbGrabber);
	}

}
