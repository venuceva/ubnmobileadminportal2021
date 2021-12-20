package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;








import com.ceva.util.DBUtils;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.DBConnector;

public class CardOrderDAO {
	Logger logger = Logger.getLogger(CardOrderDAO.class);
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	Connection connection = null;

	public ResponseDTO getDropdowns(RequestDTO requestDTO){
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		requestJSON=requestDTO.getRequestJSON();
		HashMap<String,Object> limitDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray tidJSONArray=new JSONArray();
		PreparedStatement transactionPstmt=null;
		ResultSet transactionRS=null;
		logger.debug(" [connection:::"+connection+"]");
		String entityQry="SELECT PRD_CODE, PRD_DESC FROM PRD_MASTER";
		try {
			connection=DBConnector.getConnection();
			PreparedStatement tidPstmt=connection.prepareStatement(entityQry);
			ResultSet tidRS=tidPstmt.executeQuery();
			JSONObject json=null;
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("key", tidRS.getString(1));
				 json.put("val", tidRS.getString(1));
				 tidJSONArray.add(json);
			}
			logger.debug("[tidJSONArray:::"+tidJSONArray+"]");

			resultJson.put("PRD_LIST", tidJSONArray);
			limitDataMap.put("PRD_LIST", resultJson);
			logger.debug("[CardOrderDAO][getDropdowns]limitDataMap::["+limitDataMap+"]");
			responseDTO.setData(limitDataMap);

			logger.debug("[CardOrderDAO][getDropdowns]responseDTO::["+responseDTO+"]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseDTO;
	}
	public ResponseDTO savecardInventory(RequestDTO requestDTO) {
		responseDTO = new ResponseDTO();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		PreparedStatement pstmt=null;
		String seq_ORDER_REF_NUMBER=null;
		ResultSet rset=null;
		String PlasticCode = requestJSON.get("plasticCode").toString();
			String PCode=PlasticCode.substring(0, 4);
		String psql="Insert into CARD_ORDER_DETAILS (BRANCH_ID,PRD_ID,PRD_NAME,PLASTIC_CODE,ORDERED_QUANTITY,ORDERED_DATE,STATUS_FLAG,USER_ID,ORDERED_AMOUNT,CURRENCY,ORDER_TYPE,MAKER_ID,REMARKS,TOP_UP_AMT,EMBOSS_NAME,MAIL_ID,ORDER_REF_NO)"
			+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			connection=DBConnector.getConnection();
			pstmt=connection.prepareStatement("select lpad ( ORDER_REF_NO.nextval, 6, '0' ) id from dual");
			 rset=pstmt.executeQuery();
			while(rset.next()){
				seq_ORDER_REF_NUMBER=rset.getString(1);
			}
			rset=null;
			pstmt=null;
			pstmt=connection.prepareStatement(psql);
			pstmt.setString(1, requestJSON.get("MAKER_ID").toString());
			pstmt.setString(2, requestJSON.get("productCode").toString());
			pstmt.setString(3, requestJSON.get("productDescription").toString());
			pstmt.setString(4, PCode);
			pstmt.setString(5, requestJSON.get("orderQuantity").toString());
			pstmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
			pstmt.setString(7, "IA");
			pstmt.setString(8, requestJSON.get("MAKER_ID").toString());
			pstmt.setString(9, "2000");
			pstmt.setString(10, "USD");
			pstmt.setString(11, "CO");
			pstmt.setString(12,  requestJSON.get("MAKER_ID").toString());
			pstmt.setString(13,  "REMARKS");
			pstmt.setString(14, requestJSON.get("cardValue").toString());
			pstmt.setString(15, requestJSON.get("cardEmbosingLine").toString());
			pstmt.setString(16, "MAIL_ID");
			pstmt.setString(17, seq_ORDER_REF_NUMBER);
			boolean resCnt=pstmt.execute();
			logger.debug("resCnt:::"+resCnt);
			logger.debug("[FeeManagementDAO][saveFeeDetails] resultCnt from DB:::::::::::::"+resCnt);
			if(!resCnt){
			responseDTO.addMessages("Fee Details Stored Successfully. ");
			}else{
				responseDTO.addError("Fee Creation failed.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeResultSet(rset);
		}
		return responseDTO;
	}
	public ResponseDTO getPlasticCodeAndDesc(RequestDTO requestDTO) {
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		resonseJSON=new JSONObject();
		requestJSON=requestDTO.getRequestJSON();
		HashMap<String,Object> limitDataMap=new HashMap<String,Object>();
		JSONObject resultJson=new JSONObject();
		JSONArray plasticcodedesc=new JSONArray();
		PreparedStatement transactionPstmt=null;
		ResultSet transactionRS=null;
		PreparedStatement tidPstmt=null;
		ResultSet tidRS=null;
		logger.debug(" [connection:::"+connection+"]");
		String entityQry="SELECT PLASTIC_CODE,PRD_DESC FROM PRD_MASTER WHERE PRD_CODE='"+requestJSON.getString("productCode")+"'";
		try {
			connection=DBConnector.getConnection();
			 tidPstmt=connection.prepareStatement(entityQry);
			 tidRS=tidPstmt.executeQuery();
			JSONObject json=null;
			while(tidRS.next()){
				 json=new JSONObject();
				 json.put("code", tidRS.getString(1));
				 json.put("desc", tidRS.getString(2));
				 plasticcodedesc.add(json);
			}
			logger.debug("[plasticcodedesc:::"+plasticcodedesc+"]");
				resultJson.put("PLASTIC_CODES", plasticcodedesc);
				limitDataMap.put("PLASTIC_CODES", resultJson);
			responseDTO.setData(limitDataMap);
			logger.debug("[responseDTO:::"+responseDTO+"]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(tidPstmt);
			DBUtils.closeResultSet(tidRS);
		}

		return responseDTO;
	}
	public ResponseDTO getTableDataToDisplay(RequestDTO requestDTO) {
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		HashMap<String, Object> limitDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		JSONArray tabJSONArray = new JSONArray();
		PreparedStatement transactionPstmt = null;
		ResultSet transactionRS = null;
		PreparedStatement tidPstmt=null;

		ResultSet tidRS =null;
		logger.debug(" [connection:::" + connection + "]");
		String entityQry = "SELECT ORDER_REF_NO, BRANCH_ID,COD.PRD_NAME,COD.ORDERED_QUANTITY,COD.ORDERED_DATE FROM CARD_ORDER_DETAILS COD WHERE COD.STATUS_FLAG='IA' ";
		try {
			connection = DBConnector.getConnection();
			 tidPstmt = connection.prepareStatement(entityQry);
			tidRS = tidPstmt.executeQuery();
			JSONObject json = null;
			while (tidRS.next()) {
				json = new JSONObject();
				json.put("orderId", tidRS.getString(1));
				json.put("branchId", tidRS.getString(2));
				json.put("Prdname", tidRS.getString(3));
				json.put("orderQuantity", tidRS.getString(4));
				json.put("orderdate", tidRS.getString(5));

				tabJSONArray.add(json);
			}
			logger.debug("[tabdataArray:::" + tabJSONArray + "]");

			resultJson.put("TAB_DATA_LIST", tabJSONArray);
			limitDataMap.put("TAB_DATA", resultJson);
			responseDTO.setData(limitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(tidPstmt);
			DBUtils.closeResultSet(tidRS);
		}
		return responseDTO;
	}

	public ResponseDTO saveSelectedRecord(RequestDTO requestDTO) {
		logger.debug("inside[CardOrderDAO][saveSelectedRecord]");
		logger.debug(" [saveSelectedRecord:::");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		String data=requestJSON.getString("selectedrecord");
		logger.debug("[CardOrderDAO][saveSelectedRecord]data:::["+data+"]");
		HashMap<String, Object> limitDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		JSONArray tabJSONArray = new JSONArray();
		CallableStatement callableStatement = null;
		ResultSet transactionRS = null;
		PreparedStatement psmt=null;
		String insertcard2 = "{call CARDS2(?,?,?,?,?)}";
		String selectedrecord = requestJSON.getString("selectedrecord");
		String vals[] = selectedrecord.split("&&");
		logger.debug("vals[0]::::"+vals[0]);
		logger.debug("vals[1]::::"+vals[1]);
		try {
			connection = DBConnector.getConnection();
			callableStatement = connection.prepareCall(insertcard2);
			callableStatement.setString(1, vals[0]);
			callableStatement.setString(2, "");
			callableStatement.setString(3, vals[1]);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			String errorData = callableStatement.getString(4);
			int resCnt = callableStatement.getInt(5);
			logger.debug("rescnt..:"+resCnt);
				if(resCnt == 0)
				{
					String quary="UPDATE CARD_ORDER_DETAILS SET STATUS_FLAG='CG' WHERE ORDER_REF_NO=?";
					connection = DBConnector.getConnection();
					psmt=connection.prepareStatement(quary);
					logger.debug("vals[0]::$$$$$$$::"+vals[0]);
					psmt.setString(1, vals[0]);
					psmt.executeUpdate();

				}
			logger.debug("errorData..:"+errorData);

			responseDTO.addError(errorData);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);

		}
		return responseDTO;
	}

	public ResponseDTO getDispatchDetails(RequestDTO requestDTO) {
		logger.debug("inside[CardDispatchDAO][getDispatchDetails]");
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		logger.debug("inside[CardOrderDAO][getDispatchDetails][requestJSON]::["+requestJSON+"]");
		HashMap<String, Object> limitDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		JSONArray tabJSONArray = new JSONArray();
		PreparedStatement transactionPstmt = null;
		ResultSet transactionRS = null;
		PreparedStatement tidPstmt=null;
		ResultSet tidRS=null;
		logger.debug(" [connection:::" + connection + "]");
		logger.debug("inside[CardOrderDAO][getTableDataToDisplay][connection]::["+connection+"]");
		String entityQry = "SELECT ORDER_REF_NO, BRANCH_ID,PRD_NAME,ORDERED_QUANTITY,ORDERED_DATE FROM CARD_ORDER_DETAILS COD WHERE COD.STATUS_FLAG='CG'";
		try {
			connection = DBConnector.getConnection();
			 tidPstmt = connection.prepareStatement(entityQry);
			 tidRS = tidPstmt.executeQuery();
			JSONObject json = null;
			while (tidRS.next()) {
				json = new JSONObject();
				json.put("orderQunt", tidRS.getString(1));
				json.put("branchId", tidRS.getString(2));
				json.put("prdname", tidRS.getString(3));
				json.put("orderquantity", tidRS.getString(4));
				json.put("orderdate", tidRS.getString(5));
				tabJSONArray.add(json);
			}
			logger.debug("[tabdataArray:::" + tabJSONArray + "]");
			logger.debug("inside[CardOrderDAO][getDispatchDetails][tabdataArray]::["+tabJSONArray+"]");

			resultJson.put("DISPATCH_DATA", tabJSONArray);
			logger.debug("inside[CardOrderDAO][getDispatchDetails][resultJson]::["+resultJson+"]");
			limitDataMap.put("DISPATCH_DATA", resultJson);
			responseDTO.setData(limitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(tidPstmt);
			DBUtils.closeResultSet(tidRS);
		}
		return responseDTO;
	}

	public ResponseDTO DispatchsaveSelectedRecord(RequestDTO requestDTO) {
		logger.debug("[CardDispatchDAO][saveSelectedRecord]");
		responseDTO = new ResponseDTO();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		String selectedRec=(String) requestJSON.get("selectedrecord");
		logger.debug("[CardDispatchDAO][DispatchsaveSelectedRecord][selectedRec]::["+selectedRec+"]");
		logger.debug("selectedRec..:"+selectedRec);
		PreparedStatement pstmt=null;
		String vals[] = selectedRec.split("&&");
		logger.debug("vals[0]::::"+vals[0]);
		logger.debug("vals[1]::::"+vals[1]);
		String saveQry="UPDATE CARD_ORDER_DETAILS SET STATUS_FLAG='DP' WHERE ORDER_REF_NO = ?";
		try{
			connection=DBConnector.getConnection();
			pstmt=connection.prepareStatement(saveQry);
			//pstmt.setString(1, "DP");
			pstmt.setString(1, vals[0]);
			int updated=pstmt.executeUpdate();
			logger.debug("updated..:"+updated);
			if(updated>0){
				responseDTO.addMessages("Card Dispatch Successfully. ");
			}
			else{
				responseDTO.addError("Card Dispatch Eailed");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(pstmt);

		}

		return responseDTO;
	}

	public ResponseDTO fetchdispatchDetails(RequestDTO requestDTO) {
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		HashMap<String, Object> limitDataMap = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		JSONArray tabJSONArray = new JSONArray();
		PreparedStatement transactionPstmt = null;
		ResultSet transactionRS = null;
		PreparedStatement tidPstmt=null;
		ResultSet tidRS=null;
		logger.debug(" [connection:::" + connection + "]");
		String entityQry = "SELECT ORDER_REF_NO, BRANCH_ID,PRD_NAME,ORDERED_QUANTITY,ORDERED_DATE FROM CARD_ORDER_DETAILS COD WHERE COD.STATUS_FLAG='DP'";
		try {
			connection = DBConnector.getConnection();
			 tidPstmt = connection.prepareStatement(entityQry);
			 tidRS = tidPstmt.executeQuery();
			JSONObject json = null;
			while (tidRS.next()) {
				json = new JSONObject();
				json.put("orderId", tidRS.getString(1));
				json.put("branchId", tidRS.getString(2));
				json.put("prdname", tidRS.getString(3));
				json.put("orderquantity", tidRS.getString(4));
				json.put("orderDate", tidRS.getString(5));
				tabJSONArray.add(json);
			}
			logger.debug("[tabdataArray:::" + tabJSONArray + "]");

			resultJson.put("DISPATCH_DATA", tabJSONArray);
			limitDataMap.put("DISPATCH_DATA", resultJson);
			responseDTO.setData(limitDataMap);
			logger.debug("[responseDTO:::" + responseDTO + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(tidPstmt);
			DBUtils.closeResultSet(tidRS);
		}
		return responseDTO;
	}

	public ResponseDTO savedelivarydetails(RequestDTO requestDTO) {
		responseDTO = new ResponseDTO();
		resonseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		String selectedRec=(String) requestJSON.get("selectedrecord");
		logger.debug("selectedRec..:"+selectedRec);
		String vals[] = selectedRec.split("&&");
		logger.debug("vals[0]::::"+vals[0]);
		logger.debug("vals[1]::::"+vals[1]);
		PreparedStatement pstmt=null;
		String saveQry="UPDATE CARD_ORDER_DETAILS SET STATUS_FLAG=? WHERE ORDER_REF_NO = ?";
		try{
			connection=DBConnector.getConnection();
			pstmt=connection.prepareStatement(saveQry);
			pstmt.setString(1, "CD");
			pstmt.setString(2, vals[0]);
			int updated=pstmt.executeUpdate();
			logger.debug("updated..:"+updated);
			if(updated>0){
				responseDTO.addMessages("Card Delivered Successfully. ");
			}
			else{
				responseDTO.addError("Card Delivery Eailed");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(pstmt);

		}

		return responseDTO;
	}

}
