package com.ceva.aestools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DESEncrypter
{
  protected static final String PASSPHRASE = "Manam@*63636Mirchi%^&*KCB";
  Cipher ecipher;
  Cipher dcipher;
  byte[] salt = { -87, -101, -56, 50, 86, 53, -29, 3 };
  int iterationCount = 19;
  
  private Cipher getEnCipher(String passPhrase)
  {
    try
    {
      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), this.salt, this.iterationCount);
      SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
      this.ecipher = Cipher.getInstance(key.getAlgorithm());
      
      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
      

      this.ecipher.init(1, key, paramSpec);
    }
    catch (InvalidAlgorithmParameterException e) {}catch (InvalidKeySpecException e) {}catch (NoSuchPaddingException e) {}catch (NoSuchAlgorithmException e) {}catch (InvalidKeyException e) {}
    return this.ecipher;
  }
  
  private Cipher getDecCipher(String passPhrase)
  {
    try
    {
      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), this.salt, this.iterationCount);
      SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
      
      this.dcipher = Cipher.getInstance(key.getAlgorithm());
      

      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
      

      this.dcipher.init(2, key, paramSpec);
    }
    catch (InvalidAlgorithmParameterException e) {}catch (InvalidKeySpecException e) {}catch (NoSuchPaddingException e) {}catch (NoSuchAlgorithmException e) {}catch (InvalidKeyException e) {}
    return this.dcipher;
  }
  
  protected String encrypt(String str, String passPhrase)
  {
    try
    {
      byte[] utf8 = str.getBytes("UTF8");
      

      byte[] enc = getEnCipher(passPhrase).doFinal(utf8);
      

      return Base64.encodeBase64String(enc);
    }
    catch (BadPaddingException e) {}catch (IllegalBlockSizeException e) {}catch (UnsupportedEncodingException e) {}catch (Exception e) {}
    return null;
  }
  
  protected String decrypt(String str, String passPhrase)
  {
    try
    {
      byte[] utf8 = getDecCipher(passPhrase).doFinal(Base64.decodeBase64(str.getBytes()));
      

      return new String(utf8, "UTF8");
    }
    catch (BadPaddingException e) {}catch (IllegalBlockSizeException e) {}catch (UnsupportedEncodingException e) {}
    return null;
  }
  
  public static void main(String[] args)
  {
    System.err.println("Decrypted..." + new DESEncrypter().decrypt("IUWrpq9veGo=", "Manam@*63636Mirchi%^&*KCB"));
    System.err.println("Decrypted..." + new DESEncrypter().encrypt("12345", "Manam@*63636Mirchi%^&*KCB"));
  }
}