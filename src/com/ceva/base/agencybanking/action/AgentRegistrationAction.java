package com.ceva.base.agencybanking.action;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.AgentRegistrationDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
public class AgentRegistrationAction extends ActionSupport {
	
	Logger logger=Logger.getLogger(AgentRegistrationAction.class);
	
	String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	private HttpSession session = null;
	private String terminalID;
	private String branch;
	private String zone;
	private String physicalpremis;
	private String agentbankacnumber;
	private String numberofoutlets;
	private String comercialActivity;
	private String dob;
	private String registrationDate;
	private String terminalid;

	
	public String getcommonScreen() {
		logger.debug("Inside get AgentInformation");
		AgentRegistrationDAO agentRDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agentRDAO = new AgentRegistrationDAO();
			responseDTO = agentRDAO.getAgentDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("AGENT_DETAILS");
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
			logger.debug("Exception in agent info [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			agentRDAO = null;
		}

		return result;
	}
	
	public String InsertAgentDetails() {
		logger.debug("Inside agent registrationl Insertion.. ");

		AgentRegistrationDAO agentRDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put("MAKER_ID",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("terminalID", terminalID);
			requestJSON.put("branch", branch);
			requestJSON.put("zone",	zone);
			requestJSON.put("physicalpremis",physicalpremis);
			requestJSON.put("agentbankacnumber",agentbankacnumber);
			requestJSON.put("numberofoutlets",numberofoutlets);
			requestJSON.put("comercialActivity",comercialActivity);
			requestJSON.put("dob",dob);
			requestJSON.put("registrationDate",registrationDate);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agentRDAO = new AgentRegistrationDAO();
			responseDTO = agentRDAO.InsertAgentInformation(requestDTO);
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
			logger.debug("Exception in InsertAgentInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			agentRDAO = null;
		}

		return result;
	}
	
	
	
	public String AgentInformationView() {
		logger.debug("Inside agent information view.. ");
		AgentRegistrationDAO agentRDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		
		try {
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("terminalID", terminalid);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agentRDAO = new AgentRegistrationDAO();
			responseDTO = agentRDAO.agentinformationview(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Response JSON[" + responseJSON + "]");
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
		
			responseJSON = (JSONObject) responseDTO.getData().get("AGENT-INFORMATION");
			logger.debug("Response JSON:[" + responseJSON + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in agentinformationview [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			agentRDAO = null;
		}

		return result;
	}
	
	
	public String agentModifyact() {
		logger.debug("Inside agent information Modifyact.. ");

		AgentRegistrationDAO agentRDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("terminalID", terminalid);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agentRDAO = new AgentRegistrationDAO();
			responseDTO = agentRDAO.agentinformationmodify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
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
			responseJSON = requestJSON;
		
			responseJSON = (JSONObject) responseDTO.getData().get("AGENT-INFORMATION");
			logger.debug("Response JSON:@[" + responseJSON + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in afent info [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			agentRDAO = null;
		}

		return result;
	}
	
	
	public String UpdateagentInformation() {
		logger.debug("Inside agent registrationl Insertion.. ");

		AgentRegistrationDAO agentRDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put("MAKER_ID",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("terminalID", terminalID);
			requestJSON.put("branch", branch);
			requestJSON.put("zone",	zone);
			requestJSON.put("physicalpremis",physicalpremis);
			requestJSON.put("agentbankacnumber",agentbankacnumber);
			requestJSON.put("numberofoutlets",numberofoutlets);
			requestJSON.put("comercialActivity",comercialActivity);
			requestJSON.put("dob",dob);
			requestJSON.put("registrationDate",registrationDate);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agentRDAO = new AgentRegistrationDAO();
			responseDTO = agentRDAO.UpdateAgentInformation(requestDTO);
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
			agentRDAO = null;
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

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getPhysicalpremis() {
		return physicalpremis;
	}

	public void setPhysicalpremis(String physicalpremis) {
		this.physicalpremis = physicalpremis;
	}

	public String getAgentbankacnumber() {
		return agentbankacnumber;
	}

	public void setAgentbankacnumber(String agentbankacnumber) {
		this.agentbankacnumber = agentbankacnumber;
	}

	public String getNumberofoutlets() {
		return numberofoutlets;
	}

	public void setNumberofoutlets(String numberofoutlets) {
		this.numberofoutlets = numberofoutlets;
	}

	public String getComercialActivity() {
		return comercialActivity;
	}

	public void setComercialActivity(String comercialActivity) {
		this.comercialActivity = comercialActivity;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	
	
}
