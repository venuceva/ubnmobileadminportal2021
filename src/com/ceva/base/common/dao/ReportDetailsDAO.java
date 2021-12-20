package com.ceva.base.common.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class ReportDetailsDAO {

	private Logger logger = Logger.getLogger(ReportDetailsDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	public ResponseDTO getReportDetailsInfo(RequestDTO requestDTO) {
		logger.debug("Inside GetReportDetailsInfo.. ");

		HashMap<String, Object> reportDataMap = null;
		JSONObject resultJson = null;

		JSONArray regionJSONArray = null;
		JSONArray headofficeJSONArray = null;
		JSONArray locationJSONArray = null;
		JSONArray userIdJSONArray = null;
		JSONArray bankJSONArray = null;
		JSONArray reportNameJSONArray = null;
		JSONArray userJSONArray = null;
		JSONArray txntypeJSONArray = null;
		JSONArray channelJSONArray = null;
		

		Connection connection = null;
		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		JSONObject json = null;

		String genQuery = "";
		String makerId = "";
		String userLevel = "";
		String location = "";
		String regionCode = "";
		try {
			reportDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			responseJSON = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			regionJSONArray = new JSONArray();
			headofficeJSONArray = new JSONArray();
			locationJSONArray = new JSONArray();
			userIdJSONArray = new JSONArray();
			bankJSONArray = new JSONArray();
			reportNameJSONArray = new JSONArray();
			userJSONArray = new JSONArray();
			txntypeJSONArray = new JSONArray();
			channelJSONArray = new JSONArray();

			makerId = requestDTO.getRequestJSON().getString(
					CevaCommonConstants.MAKER_ID);
			// Getting the User Level, Location

			genQuery = "select nvl(user_level,'NO'),location from user_information where common_id = (select common_id from user_login_credentials where login_user_id=?)";
			entityPstmt = connection.prepareStatement(genQuery);
			entityPstmt.setString(1, makerId);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				userLevel = entityRS.getString(1);
				location = entityRS.getString(2);
			}

			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			logger.debug(" The User Location is  [" + location + "]");

			/*if (userLevel.equalsIgnoreCase("REGION")) {
				genQuery = "Select REGION_CODE,REGION FROM REGION_MASTER WHERE REGION_CODE IN (SELECT distinct REGION_CODE FROM  BRANCH_MASTER where OFFICE_CODE in ('"
						+ location + "')) ORDER BY REGION_CODE";
			} else {*/
				genQuery = "Select REGION_CODE,REGION FROM REGION_MASTER WHERE REGION_CODE NOT IN ('01') ORDER BY REGION_CODE";
			/*}*/

			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				regionCode = entityRS.getString(1);
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)+ "-" + entityRS.getString(2));
				regionJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("Region JSON Array [" + regionJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

		/*	if (userLevel.equalsIgnoreCase("REGION")) {
				genQuery = "Select distinct HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where HPO_FLAG='HEAD' AND REGION_CODE='"
						+ regionCode + "'  ORDER BY HPO_DEPARTMENT_CODE";
			} else {*/
				genQuery = "select INSTITUTION_ID,INSTITUTION_NAME from SERVICES_INSTITUTIONS ";
		/*	}*/

			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)
						+ "-" + entityRS.getString(2));
				headofficeJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("Headoffice JSONArray [" + headofficeJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

		/*	if (userLevel.equalsIgnoreCase("BRANCH MANAGER")) {
				genQuery = "Select distinct PBM.OFFICE_CODE,PBM.OFFICE_NAME,(select store_id from store_master where location=PBM.OFFICE_CODE and rownum<2)  from BRANCH_MASTER PBM where HPO_FLAG is null and upper(HPO_NAME) = (select upper(OFFICE_NAME) from POSTA_BRANCH_MASTER where office_code=?) ORDER BY OFFICE_CODE";
			} else {*/
				genQuery = "Select OPERATORID,OPERATORNAME from MN_OPERATORS";
		/*	}*/

			entityPstmt = connection.prepareStatement(genQuery);

			if (userLevel.equalsIgnoreCase("BRANCH MANAGER")) {
				entityPstmt.setString(1, location);
			}

			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)
						+ "-" + entityRS.getString(2));
				locationJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("Location JSONArray [" + locationJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			if (userLevel.equalsIgnoreCase("BRANCH MANAGER")) {
				genQuery = "select B.LOGIN_USER_ID from USER_INFORMATION A, USER_LOGIN_CREDENTIALS B , BRANCH_MASTER PBM where A.COMMON_ID=B.COMMON_ID AND upper(A.USER_LEVEL)='USER' AND  PBM.HPO_NAME in  (select OFFICE_NAME from BRANCH_MASTER where  OFFICE_CODE = (select location from  user_information where common_id in (select common_id from user_login_credentials where login_user_id=?) ) ) AND A.LOCATION=PBM.OFFICE_CODE  AND HPO_FLAG is null";
			} else if (userLevel.equalsIgnoreCase("BRANCHE OFFICE")) {
				genQuery = "select B.LOGIN_USER_ID from USER_INFORMATION A, USER_LOGIN_CREDENTIALS B , BRANCH_MASTER PBM where A.COMMON_ID=B.COMMON_ID AND A.LOCATION=PBM.OFFICE_CODE  AND upper(A.USER_LEVEL)='USER' AND  PBM.LOCATION in (select location from  user_information where common_id in (select common_id from user_login_credentials where login_user_id=?) )";
			} else {
				genQuery = "select B.LOGIN_USER_ID from USER_INFORMATION A, USER_LOGIN_CREDENTIALS B , BRANCH_MASTER PBM where "
						+ "A.COMMON_ID=B.COMMON_ID AND upper(A.USER_LEVEL)='USER' AND A.LOCATION=PBM.OFFICE_CODE";
			}

			entityPstmt = connection.prepareStatement(genQuery);

			if (userLevel.equalsIgnoreCase("BRANCH MANAGER")
					|| userLevel.equalsIgnoreCase("BRANCHE OFFICE")) {
				entityPstmt.setString(1, makerId);
			}

			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1));
				userIdJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("UserId JSON Array [" + userIdJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			genQuery = "Select distinct (decode(STATUS,'S','SUCCESS','P','Pending','F','FAILED')) STATUS,Status st from TRAN_LOG";
			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(2));
