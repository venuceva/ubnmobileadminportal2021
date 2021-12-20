package com.ceva.crypto.utils;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.BindingProvider;

import com.ceva.crypto.utils.PropertyReader;
import com.ceva.unionbank.services.core.DesEncryptor;
import com.ceva.unionbank.services.core.Security;

public class HealthMonitor {
	public static Map<String, String> map=null;
	private static final String SERVER_ADDRESS = "172.16.10.207";
	private static final int TCP_SERVER_PORT = 5979;
	private static boolean connected = false;
	static Socket s;

	public static void main(String[] args) {

		/*  Timer timer = new Timer();
	        timer.schedule(task, 01, 5001);*/
		HealthMonitor hm=new HealthMonitor();
		hm.isAvailable("http://10.8.64.141:8001/eBusiness/SMSAlert?wsdl");
	}  

	static TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (connected == false)
			{
				System.out.println("kailash :: "+hostAvailabilityCheck());
			}
		}
	};

	public static boolean hostAvailabilityCheck()
	{ 

		boolean available = true; 
		try {               
			if (connected == false)
			{ (s = new Socket(SERVER_ADDRESS, TCP_SERVER_PORT)).close();    
			}               
		} 
		catch (UnknownHostException e) 
		{ // unknown host 
			available = false;
			s = null;
		} 
		catch (IOException e) { // io exception, service probably not running 
			available = false;
			s = null;
		} 
		catch (NullPointerException e) {
			available = false;
			s=null;
		}


		return available;   
	} 

	public void isAvailable(String urls){
		try {   
			Iterator<String> serviceurl= map.keySet().iterator();
			while (serviceurl.hasNext()) {
				String xurl = (String) serviceurl.next();
				String surl = map.get(xurl);
				if(urls.equalsIgnoreCase(surl)){
				
				if(surl.trim().length()>0)
				{
					System.out.println(xurl+"-"+surl);
					URL url = new URL(surl);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestProperty("Connection", "close");
					connection.setConnectTimeout(10000); 
					connection.connect();
					if (connection.getResponseCode() == 200) {
						surl = surl+" Service is Working!!!"+connection.getResponseMessage();
					}else{
						System.out.println(connection.getResponseCode());
						surl = surl+" Service is Not Working!!!";
					}
					System.out.println(surl);
				} 
			}
				
			}
		}

		catch (UnknownHostException e) 
		{ 
			e.printStackTrace();
		} 
		catch (IOException e) { 
			e.printStackTrace();
		} 
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public static void autheticateCall(Object port, String urlKey)
	{
		Map<String, Object> requestContext =  ((BindingProvider) port).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, map.get(urlKey));
		requestContext.put(BindingProvider.USERNAME_PROPERTY, new DesEncryptor().decrypt(Security.getBean().getServiceUserName()));
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, new DesEncryptor().decrypt(Security.getBean().getServicePassword()));
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Timeout", Collections.singletonList("10000"));
		requestContext.put(javax.xml.ws.handler.MessageContext.HTTP_REQUEST_HEADERS, headers);
	}
	static {
		map = PropertyReader.read();
		java.net.Authenticator auth = new java.net.Authenticator() {
			@Override

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(new DesEncryptor().decrypt(Security.getBean().getServiceUserName()),new DesEncryptor().decrypt(Security.getBean().getServicePassword()).toCharArray());//To
			}
		};
		Authenticator.setDefault(auth);
	}

}