package com.ceva.base.reports;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.ceva.util.CommonUtil;

import sun.misc.BASE64Encoder;

public class Test {

	private static String getLastDayofMonth(int month, int year)
    {
         Calendar dateCal = Calendar.getInstance();
        dateCal.set(year, month, 2);
        String pattern = "MMMM";
        SimpleDateFormat obDateFormat = new SimpleDateFormat(pattern);
        String monthName = obDateFormat.format(dateCal.getTime());
        int maxDay = dateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return "Last date of " + monthName + " :" + maxDay+"-"+month;
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			System.out.println(getLastDayofMonth(0,2014));
	}*/
	
	public static String imageToBase64String(File imageFile)throws Exception{
		String image=null;
		String fileExt="jpg";
		BufferedImage buffImage = ImageIO.read(imageFile);
		BASE64Encoder encoder = new BASE64Encoder();
		if(imageFile.exists())
		{

			System.out.println("File is Exists");
			if (buffImage != null) {

				java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
				//ImageIO.write(buffImage, "gif", os);
				ImageIO.write(buffImage, fileExt, os);
				byte[] data = os.toByteArray();
				image = encoder.encode(data);

			}

		}
		else
		{
			System.out.println("File does not  Exists");
		}
		return image;

	}
	
	/*public static void main(String[] args) {
		try
		{
			File f = new File("F:\\pendrive\\Bii.JPG");

			String str = imageToBase64String(f);

			System.out.println(str);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	private static String getCharForNumber(int i) {
		System.out.println("hh :"+String.valueOf((char)(55+i)));
	   // return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
		return String.valueOf((char)(55+i));
	}
	
	
	public static void main(String[] args) {
		try
		{
			String valu="451457000622444";
			System.out.println("checkdigigt value"+checkdigit(valu));
			
			System.out.println("JCCCCCCCCCCC ["+getCharForNumber(11));
			
			
			
			int yournumber=123131;
			System.out.println("ypournumeber"+String.format("%08d", yournumber));
			String str = "1077284,IAN CHEPKWONY,Customer ID Found#0028656,Mr KEITANY JEREMY,Mobile Number Found, 1077527,RAHAB RUNOH,Customer ID Found#10001041,MissMEDINA YMOHAMED,Mobile Number Found, 1077528,RAHAB RUNOH,Customer ID Found#0410516,Mr PETERSON I KAMOTHO,Mobile Number Found, 1077530,KEVIN NG'ENO,Customer ID Found#10013441,OSMAN S AHMED,Mobile Number Found, 1077531,KEVIN NG'ENO,Customer ID Found#10004304,ZAHAMAN COMPANY,Mobile Number Found, NO,NO,NO#NO,NO,NO, 10013284,GANALE F STORES,Customer ID Found#NO,NO,NO#";
//str=str.replaceAll(", NO,NO,NO", "");
//str=str.replaceAll("#NO,NO,NO", "");
			//str=str.substring(0,str.length()-1);
			List<String[]> data = null;
			List<String> list = null;
			String ss[]=null;
			String sp[]=null;
			String jc=null;
			
			ss=str.split("#");
			//System.out.println("ss values"+ss[1]);
			System.out.println("length"+ss.length);
			for (int i=0;i<ss.length;i++){
				jc=ss[i];
				jc=jc+",hai";
				sp=jc.split(",");
				System.out.println("sp values are"+sp);
				/*for(int j=0;j<sp.length;j++){
					
				}*/
				//sp=sp+",1,2";
				System.out.println("3rd values"+sp[3]);
			}
			list = new ArrayList<String>();
			/*for (String[] dat11 : data) {
				
				//pin = CommonUtil.generatePassword(4);
				//encpin = aesEncString(pin).trim();
				System.out.println(Arrays.asList(dat11));
				list.add(StringUtils.join(dat11, ",")+","+"1"+","+"2");
				//list.add(pin+","+encpin);
			}
			*/
			System.out.println(str.indexOf("JC"));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	public static int checkdigit(String idWithoutCheckdigit) {
		 
		// allowable characters within identifier
		String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";
		 
		// remove leading or trailing whitespace, convert to uppercase
		idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();
		 
		// this will be a running total
		int sum = 0;
		 
		// loop through digits from right to left
		for (int i = 0; i < idWithoutCheckdigit.length(); i++) {
		 
		//set ch to "current" character to be processed
		char ch = idWithoutCheckdigit
		.charAt(idWithoutCheckdigit.length() - i - 1);
		 
		// throw exception for invalid characters
		//if (validChars.indexOf(ch) == -1)
		
		 
		// our "digit" is calculated using ASCII value - 48
		int digit = (int)ch - 48;
		 
		// weight will be the current digit's contribution to
		// the running total
		int weight;
		if (i % 2 == 0) {
		 
		      // for alternating digits starting with the rightmost, we
		      // use our formula this is the same as multiplying x 2 and
		      // adding digits together for values 0 to 9.  Using the
		      // following formula allows us to gracefully calculate a
		      // weight for non-numeric "digits" as well (from their
		      // ASCII value - 48).
		      weight = (2 * digit) - (int) (digit / 5) * 9;
		 
		    } else {
		 
		      // even-positioned digits just contribute their ascii
		      // value minus 48
		      weight = digit;
		 
		    }
		 
		// keep a running total of weights
		sum += weight;
		 
		}
		 
		// avoid sum less than 10 (if characters below "0" allowed,
		// this could happen)
		sum = Math.abs(sum) + 10;
		 
		// check digit is amount needed to reach next number
		// divisible by ten
		return (10 - (sum % 10)) % 10;
		 
		}

}
