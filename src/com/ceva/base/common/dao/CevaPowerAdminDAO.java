package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.CevaTokenGenerator;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.security.PasswordValidation;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;

public class CevaPowerAdminDAO {

	private Logger logger = Logger.getLogger(CevaPowerAdminDAO.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	public JSONObject getUniqueUserID(String fname,String sname) throws Exception
	{
		JSONObject jres = new JSONObject();
		Connection connection = null;
		CallableStatement callableStatement = null;

		try
		{
			String proc = "{call GENERATEUSERID(?,?,?,?)}";
			connection = DBConnector.getConnection();
			callableStatement = connection.prepareCall(proc);


			callableStatement.setString(1, fname);
			callableStatement.setString(2, sname);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStatement.execute();

			String userid = callableStatement.getString(3);
			String error = callableStatement.getString(4);

			jres.put("userid", userid);
			jres.put("error", error);

			logger.debug("[CevaPowerAdminDAO][getUniqueUserID][Values : "+userid+"  "+error+" ]");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
		}

		return jres;
	}

	public String isUserPresent(String userid)
	{
		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		int count=0;
		String result=null;
		try
		{
			String query="select count(*) from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID=?";

			connection = DBConnector.getConnection();
			merchantPstmt = connection.prepareStatement(query);
			merchantPstmt.setString(1, userid);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				count = merchantRS.getInt(1);
			}

			merchantPstmt.close();
			merchantRS.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(count > 0)
			result="NO";
		else
			result="YES";

		return result;
	}

	public ResponseDTO dashboardUsers(RequestDTO requestDTO) {

		logger.debug("Inside DashboardUsers... ");

		Connection connection = null;
		HashMap<String, Object> merchantMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray userGroupsJSONArray = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String merchantQry = "SELECT UG.GROUP_ID,ULC.LOGIN_USER_ID,UG.GROUP_NAME,ULC.MAKER_ID,to_char(ULC.MAKER_DTTM,'DD-MM-YYYY'),nvl(UI.LOCATION,'000-UBN HEAD OFFICE '),nvl(UI.STORE_ID,'Bank User'),UI.EMAIL,UI.MOBILE_NO "
				+ "FROM  USER_GROUPS UG,USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI  WHERE UI.COMMON_ID = ULC.COMMON_ID and  "
				+ " UI.USER_GROUPS = UG.GROUP_ID and UG.APPL_CODE='banking' ORDER BY UG.GROUP_ID,ULC.LOGIN_USER_ID";
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
				json.put(CevaCommonConstants.ROLE_GRP_ID,
						merchantRS.getString(1));
				json.put(CevaCommonConstants.USER_ID, merchantRS.getString(2));
				json.put(CevaCommonConstants.ROLE_GRP_NAME,
						merchantRS.getString(3));
				json.put(CevaCommonConstants.MAKER_ID, merchantRS.getString(4));
				json.put(CevaCommonConstants.MAKER_DATE,
						merchantRS.getString(5));
				json.put("merchant_id",
						(merchantRS.getString(6)).split("-")[0]);
				json.put("store_id",	(merchantRS.getString(6)).split("-")[1]);
				json.put("email",	merchantRS.getString(8));
				json.put("mobile",	merchantRS.getString(9));
				userGroupsJSONArray.add(json);
				json.clear();
				json = null;
			}

			resultJson.put("USER_GROUPS", userGroupsJSONArray);
			merchantMap.put("USER_ACCESS_MNG", resultJson);

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

	public ResponseDTO getAllUserGrps(RequestDTO requestDTO) {

		logger.debug("Inside GetAllUserGrps... ");

		HashMap<String, Object> merchantMap = null;

		JSONObject resultJson = null;
		JSONObject json = null;

		JSONArray userGroupsJSONArray = null;
		JSONArray usersJSONArray = null;

		Connection connection = null;

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		List<String> groupsArray = null;
		List<String> usersSet = null;
		
		String usertype = "";
		String merchantQry="";

		
		
		
		
		String data[] = null;
		String fName = "";
		String lName = "";
		String rights = "";
		int count = 0;
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			usertype = requestJSON.getString("USER_TYPE");
			
			
			//WHERE SUBSTR(user_level,0,INSTR(user_level, '-')-1) in ('SDM','UDM','BDM','CUR','DSA')
			logger.debug("Request JSON is [" + requestJSON + "]");
			if(usertype.equalsIgnoreCase("BADM")){
				
				merchantQry = "SELECT GROUP_ID,GROUP_NAME,substr(user_level,instr(user_level,'-')+1,LENGTH(user_level)),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY') FROM  "
				+ "USER_GROUPS WHERE SUBSTR(user_level,0,INSTR(user_level, '-')-1) not in ('BADM') ORDER BY MAKER_DTTM";
			}else if(usertype.startsWith("SDM")){
				
				merchantQry = "SELECT GROUP_ID,GROUP_NAME,substr(user_level,instr(user_level,'-')+1,LENGTH(user_level)),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY') FROM  "
				+ "USER_GROUPS WHERE SUBSTR(user_level,0,INSTR(user_level, '-')-1) not in ('BADM') ORDER BY MAKER_DTTM";
			}else if(usertype.startsWith("UDM")){
				
				merchantQry = "SELECT GROUP_ID,GROUP_NAME,substr(user_level,instr(user_level,'-')+1,LENGTH(user_level)),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY') FROM  "
				+ "USER_GROUPS WHERE SUBSTR(user_level,0,INSTR(user_level, '-')-1) not in ('BADM','SDM') ORDER BY MAKER_DTTM";
			}else if(usertype.startsWith("ADM")){
				
				merchantQry = "SELECT GROUP_ID,GROUP_NAME,substr(user_level,instr(user_level,'-')+1,LENGTH(user_level)),MAKER_ID,to_char(MAKER_DTTM,'DD-MM-YYYY') FROM  "
				+ "USER_GROUPS WHERE SUBSTR(user_level,0,INSTR(user_level, '-')-1) like 'BRN%' ORDER BY MAKER_DTTM";
			}
			

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			merchantMap = new HashMap<String, Object>(100);
			resultJson = new JSONObject();
			groupsArray = new ArrayList<String>();
			usersSet = new ArrayList<String>();

			userGroupsJSONArray = new JSONArray();
			usersJSONArray = new JSONArray();

			json = new JSONObject();


			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {

				json.put(CevaCommonConstants.ROLE_GRP_ID,merchantRS.getString(1));
				json.put("USER_LEVEL",merchantRS.getString(2));
				json.put(CevaCommonConstants.ROLE_GRP_NAME,merchantRS.getString(3));
				json.put(CevaCommonConstants.MAKER_ID, merchantRS.getString(4));
				json.put(CevaCommonConstants.MAKER_DATE,merchantRS.getString(5));
				groupsArray.add(merchantRS.getString(1));
				userGroupsJSONArray.add(json);
				json.clear();
			}

			resultJson.put("USER_GROUPS", userGroupsJSONArray);

			merchantPstmt.close();
			merchantRS.close();


			logger.debug("GroupsArray is [" + groupsArray + "]");
			for (String getGrps : groupsArray) {
				merchantQry = "Select A.LOGIN_USER_ID,B.USER_NAME,B.EMP_ID,B.EMAIL "
						+ ",to_char(B.EXPIRY_DATE,'DD-MM-YYYY'),decode(B.USER_STATUS,'A','Active','L','De-Active','F','Active','N','Un-Authorize','MU','De-Active',B.USER_STATUS),B.MERCHANT_ID,GM.GROUP_TYPE from USER_LOGIN_CREDENTIALS A,USER_INFORMATION B,USER_GROUPS GM "
						+ "WHERE A.COMMON_ID=B.COMMON_ID AND UPPER(B.USER_GROUPS) =? AND GM.GROUP_ID=B.USER_GROUPS ";
				try {
					merchantPstmt = connection.prepareStatement(merchantQry);
					merchantPstmt.setString(1, getGrps.toUpperCase());
					merchantRS = merchantPstmt.executeQuery();

					while (merchantRS.next()) {
						json.put(CevaCommonConstants.ROLE_GRP_ID, getGrps);
						json.put(CevaCommonConstants.USER_ID,
								merchantRS.getString(1));
						data = merchantRS.getString(2).split("\\ ");
						fName = "";
						lName = "";
						if (data.length == 1) {
							fName = data[0];
						} else {
							fName = data[0];
							lName = data[1];
						}
						json.put(CevaCommonConstants.F_NAME, fName);
						json.put(CevaCommonConstants.L_NAME, lName);
						json.put(CevaCommonConstants.EMPLOYEE_NO,merchantRS.getString(3));
						json.put(CevaCommonConstants.EMAIL,merchantRS.getString(4));
						json.put(CevaCommonConstants.EXPIRY_DATE,merchantRS.getString(5));
						json.put("user_status", merchantRS.getString(6));
						json.put("merchant_id", merchantRS.getString(7));
						json.put("user_type", merchantRS.getString(8));
						usersJSONArray.add(json);
						usersSet.add(merchantRS.getString(1));
						json.clear();
					}

					if (usersJSONArray != null && usersJSONArray.size() > 0) {
						resultJson.put(getGrps + "_USERS", usersJSONArray);
						usersJSONArray.clear();
					}

					merchantPstmt.close();
					merchantRS.close();

				} catch (SQLException e) {
					logger.debug("Got exception in loop [" + e.getMessage()
							+ "]");
				} catch (Exception e) {
					logger.debug("Got exception in loop [" + e.getMessage()
							+ "]");
				}
			}

			merchantPstmt.close();
			merchantRS.close();
			usersJSONArray.clear();
			json.clear();

			logger.debug("Users Set [" + usersSet + "]");

			for (String getUser : usersSet) {
				rights = "";
				merchantQry = "select count(*) from user_linked_action where upper(user_id)=?";

				merchantPstmt = connection.prepareStatement(merchantQry);
				merchantPstmt.setString(1, getUser.toUpperCase());
				merchantRS = merchantPstmt.executeQuery();

				if (merchantRS.next()) {
					count = merchantRS.getInt(1);
				}

				merchantPstmt.close();
				merchantRS.close();

				if (count > 0) {
					merchantQry = "select NAME from user_linked_action where user_id=? order by id ";
				} else {
					merchantQry = "select NAME from user_linked_action where  GROUP_ID in (select user_groups from "
							+ "user_information where common_id in (select common_id from user_login_credentials where "
							+ "upper(login_user_id)=?)) and user_id is null order by id";
				}
				// merchantQry="select NAME from user_linked_action where user_id=? order by id ";

				try {

					merchantPstmt = connection.prepareStatement(merchantQry);
					merchantPstmt.setString(1, getUser.toUpperCase());
					merchantRS = merchantPstmt.executeQuery();

					while (merchantRS.next()) {
						rights += merchantRS.getString(1) + ",";
					}

					merchantPstmt.close();
					merchantRS.close();

					try {
						json.put("name",rights.substring(0, rights.lastIndexOf(",")));
						json.put("user_id", getUser);
						usersJSONArray.add(json);
						rights = "";
						json.clear();
					} catch (Exception e) {
						logger.debug("Exception in getting rights for user"
								+ " [" + getUser + "] ");
					}

					if (usersJSONArray != null && usersJSONArray.size() > 0) {
						resultJson.put(getUser + "_RIGHTS", usersJSONArray);
						usersJSONArray.clear();
					}

				} catch (SQLException e) {
					logger.debug("Got exception in loop  [" + e.getMessage()
							+ "]");
				}

			}

			merchantMap.put("USER_ACCESS_MNG", resultJson);

			logger.debug("Before getting Links [" + merchantMap + "]");

			logger.debug("Entity Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetAllUserGrps [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("SQLException in GetAllUserGrps [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
		}

		return responseDTO;
	}

	public ResponseDTO getUserGroupDetails(RequestDTO requestDTO) {
		logger.debug("Inside GetUserGroupDetails... ");

		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		Connection connection = null;

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray userRightsJSONArray = null;
		JSONObject json = null;

		String merchantQry = null;

		try {

			merchantQry = "select ID ,PID ,NAME ,CHECKED ,OPEN ,TITLE from user_Action_links "
					+ "order by to_number(id) ";

			responseDTO = new ResponseDTO();

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			userRightsJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("Connection [" + connection + "]");

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();

			while (merchantRS.next()) {
				json = new JSONObject();
				json.put("id", merchantRS.getString(1));
				json.put("pId", merchantRS.getString(2));
				json.put("name", merchantRS.getString(3));
				json.put("checked", merchantRS.getString(4));
				json.put("open", merchantRS.getString(5));
				json.put("title", merchantRS.getString(6));
				userRightsJSONArray.add(json);
			}

			resultJson.put("user_rights", userRightsJSONArray);
			merchantMap.put("user_rights", resultJson);

			logger.debug("MerchantMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserGroupDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {

			logger.debug("Exception in GetUserGroupDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			json = null;
			merchantMap = null;
			resultJson = null;
			userRightsJSONArray = null;
			json = null;
			merchantQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO viewUserGroup(RequestDTO requestDTO) {

		logger.debug("Inside  ViewUserGroup... ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray jsonArray = null;
		JSONObject rightsJson = null;
		JSONObject rightsJson1 = null;

		String merchantQry = "";
		String type = "";
		String entity = "";
		String groupId = "";

		CallableStatement callableStmt = null;
		ResultSet merchantRS = null;
		Connection connection = null;

		try {
			merchantQry = "{call getUserGroupDetails(?,?,?,?)}";
			responseDTO = new ResponseDTO();
			logger.debug("Request JSON [" + requestDTO.getRequestJSON() + "]");

			type = requestDTO.getRequestJSON().getString("TYPE");
			entity = requestDTO.getRequestJSON().getString("ENTITY");
			groupId = requestDTO.getRequestJSON().getString("GROUP_ID");
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			callableStmt = connection.prepareCall(merchantQry);
			callableStmt.setString(1, groupId);
			callableStmt.setString(2, entity);
			callableStmt.registerOutParameter(3, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(4, OracleTypes.VARCHAR);

			callableStmt.execute();
			logger.debug("Block executed successfully " + "with error_message["
					+ callableStmt.getString(4) + "]");

			merchantRS = (ResultSet) callableStmt.getObject(3);

			if (merchantRS.next()) {
				resultJson.put("group_id", merchantRS.getString(1));
				resultJson.put("group_name", merchantRS.getString(2));
				resultJson.put("maker_id", merchantRS.getString(3));
				resultJson.put("maker_dttm", merchantRS.getString(4));
				resultJson.put("entity", merchantRS.getString(5));
			}

			callableStmt.close();
			merchantRS.close();

			logger.debug(" Before Getting rights.");

			merchantQry = "{call GetRightsPkg.pGetRights(?,?,?,?,?)}";
			callableStmt = connection.prepareCall(merchantQry);
			callableStmt.setString(1, groupId);
			callableStmt.setString(2, type);
			callableStmt.setString(3, "GROUP");
			callableStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStmt.registerOutParameter(5, OracleTypes.CURSOR);

			jsonArray = new JSONArray();
			rightsJson = new JSONObject();
			rightsJson1 = new JSONObject();

			callableStmt.execute();
			logger.debug("Rights block executed successfully "
					+ "with error_message[" + callableStmt.getString(4) + "]");

			merchantRS = (ResultSet) callableStmt.getObject(5);

			while (merchantRS.next()) {
				rightsJson.put("id", merchantRS.getString(1));
				rightsJson.put("pId", merchantRS.getString(2));
				rightsJson.put("name", merchantRS.getString(3));
				rightsJson.put("checked", merchantRS.getString(4));
				rightsJson.put("open", merchantRS.getString(5));
				rightsJson.put("title", merchantRS.getString(6));
				rightsJson.put("chkDisabled", merchantRS.getString(7));
				jsonArray.add(rightsJson);
				rightsJson.clear();
			}

			rightsJson1.put("user_details", jsonArray);
			resultJson.put("json_val", rightsJson1);

			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("inside Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ViewUserGroup [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in ViewUserGroup  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			resultJson = null;
			jsonArray = null;
			rightsJson = null;
			rightsJson1 = null;

			merchantQry = null;
			type = null;
			entity = null;
			groupId = null;

		}

		return responseDTO;
	}

	// Getting User Details
	public ResponseDTO getUserDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetUserDetails.. ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String name[] = null;
		try {
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			merchantQry = "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD-MM-YYYY'),"
					+ "user_level,(select distinct cbl.OFFICE_CODE||'-'||cbl.OFFICE_NAME from BRANCH_MASTER cbl where cbl.office_code=location),"
					+ "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax from user_information "
					+ "where upper(user_groups)=? and upper(common_id)=(select common_id from user_login_credentials where upper(login_user_id)=?)";

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1,
					requestDTO.getRequestJSON().getString("GROUP_ID")
							.toUpperCase());
			merchantPstmt.setString(2,
					requestDTO.getRequestJSON().getString("USER_ID")
							.toUpperCase());
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				name = merchantRS.getString(1).split("\\ ");
				resultJson.put("first_name", name[0]);
				resultJson.put("last_name", name[1]);
				resultJson.put("emp_id", merchantRS.getString(2));
				resultJson.put("expiry_date", merchantRS.getString(3));
				resultJson.put("user_level", merchantRS.getString(4));
				resultJson.put("location", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("res_no", merchantRS.getString(7));
				resultJson.put("off_no", merchantRS.getString(8));
				resultJson.put("mobile_no", merchantRS.getString(9));
				resultJson.put("fax", merchantRS.getString(10));
				resultJson.put("GROUP_ID", requestDTO.getRequestJSON()
						.getString("GROUP_ID"));
			}

			merchantPstmt.close();
			merchantRS.close();

			merchantQry = "select json_val from user_groups where upper(group_id)=?";
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1,
					requestDTO.getRequestJSON().getString("GROUP_ID")
							.toUpperCase());
			merchantRS = merchantPstmt.executeQuery();
			if (merchantRS.next()) {
				resultJson.put("json_val", merchantRS.getString(1));
			}
			merchantMap.put("user_rights", resultJson);
			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			merchantQry = null;
			resultJson = null;
			name = null;
		}

		return responseDTO;
	}

	public ResponseDTO userInformation(RequestDTO requestDTO) {
		logger.debug("Inside UserInformation... ");

		Connection connection = null;
		CallableStatement callableStmt = null;
		ResultSet merchantRS = null;

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONObject resultJson1 = null;

		String userId = "";
		String groupId = "";
		String type = "";
		String query = "";
		String name[] = null;
		String merchantId = null;
		String userType = null;
		JSONArray storeJSONArray = null;
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			userId = requestJSON.getString("USER_ID");
			groupId = requestJSON.getString("GROUP_ID");
			type = requestJSON.getString("TYPE"); // ActiveDeactive

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			logger.debug("GroupId[" + groupId + "] userId[" + userId + "]");

			query = "{call GetRightsPkg.pGetUserDetails(?,?,?,?)}";

			callableStmt = connection.prepareCall(query);

			callableStmt.setString(1, groupId);
			callableStmt.setString(2, userId);
			callableStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callableStmt.registerOutParameter(4, OracleTypes.CURSOR);

			callableStmt.execute();

			logger.debug("Errormessage [" + callableStmt.getString(3) + "]");

			merchantRS = (ResultSet) callableStmt.getObject(4);
			String sname=null;
			
			if (merchantRS.next()) {
				
				sname = merchantRS.getString(1)+" -";
				name=sname.split("\\ ");
				//sname=name[1].concat(" ");
				resultJson.put("CV0001", userId);
				resultJson.put("CV0003", name[0]);
				resultJson.put("CV0004", name[1]);
				resultJson.put("CV0002", merchantRS.getString(2));
				resultJson.put("CV0011", merchantRS.getString(3));
				resultJson.put("CV0009", merchantRS.getString(4));
				resultJson.put("CV0010", merchantRS.getString(5));
				resultJson.put("CV0012", merchantRS.getString(6));
				resultJson.put("CV0005", merchantRS.getString(7));
				resultJson.put("CV0006", merchantRS.getString(8));
				resultJson.put("CV0007", merchantRS.getString(9));
				resultJson.put("CV0008", merchantRS.getString(10));
				resultJson.put("CV0013", merchantRS.getString(11));
				resultJson.put("CV0014", groupId);
				resultJson.put("CV0015", merchantRS.getString(12));
				resultJson.put("CV0016", merchantRS.getString(13));
				resultJson.put("CV0017", merchantRS.getString(14));
				resultJson.put("CV0018", merchantRS.getString(15));
				resultJson.put("CV0019", merchantRS.getString(16));

				merchantId = merchantRS.getString(12);
				userType = merchantRS.getString(14);
			}

			logger.debug("Inside  before if type [" + type + "]");

			if (type.equalsIgnoreCase("Modify")) {

				resultJson1 = (JSONObject) getAdminCraeteInfo(requestDTO).getData().get(CevaCommonConstants.LOCATION_INFO);
				logger.debug("ResultJson1 [" + resultJson1 + "]");
				
				resultJson.put("CLUSTER_LIST",resultJson1.get("CLUSTER_LIST"));
				resultJson.put("USER_DESIGNATION",resultJson1.get("USER_DESIGNATION"));
				resultJson.put("GROUP_TYPE",resultJson1.get("GROUP_TYPE"));
				resultJson.put("USER_GROUPS",resultJson1.get("USER_GROUPS"));

				if(userType.equals("MU") || userType.equals("MS")){
					storeJSONArray = new JSONArray();
					String storeQry = "SELECT STORE_ID,STORE_NAME from STORE_MASTER  where MERCHANT_ID=?";
					PreparedStatement storePstmt = connection.prepareStatement(storeQry);
					storePstmt.setString(1,merchantId);
					ResultSet storeRS = storePstmt.executeQuery();
					while (storeRS.next()) {
						JSONObject json = new JSONObject();
						json.put(CevaCommonConstants.SELECT_KEY, storeRS.getString(2));
						json.put(CevaCommonConstants.SELECT_VAL, storeRS.getString(1));
						storeJSONArray.add(json);
					}
					logger.debug("storeJSONArray [" + storeJSONArray + "]");
					resultJson.put(CevaCommonConstants.STORE_LIST, storeJSONArray);
				}



			}

			logger.debug("ResultJson [" + resultJson + "]");

			merchantMap.put("user_rights", resultJson);

			logger.debug("EentityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in UserInformation [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UserInformation  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			resultJson = null;
			resultJson1 = null;

			userId = null;
			groupId = null;
			type = null;
			query = null;
			name = null;
		}

		return responseDTO;
	}

	public ResponseDTO userProfile(RequestDTO requestDTO) {

		logger.debug("Inside UserProfile... ");

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		JSONObject resultJson = null;
		String userId = null;

		String merchantQry = "";
		String name[] = null;
		HashMap<String, Object> merchantMap = null;
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			userId = requestJSON.getString("makerId");
			logger.debug("USERID>>>>>>"+userId);
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + (connection == null) + "]");

			merchantQry = "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD/MM/YYYY'),"
					+ "user_level,(select distinct cbl.OFFICE_NAME from BRANCH_MASTER cbl where cbl.office_code=location),"
					+ "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,"
					+ "decode(USER_STATUS,'A','Active','L','De-Active','F','InActive',USER_STATUS),"
					+ "user_groups, (select ug.group_name from user_groups ug where ug.group_id=user_groups)"
					+ "from user_information where common_id = (select common_id from user_login_credentials where login_user_id=?)";

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1, requestJSON.getString("makerId"));
			merchantRS = merchantPstmt.executeQuery();
			if (merchantRS.next()) {
				name = merchantRS.getString(1).split("\\ ");
				resultJson.put("CV0001", userId);
				resultJson.put("CV0003", name[0]);
				resultJson.put("CV0004", name[1]);
				resultJson.put("CV0002", merchantRS.getString(2));
				resultJson.put("CV0011", merchantRS.getString(3));
				resultJson.put("CV0009", merchantRS.getString(4));
				resultJson.put("CV0010", merchantRS.getString(5));
				resultJson.put("CV0012", merchantRS.getString(6));
				resultJson.put("CV0005", merchantRS.getString(7));
				resultJson.put("CV0006", merchantRS.getString(8));
				resultJson.put("CV0007", merchantRS.getString(9));
				resultJson.put("CV0008", merchantRS.getString(10));
				resultJson.put("CV0013", merchantRS.getString(11));
				resultJson.put("CV0014", merchantRS.getString(12));
				resultJson.put("CV0015", merchantRS.getString(13));
			}

			merchantMap.put("user_rights", resultJson);

			logger.debug("EntityMap [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in UserProfile [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UserProfile  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			resultJson = null;
			userId = null;

			merchantQry = null;
			name = null;
			merchantMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO userDashInformation(RequestDTO requestDTO) {

		logger.debug("Inside UserDashInformation.. ");
		HashMap<String, Object> merchantMap = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		JSONObject resultJson = null;
		JSONObject resultJson1 = null;

		JSONObject rightsJson = null;
		JSONObject rightsJson1 = null;

		Connection connection = null;
		CallableStatement callable = null;

		String userId = "";
		String groupId = "";
		String merchantQry = "";
		String type = "";
		String makerId = "";
		String errorMessage = "";
		String groupDetails = "";
		String name[] = null;
		String data[] = null;
		JSONArray jsonArray = null;

		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			connection = DBConnector.getConnection();

			userId = requestJSON.getString("USER_ID")
					.toUpperCase();
			groupId = requestJSON.getString("GROUP_ID")
					.toUpperCase();

			type = requestJSON.getString("TYPE"); // ActiveDeactive
			makerId = requestJSON.getString("MAKER_ID"); // ActiveDeactive

			logger.debug("Type is [" + type + "]");

			if (type.equalsIgnoreCase("view")) {
				logger.debug("Connection is null [" + (connection == null)
						+ "]");
				merchantQry = "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD/MM/YYYY'),"
						+ "user_level,location,"
						+ "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,decode(USER_STATUS,'A','Active','L','De-Active','F','Active',USER_STATUS),MERCHANT_ID,STORE_ID "
						+ "from user_information where upper(user_groups)=? and upper(common_id)=(select common_id from user_login_credentials where upper(login_user_id)=?)";

				merchantPstmt = connection.prepareStatement(merchantQry);
				merchantPstmt.setString(1, groupId);
				merchantPstmt.setString(2, userId);
				merchantRS = merchantPstmt.executeQuery();

				if (merchantRS.next()) {
					//name = merchantRS.getString(1).split("\\ ");
					resultJson.put("CV0001", userId);
					if((merchantRS.getString(1)).indexOf(" ")==-1){
						resultJson.put("CV0003", merchantRS.getString(1));
						resultJson.put("CV0004", "");
					}else{
						name = merchantRS.getString(1).split("\\ ");
						resultJson.put("CV0003", name[0]);
						resultJson.put("CV0004", name[1]);
					}
					resultJson.put("CV0002", merchantRS.getString(2));
					resultJson.put("CV0011", merchantRS.getString(3));
					resultJson.put("CV0009", merchantRS.getString(4));
					resultJson.put("CV0010", merchantRS.getString(5));
					resultJson.put("CV0012", merchantRS.getString(6));
					resultJson.put("CV0005", merchantRS.getString(7));
					resultJson.put("CV0006", merchantRS.getString(8));
					resultJson.put("CV0007", merchantRS.getString(9));
					resultJson.put("CV0008", merchantRS.getString(10));
					resultJson.put("CV0013", merchantRS.getString(11));
					resultJson.put("CV0014", groupId);
					resultJson.put("CV0015", merchantRS.getString(12));
					resultJson.put("CV0016", merchantRS.getString(13));
				}

				logger.debug(" before if type[" + type + "]");

				if (type.equalsIgnoreCase("Modify")) {
					resultJson1 = (JSONObject) getAdminCraeteInfo(requestDTO)
							.getData().get(CevaCommonConstants.LOCATION_INFO);
					resultJson.put(CevaCommonConstants.LOCATION_LIST,
							resultJson1.get(CevaCommonConstants.LOCATION_LIST));
				}

				logger.debug("ResultJson [" + resultJson + "]");

				merchantMap.put("DETAILS", resultJson);

				merchantQry = "{call GetRightsPkg.pGetRights(?,?,?,?,?)}";
				callable = connection.prepareCall(merchantQry);
				callable.setString(1,
						requestJSON.getString("USER_ID"));
				callable.setString(2, type);
				callable.setString(3, "USER");
				callable.registerOutParameter(4, OracleTypes.VARCHAR);
				callable.registerOutParameter(5, OracleTypes.CURSOR);

				jsonArray = new JSONArray();
				rightsJson = new JSONObject();
				rightsJson1 = new JSONObject();

				callable.execute();
				logger.debug("Rights block executed successfully  with error_message["
						+ callable.getString(4) + "]");

				merchantRS = (ResultSet) callable.getObject(5);

				while (merchantRS.next()) {
					rightsJson.put("id", merchantRS.getString(1));
					rightsJson.put("pId", merchantRS.getString(2));
					rightsJson.put("name", merchantRS.getString(3));
					rightsJson.put("checked", merchantRS.getString(4));
					rightsJson.put("open", merchantRS.getString(5));
					rightsJson.put("title", merchantRS.getString(6));
					rightsJson.put("chkDisabled", merchantRS.getString(7));
					jsonArray.add(rightsJson);
				}

				logger.debug("Before Getting rights.");

				rightsJson1.put("user_details", jsonArray);
				resultJson.put("json_val", rightsJson1);
				merchantMap.put("user_rights", resultJson);

				logger.debug("userDashInformation 123." + resultJson);

			} else {

				jsonArray = new JSONArray();
				resultJson = new JSONObject();

				callable = connection
						.prepareCall("{call UserDashboardDetailsPkg.pGetUserDetails(?,?,?,?,?)}");
				callable.setString(1, groupId);
				callable.setString(2, makerId);
				callable.registerOutParameter(3, OracleTypes.VARCHAR);
				callable.registerOutParameter(4, OracleTypes.VARCHAR);
				callable.registerOutParameter(5, OracleTypes.CURSOR);

				callable.execute();
				try {
					logger.debug("Block executed successfully.. ");

					groupDetails = callable.getString(3);
					errorMessage = callable.getString(4);

					logger.debug("GroupDetails[" + groupDetails
							+ "] error_message[" + errorMessage + "]");

					merchantRS = (ResultSet) callable.getObject(5);
					resultJson1 = new JSONObject();
					while (merchantRS.next()) {
						resultJson1.put("username", merchantRS.getString(1));
						resultJson1.put("expirydate", merchantRS.getString(3));
						resultJson1.put("empid", merchantRS.getString(2));
						resultJson1.put("userlevel", merchantRS.getString(4));
						resultJson1
								.put("userlocation", merchantRS.getString(5));
						resultJson1.put("email", merchantRS.getString(6));
						resultJson1.put("local_res_num",
								merchantRS.getString(7));
						resultJson1.put("local_off_num",
								merchantRS.getString(8));
						resultJson1.put("mobile_no", merchantRS.getString(9));
						resultJson1.put("fax", merchantRS.getString(10));
						resultJson1
								.put("user_status", merchantRS.getString(11));
						resultJson1.put("user_id", merchantRS.getString(12));
						jsonArray.add(resultJson1);
					}

					logger.debug(" Before spliting Data.");
					data = groupDetails.split("\\~");

					resultJson.put("USER_DETAILS", jsonArray);
					resultJson.put("GROUP_ID", data[0]);
					resultJson.put("GROUP_NAME", data[1]);
					resultJson.put("MAKER_ID", data[2]);
					resultJson.put("MAKER_DTTM", data[3]);

					merchantMap.put("DETAILS", resultJson);

				} catch (Exception e) {
					logger.debug("Exception while in the loop ["
							+ e.getMessage() + "]");
				}
			}

			logger.debug("Entity Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);

		} catch (SQLException e) {
			logger.debug("SQLException in userDashInformation ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in userDashInformation  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeCallableStatement(callable);
			merchantMap = null;

			resultJson = null;
			resultJson1 = null;

			userId = null;
			groupId = null;
			merchantQry = null;
			type = null;
			makerId = null;
			errorMessage = null;
			groupDetails = null;
			name = null;
			data = null;
			jsonArray = null;
		}

		return responseDTO;
	}

	// Get Access Rights Of User
	public ResponseDTO modifyAccessRights(RequestDTO requestDTO) {

		logger.debug("Inside ModifyAccessRights... ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;

		String merchantQry = null;

		JSONArray jsonArray = null;
		JSONObject rightsJson = null;
		JSONObject rightsJson1 = null;

		PreparedStatement merchantPstmt = null;
		CallableStatement callableStmt = null;
		ResultSet merchantRS = null;
		String type = "";
		Connection connection = null;
		String name[] = null;
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			type = requestJSON.getString("TYPE");
			connection = DBConnector.getConnection();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantQry = "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD-MON-YYYY'),"
					+ "user_level,location,"
					+ "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,user_groups,decode(USER_STATUS,'A','Active','L','De-Active','F','Active',USER_STATUS),MERCHANT_ID,STORE_ID from user_information "
					+ "where  upper(common_id)=(select upper(common_id) from user_login_credentials where upper(login_user_id)=?)";

			logger.debug("MerchantQry [" + merchantQry + "]");

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1,
					requestJSON.getString("USER_ID"));
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {
				name = merchantRS.getString(1).split("\\ ");
				resultJson.put("first_name", name[0]);
				resultJson.put("last_name", name[1]);
				resultJson.put("emp_id", merchantRS.getString(2));
				resultJson.put("expiry_date", merchantRS.getString(3));
				resultJson.put("user_level", merchantRS.getString(4));
				resultJson.put("location", merchantRS.getString(5));
				resultJson.put("email", merchantRS.getString(6));
				resultJson.put("res_no", merchantRS.getString(7));
				resultJson.put("off_no", merchantRS.getString(8));
				resultJson.put("mobile_no", merchantRS.getString(9));
				resultJson.put("fax", merchantRS.getString(10));
				resultJson.put("GROUP_ID", merchantRS.getString(11));
				resultJson.put("USER_STATUS", merchantRS.getString(12));
				resultJson.put("MERCHANT_ID", merchantRS.getString(13));
				resultJson.put("STORE_ID", merchantRS.getString(14));
			}

			merchantRS.close();
			logger.debug("Before Getting ResultJson Values:" + resultJson);
			logger.debug("Before Getting rights.");

			merchantQry = "{call GetRightsPkg.pGetRights(?,?,?,?,?)}";
			callableStmt = connection.prepareCall(merchantQry);
			callableStmt.setString(1,
					requestJSON.getString("USER_ID"));
			callableStmt.setString(2, type);
			callableStmt.setString(3, "USER");
			callableStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callableStmt.registerOutParameter(5, OracleTypes.CURSOR);

			jsonArray = new JSONArray();
			rightsJson = new JSONObject();
			rightsJson1 = new JSONObject();

			callableStmt.execute();
			logger.debug("Rights block executed successfully  with error_message["
					+ callableStmt.getString(4) + "]");

			merchantRS = (ResultSet) callableStmt.getObject(5);

			while (merchantRS.next()) {
				rightsJson.put("id", merchantRS.getString(1));
				rightsJson.put("pId", merchantRS.getString(2));
				rightsJson.put("name", merchantRS.getString(3));
				rightsJson.put("checked", merchantRS.getString(4));
				rightsJson.put("open", merchantRS.getString(5));
				rightsJson.put("title", merchantRS.getString(6));
				rightsJson.put("chkDisabled", merchantRS.getString(7));
				jsonArray.add(rightsJson);
			}

			logger.debug("Before Getting rights.");

			rightsJson1.put("user_details", jsonArray);
			resultJson.put("json_val", rightsJson1);
			merchantMap.put("user_rights", resultJson);

			logger.debug("Entity Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ModifyAccessRights ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in ModifyAccessRights  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closeCallableStatement(callableStmt);

			merchantMap = null;
			resultJson = null;
			name = null;
			merchantQry = null;
			jsonArray = null;
			rightsJson = null;
			rightsJson1 = null;
			type = null;
		}

		return responseDTO;
	}

	public ResponseDTO saveUserAssignDetails(RequestDTO requestDTO) {

		logger.debug("Inside SaveUserAssignDetails... ");
		String keyVal = null;
		String groupId = null;
		String jsonVal = null;
		String userID = null;

		org.json.JSONArray jArray = null;
		org.json.JSONObject jobj = null;
		String merchantQry = null;

		Connection connection = null;
		PreparedStatement prepareStmt = null;
		PreparedStatement prepareStmt1 = null;
		ResultSet getId = null;

		org.json.JSONObject json_data = null;
		String id = "";
		String pid = "";
		String title = "";

		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			keyVal = requestJSON.getString("keyVal");
			groupId = requestJSON.getString("GROUP_ID");
			jsonVal = requestJSON.getString("jsonVal");
			userID = requestJSON.getString("user_id");

			try {
				jobj = new org.json.JSONObject(jsonVal);
				logger.debug("Json object is  [" + jobj.toString() + "]");
				jArray = new org.json.JSONArray(jobj.getString(keyVal));
			} catch (JSONException e) {
				logger.debug("Exception in parsing Json String.."
						+ e.getMessage());
			}

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantQry = "delete from user_linked_Action where user_id=?";
			connection.setAutoCommit(false);
			prepareStmt = connection.prepareStatement(merchantQry);
			prepareStmt.setString(1, userID);

			int deleteSize = prepareStmt.executeUpdate();
			connection.commit();

			logger.debug("Rows deleted are [" + deleteSize + "]");

			prepareStmt.close();
			merchantQry = "insert into user_linked_Action (ID ,PID ,NAME ,CHECKED ,OPEN ,TITLE ,GROUP_ID,USER_ID) "
					+ "values (?,?,?,?,?,?,?,?)";
			connection.setAutoCommit(false);
			prepareStmt = connection.prepareStatement(merchantQry);

			try {

				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);

					if (json_data.getString("checked").equalsIgnoreCase("true")) {

						prepareStmt1 = connection
								.prepareStatement("select id,pid,title from USER_ACTION_LINKS "
										+ "where upper(name)=upper(?)");
						prepareStmt1.setString(1, json_data.getString("name"));
						getId = prepareStmt1.executeQuery();

						if (getId.next()) {
							id = getId.getString(1);
							pid = getId.getString(2);
							title = getId.getString(3);
						}

						logger.debug(" Id [" + id + "] Pid [" + pid + "]");

						prepareStmt.setString(1, id);
						prepareStmt.setString(2, pid);
						prepareStmt.setString(3, json_data.getString("name"));
						
						prepareStmt				.setString(4, json_data.getString("checked"));
						prepareStmt.setString(5, json_data.getString("open"));
						prepareStmt.setString(6, title);
						prepareStmt.setString(7, groupId);
						prepareStmt.setString(8, userID);
						prepareStmt.addBatch();
					}
				}

				logger.debug("After preparing batch insertion for json_data ["
						+ json_data + "].");

			} catch (JSONException e) {
				logger.debug("Got exception while fetching the values from USER_ACTION_LINKS ["
						+ e.getMessage() + "]");
			}
			int size[] = prepareStmt.executeBatch();
			connection.commit();

			logger.debug("Rows inserted into user_linked_Action are ["
					+ size.length + "]");

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in SaveUserAssignDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in SaveUserAssignDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(prepareStmt);
			DBUtils.closePreparedStatement(prepareStmt1);
			DBUtils.closeResultSet(getId);

			keyVal = null;
			groupId = null;
			jsonVal = null;
			userID = null;

			jArray = null;
			jobj = null;
			merchantQry = null;

			json_data = null;
			id = null;
			pid = null;
			title = null;
		}

		return responseDTO;
	}

	// Modify User Group Rights
	public ResponseDTO modifyGroupDetails(RequestDTO requestDTO) {

		logger.debug("Inside ModifyGroupDetails.. ");

		String keyVal = null;
		String groupId = null;
		String jsonVal = null;
		String userID = null;
		String groupDesc = null;
		String entity = null;
		String applCode = null;
		String merchantQry = null;
		String groupType = null;
		String userlevel = null;

		org.json.JSONArray jArray = null;
		org.json.JSONObject jobj = null;

		PreparedStatement prepareStmt = null;
		PreparedStatement prepareStmt1 = null;
		ResultSet getId = null;

		Connection connection = null;
		String ip = null;
		String makerid = null;
		String auditQry = "";
		CallableStatement cstmt = null;

		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			groupId = requestJSON.getString("GROUP_ID");
			jsonVal = requestJSON.getString("jsonVal");
			keyVal = requestJSON.getString("keyVal");
			userID = requestJSON.getString("user_id");
			groupDesc = requestJSON.getString("GROUP_DESC");
			entity = requestJSON.getString("entity_id");
			applCode = requestJSON.getString("applCode");

			try {

				jobj = new org.json.JSONObject(jsonVal);
				logger.debug("Json object is [" + jobj.toString() + "]");
				jArray = new org.json.JSONArray(jobj.getString(keyVal));
			} catch (JSONException e) {
				logger.debug("Exception in parsing Json String ["
						+ e.getMessage() + "]");
			}

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			
			connection.setAutoCommit(false);
			/*PreparedStatement merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt = connection.prepareStatement(merchantQry);*/
			/*merchantQry = "insert into AUDIT_TRAIL(TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID,DATA_1,TRANS_CODE_DESC,MAKER_ID,DATA_2)"
					+ " values (?,?,?,?,?,?,?,?)";
			
			
			
			merchantPstmt.setString(1, "modifyGroupDetailsAck");
			merchantPstmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			merchantPstmt.setString(3, requestJSON.getString("user_id"));
			merchantPstmt.setString(4, "WEB");
			merchantPstmt.setString(5, requestJSON.getString(CevaCommonConstants.IP));
			merchantPstmt.setString(6, groupId +" : "+"Group Details Modified Successfully");
			merchantPstmt.setString(7, requestJSON.getString("user_id"));
			// pstmt.setString(8, UserLocking.getSystemMac());
			merchantPstmt.setString(8, " ");
			int count1 = merchantPstmt.executeUpdate();
			
			logger.debug("Getting the count after insertion to "
					+ "USER_GROUPS is [" + count1 + "] .");
			
			connection.commit();
			logger.debug("Audit insertion Success");
			merchantPstmt = null;*/
			
			
			

			auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
			cstmt = connection.prepareCall(auditQry);
			cstmt.setString(1, "modify-group");
			cstmt.setString(2, makerid);
			cstmt.setString(3, "WEB");
			cstmt.setString(4, " Action Selected By group id "+groupId);
			cstmt.setString(5, ip);
			cstmt.setString(6, " ");
			cstmt.setString(7, " ");
			cstmt.setString(8, " ");
			
			int ii = cstmt.executeUpdate();
			if (ii == 1) {
				logger.debug("Action Identification Interceptor -> Successfully Inserted "+ ip);
			} else {
				logger.debug("Action Identification Interceptor -> insertion failed due to some error "+ ip);
			}
			

			/******************************************************************************/

			merchantQry = "insert into USER_GROUPS_HISTORY (GROUP_ID ,GROUP_NAME ,APPL_CODE ,MAKER_ID ,MAKER_DTTM ,ENTITY ,JSON_VAL,GROUP_TYPE,USER_LEVEL) "
					+ "values (?,?,?,?,sysdate,?,?,?,?)";
			//PreparedStatement merchantPstmt = connection.prepareStatement(merchantQry);
			PreparedStatement merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, groupId);
			merchantPstmt.setString(2, groupDesc);
			merchantPstmt.setString(3, applCode);
			merchantPstmt.setString(4, userID);
			merchantPstmt.setString(5, entity);
			merchantPstmt.setString(6, jsonVal.replaceAll(
					"\"chkDisabled\":\"true\"", "\"chkDisabled\":\"false\""));
			merchantPstmt.setString(7, groupType);
			merchantPstmt.setString(8, userlevel);

			logger.debug("After assigning the values to USER_GROUPS_HISTORY. ");

			int count = merchantPstmt.executeUpdate();

			logger.debug("Getting the count after insertion to "
					+ "USER_GROUPS is [" + count + "] .");
			connection.commit();

			logger.debug("After second insertion to USER_GROUPS .");
			merchantPstmt = null;

			/******************************************************************************/

			merchantQry = "update user_groups set json_Val=? where group_id=?";
			prepareStmt = connection.prepareStatement(merchantQry);

			/*
			 * prepareStmt.setString(1, jsonVal.replaceAll(
			 * "\"chkDisabled\":\"true\"", "\"chkDisabled\":\"false\""));
			 */
			prepareStmt.setString(1, jsonVal);
			prepareStmt.setString(2, groupId.toUpperCase());

			int sizeJsonIns = prepareStmt.executeUpdate();
			connection.commit();

			logger.debug("Updated row are  [" + sizeJsonIns + "]");

			prepareStmt.close();

			merchantQry = "delete from user_linked_Action where group_id=? and user_id is null";
			connection.setAutoCommit(false);
			prepareStmt = connection.prepareStatement(merchantQry);
			prepareStmt.setString(1, groupId);

			int deleteSize = prepareStmt.executeUpdate();
			connection.commit();

			logger.debug("Rows deleted from user_linked_Action [" + deleteSize
					+ "]");

			merchantQry = "insert into user_linked_Action (ID ,PID ,NAME ,CHECKED ,OPEN ,TITLE ,GROUP_ID ) "
					+ "values (?,?,?,?,?,?,? )";
			connection.setAutoCommit(false);
			prepareStmt = connection.prepareStatement(merchantQry);

			org.json.JSONObject json_data = null;
			String id = "";
			String pid = "";
			String title = "";
			try {

				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);

					if (json_data.getString("checked").equalsIgnoreCase("true")) {

						prepareStmt1 = connection
								.prepareStatement("select id,pid,title from USER_ACTION_LINKS where upper(name)=upper(?)");
						prepareStmt1.setString(1, json_data.getString("name"));
						getId = prepareStmt1.executeQuery();

						if (getId.next()) {
							id = getId.getString(1);
							pid = getId.getString(2);
							title = getId.getString(3);
						}

						prepareStmt.setString(1, id);
						prepareStmt.setString(2, pid);
						prepareStmt.setString(3, json_data.getString("name"));
						prepareStmt
								.setString(4, json_data.getString("checked"));
						prepareStmt.setString(5, json_data.getString("open"));
						prepareStmt.setString(6, title);
						prepareStmt.setString(7, groupId);
						prepareStmt.addBatch();
					}
				}

			} catch (JSONException e) {
				logger.debug(" exception is [" + e.getMessage() + "]");
			}

			json_data = null;
			id = null;
			pid = null;
			title = null;

			int size[] = prepareStmt.executeBatch();
			connection.commit();

			logger.debug("Rows inserted in user_linked_Action [" + size.length
					+ "]");

			logger.debug(" Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in ModifyGroupDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in ModifyGroupDetails  [" + e.getMessage()
					+ "]");
			e.printStackTrace();
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(prepareStmt);
			DBUtils.closePreparedStatement(prepareStmt1);
			DBUtils.closeResultSet(getId);
			DBUtils.closeCallableStatement(cstmt);

			keyVal = null;
			groupId = null;
			jsonVal = null;
			merchantQry = null;
			jArray = null;
			jobj = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertUserGroupDetails(RequestDTO requestDTO) {

		logger.debug("Inside InsertUserGroupDetails.... ");

		// JSONObject object = null;
		String groupId = null;
		String groupDesc = null;
		String jsonVal = null;
		String userID = null;
		String entity = null;
		String applCode = null;
		String groupType = null;
		String ip = null;
		String makerid = null;
		String auditQry = "";
		CallableStatement cstmt = null;

		String userlevel = null;

		org.json.JSONArray jArray = null;
		org.json.JSONObject jobj = null;

		String id = "";
		String pid = "";
		String title = "";
		String merchantQry = "";

		PreparedStatement prepareStmt1 = null;
		ResultSet getId = null;
		Connection connection = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			jsonVal = requestJSON.getString("jsonVal");
			groupId = requestJSON.getString("GROUP_ID");
			groupDesc = requestJSON.getString("GROUP_DESC");
			userID = requestJSON.getString("user_id");
			entity = requestJSON.getString("entity_id");
			applCode = requestJSON.getString("applCode");

			groupType = requestJSON.getString("GROUP_TYPE");
			userlevel = requestJSON.getString("USER_LEVEL");
			ip=requestJSON.getString(CevaCommonConstants.IP);
			makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
			groupId=groupId.toUpperCase();
			try {

				jobj = new org.json.JSONObject(jsonVal);
				logger.debug(" jobj [" + jobj.toString() + "]");
				jArray = new org.json.JSONArray(jobj.getString("user_details"));
			} catch (JSONException e) {
				logger.debug("Exception in parsing Json String.."
						+ e.getMessage());

			}
			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			connection.setAutoCommit(false);
			
			auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
			cstmt = connection.prepareCall(auditQry);
			cstmt.setString(1, "saveGroupDetails");
			cstmt.setString(2, makerid);
			cstmt.setString(3, "WEB");
			cstmt.setString(4, " New Group Has been Saved Successfully to Database  ");
			cstmt.setString(5, ip);
			cstmt.setString(6, " ");
			cstmt.setString(7, " ");
			cstmt.setString(8, " ");

			int ii = cstmt.executeUpdate();
			if (ii == 1) {
				logger.debug("Action Identification Interceptor -> Successfully Inserted "+ ip);
			} else {
				logger.debug("Action Identification Interceptor -> insertion failed due to some error "+ ip);
			}

			merchantQry = "insert into AUDIT_TRAIL(TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID,DATA_1,TRANS_CODE_DESC,MAKER_ID,DATA_2)"
					+ " values (?,?,?,?,?,?,?,?)";
			PreparedStatement merchantPstmt = connection.prepareStatement(merchantQry);
			
			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt.setString(1, "saveGroupDetails");
			merchantPstmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			merchantPstmt.setString(3, requestJSON.getString("user_id"));
			merchantPstmt.setString(4, "WEB");
			merchantPstmt.setString(5, requestJSON.getString(CevaCommonConstants.IP));
			merchantPstmt.setString(6, groupId +" : "+"Group Details Created Successfully");
			merchantPstmt.setString(7, requestJSON.getString("user_id"));
			// pstmt.setString(8, UserLocking.getSystemMac());
			merchantPstmt.setString(8, " ");
			int count1 = merchantPstmt.executeUpdate();

			logger.debug("Getting the count after insertion to "
					+ "USER_GROUPS is [" + count1 + "] .");

			connection.commit();
			logger.debug("Audit insertion Success");
			merchantPstmt = null;

			merchantQry = "insert into USER_GROUPS (GROUP_ID ,GROUP_NAME ,APPL_CODE ,MAKER_ID ,MAKER_DTTM ,ENTITY ,JSON_VAL,GROUP_TYPE,USER_LEVEL) "
					+ "values (?,?,?,?,sysdate,?,?,?,?)";
			//PreparedStatement merchantPstmt = connection.prepareStatement(merchantQry);
			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, groupId);
			merchantPstmt.setString(2, groupDesc);
			merchantPstmt.setString(3, applCode);
			merchantPstmt.setString(4, userID);
			merchantPstmt.setString(5, entity);
			merchantPstmt.setString(6, jsonVal.replaceAll(
					"\"chkDisabled\":\"true\"", "\"chkDisabled\":\"false\""));
			merchantPstmt.setString(7, "BANKGRP");
			merchantPstmt.setString(8, userlevel);

			logger.debug("After assigning the values to USER_GROUPS. ");

			int count = merchantPstmt.executeUpdate();

			logger.debug("Getting the count after insertion to "
					+ "USER_GROUPS is [" + count + "] .");
			connection.commit();

			logger.debug("After second insertion to USER_GROUPS .");
			merchantPstmt = null;

			merchantQry = "insert into user_linked_Action (ID ,PID ,NAME ,CHECKED ,OPEN ,TITLE ,GROUP_ID) "
					+ "values (?,?,?,?,?,?,?)";
			connection.setAutoCommit(false);
			merchantPstmt = connection.prepareStatement(merchantQry);

			org.json.JSONObject json_data = null;
			try {

				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);

					if (json_data.getString("checked").equalsIgnoreCase("true")) {
						prepareStmt1 = connection
								.prepareStatement("select id,pid,title from USER_ACTION_LINKS where upper(name)=upper(?)");
						prepareStmt1.setString(1, json_data.getString("name"));
						getId = prepareStmt1.executeQuery();
						if (getId.next()) {
							id = getId.getString(1);
							pid = getId.getString(2);
							title = getId.getString(3);
						}

						logger.debug(" ID [" + id + "] PID [" + pid + "]");

						merchantPstmt.setString(1, id);
						merchantPstmt.setString(2, pid);
						merchantPstmt.setString(3, json_data.getString("name"));
						merchantPstmt.setString(4,
								json_data.getString("checked"));
						merchantPstmt.setString(5, json_data.getString("open"));
						merchantPstmt.setString(6, title);
						merchantPstmt.setString(7, groupId);
						merchantPstmt.addBatch();
					}
				}

			} catch (JSONException e) {
				logger.debug("inside  exception is [" + e.getMessage() + "].");
			}

