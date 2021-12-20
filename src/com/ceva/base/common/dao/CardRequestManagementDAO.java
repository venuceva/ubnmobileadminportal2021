package com.ceva.base.common.dao;

import java.sql.CallableStatement;
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

public class CardRequestManagementDAO {

	Logger logger = Logger.getLogger(CardRequestManagementDAO.class);
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	Connection connection = null;
	
	public ResponseDTO fetchCustomerLimitDetains(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		requestJSON=requestDTO.getRequestJSON();
		HashMap<String,Object> limitDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray tidJSONArray=new JSONArray();
		JSONArray zoneAuthJOSNArray=new JSONArray();
		JSONArray specificJSONArray=new JSONArray();
		JSONArray targetJSONArray=new JSONArray();
		PreparedStatement tidPstmt=null;
		ResultSet tidRS=null;
		logger.debug(" [connection:::"+connection+"]");
		String entityQry="SELECT PRD_CODE, PRD_DESC FROM PRD_MASTER";
		String zoneAuthQry="SELECT KEY_VALUE FROM CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=?";
		String specificQry="SELECT KEY_VALUE FROM CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=?";
		String targetQry="SELECT KEY_VALUE FROM CONFIG_DATA where KEY_GROUP=? and KEY_TYPE=?";
		try {
			connection=DBConnector.getConnection();
			tidPstmt=connection.prepareStatement(entityQry);
			tidRS=tidPstmt.executeQuery();
			JSONObject json=null;
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("key", tidRS.getString(1));
				 json.put("val", tidRS.getString(2));
				 tidJSONArray.add(json);
			}
			logger.debug("[tidJSONArray:::"+tidJSONArray+"]");
			tidPstmt=null;
			tidRS =null;
			tidPstmt=connection.prepareStatement(zoneAuthQry);
			tidPstmt.setString(1, "ZONE_AUTH");
			tidPstmt.setString(2, "ZONE_AUTH");
			tidRS=tidPstmt.executeQuery();
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("key", tidRS.getString(1));
				 json.put("val", tidRS.getString(1));
				 zoneAuthJOSNArray.add(json);
			}
			logger.debug("[zoneAuthJOSNArray:::"+zoneAuthJOSNArray+"]");
			