//				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)+ "-" + entityRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1));
				bankJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("Bank JSONArray [" + bankJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			genQuery = "SELECT RUG.REPORT_ID,(select REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE REPORT_ID=RUG.REPORT_ID) "
					+ "FROM REPORT_USER_GROUP RUG WHERE RUG.GROUP_ID=(select user_groups from user_information where "
					+ "common_id=(select common_id from user_login_credentials where login_user_id=?)) and RUG.USER_ID=? "
					+ "order by to_number(RUG.REPORT_ID) ";

			entityPstmt = connection.prepareStatement(genQuery);
			entityPstmt.setString(1,requestDTO.getRequestJSON().getString("makerId"));
			entityPstmt.setString(2,requestDTO.getRequestJSON().getString("USER_ID"));
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)+ "-" + entityRS.getString(2));
				reportNameJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("ReportNameJSONArray [" + reportNameJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();
			
			
//			genQuery = "select SERVICE_NAME,SERVICE_CODE from MOB_SERVICE_MASTER where SERVICE_CODE in (select distinct(txn_type) from tran_log where txn_type not in ('MPESAC2B'))";
			genQuery = "select SERVICE_NAME,SERVICE_CODE from MOB_SERVICE_MASTER where SERVICE_CODE in (select distinct(txn_type) from tran_log where txn_type not in ('MPESAB2C'))";

			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1));
				txntypeJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("TXNTYPEJSONArray [" + txntypeJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();
			
			
			genQuery = "select ID,CHANNEL_NAME from mob_channels where id in (select distinct(channelid) from tran_log)";

			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(2));
				channelJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("CHANNELJSONArray [" + channelJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();
			
			
			genQuery = "select distinct(LOGIN_USER_ID) from user_login_credentials UL,auth_pending AP where UL.LOGIN_USER_ID=AP.MAKER_ID";

			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}

			logger.debug("ReportNameJSONArray [" + userJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();
			

			resultJson.put("region", regionJSONArray);
			resultJson.put("headoffice", headofficeJSONArray);
			resultJson.put("location", locationJSONArray);
			resultJson.put("userid", userIdJSONArray);
			resultJson.put("bank", bankJSONArray);
			resultJson.put("reportname", reportNameJSONArray);
			resultJson.put("txntype", txntypeJSONArray);
			resultJson.put("channel", channelJSONArray);
			resultJson.put("uname", userJSONArray);
			resultJson.put("rd_region", regionCode);
			resultJson.put("makerId", makerId);

			reportDataMap.put("reportinfo", resultJson);
			logger.debug("EntityMap [" + reportDataMap.toString() + "]");
			responseDTO.setData(reportDataMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetReportDetailsInfo ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("Exception in GetReportDetailsInfo [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);

			reportDataMap = null;
			resultJson = null;

			regionJSONArray = null;
			headofficeJSONArray = null;
			locationJSONArray = null;
			userIdJSONArray = null;
			bankJSONArray = null;
			reportNameJSONArray = null;
			makerId = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertReportDetailsInfo(RequestDTO requestDTO) {

		logger.debug(" Inside InsertReportDetailsInfo.. ");

		List<String> freq = null;
		List<String> dateTime = null;
		List<String> emailIds = null;
		List<String> reports = null;

		Connection connection = null;
		PreparedStatement entityPstmt = null;

		String genQuery = "";
		int count[] = null;
		try {
			responseDTO = new ResponseDTO();
			freq = (List<String>) requestDTO.getRequestJSON().get("freq");
			dateTime = (List<String>) requestDTO.getRequestJSON().get(
					"dateTime");
			emailIds = (List<String>) requestDTO.getRequestJSON().get(
					"emailIds");
			reports = (List<String>) requestDTO.getRequestJSON().get("reports");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			connection.setAutoCommit(false);
			entityPstmt = connection
					.prepareStatement("DELETE FROM REPORT_SCHEDULER_CONFIG");
			entityPstmt.executeUpdate();
			connection.commit();

			logger.debug("Deleted the records from REPORT_SCHEDULER_CONFIG table.");
			entityPstmt.close();

			genQuery = "insert into REPORT_SCHEDULER_CONFIG(TXN_REF_ID,FREQUENCY, "
					+ "DATE_TIME, EMAIL_IDS, REPORT_NAMES, FLAG, STATUS, CREATED_DATE, "
					+ "CREATED_BY,CRON_EXPRESSION,PERIOD) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?)";

			entityPstmt = connection.prepareStatement(genQuery);

			for (int index = 0; index < freq.size(); index++) {
				String freqS = freq.get(index);
				String dateTimeS = dateTime.get(index);
				String emailIdsS = emailIds.get(index);
				String reportsS = reports.get(index);

				String cronDate = generateCronExpression(freqS, dateTimeS);

				entityPstmt.setString(1, getTxnRefNo());
				entityPstmt.setString(2, freqS);
				entityPstmt.setString(3, dateTimeS);
				entityPstmt.setString(4, emailIdsS);
				entityPstmt.setString(5, reportsS);
				entityPstmt.setString(6, "S");
				entityPstmt.setString(7, "ACTIVE");
				entityPstmt.setDate(8,
						new java.sql.Date(System.currentTimeMillis()));
				entityPstmt.setString(9, "TEMP");
				entityPstmt.setString(10, cronDate);
				entityPstmt.setString(11, getTriggerType(freqS).toUpperCase());

				entityPstmt.addBatch();
			}

			count = entityPstmt.executeBatch();
			connection.commit();
			logger.debug("Inserted count is [" + count + "]");

			responseDTO = getScheduledReportDetails(requestDTO);
			logger.debug("Final Response DTO[" + responseDTO + "]");

		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("SQLException in insertReportDetailsInfo ["
					+ responseDTO + "]");
		} catch (Exception e) {

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Exception in insertReportDetailsInfo [" + responseDTO
					+ "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);

			freq = null;
			dateTime = null;
			emailIds = null;
			reports = null;
		}
		return responseDTO;
	}

	public ResponseDTO getScheduledReportDetails(RequestDTO requestDTO) {
		responseDTO = new ResponseDTO();

		logger.debug("Inside GetScheduledReportDetails.. ");
		HashMap<String, Object> reportDataMap = null;

		JSONObject resultJson = null;

		JSONArray reportArray = null;
		JSONArray reportNameJSONArray = null;
		JSONObject json = null;

		Connection connection = null;
		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;

		String genQuery = "";
		try {

			reportDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			resultJson = new JSONObject();

			reportArray = new JSONArray();
			reportNameJSONArray = new JSONArray();

			genQuery = "select TXN_REF_ID,FREQUENCY,DATE_TIME,EMAIL_IDS,REPORT_NAMES "
					+ "from  REPORT_SCHEDULER_CONFIG order by CREATED_DATE";
			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put("TxnRefNo", entityRS.getString(1));
				json.put("frequencies", entityRS.getString(2));
				json.put("dateTime", entityRS.getString(3));
				json.put("emailids", entityRS.getString(4));
				json.put("reports", entityRS.getString(5));
				reportArray.add(json);
			}

			logger.debug("RegionJSONArray [" + reportArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			genQuery = "Select REPORT_ID,REPORT_DESCRIPTION from REPORT_DETAILS  ORDER BY REPORT_ID";
			entityPstmt = connection.prepareStatement(genQuery);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, entityRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, entityRS.getString(1)
						+ "-" + entityRS.getString(2));
				reportNameJSONArray.add(json);
			}

			logger.debug(" [reportNameJSONArray:::" + reportNameJSONArray + "]");
			entityPstmt.close();
			genQuery = null;
			entityRS.close();

			resultJson.put("reportDetails", reportNameJSONArray);
			resultJson.put("reportSchRecords", reportArray);

			reportDataMap.put("reportschinfo", resultJson);
			logger.debug(" [entityMap:::" + reportDataMap.toString() + "]");
			responseDTO.setData(reportDataMap);
			logger.debug(" [responseDTO:::" + responseDTO + "]");

		} catch (Exception e) {
			logger.debug("Got Exception in  getScheduledReportDetails["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);

			reportDataMap = null;
			resultJson = null;
			genQuery = null;
			reportArray = null;
			reportNameJSONArray = null;
			json = null;
		}

		return responseDTO;
	}

	private static String getTxnRefNo() {
		String uniq = "";
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssS");
		uniq = dateFormat.format(new Date());
		try {
			SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
			String randNo = String.valueOf(randomGenerator.nextInt())
					.replaceAll("-", "");
			uniq += randNo;
		} catch (NoSuchAlgorithmException nsae) {
			uniq += System.currentTimeMillis();
		}

		return uniq;
	}

	private String generateCronExpression(String frequency, String dateTime) {
		logger.debug("Inside GenerateCronExpression.. ");

		logger.debug("Frequency [" + frequency + "]");
		logger.debug("DateTime  [" + dateTime + "]");

		String quartArray[] = { "ALL", "JAN-MAR", "APR-JUN", "JUL-SEP",
				"OCT-DEC" };
		String cronExpression = "";
		String dat = "";
		try {

			dat = dateTime.split("##")[0];

			logger.debug("Daily :::: " + Arrays.asList(dat));

			if (frequency.equalsIgnoreCase("Daily")) {
				logger.debug("Daily..");
				cronExpression = dat;
			} else if (frequency.equalsIgnoreCase("Weekly")) {
				logger.debug("Weekly..");
				cronExpression = dat;
			} else if (frequency.equalsIgnoreCase("Monthly")) {
				logger.debug("Monthly..");
				cronExpression = dat;
			} else if (frequency.equalsIgnoreCase("Quarterly")) {
				logger.debug("Quarterly..");
				for (String quart : quartArray) {
					if (dat.toUpperCase().equalsIgnoreCase(quart)) {
						cronExpression = quart;
					}
				}
			} else if (frequency.equalsIgnoreCase("Yearly")
					|| frequency.equalsIgnoreCase("annually")) {
				logger.debug(" annually of  ");
				cronExpression = dat;
			}
		} catch (Exception e) {
			logger.debug("Exception is GenerateCronExpression ::: "
					+ e.getMessage());
		} finally {
			quartArray = null;
			cronExpression = null;
			dat = null;
		}

		return cronExpression;
	}

	private String getTriggerType(String triggerType) {
		String triggerString = null;
		try {
			if ("DAILY".equalsIgnoreCase(triggerType))
				triggerString = "D";
			else if ("WEEKLY".equalsIgnoreCase(triggerType))
				triggerString = "W";
			else if ("MONTHLY".equalsIgnoreCase(triggerType)) {
				triggerString = "M";
			} else if ("QUARTERLY".equalsIgnoreCase(triggerType)) {
				triggerString = "Q";
			} else if ("BIANNUALLY".equalsIgnoreCase(triggerType))
				triggerString = "B";
			else if ("ANNUALLY".equalsIgnoreCase(triggerType)
					|| "YEARLY".equalsIgnoreCase(triggerType))
				triggerString = "A";
		} catch (Exception e) {

			logger.debug("Exception is GetTriggerType ::: " + e.getMessage());
		}
		return triggerString;
	}

	public ResponseDTO getUserAssignedReports(RequestDTO requestDTO) {

		logger.debug("Inside GetUserAssignedReports... ");
		String userQry = "";
		//String userQry = "SELECT REPORT_ID,REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE  REPORT_ID not in (select REPORT_ID FROM REPORT_USER_GROUP WHERE USER_ID=?)  order by to_number(REPORT_ID)";
		String userQry1 = "SELECT RUG.REPORT_ID,(select REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE REPORT_ID=RUG.REPORT_ID) FROM REPORT_USER_GROUP RUG WHERE RUG.USER_ID=? order by to_number(RUG.REPORT_ID) ";
		String userQry2 = "SELECT UG.GROUP_ID,UG.GROUP_NAME,ULC.LOGIN_USER_ID,UI.EMP_ID "
				+ "FROM  USER_GROUPS UG,USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI  WHERE UI.COMMON_ID = ULC.COMMON_ID and  "
				+ " UI.USER_GROUPS = UG.GROUP_ID and ULC.LOGIN_USER_ID=trim(?) and UG.APPL_CODE='banking' ORDER BY UG.GROUP_ID,ULC.LOGIN_USER_ID";
		Connection connection = null;
		ResultSet userRS = null;
		PreparedStatement userPstmt = null;

		ResultSet grpRS = null;
		PreparedStatement grpPstmt = null;
		
		JSONObject json1 = null;
		JSONObject json = null;

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;
		JSONArray userJSONArray1 = null;

		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null  [" + connection == null + "]");
			
			String grpQry="select  UI.USER_TYPE from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=?";
			grpPstmt = connection.prepareStatement(grpQry);
			grpPstmt.setString(1, requestJSON.getString("USER_ID"));
			grpRS = grpPstmt.executeQuery();
			String userType = null;
			while(grpRS.next()){
				userType = grpRS.getString(1);
			}
			
			if(userType.equals("MA") || userType.equals("MS") || userType.equals("MU")){
				userQry = "SELECT REPORT_ID,REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE  REPORT_ID not in (select REPORT_ID FROM REPORT_USER_GROUP WHERE USER_ID=?)  order by to_number(REPORT_ID)";
			}else{
				userQry = "SELECT REPORT_ID,REPORT_DESCRIPTION FROM REPORT_DETAILS WHERE REPORT_TYPE='BANK' and REPORT_ID not in (select REPORT_ID FROM REPORT_USER_GROUP WHERE USER_ID=?)  order by to_number(REPORT_ID)";
			}
			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();
			userJSONArray1 = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1)
						+ "-" + userRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1)
						+ "-" + userRS.getString(2));
				userJSONArray.add(json);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry :::" + userJSONArray);
			logger.debug("USER_ID :::" + requestJSON.getString("USER_ID"));

			userPstmt = connection.prepareStatement(userQry1);
			userPstmt.setString(1, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json1 = new JSONObject();
				json1.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1)
						+ "-" + userRS.getString(2));
				json1.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1)
						+ "-" + userRS.getString(2));
				userJSONArray1.add(json1);
			}

			userPstmt.close();
			userRS.close();

			logger.debug("UserQry2 [" + userQry2 + "]");

			userPstmt = connection.prepareStatement(userQry2);
			userPstmt.setString(1, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			if (userRS.next()) {
				responseJSON.put("GROUP_ID", userRS.getString(1));
				responseJSON.put("GROUP_NAME", userRS.getString(2));
				responseJSON.put("USER_ID", userRS.getString(3));
				responseJSON.put("EMP_NO", userRS.getString(4));
			}

			responseJSON.put("REPORT_MAIN", userJSONArray);
			responseJSON.put("REPORT_USER_GROUP", userJSONArray1);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeResultSet(userRS);
			DBUtils.closePreparedStatement(userPstmt);

			resultMap = null;
			userJSONArray = null;
			userJSONArray1 = null;
			json1 = null;
			json = null;
		}
		return responseDTO;
	}

	public ResponseDTO insertUserAssignedReports(RequestDTO requestDTO) {

		logger.debug("Inside InsertUserAssignedReports... ");
		String makerId = "";
		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertBankInfoProc = "{call InsertUserReportDetails(?,?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			callableStatement = connection.prepareCall(insertBankInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, requestJSON.getString("GROUP_ID"));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.TXN_DATA));
			callableStatement.setString(4, requestJSON.getString("USER_ID"));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.setString(6,requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);

			logger.debug("ResultCnt from DB :: " + resCnt);

			if (resCnt == 1) {
				responseDTO
						.addMessages("Reports Assigned to the '"
								+ requestJSON.getString("USER_ID")
								+ "' Successfully. ");
			} else {
				responseDTO.addError("Assigning Reports to user failed.");
			}

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(connection);
			makerId = null;
			insertBankInfoProc = null;
		}
		return responseDTO;
	}
}
