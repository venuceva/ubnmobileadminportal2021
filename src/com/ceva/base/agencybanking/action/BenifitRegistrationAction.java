package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.ceva.base.common.dao.BenifitRegistrationDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.util.LoadPropFile;
import com.ceva.util.LoadUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BenifitRegistrationAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(BenifitRegistrationAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	HttpServletRequest request=null;
	
	private String benifitCode;
	private String benifitName;
	private String discount;

	private String result = null;
	ArrayList<String> errors = null;

	BenifitRegistrationDAO benifitRegistrationDAO = new BenifitRegistrationDAO();

	public String execute() {
		logger.debug("execute");
		responseJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		responseDTO = benifitRegistrationDAO
				.getBenifitRegistrationList(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get(
					"BENIFIT_LIST_DATA");
			this.benifitCode=responseJSON.getString("BENIFITID");
			logger.debug("benifitCode..:" + benifitCode);
			logger.debug("Response JSON [" + responseJSON + "]");
			result = "benifitHome";
		} else {
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}
		return result;
	}
	
	public String addNewBenifit(){
		logger.debug("addNewBenefit benifitCode...:"+benifitCode);
		responseJSON=new JSONObject();
		responseJSON.put("benifitCode", benifitCode);
		responseJSON.put("benifitName", benifitName);
		responseJSON.put("discount", discount);
		return "addnewbenefit";
	}
	
	public String confirmBenefit(){
		logger.debug("confirmBenefit");
		responseJSON=new JSONObject();
		responseJSON.put("benifitCode", benifitCode);
		responseJSON.put("benifitName", benifitName);
		responseJSON.put("discount", discount);
		return "confirmBenefit";
	}
	public String insertBenifit(){
		logger.debug("insertBenifit");
		HttpSession session=request.getSession();
		requestDTO=new RequestDTO();
		requestJSON=new JSONObject();
		requestJSON.put("benefitId", benifitCode);
		requestJSON.put("benefitName", benifitName);
		requestJSON.put("discount", discount);
		requestJSON.put("makerId", session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestDTO.setRequestJSON(requestJSON);
		responseDTO = benifitRegistrationDAO.insertBenifit(requestDTO);
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseDTO = benifitRegistrationDAO
					.getBenifitRegistrationList(requestDTO);
			responseJSON = (JSONObject) responseDTO.getData().get(
					"BENIFIT_LIST_DATA");
			this.benifitCode=responseJSON.getString("BENIFITID");
			result = "benifitHome";
		} else {
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}
		
		return "benifitHome";
	}
	
	public String modifynewbenefit(){
		responseJSON = new JSONObject();
		requestJSON=new JSONObject();
		requestJSON.put("benefitId", benifitCode);
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		responseDTO = benifitRegistrationDAO
				.getBenifitRegistration(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get(
					"BENIFIT_LIST_DATA");
			logger.debug("Response JSON [" + responseJSON + "]");
			result = "addnewbenefit";
		} else {
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}
		return result;
	}
	
	public String viewbenefit(){
		logger.debug("viewbenefit benifitCode...:"+benifitCode);
		responseJSON=new JSONObject();
		responseJSON.put("benifitCode", benifitCode);
		responseJSON.put("benifitName", benifitName);
		responseJSON.put("discount", discount);
		return "addnewbenefit";
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getBenifitCode() {
		return benifitCode;
	}

	public void setBenifitCode(String benifitCode) {
		this.benifitCode = benifitCode;
	}

	public String getBenifitName() {
		return benifitName;
	}

	public void setBenifitName(String benifitName) {
		this.benifitName = benifitName;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}


}
