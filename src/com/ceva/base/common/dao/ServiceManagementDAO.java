package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class ServiceManagementDAO {

	private Logger logger = Logger.getLogger(ServiceManagementDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	public ResponseDTO getBankDetails(RequestDTO requestDTO) {
		logger.debug("Inside  GetBankDetails.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray serviceJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			serviceJSONArray = new JSONArray();

			// String
			// serviceQry="Select distinct STATUS||'-'||KEY_VALUE,STATUS from CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=? order by STATUS";
			String serviceQry = "Select distinct BANK_CODE,BANK_NAME from NBIN";

			servicePstmt = connection.prepareStatement(serviceQry);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			while (serviceRS.next()) {
				json.put(CevaCommonConstants.SELECT_KEY, serviceRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, serviceRS.getString(1)
						+ "-" + serviceRS.getString(2));
				serviceJSONArray.add(json);
				json.clear();
			}

			resultJson.put("BANK_LIST", serviceJSONArray);

			serviceDataMap.put("BANK_LIST", resultJson);

			logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetBankDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetBankDetails [" + e.getMessage()
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
			serviceJSONArray.clear();
			serviceJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO getServiceInfo(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside GetServiceInfo.. ");
		HashMap<String, Object> serviceMap = null;
		JSONObject resultJson = null;
		JSONObject subServiceJSON = null;
		JSONObject feeJSON = null;

		JSONArray serviceJSONArray = null;

		ArrayList<String> serviceArray = null;
		ArrayList<String> subServiceArray = null;

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		JSONObject json = null;

		// String
		// serviceQry="Select SERVICE_CODE,SERVICE_NAME,MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') from SERVICE_MASTER where SERVICE_TYPE is null  order by SERVICE_CODE";
		/*String serviceQry = "Select SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SERVICE_NAME and rownum<2),"
				+ "MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),STATUS from SERVICE_MASTER where SERVICE_TYPE is null  "
				+ "order by SERVICE_CODE";*/
		String serviceQry = "select SERVICE_CODE,SERVICE_NAME from MOB_SERVICE_MASTER order by SERVICE_NAME";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();

			serviceMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			subServiceJSON = new JSONObject();
			feeJSON = new JSONObject();

			serviceJSONArray = new JSONArray();

			serviceArray = new ArrayList<String>();
			subServiceArray = new ArrayList<String>();

			logger.debug("connection is null [" + connection == null + "]");

			servicePstmt = connection.prepareStatement(serviceQry);

			serviceRS = servicePstmt.executeQuery();

			json = new JSONObject();
			while (serviceRS.next()) {
				json.put(CevaCommonConstants.SERVICE_CODE,
						serviceRS.getString(1));
				json.put(CevaCommonConstants.SERVICE_NAME,
						serviceRS.getString(2));
				serviceArray.add(serviceRS.getString(1));
				serviceJSONArray.add(json);
				json.clear();
			}

			servicePstmt.close();
			serviceRS.close();

			logger.debug("Service JSONArray [" + serviceJSONArray + "]");

			resultJson.put(CevaCommonConstants.SERVICE_INFO, serviceJSONArray);
			serviceJSONArray.clear();
			for (int i = 0; i < serviceArray.size(); i++) {
				
				String subServiceQry = "Select distinct FEE_CODE,FEE_NAME,SERVICE_CODE,AUTHID,"
						+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),STATUS "
						+ "from FEE_MASTER where SUB_SERVICE_CODE=?  order by FEE_CODE";

				servicePstmt = connection.prepareStatement(subServiceQry);
				servicePstmt.setString(1, serviceArray.get(i));

				serviceRS = servicePstmt.executeQuery();
				while (serviceRS.next()) {
					json.put(CevaCommonConstants.SERVICE_CODE,
							serviceRS.getString(1));
					json.put(CevaCommonConstants.SERVICE_NAME,
							serviceRS.getString(2));
					json.put(CevaCommonConstants.SUB_SERVICE_CODE,
							serviceRS.getString(3));
					json.put(CevaCommonConstants.SUB_SERVICE_NAME,
							serviceRS.getString(4));
					json.put(CevaCommonConstants.MAKER_DATE,
							serviceRS.getString(5));
					json.put(CevaCommonConstants.STATUS, serviceRS.getString(6));
					serviceJSONArray.add(json);
					json.clear();
				}
				subServiceJSON.put(serviceArray.get(i) + "_" + "SUBSERVICE",
						serviceJSONArray);
				serviceJSONArray.clear();

				servicePstmt.close();
				serviceRS.close();
			}

			String subServicesQry = "select distinct SUB_SERVICE_CODE from FEE_MASTER";
			servicePstmt = connection.prepareStatement(subServicesQry);

			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
				subServiceArray.add(serviceRS.getString(1));
			}

			servicePstmt.close();
			serviceRS.close();

			for (int i = 0; i < subServiceArray.size(); i++) {
				String feeQry = "Select distinct FEE_CODE,FEE_NAME,SUB_SERVICE_CODE,AUTHID,"
						+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),STATUS "
						+ "from FEE_MASTER where SUB_SERVICE_CODE=?  order by FEE_CODE";
				servicePstmt = connection.prepareStatement(feeQry);
				servicePstmt.setString(1, subServiceArray.get(i));

				serviceRS = servicePstmt.executeQuery();
				while (serviceRS.next()) {
					json.put(CevaCommonConstants.FEE_CODE,
							serviceRS.getString(1));
					json.put(CevaCommonConstants.SERVICE_CODE,
							serviceRS.getString(2));
					json.put(CevaCommonConstants.SUB_SERVICE_CODE,
							serviceRS.getString(3));
					json.put(CevaCommonConstants.MAKER_ID,
							serviceRS.getString(4));
					json.put(CevaCommonConstants.MAKER_DATE,
							serviceRS.getString(5));
					json.put(CevaCommonConstants.STATUS, serviceRS.getString(6));
					serviceJSONArray.add(json);
					json.clear();
				}
				feeJSON.put(subServiceArray.get(i) + "_" + "FEE",
						serviceJSONArray);
				serviceJSONArray.clear();

				servicePstmt.close();
				serviceRS.close();
			}

			serviceMap.put(CevaCommonConstants.SERVICE_INFO, resultJson);
			serviceMap
					.put(CevaCommonConstants.SUB_SERVICE_INFO, subServiceJSON);
			serviceMap.put(CevaCommonConstants.FEE_INFO, feeJSON);
			logger.debug("ServiceMap [" + serviceMap + "]");
			responseDTO.setData(serviceMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetServiceInfo [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetServiceInfo [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			serviceMap = null;
			resultJson = null;
			subServiceJSON = null;
			feeJSON = null;

			serviceJSONArray = null;

			serviceArray = null;
			subServiceArray = null;

			try {
				if (servicePstmt != null)
					servicePstmt.close();

				if (serviceRS != null)
					serviceRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO getNextServiceCode(RequestDTO requestDTO) {

		logger.debug("Inside GetNextServiceCode... ");
		HashMap<String, Object> serviceDataMap = null;

		JSONObject resultJson = null;

		JSONArray serviceJSONArray = null;
		JSONArray settlementJSONArray = null;

		Connection connection = null;

		PreparedStatement serviceIdPstmt = null;
		PreparedStatement servicePstmt = null;
		PreparedStatement settlementPstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;
		ResultSet settlementRS = null;

		String serviceIdQry = "";
		JSONObject json = null;
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			serviceJSONArray = new JSONArray();
			settlementJSONArray = new JSONArray();

			resultJson = new JSONObject();

			serviceDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");
			serviceIdQry = "Select count(SERVICE_CODE) from SERVICE_MASTER where SERVICE_TYPE is null";

			serviceIdPstmt = connection.prepareStatement(serviceIdQry);

			serviceIdRS = serviceIdPstmt.executeQuery();
			int serviceId = 0;
			if (serviceIdRS.next()) {
				// String serviceID = serviceIdRS.getString(1);
				serviceId = serviceIdRS.getInt(1);
				logger.debug("ServiceID [" + serviceId + "]");
			}

			serviceId += 1;
			resultJson.put(
					CevaCommonConstants.SERVICE_CODE,
					"S"
							+ StringUtils.leftPad(String.valueOf(serviceId), 7,
									'0'));

			String serviceQry = "Select distinct BANK_CODE,BANK_NAME from BANK_MASTER order by BANK_NAME";
			servicePstmt = connection.prepareStatement(serviceQry);

			serviceRS = servicePstmt.executeQuery();

			json = new JSONObject();

			while (serviceRS.next()) {
				json.put(CevaCommonConstants.SELECT_KEY, serviceRS.getString(1)
						+ "-" + serviceRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, serviceRS.getString(1));
				serviceJSONArray.add(json);
				json.clear();
			}

			resultJson.put(CevaCommonConstants.SERVICE_LIST, serviceJSONArray);

			String settlementQry = "Select distinct FINANCIAL_CODE,FINANCIAL_NAME from "
					+ "FINANCIAL_MASTER where TRANS_TYPE='T' order by FINANCIAL_NAME";
			settlementPstmt = connection.prepareStatement(settlementQry);
			settlementRS = settlementPstmt.executeQuery();

			while (settlementRS.next()) {

				json.put(
						CevaCommonConstants.SELECT_KEY,
						settlementRS.getString(1) + "-"
								+ settlementRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,
						settlementRS.getString(1));
				settlementJSONArray.add(json);
				json.clear();

			}

			resultJson.put(CevaCommonConstants.SETTLEMENT_LIST,
					settlementJSONArray);

			serviceDataMap.put(CevaCommonConstants.SERVICE_INFO, resultJson);

			logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetNextServiceCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetNextServiceCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			serviceDataMap = null;

			resultJson = null;

			serviceJSONArray = null;
			settlementJSONArray = null;

			try {

				if (serviceIdPstmt != null)
					serviceIdPstmt.close();
				if (servicePstmt != null)
					servicePstmt.close();
				if (settlementPstmt != null)
					settlementPstmt.close();

				if (serviceIdRS != null)
					serviceIdRS.close();
				if (serviceRS != null)
					serviceRS.close();
				if (settlementRS != null)
					settlementRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}

	public ResponseDTO insertService(RequestDTO requestDTO) {

		logger.debug("Inside InsertService... ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertServiceProc = "{call insertServiceProc(?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.SERVICE_NAME));
			callableStatement.setString(4, requestJSON
					.getString(CevaCommonConstants.SETTLEMENT_ACCOUNT));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);

			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.IP));

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO
						.addMessages("Service Information Stored Successfully. ");
			} else if (resCnt == -1) {
				responseDTO.addError("Service already available. ");
			} else {
				responseDTO.addError("Service Information Insertion Failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in GetNextServiceCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetNextServiceCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			insertServiceProc = null;
			try {

				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO checkServiceCode(RequestDTO requestDTO) {

		logger.debug("Inside CheckServiceCode... ");

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String insertServiceProc = "Select count(*) from SERVICE_MASTER where  "
				+ "SERVICE_NAME=?";
		JSONObject json = null;
		JSONObject resultJson = null;
		JSONArray settlementJSONArray = null;

		HashMap<String, Object> serviceDataMap = null;

		int resCnt = 0;
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			resultJson = requestJSON;

			serviceDataMap = new HashMap<String, Object>();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			prepareStatement = connection.prepareStatement(insertServiceProc);
			prepareStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.SERVICE_NAME));

			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				resCnt = resultSet.getInt(1);
			}

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt > 0) {
				responseDTO.addError("Service already available. ");
			}

			prepareStatement.close();
			resultSet.close();

			String settlementQry = "Select distinct BANK_CODE,BANK_NAME from BANK_MASTER order by BANK_NAME";
			prepareStatement = connection.prepareStatement(settlementQry);
			resultSet = prepareStatement.executeQuery();

			json = new JSONObject();

			settlementJSONArray = new JSONArray();

			while (resultSet.next()) {
				json.put(CevaCommonConstants.SELECT_KEY, resultSet.getString(1)
						+ "-" + resultSet.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, resultSet.getString(1));
				settlementJSONArray.add(json);
				json.clear();

			}

			resultJson.put(CevaCommonConstants.SERVICE_LIST,
					settlementJSONArray);

			serviceDataMap.put(CevaCommonConstants.SERVICE_INFO, resultJson);

			logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in CheckServiceCode [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in CheckServiceCode [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(prepareStatement);
			DBUtils.closeResultSet(resultSet);

			insertServiceProc = null;

		}
		return responseDTO;
	}

	public ResponseDTO ServiceViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside ServiceViewDetails... ");
		HashMap<String, Object> storeMap = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;

		String storeQry = "Select SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master "
				+ "where BANK_CODE=SERVICE_NAME and rownum<2),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') "
				+ "from SERVICE_MASTER where  SERVICE_CODE=? and SERVICE_TYPE is null";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			storeMap = new HashMap<String, Object>();

			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));

			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(2));
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						storeRS.getString(3));
				responseJSON.put(CevaCommonConstants.MAKER_DATE,
						storeRS.getString(4));
			}
			storeMap.put(CevaCommonConstants.SERVICE_INFO, responseJSON);
			responseDTO.setData(storeMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ServiceViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in ServiceViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			storeMap = null;
			try {

				if (storePstmt != null)
					storePstmt.close();
				if (storeRS != null)
					storeRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO SubServiceCreateScreenDetails(RequestDTO requestDTO) {

		logger.debug("Inside SubServiceCreateScreenDetails... ");
		HashMap<String, Object> serviceMap = null;
		JSONArray subServiceJSONArray = null;

		String serviceName = null;
		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;

		Connection connection = null;

		String storeQry = "Select SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SERVICE_NAME and rownum<2) from SERVICE_MASTER where  SERVICE_CODE=? and SERVICE_TYPE is null";

		try {
			serviceMap = new HashMap<String, Object>();
			subServiceJSONArray = new JSONArray();
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));

			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(2));
				serviceName = storeRS.getString(2);
			}

			storePstmt.close();
			storeRS.close();

			String subServiceIdQry = "Select count(SUB_SERVICE_CODE) from SERVICE_MASTER where SERVICE_TYPE is not null";

			storePstmt = connection.prepareStatement(subServiceIdQry);

			storeRS = storePstmt.executeQuery();
			int subServiceId = 0;
			if (storeRS.next()) {
				String serviceID = storeRS.getString(1);
				subServiceId = Integer.parseInt(serviceID);
				logger.debug("ServiceID [" + serviceID + "]");
			}
			subServiceId++;
			responseJSON
					.put(CevaCommonConstants.SUB_SERVICE_CODE, subServiceId);

			storePstmt.close();
			storeRS.close();

			String subServiceQry = "";
			if (serviceName.equals("HUDUMA")) {
				subServiceQry = "Select HUDUMA_SERVICE_CODE,HUDUMA_SERVICE_DESC from HUDUMA_MASTER order by HUDUMA_SERVICE_DESC";
			} else {
				subServiceQry = "Select PROCESS_CODE,PROCESS_NAME from PROCESS_MASTER order by PROCESS_NAME";
			}

			storePstmt = connection.prepareStatement(subServiceQry);

			storeRS = storePstmt.executeQuery();
			JSONObject json = null;
			while (storeRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(1)
						+ "-" + storeRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
				subServiceJSONArray.add(json);
				json.clear();
				json = null;
			}

			responseJSON.put(CevaCommonConstants.SUBSERVICE_LIST,
					subServiceJSONArray);

			serviceMap.put(CevaCommonConstants.SERVICE_INFO, responseJSON);

			logger.debug("Store Map [" + serviceMap + "]");
			responseDTO.setData(serviceMap);

		} catch (SQLException e) {
			logger.debug("SQLException in SubServiceCreateScreenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in SubServiceCreateScreenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			subServiceJSONArray = null;
			try {

				if (storePstmt != null)
					storePstmt.close();

				if (storeRS != null)
					storeRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO checkSubServiceCreateScreenDetails(RequestDTO requestDTO) {

		logger.debug("Inside SubServiceCreateScreenDetails... ");
		HashMap<String, Object> serviceMap = null;
		JSONArray subServiceJSONArray = null;

		PreparedStatement storePstmt = null;
		PreparedStatement subServiceIdPstmt = null;
		PreparedStatement subServicePstmt = null;

		ResultSet storeRS = null;
		ResultSet subServiceIdRS = null;
		ResultSet subServiceRS = null;

		Connection connection = null;
		String subServiceName = "";

		String storeQry = "Select SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master "
				+ "where BANK_CODE=SERVICE_NAME and rownum<2) from SERVICE_MASTER "
				+ "where  SERVICE_CODE=? and SERVICE_TYPE is null";

		try {
			serviceMap = new HashMap<String, Object>();
			subServiceJSONArray = new JSONArray();
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			responseJSON = requestJSON;
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			subServiceName = requestJSON.getString("subServiceName");

			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));

			storeRS = storePstmt.executeQuery();
			if (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(2));
			}

			String subServiceIdQry = "Select count(*) from SERVICE_MASTER where SUB_SER_TXN_CODE=? and SERVICE_CODE=?";

			subServiceIdPstmt = connection.prepareStatement(subServiceIdQry);
			subServiceIdPstmt.setString(1, subServiceName);
			subServiceIdPstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));
			subServiceIdRS = subServiceIdPstmt.executeQuery();
			int subServiceId = 0;
			if (subServiceIdRS.next()) {
				subServiceId = subServiceIdRS.getInt(1);
			}

			logger.debug("SubServiceId [" + subServiceId + "]");

			if (subServiceId > 0) {
				responseDTO
						.addError("Sub-Service already assigned to the selected Service Code.");
			}
			/*
			 * responseJSON .put(CevaCommonConstants.SUB_SERVICE_CODE,
			 * subServiceId);
			 */

			String subServiceQry = "Select PROCESS_CODE,PROCESS_NAME from PROCESS_MASTER order by PROCESS_NAME";

			subServicePstmt = connection.prepareStatement(subServiceQry);

			subServiceRS = subServicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			while (subServiceRS.next()) {

				json.put(
						CevaCommonConstants.SELECT_KEY,
						subServiceRS.getString(1) + "-"
								+ subServiceRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,
						subServiceRS.getString(1));
				subServiceJSONArray.add(json);
				json.clear();

			}

			responseJSON.put(CevaCommonConstants.SUBSERVICE_LIST,
					subServiceJSONArray);

			serviceMap.put(CevaCommonConstants.SERVICE_INFO, responseJSON);

			logger.debug("Store Map [" + serviceMap + "]");
			responseDTO.setData(serviceMap);

		} catch (SQLException e) {
			logger.debug("SQLException in SubServiceCreateScreenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in SubServiceCreateScreenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			subServiceJSONArray = null;
			try {

				if (storePstmt != null)
					storePstmt.close();
				if (subServiceIdPstmt != null)
					subServiceIdPstmt.close();
				if (subServicePstmt != null)
					subServicePstmt.close();

				if (storeRS != null)
					storeRS.close();
				if (subServiceIdRS != null)
					subServiceIdRS.close();
				if (subServiceRS != null)
					subServiceRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO insertSubService(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InsertSubService... ");

		CallableStatement callableStatement = null;
		String insertServiceProc = "{call insertSubServiceProc(?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.SERVICE_NAME));
			callableStatement
					.setString(4, requestJSON
							.getString(CevaCommonConstants.SUB_SERVICE_CODE));
			callableStatement
					.setString(5, requestJSON
							.getString(CevaCommonConstants.SUB_SERVICE_TEXT));
			callableStatement
					.setString(6, requestJSON
							.getString(CevaCommonConstants.SUB_SERVICE_NAME));
			callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);

			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.IP));

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(7);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO
						.addMessages("Sub Service Information Stored Successfully. ");
			} else if (resCnt == -1) {
				responseDTO.addError("Sub Service already available. ");
			} else {
				responseDTO
						.addError("Sub Service Inofrmation Insertion failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InsertSubService [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in InsertSubService [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO FeeCreateScrrenDetails(RequestDTO requestDTO) {

		logger.debug("Inside FeeCreateScrrenDetails.. ");
		HashMap<String, Object> serviceMap = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		Connection connection = null;

		String storeQry = "Select SERVICE_CODE,SERVICE_NAME from MOB_SERVICE_MASTER where SERVICE_CODE=?";
		JSONObject json = null;
		try {
			serviceMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt
					.setString(1, requestJSON
							.getString(CevaCommonConstants.SERVICE_CODE));

			storeRS = storePstmt.executeQuery();
			if (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(2));
			}

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			storeQry = "Select count(FEE_CODE) from FEE_MASTER_TEMP";
			storePstmt = connection.prepareStatement(storeQry);

			storeRS = storePstmt.executeQuery();
			int feeId = 0;
			if (storeRS.next()) {
				String feeID = storeRS.getString(1);
				feeId = Integer.parseInt(feeID);
				logger.debug(" FeeID [" + feeID + "]");
				feeID = null;
			}
			feeId++;
			responseJSON.put(CevaCommonConstants.FEE_CODE, feeId);

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			// storeQry =
			// "Select FINANCIAL_CODE,FINANCIAL_NAME from FINANCIAL_MASTER  where trans_type='F' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storeQry = "Select ACCT_NUMBER from FINANCIAL_MASTER  where trans_type='F' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();

			json = new JSONObject();

			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1));

			}

			logger.debug("AccountJSONArray [" + json + "]");

			responseJSON.put(CevaCommonConstants.ACCOUNT_LIST, json);
			json.clear();

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			// storeQry =
			// "Select FINANCIAL_CODE,FINANCIAL_NAME from FINANCIAL_MASTER  where trans_type='F' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storeQry = "Select ACCT_NUMBER from FINANCIAL_MASTER  where trans_type='C' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();

			json = new JSONObject();

			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1));

			}

			logger.debug("Service Tax AccountJSONArray [" + json + "]");

			responseJSON.put("ServiceTaxAccount", json);
			json.clear();
			storeQry = null;

			storePstmt.close();
			storeRS.close();

			storeQry = "select KEY_VALUE,KEY_ID FROM CONFIG_DATA WHERE KEY_GROUP='FREQUENCYDATA' AND KEY_TYPE='FREQUENCYPERIOD' order by KEY_ID";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json.put(storeRS.getString(2), storeRS.getString(1));
			}

			responseJSON.put("FREQ_DATA", json);

			json.clear();

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			storeQry = "SELECT CRCY_CODE FROM CURRENCY_MASTER";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1));
			}

			responseJSON.put("CRCY_CODE", json);

			json.clear();
			storeQry = null;
			storePstmt.close();
			storeRS.close();

			storeQry = "SELECT NETWORK_ID FROM ACQUIRER_MASTER";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1));
			}

			responseJSON.put("ACQ_ID", json);
			json.clear();
			storeQry = null;
			storePstmt.close();
			storeRS.close();

			storeQry = "select BIN,BANK_NAME from bank_master where BANK_CODE not in ('DEPOSITS','POSTAKE','MPESAKEN')";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1) + "-"
						+ storeRS.getString(2));

			}

			responseJSON.put("BINS", json);
			json.clear();

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			storeQry = "select merchant_id,merchant_NAME from merchant_master";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1) + "-"
						+ storeRS.getString(2));

			}

			responseJSON.put("merchantList", json);
			json.clear();

			storeQry = null;
			storePstmt.close();
			storeRS.close();

			/* storeQry = "select acct_no,merchant_id from bank_acct_master"; */
			storeQry = "Select FINANCIAL_CODE,FINANCIAL_NAME from FINANCIAL_MASTER  where  BANK_FLAG='M' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt = connection.prepareStatement(storeQry);
			storeRS = storePstmt.executeQuery();
			// String agentaccount="";
			while (storeRS.next()) {
				json.put(storeRS.getString(1), storeRS.getString(1) + "-"
						+ storeRS.getString(2));
				// agentaccount=storeRS.getString(1);

			}

			responseJSON.put("merchantdata", json);

			json.clear();
			serviceMap.put(CevaCommonConstants.FEE_INFO, responseJSON);

			responseDTO.setData(serviceMap);
			logger.debug("Response DTO [" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug("SQLException in FeeCreateScrrenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in FeeCreateScrrenDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			try {
				if (connection != null) {
					connection.close();
				}
				if (storePstmt != null) {
					storePstmt.close();
				}
				if (storeRS != null) {
					storeRS.close();
				}
			} catch (SQLException e) {
			}
			json = null;
		}
		return responseDTO;
	}

	public ResponseDTO insertFeeDetails(RequestDTO requestDTO) {
		Connection connection = null;
		logger.debug("Inside InsertFeeDetails... ");
		CallableStatement callableStatement = null;

		String insertServiceProc = "{call insertFeeDetailsProc(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			/*
			 * logger.debug(
			 * "[ServiceManagementDAO][insertFeeDetails][MAKER_ID Value : "
			 * +requestJSON.getString(CevaCommonConstants.MAKER_ID)+"]");
			 * logger.debug(
			 * "[ServiceManagementDAO][insertFeeDetails][SERVICE_CODE Value : "
			 * +requestJSON.getString(CevaCommonConstants.SERVICE_CODE)+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "+
			 * requestJSON.getString(CevaCommonConstants.SUB_SERVICE_CODE)+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "+
			 * requestJSON.getString(CevaCommonConstants.FEE_CODE)+"]");
			 * logger.debug
			 * ("[ServiceManagementDAO][insertFeeDetails][Value : "+requestJSON
			 * .getString("merchantname")+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "
			 * +requestJSON.getString("feename")+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "
			 * +requestJSON.getString("serviceTrans")+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "
			 * +requestJSON.getString("partnerDetails")+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "
			 * +requestJSON.getString("multiSlabFeeDetails")+"]");
			 * logger.debug("[ServiceManagementDAO][insertFeeDetails][Value : "
			 * +requestJSON.getString("offusTrnDetails")+"]");
			 */

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.SERVICE_CODE));
			callableStatement
					.setString(3, requestJSON
							.getString(CevaCommonConstants.SUB_SERVICE_CODE));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.FEE_CODE));
			callableStatement.setString(5,
					requestJSON.getString("merchantname"));
			callableStatement.setString(6, requestJSON.getString("feename"));
			callableStatement.setString(7,
					requestJSON.getString("serviceTrans"));
			callableStatement.setString(8,
					requestJSON.getString("partnerDetails"));
			callableStatement.setString(9,
					requestJSON.getString("multiSlabFeeDetails"));
			callableStatement.setString(10,
					requestJSON.getString("offusTrnDetails"));
			callableStatement.registerOutParameter(11, java.sql.Types.INTEGER);

			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.setString(13,
					requestJSON.getString("offUsFee"));
			

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(11);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages("Fee Information Stored Successfully.");
			} else if (resCnt == -1) {
				responseDTO.addError("Fee Already Available.");
			} else if (resCnt == 11) {
				responseDTO
						.addError("Fee Code Existed with MID and Txn Type Combination.");
			} else if (resCnt == 12) {
				responseDTO.addError("Fee Code Authorization Pending .");
			} else {
				responseDTO.addError("Fee Information Insertion Failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InsertFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in InsertFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			insertServiceProc = null;
			try {

				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO viewSubServiceDetails(RequestDTO requestDTO) {

		HashMap<String, Object> serviceMap = null;
		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;

		Connection connection = null;

		String storeQry = "Select SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SERVICE_NAME and "
				+ "rownum<2),SUB_SERVICE_CODE,SUB_SERVICE_NAME,MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') "
				+ "from SERVICE_MASTER where  SUB_SERVICE_CODE=?";
		logger.debug("Inside ViewSubServiceDetails... ");

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			serviceMap = new HashMap<String, Object>();

			storePstmt = connection.prepareStatement(storeQry);
			storePstmt
					.setString(1, requestJSON
							.getString(CevaCommonConstants.SUB_SERVICE_CODE));

			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(2));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
						storeRS.getString(3));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
						storeRS.getString(4));
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						storeRS.getString(5));
				responseJSON.put(CevaCommonConstants.MAKER_DATE,
						storeRS.getString(6));
			}

			serviceMap.put(CevaCommonConstants.SERVICE_INFO, responseJSON);

			responseDTO.setData(serviceMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ViewSubServiceDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in ViewSubServiceDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			try {
				if (storePstmt != null)
					storePstmt.close();
				if (storeRS != null)
					storeRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO viewFeeDetails(RequestDTO requestDTO) {

		logger.debug("Inside ViewFeeDetails.......................................................... ");
		HashMap<String, Object> serviceMap = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		Connection connection = null;
		String slabDet = "";
		// String feecode= requestJSON.getString(CevaCommonConstants.FEE_CODE);

		String storeQry = "Select SM.SERVICE_NAME,"
				+ "SM.SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SERVICE_CODE,NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
				+ "FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID,to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),FM.ONUSOFUSFLAG ,FM.SERVICE_TAX,FM.SERVICE_TAX_ACCOUNT "
				+ "from FEE_MASTER FM,MOB_SERVICE_MASTER SM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
				+ "  FM.FEE_CODE=? ORDER BY FM.FEE_CODE ASC";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			serviceMap = new HashMap<String, Object>();
			storePstmt = connection.prepareStatement(storeQry);
			// storePstmt.setString(1,requestJSON.getString(CevaCommonConstants.FEE_CODE));
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.FEE_CODE));
			storeRS = storePstmt.executeQuery();
			int i = 0;

			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
						storeRS.getString(2));
				responseJSON.put(CevaCommonConstants.FEE_CODE,
						storeRS.getString(3));
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(4));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
						storeRS.getString(5));
				responseJSON.put(CevaCommonConstants.FALT_PERCENT,
						storeRS.getString(6));
				responseJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,
						storeRS.getString(7));
				if (i == 0) {
					slabDet += storeRS.getString(8);
				} else {
					slabDet += "#" + storeRS.getString(8);
				}
				// responseJSON.put("SLAB", storeRS.getString(8));
				responseJSON.put("ACQDET", storeRS.getString(9));
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						storeRS.getString(10));
				responseJSON.put(CevaCommonConstants.MAKER_DATE,
						storeRS.getString(11));
				responseJSON.put("ONUS_OFFUS_FLAG", storeRS.getString(12));
				i++;

			}

			responseJSON.put("SLAB", slabDet);

			serviceMap.put(CevaCommonConstants.FEE_INFO, responseJSON);

			responseDTO.setData(serviceMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ViewFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in ViewFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			try {

				if (storePstmt != null)
					storePstmt.close();
				if (storeRS != null)
					storeRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO ModifyFeeCodeDetails(RequestDTO requestDTO) {

		logger.debug("Inside ViewFeeDetails.. ");
		HashMap<String, Object> serviceMap = null;

		PreparedStatement storePstmt = null;
		PreparedStatement storePstmt1 = null;
		PreparedStatement storePstmt2 = null;
		ResultSet storeRS = null;
		ResultSet storeRS1 = null;
		ResultSet storeRS2 = null;
		Connection connection = null;
		String slabDet = "";

		String storeQry = "Select SM.SERVICE_NAME,"
				+ "SM.SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SERVICE_CODE,NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
				+ "FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID,to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),FM.ONUSOFUSFLAG "
				+ "from FEE_MASTER FM,MOB_SERVICE_MASTER SM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
				+ " FM.FEE_CODE=?";
		
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			String feename = requestJSON.getString("feename");
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			serviceMap = new HashMap<String, Object>();
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.FEE_CODE));
			storeRS = storePstmt.executeQuery();
			int i = 0;

			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,
						storeRS.getString(1));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
						storeRS.getString(2));
				responseJSON.put(CevaCommonConstants.FEE_CODE,
						storeRS.getString(3));
				responseJSON.put(CevaCommonConstants.SERVICE_CODE,
						storeRS.getString(4));
				responseJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
						storeRS.getString(5));
				responseJSON.put(CevaCommonConstants.FALT_PERCENT,
						storeRS.getString(6));
				responseJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,
						storeRS.getString(7));
				if (i == 0) {
					slabDet += storeRS.getString(8);
				} else {
					slabDet += "#" + storeRS.getString(8);
				}

				// responseJSON.put("SLAB", storeRS.getString(8));
				responseJSON.put("ACQDET", storeRS.getString(9));
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						storeRS.getString(10));
				responseJSON.put(CevaCommonConstants.MAKER_DATE,
						storeRS.getString(11));
				responseJSON.put("ONUS_OFFUS_FLAG", storeRS.getString(12));
				i++;

			}
			responseJSON.put("feename", feename);

			String storeQry1 = "select KEY_VALUE,KEY_ID FROM CONFIG_DATA WHERE KEY_GROUP='FREQUENCYDATA' AND KEY_TYPE='FREQUENCYPERIOD'";
			storePstmt1 = connection.prepareStatement(storeQry1);
			storeRS1 = storePstmt1.executeQuery();
			JSONObject json = new JSONObject();
			while (storeRS1.next()) {
				json.put(storeRS1.getString(2), storeRS1.getString(1));
			}
			responseJSON.put("FREQ_DATA", json);

			storeQry1 = null;
			storePstmt1.close();
			storeRS1.close();
			json.clear();
			storeQry1 = "SELECT CRCY_CODE FROM CURRENCY_MASTER";
			storePstmt1 = connection.prepareStatement(storeQry1);
			storeRS1 = storePstmt1.executeQuery();
			while (storeRS1.next()) {
				json.put(storeRS1.getString(1), storeRS1.getString(1));
			}
			responseJSON.put("CRCY_CODE", json);

			storeQry1 = null;
			storePstmt1.close();
			storeRS1.close();

			storeQry1 = "Select  ACCT_NUMBER  from FINANCIAL_MASTER  where trans_type='F' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt1 = connection.prepareStatement(storeQry1);
			storeRS1 = storePstmt1.executeQuery();

			JSONObject json1 = new JSONObject();
			JSONObject json2 = new JSONObject();

			while (storeRS1.next()) {
				json1.put(storeRS1.getString(1), storeRS1.getString(1));

			}

			logger.debug("AccountJSONArray [" + json1 + "]");
			responseJSON.put("AccountList", json1);
			json1.clear();
			storeQry1 = null;
			storePstmt1.close();
			storeRS1.close();

			storeQry1 = "Select  ACCT_NUMBER  from FINANCIAL_MASTER  where trans_type='C' AND BANK_FLAG='B' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt1 = connection.prepareStatement(storeQry1);
			storeRS1 = storePstmt1.executeQuery();

			/*
			 * JSONObject json1 = new JSONObject(); JSONObject json2 = new
			 * JSONObject();
			 */

			while (storeRS1.next()) {
				json1.put(storeRS1.getString(1), storeRS1.getString(1));

			}

			logger.debug("Service Tax AccountJSONArray [" + json1 + "]");
			responseJSON.put("ServiceTaxAccount", json1);
			json1.clear();

			// storeQry2 = null;
			storePstmt1.close();
			storeRS1.close();
			json1.clear();
			String storeQry2 = "Select FINANCIAL_CODE,FINANCIAL_NAME from FINANCIAL_MASTER  where  BANK_FLAG='M' and FINANCIAL_CODE not like 'MPES%' and FINANCIAL_CODE not like 'KRA%' order by FINANCIAL_NAME";
			storePstmt2 = connection.prepareStatement(storeQry2);
			storeRS2 = storePstmt2.executeQuery();
			String agentAccount = "";
			while (storeRS2.next()) {
				/*
				 * json2.put(storeRS2.getString(1),
				 * storeRS2.getString(1)+"-"+storeRS2.getString(2));
				 */
				agentAccount = storeRS2.getString(1);

			}

			responseJSON.put("merchantdata", agentAccount);

			responseJSON.put("SLAB", slabDet);

			serviceMap.put(CevaCommonConstants.FEE_INFO, responseJSON);

			responseDTO.setData(serviceMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ViewFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in ViewFeeDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			serviceMap = null;
			try {

				if (storePstmt != null)
					storePstmt.close();
				if (storeRS != null)
					storeRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO inserRegisterBin(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InserRegisterBin.. ");

		CallableStatement callableStatement = null;
		String insertRegisterBinProc = "{call inserRegisterBinProc(?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertRegisterBinProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.BANK_CODE));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.BANK_NAME));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.BANK_MULTI_DATA));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.IP));

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);

			logger.debug("ResultCnt from DB [" + resCnt + "]");

			if (resCnt == 1) {
				responseDTO
						.addMessages("Register Bin Information Stored Successfully. ");
			} else if (resCnt == -1) {
				responseDTO.addError("Registerd Bank Code already available. ");
			} else {
				responseDTO
						.addError("Register Bin Information Insertion failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InserRegisterBin [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in InserRegisterBin [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			insertRegisterBinProc = null;
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO inserProcessingCode(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InserProcessingCode... ");
		CallableStatement callableStatement = null;
		String inserProcessingCodeProc = "{call inserProcessingCodeProc(?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(inserProcessingCodeProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.BANK_MULTI_DATA));
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(3);

			logger.debug(" resultCnt from DB:::" + resCnt);
			if (resCnt == 1) {
				responseDTO.addMessages(callableStatement.getString(4));
			} else if (resCnt == -1) {
				responseDTO.addError(callableStatement.getString(4));
			} else {
				responseDTO
						.addError("Processing Inofrmation Insertion failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InserProcessingCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in InserProcessingCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO inserhudumaService(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InserhudumaService... ");

		CallableStatement callableStatement = null;
		String inserHudumaCodeProc = "{call inserHudumaServiceCodeProc(?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(inserHudumaCodeProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2, requestJSON
					.getString(CevaCommonConstants.HUDUMA_SERVICE_CODE));
			callableStatement.setString(3, requestJSON
					.getString(CevaCommonConstants.HUDUMA_SERVICE_DESC));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.VIRTUAL_CARD));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.BANK_MULTI_DATA));
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(6);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages(callableStatement.getString(7));
			} else if (resCnt == -1) {

				responseDTO.addError(callableStatement.getString(7));
			} else {
				responseDTO
						.addError("Huduma Service Information Insertion failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InserhudumaService ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in InserhudumaService ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			inserHudumaCodeProc = null;
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO getBinViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetBinViewDetails... ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray binJSONArray = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		JSONObject json = null;
		String merchantQry = "SELECT  BANK_CODE ,BANK_NAME ,BIN ,BIN_DESC,MAKER_ID,"
				+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') FROM   BANK_MASTER order by BANK_CODE";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			binJSONArray = new JSONArray();

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantRS = merchantPstmt.executeQuery();
			json = new JSONObject();
			while (merchantRS.next()) {

				json.put(CevaCommonConstants.BANK_CODE, merchantRS.getString(1));
				json.put(CevaCommonConstants.BANK_NAME, merchantRS.getString(2));
				json.put(CevaCommonConstants.BIN, merchantRS.getString(3));
				json.put(CevaCommonConstants.BIN_DESC, merchantRS.getString(4));
				json.put(CevaCommonConstants.MAKER_ID, merchantRS.getString(5));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(6));
				binJSONArray.add(json);
				json.clear();

			}
			resultJson
					.put(CevaCommonConstants.MERCHANT_DASHBOARD, binJSONArray);

			merchantMap.put(CevaCommonConstants.MERCHANT_DASHBOARD, resultJson);
			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetBinViewDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetBinViewDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			merchantMap = null;
			resultJson = null;
			binJSONArray = null;
			try {

				if (merchantPstmt != null)
					merchantPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO getProcessingCodeViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetProcessingCodeViewDetails... ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray processCodeJSONArray = null;

		Connection connection = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String merchantQry = "SELECT PROCESS_CODE ,PROCESS_NAME ,MAKER_ID,to_char(MAKER_DTTM,'D-MM-YYYY HH24:MI:SS') FROM   PROCESS_MASTER order by PROCESS_CODE";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			processCodeJSONArray = new JSONArray();

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.PROC_CODE, merchantRS.getString(1));
				json.put(CevaCommonConstants.PROCESS_NAME,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.MAKER_ID, merchantRS.getString(3));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(4));
				processCodeJSONArray.add(json);
				json.clear();
				json = null;
			}

			resultJson.put(CevaCommonConstants.MERCHANT_DASHBOARD,
					processCodeJSONArray);

			merchantMap.put(CevaCommonConstants.MERCHANT_DASHBOARD, resultJson);
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			merchantMap = null;
			resultJson = null;
			processCodeJSONArray = null;

			try {
				if (merchantPstmt != null)
					merchantPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO getHudumaServiceViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetHudumaServiceViewDetails... ");

		HashMap<String, Object> merchantMap = null;

		JSONObject resultJson = null;
		JSONArray processCodeJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		Connection connection = null;
		String merchantQry = "SELECT HM.HUDUMA_SERVICE_CODE,HM.VIRTUAL_CARD ,HS.HPROCESS_CODE,HS.HPROCESS_NAME,"
				+ "HM.MAKER_ID,to_char(HS.MAKER_DTTM,'D-MM-YYYY HH24:MI:SS') "
				+ "FROM  HUDUMA_MASTER HM,HUDUMA_SERVICES HS "
				+ "where trim(HM.REF_KEY)=trim(HS.REF_KEY) order by HM.HUDUMA_SERVICE_CODE,HS.HPROCESS_CODE";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			processCodeJSONArray = new JSONArray();

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.HUDUMA_SERVICE_CODE,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.VIRTUAL_CARD,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.HUDUMA_SUB_SERVICE,
						merchantRS.getString(3));
				json.put(CevaCommonConstants.HUDUMA_SUB_SERVICE_NAME,
						merchantRS.getString(4));
				json.put(CevaCommonConstants.MAKER_ID, merchantRS.getString(5));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(6));
				processCodeJSONArray.add(json);
				json.clear();
				json = null;
			}

			resultJson.put(CevaCommonConstants.MERCHANT_DASHBOARD,
					processCodeJSONArray);

			merchantMap.put(CevaCommonConstants.MERCHANT_DASHBOARD, resultJson);
			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (merchantPstmt != null)
					merchantPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			merchantMap = null;
			resultJson = null;
			processCodeJSONArray = null;
		}
		return responseDTO;
	}

	public ResponseDTO getFeeDashBoard(RequestDTO requestDTO) {

		logger.debug("Inside GetFeeDashBoard... ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		Connection connection = null;
		/*
		 * String merchantQry=
		 * "SELECT SM.SERVICE_CODE,(select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SM.SERVICE_NAME and rownum<2),SM.SUB_SERVICE_CODE,SM.SUB_SERVICE_NAME,FM.FEE_CODE"
		 * +
		 * " FROM   SERVICE_MASTER SM FULL OUTER JOIN FEE_MASTER FM ON (FM.SERVICE_CODE = SM.SERVICE_CODE and FM.SUB_SERVICE_CODE = SM.SUB_SERVICE_CODE)  "
		 * + "order by SM.SERVICE_CODE";
		 */

		/*String merchantQry = " SELECT SM.SERVICE_NAME,SM.SERVICE_NAME,FM.FEE_CODE,FM.FEE_NAME,TO_NUMBER(FM.SLAB_FROM)/100 ,"
				+ " TO_NUMBER(FM.SLAB_TO)/100,substr(FM.FEE_STRING,instr(FM.FEE_STRING,',',1,1)+1,(instr(FM.FEE_STRING,'#',1,1)-instr(FM.FEE_STRING,',',1,1))-1)/100 bankfee,"
				+ "substr(FM.FEE_STRING,instr(FM.FEE_STRING,'#',1,1)+1)/100 merchantfee,SERVICE_TAX,DECODE(FLAT_PERCENT,'F','Flat','Percentage') "
				+ "FROM MOB_SERVICE_MASTER SM, FEE_MASTER FM  where FM.SERVICE_CODE =SM.SERVICE_CODE "
				+ "order by SM.SERVICE_CODE,FM.SLAB_FROM,FM.FEE_CODE ";*/
		String merchantQry = "SELECT SM.SERVICE_CODE||'-'||SM.SERVICE_NAME,MF.MERCHANT_ID,FM.FEE_CODE,FM.FEE_NAME,"
				+ "TO_NUMBER(FM.SLAB_FROM)/100 ,  TO_NUMBER(FM.SLAB_TO)/100,substr(FM.FEE_STRING,instr(FM.FEE_STRING,',',1,1)+1,(instr(FM.FEE_STRING,'#',1,1)-instr(FM.FEE_STRING,',',1,1))-1)/100 bankfee, "
				+ " substr(FM.FEE_STRING,instr(FM.FEE_STRING,'#',1,1)+1)/100 merchantfee,SERVICE_TAX,DECODE(FLAT_PERCENT,'F','Flat','Percentage'),MM.MERCHANT_NAME "
				+ "FROM MOB_SERVICE_MASTER SM, FEE_MASTER FM,MERCHANT_FEE MF,MERCHANT_MASTER MM  "
				+ "where FM.SERVICE_CODE =SM.SERVICE_CODE and FM.FEE_CODE = MF.FEE_CODE and MM.MERCHANT_ID = MF.MERCHANT_ID  order by SM.SERVICE_CODE,FM.SLAB_FROM,FM.FEE_CODE";

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJSONArray = new JSONArray();

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SERVICE_NAME,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.SUB_SERVICE_NAME,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.FEE_CODE, merchantRS.getString(3));
				json.put("fee_name", merchantRS.getString(4));
				json.put("slab_from", merchantRS.getString(5));
				json.put("slab_to", merchantRS.getString(6));
				// json.put("fee_string", merchantRS.getString(7));
				json.put("bank_fee", merchantRS.getString(7));
				json.put("merchant_fee", merchantRS.getString(8));
				json.put("servicetax", merchantRS.getString(9));
				json.put("percentageplat", merchantRS.getString(10));
				json.put("merchantname", merchantRS.getString(11));

				merchantJSONArray.add(json);
				json.clear();
				json = null;
			}
			logger.debug("MerchantJSONArray [" + merchantJSONArray + "]");
			resultJson.put(CevaCommonConstants.MERCHANT_DASHBOARD,
					merchantJSONArray);

			merchantMap.put(CevaCommonConstants.MERCHANT_DASHBOARD, resultJson);
			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetFeeDashBoard [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetFeeDashBoard [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			merchantMap = null;
			resultJson = null;
			merchantJSONArray = null;
			try {
				if (merchantPstmt != null)
					merchantPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO feeAuthorizationData(RequestDTO requestDTO) {

		logger.debug("Inside  FeeAuthorizationData.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;

		String userQry = "{call FEEAUTHORIZATION(?,?,?)}";
		String status = "";
		String error = "";

		CallableStatement callable = null;
		ResultSet userRS = null;

		JSONObject json = null;
		JSONArray subJsonArray = null;
		try {
			responseDTO = new ResponseDTO();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			status = requestJSON.getString(CevaCommonConstants.STATUS);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1, status);
			callable.registerOutParameter(2, OracleTypes.CURSOR);
			callable.registerOutParameter(3, OracleTypes.VARCHAR);

			callable.execute();
			logger.debug("Authorization block executed successfully "
					+ "with error_message[" + callable.getString(3) + "]");
			error = callable.getString(3);

			if (error.equalsIgnoreCase("SUCCESS")) {
				userRS = (ResultSet) callable.getObject(2);
				subJsonArray = new JSONArray();

				json = new JSONObject();
				if (status.equalsIgnoreCase("serv")) {
					while (userRS.next()) {
						json.put(CevaCommonConstants.SERVICE_CODE,
								userRS.getString(1));
						json.put(CevaCommonConstants.SERVICE_NAME,
								userRS.getString(2));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(3));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(4));
						json.put(CevaCommonConstants.status,
								userRS.getString(5));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (status.equalsIgnoreCase("subserv")) {
					while (userRS.next()) {

						json.put(CevaCommonConstants.SERVICE_CODE,
								userRS.getString(1));
						json.put(CevaCommonConstants.SERVICE_NAME,
								userRS.getString(2));
						json.put(CevaCommonConstants.SUB_SERVICE_CODE,
								userRS.getString(3));
						json.put(CevaCommonConstants.SUB_SERVICE_NAME,
								userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(5));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(6));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(7));

						subJsonArray.add(json);
						json.clear();
					}
				} else if (status.equalsIgnoreCase("proc")) {
					while (userRS.next()) {
						json.put(CevaCommonConstants.PROC_CODE,
								userRS.getString(1));
						json.put(CevaCommonConstants.PROCESS_NAME,
								userRS.getString(2));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(3));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(4));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(5));
						subJsonArray.add(json);
						json.clear();
					}
				} else {
					while (userRS.next()) {
						json.put(CevaCommonConstants.FEE_CODE,
								userRS.getString(1));
						json.put(CevaCommonConstants.SERVICE_CODE,
								userRS.getString(2));
						json.put(CevaCommonConstants.SUB_SERVICE_CODE,
								userRS.getString(3));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(5));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(6));
						subJsonArray.add(json);
						json.clear();
					}
				}

				responseJSON.put("agentMultiData", subJsonArray);
			} else {
				responseDTO.addError(error);
			}

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
			responseDTO.setData(resultMap);
		} catch (SQLException e) {
			logger.debug("SQLException in FeeAuthorizationData ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in FeeAuthorizationData 	  ["
					+ e.getMessage() + "]");
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
			responseJSON = null;
			json = null;
			subJsonArray = null;
		}
		return responseDTO;
	}

	public ResponseDTO feeAuthorizationAck(RequestDTO requestDTO) {

		logger.debug("Inside  FeeAuthorizationAck.. ");
		Connection connection = null;

		String userQry = "{call FEEAUTHORIZATIONUPD(?,?,?,?)}";
		String status = "";
		String id = "";
		String error = "";

		CallableStatement callable = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			status = requestJSON.getString(CevaCommonConstants.STATUS);
			id = requestJSON.getString(CevaCommonConstants.ID);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callable.setString(2, id);
			callable.setString(3, status);
			callable.registerOutParameter(4, OracleTypes.VARCHAR);

			callable.execute();
			error = callable.getString(4);

			logger.debug("Authorization ACK block executed successfully "
					+ "with error_message[" + error + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in FeeAuthorizationAck ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in FeeAuthorizationAck  [" + e.getMessage()
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
			userQry = null;
		}
		return responseDTO;

	}

	public ResponseDTO merchantFeeAssign(RequestDTO requestDTO) {
		logger.debug("Inside  MerchantFeeAssign.. ");
		Connection connection = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceResultSet = null;

		HashMap<String, Object> serviceDataMap = null;
		JSONObject json = null;
		JSONArray feeJsonArray = null;
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			serviceDataMap = new HashMap<String, Object>();
			json = new JSONObject();

			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			String serviceQry = "SELECT DISTINCT MERCHANT_ID,MERCHANT_NAME FROM MERCHANT_MASTER";

			servicePstmt = connection.prepareStatement(serviceQry);
			serviceResultSet = servicePstmt.executeQuery();
			json.put("ALL", "ALL");
			while (serviceResultSet.next()) {
				json.put(
						serviceResultSet.getString(1),
						serviceResultSet.getString(1) + "-"
								+ serviceResultSet.getString(2));

			}

			responseJSON.put("MERCHANT_DATA", json);

			json.clear();

			logger.debug("Merchant details fetch done.");

			servicePstmt.close();
			serviceResultSet.close();
			serviceQry = null;

			serviceQry = "SELECT DISTINCT PROCESS_CODE,PROCESS_NAME FROM PROCESS_MASTER";

			servicePstmt = connection.prepareStatement(serviceQry);
			serviceResultSet = servicePstmt.executeQuery();

			while (serviceResultSet.next()) {
				json.put(
						serviceResultSet.getString(1),
						serviceResultSet.getString(1) + "-"
								+ serviceResultSet.getString(2));
			}

			responseJSON.put("TXN_LIST", json);
			json.clear();

			servicePstmt.close();
			serviceResultSet.close();
			serviceQry = null;
			feeJsonArray = new JSONArray();
			serviceQry = "SELECT DISTINCT FEE_CODE,SERVICE_TXN_CODE||'-'||FEE_NAME FROM FEE_MASTER";

			servicePstmt = connection.prepareStatement(serviceQry);
			serviceResultSet = servicePstmt.executeQuery();

			while (serviceResultSet.next()) {
				json.put("val", serviceResultSet.getString(1));
				json.put("key", serviceResultSet.getString(2));
				feeJsonArray.add(json);
			}

			responseJSON.put("FEE_NAMES", feeJsonArray);

			logger.debug("Transaction details fetch done.");

			serviceDataMap.put("LIST_MERCHANT_DETAILS", responseJSON);
			logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);
		} catch (SQLException e) {
			logger.debug("SQLException in MerchantFeeAssign [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in MerchantFeeAssign  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(servicePstmt);
			DBUtils.closeResultSet(serviceResultSet);
			json = null;

		}
		return responseDTO;
	}

	public ResponseDTO merchantFeeAssignAck(RequestDTO requestDTO) {

		logger.debug("Inside  MerchantFeeAssignAck.. ");
		Connection connection = null;
		requestJSON = requestDTO.getRequestJSON();
		String merchantID = requestJSON.getString("merch_id");
		String userQry = "";

		logger.debug("MERHANT ID : " + merchantID);

		if (merchantID.trim().equalsIgnoreCase("ALL")) {
			logger.debug("[merchantFeeAssignAck][MERCHANT ID IN ALL : "
					+ merchantID + "]");
			userQry = "{call MERCHANTFEEINSERTIONALL(?,?,?,?,?)}";
		} else {
			logger.debug("[merchantFeeAssignAck][MERCHANT ID IN : "
					+ merchantID + "]");
			userQry = "{call MERCHANTFEEINSERTION(?,?,?,?,?)}";
		}

		String error = "";

		CallableStatement callable = null;

		try {
			responseDTO = new ResponseDTO();

			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callable.setString(2, requestJSON.getString("merch_id"));
			// callable.setString(3, requestJSON.getString("txn_type"));
			callable.setString(3, requestJSON.getString("selected_users"));
			callable.registerOutParameter(4, OracleTypes.VARCHAR);
			callable.setString(5, requestJSON.getString(CevaCommonConstants.IP));
			callable.execute();
			error = callable.getString(4);

			logger.debug("Authorization ACK block executed successfully "
					+ "with error_message[" + error + "]");

			if (error.equals("SUCCESS")) {
			} else {
				responseDTO.addError(error);
			}

		} catch (Exception e) {
			logger.debug("SQLException in MerchantFeeAssignAck ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} /*
		 * catch (Exception e) {
		 * logger.debug("Exception in MerchantFeeAssignAck  [" + e.getMessage()
		 * + "]"); responseDTO.addError("Internal Error Occured.");
		 * e.printStackTrace(); }
		 */finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			userQry = null;
		}
		return responseDTO;

	}

	public ResponseDTO UpdateFeeDetails(RequestDTO requestDTO) {

		logger.debug("Update FeeSlabDetails..... ");
		Connection connection = null;

		String userQry = "{call UPDATEFEESLABDETAILSPROC(?,?,?,?,?,?,?,?,?,?)}";

		String error = "";

		CallableStatement callable = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callable = connection.prepareCall(userQry);
			callable.setString(1, requestJSON.getString("MAKER_ID"));
			callable.setString(2, requestJSON.getString("serviceCode"));
			callable.setString(3, requestJSON.getString("subServiceCode"));
			callable.setString(4, requestJSON.getString("FeeCodeslab"));
			callable.setString(5, requestJSON.getString("serviceType"));
			callable.setString(6, requestJSON.getString("multiSlabFeeDetails"));
			callable.setString(7, requestJSON.getString("acqdetails"));
			callable.registerOutParameter(8, OracleTypes.INTEGER);
			callable.setString(9, requestJSON.getString("feename"));
			callable.setString(10,
					requestJSON.getString(CevaCommonConstants.IP));
			callable.execute();
			error = callable.getString(8);

			logger.debug("Update Fee Details successfully "
					+ "with error_message[" + error + "]");

			if (error.equals("SUCCESS")) {
			} else {
				responseDTO.addError(error);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in UpdateFee Ack [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in MerchantFeeAssignAck  ["
					+ e.getMessage() + "]");
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
			userQry = null;
		}
		return responseDTO;

	}

	public ResponseDTO getDetails(RequestDTO requestDTO) {

		logger.debug("inside GetDetails.. ");

		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		responseJSON = new JSONObject();

		HashMap<String, Object> bankdataMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray merchantJSONArray = null;

		PreparedStatement Pstmt = null;
		ResultSet merchantfeeRS = null;

		Connection connection = null;

		String bankdet = "select DISTINCT MF.MERCHANT_ID,MF.MAKERID,MF.MAKERDTTM,MF.TXN_TYPE,MF.FEE_CODE,FM.FEE_NAME from MERCHANT_FEE MF,FEE_MASTER FM where MERCHANT_ID=?";

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

	public ResponseDTO GetMerchantAssignService(RequestDTO requestDTO) {

		logger.debug("Inside GetBankDashBoard... ");
		HashMap<String, Object> bankMap = null;
		JSONObject resultJson = null;
		JSONArray MerchantJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		Connection connection = null;

		String merchantQry = "Select MERCHANT_ID,decode(TXN_TYPE,'WDL','CASH WITHDRAWAL','CDP','CASH DEPOSIT','BEQ','BALANCE ENQUARY','PUR','PURCHASE'),"
				+ " FEE_CODE,MAKERID,MAKERDTTM from merchant_fee ";

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			bankMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			MerchantJSONArray = new JSONArray();

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put("Merchant_id", merchantRS.getString(1));
				json.put("TransactionType", merchantRS.getString(2));
				json.put("Feecode", merchantRS.getString(3));
				json.put("Makerid", merchantRS.getString(4));
				json.put("makerDttm", merchantRS.getString(5));

				MerchantJSONArray.add(json);
				json.clear();
				json = null;
			}
			logger.debug("MerchantJSONArray [" + MerchantJSONArray + "]");
			resultJson.put("MERCHANT_ASSIGN_DASHBOARD", MerchantJSONArray);

			bankMap.put("MERCHANT_ASSIGN_DASHBOARD", resultJson);
			logger.debug("bankMap [" + bankMap + "]");
			responseDTO.setData(bankMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetMerchant ID DashBoard ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetFeeDashBoard [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			bankMap = null;
			resultJson = null;
			MerchantJSONArray = null;
			try {
				if (merchantPstmt != null)
					merchantPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

}
