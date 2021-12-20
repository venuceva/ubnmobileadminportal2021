package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.reports.CSVReportGeneration;
import com.ceva.base.reports.ExcelReportGeneration;
import com.ceva.base.reports.ReportGeneration;
import com.ceva.util.DBUtils;

public class ReportsDAO {

	private static Logger logger = Logger.getLogger(ReportsDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject reqJSON = null;
	JSONObject mJSON = null;

	/*public ResponseDTO getData(RequestDTO reqDTO) {
		logger.debug("Inside GetData Of Tracking...");
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		HashMap<String, Object> trackedMap = null;

		String txnCntQuery = "";
		String fromDate = null;
		String toDate = null;
		String userid = null;
		String actionname = null;
		try {
			trackedMap = new HashMap<String, Object>();
			reqJSON = reqDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			logger.debug("Request JSON [" + reqJSON + "]");

			fromDate = reqJSON.getString("fromDate");
			toDate = reqJSON.getString("toDate");
			userid = reqJSON.getString("userID").toUpperCase();
			actionname = reqJSON.getString("actionname");

			connection = DBConnector.getConnection();
			txnCntQuery = "select count(*) from AUDIT_DATA "
					    + "WHERE trunc(TXNDATE) between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY') ";

			if(userid != null && !userid.trim().equals(""))
			{
				txnCntQuery = txnCntQuery + " and NET_ID='"+userid.toUpperCase()+"'";
			}

			if(actionname != null && !actionname.trim().equals(""))
			{
				txnCntQuery = txnCntQuery + " and instr('"+actionname+"',TRANSCODE)>0";
			}

			preStmt = connection.prepareStatement(txnCntQuery);
			preStmt.setString(1, fromDate);
			preStmt.setString(2, toDate);
			//preStmt.setString(3, userid);
			rs = preStmt.executeQuery();

			int resCount = 0;
			if (rs.next()) {
				resCount = rs.getInt(1);
			}
			logger.debug("RESULT COUNT OF RECRDS : " + resCount);
			if (resCount > 0) {

				mJSON = getDetails(fromDate, toDate, userid,actionname);
				logger.debug("Genereated JSON is :: " + mJSON);
				trackedMap.put(CevaCommonConstants.TXN_DATA, mJSON);
				responseDTO.setData(trackedMap);
			}
		} catch (Exception e) {
			logger.debug(" The Exception inside getData is  [" + e.getMessage()
					+ "]");
			e.printStackTrace();
			responseDTO.addError("Internal error occured, while executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(preStmt);
			DBUtils.closeResultSet(rs);

			trackedMap = null;

			txnCntQuery = null;
			fromDate = null;
			toDate = null;
			userid = null;
		}
		return responseDTO;
	}

	public JSONObject getDetails(String fromDate, String toDate, String userid,String actionname) {
		JSONObject jres = null;
		JSONArray reportsList = null;
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			jres = new JSONObject();
			reportsList = new JSONArray();
			connection = DBConnector.getConnection();

			String query = "select A.CHANNEL_ID,nvl(A.NET_ID,' '),to_char(A.DATETIME, 'dd-mm-yyyy hh:mm:ss'),nvl(A.TRANS_CODE_DESC,' '),nvl(A.DATA_1,' '),"
				     + "nvl(A.CARD_NO,' '),nvl(A.MESSAGE,' '),nvl(A.DATA_2,' ') from AUDIT_TRAIL A,CEVA_MENU_LIST CML WHERE trunc(A.DATETIME) "
				     + "between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY')  AND CML.MENU_ACTION=A.TRANS_CODE ";
			
			
			String query = "SELECT A.CHANNEL,NVL(A.NET_ID,' '),TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mm:ss'),NVL(A.TRANSCODE_DESC,' '),NVL(A.IP,' '),NVL(A.MESSAGE,' ') "
					+ "FROM AUDIT_DATA A,CEVA_MENU_LIST CML WHERE TRUNC(A.TXNDATE) BETWEEN to_date(?,'DD/MM/YYYY') "
					+ "AND to_date(?,'DD/MM/YYYY') AND CML.MENU_ACTION=A.TRANSCODE ";
			

			if(userid != null && !userid.trim().equals(""))
			{
				query = query + " and A.NET_ID='"+userid.toUpperCase()+"' ";
			}
			if(actionname != null && !actionname.trim().equals(""))
			{
				query = query + " and instr('"+actionname+"',TRANSCODE)>0 order by A.TXNDATE desc";
				
			}
			else
			{
				query = query + " order by A.TXNDATE desc";
			}
			preStmt = connection.prepareStatement(query);

			preStmt.setString(1, fromDate);
			preStmt.setString(2, toDate);
			//preStmt.setString(3, userid);
			rs = preStmt.executeQuery();

			JSONObject txn = null;
			while (rs.next()) {
				txn = new JSONObject();

				txn.put("channel", rs.getString(1));
				txn.put("netID", rs.getString(2));
				txn.put("datetime", rs.getString(3));
				txn.put("action", rs.getString(4));
				txn.put("accessingIP", rs.getString(5));
				txn.put("message", rs.getString(6));

				reportsList.add(txn);

				txn.clear();
				txn = null;
			}

			jres.put("reportsList", reportsList);

 		} catch (Exception e) {
			logger.debug(" The Exception inside getDetails is  ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(preStmt);
			DBUtils.closeResultSet(rs);
 			reportsList = null;
		}
		return jres;
	}*/
	
	public ResponseDTO getData(RequestDTO reqDTO) {
		logger.debug("Inside GetData Of Tracking...");
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		HashMap<String, Object> trackedMap = null;

		String txnCntQuery = "";
		String fromDate = null;
		String toDate = null;
		String userid = null;
		String actionname = null;
		try {
			trackedMap = new HashMap<String, Object>();
			reqJSON = reqDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			logger.debug("Request JSON [" + reqJSON + "]");

			fromDate = reqJSON.getString("fromDate");
			toDate = reqJSON.getString("toDate");
			userid = reqJSON.getString("userID").toUpperCase();
			actionname = reqJSON.getString("actionname");

			connection = DBConnector.getConnection();
			txnCntQuery = "select count(*) from AUDIT_DATA "
					    + "WHERE trunc(TXNDATE) between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY') ";

			if(userid != null && !userid.trim().equals(""))
			{
				txnCntQuery = txnCntQuery + " and NET_ID='"+userid.toUpperCase()+"'";
			}

			if(actionname != null && !actionname.trim().equals(""))
			{
				txnCntQuery = txnCntQuery + " and instr('"+actionname+"',TRANSCODE)>0";
				
			}

			preStmt = connection.prepareStatement(txnCntQuery);
			preStmt.setString(1, fromDate);
			preStmt.setString(2, toDate);
			//preStmt.setString(3, userid);
			rs = preStmt.executeQuery();

			int resCount = 0;
			if (rs.next()) {
				resCount = rs.getInt(1);
			}
			logger.debug("RESULT COUNT OF RECRDS : " + resCount);
			if (resCount > 0) {

				mJSON = getDetails(fromDate, toDate, userid,actionname);
				logger.debug("Genereated JSON is :: " + mJSON);
				trackedMap.put(CevaCommonConstants.TXN_DATA, mJSON);
				responseDTO.setData(trackedMap);
			}
		} catch (Exception e) {
			logger.debug(" The Exception inside getData is  [" + e.getMessage()
					+ "]");
			e.printStackTrace();
			responseDTO.addError("Internal error occured, while executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(preStmt);
			DBUtils.closeResultSet(rs);

			trackedMap = null;

			txnCntQuery = null;
			fromDate = null;
			toDate = null;
			userid = null;
		}
		return responseDTO;
	}

	public JSONObject getDetails(String fromDate, String toDate, String userid,String actionname) {
		JSONObject jres = null;
		JSONArray reportsList = null;
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		PreparedStatement preStmt1 = null;
		ResultSet rs1 = null;
		String query="";
		try {
			jres = new JSONObject();
			reportsList = new JSONArray();
			connection = DBConnector.getConnection();

/*			String query = "select A.CHANNEL_ID,nvl(A.NET_ID,' '),to_char(A.DATETIME, 'dd-mm-yyyy hh:mm:ss'),nvl(A.TRANS_CODE_DESC,' '),nvl(A.DATA_1,' '),"
				     + "nvl(A.CARD_NO,' '),nvl(A.MESSAGE,' '),nvl(A.DATA_2,' ') from AUDIT_TRAIL A,CEVA_MENU_LIST CML WHERE trunc(A.DATETIME) "
				     + "between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY')  AND CML.MENU_ACTION=A.TRANS_CODE ";*/
			
		String queryabc="select distinct A.CHANNEL from AUDIT_DATA A,CEVA_MENU_LIST CML WHERE CML.MENU_ACTION=A.TRANSCODE  AND A.CHANNEL is not null "
		+ "AND ('"+actionname+"' is NULL or CML.MENU_ACTION='"+actionname+"') "
		+ "AND ('"+userid.toUpperCase()+"' is NULL or UPPER(A.NET_ID)='"+userid.toUpperCase()+"') ";
		
		preStmt1 = connection.prepareStatement(queryabc);
		rs1 = preStmt1.executeQuery();
		while (rs1.next()) {
			
		if((rs1.getString(1)).equalsIgnoreCase("WEB")){
			
				query = "SELECT A.CHANNEL,NVL(A.NET_ID,' '),TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mm:ss'),NVL(A.TRANSCODE_DESC,' '),NVL(A.IP,' '),NVL(A.MESSAGE,' ') "
						+ "FROM AUDIT_DATA A,CEVA_MENU_LIST CML WHERE TRUNC(A.TXNDATE) BETWEEN to_date(?,'DD/MM/YYYY') "
						+ "AND to_date(?,'DD/MM/YYYY') AND CML.MENU_ACTION=A.TRANSCODE  AND A.CHANNEL is not null ";
				
	
				if(userid != null && !userid.trim().equals(""))
				{
					query = query + " and UPPER(A.NET_ID)='"+userid.toUpperCase()+"' ";
				}
				if(actionname != null && !actionname.trim().equals(""))
				{
				//	query = query + " and instr('"+actionname+"',TRANSCODE)>0 order by A.TXNDATE desc";
					
					query = query + " and (parent_menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_ACTION='"+actionname+"') "
								+" or menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_ACTION='"+actionname+"') ) order by A.TXNDATE desc";
					
				}
				else
				{
					query = query + " order by A.TXNDATE desc";
				}
			
			}else{
				query = "SELECT NVL(A.CHANNEL,'CHANNEL'),NVL(A.NET_ID,' '),TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mm:ss'),NVL(CML.MENU_NAME,' '),NVL(A.IP,' '),NVL(A.MESSAGE,' ') "
						+ "FROM AUDIT_DATA A,CEVA_MENU_LIST CML WHERE TRUNC(A.TXNDATE) BETWEEN to_date(?,'DD/MM/YYYY') "
						+ "AND to_date(?,'DD/MM/YYYY') AND CML.MENU_ACTION=A.TRANSCODE  AND A.CHANNEL is not null ";
				
	
				if(userid != null && !userid.trim().equals(""))
				{
					query = query + " and UPPER(A.NET_ID)='"+userid.toUpperCase()+"' ";
				}
				if(actionname != null && !actionname.trim().equals(""))
				{
				//	query = query + " and instr('"+actionname+"',TRANSCODE)>0 order by A.TXNDATE desc";
					
					query = query + " and (parent_menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_ACTION='"+actionname+"') "
								+" or menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_ACTION='"+actionname+"') ) order by A.TXNDATE desc";
					
				}
				else
				{
					query = query + " order by A.TXNDATE desc";
				}
			}
		}
			preStmt = connection.prepareStatement(query);

			preStmt.setString(1, fromDate);
			preStmt.setString(2, toDate);
			//preStmt.setString(3, userid);
			rs = preStmt.executeQuery();

			JSONObject txn = null;
			while (rs.next()) {
				txn = new JSONObject();

				txn.put("channel", rs.getString(1));
				txn.put("netID", rs.getString(2));
				txn.put("datetime", rs.getString(3));
				txn.put("action", rs.getString(4));
				txn.put("accessingIP", rs.getString(5));
				txn.put("message", rs.getString(6));

				reportsList.add(txn);

				txn.clear();
				txn = null;
			}

			jres.put("reportsList", reportsList);

 		} catch (Exception e) {
			logger.debug(" The Exception inside getDetails is  ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(preStmt);
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(preStmt1);
			DBUtils.closeResultSet(rs1);
 			reportsList = null;
		}
		return jres;
	}
	
	
	public ResponseDTO getReportGeneration(RequestDTO requestDTO,String querys) {
		
		logger.debug("Inside Files DAO ... getReportGeneration ");

		HashMap<String, Object> storeDataMap = null;
		JSONObject resultJson = null;
		JSONArray searchJSONArray = null;

		
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		PreparedStatement pstmt = null;

		String entityQry = "";
		
		try {
			storeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			searchJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			
			reqJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			
			String jrxmlname=reqJSON.getString("jrxmlname");
			String query=querys;
			String format=reqJSON.getString("format");
			String headval=reqJSON.getString("fieldsval");
			
			
			entityQry = "SELECT RPT_NAME,FORMAT,to_char(sysdate,'dd-month-yyyy'),RPT_DESC FROM REPORT_SCHEDULE_CONFIG WHERE RPT_NAME=? AND PERIOD='I'";
			searchPstmt = connection.prepareStatement(entityQry);
			searchPstmt.setString(1, jrxmlname);
				

				ReportGeneration rg=new ReportGeneration();
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()) {
					json = new JSONObject();
					json.put("ReportName",searchRS.getString(4));
					json.put("JrxmlName",searchRS.getString(1));
					json.put("Formats",format);
					//json.put("Formats",searchRS.getString(2));
					json.put("Gdate",searchRS.getString(3));
					
					
					Map<String, Object> parameters = new HashMap<String, Object>();
					
					parameters.put("QUERY", query);
					parameters.put("SELECT_DATE", searchRS.getString(3));
					parameters.put("SEARCH", reqJSON.getString("searchby"));
					if(format.equalsIgnoreCase("pdf")){
						rg.GeneratePdfReportWithJasperReports(connection,parameters,searchRS.getString(1),searchRS.getString(1),format);	
					}
					
					if(format.equalsIgnoreCase("txt")){
						rg.GeneratePdfReportWithJasperReports(connection,parameters,searchRS.getString(1),searchRS.getString(1),format);	
					}
					
					if(format.equalsIgnoreCase("csv")){
						
						CSVReportGeneration.perform(searchRS.getString(1), connection, query,headval);
						/*rg.GeneratePdfReportWithJasperReports(connection,parameters,searchRS.getString(1),searchRS.getString(1),format);	*/
					}
					
					if(format.equalsIgnoreCase("xlsx")){
						ExcelReportGeneration.perform(searchRS.getString(1), connection, query,headval);
					}
					//String[] str=(searchRS.getString(2)).split(",");
					//for(int i=0;i<str.length;i++){
					//rg.GeneratePdfReportWithJasperReports(connection,parameters,searchRS.getString(1),searchRS.getString(1),str[i]);
					//}
					
					searchJSONArray.add(json);
				}
			
			
			resultJson.put(CevaCommonConstants.CLAIMS2_LIST, searchJSONArray);
			storeDataMap.put(CevaCommonConstants.CLAIMS2_INFO, resultJson);

			//logger.debug("StoreDataMap [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);
			//logger.debug("ResponseDTO [" + responseDTO + "]");

		} catch (SQLException e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("SQLException in getReportGeneration [" + e.getMessage()
					+ "]");
		} catch (Exception e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("Exception in getReportGeneration [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(searchPstmt);
			DBUtils.closeResultSet(searchRS);
			storeDataMap = null;
			resultJson = null;
			searchJSONArray = null;
			entityQry = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO gtReportOfflineConf(RequestDTO requestDTO,String querys) {
		
		logger.debug("Inside Files DAO ... getReportGeneration ");

		HashMap<String, Object> storeDataMap = null;
		JSONObject resultJson = null;
		JSONArray searchJSONArray = null;

		
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		PreparedStatement pstmt = null;

		String entityQry = "";
		
		try {
			storeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			searchJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			
			reqJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			
			String jrxmlname=reqJSON.getString("jrxmlname");
			String query=querys;
			String format=reqJSON.getString("format");
			String headval=reqJSON.getString("fieldsval");
			String offtype=reqJSON.getString("offtype");
			
			
			entityQry = "SELECT REPORT_NAME,REPORT_DESCRIPTION,G_REPORT_DATE,FROM_REPORT_DATE,TO_REPORT_DATE FROM REPORT_OFFLINE_DETAILS  WHERE REPORT_TYPE=? AND STATUS='C' AND G_REPORT_DATE is not null";
			searchPstmt = connection.prepareStatement(entityQry);
			searchPstmt.setString(1, offtype);
				

				ReportGeneration rg=new ReportGeneration();
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()) {
					json = new JSONObject();
					json.put("ReportName",searchRS.getString(2));
					json.put("JrxmlName",searchRS.getString(1));
					json.put("Formats",format);
					json.put("Gdate",searchRS.getString(3));
					json.put("Fdate",searchRS.getString(4));
					json.put("Tdate",searchRS.getString(5));
					
					
					
					searchJSONArray.add(json);
				}
			
			
			resultJson.put(CevaCommonConstants.CLAIMS2_LIST, searchJSONArray);
			storeDataMap.put(CevaCommonConstants.CLAIMS2_INFO, resultJson);

			//logger.debug("StoreDataMap [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);
			//logger.debug("ResponseDTO [" + responseDTO + "]");

		} catch (SQLException e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("SQLException in getReportGeneration [" + e.getMessage()
					+ "]");
		} catch (Exception e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("Exception in getReportGeneration [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(searchPstmt);
			DBUtils.closeResultSet(searchRS);
			storeDataMap = null;
			resultJson = null;
			searchJSONArray = null;
			entityQry = null;
		}
		return responseDTO;
	}	
	
	public ResponseDTO gtOfflineReportDataConf(RequestDTO requestDTO,String querys) {
		
		logger.debug("Inside Files DAO ... getReportGeneration ");

		HashMap<String, Object> storeDataMap = null;
		JSONObject resultJson = null;
		JSONArray searchJSONArray = null;

		
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		PreparedStatement pstmt = null;

		String entityQry = "";
		
		try {
			storeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			searchJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			
			reqJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			
			String jrxmlname=reqJSON.getString("jrxmlname");
			String query=querys;
			String makerid=reqJSON.getString(CevaCommonConstants.MAKER_ID);
			
			entityQry = "SELECT RPT_NAME,FORMAT,to_char(sysdate,'dd-month-yyyy'),RPT_DESC FROM REPORT_SCHEDULE_CONFIG WHERE RPT_NAME=? AND PERIOD='I'";
			searchPstmt = connection.prepareStatement(entityQry);
			searchPstmt.setString(1, jrxmlname);
				

				ReportGeneration rg=new ReportGeneration();
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()) {
					json = new JSONObject();
					json.put("ReportName",searchRS.getString(4));
					json.put("JrxmlName",searchRS.getString(1));
					json.put("Formats","Request Start , please check in File Result Link ");
					json.put("Gdate",searchRS.getString(3));
					
					
					pstmt = connection.prepareStatement("INSERT INTO  offline_report_request(ref_num,report_name,Jrxml_Name,Formats,SELECT_DATE,QUERY_Data,SEARCH_DATA,STATUS,Maker_id,Maker_dt) values(SEQ_FILEUPLOAD.nextval,?,?,?,?,?,?,'P','"+makerid+"',sysdate)");
					pstmt.setString(1, searchRS.getString(4));
					pstmt.setString(2, searchRS.getString(1));
					pstmt.setString(3, searchRS.getString(2));
					pstmt.setString(4, searchRS.getString(3));
					pstmt.setString(5, query);
					pstmt.setString(6, reqJSON.getString("searchby"));
					pstmt.executeUpdate();
					pstmt.close();
					
					
					/*Map<String, Object> parameters = new HashMap<String, Object>();
					
					parameters.put("QUERY", query);
					parameters.put("SELECT_DATE", searchRS.getString(3));
					parameters.put("SEARCH", reqJSON.getString("searchby"));
					//String[] str=(searchRS.getString(2)).split(",");
					for(int i=0;i<str.length;i++){
					rg.GeneratePdfReportWithJasperReports(connection,parameters,searchRS.getString(1),searchRS.getString(1),str[i]);
					}*/
					
					searchJSONArray.add(json);
				}
			
				connection.commit();
			resultJson.put(CevaCommonConstants.CLAIMS2_LIST, searchJSONArray);
			storeDataMap.put(CevaCommonConstants.CLAIMS2_INFO, resultJson);

			//logger.debug("StoreDataMap [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);
			//logger.debug("ResponseDTO [" + responseDTO + "]");

		} catch (SQLException e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("SQLException in getReportGeneration [" + e.getMessage()
					+ "]");
		} catch (Exception e) { responseDTO.addError("Internal Error Occured While Executing.");
			 
			logger.debug("Exception in getReportGeneration [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(searchPstmt);
			DBUtils.closeResultSet(searchRS);
			DBUtils.closePreparedStatement(pstmt);
			storeDataMap = null;
			resultJson = null;
			searchJSONArray = null;
			entityQry = null;
		}
		return responseDTO;
	}
	
	
 
	public ResponseDTO getConfigData(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [AgentDAO][getConfigData]");

	
		HashMap<String, Object> viewDataMap = null;
		Connection connection = null;

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		

		try {
			responseDTO = new ResponseDTO();
			mJSON = new JSONObject();
			reqJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();
			viewDataMap = new HashMap<String, Object>();
			
			 JSONObject json = new JSONObject();
			 JSONObject jsonlmt = new JSONObject();
			 String makerId=reqJSON.getString(CevaCommonConstants.MAKER_ID);
			 String userlink=reqJSON.getString("USER_LINKS");
			 StringBuilder sb=new StringBuilder();
			 sb.append("(");
			 JSONArray nameArray = (JSONArray) JSONSerializer.toJSON(userlink); 
			 for(Object js : nameArray){
	                JSONObject json1 = (JSONObject) js;
/*	                System.out.println(json1.get("name"));
*/	                sb.append("'"+json1.get("name")+"',");
	            }
	           // sb.append(")");
			
					//System.out.println(sb.toString().substring(0, sb.toString().length()-1)+")");
					 String channelQry = "select RPT_NAME,RPT_DESC from REPORT_SCHEDULE_CONFIG WHERE PERIOD='I' AND MODULE_ID in "+sb.toString().substring(0, sb.toString().length()-1)+")"
					 		+ " ORDER BY RPT_DESC";
					 servicePstmt = connection.prepareStatement(channelQry);
					 serviceRS = servicePstmt.executeQuery();
						while (serviceRS.next()) {
							jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
						}
					json.put("REPORT_DETAILS", jsonlmt);
					jsonlmt.clear();
					servicePstmt.close();
					serviceRS.close();
					
				
						
			 viewDataMap.put("Files_List1", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[AgentDAO][getConfigData] SQLException in getConfigData [" + e.getMessage()
					+ "]");
			responseDTO.addError("[AgentDAO][getConfigData] Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			viewDataMap = null;
			
			
		}

		return responseDTO;
	}
	
	public ResponseDTO fetchData(String refrenceno,String makerid) {

		logger.debug("Inside DashboardUsers... ");

		Connection connection = null;
		HashMap<String, Object> branchMap = new HashMap<String, Object>();

		JSONObject resultJson = null;
		JSONObject json = null;
		JSONObject json1 = null;

		JSONArray branchJSONArray = null;

		PreparedStatement branchPstmt = null;
		ResultSet branchRS = null;
		String branchQry="";

		try {

			responseDTO = new ResponseDTO();
			connection = DBConnector.getConnection();

			resultJson = new JSONObject();

			branchJSONArray = new JSONArray();
			 
			 
			 if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("CHANNEL")){
				 branchQry="select CHANNEL_NAME from CHANNEL_MASTER WHERE DISPLAY='Y'";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("LOCATION")){
				 branchQry="select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL";
				 branchPstmt=connection.prepareStatement(branchQry);
				 
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","ALL");

					branchJSONArray.add(json2);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("GROUP")){
				 branchQry="select GROUP_ID||'-'||GROUP_NAME from USER_GROUPS";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("MERCHANT")){
				 branchQry="select ORGANIGATION_ID||'-'||ACCOUNT_NAME from ORG_MASTER";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("BRANCH")){
				 branchQry="select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL order by CLUSTER_ID";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","ALL");

					branchJSONArray.add(json2);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("PRODUCT") && (refrenceno.split("-")[1]).equalsIgnoreCase("BRANCH")){
				 branchQry="select CLUSTER_ID||'-'||CLUSTER_NAME||'@'||(select count(*) from PRODUCT_PERMISSION where BRANCH=CLUSTER_ID AND PRODUCT_CODE='"+refrenceno.split("-")[2]+"') from CLUSTER_TBL order by CLUSTER_ID";
				 branchPstmt=connection.prepareStatement(branchQry);
				 
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","ALL");

					branchJSONArray.add(json2);
				 
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("PRODUCT") && (refrenceno.split("-")[1]).equalsIgnoreCase("SERVICES")){
				 branchQry="select SERVICECODE||'-'||SERVICEDESC||'@'||(select count(*) from LIMIT_PERDAY_RESTRICTION where SERVICE_CODE=SERVICECODE AND PRODUCT_CODE='"+refrenceno.split("-")[2]+"' AND APPLICATION='"+refrenceno.split("-")[3]+"') from BANK_SERVICE_CODE_MASTER WHERE APPLICATION='AGENT' order by SERVICEDESC";
				 branchPstmt=connection.prepareStatement(branchQry);
				 
				
				 
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("ACCOUNT")){
				 branchQry="select DISTINCT ACCOUNT_TYPE from ACCOUNT_OPEN";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("RMCODE")){
				 //branchQry="select distinct RMCODE from RMCODE_TBL where RMCODE is not null order by RMCODE";
				 branchQry="select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL order by CLUSTER_ID";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","ALL");

					branchJSONArray.add(json2);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("TRANSTYPE")){
				// branchQry="select distinct TRANS_TYPE from FUND_TRANSFER_TBL WHERE CHANNEL='"+refrenceno.split("-")[1]+"'";
				 branchQry="select SERVICE_CODE||'-'||UPPER(SERVICE_DESCRIPTION) from MOB_TRANS_MASTER";
				// branchQry="select distinct (select SERVICE_CODE||'-'||SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) from FUND_TRANSFER_TBL";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("TRANSTYPE1")){
				// branchQry="select distinct TRANS_TYPE from FUND_TRANSFER_TBL WHERE CHANNEL='"+refrenceno.split("-")[1]+"'";
				 branchQry="select SERVICE_CODE||'-'||UPPER(SERVICE_DESCRIPTION) from MOB_TRANS_MASTER where FEE='Y'";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("TRANSTYPE2")){
				// branchQry="select distinct TRANS_TYPE from FUND_TRANSFER_TBL WHERE CHANNEL='"+refrenceno.split("-")[1]+"'";
				 branchQry="select SERVICE_CODE||'-'||UPPER(SERVICE_DESCRIPTION) from MOB_TRANS_MASTER where SERVICE_DESCRIPTION like '%Reversal%'";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }/*else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("EXTERNAL")){
				 branchQry="select distinct super_agent from MERCHANT_MASTER WHERE MERCHANT_ID in (select distinct AGENT_ID from AGENT_INFO_COMMON WHERE AGENT_TYPE=?)";  
				 branchPstmt=connection.prepareStatement(branchQry);
				 branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }*/else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("AUDIT")){
				 branchQry="select MENU_NAME from ceva_menu_list where PARENT_MENU_ID='1' order by APPL_CODE,MENU_NAME";  
				 branchPstmt=connection.prepareStatement(branchQry);
				 
			 }/*else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("TELLER")){
				 
				
				 branchQry="select count(*) from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID=UI.COMMON_ID and UI.USER_GROUPS='TELLER' and ULC.LOGIN_USER_ID='"+makerid+"'";  
				 branchPstmt=connection.prepareStatement(branchQry);
				 
				 branchRS=branchPstmt.executeQuery();
				 while (branchRS.next()) {
					 if(branchRS.getInt(1)==0){
						 branchQry="select distinct TELLER_ID from TELLER_TRANS";
						 json1 = new JSONObject();
						 json1.put("accountno","ALL");
						 branchJSONArray.add(json1);
					 }else{
						 branchQry="select distinct TELLER_ID from TELLER_TRANS  WHERE TELLER_ID='"+makerid+"'";  
					 }
				 }
				 branchRS.close();
				 branchPstmt.close();
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("AGENTIDS")){
				 branchQry="select distinct MERCHANT_ID from MERCHANT_MASTER WHERE upper(super_agent)=upper(?)";
				 branchPstmt=connection.prepareStatement(branchQry);
				 branchPstmt.setString(1, refrenceno.split("-")[1]);
					
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("AGENTUSERIDS")){
				 branchQry="select distinct USER_ID from AGENT_INFO_COMMON WHERE AGENT_ID=?"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				 branchPstmt.setString(1, refrenceno.split("-")[1]);
			 }*/else if((refrenceno.split("-")[0]).equalsIgnoreCase("PRODUCT") && (refrenceno.split("-")[1]).equalsIgnoreCase("LIMIT")){
				 branchQry="select distinct PRD_CODE from PRD_DETAILS WHERE LIMIT_CODE is not null"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("PRODUCT") && (refrenceno.split("-")[1]).equalsIgnoreCase("FEE")){
				 branchQry="select distinct PRD_CODE from PRD_DETAILS WHERE FEE_CODE is not null"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("PRODUCT") && (refrenceno.split("-")[1]).equalsIgnoreCase("USER")){
				 branchQry="select distinct PRD_CODE from PRODUCT WHERE STATUS='A'"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("WACCOPEN")){
				 branchQry="select ACTION1 from WALLET_REPORTS_CONFIG where REPORT_NAME='"+refrenceno.split("-")[2]+"'";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("SUSPENSE")){
				 branchQry="select ACCT_NAME from WALLET_SUSPENSION_DATA where ACCT_NAME in ('VAT_ACCOUNT_NO','QUICK_TELLER_SUSPENSION','CEVA_COMMISSION_ACNO','CLICKATELL_SUSPENSION','BANK_COMMISSION_ACNO','UBN_FLOAT','UBN_SUSPENSION','NIBBS_SUSPENSE','CEVA_FLOAT','UBN_CEVA_USSD_AIRTIME_SUSPENSION','PAYBILL_FEE_SUSPENSE')";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("WALLTRAN")){
				 branchQry="select SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER where APPLICATION='"+refrenceno.split("-")[2]+"'";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("BANKLIST")){
				 branchQry="select NIBSSCODE||'-'||BANK_NAME from BANKS_DATA ";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("APPLICATION") && (refrenceno.split("-")[1]).equalsIgnoreCase("WCHANNEL")){
				 if((refrenceno.split("-")[2]).equals("AGENT")) {
					 
				 branchQry="select CHANNEL_NAME from CHANNEL_MASTER where AGENT='Y'";
				 }
				 
				 if((refrenceno.split("-")[2]).equals("WALLET")) {
					 
					 branchQry="select CHANNEL_NAME from CHANNEL_MASTER where WALLET='Y'";
					 
				}
				 branchPstmt=connection.prepareStatement(branchQry);
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("WACCOPEN")){
				 branchQry="select  ACTION_2 from WALLET_REPORT_CONFIG where REPORT_NAME='"+refrenceno.split("-")[2]+"' AND ACTION_1='"+refrenceno.split("-")[1]+"'";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }
			 else if((refrenceno.split("-")[0]).equalsIgnoreCase("WACCOPENSUB")){
				 branchQry="select  ACTION_3 from WALLET_REPORT_CONFIG where REPORT_NAME='"+refrenceno.split("-")[2]+"' AND ACTION_2='"+refrenceno.split("-")[1]+"' AND ACTION_1='"+refrenceno.split("-")[3]+"'";
				 branchPstmt=connection.prepareStatement(branchQry);
				
				 
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("WALLET") && (refrenceno.split("-")[1]).equalsIgnoreCase("TYPE")){
				 branchQry="select 'ALL' dummy from DUAL"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","WALLET");
					branchJSONArray.add(json2);
				JSONObject json3 = new JSONObject();
				   json3.put("accountno","AGENT");
				   branchJSONArray.add(json3);
			 }else if((refrenceno.split("-")[0]).equalsIgnoreCase("INVENTORY") && (refrenceno.split("-")[1]).equalsIgnoreCase("REPORT")){
				 branchQry="SELECT STATE_CODE||'-'||STATE_NAME FROM STATE_MASTER"; 
				 branchPstmt=connection.prepareStatement(branchQry);
				 JSONObject json2 = new JSONObject();
					json2.put("accountno","ALL");
					branchJSONArray.add(json2);
				
			 }

			
			
			branchRS=branchPstmt.executeQuery();

			

			while (branchRS.next()) {

				json = new JSONObject();
				json.put("accountno",branchRS.getString(1));

				branchJSONArray.add(json);

				json.clear();
				json = null;
			}


			logger.info("branchJSONArray ["+branchJSONArray+"]");

			resultJson.put("ACCOUNTNO", branchJSONArray);

			branchMap.put("ACCOUNTNO", resultJson);


			responseDTO.setData(branchMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(branchPstmt);
			DBUtils.closeResultSet(branchRS);
			branchMap = null;

			resultJson = null;
			json = null;

		}

		return responseDTO;
	}
	
	public ResponseDTO SegmentData(String refrenceno,String makerid) {

		logger.debug("Inside DashboardUsers... ");

		Connection connection = null;
		HashMap<String, Object> branchMap = new HashMap<String, Object>();

		JSONObject resultJson = null;
		JSONObject json = null;
		JSONObject json1 = null;

		JSONArray branchJSONArray = null;

		PreparedStatement branchPstmt = null;
		ResultSet branchRS = null;
		String branchQry="";

		try {

			responseDTO = new ResponseDTO();
			connection = DBConnector.getConnection();

			resultJson = new JSONObject();

			branchJSONArray = new JSONArray();
			 
			 
			 if(refrenceno.equalsIgnoreCase("SERVICES")){
				 branchQry="select SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE  MOBILE='Y'";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }
			 
			 if(refrenceno.equalsIgnoreCase("LIFESTYLE")){
				 branchQry="select PRODUCT_ID||'-'||PRODUCT_NAME from ONLINE_PRODUCTS_MASTER";
				 branchPstmt=connection.prepareStatement(branchQry);
				// branchPstmt.setString(1, refrenceno.split("-")[1]);
				 
			 }

			
			
			branchRS=branchPstmt.executeQuery();

			

			while (branchRS.next()) {

				json = new JSONObject();
				json.put("accountno",branchRS.getString(1));

				branchJSONArray.add(json);

				json.clear();
				json = null;
			}


			logger.info("branchJSONArray ["+branchJSONArray+"]");

			resultJson.put("ACCOUNTNO", branchJSONArray);

			branchMap.put("ACCOUNTNO", resultJson);


			responseDTO.setData(branchMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(branchPstmt);
			DBUtils.closeResultSet(branchRS);
			branchMap = null;

			resultJson = null;
			json = null;

		}

		return responseDTO;
	}
	

}
