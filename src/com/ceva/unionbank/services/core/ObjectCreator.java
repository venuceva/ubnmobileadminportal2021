package com.ceva.unionbank.services.core;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 
 * @author Rajkumar Pandey
 *
 */
public interface ObjectCreator {

	public void saveObject(Map<String, String> map, Object obj) throws Exception ;
	public void saveObject(JSONObject json, Object obj ) throws Exception ;
	public JSONObject getJsonObject(Object obj) throws IOException;
	public JSONArray getJsonArray(Object obj) throws IOException;
	public Object saveServiceObject(JSONObject json, Object obj ) throws Exception ;
	
}
