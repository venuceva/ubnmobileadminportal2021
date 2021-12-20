package com.ceva.banking.usermgmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class FTPUpload {

	private static ResourceBundle resource = null;

	private static Logger logger = Logger.getLogger(FTPUpload.class);

	public static boolean doSftp(String fileName) {

		resource = ResourceBundle.getBundle("pathinfo_config");

		String server = resource.getString("ftp.ip");
		int port = Integer.parseInt(resource.getString("ftp.port"));
		String user = resource.getString("ftp.username");
		String pass = resource.getString("ftp.password");
		String path = resource.getString("ftp.path");
		String firstRemoteFile = resource.getString("remoteFilePath");
		String baseDirectory = resource.getString("baseDirectory");
		InputStream inputStream = null;
		FTPClient ftpClient = null;
		FTPFile ftpFile = null;
		boolean done = false;
		File firstLocalFile = null;
		String serialNumber = null;
		String serialPath = "";
		String basePath = "";

		File createFile = null;
		try {

			ftpClient = new FTPClient();
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			firstLocalFile = new File(path + fileName);

			logger.debug("FTP Current Path  ::: "
					+ ftpClient.printWorkingDirectory());
			logger.debug("FTP baseDirectory ::: " + baseDirectory);

			logger.debug("FTP Direct IS  	    ::: "
					+ resource.getString("home.path.check"));

			if (resource.getString("home.path.check").equalsIgnoreCase("on")) {
				basePath = ftpClient.printWorkingDirectory() + File.separator
						+ baseDirectory;
			} else {
				basePath = firstRemoteFile;
			}

			logger.debug("FTP Change Directory Path ::: " + basePath);

			ftpClient.changeWorkingDirectory(basePath);

			serialNumber = fileName.split("\\.")[0];
			serialPath = ftpClient.printWorkingDirectory() + File.separator
					+ serialNumber + File.separator;
			logger.debug("FTP serialPath Directory Path ::: " + serialPath);

			createFile = new File(serialPath);
			// ftpClient.mkd(ftpClient.printWorkingDirectory()+File.separator+serialNumber);

			if (!createFile.exists()) {
				ftpClient.mkd(serialPath);
			} else {
			}
			// createFile.mkdirs();

			logger.debug("FTP Current Path1	::: "
					+ ftpClient.printWorkingDirectory());
			ftpClient.changeWorkingDirectory(serialPath);
			logger.debug("FTP Current Path2	::: "
					+ ftpClient.printWorkingDirectory());

			firstRemoteFile = ftpClient.printWorkingDirectory()
					+ File.separator;
			firstRemoteFile += fileName;

			logger.debug("FTP Final Path	::: " + firstRemoteFile);
			logger.debug("First Local File	::: "
					+ firstLocalFile.getAbsolutePath());
			inputStream = new FileInputStream(firstLocalFile);
			done = ftpClient.storeFile(firstRemoteFile, inputStream);

			if (done) {
				logger.debug("The first file is uploaded successfully.");
				ftpFile = ftpClient.listFiles()[0];
				if (ftpFile.isFile()) {
					ftpFile.setPermission(FTPFile.USER_ACCESS,
							FTPFile.READ_PERMISSION, true);
				}
			} else {
				logger.debug("upload file Failed");
			}
		} catch (IOException ex) {
			logger.debug("IOException: " + ex.getMessage());
			ex.printStackTrace();
			done = false;
		} catch (Exception ex) {
			logger.debug("Exception: " + ex.getMessage());
			ex.printStackTrace();
			done = false;
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}

				if (inputStream != null) {
					inputStream.close();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			firstLocalFile = null;
			ftpFile = null;

			createFile = null;
			resource = null;
			server = null;
			port = 0;
			user = null;
			pass = null;
			path = null;
			firstRemoteFile = null;
			baseDirectory = null;
			done = false;
			serialNumber = null;
			serialPath = null;
			basePath = null;
		}
		return done;
	}

	public static void main(String[] args) {
		System.out.println(doSftp("001189014.txt"));
	}

}