package com.ceva.user.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import java.io.InputStream;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;






import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class Main {
	public static void main(String[] args){
		ByteArrayOutputStream out = QRCode.from("https://www.youtube.com/watch?v=nt94b5L3Z90")
										.to(ImageType.PNG).withSize(500, 500).stream();
		
		try {
	
			/*FileOutputStream fout = new FileOutputStream(new File(
					"D:\\QR_Code.JPG"));
			

			fout.write(out.toByteArray());
			//File pfile=new File("D:\\QR_Code.JPG");
			BASE64Encoder encoder = new BASE64Encoder();
			String image = encoder.encodeBuffer(out.toByteArray());
			
			System.out.println(image);

			fout.flush();
			fout.close();*/

		/*} catch (FileNotFoundException e) {*/
			// Do Logging
			
			System.out.println(Main.imageToBase64String(new File("D:/PRD00008/PRD00008_5.jpg")));
		} catch (Exception e) {
			// Do Logging
		}
	}
	
	
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
	
	public static String imageToBase64String(File imageFile) throws Exception {
        String image = null;
        String fileExt = "jpg";
        BufferedImage buffImage = ImageIO.read(imageFile);
        BASE64Encoder encoder = new BASE64Encoder();
        if (imageFile.exists()) {
            //System.out.println("File is Exists");
            if (buffImage != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(buffImage, fileExt, os);
                byte data[] = os.toByteArray();
                image = encoder.encode(data);
            }
        } else {
            System.out.println("File does not  Exists");
        }
        return image;
		}
	
	
	public static void Imagesave(String imgdata,String fname,String prod){
		System.out.println(imgdata);
		System.out.println(fname);
		
		byte imageBytes[] = (byte[])null;
		BASE64Decoder decoder = new BASE64Decoder();
		String ImgFileName = null;
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		try{
			 imageBytes = decoder.decodeBuffer(imgdata);
			 InputStream in = new ByteArrayInputStream(imageBytes);
			 BufferedImage bImageFromConvert = ImageIO.read(in);
			 
			 new File(resourceBundle.getString("lifestyle_path")+prod).mkdirs();
			 ImgFileName=resourceBundle.getString("lifestyle_path")+prod+"/"+fname;
			 ImageIO.write(bImageFromConvert, "jpg", new File(ImgFileName));
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	

}
