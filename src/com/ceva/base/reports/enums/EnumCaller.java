package com.ceva.base.reports.enums;


public class EnumCaller {

	public static String getReport1Data(String reportid,String key)
	{
		String value=null;
		try {
			switch (reportid) {
			case "TIMEOUTREPORT": 
				value=String.valueOf(Report1.valueOf(key).getValue());
            break;
			case "TIMEOUTREPORT1": 
				value=String.valueOf(Report1.valueOf(key).getValue());
            break;
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		return value;
	}
	
	
	
	
	public static void main(String[] args) {
		
		if(null!=getReport1Data("TIMEOUTREPORT","KEY1"))
		{
			System.out.println(getReport1Data("TIMEOUTREPORT","KEY1"));
		}
		
		
		
	}
	
}
