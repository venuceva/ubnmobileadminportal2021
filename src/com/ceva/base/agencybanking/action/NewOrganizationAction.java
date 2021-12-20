package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.dao.NewBillPaymentDAO;
import com.ceva.base.common.dao.NewOrganizationDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.product.dao.LimitDao;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class NewOrganizationAction  extends ActionSupport implements ServletRequestAware {

	
	
	Logger logger = Logger.getLogger(NewOrganizationAction.class);
	
	private String result;
	JSONObject requestJSON = null;
	public JSONObject responseJSON = null;
	public JSONObject responseJSON1 = null;
	

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	
	private HttpServletRequest httpRequest;
	
	/*@Autowired
	private BillerBean billerBean = null;*/
	private HttpSession session = null;
	
	JSONObject terminalJSON = null;
	JSONObject userJSON = null;
	
	public String bankMultiData = null;
	public String billerCode = null;
	public String billerCode1 = null;
	public String accountNumber = null;
	public String billerId = null;
	public String abbreviation=null;
	
	public String orgid=null;
	public String orgname=null;
	public String qrcode=null;
	
	
	 private String trans;
	 private String fromdate;
	 private String todate;
	 private String fname;
	 private String application;
	 private String refno;
	 private String actionmap;
	 private String finaljsonarray;
	 private String merchanttype;
	 private String customercode;
	 
	 
	 
	 public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getMerchanttype() {
		return merchanttype;
	}
	public void setMerchanttype(String merchanttype) {
		this.merchanttype = merchanttype;
	}
	public String getFinaljsonarray() {
		return finaljsonarray;
	}
	public void setFinaljsonarray(String finaljsonarray) {
		this.finaljsonarray = finaljsonarray;
	}
	public String getActionmap() {
		return actionmap;
	}
	public void setActionmap(String actionmap) {
		this.actionmap = actionmap;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getTrans() {
		return trans;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	public String FirstPageView() {
		
		logger.debug(" FirstPageView ....");

		return "success";

	}


	public String commonScreen(){
		logger.debug("inside [NewBillPaymentAction][commonScreen]");
		
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = constructToResponseJson(httpRequest);
		responseJSON1 = responseJSON;
		logger.debug("responseJSON:::"+responseJSON1);
		
		result = "success";
		return result;
	}
	
	public String commonmarchantprd(){
		logger.debug("inside [NewBillPaymentAction][commonScreen]");
		
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = constructToResponseJson(httpRequest);
		
		JSONArray jsonarray = JSONArray.fromObject(httpRequest.getParameter("finaljsonarray"));


		responseJSON.put("FINAL_JSON", jsonarray);
		responseJSON1 = responseJSON;
		logger.debug("responseJSON:::"+responseJSON1);
		
		result = "success";
		return result;
	}

	
	public String CategoryDetails() {

		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.CategoryDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				responseJSON.put("merchanttype", merchanttype);
				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;

	}

	public String gtOfferDetails() {

		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("orgid", orgid);
			requestJSON.put("orgname", orgname);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.gtOfferDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;

	}
	
	
	public String gtProductDetails() {

		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("orgid", orgid);
			requestJSON.put("orgname", orgname);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.gtProductDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;

	}
	
	public String gtProductDetailsActivate() {

		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("orgid", orgid);
			requestJSON.put("orgname", orgname);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.gtProductDetailsActivate(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;

	}
	
	
	public String fetchBillerDataTableDetails() {
		logger.debug("inside [NewBillPaymentAction][fetchBillerDataTableDetails].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fetchBillerDataTableDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON1 = (JSONObject) responseDTO.getData().get("BILLER_LIST");
				logger.debug("Response JSON in aCTION rAVI [" + responseJSON1 + "]");
				/*terminalJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.TERMINAL_DATA);
				logger.debug("Terminal JSON [" + terminalJSON + "]");
				userJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_DATA);
				logger.debug("User JSON [" + userJSON + "]");*/
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	public String fetchPaymentMerchant() {
		logger.debug("inside [NewBillPaymentAction][fetchBillerDataTableDetails].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fetchPaymentMerchant(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON1 = (JSONObject) responseDTO.getData().get("BILLER_LIST");
				logger.debug("Response JSON in aCTION rAVI [" + responseJSON1 + "]");
				/*terminalJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.TERMINAL_DATA);
				logger.debug("Terminal JSON [" + terminalJSON + "]");
				userJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_DATA);
				logger.debug("User JSON [" + userJSON + "]");*/
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	
	
	public String productcreation(){
		logger.debug("inside [NewBillPaymentAction][productcreation]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			//requestJSON.put("billerBean", billerBean);
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			JSONArray jsonarray = JSONArray.fromObject(httpRequest.getParameter("finaljsonarray"));
			requestJSON.put("FINAL_JSON", jsonarray);
			
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = NewOrganizationDAO.productcreation(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String ServiceDetails() {
		logger.debug("[AgentAction][ServiceDetails] Inside ServiceDetails... ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

		
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][ServiceDetails]  Request DTO [" + requestDTO + "]");
			  
				NewOrganizationDAO = new NewOrganizationDAO();
				responseDTO = NewOrganizationDAO.ServiceDetails(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][ServiceDetails]  Response JSON++++++++ [" + responseJSON + "]");
				
				responseJSON.put("billerCode", billerCode);
				responseJSON.put("abbreviation", abbreviation);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			} catch (Exception e) {
			result = "fail";
			logger.debug("[AgentAction][ServiceDetails] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(NewOrganizationDAO!=null)
				NewOrganizationDAO = null;
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	
	public String billerAccountSubmitDetails(){
		
		logger.debug("inside [NewBillPaymentAction][billerAccountSubmitDetails]");
		
		responseJSON1 = new JSONObject();
		responseJSON1.put("BANK_MULTI_DATA", getBankMultiData());
		logger.debug("responseJSON:::"+responseJSON1);
		
		result = "success";
		return result;
	}
	
	
	public String registerBillerAccountDetails(){
		
		logger.debug("inside [NewBillPaymentAction][registerBillerAccountDetails]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			requestJSON.put("BILLER_ACCT_DATA", getBankMultiData());
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = NewOrganizationDAO.registerBillerAccountDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				responseJSON1=responseJSON;
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		
		
		return result;
	}
	
	
	
	public String viewBillerAccountDetails(){
		
		logger.debug("inside [NewBillPaymentAction][viewBillerAccountDetails]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			requestJSON = constructToResponseJson(httpRequest);
			
			logger.debug("responseJSON:::"+responseJSON);
			
			NewOrganizationDAO = new NewOrganizationDAO();
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = NewOrganizationDAO.viewBillerAccountDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_INFO"); 
				logger.debug("Response JSON [" + responseJSON + "]");
				responseJSON1= responseJSON;
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		
		
		return result;

		
		
	}
	
	
	
	public String payBillInitDetails(){
		
		logger.debug("inside [NewBillPaymentAction][payBillInitDetails]");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			/*logger.debug("Biller Code::"+getBillerBean().getBillerCode());
			
			requestJSON.put("billerId", getBillerBean().getBillerCode() );
			requestJSON.put("accountNumber", getAccountNumber());*/
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			
			responseDTO = NewOrganizationDAO.fetchBillerDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON1 = (JSONObject) responseDTO.getData().get("BILLER_INFO");
				logger.debug("Response JSON in aCTION rAVI [" + responseJSON1 + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	
	public String payBillSubmitDetails(){
		logger.debug("inside [NewBillPaymentAction][payBillSubmitDetails]");
		
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON1 = constructToResponseJson(httpRequest);
		
		logger.debug("responseJSON:::"+responseJSON1);
		
		result = "success";
		return result;
	}
	
	
	
	public String billPayVerifyPin() {
		logger.debug("Inside [NewBillPaymentAction][billPayVerifyPin]. ");
		HttpSession session = null;
		NewOrganizationDAO NewOrganizationDAO = null;

		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON = constructToResponseJson(httpRequest);
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			logger.debug("Request JSON [" + requestJSON + "]");

			requestDTO.setRequestJSON(requestJSON);

			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.billPayVerifyPin(requestDTO);
			logger.debug("ResponseDTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug("Errors [" + errors + "]");
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			responseJSON = requestJSON;
			responseJSON1= responseJSON;
			logger.debug("Response JSON [" + responseJSON + "]");
			logger.debug("Response JSON [" + responseJSON1 + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in BillPayment Submit Details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	
	public String payBillInsertDetails(){
		
		logger.debug("inside [NewBillPaymentAction][payBillInsertDetails]");

		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			requestJSON = constructToResponseJson(httpRequest);
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			logger.debug("Request JSON [" + requestJSON + "]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = NewOrganizationDAO.payBillInsertDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get("BILLPAY"); 
				responseJSON1= responseJSON;
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String billerTransactionHistory(){
		
		logger.debug("inside Biller Transaction History:::");
		return "success";
	}
	

	public String viewBillerDetails(){
		
		logger.debug("inside [NewBillPaymentAction][viewBillerDetails]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			requestJSON = constructToResponseJson(httpRequest);
			
			logger.debug("responseJSON:::"+responseJSON);
			
			NewOrganizationDAO = new NewOrganizationDAO();
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = NewOrganizationDAO.viewBillerDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_INFO"); 
				logger.debug("Response JSON [" + responseJSON + "]");
				responseJSON1= responseJSON;
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		
		
		return result;

		
		
	}
	
	
	public String updateBillerDetails(){
		logger.debug("inside [NewBillPaymentAction][updateBillerDetails]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			//requestJSON.put("billerBean", billerBean);
			requestJSON=constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = NewOrganizationDAO.updateBillerDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}

	
	private JSONObject constructToResponseJson(HttpServletRequest httpRequest) {

		JSONObject jsonObject = null;
		Enumeration<?> enumParams = null;
		logger.debug("Inside ConstructToResponseJson... ");
		String key = "";
		String val = "";

		try {
			enumParams = httpRequest.getParameterNames();
			jsonObject = new JSONObject();
			while (enumParams.hasMoreElements()) {
				key = (String) enumParams.nextElement();
				val = httpRequest.getParameter(key);
				jsonObject.put(key, val);
			}
			logger.debug("jsonObject Ravi...::::::"+jsonObject);
		} catch (Exception e) {
			logger.debug("Exception while converting to httpreq to bean["
					+ e.getMessage() + "]");

		} finally {
			enumParams = null;
			key = null;
			val = null;
		}
		return jsonObject;
	}
	
	

	public String billerAccountDetails() {
		logger.debug("inside [NewBillPaymentAction][billerAccountDetails].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestJSON.put("billerId", getBillerCode() );
			requestJSON.put("accountNumber", getAccountNumber());
			logger.debug("billerId:::"+getBillerCode());
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.billerAccountDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON1 = (JSONObject) responseDTO.getData().get("BILLER_ACCT_INFO");
				logger.debug("Response JSON in aCTION rAVI [" + responseJSON1 + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	
	public String modifyBillerAccountDetails(){
		
		logger.debug("inside [NewBillPaymentAction][modifyBillerAccountDetails]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			requestJSON = constructToResponseJson(httpRequest);
			
			logger.debug("requestJSON::::"+requestJSON);
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = NewOrganizationDAO.modifyBillerAccountDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}	
			
			result = "success";
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get modifyBillerAccountDetails Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}

	public String fetchBillerAccountDetails(){
		logger.debug("inside [NewBillPaymentAction][fetchBillerAccountDetails].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestJSON.put("billerId", getBillerId());
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fetchBillerAccountDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_ACCT_LIST");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerAccountDetails] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	public String fetchofferdetails(){
		logger.debug("inside [NewBillPaymentAction][fetchBillerAccountDetails].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestJSON.put("billerId", getBillerId());
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fetchofferdetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_ACCT_LIST");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerAccountDetails] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}

	
	public String fetchBillerCount(){
		logger.debug("inside [NewBillPaymentAction][fetchBillerCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestJSON.put("billerId", getBillerId());
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fetchBillerCount(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_COUNT");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerCount] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	
	
	
	public String insertmerchant(){
		logger.debug("inside [NewBillPaymentAction][insertmerchant]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			//requestJSON.put("billerBean", billerBean);
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = NewOrganizationDAO.insertmerchant(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String paymentmcode() {
		logger.debug("[AgentAction][ServiceDetails] Inside ServiceDetails... ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  
			requestJSON.put("qrcode",qrcode);
		
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][ServiceDetails]  Request DTO [" + requestDTO + "]");
			  
			    NewOrganizationDAO = new NewOrganizationDAO();
				responseDTO = NewOrganizationDAO.paymentmcode(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][ServiceDetails]  Response JSON++++++++ [" + responseJSON + "]");
				
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			} catch (Exception e) {
			result = "fail";
			logger.debug("[AgentAction][ServiceDetails] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(NewOrganizationDAO!=null)
				NewOrganizationDAO = null;
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	public String lifestyleCount(){
		logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.lifestyleCount(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_COUNT");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerCount] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	public String fraudCount(){
		logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.fraudCount(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_COUNT");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerCount] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	public String lifestyleAllCount(){
		logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.lifestyleAllCount(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_COUNT");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerCount] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	
	public String insertMerchantOffer(){
		logger.debug("inside [NewBillPaymentAction][insertMerchantOffer]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			//requestJSON.put("billerBean", billerBean);
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = NewOrganizationDAO.insertMerchantOffer(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				//responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				responseJSON =constructToResponseJson(httpRequest);
				logger.debug("Response JSON11 [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String lifestyletrackingdetails(){
		 NewOrganizationDAO NewOrganizationDAO=null;
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("trans",trans);
			requestJSON.put("application",application);
			requestJSON.put("fname",fname);
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
		
			
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.lifestyletrackingdetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;
	}

	
	public String gtTransProductDetails() {

		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("orgid", orgid);
			requestJSON.put("refno", refno);
			requestJSON.put("actionmap", actionmap);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.gtTransProductDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				responseJSON.put("orgid", orgid);
				responseJSON.put("orgname", orgname);
				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			NewOrganizationDAO = null;
			errors = null;
		}

		return result;

	}
	
	

	
	public String gtLifeStyleUpdate(){
		logger.debug("inside [NewBillPaymentAction][productcreation]");
		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			NewOrganizationDAO = new NewOrganizationDAO();
			
			//requestJSON.put("billerBean", billerBean);
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = NewOrganizationDAO.gtLifeStyleUpdate(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			
			
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {  
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO); 
				logger.debug("Response JSON [" + responseJSON + "]");
				
				/*if(actionmap.equalsIgnoreCase("drafting")){
					responseJSON.put("message", "Order Drafting to Merchant");
				}*/
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
				
		}catch(Exception e){
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String DiscountPartnersConf(){
		

		logger.debug("########################### ChannelMappingConf Data Started ###########################");



		try {

			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			
		
			logger.info("finaljsonarray >>> ["+finaljsonarray+"]");

			JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


			responseJSON.put("FINAL_JSON", jsonarray);



			logger.info("Response Json ["+ responseJSON+"]");

			result = "success";	


		} catch (Exception e) {

			result = "fail";
			e.printStackTrace();
			logger.debug("Exception in getRequest["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");

		} finally {

			requestJSON = null;

		}

		logger.debug("########################### ChannelMappingConf Data End ###########################");

		return result;
	}
	
	
	public String DiscountPartnersAck(){

		logger.debug("########################### ChannelMappingAck Data Started ###########################");

		ArrayList<String> errors = null;
		NewOrganizationDAO NewOrganizationDAO = null;
		try {
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			
			session = ServletActionContext.getRequest().getSession();
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			
			
			NewOrganizationDAO= new NewOrganizationDAO();

			logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


			JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


			responseJSON.put("FINAL_JSON", jsonarray);

			requestDTO.setRequestJSON(responseJSON);
			
			responseDTO = NewOrganizationDAO.DiscountPartnersAck(requestDTO);
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();


				for (int i = 0; i < errors.size(); i++) {

					addActionError(errors.get(i));

				}
				result = "fail";

			}
			

			logger.info("Response Json ["+ responseJSON+"]");


		} catch (Exception e) {

			result = "fail";
			e.printStackTrace();
			logger.debug("Exception in getRequest["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");

		} finally {

			requestJSON = null;

		}

		logger.debug("########################### ChannelMappingAck Data End ###########################");

		return result;
	}

	public String locatordetailsCount(){
		logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.locatordetailsCount(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("BILLER_COUNT");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in [NewBillPaymentAction][fetchBillerCount] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}

		return result;
	}
	
	
	public String locatordetails(){
		 
	NewOrganizationDAO NewOrganizationDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("trans",trans);
		requestJSON.put("fname",fname);
		/*requestJSON.put("application",application);
		
		requestJSON.put("fromdate",fromdate);
		requestJSON.put("todate",todate);
	*/
		
		 NewOrganizationDAO = new NewOrganizationDAO();
		responseDTO = NewOrganizationDAO.locatordetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
			result = "success";
		} else {
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;

		errors = null;
		NewOrganizationDAO = null;
	}
	return result;
	}
	
	public String locatorlogiondetails(){
		 
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.locatorlogiondetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}
		return result;
		}
	
	
	public String uploadeddetails(){
		 
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("application",application);
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
			
			
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.uploadeddetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				
				responseJSON.put("application",application);
				responseJSON.put("fromdate",fromdate);
				responseJSON.put("todate",todate);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}
		return result;
		}
	
	
	
	public String uploadeddetailsdelete(){
		 
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("application",application);
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
			requestJSON.put("refno",refno);
			
			
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.uploadeddetailsdelete(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				
				responseJSON.put("application",application);
				responseJSON.put("fromdate",fromdate);
				responseJSON.put("todate",todate);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}
		return result;
		}
	
	
	
	public String reportuploadeddetails(){
		 
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("application",application);
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
		
			
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.reportuploadeddetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}
		return result;
		}
	
	
	public String raasinformdetails(){
		 
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("customercode",customercode);
			
			
			 NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.raasinformdetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				responseJSON.put("trans",trans);
				
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			NewOrganizationDAO = null;
		}
		return result;
		}
	
	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public JSONObject getResponseJSON1() {
		return responseJSON1;
	}

	public void setResponseJSON1(JSONObject responseJSON1) {
		this.responseJSON1 = responseJSON1;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
/*	public BillerBean getBillerBean() {
		return billerBean;
	}

	public void setBillerBean(BillerBean billerBean) {
		this.billerBean = billerBean;
	}

	@Override
	public BillerBean getModel() {
		return billerBean;
	}*/

	public String getBankMultiData() {
		return bankMultiData;
	}

	public void setBankMultiData(String bankMultiData) {
		this.bankMultiData = bankMultiData;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}





	public String getBillerCode1() {
		return billerCode1;
	}

	public void setBillerCode1(String billerCode1) {
		this.billerCode1 = billerCode1;
	}





	public String getBillerId() {
		return billerId;
	}





	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

}	