package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class PaymentDAO {

	private Logger logger = Logger.getLogger(PaymentDAO.class);
	private ResponseDTO respDTO = null;



	public ResponseDTO acknowledgeService(RequestDTO requestDTO) {
		JSONObject jsonReq = requestDTO.getRequestJSON();
		HashMap<String, Object> data = null; 
		Connection con = null;
		CallableStatement callable = null;
		String message = "";
		String printMessage = "";
		logger.debug("[PaymentDAO] [acknowledgeService] jsonReq ["+jsonReq+"]");

		logger.debug("[PaymentDAO] [acknowledgeService] Before Calling Procedure...");

		String callingProcedure = "{call hudumaDataInsertion(?,?,?,?,?,?,?,?,?)}";

		try {
			con = DBConnector.getConnection(); 
			data = new HashMap<String,Object>();
			respDTO = new ResponseDTO();

			callable = con.prepareCall(callingProcedure);
			callable.setString(1, jsonReq.getString("total_info"));
			callable.setString(2, jsonReq.getString("billing_info"));
			callable.setString(3, jsonReq.getString("service_info"));
			callable.setString(4, jsonReq.getString("method"));
			callable.setString(5, jsonReq.getString("user_id"));
			callable.registerOutParameter(6, Types.VARCHAR);
			callable.registerOutParameter(7, Types.VARCHAR);
			callable.registerOutParameter(8, Types.VARCHAR);
			callable.registerOutParameter(9, Types.VARCHAR);

			logger.debug("[PaymentDAO] [acknowledgeService] Before executing Procedure...");

			boolean flag = callable.execute();		

			logger.debug("[PaymentDAO] [acknowledgeService] Prcedure Executed Successfully [" + flag+"]"
					+ " Error Message["+callable.getString(8)+"]");

			logger.debug("[PaymentDAO] [acknowledgeService] txn_ref_no ["+callable.getString(6)+"]");
			message = callable.getString(8);
			if(!message.equalsIgnoreCase("SUCCESS")) {
				respDTO.addError(message); 
			} else {
				printMessage = callable.getString(9);

				jsonReq.put("txn_ref_no", callable.getString(6));
 				jsonReq.put("print_details", printMessage); 

				data.put("print_details", printMessage);
				data.put("service_details", jsonReq);
				respDTO.setData(data);
			}  
		} catch (Exception e) {
			logger.debug("[PaymentDAO] [acknowledgeService] execption ["+e.getMessage()+"]");
			 
		}

		finally{
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(con);
		}

		logger.debug("[PaymentDAO] [acknowledgeService] Before executing data["+data+"]");

		return respDTO;
	}

	public ResponseDTO loadSerial(RequestDTO requestDTO) {
		JSONObject jsonReq = requestDTO.getRequestJSON();
		HashMap<String, Object> data = null; 
		Connection con = null;
		CallableStatement callable = null;
		String message = "";
		logger.debug("[PaymentDAO] [loadSerial] jsonReq ["+jsonReq+"]");

		logger.debug("[PaymentDAO] [loadSerial] Before Calling Procedure...");

		String callingProcedure = "{call HudumaPkg.LOADSERIALDATA(?,?,?)}";

		try {
			con = DBConnector.getConnection(); 
			data = new HashMap<String,Object>();
			respDTO = new ResponseDTO();

			callable = con.prepareCall(callingProcedure);
			callable.setString(1, jsonReq.getString("user_info")); 
			callable.setString(2, jsonReq.getString("user_id")); 
			callable.registerOutParameter(3, Types.VARCHAR);

			logger.debug("[PaymentDAO] [loadSerial] Before executing Procedure...");

			callable.execute();	 
			message = callable.getString(3);
			logger.debug("[PaymentDAO] [loadSerial] message ["+message+"]");

			if(!message.contains("Successfully") ) {
				respDTO.addError(message); 
			} else { 
				respDTO.addMessages(message);
			} 

		} catch (Exception e) {
			logger.debug("[PaymentDAO] [loadSerial] execption ["+e.getMessage()+"]");
			 
		}

		finally{
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(con);
		}

		logger.debug("[PaymentDAO] [loadSerial] Before executing data["+data+"]");

		return respDTO;
	}

}
