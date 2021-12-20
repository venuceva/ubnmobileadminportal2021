package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.aestools.AES;
import com.ceva.base.common.bean.PrepaidCardBean;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

public class PrepaidCardDAO {

	private Logger logger = Logger.getLogger(PrepaidCardDAO.class);

	private ResponseDTO responseDTO = null;
	private JSONObject requestJSON = null;

	public ResponseDTO fetchCustomerAccountDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		String detQry3 = "";
		JSONObject resultJson = null;
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet detRS2 = null;
		ResultSet rs = null;

		PrepaidCardBean prepaidBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		
		String teleNumber = null;
		String ip = null;
		String makerid = null;
		String auditQry = "";
		CallableStatement cstmt = null;
		
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			prepaidBean = new PrepaidCardBean();
			requestJSON = requestDTO.getRequestJSON();
			
			
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON().get("prepaidBean");
			connection = DBConnector.getConnection();

				
			
			if (resultJson.getString("institute").equalsIgnoreCase("INSTID1")) {
				logger.debug("into bfub customer details");
				detQry = "select CUSTOMERCODE,FULLNAME,BRANCHCODE,TELEPHONE,IDNUMBER,CUSTOMERTYPE,CUSTOMERSTATUS,SALUTATION,EMAIL from BFUB_CUSTOMER_INFO_VW where CUSTOMERCODE=? ";
			} else if (resultJson.getString("institute").equalsIgnoreCase("INSTID2")) {

				logger.debug("into imal customer details");
				detQry = "select CUSTOMERCODE,FULLNAME,BRANCHCODE,TELEPHONE,IDNUMBER,CUSTOMERTYPE,CUSTOMERSTATUS,SALUTATION,EMAIL from IMAL_CUSTOMER_INFO_VW where CUSTOMERCODE=? or OLDCUSTOMERCODE=? ";

			}

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customerId"));
			if (resultJson.getString("institute").equalsIgnoreCase("INSTID2")) {
			detPstmt.setString(2, resultJson.getString("customerId"));
			}
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			prepaidBean = new PrepaidCardBean();

			if (detRS.next()) {
				prepaidBean.setCustomercode(detRS.getString(1));
				prepaidBean.setFullname(detRS.getString(2));
				prepaidBean.setBranchcode(detRS.getString(3));
				prepaidBean.setTelephone(detRS.getString(4));
				prepaidBean.setIdnumber(detRS.getString(5));
				prepaidBean.setCustomertype(detRS.getString(6));
				prepaidBean.setCustomerstatus(detRS.getString(7));
				prepaidBean.setSalutation(detRS.getString(8));
				prepaidBean.setEmail(detRS.getString(9));
				flag = true;
			}
			
			teleNumber = prepaidBean.getTelephone();
			
			 
			detPstmt.close();
			detRS.close();
			
			logger.debug("Bean Details   :::: " + prepaidBean);
			
			detQry3 = "select NVL(MCI.MOBILE_NUMBER,'NO_DATA') MOB FROM MOB_CONTACT_INFO MCI ,MOB_CUSTOMER_MASTER MCM where MCM.CUSTOMER_CODE=? and MCM.ID=MCI.CUST_ID";
			
			detPstmt = connection.prepareStatement(detQry3);
			detPstmt.setString(1, resultJson.getString("customerId"));
			detRS = detPstmt.executeQuery();
			
			
			if (detRS.next()) {	prepaidBean.setTelephone(detRS.getString(1)); }
			else {	prepaidBean.setTelephone(teleNumber);	}
			
