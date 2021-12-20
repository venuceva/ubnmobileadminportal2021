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
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

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
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

public class AddNewAccountDAO {

	private Logger logger = Logger.getLogger(AddNewAccountDAO.class);

	private ResponseDTO responseDTO = null;
	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;
	ResourceBundle bundle = null;

	public ResponseDTO fetchCustomerAccountDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		StringBuffer detQry4 = null;
		String detQry3 = "";
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

		String teleNumber = null;
		String ip = null;
		String makerid = null;
		String auditQry = "";
		CallableStatement cstmt = null;

		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			connection = DBConnector.getConnection();

			/*if (resultJson.getString("institute").equalsIgnoreCase("INSTID1")) {
				logger.debug("into bfub customer details");
				detQry = "select CUSTOMERCODE,FULLNAME,BRANCHCODE,TELEPHONE,IDNUMBER,CUSTOMERTYPE,CUSTOMERSTATUS,SALUTATION,EMAIL from BFUB_CUSTOMER_INFO_VW where CUSTOMERCODE=? ";
			} else if (resultJson.getString("institute").equalsIgnoreCase(
					"INSTID2")) {*/

				logger.debug("into imal customer details");
				detQry = "select CUSTOMERCODE,FULLNAME,BRANCHCODE,TELEPHONE,IDNUMBER,CUSTOMERTYPE,CUSTOMERSTATUS,SALUTATION,EMAIL from IMAL_CUSTOMER_INFO_VW where CUSTOMERCODE=? or OLDCUSTOMERCODE=? ";

