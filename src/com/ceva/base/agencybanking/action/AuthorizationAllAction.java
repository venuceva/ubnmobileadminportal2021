package com.ceva.base.agencybanking.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.banking.usermgmt.UserManagementFileGeneratorJob;
import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.dao.AuthorizationAllDAO;
import com.ceva.base.common.dao.ServiceManagementDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class AuthorizationAllAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AuthorizationAllAction.class);

	private HttpServletRequest request = null;

	private HttpSession session = null;


	private String result;
	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;
	private RequestDTO requestDTO = null;
	private ResponseDTO responseDTO = null;

	private String AUTH_CODE;
	private String userInfoPage=null;
	private String authresult=null;

	private String STATUS;
	private String REF_NO;
	private String DECISION;
	private String formName;
	private String MID;
	private String remark;
	private String acttype;

	private String SRCHDATA;
	private String SRCHTRIA;
	


	private InputStream inputStream;
	private String fileName;
	
	private String actiontype;
	private String status;


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getActiontype() {
		return actiontype;
	}


	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	
	ResourceBundle bundle = null;
	

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	
	public String UamChecker() {
		logger.debug("Inside AuthorizationList.. ");
		ArrayList<String> errors = null;
		AuthorizationAllDAO authDAO = null;
		responseJSON = new JSONObject();
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			session = ServletActionContext.getRequest().getSession();
			//System.out.println("maker id"+session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("usertype",session.getAttribute("usertype"));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.UamChecker(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				JSONObject jres = new JSONObject();
				jres = (JSONObject) responseDTO.getData().get("AUTH_PENDING_LIST");
				responseJSON.put("AUTH_PENDING_LIST", jres);
				responseJSON.put("acttype", acttype);
				

				logger.debug("Response JSON123 [" + responseJSON + "]");
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
			e.printStackTrace();
			logger.debug("Exception in getAuthorizationList [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}
	
	

	
	
	
	public String CommonAuthListuam() {

		logger.debug("Inside CommonAuthList... ");
		AuthorizationAllDAO authDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();


			try {
				rb = ResourceBundle.getBundle("resource/headerdata");
			} catch (Exception e) {
				logger.debug("Exception while loading bundle please check the file.... headerdata under 'classes/resource' folder.");
			}
			
			
			/*System.out.println("kailash here :: "+actiontype);
			System.out.println("kailash here :: "+status);*/
			if(status.equalsIgnoreCase("PENDING")){
				status="P";
			}
			if(status.equalsIgnoreCase("AUTHORIZED")){
				status="C";
			}
			if(status.equalsIgnoreCase("REJECTED")){
				status="R";
			}
			if(status.equalsIgnoreCase("DELETED")){
				status="D";
			}
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put("auth_code", actiontype);
			requestJSON.put("status", status);
			requestJSON.put("acttype", acttype);
			requestJSON.put("usertype",session.getAttribute("usertype"));
			logger.debug("searchdata ["+SRCHDATA+" ] srchtria ["+SRCHTRIA+"]");
			if (SRCHDATA==null)
			{
				logger.debug(" NUll Value in search data");
				SRCHDATA=" ";
			}
			if (SRCHTRIA==null)
			{
				logger.debug(" NUll Value in search creteria");
				SRCHTRIA=" ";
			}
			requestJSON.put("srchdata", SRCHDATA);
			requestJSON.put("srchtria", SRCHTRIA);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.CommonAuthListuam(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);

				logger.debug("Search String is [" + acttype + "]");

				responseJSON.put("key_data", acttype);
				responseJSON.put("headerData", rb.getString(actiontype + ".header"));
				responseJSON.put("search", acttype == null ? " " : acttype);
				responseJSON.put("acttype", acttype);
				logger.debug("Authorization List Type"+ acttype);


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
			logger.debug("Exception in GetFeeDashBoard [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}
	public String AuthorizationList() {
		logger.debug("Inside AuthorizationList.. ");
		ArrayList<String> errors = null;
		AuthorizationAllDAO authDAO = null;
		responseJSON = new JSONObject();
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			session = ServletActionContext.getRequest().getSession();
			//System.out.println("maker id"+session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.getAuthorizationList(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				JSONObject jres = new JSONObject();
				jres = (JSONObject) responseDTO.getData().get("AUTH_PENDING_LIST");
				responseJSON.put("AUTH_PENDING_LIST", jres);
				responseJSON.put("acttype", acttype);
				

				logger.debug("Response JSON123 [" + responseJSON + "]");
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
			e.printStackTrace();
			logger.debug("Exception in getAuthorizationList [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}

	public String commonScreen() {
		logger.debug("Inside CommonScreen .. ");
		logger.debug("auth code value  "+AUTH_CODE);
		responseJSON = new JSONObject();
		getResponseJSON().put("auth_code", AUTH_CODE);
		getResponseJSON().put("status", STATUS);
		getResponseJSON().put("acttype", acttype);
		
		
		return SUCCESS;
	}
	
	public String CommonSearchAuth() {

		logger.debug("Inside CommonAuthList... ");
		AuthorizationAllDAO authDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();


			try {
				rb = ResourceBundle.getBundle("resource/headerdata");
			} catch (Exception e) {
				logger.debug("Exception while loading bundle please check the file.... headerdata under 'classes/resource' folder.");
			}
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put("auth_code", AUTH_CODE);
			requestJSON.put("status", STATUS);
			requestJSON.put("acttype", acttype);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.CommonAuthSearch(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);

				logger.debug("Search String is [" + AUTH_CODE + "]");

				
				//responseJSON.put("key_data", AUTH_CODE);
				//responseJSON.put("headerData", rb.getString(AUTH_CODE + ".header"));
				//responseJSON.put("search", AUTH_CODE == null ? " " : AUTH_CODE);
				//responseJSON.put("acttype", acttype);
				logger.debug("Authorization List Type"+ acttype);

				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in GetFeeDashBoard [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}

	public String CommonAuthList() {

		logger.debug("Inside CommonAuthList... ");
		AuthorizationAllDAO authDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();


			try {
				rb = ResourceBundle.getBundle("resource/headerdata");
			} catch (Exception e) {
				logger.debug("Exception while loading bundle please check the file.... headerdata under 'classes/resource' folder.");
			}
			
			
			/*System.out.println("kailash here :: "+actiontype);
			System.out.println("kailash here :: "+status);*/
			if(status.equalsIgnoreCase("PENDING")){
				status="P";
			}
			if(status.equalsIgnoreCase("AUTHORIZED")){
				status="C";
			}
			if(status.equalsIgnoreCase("REJECTED")){
				status="R";
			}
			if(status.equalsIgnoreCase("DELETED")){
				status="D";
			}
			
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestJSON.put("auth_code", actiontype);
			requestJSON.put("status", status);
			requestJSON.put("acttype", acttype);
			requestJSON.put("usertype",session.getAttribute("usertype"));
			logger.debug("searchdata ["+SRCHDATA+" ] srchtria ["+SRCHTRIA+"]");
			if (SRCHDATA==null)
			{
				logger.debug(" NUll Value in search data");
				SRCHDATA=" ";
			}
			if (SRCHTRIA==null)
			{
				logger.debug(" NUll Value in search creteria");
				SRCHTRIA=" ";
			}
			requestJSON.put("srchdata", SRCHDATA);
			requestJSON.put("srchtria", SRCHTRIA);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.CommonAuthList(requestDTO);
			
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.RESPONSE_JSON);

				logger.debug("Search String is [" + acttype + "]");

				responseJSON.put("key_data", acttype);
				responseJSON.put("headerData", rb.getString(actiontype + ".header"));
				responseJSON.put("search", acttype == null ? " " : acttype);
				responseJSON.put("acttype", acttype);
				logger.debug("Authorization List Type"+ acttype);


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
			logger.debug("Exception in GetFeeDashBoard [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
 }

	public String CommonAuthCnf() {

		logger.debug("Inside CommonAuthCnf... ");
		AuthorizationAllDAO authDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			request = ServletActionContext.getRequest();
			requestJSON.put("auth_code", AUTH_CODE);
			requestJSON.put("status", STATUS);
			requestJSON.put("ref_no", REF_NO);
			
			
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("[Class Name][CommonAuthCnf][AUTH CODE : "+AUTH_CODE+"]");

			logger.debug("Request DTO [" + requestDTO + "]");
			authDAO = new AuthorizationAllDAO();
			responseDTO = authDAO.CommonAuthConfirmationBefore(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");



			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
				responseJSON.put("acttype", acttype);
                logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}

			if (AUTH_CODE.equalsIgnoreCase("USERAUTH")) {
				userInfoPage = "createuserauth";
			} else if (AUTH_CODE.equalsIgnoreCase("MODUSERAUTH")) {
				userInfoPage = "createuserauth";
			} else if (AUTH_CODE.equalsIgnoreCase("View")) {
				userInfoPage = "userViewInformation";
			} else if (AUTH_CODE.equalsIgnoreCase("ActiveDeactive")) {
				userInfoPage = "userActivate";
			} else if (AUTH_CODE.equalsIgnoreCase("FEEAUTH") && (STATUS.equalsIgnoreCase("R"))) {
				userInfoPage = "FeeCodeRejectDetailsAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("FEEAUTH")) {
				userInfoPage = "viewFeeDetailsAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("SERVICEAUTH") && (STATUS.equalsIgnoreCase("R"))) {
				userInfoPage = "ServiceRejectAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("SERVICEAUTH")) {
				userInfoPage = "CreateServiceAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("BINAUTH")){
				userInfoPage = "CreateBinAuth";
				ServiceManagementDAO dao = new ServiceManagementDAO();
				JSONObject obj = (JSONObject)dao.getBankDetails(requestDTO).getData().get("BANK_LIST");
				responseJSON.put("BANK_LIST",obj.get("BANK_LIST"));
			} else if (AUTH_CODE.equalsIgnoreCase("SUBSEAUTH")  && (STATUS.equalsIgnoreCase("R"))) {
				userInfoPage = "SubServiceRejectRecord";
			} else if (AUTH_CODE.equalsIgnoreCase("SUBSEAUTH")) {
				userInfoPage = "CreateSubServiceAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("TERMAUTH")) {
				userInfoPage = "viewTerminalDetailsAuth";
			} /*else if (AUTH_CODE.equalsIgnoreCase("STOREAUTH")) {
				userInfoPage = "viewStoreDetailsAuth";
			}*/ else if (AUTH_CODE.equalsIgnoreCase("MERCHAUTH")) {
				userInfoPage = "viewMerchantDetailsAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("USERSTATUSAUTH")) {
				userInfoPage = "UserActiveDeactiveAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("TMMAUTH")) {
				userInfoPage = "TerminalMigrationauth";
			} else if (AUTH_CODE.equalsIgnoreCase("MERMODAUTH")) {
				userInfoPage = "MerchantModifyAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("TERMSTATUSAUTH")) {
				userInfoPage = "TerminalStatusAuth";
			} /*else if (AUTH_CODE.equalsIgnoreCase("STORESTATUSAUTH")) {
				userInfoPage = "StoreStatusauth";
			} */else if (AUTH_CODE.equalsIgnoreCase("MERCHSTATUSAUTH")) {
				userInfoPage = "MerchantStatusauth";
			} else if (AUTH_CODE.equalsIgnoreCase("ASSNTERMAUTH")) {
				userInfoPage = "AssignTerminalServiceAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("TERMMODAUTH")) {
				userInfoPage = "TerminalModificationAuth";
			} /*else if (AUTH_CODE.equalsIgnoreCase("STOREMODAUTH")) {
				userInfoPage = "StoreModificationAuth";
			}*/ else if (AUTH_CODE.equalsIgnoreCase("FEECODEMODAUTH")) {
				userInfoPage = "modifyFeeDetailsAuth";
			}else if (AUTH_CODE.equalsIgnoreCase("MPESATYPEAUTH")) {
				userInfoPage = "billerTypeAuth";
			} else if (AUTH_CODE.equalsIgnoreCase("MPESAMODACDEAUTH")) {
			    userInfoPage = "modifyBillerTypeActiveDeactive";
			} else if (AUTH_CODE.equalsIgnoreCase("MPESAMODBIDACDEAUTH")) {
				userInfoPage = "modifyBillerIdActiveDeactive";
		    } else if (AUTH_CODE.equalsIgnoreCase("MPESABTSTATUSAUTH")) {
				userInfoPage = "BillerTypeStatus";
		    } else if (AUTH_CODE.equalsIgnoreCase("MPESABIDSTATUSAUTH")) {
				userInfoPage = "BillerIDStatus";
		    } else if (AUTH_CODE.equalsIgnoreCase("NEWACCAUTH")) {
				userInfoPage = "AccRegStatus";
		    }else if (AUTH_CODE.equalsIgnoreCase("WNEWACCAUTH")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("AGNTAUTH")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("AGNTBLOCK")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("AGNTMODIFY")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("AGNTPRDUPDATE")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("AGNTSTATUSAUTH")) {
				userInfoPage = "WalletRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("TUPWATAUTH")) {
				userInfoPage = "AccRegStatus";
		    }else if (AUTH_CODE.equalsIgnoreCase("ACCACTDCT")) {
				userInfoPage = "AccStatusAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WACCACTDCT")) {
				userInfoPage = "AccStatusAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("ACCTPINRESET")) {
				userInfoPage = "PinAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WACCTPINRESET")) {
				userInfoPage = "PinAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("MODCUSTDETAUTH")) {
				userInfoPage = "ModcustAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WMODCUSTDETAUTH")) {
				userInfoPage = "ModcustAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("DELACCAUTH")) {
				userInfoPage = "DelAccAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WDELACCAUTH")) {
				userInfoPage = "DelAccAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("ENLDSBCUST")) {
				userInfoPage = "PinAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WENLDSBCUST")) {
				userInfoPage = "PinAuthAck";
		    } else if (AUTH_CODE.equalsIgnoreCase("MPESAADDBIDAUTH")) { 
				userInfoPage = "AddBillerIDAuth";
		    } else if (AUTH_CODE.equalsIgnoreCase("PRODUCTCREAUTH")) {
				userInfoPage = "ProductCreateAuth";
		    } else if (AUTH_CODE.equalsIgnoreCase("MPESAMODBIDSTATUSAUTH")) {
				userInfoPage = "BillerIDStatus";
		    }else if (AUTH_CODE.equalsIgnoreCase("BULKREGAUTH")) {
				userInfoPage = "BulkRegistrationAuth";
		    } else if (AUTH_CODE.equalsIgnoreCase("NEWWATAUTH")) {
				userInfoPage = "WalletAccRegStatus";
		    } else if (AUTH_CODE.equalsIgnoreCase("MERNEWAUTH")) {
				userInfoPage = "MerchantCreateAuth";
		    }else if (AUTH_CODE.equalsIgnoreCase("MERSTSAUTH")) {
				userInfoPage = "MerchantStatus";
		    }else if (AUTH_CODE.equalsIgnoreCase("ADDNEWACC")) {
				userInfoPage = "AddNewAccAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("DEVICEPLMNT")) {
				userInfoPage = "DeviceReplaceAccAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("LMTCUSTDETAUTH")) {
				userInfoPage = "LimitcustAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("SUPERAGENTAUTH")) {
				userInfoPage = "SuperAgentCreateAuth";
		    }else if (AUTH_CODE.equalsIgnoreCase("SUPERAGENTSTATUSAUTH")) {
				userInfoPage = "SuperAgentStatus";
		    }else if (AUTH_CODE.equalsIgnoreCase("WPASSWORDRESET")) {
				userInfoPage = "WpasswordAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("WUSSDENB")) {
				userInfoPage = "UssdEnb";
		    }else if (AUTH_CODE.equalsIgnoreCase("WMOBILEENB")) {
				userInfoPage = "MobileEnb";
		    }else if (AUTH_CODE.equalsIgnoreCase("MUSSDENB")) {
				userInfoPage = "UssdEnb";
		    }else if (AUTH_CODE.equalsIgnoreCase("MMOBILEENB")) {
				userInfoPage = "MobileEnb";
		    }else if (AUTH_CODE.equalsIgnoreCase("POSAUTH")) {
				userInfoPage = "POSAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("POSMODIFYAUTH")) {
				userInfoPage = "POSAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("POSSTATUSAUTH")) {
				userInfoPage = "POSAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("STOREAUTH")) {
				userInfoPage = "STOREAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("STOREMODAUTH")) {
				userInfoPage = "STOREAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("STORESTATUSAUTH")) {
				userInfoPage = "STOREAgentRegAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("TERMINALAUTH")) {
				userInfoPage = "TerminalManagementAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("TERMODIFYAUTH")) {
				userInfoPage = "TerminalManagementAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("TERSTATUSAUTH")) {
				userInfoPage = "TerminalManagementAuthAck";
		    }else if (AUTH_CODE.equalsIgnoreCase("COMMSWREAUT")) {
				userInfoPage = "WalletRegAuthAck";
		    } 
			else {
				userInfoPage = "userPasswordReset";
			}
			logger.debug("userInfoPage  [" + userInfoPage+ "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in CommonAuthCnf [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		}
		finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}

	public String confirmAccepted()
	{
		HttpServletRequest req = ServletActionContext.getRequest();

		Enumeration en=req.getParameterNames();
		responseJSON = new JSONObject();
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=req.getParameter(param);
			logger.debug("[AuthorizationAllAction][confirmAccepted][KEY : "+param+"][ VALUE : "+value+"]");
			responseJSON.put(param, value);
		}

		logger.debug("[AuthorizationAllAction][confirmAccepted][Response JSON : "+responseJSON+"]");

		return "success";
	}

	public String CommonAuthCnfsubmition() {

		logger.debug("Inside CommonAuthCnfsubmition... ");
		AuthorizationAllDAO authDAO = null;
		ArrayList<String> errors = null;

		ResourceBundle rb = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			request = ServletActionContext.getRequest();
			requestJSON.put("auth_code", AUTH_CODE);
			requestJSON.put("status", STATUS);
			requestJSON.put("ref_no", REF_NO);
			requestJSON.put("decs", DECISION);
			requestJSON.put("formName", formName);
			requestJSON.put("remark", remark);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			//requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("requestJSON>>>>>>>>>>>>>>>>>>"+requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			responseDTO = new AuthorizationAllDAO().CommonAuthCnfsubmition(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if("ASSNTERMAUTH".equalsIgnoreCase(AUTH_CODE)){
				UserManagementFileGeneratorJob.generateCsvFile();
			}
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				//responseJSON = (JSONObject) responseDTO.getData().get("user_rights");
				responseJSON = new JSONObject();
				responseJSON.put("formName", formName);
				responseJSON.put("acttype", acttype);
                logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";

			}

			if (DECISION.equalsIgnoreCase("A")) {

				authresult = "commonAuthAck";
			}

			else if (DECISION.equalsIgnoreCase("R")) {

				authresult = "coommonrejectACK";
			}
			
			else if (DECISION.equalsIgnoreCase("D")) {

				authresult = "coommonDeleteACK";
			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in CommonAuthCnf [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			authDAO = null;
		}

		return result;
	}
	
	
	public String downfile() {
		logger.debug("Inside Fetch Customer Details .. ");
		ArrayList<String> errors = null;
		String sourcepath=null;
		
		try {
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			bundle = ResourceBundle.getBundle("pathinfo_config");
			sourcepath = bundle.getString("BULK_FILEUPLOAD_PATH");
			
			System.out.println(fileName);	
			
//			File fileToDownload = new File("E:/Download/struts-2.3.12-src.zip");
			File fileToDownload = new File(sourcepath+fileName+".csv");
			System.out.println("path value in action"+sourcepath+fileName+".csv");
			inputStream = new FileInputStream(fileToDownload);
	        fileName = fileToDownload.getName();
	         
	        return SUCCESS;
			
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
	
	
	
	
	
	public String getSRCHDATA() {
		return SRCHDATA;
	}

	public void setSRCHDATA(String sRCHDATA) {
		SRCHDATA = sRCHDATA;
	}

	public String getSRCHTRIA() {
		return SRCHTRIA;
	}

	public void setSRCHTRIA(String sRCHTRIA) {
		SRCHTRIA = sRCHTRIA;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public JSONObject getRequestJSON() {
		return requestJSON;
	}

	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
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
	public String getAUTH_CODE() {
		return AUTH_CODE;
	}

	public void setAUTH_CODE(String aUTH_CODE) {
		AUTH_CODE = aUTH_CODE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public String getActtype() {
		return acttype;
	}

	public void setActtype(String acttype) {
		this.acttype = acttype;
	}
	
	public String getREF_NO() {
		return REF_NO;
	}

	public void setREF_NO(String rEF_NO) {
		REF_NO = rEF_NO;
	}

	public String getUserInfoPage() {
		return userInfoPage;
	}

	public void setUserInfoPage(String userInfoPage) {
		this.userInfoPage = userInfoPage;
	}

	public String getDECISION() {
		return DECISION;
	}

	public void setDECISION(String dECISION) {
		DECISION = dECISION;
	}

	public String getAuthresult() {
		return authresult;
	}

	public void setAuthresult(String authresult) {
		this.authresult = authresult;
	}
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}



}
