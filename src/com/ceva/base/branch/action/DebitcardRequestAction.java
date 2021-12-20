package com.ceva.base.branch.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.DebitcardRequestDao;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class DebitcardRequestAction extends ActionSupport{
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DebitcardRequestAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	String result=null;
	
	private String limitCode;
	
	private HttpSession session = null;
	private String status = null;
	
	 private String branchcode;
	 
	
	 private String requestid;
	 
	 private String customername;
	 private String accountnumber;
	 private String phonenumber;
	 private String cardtype;
	 private String dateofrequest;
	 private String callerstatus;
	 private String custaddress;
	 private String agentname;
	
	 
	 
	 


	
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getDateofrequest() {
		return dateofrequest;
	}
	public void setDateofrequest(String dateofrequest) {
		this.dateofrequest = dateofrequest;
	}
	public String getCallerstatus() {
		return callerstatus;
	}
	public void setCallerstatus(String callerstatus) {
		this.callerstatus = callerstatus;
	}
	public String getCustaddress() {
		return custaddress;
	}
	public void setCustaddress(String custaddress) {
		this.custaddress = custaddress;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public JSONObject getRequestJSON() {
		return requestJSON;
	}
	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
	}
	public JSONObject getResponseJSON() {
		return responseJSON;
	}
	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	
	
	public String getLimitCode() {
		return limitCode;
	}
	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}
	public RequestDTO getRequestDTO() {
		return requestDTO;
	}
	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	public ResponseDTO getResponseDTO() {
		return responseDTO;
	}
	public void setResponseDTO(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}









public String debitdetailsCount(){
	logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
	DebitcardRequestDao brnhDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		
		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		brnhDAO = new DebitcardRequestDao();
		responseDTO = brnhDAO.debitdetailsCount(requestDTO);
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
		brnhDAO = null;
	}

	return result;
}

public String debitdetails(){
	 
	DebitcardRequestDao brnhDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();
	
		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("status",status);
		
		brnhDAO = new DebitcardRequestDao();
		responseDTO = brnhDAO.debitdetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("status",status);
			
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
		brnhDAO = null;
	}
	return result;
}

public String debitdetailsView() {
	logger.debug("[AgentAction][NotificationView] Inside NotificationView... ");
	DebitcardRequestDao brnhDAO = null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		  session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		  
		
			requestJSON.put("requestid", requestid);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    brnhDAO = new DebitcardRequestDao();
			responseDTO = brnhDAO.debitdetailsView(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][NotificationView]  Response JSON++++++++ [" + responseJSON + "]");
			
			
		
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
		logger.debug("[AgentAction][NotificationView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(brnhDAO!=null)
			brnhDAO = null;
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


public String carddetailsconfirm() {
	 logger.debug("[carddetailsconfirm] Inside carddetailsconfirm ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			
			
			
			
			responseJSON.put("requestid", requestid);
			responseJSON.put("customername", customername);
			responseJSON.put("accountnumber", accountnumber);
			responseJSON.put("phonenumber", phonenumber);
			responseJSON.put("cardtype", cardtype);
			responseJSON.put("dateofrequest", dateofrequest);
			responseJSON.put("callerstatus", callerstatus);
			responseJSON.put("custaddress", custaddress);
			responseJSON.put("branchcode", branchcode);
			responseJSON.put("agentname", agentname);
			
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String carddetailsack() {
	logger.debug("[AgentAction][NotificationView] Inside NotificationView... ");
	DebitcardRequestDao brnhDAO = null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		  session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		  
		
		  requestJSON.put("requestid", requestid);
		  requestJSON.put("customername", customername);
		  requestJSON.put("accountnumber", accountnumber);
		  requestJSON.put("phonenumber", phonenumber);
		  requestJSON.put("cardtype", cardtype);
		  requestJSON.put("dateofrequest", dateofrequest);
		  requestJSON.put("callerstatus", callerstatus);
		  requestJSON.put("custaddress", custaddress);
		  requestJSON.put("agentname", agentname);
			
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    brnhDAO = new DebitcardRequestDao();
			responseDTO = brnhDAO.carddetailsack(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][NotificationView]  Response JSON++++++++ [" + responseJSON + "]");
			
			responseJSON.put("requestid", requestid);
			responseJSON.put("customername", customername);
			responseJSON.put("accountnumber", accountnumber);
			responseJSON.put("phonenumber", phonenumber);
			responseJSON.put("cardtype", cardtype);
			responseJSON.put("dateofrequest", dateofrequest);
			responseJSON.put("callerstatus", callerstatus);
			responseJSON.put("custaddress", custaddress);
			responseJSON.put("agentname", agentname);
		
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
		logger.debug("[AgentAction][NotificationView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(brnhDAO!=null)
			brnhDAO = null;
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

}
