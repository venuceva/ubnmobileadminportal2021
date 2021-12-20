package com.ceva.base.common.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class DashBoardLinkDAO {

	Logger logger = Logger.getLogger(DashBoardLinkDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject requestJSON1 = null;
	JSONObject responseJSON = null;
	JSONObject responseJSON1 = null;

	public ResponseDTO getDashBoardLinks(RequestDTO requestDTO) {

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;
		JSONArray userJSONArray1 = null;

		Connection connection = null;

		logger.debug("Inside GetDashBoardLinks method... ");
		String userQry = "";
		// String userQry =
		// "SELECT ORDER_REF_NO,DASHBOARD_LABLE FROM DASHBOARD_MASTER WHERE GROUP_ID='DASHBOARD'   and ORDER_REF_NO not in (select ORDER_REF_NO FROM DASHBOARD_USER_GROUP WHERE GROUP_ID=? and USER_ID=?)  order by to_number(ORDER_REF_NO)";
		String userQry1 = "SELECT distinct ORDER_REF_NO,DASHBOARD_LABLE FROM DASHBOARD_USER_GROUP WHERE GROUP_ID=? and USER_ID=? order by to_number(ORDER_REF_NO) ";
		String userQry2 = "SELECT UG.GROUP_ID,UG.GROUP_NAME,ULC.LOGIN_USER_ID,UI.EMP_ID "
				+ "FROM  USER_GROUPS UG,USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI  WHERE UI.COMMON_ID = ULC.COMMON_ID and  "
				+ " UI.USER_GROUPS = UG.GROUP_ID and ULC.LOGIN_USER_ID=trim(?) and UG.APPL_CODE='banking' ORDER BY UG.GROUP_ID,ULC.LOGIN_USER_ID";

		ResultSet userRS = null;
		ResultSet userRS1 = null;
		ResultSet userRS2 = null;

		JSONObject json1 = null;
		JSONObject json = null;

		PreparedStatement userPstmt = null;
		PreparedStatement userPstmt1 = null;
		PreparedStatement userPstmt2 = null;

		ResultSet grpRS = null;
		PreparedStatement grpPstmt = null;

		try {
			responseJSON = new JSONObject();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("Connection is null [" + connection + "]");

			String grpQry = "select  UI.USER_TYPE from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=?";
			grpPstmt = connection.prepareStatement(grpQry);
			grpPstmt.setString(1, requestJSON.getString("USER_ID"));
			grpRS = grpPstmt.executeQuery();
			String userType = null;
			while (grpRS.next()) {
				userType = grpRS.getString(1);
			}

			if (userType.equals("MA") || userType.equals("MS")
					|| userType.equals("MU")) {
				userQry = "SELECT ORDER_REF_NO,DASHBOARD_LABLE FROM DASHBOARD_MASTER WHERE GROUP_ID='DASHBOARD' and TYPE='MERCHANT'   and ORDER_REF_NO not in (select ORDER_REF_NO FROM DASHBOARD_USER_GROUP WHERE GROUP_ID=? and USER_ID=?)  order by to_number(ORDER_REF_NO)";
			} else {
				userQry = "SELECT ORDER_REF_NO,DASHBOARD_LABLE FROM DASHBOARD_MASTER WHERE GROUP_ID='DASHBOARD' and TYPE='BANK'   and ORDER_REF_NO not in (select ORDER_REF_NO FROM DASHBOARD_USER_GROUP WHERE GROUP_ID=? and USER_ID=?)  order by to_number(ORDER_REF_NO)";
			}

			resultMap = new HashMap<String, Object>();
			userJSONArray = new JSONArray();
			userJSONArray1 = new JSONArray();

			userPstmt = connection.prepareStatement(userQry);
			userPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID));
			userPstmt.setString(2, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			while (userRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, userRS.getString(1)
						+ "-" + userRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, userRS.getString(1)
						+ "-" + userRS.getString(2));
				userJSONArray.add(json);
			}

			logger.debug(" connection userQry :::" + userJSONArray);
			logger.debug(" connection ROLE_GRP_ID :::"+ requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID));

			userPstmt1 = connection.prepareStatement(userQry1);
			userPstmt1.setString(1,requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID));
			userPstmt1.setString(2, requestJSON.getString("USER_ID"));
			userRS1 = userPstmt1.executeQuery();

			while (userRS1.next()) {
				json1 = new JSONObject();
				json1.put(CevaCommonConstants.SELECT_KEY, userRS1.getString(1)+ "-" + userRS1.getString(2));
				json1.put(CevaCommonConstants.SELECT_VAL, userRS1.getString(1)+ "-" + userRS1.getString(2));
				userJSONArray1.add(json1);
				json1.clear();
				json1 = null;
			}

			logger.debug("UserQry2 [" + userQry2 + "]");

			userPstmt.close();
			userRS.close();

			userPstmt = connection.prepareStatement(userQry2);
			userPstmt.setString(1, requestJSON.getString("USER_ID"));
			userRS = userPstmt.executeQuery();

			if (userRS.next()) {
				responseJSON.put("GROUP_ID", userRS.getString(1));
				responseJSON.put("GROUP_NAME", userRS.getString(2));
				responseJSON.put("USER_ID", userRS.getString(3));
				responseJSON.put("EMP_NO", userRS.getString(4));
			}

			responseJSON.put(CevaCommonConstants.DASH_MAIN, userJSONArray);
			responseJSON.put(CevaCommonConstants.DASH_USER_GROUP,userJSONArray1);

			resultMap.put(CevaCommonConstants.RESPONSE_JSON, responseJSON);

			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			logger.debug("SQLException is  InsertDashBoardLinks ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");

		} catch (Exception e) {
			logger.debug("Exception is  InsertDashBoardLinks ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {
			
			DBUtils.closeResultSet(userRS1);
			DBUtils.closeResultSet(userRS);
			DBUtils.closeResultSet(userRS2);
			DBUtils.closePreparedStatement(userPstmt);
			DBUtils.closePreparedStatement(userPstmt1);
			DBUtils.closePreparedStatement(userPstmt2);
			DBUtils.closeConnection(connection);
		}

		return responseDTO;
	}

	public ResponseDTO insertDashBoardLinks(RequestDTO requestDTO) {

		Connection connection = null;

		logger.debug("Inside InsertDashBoardLinks... ");

		String makerId = "";

		CallableStatement callableStatement = null;
		String insertBankInfoProc = "{call DashBoardPkg.dashBoardInsert(?,?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			connection = connection == null ? DBConnector.getConnection():connection;

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			callableStatement = connection.prepareCall(insertBankInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2,
					requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID));
			callableStatement.setString(3,
					requestJSON.getString(CevaCommonConstants.TXN_DATA));
			callableStatement.setString(4, requestJSON.getString("user_id"));
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			callableStatement.setString(6,
					requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);

			logger.debug("ResultCnt from DB [" + resCnt + "]");

			if (resCnt == 1) {
				responseDTO
						.addMessages("DashBoard Links Assigned to Group Successfully. ");
			} else if (resCnt == -1) {
				responseDTO.addError("Bank Information Already Exists. ");
			} else {
				responseDTO.addError("Bank Information Creation failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException is  InsertDashBoardLinks ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} catch (Exception e) {
			logger.debug("Exception is  InsertDashBoardLinks ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {

			try {
				if (callableStatement != null)
					callableStatement.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
		}
		return responseDTO;
	}

	public ResponseDTO getDashboardData(RequestDTO requestDTO)
	{
		JSONObject resultJson = null;

		HashMap<String, Object> businessMap = new HashMap<String,Object>();

		PreparedStatement businessIDPstmt = null;
		ResultSet businessRS = null;
		Connection connection = null;
		JSONArray jsonArr = new JSONArray();
		String result="";
		Double total=0.0;
		responseJSON = new JSONObject();
		responseDTO = new ResponseDTO();


		String query = "select nvl(sum(to_number(REPLACE(AMOUNT,',','')))/100,'0') from TBL_SERVICE_TXNS " +
				"where EXTRACT(DAY from TXNDATE)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from TXNDATE)=EXTRACT(MONTH from SYSDATE) " +
				"and EXTRACT(YEAR from TXNDATE)=EXTRACT(YEAR from SYSDATE) and TXNCODE in ('950000','930000') and RESPONSECODE = '00' ";
		//try{connection = DBConnector.getConnection();}catch(Exception e){}

		try
		{
			
			connection = connection == null ? DBConnector.getConnection():connection;
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("gymcount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][MPESA COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		query="select nvl(sum(to_number(REPLACE(AMOUNT,',','')))/100,'0') from TBL_SERVICE_TXNS " +
				"where EXTRACT(DAY from TXNDATE)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from TXNDATE)=EXTRACT(MONTH from SYSDATE) " +
				"and EXTRACT(YEAR from TXNDATE)=EXTRACT(YEAR from SYSDATE) and SECVICEDESC in ('BC') and RESPONSECODE = '00' ";
		
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}
			responseJSON.put("bookcentercount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][AGENT COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		query="select nvl(sum(to_number(REPLACE(AMOUNT,',','')))/100,'0') from TBL_SERVICE_TXNS where" +
				" EXTRACT(DAY from TXNDATE)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from TXNDATE)=EXTRACT(MONTH from SYSDATE) " +
				"and EXTRACT(YEAR from TXNDATE)=EXTRACT(YEAR from SYSDATE) and SECVICEDESC in ('LB','LR','LPF') and RESPONSECODE = '00' ";
		
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}
			
			responseJSON.put("librarycount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][PARKING COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		query="select nvl(sum(to_number(REPLACE(AMOUNT,',','')))/100,'0') from TBL_SERVICE_TXNS where " +
				"EXTRACT(DAY from TXNDATE)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from TXNDATE)=EXTRACT(MONTH from SYSDATE) " +
				"and EXTRACT(YEAR from TXNDATE)=EXTRACT(YEAR from SYSDATE) and SECVICEDESC in ('CO') and RESPONSECODE = '00' ";
		
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("cashofficecount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][SBP COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		query = "select count(*) FROM TBL_CANTEEN_TXNS where " +
				"EXTRACT(DAY from TXNDATE)=EXTRACT(DAY from SYSDATE) and " +
				"EXTRACT(MONTH from TXNDATE)=EXTRACT(MONTH from SYSDATE) and EXTRACT(YEAR from TXNDATE)=EXTRACT(YEAR from SYSDATE) " +
				"and CANTEENTYPE in ('920000','920100')  and RESPONSECODE='00'";
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("cafteriacount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][SBP COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		/*query="select nvl(count(CHECKIN),'0') from TBL_STUDENT_INOUT where " +
				"EXTRACT(DAY from CHECKIN)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from CHECKIN)=EXTRACT(MONTH from SYSDATE) " +
				"and EXTRACT(YEAR from CHECKIN)=EXTRACT(YEAR from SYSDATE) and STATUS in ('CheckIn','CheckOut')";*/
		
		query = "select nvl(count(STATUS),'0') from TBL_STUDENT_INOUT where"
	           +" EXTRACT(DAY from CHECKIN)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from CHECKIN)=EXTRACT(MONTH from SYSDATE)"
	           +" and EXTRACT(YEAR from CHECKIN)=EXTRACT(YEAR from SYSDATE) and STATUS in ('CheckIn','CheckOut')";
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("securitycount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][SBP COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		query="select count(*) from ATTENDANCE where (L1='1' or L2='1' or  L3='1' or  L4='1' or  L5='1' or  L6='1' or  L7='1' or " +
				"L8='1' or  L9='1' or  L10='1' or  L11='1' or  L12='1' or  L13='1' or  L14='1' or  L15='1' or  L16='1' or  L17='1' or " +
				"L18='1' or  L19='1' or  L20='1' or  L21='1' or  L22='1' or  L23='1' or  L24='1' or  L25='1' or  L26='1' or  L27='1' or " +
				"L28='1' or  L29='1' or  L30='1' or  L31='1' or  L32='1' or  L33='1' or  L34='1' or  L35='1' or  L36='1') " ;
				
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("attendancecount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][SBP COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		query="select count(*) from STUDENT_WALLET_INFO where " +
				"EXTRACT(DAY FROM MAKERDTTM) = EXTRACT(DAY FROM SYSDATE) AND EXTRACT(MONTH FROM MAKERDTTM) = EXTRACT(MONTH FROM SYSDATE) " +
				"and EXTRACT(YEAR FROM MAKERDTTM) = EXTRACT(YEAR FROM SYSDATE) and STATUS = 'R'";
		try
		{
			businessIDPstmt = connection.prepareStatement(query);

			businessRS = businessIDPstmt.executeQuery();

			if(businessRS.next())
			{
				result = numberFormat(businessRS.getString(1),"###,###.###",2);
			}

			responseJSON.put("regcount",result);
			logger.debug("[SingleBusinessPermitDAO][sbpInitials][SBP COUNT : "+result+"]");

			businessIDPstmt.close();
			businessRS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		finally {
			DBUtils.closeResultSet(businessRS);
			DBUtils.closePreparedStatement(businessIDPstmt);
			DBUtils.closeConnection(connection);
		}

		try {
			responseJSON.put("totalcollections", numberFormat(String.valueOf(Math.round(total)).toString(),"###,###.###",2));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		businessMap.put("RESULT", responseJSON);
		responseDTO.setData(businessMap);

		return responseDTO;
	}
	
	public static String numberFormat(String number, String format, int scale)
			   throws Exception {
			  BigDecimal as = null;
			  NumberFormat nf = null;
			  DecimalFormat df = null;

			  String actualData = " ";
			  try {
			   as = new BigDecimal(number.toString()).setScale(scale,
			     RoundingMode.HALF_UP);

			   nf = NumberFormat.getCurrencyInstance();
			   df = (DecimalFormat) nf;
			   // df.applyPattern("###,###.###");
			   df.applyPattern(format);

			   actualData = df.format(as.doubleValue());

			   if (actualData.indexOf(".") != -1) {

			   }

			  } catch (Exception e) {
			   throw new Exception("Parsing format is invalid :::: "
			     + e.getMessage());
			  }

			  return actualData;
			 }
	
	
	/*public ResponseDTO getAssinedDashLinks(RequestDTO requestDTO) {

		logger.debug("Inside GetAssinedDashLinks.. ");

		ResultSet userRS1 = null;
		ResultSet dashqueryrs = null;
		ResultSet merchantRS = null;

		PreparedStatement businessIDPstmt = null;
		ResultSet businessRS = null;
		String result=null;
		
		Connection connection = null;
		CallableStatement callableStmt = null;

		PreparedStatement userPstmt1 = null;

		HashMap<String, Object> resultMap = null;

		JSONArray userJSONArray = null;
		JSONObject resultJson = null;
		
		
		ResultSet userRS = null;
		ResultSet userRS2 = null;

		JSONObject json1 = null;
		JSONObject json = null;

		PreparedStatement userPstmt = null;
		PreparedStatement userPstmt2 = null;

		JSONObject userJson = null;
		String fridentifiedqry="select sum(decode(mon_status,'V',1,0)) V ,sum(decode(mon_status,'R',1,0)) R from fraud_tbl";

		String userQry1 = "SELECT distinct ORDER_REF_NO,DASHBOARD_LABLE "
				+ "FROM DASHBOARD_USER_GROUP "
				+ "WHERE GROUP_ID=? and USER_ID=? order by to_number(ORDER_REF_NO)";

		// Added New Column For HalfPageDashboard on 26-06-2014
		String halfpageQry = "{call DashBoardPkg.getHalfPage(?,?,?,?,?)}";
		String dashboarditem = "{call DashBoardPkg.getDashResult(?,?,?,?)}";
		String maker_id = "";
		String group_id = "";
		String locationName = "";
		


		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			responseJSON = new JSONObject();

			maker_id = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			group_id = requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID);
			// locationName = requestJSON.getString("loc_name");

			logger.debug(" Request JSON in DashBoard DAO [" + requestJSON + "]");
			userJSONArray = new JSONArray();
			userJson = new JSONObject();
			resultJson = new JSONObject();
			resultMap = new HashMap<String, Object>();

			//connection = DBConnector.getConnection();
			connection = connection == null ? DBConnector.getConnection():connection;
			logger.debug("connection is null [" + connection == null + "]");
			
			String query = "select 1 from dual";
			//try{connection = DBConnector.getConnection();}catch(Exception e){}

			try
			{
				businessIDPstmt = connection.prepareStatement(query);

				businessRS = businessIDPstmt.executeQuery();

				if(businessRS.next())
				{
					result = numberFormat(businessRS.getString(1),"###,###.###",2);
				}

				responseJSON.put("gymcount",result);
				logger.debug("[SingleBusinessPermitDAO][ : "+result+"]");
				resultMap.put("gymcount",responseJSON);
				businessIDPstmt.close();
				businessRS.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			try{
				
				userPstmt = connection.prepareStatement(fridentifiedqry);
				userRS = userPstmt.executeQuery();

				if (userRS.next()) {
					responseJSON.put("VER_CNT", userRS.getString(1));
					responseJSON.put("IDE_CNT", userRS.getString(2));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

			try {
				userPstmt1 = connection.prepareStatement(userQry1);
				userPstmt1.setString(1,
						requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID));
				userPstmt1.setString(2, maker_id);
				userRS1 = userPstmt1.executeQuery();

				while (userRS1.next()) {
					callableStmt = connection.prepareCall(dashboarditem);
					callableStmt.setString(1, maker_id);
					callableStmt.setString(2, userRS1.getString(1));
					callableStmt.registerOutParameter(3, OracleTypes.VARCHAR);
					callableStmt.registerOutParameter(4, OracleTypes.INTEGER);

					callableStmt.execute();

					String ar[] = callableStmt.getString(3).split("#");

					userJson.put(CevaCommonConstants.SELECT_KEY, ar[0]);
					userJson.put(CevaCommonConstants.SELECT_VAL, ar[1]);
					userJSONArray.add(userJson);
					userJson.clear();

					responseJSON.put(CevaCommonConstants.DASHBOARD_LINKS,
							userJSONArray);
					resultMap.put(CevaCommonConstants.DASHBOARD_LINKS,
							responseJSON);

					callableStmt.close();
					ar = null;
				}
			} catch (Exception e) {
				logger.debug("Exception in getting DashboardLabel ::: "
						+ e.getMessage());

			}

			userJSONArray.clear();
			logger.debug("Dashboard Link's Loaded Into Array.");

			try {
				callableStmt = connection.prepareCall(halfpageQry);
				callableStmt.setString(1, maker_id);
				callableStmt.setString(2, group_id);
				callableStmt.setString(3, locationName);
				callableStmt.registerOutParameter(4, OracleTypes.CURSOR);
				callableStmt.registerOutParameter(5, OracleTypes.VARCHAR);

				callableStmt.execute();
				logger.debug(" block executed successfully "
						+ "with error_message[" + callableStmt.getString(5)
						+ "]");

				merchantRS = (ResultSet) callableStmt.getObject(4);

				logger.debug("MerchantRS is not null [" + merchantRS == null
						+ "]");

				while (merchantRS.next()) {
					userJson.put("MOBILENUMBER", merchantRS.getString(1));
					userJson.put("DATETIME", merchantRS.getString(2));
					userJson.put("STATUS", merchantRS.getString(3));
					userJson.put("AMOUNT", merchantRS.getString(4));
					userJson.put("RRN", merchantRS.getString(5));
					userJson.put("PY_SHRTCODE", merchantRS.getString(6));
					userJson.put("TXNTYPE", merchantRS.getString(7));
					userJson.put("INSTITUTE", merchantRS.getString(8));
					userJson.put("OPERATOR", merchantRS.getString(9));
					userJson.put("TXNID", merchantRS.getString(10));
					userJson.put("ACCOUNT", merchantRS.getString(11));
					userJSONArray.add(userJson);
					userJson.clear();
				}
				logger.debug("USER JSON VALUES in DASHBOARDLINK DAO ["
						+ userJson + "]");
			} catch (Exception e) {
				logger.debug("Exception in getting Live Data ::: "
						+ e.getMessage());
			}

			resultJson.put(CevaCommonConstants.HALF_PAGE, userJSONArray);

			logger.debug("Live Transactions Loaded Into Array.");

			callableStmt.close();
			merchantRS.close();
			userJSONArray.clear();

			try {
				halfpageQry = "{call DashBoardPkg.getAnnouncement(?,?,?,?)}";
				callableStmt = connection.prepareCall(halfpageQry);
				callableStmt.setString(1, maker_id);
				callableStmt.registerOutParameter(2, OracleTypes.VARCHAR);
				callableStmt.registerOutParameter(3, OracleTypes.VARCHAR);
				callableStmt.registerOutParameter(4, OracleTypes.NUMBER);
				callableStmt.execute();

				logger.debug("Announcement block executed successfully "
						+ "with error_message[" + callableStmt.getInt(4) + "]");

				resultJson.put("USER_ANNOUNCEMENT", callableStmt.getString(2));
				resultJson.put("GROUP_ANNOUNCEMENT", callableStmt.getString(3));
			} catch (Exception e) {
				logger.debug("Exception in getting AnnouncementData ::: "
						+ e.getMessage());
			}

			callableStmt.close();
			merchantRS.close();
			userJSONArray.clear();

			logger.debug("Announcement Loaded Into Array.");
			
			
			

			resultMap.put(CevaCommonConstants.HALF_PAGE, resultJson);
			responseDTO.setData(resultMap);

		} catch (SQLException e) {
			logger.debug("The SQLException is  [" + e.getMessage() + "]");
		} catch (Exception e) {
			logger.debug("The Exception is   [" + e.getMessage() + "]");
		} finally {


			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeResultSet(dashqueryrs);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeCallableStatement(callableStmt);
			DBUtils.closePreparedStatement(userPstmt1);
			DBUtils.closeConnection(connection);

			resultMap = null;

			userJSONArray = null;
			resultJson = null;
			userQry1 = null;
			halfpageQry = null;
			dashboarditem = null;
			maker_id = null;
			group_id = null;
			locationName = null;
		}

		return responseDTO;
	}*/
	
public ResponseDTO getAssinedDashLinks(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [IncomeMTFileDAO][incomeingFilesDetails]");

		logger.debug("Inside  GetBankDetails.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		JSONArray IncomeMTFilesJSONArray1 = null;

		Connection connection = null;

		PreparedStatement servicePstmt = null;

		ResultSet serviceIdRS = null;
		ResultSet serviceRS = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			IncomeMTFilesJSONArray1 = new JSONArray();
			
			String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			
			/*StringBuilder sb=new StringBuilder();
			// unprocessed
			sb.append("(SELECT count(*) FROM FUND_TRANSFER_TBL),");
			// verified files
			sb.append("(SELECT count(*) FROM FUND_TRANSFER_TBL) ");*/
			// processed
			//sb.append("(SELECT count(*) FROM ACCOUNT_OPEN WHERE RESPONSECODE='0' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),TO_CHAR(sysdate-2,'dd-MON-yy'),TO_CHAR(sysdate-1,'dd-MON-yy'),TO_CHAR(sysdate,'dd-MON-yy'), ");
			
			//sb.append("nvl((SELECT round(sum(DEBITAMOUNT)) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND CRDR_FLAG='C' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
			// verified files
			//sb.append("nvl((SELECT round(sum(DEBITAMOUNT)) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND CRDR_FLAG='D' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0')");
			
			
			//String userReport =" select "+sb.toString()+" from dual";
			
			/*String userReport =" select * from ( select NVL(ACCOUNTNO,' '),NVL(TRNS_AMT,' '),NVL(decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),' '),NVL(PAYMENTREFERENCE,' '),(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),NVL(CHANNEL,' '),NVL(to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss'),' '),NVL(RESPONSEMESSAGE,' ') from FUND_TRANSFER_TBL  order by TRANS_DATE desc)  WHERE rownum<=10";

			servicePstmt = connection.prepareStatement(userReport);
			serviceRS = servicePstmt.executeQuery();
			JSONObject json = new JSONObject();
			
			 while(serviceRS.next())
				{
				 json=new JSONObject();
				 json.put("ACCOUNTNO",serviceRS.getString(1));
				 json.put("CREDITAMOUNT",serviceRS.getString(2));
				 json.put("CREDITCREDITINDICATOR",serviceRS.getString(3));
				 json.put("CREDITPAYMENTREFERENCE",serviceRS.getString(4));
				 json.put("BATCHID",serviceRS.getString(5));
				 json.put("CHANNEL",serviceRS.getString(6));
				 json.put("TRANS_DATE",serviceRS.getString(7));
				 json.put("RESPONSEMESSAGE",serviceRS.getString(8));
				 
				 IncomeMTFilesJSONArray.add(json);
				}
			 servicePstmt.close();
			 serviceRS.close();
			 
			 StringBuilder sb=new StringBuilder();
			 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE in ('FUND_TRNS_OTCR','FUND_OTHER_REVERSAL','PAY_BILL_REVERSAL','RECHARGE','PAY_BILL','CUSTFUNDTRANSF','REVERSAL')),'0'),");
			 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER WHERE  TO_CHAR(TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
			 sb.append("nvl((SELECT count(*) FROM MOB_CUSTOMER_MASTER WHERE  TO_CHAR(DATE_CREATED, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
			 sb.append("nvl((SELECT  COUNT(distinct A.NET_ID) FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE AND TO_CHAR(TXNDATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND (CML.MENU_ACTION='LDAP_AUTH' or CML.MENU_ACTION='MOBILE_USSD')),'0'),");
			 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
			 
			 String Qry = "select UI.CLUSTER_ID,(SELECT STATUS from CONFIG_DATA where  key_group='USER_DESIGNATION' and KEY_VALUE=UI.USER_TYPE),UI.USER_TYPE from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND ULC.LOGIN_USER_ID='"+makerid+"'";
			 servicePstmt = connection.prepareStatement(Qry);
			 serviceRS = servicePstmt.executeQuery();
			 if (serviceRS.next()) {
				
				 if((serviceRS.getString(2)).contains("INITIATE")){
					 sb.append("nvl((SELECT count(*) FROM AUTH_PENDING WHERE MAKER_ID='"+makerid+"' AND ACTION='CHECKER'),'0')"); 
				 }else if((serviceRS.getString(2)).contains("AUTHORIZE")){
					 sb.append("nvl((SELECT count(*) FROM AUTH_PENDING WHERE MAKER_BRCODE='"+serviceRS.getString(1)+"' AND ACTION='MAKER'),'0')");  
				 }else{
					 sb.append("'0'"); 
				 }
				 
			 }
			 servicePstmt.close();
			 serviceRS.close();
			 
			
			 String userReport1 =" select "+sb.toString()+" from dual";
			 
			servicePstmt = connection.prepareStatement(userReport1);
			serviceRS = servicePstmt.executeQuery();
			while(serviceRS.next())
			{
			 JSONObject json1 = new JSONObject();
			 json1.put("LOGIN_USERS",serviceRS.getString(4));
			 json1.put("NEW_USER",serviceRS.getString(3));
			 json1.put("NOTIFICATION",serviceRS.getString(6));
			 json1.put("ORDER",serviceRS.getString(2));
			 json1.put("TRANS_DETAILS",serviceRS.getString(1));
			 json1.put("LOCATOR",serviceRS.getString(5));
			 IncomeMTFilesJSONArray1.add(json1);
			}*/
			resultJson.put("Files_List", IncomeMTFilesJSONArray);
			resultJson.put("Files_List1", IncomeMTFilesJSONArray1);

			serviceDataMap.put("Files_List", resultJson);

			logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetBankDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("SQLException in GetBankDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceIdRS != null)
					serviceIdRS.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			serviceDataMap = null;
			resultJson = null;
			IncomeMTFilesJSONArray.clear();
			IncomeMTFilesJSONArray = null;
		}

		return responseDTO;
	}



public ResponseDTO userAssinedDashLinksHome(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [IncomeMTFileDAO][incomeingFilesDetails]");

	logger.debug("Inside  GetBankDetails.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	JSONArray IncomeMTFilesJSONArray1 = null;

	Connection connection = null;

	PreparedStatement servicePstmt = null;

	ResultSet serviceIdRS = null;
	ResultSet serviceRS = null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();

		logger.debug("Connection is null [" + connection == null + "]");

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		IncomeMTFilesJSONArray1 = new JSONArray();
		
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		/*StringBuilder sb=new StringBuilder();
		// unprocessed
		sb.append("(SELECT count(*) FROM FUND_TRANSFER_TBL),");
		// verified files
		sb.append("(SELECT count(*) FROM FUND_TRANSFER_TBL) ");*/
		// processed
		//sb.append("(SELECT count(*) FROM ACCOUNT_OPEN WHERE RESPONSECODE='0' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),TO_CHAR(sysdate-2,'dd-MON-yy'),TO_CHAR(sysdate-1,'dd-MON-yy'),TO_CHAR(sysdate,'dd-MON-yy'), ");
		
		//sb.append("nvl((SELECT round(sum(DEBITAMOUNT)) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND CRDR_FLAG='C' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
		// verified files
		//sb.append("nvl((SELECT round(sum(DEBITAMOUNT)) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND CRDR_FLAG='D' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0')");
		
		
		//String userReport =" select "+sb.toString()+" from dual";
		
		String userReport =" select * from ( select NVL(ACCOUNTNO,' '),NVL(TRNS_AMT,' '),NVL(decode(CRDR_FLAG,'C','CREDIT','D','DEBIT'),' '),NVL(PAYMENTREFERENCE,' '),(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE),NVL(CHANNEL,' '),NVL(to_char(TRANS_DATE,'dd-mm-yyyy hh:mm:ss'),' '),NVL(RESPONSEMESSAGE,' ') from FUND_TRANSFER_TBL  order by TRANS_DATE desc)  WHERE rownum<=10";

		servicePstmt = connection.prepareStatement(userReport);
		serviceRS = servicePstmt.executeQuery();
		JSONObject json = new JSONObject();
		
		 while(serviceRS.next())
			{
			 json=new JSONObject();
			 json.put("ACCOUNTNO",serviceRS.getString(1));
			 json.put("CREDITAMOUNT",serviceRS.getString(2));
			 json.put("CREDITCREDITINDICATOR",serviceRS.getString(3));
			 json.put("CREDITPAYMENTREFERENCE",serviceRS.getString(4));
			 json.put("BATCHID",serviceRS.getString(5));
			 json.put("CHANNEL",serviceRS.getString(6));
			 json.put("TRANS_DATE",serviceRS.getString(7));
			 json.put("RESPONSEMESSAGE",serviceRS.getString(8));
			 
			 IncomeMTFilesJSONArray.add(json);
			}
		 servicePstmt.close();
		 serviceRS.close();
		 
		 StringBuilder sb=new StringBuilder();
		 sb.append("nvl((SELECT count(*) FROM FUND_TRANSFER_TBL WHERE RESPONSECODE='00' AND TO_CHAR(TRANS_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND TRANS_TYPE in ('FUND_TRNS_OTCR','FUND_OTHER_REVERSAL','PAY_BILL_REVERSAL','RECHARGE','PAY_BILL','CUSTFUNDTRANSF','REVERSAL')),'0'),");
		 sb.append("nvl((SELECT count(*) FROM ONLINE_PURCHASE_MASTER WHERE  TO_CHAR(TXN_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
		 sb.append("nvl((SELECT count(*) FROM MOB_CUSTOMER_MASTER WHERE  TO_CHAR(DATE_CREATED, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
		 sb.append("nvl((SELECT  COUNT(distinct A.NET_ID) FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE AND TO_CHAR(TXNDATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy') AND (CML.MENU_ACTION='LDAP_AUTH' or CML.MENU_ACTION='MOBILE_USSD')),'0'),");
		 sb.append("nvl((SELECT count(*) FROM CUST_ACCEPTS WHERE  TO_CHAR(TNX_DATE, 'dd-MON-yy')=TO_CHAR(sysdate,'dd-MON-yy')),'0'),");
		 
		 String Qry = "select UI.CLUSTER_ID,(SELECT STATUS from CONFIG_DATA where  key_group='USER_DESIGNATION' and KEY_VALUE=UI.USER_TYPE),UI.USER_TYPE from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID = UI.COMMON_ID AND ULC.LOGIN_USER_ID='"+makerid+"'";
		 servicePstmt = connection.prepareStatement(Qry);
		 serviceRS = servicePstmt.executeQuery();
		 if (serviceRS.next()) {
			
			 if((serviceRS.getString(2)).contains("INITIATE")){
				 sb.append("nvl((SELECT count(*) FROM AUTH_PENDING WHERE MAKER_ID='"+makerid+"' AND ACTION='CHECKER'),'0')"); 
			 }else if((serviceRS.getString(2)).contains("AUTHORIZE")){
				 sb.append("nvl((SELECT count(*) FROM AUTH_PENDING WHERE MAKER_BRCODE='"+serviceRS.getString(1)+"' AND ACTION='MAKER'),'0')");  
			 }else{
				 sb.append("'0'"); 
			 }
			 
		 }
		 servicePstmt.close();
		 serviceRS.close();
		 
		
		 String userReport1 =" select "+sb.toString()+" from dual";
		 
		servicePstmt = connection.prepareStatement(userReport1);
		serviceRS = servicePstmt.executeQuery();
		while(serviceRS.next())
		{
		 JSONObject json1 = new JSONObject();
		 json1.put("LOGIN_USERS",serviceRS.getString(4));
		 json1.put("NEW_USER",serviceRS.getString(3));
		 json1.put("NOTIFICATION",serviceRS.getString(6));
		 json1.put("ORDER",serviceRS.getString(2));
		 json1.put("TRANS_DETAILS",serviceRS.getString(1));
		 json1.put("LOCATOR",serviceRS.getString(5));
		 IncomeMTFilesJSONArray1.add(json1);
		}
		resultJson.put("Files_List", IncomeMTFilesJSONArray);
		resultJson.put("Files_List1", IncomeMTFilesJSONArray1);

		serviceDataMap.put("Files_List", resultJson);

		logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	} catch (SQLException e) {
		logger.debug("SQLException in GetBankDetails [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} catch (Exception e) {

		logger.debug("SQLException in GetBankDetails [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			if (servicePstmt != null)
				servicePstmt.close();
			if (serviceIdRS != null)
				serviceIdRS.close();
			if (serviceRS != null)
				serviceRS.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

}