			if (flag) {
				if (resultJson.getString("institute").equalsIgnoreCase(
						"INSTID1")) {
					//detQry1 = "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status  from BFUB_ACCOUNT_INFO_VW where CUSTOMERCODE=?	";
					detQry1="select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d "
							+ ",i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending') h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m where i.CUSTOMERCODE=? "
							//+ "and  exists (select 1 from MOB_ACCT_DATA m where m.ACCT_NO=i.ACCOUNTID) union select i.CUSTOMERCODE a,i.ACCOUNTID b "
							+ "and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID)"
							+ "union select i.CUSTOMERCODE a,i.ACCOUNTID b "
							+ ",i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i "
							+ "where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA_TEMP m where m.ACCT_NO=i.ACCOUNTID))";					
					logger.debug("into bfub acc details");
				} else if (resultJson.getString("institute").equalsIgnoreCase("INSTID2")) {
					logger.debug("into imal acc details");
					//detQry1 = "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE=?";
					/*detQry1="select a,b,c,d,e,f,g,h from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d "
							+ ",i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Registered' h from IMAL_ACCOUNT_INFO_VW i where i.CUSTOMERCODE=? "
							+ "and  exists (select 1 from MOB_ACCT_DATA m where m.ACCT_NO=i.ACCOUNTID) union select i.CUSTOMERCODE a,i.ACCOUNTID b "
							+ ",i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h from IMAL_ACCOUNT_INFO_VW i "
							+ "where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA m where m.ACCT_NO=i.ACCOUNTID))";*/
					
					detQry1="(select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending') h,i.PRODUCT p,i.SUBPRODUCTID k "
							+ "from IMAL_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m where i.CUSTOMERCODE=? and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID) "
							+ "union select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,"
							+ "'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA_TEMP m "
							+ "where m.ACCT_NO=i.ACCOUNTID))) UNION (select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,"
							+ "i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending') h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m "
							+ "where i.CUSTOMERCODE=(select CUSTOMERCODE from IMAL_CUSTOMER_INFO_VW where OLDCUSTOMERCODE=?)"
							+ " and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID) union select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,"
							+ "i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i where  "
							+ "i.CUSTOMERCODE=(select CUSTOMERCODE from IMAL_CUSTOMER_INFO_VW where OLDCUSTOMERCODE=?) and not exists "
							+ "(select 1 from MOB_ACCT_DATA_TEMP m where m.ACCT_NO=i.ACCOUNTID)))";
				}

				logger.debug("Acc details execution query [ " + detQry1 + " ]");

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customerId"));
				detPstmt1.setString(2, resultJson.getString("customerId"));
				if (resultJson.getString("institute").equalsIgnoreCase("INSTID2")) {
					detPstmt1.setString(3, resultJson.getString("customerId"));
					detPstmt1.setString(4, resultJson.getString("customerId"));
				}

				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(7)).append(",")
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(8)).append(",")
							.append(rs.getString(9)).append(",")
							.append(rs.getString(10));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				prepaidBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS " + billerData);
				detMap.put("AccountData", prepaidBean);
				
				billerData.delete(0, eachrow.length());
				
				
				auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
				cstmt = connection.prepareCall(auditQry);
				cstmt.setString(1, "fetchCustomerInfoAct");
				cstmt.setString(2, makerid);
				cstmt.setString(3, "WEB");
				cstmt.setString(4, makerid+" has retrieved details for Customer ID "+resultJson.getString("customerId"));
				cstmt.setString(5, ip);
				cstmt.setString(6, " ");
				cstmt.setString(7, " ");
				cstmt.setString(8, " ");
				i = cstmt.executeUpdate();
				
				if (i == 1) {
					logger.debug("Action Identification Interceptor -> Successfully Inserted "+ ip);
				} else {
					logger.debug("Action Identification Interceptor -> insertion failed due to some error "+ ip);
				}
				
			} else {
				logger.debug("no records fetched from query");
				responseDTO.addError("Invalid Customer ID.");
			}

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
			DBUtils.closeCallableStatement(cstmt);
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
		PrepaidCardBean prepaidBean = null;
		String ip=null;


		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			prepaidBean = new PrepaidCardBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			ip=requestJSON.getString(CevaCommonConstants.IP);
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("prepaidBean");
			connection = DBConnector.getConnection();
			
			logger.debug("connection is null [" + (connection == null) + "]");

				detQry = "SELECT CM.ID,CM.FIRST_NAME,CM.MSISDN,CM.DOB,CM.STATUS,CM.DOCID,to_char(CM.DATE_CREATED,'DD-MM-YYYY HH24:MI:SS') "
						+ "FROM MOB_PREPAIDCUST_MASTER CM WHERE CM.ID=?";
			

				logger.debug("First Query Executed" + detQry);
				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, resultJson.getString("customercode"));
				detRS = detPstmt.executeQuery();
				//logger.debug("First Query Executed" + detQry);
				prepaidBean = new PrepaidCardBean();

				if (detRS.next()) {
					prepaidBean.setCustomercode(detRS.getString(1));
					prepaidBean.setFullname(detRS.getString(2).replace(',',' '));
					prepaidBean.setTelephone(detRS.getString(3));
					prepaidBean.setLangugae(detRS.getString(4));
					prepaidBean.setStatus(detRS.getString(5));
					prepaidBean.setIdnumber(detRS.getString(6));
					prepaidBean.setAuthDttm(detRS.getString(7));
					flag = true;
				}
				
				logger.debug("Bean Details   :::: " + prepaidBean.getFullname());
				
				if (flag) {
				detQry1 = "select CARD_PAN,ACCOUNTNUMBER,to_char(CREATED_ON,'DD-MM-YYYY HH24:MI:SS'),CRNCY_CODE,decode(STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active') from MOB_PREPAIDCARD_DETAILS where CUST_ID=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customercode"));
				
				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

					int i = 0;
					eachrow = new StringBuilder(50);
					billerData = new StringBuilder(50);

					while (rs.next()) {
						eachrow.append(rs.getString(1)).append(",")
								.append(rs.getString(2)).append(",")
								.append(rs.getString(3)).append(",")
								.append(rs.getString(4)).append(",")
								.append(rs.getString(5));
						if (i == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						i++;
						eachrow.delete(0, eachrow.length());
					}
					
					
					prepaidBean.setMultiData(billerData.toString());
					billerData.delete(0, eachrow.length());
					
					logger.debug("biller details " + billerData);
					
					
					DBUtils.closePreparedStatement(detPstmt1);
					DBUtils.closeResultSet(rs);
					
					
					
				
					detQry1="select m,n,o,p,q,s from ("
							+ "SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_PREPAIDCUST_MASTER WHERE ID=? ) n,'Pin Resend' o,'Completed' p,NET_ID q,TXNDATE s "
							+ "FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  ='PREPINRESEND' "
							+ "UNION"
							+ "	SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_PREPAIDCUST_MASTER WHERE ID=? ) n,'Pin Reset' o,'Completed' p,NET_ID q,TXNDATE s "
							+ "FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  ='PREPINRESET' "
							+ "UNION"
							+ "	SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_PREPAIDCUST_MASTER WHERE ID=? ) n,'Card Status Change' o,'Completed' p,NET_ID q,TXNDATE s "
							+ "FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  ='PRESTATUS'	) order by s desc";
					
					
					logger.debug("Cust iD"+prepaidBean.getCustomercode());
					detPstmt1 = connection.prepareStatement(detQry1);
					detPstmt1.setString(1, prepaidBean.getCustomercode());
					detPstmt1.setString(2, prepaidBean.getCustomercode());
					detPstmt1.setString(3, prepaidBean.getCustomercode());
					detPstmt1.setString(4, prepaidBean.getCustomercode());
					detPstmt1.setString(5, prepaidBean.getCustomercode());
					detPstmt1.setString(6, prepaidBean.getCustomercode());
					
					
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
									.append(rs.getString(6));
							if (i == 0) {
								billerData.append(eachrow);
							} else {
								billerData.append("#").append(eachrow);
							}
							i++;
							eachrow.delete(0, eachrow.length());
						}
						
						
						prepaidBean.setMultiData1(billerData.toString());
						
						billerData.delete(0, eachrow.length());
						
						logger.debug("Responce details " + billerData);
						
						DBUtils.closePreparedStatement(detPstmt1);
						DBUtils.closeResultSet(rs);
						
				}
					
					else {
						logger.debug("no records fetched from query");
						responseDTO.addError("Invalid Customer ID.");
					}
				detMap.put("AccountData", prepaidBean);
				logger.debug("EntityMap [" + detMap + "]");
				responseDTO.setData(detMap);
				logger.debug("responseDTO  [" + responseDTO + "]");

		
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

			detQry = null;
			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	
	public ResponseDTO fetchActionresult(RequestDTO requestDTO) {
		
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
		PrepaidCardBean prepaidBean = null;
		String accid=null;
		String type=null;
		String encpin=null;
		String decpin=null;
		String pin =null;
		String ip =null;
		

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			prepaidBean = new PrepaidCardBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			ip=requestJSON.getString(CevaCommonConstants.IP);
			
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("prepaidBean");
			
			accid=resultJson.getString("accountid");
			type=resultJson.getString("closed");
			System.out.println("type in dao "+type );
			connection = DBConnector.getConnection();
			
			logger.debug("connection is null [" + (connection == null) + "]");
			
			if(type.equalsIgnoreCase("actdeactacc")){
				
			detQry = "SELECT CUST_ID,CARD_PAN,ACCOUNTNUMBER,CREATED_ON,(SELECT FIRST_NAME FROM MOB_PREPAIDCUST_MASTER WHERE ID=MA.CUST_ID) NAME, "
					+ "decode(STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active') FROM MOB_PREPAIDCARD_DETAILS MA where cust_id=?";
			
			
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1,accid );
			detRS = detPstmt.executeQuery();
			prepaidBean = new PrepaidCardBean();
			
			if (detRS.next()) {
				prepaidBean.setCustomercode(detRS.getString(1));
				prepaidBean.setAccountid(detRS.getString(2));
				prepaidBean.setAccountname(detRS.getString(3));
				prepaidBean.setMakerDttm(detRS.getString(4));
				prepaidBean.setFullname(detRS.getString(5));
				prepaidBean.setStatus(detRS.getString(6));

			}
			
			logger.debug("Bean Details   :::: " + prepaidBean);
			detMap.put("AccountData", prepaidBean);
			
			}
			else if(type.equalsIgnoreCase("resendpin"))
			{
				
				detQry1="select pin from MOB_PREPAIDCARD_DETAILS where CARD_PAN=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1,accid );
				detRS1 = detPstmt1.executeQuery();
				
				if (detRS1.next()) {
					encpin=detRS1.getString(1).trim();
				}
				decpin=aesDecString(encpin);
				detQry = "{call PREPAIDPKG.RESETCARDPIN(?,?,?,?,?,?,?,?,?)}";
				prepaidBean = new PrepaidCardBean();

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON(); 
				resultJson = (JSONObject) requestJSON.get("prepaidBean");

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1,accid );
				cstmt.setString(2,type);
				cstmt.setString(3,"encryptedpin");
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8,ip );
				cstmt.setString(9,decpin);
				cstmt.executeQuery();
				
				
				prepaidBean.setCustomerstatus(cstmt.getString(4));
				prepaidBean.setOperator(cstmt.getString(6));
				prepaidBean.setTelephone(cstmt.getString(5));

				logger.debug("RESEND TYPE : ["+cstmt.getString(4)+"] STATUS :["+cstmt.getString(6)+"]......."+cstmt.getString(5));
				if (!cstmt.getString(6).contains("SUCCESS")) { 
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", prepaidBean);
			}
			else if(type.equalsIgnoreCase("resetpin"))
			{
				
				detQry = "{call PREPAIDPKG.RESETCARDPIN(?,?,?,?,?,?,?,?,?)}";
				 
				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON(); 
				resultJson = (JSONObject) requestJSON.get("prepaidBean");
				
				pin = CommonUtil.generatePassword(4);
				encpin=aesEncString(pin).trim();

				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1,accid );
				cstmt.setString(2,type);
				cstmt.setString(3,encpin);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.setString(7, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(8,ip);
				cstmt.setString(9,pin);
				cstmt.executeQuery();
				
				prepaidBean.setCustomerstatus(cstmt.getString(4));
				prepaidBean.setOperator(cstmt.getString(6));
				prepaidBean.setTelephone(cstmt.getString(5));
				prepaidBean.setAuthId("PINRESET");
				
				logger.debug("RESET TYPE : ["+cstmt.getString(4)+"] STATUS :["+cstmt.getString(6)+"]......."+cstmt.getString(5));
				
				if (!cstmt.getString(6).contains("SUCCESS")) { 
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", prepaidBean);
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

			
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
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
		PrepaidCardBean prepaidBean = null;
		String accid=null;
		String type=null;
		String ip=null;
		
		
		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			prepaidBean = new PrepaidCardBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("prepaidBean");
			ip=requestJSON.getString(CevaCommonConstants.IP);
			accid=resultJson.getString("accountid");
			type=resultJson.getString("closed");
			connection = DBConnector.getConnection();
			logger.debug("IN SIDE INSERTACCOUNTSTATUS");
			
			logger.debug("connection is null [" + (connection == null) + "]");
			
				detQry = "{call PREPAIDPKG.INSERTCARDSTATUS(?,?,?,?,?,?)}";
				
				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON(); 
				resultJson = (JSONObject) requestJSON.get("prepaidBean");
				
				cstmt = connection.prepareCall(detQry);
				cstmt.setString(1,accid );
				cstmt.setString(2, requestJSON.getString(CevaCommonConstants.MAKER_ID));
				cstmt.setString(3,ip);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				
				cstmt.executeQuery();
				
				prepaidBean.setCustomerstatus(cstmt.getString(4));
				prepaidBean.setOperator(cstmt.getString(6));
				prepaidBean.setTelephone(cstmt.getString(5));
				//prepaidBean.setTelephone(cstmt.getString(5));
				
				
				logger.debug("Card status Return: ["+cstmt.getString(4)+"] STATUS :["+cstmt.getString(6)+"]......."+cstmt.getString(5));
				
				if (!cstmt.getString(6).contains("SUCCESS")) { 
					responseDTO.addError(cstmt.getString(6));
				}
				detMap.put("AccountData", prepaidBean);
			
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
			
			
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);
			detQry = null;
			
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
	
	
	
	
	
	
}
