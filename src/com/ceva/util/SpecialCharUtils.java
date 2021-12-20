package com.ceva.util;

public class SpecialCharUtils {
	
	public static String replaceString(String str) {
		str=str.replace("'", "");
		str=str.replace("\"", "");
		str=str.replace("\\", "");
		return str;
	}

}
