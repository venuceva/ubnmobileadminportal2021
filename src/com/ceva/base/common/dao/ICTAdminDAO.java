package com.ceva.base.common.dao;

import java.sql.CallableStatement;
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
import com.ceva.util.DBUtils;

public class ICTAdminDAO {

	Logger logger=Logger.getLogger(ICTAdminDAO.class);
	ResponseDTO responseDTO=null;
	JSONObject requestJSON=null;
	JSONObject resonseJSON=null; 

	public ResponseDTO insertRegionDetails(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][insertRegionDetails]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[ICTAdminDAO][insertRegionDetails][requestJSON:::"+requestJSON+"]");
		String multiData=requestJSON.getString(CevaCommonConstants.MULTI_DATA);
		logger.debug("[ICTAdminDAO][insertRegionDetails][multiData:::"+multiData+"]");
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		logger.debug("[ICTAdminDAO][insertRegionDetails][makerId:::"+makerId+"]");

		CallableStatement callableStatement = null;
		String insertRegionInfoProc = "{call insertRegionDetailsProc(?,?,?)}";

		try {
			connection=DBConnector.getConnection();
			logger.debug("[ICTAdminDAO][insertRegionDetails] connection :::"+connection);

			callableStatement = connection.prepareCall(insertRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, multiData);
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(3);
			logger.debug("[ICTAdminDAO][insertRegionDetails] resultCnt from DB:::"+resCnt);
			if(resCnt==1){
				responseDTO.addMessages("Region Information Stored Successfully. ");
			}else if(resCnt==-1){

				responseDTO.addError("Region Information Already Exists. ");
			}else{
				responseDTO.addError("Region Information Creation failed.");
			}

		} catch (SQLException e) {
			 
		} 
		finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}

		return responseDTO;
	}

	public ResponseDTO getRegionInfo(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][getRegionInfo]");
		HashMap<String,Object> regionDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray regionJSONArray=new JSONArray();
		PreparedStatement regionPstmt=null; 
		ResultSet regionRS=null;
		String regionQry="Select REGION_CODE,REGION from REGION_MASTER order by REGION_CODE";
		try {
			connection=DBConnector.getConnection();
			logger.debug("inside [ICTAdminDAO][getRegionInfo][connection:::"+connection+"]");
			regionPstmt=connection.prepareStatement(regionQry);
			regionRS=regionPstmt.executeQuery();
			JSONObject json=null;
			while(regionRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, regionRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, regionRS.getString(1));
				regionJSONArray.add(json);
			}
			logger.debug("inside [ICTAdminDAO][getRegionInfo][regionJSONArray:::"+regionJSONArray+"]");

			resultJson.put(CevaCommonConstants.REGION_LIST, regionJSONArray);
			regionDataMap.put(CevaCommonConstants.REGION_INFO, resultJson);

			logger.debug("inside [ICTAdminDAO][getRegionInfo][regionDataMap:::"+regionDataMap+"]");
			responseDTO.setData(regionDataMap);
			logger.debug("inside [ICTAdminDAO][getRegionInfo][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(regionPstmt);
			DBUtils.closeResultSet(regionRS);
		}

		return responseDTO;
	}


	public ResponseDTO insertHeadOfficeDetails(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][insertHeadOfficeDetails]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[ICTAdminDAO][insertHeadOfficeDetails][requestJSON:::"+requestJSON+"]");
		String multiData=requestJSON.getString(CevaCommonConstants.MULTI_DATA);
		logger.debug("[ICTAdminDAO][insertHeadOfficeDetails][multiData:::"+multiData+"]");
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		logger.debug("[ICTAdminDAO][insertHeadOfficeDetails][makerId:::"+makerId+"]");


		CallableStatement callableStatement = null;
		String insertHeadOfficeInfoProc = "{call insertHeadOfficeDetailsProc(?,?,?)}";

		try {

			connection=DBConnector.getConnection();
			logger.debug("[ICTAdminDAO][insertRegionDetails] connection :::"+connection);

			callableStatement = connection.prepareCall(insertHeadOfficeInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, multiData);
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(3);
			logger.debug("[ICTAdminDAO][insertHeadOfficeDetails] resultCnt from DB:::"+resCnt);
			responseDTO=getRegionInfo(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Head Office Information Stored Successfully. ");
			}else if(resCnt==-1){

				responseDTO.addError("Head Office Information Already Exists. ");
			}else{
				responseDTO.addError("Head Office Information Creation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}

		return responseDTO;
	}



	public ResponseDTO insertLocations(RequestDTO requestDTO){
		responseDTO=new  ResponseDTO();
		requestJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][insertLocations]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("[ICTAdminDAO][insertLocations][requestJSON:::"+requestJSON+"]");
		String multiData=requestJSON.getString(CevaCommonConstants.MULTI_DATA);
		logger.debug("[ICTAdminDAO][insertLocations][multiData:::"+multiData+"]");
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		logger.debug("[ICTAdminDAO][insertLocations][makerId:::"+makerId+"]");



		CallableStatement callableStatement = null;
		String insertHeadOfficeInfoProc = "{call insertLocationsProc(?,?,?)}";

		try {
			connection=DBConnector.getConnection();
			logger.debug("[ICTAdminDAO][insertLocations] connection :::"+connection);
			callableStatement = connection.prepareCall(insertHeadOfficeInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, multiData);
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(3);
			logger.debug("[ICTAdminDAO][insertLocations] resultCnt from DB:::"+resCnt);
			responseDTO=getRegionInfo(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Location Information Stored Successfully. ");
			}else if(resCnt==-1){
				/*resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);*/
				responseDTO.addError("Location Information Already Exists. ");
			}else{
				responseDTO.addError("Location Information Creation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}

		return responseDTO;
	}



	public ResponseDTO retriveHeadOffice(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][retriveHeadOffice]");
		HashMap<String,Object> regionDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray regionJSONArray=new JSONArray();
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][retriveHeadOffice][requestJSON:::"+requestJSON+"]");
		String region=requestJSON.getString(CevaCommonConstants.REGION_ID);
		logger.debug("inside [ICTAdminDAO][retriveHeadOffice][region:::"+region+"]");

		PreparedStatement regionPstmt = null;
		ResultSet regionRS= null;
		logger.debug("inside [ICTAdminDAO][retriveHeadOffice][connection:::"+connection+"]");
		String regionQry="Select HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where REGION_CODE=? and  HPO_FLAG=? order by REGION_CODE";
		try {
			connection=DBConnector.getConnection();
			regionPstmt=connection.prepareStatement(regionQry);
			regionPstmt.setString(1, region);
			regionPstmt.setString(2, "HEAD");
			regionRS=regionPstmt.executeQuery();
			JSONObject json=null;
			while(regionRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, regionRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, regionRS.getString(1));
				regionJSONArray.add(json);
			}
			logger.debug("inside [ICTAdminDAO][retriveHeadOffice][regionJSONArray:::"+regionJSONArray+"]");

			resultJson.put(CevaCommonConstants.HEADOFFFICE_LIST, regionJSONArray);
			regionDataMap.put(CevaCommonConstants.HEADOFFICE_INFO, resultJson);

			logger.debug("inside [ICTAdminDAO][retriveHeadOffice][regionDataMap:::"+regionDataMap+"]");
			responseDTO.setData(regionDataMap);
			logger.debug("inside [ICTAdminDAO][retriveHeadOffice][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(regionPstmt);
			DBUtils.closeResultSet(regionRS);
		}

		return responseDTO;
	}

	public ResponseDTO getRegionDetails(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][getRegionDetails]");
		requestJSON=requestDTO.getRequestJSON();

		HashMap<String,Object> regionMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray regionJSONArray=new JSONArray();

		PreparedStatement regionPstmt=null;
		ResultSet regionRS=null;


		String regionQry="Select REGION_CODE,REGION,REGIONAL_HQ from"
				+ " REGION_MASTER";
		try {
			connection=DBConnector.getConnection();
			logger.debug("inside [ICTAdminDAO][getTransactionInformation][connection:::"+connection+"]");
			regionPstmt=connection.prepareStatement(regionQry);
			regionRS=regionPstmt.executeQuery();
			JSONObject json=null;
			while(regionRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.REGION_CODE, regionRS.getString(1));
				json.put(CevaCommonConstants.REGION, regionRS.getString(2));
				json.put(CevaCommonConstants.REGIONAL_HQ, regionRS.getString(3));
				regionJSONArray.add(json);
			}
			logger.debug("inside [ICTAdminDAO][getRegionDetails][regionJSONArray:::"+regionJSONArray+"]");
			resultJson.put(CevaCommonConstants.REGION_DATA, regionJSONArray);
			regionMap.put(CevaCommonConstants.REGION_DATA, resultJson);
			logger.debug("inside [ICTAdminDAO][getRegionDetails][regionMap:::"+regionMap+"]");
			responseDTO.setData(regionMap);
			logger.debug("inside [ICTAdminDAO][getRegionDetails][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(regionPstmt);
			DBUtils.closeResultSet(regionRS);
		}

		return responseDTO;
	}


	public ResponseDTO updateRegionInformartion(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();

		logger.debug("inside [ICTAdminDAO][updateRegionInformartion]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][updateRegionInformartion][requestJSON:::"+requestJSON+"]");
		Connection connection=null;
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String regionCode=requestJSON.getString(CevaCommonConstants.REGION_CODE);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String regionalHq=requestJSON.getString(CevaCommonConstants.REGIONAL_HQ); 

		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call updateRegionInformartion(?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, regionCode);
			callableStatement.setString(3, region);
			callableStatement.setString(4, regionalHq);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(5);
			logger.debug("[ICTAdminDAO][updateRegionInformartion] resultCnt from DB:::"+resCnt);
			responseDTO=getRegionDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Region Information Updated Successfully. ");
			}else if(resCnt==-1){
				/*resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);*/
				responseDTO.addError("Region Information Already Exists. ");
			}else{
				responseDTO.addError("Region Information Updatation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][updateRegionInformartion][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}

	public ResponseDTO deleteRegionInformartion(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][deleteRegionInformartion]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][deleteRegionInformartion][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String regionCode=requestJSON.getString(CevaCommonConstants.REGION_CODE);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String regionalHq=requestJSON.getString(CevaCommonConstants.REGIONAL_HQ); 

		CallableStatement callableStatement = null;
		String deleteRegionInfoProc = "{call deleteRegionInformartion(?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(deleteRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, regionCode);
			callableStatement.setString(3, region);
			callableStatement.setString(4, regionalHq);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(5);
			logger.debug("[ICTAdminDAO][deleteRegionInformartion] resultCnt from DB:::"+resCnt);
			responseDTO=getRegionDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Region Information Deleted Successfully. ");
			}else if(resCnt==-1){
				responseDTO.addError("Region Information Already Exists. ");
			}else{
				responseDTO.addError("Region Information Updatation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][deleteRegionInformartion][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}

	public ResponseDTO getHeadOfficeDetails(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][getHeadOfficeDetails]");
		HashMap<String,Object> headofficeMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray headofficeJSONArray=new JSONArray();
		requestJSON=requestDTO.getRequestJSON();
		PreparedStatement headofficePstmt= null;
		ResultSet headofficeRS= null;
		logger.debug("inside [ICTAdminDAO][getHeadOfficeDetails][connection:::"+connection+"]");
		String headofficeQry="Select REGION_CODE,HPO_DEPARTMENT_CODE,HPO_NAME from"
				+ " BRANCH_MASTER where HPO_FLAG is not null";
		try {

			connection=DBConnector.getConnection();

			headofficePstmt=connection.prepareStatement(headofficeQry);
			headofficeRS=headofficePstmt.executeQuery();
			JSONObject json=null;
			while(headofficeRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.REGION, headofficeRS.getString(1));
				json.put(CevaCommonConstants.HEADOFFICE_CODE, headofficeRS.getString(2));
				json.put(CevaCommonConstants.HEADOFFICE_NAME, headofficeRS.getString(3));
				headofficeJSONArray.add(json);
			}
			logger.debug("inside [ICTAdminDAO][getHeadOfficeDetails][regionJSONArray:::"+headofficeJSONArray+"]");
			resultJson.put(CevaCommonConstants.HEADOFFICE_DATA, headofficeJSONArray);
			headofficeMap.put(CevaCommonConstants.HEADOFFICE_DATA, resultJson);
			logger.debug("inside [ICTAdminDAO][getHeadOfficeDetails][regionMap:::"+headofficeMap+"]");
			responseDTO.setData(headofficeMap);
			logger.debug("inside [ICTAdminDAO][getHeadOfficeDetails][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(headofficeRS);
			DBUtils.closePreparedStatement(headofficePstmt);
		}

		return responseDTO;
	}

	public ResponseDTO updateHeadOfficeDetails(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][updateHeadOfficeDetails]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][updateHeadOfficeDetails][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String headOfficeCode=requestJSON.getString(CevaCommonConstants.HEADOFFICE_CODE);
		String headOfficeName=requestJSON.getString(CevaCommonConstants.HEADOFFICE_NAME);



		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call updateHeadOfficeDetails(?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, region);
			callableStatement.setString(3, headOfficeCode);
			callableStatement.setString(4, headOfficeName);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(5);
			logger.debug("[ICTAdminDAO][updateHeadOfficeDetails] resultCnt from DB:::"+resCnt);
			responseDTO=getHeadOfficeDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Head Office Information Updated Successfully. ");
			}else if(resCnt==-1){ 
				responseDTO.addError("Head Office Information Already Exists. ");
			}else{
				responseDTO.addError("Head Office Information Updatation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][updateHeadOfficeDetails][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}

	public ResponseDTO deleteHeadOfficeInformartion(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][deleteHeadOfficeInformartion]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][deleteHeadOfficeInformartion][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String headOfficeCode=requestJSON.getString(CevaCommonConstants.HEADOFFICE_CODE);
		String headOfficeName=requestJSON.getString(CevaCommonConstants.HEADOFFICE_NAME); 
		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call deleteHeadOfficeInformartion(?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, region);
			callableStatement.setString(3, headOfficeCode);
			callableStatement.setString(4, headOfficeName);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(5);
			logger.debug("[ICTAdminDAO][deleteHeadOfficeInformartion] resultCnt from DB:::"+resCnt);
			responseDTO=getHeadOfficeDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Head Office Information Deleted Successfully. ");
			}else if(resCnt==-1){
				/*resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);*/
				responseDTO.addError("Head Office Information Already Exists. ");
			}else{
				responseDTO.addError("Head Office Information Delete failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][deleteHeadOfficeInformartion][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}


	public ResponseDTO getLocationDetails(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][getLocationDetails]");
		HashMap<String,Object> locationMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray locationJSONArray=new JSONArray();
		requestJSON=requestDTO.getRequestJSON();

		PreparedStatement locationPstmt= null;
		ResultSet locationRS= null;
		logger.debug("inside [ICTAdminDAO][getLocationDetails][connection:::"+connection+"]");
		String locationQry="Select REGION_CODE,HPO_DEPARTMENT_CODE,OFFICE_CODE,OFFICE_NAME from"
				+ " BRANCH_MASTER where HPO_FLAG is null";
		try {

			connection=DBConnector.getConnection();

			locationPstmt=connection.prepareStatement(locationQry);
			locationRS=locationPstmt.executeQuery();
			JSONObject json=null;
			while(locationRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.REGION, locationRS.getString(1));
				json.put(CevaCommonConstants.HEADOFFICE_CODE, locationRS.getString(2));
				json.put(CevaCommonConstants.LOCATION_CODE, locationRS.getString(3));
				json.put(CevaCommonConstants.LOCATION_NAME, locationRS.getString(4));
				locationJSONArray.add(json);
			}
			logger.debug("inside [ICTAdminDAO][getLocationDetails][locationJSONArray:::"+locationJSONArray+"]");
			resultJson.put(CevaCommonConstants.LOCATION_DATA, locationJSONArray);
			locationMap.put(CevaCommonConstants.LOCATION_DATA, resultJson);
			logger.debug("inside [ICTAdminDAO][getLocationDetails][regionMap:::"+locationMap+"]");
			responseDTO.setData(locationMap);
			logger.debug("inside [ICTAdminDAO][getLocationDetails][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(locationPstmt);
			DBUtils.closeResultSet(locationRS);
		}

		return responseDTO;
	}


	public ResponseDTO updateLocationInformation(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][updateLocationInformation]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][updateLocationInformation][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String headOfficeCode=requestJSON.getString(CevaCommonConstants.HEADOFFICE_CODE);
		String locationCode=requestJSON.getString(CevaCommonConstants.LOCATION_CODE);
		String locationName=requestJSON.getString(CevaCommonConstants.LOCATION_NAME);



		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call updateLocationDetailsProc(?,?,?,?,?,?)}";
		try {

			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, region);
			callableStatement.setString(3, headOfficeCode);
			callableStatement.setString(4, locationCode);
			callableStatement.setString(5, locationName);
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(6);
			logger.debug("[ICTAdminDAO][updateLocationInformation] resultCnt from DB:::"+resCnt);
			responseDTO=getLocationDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Location Information Updated Successfully. ");
			}else if(resCnt==-1){
				/*resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);*/
				responseDTO.addError("Location Information Already Exists. ");
			}else{
				responseDTO.addError("Location Information Updatation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][updateLocationInformation][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}

	public ResponseDTO deleteLocationInformartion(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][deleteLocationInformartion]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][deleteLocationInformartion][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String region=requestJSON.getString(CevaCommonConstants.REGION);
		String headOfficeCode=requestJSON.getString(CevaCommonConstants.HEADOFFICE_CODE);
		String locationCode=requestJSON.getString(CevaCommonConstants.LOCATION_CODE);
		String locationName=requestJSON.getString(CevaCommonConstants.LOCATION_NAME); 

		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call deleteLocationDetailsProc(?,?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection();

			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, region);
			callableStatement.setString(3, headOfficeCode);
			callableStatement.setString(4, locationCode);
			callableStatement.setString(5, locationName);
			callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(6);
			logger.debug("[ICTAdminDAO][deleteLocationInformartion] resultCnt from DB:::"+resCnt);
			responseDTO=getLocationDetails(requestDTO);
			if(resCnt==1){
				responseDTO.addMessages("Location Information Deleted Successfully. ");
			}else if(resCnt==-1){
				/*resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);*/
				responseDTO.addError("Location Information Already Exists. ");
			}else{
				responseDTO.addError("Location Information Delete failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][deleteLocationInformartion][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}


	public ResponseDTO createBranchUser(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [ICTAdminDAO][createBranchUser]");
		requestJSON=requestDTO.getRequestJSON();
		logger.debug("inside [ICTAdminDAO][createBranchUser][requestJSON:::"+requestJSON+"]");

		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String userId=requestJSON.getString(CevaCommonConstants.USER_ID);
		String employeeNo=requestJSON.getString(CevaCommonConstants.EMPLOYEE_NO);
		String firstName=requestJSON.getString(CevaCommonConstants.F_NAME);
		String lastName=requestJSON.getString(CevaCommonConstants.L_NAME);
		String userLevel=requestJSON.getString(CevaCommonConstants.USER_LEVEL);
		String mobile=requestJSON.getString(CevaCommonConstants.MOBILE);
		String email=requestJSON.getString(CevaCommonConstants.EMAIL); 

		CallableStatement callableStatement = null;
		String updateRegionInfoProc = "{call createBranchUserProc(?,?,?,?,?,?,?,?,?)}";
		try {
			connection=DBConnector.getConnection(); 
			callableStatement = connection.prepareCall(updateRegionInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, userId);
			callableStatement.setString(3, employeeNo);
			callableStatement.setString(4, firstName);
			callableStatement.setString(5, lastName);
			callableStatement.setString(6, userLevel);
			callableStatement.setString(7, mobile);
			callableStatement.setString(8, email);
			callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			int resCnt=callableStatement.getInt(9);
			logger.debug("[ICTAdminDAO][createBranchUser] resultCnt from DB:::"+resCnt);
			if(resCnt==1){
				responseDTO.addMessages("User Created Successfully. ");
			}else if(resCnt==-1){
				responseDTO.addError("User Already Exists. ");
			}else{
				responseDTO.addError("User Creation failed.");
			}

		} catch (SQLException e) {
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}
		logger.debug("inside [ICTAdminDAO][createBranchUser][responseDTO:::"+responseDTO+"]");

		return responseDTO;
	}


}
