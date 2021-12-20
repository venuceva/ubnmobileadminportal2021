package com.ceva.aestools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

/**
 * Aes encryption
 */
public class AES {
	private Logger logger = Logger.getLogger(AES.class);
	private SecretKeySpec secretKey;
	private byte[] key;

	private String decryptedString;
	private String encryptedString;

	private String skey = "";
	

	private static ResourceBundle resourceBundle = null;

	static {
		resourceBundle = ResourceBundle.getBundle("auth");
	}

	public void setKey() {

		skey = resourceBundle.getString("aes.key");

		MessageDigest sha = null;
		try {
			key = skey.getBytes("UTF-8");
			//logger.debug("Key length ====> " + key.length);
			sha = MessageDigest.getInstance("SHA-512");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit

			secretKey = new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public String getDecryptedString() {
		return decryptedString;
	}

	public void setDecryptedString(String decryptedString) {
		this.decryptedString = decryptedString;
	}

	public String getEncryptedString() {
		return encryptedString;
	}

	public void setEncryptedString(String encryptedString) {
		this.encryptedString = encryptedString;
	}

	public String encrypt(String strToEncrypt) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);

			setEncryptedString(Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));

		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public String decrypt(String strToDecrypt) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

			cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
			setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));

		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
	
	public static String generateMobeeHash(String customerId, String password)
			throws Exception {
		if ((customerId == null) || (password == null)
				|| (customerId.isEmpty()) || (password.isEmpty())) {
			throw new Exception("Invalid input");
		}
		try {
			String _salt = new DESEncrypter().encrypt(customerId.trim(),"Manam@*63636Mirchi%^&*KCB");
			byte[] saltBytes = _salt.getBytes();
			return HashGenerator.getHash(password.trim(), saltBytes).trim();
		} catch (Exception e) {
			throw new Exception("Unable to generate mobeeHash "
					+ e.getMessage());
		}
	}

	/*public static void main(String args[]) throws SQLException {
		
		String detQry = "";
		String upQry = "";
		String val3="";
		String val1="";
		String val2="";
		String genpin="";
		String outkey="";
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet rs = null;
		
		
		try{
			AES aes = new AES();
			aes.setKey();
			
			connection = DBConnector.getConnection();
			
			detQry = "select ID,MOBEEID,OLDPIN from MATCH_PIN1 where where genpin is null";
			
			detPstmt = connection.prepareStatement(detQry);
			rs = detPstmt.executeQuery();
			
			int i = 0;
			int updateCnt = 0;
			int xx= -1;

			while (rs.next()) {
				val1= rs.getString(1);
				val2= rs.getString(2);
				val3= rs.getString(3);
				String _salt = new DESEncrypter().encrypt(val2.trim(),"Manam@*63636Mirchi%^&*KCB");
				byte[] saltBytes = _salt.getBytes();
				for (int j = 0; j <= 9999; j++) {
					if(HashGenerator.getHash(leftPadding('0', String.valueOf(j), 4),saltBytes ).trim().equals(val3)){
						xx=j;
						break;
					}
				}
				if(xx!=-1)
				{
				outkey =leftPadding('0', String.valueOf(xx), 4);
				aes.encrypt(outkey);
				genpin = aes.getEncryptedString().trim();
				}
				
				upQry = "update MATCH_PIN1 set genpin=?,plainpin=? where id=?";
				
				detPstmt1 = connection.prepareStatement(upQry);
				detPstmt1.setString(1,genpin.trim());
				detPstmt1.setString(2,String.valueOf(xx));
				detPstmt1.setString(3,val1);
				
				updateCnt = detPstmt1.executeUpdate();
				connection.commit();
				
				System.out.println("ENC PIN ["+genpin+"]  Plain Pin ["+outkey+"] updateCnt["+updateCnt+"]");

				DBUtils.closePreparedStatement(detPstmt);
				DBUtils.closePreparedStatement(detPstmt1);
			}
			
		}
		catch (Exception e) {
			System.out.println("Exception in Account Fetch Detials ["+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally{
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeConnection(connection);
			
		
		}

	}
	
public static void main(String args[]) throws SQLException {
		
		String detQry = "";
		String upQry = "";
		String val3="";
		String val1="";
		String val2="";
		String genpin="";
		String outkey="";
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet rs = null;
		
		
		try{
			long fisrt = System.currentTimeMillis();
			AES aes = new AES();
			aes.setKey();
			
			connection = DBConnector.getConnection();
			detQry = "select ID,OLDPIN from testpin4 where newpin is null";
			
			detPstmt = connection.prepareStatement(detQry);
			rs = detPstmt.executeQuery();
			
			int i = 0;
			int updateCnt = 0;
			int xx= -1;
			while (rs.next()) {
				long start = System.currentTimeMillis();
				val2= rs.getString(1);
				val3= rs.getString(2);
				String _salt = new DESEncrypter().encrypt(val2.trim(),"Manam@*63636Mirchi%^&*KCB");
				byte[] saltBytes = _salt.getBytes();
				for (int j = 0; j <= 9999; j++) {
					if(HashGenerator.getHash(leftPadding('0', String.valueOf(j), 4),saltBytes ).trim().equals(val3)){
						xx=j;
						break;
					}
				}
				if(xx!=-1)
				{
				outkey =leftPadding('0', String.valueOf(xx), 4);
				aes.encrypt(outkey);
				genpin = aes.getEncryptedString().trim();
				}
				
				upQry = "update testpin4 set newpin=?,plainpin=? where id=?";
				
				detPstmt1 = connection.prepareStatement(upQry);
				detPstmt1.setString(1,genpin.trim());
				detPstmt1.setString(2,String.valueOf(xx));
				//System.out.println("va2"+ val2);
				detPstmt1.setString(3,val2);
				
				updateCnt = detPstmt1.executeUpdate();
				connection.commit();
				
				//System.out.println("ENC PIN ["+genpin+"]  Plain Pin ["+outkey+"] updateCnt["+updateCnt+"]");

				DBUtils.closePreparedStatement(detPstmt);
				DBUtils.closePreparedStatement(detPstmt1);
				System.out.println(" MOBEEID  ["+val2+"] ENC PIN ["+genpin+"]  Plain Pin ["+outkey+"] updateCnt["+updateCnt+"]   Time taken for this record ["+(System.currentTimeMillis()-start)+"]");
			}
			System.out.println("Time taken for compete loop ["+(System.currentTimeMillis()-fisrt)+"]");
			
		}
		catch (Exception e) {
			System.out.println("Exception in Account Fetch Detials ["+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally{
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeConnection(connection);
			
		
		}

	}
	
	public static String leftPadding(char ch, String pin, int len)
	{
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < (len-pin.length()); i++) {
			sb.append(ch);
		}
		return sb.append(pin).toString();
		
	}*/
	