			tidPstmt=null;
			tidRS =null;
			tidPstmt=connection.prepareStatement(specificQry);
			tidPstmt.setString(1, "SPECIFIC");
			tidPstmt.setString(2, "SPECIFIC");
			tidRS=tidPstmt.executeQuery();
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("key", tidRS.getString(1));
				 json.put("val", tidRS.getString(1));
				 specificJSONArray.add(json);
			}
			logger.debug("[specificJSONArray:::"+specificJSONArray+"]");
			
			tidPstmt=null;
			tidRS =null;
			tidPstmt=connection.prepareStatement(targetQry);
			tidPstmt.setString(1, "TARGET");
			tidPstmt.setString(2, "TARGET");
			tidRS=tidPstmt.executeQuery();
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("key", tidRS.getString(1));
				 json.put("val", tidRS.getString(1));
				 targetJSONArray.add(json);
			}
			logger.debug("[targetJSONArray:::"+targetJSONArray+"]");
			resultJson.put("PRD_LIST", tidJSONArray);
			resultJson.put("ZONE_AUTH_LIST", zoneAuthJOSNArray);
			resultJson.put("SPECIFIC_AUTH_LIST", specificJSONArray);
			resultJson.put("TARGET_AUTH_LIST", targetJSONArray);
			limitDataMap.put("PRD_LIST", resultJson);
			responseDTO.setData(limitDataMap);

			logger.debug("[CardRequestManagementDAO][fetchCustomerLimitDetains]responseDTO::["+responseDTO+"]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseDTO;
	}
	
	
	public ResponseDTO insertCustomerLimitDetails(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [CardRequestManagementDAO][insertCustomerLimitDetails]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[CardRequestManagementDAO][insertCustomerLimitDetails][requestJSON:::"+requestJSON+"]");

		CallableStatement callableStatement = null;
		String CustomerUsageSettingProc = "{call CustomerUsageSettingProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {

			connection=DBConnector.getConnection();
			logger.debug("[CardRequestManagementDAO][insertCustomerLimitDetails] connection ::: "+connection);

			callableStatement = connection.prepareCall(CustomerUsageSettingProc);
			callableStatement.setString(1, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2, requestJSON.getString("CARD_NO"));
			callableStatement.setString(3, requestJSON.getString("REG_NO"));
			callableStatement.setString(4, requestJSON.getString("CARD_TYPE"));
			callableStatement.setString(5, requestJSON.getString("PRODUCT_RESTRICT"));
			callableStatement.setString(6, requestJSON.getString("DRIVER_CODE"));
			callableStatement.setString(7, requestJSON.getString("CON_DRIVER_CODE"));
			callableStatement.setString(8, requestJSON.getString("MANAGE_KILOMETER"));
			callableStatement.setString(9, requestJSON.getString("OVERRIDE_IMPOSS"));
			callableStatement.setString(10, requestJSON.getString("LIMIT_DATA"));
			callableStatement.setString(11, requestJSON.getString("MONITOR_DATA"));
			callableStatement.setString(12, requestJSON.getString("ZONE_AUTH"));
			callableStatement.setString(13, requestJSON.getString("SPECIFIC"));
			callableStatement.registerOutParameter(14, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			
			int resCnt=callableStatement.getInt(14);
			String Msg=callableStatement.getString(15);

			logger.debug("[CardRequestManagementDAO][insertCustomerLimitDetails] resultCnt from DB:::"+resCnt);
			if(resCnt==1){
				responseDTO.addMessages(Msg);
			}else if(resCnt==-1){ 
				responseDTO.addError(Msg);
			}else{
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("[CardRequestManagementDAO][insertCustomerLimitDetails] exception:::"+e);
		}

		finally{
			try{
				if(callableStatement!=null){
					callableStatement.close();
					if(connection!=null)
						connection.close();
				}
			}catch(SQLException e){
				 
			}
		}
		return responseDTO;
	}
	
	public ResponseDTO fetchCustomerAssignedLimitDetails(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		requestJSON=requestDTO.getRequestJSON();
		HashMap<String,Object> limitDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		PreparedStatement tidPstmt=null;
		ResultSet tidRS=null;
		logger.debug(" [connection:::"+connection+"]");
		String entityQry="SELECT CLM.CARD_NO,CUST.F_NAME||' '||CUST.L_NAME,CLM.REG_NO,CLM.CARD_TYPE,CLM.PRODUCT_RESTRICT,CLM.DRIVER_CODE,CLM.CON_DRIVER_CODE,"
				+ "CLM.MANAGE_KILOMETER,CLM.OVERRIDE_IMPOSS FROM CUSTOMER_LIMIT_MASTER CLM,CUSTOMER_LIMIT_DETAILS CLD,CRD_MASTER CM,CUST_MASTER CUST where CLD.CARD_NO=CLM.CARD_NO and CM.CARD_NO= CLD.CARD_NO and CM.CARD_NO=CLM.CARD_NO and CM.CIN=CUST.CIN and   CLM.CARD_NO=trim(?)";
		try {
			connection=DBConnector.getConnection();
			
			tidPstmt=connection.prepareStatement(entityQry);
			tidPstmt.setString(1, requestJSON.getString("CARD_NO"));
			tidRS=tidPstmt.executeQuery();
			while(tidRS.next()){
				resultJson.put("cardNumber", tidRS.getString(1));
				resultJson.put("customerName", tidRS.getString(2));
				resultJson.put("registrationNumber", tidRS.getString(3));
				resultJson.put("cardType", tidRS.getString(4));
				resultJson.put("productRestrict", tidRS.getString(5));
				resultJson.put("driverCode", tidRS.getString(6));
				resultJson.put("contrDriverCode", tidRS.getString(7));
				resultJson.put("managingKilo", tidRS.getString(8));
				resultJson.put("overrideImpo", tidRS.getString(9));
			}
			
			tidPstmt=null;
			tidRS=null;
			String detailsQry="SELECT RESTRICT_TYPE,RESTRICT_CONTENT from CUSTOMER_LIMIT_DETAILS where CARD_NO=trim(?)";
			tidPstmt=connection.prepareStatement(detailsQry);
			tidPstmt.setString(1, requestJSON.getString("CARD_NO"));
			tidRS=tidPstmt.executeQuery();
			while(tidRS.next()){
				String resType=tidRS.getString(1);
					if(resType.equals("L"))
						resultJson.put("limitFinalData", tidRS.getString(2));
					if(resType.equals("M"))
						resultJson.put("monitorFinalData", tidRS.getString(2));
					if(resType.equals("Z"))
						resultJson.put("zoneAuth", tidRS.getString(2));
					if(resType.equals("S"))
						resultJson.put("specificMgmt", tidRS.getString(2));
			}
			
			limitDataMap.put("PRD_LIST", resultJson);
			responseDTO.setData(limitDataMap);

			logger.debug("[CardRequestManagementDAO][fetchCustomerAssignedLimitDetails]responseDTO::["+responseDTO+"]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseDTO;
	}
	
}
