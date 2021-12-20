package com.ceva.base.agencybanking.action;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.bytecode.opencsv.CSVReader;

import com.ceva.aestools.AES;
import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.bean.State;
import com.ceva.base.common.dao.WalletAddNewAccountDAO;
import com.ceva.base.common.dao.impl.LocalGovernmentDaoImpl;
import com.ceva.base.common.dao.impl.StateDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.spec.dao.StateDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class WalletAddNewAccountAction extends ActionSupport implements
ServletRequestAware, ModelDriven<AccountBean> {

private static final long serialVersionUID = 1L;
private Logger logger = Logger.getLogger(WalletAddNewAccountAction.class);

private JSONObject requestJSON = null;
private JSONObject responseJSON = null;
private ResponseDTO responseDTO = null;
private RequestDTO requestDTO = null;

private File fileUpload;
private String fileUploadFileName;
private String fileUploadContentType;

private InputStream fileInputStream=null;
private String fileName=null;

private HttpServletRequest request = null;
private HttpSession session = null;

ResourceBundle bundle = null;
private String result = null;
private String rege = null;
private String appid = null;
private String tomail=null;
private String status=null;
private String invstat=null;

private String fromDate = null;
private String toDate=null;
private String accountNumber=null;
private String accName=null;
private String amount = null;
private String teleNumber=null;
private String tranNumber=null;

private String filter=null;

private String monstatus=null;

private String casestatus=null;
private String caseemail=null;


private String mobileNumber=null;

private String firstName=null;
private String lastName=null;

List<State> states;
List<LocalGovernment> localGovs;



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

public String getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getCasestatus() {
return casestatus;
}

public void setCasestatus(String casestatus) {
this.casestatus = casestatus;
}

public String getCaseemail() {
return caseemail;
}

public void setCaseemail(String caseemail) {
this.caseemail = caseemail;
}


@Autowired
private AccountBean accBean = null;



public String commonScreen() {
logger.debug("Inside CommonScreen .. ");
return SUCCESS;
}

public String modiCustInfo() {
System.out.println("kailash here ::: ");
	logger.debug("Inside Insert New Account Info .. ");
	ArrayList<String> errors = null;
	ArrayList<String> succesmessage = null;
	WalletAddNewAccountDAO accountDAO = null;
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		accountDAO = new WalletAddNewAccountDAO();
		responseDTO = accountDAO.modifycustomerinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			/*setAccBean((AccountBean) responseDTO.getData().get(
					"AccountData"));*/
			succesmessage = (ArrayList<String>) responseDTO.getMessages();
			for (int i = 0; i < succesmessage.size(); i++) {
				addActionMessage(succesmessage.get(i));
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

public String fetchCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details  ");
	ArrayList<String> errors = null;
	/*StateDAO stateDAO = null;
	LocalGovernmentDAO governmentDAO = null;*/
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.fetchRegCustomerDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		
	
	

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get("AccountData"));
			/*System.out.println("dasdsadas"+(accBean.getBranchdetails()));*/
			result = "success";
			
			/*stateDAO = new StateDaoImpl();
			governmentDAO= new LocalGovernmentDaoImpl();
			localGovs = governmentDAO.getAll();
			states = stateDAO.getAll();*/
			
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

public String fetchWalletCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;
	
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.fetchRegWalletCustomerDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		
	
	

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get("AccountData"));
			
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





public String modifyCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.modifyCustomerServiceDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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


public String modifyCustInfoAck() {

	logger.debug("Inside Insert New Account Info .. ");
	ArrayList<String> errors = null;
	WalletAddNewAccountDAO accountDAO = null;
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		accountDAO = new WalletAddNewAccountDAO();
		responseDTO = accountDAO.modifyCustInfoAck(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String topupCustAck() {

logger.debug("Inside Insert New Account Info .. ");
ArrayList<String> errors = null;
WalletAddNewAccountDAO accountDAO = null;
try {
	session = ServletActionContext.getRequest().getSession();

	requestDTO = new RequestDTO();
	requestJSON = new JSONObject();

	requestJSON.put("accBean", accBean);
	requestJSON.put(CevaCommonConstants.MAKER_ID,
			session.getAttribute(CevaCommonConstants.MAKER_ID));
	requestJSON.put(CevaCommonConstants.IP, ServletActionContext
			.getRequest().getRemoteAddr());
	requestDTO.setRequestJSON(requestJSON);

	accountDAO = new WalletAddNewAccountDAO();
	responseDTO = accountDAO.topupCustAck(requestDTO);
	logger.debug("Response DTO [" + responseDTO + "]");

	if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		setAccBean((AccountBean) responseDTO.getData().get(
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

public String refundCustAck() {

	logger.debug("Inside Insert New Account Info .. ");
	ArrayList<String> errors = null;
	WalletAddNewAccountDAO accountDAO = null;
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		accountDAO = new WalletAddNewAccountDAO();
		responseDTO = accountDAO.refundCustAck(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String fetchCustomerDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.fetchServiceDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String deletefetchRegCustDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.deletefetchRegCustomerDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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


public String deleteAccounts() {

	logger.debug("Inside Insert New Account Info .. ");
	ArrayList<String> errors = null;
	WalletAddNewAccountDAO accountDAO = null;
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		accountDAO = new WalletAddNewAccountDAO();
		responseDTO = accountDAO.deleteAccounts(requestDTO);

		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String topupCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.topupCustomerServiceDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String refundCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.refundCustomerServiceDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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


public String walletfetchCustomerServiceDetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.walletfetchCustomerServiceDetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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


public String walletcustServiceActions() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());

		requestDTO.setRequestJSON(requestJSON);

		responseDTO = new WalletAddNewAccountDAO().walletcustServiceActions(requestDTO);

		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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

public String insertactdeactdetails() {
	logger.debug("Inside Account active Deactivation Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());

		requestDTO.setRequestJSON(requestJSON);
		responseDTO = new WalletAddNewAccountDAO()
				.insertaccountstatus(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get(
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


public String fetchbranchdatacapturedetails() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();

		requestJSON.put("accBean", accBean);
		requestJSON.put("makerId",
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		logger.debug("Request DTO [" + requestDTO + "]");
		responseDTO = new WalletAddNewAccountDAO()
				.fetchbranchdatacapturedetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");

		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			setAccBean((AccountBean) responseDTO.getData().get("AccountData"));
			System.out.println("dasdsadas"+(accBean.getBranchdetails()));
		
			
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

public String branchdatacaptureAck() {

	logger.debug("Inside Insert New Account Info .. ");
	ArrayList<String> errors = null;
	WalletAddNewAccountDAO accountDAO = null;
	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		

		requestJSON.put("accBean", accBean);
		requestJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext
				.getRequest().getRemoteAddr());
		requestDTO.setRequestJSON(requestJSON);

		accountDAO = new WalletAddNewAccountDAO();
		responseDTO = accountDAO.branchdatacaptureAck(requestDTO);
		logger.debug("Response DTO qqq [" + responseDTO.getErrors() + "]");
		errors = (ArrayList<String>) responseDTO.getErrors();
		for (int i = 0; i < errors.size(); i++) {
			
			if((errors.get(i)).equalsIgnoreCase("SUCCESS")){
				result = "success";
			}else{
				result = "fail";
			}
			
		}

		/*if ((responseDTO.getErrors()).toString()=="[[SUCCESS]]") {
			setAccBean((AccountBean) responseDTO.getData().get(
					"AccountData"));
			result = "success";
		} else {
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}*/
		
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

private String aesEncString(String string) {
AES aes = new AES();
aes.setKey();
aes.encrypt(string);
return aes.getEncryptedString();
}

private String aesDecString(String string) {
AES aes = new AES();
aes.setKey();
aes.decrypt(string);
return aes.getDecryptedString();
}

public JSONObject getResponseJSON() {
return responseJSON;
}

public void setResponseJSON(JSONObject responseJSON) {
this.responseJSON = responseJSON;
}

public AccountBean getAccBean() {
return accBean;
}

public void setAccBean(AccountBean accBean) {
this.accBean = accBean;
}

public InputStream getFileInputStream() {
return fileInputStream;
}
public void setFileInputStream(InputStream fileInputStream) {
this.fileInputStream = fileInputStream;
}
public String getFileName() {
return fileName;
}
public void setFileName(String fileName) {
this.fileName = fileName;
}

public String getRege() {
return rege;
}

public void setRege(String rege) {
this.rege = rege;
}

public String getAppid() {
return appid;
}

public void setAppid(String appid) {
this.appid = appid;
}

@Override
public AccountBean getModel() {
return accBean;
}

@Override
public void setServletRequest(HttpServletRequest arg0) {

}

public String getTomail() {
return tomail;
}

public void setTomail(String tomail) {
this.tomail = tomail;
}


public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getInvstat() {
return invstat;
}

public void setInvstat(String invstat) {
this.invstat = invstat;
}

public String getFromDate() {
return fromDate;
}

public void setFromDate(String fromDate) {
this.fromDate = fromDate;
}

public String getToDate() {
return toDate;
}

public void setToDate(String toDate) {
this.toDate = toDate;
}

public String getAccountNumber() {
return accountNumber;
}

public void setAccountNumber(String accountNumber) {
this.accountNumber = accountNumber;
}

public String getAccName() {
return accName;
}

public void setAccName(String accName) {
this.accName = accName;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getTeleNumber() {
return teleNumber;
}

public void setTeleNumber(String teleNumber) {
this.teleNumber = teleNumber;
}

public String getTranNumber() {
return tranNumber;
}

public void setTranNumber(String tranNumber) {
this.tranNumber = tranNumber;
}

public String getFilter() {
return filter;
}

public void setFilter(String filter) {
this.filter = filter;
}

public String getMonstatus() {
return monstatus;
}

public void setMonstatus(String monstatus) {
this.monstatus = monstatus;
}


}

