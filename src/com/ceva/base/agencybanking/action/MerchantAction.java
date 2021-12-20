package com.ceva.base.agencybanking.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.directory.shared.asn1.ber.tlv.TLV;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.banking.usermgmt.UserManagementFileGeneratorJob;
import com.ceva.base.common.dao.MerchantDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.EncryptTransactionPin;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author SURESH
 *
 */
public class MerchantAction extends ActionSupport implements
		ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(MerchantAction.class);

	String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	JSONObject terminalJSON = null;
	JSONObject userJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	protected HttpServletRequest request;

	static char[] hexval = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			                 'A', 'B', 'C', 'D', 'E', 'F' };

	private String merchantName;
	private String merchantID;
	private String storeId;
	private String storeName;
	private String location;
	private String kraPin;
	private String merchantType;
	private String managerName;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String poBoxNumber;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String mobileNumber;
	private String faxNumber;
	private String prmContactPerson;
	private String prmContactNumber;
	private String bankMultiData;
	private String bankAcctMultiData;
	private String documentMultiData;
	private String terminalMakeMultiData;
	private String terminalID;
	private String terminalUsage;
	private String terminalMake;
	private String modelNumber;
	private String serialNumber;
	private String pinEntry;
	private String validFrom;
	private String validThru;
	private String status;
	private String terminalDate;
	private String selectUsers;
	private String memberType;
	private String tpkIndex;
	private String tpkKey;
	private String selectedServices;
	private String tillId;
	private String agentMultiData;
	private String locationVal;
	private String merchantTypeVal;
	private String supervisor;
	private String admin;
	private String selectedUserText;
	private String terminalSam;
	private String area;
	private String postalCode;
	private String lrNumber;
	private String country;
	private String merchantAdminId;
	private String headerData;
	private String userName;
	private String userStatus;
	private String employeeNo;
	private String emailId;
	private String mrcode;
	private String locationcity;
	private String county;
	private String storeData;
	private String contactInfoMultidata;
	private String relationShipManagerNumber;
	private String relationShipManagerName;
	private String relationShipManagerEmail;
	private String latitude;
	private String longitude;
	private String accountNumber;
	private String googleCoOrdinates;
	private String password;
	private String encryptPassword;
	private String otp;
	private String multiData;
	private String entity;
	private String typeuser;
	private String channeldata;
	

	private HttpSession session = null;

	
	public String getMerchantCreatePage() {
		logger.debug("Inside GetMerchantCreatePage.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantCreatePageInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_INFO);
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
			logger.debug("Exception in getMerchantCreatePage [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}

		return result;
	}
	
	private static String getCharForNumber(int i) {
		System.out.println("hh :"+String.valueOf((char)(55+i)));
	  
		return String.valueOf((char)(55+i));
	}
	
	public String insertMerchantDetails() {

		logger.debug("Inside InsertMerchantDetails... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		TLV tlvobj=null;
		String qrString="";
		String[] qrlist = { mrcode, merchantName, merchantType };
		String val="";

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (agentMultiData == null) {
				addActionError("Records Missing.");
				result = "fail";
			} else {
				
				
				
				
				int size = qrlist.length;
				
				        System.out.println("The size of array is: " + size);
				
				        for (int i = 0; i < size; i++) {
				
				            System.out.println("Index[" + i + "] = " + qrlist[i] +" length ="+qrlist[i].trim().length() );
				            if(qrlist[i].trim().length()>9) 
				            {
				            	qrString+= i+""+getCharForNumber(qrlist[i].trim().length())+qrlist[i];
				            }
				            else
				            {
				            	//val=qrlist[i].trim().length();
				            	
				            	qrString+= i+""+qrlist[i].trim().length()+qrlist[i];
				            }
				            
				            //qrString+= i+""+val+qrlist[i]+"--";
				            
				            System.out.println("The string constructed in loop : " + qrString);
				        }
				        
				        System.out.println("The string constructed is : " + qrString+"33KEN"+"43404");
				        ByteArrayOutputStream out = QRCode.from(qrString+"33KEN"+"43404").to(ImageType.PNG).withSize(350, 350).stream();
/*				        FileOutputStream fout = new FileOutputStream(new File("D:/"+System.currentTimeMillis()+".JPG"));*/
				        FileOutputStream fout = new FileOutputStream(new File("D:/"+merchantID+".JPG"));
				        fout.write(out.toByteArray());
			            fout.flush();
			            fout.close();
								
				session = ServletActionContext.getRequest().getSession();
				request = ServletActionContext.getRequest();
				logger.debug("Ip Address::::"+request.getRemoteAddr());
				requestJSON.put(CevaCommonConstants.MAKER_ID, session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON.put(CevaCommonConstants.MERCHANT_NAME, merchantName);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.STORE_NAME, storeName);
				requestJSON.put(CevaCommonConstants.LOCATION_NAME, location);
				requestJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
				requestJSON.put(CevaCommonConstants.MERCHANT_TYPE, merchantType);
				requestJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
				requestJSON.put(CevaCommonConstants.EMAIL_ID, email);
				requestJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
				requestJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
				requestJSON.put(CevaCommonConstants.AGENT_MULTI_DATA, agentMultiData);
if (addressLine3 != null) {requestJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);}
				requestJSON.put(CevaCommonConstants.CITY, city);
				requestJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
				requestJSON.put(CevaCommonConstants.TELEPHONE1, telephoneNumber1);
if (telephoneNumber2 != null) {requestJSON.put(CevaCommonConstants.TELEPHONE2, telephoneNumber2);}
				requestJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
				
				logger.debug( "-*-*-*"+ accountNumber+"*-*-"+agentMultiData);
				
				requestJSON.put("area", area == null ? " " : area);
				requestJSON.put("postal_code", postalCode == null ? " " : postalCode);
				requestJSON.put("lrnumber", lrNumber == null ? " " : lrNumber);
				requestJSON.put("country", country == null ? " " : country);
			    requestJSON.put("accountNumber", accountNumber);
				requestJSON.put("relationShipManagerNumber", relationShipManagerNumber);
				requestJSON.put("relationShipManagerName", relationShipManagerName);
				requestJSON.put("relationShipManagerEmail", relationShipManagerEmail);
				requestJSON.put("latitude", latitude);
				requestJSON.put("longitude", longitude); 	
				requestJSON.put("contactInfoMultidata",contactInfoMultidata);
				requestJSON.put("password",password);
				requestJSON.put("encryptPassword",encryptPassword);
				requestJSON.put("otp",otp);
				
				logger.debug("*-*-OTP"+otp);
				
				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				
				
				/*if (agentMultiData != null) {
				requestJSON.put(CevaCommonConstants.AGENT_MULTI_DATA, agentMultiData);
				}*/
/*					 String mvnum[]=agentMultiData.split(",");
			 	
			 	System.out.println("MVISA NUMBER IS "+mvnum[0]);*/
				 
				
				/*  requestJSON.put("relationShipManager",relationShipManager);
				    requestJSON.put("bankRepresentativeName",bankRepresentativeName);
					requestJSON.put("bankRepresentativeNumber",bankRepresentativeNumber);
					//requestJSON.put("MERCHANT_ADMIN", merchantAdminId);
					//requestJSON.put(CevaCommonConstants.BANK_ACCT_MULTI_DATA, bankAcctMultiData); */
				/*if (documentMultiData != null) {
				requestJSON.put(CevaCommonConstants.DOCUMENT_MULTI_DATA, documentMultiData);
			    }*/
				
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.insertMerchantDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.ENTITY_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DB.");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ENTITY_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertMerchantDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			messages = null;
			merchantDAO = null;
		}

		return result;
	}
	
	public String getMerchantCreateSubmitDetails() {

		logger.debug("Inside GetMerchantCreateSubmitDetails.. ");
		MerchantDAO merchantDAO = null;
		// JSONObject result = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			requestDTO = new RequestDTO();

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantCreatePageInfo(requestDTO);
			synchronized (this) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.MERCHANT_INFO);
				constructToResponseCurrentJson(request, responseJSON);
			}

			logger.debug("ResponseJSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in InsertRegionDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			merchantDAO = null;
		}
		return result;
	}

	public String StoreCreateSubmittedDetails() {

		logger.debug("Inside StoreCreateSubmittedDetails.. ");
		MerchantDAO merchantDAO = null;
		try {
			// responseJSON = new JSONObject();
			requestDTO = new RequestDTO();

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantBackInfo(requestDTO);
			responseJSON = (JSONObject) responseDTO.getData().get(
					CevaCommonConstants.MERCHANT_INFO);

			session = ServletActionContext.getRequest().getSession();

			responseJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? " "
					: session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			responseJSON.put(CevaCommonConstants.MERCHANT_NAME, merchantName);
			responseJSON.put(CevaCommonConstants.STORE_ID, storeId);
			responseJSON.put(CevaCommonConstants.STORE_NAME, storeName);
			responseJSON.put(CevaCommonConstants.LOCATION_NAME, location);
			responseJSON.put(CevaCommonConstants.LOCATION_INFO, locationVal);
			responseJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
			responseJSON.put(CevaCommonConstants.MEMBER_TYPE, memberType);
			responseJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
			responseJSON.put(CevaCommonConstants.EMAIL, email);
			responseJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
			responseJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
			if (addressLine3 != null) {
				responseJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);
			}
			responseJSON.put(CevaCommonConstants.CITY, city);
			responseJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
			responseJSON.put(CevaCommonConstants.TELEPHONE1, telephoneNumber1);
			if (telephoneNumber2 != null) {
				responseJSON.put(CevaCommonConstants.TELEPHONE2,
						telephoneNumber2);
			}
			responseJSON.put(CevaCommonConstants.MOBILE_NUMBER, mobileNumber);
			responseJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
			responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
					prmContactPerson);
			responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
					prmContactNumber);

			responseJSON.put("area", area);
			responseJSON.put("postalcode", postalCode);
			responseJSON.put("lrnumber", lrNumber);
			responseJSON.put("country", country);
			responseJSON.put("merchantAdmin", merchantAdminId);

			responseJSON.put("bankAcctMultiData", bankAcctMultiData);
			if (documentMultiData != null) {
				responseJSON.put("documentMultiData", documentMultiData);
			}
			if (agentMultiData != null) {
				responseJSON.put("AgenctAcctMultiData", agentMultiData);
			}
			logger.debug("ResponseJSON [" + responseJSON + "]");
			result = "success";

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in StoreCreateSubmittedDetails ["
					+ e.getMessage() + "]");
			e.printStackTrace();
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			merchantDAO = null;
		}

		return result;
	}

	

	public String getMerchantDetails() {
		logger.debug("Inside GetMerchantDetails.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
				terminalJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.TERMINAL_DATA);
				logger.debug("Terminal JSON [" + terminalJSON + "]");
				userJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.USER_DATA);
				logger.debug("User JSON [" + userJSON + "]");
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
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getTerminalDetails() {
		logger.debug("Inside GetMerchantDetails.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getTerminalDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.TERMINAL_LIST);
				
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
			logger.debug("Exception in getMerchantDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			merchantDAO = null;
		}

		return result;
	}
	
	public String getMerchantModifySubmitDetails() {

		logger.debug("Inside GetMerchantModifySubmitDetails... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		JSONObject jsonData = null;
		try {
			storeData=ServletActionContext.getRequest().getParameter("storeData");
			logger.debug("storeData..:"+storeData);
			/*
			 * responseJSON = new JSONObject();
			 * responseJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			 * responseJSON.put(CevaCommonConstants.MERCHANT_NAME,
			 * merchantName);
			 * responseJSON.put(CevaCommonConstants.LOCATION_NAME, location);
			 * responseJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
			 * responseJSON.put(CevaCommonConstants.MERCHANT_TYPE,
			 * merchantType); responseJSON.put(CevaCommonConstants.MANAGER_NAME,
			 * managerName); responseJSON.put(CevaCommonConstants.EMAIL, email);
			 * responseJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
			 * responseJSON.put(CevaCommonConstants.ADDRESS2, addressLine2); if
			 * (addressLine3 != null) {
			 * responseJSON.put(CevaCommonConstants.ADDRESS3, addressLine3); }
			 * responseJSON.put(CevaCommonConstants.CITY, city);
			 * responseJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
			 * responseJSON.put(CevaCommonConstants.TELEPHONE1,
			 * telephoneNumber1); if (telephoneNumber2 != null) {
			 * responseJSON.put(CevaCommonConstants.TELEPHONE2,
			 * telephoneNumber2); }
			 * responseJSON.put(CevaCommonConstants.MOBILE_NUMBER,
			 * mobileNumber); responseJSON.put(CevaCommonConstants.FAX_NUMBER,
			 * faxNumber);
			 * responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
			 * prmContactPerson);
			 * responseJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
			 * prmContactNumber);
			 *
			 * responseJSON.put("area", area); responseJSON.put("postal_code",
			 * postalCode); responseJSON.put("lrnumber", lrNumber);
			 * responseJSON.put("country", country);
			 */
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantLocationDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				jsonData = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_INFO);
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
			responseJSON = constructToResponseCurrentJson(request, jsonData);

			logger.debug("Response JSON [" + responseJSON + "]");

			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in GetMerchantModifySubmitDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String getStoreCreatePage() {
		logger.debug("Inside GetStoreCreatePage.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getStoreCreatePageInfo(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				storeId = responseJSON.getString(CevaCommonConstants.STORE_ID);

				storeId = merchantID.substring(0, 4) + "-" + "S"
						+ StringUtils.leftPad(storeId, 3, "0");
				responseJSON.remove(CevaCommonConstants.STORE_ID);
				responseJSON.put(CevaCommonConstants.STORE_ID, storeId);
				responseJSON.put("merchantID", merchantID);

				tillId = getRandomInteger();
				logger.debug("ResponseJSON in action is [" + responseJSON + "]");
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
			logger.debug("Exception in getStoreCreatePage [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getStoreCreateSubmitDetails() {

		logger.debug("Inside GetStoreCreateSubmitDetails....");
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestDTO.setRequestJSON(requestJSON);
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getStoreCreatePageInfo(requestDTO);
			responseJSON = (JSONObject) responseDTO.getData().get(
					CevaCommonConstants.STORE_INFO);
			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getStoreCreatePage [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			merchantDAO = null;
		}

		return result;
	}

	public String insertStoreDetails() {

		  logger.debug("Inside InsertStoreDetails.. ");
		  MerchantDAO merchantDAO = null;
		  ArrayList<String> messages = null;
		  ArrayList<String> errors = null;
		  try {
		   requestJSON = new JSONObject();
		   requestDTO = new RequestDTO();

		   if (storeId == null) {
		    addActionError(" storeId Missing");
		    result = "fail";
		   } else {
		    session = ServletActionContext.getRequest().getSession();
		    request = ServletActionContext.getRequest();
		    requestJSON.put(CevaCommonConstants.MAKER_ID,
		      session.getAttribute(CevaCommonConstants.MAKER_ID));
		    requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
		    requestJSON
		      .put(CevaCommonConstants.MERCHANT_NAME, merchantName);
		    requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
		    requestJSON.put(CevaCommonConstants.STORE_NAME, storeName);
		    requestJSON.put(CevaCommonConstants.LOCATION_NAME, location);
		    requestJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
		    requestJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
		    requestJSON.put(CevaCommonConstants.EMAIL_ID, email);
		    requestJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
		    requestJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
		    if (addressLine3 != null) {
		     requestJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);
		    }
		    requestJSON.put(CevaCommonConstants.CITY, city);
		    requestJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
		    requestJSON.put(CevaCommonConstants.TELEPHONE1,
		      telephoneNumber1);
		    if (telephoneNumber2 != null) {
		     requestJSON.put(CevaCommonConstants.TELEPHONE2,
		       telephoneNumber2);
		    }
		    requestJSON
		      .put(CevaCommonConstants.MOBILE_NUMBER, mobileNumber);
		    requestJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
		    requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
		      prmContactPerson);
		    requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
		      prmContactNumber);
		    requestJSON.put(CevaCommonConstants.TILL_ID,
		      tillId == null ? " " : tillId);

		    requestJSON.put(CevaCommonConstants.BANK_ACCT_MULTI_DATA,
		      bankAcctMultiData);
		    if (documentMultiData != null) {
		     requestJSON.put(CevaCommonConstants.DOCUMENT_MULTI_DATA,
		       documentMultiData);
		    }
		    if (agentMultiData != null) {
		     requestJSON.put(CevaCommonConstants.AGENT_MULTI_DATA,
		       agentMultiData);
		    }

		    requestJSON.put("area", area == null ? " " : area);
		    requestJSON.put("postal_code", postalCode == null ? " "
		      : postalCode);
		    requestJSON.put("lrnumber", lrNumber == null ? " " : lrNumber);
		    requestJSON.put("country", country == null ? " " : country);
		    requestJSON.put("county", county == null ? " " : county);
		    requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
		    logger.debug("Request JSON [" + requestJSON + "]");
		    requestDTO.setRequestJSON(requestJSON);
		    logger.debug("Response DTO [" + requestDTO + "]");
		    merchantDAO = new MerchantDAO();
		    responseDTO = merchantDAO.insertStoreDetails(requestDTO);
		    logger.debug("Response DTO [" + responseDTO + "]");

		    if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		     responseJSON = (JSONObject) responseDTO.getData().get(
		       CevaCommonConstants.MERCHANT_LIST);
		     logger.debug("Response JSON [" + responseJSON + "]");
		     logger.debug("Getting messages from DB.");
		     messages = (ArrayList<String>) responseDTO.getMessages();
		     logger.debug("Messages [" + messages + "]");
		     for (int i = 0; i < messages.size(); i++) {
		      addActionMessage(messages.get(i));
		     }
		     result = "success";
		    } else {
		     logger.debug("Getting error from DB.");

		     errors = (ArrayList<String>) responseDTO.getErrors();
		     logger.debug("Errors [" + errors + "]");
		     for (int i = 0; i < errors.size(); i++) {
		      addActionError(errors.get(i));
		     }
		     result = "fail";
		    }
		   }
		   logger.debug("Result [" + result + "]");

		  } catch (Exception e) {
		   result = "fail";
		   logger.debug("Exception in insertStoreDetails [" + e.getMessage()
		     + "]");
		   addActionError("Internal error occured.");
		  } finally {
		   requestDTO = null;
		   responseDTO = null;
		   requestJSON = null;

		   errors = null;
		   messages = null;
		   merchantDAO = null;
		  }

		  return result;

		 }
	/*public String insertStoreDetails() {

		logger.debug("Inside InsertStoreDetails.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (storeId == null) {
				addActionError(" storeId Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON
						.put(CevaCommonConstants.MERCHANT_NAME, merchantName);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.STORE_NAME, storeName);
				requestJSON.put(CevaCommonConstants.LOCATION_NAME, location);
				logger.debug("[MERCHANTACTION][insertStoreDetails][Location : "+location+"]");
				requestJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
				requestJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
				requestJSON.put(CevaCommonConstants.EMAIL_ID, email);
				requestJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
				requestJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
				if (addressLine3 != null) {
					requestJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);
				}
				requestJSON.put(CevaCommonConstants.CITY, city);
				requestJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
				requestJSON.put(CevaCommonConstants.TELEPHONE1,
						telephoneNumber1);
				if (telephoneNumber2 != null) {
					requestJSON.put(CevaCommonConstants.TELEPHONE2,
							telephoneNumber2);
				}
				requestJSON
						.put(CevaCommonConstants.MOBILE_NUMBER, mobileNumber);
				requestJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
				requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						prmContactPerson);
				requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						prmContactNumber);
				requestJSON.put(CevaCommonConstants.TILL_ID,
						tillId == null ? " " : tillId);

				requestJSON.put(CevaCommonConstants.BANK_ACCT_MULTI_DATA,
						bankAcctMultiData);
				if (documentMultiData != null) {
					requestJSON.put(CevaCommonConstants.DOCUMENT_MULTI_DATA,
							documentMultiData);
				}
				if (agentMultiData != null) {
					requestJSON.put(CevaCommonConstants.AGENT_MULTI_DATA,
							agentMultiData);
				}

				requestJSON.put("area", area == null ? " " : area);
				requestJSON.put("postal_code", postalCode == null ? " "
						: postalCode);
				requestJSON.put("lrnumber", lrNumber == null ? " " : lrNumber);
				requestJSON.put("country", country == null ? " " : country);
				requestJSON.put("county", county == null ? " " : county);

				logger.debug("Request JSON [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Response DTO [" + requestDTO + "]");
				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.insertStoreDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.MERCHANT_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DB.");

					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertStoreDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			messages = null;
			merchantDAO = null;
		}

		return result;

	}*/

	public String insertTerminalDetails() {

		logger.debug("inside insertTerminalDetails.. ");

		MerchantDAO merchantDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;

		try {

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (merchantID == null) {
				addActionError("MerchantID Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				 request = ServletActionContext.getRequest();
				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
				requestJSON.put(CevaCommonConstants.TERMINAL_USAGE,terminalUsage);
				requestJSON.put(CevaCommonConstants.TERMINAL_MAKE, terminalMake);
				requestJSON.put(CevaCommonConstants.MODEL_NO, modelNumber);
				requestJSON.put(CevaCommonConstants.SERIAL_NO, serialNumber);
				requestJSON.put(CevaCommonConstants.PINENTRY,(pinEntry == null || pinEntry.equalsIgnoreCase("")) ? "NO_VAL": pinEntry);
				requestJSON.put(CevaCommonConstants.VALID_FROM, validFrom);
				requestJSON.put(CevaCommonConstants.VALID_THRU, validThru);
				requestJSON.put(CevaCommonConstants.STATUS, status);
				requestJSON.put(CevaCommonConstants.TERMINAL_DATE, terminalDate);
				requestJSON.put(CevaCommonConstants.TMK_INDEX, tpkIndex);
				requestJSON.put(CevaCommonConstants.TPK_KEY, tpkKey);
				requestJSON.put("terminal_sam", terminalSam);
				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				logger.debug("Request JSON [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.insertTerminalDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.MERCHANT_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DB.");

					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertStoreDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			messages = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getMerchantViewDetails() {
		logger.debug("Inside GetMerchantViewDetails... ");
		ArrayList<String> errors = null;
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_INFO);

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
			logger.debug("Exception in insertStoreDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}




	public String merchantTerminate() {
		logger.debug("Inside MerchantTerminate... ");
		ArrayList<String> errors = null;
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			 request = ServletActionContext.getRequest();
			 
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.merchantTerminate(requestDTO);
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
			result = "fail";
			logger.debug("Exception in merchantTerminate [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}

		return result;
	}

	public String getStoreViewDetails() {
		logger.debug("Inside GetStoreViewDetails..  ");
		ArrayList<String> errors = null;
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (merchantID != null) {
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			}
			if (merchantID != null) {
				requestJSON
						.put(CevaCommonConstants.MERCHANT_NAME, merchantName);
			}
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getStoreViewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
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
			logger.debug("Exception in GetStoreViewDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}

		return result;
	}

	public String storeTerminate() {
		logger.debug("Inside StoreTerminate.. ");
		ArrayList<String> errors = null;
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			session = ServletActionContext.getRequest().getSession();
			request = ServletActionContext.getRequest();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.storeTerminate(requestDTO);
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
			result = "fail";
			logger.debug("Exception in storeTerminate [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}

		return result;
	}

	public String getTerminalviewDetails() {

		logger.debug("Inside GetTerminalviewDetails... ");
		ArrayList<String> errors = null;
		MerchantDAO merchantDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getTerminalviewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
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
			logger.debug("Exception in GetTerminalviewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}
		return result;
	}

	public String terminateTerminal() {

		logger.debug("Inside TerminateTerminal... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			session = ServletActionContext.getRequest().getSession();
			request = ServletActionContext.getRequest();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.terminateTerminal(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
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
			logger.debug("Exception in TerminateTerminal [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String modifyTerminal() {

		logger.debug("Inside ModifyTerminal... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (merchantID == null) {
				addActionError(" merchantID Missing");
				result = "fail";
			} else {

				session = ServletActionContext.getRequest().getSession();
				request = ServletActionContext.getRequest();
				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
				requestJSON.put(CevaCommonConstants.TERMINAL_USAGE,terminalUsage);
				requestJSON.put(CevaCommonConstants.TERMINAL_MAKE, terminalMake);
				requestJSON.put(CevaCommonConstants.MODEL_NO, modelNumber);
				requestJSON.put(CevaCommonConstants.SERIAL_NO, serialNumber);
				requestJSON.put(CevaCommonConstants.PINENTRY, "");
				requestJSON.put(CevaCommonConstants.VALID_FROM, "");
				requestJSON.put(CevaCommonConstants.VALID_THRU, "");
				requestJSON.put(CevaCommonConstants.STATUS, status);
				requestJSON.put(CevaCommonConstants.TERMINAL_DATE, terminalDate);
				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.updateTerminalDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.MERCHANT_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DB.");

					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}

			}
			logger.debug("Result [" + result + "]");

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in ModifyTerminal [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			merchantDAO = null;

		}

		return result;
	}

	public String updateStore() {

		logger.debug("Inside UpdateStore... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (storeId == null) {
				addActionError("StoreId Missing.");
				result = "fail";
			} else {
				request = ServletActionContext.getRequest();
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON
						.put(CevaCommonConstants.MERCHANT_NAME, merchantName);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.STORE_NAME, storeName);
				requestJSON.put(CevaCommonConstants.LOCATION_NAME, location);
				requestJSON.put(CevaCommonConstants.KRA_PIN, kraPin);
				requestJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
				requestJSON.put(CevaCommonConstants.EMAIL_ID, email);
				requestJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
				requestJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
				if (addressLine3 != null) {
					requestJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);
				}
				requestJSON.put(CevaCommonConstants.CITY, city);
				requestJSON.put("county", area);
				requestJSON.put("postal_code", postalCode == null ? " "
						: postalCode);
				requestJSON.put("lrnumber", lrNumber == null ? " " : lrNumber);
				requestJSON.put("country", country == null ? " " : country); 
				requestJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
				requestJSON.put(CevaCommonConstants.TELEPHONE1,
						telephoneNumber1);
				if (telephoneNumber2 != null) {
					requestJSON.put(CevaCommonConstants.TELEPHONE2,
							telephoneNumber2);
				}
				requestJSON
						.put(CevaCommonConstants.MOBILE_NUMBER, mobileNumber);
				requestJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
				requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
						prmContactPerson);
				requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
						prmContactNumber);
				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				logger.debug("RequestJSON  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.updateStoreDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.MERCHANT_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DB");

					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in UpdateStore [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
			merchantDAO = null;

		}
		return result;
	}

	public String getUserstoTerminal() {
		logger.debug("Inside GetUserstoTerminal... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();
			logger.debug("merchantID:"+merchantID+"storeId:"+storeId+"terminalID:"+terminalID);
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getUserstoTerminal(requestDTO);
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
			logger.debug("Exception in getUserstoTerminal [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;

		}

		return result;
	}

	public String assignUserToTerminal() {

		logger.debug(" Inside AssignUserToTerminal... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;

		String fileName = "";
		try {

			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			if (merchantID == null) {
				addActionError("Merchant ID Missing");
				result = "fail";
			} else if (storeId == null) {
				addActionError("Store Id Missing");
				result = "fail";
			} else if (terminalID == null) {
				addActionError("Terminal Id Missing");
				result = "fail";
			} else if (selectUsers == null) {
				addActionError("Selected Users Missing");
				result = "fail";
			} else if (supervisor == null) {
				addActionError("Supervisor Missing");
				result = "fail";
			} /*else if (admin == null) {
				addActionError("Admin Missing");
				result = "fail";
			}*/ else {
				session = ServletActionContext.getRequest().getSession();
				request = ServletActionContext.getRequest();
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID.trim());
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId.trim());
				requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID.trim());
				requestJSON
						.put(CevaCommonConstants.SELECTED_USERS, selectUsers);
				requestJSON.put("SUPERVISOR", supervisor.trim());
				requestJSON.put("ADMIN", (admin != null)?admin.trim():"");
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));

				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				System.out.println("MAKERID IS : "+session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request JSON [" + requestJSON);

				logger.debug("VALUES : "+merchantID.trim()+(storeId).trim()+terminalID.trim()+selectUsers.trim()+supervisor.trim()+admin.trim());

				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.assignUsers(requestDTO);
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					logger.debug("Response DTO [" + responseDTO + "]");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ROLE_DATA);
					logger.debug("Response JSON [" + responseJSON);
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}

					fileName = UserManagementFileGeneratorJob.generateCsvFile();
					logger.debug("FileName [" + fileName + "]");
					result = "success";
				} else {
					logger.debug("Getting error from DAO.");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ROLE_DATA);
					logger.debug("Response JSON [" + responseJSON);
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}

			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in assignUserToTerminal [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
			fileName = null;

		}

		return result;

	}

	public String assignUserterminalSubmittedDetails() {

		logger.debug("Inside AssignUserterminalSubmittedDetails... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			logger.debug("merchantID:"+merchantID+"storeId:"+storeId+"terminalID:"+terminalID);
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getUserstoTerminal(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
				responseJSON.put(CevaCommonConstants.SELECTED_USERS,
						selectUsers);
				requestJSON.put("SUPERVISOR", supervisor);
				requestJSON.put("ADMIN", admin);
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
			logger.debug("Exception in AssignUserterminalSubmittedDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;

		}

		return result;

	}

	public String getAutoGeneratedTerminal() {

		logger.debug("Inside GetAutoGeneratedTerminal.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		String tpkKey = null;

		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getAutoGeneratedTerminal(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				//terminalID = responseJSON.getString(CevaCommonConstants.TERMINAL_ID);
				tpkKey = getRandomValue();
				responseJSON.put(CevaCommonConstants.TPK_KEY, tpkKey);
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
			logger.debug("Exception in GetAutoGeneratedTerminal ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;

			tpkKey = null;
		}

		return result;

	}

	public String getMerchantDashBoard() {

		logger.debug("Inside GetMerchantDashBoard.... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getMerchantDashBoard(requestDTO);
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
			logger.debug("Exception in GetMerchantDashBoard [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getStoreViewDashboardDetails() {
		logger.debug("Inside GetStoreViewDashboardDetails.. ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();

			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getStoreViewDashboardDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
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
			logger.debug("Exception in getStoreViewDashboardDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getTerminalDashboardviewDetails() {

		logger.debug("Inside GetTerminalDashboardviewDetails... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO
					.getTerminalDashboardviewDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.STORE_INFO);
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
			logger.debug("Exception in getTerminalDashboardviewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String getUserstoServices() {

		logger.debug("Inside GetUserstoServices... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getUserstoServices(requestDTO);
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
			logger.debug("Exception in getUserstoServices [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String ServiceTerminalMappingDetails() {

		logger.debug("Inside ServiceTerminalMappingDetails... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.getUserstoServices(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
				responseJSON.put(CevaCommonConstants.SELECTED_SERVICES,
						selectedServices);
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
			logger.debug("Exception in ServiceTerminalMappingDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			merchantDAO = null;
		}

		return result;
	}

	public String assignServiceToTerminal() {

		logger.debug(" Inside AssignServiceToTerminal... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			if (merchantID == null) {
				addActionError(" Merchant ID Missing");
				result = "fail";
			} else if (storeId == null) {
				addActionError("Store Id Missing");
				result = "fail";
			} else if (terminalID == null) {
				addActionError("Terminal Id Missing");
				result = "fail";
			} else if (selectedServices == null) {
				addActionError("Selected Services Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				request = ServletActionContext.getRequest();
				requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
				requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
				requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
				requestJSON.put(CevaCommonConstants.SELECTED_SERVICES,
						selectedServices);
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request JSON [" + requestJSON);

				merchantDAO = new MerchantDAO();
				responseDTO = merchantDAO.assignServices(requestDTO);
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					logger.debug("Response DTO [" + responseDTO + "]");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ROLE_DATA);
					logger.debug("Response JSON [" + responseJSON);
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Getting error from DAO.");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ROLE_DATA);
					logger.debug("Response JSON [" + responseJSON);
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in AssignServiceToTerminal ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;

			merchantDAO = null;
		}

		return result;

	}

	public String assignServiceToTerminalView() {

		logger.debug("Inside AssignServiceToTerminalView... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID, terminalID);
			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.assignServiceToTerminalView(requestDTO);
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
			logger.debug("Exception in AssignServiceToTerminalView ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;

			merchantDAO = null;
		}

		return result;
	}

	public String merchantModify() {

		logger.debug("Inside MerchantModify... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			logger.debug("bank account Details:::"+bankAcctMultiData);
			
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			session = ServletActionContext.getRequest().getSession();
			request = ServletActionContext.getRequest();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.MANAGER_NAME, managerName);
			requestJSON.put(CevaCommonConstants.EMAIL_ID, email);
			requestJSON.put(CevaCommonConstants.ADDRESS1, addressLine1);
			requestJSON.put(CevaCommonConstants.ADDRESS2, addressLine2);
			if (addressLine3 != null) {
				requestJSON.put(CevaCommonConstants.ADDRESS3, addressLine3);
			}
			requestJSON.put(CevaCommonConstants.CITY, city);
			requestJSON.put(CevaCommonConstants.POBOXNUMBER, poBoxNumber);
			requestJSON.put(CevaCommonConstants.TELEPHONE1, telephoneNumber1);

			if (telephoneNumber2 != null) {
				requestJSON.put(CevaCommonConstants.TELEPHONE2,
						telephoneNumber2);
			}

			requestJSON.put(CevaCommonConstants.MOBILE_NUMBER, mobileNumber);
			requestJSON.put(CevaCommonConstants.FAX_NUMBER, faxNumber);
			requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NAME,
					prmContactPerson);
			requestJSON.put(CevaCommonConstants.PRIMARY_CONTACT_NUMBER,
					prmContactNumber);
			requestJSON.put("MERCHANT_ADMIN", merchantAdminId);


			requestJSON.put(CevaCommonConstants.BANK_ACCT_MULTI_DATA,
					bankAcctMultiData);
			if (documentMultiData != null) {
				requestJSON.put(CevaCommonConstants.DOCUMENT_MULTI_DATA,
						documentMultiData);
			}

			requestJSON.put("area", area == null ? " " : area);
			requestJSON.put("postal_code", postalCode == null ? " "
					: postalCode);
			requestJSON.put("lrnumber", lrNumber == null ? " " : lrNumber);
			requestJSON.put("country", country == null ? " " : country);
			requestJSON.put("memberType", memberType == null ? " " : memberType);
			requestJSON.put(CevaCommonConstants.IP,request.getRemoteAddr());
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.merchantModify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.ENTITY_LIST);
				logger.debug("Response JSON [" + responseJSON + "]");
				logger.debug("Getting messages from DB");
				messages = (ArrayList<String>) responseDTO.getMessages();
				logger.debug(" Messages [" + messages + "]");
				for (int i = 0; i < messages.size(); i++) {
					addActionMessage(messages.get(i));
				}
				result = "success";
			} else {
				logger.debug("Getting error from DB.");
				errors = (ArrayList<String>) responseDTO.getErrors();
				logger.debug(" Errors [" + errors + "]");
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				responseJSON = requestJSON;

				result = "fail";
			}

			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in MerchantModify [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;

			merchantDAO = null;
		}

		return result;
	}

	public String merchantAuthorization() {

		logger.debug("Inside merchantAuthorization... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		ResourceBundle rb = null;

		String search = "";
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();
			try {
				rb = ResourceBundle.getBundle("resource/headerdata");
			} catch (Exception e) {
				logger.debug("Exception while loading bundle please check the file.... headerdata under 'resource' folder.");
			}

			requestJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? "NO"
					: session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put(CevaCommonConstants.MERCHANT_ID,
					merchantID == null ? "NO" : merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID,
					storeId == null ? "NO" : storeId);
			requestJSON.put(CevaCommonConstants.TERMINAL_ID,
					terminalID == null ? "NO" : terminalID);
			requestJSON.put(CevaCommonConstants.STATUS, status == null ? "NO"
					: status);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.merchantAuthorization(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);

				if (status.equalsIgnoreCase("MERC")) {
					search = "merchant";
				} else if (status.equalsIgnoreCase("STORE")) {
					search = "store";
				} else {
					search = "terminal";
				}

				logger.debug("Search String is [" + search + "]");

				responseJSON.put("key_data", search);
				responseJSON
						.put("headerData", rb.getString(search + ".header"));
				responseJSON.put("status", status == null ? " " : status);
				responseJSON.put("selectedUserText",
						selectedUserText == null ? " " : selectedUserText);
				responseJSON.put("bankMultiData", bankMultiData == null ? " "
						: bankMultiData);

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
			logger.debug("Exception in merchantAuthorization ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;

			merchantDAO = null;
		}

		return result;
	}

	public String merchantAuthorizationConf() {

		logger.debug("Inside merchantAuthorizationConf... ");
		try {
			responseJSON = constructToResponseJson(request);

			logger.debug("Response JSON [" + responseJSON + "]");
			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in merchantAuthorizationConf ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String merchantAuthorizationBack() {

		logger.debug("Inside merchantAuthorizationBack... ");
		try {
			responseJSON = constructToResponseJson(request);

			logger.debug("Response JSON [" + responseJSON + "]");

			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in merchantAuthorizationBack ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String merchantAuthorizationAck() {

		logger.debug("Inside merchantAuthorizationAck... ");
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			session = ServletActionContext.getRequest().getSession();

			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? "NO"
					: session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestJSON.put(CevaCommonConstants.ID,
					selectedUserText == null ? "NO" : selectedUserText);

			requestJSON.put(CevaCommonConstants.STATUS, status == null ? "NO"
					: status);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");

			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.merchantAuthorizationAck(requestDTO);
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
			logger.debug("Exception in merchantAuthorizationAck ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;

			merchantDAO = null;
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

	private JSONObject constructToResponseCurrentJson(
			HttpServletRequest httpRequest, JSONObject jsonObject) {

		Enumeration<?> enumParams = null;
		logger.debug("Inside ConstructToResponseJson... ");
		String key = "";
		String val = "";

		try {
			enumParams = httpRequest.getParameterNames();
			if (jsonObject == null) {
				jsonObject = new JSONObject();
			} else {
				while (enumParams.hasMoreElements()) {
					key = (String) enumParams.nextElement();
					val = httpRequest.getParameter(key);
					jsonObject.put(key, val);
				}
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

	public static String getRandomValue() {
		Random random = null;
		StringBuilder sb = null;
		try {
			random = new Random();
			sb = new StringBuilder();
			for (int i = 0; i < 32; i++) {
				sb.append(hexval[random.nextInt(15)]);
			}
		} catch (Exception e) {
			return " ";
		}

		return sb.toString();

	}

	private static String getRandomInteger() {
		int aStart = 0;
		int aEnd = 0;
		Random aRandom = null;
		long range = 0;
		long fraction = 0;
		Long randomNumber = 0L;
		try {
			aStart = 100000;
			aEnd = 999999;
			aRandom = new Random();
			if (aStart > aEnd) {
				throw new IllegalArgumentException("Start cannot exceed End.");
			}
			range = (long) aEnd - (long) aStart + 1;
			fraction = (long) (range * aRandom.nextDouble());
			randomNumber = (Long) (fraction + aStart);

		} catch (Exception e) {
			randomNumber = 0L;
		}

		return randomNumber.toString();

	}

/*	public String merchatUserCreateInfo() {
		
		logger.debug("Inside MerchatUser CreateInfo.. ");
		logger.debug("Merchant Id Cuming" + merchantID);
		return SUCCESS;
		
	};*/
	
	
	public String merchatUserCreateInfo() {
		logger.debug("Inside MerchatUser CreateInfo.. ");
		
		MerchantDAO merchantDAO = null;
		ArrayList<String> errors = null;
		try {
			
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
			requestJSON.put(CevaCommonConstants.STORE_ID, storeId);
			requestJSON.put(CevaCommonConstants.USER_LEVEL, typeuser);
			
			logger.debug("Merchant ID while calling action ["+merchantID+" ]Store ID ["+storeId+"]");
			
			requestJSON.put(CevaCommonConstants.MAKER_ID, session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			merchantDAO = new MerchantDAO();
			responseDTO = merchantDAO.fetchMerchantUserInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.MERCHANT_LIST);
				responseJSON.put("storeId", storeId);
				responseJSON.put("typeuser", typeuser);
				logger.debug("Merchant ID ["+merchantID+" ]Store ID ["+storeId+"]");
				responseJSON.put(CevaCommonConstants.MERCHANT_ID, merchantID);
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
			logger.debug("Exception in getMerchantCreatePage [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}

		return result;
	} 
	
	public String merchantCreateCnf(){
		
		result="success";
		logger.debug("MultiData :::: "+multiData);
		return result;
		
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getKraPin() {
		return kraPin;
	}

	public void setKraPin(String kraPin) {
		this.kraPin = kraPin;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPoBoxNumber() {
		return poBoxNumber;
	}

	public void setPoBoxNumber(String poBoxNumber) {
		this.poBoxNumber = poBoxNumber;
	}

	public String getTelephoneNumber1() {
		return telephoneNumber1;
	}

	public void setTelephoneNumber1(String telephoneNumber1) {
		this.telephoneNumber1 = telephoneNumber1;
	}

	public String getTelephoneNumber2() {
		return telephoneNumber2;
	}

	public void setTelephoneNumber2(String telephoneNumber2) {
		this.telephoneNumber2 = telephoneNumber2;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getPrmContactPerson() {
		return prmContactPerson;
	}

	public void setPrmContactPerson(String prmContactPerson) {
		this.prmContactPerson = prmContactPerson;
	}

	public String getPrmContactNumber() {
		return prmContactNumber;
	}

	public void setPrmContactNumber(String prmContactNumber) {
		this.prmContactNumber = prmContactNumber;
	}

	public String getBankMultiData() {
		return bankMultiData;
	}

	public void setBankMultiData(String bankMultiData) {
		this.bankMultiData = bankMultiData;
	}

	public String getBankAcctMultiData() {
		return bankAcctMultiData;
	}

	public void setBankAcctMultiData(String bankAcctMultiData) {
		this.bankAcctMultiData = bankAcctMultiData;
	}

	public String getDocumentMultiData() {
		return documentMultiData;
	}

	public void setDocumentMultiData(String documentMultiData) {
		this.documentMultiData = documentMultiData;
	}

	public String getTerminalMakeMultiData() {
		return terminalMakeMultiData;
	}

	public void setTerminalMakeMultiData(String terminalMakeMultiData) {
		this.terminalMakeMultiData = terminalMakeMultiData;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getTerminalUsage() {
		return terminalUsage;
	}

	public void setTerminalUsage(String terminalUsage) {
		this.terminalUsage = terminalUsage;
	}

	public String getTerminalMake() {
		return terminalMake;
	}

	public void setTerminalMake(String terminalMake) {
		this.terminalMake = terminalMake;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPinEntry() {
		return pinEntry;
	}

	public void setPinEntry(String pinEntry) {
		this.pinEntry = pinEntry;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidThru() {
		return validThru;
	}

	public void setValidThru(String validThru) {
		this.validThru = validThru;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTerminalDate() {
		return terminalDate;
	}

	public void setTerminalDate(String terminalDate) {
		this.terminalDate = terminalDate;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public JSONObject getTerminalJSON() {
		return terminalJSON;
	}

	public void setTerminalJSON(JSONObject terminalJSON) {
		this.terminalJSON = terminalJSON;
	}

	public String getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(String selectUsers) {
		this.selectUsers = selectUsers;
	}

	public JSONObject getUserJSON() {
		return userJSON;
	}

	public void setUserJSON(JSONObject userJSON) {
		this.userJSON = userJSON;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getTpkIndex() {
		return tpkIndex;
	}

	public void setTpkIndex(String tpkIndex) {
		this.tpkIndex = tpkIndex;
	}

	public String getTpkKey() {
		return tpkKey;
	}

	public void setTpkKey(String tpkKey) {
		this.tpkKey = tpkKey;
	}
	
	public String getChanneldata() {
		return channeldata;
	}

	public void setChanneldata(String channeldata) {
		this.channeldata = channeldata;
	}

	public String commonScreen() {
		logger.debug("Inside GetCommonScreen...");
		logger.debug("seshu"+contactInfoMultidata+"**-*-"+bankAcctMultiData);
		
		//storeData = storeData.substring(0,storeData.lastIndexOf("#")-1);
	//	logger.debug("storeData..:"+storeData);
		
		logger.debug("merchantTypeVal:::::::::::::::::::::::::::::::::::::::::::::;;:"+merchantTypeVal);
		responseJSON = constructToResponseJson(request);
		System.out.println(responseJSON);

		requestJSON.put("merchanttype", merchantTypeVal);
		logger.debug("responseJSON::"+responseJSON);
		result = "success";
		return result;
	}

public String getMerchantUsers()
{
		logger.debug("Inside getMerchantUsers... ");
		MerchantDAO merchantDAO = new MerchantDAO();
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			responseDTO = merchantDAO.fetchMerchantIds(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				responseJSON = (JSONObject) responseDTO.getData().get("MERCHANT_LIST");
				logger.debug("Response JSON [  ::: " + responseJSON + "]");

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
			logger.debug("Exception in fetchMerchant id's ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			merchantDAO = null;
			errors = null;
		}
		return result;
	}


	  public String merchantCreateAck(){
		 
			logger.debug("inside Merchant CreateAck... ");
			ArrayList<String> errors = null;
			MerchantDAO merchantDAO = new MerchantDAO();
			String fileName=null;
			String[] users=null;
			String[] data=null;
			String apptxt=null;
			String modmultidata=null;
			String cryptedPassword=null;
			String key = "97206B46CE46376894703ECE161F31F2";
			String randomNum = "";
			String utype = "";
			
			try {
				requestJSON =  new JSONObject();
				responseJSON = new JSONObject();
				requestDTO =  new RequestDTO();
				responseDTO = new ResponseDTO();

				if (multiData == null) {
					addActionError(" Records Missing.");
					result = "fail";
				} else {
					
					users=multiData.split("#");
					for (int i=0;i<users.length;i++){
						
						randomNum = getRNum(new Date().getTime());
						logger.debug("Random number [" + randomNum + "]");
						
						try {
							cryptedPassword = EncryptTransactionPin.encrypt(key,randomNum, 'F');
						} catch (Exception e) {}
						apptxt=users[i];
						data=apptxt.split(",");
						System.out.println("user type in action is "+data[13]);
						apptxt=apptxt+","+cryptedPassword+","+randomNum;
						utype=data[13];
						
						System.out.println("apptxt"+apptxt);
						
					}
					modmultidata=apptxt;
					
					System.out.println("outside loop"+modmultidata);

					

					
					session = ServletActionContext.getRequest().getSession();
					requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestJSON.put("merchantID", getMerchantID() == null ? "NO_DATA":  getMerchantID());
					requestJSON.put("ENTITY_ID", getEntity() == null ? "NO_DATA": getEntity());
					//requestJSON.put(CevaCommonConstants.MULTI_DATA, multiData);
					requestJSON.put(CevaCommonConstants.MULTI_DATA, modmultidata);
					
					requestJSON.put(CevaCommonConstants.CATEGORY, channeldata);
					requestJSON.put("GroupType", typeuser);
					requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

					logger.debug("Multi Data : " + multiData);
					logger.debug("typeuser: " + typeuser);

					logger.debug("Request JSON [" + requestJSON + "]");

					requestDTO.setRequestJSON(requestJSON);

					merchantDAO = new MerchantDAO();
					responseDTO = merchantDAO.insertMerchantUser(requestDTO);
					logger.debug("Response DTO [" + responseDTO + "]");
					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						responseJSON = (JSONObject) responseDTO.getData().get("USER_DETAILS");
						logger.debug("Response JSON [" + responseJSON + "]");
						//logger.debug("User type in action[" + responseJSON.get("USER_TYPE") + "]");
						if(utype.equalsIgnoreCase("MU")){
							System.out.println("into action and creationof user");
							fileName = UserManagementFileGeneratorJob.generateCsvFile();
						}
						
						result = "success";
						
						
					} else {
						logger.debug("Getting error from DB.");
						responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.LOCATION_INFO);
						//responseJSON.put(CevaCommonConstants.ENTITY, entity);
						errors = (ArrayList<String>) responseDTO.getErrors();
						logger.debug("Errors [" + errors + "]");
						for (int i = 0; i < errors.size(); i++) {
							addActionError(errors.get(i));
						}
						result = "fail";
					}

				}
				logger.debug("Result [" + result + "]");
			} catch (Exception e) {
				result = "fail";
				addActionError("Internal Error Occured, Please try again.");
				responseJSON.put("error_flag", "error");
				logger.debug("Exception in insertIctAdminDetails ["+ e.getMessage() + "]");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				merchantDAO = null;
				errors = null;
			}

			return result;
		}
	 
	  
	  public String getRNum(long x) {
			long rs = 0, rem;
			String ret = "";
			long n = x;
			for (int i = 0; i < 4; i++) {
				rem = n % 10;
				ret = ret + rem;
				rs = rs * 10 + rem;
				n = (n - rem) / 10;
			}

			return ret;
		}

	public String getSelectedServices() {
		return selectedServices;
	}

	public void setSelectedServices(String selectedServices) {
		this.selectedServices = selectedServices;
	}

	public String getTillId() {
		return tillId;
	}

	public void setTillId(String tillId) {
		this.tillId = tillId;
	}

	public String getAgentMultiData() {
		return agentMultiData;
	}

	public void setAgentMultiData(String agentMultiData) {
		this.agentMultiData = agentMultiData;
	}

	public String getLocationVal() {
		return locationVal;
	}

	public void setLocationVal(String locationVal) {
		this.locationVal = locationVal;
	}

	public String getMerchantTypeVal() {
		return merchantTypeVal;
	}

	public void setMerchantTypeVal(String merchantTypeVal) {
		this.merchantTypeVal = merchantTypeVal;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getSelectedUserText() {
		return selectedUserText;
	}

	public void setSelectedUserText(String selectedUserText) {
		this.selectedUserText = selectedUserText;
	}

	public String getTerminalSam() {
		return terminalSam;
	}

	public void setTerminalSam(String terminalSam) {
		this.terminalSam = terminalSam;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLrNumber() {
		return lrNumber;
	}

	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
	}

	public String getMerchantAdminId() {
		return merchantAdminId;
	}

	public void setMerchantAdminId(String merchantAdminId) {
		this.merchantAdminId = merchantAdminId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeaderData() {
		return headerData;
	}

	public void setHeaderData(String headerData) {
		this.headerData = headerData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMrcode() {
		return mrcode;
	}

	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
	}

	public String getLocationcity() {
		return locationcity;
	}

	public void setLocationcity(String locationcity) {
		this.locationcity = locationcity;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStoreData() {
		return storeData;
	}

	public void setStoreData(String storeData) {
		this.storeData = storeData;
	}

	public String getContactInfoMultidata() {
		return contactInfoMultidata;
	}

	public void setContactInfoMultidata(String contactInfoMultidata) {
		this.contactInfoMultidata = contactInfoMultidata;
	}

	public String getRelationShipManagerNumber() {
		return relationShipManagerNumber;
	}

	public void setRelationShipManagerNumber(String relationShipManagerNumber) {
		this.relationShipManagerNumber = relationShipManagerNumber;
	}

	public String getRelationShipManagerName() {
		return relationShipManagerName;
	}

	public void setRelationShipManagerName(String relationShipManagerName) {
		this.relationShipManagerName = relationShipManagerName;
	}

	public String getRelationShipManagerEmail() {
		return relationShipManagerEmail;
	}

	public void setRelationShipManagerEmail(String relationShipManagerEmail) {
		this.relationShipManagerEmail = relationShipManagerEmail;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getGoogleCoOrdinates() {
		return googleCoOrdinates;
	}

	public void setGoogleCoOrdinates(String googleCoOrdinates) {
		this.googleCoOrdinates = googleCoOrdinates;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getMultiData() {
		return multiData;
	}

	public void setMultiData(String multiData) {
		this.multiData = multiData;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getTypeuser() {
		return typeuser;
	}

	public void setTypeuser(String typeuser) {
		this.typeuser = typeuser;
	}
	
	
}
