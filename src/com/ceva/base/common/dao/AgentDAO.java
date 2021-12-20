package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.aestools.testcls;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.reports.CSVReportGeneration;
import com.ceva.base.reports.ExcelReportGeneration;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.unionbank.channel.CallAgentServices;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.unionbank.channel.SettlementServiceCall;
import com.ceva.util.NotificationJson;
import com.ceva.util.SpecialCharUtils;
import com.ceva.wallet.core.service.ServiceWrapper;

public class AgentDAO {
	private static Logger logger = Logger.getLogger(AgentDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	
	public ResponseDTO gtAgentRegistrtionack(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;

		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();


			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			String fname=requestJSON.getString("fname");
			String lname=requestJSON.getString("lname");
			String dob=requestJSON.getString("dob");
			String mnumber=requestJSON.getString("mnumber");
			String mailid=requestJSON.getString("mailid");
			String gender=requestJSON.getString("gender");
			String offaddress=requestJSON.getString("offaddress");
			String peraddress=requestJSON.getString("peraddress");
			
			
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("INSERT INTO DSA_REGISTRATION(ref_no,fname,lname,dob,mobile_no,mail_id,gender,off_address,Per_address) VALUES(AGENT_SEQ.nextval,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?)");
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, dob);
			pstmt.setString(4, mnumber);
			pstmt.setString(5, mailid);
			pstmt.setString(6, gender);
			pstmt.setString(7, offaddress);
			pstmt.setString(8, peraddress);
			pstmt.executeUpdate();
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
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
	
	public ResponseDTO AgentRegModifySearch(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String userReport="";
			String actiontype=requestJSON.getString("actiontype");
			String status=requestJSON.getString("status");
			/*if(type.equalsIgnoreCase("PROCESS")){
				userReport =" select  REF_NO,USER_ID,MOBILE_NO FROM USSD_REQUEST WHERE STATUS='P'";
			}else{*/
				//userReport =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active') FROM USSD_REQUEST WHERE REQUEST_TYPE in ('LIMITUPDATE','NEWREG','REGMODIFY','ACTIVE')";
			//}
				
			if(status.equalsIgnoreCase("PENDING")){
				if(actiontype.equalsIgnoreCase("DSANEWREG")){
					userReport =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active') FROM USSD_REQUEST WHERE REQUEST_TYPE='NEWREG'";
					
				}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
					userReport =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active') FROM USSD_REQUEST WHERE REQUEST_TYPE='REGMODIFY'";
	
				}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
					userReport =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active') FROM USSD_REQUEST WHERE REQUEST_TYPE='LIMITUPDATE'";
	
				}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
					userReport =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active','DEACTIVE','Deactive') FROM USSD_REQUEST WHERE REQUEST_TYPE in ('ACTIVE','DEACTIVE')";
	
				}
				servicePstmt = connection.prepareStatement(userReport);
				serviceRS = servicePstmt.executeQuery();
				JSONObject json = new JSONObject();
				
				 while(serviceRS.next())
					{
					 json=new JSONObject();
					 json.put("REF_NO",serviceRS.getString(1));
					 json.put("USER_ID",serviceRS.getString(2));
					 json.put("MOBILE_NO",serviceRS.getString(3));
					 json.put("REQUEST_TYPE",serviceRS.getString(4));
					 IncomeMTFilesJSONArray.add(json);
					}
					
				
			}else{
				if(status.equalsIgnoreCase("AUTHORIZED")){
				
				if(actiontype.equalsIgnoreCase("DSANEWREG")){
					userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSANEWREG' AND AP.STATUS='C'";
					
				}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
					userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAREGMOD' AND AP.STATUS='C'";
	
				}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
					userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSALMTUPT' AND AP.STATUS='C'";
	
				}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
					userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAUSRACT' AND AP.STATUS='C'";
	
				}
				
				}else if(status.equalsIgnoreCase("REJECTED")){
					
					if(actiontype.equalsIgnoreCase("DSANEWREG")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSANEWREG' AND AP.STATUS='R'";
						
					}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAREGMOD' AND AP.STATUS='R'";
		
					}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSALMTUPT' AND AP.STATUS='R'";
		
					}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAUSRACT' AND AP.STATUS='R'";
		
					}
				}else if(status.equalsIgnoreCase("DELETED")){
					
					if(actiontype.equalsIgnoreCase("DSANEWREG")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSANEWREG' AND AP.STATUS='D'";
						
					}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAREGMOD' AND AP.STATUS='D'";
		
					}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSALMTUPT' AND AP.STATUS='D'";
		
					}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
						userReport =" select  URH.USERID,URH.MOBILE_NO,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.MESSAGE FROM USSD_REQUEST_HISTORY URH,AUTH_PENDING AP WHERE URH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='DA' AND AP.AUTH_CODE='DSAUSRACT' AND AP.STATUS='D'";
		
					}
				}
				servicePstmt = connection.prepareStatement(userReport);
				serviceRS = servicePstmt.executeQuery();
				JSONObject json = new JSONObject();
				
				 while(serviceRS.next())
					{
					 json=new JSONObject();
					 json.put("USER_ID",serviceRS.getString(1));
					 json.put("MOBILE_NO",serviceRS.getString(2));
					 json.put("MAKER_ID",serviceRS.getString(3));
					 json.put("MAKER_DTTM",serviceRS.getString(4));
					 json.put("CHECKER_ID",serviceRS.getString(5));
					 json.put("CHECKER_DTTM",serviceRS.getString(6));
					 json.put("MESSAGE",serviceRS.getString(7));
					 IncomeMTFilesJSONArray.add(json);
					}
			}
				
			
			

			

			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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
	public ResponseDTO AgentRegModify(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [AgentDAO][AgentRegModify]");

		
		
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
			
			
				 
				  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME,ACM.MIDDLE_NAME,ACM.LAST_NAME,ACM.ACC_BRANCH,to_char(ACM.DOB,'dd-mm-yyyy'),ACM.EMAILID,ACM.GENDER,ACM.SUPER_ADM,ACM.W_PRD_CODE,MCI.MOBILE_NUMBER,"
				  		+ "MCI.ADDRESS,MCI.ID_TYPE,MCI.ID_DETAILS,MCI.NATIONALITY,MCI.RL_LGA,MCI.R_STATE,MCI.COUNTRY,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.ID,NVL(ACM.USER_ID,' '),WAD.ACCT_NO,to_char(ACM.DATE_CREATED,'dd-mm-yyyy hh:mi:ss'),W_PRD_CODE,W_PRD_DESC,AGENCY_TYPE from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.ID='"+userid+"'");
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
						json.put("userid",serviceRS.getString(21));
						json.put("walletaccountno",serviceRS.getString(22));
						json.put("onboarddate",serviceRS.getString(23));
						
						json.put("product",serviceRS.getString(24));
						json.put("prodesc",serviceRS.getString(25));
						json.put("agenttype",serviceRS.getString(26));
						
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

			logger.debug("[AgentDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
					+ "]");
			responseDTO.addError("[AgentDAO][AgentRegModify] Internal Error Occured While Executing.");
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
	
	
	public ResponseDTO AgentRegModifyAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			
			String displayname=requestJSON.getString("displayname");
			String userid=requestJSON.getString("userid");
			String empno=requestJSON.getString("empno");
			String fname=requestJSON.getString("fname");
			String lname=requestJSON.getString("lname");
			String jtitle=requestJSON.getString("jtitle");
			String mnumber=requestJSON.getString("mnumber");
			String mailid=requestJSON.getString("mailid");
			String branchcode=requestJSON.getString("branchcode");
			String damtlmt=requestJSON.getString("damtlmt");
			String numoftran=requestJSON.getString("numoftran");
			String ptamt=requestJSON.getString("ptamt");
			String limitcode=requestJSON.getString("limitcode");
			
			 
			 
			 
			 servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 auth_seq=serviceRS.getString(1);
					}
				
			
			
			pstmt2 = connection.prepareStatement("INSERT INTO  USSD_REQUEST(REF_NO,USERID,DISPLAYNAME,EMPNO,FNAME,LNAME,JOB_TITLE,MOBILE_NO,MAILID,BRANCH_DETAILS,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,REQUEST_TYPE,MAKER_ID,LIMIT_CODE,AUT_REF) values(AGENT_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,'A','NEWREG',?,?,?)");
			
			
			pstmt2.setString(1, userid.toUpperCase());
			pstmt2.setString(2, displayname);
			pstmt2.setString(3, empno);
			pstmt2.setString(4, fname);
			pstmt2.setString(5, lname);
			pstmt2.setString(6, jtitle);
			pstmt2.setString(7, mnumber);
			pstmt2.setString(8, mailid);
			pstmt2.setString(9, branchcode);
			pstmt2.setString(10, damtlmt);
			pstmt2.setString(11, numoftran);
			pstmt2.setString(12, ptamt);
			pstmt2.setString(13, makerId);
			pstmt2.setString(14, limitcode);
			pstmt2.setString(15, auth_seq);
			pstmt2.executeUpdate();
			

			pstmt2.close();
			pstmt2 = connection.prepareStatement("INSERT INTO  AGENT_INFO_COMMON_TEMP(USER_ID,USER_REF_NO,MOBILE_NO,USER_TYPE,REF_NUM) values(?,AGENT_INFO_COMMON_SEQ.nextval,?,?,?)");
			
			
			pstmt2.setString(1, userid.toUpperCase());
			pstmt2.setString(2, mnumber.replaceAll("\\+", ""));
			pstmt2.setString(3, "DSA");
			pstmt2.setString(4, auth_seq);
			pstmt2.executeUpdate();
			
			pstmt2.close();
			pstmt2 = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'DSANEWREG','P','DA',?)");
			
			
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "dsaregistration");
			json.put("channel", "WEB");
			json.put("message", "Acknowledgment :: DSA registered User Id "+userid+" and Assign Product Code is "+limitcode);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	
	
	public ResponseDTO AgentLimitUpdateAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			
			String displayname=requestJSON.getString("displayname");
			String userid=requestJSON.getString("userid");
			String empno=requestJSON.getString("empno");
			String fname=requestJSON.getString("fname");
			String lname=requestJSON.getString("lname");
			String jtitle=requestJSON.getString("jtitle");
			String mnumber=requestJSON.getString("mnumber");
			String mailid=requestJSON.getString("mailid");
			String branchcode=requestJSON.getString("branchcode");
			String damtlmt=requestJSON.getString("updatedamtlmt");
			String numoftran=requestJSON.getString("updatenumoftran");
			String ptamt=requestJSON.getString("updateptamt");
			String refno=requestJSON.getString("refno");
			String limitcode=requestJSON.getString("limitcode");
			
			 servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 auth_seq=serviceRS.getString(1);
					}
			
			
			pstmt2 = connection.prepareStatement("INSERT INTO  USSD_REQUEST_TEMP(REF_NO,USERID,DISPLAYNAME,EMPNO,FNAME,LNAME,JOB_TITLE,MOBILE_NO,MAILID,BRANCH_DETAILS,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,REQUEST_TYPE,MAKER_ID,LIMIT_CODE,AUT_REF) values("+refno+",?,?,?,?,?,?,?,?,?,?,?,?,'A','DSA',?,?,?)");
			
			
			pstmt2.setString(1, userid);
			pstmt2.setString(2, displayname);
			pstmt2.setString(3, empno);
			pstmt2.setString(4, fname);
			pstmt2.setString(5, lname);
			pstmt2.setString(6, jtitle);
			pstmt2.setString(7, mnumber);
			pstmt2.setString(8, mailid);
			pstmt2.setString(9, branchcode);
			pstmt2.setString(10, damtlmt);
			pstmt2.setString(11, numoftran);
			pstmt2.setString(12, ptamt);
			pstmt2.setString(13, makerId);
			pstmt2.setString(14, limitcode);
			pstmt2.setString(15, auth_seq);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			pstmt1 = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE='LIMITUPDATE',AUT_REF=? WHERE REF_NO=?");
			pstmt1.setString(1, auth_seq);
			pstmt1.setString(2, refno);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'DSALMTUPT','P','DA',?)");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "dsalimitupdate");
			json.put("channel", "WEB");
			json.put("message", "Acknowledgment :: DSA Product Update Code is  "+limitcode+" for User Id "+userid);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	
	
	public ResponseDTO AgentProductUpdateAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			String limitcode=requestJSON.getString("limitcode");
			String agentid=requestJSON.getString("MERCHANT_ID");
			
			
			
			 servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 auth_seq=serviceRS.getString(1);
					}
			
				 pstmt1 = connection.prepareStatement("UPDATE  MERCHANT_MASTER set REF_NUM=? WHERE MERCHANT_ID=?");
					pstmt1.setString(1, auth_seq);
					pstmt1.setString(2, agentid);
					pstmt1.executeUpdate();
					pstmt1.executeUpdate();
					pstmt1.close();
					
			pstmt2 = connection.prepareStatement("INSERT INTO  MERCHANT_MASTER_TEMP select * from MERCHANT_MASTER WHERE MERCHANT_ID='"+agentid+"'");
			
			pstmt2.executeUpdate();
			pstmt2.close();
			
			pstmt1 = connection.prepareStatement("UPDATE  MERCHANT_MASTER_TEMP set PRODUCT=? WHERE REF_NUM=?");
			pstmt1.setString(1, limitcode);
			pstmt1.setString(2, auth_seq);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'MERCHPRODUCTAUTH','P','AM',?)");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "dsalimitupdate");
			json.put("channel", "WEB");
			json.put("message", "Agent Product Update Code is  "+limitcode+" for Agent User id "+agentid);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	
	
	
	public ResponseDTO AgentRegApprovalAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegApprovalAck]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			
			String Narration=requestJSON.getString("Narration");
			String decision=requestJSON.getString("decision");
			String mnumber=requestJSON.getString("mnumber");
			String userid=requestJSON.getString("userid");
			String refno=requestJSON.getString("refno");
			String displayname=requestJSON.getString("displayname");
			String mailid=requestJSON.getString("mailid");
			String branchcode=requestJSON.getString("branchcode");
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			String limitcode=requestJSON.getString("limitcode");
			
			
			 servicePstmt = connection.prepareStatement("select AUT_REF from USSD_REQUEST where REF_NO="+refno+"");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 auth_seq=serviceRS.getString(1);
					}
			
				 servicePstmt.close();
				 serviceRS.close();
				 
			String common_query="select dbms_random.string('C',1)||round(dbms_random.value(100,999))||dbms_random.string('U',1)||lpad(commonid_seq.nextval,6,'0')  from dual";

			String desc="";
			String common_id="";
			if(decision.equalsIgnoreCase("Approval")){
				desc="C";

				servicePstmt = connection.prepareStatement(common_query);
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 common_id=serviceRS.getString(1);
					}
				
				 CommonServiceDao csd=new CommonServiceDao();
				
				 
				 JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				 JSONObject respref =  csd.genaratedevicerefno().getJSONObject("data");
				 
				 String pinref=respref.getString("pin");
				 
				pstmt = connection.prepareStatement("INSERT INTO USER_LOGIN_CREDENTIALS(LOGIN_USER_ID,PASSWORD,COMMON_ID,MAKER_ID,MAKER_DTTM,APPL_CODE) "
						+ " values('"+mnumber.replaceAll("\\+", "")+"','"+resp.getString("pinHash")+"','"+common_id+"','"+makerId+"',sysdate,'banking')");
				pstmt.executeUpdate();
				pstmt.close();
				
				requestJSON.put("commonid", common_id);
				requestJSON.put("referenceno", pinref);
				
				csd.insertIMEI(requestJSON);
				
				ServiceRequestClient.sms(mnumber, "Dear "+displayname+", please find the DSA Mobile App Activation Number "+pinref);
				
				pstmt1 = connection.prepareStatement("INSERT INTO USER_INFORMATION(COMMON_ID,USER_NAME,USER_STATUS,USER_TYPE,APPL_CODE,EMAIL,MOBILE_NO,LOCATION,MAKERID,MAKERDTTM) "
						+ " values('"+common_id+"','"+userid+"','F','DSA','banking','"+mailid+"','"+mnumber+"','"+branchcode+"','"+makerId+"',sysdate)");
				pstmt1.executeUpdate();
				
				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set STATUS='"+desc+"',MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',COMMON_ID='"+common_id+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				
				
				pstmt2 = connection.prepareStatement("INSERT INTO AGENT_INFO_COMMON SELECT * FROM AGENT_INFO_COMMON_TEMP where REF_NUM=?");
				
				pstmt2.setString(1, auth_seq);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				
				pstmt2 = connection.prepareStatement("UPDATE  AGENT_INFO_COMMON set COMMON_ID='"+common_id+"',AGENT_TYPE='INTERNAL' where REF_NUM=?");
				
				pstmt2.setString(1, auth_seq);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				
				
				pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
				pstmt2.setString(1, makerId);
				pstmt2.setString(2, auth_seq);
				pstmt2.executeUpdate();
				
				
			}else{
				
				desc="R";
				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set STATUS='"+desc+"',MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',COMMON_ID='"+common_id+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				
				pstmt2.close();
				pstmt2 = connection.prepareStatement("DELETE FROM USSD_REQUEST where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				
				pstmt2.close();
				
				pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
				pstmt2.setString(1, makerId);
				pstmt2.setString(2, auth_seq);
				pstmt2.executeUpdate();
				
			}
			
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "ApprovalAll");
			json.put("channel", "WEB");
			json.put("message", "New Registration Authorization at Decision "+decision+" User Id "+userid);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	public ResponseDTO AgentRegApprovalLimitUpdateAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegApprovalAck]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			
			String Narration=requestJSON.getString("Narration");
			String decision=requestJSON.getString("decision");
			String mnumber=requestJSON.getString("mnumber");
			String userid=requestJSON.getString("userid");
			String refno=requestJSON.getString("refno");
			String displayname=requestJSON.getString("displayname");
			String mailid=requestJSON.getString("mailid");
			String branchcode=requestJSON.getString("branchcode");
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String updatedamtlmt=requestJSON.getString("updatedamtlmt");
			String updatenumoftran=requestJSON.getString("updatenumoftran");
			String updateptamt=requestJSON.getString("updateptamt");
			String updatelimitcode=requestJSON.getString("updatelimitcode");
			
			servicePstmt = connection.prepareStatement("select AUT_REF from USSD_REQUEST where REF_NO="+refno+"");
			serviceRS = servicePstmt.executeQuery();
			
			 while(serviceRS.next())
				{
				 auth_seq=serviceRS.getString(1);
				}
		
			 servicePstmt.close();
			 serviceRS.close();
		
			
			if(decision.equalsIgnoreCase("Approval")){
				

				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set DAILY_AMT='"+updatedamtlmt+"',DAILY_TXN='"+updatenumoftran+"',PER_AMT='"+updateptamt+"',MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',LIMIT_CODE='"+updatelimitcode+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				pstmt2.close();
				
				pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
					
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
					
				pstmt2.close();
					
				pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
				pstmt2.setString(1, makerId);
				pstmt2.setString(2, auth_seq);
				pstmt2.executeUpdate();
				
				pstmt2 = connection.prepareStatement("DELETE FROM  USSD_REQUEST_TEMP where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				connection.commit();
				
			}else{
				
				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST_TEMP set MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				pstmt2.close();
				
				pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST_TEMP where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
					
				pstmt2.close();
					
				pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
				pstmt2.setString(1, makerId);
				pstmt2.setString(2, auth_seq);
				pstmt2.executeUpdate();
				
				pstmt2 = connection.prepareStatement("DELETE FROM  USSD_REQUEST_TEMP where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				connection.commit();
				
				
			}
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "ApprovalAll");
			json.put("channel", "WEB");
			json.put("message", "Limit Update at Decision "+decision+" User Id "+userid);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	
	
	public ResponseDTO AgentRegLockAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		String decision="";
		String Narration="";
		String actions="";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();


			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			String refno=requestJSON.getString("refno");
			String status=requestJSON.getString("status");
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			String userid=requestJSON.getString("userid");
			
			//System.out.println("status :: "+status);
			connection.setAutoCommit(false);
			
			if(status.equalsIgnoreCase("Unlock")){
				pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION set USER_STATUS=? where COMMON_ID=?");
				pstmt.setString(1, "L");
				pstmt.setString(2, refno);
				pstmt.executeUpdate();
				actions="luck";
			}else if(status.equalsIgnoreCase("Deactive")){
				
				 
				 servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
					serviceRS = servicePstmt.executeQuery();
					
					 while(serviceRS.next())
						{
						 auth_seq=serviceRS.getString(1);
						}
			
				 servicePstmt.close();
				 serviceRS.close();
				servicePstmt = connection.prepareStatement("SELECT AGENT_TYPE FROM AGENT_INFO_COMMON where UPPER(USER_ID)=UPPER('"+userid+"')");
				serviceRS = servicePstmt.executeQuery();
				
				 if(serviceRS.next())
					{
					
					 if((serviceRS.getString(1)).equalsIgnoreCase("EXTERNAL")){
						 
						 
							pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION SET REF_NUM=? where COMMON_ID=?");						
							pstmt.setString(1, auth_seq);
							pstmt.setString(2, refno);
							pstmt.executeUpdate();
							pstmt.close();
								
						 	pstmt = connection.prepareStatement("INSERT INTO  USER_INFORMATION_TEMP SELECT * FROM USER_INFORMATION where COMMON_ID=?");						
							pstmt.setString(1, refno);
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION_TEMP SET USER_TYPE='A' where REF_NUM=?");						
							pstmt.setString(1, auth_seq);
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'STRUSERSTATUSAUTH','P','AM',?)");
							pstmt.setString(1, makerId);
							pstmt.setString(2, auth_seq);
							pstmt.executeUpdate();
						 
					 }
					 
					 
					if((serviceRS.getString(1)).equalsIgnoreCase("INTERNAL")){
					
					servicePstmt = connection.prepareStatement("SELECT COUNT(*) FROM USSD_REQUEST where UPPER(USERID)=UPPER('"+userid+"') AND REQUEST_TYPE='ACTIVE'");
					serviceRS = servicePstmt.executeQuery();
					
					 if(serviceRS.next())
						{
							if((serviceRS.getString(1)).equalsIgnoreCase("0")){
								
								
								
								pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,AUT_REF=? where COMMON_ID=?");
								pstmt.setString(1, "ACTIVE");
								pstmt.setString(2, auth_seq);
								pstmt.setString(3, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'DSAUSRACT','P','DA',?)");
								pstmt.setString(1, makerId);
								pstmt.setString(2, auth_seq);
								pstmt.executeUpdate();
								
								
							}else{
								
								decision=requestJSON.getString("decision");
								Narration=requestJSON.getString("Narration");
								
								
								
								
								if(decision.equalsIgnoreCase("Approval")){
									pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION set USER_STATUS=? where COMMON_ID=(SELECT COMMON_ID FROM USSD_REQUEST where REF_NO=?)");
									pstmt.setString(1, "A");
									pstmt.setString(2, refno);
									pstmt.executeUpdate();
									pstmt.close();
									
									pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,MESSAGE=? where REF_NO=?");
									pstmt.setString(1, "DSA");
									pstmt.setString(2, Narration);
									pstmt.setString(3, refno);
									pstmt.executeUpdate();
									pstmt.close();
									
									pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
									
									pstmt.setString(1, refno);
									pstmt.executeUpdate();
									
									pstmt.close();
									
									pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
									pstmt.setString(1, makerId);
									pstmt.setString(2, auth_seq);
									pstmt.executeUpdate();
									
								}else{
									
									pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,MESSAGE=? where REF_NO=?");
									pstmt.setString(1, "DSA");
									pstmt.setString(2, Narration);
									pstmt.setString(3, refno);
									pstmt.executeUpdate();
									pstmt.close();
									
									pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
									
									pstmt.setString(1, refno);
									pstmt.executeUpdate();
									
									pstmt.close();
									
									pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
									pstmt.setString(1, makerId);
									pstmt.setString(2, auth_seq);
									pstmt.executeUpdate();
									
									
								}
								
							}
						
						}
					}
			}
				 actions="active";
			}else if(status.equalsIgnoreCase("Active")){
				
				servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					 auth_seq=serviceRS.getString(1);
					}
				 servicePstmt.close();
				 serviceRS.close();
				 
				servicePstmt = connection.prepareStatement("SELECT AGENT_TYPE FROM AGENT_INFO_COMMON where UPPER(USER_ID)=UPPER('"+userid+"')");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					
					 if((serviceRS.getString(1)).equalsIgnoreCase("EXTERNAL")){
						 
						 
							pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION SET REF_NUM=? where COMMON_ID=?");						
							pstmt.setString(1, auth_seq);
							pstmt.setString(2, refno);
							pstmt.executeUpdate();
							pstmt.close();
								
						 	pstmt = connection.prepareStatement("INSERT INTO  USER_INFORMATION_TEMP SELECT * FROM USER_INFORMATION where COMMON_ID=?");						
							pstmt.setString(1, refno);
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION_TEMP SET USER_TYPE='D' where REF_NUM=?");						
							pstmt.setString(1, auth_seq);
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'STRUSERSTATUSAUTH','P','AM',?)");
							pstmt.setString(1, makerId);
							pstmt.setString(2, auth_seq);
							pstmt.executeUpdate();
						 
					 }
					   if((serviceRS.getString(1)).equalsIgnoreCase("INTERNAL")){
						servicePstmt.close();
						serviceRS.close();
						
						servicePstmt = connection.prepareStatement("SELECT COUNT(*) FROM USSD_REQUEST where UPPER(USERID)=UPPER('"+userid+"') AND REQUEST_TYPE='DEACTIVE'");
						serviceRS = servicePstmt.executeQuery();
						
						 while(serviceRS.next())
							{
								if((serviceRS.getString(1)).equalsIgnoreCase("0")){
									
									
									
									pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,AUT_REF=? where COMMON_ID=?");
									pstmt.setString(1, "DEACTIVE");
									pstmt.setString(2, auth_seq);
									pstmt.setString(3, refno);
									pstmt.executeUpdate();
									pstmt.close();
									
									pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'DSAUSRACT','P','DA',?)");
									pstmt.setString(1, makerId);
									pstmt.setString(2, auth_seq);
									pstmt.executeUpdate();
									
									
								}else{
									
									decision=requestJSON.getString("decision");
									Narration=requestJSON.getString("Narration");
									servicePstmt.close();
									serviceRS.close();
									
									servicePstmt = connection.prepareStatement("select AUT_REF from USSD_REQUEST where REF_NO="+refno+"");
									serviceRS = servicePstmt.executeQuery();
									
									 while(serviceRS.next())
										{
										 auth_seq=serviceRS.getString(1);
										}
								
									
									
									if(decision.equalsIgnoreCase("Approval")){
										pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION set USER_STATUS=? where COMMON_ID=(SELECT COMMON_ID FROM USSD_REQUEST where REF_NO=?)");
										pstmt.setString(1, "D");
										pstmt.setString(2, refno);
										pstmt.executeUpdate();
										pstmt.close();
										
										pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,MESSAGE=? where REF_NO=?");
										pstmt.setString(1, "DSA");
										pstmt.setString(2, Narration);
										pstmt.setString(3, refno);
										pstmt.executeUpdate();
										pstmt.close();
										
										pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
										
										pstmt.setString(1, refno);
										pstmt.executeUpdate();
										
										pstmt.close();
										
										pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
										pstmt.setString(1, makerId);
										pstmt.setString(2, auth_seq);
										pstmt.executeUpdate();
										
									}else{
										
										pstmt = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE=?,MESSAGE=? where REF_NO=?");
										pstmt.setString(1, "DSA");
										pstmt.setString(2, Narration);
										pstmt.setString(3, refno);
										pstmt.executeUpdate();
										pstmt.close();
										
										pstmt = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
										
										pstmt.setString(1, refno);
										pstmt.executeUpdate();
										
										pstmt.close();
										
										pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
										pstmt.setString(1, makerId);
										pstmt.setString(2, auth_seq);
										pstmt.executeUpdate();
										
										
									}
									
								}
							
							}
						 }
				}
				 actions="active";	
			}else if(status.equalsIgnoreCase("Lock")){
				pstmt = connection.prepareStatement("UPDATE  USER_INFORMATION set USER_STATUS=? where COMMON_ID=?");
				pstmt.setString(1, "A");
				pstmt.setString(2, refno);
				pstmt.executeUpdate();
				 actions="luck";
			}
			
			
			pstmt = connection.prepareStatement("UPDATE  USER_LOGIN_CREDENTIALS set INCORRECT_PASSWD_CNT=? where COMMON_ID=?");
			pstmt.setInt(1, 0);
			pstmt.setString(2, refno);
			pstmt.executeUpdate();
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "agentreglock");
			json.put("channel", "WEB");
			json.put("message", "Acknowledgment :: Service selected User id "+userid+" Action "+status);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			
			connection.commit();


			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
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
	
	public ResponseDTO AgentRegPinSearch(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;
		String userReport="";

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			String msg="";
			
			String customercode=((requestJSON.getString("customercode")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("customercode")+"'";
			String application=((requestJSON.getString("application")).equalsIgnoreCase("")) ? null : ""+requestJSON.getString("application")+"";

			if(application.equalsIgnoreCase("AGENT")){
				msg="DSA Service Search for User Id ";
				/*userReport =" select UI.COMMON_ID,UI.USER_NAME,ULC.LOGIN_USER_ID FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
					+ " UPPER(UI.CUSTOMER_CODE)=UPPER("+fname+") ";*/
			userReport ="select ACM.ID,ACM.USER_ID,MCI.MOBILE_NUMBER,WAD.ACCT_NO,WAD.BALANCE,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation'),ACM.SUPER_ADM from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD "
			+ "where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' AND "
			+ " UPPER(ACM.CUSTOMER_CODE)=UPPER("+customercode+") ";

			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("REF_NO",serviceRS.getString(1));
				 json.put("USER_ID",serviceRS.getString(2));
				 json.put("MOBILE_NO",serviceRS.getString(3));
				 json.put("ACCT_NO",serviceRS.getString(4));
				 json.put("BALANCE",serviceRS.getString(5));
				 json.put("STATUS",serviceRS.getString(6));
				 json.put("SUPER_ADM",serviceRS.getString(7));
				 IncomeMTFilesJSONArray.add(json);
				}
				
				
			}
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "agentreglock");
			json.put("channel", "WEB");
			json.put("message", msg);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(json);
			
			
			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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
	
	public ResponseDTO AgentLimitUpdateSearch(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String fname=((requestJSON.getString("fname")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("fname")+"'";
			//String mnumber=((requestJSON.getString("mnumber")).equalsIgnoreCase("")) ? null : "'"+requestJSON.getString("mnumber")+"'";
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			String userReport =" select UI.COMMON_ID,UI.USER_NAME,ULC.LOGIN_USER_ID FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
					+ " UPPER(UI.USER_NAME)=UPPER("+fname+") ";
			

			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("REF_NO",serviceRS.getString(1));
				 json.put("USER_ID",serviceRS.getString(2));
				 json.put("MOBILE_NO",serviceRS.getString(3));
				 IncomeMTFilesJSONArray.add(json);
				}
				

			 JSONObject jsonaudit = new JSONObject();
			 jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
			 jsonaudit.put("transCode", "dsalimitupdate");
			 jsonaudit.put("channel", "WEB");
			 jsonaudit.put("message", "Product Update for DSA User Id "+requestJSON.getString("fname"));
			 jsonaudit.put("ip", remoteip);
			 jsonaudit.put("det1", "");
			 jsonaudit.put("det2", "");
			 jsonaudit.put("det3", "");
				
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(jsonaudit);
			
			connection.commit();
				
			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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
	
	public ResponseDTO AgentPinResetAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();


			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			String refno=requestJSON.getString("refno");;
			System.out.println("common id :: "+refno);
			
			String mnumber=requestJSON.getString("telephone");
			String userid=requestJSON.getString("fullname");
			
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			connection.setAutoCommit(false);
			
				
				CommonServiceDao csd=new CommonServiceDao();
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				pstmt = connection.prepareStatement("UPDATE  AGENT_CUSTOMER_MASTER set PIN=? where ID=?");
				pstmt.setString(1, resp.getString("pinHash"));
				pstmt.setString(2, refno);
				
				pstmt.executeUpdate();
				
				
				/*requestJSON.put("mobileno", mnumber);
				requestJSON.put("templateID", "DSA_PIN_SEND");
				requestJSON.put("outmesaage", "CUSTOMER_NAME-"+userid+",NEW_PIN-"+resp.getString("pin"));
				csd.insertSMS(requestJSON);*/
				
				ServiceRequestClient.sms(mnumber, "Dear "+userid+", Please find the your txn Pin "+resp.getString("pin"));
			

				
				JSONObject json = new JSONObject();
				json.put(CevaCommonConstants.MAKER_ID, makerId);
				json.put("transCode", "pinreset");
				json.put("channel", "WEB");
				json.put("message", "Acknowledgment :: Pin Reset for  USER ID "+userid);
				json.put("ip", remoteip);
				json.put("det1", "");
				json.put("det2", "");
				json.put("det3", "");
				
				csd.auditTrailInsert(json);
				connection.commit();
			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
	
	
	
	public ResponseDTO AgentBlockAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		String encpin = null;
		String pin = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();
			
		
				
				

				insQRY = "{call WALLETREGISTRATIONPKG.AGENTSTATUS(?,?,?,?,?)}";

				


				cstmt = connection.prepareCall(insQRY);
				cstmt.setString(1, requestJSON.getString("refno"));
				cstmt.setString(2, requestJSON.getString("telephone"));
				cstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(4, requestJSON.getString("remoteip"));
				
				
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeQuery();

				if (!(cstmt.getString(5).split("-")[1]).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(5));
				}else{
					responseDTO.addMessages((cstmt.getString(5)).split("-")[0]);
				}
				
			
			

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
		
			
			try {

				if (cstmt != null)
					cstmt.close();
				
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
		}
	
	
	public ResponseDTO AgentStatusAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		String encpin = null;
		String pin = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();
			
		
				
				

				insQRY = "{call WALLETREGISTRATIONPKG.AGENTSTATUSACTIVE(?,?,?,?,?)}";

				


				cstmt = connection.prepareCall(insQRY);
				cstmt.setString(1, requestJSON.getString("refno"));
				cstmt.setString(2, requestJSON.getString("telephone"));
				cstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(4, requestJSON.getString("remoteip"));
				
				
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeQuery();

				if (!(cstmt.getString(5).split("-")[1]).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(5));
				}else{
					responseDTO.addMessages((cstmt.getString(5)).split("-")[0]);
				}
				
			
			

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
		
			
			try {

				if (cstmt != null)
					cstmt.close();
				
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
		}
	
	
	public ResponseDTO AgentModifyAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		String encpin = null;
		String pin = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			connection = DBConnector.getConnection();
			
		
				
				

				insQRY = "{call WALLETREGISTRATIONPKG.AGENTMODIFY(?,?,?,?,?)}";

				


				cstmt = connection.prepareCall(insQRY);
				cstmt.setString(1, requestJSON.getString("refno"));
				cstmt.setString(2, requestJSON.getString("telephone"));
				cstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(4, requestJSON.getString("remoteip"));
				
				
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeQuery();

				if (!(cstmt.getString(5).split("-")[1]).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(5));
				}else{
					responseDTO.addMessages((cstmt.getString(5)).split("-")[0]);
				}
				
			
			

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
		
			
			try {

				if (cstmt != null)
					cstmt.close();
				
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
		}
	
	public ResponseDTO DsaLimitmng(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][DsaLimitmng]");

		logger.debug("Inside  DsaLimitmng.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String userReport="";
			
				userReport =" select  LIMIT_CODE,LIMIT_DESC,CREATE_DT FROM DSA_LIMIT WHERE STATUS='A'";
		
			
			

			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("LIMIT_CODE",serviceRS.getString(1));
				 json.put("LIMIT_DESC",serviceRS.getString(2));
				 json.put("CREATE_DT",serviceRS.getString(3));
				 IncomeMTFilesJSONArray.add(json);
				}
				

			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in DsaLimitmng [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in DsaLimitmng [" + e.getMessage()
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
	
	public ResponseDTO LimitCodeSearch(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [AgentDAO][LimitCodeSearch]");

		
		
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
			 String limitcode=requestJSON.getString("limitcode");
			 String actiontype=requestJSON.getString("actiontype");
			 if(actiontype.equalsIgnoreCase("PROCESS")){
				 json.put("limitcode",limitcode); 
			 }

			if(actiontype.equalsIgnoreCase("VIEW")){
					
				servicePstmt = connection.prepareStatement("SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT FROM DSA_LIMIT WHERE LIMIT_CODE='"+limitcode+"'");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",limitcode);
					json.put("limitcode",serviceRS.getString(1));
					json.put("limitdesc",serviceRS.getString(2));
					json.put("damtlmt",serviceRS.getString(3));
					json.put("numoftran",serviceRS.getString(4));
					json.put("ptamt",serviceRS.getString(5));
					}
						
					
				}else if(actiontype.equalsIgnoreCase("MODIFY")){
					
				servicePstmt = connection.prepareStatement("SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT FROM DSA_LIMIT WHERE LIMIT_CODE='"+limitcode+"'");
				serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",limitcode);
					json.put("limitcode",serviceRS.getString(1));
					json.put("limitdesc",serviceRS.getString(2));
					json.put("damtlmt",serviceRS.getString(3));
					json.put("numoftran",serviceRS.getString(4));
					json.put("ptamt",serviceRS.getString(5));
					}
						
					
				}
				
						
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[AgentDAO][LimitCodeSearch] SQLException in LimitCodeSearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("[AgentDAO][LimitCodeSearch] Internal Error Occured While Executing.");
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
			
			
		}

		return responseDTO;
	}
	
