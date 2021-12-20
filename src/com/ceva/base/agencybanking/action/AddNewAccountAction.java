package com.ceva.base.agencybanking.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ceva.base.common.dao.AccountPropertiesDAO;
import com.ceva.base.common.dao.AddNewAccountDAO;
import com.ceva.base.common.dao.WalletAddNewAccountDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddNewAccountAction extends ActionSupport implements
		ServletRequestAware, ModelDriven<AccountBean> {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AddNewAccountAction.class);

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
	 private String user_status=null;
	 
	

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
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
	
	
	
	public String modiCustInfoss() {
		
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
	

	public String fetchInstituteid() {

		logger.debug("Inside Fetch Institute Id .. ");
		ArrayList<String> errors = null;

		try {

			session = ServletActionContext.getRequest().getSession();

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());
			// requestJSON.put("customerId", accBean.getCustomercode());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			responseDTO = new AddNewAccountDAO().fetchInstdetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("INSTDETAILS");
				accBean.setResponseJSON(responseJSON);
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

	public String commonScreen() {
		logger.debug("Inside CommonScreen .. ");
		return SUCCESS;
	}

	public String fetchCustomerAccountDetails() {
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
			responseDTO = new AddNewAccountDAO()
					.fetchCustomerAccountDetails(requestDTO);
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

				responseDTO = new AddNewAccountDAO()
						.fetchInstdetails(requestDTO);
				responseJSON = (JSONObject) responseDTO.getData().get(
						"INSTDETAILS");
				accBean.setResponseJSON(responseJSON);

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

	public String insertNewAccountInfo() {

		logger.debug("Inside Insert New Account Info .. ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.insertNewAccountInfo(requestDTO);

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

	public String fetchCustomerServiceDetails() {
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
			if((accBean.getApptype()).equalsIgnoreCase("MOBILE")){
			responseDTO = new AddNewAccountDAO()
					.fetchServiceDetails(requestDTO);
			}
			if((accBean.getApptype()).equalsIgnoreCase("WALLET")){
				responseDTO = new AddNewAccountDAO()
						.fetchServiceDetailsWallet(requestDTO);
				}
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
			
			responseDTO = new AddNewAccountDAO()
					.fetchCustomerDetails(requestDTO);
			
			
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

	public String custServiceActions() {
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

			responseDTO = new AddNewAccountDAO().fetchActionresult(requestDTO);

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

			responseDTO = new AddNewAccountDAO().fetchwalletActionresult(requestDTO);

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
	
	
	public String CustomerEnableDetails() {
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
			responseDTO = new AddNewAccountDAO().CustomerEnableDetails(requestDTO);
		
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
	
	
	public String walletCustomerEnableDetails() {
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
			responseDTO = new AddNewAccountDAO().walletCustomerEnableDetails(requestDTO);
		
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
			responseJSON= new JSONObject();

			requestJSON.put("accBean", accBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("user_status",user_status);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new AddNewAccountDAO()
					.insertaccountstatus(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccBean((AccountBean) responseDTO.getData().get(
						"AccountData"));
				responseJSON.put("user_status",user_status);
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
	
	
	
	public String insertwalletactdeactdetails() {
		logger.debug("Inside wallet Account active Deactivation Details .. ");
		ArrayList<String> errors = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON= new JSONObject();

			requestJSON.put("accBean", accBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("user_status",user_status);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new AddNewAccountDAO()
					.insertwalletactdeactdetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccBean((AccountBean) responseDTO.getData().get(
						"AccountData"));
				responseJSON.put("user_status",user_status);
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

	public String insertimeiactdeactdetails() {
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

			logger.debug("Response DTO [" + requestDTO + "]");
			responseDTO = new AddNewAccountDAO().insertimeistatus(requestDTO);
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

	public String validateupload() {
		logger.debug("Inside Account active Deactivation Details .. ");
		ArrayList<String> errors = null;
		String fname = null;

		List<String[]> data = null;
		List<String> list = null;
		JSONObject json = null;
		// AccountBean accBean = null;
		String sourcepath = null;
		CSVReader csvReader = null;
		int u=0;
		
	

		try {

			bundle = ResourceBundle.getBundle("pathinfo_config");
			sourcepath = bundle.getString("BULK_FILEUPLOAD_PATH");
			
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();

			fileUpload = accBean.getUploadfile();

			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48

			System.out.println("fileUpload valeu   " + fileUpload);

			fname = (String) dateFormat.format(date);
			File saveFilePath = new File(sourcepath + fname + ".csv");
			FileUtils.copyFile(fileUpload, saveFilePath);

			System.out.println("filesaved");

			logger.debug("Fileupload Path is [" + sourcepath + fname + ".csv"+ "]");

			csvReader = new CSVReader(new FileReader(sourcepath + fname+ ".csv"));

			logger.debug("before loop");
			data = csvReader.readAll();
			
			list = new ArrayList<String>();
			
			for (String[] dat11 : data) {
				System.out.println(Arrays.asList(dat11));
				
				//list.add()
				
				list.add(StringUtils.join(dat11,","));
				u=u+1;

			}
			System.out.println("List Values are "+list);
			logger.debug("data values " + data);
			
			logger.debug("Total Number of Records found in the sheet are" + u);
			
			requestJSON.put("fname", fname);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			System.out.println("prepread reqdto");
			logger.debug("Response DTO [" + requestDTO + "]");

			responseDTO = new AddNewAccountDAO().validatebulkfile(list,requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			logger.debug("Bean Value and file name ["+ accBean.getCustomerstatus() + "] " + fname);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				setAccBean((AccountBean) responseDTO.getData().get(
						"AccountData"));
				result = "success";
				getAccBean().setTotcount(u-1);
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			//responseJSON.put("TOTCNT",u);
			// result="success";
			csvReader.close();
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
	public String insertfile() {
		logger.debug("Inside Account active Deactivation Details .. ");
		ArrayList<String> errors = null;
		String fname = null;
		
		List<String[]> data = null;
		List<String> list = null;
		JSONObject json = null;
		String sourcepath = null;
		CSVReader csvReader = null;
		String pin = null;
		String encpin = null;
		
		try {
			
			bundle = ResourceBundle.getBundle("pathinfo_config");
			sourcepath = bundle.getString("BULK_FILEUPLOAD_PATH");
			
			session = ServletActionContext.getRequest().getSession();
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			fileUpload = accBean.getUploadfile();
			
			fname = accBean.getFullname();
			csvReader = new CSVReader(new FileReader(sourcepath + fname+ ".csv"));
			
			data = csvReader.readAll();
			
			list = new ArrayList<String>();
			for (String[] dat11 : data) {
				
				pin = CommonUtil.generatePassword(4);
				encpin = aesEncString(pin).trim();
				System.out.println(Arrays.asList(dat11));
				list.add(StringUtils.join(dat11, ",")+","+pin+","+encpin);
				//list.add(pin+","+encpin);
			}
			
			System.out.println("List Values are "+list);
			logger.debug("data values " + data);
			
			requestJSON.put("fname", fname);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);
			
			System.out.println("prepread reqdto");
			logger.debug("Response DTO [" + requestDTO + "]");
			
			responseDTO = new AddNewAccountDAO().insertbulkfile(list,requestDTO);
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
			csvReader.close();
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

	public String fetchRegCustDetails() {
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
			if((accBean.getApptype()).equalsIgnoreCase("MOBILE")){
				responseDTO = new AddNewAccountDAO()
						.fetchRegCustomerDetails(requestDTO);
				
				}
				if((accBean.getApptype()).equalsIgnoreCase("WALLET")){
					responseDTO = new AddNewAccountDAO()
						.walletfetchRegCustomerDetails(requestDTO);
					
					}
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
	
	
	public String fetchRegCustDetailsModify() {
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
			System.out.println("kailash ::: "+accBean.getApptype());
			if((accBean.getApptype()).equalsIgnoreCase("MOBILE")){
				responseDTO = new AddNewAccountDAO()
						.fetchRegCustDetailsModify(requestDTO);
				
				}
				if((accBean.getApptype()).equalsIgnoreCase("WALLET")){
					responseDTO = new AddNewAccountDAO()
						.walletfetchRegCustomerDetails(requestDTO);
					
					}
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


	public String fetchRegCustDetailsEnhancement() {
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
			if((accBean.getApptype()).equalsIgnoreCase("MOBILE")){
				responseDTO = new AddNewAccountDAO()
						.fetchRegCustDetailsEnhancement(requestDTO);
				
				}
				if((accBean.getApptype()).equalsIgnoreCase("WALLET")){
					responseDTO = new AddNewAccountDAO()
						.walletfetchRegCustomerDetails(requestDTO);
					
					}
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
	
	
	public String fetchRegCustDetailsFraud() {
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
			
				responseDTO = new AddNewAccountDAO()
						.fetchRegCustDetailsFraud(requestDTO);
				
				
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

	
	public String fetchRegCustDetailsFraudConfirm() {
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
			
				responseDTO = new AddNewAccountDAO().fetchRegCustDetailsFraudConfirm(requestDTO);
				
				
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
	
	public String fetchRegCustDetailsFraudAck() {
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
			
				responseDTO = new AddNewAccountDAO().fetchRegCustDetailsFraudConfirm(requestDTO);
				
				
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


	public String modiCustInfo() {

		logger.debug("Inside Insert New Account Info .. ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.modifycustomerinfo(requestDTO);
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
	
	public String modiCustInfoPinAck() {

		logger.debug("Inside Insert New Account Info .. ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.modiCustInfoPinAck(requestDTO);
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

	public String enhancementInfo() {

		logger.debug("Inside Insert New Account Info .. ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.enhancementInfo(requestDTO);
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

	
	public String AddNewAccountAck() {

		logger.debug("Inside Insert New Account Info .. ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.AddNewAccountAck(requestDTO);
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

	
	public String fetchAllDetails() {
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
			responseDTO = new AddNewAccountDAO().fetchAllDetails(requestDTO);
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

	public String fetchAccData() {
		result = "success";
		return result;
	}

	public String fetchSearchAccounts() {

		logger.debug("Inside Fetch Search Account Data .. ");

		ArrayList<String> errors = null;

		try {

			session = ServletActionContext.getRequest().getSession();

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put("accDetails", accBean.getAccDetails());

			if (accBean.getAccDetails().equalsIgnoreCase("MakerDate")
					|| accBean.getAccDetails().equalsIgnoreCase("CheckerDate")) {
				requestJSON.put("searchVal", accBean.getSearchDate());

			} else if (accBean.getAccDetails().equalsIgnoreCase(
					"CustomerStatus")) {

				requestJSON.put("searchVal", accBean.getStatus());

			} else if (accBean.getAccDetails().equalsIgnoreCase(
					"AuthorizationStatus")) {

				requestJSON.put("searchVal", accBean.getStatus1());

			} else {

				requestJSON.put("searchVal", accBean.getSearchVal());
			}

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			responseDTO = new AddNewAccountDAO()
					.fetchAccountdetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"INSTDETAILS1");
				accBean.setResponseJSON(responseJSON);

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
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
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

	public String smsTemplate() {

		result = "SUCCESS";
		return SUCCESS;

	}

	public String smsTemplateConfirm() {
		result = "SUCCESS";
		return SUCCESS;

	}

	public String smsTemplateInsert() {
		logger.debug("Inside smsTemplateInsert ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.insertTempdata(requestDTO);

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
			logger.debug("Exception in  Sms Template Details ["
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
	
	public String smsInsert() {
		
		logger.debug("Inside Single Sms Insertion ");
		ArrayList<String> errors = null;
		AddNewAccountDAO accountDAO = null;
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

			accountDAO = new AddNewAccountDAO();
			responseDTO = accountDAO.insertsms(requestDTO);

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
			addActionError("Internal Error Occured while Single SMS insertion, Please try again.");
			logger.debug("Exception in Single Sms Details [" + e.getMessage() + "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}
	
	public String keylogs() {
	logger.debug("Inside Fetch Customer Details .. ");
	ArrayList<String> errors = null;

	try {
		session = ServletActionContext.getRequest().getSession();

		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		logger.debug("filter value :::::::::"+filter);
		if(filter==null)	{
			logger.debug("In To No1 Filter"+filter);
			requestJSON.put("REQUEST_TYPE","NONE");
			logger.debug("In To No Filter"+filter);
		}else{	
		requestJSON.put("REQUEST_TYPE",getFilter());
			
		logger.debug("monstatus:: "+monstatus+"accountNumber:: "+accountNumber+" ");
		requestJSON.put("FROMDATE",fromDate);
		requestJSON.put("TODATE",toDate);
		requestJSON.put("ACCNUMBER",accountNumber);
		requestJSON.put("MONSTATUS",monstatus);
			}
		
		logger.debug("status:::::::::"+monstatus);
		requestJSON.put("accBean", accBean);
		
		if (invstat==null){
			requestJSON.put("status", "N");				
		}else{
			requestJSON.put("status", invstat);
		}
		
		System.out.println("rege value in action"+invstat);
		requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

		requestDTO.setRequestJSON(requestJSON);
		responseDTO = new AddNewAccountDAO().fetchkeylogs(requestDTO);
		
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

	public String validatetxn() {
		logger.debug("Inside Validate Fraud Transaction...  ");
		ArrayList<String> errors = null;
		
		try {
			session = ServletActionContext.getRequest().getSession();
			logger.debug("To-email: "+getCaseemail());
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			
			requestJSON.put("accBean", accBean);
			
			requestJSON.put("remarks",getRege());
			requestJSON.put("tomail",getCaseemail());
			
				logger.debug("appid in action"+getAppid());
				
			requestJSON.put("appid",getAppid());
			
				logger.debug("Status in action: "+getCasestatus());
			
			requestJSON.put("status",getCasestatus());
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new AddNewAccountDAO().valtxn(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccBean((AccountBean) responseDTO.getData().get(
						"AccountData"));
				keylogs();
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
	
	
	public String fetchkeys() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;
		
		try {
			session = ServletActionContext.getRequest().getSession();
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			
			requestJSON.put("accBean", accBean);
			if(rege==null){
				rege="F";
			}
			requestJSON.put("rege", rege);
			System.out.println("reger val"+rege);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			
			requestDTO.setRequestJSON(requestJSON);
			//responseDTO.addSelectData(rege);
			responseDTO = new AddNewAccountDAO().fetchkeys(requestDTO);
			
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
	
	
	public String healthmonitor() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("accBean", accBean);

			requestDTO.setRequestJSON(requestJSON);
			responseDTO = new AddNewAccountDAO().healthstatus(requestDTO);
			
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
	
	
	public String keyinsert() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;
		
		try {
			session = ServletActionContext.getRequest().getSession();
			
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			
			requestJSON.put("accBean", accBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			
			requestDTO.setRequestJSON(requestJSON);
			
			responseDTO = new AddNewAccountDAO().insertkeys(requestDTO);
			
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
	
	
	public String advertScrUpload() {
		logger.debug("Inside Account active Deactivation Details .. ");
		ArrayList<String> errors = null;
		String fname = null;

		List<String[]> data = null;
		List<String> list = null;
		JSONObject json = null;
		// AccountBean accBean = null;
		String sourcepath = null;
		CSVReader csvReader = null;
		int u=0;
		String nam=null;
		
	

		try {

			bundle = ResourceBundle.getBundle("pathinfo_config");
			sourcepath = bundle.getString("ADVERT_SCR_UPLOAD_PATH");
			
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();

			fileUpload = accBean.getUploadfile();

			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48

			System.out.println("fileUpload valeu   " + fileUpload);
			nam=fileUpload.getName();
			fname = (String) dateFormat.format(date);
			File saveFilePath = new File(sourcepath + fname + ".jpg");
			FileUtils.copyFile(fileUpload, saveFilePath);

			System.out.println("filesaved");

			logger.debug("Fileupload Path is [" + sourcepath +fname + ".jpg"+ "]");

			
			requestJSON.put("fname", fname);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			System.out.println("prepread reqdto"+nam);
			logger.debug("Response DTO [" + requestDTO + "]");

			responseDTO = new AddNewAccountDAO().insertimage(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			logger.debug("Bean Value and file name ["+ accBean.getCustomerstatus() + "] " + fname);

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				//setAccBean((AccountBean) responseDTO.getData().get("AccountData"));
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);
				accBean.setResponseJSON(responseJSON);
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
				//getAccBean().setTotcount(u-1);
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			//responseJSON.put("TOTCNT",u);
			// result="success";
			//csvReader.close();
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
	
	
	public String fetchServices() {

		logger.debug("Inside FetchService Details...  ");
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Before setting to DAO.");

			
			responseDTO =new  AddNewAccountDAO().fetchimages(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);
				accBean.setResponseJSON(responseJSON);
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
			errors = null;
		}

		return result;
	}
	
	String IMEI=null;
	
	

	
	
	public String resetDevice(){
		
		logger.debug("Inside GetData ");

		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			responseDTO =new  AddNewAccountDAO().getLockedDevices(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"DEVICES");
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
			logger.debug("Exception in  getMessage  [" + e.getMessage()	+ "]");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}
		
		return SUCCESS;
	}
	
	public String updateDeviceReset(){
		
		logger.debug("Inside update Device Status ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestJSON.put("IMEI", getIMEI());
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			responseDTO =new  AddNewAccountDAO().updateDeviceReset(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"USER_ACCESS_MNG");
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
			logger.debug("Exception in  getMessage  [" + e.getMessage()	+ "]");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}

		
		
		return SUCCESS;
	}
	
	
	public String searchCaseskeylogs() {
		logger.debug("Inside Fetch Customer Details .. ");

	
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
		
			requestJSON.put("REQUEST_TYPE",getFilter());
				
			logger.debug("monstatus:: "+monstatus+"accountNumber:: "+accountNumber+" ");
			requestJSON.put("FROMDATE",fromDate);
			requestJSON.put("TODATE",toDate);
			requestJSON.put("ACCNUMBER",accountNumber);
			requestJSON.put("MONSTATUS",monstatus);
			
			logger.debug("status:::::::::"+monstatus);
			requestJSON.put("accBean", accBean);
			
			
			System.out.println("rege value in action"+invstat);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			result = "success";


		return result;
	}
	
	public String fetchAccountServiceDetails() {
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
			responseDTO = new AddNewAccountDAO()
					.fetchAccountServiceDetails(requestDTO);
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
	
	
	public String insertCustomerDetails() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;
		ArrayList<String> succesmessage = null;
		
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
			responseDTO = new AddNewAccountDAO().insertCustomerDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			
			
		

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setAccBean((AccountBean) responseDTO.getData().get(
						"AccountData"));
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
			succesmessage = null;
		}

		return result;
	}
	
	
	public String accountOpenServiceDetails() {
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
			responseDTO = new AddNewAccountDAO()
					.accountOpenServiceDetails(requestDTO);
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
	
	
	public String accountOpenServiceAck() {
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
			responseDTO = new AddNewAccountDAO()
					.accountOpenServiceAck(requestDTO);
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
	
	
	public String getCustDetailsModify() {
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
			System.out.println("ranjit ::: "+accBean.getApptype());
			if((accBean.getApptype()).equalsIgnoreCase("MOBILE")){
				responseDTO = new AddNewAccountDAO()
						.getCustDetailsModify(requestDTO);
				
				}
			
			  if((accBean.getApptype()).equalsIgnoreCase("WALLET")){ responseDTO = new
			  AddNewAccountDAO().getCustDetailsModify(requestDTO);
			  
			  }
			 
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

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
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
