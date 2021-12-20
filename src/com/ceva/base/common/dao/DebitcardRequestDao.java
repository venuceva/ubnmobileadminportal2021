package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DebitcardRequestDao {



	private Logger logger = Logger.getLogger(BranchDao.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;


	
	public ResponseDTO debitdetailsCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [DebitcardRequestDao][debitdetailsCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry1 = "select (select count(*) from DEBIT_CARD_REQUEST where STATUS='O'),"
	    		+ "(select count(*) from DEBIT_CARD_REQUEST where STATUS='C'),"
	    		+ "(select count(*) from DEBIT_CARD_REQUEST where STATUS='O' and trunc(DATE_OF_REQUEST)=trunc(SYSDATE)),"
	    		+ "(select count(*) from DEBIT_CARD_REQUEST where STATUS='C' and trunc(UPDATE_DATE)=trunc(SYSDATE)) from dual";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry1);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      
	      while (merchantRS.next())
	      {
	        resultJson.put("REQUEST_OPEN",   merchantRS.getString(1));
	        resultJson.put("REQUEST_CLOSED",   merchantRS.getString(2));
	        
	        resultJson.put("TODAY_REQUEST_OPEN",   merchantRS.getString(3));
	        resultJson.put("TODAY_REQUEST_CLOSED",   merchantRS.getString(4));
	      
	      }
	      merchantPstmt.close();
	      merchantRS.close();
	      
	     
	      
	      
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in debitdetailsCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	

	public ResponseDTO debitdetails(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String qrey = "";




		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String status=requestJSON.getString("status");
			
			if(status.equalsIgnoreCase("Open")) {
				status="O";
			}else {
				status="C";
			}
			
			qrey = "select REQUEST_ID,ACCOUNT_NUMBER,CUSTOMER_NAME,PHONE_NUMBER,CARD_TYPE,CHANNEL,to_char(DATE_OF_REQUEST,'dd-mm-yyyy'),decode(STATUS,'O','Open','Closed') from DEBIT_CARD_REQUEST where STATUS='"+status+"'";
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("REQUEST_ID", getlmtfeeRs.getString(1));
				json.put("ACCOUNT_NUMBER", getlmtfeeRs.getString(2));
				json.put("CUSTOMER_NAME", getlmtfeeRs.getString(3));
				json.put("PHONE_NUMBER", getlmtfeeRs.getString(4));
				json.put("CARD_TYPE", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("DATE_OF_REQUEST", getlmtfeeRs.getString(7));
				json.put("FINAL_STATUS", getlmtfeeRs.getString(8));
				lmtJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO debitdetailsView(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		
		logger.debug("inside [AgentDAO][debitdetailsView]");

		
		
		HashMap<String, Object> viewDataMap = null;
		JSONObject resultJson = null;

		Connection connection = null;

		PreparedStatement viewDataPstmt = null;
		PreparedStatement pstmt = null;
		ResultSet viewRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			logger.debug("[DebitcardRequestDao][debitdetailsView] Inside AgentDAO  "+ requestJSON.getString("requestid")+"--- "+requestJSON.getString(CevaCommonConstants.MAKER_ID));

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			viewDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			 JSONObject json = new JSONObject();
		
				 String viewReport="select REQUEST_ID,ACCOUNT_NUMBER,CUSTOMER_NAME,PHONE_NUMBER,CARD_TYPE,CHANNEL,to_char(DATE_OF_REQUEST,'dd-mm-yyyy'),decode(STATUS,'O','Open','Closed'),CUST_CALL,CUST_ADDRESS,AGENT,to_char(UPDATE_DATE,'dd-mm-yyyy') from DEBIT_CARD_REQUEST WHERE REQUEST_ID=?";
					
					viewDataPstmt = connection.prepareStatement(viewReport);
					viewDataPstmt.setString(1,requestJSON.getString("requestid"));
					viewRS = viewDataPstmt.executeQuery();
					
					
					 while(viewRS.next())
						{
						 json=new JSONObject();
						 json.put("REQUEST_ID", viewRS.getString(1));
							json.put("ACCOUNT_NUMBER", viewRS.getString(2));
							json.put("CUSTOMER_NAME", viewRS.getString(3));
							json.put("PHONE_NUMBER", viewRS.getString(4));
							json.put("CARD_TYPE", viewRS.getString(5));
							json.put("CHANNEL", viewRS.getString(6));
							json.put("DATE_OF_REQUEST", viewRS.getString(7));
							json.put("FINAL_STATUS", viewRS.getString(8));
							json.put("CUST_CALL", viewRS.getString(9));
							json.put("CUST_ADDRESS", viewRS.getString(10));
							json.put("AGENT", viewRS.getString(11));
							json.put("UPDATE_DATE", viewRS.getString(12));
							json.put("makerid",requestJSON.getString(CevaCommonConstants.MAKER_ID));
						 } 
					 

			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
				
	        } catch (SQLException e) {
			logger.debug("SQLException in gtFileApprovalAction [" + e.getMessage()
					+ "]");
			responseDTO.addError("[DebitcardRequestDao][debitdetailsView] Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[DebitcardRequestDao][debitdetailsView] SQLException in gtFileApprovalAction [" + e.getMessage()
					+ "]");
			responseDTO.addError("[DebitcardRequestDao][debitdetailsView] Internal Error Occured While Executing.");
		} finally {
			try {

				if (viewDataPstmt != null)
					viewDataPstmt.close();
				if (viewRS != null)
					viewRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			viewDataMap = null;
			resultJson = null;
			
		}

		return responseDTO;
	}
	
	
public ResponseDTO carddetailsack(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		
		logger.debug("inside [DebitcardRequestDao][carddetailsack]");

		
		
		HashMap<String, Object> viewDataMap = null;
		JSONObject resultJson = null;

		Connection connection = null;

		PreparedStatement viewDataPstmt = null;
		PreparedStatement pstmt = null;
		ResultSet viewRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			logger.debug("[DebitcardRequestDao][carddetailsack] Inside DebitcardRequestDao  "+ requestJSON.getString("requestid"));
			
			String callerstatus=requestJSON.getString("callerstatus");
			String custaddress=requestJSON.getString("custaddress");
			String agentname=requestJSON.getString("agentname");
			String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			viewDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			 JSONObject json = new JSONObject();
		
				 String viewReport="UPDATE DEBIT_CARD_REQUEST set CUST_ADDRESS=?,AGENT=?,CUST_CALL=?,UPDATE_DATE=sysdate,MAKER_ID=?,STATUS='C' WHERE REQUEST_ID=?";
					
					viewDataPstmt = connection.prepareStatement(viewReport);
					viewDataPstmt.setString(1,custaddress);
					viewDataPstmt.setString(2,agentname);
					viewDataPstmt.setString(3,callerstatus);
					viewDataPstmt.setString(4,makerid);
					viewDataPstmt.setString(5,requestJSON.getString("requestid"));
					boolean st=viewDataPstmt.execute();
					
					connection.commit();
					json=new JSONObject();
					/*if(st) {
						json.put("result", "Successfully Updated");
					}else {
						json.put("result", "Failed");
					}*/
					
					json.put("result", "Successfully Updated");
					 
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
				
	        } catch (SQLException e) {
			logger.debug("SQLException in gtFileApprovalAction [" + e.getMessage()
					+ "]");
			responseDTO.addError("[DebitcardRequestDao][carddetailsack] Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[DebitcardRequestDao][carddetailsack] SQLException in gtFileApprovalAction [" + e.getMessage()
					+ "]");
			responseDTO.addError("[DebitcardRequestDao][carddetailsack] Internal Error Occured While Executing.");
		} finally {
			try {

				if (viewDataPstmt != null)
					viewDataPstmt.close();
				if (viewRS != null)
					viewRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			viewDataMap = null;
			resultJson = null;
			
		}

		return responseDTO;
	}


}
