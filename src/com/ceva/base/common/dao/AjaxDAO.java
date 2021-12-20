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

public class AjaxDAO {

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;

	private Logger logger = Logger.getLogger(AjaxDAO.class);

	public ResponseDTO checkMerchantId(RequestDTO requestDTO) {
		logger.debug("Inside AjaxDAO... ");

		Connection connection = null;

		HashMap<String, Object> merchantDataMap = null;
		JSONObject resultJson = null;

		String merchantId = "";

		PreparedStatement merchantChkPstmt = null;
		ResultSet merchantChkRS = null;
		int ResCount = 0;
		String merchantChkQry = "";
		try {
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			merchantChkQry = "Select count(*) from MERCHANT_MASTER where MERCHANT_ID =?";

			resultJson = new JSONObject();
			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			merchantDataMap = new HashMap<String, Object>();
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);

			merchantChkPstmt = connection.prepareStatement(merchantChkQry);
			merchantChkPstmt.setString(1, merchantId);

			merchantChkRS = merchantChkPstmt.executeQuery();
			while (merchantChkRS.next()) {
				ResCount = merchantChkRS.getInt(1);
			}
			logger.debug("Res Count [" + ResCount + "]");

			resultJson.put(CevaCommonConstants.RESULT_COUNT, ResCount);
			merchantDataMap.put(CevaCommonConstants.MERCHANT_CHECK_INFO,
					resultJson);

			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
			logger.debug("Response DTO    [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in checkMerchantId [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in checkMerchantId [" + e.getMessage()
					+ "]");
		} finally {
			merchantDataMap = null;
			resultJson = null;
			ResCount = 0;
			merchantChkQry = null;
			merchantId = null;
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantChkPstmt);
			DBUtils.closeResultSet(merchantChkRS);
			requestDTO = null;
			requestJSON = null;
		}
		return responseDTO;
	}

	public ResponseDTO generateMerchantId(RequestDTO requestDTO) {
		logger.debug("Inside GenerateMerchantId... ");

		StringBuffer merchantId = null;
		StringBuffer storeId = null;
		String merchantName = "";
		String merchantChkQry = "";
		String merchantKey = "";

		HashMap<String, Object> merchantDataMap = null;
		JSONObject resultJson = null;

		Connection connection = null;
		ResultSet merchantChkRS = null;
		PreparedStatement merchantChkPstmt = null;
		int resCount = 0;

		try {
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = new JSONObject();
			merchantDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantChkQry = "Select count(*) from MERCHANT_MASTER where MERCHANT_NAME like ?";
			merchantName = requestJSON .getString(CevaCommonConstants.MERCHANT_NAME);
			merchantKey = merchantName.substring(0, 4);

			merchantChkPstmt = connection.prepareStatement(merchantChkQry);
			merchantChkPstmt.setString(1, merchantKey + "%");

			merchantChkRS = merchantChkPstmt.executeQuery();
			if (merchantChkRS.next()) {
				resCount = merchantChkRS.getInt(1);
			}

			logger.debug("ResCount [" + resCount + "]");

			merchantId = new StringBuffer(10);
			merchantId.append("");

			storeId = new StringBuffer(10);
			storeId.append("");

			resCount += 1;

			/*
			 * merchantId = merchantKey + "0000000000" + i; storeId =
			 * merchantKey + i + "-S000" + i;
			 */
			merchantId.append(merchantKey).append(
					StringUtils.leftPad(String.valueOf(resCount), 10, '0'));
			storeId.append(merchantKey) .append("-S")
					.append(StringUtils.leftPad(String.valueOf(resCount), 4,'0'));

			resultJson.put(CevaCommonConstants.RESULT_COUNT, resCount);
			resultJson.put(CevaCommonConstants.MERCHANT_ID, merchantId.toString().toUpperCase());
			resultJson.put(CevaCommonConstants.STORE_ID, storeId.toString());

			merchantDataMap.put(CevaCommonConstants.MERCHANT_INFO, resultJson);

			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in GenerateMerchantId ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in GenerateMerchantId [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantChkPstmt);
			DBUtils.closeResultSet(merchantChkRS);
			merchantId.delete(0, merchantId.length());
			merchantId = null;
			storeId.delete(0, storeId.length());
			storeId = null;
			merchantName = null;
			merchantChkQry = null;
			merchantKey = null;

			merchantDataMap = null;
			resultJson = null;
		}
		return responseDTO;
	}

	
	public ResponseDTO generateMvisaNumber(RequestDTO requestDTO) {
		logger.debug("Inside GenerateMvisaNumber... ");
		 
		String merchantName = "";
		String merchantChkQry = "";
		String merchantKey = "";

		HashMap<String, Object> merchantDataMap = null;
		JSONObject resultJson = null;

		Connection connection = null;
		ResultSet merchantChkRS = null;
		PreparedStatement merchantChkPstmt = null;
		int resCount = 0;

		try {
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = new JSONObject();
			merchantDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			
			merchantName = requestJSON.getString("accountNumber");
			connection = DBConnector.getConnection();
			
			logger.debug("Connection is null [" + connection == null + "]");

			merchantChkQry = "Select CARD_NUMBER FROM CARD WHERE ACCOUNT_NUMBER=?";

			merchantChkPstmt = connection.prepareStatement(merchantChkQry);
			merchantChkPstmt.setString(1, merchantName);
			
			merchantChkRS = merchantChkPstmt.executeQuery();
			
		 	 
			
			if(merchantChkRS.next()){
				
				String cardNumber= merchantChkRS.getString(1).substring(7, 13);
				logger.debug("After Substring"+ cardNumber);
				
				resultJson.put("cardNumber", cardNumber);
			}
			
			logger.debug("--------"+resultJson);

			merchantDataMap.put(CevaCommonConstants.MERCHANT_INFO, resultJson);

			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in GenerateMerchantId ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in GenerateMerchantId [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantChkPstmt);
			DBUtils.closeResultSet(merchantChkRS);
			merchantName = null;
			merchantChkQry = null;
			merchantKey = null;

			merchantDataMap = null;
			resultJson = null;
		}
		return responseDTO;
	}
	
	
	/* public ResponseDTO getTerminals(RequestDTO requestDTO) {
		logger.debug("Inside GetTerminalsDAO... ");
		Connection connection = null;

		HashMap<String, Object> terminalMap = null;
		JSONObject resultJson = null;
		JSONArray terminalJSONArray = null;

		String merchantId = "";
		String terminalQry = "";

		PreparedStatement terminalPstmt = null;
		ResultSet terminalRS = null;

		try {
			terminalMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			terminalJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			terminalQry = "Select TERMINAL_ID,STORE_ID,MERCHANT_ID,MAKER_DTTM"
					+ " from TERMINAL_MASTER  where  MERCHANT_ID=?";

			terminalPstmt = connection.prepareStatement(terminalQry);
			terminalPstmt.setString(1, merchantId);

			terminalRS = terminalPstmt.executeQuery();
			JSONObject json = null;
			while (terminalRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.TERMINAL_ID,
						terminalRS.getString(1));
				json.put(CevaCommonConstants.STORE_ID, terminalRS.getString(2));
				json.put(CevaCommonConstants.MERCHANT_ID,
						terminalRS.getString(3));
				json.put(CevaCommonConstants.MAKER_DATE,
						terminalRS.getString(4));
				terminalJSONArray.add(json);
			}
			logger.debug("Terminal JSONArray [" + terminalJSONArray + "]");
			resultJson
					.put(CevaCommonConstants.TERMINAL_LIST, terminalJSONArray);
			terminalMap.put(CevaCommonConstants.TERMINAL_LIST, resultJson);
			logger.debug("Terminal Map [" + terminalMap + "]");
			responseDTO.setData(terminalMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in getTerminals [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in getTerminals [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(terminalPstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(terminalRS);
			terminalMap = null;
			resultJson = null;
			terminalJSONArray = null;

			merchantId = null;
			terminalQry = null;
		}
		return responseDTO;
	}
	
	*/
	public ResponseDTO getTerminals(RequestDTO requestDTO) {
		logger.debug("Inside GetTerminalsDAO... ");
		Connection connection = null;

		HashMap<String, Object> terminalMap = null;
		JSONObject resultJson = null;
		JSONArray terminalJSONArray = null;

		String merchantId = "";
		String terminalQry = "";

		PreparedStatement terminalPstmt = null;
		ResultSet terminalRS = null;

		try {
			terminalMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			terminalJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			terminalQry = "Select TERMINAL_ID,STORE_ID,MERCHANT_ID,MAKER_DTTM"
					+ " from TERMINAL_MASTER  where  MERCHANT_ID=?";

			terminalPstmt = connection.prepareStatement(terminalQry);
			terminalPstmt.setString(1, merchantId);

			terminalRS = terminalPstmt.executeQuery();
			JSONObject json = null;
			while (terminalRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.TERMINAL_ID,
						terminalRS.getString(1));
				json.put(CevaCommonConstants.STORE_ID, terminalRS.getString(2));
				json.put(CevaCommonConstants.MERCHANT_ID,
						terminalRS.getString(3));
				json.put(CevaCommonConstants.MAKER_DATE,
						terminalRS.getString(4));
				terminalJSONArray.add(json);
			}
			logger.debug("Terminal JSONArray [" + terminalJSONArray + "]");
			resultJson
					.put(CevaCommonConstants.TERMINAL_LIST, terminalJSONArray);
			terminalMap.put(CevaCommonConstants.TERMINAL_LIST, resultJson);
			logger.debug("Terminal Map [" + terminalMap + "]");
			responseDTO.setData(terminalMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in getTerminals [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in getTerminals [" + e.getMessage() + "]");
		} finally {
			DBUtils.closePreparedStatement(terminalPstmt);
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(terminalRS);
			terminalMap = null;
			resultJson = null;
			terminalJSONArray = null;

			merchantId = null;
			terminalQry = null;
		}
		return responseDTO;
	}

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
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
				storeJSONArray.add(json);
			}
			
			resonseJSON.put(CevaCommonConstants.STORE_LIST, storeJSONArray);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put(CevaCommonConstants.STORE_LIST, resonseJSON);
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
	public ResponseDTO getTerminalID(RequestDTO requestDTO) {
		logger.debug("Inside GetStores DAO.. ");
		String storeId = "";
		String terminalIdQry = "Select LPAD(TERMINALID_SEQ.nextval,'8','0') from DUAL";
		String storebranchid = "Select LOCATION from STORE_MASTER WHERE STORE_ID=? ";
		String tid= "";
		String brncode="";
		PreparedStatement brncodestmt = null;
		PreparedStatement terminalIdPstmt = null;
		ResultSet brnres= null;
		ResultSet terminalIdRS= null;
		
		HashMap<String, Object> resultMap = null;
		Connection connection = null;
		
		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			
			terminalIdPstmt = connection.prepareStatement(terminalIdQry);

			terminalIdRS = terminalIdPstmt.executeQuery();
			while (terminalIdRS.next()) {
				tid = terminalIdRS.getString(1);

			}
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			logger.debug("store id in dao "+storeId);
			brncodestmt = connection.prepareStatement(storebranchid);
			brncodestmt.setString(1, storeId.trim());
			brnres = brncodestmt.executeQuery();

			if (brnres.next()) {
				brncode = brnres.getString(1);
			}

			//resonseJSON.put(CevaCommonConstants.TERMINAL_ID, brncode+""+tid);
			resonseJSON.put(CevaCommonConstants.TERMINAL_ID,tid);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put(CevaCommonConstants.STORE_LIST, resonseJSON);
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
			
			resultMap = null;
		}
		
		return responseDTO;
	}
	
	public ResponseDTO retrieveusers(RequestDTO requestDTO) {
		logger.debug("Inside GetStores DAO.. ");
		String storeId = "";
		String merchantId = "";
		String usersQry ="select A.USER_NAME,B.LOGIN_USER_ID,A.USER_STATUS,decode(A.USER_TYPE,'MA','Merchant Admin','MS','Store Admin','MU','Merchant User',A.USER_TYPE),A.MOBILE_NO from USER_INFORMATION A,USER_LOGIN_CREDENTIALS B "
				+ "where A.COMMON_ID=B.COMMON_ID and A.MERCHANT_ID=? and A.STORE_ID=? "
				+ "and A.USER_TYPE in ('MU','MS') union select A.USER_NAME,B.LOGIN_USER_ID,A.USER_STATUS,decode(A.USER_TYPE,'MA','Merchant Admin','MS','Store Admin','MU','Merchant User',A.USER_TYPE),A.MOBILE_NO "
				+ "from USER_INFORMATION A,USER_LOGIN_CREDENTIALS B where A.COMMON_ID=B.COMMON_ID "
				+ "and A.MERCHANT_ID=? and A.USER_TYPE ='MA'";
		
		//String storebranchid = "Select LOCATION from STORE_MASTER WHERE STORE_ID=? ";
		String tid= "";
		String brncode="";
		PreparedStatement brncodestmt = null;
		PreparedStatement terminalIdPstmt = null;
		ResultSet rs= null;
		ResultSet terminalIdRS= null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		
		HashMap<String, Object> resultMap = null;
		Connection connection = null;
		
		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		try {
			resonseJSON = new JSONObject();
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			
	/*		terminalIdPstmt = connection.prepareStatement(terminalIdQry);

			terminalIdRS = terminalIdPstmt.executeQuery();
			while (terminalIdRS.next()) {
				tid = terminalIdRS.getString(1);

			}*/
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			logger.debug("store id in dao "+storeId+"   Merfch"+ merchantId);
			brncodestmt = connection.prepareStatement(usersQry);
			brncodestmt.setString(1, merchantId.trim());
			brncodestmt.setString(2, storeId.trim());
			brncodestmt.setString(3, merchantId.trim());
			rs= brncodestmt.executeQuery();

			int i = 0;
			eachrow = new StringBuilder(50);
			billerData = new StringBuilder(50);
			
			while (rs.next()) {
				eachrow.append(rs.getString(1)).append(",")
						.append(rs.getString(2)).append(",")
						.append(rs.getString(3)).append(",")		
						.append(rs.getString(4)).append(",")
						.append(rs.getString(5));					
				if (i == 0) {
					billerData.append(eachrow);
				} else {
					billerData.append("#").append(eachrow);
				}
				i++;
				eachrow.delete(0, eachrow.length());
			}
			
			resonseJSON.put(CevaCommonConstants.MULTI_DATA,billerData.toString());
			//resonseJSON.put(CevaCommonConstants.TERMINAL_ID, brncode+""+tid);
			logger.debug("Response JSON [" + resonseJSON + "]");
			resultMap.put(CevaCommonConstants.STORE_LIST, resonseJSON);
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
			
			resultMap = null;
		}
		
		return responseDTO;
	}

	public ResponseDTO checkTransactionType(RequestDTO requestDTO) {

		logger.debug("Inside CheckTransactionType DAO ... ");
		Connection connection = null;

		HashMap<String, Object> UserDataMap = null;
		JSONObject resultJson = null;
		String accounttype = "";
		String bankaccount = "";

		PreparedStatement userChkPstmt = null;
		ResultSet USerChkRS = null;
		String userChkQry = "";
		int ResCount = 0;

		try {
			resultJson = new JSONObject();
			UserDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			accounttype = requestJSON.getString(CevaCommonConstants.ACCOUNT_TYPE);
			bankaccount = requestJSON.getString("BANK_ACCOUNT");
			userChkQry = "Select count(*) from FINANCIAL_MASTER where trim(ACCT_NUMBER)=trim(?) AND trim(TRANS_TYPE)=trim(?)";

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			userChkPstmt = connection.prepareStatement(userChkQry);
			userChkPstmt.setString(1, bankaccount);
			userChkPstmt.setString(2, accounttype);

			USerChkRS = userChkPstmt.executeQuery();
			if (USerChkRS.next()) {
				ResCount = USerChkRS.getInt(1);
			}

			logger.debug("ResCount [" + ResCount + "]");

			resultJson.put(CevaCommonConstants.RESULT_COUNT, ResCount);
			UserDataMap.put(CevaCommonConstants.USER_CHECK_INFO, resultJson);

			logger.debug("MerchantDataMap [" + UserDataMap + "]");
			responseDTO.setData(UserDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in  CheckTransactionType DAO ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in  CheckTransactionType DAO ["
					+ e.getMessage() + "]");
		}

		finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(userChkPstmt);
			DBUtils.closeResultSet(USerChkRS);
			UserDataMap = null;
			resultJson = null;
			accounttype = null;
			bankaccount = null;
			userChkQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO checkUserId(RequestDTO requestDTO) {
		logger.debug("Inside checkUserId DAO... ");
		JSONObject json = null;

		JSONArray userJSONArray = null;
		HashMap<String, Object> bankDataMap = null;
		JSONObject resultJson = null;

		String rrn = "";
		String userChkQry = "";

		Connection connection = null;
		PreparedStatement userChkPstmt = null;
		ResultSet USerChkRS = null;

		try {
			userChkQry = "select RRN,RESPONSECODE from LIVE_TRANLOG where RRN=?";
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			userJSONArray = new JSONArray();
			resultJson = new JSONObject();

			bankDataMap = new HashMap<String, Object>();

			rrn = requestJSON.getString(CevaCommonConstants.RRN);

			userChkPstmt = connection.prepareStatement(userChkQry);
			userChkPstmt.setString(1, rrn);
			USerChkRS = userChkPstmt.executeQuery();

			while (USerChkRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.RRN, USerChkRS.getString(1));
				json.put(CevaCommonConstants.responseCode,
						USerChkRS.getString(2));
				userJSONArray.add(json);
			}

			logger.debug("UserJSONArray [" + userJSONArray + "]");
			resultJson.put(CevaCommonConstants.RESULT_COUNT, userJSONArray);
			bankDataMap.put(CevaCommonConstants.USER_CHECK_INFO, resultJson);

			logger.debug("MerchantDataMap [" + bankDataMap + "]");
			responseDTO.setData(bankDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in CheckUserId [" + e.getMessage() + "]");
		} finally {
			requestDTO = null;
			requestJSON = null;
			json = null;
			userJSONArray = null;
			bankDataMap = null;
			resultJson = null;
			rrn = null;
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(userChkPstmt);
			DBUtils.closeResultSet(USerChkRS);
			userChkQry = null;
		}

		return responseDTO;
	}
	
	
public ResponseDTO fetchMerchUsersInfo(RequestDTO requestDTO){
		
		logger.debug("inside DAO [fetchMerchUsersInfo]");
			
		HashMap<String,Object> merchUserDataMap=new HashMap<String,Object>();
		JSONObject resultJson = null;
		String userChkQry = "";
		String merchantID = "";
		Connection connection=null;	
		JSONArray merchUserJSONArray=new JSONArray();
		PreparedStatement merchUserPstmt=null; 
		ResultSet merchUserRS=null;	
		try {
			
			userChkQry = "SELECT UI.MERCHANT_ID, ULC.LOGIN_USER_ID, UI.USER_NAME, DECODE(UI.STATUS,'A','Active'), ULC.MAKER_DTTM, ULC.MAKER_ID FROM USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC WHERE UI.COMMON_ID=ULC.COMMON_ID AND UI.MERCHANT_ID=?";
			connection=DBConnector.getConnection();
			responseDTO = new ResponseDTO();
			requestJSON =requestDTO.getRequestJSON();
			logger.debug("Inside fetchMerchUsersInfo DAO [requestJSON... "+requestJSON+"]");
			merchantID = requestJSON.getString("merchantID");
			
			resultJson = new JSONObject();
			
			merchUserPstmt=connection.prepareStatement(userChkQry);
			
			merchUserPstmt.setString(1, merchantID);
			merchUserRS=merchUserPstmt.executeQuery();
			JSONObject json=null;
		
			while (merchUserRS.next()) {
				json=new JSONObject();
				json.put("MERCH_ID", merchUserRS.getString(1));
				json.put("USER_ID",merchUserRS.getString(2));
				json.put("USER_NAME",merchUserRS.getString(3));
				json.put("STATUS",merchUserRS.getString(4));
				json.put("DATE_OF_CREATION",merchUserRS.getString(5));
				json.put("MAKER_ID",merchUserRS.getString(6));
				
				merchUserJSONArray.add(json);
			json.clear();
			}
			logger.debug("inside [fetchMerchUsersInfo][userlevelJSONArray:::"+merchUserJSONArray+"]");
			
			logger.debug("inside [fetchMerchUsersInfo][resultJson:::"+resultJson+"]");

    		resultJson.put("MERCH_USER_LIST", merchUserJSONArray);
    		merchUserDataMap.put("MERCH_USER_LIST", resultJson);

			logger.debug("inside [fetchMerchUsersInfo][regionDataMap:::"+merchUserDataMap+"]");
			responseDTO.setData(merchUserDataMap);
			logger.debug("inside [fetchMerchUsersInfo][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			e.printStackTrace();	 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchUserPstmt);
			DBUtils.closeResultSet(merchUserRS);
		}
		return responseDTO;	
	}	

public ResponseDTO checkSerialNumber(RequestDTO requestDTO) {
	logger.debug("Inside AjaxDAO... ");

	Connection connection = null;

	HashMap<String, Object> merchantDataMap = null;
	JSONObject resultJson = null;

	String serialnumber = "";

	PreparedStatement merchantChkPstmt = null;
	ResultSet merchantChkRS = null;
	int ResCount = 0;
	String merchantChkQry = "";
	try {
		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		merchantChkQry = "Select count(*) from TERMINAL_MASTER where SERIAL_NO =?";

		resultJson = new JSONObject();
		responseDTO = new ResponseDTO();

		connection = DBConnector.getConnection();
		logger.debug("Connection is null [" + connection == null + "]");
		merchantDataMap = new HashMap<String, Object>();
		serialnumber = requestJSON.getString("SERIAL_NUMBER");

		merchantChkPstmt = connection.prepareStatement(merchantChkQry);
		merchantChkPstmt.setString(1, serialnumber);

		merchantChkRS = merchantChkPstmt.executeQuery();
		while (merchantChkRS.next()) {
			ResCount = merchantChkRS.getInt(1);
		}
		logger.debug("Res Count [" + ResCount + "]");

		resultJson.put("RESULT_COUNT", ResCount);
		merchantDataMap.put("SERIAL_CHECK_INFO",resultJson);

		logger.debug("MerchantDataMap [" + merchantDataMap + "]");
		responseDTO.setData(merchantDataMap);
		logger.debug("Response DTO    [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");

		logger.debug("SQLException in checkMerchantId [" + e.getMessage()
				+ "]");
	} catch (Exception e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in checkMerchantId [" + e.getMessage()
				+ "]");
	} finally {
		merchantDataMap = null;
		resultJson = null;
		ResCount = 0;
		merchantChkQry = null;
		serialnumber = null;
		DBUtils.closeConnection(connection);
		DBUtils.closePreparedStatement(merchantChkPstmt);
		DBUtils.closeResultSet(merchantChkRS);
		requestDTO = null;
		requestJSON = null;
	}
	return responseDTO;
}
	
}
