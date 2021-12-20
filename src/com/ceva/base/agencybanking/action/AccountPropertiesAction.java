package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.bean.AccountPropertiesBean;
import com.ceva.base.common.dao.AccountPropertiesDAO;
import com.ceva.base.common.dao.AddNewAccountDAO;
import com.ceva.base.common.dao.ReportDetailsDAO;
import com.ceva.base.common.dao.ServiceManagementDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AccountPropertiesAction extends ActionSupport implements
		ServletRequestAware, ModelDriven<AccountPropertiesBean> {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AccountPropertiesAction.class);

	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;


	private ResponseDTO responseDTO = null;
	private RequestDTO requestDTO = null;

	private HttpServletRequest request = null;
	private HttpSession session = null;

	private String result = "fail";
	
	
	private String serviceCode;
	private String serviceName;


	private String subServiceCode;
	private String subServiceName;
	private String subServiceText;
	private String feeCode;
	private String serviceType;
	private String slabFrom;
	private String slabTo;
	private String flatPercent;
	private String accountMultiData;
	private String service;
	private String hudumaServiceName;
	private String multiSlabFeeDetails;
	private String partnerDetails;
	private String offusTrnDetails;
	private String serviceTrans;
	private String network;
	private String hudumaFlag;
	private String search;
	private String selectedUserText;
	private String status;
	private String merchantname;
	private String feename;
	private String menucode;



	@Autowired
	private AccountPropertiesBean accountPropBean = null;

	public String execute() {
		String accountId = null;
		logger.debug("Inside Execute Method .. ");
		ArrayList<String> errors = null;
		try {
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();

			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("accountPropBean", accountPropBean);
			requestDTO.setRequestJSON(requestJSON);

			accountId = accountPropBean.getAccountId();

			logger.info(" Account Id is [" + accountId + "]");

			if (!"".equals(accountId) && accountId != null) {
				responseDTO = new AccountPropertiesDAO().getRecord(requestDTO);
			} else {
				responseDTO = new AccountPropertiesDAO().getListAll(requestDTO);
			}

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				if (!"".equals(accountId) && accountId != null) { 
				 
					accountPropBean = (AccountPropertiesBean) responseDTO
							.getData().get("accountPropBean");
				} else {
					responseJSON = (JSONObject) responseDTO.getData().get(
							"ACRECORDS");

					accountPropBean.setResponseJSON(responseJSON);
				}

				logger.info(" Response JSON is [" + responseJSON + "]");

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
			logger.debug("Exception in Execute [" + e.getMessage() + "]");
			e.printStackTrace();
		}

		return result;
	}

	public String confirm() {
		return "success";
	}

	public String insert() {
		logger.debug("Inside Insert Method .. ");
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					ServletActionContext.getRequest().getSession()
							.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());
			requestJSON.put("accountPropBean", accountPropBean);
			requestJSON.put("action", "insert");
			requestDTO.setRequestJSON(requestJSON);

			responseDTO = new AccountPropertiesDAO()
					.insertProperties(requestDTO);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"ACRECORDS");
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
			logger.debug("Exception in Insert [" + e.getMessage() + "]");
			e.printStackTrace();
		}

		return result;

	}

	public String update() {

		logger.debug("Inside Update Method .. ");
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					ServletActionContext.getRequest().getSession()
							.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());
			requestJSON.put("accountPropBean", accountPropBean);
			requestJSON.put("action", "update");
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new AccountPropertiesDAO().updateProperties(requestDTO);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"ACRECORDS");
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
			logger.debug("Exception in Update [" + e.getMessage() + "]");
			e.printStackTrace();
		}

		return result;

	}
	
	
	
	public String FeeCreateScreenDetails() {
		logger.debug("Inside FeeCreateScreenDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,subServiceCode);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			//responseDTO = serviceDAO.FeeCreateScreenDetails(requestDTO);
			responseDTO = new AccountPropertiesDAO().updateProperties(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.FEE_INFO);
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
			logger.debug("Exception in FeeCreateScrrenDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			serviceDAO = null;
		}
		
		return result;
	}

	public String FeeCreateSubmitDetails() {
		logger.debug("Inside FeeCreateSubmitDetails... ");

		try {
			responseJSON = new JSONObject();
			responseJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			responseJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			responseJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,subServiceCode);
			responseJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,subServiceName);
			
			responseJSON.put(CevaCommonConstants.FEENAME,feename);
			responseJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			responseJSON.put(CevaCommonConstants.SERVICE_TYPE, serviceType);
			responseJSON.put(CevaCommonConstants.SLAB_FROM, slabFrom);
			responseJSON.put(CevaCommonConstants.SLAB_TO, slabTo);
			responseJSON.put(CevaCommonConstants.FALT_PERCENT, flatPercent);
			responseJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,accountMultiData);
			responseJSON.put(CevaCommonConstants.SERVICE, service);
			responseJSON.put("serviceTrans", serviceTrans);
			responseJSON.put("partnerDetails", partnerDetails);
			responseJSON.put("multiSlabFeeDetails", multiSlabFeeDetails);
			responseJSON.put("offusTrnDetails", offusTrnDetails);
			logger.debug("Bin Details::"+offusTrnDetails);
			responseJSON.put("merchantname",merchantname);
			responseJSON.put("feename",feename);

			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in FeeCreateSubmitDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String insertFeeDetails() {
		logger.debug("Inside InsertFeeDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			request = ServletActionContext.getRequest();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,subServiceCode);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,subServiceName);
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestJSON.put(CevaCommonConstants.SERVICE_TYPE, serviceType);
			requestJSON.put(CevaCommonConstants.SLAB_FROM, slabFrom);
			requestJSON.put(CevaCommonConstants.SLAB_TO, slabTo);
			requestJSON.put(CevaCommonConstants.SERVICE, service);
			requestJSON.put(CevaCommonConstants.HUDUMA_SERVICE_NAME,hudumaServiceName);
			requestJSON.put(CevaCommonConstants.FALT_PERCENT, flatPercent);
			requestJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,accountMultiData);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			requestJSON.put("serviceTrans", serviceTrans);
			requestJSON.put("partnerDetails", partnerDetails);
			requestJSON.put("multiSlabFeeDetails", multiSlabFeeDetails);
			requestJSON.put("offusTrnDetails", offusTrnDetails);
			
			requestJSON.put("merchantname", "");
			requestJSON.put("feename", feename);
			//requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.insertFeeDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages " + messages + "");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}

				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug("Errors " + errors + "");
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}

			responseJSON = requestJSON;
			//responseJSON.put("actionmessage", errors);
			logger.debug("Response JSON [" + responseJSON + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertFeeDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			serviceDAO = null;
		}

		return result;
	}
	
	public String commonScreen() {
		logger.debug("Inside CommonScreen .. ");
		return SUCCESS;
	}
	
	public String fetchServices() {

		logger.debug("Inside FetchService Details...  ");
		AccountPropertiesDAO accpropDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Before setting to DAO.");

			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchServices(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);
				accountPropBean.setResponseJSON(responseJSON);
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
			logger.debug("Exception in fetch service ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			accpropDAO = null;
			errors = null;
		}

		return result;
	}
	

	public String servicepackinsert() {

		logger.debug("Inside Service Pack Insert .. ");
		ArrayList<String> errors = null;
		AccountPropertiesDAO accpropDAO = null;
		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("accountPropBean", accountPropBean);
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.insertservicepack(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccountPropBean((AccountPropertiesBean) responseDTO.getData().get(
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
	
	
	public String insertproductdet() {
		
		logger.debug("Inside Service Pack Insert .. ");
		ArrayList<String> errors = null;
		AccountPropertiesDAO accpropDAO = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			
			requestJSON.put("accountPropBean", accountPropBean);
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);
			
			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.insertproductlimits(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccountPropBean((AccountPropertiesBean) responseDTO.getData().get(
						"AccountData"));
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug("errors in insertproductdet Action "+errors);
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
	
	
	public String retrieveMenuDetails() {

		logger.debug("Inside FetchService Details...  ");
		AccountPropertiesDAO accpropDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestJSON.put("accountPropBean", accountPropBean);
			//requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			//requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Before setting to DAO.");

			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchMenuDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);
				System.out.println("responce in action &&&&&&&&&&&"+responseJSON);
				accountPropBean.setResponseJSON(responseJSON);
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
			logger.debug("Exception in fetch service ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			accpropDAO = null;
			errors = null;
		}

		return result;
	}
	
	
	public String productCreationDetails() {

		logger.debug("Inside productCreationDetails Details...  ");
		AccountPropertiesDAO accpropDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Before setting to DAO in productCreationDetails");

			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchMenulists(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);
				accountPropBean.setResponseJSON(responseJSON);
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
			logger.debug("Exception in fetch service ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			accpropDAO = null;
			errors = null;
		}

		return result;
	}
	
	
	
	public String fetchSPacks() {

		logger.debug("Inside Fetch Institute Id .. ");
		AccountPropertiesDAO accpropDAO = null;
		ArrayList<String> errors = null;

		try {

			session = ServletActionContext.getRequest().getSession();

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			//responseDTO = new AddNewAccountDAO().fetchInstdetails(requestDTO);
			
			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchSpacks(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("INSTDETAILS");
				accountPropBean.setResponseJSON(responseJSON);
				logger.debug("Response JSON::" + responseJSON);
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
	
	

	public String fetchspdetails() {

		logger.debug("Inside Service Pack Insert .. ");
		ArrayList<String> errors = null;
		AccountPropertiesDAO accpropDAO = null;
		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("accountPropBean", accountPropBean);
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchspdet(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccountPropBean((AccountPropertiesBean) responseDTO.getData().get(
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
	
	
	public String getServiceName() {
		return serviceName;
	}
	

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}
	
	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	

	@Override
	public AccountPropertiesBean getModel() {
		return accountPropBean;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the accountPropBean
	 */
	public AccountPropertiesBean getAccountPropBean() {
		return accountPropBean;
	}

	/**
	 * @param accountPropBean
	 *            the accountPropBean to set
	 */
	public void setAccountPropBean(AccountPropertiesBean accountPropBean) {
		this.accountPropBean = accountPropBean;
	}

}
