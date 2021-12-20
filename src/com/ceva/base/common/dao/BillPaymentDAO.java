package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.EncryptTransactionPin;
import com.ceva.util.DBUtils;

public class BillPaymentDAO {

	private Logger logger = Logger.getLogger(BillPaymentDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;

	public ResponseDTO BillPaymentCreateDetails(RequestDTO requestDTO) {

		logger.debug("Inside BillPaymentDAO ... ");
		HashMap<String, Object> billerMap = null;

		Connection connection = null;
		PreparedStatement billerPstmt = null;
		ResultSet billerRS = null;

		JSONArray billerJSONArray = null;
		String billerQry = "";
		JSONObject json = null;

		try {
			responseDTO = new ResponseDTO();
			resonseJSON = new JSONObject();
			
			requestJSON = requestDTO.getRequestJSON();

			billerJSONArray = new JSONArray();
			billerMap = new HashMap<String, Object>();

			billerQry = "Select MERCHANT_ID,MERCHANT_NAME from "
					+ "MERCHANT_MASTER where AGEN_OR_BILLER=? order by MERCHANT_ID";

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			billerPstmt = connection.prepareStatement(billerQry);
			billerPstmt.setString(1, CevaCommonConstants.BILLERTYPE);
			billerRS = billerPstmt.executeQuery();

			while (billerRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, billerRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, billerRS.getString(1));
				billerJSONArray.add(json);
				json.clear(); json = null;
			}
			
			logger.debug("BillerJSONArray [" + billerJSONArray + "]");

			resonseJSON.put(CevaCommonConstants.BILLER_LIST, billerJSONArray);

			billerMap.put(CevaCommonConstants.BILLER_LIST, resonseJSON);

			logger.debug("BillerMap [" + billerMap + "]");
			responseDTO.setData(billerMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in BillPaymentCreateDetails["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in BillPaymentCreateDetails ["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(billerRS);
			DBUtils.closePreparedStatement(billerPstmt);
			billerJSONArray = null;
			billerQry = null;
			json = null;
			billerMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO billPayVerifyPin(RequestDTO requestDTO) {

		logger.debug("Inside BillPayVerifyPin... ");

		Connection connection = null;
		PreparedStatement billerPstmt = null;
		ResultSet billerRS = null;

		int resCount = 0;
		String cryptedPassword = "";
		String key1 = "";

		String billerQry = "";
		try {
			responseDTO = new ResponseDTO();
 			requestJSON = requestDTO.getRequestJSON();

			key1 = "97206B46CE46376894703ECE161F31F2";
			logger.debug("Before Enryption Value is  ["
					+ requestJSON.getString(CevaCommonConstants.PIN) + "]");

			try {
				cryptedPassword = EncryptTransactionPin.encrypt(key1,
						requestJSON.getString(CevaCommonConstants.PIN), 'F');

			} catch (Exception e) {
				logger.debug("Exception in encrypting password cryptedPassword ["
						+ cryptedPassword + "] message[" + e.getMessage() + "]");
				cryptedPassword = "";
			}

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			billerQry = "Select COUNT(*) from USER_LOGIN_CREDENTIALS  where PIN=?";
			billerPstmt = connection.prepareStatement(billerQry);
			billerPstmt.setString(1, cryptedPassword);
			billerRS = billerPstmt.executeQuery();

			if (billerRS.next()) {
				resCount = billerRS.getInt(1);
			}

			if (resCount == 1) {
				responseDTO.addMessages("Pin Verification Succss");
			} else {
				responseDTO.addError("Pin Verification Failed");
			}

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in BillPayVerifyPin [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in BillPayVerifyPin [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(billerRS);
			DBUtils.closePreparedStatement(billerPstmt);
			cryptedPassword = null;
			key1 = null;
			billerQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertBillDetails(RequestDTO requestDTO) {
		logger.debug("Inside InsertBillDetails DAO ... ");

		CallableStatement callableStatement = null;
		Connection connection = null;

		String mobileNo = null;
		String refNo = "";
		String insertBillDetailsProc = "";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");
			if (requestJSON.containsKey(CevaCommonConstants.MOBILE_NUMBER)) {
				mobileNo = requestJSON
						.getString(CevaCommonConstants.MOBILE_NUMBER);
			}

			logger.debug("Mobile No [" + mobileNo + "]");

			insertBillDetailsProc = "{call insertBillDetailsProc(?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(insertBillDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.BILLER_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.CUSTOMER_KEY));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.MODE_OF_PAYMENT));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.AMOUNT));
			callableStatement.setString(6, mobileNo);
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(8, java.sql.Types.INTEGER);

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(8);
			refNo = callableStatement.getString(7);
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO
						.addMessages("Bill paid successfully with reference number :: "
								+ refNo);
			} else if (resCnt == -1) {
				responseDTO.addError("Bill already available. ");
			} else {
				responseDTO.addError("Bill information insertion failed.");
			}

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in InsertBillDetails [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in InsertBillDetails [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			mobileNo = null;
			refNo = null;
			insertBillDetailsProc = null;
		}
		return responseDTO;
	}

	public ResponseDTO getMPesaDashBoard(RequestDTO requestDTO) {

		logger.debug("Inside GetMPesaDashBoard.. ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJSONArray = null;

		PreparedStatement merchantPstmt = null;

		ResultSet merchantRS = null;
		Connection connection = null;

		String merchantQry = "";
		try {

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJSONArray = new JSONArray();
			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantQry = "SELECT REFERENCE_NO,KEY_ID,BILLER_ID,PAYMENT_AMOUNT,"
					+ "decode(PAYMENT_STATUS,'P','Pending','S','Success',PAYMENT_STATUS),"
					+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS')  "
					+ "from BILL_PAYMENT_MASTER where PAYMENT_MODE=?";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1, "MPESA");
			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.REFERENCE_NO,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.CUSTOMER_KEY,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.BILLER_ID, merchantRS.getString(3));
				json.put(CevaCommonConstants.AMOUNT, merchantRS.getString(4));
				json.put(CevaCommonConstants.STATUS, merchantRS.getString(5));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(6));
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
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  GetMPesaDashBoard ["
					+ e.getMessage() + "]");
		} catch (Exception e) {

			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in  GetMPesaDashBoard [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);

			merchantMap = null;
			resultJson = null;
			merchantJSONArray = null;
			merchantQry = null;
		}
		return responseDTO;
	}
}
