package application;

import java.security.*;

public class Encryption {

	
	/** Empty constructor */
	public Encryption() {}
	
	/**
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	private String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/**
	 * Method encryprs provided string and hands
	 * back encrypted verstion.
	 * @param data - string to encrypt
	 * @return encrypted string
	 */
	public String encrypt(String data) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA");
			message.update(data.getBytes());
			return hexToString(message.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
