package com.ceva.base.agent.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.bean.State;
import com.ceva.base.common.dao.AgentDAO;
import com.ceva.base.common.dao.POSDetailsDAO;
import com.ceva.base.common.dao.impl.LocalGovernmentDaoImpl;
import com.ceva.base.common.dao.impl.StateDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.spec.dao.StateDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class POSAction extends ActionSupport implements ServletRequestAware{
	Logger logger = Logger.getLogger(POSAction.class);
	
	private HttpSession session = null;	
	
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 
	 private String customercode;
	 private String application;
	 
	 private String actiontype;
	 private String userid;
	 
	 private String PageDirection = null;
	 
	 
	 private String accountno = null;
	 private String walletaccountno = null;
	 private String onboarddate = null;
	 private String fullname = null;
	 private String lastname = null;
	 private String middlename = null;
	 private String branchcode = null;
	 private String dateofbirth = null;
	 private String email = null;
	 private String telephone = null;
	

	private String gender = null;
	 private String status = null;
	 
	 private String addressLine = null;
	 private String localGovernment = null;
	 private String state = null;
	 private String country = null;
	 
	 private String terminalmake = null;
	 private String modelnumber = null;
	 private String serialnumber = null;
	 
	 private String storeid;
	 private String storename;
	 private String terminalid;
	 private String searchid;
	 
	 
	 public String getSearchid() {
		return searchid;
	}

	public void setSearchid(String searchid) {
		this.searchid = searchid;
	}

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	List<State> states;
	 List<LocalGovernment> localGovs;
	 
	 List<LocalGovernment> terminal;
	 List<LocalGovernment> model;

	 private String termilstatus = null;

	 public List<LocalGovernment> getTerminal() {
		return terminal;
	}

	public void setTerminal(List<LocalGovernment> terminal) {
		this.terminal = terminal;
	}

	public List<LocalGovernment> getModel() {
		return model;
	}

	public void setModel(List<LocalGovernment> model) {
		this.model = model;
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
	 
	 
	 public String getTelephone() {
			return telephone;
	 }

	 public void setTelephone(String telephone) {
			this.telephone = telephone;
	 }
	 
	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getWalletaccountno() {
		return walletaccountno;
	}

	public void setWalletaccountno(String walletaccountno) {
		this.walletaccountno = walletaccountno;
	}

	public String getOnboarddate() {
		return onboarddate;
	}

	public void setOnboarddate(String onboarddate) {
		this.onboarddate = onboarddate;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getLocalGovernment() {
		return localGovernment;
	}

	public void setLocalGovernment(String localGovernment) {
		this.localGovernment = localGovernment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTerminalmake() {
		return terminalmake;
	}

	public void setTerminalmake(String terminalmake) {
		this.terminalmake = terminalmake;
	}

	public String getModelnumber() {
		return modelnumber;
	}

	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getPageDirection() {
			return PageDirection;
	 }

	public void setPageDirection(String pageDirection) {
			PageDirection = pageDirection;
	}
	 
	 
	 
	 public String getActiontype() {
		return actiontype;
	}


	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getCustomercode() {
		return customercode;
	}


	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}


	public String getApplication() {
		return application;
	}


	public void setApplication(String application) {
		this.application = application;
	}


	public JSONObject getResponseJSON() {
			return responseJSON;
		}


		public void setResponseJSON(JSONObject responseJSON) {
			this.responseJSON = responseJSON;
		}
		
		private String result;
		
		
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
		
		public String POSAgentSearch() throws Exception
		{
			logger.debug("Inside POSAgentSearch..");
			POSDetailsDAO agdao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
									requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("customercode", customercode);
				requestJSON.put("application", application);
				requestJSON.put("storeid", storeid);
				
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				agdao = new POSDetailsDAO();
				responseDTO= agdao.POSAgentSearch(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
					//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
					responseJSON.put("application", application);
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
				if(agdao!=null)
					 agdao = null;
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
		
		public String POSAgentAdd() {
			logger.debug("[POSAction][POSAgentAdd] Inside POSAgentAdd... ");
			POSDetailsDAO agdao=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
						requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));

					requestJSON.put("userid", userid);
					requestJSON.put("storename", storename);
					requestJSON.put("storeid", storeid);
					requestJSON.put("terminalid", terminalid);
					requestJSON.put("actiontype", actiontype);
					
					
					
					requestDTO.setRequestJSON(requestJSON);
				    logger.debug("[POSAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
				  
					agdao = new POSDetailsDAO();
					responseDTO = agdao.POSAgentAdd(requestDTO);
				  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
					logger.debug("[POSAction][AgentRegModify]  Response JSON++++++++ kailash [" + responseJSON + "]");
					
					responseJSON.put("storename", storename);
					responseJSON.put("storeid", storeid);
					
					
					if(actiontype.equalsIgnoreCase("AGENTADD")){
						/*StateDAO stateDAO = null;*/
						LocalGovernmentDAO governmentDAO = null;
						/*stateDAO = new StateDaoImpl();*/
						governmentDAO= new LocalGovernmentDaoImpl();
						/*localGovs = governmentDAO.getAll();
						states = stateDAO.getAll();*/
						terminal = governmentDAO.getTerminalMake();
						model = governmentDAO.getModel();
						
						PageDirection = "posregistrationadd";
					}
					if(actiontype.equalsIgnoreCase("AGENTVIEW")){
						responseJSON.put("terminalid", terminalid);
						PageDirection = "posregistrationview";
					}
					if(actiontype.equalsIgnoreCase("AGENTMODIFY")){
						/*StateDAO stateDAO = null;*/
						LocalGovernmentDAO governmentDAO = null;
						/*stateDAO = new StateDaoImpl();*/
						governmentDAO= new LocalGovernmentDaoImpl();
						/*localGovs = governmentDAO.getAll();
						states = stateDAO.getAll();*/
						terminal = governmentDAO.getTerminalMake();
						model = governmentDAO.getModel();
						responseJSON.put("terminalid", terminalid);
						PageDirection = "posregistrationmodify";
					}
					
					if(actiontype.equalsIgnoreCase("AGENTSTATUS")){
						responseJSON.put("terminalid", terminalid);
						PageDirection = "posregistrationstatus";
					}
					
					
					
					
					
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
				logger.debug("[POSAction][POSAgentAdd] Exception in View details ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				if(agdao!=null)
					agdao = null;
			 		if(requestDTO!=null)
					 requestDTO = null;
			 		if(responseDTO!=null)
					 responseDTO = null;
			 		if(requestJSON!=null)
					 requestJSON = null;
			 		if(messages!=null)
					 messages = null;
			 		if(errors!=null)
					 errors = null;
			}

			return result;
		}
		
		
		public String PosConfirm() {
			 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
			 
			
			 try {
					requestJSON = new JSONObject();
					responseJSON = new JSONObject();
					requestDTO = new RequestDTO();
					responseDTO = new ResponseDTO();
					
					responseJSON = constructToResponseJson(httpRequest, responseJSON);
			

			   result = "success";
			  
			 } catch (Exception e) {
			  result = "fail";
			  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
			 } finally{
				
				 if(requestDTO!=null)
					 requestDTO = null;
				 if(responseDTO!=null)
					 responseDTO = null;
				 if(requestJSON!=null)
					 requestJSON = null;
				
			 }

			 return result;

			}
		
		public String PosRegistrationAck() {
			 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
			 
			 
			 POSDetailsDAO agdao=null;
			 ArrayList<String> errors = null;
			 
			 try {
				 session = ServletActionContext.getRequest().getSession();
				 requestJSON = new JSONObject();
					responseJSON = new JSONObject();
					requestDTO = new RequestDTO();
					responseDTO = new ResponseDTO();
					
					requestJSON=constructToResponseJson(httpRequest, responseJSON);
					requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
					requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
					
					
			  requestDTO.setRequestJSON(requestJSON);

			  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

			  agdao = new POSDetailsDAO();
			  responseDTO = agdao.gtAgentRegistrtionack(requestDTO);
			  logger.debug("Response DTO is ["+responseDTO+"]");
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				  
				  responseJSON = constructToResponseJson(httpRequest, responseJSON);

			   result = "success";
			  } else {
			   errors = (ArrayList<String>) responseDTO
			     .getErrors();
			   for (int i = 0; i < errors.size(); i++) {
			    addActionError(errors.get(i));
			   }
			   result = "fail";
			  }
			 } catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
			 } finally{
				 if(agdao!=null)
					 agdao = null;
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
		public String PosRegistrationModifyAck() {
			 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
			 
			 
			 POSDetailsDAO agdao=null;
			 ArrayList<String> errors = null;
			 
			 try {
				 session = ServletActionContext.getRequest().getSession();
				 requestJSON = new JSONObject();
					responseJSON = new JSONObject();
					requestDTO = new RequestDTO();
					responseDTO = new ResponseDTO();
					
					requestJSON=constructToResponseJson(httpRequest, responseJSON);
					requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
					requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
					
					
			  requestDTO.setRequestJSON(requestJSON);

			  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

			  agdao = new POSDetailsDAO();
			  responseDTO = agdao.gtAgentRegistrtionmodifyack(requestDTO);
			  logger.debug("Response DTO is ["+responseDTO+"]");
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				  
				  responseJSON = constructToResponseJson(httpRequest, responseJSON);

			   result = "success";
			  } else {
			   errors = (ArrayList<String>) responseDTO
			     .getErrors();
			   for (int i = 0; i < errors.size(); i++) {
			    addActionError(errors.get(i));
			   }
			   result = "fail";
			  }
			 } catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
			 } finally{
				 if(agdao!=null)
					 agdao = null;
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
		
		public String PosRegistrationStatusAck() {
			 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
			 
			 
			 POSDetailsDAO agdao=null;
			 ArrayList<String> errors = null;
			 
			 try {
				 session = ServletActionContext.getRequest().getSession();
				 requestJSON = new JSONObject();
					responseJSON = new JSONObject();
					requestDTO = new RequestDTO();
					responseDTO = new ResponseDTO();
					
					requestJSON=constructToResponseJson(httpRequest, responseJSON);
					requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
					requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
					
					
			  requestDTO.setRequestJSON(requestJSON);

			  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

			  agdao = new POSDetailsDAO();
			  responseDTO = agdao.PosRegistrationStatusAck(requestDTO);
			  logger.debug("Response DTO is ["+responseDTO+"]");
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				  
				  responseJSON = constructToResponseJson(httpRequest, responseJSON);

			   result = "success";
			  } else {
			   errors = (ArrayList<String>) responseDTO
			     .getErrors();
			   for (int i = 0; i < errors.size(); i++) {
			    addActionError(errors.get(i));
			   }
			   result = "fail";
			  }
			 } catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
			 } finally{
				 if(agdao!=null)
					 agdao = null;
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
		
	public String StoreAgentSearch() throws Exception
		{
			logger.debug("Inside POSAgentSearch..");
			POSDetailsDAO agdao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
									requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("customercode", customercode);
				requestJSON.put("application", application);
				
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				agdao = new POSDetailsDAO();
				responseDTO= agdao.StoreAgentSearch(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
					//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
					responseJSON.put("application", application);
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
				if(agdao!=null)
					 agdao = null;
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
	
	public String StoreAgentAdd() {
		logger.debug("[POSAction][POSAgentAdd] Inside POSAgentAdd... ");
		POSDetailsDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));

				requestJSON.put("userid", userid);
				requestJSON.put("storeid", storeid);
				requestJSON.put("actiontype", actiontype);
				
				
				
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[POSAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
			  
				agdao = new POSDetailsDAO();
				responseDTO = agdao.StoreAgentAdd(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[POSAction][AgentRegModify]  Response JSON++++++++ kailash [" + responseJSON + "]");
				
				
				if(actiontype.equalsIgnoreCase("AGENTADD")){
					StateDAO stateDAO = null;
					LocalGovernmentDAO governmentDAO = null;
					stateDAO = new StateDaoImpl();
					governmentDAO= new LocalGovernmentDaoImpl();
					localGovs = governmentDAO.getAll();
					states = stateDAO.getAll();
					terminal = governmentDAO.getTerminalMake();
					model = governmentDAO.getModel();
					
					PageDirection = "storeregistrationadd";
				}
				if(actiontype.equalsIgnoreCase("AGENTVIEW")){
					PageDirection = "posregistrationview";
				}
				if(actiontype.equalsIgnoreCase("AGENTMODIFY")){
					StateDAO stateDAO = null;
					LocalGovernmentDAO governmentDAO = null;
					stateDAO = new StateDaoImpl();
					governmentDAO= new LocalGovernmentDaoImpl();
					localGovs = governmentDAO.getAll();
					states = stateDAO.getAll();
					terminal = governmentDAO.getTerminalMake();
					model = governmentDAO.getModel();
					PageDirection = "storeregistrationmodify";
				}
				
				if(actiontype.equalsIgnoreCase("AGENTSTATUS")){
					PageDirection = "posregistrationstatus";
				}
				
				
				
				
				
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
			logger.debug("[POSAction][POSAgentAdd] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	
	
	public String AgentStoreSearch() {
		logger.debug("[POSAction][POSAgentAdd] Inside POSAgentAdd... ");
		POSDetailsDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));

				requestJSON.put("userid", userid);
				requestJSON.put("storeid", storeid);
				requestJSON.put("actiontype", actiontype);
				
				
				
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[POSAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
			  
				agdao = new POSDetailsDAO();
				responseDTO = agdao.AgentStoreSearch(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[POSAction][AgentRegModify]  Response JSON++++++++ kailash [" + responseJSON + "]");
				
				
				
				if(actiontype.equalsIgnoreCase("AGENTVIEW")){
					PageDirection = "posregistrationview";
				}
				if(actiontype.equalsIgnoreCase("AGENTMODIFY")){
					/*StateDAO stateDAO = null;*/
					LocalGovernmentDAO governmentDAO = null;
					governmentDAO= new LocalGovernmentDaoImpl();
					/*stateDAO = new StateDaoImpl();
					
					localGovs = governmentDAO.getAll();
					states = stateDAO.getAll();*/
					terminal = governmentDAO.getTerminalMake();
					model = governmentDAO.getModel();
					PageDirection = "storeregistrationmodify";
				}
				
				if(actiontype.equalsIgnoreCase("AGENTSTATUS")){
					PageDirection = "storeregistrationstatus";
				}
				
				
				
				
				
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
			logger.debug("[POSAction][POSAgentAdd] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	
	public String storeRegistrationAck() {
		 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
		 
		 
		 POSDetailsDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 session = ServletActionContext.getRequest().getSession();
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON=constructToResponseJson(httpRequest, responseJSON);
				requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

		  agdao = new POSDetailsDAO();
		  responseDTO = agdao.storeRegistrationAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			  responseJSON = constructToResponseJson(httpRequest, responseJSON);

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	public String storeRegistrationModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
		 
		 
		 POSDetailsDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 session = ServletActionContext.getRequest().getSession();
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON=constructToResponseJson(httpRequest, responseJSON);
				requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

		  agdao = new POSDetailsDAO();
		  responseDTO = agdao.storeRegistrationModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			  responseJSON = constructToResponseJson(httpRequest, responseJSON);

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String storeStatusModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
		 
		 
		 POSDetailsDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 session = ServletActionContext.getRequest().getSession();
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON=constructToResponseJson(httpRequest, responseJSON);
				requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

		  agdao = new POSDetailsDAO();
		  responseDTO = agdao.storeStatusModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			  responseJSON = constructToResponseJson(httpRequest, responseJSON);

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String InventorySearch() {
		logger.debug("[POSAction][POSAgentAdd] Inside POSAgentAdd... ");
		POSDetailsDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
		
					/*StateDAO stateDAO = null;
					LocalGovernmentDAO governmentDAO = null;
					stateDAO = new StateDaoImpl();
					governmentDAO= new LocalGovernmentDaoImpl();
					localGovs = governmentDAO.getAll();
					states = stateDAO.getAll();*/
				result = "success";
				
			} catch (Exception e) {
			result = "fail";
			logger.debug("[POSAction][POSAgentAdd] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
public String InventorySearchDetails(){
		 
		POSDetailsDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
								requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
								
			requestJSON.put("searchid",searchid);
			
			requestJSON.put("telephone",telephone);
			requestJSON.put("storeid",storeid);
			requestJSON.put("terminalid",terminalid);
			requestJSON.put("serialnumber",serialnumber);
			requestJSON.put("state",state);
			requestJSON.put("localGovernment",localGovernment);
			requestJSON.put("status",status);
			
			 agdao = new POSDetailsDAO();
			responseDTO = agdao.InventorySearchDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				responseJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
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
			agdao = null;
			errors = null;
		}

		return result;
	}

public String InventorySearchDetailsView() {
	logger.debug("[AgentAction][InventorySearchDetailsView] Inside InventorySearchDetailsView... ");
	POSDetailsDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		
			requestJSON.put("storeid", storeid);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new POSDetailsDAO();
			responseDTO = agdao.InventorySearchDetailsView(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][InventorySearchDetailsView]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][NotificationView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
				if(requestDTO!=null)
			 requestDTO = null;
				if(responseDTO!=null)
			 responseDTO = null;
				if(requestJSON!=null)
			 requestJSON = null;
				if(messages!=null)
			 messages = null;
				if(errors!=null)
			 errors = null;
		}
		
		return result;
	}

public String terminalManagement(){
	 
	 POSDetailsDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		agdao = new POSDetailsDAO();
		
		responseDTO = agdao.terminalManagement(requestDTO);
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String terminalCreation() {
	logger.debug("[POSAction][terminalCreation] Inside POSAgentAdd... ");
	POSDetailsDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
	try {
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestDTO = new RequestDTO();
		responseDTO = new ResponseDTO();
		session = ServletActionContext.getRequest().getSession();
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));

		
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[POSAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
		  
			agdao = new POSDetailsDAO();
			responseDTO = agdao.terminalCreation(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[POSAction][AgentRegModify]  Response JSON++++++++ kailash [" + responseJSON + "]");
			
			responseJSON.put("storename", storename);
			responseJSON.put("storeid", storeid);
			
			
			
				LocalGovernmentDAO governmentDAO = null;
				governmentDAO= new LocalGovernmentDaoImpl();
			
				terminal = governmentDAO.getTerminalMake();
				model = governmentDAO.getModel();
				
			
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
		logger.debug("[POSAction][POSAgentAdd] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
	 		if(requestDTO!=null)
			 requestDTO = null;
	 		if(responseDTO!=null)
			 responseDTO = null;
	 		if(requestJSON!=null)
			 requestJSON = null;
	 		if(messages!=null)
			 messages = null;
	 		if(errors!=null)
			 errors = null;
	}

	return result;
}

public String terminalCreationAck() {
	 logger.debug("[AgentRegAck][terminalCreationAck] Inside AgentRegAck ..");
	 
	 
	 POSDetailsDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 session = ServletActionContext.getRequest().getSession();
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new POSDetailsDAO();
	  responseDTO = agdao.terminalCreationAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON = constructToResponseJson(httpRequest, responseJSON);

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String terminalCreationView() {
	 
	 
	 POSDetailsDAO agdao=null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new POSDetailsDAO();				
			responseDTO = agdao.terminalCreationView(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
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

public String terminalCreationModify() {
	 
	 
	 POSDetailsDAO agdao=null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new POSDetailsDAO();				
			responseDTO = agdao.terminalCreationModify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				LocalGovernmentDAO governmentDAO = null;
				governmentDAO= new LocalGovernmentDaoImpl();
				
				terminal = governmentDAO.getTerminalMake();
				model = governmentDAO.getModel();
				responseJSON.put("terminalid", terminalid);
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

public String terminalCreationModifyAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 POSDetailsDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 session = ServletActionContext.getRequest().getSession();
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new POSDetailsDAO();
	  responseDTO = agdao.terminalCreationModifyAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON = constructToResponseJson(httpRequest, responseJSON);

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String terminalCreationStatus() {
	 
	 
	 POSDetailsDAO agdao=null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new POSDetailsDAO();				
			responseDTO = agdao.terminalCreationModify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				
				responseJSON.put("terminalid", terminalid);
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

public String terminalCreationStatusAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 POSDetailsDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 session = ServletActionContext.getRequest().getSession();
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON=constructToResponseJson(httpRequest, responseJSON);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new POSDetailsDAO();
	  responseDTO = agdao.terminalCreationStatusAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON = constructToResponseJson(httpRequest, responseJSON);

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

}