public ResponseDTO DsaLimitNewAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String auth_seq="";
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();


			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			String limitcode=requestJSON.getString("limitcode");
			String limitdesc=requestJSON.getString("limitdesc");
			String damtlmt=requestJSON.getString("damtlmt");
			String numoftran=requestJSON.getString("numoftran");
			String ptamt=requestJSON.getString("ptamt");
			String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			String remoteip=requestJSON.getString("remoteip");
			
			connection.setAutoCommit(false);
			
			servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
			serviceRS = servicePstmt.executeQuery();
			
			 while(serviceRS.next())
				{
				 auth_seq=serviceRS.getString(1);
				}
				
			
			
			
				pstmt = connection.prepareStatement("INSERT INTO  DSA_LIMIT_TEMP(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,CREATE_DT,MAKER_ID,MAKER_DT,AUT_REF) values(?,?,?,?,?,'NEW',sysdate,?,sysdate,?)");
				pstmt.setString(1, limitcode);
				pstmt.setString(2, limitdesc);
				pstmt.setString(3, damtlmt);
				pstmt.setString(4, numoftran);
				pstmt.setString(5, ptamt);
				pstmt.setString(6, makerId);
				pstmt.setString(7, auth_seq);
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'NEWALT','P','LA',?)");
				pstmt.setString(1, makerId);
				pstmt.setString(2, auth_seq);
				pstmt.executeUpdate();
				
				JSONObject json = new JSONObject();
				json.put(CevaCommonConstants.MAKER_ID, makerId);
				json.put("transCode", "dsalimitmng");
				json.put("channel", "WEB");
				json.put("message", "New Account Open Limit Code "+limitcode);
				json.put("ip", remoteip);
				json.put("det1", "");
				json.put("det2", "");
				json.put("det3", "");

				connection.commit();
			responseDTO.addMessages("Success");

		} catch (SQLException e) { 
			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (servicePstmt != null)
					servicePstmt.close();
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
public ResponseDTO AgentRegModifySrc(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][AgentRegModifySrc]");

	logger.debug("Inside  AgentRegModifySrc.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String fname=((requestJSON.getString("fname")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("fname")+"'";
		String mnumber=((requestJSON.getString("mnumber")).equalsIgnoreCase("")) ? null : "'"+requestJSON.getString("mnumber")+"'";

		
		String userReport =" select UI.COMMON_ID,UI.USER_NAME,ULC.LOGIN_USER_ID FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
				+ "("+fname+" is NULL or UPPER(UI.USER_NAME)="+fname+") "
				+ "AND ("+mnumber+" is NULL or ULC.LOGIN_USER_ID="+mnumber+") ";
		

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("USER_ID",serviceRS.getString(2));
			 json.put("MOBILE_NO",serviceRS.getString(3));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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
	

public ResponseDTO Dsamanagement(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][AgentRegModifySrc]");

	logger.debug("Inside  AgentRegModifySrc.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
	
		String userReport =" select UI.COMMON_ID,UI.USER_NAME,ULC.LOGIN_USER_ID FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI,USSD_REQUEST UR WHERE ULC.COMMON_ID=UI.COMMON_ID AND UR.COMMON_ID=UI.COMMON_ID ";
			
		

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("USER_ID",serviceRS.getString(2));
			 json.put("MOBILE_NO",serviceRS.getString(3));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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
	

public ResponseDTO LimitAppSearch(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][LimitAppSearch]");

	logger.debug("Inside  LimitAppSearch.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String userReport="";
		String actiontype=requestJSON.getString("actiontype");
		String status=requestJSON.getString("status");
		/*if(type.equalsIgnoreCase("PROCESS")){
			userReport =" select  REF_NO,USER_ID,MOBILE_NO FROM USSD_REQUEST WHERE STATUS='P'";
		}else{*/
			//userReport =" select  LIMIT_CODE,LIMIT_DESC,CREATE_DT,decode(STATUS,'NEW','New Limit','NEWMOD','Modify Limit') FROM DSA_LIMIT_TEMP";
		//}
		if(status.equalsIgnoreCase("PENDING")){
			if(actiontype.equalsIgnoreCase("NEWALT")){
				userReport =" select  LIMIT_CODE,LIMIT_DESC,CREATE_DT,decode(STATUS,'NEW','New Limit','NEWMOD','Modify Limit'),MAKER_ID FROM DSA_LIMIT_TEMP WHERE STATUS='NEW'";
			}else if(actiontype.equalsIgnoreCase("ALMODY")){
				userReport =" select  LIMIT_CODE,LIMIT_DESC,CREATE_DT,decode(STATUS,'NEW','New Limit','NEWMOD','Modify Limit'),MAKER_ID FROM DSA_LIMIT_TEMP WHERE STATUS='NEWMOD'";
			}
			
			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("LIMIT_CODE",serviceRS.getString(1));
				 json.put("LIMIT_DESC",serviceRS.getString(2));
				 json.put("CREATE_DT",serviceRS.getString(3));
				 json.put("STATUS",serviceRS.getString(4));
				 json.put("MAKER_ID",serviceRS.getString(5));
				 IncomeMTFilesJSONArray.add(json);
				}
		}else{
			if(status.equalsIgnoreCase("AUTHORIZED")){
				
				if(actiontype.equalsIgnoreCase("NEWALT")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='NEWALT' AND AP.STATUS='C'";
				}else if(actiontype.equalsIgnoreCase("ALMODY")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='ALMODY' AND AP.STATUS='C'";
				}
				
			}else if(status.equalsIgnoreCase("REJECTED")){
				if(actiontype.equalsIgnoreCase("NEWALT")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='NEWALT' AND AP.STATUS='R'";
				}else if(actiontype.equalsIgnoreCase("ALMODY")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='ALMODY' AND AP.STATUS='R'";
				}
			}else if(status.equalsIgnoreCase("DELETED")){
				if(actiontype.equalsIgnoreCase("NEWALT")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='NEWALT' AND AP.STATUS='D'";
				}else if(actiontype.equalsIgnoreCase("ALMODY")){
					userReport =" select  DLH.LIMIT_CODE,DLH.LIMIT_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,DLH.MESSAGE FROM DSA_LIMIT_HISTORY DLH,AUTH_PENDING AP WHERE DLH.AUT_REF=AP.REF_NUM AND AUTH_FLAG='LA' AND AP.AUTH_CODE='ALMODY' AND AP.STATUS='D'";
				}
			}
			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("USER_ID",serviceRS.getString(1));
				 json.put("MOBILE_NO",serviceRS.getString(2));
				 json.put("MAKER_ID",serviceRS.getString(3));
				 json.put("MAKER_DTTM",serviceRS.getString(4));
				 json.put("CHECKER_ID",serviceRS.getString(5));
				 json.put("CHECKER_DTTM",serviceRS.getString(6));
				 json.put("MESSAGE",serviceRS.getString(7));
				 IncomeMTFilesJSONArray.add(json);
				}
		}
			
		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in LimitAppSearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in LimitAppSearch [" + e.getMessage()
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

public ResponseDTO LimitAppAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][LimitAppAck]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String auth_seq="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();
		connection.setAutoCommit(false);

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		String Narration=requestJSON.getString("Narration");
		String decision=requestJSON.getString("decision");
		String limitcode=requestJSON.getString("limitcode");
		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		servicePstmt = connection.prepareStatement("select AUT_REF from DSA_LIMIT_TEMP where LIMIT_CODE='"+limitcode+"'");
		serviceRS = servicePstmt.executeQuery();
		
		 while(serviceRS.next())
			{
			 auth_seq=serviceRS.getString(1);
			}
		
		if(decision.equalsIgnoreCase("Approval")){
			
			 
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,AUT_REF) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',AUT_REF FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT_HISTORY(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,AUT_REF,MESSAGE) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',AUT_REF,'"+Narration+"' FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			
			pstmt1 = connection.prepareStatement("DELETE FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
		}else{
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT_HISTORY(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,AUT_REF,MESSAGE) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',AUT_REF,'"+Narration+"' FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			
			pstmt1 = connection.prepareStatement("DELETE FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
			
		}
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, makerId);
		json.put("transCode", "ApprovalAll");
		json.put("channel", "WEB");
		json.put("message", "New Limit Decision  "+decision+" for Limit Code "+limitcode);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);
		
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (servicePstmt != null)
				servicePstmt.close();
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
public ResponseDTO LimitAppModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][LimitAppAck]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String auth_seq="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();
		connection.setAutoCommit(false);

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		String Narration=requestJSON.getString("Narration");
		String decision=requestJSON.getString("decision");
		String limitcode=requestJSON.getString("limitcode");
		String updatedamtlmt=requestJSON.getString("updatedamtlmt");
		String updatenumoftran=requestJSON.getString("updatenumoftran");
		String updateptamt=requestJSON.getString("updateptamt");
		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		servicePstmt = connection.prepareStatement("select AUT_REF from DSA_LIMIT_TEMP where LIMIT_CODE='"+limitcode+"'");
		serviceRS = servicePstmt.executeQuery();
		
		 while(serviceRS.next())
			{
			 auth_seq=serviceRS.getString(1);
			}
		
		if(decision.equalsIgnoreCase("Approval")){
			
			pstmt1 = connection.prepareStatement("UPDATE USSD_REQUEST set DAILY_AMT='"+updatedamtlmt+"',DAILY_TXN='"+updatenumoftran+"',PER_AMT='"+updateptamt+"' WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			pstmt1.close();
			
			pstmt1 = connection.prepareStatement("DELETE FROM DSA_LIMIT WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			 
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,CREATE_DT,AUT_REF) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',CREATE_DT,AUT_REF FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT_HISTORY(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,AUT_REF,MESSAGE) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',AUT_REF,'"+Narration+"' FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt1 = connection.prepareStatement("DELETE FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
		}else{
			pstmt = connection.prepareStatement("INSERT INTO DSA_LIMIT_HISTORY(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,AUT_REF,MESSAGE) SELECT LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,'A',AUT_REF,'"+Narration+"' FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt.executeUpdate();
			
			
			pstmt1 = connection.prepareStatement("DELETE FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE='"+limitcode+"'");
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			
		}
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, makerId);
		json.put("transCode", "ApprovalAll");
		json.put("channel", "WEB");
		json.put("message", "New Limit Decision  "+decision+" for Limit Code "+limitcode);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);
		
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (servicePstmt != null)
				servicePstmt.close();
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

public ResponseDTO LimitModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String auth_seq="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String limitcode=requestJSON.getString("limitcode");
		String limitdesc=requestJSON.getString("limitdesc");
		String updatedamtlmt=requestJSON.getString("updatedamtlmt");
		String updatenumoftran=requestJSON.getString("updatenumoftran");
		String updateptamt=requestJSON.getString("updateptamt");
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		connection.setAutoCommit(false);
		
		servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
		serviceRS = servicePstmt.executeQuery();
		
		 while(serviceRS.next())
			{
			 auth_seq=serviceRS.getString(1);
			}
			
		
			pstmt = connection.prepareStatement("INSERT INTO  DSA_LIMIT_TEMP(LIMIT_CODE,LIMIT_DESC,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,CREATE_DT,MAKER_ID,MAKER_DT,AUT_REF) values(?,?,?,?,?,'NEWMOD',sysdate,?,sysdate,?)");
			pstmt.setString(1, limitcode);
			pstmt.setString(2, limitdesc);
			pstmt.setString(3, updatedamtlmt);
			pstmt.setString(4, updatenumoftran);
			pstmt.setString(5, updateptamt);
			pstmt.setString(6, makerId);
			pstmt.setString(7, auth_seq);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'ALMODY','P','LA',?)");
			pstmt.setString(1, makerId);
			pstmt.setString(2, auth_seq);
			pstmt.executeUpdate();
			
			
			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "dsalimitmng");
			json.put("channel", "WEB");
			json.put("message", "Modify Account Open Limit Code "+updatedamtlmt);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");

			connection.commit();
		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (servicePstmt != null)
				servicePstmt.close();
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

public ResponseDTO BulkDsaReg(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][BulkDsaReg]");

	
	
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
		

		
				 String limitCodeQry = "select STATUS||'-'||KEY_VALUE,KEY_VALUE from CONFIG_DATA WHERE key_group='FILEUPLOAD' AND KEY_TYPE in ('FILETYPE')";
				 servicePstmt = connection.prepareStatement(limitCodeQry);
				 serviceRS = servicePstmt.executeQuery();
					while (serviceRS.next()) {
						jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
					}
					json.put("LIMIT_CODE", jsonlmt);
				
			
		 
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][BulkDsaReg] SQLException in BulkDsaReg [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][BulkDsaReg] Internal Error Occured While Executing.");
	} finally {
		
		viewDataMap = null;
		
		
	}

	return responseDTO;
}	



public ResponseDTO ResultSearch(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][BulkDsaReg]");

	
	
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
		

		
				 String limitCodeQry = "select STATUS||'-'||KEY_VALUE,KEY_VALUE from CONFIG_DATA WHERE key_group='FILEUPLOAD' AND KEY_TYPE in ('FILETYPE','REPORTS','NFILETYPE')";
				 servicePstmt = connection.prepareStatement(limitCodeQry);
				 serviceRS = servicePstmt.executeQuery();
					while (serviceRS.next()) {
						jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
					}
					json.put("LIMIT_CODE", jsonlmt);
				
			
		 
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][BulkDsaReg] SQLException in BulkDsaReg [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][BulkDsaReg] Internal Error Occured While Executing.");
	} finally {
		
		viewDataMap = null;
		
		
	}

	return responseDTO;
}	


public ResponseDTO AgentRegModifyDetailsAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String auth_seq="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();
		connection.setAutoCommit(false);

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		
		String displayname=requestJSON.getString("displayname");
		String userid=requestJSON.getString("userid");
		String empno=requestJSON.getString("empno");
		String fname=requestJSON.getString("fname");
		String lname=requestJSON.getString("lname");
		String jtitle=requestJSON.getString("jtitle");
		String mnumber=requestJSON.getString("mnumber");
		String mailid=requestJSON.getString("mailid");
		String branchcode=requestJSON.getString("branchcode");
		String damtlmt=requestJSON.getString("damtlmt");
		String numoftran=requestJSON.getString("numoftran");
		String ptamt=requestJSON.getString("ptamt");
		String limitcode=requestJSON.getString("limitcode");
		
		servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF') from dual");
		serviceRS = servicePstmt.executeQuery();
			
		while(serviceRS.next())
		{
				auth_seq=serviceRS.getString(1);
		}
		
		
		pstmt2 = connection.prepareStatement("INSERT INTO  USSD_REQUEST_TEMP(USERID,DISPLAYNAME,EMPNO,FNAME,LNAME,JOB_TITLE,MOBILE_NO,MAILID,BRANCH_DETAILS,DAILY_AMT,DAILY_TXN,PER_AMT,STATUS,REQUEST_TYPE,MAKER_ID,LIMIT_CODE,AUT_REF) values(?,?,?,?,?,?,?,?,?,?,?,?,'A','DSA',?,?,?)");
		
		
		pstmt2.setString(1, userid);
		pstmt2.setString(2, displayname);
		pstmt2.setString(3, empno);
		pstmt2.setString(4, fname);
		pstmt2.setString(5, lname);
		pstmt2.setString(6, jtitle);
		pstmt2.setString(7, mnumber);
		pstmt2.setString(8, mailid);
		pstmt2.setString(9, branchcode);
		pstmt2.setString(10, damtlmt);
		pstmt2.setString(11, numoftran);
		pstmt2.setString(12, ptamt);
		pstmt2.setString(13, makerId);
		pstmt2.setString(14, limitcode);
		pstmt2.setString(15, auth_seq);
		pstmt2.executeUpdate();
		pstmt2.close();
		
		pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set REQUEST_TYPE='REGMODIFY',AUT_REF=? WHERE UPPER(USERID)=UPPER(?)");
		pstmt2.setString(1, auth_seq);
		pstmt2.setString(2, userid);
		pstmt2.executeUpdate();
		pstmt2.close();
		
		
		pstmt2 = connection.prepareStatement("INSERT INTO  AUTH_PENDING(MAKER_ID,MAKER_DTTM,AUTH_CODE,STATUS,AUTH_FLAG,REF_NUM) values(?,sysdate,'DSAREGMOD','P','DA',?)");
		pstmt2.setString(1, makerId);
		pstmt2.setString(2, auth_seq);
		pstmt2.executeUpdate();
		
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, makerId);
		json.put("transCode", "dsamodify");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: DSA Registration Modified Mobile Number "+mnumber+", Branch "+branchcode+" for User "+userid);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);
		
		
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (servicePstmt != null)
				servicePstmt.close();
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



public ResponseDTO AgentRegApprovalModifyAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][AgentRegApprovalAck]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String auth_seq="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();
		connection.setAutoCommit(false);

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		String Narration=requestJSON.getString("Narration");
		String decision=requestJSON.getString("decision");
		String mnumber=requestJSON.getString("mnumber");
		String userid=requestJSON.getString("userid");
		String refno=requestJSON.getString("refno");
		String displayname=requestJSON.getString("displayname");
		String mailid=requestJSON.getString("mailid");
		String branchcode=requestJSON.getString("branchcode");
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		String updatemobileno=requestJSON.getString("updatemobileno");
		String updatebranchdetails=requestJSON.getString("updatebranchdetails");
	
		 servicePstmt = connection.prepareStatement("select AUT_REF from USSD_REQUEST where REF_NO="+refno+"");
			serviceRS = servicePstmt.executeQuery();
			
			 while(serviceRS.next())
				{
				 auth_seq=serviceRS.getString(1);
				}
		
			 servicePstmt.close();
			 serviceRS.close();
		
		if(decision.equalsIgnoreCase("Approval")){
			

			if(mnumber.equalsIgnoreCase(updatemobileno)){
				
				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set BRANCH_DETAILS='"+updatebranchdetails+"',MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				pstmt2.close();
				
				pstmt = connection.prepareStatement("UPDATE USER_INFORMATION set LOCATION='"+updatebranchdetails+"' WHERE COMMON_ID=(SELECT COMMON_ID FROM USSD_REQUEST WHERE REF_NO=? ) ");
				pstmt.setString(1, refno);
				pstmt.executeUpdate();
				pstmt.close();
				
			
			}else{
				
				CommonServiceDao csd=new CommonServiceDao();
				
				JSONObject resp =  csd.genaratePassword().getJSONObject("data");
				
				pstmt = connection.prepareStatement("UPDATE USER_LOGIN_CREDENTIALS set LOGIN_USER_ID='"+updatemobileno.replaceAll("\\+", "")+"',PASSWORD='"+resp.getString("pinHash")+"' WHERE COMMON_ID=(SELECT COMMON_ID FROM USSD_REQUEST WHERE REF_NO=? ) ");
				pstmt.setString(1, refno);
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE USER_INFORMATION set LOCATION='"+updatebranchdetails+"' WHERE COMMON_ID=(SELECT COMMON_ID FROM USSD_REQUEST WHERE REF_NO=? ) ");
				pstmt.setString(1, refno);
				pstmt.executeUpdate();
				pstmt.close();
				
				requestJSON.put("mobileno", mnumber);
				requestJSON.put("templateID", "DSA_PIN_SEND");
				requestJSON.put("outmesaage", "CUSTOMER_NAME-"+displayname+",NEW_PIN-"+resp.getString("pin"));
				
				csd.insertSMS(requestJSON);
				
				ServiceRequestClient.sms(mnumber, "Dear "+displayname+", Please find the USSD  PIN "+resp.getString("pin"));
				
				
				pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST set MOBILE_NO='"+updatemobileno+"',BRANCH_DETAILS='"+updatebranchdetails+"',MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where REF_NO=?");
				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				
				
				
			}
			
			 	pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='C',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
				pstmt2.setString(1, makerId);
				pstmt2.setString(2, auth_seq);
				pstmt2.executeUpdate();
				pstmt2.close();
				
				
				
				pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST where REF_NO=?");
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
				
				pstmt2 = connection.prepareStatement("DELETE FROM  USSD_REQUEST_TEMP where USERID=(SELECT USERID FROM USSD_REQUEST WHERE REF_NO=? )");				
				pstmt2.setString(1, refno);
				pstmt2.executeUpdate();
			
			
		}else{
			
			pstmt2 = connection.prepareStatement("UPDATE  USSD_REQUEST_TEMP set MESSAGE='"+Narration+"',CHECKER_ID='"+makerId+"',CHECKER_DT=sysdate,REQUEST_TYPE='DSA' where USERID=(SELECT USERID FROM USSD_REQUEST WHERE REF_NO=? )");
			
			pstmt2.setString(1, refno);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			pstmt2 = connection.prepareStatement("UPDATE AUTH_PENDING SET CHECKER_ID=?,STATUS='R',CHECKER_DTTM=sysdate WHERE REF_NUM=?");
			pstmt2.setString(1, makerId);
			pstmt2.setString(2, auth_seq);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			pstmt2 = connection.prepareStatement("INSERT INTO USSD_REQUEST_HISTORY SELECT * FROM USSD_REQUEST_TEMP where USERID=(SELECT USERID FROM USSD_REQUEST WHERE REF_NO=? )");
			pstmt2.setString(1, refno);
			pstmt2.executeUpdate();
			
			pstmt2 = connection.prepareStatement("DELETE FROM  USSD_REQUEST_TEMP where USERID=(SELECT USERID FROM USSD_REQUEST WHERE REF_NO=? )");				
			pstmt2.setString(1, refno);
			pstmt2.executeUpdate();
			
			
		}
		
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, makerId);
		json.put("transCode", "ApprovalAll");
		json.put("channel", "WEB");
		json.put("message", "DSA registered Modify Decision "+decision+" User Id "+userid);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);
		
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (servicePstmt != null)
				servicePstmt.close();
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

