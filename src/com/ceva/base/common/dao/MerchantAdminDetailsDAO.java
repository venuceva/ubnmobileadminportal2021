package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;

public class MerchantAdminDetailsDAO {

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	private Logger logger = Logger.getLogger(MerchantAdminDetailsDAO.class);

	public ResponseDTO getMerchantAdminDetails(RequestDTO requestDTO) {

		HashMap<String, Object> resultMap = null;
		Connection connection = null;

		logger.debug("Inside MerchantAdminDetailsDAO...");

		String adminQry = "";

		PreparedStatement adminPstmt = null;
		ResultSet adminRS = null;
		ResultSet storeRS = null;
		String userName = null;
		String userStatus = null;
		String email = null;
		String employeeNo = null;

		int merchCount = 0;
		try {

			responseDTO = new ResponseDTO();
			requestJSON = new JSONObject();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null+"]");
			adminQry = "select count(*)  from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where ULC.COMMON_ID=UI.COMMON_ID and "
					+ "UI.USER_STATUS = 'A' and UI.USER_TYPE='MA' and UI.MERCHANT_ID is null and ULC.LOGIN_USER_ID=trim(?)";

			adminPstmt = connection.prepareStatement(adminQry);
			adminPstmt.setString(1, requestJSON.getString("MERCHANT_ADMIN"));
			adminRS = adminPstmt.executeQuery();

			if (adminRS.next()) {
				merchCount = adminRS.getInt(1);
			}

			adminQry = "";
			adminPstmt.close();
			adminRS.close();

			if (merchCount == 0) {

				adminQry = "select USER_NAME,decode(USER_STATUS,'F','First Time Login','A','Active','B','Blocked',USER_STATUS),EMAIL,EMP_ID "
						+ " from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC "
						+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=trim(?)";

				adminPstmt = connection.prepareStatement(adminQry);
				adminPstmt
						.setString(1, requestJSON.getString("MERCHANT_ADMIN"));
				adminRS = adminPstmt.executeQuery();

				if (adminRS.next()) {
					userName = adminRS.getString(1);
					userStatus = adminRS.getString(2);
					email = adminRS.getString(3);
					employeeNo = adminRS.getString(4);
				}

				responseJSON.put(CevaCommonConstants.USER_NAME, userName);
				responseJSON.put(CevaCommonConstants.USER_STATUS, userStatus);
				responseJSON.put(CevaCommonConstants.EMAIL_ID, email);
				responseJSON.put(CevaCommonConstants.EMPLOYEE_NO, employeeNo);

				resultMap.put("ADMIN_INFO", responseJSON);
				responseDTO.setData(resultMap);
			} else {
				responseDTO.addError("Merchant already assigned.");
			}

		} catch (SQLException e) {
			logger.debug("Exception in MerchantAdminDetailsDAO ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {
				if (adminPstmt != null)
					adminPstmt.close();
				if (adminRS != null)
					adminRS.close();
				if (storeRS != null)
					storeRS.close();

				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return responseDTO;
	}

}
