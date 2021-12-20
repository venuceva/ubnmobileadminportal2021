package com.ceva.aestools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class AESSession {

	private Logger logger = Logger.getLogger(AESSession.class);
	
	static
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	
	public static void main(String[] args) {
		
		try{
			
			
			System.out.println("Welcome to program");	
			
			
			/* code for key generation */
			byte[] symkey = generateSessionKey();
			String generatedKey = base64Encode(symkey);
			System.out.println("Key:::"+generatedKey);
			
			/*Code for Encryption */
			String reqData = "This is sample Data";
			String encdata = encryptRequestWithSessionKey(base64Decode(generatedKey), reqData.getBytes());
			System.out.println("Encripted Data ["+encdata+"]");
			
			
			/*Code for Decryption */
			String plaindata = decryptRequestWithSessionKey(base64Decode(generatedKey),encdata);
			System.out.println("after decrypt data"+plaindata);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static byte[] generateSessionKey() throws NoSuchAlgorithmException, NoSuchProviderException
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES", "BC");
		kgen.init(256);
		SecretKey key = kgen.generateKey();
		byte[] symmKey = key.getEncoded();
		return symmKey;
	}
	
	
	
	
	
	public static String encryptRequestWithSessionKey(byte[] sessionKey,byte[] RequestData)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,IllegalBlockSizeException, BadPaddingException, NoSuchProviderException
	{
		
		String symetricKeyAlg="AES/ECB/PKCS5Padding"; //AES/ECB/PKCS7Padding
		symetricKeyAlg="AES/ECB/PKCS7Padding";
		String pkiProvider="BC";
		
		SecretKeySpec symmKeySpec = new SecretKeySpec(sessionKey, symetricKeyAlg);
		Cipher symmCipher = Cipher.getInstance(symetricKeyAlg, pkiProvider);
		symmCipher.init(Cipher.ENCRYPT_MODE, symmKeySpec);
		byte[] encXMLPidData = symmCipher.doFinal(RequestData);
		return base64Encode(encXMLPidData);
		
	}
	
	
	public static String decryptRequestWithSessionKey(
			byte[] sessionKey, String encData)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,IllegalBlockSizeException, BadPaddingException, NoSuchProviderException
	{
		
		String symetricKeyAlg="AES/ECB/PKCS5Padding"; //AES/ECB/PKCS7Padding
		
		symetricKeyAlg="AES/ECB/PKCS7Padding";
		
		String pkiProvider="BC";	
		SecretKeySpec symmKeySpec = new SecretKeySpec(sessionKey, symetricKeyAlg);
		Cipher symmCipher = Cipher.getInstance(symetricKeyAlg, pkiProvider);
		symmCipher.init(Cipher.DECRYPT_MODE, symmKeySpec);
		byte[] xmlData = symmCipher.doFinal(base64Decode(encData));
		return new String(xmlData);
		
		
	}
	
	
	
	public static String base64Encode(byte[] binaryData)
	{
		return Base64.encodeBase64String(binaryData);
	}
	
	
	public static byte[] base64Decode(String base64String)
	{
		return Base64.decodeBase64(base64String);
	}
	
}
