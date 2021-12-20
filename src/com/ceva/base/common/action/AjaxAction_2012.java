package com.ceva.base.common.action;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.AjaxActionBean;
import com.ceva.base.common.dao.AjaxDAO;
import com.ceva.base.common.dao.ICTAdminDAO;
import com.ceva.base.common.dao.MerchantAdminDetailsDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.unionbank.channel.SettlementServiceCall;
import com.opensymphony.xwork2.ActionSupport;

public class AjaxAction extends ActionSupport  {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AjaxAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	private InputStream inputStream;

	private String result;
	private String region;
	private String merchantId;
	private String merchantIdKey;
	private String storeId;
	private String sid;
	private String merchantName;
	private String method;
	private String accounttype;
	private String service;
	private String serialNumber;
	private String authcode;
	private String status;
	private String actionname;
	
	private String product;
	private String type;
	private String channel;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionname() {
		return actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	@Autowired
	private AjaxActionBean ajaxActionBean;

	public String getMerchantAdminId() {
		return merchantAdminId;
	}

	public void setMerchantAdminId(String merchantAdminId) {
		this.merchantAdminId = merchantAdminId;
	}

	private String merchantAdminId;

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String execute() throws Exception {
		logger.debug("Inside Execute method[" + method + "]");
		if (method.equalsIgnoreCase("checkTransactionType")) {
			result = checkTransactionType();
		} else if (method.equalsIgnoreCase("getMerchantAdminDetails")) {
			result = fetchMerchantAdminDetails();
		}

		logger.debug("Before returning result[" + result + "]");
		return result;
	}

	public String retriveHeadOffice() throws Exception {

		logger.debug("Inside retriveHeadOffice Region[" + region + "]");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.REGION_ID, region);
			logger.debug("Response JSON [" + responseJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Reques DTO [" + requestDTO + "]");
			ictAdminDAO = new ICTAdminDAO();
			responseDTO = ictAdminDAO.retriveHeadOffice(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.HEADOFFICE_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in retriveHeadOffice [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ictAdminDAO = null;
		}

		return SUCCESS;
	}
	
	public String fetchServise() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);	
			System.out.println("kailash here ::: "+type);
			
				connection = DBConnector.getConnection();
				
				if(type.equalsIgnoreCase("LIMIT")){
					entityQry = "select SERVICE_CODE||'-'||SERVICE_NAME from MOB_CHANNEL_MAP where ASSIGN_LIMIT='YES' AND PRODUCT='"+product.split("-")[0]+"' AND CHANNEL_NAME='"+product.split("-")[1]+"' "
							+ " AND SERVICE_CODE not in (select TXN_CODE from LIMIT_FEE_DETAILS LFD,LIMIT_FEE_MASTER LFM WHERE LFM.REF_NUM=LFD.REF_NUM AND PRODUCT='"+product.split("-")[0]+"' AND USAGE_TYPE='L' AND CHANNEL='"+product.split("-")[1]+"') order by SERVICE_NAME";
				}else if(type.equalsIgnoreCase("FEE")){
					entityQry = "select SERVICE_CODE||'-'||SERVICE_NAME from MOB_CHANNEL_MAP where ASSIGN_FEE='YES' AND PRODUCT='"+product.split("-")[0]+"' AND CHANNEL_NAME='"+product.split("-")[1]+"' "
							+ " AND SERVICE_CODE not in (select TXN_CODE from LIMIT_FEE_DETAILS LFD,LIMIT_FEE_MASTER LFM WHERE LFM.REF_NUM=LFD.REF_NUM AND PRODUCT='"+product.split("-")[0]+"' AND USAGE_TYPE='F' AND CHANNEL='"+product.split("-")[1]+"') order by SERVICE_NAME";
				}else if(type.equalsIgnoreCase("CHANNEL-LIMIT")){
					entityQry = "select DISTINCT CHANNEL_NAME from MOB_CHANNEL_MAP where ASSIGN_LIMIT='YES' AND PRODUCT='"+product+"' ";
				}else if(type.equalsIgnoreCase("CHANNEL-FEE")){
					entityQry = "select DISTINCT CHANNEL_NAME from MOB_CHANNEL_MAP where ASSIGN_FEE='YES' AND PRODUCT='"+product+"' ";
				}else if(type.equalsIgnoreCase("OPERATOR")){
					entityQry = "select OPERATORID||'-'||OPERATORNAME from MN_OPERATORS where  CHANNEL in ('"+product+"','ALL') ";
				}else if(type.equalsIgnoreCase("BRANCH-ACTIVATIES")){
					entityQry = "select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND UI.CLUSTER_ID='"+product.split("-")[0]+"'";
				}
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
	public String fetchiso() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);	
			
			
				connection = DBConnector.getConnection();
				
				
					

				searchPstmt = connection.prepareStatement("select INTERNATIONAL_DIALING from COUNTRY_ISO_CODE_TBL where INTERNATIONAL_DIALING is not null");
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}

	
	
