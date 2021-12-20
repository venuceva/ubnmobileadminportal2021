package com.ceva.base.common.dao;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.fileupload.UnsettledFileValidation;
import com.ceva.util.DBUtils;
import com.itextpdf.text.html.HtmlEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CampaignManagementDAO {

	private static Logger logger = Logger.getLogger(CampaignManagementDAO.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	Connection connection = null;
	JSONArray jsonArray=null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	JSONObject resonseJSON = null;

	
	
	public JSONObject getProductNames()
	{
		JSONArray jarr = new JSONArray();
		JSONObject jres = new JSONObject();

		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String txnCntQuery = "";

		try
		{
			connection = DBConnector.getConnection();
			txnCntQuery = "select DISTINCT PRD_CODE,PRD_CODE||'-'||PRD_NAME from PRODUCT";
			preStmt = connection.prepareStatement(txnCntQuery);
			rs = preStmt.executeQuery();

			JSONObject txn = null;
			while(rs.next())
			{
				txn = new JSONObject();

				txn.put("TEXT", rs.getString(2));
				txn.put("VALUE", rs.getString(1));

				jarr.add(txn);

				txn.clear();txn=null;
			}

			jres.put("ACTIONLIST", jarr);
			logger.debug("[HistoryTrackingDAO][getClassNames][Response JSON : "+jres+"]");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return jres;
	}


	public ResponseDTO insertCmpManagement(RequestDTO requestDTO,String strmsg,String templatename) {
		logger.debug("insertBenifit");
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		JSONObject resultJson = new JSONObject();
		HashMap<String, Object> benifitDataMap = new HashMap<String, Object>();
		PreparedStatement balpsmt = null;

		ResultSet resultset = null;
		String benefitQry = null;

		try {
			String benefitInsertQry = "INSERT INTO CAMP_TEMPLATE_MASTER (TEMPLATE_ID, TEMPLATE, CREATEDBY,CREATEDDATE,STATUS) VALUES(?,?,?,sysdate,'P')";
			connection = DBConnector.getConnection();
			balpsmt = connection.prepareStatement(benefitInsertQry);
			balpsmt.setString(1, templatename);
			balpsmt.setString(2, makeALign1(strmsg));
			balpsmt.setString(3, requestJSON.getString("makerId"));
			int insert=balpsmt.executeUpdate();
			benifitDataMap.put("INSERTED", insert);
			connection.commit();
			responseDTO.setData(benifitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(resultset);
		}
		return responseDTO;
	}
	
	public ResponseDTO insertAssginCmpManagement(RequestDTO requestDTO,String tmpname) {
		logger.debug("insertBenifit");
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		JSONObject resultJson = new JSONObject();
		HashMap<String, Object> benifitDataMap = new HashMap<String, Object>();
		PreparedStatement balpsmt = null;

		
		String benefitQry = null;
		String benefitInsertQry = null;
		try {
			
			
			
			
			if((requestJSON.getString("msgtype")).equalsIgnoreCase("NOTIFICATION")){
				
			
				
			benefitInsertQry = "INSERT INTO CAMP_ASSIGN (ID,TEMP_NAME,ASS_TEMP_TYPE,ASS_TEMP_VAL,STATUS,MAKER_ID,MAKERDTTM,SEND_TYPE,FROMTIME,TOTIME,CHANNEL) VALUES('1',?,?,?,'A',?,sysdate,'NOTIFICATION',?,?,?)";

		

			connection = DBConnector.getConnection();
			balpsmt = connection.prepareStatement(benefitInsertQry);
			balpsmt.setString(1, tmpname);
			
			if((requestJSON.getString("campaignFilter")).equalsIgnoreCase("prd_name")){
				balpsmt.setString(2, "Product Name");
				balpsmt.setString(3, requestJSON.getString("productName"));	
			}
			if((requestJSON.getString("campaignFilter")).equalsIgnoreCase("mob_no")){
				balpsmt.setString(2, "Mobile Number");
				balpsmt.setString(3, requestJSON.getString("mobileNumber"));			
			}
			if((requestJSON.getString("campaignFilter")).equalsIgnoreCase("cust_id")){
				balpsmt.setString(2, "Customer ID");
				balpsmt.setString(3, requestJSON.getString("customerid"));
			}
			balpsmt.setString(4, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			
			balpsmt.setString(5, requestJSON.getString("fromdate"));
			balpsmt.setString(6, requestJSON.getString("todate"));
			balpsmt.setString(7, requestJSON.getString("Cmp_Message"));
			int insert=balpsmt.executeUpdate();
			benifitDataMap.put("INSERTED", insert);
			balpsmt.close();
			}
			
			if((requestJSON.getString("msgtype")).equalsIgnoreCase("PUSHNOTIFICATION")){
				
				
				
				benefitInsertQry = "INSERT INTO CAMP_ASSIGN (ID,TEMP_NAME,STATUS,MAKER_ID,MAKERDTTM,SEND_TYPE,FROMTIME,PUSH_FLAG) VALUES('1',?,'A',?,sysdate,'PUSH NOTIFICATION',?,'P')";

			

				connection = DBConnector.getConnection();
				balpsmt = connection.prepareStatement(benefitInsertQry);
				balpsmt.setString(1, tmpname);
				balpsmt.setString(2, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				balpsmt.setString(3, requestJSON.getString("datetime"));
				
				int insert=balpsmt.executeUpdate();
				benifitDataMap.put("INSERTED", insert);
				balpsmt.close();
			}
			
			
			
			balpsmt = connection.prepareStatement("UPDATE CAMP_TEMPLATE_MASTER SET STATUS='A' WHERE TEMPLATE_ID=?");
			balpsmt.setString(1, tmpname);
			balpsmt.executeUpdate();
			
			connection.commit();
			responseDTO.setData(benifitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			
		}
		return responseDTO;
	}
	
	
	public ResponseDTO insertbulkCmpManagement(RequestDTO requestDTO,String tmpname) {
		logger.debug("insertBenifit");
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		JSONObject resultJson = new JSONObject();
		HashMap<String, Object> benifitDataMap = new HashMap<String, Object>();
		PreparedStatement balpsmt = null;
		
		String filename=requestJSON.getString("filename");
		String filepath=requestJSON.getString("filepath");
		String templateName=requestJSON.getString("templateName");
		String datetime=requestJSON.getString("datetime");
		
		System.out.println(filename);
		System.out.println(filepath);
		System.out.println(templateName);
		System.out.println(datetime);

		
		String benefitQry = null;
		String benefitInsertQry = null;
		try {
			
			connection = DBConnector.getConnection();
			//System.out.println("kailash .... ");
			
			
				
				
				String sql = "INSERT INTO camp_assign_customer (TEMP_NAME,USER_ID,FROMTIME,MAKER_ID,MAKERDTTM,PUSH_FLAG,FILE_NAME) VALUES(?,?,?,?,sysdate,'P',?)";
				balpsmt = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getCampData(filepath+"/"+filename)) {
		        	
		        	balpsmt.setString(1, templateName);
					balpsmt.setString(2, map.get("userid"));
					balpsmt.setString(3, datetime);
					balpsmt.setString(4, requestJSON.getString(CevaCommonConstants.MAKER_ID));
					balpsmt.setString(5, filename);
		        	
					balpsmt.addBatch();
					/*System.out.println(map.get("userid"));
					System.out.println(filename);
					System.out.println(filepath);
					System.out.println(templateName);
					System.out.println(datetime);*/
		        }
		        balpsmt.executeBatch();
		        connection.commit();
		        balpsmt.clearBatch();
		        balpsmt.close();
			
			
			
			
			responseDTO.setData(benifitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeConnection(connection);
			
			
		}
		return responseDTO;
	}

	
	public ResponseDTO ModifyCmpManagement(RequestDTO requestDTO,String temmessage,String tempid) {
		logger.debug("insertBenifit");
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		JSONObject resultJson = new JSONObject();
		HashMap<String, Object> benifitDataMap = new HashMap<String, Object>();
		PreparedStatement balpsmt = null;

		ResultSet resultset = null;
		String benefitQry = null;
		try {
			String benefitInsertQry = "UPDATE  CAMP_TEMPLATE_MASTER SET TEMPLATE_ID =?,TEMPLATE=? where TEMPLATE_ID=?";
			connection = DBConnector.getConnection();
			balpsmt = connection.prepareStatement(benefitInsertQry);
			balpsmt.setString(1, tempid);
			balpsmt.setString(2, temmessage);
			balpsmt.setString(3, tempid);
			int insert=balpsmt.executeUpdate();
			benifitDataMap.put("INSERTED", insert);
			connection.commit();
			responseDTO.setData(benifitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(resultset);
		}
		return responseDTO;
	}
	
	public ResponseDTO updateActiveCampDetails(RequestDTO requestDTO) {
		logger.debug("insertBenifit");
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		JSONObject resultJson = new JSONObject();
		HashMap<String, Object> benifitDataMap = new HashMap<String, Object>();
		PreparedStatement balpsmt = null;
		PreparedStatement balpsmt1 = null;

		ResultSet resultset = null;
		String benefitQry = null;
		try {
			String benefitInsertQry = "UPDATE  CAMP_TEMPLATE_MASTER SET STATUS=? where TEMPLATE_ID=?";
			connection = DBConnector.getConnection();
			balpsmt = connection.prepareStatement(benefitInsertQry);
			if((requestJSON.getString("status")).equalsIgnoreCase("Not yet Assign")){
				balpsmt.setString(1, "P");
			}else if((requestJSON.getString("status")).equalsIgnoreCase("Active")){
				balpsmt.setString(1, "B");
				
				balpsmt1 = connection.prepareStatement("UPDATE  CAMP_ASSIGN SET STATUS='B' where TEMP_NAME=? ");
				balpsmt1.setString(1, requestJSON.getString("Template_Name"));
				balpsmt1.executeUpdate();
				balpsmt1.close();
				
			}else{
				balpsmt1 = connection.prepareStatement("UPDATE  CAMP_ASSIGN SET STATUS='A' where TEMP_NAME=? ");
				balpsmt1.setString(1, requestJSON.getString("Template_Name"));
				balpsmt1.executeUpdate();
				balpsmt1.close();
				
				balpsmt.setString(1, "A");
			}
			
			balpsmt.setString(2, requestJSON.getString("Template_Name"));
			
			int insert=balpsmt.executeUpdate();
			benifitDataMap.put("INSERTED", insert);
			connection.commit();
			responseDTO.setData(benifitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(resultset);
		}
		return responseDTO;
	}

	private static String makeALign(String data)
	 {
	  String oData="";
	  if(data !=null)
	  {
		  System.out.println("before -->"+oData);
		  data= data.replaceAll("'", "&apos;");
		  data= data.replaceAll("\"", "&quot;");
		  
		  data= data.replaceAll("\r\n", " ");
		  System.out.println("After -->"+data);
	  }
	  else
	  {
		  data="";
	  }
	  return data;
	 }
	
	private static String makeALign1(String data)
	 {
	  String oData="";
	  if(data !=null)
	  {
		 
		  data= data.replaceAll("\r\n", " ");
	  }
	  else
	  {
		  data="";
	  }
	  return data;
	 }
	
	public ResponseDTO getCampaignInfo(RequestDTO requestDTO) {

		logger.debug("Inside getCanteenInfo DAO ... ");

		HashMap<String, Object> resultMap = null;

		JSONArray information = null;
		JSONObject resultJson = null;
		String CmpQuery = "";


		try {
			resultMap = new HashMap<String, Object>();

			information = new JSONArray();
			resultJson = new JSONObject();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("requestJSON:::"+requestJSON);

			connection = connection == null ? connection = DBConnector.getConnection() : connection;
			CmpQuery = "select  TEMPLATE_ID,TEMPLATE,CREATEDBY,CREATEDDATE,STATUS from CAMP_TEMPLATE_MASTER";

			pstmt = connection.prepareStatement(CmpQuery);
			rs = pstmt.executeQuery();
			JSONObject json = null;
			while (rs.next()) {
				json = new JSONObject();
				json.put("TEMPLATE_ID", makeALign(rs.getString(1)));
				String template=rs.getString(2);
				System.out.println("before->"+template);
				//template=makeALign(template);
				//System.out.println("After->"+template);
				json.put("TEMPLATE", makeALign(rs.getString(2)));
				
				json.put("CREATEDBY", rs.getString(3));
				json.put("CREATEDDATE", rs.getString(4));
				json.put("STATUS", rs.getString(5));
				information.add(json);
			}
			    logger.debug("information"+information);
				resultJson.put("CAMP_INFO", information);
	            resultMap.put("CAMP_INFO", resultJson);
				responseDTO.setData(resultMap);

			} catch (SQLException e) {
				logger.debug("Exception in getStudentInfo ["
						+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (rs != null)
						rs.close();
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return responseDTO;
		}
	public ResponseDTO getassgned(RequestDTO requestDTO) {
		
		logger.debug("Inside getCanteenInfo DAO ... ");
		
		HashMap<String, Object> resultMap = null;
		
		JSONArray information = null;
		JSONObject resultJson = null;
		String CmpQuery = "";
		
		
		try {
			resultMap = new HashMap<String, Object>();
			
			information = new JSONArray();
			resultJson = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("requestJSON:::"+requestJSON);
			
			connection = DBConnector.getConnection();
			CmpQuery = "select CTM.TEMPLATE_ID,CTM.TEMPLATE as Messsage,CA.ASS_TEMP_TYPE,CA.ASS_TEMP_VAL,CA.FROMTIME,CA.TOTIME,CA.MAKER_ID,CA.STATUS,CHANNEL from CAMP_ASSIGN CA,CAMP_TEMPLATE_MASTER CTM where CA.TEMP_NAME=CTM.TEMPLATE_ID";
			
			pstmt = connection.prepareStatement(CmpQuery);
			rs = pstmt.executeQuery();
			JSONObject json = null;
			while (rs.next()) {
				json = new JSONObject();
				json.put("TEMPLATE_ID", rs.getString(1));
				json.put("MESSAGE", rs.getString(2));
				json.put("ASS_TEMP_TYPE", rs.getString(4));
				json.put("ASS_TEMP_VAL", rs.getString(3));
				json.put("FROMTIME", rs.getString(5));
				json.put("TOTIME", rs.getString(6));
				json.put("MAKER_ID", rs.getString(7));
				json.put("STATUS", rs.getString(8));
				json.put("CHANNEL", rs.getString(9));
				information.add(json);
			}
			logger.debug("information"+information);
			resultJson.put("CAMP_INFO", information);
			resultMap.put("CAMP_INFO", resultJson);
			responseDTO.setData(resultMap);
			
		} catch (SQLException e) {
			logger.debug("Exception in getStudentInfo ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return responseDTO;
	}

	
	
}
