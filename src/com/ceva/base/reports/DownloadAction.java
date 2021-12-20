package com.ceva.base.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ResourceBundle;

import com.opensymphony.xwork2.ActionSupport;
 
public class DownloadAction extends ActionSupport{
 
	private InputStream inputStream;
    private String fileName;
    public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	private long contentLength;
    
    String result=SUCCESS;
    
	public String execute()
	{
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle("configurations");
			String pathx = rb.getString("OUT_PATH");
			File fileToDownload = new File(pathx+"/"+fileName);
			if(fileToDownload.exists() && !fileToDownload.isDirectory() ) //&& fileToDownload.isFile())
			{
				try
				{
					inputStream = new FileInputStream(fileToDownload);
			        fileName = fileToDownload.getName();
			        contentLength = fileToDownload.length();

			        result = SUCCESS;
				}
				catch(Exception e)
				{
					result="fail";
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			result="fail";
			e.printStackTrace();
		}
		return result;
	}

}