public String fetchfraud() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);	
			//System.out.println("kailash here ::: "+product);
			
				connection = DBConnector.getConnection();
				
				
					entityQry = "select FRAUD_ID from FRAUD_ASSIGN where  PRODUCT_CODE ='"+product+"' ";
				
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=searchRS.getString(1)+":"+region;
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
	public String fetchBillerServise() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		
			
				connection = DBConnector.getConnection();
				
				if(type.equalsIgnoreCase("Biller_Service")){
					
					JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getBillers());
					JSONArray jsonarray =  JSONArray.fromObject(json1.get("billersdata"));
					Iterator iterator = jsonarray.iterator();
					TreeSet<String> al=new TreeSet<String>();
				       while (iterator.hasNext()) {
				    	   JSONObject jsonobj=(JSONObject)iterator.next();
					       al.add("CUSTPAYBILL-"+((jsonobj).get("shortName")).toString()); 
					       
				       }
				       
				   /* entityQry = "select SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER where SERVICE_TYPE='BILLER' AND SERVICE_CODE<>'CUSTPAYBILL'";
						searchPstmt = connection.prepareStatement(entityQry);
					searchRS = searchPstmt.executeQuery();
					while (searchRS.next()){
						 al.add(searchRS.getString(1)); 
					}*/
				
					  
				     Iterator<String> itr=al.iterator();  
				       while(itr.hasNext()){  
				       // System.out.println(itr.next());
				        region=region+":"+itr.next();
				       }   
					
					
					
				}else if(type.equalsIgnoreCase("Other")){
					entityQry = "select SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER where STATUS=2 AND  SERVICE_TYPE='OTHER'";
					searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					region=region+":"+searchRS.getString(1);
				}
				}else if(type.equalsIgnoreCase("BILLERID")){
					entityQry = "select lpad(COUNT(*)+1,5,'00') from BILLER_REGISTRATION";
					searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					region=searchRS.getString(1);
				}
				}else if(type.equalsIgnoreCase("MERCHANTID")){
					entityQry = "select lpad(COUNT(*)+1,5,'00') from ORG_MASTER";
					searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					region=searchRS.getString(1);
				}
				}
				

				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}

	

	public String fetchSubcategories() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		
			
				connection = DBConnector.getConnection();
				
				
					entityQry = "select SUB_CATEGORY_ID||'-'||SUB_CATEGORY_DESC,SUB_CATEGORY_DESC from ONLINE_SUB_CATEGORY_DETAILS where CATEGORY_ID='"+product+"' order by SUB_CATEGORY_DESC";
			

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1)+","+searchRS.getString(2);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}

	

	public String checkMerchantId() {

		logger.debug("Inside CheckMerchantId MerchantId[" + merchantId + "]");
		ArrayList<String> errors = null;
		AjaxDAO ictAdminDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantId);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO[" + requestDTO + "]");
			ictAdminDAO = new AjaxDAO();
			responseDTO = ictAdminDAO.checkMerchantId(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_CHECK_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkMerchantId [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ictAdminDAO = null;
		}

		return SUCCESS;
	}

	public String fetchMerchantAdminDetails() {
		logger.debug("Inside MerchantAdminId...");
		MerchantAdminDetailsDAO ajaxDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put("MERCHANT_ADMIN", merchantAdminId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ajaxDAO = new MerchantAdminDetailsDAO();
			responseDTO = ajaxDAO.getMerchantAdminDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"ADMIN_INFO");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in MerchantAdminId [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");

		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return SUCCESS;
	}

	public String generateMerchantId() {

		logger.debug("Inside GenerateMerchantId  Merchantname ["
				+ ajaxActionBean.getMerchantName() + "]");
		ArrayList<String> errors = null;
		AjaxDAO ictAdminDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_NAME,
					merchantName);
			logger.debug("Response JSON [" + responseJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new AjaxDAO();
			responseDTO = ictAdminDAO.generateMerchantId(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_INFO);

				/*if (this.ajaxActionBean == null) {
					ajaxActionBean = new AjaxActionBean();
				}
				
				ajaxActionBean.setResponseJSON1(responseJSON);*/

				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ictAdminDAO = null;
		}

		return SUCCESS;
	}

	public String fetchStores() {
		logger.debug("Inside GetStores MerchantId[" + merchantId + "]");
		ArrayList<String> errors = null;
		AjaxDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new AjaxDAO();
			responseDTO = merchantDAO.getStores(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return SUCCESS;
	}
	public String fetchTerminal() {
		logger.debug("Inside GetStores MerchantId[" + merchantId + "]");
		ArrayList<String> errors = null;
		AjaxDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			
			requestDTO = new RequestDTO();
			
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new AjaxDAO();
			responseDTO = merchantDAO.getTerminalID(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.STORE_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}
		
		return SUCCESS;
	}
	
	public String fetchusers() {
		logger.debug("Inside GetStores MerchantId[" + merchantId + "]");
		ArrayList<String> errors = null;
		AjaxDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			
			requestDTO = new RequestDTO();
			
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantId);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new AjaxDAO();
			responseDTO = merchantDAO.retrieveusers(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.STORE_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}
		
		return SUCCESS;
	}

	public String fetchTerminals() {
		logger.debug("Inside fetchTerminals... ");
		AjaxDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new AjaxDAO();
			responseDTO = merchantDAO.getTerminals(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.TERMINAL_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in fetchTerminals [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return SUCCESS;
	}

	public String checkTransactionType() {
		logger.debug("Inside checkTransactionType.... ");
		AjaxDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.ACCOUNT_TYPE, accounttype);
			requestJSON.put(CevaCommonConstants.SERVICE_TYPE, service);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new AjaxDAO();
			responseDTO = ictAdminDAO.checkTransactionType(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_CHECK_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkTransactionType [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ictAdminDAO = null;
		}

		return SUCCESS;
	}
	
	public String fetchMerchUserInfo() {
		
		AjaxDAO ajaxDAO = null;
		
		try {
		
			logger.debug("inside [fetchMerchUserInfo][merchantID::"+merchantId+"]");
			requestJSON=new JSONObject();
			responseJSON=new JSONObject();
			requestDTO=new RequestDTO();
			responseDTO= new ResponseDTO();
		
			requestJSON.put("merchantID", merchantId);
			logger.debug("inside [fetchStudentInfo][merchantID::"+merchantId+"]");
			requestDTO.setRequestJSON(requestJSON);	
			ajaxDAO = new AjaxDAO();
		    responseDTO = ajaxDAO.fetchMerchUsersInfo(requestDTO);
		   
			if (responseDTO != null && responseDTO.getErrors().size()==0) {		
				responseJSON=(JSONObject) responseDTO.getData().get("MERCH_USER_LIST");
				logger.debug("[fetchMerchUserInfo][responseJSON:::::"+responseJSON+"]");
			}else{			
				ArrayList<String> errors1=(ArrayList<String>) responseDTO.getErrors();
				for(int i=0;i<errors1.size();i++){
					addActionError(errors1.get(i));
				}
			}
	} catch (Exception e) {
		
		logger.debug("Exception in Get   fetchMerchUserInfo ["
				+ e.getMessage() + "]");
	} finally {
		
	}
	return SUCCESS;
	}
	
	public String checkTerminalSerialNumber() {

		logger.debug("Inside Check Serial Number[" + serialNumber + "]");
		ArrayList<String> errors = null;
		AjaxDAO ictAdminDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("SERIAL_NUMBER", serialNumber);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO[" + requestDTO + "]");
			ictAdminDAO = new AjaxDAO();
			responseDTO = ictAdminDAO.checkSerialNumber(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("SERIAL_CHECK_INFO");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkMerchantId [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ictAdminDAO = null;
		}

		return SUCCESS;
	}
	
	
	public String ajaxdata() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		
		
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			

			
			requestDTO.setRequestJSON(requestJSON);		
System.out.println(authcode);
System.out.println(status);
System.out.println(actionname);
			//For Ajax Hit
			/*String filename=receiver.split(":")[1];
			String filedate=receiver.split(":")[0];*/
				logger.debug("Inside Ajax Hit...................... ");
				//logger.debug("File exceptions search page action...................... ");
				PreparedStatement searchPstmt = null;
				ResultSet searchRS = null;
				Connection connection = null;
				String entityQry="";
				connection = DBConnector.getConnection();
				
				
				
				entityQry = "select distinct MA.CUSTOMER_CODE   from MOB_CUSTOMER_MASTER_TEMP MA,AUTH_PENDING AP,MOB_CONTACT_INFO_TEMP MT,MOB_ACCT_DATA_TEMP MD "
						+	"where MA.REF_NUM=AP.REF_NUM  "
                +	"and MA.ID=MT.CUST_ID "
                +	"and MA.ID=MD.CUST_ID "
                +	"and MA.REF_NUM=MD.REF_NUM "
                +	"AND (MT.ID=(select max(A.ID) from MOB_CONTACT_INFO_TEMP A where A.CUST_ID=MT.CUST_ID) "
                +	"OR MD.ID=(select max(B.ID) from MOB_ACCT_DATA_TEMP B where B.CUST_ID=MD.CUST_ID)) "
                +	"and MA.AUTH_FLAG='AUP' "
                +	"and AP.AUTH_CODE='NEWACCAUTH' ";

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}

	public String ajaxProduct() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				//System.out.println("kailahs ::: "+sid);
				connection = DBConnector.getConnection();
				
				
				
				entityQry = "select PT.PRD_CODE||'-'||PT.PRD_NAME||'@'||PT.PRD_CODE||'@'||PT.APPLICATION   from PRD_DETAILS PD,PRODUCT PT WHERE PD.PRD_CODE=PT.PRD_CODE AND PD.PRD_CODE is not null AND PD.LIMIT_CODE is not null AND PT.STATUS='A' "+
				"AND PT.PRD_CODE not in (select PRODUCT_CODE from PRODUCT_PERMISSION WHERE BRANCH in (select trim(CLUSTER_ID) from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where UI.COMMON_ID=ULC.COMMON_ID AND ULC.LOGIN_USER_ID='"+sid+"') or BRANCH='ALL')";

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	public String ajaxBranch() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				//System.out.println("kailahs ::: "+sid);
				connection = DBConnector.getConnection();
				
				
				
				entityQry = "SELECT BRANCH_CODE||'-'||RM_CODE FROM RMCODE_CONFIG";

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
public String ajaxBranchCode() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				System.out.println("kailahs ::: "+sid);
				connection = DBConnector.getConnection();
				
				if(sid.equalsIgnoreCase("BRANCH")) {
					
					entityQry = "SELECT BANK_CLUSTER||'@'||AREA||'@'||CLUSTER_ID||'@'||CLUSTER_NAME FROM CLUSTER_TBL where DISPLAY_STATUS='Y'";

					
				}
				
				if(sid.equalsIgnoreCase("STATE")) {
					
					entityQry = "SELECT STATE_CODE||'-'||STATE_NAME FROM STATE_MASTER";

					
				}
				
				if(sid.equalsIgnoreCase("STATESEARCH")) {
					//System.out.println("kailahs ::: "+sid+"--"+serialNumber);
					entityQry = "SELECT GOVT_NAME FROM LOCAL_GOVT_MASTER where STATE_CODE='"+serialNumber+"'";

					
				}
				
				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
	public String ajaxMerchant() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				
				connection = DBConnector.getConnection();
				
				
				
				entityQry = "select KEY_VALUE||'-'||STATUS||'@'||KEY_VALUE||'@'||STATUS   from CONFIG_DATA WHERE KEY_GROUP='MERCHANTPAYMENT'";
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	public String ajaxSuperProduct() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				
				connection = DBConnector.getConnection();
				
				
				
				entityQry = "select PT.PRD_CODE||'-'||PT.PRD_NAME||'@'||PT.PRD_CODE||'@'||PT.PRODUCT_TYPE   from PRD_DETAILS PD,PRODUCT PT WHERE PD.PRD_CODE=PT.PRD_CODE AND PD.PRD_CODE is not null AND PD.LIMIT_CODE is not null AND PT.STATUS='A' AND PT.APPLICATION='Agent'  ";
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
public String ajaxSuperAgent() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				
				connection = DBConnector.getConnection();
				
				if(sid.equalsIgnoreCase("SUPERADMIN")){
					entityQry = "select TELCO_TYPE||'-'||PRODUCT||'@'||TELCO_TYPE||'-'||PRODUCT   from SUPER_AGENT  WHERE STATUS='A'";
				}
				if(sid.equalsIgnoreCase("AGENCY")){
					entityQry = "select ACM.CUSTOMER_CODE||'-'||ACI.MOBILE_NUMBER||'-'||ACM.FIRST_NAME||'@'||ACI.MOBILE_NUMBER||'-'||ACM.FIRST_NAME from AGENT_CONTACT_INFO ACI,AGENT_CUSTOMER_MASTER ACM where ACM.id=ACI.CUST_ID AND ACM.AGENCY_TYPE='AGENT' AND ACM.SUPER_ADM='ALEDIN_AGENCY'";
					
				}
				
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}




public String ajaxTransactioninfo() {
	
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			PreparedStatement searchPstmt = null;
			ResultSet searchRS = null;
			
			PreparedStatement cntPstmt = null;
			ResultSet cntRS = null;
			
			Connection connection = null;
			String entityQry="";
			String cntQry="";
			StringBuilder sb=new StringBuilder();
			region="";
						try {
						requestJSON = new JSONObject();
						responseJSON = new JSONObject();
						requestDTO = new RequestDTO();
						responseDTO = new ResponseDTO();
						
							requestDTO.setRequestJSON(requestJSON);		
							connection = DBConnector.getConnection();
							/*System.out.println(type);
							System.out.println(channel);
							System.out.println(sid);*/
							
							
							entityQry="select RESPONSEMESSAGE,DEBITNARRATION from FUND_TRANSFER_TBL where PAYMENTREFERENCE='"+sid+"' and rownum=1";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							while (searchRS.next()){
								sb.append("<tr ><td>Core Bank Response Message</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr ><td>Narration</td><td>"+searchRS.getString(2)+"</td></tr>");
								
							}
							searchPstmt.close();
							searchRS.close();
							
							
							if(type.equalsIgnoreCase("Recharge") && channel.equalsIgnoreCase("USSD")){
								
								cntQry="select COUNT(*) from RESER_FUND_TRANFER where TRANSREFNO='"+sid+"' and rownum=1";
								
								cntPstmt = connection.prepareStatement(cntQry);
								cntRS = cntPstmt.executeQuery();
								if (cntRS.next()){
									if(cntRS.getString(1).equalsIgnoreCase("1")){
								
									entityQry="select SOURCEIDENTIFIER,TARGETIDENTIFIER,CLIENTTXNREF,RAASTXNREF from RESER_FUND_TRANFER where TRANSREFNO='"+sid+"' and rownum=1";	
									searchPstmt = connection.prepareStatement(entityQry);
									searchRS = searchPstmt.executeQuery();
									while (searchRS.next()){
										sb.append("<tr ><td>Source Mobile No</td><td>"+searchRS.getString(1)+"</td></tr>");
										sb.append("<tr ><td>Recharge Mobile No</td><td>"+searchRS.getString(2)+"</td></tr>");
										sb.append("<tr ><td>Client Txn Ref</td><td>"+searchRS.getString(3)+"</td></tr>");
										sb.append("<tr ><td>RAAS Txn Ref</td><td>"+searchRS.getString(4)+"</td></tr>");
									}
								  }
								}
								
								cntPstmt.close();
								cntRS.close();
								searchPstmt.close();
								searchRS.close();
								
								cntQry="select COUNT(*) from REV_RECHARGE_TRANS where PAYMET_REF_NO='"+sid+"' and STATUS is not null and rownum=1";
								
								cntPstmt = connection.prepareStatement(cntQry);
								cntRS = cntPstmt.executeQuery();
								if (cntRS.next()){
									if(cntRS.getString(1).equalsIgnoreCase("1")){
								
									entityQry="select DECODE(STATUS,'O','Reversal Pending','MP','Reversal Processing','C','Reversal Completed',' ') from REV_RECHARGE_TRANS where PAYMET_REF_NO='"+sid+"'  and STATUS is not null and rownum=1";	
									searchPstmt = connection.prepareStatement(entityQry);
									searchRS = searchPstmt.executeQuery();
									while (searchRS.next()){
										sb.append("<tr ><td>Clickatell Status</td><td>"+searchRS.getString(1)+"</td></tr>");
										
									}
									
									
									if(cntRS.getString(1).equalsIgnoreCase("0")){
										sb.append("<tr ><td>Clickatell Status</td><td>Recharge Successful</td></tr>");
									}
									
								  }
								}
								
								
							}
							
							
							if(type.equalsIgnoreCase("Recharge") && channel.equalsIgnoreCase("MOBILE")){
								
								cntQry="select COUNT(*) from PAYBILL_TRANS_TBL where FTID='"+sid+"' and rownum=1";
								
								cntPstmt = connection.prepareStatement(cntQry);
								cntRS = cntPstmt.executeQuery();
								if (cntRS.next()){
									if(cntRS.getString(1).equalsIgnoreCase("1")){
								
									entityQry="select PB_REQUEST,PB_RESPONSE from PAYBILL_TRANS_TBL where FTID='"+sid+"' and rownum=1";	
									searchPstmt = connection.prepareStatement(entityQry);
									searchRS = searchPstmt.executeQuery();
									while (searchRS.next()){
										JSONObject JSO = JSONObject.fromObject(searchRS.getString(1));
										sb.append("<tr ><td>Recharge Mobile No</td><td>"+JSO.getString("customerMobile")+"</td></tr>");
										
										JSONObject JSO1 = JSONObject.fromObject(searchRS.getString(2));
										sb.append("<tr ><td>Paybill Respose Message</td><td>"+JSO1.getString("responseMessage")+"</td></tr>");
										
									}
								  }
								}
								
								cntPstmt.close();
								cntRS.close();
								searchPstmt.close();
								searchRS.close();
								
							}
							
							
							
							if(type.equalsIgnoreCase("Pay Bills")){
								
								cntQry="select COUNT(*) from PAYBILL_TRANS_TBL where FTID='"+sid+"' and rownum=1";
								
								cntPstmt = connection.prepareStatement(cntQry);
								cntRS = cntPstmt.executeQuery();
								if (cntRS.next()){
									if(cntRS.getString(1).equalsIgnoreCase("1")){
								
									entityQry="select PB_REQUEST,PB_RESPONSE from PAYBILL_TRANS_TBL where FTID='"+sid+"' and rownum=1";	
									searchPstmt = connection.prepareStatement(entityQry);
									searchRS = searchPstmt.executeQuery();
									while (searchRS.next()){
										JSONObject JSO = JSONObject.fromObject(searchRS.getString(1));
										sb.append("<tr ><td>Paybill Narration</td><td>"+JSO.getString("narration")+"</td></tr>");
										
										JSONObject JSO1 = JSONObject.fromObject(searchRS.getString(2));
										sb.append("<tr ><td>Paybill Respose Message</td><td>"+JSO1.getString("responseMessage")+"</td></tr>");
										
									}
								  }else{
									  sb.append("<tr ><td>Paybill Response Message</td><td>Some thing went wrong</td></tr>"); 
								  }
								}
								
								cntPstmt.close();
								cntRS.close();
								searchPstmt.close();
								searchRS.close();
								
							}
							
							
							if(type.equalsIgnoreCase("Fund Transfer to Other Banks")){
								
								cntQry="select COUNT(*) from FTO_TRANSACTIONS_TBL where FTID='"+sid.replace("~C", "")+"' and rownum=1";
								
								cntPstmt = connection.prepareStatement(cntQry);
								cntRS = cntPstmt.executeQuery();
								if (cntRS.next()){
									if(cntRS.getString(1).equalsIgnoreCase("1")){
								
									entityQry="select FTO_RESPONSE from FTO_TRANSACTIONS_TBL where FTID='"+sid.replace("~C", "")+"' and rownum=1";	
									searchPstmt = connection.prepareStatement(entityQry);
									searchRS = searchPstmt.executeQuery();
									while (searchRS.next()){
										JSONObject JSO = JSONObject.fromObject(searchRS.getString(1));
										sb.append("<tr ><td>NIP Response Message</td><td>"+JSO.getString("responseMessage")+"</td></tr>");
										
										
									}
								  }else{
									  sb.append("<tr ><td>NIP Response Message</td><td>Some thing went wrong</td></tr>"); 
								  }
								}
								
								cntPstmt.close();
								cntRS.close();
								searchPstmt.close();
								searchRS.close();
								
							}
							
							
							/*entityQry = "select PT.PRD_CODE||'-'||PT.PRD_NAME||'@'||PT.PRD_CODE||'@'||PT.APPLICATION   from PRD_DETAILS PD,PRODUCT PT WHERE PD.PRD_CODE=PT.PRD_CODE AND PD.PRD_CODE is not null AND PD.LIMIT_CODE is not null AND PT.STATUS='A'";
								
							
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							JSONObject json = null;
							while (searchRS.next()){
								json = new JSONObject();
								json.put("file",searchRS.getString(1) );
								region=region+":"+searchRS.getString(1);
							}
							*/
							
							region=sb.toString();
							
							logger.debug("Reqion value [" + region + "]");
							return "success";
							
							
						} catch (Exception e) {
							result = "fail";
							logger.debug("Exception in search ["+e.getMessage()+"]");
							addActionError("Internal error occured.");
						} finally{
						try{
							
							cntPstmt.close();
							cntRS.close();
							searchPstmt.close();
							searchRS.close();
							connection.close();
							
							}catch(Exception e){
							
							}
							requestDTO = null;
							responseDTO = null;
							requestJSON = null;
							
							messages = null;
							errors = null;
						}
			
			return result;

}

public String ajaxSettlementinfo() {
	
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
	
	String entityQry="";
	String cntQry="";
	StringBuilder sb=new StringBuilder();
	region="";
				try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
					requestDTO.setRequestJSON(requestJSON);		
					
					HashMap<String,String> hm=new HashMap<String,String>();
					String str[]=type.split("@");
					for(int i=0;i<str.length;i++) {
						hm.put(str[i].split("-")[0], str[i].split("-")[1]);	
					}
					hm.put("type", sid);
					JSONObject json1 = JSONObject.fromObject(SettlementServiceCall.agentfundFetchdata(hm));
					//System.out.println("kailash here :: "+json1);
					if((json1.getString("respcode")).equalsIgnoreCase("00")) {
						//sb.append(json1.getString("transdataheader"));
						JSONObject json2 = JSONObject.fromObject(json1.getString("transdataheader"));
						JSONObject json3 = JSONObject.fromObject(json1.getString("transdata"));
						for (Object key : json2.keySet()) {
							String keyStr = (String)key;
					        Object keyvalue = json2.get(keyStr);

					        //Print key and value
					        //System.out.println("key: "+ keyStr + " value: " + keyvalue+" details : "+json3.getString(keyStr));
					        sb.append("<tr ><td>"+keyvalue+"</td><td>"+json3.getString(keyStr)+"</td></tr>");
						}
					}
					
					
					
					
					region=sb.toString();
					
					logger.debug("Reqion value [" + region + "]");
					return "success";
					
					
				} catch (Exception e) {
					result = "fail";
					logger.debug("Exception in search ["+e.getMessage()+"]");
					addActionError("Internal error occured.");
				} finally{
				
					requestDTO = null;
					responseDTO = null;
					requestJSON = null;
					
					messages = null;
					errors = null;
				}
	
	return result;

}

