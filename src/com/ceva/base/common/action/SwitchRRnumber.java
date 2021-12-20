package com.ceva.base.common.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dao.switchrrnDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class SwitchRRnumber extends ActionSupport {

	private Logger logger = Logger.getLogger(SwitchAjaxAction.class);
	private JSONObject requestJSON = null;

	private JSONObject responseJSON = null;
	private JSONObject responseJSON1 = null;
	private RequestDTO requestDTO = null;
	private ResponseDTO responseDTO = null;
	private String RRN;
	private String result;

	public String RRNnumberDetails() {

		logger.debug("Inside GetRRNTerminals RRN [" + RRN + "]");
		ArrayList<String> errors = null;
		switchrrnDAO switchrrnDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.RRN, RRN);
			logger.debug("Response JSON [" + responseJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			switchrrnDAO = new switchrrnDAO();
			responseDTO = switchrrnDAO.SwitchRRDAO(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_CHECK_INFO);
				logger.debug("Response JSON1 [" + responseJSON1 + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in RRNnumberDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			switchrrnDAO = null;
		}
		return SUCCESS;
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

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

}
