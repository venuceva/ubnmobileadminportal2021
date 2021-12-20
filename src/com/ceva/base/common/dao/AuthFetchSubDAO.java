package com.ceva.base.common.dao;

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
import com.ceva.util.DBUtils;

public class AuthFetchSubDAO {

	Logger logger = Logger.getLogger(AuthFetchSubDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	
/*	
	private InputStream fileInputStream;
	private InputStream inputStream;
	ResourceBundle bundle = null;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
	return fileInputStream;
	}*/

	public ResponseDTO UserAuthRecordData(RequestDTO requestDTO) {

		logger.debug("Inside UserAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String auth_code = "";
		String ref_no = "";

		String name[] = null;
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			auth_code = requestJSON.getString("auth_code");
			ref_no = requestJSON.getString("ref_no");
			logger.debug("ref_no:::::>>>>" + ref_no);
			/*
			 * merchantQry =
			 * "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY')," +
			 * "user_level,(select distinct cbl.OFFICE_NAME from BRANCH_MASTER cbl where cbl.office_code=location),"
			 * +
			 * "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,(select login_user_id from user_login_credentials_temp where ref_no =? ),decode(USER_STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized',USER_STATUS) from user_information_temp "
			 * + "where ref_no=?";
			 */
			if (auth_code.equalsIgnoreCase("MODUSERAUTH")) {

				merchantQry = " select UIT.user_name ,UIT.emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY'),"
						+ " UIT.user_level,location,"
						+ " UIT.email,UIT.LOCAL_RES_NUM,UIT.LOCAL_OFF_NUM,UIT.mobile_no,UIT.fax,(select login_user_id from user_login_credentials_temp where ref_num =? ),decode(UIT.USER_STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized',USER_STATUS),UL.LOGIN_USER_ID,UIT.MERCHANT_ID,UIT.STORE_ID"
						+ " from user_information_temp UIT,USER_LOGIN_CREDENTIALS UL"
						+ " where UIT.ref_num=? AND UL.COMMON_ID=UIT.COMMON_ID";
			} else
				merchantQry = " select UIT.user_name ,UIT.emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY'),"
						+ " UIT.user_level,location,"
						+ " UIT.email,UIT.LOCAL_RES_NUM,UIT.LOCAL_OFF_NUM,UIT.mobile_no,UIT.fax,(select login_user_id from user_login_credentials_temp where ref_num =? ),decode(UIT.USER_STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized',USER_STATUS),UL.LOGIN_USER_ID,UIT.MERCHANT_ID,UIT.STORE_ID"
						+ " from user_information_temp UIT,USER_LOGIN_CREDENTIALS_TEMP UL"
						+ " where UIT.ref_num=? AND UL.COMMON_ID=UIT.COMMON_ID";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantPstmt.setString(2, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				name = merchantRS.getString(1).split("\\ ");
				resultJson.put("first_name", name[0]);
				resultJson.put("last_name", name[1]);
				resultJson.put("emp_id", merchantRS.getString(2));
				resultJson.put("expiry_date", merchantRS.getString(3));
				resultJson.put("user_level", merchantRS.getString(4));
				resultJson.put("location", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("res_no", merchantRS.getString(7));
				resultJson.put("off_no", merchantRS.getString(8));
				resultJson.put("mobile_no", merchantRS.getString(9));
				resultJson.put("fax", merchantRS.getString(10));
				resultJson.put("user_id", merchantRS.getString(11));
				resultJson.put("user_status", merchantRS.getString(12));
				resultJson.put("user_id", merchantRS.getString(13));
				resultJson.put("merchantId", merchantRS.getString(14));
				resultJson.put("storeId", merchantRS.getString(15));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
			name = null;
		}

		return responseDTO;
	}

	public ResponseDTO ServiceAuthRecordData(RequestDTO requestDTO) {

		logger.debug("Inside UserAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = "select SERVICE_CODE,SERVICE_NAME,decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized','R','Rejected') from SERVICE_MASTER_TEMP where REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("ServiceCode", merchantRS.getString(1));
				resultJson.put("ServiceName", merchantRS.getString(2));
				resultJson.put("Status", merchantRS.getString(3));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO binAuthRecordData(RequestDTO requestDTO) {

		logger.debug("Inside binAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = "Select BANK_CODE,BANK_NAME,BIN,BIN_DESC,decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized','R','Rejected') from BANK_MASTER_TEMP WHERE REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("bankCode", merchantRS.getString(1));
				resultJson.put("bankName", merchantRS.getString(2));
				resultJson.put("bin", merchantRS.getString(3));
				resultJson.put("binDescription", merchantRS.getString(4));
				resultJson.put("status", merchantRS.getString(5));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO subServiceAuthRecordData(RequestDTO requestDTO) {

		logger.debug("Inside SubServiceAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = "select SERVICE_CODE,SERVICE_NAME,decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized','R','Rejected'),SUB_SERVICE_CODE,SUB_SERVICE_NAME from SERVICE_MASTER_TEMP where  SERVICE_TYPE ='SUB' and  REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("ServiceCode", merchantRS.getString(1));
				resultJson.put("ServiceName", merchantRS.getString(2));
				resultJson.put("Status", merchantRS.getString(3));
				resultJson.put("subserviceCode", merchantRS.getString(4));
				resultJson.put("subServiceName", merchantRS.getString(5));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO viewFeeDetails(RequestDTO requestDTO) {

		logger.debug("Inside FeeCodeAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		StringBuilder eachrow = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			/*
			 * merchantQry =
			 * "select SERVICE_CODE,SERVICE_NAME,decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized'),SUB_SERVICE_CODE,SUB_SERVICE_NAME from SERVICE_MASTER_TEMP where  SERVICE_TYPE ='SUB' and  REF_NUM=?"
			 * ;
			 */

			merchantQry = "Select (select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SM.SERVICE_NAME and rownum<2),"
					+ "SM.SUB_SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SUB_SERVICE_CODE,NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
					+ "FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID,to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),FM.ONUSOFUSFLAG,FM.FEE_NAME  "
					+ "from FEE_MASTER_TEMP FM,SERVICE_MASTER SM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
					+ "SM.SUB_SERVICE_CODE=FM.SUB_SERVICE_CODE and  FM.REF_NUM=?";

			/*
			 * merchantQry =
			 * "Select (select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SM.SERVICE_NAME and rownum<2),"
			 * +
			 * "SM.SUB_SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SUB_SERVICE_CODE, NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
			 * +
			 * " FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID, to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), "
			 * +
			 * "FM.ONUSOFUSFLAG, FM.FEE_NAME,bm.BIN_DESC,AM.NETWORK_ID_DESC  from FEE_MASTER_TEMP FM,SERVICE_MASTER SM,"
			 * +
			 * " BANK_MASTER bm, ACQUIRER_MASTER AM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
			 * +
			 * "SM.SUB_SERVICE_CODE=FM.SUB_SERVICE_CODE  and bm.BIN = substr(FM.ACQDETAILS, 0, INSTR(FM.ACQDETAILS, ',')-1) "
			 * +
			 * "AND AM.NETWORK_ID = substr(FM.ACQDETAILS, INSTR(FM.ACQDETAILS, ',')+1)  and  FM.REF_NUM=?"
			 * ;
			 */

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();
			int i = 0;
			while (merchantRS.next()) {

				resultJson.put("SERVICE_NAME", merchantRS.getString(1));
				resultJson.put("SUB_SERVICE_NAME", merchantRS.getString(2));
				resultJson.put("FEE_CODE", merchantRS.getString(3));
				resultJson.put("SERVICE_CODE", merchantRS.getString(4));
				resultJson.put("SUB_SERVICE_CODE", merchantRS.getString(5));
				resultJson.put("FALT_PERCENT", merchantRS.getString(6));
				resultJson.put("ACCOUNT_MULTI_DATA", merchantRS.getString(7));
				
				eachrow = new StringBuilder(500);
				if (i == 0) {
					eachrow.append(merchantRS.getString(8));
				} else {
					eachrow.append("#").append(merchantRS.getString(8));
				}

				resultJson.put("ACQDET", merchantRS.getString(9));
				resultJson.put("MAKER_ID", merchantRS.getString(10));
				resultJson.put("MAKER_DATE", merchantRS.getString(11));

				resultJson.put("ONUS_OFFUS_FLAG", merchantRS.getString(12));
				resultJson.put("FEE_NAME", merchantRS.getString(13));
				i++;
			}
			
			logger.debug("in the slab details"+eachrow.toString());
			

			resultJson.put("SLAB", eachrow.toString());

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO modifyFeeDetails(RequestDTO requestDTO) {

		logger.debug("Inside Modify FeeCodeAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		StringBuilder eachrow = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			/*
			 * merchantQry =
			 * "select SERVICE_CODE,SERVICE_NAME,decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized'),SUB_SERVICE_CODE,SUB_SERVICE_NAME from SERVICE_MASTER_TEMP where  SERVICE_TYPE ='SUB' and  REF_NUM=?"
			 * ;
			 */

			merchantQry = "Select (select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SM.SERVICE_NAME and rownum<2),"
					+ "SM.SUB_SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SUB_SERVICE_CODE,NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
					+ "FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID,to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),FM.ONUSOFUSFLAG,FM.FEE_NAME  "
					+ "from FEE_MASTER_TEMP FM,SERVICE_MASTER SM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
					+ "SM.SUB_SERVICE_CODE=FM.SUB_SERVICE_CODE and  FM.REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();
			int i = 0;
			while (merchantRS.next()) {

				resultJson.put("SERVICE_NAME", merchantRS.getString(1));
				resultJson.put("SUB_SERVICE_NAME", merchantRS.getString(2));
				resultJson.put("FEE_CODE", merchantRS.getString(3));
				resultJson.put("SERVICE_CODE", merchantRS.getString(4));
				resultJson.put("SUB_SERVICE_CODE", merchantRS.getString(5));
				resultJson.put("FALT_PERCENT", merchantRS.getString(6));
				resultJson.put("ACCOUNT_MULTI_DATA", merchantRS.getString(7));

				eachrow = new StringBuilder(500);
				if (i == 0) {
					eachrow.append(merchantRS.getString(8));
				} else {
					eachrow.append("#").append(merchantRS.getString(8));
				}
				
				
				/*if (i == 0) {
					slabDet += merchantRS.getString(8);
				} else {
					slabDet += "#" + merchantRS.getString(8);
				}*/

				resultJson.put("ACQDET", merchantRS.getString(9));
				resultJson.put("MAKER_ID", merchantRS.getString(10));
				resultJson.put("MAKER_DATE", merchantRS.getString(11));

				resultJson.put("ONUS_OFFUS_FLAG", merchantRS.getString(12));
				resultJson.put("FEE_NAME", merchantRS.getString(13));
				i++;
			}

			resultJson.put("SLAB",eachrow.toString());

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO viewTerminalDetails(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = " SELECT MERCHANT_ID ,STORE_ID,TERMINAL_ID,MODEL_NO,SERIAL_NO,TERMINAL_MAKE,to_char(MAKER_DTTM,'DD-MM-YYYY'),decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized','R','Rejected') "
					+ " FROM TERMINAL_MASTER_TEMP WHERE REF_NUM = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("TerminalID", merchantRS.getString(3));
				resultJson.put("ModelNumber", merchantRS.getString(4));
				resultJson.put("SerialNumber", merchantRS.getString(5));
				resultJson.put("TerminalMake", merchantRS.getString(6));
				resultJson.put("terminaldate", merchantRS.getString(7));
				resultJson.put("status", merchantRS.getString(8));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO AssignTerminalServiceauth(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;
		JSONObject Json = null;
		JSONArray terminalJsonArray = null;
		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";
		String TerminalID = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			Json = new JSONObject();
			terminalJsonArray = new JSONArray();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");
			merchantQry = "SELECT TERMINAL_ID FROM TERMINAL_SERVICE_MAPPING_TEMP  WHERE REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				TerminalID = merchantRS.getString(1);
			}

			logger.debug("Terminal ID>>>>" + TerminalID);
			merchantPstmt.close();
			merchantRS.close();

			merchantQry = " SELECT MERCHANT_ID ,STORE_ID,TERMINAL_ID,MODEL_NO,SERIAL_NO,TERMINAL_MAKE,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),decode(STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized') "
					+ " FROM TERMINAL_MASTER WHERE TERMINAL_ID = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, TerminalID);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("TerminalID", merchantRS.getString(3));
				resultJson.put("ModelNumber", merchantRS.getString(4));
				resultJson.put("SerialNumber", merchantRS.getString(5));
				resultJson.put("TerminalMake", merchantRS.getString(6));
				resultJson.put("terminaldate", merchantRS.getString(7));

			}

			merchantPstmt.close();
			merchantRS.close();
			merchantQry = null;

			merchantQry = "select TERMINAL_ID,DECODE(SERVICE_CODE,'BEQ','BALANCE ENQUIRY','CDP','CASH DEPOSIT','PUR','PURCHASE','WDL','CASH WITHDRAWL'),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') "
					+ " from TERMINAL_SERVICE_MAPPING_TEMP WHERE AUTH_FLAG='AUP' and TERMINAL_ID=? ";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, TerminalID);

			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {

				Json.put("TerminalID", merchantRS.getString(1));
				Json.put("service_code", merchantRS.getString(2));
				Json.put("makerid", merchantRS.getString(3));
				Json.put("makerdateandtime", merchantRS.getString(4));
				terminalJsonArray.add(Json);
			}
			logger.debug("assign terminal service array::::"
					+ terminalJsonArray);
			resultJson.put("Assign_Terminal_Services", terminalJsonArray);
			merchantPstmt.close();
			merchantRS.close();
			merchantQry = null;

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO TerminalstatusDetails(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";
		String Terminalid = "";
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = " SELECT TERMINAL_ID  FROM TERMINAL_MASTER_TEMP WHERE REF_NUM = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				Terminalid = merchantRS.getString(1);

			}
			logger.debug("TerminalID>>>>>>:" + Terminalid);
			resultJson.clear();
			merchantPstmt.close();
			merchantRS.close();
			merchantQry = null;

			merchantQry = " SELECT MERCHANT_ID ,STORE_ID,TERMINAL_ID,MODEL_NO,SERIAL_NO,TERMINAL_MAKE,to_char(MAKER_DTTM,'DD-MM-YYYY'),decode(STATUS,'A','Active','L','De-Active','D','InActive','N','Not Authorized') "
					+ " FROM TERMINAL_MASTER WHERE TERMINAL_ID = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, Terminalid);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("TerminalID", merchantRS.getString(3));
				resultJson.put("ModelNumber", merchantRS.getString(4));
				resultJson.put("SerialNumber", merchantRS.getString(5));
				resultJson.put("TerminalMake", merchantRS.getString(6));
				resultJson.put("terminaldate", merchantRS.getString(7));
				resultJson.put("status", merchantRS.getString(8));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO TerminalModificationAuth(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";
		String Terminalid = "";
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = " SELECT TERMINAL_ID  FROM TERMINAL_MASTER_TEMP WHERE REF_NUM = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				Terminalid = merchantRS.getString(1);

			}
			logger.debug("TerminalID>>>>>>:" + Terminalid);
			resultJson.clear();
			merchantPstmt.close();
			merchantRS.close();
			merchantQry = null;

/*			merchantQry = " SELECT MERCHANT_ID ,STORE_ID,TERMINAL_ID,MODEL_NO,SERIAL_NO,TERMINAL_MAKE,to_char(TERMINAL_DATE,'DD-MM-YYYY'),decode(STATUS,'A','Active','L','De-Active','D','InActive','N','Not Authorized') "
					+ " FROM TERMINAL_MASTER WHERE TERMINAL_ID = ?";*/
			
			merchantQry = " SELECT MERCHANT_ID ,STORE_ID,TERMINAL_ID,MODEL_NO,SERIAL_NO,TERMINAL_MAKE,to_char(TERMINAL_DATE,'DD-MM-YYYY'),decode(STATUS,'A','Active','L','De-Active','D','InActive','N','Not Authorized') "
					+ " FROM TERMINAL_MASTER_TEMP WHERE REF_NUM = ?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("TerminalID", merchantRS.getString(3));
				resultJson.put("ModelNumber", merchantRS.getString(4));
				resultJson.put("SerialNumber", merchantRS.getString(5));
				resultJson.put("TerminalMake", merchantRS.getString(6));
				resultJson.put("terminaldate", merchantRS.getString(7));
				resultJson.put("status", merchantRS.getString(8));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO viewStoreDetails(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		PreparedStatement psmt = null;
		String ref_no = "";
		JSONArray bankArray = null;
		JSONArray agentArray = null;
		ResultSet rs = null;
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = " Select MERCHANT_ID,STORE_ID,STORE_NAME,LOCATION,MANAGER_NAME,EMAIL,ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,KRA_PIN,TILL_ID,"
					+ " (select AGEN_OR_BILLER  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID) ,AREA,POSTALCODE,LRNUMBER,COUNTRY,"
					+ " (select MM.MERCHANT_ADMIN  from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID), "
					+ " SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS') , (Select OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where OFFICE_CODE = SM.LOCATION) AS OfficeCode,(select cate_code||'-'||CATE_DESCRIPTION  from CATEGORY_MASTER where cate_code=(select MERCHANT_TYPE  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID))"
					+ " from STORE_MASTER_TEMP SM where  SM.REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("StoreName", merchantRS.getString(3));
				resultJson.put("location", merchantRS.getString(4));
				resultJson.put("managername", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("address", merchantRS.getString(7));
				resultJson.put("city", merchantRS.getString(8));
				resultJson.put("poboxnum", merchantRS.getString(9));
				resultJson.put("telephonenum", merchantRS.getString(10));
				resultJson.put("mobile", merchantRS.getString(11));
				resultJson.put("fax", merchantRS.getString(12));
				resultJson.put("pricontactname", merchantRS.getString(13));
				resultJson.put("pricontactnum", merchantRS.getString(14));
				resultJson.put("kra_pin", merchantRS.getString(15));
				resultJson.put("titleid", merchantRS.getString(16));
				resultJson.put("MemberType", merchantRS.getString(17));
				resultJson.put("area", merchantRS.getString(18));
				resultJson.put("postalcode", merchantRS.getString(19));
				resultJson.put("lrnumber", merchantRS.getString(20));
				resultJson.put("merchanttest", merchantRS.getString(21));
				resultJson.put("makerid", merchantRS.getString(22));
				resultJson.put("makerdttm", merchantRS.getString(23));
				resultJson.put("authid", merchantRS.getString(24));
				resultJson.put("authidttm", merchantRS.getString(25));
				resultJson.put("locationcity", merchantRS.getString(27));
				resultJson.put("merchnatType", merchantRS.getString(28));

			}

			merchantPstmt.close();
			merchantRS.close();

			String merchantQry1 = "SELECT MM.MERCHANT_NAME FROM MERCHANT_MASTER MM,STORE_MASTER_TEMP SM WHERE SM.MERCHANT_ID=MM.MERCHANT_ID AND SM.REF_NUM=?";
			psmt = connection.prepareStatement(merchantQry1);
			psmt.setString(1, ref_no);
			rs = psmt.executeQuery();
			String merchantName;

			if (rs.next()) {
				merchantName = rs.getString(1);
				logger.debug("Merchant Name:[" + merchantName + "]");
				resultJson.put("merchantName", merchantName);
			}
			psmt.close();
			rs.close();

			/*
			 * String DoucnemtQry=
			 * "SELECT ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH FROM BANK_ACCT_MASTER WHERE REF_NUM=?"
			 * ;
			 */
			String DoucnemtQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_NAME, ' '),CASE WHEN BAM.BANK_BRANCH IS NOT NULL THEN nvl((SELECT IBM.OFFICE_CODE||'-'||IBM.OFFICE_NAME FROM BRANCH_MASTER IBM WHERE IBM.OFFICE_CODE=BAM.BANK_BRANCH),' ')ELSE nvl(BAM.BANK_NAME,' ') END,TRANSFER_CODE FROM  BANK_ACCT_MASTER BAM WHERE  ref_num=? ";
			psmt = connection.prepareStatement(DoucnemtQry);
			psmt.setString(1, ref_no);
			rs = psmt.executeQuery();
			JSONObject json = null;
			bankArray = new JSONArray();
			if (rs.next()) {
				json = new JSONObject();
				json.put("accountNO", rs.getString(1));
				json.put("accountDesc", rs.getString(2));
				json.put("bankcode", rs.getString(3));
				json.put("branccode", rs.getString(4));
				bankArray.add(json);

			}
			psmt.close();
			rs.close();
			logger.debug("Bankaccountmultidata:::[" + bankArray + "]");
			resultJson.put("BankmultData", bankArray);

			String AgentQry1 = "SELECT BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MAKER_ID FROM AGENT_INFORMATION WHERE REF_NUM=?";

			psmt = connection.prepareStatement(AgentQry1);
			psmt.setString(1, ref_no);
			rs = psmt.executeQuery();
			JSONObject json1 = null;
			agentArray = new JSONArray();
			if (rs.next()) {
				json1 = new JSONObject();
				json1.put("bankagentno", rs.getString(1));
				json1.put("mpesaagentno", rs.getString(2));
				json1.put("airtelagentno", rs.getString(3));
				json1.put("orangeagentno", rs.getString(4));
				json1.put("makerid", rs.getString(5));
				agentArray.add(json1);

			}

			logger.debug("agent json:::[" + agentArray + "]");
			resultJson.put("agentmultidate", agentArray);

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closePreparedStatement(psmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO StoreModifyDetailsauth(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		PreparedStatement psmt = null;
		String ref_no = "";
		String Storeid = "";
		JSONArray bankArray = null;
		JSONArray agentArray = null;
		ResultSet rs = null;
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");
			merchantQry = "SELECT STORE_ID from STORE_MASTER_TEMP SM where  REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				Storeid = merchantRS.getString(1);
			}
			logger.debug("Store ID:" + Storeid);
			merchantRS.close();
			merchantPstmt.close();
			merchantQry = null;

			/*
			 * merchantQry =
			 * " Select MERCHANT_ID,STORE_ID,STORE_NAME,LOCATION,MANAGER_NAME,EMAIL,ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,KRA_PIN,TILL_ID,"
			 * +
			 * " (select AGEN_OR_BILLER  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID) ,AREA,POSTALCODE,LRNUMBER,COUNTRY,"
			 * +
			 * " (select MM.MERCHANT_ADMIN  from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID), "
			 * +
			 * " SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
			 * +" from STORE_MASTER SM where  SM.STORE_ID=?";
			 */

			merchantQry = "Select MERCHANT_ID,STORE_ID,STORE_NAME,"
					+ "(Select OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where OFFICE_CODE = SM.LOCATION) "
					+ "AS OfficeCode,MANAGER_NAME,EMAIL,ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,"
					+ "PRI_CONTACT_NO,KRA_PIN,TILL_ID, (select AGEN_OR_BILLER  from MERCHANT_MASTER where "
					+ "MERCHANT_ID=SM.MERCHANT_ID) ,AREA,POSTALCODE,LRNUMBER,COUNTRY, (select MM.MERCHANT_ADMIN  "
					+ "from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID),  SM.MAKER_ID,"
					+ "to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
					+ " from STORE_MASTER SM where  SM.STORE_ID=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, Storeid);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("StoreName", merchantRS.getString(3));
				resultJson.put("location", merchantRS.getString(4));
				resultJson.put("managername", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("address", merchantRS.getString(7));
				resultJson.put("city", merchantRS.getString(8));
				resultJson.put("poboxnum", merchantRS.getString(9));
				resultJson.put("telephonenum", merchantRS.getString(10));
				resultJson.put("mobile", merchantRS.getString(11));
				resultJson.put("fax", merchantRS.getString(12));
				resultJson.put("pricontactname", merchantRS.getString(13));
				resultJson.put("pricontactnum", merchantRS.getString(14));
				resultJson.put("kra_pin", merchantRS.getString(15));
				resultJson.put("titleid", merchantRS.getString(16));
				resultJson.put("merchnatType", merchantRS.getString(17));
				resultJson.put("area", merchantRS.getString(18));
				resultJson.put("postalcode", merchantRS.getString(19));
				resultJson.put("lrnumber", merchantRS.getString(20));
				resultJson.put("merchanttest", merchantRS.getString(21));
				resultJson.put("makerid", merchantRS.getString(22));
				resultJson.put("makerdttm", merchantRS.getString(23));
				resultJson.put("authid", merchantRS.getString(24));
				resultJson.put("authidttm", merchantRS.getString(25));

			}

			merchantPstmt.close();
			merchantRS.close();

			String merchantQry1 = "SELECT MM.MERCHANT_NAME FROM MERCHANT_MASTER MM,STORE_MASTER_TEMP SM WHERE SM.MERCHANT_ID=MM.MERCHANT_ID AND SM.STORE_ID=?";
			psmt = connection.prepareStatement(merchantQry1);
			psmt.setString(1, Storeid);
			rs = psmt.executeQuery();
			String merchantName;

			if (rs.next()) {
				merchantName = rs.getString(1);
				logger.debug("Merchant Name:[" + merchantName + "]");
				resultJson.put("merchantName", merchantName);
			}
			psmt.close();
			rs.close();

			String DoucnemtQry = "SELECT ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH FROM BANK_ACCT_MASTER WHERE STORE_ID=?";

			psmt = connection.prepareStatement(DoucnemtQry);
			psmt.setString(1, Storeid);
			rs = psmt.executeQuery();
			JSONObject json = null;
			bankArray = new JSONArray();
			if (rs.next()) {
				json = new JSONObject();
				json.put("accountNO", rs.getString(1));
				json.put("accountDesc", rs.getString(2));
				json.put("bankcode", rs.getString(3));
				json.put("branccode", rs.getString(4));
				bankArray.add(json);

			}
			psmt.close();
			rs.close();
			logger.debug("Bankaccountmultidata:::[" + bankArray + "]");
			resultJson.put("BankmultData", bankArray);

			String AgentQry1 = "SELECT BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MAKER_ID FROM AGENT_INFORMATION WHERE STORE_ID=?";

			psmt = connection.prepareStatement(AgentQry1);
			psmt.setString(1, Storeid);
			rs = psmt.executeQuery();
			JSONObject json1 = null;
			agentArray = new JSONArray();
			if (rs.next()) {
				json1 = new JSONObject();
				json1.put("bankagentno", rs.getString(1));
				json1.put("mpesaagentno", rs.getString(2));
				json1.put("airtelagentno", rs.getString(3));
				json1.put("orangeagentno", rs.getString(4));
				json1.put("makerid", rs.getString(5));
				agentArray.add(json1);

			}

			logger.debug("agent json:::[" + agentArray + "]");
			resultJson.put("agentmultidate", agentArray);

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closePreparedStatement(psmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO StoreStatusauthdetails(RequestDTO requestDTO) {

		logger.debug("Inside TerminalAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		PreparedStatement psmt = null;
		String ref_no = "";
		JSONArray bankArray = null;
		JSONArray agentArray = null;
		ResultSet rs = null;
		String StoreID = "";
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = " Select STORE_ID  from STORE_MASTER_TEMP SM where  SM.REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				StoreID = merchantRS.getString(1);

			}
			logger.debug("StoreID>>>>>>>>::" + StoreID);
			merchantPstmt.close();
			merchantRS.close();
			merchantQry = null;

			merchantQry = " Select MERCHANT_ID,STORE_ID,STORE_NAME,(Select OFFICE_CODE||'-'||OFFICE_NAME from BRANCH_MASTER where OFFICE_CODE = SM.LOCATION) AS OfficeCode,MANAGER_NAME,EMAIL,ADDRESS,CITY,PO_BOX_NO,TELEPHONE_NO,MOBILE_NO,FAX_NO,PRI_CONTACT_NAME,PRI_CONTACT_NO,KRA_PIN,TILL_ID,"
					+ " (select AGEN_OR_BILLER  from MERCHANT_MASTER where MERCHANT_ID=SM.MERCHANT_ID) ,AREA,POSTALCODE,LRNUMBER,COUNTRY,"
					+ " (select MM.MERCHANT_ADMIN  from MERCHANT_MASTER MM where MM.MERCHANT_ID=SM.MERCHANT_ID), "
					+ " SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'), SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
					+ " from STORE_MASTER SM where  SM.STORE_ID=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, StoreID);

			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("StoreID", merchantRS.getString(2));
				resultJson.put("StoreName", merchantRS.getString(3));
				resultJson.put("location", merchantRS.getString(4));
				resultJson.put("managername", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("address", merchantRS.getString(7));
				resultJson.put("city", merchantRS.getString(8));
				resultJson.put("poboxnum", merchantRS.getString(9));
				resultJson.put("telephonenum", merchantRS.getString(10));
				resultJson.put("mobile", merchantRS.getString(11));
				resultJson.put("fax", merchantRS.getString(12));
				resultJson.put("pricontactname", merchantRS.getString(13));
				resultJson.put("pricontactnum", merchantRS.getString(14));
				resultJson.put("kra_pin", merchantRS.getString(15));
				resultJson.put("titleid", merchantRS.getString(16));
				resultJson.put("merchnatType", merchantRS.getString(17));
				resultJson.put("area", merchantRS.getString(18));
				resultJson.put("postalcode", merchantRS.getString(19));
				resultJson.put("lrnumber", merchantRS.getString(20));
				resultJson.put("merchanttest", merchantRS.getString(21));
				resultJson.put("makerid", merchantRS.getString(22));
				resultJson.put("makerdttm", merchantRS.getString(23));
				resultJson.put("authid", merchantRS.getString(24));
				resultJson.put("authidttm", merchantRS.getString(25));

			}

			merchantPstmt.close();
			merchantRS.close();

			String merchantQry1 = "SELECT MM.MERCHANT_NAME FROM MERCHANT_MASTER MM,STORE_MASTER_TEMP SM WHERE SM.MERCHANT_ID=MM.MERCHANT_ID AND SM.REF_NUM=?";
			psmt = connection.prepareStatement(merchantQry1);
			psmt.setString(1, ref_no);
			rs = psmt.executeQuery();
			String merchantName;

			if (rs.next()) {
				merchantName = rs.getString(1);
				logger.debug("Merchant Name:[" + merchantName + "]");
				resultJson.put("merchantName", merchantName);
			}
			psmt.close();
			rs.close();

			String DoucnemtQry = "SELECT ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH FROM BANK_ACCT_MASTER WHERE STORE_ID=?";

			psmt = connection.prepareStatement(DoucnemtQry);
			psmt.setString(1, StoreID);
			rs = psmt.executeQuery();
			JSONObject json = null;
			bankArray = new JSONArray();
			if (rs.next()) {
				json = new JSONObject();
				json.put("accountNO", rs.getString(1));
				json.put("accountDesc", rs.getString(2));
				json.put("bankcode", rs.getString(3));
				json.put("branccode", rs.getString(4));
				bankArray.add(json);

			}
			psmt.close();
			rs.close();
			logger.debug("Bankaccountmultidata:::[" + bankArray + "]");
			resultJson.put("BankmultData", bankArray);

			String AgentQry1 = "SELECT BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MAKER_ID FROM AGENT_INFORMATION WHERE STORE_ID=?";

			psmt = connection.prepareStatement(AgentQry1);
			psmt.setString(1, StoreID);
			rs = psmt.executeQuery();
			JSONObject json1 = null;
			agentArray = new JSONArray();
			if (rs.next()) {
				json1 = new JSONObject();
				json1.put("bankagentno", rs.getString(1));
				json1.put("mpesaagentno", rs.getString(2));
				json1.put("airtelagentno", rs.getString(3));
				json1.put("orangeagentno", rs.getString(4));
				json1.put("makerid", rs.getString(5));
				agentArray.add(json1);

			}

			logger.debug("agent json:::[" + agentArray + "]");
			resultJson.put("agentmultidate", agentArray);

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closePreparedStatement(psmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	

	public ResponseDTO MerchantStatusAuth(RequestDTO requestDTO) {

		PreparedStatement merchantprmPstmt = null;
		ResultSet merchantPrmRS = null;
		Connection connection = null;

		logger.debug("Inside GetMerchantViewDetails... ");
		HashMap<String, Object> merchantDataMap = null;

		JSONArray jsonArray = null;
		JSONObject json = null;
		String merchantid = "";
		String merchantPrmQry = "";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();
			merchantDataMap = new HashMap<String, Object>();

			merchantPrmQry = "Select MM.MERCHANT_ID from MERCHANT_MASTER_TEMP MM where ref_num=?";
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));
			merchantPrmRS = merchantprmPstmt.executeQuery();
			while (merchantPrmRS.next()) {
				merchantid = merchantPrmRS.getString(1);
			}
			logger.debug("Merchant ID:::" + merchantid);

			merchantprmPstmt.close();
			merchantPrmRS.close();
			merchantPrmQry = null;

			merchantPrmQry = "Select MM.MERCHANT_ID,MM.MERCHANT_NAME,(select office_name from BRANCH_MASTER where office_code=MM.LOCATION), "
					+ "MM.KRA_PIN,(select CATE_DESCRIPTION from CATEGORY_MASTER where cate_code=MM.MERCHANT_TYPE),"
					+ " MM.MANAGER_NAME,MM.EMAIL,MM.ADDRESS,MM.CITY,MM.PO_BOX_NO,MM.TELEPHONE_NO,MM.MOBILE_NO,MM.FAX_NO,MM.PRI_CONTACT_NAME, "
					+ "MM.PRI_CONTACT_NO,MM.AGEN_OR_BILLER,MM.AREA,MM.POSTALCODE,MM.LRNUMBER,MM.COUNTRY,MM.MERCHANT_ADMIN,MM.STATUS,MM.MAKER_ID,"
					+ "to_char(MM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),MM.AUTHID,to_char(MM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
					+ "from MERCHANT_MASTER MM where MM.MERCHANT_ID=?";

			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1, merchantid);

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
					+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=(select trim(MERCHANT_ADMIN) from merchant_master where MERCHANT_ID=?)";
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1, merchantid);

			merchantPrmRS = merchantprmPstmt.executeQuery();
			if (merchantPrmRS.next()) {
				responseJSON.put("userName", merchantPrmRS.getString(1));
				responseJSON.put("userStatus", merchantPrmRS.getString(2));
				responseJSON.put("emailId", merchantPrmRS.getString(3));
				responseJSON.put("employeeNo", merchantPrmRS.getString(4));
			}

			merchantprmPstmt.close();
			merchantPrmRS.close();

			String merchantBankAcctQry = "SELECT distinct ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH,TRANSFER_CODE "
					+ "from BANK_ACCT_MASTER where MERCHANT_ID=?";

			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, merchantid);

			merchantPrmRS = merchantprmPstmt.executeQuery();
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;

			while (merchantPrmRS.next()) {
				eachrow = merchantPrmRS.getString(1) + ","
						+ merchantPrmRS.getString(2) + ","
						+ merchantPrmRS.getString(3) + ","
						+ merchantPrmRS.getString(4) + ","
						+ merchantPrmRS.getString(5);

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

			merchantBankAcctQry = "SELECT distinct BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MPESA_TILL_NO "
					+ "from AGENT_INFORMATION where MERCHANT_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, merchantid);

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

			merchantBankAcctQry = "SELECT distinct DOC_ID,DOC_DESC,GRACE_PERIOD,MANDATORY "
					+ "from DOCUMENT_DETAILS where MERCHANT_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, merchantid);

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

			// Below Are Fetching For Store Details
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
			merchantprmPstmt.setString(1, merchantid);
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

			logger.debug("Response JSON [" + responseJSON + "]");
			merchantDataMap.put("user_rights", responseJSON);
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

	public ResponseDTO getUserActiveDeactiveDetails(RequestDTO requestDTO) {

		logger.debug("Inside UserAuthRecordData.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		String name[] = null;
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			/*
			 * merchantQry =
			 * "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY')," +
			 * "user_level,(select distinct cbl.OFFICE_NAME from BRANCH_MASTER cbl where cbl.office_code=location),"
			 * +
			 * "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,(select login_user_id from user_login_credentials_temp where ref_no =? ),decode(USER_STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized',USER_STATUS) from user_information_temp "
			 * + "where ref_no=?";
			 */
			merchantQry = "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD/MM/YYYY'),"
					+ " user_level,location,email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,"
					+ " decode(USER_STATUS,'A','Active','L','De-Active','F','Active','MU','De-Active',USER_STATUS) ,COMMON_ID,MERCHANT_ID,STORE_ID"
					+ " from user_information where    common_id =(select common_id from USER_INFORMATION_TEMP where  REF_NUM =?) ";

			merchantPstmt = connection.prepareStatement(merchantQry);
			String Commonid = "";
			merchantPstmt.setString(1, ref_no);
			// merchantPstmt.setString(2, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				name = merchantRS.getString(1).split("\\ ");
				// resultJson.put("CV0001", "BANK0001");
				resultJson.put("CV0003", name[0]);
				resultJson.put("CV0004", name[1]);
				resultJson.put("CV0002", merchantRS.getString(2));
				resultJson.put("CV0011", merchantRS.getString(3));
				resultJson.put("CV0009", merchantRS.getString(4));
				resultJson.put("CV0010", merchantRS.getString(5));
				resultJson.put("CV0012", merchantRS.getString(6));
				resultJson.put("CV0005", merchantRS.getString(7));
				resultJson.put("CV0006", merchantRS.getString(8));
				resultJson.put("CV0007", merchantRS.getString(9));
				resultJson.put("CV0008", merchantRS.getString(10));
				resultJson.put("CV0013", merchantRS.getString(11));
				resultJson.put("CV0014", merchantRS.getString(12));
				resultJson.put("merchantId", merchantRS.getString(13));
				resultJson.put("storeId", merchantRS.getString(14));
				// resultJson.put("USERID", merchantRS.getString(12));
				Commonid = merchantRS.getString(12);

			}
			logger.debug("CommonID>>>>>:" + Commonid);

			merchantPstmt.close();
			merchantRS.close();
			// resultJson.clear();
			merchantQry = "select LOGIN_USER_ID  from USER_LOGIN_CREDENTIALS where COMMON_ID=? ";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1, Commonid);
			merchantRS = merchantPstmt.executeQuery();
			if (merchantRS.next()) {
				resultJson.put("USERID", merchantRS.getString(1));

			}

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
			name = null;
		}

		return responseDTO;
	}

	public ResponseDTO TerminalMigrationDetails(RequestDTO requestDTO) {

		logger.debug("Inside Terminal Migration Confirm.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resonseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			/*
			 * merchantQry =
			 * "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY')," +
			 * "user_level,(select distinct cbl.OFFICE_NAME from BRANCH_MASTER cbl where cbl.office_code=location),"
			 * +
			 * "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,(select login_user_id from user_login_credentials_temp where ref_no =? ),decode(USER_STATUS,'A','Active','L','De-Active','F','InActive','N','Not Authorized',USER_STATUS) from user_information_temp "
			 * + "where ref_no=?";
			 */
			merchantQry = "SELECT OLD_MERCHANTID,OLD_STOREID,TERMINALID,NEW_MERCHANTID,NEW_STOREID FROM TERMINAL_MIGRATION_TEMP WHERE REF_NUM=? ";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			// merchantPstmt.setString(2, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("merchantID", merchantRS.getString(1));
				resultJson.put("storeID", merchantRS.getString(2));
				resultJson.put("terminalID", merchantRS.getString(3));
				resultJson.put("updatemerchantID", merchantRS.getString(4));
				resultJson.put("updatestoreID", merchantRS.getString(5));
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO MerchantModifyDetails(RequestDTO requestDTO) {

		PreparedStatement merchantprmPstmt = null;
		ResultSet merchantPrmRS = null;
		Connection connection = null;

		logger.debug("Inside GetMerchantViewDetails... ");
		HashMap<String, Object> merchantDataMap = null;

		JSONArray jsonArray = null;
		JSONObject json = null;
		String merchantPrmQry = "Select MM.MERCHANT_ID,MM.MERCHANT_NAME,(select office_name from BRANCH_MASTER where office_code=MM.LOCATION), "
				+ "MM.KRA_PIN,(select CATE_DESCRIPTION from CATEGORY_MASTER where cate_code=MM.MERCHANT_TYPE),"
				+ " MM.MANAGER_NAME,MM.EMAIL,MM.ADDRESS,MM.CITY,MM.PO_BOX_NO,MM.TELEPHONE_NO,MM.MOBILE_NO,MM.FAX_NO,MM.PRI_CONTACT_NAME, "
				+ "MM.PRI_CONTACT_NO,MM.AGEN_OR_BILLER,MM.AREA,MM.POSTALCODE,MM.LRNUMBER,MM.COUNTRY,MM.MERCHANT_ADMIN,MM.STATUS,MM.MAKER_ID,"
				+ "to_char(MM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),MM.AUTHID,to_char(MM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
				+ "from MERCHANT_MASTER_TEMP MM where ref_num=?";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			merchantDataMap = new HashMap<String, Object>();
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));

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
				responseJSON.put(CevaCommonConstants.CITY,merchantPrmRS.getString(9));
				responseJSON.put(CevaCommonConstants.POBOXNUMBER,merchantPrmRS.getString(10));
				responseJSON.put(CevaCommonConstants.TELEPHONE1, telephone1);
				responseJSON.put(CevaCommonConstants.TELEPHONE2, telephone2);
				responseJSON.put(CevaCommonConstants.MOBILE_NUMBER,merchantPrmRS.getString(12));
				responseJSON.put(CevaCommonConstants.FAX_NUMBER,merchantPrmRS.getString(13));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,merchantPrmRS.getString(14));
				responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,merchantPrmRS.getString(15));
				responseJSON.put(CevaCommonConstants.MEMBER_TYPE,merchantPrmRS.getString(16));
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
					+ "where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=(select trim(MERCHANT_ADMIN) from merchant_master_temp where ref_num=?)";
			merchantprmPstmt = connection.prepareStatement(merchantPrmQry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));

			merchantPrmRS = merchantprmPstmt.executeQuery();
			if (merchantPrmRS.next()) {
				responseJSON.put("userName", merchantPrmRS.getString(1));
				responseJSON.put("userStatus", merchantPrmRS.getString(2));
				responseJSON.put("emailId", merchantPrmRS.getString(3));
				responseJSON.put("employeeNo", merchantPrmRS.getString(4));
			}

			merchantprmPstmt.close();
			merchantPrmRS.close();

			String merchantidqry = "select MERCHANT_ID  from merchant_master_temp where REF_NUM=?";
			merchantprmPstmt = connection.prepareStatement(merchantidqry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));
			merchantPrmRS = merchantprmPstmt.executeQuery();

			String MerchantID = "";
			if (merchantPrmRS.next()) {
				MerchantID = merchantPrmRS.getString(1);
			}
			logger.debug("MerchantID::::" + MerchantID);
			merchantprmPstmt.close();
			merchantPrmRS.close();

			/*
			 * String merchantBankAcctQry =
			 * "SELECT distinct ACCT_NO,ACCT_DESC,BANK_NAME,BANK_BRANCH,TRANSFER_CODE "
			 * + "from BANK_ACCT_MASTER where MERCHANT_ID=?";
			 */
			String merchantBankAcctQry = "SELECT distinct BAM.ACCT_NO,BAM.ACCT_DESC,nvl(BANK_NAME, ' '),CASE WHEN BAM.BANK_BRANCH IS NOT NULL THEN nvl((SELECT IBM.OFFICE_CODE||'-'||IBM.OFFICE_NAME FROM BRANCH_MASTER IBM WHERE IBM.OFFICE_CODE=BAM.BANK_BRANCH),' ')ELSE nvl(BAM.BANK_NAME,' ') END,TRANSFER_CODE FROM  BANK_ACCT_MASTER BAM WHERE BAM.MERCHANT_ID=? ";

			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, MerchantID);

			merchantPrmRS = merchantprmPstmt.executeQuery();
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;

			while (merchantPrmRS.next()) {
				eachrow = merchantPrmRS.getString(1) + ","
						+ merchantPrmRS.getString(2) + ","
						+ merchantPrmRS.getString(3) + ","
						+ merchantPrmRS.getString(4) + ","
						+ merchantPrmRS.getString(5);

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

			merchantBankAcctQry = "SELECT distinct BANK_AGENT_NO,MPESA_AGENT_NO,AIRTEL_AGENT_NO,ORANGE_AGENT_NO,MPESA_TILL_NO "
					+ "from AGENT_INFORMATION where MERCHANT_ID=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, MerchantID);

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

			merchantBankAcctQry = "SELECT distinct DOC_ID,DOC_DESC,GRACE_PERIOD,MANDATORY "
					+ "from DOCUMENT_DETAILS where ref_num=?";
			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));

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

			// Below Are Fetching For Store Details
			merchantprmPstmt.close();
			merchantPrmRS.close();

			jsonArray = new JSONArray();
			json = new JSONObject();

			merchantBankAcctQry = "Select SM.STORE_ID,SM.STORE_NAME,"
					+ "Decode(SM.STATUS,'A','Active','B','Inactive','N','Un-Authorize'),"
					+ "SM.MAKER_ID,to_char(SM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),"
					+ "SM.AUTHID,to_char(SM.AUTHDTTM ,'DD-MM-YYYY HH24:MI:SS')"
					+ " from MERCHANT_MASTER_TEMP MM,STORE_MASTER_TEMP SM "
					+ "where SM.MERCHANT_ID=MM.MERCHANT_ID and MM.REF_NUM=?  order by SM.STORE_ID";

			merchantprmPstmt = connection.prepareStatement(merchantBankAcctQry);
			merchantprmPstmt.setString(1, requestJSON.getString("ref_no"));
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

			logger.debug("Response JSON [" + responseJSON + "]");
			merchantDataMap.put("user_rights", responseJSON);
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

	public ResponseDTO getNextServiceCode(RequestDTO requestDTO) {

		logger.debug("Inside GetNextServiceCode... ");
		HashMap<String, Object> serviceDataMap = null;

		JSONObject resultJson = null;

		JSONArray serviceJSONArray = null;
	 
		Connection connection = null;

		PreparedStatement serviceIdPstmt = null;
		PreparedStatement servicePstmt = null;
		PreparedStatement settlementPstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;
		ResultSet settlementRS = null;

		String serviceIdQry = "";
		String ref_no = "";
		JSONObject json = null;
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			serviceJSONArray = new JSONArray();
		 
			resultJson = new JSONObject();

			serviceDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();
			ref_no = requestJSON.getString("ref_no");
			logger.debug("ref_no:::::>>>>" + ref_no);
			serviceIdQry = "select distinct SERVICE_CODE,SERVICE_NAME from SERVICE_MASTER_TEMP where REF_NUM=? and STATUS='R' ";

			serviceIdPstmt = connection.prepareStatement(serviceIdQry);
			serviceIdPstmt.setString(1, ref_no);
			serviceIdRS = serviceIdPstmt.executeQuery();
			String serviceId = "";
			String ServiceName = "";
			if (serviceIdRS.next()) {
				// String serviceID = serviceIdRS.getString(1);
				serviceId = serviceIdRS.getString(1);
				ServiceName = serviceIdRS.getString(2);
				logger.debug("ServiceID [" + serviceId + "]");
				logger.debug("ServiceID [" + ServiceName + "]");
			}
			resultJson.put("serviceId", serviceId);
			resultJson.put("ServiceName", ServiceName);
			resultJson.put("REF_NO", ref_no);

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

			serviceDataMap.put("user_rights", resultJson);

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

	public ResponseDTO subServiceAuthRejectRecordData(RequestDTO requestDTO) {

		logger.debug("Inside SubServiceCreateScreenDetails... ");
		HashMap<String, Object> serviceMap = null;
		JSONArray subServiceJSONArray = null;

		String serviceName = null;
		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		String ref_no = "";
		String SubserviceName = "";
		Connection connection = null;
		String Servicecode = "";
		String SubserviceCode = "";
		JSONObject resultJson = null;

		String storeQry = "SELECT SERVICE_CODE,SERVICE_NAME,SUB_SERVICE_CODE,SUB_SERVICE_NAME  FROM SERVICE_MASTER_TEMP WHERE REF_NUM=? AND AUTH_STATUS='AR'";

		try {
			resultJson = new JSONObject();
			serviceMap = new HashMap<String, Object>();
			subServiceJSONArray = new JSONArray();
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			ref_no = requestJSON.getString("ref_no");
			logger.debug("ref_no:::::>>>>" + ref_no);

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1, ref_no);

			storeRS = storePstmt.executeQuery();
			if (storeRS.next()) {
				Servicecode = storeRS.getString(1);
				serviceName = storeRS.getString(2);
				SubserviceCode = storeRS.getString(3);
				SubserviceName = storeRS.getString(4);
			}
			logger.debug("Servicecode:" + Servicecode + "SubserviceCode:"
					+ SubserviceCode);
			resultJson.put("Servicecode", Servicecode);
			resultJson.put("serviceName", serviceName);
			resultJson.put("SubserviceCode", SubserviceCode);
			resultJson.put("SubserviceName", SubserviceName);
			resultJson.put("ref_no", ref_no);
			storePstmt.close();
			storeRS.close();

			String subServiceQry = "";
			if ("HUDUMA".equals(serviceName)) {
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

			resultJson.put(CevaCommonConstants.SUBSERVICE_LIST,
					subServiceJSONArray);

			serviceMap.put("user_rights", resultJson);

			logger.debug("Service Map [" + serviceMap + "]");
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

	public ResponseDTO FeecodeRejectRecordModify(RequestDTO requestDTO) {

		logger.debug("Inside Reject Record FeeDetails.. ");
		HashMap<String, Object> serviceMap = null;

		PreparedStatement storePstmt = null;
		PreparedStatement storePstmt1 = null;
		PreparedStatement storePstmt2 = null;
		ResultSet storeRS = null;
		ResultSet storeRS1 = null;
		ResultSet storeRS2 = null;
		Connection connection = null;
		String slabDet = "";
		String ref_no = "";
		String feename = "";
		String storeQry = "Select (select BANK_CODE||'-'||BANK_NAME from bank_master where BANK_CODE=SM.SERVICE_NAME and rownum<2),"
				+ "SM.SUB_SERVICE_NAME,FM.FEE_CODE,FM.SERVICE_CODE,FM.SUB_SERVICE_CODE,NVL(FM.FLAT_PERCENT,' '),FM.FEE_STRING,"
				+ "FM.SLABDETAILS,FM.ACQDETAILS,FM.MAKER_ID,to_char(FM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),FM.ONUSOFUSFLAG "
				+ "from FEE_MASTER_TEMP FM,SERVICE_MASTER SM where SM.SERVICE_CODE=FM.SERVICE_CODE and "
				+ "SM.SUB_SERVICE_CODE=FM.SUB_SERVICE_CODE and  FM.REF_NUM=?";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			ref_no = requestJSON.getString("ref_no");
			logger.debug("ref_no:::::>>>>" + ref_no);

			connection = DBConnector.getConnection();
			serviceMap = new HashMap<String, Object>();
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1, ref_no);
			storeRS = storePstmt.executeQuery();
			int i = 0;

			while (storeRS.next()) {
				responseJSON.put(CevaCommonConstants.SERVICE_NAME,storeRS.getString(1));
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
			// responseJSON.put("feename", feename);

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
			storeQry2 = null;
			storePstmt2.close();
			storeRS2.close();
			json1.clear();
			storeQry2 = "select DISTINCT FEE_NAME  from fee_master_temp where REF_NUM=?";
			storePstmt2 = connection.prepareStatement(storeQry2);
			storePstmt2.setString(1, ref_no);
			storeRS2 = storePstmt2.executeQuery();

			while (storeRS2.next()) {
				/*
				 * json2.put(storeRS2.getString(1),
				 * storeRS2.getString(1)+"-"+storeRS2.getString(2));
				 */
				feename = storeRS2.getString(1);

			}
			responseJSON.put("feename", feename);
			responseJSON.put("merchantdata", agentAccount);
			responseJSON.put("SLAB", slabDet);
			
			serviceMap.put("user_rights", responseJSON);

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
				if (storePstmt2 != null)
					storePstmt2.close();
				if (storeRS != null)
					storeRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO billerTypeAuth(RequestDTO requestDTO) {

		logger.debug("Inside Biller Type Auth . ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			merchantQry = "select (select SI.INSTITUTION_NAME from SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = MABTT.INSTITUTE_ID)"
					+ " ,(select MN.OPERATORNAME from MN_OPERATORS MN where MN.OPERATORID = MABTT.OPERATOR_ID ) ,"
					+ "MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,MABTT.HAS_FIXED_AMOUNT ,"
					+ "MABTT.FIXED_AMOUNT ,"
					+ "(select SYSTEM_MODE_DESCRIPTION from MPESA_ACCTMGT_SYSTEM_MODES where SYSTEM_MODE_PK=MABTT.SYSTEM_MODE_FK) ,"
					+ "MABTT.BFUB_CREDIT_ACCT ,MABTT.BFUB_DEBIT_ACCT ,"
					+ "to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),MABTT.CREATED_BY,MABTT.REMARKS,to_char(MABTT.BILLER_ID_LENGTH) "
					+ "from MPESA_ACCTMGT_BILLER_TYPE_TEMP MABTT where  MABTT.REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				resultJson.put("institute", merchantRS.getString(1));
				resultJson.put("operator", merchantRS.getString(2));
				resultJson.put("billerType", merchantRS.getString(3));
				resultJson.put("billerTypeDescription", merchantRS.getString(4));
				resultJson.put("regex", merchantRS.getString(5));
				resultJson.put("fixedamountcheckval", merchantRS.getString(6));
				resultJson.put("amount", merchantRS.getString(7));
				resultJson.put("systemmodes", merchantRS.getString(8));
				resultJson.put("bfubCreditAccount", merchantRS.getString(9));
				resultJson.put("bfubDebitAccount", merchantRS.getString(10));
				resultJson.put("makerId", merchantRS.getString(12));
				resultJson.put("makerDttm", merchantRS.getString(11));
				resultJson.put("remark", merchantRS.getString(13));
				resultJson.put("billerIdLen", merchantRS.getString(14)); 
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantQry = "select BILLER_ID,DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT from MPESA_ACCTMGT_BILLER_ID_TEMP "
					+ "where REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();
			String eachrow = "";
			String billerData = "";
			int i = 0;
			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}

			resultJson.put("multiData", billerData);

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller Type Auth [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in Biller Type Auth  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}
	  /*   *********     */
	
	
	public ResponseDTO billerTypeActiveDeactiveAuth(RequestDTO requestDTO) {

		logger.debug("Inside Biller Type Auth . ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			
		   merchantQry ="select  MABTT.INSTITUTE_ID , MABTT.OPERATOR_ID  , MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,"
						+"MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,  MABTT.SYSTEM_MODE_FK  , MABTT.BFUB_CREDIT_ACCT ,"
						+"MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
						+"MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
						+"decode(MABTT.STATUS,'A','Active','B','De-Active'),MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID from MPESA_ACCTMGT_BILLER_TYPE_TEMP MABTT where  MABTT.REF_NUM=?";  

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				
				resultJson.put("institute", merchantRS.getString(1));
				resultJson.put("operator", merchantRS.getString(2));
				resultJson.put("billerType", merchantRS.getString(3));
				resultJson.put("billerTypeDescription", merchantRS.getString(4));
				resultJson.put("regex", merchantRS.getString(5));
				resultJson.put("fixedamountcheckval", merchantRS.getString(6));
				resultJson.put("amount", merchantRS.getString(7));
				resultJson.put("systemmodes", merchantRS.getString(8));
				resultJson.put("bfubCreditAccount", merchantRS.getString(9));
				resultJson.put("bfubDebitAccount", merchantRS.getString(10));
				resultJson.put("makerId", merchantRS.getString(12));
				resultJson.put("makerDttm", merchantRS.getString(11));
				resultJson.put("remark", merchantRS.getString(13));
				resultJson.put("billerIdLen", merchantRS.getString(14)); 
				resultJson.put("billlerTypeStatus", merchantRS.getString(16)); 
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller Type Auth [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in Biller Type Auth  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	/*    ******     */
	 
	
	public ResponseDTO billerIDActiveDeactiveAuth(RequestDTO requestDTO) {
		
		
		logger.debug("Inside Biller Id Active Deactive Auth . ");

		HashMap<String, Object> merchantMap = null;
	 
		
		String merchantQry = "";
		JSONObject resultJson = null;
	 
		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");
			
			merchantQry = "select  BILLER_ID,BILLER_TYPE_ID  from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				
				resultJson.put("billerId",merchantRS.getString(1));
				resultJson.put("billerIdType",merchantRS.getString(2));
				
				/*resultJson.put("billerIdDescription",merchantRS.getString(3));
				resultJson.put("bfubCreditAccount",merchantRS.getString(4));
				resultJson.put("bfubDebitAccount",merchantRS.getString(5));*/
				
			}
				
			merchantPstmt.close();
			merchantRS.close();

//	        merchantQry = " select DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT,Decode(STATUS,'A','Active','B','Inactive')  from MPESA_ACCTMGT_BILLER_ID_TEMP where BILLER_TYPE_ID=? and BILLER_ID=?";
	        merchantQry = " select DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT,Decode(STATUS,'A','Active','B','Inactive')  from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1,ref_no); 
			//merchantPstmt.setString(2, resultJson.getString("billerId"));
			merchantRS = merchantPstmt.executeQuery();
		 
			if(merchantRS.next()) {
				
				resultJson.put("billerIdDescription",merchantRS.getString(1));
				resultJson.put("bfubCreditAccount",merchantRS.getString(2));
				resultJson.put("bfubDebitAccount",merchantRS.getString(3));
				resultJson.put("status",merchantRS.getString(4)); 
				
				 
			}
			
			merchantPstmt.close();
			merchantRS.close();
			
			 
			
			merchantQry = "select  BILLER_TYPE_NAME  from  MPESA_ACCTMGT_BILLER_TYPE where ID=?";
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1, resultJson.getString("billerIdType")); 
			merchantRS = merchantPstmt.executeQuery();
		 
			if(merchantRS.next()) {
				
				resultJson.put("billerIdName",merchantRS.getString(1));
				
				logger.debug("3333"+resultJson);
				 
			}
			
			logger.debug("First String"+merchantRS.getString(1));
			
			merchantMap.put("user_rights", resultJson);
			
			merchantPstmt.close();
			merchantRS.close();

			 
			
			logger.debug("MerchantMap [" + merchantMap + "]");
			
			responseDTO.setData(merchantMap);
			
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} catch (Exception e) {

			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeConnection(connection);
			merchantQry = null;
		}
		return responseDTO;
	}
	
	/*public ResponseDTO billerIDActiveDeactiveAuth(RequestDTO requestDTO) {
		
		
		logger.debug("Inside Biller Id Active Deactive Auth . ");

		HashMap<String, Object> merchantMap = null;
	 
		
		String merchantQry = "";
		JSONObject resultJson = null;
	 
		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");
			
		   merchantQry ="select  MABTT.INSTITUTE_ID , MABTT.OPERATOR_ID  , MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,"
						+"MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,  MABTT.SYSTEM_MODE_FK  , MABTT.BFUB_CREDIT_ACCT ,"
						+"MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
						+"MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
						+"decode(MABTT.STATUS,'A','Active','B','De-Active'),MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID from MPESA_ACCTMGT_BILLER_TYPE_TEMP MABTT where  MABTT.REF_NUM=?";  

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				
				resultJson.put("institute", merchantRS.getString(1));
				resultJson.put("operator", merchantRS.getString(2));
				resultJson.put("billerType", merchantRS.getString(3));
				resultJson
						.put("billerTypeDescription", merchantRS.getString(4));
				resultJson.put("regex", merchantRS.getString(5));
				resultJson.put("fixedamountcheckval", merchantRS.getString(6));
				resultJson.put("amount", merchantRS.getString(7));
				resultJson.put("systemmodes", merchantRS.getString(8));
				resultJson.put("bfubCreditAccount", merchantRS.getString(9));
				resultJson.put("bfubDebitAccount", merchantRS.getString(10));
				resultJson.put("makerId", merchantRS.getString(12));
				resultJson.put("makerDttm", merchantRS.getString(11));
				resultJson.put("remark", merchantRS.getString(13));
				resultJson.put("billerIdLen", merchantRS.getString(14)); 
				resultJson.put("billlerTypeStatus", merchantRS.getString(16)); 
				
			}

			merchantPstmt.close();
			merchantRS.close();
			
			String storeQry = "Select  BILLER_ID,DESCRIPTION,nvl(BFUB_CREDIT_ACCT,' '),nvl(BFUB_DEBIT_ACCT,' '),"
					+ "decode(STATUS,'A','Active','B','De-Active')"
					+ "from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";

			merchantPstmt = connection.prepareStatement(storeQry);
			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();
		 
			
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;

			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4) + ","
						+ merchantRS.getString(5);

				if (i == 0) {
					bankAcctData += eachrow;
				} else {
					bankAcctData += "#" + eachrow;
				}
				i++;
			}

			resultJson.put("BillerIdData", bankAcctData);
			
			
			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller ID Auth [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in Biller ID Auth  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}
		return responseDTO;
	}*/
	 
	public ResponseDTO billerTypeStatusAuth(RequestDTO requestDTO) {

		logger.debug("Inside Biller Type Auth . ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");

			
		   merchantQry ="select  MABTT.INSTITUTE_ID , MABTT.OPERATOR_ID  , MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,"
						+"MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,  MABTT.SYSTEM_MODE_FK  , MABTT.BFUB_CREDIT_ACCT ,"
						+"MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
						+"MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
						+"decode(MABTT.STATUS,'A','Active','B','De-Active'),MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID from MPESA_ACCTMGT_BILLER_TYPE_TEMP MABTT where  MABTT.REF_NUM=?";  

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				
				resultJson.put("institute", merchantRS.getString(1));
				resultJson.put("operator", merchantRS.getString(2));
				resultJson.put("billerType", merchantRS.getString(3));
				resultJson
						.put("billerTypeDescription", merchantRS.getString(4));
				resultJson.put("regex", merchantRS.getString(5));
				resultJson.put("fixedamountcheckval", merchantRS.getString(6));
				resultJson.put("amount", merchantRS.getString(7));
				resultJson.put("systemmodes", merchantRS.getString(8));
				resultJson.put("bfubCreditAccount", merchantRS.getString(9));
				resultJson.put("bfubDebitAccount", merchantRS.getString(10));
				resultJson.put("makerId", merchantRS.getString(12));
				resultJson.put("makerDttm", merchantRS.getString(11));
				resultJson.put("remark", merchantRS.getString(13));
				resultJson.put("billerIdLen", merchantRS.getString(14)); 
				resultJson.put("billlerTypeStatus", merchantRS.getString(16)); 
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller Type Auth [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in Biller Type Auth  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	
	
/*public ResponseDTO billerIDStatusAuth(RequestDTO requestDTO) {
		
		
		logger.debug("Inside Biller Id  Status Auth . ");

		HashMap<String, Object> merchantMap = null;
		 
		
		String merchantQry = "";
		JSONObject resultJson = null;
		 
		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String ref_no = "";

		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			ref_no = requestJSON.getString("ref_no");
			
		   merchantQry ="select  MABTT.INSTITUTE_ID , MABTT.OPERATOR_ID  , MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,"
						+"MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,  MABTT.SYSTEM_MODE_FK  , MABTT.BFUB_CREDIT_ACCT ,"
						+"MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
						+"MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
						+"decode(MABTT.STATUS,'A','Active','B','De-Active'),MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID from MPESA_ACCTMGT_BILLER_TYPE_TEMP MABTT where  MABTT.REF_NUM=?";  

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				
				resultJson.put("institute", merchantRS.getString(1));
				resultJson.put("operator", merchantRS.getString(2));
				resultJson.put("billerType", merchantRS.getString(3));
				resultJson.put("billerTypeDescription", merchantRS.getString(4));
				resultJson.put("regex", merchantRS.getString(5));
				resultJson.put("fixedamountcheckval", merchantRS.getString(6));
				resultJson.put("amount", merchantRS.getString(7));
				resultJson.put("systemmodes", merchantRS.getString(8));
				resultJson.put("bfubCreditAccount", merchantRS.getString(9));
				resultJson.put("bfubDebitAccount", merchantRS.getString(10));
				resultJson.put("makerId", merchantRS.getString(12));
				resultJson.put("makerDttm", merchantRS.getString(11));
				resultJson.put("remark", merchantRS.getString(13));
				resultJson.put("billerIdLen", merchantRS.getString(14)); 
				resultJson.put("billlerTypeStatus", merchantRS.getString(16)); 
				
			}
			merchantPstmt.close();
			merchantRS.close();
			
			String storeQry = "Select  BILLER_ID,DESCRIPTION,nvl(BFUB_CREDIT_ACCT,' '),nvl(BFUB_DEBIT_ACCT,' '),"
					+ "decode(STATUS,'A','Active','B','De-Active')"
					+ "from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";

			merchantPstmt = connection.prepareStatement(storeQry);
			merchantPstmt.setString(1, ref_no);
			merchantRS = merchantPstmt.executeQuery();
		 
			 
			String bankAcctData = "";
			String eachrow = "";
			int i = 0;

			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","
						+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","
						+ merchantRS.getString(4) + ","
						+ merchantRS.getString(5);

				if (i == 0) {
					bankAcctData += eachrow;
				} else {
					bankAcctData += "#" + eachrow;
				}
				i++;
			}

			resultJson.put("BillerIdData", bankAcctData);
			
			
			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller ID Auth [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in Biller ID Auth  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
		}
		return responseDTO;
	}*/

	
public ResponseDTO billerIDStatusAuth(RequestDTO requestDTO) {
	
	
	logger.debug("Inside Biller Id  Status Auth . ");

	HashMap<String, Object> merchantMap = null;
	String merchantQry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement merchantPstmt = null;
	ResultSet merchantRS = null;
	String ref_no = "";
 
	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

		ref_no = requestJSON.getString("ref_no");
		
		merchantQry = "select  BILLER_ID,BILLER_TYPE_ID  from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";

		merchantPstmt = connection.prepareStatement(merchantQry);

		merchantPstmt.setString(1, ref_no);
		merchantRS = merchantPstmt.executeQuery();

		if (merchantRS.next()) {
			
			resultJson.put("billerId",merchantRS.getString(1));
			resultJson.put("billerIdType",merchantRS.getString(2));
			
			/*resultJson.put("billerIdDescription",merchantRS.getString(3));
			resultJson.put("bfubCreditAccount",merchantRS.getString(4));
			resultJson.put("bfubDebitAccount",merchantRS.getString(5));*/
			
		}
			
		merchantPstmt.close();
		merchantRS.close();

        merchantQry = " select DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT,Decode(STATUS,'A','Active','B','Inactive')  from MPESA_ACCTMGT_BILLER_ID where BILLER_TYPE_ID=? and BILLER_ID=?";
		
		merchantPstmt = connection.prepareStatement(merchantQry);
		
		merchantPstmt.setString(1, resultJson.getString("billerIdType")); 
		merchantPstmt.setString(2, resultJson.getString("billerId"));
		merchantRS = merchantPstmt.executeQuery();
	 
		if(merchantRS.next()) {
			
			resultJson.put("billerIdDescription",merchantRS.getString(1));
			resultJson.put("bfubCreditAccount",merchantRS.getString(2));
			resultJson.put("bfubDebitAccount",merchantRS.getString(3));
			resultJson.put("status",merchantRS.getString(4)); 
			
			 
		}
		
		merchantPstmt.close();
		merchantRS.close();
		
		 
		
		merchantQry = "select  BILLER_TYPE_NAME  from  MPESA_ACCTMGT_BILLER_TYPE where ID=?";
		
		merchantPstmt = connection.prepareStatement(merchantQry);
		
		merchantPstmt.setString(1, resultJson.getString("billerIdType")); 
		merchantRS = merchantPstmt.executeQuery();
	 
		if(merchantRS.next()) {
			
			resultJson.put("billerIdName",merchantRS.getString(1));
			
			logger.debug("3333"+resultJson);
			 
		}
		
		logger.debug("First String"+merchantRS.getString(1));
		
		merchantMap.put("user_rights", resultJson);
		
		merchantPstmt.close();
		merchantRS.close();

		 
		
		logger.debug("MerchantMap [" + merchantMap + "]");
		
		responseDTO.setData(merchantMap);
		
		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
}


public ResponseDTO bulkRegiAuth(RequestDTO requestDTO) {
	
	
	logger.debug("Inside Bulk Registartion Auth . ");

	HashMap<String, Object> merchantMap = null;
	String Qry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement fPstmt = null;
	ResultSet RS = null;
	String ref_no = "";
	
	
 
	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		ref_no = requestJSON.getString("ref_no");
		
		Qry = "select FILENAME from MOB_BULKUPLOAD where REF_NUM=?";

		fPstmt = connection.prepareStatement(Qry);

		fPstmt.setString(1, ref_no);
		RS = fPstmt.executeQuery();

		if (RS.next()) {
			
			resultJson.put("fname",RS.getString(1));
		}
    
		fPstmt.close();
		RS.close();
		
		merchantMap.put("user_rights", resultJson);
		
		logger.debug("MerchantMap in AuthFetchSubDAO in Bulk Registration  [" + merchantMap + "]");
		
		responseDTO.setData(merchantMap);
		
		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(RS);
		DBUtils.closePreparedStatement(fPstmt);
		DBUtils.closeConnection(connection);
		Qry = null;
	}
	return responseDTO;
}

public ResponseDTO superagentAuth(RequestDTO requestDTO) {

	logger.debug("Inside Create ProductAuth .... ");

	HashMap<String, Object> merchantMap = null;
	String merchantQry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement merchantPstmt = null;
	ResultSet merchantRS = null;
	String ref_no = "";

	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

		ref_no = requestJSON.getString("ref_no");

		merchantQry  = " select SAT.ACCOUNT_NUMBER,SAT.ACCOUNT_NAME,SAT.ACCOUNT_CURRENCY_CODE,SAT.BRANCH_CODE,SAT.EMAIL,SAT.MOBILE,'','','',"
				+ " Decode(SAT.STATUS,'A','Active','L','De-Active','B','Inactive','N','Not Authorized'),SAT.ORGANIGATION_NAME,SAT.ADDRESS_LINE_ONE,SAT.ADDRESS_LINE_TWO,(SELECT S.GOVT_NAME FROM LOCAL_GOVT_MASTER S WHERE S.GOV_CODE = SAT.LOCAL_GOVERNMENT),"
				+ " Decode(SAT.COUNTRY,'1','Nigeria'),(SELECT S.STATE_NAME FROM STATE_MASTER S WHERE S.STATE_CODE = SAT.STATE),SAT.DATE_OF_BIRTH,SAT.GENDER,SAT.ID_TYPE,SAT.ID_NUMBER,'',SAT.TELEPHONE_NUMBER,SAT.TELEPHONE_NUMBER,Decode(SAT.NATIONALITY,'1','Nigerian','2','Uganda','3','Kenya'),'','','',SAT.MAKER_ID,SAT.MAKER_DT,SAT.CITY, "
				+ " '' from ORG_MASTER_TEMP SAT where SAT.REF_NUM=? ";

		merchantPstmt = connection.prepareStatement(merchantQry);

		merchantPstmt.setString(1, ref_no);
		merchantRS = merchantPstmt.executeQuery();

		if (merchantRS.next()) {

			resultJson.put("ACCOUNTNUMBER", merchantRS.getString(1));
			resultJson.put("ACCOUNTNAME", merchantRS.getString(2));
			resultJson.put("ACCTCURRCODE", merchantRS.getString(3));
			resultJson.put("BRANCHCODE", merchantRS.getString(4));
			resultJson.put("EMAIL", merchantRS.getString(5));
			resultJson.put("MOBILE", merchantRS.getString(6));
			resultJson.put("SCHEMEDESC", merchantRS.getString(7));
			resultJson.put("SCHEMETYPE", merchantRS.getString(8));
			resultJson.put("SUBPRODUCTCODE", merchantRS.getString(9));
			resultJson.put("STATUS", merchantRS.getString(10));
			resultJson.put("MANAGERNAME", merchantRS.getString(11));
			resultJson.put("ADDRESSLINE1", merchantRS.getString(12));
			resultJson.put("ADDRESSLINE2", merchantRS.getString(13));
			resultJson.put("LOCALGOVERNMENT", merchantRS.getString(14));
			resultJson.put("COUNTRY", merchantRS.getString(15));
			resultJson.put("STATE", merchantRS.getString(16));
			resultJson.put("DOB", merchantRS.getString(17));
			resultJson.put("GENDER", merchantRS.getString(18));
			resultJson.put("ID_TYPE", merchantRS.getString(19));
			resultJson.put("ID_NUMBER", merchantRS.getString(20));
			resultJson.put("TELCO_TYPE", merchantRS.getString(21));
			resultJson.put("TELEPHONE_NUM1", merchantRS.getString(22));
			resultJson.put("TELEPHONE_NUM2", merchantRS.getString(23));
			resultJson.put("NATIONALITY", merchantRS.getString(24));
			resultJson.put("LATITUDE", merchantRS.getString(25));
			resultJson.put("LONGITUDE", merchantRS.getString(26));
			resultJson.put("AUTH_FLAG", merchantRS.getString(27));
			resultJson.put("makerId", merchantRS.getString(28));
			resultJson.put("makerDttm", merchantRS.getString(29));
			resultJson.put("CITY", merchantRS.getString(30));
			resultJson.put("CBNAGENTID", merchantRS.getString(31));

		}

		merchantPstmt.close();
		merchantRS.close();

		merchantMap.put("user_rights", resultJson);

		logger.debug("MerchantMap [" + merchantMap + "]");

		responseDTO.setData(merchantMap);

		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
}


public ResponseDTO newsuperagentAuth(RequestDTO requestDTO) {

	logger.debug("Inside Create ProductAuth .... ");

	HashMap<String, Object> merchantMap = null;
	String merchantQry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement merchantPstmt = null;
	ResultSet merchantRS = null;
	String ref_no = "";

	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

		ref_no = requestJSON.getString("ref_no");

		merchantQry = " select SAT.ACCOUNTNUMBER,SAT.ACCOUNTNAME,SAT.ACCTCURRCODE,SAT.BRANCHCODE,SAT.EMAIL,SAT.MOBILE,SAT.SCHEMEDESC,SAT.SCHEMETYPE,SAT.SUBPRODUCTCODE,"
					+ " Decode(SAT.STATUS,'A','Active','L','De-Active','B','Inactive','N','Not Authorized'),SAT.MANAGERNAME,SAT.ADDRESSLINE1,SAT.ADDRESSLINE2,(SELECT S.GOVT_NAME FROM LOCAL_GOVT_MASTER S WHERE S.GOV_CODE = SAT.LOCALGOVERNMENT),"
					+ " Decode(SAT.COUNTRY,'1','Nigeria'),(SELECT S.STATE_NAME FROM STATE_MASTER S WHERE S.STATE_CODE = SAT.STATE),SAT.DOB,SAT.GENDER,SAT.ID_TYPE,SAT.ID_NUMBER,SAT.TELCO_TYPE,SAT.TELEPHONE_NUM1,SAT.TELEPHONE_NUM2,Decode(SAT.NATIONALITY,'1','Nigerian','2','Uganda','3','Kenya'),SAT.LATITUDE,SAT.LONGITUDE,SAT.AUTH_FLAG,SAT.MAKER_ID,SAT.MAKER_DTTM,SAT.CITY, "
					+ " UBNAGENTID,PRODUCT,PRODUCT_DESC from SUPER_AGENT_TEMP SAT where SAT.REF_NUM=? ";

		merchantPstmt = connection.prepareStatement(merchantQry);

		merchantPstmt.setString(1, ref_no);
		merchantRS = merchantPstmt.executeQuery();

		if (merchantRS.next()) {

			resultJson.put("ACCOUNTNUMBER", merchantRS.getString(1));
			resultJson.put("ACCOUNTNAME", merchantRS.getString(2));
			resultJson.put("ACCTCURRCODE", merchantRS.getString(3));
			resultJson.put("BRANCHCODE", merchantRS.getString(4));
			resultJson.put("EMAIL", merchantRS.getString(5));
			resultJson.put("MOBILE", merchantRS.getString(6));
			resultJson.put("SCHEMEDESC", merchantRS.getString(7));
			resultJson.put("SCHEMETYPE", merchantRS.getString(8));
			resultJson.put("SUBPRODUCTCODE", merchantRS.getString(9));
			resultJson.put("STATUS", merchantRS.getString(10));
			resultJson.put("MANAGERNAME", merchantRS.getString(11));
			resultJson.put("ADDRESSLINE1", merchantRS.getString(12));
			resultJson.put("ADDRESSLINE2", merchantRS.getString(13));
			resultJson.put("LOCALGOVERNMENT", merchantRS.getString(14));
			resultJson.put("COUNTRY", merchantRS.getString(15));
			resultJson.put("STATE", merchantRS.getString(16));
			resultJson.put("DOB", merchantRS.getString(17));
			resultJson.put("GENDER", merchantRS.getString(18));
			resultJson.put("ID_TYPE", merchantRS.getString(19));
			resultJson.put("ID_NUMBER", merchantRS.getString(20));
			resultJson.put("TELCO_TYPE", merchantRS.getString(21));
			resultJson.put("TELEPHONE_NUM1", merchantRS.getString(22));
			resultJson.put("TELEPHONE_NUM2", merchantRS.getString(23));
			resultJson.put("NATIONALITY", merchantRS.getString(24));
			resultJson.put("LATITUDE", merchantRS.getString(25));
			resultJson.put("LONGITUDE", merchantRS.getString(26));
			resultJson.put("AUTH_FLAG", merchantRS.getString(27));
			resultJson.put("makerId", merchantRS.getString(28));
			resultJson.put("makerDttm", merchantRS.getString(29));
			resultJson.put("CITY", merchantRS.getString(30));
			resultJson.put("CBNAGENTID", merchantRS.getString(31));
			resultJson.put("PRODUCT", merchantRS.getString(32));
			resultJson.put("PRODUCT_DESC", merchantRS.getString(33));

		}

		merchantPstmt.close();
		merchantRS.close();

		merchantMap.put("user_rights", resultJson);

		logger.debug("MerchantMap [" + merchantMap + "]");

		responseDTO.setData(merchantMap);

		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
}

public ResponseDTO AccountRegAuthData(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.M_PRD_CODE,CM.M_PRD_DESC "
						+ "FROM MOB_CUSTOMER_MASTER_HIST CM,MOB_CONTACT_INFO_HIST CI,MOB_ACCT_DATA_HIST CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA_HIST mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}
			else{
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.M_PRD_CODE,CM.M_PRD_DESC "
						+ "FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI,MOB_ACCT_DATA_TEMP CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA_TEMP mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("product",detRS.getString(11));
			resultJson.put("prodesc",detRS.getString(12));
		}


		if (status.equalsIgnoreCase("D")){
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME,PRODUCT_TYPE,APP_TYPE from MOB_ACCT_DATA_HIST where REF_NUM=?";
		}
		else
		{
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME,PRODUCT_TYPE,APP_TYPE from MOB_ACCT_DATA_TEMP where REF_NUM=?";
		}
		
		
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(6) + ","
						+ rs.getString(5)+ ","
						+ rs.getString(7);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		
			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}


public ResponseDTO WalletAccountRegAuthData(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.M_PRD_CODE,CM.M_PRD_DESC "
						+ "FROM MOB_CUSTOMER_MASTER_HIST CM,MOB_CONTACT_INFO_HIST CI,WALLET_ACCT_DATA_HIST CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA_HIST mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}
			else{
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.M_PRD_CODE,CM.M_PRD_DESC "
						+ "FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI,WALLET_ACCT_DATA_TEMP CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA_TEMP mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("product",detRS.getString(11));
			resultJson.put("prodesc",detRS.getString(12));
		}


		if (status.equalsIgnoreCase("D")){
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,APP_TYPE,ALIAS_NAME from WALLET_ACCT_DATA_HIST where REF_NUM=?";
		}
		else
		{
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,APP_TYPE,ALIAS_NAME from WALLET_ACCT_DATA_TEMP where REF_NUM=?";
		}
		
		
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		
			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}

public ResponseDTO WalletAgentAccountRegAuthData(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CM.ACCOUNT_NO,CM.ACC_BRANCH,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "CM.SUPER_ADM,CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.W_PRD_CODE,W_PRD_DESC,DECODE(CM.STATUS,'B','Block','F','First Time','A','Active','Deactivation') "
						+ "FROM AGENT_CUSTOMER_MASTER_TEMP CM,AGENT_CONTACT_INFO_TEMP CI,WALLET_ACCT_DATA_TEMP CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA_TEMP mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}
			else{
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CM.ACCOUNT_NO,CM.ACC_BRANCH,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "CM.SUPER_ADM,CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.W_PRD_CODE,W_PRD_DESC,DECODE(CM.STATUS,'B','Block','F','First Time','A','Active','Deactivation') "
						+ "FROM AGENT_CUSTOMER_MASTER_TEMP CM,AGENT_CONTACT_INFO_TEMP CI,WALLET_ACCT_DATA_TEMP CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA_TEMP mt where mt.cust_id=cm.id) AND CM.REF_NUM=?";
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("product",detRS.getString(11));
			resultJson.put("prodesc",detRS.getString(12));
			resultJson.put("status",detRS.getString(13));
		}


		if (status.equalsIgnoreCase("D")){
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,APP_TYPE,ALIAS_NAME from WALLET_ACCT_DATA_HIST where REF_NUM=?";
		}
		else
		{
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,APP_TYPE,ALIAS_NAME from WALLET_ACCT_DATA_TEMP where REF_NUM=?";
		}
		
		
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		
			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}


public ResponseDTO POSRegAuth(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CM.ACCOUNT_NO,CM.ACC_BRANCH,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "CM.SUPER_ADM,CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.W_PRD_CODE,DECODE(CM.STATUS,'B','Block','F','First Time','A','Active','Deactivation') "
						+ "FROM AGENT_CUSTOMER_MASTER CM,AGENT_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CM.ID in  (select CUST_ID from TERMINAL_MASTER_TEMP mt where mt.REF_NUM=?)";
			}
			else{
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CM.ACCOUNT_NO,CM.ACC_BRANCH,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "CM.SUPER_ADM,CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.W_PRD_CODE,DECODE(CM.STATUS,'B','Block','F','First Time','A','Active','Deactivation') "
						+ "FROM AGENT_CUSTOMER_MASTER CM,AGENT_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CM.ID in  (select CUST_ID from TERMINAL_MASTER_TEMP mt where mt.REF_NUM=?)";
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("product",detRS.getString(11));
			resultJson.put("prodesc",detRS.getString(12));
		}


		if (status.equalsIgnoreCase("D")){
			detQry1 = "select MODEL_NO,TERMINAL_MAKE,SERIAL_NO,MAKER_ID,MAKER_DTTM from TERMINAL_MASTER_TEMP where REF_NUM=?";
		}
		else
		{
			detQry1 = "select MODEL_NO,TERMINAL_MAKE,SERIAL_NO,MAKER_ID,MAKER_DTTM from TERMINAL_MASTER_TEMP where REF_NUM=?";
		}
		
		
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		
			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}


public ResponseDTO TerminalDetailsAuth(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				detQry = "select terminal_id,MODEL_NO,TERMINAL_MAKE,SERIAL_NO,Decode(STATUS,'A','Active','D','Deactived','R','Retrival','P','Not yet Assign'),MAKER_ID,MAKER_DTTM from TERMINAL_MASTER_TEMP where REF_NUM=?";
			}
			else{
				detQry = "select terminal_id,MODEL_NO,TERMINAL_MAKE,SERIAL_NO,Decode(STATUS,'A','Active','D','Deactived','R','Retrival','P','Not yet Assign'),MAKER_ID,MAKER_DTTM from TERMINAL_MASTER_TEMP where REF_NUM=?";
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
			resultJson.put("terminal_id",detRS.getString(1));
		resultJson.put("MODEL_NO",detRS.getString(2));
			resultJson.put("TERMINAL_MAKE",detRS.getString(3));
			resultJson.put("SERIAL_NO",detRS.getString(4));
			resultJson.put("STATUS",detRS.getString(5));
			resultJson.put("MAKER_ID",detRS.getString(6));
			resultJson.put("MAKER_DTTM",detRS.getString(7));
		}


		
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}

public ResponseDTO StoreRegAuth(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;
	String status=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			status = requestJSON.getString("status");
			logger.debug("reference number"+ref_no);
			logger.debug("Status in Page"+status);
			
			if (status.equalsIgnoreCase("D")){
				/*detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CM.ACCOUNT_NO,CM.ACC_BRANCH,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'),"
						+ "CM.SUPER_ADM,CM.EMAILID,CM.REMARKS,CA.INSTITUTE,CM.W_PRD_CODE,DECODE(CM.STATUS,'B','Block','F','First Time','A','Active','Deactivation') "
						+ "FROM AGENT_CUSTOMER_MASTER CM,AGENT_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CM.ID in  (select CUST_ID from STORE_MASTER_TEMP mt where mt.REF_NUM=?)";*/
				
				detQry="select ACM.ACCOUNT_NO,ACM.FIRST_NAME||ACM.MIDDLE_NAME||ACM.LAST_NAME,MCI.MOBILE_NUMBER,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.CUSTOMER_CODE,WAD.ACCT_NO "
						+ "from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID in  (select CUST_ID from STORE_MASTER_TEMP mt where mt.REF_NUM=?)";
			}
			else{
				detQry="select ACM.ACCOUNT_NO,ACM.FIRST_NAME||ACM.MIDDLE_NAME||ACM.LAST_NAME,MCI.MOBILE_NUMBER,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.CUSTOMER_CODE,WAD.ACCT_NO "
						+ "from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID in  (select CUST_ID from STORE_MASTER_TEMP mt where mt.REF_NUM=?)";
		
			}

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
			
			resultJson.put("accountno",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));					
			resultJson.put("status",detRS.getString(4));
			resultJson.put("id",detRS.getString(5));
			resultJson.put("userid",detRS.getString(3));
			resultJson.put("walletaccountno",detRS.getString(6));		
			
	
		}
		
		
		detQry1 = "select STORE_NAME,STORE_ID,ADDRESS,COUNTRY,STATE,LOC_GOV,decode(STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),B_OWNER_NAME,EMAIL,TELEPHONE_NO,AREA,LONGITUDE,LATITUDE from STORE_MASTER_TEMP where REF_NUM=?";
		detPstmt1 = connection.prepareStatement(detQry1);
		detPstmt1.setString(1, ref_no);
		
		rs = detPstmt1.executeQuery();
		while (rs.next()) {
			resultJson.put("storename",rs.getString(1));
			resultJson.put("storeid",rs.getString(2));
			resultJson.put("address",rs.getString(3));
			resultJson.put("country",rs.getString(4));
			resultJson.put("state",rs.getString(5));
			resultJson.put("localGovernment",rs.getString(6));
			resultJson.put("sstatus",rs.getString(7));
			resultJson.put("boname",rs.getString(8));
			resultJson.put("email",rs.getString(9));
			resultJson.put("mobileno",(rs.getString(10)).substring(3));
			
			resultJson.put("area",rs.getString(11));
			resultJson.put("longitude",rs.getString(12));
			resultJson.put("latitude",rs.getString(13));
		}

		/*if (status.equalsIgnoreCase("D")){
			detQry1 = "select STORE_ID,STORE_NAME,MAKER_ID,MAKER_DTTM from STORE_MASTER_TEMP where REF_NUM=?";
		}
		else
		{
			detQry1 = "select STORE_ID,STORE_NAME,MAKER_ID,MAKER_DTTM from STORE_MASTER_TEMP where REF_NUM=?";
		}
		
		
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) ;

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		
			resultJson.put("multiData",billerData);*/
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}

public ResponseDTO AccountactdeactAuth(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String ref_no=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		ref_no = requestJSON.getString("ref_no");
		logger.debug("reference number"+ref_no);
		
		detQry = "SELECT (SELECT DISTINCT(CUSTOMER_CODE) FROM MOB_CUSTOMER_MASTER_TEMP WHERE ID=MA.CUST_ID) CUSTID,"
					+ "ACCT_NO,ALIAS_NAME,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ACCT_STATUS,"
					+ "DECODE(INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "DATE_CREATED,CREATED_BY,"
					+ "decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active')"
					+ " FROM MOB_ACCT_DATA_TEMP MA where REF_NUM=?";
		
		
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("status",detRS.getString(11));
		}
	
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
	}
	
	return responseDTO;
	
}



public ResponseDTO WalletAccountactdeactAuth(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String ref_no=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		ref_no = requestJSON.getString("ref_no");
		logger.debug("reference number"+ref_no);
		
		detQry = "SELECT (SELECT DISTINCT(CUSTOMER_CODE) FROM MOB_CUSTOMER_MASTER_TEMP WHERE ID=MA.CUST_ID) CUSTID,"
					+ "ACCT_NO,ALIAS_NAME,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ACCT_STATUS,"
					+ "'WALLET',"
					+ "DATE_CREATED,CREATED_BY,"
					+ "decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active')"
					+ " FROM WALLET_ACCT_DATA_TEMP MA where REF_NUM=?";
		
		
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("institute",detRS.getString(10));
			resultJson.put("status",detRS.getString(11));
		}
	
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
	}
	
	return responseDTO;
	
}

public ResponseDTO PinResetAuth(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	
	
	String ref_no=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		
		
		ref_no = requestJSON.getString("ref_no");
		logger.debug("reference number"+ref_no);
		
		detQry = " select MA.CUSTOMER_CODE,MA.FIRST_NAME,MA.DOCID,MT.MOBILE_NUMBER,"
				+ "to_char(MA.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),MA.CREATED_BY "
				+ "from MOB_CUSTOMER_MASTER_TEMP MA,MOB_CONTACT_INFO_TEMP MT where MA.ID=MT.CUST_ID and MA.REF_NUM=?";
		
		
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("mobileno",detRS.getString(4));
			resultJson.put("isocode",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
		}
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
	}
	
	return responseDTO;
	
}


public ResponseDTO ModCustDetails(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			logger.debug("reference number"+ref_no);
			
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI "
					+ "WHERE CM.REF_NUM=CI.REF_NUM AND CM.REF_NUM=?";
		

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			//resultJson.put("institute",detRS.getString(10));
		}


						
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_TEMP where REF_NUM=?";
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		

			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}

public ResponseDTO LimitCustDetails(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			logger.debug("reference number"+ref_no);
			
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC,MLT.CUST_PERDAY_LIMIT,MLT.CUST_CHANNEL_LIMIT FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI,MOB_CUSTOMER_LIMIT_TEMP MLT "
					+ "WHERE CM.REF_NUM=CI.REF_NUM AND MLT.REF_NUM=CI.REF_NUM AND CM.REF_NUM=?";
		

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			resultJson.put("pdaylimit",detRS.getString(12));
			resultJson.put("chlimit",detRS.getString(13));
			//resultJson.put("institute",detRS.getString(10));
		}


						
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_TEMP where REF_NUM=?";
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		

			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}


public ResponseDTO WalletModCustDetails(RequestDTO requestDTO) {

	logger.debug("Inside Account Details Fetching . ");

	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";


	String ref_no=null;

	try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();

		logger.debug("Request JSON [" + requestJSON + "]");

		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

	
			
			ref_no = requestJSON.getString("ref_no");
			logger.debug("reference number"+ref_no);
			
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.W_PRD_CODE,CM.W_PRD_DESC FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI "
					+ "WHERE CM.REF_NUM=CI.REF_NUM AND CM.REF_NUM=?";
		

		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		

		if (detRS.next()) {
		resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			//resultJson.put("institute",detRS.getString(10));
		}


						
			detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from WALLET_ACCT_DATA_TEMP where REF_NUM=?";
			detPstmt1 = connection.prepareStatement(detQry1);
			detPstmt1.setString(1, ref_no);
			
			rs = detPstmt1.executeQuery();

			int i = 0;
		
	
			
			while (rs.next()) {
				eachrow = rs.getString(1) + ","
						+ rs.getString(2) + ","
						+ rs.getString(3) + ","
						+ rs.getString(4) + ","
						+ rs.getString(5);

				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
		

			resultJson.put("multiData",billerData);
			detMap.put("user_rights", resultJson);
			
			
			responseDTO.setData(detMap);
	
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {

		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);

		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);

		detQry = null;
		eachrow = null;
		billerData = null;
	}

	return responseDTO;

}


public ResponseDTO deleteAccounts(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";
	
	
	String ref_no=null;
	String status=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		
		
		ref_no = requestJSON.getString("ref_no");
		status = requestJSON.getString("status");
		logger.debug("reference number"+ref_no+"  status in del"+status);
		
		if(status.equalsIgnoreCase("D"))
				{
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
				+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER_HIST CM,MOB_CONTACT_INFO_HIST CI "
				+ "WHERE CI.CUST_ID=CM.ID AND CM.ID=(select distinct(CUST_ID) from MOB_ACCT_DATA_HIST where REF_NUM=?)";
		}
		else{
		detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
				+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI "
				+ "WHERE CI.CUST_ID=CM.ID AND CM.ID=(select distinct(CUST_ID) from MOB_ACCT_DATA_TEMP where REF_NUM=?)";
		}
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			//resultJson.put("institute",detRS.getString(10));
		}
		
		
		if(status.equalsIgnoreCase("D"))
		{
		detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_HIST where REF_NUM=?";
		}
		else{
		detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_TEMP where REF_NUM=?";
		}
		detPstmt1 = connection.prepareStatement(detQry1);
		detPstmt1.setString(1, ref_no);
		