		/*	}*/

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("customerId"));
			/*if (resultJson.getString("institute").equalsIgnoreCase("INSTID2")) {*/
				detPstmt.setString(2, resultJson.getString("customerId"));
			/*}*/
			detRS = detPstmt.executeQuery();
			logger.debug("First Query Executed" + detQry);
			accBean = new AccountBean();

			if (detRS.next()) {
				accBean.setCustomercode(detRS.getString(1));
				accBean.setFullname(detRS.getString(2));
				accBean.setBranchcode(detRS.getString(3));
				accBean.setTelephone(detRS.getString(4));
				accBean.setIdnumber(detRS.getString(5));
				accBean.setCustomertype(detRS.getString(6));
				accBean.setCustomerstatus(detRS.getString(7));
				accBean.setSalutation(detRS.getString(8));
				accBean.setEmail(detRS.getString(9));
				flag = true;
			}

			teleNumber = accBean.getTelephone();

			detPstmt.close();
			detRS.close();

			logger.debug("Bean Details   :::: " + accBean);

			detQry3 = "select NVL(MCI.MOBILE_NUMBER,'NO_DATA') MOB FROM MOB_CONTACT_INFO MCI ,MOB_CUSTOMER_MASTER MCM where MCM.CUSTOMER_CODE=? and MCM.ID=MCI.CUST_ID";

			detPstmt = connection.prepareStatement(detQry3);
			detPstmt.setString(1, resultJson.getString("customerId"));
			detRS = detPstmt.executeQuery();

			if (detRS.next()) {
				accBean.setTelephone(detRS.getString(1));
			} else {
				accBean.setTelephone(teleNumber);
			}
			detQry4=new StringBuffer();
			if (flag) {
				/*if (resultJson.getString("institute").equalsIgnoreCase(
						"INSTID1")) {
					
					detQry1 = "select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d "
							+ ",i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending') h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m where i.CUSTOMERCODE=? "
							+ "and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID)"
							+ "union select i.CUSTOMERCODE a,i.ACCOUNTID b "
							+ ",i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i "
							+ "where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA_TEMP m where m.ACCT_NO=i.ACCOUNTID))";
					detQry4.append("select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ");
					detQry4.append( ",i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending','AUS','Registered','AR','Rejected') h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m where i.CUSTOMERCODE=? ");
					detQry4.append( "and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID)");
					detQry4.append( "union select i.CUSTOMERCODE a,i.ACCOUNTID b ");
					detQry4.append( ",i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from BFUB_ACCOUNT_INFO_VW i ");
					detQry4.append( "where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA_TEMP m where m.ACCT_NO=i.ACCOUNTID))");
					logger.debug("into bfub acc details");
				} else if (resultJson.getString("institute").equalsIgnoreCase(
						"INSTID2")) {*/
					logger.debug("into imal acc details");

							detQry4.append("(select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending','AUS','Registered','AR','Rejected') h,i.PRODUCT p,i.SUBPRODUCTID k ");
							detQry4.append(" from IMAL_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m where i.CUSTOMERCODE=? and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID) ");
							detQry4.append(" union select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,");
							detQry4.append(" 'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i where  i.CUSTOMERCODE=? and not exists (select 1 from MOB_ACCT_DATA_TEMP m ");
							detQry4.append(" where m.ACCT_NO=i.ACCOUNTID))) UNION (select a,b,c,d,e,f,g,h,p,k from (select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,i.FULLNAME d ,");
							detQry4.append(" i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,decode(m.auth_flag,'AD','Registered','AUP','Pending','AUS','Registered','AR','Rejected') h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i, MOB_ACCT_DATA_TEMP m ");
							detQry4.append(" where i.CUSTOMERCODE=(select CUSTOMERCODE from IMAL_CUSTOMER_INFO_VW where OLDCUSTOMERCODE=?)");
							detQry4.append("  and m.ACCT_NO=i.ACCOUNTID and m.DATE_CREATED=(select max(C.DATE_CREATED) from MOB_ACCT_DATA_TEMP C where C.ACCT_NO=i.ACCOUNTID) union select i.CUSTOMERCODE a,i.ACCOUNTID b ,i.PRODUCTID c ,");
							detQry4.append(" i.FULLNAME d ,i.CURRENCY e ,i.DORMANTSTATUS f ,i.BRANCHCODE g ,'Un Registered' h,i.PRODUCT p,i.SUBPRODUCTID k  from IMAL_ACCOUNT_INFO_VW i where  ");
							detQry4.append(" i.CUSTOMERCODE=(select CUSTOMERCODE from IMAL_CUSTOMER_INFO_VW where OLDCUSTOMERCODE=?) and not exists ");
							detQry4.append(" (select 1 from MOB_ACCT_DATA_TEMP m where m.ACCT_NO=i.ACCOUNTID)))");
				/*}*/

				logger.debug("Acc details execution query [ " + detQry4 + " ]");

				detPstmt1 = connection.prepareStatement(detQry4.toString());
				detPstmt1.setString(1, resultJson.getString("customerId"));
				detPstmt1.setString(2, resultJson.getString("customerId"));
				/*if (resultJson.getString("institute").equalsIgnoreCase(
						"INSTID2")) {*/
					detPstmt1.setString(3, resultJson.getString("customerId"));
					detPstmt1.setString(4, resultJson.getString("customerId"));
				/*}*/

				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(7)).append(",")		//BRANCHCODE
							.append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")		//FULLNAME
							.append(rs.getString(8)).append(",")
							.append(rs.getString(9)).append(",")		//PRODUCT
							.append(rs.getString(10));					//SUBPRODUCTID
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "+ billerData);
				detMap.put("AccountData", accBean);

				billerData.delete(0, eachrow.length());

				auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
				cstmt = connection.prepareCall(auditQry);
				cstmt.setString(1, "fetchCustomerInfoAct");
				cstmt.setString(2, makerid);
				cstmt.setString(3, "WEB");
				cstmt.setString(4, makerid
						+ " has retrieved details for Customer ID "
						+ resultJson.getString("customerId"));
				cstmt.setString(5, ip);
				cstmt.setString(6, " ");
				cstmt.setString(7, " ");
				cstmt.setString(8, " ");
				i = cstmt.executeUpdate();

				if (i == 1) {
					logger.debug("Action Identification Interceptor -> Successfully Inserted "
							+ ip);
				} else {
					logger.debug("Action Identification Interceptor -> insertion failed due to some error "
							+ ip);
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

	public ResponseDTO fetchInstdetails(RequestDTO requestDTO) {

		logger.debug("Inside Get Biller Type Details .. ");
		HashMap<String, Object> maap = null;
		JSONObject resultJson = null;
		JSONObject json = null;
		
		PreparedStatement pstmt = null;
		ResultSet rS = null;
		Connection connection = null;

		String Qry = "";
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			maap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			

			Qry = "SELECT SI.INSTITUTION_ID,SI.INSTITUTION_NAME from SERVICES_INSTITUTIONS SI";
			pstmt = connection.prepareStatement(Qry);
			rS = pstmt.executeQuery();

			json = new JSONObject();

			while (rS.next()) {
				json.put(rS.getString(1), rS.getString(2));
			}
			logger.debug("Output of institutions " + json);

			resultJson.put("institutesel", json);
			maap.put("INSTDETAILS", resultJson);
			logger.debug("MerchantMap [" + maap + "]");
			responseDTO.setData(maap);
			json.clear();

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
		} finally {
			try {
				rS.close();
				pstmt.close();
				connection.close();
				json = null;
			} catch (Exception es) {
			}
		}
		return responseDTO;
	}

	public ResponseDTO insertNewAccountInfo(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";

		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		String encpin = null;
		String pin = null;

		String ip = null;

		try {

			pin = CommonUtil.generatePassword(4);
			encpin = aesEncString(pin).trim();

			insQRY = "{call ACCOUNTSPKG.INSERTADDACCOUNTTOCUSTOMERPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");

			ip = requestJSON.getString(CevaCommonConstants.IP);

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("institute"));
			cstmt.setString(2, resultJson.getString("customercode"));
			cstmt.setString(3, resultJson.getString("email"));
			cstmt.setString(4, resultJson.getString("fullname"));
			cstmt.setString(5, resultJson.getString("idnumber"));
			cstmt.setString(6, resultJson.getString("langugae"));
			cstmt.setString(7, resultJson.getString("telephone"));
			cstmt.setString(8, resultJson.getString("telco"));
			cstmt.setString(9, resultJson.getString("isocode"));
			cstmt.setString(10, resultJson.getString("multiData"));
			cstmt.setString(11, resultJson.getString("newAccountData"));
			cstmt.setString(12,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(13, Types.VARCHAR);
			cstmt.setString(14, encpin);
			cstmt.setString(15, pin);
			cstmt.setString(16, ip);
			cstmt.setString(17, resultJson.getString("product"));
			cstmt.setString(18, resultJson.getString("prodesc"));
			cstmt.executeQuery();

			if (!cstmt.getString(13).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(13));
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

	public ResponseDTO modifycustomerinfo(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		try {

			insQRY = "{call ACCOUNTSPKG.MODCUSTDETPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

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
			cstmt.setString(6, resultJson.getString("newuserid"));
			cstmt.setString(7, "Product:"+resultJson.getString("changeproduct")+",Userid:"+resultJson.getString("changenewuserid"));
			cstmt.setString(8, resultJson.getString("multiData"));
			cstmt.setString(9,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("product"));
			cstmt.setString(13, resultJson.getString("prodesc"));
			cstmt.setString(14, resultJson.getString("apptype"));
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
	
	
	public ResponseDTO modiCustInfoPinAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		PreparedStatement pstmt = null;
		JSONObject resultJson = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String status="U";

		try {
			responseDTO = new ResponseDTO();
			insQRY = "";

			
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);
			
			CommonServiceDao csd=new CommonServiceDao();
			JSONObject resp =  csd.genaratePassword().getJSONObject("data");

			connection = DBConnector.getConnection();
			
			String query="SELECT NVL(AUTHID,' ') FROM MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE='"+resultJson.getString("customercode")+"'";
			
			servicePstmt = connection.prepareStatement(query);
			serviceRS = servicePstmt.executeQuery();
			if(serviceRS.next())
			{
				if((serviceRS.getString(1)).equalsIgnoreCase("MOBILE")){
					status="A";
				}
			}
			
			pstmt = connection.prepareStatement("UPDATE MOB_CONTACT_INFO SET MOBILE_NUMBER='"+resultJson.getString("telephone")+"' WHERE CUST_ID in (SELECT ID FROM MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE='"+resultJson.getString("customercode")+"')");
			pstmt.executeUpdate();
			pstmt.close();
			
			
			pstmt = connection.prepareStatement("UPDATE MOB_CUSTOMER_MASTER SET TXN_PIN=?,STATUS=? WHERE CUSTOMER_CODE='"+resultJson.getString("customercode")+"'");
			pstmt.setString(1, resp.getString("pinHash"));
			pstmt.setString(2, status);
			
			pstmt.executeUpdate();
			
			ServiceRequestClient.sms(resultJson.getString("telephone"), "Dear "+resultJson.getString("fullname")+", Please find the Mobile Banking  Transaction Pin "+resp.getString("pin"));

			 JSONObject jsonaudit = new JSONObject();
			 	jsonaudit.put(CevaCommonConstants.MAKER_ID, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			 	jsonaudit.put("transCode", "modregcustinfo");
			 	jsonaudit.put("channel", "WEB");
			 	jsonaudit.put("message", "Set Txn Pin : Mobile Number "+resultJson.getString("telephone"));
			 	jsonaudit.put("ip", ip);
			 	jsonaudit.put("det1", "");
			 	jsonaudit.put("det2", "");
			 	jsonaudit.put("det3", "");
				
				
				csd.auditTrailInsert(jsonaudit);
				
			connection.commit();

			
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			connection = null;
		}
		return responseDTO;
	}

	
	public ResponseDTO enhancementInfo(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		try {

			insQRY = "{call ACCOUNTSPKG.LIMITCUSTDETPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

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
			cstmt.setString(14, resultJson.getString("apptype"));
			cstmt.setString(15, resultJson.getString("custperdaylimit"));
			cstmt.setString(16, resultJson.getString("plimitchannel"));
			cstmt.setString(17, resultJson.getString("secfalimit"));
			cstmt.executeQuery();

			if (!cstmt.getString(10).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(10));
			}
			//responseDTO.addError("SUCCESS");
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
	
	public ResponseDTO AddNewAccountAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		try {

			insQRY = "{call ACCOUNTSPKG.ADDNEWACCOUNTPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

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
			cstmt.setString(8, resultJson.getString("multiData1"));
			cstmt.setString(9,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.setString(11, ip);
			cstmt.setString(12, resultJson.getString("product"));
			cstmt.setString(13, resultJson.getString("prodesc"));
			cstmt.setString(14, resultJson.getString("apptype"));
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
	public ResponseDTO fetchServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		String detQry2 = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet rs = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		AccountBean accBean = null;
		PreparedStatement pstmt = null;

		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			connection = DBConnector.getConnection();

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.DATE_CREATED,'DD-MM-YYYY'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.STATUS,'L','Blocked','Active'),CM.M_PRD_CODE,CM.M_PRD_DESC,NVL(CM.USER_ID,' '),decode(CM.USSD_STATUS,'L','Blocked','Active'),decode(CM.MOBILE_STATUS,'L','Blocked','Active'),CM.ID,CM.STATUS "
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
				accBean.setUserid(detRS.getString(15));
				accBean.setUssdstatus(detRS.getString(16));
				accBean.setMobilestatus(detRS.getString(17));
				accBean.setId(detRS.getString(18));
				accBean.setStatus1(detRS.getString(19));
				flag = true;
			}
			detPstmt.close();
			detRS.close();
			
			detQry2="select to_char(TRANS_DTTM,'dd-mm-yyyy'),CHANNEL from DEBIT_CARD_INFO_TBL where CUSTID='"+accBean.getCustomercode()+"' and rownum=1 and CHANNEL is not null";
			detPstmt = connection.prepareStatement(detQry2);
			detRS = detPstmt.executeQuery();
			accBean.setSmsTemplate("No");
			if (detRS.next()) {
				accBean.setSmsTemplate("Yes");
				accBean.setStdate(detRS.getString(1));
				accBean.setSttime(detRS.getString(2));
			}

			detPstmt.close();
			detRS.close();
			
			detQry2="select ACCT_NO from MOB_ACCT_DATA WHERE APP_TYPE='MOBILE' AND CUST_ID=? AND PRIM_FLAG='P'";
			detPstmt = connection.prepareStatement(detQry2);
			detPstmt.setString(1, accBean.getId());
			detRS = detPstmt.executeQuery();
			if (detRS.next()) {
				accBean.setAccountno(detRS.getString(1));
			}

			logger.debug("Bean Details   :::: " + accBean);
			

			if (flag) {
				//if((accBean.getStatus1()).equalsIgnoreCase("U")){
				
				
				
				
				

				if((accBean.getUserid()).equalsIgnoreCase(" ")){
					
					
					// detQry1 =
					// "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE=?";
					detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ISO_CURRENCY_CODE,'-') FROM MOB_ACCT_DATA A WHERE A.APP_TYPE='MOBILE' AND A.CUST_ID=? AND PRIM_FLAG='P'";

					logger.debug("Acc details execution query [ " + detQry1 + " ]");

					detPstmt1 = connection.prepareStatement(detQry1);
					detPstmt1.setString(1, accBean.getId());
					//detPstmt1.setString(1, resultJson.getString("customercode"));
					// detPstmt1.setString(2, resultJson.getString("customerId"));

					rs = detPstmt1.executeQuery();

					int i = 0;
					eachrow = new StringBuilder(50);
					billerData = new StringBuilder(50);

					while (rs.next()) {
						eachrow.append(rs.getString(1)).append(",")
								.append(rs.getString(2)).append(",")
								.append(rs.getString(4)).append(",")
								.append(rs.getString(5)).append(",")
								
								// .append(rs.getString(6)).append(",")
								.append(rs.getString(6)).append(",")
						.append("MOBILE").append(",")
						.append("Active");
						if (i == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						i++;
						eachrow.delete(0, eachrow.length());
					}
				}else{
					
				
				
				detQry1 = "select ACCT_NO,nvl(ACCT_NAME,' '),nvl(BRANCH_CODE,' '),ACCT_TYPE,nvl(ALIAS_NAME,' '),APP_TYPE,decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active') from MOB_ACCT_DATA where CUST_ID=? AND APP_TYPE='MOBILE' AND ACCT_STATUS='L'";
				
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getId());

				logger.debug("Acc details execution query [ " + detQry1 + " ]");
				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				StringBuilder billerData1 = new StringBuilder(50);
				billerData1.append("#");
				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2).replace(',', ' '))
							.append(",").append(rs.getString(3)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5).replace(',', ' '))
							.append(",").append(rs.getString(6))
							.append(",").append(rs.getString(7));
					if (i == 0) {
						billerData1.append(eachrow);
					} else {
						billerData1.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
					
				}
				
				/*accBean.setMultiData1(billerData.toString());
				billerData.delete(0, eachrow.length());*/
				
				detPstmt1.close();
				rs.close();
			detQry1 = "select ACCT_NO,decode(ACCT_STATUS,'A','Active','L','Deactivate','F','Active','N','Un-Authorize','MU','De-Active') from MOB_ACCT_DATA where CUST_ID=? AND APP_TYPE='MOBILE'";
				
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getId());
				rs = detPstmt1.executeQuery();
				JSONObject json = new JSONObject();
				while (rs.next()) {
					json.put(rs.getString(1), rs.getString(2));
				}
	    	    //System.out.println("kailash here ::: "+json);
				JSONObject json1 =null;
				boolean serviceresult=false;
				try{
					json1 = JSONObject.fromObject(ServiceRequestClient.getCustomerAccountDetail(accBean.getUserid()));
					serviceresult=true;
				}catch(JSONException je){
					serviceresult=false;
					je.printStackTrace();
				}
				
				if(serviceresult){
					
				
				if((json1.get("respcode")).equals("00")){
				
				JSONArray jsonarray =  JSONArray.fromObject(json1.get("balInfo"));
				Iterator iterator = jsonarray.iterator();
				TreeSet<String> al=new TreeSet<String>();
				int i1 = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				
			       while (iterator.hasNext()) {
			    	   JSONObject jsonobj=(JSONObject)iterator.next();
				       
				       JSONArray jsonarray1 =  JSONArray.fromObject(jsonobj.get("custAcctInfo"));
						Iterator iterator1 = jsonarray1.iterator();
						 while (iterator1.hasNext()) {
					    	   JSONObject jsonobj1=(JSONObject)iterator1.next();
					    	    eachrow.append(jsonobj1.get("accountNo")).append(",");
								eachrow.append(jsonobj1.get("accountname")).append(",");
								eachrow.append(jsonobj1.get("brnCode")).append(",");
								eachrow.append(jsonobj1.get("accountproduct")).append(",");
								eachrow.append(jsonobj1.get("accountcurrency")).append(",");
					    	    eachrow.append("MOBILE").append(",");
					    	    if(json.get(jsonobj1.get("accountNo"))!=null){
					    	    	eachrow.append(json.get(jsonobj1.get("accountNo")));	
					    	    }else{
					    	    	eachrow.append("Active");
					    	    	
					    	    	pstmt = connection.prepareStatement("INSERT INTO  MOB_ACCT_DATA(ID,CUST_ID,ACCT_NO,ACCT_NAME,BRANCH_CODE,ACCT_TYPE,ALIAS_NAME,APP_TYPE,ACCT_STATUS,PRIM_FLAG,DATE_CREATED,CREATED_BY,ISO_CURRENCY_CODE,REG_STATUS,INSTITUTE,AUTH_FLAG) "
					    	    			+ "values(MOB_ACCT_DATA_ID_SEQ.NEXTVAL,?,?,?,?,?,?,'MOBILE','A','S',sysdate,'MISACC',?,'Registered','INSTID2','AUP')");
					    			
					    			
					    			pstmt.setString(1, accBean.getId());
					    			pstmt.setString(2, (String)jsonobj1.get("accountNo"));
					    			pstmt.setString(3, (String)jsonobj1.get("accountname"));
					    			pstmt.setString(4, (String)jsonobj1.get("brnCode"));
					    			pstmt.setString(5, (String)jsonobj1.get("accountproduct"));
					    			pstmt.setString(6, (String)jsonobj1.get("accountname"));
					    			pstmt.setString(7, (String)jsonobj1.get("accountcurrency"));
					    			
					    			pstmt.executeUpdate();
					    	    }
					    	    
						if (i1 == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						i1++;
						eachrow.delete(0, eachrow.length());
						 
						 }
				     
			       }
			       connection.commit();
			       billerData.append(billerData1);
				
				}else{
				

				
					detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ISO_CURRENCY_CODE,'-') FROM MOB_ACCT_DATA A WHERE A.APP_TYPE='MOBILE' AND A.CUST_ID=? AND PRIM_FLAG='P'";
	
					logger.debug("Acc details execution query [ " + detQry1 + " ]");
	
					detPstmt1 = connection.prepareStatement(detQry1);
					detPstmt1.setString(1, accBean.getId());
					//detPstmt1.setString(1, resultJson.getString("customercode"));
					// detPstmt1.setString(2, resultJson.getString("customerId"));
	
					rs = detPstmt1.executeQuery();
	
					int ii = 0;
					eachrow = new StringBuilder(50);
					billerData = new StringBuilder(50);
	
					while (rs.next()) {
						eachrow.append(rs.getString(1)).append(",")
								.append(rs.getString(2)).append(",")
								.append(rs.getString(4)).append(",")
								.append(rs.getString(5)).append(",")
								
								// .append(rs.getString(6)).append(",")
								.append(rs.getString(6)).append(",")
						.append("MOBILE").append(",")
						.append("Active");
						if (ii == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						ii++;
						eachrow.delete(0, eachrow.length());
					}
				
					
					
					}
				
				}else{
					
					

					

					
					detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ISO_CURRENCY_CODE,'-') FROM MOB_ACCT_DATA A WHERE A.APP_TYPE='MOBILE' AND A.CUST_ID=? AND PRIM_FLAG='P'";
	
					logger.debug("Acc details execution query [ " + detQry1 + " ]");
	
					detPstmt1 = connection.prepareStatement(detQry1);
					detPstmt1.setString(1, accBean.getId());
					//detPstmt1.setString(1, resultJson.getString("customercode"));
					// detPstmt1.setString(2, resultJson.getString("customerId"));
	
					rs = detPstmt1.executeQuery();
	
					int ii = 0;
					eachrow = new StringBuilder(50);
					billerData = new StringBuilder(50);
	
					while (rs.next()) {
						eachrow.append(rs.getString(1)).append(",")
								.append(rs.getString(2)).append(",")
								.append(rs.getString(4)).append(",")
								.append(rs.getString(5)).append(",")
								
								// .append(rs.getString(6)).append(",")
								.append(rs.getString(6)).append(",")
						.append("MOBILE").append(",")
						.append("Active");
						if (ii == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						ii++;
						eachrow.delete(0, eachrow.length());
					}
				
					
					
					
				 }
				}
				accBean.setMultiData(billerData.toString());
				billerData.delete(0, eachrow.length());

				logger.debug("biller details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);


				/*detQry1 = " select * from (select m,n,o,p,q,r,s,t from "
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
						+ "SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE=? ) n,DECODE(DETAIL_2,'PINRESEND','Pin Resend','USSD_ENABLED','Ussd Channel Enabled','USSD_DISABLED','Ussd Channel Disabled','MOBILE_ENABLED','Mobile Channel Enabled','MOBILE_DISABLED','Mobile Channel Disabled','DISABLECUSTOMER','Customer Disabled','ENABLECUSTOMER','Customer Enabled') o,'Completed' p,NET_ID q,"
						+ "' ' r,TXNDATE s,' ' t FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  in('PINRESEND','USSD_ENABLED','USSD_DISABLED','MOBILE_ENABLED','MOBILE_DISABLED','DISABLECUSTOMER','ENABLECUSTOMER')  "
						+ "UNION "
						+ "SELECT A.ACCT_NO m,nvl(A.ALIAS_NAME,' ') n,DECODE(B.AUTH_CODE,'DELACCAUTH','Account Deletion') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM MOB_ACCT_DATA_HIST A,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM "
						+ "AND A.CUST_id=(SELECT id FROM MOB_CUSTOMER_MASTER WHERE customer_code=? )) order by s desc ) WHERE rownum<6";*/
				
				detQry1 = "select * from (select m,n,o,p,q,r,s,t,u from( " 
						+ "SELECT A.customer_code m,nvl(a.first_name,' ') n,(select HEADING_NAMES from AUTH_MASTER AM where AM.AUTH_CODE=B.AUTH_CODE)||'-'||ACTION_DETAILS o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p, " 
						+ "B.MAKER_ID q,DECODE(B.CHECKER_ID,NULL,' ',B.CHECKER_ID) r,to_char(B.MAKER_DTTM,'dd-mm-yyyy hh24:mi:ss') s,DECODE(B.CHECKER_DTTM,NULL,' ',to_char(B.CHECKER_DTTM,'dd-mm-yyyy hh24:mi:ss')) t,B.MAKER_BRCODE u  " 
						+ "FROM MOB_CUSTOMER_MASTER_TEMP A,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM  " 
						+ "AND A.customer_code=?) order by s desc ) WHERE rownum<6 ";

				logger.debug("Cust iD" + accBean.getCustomercode());
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getCustomercode());
				/*detPstmt1.setString(2, accBean.getCustomercode());
				detPstmt1.setString(3, accBean.getCustomercode());
				detPstmt1.setString(4, accBean.getCustomercode());
				detPstmt1.setString(5, accBean.getCustomercode());*/

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

				accBean.setMultiData1(billerData.toString());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details " + billerData);

				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closeResultSet(rs);

				detQry1 = " select * from (select nvl(MAC_ADDR,' '),nvl(DEVICE_IP,' '),nvl(IMEI_NO,' '),nvl(SERIAL_NO,' '),nvl(VERSION,' '),nvl(replace(DEVICE_TYPE,',','-'),' '),nvl(OS_TYPE,' '),decode(STATUS,'A','Active','Deactivate') from MOB_IMEI_DATA where UPPER(USER_ID)=UPPER('"+accBean.getUserid()+"') order by TRANS_DTTM desc )WHERE rownum<6";

				detPstmt1 = connection.prepareStatement(detQry1);
				//detPstmt1.setString(1, accBean.getCustomercode());

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
				/*AND CUSTOMER_CODE=?*/
				/*detQry1 = "select * from (select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss'),RESPONSEMESSAGE from FUND_TRANSFER_TBL "
						+ "WHERE RESPONSECODE='00' and ( UPPER(USERID) in (Select UPPER(USER_ID) from MOB_CUSTOMER_MASTER where CUSTOMER_CODE=?) or "
						+ "USERID in (Select MCI.MOBILE_NUMBER from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCI where MCI.CUST_ID=MCM.ID AND MCM.CUSTOMER_CODE=?)) order by TRANS_DATE desc)  WHERE rownum<6";*/
				
				detQry1 = "select * from (select ACCOUNTNO,TRNS_AMT,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss'),RESPONSEMESSAGE from FUND_TRANSFER_TBL "
						+ "WHERE RESPONSECODE='00' and UPPER(USERID)in (UPPER('"+accBean.getUserid()+"'),'"+accBean.getTelephone()+"') order by TRANS_DATE desc)  WHERE rownum<6";
				
				detPstmt1 = connection.prepareStatement(detQry1);
				//detPstmt1.setString(1, accBean.getCustomercode());
				//detPstmt1.setString(2, accBean.getCustomercode());

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
							/*.append(rs.getString(6)).append(",")*/
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
			responseDTO.addError("An error has occurred ("+e.getMessage()+"). please contact your administrator.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("internal service error occurred . please contact your administrator.");
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
	
	
	public ResponseDTO fetchCustomerDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		String detQry1 = "";
		String detQry2 = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		ResultSet rs = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		AccountBean accBean = null;
		PreparedStatement pstmt = null;

		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			connection = DBConnector.getConnection();

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.DATE_CREATED,'DD-MM-YYYY'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.STATUS,'L','Blocked','Active'),CM.M_PRD_CODE,CM.M_PRD_DESC,NVL(CM.USER_ID,' '),decode(CM.USSD_STATUS,'L','Blocked','Active'),decode(CM.MOBILE_STATUS,'L','Blocked','Active'),CM.ID,CM.STATUS "
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
				accBean.setUserid(detRS.getString(15));
				accBean.setUssdstatus(detRS.getString(16));
				accBean.setMobilestatus(detRS.getString(17));
				accBean.setId(detRS.getString(18));
				accBean.setStatus1(detRS.getString(19));
				
			}
			detPstmt.close();
			detRS.close();
			
			
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("An error has occurred ("+e.getMessage()+"). please contact your administrator.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("internal service error occurred . please contact your administrator.");
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
	
	
	public ResponseDTO fetchServiceDetailsWallet(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.BALANCE,"
					+ "to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.W_STATUS,'L','Blocked','Active'),CM.W_PRD_CODE,CM.W_PRD_DESC,NVL(CM.USER_ID,' '),decode(CM.W_USSD_STATUS,'L','Blocked','Active'),decode(CM.W_MOBILE_STATUS,'L','Blocked','Active'),CM.ID,CM.STATUS "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
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
				accBean.setUserid(detRS.getString(15));
				accBean.setUssdstatus(detRS.getString(16));
				accBean.setMobilestatus(detRS.getString(17));
				accBean.setId(detRS.getString(18));
				accBean.setStatus1(detRS.getString(19));
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {
				detQry1 = "select ACCT_NO,nvl(ACCT_NAME,' '),nvl(BRANCH_CODE,' '),ACCT_TYPE,BALANCE,APP_TYPE,decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active') from WALLET_ACCT_DATA where CUST_ID=(select ID from MOB_CUSTOMER_MASTER where CUSTOMER_CODE=?)";
				
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
							.append(",").append(rs.getString(6))
							.append(",").append(rs.getString(7));
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
						+ "FROM WALLET_ACCT_DATA_TEMP A ,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('WACCACTDCT') "
						+ "AND A.CUST_ID =(SELECT ID FROM MOB_customer_master WHERE customer_code=? ) "
						+ "UNION "
						+ "SELECT A.CUSTOMER_CODE m,A.FIRST_NAME n,DECODE(B.AUTH_CODE,'WACCTPINRESET','Pin Reset','WMODCUSTDETAUTH','Customer Details Modify') o,"
						+ "DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,"
						+ "DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t FROM MOB_CUSTOMER_MASTER_TEMP A,AUTH_PENDING B WHERE A.REF_NUM =B.REF_NUM "
						+ "AND B.AUTH_CODE IN('WACCTPINRESET','WMODCUSTDETAUTH') AND A.CUSTOMER_CODE=? "
						+ "UNION "
						+ "SELECT DETAIL_3 m,(SELECT FIRST_NAME FROM MOB_CUSTOMER_MASTER WHERE CUSTOMER_CODE=? ) n,DECODE(DETAIL_2,'PINRESEND','Pin Resend','WUNBLOCKPIN','Pin Unblock','WDISABLECUSTOMER','Customer Disabled','WENABLECUSTOMER','Customer Enabled') o,'Completed' p,NET_ID q,"
						+ "' ' r,TXNDATE s,' ' t FROM AUDIT_DATA WHERE DETAIL_3=? AND DETAIL_2  in('WPINRESEND','WUNBLOCKPIN','WDISABLECUSTOMER','WENABLECUSTOMER')  "
						+ "UNION "
						+ "SELECT A.ACCT_NO m,nvl(A.ALIAS_NAME,' ') n,DECODE(B.AUTH_CODE,'WDELACCAUTH','Account Deletion') o,DECODE(B.STATUS,'C','AUTHORIZED','PENDING') p,"
						+ "B.MAKER_ID q,DECODE(A.AUTHID,NULL,' ',A.AUTHID) r,B.MAKER_DTTM s,DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM) t "
						+ "FROM WALLET_ACCT_DATA_HIST A,AUTH_PENDING B WHERE A.REF_NUM  =B.REF_NUM AND B.AUTH_CODE IN('WDELACCAUTH') "
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

				//detQry1 = "select POSTING_RRN,(SELECT SERVICE_NAME FROM mob_service_master C WHERE C.SERVICE_CODE=T.TXN_TYPE) TXN_TYPE,MSISDN,to_date(TXN_TIME,'DD-MM-YYYY HH24:MI:SS') TXN_TIME,DEBIT_AC,(select CHANNEL_NAME from mob_channels MC where MC.ID=T.CHANNELID)  CHANNELID,DECODE(STATUS,'F','Failed','P','Pending','S','SUCCESS') STATUS,STATUS_DESC,NARRATION from TRAN_LOG T where MSISDN=(select '254'||mobile_number from mob_contact_info where cust_id=(select id from mob_customer_master where customer_code=?)) and rownum<6 order by TXN_TIME desc ";
				detQry1 = "select ACCOUNT,AMOUNT,DECODE(DRCR_FLAG,'C','Credit','D','Debit'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE AND APPLICATION='AGENT'),TXN_REF_NO,CHANNEL,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NARRATION from WALLET_FIN_TXN_POSTING WFTP where ACCOUNT in (select ACCT_NO from WALLET_ACCT_DATA WAD,MOB_CUSTOMER_MASTER MCM where WAD.cust_id=MCM.id AND MCM.customer_code=?)";

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
							.append(rs.getString(8));
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
	
	
	public ResponseDTO CustomerEnableDetails(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.STATUS,'L','Blocked','Active'),CM.M_PRD_CODE,CM.M_PRD_DESC,DECODE(CM.USSD_STATUS,'L','Blocked','Active'),DECODE(CM.MOBILE_STATUS,'L','Blocked','Active') "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID "
					+ "and CA.rowid = (select max(rowid) from MOB_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("accountid"));
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
				accBean.setUssdstatus(detRS.getString(15));
				accBean.setMobilestatus(detRS.getString(16));
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
	
	public ResponseDTO walletCustomerEnableDetails(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,DECODE(CI.MOBILE_OPERATOR,'OPID1','Safaricom','OPID2','Airtel','OPID3','Orange'),CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,DECODE(CA.INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
					+ "to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),(select CUSTOMERPINSTATUSNAME from MOB_CUSTOMERPINSTATUS PC where PC.CUSTOMERPINSTATUSID=CM.PIN_STATUS) PINSTATUS,DECODE(CM.W_STATUS,'L','Blocked','Active'),CM.W_PRD_CODE,CM.W_PRD_DESC,DECODE(CM.W_USSD_STATUS,'L','Blocked','Active'),DECODE(CM.W_MOBILE_STATUS,'L','Blocked','Active') "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID "
					+ "and CA.rowid = (select max(rowid) from WALLET_ACCT_DATA mt where mt.cust_id=cm.id) AND CM.CUSTOMER_CODE=?";

			logger.debug("First Query Executed" + detQry);
			detPstmt = connection.prepareStatement(detQry);
			detPstmt.setString(1, resultJson.getString("accountid"));
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
				accBean.setUssdstatus(detRS.getString(15));
				accBean.setMobilestatus(detRS.getString(16));
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

				detQry = "SELECT (SELECT CUSTOMER_CODE FROM MOB_CUSTOMER_MASTER WHERE ID=MA.CUST_ID) CUSTID,"
						+ "ACCT_NO,nvl(ALIAS_NAME,' '),nvl(ACCT_NAME,' '),BRANCH_CODE,ACCT_TYPE,ACCT_STATUS,"
						+ "DECODE(INSTITUTE,'INSTID2','FCUBS') INSTITUTE,"
						+ "DATE_CREATED,CREATED_BY,"
						+ "decode(ACCT_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active')"
						+ " FROM MOB_ACCT_DATA MA where acct_no=?";

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

			}  else if (type.equalsIgnoreCase("passwordreset")) {
				
				detQry = "SELECT MCI.MOBILE_NUMBER,MCM.USER_ID FROM MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCI WHERE MCM.ID=MCI.CUST_ID AND MCI.APP_TYPE='MOBILE' AND MCM.customer_code=?";
				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, accid);
				detRS = detPstmt.executeQuery();
				accBean = new AccountBean();
				
				if (detRS.next()) {
					accBean.setMobno(detRS.getString(1));
					accBean.setUserid(detRS.getString(2));
				}
				
				String newpass=CommonServiceDao.getGenPass();
				
				JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.ChangePassword(newpass, accBean.getUserid(), "MOBILE"));
				if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
					ServiceRequestClient.sms(accBean.getMobno(), "Dear "+accBean.getUserid()+", Password Reset successful ,Please find the New Password  "+newpass);
				}
				

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
			}else if (type.equalsIgnoreCase("resendpin")) {

				detQry1 = "select pin from MOB_CUSTOMER_MASTER where CUSTOMER_CODE=?";
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
			}  else if (type.equalsIgnoreCase("chanelenbdismb")) {

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
			}else if (type.equalsIgnoreCase("chanelenbdisussd")) {

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
			}  else if (type.equalsIgnoreCase("customerchanelenbdismb")) {

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
			}else if (type.equalsIgnoreCase("customerchanelenbdisussd")) {

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
			}else if (type.equalsIgnoreCase("unblockpin")) {

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
			} else if (type.equalsIgnoreCase("enabledisablecust")) {

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
			}else if (type.equalsIgnoreCase("disablecust")) {

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

				detQry = "select MAC_ADDR,DEVICE_IP,IMEI_NO,SERIAL_NO,VERSION,DEVICE_TYPE,OS_TYPE,decode(STATUS,'A','Active','L','Lock') "
						+ "from MOB_IMEI_DATA where STATUS='A' AND UPPER(USER_ID)=(select UPPER(USER_ID) from mob_customer_master where CUSTOMER_CODE=?)";

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
	
	
	public ResponseDTO fetchwalletActionresult(RequestDTO requestDTO) {

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

				detQry = "SELECT (SELECT CUSTOMER_CODE FROM MOB_CUSTOMER_MASTER WHERE ID=MA.CUST_ID) CUSTID,"
						+ "ACCT_NO,nvl(ALIAS_NAME,' '),nvl(ACCT_NAME,' '),BRANCH_CODE,ACCT_TYPE,ACCT_STATUS,"
						+ "'WALLET',"
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

			}  else if (type.equalsIgnoreCase("passwordreset")) {
				
				detQry = "SELECT MCI.MOBILE_NUMBER,MCM.USER_ID,MCM.CUST_TYPE,MCM.FIRST_NAME FROM MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCI WHERE MCM.ID=MCI.CUST_ID  AND MCM.customer_code=?";
				detPstmt = connection.prepareStatement(detQry);
				detPstmt.setString(1, accid);
				detRS = detPstmt.executeQuery();
				accBean = new AccountBean();
				String custtype="M";
				
				if (detRS.next()) {
					accBean.setMobno(detRS.getString(1));
					accBean.setUserid(detRS.getString(2));
					custtype=detRS.getString(3);
					accBean.setFullname(detRS.getString(4));
				}
				
				
					String newpass=CommonServiceDao.getGenPass();
					String enpass=CommonServiceDao.b64_sha256(newpass);
					/*
					
					ServiceRequestClient.sms(accBean.getMobno(), "Dear "+accBean.getFullname()+", Password Reset successful ,Please find the New Password  "+newpass);*/
					
					detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

					responseDTO = new ResponseDTO();
					requestJSON = requestDTO.getRequestJSON();
					resultJson = (JSONObject) requestJSON.get("accBean");

				

					cstmt = connection.prepareCall(detQry);
					cstmt.setString(1, accid);
					cstmt.setString(2, type);
					cstmt.setString(3, enpass);
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
				
				
				
				
				

			
			}else if (type.equalsIgnoreCase("resendpin")) {

				detQry1 = "select pin from MOB_CUSTOMER_MASTER where CUSTOMER_CODE=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accid);
				detRS1 = detPstmt1.executeQuery();

				if (detRS1.next()) {
					encpin = detRS1.getString(1).trim();
				}
				decpin = aesDecString(encpin);
				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";
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

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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
			}  else if (type.equalsIgnoreCase("chanelenbdismb")) {

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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
			}else if (type.equalsIgnoreCase("chanelenbdisussd")) {

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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

				detQry = "{call WALLETACCOUNTSPKG.RESETPIN(?,?,?,?,?,?,?,?,?)}";

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

				detQry = "select MAC_ADDR,DEVICE_IP,IMEI_NO,SERIAL_NO,VERSION,DEVICE_TYPE,OS_TYPE,decode(STATUS,'A','Active','L','Lock') "
						+ "from MOB_IMEI_DATA where STATUS='A' AND UPPER(USER_ID)=(select UPPER(USER_ID) from mob_customer_master where CUSTOMER_CODE=?)";

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

			detQry = "{call ACCOUNTSPKG.INSERTACCSTATUS(?,?,?,?,?,?)}";

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
	
	
	public ResponseDTO insertwalletactdeactdetails(RequestDTO requestDTO) {

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
			logger.debug("SQLException in wallet Account Status Details ["
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
	

	public ResponseDTO insertimeistatus(RequestDTO requestDTO) {

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
		String type = null;
		String type1 = null;
		String ip = null;
		String makerid = null;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			type = resultJson.getString("customercode");
			logger.debug("IN " + type1 + "SIDE INSERT" + type + " IMEI" + accid
					+ " STATUS");

			connection = DBConnector.getConnection();

			logger.debug("IN SIDE INSERT IMEI STATUS");

			detQry = "{call ACCOUNTSPKG.INSERTIMEISTATUS(?,?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			cstmt = connection.prepareCall(detQry);
			cstmt.setString(1, type);
			cstmt.setString(2, makerid);
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

	public ResponseDTO insertbulkfile(List<String> extdata,RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		
		Connection con = null;
		CallableStatement cstmt = null;
		
		AccountBean accBean = null;
		String ip = null;
		String makerid = null;
		
		String errorMessage = "";
		String arrayData[] = null;
		String fname = null;
		String updateUserDet = "{call ACCOUNTSPKG.INSERTBULKDATA(?,?,?,?,?)}";
		
		DatabaseMetaData dmd = null;
		Connection metaDataConnection = null;
		OracleConnection oraConnection = null;

		ArrayDescriptor des = null;
		ARRAY array_to_pass = null;

		try {
			
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			fname = requestJSON.getString("fname");
			arrayData = extdata.toArray(new String[extdata.size()]);

			requestJSON = new JSONObject();

			ip = ServletActionContext.getRequest().getRemoteAddr();
			con = DBConnector.getConnection();

			dmd = con.getMetaData();

			if (dmd != null) {
				metaDataConnection = dmd.getConnection();
			}

			if (!(metaDataConnection instanceof OracleConnection)) {
				throw new Exception("This is not oracle instance.....");
			}

			oraConnection = (OracleConnection) metaDataConnection;
			des = ArrayDescriptor.createDescriptor("ARRAY_TABLE", oraConnection);
			array_to_pass = new ARRAY(des, oraConnection, arrayData);
			
			logger.debug("Array Prepared before sending to Block [" + array_to_pass + "]");
			cstmt = oraConnection.prepareCall(updateUserDet);
			cstmt.setArray(1, array_to_pass);
			cstmt.setString(2, fname);
			cstmt.setString(3, makerid);
			cstmt.setString(4, ip);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			cstmt.execute();

			errorMessage = cstmt.getString(5);
			logger.debug("errorMessage [" + errorMessage + "]");

			if (errorMessage.contains("success")) {
				responseDTO.addMessages(errorMessage);
			} else {
				responseDTO.addError(errorMessage);
			}

			/*accBean.setCustomerstatus("U");
			
			accBean.setCustomerId(sss);*/
			
			accBean.setFullname(fname);
			
			detMap.put("AccountData",accBean);
			responseDTO.setData(detMap);

		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(con);


		}

		return responseDTO;

	}

	public ResponseDTO validatebulkfile(List<String> extdata,
			RequestDTO requestDTO) {

		logger.debug("Inside Account Creation.. ");
		HashMap<String, Object> detMap = null;

		Connection con = null;
		CallableStatement callableStatement = null;
		
		String ip = null;
		String makerid = null;
		AccountBean accBean = null;
		String fname = null;

		String errorMessage = "";
		String arrayData[] = null;
		String alldata[] = null;
		String sss = null;
		
		String updateUserDet = "{call ACCOUNTSPKG.VALIDATEBULKFILE(?,?,?,?,?)}";

		DatabaseMetaData dmd = null;
		Connection metaDataConnection = null;
		OracleConnection oraConnection = null;

		ArrayDescriptor des = null;
		ARRAY array_to_pass = null;
		ARRAY array_ret = null;

		try {

			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			
			

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			fname = requestJSON.getString("fname");
			arrayData = extdata.toArray(new String[extdata.size()]);

			requestJSON = new JSONObject();

			ip = ServletActionContext.getRequest().getRemoteAddr();
			con = DBConnector.getConnection();

			dmd = con.getMetaData();

			if (dmd != null) {
				metaDataConnection = dmd.getConnection();
			}

			if (!(metaDataConnection instanceof OracleConnection)) {
				throw new Exception("This is not oracle instance.....");
			}

			oraConnection = (OracleConnection) metaDataConnection;
			des = ArrayDescriptor.createDescriptor("ARRAY_TABLE", oraConnection);
			array_to_pass = new ARRAY(des, oraConnection, arrayData);
			
			logger.debug("Array Prepared before sending to Block [" + array_to_pass + "]");
			callableStatement = oraConnection.prepareCall(updateUserDet);
			callableStatement.setArray(1, array_to_pass);
			callableStatement.setString(2, makerid);
			callableStatement.setString(3, ip);
			callableStatement.registerOutParameter(4, java.sql.Types.ARRAY,"ARRAY_TABLE");
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStatement.execute();

			array_ret = ((OracleCallableStatement) callableStatement).getARRAY(4);
			logger.debug("Return Value from DB [" + array_ret + "]");
			alldata=(String[]) array_ret.getArray();
			
		    List<String> list = new ArrayList<String>();

		    for(String s : alldata) {
		       if(s != null && s.length() > 0) {
		          list.add(s);
		       }
		    }

		    alldata = list.toArray(new String[list.size()]);
		
			sss=StringUtils.join(alldata,' ');
			//System.out.println("JC TEST VALUES        ["+sss);
			sss=sss.replaceAll("null,","");
			
			/*System.out.println("JC TEST VALUES        ["+sss);*/
			
			System.out.println("sss values"+sss+"length ="+sss.length());
			if(sss.length()>0){
			sss=sss.substring(0,sss.length()-1);
			}
			logger.debug("Data Before Sending to JSP [" + sss + "]");
			
			errorMessage = callableStatement.getString(5);
			logger.debug("errorMessage [" + errorMessage + "]");

			if (errorMessage.contains("success")) {
				responseDTO.addMessages(errorMessage);
			} else {
				responseDTO.addError(errorMessage);
			}

			accBean.setCustomerstatus("U");
			accBean.setFullname(fname);
			accBean.setCustomerId(sss);
			
			detMap.put("AccountData",accBean);
			responseDTO.setData(detMap);

		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(con);

		}

		return responseDTO;

	}

	public ResponseDTO deleteAccounts(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		JSONObject resultJson = null;
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet detRS = null;
		
		AccountBean accBean = null;
		String type = null;
		String type1 = null;
		String ip = null;
		String makerid = null;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			type = resultJson.getString("customercode");

			logger.debug("IN " + type1 + "SIDE INSERT" + type + " IMEI STATUS");

			connection = DBConnector.getConnection();

			logger.debug("Inside Delete Accounts");
			

			

			detQry = "{call ACCOUNTSPKG.DELETEACCOUNTS(?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			logger.debug("comments recieved"+resultJson.getString("comment"));

			cstmt = connection.prepareCall(detQry);
			cstmt.setString(1, type);
			cstmt.setString(2, makerid);
			cstmt.setString(3, ip);
			cstmt.setString(4, resultJson.getString("multiData"));
			cstmt.registerOutParameter(5, Types.VARCHAR);
			

			cstmt.executeQuery();

			accBean.setFullname(cstmt.getString(5));

			// accBean.setTelephone(cstmt.getString(5));

			logger.debug("Accout status Return: STATUS :......."
					+ cstmt.getString(5));

			if (!cstmt.getString(5).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(5));
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
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(cstmt);
			detQry = null;

		}

		return responseDTO;

	}

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,NVL(CM.USER_ID,' '),CM.STATUS,CM.SMSTOKEN "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setSmstoken(detRS.getString(15));
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {

				logger.debug("into acc details");
				detPstmt.close();
				detRS.close();
				
				/*String entityQry="Select b.TOKEN_VALUE,b.PERDAY_LIMIT,b.CHANNEL_PERDAY_LIMIT from product b where  b.PRD_CODE=?";
				detPstmt = connection.prepareStatement(entityQry);
				detPstmt.setString(1,accBean.getProduct());
				detRS = detPstmt.executeQuery();
				 
				while (detRS.next()) {
					accBean.setTokenamt(detRS.getString(1));
					accBean.setPdaylimitamt(detRS.getString(2));
					accBean.setPlimitchannel(detRS.getString(3));
				}
				*/
				
				//System.out.println("kailash here ::: "+accBean.getUserid());  
				
				//if((accBean.getStatus()).equalsIgnoreCase("U")){
				if((accBean.getUserid()).equalsIgnoreCase(" ")){ 
				
				
				// detQry1 =
				// "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE=?";
				detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ISO_CURRENCY_CODE,'-') FROM MOB_ACCT_DATA A WHERE A.APP_TYPE='MOBILE' AND A.CUST_ID=(select ID from MOB_customer_master where customer_code=? )";

				logger.debug("Acc details execution query [ " + detQry1 + " ]");

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customercode"));
				// detPstmt1.setString(2, resultJson.getString("customerId"));

				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							// .append(rs.getString(6)).append(",")
							.append(rs.getString(6));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}
				}else{
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getCustomerAccountDetail(accBean.getUserid()));
				JSONArray jsonarray =  JSONArray.fromObject(json1.get("balInfo"));
				Iterator iterator = jsonarray.iterator();
				TreeSet<String> al=new TreeSet<String>();
				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				
			       while (iterator.hasNext()) {
			    	   JSONObject jsonobj=(JSONObject)iterator.next();
				       
				       JSONArray jsonarray1 =  JSONArray.fromObject(jsonobj.get("custAcctInfo"));
						Iterator iterator1 = jsonarray1.iterator();
						 while (iterator1.hasNext()) {
					    	   JSONObject jsonobj1=(JSONObject)iterator1.next();
					    	   //System.out.println(jsonobj1);
					    	   //System.out.println(jsonobj1.get("accountNo")+"--"+jsonobj1.get("accountname")+"--"+jsonobj1.get("brnCode")+"--"+jsonobj1.get("accountproduct")+"--"+jsonobj1.get("accountstatus"));
					    	   eachrow.append(jsonobj1.get("accountNo")).append(",")
								.append(jsonobj1.get("accountname")).append(",")
								.append(jsonobj1.get("brnCode")).append(",")
								.append(jsonobj1.get("accountproduct")).append(",")
								//.append(jsonobj1.get("accountcurrency")).append(",")
								
								// .append(rs.getString(6)).append(",")
								.append(jsonobj1.get("accountcurrency"));
						if (i == 0) {
							billerData.append(eachrow);
						} else {
							billerData.append("#").append(eachrow);
						}
						i++;
						eachrow.delete(0, eachrow.length());
						 
						 }
				     
			       }
				}

				accBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "
						+ billerData);
				detMap.put("AccountData", accBean);

				billerData.delete(0, eachrow.length());
			} else {
				logger.debug("no records fetched from query");
				responseDTO.addError("Invalid Customer ID.");
			}

			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("An error has occurred ("+e.getMessage()+"). please contact your administrator.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("internal service error occurred . please contact your administrator.");
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

	
	public ResponseDTO fetchRegCustDetailsModify(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,NVL(CM.USER_ID,' '),CM.STATUS,CM.SMSTOKEN "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setSmstoken(detRS.getString(15));
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {

				logger.debug("into acc details");
				detPstmt.close();
				detRS.close();
				
				
				
				//if((accBean.getStatus()).equalsIgnoreCase("U")){
				
				
				
				// detQry1 =
				// "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE=?";
				detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ISO_CURRENCY_CODE,'-') FROM MOB_ACCT_DATA A WHERE A.APP_TYPE='MOBILE' AND PRIM_FLAG='P' AND A.CUST_ID=(select ID from MOB_customer_master where customer_code=? )";

				logger.debug("Acc details execution query [ " + detQry1 + " ]");

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customercode"));
				// detPstmt1.setString(2, resultJson.getString("customerId"));

				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							// .append(rs.getString(6)).append(",")
							.append(rs.getString(6));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}
				

				accBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "
						+ billerData);
				detMap.put("AccountData", accBean);

				billerData.delete(0, eachrow.length());
			} else {
				logger.debug("no records fetched from query");
				responseDTO.addError("Invalid Customer ID.");
			}

			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("An error has occurred ("+e.getMessage()+"). please contact your administrator.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("internal service error occurred . please contact your administrator.");
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

	
	
	public ResponseDTO fetchRegCustDetailsEnhancement(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,CM.USER_ID,CM.STATUS,CM.ID "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setId(detRS.getString(15));
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);
			detPstmt.close();
			detRS.close();
			if (flag){
				String entityQry="Select b.TOKEN_VALUE,b.PERDAY_LIMIT,b.CHANNEL_PERDAY_LIMIT,USSD_SECOND_FA_AMT from product b where  b.PRD_CODE=?";
				detPstmt = connection.prepareStatement(entityQry);
				detPstmt.setString(1,accBean.getProduct());
				detRS = detPstmt.executeQuery();
				 
				while (detRS.next()) {
					accBean.setTokenamt(detRS.getString(1));
					accBean.setPdaylimitamt(detRS.getString(2));
					accBean.setPlimitchannel(detRS.getString(3));
					accBean.setSecfalimit(detRS.getString(4));
				}
				
				detPstmt.close();
				detRS.close();
				
				
				detPstmt = connection.prepareStatement("Select count(*) from MOB_CUSTOMER_LIMIT where  CUST_ID=?");
				detPstmt.setString(1,accBean.getId());
				detRS = detPstmt.executeQuery();
				 
				while (detRS.next()) {
					if((detRS.getString(1)).equalsIgnoreCase("0")){
						accBean.setCustperdaylimit(accBean.getPdaylimitamt());
						accBean.setClimitchannel(accBean.getPlimitchannel());
					}else{
						detPstmt1 = connection.prepareStatement("Select CUST_PERDAY_LIMIT,CUST_CHANNEL_LIMIT,USSD_SECOND_FA_AMT from MOB_CUSTOMER_LIMIT where  CUST_ID=?");
						detPstmt1.setString(1,accBean.getId());
						detRS2 = detPstmt1.executeQuery();
						while (detRS2.next()) {
							accBean.setCustperdaylimit(detRS2.getString(1));
							accBean.setClimitchannel(detRS2.getString(2));
							accBean.setSecfalimit(detRS2.getString(3));
						}
					}
					
				}
				
				detMap.put("AccountData", accBean);
			}else{	
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
	
	public ResponseDTO fetchRegCustDetailsFraud(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,CM.USER_ID,CM.STATUS,CM.ID "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setId(detRS.getString(15));
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);
			detPstmt.close();
			detRS.close();
			if (flag){
				
				detQry1 = "select FRAUD_ID,FRAUD_DESC,'Active' from FRAUD_ASSIGN where PRODUCT_CODE=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getProduct());
				rs = detPstmt1.executeQuery();
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				int ii = 0;
				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
					.append(rs.getString(2)).append(",")
					.append(rs.getString(3));
					if (ii == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					ii++;
					eachrow.delete(0, eachrow.length());
				}
				accBean.setMultiData(billerData.toString());
				detMap.put("AccountData", accBean);
				
			}else{	
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

	
	public ResponseDTO fetchRegCustDetailsFraudConfirm(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,CM.USER_ID,CM.STATUS,CM.ID "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setId(detRS.getString(15));
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);
			detPstmt.close();
			detRS.close();
			if (flag){
				
				detQry1 = "select FRAUD_ID,FRAUD_DESC,'Active' from FRAUD_ASSIGN where PRODUCT_CODE=?";
				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, accBean.getProduct());
				rs = detPstmt1.executeQuery();
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				int ii = 0;
				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
					.append(rs.getString(2)).append(",")
					.append(rs.getString(3));
					if (ii == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					ii++;
					eachrow.delete(0, eachrow.length());
				}
				accBean.setMultiData(billerData.toString());
				detMap.put("AccountData", accBean);
				
			}else{	
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
	
	public ResponseDTO walletfetchRegCustomerDetails(RequestDTO requestDTO) {

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

			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.W_PRD_CODE,CM.W_PRD_DESC "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,WALLET_ACCT_DATA CA "
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
				accBean.setApptype("WALLET");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);

			if (flag) {

				logger.debug("into acc details");
				// detQry1 =
				// "select CUSTOMERCODE,ACCOUNTID,PRODUCTID,FULLNAME,CURRENCY,DORMANTSTATUS,BRANCHCODE,Decode ((SELECT COUNT(*) FROM MOB_ACCT_DATA where CUST_ID =?),0,'Not Registered','Registered') status from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE=?";
				detQry1 = "SELECT A.ACCT_NO,NVL(A.ACCT_NAME,' '),DECODE(A.AUTHDTTM,NULL,' ',A.AUTHDTTM),NVL(A.BRANCH_CODE,' '),NVL(A.ACCT_TYPE,' '),NVL(A.ALIAS_NAME,'-') FROM WALLET_ACCT_DATA A WHERE A.APP_TYPE='WALLET' AND A.CUST_ID=(select ID from MOB_customer_master where customer_code=? )";

				logger.debug("Acc details execution query [ " + detQry1 + " ]");

				detPstmt1 = connection.prepareStatement(detQry1);
				detPstmt1.setString(1, resultJson.getString("customercode"));
				// detPstmt1.setString(2, resultJson.getString("customerId"));

				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							// .append(rs.getString(6)).append(",")
							.append(rs.getString(6));
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "
						+ billerData);
				detMap.put("AccountData", accBean);

				billerData.delete(0, eachrow.length());
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

	public ResponseDTO fetchAllDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		StringBuilder detQry = null;
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		ResultSet rs = null;
		
		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();

			detQry = new StringBuilder(50);
			detQry.append("select MA.CUSTOMER_CODE,MA.FIRST_NAME,decode(AC.INSTITUTE,'INSTID1','BFUB','INSTID2','IMAL/AMANAH'),AC.ACCT_TYPE,");
			detQry.append("AC.ACCT_NO,MT.ISO_COUNTRY_CODE||MT.MOBILE_NUMBER,MA.CREATED_BY,to_char(MA.DATE_CREATED,'DD-MON-YYYY HH24:MI:SS'),");
			detQry.append("MA.AUTHID,to_char(MA.AUTHDTTM,'DD-MON-YYYY HH24:MI:SS'),");
			detQry.append("decode(AP.STATUS,'C','Completed','P','Pending','R','Rejected') ");
			detQry.append("from MOB_CUSTOMER_MASTER_TEMP MA,AUTH_PENDING AP,MOB_CONTACT_INFO_TEMP MT,MOB_ACCT_DATA_TEMP AC ");
			detQry.append("where MA.REF_NUM=AP.REF_NUM and MA.ID=MT.CUST_ID and MA.ID=AC.CUST_ID and MT.CUST_ID=AC.CUST_ID ");
			detQry.append("and ap.ref_num=ac.ref_num order by AP.MAKER_DTTM desc,AP.Status");

			detPstmt = connection.prepareStatement(detQry.toString());
			rs = detPstmt.executeQuery();

			int i = 0;
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
						.append(rs.getString(8)).append(",")
						.append(rs.getString(9)).append(",")
						.append(rs.getString(10)).append(",")
						.append(rs.getString(11));

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
			logger.debug("Bean Details in Get All Details :::: " + accBean);

			detMap.put("AccountData", accBean);

			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Fetching Details [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Fetching Detials [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeConnection(connection);

			detQry = null;

		}

		return responseDTO;

	}

	public ResponseDTO fetchAccountdetails(RequestDTO requestDTO) {

		logger.debug("Inside Get Account   Details .. ");
		HashMap<String, Object> maap = null;
		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray accjsonArray = null;

		PreparedStatement pstmt = null;
		ResultSet rS = null;
		Connection connection = null;

		String Qry = "";
		String Qry1 = "";
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();
			accjsonArray = new JSONArray();

			maap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			resultJson = (JSONObject) requestDTO.getRequestJSON();

			Qry = "SELECT MCM.CUSTOMER_CODE, MCM.FIRST_NAME,MCD.INSTITUTE,MCD.ACCT_TYPE,MCD.ACCT_NO,MCI.MOBILE_NUMBER,"
					+ "nvl(AP.MAKER_ID,' '),nvl((to_char(AP.MAKER_DTTM,'DD-MON-YYYY')),' '),nvl(MCM.AUTHID,' '),nvl((to_char(MCM.AUTHDTTM,'DD-MON-YYYY')),' '),"
					+ "nvl(DECODE(MCM.STATUS,'A','Active','B','Inactive','N','Un-Authorize','R','Authorization Rejected'),' '),"
					+ "nvl(Decode(AP.STATUS,'C','Completed','P','Pending','R','Rejected'),' ') FROM MOB_CONTACT_INFO_TEMP MCI,MOB_ACCT_DATA_TEMP MCD,MOB_CUSTOMER_MASTER_TEMP MCM,AUTH_PENDING AP"
					+ " WHERE MCM.REF_NUM=AP.REF_NUM AND MCM.ID  = MCI.CUST_ID AND MCM.ID  = MCD.CUST_ID AND MCI.CUST_ID = MCD.CUST_ID AND AP.REF_NUM = MCD.REF_NUM";

			if (resultJson.getString("accDetails").equalsIgnoreCase(
					"CustomerId")) {

				Qry1 = Qry + " and  MCM.CUSTOMER_CODE=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"CustomerName")) {

				Qry1 = Qry + " and MCM.FIRST_NAME=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"Institute")) {

				Qry1 = Qry + " and MCD.INSTITUTE=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"AccountType")) {

				Qry1 = Qry + " and MCD.ACCT_TYPE=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"AccountNumber")) {

				Qry1 = Qry + " and MCD.ACCT_NO=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"MobileNumber")) {

				Qry1 = Qry + " and MCI.MOBILE_NUMBER=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"MakerId")) {

				Qry1 = Qry + " and MCD.CREATED_BY=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"MakerDate")) {

				Qry1 = Qry + " and MCD.DATE_CREATED=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"CheckerId")) {

				Qry1 = Qry + " and MCD.AUTHID=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"CheckerDate")) {

				Qry1 = Qry + " and MCD.AUTHDTTM=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"CustomerStatus")) {

				Qry1 = Qry + " and MCM.STATUS=?";

			} else if (resultJson.getString("accDetails").equalsIgnoreCase(
					"AuthorizationStatus")) {

				Qry1 = Qry + " and AP.STATUS=?";

			}

			pstmt = connection.prepareStatement(Qry1);

			pstmt.setString(1, resultJson.getString("searchVal"));

			rS = pstmt.executeQuery();

			json = new JSONObject();

			while (rS.next()) {

				json.put("custId", rS.getString(1));
				json.put("customerName", rS.getString(2));
				json.put("instituteCode", rS.getString(3));
				json.put("accountType", rS.getString(4));
				json.put("accountNumber", rS.getString(5));
				json.put("mobileNumber", rS.getString(6));
				json.put("authId", rS.getString(7));
				json.put("authDttm", rS.getString(8));
				json.put("createdId", rS.getString(9));
				json.put("createsDttm", rS.getString(10));
				json.put("customerStatus", rS.getString(11));
				json.put("authStatus", rS.getString(12));

				accjsonArray.add(json);
				json.clear();
			}

			resultJson.put("accounts", accjsonArray);

			maap.put("INSTDETAILS1", resultJson);

			logger.debug("MerchantMap [" + maap + "]");

			responseDTO.setData(maap);

			pstmt.close();
			rS.close();
			json.clear();

		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
		} finally {
			try {
				rS.close();
				pstmt.close();
				connection.close();
				json = null;
			} catch (Exception es) {
			}
		}
		return responseDTO;
	}

	public ResponseDTO insertTempdata(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		String makerid = null;
		String ip = null;

		try {

			insQRY = "{call ACCOUNTSPKG.SMSPROC(?,?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("smsTemplateId"));
			cstmt.setString(2, resultJson.getString("smsTemplate"));
			cstmt.setString(4, ip);
			cstmt.setString(3, makerid);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeQuery();

			if (!cstmt.getString(5).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(5));
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
	
	
	public ResponseDTO insertsms(RequestDTO requestDTO) {
		
		Connection connection = null;
		String insQRY = "";		
		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		
		String makerid = null;
		String ip = null;
		
		try {
			
			insQRY = "{call ACCOUNTSPKG.SMSPROC(?,?,?,?,?)}";
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			
			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			connection = DBConnector.getConnection();
			
			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("mobno"));
			cstmt.setString(2, resultJson.getString("message"));
			cstmt.setString(4, ip);
			cstmt.setString(3, makerid);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeQuery();
			
			if (!cstmt.getString(5).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(5));
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
	
	
	public ResponseDTO fetchkeylogs(RequestDTO requestDTO) {

		logger.debug("Inside Fraud Management . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		PreparedStatement pstmt = null;
		ResultSet rS = null;
		
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		StringBuilder billerData1 = null;
		StringBuilder eachrow1 = null;
		AccountBean accBean = null;
		
		String Qry = "";
		String cntQry = "";
		String status = "";

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Inside Fraud Management");
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();

			

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				
				int k = 0;
				eachrow1 = new StringBuilder(50);
				billerData1 = new StringBuilder(50);
				status = requestJSON.getString("status");	
				
				
				/*
				StringBuilder sb = new StringBuilder();
				
				sb.append("SELECT  * from (select FT.MOBNUM,FT.TXN_AMOUNT,FT.DATE_CREATED,FT.FRAUD_DTTM,FT.REQUEST_CHANNEL,FT.ERROR_DESC,FT.FRDPK,decode(FT.MON_STATUS,'N','NEW','O','OPENED','E','ESCLATED','C','CLOSED'),FT.TRPK from FRAUD_TBL FT,TRAN_LOG TL where FT.TRPK=TL.TRANLOG_PK ");
				
				
				if(requestJSON.getString("REQUEST_TYPE").equals("NONE")){
					sb.append(" and  FT.MON_STATUS= '"+ status +"' order by DATE_CREATED desc ) where rownum < 11   ");
				} 
				
				else {
					
					if(!"A".equals(requestJSON.getString("MONSTATUS"))){
						sb.append(" and FT.MON_STATUS='"+requestJSON.getString("MONSTATUS")+"'");
						}
					if(requestJSON.getString("REQUEST_TYPE").equals("ACCNUM")){					
				 		
						sb.append(" and  TL.DEBIT_AC= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc ) where rownum < 11   ");
					
					}else if(requestJSON.getString("REQUEST_TYPE").equals("AMOUNT")){
						
				 		sb.append(" and  FT.TXN_AMOUNT= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc ) where rownum < 11   ");
						
					}else if(requestJSON.getString("REQUEST_TYPE").equals("TELENUM")){
						
				 		sb.append(" and  TL.MSISDN= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc ) where rownum < 11   ");
	
					}else if(requestJSON.getString("REQUEST_TYPE").equals("TRANSNUM")){
						
				 		sb.append(" and  TL.TXN_ID= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc ) where rownum < 11   ");
					}
				}*/
				
				//detQry = "SELECT  * from (select MOBNUM,TXN_AMOUNT,DATE_CREATED,FRAUD_DTTM,REQUEST_CHANNEL,ERROR_DESC,FRDPK,decode(MON_STATUS,'N','NEW','O','OPENED','E','ESCLATED','C','CLOSED'),TRPK from FRAUD_TBL where MON_STATUS=? "
					//	+ "order by DATE_CREATED desc ) where rownum < 11 ";
				Qry = "SELECT  * from (select USERID,TXN_AMOUNT,TRANS_DATE,FRAUD_DTTM,REQUEST_CHANNEL,ERROR_DESC,FRDPK,Decode(MON_STATUS,'Y','New Case','O','Opened','E','Esclated','C','Closed'),nvl(REMARKS,' '),nvl(ID,' '),nvl(ACCOUNT_NO,' ') from FRAUD_TBL "
						+ "where MON_STATUS='Y' order by DATE_CREATED desc ) where rownum < 100 ";
				
				detPstmt = connection.prepareStatement(Qry.toString());
				//detPstmt.setString(1,status);
				logger.debug("Fraud Transaction latest 10 Query  [ " + Qry+ " ]");
				detRS = detPstmt.executeQuery();

				while (detRS.next()) {
					
							eachrow.append(detRS.getString(1))
							.append(",").append(detRS.getString(2))
							.append(",").append(detRS.getString(3))
							.append(",").append(detRS.getString(4))
							.append(",").append(detRS.getString(5))
							.append(",").append(detRS.getString(6))
							.append(",").append(detRS.getString(7))
							.append(",").append(detRS.getString(8))
							.append(",").append(detRS.getString(9))
							.append(",").append(detRS.getString(10))
							.append(",").append(detRS.getString(11));
					
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setAccDetails(billerData.toString().trim());
				billerData.delete(0, eachrow.length());

				logger.debug("Responce details in Fetch Keys " + billerData);
				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				/*Qry = "SELECT  * from (select MOBNUM,TXN_AMOUNT,DATE_CREATED,FRAUD_DTTM,REQUEST_CHANNEL,ERROR_DESC,FRDPK,Decode(MON_STATUS,'V','Verified'),nvl(REMARKS,' '),nvl(VER_ID,' ') from FRAUD_TBL "
						+ "where MON_STATUS='N' order by DATE_CREATED desc ) where rownum < 11 ";
				pstmt = connection.prepareStatement(Qry);
				logger.debug("Latest Verified 10 Fraud Transactions  [ " + Qry+ " ]");
				rS = pstmt.executeQuery();

				while (rS.next()) {
					
					eachrow1.append(rS.getString(1))
					.append(",").append(rS.getString(2))
					.append(",").append(rS.getString(3))
					.append(",").append(rS.getString(4))
					.append(",").append(rS.getString(5))
					.append(",").append(rS.getString(6))
					.append(",").append(rS.getString(7))
					.append(",").append(rS.getString(8))
					.append(",").append(rS.getString(9))
					.append(",").append(rS.getString(10));
			
			if (k == 0) {
				billerData1.append(eachrow1);
			} else {
				billerData1.append("#").append(eachrow1);
			}
			k++;
			eachrow1.delete(0, eachrow1.length());
		}
				accBean.setStatus(billerData1.toString().trim());
				logger.debug("Responce details in Fraud Transactions with full responce" + billerData1);
				eachrow1.setLength(0);
				billerData1.setLength(0);
				pstmt.close();
				rS.close();
				k=0;*/
				
				
				
				/********************/
				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				cntQry = "SELECT SUM(DECODE(mon_status,'N',1,0)) NEW,SUM(DECODE(mon_status,'O',1,0)) OPEN,SUM(DECODE(mon_status,'E',1,0)) ESCLATED,SUM(DECODE(mon_status,'C',1,0)) CLOSED FROM fraud_tbl";
				pstmt = connection.prepareStatement(cntQry);
				logger.debug("Latest Verified  [ " + cntQry+ " ]");
				rS = pstmt.executeQuery();

				while (rS.next()) {
					
				eachrow1.append(rS.getString(1))
						.append(",").append(rS.getString(2))
						.append(",").append(rS.getString(3))
						.append(",").append(rS.getString(4));
			
			if (k == 0) {
				billerData1.append(eachrow1);
			} else {
				billerData1.append("#").append(eachrow1);
			}
			k++;
			eachrow1.delete(0, eachrow1.length());
		}
				logger.debug("Count in Fraud Transactions with full responce" + billerData1);
				accBean.setCampname(billerData1.toString().trim());
				billerData1.setLength(0);
				pstmt.close();
				rS.close();

				
				/********************/
	
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Fraud Transactions Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fraud Transactions ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fraud Transactions");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rS);
			
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			eachrow = null;
			billerData = null;
			Qry=null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO fetchkeys(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry1 = "";
		
		Connection connection = null;
		PreparedStatement detPstmt1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet detRS = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		StringBuilder billerData1 = null;
		StringBuilder eachrow1 = null;
		AccountBean accBean = null;
		
		JSONObject keymgmtjson = null;
		String Qry = "";
		String rege = "";
		String generatedKey =null;


		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Inside Fetch Encrypted Keys");
			logger.debug("Request JSON [" + requestJSON + "]");
			rege =requestJSON.getString("rege");
			connection = DBConnector.getConnection();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				
				eachrow1 = new StringBuilder(50);
				billerData1 = new StringBuilder(50);


				detQry1 = "select CHANNEL_NAME,ENC_KEY,DTTM from MOB_CHANNELENCKEYS ";
				detPstmt1 = connection.prepareStatement(detQry1);
				logger.debug("Transaction Details execution query [ " + detQry1+ " ]");
				rs = detPstmt1.executeQuery();

				i = 0;
				eachrow = new StringBuilder(100);
				billerData = new StringBuilder(100);

				while (rs.next()) {
					
					byte[] symkey =AESSession.generateSessionKey();
					generatedKey = AESSession.base64Encode(symkey);
					//System.out.println("Key:::"+generatedKey.trim());
					//System.out.println("Key11:::"+generatedKey);
					//System.out.println("Random Key :: "+symkey);
					eachrow.append(rs.getString(1)).append(",").append(rs.getString(2)).append(",").append(rs.getString(3));
					eachrow1.append(rs.getString(1)).append(",").append(rs.getString(2)).append(",").append(generatedKey.trim()).append(",").append(rs.getString(3));
					if (i == 0) {
						billerData.append(eachrow);
						billerData1.append(eachrow1);
					} else {
						billerData.append("#").append(eachrow);
						billerData1.append("#").append(eachrow1);
					}
					i++;
					eachrow.delete(0, eachrow.length());
					eachrow1.delete(0, eachrow1.length());
				}

				accBean.setAccDetails(billerData.toString().trim());
				logger.debug("billerdata 1 "+billerData1.toString());
				logger.debug("billerdata 11 "+billerData1.toString().trim());
				accBean.setStatus(billerData1.toString().trim());
				accBean.setAuthStatus(rege);

				billerData.delete(0, eachrow.length());
				billerData1.delete(0, eachrow1.length());

				logger.debug("Responce details in Fetch Keys " + billerData);
				
				logger.debug("Responce details in Fetch Keys " + billerData1);

				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				

				Qry = "SELECT CHANNEL_NAME from MOB_CHANNELS";
				pstmt = connection.prepareStatement(Qry);
				detRS = pstmt.executeQuery();

				keymgmtjson = new JSONObject();

				while (detRS.next()) {
					keymgmtjson.put(detRS.getString(1), detRS.getString(1));
				}
				accBean.setKeyJson(keymgmtjson);
				
				logger.debug("Output of institutions " + keymgmtjson);
				pstmt.close();
				detRS.close();

	
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fetch Keys. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fetch Keys.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(rs);
			DBUtils.closeResultSet(detRS);
			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);

			eachrow = null;
			billerData = null;
			
			//json.clear();
		}

		return responseDTO;

	}
	
	public ResponseDTO insertkeys(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";

		CallableStatement cstmt = null;
		JSONObject resultJson = null;

		String ip = null;
		String makerid = null;

		try {
			logger.debug("Inside InsertKeys");
			insQRY = "{call ACCOUNTSPKG.INSERTKEYS(?,?,?,?)}";

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");

			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			logger.debug("MultiData Values are"+resultJson.getString("multiData"));

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("multiData"));
			cstmt.setString(2, makerid);
			cstmt.setString(3, ip);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.executeQuery();

			if (!cstmt.getString(4).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(4));
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
	
	
	public ResponseDTO getLockedDevices(RequestDTO requestDTO) {

		logger.debug("Inside getData... ");

		Connection connection = null;
		HashMap<String, Object> merchantMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray userGroupsJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String merchantQry = "SELECT IMEI_REF_ID,IMEI,TRAN_DTTM,ATTEMPTS_COUNT,APPID,USERIDS,DECODE(DEVISE_STATUS,'A','Active','L','Locked',DEVISE_STATUS),IP,PORT,CUST_ID,MOBILE_NO "+  
							 " FROM IMEI_LOCKING_UNLOCKING WHERE DEVISE_STATUS='L' and ATTEMPTS_COUNT > 2 ";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			userGroupsJSONArray = new JSONArray();

			while (merchantRS.next()) {
				json = new JSONObject();
				
				json.put("IMEI_REF_ID",merchantRS.getString(1));
				json.put("IMEI",merchantRS.getString(2));
				json.put("TRAN_DTTM",merchantRS.getString(3));
				json.put("ATTEMPTS_COUNT",merchantRS.getString(4));
				json.put("APPID",merchantRS.getString(5));
				//json.put("USERIDS",merchantRS.getClob(6));
				json.put("DEVISE_STATUS",merchantRS.getString(7));
				json.put("IP",merchantRS.getString(8));
				json.put("PORT",merchantRS.getString(9));
				json.put("CUST_ID",merchantRS.getString(10));
				json.put("MOBILE_NO",(merchantRS.getString(11)!=null)?merchantRS.getString(11):"");
				
				userGroupsJSONArray.add(json);
				json.clear();
				json = null;
			}

			resultJson.put("DEVICES", userGroupsJSONArray);
			merchantMap.put("DEVICES", resultJson);

			logger.debug("Entity Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantMap = null;

			resultJson = null;
			json = null;
			merchantQry = null;
			userGroupsJSONArray = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO updateDeviceReset(RequestDTO requestDTO){
		logger.debug("Inside updateDeviceReset..");

		Connection connection = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String merchantQry1 = "UPDATE IMEI_LOCKING_UNLOCKING SET ATTEMPTS_COUNT='0',DEVISE_STATUS='A' WHERE IMEI=? ";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();
			merchantPstmt = connection.prepareStatement(merchantQry1);
			merchantPstmt.setString(1, requestJSON.getString("IMEI"));
			merchantPstmt.executeUpdate();
			connection.commit();
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in UpdateDeviceReset [" + e.getMessage()	+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("SQLException in DashboardUsers [" + e.getMessage()+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
		}

		return responseDTO;
	}

	public ResponseDTO valtxn(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		
		CallableStatement cstmt = null;
		JSONObject resultJson = null;
		
		String ip = null;
		String makerid = null;
		String remarks = null;
		String appid = null;
		String tomail=null;
		String status=null;
		
		try {
			logger.debug("Inside InsertKeys");
			insQRY = "{call ACCOUNTSPKG.VALFRAUDTXN(?,?,?,?,?,?,?)}";
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			
			logger.debug("appid in DAO"+requestJSON.getString("appid"));
			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			remarks = requestJSON.getString("remarks");
			appid = requestJSON.getString("appid");
			tomail=requestJSON.getString("tomail");
			status=requestJSON.getString("status");
			
			
			logger.debug("MultiData Values are"+resultJson.getString("multiData"));
			
			connection = DBConnector.getConnection();
			
			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, remarks);
			cstmt.setString(2, appid);
			cstmt.setString(3, makerid);
			cstmt.setString(4, ip);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.setString(6, tomail);
			cstmt.setString(7, status);
			cstmt.executeQuery();
			
			if (!cstmt.getString(5).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(5));
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
	
	
	public ResponseDTO insertimage(RequestDTO requestDTO) {
		String insQRY = "";
		
		Connection connection = null;
		CallableStatement cstmt = null;
		PreparedStatement userPstmt = null;
		ResultSet userRS = null;

		String ip = null;
		String makerid = null;
		String fname = null;
		String userQry = "";

		JSONObject json = null;

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;
		
		try {
			logger.debug("Inside InsertKeys");
			insQRY = "{call ACCOUNTSPKG.INSERTIMAGE(?,?,?,?)}";
			
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			
			ip = requestJSON.getString(CevaCommonConstants.IP);
			makerid = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			fname = requestJSON.getString("fname");
			
			connection = DBConnector.getConnection();
			
			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1,fname);
			cstmt.setString(2, makerid);
			cstmt.setString(3, ip);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.executeQuery();
			
			if (!cstmt.getString(4).contains("SUCCESS")) {
				responseDTO.addError(cstmt.getString(4));
			}
			
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			userQry = "SELECT IMAGEPATH,IMAGEPATH||'-'||IMG_ORDER from SLIDE_IMAGES  order by IMG_ORDER";
	
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
				userJSONArray.add(json);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry ::: and imagenames are" + userJSONArray);
			responseJSON.put("IMAGENAMES", userJSONArray);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);
			
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(userPstmt);
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			cstmt = null;
			connection = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO fetchimages(RequestDTO requestDTO) {
		logger.debug("Inside Fetch Services in DAO... ");
		String userQry = "";

		Connection connection = null;
		PreparedStatement userPstmt = null;
		ResultSet userRS = null;

		JSONObject json = null;
		HashMap<String, Object> resultMap = null;
		JSONArray userJSONArray = null;

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			userQry = "SELECT IMAGEPATH,IMAGEPATH||'-'||IMG_ORDER from SLIDE_IMAGES";
	
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(2));
				userJSONArray.add(json);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry :::" + userJSONArray);
			responseJSON.put("IMAGENAMES", userJSONArray);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing1.");
		} finally {

			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(userPstmt);
			DBUtils.closeConnection(connection);

			resultMap = null;
			userJSONArray = null;
			json = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO healthstatus(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
		String detQry1 = "";
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		ResultSet detRS = null;
		ResultSet rs = null;
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		AccountBean accBean = null;
		
		int i=0;


		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Inside Fetch Encrypted Keys");
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();

				detQry1 = "Select COMPONENT,ISEXTERNAL,TECHNIQUE,IP,PORT,FLOATBALANCE,URL,URLRESPONSE,TIMEUPDATED,Decode(STATUS,'U','UP','D','Down',STATUS) STATUS from MOB_HEALTH_MONITOR";
				detPstmt = connection.prepareStatement(detQry1);
				logger.debug("Transaction Details execution query [ " + detQry1+ " ]");
				rs = detPstmt.executeQuery();

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

				accBean.setAccDetails(billerData.toString().trim());

				billerData.delete(0, eachrow.length());

				logger.debug("Responce details in Fetch Keys " + billerData);
				

				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				
	
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fetch Keys. ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fetch Keys.");
			e.printStackTrace();
		} finally {

			DBUtils.closeResultSet(rs);
			DBUtils.closeResultSet(detRS);
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeConnection(connection);

			eachrow = null;
			billerData = null;
			
		}

		return responseDTO;

	}
	
	
	public ResponseDTO searchCasesfetchkeylogs(RequestDTO requestDTO) {

		logger.debug("Inside Fraud Management . ");

		HashMap<String, Object> detMap = null;
		String detQry = "";
		
		Connection connection = null;
		PreparedStatement detPstmt = null;
		PreparedStatement detPstmt1 = null;
		ResultSet detRS = null;
		PreparedStatement pstmt = null;
		ResultSet rS = null;
		
		
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		StringBuilder billerData1 = null;
		StringBuilder eachrow1 = null;
		AccountBean accBean = null;
		
		String Qry = "";
		String cntQry = "";
		String status = "";

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			accBean = new AccountBean();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Inside Fraud Management");
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();

			

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);
				
				int k = 0;
				eachrow1 = new StringBuilder(50);
				billerData1 = new StringBuilder(50);
				status = requestJSON.getString("status");	
				
				if (status=="NONE"){
					status="N";	
					}
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("SELECT  * from (select FT.MOBNUM,FT.TXN_AMOUNT,FT.DATE_CREATED,FT.FRAUD_DTTM,FT.REQUEST_CHANNEL,FT.ERROR_DESC,FT.FRDPK,decode(FT.MON_STATUS,'N','NEW','O','OPENED','E','ESCLATED','C','CLOSED'),FT.TRPK from FRAUD_TBL FT,TRAN_LOG TL where FT.TRPK=TL.TRANLOG_PK ");
				
				
				if(requestJSON.getString("REQUEST_TYPE").equals("NONE")){
					sb.append(" and  FT.MON_STATUS= '"+ status +"' order by DATE_CREATED desc ) where rownum < 11   ");
				} 
				
				else {
					
					if(!"A".equals(requestJSON.getString("MONSTATUS"))){
						sb.append(" and FT.MON_STATUS='"+requestJSON.getString("MONSTATUS")+"'");
						}
					if(requestJSON.getString("REQUEST_TYPE").equals("ACCNUM")){					
				 		
						sb.append(" and  TL.DEBIT_AC= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc )   ");
					
					}else if(requestJSON.getString("REQUEST_TYPE").equals("AMOUNT")){
						
				 		sb.append(" and  FT.TXN_AMOUNT= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc )    ");
						
					}else if(requestJSON.getString("REQUEST_TYPE").equals("TELENUM")){
						
				 		sb.append(" and  TL.MSISDN= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc )    ");
	
					}else if(requestJSON.getString("REQUEST_TYPE").equals("TRANSNUM")){
						
				 		sb.append(" and  TL.TXN_ID= '"+ requestJSON.getString("ACCNUMBER")+"' and FT.FRAUD_DTTM  between TO_DATE('"+requestJSON.getString("FROMDATE")+"','dd/MM/yyyy') AND TO_DATE('"+requestJSON.getString("TODATE")+"','dd/MM/yyyy') order by DATE_CREATED desc ) ");
					}
				}
				
				//detQry = "SELECT  * from (select MOBNUM,TXN_AMOUNT,DATE_CREATED,FRAUD_DTTM,REQUEST_CHANNEL,ERROR_DESC,FRDPK,decode(MON_STATUS,'N','NEW','O','OPENED','E','ESCLATED','C','CLOSED'),TRPK from FRAUD_TBL where MON_STATUS=? "
					//	+ "order by DATE_CREATED desc ) where rownum < 11 ";
				detPstmt = connection.prepareStatement(sb.toString());
				//detPstmt.setString(1,status);
				logger.debug("Fraud Transaction latest 10 Query  [ " + sb.toString()+ " ]");
				detRS = detPstmt.executeQuery();

				while (detRS.next()) {
					
							eachrow.append(detRS.getString(1))
							.append(",").append(detRS.getString(2))
							.append(",").append(detRS.getString(3))
							.append(",").append(detRS.getString(4))
							.append(",").append(detRS.getString(5))
							.append(",").append(detRS.getString(6))
							.append(",").append(detRS.getString(7))
							.append(",").append(detRS.getString(8))
							.append(",").append(detRS.getString(9));
					
					if (i == 0) {
						billerData.append(eachrow);
					} else {
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}

				accBean.setAccDetails(billerData.toString().trim());
				billerData.delete(0, eachrow.length());

				logger.debug("Responce details in Fetch Keys " + billerData);
				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				Qry = "SELECT  * from (select MOBNUM,TXN_AMOUNT,DATE_CREATED,FRAUD_DTTM,REQUEST_CHANNEL,ERROR_DESC,FRDPK,Decode(MON_STATUS,'V','Verified'),nvl(REMARKS,' '),nvl(VER_ID,' ') from FRAUD_TBL "
						+ "where MON_STATUS='V' order by DATE_CREATED desc ) where rownum < 11 ";
				pstmt = connection.prepareStatement(Qry);
				logger.debug("Latest Verified 10 Fraud Transactions  [ " + Qry+ " ]");
				rS = pstmt.executeQuery();

				while (rS.next()) {
					
					eachrow1.append(rS.getString(1))
					.append(",").append(rS.getString(2))
					.append(",").append(rS.getString(3))
					.append(",").append(rS.getString(4))
					.append(",").append(rS.getString(5))
					.append(",").append(rS.getString(6))
					.append(",").append(rS.getString(7))
					.append(",").append(rS.getString(8))
					.append(",").append(rS.getString(9))
					.append(",").append(rS.getString(10));
			
			if (k == 0) {
				billerData1.append(eachrow1);
			} else {
				billerData1.append("#").append(eachrow1);
			}
			k++;
			eachrow1.delete(0, eachrow1.length());
		}
				accBean.setStatus(billerData1.toString().trim());
				logger.debug("Responce details in Fraud Transactions with full responce" + billerData1);
				eachrow1.setLength(0);
				billerData1.setLength(0);
				pstmt.close();
				rS.close();
				k=0;
				
				
				
				/********************/
				
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();

				cntQry = "SELECT SUM(DECODE(mon_status,'N',1,0)) NEW,SUM(DECODE(mon_status,'O',1,0)) OPEN,SUM(DECODE(mon_status,'E',1,0)) ESCLATED,SUM(DECODE(mon_status,'C',1,0)) CLOSED FROM fraud_tbl";
				pstmt = connection.prepareStatement(cntQry);
				logger.debug("Latest Verified  [ " + cntQry+ " ]");
				rS = pstmt.executeQuery();

				while (rS.next()) {
					
				eachrow1.append(rS.getString(1))
						.append(",").append(rS.getString(2))
						.append(",").append(rS.getString(3))
						.append(",").append(rS.getString(4));
			
			if (k == 0) {
				billerData1.append(eachrow1);
			} else {
				billerData1.append("#").append(eachrow1);
			}
			k++;
			eachrow1.delete(0, eachrow1.length());
		}
				logger.debug("Count in Fraud Transactions with full responce" + billerData1);
				accBean.setCampname(billerData1.toString().trim());
				billerData1.setLength(0);
				pstmt.close();
				rS.close();

				
				/********************/
	
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Fraud Transactions Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fraud Transactions ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account Fetch Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing in Fraud Transactions");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(detPstmt1);
			DBUtils.closeResultSet(rS);
			
			DBUtils.closePreparedStatement(detPstmt);
			DBUtils.closeResultSet(detRS);
			DBUtils.closeConnection(connection);

			eachrow = null;
			billerData = null;
			Qry=null;
		}
		return responseDTO;
	}
	
	public ResponseDTO fetchAccountServiceDetails(RequestDTO requestDTO) {

		logger.debug("Inside Account Details Fetching . ");

		HashMap<String, Object> detMap = null;
	
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
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			makerid=requestJSON.getString("makerId");
			connection = DBConnector.getConnection();
			
			
			
			
			
				JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.AccountDetails(resultJson.getString("accountno")));
				
				String mobileno="";
				
				if((json1.getString("phone")).startsWith("0")){
					mobileno="234"+(json1.getString("phone")).substring(1);
				}else{
					mobileno="234"+json1.getString("phone");
				}
				
				
				JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.custandactbycustid(json1.getString("custID")));
				
			//	System.out.println(json1.getString("custinfo"));
					
					
					accBean = new AccountBean();
					
					accBean.setCustomercode(custid);
					accBean.setAccountno(resultJson.getString("accountno"));
					accBean.setCustomercode(json1.getString("custID"));
					accBean.setBranchcode(json1.getString("branchCode"));
					accBean.setFullname((json1.get("accountName")).toString());	
					accBean.setComment((json1.get("currencyCode")).toString());
					accBean.setTelephone(mobileno);
					accBean.setProduct((json1.get("productName")).toString());
					accBean.setAccDetails((json1.get("accountStatus")).toString());
					
					JSONArray jsonarray =  JSONArray.fromObject(json2.get("custinfo"));
					Iterator iterator = jsonarray.iterator();
					while (iterator.hasNext()) {
						JSONObject jsonobj=(JSONObject)iterator.next();
						accBean.setIdnumber((String)jsonobj.get("nationalityID"));
						accBean.setDob((String)jsonobj.get("dateOfBirth"));
						accBean.setEmail((String)jsonobj.get("custEmail"));
						accBean.setGender((String)jsonobj.get("gender"));
						
					}
					
					
					
					accBean.setApptype(resultJson.getString("apptype"));
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

			eachrow = null;
			billerData = null;
		}

		return responseDTO;

	}
	
	public ResponseDTO insertCustomerDetails(RequestDTO requestDTO) {
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
			
			insQRY = "{call CUSTOMERONBOARDPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			
			
			ip = requestJSON.getString(CevaCommonConstants.IP);

			

			cstmt = connection.prepareCall(insQRY);
			cstmt.setString(1, resultJson.getString("customercode"));
			cstmt.setString(2, resultJson.getString("email"));
			cstmt.setString(3, resultJson.getString("fullname"));
			cstmt.setString(4, resultJson.getString("staffid")); 
			cstmt.setString(5, resultJson.getString("telephone"));
			cstmt.setString(6, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			cstmt.setString(7, "");
			cstmt.setString(8, "");
			cstmt.setString(9, ip);			
			
			cstmt.setString(10, resultJson.getString("gender"));
			cstmt.setString(11, resultJson.getString("dob"));
			cstmt.setString(12, "WEB");
			cstmt.setString(13, "");
			cstmt.setString(14, "NGN");			
			cstmt.setString(15, "MOBILE");
			cstmt.setString(16, "N");
			cstmt.setString(17, resultJson.getString("accountno"));
			cstmt.setString(18, resultJson.getString("fullname"));			
			cstmt.setString(19, resultJson.getString("branchcode"));
			cstmt.setString(20, resultJson.getString("product"));
			cstmt.setString(21, resultJson.getString("fullname"));
			cstmt.setString(22, "Registered");
			cstmt.setString(23,  resultJson.getString("accDetails"));
			cstmt.setString(24, "P");
			cstmt.registerOutParameter(25, Types.VARCHAR);
			cstmt.registerOutParameter(26, Types.VARCHAR);
			cstmt.executeQuery();

			

			if ((cstmt.getString(26)).equalsIgnoreCase("00")) {
				
				ServiceRequestClient.sms(resultJson.getString("telephone"), "Dear "+resultJson.getString("fullname")+", welcome to a world of possibilities on Union Mobile/USSD. Please dial *826*5# or download the app from google play, app store or windows store to create your pin.");

				responseDTO.addMessages(cstmt.getString(25));
			}else{
				responseDTO.addMessages(cstmt.getString(25));				
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
	
	
	public ResponseDTO accountOpenServiceDetails(RequestDTO requestDTO) {

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
		String bvnnum=null;
		String srchcriteria=null;

		AccountBean accBean=null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();
			accBean= new AccountBean();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = (JSONObject) requestDTO.getRequestJSON()
					.get("accBean");
			makerid=requestJSON.getString("makerId");
			bvnnum=resultJson.getString("mobileno");
			srchcriteria=resultJson.getString("srchcriteria");
			connection = DBConnector.getConnection();
			
			
			
			
			
			/*if(srchcriteria.equalsIgnoreCase("ACCOUNTOPEN")){*/
				
				
				detPstmt = connection.prepareStatement("select count(*) from PAYSTACK_TBL where BVN=? and STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') and BVN is not null and W_STATUS is null");
				detPstmt.setString(1,bvnnum);
				detRS = detPstmt.executeQuery();
				while (detRS.next()) {
					if(detRS.getInt(1)==0){
						responseDTO.addError("This Customer BVN Number "+resultJson.getString("mobileno")+" Not Found In paystack . ");
					}else{
						JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.bvnInfo(bvnnum));
						
						if((json2.get("respcode")).equals("00")){
							
							detPstmt1 = connection.prepareStatement("select SUM(AMOUNT) AS TOTAMT,count(*) AS COUNT from PAYSTACK_TBL where STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') and BVN=?  and BVN is not null and AMOUNT is not null");
							detPstmt1.setString(1,bvnnum);
							detRS2 = detPstmt1.executeQuery();
							while (detRS2.next()) {
								accBean.setBalance(detRS2.getString("TOTAMT"));	
								accBean.setMessage(detRS2.getString("COUNT"));
							}
							detPstmt1.close();
							detRS2.close();	
						
							
						
						
						detPstmt1 = connection.prepareStatement("select STATUS,NVL(ACCOUNT,' '),AMOUNT,TO_CHAR(TXNDATE,'DD-MM-YYYY') as TDATE,DECODE(STATUS,'ACCOUNTNO','Account Credit Pending','Account Open and Credit Pending') as SSTATUS,TXNREF,NVL(ACCOUNT,' ') as ACCOUNT_NO,NVL(CUST_ID,' ') AS CUST_IDS from PAYSTACK_TBL where BVN=? and STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') and BVN is not null and rownum=1");
						detPstmt1.setString(1,bvnnum);
						detRS2 = detPstmt1.executeQuery();
						while (detRS2.next()) {
						
						
							/*accBean = new AccountBean();*/
							
							String mobileno="";
							
							accBean.setIDNumber(bvnnum);
							accBean.setReferenceno(detRS2.getString("TXNREF"));
							accBean.setFullname((json2.get("FirstName")).toString());
							accBean.setMiddlename((json2.get("FirstName")).toString());
							accBean.setLastname((json2.get("MiddleName")).toString());
							accBean.setFullname((json2.get("LastName")).toString());
							
							accBean.setGender("");
							accBean.setTelephone(json2.getString("PhoneNumber"));
							accBean.setIdnumber(detRS2.getString("ACCOUNT_NO"));
							accBean.setDob((json2.get("DateOfBirth")).toString());
							accBean.setLangugae((json2.get("EnrollmentBank")).toString());
							accBean.setEmail("");
							
							accBean.setStdate(detRS2.getString("TDATE"));
							accBean.setCustomerstatus(detRS2.getString("SSTATUS"));
							accBean.setApptype(resultJson.getString("apptype"));
							accBean.setAuthStatus(detRS2.getString("STATUS"));
							//accBean.setMessage("");
						 
							
						}
						
						/*detPstmt1 = connection.prepareStatement("select NVL(ACCOUNT,' ')  as ACCOUNT_NO,DECODE(STATUS,'ACCOUNTNO','Account Credit Pending','SUCCESS','Account Credit Pending','Account Open and Credit Pending') as SSTATUS from PAYSTACK_TBL where STATUS in ('SUCCESS','ACCOUNTNO') and BVN=?  and BVN is not null and AMOUNT is not null and ACCOUNT is not null and rownum=1");
						detPstmt1.setString(1,bvnnum);
						detRS2 = detPstmt1.executeQuery();
						if (detRS2.next()) {
							accBean.setIdnumber(detRS2.getString("ACCOUNT_NO"));
							accBean.setCustomerstatus(detRS2.getString("SSTATUS"));

						}else{
							accBean.setIdnumber("");
							accBean.setCustomerstatus("Account Open and Credit Pending");
						}
						detPstmt1.close();
						detRS2.close();	
						*/
						
						
					}else{
						 responseDTO.addError((json2.get("respdesc")).toString());
					 }
					
				}
				

				}
				

			/*	detPstmt.close();
				detRS.close();	
			}
			
			
			if(srchcriteria.equalsIgnoreCase("CREDIT")){
				
				
				detPstmt = connection.prepareStatement("select count(*) from PAYSTACK_TBL where BVN=? and STATUS in ('ACCOUNTNO') and BVN is not null and rownum=1");
				detPstmt.setString(1,bvnnum);
				detRS = detPstmt.executeQuery();
				while (detRS.next()) {
					if(detRS.getInt(1)==0){
						responseDTO.addError("This Customer BVN Number "+resultJson.getString("mobileno")+" Not Found In paystack . ");
					}else{
						accBean = new AccountBean();
						detPstmt1 = connection.prepareStatement("select SUM(AMOUNT) AS TOTAMT,count(*) AS COUNT from PAYSTACK_TBL where BVN=? and STATUS in ('ACCOUNTNO') and BVN is not null");
						detPstmt1.setString(1,bvnnum);
						detRS2 = detPstmt1.executeQuery();
						while (detRS2.next()) {
							accBean.setBalance(detRS2.getString("TOTAMT"));	
							accBean.setMessage(detRS2.getString("COUNT"));
						}
						detPstmt1.close();
						detRS2.close();
						
						
						
						detPstmt1 = connection.prepareStatement("select STATUS,NVL(ACCOUNT,' '),AMOUNT,TO_CHAR(TXNDATE,'DD-MM-YYYY') as TDATE,DECODE(STATUS,'ACCOUNTNO','Account Credit Pending','Account Open and Credit Pending') as SSTATUS,TXNREF,NVL(ACCOUNT,' ') as ACCOUNT_NO,NVL(CUST_ID,' ') AS CUST_IDS from PAYSTACK_TBL where BVN=? and STATUS in ('ACCOUNTNO') and BVN is not null and rownum=1");
						detPstmt1.setString(1,bvnnum);
						detRS2 = detPstmt1.executeQuery();
						while (detRS2.next()) {
						
						JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.bvnInfo(bvnnum));
						if((json2.get("respcode")).equals("00")){
							
							
							String mobileno="";
							
							accBean.setIDNumber(bvnnum);
							accBean.setReferenceno(detRS2.getString("TXNREF"));
							accBean.setFullname((json2.get("FirstName")).toString());
							accBean.setMiddlename((json2.get("FirstName")).toString());
							accBean.setLastname((json2.get("MiddleName")).toString());
							accBean.setFullname((json2.get("LastName")).toString());
							
							accBean.setGender("");
							accBean.setTelephone(json2.getString("PhoneNumber"));
							accBean.setIdnumber(detRS2.getString("ACCOUNT_NO"));
							accBean.setDob((json2.get("DateOfBirth")).toString());
							accBean.setLangugae((json2.get("EnrollmentBank")).toString());
							accBean.setEmail("");
							
							accBean.setStdate(detRS2.getString("TDATE"));
							accBean.setCustomerstatus(detRS2.getString("SSTATUS"));
							accBean.setApptype(resultJson.getString("apptype"));
							accBean.setAuthStatus(detRS2.getString("STATUS"));
						 }
							
						}
					}
				}
				
				
				detPstmt.close();
				detRS.close();	
				
			}
			*/
			
			
			
			/*detPstmt = connection.prepareStatement("select count(*) from PAYSTACK_TBL where BVN=? and STATUS in ('DEBITSUCCESS','VERIFY','TIMEOUTPAY','OTPVALFAIL','ACCOUNTNO') and BVN is not null and rownum=1");
			detPstmt.setString(1,bvnnum);
			detRS = detPstmt.executeQuery();
			while (detRS.next()) {
				
				if(detRS.getInt(1)==0){
					responseDTO.addError("This Customer BVN Number "+resultJson.getString("mobileno")+" Not Found In paystack . ");
				}else{
					
					detPstmt1 = connection.prepareStatement("select STATUS,NVL(ACCOUNT,' '),AMOUNT,TO_CHAR(TXNDATE,'DD-MM-YYYY') as TDATE,DECODE(STATUS,'ACCOUNTNO','Account Credit Pending','Account Open and Credit Pending') as SSTATUS,TXNREF,NVL(ACCOUNT,' ') as ACCOUNT_NO,NVL(CUST_ID,' ') AS CUST_IDS from PAYSTACK_TBL where BVN=? and STATUS in ('DEBITSUCCESS','VERIFY','TIMEOUTPAY','OTPVALFAIL','ACCOUNTNO') and BVN is not null and rownum=1");
					detPstmt1.setString(1,bvnnum);
					detRS2 = detPstmt1.executeQuery();
					while (detRS2.next()) {
						//System.out.println("kailash here ::: "+detRS2.getString("STATUS"));
						if((detRS2.getString("STATUS")).equalsIgnoreCase("ACCOUNTNO")){

							JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.bvnInfo(bvnnum));
							if((json2.get("respcode")).equals("00")){
								accBean = new AccountBean();
								
								String mobileno="";
								
								accBean.setIDNumber(bvnnum);
								accBean.setReferenceno(detRS2.getString("TXNREF"));
								accBean.setFullname((json2.get("FirstName")).toString());
								accBean.setMiddlename((json2.get("FirstName")).toString());
								accBean.setLastname((json2.get("MiddleName")).toString());
								accBean.setFullname((json2.get("LastName")).toString());
								
								accBean.setGender("");
								accBean.setTelephone(json2.getString("PhoneNumber"));
								accBean.setIdnumber(detRS2.getString("ACCOUNT_NO"));
								accBean.setDob((json2.get("DateOfBirth")).toString());
								accBean.setLangugae((json2.get("EnrollmentBank")).toString());
								accBean.setEmail("");
								accBean.setBalance(detRS2.getString("AMOUNT"));
								accBean.setStdate(detRS2.getString("TDATE"));
								accBean.setCustomerstatus(detRS2.getString("SSTATUS"));
								accBean.setApptype(resultJson.getString("apptype"));
								accBean.setAuthStatus(detRS2.getString("STATUS"));
								
								
							}
						
							
							
						}else{
							JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.bvnInfo(bvnnum));
							if((json2.get("respcode")).equals("00")){
								accBean = new AccountBean();
								
								String mobileno="";
								
								accBean.setIDNumber(bvnnum);
								accBean.setReferenceno(detRS2.getString("TXNREF"));
								accBean.setFullname((json2.get("FirstName")).toString());
								accBean.setMiddlename((json2.get("FirstName")).toString());
								accBean.setLastname((json2.get("MiddleName")).toString());
								accBean.setFullname((json2.get("LastName")).toString());
								
								accBean.setGender("");
								accBean.setTelephone(json2.getString("PhoneNumber"));
								accBean.setIdnumber("");
								accBean.setDob((json2.get("DateOfBirth")).toString());
								accBean.setLangugae((json2.get("EnrollmentBank")).toString());
								accBean.setEmail("");
								accBean.setBalance(detRS2.getString("AMOUNT"));
								accBean.setStdate(detRS2.getString("TDATE"));
								accBean.setCustomerstatus(detRS2.getString("SSTATUS"));
								accBean.setApptype(resultJson.getString("apptype"));
								accBean.setAuthStatus(detRS2.getString("STATUS"));
								
							}
						}
					}
					
					
					
				}
				
			}*/
		
				
			detMap.put("AccountData", accBean);
			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);
			
			
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
	
	
	public ResponseDTO accountOpenServiceAck(RequestDTO requestDTO) {
		Connection connection = null;
		String insQRY = "";
		String ip = null;

		PreparedStatement pstmt = null;
		JSONObject resultJson = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		String status="U";

		try {
			responseDTO = new ResponseDTO();
			insQRY = "";

			
			requestJSON = requestDTO.getRequestJSON();
			resultJson = (JSONObject) requestJSON.get("accBean");
			ip = requestJSON.getString(CevaCommonConstants.IP);
			
			CommonServiceDao csd=new CommonServiceDao();
			JSONObject resp =  csd.genaratePassword().getJSONObject("data");

			connection = DBConnector.getConnection();
			String ref="ACPAY"+System.currentTimeMillis();
			
			servicePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),NVL(CLUSTER_ID,' ')  from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+requestJSON.getString(CevaCommonConstants.MAKER_ID)+"')");


			serviceRS = servicePstmt.executeQuery();
			while (serviceRS.next()) {
				
			
			
			
			
			pstmt = connection.prepareStatement("INSERT INTO ACCOUNT_OPEN_WEB(TXNREFNO,F_NAME,M_NAME,L_NAME,DATEOFBIRTH,PRODUCTCODE,BRANCH,RMCODE,AMOUNT,BVN,MAKER_ID,INITIATORID,GENDER,MOBILENO,MAKER_DT,STATUS,AUTH_REFERENCENO,CUST_STATUS,ACCOUNT_NO,PAYBILL_STATUS,NEW_TXNREFNO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,'P',?,?,?,?,?)");
			pstmt.setString(1, resultJson.getString("referenceno"));
			pstmt.setString(2, resultJson.getString("fullname"));
			pstmt.setString(3, resultJson.getString("middlename"));
			pstmt.setString(4, resultJson.getString("lastname"));
			pstmt.setString(5, resultJson.getString("dob"));
			pstmt.setString(6, resultJson.getString("product"));
			pstmt.setString(7, (resultJson.getString("branchdetails")).split("-")[0]);
			pstmt.setString(8, resultJson.getString("rmcode"));
			pstmt.setString(9, resultJson.getString("balance"));
			pstmt.setString(10, resultJson.getString("IDNumber"));
			pstmt.setString(11, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			pstmt.setString(12, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			pstmt.setString(13, resultJson.getString("gender"));
			pstmt.setString(14, resultJson.getString("telephone"));
			pstmt.setString(15, serviceRS.getString(1));
			pstmt.setString(16, resultJson.getString("customerstatus"));
			pstmt.setString(17, resultJson.getString("idnumber"));
			pstmt.setString(18, resultJson.getString("authStatus"));
			pstmt.setString(19, ref);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='P',NEW_TXNREFNO=? WHERE  STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') AND BVN=?");
			pstmt.setString(1, ref);
			pstmt.setString(2, resultJson.getString("IDNumber"));
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = connection.prepareStatement("insert into auth_pending ( ref_num,maker_id,maker_dttm,auth_code,status,auth_flag,MAKER_BRCODE,ACTION,ACTION_DETAILS) values('"+serviceRS.getString(1)+"','"+requestJSON.getString(CevaCommonConstants.MAKER_ID)+"',sysdate,'ACCOUNTAUTH','P','AO','"+serviceRS.getString(2)+"','MAKER','BVN Number "+resultJson.getString("IDNumber")+" for Account Opening')");
			pstmt.executeUpdate();
			
			
			}
			
			/*ServiceRequestClient.sms(resultJson.getString("telephone"), "Dear "+resultJson.getString("fullname")+", Please find the Mobile Banking  Transaction Pin "+resp.getString("pin"));

			 JSONObject jsonaudit = new JSONObject();
			 	jsonaudit.put(CevaCommonConstants.MAKER_ID, requestJSON.getString(CevaCommonConstants.MAKER_ID));
			 	jsonaudit.put("transCode", "modregcustinfo");
			 	jsonaudit.put("channel", "WEB");
			 	jsonaudit.put("message", "Set Txn Pin : Mobile Number "+resultJson.getString("telephone"));
			 	jsonaudit.put("ip", ip);
			 	jsonaudit.put("det1", "");
			 	jsonaudit.put("det2", "");
			 	jsonaudit.put("det3", "");
				
				
				csd.auditTrailInsert(jsonaudit);*/
				
			connection.commit();
			responseDTO.addMessages("Success ..");
			
		} catch (Exception ex) {
			logger.error("Error Occured..!" + ex.getLocalizedMessage());
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			connection = null;
		}
		return responseDTO;
	}
	
	public ResponseDTO getCustDetailsModify(RequestDTO requestDTO) {

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
		String mobno=null;

		AccountBean accBean = null;
		StringBuilder billerData = null;
		StringBuilder eachrow = null;
		boolean flag = false;

		try {
			responseDTO = new ResponseDTO();
			detMap = new HashMap<String, Object>();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			resultJson = (JSONObject) requestDTO.getRequestJSON().get("accBean");
			
			connection = DBConnector.getConnection();


			detQry = "SELECT CM.CUSTOMER_CODE,CM.FIRST_NAME,CI.MOBILE_OPERATOR,CI.ISO_COUNTRY_CODE,CI.MOBILE_NUMBER,to_char(CM.DOB,'dd-mm-yyyy'), "
					+ "DECODE(CM.LANGUAGE_ID,'1','English','2','Swaheli'),CM.EMAILID,CA.INSTITUTE,to_char(CM.AUTHDTTM,'DD-MM-YYYY HH24:MI:SS'),CM.M_PRD_CODE,CM.M_PRD_DESC,NVL(CM.USER_ID,' '),CM.STATUS,CM.SMSTOKEN "
					+ "FROM MOB_CUSTOMER_MASTER CM,MOB_CONTACT_INFO CI,MOB_ACCT_DATA CA "
					+ "WHERE CM.ID=CI.CUST_ID and CM.ID=CA.CUST_ID AND CM.ID=CA.CUST_ID "
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
				accBean.setProduct(detRS.getString(11));
				accBean.setProdesc(detRS.getString(12));
				accBean.setUserid(detRS.getString(13));
				accBean.setStatus(detRS.getString(14));
				accBean.setSmstoken(detRS.getString(15));
				
				
				accBean.setApptype("MOBILE");
				flag = true;
			}

			logger.debug("Bean Details   :::: " + accBean);
			
			
			if (flag) {

				 

				logger.debug("into acc details");
				detPstmt.close();
				detRS.close();
				
				detQry1 = "SELECT MAKER_DT,RMCODE,MAKER_ID,NVL(INTRODUCER,' ') "
						+ "FROM RMCODE_TBL  "
						+ "WHERE CUSTOMER='"+accBean.getCustomercode()+"'" ;
						
				logger.debug("Acc details  query [ " + detQry1 + " ]");
				detPstmt = connection.prepareStatement(detQry1);
				detRS = detPstmt.executeQuery();

				if (detRS.next()) {
					accBean.setMakerdt(detRS.getString(1));
					accBean.setBranch(detRS.getString(2));
					accBean.setAuthid(detRS.getString(3));
					accBean.setIntroducer(detRS.getString(4));
				}
				detPstmt.close();
				detRS.close();
				
				
			detQry1="select to_char(TRANS_DTTM,'dd-mm-yyyy'),CHANNEL from DEBIT_CARD_INFO_TBL where CUSTID='"+accBean.getCustomercode()+"' and rownum=1 and CHANNEL is not null"; 
				
				logger.debug("Acc details  query [ " + detQry1 + " ]");
				detPstmt = connection.prepareStatement(detQry1);
				detRS = detPstmt.executeQuery();

				if (detRS.next()) {
					accBean.setTransdate(detRS.getString(1));
					accBean.setChnname(detRS.getString(2));
				}
				detPstmt.close();
				detRS.close();
				
		detQry1 ="select ap.MAKER_ID,ap.CHECKER_ID,ap.MAKER_DTTM,ap.CHECKER_DTTM,(select HEADING_NAMES from AUTH_MASTER AM where AM.AUTH_CODE=ap.AUTH_CODE),ap.MAKER_BRCODE from mob_customer_master_temp mcm,auth_pending ap,MOB_CONTACT_INFO mci  where mcm.REF_NUM=ap.ref_num and  mcm.ID=mci.CUST_ID and  ap.STATUS='C' and mci.MOBILE_NUMBER=? order   by ap.MAKER_DTTM "; 
				logger.debug("Acc details  query [ " + detQry1 + " ]");
					detPstmt1 = connection.prepareStatement(detQry1);
				
				detPstmt1.setString(1, accBean.getTelephone());
				
				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")
							.append(rs.getString(6));
					if (i == 0) {
						billerData.append(eachrow);
					} 
					else 
					{
						billerData.append("#").append(eachrow);
					}
					i++;
					eachrow.delete(0, eachrow.length());
				}
				

				accBean.setMultiData(billerData.toString());

				logger.debug("BEAN DETAILS IN getCustDetailsModify "
						+ billerData);
				detMap.put("AccountData", accBean);

				billerData.delete(0, eachrow.length());
			} else {
				logger.debug("no records get from query");
				responseDTO.addError("No Data found");
			}

			logger.debug("EntityMap [" + detMap + "]");
			responseDTO.setData(detMap);

		} catch (SQLException e) {
			logger.debug("SQLException in Account Fetch Details ["
					+ e.getMessage() + "]");
			responseDTO.addError("An error has occurred ("+e.getMessage()+"). please contact your administrator.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in Account get Detials ["
					+ e.getMessage() + "]");
			responseDTO.addError("internal service error occurred . please contact your administrator.");
			e.printStackTrace();
		} finally
		{

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
