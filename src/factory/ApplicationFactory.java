package factory;

import application.Encryption;

/**
 * @author dav23r
 * Provides means of creating application-related objects.
 * All implementations should override listed methods.
 */
public interface ApplicationFactory {
	
	/**
	 * Creates new 'Encryption object'
	 * @return Encryption
	 */
	Encryption getEncryption();

}