			json_data = null;

			int size[] = merchantPstmt.executeBatch();
			connection.commit();

			logger.debug("Rows Inserted are [" + size.length + "]");

			responseDTO = getUserGroupDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in InsertUserGroupDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in InsertUserGroupDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(prepareStmt1);
			DBUtils.closeResultSet(getId);
			DBUtils.closeCallableStatement(cstmt);

			groupId = null;
			groupDesc = null;
			jsonVal = null;
			userID = null;
			entity = null;
			applCode = null;

			jArray = null;
			jobj = null;
			merchantQry = null;
			id = null;
			pid = null;
			title = null;
		}

		return responseDTO;
	}

	public ResponseDTO getAllUserDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetAllUserDetails.. ");

		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJSONArray = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String merchantQry = "";

		try {
			merchantQry = "SELECT ULC.LOGIN_USER_ID ,UI.USER_NAME,NVL(UI.USER_GROUPS,'NOT ASSIGNED'),"
					+ "CBM.OFFICE_CODE||'-'||CBM.OFFICE_NAME  "
					+ "FROM  USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC,BRANCH_MASTER CBM "
					+ "WHERE CBM.OFFICE_CODE=UI.LOCATION AND UI.COMMON_ID=ULC.COMMON_ID AND UI.USER_TYPE='BA'";
			merchantMap = new HashMap<String, Object>();
			responseDTO = new ResponseDTO();

			resultJson = new JSONObject();
			merchantJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.USER_ID, merchantRS.getString(1));
				json.put(CevaCommonConstants.USER_NAME, merchantRS.getString(2));
				json.put(CevaCommonConstants.ROLE_GRP_ID,
						merchantRS.getString(3));
				json.put(CevaCommonConstants.OFFICE_LOCATON,
						merchantRS.getString(4));
				merchantJSONArray.add(json);
			}

			resultJson
					.put(CevaCommonConstants.MERCHANT_LIST, merchantJSONArray);
			merchantMap.put(CevaCommonConstants.MERCHANT_LIST, resultJson);
			logger.debug("Merchant Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetAllUserDetails [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetAllUserDetails  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);

			merchantMap = null;
			resultJson = null;
			merchantJSONArray = null;
			merchantQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO getIctAdminData(RequestDTO requestDTO) {

		logger.debug("Inside GetIctAdminData.... ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray merchantJSONArray = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;

		String merchantQry = "";
		try {

			merchantQry = "Select A.LOGIN_USER_ID,B.USER_NAME,B.EMP_ID,B.EMAIL from USER_LOGIN_CREDENTIALS A,USER_INFORMATION B WHERE A.COMMON_ID=B.COMMON_ID ";
			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			merchantJSONArray = new JSONArray();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			merchantPstmt = connection.prepareStatement(merchantQry);
			merchantRS = merchantPstmt.executeQuery();
			JSONObject json = null;
			while (merchantRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.USER_ID, merchantRS.getString(1));
				json.put(CevaCommonConstants.USER_NAME, merchantRS.getString(2));
				json.put(CevaCommonConstants.EMAIL, merchantRS.getString(3));
				json.put(CevaCommonConstants.EMPLOYEE_NO,
						merchantRS.getString(4));
				merchantJSONArray.add(json);
			}

			resultJson
					.put(CevaCommonConstants.MERCHANT_LIST, merchantJSONArray);
			merchantMap.put(CevaCommonConstants.MERCHANT_LIST, resultJson);
			logger.debug("Merchant Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
		} catch (SQLException e) {
			logger.debug("SQLException in GetIctAdminData [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetIctAdminData  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeResultSet(merchantRS);
			merchantQry = null;
			merchantMap = null;
			resultJson = null;
			merchantJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO getCevaEntitys(RequestDTO requestDTO) {
		logger.debug("Inside GetCevaEntitys...  ");

		JSONArray entityJSONArray = null;
		HashMap<String, Object> appDataMap = null;
		String userType = null;
		String makerType = null;
		String makerId = "";
		String entityQry = "";
		String userTypeQry = "";

		Connection connection = null;
		PreparedStatement entityPstmt = null;
		PreparedStatement userTypePstmt = null;
		ResultSet userTypeRs = null;
		ResultSet entityRs = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			responseJSON = new JSONObject();

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);

			connection = DBConnector.getConnection();
			entityJSONArray = new JSONArray();
			appDataMap = new HashMap<String, Object>();

			userTypeQry = "SELECT USER_TYPE from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where"
					+ " ULC.COMMON_ID=UI.COMMON_ID and ULC.LOGIN_USER_ID=? ";
			userTypePstmt = connection.prepareStatement(userTypeQry);
			userTypePstmt.setString(1, makerId);
			userTypeRs = userTypePstmt.executeQuery();

			if (userTypeRs.next()) {
				makerType = userTypeRs.getString(1);
			}
			logger.debug("Maker Type [" + makerType + "]");

			if (makerType.equals("CPA")) {
				userType = "CPE";
			} else if (makerType.equals("OA")) {
				userType = "OE";
			} else if (makerType.equals("BA")) {
				userType = "BE";
			} else {
				userType = "U";
			}

			entityQry = "Select UI.ENTITY,UI.USER_NAME from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC where ULC.COMMON_ID=UI.COMMON_ID and ULC.MAKER_ID=? and UI.USER_TYPE=?";
			entityPstmt = connection.prepareStatement(entityQry);
			entityPstmt.setString(1, makerId);
			entityPstmt.setString(2, userType);
			entityRs = entityPstmt.executeQuery();
			JSONObject json = null;

			while (entityRs.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_VAL, entityRs.getString(1));
				json.put(CevaCommonConstants.SELECT_KEY, entityRs.getString(2));
				entityJSONArray.add(json);
			}
			logger.debug("Entity List [" + entityJSONArray + "]");
			responseJSON.put(CevaCommonConstants.ENTITY_LIST, entityJSONArray);
			appDataMap.put(CevaCommonConstants.ENTITY_LIST, responseJSON);
			responseDTO.setData(appDataMap);
			json = null;

		} catch (SQLException e) {
			logger.debug("SQLException in GetCevaEntitys [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetCevaEntitys  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {

			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closePreparedStatement(userTypePstmt);
			DBUtils.closeResultSet(userTypeRs);
			DBUtils.closeResultSet(entityRs);

			entityJSONArray = null;
			appDataMap = null;
			userType = null;
			makerType = null;
			makerId = null;
			entityQry = null;
			userTypeQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO getCevaApplications(RequestDTO requestDTO) {

		logger.debug("Inside GetCevaApplications... ");
		JSONObject resultJson = null;
		HashMap<String, Object> roleGrpDataMap = null;
		JSONArray applTypeJSONArray = null;

		PreparedStatement appDataPstmt = null;
		ResultSet applDataRS = null;
		Connection connection = null;

		String appDataQry = "";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			applTypeJSONArray = new JSONArray();
			resultJson = new JSONObject();
			roleGrpDataMap = new HashMap<String, Object>();

			connection = DBConnector.getConnection();

			appDataQry = "Select KEY_VALUE from CONFIG_COMMON_DATA where KEY_GROUP=? and KEY_TYPE=?";

			appDataPstmt = connection.prepareStatement(appDataQry);
			appDataPstmt.setString(1, "CEVA_APPL");
			appDataPstmt.setString(2, "APPL_CODE");
			applDataRS = appDataPstmt.executeQuery();
			JSONObject json = null;
			while (applDataRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						applDataRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL,
						applDataRS.getString(1));
				applTypeJSONArray.add(json);
			}

			resultJson.put(CevaCommonConstants.APPL_TYPES, applTypeJSONArray);
			roleGrpDataMap.put(CevaCommonConstants.APPL_TYPES, resultJson);
			responseDTO.setData(roleGrpDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in InsertRecoveryDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in InsertRecoveryDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(appDataPstmt);
			DBUtils.closeResultSet(applDataRS);

			resultJson = null;
			roleGrpDataMap = null;
			applTypeJSONArray = null;
			appDataQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertEntityInfo(RequestDTO requestDTO) {

		logger.debug("Inside InsertEntityInfo... ");

		HashMap<String, Object> resultMap = null;

		String entityLocation = null;
		String entityProvince = null;
		String telephoneOff = null;
		String telephoneRes = null;
		String mobile = null;
		String fax = null;
		String contactLocation = null;
		String contactProvince = null;
		String contactCountry = null;
		String makerId = "";
		String entityName = "";
		String address = "";
		String entityCity = "";
		String entityCountry = "";
		String entityPostalCode = "";
		String applicationType = "";
		String contactPerson = "";
		String mailingAddress = "";
		String contactCity = "";
		String contactEmail = "";
		String contactPostalCode = "";

		Connection connection = null;
		CallableStatement callableStatement = null;
		String insertStoreProc = "";
		try {

			insertStoreProc = "{call USERENTITY_CREATE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			resultMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			entityName = requestJSON.getString(CevaCommonConstants.ENTITY_NAME);
			if (requestJSON.containsKey(CevaCommonConstants.ENTITY_LOCATION))
				entityLocation = requestJSON
						.getString(CevaCommonConstants.ENTITY_LOCATION);

			address = requestJSON.getString(CevaCommonConstants.ENTITY_ADDRESS);
			entityCity = requestJSON.getString(CevaCommonConstants.ENTITY_CITY);
			if (requestJSON.containsKey(CevaCommonConstants.ENTITY_PROVINCE))
				entityProvince = requestJSON
						.getString(CevaCommonConstants.ENTITY_PROVINCE);
			entityCountry = requestJSON
					.getString(CevaCommonConstants.ENTITY_COUNTRY);
			entityPostalCode = requestJSON
					.getString(CevaCommonConstants.ENTITY_COUNTRY);
			applicationType = requestJSON
					.getString(CevaCommonConstants.APPL_NAME);
			contactPerson = requestJSON
					.getString(CevaCommonConstants.CONTACT_PERSON);
			mailingAddress = requestJSON
					.getString(CevaCommonConstants.MAILING_ADDRESS);
			if (requestJSON.containsKey(CevaCommonConstants.OFF_TELEPHONE))
				telephoneOff = requestJSON
						.getString(CevaCommonConstants.OFF_TELEPHONE);
			if (requestJSON.containsKey(CevaCommonConstants.RES_TELEPHONE))
				telephoneRes = requestJSON
						.getString(CevaCommonConstants.RES_TELEPHONE);
			if (requestJSON.containsKey(CevaCommonConstants.MOBILE))
				mobile = requestJSON.getString(CevaCommonConstants.MOBILE);
			if (requestJSON.containsKey(CevaCommonConstants.FAX))
				fax = requestJSON.getString(CevaCommonConstants.FAX);
			if (requestJSON.containsKey(CevaCommonConstants.CONTACT_LOCATION))
				contactLocation = requestJSON
						.getString(CevaCommonConstants.CONTACT_LOCATION);
			contactCity = requestJSON
					.getString(CevaCommonConstants.CONTACT_CITY);
			if (requestJSON.containsKey(CevaCommonConstants.CONTACT_PROVINCE))
				contactProvince = requestJSON
						.getString(CevaCommonConstants.CONTACT_PROVINCE);
			if (requestJSON.containsKey(CevaCommonConstants.CONTACT_COUNTRY))
				contactCountry = requestJSON
						.getString(CevaCommonConstants.CONTACT_COUNTRY);
			contactEmail = requestJSON
					.getString(CevaCommonConstants.CONTACT_EMAIL);
			contactPostalCode = requestJSON
					.getString(CevaCommonConstants.ENTITY_COUNTRY);

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection + "]");

			callableStatement = connection.prepareCall(insertStoreProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, entityName);
			callableStatement.setString(3, entityLocation);
			callableStatement.setString(4, address);
			callableStatement.setString(5, entityCity);
			callableStatement.setString(6, entityProvince);
			callableStatement.setString(7, entityCountry);
			callableStatement.setString(8, entityPostalCode);
			callableStatement.setString(9, applicationType);
			callableStatement.setString(10, contactPerson);
			callableStatement.setString(11, mailingAddress);
			callableStatement.setString(12, telephoneOff);
			callableStatement.setString(13, telephoneRes);
			callableStatement.setString(14, mobile);
			callableStatement.setString(15, fax);
			callableStatement.setString(16, contactLocation);
			callableStatement.setString(17, contactCity);
			callableStatement.setString(18, contactProvince);
			callableStatement.setString(19, contactCountry);
			callableStatement.setString(20, contactEmail);
			callableStatement.setString(21, contactPostalCode);
			callableStatement.registerOutParameter(22, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(23, java.sql.Types.INTEGER);
			//callableStatement.setString(24, requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			String entityCode = callableStatement.getString(22);
			int resCnt = callableStatement.getInt(23);
			logger.debug("ResultCnt from DB :::" + resCnt);

			if (resCnt == 1) {
				responseDTO
						.addMessages("Entity Successfully Craeted with Entity ID: "
								+ entityCode);
			} else if (resCnt == -1) {
				resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);
				responseDTO.addError("Entity Already Created. ");
			} else {
				resultMap.put(CevaCommonConstants.RESPONSE_JSON, requestJSON);
				responseDTO.setData(resultMap);
				responseDTO.addError("Entity  Creation failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in InsertEntityInfo [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in InsertEntityInfo  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);

			resultMap = null;

			entityLocation = null;
			entityProvince = null;
			telephoneOff = null;
			telephoneRes = null;
			mobile = null;
			fax = null;
			contactLocation = null;
			contactProvince = null;
			contactCountry = null;
			makerId = null;
			entityName = null;
			address = null;
			entityCity = null;
			entityCountry = null;
			entityPostalCode = null;
			applicationType = null;
			contactPerson = null;
			mailingAddress = null;
			contactCity = null;
			contactEmail = null;
			contactPostalCode = null;
		}
		return responseDTO;
	}

	public ResponseDTO getUserCreatePage(RequestDTO requestDTO) {

		logger.debug("Inside GetUserCreatePage... ");

		ArrayList<String> applicationTypeList = null;
		HashMap<String, Object> appDataMap = null;
		JSONArray applicationTypeJSONArray = null;
		// String entity = "";

		Connection connection = null;
		PreparedStatement appDataPstmt = null;
		ResultSet appDataRs = null;
		String appDataQry = "";
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			// entity = requestJSON.getString(CevaCommonConstants.ENTITY);

			applicationTypeList = new ArrayList<String>();
			appDataMap = new HashMap<String, Object>();
			applicationTypeJSONArray = new JSONArray();

			appDataQry = "Select KEY_VALUE from CONFIG_COMMON_DATA where KEY_GROUP=? and KEY_TYPE=?";

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			appDataPstmt = connection.prepareStatement(appDataQry);
			appDataPstmt.setString(1, "CEVA_APPL");
			appDataPstmt.setString(2, "APPL_CODE");
			appDataRs = appDataPstmt.executeQuery();
			JSONObject json;
			while (appDataRs.next()) {
				applicationTypeList.add(appDataRs.getString(1));
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_VAL, appDataRs.getString(1));
				json.put(CevaCommonConstants.SELECT_KEY, appDataRs.getString(1));
				applicationTypeJSONArray.add(json);
			}

			responseJSON.put(CevaCommonConstants.APPL_TYPES,
					applicationTypeJSONArray);
			appDataMap.put(CevaCommonConstants.APPL_TYPES, responseJSON);
			responseDTO.setData(appDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetUserCreatePage [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUserCreatePage  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(appDataPstmt);
			DBUtils.closeResultSet(appDataRs);
		}

		return responseDTO;
	}

	public ResponseDTO userModifyAck(RequestDTO requestDTO) {
		logger.debug("Inside UserModifyAck... ");

		Connection connection = null;
		CallableStatement callableStatement = null;

		String userString = "";
		String userMakerId = "";
		String errorMessage = "";

		String updateUserDet = "{call UserModifyAckDetails(?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			userString = requestJSON.getString("user_info");
			userMakerId = requestJSON.getString("user_id");

			connection = DBConnector.getConnection();
			callableStatement = connection.prepareCall(updateUserDet);
			callableStatement.setString(1, userString);
			callableStatement.setString(2, userMakerId);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.setString(4,requestJSON.getString(CevaCommonConstants.IP));

			callableStatement.executeUpdate();

			errorMessage = callableStatement.getString(3);

			if (errorMessage.equalsIgnoreCase("SUCCESS")) {
				responseDTO.addMessages("User Modify Details Acknowledged. ");
			} else {
				responseDTO.addError(errorMessage);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in UserModifyAck [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UserModifyAck  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			userString = null;
			userMakerId = null;
			errorMessage = null;
			updateUserDet = null;
		}
		return responseDTO;
	}

	public ResponseDTO createUser(RequestDTO requestDTO) {

		logger.debug("Inside CreateUser... ");

		Connection connection = null;
		PreparedStatement pstmt = null;
		CallableStatement callableStatement = null;

		String makerId = null;
		String userId = null;
		String employeeNo = null;
		String firstName = null;
		String lastName = null;
		String expiryDate = null;
		String email = null;
		String applicationType = null;
		String entity = null;
		String mobile = null;
		String telephoneOff = null;
		String telephoneRes = null;
		String fax = null;

		String insertAdminStoreProc = "{call USERADMIN_CREATE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String hashPassowrd = "";
		String passwordQry = "";
		String userID = "";
		String password = "";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			// entity = entity.split("-")[0];
			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			userId = requestJSON.getString(CevaCommonConstants.USER_ID);
			employeeNo = requestJSON.getString(CevaCommonConstants.EMPLOYEE_NO);
			firstName = requestJSON.getString(CevaCommonConstants.F_NAME);
			lastName = requestJSON.getString(CevaCommonConstants.L_NAME);
			expiryDate = requestJSON.getString(CevaCommonConstants.EXPIRY_DATE);
			email = requestJSON.getString(CevaCommonConstants.EMAIL);
			applicationType = requestJSON
					.getString(CevaCommonConstants.APPL_NAME);
			entity = requestJSON.getString(CevaCommonConstants.ENTITY);
			mobile = requestJSON.getString(CevaCommonConstants.MOBILE);

			connection = DBConnector.getConnection();
			logger.debug("Maker Id [" + makerId + "]");

			callableStatement = connection.prepareCall(insertAdminStoreProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, userId);
			callableStatement.setString(3, employeeNo);
			callableStatement.setString(4, firstName);
			callableStatement.setString(5, lastName);
			callableStatement.setString(6, expiryDate);
			callableStatement.setString(7, email);
			callableStatement.setString(8, applicationType);
			callableStatement.setString(9, entity);
			callableStatement.setString(10, telephoneOff);
			callableStatement.setString(11, telephoneRes);
			callableStatement.setString(12, mobile);
			callableStatement.setString(13, fax);
			callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(16, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			userID = callableStatement.getString(14);
			password = callableStatement.getString(15);
			int resCnt = callableStatement.getInt(16);
			logger.debug("ResCnt [" + resCnt + "---" + userID + "---"
					+ password + "]");
			responseDTO = getCevaEntitys(requestDTO);
			if (resCnt == 1) {
				hashPassowrd = CevaTokenGenerator.getToken(password);
				passwordQry = "Update USER_LOGIN_CREDENTIALS set ENCRYPT_PASSWORD=? where LOGIN_USER_ID=? and PASSWORD=?";
				pstmt = connection.prepareStatement(passwordQry);
				pstmt.setString(1, hashPassowrd);
				pstmt.setString(2, userID);
				pstmt.setString(3, password);
				int updCnt = pstmt.executeUpdate();
				logger.debug("Update Count [" + updCnt + "]");
				if (updCnt == 1) {
					responseDTO
							.addMessages("User Admin Successfully Craeted with User ID: "
									+ userID);
				}
			} else if (resCnt == -1) {
				responseDTO.addMessages("User Admin Already Created. ");
			} else {
				responseDTO.addError("User Admin  Creation failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in CreateUser [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in CreateUser  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closePreparedStatement(pstmt);

			makerId = null;
			userId = null;
			employeeNo = null;
			firstName = null;
			lastName = null;
			expiryDate = null;
			email = null;
			applicationType = null;
			entity = null;
			mobile = null;
			telephoneOff = null;
			telephoneRes = null;
			fax = null;

			insertAdminStoreProc = null;
			hashPassowrd = null;
			passwordQry = null;
			userID = null;
			password = null;
		}
		return responseDTO;
	}

	public ResponseDTO resetPassword(RequestDTO requestDTO) {

		logger.debug("Inside ResetPassword.. ");

		String password = null;
		String encryptPassword = null;
		String oldPasswordCheckQry = null;
		String oldPasswordVal = null;
		String prevPassword = null;
		String repeatPwdStatus = null;

		String makerId = null;
		String userId = null;
		String reason = null;

		Connection connection = null;
		CallableStatement callableStatement = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		HashMap<String, String> passwordMap = null;

		String resetPwdProc = "";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			passwordMap = new HashMap<String, String>();

			oldPasswordCheckQry = "SELECT PASSWORD,OLD_PASSWORDS from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID=?";

			logger.debug("Request JSON [" + requestJSON + "]");

			password = CommonUtil.generatePassword();
			logger.debug("Password [" + password + "]");
			encryptPassword = CommonUtil.b64_sha256(password);
			logger.debug("EncryptPassword [" + encryptPassword + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			userId = requestJSON.getString(CevaCommonConstants.USER_ID);
			reason = requestJSON.getString(CevaCommonConstants.REASON);

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			pstmt = connection.prepareStatement(oldPasswordCheckQry);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				prevPassword = rs.getString(1);
				oldPasswordVal = rs.getString(2);
			}

			logger.debug("OldPasswordVal [" + oldPasswordVal
					+ "] PrevPassword [" + prevPassword + "]");
			if (prevPassword != null) {
				passwordMap = (HashMap<String, String>) PasswordValidation
						.maxCheck(password, prevPassword, oldPasswordVal);
				repeatPwdStatus = passwordMap.get("RESPCODE");
				logger.debug("RepeatPwdStatus [" + repeatPwdStatus + "]");
			}
			if (repeatPwdStatus.equals("00")) {
				oldPasswordVal = passwordMap.get("OLDPWDS");
				logger.debug(" [oldPasswordVal from MAP :" + oldPasswordVal
						+ "]");

				resetPwdProc = "{call resetPasswordProc(?,?,?,?,?,?,?,?,?)}";

				callableStatement = connection.prepareCall(resetPwdProc);
				callableStatement.setString(1, makerId);
				callableStatement.setString(2, userId);
				callableStatement.setString(3, reason);
				callableStatement.setString(4, password);
				callableStatement.setString(5, encryptPassword);
				callableStatement.setString(6, prevPassword);
				callableStatement.setString(7, oldPasswordVal);
				callableStatement.registerOutParameter(8,
						java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(9,
						java.sql.Types.VARCHAR);
				// callableStatement.setString(10,
				// requestJSON.getString(CevaCommonConstants.IP));
				callableStatement.executeUpdate();

				logger.debug("Message [" + callableStatement.getString(9) + "]");

				int resCnt = callableStatement.getInt(8);
				if (resCnt == 1) {
					responseDTO
							.addMessages("Password Reset Successfully Completed. ");
				} else if (resCnt == -1) {
					responseDTO
							.addError("User Id Doen't Belongs to Current Admin.");
				} else {
					responseDTO
							.addError("There is an issue in Reset Password ");
				}
			} else {
				responseDTO
						.addError("New Password should not be equal one of the last 5 passwords. ");
			}
		} catch (SQLException e) {
			logger.debug("SQLException in ResetPassword [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in ResetPassword  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeResultSet(rs);

			password = null;
			encryptPassword = null;
			oldPasswordCheckQry = null;
			oldPasswordVal = null;
			prevPassword = null;
			repeatPwdStatus = null;

			makerId = null;
			userId = null;
			reason = null;

			passwordMap = null;

			resetPwdProc = null;
		}

		return responseDTO;
	}

	/*
	 * public ResponseDTO resetPassword_bkp(RequestDTO requestDTO) {
	 * logger.debug("Request JSON [" + requestJSON + "]"); Connection connection
	 * = null; String makerId = ""; String userId = ""; String reason = "";
	 * 
	 * CallableStatement callableStatement = null; PreparedStatement pstmt =
	 * null; String resetPwdProc = "{call resetPasswordProc(?,?,?,?,?,?)}"; try
	 * {
	 * 
	 * responseDTO = new ResponseDTO(); responseJSON = new JSONObject();
	 * requestJSON = requestDTO.getRequestJSON(); logger.debug("Request JSON ["
	 * + requestJSON + "]");
	 * 
	 * makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID); userId =
	 * requestJSON.getString(CevaCommonConstants.USER_ID); reason =
	 * requestJSON.getString(CevaCommonConstants.REASON);
	 * 
	 * connection = DBConnector.getConnection(); logger.debug(" connection :::"
	 * + connection);
	 * 
	 * callableStatement = connection.prepareCall(resetPwdProc);
	 * callableStatement.setString(1, makerId); callableStatement.setString(2,
	 * userId); callableStatement.setString(3, reason);
	 * callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
	 * callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
	 * callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
	 * 
	 * callableStatement.executeUpdate(); String newPassword =
	 * callableStatement.getString(4); int resCnt = callableStatement.getInt(5);
	 * if (resCnt == 1) { String hashPassowrd =
	 * CevaTokenGenerator.getToken(newPassword); String passwordQry =
	 * "Update USER_LOGIN_CREDENTIALS set ENCRYPT_PASSWORD=? where LOGIN_USER_ID=? and PASSWORD=?"
	 * ; pstmt = connection.prepareStatement(passwordQry); pstmt.setString(1,
	 * hashPassowrd); pstmt.setString(2, userId); pstmt.setString(3,
	 * newPassword); int updCnt = pstmt.executeUpdate();
	 * logger.debug("[updCnt==>" + updCnt + "]"); if (updCnt == 1) { responseDTO
	 * .addMessages("Password Reset Successfully Completed. "); } } else if
	 * (resCnt == -1) { responseDTO
	 * .addError("User Id Doen't Belongs to Current Admin."); } else {
	 * responseDTO.addError("There is an issue in Reset Password "); } } catch
	 * (SQLException e) {
	 * 
	 * try { connection.rollback(); } catch (SQLException e1) {
	 * e1.printStackTrace(); } } finally { DBUtils.closeConnection(connection);
	 * DBUtils.closeCallableStatement(callableStatement);
	 * DBUtils.closePreparedStatement(pstmt); }
	 * 
	 * return responseDTO; }
	 */

	public ResponseDTO updateUserStatus(RequestDTO requestDTO) {
		logger.debug("Inside UpdateUserStatus... ");

		Connection connection = null;
		String makerId = "";
		String userId = "";

		CallableStatement callableStatement = null;
		String updateStatusProc = "{call UPDATEUSERSTATUSPROC(?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			userId = requestJSON.getString(CevaCommonConstants.USER_ID);

			if((makerId.toUpperCase()).equalsIgnoreCase(userId.toUpperCase())){
				responseDTO.addError("Login User id and User Status Change Uset id are Same, so User Id Doen't Change Status");
			}else{
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(updateStatusProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, userId);
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.setString(4, requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(3);
			logger.debug("ResCnt is [" + resCnt + "]");
			if (resCnt == 1) {
				responseDTO.addMessages("User Status Changed. Authorisation required ");
			} else if (resCnt == -1) {
				responseDTO.addError("User Id Doen't Belongs to Current Admin. ");
			}
			else if (resCnt == -3) {
				responseDTO.addError("User Id already in pending State");
			}
			else {
				responseDTO.addError("There is an issue in Changing Current Status ");
			}
			}
		} catch (SQLException e) {
			logger.debug("SQLException in UpdateUserStatus [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UpdateUserStatus  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			makerId = null;
			userId = null;
			updateStatusProc = null;
		}
		return responseDTO;
	}

	public ResponseDTO getRoles(RequestDTO requestDTO) {

		logger.debug("Inside GetRoles... ");

		JSONObject resultJson = null;
		HashMap<String, Object> roleGrpDataMap = null;
		JSONArray applTypeJSONArray = null;
		JSONArray roleGrpJSONArray = null;
		JSONArray rolesJSONArray = null;

		Connection connection = null;

		PreparedStatement appDataPstmt = null;
		PreparedStatement roleGrpPstmt = null;
		PreparedStatement rolesPstmt = null;
		ResultSet roleGrpRs = null;
		ResultSet rolesRS = null;
		ResultSet applDataRS = null;

		String appDataQry = "";
		String roleGrpQry = "";
		String applName = "";
		String rolesQry = "";

		try {
			resultJson = new JSONObject();
			roleGrpDataMap = new HashMap<String, Object>();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			applTypeJSONArray = new JSONArray();
			roleGrpJSONArray = new JSONArray();
			rolesJSONArray = new JSONArray();

			appDataQry = "Select KEY_VALUE from CONFIG_COMMON_DATA where KEY_GROUP=? and KEY_TYPE=?";
			connection = DBConnector.getConnection();
			appDataPstmt = connection.prepareStatement(appDataQry);
			appDataPstmt.setString(1, "CEVA_APPL");
			appDataPstmt.setString(2, "APPL_CODE");
			applDataRS = appDataPstmt.executeQuery();

			JSONObject json = null;
			while (applDataRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						applDataRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL,
						applDataRS.getString(1));
				applTypeJSONArray.add(json);
			}
			logger.debug("ApplType JSONArray [" + applTypeJSONArray + "]");

			json = null;

			roleGrpQry = "Select GROUP_ID,GROUP_NAME from USER_GROUPS";
			roleGrpPstmt = connection.prepareStatement(roleGrpQry);
			roleGrpRs = roleGrpPstmt.executeQuery();
			while (roleGrpRs.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, roleGrpRs.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, roleGrpRs.getString(1));
				roleGrpJSONArray.add(json);
			}
			logger.debug("RoleGrpJSONArray [" + roleGrpJSONArray + "]");
			applName = requestJSON.getString(CevaCommonConstants.APPL_NAME);

			if (applName.equals("Ceva")) {
				rolesQry = "Select MENU_ID,MENU_NAME from CEVA_MENU_LIST where ACTION_STATUS=? and APPL_CODE in (?,?) order by MENU_ID";
				rolesPstmt = connection.prepareStatement(rolesQry);
				rolesPstmt.setString(1, "Y");
				rolesPstmt.setString(2,
						requestJSON.getString(CevaCommonConstants.APPL_NAME));
				rolesPstmt.setString(3, "banking");
			} else {
				rolesQry = "Select MENU_ID,MENU_NAME from CEVA_MENU_LIST where ACTION_STATUS=? and APPL_CODE=? order by MENU_ID";
				rolesPstmt = connection.prepareStatement(rolesQry);
				rolesPstmt.setString(1, "Y");
				rolesPstmt.setString(2,
						requestJSON.getString(CevaCommonConstants.APPL_NAME));
			}

			rolesRS = rolesPstmt.executeQuery();
			while (rolesRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, rolesRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, rolesRS.getString(1));
				rolesJSONArray.add(json);
			}
			logger.debug("RolesJSON Array [" + rolesJSONArray + "]");

			resultJson.put(CevaCommonConstants.APPL_TYPES, applTypeJSONArray);
			resultJson.put(CevaCommonConstants.ROLEGRP_LIST, roleGrpJSONArray);
			resultJson.put(CevaCommonConstants.ROLES_LIST, rolesJSONArray);
			roleGrpDataMap.put(CevaCommonConstants.ROLE_DATA, resultJson);
			responseDTO.setData(roleGrpDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetRoles [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetRoles  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(appDataPstmt);
			DBUtils.closePreparedStatement(roleGrpPstmt);
			DBUtils.closePreparedStatement(rolesPstmt);
			DBUtils.closeResultSet(applDataRS);
			DBUtils.closeResultSet(roleGrpRs);
			DBUtils.closeResultSet(rolesRS);

			resultJson = null;
			roleGrpDataMap = null;
			applTypeJSONArray = null;
			roleGrpJSONArray = null;
			rolesJSONArray = null;
			appDataQry = null;
			roleGrpQry = null;
			applName = null;
			rolesQry = null;
		}

		return responseDTO;
	}

	public ResponseDTO createRoleGroup(RequestDTO requestDTO) {
		logger.debug("Inside CreateRoleGroup... ");
		Connection connection = null;
		String makerId = "";
		String applicationName = "";
		String roleGrpId = "";
		String roleGrpName = "";

		CallableStatement callableStatement = null;

		String createRoleGrp = "{call CreateRoleGroupProc(?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			applicationName = requestJSON
					.getString(CevaCommonConstants.APPL_NAME);
			roleGrpId = requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID);
			roleGrpName = requestJSON
					.getString(CevaCommonConstants.ROLE_GRP_NAME);

			callableStatement = connection.prepareCall(createRoleGrp);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, applicationName);
			callableStatement.setString(3, roleGrpId);
			callableStatement.setString(4, roleGrpName);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			//callableStatement.setString(6, requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);
			logger.debug("RresCnt [" + resCnt + "]");
			responseDTO = getCevaApplications(requestDTO);
			if (resCnt == 1) {
				responseDTO
						.addMessages(roleGrpId + " is successfully Created.");
			} else if (resCnt == -1) {
				responseDTO
						.addError("Entity doesnt belongs to Current login User. ");
			} else if (resCnt == -2) {

				responseDTO
						.addError("Role Group Id Already Existed for this Entity. ");
			} else {
				responseDTO.addError("There is an issue in Create Role Group ");
			}
		} catch (SQLException e) {
			logger.debug("SQLException in createRoleGroup [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in createRoleGroup  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			makerId = "";
			applicationName = "";
			roleGrpId = "";
			roleGrpName = "";
		}
		return responseDTO;
	}

	public ResponseDTO assignRoles(RequestDTO requestDTO) {

		logger.debug("Inside AssignRoles... ");

		Connection connection = null;
		String makerId = "";
		String applicationName = "";
		String roleGrpId = "";
		String selectedRoles = "";

		CallableStatement callableStatement = null;
		String assignRoles = "{call AssignRolesProc(?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			applicationName = requestJSON
					.getString(CevaCommonConstants.APPL_NAME);
			roleGrpId = requestJSON.getString(CevaCommonConstants.ROLE_GRP_ID);
			selectedRoles = requestJSON
					.getString(CevaCommonConstants.SELECTED_ROLES);

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection.prepareCall(assignRoles);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, applicationName);
			callableStatement.setString(3, roleGrpId);
			callableStatement.setString(4, selectedRoles);
			callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
			//callableStatement.setString(6, requestJSON.getString(CevaCommonConstants.IP));
			logger.debug("Before calling executeUpdate Method.");
			callableStatement.executeUpdate();
			logger.debug("After calling executeUpdate Method.");
			int resCnt = callableStatement.getInt(5);
			logger.debug("ResCnt [" + resCnt + "]");
			responseDTO = getRoles(requestDTO);
			if (resCnt == 1) {
				responseDTO.addMessages("Assigned Roles to " + roleGrpId
						+ " is successfully Completed.");
			} else if (resCnt == -1) {
				responseDTO.addError("Role Group Combination  doesnt Exists. ");
			} else {
				responseDTO.addError("There is an issue in Create Role Group ");
			}
		} catch (SQLException e) {
			logger.debug("SQLException in AssignRoles [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in AssignRoles  [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			makerId = "";
			applicationName = "";
			roleGrpId = "";
			selectedRoles = "";
		}
		return responseDTO;
	}

	public ResponseDTO getUsersToAssign(RequestDTO requestDTO) {
		logger.debug("Inside GetUsersToAssign... ");

		HashMap<String, Object> userGrpDataMap = null;
		JSONArray applTypeJSONArray = null;
		JSONArray roleGrpJSONArray = null;
		JSONArray usersJSONArray = null;
		JSONObject resultJson = null;

		Connection connection = null;

		ResultSet usersRS = null;
		PreparedStatement usersPstmt = null;
		PreparedStatement appDataPstmt = null;
		PreparedStatement roleGrpPstmt = null;
		ResultSet roleGrpRs = null;
		ResultSet applDataRS = null;

		String usersQry = null;
		String appDataQry = "";
		String applName = "";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			resultJson = new JSONObject();

			userGrpDataMap = new HashMap<String, Object>();
			applTypeJSONArray = new JSONArray();
			roleGrpJSONArray = new JSONArray();
			usersJSONArray = new JSONArray();

			connection = DBConnector.getConnection();

			appDataQry = "Select KEY_VALUE from CONFIG_COMMON_DATA where KEY_GROUP=? and KEY_TYPE=?";

			appDataPstmt = connection.prepareStatement(appDataQry);
			appDataPstmt.setString(1, "CEVA_APPL");
			appDataPstmt.setString(2, "APPL_CODE");
			applDataRS = appDataPstmt.executeQuery();
			JSONObject json = null;
			while (applDataRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						applDataRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL,
						applDataRS.getString(1));
				applTypeJSONArray.add(json);
			}
			logger.debug("ApplTypeJSONArray [" + applTypeJSONArray + "]");

			String roleGrpQry = "Select GROUP_ID,GROUP_NAME from USER_GROUPS where MAKER_ID=?";
			roleGrpPstmt = connection.prepareStatement(roleGrpQry);
			roleGrpPstmt.setString(1,
					requestJSON.getString(CevaCommonConstants.MAKER_ID));
			roleGrpRs = roleGrpPstmt.executeQuery();
			while (roleGrpRs.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, roleGrpRs.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL, roleGrpRs.getString(1));
				roleGrpJSONArray.add(json);
			}
			logger.debug("RoleGrpJSONArray [ " + roleGrpJSONArray + "]");
			applName = requestJSON.getString(CevaCommonConstants.APPL_NAME);

			if (applName.equals("Ceva")) {
				usersQry = "Select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID=UI.COMMON_ID and UI.USER_GROUPS is null and ULC.MAKER_ID=?";
				usersPstmt = connection.prepareStatement(usersQry);
				usersPstmt.setString(1,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
			} else {
				usersQry = "Select ULC.LOGIN_USER_ID from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI where ULC.COMMON_ID=UI.COMMON_ID and UI.USER_GROUPS is null and ULC.MAKER_ID=? and ULC.APPL_CODE=?";
				usersPstmt = connection.prepareStatement(usersQry);
				usersPstmt.setString(1,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				usersPstmt.setString(2,
						requestJSON.getString(CevaCommonConstants.APPL_NAME));
			}

			usersRS = usersPstmt.executeQuery();
			while (usersRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY, usersRS.getString(1));
				json.put(CevaCommonConstants.SELECT_VAL, usersRS.getString(1));
				usersJSONArray.add(json);
			}
			logger.debug("UsersJSON Array [" + usersJSONArray + "]");

			resultJson.put(CevaCommonConstants.APPL_TYPES, applTypeJSONArray);
			resultJson.put(CevaCommonConstants.ROLEGRP_LIST, roleGrpJSONArray);
			resultJson.put(CevaCommonConstants.USERS_LIST, usersJSONArray);
			userGrpDataMap
					.put(CevaCommonConstants.ASSIGN_USER_DATA, resultJson);
			responseDTO.setData(userGrpDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetUsersToAssign [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUsersToAssign  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(usersPstmt);
			DBUtils.closePreparedStatement(appDataPstmt);
			DBUtils.closePreparedStatement(roleGrpPstmt);

			DBUtils.closeResultSet(applDataRS);
			DBUtils.closeResultSet(roleGrpRs);
			DBUtils.closeResultSet(usersRS);

			userGrpDataMap = null;
			applTypeJSONArray = null;
			roleGrpJSONArray = null;
			usersJSONArray = null;
			resultJson = null;
			usersQry = null;
			appDataQry = null;
			applName = null;

		}

		return responseDTO;
	}

	public ResponseDTO getAdminCraeteInfo(RequestDTO requestDTO) {

		logger.debug("Inside GetAdminCraeteInfo.... ");

		HashMap<String, Object> locationDataMap = null;
		JSONObject resultJson = null;
		JSONArray locationJSONArray = null;
		JSONArray userDesigJSONArray = null;
		JSONArray userGrpJSONArray = null;
		JSONArray storeJSONArray = null;
		JSONArray merchantAdminJSONArray = null;
		JSONArray clusteridJSONArray = null;
		CallableStatement callableStmt = null;
		ResultSet locationRS = null;
		ResultSet userDesignationRS = null;
		ResultSet usergrpRS = null;
		ResultSet storeRS = null;

		String locationQry = "";
		String groupId = "";
		String groupType = "";
		String userGroups = "";
		Connection connection = null;
		String userType = "";
		locationQry = "{call GetRightsPkg.pGetBranchDetails(?,?,?,?,?,?,?,?)}";
		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			groupId = requestJSON.getString("GROUP_ID");
			locationDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			locationJSONArray = new JSONArray();
			userDesigJSONArray = new JSONArray();
			userGrpJSONArray = new JSONArray();
			storeJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			callableStmt = connection.prepareCall(locationQry);

			callableStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			callableStmt.setString(2,requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStmt.setString(3, groupId);
			callableStmt.registerOutParameter(4, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(5, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callableStmt.registerOutParameter(7, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(8, OracleTypes.VARCHAR);

			callableStmt.execute();
			logger.debug("Errormessage[" + callableStmt.getString(1) + "]");
			JSONObject json = null;
			/*locationRS = (ResultSet) callableStmt.getObject(4);
			
			while (locationRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,locationRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,locationRS.getString(1));
				locationJSONArray.add(json);
				json.clear();
				json = null;
			}*/
			
			/*JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.branchList());
			JSONArray jsonarray =  JSONArray.fromObject(json1.get("branchList"));
			Iterator iterator = jsonarray.iterator();
		       while (iterator.hasNext()) {
		    	   JSONObject jsonobj=(JSONObject)iterator.next();
			       	
			       	json = new JSONObject();
					json.put(CevaCommonConstants.SELECT_KEY,(jsonobj).get("branchCode"));
					json.put(CevaCommonConstants.SELECT_VAL,(jsonobj).get("branchName"));
					locationJSONArray.add(json);
					json.clear();
					json = null;
			       	
		       }
		
			

			resultJson.put(CevaCommonConstants.LOCATION_LIST, locationJSONArray);*/

			userDesignationRS = (ResultSet) callableStmt.getObject(5);
			while (userDesignationRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,userDesignationRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,userDesignationRS.getString(1));
				userDesigJSONArray.add(json);
				json.clear();
				json = null;
			}
			resultJson.put("USER_DESIGNATION", userDesigJSONArray);
			
			
			usergrpRS = (ResultSet) callableStmt.getObject(7);
			while (usergrpRS.next()) {
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,usergrpRS.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,usergrpRS.getString(1));
				userGrpJSONArray.add(json);
				json.clear();
				json = null;
			}
			resultJson.put("USER_GROUPS", userGrpJSONArray);

			groupType = callableStmt.getString(6);
			
			logger.debug("GroupType [" + groupType + "]");
			resultJson.put("GROUP_TYPE", groupType);
			locationDataMap.put(CevaCommonConstants.LOCATION_INFO, resultJson);

			logger.debug(" LocationDataMap [" + locationDataMap + "]");
			responseDTO.setData(locationDataMap);

			userType = callableStmt.getString(8);
			resultJson.put("USER_LEVEL", userType);

			logger.debug(" userType [" + userType + "]");

		/*	if (userType.equals("MS") || userType.equals("MU")) {
				storeRS = (ResultSet) callableStmt.getObject(7);
				while (storeRS.next()) {
					json = new JSONObject();
					json.put(CevaCommonConstants.SELECT_KEY,
							storeRS.getString(2));
					json.put(CevaCommonConstants.SELECT_VAL,
							storeRS.getString(1));
					storeJSONArray.add(json);
				}
			}

			logger.debug("inside [storeJSONArray:::" + storeJSONArray + "]");
			resultJson.put("MERCHANT_LIST", storeJSONArray);*/

			String merchantAdminQry = "select MERCHANT_ID,MERCHANT_NAME from MERCHANT_MASTER where "
					+ "MERCHANT_ID not in (select DISTINCT MERCHANT_ID from USER_INFORMATION where MERCHANT_ID is not null or length(MERCHANT_ID)=16 )";

			PreparedStatement pstmt = connection.prepareStatement(merchantAdminQry);
			ResultSet rs= pstmt.executeQuery();
			merchantAdminJSONArray = new JSONArray();
			while(rs.next()){
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						rs.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,
						rs.getString(1));
				merchantAdminJSONArray.add(json);
			}
			resultJson.put("MERCHANT_ADMIN_LIST", merchantAdminJSONArray);
			
			
			String clusterid = "select CLUSTER_NAME,CLUSTER_ID from CLUSTER_TBL";

			PreparedStatement pstmt1 = connection.prepareStatement(clusterid);
			ResultSet rs1= pstmt1.executeQuery();
			clusteridJSONArray = new JSONArray();
			while(rs1.next()){
				json = new JSONObject();
				json.put(CevaCommonConstants.SELECT_KEY,
						rs1.getString(2));
				json.put(CevaCommonConstants.SELECT_VAL,
						rs1.getString(1));
				clusteridJSONArray.add(json);
			}
			resultJson.put("CLUSTER_LIST", clusteridJSONArray);
			
			

			logger.debug("inside [responseDTO:::" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetAdminCraeteInfo ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetAdminCraeteInfo  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStmt);
			DBUtils.closeResultSet(locationRS);
			DBUtils.closeResultSet(userDesignationRS);

			locationDataMap = null;
			resultJson = null;
			locationJSONArray = null;
			userDesigJSONArray = null;

			locationQry = null;
			groupId = null;
			groupType = null;
		}

		return responseDTO;
	}

	public ResponseDTO insertIctAdminDetails(RequestDTO requestDTO) {
		logger.debug("Inside InsertIctAdminDetails... ");

		Connection connection = null;
		CallableStatement callableStatement = null;

		String insertBankInfoProc = "{call insertICTAdminProc(?,?,?,?,?,?,?)}";
		// String insertmerchInfoProc =
		// "{call INSERTICTADMINPROC1(?,?,?,?,?,?,?)}";

		String multiData = "";
		String makerId = "";
		String groupId = "";
		String entityId = "";
		String userDetails = "";
		String ip = null;
		String auditQry = "";
		CallableStatement cstmt = null;
		
		HashMap<String, Object> userGrpDataMap = null;
		try {
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			multiData = requestJSON.getString(CevaCommonConstants.MULTI_DATA);
			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			ip=requestJSON.getString(CevaCommonConstants.IP);
			groupId = requestJSON.getString("GROUP_ID");
			entityId = requestJSON.getString("ENTITY_ID");

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			/*
			 * if(typeuser.equalsIgnoreCase("Bank")) {
			 */
			callableStatement = connection.prepareCall(insertBankInfoProc);
			/*
			 * } else if(typeuser.equalsIgnoreCase("Merchant")) {
			 * callableStatement = connection.prepareCall(insertmerchInfoProc);
			 * }
			 */
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, multiData);
			callableStatement.setString(3, groupId);
			callableStatement.setString(4, entityId);
			callableStatement.registerOutParameter(5, Types.INTEGER);
			callableStatement.registerOutParameter(6, Types.VARCHAR);
			callableStatement.setString(7,requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				userGrpDataMap = new HashMap<String, Object>();
				responseJSON = new JSONObject();
				userDetails = callableStatement.getString(6);
				logger.debug("User Details  [" + userDetails + "]");
				responseJSON.put("USER_DETAILS", userDetails);
				userGrpDataMap.put("USER_DETAILS", responseJSON);
				responseDTO.setData(userGrpDataMap);

			} else if (resCnt == -1) {
				responseDTO = getAdminCraeteInfo(requestDTO);
				responseDTO.addError("ICT Amdin Already Exists. ");
			} else {
				responseDTO = getAdminCraeteInfo(requestDTO);
				responseDTO.addError("ICT Amdin Creation failed.");
			}
			
			auditQry = "{call AUDITPKG.INSERTAUDIT(?,?,?,?,?,?,?,?)}";
			cstmt = connection.prepareCall(auditQry);
			cstmt.setString(1, "insertIctAdminDetails");
			cstmt.setString(2, makerId);
			cstmt.setString(3, "WEB");
			if (resCnt == 1) {
			cstmt.setString(4, " New User created with Group ID [ "+groupId+"] and Entity ID ["+entityId+"]");
			} else if (resCnt == -1) {
				cstmt.setString(4, " Given Data Already Exists   ");	
			} else {
				cstmt.setString(4, " New User Creation failed. ");
			}
			cstmt.setString(5, ip);
			cstmt.setString(6, " ");
			cstmt.setString(7, " ");
			cstmt.setString(8, " ");

			int i = cstmt.executeUpdate();
			if (i == 1) {
				logger.debug("Action Identification -> Successfully Inserted "+ ip);
			} else {
				logger.debug("Action Identification -> insertion failed due to some error "+ ip);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in insertIctAdminDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in insertIctAdminDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeCallableStatement(cstmt);
			insertBankInfoProc = null;
			multiData = null;
			makerId = null;
			groupId = null;
			entityId = null;
			userDetails = null;
			userGrpDataMap = null;
		}

		return responseDTO;
	}
	public ResponseDTO insertMerchantIctAdminDetails(RequestDTO requestDTO) {
		logger.debug("Inside InsertMerchantIctAdminDetails... ");

		Connection connection = null;
		CallableStatement callableStatement = null;

		String insertBankInfoProc = "{call insertICTAdminProc(?,?,?,?,?,?,?)}";

		String multiData = "";
		String makerId = "";
		String groupId = "";
		String entityId = "";
		String userDetails = "";
		// String typeuser="";
		HashMap<String, Object> userGrpDataMap = null;
		try {
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			multiData = requestJSON.getString(CevaCommonConstants.MULTI_DATA);
			makerId = requestJSON.getString(CevaCommonConstants.MAKER_ID);
			groupId = requestJSON.getString("GROUP_ID");
			entityId = requestJSON.getString("ENTITY_ID");
			// typeuser=requestJSON.getString("typeuser");
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(insertBankInfoProc);
			callableStatement.setString(1, makerId);
			callableStatement.setString(2, multiData);
			callableStatement.setString(3, groupId);
			callableStatement.setString(4, entityId);
			callableStatement.registerOutParameter(5, Types.INTEGER);
			callableStatement.registerOutParameter(6, Types.VARCHAR);
			callableStatement.setString(7, requestJSON.getString(CevaCommonConstants.IP));
		//	callableStatement.setString(8, typeuser);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(5);
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				userGrpDataMap = new HashMap<String, Object>();
				responseJSON = new JSONObject();
				userDetails = callableStatement.getString(6);
				logger.debug("User Details  [" + userDetails + "]");
				responseJSON.put("USER_DETAILS", userDetails);
				userGrpDataMap.put("USER_DETAILS", responseJSON);
				responseDTO.setData(userGrpDataMap);

			} else if (resCnt == -1) {
				responseDTO = getAdminCraeteInfo(requestDTO);
				responseDTO.addError("ICT Amdin Already Exists. ");
			} else {
				responseDTO = getAdminCraeteInfo(requestDTO);
				responseDTO.addError("ICT Amdin Creation failed.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in insertIctAdminDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in insertIctAdminDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			insertBankInfoProc = null;
			multiData = null;
			makerId = null;
			groupId = null;
			entityId = null;
			userDetails = null;
			userGrpDataMap = null;
		}

		return responseDTO;
	}

	public ResponseDTO getICTAdminViewDetails(RequestDTO requestDTO) {

		logger.debug("Inside GetICTAdminViewDetails.. ");
		JSONObject locationJSON = null;

		HashMap<String, Object> USerIctAdminDataMap = null;
		Connection connection = null;
		PreparedStatement userIctAdmin = null;
		ResultSet userAdminIctRS = null;

		String ictAdminQry = "select A.LOGIN_USER_ID, B.EMP_ID, B.USER_NAME, B.USER_NAME, B.LOCAL_RES_NUM, B.LOCAL_OFF_NUM, "
				+ "B.MOBILE_NO, B.FAX, B.USER_LEVEL, B.LOCATION, B.EXPIRY_DATE, B.EMAIL "
				+ "from USER_LOGIN_CREDENTIALS A,USER_INFORMATION B "
				+ "WHERE A.COMMON_ID=B.COMMON_ID AND LOGIN_USER_ID=?";
		String userIctData = "";

		try {

			USerIctAdminDataMap = new HashMap<String, Object>();
			locationJSON = new JSONObject();

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			logger.debug("Connection is null [" + connection == null + "]");

			responseDTO = getAdminCraeteInfo(requestDTO);
			locationJSON = (JSONObject) responseDTO.getData().get(
					CevaCommonConstants.LOCATION_INFO);
			userIctAdmin = connection.prepareStatement(ictAdminQry);
			userIctAdmin.setString(1,
					requestJSON.getString(CevaCommonConstants.USER_ID));
			userAdminIctRS = userIctAdmin.executeQuery();
			while (userAdminIctRS.next()) {
				responseJSON.put(CevaCommonConstants.USER_ID,
						userAdminIctRS.getString(1));
				responseJSON.put(CevaCommonConstants.EMPLOYEE_NO,
						userAdminIctRS.getString(2));
				responseJSON.put(CevaCommonConstants.FIRST_NAME,
						userAdminIctRS.getString(3));
				responseJSON.put(CevaCommonConstants.LAST_NAME,
						userAdminIctRS.getString(4));
				responseJSON.put(CevaCommonConstants.RES_TELEPHONE,
						userAdminIctRS.getString(5));
				responseJSON.put(CevaCommonConstants.OFF_TELEPHONE,
						userAdminIctRS.getString(6));
				responseJSON.put(CevaCommonConstants.MOBILE,
						userAdminIctRS.getString(7));
				responseJSON.put(CevaCommonConstants.FAX,
						userAdminIctRS.getString(8));
				responseJSON.put(CevaCommonConstants.USER_LEVEL,
						userAdminIctRS.getString(9));
				responseJSON.put(CevaCommonConstants.OFFICE_LOCATON,
						userAdminIctRS.getString(10));
				responseJSON.put(CevaCommonConstants.EXPIRY_DATE,
						userAdminIctRS.getString(11));
				responseJSON.put(CevaCommonConstants.EMAIL,
						userAdminIctRS.getString(12));
			}

			responseJSON.put("UserIctAdminData", userIctData);

			logger.debug("Response JSON [" + responseJSON + "]");
			USerIctAdminDataMap.put(CevaCommonConstants.ICTADMIN_INFO,
					responseJSON);
			USerIctAdminDataMap.put(CevaCommonConstants.LOCATION_INFO,
					locationJSON);
			logger.debug("IctAdminDataMap [" + USerIctAdminDataMap + "]");
			responseDTO.setData(USerIctAdminDataMap);

		} catch (SQLException e) {
			logger.debug("SQLException in GetICTAdminViewDetails ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetICTAdminViewDetails  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(userIctAdmin);
			DBUtils.closeResultSet(userAdminIctRS);

			locationJSON = null;
			USerIctAdminDataMap = null;
			userIctData = null;
		}

		return responseDTO;
	}

	public ResponseDTO getUnAuthUsersList(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside GetUnAuthUsersList.. ");
		HashMap<String, Object> merchantMap = null;
		JSONObject resultJson = null;
		JSONArray userJSONArray = null;
		PreparedStatement userPstmt = null;
		ResultSet userRS = null;
		// String merchantid="";

		String merchantQry = "SELECT ULC.LOGIN_USER_ID ,UI.USER_NAME,NVL(UI.USER_GROUPS,'NOT ASSIGNED'),"
				+ "UI.EMAIL,UI.EMP_ID,decode(UI.USER_STATUS,'N','New User')  "
				+ "FROM  USER_INFORMATION_TEMP UI,USER_LOGIN_CREDENTIALS_TEMP ULC  "
				+ "WHERE UI.COMMON_ID=ULC.COMMON_ID AND UI.AUTH_FLAG IN ('MAP','AUP') and UI.USER_GROUPS=?";
		try {
			merchantMap = new HashMap<String, Object>();
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			responseJSON = new JSONObject();

			resultJson = new JSONObject();
			userJSONArray = new JSONArray();

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");

			userPstmt = connection.prepareStatement(merchantQry);
			userPstmt.setString(1, requestJSON.getString("GROUP_ID"));
			userRS = userPstmt.executeQuery();
			JSONObject json = null;

			while (userRS.next()) {
				json = new JSONObject();
				json.put("USER_ID", userRS.getString(1));
				json.put("USER_NAME", userRS.getString(2));
				json.put("ROLE_GRP_ID", userRS.getString(3));
				json.put("EMAIL", userRS.getString(4));
				json.put("EMPID", userRS.getString(5));
				json.put("USER_STATUS", userRS.getString(6));
				userJSONArray.add(json);
				json.clear();
				json = null;
			}
			resultJson.put("USER_LIST", userJSONArray);
			merchantMap.put("USER_LIST", resultJson);

			logger.debug("Entity Map [" + merchantMap + "]");
			responseDTO.setData(merchantMap);
			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			logger.debug("SQLException in GetUnAuthUsersList ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in GetUnAuthUsersList  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(userPstmt);
			DBUtils.closeResultSet(userRS);

			merchantMap = null;
			resultJson = null;
			userJSONArray = null;
		}

		return responseDTO;
	}

	public ResponseDTO userAuthorizationAck(RequestDTO requestDTO) {
		logger.debug("Inside UserAuthorizationAck... ");

		Connection connection = null;
		CallableStatement callableStatement = null;

		String usersString = "";
		String userMakerId = "";
		String errorMessage = "";

		String updateUserDet = "{call AUTHORIZEUSERS(?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			usersString = requestJSON.getString("USERS");
			userMakerId = requestJSON.getString("user_id");

			connection = DBConnector.getConnection();
			callableStatement = connection.prepareCall(updateUserDet);
			callableStatement.setString(1, userMakerId);
			callableStatement.setString(2, usersString);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();

			errorMessage = callableStatement.getString(3);
			logger.debug("errorMessage [" + errorMessage + "]");

			if (errorMessage.equalsIgnoreCase("SUCCESS")) {
				responseDTO.addMessages("User Authorization Acknowledged. ");
			} else {
				responseDTO
						.addError("User authorization got failed, please try again.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in UserAuthorizationAck ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UserAuthorizationAck  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			usersString = null;
			userMakerId = null;
			errorMessage = null;
			updateUserDet = null;
		}
		return responseDTO;
	}

	public ResponseDTO RejectuserAuthorizationAck(RequestDTO requestDTO) {
		logger.debug("Inside RejectuserAuthorizationAck... ");

		Connection connection = null;
		CallableStatement callableStatement = null;

		String usersString = "";
		String userMakerId = "";
		String errorMessage = "";

		String updateUserDet = "{call REJECTAUTHORIZEUSERS(?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");

			usersString = requestJSON.getString("USERS");
			userMakerId = requestJSON.getString("user_id");

			connection = DBConnector.getConnection();
			callableStatement = connection.prepareCall(updateUserDet);
			callableStatement.setString(1, userMakerId);
			callableStatement.setString(2, usersString);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();

			errorMessage = callableStatement.getString(3);
			logger.debug("errorMessage [" + errorMessage + "]");

			if (errorMessage.equalsIgnoreCase("SUCCESS")) {
				responseDTO.addMessages(" Reject User Authorization Acknowledged. ");
			} else {
				responseDTO.addError("User authorization got failed, please try again.");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in UserAuthorizationAck ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} catch (Exception e) {
			logger.debug("Exception in UserAuthorizationAck  ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			usersString = null;
			userMakerId = null;
			errorMessage = null;
			updateUserDet = null;
		}
		return responseDTO;
	}
	
	
		public ResponseDTO healthstatus(RequestDTO requestDTO) {
			logger.debug("Inside GetUsersToAssign... ");

			HashMap<String, Object> userGrpDataMap = null;
			JSONArray applTypeJSONArray = null;
			JSONObject resultJson = null;

			Connection connection = null;

			ResultSet usersRS = null;
			PreparedStatement detPstmt1 = null;
			PreparedStatement appDataPstmt = null;
			PreparedStatement roleGrpPstmt = null;
			ResultSet rs = null;
			ResultSet applDataRS = null;
			String appDataQry = null;
			StringBuilder billerData = null;
			StringBuilder eachrow = null;
			
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON [" + requestJSON + "]");
				resultJson = new JSONObject();

				userGrpDataMap = new HashMap<String, Object>();
				applTypeJSONArray = new JSONArray();

				connection = DBConnector.getConnection();

				appDataQry = "Select COMPONENT,ISEXTERNAL,TECHNIQUE,IP,PORT,FLOATBALANCE,URL,URLRESPONSE,TIMEUPDATED,STATUS from MOB_HEALTH_MONITOR";

				detPstmt1 = connection.prepareStatement(appDataQry);
				applDataRS = detPstmt1.executeQuery();
				JSONObject json = null;


				rs = detPstmt1.executeQuery();

				int i = 0;
				eachrow = new StringBuilder(50);
				billerData = new StringBuilder(50);

				while (rs.next()) {
					eachrow.append(rs.getString(1)).append(",")
							.append(rs.getString(2)).append(",")
							.append(rs.getString(3)).append(",")		//BRANCHCODE
							.append(rs.getString(4)).append(",")
							.append(rs.getString(5)).append(",")		//FULLNAME
							.append(rs.getString(6)).append(",")
							.append(rs.getString(7)).append(",")		//PRODUCT
							.append(rs.getString(8)).append(",")		//PRODUCT
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

				resultJson.put("multiData", billerData);

				logger.debug("BEAN DETAILS IN FETCHCUSTOMERACCOUNTDETAILS "+ billerData);
				userGrpDataMap.put("AccountData", resultJson);
				responseDTO.setData(userGrpDataMap);
				
				//billerData.delete(0, eachrow.length());

				

			} catch (SQLException e) {
				logger.debug("SQLException in GetUsersToAssign [" + e.getMessage()
						+ "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {
				logger.debug("Exception in GetUsersToAssign  [" + e.getMessage()
						+ "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} finally {
				DBUtils.closeConnection(connection);
				DBUtils.closePreparedStatement(detPstmt1);
				DBUtils.closePreparedStatement(appDataPstmt);
				DBUtils.closePreparedStatement(roleGrpPstmt);

				DBUtils.closeResultSet(applDataRS);
				DBUtils.closeResultSet(rs);
				DBUtils.closeResultSet(usersRS);

				userGrpDataMap = null;
				applTypeJSONArray = null;
				resultJson = null;
				appDataQry = null;

			}

			return responseDTO;
		}
	
	
}