public ResponseDTO ApprovalAll(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ApprovalAll]");

	logger.debug("Inside  ApprovalAll.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;
	String queries="";


	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		queries ="select distinct AR.RELATION,AR.RELATION_CODE,AR.RELATION_ACTION,"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING WHERE AUTH_FLAG=AR.RELATION_CODE AND STATUS='P' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING WHERE AUTH_FLAG=AR.RELATION_CODE AND STATUS='C' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING WHERE AUTH_FLAG=AR.RELATION_CODE AND STATUS='R' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING WHERE AUTH_FLAG=AR.RELATION_CODE AND STATUS='D' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"') "
				+ "from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC,USER_LINKED_ACTION ULA,USER_ACTION_LINKS UAL,AUTH_MASTER AM,AUTH_RELATION AR "
		+"where UI.COMMON_ID=ULC.COMMON_ID AND "
		+"ULA.GROUP_ID=UI.USER_GROUPS  AND  "
		+"ULA.ID=UAL.ID  AND "
		+"UAL.ACTION is null AND "
		+"AM.AUTH_CODE=UAL.ALIAS_NAME AND "
		+"AR.RELATION_CODE=AM.RELATION AND "
		+"ULC.LOGIN_USER_ID='"+maker_id+"' ";
		
		/*queries =" select  REF_NO,USERID,MOBILE_NO,decode(REQUEST_TYPE,'LIMITUPDATE','Limit Update','NEWREG','New Registration','REGMODIFY','Modify','ACTIVE','Active') FROM USSD_REQUEST WHERE REQUEST_TYPE in ('LIMITUPDATE','NEWREG','REGMODIFY','ACTIVE')";
	*/
		servicePstmt = connection.prepareStatement(queries);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("RELATION",serviceRS.getString(1));
			 json.put("RELATION_CODE",serviceRS.getString(2));
			 json.put("RELATION_ACTION",serviceRS.getString(3));
			 json.put("PENDING",serviceRS.getString(4));
			 json.put("AUTHORIZED",serviceRS.getString(5));
			 json.put("REJECTED",serviceRS.getString(6));
			 json.put("DELETED",serviceRS.getString(7));
			 json.put("POSITION","MAIN");
			 json.put("STATUS","ALL");
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ApprovalAll [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ApprovalAll [" + e.getMessage()
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

public ResponseDTO ApprovalDetails(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ApprovalDetails]");

	logger.debug("Inside  ApprovalDetails.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;
	String queries="";


	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String actiontype=requestJSON.getString("actiontype");
		String status=requestJSON.getString("status");
		
		queries ="select  AM.HEADING_NAMES,AM.AUTH_CODE,AM.ACTION_URL,"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING AP WHERE AP.AUTH_CODE=AM.AUTH_CODE AND AP.STATUS='P' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING AP WHERE AP.AUTH_CODE=AM.AUTH_CODE AND AP.STATUS='C' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING AP WHERE AP.AUTH_CODE=AM.AUTH_CODE AND AP.STATUS='R' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"'),"
				+ "(SELECT COUNT(*) FROM AUTH_PENDING AP WHERE AP.AUTH_CODE=AM.AUTH_CODE AND AP.STATUS='D' AND MAKER_BRCODE=UI.CLUSTER_ID AND MAKER_ID<>'"+maker_id+"') "
				+ "from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC,USER_LINKED_ACTION ULA,USER_ACTION_LINKS UAL,AUTH_MASTER AM,AUTH_RELATION AR "
		+"where UI.COMMON_ID=ULC.COMMON_ID AND "
		+"ULA.GROUP_ID=UI.USER_GROUPS  AND  "
		+"ULA.ID=UAL.ID  AND "
		+"UAL.ACTION is null AND "
		+"AM.AUTH_CODE=UAL.ALIAS_NAME AND "
		+"AR.RELATION_CODE=AM.RELATION AND "
		+"ULC.LOGIN_USER_ID='"+maker_id+"' AND AR.RELATION_CODE='"+actiontype+"'";
		
		servicePstmt = connection.prepareStatement(queries);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("RELATION",serviceRS.getString(1));
			 json.put("RELATION_CODE",serviceRS.getString(2));
			 json.put("RELATION_ACTION",serviceRS.getString(3));
			 json.put("PENDING",serviceRS.getString(4));
			 json.put("AUTHORIZED",serviceRS.getString(5));
			 json.put("REJECTED",serviceRS.getString(6));
			 json.put("DELETED",serviceRS.getString(7));
			 json.put("POSITION","SUB");
			 json.put("STATUS",status);
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ApprovalDetails [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ApprovalDetails [" + e.getMessage()
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
public ResponseDTO ChannelManagement(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ChannelManagement]");

	logger.debug("Inside  ChannelManagement.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		

		
		String userReport =" select REF_NO,CHANNEL_NAME,SERVICE_NAME,ASSIGN_LIMIT,ASSIGN_FEE,PRODUCT,APPLICATION,ASSIGN_LOYALTY,trunc(MAKER_DT) as makerdt FROM MOB_CHANNEL_MAP ORDER BY makerdt desc";
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("CHANNEL_NAME",serviceRS.getString(2));
			 json.put("SERVICE_NAME",serviceRS.getString(3));
			 json.put("ASSIGN_LIMIT",serviceRS.getString(4));
			 json.put("ASSIGN_FEE",serviceRS.getString(5));
			 json.put("PRODUCT",serviceRS.getString(6));
			 json.put("APPLICATION",serviceRS.getString(7));
			 json.put("ASSIGN_LOYALTY",serviceRS.getString(8));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
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

public ResponseDTO FraudDetails(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ChannelManagement]");

	logger.debug("Inside  ChannelManagement.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		
		String userReport = "SELECT  * from (select USERID,TXN_AMOUNT,TRANS_DATE,MOBNUM,REQUEST_CHANNEL,ERROR_DESC,nvl(ID,' ') from FRAUD_TBL "
				+ "where MON_STATUS='Y' order by DATE_CREATED desc ) where rownum < 100 ";
		
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("USERID",serviceRS.getString(1));
			 json.put("TXN_AMOUNT",serviceRS.getString(2));
			 json.put("TRANS_DATE",serviceRS.getString(3));
			 json.put("MOBNUM",serviceRS.getString(4));
			 json.put("REQUEST_CHANNEL",serviceRS.getString(5));
			 json.put("ERROR_DESC",serviceRS.getString(6));
			 json.put("ID",serviceRS.getString(7));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
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


public ResponseDTO ChannelMapping(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][ChannelMapping]");

	
	
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
		

		 
				
			 String channelQry = "select CHANNEL_ID||'-'||CHANNEL_NAME,CHANNEL_NAME from CHANNEL_MASTER WHERE DISPLAY='Y' ORDER BY CHANNEL_ID";
			 servicePstmt = connection.prepareStatement(channelQry);
			 serviceRS = servicePstmt.executeQuery();
				while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
				}
			json.put("CHANNEL_MASTER", jsonlmt);
			jsonlmt.clear();
			servicePstmt.close();
			serviceRS.close();
			
			String serviceQry = "select SERVICECODE||'-'||SERVICEDESC,SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  ORDER BY SERVICEDESC";
			servicePstmt = connection.prepareStatement(serviceQry);
			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
			}
			json.put("SERVICE_MASTER", jsonlmt);
			jsonlmt.clear();
				
			String productQry = "select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT WHERE STATUS='A' ORDER BY PRD_CODE";
			servicePstmt = connection.prepareStatement(productQry);
			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
			}
			json.put("PRODUCT_TEMP", jsonlmt);
			jsonlmt.clear();
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][ChannelMapping] SQLException in ChannelMapping [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][ChannelMapping] Internal Error Occured While Executing.");
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

public ResponseDTO ChannelMappingAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	String channel=null;
	String services=null;
	String limit=null;
	String fee=null;
	String loyality=null;
	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String productcode=requestJSON.getString("productcode");
		String application=requestJSON.getString("application");
		String remoteip=requestJSON.getString("remoteip");
		
		String branchQry="insert into MOB_CHANNEL_MAP(CHANNEL_NAME,SERVICE_CODE,SERVICE_NAME,ASSIGN_LIMIT,ASSIGN_FEE,MAKER_ID,MAKER_DT,PRODUCT,APPLICATION,ASSIGN_LOYALTY) " +
				" values(?,?,?,?,?,?,sysdate,?,?,?) ";

		servicePstmt=connection.prepareStatement(branchQry);
	


		JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			channel = reqData.getString("Channel");
			services = reqData.getString("Services");
			limit = reqData.getString("Limit");
			fee = reqData.getString("Fee");


			servicePstmt.setString(1,channel);
			servicePstmt.setString(2,services.split("-")[0]);
			servicePstmt.setString(3, services.split("-")[1]);
			servicePstmt.setString(4,limit);
			servicePstmt.setString(5,fee);
			servicePstmt.setString(6,maker_id);
			servicePstmt.setString(7,productcode);
			servicePstmt.setString(8,application);
			servicePstmt.setString(9,"NO");
			
			
			servicePstmt.addBatch();
			

		}

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "channelmanagement");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: Service Mapping for product id "+productcode);
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



public ResponseDTO FinAccountMapping(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ChannelManagement]");

	logger.debug("Inside  ChannelManagement.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		

		
		String userReport =" select REF_NO,CHANNEL_NAME,PRO_CODE,FEE_CODE||'-'||FEE_DESC,APPLICATION,ACCOUNT_NO FROM FIN_ACCOUNT_TBL";
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("CHANNEL_NAME",serviceRS.getString(2));
			 json.put("PRO_CODE",serviceRS.getString(3));
			 json.put("FEE_CODE",serviceRS.getString(4));
			 json.put("APPLICATION",serviceRS.getString(5));
			 json.put("ACCOUNT_NO",serviceRS.getString(6));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
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

public ResponseDTO FinAccountMappingAdd(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][ChannelMapping]");

	
	
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
		

		 
				
				 String channelQry = "select CHANNEL_ID||'-'||CHANNEL_NAME,CHANNEL_NAME from CHANNEL_MASTER WHERE DISPLAY='Y' ORDER BY CHANNEL_ID";
				 servicePstmt = connection.prepareStatement(channelQry);
				 serviceRS = servicePstmt.executeQuery();
					while (serviceRS.next()) {
						jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
					}
				json.put("CHANNEL_MASTER", jsonlmt);
				jsonlmt.clear();
				servicePstmt.close();
				serviceRS.close();
				
				
					
				String productQry = "select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT ORDER BY PRD_CODE";
				servicePstmt = connection.prepareStatement(productQry);
				serviceRS = servicePstmt.executeQuery();
				while (serviceRS.next()) {
						jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
				}
				json.put("PRODUCT_TEMP", jsonlmt);
				jsonlmt.clear();
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][ChannelMapping] SQLException in ChannelMapping [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][ChannelMapping] Internal Error Occured While Executing.");
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


public ResponseDTO FinAccountMappingAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	String channel=null;
	String feeTransaction=null;
	String account=null;
	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String productcode=requestJSON.getString("productcode");
		String application=requestJSON.getString("application");
		
		String branchQry="insert into FIN_ACCOUNT_TBL(CHANNEL_NAME,FEE_CODE,FEE_DESC,ACCOUNT_NO,MAKER_ID,MAKER_DT,PRO_CODE,APPLICATION) " +
				" values(?,?,?,?,?,sysdate,?,?) ";

		servicePstmt=connection.prepareStatement(branchQry);
	


		JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			channel = reqData.getString("Channel");
			feeTransaction = reqData.getString("FeeTransaction");
			account = reqData.getString("account");


			servicePstmt.setString(1,channel);
			servicePstmt.setString(2,feeTransaction.split("-")[0]);
			servicePstmt.setString(3, feeTransaction.split("-")[1]);
			servicePstmt.setString(4,account);
			servicePstmt.setString(5,maker_id);
			servicePstmt.setString(6,productcode);
			servicePstmt.setString(7,application);
			
			
			servicePstmt.addBatch();
			

		}

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
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



public ResponseDTO AgentProductUpdateSearch(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][AgentRegModifySearch]");

	logger.debug("Inside  AgentRegModifySearch.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String fname=((requestJSON.getString("fname")).equalsIgnoreCase("")) ? null :  "'"+requestJSON.getString("fname")+"'";
		//String mnumber=((requestJSON.getString("mnumber")).equalsIgnoreCase("")) ? null : "'"+requestJSON.getString("mnumber")+"'";
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		String userReport =" select REF_NUM,MERCHANT_ID,MERCHANT_NAME,ACCOUNT_NUMBER,SUPER_AGENT FROM MERCHANT_MASTER WHERE "
				+ " UPPER(MERCHANT_ID)=upper("+fname+") ";
		

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("REF_NO",serviceRS.getString(1));
			 json.put("MERCHANT_ID",serviceRS.getString(2));
			 json.put("MERCHANT_NAME",serviceRS.getString(3));
			 json.put("ACCOUNT_NUMBER",serviceRS.getString(4));
			 json.put("SUPER_AGENT",serviceRS.getString(5));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		 JSONObject jsonaudit = new JSONObject();
		 jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
		 jsonaudit.put("transCode", "dsalimitupdate");
		 jsonaudit.put("channel", "WEB");
		 jsonaudit.put("message", "Product Update for AGENT User Id "+requestJSON.getString("fname"));
		 jsonaudit.put("ip", remoteip);
		 jsonaudit.put("det1", "");
		 jsonaudit.put("det2", "");
		 jsonaudit.put("det3", "");
			
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(jsonaudit);
		 
		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
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

public ResponseDTO AgentProductDetails(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][AgentRegModify]");

	
	
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
		 String userid=requestJSON.getString("userid");
		 String actiontype=requestJSON.getString("actiontype");
		 
		  String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		  String remoteip=requestJSON.getString("remoteip");
		  String Actionname="dsalimitupdate";
		 
		 
		servicePstmt = connection.prepareStatement("SELECT MERCHANT_ID,MERCHANT_NAME,ACCOUNT_NUMBER,EMAIL,TELEPHONE_NO,PRODUCT,SUPER_AGENT FROM MERCHANT_MASTER WHERE REF_NUM='"+userid+"'");
				
		
		serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					
					json.put("MERCHANT_ID",serviceRS.getString(1));
					json.put("MERCHANT_NAME",serviceRS.getString(2));
					json.put("ACCOUNT_NUMBER",serviceRS.getString(3));
					json.put("EMAIL",serviceRS.getString(4));
					json.put("TELEPHONE_NO",serviceRS.getString(5));
					json.put("PRODUCT",serviceRS.getString(6));
					json.put("SUPER_AGENT",serviceRS.getString(7));
					}
				
				
				 String limitCodeQry = "select PRD_CODE||'-'||PRD_NAME||'-'||LIMIT_CODE||'-'||FEE_CODE,PRD_CODE from PRD_DETAILS WHERE PRD_CODE is not null AND LIMIT_CODE is not null AND FEE_CODE is not null AND PRD_CODE in (SELECT PRD_CODE FROM PRODUCT WHERE APPLICATION='AGENT' AND PRD_CODE!='"+json.getString("PRODUCT")+"')";
				 servicePstmt = connection.prepareStatement(limitCodeQry);
				 serviceRS = servicePstmt.executeQuery();
					while (serviceRS.next()) {
						jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
					}
					json.put("LIMIT_CODE", jsonlmt);
					jsonlmt.clear();
					
					servicePstmt.close();
					serviceRS.close();		
		
					 String Actiondesc="Product Update for AGENT User Id "+json.getString("MERCHANT_ID");
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
					
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][AgentRegModify] SQLException in AgentRegModify [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][AgentRegModify] Internal Error Occured While Executing.");
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


