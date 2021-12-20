package com.ceva.unionbank.services.core;

import java.util.Map;

import com.ceva.unionbank.services.bean.ServiceIntegratorBean;
import com.ceva.unionbank.services.handler.SimpleXMLParser;

public class Security {
public static ServiceIntegratorBean getBean()
{
	Map<String, String> map = SimpleXMLParser.parseXML("/resource/IntegratorInfo.xml");
	ServiceIntegratorBean scredobj = new ServiceIntegratorBean();
	ObjectCreatorImpl abean = new ObjectCreatorImpl();
	try {
		abean.saveObject(map, scredobj);
		scredobj.setServiceUserName(scredobj.getServiceUserName());
		scredobj.setServicePassword(scredobj.getServicePassword());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return scredobj;
}
public static void main(String[] args) {
	getBean();
}
}