		rs = detPstmt1.executeQuery();
		
		int i = 0;
		
		
		
		while (rs.next()) {
			eachrow = rs.getString(1) + ","
					+ rs.getString(2) + ","
					+ rs.getString(3) + ","
					+ rs.getString(4) + ","
					+ rs.getString(5);
			
			if (i == 0) {
				billerData += eachrow;
			} else {
				billerData += "#" + eachrow;
			}
			i++;
		}
		
		
		
		
		resultJson.put("multiData",billerData);
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
		eachrow = null;
		billerData = null;
	}
	
	return responseDTO;
	
}


public ResponseDTO addnewAccounts(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";
	
	
	String ref_no=null;
	String status=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		
		
		ref_no = requestJSON.getString("ref_no");
		status = requestJSON.getString("status");
		logger.debug("reference number"+ref_no+"  status in del"+status);
		
		if(status.equalsIgnoreCase("D"))
				{
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
				+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER_HIST CM,MOB_CONTACT_INFO_HIST CI "
				+ "WHERE CI.CUST_ID=CM.ID AND CM.ID=(select distinct(CUST_ID) from MOB_ACCT_DATA_HIST where REF_NUM=?)";
		}
		else{
		detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
				+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER_TEMP CM,MOB_CONTACT_INFO_TEMP CI "
				+ "WHERE CI.CUST_ID=CM.ID AND CM.ID=(select distinct(CUST_ID) from MOB_ACCT_DATA_TEMP where REF_NUM=?)";
		}
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			//resultJson.put("institute",detRS.getString(10));
		}
		
		
		if(status.equalsIgnoreCase("D"))
		{
		detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_HIST where REF_NUM=?";
		}
		else{
		detQry1 = "select ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME from MOB_ACCT_DATA_TEMP where REF_NUM=?";
		}
		detPstmt1 = connection.prepareStatement(detQry1);
		detPstmt1.setString(1, ref_no);
		
		rs = detPstmt1.executeQuery();
		
		int i = 0;
		
		
		
		while (rs.next()) {
			eachrow = rs.getString(1) + ","
					+ rs.getString(2) + ","
					+ rs.getString(3) + ","
					+ rs.getString(4) + ","
					+ rs.getString(5);
			
			if (i == 0) {
				billerData += eachrow;
			} else {
				billerData += "#" + eachrow;
			}
			i++;
		}
		
		
		
		
		resultJson.put("multiData",billerData);
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
		eachrow = null;
		billerData = null;
	}
	
	return responseDTO;
	
}

