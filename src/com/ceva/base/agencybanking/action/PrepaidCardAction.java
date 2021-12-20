package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.PrepaidCardBean;
import com.ceva.base.common.dao.PrepaidCardDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PrepaidCardAction extends ActionSupport implements
		ServletRequestAware, ModelDriven<PrepaidCardBean> {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PrepaidCardAction.class);

	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;
	private ResponseDTO responseDTO = null;
	private RequestDTO requestDTO = null;

	private HttpServletRequest request = null;
	private HttpSession session = null;

	private String result = null;

	@Autowired
	private PrepaidCardBean prepaidBean = null;

	public String commonScreen() {
		logger.debug("Inside CommonScreen .. ");
		return SUCCESS;
	}

	public String fetchPrepaidCardDetails() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("prepaidBean", prepaidBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			responseDTO = new PrepaidCardDAO().fetchServiceDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setPrepaidBean((PrepaidCardBean) responseDTO.getData().get(
						"AccountData"));
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
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in Account Fetch Details ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return result;
	}

	public String prepaidServiceActions() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("prepaidBean", prepaidBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			responseDTO = new PrepaidCardDAO().fetchActionresult(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setPrepaidBean((PrepaidCardBean) responseDTO.getData().get(
						"AccountData"));
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
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in Account Fetch Details ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return result;
	}
	
	public String insertprepaidactdeactdetails() {
		logger.debug("Inside Account active Deactivation Details .. ");
		ArrayList<String> errors = null;
		
		try {
			session = ServletActionContext.getRequest().getSession();
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			
			requestJSON.put("prepaidBean", prepaidBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new PrepaidCardDAO().insertaccountstatus(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setPrepaidBean((PrepaidCardBean) responseDTO.getData().get(
						"AccountData"));
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
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in Account Fetch Details ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
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

	public PrepaidCardBean getPrepaidBean() {
		return prepaidBean;
	}

	public void setPrepaidBean(PrepaidCardBean prepaidBean) {
		this.prepaidBean = prepaidBean;
	}

	@Override
	public PrepaidCardBean getModel() {
		return prepaidBean;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {

	}

}
