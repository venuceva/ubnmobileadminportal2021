package com.ceva.unionbank.channel;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONObject;
/**
 * 
 * @author Rajkumar Pandey
 *
 */
public class ServiceRequestSender {

	private static final String USER_AGENT = "Mozilla/5.0";

	public static String postRequest(String url, JSONObject request){

		String req ="";
		URL obj = null;
		String response ="";
		DataOutputStream writer = null;
		BufferedReader in = null;

		try 
		{
			if(request != null)
			{
				req= request.toString();
				req = URLEncoder.encode(req, "UTF-8");
			}
			System.out.println("Request ["+req+"]");
			System.out.println("URL ["+url+"]");
			obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Length", "" + req.length());

			writer = new DataOutputStream(con.getOutputStream());
			writer.writeBytes(req);
			writer.flush();

			int responseCode = con.getResponseCode();
			System.out.println("Response Code [" + responseCode+"]");

			in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = in.readLine()) != null) 
			{
				sb.append(line);
				line= null;
			}
			response= URLDecoder.decode(sb.toString(), "UTF-8");
			
			sb.setLength(0);
			sb=null;
			System.out.println(response.toString());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(writer !=null)
				{
					writer.close();
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			try 
			{
				if(in != null)
				{
					in.close();
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		System.out.println("Response ["+response.toString()+"]");
		return response.toString();

	}


}
