package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;

public class ServiceMgmtAjaxDAO {

	ResponseDTO responseDTO=null;
	JSONObject requestJSON=null;
	JSONObject resonseJSON=null;
	


	Logger logger=Logger.getLogger(ServiceMgmtAjaxDAO.class);



	public ResponseDTO getHudumaSubService(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		HashMap<String,Object> resultMap=new HashMap<String,Object>();
		JSONArray storeJSONArray=new JSONArray();
		Connection connection=null;

		logger.debug("inside [ServiceMgmtAjaxDAO][getHudumaSubService]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[ServiceMgmtAjaxDAO][getHudumaSubService][requestJSON:::"+requestJSON+"]");
		resonseJSON=requestJSON;

		String refKeyQry="select REF_KEY from HUDUMA_MASTER where HUDUMA_SERVICE_CODE=?";

		PreparedStatement refKeyPstmt=null;
		PreparedStatement storePstmt=null;
		ResultSet refKeyRS=null;
		ResultSet storeRS=null;
		
		String refKey="";

		try {
			connection=DBConnector.getConnection();
			logger.debug("[ServiceMgmtAjaxDAO][getHudumaSubService] connection ["+connection);

			refKeyPstmt = connection.prepareStatement(refKeyQry);
			refKeyPstmt.setString(1, requestJSON.getString(CevaCommonConstants.SERVICE_CODE));
			refKeyRS=refKeyPstmt.executeQuery();
			
			
			if(refKeyRS.next()){
				refKey=refKeyRS.getString(1);
			}
			
			logger.debug("[ServiceMgmtAjaxDAO][getHudumaSubService] refKey["+refKey);
			
			String storeQry="SELECT HPROCESS_CODE,HPROCESS_NAME from HUDUMA_SERVICES  where REF_KEY=?";
			storePstmt = connection.prepareStatement(storeQry);
			storePstmt.setString(1, refKey);
			storeRS=storePstmt.executeQuery();
			JSONObject json=null;

			if(storeRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(1)+"-"+storeRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
				storeJSONArray.add(json);
			}

			resonseJSON.put(CevaCommonConstants.SERVICE_LIST,storeJSONArray);
			logger.debug("[ServiceMgmtAjaxDAO][getHudumaSubService] resonseJSON ["+resonseJSON);
			resultMap.put(CevaCommonConstants.SERVICE_LIST, resonseJSON);
			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			 
		}
		finally{
			try{
				if(refKeyPstmt!=null)
					refKeyPstmt.close();
				if(storePstmt!=null)
					storePstmt.close();
				if(refKeyRS!=null)
					refKeyRS.close();
				if(storeRS!=null)
					storeRS.close();

				if(connection!=null)
					connection.close();
			}catch(SQLException e){
				 
			}
		}

		return responseDTO;
	}
	public ResponseDTO searchSubService(RequestDTO requestDTO) {

		HashMap<String, Object> resultMap = null;

		Connection connection = null;

		logger.debug("Inside GetHudumaSubService.. ");

		PreparedStatement refKeyPstmt = null;
		ResultSet refKeyRS = null;
		
		PreparedStatement refKeyPstmt1 = null;
		ResultSet refKeyRS1 = null;
		JSONArray listJSONArray=new JSONArray();
		JSONArray ServiceJSONArray=new JSONArray();
		String refKey = "";
		JSONObject json = null;
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			resultMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();
			logger.debug(" connection is null  [" + connection == null + "]");

			requestJSON = requestDTO.getRequestJSON();
			String MerchantID=requestJSON.getString("merch_id");
			
			logger.debug("Merchant ID>>>>>>"+MerchantID);
			resonseJSON = requestJSON;
			/*refKey = "select distinct FEE_NAME,fee_Code  from fee_master where SERVICE_TXN_CODE=?";*/
			refKey = "select DISTINCT FEE_CODE,SERVICE_TXN_CODE||'-'||FEE_NAME from fee_master where fee_code not in (select fee_code from merchant_fee where merchant_id =?)";
			refKeyPstmt = connection.prepareStatement(refKey);
			refKeyPstmt.setString(1, requestJSON.getString("merch_id"));
			refKeyRS = refKeyPstmt.executeQuery();
			json = new JSONObject();

			while (refKeyRS.next()) {
				
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, refKeyRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, refKeyRS.getString(1));
				listJSONArray.add(json);
				//json.put(refKeyRS.getString(1), refKeyRS.getString(2));
				
			}
			logger.debug("listJSONARRAY>>>>>>>>>"+listJSONArray);
			resonseJSON.put("NOT_ASSIGNED", listJSONArray);
	
		//	refKeyPstmt.close();
			//refKeyRS.close();
			//refKey=null;
			
			String refKey1 = "SELECT DISTINCT ME.FEE_CODE,FE.SERVICE_TXN_CODE||'-'||FEE_NAME FROM MERCHANT_FEE ME,FEE_MASTER FE WHERE ME.FEE_CODE=FE.FEE_CODE AND ME.MERCHANT_ID=?";
			logger.debug("refKey1::"+refKey1);
			refKeyPstmt1 = connection.prepareStatement(refKey1);
			refKeyPstmt1.setString(1, requestJSON.getString("merch_id"));
			refKeyRS1 = refKeyPstmt1.executeQuery();
		
			while (refKeyRS1.next()) {
			
				json.put(CevaCommonConstants.SELECT_KEY, refKeyRS1.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, refKeyRS1.getString(1));
				ServiceJSONArray.add(json);
				//json.put(refKeyRS.getString(1), refKeyRS.getString(2));
				
			}
			logger.debug("ServiceJSONArray>>>>>>>>>"+ServiceJSONArray);
			resonseJSON.put("ASSIGNED", ServiceJSONArray);
			
		
			
			logger.debug(" responseJSON [" + resonseJSON);
			resultMap.put(CevaCommonConstants.SERVICE_LIST, resonseJSON);
			responseDTO.setData(resultMap);

		} catch (SQLException e) {

		} finally {
			try {
				if (refKeyPstmt != null)
					refKeyPstmt.close();

				if (refKeyRS != null)
					refKeyRS.close();

				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
		}

		return responseDTO;
	}
	
	

}
