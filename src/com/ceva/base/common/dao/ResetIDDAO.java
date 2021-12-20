package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.banking.usermgmt.FtpPosPasswordUpdater;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class ResetIDDAO {

	private Logger logger = Logger.getLogger(ResetIDDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	PreparedStatement pstmt = null;

	public ResponseDTO resetID(RequestDTO requestDTO) {
		logger.debug("Inside  ResetIDDAO....");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertpinProc = "{call CashDepositPkg.transactionPinAlert(?,?,?,?,?,?)}";
		String message = "";
		try {
			responseDTO = new ResponseDTO();
			resonseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertpinProc);
			callableStatement.setString(1,requestJSON.getString(CevaCommonConstants.USER_ID));
			callableStatement.setString(2,requestJSON.getString(CevaCommonConstants.RESETID));
			callableStatement.setString(3,requestJSON.getString(CevaCommonConstants.randomNUM));
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.setString(6, requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();

			message = callableStatement.getString(4);
			//callableStatement.close();
			//user password updation in ftp_stirng
			String usersQry = "Select SERIAL_NO, TERMINAL_ID,STORE_ID, MERCHANT_ID from USER_TERMINAL_MAPPING UTM where ASSIGN_FALG = 'I'";
			pstmt =connection.prepareStatement(usersQry);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				new  FtpPosPasswordUpdater().generateCsvFile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}

			logger.debug("  Message [" + message + "]");

			if (!message.equalsIgnoreCase("SUCCESS"))
			{
				responseDTO.addError(message);
			} else {
				responseDTO.addMessages(message);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in ResetIDDAO [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("Exception in ResetIDDAO [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			insertpinProc = null;
		}

		return responseDTO;
	}
}
