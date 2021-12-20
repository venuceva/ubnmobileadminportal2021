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
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;

public class AgentRegistrationDAO {

	Logger logger = Logger.getLogger(AgentRegistrationDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	
	public ResponseDTO getAgentDetails(RequestDTO requestDTO) {

		logger.debug("Inside Agent Details... ");
		HashMap<String, Object> agentDataMap = null;

		JSONObject resultJson = null;
		JSONArray agentJSONArray = null;
		JSONArray BrachJSONArray = null;
		JSONArray zoneJSONArray = null;
		JSONArray agentdataJSONArray = null;
		Connection connection = null;
		PreparedStatement agentPstmt = null;
		PreparedStatement zonePstmt = null;
		PreparedStatement BranchPstmt = null;
		PreparedStatement agentDatapsmt = null;
	
		ResultSet agentRS = null;
		ResultSet BranchRS = null;
		ResultSet zoneRS = null;
		ResultSet agentdataRS = null;
		JSONObject json = null;
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			agentJSONArray = new JSONArray();
			BrachJSONArray = new JSONArray();
			zoneJSONArray = new JSONArray();
			agentdataJSONArray = new JSONArray();
			resultJson = new JSONObject();

			agentDataMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			String agentQry = "select TERMINAL_ID from TERMINAL_MASTER";
			agentPstmt = connection.prepareStatement(agentQry);
			agentRS = agentPstmt.executeQuery();
			json = new JSONObject();
			while (agentRS.next()) {

				json.put("key",agentRS.getString(1));
				json.put("val",agentRS.getString(1));
				agentJSONArray.add(json);
				json.clear();

			}
			resultJson.put("TERMINAL_ID",agentJSONArray);
			
			String branchLQry="select HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where HPO_FLAG='HEAD'";
			BranchPstmt = connection.prepareStatement(branchLQry);
			BranchRS = BranchPstmt.executeQuery();
			while (BranchRS.next()) {
				
				json.put("key",BranchRS.getString(2));
				json.put("val",BranchRS.getString(1));
			
				BrachJSONArray.add(json);
				json.clear();
				//json = null;
			}
			logger.debug("BrachJSONArray>>>>>>>>>>> [" + BrachJSONArray + "]");
			
			resultJson.put("BRANCH_DETAILS",BrachJSONArray);
			
			String zoneQry="select OFFICE_NAME from BRANCH_MASTER where OFFICE_NAME is not  null";
			
			zonePstmt = connection.prepareStatement(zoneQry);
			zoneRS = zonePstmt.executeQuery();
			JSONObject json1 = null;
			
			while (zoneRS.next()) {
				json1 = new JSONObject();
				json1.put("key",zoneRS.getString(1));
				json1.put("val",zoneRS.getString(1));
			
				zoneJSONArray.add(json1);
				json1.clear();
				json1 = null;
			}
			logger.debug("zoneJSONArray>>>>>>>>>>> [" + zoneJSONArray + "]");
			resultJson.put("ZONE_DETAILS",zoneJSONArray);
		
			String agentdata="SELECT TERMINALID,BRANCH,ZONE,PHYSICALPREMIS,MAKER_ID,MAKERDTTM FROM AGENT_REGISTRATION";
			
			
			agentDatapsmt = connection.prepareStatement(agentdata);
			agentdataRS = agentDatapsmt.executeQuery();
			//JSONObject json1 = null;
			
			while (agentdataRS.next()) {
				json1 = new JSONObject();
				json1.put("terminalid",agentdataRS.getString(1));
				json1.put("branch",agentdataRS.getString(2));
				json1.put("zone",agentdataRS.getString(3));
				json1.put("physicalpremis",agentdataRS.getString(4));
				json1.put("makerid",agentdataRS.getString(5));
				json1.put("makerdate",agentdataRS.getString(6));
				
				agentdataJSONArray.add(json1);
				json1.clear();
				//json = null;
			}
			
			logger.debug("agentdataJSONArray [" + agentdataJSONArray + "]");
			resultJson.put("AgentData",agentdataJSONArray);
		
			
			agentDataMap.put("AGENT_DETAILS", resultJson);

			logger.debug("agentDataMap [" + agentDataMap + "]");
			responseDTO.setData(agentDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Terminal Code ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in MerchantCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			resultJson = null;
			agentJSONArray = null;
			BrachJSONArray = null;
			zoneJSONArray = null;
			agentdataJSONArray = null;
			

			try {
				if (zonePstmt != null)
					zonePstmt.close();
				if (BranchPstmt != null)
					BranchPstmt.close();
				if (agentDatapsmt != null)
					agentDatapsmt.close();
				
				if (agentPstmt != null)
					agentPstmt.close();

				if (agentRS != null)
					agentRS.close();
			
				if (BranchRS != null)
					BranchRS.close();
				if (zoneRS != null)
					zoneRS.close();
				if (agentdataRS != null)
					agentdataRS.close();
				
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}
	
	public ResponseDTO InsertAgentInformation(RequestDTO requestDTO) {

		logger.debug("Inside agent information... ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertServiceProc = "{call INSERTAGENTINFORMATION(?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,	requestJSON.getString("MAKER_ID"));
			callableStatement.setString(2,	requestJSON.getString("terminalID"));
			callableStatement.setString(3,	requestJSON.getString("branch"));
			callableStatement.setString(4,	requestJSON.getString("zone"));
			callableStatement.setString(5, requestJSON.getString("physicalpremis"));
			callableStatement.setString(6,	requestJSON.getString("agentbankacnumber"));
			callableStatement.setString(7,	requestJSON.getString("numberofoutlets"));
			callableStatement.setString(8,	requestJSON.getString("comercialActivity"));
			callableStatement.setString(9,	requestJSON.getString("dob"));
			callableStatement.setString(10,	requestJSON.getString("registrationDate"));
			callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(12, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(12);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages("Agent Information insertion Successfully. ");
			} else if( resCnt == -1) {
				responseDTO.addError("Terminal ID Already Exsited.");
			}
			else
			{
				responseDTO.addError("Agent Information insertion Failed.");
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
	
	
	
	public ResponseDTO agentinformationview(RequestDTO requestDTO) {

		logger.debug("Inside Agent Information view... ");
		HashMap<String, Object> agentDataMap = null;

		JSONObject resultJson = null;
		JSONArray agentJSONArray = null;
		Connection connection = null;
		PreparedStatement agentPstmt = null;
		ResultSet agentRS = null;
		JSONObject json = null;
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			agentJSONArray = new JSONArray();
			resultJson = new JSONObject();
			agentDataMap = new HashMap<String, Object>();
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			String agentQry = "SELECT TERMINALID,BRANCH,ZONE,PHYSICALPREMIS,AGENTBANKACNUMBER,NUMBEROFOUTLETS,COMERCIALACTIVITY,DOB,REGISTRATIONDATE FROM AGENT_REGISTRATION WHERE TERMINALID=?";
			agentPstmt = connection.prepareStatement(agentQry);
			agentPstmt.setString(1, requestJSON.getString("terminalID"));
			agentRS = agentPstmt.executeQuery();
			json = new JSONObject();
			while (agentRS.next()) {

				json.put("terminalid",agentRS.getString(1));
				json.put("branch",agentRS.getString(2));
				json.put("zone",agentRS.getString(3));
				json.put("physicalpremis",agentRS.getString(4));
				json.put("agentbankacnumber",agentRS.getString(5));
				json.put("numberofoutlets",agentRS.getString(6));
				json.put("comercialActivity",agentRS.getString(7));
				json.put("dob",agentRS.getString(8));
				json.put("registrationDate",agentRS.getString(9));

			}
			agentDataMap.put("AGENT-INFORMATION", json);
			logger.debug("AGENT-INFORMATION [" + agentDataMap + "]");
			responseDTO.setData(agentDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Terminal Code ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in MerchantCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			resultJson = null;

			agentJSONArray = null;

			try {

				if (agentPstmt != null)
					agentPstmt.close();		

				if (agentRS != null)
					agentRS.close();
			

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}
	
	
	public ResponseDTO agentinformationmodify(RequestDTO requestDTO) {

		logger.debug("Inside modify action .. ");
		HashMap<String, Object> agentDataMap = null;

		JSONObject resultJson = null;
		JSONArray agentJSONArray = null;
		JSONArray BrachJSONArray = null;
		JSONArray zoneJSONArray = null;
		
		Connection connection = null;
		PreparedStatement agentPstmt = null;
		PreparedStatement agentterminalPstmt = null;
		ResultSet agentRS = null;
		ResultSet agentterminalRS = null;
		JSONObject json = null;
			try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			agentJSONArray = new JSONArray();
			BrachJSONArray = new JSONArray();
			zoneJSONArray = new JSONArray();
			
			JSONObject	resJson = new JSONObject();
			agentDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");
		
			String agentQry = "SELECT TERMINALID,BRANCH,ZONE,PHYSICALPREMIS,AGENTBANKACNUMBER,NUMBEROFOUTLETS,COMERCIALACTIVITY,DOB,REGISTRATIONDATE FROM AGENT_REGISTRATION WHERE TERMINALID=?";
			agentPstmt = connection.prepareStatement(agentQry);
			agentPstmt.setString(1, requestJSON.getString("terminalID"));
			agentRS = agentPstmt.executeQuery();
			json = new JSONObject();
			while (agentRS.next()) {

				resJson.put("terminalid",agentRS.getString(1));
				resJson.put("branch",agentRS.getString(2));
				resJson.put("zone",agentRS.getString(3));
				resJson.put("physicalpremis",agentRS.getString(4));
				resJson.put("agentbankacnumber",agentRS.getString(5));
				resJson.put("numberofoutlets",agentRS.getString(6));
				resJson.put("comercialActivity",agentRS.getString(7));
				resJson.put("dob",agentRS.getString(8));
				resJson.put("registrationDate",agentRS.getString(9));
				//json.clear();
				
			}
			agentPstmt.close();
			agentRS.close();
			
			String agenttermialQry = "select TERMINAL_ID from TERMINAL_MASTER";
			agentterminalPstmt = connection.prepareStatement(agenttermialQry);
			agentterminalRS = agentterminalPstmt.executeQuery();
		//	json = new JSONObject();
			while (agentterminalRS.next()) {

				json.put("key",agentterminalRS.getString(1));
				json.put("val",agentterminalRS.getString(1));
				agentJSONArray.add(json);
				json.clear();

			}
			resJson.put("TERMINAL_ID",agentJSONArray);
			agentterminalPstmt.close();
			agentterminalRS.close();
			
			String branchLQry="select HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where HPO_FLAG='HEAD'";
			agentterminalPstmt = connection.prepareStatement(branchLQry);
			agentterminalRS = agentterminalPstmt.executeQuery();
			while (agentterminalRS.next()) {
				
				json.put("key",agentterminalRS.getString(2));
				json.put("val",agentterminalRS.getString(1));
			
				BrachJSONArray.add(json);
				json.clear();
				//json = null;
			}

			agentterminalPstmt.close();
			agentterminalRS.close();
			
			String zoneQry="select OFFICE_NAME from BRANCH_MASTER where OFFICE_NAME is not  null";
			
			agentterminalPstmt = connection.prepareStatement(zoneQry);
			agentterminalRS = agentterminalPstmt.executeQuery();
		//	JSONObject json1 = null;
			
			while (agentterminalRS.next()) {
			//	json = new JSONObject();
				json.put("key",agentterminalRS.getString(1));
				json.put("val",agentterminalRS.getString(1));
			
				zoneJSONArray.add(json);
				json.clear();
				
			}
			logger.debug("zoneJSONArray>>>>>>>>>>> [" + zoneJSONArray + "]");
			resJson.put("ZONE_DETAILS",zoneJSONArray);
			
			resJson.put("BRANCH_DETAILS",BrachJSONArray);
			
			agentDataMap.put("AGENT-INFORMATION", resJson);

			logger.debug("AGENT-INFORMATION [" + agentDataMap + "]");
			responseDTO.setData(agentDataMap);
			logger.debug("Response DTO " + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in Terminal Code ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in MerchantCode ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			resultJson = null;

		
			agentJSONArray = null;

			try {

				if (agentPstmt != null)
					agentPstmt.close();
			

				if (agentRS != null)
					agentRS.close();
			

				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}
	
	
	
	public ResponseDTO UpdateAgentInformation(RequestDTO requestDTO) {

		logger.debug("Inside agent information Updattion... ");

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertServiceProc = "{call UPDATEAGENTINFORMATION(?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertServiceProc);
			callableStatement.setString(1,	requestJSON.getString("MAKER_ID"));
			callableStatement.setString(2,	requestJSON.getString("terminalID"));
			callableStatement.setString(3,	requestJSON.getString("branch"));
			callableStatement.setString(4,	requestJSON.getString("zone"));
			callableStatement.setString(5, requestJSON.getString("physicalpremis"));
			callableStatement.setString(6,	requestJSON.getString("agentbankacnumber"));
			callableStatement.setString(7,	requestJSON.getString("numberofoutlets"));
			callableStatement.setString(8,	requestJSON.getString("comercialActivity"));
			callableStatement.setString(9,	requestJSON.getString("dob"));
			callableStatement.setString(10,	requestJSON.getString("registrationDate"));
			callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(12, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(12);

			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO
						.addMessages("Agent Information updation Successfully. ");
			} else {
				responseDTO.addError("Agent Information updation Failed.");
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
