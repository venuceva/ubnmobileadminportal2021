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
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class LoyalityPointDetailsDAO {
	private static Logger logger = Logger.getLogger(LoyalityPointDetailsDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
	public ResponseDTO LoyalityPointDetails(RequestDTO requestDTO) {

		logger.debug(" Inside LoyalityPointDetails.. ");

		HashMap<String, Object> loyalityDataMap = null;
		JSONArray loyalityJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getloyalityPstmt = null;
		ResultSet getloyalityRs = null;

		Connection connection = null;

		String loyalityQry = "select LOYALTY_CODE,LOYALTY_DESC,INITIATOR_ID,to_char(INITIATOR_DTTM,'dd-mm-yyyy'),PRODUCT,APPLICATION from LOYALTY_MASTER";



		try {
			responseDTO = new ResponseDTO();

			loyalityDataMap = new HashMap<String, Object>();
			loyalityJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getloyalityPstmt = connection.prepareStatement(loyalityQry);

			getloyalityRs = getloyalityPstmt.executeQuery();

			while (getloyalityRs.next()) {

				
				json = new JSONObject();
				json.put("LOYALTY_CODE", getloyalityRs.getString(1));
				json.put("LOYALTY_DESC", getloyalityRs.getString(2));
				json.put("INITIATOR_ID", getloyalityRs.getString(3));
				json.put("INITIATOR_DTTM", getloyalityRs.getString(4));
				json.put("PRODUCT", getloyalityRs.getString(5));
				json.put("APPLICATION", getloyalityRs.getString(6));
				loyalityJsonArray.add(json);

			}

			resultJson.put("VIEW_LOYALITY_DATA", loyalityJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			loyalityDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + loyalityDataMap + "]");
			responseDTO.setData(loyalityDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getloyalityRs != null) {
					getloyalityRs.close();
				}

				if (getloyalityPstmt != null) {
					getloyalityPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			loyalityDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO fetchLoyalityPointInfo(RequestDTO requestDTO) {

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
		
		

		
		String autoLoyalityQry = "select 'LY'||LPAD(count(*)+1,4, '0') from LOYALTY_MASTER";

		logger.info("frqQry ["+autoLoyalityQry+"]");
		
		
		
		String productQuery ="select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT  order by PRD_CODE";



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
				
				
					
					
					gettrnsPstmt = connection.prepareStatement(autoLoyalityQry);
	
					gettrnsRs = gettrnsPstmt.executeQuery();
	
					json = new JSONObject();
					while (gettrnsRs.next()) {
	
						resultJson.put("LIMIT_CODE", gettrnsRs.getString(1));
	
					}
					
					gettrnsPstmt.close();
					gettrnsRs.close();
					
					

					
					gettrnsPstmt = connection.prepareStatement(productQuery);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("PRODUCT_TEMP", json);
					
					
						
					
						gettrnsPstmt.close();
						gettrnsRs.close();
						
						
					/*gettrnsPstmt = connection.prepareStatement("select SERVICE_CODE||'-'||SERVICE_NAME,SERVICE_CODE||'-'||SERVICE_NAME FROM MOB_SERVICE_MASTER WHERE service_type='MERCHANT'");

						gettrnsRs = gettrnsPstmt.executeQuery();

						JSONObject json1 = new JSONObject();
						while (gettrnsRs.next()) {

							json1.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

						}

						resultJson.put("MERCHANT_GRP", json1);*/
						
						
							
						
							gettrnsPstmt.close();
							gettrnsRs.close();
					

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
	
	
	
	public ResponseDTO loyaltySettingInsert(RequestDTO requestDTO) {

		logger.debug("Inside [LoyalityPointDetailsDAO][loyaltySettingInsert] ... ");

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
			callable = connection.prepareCall("{call LOYALTYPKG.loyaltySettingInsertProc(?,?,?,?,?,?,?,?,?,?)}");

			callable.setString(1, requestJSON.getString("limitCodeval"));
			callable.setString(2, requestJSON.getString("limitDescriptionval"));
			callable.setString(3, requestJSON.getString("MinPoints"));
			callable.setString(4, requestJSON.getString("UnitSize"));
			callable.setString(5, requestJSON.getString("redemptionpoints"));
			callable.setString(6, requestJSON.getString("redemptionamount"));
			callable.setString(7, requestJSON.getString("productcode"));
			callable.setString(8, requestJSON.getString("application"));
			callable.setString(9, requestJSON.getString("makerId"));
			callable.registerOutParameter(10, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(10).indexOf("SUCCESS") == -1) {
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
	
	public ResponseDTO fetchLoyalityPointSercive(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [LoyalityPointDetailsDAO][fetchLoyalityPointSercive]");

		
		
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
			

			
				String serviceQry = "select SERVICE_CODE||'-'||SERVICE_NAME,SERVICE_CODE||'-'||SERVICE_NAME from MOB_CHANNEL_MAP WHERE ASSIGN_LOYALTY='YES'";
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

			logger.debug("[LoyalityPointDetailsDAO][fetchLoyalityPointSercive] SQLException in fetchLoyalityPointSercive [" + e.getMessage()
					+ "]");
			responseDTO.addError("[LoyalityPointDetailsDAO][fetchLoyalityPointSercive] Internal Error Occured While Executing.");
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
	
	
	public ResponseDTO LoyalityPointSerciveAck(RequestDTO requestDTO) {

		logger.debug("Inside [LoyalityPointDetailsDAO][LoyalityPointSerciveAck] ... ");

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
			callable = connection.prepareCall("{call LOYALTYPKG.loyaltyDetails(?,?,?)}");

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
	
	public ResponseDTO viewLoyaltyDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [LoyalityPointDetailsDAO][viewLoyaltyDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String merchantQry = "Select PRODUCT,APPLICATION,LOYALTY_CODE,LOYALTY_DESC,R_MIN_POINTS,R_UNIT_SIZE,"
				+ "R_AMOUNT,R_MIN_POINTS "
				+ "from LOYALTY_MASTER where LOYALTY_CODE=?";
		try {

			responseDTO = new ResponseDTO();

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is [" + connection + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			
			requestJSON = requestDTO.getRequestJSON();
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt.setString(1, requestJSON.getString("loyaltycode"));
			
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				resultJson.put("PRODUCT",merchantRS.getString(1));
				resultJson.put("APPLICATION",merchantRS.getString(2));
				resultJson.put("LOYALTY_CODE",merchantRS.getString(3));
				resultJson.put("LOYALTY_DESC",merchantRS.getString(4));
				resultJson.put("R_MIN_POINTS",merchantRS.getString(5));
				resultJson.put("R_UNIT_SIZE", merchantRS.getString(6));
				resultJson.put("R_AMOUNT",merchantRS.getString(7));
				resultJson.put("R_MIN_POINTS",merchantRS.getString(8));
				
				
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap.put("BILLER_INFO", resultJson);
			logger.debug("resultJson:::: [" + resultJson + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in viewLoyaltyDetails ["+ e.getMessage() + "]");
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
	
	public ResponseDTO fetchLoyaltyDetails(RequestDTO requestDTO)
	  {
	    Connection connection = null;
	    this.logger.debug("Inside [LoyalityPointDetailsDAO][fetchLoyaltyDetails].. ");
	    
	    HashMap<String, Object> merchantMap = null;
	    JSONObject resultJson = null;
	    JSONArray merchantJsonArray = null;
	    PreparedStatement merchantPstmt = null;
	    ResultSet merchantRS = null;
	    
	    JSONObject json = null;
	    
	    
	    String merchantQry = "Select SERVICE_CODE,SERVICE_DESC,TXN_AMOUNT,NO_OF_POINT,to_char(MAKER_DTTM,'DD-MM-YYYY HH24:MI:SS')"
				+"from LOYALTY_DETAILS where LOYALTY_CODE=?  order by SERVICE_DESC";
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
	      
	      merchantPstmt.setString(1, this.requestJSON.getString("loyaltycode"));
	      
	      merchantRS = merchantPstmt.executeQuery();
	      while (merchantRS.next())
	      {
	        json = new JSONObject();
	        json.put("BILLER_ID", 
	          merchantRS.getString(1));
	        json.put("ACCOUNT_NUMBER", 
	          merchantRS.getString(2));
	        json.put("ACCOUNT_NAME", 
	          merchantRS.getString(3));
	        json.put("MAKER_ID", 
	          merchantRS.getString(4));
	        json.put("MAKER_DTTM", 
	          merchantRS.getString(5));
	        merchantJsonArray.add(json);
	      }
	     
	      resultJson.put("BILLER_ACCT_LIST", merchantJsonArray);
	      merchantMap.put("BILLER_ACCT_LIST", resultJson);
	      this.logger.debug("EntityMap [" + merchantMap + "]");
	      this.responseDTO.setData(merchantMap);
	    }
	    catch (Exception e)
	    {
	      this.logger.debug("Got Exception in fetchLoyaltyDetails [" + 
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

	public ResponseDTO viewLoyaltyAssingDetails(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside [LoyalityPointDetailsDAO][viewLoyaltyAssingDetails].. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String merchantQry = "Select LM.PRODUCT,LM.APPLICATION,LM.LOYALTY_CODE,LM.LOYALTY_DESC,LD.SERVICE_CODE,LD.TXN_AMOUNT,LD.NO_OF_POINT"
				+ " from LOYALTY_MASTER LM,LOYALTY_DETAILS LD WHERE LD.LOYALTY_CODE=LM.LOYALTY_CODE AND LD.SERVICE_CODE=?";
		
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
				resultJson.put("PRODUCT",merchantRS.getString(1));
				resultJson.put("APPLICATION",merchantRS.getString(2));
				resultJson.put("LOYALTY_CODE",merchantRS.getString(3));
				resultJson.put("LOYALTY_DESC",merchantRS.getString(4));
				resultJson.put("SERVICE_CODE",merchantRS.getString(5));
				resultJson.put("TXN_AMOUNT", merchantRS.getString(6));
				resultJson.put("NO_OF_POINT", merchantRS.getString(7));
				
			}
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap.put("BILLER_INFO", resultJson);
			logger.debug("resultJson:::: [" + resultJson + "]");
			responseDTO.setData(merchantMap);

		} catch (Exception e) {
			logger.debug("Got Exception in viewLoyaltyAssingDetails ["+ e.getMessage() + "]");
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

	public ResponseDTO ModifyAssignLoyaltyModify(RequestDTO requestDTO) {

		logger.debug("Inside [LoyalityPointDetailsDAO][ModifyAssignLoyaltyModify] ... ");

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
			callable = connection.prepareCall("{call LOYALTYPKG.ModifyAssignLoyaltyModifyProc(?,?,?,?,?,?,?)}");

			callable.setString(1, requestJSON.getString("productcode"));
			callable.setString(2, requestJSON.getString("loyaltycode"));
			callable.setString(3, requestJSON.getString("servicecode"));
			callable.setString(4, requestJSON.getString("txnamount"));
			callable.setString(5, requestJSON.getString("Noofpoints"));
			callable.setString(6, requestJSON.getString("makerId"));
			callable.registerOutParameter(7, OracleTypes.VARCHAR);

			callable.execute();

			if (callable.getString(7).indexOf("SUCCESS") == -1) {
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
	

}
