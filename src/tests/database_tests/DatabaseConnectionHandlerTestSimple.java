package tests.database_tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import database.DatabaseConnectionHandler;

import org.junit.Before;
import org.junit.Test;

/** 
 * @author dav23r
 * Trivial test ConnectionHandler. Ensure creation of 
 * valid connection with database and it's subsequent
 * termination.
 */
public class DatabaseConnectionHandlerTestSimple {

	DatabaseConnectionHandler conHandler = null;

	@Before
	public void setUp() throws Exception {
		conHandler = 
				DatabaseConnectionHandler.getConnectionHandler();
	}

	@Test
	public void test() throws SQLException {
		Connection con = conHandler.getConnection();
		assertTrue (con.isValid(0));
		con.close();
		assertFalse(con.isValid(0));
	}

}
