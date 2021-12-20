package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.DBUtil;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.util.DBUtils;


public class BranchDao {


	private Logger logger = Logger.getLogger(BranchDao.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;


	
	public ResponseDTO branchStatusinfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select FCUBS_BRANCH_CODE,BRANCH_NAME,STATE,LOCAL_GOVERNMENT,BRANCH_STATUS from ACTIVE_BRANCHES";


		/*
		 * String lmtfeeQry =
		 * "select FCUBS_BRANCH_CODE,BRANCH_NAME,STATE,LOCAL_GOVERNMENT,BRANCH_STATUS from ACTIVE_BRANCHES where rownum<10"
		 * ;
		 *  where FCUBS_BRANCH_CODE='557'
		 */

		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				json.put("CLUSTER_ID", getlmtfeeRs.getString(1));
				json.put("CLUSTER_NAME", getlmtfeeRs.getString(2));
				json.put("STATE", getlmtfeeRs.getString(3));
				json.put("LOCAL_GOVERNMENT", getlmtfeeRs.getString(4));
				json.put("BRANCH_STATUS", getlmtfeeRs.getString(5));
							
				lmtJsonArray.add(json);
			}

			resultJson.put("BRANCH_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO branchDetails(RequestDTO requestDTO) {

		
		HashMap<String, Object> limitcodeDataMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;
		JSONArray JsonArray= new JSONArray();
		PreparedStatement getlimitcodePstmt = null;
		ResultSet getlimitcodeRs = null;
		Connection connection = null;

		String  limitcode = "";
		String  limitcodeCountQry = "select FCUBS_BRANCH_CODE,BRANCH_NAME,BRANCH_ADDRESS,STATE,OPENING_TIME,CLOSING_TIME,TRANS_DTTM,decode(BRANCH_STATUS,'A','Active','Deactive'),LOCAL_GOVERNMENT,ZONE from ACTIVE_BRANCHES where FCUBS_BRANCH_CODE=?";

		try {
			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();
			limitcodeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();

			limitcode = requestJSON.getString(CevaCommonConstants. LIMIT_CODE);

			getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
			getlimitcodePstmt.setString(1, limitcode);
			getlimitcodeRs = getlimitcodePstmt.executeQuery();
			json = new JSONObject();
			
			if (getlimitcodeRs.next()) {				
				 json.put("FCUBS_BRANCH_CODE", getlimitcodeRs.getString(1));
			     json.put("BRANCH_NAME", getlimitcodeRs.getString(2));
			     json.put("BRANCH_ADDRESS", getlimitcodeRs.getString(3));
			     json.put("STATE", getlimitcodeRs.getString(4));
			     json.put("OPENING_TIME", getlimitcodeRs.getString(5));
			     json.put("CLOSING_TIME", getlimitcodeRs.getString(6));
			     json.put("transdttm", getlimitcodeRs.getString(7));
			     json.put("BRANCH_STATUS", getlimitcodeRs.getString(8));
			     json.put("LOCAL_GOVERNMENT", getlimitcodeRs.getString(9));
			     json.put("ZONE", getlimitcodeRs.getString(10));
			}
			

			resultJson.put("limitcodedetails", json);	
			limitcodeDataMap.put("BRANH_DETS", resultJson);
			responseDTO.setData(limitcodeDataMap);
			
			
			getlimitcodePstmt.close();
			getlimitcodeRs.close();
			

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
		} finally {
			try {

				if (getlimitcodeRs != null) {
					getlimitcodeRs.close();
				}

				if (getlimitcodePstmt != null) {
					getlimitcodePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			limitcodeDataMap = null;
			resultJson = null;
		}
		return responseDTO;
}

	
	public ResponseDTO updateActiveBranchDetails(RequestDTO requestDTO) {
		
		HashMap<String, Object> limitcodeDataMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;
		JSONArray JsonArray= new JSONArray();
		PreparedStatement getlimitcodePstmt = null;
		ResultSet getlimitcodeRs = null;
		Connection connection = null;

		String  limitcode = "";
		try {
			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();
			limitcodeDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();

			
			json = new JSONObject();
			
			if((requestJSON.getString("status")).equalsIgnoreCase("Active")){
				
				getlimitcodePstmt = connection.prepareStatement("UPDATE  ACTIVE_BRANCHES SET BRANCH_STATUS='D' where FCUBS_BRANCH_CODE=? ");
				getlimitcodePstmt.setString(1, requestJSON.getString("branchcode"));
				getlimitcodePstmt.executeUpdate();
				getlimitcodePstmt.close();
				json.put("message", "Branch Code "+requestJSON.getString("branchcode")+" Successfully Deactivated ");
			}else{
				getlimitcodePstmt = connection.prepareStatement("UPDATE  ACTIVE_BRANCHES SET BRANCH_STATUS='A' where FCUBS_BRANCH_CODE=? ");
				getlimitcodePstmt.setString(1, requestJSON.getString("branchcode"));
				getlimitcodePstmt.executeUpdate();
				getlimitcodePstmt.close();
				json.put("message", "Branch Code "+requestJSON.getString("branchcode")+" Successfully Actived ");
			}
			connection.commit();
			resultJson.put("limitcodedetails", json);	
			limitcodeDataMap.put("BRANH_DETS", resultJson);
			responseDTO.setData(limitcodeDataMap);
			

			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(getlimitcodePstmt);
			DBUtils.closeResultSet(getlimitcodeRs);
		}
		return responseDTO;
	}
	


}