/*	public static void main(String[] args) throws Exception {
		for (int i = 1000; i <= 9999; i++) {
			String dat = generateMobeeHash("20169638", String.valueOf(i));
			
			if(dat.contains("BwRXQ3s8wr+tk9oEp6GOvdYJZy0=")){
				System.out.println(i);
			}
		}
	}*/
	
	
	public static void main(String args[]) {
		String strToEncrypt = "0721";
		// strToEncrypt = "4015";
		// final String strPssword =
		// "AeH6GrLRGK2SBtNiziAdl+Z9HK+98qChhGuCaLZ7O5M";
		AES aes = new AES();

		aes.setKey();

		aes.encrypt(strToEncrypt.trim());

		System.out.println("String to Encrypt: " + strToEncrypt);
		System.out.println("Encrypted: " + aes.getEncryptedString());

		final String strToDecrypt = aes.getEncryptedString();
		//aes.decrypt("WVb3NUjTa1kYmbm4Vm7A6w==");

		//System.out.println("String To Decrypt : " + strToDecrypt);
		System.out.println("Decrypted : " + aes.getDecryptedString());

	}
	
/*
public static void main(String args[]) throws SQLException {
		
		String detQry = "";
		String upQry = "";
		String val3="";
		String val1="";
		String val2="";
		String genpin="";
		String outkey="";
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet rs = null;
		
		
		try{
			long fisrt = System.currentTimeMillis();
			AES aes = new AES();
			aes.setKey();
			
			connection = DBConnector.getConnection();
			detQry = "select ID,PLAINPIN from testpin1 where newpin is null";
			
			detPstmt = connection.prepareStatement(detQry);
			rs = detPstmt.executeQuery();
			
			int i = 0;
			int updateCnt = 0;
			int xx= -1;
			while (rs.next()) {
				long start = System.currentTimeMillis();
				val2= rs.getString(1);
				val3= rs.getString(2);
				String _salt = new DESEncrypter().encrypt(val2.trim(),"Manam@*63636Mirchi%^&*KCB");
				byte[] saltBytes = _salt.getBytes();
				for (int j = 0; j <= 9999; j++) {
					if(HashGenerator.getHash(leftPadding('0', String.valueOf(j), 4),saltBytes ).trim().equals(val3)){
						xx=j;
						break;
					}
				}
				if(xx!=-1)
				{
				//outkey =leftPadding('0', String.valueOf(xx), 4);
				//outkey=CommonUtil.generatePassword(4);
				outkey=val3;
				aes.encrypt(outkey);
				genpin = aes.getEncryptedString().trim();
				//}
				
				//upQry = "update testpin1 set newpin=?,plainpin=? where id=?";
				upQry = "update testpin1 set newpin=? where id=?";
				
				detPstmt1 = connection.prepareStatement(upQry);
				detPstmt1.setString(1,genpin.trim());
				//detPstmt1.setString(2,outkey);
				//System.out.println("va2"+ val2);
				detPstmt1.setString(2,val2);
				
				updateCnt = detPstmt1.executeUpdate();
				connection.commit();
				
				//System.out.println("ENC PIN ["+genpin+"]  Plain Pin ["+outkey+"] updateCnt["+updateCnt+"]");

				DBUtils.closePreparedStatement(detPstmt);
				DBUtils.closePreparedStatement(detPstmt1);
				System.out.println(" MOBEEID  ["+val2+"] ENC PIN ["+genpin+"]  Plain Pin ["+outkey+"] updateCnt["+updateCnt+"]   Time taken for this record ["+(System.currentTimeMillis()-start)+"]");
			}
			System.out.println("Time taken for compete loop ["+(System.currentTimeMillis()-fisrt)+"]");
			
		}
		catch (Exception e) {
			System.out.println("Exception in Account Fetch Detials ["+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally{
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeConnection(connection);
			
		
		}

	}*/

}
