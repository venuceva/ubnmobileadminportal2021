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

public class switchrrnDAO {
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	Connection connection = null;
	JSONObject json = null;

	Logger logger = Logger.getLogger(switchrrnDAO.class);

	public ResponseDTO SwitchRRDAO(RequestDTO requestDTO) {

		logger.debug("Inside SwitchrrnDAO......");
		HashMap<String, Object> BankDataMap = null;
		JSONObject resultJson = null;

		String rrn = "";

		PreparedStatement userChkPstmt = null;
		ResultSet USerChkRS = null;
		String userChkQry = "select TL.MSISDN MobileNumber, to_char(TL.TXN_TIME,'DD-MON-YYYY HH24:MI:SS') DateTime, "
				+ "DECODE(TL.STATUS, 'P', 'Pending', 'S', 'Success','F','Failed') STATUS, to_number(TL.TXN_AMOUNT)/100 Amount, TL.BILL_REF_NO RRN, "
				+ "TL.TXN_TYPE TXNTYPE, (select INSTITUTION_NAME from SERVICES_INSTITUTIONS where INSTITUTION_ID=TL.INSTITUTE) INSTITUTE, "
				+ "(select OPERATORNAME from MN_OPERATORS where  OPERATORID=TL.OPERATOR) OPERATOR, TL.TXN_ID TXNID, TL.PAYBILL_SHORTCODE ,"
				+ "TL.INVOICE_NO ,TL.ORG_ACCOUNT_BAL ,to_char(TL.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS') ,TL.TXN_TYPE ,TL.PAYBILLRESPONSE ,TL.CREDIT_AC ,TL.DEBIT_AC ,"
				+ "TL.CHANNELID ,TL.NARRATION FROM TRAN_LOG TL where TL.BILL_REF_NO=?";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();

			BankDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");
			rrn = requestJSON.getString(CevaCommonConstants.RRN);
			userChkPstmt = connection.prepareStatement(userChkQry);
			userChkPstmt.setString(1, rrn);
			USerChkRS = userChkPstmt.executeQuery();

			if (USerChkRS.next()) {
				resultJson.put("MOBILENUMBER", USerChkRS.getString(1));
				resultJson.put("DATETIME", USerChkRS.getString(2));
				resultJson.put("STATUS", USerChkRS.getString(3));
				resultJson.put("AMOUNT", USerChkRS.getString(4));
				resultJson.put("RRN", USerChkRS.getString(5));
				resultJson.put("TXNTYPE", USerChkRS.getString(6));
				resultJson.put("INSTITUTE", USerChkRS.getString(7));
				resultJson.put("OPERATOR", USerChkRS.getString(8));
				resultJson.put("TXNID", USerChkRS.getString(9));
				resultJson.put("PAYBILLSHT", USerChkRS.getString(10));
				resultJson.put("INVNO", USerChkRS.getString(11));
				resultJson.put("ORGACTBAL", USerChkRS.getString(12));
				resultJson.put("DATECRE", USerChkRS.getString(13));
				resultJson.put("RRN", USerChkRS.getString(14));
				resultJson.put("TXNTYPE", USerChkRS.getString(15));
				resultJson.put("INSTITUTE", USerChkRS.getString(16));
				resultJson.put("OPERATOR", USerChkRS.getString(17));
				resultJson.put("TXNID", USerChkRS.getString(18));
			}

			BankDataMap.put(CevaCommonConstants.USER_CHECK_INFO, resultJson);

			logger.debug("inside [switchrrnDAO][SwitchrrDAO][merchantDataMap:::"
					+ BankDataMap + "]");
			responseDTO.setData(BankDataMap);
			logger.debug("inside [switchrrnDAO][SwitchrrDAO][responseDTO:::"
					+ responseDTO + "]");

		} catch (SQLException e) {

		} finally {
			try {
				if (userChkPstmt != null)
					userChkPstmt.close();
				if (connection != null)
					connection.close();
				if (USerChkRS != null) {
					USerChkRS.close();
				}
			} catch (SQLException se) {

			}
		}

		return responseDTO;
	}

}
