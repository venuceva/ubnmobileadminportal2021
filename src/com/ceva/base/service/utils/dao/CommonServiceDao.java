package com.ceva.base.service.utils.dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.CommonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import com.ceva.util.DBUtils;


public class CommonServiceDao {


	private Logger logger = Logger.getLogger(CommonServiceDao.class);
	JSONObject responseJson=null;

	private String MAILFROM = "alerts@xxx.co.ke";


	public  JSONObject auditTrailInsert(JSONObject requestJSON)
	{

		Connection connection = null;
		PreparedStatement pstmt = null;
		String auditQry=null;
		int i=0;		
		responseJson = new JSONObject();
		String transCodedesccQuery=null;
		PreparedStatement transdescPstmt=null;
		ResultSet transrs = null;
		String trascodedesc = null;
		
		try {

			connection = DBConnector.getConnection();
			
			logger.info("Request Json ["+requestJSON+"]");
			
			String makerid = requestJSON.containsKey(CevaCommonConstants.MAKER_ID)?requestJSON.getString(CevaCommonConstants.MAKER_ID):"";
			String transCode = requestJSON.containsKey("transCode")?requestJSON.getString("transCode"):"";
			String channel = requestJSON.containsKey("channel")?requestJSON.getString("channel"):"";
			String message = requestJSON.containsKey("message")?requestJSON.getString("message"):"";
			String ip = requestJSON.containsKey("ip")?requestJSON.getString("ip"):"";
			String det1= requestJSON.containsKey("det1")?requestJSON.getString("det1"):"";
			String det2= requestJSON.containsKey("det2")?requestJSON.getString("det2"):"";
			String det3=  requestJSON.containsKey("det3")?requestJSON.getString("det3"):"";
			
			//transCodedesccQuery="SELECT NVL(MENU_NAME,'NO_DATA'||?) FROM  CEVA_MENU_LIST   WHERE MENU_ACTION=?";
			transCodedesccQuery="SELECT (select MENU_NAME||'>>' from CEVA_MENU_LIST WHERE MENU_ID=LLL.PARENT_MENU_ID)||NVL(MENU_NAME,'NO_DATA'||?) FROM  CEVA_MENU_LIST LLL  WHERE MENU_ACTION=?";
			logger.info("TransCodedescc Query ["+transCodedesccQuery+"]");
			
			transdescPstmt = connection.prepareStatement(transCodedesccQuery);
			
			transdescPstmt.setString(1, transCode);
			transdescPstmt.setString(2, transCode);
			
			transrs = transdescPstmt.executeQuery();

			if (transrs.next()) {
				trascodedesc = transrs.getString(1).trim();
			}
			
			
			auditQry= "INSERT INTO AUDIT_DATA(ID,TRANSCODE,TRANSCODE_DESC,TXNDATE,NET_ID,CHANNEL,MESSAGE,IP,DETAIL_1,DETAIL_2,DETAIL_3) "+
					" VALUES (TO_CHAR(SYSTIMESTAMP,'DDISSSSSFF'),?,?,sysdate,?,?,?,?,?,?,?)";
			
			
			logger.info("Audit Data Query ["+auditQry+"]");
			
			pstmt = connection.prepareStatement(auditQry);
			pstmt.setString(1,transCode); 
			pstmt.setString(2,trascodedesc);
			pstmt.setString(3, makerid);
			pstmt.setString(4, channel); 
			pstmt.setString(5, message); 
			pstmt.setString(6,ip);
			pstmt.setString(7,det1);
			pstmt.setString(8,det2);
			pstmt.setString(9,det3);

			i = pstmt.executeUpdate();
			connection.commit();

			if (i == 1) {

				responseJson.put("status", "S");
				responseJson.put("message", "Successfully Inserted" );

				logger.debug("Action Identification Interceptor -> Successfully Inserted "
						+ ip);
				
			} else {

				responseJson.put("status", "E");
				responseJson.put("message", "Insertion failed due to some error" );

				logger.debug("Action Identification Interceptor -> insertion failed due to some error "
						+ ip);
				
			}



		} catch (Exception ex) {
			
			try {
				
				if(connection!=null)
					connection.rollback();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			//responseDTO.addError("Internal Error Occured While Executing.");

			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closePreparedStatement(transdescPstmt);
			DBUtils.closeResultSet(transrs);
			DBUtils.closeConnection(connection);
			pstmt = null;
			transrs=null;
			transdescPstmt=null;
			connection = null;
			trascodedesc = null;
			auditQry=null;
			
		}

		return responseJson;

	}


	public JSONObject insertSMS(JSONObject requestJSON) {

		Connection connection = null;
		PreparedStatement smsps = null;

		String query = null;
		responseJson = new JSONObject();

		try {

			connection = DBConnector.getConnection();

			logger.info("Request Json ["+requestJSON+"]");
			
			Random rnd = new Random();
			int number = 10000000 + rnd.nextInt(99999999);
			String reference = String.valueOf(number);

			String mobileno  = requestJSON.containsKey("mobileno")? requestJSON.getString("mobileno"):"" ;
			String templateID  = requestJSON.containsKey("templateID")? requestJSON.getString("templateID"):"" ;
			String outmesaage = requestJSON.containsKey("outmesaage")? requestJSON.getString("outmesaage"):"" ;

			query = "Insert into ALERTS (MSG_DATE,MOBILE_NO,APPL,FETCH_STATUS,TXN_REF_NO,RETRY_COUNT,SENT_STATUS,TEMPLATE_ID,OUT_MESSAGE)" +
					"values(sysdate,?,'SMS','V',?,0,'',?,?)";

			logger.info("SMS Query ["+query+"]");
			
			smsps = connection.prepareStatement(query);
			smsps.setString(1, mobileno);
			smsps.setString(2, reference);
			smsps.setString(3, templateID);
			smsps.setString(4, outmesaage);

			smsps.executeUpdate();
			connection.commit();
			responseJson.put("status", "S");
			responseJson.put("message", "Successfully Inserted" );
			

		} catch (Exception e) {

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			try {

				if (smsps != null) {
					smsps.close();
				}

				if (connection != null) {
					connection.close();
				}
				query = null;
				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return responseJson;

	}

	
	public JSONObject insertIMEI(JSONObject requestJSON) {

		Connection connection = null;
		PreparedStatement smsps = null;

		String query = null;
		responseJson = new JSONObject();

		try {

			connection = DBConnector.getConnection();

			logger.info("Request Json ["+requestJSON+"]");
			
			Random rnd = new Random();
			int number = 10000000 + rnd.nextInt(99999999);
			String reference = String.valueOf(number);

			String commonid  = requestJSON.containsKey("commonid")? requestJSON.getString("commonid"):"" ;
			String referenceno  = requestJSON.containsKey("referenceno")? requestJSON.getString("referenceno"):"" ;

			query = "Insert into MOB_IMEI_DATA (ID,COMMON_ID,REF_NO,STATUS)" +
					"values(IMG_SEQ.nextval,?,?,'P')";

			logger.info("SMS Query ["+query+"]");
			
			smsps = connection.prepareStatement(query);
			smsps.setString(1, commonid);
			smsps.setString(2, referenceno);
		

			smsps.executeUpdate();
			connection.commit();
			responseJson.put("status", "S");
			responseJson.put("message", "Successfully Inserted" );
			

		} catch (Exception e) {

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			try {

				if (smsps != null) {
					smsps.close();
				}

				if (connection != null) {
					connection.close();
				}
				query = null;
				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return responseJson;

	}

	public JSONObject insertIMEIAgent(JSONObject requestJSON) {

		Connection connection = null;
		PreparedStatement smsps = null;

		String query = null;
		String common_query = null;
		String commonid =null;
		String userid =null;
		String mobileno =null;
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		responseJson = new JSONObject();

		try {

			connection = DBConnector.getConnection();

			logger.info("Request Json ["+requestJSON+"]");
			
			Random rnd = new Random();
			int number = 10000000 + rnd.nextInt(99999999);
			String reference = String.valueOf(number);

			String ref_no  = requestJSON.containsKey("ref_no")? requestJSON.getString("ref_no"):"" ;
			String referenceno  = requestJSON.containsKey("referenceno")? requestJSON.getString("referenceno"):"" ;
			
			common_query="select COMMON_ID,USER_ID,MOBILE_NO from AGENT_INFO_COMMON_TEMP where REF_NUM="+ref_no+"";
			
			servicePstmt = connection.prepareStatement(common_query);
			serviceRS = servicePstmt.executeQuery();
			
			 while(serviceRS.next())
				{
				 commonid=serviceRS.getString(1);
				 userid=serviceRS.getString(2);
				 mobileno=serviceRS.getString(3);
				}

			query = "Insert into MOB_IMEI_DATA (ID,COMMON_ID,REF_NO,STATUS)" +
					"values(IMG_SEQ.nextval,?,?,'P')";

			logger.info("SMS Query ["+query+"]");
			
			smsps = connection.prepareStatement(query);
			smsps.setString(1, commonid);
			smsps.setString(2, referenceno);
		

			smsps.executeUpdate();
			connection.commit();
			responseJson.put("status", "S");
			responseJson.put("message", "Successfully Inserted" );
			ServiceRequestClient.sms(mobileno, "Dear "+userid+", please find the DSA Mobile App Activation Number "+referenceno);

		} catch (Exception e) {

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			try {

				if (smsps != null) {
					smsps.close();
				}
				if (servicePstmt != null) {
					servicePstmt.close();
				}
				if (serviceRS != null) {
					serviceRS.close();
				}
				if (connection != null) {
					connection.close();
				}
				query = null;
				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return responseJson;

	}


	public JSONObject insertEmail(JSONObject requestJSON) {

		Connection connection = null;
		PreparedStatement smsps = null;

		String query = null;
		responseJson = new JSONObject();

		try {

			connection = DBConnector.getConnection();
			
			logger.info("requestJSON ["+requestJSON+"]");

			Random rnd = new Random();
			int number = 10000000 + rnd.nextInt(99999999);
			String reference = String.valueOf(number);

			String mailto  = requestJSON.containsKey("mailto")? requestJSON.getString("mailto"):"";
			String mobileno =  requestJSON.containsKey("mobileno")? requestJSON.getString("mobileno"):"";
			String mailmessage =requestJSON.containsKey("mailmessage")?  requestJSON.getString("mailmessage"):""; // v_message_mail := '<FONT FACE=ARIAL>Dear '||vMerchantName ||', <BR><P>Please find below user id and temporary password </P><P><B>USER ID : </B>'||vMerchantName||'</P><P><B>PASSWORD :</B>'|| vpassword||'</P></FONT>';
			String subject = requestJSON.containsKey("subject")? requestJSON.getString("subject"):""; //'First Time Login Password #1'
			String txntype = requestJSON.containsKey("txntype")? requestJSON.getString("txntype"):""; //'INTERNALPASSWORD'


			query =  "insert into alerts(MSG_DATE,FAX_NO,EMAIL_ID,MOBILE_NO,MESSAGE, APPL,FETCH_STATUS,MAILFROM,  "+
					" MAILTO,MAILCC,MAILBCC,SUBJECT,APPCODE, "+
					" AGENCY_NAME,DETAILS,TXN_TYPE,TXN_REF_NO, RETRY_COUNT,RESDTTM,SENT_STATUS, "+
					" DELIVERY_STATUS,REQSTATUSDTTM,TEMPLATE_ID, UNIQUE_ID,INBOUND_MESSAGE_ID,REQDTTM, "+
					" IN_MESSAGE,OUT_MESSAGE) "+
					" values(sysdate,'',?,?, "+
					" ?,'MAIL','P',?,?,'','', "+
					" ?,'','','Success',?,?,'0', "+
					" sysdate,'','','','','','','','','') ";

			logger.info("Email Query ["+query+"]");

			smsps = connection.prepareStatement(query);
			smsps.setString(1, mailto);
			smsps.setString(2, mobileno);
			smsps.setString(3, mailmessage);
			smsps.setString(4, MAILFROM);
			smsps.setString(5, mailto);
			smsps.setString(6, subject);
			smsps.setString(7, txntype);
			smsps.setString(8, reference);

			smsps.executeUpdate();
			connection.commit();

			responseJson.put("status", "S");
			responseJson.put("message", "Successfully Inserted" );

			
		} catch (Exception e) {

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			
			try {

				if (smsps != null) {
					smsps.close();
				}

				if (connection != null) {
					connection.close();
				}
				query = null;
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
		}

		return responseJson;

	}
	
	
	public JSONObject genaratePassword()
	{

		String pin,pinHash;
		responseJson = new JSONObject();

		try{

			pin = CommonUtil.generatePassword(4);
			pinHash = CommonUtil.b64_sha256(pin).trim();

			JSONObject resp = new JSONObject();
			resp.put("pin", pin);
			resp.put("pinHash", pinHash);

			responseJson.put("status", "S");
			responseJson.put("message", "Success" );
			responseJson.put("data", resp);

		}catch(Exception e)
		{

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		}

		return responseJson;

	}
	
	public JSONObject passwordHashing(String pin)
	{

		String pinHash;
		responseJson = new JSONObject();

		try{

			pinHash = CommonUtil.b64_sha256(pin).trim();

			JSONObject resp = new JSONObject();
			resp.put("pin", pin);
			resp.put("pinHash", pinHash);

			responseJson.put("status", "S");
			responseJson.put("message", "Success" );
			responseJson.put("data", resp);

		}catch(Exception e)
		{

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		}

		return responseJson;

	}
	
	public JSONObject genaratedevicerefno()
	{

		String pin,pinHash;
		responseJson = new JSONObject();

		try{

			pin = CommonUtil.generatePassword(6);
			pinHash = CommonUtil.b64_sha256(pin).trim();

			JSONObject resp = new JSONObject();
			resp.put("pin", pin);
			resp.put("pinHash", pinHash);

			responseJson.put("status", "S");
			responseJson.put("message", "Success" );
			responseJson.put("data", resp);

		}catch(Exception e)
		{

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		}

		return responseJson;

	}



	public JSONObject getPassword(JSONObject requestJSON) {

		Connection connection = null;
		PreparedStatement hashpinPstmt = null;
		ResultSet hashpinRS = null;

		responseJson = new JSONObject();
		String pinQry=null;
		String hashpin=null;
		
		try {

			connection = DBConnector.getConnection();
			String customercode = requestJSON.getString("customercode");

			pinQry = "select pin from MOB_CUSTOMER_MASTER where CUSTOMER_CODE=?";
			hashpinPstmt = connection.prepareStatement(pinQry);
			hashpinPstmt.setString(1, customercode);
			hashpinRS = hashpinPstmt.executeQuery();

			if (hashpinRS.next()) {
				hashpin = hashpinRS.getString(1).trim();
			}

			JSONObject resp = new JSONObject();
			resp.put("hashPin", hashpin);

			responseJson.put("status", "S");
			responseJson.put("message", "Success" );
			responseJson.put("data", resp);

		} catch (Exception e) {

			e.printStackTrace();
			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			
			try {

				if (hashpinPstmt != null) {
					hashpinPstmt.close();
				}

				if (hashpinRS != null) {
					hashpinRS.close();
				}

				if (connection != null) {
					connection.close();
				}
				
				pinQry=null;
				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return responseJson;

	}


	
/*
	public JSONObject updatePassword(JSONObject requestJSON)
	{

		Connection connection = null;

		CallableStatement cstmt = null;
		String auditQry=null;
		int i=0;		
		responseJson = new JSONObject();
		String detQry,pin,hashpin,customercode,ip;

		try {

			connection = DBConnector.getConnection();

			detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";


			pin = CommonUtil.generatePassword(4);
			hashpin = CommonUtil.b64_sha256(pin).trim();
			customercode = requestJSON.getString("customercode");
			ip = requestJSON.getString("ip");
			
			
			cstmt = connection.prepareCall(detQry);
			cstmt.setString(1, customercode);
			cstmt.setString(2, "resetpin");
			cstmt.setString(3, hashpin);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.setString(7,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(8, ip);
			cstmt.setString(9, pin);
			cstmt.executeQuery();

			
			JSONObject resp = new JSONObject();
			resp.put("CustomerStatus", cstmt.getString(4));
			resp.put("STATUS", cstmt.getString(6));
			resp.put("CustomerTelephone", cstmt.getString(5));
			resp.put("AuthId", "PINRESET");
			
			responseJson.put("status", "S");
			responseJson.put("message", "Successfully Inserted" );
			responseJson.put("data", resp);


		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			//responseDTO.addError("Internal Error Occured While Executing.");

			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}

		return null;

	}

	public JSONObject insertNewAccountInfo(JSONObject resultJson) {

		Connection connection = null;
		String insQRY = "";

		CallableStatement cstmt = null;
		//JSONObject resultJson = null;
		String pinHash = null;
		String pin = null;

		String ip = null;

		responseJson = new JSONObject();

		try {

			pin = CommonUtil.generatePassword(4);
			pinHash = CommonUtil.b64_sha256(pin).trim();;

			insQRY = "{call ACCOUNTSPKG.INSERTADDACCOUNTTOCUSTOMERPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			//resultJson = (JSONObject) requestJSON.get("accBean");

			ip = resultJson.getString("ip");
			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("institute"));
			cstmt.setString(2, resultJson.getString("customercode"));
			cstmt.setString(3, resultJson.getString("email"));
			cstmt.setString(4, resultJson.getString("fullname"));
			cstmt.setString(5, resultJson.getString("idnumber"));
			cstmt.setString(6, resultJson.getString("langugae"));
			cstmt.setString(7, resultJson.getString("telephone"));
			cstmt.setString(8, resultJson.getString("telco"));
			cstmt.setString(9, resultJson.getString("isocode"));
			cstmt.setString(10, resultJson.getString("multiData"));
			cstmt.setString(11, resultJson.getString("newAccountData"));
			cstmt.setString(12,
					resultJson.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(13, Types.VARCHAR);
			cstmt.setString(14, pinHash);
			cstmt.setString(15, pin);
			cstmt.setString(16, ip);
			cstmt.executeQuery();

			if (!cstmt.getString(13).contains("SUCCESS")) {
				//responseDTO.addError(cstmt.getString(13));

				responseJson.put("status", "S");
				responseJson.put("message", cstmt.getString(13) );

			}

		} catch (Exception ex) {
			
			ex.printStackTrace();
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			//responseDTO.addError("Internal Error Occured While Executing.");

			responseJson.put("status", "E");
			responseJson.put("message","Internal Error Occured While Executing.");

		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}

		return responseJson;

	}
*/
	public static void main(String[] args) {
		
		
		try
		{
			
			CommonServiceDao dao = new CommonServiceDao();
			JSONObject respref =  dao.passwordHashing("1234").getJSONObject("data");
			String pinref=respref.getString("pinHash");
			System.out.println(pinref);
			/*JSONObject smsreqJson = new JSONObject();
			smsreqJson.put("mobileno", "254666666666");
			smsreqJson.put("templateID", "W_ONBRD_ACC");
			smsreqJson.put("outmesaage", "CUSTOMER_NAME-CLEMENT NDEGWA NDERI,NEW_PIN-5471");
			JSONObject response =  dao.insertSMS(smsreqJson);
			*/
			
			/*JSONObject emailreqJson = new JSONObject();
			emailreqJson.put("mailto", "bevarasravan@gmail.com");
			emailreqJson.put("mobileno", "9502188246");
			emailreqJson.put("mailmessage", "<FONT FACE=ARIAL>Dear Merchant , <BR><P>Please find below user id and temporary password </P><P><B>USER ID : </B> Userid </P><P><B>PASSWORD :</B> 8888 </P></FONT>");
			emailreqJson.put("subject", "First Time Login Password #1");
			emailreqJson.put("txntype", "INTERNALPASSWORD");
			
			JSONObject response =  dao.insertEmail(emailreqJson);*/
			
			
			/*JSONObject aditJson = new JSONObject();
			aditJson.put(CevaCommonConstants.MAKER_ID,"CEVA");
			aditJson.put("transCode","AuthorizationAll");
			aditJson.put("channel","WEB");
			aditJson.put("message","AuthorizationAll Action Selected By CEVA");
			aditJson.put("ip","0:0:0:0:0:0:0:1");
			
			JSONObject response =  dao.aditTrailInsert(aditJson);*/
			
			//JSONObject response = dao.genaratePassword(); //{"status":"S","message":"Success","data":{"pinHash":"TDwyguIFD2akd0sz9EWoQ4o8tlaeSxqEB1D9t7BDkX0"}}
			
			
			
			
		/*	JSONObject getpinJson = new JSONObject();
			getpinJson.put("customercode","1000170512");
			
			JSONObject response =  dao.getPassword(getpinJson);
			System.out.println(response); */
			
			/*JSONObject accountnew = new JSONObject();
			
			accountnew.put("ip", "0:0:0:0:0:0:0:1");
			accountnew.put("institute", "institute");
			accountnew.put("customercode", "");
			accountnew.put("email", "bevarasravan@gmail.com");
			accountnew.put("fullname", "Sravan Kumar B");
			accountnew.put("idnumber", "12345");
			accountnew.put("langugae", "1");
			accountnew.put("telephone", "9502188246");
			
			
			accountnew.put("telco", "telco");
			accountnew.put("isocode", "isocode");
			accountnew.put("multiData", "multiData");
			accountnew.put("newAccountData", "newAccountData");
			accountnew.put(CevaCommonConstants.MAKER_ID, "CEVABO");
			
			JSONObject response =  dao.insertNewAccountInfo(accountnew);*/
			
			//insertNewAccountInfo
			
			//System.out.println(getGenPass());
			//System.out.println(b64_sha256(getGenPass()));
			
			
		}catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
		
	}
	
	   public static String getGenPass(){
	    	String out="";
	    	
	    	//begin method with return string and you may or may not give input
	    	char[] values1 = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	    	    char[] values2 = {'!','@','&','$','#','%','*'};
	    	    char[] values3 = {'1','2','3','4','5','6','7','8','9','0'};
	    	    String out1="";
	    	    String out2="";
	    	    String out3="";
	    	 
	    	         for (int i=0;i<6;i++)
	    	            {
	    	             int idx=new SecureRandom().nextInt(values1.length);
	    	            out1+= values1[idx];
	    	            }
	    	 
	    	    for (int i=0;i<3;i++)
	    	            {
	    	            int idx=new SecureRandom().nextInt(values3.length);
	    	             out2+= values3[idx];
	    	            }
	    	 
	    	    for (int i=0;i<1;i++)
	    	            {
	    	            int idx=new SecureRandom().nextInt(values2.length);
	    	             out3+= values2[idx];
	    	            }
	    	 
	    	    out= out1.concat(out3).concat(out2);
	    	    return out;
	    	 
	    	//end of method returning String 'OUT' as a random password
	    }
	   
	   public static  String  b64_sha256(String inputString){ 
			String outputString="";
			if(inputString!=null){
				outputString=Base64.encodeBase64String(DigestUtils.sha256(inputString)).trim();
				//System.out.println("b64_sha256 outputString::"+outputString);
			}
			else{
				System.out.println("Input String Missing for b64_sha256");
			}
			outputString=outputString.substring(0, outputString.length()-1);
			return outputString;

		}
	
}