public ResponseDTO ApplicationCode(RequestDTO requestDTO) {

	logger.debug("Inside ApplicationCode.. ");
	HashMap<String, Object> binMap = null;
	JSONObject resultJson = null;
	JSONObject json = null;

	

	Connection connection = null;
	PreparedStatement binPstmt = null;
	ResultSet binRs = null;



	try {
		json = new JSONObject();

		responseDTO = new ResponseDTO();

		binMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		

		connection = DBConnector.getConnection();
		logger.debug("connection is null [" + connection == null + "]");

		
		String applicationCodeQry = "select KEY_VALUE,KEY_VALUE from CONFIG_DATA WHERE key_group='APPLICATION'";

		binPstmt = connection.prepareStatement(applicationCodeQry);

		binRs = binPstmt.executeQuery();
		while (binRs.next()) {
			json.put(binRs.getString(1), binRs.getString(2));
		}
		resultJson.put("APPLICATION_CODE", json);
		json.clear();

		
		binMap.put(CevaCommonConstants.BIN_INFO, resultJson);

		logger.debug("ProductManagement [" + binMap + "]");

		responseDTO.setData(binMap);

	} catch (Exception e) {
		e.printStackTrace();
		logger.debug("Got Exception in ProductManDao [" + e.getMessage()
				+ "]");
	} finally {
		try {

			if (binRs != null) {
				binRs.close();
			}
			if (binPstmt != null) {
				binPstmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			 
		}
		binMap = null;
		resultJson = null;
	}

	return responseDTO;
}


public ResponseDTO AgentRegDevice(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String refno=requestJSON.getString("refno");;
		System.out.println("common id :: "+refno);
		
		String mnumber=requestJSON.getString("mnumber");
		String userid=requestJSON.getString("userid");
		/*String mailid=requestJSON.getString("mailid");
		String name=requestJSON.getString("fname")+" "+requestJSON.getString("lname");
		String branchcode=requestJSON.getString("branchcode");*/
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		connection.setAutoCommit(false);
		
		
	
			pstmt = connection.prepareStatement("UPDATE  MOB_IMEI_DATA set STATUS=? where COMMON_ID=?");
			pstmt.setString(1, "B");
			pstmt.setString(2, refno);
			pstmt.executeUpdate();
			
			CommonServiceDao csd=new CommonServiceDao();
			JSONObject respref =  csd.genaratedevicerefno().getJSONObject("data");
			
			requestJSON.put("commonid", refno);
			requestJSON.put("referenceno", respref.getString("pin"));
			
			csd.insertIMEI(requestJSON);
			
			ServiceRequestClient.sms(mnumber, "Dear "+userid+", please find the DSA Mobile App Activation Number "+respref.getString("pin"));
			connection.commit();
		//	ServiceRequestClient.sms(mnumber, "Dear "+name+", Please find the USSD  PIN "+resp.getString("pin"));
		

			
			JSONObject json = new JSONObject();
			json.put(CevaCommonConstants.MAKER_ID, makerId);
			json.put("transCode", "devicereplacement");
			json.put("channel", "WEB");
			json.put("message", "Acknowledgment :: Device Replacement User id "+userid);
			json.put("ip", remoteip);
			json.put("det1", "");
			json.put("det2", "");
			json.put("det3", "");
			
			csd.auditTrailInsert(json);

		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (servicePstmt != null)
				servicePstmt.close();
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


public ResponseDTO dsaReconcilesearch(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		connection.setAutoCommit(false);
		
		
		 String entityQry2 =  "SELECT count(*) from TELLER_INFO WHERE USER_ID=?";

		 servicePstmt = connection.prepareStatement(entityQry2);
		 servicePstmt.setString(1,makerId);
		 serviceRS = servicePstmt.executeQuery();
			     
			
			    while (serviceRS.next()) {
			    	if(serviceRS.getInt(1)==0){
			    		responseDTO.addMessages("fail");
			    	}else{
			     
			    	responseDTO.addMessages("Success");
			    	}
			    }
			    
			
		

			
		

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
			if (servicePstmt != null)
				servicePstmt.close();
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


public ResponseDTO dsaReconcileDetails(RequestDTO requestDTO) {

	logger.debug("Inside fetchlimitcodeInfo.. ");
	HashMap<String, Object> limitcodeDataMap = null;

	JSONObject resultJson = null;
	JSONObject json = null;
	JSONArray JsonArray= new JSONArray();
	PreparedStatement getlimitcodePstmt = null;
	ResultSet getlimitcodeRs = null;
	Connection connection = null;

	String  userid = "";
	String  dsaQry = "select  USERID,DISPLAYNAME,BRANCH_DETAILS,MOBILE_NO from USSD_REQUEST where USERID=?";

	try {
		requestJSON = requestDTO.getRequestJSON();
		responseDTO = new ResponseDTO();
		limitcodeDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		connection = DBConnector.getConnection();

		userid = requestJSON.getString("userid");

		getlimitcodePstmt = connection.prepareStatement(dsaQry);
		getlimitcodePstmt.setString(1,userid);

		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		json = new JSONObject();

		if (getlimitcodeRs.next()) {				
			json.put("USERID", getlimitcodeRs.getString(1));
		     json.put("DISPLAYNAME", getlimitcodeRs.getString(2));
		     json.put("BRANCH_DETAILS", getlimitcodeRs.getString(3));
		     json.put("MOBILE_NO", getlimitcodeRs.getString(4));
			
		}
		
		
		 getlimitcodePstmt.close();
			getlimitcodeRs.close();
		    
		    String entityQry2 =  "SELECT NVL(SUM(CREDITAMOUNT),'0') from FUND_TRANSFER_TBL "
					+" where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND CRDR_FLAG='C'";
 
			getlimitcodePstmt = connection.prepareStatement(entityQry2);
			getlimitcodePstmt.setString(1,userid);
			getlimitcodeRs = getlimitcodePstmt.executeQuery();
			     
			
			    if (getlimitcodeRs.next()) {
			     
			     json.put("CREDIT", getlimitcodeRs.getString(1));
			     
			    }
			    
			    getlimitcodePstmt.close();
				getlimitcodeRs.close();
			    
			    String entityQry3 =  "SELECT NVL(SUM(CREDITAMOUNT),'0') from FUND_TRANSFER_TBL "
						+" where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND CRDR_FLAG='D'";
	    
				getlimitcodePstmt = connection.prepareStatement(entityQry3);
				getlimitcodePstmt.setString(1,userid);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     
				
				 if (getlimitcodeRs.next()) {
				     
				 json.put("DEBIT", getlimitcodeRs.getString(1));
				     
				 }
				 
				 getlimitcodePstmt.close();
					getlimitcodeRs.close();
					
					String entityQry4 =  "SELECT  (SELECT NVL(SUM(CREDITAMOUNT),'0') from FUND_TRANSFER_TBL  where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND CRDR_FLAG='D')-(SELECT NVL(SUM(CREDITAMOUNT),'0') from FUND_TRANSFER_TBL  where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND CRDR_FLAG='C')"
							+" from DUAL";
		    
					getlimitcodePstmt = connection.prepareStatement(entityQry4);
					getlimitcodePstmt.setString(1,userid);
					getlimitcodePstmt.setString(2,userid);
					getlimitcodeRs = getlimitcodePstmt.executeQuery();
					     
					
					 if (getlimitcodeRs.next()) {
					     
					 json.put("BALANCEAMOUNT", getlimitcodeRs.getString(1));
					     
					 }
					 
					 getlimitcodePstmt.close();
						getlimitcodeRs.close();
					
					
				//json.put("BALANCEAMOUNT", Double.parseDouble(json.getString("DEBIT"))-Double.parseDouble(json.getString("CREDIT")));
		
						
		resultJson.put("dsaReconcileDetails", json);	
		
		getlimitcodePstmt.close();
		getlimitcodeRs.close();
		
		String entityQry1 =  "SELECT ID||'-'||CREDITAMOUNT||'-'||CRDR_FLAG,NVL(PAYMENTREFERENCE,' '),NVL(ACCOUNTNO,' '),NVL(CREDITAMOUNT,' '),decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),(case CRDR_FLAG  when 'C' then DEBITACCCOUNTNUMBER else CREDITACCCOUNTNUMBER end),(select SERVICE_NAME from MOB_SERVICE_MASTER WHERE SERVICE_CODE=TRANS_TYPE),CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss') from FUND_TRANSFER_TBL "
				+" where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND ACCOUNTNO is not null order by TRANS_DATE";
	
		    
		getlimitcodePstmt = connection.prepareStatement(entityQry1);
		getlimitcodePstmt.setString(1,userid);
		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		     

		    while (getlimitcodeRs.next()) {
		     json.put("ID", getlimitcodeRs.getString(1));
		     json.put("REFERENCE", getlimitcodeRs.getString(2));
		     json.put("ACCOUNTNO", getlimitcodeRs.getString(3));
		     json.put("CREDITAMOUNT", getlimitcodeRs.getString(4));
		     json.put("CRDR_FLAG", getlimitcodeRs.getString(5));
		     json.put("SUSACCOUNTNO", getlimitcodeRs.getString(6));
		     json.put("SERVICE_NAME", getlimitcodeRs.getString(7));
		     json.put("CHANNEL", getlimitcodeRs.getString(8));
		     json.put("TRANS_DATE", getlimitcodeRs.getString(9));
		     
		     
		     JsonArray.add(json);
		     
		    }
		    
		    resultJson.put("dsaReconcileDetails2", JsonArray);
		    
		   
			    
			 
		    
		    
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


public ResponseDTO dsaReconcileConf(RequestDTO requestDTO) {

	logger.debug("Inside fetchlimitcodeInfo.. ");
	HashMap<String, Object> limitcodeDataMap = null;

	JSONObject resultJson = null;
	JSONObject json = null;
	JSONArray JsonArray= new JSONArray();
	PreparedStatement getlimitcodePstmt = null;
	ResultSet getlimitcodeRs = null;
	Connection connection = null;

	String  userid = "";
	String  rno = "";
	String  dsaQry = "select  USERID,DISPLAYNAME,BRANCH_DETAILS,MOBILE_NO from USSD_REQUEST where USERID=?";

	try {
		requestJSON = requestDTO.getRequestJSON();
		responseDTO = new ResponseDTO();
		limitcodeDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		connection = DBConnector.getConnection();

		userid = requestJSON.getString("userid");
		rno = requestJSON.getString("rno");
		

		getlimitcodePstmt = connection.prepareStatement(dsaQry);
		getlimitcodePstmt.setString(1,userid);

		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		json = new JSONObject();

		if (getlimitcodeRs.next()) {				
			json.put("USERID", getlimitcodeRs.getString(1));
		     json.put("DISPLAYNAME", getlimitcodeRs.getString(2));
		     json.put("BRANCH_DETAILS", getlimitcodeRs.getString(3));
		     json.put("MOBILE_NO", getlimitcodeRs.getString(4));
			
		}
		
		
		 
		
						
		resultJson.put("dsaReconcileDetails", json);	
		
		getlimitcodePstmt.close();
		getlimitcodeRs.close();
		
		
		String entityQry1 =  "SELECT ID||'-'||CREDITAMOUNT||'-'||CRDR_FLAG,NVL(PAYMENTREFERENCE,' '),NVL(ACCOUNTNO,' '),NVL(CREDITAMOUNT,' '),decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),(case CRDR_FLAG  when 'C' then DEBITACCCOUNTNUMBER else CREDITACCCOUNTNUMBER end),(select SERVICE_NAME from MOB_SERVICE_MASTER WHERE SERVICE_CODE=TRANS_TYPE),CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss') from FUND_TRANSFER_TBL "
				+" where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND ACCOUNTNO is not null AND ID in ("+rno+")  order by TRANS_DATE";
		    
		getlimitcodePstmt = connection.prepareStatement(entityQry1);
		getlimitcodePstmt.setString(1,userid);
		
		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		     

		    while (getlimitcodeRs.next()) {
		    	json.put("ID", getlimitcodeRs.getString(1));
			     json.put("REFERENCE", getlimitcodeRs.getString(2));
			     json.put("ACCOUNTNO", getlimitcodeRs.getString(3));
			     json.put("CREDITAMOUNT", getlimitcodeRs.getString(4));
			     json.put("CRDR_FLAG", getlimitcodeRs.getString(5));
			     json.put("SUSACCOUNTNO", getlimitcodeRs.getString(6));
			     json.put("SERVICE_NAME", getlimitcodeRs.getString(7));
			     json.put("CHANNEL", getlimitcodeRs.getString(8));
			     json.put("TRANS_DATE", getlimitcodeRs.getString(9));
			     
		     
		     JsonArray.add(json);
		     
		    }
		    
		    resultJson.put("dsaReconcileDetails2", JsonArray);
		    
		   
			    
			 
		    
		    
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

public ResponseDTO dsaReconcileAck(RequestDTO requestDTO) {

	logger.debug("Inside fetchlimitcodeInfo.. ");
	HashMap<String, Object> limitcodeDataMap = null;

	JSONObject resultJson = null;
	JSONObject json = null;
	HashMap<String,String> mp = null;
	JSONArray JsonArray= new JSONArray();
	JSONArray JsonArray1= new JSONArray();
	PreparedStatement getlimitcodePstmt = null;
	ResultSet getlimitcodeRs = null;
	Connection connection = null;

	String  userid = "";
	String  rno = "";
	
	String  cramt = "";
	String  dramt = "";
	String  balance = "";
	String  tellercrdr = "";
	String  suspcrdr = "";
	String telleraccno="";
	String suspenaccno="0040319770";
	String branccode="";
	int seqrefno=0;
	PreparedStatement pstmt2 = null;
	String  dsaQry = "select  USERID,DISPLAYNAME,BRANCH_DETAILS,MOBILE_NO from USSD_REQUEST where USERID=?";

	try {
		requestJSON = requestDTO.getRequestJSON();
		responseDTO = new ResponseDTO();
		limitcodeDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();

		connection = DBConnector.getConnection();

		userid = requestJSON.getString("userid");
		rno = requestJSON.getString("rno");
		
		cramt=requestJSON.getString("vselcr");
		dramt=requestJSON.getString("vseldr");
		balance=requestJSON.getString("vselbl");
		
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		

		getlimitcodePstmt = connection.prepareStatement(dsaQry);
		getlimitcodePstmt.setString(1,userid);

		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		json = new JSONObject();

		if (getlimitcodeRs.next()) {				
			json.put("USERID", getlimitcodeRs.getString(1));
		     json.put("DISPLAYNAME", getlimitcodeRs.getString(2));
		     json.put("BRANCH_DETAILS", getlimitcodeRs.getString(3));
		     json.put("MOBILE_NO", getlimitcodeRs.getString(4));
			
		}
		
		
		 
		
						
		resultJson.put("dsaReconcileDetails", json);	
		
		getlimitcodePstmt.close();
		getlimitcodeRs.close();
		
		
		String entityQry1 =  "SELECT ID||'-'||CREDITAMOUNT||'-'||CRDR_FLAG,NVL(PAYMENTREFERENCE,' '),NVL(ACCOUNTNO,' '),NVL(CREDITAMOUNT,' '),decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),(case CRDR_FLAG  when 'C' then DEBITACCCOUNTNUMBER else CREDITACCCOUNTNUMBER end),(select SERVICE_NAME from MOB_SERVICE_MASTER WHERE SERVICE_CODE=TRANS_TYPE),CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss') from FUND_TRANSFER_TBL "
				+" where RESPONSECODE='00' AND UPPER(USERID)=UPPER(?) AND RECONCILE_FLAG='N' AND ACCOUNTNO is not null AND ID in ("+rno+")  order by TRANS_DATE";
		    
		getlimitcodePstmt = connection.prepareStatement(entityQry1);
		getlimitcodePstmt.setString(1,userid);
		
		getlimitcodeRs = getlimitcodePstmt.executeQuery();
		     

		    while (getlimitcodeRs.next()) {
		    	json.put("ID", getlimitcodeRs.getString(1));
			     json.put("REFERENCE", getlimitcodeRs.getString(2));
			     json.put("ACCOUNTNO", getlimitcodeRs.getString(3));
			     json.put("CREDITAMOUNT", getlimitcodeRs.getString(4));
			     json.put("CRDR_FLAG", getlimitcodeRs.getString(5));
			     json.put("SUSACCOUNTNO", getlimitcodeRs.getString(6));
			     json.put("SERVICE_NAME", getlimitcodeRs.getString(7));
			     json.put("CHANNEL", getlimitcodeRs.getString(8));
			     json.put("TRANS_DATE", getlimitcodeRs.getString(9));
			     
		     
		     JsonArray.add(json);
		     
		    }
		    
		    getlimitcodePstmt.close();
		    getlimitcodeRs.close();
		    
		    String tellerquery =  "SELECT ACCOUNT_NO,BRN_CODE,TELLER_TRANS_SEQ.nextval FROM TELLER_INFO WHERE USER_ID=?";
			    
			getlimitcodePstmt = connection.prepareStatement(tellerquery);
			getlimitcodePstmt.setString(1,makerId);
			
			getlimitcodeRs = getlimitcodePstmt.executeQuery();
			     

			    while (getlimitcodeRs.next()) {
			    	
			    	telleraccno=getlimitcodeRs.getString(1);
			    	branccode=getlimitcodeRs.getString(2);
			    	seqrefno=getlimitcodeRs.getInt(3);
			    }
		    
		    
		    mp = new HashMap<String,String>();
		    
	if(balance.contains("-")){
		 	mp.put("DRACCOUNT", suspenaccno);
		    mp.put("CRACCOUNT", telleraccno);
		    mp.put("AMOUNT", balance.substring(1));
		    mp.put("NAME", json.getString("USERID"));
		    mp.put("DRBRANCHCODE", branccode);
		    mp.put("CRBRANCHCODE", (json.getString("BRANCH_DETAILS")).split("-")[0]);
		    tellercrdr = "C";
			suspcrdr = "D";
		    
	}else{
		mp.put("DRACCOUNT", telleraccno);
	    mp.put("CRACCOUNT", suspenaccno);
	    mp.put("AMOUNT", balance);
	    mp.put("NAME", json.getString("USERID"));
	    mp.put("DRBRANCHCODE", (json.getString("BRANCH_DETAILS")).split("-")[0]);
	    mp.put("CRBRANCHCODE", branccode);
	    
	    tellercrdr = "D";
		suspcrdr = "C";
	}
		    
		    
	/*JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.reconciliation(mp));
	
	if((json2.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
		pstmt2 = connection.prepareStatement("UPDATE FUND_TRANSFER_TBL SET RECONCILE_FLAG='Y',TELLER_REFNO="+seqrefno+" WHERE ID in ("+rno+")");
		pstmt2.executeUpdate();
		pstmt2.close();
		
		pstmt2 = connection.prepareStatement("INSERT INTO TELLER_TRANS(REFERENCE_NO,BATCHID,TELLER_NO,TELLER_AMOUNT,TELLER_DRCR,TELLER_BRANCH,SUSPENS_NO,SUSPENS_AMOUNT,SUSPENS_DRCR,DSA_BRANCH,TRANSACTION_DT,MAKER_ID,MAKER_TD) values(?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate)");
		
		pstmt2.setInt(1, seqrefno);
		pstmt2.setString(2, json2.getString("BatchId"));
		pstmt2.setString(3, telleraccno);
		pstmt2.setString(4, balance.substring(1));
		pstmt2.setString(5, tellercrdr);
		pstmt2.setString(6, branccode);
		pstmt2.setString(7, suspenaccno);
		pstmt2.setString(8, balance.substring(1));
		pstmt2.setString(9, suspcrdr);
		pstmt2.setString(10, (json.getString("BRANCH_DETAILS")).split("-")[0]);
		pstmt2.setString(11, makerId);
		pstmt2.executeUpdate();
		
		json.put("status_message", json2.getString("responseMessage")+" : Batch Id "+json2.getString("BatchId"));		

	}else{
		json.put("status_message", json2.getString("respdesc"));		
	
	}*/
	
	resultJson.put("message", json);
	
		    JSONObject jsonaudit = new JSONObject();
			 jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
			 jsonaudit.put("transCode", "dsareconcile");
			 jsonaudit.put("channel", "WEB");
			 jsonaudit.put("message", "Teller Reconcile selected user id   "+json.getString("USERID"));
			 jsonaudit.put("ip", remoteip);
			 jsonaudit.put("det1", "");
			 jsonaudit.put("det2", "");
			 jsonaudit.put("det3", "");
				
			CommonServiceDao csd=new CommonServiceDao();
			csd.auditTrailInsert(jsonaudit);
		    
		    resultJson.put("dsaReconcileDetails2", JsonArray);
		    
		   
		    connection.commit(); 
			 
		    
		    
		    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
		    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
		    responseDTO.setData(limitcodeDataMap);

	} catch (SQLException e) {
		responseDTO.addError("Transaction Fail.");
		logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
	} catch (Exception e) {
		responseDTO.addError("Transaction Fail.");
		logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
	} finally {
		try {

			if (getlimitcodeRs != null) {
				getlimitcodeRs.close();
			}
			
			if (pstmt2 != null) {
				pstmt2.close();
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

public ResponseDTO complaincedetails(RequestDTO requestDTO) {

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
	String application="";



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
		
		String fromdate=requestJSON.getString("fromdate");
		String todate=requestJSON.getString("todate");
		
		String searchval=requestJSON.getString("searchval");
		System.out.println("search type :: "+searchval);
		
	
		if(trans.equalsIgnoreCase("MOBILE")){
			application=requestJSON.getString("application");
			//System.out.println("#### application ###### "+application);
			
			if(searchval.equalsIgnoreCase("currdate")) {
				
			
		
				if(application.equalsIgnoreCase("ACCT_NO")){
					qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
							+ "from FUND_TRANSFER_TBL where ACCOUNTNO='"+fname+"' order by TRANS_DATE desc" ;	
				}
				
				if(application.equalsIgnoreCase("PAYMENT_REF")){
					qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
							+ "from FUND_TRANSFER_TBL where PAYMENTREFERENCE like '%"+fname+"%' order by TRANS_DATE desc";	
				}
			}
			
			if(searchval.equalsIgnoreCase("olddate")) {
				
				
				
				if(application.equalsIgnoreCase("ACCT_NO")){
					qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
							+ "from FUND_TRANSFER_TBL_HST where ACCOUNTNO='"+fname+"' and TRUNC(TRANS_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TRANS_DATE desc" ;	
				}
				
				if(application.equalsIgnoreCase("PAYMENT_REF")){
					qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
							+ "from FUND_TRANSFER_TBL_HST where PAYMENTREFERENCE like '%"+fname+"%' and TRUNC(TRANS_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TRANS_DATE desc";	
				}
			}
			
			/*if(application.equalsIgnoreCase("DATE")){
				qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
						+ "from FUND_TRANSFER_TBL where TRUNC(TRANS_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TRANS_DATE desc";	
			}*/
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("ACCOUNTNO", getlmtfeeRs.getString(1));
				json.put("CREDITAMOUNT", getlmtfeeRs.getString(2));
				json.put("CREDITCREDITINDICATOR", getlmtfeeRs.getString(3));
				json.put("CREDITPAYMENTREFERENCE", getlmtfeeRs.getString(4));
				json.put("BATCHID", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("USERID", getlmtfeeRs.getString(7));
				json.put("TRANS_DATE", getlmtfeeRs.getString(8));
				json.put("RESPONSEMESSAGE", "");
				json.put("STATUS", getlmtfeeRs.getString(10));
				json.put("BEN_PAYBILL_CODE", getlmtfeeRs.getString(11));
				json.put("BEN_CUST_NAME", (getlmtfeeRs.getString(12)).replace("'", " "));
				json.put("BEN_PAYBILL_ACTNO", getlmtfeeRs.getString(13));
				json.put("CREDITACCCOUNTNUMBER", getlmtfeeRs.getString(14));
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		}else{
			
			
			 
			application=requestJSON.getString("wapplication");
			//System.out.println("#### application ###### "+application);
			String agenttopup="";
			String fundtransfertoother="";
			String paybill="";
			String tranownbank="";
			
			if(application.equalsIgnoreCase("MOBILE_NO")){
				
				if(fname.length()==10) {
					fname="234"+fname;
				}
				if(fname.length()==11) {
					fname="234"+fname.substring(1);
				}
				
				
				
				
				/* qrey="select * from ((select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,TO_CHAR(NVL(AMOUNT,0)),CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),TXN_STAMP as tdate,DECODE(SERVICECODE,'AGCASHDEP','Cash Deposite','cash withdrawal') as scode,TO_CHAR(NVL(TXN_AMT,0)),TO_CHAR(NVL(FEE_AMT,0)),TXN_REF_NO  " + 
						"from WALLET_FIN_TXN where USER_ID='"+fname+"' and SERVICECODE in ('AGCASHDEP','AGCASHWTHD') ) " + 
						"union all  " + 
						"(select AFT.PAYMENTREFERENCE,AFT.USERID,AFT.CREDITTRANSACTIONID,AFT.DEBITTRANSACTIONID,TO_CHAR(NVL(AFT.TRNS_AMT,0)),AFT.CHANNEL,decode(AFT.RESPONSECODE,'00','Success','Failed'),to_char(AFT.TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),AFT.TRANS_DATE  as tdate,'Agent Fund' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_FUND_TRANSFER_TBL AFT LEFT JOIN WALLET_FIN_TXN WFT ON AFT.PAYMENTREFERENCE=WFT.ext_txn_ref_no where USER_ID='"+fname+"' AND TRANS_TYPE='AGENTTOPUP' ) " + 
						"union all  " + 
						"(select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,TO_CHAR(NVL(AWFT.AMOUNT,0)),AWFT.CHANNEL,decode(AWFT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWFT.TRANS_DATE_TIME  as tdate,'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_FTO_TRANS_TBL AWFT LEFT JOIN WALLET_FIN_TXN WFT ON AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO where AWFT.USER_ID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWPT.TRANS_DATE_TIME  as tdate,DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_PB_TRANS_TBL AWPT LEFT JOIN WALLET_FIN_TXN WFT ON AWPT.FTBATCHID=WFT.ext_txn_ref_no where AWPT.USER_ID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWTT.FTBATCHID,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,TO_CHAR(NVL(AWTT.AMOUNT,0)),AWTT.CHANNEL,decode(AWTT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWTT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWTT.TRANS_DATE_TIME  as tdate,'Fund Transfer Own bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  " + 
						"from AGENT_WALLET_TRANS_TBL AWTT LEFT JOIN WALLET_FIN_TXN WFT ON AWTT.FTBATCHID=WFT.ext_txn_ref_no where AWTT.USER_ID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R') "+
						"union all  "+
						"(select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  " + 
						"from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no where TTA.APPROVEDBY='"+fname+"' and TTA.INTERNALID=WFT.txn_ref_no)) order by tdate desc";
				    */
				qrey="select * from ((select FT.EXT_TXN_REF_NO,FT.USER_ID,FT.DR_ACCOUNT,FT.CR_ACCOUNT,TO_CHAR(NVL(FT.AMOUNT,0)),FT.CHANNEL,(CASE WHEN wat.RESP_CODE IN ('00','69')   THEN 'Success'  ELSE 'Failed' END)," +
                        "to_char(FT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),FT.TXN_STAMP as tdate, " +
                        " (select SCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER SCM where SCM.SERVICECODE=coalesce(ft.SERVICECODE,wat.trans_type)),TO_CHAR(NVL(FT.TXN_AMT,0)),TO_CHAR(NVL(FT.FEE_AMT,0)),TXN_REF_NO " +
                        " from wallet_agncore_txntbl wat LEFT JOIN WALLET_FIN_TXN FT on   wat.pay_ref_no=ft.EXT_TXN_REF_NO   where   ft.USER_ID='"+fname+"'  AND substr(TXN_REF_NO,1,1)!='R' "+
                        "  union all " +
                        " select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0))," +
                        " NVL(WFT.CHANNEL,'POS'), " +
                        " (CASE WHEN TTA.BANKRESPONSECODE IN ('00','69')  AND NVL(WFT.ext_txn_ref_no,'NO_DATA')!='NO_DATA' " +
                        " THEN 'Success'" +
                        " ELSE 'Failed'" +
                        " END) AS STATUS," +
                        " to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH'," +
                        " 'Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH'," +
                        " 'Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0))," +
                        " TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no" +
                        " where APPROVEDBY='"+fname+"')) order by tdate desc";	
				
			}else if(application.equalsIgnoreCase("PAYMENT_REF")){
				
				/*qrey="select * from ((select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,TO_CHAR(NVL(AMOUNT,0)),CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),TXN_STAMP as tdate,DECODE(SERVICECODE,'AGCASHDEP','Cash Deposite','cash withdrawal') as scode,TO_CHAR(NVL(TXN_AMT,0)),TO_CHAR(NVL(FEE_AMT,0)),TXN_REF_NO  " + 
						"from WALLET_FIN_TXN where EXT_TXN_REF_NO='"+fname+"' and SERVICECODE in ('AGCASHDEP','AGCASHWTHD') ) " + 
						"union all  " + 
						"(select AFT.PAYMENTREFERENCE,AFT.USERID,AFT.CREDITTRANSACTIONID,AFT.DEBITTRANSACTIONID,TO_CHAR(NVL(AFT.TRNS_AMT,0)),AFT.CHANNEL,decode(AFT.RESPONSECODE,'00','Success','Failed'),to_char(AFT.TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),AFT.TRANS_DATE  as tdate,'Agent Fund' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_FUND_TRANSFER_TBL AFT LEFT JOIN WALLET_FIN_TXN WFT ON AFT.PAYMENTREFERENCE=WFT.ext_txn_ref_no where AFT.PAYMENTREFERENCE='"+fname+"' AND TRANS_TYPE='AGENTTOPUP' ) " + 
						"union all  " + 
						"(select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,TO_CHAR(NVL(AWFT.AMOUNT,0)),AWFT.CHANNEL,decode(AWFT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWFT.TRANS_DATE_TIME  as tdate,'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_FTO_TRANS_TBL AWFT LEFT JOIN WALLET_FIN_TXN WFT ON AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO where AWFT.FTBATCHID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWPT.TRANS_DATE_TIME  as tdate,DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_PB_TRANS_TBL AWPT LEFT JOIN WALLET_FIN_TXN WFT ON AWPT.FTBATCHID=WFT.ext_txn_ref_no where AWPT.FTBATCHID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWTT.FTBATCHID,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,TO_CHAR(NVL(AWTT.AMOUNT,0)),AWTT.CHANNEL,decode(AWTT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWTT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWTT.TRANS_DATE_TIME  as tdate,'Fund Transfer Own bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_TRANS_TBL AWTT LEFT JOIN WALLET_FIN_TXN WFT ON AWTT.FTBATCHID=WFT.ext_txn_ref_no where AWTT.FTBATCHID='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R') "+
						"union all  "+
						"(select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  " + 
						"from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no where TTA.BANKRRN='"+fname+"' and TTA.INTERNALID=WFT.txn_ref_no)) order by tdate desc";
				*/
				qrey="select * from ((select FT.EXT_TXN_REF_NO,FT.USER_ID,FT.DR_ACCOUNT,FT.CR_ACCOUNT,TO_CHAR(NVL(FT.AMOUNT,0)),FT.CHANNEL,(CASE WHEN wat.RESP_CODE IN ('00','69')   THEN 'Success'  ELSE 'Failed' END)," +
                        "to_char(FT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),FT.TXN_STAMP as tdate, " +
                        " (select SCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER SCM where SCM.SERVICECODE=coalesce(ft.SERVICECODE,wat.trans_type)),TO_CHAR(NVL(FT.TXN_AMT,0)),TO_CHAR(NVL(FT.FEE_AMT,0)),TXN_REF_NO " +
                        " from  wallet_agncore_txntbl wat LEFT JOIN WALLET_FIN_TXN FT on  ft.EXT_TXN_REF_NO=wat.pay_ref_no     where   ft.EXT_TXN_REF_NO='"+fname+"' and substr(TXN_REF_NO,1,1)!='R'  " +
                        "  union all " +
                        " select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0))," +
                        " NVL(WFT.CHANNEL,'POS'), " +
                        " (CASE WHEN TTA.BANKRESPONSECODE IN ('00','09')  AND NVL(WFT.ext_txn_ref_no,'NO_DATA')!='NO_DATA' " +
                        " THEN 'Success'" +
                        " ELSE 'Failed'" +
                        " END) AS STATUS," +
                        " to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH'," +
                        " 'Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH'," +
                        " 'Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0))," +
                        " TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no" +
                        " where TTA.BANKRRN='"+fname+"')) order by tdate desc";	
			}else if(application.equalsIgnoreCase("WALLET_REF")){
				
				/*qrey="select * from ((select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,TO_CHAR(NVL(AMOUNT,0)),CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),TXN_STAMP as tdate,DECODE(SERVICECODE,'AGCASHDEP','Cash Deposite','cash withdrawal') as scode,TO_CHAR(NVL(TXN_AMT,0)),TO_CHAR(NVL(FEE_AMT,0)),TXN_REF_NO  " + 
						"from WALLET_FIN_TXN where TXN_REF_NO='"+fname+"' and SERVICECODE in ('AGCASHDEP','AGCASHWTHD') ) " + 
						"union all  " + 
						"(select AFT.PAYMENTREFERENCE,AFT.USERID,AFT.CREDITTRANSACTIONID,AFT.DEBITTRANSACTIONID,TO_CHAR(NVL(AFT.TRNS_AMT,0)),AFT.CHANNEL,decode(AFT.RESPONSECODE,'00','Success','Failed'),to_char(AFT.TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),AFT.TRANS_DATE  as tdate,'Agent Fund' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_FUND_TRANSFER_TBL AFT LEFT JOIN WALLET_FIN_TXN WFT ON AFT.PAYMENTREFERENCE=WFT.ext_txn_ref_no where WFT.txn_ref_no='"+fname+"' AND TRANS_TYPE='AGENTTOPUP' ) " + 
						"union all  " + 
						"(select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,TO_CHAR(NVL(AWFT.AMOUNT,0)),AWFT.CHANNEL,decode(AWFT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWFT.TRANS_DATE_TIME  as tdate,'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_FTO_TRANS_TBL AWFT LEFT JOIN WALLET_FIN_TXN WFT ON AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO where WFT.TXN_REF_NO='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWPT.TRANS_DATE_TIME  as tdate,DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_PB_TRANS_TBL AWPT LEFT JOIN WALLET_FIN_TXN WFT ON AWPT.FTBATCHID=WFT.ext_txn_ref_no where WFT.txn_ref_no='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R' ) " + 
						"union all  " + 
						"(select AWTT.FTBATCHID,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,TO_CHAR(NVL(AWTT.AMOUNT,0)),AWTT.CHANNEL,decode(AWTT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWTT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWTT.TRANS_DATE_TIME  as tdate,'Fund Transfer Own bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO   " + 
						"from AGENT_WALLET_TRANS_TBL AWTT LEFT JOIN WALLET_FIN_TXN WFT ON AWTT.FTBATCHID=WFT.ext_txn_ref_no where WFT.txn_ref_no='"+fname+"' AND substr(TXN_REF_NO,1,1)!='R') "+
						"union all  "+
						"(select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  " + 
						"from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no where WFT.txn_ref_no='"+fname+"' and TTA.INTERNALID=WFT.txn_ref_no)) order by tdate desc";
				  */
				  qrey="select * from ((select FT.EXT_TXN_REF_NO,FT.USER_ID,FT.DR_ACCOUNT,FT.CR_ACCOUNT,TO_CHAR(NVL(FT.AMOUNT,0)),FT.CHANNEL,(CASE WHEN wat.RESP_CODE IN ('00','09')   THEN 'Success'  ELSE 'Failed' END)," +
                        "to_char(FT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),FT.TXN_STAMP as tdate, " +
                        " (select SCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER SCM where SCM.SERVICECODE=coalesce(ft.SERVICECODE,wat.trans_type)),TO_CHAR(NVL(FT.TXN_AMT,0)),TO_CHAR(NVL(FT.FEE_AMT,0)),TXN_REF_NO " +
                        " from   wallet_agncore_txntbl wat LEFT JOIN WALLET_FIN_TXN FT on ft.EXT_TXN_REF_NO=wat.pay_ref_no   where   ft.TXN_REF_NO='"+fname+"' and  substr(TXN_REF_NO,1,1)!='R'  " +
                        "  union all " +
                        " select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0))," +
                        " NVL(WFT.CHANNEL,'POS'), " +
                        " (CASE WHEN TTA.BANKRESPONSECODE IN ('00','09')  AND NVL(WFT.ext_txn_ref_no,'NO_DATA')!='NO_DATA' " +
                        " THEN 'Success'" +
                        " ELSE 'Failed'" +
                        " END) AS STATUS," +
                        " to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH'," +
                        " 'Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH'," +
                        " 'Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0))," +
                        " TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no" +
                        " where TTA.INTERNALID='"+fname+"')) order by tdate desc";
				  
			}else if(application.equalsIgnoreCase("POSRRN")){
				
				qrey="select * from ((select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),TXN_REF_NO  " + 
						"from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no where TTA.POSRRN='"+fname+"' and TTA.TERMINALNUMBER='"+requestJSON.getString("terminalid")+"' and TTA.INTERNALID=WFT.txn_ref_no)) order by tdate desc";
				
			}
				
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("TXN_REF_NO", getlmtfeeRs.getString(1));
					json.put("USER_ID", getlmtfeeRs.getString(2));
					json.put("DR_ACCOUNT", getlmtfeeRs.getString(3));
					json.put("CR_ACCOUNT", getlmtfeeRs.getString(4));
					json.put("AMOUNT", getlmtfeeRs.getString(5));
					json.put("CHANNEL", getlmtfeeRs.getString(6));
					json.put("STATUS", getlmtfeeRs.getString(7));
					json.put("TXN_DTTM", getlmtfeeRs.getString(8));
					json.put("SERVICECODE", getlmtfeeRs.getString(10));
					json.put("TXN_AMT", getlmtfeeRs.getString(11));
					json.put("FEE_AMT", getlmtfeeRs.getString(12));
					json.put("WALLET_REF_NO", getlmtfeeRs.getString(13));
					
					lmtJsonArray.add(json);
				}
				
				
				
				
				
			
			
			
			
			/*if(application.equalsIgnoreCase("DATE")){
				qrey = "select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,PAYMENTREFERENCE,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DECODE(RESPONSECODE,'00','Success','Failed'),NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' '),NVL(BEN_CUST_NAME,' '),NVL(BEN_PAYBILL_ACTNO,' '),CREDITACCCOUNTNUMBER "
						+ "from FUND_TRANSFER_TBL where TRUNC(TRANS_DATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TRANS_DATE desc";	
			}*/
			
			
			

			
		
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



public ResponseDTO ParameterCofigAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	String adminType=null;
	String adminName=null;
	
	int ad=1;
	int bn=1;
	int sp=1;
	int ua=1;
	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		String branchQry="insert into CONFIG_DATA(KEY_GROUP,KEY_TYPE,KEY_ID,KEY_VALUE,STATUS,MAKER_ID,MAKER_DTTM) " +
				" values(?,?,SEQ_PARAMETER.nextval,?,?,?,sysdate) ";

		servicePstmt=connection.prepareStatement(branchQry);
	


		JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			adminType = reqData.getString("adminType");
			adminName = reqData.getString("adminName");
			

			servicePstmt.setString(1,"USER_DESIGNATION");
			if(adminType.equalsIgnoreCase("Super Admin")){
				servicePstmt.setString(2,"BADM");
				servicePstmt.setString(3,"SDM"+sp);
				servicePstmt.setString(4,adminName);
				sp=sp+1;
			}
			
			if(adminType.equalsIgnoreCase("User Admin")){
				servicePstmt.setString(2,"SDM");
				servicePstmt.setString(3,"UDM"+ua);
				servicePstmt.setString(4,adminName);
				ua=ua+1;
			}
			
			if(adminType.equalsIgnoreCase("Admin")){
				servicePstmt.setString(2,"UDM");
				servicePstmt.setString(3,"ADM"+ad);
				servicePstmt.setString(4,adminName);
				ad=ad+1;
			}
			
			if(adminType.equalsIgnoreCase("Branch Admin")){
				servicePstmt.setString(2,"ADM");
				servicePstmt.setString(3,"BRN"+bn);
				servicePstmt.setString(4,adminName);
				bn=bn+1;
			}
			
			servicePstmt.setString(5,maker_id);
			
			
			
			servicePstmt.addBatch();
			

		}

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
		
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "ParameterCreation");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: Parameter Configuration : Admin Type "+adminType);
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

public ResponseDTO ParameterCofigAuthAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	String adminType=null;
	String adminName=null;
	String adminNamedesc=null;
	int ad=1;
	int bn=1;
	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
		String branchQry="insert into CONFIG_DATA(KEY_GROUP,KEY_TYPE,KEY_ID,KEY_VALUE,STATUS,MAKER_ID,MAKER_DTTM) " +
				" values(?,?,SEQ_PARAMETER.nextval,?,?,?,sysdate) ";

		servicePstmt=connection.prepareStatement(branchQry);
	


		JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			adminType = reqData.getString("adminType");
			adminName = reqData.getString("adminName");
			adminNamedesc = reqData.getString("adminNamedesc");
			
			//System.out.println("adminType"+adminType+" :: adminName"+adminName+" :: adminNamedesc"+adminNamedesc);

			servicePstmt.setString(1,"USER_DESIGNATION");
			servicePstmt.setString(2,adminNamedesc);
			
			
			
			if(adminType.equalsIgnoreCase("VIEW")){
				servicePstmt.setString(3,adminNamedesc+"V");	
			}
			
			if(adminType.equalsIgnoreCase("AUTHORIZE")){
				servicePstmt.setString(3,adminNamedesc+"A");
			}
			
			if(adminType.equalsIgnoreCase("INITIATE")){
				servicePstmt.setString(3,adminNamedesc+"I");
			}
			
			
			servicePstmt.setString(4,adminName+" - "+adminType);
			
			servicePstmt.setString(5,maker_id);
			
			
			
			servicePstmt.addBatch();
			

		}

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
		
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "ParameterCreation");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: Parameter Configuration : Admin Type "+adminType);
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


public ResponseDTO ClusterCreationAck(RequestDTO requestDTO) {


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
		
		String branchQry="insert into CLUSTER_TBL(CLUSTER_ID,CLUSTER_NAME,MAKER_ID,MAKER_DT) " +
				" values(?,?,?,sysdate) ";

		servicePstmt=connection.prepareStatement(branchQry);
	


		JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			adminType = reqData.getString("adminType");
			adminName = reqData.getString("adminName");
			
			
			System.out.println("adminType :: "+adminType+" :: adminName"+adminName+" maker id ::: "+maker_id);

			servicePstmt.setString(1,adminType);
			servicePstmt.setString(2,adminName);
			servicePstmt.setString(3,maker_id);
			
			
			
			servicePstmt.addBatch();
			

		}

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
		
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "clusterCreation");
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


public ResponseDTO notification(RequestDTO requestDTO) {

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

		
		
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 
		 String Qry = "select UI.CLUSTER_ID,(SELECT STATUS from CONFIG_DATA where  key_group='USER_DESIGNATION' and KEY_VALUE=UI.USER_TYPE),UI.USER_TYPE from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND ULC.LOGIN_USER_ID='"+makerId+"'";
		 servicePstmt = connection.prepareStatement(Qry);
		 serviceRS = servicePstmt.executeQuery();
		 while (serviceRS.next()) {
			 branchid=serviceRS.getString(1);
			 usertype=serviceRS.getString(2);
		 }
		 servicePstmt.close();
		 serviceRS.close();
		
		 if(usertype.contains("INITIATE")){
			 qrey = "select AP.REF_NUM,AP.MAKER_ID,to_char(AP.MAKER_DTTM,'dd-MON-yyyy'),NVL(AP.CHECKER_ID,' '),NVL(to_char(AP.CHECKER_DTTM,'dd-MON-yyyy'),' '),NVL((select RELATION FROM AUTH_RELATION WHERE RELATION_CODE=AP.AUTH_FLAG),' '),NVL((select HEADING_NAMES FROM AUTH_MASTER WHERE AUTH_CODE=AP.AUTH_CODE AND RELATION=AP.AUTH_FLAG),' '),DECODE(AP.STATUS,'P','Process','C','Approval','R','Reject') FROM AUTH_PENDING AP where AP.MAKER_ID='"+makerId+"' order by AP.MAKER_DTTM desc"; 
		 }else if(usertype.contains("AUTHORIZE")){
			 qrey = "select AP.REF_NUM,AP.MAKER_ID,to_char(AP.MAKER_DTTM,'dd-MON-yyyy'),NVL(AP.CHECKER_ID,' '),NVL(to_char(AP.CHECKER_DTTM,'dd-MON-yyyy'),' '),NVL((select RELATION FROM AUTH_RELATION WHERE RELATION_CODE=AP.AUTH_FLAG),' '),NVL((select HEADING_NAMES FROM AUTH_MASTER WHERE AUTH_CODE=AP.AUTH_CODE AND RELATION=AP.AUTH_FLAG),' '),DECODE(AP.STATUS,'P','Process','C','Approval','R','Reject') FROM AUTH_PENDING AP where AP.MAKER_BRCODE='"+branchid+"'  order by AP.MAKER_DTTM desc"; 
		 }else{
			 qrey = "select AP.REF_NUM,AP.MAKER_ID,to_char(AP.MAKER_DTTM,'dd-MON-yyyy'),NVL(AP.CHECKER_ID,' '),NVL(to_char(AP.CHECKER_DTTM,'dd-MON-yyyy'),' '),NVL((select RELATION FROM AUTH_RELATION WHERE RELATION_CODE=AP.AUTH_FLAG),' '),NVL((select HEADING_NAMES FROM AUTH_MASTER WHERE AUTH_CODE=AP.AUTH_CODE AND RELATION=AP.AUTH_FLAG),' '),DECODE(AP.STATUS,'P','Process','C','Approval','R','Reject') FROM AUTH_PENDING AP  order by AP.MAKER_DTTM desc"; 
		 }

		getlmtfeePstmt = connection.prepareStatement(qrey);

		getlmtfeeRs = getlmtfeePstmt.executeQuery();

		while (getlmtfeeRs.next()) {

			json = new JSONObject();
			json.put("REF_NUM", getlmtfeeRs.getString(1));
			json.put("MAKER_ID", getlmtfeeRs.getString(2));
			json.put("MAKER_DTTM", getlmtfeeRs.getString(3));
			json.put("CHECKER_ID", getlmtfeeRs.getString(4));
			json.put("CHECKER_DTTM", getlmtfeeRs.getString(5));
			json.put("MAIN_MENU", getlmtfeeRs.getString(6));
			json.put("ACTION_MENU", getlmtfeeRs.getString(7));
			json.put("STATUS", getlmtfeeRs.getString(8));
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


public ResponseDTO NotificationView(RequestDTO requestDTO) {
	
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
		
		logger.debug("[AgentDAO][NotificationView] Inside AgentDAO  "+ requestJSON.getString("refno"));

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		viewDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		 JSONObject json = new JSONObject();
	
			 String viewReport="select AP.REF_NUM,AP.MAKER_ID,to_char(AP.MAKER_DTTM,'dd-MON-yyyy'),NVL(AP.CHECKER_ID,' '),NVL(to_char(AP.CHECKER_DTTM,'dd-MON-yyyy'),' '),NVL((select RELATION FROM AUTH_RELATION WHERE RELATION_CODE=AP.AUTH_FLAG),' '),NVL((select HEADING_NAMES FROM AUTH_MASTER WHERE AUTH_CODE=AP.AUTH_CODE AND RELATION=AP.AUTH_FLAG),' '),DECODE(AP.STATUS,'P','Process','C','Approval','R','Reject'),NVL(ACTION_DETAILS,' '),NVL(REASON,' ') FROM AUTH_PENDING AP WHERE AP.REF_NUM=?";
				
				viewDataPstmt = connection.prepareStatement(viewReport);
				viewDataPstmt.setString(1,requestJSON.getString("refno"));
				viewRS = viewDataPstmt.executeQuery();
				
				
				 while(viewRS.next())
					{
					 json=new JSONObject();
					 json.put("REF_NUM", viewRS.getString(1));
						json.put("MAKER_ID", viewRS.getString(2));
						json.put("MAKER_DTTM", viewRS.getString(3));
						json.put("CHECKER_ID", viewRS.getString(4));
						json.put("CHECKER_DTTM", viewRS.getString(5));
						json.put("MAIN_MENU", viewRS.getString(6));
						json.put("ACTION_MENU", viewRS.getString(7));
						json.put("STATUS", viewRS.getString(8));
						json.put("ACTION_DETAILS", viewRS.getString(9));
						json.put("REASON", viewRS.getString(10));
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

public ResponseDTO branchActivities(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][branchActivities]");

	
	
	HashMap<String, Object> viewDataMap = null;
	Connection connection = null;

	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	String branchid="";
	String usertype="";

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();
		viewDataMap = new HashMap<String, Object>();
		
		 JSONObject json = new JSONObject();
		 JSONObject jsonlmt = new JSONObject();
		

		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 
		 String Qry = "select UI.CLUSTER_ID,(SELECT STATUS from CONFIG_DATA where  key_group='USER_DESIGNATION' and KEY_VALUE=UI.USER_TYPE),UI.USER_TYPE from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND ULC.LOGIN_USER_ID='"+makerId+"'";
		 servicePstmt = connection.prepareStatement(Qry);
		 serviceRS = servicePstmt.executeQuery();
		 while (serviceRS.next()) {
			 branchid=serviceRS.getString(1);
			 usertype=serviceRS.getString(2);
		 }
		 servicePstmt.close();
		 serviceRS.close();
		 
		 String productQry ="";
		 if(usertype.contains("INITIATE")){
			 productQry = "select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID='"+branchid+"'"; 
			 //productQry = "select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND ULC.LOGIN_USER_ID='"+makerId+"'"; 
		 }else if(usertype.contains("AUTHORIZE")){
			 productQry = "select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID='"+branchid+"'";
			 //productQry = "select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND UI.CLUSTER_ID='"+branchid+"'"; 
		 }else{
			// productQry = "select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID"; 
			 productQry = "select CLUSTER_ID||'-'||CLUSTER_NAME from CLUSTER_TBL"; 
		 }
		 
			
			servicePstmt = connection.prepareStatement(productQry);
			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(1));
			}
			json.put("PRODUCT_TEMP", jsonlmt);
			jsonlmt.clear();
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][branchActivities] SQLException in branchActivities [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][branchActivities] Internal Error Occured While Executing.");
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

public ResponseDTO CustomerQueries(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][CustomerQueries]");

	logger.debug("Inside  CustomerQueries.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		

		
		String userReport =" select ID,USER_ID,SUBJECT,CREATE_DT FROM TBL_CHAT_MAIN WHERE STATUS='N' order by CREATE_DT";
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("ID",serviceRS.getString(1));
			 json.put("USER_ID",serviceRS.getString(2));
			 json.put("SUBJECT",serviceRS.getString(3));
			 json.put("CREATE_DT",serviceRS.getString(4));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in CustomerQueries [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in CustomerQueries [" + e.getMessage()
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

public ResponseDTO CustomerQueriesView(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	
	logger.debug("inside [AgentDAO][CustomerQueriesView]");

	
	
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
		
		logger.debug("[AgentDAO][CustomerQueriesView] Inside AgentDAO  "+ requestJSON.getString("refno"));

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		viewDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		 JSONObject json = new JSONObject();
	
			 String viewReport="SELECT MCM.FIRST_NAME,MCM.USER_ID,MCM.CUSTOMER_CODE,MCM.GENDER,TCM.SUBJECT,TCM.ID FROM MOB_CUSTOMER_MASTER MCM,TBL_CHAT_MAIN TCM WHERE UPPER(MCM.USER_ID)=UPPER(TCM.USER_ID) AND TCM.ID=?";
				
				viewDataPstmt = connection.prepareStatement(viewReport);
				viewDataPstmt.setInt(1,Integer.parseInt(requestJSON.getString("refno")));
				viewRS = viewDataPstmt.executeQuery();
				
				
				 while(viewRS.next())
					{
					 json=new JSONObject();
					 json.put("FIRST_NAME", viewRS.getString(1));
					 json.put("USER_ID", viewRS.getString(2));
					 json.put("CUSTOMER_CODE", viewRS.getString(3));
					 json.put("GENDER", viewRS.getString(4));
					 json.put("SUBJECT", viewRS.getString(5));
					 json.put("ID", viewRS.getString(6));
					 } 
				 json.put("HISTORY", getHistory(connection, Integer.parseInt(requestJSON.getString("refno"))));

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

public static String getHistory(Connection conn,int refno){
	
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;
	StringBuilder sb=new StringBuilder();
	sb.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\" ");
	String userReport ="";
	try{
		servicePstmt = conn.prepareStatement("select USER_TYPE,MESSAGE,USER_ID,MAKER_DT from TBL_CHAT WHERE ID="+refno+" order by MAKER_DT ");
		serviceRS = servicePstmt.executeQuery();
		 while(serviceRS.next())
			{
			if(serviceRS.getString(1).equalsIgnoreCase("C")){
				sb.append("<tr>");
				sb.append("<td width=\"25%\"><div align=\"left\">"+serviceRS.getString(3)+"</div><div align=\"left\">"+serviceRS.getString(4)+"</div></td>");
				sb.append("<td width=\"20%\"></td>");
				sb.append("<td width=\"10%\"></td>");
				sb.append("<td width=\"20%\"><div class=\"bubblet\"  >"+serviceRS.getString(2)+"</div></td>");
				sb.append("<td width=\"25%\"></td>");
				sb.append("</tr>");
			}
			if(serviceRS.getString(1).equalsIgnoreCase("B")){
				sb.append("<tr>");
				sb.append("<td width=\"25%\"></td>");
				sb.append("<td width=\"20%\"><div class=\"bubbled\"  >"+serviceRS.getString(2)+"</div></td>");
				sb.append("<td width=\"10%\"></td>");
				sb.append("<td width=\"20%\"><div align=\"right\">"+serviceRS.getString(3)+"</div><div align=\"right\">"+serviceRS.getString(4)+"</div></td>");
				sb.append("<td width=\"25%\"></td>");
				sb.append("</tr>");
			}
			
			}
		 sb.append("</table>");	
			 
		}catch(Exception e){
		e.printStackTrace();
	}finally{
		try{
			servicePstmt.close();
			serviceRS.close();
		}catch(Exception e){
			
		}
	}
	
	return sb.toString();
}

public ResponseDTO CustomerQueriesReplay(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	
	logger.debug("inside [AgentDAO][CustomerQueriesReplay]");

	
	
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
		
		logger.debug("[AgentDAO][CustomerQueriesReplay] Inside  "+ requestJSON.getString("refno"));

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		viewDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		 JSONObject json = new JSONObject();
		 String refno=requestJSON.getString("refno");
		 String userid=requestJSON.getString("userid");
		 String filename=requestJSON.getString("filename");
		 String Narration=requestJSON.getString("Narration");
		 
		 String makerId=requestJSON.getString("makerId");
		 String remoteip=requestJSON.getString("remoteip");
		 
		 connection.setAutoCommit(false);
		
			
				
				pstmt = connection.prepareStatement("UPDATE  TBL_CHAT_MAIN SET STATUS='R' WHERE ID=?");			
				pstmt.setInt(1, Integer.parseInt(refno));
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("INSERT INTO  TBL_CHAT(ID,USER_ID,USER_TYPE,SUBJECT,MESSAGE) values(?,?,?,?,?)");			
				pstmt.setInt(1, Integer.parseInt(refno));
				pstmt.setString(2, makerId);
				pstmt.setString(3, "B");
				pstmt.setString(4, filename);
				pstmt.setString(5, Narration);
				pstmt.executeUpdate();
				connection.commit();
		
					 json=new JSONObject();
					 json.put("MESSAGE_SUS", "User id is "+userid +" , Subject  "+filename+" Successfully Sent");
					 
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
			
        } catch (SQLException e) {
		logger.debug("SQLException in gtFileApprovalAction [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][gtFileApprovalActionAck] Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtFileApprovalActionAck] SQLException in gtFileApprovalAction [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][gtFileApprovalActionAck] Internal Error Occured While Executing.");
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

public ResponseDTO CustomerQueriesReplied(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][CustomerQueries]");

	logger.debug("Inside  CustomerQueries.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		

		
		String userReport =" select ID,USER_ID,SUBJECT,CREATE_DT FROM TBL_CHAT_MAIN WHERE STATUS='R' order by CREATE_DT";
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("ID",serviceRS.getString(1));
			 json.put("USER_ID",serviceRS.getString(2));
			 json.put("SUBJECT",serviceRS.getString(3));
			 json.put("CREATE_DT",serviceRS.getString(4));
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in CustomerQueries [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in CustomerQueries [" + e.getMessage()
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
public static void main(String[] args) {
	try{
		/*JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getBillers());
		JSONArray jsonarray =  JSONArray.fromObject(json1.get("billersdata"));
		Iterator iterator = jsonarray.iterator();
		TreeSet<String> al=new TreeSet<String>();
	       while (iterator.hasNext()) {
	    	   JSONObject jsonobj=(JSONObject)iterator.next();
		       //System.out.println((jsonobj).get("shortName"));
		       al.add(((jsonobj).get("shortName")).toString()); 
		       if(((jsonobj).get("shortName")).toString().equalsIgnoreCase("AIRTIME")){
		    	System.out.println(jsonobj);   
		       }
	       }*/
	
		  
	      /* Iterator<String> itr=al.iterator();  
	       while(itr.hasNext()){  
	        System.out.println(itr.next());
	       }   
		*/
		 /***** Account from internet bank****/ 
		/*JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getCustomerAccountDetail("yisedu"));
		//System.out.println("kailash ::: "+json1.getString("balInfo"));
		JSONArray jsonarray =  JSONArray.fromObject(json1.get("balInfo"));
		Iterator iterator = jsonarray.iterator();
		TreeSet<String> al=new TreeSet<String>();
	       while (iterator.hasNext()) {
	    	   JSONObject jsonobj=(JSONObject)iterator.next();
		       
		       JSONArray jsonarray1 =  JSONArray.fromObject(jsonobj.get("custAcctInfo"));
				Iterator iterator1 = jsonarray1.iterator();
				 while (iterator1.hasNext()) {
			    	   JSONObject jsonobj1=(JSONObject)iterator1.next();
			    	   //System.out.println(jsonobj1);
			    	   System.out.println(jsonobj1.get("accountNo")+"--"+jsonobj1.get("accountname")+"--"+jsonobj1.get("brnCode")+"--"+jsonobj1.get("accountproduct")+"--"+jsonobj1.get("accountstatus"));
				 }
		     
	       }*/
		 /***** Account from core bank****/ 
		/*JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid("003596147"));
		//System.out.println(json1);
		JSONArray jsonarray =  JSONArray.fromObject(json1.get("custactinfo"));
		Iterator iterator = jsonarray.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonobj=(JSONObject)iterator.next();
			//System.out.println(jsonobj);
			JSONArray jsonarray1 =  JSONArray.fromObject(jsonobj.get("acctsumm"));
			Iterator iterator1 = jsonarray1.iterator();
			 while (iterator1.hasNext()) {
				 JSONObject jsonobj1=(JSONObject)iterator1.next(); 
				 System.out.println(jsonobj1.get("accountNo"));
			 }
			
		}*/
		
		JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid("003596147"));
		//System.out.println(json1);
		JSONArray jsonarray =  JSONArray.fromObject(json1.get("custinfo"));
		Iterator iterator = jsonarray.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonobj=(JSONObject)iterator.next();
			System.out.println(jsonobj.get("custPhone"));
			
			
		}
		  
	}catch(Exception e){
		e.printStackTrace();
	}
	
}


public ResponseDTO FraudCreationconfigAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	
	String amount="0";
	String day="0";
	String hour="0";
	String acctno="0";
	String mobileno="0";
	String userid="";
	String using="";
	String channel="";
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String fraudcode=requestJSON.getString("fraudcode");
		String frauddesc=requestJSON.getString("frauddesc");
		String contcentermailid=requestJSON.getString("contcentermailid");
		String decisions=requestJSON.getString("decisions");
		String Customersms=requestJSON.getString("Customersms");
		String Customeremail=requestJSON.getString("Customeremail");
		String ruledesc=requestJSON.getString("ruledesc");
		String rulecode=requestJSON.getString("rulecode");
		String ruletype=requestJSON.getString("ruletype");
		
		
		
		
		connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO FRAUD_MASTER(FRAUD_ID,FRAUD_DESC,RES_CUSTOMER_SMS,RES_CUSTOMER_EMAIL,CALLCENTERMAILIDS,DECISION,RULE_DESC,RULE_PARAMETER,RULE_CODE,STATUS) VALUES(?,?,?,?,?,?,?,?,?,'A')");
		pstmt.setString(1, fraudcode);
		pstmt.setString(2, frauddesc);
		pstmt.setString(3, Customersms);
		pstmt.setString(4, Customeremail);
		pstmt.setString(5, contcentermailid.trim());
		pstmt.setString(6, decisions);
		pstmt.setString(7, ruledesc);
		pstmt.setString(8, rulecode);
		pstmt.setString(9, ruletype);
		pstmt.executeUpdate();
		
		
		pstmt.close();
		System.out.println(rulecode);
		String str[]=rulecode.split(",");
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]);
			if(str[i].indexOf("AMOUNT")>=0){
				amount=str[i].split(":")[1];
			}
			if(str[i].indexOf("DAYS")>=0){
				day=str[i].split(":")[1];
			}
			if(str[i].indexOf("HOUR")>=0){
				hour=str[i].split(":")[1];
			}
			if(str[i].indexOf("ACCOUNTNO")>=0){
				acctno=str[i].split(":")[1];
			}
			if(str[i].indexOf("MOBILE_NUMBER")>=0){
				mobileno=str[i].split(":")[1];
			}
			if(str[i].indexOf("USING")>=0){
				using=str[i].split(":")[1];
			}
			if(str[i].indexOf("CHANNEL")>=0){
				channel=str[i].split(":")[1];
			}
			if(str[i].indexOf("USERID")>=0){
				userid=str[i].split(":")[1];
			}
		}
		
		
		pstmt = connection.prepareStatement("INSERT INTO FRAUD_PARAMETER(FRAUD_ID,RULE_CODE,F_AMOUNT,F_DAYS,F_HOUR,F_ACCOUNTNO,F_MOBILE_NUMBER,F_USERID,F_USING,F_CHANNEL) VALUES(?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, fraudcode);
		pstmt.setString(2, ruletype);
		pstmt.setInt(3, Integer.parseInt(amount));
		pstmt.setInt(4, Integer.parseInt(day));
		pstmt.setInt(5, Integer.parseInt(hour));
		pstmt.setInt(6, Integer.parseInt(acctno));
		pstmt.setInt(7, Integer.parseInt(mobileno));
		pstmt.setString(8, userid);
		pstmt.setString(9, using);
		pstmt.setString(10, channel);
		pstmt.executeUpdate();
		
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
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

public ResponseDTO ParameterCreationauthvw(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	
	logger.debug("inside [AgentDAO][CustomerQueriesView]");

	
	
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
		
		logger.debug("[AgentDAO][CustomerQueriesView] Inside AgentDAO  "+ requestJSON.getString("refno"));

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		viewDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		 JSONObject json = new JSONObject();
	
			 String viewReport="SELECT FRAUD_ID,FRAUD_DESC,RES_CUSTOMER_SMS,RES_CUSTOMER_EMAIL,CALLCENTERMAILIDS,DECISION,RULE_DESC FROM FRAUD_MASTER WHERE FRAUD_ID=?";
				
				viewDataPstmt = connection.prepareStatement(viewReport);
				viewDataPstmt.setString(1,requestJSON.getString("refno"));
				viewRS = viewDataPstmt.executeQuery();
				
				
				 while(viewRS.next())
					{
					 json=new JSONObject();
					 json.put("fraudcode", viewRS.getString(1));
					 json.put("frauddesc", viewRS.getString(2));
					 json.put("Customersms", viewRS.getString(3));
					 json.put("Customeremail", viewRS.getString(4));
					 json.put("contcentermailid", viewRS.getString(5));
					 json.put("decisions", viewRS.getString(6));
					 json.put("ruledesc", viewRS.getString(7));
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


public ResponseDTO fraudAssign(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][ChannelManagement]");

	logger.debug("Inside  ChannelManagement.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		

		
		String userReport =" select PRODUCT_CODE,FRAUD_DESC,MAKER_ID,MAKER_DT FROM FRAUD_ASSIGN ORDER BY PRODUCT_CODE desc";
			

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("PRODUCT_CODE",serviceRS.getString(1));
			 json.put("FRAUD_DESC",serviceRS.getString(2));
			 json.put("MAKER_ID",serviceRS.getString(3));
			 json.put("MAKER_DT",serviceRS.getString(4));;
			 IncomeMTFilesJSONArray.add(json);
			}
			

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in ChannelManagement [" + e.getMessage()
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

public ResponseDTO fraudMapping(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][ChannelMapping]");

	
	
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
		

		 
		
			
			String serviceQry = "select FRAUD_ID,FRAUD_DESC from FRAUD_MASTER  ORDER BY FRAUD_ID";
			servicePstmt = connection.prepareStatement(serviceQry);
			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
			}
			json.put("SERVICE_MASTER", jsonlmt);
			jsonlmt.clear();
				
			String productQry = "select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT WHERE STATUS='A' ORDER BY PRD_CODE";
			servicePstmt = connection.prepareStatement(productQry);
			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
					jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
			}
			json.put("PRODUCT_TEMP", jsonlmt);
			jsonlmt.clear();
			
					
		 viewDataMap.put("VewDetails", json);
		 responseDTO.setData(viewDataMap);
		
       } catch (Exception e) {

		logger.debug("[AgentDAO][ChannelMapping] SQLException in ChannelMapping [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][ChannelMapping] Internal Error Occured While Executing.");
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


public ResponseDTO fraudMappingAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	String channel=null;
	String services=null;
	String limit=null;
	String fee=null;
	String loyality=null;
	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String productcode=requestJSON.getString("productcode");
		String fruadrules=requestJSON.getString("fruadrules");
		String application=requestJSON.getString("application");
		String remoteip=requestJSON.getString("remoteip");
		
		
		servicePstmt = connection.prepareStatement("DELETE FROM  FRAUD_ASSIGN WHERE PRODUCT_CODE=?");
		servicePstmt.setString(1, productcode);
		servicePstmt.executeUpdate();
		servicePstmt.close();
		String branchQry="insert into FRAUD_ASSIGN(PRODUCT_CODE,FRAUD_ID,FRAUD_DESC,MAKER_DT,MAKER_ID) " +
				" values(?,?,?,sysdate,?) ";

		servicePstmt=connection.prepareStatement(branchQry);
	
		String[] str=fruadrules.split(",");
		for(int i = 0; i < str.length; i++){
			servicePstmt.setString(1,productcode);
			servicePstmt.setString(2,str[i].split("=")[0]);
			servicePstmt.setString(3, str[i].split("=")[1]);
			servicePstmt.setString(4,maker_id);
			servicePstmt.addBatch();
		}

		/*JSONArray branchArr =  requestDTO.getRequestJSON().getJSONArray("FINAL_JSON");
		

		for (int i = 0; i < branchArr.size(); i++) {

			JSONObject reqData = branchArr.getJSONObject(i);

			channel = reqData.getString("Channel");
			services = reqData.getString("Services");
			limit = reqData.getString("Limit");
			fee = reqData.getString("Fee");
			loyality = reqData.getString("Loyality");


			servicePstmt.setString(1,channel);
			servicePstmt.setString(2,services.split("-")[0]);
			servicePstmt.setString(3, services.split("-")[1]);
			servicePstmt.setString(4,limit);
			servicePstmt.setString(5,fee);
			servicePstmt.setString(6,maker_id);
			servicePstmt.setString(7,productcode);
			servicePstmt.setString(8,application);
			servicePstmt.setString(9,loyality);
			
			
			servicePstmt.addBatch();
			

		}*/

		servicePstmt.executeBatch(); // insert remaining records 
		servicePstmt.close();
		
		JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "channelmanagement");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: Service Mapping for product id "+productcode);
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

public ResponseDTO registrationfaildetails(RequestDTO requestDTO) {

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
	String qrey1 = "";
	String application="";
	boolean a=false;



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
		
		String customerId=requestJSON.getString("customerId");
		String srchcriteria=requestJSON.getString("srchcriteria");
		String userid="";
		String mobileno="";
		
		
			if(srchcriteria.equalsIgnoreCase("ACCT_NO")){
				qrey = "select USER_ID,ACT_NUMBER,CUST_MOB,CUSTID,CHANNEL,TRANS_DTTM,BANK_PROFILE_STATUS from USER_REG_FAIL_DATA where ACT_NUMBER='"+customerId+"' order by TRANS_DTTM";
	
			}
			
			if(srchcriteria.equalsIgnoreCase("MOBILE_NUMBER")){
				qrey = "select USER_ID,ACT_NUMBER,CUST_MOB,CUSTID,CHANNEL,TRANS_DTTM,BANK_PROFILE_STATUS from USER_REG_FAIL_DATA where CUST_MOB='"+customerId+"' order by TRANS_DTTM";
	
			}
			
			if(srchcriteria.equalsIgnoreCase("CUSTOMER_CODE")){
				qrey = "select USER_ID,ACT_NUMBER,CUST_MOB,CUSTID,CHANNEL,TRANS_DTTM,BANK_PROFILE_STATUS from USER_REG_FAIL_DATA where CUSTID='"+customerId+"' order by TRANS_DTTM";
	
			}
	
		
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("USER_ID", getlmtfeeRs.getString(1));
				userid=getlmtfeeRs.getString(1);
				json.put("ACT_NUMBER", getlmtfeeRs.getString(2));
				json.put("CUST_MOB", getlmtfeeRs.getString(3));
				mobileno=getlmtfeeRs.getString(3);
				if(mobileno.startsWith("0")){
					mobileno="234"+mobileno.substring(1);
				}
				json.put("CUSTID", getlmtfeeRs.getString(4));
				json.put("CHANNEL", getlmtfeeRs.getString(5));
				json.put("TRANS_DTTM", getlmtfeeRs.getString(6));
				json.put("BANK_PROFILE_STATUS", getlmtfeeRs.getString(7));
				
				lmtJsonArray.add(json);
				a=true;
			}
			
			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			resultJson.put("ERROR_DESC", " ");
			if(a){
				//qrey1 = "select distinct DETAIL_3 FROM AUDIT_DATA where upper(NET_ID) in (upper('"+customerId+"'),upper('"+userid+"')) or DETAIL_1='"+mobileno+"' and transcode='CUSTREG' and rownum=1";

			qrey1 = "select distinct DETAIL_3 FROM AUDIT_DATA where  DETAIL_1='"+mobileno+"' and transcode='CUSTREG' and rownum=1";
		
			
			getlmtfeePstmt = connection.prepareStatement(qrey1);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();
			resultJson.put("ERROR_DESC", "Error From Services ");	
			if (getlmtfeeRs.next()) {
				resultJson.put("ERROR_DESC", getlmtfeeRs.getString(1));	
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


public ResponseDTO xpxdetails(RequestDTO requestDTO) {

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
	String qrey1 = "";
	String application="";
	boolean a=false;



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
		
		String customerId=requestJSON.getString("customerId");
		String srchcriteria=requestJSON.getString("srchcriteria");
		String userid="";
		String mobileno="";
		
		
			if(srchcriteria.equalsIgnoreCase("ACCT_NO")){
				qrey = "select MCI.MOBILE_NUMBER from MOB_ACCT_DATA MAD,MOB_CONTACT_INFO MCI where MAD.CUST_ID=MCI.CUST_ID AND ACCT_NO='"+customerId+"' AND PRIM_FLAG='P'";
	
			}
			
			if(srchcriteria.equalsIgnoreCase("MOBILE_NUMBER")){
				qrey = "select MOBILE_NUMBER from MOB_CONTACT_INFO where MOBILE_NUMBER='"+customerId+"'";
	
			}
			
			if(srchcriteria.equalsIgnoreCase("CUSTOMER_CODE")){
				
				qrey = "select MCI.MOBILE_NUMBER from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCI where MCM.ID=MCI.CUST_ID AND CUSTOMER_CODE='"+customerId+"'";
	
			}
	
		
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				mobileno=(getlmtfeeRs.getString(1)).replace("234", "");
				
			}
			
			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			qrey="select MCM.ID,NVL(MCM.USER_ID,' '),MAD.ACCT_NO,MCI.MOBILE_NUMBER,MCM.CUSTOMER_CODE,NVL(MCM.AUTHID,' '),MCM.DATE_CREATED from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCI,MOB_ACCT_DATA MAD where MCM.ID=MCI.CUST_ID AND MCI.CUST_ID=MAD.CUST_ID AND MAD.PRIM_FLAG='P' AND MCI.MOBILE_NUMBER like '%"+mobileno+"'";
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("USER_ID", getlmtfeeRs.getString(2));
				json.put("ACT_NUMBER", getlmtfeeRs.getString(3));
				json.put("CUST_MOB", getlmtfeeRs.getString(4));
				json.put("CUSTID", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("TRANS_DTTM", getlmtfeeRs.getString(7));
				json.put("ID", getlmtfeeRs.getString(1));
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

public ResponseDTO rassinformation(RequestDTO requestDTO) {

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
	String qrey1 = "";
	String application="";



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
		
		String customerId=requestJSON.getString("customerId");
		String srchcriteria=requestJSON.getString("srchcriteria");
		String rassdt[]=customerId.split(",");
		
		StringBuilder sb=new StringBuilder();
		/*sb.append("(");
		int j=0;
		for(int i=0;i<rassdt.length;i++){
			
			sb.append(" SERVICE_ID like '%"+rassdt[i]+"%' ");
			j++;
			//System.out.println(j+" --- "+rassdt.length);
			if(j!=rassdt.length){
				sb.append(" or ");
			}
		}
		sb.append(" ) and TXN_STIME is not null ");*/
		
		
		
		int j=0;
		for(int i=0;i<rassdt.length;i++){
			sb.append(" '"+rassdt[i]+"' ");
			j++;
			if(j!=rassdt.length){
				sb.append(",");
			}
		}
	
		//System.out.println("select TXN_STIME,TXN_ETIME,TRUNUPTIME,BATCHID,PAYMENTREFERENCE,SERVICE_ID,ACCOUNTNO from fund_transfer_tbl where "+sb.toString());
		
		
				qrey = "select TXN_STIME,TXN_ETIME,TRUNUPTIME,BATCHID,PAYMENTREFERENCE,SERVICE_ID,ACCOUNTNO from fund_transfer_tbl where PAYMENTREFERENCE in (select  TRANSREFNO from RESER_FUND_TRANFER where RAASTXNREF in ("+sb.toString()+"))";
	String headval="STIME,ETIME,TRUNUPTIME,BATCH ID,PAYMENTREFERENCE,SERVICE ID,ACCOUNT NO";
		
				ExcelReportGeneration.perform("Rass_timeout_information", connection, qrey,headval);
		
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("TXN_STIME", getlmtfeeRs.getString(1));
				json.put("TXN_ETIME", getlmtfeeRs.getString(2));
				json.put("TRUNUPTIME", getlmtfeeRs.getString(3));
				json.put("BATCHID", getlmtfeeRs.getString(4));
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(5));
				json.put("SERVICE_ID", getlmtfeeRs.getString(6));
				json.put("ACCOUNTNO", getlmtfeeRs.getString(7));
				
				lmtJsonArray.add(json);
			}
			
			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			
		
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


public ResponseDTO segmentAck(RequestDTO requestDTO) {


	Connection connection = null;
	HashMap<String, Object> branchMap = new HashMap<String, Object>();


	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	

	try {

		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();

		String maker_id=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		String remoteip=requestJSON.getString("remoteip");
		
		String segments=requestJSON.getString("segments");
		String subsegments=requestJSON.getString("subsegments");
		String colors=requestJSON.getString("colors");
		String services=requestJSON.getString("services");
		String campaigndis=requestJSON.getString("campaigndis");
		String lifestyle=requestJSON.getString("lifestyle");
		String dvPreviews=requestJSON.getString("dvPreviews");
		String tonev=requestJSON.getString("tonev");
		String faqs=requestJSON.getString("faqs");
		String rms=requestJSON.getString("rms");
		
		
		String branchQry="insert into SEGMENT(SEGMENTS,SUBSEGMENTS,COLORS,SERVICES,CAMPAIGNDIS,LIFESTYLE,DVPREVIEWS,TONEV,FAQS,RMS) " +
				" values(?,?,?,?,?,?,?,?,?,?) ";

		servicePstmt=connection.prepareStatement(branchQry);
	
		
			servicePstmt.setString(1,segments);
			servicePstmt.setString(2,subsegments);
			servicePstmt.setString(3, colors);
			servicePstmt.setString(4,services);
			servicePstmt.setString(5,campaigndis);
			servicePstmt.setString(6,lifestyle);
			servicePstmt.setString(7,dvPreviews);
			servicePstmt.setString(8, tonev);
			servicePstmt.setString(9,faqs);
			servicePstmt.setString(10,rms);
			servicePstmt.execute();
		
		

		
		
		/*JSONObject json = new JSONObject();
		json.put(CevaCommonConstants.MAKER_ID, maker_id);
		json.put("transCode", "channelmanagement");
		json.put("channel", "WEB");
		json.put("message", "Acknowledgment :: Service Mapping for product id "+productcode);
		json.put("ip", remoteip);
		json.put("det1", "");
		json.put("det2", "");
		json.put("det3", "");
		
		CommonServiceDao csd=new CommonServiceDao();
		csd.auditTrailInsert(json);*/
		
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


public ResponseDTO agentrequestdetails(RequestDTO requestDTO) {

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
	String application="";



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
		
		String mnumber=requestJSON.getString("mnumber");
		application=requestJSON.getString("application");
		
		/*application=requestJSON.getString("application");
	
		if(application.equalsIgnoreCase("REFUND_REQUEST")){
			
		
			qrey="select AFT.PAYMENTREFERENCE,AFT.TRNS_AMT,AFT.CHANNEL,AFT.USERID,AFT.TRANS_TYPE,AFT.TRANS_DATE,AFT.DEBITNARRATION,AFT.BATCHID,WAD.ACCT_NO from AGENT_FUND_TRANSFER_TBL AFT,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD  where AFT.USERID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID and not exists (select 1 from WALLET_FIN_TXN WFT where  WFT.EXT_TXN_REF_NO=AFT.PAYMENTREFERENCE) AND AFT.RESPONSECODE='00' AND TRANS_TYPE='AGENTTOPUP' AND AFT.USERID='"+mnumber+"' and not exists (select 1 from UNIONCEVA.AGENT_FUND_TRANSFER_TBL WFT1 where  WFT1.PAYMENTREFERENCE='R'||AFT.PAYMENTREFERENCE) ";   
		}
		
		if(application.equalsIgnoreCase("PAYBILL_REVERSAL_REQUEST")){
			
			
			qrey="select WFT.TXN_REF_NO, WFT.TXN_AMT, WFT.CHANNEL, WFT.USER_ID, WFT.SERVICECODE, WFT.TXN_STAMP, WFT.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD "
					+"  where WFT.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND WFT.USER_ID='"+mnumber+"' AND WFT.SERVICECODE='WALPAYBILLAGN' AND NOT EXISTS "
					+"  ( SELECT 1 "
					+"  FROM AGENT_WALLET_PB_TRANS_TBL AFT "
					+"  WHERE AFT.FTID=WFT.EXT_TXN_REF_NO "
					+" and  AFT.PB_RESP_CODE='00' "
					+"  ) "
					+"  and not exists "
					+"  ( select 1 from WALLET_FIN_TXN WFT1 where WFT1.SERVICECODE='WALPAYBILLAGN' and 'R'||WFT1.txn_ref_no=WFT.txn_ref_no ) "
					+"  minus "
					+"  ( select WFT4.TXN_REF_NO, WFT4.TXN_AMT, WFT4.CHANNEL, WFT4.USER_ID, WFT4.SERVICECODE, WFT4.TXN_STAMP, WFT4.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT4,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD "
					+"  where WFT4.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND  WFT4.SERVICECODE='WALPAYBILLAGN'and WFT4.txn_Ref_no in ( "
					+"  select substr(WFT2.txn_Ref_no,2) from WALLET_FIN_TXN WFT2 where WFT2.USER_ID='"+mnumber+"' and wft2.SERVICECODE='WALPAYBILLAGN'  "
					+"  and  exists ( select 1 from WALLET_FIN_TXN WFT3 where wft3.SERVICECODE='WALPAYBILLAGN' and 'R'||WFT3.txn_ref_no=WFT2.txn_ref_no ))) ";   
		}
		
		if(application.equalsIgnoreCase("OTHERBANK_REVERSAL_REQUEST")){
			
			
			qrey=" select WFT.TXN_REF_NO, WFT.TXN_AMT, WFT.CHANNEL, WFT.USER_ID, WFT.SERVICECODE, WFT.TXN_STAMP, WFT.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD "
					+" WHERE WFT.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND WFT.USER_ID='"+mnumber+"'  and WFT.SERVICECODE='WALAGNOTBANK' AND NOT EXISTS "
					+"   ( SELECT 1 "
					+"   FROM AGENT_WALLET_FTO_TRANS_TBL AFT "
					+"   WHERE AFT.FTID=WFT.EXT_TXN_REF_NO "
					+"  and  AFT.FTO_RESP_CODE='00' "
					+"   ) "
					+"   and not exists "
					+"   ( select 1 from WALLET_FIN_TXN WFT1 where WFT1.SERVICECODE='WALAGNOTBANK' and 'R'||WFT1.txn_ref_no=WFT.txn_ref_no ) "
					+"   minus "
					+"   ( select WFT4.TXN_REF_NO, WFT4.TXN_AMT, WFT4.CHANNEL, WFT4.USER_ID, WFT4.SERVICECODE, WFT4.TXN_STAMP, WFT4.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT4,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD where WFT4.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND WFT4.SERVICECODE='WALAGNOTBANK'and WFT4.txn_Ref_no in ( " 
					+"   select substr(WFT2.txn_Ref_no,2) from WALLET_FIN_TXN WFT2 where WFT2.USER_ID='"+mnumber+"'  and  wft2.SERVICECODE='WALAGNOTBANK' " 
					+"   and  exists ( select 1 from WALLET_FIN_TXN WFT3 where wft3.SERVICECODE='WALAGNOTBANK' and 'R'||WFT3.txn_ref_no=WFT2.txn_ref_no ))) ";   
		}

		if(application.equalsIgnoreCase("OWNBANK_REVERSAL_REQUEST")){
	
	
			qrey=" select WFT.TXN_REF_NO, WFT.TXN_AMT, WFT.CHANNEL, WFT.USER_ID, WFT.SERVICECODE, WFT.TXN_STAMP, WFT.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD "
					+" WHERE WFT.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND WFT.USER_ID='"+mnumber+"'  and WFT.SERVICECODE='WALAGNOWNBANK' AND NOT EXISTS "
					+"   ( SELECT 1 "
					+"   FROM AGENT_WALLET_TRANS_TBL AFT "
					+"   WHERE AFT.FTID=WFT.EXT_TXN_REF_NO "
					+"  and  AFT.FTO_RESP_CODE='00' "
					+"   ) "
					+"   and not exists "
					+"   ( select 1 from WALLET_FIN_TXN WFT1 where WFT1.SERVICECODE='WALAGNOWNBANK' and 'R'||WFT1.txn_ref_no=WFT.txn_ref_no )  "
					+"   minus "
					+"   ( select WFT4.TXN_REF_NO, WFT4.TXN_AMT, WFT4.CHANNEL, WFT4.USER_ID, WFT4.SERVICECODE, WFT4.TXN_STAMP, WFT4.DR_NARRATION,'',WAD.ACCT_NO from WALLET_FIN_TXN WFT4,AGENT_CONTACT_INFO ACI,WALLET_ACCT_DATA WAD where WFT4.USER_ID=ACI.MOBILE_NUMBER AND WAD.CUST_ID=ACI.CUST_ID AND WFT4.SERVICECODE='WALAGNOWNBANK'and WFT4.txn_Ref_no in (  "
					+"   select substr(WFT2.txn_Ref_no,2) from WALLET_FIN_TXN WFT2 where WFT2.USER_ID='"+mnumber+"'  and  wft2.SERVICECODE='WALAGNOWNBANK'  "
					+"   and  exists ( select 1 from WALLET_FIN_TXN WFT3 where wft3.SERVICECODE='WALAGNOWNBANK' and 'R'||WFT3.txn_ref_no=WFT2.txn_ref_no ))) ";   
		}*/
		
		if(application.equalsIgnoreCase("SUCCESS_REV")) {
			
		
		
		qrey="select * from ("+
				"(select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,TO_CHAR(NVL(AWFT.AMOUNT,0)),AWFT.CHANNEL,decode(AWFT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWFT.TRANS_DATE_TIME  as tdate,'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
				"from AGENT_WALLET_FTO_TRANS_TBL AWFT,WALLET_FIN_TXN WFT where  AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO AND AWFT.FTBATCHID='"+mnumber+"' AND substr(TXN_REF_NO,1,1)!='R' AND AWFT.FTO_RESP_CODE='00' ) " + 
				"union all  " + 
				"(select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
				"from TBL_TRANLOG_ALL TTA ,WALLET_FIN_TXN WFT where  TTA.BANKRRN=WFT.ext_txn_ref_no AND TTA.BANKRRN='"+mnumber+"' and substr(TXN_REF_NO,1,1)!='R' AND TTA.BANKRESPONSECODE='00' ) " + 
				"union all  " + 
				"(select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWPT.TRANS_DATE_TIME  as tdate,DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
				"from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT WHERE   AWPT.FTBATCHID=WFT.ext_txn_ref_no AND AWPT.FTBATCHID='"+mnumber+"' AND substr(TXN_REF_NO,1,1)!='R' and AWPT.PB_RESP_CODE='00') " + 
				") order by tdate desc";
		
		}
		
		if(application.equalsIgnoreCase("REV_REV")) {
			
			
			
			qrey="select * from ("+
					"(select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,TO_CHAR(NVL(AWFT.AMOUNT,0)),AWFT.CHANNEL,decode(AWFT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWFT.TRANS_DATE_TIME  as tdate,'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
					"from AGENT_WALLET_FTO_TRANS_TBL AWFT,WALLET_FIN_TXN WFT where  AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO AND AWFT.FTBATCHID='"+mnumber+"' AND substr(TXN_REF_NO,1,1)='R'  ) " + 
					"union all  " + 
					"(select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,' ',TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),decode(TTA.BANKRESPONSECODE,'00','Success','09','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TTA.TXNDATE  as tdate,DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
					"from TBL_TRANLOG_ALL TTA ,WALLET_FIN_TXN WFT where  TTA.BANKRRN=WFT.ext_txn_ref_no AND TTA.BANKRRN='"+mnumber+"' and substr(TXN_REF_NO,1,1)='R'  ) " + 
					"union all  " + 
					"(select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),AWPT.TRANS_DATE_TIME  as tdate,DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0))   " + 
					"from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT WHERE   AWPT.FTBATCHID=WFT.ext_txn_ref_no AND AWPT.FTBATCHID='"+mnumber+"' AND substr(TXN_REF_NO,1,1)='R' ) " + 
					") order by tdate desc";
			
			
			
			}
		
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("TXN_REF_NO", getlmtfeeRs.getString(1));
				json.put("USER_ID", getlmtfeeRs.getString(2));
				json.put("DR_ACCOUNT", getlmtfeeRs.getString(3));
				json.put("CR_ACCOUNT", getlmtfeeRs.getString(4));
				json.put("AMOUNT", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("STATUS", getlmtfeeRs.getString(7));
				json.put("TXN_DTTM", getlmtfeeRs.getString(8));
				json.put("SERVICECODE", getlmtfeeRs.getString(10));
				json.put("TXN_AMT", getlmtfeeRs.getString(11));
				json.put("FEE_AMT", getlmtfeeRs.getString(12));
				
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
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

public ResponseDTO agentrequestinfo(RequestDTO requestDTO) {

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
	String application="";



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
		String requesttype=requestJSON.getString("requesttype");
		
		StringBuilder sb=new StringBuilder();
		
		
		
		if(requesttype.equalsIgnoreCase("Fund Transfer to Other Bank")) {
			qrey="select WFT.EXT_TXN_REF_NO,WFT.USER_ID,AWFT.FROM_ACCOUNT,WFT.TXN_REF_NO,AWFT.AMOUNT,WFT.CHANNEL,to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),LTRIM(WFT.TXN_AMT,'0'),WFT.FEE_AMT,(Select BANK_NAME from BANKS_DATA where NIBSSCODE=AWFT.TO_BRANCH_CODE),FTO_REQUEST,WFT.SERVICECODE,AWFT.FTO_RESP_CODE,AWFT.FTO_RESP_DESC from WALLET_FIN_TXN WFT,AGENT_WALLET_FTO_TRANS_TBL AWFT " + 
					" where   WFT.EXT_TXN_REF_NO=AWFT.FTBATCHID AND EXT_TXN_REF_NO='"+refno+"' and substr(TXN_REF_NO,1,1)!='R'";
		
		
		
	
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("USERID", getlmtfeeRs.getString(2));
				json.put("FROM_ACCOUNT", getlmtfeeRs.getString(3));
				json.put("TO_ACCOUNT", getlmtfeeRs.getString(4));
				json.put("TRNS_AMT", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("TRANS_DATE", getlmtfeeRs.getString(7));
				json.put("TXN_AMT", getlmtfeeRs.getString(8));
				json.put("FEE_AMT", getlmtfeeRs.getString(9));
				
				json.put("FTO_RESP_CODE", getlmtfeeRs.getString(13)+" - "+getlmtfeeRs.getString(14));
				json.put("THIRDPT", "Fund Transfer to Other Bank Response");
				
				sb.append("Fund Transfer to Other Bank Response : "+getlmtfeeRs.getString(13)+" - "+getlmtfeeRs.getString(14)+"#");
				
				json.put("BANK_NAME1", "Beneficiary Bank Name");
				json.put("BANK_NAME", getlmtfeeRs.getString(10));
				
				sb.append("Beneficiary Bank Name : "+getlmtfeeRs.getString(10)+"#");
				
				json.put("WAL_RESP", "Wallet Account Successfully Debited");
				sb.append("Wallet Response : Wallet Account Successfully Debited "+"#");
				json.put("SERVICECODE", getlmtfeeRs.getString(12));
				JSONObject jobj=JSONObject.fromObject(getlmtfeeRs.getString(11));
				if(jobj.containsKey("beneficiaryAccountName")) {
					json.put("BEN_NAME1", "Beneficiary Account Name");	
					json.put("BEN_NAME", jobj.getString("beneficiaryAccountName"));	
					
					sb.append("Beneficiary Account Name : "+jobj.getString("beneficiaryAccountName")+"#");
				}
				if(jobj.containsKey("BeneficiaryAccountNumber")) {
					json.put("BEN_ACCT1", "Beneficiary Account Number");
					json.put("BEN_ACCT", jobj.getString("BeneficiaryAccountNumber"));	
					
					sb.append("Beneficiary Account Number : "+jobj.getString("BeneficiaryAccountNumber")+"#");
				}
				json.put("FULLDET", sb.toString());
				
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		}
		
		if(requesttype.equalsIgnoreCase("Pay Bill")) {

			qrey="select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,WFT.TXN_REF_NO,TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),WFT.SERVICECODE,AWPT.PB_REQUEST,AWPT.PB_RESP_CODE,AWPT.PB_RESP_DESC  " + 
					" from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT WHERE  AWPT.FTBATCHID=WFT.ext_txn_ref_no AND AWPT.FTBATCHID='"+refno+"' AND substr(TXN_REF_NO,1,1)!='R' ";
		
		
		
	
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("USERID", getlmtfeeRs.getString(2));
				json.put("FROM_ACCOUNT", getlmtfeeRs.getString(3));
				json.put("TO_ACCOUNT", getlmtfeeRs.getString(4));
				json.put("TRNS_AMT", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("TRANS_DATE", getlmtfeeRs.getString(7));
				json.put("TXN_AMT", getlmtfeeRs.getString(8));
				json.put("FEE_AMT", getlmtfeeRs.getString(9));
				
				json.put("FTO_RESP_CODE", getlmtfeeRs.getString(12)+" - "+getlmtfeeRs.getString(13));
				json.put("THIRDPT", "Pay Bill Response");
				
				sb.append("Pay Bill Response : "+getlmtfeeRs.getString(12)+" - "+getlmtfeeRs.getString(13)+"#");

				
				json.put("WAL_RESP", "Wallet Account Successfully Debited");
				sb.append("Wallet Response : Wallet Account Successfully Debited "+"#");
				
				json.put("SERVICECODE", getlmtfeeRs.getString(10));
				JSONObject jobj=JSONObject.fromObject(getlmtfeeRs.getString(11));
				if(jobj.containsKey("beneficiaryAccountName")) {
					json.put("BEN_NAME1", "Payment code");	
					json.put("BEN_NAME", jobj.getString("paymentcode"));
					
					sb.append("Payment code : "+jobj.getString("paymentcode")+"#");
				}
				if(jobj.containsKey("BeneficiaryAccountNumber")) {
					json.put("BEN_ACCT1", "Trans desc");
					json.put("BEN_ACCT", jobj.getString("transdesc"));	
					
					sb.append("Trans desc : "+jobj.getString("transdesc")+"#");
				}
				
				if(jobj.containsKey("custmobile")) {
					json.put("BANK_NAME1", "Customer Mobile");
					json.put("BANK_NAME", (jobj.getString("custmobile")).replace("NA", ""));
					
					sb.append("Customer Mobile : "+(jobj.getString("custmobile")).replace("NA", "")+"#");
				}
				json.put("FULLDET", sb.toString());
				
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		}
		
		if(requesttype.equalsIgnoreCase("Recharge")) {

			qrey="select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,WFT.TXN_REF_NO,TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),WFT.SERVICECODE,AWPT.PB_REQUEST,AWPT.PB_RESP_CODE,AWPT.PB_RESP_DESC  " + 
					" from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT WHERE   AWPT.FTBATCHID=WFT.ext_txn_ref_no AND AWPT.FTBATCHID='"+refno+"' AND substr(TXN_REF_NO,1,1)!='R' ";
		
		
		
	
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("USERID", getlmtfeeRs.getString(2));
				json.put("FROM_ACCOUNT", getlmtfeeRs.getString(3));
				json.put("TO_ACCOUNT", getlmtfeeRs.getString(4));
				json.put("TRNS_AMT", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("TRANS_DATE", getlmtfeeRs.getString(7));
				json.put("TXN_AMT", getlmtfeeRs.getString(8));
				json.put("FEE_AMT", getlmtfeeRs.getString(9));
				
				json.put("FTO_RESP_CODE", getlmtfeeRs.getString(12)+" - "+getlmtfeeRs.getString(13));
				json.put("THIRDPT", "Recharge Response");
				
				sb.append("Pay Bill Response : "+getlmtfeeRs.getString(12)+" - "+getlmtfeeRs.getString(13)+"#");

				
				json.put("WAL_RESP", "Wallet Account Successfully Debited");
				sb.append("Wallet Response : Wallet Account Successfully Debited "+"#");
				
				json.put("SERVICECODE", getlmtfeeRs.getString(10));
				JSONObject jobj=JSONObject.fromObject(getlmtfeeRs.getString(11));
				if(jobj.containsKey("beneficiaryAccountName")) {
					json.put("BEN_NAME1", "Payment code");	
					json.put("BEN_NAME", jobj.getString("paymentcode"));
					
					sb.append("Payment code : "+jobj.getString("paymentcode")+"#");
				}
				if(jobj.containsKey("BeneficiaryAccountNumber")) {
					json.put("BEN_ACCT1", "Trans desc");
					json.put("BEN_ACCT", jobj.getString("transdesc"));	
					
					sb.append("Trans desc : "+jobj.getString("transdesc")+"#");
				}
				
				if(jobj.containsKey("custmobile")) {
					json.put("BANK_NAME1", "Customer Mobile");
					json.put("BANK_NAME", (jobj.getString("custmobile")).replace("NA", ""));
					
					sb.append("Customer Mobile : "+(jobj.getString("custmobile")).replace("NA", "")+"#");
				}
				json.put("FULLDET", sb.toString());
				
				
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		}
		if(requesttype.equalsIgnoreCase("Cashwithdrawal Card Other bank") || requesttype.equalsIgnoreCase("Cashwithdrawal Card Union bank") || requesttype.equalsIgnoreCase("Fundtransfer Card Otherbank") || requesttype.equalsIgnoreCase("Fundtransfer Card Union bank")) {

			qrey="select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,WFT.TXN_REF_NO,TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CHANNEL,'POS'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),WFT.SERVICECODE,TTA.BANKRESPONSECODE,NVL(TTA.BANKRESPONSEMSG,' '),DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode  " + 
					" from TBL_TRANLOG_ALL TTA ,WALLET_FIN_TXN WFT where TTA.BANKRRN=WFT.ext_txn_ref_no and substr(TXN_REF_NO,1,1)!='R' AND TTA.BANKRRN='"+refno+"' ";
		
		
		
	
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("USERID", getlmtfeeRs.getString(2));
				json.put("FROM_ACCOUNT", getlmtfeeRs.getString(3));
				json.put("TO_ACCOUNT", getlmtfeeRs.getString(4));
				json.put("TRNS_AMT", getlmtfeeRs.getString(5));
				json.put("CHANNEL", getlmtfeeRs.getString(6));
				json.put("TRANS_DATE", getlmtfeeRs.getString(7));
				json.put("TXN_AMT", getlmtfeeRs.getString(8));
				json.put("FEE_AMT", getlmtfeeRs.getString(9));
				
				json.put("FTO_RESP_CODE", getlmtfeeRs.getString(11)+" - "+getlmtfeeRs.getString(12));
				json.put("THIRDPT", getlmtfeeRs.getString(13));
				
				sb.append(getlmtfeeRs.getString(13)+" : "+getlmtfeeRs.getString(11)+" - "+getlmtfeeRs.getString(12)+"#");

				
				json.put("WAL_RESP", "Wallet Account Successfully Debited");
				sb.append("Wallet Response : Wallet Account Successfully Debited "+"#");
				
				json.put("SERVICECODE", getlmtfeeRs.getString(10));
				
				json.put("BEN_NAME1", "");	
				json.put("BEN_NAME", "");
				json.put("BEN_ACCT1", "");
				json.put("BEN_ACCT", "");
				json.put("FULLDET", sb.toString());
				
				/*JSONObject jobj=JSONObject.fromObject(getlmtfeeRs.getString(11));
				if(jobj.containsKey("beneficiaryAccountName")) {
					json.put("BEN_NAME1", "Payment code");	
					json.put("BEN_NAME", jobj.getString("paymentcode"));
					
					sb.append("Payment code : "+jobj.getString("paymentcode")+"#");
				}
				if(jobj.containsKey("BeneficiaryAccountNumber")) {
					json.put("BEN_ACCT1", "Trans desc");
					json.put("BEN_ACCT", jobj.getString("transdesc"));	
					
					sb.append("Trans desc : "+jobj.getString("transdesc")+"#");
				}
				
				if(jobj.containsKey("custmobile")) {
					json.put("BANK_NAME1", "Customer Mobile");
					json.put("BANK_NAME", (jobj.getString("custmobile")).replace("NA", ""));
					
					sb.append("Customer Mobile : "+(jobj.getString("custmobile")).replace("NA", "")+"#");
				}
				json.put("FULLDET", sb.toString());*/
				
				
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		}
		
		getlmtfeePstmt = connection.prepareStatement("SELECT COUNT(*) FROM WALLET_FIN_TXN WHERE EXT_TXN_REF_NO='"+refno+"' AND SUBSTR(TXN_REF_NO,1,1)='R'");

		getlmtfeeRs = getlmtfeePstmt.executeQuery();

		while (getlmtfeeRs.next()) {
			if(getlmtfeeRs.getInt(1)==0) {
				json.put("WALLET_REV", "NO");
			}else {
				json.put("WALLET_REV", "YES");
			}
			
		}
		
		getlmtfeePstmt.close();
		getlmtfeeRs.close();
		
		getlmtfeePstmt = connection.prepareStatement("SELECT COUNT(*) FROM AGN_POLLER_POSTING WHERE TXN_REF_NO='R"+refno+"'");

		getlmtfeeRs = getlmtfeePstmt.executeQuery();

		while (getlmtfeeRs.next()) {
			
			if(getlmtfeeRs.getInt(1)==0) {
				json.put("POST_REV", "NO");
			}else {
				json.put("POST_REV", "YES");
			}
		}
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO agentrequestinfoAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;

	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String paymentrefno=requestJSON.getString("paymentrefno");
		String txnamt=requestJSON.getString("txnamt");
		String userid=requestJSON.getString("userid");
		String channel=requestJSON.getString("channel");
		String txntype=requestJSON.getString("txntype");
		String txndatetime=requestJSON.getString("txndatetime");
		String Narration=requestJSON.getString("fulldata");
		String batchid=requestJSON.getString("toacct");
		String remarks=requestJSON.getString("remarks");
		String requesttype=requestJSON.getString("requesttype");
		String waccountno=requestJSON.getString("waccountno");
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String actiontype=requestJSON.getString("actiontype");
		String application=requestJSON.getString("application");
		
		
		connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO WALLET_SUCCREV_REQUEST(PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,REQUEST_TYPE,REMARKS,MAKER_ID,MAKER_DT,STATUS,ACCT_NO,BATCHID,DEBITNARRATION,TRANS_DATE,ACTION_TYPE,P_SEQNO,APPLICATION) VALUES(?,?,?,?,?,?,?,?,SYSDATE,'P',?,?,?,?,?,WALLET_SUCCREV_REQUEST_SEQ.nextval,?)");
		pstmt.setString(1, paymentrefno);
		pstmt.setString(2, txnamt);
		pstmt.setString(3, channel);
		pstmt.setString(4, userid);
		pstmt.setString(5, txntype);
		pstmt.setString(6, requesttype);
		pstmt.setString(7, remarks);
		pstmt.setString(8, makerid);
		pstmt.setString(9, waccountno);
		pstmt.setString(10, batchid);
		pstmt.setString(11, Narration);
		pstmt.setString(12, txndatetime);
		pstmt.setString(13, actiontype);
		pstmt.setString(14, application);
		pstmt.executeUpdate();
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
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

public ResponseDTO agentrequestApproval(RequestDTO requestDTO) {

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
	String application="";



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
		
		
		StringBuilder sb=new StringBuilder();
	
		sb.append("SELECT (select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='AGNPAYBILLAIRTIME'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='AGNPAYBILLAIRTIME'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='WALPAYBILLAGN'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='WALPAYBILLAGN'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='WALAGNOTBANK'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='WALAGNOTBANK'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDCSHWTDOTH'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDCSHWTDOTH'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDCSHWTDOWN'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDCSHWTDOWN'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDFTXNOTH'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDFTXNOTH'),");
		
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDFTXNOWN'),");
		sb.append("(select count(*) from WALLET_SUCCREV_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDFTXNOWN')  FROM DUAL");
		
		
		
		/*sb.append("(select count(*) from WALLET_AGENT_REQUEST where STATUS='P' AND REQUEST_TYPE='OWNBANK_REVERSAL_REQUEST'),");
		sb.append("(select count(*) from WALLET_AGENT_REQUEST where STATUS='C' AND REQUEST_TYPE='OWNBANK_REVERSAL_REQUEST') FROM DUAL");*/
			 
			
			getlmtfeePstmt = connection.prepareStatement(sb.toString());

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("AGNPAYBILLAIRTIME_PENDING", getlmtfeeRs.getString(1));
				json.put("AGNPAYBILLAIRTIME_COMPLETED", getlmtfeeRs.getString(2));
				json.put("WALPAYBILLAGN_PENDING", getlmtfeeRs.getString(3));
				json.put("WALPAYBILLAGN_COMPLETED", getlmtfeeRs.getString(4));
				json.put("WALAGNOTBANK_PENDING", getlmtfeeRs.getString(5));
				json.put("WALAGNOTBANK_COMPLETED", getlmtfeeRs.getString(6));
				
				json.put("AGNCRDCSHWTDOTH_PENDING", getlmtfeeRs.getString(7));
				json.put("AGNCRDCSHWTDOTH_COMPLETED", getlmtfeeRs.getString(8));
				
				json.put("AGNCRDCSHWTDOWN_PENDING", getlmtfeeRs.getString(9));
				json.put("AGNCRDCSHWTDOWN_COMPLETED", getlmtfeeRs.getString(10));
				
				json.put("AGNCRDFTXNOTH_PENDING", getlmtfeeRs.getString(11));
				json.put("AGNCRDFTXNOTH_COMPLETED", getlmtfeeRs.getString(12));
				
				json.put("AGNCRDFTXNOWN_PENDING", getlmtfeeRs.getString(13));
				json.put("AGNCRDFTXNOWN_COMPLETED", getlmtfeeRs.getString(14));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO agentrequestapprovaldetails(RequestDTO requestDTO) {

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
	String application="";



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
		
		application=requestJSON.getString("application");
		
		String txntype=application.split("_")[0];
		application=application.split("_")[1];
	
		if(application.equalsIgnoreCase("PENDING")){
			
			qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,REQUEST_TYPE,TRANS_DATE,MAKER_ID,MAKER_DT,ACCT_NO,ACTION_TYPE,P_SEQNO from WALLET_SUCCREV_REQUEST  where  STATUS='P' AND TXN_TYPE='"+txntype+"'";   

		}else if(application.equalsIgnoreCase("COMPLETED")){
			qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,REQUEST_TYPE,TRANS_DATE,MAKER_ID,MAKER_DT,ACCT_NO,ACTION_TYPE,P_SEQNO from WALLET_SUCCREV_REQUEST  where  STATUS='C' AND TXN_TYPE='"+txntype+"'";   

		}
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("TRNS_AMT", getlmtfeeRs.getString(2));
				json.put("CHANNEL", getlmtfeeRs.getString(3));
				json.put("USERID", getlmtfeeRs.getString(4));
				json.put("TRANS_TYPE", getlmtfeeRs.getString(5));
				json.put("TRANS_DATE", getlmtfeeRs.getString(6));
				json.put("MAKER_ID", getlmtfeeRs.getString(7));
				json.put("MAKER_DT", getlmtfeeRs.getString(8));
				json.put("ACCT_NO", getlmtfeeRs.getString(9));
				json.put("ACTION_TYPE", getlmtfeeRs.getString(10));
				json.put("P_SEQNO", getlmtfeeRs.getString(11));
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
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

public ResponseDTO agentrequestapprovalinfo(RequestDTO requestDTO) {

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
	String application="";



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
		application=requestJSON.getString("application");
		
		String txntype=application.split("_")[0];
		application=application.split("_")[1];
		
		if(application.equalsIgnoreCase("PENDING")){
			qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,TRANS_DATE,MAKER_ID,MAKER_DT,REMARKS,ACTION_TYPE,REQUEST_TYPE,BATCHID,ACCT_NO,DEBITNARRATION,P_SEQNO,NVL(APPLICATION,'SUCCESS_REV') from WALLET_SUCCREV_REQUEST  where STATUS='P' AND TXN_TYPE='"+txntype+"' AND P_SEQNO='"+refno+"'";   

		}else if(application.equalsIgnoreCase("COMPLETED")){
			qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,TRANS_DATE,MAKER_ID,MAKER_DT,REMARKS,ACTION_TYPE,REQUEST_TYPE,BATCHID,ACCT_NO,DEBITNARRATION,P_SEQNO,NVL(APPLICATION,'SUCCESS_REV') from WALLET_SUCCREV_REQUEST  where STATUS='C' AND TXN_TYPE='"+txntype+"' AND P_SEQNO='"+refno+"'";   

		}
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("TRNS_AMT", getlmtfeeRs.getString(2));
				json.put("CHANNEL", getlmtfeeRs.getString(3));
				json.put("USERID", getlmtfeeRs.getString(4));
				json.put("TRANS_TYPE", getlmtfeeRs.getString(5));
				json.put("TRANS_DATE", getlmtfeeRs.getString(6));
				json.put("MAKER_ID", getlmtfeeRs.getString(7));
				json.put("MAKER_DT", getlmtfeeRs.getString(8));
				json.put("REMARK", getlmtfeeRs.getString(9));
				
				json.put("ACTION_TYPE", getlmtfeeRs.getString(10));
				json.put("REQUEST_TYPE", getlmtfeeRs.getString(11));
				json.put("WALLETNO", getlmtfeeRs.getString(12));
				json.put("ACCT_NO", getlmtfeeRs.getString(13));
				json.put("DETAILS", ((getlmtfeeRs.getString(14).replaceAll(":", "<font color=\"red\">"))).replaceAll("#", "</font><br>"));
				json.put("P_SEQNO", getlmtfeeRs.getString(15));
				json.put("REQAPPLICATION", getlmtfeeRs.getString(16));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO agentrequestinfoApprovalAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	CallableStatement cstmt = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;

	org.json.JSONObject jsonob=null;
	String corerequest= null;
	String walletrequest= null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String paymentrefno=requestJSON.getString("paymentrefno");
		String txnamt=requestJSON.getString("txnamt");
		String userid=requestJSON.getString("userid");
		String channel=requestJSON.getString("channel");
		String txntype=requestJSON.getString("txntype");
		String txndatetime=requestJSON.getString("txndatetime");
		String Narration=requestJSON.getString("Narration");
		String batchid=requestJSON.getString("batchid");
		String waccountno=requestJSON.getString("waccountno");
		String application=requestJSON.getString("txntype");
		String actiontype=requestJSON.getString("actiontype");
		String trans=requestJSON.getString("trans");
		
		String refno=requestJSON.getString("refno");
		
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String requesttype=requestJSON.getString("reqapplication");
		System.out.println("requesttype :: "+requesttype);
		System.out.println("application :: "+application);
		System.out.println("batchid :: "+batchid);
		System.out.println("paymentrefno :: "+paymentrefno);
		System.out.println("txntype :: "+trans);
		
		if(requesttype.equalsIgnoreCase("SUCCESS_REV")) {
			

			String insQRY = "{call pReversalTxnVer2(?,?,?,?,?)}";

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, batchid);
			cstmt.setString(2, trans);
			cstmt.setString(3, "U");
			
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeQuery();
System.out.println("Response code :: "+cstmt.getString(5));
System.out.println("Response Message :: "+cstmt.getString(4));
			if ((cstmt.getString(5)).contains("1")) {
				System.out.println("kailash here :: ");
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, "Wallet Response :"+cstmt.getString(4));
				pstmt.setString(3, paymentrefno);
				pstmt.setString(4, refno);

				pstmt.executeUpdate();
				connection.commit();
				responseDTO.addMessages("Success");
			}else{
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='F',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, "Wallet Response :"+cstmt.getString(4));
				pstmt.setString(3, paymentrefno);
				pstmt.setString(4, refno);
				pstmt.executeUpdate();
				connection.commit();
				
				responseDTO.addError("Wallet Response :"+cstmt.getString(4));
			}
		
			
		
		/*if(application.equalsIgnoreCase("Fund Transfer to Other Bank")) {
			corerequest="NIP_CORE_SUCCESS_TRANS_REV";
			walletrequest="NIP_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Pay Bill")) {
			corerequest="PAYBILL_CORE_SUCCESS_TRANS_REV";
			walletrequest="PAYBILL_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Recharge")) {
			corerequest="RECHARGE_CORE_SUCCESS_TRANS_REV";
			walletrequest="RECHARGE_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Cashwithdrawal Card Other bank")) {
			corerequest="AGNCRDCSHWTDOTH_WALLET_SUCCESS_TRANS_REV";
			walletrequest="AGNCRDCSHWTDOTH_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Cashwithdrawal Card Union bank")) {
			corerequest="AGNCRDCSHWTDOWN_WALLET_SUCCESS_TRANS_REV";
			walletrequest="AGNCRDCSHWTDOWN_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Fundtransfer Card Otherbank")) {
			corerequest="AGNCRDFTXNOTH_WALLET_SUCCESS_TRANS_REV";
			walletrequest="AGNCRDFTXNOTH_WALLET_SUCCESS_TRANS_REV";
		}
		
		if(application.equalsIgnoreCase("Fundtransfer Card Union bank")) {
			corerequest="AGNCRDFTXNOWN_WALLET_SUCCESS_TRANS_REV";
			walletrequest="AGNCRDFTXNOWN_WALLET_SUCCESS_TRANS_REV";
		}*/
			
			/*if(actiontype.equalsIgnoreCase("Bank Posting Reversal")) {
				JSONObject json1 = JSONObject.fromObject(SettlementServiceCall.corePostingsRev(corerequest, makerid, paymentrefno));
				if((json1.getString("respcode")).equalsIgnoreCase("00")){
					
					connection.setAutoCommit(false);
					pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, application+" Response :"+json1.getString("respdesc"));
					pstmt.setString(3, paymentrefno);
					pstmt.setString(4, refno);
					
					pstmt.executeUpdate();
					connection.commit();
					
					responseDTO.addMessages("Success");
				}else {
					connection.setAutoCommit(false);
					pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='F',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, application+" Response :"+json1.getString("respdesc"));
					pstmt.setString(3, paymentrefno);
					pstmt.setString(4, refno);
					pstmt.executeUpdate();
					connection.commit();
					
					responseDTO.addError(application+" Response :"+json1.getString("respdesc"));
				}
			}*/
			
			/*if(actiontype.equalsIgnoreCase("Wallet Reversal")) {
				JSONObject json1 = JSONObject.fromObject(SettlementServiceCall.walletReversal(walletrequest, makerid, batchid));
				
				if((json1.getString("respcode")).equalsIgnoreCase("00")){
					System.out.println("kailash here :: ");
					connection.setAutoCommit(false);
					pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, "Wallet Response :"+json1.getString("respdesc"));
					pstmt.setString(3, paymentrefno);
					pstmt.setString(4, refno);

					pstmt.executeUpdate();
					connection.commit();
					
					responseDTO.addMessages("Success");
				}else {
					connection.setAutoCommit(false);
					pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='F',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, "Wallet Response :"+json1.getString("respdesc"));
					pstmt.setString(3, paymentrefno);
					pstmt.setString(4, refno);
					pstmt.executeUpdate();
					connection.commit();
					
					responseDTO.addError("Wallet Response :"+json1.getString("respdesc"));
				}
			}*/
			
			/*if(actiontype.equalsIgnoreCase("Complete Reversal")) {
				JSONObject json1 = JSONObject.fromObject(SettlementServiceCall.walletReversal(walletrequest, makerid, batchid));
				if((json1.getString("respcode")).equalsIgnoreCase("00")){
					
					String str="Wallet Response :"+json1.getString("respdesc");
					JSONObject json2 = JSONObject.fromObject(SettlementServiceCall.corePostingsRev(corerequest, makerid, paymentrefno));
					
					if((json2.getString("respcode")).equalsIgnoreCase("00")){
						
						connection.setAutoCommit(false);
						pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
						pstmt.setString(1, makerid);
						pstmt.setString(2, str+","+application+" Response :"+json2.getString("respdesc"));
						pstmt.setString(3, paymentrefno);
						pstmt.setString(4, refno);
						pstmt.executeUpdate();
						connection.commit();
						
						responseDTO.addMessages("Success");
					}else {
						connection.setAutoCommit(false);
						pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='F',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
						pstmt.setString(1, makerid);
						pstmt.setString(2, str+","+application+" Response :"+json2.getString("respdesc"));
						pstmt.setString(3, paymentrefno);
						pstmt.setString(4, refno);
						pstmt.executeUpdate();
						connection.commit();
						
						responseDTO.addError(str+","+application+" Response :"+json2.getString("respdesc"));
					}
					
				}
				
			}*/
		
		}else {
			String insQRY = "{call pRevTxnRevarsal(?,?,?,?,?)}";

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, "R"+batchid);
			cstmt.setString(2, trans);
			cstmt.setString(3, "U");
			
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeQuery();
System.out.println("Response code :: "+cstmt.getString(5));
System.out.println("Response Message :: "+cstmt.getString(4));
			if ((cstmt.getString(5)).contains("1")) {
				System.out.println("kailash here :: ");
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, "Wallet Response :"+cstmt.getString(4));
				pstmt.setString(3, paymentrefno);
				pstmt.setString(4, refno);

				pstmt.executeUpdate();
				connection.commit();
				responseDTO.addMessages("Success");
			}else{
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("UPDATE WALLET_SUCCREV_REQUEST SET STATUS='F',CHECKER_ID=?,CHECKER_DATE=sysdate,RESPENSE_MESSAGE=? WHERE PAYMENTREFERENCE=? AND P_SEQNO=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, "Wallet Response :"+cstmt.getString(4));
				pstmt.setString(3, paymentrefno);
				pstmt.setString(4, refno);
				pstmt.executeUpdate();
				connection.commit();
				
				responseDTO.addError("Wallet Response :"+cstmt.getString(4));
			}
		}
	
		
		

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
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


public ResponseDTO walletaccinfo(RequestDTO requestDTO) {

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
	String application="";



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
		
		
	
		
				qrey = "select  acct_no,ACCT_NAME,CUST_TYPE,balance,nvl(decode(CUST_TYPE,'WALLET',(select MOBILE_NUMBER from MOB_CONTACT_INFO where CUST_ID=wad.CUST_ID),'AGENT',(select MOBILE_NUMBER from AGENT_CONTACT_INFO where CUST_ID=wad.CUST_ID)),' ') from wallet_acct_data wad where balance>0 and CUST_TYPE in ('WALLET','AGENT') order by acct_no" ;	
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("ACCOUNTNO", getlmtfeeRs.getString(1));
				json.put("ACCT_NAME", SpecialCharUtils.replaceString(getlmtfeeRs.getString(2)));
				json.put("CUST_TYPE", getlmtfeeRs.getString(3));
				json.put("BALANCE", getlmtfeeRs.getString(4));
				json.put("MOBILENO", getlmtfeeRs.getString(5));
				
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
		
			qrey = "select  sum(balance) from wallet_acct_data where balance>0 and CUST_TYPE in ('WALLET','AGENT')" ;	
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				resultJson.put("TOT_BALANCE", getlmtfeeRs.getString(1));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
		
		
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

public ResponseDTO walletbalancesheet(RequestDTO requestDTO) {

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
	String application="";



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
		String fromdate=requestJSON.getString("fromdate");
		
		
	
		
				
		
			qrey = "select sum(decode(drcr_flag,'D',-amount,amount)) from wallet_fin_txn_posting where substr(account,1,1) in  (1,5,6,0) and trunc(TXN_STAMP)<to_date('"+fromdate+"','dd/mm/yyyy')" ;	
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				resultJson.put("WAL_CUST_BALANCE", getlmtfeeRs.getString(1));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			qrey = "select sum(decode(drcr_flag,'D',-amount,amount)) from wallet_fin_txn_posting where substr(account,1,1)=9 and trunc(TXN_STAMP)<to_date('"+fromdate+"','dd/mm/yyyy')" ;	
			
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				resultJson.put("WAL_SUP_BALANCE", getlmtfeeRs.getString(1));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
		
		
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

public ResponseDTO imeidetails(RequestDTO requestDTO) {

	logger.debug(" Inside fetchProductGridInfo.. ");

	HashMap<String, Object> lmtfeeDataMap = null;
	JSONArray lmtJsonArray = null;
	JSONArray feeJsonArray = null;
	
	
	JSONObject resultJson = null;
	JSONObject json = null;
	JSONObject json1 = null;

	PreparedStatement getlmtfeePstmt = null;
	ResultSet getlmtfeeRs = null;

	Connection connection = null;

	String qrey = "";
	String application="";



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
		String imeinumber=requestJSON.getString("imeinumber");

				qrey = "select MID.DEVICE_IP,MID.IMEI_NO,DECODE(MID.STATUS,'A','Active','Deactivated'),MID.TRANS_DTTM,MID.USER_ID,mcm.FIRST_NAME,mad.ACCT_NO,NVL(MID.DEVICE_TYPE,' '),NVL(MID.VERSION,' ') "
						+ "from MOB_IMEI_DATA MID,mob_customer_master MCM,mob_acct_data MAD where UPPER(MID.USER_ID)=UPPER(MCM.user_id) and MCM.id=MAD.cust_id and MAD.prim_flag='P' and MID.IMEI_NO='"+imeinumber+"' and rownum<=10 order by MID.TRANS_DTTM desc" ;	
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("DEVICE_IP", getlmtfeeRs.getString(1));
				json.put("IMEI_NO", getlmtfeeRs.getString(2));
				json.put("STATUS", getlmtfeeRs.getString(3));
				json.put("TRANS_DTTM", getlmtfeeRs.getString(4));
				json.put("USER_ID", getlmtfeeRs.getString(5));
				json.put("FIRST_NAME", getlmtfeeRs.getString(6));
				json.put("ACCT_NO", getlmtfeeRs.getString(7));
				json.put("DEVICE_TYPE", getlmtfeeRs.getString(8));
				json.put("VERSION", getlmtfeeRs.getString(9));
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			
			getlmtfeePstmt = connection.prepareStatement("SELECT COUNT(*) FROM MOB_IMEI_BLOCK WHERE IMEI_NO='"+imeinumber+"'");

			getlmtfeeRs = getlmtfeePstmt.executeQuery();
			json1 = new JSONObject();
			while (getlmtfeeRs.next()) {
				
				json1.put("CNT", getlmtfeeRs.getString(1));
				
			}
		
		
		
		
		resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
		resultJson.put("VIEW_LMT_DATA_CNT", json1);
		
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

public ResponseDTO imeiinserAck(RequestDTO requestDTO) {

	logger.debug(" Inside fetchProductGridInfo.. ");

	HashMap<String, Object> lmtfeeDataMap = null;
	JSONArray lmtJsonArray = null;
	JSONArray feeJsonArray = null;
	
	
	JSONObject resultJson = null;
	JSONObject json = null;

	PreparedStatement pstmt = null;

	Connection connection = null;

	String qrey = "";
	String application="";



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
		String imeinumber=requestJSON.getString("imeinumber");
		String imeistatus=requestJSON.getString("imeistatus");
		String reason=requestJSON.getString("reason");

		connection.setAutoCommit(false);
		if(imeistatus.equalsIgnoreCase("Block")) {
			pstmt = connection.prepareStatement("INSERT INTO MOB_IMEI_BLOCK(IMEI_NO,REASONS,MAKER_ID,MAKER_DT) VALUES(?,?,?,sysdate)");
			pstmt.setString(1, imeinumber);
			pstmt.setString(2, reason);
			pstmt.setString(3, makerId);
			pstmt.executeUpdate();
		}else {
			
			pstmt = connection.prepareStatement("INSERT INTO MOB_IMEI_BLOCK_HIST(IMEI_NO,REASONS_BLOCCK,MAKER_ID,MAKER_DT,CHECKER_ID,CHECKER_DT,REASONS_UNBLOCCK) select IMEI_NO,REASONS,MAKER_ID,MAKER_DT,?,SYSDATE,? from MOB_IMEI_BLOCK WHERE IMEI_NO=?");
			
			pstmt.setString(1, makerId);
			pstmt.setString(2, reason);			
			pstmt.setString(3, imeinumber);

			pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement("update MOB_IMEI_DATA set STATUS='L' where IMEI_NO=?");			
			pstmt.setString(1, imeinumber);
			pstmt.executeUpdate();
			
			
			pstmt = connection.prepareStatement("DELETE FROM  MOB_IMEI_BLOCK WHERE IMEI_NO=?");
			pstmt.setString(1, imeinumber);
			pstmt.executeUpdate();
		}
		
		connection.commit();
		
		responseDTO.addMessages("IMEI "+imeistatus+ " successfully ");


	} catch (Exception e) {
		logger.debug("Got Exception in View Product Details ["
				+ e.getMessage() + "]");
	} finally {
		
		try {

			

			if (pstmt != null) {
				pstmt.close();
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

public ResponseDTO viewReconcileinfo(RequestDTO requestDTO) {

	logger.debug(" Inside fetchProductGridInfo.. ");

	HashMap<String, Object> lmtfeeDataMap = null;
	JSONArray lmtJsonArray = null;
	JSONArray feeJsonArray = null;
	
	
	JSONObject resultJson = null;
	JSONObject json = null;

	PreparedStatement getlmtfeePstmt = null;
	ResultSet getlmtfeeRs = null;

	Connection connection = null;

	String lmtfeeQry = "select BSCM.SERVICECODE,bscm.servicedesc,count(WUT.SERVICECODE),nvl(sum(WUT.TXNAMOUNT),0)  from WALLET_UNSETTELMENT_TBL WUT, BANK_SERVICE_CODE_MASTER BSCM where WUT.SERVICECODE=BSCM.SERVICECODE and WUT.EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) and WUT.EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and WUT.EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) AND BSCM.APPLICATION='AGENT' group by BSCM.SERVICECODE,bscm.servicedesc order by count(WUT.SERVICECODE) desc";
			/*+ " UNION ALL "
			+ " select BSCM.SERVICECODE,bscm.servicedesc,count(WUT.SERVICECODE),nvl(sum(WUT.TXNAMOUNT),0) from WALLET_UNSETTELMENT_TBL WUT FULL OUTER JOIN BANK_SERVICE_CODE_MASTER BSCM ON WUT.SERVICECODE=BSCM.SERVICECODE where  BSCM.APPLICATION='AGENT' group by BSCM.SERVICECODE,bscm.servicedesc having count(WUT.SERVICECODE)=0 ";

*/

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
			json.put("SERVICECODE", getlmtfeeRs.getString(1));
			json.put("SERVICEDESC", getlmtfeeRs.getString(2));
			json.put("COUNT", getlmtfeeRs.getString(3));
			json.put("AMOUNT", getlmtfeeRs.getString(4));
			feeJsonArray.add(json);
			

		}

		resultJson.put("VIEW_RECON_DATA", feeJsonArray);
		
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

public ResponseDTO settlemetreportinfo(RequestDTO requestDTO) {

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
	String application="";

	try {
		responseDTO = new ResponseDTO();

		lmtfeeDataMap = new HashMap<String, Object>();
		lmtJsonArray = new JSONArray();
		feeJsonArray = new JSONArray();
		
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		connection = DBConnector.getConnection();
		String fromdate=requestJSON.getString("fromdate");
		String todate=requestJSON.getString("todate");
		
		logger.debug("connection is null [" + connection == null + "]");
		
	
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
	/*qrey = "select wst.SETID,wst.USERID,wst.TXNREF,wst.EXTTXNREF,wst.SERVICECODE,wst.TXNAMOUNT,wst.TXNDATE,wst.IMPACTWALAC,wst.x,wst.SETDRCR,wst.SETAMOUNT,wst.NARRATION,wst.CHANNEL,wst.STATUS from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF";
	*/
		String qrey1 ="";
		if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
		 qrey1 = "select wst.USERID,wst.TXNREF,wst.EXTTXNREF,wst.SERVICECODE,wst.TXNAMOUNT,wst.TXNDATE,wst.CHANNEL,DECODE(wst.SETDRCR,'C','Credit','Debit') from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF and TRUNC(wst.TXNDATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TXNDATE desc";
		}else{
			 qrey1 = "select wst.USERID,wst.TXNREF,wst.EXTTXNREF,wst.SERVICECODE,wst.TXNAMOUNT,wst.TXNDATE,wst.CHANNEL,DECODE(wst.SETDRCR,'C','Credit','Debit') from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF and TRUNC(wst.TXNDATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')  and wst.SERVICECODE='"+requestJSON.getString("servicecode")+"' order by TXNDATE desc";
			}
		getlmtfeePstmt = connection.prepareStatement(qrey1);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			
			while (getlmtfeeRs.next()) {
				json = new JSONObject();
				json.put("USERID", getlmtfeeRs.getString(1));
				json.put("TXNREF", getlmtfeeRs.getString(2));
				json.put("EXTTXNREF", getlmtfeeRs.getString(3));
				json.put("SERVICECODE", getlmtfeeRs.getString(4));
				json.put("TXNAMOUNT", getlmtfeeRs.getString(5));
				json.put("TXNDATE", getlmtfeeRs.getString(6));
				json.put("CHANNEL", getlmtfeeRs.getString(7));
				json.put("SETDRCR", getlmtfeeRs.getString(8));
				
				lmtJsonArray.add(json);
			}
			
			
			
			 
			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
			qrey = "select sum(wst.TXNAMOUNT),count(*),TRUNC(DBMS_RANDOM.VALUE(1,10000)) from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF and TRUNC(wst.TXNDATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by wst.TXNDATE desc";	
			}else {
				qrey = "select sum(wst.TXNAMOUNT),count(*),TRUNC(DBMS_RANDOM.VALUE(1,10000)) from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF and TRUNC(wst.TXNDATE) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')  and wst.SERVICECODE='"+requestJSON.getString("servicecode")+"' order by wst.TXNDATE desc";	
	
			}
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				resultJson.put("TOT_BALANCE", getlmtfeeRs.getString(1));
				resultJson.put("COUNT", getlmtfeeRs.getString(2));
				resultJson.put("SETTLEMET", "Settlement"+getlmtfeeRs.getString(3));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			String settstr="";
			if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
			settstr="select wst.setid,wst.userid,wst.txnref,wst.exttxnref,wst.servicecode,wst.txnamount,wst.txndate,wst.impactwalac,wst.walfloatac,wst.setdrcr,wst.setamount,wst.narration,wst.channel from wallet_settelment_tbl wst,wallet_unsettelment_tbl wust where wst.exttxnref =wust.exttxnref and trunc(wst.txndate) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by wst.txndate desc";
			}else {
				settstr="select wst.setid,wst.userid,wst.txnref,wst.exttxnref,wst.servicecode,wst.txnamount,wst.txndate,wst.impactwalac,wst.walfloatac,wst.setdrcr,wst.setamount,wst.narration,wst.channel from wallet_settelment_tbl wst,wallet_unsettelment_tbl wust where wst.exttxnref =wust.exttxnref and trunc(wst.txndate) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')  and wst.SERVICECODE='"+requestJSON.getString("servicecode")+"' order by wst.txndate desc";
		
			}
			String heading="Sno,User Id,Txn refernce no,Extrefernce no,service code,Txn Amount,Txn Date,Impact wallet acccount,Wallet float acccount,crdr flag,Amount,Narration,Channel";
			
			CSVReportGeneration.perform(resultJson.getString("SETTLEMET"), connection, settstr,heading);
		
		
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

public ResponseDTO unsettlemetreportinfo(RequestDTO requestDTO) {

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
	String application="";

	try {
		responseDTO = new ResponseDTO();

		lmtfeeDataMap = new HashMap<String, Object>();
		lmtJsonArray = new JSONArray();
		feeJsonArray = new JSONArray();
		
		resultJson = new JSONObject();

		requestJSON = requestDTO.getRequestJSON();
		connection = DBConnector.getConnection();
		/*String fromdate=requestJSON.getString("fromdate");
		String todate=requestJSON.getString("todate");*/
		
		logger.debug("connection is null [" + connection == null + "]");
		
	
		String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");
		
	/*qrey = "select wst.unSETID,wst.USERID,wst.TXNREF,wst.EXTTXNREF,wst.SERVICECODE,wst.TXNAMOUNT,wst.TXNDATE,wst.IMPACTWALAC,wst.x,wst.SETDRCR,wst.SETAMOUNT,wst.NARRATION,wst.CHANNEL,wst.STATUS from WALLET_SETTELMENT_TBL wst,WALLET_UNSETTELMENT_TBL wust where wst.EXTTXNREF =wust.EXTTXNREF";
	*/
		
		if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
			qrey = "select USERID,TXNREF,EXTTXNREF,SERVICECODE,TXNAMOUNT,TXNDATE,CHANNEL,DECODE(SETDRCR,'C','Credit','Debit')  from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) order by TXNDATE desc";
	
		}else {
			qrey = "select USERID,TXNREF,EXTTXNREF,SERVICECODE,TXNAMOUNT,TXNDATE,CHANNEL,DECODE(SETDRCR,'C','Credit','Debit')  from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL)  and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) and SERVICECODE='"+requestJSON.getString("servicecode")+"' order by TXNDATE desc";
	
		}
		
		System.out.println(qrey);
		getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			
			while (getlmtfeeRs.next()) {
				json = new JSONObject();
				json.put("USERID", getlmtfeeRs.getString(1));
				json.put("TXNREF", getlmtfeeRs.getString(2));
				json.put("EXTTXNREF", getlmtfeeRs.getString(3));
				json.put("SERVICECODE", getlmtfeeRs.getString(4));
				json.put("TXNAMOUNT", getlmtfeeRs.getString(5));
				json.put("TXNDATE", getlmtfeeRs.getString(6));
				json.put("CHANNEL", getlmtfeeRs.getString(7));
				json.put("SETDRCR", getlmtfeeRs.getString(8));
				
				lmtJsonArray.add(json);
			}
			
			
			
			
			 
			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
		
			   qrey = "select sum(TXNAMOUNT),count(*),TRUNC(DBMS_RANDOM.VALUE(1,10000)) from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) order by TXNDATE desc";	
			}else {
				qrey = "select sum(TXNAMOUNT),count(*),TRUNC(DBMS_RANDOM.VALUE(1,10000)) from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) and SERVICECODE='"+requestJSON.getString("servicecode")+"' order by TXNDATE desc";	
	
			}
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				resultJson.put("TOT_BALANCE", getlmtfeeRs.getString(1));
				resultJson.put("COUNT", getlmtfeeRs.getString(2));
				resultJson.put("SETTLEMET", "Unsettlement");
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			String settstr="";
			if((requestJSON.getString("servicecode")).equalsIgnoreCase("ALL")) {
			settstr="select wst.userid,wst.txnref,wst.exttxnref,wst.servicecode,wst.txnamount,'' from wallet_unsettelment_tbl wst where exttxnref not in ( select exttxnref from wallet_settelment_tbl) and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) order by txndate desc";
			}else {
			settstr="select '\"'||wst.userid||'\"','\"'||wst.txnref||'\"','\"'||wst.exttxnref||'\"','\"'||wst.servicecode||'\"','\"'||wst.txnamount||'\"','\"'||CHANNEL||'\"','' from wallet_unsettelment_tbl wst where exttxnref not in ( select exttxnref from wallet_settelment_tbl) and  EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST) and EXTTXNREF not in ( select TXN_REFERNCE_NO from FILE_UPLOAD_UNSETTLE) and SERVICECODE='"+requestJSON.getString("servicecode")+"' order by txndate desc";
	
			}
			String heading="USER_ID,TXN_REFERNCE_NO,EXTREFERNCE_NO,SERVICE_CODE,TXN_AMOUNT,CHANNEL,ACTION";
			
			CSVReportGeneration.perform(resultJson.getString("SETTLEMET"), connection, settstr,heading);
		
		
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

public ResponseDTO settlementrequestinfo(RequestDTO requestDTO) {

	logger.debug(" Inside fetchProductGridInfo.. ");

	HashMap<String, Object> lmtfeeDataMap = null;
	JSONArray lmtJsonArray = null;
	JSONArray feeJsonArray = null;
	
	
	JSONObject resultJson = null;
	JSONObject json = null;
	HashMap<String,String> hm = null;

	PreparedStatement getlmtfeePstmt = null;
	ResultSet getlmtfeeRs = null;

	Connection connection = null;

	String qrey = "";
	String application="";



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
		String requesttype=requestJSON.getString("requesttype");
		
		
		
		
		
		qrey = "select USERID,TXNREF,EXTTXNREF,SERVICECODE,TXNAMOUNT,TXNDATE,CHANNEL,DECODE(SETDRCR,'C','Credit','Debit'),NARRATION  from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) and TXNREF='"+refno+"' and SERVICECODE='"+requesttype+"'";

	
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();
			json = new JSONObject();
			while (getlmtfeeRs.next()) {

				
				json.put("USERID", getlmtfeeRs.getString(1));
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(3));
				json.put("BATCHID", getlmtfeeRs.getString(2));
				json.put("TRANS_TYPE", getlmtfeeRs.getString(4));
				json.put("TRNS_AMT", getlmtfeeRs.getString(5));
				json.put("TRANS_DATE", getlmtfeeRs.getString(6));
				
				json.put("CHANNEL", getlmtfeeRs.getString(7));
				json.put("SETDRCR", getlmtfeeRs.getString(8));
				json.put("DEBITNARRATION", getlmtfeeRs.getString(9));

				hm= new HashMap();
				hm.put("userid", getlmtfeeRs.getString(1));
				hm.put("channel", getlmtfeeRs.getString(7));
				hm.put("exttxnrefno", getlmtfeeRs.getString(3));
				hm.put("walletrefno", getlmtfeeRs.getString(2));
				hm.put("reqtype", getlmtfeeRs.getString(4));
				
				json.put("hashdata", "userid-"+getlmtfeeRs.getString(1)+"@channel-"+getlmtfeeRs.getString(7)+"@exttxnrefno-"+getlmtfeeRs.getString(3)+"@walletrefno-"+getlmtfeeRs.getString(2)+"@reqtype-"+getlmtfeeRs.getString(4));
				
				JSONObject json1 = JSONObject.fromObject(SettlementServiceCall.agentfundGetStatus(hm));
				System.out.println("kailash here :: "+json1);
				if((json1.getString("respcode")).equalsIgnoreCase("00")) {
					if((getlmtfeeRs.getString(4)).equalsIgnoreCase("AGENTFUND") || (getlmtfeeRs.getString(4)).equalsIgnoreCase("AGCASHDEP") || (getlmtfeeRs.getString(4)).equalsIgnoreCase("AGCASHWTHD") || (getlmtfeeRs.getString(4)).equalsIgnoreCase("WALAGNOWNBANK")) {
					json.put("bankecostatus", json1.getString("bankecostatus"));
					json.put("walecostatus", json1.getString("walecostatus"));
					json.put("tpartystatus", "0");
					}else {
						json.put("bankecostatus", json1.getString("bankecostatus"));
						json.put("walecostatus", json1.getString("walecostatus"));
						json.put("tpartystatus", json1.getString("tpartystatus"));
					}
				}
			}
			
			
			//JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(resultJson.getString("accountno")));
			

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			

		
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO settlementrequestinfoAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][settlementrequestinfoAck]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;

	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String paymentrefno=requestJSON.getString("paymentrefno");
		String txnamt=requestJSON.getString("txnamt");
		String userid=requestJSON.getString("userid");
		String channel=requestJSON.getString("channel");
		String txntype=requestJSON.getString("txntype");
		String txndatetime=requestJSON.getString("txndatetime");
		String Narration=requestJSON.getString("Narration");
		String batchid=requestJSON.getString("batchid");
		String remarks=requestJSON.getString("remarks");
		String requesttype=requestJSON.getString("requesttype");
		//String waccountno=requestJSON.getString("waccountno");
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		
		connection.setAutoCommit(false);
		pstmt = connection.prepareStatement("INSERT INTO WALLET_UNSETTLE_REQUEST(PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,REQUEST_TYPE,REMARKS,MAKER_ID,MAKER_DT,STATUS,ACCT_NO,BATCHID,DEBITNARRATION,TRANS_DATE) VALUES(?,?,?,?,?,?,?,?,SYSDATE,'P',?,?,?,?)");
		pstmt.setString(1, paymentrefno);
		pstmt.setString(2, txnamt);
		pstmt.setString(3, channel);
		pstmt.setString(4, userid);
		pstmt.setString(5, txntype);
		pstmt.setString(6, requesttype);
		pstmt.setString(7, remarks);
		pstmt.setString(8, makerid);
		pstmt.setString(9, "");
		pstmt.setString(10, batchid);
		pstmt.setString(11, Narration);
		pstmt.setString(12, txndatetime);
		pstmt.executeUpdate();
		connection.commit();


		responseDTO.addMessages("Success");

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][settlementrequestinfoAck] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][settlementrequestinfoAck] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
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

public ResponseDTO settlemetrequestApproval(RequestDTO requestDTO) {

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
	String application="";



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
		
		
		StringBuilder sb=new StringBuilder();
	
		sb.append("SELECT (select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGENTFUND'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGENTFUND'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='WALAGNOWNBANK'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='WALAGNOWNBANK'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='WALPAYBILLAGN'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='WALPAYBILLAGN'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='WALAGNOTBANK'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='WALAGNOTBANK'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNPAYBILLAIRTIME'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNPAYBILLAIRTIME'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDCSHWTDOWN'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDCSHWTDOWN'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNUSSDAIRTIME'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNUSSDAIRTIME'), ");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDFTXNOWN'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDFTXNOWN'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDCSHWTDOTH'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDCSHWTDOTH'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGCASHDEP'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGCASHDEP'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGNCRDFTXNOTH'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGNCRDFTXNOTH'),");
		
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='P' AND TXN_TYPE='AGCASHWTHD'),");
		sb.append("(select count(*) from WALLET_UNSETTLE_REQUEST where STATUS='C' AND TXN_TYPE='AGCASHWTHD')  FROM DUAL");
		
		
			 
			
			getlmtfeePstmt = connection.prepareStatement(sb.toString());

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("FUNDING_PENDING", getlmtfeeRs.getString(1));
				json.put("FUNDING_COMPLETED", getlmtfeeRs.getString(2));
				json.put("WALAGNOWNBANK_PENDING", getlmtfeeRs.getString(3));
				json.put("WALAGNOWNBANK_COMPLETED", getlmtfeeRs.getString(4));
				json.put("WALPAYBILLAGN_PENDING", getlmtfeeRs.getString(5));
				json.put("WALPAYBILLAGN_COMPLETED", getlmtfeeRs.getString(6));
				json.put("WALAGNOTBANK_PENDING", getlmtfeeRs.getString(7));
				json.put("WALAGNOTBANK_COMPLETED", getlmtfeeRs.getString(8));
				
				json.put("AGNPAYBILLAIRTIME_PENDING", getlmtfeeRs.getString(9));
				json.put("AGNPAYBILLAIRTIME_COMPLETED", getlmtfeeRs.getString(10));
				
				json.put("AGNCRDCSHWTDOWN_PENDING", getlmtfeeRs.getString(11));
				json.put("AGNCRDCSHWTDOWN_COMPLETED", getlmtfeeRs.getString(12));
				
				json.put("AGNUSSDAIRTIME_PENDING", getlmtfeeRs.getString(13));
				json.put("AGNUSSDAIRTIME_COMPLETED", getlmtfeeRs.getString(14));
				
				json.put("AGNCRDFTXNOWN_PENDING", getlmtfeeRs.getString(15));
				json.put("AGNCRDFTXNOWN_COMPLETED", getlmtfeeRs.getString(16));
				
				json.put("AGNCRDCSHWTDOTH_PENDING", getlmtfeeRs.getString(17));
				json.put("AGNCRDCSHWTDOTH_COMPLETED", getlmtfeeRs.getString(18));
				
				json.put("AGCASHDEP_PENDING", getlmtfeeRs.getString(19));
				json.put("AGCASHDEP_COMPLETED", getlmtfeeRs.getString(20));
				
				json.put("AGNCRDFTXNOTH_PENDING", getlmtfeeRs.getString(21));
				json.put("AGNCRDFTXNOTH_COMPLETED", getlmtfeeRs.getString(22));
				
				json.put("AGCASHWTHD_PENDING", getlmtfeeRs.getString(23));
				json.put("AGCASHWTHD_COMPLETED", getlmtfeeRs.getString(24));
				
				
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO settlementrequestapprovaldetails(RequestDTO requestDTO) {

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
	String application="";
	String txntype="";



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
		
		application=requestJSON.getString("application");
		txntype=requestJSON.getString("txntype");
		
		qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,TRANS_DATE,MAKER_ID,MAKER_DT,ACCT_NO from WALLET_UNSETTLE_REQUEST  where  STATUS='"+application+"' AND TXN_TYPE='"+txntype+"'";
	
		
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("TRNS_AMT", getlmtfeeRs.getString(2));
				json.put("CHANNEL", getlmtfeeRs.getString(3));
				json.put("USERID", getlmtfeeRs.getString(4));
				json.put("TRANS_TYPE", getlmtfeeRs.getString(5));
				json.put("TRANS_DATE", getlmtfeeRs.getString(6));
				json.put("MAKER_ID", getlmtfeeRs.getString(7));
				json.put("MAKER_DT", getlmtfeeRs.getString(8));
				json.put("ACCT_NO", getlmtfeeRs.getString(9));
				lmtJsonArray.add(json);
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
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

public ResponseDTO settlementrequestapprovalinfo(RequestDTO requestDTO) {

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
	String application="";
	String txntype="";



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
		application=requestJSON.getString("application");
		txntype=requestJSON.getString("txntype");
		
		qrey="select PAYMENTREFERENCE,TRNS_AMT,CHANNEL,USER_ID,TXN_TYPE,TRANS_DATE,MAKER_ID,BATCHID,REQUEST_TYPE,REMARKS from WALLET_UNSETTLE_REQUEST  where STATUS='"+application+"' AND TXN_TYPE='"+txntype+"' AND PAYMENTREFERENCE='"+refno+"'";   

		
			
			getlmtfeePstmt = connection.prepareStatement(qrey);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("PAYMENTREFERENCE", getlmtfeeRs.getString(1));
				json.put("TRNS_AMT", getlmtfeeRs.getString(2));
				json.put("CHANNEL", getlmtfeeRs.getString(3));
				json.put("USERID", getlmtfeeRs.getString(4));
				json.put("TRANS_TYPE", getlmtfeeRs.getString(5));
				json.put("TRANS_DATE", getlmtfeeRs.getString(6));
				json.put("DEBITNARRATION", getlmtfeeRs.getString(7));
				json.put("BATCHID", getlmtfeeRs.getString(8));
				json.put("ACCT_NO", getlmtfeeRs.getString(9));
				json.put("REMARK", getlmtfeeRs.getString(10));
			}

			getlmtfeePstmt.close();
			getlmtfeeRs.close();
			
		
		
		
		
		resultJson.put("VIEW_LMT_DATA", json);
		
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

public ResponseDTO settlementrequestinfoApprovalAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][Settlement Approval]");


	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;

	org.json.JSONObject jsonob=null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();


		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		String paymentrefno=requestJSON.getString("paymentrefno");
		String txnamt=requestJSON.getString("txnamt");
		String userid=requestJSON.getString("userid");
		String channel=requestJSON.getString("channel");
		String txntype=requestJSON.getString("txntype");
		String txndatetime=requestJSON.getString("txndatetime");
		String Narration=requestJSON.getString("Narration");
		String batchid=requestJSON.getString("batchid");
		String waccountno=requestJSON.getString("waccountno");
		String application=requestJSON.getString("application");
		System.out.println("kailash here ::: "+application);
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		HashMap hm= new HashMap();
		hm.put("userid", requestJSON.getString("userid"));
		hm.put("channel", requestJSON.getString("channel"));
		hm.put("extpaymetrefno", requestJSON.getString("paymentrefno"));
		hm.put("walletpaymetrefno", requestJSON.getString("batchid"));
		hm.put("txnamt", requestJSON.getString("txnamt"));
		hm.put("reqtype", requestJSON.getString("txntype"));
		hm.put("action", requestJSON.getString("waccountno"));
		connection.setAutoCommit(false);
		if((requestJSON.getString("requesttype")).equalsIgnoreCase("Approval")) {
			
			JSONObject json1=null;
		try {
			json1 = JSONObject.fromObject(SettlementServiceCall.agentfundReversalPostings(hm));
			logger.debug("inside [AgentDAO][Settlement Approval] -- "+json1);
		}catch(Exception ee) {
			responseDTO.addError("Internal Error Occured While Executing.");
			ee.printStackTrace();
		}
			
			if((json1.getString("respcode")).equalsIgnoreCase("00")){
				
				
				pstmt = connection.prepareStatement("UPDATE WALLET_UNSETTLE_REQUEST SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE PAYMENTREFERENCE=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, paymentrefno);
				pstmt.executeUpdate();
				
				
				responseDTO.addMessages("Success");
			}else{
				responseDTO.addError(jsonob.getString("respdesc"));
			}
		}else {
			pstmt = connection.prepareStatement("UPDATE WALLET_UNSETTLE_REQUEST SET STATUS='R',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE PAYMENTREFERENCE=?");
			pstmt.setString(1, makerid);
			pstmt.setString(2, paymentrefno);
			pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement("INSERT INTO WALLET_UNSETTLE_REJECTED SELECT * FROM WALLET_UNSETTLE_REQUEST WHERE PAYMENTREFERENCE=?");
			pstmt.setString(1, paymentrefno);
			pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement("DELETE FROM WALLET_UNSETTLE_REQUEST WHERE PAYMENTREFERENCE=?");
			pstmt.setString(1, paymentrefno);
			pstmt.executeUpdate();
			
			responseDTO.addMessages("Success");
		}
		connection.commit();

	} catch (SQLException e) { 
		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("[AgentDAO][gtAgentRegistrtionack] SQLException in usdnastrooptionConf [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (pstmt != null)
				pstmt.close();
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

public ResponseDTO AgentRegModifyProduct(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][AgentRegModifyProduct]");

	
	
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
		 String customercode=requestJSON.getString("customercode");
		 String application=requestJSON.getString("application");
		 
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 String Actionname="";
		 String Actiondesc="";
		
		
			 
			  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME,ACM.MIDDLE_NAME,ACM.LAST_NAME,ACM.ACC_BRANCH,to_char(ACM.DOB,'dd-mm-yyyy'),ACM.EMAILID,ACM.GENDER,ACM.SUPER_ADM,ACM.W_PRD_CODE,MCI.MOBILE_NUMBER,"
			  		+ "MCI.ADDRESS,MCI.ID_TYPE,MCI.ID_DETAILS,MCI.NATIONALITY,MCI.RL_LGA,MCI.R_STATE,MCI.COUNTRY,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.ID,NVL(ACM.USER_ID,' '),WAD.ACCT_NO,to_char(ACM.DATE_CREATED,'dd-mm-yyyy hh:mi:ss'),W_PRD_CODE,(SELECT PRD_NAME from PRODUCT WHERE PRD_CODE=W_PRD_CODE),AGENCY_TYPE from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.CUSTOMER_CODE='"+customercode+"'");
			  serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",customercode);
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
					json.put("userid",serviceRS.getString(21));
					json.put("walletaccountno",serviceRS.getString(22));
					json.put("onboarddate",serviceRS.getString(23));
					
					json.put("product",serviceRS.getString(24));
					json.put("prodesc",serviceRS.getString(25));
					json.put("agenttype",serviceRS.getString(26));
					
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

		logger.debug("[AgentDAO][AgentRegModifyProduct] SQLException in AgentRegModifyProduct [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][AgentRegModifyProduct] Internal Error Occured While Executing.");
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

public ResponseDTO AgentModifyProductAck(RequestDTO requestDTO) {
	Connection connection = null;
	String insQRY = "";
	String ip = null;

	CallableStatement cstmt = null;
	JSONObject resultJson = null;
	String encpin = null;
	String pin = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();
		
			insQRY = "{call posRegistrationpkg.AGENTMODIFYPRODUCT(?,?,?,?,?,?,?)}";

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, requestJSON.getString("refno"));
			cstmt.setString(2, requestJSON.getString("telephone"));
			cstmt.setString(3, requestJSON.getString("product"));
			cstmt.setString(4, requestJSON.getString("prodesc"));
			cstmt.setString(5, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(6, requestJSON.getString("remoteip"));
			
			
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.executeQuery();

			if (!(cstmt.getString(7).split("-")[1]).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(7));
			}else{
				responseDTO.addMessages((cstmt.getString(7)).split("-")[0]);
			}
			
		
		

	} catch (Exception ex) {
		logger.error("Error Occured..!" + ex.getLocalizedMessage());
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
	
		
		try {

			if (cstmt != null)
				cstmt.close();
			
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
	}
	return responseDTO;
	}

public ResponseDTO AgentSweepRequestDetails(RequestDTO requestDTO) {
	
	
	
	
	logger.debug("inside [AgentDAO][AgentSweepRequestDetails]");

	
	
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
		 String customercode=requestJSON.getString("customercode");
		 String application=requestJSON.getString("application");
		 
		 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		 String remoteip=requestJSON.getString("remoteip");
		 String Actionname="";
		 String Actiondesc="";
		
		
			 
			  servicePstmt = connection.prepareStatement("select ACM.ACCOUNT_NO,ACM.FIRST_NAME,ACM.MIDDLE_NAME,ACM.LAST_NAME,ACM.ACC_BRANCH,to_char(ACM.DOB,'dd-mm-yyyy'),ACM.EMAILID,ACM.GENDER,ACM.SUPER_ADM,ACM.W_PRD_CODE,MCI.MOBILE_NUMBER,"
			  		+ "MCI.ADDRESS,MCI.ID_TYPE,MCI.ID_DETAILS,MCI.NATIONALITY,MCI.RL_LGA,MCI.R_STATE,MCI.COUNTRY,decode(ACM.STATUS,'B','Block','F','Inactive','A','Active','L','Deactivation','Active'),ACM.ID,NVL(ACM.USER_ID,' '),WAD.ACCT_NO,to_char(ACM.DATE_CREATED,'dd-mm-yyyy hh:mi:ss'),W_PRD_CODE,(SELECT PRD_NAME from PRODUCT WHERE PRD_CODE=W_PRD_CODE),AGENCY_TYPE from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO MCI,WALLET_ACCT_DATA WAD where ACM.ID=MCI.CUST_ID and MCI.CUST_ID=WAD.CUST_ID and WAD.CUST_TYPE='AGENT' and ACM.CUSTOMER_CODE='"+customercode+"'");
			  serviceRS = servicePstmt.executeQuery();
				
				 while(serviceRS.next())
					{
					json.put("refno",customercode);
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
					json.put("userid",serviceRS.getString(21));
					json.put("walletaccountno",serviceRS.getString(22));
					json.put("onboarddate",serviceRS.getString(23));
					
					json.put("product",serviceRS.getString(24));
					json.put("prodesc",serviceRS.getString(25));
					json.put("agenttype",serviceRS.getString(26));
					
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

		logger.debug("[AgentDAO][AgentRegModifyProduct] SQLException in AgentRegModifyProduct [" + e.getMessage()
				+ "]");
		responseDTO.addError("[AgentDAO][AgentRegModifyProduct] Internal Error Occured While Executing.");
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

public ResponseDTO AgentSweepRequestDetailsAck(RequestDTO requestDTO) {
	Connection connection = null;
	String insQRY = "";
	String ip = null;

	CallableStatement cstmt = null;
	JSONObject resultJson = null;
	String encpin = null;
	String pin = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		connection = DBConnector.getConnection();
		
			insQRY = "{call posRegistrationpkg.AGENTCOMMISSIONSWEEP(?,?,?,?,?,?,?)}";

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, requestJSON.getString("refno"));
			cstmt.setString(2, requestJSON.getString("stDate"));
			cstmt.setString(3, requestJSON.getString("initiondate"));
			cstmt.setString(4, requestJSON.getString("reason"));
			cstmt.setString(5, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(6, requestJSON.getString("remoteip"));
			
			
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.executeQuery();

			System.out.println("kailash -- "+cstmt.getString(7));
			/*if ((cstmt.getString(7)).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(7));
			}else{*/
				responseDTO.addMessages("SUCCESS");
			//}
			
		
		

	} catch (Exception ex) {
		logger.error("Error Occured..!" + ex.getLocalizedMessage());
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
	
		
		try {

			if (cstmt != null)
				cstmt.close();
			
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
	}
	return responseDTO;
	}
}
