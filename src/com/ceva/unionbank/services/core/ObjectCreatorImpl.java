package com.ceva.unionbank.services.core;

import java.beans.Statement;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Rajkumar Pandey
 *
 */
public class ObjectCreatorImpl implements ObjectCreator{
	private static String SET = "set";
	@Override
	public void saveObject(Map<String, String> map, Object obj) throws Exception {

		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();		
		while(it.hasNext())
		{
			String key = it.next();
			Statement stmt = new Statement(obj,SET+WordUtils.capitalize(key) , new Object[]{map.get(key)});
			stmt.execute();
		}
	}

	@Override
	public void saveObject(JSONObject json, Object obj) throws Exception {
		Iterator<String> it =json.keys();
		while(it.hasNext())
		{
			String key = it.next();
			Statement stmt = new Statement(obj,SET+WordUtils.capitalize(key) , new Object[]{json.get(key)});
			stmt.execute();
		}
	}

	@Override
	public JSONObject getJsonObject(Object obj){

		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject= null;
		try {
			String json = mapper.writeValueAsString(obj);
			jsonObject = new JSONObject(json);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	@Override
	public Object saveServiceObject(JSONObject json, Object obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json.toString(), obj.getClass());
	}

	@Override
	public JSONArray getJsonArray(Object obj) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JSONArray jsonArray= null;
		try {
			String json = mapper.writeValueAsString(obj);
			jsonArray = new JSONArray(json);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	
}
