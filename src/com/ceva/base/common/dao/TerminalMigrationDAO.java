package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.DBConnector;
public class TerminalMigrationDAO {

	Logger logger = Logger.getLogger(TerminalMigrationDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	
	
	public ResponseDTO InsertTerminalMigrationDetails(RequestDTO requestDTO) {

		logger.debug("Inside Terminal migration Details Insertion... ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertServiceProc = "{call TERMINALMIGRATION(?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,	requestJSON.getString("MAKER_ID"));
			callableStatement.setString(2,	requestJSON.getString("merchantID"));
			callableStatement.setString(3,	requestJSON.getString("storeID"));
			callableStatement.setString(4,	requestJSON.getString("terminalID"));
			callableStatement.setString(5, requestJSON.getString("updatemerchantID"));
			callableStatement.setString(6,	requestJSON.getString("updatestoreID"));
			callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(7);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages("Terminal migration  Successfully. ");
			}
			else
			{
				responseDTO.addError("Terminal migration  Failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in  ["	+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in  ["	+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			insertServiceProc = null;
			try {

				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}
	
}
