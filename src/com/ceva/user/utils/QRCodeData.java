package com.ceva.user.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import java.security.SecureRandom;
import java.awt.image.BufferedImage;





import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRCodeData {
	
	public static String encodeByBase64(byte[] encryptData)
	{
		BASE64Encoder encode=new BASE64Encoder();
		return encode.encode(encryptData);
	}
	
	public static byte [] decodeByBase64(String decryptData)
	{
		byte[] data=null;
		try {
			BASE64Decoder decode= new BASE64Decoder();
			data= decode.decodeBuffer(decryptData);
		} catch (Exception e) {
			return data;
		}
		return data;
	}
	
	public static String gtQrcodeData(String strdata){
		
		ByteArrayOutputStream out = QRCode.from(strdata)
				.to(ImageType.PNG).withSize(250, 250).stream();
		BASE64Encoder encoder = new BASE64Encoder();
		return "data:image/jpeg;base64,"+encoder.encodeBuffer(out.toByteArray());
	}
	
	public static String gentag(String accno)
	 {
	  final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	  SecureRandom rnd = new SecureRandom();
	  int len = 4;
	     StringBuilder sb = new StringBuilder( len );
	     for( int i = 0; i < len; i++ ) 
	        sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	     return sb.toString()+accno.substring(accno.length()-2);  
	 }

	
	public static void main(String args[]){
		
		System.out.println(QRCodeData.gentag("1234567890"));
	}
}
