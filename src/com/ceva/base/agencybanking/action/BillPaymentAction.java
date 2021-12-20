package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.BillPaymentDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class BillPaymentAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(BillPaymentAction.class);

	private String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private String billerName;
	private String biller;
	private String customerKey;
	private String modeOfPayment;
	private String amount;
	private String referenceNo;
	private String transPin;
	private String mobileNo;

	public String getCommonScreen() {
		logger.debug("inside GetCommonScreen.");
		result = "success";
		return result;
	}

	public String BillPaymentCreateDetails() {
		logger.debug("inside  BillPaymentCreate Details.");
		BillPaymentDAO billerDAO = null;

		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			logger.debug("Request JSON [" + requestJSON + "]");
			requestDTO.setRequestJSON(requestJSON);

			billerDAO = new BillPaymentDAO();
			responseDTO = billerDAO.BillPaymentCreateDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}

				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BILLER_LIST);
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
			logger.debug("Exception in BillPayment Create Details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			billerDAO = null;
			errors = null;
		}

		return result;
	}

	public String billPaymentSubmitDetails() {
		logger.debug("inside BillPaymentSubmit Details.");
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestJSON.put(CevaCommonConstants.BILLER_ID, biller);
			requestJSON.put(CevaCommonConstants.BILLER, billerName);
			requestJSON.put(CevaCommonConstants.CUSTOMER_KEY, customerKey);
			requestJSON.put(CevaCommonConstants.MODE_OF_PAYMENT, modeOfPayment);
			requestJSON.put(CevaCommonConstants.AMOUNT, amount);

			if (mobileNo != null)
				requestJSON.put(CevaCommonConstants.MOBILE_NUMBER, mobileNo);
			responseJSON = requestJSON;
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in BillPayment Submit Details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

		}

		return result;
	}

	public String billPayVerifyPin() {
		logger.debug("inside BillPayVerifyPin. ");
		HttpSession session = null;
		BillPaymentDAO billerDAO = null;

		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.BILLER_ID, biller);
			requestJSON.put(CevaCommonConstants.BILLER, billerName);
			requestJSON.put(CevaCommonConstants.CUSTOMER_KEY, customerKey);
			requestJSON.put(CevaCommonConstants.MODE_OF_PAYMENT, modeOfPayment);
			requestJSON.put(CevaCommonConstants.AMOUNT, amount);

			if (mobileNo != null) {
				requestJSON.put(CevaCommonConstants.MOBILE_NUMBER, mobileNo);
			}

			requestJSON.put(CevaCommonConstants.PIN, transPin);

			logger.debug("Request JSON [" + requestJSON + "]");

			requestDTO.setRequestJSON(requestJSON);

			billerDAO = new BillPaymentDAO();
			responseDTO = billerDAO.billPayVerifyPin(requestDTO);
			logger.debug("ResponseDTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug("Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
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
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in BillPayment Submit Details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			billerDAO = null;
		}

		return result;
	}

	public String insertBillDetails() {
		logger.debug("inside InsertBill Details. ");
		HttpSession session = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		BillPaymentDAO billerDAO = null;

		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			result = "success";
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.BILLER_ID, biller);
			requestJSON.put(CevaCommonConstants.CUSTOMER_KEY, customerKey);
			requestJSON.put(CevaCommonConstants.MODE_OF_PAYMENT, modeOfPayment);
			requestJSON.put(CevaCommonConstants.AMOUNT, amount);

			logger.debug("inside [mobileNo::" + mobileNo + "]");

			if (mobileNo != null) {
				requestJSON.put(CevaCommonConstants.MOBILE_NUMBER, mobileNo);
			}

			logger.debug("Request JSON [" + requestJSON + "]");

			requestDTO.setRequestJSON(requestJSON);

			billerDAO = new BillPaymentDAO();
			responseDTO = billerDAO.insertBillDetails(requestDTO);
			logger.debug("[responseDTO:::::" + responseDTO + "]");

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
			logger.debug("Exception in InsertBill Details [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			billerDAO = null;
		}

		return result;
	}

	public String getMPesaDashBoard() {

		logger.debug("inside GetMPesa DashBoard.");
		BillPaymentDAO billerDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			billerDAO = new BillPaymentDAO();
			responseDTO = billerDAO.getMPesaDashBoard(requestDTO);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
				logger.debug("Response JSON[" + responseJSON + "]");
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
			logger.debug("Exception in MPesaDashBoard Details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			billerDAO = null;
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

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getBiller() {
		return biller;
	}

	public void setBiller(String biller) {
		this.biller = biller;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTransPin() {
		return transPin;
	}

	public void setTransPin(String transPin) {
		this.transPin = transPin;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
