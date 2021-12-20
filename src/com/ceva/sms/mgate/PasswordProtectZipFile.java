package com.ceva.sms.mgate;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Demonstrates adding files to zip file with standard Zip Encryption
 */

public class PasswordProtectZipFile {

	private String password;

	public String getPassword() {
		return password;
	} 
	 
	private static boolean process(String path, String zipFileName,
			String password, String[] csvFileNames) {
		boolean isprotected = false;

		ZipFile zipFile = null;
		ZipParameters parameters = null;

		ArrayList<File> filesToAdd = null;

		try {
			zipFile = new ZipFile(new File(zipFileName));

			filesToAdd = new ArrayList<File>();
			for (String fname : csvFileNames) {
				filesToAdd.add(new File(path + fname));
			}

			parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters
					.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);

			zipFile.addFiles(filesToAdd, parameters);
			isprotected = true;
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
			zipFile = null;
			filesToAdd = null;
			parameters = null;
		}
		return isprotected;
	}

	public void setZip(String[] files) {
		zipMultipleFiles(files);
	}

	private boolean zipMultipleFiles(String[] files) {
		String filesList[] = {files[0],files[1]};
		password = "Pandey12!@";
		boolean flag = process(files[2], files[3], password, filesList);

		return flag;
	}

	public static void main(String[] args) {
		String sourcePath = "F:/file/";
		String fileName = "F:/mytest.zip";
		String password1 = "Pandey12!@";
		String[] zipFileName = new File(sourcePath).list();

		if (process(sourcePath, fileName, password1, zipFileName)) {
			System.out.println("succesfully done");
		} else {
			System.out.println("Failed....");
		}
	}
}