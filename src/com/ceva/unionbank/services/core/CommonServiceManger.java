package com.ceva.unionbank.services.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;



public class CommonServiceManger{
	
	private static Logger log = Logger.getLogger(CommonServiceManger.class);
	public static boolean forceDebit(JSONObject jrequest)
	 {
	  JSONObject jresponse = new JSONObject();


		Connection connection = null;
		CallableStatement cstmt = null;
		PreparedStatement pstmt= null;
		boolean status = true;
		try {
			String username = jrequest.getString("username");
			String payref = jrequest.getString("payref");
			String channel = jrequest.getString("channel");
			String flowid = jrequest.getString("flowid");
			String oType =jrequest.has("otype")?jrequest.getString("otype"):"";
			String serviceid =jrequest.has("serviceid")?jrequest.getString("serviceid"):"";
			String frommobile =jrequest.has("frommobile")?jrequest.getString("frommobile"):"";
			String tomobile =jrequest.has("tomobile")?jrequest.getString("tomobile"):"";
			String category="0000";
			String custos ="";
			String txnAmt="";
			String servicecode=jrequest.getString("servicecode");
			System.out.println(username+"|"+flowid+"| Request:"+jrequest.toString());
			txnAmt = (txnAmt==null)?"0":txnAmt;
			username = username.toUpperCase();
			// pGetTxnSettings.pFeeCheck ('collins','USSD','MNT','FUND_TRNS_OTCR','0000','1000', pFeeStr, RetStr, ErrorMsg, RetVal);
			System.out.println(username+"|"+flowid+"| pGetTxnSettings.pFeeCheck Request:"+username+"|"+channel+"|"+oType+"|"+servicecode+"|"+category+"|"+txnAmt);
			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);
			cstmt = connection.prepareCall("{call pGetTxnSettings.pFeeCheck(?,?,?,?,?,?,?,?,?,?)}");

			cstmt.setString(1, username);
			cstmt.setString(2, channel ); 
			cstmt.setString(3, oType);
			cstmt.setString(4, servicecode);
			cstmt.setString(5, category); 
			cstmt.setString(6, txnAmt); 
			cstmt.registerOutParameter(7, Types.VARCHAR); //pFeeStr
			cstmt.registerOutParameter(8, Types.VARCHAR); //RetStr
			cstmt.registerOutParameter(9, Types.VARCHAR); //ErrorMsg
			cstmt.registerOutParameter(10, Types.NUMERIC); //RetVal

			cstmt.executeQuery();
			String feeData = cstmt.getString(7);
			String returnData = cstmt.getString(8);
			String errorMsg = cstmt.getString(9);
			int resocode = cstmt.getInt(10);
			cstmt.close();

			System.out.println(username+"|"+flowid+"| pGetTxnSettings.pFeeCheck Response-feeData:"+feeData+"|returnData:"+returnData+"|errorMsg:"+errorMsg+"|resocode:"+resocode);
			
		/*	String Query = "insert into CONDITIONAL_DEBIT_TBL (PAYMET_REF_NO,BRANCH_CODE,AMOUNT,FEE,SERVICECODE,CHANNEL"
					+ ",SERVICEID,FROMMOBILE,TOMOBILE,ACCCOUNTNUMBER,USERNAME,TRANS_DTTM,CALLERDESC,STATUS,REQUEST,RESPONSE,"
					+ "RESPCODE,RESDESC) VALUES(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(Query);
			pstmt.setString(1, payref);
			pstmt.setString(2, jrequest.getString("branchCode"));
			pstmt.setString(3, jrequest.getString(txnAmt));
			pstmt.setString(4, jrequest.getString(feeData));
			pstmt.setString(5, jrequest.getString(servicecode));
			pstmt.setString(6, jrequest.getString(channel));
			pstmt.setString(7, jrequest.getString(serviceid));
			pstmt.setString(8, jrequest.getString(frommobile));
			pstmt.setString(9, jrequest.getString(tomobile));
			pstmt.setString(10, jrequest.getString("fromAcccountNumber"));
			pstmt.setString(11, jrequest.getString(username));
			pstmt.setString(12, jrequest.getString("description"));
			pstmt.setString(13, jrequest.getString("O"));
			pstmt.setString(14, jrequest.getString(""));
			pstmt.setString(15, jrequest.getString(""));
			pstmt.setString(16, jrequest.getString(""));
			pstmt.setString(17, jrequest.getString(""));
			int count = pstmt.executeUpdate();*/
			connection.commit();
			
			/*if(resocode==0)
				status=true;*/	
			
			System.out.println("Final Status ["+status+"]");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("|getServicePacksChildren| Exception occured.."+ sqle);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("|getServicePacksChildren| Exception occured.."+ e);
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}
		return status;
	 }
	
	public static  JSONArray getDebitCards(String custid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray pans = null;
		try {
			conn = DBConnector.getConnection();
			String Query = 	"SELECT PAN,EXPIRY_DATE FROM CARDS_ENQUIRY where CUSTOMER_ID in (?) and CARD_STATUS=1 and HOLD_RSP_CODE is null";
			pstmt = conn.prepareStatement(Query);
			pstmt.setString(1, custid);
			rs = pstmt.executeQuery();
			List<String> pan = new ArrayList<>();
			while(rs.next()) 
			{
				pan.add(rs.getString("PAN")+"|"+rs.getString("EXPIRY_DATE"));

			}
			pans = new JSONArray(pan);
		} catch (SQLException  ex) {

			ex.printStackTrace();
		}
		finally
		{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return pans;

	}
	
	
	public static String getfundtransferPayRef()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String getfundPayRef()
	{
		return "";
	}
	public static void main(String[] args) {
		
		//System.out.println(getDebitCardsByMobileNo("2348023038766", "12345667889"));
		
		JSONObject request = new JSONObject();
		
		 		try {
		 			
				request.put("servicecode", "FUND_TRNS_OTCR");
		        request.put("channel", "USSD");
		        request.put("otype", "AIRTEL");
		        request.put("payref", getfundtransferPayRef());
		        request.put("fromAcccountNumber", "0005534521");
		        request.put("amount", "");
		        request.put("branchCode", "1234");
		        request.put("username", "2348036010740");
		        request.put("flowid", "181818181");
		        request.put("description", "charging fee for Balance enquiry");//FORCE_DEBIT
		 		} catch (JSONException e) {
					e.printStackTrace();
				}		
		System.out.println(forceDebit(request));
		
	}
}
