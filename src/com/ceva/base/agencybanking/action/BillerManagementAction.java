package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class BillerManagementAction 
extends ActionSupport { 

	private static final long serialVersionUID = 1L;

	private Logger logger=Logger.getLogger(BillerManagementAction.class);

	private String result;
	JSONObject requestJSON=null;
	JSONObject responseJSON=null;
	RequestDTO requestDTO=null;
	ResponseDTO responseDTO=null;

	public String getCommonScreen(){
		logger.debug("inside [getCommonScreen]");
		result="success";
		return result;
	}

	public String getBillerRelatedInfo(){
		logger.debug("inside  GetBillerRelated Info.");
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestDTO.setRequestJSON(requestJSON);	
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null 
					&& responseDTO.getErrors().size() == 0) {		
				responseJSON=(JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER_REL_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			}else{			
				errors = (ArrayList<String>) responseDTO.getErrors();
				for(int i=0;i<errors.size();i++){
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null; 
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

}
