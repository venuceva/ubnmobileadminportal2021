package com.ceva.unionbank.services.handler;


import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 
 * @author Rajkumar Pandey
 * 
 */
public class SimpleXMLHandler extends DefaultHandler {
	HashMap<String, String> hashMap= new HashMap<String, String>();
	String value="";
	String key="";
	
	public Map<String, String> getMap()
	{
		return hashMap;
	}
	
    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
    	
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
    	if(!"UBN_CREDENTIAL".equals(qName))
    	{
    	 hashMap.put( qName,key);
    	}
        
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    	 key=new String(ch, start, length);
    	
       
    }
}

