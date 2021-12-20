package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.service.utils.dao.CommonServiceDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class POSDetailsDAO {
	private static Logger logger = Logger.getLogger(POSDetailsDAO.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
	public ResponseDTO POSAgentSearch(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [POSDetailsDAO][POSAgentSearch]");

		logger.debug("Inside  POSAgentSearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		JSONArray IncomeMTFilesJSONArray1 = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;
		String userReport="";
		String userReport1="";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			IncomeMTFilesJSONArray1 = new JSONArray();
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			String msg="";
			
			String customercode=((requestJSON.getString("customercode")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("customercode")+"'";
			String application=((requestJSON.getString("application")).equalsIgnoreCase("")) ? null : ""+requestJSON.getString("application")+"";
			String storeid=((requestJSON.getString("storeid")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("storeid")+"'";

			//if(application.equalsIgnoreCase("AGENT")){
				msg="DSA Service Search for User Id ";

			userReport ="select ACM.ID,to_char(WAD.DATE_CREATED,'dd-mm-yyyy'),MCI.MOBILE_NUMBER,WAD.ACCT_NO,ACM.USER_ID,DECODE(ACM.STATUS,'B','BLOCK','F','INACTIVE','A','ACTIVE','L','DEACTIVATION'),ACM.FIRST_NAME,SM.STORE_NAME,SM.STORE_ID,SM.ADDRESS,SM.LOC_GOV,SM.STATE,DECODE(SM.STATUS,'B','BLOCK','F','INACTIVE','A','ACTIVE','L','DEACTIVATION'),ACM.ACCOUNT_NO,ACM.CUSTOMER_CODE from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD,STORE_MASTER SM "
			+ "where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID  and MCI.CUST_ID=SM.CUST_ID and WAD.CUST_TYPE='AGENT' AND "
			+ " UPPER(ACM.CUSTOMER_CODE)=UPPER("+customercode+") and SM.STORE_ID="+storeid+" " ;

			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("REF_NO",serviceRS.getString(1));
				 json.put("DATE_CREATED",serviceRS.getString(2));
				 json.put("MOBILE_NO",serviceRS.getString(3));
				 json.put("ACCT_NO",serviceRS.getString(4));
				 json.put("USER_ID",serviceRS.getString(5));
				 json.put("STATUS",serviceRS.getString(6));
				 json.put("FIRST_NAME",serviceRS.getString(7));
				 json.put("STORE_NAME",serviceRS.getString(8));
				 json.put("STORE_ID",serviceRS.getString(9));
				 json.put("ADDRESS",serviceRS.getString(10));
				 json.put("LOC_GOV",serviceRS.getString(11));
				 json.put("STATE",serviceRS.getString(12));
				 json.put("SSTATUS",serviceRS.getString(13));
				 json.put("COREACCNO",serviceRS.getString(14));
				 json.put("AGENTID",serviceRS.getString(15));
				 IncomeMTFilesJSONArray.add(json);
				}
			 
			 servicePstmt.close();
			 serviceRS.close();
			 
			 userReport1 ="select TERMINAL_ID,SERIAL_NO,TERMINAL_MAKE,MODEL_NO,MAKER_DTTM,STATUS FROM TERMINAL_MASTER "
						+ "where  STORE_ID="+storeid+" ";

						servicePstmt = connection.prepareStatement(userReport1);
						serviceRS = servicePstmt.executeQuery();
						JSONObject json1 = new JSONObject();
						
						 while(serviceRS.next())
							{
							 json1=new JSONObject();
							 json1.put("TERMINAL_ID",serviceRS.getString(1));
							 json1.put("SERIAL_NO",serviceRS.getString(2));
							 json1.put("TERMINAL_MAKE",serviceRS.getString(3));
							 json1.put("MODEL_NO",serviceRS.getString(4));
							 json1.put("MAKER_DTTM",serviceRS.getString(5));
							 json1.put("STATUS",serviceRS.getString(6));
							 
							 IncomeMTFilesJSONArray1.add(json1);
							}
				
				
			//}
			
			
			/*JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "agentreglock");
			json.put("channel", "WEB");
			json.put("message", msg);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);*/
			
			
			resultJson.put("Files_List", IncomeMTFilesJSONArray);
			resultJson.put("Files_List1", IncomeMTFilesJSONArray1);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in POSAgentSearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in POSAgentSearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceIdRS != null)
					serviceIdRS.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			serviceDataMap = null;
			resultJson = null;
			IncomeMTFilesJSONArray.clear();
			IncomeMTFilesJSONArray = null;
		}

		return responseDTO;
	}
	
	
public ResponseDTO POSAgentAdd(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [POSDetailsDAO][POSAgentAdd]");

		
		
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
			 JSONArray JsonArray= new JSONArray();
			 String userid=requestJSON.getString("userid");
			 String actiontype=requestJSON.getString("actiontype");
			 
			 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			 String remoteip=requestJSON.getString("remoteip");
			 String Actionname="";
			 String Actiondesc="";
			
			
				 
				  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME,ACM.MIDDLE_NAME,ACM.LAST_NAME,ACM.ACC_BRANCH,to_char(ACM.DOB,'dd-mm-yyyy'),ACM.EMAILID,ACM.GENDER,ACM.SUPER_ADM,ACM.W_PRD_CODE,MCI.MOBILE_NUMBER,MCI.ADDRESS,"
				  		+ "MCI.ID_TYPE,MCI.ID_DETAILS,MCI.NATIONALITY,MCI.RL_LGA,MCI.R_STATE,MCI.COUNTRY,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.ID,NVL(ACM.USER_ID,' '),WAD.ACCT_NO,to_char(ACM.DATE_CREATED,'dd-mm-yyyy hh:mi:ss'),W_PRD_CODE,W_PRD_DESC,AGENCY_TYPE from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID='"+userid+"'");
				  serviceRS = servicePstmt.executeQuery();
					
					 while(serviceRS.next())
						{
						json.put("refno",userid);
						json.put("accountno",serviceRS.getString(1));
						json.put("fullname",serviceRS.getString(2));
						json.put("middlename",serviceRS.getString(3));
						json.put("lastname",serviceRS.getString(4));
						json.put("branchcode",serviceRS.getString(5));
						json.put("dateofbirth",serviceRS.getString(6));
						json.put("email",serviceRS.getString(7));
						json.put("gender",serviceRS.getString(8));
						json.put("superadmin",serviceRS.getString(9));
						json.put("telephone",serviceRS.getString(11));
						
						json.put("address",serviceRS.getString(12));
						json.put("idtype",serviceRS.getString(13));
						json.put("iddetails",serviceRS.getString(14));
						json.put("nationality",serviceRS.getString(15));
						json.put("lga",serviceRS.getString(16));
						json.put("state",serviceRS.getString(17));
						json.put("country",serviceRS.getString(18));
						json.put("status",serviceRS.getString(19));
						json.put("id",serviceRS.getString(20));
						json.put("userid",serviceRS.getString(11));
						json.put("walletaccountno",serviceRS.getString(22));
						json.put("onboarddate",serviceRS.getString(23));
						
						json.put("product",serviceRS.getString(24));
						json.put("prodesc",serviceRS.getString(25));
						json.put("agenttype",serviceRS.getString(26));
						
						
						}
					 servicePstmt.close();
					 serviceRS.close();
					 
					 if(actiontype.equalsIgnoreCase("AGENTADD")) {
						 
					
					 
					servicePstmt = connection.prepareStatement("select TERMINALID_SEQ.nextval from DUAL");
						  serviceRS = servicePstmt.executeQuery();
							
							 while(serviceRS.next())
								{
								 json.put("terminalid",serviceRS.getString(1));
								}
					 
							 servicePstmt.close();
							 serviceRS.close();
					 }
							 
							 json.put("modelno","");
							 json.put("terminalmake","");
							 json.put("serialno","");
							 json.put("terminal_status","");
							 
							 servicePstmt = connection.prepareStatement("select MODEL_NO,TERMINAL_MAKE,SERIAL_NO,DECODE(STATUS,'A','Active','D','Deactive','R','Retrieve') FROM TERMINAL_MASTER where TERMINAL_ID='"+requestJSON.getString("terminalid")+"'");
							  	serviceRS = servicePstmt.executeQuery();
								
								 while(serviceRS.next())
								 {
									 json.put("modelno",serviceRS.getString(1));
										json.put("terminalmake",serviceRS.getString(2));
										json.put("serialno",serviceRS.getString(3));
										json.put("terminal_status",serviceRS.getString(4));
								 }
								 
							
				 
				
			 
			 	JSONObject jsonaudit = new JSONObject();
			 	jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
			 	jsonaudit.put("transCode", Actionname);
			 	jsonaudit.put("channel", "WEB");
			 	jsonaudit.put("message", Actiondesc);
			 	jsonaudit.put("ip", remoteip);
			 	jsonaudit.put("det1", "");
			 	jsonaudit.put("det2", "");
			 	jsonaudit.put("det3", "");
				
				CommonServiceDao csd=new CommonServiceDao();
				csd.auditTrailInsert(jsonaudit);
				
				connection.commit();
						
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[POSDetailsDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
					+ "]");
			responseDTO.addError("[POSDetailsDAO][AgentRegModify] Internal Error Occured While Executing.");
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


public ResponseDTO gtAgentRegistrtionack(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		
		insQRY = "{call posRegistrationpkg.posregistrationproc(?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("userid"));
		cstmt.setString(2, requestJSON.getString("storeid"));
		cstmt.setString(3, requestJSON.getString("terminalid"));

		cstmt.setString(4, requestJSON.getString("terminalmake"));
		cstmt.setString(5, requestJSON.getString("modelnumber"));
		cstmt.setString(6, requestJSON.getString("serialnumber"));			
		cstmt.setString(7, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(8, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(9, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(9)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(9));
		}else{
			
			responseDTO.addMessages(cstmt.getString(9));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO gtAgentRegistrtionmodifyack(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		
		insQRY = "{call posRegistrationpkg.posmodifyproc(?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("userid"));
		cstmt.setString(2, requestJSON.getString("storeid"));
		cstmt.setString(3, requestJSON.getString("terminalid"));

		cstmt.setString(4, requestJSON.getString("terminalmake"));
		cstmt.setString(5, requestJSON.getString("modelnumber"));
		cstmt.setString(6, requestJSON.getString("serialnumber"));			
		cstmt.setString(7, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(8, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(9, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(9)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(9));
		}else{
			
			responseDTO.addMessages(cstmt.getString(9));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}
public ResponseDTO PosRegistrationStatusAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	
	String ststau = "";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		if((requestJSON.getString("termilstatus")).equalsIgnoreCase("Active")) {
			ststau="A";
		}
		
		if((requestJSON.getString("termilstatus")).equalsIgnoreCase("Deactive")) {
			ststau="D";
		}
		
		if((requestJSON.getString("termilstatus")).equalsIgnoreCase("Retrive")) {
			ststau="R";
		}
		
		
		insQRY = "{call posRegistrationpkg.posstatusproc(?,?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("userid"));
		cstmt.setString(2, requestJSON.getString("storeid"));
		cstmt.setString(3, requestJSON.getString("terminalid"));
		
		cstmt.setString(4, requestJSON.getString("terminalmake"));
		cstmt.setString(5, requestJSON.getString("modelnumber"));
		cstmt.setString(6, requestJSON.getString("serialnumber"));
		cstmt.setString(7, ststau);		
		cstmt.setString(8, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(9, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(10, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(10)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(10));
		}else{
			
			responseDTO.addMessages(cstmt.getString(10));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}


public ResponseDTO StoreAgentSearch(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][POSAgentSearch]");

	logger.debug("Inside  POSAgentSearch.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	JSONArray IncomeMTFilesJSONArray1 = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;
	String userReport="";
	String userReport1="";

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		
		IncomeMTFilesJSONArray = new JSONArray();
		IncomeMTFilesJSONArray1 = new JSONArray();
		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		String msg="";
		
		String customercode=((requestJSON.getString("customercode")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("customercode")+"'";
		String application=((requestJSON.getString("application")).equalsIgnoreCase("")) ? null : ""+requestJSON.getString("application")+"";

		//if(application.equalsIgnoreCase("AGENT")){
			msg="DSA Service Search for User Id ";

		userReport ="select ACM.ID,to_char(WAD.DATE_CREATED,'dd-mm-yyyy'),MCI.MOBILE_NUMBER,WAD.ACCT_NO,ACM.USER_ID,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation'),ACM.FIRST_NAME from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD "
		+ "where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' AND "
		+ " UPPER(ACM.CUSTOMER_CODE)=UPPER("+customercode+") ";

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("DATE_CREATED",serviceRS.getString(2));
			 json.put("MOBILE_NO",serviceRS.getString(3));
			 json.put("ACCT_NO",serviceRS.getString(4));
			 json.put("USER_ID",serviceRS.getString(5));
			 json.put("STATUS",serviceRS.getString(6));
			 json.put("FIRST_NAME",serviceRS.getString(7));
			 IncomeMTFilesJSONArray.add(json);
			}
			
			
		//}
		 servicePstmt.close();
		 serviceRS.close();
		 
		userReport1 ="select SM.STORE_NAME,SM.STORE_ID,SM.ADDRESS,SM.LOC_GOV,SM.STATE,DECODE(SM.STATUS,'B','BLOCK','F','INACTIVE','A','ACTIVE','L','DEACTIVATION') from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD,STORE_MASTER SM "
				+ "where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID  and MCI.CUST_ID=SM.CUST_ID and WAD.CUST_TYPE='AGENT' AND "
				+ " UPPER(ACM.CUSTOMER_CODE)=UPPER("+customercode+") ";

				servicePstmt = connection.prepareStatement(userReport1);
				serviceRS = servicePstmt.executeQuery();
				JSONObject json1 = new JSONObject();
				
				 while(serviceRS.next())
					{
					 json1=new JSONObject();
					 
					 json1.put("STORE_NAME",serviceRS.getString(1));
					 json1.put("STORE_ID",serviceRS.getString(2));
					 json1.put("ADDRESS",serviceRS.getString(3));
					 json1.put("LOC_GOV",serviceRS.getString(4));
					 json1.put("STATE",serviceRS.getString(5));
					 json1.put("SSTATUS",serviceRS.getString(6));
					 IncomeMTFilesJSONArray1.add(json1);
					}
		
		
		/*JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, makerId);
		json.put("transCode", "agentreglock");
		json.put("channel", "WEB");
		json.put("message", msg);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);*/
		
		
		resultJson.put("Files_List", IncomeMTFilesJSONArray);
		resultJson.put("Files_List1", IncomeMTFilesJSONArray1);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in POSAgentSearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in POSAgentSearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (servicePstmt != null)
				servicePstmt.close();
			if (serviceIdRS != null)
				serviceIdRS.close();
			if (serviceRS != null)
				serviceRS.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO StoreAgentAdd(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [POSDetailsDAO][POSAgentAdd]");

	
	
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
		 JSONArray JsonArray= new JSONArray();
		 String userid=requestJSON.getString("userid");
		 String actiontype=requestJSON.getString("actiontype");
		 
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 String Actionname="";
		 String Actiondesc="";
		
		
			 
			  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME||ACM.MIDDLE_NAME||ACM.LAST_NAME,MCI.MOBILE_NUMBER,"
			  		+ "decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.CUSTOMER_CODE,WAD.ACCT_NO,ACM.CUSTOMER_CODE||'-S'||STORE_SEQ.nextval from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID='"+userid+"'");
			  serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",userid);
					json.put("accountno",serviceRS.getString(1));
					json.put("fullname",serviceRS.getString(2));					
					json.put("status",serviceRS.getString(4));
					json.put("id",serviceRS.getString(5));
					json.put("userid",serviceRS.getString(3));
					json.put("walletaccountno",serviceRS.getString(6));
					json.put("storeid",serviceRS.getString(7));
					
					
					}
				 servicePstmt.close();
				 serviceRS.close();
				 
				
		 
		 
			
		 
		 	JSONObject jsonaudit = new JSONObject();
		 	jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
		 	jsonaudit.put("transCode", Actionname);
		 	jsonaudit.put("channel", "WEB");
		 	jsonaudit.put("message", Actiondesc);
		 	jsonaudit.put("ip", remoteip);
		 	jsonaudit.put("det1", "");
		 	jsonaudit.put("det2", "");
		 	jsonaudit.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(jsonaudit);
			
			connection.commit();
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[POSDetailsDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
				+ "]");
		responseDTO.addError("[POSDetailsDAO][AgentRegModify] Internal Error Occured While Executing.");
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


public ResponseDTO AgentStoreSearch(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [POSDetailsDAO][POSAgentAdd]");

	
	
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
		 JSONArray JsonArray= new JSONArray();
		 String userid=requestJSON.getString("userid");
		 String actiontype=requestJSON.getString("actiontype");
		 
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 String Actionname="";
		 String Actiondesc="";
		
		
			 
			  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME||ACM.MIDDLE_NAME||ACM.LAST_NAME,MCI.MOBILE_NUMBER,"
			  		+ "decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.CUSTOMER_CODE,WAD.ACCT_NO from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID='"+userid+"'");
			  serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",userid);
					json.put("accountno",serviceRS.getString(1));
					json.put("fullname",serviceRS.getString(2));					
					json.put("status",serviceRS.getString(4));
					json.put("id",serviceRS.getString(5));
					json.put("userid",serviceRS.getString(3));
					json.put("walletaccountno",serviceRS.getString(6));					
					
					}
				 servicePstmt.close();
				 serviceRS.close();
				 
				 servicePstmt = connection.prepareStatement("select STORE_NAME,STORE_ID,ADDRESS,COUNTRY,(select STATE_CODE||'-'||STATE_NAME from STATE_MASTER where UPPER(STATE_NAME)=UPPER(STATE)) as STATE,LOC_GOV,decode(STATUS,'B','Block','F','Inactive','A','Active','L','Deactive','Active'),B_OWNER_NAME,EMAIL,TELEPHONE_NO,AREA,LONGITUDE,LATITUDE from STORE_MASTER where STORE_ID='"+requestJSON.getString("storeid")+"'");
				  	serviceRS = servicePstmt.executeQuery();
					
					 while(serviceRS.next())
					 {
						 json.put("storename",serviceRS.getString(1));
							json.put("storeid",serviceRS.getString(2));
							json.put("address",serviceRS.getString(3));
							json.put("country",serviceRS.getString(4));
							json.put("state",serviceRS.getString(5));
							json.put("localGovernment",serviceRS.getString(6));
							json.put("sstatus",serviceRS.getString(7));
							json.put("boname",serviceRS.getString(8));
							json.put("email",serviceRS.getString(9));
							json.put("mobileno",(serviceRS.getString(10)).substring(3));
							
							json.put("area",serviceRS.getString(11));
							json.put("longitude",serviceRS.getString(12));
							json.put("latitude",serviceRS.getString(13));
					 }
					 
		 
		 
			
		 
		 	JSONObject jsonaudit = new JSONObject();
		 	jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
		 	jsonaudit.put("transCode", Actionname);
		 	jsonaudit.put("channel", "WEB");
		 	jsonaudit.put("message", Actiondesc);
		 	jsonaudit.put("ip", remoteip);
		 	jsonaudit.put("det1", "");
		 	jsonaudit.put("det2", "");
		 	jsonaudit.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(jsonaudit);
			
			connection.commit();
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[POSDetailsDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
				+ "]");
		responseDTO.addError("[POSDetailsDAO][AgentRegModify] Internal Error Occured While Executing.");
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

public ResponseDTO storeRegistrationAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		
		
		insQRY = "{call posRegistrationpkg.storeregistrationproc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("telephone"));
		cstmt.setString(2, requestJSON.getString("id"));
		cstmt.setString(3, requestJSON.getString("boname"));
		cstmt.setString(4, requestJSON.getString("email"));
		cstmt.setString(5, requestJSON.getString("mobileno"));
		cstmt.setString(6, requestJSON.getString("area"));
		cstmt.setString(7, requestJSON.getString("addressLine"));
		cstmt.setString(8, requestJSON.getString("localGovernment"));
		cstmt.setString(9, requestJSON.getString("state"));
		cstmt.setString(10, requestJSON.getString("country"));
		cstmt.setString(11, requestJSON.getString("latitude"));
		cstmt.setString(12, requestJSON.getString("longitude"));
		cstmt.setString(13, requestJSON.getString("storename"));
		cstmt.setString(14, requestJSON.getString("storeid"));
		cstmt.setString(15, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(16, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(17, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(17)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(17));
		}else{
			
			responseDTO.addMessages(cstmt.getString(17));
		}
		
		
		


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO storeRegistrationModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		
		
insQRY = "{call posRegistrationpkg.storeregistrationmodifyproc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("telephone"));
		cstmt.setString(2, requestJSON.getString("id"));
		cstmt.setString(3, requestJSON.getString("boname"));
		cstmt.setString(4, requestJSON.getString("email"));
		cstmt.setString(5, requestJSON.getString("mobileno"));
		cstmt.setString(6, requestJSON.getString("area"));
		cstmt.setString(7, requestJSON.getString("addressLine"));
		cstmt.setString(8, requestJSON.getString("localGovernment"));
		cstmt.setString(9, requestJSON.getString("state"));
		cstmt.setString(10, requestJSON.getString("country"));
		cstmt.setString(11, requestJSON.getString("latitude"));
		cstmt.setString(12, requestJSON.getString("longitude"));
		cstmt.setString(13, requestJSON.getString("storename"));
		cstmt.setString(14, requestJSON.getString("storeid"));
		cstmt.setString(15, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(16, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(17, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(17)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(17));
		}else{
			
			responseDTO.addMessages(cstmt.getString(17));
		}
		
		
		
		


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO storeStatusModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	String ststus="";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		if ((requestJSON.getString("sstatus").equalsIgnoreCase("Active"))) {
			ststus="A";
		}else {
			ststus="L";
		}
		
		
		insQRY = "{call posRegistrationpkg.storestatusmodifyproc(?,?,?,?,?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		cstmt.setString(1, requestJSON.getString("telephone"));
		cstmt.setString(2, requestJSON.getString("addressLine"));
		cstmt.setString(3, requestJSON.getString("localGovernment"));
		cstmt.setString(4, requestJSON.getString("state"));
		cstmt.setString(5, requestJSON.getString("country"));
		cstmt.setString(6, requestJSON.getString("storename"));
		cstmt.setString(7, requestJSON.getString("storeid"));
		cstmt.setString(8, ststus);
		cstmt.setString(9, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(10, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(11, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(11)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(11));
		}else{
			
			responseDTO.addMessages(cstmt.getString(11));
		}
		
		
		


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO InventorySearchDetails(RequestDTO requestDTO) {

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

	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String branchid="";
	String usertype="";


	try {
		responseDTO = new ResponseDTO();

		lmtfeeDataMap = new HashMap<String, Object>();
		lmtJsonArray = new JSONArray();
		feeJsonArray = new JSONArray();
		
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		connection = DBConnector.getConnection();

		
		String wherestr="";
		String whereststus="";
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 
		 String searchid=requestJSON.getString("searchid");
		 
		 if(searchid.equalsIgnoreCase("mobilenumber1")) {
			 wherestr=" AND aci.mobile_number='234"+requestJSON.getString("telephone")+"'" ;
		 }
		 
		 if(searchid.equalsIgnoreCase("storeid1")) {
			 wherestr=" AND UPPER(sm.STORE_NAME) like UPPER('%"+requestJSON.getString("storeid")+"%')" ;
		 }
		 
		 if(searchid.equalsIgnoreCase("terminalid1")) {
			 wherestr=" AND tm.terminal_id='"+requestJSON.getString("terminalid")+"'" ;
		 }
		 
		 if(searchid.equalsIgnoreCase("serialnumber1")) {
			 wherestr=" AND tm.serial_no='"+requestJSON.getString("serialnumber")+"'" ;
		 }
		 
		 if(searchid.equalsIgnoreCase("state1")) {
			 wherestr=" AND UPPER(sm.STATE)=UPPER('"+(requestJSON.getString("state")).split("-")[1]+"')" ;
			 
			 System.out.println("kailash here "+requestJSON.getString("localGovernment"));
			 if(!(requestJSON.getString("localGovernment")).equalsIgnoreCase("")) {
				 wherestr=wherestr+" AND UPPER(sm.LOC_GOV)=UPPER('"+requestJSON.getString("localGovernment")+"')" ; 
			 }
		 }
		 
		 if(!(requestJSON.getString("status")).equalsIgnoreCase("ALL")) {
			 whereststus=" AND tm.STATUS='"+requestJSON.getString("status")+"'" ; 
		 }
		 
		qrey = "select aci.mobile_number,acm.first_name,replace(nvl(aci.address,' '),chr(9),''),tm.terminal_id,tm.serial_no,tm.STATUS,tm.cust_id,aci.R_STATE,aci.RL_LGA from AGENT_CONTACT_INFO ACI,AGENT_CUSTOMER_MASTER ACM,STORE_MASTER SM,TERMINAL_MASTER TM "
				+ "WHERE aci.cust_id=acm.id and aci.cust_id=sm.cust_id and sm.STORE_ID=tm.STORE_ID "+wherestr+" "+whereststus; 

		getlmtfeePstmt = connection.prepareStatement(qrey);

		getlmtfeeRs = getlmtfeePstmt.executeQuery();

		while (getlmtfeeRs.next()) {

			json = new JSONObject();
			json.put("mobile_number", getlmtfeeRs.getString(1));
			json.put("first_name", getlmtfeeRs.getString(2));
			json.put("store_id", getlmtfeeRs.getString(3));
			json.put("terminal_id", getlmtfeeRs.getString(4));
			json.put("serial_no", getlmtfeeRs.getString(5));
			json.put("STATUS", getlmtfeeRs.getString(6));
			json.put("cust_id", getlmtfeeRs.getString(7));
			json.put("state", getlmtfeeRs.getString(8));
			json.put("loc_gov", getlmtfeeRs.getString(9));
			lmtJsonArray.add(json);
		}
		//NotificationJson.addFiled(makerId, "Hai");
		resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
		
		logger.info("Final Json Object ["+resultJson+"]");
		
		lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
		logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
		responseDTO.setData(lmtfeeDataMap);


	} catch (Exception e) {
		logger.debug("Got Exception in View Product Details ["
				+ e.getMessage() + "]");
		e.printStackTrace();
	} finally {
		
		try {

			if (getlmtfeeRs != null) {
				getlmtfeeRs.close();
			}

			if (getlmtfeePstmt != null) {
				getlmtfeePstmt.close();
			}
			
			if (serviceRS != null) {
				serviceRS.close();
			}

			if (servicePstmt != null) {
				servicePstmt.close();
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

public ResponseDTO InventorySearchDetailsView(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	
	logger.debug("inside [AgentDAO][NotificationView]");

	
	
	HashMap<String, Object> viewDataMap = null;
	JSONObject resultJson = null;

	Connection connection = null;

	PreparedStatement viewDataPstmt = null;
	PreparedStatement pstmt = null;
	ResultSet viewRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		logger.debug("[AgentDAO][NotificationView] Inside AgentDAO  "+ requestJSON.getString("storeid"));

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		viewDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		 JSONObject json = new JSONObject();
	
			 String viewReport="select ACM.ACCOUNT_NO,ACM.FIRST_NAME,ACM.MIDDLE_NAME,ACM.LAST_NAME,ACM.ACC_BRANCH,to_char(ACM.DOB,'dd-mm-yyyy'),ACM.EMAILID,ACM.GENDER,MCI.MOBILE_NUMBER,MCI.ADDRESS,MCI.ID_TYPE,MCI.ID_DETAILS,MCI.NATIONALITY,MCI.RL_LGA,MCI.R_STATE,MCI.COUNTRY,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.ID,WAD.ACCT_NO,to_char(ACM.DATE_CREATED,'dd-mm-yyyy hh:mi:ss'),W_PRD_CODE,W_PRD_DESC,AGENCY_TYPE,sm.STORE_NAME,sm.STORE_ID,sm.STATE,sm.LOC_GOV,sm.AREA,sm.ADDRESS,sm.B_OWNER_NAME,DECODE(sm.STATUS,'A','Active','Deactive'),TM.MODEL_NO,TM.TERMINAL_MAKE,TM.SERIAL_NO,DECODE(TM.STATUS,'A','Active','D','Deactive','R','Retrieve'),TERMINAL_ID from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD,STORE_MASTER SM,TERMINAL_MASTER TM "
			 		+ "where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and MCI.cust_id=acm.id and MCI.cust_id=sm.cust_id and sm.STORE_ID=tm.STORE_ID AND TM.SERIAL_NO=?"; 
			 		
				
				viewDataPstmt = connection.prepareStatement(viewReport);
				viewDataPstmt.setString(1,requestJSON.getString("storeid"));
				viewRS = viewDataPstmt.executeQuery();
				
				
				 while(viewRS.next())
					{
					 json=new JSONObject();
					 json.put("ACCOUNT_NO", viewRS.getString(1));
						json.put("FIRST_NAME", viewRS.getString(2));
						json.put("MIDDLE_NAME", viewRS.getString(3));
						json.put("LAST_NAME", viewRS.getString(4));
						json.put("ACC_BRANCH", viewRS.getString(5));
						json.put("DOB", viewRS.getString(6));
						json.put("EMAILID", viewRS.getString(7));
						json.put("GENDER", viewRS.getString(8));
						json.put("MOBILE_NUMBER", viewRS.getString(9));
						json.put("ADDRESS", viewRS.getString(10));
						json.put("ID_TYPE", viewRS.getString(11));
						json.put("ID_DETAILS", viewRS.getString(12));
						json.put("NATIONALITY", viewRS.getString(13));
						json.put("A_RL_LGA", viewRS.getString(14));
						json.put("A_R_STATE", viewRS.getString(15));
						json.put("A_COUNTRY", viewRS.getString(16));
						json.put("A_STATUS", viewRS.getString(17));
						json.put("ID", viewRS.getString(18));
						json.put("W_ACCT_NO", viewRS.getString(19));
						json.put("DATE_CREATED", viewRS.getString(20));
						json.put("W_PRD_CODE", viewRS.getString(21));
						json.put("W_PRD_DESC", viewRS.getString(22));
						json.put("AGENCY_TYPE", viewRS.getString(23));
						
						json.put("STORE_NAME", viewRS.getString(24));
						json.put("STORE_ID", viewRS.getString(25));
						json.put("T_STATE", viewRS.getString(26));
						json.put("T_LOC_GOV", viewRS.getString(27));
						json.put("T_AREA", viewRS.getString(28));
						json.put("T_ADDRESS", viewRS.getString(29));
						json.put("B_OWNER_NAME", viewRS.getString(30));
						json.put("S_STATUS", viewRS.getString(31));
						
						json.put("MODEL_NO", viewRS.getString(32));
						json.put("TERMINAL_MAKE", viewRS.getString(33));
						json.put("SERIAL_NO", viewRS.getString(34));
						json.put("T_STATUS", viewRS.getString(35));
						json.put("TERMINAL_ID", viewRS.getString(36));
						
					 } 
				 

		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
			
        } catch (SQLException e) {
		logger.debug("SQLException in gtFileApprovalAction [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][gtFileApprovalAction] Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtFileApprovalAction] SQLException in gtFileApprovalAction [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][gtFileApprovalAction] Internal Error Occured While Executing.");
	} finally {
		try {

			if (viewDataPstmt != null)
				viewDataPstmt.close();
			if (viewRS != null)
				viewRS.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		viewDataMap = null;
		resultJson = null;
		
	}

	return responseDTO;
}

public ResponseDTO terminalManagement(RequestDTO requestDTO) {

	logger.debug(" Inside fetchProductGridInfo.. ");

	HashMap<String, Object> lmtfeeDataMap = null;
	JSONArray lmtJsonArray = null;
	JSONArray feeJsonArray = null;
	
	
	JSONObject resultJson = null;
	JSONObject json = null;

	PreparedStatement getlmtfeePstmt = null;
	ResultSet getlmtfeeRs = null;

	Connection connection = null;

	String lmtfeeQry = "select TERMINAL_ID,SERIAL_NO,MODEL_NO,TERMINAL_MAKE,STATUS,to_char(MAKER_DTTM,'dd-mm-yyyy hh24:mi:ss') from TERMINAL_MASTER order by MAKER_DTTM";



	try {
		responseDTO = new ResponseDTO();

		lmtfeeDataMap = new HashMap<String, Object>();
		lmtJsonArray = new JSONArray();
		feeJsonArray = new JSONArray();
		
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		connection = DBConnector.getConnection();

		logger.debug("connection is null [" + connection == null + "]");

		getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

		getlmtfeeRs = getlmtfeePstmt.executeQuery();

		while (getlmtfeeRs.next()) {
			json = new JSONObject();
			json.put("TERMINAL_ID", getlmtfeeRs.getString(1));
			json.put("SERIAL_NO", getlmtfeeRs.getString(2));
			json.put("MODEL_NO", getlmtfeeRs.getString(3));
			json.put("TERMINAL_MAKE", getlmtfeeRs.getString(4));
			json.put("STATUS", getlmtfeeRs.getString(5));
			json.put("MAKER_DTTM", getlmtfeeRs.getString(6));
			
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

public ResponseDTO terminalCreation(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [POSDetailsDAO][POSAgentAdd]");

	
	
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
		 JSONArray JsonArray= new JSONArray();
		 
		 
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 String Actionname="";
		 String Actiondesc="";
		
		
			 
			  servicePstmt = connection.prepareStatement("select TERMINALID_SEQ.nextval FROM DUAL");
			  serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					
					json.put("terminalid",serviceRS.getString(1));
					
					}
				 servicePstmt.close();
				 serviceRS.close();
			
		 
			
		 
		 	JSONObject jsonaudit = new JSONObject();
		 	jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
		 	jsonaudit.put("transCode", Actionname);
		 	jsonaudit.put("channel", "WEB");
		 	jsonaudit.put("message", Actiondesc);
		 	jsonaudit.put("ip", remoteip);
		 	jsonaudit.put("det1", "");
		 	jsonaudit.put("det2", "");
		 	jsonaudit.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(jsonaudit);
			
			connection.commit();
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[POSDetailsDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
				+ "]");
		responseDTO.addError("[POSDetailsDAO][AgentRegModify] Internal Error Occured While Executing.");
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

public ResponseDTO terminalCreationAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		
		insQRY = "{call posRegistrationpkg.terminalcreationproc(?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		
		cstmt.setString(1, requestJSON.getString("terminalid"));
		cstmt.setString(2, requestJSON.getString("terminalmake"));
		cstmt.setString(3, requestJSON.getString("modelnumber"));
		cstmt.setString(4, requestJSON.getString("serialnumber"));			
		cstmt.setString(5, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(6, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(7, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(7)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(7));
		}else{
			
			responseDTO.addMessages(cstmt.getString(7));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO terminalCreationView(RequestDTO requestDTO) {

	logger.debug("Inside fetchlimitcodeInfo.. ");
	HashMap<String, Object> limitcodeDataMap = null;

	JSONObject resultJson = null;
	JSONObject json = null;
	JSONArray JsonArray= new JSONArray();
	PreparedStatement getlimitcodePstmt = null;
	ResultSet getlimitcodeRs = null;
	Connection connection = null;

	String  terminalid = "";
	

	try {
		requestJSON = requestDTO.getRequestJSON();
		responseDTO = new ResponseDTO();
		limitcodeDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		connection = DBConnector.getConnection();

		terminalid = requestJSON.getString("terminalid");
		
		String  limitcodeCountQry = "select TERMINAL_ID,SERIAL_NO,MODEL_NO,TERMINAL_MAKE,DECODE(STATUS,'A','Active','D','Deactived','R','Retrival','P','Not yet Assign'),to_char(MAKER_DTTM,'dd-mm-yyyy') FROM TERMINAL_MASTER WHERE TERMINAL_ID=? ";

		getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
		getlimitcodePstmt.setString(1,terminalid);

		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		json = new JSONObject();

		if (getlimitcodeRs.next()) {	
			
			json.put("TERMINAL_ID", getlimitcodeRs.getString(1));
		     json.put("SERIAL_NO", getlimitcodeRs.getString(2));
		     json.put("MODEL_NO", getlimitcodeRs.getString(3));
		     json.put("TERMINAL_MAKE", getlimitcodeRs.getString(4));
		     json.put("STATUS", getlimitcodeRs.getString(5));
		     json.put("MAKER_DTTM", getlimitcodeRs.getString(6));
		}
		
		getlimitcodePstmt.close();
		getlimitcodeRs.close();
		
		
		
		if(!(json.get("STATUS")).equals("Not yet Assign")) {
			
			String  limitcodeCountQry1 = "select SM.STORE_NAME,SM.STORE_ID,SM.AREA,SM.STATE,SM.LOC_GOV,SM.COUNTRY,TM.USER_ID,to_char(UTM.MAKER_DTTM,'dd-mm-yyyy') from STORE_MASTER SM,TERMINAL_MASTER TM,USER_TERMINAL_MAPPING UTM where SM.STORE_ID=TM.STORE_ID AND UTM.STORE_ID=TM.STORE_ID and TM.TERMINAL_ID=? ";

			getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry1);
			getlimitcodePstmt.setString(1,terminalid);
			getlimitcodeRs = getlimitcodePstmt.executeQuery();
			
			if (getlimitcodeRs.next()) {	
				
				json.put("STORE_NAME", getlimitcodeRs.getString(1));
			     json.put("STORE_ID", getlimitcodeRs.getString(2));
			     json.put("AREA", getlimitcodeRs.getString(3));
			     json.put("STATE", getlimitcodeRs.getString(4));
			     json.put("LOC_GOV", getlimitcodeRs.getString(5));
			     json.put("COUNTRY", getlimitcodeRs.getString(6));
			     json.put("USER_ID", getlimitcodeRs.getString(7));
			     json.put("ASS_MAKER_DTTM", getlimitcodeRs.getString(8));
			}
			
		}else {
			json.put("STORE_NAME", "");
		     json.put("STORE_ID", "");
		     json.put("AREA", "");
		     json.put("STATE", "");
		     json.put("LOC_GOV", "");
		     json.put("COUNTRY", "");
		     json.put("USER_ID", "");
		     json.put("ASS_MAKER_DTTM","");
		}

		resultJson.put("limitcodedetails", json);	
		
		
		
		
		
		    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
		    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
		    responseDTO.setData(limitcodeDataMap);

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


public ResponseDTO terminalCreationModify(RequestDTO requestDTO) {

	logger.debug("Inside fetchlimitcodeInfo.. ");
	HashMap<String, Object> limitcodeDataMap = null;

	JSONObject resultJson = null;
	JSONObject json = null;
	JSONArray JsonArray= new JSONArray();
	PreparedStatement getlimitcodePstmt = null;
	ResultSet getlimitcodeRs = null;
	Connection connection = null;

	String  terminalid = "";
	

	try {
		requestJSON = requestDTO.getRequestJSON();
		responseDTO = new ResponseDTO();
		limitcodeDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		connection = DBConnector.getConnection();

		terminalid = requestJSON.getString("terminalid");
		
		String  limitcodeCountQry = "select TERMINAL_ID,SERIAL_NO,MODEL_NO,TERMINAL_MAKE,DECODE(STATUS,'A','Active','D','Deactived','R','Retrival','P','Not yet Assign'),to_char(MAKER_DTTM,'dd-mm-yyyy') FROM TERMINAL_MASTER WHERE TERMINAL_ID=? ";

		getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
		getlimitcodePstmt.setString(1,terminalid);

		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		json = new JSONObject();

		if (getlimitcodeRs.next()) {	
			
			json.put("TERMINAL_ID", getlimitcodeRs.getString(1));
		     json.put("SERIAL_NO", getlimitcodeRs.getString(2));
		     json.put("MODEL_NO", getlimitcodeRs.getString(3));
		     json.put("TERMINAL_MAKE", getlimitcodeRs.getString(4));
		     json.put("STATUS", getlimitcodeRs.getString(5));
		     json.put("MAKER_DTTM", getlimitcodeRs.getString(6));
		}
		
		getlimitcodePstmt.close();
		getlimitcodeRs.close();
		
		
		
		

		resultJson.put("limitcodedetails", json);	
		
		
		
		
		
		    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
		    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
		    responseDTO.setData(limitcodeDataMap);

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

public ResponseDTO terminalCreationModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		
		insQRY = "{call posRegistrationpkg.terminalmodifyproc(?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		
		cstmt.setString(1, requestJSON.getString("terminalid"));
		cstmt.setString(2, requestJSON.getString("terminalmake"));
		cstmt.setString(3, requestJSON.getString("modelnumber"));
		cstmt.setString(4, requestJSON.getString("serialnumber"));			
		cstmt.setString(5, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(6, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(7, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(7)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(7));
		}else{
			
			responseDTO.addMessages(cstmt.getString(7));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

public ResponseDTO terminalCreationStatusAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [POSDetailsDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	String insQRY = "";
	String ip = "";
	CallableStatement cstmt = null;
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		/*String telephone=requestJSON.getString("telephone");
		String terminalmake=requestJSON.getString("terminalmake");
		String modelnumber=requestJSON.getString("modelnumber");
		String serialnumber=requestJSON.getString("serialnumber");*/
		
		
		insQRY = "{call posRegistrationpkg.terminalstatusproc(?,?,?,?,?,?,?)}";

		
		ip = requestJSON.getString(CevaCommonConstants.IP);


		cstmt = connection.prepareCall(insQRY);
		
		cstmt.setString(1, requestJSON.getString("terminalid"));
		cstmt.setString(2, requestJSON.getString("terminalmake"));
		cstmt.setString(3, requestJSON.getString("modelnumber"));
		cstmt.setString(4, requestJSON.getString("serialnumber"));			
		cstmt.setString(5, requestJSON.getString(CevaCommonConstants.MAKER_ID));	
		cstmt.setString(6, requestJSON.getString(CevaCommonConstants.IP));
		
		cstmt.registerOutParameter(7, Types.VARCHAR);
		cstmt.executeQuery();

		if (!(cstmt.getString(7)).contains("SUCCESS")) {
			responseDTO.addError(cstmt.getString(7));
		}else{
			
			responseDTO.addMessages(cstmt.getString(7));
		}
		
		
		
		/*connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO TERMINAL_MASTER(USER_ID,TERMINAL_ID) VALUES(?,?)");
		pstmt.setString(1, telephone);
		pstmt.setString(2, telephone);
		
		
		pstmt.executeUpdate();
		connection.commit();*/


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[POSDetailsDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (cstmt != null)
				cstmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

}
