package com.ceva.base.common.action;

import java.io.InputStream;
import java.util.ArrayList;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.AjaxActionBean;
import com.ceva.base.common.dao.AssignTerminalAjaxDAO;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class AssignTerminalAjaxAction extends ActionSupport{

	private Logger logger = Logger.getLogger(AssignTerminalAjaxAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	
	private String result;
	private String merchantId;
	private String storeID;
	private String terminalID;
	private String updatemerchantID;

	public String fetchStores() {
		logger.debug("Inside GetStores MerchantId[" + merchantId + "]");
		ArrayList<String> errors = null;
		AssignTerminalAjaxDAO assntmDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("MERCHANT_ID", merchantId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getStores(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("STORE_LIST");
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
			assntmDAO = null;
		}

		return SUCCESS;
	}

	public String fetchTerminal() {
		logger.debug("Inside GetStores MerchantId[" + storeID + "]");
		ArrayList<String> errors = null;
		AssignTerminalAjaxDAO assntmDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("STORE_ID", storeID);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getTerminal(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("TERMINAL_LIST");
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
			assntmDAO = null;
		}

		return SUCCESS;
	}
	
	public String fetchUsers() {
		logger.debug("Inside User Action[" + terminalID + "]");
		ArrayList<String> errors = null;
		AssignTerminalAjaxDAO assntmDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("TERMINAL_ID", terminalID);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getUsers(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("USERS_LIST");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getUsers [" + e.getMessage()	+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			assntmDAO = null;
		}

		return SUCCESS;
	}
	
	public String MerchantFeeviewDetails() {
		logger.debug("Inside CheckSwitchStaus.. ");
		AssignTerminalAjaxDAO assntmDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
				requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			requestJSON.put("mrcode", merchantId);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");

			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("MERCHANTfEEDETAILS");
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
				logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null; 
		}

		return result;
	}
	public String fetchTerminalDetails() {
		logger.debug("Inside User Action[" + terminalID + "]");
		ArrayList<String> errors = null;
		AssignTerminalAjaxDAO assntmDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("TERMINAL_ID", terminalID);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getTerminalDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("TERMINAL_DETAILS");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getUsers [" + e.getMessage()	+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			assntmDAO = null;
		}

		return SUCCESS;
	}
	
	public String TerminalStoreDetails() {
		logger.debug("Inside GetStores MerchantId[" + updatemerchantID + "]");
		ArrayList<String> errors = null;
		AssignTerminalAjaxDAO assntmDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("MERCHANT_ID", updatemerchantID);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			assntmDAO = new AssignTerminalAjaxDAO();
			responseDTO = assntmDAO.getStores(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("STORE_LIST");
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
			assntmDAO = null;
		}

		return SUCCESS;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getUpdatemerchantID() {
		return updatemerchantID;
	}

	public void setUpdatemerchantID(String updatemerchantID) {
		this.updatemerchantID = updatemerchantID;
	}
	
	
}
