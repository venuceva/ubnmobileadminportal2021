package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.bean.PayBillBean;
import com.ceva.base.common.dao.PayBillDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class PayBillDaoImpl implements PayBillDAO {

	private Logger logger = Logger.getLogger(PayBillDAO.class);

	private ResponseDTO responseDTO = null;
	private JSONObject requestJSON = null;

	public ResponseDTO getDashboard(RequestDTO requestDTO) {

		logger.debug("Inside Get MPesa DashBoard.. ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray jsonArray = null;

		PreparedStatement merchantPstmt = null;

		ResultSet merchantRS = null;
		Connection connection = null;
		String merchantQry = "";

		ArrayList<String> billerIdArray = null;

		JSONObject json = null;
		try {

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			jsonArray = new JSONArray();
			responseDTO = new ResponseDTO();
			billerIdArray = new ArrayList<String>();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection.equals(null) + "]");

			merchantQry = "SELECT MABT.ID,MABT.BILLER_TYPE_NAME,MABT.DESCRIPTION,MABT.CREATED_BY,"
					+ "to_char(MABT.DATE_CREATED,'DD-MM-YYYY HH24:MI:SS'),nvl(MABT.BFUB_CREDIT_ACCT,' '),"
					+ "nvl(MABT.BFUB_DEBIT_ACCT,' '),decode(MABT.STATUS,'A','Active','B','De-Active') "
					+ "from MPESA_ACCTMGT_BILLER_TYPE MABT ";

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();

			while (merchantRS.next()) {
				json.put("id", merchantRS.getString(1));
				json.put("billerType", merchantRS.getString(2));
				json.put("description", merchantRS.getString(3));
				json.put("createdBy", merchantRS.getString(4));
				json.put("createdDate", merchantRS.getString(5));
				json.put("bfubcract", merchantRS.getString(6));
				json.put("bfubdract", merchantRS.getString(7));
				json.put("status", merchantRS.getString(8));
				billerIdArray.add(merchantRS.getString(1) + "~"	+ merchantRS.getString(2));
				jsonArray.add(json);
				json.clear();
			}

			logger.debug("JSON Array [" + jsonArray + "]");
			resultJson.put(CevaCommonConstants.MERCHANT_DASHBOARD, jsonArray);

			merchantRS.close();
			merchantPstmt.close();

			jsonArray.clear();

			for (int i = 0; i < billerIdArray.size(); i++) {
				String storeQry = "Select ID,BILLER_ID,DESCRIPTION,CREATED_BY,to_char(DATE_CREATED,'DD-MM-YYYY HH24:MI:SS')"
						+ ",nvl(BFUB_CREDIT_ACCT,' '),nvl(BFUB_DEBIT_ACCT,' '),decode(STATUS,'A','Active','B','De-Active') "
						+ "from MPESA_ACCTMGT_BILLER_ID where BILLER_TYPE_ID=?";

				merchantPstmt = connection.prepareStatement(storeQry);
				merchantPstmt.setString(1, billerIdArray.get(i).split("~")[0]);
				merchantRS = merchantPstmt.executeQuery();
				json.clear();

				while (merchantRS.next()) {
					json.put("id", merchantRS.getString(1));
					json.put("billerId", merchantRS.getString(2));
					json.put("description", merchantRS.getString(3));
					json.put("createdby", merchantRS.getString(4));
					json.put("createddate", merchantRS.getString(5));
					json.put("bfubcract", merchantRS.getString(6));
					json.put("bfubdract", merchantRS.getString(7));
					json.put("status", merchantRS.getString(8));
					jsonArray.add(json);
					json.clear();
				}

				if (billerIdArray != null && billerIdArray.size() > 0) {
					resultJson.put(billerIdArray.get(i).split("~")[1]
							+ "_BILLER_IDS", jsonArray);
					jsonArray.clear();
				}

				merchantPstmt.close();
				merchantRS.close();
			}

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
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeConnection(connection);
			merchantMap = null;
			resultJson = null;
			jsonArray = null;
			merchantQry = null;
		}
		return responseDTO;
	}

	public ResponseDTO getBillerTypeDetails(RequestDTO requestDTO) {

		logger.debug("Inside Get Biller Type Details .. ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;

		PreparedStatement merchantPstmt = null;

		ResultSet merchantRS = null;
		Connection connection = null;

		String merchantQry = "";

		JSONObject json = null;
		try {

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			responseDTO = new ResponseDTO();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection.equals(null) + "]");

			merchantQry = "SELECT SI.INSTITUTION_ID,SI.INSTITUTION_NAME from SERVICES_INSTITUTIONS SI";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();

			while (merchantRS.next()) {
				json.put(merchantRS.getString(1), merchantRS.getString(2));
			}

			resultJson.put("institutesel", json);

			merchantPstmt.close();
			merchantRS.close();
			json.clear();

			/*merchantQry = "SELECT MO.OPERATORID,MO.OPERATORNAME from MN_OPERATORS MO";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				json.put(merchantRS.getString(1), merchantRS.getString(2));
			}

			resultJson.put("operatorsel", json);

			merchantPstmt.close();
			merchantRS.close();
			json.clear();*/
			resultJson.put("operatorsel", new StateDaoImpl().getStatesToSelectBox());
			merchantQry = "SELECT MABT.SYSTEM_MODE_PK,MABT.SYSTEM_MODE_DESCRIPTION from MPESA_ACCTMGT_SYSTEM_MODES MABT";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				json.put(merchantRS.getString(1), merchantRS.getString(2));
			}

			resultJson.put("systemmodessel", json);

			merchantMap.put(CevaCommonConstants.BILLER, resultJson);
			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  Get Biller Type Details ["
					+ e.getMessage() + "]");
		} catch (Exception e) {

			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in  Get Biller Type Details ["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeConnection(connection);
			merchantQry = null;
		}
		return responseDTO;
	}

	public ResponseDTO insertBillerType(RequestDTO requestDTO) {

		logger.debug("Inside InsertBillerType DAO ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject payBillBean = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			payBillBean = requestJSON.getJSONObject("payBillBean");
			billerDataMap = new HashMap<String, Object>();
			connection = connection ==null ? DBConnector.getConnection() : connection;

			callable = connection.prepareCall("{call MPESAPAYBILLPKG.INSERTBILLERTYPEDETAILS(?,?,?,?,?,?,?,?)}");
			callable.setString(1, payBillBean.getString("institute"));
			callable.setString(2, payBillBean.getString("operator"));
			callable.setString(3, payBillBean.getString("billerTypeDescription"));
			callable.setString(4, payBillBean.getString("fixedamountcheckval"));
			callable.setString(5, payBillBean.getString("amount"));
			callable.setString(6, payBillBean.getString("bfubCreditAccount"));
			callable.setString(7, requestJSON.getString("makerId"));
			callable.registerOutParameter(8, OracleTypes.VARCHAR);

			callable.execute();
			/*logger.debug("Connection is null [" + connection.equals(null) + "]");
			callable = connection.prepareCall("{call MPESAPAYBILLPKG.INSERTBILLERTYPEDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callable.setString(1, payBillBean.getString("institute"));
			callable.setString(2, payBillBean.getString("operator"));
			callable.setString(3, payBillBean.getString("billerType"));
			callable.setString(4, payBillBean.getString("billerTypeDescription"));
			callable.setString(5, payBillBean.getString("regex"));
			callable.setString(6, payBillBean.getString("fixedamountcheckval"));
			callable.setString(7, payBillBean.getString("amount"));
			callable.setString(8, payBillBean.getString("systemmodes"));
			callable.setString(9, payBillBean.getString("bfubCreditAccount"));
			callable.setString(10, payBillBean.getString("bfubDebitAccount"));
			callable.setString(11, payBillBean.getString("multiData"));
			callable.setString(12, requestJSON.getString("makerId"));
			callable.setString(13, payBillBean.getString("billerIdLen"));
			callable.registerOutParameter(14, OracleTypes.VARCHAR);

			callable.execute();*/

			if (callable.getString(8).indexOf("SUCCESS") == -1) {
				responseDTO.addError("Internal Error Occured.");
			} else {

			}
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in Insert BillerType [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Insert BillerType [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO updateBillerType(RequestDTO requestDTO) {

		logger.debug("Inside Update BillerType DAO ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject payBillBean = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			payBillBean = requestJSON.getJSONObject("payBillBean");
			billerDataMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection.equals(null) + "]");
			callable = connection
					.prepareCall("{call MPESAPAYBILLPKG.UPDATEBILLERTYPEDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callable.setString(1, payBillBean.getString("institute"));
			callable.setString(2, payBillBean.getString("operator"));
			callable.setString(3, payBillBean.getString("billerType"));
			callable.setString(4,
					payBillBean.getString("billerTypeDescription"));
			callable.setString(5, payBillBean.getString("regex"));
			callable.setString(6, payBillBean.getString("fixedamountcheckval"));
			callable.setString(7, payBillBean.getString("amount"));
			callable.setString(8, payBillBean.getString("systemmodes"));
			callable.setString(9, payBillBean.getString("bfubCreditAccount"));
			callable.setString(10, payBillBean.getString("bfubDebitAccount"));
			callable.setString(11, payBillBean.getString("multiData"));
			callable.setString(12, requestJSON.getString("makerId"));
			callable.setString(13, payBillBean.getString("billerIdLen"));
			callable.setString(14, payBillBean.getString("id"));
			callable.registerOutParameter(15, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(15).indexOf("SUCCESS") == -1) {
				responseDTO.addError(callable.getString(15));
			} else {

			}
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in Insert BillerType [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Insert BillerType [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}


	public ResponseDTO insertAddBillerID(RequestDTO requestDTO) {

		logger.debug("Inside Insert Add Biller ID...");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject payBillBean = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			payBillBean = requestJSON.getJSONObject("payBillBean");

			billerDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			callable = connection.prepareCall("{call MPESAPAYBILLPKG.INSERTADDBILLERID(?,?,?,?)}");

			callable.setString(1, payBillBean.getString("mulData"));
			callable.setString(2, payBillBean.getString("billerType"));
			callable.setString(3, requestJSON.getString("makerId"));
			callable.registerOutParameter(4, OracleTypes.VARCHAR);
			callable.execute();


			if (callable.getString(4).indexOf("SUCCESS") == -1){
				responseDTO.addError(callable.getString(4));
			}else{

			}
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in Insert BillerID [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Insert BillerID [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}
		return responseDTO;
	}

	public ResponseDTO billerTypeActDeact(RequestDTO requestDTO) {

		logger.debug("Inside Biller Type Act Deact DAO ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject payBillBean = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			payBillBean = requestJSON.getJSONObject("payBillBean");
			billerDataMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			callable = connection.prepareCall("{call MPESAPAYBILLPKG.UPDATEBILLERTYPESTATUS(?,?,?,?)}");
			callable.setString(1, payBillBean.getString("id"));
			callable.setString(2, payBillBean.getString("billerType"));
			callable.setString(3, requestJSON.getString("makerId"));
 			callable.registerOutParameter(4, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(4).indexOf("SUCCESS") == -1) {
				responseDTO.addError(callable.getString(4));
			} else {

			}
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in Insert BillerType [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Insert BillerType [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO listBillerType(RequestDTO requestDTO) {

		logger.debug("Inside Biller Type . ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		PayBillBean payBillBean = null;

		try {/*
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();

			payBillBean = new PayBillBean();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON().get("payBillBean");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			merchantQry = "select (select SI.INSTITUTION_NAME from SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = MABTT.INSTITUTE_ID)"
					+ " ,(select MN.OPERATORNAME from MN_OPERATORS MN where MN.OPERATORID = MABTT.OPERATOR_ID ) ,"
					+ "MABTT.BILLER_TYPE_NAME,MABTT.DESCRIPTION ,MABTT.REGEX ,MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,"
					+ "(select SYSTEM_MODE_DESCRIPTION from MPESA_ACCTMGT_SYSTEM_MODES where SYSTEM_MODE_PK=MABTT.SYSTEM_MODE_FK) ,"
					+ "MABTT.BFUB_CREDIT_ACCT ,MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
					+ "MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
					+ "MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID, decode(MABTT.STATUS,'A','Active','B','De-Active') "
					+ "from MPESA_ACCTMGT_BILLER_TYPE MABTT "
					+ "where  MABTT.ID=? and MABTT.BILLER_TYPE_NAME=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, resultJson.getString("id"));
			merchantPstmt.setString(2, resultJson.getString("billerType"));
			merchantRS = merchantPstmt.executeQuery();

			payBillBean = new PayBillBean();

			if (merchantRS.next()) {
				payBillBean.setInstitute(merchantRS.getString(1));
				payBillBean.setOperator(merchantRS.getString(2));
				payBillBean.setBillerType(merchantRS.getString(3));
				payBillBean.setBillerTypeDescription(merchantRS.getString(4));
				payBillBean.setRegex(merchantRS.getString(5));
				payBillBean.setFixedamountcheckval(merchantRS.getString(6));
				payBillBean.setAmount(merchantRS.getString(7));
				payBillBean.setSystemmodes(merchantRS.getString(8));
				payBillBean.setBfubCreditAccount(merchantRS.getString(9));
				payBillBean.setBfubDebitAccount(merchantRS.getString(10));
				payBillBean.setMakerId(merchantRS.getString(12));
				payBillBean.setMakerDttm(merchantRS.getString(11));
				payBillBean.setBillerIdLen(merchantRS.getString(13));
				payBillBean.setAuthId(merchantRS.getString(14));
				payBillBean.setAuthDttm(merchantRS.getString(15));
				payBillBean.setFixedamountcheckval(merchantRS.getString(16));
				payBillBean.setBilleridcheckval(merchantRS.getString(17));
				payBillBean.setStatus(merchantRS.getString(18));
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantQry = "select BILLER_ID,DESCRIPTION,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT from MPESA_ACCTMGT_BILLER_ID where  BILLER_TYPE_ID=?";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1, resultJson.getString("id"));

			merchantRS = merchantPstmt.executeQuery();
			String billerData = "";
			String eachrow = "";
			int i = 0;
			while (merchantRS.next()) {
				eachrow = merchantRS.getString(1) + ","	+ merchantRS.getString(2) + ","
						+ merchantRS.getString(3) + ","	+ merchantRS.getString(4);
				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			payBillBean.setMultiData(billerData);

			merchantMap.put("payBillData", payBillBean);

			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller Type [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		*/} catch (Exception e) {
			logger.debug("Exception in Biller Type [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			merchantQry = null;
		}

		return responseDTO;

	}

	public ResponseDTO modifyListBillerType(RequestDTO requestDTO) {

		logger.debug("Inside Modify Biller Type . ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		PayBillBean payBillBean = null;

		try {/*
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			// resultJson = new JSONObject();
			payBillBean = new PayBillBean();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON().get(
					"payBillBean");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			merchantQry = "select  MABTT.INSTITUTE_ID , MABTT.OPERATOR_ID  , MABTT.BILLER_TYPE_NAME ,MABTT.DESCRIPTION ,MABTT.REGEX ,"
					+ "MABTT.HAS_FIXED_AMOUNT , MABTT.FIXED_AMOUNT ,  MABTT.SYSTEM_MODE_FK  , MABTT.BFUB_CREDIT_ACCT ,"
					+ "MABTT.BFUB_DEBIT_ACCT , to_char(MABTT.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),"
					+ "MABTT.CREATED_BY, to_char(MABTT.BILLER_ID_LENGTH) ,MABTT.AUTHID, to_char(MABTT.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),"
					+ "MABTT.HAS_FIXED_AMOUNT, MABTT.HAS_BILLER_ID, decode(MABTT.STATUS,'A','Active','B','De-Active') "
					+ "from MPESA_ACCTMGT_BILLER_TYPE MABTT "
					+ "where  MABTT.ID=? and MABTT.BILLER_TYPE_NAME=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, resultJson.getString("id"));
			merchantPstmt.setString(2, resultJson.getString("billerType"));
			merchantRS = merchantPstmt.executeQuery();

			payBillBean = new PayBillBean();

			if (merchantRS.next()) {
				payBillBean.setInstitute(merchantRS.getString(1));
				payBillBean.setOperator(merchantRS.getString(2));
				payBillBean.setBillerType(merchantRS.getString(3));
				payBillBean.setBillerTypeDescription(merchantRS.getString(4));
				payBillBean.setRegex(merchantRS.getString(5));
				payBillBean.setFixedamountcheckval(merchantRS.getString(6));
				payBillBean.setAmount(merchantRS.getString(7));
				payBillBean.setSystemmodes(merchantRS.getString(8));
				payBillBean.setBfubCreditAccount(merchantRS.getString(9));
				payBillBean.setBfubDebitAccount(merchantRS.getString(10));
				payBillBean.setMakerId(merchantRS.getString(12));
				payBillBean.setMakerDttm(merchantRS.getString(11));
				payBillBean.setBillerIdLen(merchantRS.getString(13));
				payBillBean.setAuthId(merchantRS.getString(14));
				payBillBean.setAuthDttm(merchantRS.getString(15));
				payBillBean.setFixedamountcheckval(merchantRS.getString(16));
				payBillBean.setBilleridcheckval(merchantRS.getString(17));
				payBillBean.setStatus(merchantRS.getString(18));
				payBillBean.setId(resultJson.getString("id"));
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("payBillData", payBillBean);

			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Biller Type [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		*/} catch (Exception e) {
			logger.debug("Exception in Biller Type [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			merchantQry = null;
		}

		return responseDTO;

	}


	public ResponseDTO getBillerIDDetails(RequestDTO requestDTO) {

		logger.debug("Inside Get Biller ID Details .. ");
		HashMap<String, Object> merchantMap = null;
		JSONObject requestJSON = null;

		PreparedStatement merchantPstmt = null;

		ResultSet merchantRS = null;
		Connection connection = null;

		String merchantQry = "";

		PayBillBean payBillBean = null;

		JSONObject payBillBean1 = null;

		try {/*

			merchantMap = new HashMap<String, Object>();
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();

			payBillBean1 = requestJSON.getJSONObject("payBillBean");

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			merchantQry = "select  BILLER_ID,BILLER_TYPE_ID,DESCRIPTION,CREATED_BY,BFUB_CREDIT_ACCT,BFUB_DEBIT_ACCT from MPESA_ACCTMGT_BILLER_ID where BILLER_ID=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, payBillBean1.getString("billerId"));

			merchantRS = merchantPstmt.executeQuery();

			payBillBean = new PayBillBean();

			if (merchantRS.next()) {

				payBillBean.setBillerId(merchantRS.getString(1));
				payBillBean.setBillerIdType(merchantRS.getString(2));
				payBillBean.setBillerIdDescription(merchantRS.getString(3));
				payBillBean.setAuthId(merchantRS.getString(4));
				payBillBean.setBfubCreditAccount(merchantRS.getString(5));
				payBillBean.setBfubDebitAccount(merchantRS.getString(6));

			}

			merchantPstmt.close();
			merchantRS.close();

			//BILLER_TYPE_ID
			//String ses=payBillBean1.getString("billerIdType");

			merchantQry = "select  BILLER_TYPE_NAME  from  MPESA_ACCTMGT_BILLER_TYPE where ID=?";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1,payBillBean.getBillerIdType());

			merchantRS = merchantPstmt.executeQuery();

			if(merchantRS.next()) {

				payBillBean.setBillerIdName(merchantRS.getString(1));

			}

			logger.debug("First String"+merchantRS.getString(1));

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("modifyPayBillerIDData", payBillBean);

			logger.debug("MerchantMap [" + merchantMap + "]");

			responseDTO.setData(merchantMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		*/} catch (Exception e) {

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



public ResponseDTO updateBillerID(RequestDTO requestDTO) {

	logger.debug("Inside Update Biller ID ... ");

	HashMap<String, Object> billerIdDataMap = null;
	Connection connection = null;
	CallableStatement callable = null;

	JSONObject payBillBean = null;

	try {
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");

		payBillBean = requestJSON.getJSONObject("payBillBean");

		billerIdDataMap = new HashMap<String, Object>();
		connection = DBConnector.getConnection();
		logger.debug("Connection is null [" + connection == null + "]");
		callable = connection.prepareCall("{call MPESAPAYBILLPKG.UPDATEBILLERIDDETAILS(?,?,?,?,?,?,?)}");

		callable.setString(1, payBillBean.getString("billerId"));
		callable.setString(2, payBillBean.getString("billerIdType"));
		callable.setString(3, payBillBean.getString("billerIdDescription"));
		callable.setString(4, payBillBean.getString("bfubCreditAccount"));
		callable.setString(5, payBillBean.getString("bfubDebitAccount"));
		callable.setString(6, requestJSON.getString("makerId"));
		callable.registerOutParameter(7, OracleTypes.VARCHAR);

		callable.execute();

		if (callable.getString(7).indexOf("SUCCESS") == -1) {
			responseDTO.addError(callable.getString(7));
		} else {

		}
		responseDTO.setData(billerIdDataMap);

	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");

		logger.debug("SQLException in Update Biller ID [" + e.getMessage()
				+ "]");
		e.printStackTrace();
	} catch (Exception e) {
		responseDTO.addError("Internal Error Occured While Executing.");

		logger.debug("Exception in Update Biller ID [" + e.getMessage()
				+ "]");
	} finally {
		DBUtils.closeCallableStatement(callable);
		DBUtils.closeConnection(connection);
		billerIdDataMap = null;
	}

	 return responseDTO;
  }


public ResponseDTO modifyBillerIDDetails(RequestDTO requestDTO) {

	logger.debug("Inside Modify  Biller ID Details .. ");
	HashMap<String, Object> merchantMap = null;
	JSONObject requestJSON = null;

	PreparedStatement merchantPstmt = null;

	ResultSet merchantRS = null;
	Connection connection = null;

	String merchantQry = "";

	PayBillBean payBillBean = null;

	JSONObject payBillBean1 = null;

	try {

	} catch (Exception e) {

		responseDTO.addError("Internal Error Occured While Executing.");
		logger.debug("Exception in  Modify Biller ID Details ["
				+ e.getMessage() + "]");
	} finally {
		DBUtils.closeResultSet(merchantRS);
		DBUtils.closePreparedStatement(merchantPstmt);
		DBUtils.closeConnection(connection);
		merchantQry = null;
	}
	return responseDTO;
}



public ResponseDTO billerIDStatusAck(RequestDTO requestDTO) {

	logger.debug("Inside Biller Type Act Deact DAO ... ");

	HashMap<String, Object> billerDataMap = null;
	Connection connection = null;
	CallableStatement callable = null;

	JSONObject payBillBean = null;

	try {
		responseDTO = new ResponseDTO();
		requestJSON = requestDTO.getRequestJSON();
		logger.debug("Request JSON [" + requestJSON + "]");

		payBillBean = requestJSON.getJSONObject("payBillBean");
		billerDataMap = new HashMap<String, Object>();
		connection = DBConnector.getConnection();
		logger.debug("Connection is null [" + connection == null + "]");
		callable = connection.prepareCall("{call MPESAPAYBILLPKG.UPDATEMODIFYBILLERIDSTATUS(?,?,?,?,?,?,?,?)}");

		callable.setString(1, payBillBean.getString("billerId"));
		callable.setString(2, payBillBean.getString("billerIdType"));
		callable.setString(3, payBillBean.getString("billerIdDescription"));
		callable.setString(4, payBillBean.getString("bfubCreditAccount"));
		callable.setString(5, payBillBean.getString("bfubDebitAccount"));
		callable.setString(6, payBillBean.getString("id"));
		callable.setString(7, requestJSON.getString("makerId"));
		callable.registerOutParameter(8, OracleTypes.VARCHAR);

		callable.execute();

		if (callable.getString(8).indexOf("SUCCESS") == -1) {
			responseDTO.addError(callable.getString(8));
		} else {

		}
		responseDTO.setData(billerDataMap);


	} catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing.");

		logger.debug("SQLException in Insert BillerType [" + e.getMessage()
				+ "]");
	} catch (Exception e) {
		responseDTO.addError("Internal Error Occured While Executing.");

		logger.debug("Exception in Insert BillerType [" + e.getMessage()
				+ "]");
	} finally {
		DBUtils.closeCallableStatement(callable);
		DBUtils.closeConnection(connection);
		billerDataMap = null;
	}

	return responseDTO;
}

@Override
public ResponseDTO getBillerChannel(String billerId) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO getChannelBillers(String ChannelId) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO getChannelById(String id) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuilder qry = new StringBuilder();
	JSONObject respJSON = new JSONObject();
	HashMap<String, Object> data = new HashMap<String, Object>();
	logger.info("id..:"+id);
	try{
		qry.append("SELECT SI.INSTITUTION_ID,SI.INSTITUTION_NAME from SERVICES_INSTITUTIONS SI 	WHERE 1=1 ");
		if(!"".equals(id) && id != null ){
			qry.append(" AND INSTITUTION_ID = '"+id+"'");
		}
		connection = connection == null ? DBConnector.getConnection() : connection;
		pstmt = connection.prepareStatement(qry.toString());
		rs=pstmt.executeQuery();
		respJSON.put("institutesel", ResultSetConvertor.convertResultSetToSelectBox(rs));
		logger.info(respJSON);
		data.put(CevaCommonConstants.BILLER, respJSON);
		responseDTO.setData(data);
		//connection.close();


	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		respJSON = null;
		data = null;
	}
	return responseDTO;
}

@Override
public ResponseDTO getBillerById(String id) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO insertBillerChannelMap(RequestDTO dto) {
	ResponseDTO responseDTO = new ResponseDTO();
	Connection connection= null;
	CallableStatement callable = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	PayBillBean payBillBean = null;
	JSONObject requestJSON =null;
	try{
		payBillBean = (PayBillBean) dto.getBean();
		logger.info(payBillBean.toString());
		requestJSON = dto.getRequestJSON();
		connection = connection == null ? DBConnector.getConnection() : connection;
		callable = connection.prepareCall("{call MPESAPAYBILLPKG.INSERTBILLERTYPEDETAILS(?,?,?,?,?,?,?,?)}");
		callable.setString(1, payBillBean.getInstitute());
		callable.setString(2, payBillBean.getOperator());
		callable.setString(3, payBillBean.getBillerTypeDescription());
		callable.setString(4, payBillBean.getFixedamountcheckval());
		callable.setString(5, payBillBean.getAmount());
		callable.setString(6, payBillBean.getBfubCreditAccount());
		callable.setString(7, requestJSON.getString("makerId"));
		callable.registerOutParameter(8, OracleTypes.VARCHAR);
		callable.execute();
		String result = callable.getString(8);
		logger.info("result..:"+result);
		if (!"SUCCESS".equals(result)) {
			responseDTO.addError(result);
		} else {
			logger.info("biller category mapp completed successfully..");
		}

	}catch (SQLException e) {
		responseDTO.addError("Internal Error Occured While Executing."+e.getMessage());

		logger.debug("SQLException in Insert BillerType [" + e.getMessage()
				+ "]");
	} catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closeCallableStatement(callable);
		DBUtils.closeConnection(connection);
		processor= null;
		payBillBean = null;
		requestJSON = null;
	}
	return responseDTO;
}

@Override
public ResponseDTO insertBiller(RequestDTO dto) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO authorizeBiller(RequestDTO dto) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO updateBiller(RequestDTO dto) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO deleteBiller(RequestDTO dto) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BeanProcessor processor = null;
	try{



	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		processor= null;
	}
	return responseDTO;
}

@Override
public ResponseDTO getChannels() {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuilder qry = new StringBuilder();
	JSONObject respJSON = new JSONObject();
	List<Channels> channels=null;
	HashMap<String, Object> data = new HashMap<String, Object>();
	try{
		qry.append("SELECT SI.INSTITUTION_ID as instituteId,SI.INSTITUTION_NAME as instituteName, SI.INSTITUTION_DESCRIPTION as instituteDescription from SERVICES_INSTITUTIONS SI 	WHERE 1=1 ");

		connection = connection == null ? DBConnector.getConnection() : connection;
		pstmt = connection.prepareStatement(qry.toString());
		rs=pstmt.executeQuery();
		channels= new BeanProcessor().toBeanList(rs, Channels.class);
		data.put(CevaCommonConstants.OBJECT, channels);
		responseDTO.setData(data);
		//connection.close();

	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		respJSON = null;
		data = null;
	}
	return responseDTO;
}

@Override
public ResponseDTO getChannelServices(String id) {
	responseDTO = new ResponseDTO();
	Connection connection= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuilder qry = new StringBuilder();
	List<PayBillBean> payBillBeans=null;
	HashMap<String, Object> data = new HashMap<String, Object>();
	try{
		qry.append("SELECT BCM.INSTID_ID as institute, (SELECT SI.INSTITUTION_NAME FROM SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = BCM.INSTID_ID) AS instituteText, BCM.OPERATOR as operator, (SELECT BC.CAT_NAME FROM BILLER_CATEGORY BC WHERE BC.CAT_ID=BCM.OPERATOR)  as operatorText, BCM.FIXED_PERCENT AS fixedamountcheckval, BCM.AMOUNT amount, BCM.ACCOUNT AS bfubCreditAccount, BCM.STATUS AS status, BCM.BILLER_DESC AS billerTypeDescription, BCM.MAKER AS maker FROM BILLER_CHANNEL_MAPPING BCM WHERE BCM.INSTID_ID=?");

		connection = connection == null ? DBConnector.getConnection() : connection;
		pstmt = connection.prepareStatement(qry.toString());
		pstmt.setString(1, id);
		rs=pstmt.executeQuery();
		payBillBeans= new BeanProcessor().toBeanList(rs, PayBillBean.class);
		data.put(CevaCommonConstants.OBJECT, payBillBeans);
		logger.info(data.toString());
		responseDTO.setData(data);

	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error..:"+e.getLocalizedMessage());
		responseDTO.addError("Error..:"+e.getMessage());
	}finally{
		DBUtils.closeResultSet(rs);
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		data = null;
	}
	return responseDTO;
}

@Override
public PayBillBean get(PayBillBean bean) {
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	logger.info(bean.toString());
	String sql = prepareQuery(bean);
	logger.info(sql);
	BeanProcessor processor = null;
	try {
		connection = connection == null ? connection = DBConnector
				.getConnection() : connection;
		pstmt = connection.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if (rs != null && rs.next()) {
			processor = new BeanProcessor();
			bean = processor.toBean(rs, PayBillBean.class);
		}
		if (bean != null) {
			logger.info(bean.toString());
		}

	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error" + e.getLocalizedMessage());
	} finally {
		DBUtils.closeResultSet(rs);
		DBUtils.closePreparedStatement(pstmt);
		DBUtils.closeConnection(connection);
		sql = null;
		processor = null;
	}
	return bean;
}

private String prepareQuery(PayBillBean bean){
	StringBuilder sql =null;

	try {
			 sql = new StringBuilder(qry);

		if (bean.getId() != null && bean.getId().length()>0) {
			sql.append(" AND BCMT.ID = '" + bean.getId()+ "'");
		}
		if (bean.getBfubCreditAccount() != null && bean.getBfubCreditAccount().length() > 0) {
			sql.append(" AND BCMT.ACCOUNT = '" + bean.getBfubCreditAccount() + "'");
		}
		if (bean.getRefNum() != null && bean.getRefNum().length() > 0) {
			sql.append(" AND BCMT.REF_NUM = '" + bean.getRefNum() + "'");
		}

	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error" + e.getLocalizedMessage());
	}finally{
		bean = null;
	}
	return sql.toString();
}

}
