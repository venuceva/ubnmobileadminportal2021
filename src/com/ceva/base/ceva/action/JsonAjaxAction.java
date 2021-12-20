package com.ceva.base.ceva.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.eclipse.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;

import au.com.bytecode.opencsv.CSVReader;

import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.bean.BillerBean;
import com.ceva.base.common.dao.AjaxDAO;
import com.ceva.base.common.dao.ServiceMgmtAjaxDAO;
import com.ceva.base.common.dao.SwitchUIDAO;
import com.ceva.base.common.dao.UserAjaxDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.DBUtils;
import com.ceva.util.NotificationJson;
import com.ceva.util.QueryactiveDirectory;
import com.opensymphony.xwork2.ActionSupport;
import com.ceva.util.CheckDigitGeneration;

public class JsonAjaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(JsonAjaxAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	JSONObject limitsJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private String hoffice = null;
	private String region = null;
	private String location = null;
	private String method = null;
	private String selectedSelBox = null;
	private String fillSelectBox = null;

	private String entity = null;
	private String groupId = null;
	private String userId = null;
	private String employeeNo = null;
	private String dlNo = null;
	
	private String mobiles = null;
	private String wallet = null;
	

	private String mvisaid = null;

	private Map<String, String> details;
	private int finalCount = 0;
	private int finalCount1 = 0;
	private int finalCount2 = 0;
	private int finalCount3 = 0;
	private int finalCount4 = 0;
	private int finalCount5 = 0;
	private int finalCount6 = 0;
	private int finalCount7 = 0;

	private String dlName;
	private String dlIdnumber;
	private String status;
	private String serialNo;
	
	private String accNumber = null;
	private String custcode = null;
	private String institute = null;

	

	// String agencyName;
	private String accounttype;
	private String service;

	private String mobile;
	private String amount;

	private String bin;
	private String bankIndex;
	private String bankCode;
	private String message;

	private String hudumaService;
	private String processingCode;

	private String txnType;
	private String merchantId;
	private String tempstatus;
	private String bankAccount;
	private String terminalSearch;
	private String feeCodeSearch;
	private String serialNumber;
	private String terminalID;
	private String selectUsers;
	private String rrnSearch;
	

	private String accname;
	private String brcode;
	private String prdid;
	

	private String refno;
	private String makerid;
	private String multidata;
	private String email;
	private String telco;
	private String isocode;
	private String telephone;
	private String auth_code;
	private String language;
	private File bulkupload;
	private File fileUpload;
	
	private String appID;
	
	
	private String menucode;
	
	 private String fname="";
	 private String lname="";
	 private String dob="";
	 private String mnumber="";
	 private String link="";
	 
	 private String transaction="";
	 private String frequency="";
	 private String limitcode="";
	 private String apptype="";
	 
	 private String frmdate="";
	 private String todate="";
	 
	 
	 
	 
	public String getFrmdate() {
		return frmdate;
	}


	public void setFrmdate(String frmdate) {
		this.frmdate = frmdate;
	}


	public String getTodate() {
		return todate;
	}


	public void setTodate(String todate) {
		this.todate = todate;
	}


	public int getFinalCount1() {
		return finalCount1;
	}


	public void setFinalCount1(int finalCount1) {
		this.finalCount1 = finalCount1;
	}


	public int getFinalCount2() {
		return finalCount2;
	}


	public void setFinalCount2(int finalCount2) {
		this.finalCount2 = finalCount2;
	}


	public int getFinalCount3() {
		return finalCount3;
	}


	public void setFinalCount3(int finalCount3) {
		this.finalCount3 = finalCount3;
	}


	public int getFinalCount4() {
		return finalCount4;
	}


	public void setFinalCount4(int finalCount4) {
		this.finalCount4 = finalCount4;
	}


	public int getFinalCount5() {
		return finalCount5;
	}


	public void setFinalCount5(int finalCount5) {
		this.finalCount5 = finalCount5;
	}


	public int getFinalCount6() {
		return finalCount6;
	}


	public void setFinalCount6(int finalCount6) {
		this.finalCount6 = finalCount6;
	}


	public int getFinalCount7() {
		return finalCount7;
	}


	public void setFinalCount7(int finalCount7) {
		this.finalCount7 = finalCount7;
	}


	public String getApptype() {
		return apptype;
	}


	public void setApptype(String apptype) {
		this.apptype = apptype;
	}


	public String getBillerId() {
		return billerId;
	}


	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}


	public String getCustomerAccount() {
		return customerAccount;
	}


	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}


	public String getBillerType() {
		return billerType;
	}


	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}


	public String getBillerCode() {
		return billerCode;
	}


	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}


	public BillerBean getBillerBean() {
		return billerBean;
	}


	public void setBillerBean(BillerBean billerBean) {
		this.billerBean = billerBean;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	private String billerId;
	private String customerAccount;
	private String billerType;
	private String billerCode;

	private BillerBean billerBean;
	private String accountNumber;
	
	
	public String getTransaction() {
		return transaction;
	}


	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getLimitcode() {
		return limitcode;
	}


	public void setLimitcode(String limitcode) {
		this.limitcode = limitcode;
	}


	public String getMobiles() {
		return mobiles;
	}


	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}


	public String getWallet() {
		return wallet;
	}


	public void setWallet(String wallet) {
		this.wallet = wallet;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getMnumber() {
		return mnumber;
	}


	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}

	private HttpSession session = null;
	private HttpServletRequest request;
	private ResourceBundle rb = null;

	@Override
	public String execute() throws Exception {
		logger.debug("Inside Execute Method.");
		logger.debug("Execute method [" + method + "] ");
		String result = ERROR;

		try {
			if (method.equalsIgnoreCase("searchData")) {
				result = searchData();
			} else if (method.equalsIgnoreCase("searchEntity")) {
				result = checkCountGroup();
			} else if (method.equalsIgnoreCase("searchUser")) {
				result = searchUser();
			} else if (method.equalsIgnoreCase("searchKraData")) {
				result = searchKraData();
			} else if (method.equalsIgnoreCase("generatevisaid")) {
				result = generateVisaId();
			} else if (method.equalsIgnoreCase("searchSerial")) {
				result = searchSerial();
			} else if (method.equalsIgnoreCase("searchSerialData")) {
				result = searchSerialData();
			} else if (method.equalsIgnoreCase("checkTransactionType")) {
				result = checkTransactionType();
			} else if (method.equalsIgnoreCase("userIdStatus")) {
				result = userIdStatus();
			} else if (method.equalsIgnoreCase("searchHudumaMobile")) {
				result = searchHudumaMobile();
			} else if (method.equalsIgnoreCase("searchBin")) {
				result = searchBin();
			} else if (method.equalsIgnoreCase("checkHudumaSubService")) {
				result = checkHudumaSubService();
			} else if (method.equalsIgnoreCase("SwitchStatus")) {
				result = switchStatus();
			} else if (method.equalsIgnoreCase("userDetails")) {
				result = userDetails();
			} else if (method.equalsIgnoreCase("searchProcessingCode")) {
				result = searchProcessingCode();
			} else if (method.equalsIgnoreCase("searchTxnType")) {
				result = searchTxnType();
			} else if (method.equalsIgnoreCase("terminalDetails")) {
				result = terminalDetailsSearch();
			} else if (method.equalsIgnoreCase("serialNumberMethod")) {
				result = CheckSearialNumber();
			} else if (method.equalsIgnoreCase("assignUserMethod")) {
				result = CheckAssignUsers();
			} else if (method.equalsIgnoreCase("RRNDetailsSearch")) {
				result = searchRRnDetails();
			}else if (method.equalsIgnoreCase("addnewaccounts")) {
				result = addnewaccounts();
			}else if (method.equalsIgnoreCase("updateAccAuthData")) {
				result = updateAccAuthData();
			}else if (method.equalsIgnoreCase("fetchAccData")) {
				result = fetchAccData();
			}else if (method.equalsIgnoreCase("fetchNotification")) {
				result = fetchNotification();
			}else if (method.equalsIgnoreCase("fetchAccDatafromCore")) {
				result = fetchAccDatafromCore();
			}else if (method.equalsIgnoreCase("fetchAccDatafromCorenew")) {
				result = fetchAccDatafromCorenew();
			}else if (method.equalsIgnoreCase("addCustomerValidation")) {
				result = addCustomerValidation();
			}else if (method.equalsIgnoreCase("fetchAddNewAccDatafromCore")) {
				result = fetchAddNewAccDatafromCore();
			}else if (method.equalsIgnoreCase("fetchWalletAccData")) {
				result = fetchWalletAccData();
			}else if (method.equalsIgnoreCase("verifyMobile")) {
			result = verifyMobile();
			}else if (method.equalsIgnoreCase("verifyuserid")) {
				result = verifyuserid();
			}else if (method.equalsIgnoreCase("fetchlimiservices")) {
				result = fetchlimiservices();
			}else if (method.equalsIgnoreCase("fetchproducts")) {
				result = fetchproducts();
			}else if (method.equalsIgnoreCase("fetchPrdDetails")) {
				result = fetchPrdDetails();
			}else if (method.equalsIgnoreCase("fetchMenuDetails")) {
				result = fetchMenuDetails();
			}else if (method.equalsIgnoreCase("verifyMenucode")) {
				result = verifyMenucode();
			}else if (method.equalsIgnoreCase("fetchPrepCardData")) {
				result = fetchPrepCardData();
			}else if (method.equalsIgnoreCase("validatebulkdata")) {
				result = validatebulkdata();
			}else if (method.equalsIgnoreCase("fetchSpackDetails")) {
				result = fetchSpackDetails();
			}else if (method.equalsIgnoreCase("txndetails")) {
				result = txndetails();
		} else if (method.equalsIgnoreCase("searchAgent")) {
			result = searchAgent();
		} else if (method.equalsIgnoreCase("validatemobileno")) {
			result = validatemobileno();
		} else if (method.equalsIgnoreCase("validateuserid")) {
			result = validateuserid();
		} else if (method.equalsIgnoreCase("searchProduct")) {
			result = searchProduct();
		}else if (method.equalsIgnoreCase("searchAuthPendinglf")) {
			result = searchAuthPendinglf();
		}else if (method.equalsIgnoreCase("searchLimit")) {
			result = searchLimit();
		}else if (method.equalsIgnoreCase("searchAuth")) {
			result = searchAuth();
		}else if (method.equalsIgnoreCase("superagentsearch")) {
			result = superagentsearch();
		}else if (method.equalsIgnoreCase("searchValidation")) {
			result = searchValidation();
		}else if (method.equalsIgnoreCase("ValidationDevice")) {
			result = ValidationDevice();
		}else if (method.equalsIgnoreCase("searchFee")) {
			result = searchFee();
		} else if (method.equalsIgnoreCase("validatelimit")) {
			result = validatelimit();
		} else if (method.equalsIgnoreCase("validatecluster")) {
			result = validatecluster();
		} else if (method.equalsIgnoreCase("validateadmintype")) {
			result = validateadmintype();
		}else if(method.equalsIgnoreCase("searchBillerAccount")){
			result = searchBillerAccount();
		}else if(method.equalsIgnoreCase("getBillerAccountDetails")){
			result = fetchBillerAccountDetails();
		} else if (method.equalsIgnoreCase("validateaccountdetails")) {
			result = validateaccountdetails();
		}else if (method.equalsIgnoreCase("rmvalidation")) {
			result = rmvalidation();
		}else if (method.equalsIgnoreCase("dashboardpie")) {
			result = dashboardpie();
		} else if (method.equalsIgnoreCase("validatemerchant")) {
			result = validatemerchant();
		}else if (method.equalsIgnoreCase("authactionview")) {
			result = authactionview();
		}else if (method.equalsIgnoreCase("validatecnt")) {
			result = validatecnt();
		}else if (method.equalsIgnoreCase("primaryaccountdetails")) {
			result = primaryaccountdetails();
		}else if (method.equalsIgnoreCase("verifyMobileNo")) {
			result = verifyMobileNo();
		}else if (method.equalsIgnoreCase("searchCampaign")) {
			result = searchCampaign();
		}else if (method.equalsIgnoreCase("verifyWalletAgentMobile")) {
			result = verifyWalletAgentMobile();
		}else if (method.equalsIgnoreCase("searchTerminalSerial")) {
				result = searchTerminalSerial();
		} else if (method.equalsIgnoreCase("validationstatement")) {
			result = validationstatement();
		} else if (method.equalsIgnoreCase("validaterefsucrev")) {
			result = validaterefsucrev();
		} else if (method.equalsIgnoreCase("fileuploadrec")) {
			result = fileuploadrec();
		}else if (method.equalsIgnoreCase("validationpos")) {
			result = validationpos();
		}  	
										
		} catch (Exception e) {
			logger.debug("Inside execute [" + e.getMessage() + "]");
		} finally {

		}

		return result;
	}

	
	public String searchAgent() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			mnumber=(mnumber.equalsIgnoreCase("")) ? null : "'"+mnumber+"'";
			link=(link.equalsIgnoreCase("")) ? null :  link;
			//System.out.println("kailash here ::: "+link);
			
			connection = DBConnector.getConnection();
			
			if(link.equalsIgnoreCase("LOCK")){
				queryConst = "SELECT COUNT(*) FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
						+ "("+fname+" is NULL or upper(UI.USER_NAME)="+fname+") "
						+ "AND ("+mnumber+" is NULL or ULC.LOGIN_USER_ID="+mnumber+") ";
			}else if(link.equalsIgnoreCase("PIN")){
				queryConst = "SELECT COUNT(*) FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
						+ "("+fname+" is NULL or upper(UI.USER_NAME)="+fname+") "
						+ "AND ("+mnumber+" is NULL or ULC.LOGIN_USER_ID="+mnumber+") AND UI.USER_STATUS in ('A','F')";
			}else if(link.equalsIgnoreCase("MODIFY")){
				queryConst = "SELECT COUNT(*) FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
						+ "("+fname+" is NULL or upper(UI.USER_NAME)="+fname+") "
						+ "AND ("+mnumber+" is NULL or ULC.LOGIN_USER_ID="+mnumber+") AND UI.USER_STATUS in ('A','F')";
			}else if(link.equalsIgnoreCase("UPDATE")){
				queryConst = "SELECT COUNT(*) FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE ULC.COMMON_ID=UI.COMMON_ID AND "
						+ "("+fname+" is NULL or upper(UI.USER_NAME)="+fname+") "
						+ "AND ("+mnumber+" is NULL or ULC.LOGIN_USER_ID="+mnumber+") AND UI.USER_STATUS in ('A','F')";
			}else if(link.equalsIgnoreCase("LIMITAP")){
				queryConst = "SELECT COUNT(*) FROM DSA_LIMIT WHERE LIMIT_CODE="+fname+"";
			}else if(link.equalsIgnoreCase("LIMIT")){
				queryConst = "SELECT COUNT(*) FROM DSA_LIMIT_TEMP WHERE LIMIT_CODE="+fname+"";
			}
			else if(link.equalsIgnoreCase("AUTHPEND")){
				queryConst = "SELECT COUNT(*) FROM USSD_REQUEST_TEMP WHERE UPPER(USERID)="+fname+"";
			}else if(link.equalsIgnoreCase("AUTHPENDLMT")){
				queryConst = "SELECT COUNT(*) FROM DSA_LIMIT_TEMP WHERE UPPER(LIMIT_CODE)=UPPER("+fname+")";
			}
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String searchProduct() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			queryConst = "SELECT COUNT(*) FROM PRODUCT WHERE PRD_CODE="+fname+"";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
					message ="Product Code Already Exit";
				}
				
			}
			
			entityPstmt.close();
			entityRS.close();
			
			queryConst = "SELECT COUNT(*) FROM PRODUCT_TEMP WHERE PRD_CODE="+fname+"";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="Product Code Authorized Pending";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validationpos() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";
			connection = DBConnector.getConnection();
			
			queryConst = "select count(*) from STORE_MASTER_TEMP ACMT,auth_pending AP where  AP.STATUS='P' and TELEPHONE_NO=? and AUTH_CODE='STOREAUTH' and ACMT.REF_NUM=AP.REF_NUM";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityPstmt.setString(1, fname);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
					message =entityRS.getString(1);
				
			}
			
			entityPstmt.close();
			entityRS.close();
			
			

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchTerminalSerial() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";
			connection = DBConnector.getConnection();
			
			queryConst = "SELECT COUNT(*) FROM TERMINAL_MASTER WHERE SERIAL_NO='"+fname+"'";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
					message ="Serial Number Already Exit";
				}
				
			}
			
			entityPstmt.close();
			entityRS.close();
			
			queryConst = "select count(*) from TERMINAL_MASTER_TEMP ACMT,auth_pending AP where  AP.STATUS='P' and SERIAL_NO='"+fname+"' and AUTH_CODE='POSAUTH' and ACMT.REF_NUM=AP.REF_NUM";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="Serial Number "+fname+" pending for authorization";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchLimit() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "SELECT COUNT(*) FROM LIMIT_FEE_MASTER_TEMP WHERE PRODUCT="+fname+" AND USAGE_TYPE='L'";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="Product Code Authorized Pending";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	
	public String searchAuthPendinglf() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "SELECT COUNT(*) FROM LIMIT_FEE_DETAILS_TEMP LFDT,LIMIT_FEE_MASTER LFM WHERE LMT_FEE_CODE="+fname+" and lfdt.ref_num=lfm.ref_num";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="Pending for Authorized ";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchCampaign() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			//fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			queryConst = "SELECT COUNT(*) FROM CAMP_TEMPLATE_MASTER WHERE UPPER(TEMPLATE_ID)=UPPER(?)";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityPstmt.setString(1, fname);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
					message ="Template Name Already Exit";
				}
				
			}
			
			entityPstmt.close();
			entityRS.close();
			
			

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchAuth() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			mnumber=(mnumber.equalsIgnoreCase("")) ? null : "'"+mnumber+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "select count(*) from MOB_CUSTOMER_MASTER_TEMP MCM,AUTH_PENDING AP where AP.REF_NUM=MCM.REF_NUM AND AP.STATUS='P' AND MCM.CUSTOMER_CODE="+fname+" AND AUTH_CODE="+mnumber+"";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="This Customer Authorized Pending";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String superagentsearch() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			mnumber=(mnumber.equalsIgnoreCase("")) ? null : "'"+mnumber+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "select count(*) from SUPER_AGENT  where TELCO_TYPE="+mnumber+"";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="This Super Agent Already Exit";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchValidation() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "select count(*) from MOB_CUSTOMER_MASTER where CUSTOMER_CODE="+fname+" AND USER_ID is not null";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)==0){
				message ="This Customer Using Only USSD So this functionality not applicable ";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String ValidationDevice() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "select count(*) from MOB_IMEI_DATA where UPPER(user_id) in (select UPPER(user_id) from MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE="+fname+" ) AND STATUS='A'";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)==0){
				message ="This Customer did not have Active Device ";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	public String searchFee() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		message="SUCCESS";
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			connection = DBConnector.getConnection();
			
			
			
			queryConst = "SELECT COUNT(*) FROM LIMIT_FEE_MASTER_TEMP WHERE PRODUCT="+fname+" AND USAGE_TYPE='F'";
			
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(entityRS.getInt(1)!=0){
				message ="Product Code Authorized Pending";
				}
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			message = "Creation Error";
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validatelimit() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			transaction=(transaction.equalsIgnoreCase("")) ? null :  "'"+transaction.split("-")[0]+"'";
			frequency=(frequency.equalsIgnoreCase("")) ? null :  "'"+frequency+"'";
			limitcode=(limitcode.equalsIgnoreCase("")) ? null :  "'"+limitcode+"'";
			
			
			
			connection = DBConnector.getConnection();
			queryConst = "SELECT COUNT(*) FROM LIMIT_FEE_MASTER LFM,LIMIT_FEE_DETAILS LFD  WHERE LFM.REF_NUM=LFD.REF_NUM AND LFM.LMT_FEE_CODE="+limitcode+" AND LFD.TXN_CODE="+transaction+" AND LFD.FREQUENCY="+frequency+"";
			System.out.println(queryConst);		
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validatecluster() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			transaction=(transaction.equalsIgnoreCase("")) ? null :  "'"+transaction+"'";
		
			
			
			
			connection = DBConnector.getConnection();
			queryConst = "SELECT COUNT(*) FROM CLUSTER_TBL  WHERE CLUSTER_ID="+transaction+"";
			System.out.println(queryConst);		
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validatemerchant() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			transaction=(transaction.equalsIgnoreCase("")) ? null :  "'"+transaction+"'";
		
			
			
			
			connection = DBConnector.getConnection();
			queryConst = "SELECT COUNT(*) FROM ORG_MASTER  WHERE ORGANIGATION_ID="+transaction+"";
			System.out.println(queryConst);		
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validatecnt() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			transaction=(transaction.equalsIgnoreCase("")) ? null :  ""+transaction+"";
		
		
			
			
			connection = DBConnector.getConnection();
			
			queryConst = "SELECT TO_CHAR(SYSDATE,'ddmmyyyy') FROM DUAL";
			System.out.println(queryConst);
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				if(!(entityRS.getString(1)).equalsIgnoreCase(transaction.substring(0,8))){
					finalCount =99;
				}
			}
			entityPstmt.close();
			entityRS.close();
			if(finalCount!=99){
				queryConst = "SELECT COUNT(*) FROM FILE_UPLOAD_MASTER  WHERE UPPER(F_NAME)=UPPER('"+transaction+"')";
				
				entityPstmt = connection.prepareStatement(queryConst);
				entityRS = entityPstmt.executeQuery();

				if (entityRS.next()) {
					finalCount = entityRS.getInt(1);
				}

			}
			
			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String validateadmintype() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			transaction=(transaction.equalsIgnoreCase("")) ? null :  "'"+transaction+"'";
			frequency=(frequency.equalsIgnoreCase("")) ? null :  "'"+frequency+"'";
			limitcode=(limitcode.equalsIgnoreCase("")) ? null :  ""+limitcode+"";
			
			connection = DBConnector.getConnection();
			//System.out.println(transaction+"--"+frequency+"---"+limitcode);
			if(limitcode.equalsIgnoreCase("admintype")){
			queryConst = "SELECT COUNT(*) FROM CONFIG_DATA  WHERE KEY_GROUP='USER_DESIGNATION' AND STATUS="+frequency;
			}
			if(limitcode.equalsIgnoreCase("adminlevel")){
				queryConst = "SELECT COUNT(*) FROM CONFIG_DATA  WHERE KEY_GROUP='USER_DESIGNATION' AND STATUS="+frequency+" AND KEY_TYPE="+transaction+"";
			}
			System.out.println(queryConst);		
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

				if (entityRS.next()) {
					finalCount = entityRS.getInt(1);
				}
			
			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	

	public String validatemobileno() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			mnumber=(mnumber.equalsIgnoreCase("")) ? null : "'"+mnumber+"'";
			
			
			
			connection = DBConnector.getConnection();
			queryConst = "SELECT COUNT(*) FROM USER_LOGIN_CREDENTIALS  WHERE LOGIN_USER_ID="+mnumber+" ";
					
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}

	public String validateuserid() {
		logger.debug("Inside searchAgent.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			

			fname=(fname.equalsIgnoreCase("")) ? null :  "'"+fname+"'";
			
			
			
			connection = DBConnector.getConnection();
			queryConst = "SELECT COUNT(*) FROM USSD_REQUEST  WHERE UPPER(USERID)=upper("+fname+") ";
					
			
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	
	
	public String searchBillerAccount() {
		logger.debug("Inside searchBillerAccount... ");
		String queryConst = "";
		logger.debug("Biller Id        [" + getBillerId() + "]");
		logger.debug("Account Number [" + getAccountNumber() + "]");

		CallableStatement callable = null;
		Connection connection = null;
		try {
			/*connection = DBConnector.getConnection();

			queryConst = "{call HudumaPkg.CHECKBIN(?,?,?,?,?)}";

			logger.debug(" queryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getBin());
			callable.setString(2, getBankIndex());
			callable.setString(3, getBankCode());
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.registerOutParameter(5, Types.VARCHAR);
			callable.execute();

			logger.debug("message [" + callable.getString(4) + "] Status ["
					+ callable.getString(5) + "] ");*/

			/*setStatus(callable.getString(5));
			setMessage(callable.getString(4));*/
			
			setStatus("NOTFOUND");
			setMessage("SUCCESS");

		} catch (Exception e) {
			setStatus("NO");
			logger.debug("Exception is [" + e.getMessage() + "]");
		} finally {

			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String fetchBillerAccountDetails() {
		
		logger.debug("Inside fetchBillerAccountDetails... ");
		String queryConst = "";
		logger.debug("Biller Code        [" + getBillerCode() + "]");

		responseJSON = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try {
			connection = DBConnector.getConnection();

			queryConst = "select ACCOUNT_NUMBER,ACCOUNT_NAME from BILLER_ACCOUNT_MASTER where BILLER_ID=? ";
			preparedStatement = connection.prepareStatement(queryConst.toUpperCase());
			preparedStatement.setString(1, getBillerCode());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				jsonObject = new JSONObject();
				jsonObject.put("val", resultSet.getString(1));
				jsonObject.put("key", resultSet.getString(2));
				jsonArray.add(jsonObject);
			}
			
			responseJSON.put("BILLER_ACCT_DATA", jsonArray);
			
			resultSet.close();
			preparedStatement.close();
			
			
			queryConst = "select NAME,COMM_TYPE,AMOUNT/100 from BILLER_REGISTRATION where BILLER_ID=? ";
			preparedStatement = connection.prepareStatement(queryConst.toUpperCase());
			preparedStatement.setString(1, getBillerCode());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				responseJSON.put("BILLER_ABBR", resultSet.getString(1));
				responseJSON.put("COMM_TYPE", resultSet.getString(2));
		        responseJSON.put("COMM_AMT", resultSet.getString(3));
			}
			
			logger.debug("Response JSON::"+responseJSON);
			
			resultSet.close();
			preparedStatement.close();
			
			
			setStatus("NOTFOUND");
			setMessage("SUCCESS");

		} catch (Exception e) {
			setStatus("NO");
			logger.debug("Exception is [" + e.getMessage() + "]");
		} finally {

			DBUtils.closePreparedStatement(preparedStatement);
			DBUtils.closeResultSet(resultSet);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	
	public String checkCountGroup() {
		logger.debug("Inside CheckCountGroup.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			connection = DBConnector.getConnection();
			queryConst = "select count(*) from user_groups where upper(group_id)=?";
			entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
			entityPstmt.setString(1, groupId);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}

	public String searchKraData() {
		logger.debug("Inside SearchKraData.. ");
		logger.debug("	 Dlno [" + getDlNo() + "]");
		CallableStatement callableStatement = null;
		String queryConst = "{call getDlDetails(?,?,?,?,?)}";
		String message = "";
		Connection connection = null;

		try {
			connection = DBConnector.getConnection();
			logger.debug("	 queryConst [" + queryConst + "]");
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, getDlNo());
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			callableStatement.registerOutParameter(4, Types.VARCHAR);
			callableStatement.registerOutParameter(5, Types.VARCHAR);

			callableStatement.execute();

			logger.debug(" 	 After Executing callableStatement.");

			message = callableStatement.getString(4);
			setStatus(callableStatement.getString(5));

			logger.debug(" 	 After Executing message[" + message + "] status["
					+ callableStatement.getString(5) + "]");

			if (message.equalsIgnoreCase("SUCCESS")) {
				setDlName(callableStatement.getString(2));
				setDlIdnumber(callableStatement.getString(3));
			} else {
				setDlName("NO");
				setDlIdnumber("NO");
			}

			logger.debug(" 	After Executing query.");

		} catch (Exception e) {

			dlName = "NO";
			dlIdnumber = "NO";
			status = "";
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			queryConst = null;
			message = null;
		}

		logger.debug("   Details are : " + details);

		return SUCCESS;
	}
	
	

	public String searchSerial() {
		logger.debug("Inside SearchSerial... ");
		logger.debug("	 dlno [" + getDlNo() + "]");
		CallableStatement callableStatement = null;
		String queryConst = "{call getSerialDetails(?,?,?)}";
		String message = "";
		Connection connection = null;

		try {
			connection = DBConnector.getConnection();

			logger.debug("	 queryConst [" + queryConst + "]");
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, getSerialNo());
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			callableStatement.execute();

			logger.debug(" 	 After Executing callableStatement.");

			message = callableStatement.getString(2);
			setStatus(callableStatement.getString(3));

			logger.debug(" 	 After Executing message[" + message + "] "
					+ "status[" + callableStatement.getString(3) + "]");

			logger.debug(" 	After Executing query.");

		} catch (Exception e) {

			setStatus("NO");
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			queryConst = null;
			message = null;
		}

		logger.debug("   Details are : " + getStatus());

		return SUCCESS;
	}

	public String searchSerialData() {
		logger.debug("Inside 	SearchSerialData... ");
		logger.debug("	 dlno [" + getDlNo() + "]");
		CallableStatement callableStatement = null;
		String queryConst = "{call HudumaPkg.CHECKSERIALDATA(?,?,?)}";
		String message = "";
		Connection connection = null;

		try {
			connection = DBConnector.getConnection();

			logger.debug("QueryConst [" + queryConst + "]");
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, getSerialNo());
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			callableStatement.execute();

			logger.debug(" 	 After Executing callableStatement.");

			message = callableStatement.getString(2);
			setStatus(callableStatement.getString(3));

			logger.debug(" 	 After Executing message[" + message + "] "
					+ "status[" + callableStatement.getString(3) + "]");

			logger.debug("After Executing query.");

		} catch (Exception e) {
			setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			queryConst = null;
			message = null;
		}

		logger.debug(" Details are : " + getStatus());

		return SUCCESS;
	}
	
	public String searchBfubAccounts() {
		logger.debug("Inside Search Bfub Accounts.. ");
		CallableStatement callableStatement = null;
		String queryConst = "";
		Connection connection = null;
		AccountBean accBean = null;
		try {
			accBean = new AccountBean();
			
				queryConst = "{call MPESAPAYBILLPKG.BFUBTYPEACCTCHECK(?)}";

			connection = DBConnector.getConnection();
			logger.debug("Query Const [" + queryConst + "]");
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, accBean.getAccountid());
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			callableStatement.registerOutParameter(4, Types.VARCHAR);
			callableStatement.registerOutParameter(5, Types.VARCHAR);
			callableStatement.registerOutParameter(6, Types.VARCHAR);
			

			callableStatement.execute();

			logger.debug("After Executing callableStatement.");

			logger.debug("After Executing Message ["
					+ callableStatement.getString(8) + "] Status ["
					+ callableStatement.getString(7) + "] Master Status["
					+ callableStatement.getString(9) + "]");

			accBean.setAccountid(callableStatement.getString(2));
			accBean.setAccountname(callableStatement.getString(3));
			accBean.setBranchcode(callableStatement.getString(4));
			accBean.setProductid(callableStatement.getString(5));
			accBean.setAccountname(callableStatement.getString(6));

			logger.debug("After Executing query.");

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String searchData() {
		logger.debug("Inside SearchData.. ");
		String queryConst = "";
		logger.debug("Region [" + getRegion() + "]");
		logger.debug("Head Office [" + getHoffice() + "]");
		logger.debug("Location [" + getLocation() + "]");
		logger.debug("Selected Select Box [" + getSelectedSelBox() + "]");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		Connection connection = null;
		try {

			details = new HashMap<String, String>();
			connection = DBConnector.getConnection();

			if (getSelectedSelBox().equalsIgnoreCase("region")) {
				queryConst = "Select distinct HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where HPO_FLAG='HEAD' and REGION_CODE in ("
						+ getRegion() + ") ORDER BY HPO_DEPARTMENT_CODE";
				// queryConst =
				// "Select distinct HPO_DEPARTMENT_CODE,HPO_NAME from IMP_BRANCH_MASTER where HPO_FLAG='HEAD' and REGION_CODE in ("+getRegion()+") ORDER BY HPO_DEPARTMENT_CODE";
				fillSelectBox = "headOffice";
			} else if (getSelectedSelBox().equalsIgnoreCase("headOffice")) {
				queryConst = "Select distinct OFFICE_CODE,OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null  and HPO_DEPARTMENT_CODE in ("
						+ getHoffice() + ") ORDER BY OFFICE_CODE";
				// queryConst =
				// "Select distinct OFFICE_CODE,OFFICE_NAME from IMP_BRANCH_MASTER where HPO_FLAG is null  and HPO_DEPARTMENT_CODE in ("+getHoffice()+") ORDER BY OFFICE_CODE";
				fillSelectBox = "Location";
			} else if (getSelectedSelBox().equalsIgnoreCase("Location")) {
				// queryConst =
				// "select distinct system_user_id,login_user_id from user_id_mapping where system_user_id in (select user_id from user_master where upper(branch_location) in ("
				// + getLocation().toUpperCase().trim() + "))";
				queryConst = "select LOGIN_USER_ID from USER_LOGIN_CREDENTIALS where COMMON_ID in "
						+ "(select COMMON_ID from USER_INFORMATION where upper(location) in ("
						+ getLocation() + ") and upper(USER_LEVEL)='USER')";
				fillSelectBox = "userid";
			}

			logger.debug("QueryConst [" + queryConst + "]");
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {

				if (fillSelectBox.equalsIgnoreCase("Location")) {

					details.put(entityRS.getString(1), entityRS.getString(1)
							+ "-" + entityRS.getString(2));
				} else if (fillSelectBox.equalsIgnoreCase("userid")) {

					details.put(entityRS.getString(1), entityRS.getString(1));
				} else {
					details.put(entityRS.getString(1), entityRS.getString(1)
							+ "-" + entityRS.getString(2));
				}
			}

		} catch (Exception e) {
			setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
			queryConst = null;
		}

		logger.debug("Details are : " + details);

		return SUCCESS;
	}

	public String searchHudumaMobile() {
		logger.debug("Inside 	[searchHudumaMobile]");
		String queryConst = "";
		logger.debug("	[searchHudumaMobile] mobile [" + getMobile() + "]");
		logger.debug("	[searchHudumaMobile] amount [" + getAmount() + "]");

		CallableStatement callable = null;
		Connection connection = null;
		try {
			details = new HashMap<String, String>();
			connection = DBConnector.getConnection();

			queryConst = "{call HudumaPkg.CHECKMOBILESERVICES(?,?,?,?)}";

			logger.debug("  	[searchHudumaMobile] queryConst [" + queryConst
					+ "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getMobile());
			callable.setString(2, getAmount());
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.execute();

			logger.debug("  [searchHudumaMobile]  message["
					+ callable.getString(3) + "] Status ["
					+ callable.getString(4) + "] ");

			setMessage(callable.getString(3));
			setStatus(callable.getString(4));

		} catch (Exception e) {
			setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}
		return SUCCESS;
	}

	public String searchBin() {
		logger.debug("Inside searchBin... ");
		String queryConst = "";
		logger.debug("BIN        [" + getBin() + "]");
		logger.debug("Bank Index [" + getBankIndex() + "]");
		logger.debug("Bank Code  [" + getBankCode() + "]");

		CallableStatement callable = null;
		Connection connection = null;
		try {
			connection = DBConnector.getConnection();

			queryConst = "{call HudumaPkg.CHECKBIN(?,?,?,?,?,?)}";

			logger.debug(" queryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getBin());
			callable.setString(2, getBankIndex());
			callable.setString(3, getBankCode());
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.registerOutParameter(5, Types.VARCHAR);
			callable.registerOutParameter(6, Types.VARCHAR);
			callable.execute();

			logger.debug("message [" + callable.getString(4) + "] Status ["
					+ callable.getString(5) + "] temp_status ["
					+ callable.getString(6) + "]");

			setStatus(callable.getString(5));
			setMessage(callable.getString(4));
			setTempstatus(callable.getString(6));

		} catch (Exception e) {
			setStatus("NO");
			logger.debug("Exception is [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String searchUser() {
		logger.debug("Inside SearchUser.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		try {

			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("User_Id", userId);
			logger.debug("testing perpose>>>>>>>>>>>>>>>>>>>>>>>>" + userId);
			logger.debug("testing perpose>>>>>>>>>>>>>>#####>>>>>>>>>>"
					+ getUserId().toUpperCase());
			connection = DBConnector.getConnection();

			queryConst = "{call HudumaPkg.CHECKUSER(?,?,?,?)}";

			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getUserId().toUpperCase());
			callable.setString(2, getEmployeeNo());
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.execute();

			logger.debug("  message [" + callable.getString(3) + "]"
					+ " Status [" + callable.getString(4) + "] ");

			setMessage(callable.getString(3));
			setStatus(callable.getString(4));

		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}
	
	
	public String addnewaccounts() {
		logger.debug("Inside Account Creation.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		String ip = null;
		
		try {

			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("Acc_No", accNumber);
			//System.out.println("in json action"+accNumber);
			logger.debug("Account Number " + accNumber+ "] [  custid +"+ custcode+" institute [ "+institute);
			//logger.debug("testing perpose>>>>>>>>>>>>>>#####>>>>>>>>>>"+ getAccNumber().toUpperCase());
			ip=ServletActionContext.getRequest().getRemoteAddr();
			connection = DBConnector.getConnection();

			queryConst = "{call ACCOUNTSPKG.ACCOUNTUSERCHECK(?,?,?,?,?,?,?,?,?,?,?)}";

			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1,accNumber);
			callable.setString(2,custcode);
			callable.setString(3,institute);
			callable.registerOutParameter(4, Types.VARCHAR);		//PRODUCT
			callable.registerOutParameter(5, Types.VARCHAR);		//FULLNAME
			callable.registerOutParameter(6, Types.VARCHAR);		//BRANCHCODE
			callable.registerOutParameter(7, Types.VARCHAR);
			callable.registerOutParameter(8, Types.VARCHAR);
			callable.setString(9,makerid);
			callable.setString(10,ip);
			callable.registerOutParameter(11, Types.VARCHAR);		//SUBPRODUCTID
			callable.execute();

			logger.debug("  message [" + callable.getString(4) + "]"+ " Status [" + callable.getString(5) + "] + ["+  callable.getString(6));

			setPrdid(callable.getString(4));						//PRODUCT
			setAccname(callable.getString(5));
			setBrcode(callable.getString(6));
			setMessage(callable.getString(8));
			setStatus(callable.getString(7));
			setAccounttype(callable.getString(11));					//SUBPRODUCTID


		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}
	
	
	public String updateAccAuthData() {
		logger.debug("Inside Authorization Modificaiton. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		String ip = null;
		try {

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("Acc_No", accNumber);
			
			logger.debug("MultiData in updateAccAuthData"+multidata);
			connection = DBConnector.getConnection();

			queryConst = "{call ACCOUNTSPKG.UPDATEACCAUTHDATA(?,?,?,?,?,?,?,?,?,?,?,?)}";
			ip=ServletActionContext.getRequest().getRemoteAddr();
			
			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1,refno);
			callable.setString(2,institute);
			callable.setString(3,email);
			callable.setString(4,language);
			callable.setString(5,telephone);
			callable.setString(6,telco);
			callable.setString(7,isocode);
			callable.setString(8,multidata);
			callable.setString(9,makerid);
			callable.setString(10,auth_code);
			callable.registerOutParameter(11, Types.VARCHAR);
			callable.setString(12,ip);
			callable.execute();

			//logger.debug("  message [" + callable.getString(4) + "]"+ " Status [" + callable.getString(5) + "] + ["+  callable.getString(6));


			setStatus(callable.getString(11));


		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}

		
		public String fetchAccData() {
			logger.debug("Inside Account Creation.. ");
			String queryConst1 = "";
			CallableStatement callable = null;
			Connection connection = null;
			String ip = null;
			try {

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				ip=ServletActionContext.getRequest().getRemoteAddr();
			
				connection = DBConnector.getConnection();
				
				queryConst1 = "{call ACCOUNTSPKG.GETACCDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

				callable = connection.prepareCall(queryConst1);
				callable.setString(1,refno);
				callable.setString(2,custcode);
				callable.registerOutParameter(3, Types.VARCHAR);
				callable.registerOutParameter(4, Types.VARCHAR);
				callable.registerOutParameter(5, Types.VARCHAR);
				callable.registerOutParameter(6, Types.VARCHAR);
				callable.registerOutParameter(7, Types.VARCHAR);
				callable.registerOutParameter(8, Types.VARCHAR);
				callable.registerOutParameter(9, Types.VARCHAR);
				callable.registerOutParameter(10, Types.VARCHAR);
				callable.registerOutParameter(11, Types.VARCHAR);
				callable.registerOutParameter(12, Types.VARCHAR);
				callable.registerOutParameter(13, Types.VARCHAR);
				callable.setString(14,makerid);
				callable.setString(15,ip);
				callable.setString(16,apptype);

				callable.execute();

				logger.debug("  message [" + callable.getString(3) + "]"+ " Status [" + callable.getString(4) + "] + ["+  callable.getString(5));
				

				setCustcode(callable.getString(3));
				setAccNumber(callable.getString(4));
				setAccname(callable.getString(5));
				setMobile(callable.getString(6));
				setLanguage(callable.getString(7));
				setMessage(callable.getString(9));
				setStatus(callable.getString(8));
				setTelco(callable.getString(10));
				setMobiles(callable.getString(11));
				setWallet(callable.getString(12));
				setUserId(callable.getString(13));


			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				queryConst1 = null;
			}

			return SUCCESS;
		}
		
		
		
		public String fetchNotification() {
			logger.debug("Inside Account Creation.. ");
			try {

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				request = ServletActionContext.getRequest();
				
				setCustcode(NotificationJson.getMessage(request.getSession().getAttribute(CevaCommonConstants.MAKER_ID).toString()));
				


			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} 
			return SUCCESS;
		}
		
		public String primaryaccountdetails() {
			logger.debug("Inside primaryaccountdetails.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			String queryConst1 = "";
			Connection connection = null;
			try {

				connection = DBConnector.getConnection();
				
				if(apptype.equalsIgnoreCase("MOBILE")){
					
				
				
					if(refno.equalsIgnoreCase("CUSTOMER_CODE")){
						
						queryConst = "select count(*) from MOB_ACCT_DATA where cust_id in (select id from MOB_CUSTOMER_MASTER where CUSTOMER_CODE='"+custcode+"') AND PRIM_FLAG='P'";	
						
						queryConst1 = "select id from MOB_CUSTOMER_MASTER where CUSTOMER_CODE='"+custcode+"'";
					}if(refno.equalsIgnoreCase("ACCT_NO")){
						
						queryConst = "select count(*) from MOB_ACCT_DATA where ACCT_NO='"+custcode+"'  AND PRIM_FLAG='P'";
						
						queryConst1 = "select count(*) from MOB_ACCT_DATA where ACCT_NO='"+custcode+"'";
						
					}if(refno.equalsIgnoreCase("USER_ID")){
						
						queryConst = "select count(*) from MOB_ACCT_DATA where cust_id in (select id from MOB_CUSTOMER_MASTER where UPPER(USER_ID)=UPPER('"+custcode+"'))  AND PRIM_FLAG='P'";	
						queryConst1 = "select count(*) from MOB_CUSTOMER_MASTER where UPPER(USER_ID)=UPPER('"+custcode+"')";
						
					}if(refno.equalsIgnoreCase("MOBILE_NUMBER")){
						
						queryConst = "select count(*) from MOB_ACCT_DATA where cust_id in (select cust_id from MOB_CONTACT_INFO where MOBILE_NUMBER='"+custcode+"')  AND PRIM_FLAG='P'";	
						queryConst1 = "select count(*) from MOB_CONTACT_INFO where MOBILE_NUMBER='"+custcode+"'";
					}
				
				
					entityPstmt = connection.prepareStatement(queryConst1);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}
					entityPstmt.close();
					entityRS.close();
					
					if(finalCount==0){
						finalCount=-1;
					}else{
					
						entityPstmt = connection.prepareStatement(queryConst);
						entityRS = entityPstmt.executeQuery();
	
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
						}
					}
				
				}else{
					finalCount=1;
				}
				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}	

		
		public String validateaccountdetails() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";

				connection = DBConnector.getConnection();
				
					if(service.equalsIgnoreCase("ACCT_NO")){
						
						queryConst = "select COUNT(*) from FUND_TRANSFER_TBL where ACCOUNTNO='"+fname+"'";	
						
					}else{
						queryConst = "select COUNT(*) from FUND_TRANSFER_TBL where CREDITPAYMENTREFERENCE='"+fname+"'";
					}
				
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}	
				

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		
		
		public String fileuploadrec() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				refno=(refno.equalsIgnoreCase("")) ? null :  ""+refno+"";

				connection = DBConnector.getConnection();
				
					
						
						queryConst = "select COUNT(*) from FILE_UPLOAD_UNSETTLE where REF_NUM='"+refno+"' and STATUS in ('C','F')";	
					
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}	
				

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		public String validaterefsucrev() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";

				connection = DBConnector.getConnection();
				
					
						
						queryConst = "select COUNT(*) from WALLET_SUCCREV_REQUEST where PAYMENTREFERENCE='"+fname+"' and STATUS='P'";	
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}	
				

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}		
		
		public String validationstatement() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";
				
				frmdate=(fname.equalsIgnoreCase("")) ? null :  ""+frmdate+"";
				todate=(fname.equalsIgnoreCase("")) ? null :  ""+todate+"";

				connection = DBConnector.getConnection();
				
					if(service.equalsIgnoreCase("AGENT")){
						queryConst = "select COUNT(*) from WALLET_ACCT_DATA MD,AGENT_CONTACT_INFO MCI,AGENT_CUSTOMER_MASTER ACM where ACM.ID=MCI.CUST_ID AND MD.CUST_ID=MCI.CUST_ID AND PRIM_FLAG='P' AND MOBILE_NUMBER='234"+fname+"'";
						
					}else{
						queryConst = "select COUNT(*) from WALLET_ACCT_DATA MD,MOB_CONTACT_INFO MCI,MOB_CUSTOMER_MASTER ACM where ACM.ID=MCI.CUST_ID AND MD.CUST_ID=MCI.CUST_ID AND PRIM_FLAG='P' AND MOBILE_NUMBER='234"+fname+"'";
					}
				
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
						
						if(finalCount==0) {
							finalCount=50;
						}
					}
					entityPstmt.close();
					entityRS.close();
					
					if(finalCount!=50) {
						queryConst = "select COUNT(*) from WALLET_FIN_TXN_POSTING where USER_ID='234"+fname+"' AND TRUNC(TXN_STAMP) between to_date('"+frmdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy')";
						entityPstmt = connection.prepareStatement(queryConst);
						entityRS = entityPstmt.executeQuery();

						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							
						}
	
					}
				

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		public String rmvalidation() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";

				connection = DBConnector.getConnection();
					
						queryConst = "select COUNT(*) from RMCODE_TBL where CUSTOMER='"+fname+"'";	
				
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}	
				

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
			
		public String dashboardpie() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			String userReport1="";
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";

				connection = DBConnector.getConnection();
				StringBuilder sb=new StringBuilder();
				if(fname.equalsIgnoreCase("user")){
					 sb.append("nvl((SELECT  COUNT(distinct A.NET_ID) FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE AND TO_CHAR(TXNDATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND (CML.MENU_ACTION='LDAP_AUTH')),'0'),");
					 sb.append("nvl((SELECT  COUNT(distinct A.NET_ID) FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE AND TO_CHAR(TXNDATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND (CML.MENU_ACTION='MOBILE_USSD')),'0')");
					 userReport1 =" select "+sb.toString()+" from dual";
					 entityPstmt = connection.prepareStatement(userReport1);
						entityRS = entityPstmt.executeQuery();
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							finalCount1 = entityRS.getInt(2);
							
						}	
					
				}else if(fname.equalsIgnoreCase("newuser")){
					 sb.append("nvl((SELECT count(*) FROM MOB_CUSTOMER_MASTER WHERE  TO_CHAR(DATE_CREATED, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND AUTHID='MOBILE'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM MOB_CUSTOMER_MASTER WHERE  TO_CHAR(DATE_CREATED, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND AUTHID='USSD'),'0')");
					 userReport1 =" select "+sb.toString()+" from dual";
					 entityPstmt = connection.prepareStatement(userReport1);
						entityRS = entityPstmt.executeQuery();
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							finalCount1 = entityRS.getInt(2);
							
						}	
					
				}else if(fname.equalsIgnoreCase("order")){
					 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER OPM1, ONLINE_PRODUCTS_MASTER OPM2 WHERE  OPM1.PRODUCT_ID=OPM2.PRODUCT_ID AND TO_CHAR(OPM1.TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND OPM2.CATEGORY_ID='ONCAT001'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER OPM1, ONLINE_PRODUCTS_MASTER OPM2 WHERE  OPM1.PRODUCT_ID=OPM2.PRODUCT_ID AND TO_CHAR(OPM1.TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND OPM2.CATEGORY_ID='ONCAT002'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER OPM1, ONLINE_PRODUCTS_MASTER OPM2 WHERE  OPM1.PRODUCT_ID=OPM2.PRODUCT_ID AND TO_CHAR(OPM1.TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND OPM2.CATEGORY_ID='ONCAT003'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER OPM1, ONLINE_PRODUCTS_MASTER OPM2 WHERE  OPM1.PRODUCT_ID=OPM2.PRODUCT_ID AND TO_CHAR(OPM1.TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND OPM2.CATEGORY_ID='ONCAT004'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER OPM1, ONLINE_PRODUCTS_MASTER OPM2 WHERE  OPM1.PRODUCT_ID=OPM2.PRODUCT_ID AND TO_CHAR(OPM1.TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND OPM2.CATEGORY_ID='ONCAT005'),'0')");
					 userReport1 =" select "+sb.toString()+" from dual";
					 entityPstmt = connection.prepareStatement(userReport1);
						entityRS = entityPstmt.executeQuery();
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							finalCount1 = entityRS.getInt(2);
							finalCount2 = entityRS.getInt(3);
							finalCount3 = entityRS.getInt(4);
							finalCount4 = entityRS.getInt(5);
							
						}	
					
				}else if(fname.equalsIgnoreCase("trans")){
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='FUND_TRNS_OTCR'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='FUND_OTHER_REVERSAL'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='PAY_BILL_REVERSAL'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='RECHARGE' AND REVERSALSTATUS='N'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='PAY_BILL'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='CUSTFUNDTRANSF'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='REVERSAL'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE='RECHARGE' AND REVERSALSTATUS='Y'),'0')");



					 userReport1 =" select "+sb.toString()+" from dual";
					 entityPstmt = connection.prepareStatement(userReport1);
						entityRS = entityPstmt.executeQuery();
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							finalCount1 = entityRS.getInt(2);
							finalCount2 = entityRS.getInt(3);
							finalCount3 = entityRS.getInt(4);
							finalCount4 = entityRS.getInt(5);
							finalCount5 = entityRS.getInt(6);
							finalCount6 = entityRS.getInt(7);
							finalCount7 = entityRS.getInt(8);
							
						}	
					
				}else if(fname.equalsIgnoreCase("locator")){
					 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND status='AU'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND status='AA'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND status='CU'),'0'),");
					 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND status='CA'),'0')");
					 userReport1 =" select "+sb.toString()+" from dual";
					 entityPstmt = connection.prepareStatement(userReport1);
						entityRS = entityPstmt.executeQuery();
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
							finalCount1 = entityRS.getInt(2);
							finalCount2 = entityRS.getInt(3);
							finalCount3 = entityRS.getInt(4);
							
						}	
					
				}
				
				
				
					
					

			

				logger.debug("Got Count [" + institute + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		public String fetchAccDatafromCore() {
			logger.debug("Inside Account Creation.. ");
			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			String entityQry = "";
			Connection connection = null;
			try {
				connection = DBConnector.getConnection();
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				entityQry = "select substr('"+accNumber+"',length('"+accNumber+"')-3)||''||COUNT(*)+1 from ORG_MASTER";
				entityPstmt = connection.prepareStatement(entityQry);
				entityRS = entityPstmt.executeQuery();
				while (entityRS.next()){
					setLanguage(entityRS.getString(1));
				}
				entityPstmt.close();
				entityRS.close();
				
				queryConst = "select COUNT(*) from ORG_MASTER WHERE ACCOUNT_NUMBER='"+accNumber+"'";
				entityPstmt = connection.prepareStatement(queryConst);
				entityRS = entityPstmt.executeQuery();
				while (entityRS.next()){
					if(entityRS.getInt(1)==0){
						JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(accNumber));
						setAccname(json1.getString("accountName"));
						setService(json1.getString("currencyCode"));
						setEmail("");
						setMobile(json1.getString("phone"));
						setCustcode(json1.getString("branchName"));
						setBrcode(json1.getString("branchCode"));
						setMessage("success");
					}else{
						setMessage("Account Number Already Registred.");
					}
				}
				
				



			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		
		public String fetchAccDatafromCorenew() {
			logger.debug("Inside Account Creation.. ");
			String mobileno="";
			try {
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(accNumber));
				if(!(json1.getString("custID")).equalsIgnoreCase("")){
				setAccname(json1.getString("accountName"));
				setService(json1.getString("currencyCode"));
				setAccountNumber(accNumber);
				
				if((json1.getString("phone")).startsWith("0")){
					mobileno="234"+(json1.getString("phone")).substring(1);
				}else if((json1.getString("phone")).startsWith("234")){
					mobileno=json1.getString("phone");
				}else{
					mobileno="234"+json1.getString("phone");
				}
				
				setMobile(mobileno);
				setCustcode(json1.getString("custID"));
				setBrcode(json1.getString("branchCode"));
				setAccounttype(json1.getString("productName"));
				
					setMessage("success");	
				}else{
					setMessage("Invalid Account Number");
				}
				


			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} 
			return SUCCESS;
		}
		
		
		public String addCustomerValidation() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				custcode=(custcode.equalsIgnoreCase("")) ? null :  ""+custcode+"";
				mobile=(mobile.equalsIgnoreCase("")) ? null :  ""+mobile+"";

				connection = DBConnector.getConnection();
					
					queryConst = "select COUNT(*) from MOB_CUSTOMER_MASTER where CUSTOMER_CODE='"+custcode+"'";	
				
				
					
					entityPstmt = connection.prepareStatement(queryConst);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
						
					}	
					
					entityPstmt.close();
					entityRS.close();
					
					if(finalCount==0){
					
						queryConst = "select COUNT(*) from MOB_CONTACT_INFO where MOBILE_NUMBER='"+mobile+"'";	
					
					
						
						entityPstmt = connection.prepareStatement(queryConst);
						entityRS = entityPstmt.executeQuery();
	
						if (entityRS.next()) {
							finalCount = entityRS.getInt(1);
						}
						if(finalCount!=0){
							setMessage("Mobile Number Is already Exits");
						}
						
					}else{
						setMessage("Customer already Exits");
					}

				logger.debug("Got Count [" + finalCount + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 0;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		public String authactionview() {
			logger.debug("Inside searchAgent.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			String userReport1="";
			try {
				fname=(fname.equalsIgnoreCase("")) ? null :  ""+fname+"";

				connection = DBConnector.getConnection();
				StringBuilder sb=new StringBuilder();
				
				
				JSONObject json = new JSONObject();
				
				 String viewReport="select AP.REF_NUM,AP.MAKER_ID,to_char(AP.MAKER_DTTM,'dd-MON-yyyy'),NVL(AP.CHECKER_ID,' '),NVL(to_char(AP.CHECKER_DTTM,'dd-MON-yyyy'),' '),NVL((select RELATION FROM AUTH_RELATION WHERE RELATION_CODE=AP.AUTH_FLAG),' '),NVL((select HEADING_NAMES FROM AUTH_MASTER WHERE AUTH_CODE=AP.AUTH_CODE AND RELATION=AP.AUTH_FLAG),' '),DECODE(AP.STATUS,'P','Process','C','Approval','R','Reject'),NVL(ACTION_DETAILS,' '),NVL(REASON,' ') FROM AUTH_PENDING AP WHERE AP.REF_NUM=?";
					
				 entityPstmt = connection.prepareStatement(viewReport);
				 entityPstmt.setString(1,fname);
				 entityRS = entityPstmt.executeQuery();
					
					
					 while(entityRS.next())
						{
						 json=new JSONObject();
						 json.put("REF_NUM", entityRS.getString(1));
							json.put("MAKER_ID", entityRS.getString(2));
							json.put("MAKER_DTTM", entityRS.getString(3));
							json.put("CHECKER_ID", entityRS.getString(4));
							json.put("CHECKER_DTTM", entityRS.getString(5));
							json.put("MAIN_MENU", entityRS.getString(6));
							json.put("ACTION_MENU", entityRS.getString(7));
							json.put("STATUS", entityRS.getString(8));
							json.put("ACTION_DETAILS", entityRS.getString(9));
							json.put("REASON", entityRS.getString(10));
						 } 
					

				setMessage(json.toString());

				logger.debug("Got Count [" + institute + "]");

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		
		
		public String fetchAddNewAccDatafromCore() {
			logger.debug("Inside Account Creation.. ");
			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			String entityQry = "";
			Connection connection = null;
			try {
				connection = DBConnector.getConnection();
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				
				
				/*queryConst = "select COUNT(*) from ORG_MASTER WHERE ACCOUNT_NUMBER='"+accNumber+"'";
				entityPstmt = connection.prepareStatement(queryConst);
				entityRS = entityPstmt.executeQuery();
				while (entityRS.next()){
					if(entityRS.getInt(1)==0){*/
						JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(accNumber));
						setAccname(json1.getString("accountName"));
						setService(json1.getString("currencyCode"));
						setEmail(json1.getString("productName"));
						setMobile(json1.getString("phone"));
						setCustcode(json1.getString("branchName"));
						setBrcode(json1.getString("branchCode"));
						setMessage("success");
					/*}else{
						setMessage("Account Number Already Registred.");
					}
				}*/
				
				



			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		
		public String fetchWalletAccData() {
			logger.debug("Inside Account Creation.. ");
			String queryConst1 = "";
			CallableStatement callable = null;
			Connection connection = null;
			String ip = null;
			try {

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				ip=ServletActionContext.getRequest().getRemoteAddr();
			
				connection = DBConnector.getConnection();
				
				
				queryConst1 = "{call WALLETACCOUNTSPKG.GETACCDATA(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
				//queryConst1 = "{call WALLETACCOUNTSPKG.GETACCDATA(?,?,?,?,?,?,?,?,?,?,?,?)}";

				callable = connection.prepareCall(queryConst1);
				callable.setString(1,refno);
				callable.setString(2,custcode);
				callable.registerOutParameter(3, Types.VARCHAR);
				callable.registerOutParameter(4, Types.VARCHAR);
				callable.registerOutParameter(5, Types.VARCHAR);
				callable.registerOutParameter(6, Types.VARCHAR);
				callable.registerOutParameter(7, Types.VARCHAR);
				callable.registerOutParameter(8, Types.VARCHAR);
				callable.registerOutParameter(9, Types.VARCHAR);
				callable.registerOutParameter(10, Types.VARCHAR);
				callable.setString(11,makerid);
				callable.setString(12,ip);
				callable.setString(13,apptype);
				callable.execute();

				logger.debug("  message [" + callable.getString(3) + "]"+ " Status [" + callable.getString(4) + "] + ["+  callable.getString(5));
				

				setCustcode(callable.getString(3));
				setAccNumber(callable.getString(4));
				setAccname(callable.getString(5));
				setMobile(callable.getString(6));
				setLanguage(callable.getString(7));
				setMessage(callable.getString(9));
				setStatus(callable.getString(8));
				setTelco(callable.getString(10));
				setMobiles(apptype);


			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				queryConst1 = null;
			}

			return SUCCESS;
		}
		
		
		public String verifyMobile() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";

			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				connection = DBConnector.getConnection();
				String multi=" ";
				
				System.out.println("kailash "+isocode);
				
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid(mobile));
				//System.out.println(json1);
				JSONArray jsonarray =  JSONArray.fromObject(json1.get("custinfo"));
				Iterator iterator = jsonarray.iterator();
				while (iterator.hasNext()) {
					JSONObject jsonobj=(JSONObject)iterator.next();
					multi=(String)jsonobj.get("custPhone");
				}
				
				if(isocode.equalsIgnoreCase("nigeria")) {
					
					if(multi.startsWith("0")){
						multi="234"+multi.substring(1);
					}
					if(multi.length()==10){
						multi="234"+multi;
					}
				}
				
				/*
				Qry = "select CUSTOMER_CODE||'-'||FIRST_NAME from MOB_CUSTOMER_MASTER where ID in (Select CUST_ID from MOB_CONTACT_INFO where MOBILE_NUMBER=? AND APP_TYPE='WALLET')";
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1,mobile);
				rS = pstmt.executeQuery();
				String custdata=null;
				
				int i=0;

				while (rS.next()) {
					custdata = rS.getString(1);	
					
					if (i == 0) {
						multi += custdata;
					} else {
						multi += "#" + custdata;
					}
					i++;
				}*/
				
				
				logger.debug("Mobile Numbers in Return" + multi);
				
				setLanguage(multi);
				//setMessage();
				//setStatus(callable.getString(8));
				
				
			}catch(JSONException je){
				setLanguage("0");
				je.printStackTrace();
			} catch (Exception e) {
				setLanguage("0");
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		
		
		public String verifyWalletAgentMobile() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";

			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				connection = DBConnector.getConnection();
				String multi=" ";
				
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(mobile));
				//System.out.println(json1);
				multi=(String)json1.getString("phone");
				if(multi.startsWith("0")){
					multi="234"+(json1.getString("phone")).substring(1);
				}
				if(multi.length()==10){
					multi="234"+json1.getString("phone");
				}

				logger.debug("Mobile Numbers in Return" + multi);
				
				setLanguage(multi);
				//setMessage();
				//setStatus(callable.getString(8));
				
				
			}catch(JSONException je){
				setLanguage("0");
				je.printStackTrace();
			} catch (Exception e) {
				setLanguage("0");
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		public String verifyuserid() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";

			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				connection = DBConnector.getConnection();
				String multi=" ";
				
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getCustomerDetail(mobile));
				//System.out.println(json1);
				JSONArray jsonarray =  JSONArray.fromObject(json1.get("CUST_INFO"));
				Iterator iterator = jsonarray.iterator();
				while (iterator.hasNext()) {
					JSONObject jsonobj=(JSONObject)iterator.next();
					multi=(String)jsonobj.get("mobilenumber");
				}
				if(multi.startsWith("0")){
					multi="234"+multi.substring(1);
				}
				
				
				logger.debug("Mobile Numbers in Return" + multi);
				
				setLanguage(multi);
				//setMessage();
				//setStatus(callable.getString(8));
				
				
			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		
		public String fetchlimiservices() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";
			JSONObject json = null;
			HashMap<String, Object> resultMap = null;
			
			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				connection = DBConnector.getConnection();
				resultMap = new HashMap<String, Object>();
				
				Qry="select mp.menu_code||'-'||mp.DISPLAY_NAME,mp.DISPLAY_NAME,mi.id from mob_servicepack_master mi,mob_servicepack_map mp "
						+ "where mi.id=mp.pid and mp.menu_type='SERVICE' connect by  "
						+ "prior trim(mp.MENU_CODE)=mi.SERVICEPACK_CODE start with mi.servicepack_code=?";		
				
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1,menucode);
				rS = pstmt.executeQuery();
				
				json = new JSONObject();
				while (rS.next()) {
					json.put(rS.getString(1), rS.getString(2));
				}

				setResponseJSON(json);
				logger.debug("values passed in json object in ajax"+json);
				pstmt.close();
				rS.close();

				resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				responseDTO.setData(resultMap);
				
			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		public String fetchMenuDetails() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";
			JSONObject json = null;
			HashMap<String, Object> resultMap = null;
			String eachrow = "";
			String billerData = "";
			
			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				connection = DBConnector.getConnection();
				resultMap = new HashMap<String, Object>();
				
				Qry = "select MENU_TYPE,DISPLAY_NAME,STATUS from MOB_SERVICEPACK_MAP where pid=(select id from MOB_SERVICEPACK_MASTER where SERVICEPACK_CODE=?)";	
				
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1,menucode);
				rS = pstmt.executeQuery();
				
				rS = pstmt.executeQuery();

				int i = 0;

				while (rS.next()) {
					eachrow = rS.getString(1) + ","+ rS.getString(2)+ ","+ rS.getString(3);
					if (i == 0) {
						billerData += eachrow;
					} else {
						billerData += "#" + eachrow;
					}
					i++;
				}
				
				json = new JSONObject();
				json.put("servicepackdet",billerData);
				setResponseJSON(json);
				logger.debug("values passed in json object in ajax"+json);
				pstmt.close();
				rS.close();

				//resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				//responseDTO.setData(resultMap);
				
			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		public String fetchproducts() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";
			JSONObject json = null;
			HashMap<String, Object> resultMap = null;
			
			
			try {
				
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				connection = DBConnector.getConnection();
				resultMap = new HashMap<String, Object>();
				
				Qry="select distinct(PRODUCT_ID),PRODUCT_ID from MOB_PRODUCTS_MASTER where INSTITUTE_ID=?";		
				
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1,institute);
				rS = pstmt.executeQuery();
				
				json = new JSONObject();
				while (rS.next()) {
					json.put(rS.getString(1), rS.getString(2));
				}
				
				setResponseJSON(json);
				logger.debug("values passed in json object in ajax"+json);
				pstmt.close();
				rS.close();
				
				resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				responseDTO.setData(resultMap);
				
			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				Qry = null;
			}
			
			return SUCCESS;
		}
		
		public String fetchSpackDetails() {
			logger.debug("Inside Account Creation.. ");
			CallableStatement callable = null;
			Connection connection = null;
		/*	PreparedStatement pstmt = null;
			ResultSet rS = null;
			String Qry = "";*/
			JSONObject json = null;
	/*		String eachrow = "";
			String billerData = "";
			String detQry1 = "";*/

			String userQry = "";
			String userQry1 = "";
			String userQry2 = "";
			ResultSet userRS = null;
			PreparedStatement userPstmt = null;

			JSONObject json1 = null;
			JSONObject json2 = null;
			HashMap<String, Object> resultMap = null;

			JSONArray userJSONArray = null;
			JSONArray userJSONArray1 = null;
			JSONArray userJSONArray2 = null;
			
			
			try {
				
				
				/**********/
				
				logger.debug("Inside Fetch Service Packs.. ");
				json = new JSONObject();

				connection = DBConnector.getConnection();
				logger.debug("Connection is null  [" + connection == null + "]");
				

				userQry = "SELECT SERVICE_CODE,SERVICE_NAME FROM MOB_SERVICE_MASTER where service_code not in "
						+ "( SELECT MENU_CODE FROM MOB_SERVICEPACK_MAP where pid= (select id from MOB_SERVICEPACK_MASTER where SERVICEPACK_CODE=?) "
						+ "and menu_type='SERVICE')";
				
				

				userQry1 = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='0' "
						+ "and SERVICEPACK_CODE not in (select distinct(menu_code) from MOB_SERVICEPACK_MAP)";
				
				
				//MENU_TYPE||'--'||
				userQry2 = " SELECT MENU_CODE,(case sp.menu_type when 'SERVICE' then  "
						+ "(select ST.SERVICE_NAME from MOB_SERVICE_MASTER ST where ST.SERVICE_CODE=SP.MENU_CODE)  else "
						+ "(select SERVICEPACK_NAME from MOB_SERVICEPACK_MASTER SV where SV.SERVICEPACK_CODE=SP.MENU_CODE) end)  "
						+ "FROM MOB_SERVICEPACK_MAP SP  WHERE pid=(SELECT id FROM MOB_SERVICEPACK_MASTER WHERE SERVICEPACK_CODE=? )  "
						+ "order by menu_order";
				
				
		
				resultMap = new HashMap<String, Object>();
				userJSONArray = new JSONArray();
				userJSONArray1 = new JSONArray();
				userJSONArray2 = new JSONArray();

				userPstmt = connection.prepareStatement(userQry);
				userPstmt.setString(1, prdid);
				//userPstmt.setString(1, requestJSON.getString("USER_ID"));
				userRS = userPstmt.executeQuery();

				while (userRS.next()) {
					json = new JSONObject();
					//json.put(CevaCommonConstants.SELECT_KEY, "SERVICE--"+userRS.getString(1));
					json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
					json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
					userJSONArray.add(json);
				}

				userPstmt.close();
				userRS.close();

				logger.debug("UserQry :::" + userJSONArray);

				userPstmt = connection.prepareStatement(userQry1);
				userRS = userPstmt.executeQuery();

				while (userRS.next()) {
					json1 = new JSONObject();
					//json1.put(CevaCommonConstants.SELECT_KEY, "MENULIST--"+userRS.getString(1));
					json1.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
					json1.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
					userJSONArray1.add(json1);
				}

				userPstmt.close();
				userRS.close();
				
				logger.debug("UserQry :::" + userJSONArray1);

				userPstmt = connection.prepareStatement(userQry2);
				userPstmt.setString(1, prdid);
				userRS = userPstmt.executeQuery();

				while (userRS.next()) {
					json2 = new JSONObject();
					json2.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
					json2.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
					userJSONArray2.add(json2);
				}

				userPstmt.close();
				userRS.close();

				logger.debug("output from query"+userJSONArray2);
				
				json.put("SERVICEPACKS", userJSONArray);
				json.put("MENULIST", userJSONArray1);
				json.put("SELECTEDLIST", userJSONArray2);
				
				//logger.debug("output from query"+userJSONArray1);

				//resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				setResponseJSON(json);
				
				//responseDTO.setData(resultMap);
				
				/**********/
			/*	
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				connection = DBConnector.getConnection();
				
				Qry="SELECT SERVICEPACK_NAME,SERVICEPACK_CODE||'-'||SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER";		
				
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1,institute);
				rS = pstmt.executeQuery();
				
				json = new JSONObject();
				while (rS.next()) {
					json.put(rS.getString(1), rS.getString(2));
				}
				
				
				//json.clear();

				Qry = null;
				pstmt.close();
				rS.close();
				
				json = new JSONObject();
								
				detQry1 = "SELECT SERVICEPACK_CODE,SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER where USER_LEVEL='0' "
						+ "and SERVICEPACK_CODE not in (select distinct(menu_code) from MOB_SERVICEPACK_MAP)";
				
				pstmt = connection.prepareStatement(detQry1);
				pstmt.setString(1, prdid);
				
				rS = pstmt.executeQuery();

				int i = 0;

				while (rS.next()) {
					eachrow = rS.getString(1) + ","+ rS.getString(2);
					if (i == 0) {
						billerData += eachrow;
					} else {
						billerData += "#" + eachrow;
					}
					i++;
				}
				
				json.put("subprddata",billerData);
				logger.debug("values passed in json object in ajax"+json);
				
				//detMap.put("user_rights", resultJson);
				//responseDTO.setData(detMap);
				
				eachrow="";
				billerData = "";
				pstmt.close();
				rS.close();
				
				
				
				Qry = "SELECT (SELECT SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER P WHERE P.SERVICEPACK_CODE=M.SPACK_ID) SPACK_ID,"
						+ "(SELECT SERVICE_NAME from MOB_SERVICE_MASTER S WHERE S.SERVICE_CODE=M.SERVICE_ID) SERVICE_ID,"
						+ "(SELECT KEY_VALUE FROM CONFIG_DATA C WHERE C.KEY_ID=M.FREQUENCY) FREQUENCY,"
						+ "DECODE(CONDITION,'C','COUNT','A','AMOUNT') CONDITION,"
						+ "FROM_VALUE,TO_VALUE FROM MOB_PRODUCTS_SERVICE_MAP M WHERE PRODUCT_ID=?";
				
				pstmt = connection.prepareStatement(Qry);
				pstmt.setString(1, prdid);
				
				rS = pstmt.executeQuery();

				int j = 0;

				while (rS.next()) {
					eachrow = rS.getString(1) + ","
							+ rS.getString(2) + ","
							+ rS.getString(3) + ","
							+ rS.getString(4) + ","
							+ rS.getString(5) + ","
							+ rS.getString(6);
					
					if (j == 0) {
						billerData += eachrow;
					} else {
						billerData += "#" + eachrow;
					}
					j++;
				}
				
				json.put("status",billerData);
				
				setResponseJSON(json);
				
				
				//setLimitsJSON(json);
				//resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
				//responseDTO.setData(resultMap);
*/				
			} catch (Exception e) {
				logger.debug(" Got error [" + e.getMessage() + "]");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closeCallableStatement(callable);
				//Qry = null;
				userQry = null;
				userQry1 = null;
			}
			
			return SUCCESS;
		}
		
		
		
		public String verifyMobileNo() {
			logger.debug("Inside CheckCountGroup.. ");

			PreparedStatement entityPstmt = null;
			ResultSet entityRS = null;
			String queryConst = "";
			Connection connection = null;
			try {
				connection = DBConnector.getConnection();
				
				if(mobile.equalsIgnoreCase(mnumber)){
					finalCount =0;
				}else{
				
					queryConst = "select count(*) from MOB_CONTACT_INFO where MOBILE_NUMBER=?";
					entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
					entityPstmt.setString(1, mobile);
					entityRS = entityPstmt.executeQuery();
	
					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}
			   }
				logger.debug("Got Count [" + finalCount + "]");
				//setLanguage(finalCount);

			} catch (Exception e) {
				logger.debug("Got error [" + e.getMessage() + "]");
				finalCount = 2;
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(entityPstmt);
				DBUtils.closeResultSet(entityRS);
			}
			return SUCCESS;
		}
		
		
		
			public String verifyMenucode() {
				logger.debug("Inside CheckCountGroup.. ");

				PreparedStatement entityPstmt = null;
				ResultSet entityRS = null;
				String queryConst = "";
				Connection connection = null;
				try {
					connection = DBConnector.getConnection();
					queryConst = "select count(*) from MOB_SERVICEPACK_MASTER where SERVICEPACK_CODE=?";
					entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
					entityPstmt.setString(1, mobile);
					entityRS = entityPstmt.executeQuery();

					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}
					
					logger.debug("Got Count [" + finalCount + "]");
					//setLanguage(finalCount);

				} catch (Exception e) {
					logger.debug("Got error [" + e.getMessage() + "]");
					finalCount = 0;
				} finally {
					DBUtils.closeConnection(connection);
					DBUtils.closePreparedStatement(entityPstmt);
					DBUtils.closeResultSet(entityRS);
				}
				return SUCCESS;
			}
			public String generateVisaId() {
				logger.debug("Inside CheckCountGroup.. ");
				
				PreparedStatement entityPstmt = null;
				ResultSet entityRS = null;
				String queryConst = "";
				Connection connection = null;
				try {
					connection = DBConnector.getConnection();
					queryConst = "select MOB_MVISA_ID_SEQ.nextval from dual";
					entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
					//entityPstmt.setString(1, mobile);
					entityRS = entityPstmt.executeQuery();
					
					if (entityRS.next()) {
						finalCount = entityRS.getInt(1);
					}
					
					logger.debug("Got Count [" + finalCount + "]");
					
					String mvid=dlName+finalCount;
					
					logger.debug("Generated Mvisa ID Value without Check Digit"+mvid);
					int chkval=CheckDigitGeneration.checkdigit(mvid);
					
					logger.debug("Generated CheckDigit Value"+chkval);
					
					logger.debug("Generated Mvisa ID"+mvid+chkval);
					//setLanguage(finalCount);
					
					mvisaid=mvid+chkval;
					
				} catch (Exception e) {
					logger.debug("Got error [" + e.getMessage() + "]");
					finalCount = 0;
				} finally {
					DBUtils.closeConnection(connection);
					DBUtils.closePreparedStatement(entityPstmt);
					DBUtils.closeResultSet(entityRS);
				}
				return SUCCESS;
			}
			
			
			public String fetchPrepCardData() {
				logger.debug("Inside Account Creation.. ");
				String queryConst1 = "";
				CallableStatement callable = null;
				Connection connection = null;
				String ip = null;
				try {

					requestJSON = new JSONObject();
					requestDTO = new RequestDTO();
					ip=ServletActionContext.getRequest().getRemoteAddr();
				
					connection = DBConnector.getConnection();
					
					
					queryConst1 = "{call PREPAIDPKG.GETCARDDATA(?,?,?,?,?,?,?,?,?,?,?)}";

					callable = connection.prepareCall(queryConst1);
					callable.setString(1,refno);
					callable.setString(2,custcode);
					callable.registerOutParameter(3, Types.VARCHAR);
					callable.registerOutParameter(4, Types.VARCHAR);
					callable.registerOutParameter(5, Types.VARCHAR);
					callable.registerOutParameter(6, Types.VARCHAR);
					callable.registerOutParameter(7, Types.VARCHAR);
					callable.registerOutParameter(8, Types.VARCHAR);
					callable.registerOutParameter(9, Types.VARCHAR);
					callable.setString(10,makerid);
					callable.setString(11,ip);
					callable.execute();

					logger.debug("  message [" + callable.getString(3) + "]"+ " Status [" + callable.getString(4) + "] + ["+  callable.getString(5));
					

					setCustcode(callable.getString(3));
					setAccNumber(callable.getString(4));
					setAccname(callable.getString(5));
					setMobile(callable.getString(6));
					setLanguage(callable.getString(7));
					setMessage(callable.getString(9));
					setStatus(callable.getString(8));


				} catch (Exception e) {
					logger.debug(" Got error [" + e.getMessage() + "]");
				} finally {
					DBUtils.closeConnection(connection);
					DBUtils.closeCallableStatement(callable);
					queryConst1 = null;
				}

				return SUCCESS;
			}
			
			
			public String validatebulkdata() {
				logger.debug("Inside Account Creation.. ");
				String queryConst1 = "";
				PreparedStatement detPstmt = null;
				PreparedStatement pstmt = null;
				ResultSet rs=null;
				Connection con = null;
				String ip = null;
				CSVReader csvReader = null;
				String[] nextLine;
				String Qry=null; 
				JSONObject json = null;
				AccountBean accBean = null;
				
				try {
					
					requestJSON = new JSONObject();
					requestDTO = new RequestDTO();
					ip=ServletActionContext.getRequest().getRemoteAddr();
					con = DBConnector.getConnection();
			//		fileUpload=accBean.getBulkupload();
					
/*					DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
					Date date = new Date();
					System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
					
					System.out.println("fileUpload valeu   "+fileUpload);
					File saveFilePath = new File("E:/Upload/" + dateFormat.format(date)+".csv");
					FileUtils.copyFile(fileUpload, saveFilePath);
					
					System.out.println("filesaved");*/
					
					csvReader = new CSVReader(new FileReader("C:\\Users\\user\\Desktop\\test1.csv"));
					String[] headerRow = csvReader.readNext();
					
					if (null == headerRow) {
						throw new FileNotFoundException(
								"No columns defined in given CSV file.Please check the CSV file format.");
					}
					
					
						con = DBConnector.getConnection();
						con.setAutoCommit(false);

						final int batchSize = 1000;
						int count = 0;
						//Date date = null;
						String custids="";
						String mobnos="";
						
						while ((nextLine = csvReader.readNext()) != null) {
							if (null != nextLine) {
								int index = 1;
								for (String string : nextLine) {
									if (index==1)
									{
										if (count==0)
										custids+="'"+string;
										else
										custids+="','"+ string;
									}
									
									if (index==2)
									{
										if (count==0)
											mobnos+="'"+string;
										else
											mobnos+="','"+ string;
									}
									index++;
								}
								//ps.addBatch();
							}
							if (++count % batchSize == 0) {
								//ps.executeBatch();
							}
						}
						
						custids=custids.concat("'");
						mobnos=mobnos.concat("'");
						System.out.println("total index"+count+"custids"+custids);
						System.out.println("mobnos"+mobnos);
						
						//String qry=("SELECT CUSTOMER_CODE,FIRST_NAME from MOB_CUSTOMER_MASTER where CUSTOMER_CODE in (" + custids+")");
						StringBuilder eachrow = new StringBuilder(50);
						StringBuilder billerData = new StringBuilder(50);
						Qry = "select a,b,c from (SELECT CUSTOMER_CODE a,FIRST_NAME b,'CUSTOMERCODE' c from MOB_CUSTOMER_MASTER where CUSTOMER_CODE in (" + custids+") "
								+ "union "
								+ "SELECT CUSTOMER_CODE a,FIRST_NAME b,'MOBILE NUMBER' c from MOB_CUSTOMER_MASTER where ID in (select cust_id from mob_contact_info where MOBILE_NUMBER in (" + mobnos+"))) order by c";
						
						//System.out.println("Query before executing"+qry);
						//rs=con.createStatement().executeQuery(qry);
						
						int i = 0;
						json = new JSONObject();
						pstmt = con.prepareStatement(Qry);
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
						
							eachrow.append(rs.getString(1)).append(",").append(rs.getString(2)).append(",").append(rs.getString(3));
							if (i == 0) billerData.append(eachrow);
							else billerData.append("#").append(eachrow);
							
							i++;
							eachrow.delete(0, eachrow.length());
							System.out.println("eachrow"+rs.getString(1) + "-"	+ rs.getString(2)+"-"+rs.getString(3));
						}
						
						//json.put("customers",billerData.toString());
						setCustcode(billerData.toString());
						
						//	accBean.setSalutation(billerData.toString());
							billerData.delete(0, eachrow.length());
							logger.debug("Responce details " + billerData);
							
							DBUtils.closePreparedStatement(detPstmt);
							DBUtils.closeResultSet(rs);
							
						/*StringBuilder eachrow1 = new StringBuilder(50);
						StringBuilder billerData1 = new StringBuilder(50);
						String qry1=("SELECT CUSTOMER_CODE,FIRST_NAME from MOB_CUSTOMER_MASTER where ID in (select cust_id from mob_contact_info where MOBILE_NUMBER in (" + mobnos+"))");
						
						System.out.println("Query before executing"+qry1);
						rs=con.createStatement().executeQuery(qry1);
						
						i = 0;
						while (rs.next()) {
							
							eachrow1.append(rs.getString(1)).append(",").append(rs.getString(2));
							if (i == 0) billerData1.append(eachrow1);
							else billerData1.append("#").append(eachrow1);
							i++;
							eachrow.delete(0, eachrow.length());
							System.out.println("eachrow111"+rs.getString(1) + "-"	+ rs.getString(2));
						}*/
						//json.put("mobilenumbers",billerData.toString());
						//setTelephone(billerData1.toString());
							//accBean.setAccDetails(billerData1.toString());
						//	billerData.delete(0, eachrow1.length());
						//	logger.debug("Responce details111 " + billerData1);
							
						//	DBUtils.closePreparedStatement(detPstmt);
						//	DBUtils.closeResultSet(rs);
						//	con.commit();
						//	//con.close();
							csvReader.close();
							
				} catch (Exception e) {
					logger.debug(" Got error [" + e.getMessage() + "]");
				} finally {
					DBUtils.closeConnection(con);
					DBUtils.closePreparedStatement(detPstmt);
					queryConst1 = null;
				}

				return SUCCESS;
			}
	

	public String searchProcessingCode() {
		logger.debug("Inside SearchProcessingCode.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		try {

			connection = DBConnector.getConnection();

			queryConst = "{call HudumaPkg.checkProcessingCode(?,?,?)}";

			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getProcessingCode());
			callable.registerOutParameter(2, Types.VARCHAR);
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.execute();

			logger.debug("  message [" + callable.getString(2) + "]"
					+ " Status [" + callable.getString(3) + "] ");

			setMessage(callable.getString(2));
			setStatus(callable.getString(3));

		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String checkTransactionType() {
		logger.debug("Inside CheckTransactionType [" + accounttype + "]");
		AjaxDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			logger.debug("Accounttype [" + accounttype + "]");
			logger.debug("bankAccount     [" + bankAccount + "]");

			requestJSON.put(CevaCommonConstants.ACCOUNT_TYPE, accounttype);
			requestJSON.put("BANK_ACCOUNT", bankAccount);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new AjaxDAO();
			responseDTO = ictAdminDAO.checkTransactionType(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_CHECK_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {
			ictAdminDAO = null;
			errors = null;
		}

		return SUCCESS;
	}

	public String userIdStatus() {
		logger.debug("Inside UserIdStatus... ");
		ArrayList<String> errors = null;
		UserAjaxDAO userDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("User_Id", userId);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			userDAO = new UserAjaxDAO();
			responseDTO = userDAO.checkuserId(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"RESULT_COUNT_DATA");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {
			userDAO = null;
			errors = null;
		}

		return SUCCESS;
	}

	public String checkHudumaSubService() {
		logger.debug("Inside Service [" + hudumaService + "]");
		ArrayList<String> errors = null;
		ServiceMgmtAjaxDAO ajaxDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.SERVICE_CODE, hudumaService);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ajaxDAO = new ServiceMgmtAjaxDAO();
			responseDTO = ajaxDAO.getHudumaSubService(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {
			ajaxDAO = null;
			errors = null;
		}
		return SUCCESS;
	}

	public String switchStatus() {
		logger.debug("Inside SwitchStatus.. ");
		SwitchUIDAO dao = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			dao = new SwitchUIDAO();
			responseDTO = dao.getBankSwitchStatus(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SWITCH_BANK_DATA);
				logger.debug("Response JSON [" + responseJSON + "]");

			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}

			}
		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {
			dao = null;
			errors = null;
		}

		return SUCCESS;

	}

	public String userDetails() {
		logger.debug("Inside UserDetails.. ");
		QueryactiveDirectory qrd = null;
		try {
			responseJSON = new JSONObject();
			rb = ResourceBundle.getBundle("pathinfo_config");
			try {
				
				/*qrd = new QueryactiveDirectory(rb.getString("ldap.username"),
						rb.getString("ldap.password"), rb.getString("ldap.url"));
				qrd.getUserDetails(getUserId());
				qrd.closeDirectoryContext();
				responseJSON.put("USERS_LIST", qrd.getUserInfo());*/
				responseJSON.put("USERS_LIST", JSONObject.fromObject(ServiceRequestClient.getUserProfile(userId)));
				
				
			} catch (Exception e) {
				logger.debug("Exception while fetching records from AD ["
						+ e.getMessage() + "]");
				e.printStackTrace();
			}

		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {

		}

		return SUCCESS;

	}

	public String searchTxnType() {
		logger.debug("Inside SearchTxnType");
		ArrayList<String> errors = null;
		ServiceMgmtAjaxDAO ajaxDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			// requestJSON.put("txn_type", txnType == null ? " " : txnType);
			requestJSON.put("merch_id", txnType == null ? " " : txnType);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ajaxDAO = new ServiceMgmtAjaxDAO();
			responseDTO = ajaxDAO.searchSubService(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.SERVICE_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("Exception is  [" + e.getMessage() + "]");
		} finally {
			ajaxDAO = null;
			errors = null;
		}
		return SUCCESS;
	}

	public String terminalDetailsSearch() {
		logger.debug("Inside terminalDetailsSearch.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		JSONObject county = new JSONObject();
		JSONObject subcounty = new JSONObject();
		JSONObject terminal = new JSONObject();
		requestJSON = new JSONObject();
		try {
			request = ServletActionContext.getRequest();
			connection = DBConnector.getConnection();
			logger.debug("Get Serial no Id::::" + getTerminalSearch());
			queryConst = "{call HudumaPkg.terminalDetailsSearch(?,?,?,?,?)}";

			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getTerminalSearch());
			callable.registerOutParameter(2, OracleTypes.CURSOR);
			callable.setString(3, request.getRemoteAddr() + "");
			callable.setString(
					4,
					request.getSession()
							.getAttribute(CevaCommonConstants.MAKER_ID)
							.toString());
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();
			String msg = callable.getString(5);
			if ("SUCCESS".equals(msg)) {
				requestJSON.put("message", "SUCCESS");
				ResultSet rs = (ResultSet) callable.getObject(2);
				if (rs.next()) {
					terminal.put("TERMINAL_ID", rs.getString(1));
					terminal.put("STORE_NAME", rs.getString(2));
					terminal.put("STATUS", rs.getString(3));
					terminal.put("TERMINAL_MAKE", rs.getString(4));
					terminal.put("MODEL_NO", rs.getString(5));
					terminal.put("SERIAL_NO", rs.getString(6));
					terminal.put("MAKER_DATE", rs.getString(7));
					terminal.put("MERCHANT_NAME", rs.getString(8));
					terminal.put("STORE_ID", rs.getString(9));
					terminal.put("MERCHANT_ID", rs.getString(10));
				}
				rs.close();
				requestJSON.put("terminal", terminal);

			} else {
				requestJSON.put("message", "No Data Found");
			}
			logger.debug("requestJSON : " + requestJSON);
		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	

	public String CheckSearialNumber() {
		logger.debug("Inside SearchUser.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		try {

			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put("serialNumber", serialNumber);
			connection = DBConnector.getConnection();
			queryConst = "{call HudumaPkg.CHECKSERIAL(?,?,?)}";
			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getSerialNumber());
			callable.registerOutParameter(2, Types.VARCHAR);
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.execute();
			logger.debug("  message [" + callable.getString(2) + "]"
					+ " Status [" + callable.getString(3) + "] ");
			setMessage(callable.getString(2));
			setStatus(callable.getString(3));

		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String CheckAssignUsers() {
		logger.debug("Inside Check Assign Users.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;
		try {

			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			logger.debug("Terminalid:" + terminalID + "selectUsers"
					+ selectUsers);
			requestJSON.put("terminalID", terminalID);
			requestJSON.put("selectUsers", selectUsers);

			connection = DBConnector.getConnection();
			queryConst = "{call HudumaPkg.CHECKASSIGNUSERS(?,?,?,?)}";
			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getTerminalID());
			callable.setString(2, getSelectUsers());
			callable.registerOutParameter(3, Types.VARCHAR);
			callable.registerOutParameter(4, Types.VARCHAR);
			callable.execute();
			logger.debug("  message [" + callable.getString(3) + "]"
					+ " Status [" + callable.getString(4) + "] ");
			setMessage(callable.getString(3));
			setStatus(callable.getString(4));
			setUserId(callable.getString(2));

		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String searchRRnDetails() {
		logger.debug("Inside RRN Details Search.. ");
		String queryConst = "";
		CallableStatement callable = null;
		Connection connection = null;

		JSONObject resultJson = new JSONObject();
		requestJSON = new JSONObject();
		try {
			request = ServletActionContext.getRequest();
			connection = DBConnector.getConnection();
			queryConst = "{call HudumaPkg.rrnDetailsSearch(?,?,?,?,?)}";

			logger.debug("QueryConst [" + queryConst + "]");
			callable = connection.prepareCall(queryConst);
			callable.setString(1, getRrnSearch());
			callable.registerOutParameter(2, OracleTypes.CURSOR);
			callable.setString(3, request.getRemoteAddr() + "");
			callable.setString(4,request.getSession().getAttribute(CevaCommonConstants.MAKER_ID).toString());
			callable.registerOutParameter(5, Types.VARCHAR);

			callable.execute();
			String msg = callable.getString(5);
			if ("SUCCESS".equals(msg)) {
				requestJSON.put("message", "SUCCESS");
				ResultSet rs = (ResultSet) callable.getObject(2);
				if (rs.next()) {
					resultJson.put(CevaCommonConstants.RRN, rs.getString(1));
					resultJson.put(CevaCommonConstants.responseCode,
							rs.getString(2));
					resultJson.put("amt", rs.getString(3));
					resultJson.put("mid", rs.getString(4));
					resultJson.put("tid", rs.getString(5));
					resultJson.put("maskedcard", rs.getString(6));
					resultJson.put("authid", rs.getString(7));
					resultJson.put("bankname", rs.getString(8));
					resultJson.put("location", rs.getString(9));
					resultJson.put("txntype", rs.getString(10));
					resultJson.put("posrrn", rs.getString(11));
				}
				rs.close();
				requestJSON.put("RRN", resultJson);

			} else {
				requestJSON.put("message", "No Data Found");
			}
			logger.debug("requestJSON : " + requestJSON);
		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeCallableStatement(callable);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}
	
	
	
	
	public String fetchPrdDetails() {
		logger.debug("Inside Account Creation.. ");
		CallableStatement callable = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rS = null;
		String Qry = "";
		JSONObject json = null;
		String eachrow = "";
		String billerData = "";
		String detQry1 = "";
		
		
		try {
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			connection = DBConnector.getConnection();
			
			/*Qry="select distinct(PRODUCT_ID),PRODUCT_ID from MOB_PRODUCTS_MASTER where INSTITUTE_ID=?";		
			
			pstmt = connection.prepareStatement(Qry);
			pstmt.setString(1,institute);
			rS = pstmt.executeQuery();
			
			json = new JSONObject();
			while (rS.next()) {
				json.put(rS.getString(1), rS.getString(2));
			}
			*/
			
			//json.clear();

			/*Qry = null;
			pstmt.close();
			rS.close();*/
			
			json = new JSONObject();
			detQry1 = "select SUB_PRODUCT_ID,SUB_PRODUCT_ID_DESC from MOB_PRODUCTS_MASTER where PRODUCT_ID=?";
			pstmt = connection.prepareStatement(detQry1);
			pstmt.setString(1, prdid);
			
			rS = pstmt.executeQuery();

			int i = 0;

			while (rS.next()) {
				eachrow = rS.getString(1) + ","+ rS.getString(2);
				if (i == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				i++;
			}
			
			json.put("subprddata",billerData);
			logger.debug("values passed in json object in ajax"+json);
			
			//detMap.put("user_rights", resultJson);
			//responseDTO.setData(detMap);
			
			eachrow="";
			billerData = "";
			pstmt.close();
			rS.close();
			
			
			
			Qry = "SELECT (SELECT SERVICEPACK_NAME FROM MOB_SERVICEPACK_MASTER P WHERE P.SERVICEPACK_CODE=M.SPACK_ID) SPACK_ID,"
					+ "(SELECT SERVICE_NAME from MOB_SERVICE_MASTER S WHERE S.SERVICE_CODE=M.SERVICE_ID) SERVICE_ID,"
					+ "(SELECT KEY_VALUE FROM CONFIG_DATA C WHERE C.KEY_ID=M.FREQUENCY) FREQUENCY,"
					+ "DECODE(CONDITION,'C','COUNT','A','AMOUNT') CONDITION,"
					+ "FROM_VALUE,TO_VALUE FROM MOB_PRODUCTS_SERVICE_MAP M WHERE PRODUCT_ID=?";
			
			pstmt = connection.prepareStatement(Qry);
			pstmt.setString(1, prdid);
			
			rS = pstmt.executeQuery();

			int j = 0;

			while (rS.next()) {
				eachrow = rS.getString(1) + ","
						+ rS.getString(2) + ","
						+ rS.getString(3) + ","
						+ rS.getString(4) + ","
						+ rS.getString(5) + ","
						+ rS.getString(6);
				
				if (j == 0) {
					billerData += eachrow;
				} else {
					billerData += "#" + eachrow;
				}
				j++;
			}
			
			json.put("status",billerData);
			
			setResponseJSON(json);
			
			
			//setLimitsJSON(json);
			//resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);
			//responseDTO.setData(resultMap);
			
		} catch (Exception e) {
			logger.debug(" Got error [" + e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callable);
			Qry = null;
		}
		
		return SUCCESS;
	}
	
	
	
	public String txndetails() {
		
		logger.debug("Inside Transaction Details Fet ching......");
		Connection connection = null;
		HashMap<String, Object> BankDataMap = null;
		JSONObject resultJson = null;

		String rrn = "";

		PreparedStatement userChkPstmt = null;
		ResultSet USerChkRS = null;
		
		String userChkQry = "select TXN_ID,TXN_TIME,TXN_AMOUNT,PAYBILL_SHORTCODE,BILL_REF_NO,MSISDN,POSTING_DATE,STATUS,TXN_TYPE,PAYBILLRESPONSE,INSTITUTE,OPERATOR,CREDIT_AC,DEBIT_AC,CHANNELID,POSTING_RRN,BILLER_TYPE,CUST_ID,LATTITUDE,LONGITUDE from TRAN_LOG where TRANLOG_PK=?";
		//String userChkQry = "select TXN_ID,TXN_TIME,TXN_AMOUNT,PAYBILL_SHORTCODE,BILL_REF_NO,MSISDN,POSTING_DATE,STATUS,TXN_TYPE,PAYBILLRESPONSE,INSTITUTE,OPERATOR,CREDIT_AC,DEBIT_AC,CHANNELID,POSTING_RRN,BILLER_TYPE,CUST_ID,LATTITUDE,LONGITUDE from TRAN_LOG where MSISDN=?";

		try {
			
			requestJSON = new JSONObject();

			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");
			//rrn = requestJSON.getString(CevaCommonConstants.RRN);
			userChkPstmt = connection.prepareStatement(userChkQry);
			userChkPstmt.setString(1, appID);
			USerChkRS = userChkPstmt.executeQuery();

			if (USerChkRS.next()) {
				resultJson.put("TXN_ID", USerChkRS.getString(1));
				resultJson.put("TXN_TIME", USerChkRS.getString(2));
				resultJson.put("TXN_AMOUNT", USerChkRS.getString(3));
				resultJson.put("PAYBILL_SHORTCODE", USerChkRS.getString(4));
				resultJson.put("BILL_REFNUM", USerChkRS.getString(5));
				resultJson.put("MOBILENUMBER", USerChkRS.getString(6));
				resultJson.put("POSTAGEDATE", USerChkRS.getString(7));
				resultJson.put("STATUS", USerChkRS.getString(8));
				resultJson.put("TXNTYPE", USerChkRS.getString(9));
				resultJson.put("PAYBILRESPONCE", USerChkRS.getString(10));
				resultJson.put("INSTITUTE", USerChkRS.getString(11));
				resultJson.put("OPERATOR", USerChkRS.getString(12));
				resultJson.put("CREDIT_AC", USerChkRS.getString(13));
				resultJson.put("DEBIT_AC", USerChkRS.getString(14));
				resultJson.put("CHANNELID", USerChkRS.getString(15));
				resultJson.put("POSTING_RRN", USerChkRS.getString(16));
				resultJson.put("BILLER_TYPE", USerChkRS.getString(17));
				resultJson.put("CUST_ID", USerChkRS.getString(18));
				resultJson.put("LATTITUDE", USerChkRS.getString(19));
				resultJson.put("LONGITUDE", USerChkRS.getString(20));
			}
			requestJSON.put("TRANSACTION_VIEW", resultJson);
			
			logger.debug("inside [requestJSON][txndetails][requestJSON:::"
					+ requestJSON + "]");

		} catch (SQLException e) {

		} finally {
			try {
				if (userChkPstmt != null)
					userChkPstmt.close();
				if (connection != null)
					connection.close();
				if (USerChkRS != null) {
					USerChkRS.close();
				}
			} catch (SQLException se) {

			}
		}
		
		return SUCCESS;
	}
	
	public JSONObject getLimitsJSON() {
		return limitsJSON;
	}

	public void setLimitsJSON(JSONObject limitsJSON) {
		this.limitsJSON = limitsJSON;
	}

	public String getHoffice() {
		return hoffice;
	}

	public void setHoffice(String hoffice) {
		this.hoffice = hoffice;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getFillSelectBox() {
		return fillSelectBox;
	}

	public void setFillSelectBox(String fillSelectBox) {
		this.fillSelectBox = fillSelectBox;
	}

	public String getSelectedSelBox() {
		return selectedSelBox;
	}

	public void setSelectedSelBox(String selectedSelBox) {
		this.selectedSelBox = selectedSelBox;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getFinalCount() {
		return finalCount;
	}

	public void setFinalCount(int finalCount) {
		this.finalCount = finalCount;
	}

	public String getDlNo() {
		return dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public String getDlName() {
		return dlName;
	}

	public void setDlName(String dlName) {
		this.dlName = dlName;
	}

	public String getDlIdnumber() {
		return dlIdnumber;
	}

	public void setDlIdnumber(String dlIdnumber) {
		this.dlIdnumber = dlIdnumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBankIndex() {
		return bankIndex;
	}

	public void setBankIndex(String bankIndex) {
		this.bankIndex = bankIndex;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getHudumaService() {
		return hudumaService;
	}

	public void setHudumaService(String hudumaService) {
		this.hudumaService = hudumaService;
	}

	public String getProcessingCode() {
		return processingCode;
	}

	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTempstatus() {
		return tempstatus;
	}

	public void setTempstatus(String tempstatus) {
		this.tempstatus = tempstatus;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getTerminalSearch() {
		return terminalSearch;
	}

	public void setTerminalSearch(String terminalSearch) {
		this.terminalSearch = terminalSearch;
	}

	public String getFeeCodeSearch() {
		return feeCodeSearch;
	}

	public void setFeeCodeSearch(String feeCodeSearch) {
		this.feeCodeSearch = feeCodeSearch;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(String selectUsers) {
		this.selectUsers = selectUsers;
	}

	public String getRrnSearch() {
		return rrnSearch;
	}

	public void setRrnSearch(String rrnSearch) {
		this.rrnSearch = rrnSearch;
	}
	
	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}


	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}
	
	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getPrdid() {
		return prdid;
	}

	public void setPrdid(String prdid) {
		this.prdid = prdid;
	}
	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getMakerid() {
		return makerid;
	}

	public void setMakerid(String makerid) {
		this.makerid = makerid;
	}

	public String getMultidata() {
		return multidata;
	}

	public void setMultidata(String multidata) {
		this.multidata = multidata;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelco() {
		return telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public String getIsocode() {
		return isocode;
	}

	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}
	
	public String getMvisaid() {
		return mvisaid;
	}

	public void setMvisaid(String mvisaid) {
		this.mvisaid = mvisaid;
	}
	
	
	
	public File getBulkupload() {
		return bulkupload;
	}

	public void setBulkupload(File bulkupload) {
		this.bulkupload = bulkupload;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}


	
}
