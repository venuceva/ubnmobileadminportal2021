package com.ceva.base.common.dao;

import java.sql.CallableStatement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;
 

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.EncryptTransactionPin;
import com.ceva.util.DBUtils;
 

import org.apache.commons.lang3.StringUtils;

public class MerchantDAO {

	private Logger logger = Logger.getLogger(MerchantDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	public ResponseDTO getMerchantDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside GetMerchantDetails.. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONObject terminalJSON = null;

		JSONArray merchantJsonArray = null;

		ArrayList<String> merchatArray = null;
		ArrayList<String> storeArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;

		String merchantQry = "Select MERCHANT_ID,MERCHANT_NAME,"
				+ "Decode(STATUS,'A','Active','B','Inactive','N','Un-Authorize'),"
				+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') "
				+ "from MERCHANT_MASTER order by MERCHANT_ID";
		try {

			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJsonArray = new JSONArray();
			terminalJSON = new JSONObject();

			merchatArray = new ArrayList<String>();
			storeArray = new ArrayList<String>();

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();
			while (merchantRS.next()) {

				json.put(CevaCommonConstants.MERCHANT_ID,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.MERCHANT_NAME,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.STATUS, merchantRS.getString(3));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(4));
				merchatArray.add(merchantRS.getString(1));
				merchantJsonArray.add(json);
				json.clear();
			}

			merchantRS.close();
			merchantPstmt.close();

			resultJson
					.put(CevaCommonConstants.MERCHANT_LIST, merchantJsonArray);

			merchantJsonArray.clear();

			for (int i = 0; i < merchatArray.size(); i++) {
				String storeQry = "Select SM.STORE_ID,SM.STORE_NAME,MM.MERCHANT_ID,MM.MERCHANT_NAME,"
						+ "Decode(SM.STATUS,'A','Active','B','Inactive','N','Un-Authorize'),to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS')"
						+ " from MERCHANT_MASTER MM,STORE_MASTER SM where SM.MERCHANT_ID=MM.MERCHANT_ID and MM.MERCHANT_ID=?  order by SM.STORE_ID";

				merchantPstmt = connection.prepareStatement(storeQry);
				merchantPstmt.setString(1, merchatArray.get(i));
				merchantRS = merchantPstmt.executeQuery();
				json.clear();

				while (merchantRS.next()) {
					json.put(CevaCommonConstants.STORE_ID,
							merchantRS.getString(1));
					json.put(CevaCommonConstants.STORE_NAME,
							merchantRS.getString(2));
					json.put(CevaCommonConstants.MERCHANT_ID,
							merchantRS.getString(3));
					json.put(CevaCommonConstants.MERCHANT_NAME,
							merchantRS.getString(4));
					json.put(CevaCommonConstants.STATUS,
							merchantRS.getString(5));
					json.put(CevaCommonConstants.MAKER_DATE,
							merchantRS.getString(6));
					storeArray.add(merchantRS.getString(1));
					merchantJsonArray.add(json);
					json.clear();
				}

				if (merchatArray != null && merchatArray.size() > 0) {
					resultJson.put(merchatArray.get(i) + "_STORES",
							merchantJsonArray);
					merchantJsonArray.clear();
				}

				merchantPstmt.close();
				merchantRS.close();
			}

			json.clear();
/*			String terminalQry = "Select TERMINAL_ID,STORE_ID,MERCHANT_ID,Decode(STATUS,'A','Active','B','Inactive','D','Deactive'),"
					+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),"
					+ "SERIAL_NO from TERMINAL_MASTER  where trim(STORE_ID)=trim(?)";
*/			
			String terminalQry = "select B.LOGIN_USER_ID,A.USER_NAME,A.USER_STATUS,decode(A.USER_TYPE,'MU','Merchant User','MS','Merchant Supervisor','MA','Merchant Admin'),"
					+ "A.EMAIL,A.MOBILE_NO,A.MERCHANT_ID,A.STORE_ID,to_char(A.MAKERDTTM,'DD-MM-YYYY HH24:MI:SS'),Decode(A.STATUS,'A','Active','B','Inactive','D','Deactive'),A.USER_GROUPS,  (select CHANNEL_VALUE from CHANNEL_USERS C where C.COMMON_ID =A.COMMON_ID and CHANNEL_ID='Mobile') Mobile,(select CHANNEL_VALUE from CHANNEL_USERS C where C.COMMON_ID =A.COMMON_ID and CHANNEL_ID='POS') POS "
					+ "from user_information A,USER_LOGIN_CREDENTIALS B where A.COMMON_ID=B.COMMON_ID and trim(STORE_ID)=trim(?)";

			for (int i = 0; i < storeArray.size(); i++) {

				merchantPstmt = connection.prepareStatement(terminalQry);
				merchantPstmt.setString(1, storeArray.get(i));
				merchantRS = merchantPstmt.executeQuery();

				while (merchantRS.next()) {
					json.put(CevaCommonConstants.TERMINAL_ID,
							merchantRS.getString(1));
					json.put(CevaCommonConstants.STORE_ID,
							merchantRS.getString(8));
					json.put(CevaCommonConstants.MERCHANT_ID,
							merchantRS.getString(7));
					json.put(CevaCommonConstants.STATUS,
							merchantRS.getString(10));
					json.put(CevaCommonConstants.MAKER_DATE,
							merchantRS.getString(9));
					json.put("group_id",merchantRS.getString(11));
					json.put("serialNo", merchantRS.getString(2));
					json.put("mobileno", merchantRS.getString(12));
					json.put("posno", merchantRS.getString(13));
					merchantJsonArray.add(json);
					json.clear();
				}

				merchantPstmt.close();
				merchantRS.close();

				if (merchatArray != null && merchatArray.size() > 0) {
					terminalJSON.put(storeArray.get(i) + "_TERMINALS",
							merchantJsonArray);
					merchantJsonArray.clear();
				}

			}

			merchantMap.put(CevaCommonConstants.MERCHANT_LIST, resultJson);
			merchantMap.put(CevaCommonConstants.TERMINAL_DATA, terminalJSON);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in GetMerchantDetails ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			resultJson = null;
			merchantJsonArray = null;

			terminalJSON = null;

			merchatArray = null;
			storeArray = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO getTerminalDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside GetMerchantDetails.. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;

		JSONArray merchantJsonArray = null;

		ArrayList<String> merchatArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;

		String merchantQry = "Select TERMINAL_ID,STORE_ID,MERCHANT_ID,Decode(STATUS,'A','Active','B','Inactive','D','Deactive'),"
					+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),SERIAL_NO,(select login_user_id from user_login_credentials where "
					+ " common_id in ( select common_id from CHANNEL_USERS C where C.CHANNEL_VALUE=T.TERMINAL_ID)) USER_ID from TERMINAL_MASTER T ";
		try {

			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJsonArray = new JSONArray();

			merchatArray = new ArrayList<String>();

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();
			while (merchantRS.next()) {

				json.put(CevaCommonConstants.TERMINAL_ID,merchantRS.getString(1));
				json.put(CevaCommonConstants.STORE_ID,merchantRS.getString(2));
				json.put(CevaCommonConstants.MERCHANT_ID,merchantRS.getString(3));
				json.put(CevaCommonConstants.STATUS,merchantRS.getString(4));
				json.put(CevaCommonConstants.MAKER_DATE,merchantRS.getString(5));
				json.put("serialNo", merchantRS.getString(6));
				json.put(CevaCommonConstants.USER_ID, merchantRS.getString(7));
				merchatArray.add(merchantRS.getString(1));
				merchantJsonArray.add(json);
				json.clear();
			}

			merchantRS.close();
			merchantPstmt.close();
			
			
			
			resultJson.put(CevaCommonConstants.TERMINAL_LIST, merchantJsonArray);
			merchantMap.put(CevaCommonConstants.TERMINAL_LIST, resultJson);
			merchantJsonArray.clear();
			
			
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in GetTerminalDetails ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			resultJson = null;
			merchantJsonArray = null;


			merchatArray = null;
		}
		return responseDTO;
	}

	public ResponseDTO getMerchantCreatePageInfo(RequestDTO requestDTO) {

		logger.debug("Inside GetMerchantCreatePageInfo.. ");
		HashMap<String, Object> merchantDataMap = null;

		JSONObject resultJson = null;

		PreparedStatement locationPstmt = null;
		ResultSet locationRS = null;

		Connection connection = null;

		String entityQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null "
				+ "order by to_number(OFFICE_CODE)";
		JSONObject json = null;
		try {
			responseDTO = new ResponseDTO();

			merchantDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			locationPstmt = connection.prepareStatement(entityQry);

			locationRS = locationPstmt.executeQuery();
			json = new JSONObject();

			while (locationRS.next()) {
				json.put(locationRS.getString(2), locationRS.getString(2));
			}

			resultJson.put(CevaCommonConstants.LOCATION_LIST, json);
			json.clear();

			locationPstmt.close();
			locationRS.close();

			String acctCatQry = "Select KEY_VALUE from CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=? order by KEY_VALUE";
			locationPstmt = connection.prepareStatement(acctCatQry);
			locationPstmt.setString(1, "AGENCY");
			locationPstmt.setString(2, "AGENCY_ACCT_CAT");

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(1));
			}

			resultJson.put(CevaCommonConstants.ACCOUNT_CATEGORYS, json);

			json.clear();
			locationPstmt.close();
			locationRS.close();

			String transferCodeQry = "Select KEY_VALUE from CONFIG_DATA where KEY_GROUP='AGENCY' and KEY_TYPE='AGENCY_TRANSFER_CODE'";
			locationPstmt = connection.prepareStatement(transferCodeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(1));
			}
			resultJson.put(CevaCommonConstants.TRANSFER_CODES, json);
			json.clear();
			locationPstmt.close();
			locationRS.close();
			
			
			
			String binrangeQry = "select distinct(bin),bin||'-'||description from mob_bins_accounts";
			locationPstmt = connection.prepareStatement(binrangeQry);
			
			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));
			}
			resultJson.put(CevaCommonConstants.BIN_CODE, json);
			json.clear();
			locationPstmt.close();
			locationRS.close();

			String merchantTypeQry = "Select CATE_CODE,CATE_CODE||'-'||CATE_DESCRIPTION from CATEGORY_MASTER order by CATE_DESCRIPTION";
			locationPstmt = connection.prepareStatement(merchantTypeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));

			}
			resultJson.put(CevaCommonConstants.MERCHANT_TYPE, json);
			json.clear();
			locationPstmt.close();
			locationRS.close();
			
			
			String countryCodeQry = "select  COUNTRY_CODE||'-'||COUNTRY_NAME,COUNTRY_NAME from COUNTRY_MASTER";
			locationPstmt = connection.prepareStatement(countryCodeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));

			}
			resultJson.put("Country_List", json);
			json.clear();
			locationPstmt.close();
			locationRS.close();
			
			
			
			String countyCodeQry = "select  county_code||'-'||county_name,county_name from county_master";
			locationPstmt = connection.prepareStatement(countyCodeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));

			}
			resultJson.put("County_List", json);
			json.clear();
			locationPstmt.close();
			locationRS.close();
			
			
			
