package com.ceva.unionbank.services.core;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEncryptor {

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DES_ENCRYPTION_SCHEME = "DES";
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	byte[] keyAsBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public DesEncryptor() {
		myEncryptionKey = "U5cp5ywS7byc8b75z8uF95swojlrXNX6";
 		myEncryptionScheme = DES_ENCRYPTION_SCHEME;
		try {
			keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
			myKeySpec = new DESKeySpec(keyAsBytes);
			mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
			cipher = Cipher.getInstance(myEncryptionScheme);
			key = mySecretKeyFactory.generateSecret(myKeySpec);
		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Method To Encrypt The String
	 */
	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			BASE64Encoder base64encoder = new BASE64Encoder();
			encryptedString = base64encoder.encode(encryptedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	/**
	 * Method To Decrypt An Ecrypted String
	 */
	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = bytes2String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	public static void main(String args[]) throws Exception {
		DesEncryptor myEncryptor = new DesEncryptor();

		String strarry[] = { "tricorde", "password$1" };

		for (String stringToEncrypt : strarry) {
			String encrypted = myEncryptor.encrypt(stringToEncrypt);
			String decrypted = myEncryptor.decrypt(encrypted);

			System.out.println("String To Encrypt: " + stringToEncrypt);
			System.out.println("Encrypted Value :" + encrypted);
			System.out.println("Decrypted Value :" + decrypted);
		}

	}

}