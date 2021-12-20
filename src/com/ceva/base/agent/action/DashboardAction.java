package com.ceva.base.agent.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.dao.AgentDAO;
import com.ceva.base.common.dao.DashboardDAO;
import com.ceva.base.common.dao.POSDetailsDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class DashboardAction extends ActionSupport   implements ServletRequestAware{

	Logger logger = Logger.getLogger(DashboardAction.class);
	
	 private HttpSession session = null;	
		
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 
	 private String result;
	 
	 private String fromdate;
	 private String todate;
	 private String state;
	 private String localGovernment;
	 private String status;

	 
	 

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocalGovernment() {
		return localGovernment;
	}

	public void setLocalGovernment(String localGovernment) {
		this.localGovernment = localGovernment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JSONObject getResponseJSON() {
				return responseJSON;
	}
	
	public void setResponseJSON(JSONObject responseJSON) {
				this.responseJSON = responseJSON;
	}
	
	private HttpServletRequest httpRequest = null;

	@Override
	public void setServletRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	@SuppressWarnings("rawtypes")
	private JSONObject constructToResponseJson(HttpServletRequest httpRequest, JSONObject jsonObject) {

		Enumeration enumParams = null;
		logger.debug("Inside ConstructToResponseJson...");
		try {
			if (jsonObject == null)
				jsonObject = new JSONObject();
			System.out.println("444");
			enumParams = httpRequest.getParameterNames();
			while (enumParams.hasMoreElements()) {
				String key = (String) enumParams.nextElement();
				String val = httpRequest.getParameter(key);
				jsonObject.put(key, val);
			}

		} catch (Exception e) {
			logger.debug(" Exception in ConstructToResponseJson ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			enumParams = null;
		}
		logger.debug(" jsonObject[" + jsonObject + "]");

		return jsonObject;
	}  

	
	public String FirstPageView() {
			
			logger.debug(" FirstPageView ....");
	
			return "success";
	
	}
	
	public String DashboardDetails() throws Exception
	{
		logger.debug("Inside DashboardDetails..");
		DashboardDAO dbdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			dbdao = new DashboardDAO();
			responseDTO= dbdao.DashboardDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			logger.debug("Exception in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(dbdao!=null)
				dbdao = null;
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			 if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	
	public String TopAgentSearchDetails(){
		 
		DashboardDAO dbdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
								requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
								
			
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
			requestJSON.put("state",state);
			requestJSON.put("localGovernment",localGovernment);
			requestJSON.put("status",status);
			
			dbdao = new DashboardDAO();
			responseDTO = dbdao.TopAgentSearchDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				responseJSON.put("fromdate",fromdate);
				responseJSON.put("todate",todate);
				responseJSON.put("state",state.split("-")[1]);
				responseJSON.put("localGovernment",localGovernment);
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
			e.printStackTrace();
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			dbdao = null;
			errors = null;
		}

		return result;
	}
	
	public String ChannelDetails(){
		 
		DashboardDAO dbdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
								requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
								
			
			requestJSON.put("fromdate",fromdate);
			requestJSON.put("todate",todate);
			
			
			dbdao = new DashboardDAO();
			responseDTO = dbdao.ChannelDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
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
			e.printStackTrace();
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			dbdao = null;
			errors = null;
		}

		return result;
	}
}
