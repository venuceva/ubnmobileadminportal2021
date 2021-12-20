package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.bean.AccountPropertiesBean;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

public class AccountPropertiesDAO {
	Logger logger = Logger.getLogger(AccountPropertiesDAO.class);
	ResponseDTO responseDTO = new ResponseDTO();
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	String insertProperties = "{call ACCOUNTSPROPPKG.INSERTACCOUNTPROPERTIES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	String updateProperties = "{call ACCOUNTSPROPPKG.UPDATEACCOUNTPROPERTIES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	HashMap<String, Object> dataMap = null;
	Connection connection = null;
	CallableStatement cstmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	


	public ResponseDTO insertProperties(RequestDTO requestDTO) {

		JSONArray acprops = new JSONArray();
		JSONObject obj = null;
		JSONObject resultJson = null;
		JSONObject acctPropBean = null;
		
		
		try {
			requestJSON = requestDTO.getRequestJSON();
			resultJson = new JSONObject();
			acctPropBean = requestJSON.getJSONObject("accountPropBean");

			responseDTO = new ResponseDTO();
			dataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection()
					: connection;
			cstmt = connection.prepareCall(insertProperties);
			cstmt.setString(1, acctPropBean.getString("channelID"));
			cstmt.setString(2, acctPropBean.getString("customerType"));
			cstmt.setString(3, acctPropBean.getString("customerSubType"));
			cstmt.setString(4, acctPropBean.getString("businessUnit"));
			cstmt.setString(5, acctPropBean.getString("custSegmentID"));
			cstmt.setString(6, acctPropBean.getString("isCustSWIFTEnabled"));
			cstmt.setString(7, acctPropBean.getString("subProductID"));
			cstmt.setString(8, acctPropBean.getString("isChequeBookReq"));
			cstmt.setString(9, acctPropBean.getString("chequeBookType"));
			cstmt.setString(10, acctPropBean.getString("isCreditCardReq"));
			cstmt.setString(11, acctPropBean.getString("isdebitCardReq"));
			cstmt.setString(12, acctPropBean.getString("isEStatementReq"));
			cstmt.setString(13, acctPropBean.getString("isInternetBankingReq"));
			cstmt.setString(14, acctPropBean.getString("isBancassuranceReq"));
			cstmt.setString(15, acctPropBean.getString("isSimpleBankingReq"));
			cstmt.setString(16, acctPropBean.getString("restrictionFlag"));
			cstmt.setString(17, acctPropBean.getString("acType"));
			cstmt.setString(18,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(19, requestJSON.getString(CevaCommonConstants.IP));
			cstmt.setString(20, acctPropBean.getString("productCode"));
			cstmt.registerOutParameter(21, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(22, OracleTypes.CURSOR);
			cstmt.setString(23, acctPropBean.getString("description"));

			cstmt.executeQuery();
			logger.debug("executed procedure....");
			String message = cstmt.getNString(21);
			if ("SUCCESS".equals(message)) {
				rs = (ResultSet) cstmt.getObject(22);
				obj = new JSONObject();

				while (rs.next()) {
					obj.put("acType", rs.getString(1));
					obj.put("customerType", rs.getString(2));
					obj.put("businessUnit", rs.getString(3));
					obj.put("custSegmentID", rs.getString(4));
					obj.put("branchCode", rs.getString(5));
					obj.put("subProductID", rs.getString(6));
					obj.put("isdebitCardReq", rs.getString(7));
					obj.put("accountId", rs.getString(8));
					acprops.add(obj);
					obj.clear();
				}

				resultJson.put("ACRECORDS", acprops);
				dataMap.put("ACRECORDS", resultJson);
				responseDTO.setData(dataMap);
			} else {
				responseDTO.addError(message);
			}

		} catch (Exception ex) {
			logger.error("Error Occured ..!" + ex.getLocalizedMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
				obj = null;
			} catch (Exception es) {
			}
		}
		return responseDTO;
	}

	public ResponseDTO getListAll(RequestDTO requestDTO) {

		JSONArray acprops = null;
		JSONObject obj = null;
		JSONObject resultJson = null;
		String qry = "select ACTYPE, CUSTOMERTYPE, BUSINESSUNIT, CUSTSEGMENTID,BRANCHCODE, SUBPRODUCTID, ISDEBITCARDREQ, "
				+ "RESTRICTIONFLAG, ACCOUNTID,PRODUCTID,CHANNELID,CUSTOMERSUBTYPE from  MOB_PRODUCTS";
		try {
			acprops = new JSONArray();
			responseDTO = new ResponseDTO();
			dataMap = new HashMap<String, Object>();
			connection = connection == null ? DBConnector.getConnection()
					: connection;

			resultJson = new JSONObject();

			pstmt = connection.prepareStatement(qry);
			rs = pstmt.executeQuery();
			obj = new JSONObject();

			while (rs.next()) {
				obj.put("acType", rs.getString(1));
				obj.put("customerType", rs.getString(2));
				obj.put("businessUnit", rs.getString(3));
				obj.put("custSegmentID", rs.getString(4));
				obj.put("branchCode", rs.getString(5));
				obj.put("subProductID", rs.getString(6));
				obj.put("isdebitCardReq", rs.getString(7));
				obj.put("restrictionFlag", rs.getString(8));
				obj.put("accountId", rs.getString(9));
				obj.put("productCode", rs.getString(10));
				obj.put("channelID", rs.getString(11));
				obj.put("customerSubType", rs.getString(12));
				acprops.add(obj);
				obj.clear();
			}

			resultJson.put("ACRECORDS", acprops);
			dataMap.put("ACRECORDS", resultJson);
			responseDTO.setData(dataMap);
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
				obj = null;
			} catch (Exception es) {
			}
		}

		return responseDTO;
	}

	public ResponseDTO getRecord(RequestDTO requestDTO) {

		String qry = "select ACCOUNTID,CHANNELID, CUSTOMERTYPE, CUSTOMERSUBTYPE, BUSINESSUNIT, CUSTSEGMENTID, "
				+ "IDENTIFICATIONTYPE, IDENTIFICATIONNO, BRANCHCODE, ISCUSTSWIFTENABLED, SUBPRODUCTID, "
				+ "ISCHEQUEBOOKREQ, CHEQUEBOOKTYPE,ISCREDITCARDREQ, ISDEBITCARDREQ, ISESTATEMENTREQ, ISINTERNETBANKINGREQ, "
				+ "ISBANCASSURANCEREQ, ISSIMPLEBANKINGREQ, RESTRICTIONFLAG, ACTYPE,PRODUCTID,description from MOB_PRODUCTS where ACCOUNTID=?";

		AccountPropertiesBean accountPropBean = null;
		JSONObject request = null;
		try {
			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();
			dataMap = new HashMap<String, Object>();
			accountPropBean = new AccountPropertiesBean();

			request = (JSONObject) requestJSON.get("accountPropBean");

			logger.info("RequestJSON in DAO [" + requestJSON + "]");

			connection = connection == null ? DBConnector.getConnection()
					: connection;
			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, request.getString("accountId"));

			rs = pstmt.executeQuery();
			if (rs.next()) {
				accountPropBean.setAccountId(rs.getString(1));
				accountPropBean.setChannelID(rs.getString(2));
				accountPropBean.setCustomerType(rs.getString(3));
				accountPropBean.setCustomerSubType(rs.getString(4));
				accountPropBean.setBusinessUnit(rs.getString(5));
				accountPropBean.setCustSegmentID(rs.getString(6));

				accountPropBean.setIdentificationType(rs.getString(7));
				accountPropBean.setIdentificationNo(rs.getString(8));
				accountPropBean.setBranchCode(rs.getString(9));
				accountPropBean.setIsCustSWIFTEnabled(rs.getString(10));
				accountPropBean.setSubProductID(rs.getString(11));

				accountPropBean.setIsChequeBookReq(rs.getString(12));
				accountPropBean.setChequeBookType(rs.getString(13));
				accountPropBean.setIsCreditCardReq(rs.getString(14));
				accountPropBean.setIsdebitCardReq(rs.getString(15));
				accountPropBean.setIsEStatementReq(rs.getString(16));
				accountPropBean.setIsInternetBankingReq(rs.getString(17));

				accountPropBean.setIsBancassuranceReq(rs.getString(18));
				accountPropBean.setIsSimpleBankingReq(rs.getString(19));
				accountPropBean.setRestrictionFlag(rs.getString(20));
				accountPropBean.setAcType(rs.getString(21));
				accountPropBean.setProductCode(rs.getString(22));
				
				accountPropBean.setDescription(rs.getString(23));
			}

			dataMap.put("accountPropBean", accountPropBean);
			responseDTO.setData(dataMap);
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception es) {
			}
		}
		return responseDTO;
	}

	
	public ResponseDTO updateProperties(RequestDTO requestDTO) {
		
		JSONArray acprops = null;
		JSONObject obj = null;
		JSONObject resultJson = null;
		JSONObject acctPropBean = null;
		try {
			
			requestJSON = requestDTO.getRequestJSON();
			acctPropBean = requestJSON.getJSONObject("accountPropBean");
			acprops = new JSONArray();
			resultJson = new JSONObject();
			
			responseDTO = new ResponseDTO();
			dataMap = new HashMap<String, Object>();
			
			connection = connection == null ? DBConnector.getConnection()
					: connection;
			cstmt = connection.prepareCall(updateProperties);
			
			cstmt.setString(1, acctPropBean.getString("channelID"));
			cstmt.setString(2, acctPropBean.getString("customerType"));
			cstmt.setString(3, acctPropBean.getString("customerSubType"));
			cstmt.setString(4, acctPropBean.getString("businessUnit"));
			cstmt.setString(5, acctPropBean.getString("custSegmentID"));
			cstmt.setString(6, acctPropBean.getString("isCustSWIFTEnabled"));
			cstmt.setString(7, acctPropBean.getString("subProductID"));
			cstmt.setString(8, acctPropBean.getString("isChequeBookReq"));
			cstmt.setString(9, acctPropBean.getString("chequeBookType"));
			cstmt.setString(10, acctPropBean.getString("isCreditCardReq"));
			cstmt.setString(11, acctPropBean.getString("isdebitCardReq"));
			cstmt.setString(12, acctPropBean.getString("isEStatementReq"));
			cstmt.setString(13, acctPropBean.getString("isInternetBankingReq"));
			cstmt.setString(14, acctPropBean.getString("isBancassuranceReq"));
			cstmt.setString(15, acctPropBean.getString("isSimpleBankingReq"));
			cstmt.setString(16, acctPropBean.getString("restrictionFlag"));
			cstmt.setString(17, acctPropBean.getString("acType"));
			cstmt.setString(18,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(19, requestJSON.getString(CevaCommonConstants.IP));
			cstmt.setString(20, acctPropBean.getString("productCode"));
			cstmt.setString(21, acctPropBean.getString("accountId"));
			cstmt.registerOutParameter(22, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(23, OracleTypes.CURSOR);
			cstmt.setString(24, acctPropBean.getString("description"));
			
			cstmt.executeQuery();
			logger.debug("executed procedure....");
			String message = cstmt.getNString(22);
			if ("SUCCESS".equals(message)) {
				rs = (ResultSet) cstmt.getObject(23);
				obj = new JSONObject();
				while (rs.next()) {
					obj.put("acType", rs.getString(1));
					obj.put("customerType", rs.getString(2));
					obj.put("businessUnit", rs.getString(3));
					obj.put("custSegmentID", rs.getString(4));
					obj.put("branchCode", rs.getString(5));
					obj.put("subProductID", rs.getString(6));
					obj.put("isdebitCardReq", rs.getString(7));
					obj.put("accountId", rs.getString(8));
					acprops.add(obj);
					obj.clear();
				}
				
				resultJson.put("ACRECORDS", acprops);
				dataMap.put("ACRECORDS", resultJson);
				responseDTO.setData(dataMap);
			} else {
				responseDTO.addError(message);
			}
			
			logger.debug(acprops);
		} catch (Exception ex) {
			logger.error("Error Occured ..!" + ex.getLocalizedMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
				obj = null;
			} catch (Exception es) {
			}
		}
		return responseDTO;
	}
	
	
	
	public ResponseDTO fetchProducts(RequestDTO requestDTO) {
		logger.debug("Inside GetStores DAO.. ");
		String storeQry = "";

		HashMap<String, Object> resultMap = null;
		JSONArray storeJSONArray = null;
		Connection connection = null;

		PreparedStatement storePstmt = null;
		ResultSet storeRS = null;
		String institute=null;
		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			institute=requestJSON.getString("Institute");
			logger.debug("Request JSON [" + requestJSON + "]");
			resultMap = new HashMap<String, Object>();
			storeJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Connection is  [" + connection+ "]");
			storeQry = "SELECT SUB_PRODUCT_ID,SUB_PRODUCT_ID_DESC from MOB_PRODUCTS_MASTER  where INSTITUTE_ID=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1,requestJSON.getString("Institute"));
			storeRS = storePstmt.executeQuery();
			while (storeRS.next()) {
				JSONObject json = new JSONObject();
				json.put("key", storeRS.getString(2));
				json.put("val", storeRS.getString(1));
				storeJSONArray.add(json);
			}
			
			responseJSON.put("PRODUCT_LIST", storeJSONArray);
			logger.debug("Response JSON [" + responseJSON + "]");
			resultMap.put("PRODUCT_LIST", responseJSON);
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
	


	public ResponseDTO fetchServices(RequestDTO requestDTO) {
		logger.debug("Inside Fetch Services in DAO... ");
		String userQry = "";
		String userQry1 = "";
		//String userQry1 = "SELECT RUG.REPORT_ID,(select REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE REPORT_ID=RUG.REPORT_ID) FROM REPORT_USER_GROUP RUG WHERE RUG.USER_ID=? order by to_number(RUG.REPORT_ID) ";
		Connection connection = null;
		ResultSet userRS = null;
		PreparedStatement userPstmt = null;

		JSONObject json1 = null;
		JSONObject json = null;

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;
		JSONArray userJSONArray1 = null;

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null  [" + connection == null + "]");
			

			userQry = "SELECT SERVICE_CODE,SERVICE_NAME FROM MOB_SERVICE_MASTER";
			//userQry1 = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='0'";
			userQry1 = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='0' "
					+ "and SERVICEPACK_CODE not in (select distinct(menu_code) from MOB_SERVICEPACK_MAP)";
	
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();
			userJSONArray1 = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			//userPstmt.setString(1, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, "SERVICE--"+userRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
				userJSONArray.add(json);
				//System.out.println("into while "+json);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry :::" + userJSONArray);
			//logger.debug("USER_ID :::" + requestJSON.getString("USER_ID"));

			userPstmt = connection.prepareStatement(userQry1);
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json1 = new JSONObject();
				json1.put(CevaCommonConstants.SELECT_KEY, "MENULIST--"+userRS.getString(1));
				json1.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
				userJSONArray1.add(json1);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("output from query"+userJSONArray);
			responseJSON.put("SERVICES", userJSONArray);
			responseJSON.put("MENULIST", userJSONArray1);
			logger.debug("output from query"+userJSONArray1);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing1.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(userPstmt);

			resultMap = null;
			userJSONArray = null;
			userJSONArray1 = null;
			json1 = null;
			json = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO insertservicepack(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		String ip = null;
		String makerid = null;
		
		try {
			
			insQRY = "{call PRODUCTPKG.INSERTSERVICEPACK(?,?,?,?,?,?,?,?)}";
			 
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON(); 
			resultJson = (JSONObject) requestJSON.get("accountPropBean");
			
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("menucode"));
			cstmt.setString(2, resultJson.getString("menuname"));
			cstmt.setString(3, resultJson.getString("dispname"));
			cstmt.setString(4, resultJson.getString("menulevel"));
			cstmt.setString(5, resultJson.getString("multiData"));
			cstmt.setString(6, makerid);
			cstmt.setString(7,ip);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.executeQuery();
			

			if (!cstmt.getString(8).contains("SUCCESS")) { 
				responseDTO.addError(cstmt.getString(8));
			}

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO insertproductlimits(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		
		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		
		String ip = null;
		String makerid = null;
		
		try {
			
			insQRY = "{call PRODUCTPKG.INSERTPRODUCTSERLIMITS(?,?,?,?,?,?)}";
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON(); 
			resultJson = (JSONObject) requestJSON.get("accountPropBean");
			System.out.println("resultJson===============>"+resultJson);
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			connection = DBConnector.getConnection();
			
			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("productid"));
			//cstmt.setString(2, resultJson.getString("prddesc"));
			cstmt.setString(2, resultJson.getString("institute"));
			//cstmt.setString(4, resultJson.getString("subproductData"));
			cstmt.setString(3, resultJson.getString("limitData"));
			cstmt.setString(4, makerid);
			cstmt.setString(5,ip);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.executeQuery();
			
			logger.debug("Responce From DB "+cstmt.getString(6));
			
			if (!cstmt.getString(6).contains("SUCCESS")) { 
				responseDTO.addError(cstmt.getString(8));
			}
			
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
/*	public ResponseDTO insertproductlimits(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		
		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		
		String ip = null;
		String makerid = null;
		
		try {
			
			insQRY = "{call PRODUCTPKG.INSERTPRODUCTLIMITS(?,?,?,?,?,?,?,?)}";
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON(); 
			resultJson = (JSONObject) requestJSON.get("accountPropBean");
			System.out.println("resultJson===============>"+resultJson);
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			connection = DBConnector.getConnection();
			
			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("productid"));
			cstmt.setString(2, resultJson.getString("prddesc"));
			cstmt.setString(3, resultJson.getString("institute"));
			cstmt.setString(4, resultJson.getString("subproductData"));
			cstmt.setString(5, resultJson.getString("limitData"));
			cstmt.setString(6, makerid);
			cstmt.setString(7,ip);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.executeQuery();
			
			logger.debug("Responce From DB "+cstmt.getString(8));
			
			if (!cstmt.getString(8).contains("SUCCESS")) { 
				responseDTO.addError(cstmt.getString(8));
			}
			
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
*/	
	
	
	
	public ResponseDTO fetchMenuDetails(RequestDTO requestDTO) {
		logger.debug("Inside Fetch Services in DAO... ");
		String userQry = "";
		String mcode = "";
		Connection connection = null;
		ResultSet userRS = null;
		PreparedStatement userPstmt = null;

		JSONObject json = null;
		JSONObject resultJson = null;

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			
			

			connection = DBConnector.getConnection();
			logger.debug("Connection is null  [" + connection == null + "]");
			

			userQry = "select MENU_TYPE,DISPLAY_NAME,STATUS from MOB_SERVICEPACK_MAP where pid=(select id from MOB_SERVICEPACK_MASTER where SERVICEPACK_CODE=?";
	
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1,mcode);
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
				userJSONArray.add(json);
				System.out.println("into while "+json);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry :::" + userJSONArray);
			//logger.debug("USER_ID :::" + requestJSON.getString("USER_ID"));

			logger.debug("output from query"+userJSONArray);
			responseJSON.put("MENULISTDET", userJSONArray);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing1.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(userPstmt);

			resultMap = null;
			userJSONArray = null;
			json = null;
		}
		return responseDTO;
	}
	
	
	
		public ResponseDTO fetchMenulists(RequestDTO requestDTO) {

			logger.debug("Inside FeeCreateScrrenDetails.. ");
			HashMap<String, Object> serviceMap = null;

			PreparedStatement storePstmt = null;
			ResultSet storeRS = null;
			Connection connection = null;

			String storeQry = " ";
			JSONObject json = null;
			try {
				serviceMap = new HashMap<String, Object>();

				responseDTO = new ResponseDTO();
				responseJSON = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();

				logger.debug("Request JSON [" + requestJSON + "]");

				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				
				
				connection = DBConnector.getConnection();
				logger.debug("Connection is null  [" + connection == null + "]");

				storeQry = "SELECT SERVICEPACK_NAME,SERVICEPACK_CODE||'-'||SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='1'";
		
				storePstmt = connection.prepareStatement(storeQry);
				storeRS = storePstmt.executeQuery();

				json = new JSONObject();
				
				while (storeRS.next()) {
					json.put(storeRS.getString(2), storeRS.getString(1));
						}
				storeQry = null;
				storePstmt.close();
				storeRS.close();

				logger.debug("UserQry :::" + json);
				responseJSON.put("MENULISTS", json);
				json.clear();
				storeQry = "select KEY_VALUE,KEY_ID||'-'||KEY_VALUE FROM CONFIG_DATA WHERE KEY_GROUP='FREQUENCYDATA' AND KEY_TYPE='FREQUENCYPERIOD' order by KEY_ID";
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
				
				serviceMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				responseDTO.setData(serviceMap);
				logger.debug("Response DTO [" + responseDTO + "]");
			} catch (SQLException e) {
				logger.debug("SQLException in FeeCreateScrrenDetails ["+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {

				logger.debug("SQLException in FeeCreateScrrenDetails ["+ e.getMessage() + "]");
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
		
		
		public ResponseDTO fetchspdet(RequestDTO requestDTO) {

			logger.debug("Inside Account Details Fetching . ");

			HashMap<String, Object> detMap = null;
			String detQry = "";
			String detQry1 = "";
			JSONObject resultJson = null;
			Connection connection = null;
			PreparedStatement detPstmt = null;
			PreparedStatement detPstmt1 = null;
			ResultSet detRS = null;
			ResultSet detRS2 = null;
			ResultSet rs = null;

			AccountPropertiesBean accountPropBean = null;
			StringBuilder billerData = null;
			StringBuilder eachrow = null;
			String val=null;
			String spcode=null;
			boolean flag = false;

			try {
				responseDTO = new ResponseDTO();
				detMap = new HashMap<String, Object>();
				accountPropBean = new AccountPropertiesBean();
				requestJSON = requestDTO.getRequestJSON();

				logger.debug("Request JSON [" + requestJSON + "]");

				resultJson = (JSONObject) requestDTO.getRequestJSON().get("accountPropBean");
				

				responseDTO = new ResponseDTO();
				dataMap = new HashMap<String, Object>();
				
				System.out.println("seltext value from page is "+resultJson.getString("seltext"));
				System.out.println("selval value from page is "+resultJson.getString("selval"));
				System.out.println("prddesc value from page is "+resultJson.getString("prddesc"));
				spcode=resultJson.getString("prddesc");
				val=resultJson.getString("selval");
				val=val.replace(",","','");
				val = val.substring(0, val.length()-2);
				
				connection = DBConnector.getConnection();

				detQry = "select MENU_TYPE||'--'||MENU_CODE,DISPLAY_NAME,TITLE,SER_STATUS from MOB_SERVICEPACK_MAP where MENU_CODE in ('"+val+") "
						+ "AND pid=(SELECT id FROM MOB_SERVICEPACK_MASTER WHERE SERVICEPACK_CODE='"+spcode+"')";

				/*logger.debug("First Query Executed" + detQry);
				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, resultJson.getString("customercode"));
				detRS = detPstmt.executeQuery();
				accBean = new AccountBean();*/

				
				//logger.debug("Bean Details   :::: " + accBean);
				logger.debug("into acc details");
				//detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ALIAS_NAME,'-') FROM MOB_ACCT_DATA A WHERE A.CUST_ID=(select ID from MOB_customer_master where customer_code=? )";

					logger.debug("Acc details execution query [ " + detQry + " ]");

					detPstmt1 = connection.prepareStatement(detQry);
					//detPstmt1.setString(1, resultJson.getString("customercode"));

					rs = detPstmt1.executeQuery();

					int i = 0;
					eachrow = new StringBuilder(50);
					billerData = new StringBuilder(50);

					while (rs.next()) {
						eachrow.append(rs.getString(1)).append(",")
								.append(rs.getString(2)).append(",")
								.append(rs.getString(3)).append(",")
								.append(rs.getString(4));
						if (i == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						i++;
						eachrow.delete(0, eachrow.length());
					}

					accountPropBean.setMultiData(billerData.toString());

					logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "	+ billerData);
					detMap.put("AccountData", accountPropBean);

					billerData.delete(0, eachrow.length());
				

				logger.debug("EntityMap [" + detMap + "]");
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

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(detRS2);

				detQry = null;
				eachrow = null;
				billerData = null;
			}

			return responseDTO;

		}
		/*public ResponseDTO fetchservicepacks(RequestDTO requestDTO) {
			
			logger.debug("Inside Fetch Service Packs.. ");
			HashMap<String, Object> serviceMap = null;
			
			PreparedStatement storePstmt = null;
			ResultSet storeRS = null;
			Connection connection = null;
			
			String storeQry = " ";
			JSONObject json = null;
			try {
				serviceMap = new HashMap<String, Object>();
				
				responseDTO = new ResponseDTO();
				responseJSON = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();
				
				logger.debug("Request JSON [" + requestJSON + "]");
				
				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				
				
				connection = DBConnector.getConnection();
				logger.debug("Connection is null  [" + connection == null + "]");
				
				storeQry = "SELECT SERVICEPACK_NAME,SERVICEPACK_CODE||'-'||SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER ";
				
				storePstmt = connection.prepareStatement(storeQry);
				storeRS = storePstmt.executeQuery();
				
				json = new JSONObject();
				
				while (storeRS.next()) {
					json.put(storeRS.getString(2), storeRS.getString(1));
				}
				storeQry = null;
				storePstmt.close();
				storeRS.close();
				
				logger.debug("UserQry :::" + json);
				responseJSON.put("SERVICEPACKS", json);
				json.clear();
				
				
				
				
				serviceMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				responseDTO.setData(serviceMap);
				logger.debug("Response DTO [" + responseDTO + "]");
			} catch (SQLException e) {
				logger.debug("SQLException in Fetch Service Packs.. ["+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {
				
				logger.debug("SQLException in Fetch Service Packs.. ["+ e.getMessage() + "]");
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
		}*/
		
		
		public ResponseDTO fetchservicepacks(RequestDTO requestDTO) {
			logger.debug("Inside Fetch Services in DAO... ");
			String userQry = "";
			String userQry1 = "";
			Connection connection = null;
			ResultSet userRS = null;
			PreparedStatement userPstmt = null;

			JSONObject json1 = null;
			JSONObject json = null;

			HashMap<String, Object> resultMap = null;

			JSONArray userJSONArray = null;
			JSONArray userJSONArray1 = null;

			try {

				logger.debug("Inside Fetch Service Packs.. ");
				responseDTO = new ResponseDTO();
				responseJSON = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();

				logger.debug("Request JSON [" + requestJSON + "]");

				connection = DBConnector.getConnection();
				logger.debug("Connection is null  [" + connection == null + "]");
				

				userQry = "SELECT SERVICEPACK_NAME,SERVICEPACK_CODE||'-'||SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER ";

				userQry1 = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='0' "
						+ "and SERVICEPACK_CODE not in (select distinct(menu_code) from MOB_SERVICEPACK_MAP)";
		
				resultMap = new HashMap<String, Object>();
				userJSONArray = new JSONArray();
				userJSONArray1 = new JSONArray();

				userPstmt = connection.prepareStatement(userQry);
				//userPstmt.setString(1, requestJSON.getString("USER_ID"));
				userRS = userPstmt.executeQuery();

				while (userRS.next()) {
					json = new JSONObject();
					json.put(CevaCommonConstants.SELECT_KEY, "SERVICE--"+userRS.getString(1));
					json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
					userJSONArray.add(json);
				}

				userPstmt.close();
				userRS.close();

				logger.debug("UserQry :::" + userJSONArray);

				userPstmt = connection.prepareStatement(userQry1);
				userRS = userPstmt.executeQuery();

				while (userRS.next()) {
					json1 = new JSONObject();
					json1.put(CevaCommonConstants.SELECT_KEY, "MENULIST--"+userRS.getString(1));
					json1.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
					userJSONArray1.add(json1);
				}

				userPstmt.close();
				userRS.close();

				logger.debug("output from query"+userJSONArray);
				responseJSON.put("SERVICEPACKS", userJSONArray);
				responseJSON.put("MENULIST", userJSONArray1);
				logger.debug("output from query"+userJSONArray1);

				resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

				responseDTO.setData(resultMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing1.");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeResultSet(userRS);
				DBUtils.closePreparedStatement(userPstmt);

				resultMap = null;
				userJSONArray = null;
				userJSONArray1 = null;
				json1 = null;
				json = null;
			}
			return responseDTO;
		}
		
		public ResponseDTO fetchSpacks(RequestDTO requestDTO) {

			logger.debug("Inside Get Service Packs .. ");
			HashMap<String, Object> maap = null;
			JSONObject resultJson = null;
			JSONObject json = null;
			PreparedStatement pstmt = null;

			ResultSet rS = null;
			Connection connection = null;

			String Qry = "";
			try {

				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				maap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");

				Qry = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER";
				pstmt = connection.prepareStatement(Qry);
				rS = pstmt.executeQuery();

				json = new JSONObject();

				while (rS.next()) {
					json.put(rS.getString(1), rS.getString(2));
				}
				logger.debug("Service Packs from DB " + json);

				resultJson.put("SERVICELIST", json);
				maap.put("INSTDETAILS", resultJson);
				logger.debug("Service Map [" + maap + "]");
				responseDTO.setData(maap);

				pstmt.close();
				rS.close();
				json.clear();

			} catch (Exception ex) {
				logger.error("Error Occured..!" + ex.getLocalizedMessage());
			} finally {
				try {
					rS.close();
					pstmt.close();
					connection.close();
					json = null;
				} catch (Exception es) {
				}
			}
			return responseDTO;
		}
		

}
