package com.ceva.unionbank.channel;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ceva.unionbank.common.successreversal.services.core.SuccessTransReversalServiceCaller;
import com.ceva.unionbank.wallet.settlement.services.core.WalletSettlementServiceCaller;

public class SettlementServiceCall {
	
	private static Logger logger = Logger.getLogger(SettlementServiceCall.class);
	
	public static void main(String args[]) {
		//agentfundFetchdata();
	}

	public static String agentfundGetStatus(HashMap<String,String> req)
	{
		String responsemsg="";
		JSONObject response = null;
		try {

			JSONObject request = new JSONObject();
			/*request.put("userid", "2348152190597");
			request.put("channel", "MOBILE");
			request.put("exttxnrefno", "AW1009703111707650330");
			request.put("walletrefno", "9064812636960973008");
			request.put("reqtype", "AGENTFUND_GET_STATUS" );
			request.put("transtype", "NONFINANCIAL");*/
			
			request.put("userid", req.get("userid"));
			request.put("channel", req.get("channel"));
			request.put("exttxnrefno", req.get("exttxnrefno"));
			request.put("walletrefno", req.get("walletrefno"));
			request.put("reqtype", req.get("reqtype")+"_GET_STATUS" );
			request.put("transtype", "NONFINANCIAL");
			
			System.out.println( "Request :::" +request );
			response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response);
			responsemsg=response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responsemsg;
	}
	
	public static String agentfundFetchdata(HashMap<String,String> req)
	{
		String responsemsg="";
		JSONObject response = null;
		try {

			JSONObject request = new JSONObject();
			/*request.put("userid", "2348152190597");
			request.put("channel", "MOBILE");
			request.put("exttxnrefno", "AW1009703111707650330");
			request.put("walletrefno", "9064812636960973008");
			request.put("reqtype", "AGENTFUND_FETCH_DATA" );*/
			
			request.put("userid", req.get("userid"));
			request.put("channel", req.get("channel"));
			request.put("exttxnrefno", req.get("exttxnrefno"));
			request.put("walletrefno", req.get("walletrefno"));
			request.put("reqtype", req.get("reqtype")+"_FETCH_DATA" );
			
			request.put("transtype", "NONFINANCIAL");
			
			// request.put("type", "WALLET" );
			
			request.put("type", req.get("type") );
			
			System.out.println( "Request :::" +request );
			response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response );
			responsemsg=response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responsemsg;
	}
	
	public static String agentfundReversalPostings(HashMap<String,String> req)
	{
		String responsemsg="";
		JSONObject response = null;
		try {


			JSONObject request = new JSONObject();
			request.put("userid", req.get("userid"));
			request.put("channel", req.get("channel"));
			request.put("extpaymetrefno", req.get("extpaymetrefno"));
			request.put("walletpaymetrefno", req.get("walletpaymetrefno"));
			request.put("reqtype", req.get("reqtype")+"_"+getActionVal(req.get("action")) );
			request.put("transtype", "FINANCIAL");
			request.put("txnamt", req.get("txnamt"));
			
			
			System.out.println( "Request :::" +request );
			response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response );

			responsemsg=response.toString();
		


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responsemsg;
	}
	
