package factory;

import application.Encryption;

/**
 * @author dav23r
 * Implementation of default application factory. Uses singleton pattern
 */
public class DefaultApplicationFactory implements ApplicationFactory {

	private static DefaultApplicationFactory defFactoryInstance = null;
	
	public static ApplicationFactory getFactoryInstanse(){
		if (defFactoryInstance == null)
			defFactoryInstance = new DefaultApplicationFactory(); 
		
		return defFactoryInstance;
	}
	
	@Override
	public Encryption getEncryption() {
		return new Encryption();
	}

}
