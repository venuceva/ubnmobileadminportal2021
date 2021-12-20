package com.ceva.base.common.dao;

import java.io.Console;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.util.DBUtils;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;

public class CallCenterDAO {

	Logger logger=Logger.getLogger(CallCenterDAO.class);

	ResponseDTO responseDTO=null;
	JSONObject requestJSON=null;
	JSONObject responseJSON=null;
	Connection connection=null;

	public ResponseDTO customerBalanceInfo(RequestDTO requestDTO) {

		logger.debug("[CallCenterDAO][customerBalanceInfo]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		HashMap<String, Object> cardbalDataMap = null;
		JSONObject resultJson = new JSONObject();
		JSONArray balJSONArray = new JSONArray();

		PreparedStatement  balpsmt=null;
		ResultSet crdbalRS =null;
		String entityQry=null;
		JSONObject json = null;
		try{
		cardbalDataMap = new HashMap<String, Object>();
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");
			if("ANUM".equalsIgnoreCase(radioselect))
			{
					logger.debug("Inside [AccountNumber]");
				 entityQry = "select CN.CARD_NO,decode(CN.CARD_STATUS, 'I','Active','B','Blocked','L','Locked','Inactive',CN.CARD_STATUS),to_char(b.CARD_ACCT_BAL,'99999.99')"
						+ " from CRD_MASTER CN,ACCT_MASTER b,CRD_ACCT_LINK CL "
						+ "where CL.CIN=CN.CIN AND CL.CARD_NO=CN.CARD_NO AND b.CARD_ACCT=CL.CARD_ACCT AND CL.CARD_ACCT=?";


			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
					logger.debug("Inside[CardNumber]");
				 entityQry = "select CN.CARD_NO,decode(CN.CARD_STATUS, 'I','Active','B','Blocked','L','Locked','Inactive',CN.CARD_STATUS),b.CARD_ACCT_BAL"
						+ " from CRD_MASTER CN,ACCT_MASTER b,CRD_ACCT_LINK CL "
						+ "where CL.CIN=CN.CIN AND CL.CARD_NO=CN.CARD_NO AND b.CARD_ACCT=CL.CARD_ACCT AND CN.CARD_NO=?";

			}
			connection = DBConnector.getConnection();
			logger.debug("Connection [" + connection + "]");
			balpsmt = connection.prepareStatement(entityQry);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, cardNumber);
			}
			//balpsmt.setString(1, cardNumber);

			crdbalRS = balpsmt.executeQuery();

			while (crdbalRS.next()) {
				json = new JSONObject();
				json.put("cardNumber", crdbalRS.getString(1));
				json.put("cardstatus", crdbalRS.getString(2));
				json.put("customerBalance", crdbalRS.getString(3));

			}
			resultJson.put("CUSTOMER_BALANCE_INFO", json);
			cardbalDataMap.put("CUSTOMER_BALANCE_INFO", resultJson);
			responseDTO.setData(cardbalDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException in GetUserGroupDetails ["+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		}catch (Exception e) {
			logger.debug("Exception in GetUserGroupDetails  [" + e.getMessage()+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		}
		finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(crdbalRS);
			json=null;
			entityQry=null;
			crdbalRS =null;
		}
		return responseDTO;
	}

	public ResponseDTO custAccountSummary(RequestDTO requestDTO) {

		logger.debug("[CallCenterDAO][custAccountSummary]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();


		HashMap<String, Object> accountsummaryMap =null;
		JSONObject resultJson = new JSONObject();

		JSONArray balJSONArray = null;
		PreparedStatement  acsmpsmt=null;
		ResultSet acsmRS =null;
		String entityQry = null;
		JSONObject json = null;
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");
		try
		{

		if("ANUM".equalsIgnoreCase(radioselect))
		{
				logger.debug("Inside [AccountNumber]");
				 entityQry = "select AM.CARD_ACCT, CUST.F_NAME||' '||CUST.L_NAME ,CM.BR_ID,'Prepaid',AM.CARD_ACCT_BAL,decode(AM.CARD_ACCT_STATUS,'O','Active','I','Active','B','Blocked','L','Locked',AM.CARD_ACCT_STATUS)"+
						"from ACCT_MASTER AM ,CRD_MASTER CM,CUST_MASTER CUST ,CRD_ACCT_LINK CAL WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and CM.CIN = CUST.CIN and CAL.CARD_ACCT = ?";


		}else if("CNUM".equalsIgnoreCase(radioselect))
		{
				logger.debug("Inside[CardNumber]");
				 entityQry = "select AM.CARD_ACCT, CUST.F_NAME||' '||CUST.L_NAME ,CM.BR_ID,'Prepaid',AM.CARD_ACCT_BAL,decode(AM.CARD_ACCT_STATUS,'O','Active','I','Active','B','Blocked','L','Locked',AM.CARD_ACCT_STATUS)"+
						"from ACCT_MASTER AM ,CRD_MASTER CM,CUST_MASTER CUST ,CRD_ACCT_LINK CAL WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and CM.CIN = CUST.CIN and CM.CARD_NO = ?";


		}

		 accountsummaryMap = new HashMap<String, Object>();
		 balJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("[CallCenterDAO][custAccountSummary]connection::["+connection+"]");
			acsmpsmt = connection.prepareStatement(entityQry);
			//acsmpsmt.setString(1, cardNumber);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				acsmpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				acsmpsmt.setString(1, cardNumber);
			}

			acsmRS = acsmpsmt.executeQuery();

			while (acsmRS.next()) {
				json = new JSONObject();
				json.put("cardaccnumber", acsmRS.getString(1));
				json.put("accountName", acsmRS.getString(2));
				json.put("branchID", acsmRS.getString(3));
				json.put("accountType", acsmRS.getString(4));
				json.put("accountbalance", acsmRS.getString(5));
				json.put("accountStatus", acsmRS.getString(6));
				balJSONArray.add(json);
			}
			logger.debug("[CallCenterDAO][custAccountSummary]balJSONArray::["+balJSONArray+"]");
			resultJson.put("ACCOUNT_SUMMARY_INFO", balJSONArray);
			accountsummaryMap.put("ACCOUNT_SUMMARY_INFO", resultJson);
			responseDTO.setData(accountsummaryMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(acsmpsmt);
			DBUtils.closeResultSet(acsmRS);
			balJSONArray=null;
			json=null;
			balJSONArray=null;
			 entityQry = null;

		}
		return responseDTO;
	}



	public ResponseDTO miniStatementDetails(RequestDTO requestDTO) {

		logger.debug("[CallCenterDAO][miniStatementDetails]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		HashMap<String, Object> accountsummaryMap = null;

		JSONObject resultJson = null;
		JSONArray balJSONArray = null;

		PreparedStatement  acsmpsmt=null;

		ResultSet acsmRS =null;
		String entityQry = null;
		try{
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");

		logger.debug("[CallCenterDAO][miniStatementDetails]::::["+cardNumber+"]");

		if("ANUM".equalsIgnoreCase(radioselect))
		{
				logger.debug("Inside [AccountNumber]");
				entityQry = "select AM.CARD_ACCT, CUST.F_NAME||' '||CUST.L_NAME ,CM.BR_ID,CM.CARD_NO,'Prepaid',AM.CARD_ACCT_BAL,decode(AM.CARD_ACCT_STATUS,'O','Active','I','Active','B','Blocked','L','Locked',AM.CARD_ACCT_STATUS)"+
						"from ACCT_MASTER AM ,CRD_MASTER CM,CUST_MASTER CUST ,CRD_ACCT_LINK CAL WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and CM.CIN = CUST.CIN and CAL.CARD_ACCT = ?";


		}else if("CNUM".equalsIgnoreCase(radioselect))
		{
				logger.debug("Inside[CardNumber]");
				entityQry = "select AM.CARD_ACCT, CUST.F_NAME||' '||CUST.L_NAME ,CM.BR_ID,CM.CARD_NO,'Prepaid',AM.CARD_ACCT_BAL,decode(AM.CARD_ACCT_STATUS,'O','Active','I','Active','B','Blocked','L','Locked',AM.CARD_ACCT_STATUS)"+
						"from ACCT_MASTER AM ,CRD_MASTER CM,CUST_MASTER CUST ,CRD_ACCT_LINK CAL WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and CM.CIN = CUST.CIN and CM.CARD_NO = ?";


		}
		/*String entityQry = "select AM.CARD_ACCT, CUST.F_NAME||' '||CUST.L_NAME ,CM.BR_ID,CM.CARD_NO,'Prepaid',AM.CARD_ACCT_BAL,decode(AM.CARD_ACCT_STATUS,'I','Active','B','Blocked','L','Locked',AM.CARD_ACCT_STATUS)"+
"from ACCT_MASTER AM ,CRD_MASTER CM,CUST_MASTER CUST ,CRD_ACCT_LINK CAL WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and CM.CIN = CUST.CIN and CM.CARD_NO = ?";*/

			resultJson = new JSONObject();
			 balJSONArray = new JSONArray();
			 accountsummaryMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("[CallCenterDAO][miniStatementDetails]::::[connection:"+connection+"]");
			acsmpsmt = connection.prepareStatement(entityQry);
			//acsmpsmt.setString(1, cardNumber);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				acsmpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				acsmpsmt.setString(1, cardNumber);
			}


			acsmRS = acsmpsmt.executeQuery();
			JSONObject json = null;
			while (acsmRS.next()) {
				json = new JSONObject();
				json.put("cardaccnumber", acsmRS.getString(1));
				json.put("accountName", acsmRS.getString(2));
				json.put("branchID", acsmRS.getString(3));
				json.put("cardNumber", acsmRS.getString(4));
				json.put("accountType", acsmRS.getString(5));
				json.put("accountbalance", acsmRS.getString(6));
				json.put("accountStatus", acsmRS.getString(7));
				balJSONArray.add(json);


			}
			logger.debug("[CallCenterDAO][miniStatementDetails]balJSONArray::["+balJSONArray+"]");

			resultJson.put("MINISTATEMENT_INFO", balJSONArray);
			accountsummaryMap.put("MINISTATEMENT_INFO", resultJson);
			responseDTO.setData(accountsummaryMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(acsmpsmt);
			DBUtils.closeResultSet(acsmRS);
			 entityQry = null;
			 balJSONArray = null;
			 resultJson = null;
			 accountsummaryMap = null;
		}
		return responseDTO;
	}



	public ResponseDTO fetchtransactioninfo(RequestDTO requestDTO) {

		logger.debug("[CallCenterDAO][fetchtransactioninfo]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		HashMap<String, Object> accountsummaryMap = null;
		JSONObject resultJson = null;
		JSONArray ministateJSONArray = null;

		PreparedStatement  minstmpsmt=null;

		ResultSet minstRS =null;
		String entityQry="";
		JSONObject json = null;
		try {
		String accountNumber1=requestJSON.getString("accountNumber1");
		String cardNumber=requestJSON.getString("cardNumber");


		logger.debug(" [connection:::" + connection + "]");
		 entityQry = "select to_char(cts.maker_dttm,'DD-Mon-YYYY HH24:MI:SS'),(select txn_name from trans_master where txn_code=cts.txn_code),(select crcy_code from currency_master"
				+ " where crcy_code=cts.card_acct_ccy or num_crcy_code=cts.card_acct_ccy),"
				+ " decode(cts.drcr_flag,'D',to_char(cts.card_acct_amount,'999999999999.99'),'X'),"
				+ "	decode(cts.drcr_flag,'C',to_char(cts.card_acct_amount,'999999999999.99'),'X')"
				+ " from   CRD_ACCT_TRANS cts, ACCT_MASTER am , CRD_ACCT_LINK link where  cts.card_no=? and"
				+ " cts.card_acct=? and"
				+ " am.card_acct=cts.card_acct and"
				+ "	am.card_acct=link.card_acct and"
				+ "	link.card_no=cts.card_no  and"
				+ " rownum<11  order by cts.maker_dttm desc,cts.txn_order asc";

			resultJson = new JSONObject();
			ministateJSONArray = new JSONArray();
			 accountsummaryMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			minstmpsmt = connection.prepareStatement(entityQry);
			minstmpsmt.setString(1, cardNumber);
			minstmpsmt.setString(2, accountNumber1);

			minstRS = minstmpsmt.executeQuery();

			String credit="";
			String debit="";

			while (minstRS.next()) {
				json = new JSONObject();
				json.put("transactionDate", minstRS.getString(1));
				json.put("trnsDes", minstRS.getString(2));
				json.put("currency", minstRS.getString(3));

				debit = minstRS.getString(4);
				credit= minstRS.getString(5);
			    if(".00".equalsIgnoreCase(debit.trim()) || "0".equalsIgnoreCase(debit) || "0.00".equalsIgnoreCase(debit))
			    {
			     debit = "";

			    }
			    else
			    if(".00".equalsIgnoreCase(credit.trim()) || "0".equalsIgnoreCase(credit) || "0.00".equalsIgnoreCase(credit))
			    {
			     credit = "";
			    }
			    json.put("debit", debit);
			    json.put("credit", credit);

			    ministateJSONArray.add(json);
			   }

				logger.debug("[CallCenterDAO][fetchtransactioninfo]balJSONArray::["+ministateJSONArray+"]");

			resultJson.put("MINISTATEMENT_TRANS_INFO", ministateJSONArray);
			accountsummaryMap.put("MINISTATEMENT_TRANS_INFO", resultJson);
			responseDTO.setData(accountsummaryMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(minstmpsmt);
			DBUtils.closeResultSet(minstRS);
			 json = null;
			 ministateJSONArray = null;
			 resultJson = null;

		}
		return responseDTO;
	}



	public ResponseDTO statementofAccount(RequestDTO requestDTO) {

		logger.debug("Inside [CallCenterDAO][statementofAccount]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		HashMap<String, Object> stateaccMap = null;
		JSONObject resultJson = null;
		JSONArray ministateJSONArray = null;

		PreparedStatement  stataccmpsmt=null;

		ResultSet stateaccRS =null;
		String entityQry="";
		JSONObject json = null;
		try {
		String accountNumber1=requestJSON.getString("accountNumber1");
		String cardNumber=requestJSON.getString("cardNumber");

		 entityQry = "select to_char(cts.maker_dttm,'DD-Mon-YYYY HH24:MI:SS'),(select txn_name from trans_master where txn_code=cts.txn_code),(select crcy_code from currency_master"
				+ " where crcy_code=cts.card_acct_ccy or num_crcy_code=cts.card_acct_ccy),"
				+ " decode(cts.drcr_flag,'D',to_char(cts.card_acct_amount,'999999999999.99'),'X'),"
				+ "	decode(cts.drcr_flag,'C',to_char(cts.card_acct_amount,'999999999999.99'),'X')"
				+ " from   CRD_ACCT_TRANS cts, ACCT_MASTER am , CRD_ACCT_LINK link where  cts.card_no=? and"
				+ " cts.card_acct=? and"
				+ " am.card_acct=cts.card_acct and"
				+ "	am.card_acct=link.card_acct and"
				+ "	link.card_no=cts.card_no  and"
				+ " rownum<30  order by cts.maker_dttm desc,cts.txn_order asc";

		 	stateaccMap = new HashMap<String, Object>();
		 	resultJson = new JSONObject();
		 	ministateJSONArray = new JSONArray();
			connection = DBConnector.getConnection();
			logger.debug("Inside [CallCenterDAO][statementofAccount][connection:"+connection+"]");
			stataccmpsmt = connection.prepareStatement(entityQry);
			stataccmpsmt.setString(1, cardNumber);
			stataccmpsmt.setString(2, accountNumber1);

			stateaccRS = stataccmpsmt.executeQuery();

			while (stateaccRS.next()) {
				json = new JSONObject();
				String credit="";
				String debit="";
				json.put("transactionDate", stateaccRS.getString(1));
				json.put("trnsDes", stateaccRS.getString(2));
				json.put("currency", stateaccRS.getString(3));
				debit = stateaccRS.getString(4);
				credit= stateaccRS.getString(5);
			    if(".00".equalsIgnoreCase(debit.trim()) || "0".equalsIgnoreCase(debit) || "0.00".equalsIgnoreCase(debit))
			    {
			     debit = " ";

			    }
			    else
			    if(".00".equalsIgnoreCase(credit.trim()) || "0".equalsIgnoreCase(credit) || "0.00".equalsIgnoreCase(credit))
			    {
			     credit = " ";
			    }
			    json.put("debit", debit);
			    json.put("credit", credit);

			    ministateJSONArray.add(json);
			   }

				logger.debug("[CallCenterDAO][statementofAccount][ministateJSONArray:"+ministateJSONArray+"]");

			resultJson.put("STATEMENTOF_ACCOUT", ministateJSONArray);
			stateaccMap.put("STATEMENTOF_ACCOUT", resultJson);
			responseDTO.setData(stateaccMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(stataccmpsmt);
			DBUtils.closeResultSet(stateaccRS);
			stateaccMap = null;
			resultJson = null;
			ministateJSONArray = null;
			entityQry=null;
		}
		return responseDTO;
	}

	public ResponseDTO fetchAuditTrailDetails (RequestDTO requestDTO) {

		logger.debug("[CallCenterDAO][fetchAuditTrailDetails]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		HashMap<String, Object> cardbalDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		JSONArray balJSONArray = new JSONArray();
		PreparedStatement  balpsmt=null;
		ResultSet crdbalRS =null;
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");
		String entityQry=null;

		logger.debug("[CallCenterDAO][fetchAuditTrailDetails]::::["+cardNumber+"]");
		if("ANUM".equalsIgnoreCase(radioselect))
		{
				entityQry = "select   TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID from AUDIT_TRAIL where CARD_NO=?";


		}else if("CNUM".equalsIgnoreCase(radioselect))
		{
				entityQry = "select   TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID from AUDIT_TRAIL where CARD_NO=?";


		}
		try {
			connection = DBConnector.getConnection();
			logger.debug("[CallCenterDAO][fetchAuditTrailDetails]::::[connection:"+connection+"]");
			balpsmt = connection.prepareStatement(entityQry);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, cardNumber);
			}

			crdbalRS = balpsmt.executeQuery();
			JSONObject json = null;
			while (crdbalRS.next()) {
				json = new JSONObject();
				json.put("transCode", crdbalRS.getString(1));
				json.put("dateTime", crdbalRS.getString(2));
				json.put("netID", crdbalRS.getString(3));
				json.put("channelID", crdbalRS.getString(4));
				balJSONArray.add(json);
			}
			resultJson.put("AUDIT_TRAIL_INFO", balJSONArray);
			logger.debug("[CallCenterDAO][fetchAuditTrailDetails]::::["+balJSONArray+"]");
			cardbalDataMap.put("AUDIT_TRAIL_INFO_DATA", resultJson);
			responseDTO.setData(cardbalDataMap);
		} catch (SQLException e) {

			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(crdbalRS);
		}

     return responseDTO;
	}

	public ResponseDTO fetchCardHistoryDetails(RequestDTO requestDTO) {
		logger.debug("[CallCenterDAO][fetchCardHistoryDetails]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		HashMap<String, Object> cardbalDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		PreparedStatement  balpsmt=null;

		ResultSet crdbalRS =null;
		String entityQry = null;
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");

		if("ANUM".equalsIgnoreCase(radioselect))
		{
				 entityQry="select crd.card_no,cod.ordered_date,v_from_dt,v_from_dt,cod.plastic_code,cod.prd_name,cod.currency,v_thru_dt,decode(CARD_STATUS,'I','Active','B','Blocked','L','Locked','S','Suspended',CARD_STATUS) from crd_master crd,card_order_details cod where crd.ACC_NO= ? and batch_no=cod.order_ref_no";


		}else if("CNUM".equalsIgnoreCase(radioselect))
		{
				entityQry="select crd.card_no,cod.ordered_date,v_from_dt,v_from_dt,cod.plastic_code,cod.prd_name,cod.currency,v_thru_dt,decode(CARD_STATUS,'I','Active','B','Blocked','L','Locked','S','Suspended',CARD_STATUS) from crd_master crd,card_order_details cod where card_no= ? and batch_no=cod.order_ref_no";


		}

		try {
			connection = DBConnector.getConnection();
			logger.debug("[CallCenterDAO][fetchCardHistoryDetails][connection:"+connection+"]");
			balpsmt = connection.prepareStatement(entityQry);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				balpsmt.setString(1, cardNumber);
			}
			crdbalRS = balpsmt.executeQuery();
			JSONObject json = null;
			while (crdbalRS.next()) {
				json = new JSONObject();
				json.put("cardNumber1", crdbalRS.getString(1));
				json.put("cardOrderDate", crdbalRS.getString(2));
				json.put("cardGeneratedDate", crdbalRS.getString(3));
				json.put("cardActivatedDate", crdbalRS.getString(4));
				json.put("plasticName", crdbalRS.getString(5));
				json.put("productName", crdbalRS.getString(6));
				json.put("currency", crdbalRS.getString(7));
				json.put("cardExpiryDate", crdbalRS.getString(8));
				json.put("cardStatus", crdbalRS.getString(9));
			}

			resultJson.put("CARD_HISTORY_INFO", json);
			logger.debug("[CallCenterDAO][fetchCardHistoryDetails][requestDTO:::::"+resultJson+"]");
			cardbalDataMap.put("CARD_HISTORY_INFO", resultJson);
			responseDTO.setData(cardbalDataMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(balpsmt);
			DBUtils.closeResultSet(crdbalRS);
		}
		return responseDTO;

	}

	public ResponseDTO fetchCustomerDetails(RequestDTO requestDTO) {

		logger.debug("inside [CallCenterDAO][fetchCustomerDetails]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		JSONObject resultJson = new JSONObject();
		PreparedStatement  custpsmt=null;
		HashMap<String, Object> accountMap = null;

		ResultSet custupdateRS =null;
		String entityQry=null;
		String cardNumber=requestJSON.getString("cardNumber");
		String accountNumber=requestJSON.getString("accountNumber");
		String radioselect=requestJSON.getString("radioselect");

			if("ANUM".equalsIgnoreCase(radioselect))
			{
					logger.debug("Inside [AccountNumber]");
				 entityQry = "select CUST.F_NAME,CUST.M_NAME,CUST.L_NAME,to_char(CUST.DOB,'DD-MM-YYYY'),CUST.GENDER,CUST.MOBILE_NO,CUST.EMAIL_ID,CUST.ADDRESS  from CUST_MASTER CUST ,CRD_MASTER CM "
							+ "WHERE CUST.CIN=CM.CIN and CM.CARD_NO=?";


			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
					logger.debug("Inside[CardNumber]");
				 entityQry = "select CUST.F_NAME,CUST.M_NAME,CUST.L_NAME,to_char(CUST.DOB,'DD-MM-YYYY'),CUST.GENDER,CUST.MOBILE_NO,CUST.EMAIL_ID,CUST.ADDRESS  from CUST_MASTER CUST ,CRD_MASTER CM "
							+ "WHERE CUST.CIN=CM.CIN and CM.CARD_NO=?";


			}

		try {
			accountMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("[CallCenterDAO][fetchCustomerDetails] [connection:::" + connection + "]");
			custpsmt = connection.prepareStatement(entityQry);
			if("ANUM".equalsIgnoreCase(radioselect))
			{
				custpsmt.setString(1, accountNumber);

			}else if("CNUM".equalsIgnoreCase(radioselect))
			{
				custpsmt.setString(1, cardNumber);
			}

			custupdateRS = custpsmt.executeQuery();
			JSONObject json = null;
			while (custupdateRS.next()) {
				json = new JSONObject();
				resultJson.put("F_NAME", custupdateRS.getString(1));
				resultJson.put("M_NAME", custupdateRS.getString(2));
				resultJson.put("L_NAME", custupdateRS.getString(3));
				resultJson.put("DOB", custupdateRS.getString(4));
				resultJson.put("GENDER", custupdateRS.getString(5));
				resultJson.put("MOBILE_NO", custupdateRS.getString(6));
				resultJson.put("EMAIL_ID", custupdateRS.getString(7));
				resultJson.put("ADDRESS", custupdateRS.getString(8));

			}

			resultJson.put("cardNumber",cardNumber);
			accountMap.put("CUST_DATA", resultJson);
			logger.debug("[CallCenterDAO][fetchCustomerDetails][accountMap:" + accountMap + "]");

			responseDTO.setData(accountMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(custpsmt);
			DBUtils.closeResultSet(custupdateRS);
		}
		return responseDTO;
	}


	public ResponseDTO updateCustProfile(RequestDTO requestDTO) {

		logger.debug("Inside [CallCenterDAO][updateCustProfile].. ");

		Connection connection = null;
		PreparedStatement updatePstmt = null;
		ResultSet updateRS = null;
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			connection = DBConnector.getConnection();
			logger.debug(" [CallCenterDAO][updateCustProfile] [connection:" + connection + "]");
			requestJSON=requestDTO.getRequestJSON();
			logger.debug("[CallCenterDAO][updateCustProfile] [requestJSON:  [" + requestJSON + "]");
			String updateAcctStatus="update CUST_MASTER set ADDRESS=? where CIN in (select cin from CRD_MASTER where CARD_NO=?)";
			updatePstmt=connection.prepareStatement(updateAcctStatus);
			updatePstmt.setString(1, requestJSON.getString("ADDRESS"));
			updatePstmt.setString(2, requestJSON.getString("cardNumber"));
			int resCnt=updatePstmt.executeUpdate();
			if(resCnt>0){
				responseDTO.addMessages("Customer Details Successfully Updated.");
			}else{
				responseDTO.addError("Customer Details Updation Failed.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (updatePstmt != null)
					updatePstmt.close();
				if (updateRS != null)
					updateRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return responseDTO;
	}

	public ResponseDTO fetchCustomerAcctInformation(RequestDTO requestDTO) {

		logger.debug("Inside[CallCenterDAO][fetchCustomerAcctInformation].. ");
		HashMap<String, Object> accountMap = null;
		JSONObject resultJson = null;
		JSONArray accountJSONArray = null;

		Connection connection = null;
		PreparedStatement accountPstmt = null;
		PreparedStatement cardDetPstmt = null;
		ResultSet accountRS = null;
		ResultSet cardDetRS = null;


		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			accountMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			accountJSONArray = new JSONArray();
			connection = DBConnector.getConnection();

			logger.debug("[CallCenterDAO][fetchCustomerAcctInformation][Connection" + connection+ "]");

			requestJSON=requestDTO.getRequestJSON();
			logger.debug("[CallCenterDAO][fetchCustomerAcctInformation][requestJSON:" + requestJSON + "]");

			String acctQry = "select CM.CARD_NO,AM.CARD_ACCT,CM.BR_ID,'Prepaid',to_char(AM.CARD_ACCT_BAL,'99999999.99'),decode(AM.CARD_ACCT_STATUS,'I','Active','O','Active','B','Blocked','L','Locked','S','Suspended',AM.CARD_ACCT_STATUS) from ACCT_MASTER AM ,CRD_MASTER CM,CRD_ACCT_LINK CAL "
					+ "WHERE AM.CARD_ACCT=CAL.CARD_ACCT and CAL.CIN=CM.CIN and  CM.CARD_NO = ?";

			String cardDetailsQry = "select CM.CARD_NO,decode(CM.CARD_STATUS,'I','Active','B','Blocked','L','Locked','S','Suspended',CM.CARD_STATUS),CUST.F_NAME||' '||CUST.L_NAME "
					+ "from CRD_MASTER CM,CARD_ORDER_DETAILS COD,CUST_MASTER CUST "
					+ "where card_no=? and CM.BATCH_NO=COD.ORDER_REF_NO and CUST.CIN=CM.CIN";

			accountPstmt = connection.prepareStatement(acctQry);
			accountPstmt.setString(1, requestJSON.getString("CARD_NO"));
			accountRS = accountPstmt.executeQuery();

			cardDetPstmt=  connection.prepareStatement(cardDetailsQry);
			cardDetPstmt.setString(1, requestJSON.getString("CARD_NO"));
			cardDetRS = cardDetPstmt.executeQuery();

			JSONObject json = null;

			while (accountRS.next()) {
				json = new JSONObject();
				json.put("CARD_NO", accountRS.getString(1));
				json.put("CARD_ACCT", accountRS.getString(2));
				json.put("BRANCH", accountRS.getString(3));
				json.put("ACCT_TYPE", accountRS.getString(4));
				json.put("ACCT_BAL", accountRS.getString(5));
				json.put("ACCT_STATUS", accountRS.getString(6));
				accountJSONArray.add(json);
			}
			logger.debug("[CallCenterDAO][fetchCustomerAcctInformation][accountJSONArray:" + accountJSONArray + "]");

			resultJson.put("ACCOUNTS", accountJSONArray);

			while(cardDetRS.next()){
				resultJson.put("CARD_NO", cardDetRS.getString(1));
				resultJson.put("CARD_STATUS", cardDetRS.getString(2));
				resultJson.put("PRD_NAME", cardDetRS.getString(3));
			}
			accountMap.put("ACCOUNTS", resultJson);

			responseDTO.setData(accountMap);

			json = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (accountPstmt != null)
					accountPstmt.close();
				if (cardDetPstmt != null)
					cardDetPstmt.close();
				if (accountRS != null)
					accountRS.close();
				if (cardDetRS != null)
					cardDetRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			accountMap = null;
			resultJson = null;
			accountJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO resetWebPassword(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [CallCenterDAO][resetWebPassword]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[getWebPasswordReset][requestJSON:::"+requestJSON+"]");

		CallableStatement callableStatement = null;
		String resetWebPortalPwdProc = "{call resetWebPortalPwdProc(?,?,?,?,?)}";
		try {

			connection=DBConnector.getConnection();
			logger.debug("[CallCenterDAO][resetWebPassword][connection :::"+connection+"]");

			callableStatement = connection.prepareCall(resetWebPortalPwdProc);
			callableStatement.setString(1, requestJSON.getString("CARD_NO"));
			callableStatement.setString(2, requestJSON.getString("PASSWORD"));
			callableStatement.setString(3, requestJSON.getString("ENCRYPT_PASSWORD"));
			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(4);
			String Msg=callableStatement.getString(5);
			logger.debug("[CallCenterDAO][resetWebPassword] resultCnt from DB:::"+resCnt);
			logger.debug("[CallCenterDAO][resetWebPassword] [Msg:::"+Msg+"]");

			if(resCnt==1){
				responseDTO.addMessages(Msg);
			}else if(resCnt==-1){

				responseDTO.addError(Msg);
			}else{
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {

		}

		finally{

			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (Exception e) {

			}
		}

		return responseDTO;
	}
}
