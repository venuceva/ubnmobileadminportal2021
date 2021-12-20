package com.ceva.base.reports.enums;

public enum Report1 {
	KEY1("value1"),
	KEY2("value2");
	
	private String value;
	
	Report1(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
	
}
