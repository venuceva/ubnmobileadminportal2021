package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class BillerManagementDAO {

	private Logger logger = Logger.getLogger(BillerManagementDAO.class);
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	public ResponseDTO getBillerRelatedInfo(RequestDTO requestDTO) {

		logger.debug("Inside BillerCategoryPstmt DAO ... ");
		HashMap<String, Object> billerDataMap = null;
		JSONObject resultJson = null;
		JSONArray billerCategoryJSONArray = null;

		PreparedStatement billerCategoryPstmt = null;
		ResultSet billerCategoryRS = null;
		Connection connection = null;

		String billerCategoryQry = "";
		try {
			billerDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			billerCategoryJSONArray = new JSONArray(); 
			responseDTO = new ResponseDTO(); 
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			billerCategoryQry = "Select KEY_VALUE from CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=? order by KEY_ID";

			billerCategoryPstmt = connection
					.prepareStatement(billerCategoryQry);
			billerCategoryPstmt.setString(1, CevaCommonConstants.KEY_BCL_GROUP);
			billerCategoryPstmt.setString(2, CevaCommonConstants.KEY_BCL_TYPE);
			billerCategoryRS = billerCategoryPstmt.executeQuery();
			JSONObject json = null;
			while (billerCategoryRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						billerCategoryRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL,
						billerCategoryRS.getString(1));
				billerCategoryJSONArray.add(json);
				json.clear();
				json = null; 
			}
			logger.debug("BillerCategoryJSONArray [" + billerCategoryJSONArray
					+ "]");

			resultJson.put(CevaCommonConstants.BILLER_CATEGORY_LIST,
					billerCategoryJSONArray);

			billerDataMap.put(CevaCommonConstants.BILLER_REL_INFO, resultJson);
			logger.debug("BillerDataMap [" + billerDataMap + "]");

			responseDTO.setData(billerDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in getBillerRelatedInfo ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in getBillerRelatedInfo [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(billerCategoryPstmt);
			DBUtils.closeResultSet(billerCategoryRS);
			billerDataMap = null;
			resultJson = null;
			billerCategoryJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO getBillerCustomerData(RequestDTO requestDTO) {

		logger.debug("Inside GetBillerCustomerData DAO ... ");

		JSONArray jsonArray = null;

		String id = null;
		String dataVal = null;
		String tableName = null;
		String tableColumns = null;

		String query = null;

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			billerDataMap = new HashMap<String, Object>();
			jsonArray = new JSONArray();

			id = requestJSON.getString(CevaCommonConstants.ID);
			dataVal = requestJSON.getString(CevaCommonConstants.INPUT_DATA);
			tableName = requestJSON.getString(CevaCommonConstants.TABLE_NAME);
			tableColumns = requestJSON
					.getString(CevaCommonConstants.TABLE_COLUMNS);

			query = "select " + tableColumns + " from " + tableName
					+ " where upper(" + id + ") like upper(?)";
			logger.debug("Query is [" + query + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dataVal + "%");
			rs = pstmt.executeQuery();
			JSONObject json = null;
			while (rs.next()) {
				json = new JSONObject();
				json.put("ACCT_NO", rs.getString(1));
				json.put("CUSTOMER_NAME", rs.getString(2));
				json.put("EMAIL", rs.getString(3));
				json.put("MAKER_ID", rs.getString(4));
				json.put("MAKER_DATE", rs.getString(5));
				jsonArray.add(json);
				json.clear();
				json = null;
			}
			responseJSON.put("CUSTOMER_DATA", jsonArray);
			billerDataMap.put("CUSTOMER_DATA", responseJSON);
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in GetBillerCustomerData ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in GetBillerCustomerData ["
					+ e.getMessage() + "]");
		} finally {
			jsonArray = null;
			id = null;
			dataVal = null;
			tableName = null;
			tableColumns = null;
			query = null;
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeResultSet(rs);
			billerDataMap = null;
		}

		return responseDTO;
	}

}
