package com.ceva.unionbank.services.handler;


import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SimpleXMLParser {

	public static Map<String, String> parseXML(String fileName) 
	{
		SimpleXMLHandler handler = new SimpleXMLHandler();
		try 
		{
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			InputStream data = SimpleXMLParser.class.getResourceAsStream(fileName);
			parser.parse(data, handler);

		} 
		catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return handler.getMap();

	}
	
	
	public static void main(String[] args) throws Exception 
	{
		System.out.println();
	}

}

