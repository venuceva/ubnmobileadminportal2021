package com.ceva.crypto.utils;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
/**
 * 
 * @author Rajkumar Pandey
 *
 */
public class PropertyReader {
	private static ResourceBundle resbun = ResourceBundle.getBundle("resource/Services");
	protected static Logger logger = Logger.getLogger(PropertyReader.class);
	public static Map<String, String> read()
	{
		Enumeration<String> senum = resbun.getKeys();
		Map<String, String> property = new HashMap<String, String>();
		while (senum.hasMoreElements()) {
			String key = (String) senum.nextElement();
			property.put(key, resbun.getString(key));
		}
		return property;
	}

	
}