	public static String agentfundReversalPostingsFileupload(HashMap<String,String> req)
	{
		String responsemsg="";
		JSONObject response = null;
		try {


			JSONObject request = new JSONObject();
			request.put("userid", req.get("userid"));
			request.put("channel", req.get("channel"));
			request.put("extpaymetrefno", req.get("extpaymetrefno"));
			request.put("walletpaymetrefno", req.get("walletpaymetrefno"));
			request.put("reqtype", req.get("reqtype")+"_"+req.get("action") );
			request.put("transtype", "FINANCIAL");
			request.put("txnamt", req.get("txnamt"));
			
			
			System.out.println( "Request :::" +request );
			response =  WalletSettlementServiceCaller.route(request);
			System.out.println("Response :::" +response );

			responsemsg=response.toString();
		


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responsemsg;
	}
	
	
	
	public static String getActionVal(String str) {
		String rval="";
		if(str.equalsIgnoreCase("Wallet Funding")) {
			rval="WALFUND";
		}
		
		if(str.equalsIgnoreCase("Wallet Reversal")) {
			rval="WALREV";
		}
		
		if(str.equalsIgnoreCase("BankPosting")) {
			rval="BANKPOST";
		}
		
		if(str.equalsIgnoreCase("Wallet Posting")) {
			rval="WALPOST";
		}
		
		if(str.equalsIgnoreCase("DisCard")) {
			rval="DISCARD";
		}
		return rval;
		
	}
	
	public static boolean getTxnCodevalid(String txncode) {
		boolean vald=false;
		if(txncode.contentEquals("AGENTFUND")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALLETOACC")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALAGNOTBANK")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNPAYBILLAIRTIME")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALPAYBILLAGN")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALAGNOWNBANK")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDFTXNOWN")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDCSHWTDOTH")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDFTXNOTH")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDCSHWTDOWN")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGCASHWTHD")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGCASHDEP")) {
			vald=true;
		}
		
		return vald;
	}
	
	
	public static boolean getTxnCodeActionvalid(String txncode,String Action) {
		boolean vald=false;
		if(txncode.contentEquals("AGENTFUND") && Action.contentEquals("WALFUND")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALLETOACC") && Action.contentEquals("WALREV")) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALAGNOTBANK") && (Action.contentEquals("WALREV") || Action.contentEquals("BANKPOST"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNPAYBILLAIRTIME") && (Action.contentEquals("WALREV") || Action.contentEquals("BANKPOST"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALPAYBILLAGN") && (Action.contentEquals("WALREV") || Action.contentEquals("BANKPOST"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("WALAGNOWNBANK") && Action.contentEquals("WALREV")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDFTXNOWN") && (Action.contentEquals("WALPOST") || Action.contentEquals("BANKPOST") || Action.contentEquals("DISCARD"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDCSHWTDOTH") && (Action.contentEquals("WALPOST") || Action.contentEquals("BANKPOST") || Action.contentEquals("DISCARD"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDFTXNOTH") && (Action.contentEquals("WALPOST") || Action.contentEquals("BANKPOST") || Action.contentEquals("DISCARD"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGNCRDCSHWTDOWN") && (Action.contentEquals("WALPOST") || Action.contentEquals("BANKPOST") || Action.contentEquals("DISCARD"))) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGCASHWTHD") && Action.contentEquals("BANKPOST")) {
			vald=true;
		}
		
		if(txncode.contentEquals("AGCASHDEP") && Action.contentEquals("BANKPOST")) {
			vald=true;
		}
		
		return vald;
	}
	
	
	public static String corePostingsRev(String reqtype,String userid,String exttxnrefno)
	{

		String responsemsg="";
		JSONObject response = null;
		try {
			
			JSONObject request1 = new JSONObject();
			
			request1.put("reqtype", reqtype);
			request1.put("userid", userid);
			request1.put("channel", "WEB");
			request1.put("exttxnrefno", exttxnrefno);
			 
			 System.out.println( "Request kailash :::" +request1 );
			 
			response =  SuccessTransReversalServiceCaller.route(request1);
			
			System.out.println("Response Json :::" + response );
			responsemsg=response.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return responsemsg;
	}
	
	public static String walletReversal(String reqtype,String userid,String walletrefno) {
		
		String responsemsg="";
		JSONObject response = null;
		try {
			
			JSONObject request1 = new JSONObject();
			
			request1.put("reqtype", reqtype);
			request1.put("userid", userid);
			request1.put("channel", "WEB");
			request1.put("walletrefno", walletrefno);
			
			System.out.println( "Request :::" +request1 );
			response =  SuccessTransReversalServiceCaller.route(request1);
			
			System.out.println("Response Json :::" + response );
			responsemsg=response.toString();
			
		} catch (Exception e) {	
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return responsemsg;
	}
	
}
