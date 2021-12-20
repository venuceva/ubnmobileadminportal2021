package com.ceva.unionbank.channel;





import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ceva.unionbank.wallet.settlement.services.core.WalletSettlementServiceCaller;
import com.ceva.wallet.core.service.ServiceWrapper;

public class CallAgentServices {

	private static Logger logger = Logger.getLogger(CallAgentServices.class);
	
	public static JSONObject agentFund(String userid,String channel,String craccount,String txnamt,String narration,String paymetrefno)
	{
		JSONObject jresponse = new JSONObject();
		
		try {
			logger.debug("Inside  agentFund.... ");
			JSONObject jrequest = new JSONObject();
			JSONObject jheader = new JSONObject();
			jheader.put("userid", userid);
			jheader.put("flowid", ""+System.currentTimeMillis());
			jheader.put("channel", channel);
			jheader.put("requesttype", "TRANSAC");// fixed
			jheader.put("nanotime", ""+System.nanoTime());//fixed
			
			logger.debug("jheader  agentFund.... "+jheader);

			JSONObject jbody = new JSONObject();


			jbody.put("txnrefno", "RFD"+System.currentTimeMillis());
			jbody.put("craccount", craccount); /*"5621684582"*/
			jbody.put("cracctype", "W");
			jbody.put("txnamount", txnamt);//
			jbody.put("curreny", "NAIRA");
			jbody.put("txntype", "T"); // A - Agent Initiated || C - Customer Initiated
			jbody.put("servicecode", "AGENTFUND");
			jbody.put("exttxnrefno", paymetrefno);
			jbody.put("crnarration", narration);
			jbody.put("drnarration", narration);
			jbody.put("feenarration", narration);
			jbody.put("txnexactcode", "999999");
			jbody.put("txnnano", ""+System.nanoTime());


			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			
			logger.debug("jbody  agentFund.... "+jbody);

			System.out.println(jrequest);
			jresponse= new ServiceWrapper().caller(jrequest);
			logger.debug("jresponse  agentFund.... "+jresponse);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jresponse;
	}


	public static JSONObject paybill(String userid,String channel,String draccount,String txnamt,String narration,String paymetrefno)
	{
		JSONObject jrequest = new JSONObject();
		JSONObject jresponse = new JSONObject();
		/**
		 * 1. channel
		 * 2. flowid
		 * 3. userid
		 * 4. requesttype
		 * 5. nanotime
		 */
		try {
			logger.debug("Inside  paybill.... ");
			JSONObject jheader = new JSONObject();
			jheader.put("userid", userid);
			jheader.put("flowid", ""+System.currentTimeMillis());
			jheader.put("channel", channel);
			jheader.put("requesttype", "TRANSAC");
			jheader.put("nanotime", ""+System.nanoTime());
			logger.debug("jheader  paybill.... "+jheader);
			JSONObject jbody = new JSONObject();

			//String txnrefno=""+System.currentTimeMillis();


			
			//System.out.println(postAccountEntries(jrequest));
			//System.out.println(new ServiceImpl().walletTransaction(jrequest));

			System.out.println("------------------------");
			jbody.put("txnrefno", "R"+paymetrefno);
			jbody.put("draccount", draccount);
			jbody.put("dracctype", "W");// W - Wallet || S suspension
			jbody.put("txnamount", txnamt);
			jbody.put("curreny", "NAIRA");
			jbody.put("txntype", "C"); // A - Agent Initiated || C - Customer Initiated
			jbody.put("servicecode", "WALPAYBILLAGN");
			jbody.put("exttxnrefno", paymetrefno);
			jbody.put("crnarration", narration);
			jbody.put("drnarration", narration);
			jbody.put("feenarration", narration);
			jbody.put("txnexactcode", "000001");
			jbody.put("txnnano", ""+System.nanoTime());
			jbody.put("reversal", "Y");
			jbody.put("origintxnrefno", paymetrefno);
			logger.debug("jbody  paybill.... "+jbody);
			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			logger.debug("jrequest  paybill.... "+jrequest);
			
			jresponse= new ServiceWrapper().caller(jrequest);
			logger.debug("jresponse  paybill.... "+jresponse);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jresponse;
	}

	public static JSONObject fundotherbank(String userid,String channel,String draccount,String txnamt,String narration,String paymetrefno)
	{
		JSONObject jrequest = new JSONObject();
		JSONObject jresponse = new JSONObject();
		try {
			logger.debug("Inside  fundotherbank.... ");
			JSONObject jheader = new JSONObject();
			jheader.put("userid", userid);
			jheader.put("flowid", ""+System.currentTimeMillis());
			jheader.put("channel", channel);
			jheader.put("requesttype", "TRANSAC");
			jheader.put("nanotime", ""+System.nanoTime());
			logger.debug("jheader  fundotherbank.... "+jheader);
			
			
			JSONObject jbody = new JSONObject();
			System.out.println("------------------------");
			jbody.put("txnrefno", "R"+paymetrefno);
			jbody.put("draccount", draccount);
			jbody.put("dracctype", "W");// W - Wallet || S suspension
			jbody.put("txnamount", txnamt);
			jbody.put("curreny", "NAIRA");
			jbody.put("txntype", "C"); // A - Agent Initiated || C - Customer Initiated
			jbody.put("servicecode", "WALAGNOTBANK");
			jbody.put("exttxnrefno", paymetrefno);
			jbody.put("crnarration", narration);
			jbody.put("drnarration", narration);
			jbody.put("feenarration", narration);
			jbody.put("txnexactcode", "000001");
			jbody.put("txnnano", ""+System.nanoTime());
			jbody.put("reversal", "Y");
			jbody.put("origintxnrefno", paymetrefno);
			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			
			logger.debug("jbody  fundotherbank.... "+jbody);
			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			
			logger.debug("jrequest  fundotherbank.... "+jrequest);
			jresponse= new ServiceWrapper().caller(jrequest);
			logger.debug("jresponse  fundotherbank.... "+jresponse);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jresponse;
	}

	public static JSONObject fundunionbank(String userid,String channel,String draccount,String txnamt,String narration,String paymetrefno)
	{
		JSONObject jrequest = new JSONObject();
		JSONObject jresponse = new JSONObject();
		try {
			logger.debug("Inside  fundunionbank.... ");
			JSONObject jheader = new JSONObject();
			jheader.put("userid", userid);
			jheader.put("flowid", ""+System.currentTimeMillis());
			jheader.put("channel", channel);
			jheader.put("requesttype", "TRANSAC");
			jheader.put("nanotime", ""+System.nanoTime());
			logger.debug("jheader  fundunionbank.... "+jheader);
			JSONObject jbody = new JSONObject();
			
			
			/*System.out.println(new ServiceImpl().walletTransaction(jrequest));*/

			System.out.println("------------------------");
			jbody.put("txnrefno", "R"+paymetrefno);
			jbody.put("draccount", draccount);
			jbody.put("dracctype", "W");// W - Wallet || S suspension
			jbody.put("txnamount", txnamt);
			jbody.put("curreny", "NAIRA");
			jbody.put("txntype", "C"); // A - Agent Initiated || C - Customer Initiated
			jbody.put("servicecode", "WALAGNOWNBANK");
			jbody.put("exttxnrefno", paymetrefno);
			jbody.put("crnarration", narration);
			jbody.put("drnarration", narration);
			jbody.put("feenarration", narration);
			jbody.put("txnexactcode", "000001");
			jbody.put("txnnano", ""+System.nanoTime());
			jbody.put("reversal", "Y");
			jbody.put("origintxnrefno", paymetrefno);
			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			
			logger.debug("jbody  fundunionbank.... "+jbody);
			
			jrequest.put("jheader", jheader);
			jrequest.put("jbody", jbody);
			logger.debug("jrequest  fundunionbank.... "+jrequest);
			jresponse= new ServiceWrapper().caller(jrequest);
			logger.debug("jresponse  fundunionbank.... "+jresponse);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jresponse;
	}
	
	public	static JSONObject  cashDepsoti(String mobileNo,String cust_mobile,String amount,String pin)
	{
		JSONObject request = new JSONObject();
		String response="";
		JSONObject resp=null;
		try {
			request.put("channel", "WEB");
	         request.put("userid", mobileNo);
	         request.put("custmobno", cust_mobile);
	         request.put("amount", amount);
	         request.put("pin", pin);
	         //request.put("refernceno", ref);
	        // request.put("token", otp);
	         request.put("reqtype", "AGENTCASHDEP" );
	         
	        // resp =new ServiceWrapper().caller(jrequest);
	         System.out.println("Response Message ::: "+resp);
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
	return resp;	
	}
	
	public	static JSONObject  cashWithdrawal (String mobileNo,String cust_mobile,String amount,String ref,String otp)
	{
		JSONObject request = new JSONObject();
		String response="";
		JSONObject resp=null;
		try {
			request.put("channel", "WEB");
	         request.put("userid", mobileNo);
	         request.put("custmobno", cust_mobile);
	         request.put("amount", amount);
	         request.put("refernceno", ref);
	         request.put("token", otp);
	         request.put("reqtype", "AGENTCASHWITH" );
	         
	         //resp =AgentWalletServiceCaller.route(request);
	         System.out.println("Response Message ::: "+resp);
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
	return resp;	
	}
	
	public static void agentfundGetStatus ()
	{
		
		try {

			JSONObject request = new JSONObject();
			request.put("userid", "2348149339004");
			request.put("channel", "USSD");
			request.put("exttxnrefno", "000018181108115853473808527321");
			request.put("walletrefno", "10321843473808468714");
			request.put("reqtype", "AGNUNIONTRNS_GET_STATUS" );
			request.put("transtype", "NONFINANCIAL");
			
			System.out.println( "Request :::" +request );
			JSONObject response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response );


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}	
	
	public static void agentfundFetchdata()
	{
		
		try {

			JSONObject request = new JSONObject();
			request.put("userid", "2348152190597");
			request.put("channel", "MOBILE");
			request.put("exttxnrefno", "AW1009703111707650330");
			request.put("walletrefno", "9064812636960973008");
			request.put("reqtype", "AGENTFUND_FETCH_DATA" );
			request.put("transtype", "NONFINANCIAL");
			
			// request.put("type", "WALLET" );
			
			request.put("type", "BANK" );
			
			System.out.println( "Request :::" +request );
			JSONObject response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response );


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		agentfundFetchdata();
	}
	
}
