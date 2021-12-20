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

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class BarChatDAO {

	Logger logger = Logger.getLogger(BarChatDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;

	public ResponseDTO getBarData(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside getBarData.. ");

		CallableStatement callableStatement = null;
		String insertAnnouncementProc = "{call insertAnnouncementProc(?,?,?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertAnnouncementProc);
			callableStatement.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.setString(2,
					requestJSON.getString("TYPE_OF_DATA"));
			callableStatement.setString(3,
					requestJSON.getString("TYPE_OF_DATA_VAL"));
			callableStatement.setString(4, requestJSON.getString("MESSAGE"));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);
			String Msg = callableStatement.getString(6);

			logger.debug("ResultCnt from DB [" + resCnt + "]");

			if (resCnt == 1) {
				responseDTO.addMessages(Msg);
			} else if (resCnt == -1) {
				responseDTO.addError(Msg);
			} else {
				responseDTO.addError(Msg);
			}

		} catch (SQLException e) {
			logger.debug("Got SQLException [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} catch (Exception e) {
			logger.debug("Got Exception [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
					if (connection != null)
						connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return responseDTO;
	}
	
	public ResponseDTO fetchStores(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		Connection connection=null;
		logger.debug("inside [BarChatDAO][getStores]");
		HashMap<String,Object> regionDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray regionJSONArray=new JSONArray();
		PreparedStatement regionPstmt=null; 
		ResultSet regionRS=null;
		String regionQry="Select STORE_ID,STORE_NAME from STORE_MASTER WHERE MERCHANT_ID=?";
		try {
			connection=DBConnector.getConnection();
			logger.debug("inside [BarChatDAO][getStores][connection:::"+connection+"]");
			requestJSON=requestDTO.getRequestJSON();
			regionPstmt=connection.prepareStatement(regionQry);
			regionPstmt.setString(1,
					requestJSON.getString("MERCHANT_ID"));
			regionRS=regionPstmt.executeQuery();
			JSONObject json=null;
			while(regionRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, regionRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, regionRS.getString(1));
				regionJSONArray.add(json);
			}
			logger.debug("inside [BarChatDAO][getRegionInfo][regionJSONArray:::"+regionJSONArray+"]");

			resultJson.put("STORE_LIST", regionJSONArray);
			regionDataMap.put("STORE_LIST", resultJson);

			logger.debug("inside [BarChatDAO][getStores][resultJson:::"+resultJson+"]");
			responseDTO.setData(regionDataMap);
			logger.debug("inside [BarChatDAO][getStores][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(regionPstmt);
			DBUtils.closeResultSet(regionRS);
		}

		return responseDTO;
		
	}
	
	public ResponseDTO fetchmerchantBarData(RequestDTO requestDTO) {

		Connection connection = null;

		logger.debug("Inside MerchantBar... ");

		String makerId = "";
		String merchantid = null;
		
		String reportType = null;
		String reportname = null;
		String reportDate = null;
		String yearName = null;
		String quarterName = null;
		String monthName = null;
		String weekName = null;
		HashMap<String,Object> graphmap=new HashMap<String,Object>();

		CallableStatement callableStatement = null;
		String insertBankInfoProc = "{call BarGraphPkg.getBarData(?,?,?,?,?,?,?,?,?,?)}";
		try {
			JSONObject resultJson=new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			
			reportType=requestJSON.getString("REPORT_TYPE");
			/*merchantid=requestJSON.getString("MERCHANT_ID");
			reportname=requestJSON.getString("REPORT_NAME");
			reportDate=requestJSON.getString("REPORT_DATE");
			yearName=requestJSON.getString("REPORT_YEAR");
			monthName=requestJSON.getString("REPORT_MONTH");
			weekName=requestJSON.getString("REPORT_WEEK");
			quarterName=requestJSON.getString("REPORT_QUARTER");*/
			
			if(reportType.equals("D")){
				reportDate=requestJSON.getString("REPORT_DATE");
			}else if(reportType.equals("W")){
				yearName=requestJSON.getString("REPORT_YEAR");
				monthName=requestJSON.getString("REPORT_MONTH");
				weekName=requestJSON.getString("REPORT_WEEK");
			}else if(reportType.equals("M")){
				yearName=requestJSON.getString("REPORT_YEAR");
				monthName=requestJSON.getString("REPORT_MONTH");
			}else if(reportType.equals("Q")){
				yearName=requestJSON.getString("REPORT_YEAR");
				quarterName=requestJSON.getString("REPORT_QUARTER");
			}
			

			connection = DBConnector.getConnection();
			//makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			callableStatement = connection.prepareCall(insertBankInfoProc);
			
			callableStatement.setString(1, reportType);
			callableStatement.setString(2, merchantid);
			callableStatement.setString(3, reportname);
			callableStatement.setString(4, reportDate);
			callableStatement.setString(5, yearName);
			callableStatement.setString(6, monthName);
			callableStatement.setString(7, weekName);
			callableStatement.setString(8, quarterName);
			callableStatement.registerOutParameter(9, OracleTypes.VARCHAR);
			callableStatement.registerOutParameter(10, java.sql.Types.INTEGER);
			
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(10);

			logger.debug("ResultCnt from DB [" + resCnt + "]");

			if (resCnt == 1) {
				resultJson.put("GRP_DATA", callableStatement.getString(9));
				resultJson.put("MESSAGE", "SUCCESS");
				logger.debug("grp_data "+callableStatement.getString(9));
				graphmap.put("GRP_DATA", resultJson);
				logger.debug("inside [BarChatDAO][getStores][resultJson:::"+resultJson+"]");
				responseDTO.setData(graphmap);
			
				logger.debug("inside [BarChatDAO][getStores][responseDTO:::"+responseDTO+"]");

				
			} else if (resCnt == -1) {
				responseDTO.addError("Bank Information Already Exists. ");
			} else {
				responseDTO.addError("Bank Information Creation failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException is  fetchmerchantBarData ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} catch (Exception e) {
			logger.debug("Exception is  fetchmerchantBarData ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {

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
	
	public ResponseDTO fetchuserlevel(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
	
		Connection connection=null;
		logger.debug("inside [BarCharDAO][fetchuserlevel]");
		HashMap<String,Object> regionDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray regionJSONArray=new JSONArray();
		PreparedStatement regionPstmt=null;
		ResultSet regionRS=null;
		PreparedStatement regionPstmt1=null;
		ResultSet regionRS1=null;
		String regionQry= null;
		String rQry= null;
		String usertype= null;
		
		try {
			connection=DBConnector.getConnection();
			logger.debug("inside [BarCharDAO][fetchuserlevel][connection:::"+connection+"]");
			
			requestJSON=requestDTO.getRequestJSON();
			
			if(requestJSON.getString("group").equals("GRPTYPE")){
				
				rQry="SELECT USER_TYPE FROM USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC WHERE UI.COMMON_ID=ULC.COMMON_ID AND LOGIN_USER_ID=?";
				
				regionPstmt1=connection.prepareStatement(rQry);
				regionPstmt1.setString(1,requestJSON.getString("makerid"));
				regionRS1=regionPstmt1.executeQuery();
				while(regionRS1.next()){
					usertype=regionRS1.getString(1);
				}
				regionPstmt1.close();
				regionRS1.close();
				
				if(usertype.equalsIgnoreCase("BADM")){
					usertype="'BADM','SDM','UDM'";
				}else if(usertype.startsWith("SDM")){
					usertype="'SDM','UDM'";
				}else if(usertype.startsWith("UDM")){
					usertype="'UDM'";
				}else if(usertype.startsWith("ADM")){
					usertype="'ADM'";
				}
				
				
				
				regionQry="SELECT KEY_VALUE||'-'||STATUS,STATUS FROM CONFIG_DATA WHERE KEY_GROUP='USER_DESIGNATION' AND KEY_TYPE in ("+usertype+") ORDER BY KEY_ID";
				System.out.println("regionQry ::: "+regionQry);
				regionPstmt=connection.prepareStatement(regionQry);
				//regionPstmt.setString(1,requestJSON.getString("makerid"));


				logger.debug("[BarChatDAO][fetchuserlevel][MAKER ID : "+requestJSON.getString("makerid")+"]");
			}else if(requestJSON.getString("group").equals("GRPLVL")){
				regionQry="SELECT KEY_VALUE||'-'||STATUS,SUBSTR(STATUS,INSTR(STATUS,'-')+1) FROM CONFIG_DATA WHERE KEY_GROUP='USER_DESIGNATION' AND KEY_TYPE=?";
				
				regionPstmt=connection.prepareStatement(regionQry);
				regionPstmt.setString(1,requestJSON.getString("groupType").split("-")[0]);


				logger.debug("[BarChatDAO][fetchuserlevel][GROUP TYPE : "+requestJSON.getString("groupType")+"]");
			}
			
			
			/*if(requestJSON.getString("group").equals("GRPTYPE")){
			regionQry="SELECT KEY_VALUE||'-'||STATUS,STATUS FROM CONFIG_DATA WHERE KEY_GROUP='USER_DESIGNATION' AND KEY_TYPE=?";
			}*/
			
			

			regionRS=regionPstmt.executeQuery();
			JSONObject json=null;
			while(regionRS.next()){
				json=new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, regionRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, regionRS.getString(1));
				regionJSONArray.add(json);
			}
			logger.debug("inside [BarCharDAO][fetchuserlevel][userlevelJSONArray:::"+regionJSONArray+"]");

			resultJson.put("USER_LEVEL_LIST", regionJSONArray);
			regionDataMap.put("USER_LEVEL_LIST", resultJson);

			logger.debug("inside [BarCharDAO][fetchuserlevel][resultJson:::"+resultJson+"]");
			responseDTO.setData(regionDataMap);
			logger.debug("inside [BarCharDAO][fetchuserlevel][responseDTO:::"+responseDTO+"]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(regionPstmt);
			DBUtils.closeResultSet(regionRS);
		}

		return responseDTO;
		
	}

	
}
