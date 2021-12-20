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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DashboardDAO {
	
	private static Logger logger = Logger.getLogger(DashboardDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
	public ResponseDTO DashboardDetails(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][ApprovalAll]");

		logger.debug("Inside  ApprovalAll.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;
		String queries="";


		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			queries ="select distinct DM.DASHBOARD_LABLE,DM.GROUP_ID,DM.DASH_BOARD_ACTION"
					+ " from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC,USER_LINKED_ACTION ULA,USER_ACTION_LINKS UAL,DASHBOARD_MASTER DM  "
			+"where UI.COMMON_ID=ULC.COMMON_ID AND "
			+"ULA.GROUP_ID=UI.USER_GROUPS  AND  "
			+"ULA.ID=UAL.ID  AND "
			+"UAL.ACTION is null AND "
			+"DM.GROUP_ID=UAL.ALIAS_NAME AND  "
			+"ULC.LOGIN_USER_ID='"+maker_id+"' ";
			
		
			servicePstmt = connection.prepareStatement(queries);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("RELATION",serviceRS.getString(1));
				 json.put("RELATION_CODE",serviceRS.getString(2));
				 json.put("RELATION_ACTION",serviceRS.getString(3));
				 IncomeMTFilesJSONArray.add(json);
				}
				

			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in ApprovalAll [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in ApprovalAll [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceIdRS != null)
					serviceIdRS.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			serviceDataMap = null;
			resultJson = null;
			IncomeMTFilesJSONArray.clear();
			IncomeMTFilesJSONArray = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO TopAgentSearchDetails(RequestDTO requestDTO) {

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

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String branchid="";
		String usertype="";


		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			
			String wherestr="";
			String whereststus="";
			 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			 String remoteip=requestJSON.getString("remoteip");
			 
			 /*String searchid=requestJSON.getString("searchid");
			 
			 if(searchid.equalsIgnoreCase("mobilenumber1")) {
				 wherestr=" AND aci.mobile_number='234"+requestJSON.getString("telephone")+"'" ;
			 }
			 
			 if(searchid.equalsIgnoreCase("storeid1")) {
				 wherestr=" AND UPPER(sm.STORE_NAME) like UPPER('%"+requestJSON.getString("storeid")+"%')" ;
			 }
			 
			 if(searchid.equalsIgnoreCase("terminalid1")) {
				 wherestr=" AND tm.terminal_id='"+requestJSON.getString("terminalid")+"'" ;
			 }
			 
			 if(searchid.equalsIgnoreCase("serialnumber1")) {
				 wherestr=" AND tm.serial_no='"+requestJSON.getString("serialnumber")+"'" ;
			 }
			 
			 if(searchid.equalsIgnoreCase("state1")) {
				 wherestr=" AND UPPER(sm.STATE)=UPPER('"+(requestJSON.getString("state")).split("-")[1]+"')" ;
				 
				 System.out.println("kailash here "+requestJSON.getString("localGovernment"));
				 if(!(requestJSON.getString("localGovernment")).equalsIgnoreCase("")) {
					 wherestr=wherestr+" AND UPPER(sm.LOC_GOV)=UPPER('"+requestJSON.getString("localGovernment")+"')" ; 
				 }
			 }
			 
			 if(!(requestJSON.getString("status")).equalsIgnoreCase("ALL")) {
				 whereststus=" AND tm.STATUS='"+requestJSON.getString("status")+"'" ; 
			 }*/
			 
			/*qrey = "select aci.mobile_number,acm.first_name,sm.STORE_NAME,tm.terminal_id,tm.serial_no,tm.STATUS,tm.cust_id,sm.STATE,sm.LOC_GOV from AGENT_CONTACT_INFO ACI,AGENT_CUSTOMER_MASTER ACM,STORE_MASTER SM,TERMINAL_MASTER TM "
					+ "WHERE aci.cust_id=acm.id and aci.cust_id=sm.cust_id and sm.STORE_ID=tm.STORE_ID "; */
			 
			 qrey = "select * from (select NVL(sum(wft.TXN_AMT),0),NVL(count(wft.TXN_AMT),0),aci.MOBILE_NUMBER,acm.FIRST_NAME,NVL(aci.RL_LGA,' '),NVL(aci.r_state,' ') "
			 		+ "from WALLET_FIN_TXN wft,AGENT_CONTACT_INFO aci,AGENT_CUSTOMER_MASTER acm where "
			 		+ "not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||wft.txn_ref_no) "
			 		+ "and substr(wft.txn_ref_no,1,1)<>'R' and wft.USER_ID=aci.MOBILE_NUMBER and aci.cust_id=acm.id   "
			 		/*+ "and to_char(wft.TXN_STAMP,'dd-mm-yyyy')=to_char(to_date('2019-08-01','yyyy-mm-dd'),'dd-mm-yyyy') "*/
			 		+ "group by aci.MOBILE_NUMBER,acm.FIRST_NAME,NVL(aci.RL_LGA,' '),NVL(aci.r_state,' ')  "
			 		+ "order by NVL(count(wft.TXN_AMT),0) desc) WHERE rownum<6";

			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("SUM_TXN_AMT", getlmtfeeRs.getString(1));
				json.put("CNT_TXN", getlmtfeeRs.getString(2));
				json.put("MOBILE_NUMBER", getlmtfeeRs.getString(3));
				json.put("FIRST_NAME", getlmtfeeRs.getString(4));
				json.put("RL_LGA", getlmtfeeRs.getString(5));
				json.put("r_state", getlmtfeeRs.getString(6));
				
				lmtJsonArray.add(json);
			}
			//NotificationJson.addFiled(makerId, "Hai");
			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}
				
				if (serviceRS != null) {
					serviceRS.close();
				}

				if (servicePstmt != null) {
					servicePstmt.close();
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
	
	public ResponseDTO ChannelDetails(RequestDTO requestDTO) {

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

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String branchid="";
		String usertype="";


		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			
			String wherestr="";
			String whereststus="";
			 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			 String remoteip=requestJSON.getString("remoteip");
			 
			
			 qrey = "select CHANNEL,sum(AMOUNT),count(*) from wallet_fin_txn group by CHANNEL";

			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("CHANNEL", getlmtfeeRs.getString(1));
				json.put("AMOUNT", getlmtfeeRs.getString(2));
				json.put("COUNT", getlmtfeeRs.getString(3));
				
				
				lmtJsonArray.add(json);
			}
			//NotificationJson.addFiled(makerId, "Hai");
			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}
				
				if (serviceRS != null) {
					serviceRS.close();
				}

				if (servicePstmt != null) {
					servicePstmt.close();
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

}
