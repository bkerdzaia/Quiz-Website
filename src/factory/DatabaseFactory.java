package factory;

import application.Database;

public class DatabaseFactory {

	public static Database getDatabase() {
		return new Database();
	}
	
}
