package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.bean.Agent;
import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.bean.State;
import com.ceva.base.common.dao.NewOrganizationDAO;
import com.ceva.base.common.dao.impl.LocalGovernmentDaoImpl;
import com.ceva.base.common.dao.impl.StateDaoImpl;
import com.ceva.base.common.dao.impl.SuperAgentDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.product.dao.LimitDao;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.spec.dao.StateDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SuperAgentAction extends ActionSupport implements ModelDriven<Agent>{
	Logger logger =Logger.getLogger(this.getClass());

	private Agent agent;
	JSONObject responseJOSN;
	String cmd;
	
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	String result=null;
	
	private String accNumbers;
	
	
	public String getAccNumbers() {
		return accNumbers;
	}
	public void setAccNumbers(String accNumbers) {
		this.accNumbers = accNumbers;
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
	

	List<State> states;
	List<LocalGovernment> localGovs;

	public String execute(){

		return SUCCESS;
	}
	
	
	public String superagentinfo(){
		 
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			lmtDAO = new LimitDao();
			
			responseDTO = lmtDAO.superagentinfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("SUPER_AGENT_INFO");
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
			lmtDAO = null;
			errors = null;
		}

		return result;
	}
	
	
	public String AgentView() {

		logger.debug("Inside AgentView... ");
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("accNumbers", accNumbers);
			requestJSON.put("cmd", cmd);
			
			
			logger.debug("[Class Name][AgentView][requestDTO : "+requestDTO+"]");
			
			requestDTO.setRequestJSON(requestJSON);

			

			logger.debug("Request DTO [" + requestDTO + "]");
			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.AgentView(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");



			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
                logger.debug("Response JSON [" + responseJSON + "]");
                responseJSON.put("cmd", cmd);
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
			logger.debug("Exception in AgentView [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		}
		finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			lmtDAO = null;
		}

		return result;
	}
	
	
	public String SuperAgentView() {

		logger.debug("Inside AgentView... ");
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("accNumbers", accNumbers);
			requestJSON.put("cmd", cmd);
			
			
			logger.debug("[Class Name][AgentView][requestDTO : "+requestDTO+"]");
			
			requestDTO.setRequestJSON(requestJSON);

			

			logger.debug("Request DTO [" + requestDTO + "]");
			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.SuperAgentView(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");



			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
                logger.debug("Response JSON [" + responseJSON + "]");
                responseJSON.put("cmd", cmd);
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
			logger.debug("Exception in AgentView [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		}
		finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			lmtDAO = null;
		}

		return result;
	}
	
		

	public String searchAccount(){
		StateDAO stateDAO = null;
		LocalGovernmentDAO governmentDAO = null;
		try{
			setCmd("CREATE");
			
			if((agent.getSrchcriteria()).equalsIgnoreCase("DIRECT")){
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(agent.getAccountNumbers()));
				logger.info("account number..:"+agent.getAccountNumbers());
				agent.setAccountName(json1.getString("accountName"));
				agent.setAcctCurrCode(json1.getString("currencyCode"));
				agent.setEmail("");
				
				String mobileno="";
				
				if((json1.getString("phone")).startsWith("0")){
					mobileno="234"+(json1.getString("phone")).substring(1);
				}else{
					mobileno="234"+json1.getString("phone");
				}
				agent.setMobile(mobileno);
				agent.setSchemeType(json1.getString("accountStatus"));
				agent.setSchemeDesc("");
				agent.setBranchCode(json1.getString("branchCode"));
				agent.setSubProductCode(json1.getString("productName"));
			}
			
			//logger.info(agent.toString());
			stateDAO = new StateDaoImpl();
			governmentDAO= new LocalGovernmentDaoImpl();
			localGovs = governmentDAO.getAll();
			states = stateDAO.getAll();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in search agent account..:"+e.getLocalizedMessage());
			//addActionError("Error While Searching Account");
		}finally{
			stateDAO = null;
		}
		return SUCCESS;
	}
	
	
	
	public String save(){
		String response = "";
		logger.info(agent.toString());
		ResponseDTO responseDTO = null;
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			data.put(CevaCommonConstants.OBJECT, agent);
			data.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			data.put(CevaCommonConstants.MAKER_ID, ServletActionContext.getRequest().getSession().getAttribute(CevaCommonConstants.MAKER_ID));
			responseDTO= new SuperAgentDaoImpl().save(data);
			if("SUCCESS".equals(responseDTO.getMessages().get(0))){
				logger.info("product Created successfully");
				response = SUCCESS;
			}else{
				List<String> errors= responseDTO.getErrors();
				for(String error: errors){
					addActionError(error);
				}
				localGovs = new LocalGovernmentDaoImpl().getAll();
				states = new StateDaoImpl().getAll();
				response = ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error while savng super agent.."+e.getLocalizedMessage());
			response = ERROR;
		}finally{
		data= null;
		responseDTO = null;
		}
		return response;
	}
	
	
	public String superagentsave(){
		String response = "";
		logger.info(agent.toString());
		ResponseDTO responseDTO = null;
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			data.put(CevaCommonConstants.OBJECT, agent);
			data.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			data.put(CevaCommonConstants.MAKER_ID, ServletActionContext.getRequest().getSession().getAttribute(CevaCommonConstants.MAKER_ID));
			responseDTO= new SuperAgentDaoImpl().save(data);
			if("SUCCESS".equals(responseDTO.getMessages().get(0))){
				logger.info("product Created successfully");
				response = SUCCESS;
			}else{
				List<String> errors= responseDTO.getErrors();
				for(String error: errors){
					addActionError(error);
				}
				/*localGovs = new LocalGovernmentDaoImpl().getAll();
				states = new StateDaoImpl().getAll();*/
				response = ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error while savng super agent.."+e.getLocalizedMessage());
			response = ERROR;
		}finally{
		data= null;
		responseDTO = null;
		}
		return response;
	}

	public String view() {
		String response = "";

		logger.info(agent.toString());

		try {
			//setCmd("VIEW");
			logger.info("command..:"+cmd);
			agent = new SuperAgentDaoImpl().getByObject(agent);
			if (agent == null) {
				addActionError("No Agent Found With Input.");
				response = "home";
			} else {
				localGovs = new LocalGovernmentDaoImpl().getAll();
				states = new StateDaoImpl().getAll();
				logger.info(agent.toString());
				response = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while savng super agent.."
					+ e.getLocalizedMessage());
			response = ERROR;
		} finally {
		}
		return response;
	}


	
	public String activeDeactive() {

		logger.debug("Inside activeDeactive... ");
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("accNumbers", accNumbers);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put(CevaCommonConstants.MAKER_ID, ServletActionContext.getRequest().getSession().getAttribute(CevaCommonConstants.MAKER_ID));
			
			
			logger.debug("[Class Name][activeDeactive][requestDTO : "+requestDTO+"]");
			
			requestDTO.setRequestJSON(requestJSON);

			

			logger.debug("Request DTO [" + requestDTO + "]");
			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.activeDeactive(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");



			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				/*responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
                logger.debug("Response JSON [" + responseJSON + "]");
                responseJSON.put("cmd", cmd);*/
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
			logger.debug("Exception in activeDeactive [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		}
		finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			lmtDAO = null;
		}

		return result;
	}
	
	
	
	public String SuperactiveDeactive() {

		logger.debug("Inside activeDeactive... ");
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("accNumbers", accNumbers);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put(CevaCommonConstants.MAKER_ID, ServletActionContext.getRequest().getSession().getAttribute(CevaCommonConstants.MAKER_ID));
			
			
			logger.debug("[Class Name][activeDeactive][requestDTO : "+requestDTO+"]");
			
			requestDTO.setRequestJSON(requestJSON);

			

			logger.debug("Request DTO [" + requestDTO + "]");
			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.SuperactiveDeactive(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");



			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
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
			logger.debug("Exception in activeDeactive [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		}
		finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			lmtDAO = null;
		}

		return result;
	}
	
	
	public String superAgentCount(){
		logger.debug("inside [NewBillPaymentAction][lifestyleCount].. ");
		NewOrganizationDAO NewOrganizationDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			NewOrganizationDAO = new NewOrganizationDAO();
			responseDTO = NewOrganizationDAO.superAgentCount(requestDTO);
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
			NewOrganizationDAO = null;
		}

		return result;
	}
	

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public JSONObject getResponseJOSN() {
		return responseJOSN;
	}

	public void setResponseJOSN(JSONObject responseJOSN) {
		this.responseJOSN = responseJOSN;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<LocalGovernment> getLocalGovs() {
		return localGovs;
	}

	public void setLocalGovs(List<LocalGovernment> localGovs) {
		this.localGovs = localGovs;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public Agent getModel() {
		return agent;
	}


}
