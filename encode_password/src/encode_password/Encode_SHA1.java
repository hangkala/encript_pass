/**
 * 
 */
package encode_password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * @author Administrator
 *
 */
public class Encode_SHA1 {
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final Random RANDOM = new SecureRandom();
	
	public String getSalt(int length) {
		 StringBuilder returnValue = new StringBuilder(length);
         for (int i = 0; i < length; i++) {
             returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
         }
         return new String(returnValue);
	}
	
	private String encodeSHA1(String pass, String salt) throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update((pass+salt).getBytes());
			byte[] digest = md.digest();
			
			StringBuilder hexStr = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				hexStr.append(Integer.toHexString(0xFF & digest[i]));
			}
			return hexStr.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new NoSuchAlgorithmException();
		}
	}
	
	public String encodeBase64(String pass, String salt) throws NoSuchAlgorithmException {
		try {
			String encodePass = "";
			String firstEncode = encodeSHA1(pass, salt);
			encodePass = Base64.getEncoder().encodeToString(firstEncode.getBytes());
			return encodePass;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoSuchAlgorithmException();
		}
	}
	
	public boolean validatePass(String inputPass, String securePass, String salt) throws NoSuchAlgorithmException {
		try {
			String newPass = encodeBase64(inputPass, salt);
			return newPass.equals(securePass);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NoSuchAlgorithmException();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Encode_SHA1 test = new Encode_SHA1();
//			String pass = "hanght";
//			String salt = test.getSalt(pass.length());
//			System.out.println(salt);
//			System.out.println(test.encodeBase64(pass, salt));
			
			String salt = "Vs6IWP";
			String input = "hanght";
			String securePass = "ZTUzMzQxOGJkNzc1MjQ2Mzg2ODM4MGZhZGQ0N2Q2NThiY2M1MTViMQ==";
			System.out.println(test.validatePass(input, securePass, salt));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

}
