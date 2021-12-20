package com.ceva.base.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;



public class USSDRequest {
	private static Logger logger = Logger.getLogger(USSDRequest.class);
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
public ResponseDTO getUssdRequest(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [USSDRequest][getUssdRequest]");


		Connection connection = null;
		
		PreparedStatement pstmt = null;

		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();


		

			String req_type=requestJSON.getString("reqtype");
		
			connection.setAutoCommit(false);
			
			if(req_type.equalsIgnoreCase("DSA")){
				pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST(ref_no,USER_ID,MOBILE_NO,STATUS,REQUEST_TYPE) VALUES(AGENT_SEQ.nextval,?,?,'P','DSA')");
				pstmt.setString(1, requestJSON.getString("USER_ID"));
				pstmt.setString(2, requestJSON.getString("MOBILE_NO"));
				pstmt.executeUpdate();
			}else{
				pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST(ref_no,BVN_NO,MOBILE_NO,DOB,STATUS,REQUEST_TYPE) VALUES(AGENT_SEQ.nextval,?,?,to_date(?,'dd/mm/yyyy'),'P','CUST')");
				pstmt.setString(1, requestJSON.getString("BVN_NO"));
				pstmt.setString(2, requestJSON.getString("MOBILE_NO"));
				pstmt.setString(3, requestJSON.getString("DOB"));
				pstmt.executeUpdate();
			}
			
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[[USSDRequest][getUssdRequest] SQLException in  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[USSDRequest][getUssdRequest] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			
		}

		return responseDTO;
	}

}
