package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.DBUtils;

public class AuthorizationAllDAO {

	Logger logger = Logger.getLogger(AuthorizationAllDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	
	
	public ResponseDTO UamChecker(RequestDTO requestDTO) {

		logger.debug("Inside getAuthorizationList.. ");
		HashMap<String, Object> announceMap = null;
		JSONObject resultJson = null;
		JSONArray announceJSONArray = null;

		Connection connection = null;
		PreparedStatement announcePstmt = null;
		ResultSet announceRS = null;

		String query = "";
		String makerid = "";
		String usertype = "";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = new JSONObject();
			resonseJSON = new JSONObject();

			announceMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			announceJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			requestJSON = requestDTO.getRequestJSON();

			//logger.debug("Maker ID >>>>" + requestJSON.getString(CevaCommonConstants.MAKER_ID));
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			usertype=requestJSON.getString("usertype");
				
			//if(usertype.equalsIgnoreCase("BADM")){
				query = "SELECT "
						+ "A.RELATION,A.HEADING_NAMES,A.ACTION_URL,A.AUTH_CODE,"
						+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='P' ),"
						+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='C'),"
						+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='R'),"
						+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='P'  AND  to_char(B.MAKER_DTTM)=to_char(sysdate)),"
						+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='D')  FROM AUTH_MASTER A order by RELATION";

			//}
			
			
			announcePstmt = connection.prepareStatement(query);

			announceRS = announcePstmt.executeQuery();
			announceRS.next();
			String relation = announceRS.getString(1);
			String rel = "";
			JSONObject json = new JSONObject();

			do {

				json = new JSONObject();
				rel = announceRS.getString(1);

				if (relation.equalsIgnoreCase(rel)) {

					json.put("RELATION", announceRS.getString(1));
					json.put("HEADING_NAME", announceRS.getString(2));
					json.put("ACTION_URL", announceRS.getString(3));
					json.put("AUTH_CODE", announceRS.getString(4));
					json.put("PENDING", announceRS.getString(5));
					json.put("CLOSED", announceRS.getString(6));
					json.put("REJECTED", announceRS.getString(7));
					json.put("NEW", announceRS.getString(8));
					json.put("DELETED", announceRS.getString(9));

					announceJSONArray.add(json);

				} else {
					resultJson.put(relation, announceJSONArray);
					relation = announceRS.getString(1);

					json.put("RELATION", announceRS.getString(1));
					json.put("HEADING_NAME", announceRS.getString(2));
					json.put("ACTION_URL", announceRS.getString(3));
					json.put("AUTH_CODE", announceRS.getString(4));
					json.put("PENDING", announceRS.getString(5));
					json.put("CLOSED", announceRS.getString(6));
					json.put("REJECTED", announceRS.getString(7));
					json.put("NEW", announceRS.getString(8));
					json.put("DELETED", announceRS.getString(9));

					announceJSONArray.clear();
					announceJSONArray.add(json);
				}

				json.clear();
				json = null;
			} while (announceRS.next());

			announceJSONArray.add(json);
			resultJson.put(relation, announceJSONArray);

			// resultJson.put("AUTH_PENDING_LIST", announceJSONArray);
			logger.debug("[AuthorizationAllDAO][getAuthorizationList][RESULT JSON : "
					+ resultJson + "]");
			announceMap.put("AUTH_PENDING_LIST", resultJson);

			logger.debug("getAuthorizationList AuthMap [" + announceMap + "]");
			responseDTO.setData(announceMap);
			json = null;
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured.");
			logger.debug("Got SQLException in GetAnnouncementInformation ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured.");
			logger.debug("Got Exception in GetAnnouncementInformation ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (announcePstmt != null)
					announcePstmt.close();
				if (announceRS != null)
					announceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			announceMap = null;
			resultJson = null;
			announceJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO getAuthorizationList(RequestDTO requestDTO) {

		logger.debug("Inside getAuthorizationList.. ");
		HashMap<String, Object> announceMap = null;
		JSONObject resultJson = null;
		JSONArray announceJSONArray = null;

		Connection connection = null;
		PreparedStatement announcePstmt = null;
		ResultSet announceRS = null;

		String query = "";
		String makerid = "";
		String brncode="";
		String actionusr="";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = new JSONObject();
			resonseJSON = new JSONObject();

			announceMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			announceJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			requestJSON = requestDTO.getRequestJSON();

			//logger.debug("Maker ID >>>>" + requestJSON.getString(CevaCommonConstants.MAKER_ID));
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			
			String brnquery=" select SUBSTR(LOCATION,0,INSTR(LOCATION, '-')-1),decode(USER_TYPE,'BDMCK','CHECKER','UDM','CHECKER','SDM','CHECKER',USER_TYPE) from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+makerid+"')";
			announcePstmt = connection.prepareStatement(brnquery);
			announceRS = announcePstmt.executeQuery();
			while(announceRS.next()){
				brncode=announceRS.getString(1);
				actionusr=announceRS.getString(2);
			}
			
			
			query = "SELECT "
					+ "A.RELATION,A.HEADING_NAMES,A.ACTION_URL,A.AUTH_CODE,"
					+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='P' and B.maker_id <> '"+makerid+"' AND B.MAKER_BRCODE='"+brncode+"'),"
					+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='C' and B.maker_id <> '"+makerid+"' AND B.MAKER_BRCODE='"+brncode+"'),"
					+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='R' AND B.MAKER_BRCODE='"+brncode+"'),"
					//+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='R' and B.maker_id = '"+makerid+"'),"
					+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='P' and B.maker_id <> '"+makerid+"' AND B.MAKER_BRCODE='"+brncode+"'  AND B.ACTION='"+actionusr+"' AND  to_char(B.MAKER_DTTM)=to_char(sysdate)),"
					+ "(SELECT COUNT(*) FROM AUTH_PENDING B WHERE B.AUTH_CODE=A.AUTH_CODE AND B.STATUS='D'  AND B.MAKER_BRCODE='"+brncode+"' )  FROM AUTH_MASTER A order by RELATION";

			announcePstmt = connection.prepareStatement(query);

			announceRS = announcePstmt.executeQuery();
			announceRS.next();
			String relation = announceRS.getString(1);
			String rel = "";
			JSONObject json = new JSONObject();

			do {

				json = new JSONObject();
				rel = announceRS.getString(1);

				if (relation.equalsIgnoreCase(rel)) {

					json.put("RELATION", announceRS.getString(1));
					json.put("HEADING_NAME", announceRS.getString(2));
					json.put("ACTION_URL", announceRS.getString(3));
					json.put("AUTH_CODE", announceRS.getString(4));
					json.put("PENDING", announceRS.getString(5));
					json.put("CLOSED", announceRS.getString(6));
					json.put("REJECTED", announceRS.getString(7));
					json.put("NEW", announceRS.getString(8));
					json.put("DELETED", announceRS.getString(9));

					announceJSONArray.add(json);

				} else {
					resultJson.put(relation, announceJSONArray);
					relation = announceRS.getString(1);

					json.put("RELATION", announceRS.getString(1));
					json.put("HEADING_NAME", announceRS.getString(2));
					json.put("ACTION_URL", announceRS.getString(3));
					json.put("AUTH_CODE", announceRS.getString(4));
					json.put("PENDING", announceRS.getString(5));
					json.put("CLOSED", announceRS.getString(6));
					json.put("REJECTED", announceRS.getString(7));
					json.put("NEW", announceRS.getString(8));
					json.put("DELETED", announceRS.getString(9));

					announceJSONArray.clear();
					announceJSONArray.add(json);
				}

				json.clear();
				json = null;
			} while (announceRS.next());

			announceJSONArray.add(json);
			resultJson.put(relation, announceJSONArray);

			// resultJson.put("AUTH_PENDING_LIST", announceJSONArray);
			logger.debug("[AuthorizationAllDAO][getAuthorizationList][RESULT JSON : "
					+ resultJson + "]");
			announceMap.put("AUTH_PENDING_LIST", resultJson);

			logger.debug("getAuthorizationList AuthMap [" + announceMap + "]");
			responseDTO.setData(announceMap);
			json = null;
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured.");
			logger.debug("Got SQLException in GetAnnouncementInformation ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured.");
			logger.debug("Got Exception in GetAnnouncementInformation ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (announcePstmt != null)
					announcePstmt.close();
				if (announceRS != null)
					announceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			announceMap = null;
			resultJson = null;
			announceJSONArray = null;
		}

		return responseDTO;
	}

	
	
	public ResponseDTO CommonAuthListuam(RequestDTO requestDTO) {

		logger.debug("Inside  CommonAuthListuam.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;

		String userQry = "{call AuthPkg.PendingAuthUam(?,?,?,?,?,?,?,?)}";
		String auth_code = "";
		String srchdata = "";
		String srchtria = "";
		String srchval = "";
		String error = "";
		String status = "";
		String MakerID = "";
		String ip = null;

		CallableStatement callable = null;
		ResultSet userRS = null;

		JSONObject json = null;
		JSONArray subJsonArray = null;
		try {
			responseDTO = new ResponseDTO();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			auth_code = requestJSON.getString("auth_code");
			srchdata = requestJSON.getString("srchdata");
			srchtria = requestJSON.getString("srchtria");
			srchval="and "+srchtria+"='"+srchdata+"' ";
			logger.debug("data for search values is +"+srchval);
			status = requestJSON.getString("status");
			MakerID = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			ip=requestJSON.getString(CevaCommonConstants.IP);
			logger.debug("Maker ID>>>>" + MakerID);
			logger.debug("Search Value in DAO is >>>>" + srchval);
			logger.debug("Authorization  " + " auth_code " + auth_code	+ "] status " + status);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1, auth_code);
			callable.setString(2, status);
			callable.registerOutParameter(3, OracleTypes.CURSOR);
			callable.registerOutParameter(4, OracleTypes.VARCHAR);
			callable.setString(5, MakerID);
			callable.setString(6, ip);
			callable.setString(7, srchval);
			callable.setString(8, requestJSON.getString("usertype"));
			//callable.setString(8, srchtria);

			callable.execute();
			logger.debug("Authorization block executed successfully "
					+ "with error_message[" + callable.getString(4) + "]");
			
			error = callable.getString(4);

			if (error.equalsIgnoreCase("SUCCESS")) {
				userRS = (ResultSet) callable.getObject(3);
				subJsonArray = new JSONArray();

				json = new JSONObject();
				if (auth_code.equalsIgnoreCase("USERAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("LOGIN_USER_ID", userRS.getString(2));
						json.put("USER_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DTTM", userRS.getString(5));
						json.put("LOCATION", userRS.getString(6));
						json.put("OFFICE_NAME", userRS.getString(7));
						json.put("AUTH_FLAG", userRS.getString(8));
						json.put("STATUS", userRS.getString(9));
						subJsonArray.add(json);
						json.clear();
					}

				} else if (auth_code.equalsIgnoreCase("MODUSERAUTH")) {
					while (userRS.next()) {

						json.put("REF_NO", userRS.getString(1));
						json.put("LOGIN_USER_ID", userRS.getString(2));
						json.put("USER_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DTTM", userRS.getString(5));
						json.put("LOCATION", userRS.getString(6));
						json.put("OFFICE_NAME", userRS.getString(7));
						json.put("AUTH_FLAG", userRS.getString(8));
						json.put("STATUS", userRS.getString(9));
						subJsonArray.add(json);
						json.clear();

					}
				}  else if (auth_code.equalsIgnoreCase("USERSTATUSAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("User_id", userRS.getString(2));
						json.put("User_Name", userRS.getString(3));
						json.put("Location", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						subJsonArray.add(json);
						json.clear();

					}

				}
				
				else {
					responseDTO.addError(error);
				}

				resonseJSON.put("agentMultiData", subJsonArray);
				resultMap.put(CevaCommonConstants.RESPONSE_JSON, resonseJSON);
				responseDTO.setData(resultMap);
				logger.debug(" CommonAuthListuam resultMap " + resonseJSON);

			}
		} catch (SQLException e) {
			logger.debug("SQLException in CommonAuthListuam [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in CommonAuthListuam 	  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			resultMap = null;
			userQry = null;
			resonseJSON = null;
			json = null;
			subJsonArray = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO CommonAuthList(RequestDTO requestDTO) {

		logger.debug("Inside  CommonAuthList.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;

		String userQry = "{call AuthPkg.PendingAuth(?,?,?,?,?,?,?)}";
		String auth_code = "";
		String srchdata = "";
		String srchtria = "";
		String srchval = "";
		String error = "";
		String status = "";
		String MakerID = "";
		String ip = null;

		CallableStatement callable = null;
		ResultSet userRS = null;

		JSONObject json = null;
		JSONArray subJsonArray = null;
		try {
			responseDTO = new ResponseDTO();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			auth_code = requestJSON.getString("auth_code");
			srchdata = requestJSON.getString("srchdata");
			srchtria = requestJSON.getString("srchtria");
			srchval="and "+srchtria+"='"+srchdata+"' ";
			logger.debug("data for search values is +"+srchval);
			status = requestJSON.getString("status");
			MakerID = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			ip=requestJSON.getString(CevaCommonConstants.IP);
			logger.debug("Maker ID>>>>" + MakerID);
			logger.debug("Search Value in DAO is >>>>" + srchval);
			logger.debug("Authorization444  " + " auth_code " + auth_code	+ "] status " + status);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1, auth_code);
			callable.setString(2, status);
			callable.registerOutParameter(3, OracleTypes.CURSOR);
			callable.registerOutParameter(4, OracleTypes.VARCHAR);
			callable.setString(5, MakerID);
			callable.setString(6, ip);
			callable.setString(7, srchval);
			//callable.setString(8, srchtria);

			callable.execute();
			logger.debug("Authorization block executed successfully "
					+ "with error_message[" + callable.getString(4) + "]");
			
			error = callable.getString(4);

			if (error.equalsIgnoreCase("SUCCESS")) {
				userRS = (ResultSet) callable.getObject(3);
				subJsonArray = new JSONArray();

				json = new JSONObject();
				if (auth_code.equalsIgnoreCase("SERVICEAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("SERVICE_CODE", userRS.getString(2));
						json.put("SERVICE_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DATE", userRS.getString(5));
						json.put("status", userRS.getString(6));

						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("SUBSEAUTH")) {
					while (userRS.next()) {

						json.put("REF_NO", userRS.getString(1));
						json.put("SERVICE_CODE", userRS.getString(2));
						json.put("SERVICE_NAME", userRS.getString(3));
						json.put("SUB_SERVICE_CODE", userRS.getString(4));
						json.put("SUB_SERVICE_NAME", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));

						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("PRCSSAUTH")) {
					while (userRS.next()) {
						json.put(CevaCommonConstants.PROC_CODE,userRS.getString(1));
						json.put(CevaCommonConstants.PROCESS_NAME,userRS.getString(2));
						json.put(CevaCommonConstants.MAKER_ID,userRS.getString(3));
						json.put(CevaCommonConstants.MAKER_DATE,userRS.getString(4));
						json.put(CevaCommonConstants.STATUS,userRS.getString(5));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("BINAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("BIN_CODE", userRS.getString(2));
						json.put("BANK_NAME", userRS.getString(3));
						json.put("BIN", userRS.getString(4));
						json.put("BIN_DESC", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("FEEAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("FEE_CODE", userRS.getString(2));
						json.put("SERVICE_CODE", userRS.getString(3));
						json.put("SUB_SERVICE_CODE", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						json.put("FEENAME", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("USERAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("LOGIN_USER_ID", userRS.getString(2));
						json.put("USER_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DTTM", userRS.getString(5));
						json.put("LOCATION", userRS.getString(6));
						json.put("OFFICE_NAME", userRS.getString(7));
						json.put("AUTH_FLAG", userRS.getString(8));
						json.put("STATUS", userRS.getString(10));
						subJsonArray.add(json);
						json.clear();
					}

				} else if (auth_code.equalsIgnoreCase("MODUSERAUTH")) {
					while (userRS.next()) {

						json.put("REF_NO", userRS.getString(1));
						json.put("LOGIN_USER_ID", userRS.getString(2));
						json.put("USER_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DTTM", userRS.getString(5));
						json.put("LOCATION", userRS.getString(6));
						json.put("OFFICE_NAME", userRS.getString(7));
						json.put("AUTH_FLAG", userRS.getString(8));
						json.put("STATUS", userRS.getString(9));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("MERCHAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("MER_ID", userRS.getString(2));
						json.put("MER_NAME", userRS.getString(3));
					  //json.put("MER_ADM", userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_ID,userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_DATE,userRS.getString(5));
						json.put("STATUS",userRS.getString(6));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("STOREAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("STORE_ID", userRS.getString(2));
						json.put("STORE_NAME", userRS.getString(3));
						json.put("MERCHANT_ID", userRS.getString(4));
						json.put("MERCHANT_NAME", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (auth_code.equalsIgnoreCase("TERMAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("TERMINAL_ID", userRS.getString(2));
						json.put("STORE_ID", userRS.getString(3));
						json.put("MERCHANT_ID", userRS.getString(4));
						json.put("SERIAL_NO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("USERSTATUSAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("User_id", userRS.getString(2));
						json.put("User_Name", userRS.getString(3));
						json.put("Location", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						subJsonArray.add(json);
						json.clear();

					}

				} else if (auth_code.equalsIgnoreCase("TMMAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("old_merchantid", userRS.getString(2));
						json.put("old_storeid", userRS.getString(3));
						json.put("new_merchantid", userRS.getString(4));
						json.put("new_storeid", userRS.getString(5));
						json.put("Terminalid", userRS.getString(6));
						json.put("makerid", userRS.getString(7));
						json.put("maker_date", userRS.getString(8));
						json.put("status", userRS.getString(9));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("MERMODAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("MERCHANT_ID", userRS.getString(2));
						json.put("MANAGER_NAME", userRS.getString(3));
						json.put("EMAIL", userRS.getString(4));
						json.put("MOBILE", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("TERMSTATUSAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("MERCHANT_ID", userRS.getString(2));
						json.put("STORE_ID", userRS.getString(3));
						json.put("TERMINAL_ID", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						subJsonArray.add(json);
						json.clear();

					}
				}  else if (auth_code.equalsIgnoreCase("MERCHSTATUSAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("MERCHANT_ID", userRS.getString(2));
						json.put("MERCHANT_NAME", userRS.getString(3));
						json.put("MAKER_ID", userRS.getString(4));
						json.put("MAKER_DATE", userRS.getString(5));
						json.put("STATUS", userRS.getString(6));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("ASSNTERMAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("TERMINAL_ID", userRS.getString(2));
						json.put("STORE_ID", userRS.getString(3));
						json.put("MERCHANT_ID", userRS.getString(4));
						json.put("SERIAL_NO", userRS.getString(5));
						json.put("TERMINAL_MAKE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("TERMMODAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("TERMINAL_ID", userRS.getString(2));
						json.put("STORE_ID", userRS.getString(3));
						json.put("MERCHANT_ID", userRS.getString(4));
						json.put("SERIAL_NO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DATE", userRS.getString(7));
						json.put("STATUS", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();

					}
				} /*else if (auth_code.equalsIgnoreCase("STOREMODAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("MERCHANT_ID", userRS.getString(2));
						json.put("STORE_ID", userRS.getString(3));
						json.put("MANAGER_NAME", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						subJsonArray.add(json);
						json.clear();

					}
					
				
				}*/
				
				else if (auth_code.equalsIgnoreCase("MERNEWAUTH")) {

					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ACCOUNTNUMBER", userRS.getString(2));
						json.put("ACCOUNTNAME", userRS.getString(3));
						json.put("MANAGERNAME", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(8));
						json.put("MAKER_DTTM", userRS.getString(9));
						json.put("STATUS", userRS.getString(10));
 						subJsonArray.add(json);
						json.clear();
					}
				}else if (auth_code.equalsIgnoreCase("MERSTSAUTH")) {

					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ACCOUNTNUMBER", userRS.getString(2));
						json.put("ACCOUNTNAME", userRS.getString(3));
						json.put("MANAGERNAME", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(8));
						json.put("MAKER_DTTM", userRS.getString(9));
						json.put("STATUS", userRS.getString(10));
 						subJsonArray.add(json);
						json.clear();
					}
				}
				else if (auth_code.equalsIgnoreCase("FEECODEMODAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("FEE_CODE", userRS.getString(2));
						json.put("SERVICE_CODE", userRS.getString(3));
						json.put("SUB_SERVICE_CODE", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(5));
						json.put("MAKER_DATE", userRS.getString(6));
						json.put("STATUS", userRS.getString(7));
						json.put("FEENAME", userRS.getString(8));
						subJsonArray.add(json);
						json.clear();
					}

				} else if (auth_code.equalsIgnoreCase("MPESATYPEAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ID", userRS.getString(2));
						json.put("BILLER_TYPE_NAME", userRS.getString(3));
						json.put("BILLER_TYPE_DESC", userRS.getString(4));
						json.put("BFUB_CRE_ACCT", userRS.getString(5));
						json.put("BFUB_DRE_ACCT", userRS.getString(6));
						json.put("MAKER_ID", userRS.getString(8));
						json.put("MAKER_DTTM", userRS.getString(7));
						json.put("STATUS", userRS.getString(9));
 						subJsonArray.add(json);
						json.clear();

					}
				} else if (auth_code.equalsIgnoreCase("MPESAMODACDEAUTH")) {
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ID", userRS.getString(2));
						json.put("BILLER_TYPE_NAME", userRS.getString(3));
						json.put("BILLER_TYPE_DESC", userRS.getString(4));
						json.put("BFUB_CRE_ACCT", userRS.getString(5));
						json.put("BFUB_DRE_ACCT", userRS.getString(6));
						json.put("MAKER_ID", userRS.getString(8));
						json.put("MAKER_DTTM", userRS.getString(7));
						json.put("STATUS", userRS.getString(9));
 						subJsonArray.add(json);
						json.clear();

					}
				}  else if (auth_code.equalsIgnoreCase("MPESAMODBIDACDEAUTH")) {
					while (userRS.next()) {
						
						json.put("REF_NO", userRS.getString(1));
						//json.put("ID", userRS.getString(2));
						//json.put("BILLER_TYPE_NAME", userRS.getString(3));
						json.put("BILLER_TYPE_DESC", userRS.getString(2));
						json.put("BFUB_CRE_ACCT", userRS.getString(3));
						json.put("BFUB_DRE_ACCT", userRS.getString(4));
						json.put("MAKER_ID", userRS.getString(6));
						json.put("MAKER_DTTM", userRS.getString(5));
						json.put("STATUS", userRS.getString(7));
 						subJsonArray.add(json);
						json.clear();
						
					}
				} else if (auth_code.equalsIgnoreCase("MPESABTSTATUSAUTH")) {
					
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("ID", userRS.getString(2));
							json.put("BILLER_TYPE_NAME", userRS.getString(3));
							json.put("BILLER_TYPE_DESC", userRS.getString(4));
							json.put("BFUB_CRE_ACCT", userRS.getString(5));
							json.put("BFUB_DRE_ACCT", userRS.getString(6));
							json.put("MAKER_ID", userRS.getString(8));
							json.put("MAKER_DTTM", userRS.getString(7));
							json.put("STATUS", userRS.getString(9));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					} 
				else if (auth_code.equalsIgnoreCase("MPESABIDSTATUSAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ID", userRS.getString(2));
						json.put("BILLER_TYPE_NAME", userRS.getString(3));
						json.put("BILLER_TYPE_DESC", userRS.getString(4));
						json.put("BFUB_CRE_ACCT", userRS.getString(5));
						json.put("BFUB_DRE_ACCT", userRS.getString(6));
						json.put("MAKER_ID", userRS.getString(8));
						json.put("MAKER_DTTM", userRS.getString(7));
						json.put("STATUS", userRS.getString(9));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}
				else if (auth_code.equalsIgnoreCase("NEWACCAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WNEWACCAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("AGNTAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("AGNTBLOCK")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("AGNTSTATUSAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("AGNTMODIFY")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("AGNTPRDUPDATE")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("COMMSWREAUT")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("TUPWATAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("REFWATAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("NEWWATAUTH")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENUMBER", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("ACCACTDCT")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ACCNO", userRS.getString(2));
						json.put("ACCNAME", userRS.getString(3));
						json.put("ALIASNAME", userRS.getString(4));
						json.put("BRCODE", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WACCACTDCT")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("ACCNO", userRS.getString(2));
						json.put("ACCNAME", userRS.getString(3));
						json.put("ALIASNAME", userRS.getString(4));
						json.put("BRCODE", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				} else if (auth_code.equalsIgnoreCase("ENLDSBCUST")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WENLDSBCUST")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("ACCTPINRESET")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WACCTPINRESET")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				} else if (auth_code.equalsIgnoreCase("WPASSWORDRESET")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WUSSDENB")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("WMOBILEENB")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("MUSSDENB")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("MMOBILEENB")) {
					
					while (userRS.next()) {
						json.put("REF_NO", userRS.getString(1));
						json.put("CUSTCODE", userRS.getString(2));
						json.put("NAME", userRS.getString(3));
						json.put("NATIONALID", userRS.getString(4));
						json.put("MOBILENO", userRS.getString(5));
						json.put("MAKER_ID", userRS.getString(7));
						json.put("MAKER_DTTM", userRS.getString(6));
						json.put("STATUS", userRS.getString(8));
 						subJsonArray.add(json);
						json.clear();
						
					}
				}else if (auth_code.equalsIgnoreCase("MODCUSTDETAUTH")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTCODE", userRS.getString(2));
							json.put("NAME", userRS.getString(3));
							json.put("NATIONALID", userRS.getString(4));
							json.put("MOBILENO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					}else if (auth_code.equalsIgnoreCase("LMTCUSTDETAUTH")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTCODE", userRS.getString(2));
							json.put("NAME", userRS.getString(3));
							json.put("NATIONALID", userRS.getString(4));
							json.put("MOBILENO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					}else if (auth_code.equalsIgnoreCase("ADDNEWACC")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTCODE", userRS.getString(2));
							json.put("NAME", userRS.getString(3));
							json.put("NATIONALID", userRS.getString(4));
							json.put("MOBILENO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					}else if (auth_code.equalsIgnoreCase("DEVICEPLMNT")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTCODE", userRS.getString(2));
							json.put("NAME", userRS.getString(3));
							json.put("NATIONALID", userRS.getString(4));
							json.put("MOBILENO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					}else if (auth_code.equalsIgnoreCase("WMODCUSTDETAUTH")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTCODE", userRS.getString(2));
							json.put("NAME", userRS.getString(3));
							json.put("NATIONALID", userRS.getString(4));
							json.put("MOBILENO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					}  else if (auth_code.equalsIgnoreCase("MPESAADDBIDAUTH")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							//json.put("ID", userRS.getString(2));
							json.put("BILLER_TYPE_NAME", userRS.getString(2));
							json.put("BILLER_TYPE_DESC", userRS.getString(3));
							json.put("BFUB_CRE_ACCT", userRS.getString(4));
							json.put("BFUB_DRE_ACCT", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					} else if (auth_code.equalsIgnoreCase("PRODUCTCREAUTH")) {
						
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("AccountID", userRS.getString(2));
							json.put("ChannelID", userRS.getString(3));
							json.put("ProductID", userRS.getString(4));
							json.put("CustomerType", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(6));
							json.put("MAKER_DTTM", userRS.getString(7));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
							
						}
					} else if (auth_code.equalsIgnoreCase("MPESAMODBIDSTATUSAUTH")) {
						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
						  //json.put("ID", userRS.getString(2));
							json.put("BILLER_TYPE_NAME", userRS.getString(2));
							json.put("BILLER_TYPE_DESC", userRS.getString(3));
							json.put("BFUB_CRE_ACCT", userRS.getString(4));
							json.put("BFUB_DRE_ACCT", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(6));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
						}
					}
					else if (auth_code.equalsIgnoreCase("DELACCAUTH")) {
				          while (userRS.next())
				          {
				            json.put("REF_NO", userRS.getString(1));
				            json.put("CUSTCODE", userRS.getString(2));
				            json.put("NAME", userRS.getString(3));
				            json.put("ACC_NO", userRS.getString(4));
				            json.put("MAKER_ID", userRS.getString(5));
				            json.put("MAKER_DTTM", userRS.getString(6));
				            json.put("STATUS", userRS.getString(7));
				            subJsonArray.add(json);
				            json.clear();
				          }
				        }
					else if (auth_code.equalsIgnoreCase("WDELACCAUTH")) {
				          while (userRS.next())
				          {
				            json.put("REF_NO", userRS.getString(1));
				            json.put("CUSTCODE", userRS.getString(2));
				            json.put("NAME", userRS.getString(3));
				            json.put("ACC_NO", userRS.getString(4));
				            json.put("MAKER_ID", userRS.getString(5));
				            json.put("MAKER_DTTM", userRS.getString(6));
				            json.put("STATUS", userRS.getString(7));
				            subJsonArray.add(json);
				            json.clear();
				          }
				        }
					else if (auth_code.equalsIgnoreCase("BULKREGAUTH")) {
						while (userRS.next())
						{
							json.put("REF_NO", userRS.getString(1));
							json.put("FILENAME", userRS.getString(2));
							/*json.put("NAME", userRS.getString(3));
							json.put("ACC_NO", userRS.getString(4));*/
							json.put("MAKER_ID", userRS.getString(3));
							json.put("MAKER_DTTM", userRS.getString(4));
							json.put("STATUS", userRS.getString(5));
							subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("SUPERAGENTAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("ACCOUNTNUMBER", userRS.getString(2));
							json.put("ACCOUNTNAME", userRS.getString(3));
							json.put("MANAGERNAME", userRS.getString(8));
							json.put("MAKER_ID", userRS.getString(11));
							json.put("MAKER_DTTM", userRS.getString(12));
							json.put("STATUS", userRS.getString(13));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("SUPERAGENTSTATUSAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("ACCOUNTNUMBER", userRS.getString(2));
							json.put("ACCOUNTNAME", userRS.getString(3));
							json.put("MANAGERNAME", userRS.getString(8));
							json.put("MAKER_ID", userRS.getString(11));
							json.put("MAKER_DTTM", userRS.getString(12));
							json.put("STATUS", userRS.getString(13));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("POSAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("FIRST_NAME", userRS.getString(3));
							json.put("MOBILE_NUMBER", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("POSMODIFYAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("FIRST_NAME", userRS.getString(3));
							json.put("MOBILE_NUMBER", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("POSSTATUSAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("FIRST_NAME", userRS.getString(3));
							json.put("MOBILE_NUMBER", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("STOREAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("MOBILE_NUMBER", userRS.getString(3));
							json.put("STORE_ID", userRS.getString(4));
							json.put("STORE_NAME", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(6));
							json.put("MAKER_DTTM", userRS.getString(7));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("STOREMODAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("MOBILE_NUMBER", userRS.getString(3));
							json.put("STORE_ID", userRS.getString(4));
							json.put("STORE_NAME", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(6));
							json.put("MAKER_DTTM", userRS.getString(7));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("STORESTATUSAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("CUSTOMER_CODE", userRS.getString(2));
							json.put("MOBILE_NUMBER", userRS.getString(3));
							json.put("STORE_ID", userRS.getString(4));
							json.put("STORE_NAME", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(6));
							json.put("MAKER_DTTM", userRS.getString(7));
							json.put("STATUS", userRS.getString(8));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("TERMINALAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("TERMINAL_ID", userRS.getString(2));
							json.put("TERMINAL_MAKE", userRS.getString(3));
							json.put("MODEL_NO", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("TERMODIFYAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("TERMINAL_ID", userRS.getString(2));
							json.put("TERMINAL_MAKE", userRS.getString(3));
							json.put("MODEL_NO", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}else if (auth_code.equalsIgnoreCase("TERSTATUSAUTH")) {

						while (userRS.next()) {
							json.put("REF_NO", userRS.getString(1));
							json.put("TERMINAL_ID", userRS.getString(2));
							json.put("TERMINAL_MAKE", userRS.getString(3));
							json.put("MODEL_NO", userRS.getString(4));
							json.put("SERIAL_NO", userRS.getString(5));							
							json.put("MAKER_ID", userRS.getString(7));
							json.put("MAKER_DTTM", userRS.getString(8));
							json.put("STATUS", userRS.getString(6));
	 						subJsonArray.add(json);
							json.clear();
						}
					}
				
				
				else {
					responseDTO.addError(error);
				}

				resonseJSON.put("agentMultiData", subJsonArray);
				resultMap.put(CevaCommonConstants.RESPONSE_JSON, resonseJSON);
				responseDTO.setData(resultMap);
				logger.debug(" CommonAuthList resultMap " + resonseJSON);

			}
		} catch (SQLException e) {
			logger.debug("SQLException in CommonAuthList [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in CommonAuthList 	  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			resultMap = null;
			userQry = null;
			resonseJSON = null;
			json = null;
			subJsonArray = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO CommonAuthSearch(RequestDTO requestDTO) {

		logger.debug("Inside AuthorizationAllDAO CommonAuthCnfsubmition.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;
		String auth_code = "";
		String status = "";

		PreparedStatement detPstmt = null;
		ResultSet userRS = null;
		JSONObject json = null;
		JSONArray subJsonArray = null;
		
		String userQry = "select 1 from dual";
		try {
			responseDTO = new ResponseDTO();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;
			
			subJsonArray = new JSONArray();

			json = new JSONObject();
			
			auth_code = requestJSON.getString("auth_code");
			status = requestJSON.getString("status");
			connection = DBConnector.getConnection();
			detPstmt = connection.prepareStatement(userQry);
			
			userRS = detPstmt.executeQuery();
			
			json.put("AUTH_CODE",auth_code );
			json.put("STATUS",status );
			
			subJsonArray.add(json);
			
			resonseJSON.put("agentMultiData", subJsonArray);
			resultMap.put(CevaCommonConstants.RESPONSE_JSON, resonseJSON);
			responseDTO.setData(resultMap);
			logger.debug(" AuthorizationAllDAO CommonAuthCnfsubmition  resultMap "
					+ responseDTO);
			

		} catch (SQLException e) {
			logger.debug("SQLException in CommonAuthList [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();

		} catch (Exception e) {
			logger.debug("Exception in CommonAuthList 	  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			//DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(detPstmt);
			resultMap = null;

			resonseJSON = null;
			json = null;
			subJsonArray = null;
		}
		return responseDTO;
	}

	public ResponseDTO CommonAuthConfirmationBefore(RequestDTO requestDTO) {

		logger.debug("Inside  CommonAuthConfirmationBefore.. ");
		Connection connection = null;
		AuthFetchSubDAO authsubDTO = new AuthFetchSubDAO();

		String auth_code = "";
		String status = "";
		String ref_no = "";

		CallableStatement callable = null;

		try {
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			auth_code = requestJSON.getString("auth_code");
			status = requestJSON.getString("status");
			ref_no = requestJSON.getString("ref_no");

			logger.debug("Authorization  " + " auth_code789 " + auth_code+ "] status " + status + "] refno " + ref_no);

			connection = DBConnector.getConnection();

			if (auth_code.equalsIgnoreCase("USERAUTH")) {

				responseDTO = authsubDTO.UserAuthRecordData(requestDTO);

			} else if (auth_code.equalsIgnoreCase("MODUSERAUTH")) {

				responseDTO = authsubDTO.UserAuthRecordData(requestDTO);

			} else if (auth_code.equalsIgnoreCase("SERVICEAUTH")&& (status.equalsIgnoreCase("R"))) {
				responseDTO = authsubDTO.getNextServiceCode(requestDTO);
			} else if (auth_code.equalsIgnoreCase("SERVICEAUTH")) {
				responseDTO = authsubDTO.ServiceAuthRecordData(requestDTO);
			} else if (auth_code.equalsIgnoreCase("BINAUTH")) {
				responseDTO = authsubDTO.binAuthRecordData(requestDTO);
			} else if (auth_code.equalsIgnoreCase("SUBSEAUTH")
					&& (status.equalsIgnoreCase("R"))) {
				responseDTO = authsubDTO.subServiceAuthRejectRecordData(requestDTO);
			} else if (auth_code.equalsIgnoreCase("SUBSEAUTH")) {
				responseDTO = authsubDTO.subServiceAuthRecordData(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("FEEAUTH")
					&& (status.equalsIgnoreCase("R"))) {
				responseDTO = authsubDTO.FeecodeRejectRecordModify(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("FEEAUTH")) {
				responseDTO = authsubDTO.viewFeeDetails(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("TERMAUTH")) {
				responseDTO = authsubDTO.viewTerminalDetails(requestDTO);
			} /*else if (auth_code.equalsIgnoreCase("STOREAUTH")) {
				responseDTO = authsubDTO.viewStoreDetails(requestDTO);
			} */ else if (auth_code.equalsIgnoreCase("USERSTATUSAUTH")) {
				responseDTO = authsubDTO
						.getUserActiveDeactiveDetails(requestDTO);
			} else if (auth_code.equalsIgnoreCase("TMMAUTH")) {
				responseDTO = authsubDTO.TerminalMigrationDetails(requestDTO);
			} else if (auth_code.equalsIgnoreCase("MERMODAUTH")) {
				responseDTO = authsubDTO.MerchantModifyDetails(requestDTO);
			} else if (auth_code.equalsIgnoreCase("TERMSTATUSAUTH")) {
				responseDTO = authsubDTO.TerminalstatusDetails(requestDTO);
			}

			/*else if (auth_code.equalsIgnoreCase("STORESTATUSAUTH")) {
				responseDTO = authsubDTO.StoreStatusauthdetails(requestDTO);
			}*/

			else if (auth_code.equalsIgnoreCase("MERCHSTATUSAUTH")) {
				responseDTO = authsubDTO.MerchantStatusAuth(requestDTO);
			}

			else if (auth_code.equalsIgnoreCase("ASSNTERMAUTH")) {
				responseDTO = authsubDTO.AssignTerminalServiceauth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("TERMMODAUTH")) {
				responseDTO = authsubDTO.TerminalModificationAuth(requestDTO);
			}

			/*else if (auth_code.equalsIgnoreCase("STOREMODAUTH")) {
				responseDTO = authsubDTO.StoreModifyDetailsauth(requestDTO);
			}*/

			else if (auth_code.equalsIgnoreCase("MPESATYPEAUTH")) {
				responseDTO = authsubDTO.billerTypeAuth(requestDTO);
			}
			
			else if (auth_code.equalsIgnoreCase("MPESAMODACDEAUTH")) {
				responseDTO = authsubDTO.billerTypeActiveDeactiveAuth(requestDTO);
			}
			
			else if (auth_code.equalsIgnoreCase("MPESAMODBIDACDEAUTH")) {
				responseDTO = authsubDTO.billerIDActiveDeactiveAuth(requestDTO);
			}
			
			else if (auth_code.equalsIgnoreCase("MPESABTSTATUSAUTH")) {
				responseDTO = authsubDTO.billerTypeStatusAuth(requestDTO);
			}
			
			
			else if (auth_code.equalsIgnoreCase("MPESABIDSTATUSAUTH")) {
				responseDTO = authsubDTO.billerIDStatusAuth(requestDTO);
			}

			else if (auth_code.equalsIgnoreCase("FEECODEMODAUTH")) {
				responseDTO = authsubDTO.modifyFeeDetails(requestDTO);
			}
			
			else if (auth_code.equalsIgnoreCase("NEWACCAUTH")) {
				responseDTO = authsubDTO.AccountRegAuthData(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("WNEWACCAUTH")) {
				responseDTO = authsubDTO.WalletAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("AGNTAUTH")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("AGNTBLOCK")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("AGNTMODIFY")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("AGNTPRDUPDATE")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("AGNTSTATUSAUTH")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}else if (auth_code.equalsIgnoreCase("TUPWATAUTH")) {
				responseDTO = authsubDTO.WalletAccountRegAuthData(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("ACCACTDCT")) {
				responseDTO = authsubDTO.AccountactdeactAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("WACCACTDCT")) {
				responseDTO = authsubDTO.WalletAccountactdeactAuth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("ACCTPINRESET")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("WACCTPINRESET")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("WPASSWORDRESET")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("WUSSDENB")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("WMOBILEENB")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("MUSSDENB")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("MMOBILEENB")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("MODCUSTDETAUTH")) {
				responseDTO = authsubDTO.ModCustDetails(requestDTO);
			} else if (auth_code.equalsIgnoreCase("WMODCUSTDETAUTH")) {
				responseDTO = authsubDTO.WalletModCustDetails(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("DELACCAUTH")) {
				responseDTO = authsubDTO.deleteAccounts(requestDTO);
			} else if (auth_code.equalsIgnoreCase("WDELACCAUTH")) {
				responseDTO = authsubDTO.deleteAccounts(requestDTO);
			} else if (auth_code.equalsIgnoreCase("ENLDSBCUST")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("WENLDSBCUST")) {
				responseDTO = authsubDTO.PinResetAuth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("MPESAADDBIDAUTH")) { 
				responseDTO = authsubDTO.addBillerIDAuth(requestDTO);
		    }else if (auth_code.equalsIgnoreCase("PRODUCTCREAUTH")) { 
				responseDTO = authsubDTO.createProductAuth(requestDTO);
			} else if (auth_code.equalsIgnoreCase("MPESAMODBIDSTATUSAUTH")) {
				responseDTO = authsubDTO.billerIDStatusAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("BULKREGAUTH")) {
				responseDTO = authsubDTO.bulkRegiAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("MERNEWAUTH")) {
				responseDTO = authsubDTO.superagentAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("MERSTSAUTH")) {
				responseDTO = authsubDTO.superagentAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("ADDNEWACC")) {
				responseDTO = authsubDTO.addnewAccounts(requestDTO);
			}else if (auth_code.equalsIgnoreCase("DEVICEPLMNT")) {
				responseDTO = authsubDTO.devicereplace(requestDTO);
			}else if (auth_code.equalsIgnoreCase("LMTCUSTDETAUTH")) {
				responseDTO = authsubDTO.LimitCustDetails(requestDTO);
			}else if (auth_code.equalsIgnoreCase("SUPERAGENTAUTH")) {
				responseDTO = authsubDTO.newsuperagentAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("SUPERAGENTSTATUSAUTH")) {
				responseDTO = authsubDTO.newsuperagentAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("POSAUTH")) {
				responseDTO = authsubDTO.POSRegAuth(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("POSMODIFYAUTH")) {
				responseDTO = authsubDTO.POSRegAuth(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("POSSTATUSAUTH")) {
				responseDTO = authsubDTO.POSRegAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("STOREAUTH")) {
				responseDTO = authsubDTO.StoreRegAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("STOREMODAUTH")) {
				responseDTO = authsubDTO.StoreRegAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("STORESTATUSAUTH")) {
				responseDTO = authsubDTO.StoreRegAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("TERMINALAUTH")) {
				responseDTO = authsubDTO.TerminalDetailsAuth(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("TERMODIFYAUTH")) {
				responseDTO = authsubDTO.TerminalDetailsAuth(requestDTO);
			}
			else if (auth_code.equalsIgnoreCase("TERSTATUSAUTH")) {
				responseDTO = authsubDTO.TerminalDetailsAuth(requestDTO);
			}else if (auth_code.equalsIgnoreCase("COMMSWREAUT")) {
				responseDTO = authsubDTO.WalletAgentAccountRegAuthData(requestDTO);
			}
			
			

			logger.debug(" CommonAuthConfirmationBefore  resultMap "
					+ responseDTO);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				resonseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
				logger.debug("Response JSON CommonAuthConfirmationBefore ["
						+ resonseJSON + "]");

			}

		} catch (SQLException e) {
			logger.debug("SQLException in CommonAuthList [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();

		} catch (Exception e) {
			logger.debug("Exception in CommonAuthList 	  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

			resonseJSON = null;
		}
		return responseDTO;
	}

	public ResponseDTO CommonAuthCnfsubmition(RequestDTO requestDTO) {

		logger.debug("Inside AuthorizationAllDAO CommonAuthCnfsubmition.. ");
		Connection connection = null;

		String auth_code = "";
		String status = "";
		String ref_no = "";
		String decs = "";
		String error = "";
		String makerId = "";
		String remark = "";

		CallableStatement callable = null;
		
		String userQry = "{call AuthPkg.RecordConfirmation(?,?,?,?,?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			auth_code = requestJSON.getString("auth_code");
			status = requestJSON.getString("status");
			ref_no = requestJSON.getString("ref_no");
			decs = requestJSON.getString("decs");
			remark = requestJSON.getString("remark");
			
			logger.debug(requestJSON.getString("remark"));
			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			logger.debug("Authorization  " + " auth_code" + auth_code
					+ "] status " + status + "] refno " + ref_no + " decs "
					+ decs + "Remote ip:"
					+ requestJSON.getString(CevaCommonConstants.IP));

			connection = DBConnector.getConnection();
			String serviceval=serviceCall(auth_code,ref_no,connection,makerId,decs);
			//System.out.println("kailash ::: "+serviceval+" ::: ");
			if(serviceval.equalsIgnoreCase("SUCCESS")){
				
			

			callable = connection.prepareCall(userQry);
			callable.setString(1, auth_code);
			callable.setString(2, status);
			callable.setString(3, decs);
			callable.setString(4, ref_no);
			callable.setString(5, makerId);
			callable.setString(6, remark);
			callable.registerOutParameter(7, OracleTypes.CURSOR);
			callable.registerOutParameter(8, OracleTypes.VARCHAR);
			callable.setString(9, requestJSON.getString(CevaCommonConstants.IP));

			callable.execute();
			logger.debug("Authorization block executed successfully "
					+ "with error_message[" + callable.getString(8) + "]");
			error = callable.getString(8);

			if (!error.equalsIgnoreCase("SUCCESS")) {

				responseDTO.addError(error);
			}
			
			}else{
				responseDTO.addError(serviceval);	
			}

			logger.debug(" AuthorizationAllDAO CommonAuthCnfsubmition  resultMap "
					+ responseDTO);

		} catch (SQLException e) {
			logger.debug("SQLException in CommonAuthList [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();

		} catch (Exception e) {
			logger.debug("Exception in CommonAuthList 	  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

			resonseJSON = null;
		}
		return responseDTO;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	
	
	public String serviceCall(String transcode,String refno,Connection con,String Makerid,String decision){
		String rval="SUCCESS";
		PreparedStatement servicePstmt = null;
		PreparedStatement pstmt = null;
		ResultSet serviceRS = null;
		
		String query="";
		try{
			
		if(decision.equalsIgnoreCase("A")){
			
			if(transcode.equalsIgnoreCase("AGNTAUTH")){
				query="SELECT ACI.MOBILE_NUMBER,ACM.CUSTOMER_CODE,ACM.FIRST_NAME,WADT.ACCT_NO FROM AGENT_CUSTOMER_MASTER_TEMP ACM,AGENT_CONTACT_INFO_TEMP ACI,WALLET_ACCT_DATA_TEMP WADT WHERE ACM.ID=ACI.CUST_ID AND WADT.CUST_ID=ACI.CUST_ID  AND ACM.REF_NUM='"+refno+"'";
				
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				CommonServiceDao csd=new CommonServiceDao();
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				
				if(serviceRS.next())
				{
					ServiceRequestClient.sms(serviceRS.getString(1), "Dear "+serviceRS.getString(3)+", you are now a UnionDirect Agent. Your wallet I.D. is  "+serviceRS.getString(4)+". Dial 826*008# to activate, or download the UnionDirect App.");
				System.out.println("Dear "+serviceRS.getString(3)+", welcome to Union Bank agent platform. Your wallet Account No. is "+serviceRS.getString(4)+". Dial 826*008# to activate your wallet account.");
				}
				
			}
			/* Active-Deactive Account */
			if(transcode.equalsIgnoreCase("ACCACTDCT")){
				query="select MADT.ACCT_NO,MADT.ACCT_STATUS,MCM.USER_ID from MOB_ACCT_DATA_TEMP MADT,MOB_CUSTOMER_MASTER MCM where MCM.ID=MADT.CUST_ID AND MADT.REF_NUM='"+refno+"'";
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				
				if(serviceRS.next())
				{
				
					if((serviceRS.getString(2)).equalsIgnoreCase("A")){
						JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.ModifyAccount(serviceRS.getString(1),serviceRS.getString(3),"MOBILE","ACT",Makerid));
						if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
							rval=jsonaut.getString("respdesc");
						}else{
							rval=jsonaut.getString("respdesc");
						}
					}else{
						JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.ModifyAccount(serviceRS.getString(1),serviceRS.getString(3),"MOBILE","DEACT",Makerid));
						if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
							rval=jsonaut.getString("respdesc");
						}else{
							rval=jsonaut.getString("respdesc");
						}
					}
				}
				servicePstmt.close();
				serviceRS.close();
			}
			
			/* Delete Accounts */
			if(transcode.equalsIgnoreCase("DELACCAUTH")){
				query="select MADT.ACCT_NO,MADT.ACCT_STATUS,MCM.USER_ID from MOB_ACCT_DATA_TEMP MADT,MOB_CUSTOMER_MASTER MCM where MCM.ID=MADT.CUST_ID AND MADT.REF_NUM='"+refno+"'";
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				
				if(serviceRS.next())
				{
				
					JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.ModifyAccount(serviceRS.getString(1),serviceRS.getString(3),"MOBILE","DEL",Makerid));
					if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
						rval=jsonaut.getString("respdesc");
					}else{
						rval=jsonaut.getString("respdesc");
					}
				}
				servicePstmt.close();
				serviceRS.close();
			}
			
			/* Add New Account Authorization */
			if(transcode.equalsIgnoreCase("ADDNEWACC")){
				
				query="select MADT.ACCT_NO,MADT.ACCT_STATUS,MCM.USER_ID from MOB_ACCT_DATA_TEMP MADT,MOB_CUSTOMER_MASTER MCM where MCM.ID=MADT.CUST_ID AND MADT.REF_NUM='"+refno+"'";
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				
				if(serviceRS.next())
				{
				
					JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.AddAccount(serviceRS.getString(1),serviceRS.getString(3),"MOBILE"));
					if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
						rval=jsonaut.getString("respdesc");
					}else{
						rval=jsonaut.getString("respdesc");
					}
				
				}
				servicePstmt.close();
				serviceRS.close();
			}
			
			/* Pin Reset */
			if(transcode.equalsIgnoreCase("ACCTPINRESET")){
				query="SELECT MCI.MOBILE_NUMBER,MCM.CUSTOMER_CODE,MCM.FIRST_NAME FROM MOB_CUSTOMER_MASTER_TEMP MCM,MOB_CONTACT_INFO MCI WHERE MCM.ID=MCI.CUST_ID AND MCI.APP_TYPE='MOBILE' AND MCM.REF_NUM='"+refno+"'";
				
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				CommonServiceDao csd=new CommonServiceDao();
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				
				if(serviceRS.next())
				{
					pstmt = con.prepareStatement("UPDATE MOB_CUSTOMER_MASTER SET TXN_PIN=? WHERE CUSTOMER_CODE=?");
					pstmt.setString(1, resp.getString("pinHash"));
					pstmt.setString(2, serviceRS.getString(2));
					
					pstmt.executeUpdate();
					con.commit();
					pstmt.close();
					ServiceRequestClient.sms(serviceRS.getString(1), "Dear "+serviceRS.getString(3)+", Please find the Mobile Banking  Transaction Pin "+resp.getString("pin"));
					rval="SUCCESS";
				}
			}
			
			if(transcode.equalsIgnoreCase("WACCTPINRESET")){
				query="SELECT MCI.MOBILE_NUMBER,MCM.CUSTOMER_CODE,MCM.FIRST_NAME FROM MOB_CUSTOMER_MASTER_TEMP MCM,MOB_CONTACT_INFO MCI WHERE MCM.ID=MCI.CUST_ID AND  MCM.REF_NUM='"+refno+"'";
				/*MCM.W_APP_TYPE='WALLET' AND*/
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				CommonServiceDao csd=new CommonServiceDao();
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				
				if(serviceRS.next())
				{
					pstmt = con.prepareStatement("UPDATE MOB_CUSTOMER_MASTER SET TXN_PIN=? WHERE CUSTOMER_CODE=?");
					pstmt.setString(1, resp.getString("pinHash"));
					pstmt.setString(2, serviceRS.getString(2));
					
					pstmt.executeUpdate();
					con.commit();
					pstmt.close();
					ServiceRequestClient.sms(serviceRS.getString(1), "Dear "+serviceRS.getString(3)+", Please find the Wallet Account Transaction Pin "+resp.getString("pin"));
					rval="SUCCESS";
				}
			}
			
			if(transcode.equalsIgnoreCase("WPASSWORDRESET")){
				query="SELECT MCI.MOBILE_NUMBER,MCM.CUSTOMER_CODE,MCM.FIRST_NAME FROM MOB_CUSTOMER_MASTER_TEMP MCM,MOB_CONTACT_INFO MCI WHERE MCM.ID=MCI.CUST_ID AND  MCM.REF_NUM='"+refno+"'";
				/*MCM.W_APP_TYPE='WALLET' AND*/
				servicePstmt = con.prepareStatement(query);
				serviceRS = servicePstmt.executeQuery();
				CommonServiceDao csd=new CommonServiceDao();
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				
				if(serviceRS.next())
				{
					pstmt = con.prepareStatement("update WALLET_CUSTOMER_LOGIN set USER_PASSWORD=?,PASSWORD_STATUS=1 where USER_ID in ( select USER_ID  from MOB_CUSTOMER_MASTER where customer_code=?)");
					pstmt.setString(1, resp.getString("pinHash"));
					pstmt.setString(2, serviceRS.getString(2));
					
					pstmt.executeUpdate();
					con.commit();
					ServiceRequestClient.sms(serviceRS.getString(1), "Dear "+serviceRS.getString(3)+", Password Reset successful ,Please find the New Password  "+resp.getString("pin"));
					rval="SUCCESS";
				}
			}
			
		}
		}catch(Exception e){
			rval="FAIL";
			e.printStackTrace();
		}finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closePreparedStatement(servicePstmt);
			DBUtils.closeResultSet(serviceRS);
		}
		
		return rval;
	}

}
