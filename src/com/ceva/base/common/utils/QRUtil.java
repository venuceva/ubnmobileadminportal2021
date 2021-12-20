package com.ceva.base.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRUtil extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String qrtext = request.getParameter("qrtext");

		ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).withSize(350, 350).stream();
		
		 try {
			 
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48

            FileOutputStream fout = new FileOutputStream(new File("D:/QR_Code"+dateFormat.format(date)+".JPG"));
 
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
 
        } catch (FileNotFoundException e) {
            // Do Logging
        } catch (IOException e) {
            // Do Logging
        }
        
		
		response.setContentType("image/png");
		response.setContentLength(out.size());
		
		OutputStream outStream = response.getOutputStream();

		outStream.write(out.toByteArray());

		outStream.flush();
		outStream.close();
	}
}
