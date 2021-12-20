package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.agent.action.AgentAction;
import com.ceva.base.common.dao.LoyalityPointDetailsDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class LoyalityAction  extends ActionSupport implements ServletRequestAware{
	
	Logger logger = Logger.getLogger(LoyalityAction.class);
	
	 private HttpSession session = null;	
		
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;
	 public JSONObject responseJSON1 = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 private HttpServletRequest httpRequest;
	 
	 
	 
	 private String result;
	 
	 
	 private String productcode;
	 private String loyaltycode;
	 private String application;
	 private String loyaltydesc;
	 private String bankMultiData;
	 
	 
	 
	 public String getBankMultiData() {
		return bankMultiData;
	}

	public void setBankMultiData(String bankMultiData) {
		this.bankMultiData = bankMultiData;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getLoyaltydesc() {
		return loyaltydesc;
	}

	public void setLoyaltydesc(String loyaltydesc) {
		this.loyaltydesc = loyaltydesc;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getLoyaltycode() {
		return loyaltycode;
	}

	public void setLoyaltycode(String loyaltycode) {
		this.loyaltycode = loyaltycode;
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
	 
		public String commonScreen(){
			logger.debug("inside [LoyalityAction][commonScreen]");
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = constructToResponseJson(httpRequest);
			responseJSON1 = responseJSON;
			logger.debug("responseJSON:::"+responseJSON1);
			
			result = "success";
			return result;
		}
	 
	 
	public String LoyalityPointDetails()
	{
			logger.debug("Inside LoyalityPointDetails..");
			LoyalityPointDetailsDAO lpdao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				lpdao = new LoyalityPointDetailsDAO();
				
				responseDTO= lpdao.LoyalityPointDetails(requestDTO);
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
				lpdao = null;
				errors = null;
			}

			return result;
	}
	
	public String LoyalityPointInfo() {

		LoyalityPointDetailsDAO lpdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			/*requestJSON.put("linkmode", linkmode);
			
			requestJSON.put("LimitCode", limitCode);
			requestJSON.put("LimitDescription", limitDescription);
			
			requestJSON.put("Feedescrption", feeDescription);
			requestJSON.put("FeeCode", feeCode);
			
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);*/
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			lpdao = new LoyalityPointDetailsDAO();
			responseDTO = lpdao.fetchLoyalityPointInfo(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
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
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			lpdao = null;
			errors = null;
		}

		return result;

	}
	
	public String loyaltySettingInsert(){
		logger.debug("inside [LoyalityAction][loyaltySettingInsert]");
		ArrayList<String> errors = null;
		LoyalityPointDetailsDAO lpdao=null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			lpdao = new LoyalityPointDetailsDAO();
			
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = lpdao.loyaltySettingInsert(requestDTO);

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
			logger.debug("Exception in Get loyaltySettingInsert Info ["+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		}finally{
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	
	public String LoyalityPointSercive() {

		LoyalityPointDetailsDAO lpdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			
			requestJSON.put("productcode", productcode);
			requestJSON.put("loyaltycode", loyaltycode);
			requestJSON.put("application", application);
			requestJSON.put("loyaltydesc", loyaltydesc);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			lpdao = new LoyalityPointDetailsDAO();
			responseDTO = lpdao.fetchLoyalityPointSercive(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("Response JSON  [" + responseJSON + "]");
				result = "success";
				
				responseJSON.put("productcode", productcode);
				responseJSON.put("loyaltycode", loyaltycode);
				responseJSON.put("application", application);
				responseJSON.put("loyaltydesc", loyaltydesc);

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
			lpdao = null;
			errors = null;
		}

		return result;

	}
	
	public String LoyalityPointSerciveConfirm(){
		
		logger.debug("inside [LoyalityAction][LoyalityPointSerciveConfirm]");
		
		responseJSON1 = new JSONObject();
		responseJSON1.put("productcode", productcode);
		responseJSON1.put("loyaltycode", loyaltycode);
		responseJSON1.put("application", application);
		responseJSON1.put("loyaltydesc", loyaltydesc);
		responseJSON1.put("BANK_MULTI_DATA", getBankMultiData());
		logger.debug("responseJSON:::"+responseJSON1);
		
		result = "success";
		return result;
	}
	
	
	
	
	public String LoyalityPointSerciveAck(){
		
		logger.debug("inside [LoyalityAction][LoyalityPointSerciveAck]");
		ArrayList<String> errors = null;
		LoyalityPointDetailsDAO lpdao=null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			lpdao = new LoyalityPointDetailsDAO();
			
			requestJSON.put("BILLER_ACCT_DATA", getBankMultiData());
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = lpdao.LoyalityPointSerciveAck(requestDTO);

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
	
	public String viewLoyaltyDetails(){
		
		logger.debug("inside [LoyalityAction][viewLoyaltyDetails]");
		ArrayList<String> errors = null;
		LoyalityPointDetailsDAO lpdao=null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			requestJSON = constructToResponseJson(httpRequest);
			
			logger.debug("responseJSON:::"+responseJSON);
			
			lpdao = new LoyalityPointDetailsDAO();
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = lpdao.viewLoyaltyDetails(requestDTO);

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
	
	
	public String fetchLoyaltyDetails(){
		logger.debug("inside [LoyalityAction][fetchLoyaltyDetails].. ");
		LoyalityPointDetailsDAO lpdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestJSON.put("loyaltycode", loyaltycode);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			lpdao = new LoyalityPointDetailsDAO();
			responseDTO = lpdao.fetchLoyaltyDetails(requestDTO);
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
			logger.debug("Exception in [LoyalityAction][fetchLoyaltyDetails] [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			lpdao = null;
		}

		return result;
	}
	
	
	public String viewLoyaltyAssingDetails(){
		
		logger.debug("inside [LoyalityAction][viewLoyaltyAssingDetails]");
		ArrayList<String> errors = null;
		
		LoyalityPointDetailsDAO lpdao=null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			requestJSON = constructToResponseJson(httpRequest);
			
			logger.debug("responseJSON:::"+responseJSON);
			
			lpdao = new LoyalityPointDetailsDAO();
			logger.debug("[requestJSON::"+requestJSON+"]");
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = lpdao.viewLoyaltyAssingDetails(requestDTO);

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
	
	public String ModifyAssignLoyaltyModify(){
		logger.debug("inside [LoyalityAction][loyaltySettingInsert]");
		ArrayList<String> errors = null;
		LoyalityPointDetailsDAO lpdao=null;
		try{
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			lpdao = new LoyalityPointDetailsDAO();
			
			requestJSON= constructToResponseJson(httpRequest);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			
			
			responseDTO = lpdao.ModifyAssignLoyaltyModify(requestDTO);

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
			logger.debug("Exception in Get loyaltySettingInsert Info ["+ e.getMessage() + "]");
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

}
