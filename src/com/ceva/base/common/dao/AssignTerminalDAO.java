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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class AssignTerminalDAO {

	Logger logger = Logger.getLogger(AssignTerminalDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	
	public ResponseDTO getmerchantidDetails(RequestDTO requestDTO) {

		logger.debug("Inside getmerchantidDetails... ");
		HashMap<String, Object> merchantIdDataMap = null;

		JSONObject resultJson = null;


		JSONArray merchantJSONArray = null;
		JSONArray UserSONArray = null;
		Connection connection = null;
		PreparedStatement MerchantPstmt = null;
		PreparedStatement UserPstmt = null;
	
		ResultSet merchanttRS = null;
		ResultSet UserRS = null;
	
		JSONObject json = null;
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

	
			merchantJSONArray = new JSONArray();
			UserSONArray = new JSONArray();
			
			resultJson = new JSONObject();

			merchantIdDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");
		
			String MerchantQry = "select MERCHANT_NAME,MERCHANT_ID from MERCHANT_MASTER";
			MerchantPstmt = connection.prepareStatement(MerchantQry);
			merchanttRS = MerchantPstmt.executeQuery();
			json = new JSONObject();
			while (merchanttRS.next()) {

				json.put("key",merchanttRS.getString(1));
				json.put("val",merchanttRS.getString(2));
				merchantJSONArray.add(json);
				json.clear();

			}
			resultJson.put("MERCHANT_ID",merchantJSONArray);

			String AssignUsersList="Select MERCHANT_ID,STORE_ID,TERMINAL_ID,ASSIGN_USER,MAKER_ID,MAKER_DTTM from assign_terminal_users";
			
			UserPstmt = connection.prepareStatement(AssignUsersList);
			UserRS = UserPstmt.executeQuery();
			
			while (UserRS.next()) {
				
				json.put("merchantID",UserRS.getString(1));
				json.put("storeID",UserRS.getString(2));
				json.put("terminalID",UserRS.getString(3));
				json.put("Userslist",UserRS.getString(4));
				json.put("Maker_id", UserRS.getString(5));
				json.put("Maker_Dttm", UserRS.getString(6));
				UserSONArray.add(json);
				json.clear();
			//	json = null;
			}
			logger.debug("UserSONArray>>>>>>>>>>> [" + UserSONArray + "]");
			resultJson.put("USER_DETAILS",UserSONArray);
			merchantIdDataMap.put("MERCHANT_ID", resultJson);
			logger.debug("merchantIdDataMap [" + merchantIdDataMap + "]");
			responseDTO.setData(merchantIdDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in MerchantCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in MerchantCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

	

			resultJson = null;

		
			merchantJSONArray = null;

			try {

				if (MerchantPstmt != null)
					MerchantPstmt.close();
			

				if (merchanttRS != null)
					merchanttRS.close();
			

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}
	
	public ResponseDTO InsertAssignUserDetails(RequestDTO requestDTO) {

		logger.debug("Inside InsertAssignTerminalUsers... ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertServiceProc = "{call INSERT_ASSIGNTERMINALUSER(?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,	requestJSON.getString("merchantID"));
			callableStatement.setString(2,	requestJSON.getString("storeID"));
			callableStatement.setString(3,	requestJSON.getString("terminalID"));
			callableStatement.setString(4, requestJSON.getString("assignUsers"));
			callableStatement.setString(5,	requestJSON.getString("MAKER_ID"));
			callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(7);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO
						.addMessages("Assign Terminal Users Stored Successfully. ");
			} else if (resCnt == -1) {
				responseDTO.addError("Assign Terminal User already available. ");
			} else {
				responseDTO.addError("Assign Terminal Users Insertion Failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in  ["	+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in  ["	+ e.getMessage() + "]");
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

}
