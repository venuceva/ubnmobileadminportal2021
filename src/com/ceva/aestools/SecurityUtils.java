package com.ceva.aestools;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class SecurityUtils extends StringUtils {
	public static String[] splitAndTrim(String string, String delim) {
		if (string == null) {
			return null;
		}
		if (isEmpty(string)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		String[] rawTokens = split(string, delim);
		List<String> tokens = new ArrayList<String>();
		if (rawTokens != null) {
			for (int i = 0; i < rawTokens.length; i++) {
				String token = trim(rawTokens[i]);
				if (isNotEmpty(token)) {
					tokens.add(token);
				}
			}
		}
		return (String[]) toArrayOfComponentType(tokens.toArray(), String.class);
	}

	public static Object[] toArrayOfComponentType(Object[] objects,
			Class<?> clazz) {
		if ((objects == null)
				|| (objects.getClass().getComponentType().equals(clazz))) {
			return objects;
		}
		if (clazz == null) {
			throw new IllegalArgumentException(
					"Array target class must not be null");
		}
		Object[] result = (Object[]) Array.newInstance(clazz, objects.length);
		System.arraycopy(objects, 0, result, 0, objects.length);
		return result;
	}
}
