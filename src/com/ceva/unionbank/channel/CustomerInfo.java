package com.ceva.unionbank.channel;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomerInfo {
	JSONObject resultJson = null;
	public void getDetails(){
		resultJson = new JSONObject();
		JSONObject jsonlmt1 = new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.getCustomerDetail("tdamusan"));
		JSONArray jsonarray =  JSONArray.fromObject(json2.get("CUST_INFO"));
		Iterator iterator = jsonarray.iterator();
		
	     while (iterator.hasNext()) {
	    	   JSONObject jsonobj=(JSONObject)iterator.next();
	    	   jsonlmt1.put("firstname", jsonobj.getString("firstname"));
	    	   jsonlmt1.put("lastname", jsonobj.getString("lastname"));
	    	   jsonlmt1.put("mobilenumber", jsonobj.getString("mobilenumber"));
	    	   jsonlmt1.put("customeremail", jsonobj.getString("customeremail"));
	    	   jsonlmt1.put("username", jsonobj.getString("username"));
	    	   
	    	   JSONObject jsonlmt2 = new JSONObject();
	    	   jsonlmt2.put("accountcurrency", jsonobj.getString("accountcurrency"));
	    	   jsonlmt2.put("accountnumber", jsonobj.getString("accountnumber"));
	    	   jsonlmt2.put("accright", jsonobj.getString("accright"));
	    	   
	    	   jsonlmt1.put("ACC-"+jsonobj.getString("accountnumber"), jsonlmt2);
	     }
	     
	     json.put("CUST_INFO", jsonlmt1);
	     System.out.println(json);
		 jsonlmt1.clear();
	}

	public static void main(String args[]){
		CustomerInfo ci=new CustomerInfo();
		ci.getDetails();
	}
	
}
