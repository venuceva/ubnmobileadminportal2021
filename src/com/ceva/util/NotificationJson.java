package com.ceva.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NotificationJson {
	static JSONObject obj=null;
	static FileWriter file=null;
	static JSONParser parser=null;
	static JSONArray list=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			//System.out.println(getJsonObject());
			//addFiled("collins","kailash16");
			//addFiled("name4","kailash2");
			//System.out.println(getJsonObject());
			//deleteFiled("collins","kailash16");
			//System.out.println(getJsonObject());
			//System.out.println(getMessage("name3"));
			System.out.println(getMessage("CEVABO"));
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static JSONObject getJsonObject() throws Exception{
		parser = new JSONParser();
		return (JSONObject)parser.parse(new FileReader("D:\\test.json"));
	}
	
	public static void addFiled(String key,String Message) throws Exception{
		obj =getJsonObject();
		if(obj.containsKey(key)){
			list = (JSONArray)obj.get(key);
			list.add(Message);
			obj.put(key, list);
		}else{
			list = new JSONArray();
			list.add(Message);
			obj.put(key, list);
		}
		
		file = new FileWriter("D:\\test.json");
		file.write(obj.toJSONString());
        file.flush();
	}
	
	public static void deleteFiled(String key,String Message) throws Exception{
		obj =getJsonObject();
		if(obj.containsKey(key)){
		list = (JSONArray)obj.get(key);
		list.remove(Message);
		obj.put(key, list);
		}
		file = new FileWriter("D:\\test.json");
		file.write(obj.toJSONString());
        file.flush();
	}
	
	public static String getMessage(String key) throws Exception{
		String val=(getJsonObject().get(key)).toString();
			obj =getJsonObject();
			obj.remove(key);
			file = new FileWriter("D:\\test.json");
			file.write(obj.toJSONString());
	        file.flush();
			return val;
			
			
		
	}


}