public ResponseDTO devicereplace(RequestDTO requestDTO) {
	
	logger.debug("Inside Account Details Fetching . ");
	
	HashMap<String, Object> detMap = null;
	String detQry = "";
	String detQry1 = "";
	JSONObject resultJson = null;
	Connection connection = null;
	PreparedStatement detPstmt = null;
	PreparedStatement detPstmt1 = null;
	ResultSet detRS = null;
	ResultSet rs = null;
	String eachrow = "";
	String billerData = "";
	
	
	String ref_no=null;
	String status=null;
	
	try {
		responseDTO = new ResponseDTO();
		detMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		requestJSON = requestDTO.getRequestJSON();
		
		
		logger.debug("Request JSON [" + requestJSON + "]");
		
		//resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
		
		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");
		
		
		
		ref_no = requestJSON.getString("ref_no");
		status = requestJSON.getString("status");
		logger.debug("reference number"+ref_no+"  status in del"+status);
		
		if(status.equalsIgnoreCase("D"))
				{
			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_IMEI_DATA_TEMP MID "
					+ "WHERE CI.CUST_ID=CM.ID AND CM.USER_ID=MID.USER_ID AND MID.REF_NO=?";
		}
		else{
		detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID,"
				+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CM.REMARKS,CM.M_PRD_CODE,CM.M_PRD_DESC FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_IMEI_DATA_TEMP MID "
				+ "WHERE CI.CUST_ID=CM.ID AND CM.USER_ID=MID.USER_ID AND MID.REF_NO=?";
		}
		detPstmt = connection.prepareStatement(detQry);
		detPstmt.setString(1,ref_no);
		detRS = detPstmt.executeQuery();
		
		
		if (detRS.next()) {
			resultJson.put("custcode",detRS.getString(1));
			resultJson.put("fullname",detRS.getString(2));
			resultJson.put("telco",detRS.getString(3));
			resultJson.put("isocode",detRS.getString(4));
			resultJson.put("mobileno",detRS.getString(5));
			resultJson.put("nationalid",detRS.getString(6));
			resultJson.put("language",detRS.getString(7));
			resultJson.put("email",detRS.getString(8));
			resultJson.put("remarks",detRS.getString(9));
			resultJson.put("product",detRS.getString(10));
			resultJson.put("prodesc",detRS.getString(11));
			//resultJson.put("institute",detRS.getString(10));
		}
		
		
		if(status.equalsIgnoreCase("D"))
		{
		detQry1 = "select NVL(MAC_ADDR,' '),NVL(DEVICE_IP,' '),NVL(IMEI_NO,' '),NVL(SERIAL_NO,' '),NVL(DEVICE_TYPE,' ') from MOB_IMEI_DATA_TEMP where REF_NO=?";
		
		}
		else{
		detQry1 = "select NVL(MAC_ADDR,' '),NVL(DEVICE_IP,' '),NVL(IMEI_NO,' '),NVL(SERIAL_NO,' '),NVL(DEVICE_TYPE,' ') from MOB_IMEI_DATA_TEMP where REF_NO=?";
		}
		detPstmt1 = connection.prepareStatement(detQry1);
		detPstmt1.setString(1, ref_no);
		
		rs = detPstmt1.executeQuery();
		
		int i = 0;
		
		
		
		while (rs.next()) {
			eachrow = rs.getString(1) + ","
					+ rs.getString(2) + ","
					+ rs.getString(3) + ","
					+ rs.getString(4) + ","
					+ rs.getString(5);
			
			if (i == 0) {
				billerData += eachrow;
			} else {
				billerData += "#" + eachrow;
			}
			i++;
		}
		
		
		
		
		resultJson.put("multiData",billerData);
		detMap.put("user_rights", resultJson);
		
		
		responseDTO.setData(detMap);
		
	} catch (SQLException e) {
		logger.debug("SQLException in Account Fetch Details ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing. ");
		e.printStackTrace();
	} catch (Exception e) {
		logger.debug("Exception in Account Fetch Detials ["
				+ e.getMessage() + "]");
		responseDTO.addError("Internal Error Occured While Executing.");
		e.printStackTrace();
	} finally {
		
		DBUtils.closePreparedStatement(detPstmt1);
		DBUtils.closeResultSet(rs);
		
		DBUtils.closePreparedStatement(detPstmt);
		DBUtils.closeResultSet(detRS);
		DBUtils.closeConnection(connection);
		
		detQry = null;
		eachrow = null;
		billerData = null;
	}
	
	return responseDTO;
	
}


