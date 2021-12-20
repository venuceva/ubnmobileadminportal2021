package com.ceva.base.common.dao;

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
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class MarketsCheckerDAO {

	Logger logger=Logger.getLogger(MarketsCheckerDAO.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	JSONArray dataJsonArray = null;
	HashMap<String, Object> dataMap = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qry ="";
	
	public ResponseDTO authRecordsCount(RequestDTO requestDTO) {
		
		qry="select STATUS,count(*) from ONLINE_PRODUCTS_MASTER where STATUS in ('M','A','R','AL') group by STATUS";
		String status="";
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			pstmt = connection.prepareStatement(qry);
			rs = pstmt.executeQuery();
			
			dataJsonArray = new JSONArray();
			JSONObject json =null;
			while (rs.next()) {
				json = new JSONObject();
				
				if(rs.getString(1).equals("M"))
					status="Product Un-Authorized";
				else if(rs.getString(1).equals("A"))
					status="Product Authorized";
				else if(rs.getString(1).equals("R"))
					status="Product Rejected";
				else if(rs.getString(1).equals("AL"))
					status="Product active";
				
				json.put("STATUS", status);
				json.put("COUNT", rs.getString(2));
				
				dataJsonArray.add(json);
			}
			
			qry="select STATUS,count(*) from ONLINE_PRODUCT_OFFERS_DEALS where STATUS in ('M','A','R') group by STATUS";
			
			pstmt = connection.prepareStatement(qry);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				json = new JSONObject();
				if(rs.getString(1).equals("M"))
					status="Deal/Offer Un-Authorized";
				else if(rs.getString(1).equals("A"))
					status="Deal/Offer Authorized";
				else if(rs.getString(1).equals("R"))
					status="Deal/Offer Rejected";
				
				json.put("STATUS", status);
				json.put("COUNT", rs.getString(2));
				
				dataJsonArray.add(json);
			}
			
			
			logger.debug("Res JSON Array [" + dataJsonArray + "]");

			responseJSON.put("RESPONSE_DATA", dataJsonArray);
			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in auth count [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in auth count [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}

	
	public ResponseDTO productInfoView(RequestDTO requestDTO) {
		
		qry="select OPM.PRODUCT_ID,OPM.PRODUCT_NAME,OPM.PRODUCT_PRICE,OPM.CATEGORY_ID,OCD.CATEGORY_DESC,  "
				+ "OPM.SUB_CATEGORY_ID,OSCD.SUB_CATEGORY_DESC,  decode(OPM.STATUS,'M','Un-Auth','A','Approved','R','Rejected',OPM.STATUS) STATUS,"
				+ "OPM.MAKER_ID,  to_char(OPM.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') MAKER_DTTM,CHECKER_ID, to_char(OPM.CHECKER_DTTM,'DD-MM-YYYY HH24:MI:SS') CHECKER_DTTM,OPM.COMMENTS "
				+ " from ONLINE_PRODUCTS_MASTER OPM,ONLINE_CATEGORY_DETAILS OCD,ONLINE_SUB_CATEGORY_DETAILS OSCD "
				+ " where OPM.CATEGORY_ID=OCD.CATEGORY_ID and OPM.SUB_CATEGORY_ID=OSCD.SUB_CATEGORY_ID  and "
				+ "OCD.CATEGORY_ID=OSCD.CATEGORY_ID and OPM.PRODUCT_ID=?";
		
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, requestJSON.getString("PRODUCT_ID"));
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				responseJSON.put("PRODUCT_ID", rs.getString(1));
				responseJSON.put("PRODUCT_NAME", rs.getString(2));
				responseJSON.put("PRODUCT_PRICE", rs.getString(3));
				responseJSON.put("CATEGORY_ID", rs.getString(4));
				responseJSON.put("CATEGORY_DESC", rs.getString(5));
				responseJSON.put("SUB_CATEGORY_ID", rs.getString(6));
				responseJSON.put("SUB_CATEGORY_DESC", rs.getString(7));
				responseJSON.put("STATUS", rs.getString(8));
				responseJSON.put("MAKER_ID", rs.getString(9));
				responseJSON.put("MAKER_DTTM", rs.getString(10));
				responseJSON.put("CHECKER_ID", rs.getString(11));
				responseJSON.put("CHECKER_DTTM", rs.getString(12));
				responseJSON.put("COMMENTS", rs.getString(13));
				
			}
			
			logger.debug("Res JSON  [" + responseJSON + "]");

			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in auth count [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in auth count [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}


	public ResponseDTO productApproveOrReject(RequestDTO requestDTO) {
		
		qry="update ONLINE_PRODUCTS_MASTER set STATUS=?,COMMENTS=? where PRODUCT_ID=? ";
		
		String reason="Success";
		
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			
			if(requestJSON.getString("APPROVE_REJECT").equals("R")){
				reason=requestJSON.getString("REASON");
			}
			
			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, requestJSON.getString("APPROVE_REJECT"));
			pstmt.setString(2, reason);
			pstmt.setString(3, requestJSON.getString("PRODUCT_ID"));
			int cnt = pstmt.executeUpdate();
			
			connection.commit();
			
			if(cnt >0){
				responseJSON.put("STATUS", "Success");
			}else{
				responseDTO.addError("Updation Failed.");
			}
			
			
			logger.debug("Res JSON  [" + responseJSON + "]");

			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in approve [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in approve [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}
	public ResponseDTO productApproveActive(RequestDTO requestDTO) {
		
		qry="update ONLINE_PRODUCTS_MASTER set STATUS=?,COMMENTS=? where PRODUCT_ID=? ";
		
		String reason="Success";
		
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			
			if(requestJSON.getString("APPROVE_REJECT").equals("R")){
				reason=requestJSON.getString("REASON");
			}
			
			pstmt = connection.prepareStatement(qry);
			if(requestJSON.getString("APPROVE_REJECT").equals("L")){
				pstmt.setString(1, requestJSON.getString("APPROVE_REJECT"));	
			}else{
				pstmt.setString(1, "A");
			}
			
			pstmt.setString(2, reason);
			pstmt.setString(3, requestJSON.getString("PRODUCT_ID"));
			int cnt = pstmt.executeUpdate();
			
			connection.commit();
			
			if(cnt >0){
				responseJSON.put("STATUS", "Success");
			}else{
				responseDTO.addError("Updation Failed.");
			}
			
			
			logger.debug("Res JSON  [" + responseJSON + "]");

			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in approve [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in approve [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}
	
	public ResponseDTO offersInfoView(RequestDTO requestDTO) {
		
		qry="select OPFD.ID ID,OPM.CATEGORY_ID,OCD.CATEGORY_DESC,OPM.SUB_CATEGORY_ID,OSCD.SUB_CATEGORY_DESC  ,"
				+ "OPM.PRODUCT_ID PRODUCT_ID,OPM.PRODUCT_NAME PRODUCT_NAME,OPM.PRODUCT_PRICE PRODUCT_PRICE,  "
				+ "decode(OPFD.OFFER_TYPE,'D','Deal','O','Offer',OPFD.OFFER_TYPE) OFFER_TYPE,  "
				+ "decode(OPFD.TIME_LIMIT,'Y','Yes','N','No',OPFD.TIME_LIMIT) TIME_LIMIT,  "
				+ "to_char(OPFD.END_DATE,'DD-MM-YYYY HH24:MI:SS') END_DATE,  "
				+ "decode(OPFD.DISCOUNT_CASHBACK,'D','Discount','C','Cash Back',OPFD.DISCOUNT_CASHBACK) DISCOUNT_CASHBACK,  "
				+ "OPFD.DIS_CASHBACK_PER DIS_CASHBACK_PER,  decode(OPFD.STATUS,'M','Un-Authorized','A','Approved','R','Rejected',OPFD.STATUS) STATUS,  "
				+ "OPFD.MAKER_ID MAKER_ID,to_char(OPFD.MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS') MAKER_DTTM,  "
				+ "OPFD.CHECKER_ID CHECKER_ID,to_char(OPFD.CHECKER_DTTM,'DD-MM-YYYY HH24:MI:SS') CHECKER_DTTM,  "
				+ "OPFD.COMMENTS  from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PRODUCT_OFFERS_DEALS OPFD  ,"
				+ "ONLINE_CATEGORY_DETAILS OCD,ONLINE_SUB_CATEGORY_DETAILS OSCD  "
				+ "where OPM.PRODUCT_ID=OPFD.PRODUCT_ID and OPM.STATUS='A' and  OPM.CATEGORY_ID=OCD.CATEGORY_ID "
				+ "and OPM.SUB_CATEGORY_ID=OSCD.SUB_CATEGORY_ID  and  OCD.CATEGORY_ID=OSCD.CATEGORY_ID and OPFD.ID=?";
		
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, requestJSON.getString("OFFER_ID"));
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				responseJSON.put("OFFER_ID", rs.getString(1));
				responseJSON.put("CATEGORY_ID", rs.getString(2));
				responseJSON.put("CATEGORY_DESC", rs.getString(3));
				responseJSON.put("SUB_CATEGORY_ID", rs.getString(4));
				responseJSON.put("SUB_CATEGORY_DESC", rs.getString(5));
				responseJSON.put("PRODUCT_ID", rs.getString(6));
				responseJSON.put("PRODUCT_NAME", rs.getString(7));
				responseJSON.put("PRODUCT_PRICE", rs.getString(8));
				responseJSON.put("OFFER_TYPE", rs.getString(9));
				responseJSON.put("TIME_LIMIT", rs.getString(10));
				responseJSON.put("END_DATE", rs.getString(11));
				responseJSON.put("DISCOUNT_CASHBACK", rs.getString(12));
				responseJSON.put("DIS_CASHBACK_PER", rs.getString(13));
				responseJSON.put("STATUS", rs.getString(14));
				responseJSON.put("MAKER_ID", rs.getString(15));
				responseJSON.put("MAKER_DTTM", rs.getString(16));
				responseJSON.put("CHECKER_ID", rs.getString(17));
				responseJSON.put("CHECKER_DTTM", rs.getString(18));
				responseJSON.put("COMMENTS", rs.getString(19));
				
			}
			
			logger.debug("Res JSON  [" + responseJSON + "]");

			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in auth count [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in auth count [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}

	
	
	public ResponseDTO offerApproveOrReject(RequestDTO requestDTO) {
		
		qry="update ONLINE_PRODUCT_OFFERS_DEALS set STATUS=?,COMMENTS=? where ID=? ";
		
		String reason="Success";
		
		try{
			dataMap = new HashMap<String, Object>();
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			
			if(requestJSON.getString("APPROVE_REJECT").equals("R")){
				reason=requestJSON.getString("REASON");
			}
			
			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, requestJSON.getString("APPROVE_REJECT"));
			pstmt.setString(2, reason);
			pstmt.setString(3, requestJSON.getString("OFFER_ID"));
			int cnt = pstmt.executeUpdate();
			
			connection.commit();
			
			if(cnt >0){
				responseJSON.put("STATUS", "Success");
			}else{
				responseDTO.addError("Updation Failed.");
			}
			
			
			logger.debug("Res JSON  [" + responseJSON + "]");

			dataMap.put("RESPONSE_DATA", responseJSON);

			logger.debug("dataMap [" + dataMap + "]");
			responseDTO.setData(dataMap);
			logger.debug("Response DTO [" + responseDTO + "]");
			
		}catch (SQLException e) { 
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in approve [" + e.getMessage() + "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in approve [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			dataMap = null;
			responseJSON = null;
	
		}
		
		return responseDTO;
	}

	public ResponseDTO productdetails(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String qrey = "";




		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			
		
			
			
			
			
			String status=requestJSON.getString("status");
			
			//System.out.println("kailash ::: "+status);
			if(status.equalsIgnoreCase("UN-AUTH")){
				
					qrey = "select PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,CATEGORY_ID,SUB_CATEGORY_ID,decode(STATUS,'M','Un-Authorized','A','Approved','R','Rejected',STATUS) STATUS,MAKER_ID,MAKER_DTTM from ONLINE_PRODUCTS_MASTER where STATUS='M' order by MAKER_DTTM";
	
					getlmtfeePstmt = connection.prepareStatement(qrey);

					getlmtfeeRs = getlmtfeePstmt.executeQuery();

					while (getlmtfeeRs.next()) {

						json = new JSONObject();
						json.put("PRODUCT_ID", getlmtfeeRs.getString(1));
						json.put("PRODUCT_NAME", (getlmtfeeRs.getString(2)).trim());
						json.put("PRODUCT_PRICE", getlmtfeeRs.getString(3));
						json.put("CATEGORY_ID", getlmtfeeRs.getString(4));
						json.put("SUB_CATEGORY_ID", getlmtfeeRs.getString(5));
						json.put("STATUS", getlmtfeeRs.getString(6));
						json.put("MAKER_ID", getlmtfeeRs.getString(7));
						json.put("MAKER_DTTM", getlmtfeeRs.getString(8));
						
						lmtJsonArray.add(json);
					}
			
			}else if(status.equalsIgnoreCase("AUTH")){
				
				qrey = "select PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,CATEGORY_ID,SUB_CATEGORY_ID,decode(STATUS,'M','Un-Authorized','A','Approved','R','Rejected',STATUS) STATUS,MAKER_ID,MAKER_DTTM from ONLINE_PRODUCTS_MASTER where STATUS in ('A') order by MAKER_DTTM";

				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("PRODUCT_ID", getlmtfeeRs.getString(1));
					json.put("PRODUCT_NAME", getlmtfeeRs.getString(2));
					json.put("PRODUCT_PRICE", getlmtfeeRs.getString(3));
					json.put("CATEGORY_ID", getlmtfeeRs.getString(4));
					json.put("SUB_CATEGORY_ID", getlmtfeeRs.getString(5));
					json.put("STATUS", getlmtfeeRs.getString(6));
					json.put("MAKER_ID", getlmtfeeRs.getString(7));
					json.put("MAKER_DTTM", getlmtfeeRs.getString(8));
					
					lmtJsonArray.add(json);
				}
		
		}else if(status.equalsIgnoreCase("REJECT")){
			
			qrey = "select PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,CATEGORY_ID,SUB_CATEGORY_ID,decode(STATUS,'M','Un-Authorized','A','Approved','R','Rejected',STATUS) STATUS,MAKER_ID,MAKER_DTTM from ONLINE_PRODUCTS_MASTER where STATUS='R' order by MAKER_DTTM";

			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PRODUCT_ID", getlmtfeeRs.getString(1));
				json.put("PRODUCT_NAME", getlmtfeeRs.getString(2));
				json.put("PRODUCT_PRICE", getlmtfeeRs.getString(3));
				json.put("CATEGORY_ID", getlmtfeeRs.getString(4));
				json.put("SUB_CATEGORY_ID", getlmtfeeRs.getString(5));
				json.put("STATUS", getlmtfeeRs.getString(6));
				json.put("MAKER_ID", getlmtfeeRs.getString(7));
				json.put("MAKER_DTTM", getlmtfeeRs.getString(8));
				
				lmtJsonArray.add(json);
			}
	
	}else if(status.equalsIgnoreCase("DEAL-UN-AUTH")){
				
				qrey = "select OPFD.ID,OPM.PRODUCT_ID,OPM.PRODUCT_NAME,OPM.PRODUCT_PRICE,OPFD.OFFER_TYPE,  OPFD.DISCOUNT_CASHBACK,decode(OPFD.STATUS,'M','Un-Authorized','A','Approved','R','Rejected',OPFD.STATUS) STATUS,OPFD.MAKER_ID,OPFD.MAKER_DTTM "
						+ "from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PRODUCT_OFFERS_DEALS OPFD  "
						+ "where OPM.PRODUCT_ID=OPFD.PRODUCT_ID and OPM.STATUS='A' and OPFD.STATUS='M' order by OPFD.MAKER_DTTM ";

			
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("ID", getlmtfeeRs.getString(1));
					json.put("PRODUCT_ID", getlmtfeeRs.getString(2));
					json.put("PRODUCT_NAME", getlmtfeeRs.getString(3));
					json.put("PRODUCT_PRICE", getlmtfeeRs.getString(4));
					json.put("OFFER_TYPE", getlmtfeeRs.getString(5));
					json.put("DISCOUNT_CASHBACK", getlmtfeeRs.getString(6));
					json.put("STATUS", getlmtfeeRs.getString(7));
					json.put("MAKER_ID", getlmtfeeRs.getString(8));
					json.put("MAKER_DTTM", getlmtfeeRs.getString(9));
					
					lmtJsonArray.add(json);
				}
			}else if(status.equalsIgnoreCase("DEAL-AUTH")){
				
				qrey = "select OPFD.ID,OPM.PRODUCT_ID,OPM.PRODUCT_NAME,OPM.PRODUCT_PRICE,OPFD.OFFER_TYPE,  OPFD.DISCOUNT_CASHBACK,decode(OPFD.STATUS,'M','Un-Authorized','A','Approved','R','Rejected',OPFD.STATUS) STATUS,OPFD.MAKER_ID,OPFD.MAKER_DTTM "
						+ "from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PRODUCT_OFFERS_DEALS OPFD  "
						+ "where OPM.PRODUCT_ID=OPFD.PRODUCT_ID and OPM.STATUS='A' and OPFD.STATUS='A' order by OPFD.MAKER_DTTM ";

			
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("ID", getlmtfeeRs.getString(1));
					json.put("PRODUCT_ID", getlmtfeeRs.getString(2));
					json.put("PRODUCT_NAME", getlmtfeeRs.getString(3));
					json.put("PRODUCT_PRICE", getlmtfeeRs.getString(4));
					json.put("OFFER_TYPE", getlmtfeeRs.getString(5));
					json.put("DISCOUNT_CASHBACK", getlmtfeeRs.getString(6));
					json.put("STATUS", getlmtfeeRs.getString(7));
					json.put("MAKER_ID", getlmtfeeRs.getString(8));
					json.put("MAKER_DTTM", getlmtfeeRs.getString(9));
					
					lmtJsonArray.add(json);
				}
			}else if(status.equalsIgnoreCase("DEAL-REJECT")){
				
				qrey = "select OPFD.ID,OPM.PRODUCT_ID,OPM.PRODUCT_NAME,OPM.PRODUCT_PRICE,OPFD.OFFER_TYPE,  OPFD.DISCOUNT_CASHBACK,decode(OPFD.STATUS,'M','Un-Authorized','A','Approved','R','Rejected',OPFD.STATUS) STATUS,OPFD.MAKER_ID,OPFD.MAKER_DTTM "
						+ "from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PRODUCT_OFFERS_DEALS OPFD  "
						+ "where OPM.PRODUCT_ID=OPFD.PRODUCT_ID and OPM.STATUS='A' and OPFD.STATUS='R' order by OPFD.MAKER_DTTM ";

			
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("ID", getlmtfeeRs.getString(1));
					json.put("PRODUCT_ID", getlmtfeeRs.getString(2));
					json.put("PRODUCT_NAME", getlmtfeeRs.getString(3));
					json.put("PRODUCT_PRICE", getlmtfeeRs.getString(4));
					json.put("OFFER_TYPE", getlmtfeeRs.getString(5));
					json.put("DISCOUNT_CASHBACK", getlmtfeeRs.getString(6));
					json.put("STATUS", getlmtfeeRs.getString(7));
					json.put("MAKER_ID", getlmtfeeRs.getString(8));
					json.put("MAKER_DTTM", getlmtfeeRs.getString(9));
					
					lmtJsonArray.add(json);
				}
			}else if(status.equalsIgnoreCase("AD-AUTH")){
				
				qrey = "select PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,CATEGORY_ID,SUB_CATEGORY_ID,decode(STATUS,'M','Un-Authorized','A','Approved','R','Rejected','AL','Deactivation',STATUS) STATUS,MAKER_ID,MAKER_DTTM from ONLINE_PRODUCTS_MASTER where STATUS='AL' order by MAKER_DTTM";

				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("PRODUCT_ID", getlmtfeeRs.getString(1));
					json.put("PRODUCT_NAME", getlmtfeeRs.getString(2));
					json.put("PRODUCT_PRICE", getlmtfeeRs.getString(3));
					json.put("CATEGORY_ID", getlmtfeeRs.getString(4));
					json.put("SUB_CATEGORY_ID", getlmtfeeRs.getString(5));
					json.put("STATUS", getlmtfeeRs.getString(6));
					json.put("MAKER_ID", getlmtfeeRs.getString(7));
					json.put("MAKER_DTTM", getlmtfeeRs.getString(8));
					
					lmtJsonArray.add(json);
				}
		
		}
			
			
			

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
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

	
}