public String ajaxWalletTransactioninfo() {
	

	PreparedStatement searchPstmt = null;
	ResultSet searchRS = null;
	

	
	Connection connection = null;
	String entityQry="";

	StringBuilder sb=new StringBuilder();
	region="";
				try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
					requestDTO.setRequestJSON(requestJSON);		
					connection = DBConnector.getConnection();
					
					System.out.println("SID VALUR"+sid);
					
					//if("Cash Deposite".equalsIgnoreCase(sid.split("-")[1])) {
					if("Cash Deposit".equalsIgnoreCase(sid.split("-")[1])) {
						
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						
						entityQry="select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,AMOUNT,CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),SERVICECODE,TXN_AMT,FEE_AMT from WALLET_FIN_TXN where EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						while (searchRS.next()){
							sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
							sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
							sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
							sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
							sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
							sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
							sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
							sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
							sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
							sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
							
							
						}
						sb.append("</table>");
						
					}else if("cash withdrawal".equalsIgnoreCase(sid.split("-")[1])) {
						
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						
						entityQry="select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,AMOUNT,CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),SERVICECODE,TXN_AMT,FEE_AMT from WALLET_FIN_TXN where EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						while (searchRS.next()){
							sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
							sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
							sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
							sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
							sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
							sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
							sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
							sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
							sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
							sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
							
							
						}
						sb.append("</table>");
						
					}else if("Agent Fund".equalsIgnoreCase(sid.split("-")[1])) {
						
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						if("Success".equalsIgnoreCase(sid.split("-")[2])) {
							
							entityQry="select EXT_TXN_REF_NO,USER_ID,DR_ACCOUNT,CR_ACCOUNT,AMOUNT,CHANNEL,'Success',to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),SERVICECODE,TXN_AMT,FEE_AMT from WALLET_FIN_TXN where EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							if (searchRS.next()){
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\">Wallet Credited Successful</td></tr>");
								
								
							}else {
								sb.append("<tr><td>Response</td><td>Wallet Credit Failed</td></tr>");
							}
						}else {
							entityQry="select PAYMENTREFERENCE,USERID,DEBITTRANSACTIONID,CREDITACCCOUNTNUMBER,TRNS_AMT,CHANNEL,RESPONSECODE,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss'),TRANS_TYPE from AGENT_FUND_TRANSFER_TBL where PAYMENTREFERENCE='"+sid.split("-")[0]+"'";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							if (searchRS.next()){
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								
							}
						}
						
						sb.append("</table>");
						
					}else if("Fund Transfer Other Bank".equalsIgnoreCase(sid.split("-")[1])) {
						
						System.out.println("split vl"+sid.split("-")[1]);
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						if("Success".equalsIgnoreCase(sid.split("-")[2])) {
							
							
							entityQry="select WFT.EXT_TXN_REF_NO,WFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,WFT.AMOUNT,WFT.CHANNEL,'Success',to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),WFT.SERVICECODE,WFT.TXN_AMT,WFT.FEE_AMT,(Select BANK_NAME from BANKS_DATA where NIBSSCODE=AWFT.TO_BRANCH_CODE),FTO_REQUEST from WALLET_FIN_TXN WFT,AGENT_WALLET_FTO_TRANS_TBL AWFT " + 
									" where  WFT.EXT_TXN_REF_NO=AWFT.FTBATCHID AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							if (searchRS.next()){
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
								sb.append("<tr><td>Beneficiary Bank</td><td>"+searchRS.getString(12)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								/*sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\">Wallet Debit Successful</td></tr>");*/
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">"+sid.split("-")[1]+" Request </td></tr>");
								JSONObject jobj=JSONObject.fromObject(searchRS.getString(13));
								if(jobj.containsKey("beneficiaryAccountName")) {
									sb.append("<tr><td>Beneficiary Account Name</td><td>"+jobj.getString("beneficiaryAccountName")+"</td></tr>");
								}
								if(jobj.containsKey("BeneficiaryAccountNumber")) {
								sb.append("<tr><td>Beneficiary Account Number</td><td>"+jobj.getString("BeneficiaryAccountNumber")+"</td></tr>");
								}
								
								
								searchPstmt.close();
								searchRS.close();
								sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
								entityQry="select WFT.EXT_TXN_REF_NO,WFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,WFT.AMOUNT,WFT.CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Debit Successful'),to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),WFT.SERVICECODE,WFT.TXN_AMT,WFT.FEE_AMT,(Select BANK_NAME from BANKS_DATA where NIBSSCODE=AWFT.TO_BRANCH_CODE) from WALLET_FIN_TXN WFT,AGENT_WALLET_FTO_TRANS_TBL AWFT " + 
										" where  WFT.EXT_TXN_REF_NO=AWFT.FTBATCHID AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"' ORDER BY  WFT.TXN_STAMP asc ";	
								searchPstmt = connection.prepareStatement(entityQry);
								searchRS = searchPstmt.executeQuery();
								while (searchRS.next()){
									sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(7)+"</td></tr>");
									sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
									sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
									sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
									sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
									sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
							    }
								
							
						}else {
							sb.append("<tr><td>Response</td><td>Transaction Failed</td></tr>");
						}
						
					}else {
						entityQry="select AMOUNT,FROM_ACCOUNT,TO_ACCOUNT,CHANNEL,BANK_CODE,BANK_NAME,FTO_RESP_CODE,FTO_RESP_DESC,FTBATCHID,USER_ID from AGENT_WALLET_FTO_TRANS_TBL where FTBATCHID='"+sid.split("-")[0]+"'";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\">Fund Transfer Other Bank Response Details </td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						while (searchRS.next()){
							sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(9)+"</td></tr>");
							sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(10)+"</td></tr>");
							sb.append("<tr><td>Amount</td><td>"+searchRS.getString(1)+"</td></tr>");
							sb.append("<tr><td>Channel</td><td>"+searchRS.getString(4)+"</td></tr>");
							sb.append("<tr><td>From Account</td><td>"+searchRS.getString(2)+"</td></tr>");
							sb.append("<tr><td>To Account</td><td>"+searchRS.getString(3)+"</td></tr>");
							sb.append("<tr><td>Beneficiary Code</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Beneficiary Bank</td><td>"+searchRS.getString(6)+"</td></tr>");
							sb.append("<tr><td>Response Code</td><td>"+searchRS.getString(7)+"</td></tr>");
							sb.append("<tr><td>Response Message</td><td>"+searchRS.getString(8)+"</td></tr>");
							
						}
						searchPstmt.close();
						searchRS.close();
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						entityQry="select WFT.EXT_TXN_REF_NO,WFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,WFT.AMOUNT,WFT.CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Debit Successful'),to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),WFT.SERVICECODE,WFT.TXN_AMT,WFT.FEE_AMT,(Select BANK_NAME from BANKS_DATA where NIBSSCODE=AWFT.TO_BRANCH_CODE) from WALLET_FIN_TXN WFT,AGENT_WALLET_FTO_TRANS_TBL AWFT " + 
								" where  WFT.EXT_TXN_REF_NO=AWFT.FTBATCHID AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"' ORDER BY  WFT.TXN_STAMP asc ";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						int i=0;
						while (searchRS.next()){
							sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(7)+"</td></tr>");
							sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
							sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
							sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
							sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
							i=i+1;
					    }
						if(i==1) {
							sb.append("<tr style=\"bgcolor:red\"><td colspan='2'>Transaction pending for reversal </td></tr>");
						}
						if(i==0) {
							sb.append("<tr><td>Some thing went wrong</td></tr>");
						}
					}
						sb.append("</table>");
					}else if("Fund Transfer Own Bank".equalsIgnoreCase(sid.split("-")[1])) {
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						if("Success".equalsIgnoreCase(sid.split("-")[2])) {
							
							entityQry="select AWTT.FTBATCHID,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,AWTT.AMOUNT,AWTT.CHANNEL,decode(AWTT.FTO_RESP_CODE,'00','Success','Failed'),to_char(AWTT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),'Fund Transfer Own bank',NVL(WFT.AMOUNT,0),NVL(WFT.FEE_AMT,0),FTO_REQUEST from AGENT_WALLET_TRANS_TBL AWTT, WALLET_FIN_TXN WFT   " + 
									" where  AWTT.FTBATCHID=WFT.EXT_TXN_REF_NO AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							if (searchRS.next()){
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\">Wallet Debit Successful</td></tr>");
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">"+sid.split("-")[1]+" Request </td></tr>");
								JSONObject jobj=JSONObject.fromObject(searchRS.getString(12));
								if(jobj.containsKey("beneficiaryAccountName")) {
									sb.append("<tr><td>beneficiary Account Name</td><td>"+jobj.getString("beneficiaryAccountName")+"</td></tr>");
								}
								if(jobj.containsKey("BeneficiaryAccountNumber")) {
								sb.append("<tr><td>Beneficiary Account Number</td><td>"+jobj.getString("BeneficiaryAccountNumber")+"</td></tr>");
								}
								
								
							
							}else {
								sb.append("<tr><td>Response</td><td>Transaction Failed</td></tr>");
							}
						
						}else {
							
							entityQry="select USER_ID,FTBATCHID,AMOUNT,FROM_ACCOUNT,TO_ACCOUNT,CHANNEL,FTO_RESP_CODE,FTO_RESP_DESC from AGENT_WALLET_TRANS_TBL WHERE FTBATCHID='"+sid.split("-")[0]+"' ";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\">Fund Transfer Own Bank Response Details </td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
		
							while (searchRS.next()){
								
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>From Account</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>To Account</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Response Code</td><td>"+searchRS.getString(7)+"</td></tr>");
								sb.append("<tr><td>Response Message</td><td>"+searchRS.getString(8)+"</td></tr>");
								
							}
							searchPstmt.close();
							searchRS.close();
							
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							
							entityQry="select WFT.EXT_TXN_REF_NO,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,AWTT.AMOUNT,AWTT.CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Debit Successful'),to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),'Fund Transfer Own bank',NVL(WFT.AMOUNT,0),NVL(WFT.FEE_AMT,0) from AGENT_WALLET_TRANS_TBL AWTT, WALLET_FIN_TXN WFT   " + 
									" where  AWTT.FTBATCHID=WFT.EXT_TXN_REF_NO AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"' order by WFT.TXN_STAMP asc";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							int i=0;
							while (searchRS.next()){
								sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								i=i+1;
							}
							searchPstmt.close();
							searchRS.close();
							
							if(i==1) {
								sb.append("<tr style=\"bgcolor:red\"><td colspan='2'>Transaction pending for reversal </td></tr>");
							}
							
							if(i==0) {
								sb.append("<tr><td>Some thing went wrong</td></tr>");
							}
						}
						sb.append("</table>");
					}else if("Paybill".equalsIgnoreCase(sid.split("-")[1]) || "Paybill Airtime Recharge".equalsIgnoreCase(sid.split("-")[1])) {
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						if("Success".equalsIgnoreCase(sid.split("-")[2])) {
							entityQry="select WFT.EXT_TXN_REF_NO,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',AWPT.AMOUNT,AWPT.CHANNEL,decode(AWPT.PB_RESP_CODE,'00','Success','Failed'),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),'Pay Bill',NVL(WFT.AMOUNT,0),NVL(WFT.FEE_AMT,0),PB_REQUEST  from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT  " + 
									" where  AWPT.FTBATCHID=WFT.EXT_TXN_REF_NO AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"'";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							if (searchRS.next()){
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Debit Account Number</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Credit Account Number</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(9)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								
								/*sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\">Wallet Debit Successful</td></tr>");*/
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">"+sid.split("-")[1]+" Request </td></tr>");
								JSONObject jobj=JSONObject.fromObject(searchRS.getString(12));
								if(jobj.containsKey("paymentcode")) {
									sb.append("<tr><td>Payment code</td><td>"+jobj.getString("paymentcode")+"</td></tr>");
								}
								if(jobj.containsKey("transdesc")) {
								sb.append("<tr><td>Trans desc</td><td>"+jobj.getString("transdesc")+"</td></tr>");
								}
								if(jobj.containsKey("custmobile")) {
									sb.append("<tr><td>Customer Mobile</td><td>"+(jobj.getString("custmobile")).replace("NA", "")+"</td></tr>");
								}
								
								searchPstmt.close();
								searchRS.close();
								
								sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
								sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
								sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
								
								
								
								entityQry="select WFT.EXT_TXN_REF_NO,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',AWPT.AMOUNT,AWPT.CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Debit Successful'),to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),'Pay Bill',NVL(WFT.AMOUNT,0),NVL(WFT.FEE_AMT,0)  from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT  " + 
										" where  AWPT.FTBATCHID=WFT.EXT_TXN_REF_NO AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"' order by  WFT.TXN_STAMP asc";	
								searchPstmt = connection.prepareStatement(entityQry);
								searchRS = searchPstmt.executeQuery();
								int i=0;
								while (searchRS.next()){
									sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(7)+"</td></tr>");
									//sb.append("<tr><td colspan=\"2\" style=\"color:red\">-----------------------------------------------------------------------------------------</td></tr>");
									
									sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
									sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
									sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
									sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
									sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
									sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
									//sb.append("<tr><td colspan=\"2\" style=\"color:red\">-----------------------------------------------------------------------------------------</td></tr>");
									
								}
								
							}else {
								sb.append("<tr><td>Response</td><td>Transaction Failed</td></tr>");
							}
						}else {
							
							entityQry="select USER_ID,FTBATCHID,AMOUNT,FROM_ACCOUNT,CHANNEL,PB_RESP_CODE,PB_RESP_DESC from AGENT_WALLET_PB_TRANS_TBL WHERE FTBATCHID='"+sid.split("-")[0]+"' ";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\">Pay Bill Response Details </td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
		
							while (searchRS.next()){
								
								sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(2)+"</td></tr>");
								sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(1)+"</td></tr>");
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(3)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>From Account</td><td>"+searchRS.getString(4)+"</td></tr>");
								sb.append("<tr><td>Response Code</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Response Message</td><td>"+searchRS.getString(7)+"</td></tr>");
								
							}
							searchPstmt.close();
							searchRS.close();
							
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
							sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
							
							
							
							entityQry="select WFT.EXT_TXN_REF_NO,AWPT.USER_ID,AWPT.FROM_ACCOUNT,' ',AWPT.AMOUNT,AWPT.CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Debit Successful'),to_char(WFT.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),'Pay Bill',NVL(WFT.AMOUNT,0),NVL(WFT.FEE_AMT,0)  from AGENT_WALLET_PB_TRANS_TBL AWPT,WALLET_FIN_TXN WFT  " + 
									" where  AWPT.FTBATCHID=WFT.EXT_TXN_REF_NO AND EXT_TXN_REF_NO='"+sid.split("-")[0]+"' order by WFT.TXN_STAMP asc";	
							searchPstmt = connection.prepareStatement(entityQry);
							searchRS = searchPstmt.executeQuery();
							int i=0;
							while (searchRS.next()){
								sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(7)+"</td></tr>");
								//sb.append("<tr><td colspan=\"2\" style=\"color:red\">-----------------------------------------------------------------------------------------</td></tr>");
								
								sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
								sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(10)+"</td></tr>");
								sb.append("<tr><td>Fee</td><td>"+searchRS.getString(11)+"</td></tr>");
								sb.append("<tr><td>Channel</td><td>"+searchRS.getString(6)+"</td></tr>");
								sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(8)+"</td></tr>");
								sb.append("<tr><td>Response</td><td>"+searchRS.getString(7)+"</td></tr>");
								//sb.append("<tr><td colspan=\"2\" style=\"color:red\">-----------------------------------------------------------------------------------------</td></tr>");
								
								i=i+1;
							}
							searchPstmt.close();
							searchRS.close();
							
							if(i==1) {
								sb.append("<tr style=\"bgcolor:red\"><td colspan='2'>Transaction pending for reversal </td></tr>");
							}
							if(i==0) {
								sb.append("<tr><td>Some thing went wrong</td></tr>");
							}
						}
						sb.append("</table>");
					}else if("Cashwithdrawal Card Other bank".equalsIgnoreCase(sid.split("-")[1]) || "Cashwithdrawal Card Union bank".equalsIgnoreCase(sid.split("-")[1]) || "Fundtransfer Card Otherbank".equalsIgnoreCase(sid.split("-")[1]) || "Fundtransfer Card Union bank".equalsIgnoreCase(sid.split("-")[1])) {
						sb.append("<table width='100%' class='table table-striped table-bordered bootstrap-datatable datatable'>");
						entityQry="select POSRRN,INTERNALID,BANKRRN,STAN,AMOUNT/100,PAN,TERMINALNUMBER,BANKRESPONSECODE,NVL(BANKRESPONSEMSG,' '),APPROVEDBY,DECODE(TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank'),to_char(TXNDATE,'dd-mm-yyyy hh:mi:ss'),DECODE(BANKRESPONSECODE,'00','Success','09','Success','Fail') from tbl_tranlog_all where BANKRRN='"+sid.split("-")[0]+"' AND INTERNALID='"+sid.split("-")[3]+"'";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						if (searchRS.next()){
							sb.append("<tr><td>Payment Reference Number</td><td>"+searchRS.getString(3)+"</td></tr>");
							sb.append("<tr><td>Wallet Reference Number</td><td>"+sid.split("-")[3]+"</td></tr>");
							sb.append("<tr><td>Mobile Number</td><td>"+searchRS.getString(10)+"</td></tr>");
							sb.append("<tr><td>Card Number</td><td>"+searchRS.getString(6)+"</td></tr>");
							sb.append("<tr><td>POS RRNr</td><td>"+searchRS.getString(1)+"</td></tr>");
							sb.append("<tr><td>INTERNAL ID</td><td>"+searchRS.getString(2)+"</td></tr>");
							sb.append("<tr><td>STAN</td><td>"+searchRS.getString(4)+"</td></tr>");
							sb.append("<tr><td>AMOUNT</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Channel</td><td>POS</td></tr>");
							sb.append("<tr><td>TERMINAL NUMBER</td><td>"+searchRS.getString(7)+"</td></tr>");
							sb.append("<tr><td>Service Type</td><td>"+searchRS.getString(11)+"</td></tr>");
							sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(12)+"</td></tr>");
							sb.append("<tr><td>Response code</td><td>"+searchRS.getString(8)+"</td></tr>");
							sb.append("<tr><td>Service Response Message</td><td>"+searchRS.getString(9)+"</td></tr>");
							sb.append("<tr><td>Response Message</td><td>"+searchRS.getString(13)+"</td></tr>");
							
							
								
						
							
						}
						
						searchPstmt.close();
						searchRS.close();
						
					if("Success".equalsIgnoreCase(sid.split("-")[2])) {
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\">Wallet Transaction Details </td></tr>");
						sb.append("<tr><td colspan=\"2\" style=\"color:red\"></td></tr>");
						
						entityQry="select AMOUNT,CHANNEL,DECODE(SUBSTR(TXN_REF_NO,1,1),'R','Reversal Successful','Wallet Credited Successful'),to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NVL(AMOUNT,0),NVL(FEE_AMT,0) from WALLET_FIN_TXN    " + 
								" where  EXT_TXN_REF_NO='"+sid.split("-")[0]+"' AND TXN_REF_NO in ('"+sid.split("-")[3]+"','R"+sid.split("-")[3]+"')";	
						searchPstmt = connection.prepareStatement(entityQry);
						searchRS = searchPstmt.executeQuery();
						int i=0;
						while (searchRS.next()){
							sb.append("<tr style=\"color:orange\"><td>Transaction Status</td><td>"+searchRS.getString(3)+"</td></tr>");
							
							sb.append("<tr><td>Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Txn Amount</td><td>"+searchRS.getString(5)+"</td></tr>");
							sb.append("<tr><td>Fee</td><td>"+searchRS.getString(6)+"</td></tr>");
							sb.append("<tr><td>Channel</td><td>"+searchRS.getString(2)+"</td></tr>");
							sb.append("<tr><td>Date and Time</td><td>"+searchRS.getString(4)+"</td></tr>");
							
							i=i+1;
						}
						searchPstmt.close();
						searchRS.close();
						if(i==0) {
							sb.append("<tr><td>Some thing went wrong</td></tr>");
						}
					}
						
						sb.append("</table>");
					}
					
						
					
					region=sb.toString();
					
					logger.debug("Reqion value [" + region + "]");
					return "success";
					
					
				} catch (Exception e) {
					result = "fail";
					logger.debug("Exception in search ["+e.getMessage()+"]");
					addActionError("Internal error occured.");
				} finally{
				try{
					

					searchPstmt.close();
					searchRS.close();
					connection.close();
					
					}catch(Exception e){
					
					}
					requestDTO = null;
					responseDTO = null;
					requestJSON = null;

				}
	
	return result;

}

	
	public String fraudjx() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

				
				connection = DBConnector.getConnection();
				
				if((type.split("-")[0]).equalsIgnoreCase("MAIN")){
					entityQry = "select distinct RULE_CODE||'-'||RULE_NAME   from FRAUD_CONFIGURATION WHERE ACTION='Y'";
				}else if((type.split("-")[0]).equalsIgnoreCase("PARAMETER")){
					entityQry = "select distinct RULE_PARAM||'-'||RULE_PARAM   from FRAUD_CONFIGURATION WHERE RULE_CODE='"+type.split("-")[1]+"'";
				}else if((type.split("-")[0]).equalsIgnoreCase("CONDITION")){
					entityQry = "select distinct CONDITION||'-'||CONDITION   from FRAUD_CONFIGURATION WHERE RULE_PARAM='"+type.split("-")[1]+"'";
				}else if((type.split("-")[0]).equalsIgnoreCase("FIELD")){
					entityQry = "select FILED_TYPE1||'-'||FILED_TYPE2||'-'||MORE_CONDITION   from FRAUD_CONFIGURATION WHERE CONDITION='"+type.split("-")[1]+"' AND RULE_CODE='"+type.split("-")[2]+"'";
				}
				
				
					

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}
	
	
	
	public String ajaxAccount() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

			
			
			
			JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid(sid));
			//System.out.println(json1);
			JSONArray jsonarray =  JSONArray.fromObject(json1.get("custactinfo"));
			Iterator iterator = jsonarray.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonobj=(JSONObject)iterator.next();
				JSONArray jsonarray1 =  JSONArray.fromObject(jsonobj.get("acctsumm"));
				Iterator iterator1 = jsonarray1.iterator();
				 while (iterator1.hasNext()) {
					 JSONObject jsonobj1=(JSONObject)iterator1.next(); 
					 region=region+":"+jsonobj1.get("accountNo");
				 }
				
			}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}


