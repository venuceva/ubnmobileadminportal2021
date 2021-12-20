package com.ceva.base.agencybanking.action;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


import com.ceva.base.common.dao.AssignTerminalDAO;
import com.ceva.base.common.dao.ServiceManagementDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class AssignTerminalAction extends ActionSupport {
	
	private Logger logger = Logger.getLogger(AuthorizationAllAction.class);
	
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	
	private String result;
	private HttpSession session = null;
	
	private String merchantID;
	private String storeID;
	private String terminalID;
	private String assignUsers;
	
	public String getcommonScreen() {
		AssignTerminalDAO ATDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ATDAO = new AssignTerminalDAO();
			responseDTO = ATDAO.getmerchantidDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("MERCHANT_ID");
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug("Errors [" + errors + "]");
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getMerchantInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			ATDAO = null;
		}

		return result;
	}
	
	public String InsertAssignTerminalUsers() {
		logger.debug("Inside AssignTerminal Insertion.. ");

		AssignTerminalDAO ATDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put("MAKER_ID",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("merchantID", merchantID);
			requestJSON.put("storeID", storeID);
			requestJSON.put("terminalID",	terminalID);
			requestJSON.put("assignUsers",assignUsers);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ATDAO = new AssignTerminalDAO();
			responseDTO = ATDAO.InsertAssignUserDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

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
			logger.debug("Response JSON [" + responseJSON + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in InsertService [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			ATDAO = null;
		}

		return result;
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

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getAssignUsers() {
		return assignUsers;
	}

	public void setAssignUsers(String assignUsers) {
		this.assignUsers = assignUsers;
	}


}
