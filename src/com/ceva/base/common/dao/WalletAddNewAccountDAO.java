package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.aestools.AES;
import com.ceva.aestools.AESSession;
import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.dao.impl.BranchDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.BranchDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

public class WalletAddNewAccountDAO {


	private Logger logger = Logger.getLogger(WalletAddNewAccountDAO.class);

	private ResponseDTO responseDTO = null;
	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;
	ResourceBundle bundle = null;
	BranchDAO branchDAO = null;

	public ResponseDTO fetchRegCustomerDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;
		String custid="";
		String custaccount="";
		String branch="";
		String makerid="";
		String superadm="";
		String accstatus="1";
		String accstatus1="1";
		

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			branchDAO = new BranchDaoImpl();
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			makerid=requestJSON.getString("makerId");
			connection = DBConnector.getConnection();
			
			superadm=resultJson.getString("supercriteria");
			
			detQry="select count(*) from AGENT_CUSTOMER_MASTER where ACCOUNT_NO=?";
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("accountno"));
			detRS = detPstmt.executeQuery();
			if (detRS.next()) {
				accstatus=detRS.getString(1);
			}
			
			if(accstatus.equalsIgnoreCase("0")) {
				
				detPstmt1 = connection.prepareStatement("select count(*) from AGENT_CUSTOMER_MASTER_TEMP ACMT,auth_pending AP where  AP.STATUS='P' and ACCOUNT_NO=? and AUTH_CODE='AGNTAUTH' and ACMT.REF_NUM=AP.REF_NUM");
				detPstmt1.setString(1, resultJson.getString("accountno"));
				detRS2 = detPstmt1.executeQuery();
				if (detRS2.next()) {
						accstatus1=detRS2.getString(1);
				}
						
					if(accstatus1.equalsIgnoreCase("0")) {
					
						try{
							
							if(superadm.equalsIgnoreCase("AIRTEL")){
								
							
							
						JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(resultJson.getString("accountno")));
						
						if((json1.getString("custID")).equalsIgnoreCase("")){
							responseDTO.addError(resultJson.getString("accountno")+" Account Number not Valid. ");
						}else{
						/*setAccname(json1.getString("accountName"));
						setService(json1.getString("currencyCode"));
						setEmail("");
						setMobile(json1.getString("phone"));
						setCustcode(json1.getString("branchName"));
						setBrcode(json1.getString("branchCode"));*/
						String mobileno="";
						
						if((json1.getString("phone")).startsWith("0")){
							mobileno="234"+(json1.getString("phone")).substring(1);
						}else{
							mobileno="234"+json1.getString("phone");
						}
						
						
						if(!mobileno.equalsIgnoreCase("234")){
						
						
								JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.getAirTelkyc(mobileno));
								
								
								if((json2.get("respcode")).equals("00")){
								
									
									accBean = new AccountBean();
									
									accBean.setCustomercode(custid);
									accBean.setAccountno(resultJson.getString("accountno"));
									accBean.setBranchcode(json1.getString("branchCode"));
									accBean.setFullname((json1.get("accountName")).toString());
									
									if(json2.containsKey("gender")){
										accBean.setGender((json2.get("gender")).toString());
									}else{
										accBean.setGender("");
									}
									
									//accBean.setGender((json2.get("gender")).toString());
									accBean.setTelephone(mobileno);
									
									if(json2.containsKey("identification_type")){
										accBean.setIdnumber((json2.get("identification_type")).toString());
									}else{
										accBean.setIdnumber("");
									}
									//accBean.setIdnumber((json2.get("identification_type")).toString());
									//accBean.setIdnumber("");
									
									if(json2.containsKey("dob")){
										accBean.setDob((json2.get("dob")).toString());
									}else{
										accBean.setDob("");
									}
									
									if(json2.containsKey("email")){
										accBean.setEmail((json2.get("email")).toString());
									}else{
										accBean.setEmail("");
									}
									
									if(json2.containsKey("address")){
										accBean.setAddressLine((json2.get("address")).toString());
									}else{
										accBean.setAddressLine("");
									}
									
									if(json2.containsKey("nationality")){
										accBean.setNationality((json2.get("nationality")).toString());
									}else{
										accBean.setNationality("");
									}
									
									if(json2.containsKey("residential_lga")){
										accBean.setLocalGovernment((json2.get("residential_lga")).toString());
									}else{
										accBean.setLocalGovernment("");
									}
									
									if(json2.containsKey("residential_state")){
										accBean.setState((json2.get("residential_state")).toString());
									}else{
										accBean.setState("");
									}
									
									//accBean.setDob((json2.get("dob")).toString());
									accBean.setLangugae("");
									//accBean.setEmail((json2.get("email")).toString());
									//accBean.setAddressLine((json2.get("address")).toString());
									//accBean.setNationality((json2.get("nationality")).toString());
									//accBean.setLocalGovernment((json2.get("residential_lga")).toString());
									//accBean.setState((json2.get("residential_state")).toString());
									accBean.setCountry("Nigeria");
									
									
									accBean.setInstitute("");
									accBean.setAuthDttm("");
									accBean.setProduct("");
									accBean.setProdesc("");
									accBean.setStatus("N");
									
									accBean.setApptype(resultJson.getString("apptype"));
									accBean.setSupercriteria(superadm);
									detMap.put("AccountData", accBean);
									logger.debug("EntityMap [" + detMap + "]");
									responseDTO.setData(detMap);
									
									
									
								}else{
									responseDTO.addError("This Customer Mobile No "+mobileno+" Not Found In Airtel Service. ");
								}
						
						}else{
							responseDTO.addError(resultJson.getString("accountno")+" Account Customer Mobile No Not Found In Core Bank Service. ");
						}
					  }
						
					}
							
							
							if(superadm.equalsIgnoreCase("UNION_BANK_CUSTOMER") || superadm.equalsIgnoreCase("ALEDIN_AGENCY")){
								
								
								
								JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(resultJson.getString("accountno")));
								
								if((json1.getString("custID")).equalsIgnoreCase("")){
									responseDTO.addError(resultJson.getString("accountno")+" Account Number not Valid. ");
								}else{
								
								/*setAccname(json1.getString("accountName"));
								setService(json1.getString("currencyCode"));
								setEmail("");
								setMobile(json1.getString("phone"));
								setCustcode(json1.getString("branchName"));
								setBrcode(json1.getString("branchCode"));*/
								String mobileno="";
								
								if((json1.getString("phone")).startsWith("0")){
									mobileno="234"+(json1.getString("phone")).substring(1);
								}else{
									mobileno="234"+json1.getString("phone");
								}
								
								
								
								
								
								if(!mobileno.equalsIgnoreCase("234")){
								
									
									accBean = new AccountBean();
									
									accBean.setCustomercode(custid);
									accBean.setAccountno(resultJson.getString("accountno"));
									accBean.setBranchcode(json1.getString("branchCode"));
									accBean.setFullname((json1.get("accountName")).toString());
									accBean.setGender("");
									accBean.setTelephone(mobileno);
									accBean.setIdnumber("");
									accBean.setDob("");
									accBean.setLangugae("");
									accBean.setEmail("");
									accBean.setAddressLine((json1.get("strAdd1")).toString()+" "+(json1.get("strAdd2")).toString()+" "+(json1.get("strAdd3")).toString());
									accBean.setNationality("");
									accBean.setLocalGovernment("");
									accBean.setState("");
									accBean.setCountry("Nigeria");
									
									
									accBean.setInstitute("");
									accBean.setAuthDttm("");
									accBean.setProduct("");
									accBean.setProdesc("");
									accBean.setStatus("N");
									
									accBean.setApptype(resultJson.getString("apptype"));
									accBean.setSupercriteria(superadm);
									
									
									detMap.put("AccountData", accBean);
									logger.debug("EntityMap [" + detMap + "]");
									responseDTO.setData(detMap);
									
									
									
								}else{
									responseDTO.addError(resultJson.getString("accountno")+" Account Customer Mobile No Not Found In Core Bank Service. ");
								}
							}
						}
							
						
						}catch(JSONException je){
							responseDTO.addError("Core bank service not working, please contact to support team.");
							je.printStackTrace();
						}
				}else {
					responseDTO.addError("Account Number "+resultJson.getString("accountno")+" Pending for Authorization. ");
				}
			}else {
				responseDTO.addError("Account Number "+resultJson.getString("accountno")+" already registred. ");
			}
			
			

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	public ResponseDTO fetchRegWalletCustomerDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;
		String custid="";
		String custaccount="";
		String branch="";
		String makerid="";
		

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			branchDAO = new BranchDaoImpl();
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			makerid=requestJSON.getString("makerId");
			connection = DBConnector.getConnection();
			
			if((resultJson.getString("srchcriteria")).equalsIgnoreCase("AIRTEL")){
				JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.getAirTelkyc(resultJson.getString("mobileno")));
				if((json2.get("respcode")).equals("00")){
					accBean = new AccountBean();
					accBean.setFullname((json2.get("fname")).toString());
					accBean.setGender((json2.get("gender")).toString());
					accBean.setTelephone("234"+resultJson.getString("mobileno"));
					accBean.setIdnumber((json2.get("identification_type")).toString());
					accBean.setDob((json2.get("dob")).toString());
					accBean.setLangugae("");
					accBean.setEmail((json2.get("email")).toString());
					
					accBean.setApptype(resultJson.getString("apptype"));
					detMap.put("AccountData", accBean);
					logger.debug("EntityMap [" + detMap + "]");
					responseDTO.setData(detMap);
					
				}else{
					responseDTO.addError("This Customer Mobile No 234"+resultJson.getString("mobileno")+" Not Found In Airtel Service. ");
				}
			}
			
			if((resultJson.getString("srchcriteria")).equalsIgnoreCase("UNION_BANK_CUSTOMER")){
				try{
				JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(resultJson.getString("mobileno")));
					accBean = new AccountBean();
					
					String mobileno="";
					
					if((json2.getString("phone")).startsWith("0")){
						mobileno="234"+(json2.getString("phone")).substring(1);
					}else{
						mobileno="234"+json2.getString("phone");
					}
					accBean.setFullname((json2.get("customerName")).toString());
					accBean.setGender("");
					accBean.setTelephone(mobileno);
					accBean.setIdnumber("");
					accBean.setDob("");
					accBean.setLangugae("");
					accBean.setEmail("");
					
					accBean.setApptype(resultJson.getString("apptype"));
					detMap.put("AccountData", accBean);
					logger.debug("EntityMap [" + detMap + "]");
					responseDTO.setData(detMap);
					
				}catch(JSONException je){
					responseDTO.addError("Core bank service not working, please contact  to support team.");
					je.printStackTrace();
				}
			}
			
			if((resultJson.getString("srchcriteria")).equalsIgnoreCase("BVN")){
				JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.bvnInfo(resultJson.getString("mobileno")));
				if((json2.get("respcode")).equals("00")){
					accBean = new AccountBean();
					
					String mobileno="";
					
					if((json2.getString("PhoneNumber")).startsWith("0")){
						mobileno="234"+(json2.getString("PhoneNumber")).substring(1);
					}else{
						mobileno="234"+json2.getString("PhoneNumber");
					}
					accBean.setFullname((json2.get("FirstName")).toString());
					accBean.setGender("");
					accBean.setTelephone(mobileno);
					accBean.setIdnumber("");
					accBean.setDob((json2.get("DateOfBirth")).toString());
					accBean.setLangugae("");
					accBean.setEmail("");
					
					accBean.setApptype(resultJson.getString("apptype"));
					detMap.put("AccountData", accBean);
					logger.debug("EntityMap [" + detMap + "]");
					responseDTO.setData(detMap);
					
				}else{
					responseDTO.addError("This Customer Mobile No 234"+resultJson.getString("mobileno")+" Not Found In Airtel Service. ");
				}
			}
			
			
				//System.out.println("kailash :: "+resultJson.getString("accountno"));
				
			
			
			

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	public ResponseDTO modifyCustomerServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			connection = DBConnector.getConnection();

			 detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID, "
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.PRD_CODE,CM.PRD_DESC "
						+ "FROM WALLET_CUSTOMER_MASTER CM,WALLET_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	
	
	public ResponseDTO topupCustomerServiceDetails(RequestDTO requestDTO) {

		System.out.println(requestDTO.toString()+"Ranjit" );
	            
			HashMap<String, Object> detMap = null;
			String detQry = "";
			String detQry1 = "";
			JSONObject resultJson = null;
			Connection connection = null;
			PreparedStatement detPstmt = null;
			PreparedStatement detPstmt1 = null;
			ResultSet detRS = null;
			ResultSet detRS2 = null;
			ResultSet rs = null;

			AccountBean accBean = null;
			StringBuilder billerData = null;
			StringBuilder eachrow = null;
			boolean flag = false;

			try {
				responseDTO = new ResponseDTO();
				detMap = new HashMap<String, Object>();
				requestJSON = requestDTO.getRequestJSON();

				logger.debug("Request JSON [" + requestJSON + "]");

				resultJson = (JSONObject) requestDTO.getRequestJSON()
						.get("accBean");
				
				System.out.println(resultJson);
				connection = DBConnector.getConnection();
					String appType=resultJson.getString("apptype");
					System.out.println(appType);		
				if(appType.equals("AGENTWALLET")) {
					detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
							+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CA.BALANCE,CA.ACCT_NO,CA.CUST_TYPE "
							+ "FROM AGENT_CUSTOMER_MASTER CM,AGENT_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
							+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
							+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

					}
				else {
					detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
							+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CA.BALANCE,CA.ACCT_NO,CM.W_APP_TYPE "
							+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
							+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
							+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";


				}
				
				
				 			logger.debug("First Query Executed" + detQry);
				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, resultJson.getString("customercode"));
				detRS = detPstmt.executeQuery();
				logger.debug("First Query Executed" + detQry);
				accBean = new AccountBean();

				if (detRS.next()) {
					accBean.setCustomercode(detRS.getString(1));
					accBean.setFullname(detRS.getString(2));
					accBean.setTelco(detRS.getString(3));
					accBean.setIsocode(detRS.getString(4));
					accBean.setTelephone(detRS.getString(5));
					accBean.setIdnumber(detRS.getString(6));
					accBean.setLangugae(detRS.getString(7));
					accBean.setEmail(detRS.getString(8));
					accBean.setInstitute(detRS.getString(9));
					accBean.setAuthDttm(detRS.getString(10));
					accBean.setBalance(detRS.getString(11));
					accBean.setNewAccountData(detRS.getString(12));
					accBean.setApptype(detRS.getString(13));
					flag = true;
				}

				logger.debug("Bean Details   :::: " + accBean);

				
				detMap.put("AccountData", accBean);
				logger.debug("EntityMap [" + detMap + "]");
				responseDTO.setData(detMap);

			} catch (SQLException e) {
				logger.debug("SQLException in Account Fetch Details ["
						+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing. ");
				e.printStackTrace();
			} catch (Exception e) {
				logger.debug("Exception in Account Fetch Detials ["
						+ e.getMessage() + "]");
				responseDTO.addError("Internal Error Occured While Executing.");
				e.printStackTrace();
			} finally {

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				DBUtils.closePreparedStatement(detPstmt);
				DBUtils.closeResultSet(detRS);
				DBUtils.closeConnection(connection);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(detRS2);

				detQry = null;
				eachrow = null;
				billerData = null;
			}

			return responseDTO;

		}
	
	public ResponseDTO refundCustomerServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			connection = DBConnector.getConnection();
			
			String appType=resultJson.getString("apptype");
			if(appType.equals("AGENTWALLET")) {
				

			 detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CA.BALANCE,CA.ACCT_NO,CA.CUST_TYPE "
						+ "FROM AGENT_CUSTOMER_MASTER CM,AGENT_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";
			}
			else
			{
				detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CA.BALANCE,CA.ACCT_NO,CM.W_APP_TYPE "
						+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";


			
			}
			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setBalance(detRS.getString(11));
				accBean.setNewAccountData(detRS.getString(12));
				accBean.setApptype(detRS.getString(13));
				flag = true;
			}
			logger.debug("Bean Details   :::: " + accBean);

			
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	public ResponseDTO modifycustomerinfo(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		String encpin = null;
		String pin = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			pin = CommonUtil.generatePassword(4);
			encpin = aesEncString(pin).trim();
			resultJson = (JSONObject) requestJSON.get("accBean");
			connection = DBConnector.getConnection();
			
			if(resultJson.getString("apptype").equalsIgnoreCase("CUSTWALLET")){
			insQRY = "{call WALLETREGISTRATIONPKG.INSERTWCUSTDETAILSWEB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			
			
			ip = requestJSON.getString(CevaCommonConstants.IP);

			

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("fullname"));
			cstmt.setString(2, "");
			cstmt.setString(3, "234");
			cstmt.setString(4, resultJson.getString("gender")); //resultJson.getString("branchcode")
			cstmt.setString(5, resultJson.getString("email"));
			cstmt.setString(6, resultJson.getString("dob"));
			cstmt.setString(7, "");
			cstmt.setString(8, "");
			cstmt.setString(9, "");			
			
			cstmt.setString(10, "");
			cstmt.setString(11, "");
			cstmt.setString(12, "");
			cstmt.setString(13, "");
			cstmt.setString(14, resultJson.getString("telephone"));			
			cstmt.setString(15,requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(16, encpin);
			cstmt.setString(17, pin);
			cstmt.setString(18, ip);			
			cstmt.setString(19, "");
			cstmt.setString(20, "WEB");
			cstmt.setString(21, "B");
			
			cstmt.registerOutParameter(22, Types.VARCHAR);
			cstmt.executeQuery();
			
			/*System.out.println("kailash here "+cstmt.getString(22));
			responseDTO.addMessages(cstmt.getString(22).split("-")[0]);*/
			if (!(cstmt.getString(22).split("-")[1]).contains("SUCCESS")) {
				responseDTO.addError((cstmt.getString(22)).split("-")[0]);
			}else{
				
				responseDTO.addMessages((cstmt.getString(22)).split("-")[0]);
			}
			
			}
			
			
			if(resultJson.getString("apptype").equalsIgnoreCase("AGENTWALLET")){
				
				

				insQRY = "{call AGENTREGISTRATIONPKG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

				
				ip = requestJSON.getString(CevaCommonConstants.IP);

/*System.out.println("fullname :1: "+resultJson.getString("fullname"));
System.out.println("telco :2: "+resultJson.getString("telco"));
System.out.println("isocode :3: "+resultJson.getString("isocode"));
System.out.println("gender :4: "+resultJson.getString("gender"));
System.out.println("email :5: "+resultJson.getString("email"));
System.out.println("dob :6: "+resultJson.getString("dob"));
System.out.println("IDType :7: "+resultJson.getString("IDType"));
System.out.println("IDNumber :8: "+resultJson.getString("IDNumber"));
System.out.println("addressLine :9: "+resultJson.getString("addressLine"));
System.out.println("nationality :10: "+resultJson.getString("nationality"));
System.out.println("localGovernment :11: "+resultJson.getString("localGovernment"));
System.out.println("state :12: "+resultJson.getString("state"));
System.out.println("country :13: "+resultJson.getString("country"));
System.out.println("telephone :14: "+resultJson.getString("telephone"));

System.out.println("branchcode :15: "+resultJson.getString("branchcode"));
System.out.println("accountno :16: "+resultJson.getString("accountno"));
System.out.println("prodesc :17: "+resultJson.getString("prodesc"));
System.out.println("product :18: "+resultJson.getString("product"));
*/
				
				
				cstmt = connection.prepareCall(insQRY);
				cstmt.setString(1, resultJson.getString("fullname"));
				cstmt.setString(2, resultJson.getString("telco"));
				cstmt.setString(3, resultJson.getString("isocode"));
				cstmt.setString(4, resultJson.getString("gender"));
				cstmt.setString(5, resultJson.getString("email"));
				cstmt.setString(6, resultJson.getString("dob"));
				cstmt.setString(7, resultJson.getString("addressLine"));
				cstmt.setString(8, resultJson.getString("IDType"));			
				cstmt.setString(9, resultJson.getString("idnumber"));
				cstmt.setString(10, resultJson.getString("nationality"));
				cstmt.setString(11, resultJson.getString("localGovernment"));
				cstmt.setString(12, resultJson.getString("state"));
				cstmt.setString(13, resultJson.getString("country"));
				cstmt.setString(14, resultJson.getString("telephone"));			
				cstmt.setString(15,requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(16, encpin);
				cstmt.setString(17, pin);
				cstmt.setString(18, resultJson.getString("branchcode"));			
				cstmt.setString(19, resultJson.getString("accountno"));
				cstmt.setString(20, resultJson.getString("prodesc"));
				cstmt.setString(21, resultJson.getString("product"));
				
				cstmt.setString(22, resultJson.getString("staffid"));
				cstmt.setString(23, resultJson.getString("aledinagency"));
				cstmt.setString(24, resultJson.getString("agencycustid"));
				cstmt.setString(25, resultJson.getString("agencymobileno"));
				
				cstmt.setString(26, resultJson.getString("agbranch"));
				cstmt.setString(27, resultJson.getString("cluster"));
				
				cstmt.registerOutParameter(28, Types.VARCHAR);
				cstmt.executeQuery();

				if (!(cstmt.getString(28).split("-")[1]).contains("SUCCESS")) {
					responseDTO.addError((cstmt.getString(28)).split("-")[0]);
				}else{
					
					responseDTO.addMessages((cstmt.getString(28)).split("-")[0]);
				}
				
				}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO branchdatacaptureAck(RequestDTO requestDTO) {
		Connection connection = null;
		String ip = null;
		PreparedStatement pstmt = null;
		JSONObject resultJson = null;
		
		try {
			
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);

			connection = DBConnector.getConnection();
			
			
			
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement("INSERT INTO RMCODE_TBL(CUSTOMER,RMCODE,MAKER_ID,INTRODUCER) VALUES(?,?,?,?)");
			pstmt.setString(1, resultJson.getString("customercode"));
			pstmt.setString(2, resultJson.getString("langugae"));
			pstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			pstmt.setString(4, resultJson.getString("product"));
			pstmt.executeUpdate();
			connection.commit();
			
			responseDTO.addError("SUCCESS");
			

			

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			pstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO modifyCustInfoAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;


		try {
			
	

			insQRY = "{call WALLETACCOUNTSPKG.MODIFYCUSTDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("customercode"));
			cstmt.setString(2, resultJson.getString("email"));
			cstmt.setString(3, resultJson.getString("idnumber"));
			cstmt.setString(4, resultJson.getString("langugae"));
			cstmt.setString(5, resultJson.getString("telephone"));
			cstmt.setString(6, resultJson.getString("telco"));
			cstmt.setString(7, resultJson.getString("isocode"));
			cstmt.setString(8, resultJson.getString("multiData"));
			cstmt.setString(9,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("product"));
			cstmt.setString(13, resultJson.getString("prodesc"));
			cstmt.executeQuery();

			if (!cstmt.getString(10).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(10));
			}

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO topupCustAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;
		PreparedStatement pstmt =null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;


		try {
			
	
/*			insQRY = "{call WALLETACCOUNTSPKG.TOPUPAMOUNT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
*/			
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			System.out.println(resultJson.toString());
			ip = requestJSON.getString(CevaCommonConstants.IP);
			
			String refnum=""+System.currentTimeMillis();
			System.out.println("Entered is: "+ refnum); 
			connection = DBConnector.getConnection();
			connection.setAutoCommit(false);
			
			/*cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("customercode"));
			cstmt.setString(2, resultJson.getString("email"));
			cstmt.setString(3, resultJson.getString("idnumber"));
			cstmt.setString(4, resultJson.getString("langugae"));
			cstmt.setString(5, resultJson.getString("telephone"));
			cstmt.setString(6, resultJson.getString("telco"));
			cstmt.setString(7, resultJson.getString("isocode"));
			cstmt.setString(8, resultJson.getString("multiData"));
			cstmt.setString(9,
			requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR); 
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("topup"));
			cstmt.setString(13, resultJson.getString("newAccountData"));*/
			
	String Query="insert into WALLET_WEB_TRANSACTIONS (TRANS_REF_NO,CUST_ID,MOBILE_NUMBER,ACCOUNT_NUMBER,TXN_AMOUNT,MAKER_ID,WALLET_TYPE,TXN_DATE,STATUS,REF_NUM,MAKER_DT,TXN_TYPE)	values(?,?,?,?,?,?,?,sysdate,'P',53322,sysdate,'DEPOSIT')";
			
	//String qry="insert into WALLET_WEB_TRANSACTIONS (TRANS_REF_NO,CUST_ID,MOBILE_NUMBER,ACCOUNT_NUMBER,TXN_AMOUNT) values (?,?,?,?,?)";
			
	pstmt = connection.prepareStatement(Query);
	pstmt.setString(1, refnum);;
	pstmt.setInt(2, Integer.parseInt(resultJson.getString("customercode")));
	pstmt.setString(3, resultJson.getString("telephone"));
	pstmt.setString(4, resultJson.getString("newAccountData"));
	pstmt.setString(5, resultJson.getString("topup"));
	pstmt.setString(6,requestJSON.getString(CevaCommonConstants.MAKER_ID));
	pstmt.setString(7, resultJson.getString("apptype"));
	
			pstmt.executeQuery();
			connection.commit();
			
			

			/*if (!cstmt.getString(10).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(10));
			}*/

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			
			
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
		
	public ResponseDTO refundCustAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
           PreparedStatement pstmt =null;

		try {
			
	
			/*insQRY = "{call WALLETACCOUNTSPKG.REFUNDAMOUNT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";*/

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);
            String ref=""+System.currentTimeMillis();
			connection = DBConnector.getConnection();

			/*cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("customercode"));
			cstmt.setString(2, resultJson.getString("email"));
			cstmt.setString(3, resultJson.getString("idnumber"));
			cstmt.setString(4, resultJson.getString("langugae"));
			cstmt.setString(5, resultJson.getString("telephone"));
			cstmt.setString(6, resultJson.getString("telco"));
			cstmt.setString(7, resultJson.getString("isocode"));
			cstmt.setString(8, resultJson.getString("multiData"));
			cstmt.setString(9,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR);
	
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("refund"));
			cstmt.setString(13, resultJson.getString("newAccountData"));*/
			
			
			String Query="insert into WALLET_WEB_TRANSACTIONS (TRANS_REF_NO,CUST_ID,MOBILE_NUMBER,ACCOUNT_NUMBER,TXN_AMOUNT,MAKER_ID,WALLET_TYPE,TXN_DATE,STATUS,REF_NUM,MAKER_DT,TXN_TYPE)	values(?,?,?,?,?,?,?,sysdate,'P',53322,sysdate,'WITHDRAWAL')";
			pstmt = connection.prepareStatement(Query);
			pstmt.setString(1, ref);
			pstmt.setInt(2, Integer.parseInt(resultJson.getString("customercode")));
			pstmt.setString(3, resultJson.getString("telephone"));
			pstmt.setString(4, resultJson.getString("newAccountData"));
			pstmt.setString(5, resultJson.getString("topup"));
			pstmt.setString(6,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			pstmt.setString(7, resultJson.getString("apptype"));
		pstmt.executeQuery();
		connection.commit();

			/*if (!cstmt.getString(10).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(10));
			}*/

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO fetchServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet rs = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		AccountBean accBean = null;

		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			connection = DBConnector.getConnection();

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID, "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.STATUS,'A','Active','B','Blocked'),CM.PRD_CODE,CM.PRD_DESC "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID "
					+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setClearedbalance(detRS.getString(11));
				accBean.setStatus(detRS.getString(12));
				accBean.setProduct(detRS.getString(13));
				accBean.setProdesc(detRS.getString(14));
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {
				int i = 0;
				


				detQry1 = "select m,n,o,p,q,r,s,t from "
						+ "(SELECT A.ACCT_NO m ,nvl(A.ACCT_NAME,' ') n,DECODE(A.ACCT_STATUS,'L','De-Active','A','Active') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,  B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM MOB_ACCT_DATA_TEMP A ,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('ACCACTDCT') "
						+ "AND A.CUST_ID =(SELECT ID FROM MOB_customer_master WHERE customer_code=? ) "
						+ "UNION "
						+ "SELECT A.CUSTOMER_CODE m,A.FIRST_NAME n,DECODE(B.AUTH_CODE,'ACCTPINRESET','Pin Reset','MODCUSTDETAUTH','Customer Details Modify') o,"
						+ "DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,"
						+ "DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t FROM MOB_CUSTOMER_MASTER_TEMP A,AUTH_PENDING B WHERE A.REF_NUM =B.REF_NUM "
						+ "AND B.AUTH_CODE IN('ACCTPINRESET','MODCUSTDETAUTH') AND A.CUSTOMER_CODE=? "
						+ "UNION "
						+ "SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE=? ) n,DECODE(DETAIL_2,'PINRESEND','Pin Resend','UNBLOCKPIN','Pin Unblock','DISABLECUSTOMER','Customer Disabled','ENABLECUSTOMER','Customer Enabled') o,'Completed' p,NET_ID q,"
						+ "' ' r,TXNDATE s,' ' t FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  in('PINRESEND','UNBLOCKPIN','DISABLECUSTOMER','ENABLECUSTOMER')  "
						+ "UNION "
						+ "SELECT A.ACCT_NO m,nvl(A.ALIAS_NAME,' ') n,DECODE(B.AUTH_CODE,'DELACCAUTH','Account Deletion') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM MOB_ACCT_DATA_HIST A,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('DELACCAUTH') "
						+ "AND A.CUST_id=(SELECT id FROM MOB_CUSTOMER_MASTER WHERE customer_code=? )) order by s desc ";

				logger.debug("Cust iD" + accBean.getCustomercode());
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());
				detPstmt1.setString(2, accBean.getCustomercode());
				detPstmt1.setString(3, accBean.getCustomercode());
				detPstmt1.setString(4, accBean.getCustomercode());
				detPstmt1.setString(5, accBean.getCustomercode());

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setMultiData1(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				detQry1 = "select MAC_ADDR,DEVICE_IP,IMEI_NO,SERIAL_NO,nvl(VERSION,' '),nvl(DEVICE_TYPE,' '),nvl(OS_TYPE,' '),decode(STATUS,'A','Active','L','De-Active') from MOB_IMEI_DATA where cust_id=(select id from mob_customer_master where CUSTOMER_CODE=?)";

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setSalutation(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				detQry1 = "select POSTING_RRN,(SELECT SERVICE_NAME FROM mob_service_master C WHERE C.SERVICE_CODE=T.TXN_TYPE) TXN_TYPE,MSISDN,to_date(TXN_TIME,'DD-MM-YYYY HH24:MI:SS') TXN_TIME,DEBIT_AC,(select CHANNEL_NAME from mob_channels MC where MC.ID=T.CHANNELID)  CHANNELID,DECODE(STATUS,'F','Failed','P','Pending','S','SUCCESS') STATUS,STATUS_DESC,NARRATION from TRAN_LOG T where MSISDN=(select '254'||mobile_number from mob_contact_info where cust_id=(select id from mob_customer_master where customer_code=?)) and rownum<6 order by TXN_TIME desc ";

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());

				logger.debug("Transaction Details execution query [ " + detQry1
						+ " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(100);
				billerData = new StringBuilder(100);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8)).append(",")
							.append(rs.getString(9));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setAccDetails(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

			}

			else {
				logger.debug("no records fetched from query");
				responseDTO.addError("Invalid Customer ID.");
			}
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(rs);
			DBUtils.closeResultSet(detRS);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeConnection(connection);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	public ResponseDTO deletefetchRegCustomerDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			connection = DBConnector.getConnection();

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID, "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.PRD_CODE,CM.PRD_DESC "
					+ "FROM WALLET_CUSTOMER_MASTER CM,WALLET_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
					+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	
	public ResponseDTO deleteAccounts(RequestDTO requestDTO) {

		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;


		try {
			
	

			insQRY = "{call WALLETACCOUNTSPKG.DELETECUSTDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("customercode"));
			cstmt.setString(2, resultJson.getString("email"));
			cstmt.setString(3, resultJson.getString("idnumber"));
			cstmt.setString(4, resultJson.getString("langugae"));
			cstmt.setString(5, resultJson.getString("telephone"));
			cstmt.setString(6, resultJson.getString("telco"));
			cstmt.setString(7, resultJson.getString("isocode"));
			cstmt.setString(8, resultJson.getString("multiData"));
			cstmt.setString(9,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("product"));
			cstmt.setString(13, resultJson.getString("prodesc"));
			cstmt.executeQuery();

			if (!cstmt.getString(10).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(10));
			}

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
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

	public ResponseDTO walletfetchCustomerServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet rs = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		AccountBean accBean = null;

		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			connection = DBConnector.getConnection();

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,CM.DOCID, "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.STATUS,'A','Active','B','Blocked'),CM.PRD_CODE,CM.PRD_DESC "
					+ "FROM WALLET_CUSTOMER_MASTER CM,WALLET_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID "
					+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setClearedbalance(detRS.getString(11));
				accBean.setStatus(detRS.getString(12));
				accBean.setProduct(detRS.getString(13));
				accBean.setProdesc(detRS.getString(14));
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {
				detQry1 = "select ACCT_NO,nvl(ACCT_NAME,' '),nvl(BRANCH_CODE,' '),ACCT_TYPE,nvl(ALIAS_NAME,' '),decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active') from WALLET_ACCT_DATA where CUST_ID=(select ID from WALLET_CUSTOMER_MASTER where CUSTOMER_CODE=?)";
				
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customercode"));

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2).replace(',', ' '))
							.append(",").append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5).replace(',', ' '))
							.append(",").append(rs.getString(6));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setMultiData(billerData.toString());
				billerData.delete(0, eachrow.length());

				logger.debug("biller details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);


				detQry1 = "select m,n,o,p,q,r,s,t from "
						+ "(SELECT A.ACCT_NO m ,nvl(A.ACCT_NAME,' ') n,DECODE(A.ACCT_STATUS,'L','De-Active','A','Active') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,  B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM WALLET_ACCT_DATA_TEMP A ,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('ACCACTDCT') "
						+ "AND A.CUST_ID =(SELECT ID FROM WALLET_CUSTOMER_MASTER WHERE customer_code=? ) "
						+ "UNION "
						+ "SELECT A.CUSTOMER_CODE m,A.FIRST_NAME n,DECODE(B.AUTH_CODE,'ACCTPINRESET','Pin Reset','MODCUSTDETAUTH','Customer Details Modify') o,"
						+ "DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,"
						+ "DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t FROM WALLET_CUSTOMER_MASTER_TEMP A,AUTH_PENDING B WHERE A.REF_NUM =B.REF_NUM "
						+ "AND B.AUTH_CODE IN('ACCTPINRESET','MODCUSTDETAUTH') AND A.CUSTOMER_CODE=? "
						+ "UNION "
						+ "SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM WALLET_CUSTOMER_MASTER WHERE CUSTOMER_CODE=? ) n,DECODE(DETAIL_2,'PINRESEND','Pin Resend','UNBLOCKPIN','Pin Unblock','DISABLECUSTOMER','Customer Disabled','ENABLECUSTOMER','Customer Enabled') o,'Completed' p,NET_ID q,"
						+ "' ' r,TXNDATE s,' ' t FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  in('PINRESEND','UNBLOCKPIN','DISABLECUSTOMER','ENABLECUSTOMER')  "
						+ "UNION "
						+ "SELECT A.ACCT_NO m,nvl(A.ALIAS_NAME,' ') n,DECODE(B.AUTH_CODE,'DELACCAUTH','Account Deletion') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM WALLET_ACCT_DATA_HIST A,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('DELACCAUTH') "
						+ "AND A.CUST_id=(SELECT id FROM WALLET_CUSTOMER_MASTER WHERE customer_code=? )) order by s desc ";

				logger.debug("Cust iD" + accBean.getCustomercode());
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());
				detPstmt1.setString(2, accBean.getCustomercode());
				detPstmt1.setString(3, accBean.getCustomercode());
				detPstmt1.setString(4, accBean.getCustomercode());
				detPstmt1.setString(5, accBean.getCustomercode());

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setMultiData1(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				detQry1 = "select MAC_ADDR,DEVICE_IP,IMEI_NO,SERIAL_NO,nvl(VERSION,' '),nvl(DEVICE_TYPE,' '),nvl(OS_TYPE,' '),decode(STATUS,'A','Active','L','De-Active') from MOB_IMEI_DATA where cust_id=(select id from WALLET_CUSTOMER_MASTER where CUSTOMER_CODE=?)";

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setSalutation(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				detQry1 = "select POSTING_RRN,(SELECT SERVICE_NAME FROM mob_service_master C WHERE C.SERVICE_CODE=T.TXN_TYPE) TXN_TYPE,MSISDN,to_date(TXN_TIME,'DD-MM-YYYY HH24:MI:SS') TXN_TIME,DEBIT_AC,(select CHANNEL_NAME from mob_channels MC where MC.ID=T.CHANNELID)  CHANNELID,DECODE(STATUS,'F','Failed','P','Pending','S','SUCCESS') STATUS,STATUS_DESC,NARRATION from TRAN_LOG T where MSISDN=(select '254'||mobile_number from WALLET_CONTACT_INFO where cust_id=(select id from WALLET_CUSTOMER_MASTER where customer_code=?)) and rownum<6 order by TXN_TIME desc ";

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());

				logger.debug("Transaction Details execution query [ " + detQry1
						+ " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(100);
				billerData = new StringBuilder(100);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(8)).append(",")
							.append(rs.getString(9));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setAccDetails(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

			}

			else {
				logger.debug("no records fetched from query");
				responseDTO.addError("Invalid Customer ID.");
			}
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(rs);
			DBUtils.closeResultSet(detRS);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeConnection(connection);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	
	
	public ResponseDTO walletcustServiceActions(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		CallableStatement cstmt = null;
		ResultSet detRS = null;
		ResultSet detRS1 = null;
		
		AccountBean accBean = null;
		String accid = null;
		String type = null;
		String encpin = null;
		String decpin = null;
		String pin = null;
		String ip = null;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			ip = requestJSON.getString(CevaCommonConstants.IP);

			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");

			accid = resultJson.getString("accountid");
			type = resultJson.getString("closed");
			connection = DBConnector.getConnection();

			if (type.equalsIgnoreCase("actdeactacc")) {

				detQry = "SELECT (SELECT CUSTOMER_CODE FROM WALLET_CUSTOMER_MASTER WHERE ID=MA.CUST_ID) CUSTID,"
						+ "ACCT_NO,nvl(ALIAS_NAME,' '),nvl(ACCT_NAME,' '),BRANCH_CODE,ACCT_TYPE,ACCT_STATUS,"
						+ "DECODE(INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
						+ "DATE_CREATED,CREATED_BY,"
						+ "decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active')"
						+ " FROM WALLET_ACCT_DATA MA where acct_no=?";

				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, accid);
				detRS = detPstmt.executeQuery();
				accBean = new AccountBean();

				if (detRS.next()) {
					accBean.setCustomercode(detRS.getString(1));
					accBean.setAccountid(detRS.getString(2));
					accBean.setFullname(detRS.getString(3));
					accBean.setAccountname(detRS.getString(4));
					accBean.setBranchcode(detRS.getString(5));
					accBean.setProductid(detRS.getString(6));
					accBean.setDormantstatus(detRS.getString(7));
					accBean.setInstitute(detRS.getString(8));
					accBean.setAuthDttm(detRS.getString(9));
					accBean.setMakerId(detRS.getString(10));
					accBean.setStopped(detRS.getString(11));

				}

				logger.debug("Bean Details   :::: " + accBean);
				detMap.put("AccountData", accBean);

			} else if (type.equalsIgnoreCase("resendpin")) {

				detQry1 = "select pin from WALLET_CUSTOMER_MASTER where CUSTOMER_CODE=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accid);
				detRS1 = detPstmt1.executeQuery();

				if (detRS1.next()) {
					encpin = detRS1.getString(1).trim();
				}
				decpin = aesDecString(encpin);
				detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";
				accBean = new AccountBean();

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				resultJson = (JSONObject) requestJSON.get("accBean");

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1, accid);
				cstmt.setString(2, type);
				cstmt.setString(3, "encryptedpin");
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8, ip);
				cstmt.setString(9, decpin);
				cstmt.executeQuery();

				accBean.setCustomerstatus(cstmt.getString(4));
				accBean.setOperator(cstmt.getString(6));
				accBean.setTelephone(cstmt.getString(5));

				logger.debug("RESEND TYPE : [" + cstmt.getString(4)
						+ "] STATUS :[" + cstmt.getString(6) + "]......."
						+ cstmt.getString(5));
				if (!cstmt.getString(6).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", accBean);
			} else if (type.equalsIgnoreCase("resetpin")) {

				detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				resultJson = (JSONObject) requestJSON.get("accBean");

				pin = CommonUtil.generatePassword(4);
				encpin = aesEncString(pin).trim();

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1, accid);
				cstmt.setString(2, type);
				cstmt.setString(3, encpin);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8, ip);
				cstmt.setString(9, pin);
				cstmt.executeQuery();

				accBean.setCustomerstatus(cstmt.getString(4));
				accBean.setOperator(cstmt.getString(6));
				accBean.setTelephone(cstmt.getString(5));
				accBean.setAuthId("PINRESET");

				logger.debug("RESET TYPE : [" + cstmt.getString(4)
						+ "] STATUS :[" + cstmt.getString(6) + "]......."
						+ cstmt.getString(5));

				if (!cstmt.getString(6).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", accBean);
			} else if (type.equalsIgnoreCase("unblockpin")) {

				detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				resultJson = (JSONObject) requestJSON.get("accBean");

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1, accid);
				cstmt.setString(2, type);
				cstmt.setString(3, " ");
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8, ip);
				cstmt.setString(9, " ");
				cstmt.executeQuery();

				accBean.setCustomerstatus(cstmt.getString(4));
				accBean.setOperator(cstmt.getString(6));
				accBean.setTelephone(cstmt.getString(5));
				accBean.setAuthId("UNBLOCKPIN");

				logger.debug("RESET TYPE : [" + cstmt.getString(4)
						+ "] STATUS :[" + cstmt.getString(6) + "]......."
						+ cstmt.getString(5));

				if (!cstmt.getString(6).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", accBean);
			} else if (type.equalsIgnoreCase("disablecust")) {

				detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				resultJson = (JSONObject) requestJSON.get("accBean");

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1, accid);
				cstmt.setString(2, type);
				cstmt.setString(3, " ");
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8, ip);
				cstmt.setString(9, " ");
				cstmt.executeQuery();

				accBean.setCustomerstatus(cstmt.getString(4));
				accBean.setOperator(cstmt.getString(6));
				accBean.setTelephone(cstmt.getString(5));
				accBean.setAuthId("DISABLECUST");

				logger.debug("DISABLE CUSTOMER : [" + cstmt.getString(4)
						+ "] STATUS :[" + cstmt.getString(6) + "]......."
						+ cstmt.getString(5));

				if (!cstmt.getString(6).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", accBean);
			} else if (type.equalsIgnoreCase("enablecust")) {

				detQry = "{call ACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				resultJson = (JSONObject) requestJSON.get("accBean");

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1, accid);
				cstmt.setString(2, type);
				cstmt.setString(3, " ");
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8, ip);
				cstmt.setString(9, " ");
				cstmt.executeQuery();

				accBean.setCustomerstatus(cstmt.getString(4));
				accBean.setOperator(cstmt.getString(6));
				accBean.setTelephone(cstmt.getString(5));
				accBean.setAuthId("ENABLECUST");

				logger.debug("ENABLE CUSTOMER : [" + cstmt.getString(4)
						+ "] STATUS :[" + cstmt.getString(6) + "]......."
						+ cstmt.getString(5));

				if (!cstmt.getString(6).contains("SUCCESS")) {
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", accBean);
			} else if (type.equalsIgnoreCase("actdeactimei")) {

				detQry = "select MAC_ADDR,DEVICE_IP,IMEI_NO,SERIAL_NO,VERSION,DEVICE_TYPE,OS_TYPE,decode(STATUS,'A','Active','L','De-Active') "
						+ "from MOB_IMEI_DATA where cust_id=(select id from WALLET_CUSTOMER_MASTER where CUSTOMER_CODE=?)";

				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, accid);
				detRS = detPstmt.executeQuery();
				accBean = new AccountBean();

				if (detRS.next()) {
					accBean.setAccountname(detRS.getString(1));
					accBean.setAccountid(detRS.getString(2));
					accBean.setCustomercode(detRS.getString(3));
					accBean.setFullname(detRS.getString(4));
					accBean.setProductid(detRS.getString(5));
					accBean.setInstitute(detRS.getString(6));
					accBean.setAuthDttm(detRS.getString(7));
					accBean.setStopped(detRS.getString(8));
				}

				logger.debug("Bean Details   :::: " + accBean);
				detMap.put("AccountData", accBean);

			}

			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {
			

			DBUtils.closeResultSet(detRS);
			DBUtils.closeResultSet(detRS1);
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeConnection(connection);
			detQry = null;

		}

		return responseDTO;

	}
	public ResponseDTO insertaccountstatus(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		CallableStatement cstmt = null;
		ResultSet detRS = null;
		
		AccountBean accBean = null;
		String accid = null;
		String ip = null;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);
			accid = resultJson.getString("accountid");
			connection = DBConnector.getConnection();
			logger.debug("IN SIDE INSERTACCOUNTSTATUS");

			detQry = "{call WALLETACCOUNTSPKG.INSERTACCSTATUS(?,?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			cstmt = connection.prepareCall(detQry);
			cstmt.setString(1, accid);
			cstmt.setString(2,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(3, ip);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);

			cstmt.executeQuery();

			accBean.setCustomerstatus(cstmt.getString(4));
			accBean.setOperator(cstmt.getString(6));
			accBean.setTelephone(cstmt.getString(5));
			// accBean.setTelephone(cstmt.getString(5));

			logger.debug("Accout status Return: [" + cstmt.getString(4)
					+ "] STATUS :[" + cstmt.getString(6) + "]......."
					+ cstmt.getString(5));

			if (!cstmt.getString(6).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(6));
			}
			detMap.put("AccountData", accBean);

			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Status Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(detRS);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			detQry = null;

		}

		return responseDTO;

	}
	public ResponseDTO fetchbranchdatacapturedetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;
		
		String makerid="";
		

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");
			branchDAO = new BranchDaoImpl();
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			makerid=requestJSON.getString("makerId");
			connection = DBConnector.getConnection();
			
		
			

			 detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
						+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,CA.ACCT_NO,CA.BRANCH_CODE "
						+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
						+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
						+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA mt where mt.cust_id=cm.id AND PRIM_FLAG='P') AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customercode"));
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setNewAccountData(detRS.getString(13));
				accBean.setBranchcode(detRS.getString(14));
				accBean.setFullname(detRS.getString(2));
				accBean.setTelco(detRS.getString(3));
				accBean.setIsocode(detRS.getString(4));
				accBean.setTelephone(detRS.getString(5));
				accBean.setIdnumber(detRS.getString(6));
				accBean.setLangugae(detRS.getString(7));
				accBean.setEmail(detRS.getString(8));
				accBean.setInstitute(detRS.getString(9));
				accBean.setAuthDttm(detRS.getString(10));
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				
				flag = true;
			}
			
			logger.debug("Bean Details   :::: " + accBean);
			
			accBean.setBranchdetails((branchDAO.getBranchesToSelectBox()).toString());
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rs);

			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(detRS2);

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
}