public String ajaxServiceType() {
		
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		PreparedStatement searchPstmt = null;
		ResultSet searchRS = null;
		Connection connection = null;
		String entityQry="";
		region="";
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
	
			requestDTO.setRequestJSON(requestJSON);		

			
				connection = DBConnector.getConnection();
				
				/*System.out.println(type);
				System.out.println(service);
				System.out.println(product);*/
				if(type.equalsIgnoreCase("OTHER") || type.equalsIgnoreCase("MERCHANT")){
					entityQry = "select SERVICE_CODE||'-'||SERVICE_NAME||'@'||SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER WHERE SERVICE_TYPE='"+type+"' ORDER BY SERVICE_NAME";	
				}
				if(type.equalsIgnoreCase("BILLER")){
					entityQry = "select S_CATEGORY_CODE||'-'||NAME||'@'||S_CATEGORY_CODE||'-'||NAME from BILLER_REGISTRATION  ORDER BY S_CATEGORY_CODE";	
				}
				if(type.equalsIgnoreCase("Agent")){
						entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='AGENT' AND SERVICECODE not in (select SERVICE_CODE from mob_channel_map where product='"+product+"' AND CHANNEL_NAME='"+service+"')";	
					
				}
				
				if(type.equalsIgnoreCase("Mobile Banking")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='MOBILE' AND SERVICECODE not in (select SERVICE_CODE from mob_channel_map where product='"+product+"' AND CHANNEL_NAME='"+service+"')";	
				}
				
				if(type.equalsIgnoreCase("Wallet")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='WALLET' AND SERVICECODE not in (select SERVICE_CODE from mob_channel_map where product='"+product+"' AND CHANNEL_NAME='"+service+"')";
				}
				
				/*if(type.equalsIgnoreCase("Agent") && service.equalsIgnoreCase("MOBILE")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='AGENT' AND MOBILE='Y' AND SERVICECODE not in (select SERVICE_CODE from mob_channel_map where product='"+product+"' AND CHANNEL_NAME='MOBILE')";	
				}
				if(type.equalsIgnoreCase("Agent") && service.equalsIgnoreCase("USSD")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='AGENT' AND USSD='Y' AND SERVICECODE not in (select SERVICE_CODE from mob_channel_map where product='"+product+"' AND CHANNEL_NAME='USSD')";	
				}
				
				if(type.equalsIgnoreCase("Mobile Banking") && service.equalsIgnoreCase("MOBILE")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='MOBILE' AND MOBILE='Y'";		
				}
				if(type.equalsIgnoreCase("Mobile Banking") && service.equalsIgnoreCase("USSD")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='MOBILE' AND USSD='Y'";		
				}
				if(type.equalsIgnoreCase("Wallet") && service.equalsIgnoreCase("MOBILE")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='WALLET' AND MOBILE='Y'";		
				}
				if(type.equalsIgnoreCase("Wallet") && service.equalsIgnoreCase("USSD")){
					entityQry = "select SERVICECODE||'-'||SERVICEDESC||'@'||SERVICECODE||'-'||SERVICEDESC from BANK_SERVICE_CODE_MASTER  WHERE APPLICATION='WALLET' AND USSD='Y'";	
				}
				*/
				

				searchPstmt = connection.prepareStatement(entityQry);
				searchRS = searchPstmt.executeQuery();
				JSONObject json = null;
				while (searchRS.next()){
					json = new JSONObject();
					json.put("file",searchRS.getString(1) );
					region=region+":"+searchRS.getString(1);
				}
				
				logger.debug("Reqion value [" + region + "]");
				return "success";
		
			
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in search ["+e.getMessage()+"]");
			addActionError("Internal error occured.");
		} finally{
			try{
				connection.close();
				searchPstmt.close();
				searchRS.close();
			}catch(Exception e){
				
			}
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			messages = null;
			errors = null;
		}

		return result;

	}




