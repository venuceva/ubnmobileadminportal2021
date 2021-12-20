package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.fileupload.UnsettledFileValidation;
import com.ceva.base.reports.CSVReportGeneration;
import com.ceva.unionbank.channel.CallAgentServices;
import com.ceva.unionbank.channel.FileValidator;
import com.ceva.unionbank.channel.RaasValidator;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.unionbank.channel.SettlementServiceCall;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FileUploadDAO {
private static Logger logger = Logger.getLogger(FileUploadDAO.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	
	public ResponseDTO fileUpload(RequestDTO requestDTO) {
		
		
		
		
		logger.debug("inside [FileUploadDAO][fileUpload]");

		
		
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
			

			
					 String limitCodeQry = "select STATUS||'-'||KEY_VALUE,KEY_VALUE from CONFIG_DATA WHERE key_group='FILEUPLOAD' AND KEY_TYPE in ('F_FILETYPE')";
					 servicePstmt = connection.prepareStatement(limitCodeQry);
					 serviceRS = servicePstmt.executeQuery();
						while (serviceRS.next()) {
							jsonlmt.put(serviceRS.getString(1), serviceRS.getString(2));
						}
						json.put("LIMIT_CODE", jsonlmt);
					
				
			 
				
						
			 viewDataMap.put("VewDetails", json);
			 responseDTO.setData(viewDataMap);
			
	       } catch (Exception e) {

			logger.debug("[FileUploadDAO] [fileUpload] SQLException in BulkDsaReg [" + e.getMessage()
					+ "]");
			responseDTO.addError("[FileUploadDAO] [fileUpload] Internal Error Occured While Executing.");
		} finally {
			
			viewDataMap = null;
			
			
		}

		return responseDTO;
	}
	
	
	public ResponseDTO gtFileUploadData(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		Connection connection = null;
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		
		

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String filepath=requestJSON.getString("filepath");
			String filename=requestJSON.getString("filename");
			String txntype=(requestJSON.getString("limitcode")).split("-")[0];
			
			System.out.println("txn code :: "+txntype+" :: file path :: "+filepath+"/"+filename);
			
			JSONObject json = new JSONObject();
			json.put("status",FileValidator.getFileInformation(filepath+"/"+filename,txntype,connection));
			
			//RaasValidator.getFileInformation(filepath+"/"+filename)

			resultJson.put("Files_List", json);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		}catch (Exception e) {

			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				
				
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
	
	public ResponseDTO fileUploadProcessack(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;

		Connection connection = null;
		PreparedStatement pstmt2 = null;
		Statement statement=null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			String limitcode=requestJSON.getString("limitcode");
			String filename=requestJSON.getString("filename");
			String records=requestJSON.getString("records");
			String filepath=requestJSON.getString("filepath");
			
			String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			pstmt2 = connection.prepareStatement("INSERT INTO  file_upload_master(ref_num,f_type,f_name,num_of_record,upload_date,status,maker_id,maker_dt) values(SEQ_FILEUPLOAD.nextval,'"+limitcode.split("-")[0]+"','"+filename+"','"+records+"',sysdate,'A','"+makerid+"',sysdate)");
			pstmt2.executeUpdate();
			pstmt2.close();
			
			if((limitcode.split("-")[0]).equalsIgnoreCase("UNSETTLED")) {
				
			
			
				String sql = "insert into FILE_UPLOAD_UNSETTLE (REF_NUM,USER_ID,TXN_REFERNCE_NO,EXTREFERNCE_NO,SERVICE_CODE,TXN_AMOUNT,ACTION,STATUS,CHANNEL) values(SEQ_FILEUPLOAD.currval,?,?,?,?,?,?,'P',?)";
				pstmt2 = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getData(filepath+"/"+filename)) {
		        	
		        	pstmt2.setString(1, (String) map.get("userid"));
		        	pstmt2.setString(2, (String) map.get("txnrefno"));
		        	pstmt2.setString(3, (String) map.get("extrefno"));
		        	pstmt2.setString(4, (String) map.get("servicecode"));
		        	pstmt2.setString(5, (String) map.get("txnamt"));
		        	pstmt2.setString(6, (String) map.get("action"));
		        	pstmt2.setString(7, (String) map.get("channel"));
		        	
		        	pstmt2.addBatch();
		        }
		        pstmt2.executeBatch();
		        connection.commit();
		        pstmt2.clearBatch();
		        pstmt2.close();
		       
			}
			
			if((limitcode.split("-")[0]).equalsIgnoreCase("REVSUCC")) {
				
				String sql = "insert into FILE_UPLOAD_SUCCREV (REF_NUM,USER_ID,PAYMENT_REFERNCE_NO,TXN_AMOUNT,STATUS) values(SEQ_FILEUPLOAD.currval,?,?,?,'P')";
				pstmt2 = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getDataSuccrev(filepath+"/"+filename)) {
		        	
		        	pstmt2.setString(1, (String) map.get("userid"));
		        	pstmt2.setString(2, (String) map.get("txnrefno"));
		        	pstmt2.setString(3, (String) map.get("txnamt"));
		        	
		        	
		        	pstmt2.addBatch();
		        }
		        pstmt2.executeBatch();
		        connection.commit();
		        pstmt2.clearBatch();
		        pstmt2.close();
				
			}
			
			if((limitcode.split("-")[0]).equalsIgnoreCase("TERMINAL")) {
				
				String sql = "insert into FILE_UPLOAD_TERMINAL (REF_NUM,USER_ID,TERMINAL_MAKE,MODEL_NUMBER,SERIAL_NUMBER,STATUS) values(SEQ_FILEUPLOAD.currval,?,?,?,?,'P')";
				pstmt2 = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getDataTerminal(filepath+"/"+filename)) {
		        	
		        	pstmt2.setString(1, (String) map.get("userid"));
		        	pstmt2.setString(2, (String) map.get("terminalmake"));
		        	pstmt2.setString(3, (String) map.get("modelno"));
		        	pstmt2.setString(4, (String) map.get("serailno"));
		        	
		        	
		        	pstmt2.addBatch();
		        }
		        pstmt2.executeBatch();
		        connection.commit();
		        pstmt2.clearBatch();
		        pstmt2.close();
				
			}
			
			/*if((limitcode.split("-")[0]).equalsIgnoreCase("PREENROLMENT")) {
				
				String sql = "insert into FILE_UPLOAD_PREENROLMENT (REF_NUM,SEQ_NO,ACCOUNT_NO,UPLOAD_DATE,STATUS) values(SEQ_FILEUPLOAD.currval,PREENC_SEQ.nextval,?,SYSDATE,'P')";
				pstmt2 = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getDataPreEnrolment(filepath+"/"+filename)) {
		        	
		        	pstmt2.setString(1, (String) map.get("accountno"));
		        	pstmt2.addBatch();
		        }
		        pstmt2.executeBatch();
		        connection.commit();
		        pstmt2.clearBatch();
		        pstmt2.close();
				
			}*/
			
			if((limitcode.split("-")[0]).equalsIgnoreCase("PREENROLMENT")) {
				
				String sql = "insert into FILE_UPLOAD_PREENROLMENT (REF_NUM,SEQ_NO,ACCOUNT_NO,UPLOAD_DATE,STATUS,CUST_ID,MOBILE_NUMBER,FULL_NAME,BRANCH_CODE,ACCT_TYPE,PRODUCT_NAME,DOB,EMAIL_ID,GENDER,SERVICE_RESPONSE) values(SEQ_FILEUPLOAD.currval,PREENC_SEQ.nextval,?,SYSDATE,'P',?,?,?,?,?,?,?,?,?,?)";
				pstmt2 = connection.prepareStatement(sql);
		        for (Map<String, String> map : UnsettledFileValidation.getDataPreEnrolment(filepath+"/"+filename)) {
		        	
		        	pstmt2.setString(1, (String) map.get("accountno"));
		        	
		        	try {
				        	JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails((String) map.get("accountno")));
				        	if(!(json1.getString("custID")).equalsIgnoreCase("")){
							
							String mobileno="";
							
							if((json1.getString("phone")).startsWith("0")){
								mobileno="234"+(json1.getString("phone")).substring(1);
							}else{
								mobileno="234"+json1.getString("phone");
							}
							
							pstmt2.setString(2, json1.getString("custID"));
							pstmt2.setString(3, mobileno);
							pstmt2.setString(4, (json1.get("accountName")).toString());
							pstmt2.setString(5, json1.getString("branchCode"));
							pstmt2.setString(6, (json1.get("accountStatus")).toString());
							pstmt2.setString(7, (json1.get("productName")).toString());
							
							JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid(json1.getString("custID")));
							JSONArray jsonarray =  JSONArray.fromObject(json2.get("custinfo"));
							Iterator iterator = jsonarray.iterator();
							while (iterator.hasNext()) {
								JSONObject jsonobj=(JSONObject)iterator.next();
							
								
								pstmt2.setString(8, (String)jsonobj.get("dateOfBirth"));
								pstmt2.setString(9, (String)jsonobj.get("custEmail"));
								pstmt2.setString(10, (String)jsonobj.get("gender"));
								
							}
							
							pstmt2.setString(11, "service working");
								
							}else {
								pstmt2.setString(2, "");
								pstmt2.setString(3, "");
								pstmt2.setString(4, "");
								pstmt2.setString(5, "");
								pstmt2.setString(6, "");
								pstmt2.setString(7, "");
								pstmt2.setString(8, "");
								pstmt2.setString(9, "");
								pstmt2.setString(10, "");
								pstmt2.setString(11, "Invalid Account Number");
							}
							
							
							
		        	}catch(Exception e) {
		        		pstmt2.setString(2, "");
						pstmt2.setString(3, "");
						pstmt2.setString(4, "");
						pstmt2.setString(5, "");
						pstmt2.setString(6, "");
						pstmt2.setString(7, "");
						pstmt2.setString(8, "");
						pstmt2.setString(9, "");
						pstmt2.setString(10, "");
						pstmt2.setString(11, "service not working");
		        	}
		        	
		        	
		        	pstmt2.addBatch();
		        }
		        pstmt2.executeBatch();
		        connection.commit();
		        pstmt2.clearBatch();
		        pstmt2.close();
				
			}
			
			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);
			connection.commit();
			
			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		}catch (Exception e) {

			logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (pstmt2 != null)
					pstmt2.close();
				
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
	
	
	public ResponseDTO fileUploadApprovalView(RequestDTO requestDTO) {

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
		
			sb.append("SELECT (select count(*) from FILE_UPLOAD_MASTER where STATUS='A' AND F_TYPE='UNSETTLED'),"
					+ "(select count(*) from FILE_UPLOAD_MASTER where STATUS='A' AND F_TYPE='REVSUCC'),"
					+ "(select count(*) from FILE_UPLOAD_MASTER where STATUS='A' AND F_TYPE='PREENROLMENT'),"
					+ "(select count(*) from FILE_UPLOAD_MASTER where STATUS='A' AND F_TYPE='TERMINAL') FROM DUAL");
			//sb.append("(select count(*) from FILE_UPLOAD_MASTER where STATUS='C' AND F_TYPE='UNSETTLED')  FROM DUAL");
			
			
				
				getlmtfeePstmt = connection.prepareStatement(sb.toString());

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("FUNDING_PENDING", getlmtfeeRs.getString(1));
					json.put("REVSUCC_PENDING", getlmtfeeRs.getString(2));
					json.put("PREENROLMENT_PENDING", getlmtfeeRs.getString(3));
					json.put("TERMINAL_PENDING", getlmtfeeRs.getString(4));
					//json.put("FUNDING_COMPLETED", getlmtfeeRs.getString(2));
					
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
	
	
	public ResponseDTO fileUploadApprovaldetails(RequestDTO requestDTO) {

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
			
			
		
			if(application.equalsIgnoreCase("FUNDING_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='A' and f_type='UNSETTLED'";   

			}else if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='C' and f_type='UNSETTLED'";   

			}else if(application.equalsIgnoreCase("REVSUCC_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='A' and f_type='REVSUCC'";   

			}else if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='C' and f_type='REVSUCC'";   

			}else if(application.equalsIgnoreCase("TERMINAL_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='A' and f_type='TERMINAL'";   

			}else if(application.equalsIgnoreCase("TERMINAL_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='C' and f_type='TERMINAL'";   

			}else if(application.equalsIgnoreCase("PREENROLMENT_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='A' and f_type='PREENROLMENT'";   

			}else if(application.equalsIgnoreCase("PREENROLMENT_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID from file_upload_master where status='C' and f_type='PREENROLMENT'";   

			}
				
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("REF_NUM", getlmtfeeRs.getString(1));
					json.put("f_name", getlmtfeeRs.getString(2));
					json.put("NUM_OF_RECORD", getlmtfeeRs.getString(3));
					json.put("UPLOAD_DATE", getlmtfeeRs.getString(4));
					json.put("MAKER_ID", getlmtfeeRs.getString(5));
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
	
	public ResponseDTO fileuploadrequestapprovalinfo(RequestDTO requestDTO) {

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
			
			if(application.equalsIgnoreCase("FUNDING_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy hh24:mi:ss'),MAKER_ID,F_TYPE from file_upload_master where status='A' and f_type='UNSETTLED' AND REF_NUM='"+refno+"'";   

			}else if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID,F_TYPE from file_upload_master where status='C' and f_type='UNSETTLED' AND REF_NUM='"+refno+"'";     

			}else if(application.equalsIgnoreCase("REVSUCC_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy hh24:mi:ss'),MAKER_ID,F_TYPE from file_upload_master where status='A' and f_type='REVSUCC' AND REF_NUM='"+refno+"'";   

			}else if(application.equalsIgnoreCase("REVSUCC_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID,F_TYPE from file_upload_master where status='C' and f_type='REVSUCC' AND REF_NUM='"+refno+"'";     

			}else if(application.equalsIgnoreCase("TERMINAL_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy hh24:mi:ss'),MAKER_ID,F_TYPE from file_upload_master where status='A' and f_type='TERMINAL' AND REF_NUM='"+refno+"'";   

			}else if(application.equalsIgnoreCase("TERMINAL_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID,F_TYPE from file_upload_master where status='C' and f_type='TERMINAL' AND REF_NUM='"+refno+"'";     

			}else if(application.equalsIgnoreCase("PREENROLMENT_PENDING")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy hh24:mi:ss'),MAKER_ID,F_TYPE from file_upload_master where status='A' and f_type='PREENROLMENT' AND REF_NUM='"+refno+"'";   

			}else if(application.equalsIgnoreCase("PREENROLMENT_COMPLETED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,UPLOAD_DATE,MAKER_ID,F_TYPE from file_upload_master where status='C' and f_type='PREENROLMENT' AND REF_NUM='"+refno+"'";     

			}
			
				
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("REF_NUM", getlmtfeeRs.getString(1));
					json.put("f_name", getlmtfeeRs.getString(2));
					json.put("NUM_OF_RECORD", getlmtfeeRs.getString(3));
					json.put("UPLOAD_DATE", getlmtfeeRs.getString(4));
					json.put("MAKER_ID", getlmtfeeRs.getString(5));
					json.put("F_TYPE", getlmtfeeRs.getString(6));
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
	
	public ResponseDTO fileuploadrequestinfoApprovalAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		HashMap<String, Object> lmtfeeDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		JSONArray lmtJsonArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		org.json.JSONObject jsonob=null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			lmtJsonArray = new JSONArray();
			lmtfeeDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();
			String filerefno=requestJSON.getString("filerefno");
			String requesttype=requestJSON.getString("requesttype");
			String reason=requestJSON.getString("reason");
			
			String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			JSONObject json = new JSONObject();
				
				connection.setAutoCommit(false);
			
				if(requesttype.contentEquals("Approval")) {
					
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE REF_NUM=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, filerefno);
					pstmt.executeUpdate();					
					pstmt.close();
					
					
					
					 servicePstmt = connection.prepareStatement("select USER_ID,TXN_REFERNCE_NO,EXTREFERNCE_NO,SERVICE_CODE,TXN_AMOUNT,CHANNEL,ACTION,CHANNEL from FILE_UPLOAD_UNSETTLE where STATUS='P' and REF_NUM='"+filerefno+"'");
					
					 serviceRS = servicePstmt.executeQuery();
					 
					
						
					 while(serviceRS.next())
						{
						
						 
						 	pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_UNSETTLE SET STATUS='I' WHERE EXTREFERNCE_NO=?");
							pstmt.setString(1, serviceRS.getString(3));
							pstmt.executeUpdate();
							pstmt.close();
							connection.commit();
							
						 JSONObject json1=null;
						 HashMap<String,String> hm= new HashMap<String,String>();
							hm.put("userid", serviceRS.getString(1));
							hm.put("channel", serviceRS.getString(6));
							hm.put("extpaymetrefno", serviceRS.getString(3));
							hm.put("walletpaymetrefno", serviceRS.getString(2));
							hm.put("txnamt", serviceRS.getString(5));
							hm.put("reqtype", serviceRS.getString(4));
							hm.put("action", serviceRS.getString(7));
							try {
								json1 = JSONObject.fromObject(SettlementServiceCall.agentfundReversalPostingsFileupload(hm));
								hm.clear();
								logger.debug("inside [AgentDAO][Settlement Approval] -- "+json1);
							}catch(Exception ee) {
								responseDTO.addError("Internal Error Occured While Executing.");
								ee.printStackTrace();
							}

							if((json1.getString("respcode")).equalsIgnoreCase("00")){
								
								
								pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_UNSETTLE SET STATUS='C',ERR_DESC=?,REC_SEQ=? WHERE EXTREFERNCE_NO=?");
								pstmt.setString(1, json1.getString("respdesc"));
								pstmt.setString(2, json1.getString("respcode"));
								pstmt.setString(3, serviceRS.getString(3));
								pstmt.executeUpdate();
								
								
								
								pstmt.close();
							}else {
								pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_UNSETTLE SET STATUS='F',ERR_DESC=?,REC_SEQ=? WHERE EXTREFERNCE_NO=?");
								pstmt.setString(1, json1.getString("respdesc"));
								pstmt.setString(2, json1.getString("respcode"));
								pstmt.setString(3, serviceRS.getString(3));
								pstmt.executeUpdate();
								
								
							}
							
							
						 
						}
					 connection.commit();
					 
					 servicePstmt = connection.prepareStatement("select STATUS,count(*) from FILE_UPLOAD_UNSETTLE where STATUS in ('C','F') and REF_NUM='"+filerefno+"' group by STATUS");
						
					 serviceRS = servicePstmt.executeQuery();
					 
					 json.put("C", "0");
					 json.put("F", "0");
						
					 while(serviceRS.next())
						{
						 json.put(serviceRS.getString(1), serviceRS.getString(2));
						}
					
					
					
				}else {
					
					pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_MASTER_HIST SELECT * FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER_HIST SET STATUS='R',CHECKER_ID=?,CHECKER_DATE=sysdate,REASON_MSG=? WHERE REF_NUM=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, reason);
					pstmt.setString(3, filerefno);

					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					
					pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_UNSETTLE_HIST SELECT * FROM FILE_UPLOAD_UNSETTLE WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_UNSETTLE_HIST SET STATUS='R' WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_UNSETTLE WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					connection.commit();
				}
				
				
				 
				//resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
				
				logger.info("Final Json Object ["+json+"]");
				
				lmtfeeDataMap.put("LMT_FEE_INFO", json);
				logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
				responseDTO.setData(lmtfeeDataMap);
		
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
	
public ResponseDTO fileuploadsuccrevApprovalAck(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


		HashMap<String, Object> serviceDataMap = null;
		HashMap<String, Object> lmtfeeDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		JSONArray lmtJsonArray = null;

		Connection connection = null;
		
		PreparedStatement pstmt = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;

		org.json.JSONObject jsonob=null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			
			lmtJsonArray = new JSONArray();
			lmtfeeDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();
			String filerefno=requestJSON.getString("filerefno");
			String requesttype=requestJSON.getString("requesttype");
			String reason=requestJSON.getString("reason");
			
			String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();

			JSONObject json = new JSONObject();
				
				connection.setAutoCommit(false);
			
				if(requesttype.contentEquals("Approval")) {
					
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE REF_NUM=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, filerefno);
					pstmt.executeUpdate();					
					pstmt.close();
					
					
					
					/* servicePstmt = connection.prepareStatement("select USER_ID,PAYMENT_REFERNCE_NO from FILE_UPLOAD_SUCCREV where STATUS='P' and REF_NUM='"+filerefno+"'");
					
					 serviceRS = servicePstmt.executeQuery();
					 
					
						
					 while(serviceRS.next())
						{
						
						 
						 	pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_SUCCREV SET STATUS='I' WHERE PAYMENT_REFERNCE_NO=?");
							pstmt.setString(1, serviceRS.getString(2));
							pstmt.executeUpdate();
							pstmt.close();
							connection.commit();
							
						 JSONObject json1=null;
						 HashMap<String,String> hm= new HashMap<String,String>();
							hm.put("userid", serviceRS.getString(1));
							hm.put("channel", serviceRS.getString(6));
							hm.put("extpaymetrefno", serviceRS.getString(3));
							hm.put("walletpaymetrefno", serviceRS.getString(2));
							hm.put("txnamt", serviceRS.getString(5));
							hm.put("reqtype", serviceRS.getString(4));
							hm.put("action", serviceRS.getString(7));
							try {
								json1 = JSONObject.fromObject(SettlementServiceCall.agentfundReversalPostingsFileupload(hm));
								hm.clear();
								logger.debug("inside [AgentDAO][Settlement Approval] -- "+json1);
							}catch(Exception ee) {
								responseDTO.addError("Internal Error Occured While Executing.");
								ee.printStackTrace();
							}

							if((json1.getString("respcode")).equalsIgnoreCase("00")){
								
								
								pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_SUCCREV SET STATUS='C',ERR_DESC=?,REC_SEQ=? WHERE PAYMENT_REFERNCE_NO=?");
								pstmt.setString(1, json1.getString("respdesc"));
								pstmt.setString(2, json1.getString("respcode"));
								pstmt.setString(3, serviceRS.getString(2));
								pstmt.executeUpdate();
								
								
								
								pstmt.close();
							}else {
								pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_SUCCREV SET STATUS='F',ERR_DESC=?,REC_SEQ=? WHERE PAYMENT_REFERNCE_NO=?");
								pstmt.setString(1, json1.getString("respdesc"));
								pstmt.setString(2, json1.getString("respcode"));
								pstmt.setString(3, serviceRS.getString(2));
								pstmt.executeUpdate();
								
								
							}
							
							
						 
						}*/
					 connection.commit();
					 
					 servicePstmt = connection.prepareStatement("select STATUS,count(*) from FILE_UPLOAD_SUCCREV where STATUS in ('C','F') and REF_NUM='"+filerefno+"' group by STATUS");
						
					 serviceRS = servicePstmt.executeQuery();
					 
					 json.put("C", "0");
					 json.put("F", "0");
						
					 while(serviceRS.next())
						{
						 json.put(serviceRS.getString(1), serviceRS.getString(2));
						}
					
					
					
				}else {
					
					pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_MASTER_HIST SELECT * FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER_HIST SET STATUS='R',CHECKER_ID=?,CHECKER_DATE=sysdate,REASON_MSG=? WHERE REF_NUM=?");
					pstmt.setString(1, makerid);
					pstmt.setString(2, reason);
					pstmt.setString(3, filerefno);

					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					
					/*pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_UNSETTLE_HIST SELECT * FROM FILE_UPLOAD_UNSETTLE WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_UNSETTLE_HIST SET STATUS='R' WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					
					pstmt.close();
					
					pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_UNSETTLE WHERE REF_NUM=?");
					pstmt.setString(1, filerefno);
					pstmt.executeUpdate();
					connection.commit();*/
				}
				
				
				 
				//resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
				
				logger.info("Final Json Object ["+json+"]");
				
				lmtfeeDataMap.put("LMT_FEE_INFO", json);
				logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
				responseDTO.setData(lmtfeeDataMap);
		
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


public ResponseDTO fileuploadterminalApprovalAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][gtAgentRegistrtionack]");


	HashMap<String, Object> serviceDataMap = null;
	HashMap<String, Object> lmtfeeDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	JSONArray lmtJsonArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null;
	
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	org.json.JSONObject jsonob=null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		lmtJsonArray = new JSONArray();
		lmtfeeDataMap = new HashMap<String, Object>();

		connection = DBConnector.getConnection();
		String filerefno=requestJSON.getString("filerefno");
		String requesttype=requestJSON.getString("requesttype");
		String reason=requestJSON.getString("reason");
		
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		JSONObject json = new JSONObject();
			
			connection.setAutoCommit(false);
		
			if(requesttype.contentEquals("Approval")) {
				
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE REF_NUM=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, filerefno);
				pstmt.executeUpdate();					
				pstmt.close();
				
				
				
				 servicePstmt = connection.prepareStatement("select USER_ID,TERMINAL_MAKE,MODEL_NUMBER,SERIAL_NUMBER from FILE_UPLOAD_TERMINAL where STATUS='P' and REF_NUM='"+filerefno+"'");
				
				 serviceRS = servicePstmt.executeQuery();
				 
				 String pcalling = "{call BULKPOSUPLOAD(?,?,?,?,?,?,?,?,?,?)}";
					
				 while(serviceRS.next())
					{
					
					 
					 	pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_TERMINAL SET STATUS='I' WHERE USER_ID=?");
						pstmt.setString(1, serviceRS.getString(1));
						pstmt.executeUpdate();
						pstmt.close();
						connection.commit();
						
						cstmt = connection.prepareCall(pcalling);
						cstmt.setString(1, serviceRS.getString(1));
						cstmt.setString(2, serviceRS.getString(2));
						cstmt.setString(3, serviceRS.getString(3));
						cstmt.setString(4, serviceRS.getString(4)); 
						cstmt.setString(5, makerid);
						cstmt.setString(6, remoteip);
						cstmt.setString(7, "");
						cstmt.setString(8, filerefno);
						cstmt.registerOutParameter(9, Types.INTEGER);
						cstmt.registerOutParameter(10, Types.VARCHAR);
						cstmt.executeQuery();
						
					 

						if((cstmt.getString(10)).equalsIgnoreCase("SUCCESS")){
							
							
							pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_TERMINAL SET STATUS='C',REC_SEQ=?,ERR_DESC=? WHERE USER_ID=?");
							pstmt.setString(1, ""+cstmt.getInt(9));
							pstmt.setString(2, cstmt.getString(10));
							pstmt.setString(3, serviceRS.getString(1));
							pstmt.executeUpdate();
							
							
							
							pstmt.close();
						}else {
							pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_TERMINAL SET STATUS='F',REC_SEQ=?,ERR_DESC=? WHERE USER_ID=?");
							pstmt.setString(1, ""+cstmt.getInt(9));
							pstmt.setString(2, cstmt.getString(10));
							pstmt.setString(3, serviceRS.getString(1));
							pstmt.executeUpdate();
							
							
						}
						
						
					 
					}
				 connection.commit();
				 
				 servicePstmt = connection.prepareStatement("select STATUS,count(*) from FILE_UPLOAD_TERMINAL where STATUS in ('C','F') and REF_NUM='"+filerefno+"' group by STATUS");
					
				 serviceRS = servicePstmt.executeQuery();
				 
				 json.put("C", "0");
				 json.put("F", "0");
					
				 while(serviceRS.next())
					{
					 json.put(serviceRS.getString(1), serviceRS.getString(2));
					}
				
				
				
			}else {
				
				pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_MASTER_HIST SELECT * FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER_HIST SET STATUS='R',CHECKER_ID=?,CHECKER_DATE=sysdate,REASON_MSG=? WHERE REF_NUM=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, reason);
				pstmt.setString(3, filerefno);

				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				
				pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_TERMINAL_HIST SELECT * FROM FILE_UPLOAD_TERMINAL WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_TERMINAL_HIST SET STATUS='R' WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_TERMINAL WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				connection.commit();
			}
			
			
			 
			//resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+json+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", json);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);
	
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


public ResponseDTO fileuploadPreEnrollementApprovalAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][fileuploadPreEnrollementApprovalAck]");


	HashMap<String, Object> serviceDataMap = null;
	HashMap<String, Object> lmtfeeDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	JSONArray lmtJsonArray = null;

	Connection connection = null;
	
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null;
	
	PreparedStatement servicePstmt = null;
	ResultSet serviceRS = null;

	org.json.JSONObject jsonob=null;
	String ip=null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		
		lmtJsonArray = new JSONArray();
		lmtfeeDataMap = new HashMap<String, Object>();

		connection = DBConnector.getConnection();
		String filerefno=requestJSON.getString("filerefno");
		String requesttype=requestJSON.getString("requesttype");
		String reason=requestJSON.getString("reason");
		
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		String remoteip=requestJSON.getString("remoteip");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();

		JSONObject json = new JSONObject();
			
			connection.setAutoCommit(false);
		
			if(requesttype.contentEquals("Approval")) {
				
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER SET STATUS='C',CHECKER_ID=?,CHECKER_DATE=sysdate WHERE REF_NUM=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, filerefno);
				pstmt.executeUpdate();					
				pstmt.close();
				
				/*pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_PREENROLMENT SET STATUS='S' WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();					
				pstmt.close();
				
				json.put("C", "Request Processing");
				json.put("F", "Request Processing");*/
				
				ip = requestJSON.getString("remoteip");
				String pcalling = "{call PONBOARDCUSTOMERTRG(?,?,?,?,?)}";
				cstmt = connection.prepareCall(pcalling);
				cstmt.setString(1, filerefno); 
				cstmt.setString(2, requestJSON.getString(CevaCommonConstants.MAKER_ID));  
				cstmt.setString(3, ip); 
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.executeQuery();
				
				json.put("C", (cstmt.getString(5)).split(",")[0]);
				json.put("F", (cstmt.getString(5)).split(",")[1]);
				
				
				connection.commit();
				
				}else {
				
				pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_MASTER_HIST SELECT * FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_MASTER_HIST SET STATUS='R',CHECKER_ID=?,CHECKER_DATE=sysdate,REASON_MSG=? WHERE REF_NUM=?");
				pstmt.setString(1, makerid);
				pstmt.setString(2, reason);
				pstmt.setString(3, filerefno);

				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_MASTER WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				
				pstmt = connection.prepareStatement("INSERT INTO FILE_UPLOAD_PREENROLMENT_HIST SELECT * FROM FILE_UPLOAD_PREENROLMENT WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE FILE_UPLOAD_PREENROLMENT_HIST SET STATUS='R' WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = connection.prepareStatement("DELETE FROM FILE_UPLOAD_PREENROLMENT WHERE REF_NUM=?");
				pstmt.setString(1, filerefno);
				pstmt.executeUpdate();
				connection.commit();
			}
			
			
			 
			//resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			
			logger.info("Final Json Object ["+json+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", json);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);
	
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
	
	
	public ResponseDTO fileUploadResult(RequestDTO requestDTO) {

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
		
			sb.append("SELECT (select count(*) from FILE_UPLOAD_MASTER where STATUS='C' AND F_TYPE='PREENROLMENT'),");
			sb.append("(select count(*) from FILE_UPLOAD_MASTER_HIST where STATUS='R' AND F_TYPE='PREENROLMENT')  FROM DUAL");
			
			
				
				getlmtfeePstmt = connection.prepareStatement(sb.toString());

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("FUNDING_COMPLETED", getlmtfeeRs.getString(1));
					json.put("FUNDING_REJECTED", getlmtfeeRs.getString(2));
					
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
	public ResponseDTO fileUploadResultdetails(RequestDTO requestDTO) {

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
			
			System.out.println("kailash here :: "+application);
		
			if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
				
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy'),MAKER_ID,to_char(CHECKER_DATE,'dd-mm-yyyy'),CHECKER_ID from file_upload_master where status='C' and f_type='PREENROLMENT'";   

			}else if(application.equalsIgnoreCase("FUNDING_REJECTED")){
				qrey="select REF_NUM,f_name,NUM_OF_RECORD,to_char(UPLOAD_DATE,'dd-mm-yyyy'),MAKER_ID,to_char(CHECKER_DATE,'dd-mm-yyyy'),CHECKER_ID from file_upload_master_hist where status='R' and f_type='PREENROLMENT'";   

			}
				
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("REF_NUM", getlmtfeeRs.getString(1));
					json.put("f_name", getlmtfeeRs.getString(2));
					json.put("NUM_OF_RECORD", getlmtfeeRs.getString(3));
					json.put("UPLOAD_DATE", getlmtfeeRs.getString(4));
					json.put("MAKER_ID", getlmtfeeRs.getString(5));
					json.put("CHECKER_DATE", getlmtfeeRs.getString(6));
					json.put("CHECKER_ID", getlmtfeeRs.getString(7));
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
	
	public ResponseDTO fileUploadResultdetailsview(RequestDTO requestDTO) {

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
		String refno="";


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
			refno=requestJSON.getString("refno");
			
			System.out.println("kailash here :: "+application);
		
			if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
				
				qrey="select ACCOUNT_NO,NVL(CUST_ID,' '),NVL(MOBILE_NUMBER,' '),decode(STATUS,'S','Success','F','Failed','Processing'),NVL(REC_SEQ,''),NVL(ERR_DESC,SERVICE_RESPONSE),SERVICE_RESPONSE from FILE_UPLOAD_PREENROLMENT where REF_NUM='"+refno+"'"; 
				
			}else if(application.equalsIgnoreCase("FUNDING_REJECTED")){
				qrey="select ACCOUNT_NO,NVL(CUST_ID,''),NVL(MOBILE_NUMBER,''),SERVICE_CODE,TXN_AMOUNT,ACTION,'Rejected' from FILE_UPLOAD_PREENROLMENT_HIST where status='R' and REF_NUM='"+refno+"' ";   

			}
				
				getlmtfeePstmt = connection.prepareStatement(qrey);

				getlmtfeeRs = getlmtfeePstmt.executeQuery();

				while (getlmtfeeRs.next()) {

					json = new JSONObject();
					json.put("USER_ID", getlmtfeeRs.getString(1));
					json.put("TXN_REFERNCE_NO", getlmtfeeRs.getString(2));
					json.put("EXTREFERNCE_NO", getlmtfeeRs.getString(3));
					json.put("SERVICE_CODE", getlmtfeeRs.getString(4));
					json.put("TXN_AMOUNT", getlmtfeeRs.getString(5));
					json.put("ACTION", getlmtfeeRs.getString(6));
					lmtJsonArray.add(json);
				}

				getlmtfeePstmt.close();
				getlmtfeeRs.close();
				
				if(application.equalsIgnoreCase("FUNDING_COMPLETED")){
					String  settstr="select ACCOUNT_NO,NVL(CUST_ID,''),NVL(MOBILE_NUMBER,''),decode(STATUS,'S','Success','F','Failed','Processing'),NVL(ERR_DESC,SERVICE_RESPONSE) from FILE_UPLOAD_PREENROLMENT where REF_NUM='"+refno+"'";
				
				String heading="ACCOUNT NO,CUSTOMER ID,MOBILE NUMBER,STATUS,RESPONSE MESSAGE";
				
				CSVReportGeneration.perform("file_result", connection, settstr,heading);
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
