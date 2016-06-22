package tests.database_tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import database.DatabaseConnectionHandler;
import database.DefaultDatabaseConnectionHandler;

import org.junit.Before;
import org.junit.Test;

/** 
 * @author dav23r
 * Trivial test ConnectionHandler. Ensure creation of 
 * valid connection with database and it's subsequent
 * termination. Test is performed on real database,
 * not mock one.
 */
public class DatabaseConnectionHandlerTestSimple {

	DatabaseConnectionHandler conHandler = null;

	@Before
	public void setUp(){
		conHandler = 
				DefaultDatabaseConnectionHandler.getConnectionHandler();
	}

	@Test
	public void test() throws SQLException {
		Connection con = conHandler.getConnection();
		assertTrue (con.isValid(0));
		con.close();
		assertFalse(con.isValid(0));
	}

}