public String ajaxStoreinfo() {
	
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
	PreparedStatement searchPstmt = null;
	ResultSet searchRS = null;
	Connection connection = null;
	String entityQry="";
	region="";
	try {
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestDTO = new RequestDTO();
		responseDTO = new ResponseDTO();

		requestDTO.setRequestJSON(requestJSON);		

		
			connection = DBConnector.getConnection();
			System.out.println(service);
			/*System.out.println(type);
			
			System.out.println(product);*/
			
			//System.out.println(service);
			
				entityQry = "select STORE_ID||'@'||STORE_NAME from STORE_MASTER where MERCHANT_ID='"+service+"' ORDER BY STORE_ID";	
			
			

			searchPstmt = connection.prepareStatement(entityQry);
			searchRS = searchPstmt.executeQuery();
			JSONObject json = null;
			while (searchRS.next()){
				json = new JSONObject();
				json.put("file",searchRS.getString(1) );
				region=region+":"+searchRS.getString(1);
			}
			
			logger.debug("Reqion value [" + region + "]");
			return "success";
	
		
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in search ["+e.getMessage()+"]");
		addActionError("Internal error occured.");
	} finally{
		try{
			connection.close();
			searchPstmt.close();
			searchRS.close();
		}catch(Exception e){
			
		}
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		
		messages = null;
		errors = null;
	}

	return result;

}
	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantIdKey() {
		return merchantIdKey;
	}

	public void setMerchantIdKey(String merchantIdKey) {
		this.merchantIdKey = merchantIdKey;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public AjaxActionBean getAjaxJsonBean() {
		return ajaxActionBean;
	}

	public void setAjaxJsonBean(AjaxActionBean ajaxJsonBean) {
		this.ajaxActionBean = ajaxJsonBean;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	} 

}
