package com.ceva.base.common.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.EncryptTransactionPin;
import com.ceva.base.reports.ExcelReportGeneration;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.user.utils.Main;
import com.ceva.user.utils.QRCodeData;
import com.ceva.util.DBUtils;

public class NewOrganizationDAO {
	
	
	Logger logger = Logger.getLogger(NewBillPaymentDAO.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
	
	public ResponseDTO CategoryDetails(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;

		Connection connection = null;
		
		

		
		
		String productQuerylimit ="select CATEGORY_ID||'-'||CATEGORY_DESC,CATEGORY_DESC from ONLINE_CATEGORY_DETAILS order by CATEGORY_DESC";



		JSONObject json = null;
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			if(connection!=null)
			{
				
				
			
					
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("PRODUCT_CTG", json);
					gettrnsPstmt.close();
					gettrnsRs.close();
					
					gettrnsPstmt = connection.prepareStatement("select  STATE_NAME as sn,STATE_NAME as sns from STATE_MASTER order by sns");

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					json.put("ALL", "ALL");
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("STATE_NAME", json);
					
					
					
				lmtDataMap.put("LIMIT_INFO", resultJson);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO gtOfferDetails(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;

		Connection connection = null;
		
		

		
		
		String productQuerylimit ="";



		JSONObject json = null;
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			if(connection!=null)
			{
				
				
				productQuerylimit ="select (SELECT CATEGORY_DESC FROM ONLINE_CATEGORY_DETAILS WHERE CATEGORY_ID=OPM.CATEGORY_ID),(SELECT SUB_CATEGORY_DESC FROM ONLINE_SUB_CATEGORY_DETAILS WHERE SUB_CATEGORY_ID=OPM.SUB_CATEGORY_ID),PRODUCT_PRICE,MERCHANT_ID,MERCHANT_NAME from ONLINE_PRODUCTS_MASTER OPM WHERE PRODUCT_ID='"+requestJSON.getString("orgname")+"'";
					
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {
						
						json.put("CATEGORY_DESC",gettrnsRs.getString(1));
						json.put("SUB_CATEGORY_DESC",gettrnsRs.getString(2));
						json.put("PRODUCT_PRICE",gettrnsRs.getString(3));
						json.put("MERCHANT_ID",gettrnsRs.getString(4));
						json.put("MERCHANT_NAME",gettrnsRs.getString(5));

					}

					
					
					
				lmtDataMap.put("LIMIT_INFO", json);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO gtProductDetails(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;

		Connection connection = null;
		
		

		
		
		String productQuerylimit ="";



		JSONObject json = null;
		
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");

			logger.debug("connection is null [" + connection == null + "]");
			String defaultstr="data:image/jpeg;base64,";

			if(connection!=null)
			{
				
				
				productQuerylimit ="select MERCHANT_NAME,MERCHANT_ID,(SELECT OCD.CATEGORY_DESC FROM ONLINE_CATEGORY_DETAILS OCD WHERE OCD.CATEGORY_ID=OPM.CATEGORY_ID),(SELECT OSCD.SUB_CATEGORY_DESC FROM ONLINE_SUB_CATEGORY_DETAILS OSCD WHERE OSCD.SUB_CATEGORY_ID=OPM.SUB_CATEGORY_ID),PRODUCT_NAME,PRODUCT_ID,PRODUCT_PRICE,EFFECT_DATE,QUANTITY,PRODUCT_DESC,MAKER_ID,NVL(IMG1,'0'),NVL(IMG2,'0'),NVL(IMG3,'0'),NVL(IMG4,'0'),NVL(MAIN_IMG,'0') from ONLINE_PRODUCTS_MASTER OPM WHERE PRODUCT_ID='"+requestJSON.getString("orgname")+"'";
					
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {
						
						json.put("merchantname",gettrnsRs.getString(1));
						json.put("merchantid",gettrnsRs.getString(2));
						json.put("productctg",gettrnsRs.getString(3));
						json.put("productsubctg",gettrnsRs.getString(4));
						json.put("productname",gettrnsRs.getString(5));
						json.put("productid",gettrnsRs.getString(6));
						json.put("productprice",gettrnsRs.getString(7));
						json.put("producteffect",gettrnsRs.getString(8));
						json.put("quantitystock",gettrnsRs.getString(9));
						json.put("productdesc",gettrnsRs.getString(10));
						json.put("makerId",gettrnsRs.getString(11));
						
						if(!gettrnsRs.getString(12).equalsIgnoreCase("0")){
							json.put("pimg2",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(12)+".jpg")));	
						}else{
							json.put("pimg2","");
						}
						if(!gettrnsRs.getString(13).equalsIgnoreCase("0")){
						json.put("pimg3",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(13)+".jpg")));
						}else{
							json.put("pimg3","");
						}
						if(!gettrnsRs.getString(14).equalsIgnoreCase("0")){
						json.put("pimg4",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(14)+".jpg")));
						}else{
							json.put("pimg4","");
						}
						if(!gettrnsRs.getString(15).equalsIgnoreCase("0")){
						json.put("pimg5",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(15)+".jpg")));
						}else{
							json.put("pimg5","");
						}
						if(!gettrnsRs.getString(16).equalsIgnoreCase("0")){
						json.put("pimg1",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(16)+".jpg")));
						}else{
							json.put("pimg1","");
						}
					}
					gettrnsPstmt.close();
					gettrnsRs.close();
					String assgnstate ="select STATE,NVL(CITY,' ') from ONLINE_PRODUCTS_STATE WHERE PRODUCT_ID='"+requestJSON.getString("orgname")+"'";
					
					gettrnsPstmt = connection.prepareStatement(assgnstate);
					
					gettrnsRs = gettrnsPstmt.executeQuery();
					String strss="";
					while (gettrnsRs.next()) {
						strss=strss+""+gettrnsRs.getString(1)+","+gettrnsRs.getString(2)+"#";	
							
					}
					json.put("ASS_STATE", strss);
					
				lmtDataMap.put("LIMIT_INFO", json);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

		
	public ResponseDTO gtProductDetailsActivate(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;
		PreparedStatement pstmt = null;

		Connection connection = null;
		
		

		
		
		String productQuerylimit ="";



		JSONObject json = null;
		
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");

			logger.debug("connection is null [" + connection == null + "]");
			String defaultstr="data:image/jpeg;base64,";

			if(connection!=null)
			{
				
				
				productQuerylimit ="select STATUS from ONLINE_PRODUCTS_MASTER OPM WHERE PRODUCT_ID='"+requestJSON.getString("orgname")+"'";
					
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {
						
						json.put("status",gettrnsRs.getString(1));
						
						
						
					}
					gettrnsPstmt.close();
					gettrnsRs.close();
					
					if(json.get("status").equals("A")){
						json.put("status","De-Activate");
						
						pstmt = connection.prepareStatement("UPDATE ONLINE_PRODUCTS_MASTER SET STATUS='AL' WHERE PRODUCT_ID=?");
						pstmt.setString(1, requestJSON.getString("orgname"));
						
					}else{
						pstmt = connection.prepareStatement("UPDATE ONLINE_PRODUCTS_MASTER SET STATUS='AA' WHERE PRODUCT_ID=?");
						pstmt.setString(1, requestJSON.getString("orgname"));
						json.put("status","Activate");	
					}
					pstmt.executeUpdate();
					connection.commit();
					
				lmtDataMap.put("LIMIT_INFO", json);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}
				
				if (pstmt != null) {
					pstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

		
	
	public ResponseDTO fetchBillerDataTableDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [NewBillPaymentDAO][fetchBillerDataTableDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONObject terminalJSON = null;

		JSONArray merchantJsonArray = null;

		//ArrayList<String> merchatArray = null;
		ArrayList<String> storeArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;

		String merchantQry = "Select ORGANIGATION_ID,ACCOUNT_NAME,MERCHANT_TYPE,MAKER_ID,to_char(MAKER_DT,'DD-MM-YYYY HH24:MI:SS') from ORG_MASTER order by ORGANIGATION_ID";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJsonArray = new JSONArray();
			terminalJSON = new JSONObject();

			//merchatArray = new ArrayList<String>();
			storeArray = new ArrayList<String>();

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();
			while (merchantRS.next()) {
				json.put("ORGANIGATION_ID",merchantRS.getString(1));
				json.put("ORGANIGATION_NAME",merchantRS.getString(2));
				json.put("MERCHANT_TYPE",merchantRS.getString(3));
				json.put("MAKER_ID",merchantRS.getString(4));
				json.put("MAKER_DT",merchantRS.getString(5));
				merchantJsonArray.add(json);
				json.clear();
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			resultJson.put("BILLER_LIST", merchantJsonArray);

			merchantJsonArray.clear();

			/*for (int i = 0; i < merchatArray.size(); i++) {
				String storeQry = "Select BILLER_ID,ACCOUNT_NUMBER,ACCOUNT_NAME,MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS')"
						+"from BILLER_ACCOUNT_MASTER where BILLER_ID=?  order by ACCOUNT_NUMBER";

				merchantPstmt = connection.prepareStatement(storeQry);
				merchantPstmt.setString(1, merchatArray.get(i));
				merchantRS = merchantPstmt.executeQuery();
				json.clear();

				while (merchantRS.next()) {
					json.put("BILLER_ID",
							merchantRS.getString(1));
					json.put("ACCOUNT_NUMBER",
							merchantRS.getString(2));
					json.put("ACCOUNT_NAME",
							merchantRS.getString(3));
					json.put("MAKER_ID",
							merchantRS.getString(4));
					json.put("MAKER_DATE",
							merchantRS.getString(5));
					storeArray.add(merchantRS.getString(1));
					merchantJsonArray.add(json);
					json.clear();
				}

				if (merchatArray != null && merchatArray.size() > 0) {
					resultJson.put(merchatArray.get(i) + "_ACCOUNTS",
							merchantJsonArray);
					merchantJsonArray.clear();
				}
				DBUtils.closePreparedStatement(merchantPstmt);
				DBUtils.closeResultSet(merchantRS);
			}*/

			json.clear();
			
			merchantMap.put("BILLER_LIST", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in fetchBillerDataTableDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			resultJson = null;
			merchantJsonArray = null;

			terminalJSON = null;

			//merchatArray = null;
			storeArray = null;
		}
		return responseDTO;
	}

	
	public ResponseDTO fetchPaymentMerchant(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [NewBillPaymentDAO][fetchBillerDataTableDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONObject terminalJSON = null;

		JSONArray merchantJsonArray = null;

		//ArrayList<String> merchatArray = null;
		ArrayList<String> storeArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;

		String merchantQry = "Select ORGANIGATION_ID,ACCOUNT_NAME,MERCHANT_TYPE,MAKER_ID,to_char(MAKER_DT,'DD-MM-YYYY HH24:MI:SS'),QR_CODE from ORG_MASTER WHERE MERCHANT_TYPE='Payment Merchant' order by ORGANIGATION_ID";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJsonArray = new JSONArray();
			terminalJSON = new JSONObject();

			//merchatArray = new ArrayList<String>();
			storeArray = new ArrayList<String>();

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			json = new JSONObject();
			while (merchantRS.next()) {
				json.put("ORGANIGATION_ID",merchantRS.getString(1));
				json.put("ORGANIGATION_NAME",merchantRS.getString(2));
				json.put("MERCHANT_TYPE",merchantRS.getString(3));
				json.put("MAKER_ID",merchantRS.getString(4));
				json.put("MAKER_DT",merchantRS.getString(5));
				json.put("QR_CODE",merchantRS.getString(6));
				merchantJsonArray.add(json);
				json.clear();
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			resultJson.put("BILLER_LIST", merchantJsonArray);

			merchantJsonArray.clear();

			/*for (int i = 0; i < merchatArray.size(); i++) {
				String storeQry = "Select BILLER_ID,ACCOUNT_NUMBER,ACCOUNT_NAME,MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS')"
						+"from BILLER_ACCOUNT_MASTER where BILLER_ID=?  order by ACCOUNT_NUMBER";

				merchantPstmt = connection.prepareStatement(storeQry);
				merchantPstmt.setString(1, merchatArray.get(i));
				merchantRS = merchantPstmt.executeQuery();
				json.clear();

				while (merchantRS.next()) {
					json.put("BILLER_ID",
							merchantRS.getString(1));
					json.put("ACCOUNT_NUMBER",
							merchantRS.getString(2));
					json.put("ACCOUNT_NAME",
							merchantRS.getString(3));
					json.put("MAKER_ID",
							merchantRS.getString(4));
					json.put("MAKER_DATE",
							merchantRS.getString(5));
					storeArray.add(merchantRS.getString(1));
					merchantJsonArray.add(json);
					json.clear();
				}

				if (merchatArray != null && merchatArray.size() > 0) {
					resultJson.put(merchatArray.get(i) + "_ACCOUNTS",
							merchantJsonArray);
					merchantJsonArray.clear();
				}
				DBUtils.closePreparedStatement(merchantPstmt);
				DBUtils.closeResultSet(merchantRS);
			}*/

			json.clear();
			
			merchantMap.put("BILLER_LIST", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in fetchBillerDataTableDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			resultJson = null;
			merchantJsonArray = null;

			terminalJSON = null;

			//merchatArray = null;
			storeArray = null;
		}
		return responseDTO;
	}

	
	
	public ResponseDTO productcreation(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][productcreation] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		String state=null;
		String city=null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			String defaultstr="data:image/jpeg;base64,";
			
			for(int i=1;i<=5;i++){
				if(!(requestJSON.getString("pimg"+i)).equalsIgnoreCase("")){
					Main.Imagesave((requestJSON.getString("imgdata"+i)).replaceFirst(defaultstr, ""), requestJSON.getString("pimg"+i)+".jpg",requestJSON.getString("productid"));
				}
			}
			

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			
			connection.setAutoCommit(false);
			
			/*pstmt1 = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),NVL(CLUSTER_ID,' ')  from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+requestJSON.getString("makerId")+"')");

			rs = pstmt1.executeQuery();
			while (rs.next()) {
				
				pstmt = connection.prepareStatement("insert into auth_pending ( ref_num,maker_id,maker_dttm,auth_code,status,auth_flag,MAKER_BRCODE,ACTION,ACTION_DETAILS) values('"+rs.getString(1)+"','"+requestJSON.getString("makerId")+"',sysdate,'LMTMODAUTH','P','LM','"+rs.getString(2)+"','MAKER','product Code "+requestJSON.getString("productname")+" Created')");
				pstmt.executeUpdate();
				pstmt.close();*/
			
					pstmt = connection.prepareStatement("INSERT INTO ONLINE_PRODUCTS_MASTER(MERCHANT_NAME,MERCHANT_ID,CATEGORY_ID,SUB_CATEGORY_ID,PRODUCT_NAME,PRODUCT_ID,PRODUCT_PRICE,EFFECT_DATE,QUANTITY,PRODUCT_DESC,MAKER_ID,MAKER_DTTM,STATUS,IMG1,IMG2,IMG3,IMG4,MAIN_IMG) VALUES(?,?,?,?,?,?,?,?,?,?,?,sysdate,'M',?,?,?,?,?)");
					
					pstmt.setString(1, requestJSON.getString("merchantname"));
					pstmt.setString(2, requestJSON.getString("merchantid"));
					pstmt.setString(3, (requestJSON.getString("productctg")).split("-")[0]);
					pstmt.setString(4, (requestJSON.getString("productsubctg")).split("-")[0]);
					pstmt.setString(5, requestJSON.getString("productname"));
					pstmt.setString(6, requestJSON.getString("productid"));
					pstmt.setString(7, requestJSON.getString("productprice"));
					pstmt.setString(8, requestJSON.getString("producteffect"));
					pstmt.setString(9, requestJSON.getString("quantitystock"));
					pstmt.setString(10, requestJSON.getString("productdesc"));
					pstmt.setString(11, requestJSON.getString("makerId"));
					
					pstmt.setString(12, requestJSON.getString("pimg2"));
					pstmt.setString(13, requestJSON.getString("pimg3"));
					pstmt.setString(14, requestJSON.getString("pimg4"));
					pstmt.setString(15, requestJSON.getString("pimg5"));
					pstmt.setString(16, requestJSON.getString("pimg1"));
					
					
					pstmt.executeUpdate();
					
					pstmt.close();
					
					String branchQry="insert into ONLINE_PRODUCTS_STATE(PRODUCT_ID,STATE,CITY,MAKER_ID,MAKER_DT) " +
							" values(?,?,?,?,sysdate) ";
		
					pstmt=connection.prepareStatement(branchQry);
					
					JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
					
		
					for (int i = 0; i < branchArr.size(); i++) {
						JSONObject reqData = branchArr.getJSONObject(i);
						state = reqData.getString("state");
						city = reqData.getString("city");
						
						pstmt.setString(1,requestJSON.getString("productid"));
						pstmt.setString(2,state);
						pstmt.setString(3,city);
						pstmt.setString(4,requestJSON.getString("makerId"));
						
						
						
						pstmt.addBatch();
					}
					
					pstmt.executeBatch(); // insert remaining records 
					pstmt.close();
			
			//}
			connection.commit();
			

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO ServiceDetails(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [AgentDAO][ServiceDetails]");

		
		
		HashMap<String, Object> viewDataMap = null;
		Connection connection = null;

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();
			viewDataMap = new HashMap<String, Object>();
			
			 JSONObject json = new JSONObject();
			 JSONObject jsonlmt = new JSONObject();
			

			 
					
					String serviceQry = "select SERVICE_CODE||'-'||SERVICE_NAME,SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER ORDER BY SERVICE_NAME";
					servicePstmt = connection.prepareStatement(serviceQry);
					serviceRS = servicePstmt.executeQuery();
					while (serviceRS.next()) {
							jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
					}
					json.put("SERVICE_MASTER", jsonlmt);
					jsonlmt.clear();
					
						
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[AgentDAO][ServiceDetails] SQLException in ServiceDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("[AgentDAO][ServiceDetails] Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			viewDataMap = null;
			
			
		}

		return responseDTO;
	}	

	
	
	public ResponseDTO registerBillerAccountDetails(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][registerBillerAccountDetails] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection  + "]");
			callable = connection.prepareCall("{call PAYBILLPKG.registerBillerAccountProc(?,?,?)}");

			callable.setString(1, requestJSON.getString("BILLER_ACCT_DATA"));
			callable.setString(2, requestJSON.getString("makerId"));
			callable.registerOutParameter(3, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(3).indexOf("SUCCESS") == -1) {
				responseDTO.addError("Internal Error Occured.");
			} else {

			}

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO viewBillerAccountDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [NewBillPaymentDAO][viewBillerAccountDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String merchantQry = "Select BILLER_CATEGORY_ID,BILLER_ID,BILLER_NAME,SERVICE_CODE,ACCOUNT_NUMBER,ACCOUNT_NAME"
				+ " from BILLER_ACCOUNT_MASTER where BILLER_ID=?";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			
			requestJSON = requestDTO.getRequestJSON();
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1, requestJSON.getString("billerCode"));
			
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				resultJson.put("BILLER_CATEGORY_ID",merchantRS.getString(1));
				resultJson.put("BILLER_ID",merchantRS.getString(2));
				resultJson.put("BILLER_NAME",merchantRS.getString(3));
				resultJson.put("SERVICE_CODE",merchantRS.getString(4));
				resultJson.put("ACCOUNT_NUMBER",merchantRS.getString(5));
				resultJson.put("ACCOUNT_NAME", merchantRS.getString(6));
				
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap.put("BILLER_INFO", resultJson);
			logger.debug("resultJson:::: [" + resultJson + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in viewBillerAccountDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			resultJson = null;
		}
		return responseDTO;
	}

	
	
	public ResponseDTO fetchBillerDetails(RequestDTO requestDTO){
		
		logger.debug("[NewBillPaymentDAO][fetchBillerDetails]..");
		
		HashMap<String, Object> billerMap = null;

		Connection connection = null;
		PreparedStatement billerPstmt = null;
		ResultSet billerRS = null;

		String billerQry = "";
		
		try{
			
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			JSONArray jsonArray = null;
			JSONObject jsonObject = null;
			logger.debug("[NewBillPaymentDAO][fetchBillerDetails][requestJSON..."+requestJSON+"]");
			
			billerMap = new HashMap<String, Object>();

			/*billerQry = "select BR.BILLER_ID,BR.NAME,BAM.ACCOUNT_NUMBER,BAM.ACCOUNT_NAME"
					+ " from BILLER_REGISTRATION BR,BILLER_ACCOUNT_MASTER BAM "
					+ "where BR.BILLER_ID = BAM.BILLER_ID and BR.BILLER_ID =trim(?) and BAM.ACCOUNT_NUMBER =trim(?)";*/
			
			billerQry = "select BR.BILLER_ID,BR.NAME "
					+ " from BILLER_REGISTRATION BR order by BR.BILLER_ID";

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");

			billerPstmt = connection.prepareStatement(billerQry);
			
			billerRS = billerPstmt.executeQuery();

			jsonArray = new JSONArray();
			
			while (billerRS.next()) {
				jsonObject = new JSONObject();
				jsonObject.put("val", billerRS.getString(1));
				jsonObject.put("key", billerRS.getString(2));
				jsonArray.add(jsonObject);
			}
			responseJSON.put("BILLER_DATA", jsonArray);
			
			jsonArray.clear();
			jsonArray = null;
			billerRS.close();
			billerPstmt.close();
			
			billerMap.put("BILLER_INFO", responseJSON);

			logger.debug("BillerMap [" + billerMap + "]");
			responseDTO.setData(billerMap);
			logger.debug("Response DTO [" + responseDTO + "]");			
			
		}catch(SQLException sqlException){
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in BillPaymentCreateDetails["+ sqlException.getMessage() + "]");
		}catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in BillPaymentCreateDetails ["+ e.getMessage() + "]");
		}finally{
				DBUtils.closeResultSet(billerRS);
				DBUtils.closePreparedStatement(billerPstmt);
				DBUtils.closeConnection(connection);
				responseJSON = null;
				billerQry = null;
				billerMap = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO billPayVerifyPin(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][fetchBillerDetails]... ");

		Connection connection = null;
		PreparedStatement billerPstmt = null;
		ResultSet billerRS = null;

		int resCount = 0;
		String cryptedPassword = "";
		String key1 = "";

		String billerQry = "";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			key1 = "97206B46CE46376894703ECE161F31F2";
			logger.debug("Before Enryption Value is  ["
					+ requestJSON.getString(CevaCommonConstants.PIN) + "]");

			try {
				cryptedPassword = EncryptTransactionPin.encrypt(key1,
						requestJSON.getString(CevaCommonConstants.PIN), 'F');

			} catch (Exception e) {
				logger.debug("Exception in encrypting password cryptedPassword ["
						+ cryptedPassword + "] message[" + e.getMessage() + "]");
				cryptedPassword = "";
			}

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			billerQry = "Select COUNT(*) from USER_LOGIN_CREDENTIALS  where PIN=? and LOGIN_USER_ID=trim(?)";
			billerPstmt = connection.prepareStatement(billerQry);
			billerPstmt.setString(1, cryptedPassword);
			billerPstmt.setString(2, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			billerRS = billerPstmt.executeQuery();

			if (billerRS.next()) {
				resCount = billerRS.getInt(1);
			}

			if (resCount == 1) {
				responseDTO.addMessages("Pin Verification Success");
			} else {
				responseDTO.addError("Pin Verification Failed");
			}

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in BillPayVerifyPin [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in BillPayVerifyPin [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeResultSet(billerRS);
			DBUtils.closePreparedStatement(billerPstmt);
			DBUtils.closeConnection(connection);
			cryptedPassword = null;
			key1 = null;
			billerQry = null;
		}

		return responseDTO;
	}

	
	public ResponseDTO payBillInsertDetails(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][payBillInsertDetails] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			callable = connection.prepareCall("{call PAYBILLPKG.paybillInsertProc(?,?,?,?,?,?,?,?,?,?,?)}");

			callable.setString(1, requestJSON.getString("billerCode"));
			callable.setString(2, requestJSON.getString("accountNo"));
			callable.setString(3, requestJSON.getString("accountName"));
			callable.setString(4, requestJSON.getString("customerName"));
			callable.setString(5, requestJSON.getString("telephone"));
			callable.setString(6, requestJSON.getString("modeOfPayment"));
			callable.setString(7, requestJSON.getString("amount"));
			callable.setString(8, requestJSON.getString("narration"));
			callable.setString(9, requestJSON.getString("makerId"));
			callable.registerOutParameter(10, OracleTypes.VARCHAR);
			callable.registerOutParameter(11, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(10).indexOf("SUCCESS") == -1) {
				responseDTO.addError(callable.getString(11));
			} else {
				responseDTO.addMessages(callable.getString(11));
			}

			requestJSON.put("Message", callable.getString(11));
			responseJSON = requestJSON;
			billerDataMap.put("BILLPAY", responseJSON);
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}


	
	
	
	public ResponseDTO viewBillerDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [NewBillPaymentDAO][viewBillerDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String merchantQry = "Select BILLER_ID,NAME,ABBREVATION,COMM_TYPE,AMOUNT/100,MAKER_ID,"
				+ "to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS'),AGENCY_TYPE,ADDRESS,TELEPHONE,CONTACT_PERSON"
				+ ",EMAIL from BILLER_REGISTRATION where BILLER_ID=?";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			
			requestJSON = requestDTO.getRequestJSON();
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1, requestJSON.getString("billerCode"));
			
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				resultJson.put("BILLER_ID",merchantRS.getString(1));
				resultJson.put("NAME",merchantRS.getString(2));
				resultJson.put("ABBREVATION",merchantRS.getString(3));
				resultJson.put("COMM_TYPE",merchantRS.getString(4));
				resultJson.put("AMOUNT",merchantRS.getString(5));
				resultJson.put("MAKER_ID", merchantRS.getString(6));
				resultJson.put("MAKER_DATE",merchantRS.getString(7));
				resultJson.put("AGENCY_TYPE",merchantRS.getString(8));
				resultJson.put("ADDRESS",merchantRS.getString(9));
				resultJson.put("TELEPHONE",merchantRS.getString(10));
				resultJson.put("CONTACT_PERSON",merchantRS.getString(11));
				resultJson.put("EMAIL",merchantRS.getString(12));
				
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap.put("BILLER_INFO", resultJson);
			logger.debug("resultJson:::: [" + resultJson + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in viewBillerDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			resultJson = null;
		}
		return responseDTO;
	}


	public ResponseDTO updateBillerDetails(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][updateBillerDetails] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		//JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			//billerBean = requestJSON.getJSONObject("billerBean");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			callable = connection.prepareCall("{call PAYBILLPKG.updateBillerProc(?,?,?,?,?)}");

			callable.setString(1, requestJSON.getString("abbreviation"));
			callable.setString(2, requestJSON.getString("billerCode"));
			callable.setString(3, requestJSON.getString("name"));
			/*callable.setString(4, requestJSON.getString("agencyType"));
			callable.setString(5, requestJSON.getString("address"));
			callable.setString(6, requestJSON.getString("telephone"));
			callable.setString(7, requestJSON.getString("contactPerson"));
			callable.setString(8, requestJSON.getString("email"));
			callable.setString(9, requestJSON.getString("commissionType"));
			callable.setString(10, requestJSON.getString("amount"));
			callable.setString(11, requestJSON.getString("rate"));*/
			callable.setString(4, requestJSON.getString("makerId"));
			callable.registerOutParameter(5, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(5).indexOf("SUCCESS") == -1) {
				responseDTO.addError("Internal Error Occured.");
			} else {

			}

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}

	
	public ResponseDTO billerAccountDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [NewBillPaymentDAO][billerAccountDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject json = null;

		String merchantQry = "select BR.BILLER_ID,BR.NAME,BAM.ACCOUNT_NUMBER,BAM.ACCOUNT_NAME,BAM.ACCOUNT_TYPE,BAM.BILLER_CODE from BILLER_REGISTRATION BR,BILLER_ACCOUNT_MASTER BAM where BR.BILLER_ID = BAM.BILLER_ID and BAM.BILLER_ID=? and BAM.ACCOUNT_NUMBER=?";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			
			requestJSON = requestDTO.getRequestJSON();
			
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1, requestJSON.getString("billerId"));
			merchantPstmt.setString(2, requestJSON.getString("accountNumber"));
			
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				resultJson.put("BILLER_ID",merchantRS.getString(1));
				resultJson.put("NAME",merchantRS.getString(2));
				resultJson.put("ACCOUNT_NUMBER",merchantRS.getString(3));
				resultJson.put("ACCOUNT_NAME",merchantRS.getString(4));
				resultJson.put("ACCOUNT_TYPE",merchantRS.getString(5));
				resultJson.put("BILLER_CODE",merchantRS.getString(6));
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			resultJson.put("BILLER_ACCT_INFO", resultJson);			
			merchantMap.put("BILLER_ACCT_INFO", resultJson);
			logger.debug("Result Json [" + resultJson + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in billerAccountDetails ["+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeConnection(connection);

			merchantMap = null;
			resultJson = null;		}
		return responseDTO;
	}

	
	
	public ResponseDTO modifyBillerAccountDetails(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][modifyBillerAccountDetails] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		CallableStatement callable = null;

		JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			callable = connection.prepareCall("{call PAYBILLPKG.updateBillerAccountProc(?,?,?,?,?)}");

			callable.setString(1, requestJSON.getString("billerId"));
			callable.setString(2, requestJSON.getString("accountNumber"));
			callable.setString(3, requestJSON.getString("accountName"));
			callable.setString(4, requestJSON.getString("accountType"));
			callable.registerOutParameter(5, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(5).indexOf("SUCCESS") == -1) {
				responseDTO.addError("Internal Error Occured.");
			} else {

			}
			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}


	
	public ResponseDTO fetchBillerAccountDetails(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerAccountDetails].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    JSONArray merchantJsonArray = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select PRODUCT_NAME,PRODUCT_ID,PRODUCT_PRICE,MAKER_ID,MAKER_DTTM "
				+"from ONLINE_PRODUCTS_MASTER where MERCHANT_ID=? AND STATUS='A' order by PRODUCT_ID";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      merchantJsonArray = new JSONArray();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantPstmt.setString(1, this.requestJSON.getString("billerId"));
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        json = new JSONObject();
	        json.put("PRODUCT_NAME", 
	          merchantRS.getString(1));
	        json.put("PRODUCT_ID", 
	          merchantRS.getString(2));
	        json.put("PRODUCT_PRICE", 
	          merchantRS.getString(3));
	        json.put("MAKER_ID", 
	          merchantRS.getString(4));
	        json.put("MAKER_DTTM", 
	          merchantRS.getString(5));
	        merchantJsonArray.add(json);
	      }
	      /*DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);*/
	      resultJson.put("BILLER_ACCT_LIST", merchantJsonArray);
	      merchantMap.put("BILLER_ACCT_LIST", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerAccountDetails [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	      merchantJsonArray = null;
	    }
	    return this.responseDTO;
	  }

	public ResponseDTO fetchofferdetails(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerAccountDetails].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    JSONArray merchantJsonArray = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select PRODUCT_ID,OFFER_TYPE,TIME_LIMIT,NVL(to_char(END_DATE,'dd-mm-yyyy'),' '),DISCOUNT_CASHBACK,DIS_CASHBACK_PER,MAKER_ID,MAKER_DTTM "
				+"from ONLINE_PRODUCT_OFFERS_DEALS where PRODUCT_ID=? AND STATUS='A' order by ID";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      merchantJsonArray = new JSONArray();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantPstmt.setString(1, this.requestJSON.getString("billerId"));
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        json = new JSONObject();
	        json.put("PRODUCT_ID", 
	          merchantRS.getString(1));
	        json.put("OFFER_TYPE", 
	          merchantRS.getString(2));
	        json.put("TIME_LIMIT", 
	          merchantRS.getString(3));
	        json.put("END_DATE", 
	          merchantRS.getString(4));
	        json.put("DISCOUNT_CASHBACK", 
	  	          merchantRS.getString(5));
	        json.put("DIS_CASHBACK_PER", 
	  	          merchantRS.getString(6));
	        json.put("MAKER_ID", 
		  	          merchantRS.getString(7));
	        json.put("MAKER_DTTM", 
		  	          merchantRS.getString(8));
	        merchantJsonArray.add(json);
	      }
	      /*DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);*/
	      resultJson.put("BILLER_ACCT_LIST", merchantJsonArray);
	      merchantMap.put("BILLER_ACCT_LIST", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerAccountDetails [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	      merchantJsonArray = null;
	    }
	    return this.responseDTO;
	  }

	
	
	public ResponseDTO fetchBillerCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select lpad(COUNT(*),5,'0') FROM BILLER_REGISTRATION";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("BILLER_COUNT",   merchantRS.getString(1));
	      }
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	public ResponseDTO lifestyleCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "select (Select 'PRD'||lpad(COUNT(*)+1,4,'0') FROM ONLINE_PRODUCTS_MASTER)||LIFE_SEQ.nextval from dual";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("BILLER_COUNT",   merchantRS.getString(1));
	      }
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	
	public ResponseDTO superAgentCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select 'SA'||lpad(AGENT_SEQ.nextval,6,'0') FROM DUAL";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("BILLER_COUNT",   merchantRS.getString(1));
	      }
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	public ResponseDTO fraudCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select 'FRD'||lpad(COUNT(*),5,'0') FROM FRAUD_MASTER";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("BILLER_COUNT",   merchantRS.getString(1));
	      }
	      /*DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);*/
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	public ResponseDTO lifestyleAllCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry1 = "(Select count(*) FROM ONLINE_PURCHASE_MASTER WHERE DELIVERY_STATUS='OP')";
	    String merchantQry2 = "(Select count(*) FROM ONLINE_PURCHASE_MASTER WHERE DELIVERY_STATUS='DFP')";
	    String merchantQry3 = "(Select count(*) FROM ONLINE_PURCHASE_MASTER WHERE DELIVERY_STATUS='DSP')";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement("SELECT "+merchantQry1+","+merchantQry2+","+merchantQry3+" from dual");
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("BILLER_COUNT1",   merchantRS.getString(1));
	        resultJson.put("BILLER_COUNT2",   merchantRS.getString(2));
	        resultJson.put("BILLER_COUNT3",   merchantRS.getString(3));
	      }
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	
	
	public ResponseDTO insertmerchant(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][insertmerchant] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		String branchid="";

		//JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			//billerBean = requestJSON.getJSONObject("billerBean");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			
			
				servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),CLUSTER_ID from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where UI.COMMON_ID=ULC.COMMON_ID AND ULC.LOGIN_USER_ID='"+requestJSON.getString("makerId")+"'");
				serviceRS = servicePstmt.executeQuery();
				
				while(serviceRS.next())
				{
					 auth_seq=serviceRS.getString(1);
					 branchid=serviceRS.getString(2);
				}			
			
			connection.setAutoCommit(false);
			if((requestJSON.getString("managertype")).equalsIgnoreCase("Payment Merchant")){
				pstmt = connection.prepareStatement("INSERT INTO ORG_MASTER_TEMP(ACCOUNT_NUMBER,ACCOUNT_NAME,ACCOUNT_CURRENCY_CODE,BRANCH_CODE,EMAIL,MOBILE,ORGANIGATION_NAME,ORGANIGATION_ID,status,merchant_type,Date_Of_Birth,Gender,ID_Type,ID_NUMBER,Telephone_Number,Nationality,Address_Line_one,Address_Line_two,Local_Government,State,Country,City,MAKER_ID,MAKER_DT,QR_CODE,REF_NUM,LONGITUDE,LATITUDE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,QR_CODE_SEQ.nextval||'"+QRCodeData.gentag(requestJSON.getString("accountNumbers"))+"'),?,?,?");
	
			}else{
				pstmt = connection.prepareStatement("INSERT INTO ORG_MASTER_TEMP(ACCOUNT_NUMBER,ACCOUNT_NAME,ACCOUNT_CURRENCY_CODE,BRANCH_CODE,EMAIL,MOBILE,ORGANIGATION_NAME,ORGANIGATION_ID,status,merchant_type,Date_Of_Birth,Gender,ID_Type,ID_NUMBER,Telephone_Number,Nationality,Address_Line_one,Address_Line_two,Local_Government,State,Country,City,MAKER_ID,MAKER_DT,REF_NUM,LONGITUDE,LATITUDE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)");

			}
			pstmt.setString(1, requestJSON.getString("accountNumbers"));
			pstmt.setString(2, requestJSON.getString("accountName"));
			pstmt.setString(3, requestJSON.getString("acctCurrCode"));
			pstmt.setString(4, requestJSON.getString("branchCode"));
			pstmt.setString(5, requestJSON.getString("email"));
			pstmt.setString(6, requestJSON.getString("mobile"));
			pstmt.setString(7, requestJSON.getString("managerName"));
			pstmt.setString(8, requestJSON.getString("managerId"));
			pstmt.setString(9, "C");
			pstmt.setString(10, requestJSON.getString("managertype"));
			pstmt.setString(11, requestJSON.getString("dob"));
			pstmt.setString(12, requestJSON.getString("gender"));
			pstmt.setString(13, requestJSON.getString("IDType"));
			pstmt.setString(14, requestJSON.getString("IDNumber"));
			pstmt.setString(15, requestJSON.getString("telephoneNumber2"));
			pstmt.setString(16, requestJSON.getString("nationality"));
			pstmt.setString(17, requestJSON.getString("addressLine1"));
			pstmt.setString(18, requestJSON.getString("addressLine2"));
			pstmt.setString(19, requestJSON.getString("localGovernment"));
			pstmt.setString(20, requestJSON.getString("state"));
			pstmt.setString(21, requestJSON.getString("country"));
			pstmt.setString(22, requestJSON.getString("city"));
			pstmt.setString(23, requestJSON.getString("makerId"));
			pstmt.setString(24, auth_seq);
			pstmt.setString(25, requestJSON.getString("langitude"));
			pstmt.setString(26, requestJSON.getString("latitude"));
			
			pstmt.executeUpdate();
			
			
			pstmt.close();
			pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM,MAKER_BRCODE,ACTION,ACTION_DETAILS) values(?,sysdate,'MERNEWAUTH','P','MM',?,?,'MAKER',?)");
			
			
			pstmt.setString(1, requestJSON.getString("makerId"));
			pstmt.setString(2, auth_seq);
			pstmt.setString(3, branchid);
			pstmt.setString(4, "Merchant Create for Account "+requestJSON.getString("accountNumbers"));
			pstmt.executeUpdate();
			
			connection.commit();
			

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO insertMerchantOffer(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][insertMerchantOffer] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		PreparedStatement pstmt = null;

		//JSONObject billerBean = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			//billerBean = requestJSON.getJSONObject("billerBean");

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			
			
			pstmt = connection.prepareStatement("INSERT INTO ONLINE_PRODUCT_OFFERS_DEALS(ID,PRODUCT_ID,OFFER_TYPE,TIME_LIMIT,FROM_DT,END_DATE,DISCOUNT_CASHBACK,DIS_CASHBACK_PER,MAKER_ID,MAKER_DTTM,STATUS,MERCHANT_ID,MERCHANT_NAME) VALUES('POD'||PRD_OFFER_SEQ.nextval,?,?,?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?,?,sysdate,'M',?,?)");

			
			pstmt.setString(1, requestJSON.getString("productid"));
			pstmt.setString(2, requestJSON.getString("offertype"));
			pstmt.setString(3, requestJSON.getString("timelimit"));
			if(requestJSON.getString("timelimit").equalsIgnoreCase("Yes")){
				pstmt.setString(4, requestJSON.getString("fromdate"));
				pstmt.setString(5, requestJSON.getString("enddate"));	
			}else{
				pstmt.setString(4, "");
				pstmt.setString(5, "");	
			}
			
			pstmt.setString(6, requestJSON.getString("distype"));
			pstmt.setString(7, requestJSON.getString("doffer"));
			pstmt.setString(8, requestJSON.getString("makerId"));			
			pstmt.setString(9, requestJSON.getString("merchantid"));
			pstmt.setString(10, requestJSON.getString("merchantname"));

			
			pstmt.executeUpdate();
			connection.commit();
			

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO paymentmcode(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [AgentDAO][ServiceDetails]");

		
		
		HashMap<String, Object> viewDataMap = null;
		Connection connection = null;

		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();
			viewDataMap = new HashMap<String, Object>();
			String qrcode=requestJSON.getString("qrcode");
			 JSONObject json = new JSONObject();
			
			 QRCodeData qrd=new QRCodeData();
			 
					
						json.put("SERVICE_CODE", qrd.gtQrcodeData(qrcode));
						json.put("SERVICE_NUMBER", qrcode);
				
						
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[AgentDAO][ServiceDetails] SQLException in ServiceDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("[AgentDAO][ServiceDetails] Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			viewDataMap = null;
			
			
		}

		return responseDTO;
	}	

	public ResponseDTO lifestyletrackingdetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String trans=requestJSON.getString("trans");
			String application=requestJSON.getString("application");
			String fname=requestJSON.getString("fname");
			
			String fromdate=requestJSON.getString("fromdate");
			String todate=requestJSON.getString("todate");
			
			if(trans.equalsIgnoreCase("Sales")){
				trans="'OP'";	
			}else if(trans.equalsIgnoreCase("Drafting")){
				trans="'DFP'";	
			}else if(trans.equalsIgnoreCase("Dispatched")){
				trans="'DSP'";
			}else if(trans.equalsIgnoreCase("Delivered")){
				trans="'DLV'";
			}else if(trans.equalsIgnoreCase("Search")){
				trans="'OP','DFP','DSP','DLV'";
			}
			
		
				
			
			
				if(application.equalsIgnoreCase("MERCHANTID")){
					qrey = "select TXN_REF_NO,ACC_NO,USER_NAME,FINAL_AMOUNT,MERCHANT_ID,PRODUCT_ID,PRODUCT_PRICE,DISCOUNT_PRICE,TXN_DATE,decode(DELIVERY_STATUS,'OP','Sales Order','DFP','Order Drafting','DSP','Dispatched','DLV','Delivered') "
							+ "from ONLINE_PURCHASE_MASTER where  MERCHANT_ID='"+fname+"' AND DELIVERY_STATUS in ("+trans+")";	
				}
				
				if(application.equalsIgnoreCase("PRODUCTID")){
					qrey = "select TXN_REF_NO,ACC_NO,USER_NAME,FINAL_AMOUNT,MERCHANT_ID,PRODUCT_ID,PRODUCT_PRICE,DISCOUNT_PRICE,TXN_DATE,decode(DELIVERY_STATUS,'OP','Sales Order','DFP','Order Drafting','DSP','Dispatched','DLV','Delivered') "
							+ "from ONLINE_PURCHASE_MASTER where PRODUCT_ID='"+fname+"' AND DELIVERY_STATUS in ("+trans+")";	
				}
				
				if(application.equalsIgnoreCase("DATE")){
					qrey = "select TXN_REF_NO,ACC_NO,USER_NAME,FINAL_AMOUNT,MERCHANT_ID,PRODUCT_ID,PRODUCT_PRICE,DISCOUNT_PRICE,TXN_DATE,decode(DELIVERY_STATUS,'OP','Sales Order','DFP','Order Drafting','DSP','Dispatched','DLV','Delivered') "
							+ "from ONLINE_PURCHASE_MASTER where TRUNC(TXN_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')  AND DELIVERY_STATUS in ("+trans+")";	
				}
				
				if(application.equalsIgnoreCase("ACCNO")){
					qrey = "select TXN_REF_NO,ACC_NO,USER_NAME,FINAL_AMOUNT,MERCHANT_ID,PRODUCT_ID,PRODUCT_PRICE,DISCOUNT_PRICE,TXN_DATE,decode(DELIVERY_STATUS,'OP','Sales Order','DFP','Order Drafting','DSP','Dispatched','DLV','Delivered') "
							+ "from ONLINE_PURCHASE_MASTER where ACC_NO='"+fname+"'  AND DELIVERY_STATUS in ("+trans+")";	
				}
				
				if(application.equalsIgnoreCase("PAYMENT_REF")){
					qrey = "select TXN_REF_NO,ACC_NO,USER_NAME,FINAL_AMOUNT,MERCHANT_ID,PRODUCT_ID,PRODUCT_PRICE,DISCOUNT_PRICE,TXN_DATE,decode(DELIVERY_STATUS,'OP','Sales Order','DFP','Order Drafting','DSP','Dispatched','DLV','Delivered') "
							+ "from ONLINE_PURCHASE_MASTER where TXN_REF_NO='"+fname+"'  AND DELIVERY_STATUS in ("+trans+")";
				}
			
		
			
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("TXN_REF_NO", getlmtfeeRs.getString(1));
				json.put("ACCOUNT_NO", getlmtfeeRs.getString(2));
				json.put("PURCHASE_USER_NAME", getlmtfeeRs.getString(3));
				json.put("FINAL_AMOUNT", getlmtfeeRs.getString(4));
				json.put("MERCHANT_ID", getlmtfeeRs.getString(5));
				json.put("PRODUCT_ID", getlmtfeeRs.getString(6));
				json.put("PRODUCT_PRICE", getlmtfeeRs.getString(7));
				json.put("DISCOUNT_PRICE", getlmtfeeRs.getString(8));
				json.put("TXN_DATE", getlmtfeeRs.getString(9));
				json.put("DELIVERY_STATUS", getlmtfeeRs.getString(10));
				lmtJsonArray.add(json);
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
	
	
	public ResponseDTO gtTransProductDetails(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;

		Connection connection = null;
		
		

		
		
		String productQuerylimit ="";



		JSONObject json = null;
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");

			logger.debug("connection is null [" + connection == null + "]");
			String defaultstr="data:image/jpeg;base64,";

			if(connection!=null)
			{
				
				
				productQuerylimit ="select OPM.MERCHANT_NAME,OPM.MERCHANT_ID,OPM.CATEGORY_ID,OPM.SUB_CATEGORY_ID,OPM.PRODUCT_NAME,OPM.PRODUCT_ID,NVL(OPM.IMG1,'0'),NVL(OPM.IMG2,'0'),NVL(OPM.IMG3,'0'),NVL(OPM.IMG4,'0'),NVL(OPM.MAIN_IMG,'0'),"
						+ "ONM.PRODUCT_PRICE,ONM.DISCOUNT_PRICE,ONM.FINAL_AMOUNT,ONM.TXN_REF_NO,ONM.DELIVERY_STATUS,ONM.ACC_NO,ONM.USER_NAME,ONM.DELIVERY_ADDRESS,ONM.QUANTITY,ONM.COURIER_NAME,ONM.COURIER_ID,ONM.DELIVERY_DATE,ONM.MSISDN,ONM.TXN_DATE,ONM.COURIER_DATE,ONM.ORDER_DRAFING_DATE "
						+ "from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PURCHASE_MASTER ONM WHERE OPM.PRODUCT_ID=ONM.PRODUCT_ID AND ONM.PRODUCT_ID='"+requestJSON.getString("orgid")+"' AND ONM.TXN_REF_NO='"+requestJSON.getString("refno")+"'";
	
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {
						
						json.put("merchantname",gettrnsRs.getString(1));
						json.put("merchantid",gettrnsRs.getString(2));
						json.put("productctg",gettrnsRs.getString(3));
						json.put("productsubctg",gettrnsRs.getString(4));
						json.put("productname",gettrnsRs.getString(5));
						json.put("productid",gettrnsRs.getString(6));
						
						json.put("productprice",gettrnsRs.getString(12));
						json.put("offerprice",gettrnsRs.getString(13));
						json.put("txnamount",gettrnsRs.getString(14));
						json.put("payrefno",gettrnsRs.getString(15));
						json.put("deliverystatus",gettrnsRs.getString(16));
						json.put("accountno",gettrnsRs.getString(17));
						json.put("userid",gettrnsRs.getString(18));
						json.put("deliveradd",gettrnsRs.getString(19));
						json.put("quantity",gettrnsRs.getString(20));
						json.put("couriername",gettrnsRs.getString(21));
						json.put("courierid",gettrnsRs.getString(22));
						json.put("deliverydate",gettrnsRs.getString(23));
						json.put("mobnumber",gettrnsRs.getString(24));
						json.put("txndate",gettrnsRs.getString(25));
						json.put("Dispatch",gettrnsRs.getString(26));
						json.put("Drafting",gettrnsRs.getString(27));
						
						/*if(requestJSON.getString("actionmap").equalsIgnoreCase("View")){
							
						
						if(!gettrnsRs.getString(7).equalsIgnoreCase("0")){
							json.put("pimg2",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(7)+".jpg")));	
						}else{
							json.put("pimg2","");
						}
						if(!gettrnsRs.getString(8).equalsIgnoreCase("0")){
						json.put("pimg3",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(8)+".jpg")));
						}else{
							json.put("pimg3","");
						}
						if(!gettrnsRs.getString(9).equalsIgnoreCase("0")){
						json.put("pimg4",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(9)+".jpg")));
						}else{
							json.put("pimg4","");
						}
						if(!gettrnsRs.getString(10).equalsIgnoreCase("0")){
						json.put("pimg5",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(10)+".jpg")));
						}else{
							json.put("pimg5","");
						}
						if(!gettrnsRs.getString(11).equalsIgnoreCase("0")){
						json.put("pimg1",defaultstr+""+Main.imageToBase64String(new File(resourceBundle.getString("lifestyle_path")+gettrnsRs.getString(6)+"/"+gettrnsRs.getString(11)+".jpg")));
						}else{
							json.put("pimg1","");
						}
						}else{*/
							json.put("pimg2","");
							json.put("pimg3","");
							json.put("pimg4","");
							json.put("pimg5","");
							json.put("pimg1","");
						/*}*/
					}

					
					
					
				lmtDataMap.put("LIMIT_INFO", json);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO gtLifeStyleUpdate(RequestDTO requestDTO) {

		logger.debug("Inside [NewBillPaymentDAO][gtLifeStyleUpdate] ... ");

		HashMap<String, Object> billerDataMap = null;
		Connection connection = null;
		PreparedStatement pstmt = null;


		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			
			
			
			

			billerDataMap = new HashMap<String, Object>();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is [" + connection + "]");
			
			connection.setAutoCommit(false);
			if((requestJSON.getString("actionmap")).equalsIgnoreCase("drafting")){
				
				pstmt = connection.prepareStatement("UPDATE ONLINE_PURCHASE_MASTER SET DELIVERY_STATUS='DFP',ORDER_DRAFING_DATE=sysdate where TXN_REF_NO=?");
				pstmt.setString(1, requestJSON.getString("payrefno"));
				
				
				pstmt.executeUpdate();
				pstmt.close();
				billerDataMap.put("message", "Order Drafting to Merchant");
			}
			
			if((requestJSON.getString("actionmap")).equalsIgnoreCase("dispatched")){
				
				pstmt = connection.prepareStatement("UPDATE ONLINE_PURCHASE_MASTER SET DELIVERY_STATUS='DSP',COURIER_DATE=?,COURIER_NAME=?,COURIER_ID=? where TXN_REF_NO=?");
				pstmt.setString(1, requestJSON.getString("Dispatch"));
				pstmt.setString(2, requestJSON.getString("couriername"));
				pstmt.setString(3, requestJSON.getString("courierid"));
				pstmt.setString(4, requestJSON.getString("payrefno"));
				
				
				pstmt.executeUpdate();
				billerDataMap.put("message", "Order Drafting to Merchant");
			}
			
			if((requestJSON.getString("actionmap")).equalsIgnoreCase("delivery")){
				
				pstmt = connection.prepareStatement("UPDATE ONLINE_PURCHASE_MASTER SET DELIVERY_STATUS='DLV',DELIVERY_DATE=? where TXN_REF_NO=?");
				pstmt.setString(1, requestJSON.getString("deliverydate"));
				pstmt.setString(2, requestJSON.getString("payrefno"));
				
				
				pstmt.executeUpdate();
				billerDataMap.put("message", "Order Drafting to Merchant");
			}
			billerDataMap.put("message", "Order Drafting to Merchant");
			//billerDataMap.put("message", "success");
			connection.commit();
			

			responseDTO.setData(billerDataMap);
		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("SQLException in register Biller [" + e.getMessage()
					+ "]");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");

			logger.debug("Exception in Register Biller [" + e.getMessage()
					+ "]");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			billerDataMap = null;
		}

		return responseDTO;
	}
			
	public ResponseDTO DiscountPartnersAck(RequestDTO requestDTO) {


		Connection connection = null;
		HashMap<String, Object> branchMap = new HashMap<String, Object>();


		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		String adminType=null;
		String adminName=null;
		
		

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();

			String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String branchQry="insert into ORG_MASTER(ORGANIGATION_ID,ACCOUNT_NAME,merchant_type,STATUS,MAKER_ID,MAKER_DT) " +
					" values(?,?,?,?,?,sysdate) ";

			servicePstmt=connection.prepareStatement(branchQry);
		


			JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
			

			for (int i = 0; i < branchArr.size(); i++) {

				JSONObject reqData = branchArr.getJSONObject(i);

				adminType = reqData.getString("adminType");
				adminName = reqData.getString("adminName");
				
				
				System.out.println("adminType :: "+adminType+" :: adminName"+adminName+" maker id ::: "+maker_id);

				servicePstmt.setString(1,adminType);
				servicePstmt.setString(2,adminName);
				servicePstmt.setString(3,"Lifestyle_Coupon");
				servicePstmt.setString(4,"C");
				servicePstmt.setString(5,maker_id);
				
				
				
				servicePstmt.addBatch();
				

			}

			servicePstmt.executeBatch(); // insert remaining records 
			servicePstmt.close();
			
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, maker_id);
			json.put("transCode", "discountcode-reg");
			json.put("channel", "WEB");
			json.put("message", "Acknowledgment :: Cluster Configuration : Cluster Id ::  "+adminType);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			connection.commit();

			responseDTO.setData(branchMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			branchMap = null;


		}

		return responseDTO;
	}
	
	public ResponseDTO locatordetailsCount(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [NewBillPaymentDAO][fetchBillerCount].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry1 = "(select count(*) from AGENT_GPS_LOCATION where TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY')=TO_CHAR(SYSDATE,'DD-MM-YYYY'))";
	    String merchantQry2 = "SELECT TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY') as tdate,nvl(sum(case when FINAL_STATUS in ('AU','CU','AA','COMPLETE') then 1 end),0) as TOT_REQUEST,nvl(sum(case when FINAL_STATUS in('AA','COMPLETE') then 1 end),0) as AGNT_ACCPT,nvl(sum(case when FINAL_STATUS = 'CU' then 1 end),0) as CANCEL_USER,nvl(sum(case when FINAL_STATUS = 'AU' then 1 end),0) as AGNT_NOT_ACCPT,count(*) FROM CUST_ACCEPTS where TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY')=TO_CHAR(SYSDATE,'DD-MM-YYYY') AND FINAL_STATUS is not null   AND CUSTOMER_ID is not null group by TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY')";
	    try
	    {
	      this.requestJSON = requestDTO.getRequestJSON();
	      
	      this.responseDTO = new ResponseDTO();
	      
	      connection = connection == null ? DBConnector.getConnection() : connection;
	      this.logger.debug("connection is [" + connection + "]");
	      
	      merchantMap = new HashMap();
	      resultJson = new JSONObject();
	      
	      merchantPstmt = connection.prepareStatement(merchantQry1);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        resultJson.put("DSA_LOGIN",   merchantRS.getString(1));
	      
	      }
	      merchantPstmt.close();
	      merchantRS.close();
	      
	     
	      
	      merchantPstmt = connection.prepareStatement(merchantQry2);
	      
	      merchantRS = merchantPstmt.executeQuery();
	      if (merchantRS.next())
	      {
	        resultJson.put("TOTAL_CALLS",   merchantRS.getString(2));
	        resultJson.put("AGENT_ACCEPT",   merchantRS.getString(3));
	        resultJson.put("CUST_CANCEL",   merchantRS.getString(4));
	        resultJson.put("CUST_TIMEOUT",   merchantRS.getString(5));
	      
	      }else{
	    	  resultJson.put("TOTAL_CALLS",   "0");
		        resultJson.put("AGENT_ACCEPT",   "0");
		        resultJson.put("CUST_CANCEL",   "0");
		        resultJson.put("CUST_TIMEOUT",   "0"); 
	      }
	      
	      
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      merchantMap.put("BILLER_COUNT", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchBillerCount [" + 
	        e.getMessage() + "]");
	      e.printStackTrace();
	    }
	    finally
	    {
	      DBUtils.closeResultSet(merchantRS);
	      DBUtils.closePreparedStatement(merchantPstmt);
	      DBUtils.closeConnection(connection);
	      
	      merchantMap = null;
	      resultJson = null;
	    }
	    return this.responseDTO;
	  }
	
	public ResponseDTO locatordetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String trans=requestJSON.getString("trans");
			String fname=requestJSON.getString("fname");
			
			
			/*String application=requestJSON.getString("application");
			String fname=requestJSON.getString("fname");
			
			String fromdate=requestJSON.getString("fromdate");
			String todate=requestJSON.getString("todate");*/
			
			if(trans.equalsIgnoreCase("CUST_REQUEST")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,DECODE(FINAL_STATUS,'AA','Accepted','Request') from CUST_ACCEPTS where FINAL_STATUS is not null   AND CUSTOMER_ID is not null AND USER_NAME='"+fname+"'";
	
			}
			
			if(trans.equalsIgnoreCase("DSA_ACCPT")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,DECODE(FINAL_STATUS,'AA','Accepted','Request') from CUST_ACCEPTS where FINAL_STATUS is not null  AND CUSTOMER_ID is not null AND CUSTOMER_ACCPET_AGENT='"+fname+"'";
	
			}
			
		
			
			if(trans.equalsIgnoreCase("locatortotcalls")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,'Request' from CUST_ACCEPTS where FINAL_STATUS is not null and CUSTOMER_ID is not null AND FINAL_STATUS in ('AU','CU','AA','COMPLETE') AND trunc(DSA_DATE_TIME)=trunc(sysdate)";
	
			}
			
			if(trans.equalsIgnoreCase("locatorcalltimeout")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,'Time Out' from CUST_ACCEPTS where FINAL_STATUS is not null and CUSTOMER_ID is not null AND FINAL_STATUS in ('AU') AND trunc(DSA_DATE_TIME)=trunc(sysdate)";
	
			}
			
			if(trans.equalsIgnoreCase("locatoragentaccept")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,'Accepted' from CUST_ACCEPTS where FINAL_STATUS is not null and CUSTOMER_ID is not null AND FINAL_STATUS in ('AA','COMPLETE') AND trunc(DSA_DATE_TIME)=trunc(sysdate)";
	
			}
			
			if(trans.equalsIgnoreCase("locatorcallcanceled")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,'Cancelled' from CUST_ACCEPTS where FINAL_STATUS is not null and CUSTOMER_ID is not null AND FINAL_STATUS in ('CU') AND trunc(DSA_DATE_TIME)=trunc(sysdate)";
	
			}
			
			if(trans.equalsIgnoreCase("locatorcustomercall")){
				qrey = "select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,to_char(DSA_DATE_TIME,'dd-mm-yyyy'),NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,'Cancelled' from CUST_ACCEPTS where FINAL_STATUS is not null and CUSTOMER_ID is not null AND FINAL_STATUS in ('AU') AND trunc(DSA_DATE_TIME)=trunc(sysdate)";
	
			}
			
	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("USER_NAME", getlmtfeeRs.getString(1));
				json.put("CUSTOMER_ID", getlmtfeeRs.getString(2));
				json.put("TITTLE", getlmtfeeRs.getString(3));
				json.put("LATITUDE", getlmtfeeRs.getString(4));
				json.put("LONGITUDE", getlmtfeeRs.getString(5));
				json.put("TNX_DATE", getlmtfeeRs.getString(6));
				json.put("CUSTOMER_ACCPET_AGENT", getlmtfeeRs.getString(7));
				json.put("FINAL_STATUS", getlmtfeeRs.getString(8));
				lmtJsonArray.add(json);
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
	
	public ResponseDTO locatorlogiondetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
		
			qrey = "select USER_ID,LATITUDE,LONGITUDE from AGENT_GPS_LOCATION where TRUNC(DSA_DATE_TIME)=TRUNC(SYSDATE)";
	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("USER_ID", getlmtfeeRs.getString(1));
				json.put("LATITUDE", getlmtfeeRs.getString(2));
				json.put("LONGITUDE", getlmtfeeRs.getString(3));				
				lmtJsonArray.add(json);
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
	
	
	
	public ResponseDTO uploadeddetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			/*String trans=requestJSON.getString("trans");
			String application=requestJSON.getString("application");
			String fname=requestJSON.getString("fname");
			*/
			String fromdate=requestJSON.getString("fromdate");
			String todate=requestJSON.getString("todate");
			
			qrey = "select ref_num,f_name,num_of_record,to_char(upload_date,'dd-mm-yyyy'),status,maker_id from file_upload_master where TRUNC(UPLOAD_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')";
	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("ref_num", getlmtfeeRs.getString(1));
				json.put("f_name", getlmtfeeRs.getString(2));
				json.put("num_of_record", getlmtfeeRs.getString(3));
				json.put("upload_date", getlmtfeeRs.getString(4));
				json.put("status", getlmtfeeRs.getString(5));
				json.put("maker_id", getlmtfeeRs.getString(6));
				lmtJsonArray.add(json);
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
	
	public ResponseDTO uploadeddetailsdelete(RequestDTO requestDTO) {

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
		PreparedStatement pstmt = null;



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String refno=requestJSON.getString("refno");
			String application=requestJSON.getString("application");
			
			String fromdate=requestJSON.getString("fromdate");
			String todate=requestJSON.getString("todate");
			
			pstmt = connection.prepareStatement("DELETE from file_upload_master  where ref_num=?");
			pstmt.setString(1, refno);
			
			pstmt.executeUpdate();
			
			connection.commit();
			
			
			qrey = "select ref_num,f_name,num_of_record,to_char(upload_date,'dd-mm-yyyy'),status,maker_id from file_upload_master where TRUNC(UPLOAD_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')";
	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("ref_num", getlmtfeeRs.getString(1));
				json.put("f_name", getlmtfeeRs.getString(2));
				json.put("num_of_record", getlmtfeeRs.getString(3));
				json.put("upload_date", getlmtfeeRs.getString(4));
				json.put("status", getlmtfeeRs.getString(5));
				json.put("maker_id", getlmtfeeRs.getString(6));
				lmtJsonArray.add(json);
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
	
	
	public ResponseDTO reportuploadeddetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			/*String trans=requestJSON.getString("trans");
			String application=requestJSON.getString("application");
			String fname=requestJSON.getString("fname"); */
			
			String fromdate=requestJSON.getString("fromdate");
			String todate=requestJSON.getString("todate");
			
			qrey = "select ref_num,REPORT_NAME,SEARCH_DATA,to_char(MAKER_DT,'dd-mm-yyyy'),status,maker_id,JRXML_NAME from OFFLINE_REPORT_REQUEST where TRUNC(MAKER_DT) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')";
	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("ref_num", getlmtfeeRs.getString(1));
				json.put("REPORT_NAME", getlmtfeeRs.getString(2));
				json.put("SEARCH_DATA", getlmtfeeRs.getString(3));
				json.put("MAKER_DT", getlmtfeeRs.getString(4));
				json.put("status", getlmtfeeRs.getString(5));
				json.put("maker_id", getlmtfeeRs.getString(6));
				json.put("f_name", getlmtfeeRs.getString(7)+"_"+getlmtfeeRs.getString(1));
				lmtJsonArray.add(json);
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
	
	public ResponseDTO raasinformdetails(RequestDTO requestDTO) {

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
			
		
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			/*String trans=requestJSON.getString("trans");
			String application=requestJSON.getString("application");
			String fname=requestJSON.getString("fname"); */
			
			String customercode=requestJSON.getString("customercode");
			String rassdt[]=customercode.split(",");
			
			int j=0;
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<rassdt.length;i++){
				sb.append(" '"+rassdt[i]+"' ");
				j++;
				if(j!=rassdt.length){
					sb.append(",");
				}
			}
			
			
			qrey = "select  TRANSREFNO,RAASTXNREF from RESER_FUND_TRANFER where TRANSREFNO in ("+sb.toString()+")";
			String headval="Txn Reference No,RAAS Number";
			
			ExcelReportGeneration.perform("Nofound_Raasno_information", connection, qrey,headval);
	
			//System.out.println("kailash :: "+qrey);
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("TRANSREFNO", getlmtfeeRs.getString(1));
				json.put("RAASTXNREF", getlmtfeeRs.getString(2));				
				lmtJsonArray.add(json);
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