public ResponseDTO addBillerIDAuth(RequestDTO requestDTO) {
	
	
	logger.debug("Inside Add Biller Id Auth .... ");

	HashMap<String, Object> merchantMap = null;
	String merchantQry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement merchantPstmt = null;
	ResultSet merchantRS = null;
	String ref_no = "";
 
	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

		ref_no = requestJSON.getString("ref_no");
		
		merchantQry = "select  BILLER_ID,BILLER_TYPE_ID  from MPESA_ACCTMGT_BILLER_ID_TEMP where REF_NUM=?";

		merchantPstmt = connection.prepareStatement(merchantQry);

		merchantPstmt.setString(1, ref_no);
		merchantRS = merchantPstmt.executeQuery();

		if (merchantRS.next()) {
			
			resultJson.put("billerId",merchantRS.getString(1));
			resultJson.put("billerIdType",merchantRS.getString(2));
			 
		}
			
		merchantPstmt.close();
		merchantRS.close();

        merchantQry = " select DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT,Decode(STATUS,'A','Active','B','Inactive')  from MPESA_ACCTMGT_BILLER_ID_TEMP where BILLER_TYPE_ID=? and REF_NUM=?";
		
		merchantPstmt = connection.prepareStatement(merchantQry);
		
		merchantPstmt.setString(1, resultJson.getString("billerIdType")); 
		merchantPstmt.setString(2, ref_no);
		merchantRS = merchantPstmt.executeQuery();
	 
		if(merchantRS.next()) {
			
			resultJson.put("billerIdDescription",merchantRS.getString(1));
			resultJson.put("bfubCreditAccount",merchantRS.getString(2));
			resultJson.put("bfubDebitAccount",merchantRS.getString(3));
			resultJson.put("status",merchantRS.getString(4)); 
			
		}
		
		merchantPstmt.close();
		merchantRS.close();
		 
		
		merchantMap.put("user_rights", resultJson);
		
		merchantPstmt.close();
		merchantRS.close();

		 
		
		logger.debug("MerchantMap [" + merchantMap + "]");
		
		responseDTO.setData(merchantMap);
		
		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
}

