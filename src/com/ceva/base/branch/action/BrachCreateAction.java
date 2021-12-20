package com.ceva.base.branch.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.BranchDao;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.product.dao.LimitDao;
import com.ceva.base.common.product.dao.ProductManDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class BrachCreateAction  extends ActionSupport {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(BrachCreateAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	String result=null;
	
	private String limitCode,limitDescription,finaljson;
	private String feefinaljson,feeCode,feeDescription;
	
	private HttpSession session = null;
	private String status = null;
	private String templateName = null;
	private String cmpMessage = null;
	 private String limitDesc;
	 private String trrefno;
	 private String linkmode;
	 private String branchcode;
	 
	 
	 private String transaction;
	 private String seqno;
	 


	
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






public String branchStatusinfo(){
	 
	BranchDao brnhDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
	    responseJSON= new JSONObject(); 
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		brnhDAO = new BranchDao();
		
		responseDTO = brnhDAO.branchStatusinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		brnhDAO = null;
		errors = null;
	}

	return result;
}

public String branchDetails() {
	 
	 
	BranchDao brnhDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			brnhDAO = new BranchDao();				
			responseDTO = brnhDAO.branchDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get("BRANH_DETS");
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

}
public String updateActiveDetails() {
	logger.debug("Inside Insert Method .. ");
	BranchDao brnhDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestJSON.put("branchcode", branchcode);
		requestJSON.put("status", status);
		
		System.out.println(branchcode+"---"+status);
		requestDTO.setRequestJSON(requestJSON);				
		logger.debug("Request DTO [" + requestDTO + "]");
		 brnhDAO = new BranchDao();							
		responseDTO = brnhDAO.updateActiveBranchDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			
			
			responseJSON = (JSONObject) responseDTO.getData().get("BRANH_DETS");
			logger.debug("Response JSON  [" + responseJSON + "]");
			//responseJSON = constructToResponseJson(httpRequest,responseJSON);
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
		logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
				+ "]");
		
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		
		errors = null;
	}


return "success";
}
}
