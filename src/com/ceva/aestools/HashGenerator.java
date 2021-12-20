package com.ceva.aestools;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class HashGenerator {
	private static final int ITERATION_NUMBER = 1000;
	protected static final char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected String encrypt(String input, char[] digits) throws Exception {
		byte[] salt = getSalt();
		byte[] bDigest = getHash(1000, input, salt, digits);
		String sDigest = byteToBase64(bDigest);
		String sSalt = byteToBase64(salt);
		return sDigest + "||" + sSalt;
	}

	protected String decrypt(String input, char[] digits) throws Exception {
		String[] inputArray = SecurityUtils.splitAndTrim(input, "||");
		String inputPassword = inputArray[0];
		byte[] salt = base64ToByte(inputArray[2]);

		byte[] proposedDigest = getHash(1000, inputPassword, salt, digits);
		return byteToBase64(proposedDigest);
	}

	protected boolean validate(String[] input, char[] digits)
			throws IOException, NoSuchAlgorithmException {
		String inputPassword = input[0];
		byte[] digest = base64ToByte(input[1]);
		byte[] salt = base64ToByte(input[2]);

		byte[] proposedDigest = getHash(1000, inputPassword, salt, digits);
		return Arrays.equals(proposedDigest, digest);
	}

	private byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return salt;
	}

	public static String getHash(String password, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < 1000; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return byteToBase64(input);
	}

	public byte[] getHash(int iterationNb, String password, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < iterationNb; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}

	private byte[] getHash(int iterationNb, String password, byte[] salt,
			char[] digits) throws NoSuchAlgorithmException, IOException {
		StringBuilder hash = new StringBuilder();

		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));
		for (int idx = 0; idx < hashedBytes.length; idx++) {
			byte b = hashedBytes[idx];
			hash.append(digits[((b & 0xF0) >> 4)]);
			hash.append(digits[(b & 0xF)]);
		}
		return base64ToByte(hash.toString());
	}

	private static byte[] base64ToByte(String data) throws IOException {
		Base64 decoder = new Base64();
		return decoder.decode(data);
	}

	private static String byteToBase64(byte[] data) {
		Base64 endecoder = new Base64();
		return endecoder.encodeToString(data);
	}
}