public ResponseDTO createProductAuth(RequestDTO requestDTO) {
	
	
	logger.debug("Inside Create ProductAuth .... ");

	HashMap<String, Object> merchantMap = null;
	String merchantQry = "";
	JSONObject resultJson = null;

	Connection connection = null;
	PreparedStatement merchantPstmt = null;
	ResultSet merchantRS = null;
	String ref_no = "";
 
	try {

		responseDTO = new ResponseDTO();
		merchantMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");
		responseJSON = requestJSON;

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + (connection == null) + "]");

		ref_no = requestJSON.getString("ref_no");
		
		merchantQry = "select ACTYPE,BUSINESSUNIT,DESCRIPTION,PRODUCTID,CHANNELID,CUSTOMERTYPE,CUSTOMERSUBTYPE,CUSTSEGMENTID,SUBPRODUCTID,ISCUSTSWIFTENABLED,"
                      +"ISCHEQUEBOOKREQ,CHEQUEBOOKTYPE,RESTRICTIONFLAG,ISDEBITCARDREQ,ISESTATEMENTREQ,ISINTERNETBANKINGREQ,ISBANCASSURANCEREQ,ISSIMPLEBANKINGREQ,"
                      +"ISCREDITCARDREQ from MOB_PRODUCTS_TEMP where REF_NUM= ?";

		merchantPstmt = connection.prepareStatement(merchantQry);

		merchantPstmt.setString(1, ref_no);
		merchantRS = merchantPstmt.executeQuery();

		if (merchantRS.next()) {
			
			resultJson.put("acType",merchantRS.getString(1));
			resultJson.put("businessUnit",merchantRS.getString(2));
			resultJson.put("description",merchantRS.getString(3));
			resultJson.put("productCode",merchantRS.getString(4));
			resultJson.put("channelID",merchantRS.getString(5));
			resultJson.put("customerType",merchantRS.getString(6));
			resultJson.put("customerSubType",merchantRS.getString(7));
			resultJson.put("custSegmentID",merchantRS.getString(8));
			resultJson.put("subProductID",merchantRS.getString(9));
			resultJson.put("isCustSWIFTEnabled",merchantRS.getString(10));
			resultJson.put("isChequeBookReq",merchantRS.getString(11));
			resultJson.put("chequeBookType",merchantRS.getString(12));
			resultJson.put("restrictionFlag",merchantRS.getString(13));
			resultJson.put("isdebitCardReq",merchantRS.getString(14));
			resultJson.put("isEStatementReq",merchantRS.getString(15));
			resultJson.put("isInternetBankingReq",merchantRS.getString(16));
			resultJson.put("isBancassuranceReq",merchantRS.getString(17));
			resultJson.put("isSimpleBankingReq",merchantRS.getString(18));
			resultJson.put("isCreditCardReq",merchantRS.getString(19));
		}
			
		merchantPstmt.close();
		merchantRS.close();
 
		merchantMap.put("user_rights", resultJson);
		 
		logger.debug("MerchantMap [" + merchantMap + "]");
		
		responseDTO.setData(merchantMap);
		
		logger.debug("Response DTO [" + responseDTO + "]");

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("SQLException in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Get Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
} 


	
}
