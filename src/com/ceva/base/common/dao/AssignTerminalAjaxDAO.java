package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class AssignTerminalAjaxDAO {

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;

	private Logger logger = Logger.getLogger(AssignTerminalAjaxDAO.class);
	
	public ResponseDTO getStores(RequestDTO requestDTO) {
		logger.debug("Inside GetStores DAO.. ");
		String storeQry = "";

		HashMap<String, Object> resultMap = null;
		JSONArray storeJSONArray = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			storeJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			storeQry = "SELECT STORE_ID,STORE_NAME from STORE_MASTER  where MERCHANT_ID=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,requestJSON.getString("MERCHANT_ID"));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put("key", storeRS.getString(2));
				json.put("val", storeRS.getString(1));
				storeJSONArray.add(json);
			}
			
			resonseJSON.put("STORE_LIST", storeJSONArray);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put("STORE_LIST", resonseJSON);
			responseDTO.setData(resultMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in  GetStores DAO [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in  GetStores DAO [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(storePstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(storeRS);

			storeQry = null;
			resultMap = null;
			storeJSONArray = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO getTerminal(RequestDTO requestDTO) {
		logger.debug("Inside Terminal DAO.. ");
		String storeQry = "";

		HashMap<String, Object> resultMap = null;
		JSONArray storeJSONArray = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			storeJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			storeQry = "SELECT TERMINAL_ID FROM TERMINAL_MASTER where STORE_ID=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,requestJSON.getString("STORE_ID"));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put("key", storeRS.getString(1));
				json.put("val", storeRS.getString(1));
				storeJSONArray.add(json);
			}
			
			resonseJSON.put("TERMINAL_LIST", storeJSONArray);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put("TERMINAL_LIST", resonseJSON);
			responseDTO.setData(resultMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in  GetStores DAO [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in  GetStores DAO [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(storePstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(storeRS);

			storeQry = null;
			resultMap = null;
			storeJSONArray = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO getUsers(RequestDTO requestDTO) {
		logger.debug("Inside Users  DAO.. ");
		String storeQry = "";

		HashMap<String, Object> resultMap = null;
		JSONArray storeJSONArray = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			storeJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			storeQry = "SELECT TERMINAL_ID,TERMINAL_MAKE FROM TERMINAL_MASTER where TERMINAL_ID=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,requestJSON.getString("TERMINAL_ID"));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put("key", storeRS.getString(2));
				json.put("val", storeRS.getString(1));
				storeJSONArray.add(json);
			}
			
			resonseJSON.put("USERS_LIST", storeJSONArray);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put("USERS_LIST", resonseJSON);
			responseDTO.setData(resultMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in  USERS DAO [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in  USERS DAO [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(storePstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(storeRS);

			storeQry = null;
			resultMap = null;
			storeJSONArray = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO getDetails(RequestDTO requestDTO) {

		logger.debug("inside GetDetails.. ");

		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		//responseJSON = new JSONObject();

		HashMap<String, Object> bankdataMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray merchantJSONArray = null;

		PreparedStatement Pstmt = null;
		ResultSet merchantfeeRS = null;

		Connection connection = null;

		String bankdet = " SELECT distinct a.MERCHANT_ID,a.MAKERID,a.MAKERDTTM,a.TXN_TYPE,a.FEE_CODE,b.FEE_NAME FROM MERCHANT_FEE a,FEE_MASTER b WHERE a.MERCHANT_ID= ? " 
				+" AND a.FEE_CODE=b.FEE_CODE and a.txn_type=b.SERVICE_TXN_CODE ";

		try {
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			bankdataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			Pstmt = connection.prepareStatement(bankdet);
			Pstmt.setString(1, requestJSON.getString("mrcode"));
			merchantfeeRS = Pstmt.executeQuery();

			merchantJSONArray = new JSONArray();

			while (merchantfeeRS.next()) {
				json = new JSONObject();
				json.put("merchantID", merchantfeeRS.getString(1));
				json.put("makerID", merchantfeeRS.getString(2));
				json.put("makerDttm", merchantfeeRS.getString(3));
				json.put("txnType", merchantfeeRS.getString(4));
				json.put("feeCode", merchantfeeRS.getString(5));
				json.put("feename", merchantfeeRS.getString(6));
				merchantJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("merchantJSONArray [" + merchantJSONArray + "]");

			resultJson.put("MERCHANTfEEDETAILS", merchantJSONArray);
			bankdataMap.put("MERCHANTfEEDETAILS", resultJson);
			responseDTO.setData(bankdataMap);
			logger.debug("ResponseDTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetDetails [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("SQLException in GetDetails [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (Pstmt != null)
					Pstmt.close();
				if (connection != null)
					connection.close();
				if (merchantfeeRS != null) {
					merchantfeeRS.close();
				}
			} catch (SQLException se) {

			}

			bankdataMap = null;

			resultJson = null;
			json = null;

			merchantJSONArray = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO getTerminalDetails(RequestDTO requestDTO) {
		logger.debug("Inside Terminal De  Details.. ");
		String storeQry = "";

		HashMap<String, Object> resultMap = null;
		JSONArray storeJSONArray = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			storeJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			storeQry = "SELECT MERCHANT_ID,STORE_ID,TERMINAL_ID,TERMINAL_MAKE,MAKER_ID,MAKER_DTTM FROM TERMINAL_MASTER where TERMINAL_ID=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,requestJSON.getString("TERMINAL_ID"));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put("merchantID", storeRS.getString(1));
				json.put("storeID", storeRS.getString(2));
				json.put("terminalID", storeRS.getString(3));
				json.put("termianlMake", storeRS.getString(4));
				json.put("makerid", storeRS.getString(5));
				json.put("makerdatetime", storeRS.getString(6));
				storeJSONArray.add(json);
			}
			
			resonseJSON.put("TERMINAL_DETAILS", storeJSONArray);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put("TERMINAL_DETAILS", resonseJSON);
			responseDTO.setData(resultMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in  Terminal Details DAO [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in  USERS DAO [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(storePstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(storeRS);

			storeQry = null;
			resultMap = null;
			storeJSONArray = null;
		}

		return responseDTO;
	}
}