/*			String mvisaidQry = "select MOB_MVISA_ID_SEQ.nextval from dual";
			locationPstmt = connection.prepareStatement(mvisaidQry);
			String mvisaid=null;

			locationRS = locationPstmt.executeQuery();
			
			while (locationRS.next()) {
				mvisaid=locationRS.getString(1);
			}
			
			resultJson.put(CevaCommonConstants.AGENT, mvisaid);
			json.clear();
			locationPstmt.close();
			locationRS.close();*/
			
			

			String merchantAdminQry = "Select ULC.LOGIN_USER_ID,ULC.LOGIN_USER_ID||'-'||UI.USER_NAME from "
					+ "USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where UI.COMMON_ID=ULC.COMMON_ID and "
					+ "UI.USER_TYPE=? and UI.USER_STATUS=? ";

			locationPstmt = connection.prepareStatement(merchantAdminQry);
			locationPstmt.setString(1, "MA");
			locationPstmt.setString(2, "F");

			locationRS = locationPstmt.executeQuery();

			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));
			}

			resultJson.put("MERCHANT_ADMIN", json);
			json.clear();
			locationPstmt.close();
			locationRS.close();

			resultJson.put("AGEN_BILL_RAND", RandomStringUtils.randomNumeric(6));

			merchantDataMap.put(CevaCommonConstants.MERCHANT_INFO, resultJson);
			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
			merchantTypeQry = null;
			transferCodeQry = null;
			acctCatQry = null;

		} catch (SQLException e) {
			logger.debug("Got SQLException in GetMerchantCreatePageInfo ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Got Exception in 	GetMerchantCreatePageInfo ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (locationRS != null) {
					locationRS.close();
				}
				if (locationPstmt != null) {
					locationPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			merchantDataMap = null;
			resultJson = null;

		}

		return responseDTO;
	}
	

	public ResponseDTO getMerchantBackInfo(RequestDTO requestDTO) {

		logger.debug("Inside GetMerchantBackInfo.. ");
		HashMap<String, Object> merchantDataMap = null;

		JSONObject resultJson = null;

		PreparedStatement locationPstmt = null;

		ResultSet locationRS = null;

		Connection connection = null;

		String entityQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null "
				         + "order by to_number(OFFICE_CODE)";
		JSONObject json = null;
		try {
			responseDTO = new ResponseDTO();
			resultJson = new JSONObject();

			merchantDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			locationPstmt = connection.prepareStatement(entityQry);

			locationRS = locationPstmt.executeQuery();
			json = new JSONObject();

			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));
			}

			resultJson.put(CevaCommonConstants.LOCATION_LIST, json);
			json.clear();

			merchantDataMap.put(CevaCommonConstants.MERCHANT_INFO, resultJson);
			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
		} catch (SQLException e) {
			logger.debug("Got SQLException in GetMerchantBackInfo ["
					+ e.getMessage() + "]");
			responseDTO
					.addError("Internal Error Occured While Executing Data.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Got Exception in 	GetMerchantBackInfo ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing Data.");
			e.printStackTrace();
		} finally {
			try {

				if (locationRS != null) {
					locationRS.close();
				}

				if (locationPstmt != null) {
					locationPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultJson = null;
			merchantDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertMerchantDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InsertMerchantDetails.. ");

		String address = null;
		String telephone = null;
		String addressLine1 = null;
		String addressLine2 = null;
		String addressLine3 = null;
		String telephoneNumber1 = null;
		String telephoneNumber2 = null;

		String documentMultiData = "";
		String agentMultiData = "";
		String tillId = "";

		CallableStatement callableStatement = null;
		String insertMerchantDetailsProc = "{call MerchantMgmtPkg.MerchantDetailsInsertProc"
				                        + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String Msg = "";
		try {
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			addressLine1 = requestJSON.getString(CevaCommonConstants.ADDRESS1);
			addressLine2 = requestJSON.getString(CevaCommonConstants.ADDRESS2);
			if (requestJSON.containsKey(CevaCommonConstants.ADDRESS3)) {
				addressLine3 = requestJSON .getString(CevaCommonConstants.ADDRESS3);
			} else {
				addressLine3 = "";
			}

			telephoneNumber1 = requestJSON .getString(CevaCommonConstants.TELEPHONE1);
			if (requestJSON.containsKey(CevaCommonConstants.TELEPHONE2)) {
				telephoneNumber2 = requestJSON .getString(CevaCommonConstants.TELEPHONE2);
			} else {
				telephoneNumber2 = "";
			}

			address = addressLine1 + "#" + addressLine2 + "#" + addressLine3;
			telephone = telephoneNumber1 + "#" + telephoneNumber2;

			/*if (requestJSON .containsKey(CevaCommonConstants.DOCUMENT_MULTI_DATA)) {
				documentMultiData = requestJSON .getString(CevaCommonConstants.DOCUMENT_MULTI_DATA);
			}*/
			
			if (requestJSON.containsKey(CevaCommonConstants.AGENT_MULTI_DATA )) {
				agentMultiData = requestJSON .getString(CevaCommonConstants.AGENT_MULTI_DATA);
				logger.debug("message"+agentMultiData);
			}

			tillId = getRandomInteger();

			callableStatement = connection.prepareCall(insertMerchantDetailsProc);
			
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.MERCHANT_NAME));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.STORE_NAME));
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.LOCATION_NAME).split("-")[0]);
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.KRA_PIN));
			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.MERCHANT_TYPE));
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.MANAGER_NAME));
			callableStatement.setString(10,
					requestJSON.getString(CevaCommonConstants.EMAIL_ID));
			callableStatement.setString(11, address);
			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.CITY));
			callableStatement.setString(13,
					requestJSON.getString(CevaCommonConstants.POBOXNUMBER));
			callableStatement.setString(14, telephone);
			callableStatement.setString(15,requestJSON.getString(CevaCommonConstants.FAX_NUMBER));
			callableStatement.setString(16, tillId);
			String area=requestJSON.getString("area");
			if(area.contains("-"))
			{
				area = area.split("-")[1].trim();
			}
			else area=area.trim();
			callableStatement.setString(17, area);
			callableStatement.setString(18, requestJSON.getString("postal_code"));
			callableStatement.setString(19, requestJSON.getString("lrnumber"));
			callableStatement.setString(20, requestJSON.getString("country"));
			callableStatement.setString(21, requestJSON.getString("accountNumber"));
			callableStatement.setString(22, requestJSON.getString("relationShipManagerNumber"));
			callableStatement.setString(23, requestJSON.getString("relationShipManagerName"));
			callableStatement.setString(24, requestJSON.getString("relationShipManagerEmail"));
			callableStatement.setString(25, requestJSON.getString("latitude"));
			callableStatement.setString(26, requestJSON.getString("longitude"));	
			callableStatement.setString(27, agentMultiData);
			callableStatement.setString(28, requestJSON.getString("contactInfoMultidata"));
			callableStatement.setString(29, requestJSON.getString("password"));
			callableStatement.setString(30, requestJSON.getString("encryptPassword"));
			callableStatement.setString(31, requestJSON.getString("otp"));
			callableStatement.setString(32, requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.registerOutParameter(33, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(34, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(33);
			Msg = callableStatement.getString(34);

			/* callableStatement.setString(16, requestJSON .getString(CevaCommonConstants.BANK_ACCT_MULTI_DATA).trim());
			   callableStatement.setString(9, requestJSON.getString(CevaCommonConstants.MEMBER_TYPE)); */
			//callableStatement.setString(16, requestJSON.getString(CevaCommonConstants.MOBILE_NUMBER));
			//callableStatement.setString(18, requestJSON .getString(CevaCommonConstants.PRIMARY_CONTACT_NAME));
			//callableStatement.setString(19, requestJSON .getString(CevaCommonConstants.PRIMARY_CONTACT_NUMBER));
			//callableStatement.setString(21, documentMultiData);
			
			//callableStatement.setString(23, requestJSON.getString("contactInfoMultidata"));
			/*callableStatement.setString(24, requestJSON.getString("relationShipManager"));
			callableStatement.setString(25, requestJSON.getString("bankRepresentativeName"));
			callableStatement.setString(26, requestJSON.getString("bankRepresentativeNumber")); */
			
			
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);

			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in insertMerchantDetails [" + e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {

			}
			insertMerchantDetailsProc = null;
			Msg = null;
			address = null;
			telephone = null;
			addressLine1 = null;
			addressLine2 = null;
			addressLine3 = null;
			telephoneNumber1 = null;
			telephoneNumber2 = null;
			insertMerchantDetailsProc = null;
			documentMultiData = null;
			agentMultiData = null;
			tillId = null;
		}

		return responseDTO;
	}

	public ResponseDTO getStoreCreatePageInfo(RequestDTO requestDTO) {

		logger.debug("Inside GetStoreCreatePageInfo.. ");
		HashMap<String, Object> storeDataMap = null;
		JSONObject resultJson = null;

		PreparedStatement locationPstmt = null;
		ResultSet locationRS = null;

		Connection connection = null;
		String entityQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG "
				+ "is null order by to_number(OFFICE_CODE)";

		try {
			storeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			locationPstmt = connection.prepareStatement(entityQry);

			locationRS = locationPstmt.executeQuery();
			JSONObject json = new JSONObject();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));
			}

			logger.debug("Location JSONArray [" + json + "]");
			resultJson.put(CevaCommonConstants.LOCATION_LIST, json);
			json.clear();
			json = null;
			String storeIdQry = "Select count(STORE_ID) from STORE_MASTER where MERCHANT_ID=?";

			locationPstmt.close();
			locationRS.close();

			locationPstmt = connection.prepareStatement(storeIdQry);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			locationRS = locationPstmt.executeQuery();
			int storeId = 0;

			if (locationRS.next()) {
				storeId = Integer.parseInt(locationRS.getString(1));
			}

			storeId++;

			resultJson.put("storeId", storeId);

			logger.debug("StoreId [" + storeId + "]");

			locationPstmt.close();
			locationRS.close();

			String merchantPrmQry = "Select MERCHANT_ID,MERCHANT_NAME,LOCATION,KRA_PIN, MERCHANT_TYPE||'-'||CATE_DESCRIPTION ,MANAGER_NAME,EMAIL,"
					+ "ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,AGEN_OR_BILLER"
					+ ",AREA,POSTALCODE,LRNUMBER,COUNTRY,MERCHANT_ADMIN  from MERCHANT_MASTER MM,CATEGORY_MASTER CM where MM.MERCHANT_ID=? AND  MM.MERCHANT_TYPE=CM.CATE_CODE ";

			locationPstmt = connection.prepareStatement(merchantPrmQry);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			locationRS = locationPstmt.executeQuery();
			if (locationRS.next()) {
				resultJson.put(CevaCommonConstants.MERCHANT_ID,
						locationRS.getString(1));
				resultJson.put(CevaCommonConstants.MERCHANT_NAME,
						locationRS.getString(2));
				resultJson.put(CevaCommonConstants.LOCATION_INFO,
						locationRS.getString(3));
				resultJson.put(CevaCommonConstants.KRA_PIN,
						locationRS.getString(4));
				resultJson.put(CevaCommonConstants.MERCHANT_TYPE,
						locationRS.getString(5));
				resultJson.put(CevaCommonConstants.MANAGER_NAME,
						locationRS.getString(6));
				resultJson.put(CevaCommonConstants.EMAIL,
						locationRS.getString(7));

				String address = locationRS.getString(8);
				String[] addressVal = address.split("#");
				String address1 = "";
				String address2 = "";
				String address3 = "";
				if (addressVal.length == 1) {
					address1 = addressVal[0];
				}
				if (addressVal.length == 2) {
					address1 = addressVal[0];
					address2 = addressVal[1];
				}
				if (addressVal.length == 3) {
					address1 = addressVal[0];
					address2 = addressVal[1];
					address3 = addressVal[2];
				}

				String telphone = locationRS.getString(11);
				String[] telPhoneArr = telphone.split("#");
				String telephone1 = "";
				String telephone2 = "";

				if (telPhoneArr.length == 1)
					telephone1 = telPhoneArr[0];
				if (telPhoneArr.length == 2) {
					telephone1 = telPhoneArr[0];
					telephone2 = telPhoneArr[1];
				}

				resultJson.put(CevaCommonConstants.ADDRESS1, address1);
				resultJson.put(CevaCommonConstants.ADDRESS2, address2);
				resultJson.put(CevaCommonConstants.ADDRESS3, address3);
				resultJson.put(CevaCommonConstants.CITY,
						locationRS.getString(9));
				resultJson.put(CevaCommonConstants.POBOXNUMBER,
						locationRS.getString(10));
				resultJson.put(CevaCommonConstants.TELEPHONE1, telephone1);
				resultJson.put(CevaCommonConstants.TELEPHONE2, telephone2);
				resultJson.put(CevaCommonConstants.MOBILE_NUMBER,
						locationRS.getString(12));
				resultJson.put(CevaCommonConstants.FAX_NUMBER,
						locationRS.getString(13));
				resultJson.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						locationRS.getString(14));
				resultJson.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						locationRS.getString(15));
				resultJson.put(CevaCommonConstants.MEMBER_TYPE,
						locationRS.getString(16));

				resultJson.put("area", locationRS.getString(17));
				resultJson.put("postalcode", locationRS.getString(18));
				resultJson.put("lrnumber", locationRS.getString(19));
				resultJson.put("country", locationRS.getString(20));
				resultJson.put("merchantAdminId", locationRS.getString(21));
				//resultJson.put("relationShipManager", locationRS.getString(22));
				/*resultJson.put("bankRepresentativeName", locationRS.getString(23));
				resultJson.put("bankRepresentativeNumber", locationRS.getString(24));*/
				

				telphone = null;
				telPhoneArr = null;
				telephone1 = null;
				telephone2 = null;
				address = null;
				addressVal = null;
				address1 = null;
				address2 = null;
				address3 = null;
			}

			locationPstmt.close();
			locationRS.close();

			merchantPrmQry = "select USER_NAME,decode(USER_STATUS,'F','First Time Login','A','Active','B','Blocked',USER_STATUS),EMAIL,EMP_ID "
					+ " from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC "
					+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=(select trim(MERCHANT_ADMIN) from merchant_master where merchant_id=?)";
			locationPstmt = connection.prepareStatement(merchantPrmQry);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			locationRS = locationPstmt.executeQuery();
			if (locationRS.next()) {
				resultJson.put("userName", locationRS.getString(1));
				resultJson.put("userStatus", locationRS.getString(2));
				resultJson.put("emailId", locationRS.getString(3));
				resultJson.put("employeeNo", locationRS.getString(4));

			}

			String firstStoreId = requestJSON.getString(
					CevaCommonConstants.MERCHANT_ID).substring(0, 4);
			firstStoreId = firstStoreId + "-S001";

			locationPstmt.close();
			locationRS.close();

			/*String merchantBankAcctQry = "SELECT distinct ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH,TRANSFER_CODE "
					+ "from BANK_ACCT_MASTER where MERCHANT_ID=?";*/
			//String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_NAME, ' '),CASE WHEN BAM.BANK_BRANCH IS NOT NULL THEN nvl((SELECT IBM.OFFICE_CODE||'-'||IBM.OFFICE_NAME FROM BRANCH_MASTER IBM WHERE IBM.OFFICE_CODE=BAM.BANK_BRANCH),' ')ELSE nvl(BAM.BANK_NAME,' ') END,TRANSFER_CODE FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? ";
			
			String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_BRANCH, ' ')  FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? ";
			locationPstmt = connection.prepareStatement(merchantBankAcctQry);
			locationPstmt.setString(1,requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			//locationPstmt.setString(2, firstStoreId);

			locationRS = locationPstmt.executeQuery();
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;
			while (locationRS.next()) {
				eachrow = locationRS.getString(1) + ","
						+ locationRS.getString(2) + ","
						+ locationRS.getString(3);
				if (i == 0) {
					bankAcctData += eachrow;
				} else {
					bankAcctData += "#" + eachrow;
				}
				i++;
			}
			resultJson.put("bankAcctMultiData", bankAcctData);

			locationPstmt.close();
			locationRS.close();

			String merchantAgentAcctQry = "SELECT distinct BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MPESA_TILL_NO "
					+ "from AGENT_INFORMATION where MERCHANT_ID=?";
			locationPstmt = connection.prepareStatement(merchantAgentAcctQry);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
		//	locationPstmt.setString(2, firstStoreId);

			locationRS = locationPstmt.executeQuery();
			String agentData = "";
			eachrow = "";
			i = 0;
			while (locationRS.next()) {
				eachrow = locationRS.getString(1) + ","
						+ locationRS.getString(2) + ","
						+ locationRS.getString(3) + ","
						+ locationRS.getString(4) + ","
						+ locationRS.getString(5);

				if (i == 0) {
					agentData += eachrow;
				} else {
					agentData += "#" + eachrow;
				}

				i++;
			}
			resultJson.put("AgenctAcctMultiData", agentData);

			locationPstmt.close();
			locationRS.close();

			String merchantDocumentQry = "SELECT distinct DOC_ID,DOC_DESC,GRACE_PERIOD,MANDATORY "
					                   + "from DOCUMENT_DETAILS where MERCHANT_ID=?";
			locationPstmt = connection.prepareStatement(merchantDocumentQry);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			//locationPstmt.setString(2, firstStoreId);

			locationRS = locationPstmt.executeQuery();
			String documentData = "";
			eachrow = "";

			i = 0;

			while (locationRS.next()) {
				eachrow = locationRS.getString(1) + ","
						+ locationRS.getString(2) + ","
						+ locationRS.getString(3) + ","
						+ locationRS.getString(4);
				if (i == 0) {
					documentData += eachrow;
				} else {
					documentData += "#" + eachrow;
				}

				i++;
			}

			resultJson.put("documentMultiData", documentData);
			
			/* Contact Data */
			
			/* String merchantPrmQry1 = "select PRIMARY_CONTACT_PERSON,PRIMARY_CONTACT_NUMBER,MOBILE_NUMBER from "
					               + "BANK_CONTACT_DEATAILS_TEMP  WHERE MERCHANT_ID=?";
			  
			//String merchantPrmQry1 = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_BRANCH, ' ')  FROM  BANK_ACCT_MASTER BAM WHERE  ref_num=? ";
			
			locationPstmt = connection.prepareStatement(merchantPrmQry1);
			locationPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			locationRS = locationPstmt.executeQuery();
			String bankAcctData1 = "";
			String eachrow1 = "";
			int i1 = 0;

			while (locationRS.next()) {
				
				eachrow1 = locationRS.getString(1) + ","
						+ locationRS.getString(2) + ","
						+ locationRS.getString(3);
				
				if (i1 == 0) {
					bankAcctData1 += eachrow1;
				} else {
					bankAcctData1 += "#" + eachrow1;
				}
				i++;
			}

			resultJson.put("contactInfoMultidata", bankAcctData1);

			locationPstmt.close();
			locationRS.close();  */
			
			/* Ending Contact Data */

			storeDataMap.put(CevaCommonConstants.STORE_INFO, resultJson);

			logger.debug("StoreDataMap [" + storeDataMap + "]");

			responseDTO.setData(storeDataMap);

			documentData = "";
			eachrow = "";
			merchantDocumentQry = "";
			agentData = "";
			merchantAgentAcctQry = "";
			bankAcctData = "";
			eachrow = "";
			firstStoreId = "";

		} catch (SQLException e) {
			logger.debug("SQLException in GetStoreCreatePageInfo ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Exception in GetStoreCreatePageInfo ["
					+ e.getMessage() + "]");
		} finally {
			try {
				if (locationPstmt != null)
					locationPstmt.close();

				if (locationRS != null)
					locationRS.close();

				if (connection != null)
					connection.close();

			} catch (Exception e) {

			}
			entityQry = null;
			storeDataMap = null;
			resultJson = null;
		}
		return responseDTO;
	}

	/*public ResponseDTO insertStoreDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InsertStoreDetails.. ");

		String address = null;
		String telephone = null;
		String addressLine1 = null;
		String addressLine2 = null;
		String addressLine3 = null;
		String telephoneNumber1 = null;
		String telephoneNumber2 = null;
		String documentMultiData = "";
		String agentMultiData = "";
		CallableStatement callableStatement = null;
		String insertMerchantDetailsProc = "{call MerchantMgmtPkg.StoreDetailsInsertProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			addressLine1 = requestJSON.getString(CevaCommonConstants.ADDRESS1);
			addressLine2 = requestJSON.getString(CevaCommonConstants.ADDRESS2);
			if (requestJSON.containsKey(CevaCommonConstants.ADDRESS3)) {
				addressLine3 = requestJSON
						.getString(CevaCommonConstants.ADDRESS3);
			} else {
				addressLine3 = "";
			}

			telephoneNumber1 = requestJSON
					.getString(CevaCommonConstants.TELEPHONE1);
			if (requestJSON.containsKey(CevaCommonConstants.TELEPHONE2)) {
				telephoneNumber2 = requestJSON
						.getString(CevaCommonConstants.TELEPHONE2);
			} else {
				telephoneNumber2 = "";
			}

			address = addressLine1 + "#" + addressLine2 + "#" + addressLine3;
			telephone = telephoneNumber1 + "#" + telephoneNumber2;

			if (requestJSON
					.containsKey(CevaCommonConstants.DOCUMENT_MULTI_DATA)) {
				documentMultiData = requestJSON
						.getString(CevaCommonConstants.DOCUMENT_MULTI_DATA);
			}
			if (requestJSON.containsKey(CevaCommonConstants.AGENT_MULTI_DATA)) {
				agentMultiData = requestJSON
						.getString(CevaCommonConstants.AGENT_MULTI_DATA);
			}

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection
					.prepareCall(insertMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.MERCHANT_NAME));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.STORE_NAME));
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.LOCATION_NAME));
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.KRA_PIN));
			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.MANAGER_NAME));
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.EMAIL_ID));
			callableStatement.setString(10, address);
			callableStatement.setString(11,
					requestJSON.getString(CevaCommonConstants.CITY));
			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.POBOXNUMBER));
			callableStatement.setString(13, telephone);
			callableStatement.setString(14,
					requestJSON.getString(CevaCommonConstants.MOBILE_NUMBER));
			callableStatement.setString(15,
					requestJSON.getString(CevaCommonConstants.FAX_NUMBER));
			callableStatement.setString(16, requestJSON
					.getString(CevaCommonConstants.PRIMARY_CONTACT_NAME));
			callableStatement.setString(17, requestJSON
					.getString(CevaCommonConstants.PRIMARY_CONTACT_NUMBER));
			callableStatement.setString(18,
					requestJSON.getString(CevaCommonConstants.TILL_ID));
			callableStatement.setString(19, requestJSON
					.getString(CevaCommonConstants.BANK_ACCT_MULTI_DATA));
			callableStatement.setString(20, documentMultiData);
			callableStatement.setString(21, agentMultiData);
			callableStatement.setString(22, requestJSON.getString("area"));
			callableStatement.setString(23,
					requestJSON.getString("postal_code"));
			callableStatement.setString(24, requestJSON.getString("lrnumber"));
			callableStatement.setString(25, requestJSON.getString("country"));

			callableStatement.registerOutParameter(26, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(27, java.sql.Types.VARCHAR);
			callableStatement.setString(28, requestJSON.getString("county"));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(26);
			String Msg = callableStatement.getString(27);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InsertStoreDetails is ["
					+ e.getMessage() + "]");
			responseDTO
					.addError("Internal Error Occured While Inserting Store Details.");
		} catch (Exception e) {
			logger.debug("Exception in InsertStoreDetails is    ["
					+ e.getMessage() + "]");
			responseDTO
					.addError("Internal Error Occured While Inserting Store Details.");
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

			address = null;
			telephone = null;
			addressLine1 = null;
			addressLine2 = null;
			addressLine3 = null;
			telephoneNumber1 = null;
			telephoneNumber2 = null;
			documentMultiData = null;
			agentMultiData = null;
			insertMerchantDetailsProc = null;
		}

		return responseDTO;
	}*/

	public ResponseDTO insertStoreDetails(RequestDTO requestDTO) {

		  Connection connection = null;
		  logger.debug("Inside InsertStoreDetails.. ");

		  String address = null;
		  String telephone = null;
		  String addressLine1 = null;
		  String addressLine2 = null;
		  String addressLine3 = null;
		  String telephoneNumber1 = null;
		  String telephoneNumber2 = null;
		  String documentMultiData = "";
		  String agentMultiData = "";
		  CallableStatement callableStatement = null;
		  String insertMerchantDetailsProc = "{call MerchantMgmtPkg.StoreDetailsInsertProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		  try {

		   responseDTO = new ResponseDTO();
		   requestJSON = requestDTO.getRequestJSON();
		   logger.debug("Request JSON [" + requestJSON + "]");

		   addressLine1 = requestJSON.getString(CevaCommonConstants.ADDRESS1);
		   addressLine2 = requestJSON.getString(CevaCommonConstants.ADDRESS2);
		   if (requestJSON.containsKey(CevaCommonConstants.ADDRESS3)) {
		    addressLine3 = requestJSON
		      .getString(CevaCommonConstants.ADDRESS3);
		   } else {
		    addressLine3 = "";
		   }

		   telephoneNumber1 = requestJSON
		     .getString(CevaCommonConstants.TELEPHONE1);
		   if (requestJSON.containsKey(CevaCommonConstants.TELEPHONE2)) {
		    telephoneNumber2 = requestJSON
		      .getString(CevaCommonConstants.TELEPHONE2);
		   } else {
		    telephoneNumber2 = "";
		   }

		   address = addressLine1 + "#" + addressLine2 + "#" + addressLine3;
		   telephone = telephoneNumber1 + "#" + telephoneNumber2;

		   if (requestJSON
		     .containsKey(CevaCommonConstants.DOCUMENT_MULTI_DATA)) {
		    documentMultiData = requestJSON
		      .getString(CevaCommonConstants.DOCUMENT_MULTI_DATA);
		   }
		   if (requestJSON.containsKey(CevaCommonConstants.AGENT_MULTI_DATA)) {
		    agentMultiData = requestJSON
		      .getString(CevaCommonConstants.AGENT_MULTI_DATA);
		   }

		   connection = DBConnector.getConnection();
		   logger.debug("connection is null [" + connection == null + "]");

		   callableStatement = connection
		     .prepareCall(insertMerchantDetailsProc);
		   callableStatement.setString(1,
		     requestJSON.getString(CevaCommonConstants.MAKER_ID));
		   callableStatement.setString(2,
		     requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
		   callableStatement.setString(3,
		     requestJSON.getString(CevaCommonConstants.MERCHANT_NAME));
		   callableStatement.setString(4,
		     requestJSON.getString(CevaCommonConstants.STORE_ID));
		   callableStatement.setString(5,
		     requestJSON.getString(CevaCommonConstants.STORE_NAME));
		   callableStatement.setString(6,
		     requestJSON.getString(CevaCommonConstants.LOCATION_NAME));
		   callableStatement.setString(7,
		     requestJSON.getString(CevaCommonConstants.KRA_PIN));
		   callableStatement.setString(8,
		     requestJSON.getString(CevaCommonConstants.MANAGER_NAME));
		   callableStatement.setString(9,
		     requestJSON.getString(CevaCommonConstants.EMAIL_ID));
		   callableStatement.setString(10, address);
		   callableStatement.setString(11,
		     requestJSON.getString(CevaCommonConstants.CITY));
		   callableStatement.setString(12,
		     requestJSON.getString(CevaCommonConstants.POBOXNUMBER));
		   callableStatement.setString(13, telephone);
		   callableStatement.setString(14,
		     requestJSON.getString(CevaCommonConstants.MOBILE_NUMBER));
		   callableStatement.setString(15,
		     requestJSON.getString(CevaCommonConstants.FAX_NUMBER));
		   callableStatement.setString(16, requestJSON
		     .getString(CevaCommonConstants.PRIMARY_CONTACT_NAME));
		   callableStatement.setString(17, requestJSON
		     .getString(CevaCommonConstants.PRIMARY_CONTACT_NUMBER));
		   callableStatement.setString(18,
		     requestJSON.getString(CevaCommonConstants.TILL_ID));
		   callableStatement.setString(19, requestJSON
		     .getString(CevaCommonConstants.BANK_ACCT_MULTI_DATA));
		   callableStatement.setString(20, documentMultiData);
		   callableStatement.setString(21, agentMultiData);
		   
		   
		   
		   
		   String area=requestJSON.getString("area");
			if(area.contains("-"))
			{
				area = area.split("-")[1].trim();
			}
			else area=area.trim();
			callableStatement.setString(22, area);
		    callableStatement.setString(23,
		     requestJSON.getString("postal_code"));
		   callableStatement.setString(24, requestJSON.getString("lrnumber"));
		   callableStatement.setString(25, requestJSON.getString("country"));

		   callableStatement.registerOutParameter(26, java.sql.Types.INTEGER);
		   callableStatement.registerOutParameter(27, java.sql.Types.VARCHAR);
		   callableStatement.setString(28, requestJSON.getString("county"));
		   callableStatement.setString(29, requestJSON
					.getString(CevaCommonConstants.IP));
		   callableStatement.executeUpdate();
		   int resCnt = callableStatement.getInt(26);
		   String Msg = callableStatement.getString(27);

		   logger.debug("ResultCnt from DB [" + resCnt + "]");
		   responseDTO = getMerchantDetails(requestDTO);
		   if (resCnt == 1) {
		    responseDTO.addMessages(Msg);
		   } else {
		    responseDTO.addError(Msg);
		   }

		  } catch (SQLException e) {
		   logger.debug("SQLException in InsertStoreDetails is ["
		     + e.getMessage() + "]");
		   responseDTO
		     .addError("Internal Error Occured While Inserting Store Details.");
		  } catch (Exception e) {
		   logger.debug("Exception in InsertStoreDetails is    ["
		     + e.getMessage() + "]");
		   responseDTO
		     .addError("Internal Error Occured While Inserting Store Details.");
		  } finally {
		   try {
		    if (callableStatement != null) {
		     callableStatement.close();
		    }
		    if (connection != null) {
		     connection.close();
		    }
		   } catch (SQLException e) {

		   }

		   address = null;
		   telephone = null;
		   addressLine1 = null;
		   addressLine2 = null;
		   addressLine3 = null;
		   telephoneNumber1 = null;
		   telephoneNumber2 = null;
		   documentMultiData = null;
		   agentMultiData = null;
		   insertMerchantDetailsProc = null;
		  }

		  return responseDTO;
		 }

	public ResponseDTO insertTerminalDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside InsertTerminalDetails.. ");
		JSONObject resultJson = null;

		String tmkKey = "";
		String encTmkKey = "";


		PreparedStatement terminalIdPstmt = null;
		PreparedStatement tmkPstmt = null;
		PreparedStatement brncodestmt = null;
		ResultSet terminalIdRS = null;
		ResultSet brnres= null;

		HashMap<String, Object> resultmap = null;

		CallableStatement callableStatement = null;
		//String terminalIdQry = "select LPAD(TERMINALID_SEQ.nextval,'5','0') from DUAL";
		//String storebranchid = "Select LOCATION from STORE_MASTER WHERE STORE_ID=? ";
		String insertMerchantDetailsProc = "{call MerchantMgmtPkg.TerminalDetailsInsertProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultmap = new HashMap<String, Object>();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");


			/*terminalIdPstmt = connection.prepareStatement(terminalIdQry);
			terminalIdRS = terminalIdPstmt.executeQuery();

			while (terminalIdRS.next()) {
				tid = terminalIdRS.getString(1);

			}
			logger.debug(" Terminal Leftpading  "+tid );

		    storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			brncodestmt = connection.prepareStatement(storebranchid);
			brncodestmt.setString(1, storeId.trim());
			brnres = brncodestmt.executeQuery();

			if (brnres.next()) {
				brncode = brnres.getString(1);
			}

			String termina_id_main=brncode+""+tid;
			*/

			tmkKey = requestJSON.getString(CevaCommonConstants.TPK_KEY);

			try {
				encTmkKey = EncryptTransactionPin.encrypt(
						"97206B46CE46376894703ECE161F31F2", tmkKey, 'F');
			} catch (Exception e) {
				logger.debug(" Exception is ::: " + e.getMessage());
				encTmkKey = "";
			}

			logger.debug(" TmkKey1111[" + tmkKey + "] EncTmkKey[" + encTmkKey + "]");

			callableStatement = connection
					.prepareCall(insertMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
		    callableStatement.setString(4,
				requestJSON.getString(CevaCommonConstants.TERMINAL_ID));

			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.TERMINAL_USAGE));
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.TERMINAL_MAKE));
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.MODEL_NO));
			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.SERIAL_NO));
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.VALID_FROM));
			callableStatement.setString(10,
					requestJSON.getString(CevaCommonConstants.VALID_THRU));
			callableStatement.setString(11,
					requestJSON.getString(CevaCommonConstants.STATUS));
			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.TERMINAL_DATE));
			callableStatement.setString(13,
					requestJSON.getString(CevaCommonConstants.TMK_INDEX));
			callableStatement.setString(14, encTmkKey);
			callableStatement.registerOutParameter(15, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
			callableStatement.setString(17, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(15);
			String Msg = callableStatement.getString(16);

			logger.debug("ResultCnt from DB [" + resCnt + "]");


		    responseDTO = getMerchantDetails(requestDTO);


			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in InsertTerminalDetails is ["
					+ e.getMessage() + "]");
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			insertMerchantDetailsProc = null;
			tmkKey = null;
			encTmkKey = null;
		}
		return responseDTO;
	}

	public ResponseDTO getMerchantViewDetails(RequestDTO requestDTO) {

		PreparedStatement merchantprmPstmt = null;
		ResultSet merchantPrmRS = null;
		Connection connection = null;

		logger.debug("Inside GetMerchantViewDetails... ");
		HashMap<String, Object> merchantDataMap = null;

		JSONArray jsonArray = null;
		JSONObject json = null;
		String merchantPrmQry = "Select MM.MERCHANT_ID,MM.MERCHANT_NAME,(select office_code||'-'||office_name  from BRANCH_MASTER where office_code=MM.LOCATION), "
				+ "MM.KRA_PIN,(select cate_code||'-'||CATE_DESCRIPTION  from CATEGORY_MASTER where cate_code=MM.MERCHANT_TYPE),"
				+ " MM.MANAGER_NAME,MM.EMAIL,MM.ADDRESS,MM.CITY,MM.PO_BOX_NO,MM.TELEPHONE_NO,MM.MOBILE_NO,MM.FAX_NO,MM.PRI_CONTACT_NAME, "
				+ "MM.PRI_CONTACT_NO,MM.AGEN_OR_BILLER,MM.AREA,MM.POSTALCODE,MM.LRNUMBER,MM.COUNTRY,MM.MERCHANT_ADMIN,MM.STATUS,MM.MAKER_ID,"
				+ "to_char(MM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),MM.AUTHID,to_char(MM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS'),"
				+ "LATITUDE,LONGITUDE,ACCOUNT_NUMBER,RELATIONSHIP_MANAGER_NUMBER, RELATIONSHIP_MANAGER_NAME, RELATIONSHIP_MANAGER_EMAIL"
				+ "  from MERCHANT_MASTER MM where MM.MERCHANT_ID=?";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("connection is null " + connection == null + "]");
			merchantDataMap = new HashMap<String, Object>();
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			while (merchantPrmRS.next()) {
				responseJSON.put(CevaCommonConstants.MERCHANT_ID,
						merchantPrmRS.getString(1));
				responseJSON.put(CevaCommonConstants.MERCHANT_NAME,
						merchantPrmRS.getString(2));
				responseJSON.put(CevaCommonConstants.LOCATION_NAME,
						merchantPrmRS.getString(3));
				responseJSON.put(CevaCommonConstants.KRA_PIN,
						merchantPrmRS.getString(4));
				responseJSON.put(CevaCommonConstants.MERCHANT_TYPE,
						merchantPrmRS.getString(5));
				responseJSON.put(CevaCommonConstants.MANAGER_NAME,
						merchantPrmRS.getString(6));
				responseJSON.put(CevaCommonConstants.EMAIL,
						merchantPrmRS.getString(7));

				String address = merchantPrmRS.getString(8);
				String[] addressVal = address.split("#");
				String address1 = "";
				String address2 = "";
				String address3 = "";

				if (addressVal.length == 1) {
					address1 = addressVal[0];
				}
				if (addressVal.length == 2) {
					address1 = addressVal[0];
					address2 = addressVal[1];
				}
				if (addressVal.length == 3) {
					address1 = addressVal[0];
					address2 = addressVal[1];
					address3 = addressVal[2];
				}

				String telphone = merchantPrmRS.getString(11);
				String[] telPhoneArr = telphone.split("#");
				String telephone1 = "";
				String telephone2 = "";
				if (telPhoneArr.length == 1)
					telephone1 = telPhoneArr[0];
				if (telPhoneArr.length == 2) {
					telephone1 = telPhoneArr[0];
					telephone2 = telPhoneArr[1];
				}

				responseJSON.put(CevaCommonConstants.ADDRESS1, address1);
				responseJSON.put(CevaCommonConstants.ADDRESS2, address2);
				responseJSON.put(CevaCommonConstants.ADDRESS3, address3);
				responseJSON.put(CevaCommonConstants.CITY,
						merchantPrmRS.getString(9));
				responseJSON.put(CevaCommonConstants.POBOXNUMBER,
						merchantPrmRS.getString(10));
				responseJSON.put(CevaCommonConstants.TELEPHONE1, telephone1);
				responseJSON.put(CevaCommonConstants.TELEPHONE2, telephone2);
				responseJSON.put(CevaCommonConstants.MOBILE_NUMBER,
						merchantPrmRS.getString(12));
				responseJSON.put(CevaCommonConstants.FAX_NUMBER,
						merchantPrmRS.getString(13));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						merchantPrmRS.getString(14));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						merchantPrmRS.getString(15));
				responseJSON.put(CevaCommonConstants.MEMBER_TYPE,
						merchantPrmRS.getString(16));
				responseJSON.put("AREA", merchantPrmRS.getString(17));
				responseJSON.put("POSTALCODE", merchantPrmRS.getString(18));
				responseJSON.put("LRNUMBER", merchantPrmRS.getString(19));
				responseJSON.put("COUNTRY", merchantPrmRS.getString(20));
				responseJSON.put("merchantAdmin", merchantPrmRS.getString(21));
				responseJSON.put("merstatus", merchantPrmRS.getString(22));
				responseJSON.put("createId", merchantPrmRS.getString(23));
				responseJSON.put("createDate", merchantPrmRS.getString(24));
				responseJSON.put("authId", merchantPrmRS.getString(25));
				responseJSON.put("authDate", merchantPrmRS.getString(26));
				responseJSON.put("latitude", merchantPrmRS.getString(27));
				responseJSON.put("longitude", merchantPrmRS.getString(28));
				responseJSON.put("accountNumber", merchantPrmRS.getString(29));
				responseJSON.put("relationShipManagerNumber", merchantPrmRS.getString(30));
				responseJSON.put("relationShipManagerName", merchantPrmRS.getString(31));
				responseJSON.put("relationShipManagerEmail", merchantPrmRS.getString(32));
				
				logger.debug("Postal Code:"+merchantPrmRS.getString(30)+"---"+" LR number:"+merchantPrmRS.getString(31)+"---"+"Primary Contact Number"+merchantPrmRS.getString(32)+"---"+"Primary Contact name:"+merchantPrmRS.getString(14));

				address = null;
				addressVal = null;
				address1 = null;
				address2 = null;
				address3 = null;
				telphone = null;
				telPhoneArr = null;
				telephone1 = null;
				telephone2 = null;
			}

			merchantprmPstmt.close();
			merchantPrmRS.close();

			merchantPrmQry = "select USER_NAME,decode(USER_STATUS,'F','First Time Login','A','Active','B','Blocked',USER_STATUS),EMAIL,EMP_ID "
					+ " from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC "
					+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=(select trim(MERCHANT_ADMIN) from merchant_master where merchant_id=?)";
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			if (merchantPrmRS.next()) {
				responseJSON.put("userName", merchantPrmRS.getString(1));
				responseJSON.put("userStatus", merchantPrmRS.getString(2));
				responseJSON.put("emailId", merchantPrmRS.getString(3));
				responseJSON.put("employeeNo", merchantPrmRS.getString(4));
			}

			merchantprmPstmt.close();
			merchantPrmRS.close();

		/*	String merchantBankAcctQry = "SELECT distinct ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH,TRANSFER_CODE "
					+ "from BANK_ACCT_MASTER where MERCHANT_ID=?";*/
			//String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_NAME, ' '),CASE WHEN BAM.BANK_BRANCH IS NOT NULL THEN nvl((SELECT IBM.OFFICE_CODE||'-'||IBM.OFFICE_NAME FROM BRANCH_MASTER IBM WHERE IBM.OFFICE_CODE=BAM.BANK_BRANCH),' ')ELSE nvl(BAM.BANK_NAME,' ') END,TRANSFER_CODE FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? ";

			String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_BRANCH, ' ') FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? ";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;

			while (merchantPrmRS.next()) {
				eachrow = merchantPrmRS.getString(1) + ","
						+ merchantPrmRS.getString(2) + ","
						//+ merchantPrmRS.getString(3) + ","
						//+ merchantPrmRS.getString(4) + ","
						+ merchantPrmRS.getString(3);

				if (i == 0) {
					bankAcctData += eachrow;
				} else {
					bankAcctData += "#" + eachrow;
				}
				i++;
			}

			responseJSON.put("bankAcctMultiData", bankAcctData);

			merchantprmPstmt.close();
			merchantPrmRS.close();

			merchantBankAcctQry = "SELECT distinct BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MPESA_TILL_NO from AGENT_INFORMATION where MERCHANT_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			String agentData = "";
			eachrow = "";
			i = 0;
			while (merchantPrmRS.next()) {
				eachrow = merchantPrmRS.getString(1) + ","
						+ merchantPrmRS.getString(2) + ","
						+ merchantPrmRS.getString(3) + ","
						+ merchantPrmRS.getString(4) + ","
						+ merchantPrmRS.getString(5);

				if (i == 0) {
					agentData += eachrow;
				} else {
					agentData += "#" + eachrow;
				}

				i++;
			}
			responseJSON.put("AgenctAcctMultiData", agentData);

			merchantprmPstmt.close();
			merchantPrmRS.close();

			merchantBankAcctQry = "SELECT distinct DOC_ID,DOC_DESC,GRACE_PERIOD,MANDATORY from DOCUMENT_DETAILS where MERCHANT_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			String documentData = "";
			eachrow = "";
			i = 0;
			while (merchantPrmRS.next()) {
				eachrow = merchantPrmRS.getString(1) + ","
						+ merchantPrmRS.getString(2) + ","
						+ merchantPrmRS.getString(3) + ","
						+ merchantPrmRS.getString(4);

				if (i == 0) {
					documentData += eachrow;
				} else {
					documentData += "#" + eachrow;
				}

				i++;
			}
			responseJSON.put("documentMultiData", documentData);
			
			
			// ***
			
			String countryCodeQry = "select  COUNTRY_CODE||'-'||COUNTRY_NAME,COUNTRY_NAME from COUNTRY_MASTER";
			merchantprmPstmt = connection.prepareStatement(countryCodeQry);
			json = new JSONObject();
			
			merchantPrmRS = merchantprmPstmt.executeQuery();
			while (merchantPrmRS.next()) {
				
				json.put(merchantPrmRS.getString(1), merchantPrmRS.getString(2));
			}
			
			responseJSON.put("Country_List", json);
			json.clear();
			merchantprmPstmt.close();
			merchantPrmRS.close();
			
			
			String countyCodeQry = "select  county_code||'-'||county_name,county_name from county_master";
			merchantprmPstmt = connection.prepareStatement(countyCodeQry);

			merchantPrmRS = merchantprmPstmt.executeQuery();
			while (merchantPrmRS.next()) {
				json.put(merchantPrmRS.getString(1), merchantPrmRS.getString(2));

			}
			responseJSON.put("County_List", json);
			json.clear();
			merchantprmPstmt.close();
			merchantPrmRS.close();
			
			//Below Are Fetching For Store Details
			merchantprmPstmt.close();
			merchantPrmRS.close();

			jsonArray = new JSONArray();
			json = new JSONObject();

			merchantBankAcctQry = "Select SM.STORE_ID,SM.STORE_NAME,"
					+ "Decode(SM.STATUS,'A','Active','B','Inactive','N','Un-Authorize'),"
					+ "SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),"
					+ "SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
					+ " from MERCHANT_MASTER MM,STORE_MASTER SM "
					+ "where SM.MERCHANT_ID=MM.MERCHANT_ID and MM.MERCHANT_ID=?  order by SM.STORE_ID";

			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantPrmRS = merchantprmPstmt.executeQuery();
			json.clear();

			while (merchantPrmRS.next()) {
				json.put(CevaCommonConstants.STORE_ID,
						merchantPrmRS.getString(1));
				json.put(CevaCommonConstants.STORE_NAME,
						merchantPrmRS.getString(2));
				json.put(CevaCommonConstants.STATUS, merchantPrmRS.getString(3));
				json.put(CevaCommonConstants.MAKER_ID,
						merchantPrmRS.getString(4));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantPrmRS.getString(5));
				json.put("authid", merchantPrmRS.getString(6));
				json.put("authdttm", merchantPrmRS.getString(7));

				jsonArray.add(json);
				json.clear();
			}

			responseJSON.put("storeData", jsonArray);

			merchantprmPstmt.close();
			merchantPrmRS.close();

			merchantBankAcctQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null "
					+ "order by to_number(OFFICE_CODE)";

			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantPrmRS = merchantprmPstmt.executeQuery();

			while (merchantPrmRS.next()) {
				json.put(merchantPrmRS.getString(1), merchantPrmRS.getString(2));
			}

			responseJSON.put(CevaCommonConstants.LOCATION_LIST, json);

			json.clear();

			logger.debug("Response JSON [" + responseJSON + "]");
			merchantDataMap .put(CevaCommonConstants.MERCHANT_INFO, responseJSON);
			logger.debug("MerchantData Map [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);

			merchantBankAcctQry = null;

		} catch (SQLException e) {
			logger.debug("SQLException in GetMerchantViewDetails is ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Exception in GetMerchantViewDetails is ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (merchantprmPstmt != null)
					merchantprmPstmt.close();

				if (merchantPrmRS != null)
					merchantPrmRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			merchantPrmQry = null;
			merchantDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO getMerchantLocationDetails(RequestDTO requestDTO) {

		PreparedStatement merchantprmPstmt = null;
		ResultSet merchantPrmRS = null;
		Connection connection = null;

		logger.debug("Inside GetMerchantViewDetails... ");
		HashMap<String, Object> merchantDataMap = null;

		JSONObject json = null;
		String merchantPrmQry = "";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("connection is null " + connection == null + "]");
			merchantDataMap = new HashMap<String, Object>();

			merchantPrmQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null "
					+ "order by to_number(OFFICE_CODE)";

			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantPrmRS = merchantprmPstmt.executeQuery();
			json = new JSONObject();

			while (merchantPrmRS.next()) {
				json.put(merchantPrmRS.getString(1), merchantPrmRS.getString(2));
			}

			responseJSON.put(CevaCommonConstants.LOCATION_LIST, json);

			json.clear();

			logger.debug("Response JSON [" + responseJSON + "]");
			merchantDataMap
					.put(CevaCommonConstants.MERCHANT_INFO, responseJSON);
			logger.debug("MerchantData Map [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetMerchantViewDetails is ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Exception in GetMerchantViewDetails is ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (merchantprmPstmt != null)
					merchantprmPstmt.close();

				if (merchantPrmRS != null)
					merchantPrmRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			merchantPrmQry = null;
			merchantDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO merchantTerminate(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside MerchantTerminate.. ");

		CallableStatement callableStatement = null;
		String terminateMerchantDetailsProc = "{call MerchantMgmtPkg.MerchantTerminateProc(?,?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug(" connection == null [" + connection == null + "]");

			callableStatement = connection
					.prepareCall(terminateMerchantDetailsProc);
			callableStatement.setString(1,requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.setString(5, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(3);
			String Msg = callableStatement.getString(4);

			logger.debug("ResCnt [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in MerchantTerminate [" + e.getMessage()
					+ "]");
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}
			terminateMerchantDetailsProc = null;
		}

		return responseDTO;
	}

	public ResponseDTO getStoreViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetStoreViewDetails... ");

		HashMap<String, Object> storeDataMap = null;
		String merchantName = "";
		String merchantQry = "select MERCHANT_NAME from MERCHANT_MASTER where MERCHANT_ID=?";
		PreparedStatement merchantprmPstmt = null;
		ResultSet merchantRS = null;

		Connection connection = null;

		JSONObject json = null;

		String merchantPrmQry = "";
		try {

			storeDataMap = new HashMap<String, Object>();
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			merchantName = requestJSON
					.getString(CevaCommonConstants.MERCHANT_NAME);

			connection = DBConnector.getConnection();

			merchantprmPstmt = connection.prepareStatement(merchantQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantRS = merchantprmPstmt.executeQuery();

			if (merchantRS.next()) {
				merchantName = merchantRS.getString(1);
			}

			merchantprmPstmt.close();
			merchantRS.close();

			logger.debug("Merchant Name [" + merchantName + "]");

			logger.debug("Connection is null [" + connection == null + "]");
		/*	merchantPrmQry = "Select MERCHANT_ID,STORE_ID,STORE_NAME,LOCATION,MANAGER_NAME,EMAIL,ADDRESS,CITY,"
					+ "PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,KRA_PIN,TILL_ID,"
					+ "(select AGEN_OR_BILLER  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID) ,"
					+ "AREA,POSTALCODE,LRNUMBER,COUNTRY,(select MM.MERCHANT_ADMIN  from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID),"
					+ "SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS') "
					+ "from STORE_MASTER SM where SM.MERCHANT_ID=? and SM.STORE_ID=?";*/

			merchantPrmQry = "Select MERCHANT_ID,STORE_ID,STORE_NAME,LOCATION,MANAGER_NAME,EMAIL,ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,KRA_PIN,TILL_ID,(select AGEN_OR_BILLER  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID),AREA,POSTALCODE,LRNUMBER,COUNTRY,(select MM.MERCHANT_ADMIN  from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID),SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS'),(select cate_code||'-'||CATE_DESCRIPTION  from CATEGORY_MASTER where cate_code=(select MERCHANT_TYPE  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID)),(Select OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where OFFICE_CODE = SM.LOCATION) AS OfficeCode,SM.COUNTY  from STORE_MASTER SM where SM.MERCHANT_ID=? and SM.STORE_ID=?";

			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantprmPstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			merchantRS = merchantprmPstmt.executeQuery();

			while (merchantRS.next()) {
				responseJSON.put(CevaCommonConstants.MERCHANT_ID,
						merchantRS.getString(1));
				responseJSON.put(CevaCommonConstants.MERCHANT_NAME,
						merchantName);
				responseJSON.put(CevaCommonConstants.STORE_ID,
						merchantRS.getString(2));
				responseJSON.put(CevaCommonConstants.STORE_NAME,
						merchantRS.getString(3));
				responseJSON.put(CevaCommonConstants.LOCATION_NAME,
						merchantRS.getString(4));
				responseJSON.put(CevaCommonConstants.MANAGER_NAME,
						merchantRS.getString(5));
				responseJSON.put(CevaCommonConstants.EMAIL,
						merchantRS.getString(6));

				String address = merchantRS.getString(7);
				String[] addressVal = address.split("#");
				String address1 = "";
				String address2 = "";
				String address3 = "";
				if (addressVal.length == 1) {
					address1 = addressVal[0];
				}
				if (addressVal.length == 2) {
					address1 = addressVal[0];
					address2 = addressVal[1];
				}
				if (addressVal.length == 3) {
					address1 = addressVal[0];
					address2 = addressVal[1];
					address3 = addressVal[2];
				}

				String telphone = merchantRS.getString(10);
				String[] telPhoneArr = telphone.split("#");
				String telephone1 = "";
				String telephone2 = "";
				if (telPhoneArr.length == 1)
					telephone1 = telPhoneArr[0];
				if (telPhoneArr.length == 2) {
					telephone1 = telPhoneArr[0];
					telephone2 = telPhoneArr[1];
				}

				responseJSON.put(CevaCommonConstants.ADDRESS1, address1);
				responseJSON.put(CevaCommonConstants.ADDRESS2, address2);
				responseJSON.put(CevaCommonConstants.ADDRESS3, address3);
				responseJSON.put(CevaCommonConstants.CITY,
						merchantRS.getString(8));
				responseJSON.put(CevaCommonConstants.POBOXNUMBER,
						merchantRS.getString(9));
				responseJSON.put(CevaCommonConstants.TELEPHONE1, telephone1);
				responseJSON.put(CevaCommonConstants.TELEPHONE2, telephone2);
				responseJSON.put(CevaCommonConstants.MOBILE_NUMBER,
						merchantRS.getString(11));
				responseJSON.put(CevaCommonConstants.FAX_NUMBER,
						merchantRS.getString(12));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						merchantRS.getString(13));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						merchantRS.getString(14));
				responseJSON.put(CevaCommonConstants.KRA_PIN,
						merchantRS.getString(15));
				responseJSON.put(CevaCommonConstants.TILL_ID,
						merchantRS.getString(16));

				responseJSON.put(CevaCommonConstants.MEMBER_TYPE,
						merchantRS.getString(17));

				responseJSON.put("area", merchantRS.getString(18));
				responseJSON.put("postalcode", merchantRS.getString(19));
				responseJSON.put("lrnumber", merchantRS.getString(20));
				responseJSON.put("country", merchantRS.getString(21));
				responseJSON.put("merchantAdmin", merchantRS.getString(22));
				responseJSON.put("createId", merchantRS.getString(23));
				responseJSON.put("createDate", merchantRS.getString(24));
				responseJSON.put("authId", merchantRS.getString(25));
				responseJSON.put("authDate", merchantRS.getString(26));
				responseJSON.put("merchantTypeVal", merchantRS.getString(27));
			    responseJSON.put("locationcity", merchantRS.getString(28));
			    responseJSON.put("county", merchantRS.getString(29));

				address = null;
				addressVal = null;
				address1 = null;
				address2 = null;
				address3 = null;
				telphone = null;
				telPhoneArr = null;
				telephone1 = null;
				telephone2 = null;
			}

			merchantprmPstmt.close();
			merchantRS.close();

			merchantPrmQry = "select USER_NAME,decode(USER_STATUS,'F','First Time Login','A','Active','B','Blocked',USER_STATUS),EMAIL,EMP_ID "
					+ " from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC "
					+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=(select trim(MERCHANT_ADMIN) from merchant_master where merchant_id=?)";
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			merchantRS = merchantprmPstmt.executeQuery();
			if (merchantRS.next()) {
				responseJSON.put("userName", merchantRS.getString(1));
				responseJSON.put("userStatus", merchantRS.getString(2));
				responseJSON.put("emailId", merchantRS.getString(3));
				responseJSON.put("employeeNo", merchantRS.getString(4));

			}

			merchantprmPstmt.close();
			merchantRS.close();

			/*String merchantBankAcctQry = "SELECT distinct ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH,TRANSFER_CODE from "
					+ "BANK_ACCT_MASTER where MERCHANT_ID=? and STORE_ID=?";*/
			String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_NAME, ' '),CASE WHEN BAM.BANK_BRANCH IS NOT NULL THEN nvl((SELECT IBM.OFFICE_CODE||'-'||IBM.OFFICE_NAME FROM BRANCH_MASTER IBM WHERE IBM.OFFICE_CODE=BAM.BANK_BRANCH),' ')ELSE nvl(BAM.BANK_NAME,' ') END,TRANSFER_CODE FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? and BAM.STORE_ID=? ";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantprmPstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			merchantRS = merchantprmPstmt.executeQuery();
			String bankAcctData = "";
			String eachrow = "";
			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4) + ","
						+ merchantRS.getString(5);
				bankAcctData = bankAcctData + "#" + eachrow;
			}

			responseJSON.put("bankAcctMultiData", bankAcctData);

			merchantprmPstmt.close();
			merchantRS.close();

			String merchantAgentAcctQry = "SELECT distinct BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MPESA_TILL_NO from AGENT_INFORMATION where MERCHANT_ID=? and STORE_ID=?";
			merchantprmPstmt = connection
					.prepareStatement(merchantAgentAcctQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantprmPstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			merchantRS = merchantprmPstmt.executeQuery();
			String agentData = "";
			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4) + ","
						+ merchantRS.getString(5);
				agentData = agentData + "#" + eachrow;
			}
			responseJSON.put("AgenctAcctMultiData", agentData);

			merchantprmPstmt.close();
			merchantRS.close();

			String merchantDocumentQry = "SELECT distinct DOC_ID,DOC_DESC,GRACE_PERIOD,MANDATORY from DOCUMENT_DETAILS "
					+ "where MERCHANT_ID=? and STORE_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantDocumentQry);
			merchantprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			merchantprmPstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			merchantRS = merchantprmPstmt.executeQuery();
			String documentData = "";
			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4);
				documentData = documentData + "#" + eachrow;
			}
			responseJSON.put("documentMultiData", documentData);

			documentData = null;
			agentData = null;
			bankAcctData = null;
			eachrow = null;

			merchantprmPstmt.close();
			merchantRS.close();

			merchantQry = "Select distinct OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME "
					+ "from BRANCH_MASTER where HPO_FLAG is null "
					+ "order by to_number(OFFICE_CODE)";

			merchantprmPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantprmPstmt.executeQuery();

			json = new JSONObject();
			while (merchantRS.next()) {
				json.put(merchantRS.getString(1), merchantRS.getString(2));
			}

			responseJSON.put(CevaCommonConstants.LOCATION_LIST, json);

			json.clear();

			logger.debug("Response JSON [" + responseJSON + "]");

			storeDataMap.put(CevaCommonConstants.STORE_INFO, responseJSON);
			logger.debug("MerchantDataMap [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);

		} catch (Exception e) {
			logger.debug("Exception in GetStoreViewDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		}

		finally {
			try {

				if (merchantprmPstmt != null)
					merchantprmPstmt.close();
				if (merchantRS != null)
					merchantRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			merchantName = null;
			storeDataMap = null;
			merchantQry = null;
		}
		return responseDTO;
	}

	public ResponseDTO storeTerminate(RequestDTO requestDTO) {
		logger.debug("Inside StoreTerminate.. ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String terminateMerchantDetailsProc = "{call MerchantMgmtPkg.StoreTerminateProc(?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection
					.prepareCall(terminateMerchantDetailsProc);
			callableStatement.setString(1,requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStatement.setString(6, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(4);
			String Msg = callableStatement.getString(5);

			logger.debug("ResCnt [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in MerchantTerminate [" + e.getMessage()
					+ "]");
		}

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
			terminateMerchantDetailsProc = null;
		}

		return responseDTO;
	}

	public ResponseDTO getTerminalviewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetTerminalviewDetails..");

		JSONArray merchantJSONArray = null;
		JSONArray storeJSONArray = null;
		JSONArray userJSONArray = null;
		HashMap<String, Object> terminalDataMap = null;

		PreparedStatement terminalprmPstmt = null;
		PreparedStatement merchantPstmt = null;
		PreparedStatement storePstmt = null;
		PreparedStatement userPstmt = null;
		ResultSet terminalRS = null;
		ResultSet merchantRS = null;
		ResultSet storeRS = null;
		ResultSet userRS = null;
		Connection connection = null;

		String storePrmQry = "Select MERCHANT_ID,STORE_ID,TERMINAL_ID,TERMINAL_USAGE,TERMINAL_MAKE,MODEL_NO,"
				+ "SERIAL_NO,PIN_ENTRY,to_char(VALID_FROM,'DD-MM-YY'),to_char(VALID_THRU,'DD-MM-YY'),Decode(STATUS,'A','Active','B','Inactive','N','Un-Authorize','D','Inactive' ),to_char(MAKER_DTTM,'DD-MM-YYYY'),TMK_INDEX,TPK"
				+ " from TERMINAL_MASTER where MERCHANT_ID=? and trim(STORE_ID)=trim(?) and TERMINAL_ID=?";
		try {
			merchantJSONArray = new JSONArray();
			storeJSONArray = new JSONArray();
			userJSONArray = new JSONArray();
			terminalDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			terminalprmPstmt = connection.prepareStatement(storePrmQry);
			terminalprmPstmt.setString(1,requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			terminalprmPstmt.setString(2,requestJSON.getString(CevaCommonConstants.STORE_ID));
			terminalprmPstmt.setString(3,requestJSON.getString(CevaCommonConstants.TERMINAL_ID));

			terminalRS = terminalprmPstmt.executeQuery();
			while (terminalRS.next()) {
				responseJSON.put(CevaCommonConstants.MERCHANT_ID,
						terminalRS.getString(1));
				responseJSON.put(CevaCommonConstants.STORE_ID,
						terminalRS.getString(2));
				responseJSON.put(CevaCommonConstants.TERMINAL_ID,
						terminalRS.getString(3));
				responseJSON.put(CevaCommonConstants.TERM_USAGE,
						terminalRS.getString(4));
				responseJSON.put(CevaCommonConstants.TERMINAL_MAKE,
						terminalRS.getString(5));
				responseJSON.put(CevaCommonConstants.MODEL_NO,
						terminalRS.getString(6));
				responseJSON.put(CevaCommonConstants.SERIAL_NO,
						terminalRS.getString(7));
				responseJSON.put(CevaCommonConstants.PINENTRY,
						terminalRS.getString(8));
				responseJSON.put(CevaCommonConstants.VALID_FROM,
						terminalRS.getString(9));
				responseJSON.put(CevaCommonConstants.VALID_THRU,
						terminalRS.getString(10));
				responseJSON.put(CevaCommonConstants.STATUS,
						terminalRS.getString(11));
				responseJSON.put(CevaCommonConstants.TERMINAL_DATE,
						terminalRS.getString(12));
				responseJSON.put(CevaCommonConstants.TMK_INDEX,
						terminalRS.getString(13));
				responseJSON.put(CevaCommonConstants.TPK_KEY,
						terminalRS.getString(14));
			}

			String merchnatQry = "Select MERCHANT_ID,MERCHANT_NAME from MERCHANT_MASTER order by MERCHANT_NAME";
			merchantPstmt = connection.prepareStatement(merchnatQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						merchantRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,
						merchantRS.getString(1));
				merchantJSONArray.add(json);
			}

			json = null;
			logger.debug("MerchantJSONArray [" + merchantJSONArray + "]");

			String storeQry = "Select STORE_ID,STORE_NAME from STORE_MASTER where trim(MERCHANT_ID)=trim(?) order by STORE_NAME";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));

			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
				storeJSONArray.add(json);
			}

			logger.debug("StoreJSONArray [" + storeJSONArray + "]");

			json = null;
			String UserQry="SELECT DISTINCT USER_ID,TERMINAL_ID,STORE_ID,MERCHANT_ID,MAKER_DTTM,MAKER_ID FROM USER_TERMINAL_MAPPING WHERE TERMINAL_ID=?";

			userPstmt = connection.prepareStatement(UserQry);
			userPstmt.setString(1,requestJSON.getString(CevaCommonConstants.TERMINAL_ID));

			userRS = userPstmt.executeQuery();
			while (userRS.next()) {
				json = new JSONObject();
				json.put("TerminalID", userRS.getString(2));
				json.put("UserID", userRS.getString(1));
				json.put("StoreID", userRS.getString(3));
				json.put("MerchantID", userRS.getString(4));
				json.put("MakerDttm", userRS.getString(5));
				json.put("MakerID", userRS.getString(6));
				userJSONArray.add(json);
			}

			logger.debug("userJSONArray [" + userJSONArray + "]");

			responseJSON.put("USER_LIST",	userJSONArray);
			responseJSON.put(CevaCommonConstants.MERCHANT_LIST,merchantJSONArray);
			responseJSON.put(CevaCommonConstants.STORE_LIST,storeJSONArray);
			logger.debug("Response JSON [" + responseJSON + "]");
			terminalDataMap.put(CevaCommonConstants.STORE_INFO, responseJSON);
			logger.debug("TerminalDataMap [" + terminalDataMap + "]");
			responseDTO.setData(terminalDataMap);

		} catch (Exception e) {
			logger.debug("Exception in GetTerminalviewDetails ["
					+ e.getMessage() + "]");
		}

		finally {
			try {

				if (terminalprmPstmt != null)
					terminalprmPstmt.close();
				if (merchantPstmt != null)
					merchantPstmt.close();
				if (storePstmt != null)
					storePstmt.close();

				if (terminalRS != null)
					terminalRS.close();
				if (merchantRS != null)
					merchantRS.close();
				if (storeRS != null)
					storeRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}

			merchantJSONArray = null;
			storeJSONArray = null;
			terminalDataMap = null;
		}
		return responseDTO;
	}

	public ResponseDTO terminateTerminal(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside TerminateTerminal.. ");

		CallableStatement callableStatement = null;
		String terminateMerchantDetailsProc = "{call MerchantMgmtPkg.TerminalTerminateProc(?,?,?,?,?,?,?)}";
		String Msg = "";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection
					.prepareCall(terminateMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.TERMINAL_ID));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStatement.setString(7, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);
			Msg = callableStatement.getString(6);

			logger.debug("ResCnt [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in TerminateTerminal [" + e.getMessage()
					+ "]");
		}

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
			terminateMerchantDetailsProc = null;
			Msg = null;
		}

		return responseDTO;
	}

	public ResponseDTO updateTerminalDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside UpdateTerminalDetails... ");

		CallableStatement callableStatement = null;
		String insertMerchantDetailsProc = "{call MerchantMgmtPkg.TerminalDetailsUpdatePro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String Msg = "";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection
					.prepareCall(insertMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.TERMINAL_ID));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.TERMINAL_USAGE));
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.TERMINAL_MAKE));
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.MODEL_NO));
			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.SERIAL_NO));
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.PINENTRY));
			callableStatement.setString(10,
					requestJSON.getString(CevaCommonConstants.VALID_FROM));
			callableStatement.setString(11,
					requestJSON.getString(CevaCommonConstants.VALID_THRU));
			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.STATUS));
			callableStatement.setString(13,
					requestJSON.getString(CevaCommonConstants.TERMINAL_DATE));
			callableStatement.registerOutParameter(14, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
			callableStatement.setString(16, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(14);
			Msg = callableStatement.getString(15);

			logger.debug("Res Cnt [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in UpdateTerminalDetails ["
					+ e.getMessage() + "]");
		}

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			insertMerchantDetailsProc = null;
			Msg = null;
		}
		return responseDTO;
	}

	public ResponseDTO updateStoreDetails(RequestDTO requestDTO) {
		logger.debug("Inside UpdateStoreDetails.. ");

		Connection connection = null;
		String address = null;
		String telephone = null;
		String addressLine1 = null;
		String addressLine2 = null;
		String addressLine3 = null;
		String telephoneNumber1 = null;
		String telephoneNumber2 = null;
		String Msg = "";
		CallableStatement callableStatement = null;
		String insertMerchantDetailsProc = "{call MerchantMgmtPkg.StoreDetailsUpdateProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
		
			addressLine1 = requestJSON.getString(CevaCommonConstants.ADDRESS1);
			addressLine2 = requestJSON.getString(CevaCommonConstants.ADDRESS2);
			if (requestJSON.containsKey(CevaCommonConstants.ADDRESS3)) {
				addressLine3 = requestJSON
						.getString(CevaCommonConstants.ADDRESS3);
			} else {
				addressLine3 = "";
			}

			telephoneNumber1 = requestJSON
					.getString(CevaCommonConstants.TELEPHONE1);
			if (requestJSON.containsKey(CevaCommonConstants.TELEPHONE2)) {
				telephoneNumber2 = requestJSON
						.getString(CevaCommonConstants.TELEPHONE2);
			} else {
				telephoneNumber2 = "";
			}

			address = addressLine1 + "#" + addressLine2 + "#" + addressLine3;
			telephone = telephoneNumber1 + "#" + telephoneNumber2;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection
					.prepareCall(insertMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.MERCHANT_NAME));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			callableStatement.setString(5,
					requestJSON.getString(CevaCommonConstants.STORE_NAME));
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.LOCATION_NAME));
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.KRA_PIN));
			callableStatement.setString(8,
					requestJSON.getString(CevaCommonConstants.MANAGER_NAME));
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.EMAIL_ID));
			callableStatement.setString(10, address);
			callableStatement.setString(11,
					requestJSON.getString(CevaCommonConstants.CITY));
			callableStatement.setString(12,
					requestJSON.getString(CevaCommonConstants.POBOXNUMBER));
			callableStatement.setString(13, telephone);
			callableStatement.setString(14,
					requestJSON.getString(CevaCommonConstants.MOBILE_NUMBER));
			callableStatement.setString(15,
					requestJSON.getString(CevaCommonConstants.FAX_NUMBER));
			callableStatement.setString(16, requestJSON
					.getString(CevaCommonConstants.PRIMARY_CONTACT_NAME));
			callableStatement.setString(17, requestJSON
					.getString(CevaCommonConstants.PRIMARY_CONTACT_NUMBER));
			callableStatement.registerOutParameter(18, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(19, java.sql.Types.VARCHAR);

			callableStatement.setString(20,requestJSON.getString("county"));
			//callableStatement.setString(21, requestJSON.getString(CevaCommonConstants.IP));


			callableStatement.setString(20, requestJSON
					.getString(CevaCommonConstants.IP));
			String area=requestJSON.getString("county");
			if(area.contains("-"))
			{
				area = area.split("-")[1].trim();
			}
			else area=area.trim();
			
			callableStatement.setString(20,area);
			callableStatement.setString(21,requestJSON.getString("postal_code"));
			callableStatement.setString(22,requestJSON.getString("lrnumber"));
			callableStatement.setString(23,requestJSON.getString("country"));
		
			callableStatement.setString(24, requestJSON
					.getString(CevaCommonConstants.IP));

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(18);
			Msg = callableStatement.getString(19);

			logger.debug("ResCnt [" + resCnt + "]");
			logger.debug("Msg [" + Msg + "]");

			responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in UpdateStoreDetails [" + e.getMessage()
					+ "]");
			e.printStackTrace();
		}

		finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {

			}
			insertMerchantDetailsProc = null;
			Msg = null;
			address = null;
			telephone = null;
			addressLine1 = null;
			addressLine2 = null;
			addressLine3 = null;
			telephoneNumber1 = null;
			telephoneNumber2 = null;
		}
		return responseDTO;
	}

	public ResponseDTO getUserstoTerminal(RequestDTO requestDTO) {

		HashMap<String, Object> resultMap = null;
		JSONArray userJSONArray = null;
		Connection connection = null;

		logger.debug("Inside GetUserstoTerminal.. ");

		String userQry = "SELECT ULC.LOGIN_USER_ID,ULC.LOGIN_USER_ID||'-'||UI.USER_NAME "
				+ "from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI "
				+ "where UI.USER_TYPE=? and UI.COMMON_ID=ULC.COMMON_ID and UI.MERCHANT_ID=trim(?)  and (UI.USER_STATUS='A' or UI.USER_STATUS='F') ";

		PreparedStatement userPstmt = null;
		ResultSet userRS = null;
		String terminalId = null;
		String storeId = null;
		String merchantId = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			terminalId = requestJSON.getString(CevaCommonConstants.TERMINAL_ID);
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);


			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1, "MU");
			userPstmt.setString(2, merchantId);
			userRS = userPstmt.executeQuery();
			JSONObject json = null;

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}
			responseJSON.put(CevaCommonConstants.USERS_LIST, userJSONArray);

			userQry = "";
			userPstmt.close();
			userRS.close();
			userJSONArray.clear();

			/*userQry = "SELECT ULC.LOGIN_USER_ID,ULC.LOGIN_USER_ID||'-'||UI.USER_NAME "
					+ "from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI "
					+ "where UI.USER_TYPE in ('MA','MS') and UI.COMMON_ID=ULC.COMMON_ID and  UI.MERCHANT_ID=? ";*/
			userQry = "SELECT ULC.LOGIN_USER_ID,ULC.LOGIN_USER_ID||'-'||UI.USER_NAME "
					+ "from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI "
					+ "where UI.USER_TYPE in ('MA','MS') and UI.COMMON_ID=ULC.COMMON_ID ";

			userPstmt = connection.prepareStatement(userQry);

			/*userPstmt.setString(1, merchantId);*/

			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}
			responseJSON
					.put(CevaCommonConstants.ADMIN_TYPE_LIST, userJSONArray);

			userQry = "";
			userPstmt.close();
			userRS.close();

			userQry = "select USER_ID from USER_TERMINAL_MAPPING "
					+ "where MERCHANT_ID = ? and STORE_ID = ? and TERMINAL_ID = ? and "
					+ "trunc(MAKER_DTTM)=(select trunc(max(MAKER_DTTM)) "
					+ "from USER_TERMINAL_MAPPING where MERCHANT_ID = ? and STORE_ID = ? and TERMINAL_ID = ? ) order by USER_ID";


			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1, merchantId.trim());
			userPstmt.setString(2, storeId.trim());
			userPstmt.setString(3, terminalId.trim());
			userPstmt.setString(4, merchantId.trim());
			userPstmt.setString(5, storeId.trim());
			userPstmt.setString(6, terminalId.trim());

			userRS = userPstmt.executeQuery();

			userJSONArray = null;
			userJSONArray = new JSONArray();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}

			responseJSON.put("exist_users", userJSONArray);
			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
			responseDTO.setData(resultMap);
		} catch (Exception e) {
			logger.debug("Exception in GetUserstoTerminal [" + e.getMessage()
					+ "]");
		} finally {
			try {
				if (userPstmt != null)
					userPstmt.close();
				if (userRS != null)
					userRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			terminalId = null;
			storeId = null;
			merchantId = null;
			userQry = null;
			resultMap = null;
			userJSONArray = null;
		}
		return responseDTO;
	}

	public ResponseDTO assignUsers(RequestDTO requestDTO) {

		Connection connection = null;
		String makerId = "";
		String merchantId = "";
		String terminalId = "";
		String selectedUser = "";
		String supervisor = "";
		String admin = "";
		String storeId = "";

		CallableStatement callableStatement = null;
		String assignUsers = "{call MerchantMgmtPkg.assignUsersToTerminalProc(?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			terminalId = requestJSON.getString(CevaCommonConstants.TERMINAL_ID);
			selectedUser = requestJSON.getString(CevaCommonConstants.SELECTED_USERS);
			supervisor = requestJSON.getString("SUPERVISOR");
			admin = requestJSON.getString("ADMIN");

			logger.debug("[MerchantDAO][assignUsers][Admin : "+admin+"]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection.prepareCall(assignUsers);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, merchantId);
			callableStatement.setString(3, storeId);
			callableStatement.setString(4, terminalId);
			callableStatement.setString(5, selectedUser);
			callableStatement.setString(6, supervisor);
			callableStatement.setString(7, admin);
			callableStatement.registerOutParameter(8, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
			callableStatement.setString(10, requestJSON.getString(CevaCommonConstants.IP));
			logger.debug(" before calling executeUpdate Method :::");
			callableStatement.executeUpdate();
			logger.debug(" After calling executeUpdate Method :::");
			int resCnt = callableStatement.getInt(8);
			logger.debug(" resCnt[" + resCnt + "]");

			if (resCnt == 1) {
				responseDTO.addMessages("Assigned Users to " + terminalId
						+ " is successfully Completed.");
			} else if (resCnt == -1 || resCnt == -2 || resCnt == -3) {
				responseDTO.addError(callableStatement.getString(9));
			} else {
				responseDTO.addError("some issue occured.");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			responseDTO.addError("Internal Issue Occured.");
		}

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			storeId = null;
			makerId = null;
			merchantId = null;
			terminalId = null;
			selectedUser = null;
			supervisor = null;
			admin = null;
			assignUsers = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO assignUserstoterm(RequestDTO requestDTO) {

		Connection connection = null;
		String makerId = "";
		String merchantId = "";
		String terminalId = "";
		String selectedUser = "";
		//String supervisor = "";
		String admin = "";
		String storeId = "";

		CallableStatement callableStatement = null;
		//String assignUsers = "{call MerchantMgmtPkg.assignUsersToTerminalmodProc(?,?,?,?,?,?,?,?,?,?)}";
		String assignUsers = "{call MerchantMgmtPkg.assignUsersToTerminalmodProc(?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			terminalId = requestJSON.getString(CevaCommonConstants.TERMINAL_ID);
			selectedUser = requestJSON.getString(CevaCommonConstants.SELECTED_USERS);
			//supervisor = requestJSON.getString("SUPERVISOR");
			//admin = requestJSON.getString("ADMIN");

			logger.debug("[MerchantDAO][assignUsers][Admin : "+admin+"]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection.prepareCall(assignUsers);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, merchantId);
			callableStatement.setString(3, storeId);
			callableStatement.setString(4, terminalId);
			callableStatement.setString(5, selectedUser);
			//callableStatement.setString(6, supervisor);
			//callableStatement.setString(7, admin);
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStatement.setString(8, requestJSON.getString(CevaCommonConstants.IP));
			logger.debug(" before calling executeUpdate Method :::");
			callableStatement.executeUpdate();
			logger.debug(" After calling executeUpdate Method :::");
			int resCnt = callableStatement.getInt(8);
			logger.debug(" resCnt[" + resCnt + "]");

			if (resCnt == 1) {
				responseDTO.addMessages("Assigned Users to " + terminalId
						+ " is successfully Completed.");
			} else if (resCnt == -1 || resCnt == -2 || resCnt == -3) {
				responseDTO.addError(callableStatement.getString(9));
			} else {
				responseDTO.addError("some issue occured.");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			responseDTO.addError("Internal Issue Occured.");
		}

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			storeId = null;
			makerId = null;
			merchantId = null;
			terminalId = null;
			selectedUser = null;
			//supervisor = null;
			admin = null;
			assignUsers = null;
		}

		return responseDTO;
	}

	public ResponseDTO getAutoGeneratedTerminal(RequestDTO requestDTO) {

		logger.debug("Inside GetAutoGeneratedTerminal.. ");
		HashMap<String, Object> storeDataMap = null;
		JSONObject resultJson = null;

		PreparedStatement terminalIdPstmt = null;
		PreparedStatement tmkPstmt = null;
		PreparedStatement brncodestmt = null;

		ResultSet terminalIdRS = null;
		ResultSet tmkRS = null;
		ResultSet brnres= null;
		Connection connection = null;

		String terminalIdQry = "Select LPAD(TERMINALID_SEQ.nextval,'5','0') from DUAL";
		String tmkIndexQry = "";
		String tmkIndex = "";
		String tid= "";
		
		JSONArray merchantJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		try {
			storeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			
			merchantJSONArray = new JSONArray();


			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");
			terminalIdPstmt = connection.prepareStatement(terminalIdQry);

			terminalIdRS = terminalIdPstmt.executeQuery();
			String terminalId = "";

			while (terminalIdRS.next()) {
				tid = terminalIdRS.getString(1);

			}

			tmkIndexQry = "Select TMKINDEX_SEQ.nextval from DUAL";
            tmkPstmt = connection.prepareStatement(tmkIndexQry);
			tmkRS = tmkPstmt.executeQuery();
			if (tmkRS.next()) {
				tmkIndex = tmkRS.getString(1);
			}
			
			
			String merchnatQry = "Select MERCHANT_ID,MERCHANT_NAME from MERCHANT_MASTER order by MERCHANT_NAME";
			merchantPstmt = connection.prepareStatement(merchnatQry);

			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,merchantRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,merchantRS.getString(1));
				merchantJSONArray.add(json);
			}

			json = null;
			logger.debug("MerchantJSONArray [" + merchantJSONArray + "]");


			responseJSON.put(CevaCommonConstants.MERCHANT_LIST,merchantJSONArray);
			responseJSON.put(CevaCommonConstants.TMK_INDEX, tmkIndex);
			responseJSON.put(CevaCommonConstants.tid, tid);

			logger.debug("Response JSON [" + responseJSON + "]");
			storeDataMap.put(CevaCommonConstants.STORE_INFO, responseJSON);

			logger.debug("StoreDataMap [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);

		} catch (Exception e) {
			logger.debug("Exception in GetAutoGeneratedTerminal ["
					+ e.getMessage() + "]");
		}

		finally {
			try {
				if (terminalIdPstmt != null)
					terminalIdPstmt.close();
				if (tmkPstmt != null)
					tmkPstmt.close();

				if (terminalIdRS != null)
					terminalIdRS.close();
				if (tmkRS != null)
					tmkRS.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}

			storeDataMap = null;
			resultJson = null;
			terminalIdQry = null;
			tmkIndexQry = null;
			tmkIndex = null;
		}

		return responseDTO;
	}

	public ResponseDTO getMerchantDashBoard(RequestDTO requestDTO) {

		logger.debug("Inside GetMerchantDashBoard... ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJSONArray = null;

		PreparedStatement merchantPstmt = null;

		ResultSet merchantRS = null;
		Connection connection = null;

		String merchantQry = "SELECT SM.MERCHANT_ID,SM.STORE_ID,SM.STORE_NAME,(select office_name from BRANCH_MASTER where office_code=SM.location and rownum<2),"
				+ "TM.TERMINAL_ID,decode(TM.STATUS,'A','Active','B','InActive',TM.STATUS),TM.SERIAL_NO,TM.MAKER_ID,MM.MOBILE_NO,MM.EMAIL "
				+ "FROM   STORE_MASTER SM ,TERMINAL_MASTER TM ,MERCHANT_MASTER MM"
				+ " where (TM.STORE_ID = SM.STORE_ID) AND  (TM.MERCHANT_ID = MM.MERCHANT_ID)  "
				+ " order by SM.MERCHANT_ID,SM.STORE_ID,TM.TERMINAL_ID";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;

			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.MERCHANT_ID,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.STORE_ID, merchantRS.getString(2));
				json.put("store_name", merchantRS.getString(3));
				json.put("store_location", merchantRS.getString(4));
				json.put(CevaCommonConstants.TERMINAL_ID,
						merchantRS.getString(5));
				json.put(CevaCommonConstants.STATUS, merchantRS.getString(6));
				json.put("serialno", merchantRS.getString(7));
				json.put("authterminalid", merchantRS.getString(8));
				json.put("Mobile", merchantRS.getString(9));
				json.put("Email", merchantRS.getString(10));

				merchantJSONArray.add(json);
			}

			json = null;

			logger.debug("MerchantJSONArray [" + merchantJSONArray + "]");
			resultJson.put(CevaCommonConstants.MERCHANT_DASHBOARD,
					merchantJSONArray);

			merchantMap.put(CevaCommonConstants.MERCHANT_DASHBOARD, resultJson);
			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("ResponseDTO [" + responseDTO + "]");

		} catch (Exception e) {
			logger.debug("Exception in GetMerchantDashBoard [" + e.getMessage()
					+ "]");
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
			merchantJSONArray = null;
			merchantQry = null;
		}
		return responseDTO;
	}

	public ResponseDTO getStoreViewDashboardDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetStoreViewDashboardDetails..");
		HashMap<String, Object> storeDataMap = null;

		PreparedStatement storeprmPstmt = null;
		ResultSet storePrmRS = null;
		Connection connection = null;

		String storePrmQry = "Select SM.MERCHANT_ID,SM.STORE_ID,SM.STORE_NAME,SM.LOCATION,SM.MANAGER_NAME,SM.EMAIL,SM.ADDRESS,SM.CITY,SM.PO_BOX_NO,SM.TELEPHONE_NO,SM.MOBILE_NO,SM.FAX_NO,SM.PRI_CONTACT_NAME,SM.PRI_CONTACT_NO,SM.KRA_PIN,MM.MERCHANT_NAME from STORE_MASTER SM,MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID and trim(SM.STORE_ID)=trim(?)";
		try {
			storeDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			storeprmPstmt = connection.prepareStatement(storePrmQry);
			storeprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.STORE_ID));
			storePrmRS = storeprmPstmt.executeQuery();
			while (storePrmRS.next()) {
				responseJSON.put(CevaCommonConstants.MERCHANT_ID,
						storePrmRS.getString(1));
				responseJSON.put(CevaCommonConstants.STORE_ID,
						storePrmRS.getString(2));
				responseJSON.put(CevaCommonConstants.STORE_NAME,
						storePrmRS.getString(3));
				responseJSON.put(CevaCommonConstants.LOCATION_NAME,
						storePrmRS.getString(4));
				responseJSON.put(CevaCommonConstants.MANAGER_NAME,
						storePrmRS.getString(5));
				responseJSON.put(CevaCommonConstants.EMAIL,
						storePrmRS.getString(6));
				String address = storePrmRS.getString(7);
				String[] addressVal = address.split("#");
				String address1 = "";
				String address2 = "";
				String address3 = "";
				if (addressVal.length == 1) {
					address1 = addressVal[0];
				}
				if (addressVal.length == 2) {
					address1 = addressVal[0];
					address2 = addressVal[1];
				}
				if (addressVal.length == 3) {
					address1 = addressVal[0];
					address2 = addressVal[1];
					address3 = addressVal[2];
				}

				String telphone = storePrmRS.getString(10);
				String[] telPhoneArr = telphone.split("#");
				String telephone1 = "";
				String telephone2 = "";
				if (telPhoneArr.length == 1)
					telephone1 = telPhoneArr[0];
				if (telPhoneArr.length == 2) {
					telephone1 = telPhoneArr[0];
					telephone2 = telPhoneArr[1];
				}

				responseJSON.put(CevaCommonConstants.ADDRESS1, address1);
				responseJSON.put(CevaCommonConstants.ADDRESS2, address2);
				responseJSON.put(CevaCommonConstants.ADDRESS3, address3);
				responseJSON.put(CevaCommonConstants.CITY,
						storePrmRS.getString(8));
				responseJSON.put(CevaCommonConstants.POBOXNUMBER,
						storePrmRS.getString(9));
				responseJSON.put(CevaCommonConstants.TELEPHONE1, telephone1);
				responseJSON.put(CevaCommonConstants.TELEPHONE2, telephone2);
				responseJSON.put(CevaCommonConstants.MOBILE_NUMBER,
						storePrmRS.getString(11));
				responseJSON.put(CevaCommonConstants.FAX_NUMBER,
						storePrmRS.getString(12));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						storePrmRS.getString(13));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						storePrmRS.getString(14));
				responseJSON.put(CevaCommonConstants.KRA_PIN,
						storePrmRS.getString(15));
				responseJSON.put(CevaCommonConstants.MERCHANT_NAME,
						storePrmRS.getString(16));

				address = null;
				addressVal = null;
				address1 = null;
				address2 = null;
				address3 = null;
				telphone = null;
				telPhoneArr = null;
				telephone1 = null;
				telephone2 = null;
			}

			logger.debug("Response JSON [" + responseJSON + "]");
			storeDataMap.put(CevaCommonConstants.STORE_INFO, responseJSON);
			logger.debug("MerchantData Map [" + storeDataMap + "]");
			responseDTO.setData(storeDataMap);

		} catch (Exception e) {
			logger.debug("Exception in GetStoreViewDashboardDetails ["
					+ e.getMessage() + "]");
		} finally {
			try {
				if (storeprmPstmt != null)
					storeprmPstmt.close();
				if (storePrmRS != null)
					storePrmRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			storeDataMap = null;
			storePrmQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO getTerminalDashboardviewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetTerminalDashboardviewDetails.. ");
		HashMap<String, Object> terminalDataMap = null;
		PreparedStatement terminalprmPstmt = null;
		ResultSet terminalRS = null;
		Connection connection = null;

		String storePrmQry = "Select MERCHANT_ID,STORE_ID,TERMINAL_ID,TERMINAL_USAGE,TERMINAL_MAKE,MODEL_NO,"
				+ "SERIAL_NO,PIN_ENTRY,to_char(VALID_FROM,'DD-MM-YYYY'),to_char(VALID_THRU,'DD-MM-YYYY'),Decode(STATUS,'A','Active','B','Inactive','N','Un-Authorize'),"
				+ "to_char(MAKER_DTTM,'DD-MM-YYYY')"
				+ " from TERMINAL_MASTER where  trim(TERMINAL_ID)=trim(?)";
		try {
			terminalDataMap = new HashMap<String, Object>();
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			terminalprmPstmt = connection.prepareStatement(storePrmQry);
			terminalprmPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.TERMINAL_ID));
			terminalRS = terminalprmPstmt.executeQuery();

			while (terminalRS.next()) {
				responseJSON.put(CevaCommonConstants.MERCHANT_ID,
						terminalRS.getString(1));
				responseJSON.put(CevaCommonConstants.STORE_ID,
						terminalRS.getString(2));
				responseJSON.put(CevaCommonConstants.TERMINAL_ID,
						terminalRS.getString(3));
				responseJSON.put(CevaCommonConstants.TERM_USAGE,
						terminalRS.getString(4));
				responseJSON.put(CevaCommonConstants.TERMINAL_MAKE,
						terminalRS.getString(5));
				responseJSON.put(CevaCommonConstants.MODEL_NO,
						terminalRS.getString(6));
				responseJSON.put(CevaCommonConstants.SERIAL_NO,
						terminalRS.getString(7));
				responseJSON.put(CevaCommonConstants.PINENTRY,
						terminalRS.getString(8));
				responseJSON.put(CevaCommonConstants.VALID_FROM,
						terminalRS.getString(9));
				responseJSON.put(CevaCommonConstants.VALID_THRU,
						terminalRS.getString(10));
				responseJSON.put(CevaCommonConstants.STATUS,
						terminalRS.getString(11));
				responseJSON.put(CevaCommonConstants.TERMINAL_DATE,
						terminalRS.getString(12));
			}

			logger.debug("Response JSON [" + responseJSON + "]");
			terminalDataMap.put(CevaCommonConstants.STORE_INFO, responseJSON);
			responseDTO.setData(terminalDataMap);

		} catch (Exception e) {
			logger.debug("Exception in GetTerminalDashboardviewDetails ["
					+ e.getMessage() + "]");
		} finally {
			try {
				if (terminalprmPstmt != null)
					terminalprmPstmt.close();
				if (terminalRS != null)
					terminalRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			storePrmQry = null;
			terminalDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO getUserstoServices(RequestDTO requestDTO) {

		HashMap<String, Object> resultMap = null;
		JSONArray userJSONArray = null;
		Connection connection = null;

		logger.debug("Inside GetUserstoServices... ");

		// String userQry =
		// "SELECT SERVICE_CODE,SERVICE_CODE||'-'||SERVICE_NAME from SERVICE_MASTER where SERVICE_TYPE is not null";
		String userQry = "SELECT  PROCESS_CODE,PROCESS_CODE||'-'||PROCESS_NAME from Process_Master";

		//String userQry = "SELECT DISTINCT SERVICE_TXN_CODE,FEE_NAME FROM FEE_MASTER ";

		PreparedStatement userPstmt = null;
		ResultSet userRS = null;

		try {
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			userPstmt = connection.prepareStatement(userQry);
			/*userPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID)
							.trim());*/
			userRS = userPstmt.executeQuery();
			JSONObject json = null;
			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}

			responseJSON.put(CevaCommonConstants.SERVICE_LIST, userJSONArray);

			userPstmt.close();
			userRS.close();
			userJSONArray.clear();

			userQry = "select SERVICE_CODE from TERMINAL_SERVICE_MAPPING where TERMINAL_ID=?";
			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.TERMINAL_ID)
							.trim());

			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}

			responseJSON.put("ASSIGNED_LIST", userJSONArray);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserstoServices ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Exception in GetUserstoServices 	[" + e.getMessage()
					+ "]");
		} finally {
			try {
				if (userPstmt != null)
					userPstmt.close();
				if (userRS != null)
					userRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			resultMap = null;
			userJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO assignServices(RequestDTO requestDTO) {

		logger.debug("Inside AssignServices... ");

		Connection connection = null;
		String makerId = "";
		String merchantId = "";
		String storeId = "";
		String terminalId = "";
		String selectedUser = "";

		CallableStatement callableStatement = null;
		String assignUsers = "{call MerchantMgmtPkg.assignServicesToTerminalProc(?,?,?,?,?,?,?,?)}";
		String msg = "";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			storeId = requestJSON.getString(CevaCommonConstants.STORE_ID);
			terminalId = requestJSON.getString(CevaCommonConstants.TERMINAL_ID);

			selectedUser = requestJSON
					.getString(CevaCommonConstants.SELECTED_SERVICES);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(assignUsers);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, merchantId);
			callableStatement.setString(3, storeId);
			callableStatement.setString(4, terminalId);
			callableStatement.setString(5, selectedUser);
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
			
			callableStatement.setString(8, requestJSON
					.getString(CevaCommonConstants.IP));
			logger.debug(" Before calling executeUpdate Method :::");
			callableStatement.executeUpdate();
			logger.debug(" After calling executeUpdate Method :::");
			int resCnt = callableStatement.getInt(6);
			msg = callableStatement.getString(7);
			logger.debug("ResCnt  [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages("Assigned Services to " + terminalId
						+ " is successfully Completed.");
			} else if (resCnt == -1) {
				responseDTO.addError(msg);
			} else {
				responseDTO.addError(msg);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			logger.debug("SQLException in AssignServices ["
					+ exception.getMessage() + "]");
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.debug("Exception in AssignServices ["
					+ exception.getMessage() + "]");
		} finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			makerId = null;
			merchantId = null;
			storeId = null;
			terminalId = null;
			selectedUser = null;
			assignUsers = null;
			msg = null;
		}
		return responseDTO;
	}

	public ResponseDTO assignServiceToTerminalView(RequestDTO requestDTO) {

		logger.debug("Inside  AssignServiceToTerminalView.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;

		String userQry = "SELECT  PROCESS_CODE||'-'||PROCESS_NAME from Process_Master where PROCESS_CODE in (select SERVICE_CODE from TERMINAL_SERVICE_MAPPING where TERMINAL_ID=?)";
		PreparedStatement userPstmt = null;
		ResultSet userRS = null;

		JSONObject json1 = null;
		JSONArray subJsonArray = null;
		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			resultMap = new HashMap<String, Object>();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.TERMINAL_ID)
							.trim());
			userRS = userPstmt.executeQuery();
			subJsonArray = new JSONArray();

			while (userRS.next()) {
				json1 = new JSONObject();
				json1.put("service", userRS.getString(1));
				subJsonArray.add(json1);

				json1.clear();
				json1 = null;
			}

			responseJSON.put(CevaCommonConstants.SERVICE_LIST, subJsonArray);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			logger.debug("SQLException in AssignServiceToTerminalView ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in AssignServiceToTerminalView 	  ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			try {
				if (userPstmt != null) {
					userPstmt.close();
				}
				if (userRS != null) {
					userRS.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			resultMap = null;
			userQry = null;
			responseJSON = null;
			json1 = null;
			subJsonArray = null;
		}
		return responseDTO;

	}

	public ResponseDTO merchantAuthorization(RequestDTO requestDTO) {

		logger.debug("Inside  MerchantAuthorization.. ");
		Connection connection = null;
		HashMap<String, Object> resultMap = null;

		String userQry = "{call MerchantMgmtPkg.MmsAuthorizationListProc(?,?,?,?,?)}";
		String status = "";
		String id = "";
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

			if (status.equalsIgnoreCase("MERC")) {
				id = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			} else if (status.equalsIgnoreCase("STORE")) {
				id = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			} else {
				id = requestJSON.getString(CevaCommonConstants.STORE_ID);
			}
			callable = connection.prepareCall(userQry);
			callable.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callable.setString(2, id);
			callable.setString(3, status);
			callable.registerOutParameter(4, OracleTypes.CURSOR);
			callable.registerOutParameter(5, OracleTypes.VARCHAR);

			callable.execute();
			logger.debug("Authorization block executed successfully "
					+ "with error_message[" + callable.getString(5) + "]");
			error = callable.getString(5);

			if (error.equalsIgnoreCase("SUCCESS")) {
				userRS = (ResultSet) callable.getObject(4);
				subJsonArray = new JSONArray();

				json = new JSONObject();
				if (status.equalsIgnoreCase("MERC")) {
					while (userRS.next()) {
						json.put("USER_ID", userRS.getString(1));
						json.put(CevaCommonConstants.MERCHANT_NAME,
								userRS.getString(2));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(3));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(5));
						json.put("MERCH_ADMIN", userRS.getString(6));
						subJsonArray.add(json);
						json.clear();
					}
				} else if (status.equalsIgnoreCase("STORE")) {
					while (userRS.next()) {
						json.put("USER_ID", userRS.getString(1));
						json.put(CevaCommonConstants.STORE_NAME,
								userRS.getString(2));
						json.put(CevaCommonConstants.MERCHANT_ID,
								userRS.getString(3));
						json.put(CevaCommonConstants.MERCHANT_NAME,
								userRS.getString(4));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(5));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(6));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(7));
						subJsonArray.add(json);
						json.clear();
					}
				} else {
					while (userRS.next()) {
						json.put("USER_ID", userRS.getString(1));
						json.put(CevaCommonConstants.STORE_ID,
								userRS.getString(2));
						json.put(CevaCommonConstants.MERCHANT_ID,
								userRS.getString(3));
						json.put(CevaCommonConstants.STATUS,
								userRS.getString(4));
						json.put(CevaCommonConstants.MAKER_DATE,
								userRS.getString(5));
						json.put(CevaCommonConstants.MAKER_ID,
								userRS.getString(6));
						json.put("serialNo", userRS.getString(7));
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
			logger.debug("SQLException in merchantAuthorization ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in merchantAuthorization 	  ["
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

	public ResponseDTO merchantAuthorizationAck(RequestDTO requestDTO) {

		logger.debug("Inside  MerchantAuthorizationAck.. ");
		Connection connection = null;

		String userQry = "{call MerchantMgmtPkg.MmsAuthorizationUpdateProc(?,?,?,?)}";
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
			logger.debug("SQLException in MerchantAuthorizationAck ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in MerchantAuthorizationAck  ["
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

	public ResponseDTO merchantModify(RequestDTO requestDTO) {

		logger.debug("Inside MerchantModify.. ");

		String address = null;
		String telephone = null;
		String addressLine1 = null;
		String addressLine2 = null;
		String addressLine3 = null;
		String telephoneNumber1 = null;
		String telephoneNumber2 = null;

		Connection connection = null;
		CallableStatement callableStatement = null;
		String modifyMerchantDetailsProc = "{call MerchantMgmtPkg.MerchantDetailsModifyProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String Msg = "";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			addressLine1 = requestJSON.getString(CevaCommonConstants.ADDRESS1);
			addressLine2 = requestJSON.getString(CevaCommonConstants.ADDRESS2);
		
			if (requestJSON.containsKey(CevaCommonConstants.ADDRESS3)) {
				addressLine3 = requestJSON
						.getString(CevaCommonConstants.ADDRESS3);
			} else {
				addressLine3 = "";
			}

			telephoneNumber1 = requestJSON
					.getString(CevaCommonConstants.TELEPHONE1);
			if (requestJSON.containsKey(CevaCommonConstants.TELEPHONE2)) {
				telephoneNumber2 = requestJSON
						.getString(CevaCommonConstants.TELEPHONE2);
			} else {
				telephoneNumber2 = "";
			}

			address = addressLine1 + "#" + addressLine2 + "#" + addressLine3;
			telephone = telephoneNumber1 + "#" + telephoneNumber2;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection
					.prepareCall(modifyMerchantDetailsProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.MERCHANT_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.MANAGER_NAME));
			callableStatement.setString(4,
					requestJSON.getString(CevaCommonConstants.EMAIL_ID));
			callableStatement.setString(5, address);
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.CITY));
			callableStatement.setString(7,
					requestJSON.getString(CevaCommonConstants.POBOXNUMBER));
			callableStatement.setString(8, telephone);
			callableStatement.setString(9,
					requestJSON.getString(CevaCommonConstants.MOBILE_NUMBER));
			callableStatement.setString(10,
					requestJSON.getString(CevaCommonConstants.FAX_NUMBER));
			callableStatement.setString(11, requestJSON
					.getString(CevaCommonConstants.PRIMARY_CONTACT_NAME));
			callableStatement.setString(12, requestJSON.getString(CevaCommonConstants.PRIMARY_CONTACT_NUMBER));
			callableStatement.setString(13, requestJSON.getString(CevaCommonConstants.BANK_ACCT_MULTI_DATA));

			callableStatement.registerOutParameter(14, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
			
			String area=requestJSON.getString("area");
			if(area.contains("-"))
			{
				area = area.split("-")[1].trim();
			}
			else area=area.trim();
			callableStatement.setString(16, area);
			callableStatement.setString(17,requestJSON.getString("postal_code"));
			callableStatement.setString(18,requestJSON.getString("lrnumber"));
			callableStatement.setString(19,requestJSON.getString("country"));
			callableStatement.setString(20,requestJSON.getString("memberType"));
			callableStatement.setString(21, requestJSON
					.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(14);
			Msg = callableStatement.getString(15);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			//responseDTO = getMerchantDetails(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {

				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in MerchantModify [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			logger.debug("Exception in MerchantModify [" + e.getMessage() + "]");
		} finally {

			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {

			}
			Msg = null;
			address = null;
			telephone = null;
			addressLine1 = null;
			addressLine2 = null;
			addressLine3 = null;
			telephoneNumber1 = null;
			telephoneNumber2 = null;
			modifyMerchantDetailsProc = null;
		}

		return responseDTO;
	}

	private static String getRandomInteger() {
		int aStart = 100000;
		int aEnd = 999999;
		Random aRandom = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * aRandom.nextDouble());
		Long randomNumber = (Long) (fraction + aStart);
		return randomNumber.toString();
	}

	public ResponseDTO fetchMerchantIds(RequestDTO requestDTO) {

	    Connection connection = null;

		logger.debug("Inside fetchMerchantIds.. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJsonArray = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;
		String profQry = null;
		requestJSON=requestDTO.getRequestJSON();

		profQry = "Select distinct MERCHANT_ID from USER_INFORMATION";

		try {

			responseDTO = new ResponseDTO();
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJsonArray = new JSONArray();
			merchantPstmt = connection.prepareStatement(profQry);
			merchantRS = merchantPstmt.executeQuery();
			json = new JSONObject();

			while (merchantRS.next()) {
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, merchantRS.getString(1));
				merchantJsonArray.add(json);
			}

			merchantRS.close();
			merchantPstmt.close();
			resultJson.put("MERCHANT_LIST", merchantJsonArray);
			json.clear();

			merchantMap.put("MERCHANT_LIST", resultJson);
			logger.debug("EntityMap [ :: " + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in GetMerchantDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap = null;
			resultJson = null;
			merchantJsonArray = null;
		}
		return responseDTO;
	}

	
	 public ResponseDTO fetchMerchantUserInfo(RequestDTO requestDTO) {

        Connection connection = null;
	    logger.debug("Inside Fetch MerchantUserInfo.. ");
 
		HashMap<String, Object> locationDataMap = null;
		JSONObject resultJson = null;
		JSONArray storeJSONArray = null;
		JSONArray terminalJSONArray = null;
		CallableStatement callableStmt = null;
		ResultSet locationRS = null;
		ResultSet userDesignationRS = null;
		ResultSet storeRS = null;
		ResultSet terminalRS = null;

		String locationQry = "";
		String merchantId = "";
		String storeid = "";
		 
		locationQry = "{call GetRightsPkg.pGetLocationDetails(?,?,?,?,?,?,?,?,?)}";
		try {
			
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			 
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");
			
		 
			merchantId = requestJSON.getString(CevaCommonConstants.MERCHANT_ID);
			storeid=requestJSON.getString(CevaCommonConstants.STORE_ID);
			locationDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			 

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			callableStmt = connection.prepareCall(locationQry);

			callableStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStmt.setString(2, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStmt.setString(3, merchantId);
			callableStmt.registerOutParameter(4, OracleTypes.CURSOR);
		    //callableStmt.registerOutParameter(5, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callableStmt.registerOutParameter(6, OracleTypes.CURSOR);
		    callableStmt.registerOutParameter(7, OracleTypes.VARCHAR);
		    callableStmt.setString(8, storeid);
		    callableStmt.registerOutParameter(9, OracleTypes.CURSOR);
		    
			callableStmt.execute();
			
			locationRS = (ResultSet) callableStmt.getObject(4);
			JSONObject json = new JSONObject();
			
			while (locationRS.next()) {
				
				json.put(locationRS.getString(2), locationRS.getString(2));
				json.put("storeId", storeid);
				//json.put(CevaCommonConstants.SELECT_KEY,locationRS.getString(2));
				//json.put(CevaCommonConstants.SELECT_VAL,locationRS.getString(1));
				//locationJSONArray.add(json);
				//json.clear();
				//json = null;
			}

			resultJson.put(CevaCommonConstants.LOCATION_LIST, json);
			logger.debug("location list *-*-"+resultJson);
			
			json = new JSONObject();
			storeJSONArray = new JSONArray();
			
			storeRS = (ResultSet) callableStmt.getObject(6);
			
			while (storeRS.next()) {
				
				json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
				
				storeJSONArray.add(json);
			}
		 
		    resultJson.put("MERCHANTID_LIST", storeJSONArray);
		    
		
		    json = new JSONObject();
		    terminalJSONArray = new JSONArray();
		    
		    terminalRS = (ResultSet) callableStmt.getObject(9);
		    
		    while (terminalRS.next()) {
		    	
		    	json.put(CevaCommonConstants.SELECT_KEY, terminalRS.getString(1));
		    	json.put(CevaCommonConstants.SELECT_VAL, terminalRS.getString(2));
		    	
		    	terminalJSONArray.add(json);
		    }
		    
		    resultJson.put("TERMINALID_LIST", terminalJSONArray);
		    
		
			/*userDesignationRS = (ResultSet) callableStmt.getObject(5);
			while (userDesignationRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,userDesignationRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,userDesignationRS.getString(1));
				userDesigJSONArray.add(json);
				json.clear();
				json = null;
			}
			
			resultJson.put("USER_DESIGNATION", userDesigJSONArray); */

			//groupType = callableStmt.getString(6);
			
			//logger.debug("GroupType [" + groupType + "]");
			
			//resultJson.put("GROUP_TYPE", groupType);
			
			//locationDataMap.put(CevaCommonConstants.LOCATION_INFO, resultJson);

			//logger.debug(" LocationDataMap [" + locationDataMap + "]");
			
			//responseDTO.setData(locationDataMap);

			/*userType = callableStmt.getString(8);
			resultJson.put("USER_LEVEL", userType);

			logger.debug(" userType [" + userType + "] ");

			if (userType.equals("MS") || userType.equals("MU")) {
				storeRS = (ResultSet) callableStmt.getObject(7);
				while (storeRS.next()) {
					json = new JSONObject();
					json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(2));
					json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
					
					storeJSONArray.add(json);
				}
			}

			logger.debug("inside [storeJSONArray:::" + storeJSONArray + "]");
			resultJson.put("MERCHANT_LIST", storeJSONArray); */

			/*String merchantAdminQry = "select MERCHANT_ID,MERCHANT_NAME from MERCHANT_MASTER where "
					                + "MERCHANT_ID not in (select DISTINCT MERCHANT_ID from USER_INFORMATION where MERCHANT_ID is not null or length(MERCHANT_ID)=16 )";

			PreparedStatement pstmt = connection.prepareStatement(merchantAdminQry);
			ResultSet rs= pstmt.executeQuery();
			merchantAdminJSONArray = new JSONArray();
			while(rs.next()){
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, rs.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, rs.getString(1));
				merchantAdminJSONArray.add(json);
			}
			resultJson.put("MERCHANT_ADMIN_LIST", merchantAdminJSONArray); */
			
			locationDataMap.put("MERCHANT_LIST", resultJson);
			
			logger.debug("EntityMap [ :: " + locationDataMap + "]");
			responseDTO.setData(locationDataMap);
			
			logger.debug("inside [responseDTO:::" + responseDTO + "]");

		} catch (SQLException e) {
			
			logger.debug("SQLException in GetAdminCraeteInfo [" + e.getMessage() + "]");
			
			responseDTO.addError("Internal Error Occured While Executing.");
			
		} catch (Exception e) {
			logger.debug("Exception in GetAdminCraeteInfo  [" + e.getMessage() + "]");
			
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStmt);
			DBUtils.closeResultSet(locationRS);
			DBUtils.closeResultSet(userDesignationRS);

			locationDataMap = null;
			resultJson = null;
			locationQry = null;
			merchantId = null;
			storeid = null;

		}
		return responseDTO;
	} 
	
	
	/*public ResponseDTO fetchMerchantUserInfo(RequestDTO requestDTO) {

		logger.debug("Inside Merchat UserCreateInfo.. ");
		HashMap<String, Object> merchantDataMap = null;

		JSONObject resultJson = null;
		JSONArray locationList = null;
		PreparedStatement locationPstmt = null;
		ResultSet locationRS = null;

		Connection connection = null;

		String entityQry = "Select OFFICE_CODE,OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null "
				         + "order by to_number(OFFICE_CODE)";
		JSONObject json = null;
		try {
			responseDTO = new ResponseDTO();

			merchantDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			locationList = new JSONArray();
			
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			locationPstmt = connection.prepareStatement(entityQry);

			locationRS = locationPstmt.executeQuery();
			json = new JSONObject();

			while (locationRS.next()) {
				json.put(locationRS.getString(2), locationRS.getString(2));
				
				//locationList.add(json);
			}

			resultJson.put(CevaCommonConstants.LOCATION_LIST, json);
			json.clear();

			locationPstmt.close();
			locationRS.close();
			 

			String merchantTypeQry = "Select CATE_CODE,CATE_CODE||'-'||CATE_DESCRIPTION from CATEGORY_MASTER order by CATE_DESCRIPTION";
			locationPstmt = connection.prepareStatement(merchantTypeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));
			}
			
			resultJson.put(CevaCommonConstants.MERCHANT_TYPE, json);
			json.clear();
			locationPstmt.close();
			locationRS.close();
			
			
			String countryCodeQry = "select  COUNTRY_CODE||'-'||COUNTRY_NAME,COUNTRY_NAME from COUNTRY_MASTER";
			locationPstmt = connection.prepareStatement(countryCodeQry);

			locationRS = locationPstmt.executeQuery();
			while (locationRS.next()) {
				json.put(locationRS.getString(1), locationRS.getString(2));

			}
			resultJson.put("Country_List", json);
			json.clear();
			locationPstmt.close();
			locationRS.close();

			merchantDataMap.put(CevaCommonConstants.MERCHANT_INFO, resultJson);
			logger.debug("MerchantDataMap [" + merchantDataMap + "]");
			responseDTO.setData(merchantDataMap);
			merchantTypeQry = null;
			 
		} catch (SQLException e) {
			logger.debug("Got SQLException in GetMerchantCreatePageInfo ["
					+ e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("Got Exception in 	GetMerchantCreatePageInfo ["
					+ e.getMessage() + "]");
		} finally {
			try {

				if (locationRS != null) {
					locationRS.close();
				}
				if (locationPstmt != null) {
					locationPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			merchantDataMap = null;
			resultJson = null;

		}

		return responseDTO;
	}*/
	 
	 public ResponseDTO insertMerchantUser(RequestDTO requestDTO) {
		 
			logger.debug("Inside Insert MerchantUser... ");
			
			Connection connection = null;
			CallableStatement callableStatement = null;

			//String insertBankInfoProc =  "{call insertICTAdminProc(?,?,?,?,?,?,?)}";
			
		    String insertmerchInfoProc = "{call INSERTMERCHANTUSERPROC(?,?,?,?,?,?,?,?)}";

			String multiData = "";
			String channelData = "";
			String makerId = "";
			String merchantID = "";
			String entityId = "";
			String userDetails = "";
			String ip = null;
			String auditQry = "";
			String typeuser = "";
			CallableStatement cstmt = null;
			
			HashMap<String, Object> userGrpDataMap = null;
			try {
				 
				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON [" + requestJSON + "]");

				multiData = requestJSON.getString(CevaCommonConstants.MULTI_DATA);
				channelData = requestJSON.getString(CevaCommonConstants.CATEGORY);
				makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
				ip = requestJSON.getString(CevaCommonConstants.IP);
				merchantID = requestJSON.getString("merchantID");
				entityId = requestJSON.getString("ENTITY_ID");
				typeuser = requestJSON.getString("GroupType");
				logger.debug("typeuser:::" + typeuser);
				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");
				
				//if(typeuser.equalsIgnoreCase("Merchant")) {
					   logger.debug("CUMING INSIDE MERCHANT");
				       callableStatement = connection.prepareCall(insertmerchInfoProc);
				  //}
				
				callableStatement.setString(1, makerId);
				callableStatement.setString(2, multiData);
				callableStatement.setString(3, channelData);
				callableStatement.setString(4, merchantID);
				callableStatement.setString(5, entityId);
				callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
				callableStatement.setString(8,requestJSON.getString(CevaCommonConstants.IP));
				callableStatement.executeUpdate();
				
				int resCnt = callableStatement.getInt(6);
				
				logger.debug("ResultCnt from DB [" + resCnt + "]");
				
				if (resCnt == 1) {
					userGrpDataMap = new HashMap<String, Object>();
					responseJSON = new JSONObject();
					userDetails = callableStatement.getString(7);
					logger.debug("User Details  [" + userDetails + "]");
					if (userDetails.indexOf("SUCCESS")!=-1)
					{
					responseJSON.put("USER_DETAILS", userDetails);
					userGrpDataMap.put("USER_DETAILS", responseJSON);
					}
					else{
						System.out.println("JCCCCCCCCCCCCCCCCCCCCCCCCCC");
						responseDTO.addError(userDetails);
						
					}
					
					responseDTO.setData(userGrpDataMap);

				}else{
					//responseDTO = getAdminCraeteInfo(requestDTO);
					responseDTO.addError("Merchant Admin Creation failed.");
				}
				
				auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
				cstmt = connection.prepareCall(auditQry);
				cstmt.setString(1, "insertmerchInfoProc");
				cstmt.setString(2, makerId);
				cstmt.setString(3, "WEB");
				if (resCnt == 1) {
				    cstmt.setString(4, " New User created with Group ID ["+merchantID+"] and Entity ID ["+entityId+"]");
				} else if (resCnt == -1) {
					cstmt.setString(4, " Given Data Already Exists. ");	
				} else {
					cstmt.setString(4, " New User Creation failed. ");
				}
				cstmt.setString(5, ip);
				cstmt.setString(6, " ");
				cstmt.setString(7, " ");
				cstmt.setString(8, " ");

				int i = cstmt.executeUpdate();
				if (i == 1) {
					logger.debug("Action Identification -> Successfully Inserted "+ ip);
				} else {
					logger.debug("Action Identification -> insertion failed due to some error "+ ip);
				} 

			} catch (SQLException e) {
				logger.debug("SQLException in insertIctAdminDetails ["+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {
				logger.debug("Exception in insertIctAdminDetails  [" + e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callableStatement);
				DBUtils.closeCallableStatement(cstmt);
				//insertBankInfoProc = null;
				multiData = null;
				makerId = null;
				merchantID = null;
				entityId = null;
				userDetails = null;
				userGrpDataMap = null;
			}

			return responseDTO;
		}
	
}
