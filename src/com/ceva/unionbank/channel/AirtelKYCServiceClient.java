package com.ceva.unionbank.channel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class AirtelKYCServiceClient {
	private static final String AIRTEL_KYC_URL="http://172.24.2.68:9900/getKycDetails/?";
	public static String call(JSONObject jrequest) {
		JSONObject jresponse = new JSONObject();
		BufferedReader in  =null;
		String slog=null;
		long stime = System.currentTimeMillis();
		try {
			slog= jrequest.getString("msisdn")+"|"+jrequest.getString("flowid")+"|";
			System.out.println(slog+"Request|"+jrequest);
			StringBuilder kycurl = new StringBuilder(100);
			kycurl.append(AIRTEL_KYC_URL).append("msisdn=").append(jrequest.getString("msisdn")).append("&requestkey=");
			kycurl.append(jrequest.getString("requestkey")).append("&format=json");
			jresponse.put("respcode", "78");
			jresponse.put("respdesc", "Error In service Call");
			String url = kycurl.toString();
			System.out.println(slog+"URL|"+url);
			System.out.println("TURN-UP-TIME: [WALLET_CUST_VERIFY]" +" REQUEST CONNECT START ["+(System.currentTimeMillis()-stime)+"]");
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println(slog+"ResponseCode|"+responseCode);
			in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			jresponse = new JSONObject(response.toString());
			System.out.println("TURN-UP-TIME: [WALLET_CUST_VERIFY]" +"  TOTAL TIME ["+(System.currentTimeMillis()-stime)+"]");
			if("ACTIVATED".equals(jresponse.getString("status")))
			{
				jresponse.put("respcode", "00");
				jresponse.put("respdesc", "Success");
			}
			else
			{
				
				jresponse.put("respcode", "01");
				jresponse.put("respdesc", jresponse.getString("status"));
			}
			
			in.close();
			System.out.println(slog+"Response|" + response.toString());
			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(slog+"Exception|" + e.getMessage());
		}
		finally {
			try {
				if(in !=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jresponse.toString();

	}
	static final Random random = new Random(); // Or SecureRandom
	static final int startChar = (int) '!';
	static final int endChar = (int) '~';

	/*static String randomString(final int maxLength) {
	  final int length = random.nextInt(maxLength + 1);
	  return random.ints(length, startChar, endChar + 1)
	        .mapToObj((i) -> (char) i)
	        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	        .toString();
	}*/
	
	/*private static String getReqkey()
	{
		
		return randomString(8);
	}*/

	public static void main(String[] args) {
		JSONObject jrequest = new JSONObject();
		try {
			jrequest.put("msisdn", "2348022221569");
			jrequest.put("flowid", "12345678");
			jrequest.put("requestkey", "A0121211");
			jrequest.put("channel", "USSD");
			//JSONObject jresponse = call(jrequest);
			//System.out.println("Response :"+jresponse);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
