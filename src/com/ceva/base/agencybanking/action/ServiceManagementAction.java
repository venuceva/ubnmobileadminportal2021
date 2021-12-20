package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.dao.BankDAO;
import com.ceva.base.common.dao.MerchantDAO;
import com.ceva.base.common.dao.ServiceManagementDAO;
import com.ceva.base.common.dao.SwitchUIDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class ServiceManagementAction extends ActionSupport implements
		ServletRequestAware {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ServiceManagementAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	JSONObject subServiceJSON = null;
	JSONObject feeJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private HttpServletRequest request = null;

	private String result;
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

	private String bankMultiData;
	private String bin;
	private String binDescription;
	private String bankCode;
	private String bankName;
	private String settlementAccount;
	private String serviceNameDesc;
	private String settlementAccountDesc;
	private String virtualCard;
	private String hudumaServiceCode;
	private String hudumaServiceDesc;
	private String hudumaService;
	private String hudumaSubServiceName;
	private String hudumaSubService;
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

	private String subservice;
	private String merchantId;
	private String selectUsers;
	private String selectUsersText;
	private String mrcode;
	private String FeeCodeslab;
	private String acqdetails;
	private String subservicecode;
	private String mrfeeCode;
	private String modifyfeecode;
	private String fromslab;
	private String toslab;
	private String offUsFee;
	
	
	private HttpServletRequest httpRequest = null;
	
	
	public String getOffUsFee() {
		return offUsFee;
	}

	public void setOffUsFee(String offUsFee) {
		this.offUsFee = offUsFee;
	}

	public String getMrfeeCode() {
		return mrfeeCode;
	}

	public void setMrfeeCode(String mrfeeCode) {
		this.mrfeeCode = mrfeeCode;
	}

	public String getAcqdetails() {
		return acqdetails;
	}

	public void setAcqdetails(String acqdetails) {
		this.acqdetails = acqdetails;
	}

	public String getFeeCodeslab() {
		return FeeCodeslab;
	}

	public void setFeeCodeslab(String feeCodeslab) {
		FeeCodeslab = feeCodeslab;
	}

	private HttpSession session = null;

	public String getCommonScreen() {
		logger.debug("Inside GetCommonScreen..");
		result = "success";
		return result;
	}

	public String getServiceInfo() {
		logger.debug("Inside getServiceInfo... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getServiceInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				subServiceJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SUB_SERVICE_INFO);
				logger.debug("SubService JSON [" + subServiceJSON + "]");
				feeJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.FEE_INFO);
				logger.debug("Fee JSON [" + feeJSON + "]");
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
			logger.debug("Exception in getServiceInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String nextServiceCode() {
		logger.debug("Inside GetNextServiceCode...");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getNextServiceCode(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_INFO);
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
			logger.debug("Exception in getServiceInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String getServiceCreateConfirmScreen() {
		logger.debug("Inside GetServiceCreateConfirmScreen... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SETTLEMENT_ACCOUNT,
					settlementAccount == null ? " " : settlementAccount);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME_DESC,
					serviceNameDesc == null ? " " : serviceNameDesc);
			requestJSON
					.put(CevaCommonConstants.SETTLEMENT_DESC,
							settlementAccountDesc == null ? " "
									: settlementAccountDesc);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.checkServiceCode(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug("Errors [" + errors + "]");
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}

			responseJSON = (JSONObject) responseDTO.getData().get(
					CevaCommonConstants.SERVICE_INFO);
			logger.debug("Response JSON [" + responseJSON + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getServiceInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String insertService() {
		logger.debug("Inside InsertService.. ");

		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			httpRequest=ServletActionContext.getRequest();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SETTLEMENT_ACCOUNT,
					settlementAccount);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME_DESC,
					serviceNameDesc);
			requestJSON.put(CevaCommonConstants.SETTLEMENT_DESC,
					settlementAccountDesc);

			requestJSON.put(CevaCommonConstants.IP, httpRequest.getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.insertService(requestDTO);
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
			serviceDAO = null;
		}

		return result;
	}

	public String ServiceViewDetails() {
		logger.debug("Inside ServiceViewDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.ServiceViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.SERVICE_INFO);
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
			logger.debug("Exception in ServiceViewDetails [" + e.getMessage()
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

	public String SubServiceCreateScreenDetails() {
		logger.debug("Inside SubServiceCreateScreenDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);

			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.SubServiceCreateScreenDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_INFO);
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
			logger.debug("Exception in SubServiceCreateScreenDetails ["
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

	public String SubServiceCreateSubmitDetails() {
		logger.debug("Inside SubServiceCreateScreenDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
					subServiceCode);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
					subServiceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_TEXT,
					subServiceText);

			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.checkSubServiceCreateScreenDetails(requestDTO);
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
			responseJSON = (JSONObject) responseDTO.getData().get(
					CevaCommonConstants.SERVICE_INFO);
			logger.debug("Response JSON [" + responseJSON + "]");

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in SubServiceCreateScreenDetails ["
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

	public String insertSubService() {
		logger.debug("Inside InsertSubService... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			httpRequest=ServletActionContext.getRequest();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
					subServiceCode);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
					subServiceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_TEXT,
					subServiceText);
			requestJSON.put(CevaCommonConstants.IP, httpRequest.getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.insertSubService(requestDTO);
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
			logger.debug("Exception in InsertSubService [" + e.getMessage()
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

	public String FeeCreateScrrenDetails() {
		logger.debug("Inside FeeCreateScreenDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, subServiceCode);
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);


			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.FeeCreateScrrenDetails(requestDTO);
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
			responseJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,					subServiceCode);
			responseJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,subServiceName);

			responseJSON.put(CevaCommonConstants.FEENAME,feename);
			responseJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			responseJSON.put(CevaCommonConstants.SERVICE_TYPE, serviceType);
			responseJSON.put(CevaCommonConstants.SLAB_FROM, slabFrom);
			responseJSON.put(CevaCommonConstants.SLAB_TO, slabTo);
			responseJSON.put(CevaCommonConstants.FALT_PERCENT, flatPercent);
			responseJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,
					accountMultiData);
			responseJSON.put(CevaCommonConstants.SERVICE, service);
			responseJSON.put(CevaCommonConstants.HUDUMA_SERVICE_NAME,
					hudumaServiceName);
			responseJSON.put(CevaCommonConstants.HUDUMA_SUB_SERVICE,
					hudumaSubService);
			responseJSON.put(CevaCommonConstants.HUDUMA_SUB_SERVICE_NAME,
					hudumaSubServiceName);
			responseJSON.put("serviceTrans", serviceTrans);
			responseJSON.put("partnerDetails", partnerDetails);
			responseJSON.put("multiSlabFeeDetails", multiSlabFeeDetails);
			responseJSON.put("offusTrnDetails", offusTrnDetails);

			responseJSON.put("merchantname", merchantname);
			responseJSON.put("feename", feename);
			
			if(offUsFee == null)
				offUsFee = "0";
			
			responseJSON.put("offUsFee", offUsFee);

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
			httpRequest=ServletActionContext.getRequest();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.SERVICE_CODE, serviceCode);
			requestJSON.put(CevaCommonConstants.SERVICE_NAME, serviceName);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
					subServiceCode);
			requestJSON.put(CevaCommonConstants.SUB_SERVICE_NAME,
					subServiceName);
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestJSON.put(CevaCommonConstants.SERVICE_TYPE, serviceType);
			requestJSON.put(CevaCommonConstants.SLAB_FROM, slabFrom);
			requestJSON.put(CevaCommonConstants.SLAB_TO, slabTo);
			requestJSON.put(CevaCommonConstants.SERVICE, service);
			requestJSON.put(CevaCommonConstants.HUDUMA_SERVICE_NAME,
					hudumaServiceName);
			requestJSON.put(CevaCommonConstants.FALT_PERCENT, flatPercent);
			requestJSON.put(CevaCommonConstants.ACCOUNT_MULTI_DATA,
					accountMultiData);
			requestJSON.put(CevaCommonConstants.HUDUMA_SUB_SERVICE,
					hudumaSubService);

			requestJSON.put("serviceTrans", serviceTrans);
			requestJSON.put("partnerDetails", partnerDetails);
			requestJSON.put("multiSlabFeeDetails", multiSlabFeeDetails);
			requestJSON.put("offusTrnDetails", offusTrnDetails);

			requestJSON.put("merchantname", "");
			requestJSON.put("feename", feename);
			requestJSON.put(CevaCommonConstants.IP, httpRequest.getRemoteAddr());
			
			
			if(offUsFee == null)
				offUsFee = "0";
			
			requestJSON.put("offUsFee", offUsFee);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.insertFeeDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
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
			responseJSON.put("actionmessage", errors);
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

	public String viewSubServiceDetails() {
		logger.debug("Inside ViewSubServiceDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SUB_SERVICE_CODE,
					subServiceCode);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.viewSubServiceDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_INFO);
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

	public String viewFeeDetails() {
		logger.debug("Inside ViewFeeDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.viewFeeDetails(requestDTO);
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
			logger.debug("Exception in ViewFeeDetails [" + e.getMessage() + "]");
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

	public String ModifyFeeCodeDetails() {
		logger.debug("Inside ViewFeeDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			logger.debug("feename__________________ [" + feename + "]");
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestJSON.put("feename", feename);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.ModifyFeeCodeDetails(requestDTO);
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
			logger.debug("Exception in ViewFeeDetails [" + e.getMessage() + "]");
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

	public String getMerchantFeeDetails() {
		logger.debug("Inside ViewFeeDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			logger.debug("modifyfeecode>>>>>>>[" + modifyfeecode + "]");
			requestJSON.put(CevaCommonConstants.FEE_CODE, modifyfeecode);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.viewFeeDetails(requestDTO);
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
			logger.debug("Exception in ViewFeeDetails [" + e.getMessage() + "]");
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

	public String getBankDetails() {
		logger.debug("Inside GetBankDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getBankDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"BANK_LIST");
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
			logger.debug("Exception in getBankDetails [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String registerBinSubmitDetails() {
		logger.debug("Inside RegisterBinSubmitDetails... ");
		try {
			responseJSON = new JSONObject();

			responseJSON.put(CevaCommonConstants.BANK_CODE, bankCode);
			responseJSON.put(CevaCommonConstants.BANK_NAME, bankName);
			responseJSON.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			logger.debug("Response JSON[" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in registerBinSubmitDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String inserRegisterBin() {
		logger.debug("Inside InserRegisterBin... ");

		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.BANK_CODE, bankCode);
			requestJSON.put(CevaCommonConstants.BANK_NAME, bankName);
			requestJSON.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr() );

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.inserRegisterBin(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
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
			logger.debug("Exception in inserRegisterBin [" + e.getMessage()
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

	public String registerProcessingCodeSubmitDetails() {
		logger.debug("Inside RegisterProcessingCodeSubmitDetails... ");
		try {

			responseJSON = new JSONObject();

			responseJSON
					.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			logger.debug("Response JSON[" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in registerProcessingCodeSubmitDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String inserProcessingCode() {
		logger.debug("Inside InserProcessingCode ... ");

		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.inserProcessingCode(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
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
			logger.debug("Exception in inserProcessingCode [" + e.getMessage()
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

	public String inserhudumaService() {
		logger.debug("Inside InserhudumaService.. ");

		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.HUDUMA_SERVICE_CODE,
					hudumaServiceCode);
			requestJSON.put(CevaCommonConstants.HUDUMA_SERVICE_DESC,
					hudumaServiceDesc);
			requestJSON.put(CevaCommonConstants.VIRTUAL_CARD, virtualCard);
			requestJSON.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			logger.debug("Resquest JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.inserhudumaService(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
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
			logger.debug("Exception in InserhudumaService [" + e.getMessage()
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

	public String registerHuduamServiceSubmitDetails() {
		logger.debug("Inside RegisterHuduamServiceSubmitDetails... ");

		try {
			responseJSON = new JSONObject();

			responseJSON
					.put(CevaCommonConstants.BANK_MULTI_DATA, bankMultiData);
			responseJSON.put(CevaCommonConstants.HUDUMA_SERVICE_CODE,
					hudumaServiceCode);
			responseJSON.put(CevaCommonConstants.HUDUMA_SERVICE_DESC,
					hudumaServiceDesc);
			responseJSON.put(CevaCommonConstants.VIRTUAL_CARD, virtualCard);
			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in registerHuduamServiceSubmitDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String getBinViewDetails() {

		logger.debug("Inside GetBinViewDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getBinViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
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
			logger.debug("Exception in GetBinViewDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String getProcessingCodeViewDetails() {

		logger.debug("Inside GetProcessingCodeViewDetails..");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getProcessingCodeViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
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
			logger.debug("Exception in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String getHudumaServiceViewDetails() {

		logger.debug("Inside GetHudumaServiceViewDetails... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getHudumaServiceViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
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
			logger.debug("Exception in GetHudumaServiceViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String getFeeDashBoard() {
		logger.debug("Inside GetFeeDashBoard... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.getFeeDashBoard(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
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
			logger.debug("Exception in GetFeeDashBoard [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String feeAuthorization() {

		logger.debug("Inside FeeAuthorization... ");
		ServiceManagementDAO serviceDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			try {
				rb = ResourceBundle.getBundle("resource/headerdata");
			} catch (Exception e) {
				logger.debug("Exception while loading bundle please check the file.... headerdata under 'classes/resource' folder.");
			}

			requestJSON.put(CevaCommonConstants.STATUS, service);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.feeAuthorizationData(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(	CevaCommonConstants.RESPONSE_JSON);

				if (service.equalsIgnoreCase("serv")) {
					search = "service";
				} else if (service.equalsIgnoreCase("subserv")) {
					search = "subservice";
				} else if (service.equalsIgnoreCase("proc")) {
					search = "proccode";
				} else if (service.equalsIgnoreCase("BinAuth")) {
					search = "binCode";
				} else {
					search = "feecode";
				}

				logger.debug("Search String is [" + search + "]");

				responseJSON.put("key_data", search);
				responseJSON	.put("headerData", rb.getString(search + ".header"));
				responseJSON.put("search", search == null ? " " : search);
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
			logger.debug("Exception in GetFeeDashBoard [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String feeAuthorizationCnf() {

		logger.debug("Inside FeeAuthorizationCnf... ");
		try {
			responseJSON = constructToResponseJson(request);

			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in FeeAuthorizationCnf [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String feeAuthorizationBack() {

		logger.debug("Inside feeAuthorizationBack... ");
		try {
			responseJSON = constructToResponseJson(request);

			logger.debug("Response JSON [" + responseJSON + "]");

			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in feeAuthorizationBack [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String feeAuthorizationAck() {

		logger.debug("Inside feeAuthorizationAck... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ServiceManagementDAO serviceDAO = null;
		try {
			session = ServletActionContext.getRequest().getSession();

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? "NO"
					: session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put(CevaCommonConstants.ID,selectedUserText == null ? "NO" : selectedUserText);

			requestJSON.put(CevaCommonConstants.STATUS, status == null ? "NO" : status);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");

			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.feeAuthorizationAck(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
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
			logger.debug("Exception in feeAuthorizationAck [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}

		return result;
	}

	public String merchantFeeAssign() {

		logger.debug("Inside merchantFeeAssign... ");
		ArrayList<String> errors = null;
		ServiceManagementDAO serviceDAO = null;
		try {
			// session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");

			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.merchantFeeAssign(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"LIST_MERCHANT_DETAILS");
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
			logger.debug("Exception in merchantFeeAssign [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
		}
		return result;
	}

	public String merchantFeeAssignCnf() {

		logger.debug("Inside MerchantFeeAssignCnf... ");
		try {
			responseJSON = constructToResponseJson(request);

			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in MerchantFeeAssignCnf [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}


	public String merchantFeeAssignAck() {

		logger.debug("Inside merchantFeeAssignAck... ");
		ArrayList<String> errors = null;
		ServiceManagementDAO serviceDAO = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("merch_id", merchantId);
		//	requestJSON.put("txn_type", subservice);
			//requestJSON.put("txn_type", subservicecode);
			requestJSON.put("selected_users", selectUsers);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");

			serviceDAO = new ServiceManagementDAO();
			responseDTO = serviceDAO.merchantFeeAssignAck(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LIST_MERCHANT_DETAILS");
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
			logger.debug("Exception in merchantFeeAssignAck [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			serviceDAO = null;
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



public String MerchantFeeviewDetails() {
	logger.debug("Inside CheckSwitchStaus.. ");
	ServiceManagementDAO serviceDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");

		requestJSON.put("mrcode", mrcode);
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request JSON [" + requestJSON + "]");

		serviceDAO = new ServiceManagementDAO();
		responseDTO = serviceDAO.getDetails(requestDTO);

		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("MERCHANTfEEDETAILS");
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
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
				+ "]");
		addActionError("Internal error occured.");
		result = "fail";
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		errors = null;
	}

	return result;
}


public String UpdateSlabDetails() {

	logger.debug("Inside UpdateSlabDetails.. ");
	ServiceManagementDAO serviceDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			httpRequest=ServletActionContext.getRequest();
			session = ServletActionContext.getRequest().getSession();

		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		logger.debug("serviceCode>>>>>>>>>>[" + serviceCode + "]");
		logger.debug("fromslab>>>>>>>>>>[" + fromslab + "]");
		requestJSON.put("MAKER_ID",session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("multiSlabFeeDetails", multiSlabFeeDetails);
		requestJSON.put("FeeCodeslab", FeeCodeslab);
		requestJSON.put("serviceCode", serviceCode);

		requestJSON.put("serviceType", serviceType);
		requestJSON.put("subServiceCode", subServiceCode);
		requestJSON.put("acqdetails", acqdetails);
		requestJSON.put("feename", feename);
		requestJSON.put(CevaCommonConstants.IP, httpRequest.getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request JSON [" + requestJSON + "]");

		serviceDAO = new ServiceManagementDAO();
		responseDTO = serviceDAO.UpdateFeeDetails(requestDTO);

		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

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
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
				+ "]");
		addActionError("Internal error occured.");
		result = "fail";
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		errors = null;
	}

	return result;
}



public String getMerchantAssignServices() {
	logger.debug("Inside Merchant Assign Service... ");
	ServiceManagementDAO serviceDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		serviceDAO = new ServiceManagementDAO();
		responseDTO = serviceDAO.GetMerchantAssignService(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("MERCHANT_ASSIGN_DASHBOARD");
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
		logger.debug("Exception in Get Assin Merchant DashBoard [" + e.getMessage()
				+ "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		errors = null;
		serviceDAO = null;
	}

	return result;
}






	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSubServiceCode() {
		return subServiceCode;
	}

	public void setSubServiceCode(String subServiceCode) {
		this.subServiceCode = subServiceCode;
	}

	public String getSubServiceName() {
		return subServiceName;
	}

	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}

	public String getSubServiceText() {
		return subServiceText;
	}

	public void setSubServiceText(String subServiceText) {
		this.subServiceText = subServiceText;
	}

	public JSONObject getSubServiceJSON() {
		return subServiceJSON;
	}

	public void setSubServiceJSON(JSONObject subServiceJSON) {
		this.subServiceJSON = subServiceJSON;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSlabFrom() {
		return slabFrom;
	}

	public void setSlabFrom(String slabFrom) {
		this.slabFrom = slabFrom;
	}

	public String getSlabTo() {
		return slabTo;
	}

	public void setSlabTo(String slabTo) {
		this.slabTo = slabTo;
	}

	public String getFlatPercent() {
		return flatPercent;
	}

	public void setFlatPercent(String flatPercent) {
		this.flatPercent = flatPercent;
	}

	public String getAccountMultiData() {
		return accountMultiData;
	}

	public void setAccountMultiData(String accountMultiData) {
		this.accountMultiData = accountMultiData;
	}

	public JSONObject getFeeJSON() {
		return feeJSON;
	}

	public void setFeeJSON(JSONObject feeJSON) {
		this.feeJSON = feeJSON;
	}

	public String getHudumaServiceName() {
		return hudumaServiceName;
	}

	public void setHudumaServiceName(String hudumaServiceName) {
		this.hudumaServiceName = hudumaServiceName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getBankMultiData() {
		return bankMultiData;
	}

	public void setBankMultiData(String bankMultiData) {
		this.bankMultiData = bankMultiData;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBinDescription() {
		return binDescription;
	}

	public void setBinDescription(String binDescription) {
		this.binDescription = binDescription;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSettlementAccount() {
		return settlementAccount;
	}

	public void setSettlementAccount(String settlementAccount) {
		this.settlementAccount = settlementAccount;
	}

	public String getServiceNameDesc() {
		return serviceNameDesc;
	}

	public void setServiceNameDesc(String serviceNameDesc) {
		this.serviceNameDesc = serviceNameDesc;
	}

	public String getSettlementAccountDesc() {
		return settlementAccountDesc;
	}

	public void setSettlementAccountDesc(String settlementAccountDesc) {
		this.settlementAccountDesc = settlementAccountDesc;
	}

	public String getVirtualCard() {
		return virtualCard;
	}

	public void setVirtualCard(String virtualCard) {
		this.virtualCard = virtualCard;
	}

	public String getHudumaServiceCode() {
		return hudumaServiceCode;
	}

	public void setHudumaServiceCode(String hudumaServiceCode) {
		this.hudumaServiceCode = hudumaServiceCode;
	}

	public String getHudumaServiceDesc() {
		return hudumaServiceDesc;
	}

	public void setHudumaServiceDesc(String hudumaServiceDesc) {
		this.hudumaServiceDesc = hudumaServiceDesc;
	}

	public String getHudumaService() {
		return hudumaService;
	}

	public void setHudumaService(String hudumaService) {
		this.hudumaService = hudumaService;
	}

	public String getHudumaSubServiceName() {
		return hudumaSubServiceName;
	}

	public void setHudumaSubServiceName(String hudumaSubServiceName) {
		this.hudumaSubServiceName = hudumaSubServiceName;
	}

	public String getHudumaSubService() {
		return hudumaSubService;
	}

	public void setHudumaSubService(String hudumaSubService) {
		this.hudumaSubService = hudumaSubService;
	}

	public String getMultiSlabFeeDetails() {
		return multiSlabFeeDetails;
	}

	public void setMultiSlabFeeDetails(String multiSlabFeeDetails) {
		this.multiSlabFeeDetails = multiSlabFeeDetails;
	}

	public String getPartnerDetails() {
		return partnerDetails;
	}

	public void setPartnerDetails(String partnerDetails) {
		this.partnerDetails = partnerDetails;
	}

	public String getOffusTrnDetails() {
		return offusTrnDetails;
	}

	public void setOffusTrnDetails(String offusTrnDetails) {
		this.offusTrnDetails = offusTrnDetails;
	}

	public String getServiceTrans() {
		return serviceTrans;
	}

	public void setServiceTrans(String serviceTrans) {
		this.serviceTrans = serviceTrans;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getHudumaFlag() {
		return hudumaFlag;
	}

	public void setHudumaFlag(String hudumaFlag) {
		this.hudumaFlag = hudumaFlag;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSelectedUserText() {
		return selectedUserText;
	}

	public void setSelectedUserText(String selectedUserText) {
		this.selectedUserText = selectedUserText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getFeename() {
		return feename;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public String getSubservice() {
		return subservice;
	}

	public void setSubservice(String subservice) {
		this.subservice = subservice;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(String selectUsers) {
		this.selectUsers = selectUsers;
	}

	public String getMrcode() {
		return mrcode;
	}

	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
	}

	public String getSelectUsersText() {
		return selectUsersText;
	}

	public void setSelectUsersText(String selectUsersText) {
		this.selectUsersText = selectUsersText;
	}

	public String getSubservicecode() {
		return subservicecode;
	}

	public void setSubservicecode(String subservicecode) {
		this.subservicecode = subservicecode;
	}

	public String getModifyfeecode() {
		return modifyfeecode;
	}

	public void setModifyfeecode(String modifyfeecode) {
		this.modifyfeecode = modifyfeecode;
	}

	public String getFromslab() {
		return fromslab;
	}

	public void setFromslab(String fromslab) {
		this.fromslab = fromslab;
	}

	public String getToslab() {
		return toslab;
	}

	public void setToslab(String toslab) {
		this.toslab = toslab;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}


}
