package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.bean.CevaCommonMenuBean;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.AesUtil;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.security.PasswordValidation;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.user.security.UserLocking;
import com.ceva.user.security.UserLockingClient;
import com.ceva.user.security.UserLockingProcess;
import com.ceva.util.CommonUtil;
import com.ceva.util.DBUtils;
import com.ceva.util.QueryactiveDirectory;

public class CommonLoginDAO {

	private Logger logger = Logger.getLogger(CommonLoginDAO.class);
	ResponseDTO responseDTO = null;
	// JSONObject resonseJSON;
	JSONObject requestJSON;
	JSONObject menuJSON = null;

	HashMap<String, Object> menuMap = null;

	private String userid;
	private String password;
	private String usertype;
	private String userGroup;
	private String applName;
	private String loginEntity;
	private String userLevel;
	private String location;
	private String loginTime;
	private String pidMenuQuery = null;
	private String randomVal = null;
	private String remoteIp = null;
	private String dbPassword = null;
	private String systemStatus = null;
	private String USLIDN = null;
	boolean isAuthUser = false;
	private String authRequired = null;
	private ResourceBundle rb = null;
	private String tokens = null;

	public ResponseDTO validatLogin(RequestDTO requestDTO) {

		logger.debug("Inside ValidateLogin... ");
		Connection connection = null;

		PreparedStatement prepareStmt = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;

		String userStatus = null;
		String lockTIme = null;
		String validateSystem = "";
		String validateAppl = "";
		String userSystemStatusQry = "";
		String token = "";
		String authValue = "";
		String userdetails = "";
		String updateStatus = "";
		String userData = "";
 		String validatePwdExpQry = "";
		String passQry = "";
		String userLockProc = "";
		String userGrpQry = "";
		String getHiddenPassword = "";
		String redirectLogin="";
		String validateQry="";
		String userLevelname="";
		String salt="";
		String iv="";
		String passkey="";
		String errormessage="";

		int wrongPwdCnt = 0;
		int status = 0;
		int applCnt = 0;
		int sysCnt = 0;

		QueryactiveDirectory qrd = null;

		rb = ResourceBundle.getBundle("pathinfo_config");
		try {
			authRequired = rb.getString("AUTH_REQUIRED");
		} catch (Exception e) {
			logger.debug("AuthRequired not configured in property file.. By default setting to 'NO'");
			authRequired = "NO";
		}
		logger.debug("AuthRequired [" + authRequired + "]");

		try {

			menuJSON = new JSONObject();
			responseDTO = new ResponseDTO();

			requestJSON = requestDTO.getRequestJSON();
			//logger.debug("Request JSON [" + requestJSON + "]");

			userid = requestJSON.getString(CevaCommonConstants.USER_ID);
			password = requestJSON.getString(CevaCommonConstants.PASSWORD);
			tokens = requestJSON.getString("TOKENS");
			applName = requestJSON.getString(CevaCommonConstants.APPL_NAME);
			randomVal = requestJSON.getString(CevaCommonConstants.RANDOM_VAL);
			salt = requestJSON.getString("SALT");
			iv = requestJSON.getString("IV");
			passkey = requestJSON.getString("PASSWORDKEY");
			remoteIp = requestJSON.getString("REMOTE_IP");
			
			
			getHiddenPassword = requestJSON.getString("getHiddenPassword");
			password = getHiddenPassword;
			if (userid == null) {
				responseDTO.addError("Invalid User Id");
			} else if (password == null) {
				responseDTO.addError("Invalid Password");
			} else if (tokens == null) {
				responseDTO.addError("Invalid Token");
			}  else if (authRequired == null) {
				responseDTO.addError("User Authentication not configured");
			} else {

				connection = DBConnector.getConnection();
				//logger.debug("Connection is null [" + connection == null + "]");

				connection.setAutoCommit(false);

				validateAppl = "select count(*) "
						+ "from USER_LOGIN_CREDENTIALS "
						+ "where LOGIN_USER_ID=? and APPL_CODE=?";

				prepareStmt = connection.prepareStatement(validateAppl);
				prepareStmt.setString(1, userid);
				prepareStmt.setString(2, applName);
				rs = prepareStmt.executeQuery();

				if (rs.next()) {
					applCnt = rs.getInt(1);
				}
				logger.debug("User Login token [" + tokens + "]");
				logger.debug("User Login Count Check [" + applCnt + "]");
				prepareStmt.close();
				rs.close();

				if (applCnt == 1) {
					if (authRequired.equals("YES")) {
						validateSystem = "Select count(*) "
								+ "from USER_LOCKING_INFO where USER_ID=?";
						prepareStmt = connection
								.prepareStatement(validateSystem);
						prepareStmt.setString(1, userid);
						rs = prepareStmt.executeQuery();
						if (rs.next()) {
							sysCnt = rs.getInt(1);
						}

						prepareStmt.close();
						rs.close();

						logger.debug("SysCnt for USER_LOCKING_INFO is ["
								+ sysCnt + "]");
						if (sysCnt == 1) {
							userSystemStatusQry = "Select STATUS,ULSID,USER_DATA from USER_LOCKING_INFO where USER_ID=?";
							prepareStmt = connection
									.prepareStatement(userSystemStatusQry);
							prepareStmt.setString(1, userid);
							rs = prepareStmt.executeQuery();

							if (rs.next()) {
								systemStatus = rs.getString(1);
								USLIDN = rs.getString(2);
								userData = rs.getString(3);
							}

							logger.debug("SystemStatus [" + systemStatus
									+ "] USLIDN [" + USLIDN + "]");
							token = UserLocking.getRandomValue();

							if (systemStatus.equals("W")) {
								authValue = UserLockingProcess.regiterReq(
										USLIDN, userid, token);
								logger.debug("authValue - " + authValue);

								try {
									userdetails = UserLockingClient
											.callLockingServer(remoteIp, 7777,
													authValue);
									logger.debug("userdetails - " + userdetails);
									isAuthUser = UserLockingProcess
											.processRegistration(userid,
													userdetails, token, USLIDN);

								} catch (Exception e) {
									logger.debug("Handled Exception while fetching user-data ::: "
											+ e.getMessage());
								}
								// if(userdetails.equalsIgnoreCase("NOVAL"))

								logger.debug("Is data valid Data in W State  - "
										+ isAuthUser);
								prepareStmt.close();
								rs.close();

								if (isAuthUser) {
									updateStatus = "update USER_LOCKING_INFO set STATUS=? where USER_ID=?";
									prepareStmt = connection
											.prepareStatement(updateStatus);
									prepareStmt.setString(1, "R");
									prepareStmt.setString(2, userid);
									int updCnt = prepareStmt.executeUpdate();
									if (updCnt == 1)
										logger.debug("Status Updated to R");

									prepareStmt.close();
								}
							}

							if (systemStatus.equals("R")) {
								authValue = userData;
								logger.debug("R authValue - " + authValue);
								userData = UserLockingProcess.validateReq(
										userid, authValue, token);
								logger.debug("R userData - " + userData);
								userdetails = UserLockingClient
										.callLockingServer(remoteIp, 7777,
												userData);
								logger.debug("R userdetails - " + userdetails);
								isAuthUser = UserLockingProcess.isValidUser(
										userid, userdetails, token, authValue);
								logger.debug("R isAuthUser - " + isAuthUser);
							}

							if (isAuthUser) {
								logger.debug("User Authentication Success");
							} else {
								responseDTO
										.addError("Locking System Authetication failed.");
							}
						} else {
							responseDTO
									.addError("Please Register for User Locking System.");
						}
					}
					prepareStmt.close();
					rs.close();
					// common part for auth and non-auth
					if (isAuthUser || authRequired.equals("NO")) {
						try {
							boolean flagUser = false;
							boolean tokenflag = false;
							//System.out.println("TESTING IN AUTH NO");
							//comment if we need to use in local
						
						try {

								/*userid = requestJSON.getString(CevaCommonConstants.USER_ID);
								password = requestJSON.getString(CevaCommonConstants.PASSWORD);
								logger.debug("Extension from RB +["+  rb.getString("ldap.ext")+"]   ["+rb.getString("ldap.url")+"]");
								System.out.println("Extension from RB +["+  rb.getString("ldap.ext")+"]   ["+rb.getString("ldap.url")+"]");

								qrd = new QueryactiveDirectory(userid+ rb.getString("ldap.ext"), password,rb.getString("ldap.url"));*/
							
							JSONObject jsonaut = JSONObject.fromObject(ServiceRequestClient.ldap(requestJSON.getString(CevaCommonConstants.USER_ID),AesUtil.decrypt(password, passkey, salt, iv),salt));	
							if((jsonaut.getString("respdesc")).equalsIgnoreCase("SUCCESS")){
									flagUser=true;
								}else{
									flagUser=false;
									errormessage=jsonaut.getString("respdesc");
								}
							//tokenflag=true;
							JSONObject jsontoken = JSONObject.fromObject(ServiceRequestClient.tokenVal(requestJSON.getString(CevaCommonConstants.USER_ID),tokens));	
							if((jsontoken.getString("respcode")).equalsIgnoreCase("00")){
									tokenflag=true;
								}else{
									tokenflag=false;
									errormessage=jsontoken.getString("respdesc");
								}
							
								//qrd.closeDirectoryContext();
							} catch (Exception e) {
								logger.debug("Exception while fetching records from AD ["
										+ e.getMessage() + "]");
								e.printStackTrace();
							} finally {

							} 
							
						//flagUser=true;
							//uncomment this
							//flagUser = true;
							logger.debug("Token  Authentication is [" + tokenflag+ "]+");
							logger.debug("Token  Authentication Message [" + errormessage+ "]+");
							/********************  username and token ******************/
							
							
							if (!tokenflag) {

								validateQry = "select count(*) from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID=? AND TOKEN=?";
								prepareStmt = connection
										.prepareStatement(validateQry);
								prepareStmt.setString(1, userid);
								prepareStmt.setString(2, CommonUtil.b64_sha256(tokens));
								rs = prepareStmt.executeQuery();
								if (rs.next()) {
									if(rs.getInt(1)!=0){
									tokenflag=true;
									}
								}

							
								prepareStmt.close();
								rs.close();
							}
							logger.debug("Token  Authentication is [" + tokenflag+ "]");
							logger.debug("AD  Authentication is [" + flagUser+ "]");

							// If AD Check False Then querying DB
							if (!flagUser) {

								validateQry = "select PASSWORD from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID=?";
								prepareStmt = connection
										.prepareStatement(validateQry);
								prepareStmt.setString(1, userid);
								rs = prepareStmt.executeQuery();
								if (rs.next()) {
									dbPassword = rs.getString(1);
								}

							
								prepareStmt.close();
								rs.close();
							}
							
							
							/********************  username and token end ******************/
							//System.out.println(" Password1 ::: "+dbPassword);
							//System.out.println(" Password2 ::: "+CommonUtil.b64_sha256(AesUtil.decrypt(password, passkey, salt, iv)));
							// If Password equals or AD Authorization
							if (!tokenflag) {
								responseDTO.addError(errormessage);
							}else{
							if (flagUser || dbPassword.equals(CommonUtil.b64_sha256(AesUtil.decrypt(password, passkey, salt, iv)))) {
								//if (flagUser) {
							
								// if (false) {
								userGrpQry = "select USER_GROUPS,USER_STATUS,ENTITY,USER_LEVEL,UI.LOCATION,"
										+ "to_char(sysdate,'DD-MM-YYYY HH24:MI:SS'),to_char(USER_LOCK_TIME,'DD-MM-YYYY HH24:MI:SS'),USER_TYPE,(select STATUS from config_data where KEY_VALUE=USER_TYPE) "
										+ "from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC "
										+ "where UI.COMMON_ID=ULC.COMMON_ID and ULC.LOGIN_USER_ID=?";
								prepareStmt = connection
										.prepareStatement(userGrpQry);
								prepareStmt.setString(1, userid);
								rs = prepareStmt.executeQuery();

								if (rs.next()) {
									userGroup = rs.getString(1);
									userStatus = rs.getString(2);
									loginEntity = rs.getString(3);
									userLevel = rs.getString(4);
									location = (rs.getString(5)).split("-")[1];
									loginTime = rs.getString(6);
									lockTIme = rs.getString(7);
									usertype = rs.getString(8);
									userLevelname = rs.getString(9);
								}

								prepareStmt.close();
								rs.close();

								logger.debug("lockTIme [" + lockTIme
										+ "] User Group [" + userGroup
										+ "] LoginEntity[" + loginEntity + "]");
								if (userGroup == null) {
									responseDTO.addError("Rights not assigned");
								} else {

									// code added for validating merchant type
									// Users & Merchant Admin's
									passQry = "select count(GROUP_TYPE) from user_groups "
											+ "where GROUP_ID=? and GROUP_TYPE='BANKGRP' ";

									prepareStmt = connection
											.prepareStatement(passQry);
									prepareStmt.setString(1, userGroup);
									rs = prepareStmt.executeQuery();

									int merchantTypeCnt = 0;
									if (rs.next()) {
										merchantTypeCnt = rs.getInt(1);
									}

									prepareStmt.close();
									rs.close();

									if (merchantTypeCnt == 0) {
										responseDTO
												.addError("User doesn't belongs to the 'Bank Portal' ");
									} else {

										menuMap = new HashMap<String, Object>();
										if (lockTIme != null
												&& userStatus.equals("L")) {

											status = PasswordActivate(userid,
													userStatus, "VALID");
											logger.debug("Status from PasswordActivate ["
													+ status + "]"+userStatus);

											userStatus = (status == 1 ? userStatus = "A"
													: userStatus);
										}
										
										if (userStatus.equals("A")) {
											/*int PwdExpCnt = 0;
											validatePwdExpQry = "select to_number(abs(LAST_PASSWD_CHANGE - sysdate)) from "
													+ "USER_LOGIN_CREDENTIALS "
													+ "where LOGIN_USER_ID=?";
											prepareStmt = connection
													.prepareStatement(validatePwdExpQry);
											prepareStmt.setString(1, userid);
											rs = prepareStmt.executeQuery();

											if (rs.next()) {
												PwdExpCnt = rs.getInt(1);
											}

											prepareStmt.close();
											rs.close();

											logger.debug("PwdExpCnt ["
													+ PwdExpCnt + "]");

											if (PwdExpCnt > 90) {
												userStatus = "F";
												menuMap.put("userStatus",
														userStatus);
											} else {*/
												logger.debug("Inside Else For Getting Menus..");
												menuJSON = getMenuData();
												menuMap.put(CevaCommonConstants.MENU_DATA,menuJSON);
												menuMap.put("userid", userid);
												menuMap.put("userStatus",userStatus);
												menuMap.put("LoginEntity",loginEntity);
												menuMap.put("UserLevel",userLevel);
												menuMap.put("Location",location);
												menuMap.put("links_pid_query",pidMenuQuery);
												menuMap.put("LoginTime",loginTime);
												menuMap.put("userGroup",userGroup);
												menuMap.put("usertype",usertype);
												menuMap.put("userLevelname",userLevelname);
												
												PasswordActivate(userid,userStatus, "UPDATECNT");
												String lastLogin = updateLastLogin(userid);
												menuMap.put("lastLogin",lastLogin);
												
												System.out.println("MENU MAP =============>"+menuMap);

											//}
										} else if (userStatus.equals("F")) {
											menuMap.put("userStatus",
													userStatus);
										} else if (userStatus.equals("N")) {
											responseDTO
													.addError("User authorization not done, please check with administrator.");
										} else if (userStatus.equals("L")) {
											if (status == -2) {
												responseDTO
														.addError("Sorry, there have been more than 3 failed login attempts for this user id. It is temporarily blocked.Try again after 5 min");
											} else {
												responseDTO
														.addError("User Got Deactivated, Please activate in order to process the login.");
											}
										} else {
											responseDTO
													.addError("User Got Deactivated, Please activate in order to process the login.");
										}
										responseDTO.setData(menuMap);
									}
								}

							} else {

								passQry = "select ULC.USER_LOCK_TIME,UI.USER_STATUS "
										+ "from USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI "
										+ "where UI.COMMON_ID=ULC.COMMON_ID and ULC.LOGIN_USER_ID=?";
								prepareStmt = connection
										.prepareStatement(passQry);
								prepareStmt.setString(1, userid);
								rs = prepareStmt.executeQuery();

								if (rs.next()) {
									lockTIme = rs.getString(1);
									userStatus = rs.getString(2);
								}

								prepareStmt.close();
								rs.close();

								logger.debug("LockTime [" + lockTIme
										+ "] UserStatus [" + userStatus + "]");

								if (userStatus.equals("L")) {
									status = PasswordActivate(userid,
											userStatus, "INVALID");
									logger.debug("The User Status is ["
											+ status + "]");
									if (status == -2) {
										responseDTO
												.addError("User Account Blocked. Please activate in order to process the login.");
									}
									
									if (status == -1) {
										responseDTO
												.addError("Invalid User Id / Password.");
									}
								} else {
									logger.debug("Branch User>>>>>login2");
									userLockProc = "{call UserLockingProc(?,?,?)}";
									callableStatement = connection
											.prepareCall(userLockProc);
									callableStatement.setString(1, userid);
									callableStatement.registerOutParameter(2,
											java.sql.Types.INTEGER);
									callableStatement.setString(3, requestJSON
											.getString(CevaCommonConstants.IP));
									callableStatement.executeUpdate();
									wrongPwdCnt = callableStatement.getInt(2);
									logger.debug("Invalid Password Count wrongPwdCnt ["
											+ wrongPwdCnt + "]");
									if (wrongPwdCnt >= 6) {
										logger.debug("DbPassword ["
												+ dbPassword + "]");
										responseDTO
												.addError("User Account Blocked. Please activate in order to process the login.");
									} else {
										logger.debug("DbPassword ["
												+ dbPassword + "]");
										responseDTO
												.addError("Invalid User Id / Password.");
									}
								}
							
						}
						}
						} catch (Exception e) {
							responseDTO
									.addError("Internal Error Occured At Login.Please re-check and try again.");
						}
						
					} else {
						responseDTO
								.addError("User authentication not configured.");
					}

				} else {

					logger.debug("Connection is null [" + connection == null
							+ "]");

					connection.setAutoCommit(false);

					passQry = "insert into AUDIT_TRAIL(TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID,DATA_1,TRANS_CODE_DESC,MAKER_ID,DATA_2)"
							+ " values (?,?,?,?,?,?,?,?)";

					prepareStmt = connection.prepareStatement(passQry);
					prepareStmt.setString(1, "loginFaild");
					prepareStmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
					prepareStmt.setString(3, requestJSON.getString("userId"));
					prepareStmt.setString(4, "WEB");
					prepareStmt.setString(5,requestJSON.getString(CevaCommonConstants.IP));
					prepareStmt.setString(6, "Login Failed");
					prepareStmt.setString(7, requestJSON.getString("userId"));
					// pstmt.setString(8, UserLocking.getSystemMac());
					prepareStmt.setString(8, " ");
					int count2 = prepareStmt.executeUpdate();

					logger.debug("Getting the count after insertion to "
							+ "USER_GROUPS is [" + count2 + "] .");

					connection.commit();
					logger.debug("Audit insertion Success");

					responseDTO
							.addError("User doesn't belongs to 'Mobile Banking', please re-check and try again.");
				}
			}
		} catch (SQLException e2) {
			responseDTO.addError("Unable to connect to DB.");
			logger.debug("DB connection is null, please check the network configurations. Exception is ["
					+ e2.getMessage() + "]");
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Internal Error Occured. the exception is ["
					+ e.getMessage() + "]");
			responseDTO
					.addError("Internal Error Occured.Please re-check and try again.");
			e.printStackTrace();
		} finally {

			DBUtils.closePreparedStatement(prepareStmt);
			DBUtils.closeResultSet(rs);
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(connection);

			menuJSON = null;
			userStatus = null;
			lockTIme = null;
			validateSystem = null;
			validateAppl = null;
			userStatus = null;
			lockTIme = null;
			validateSystem = null;
			validateAppl = null;
			userSystemStatusQry = null;
			token = null;
			authValue = null;
			userdetails = null;
			updateStatus = null;
			userData = null;
 			validatePwdExpQry = null;
			passQry = null;
			userLockProc = null;

		}
		return responseDTO;
	}

	public static int PasswordActivate(String userId, String userStatus,
			String flag) {
		Connection conn = null;
		CallableStatement callableStatement = null;
		int status = 0;

		try {
			conn = DBConnector.getConnection();

			String userLockProc = "{call UserLockingCheckingProc(?,?,?,?)}";
			callableStatement = conn.prepareCall(userLockProc);
			callableStatement.setString(1, userId);
			callableStatement.setString(2, userStatus);
			callableStatement.setString(3, flag);
			callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);
			callableStatement.executeUpdate();
			status = callableStatement.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn);
			DBUtils.closeCallableStatement(callableStatement);
		}
		return status;
	}

	public JSONObject getMenuData() throws Exception {
		JSONObject jres = null;
		HashMap<String, Object> map = null;
		List<CevaCommonMenuBean> menuAl = null;
		Connection connection = null;
		CevaCommonMenuBean menuBean = null;
		String menuQry = null;

		int count = 0;
		String toPrepStmt = null;

		PreparedStatement menuStmt = null;
		ResultSet menuRS = null;

		try {
			jres = new JSONObject();
			map = new HashMap<String, Object>();
			menuAl = new ArrayList<CevaCommonMenuBean>();
			connection = DBConnector.getConnection();
			logger.debug("UserGroup [" + userGroup + "] ApplName[" + applName
					+ "] Userid [" + userid + "]");

			menuQry = "select count(*) from user_linked_action where upper(user_id)=?";

			menuStmt = connection.prepareStatement(menuQry);
			menuStmt.setString(1, userid.toUpperCase());
			menuRS = menuStmt.executeQuery();

			if (menuRS.next()) {
				count = menuRS.getInt(1);
			}

			logger.debug("The Query count is [" + count + "]");

			if (count == 0) {
				menuQry = "Select distinct ULA.ID,ULA.NAME,ULA.PID,(select action from USER_ACTION_LINKS where UPPER(NAME)=UPPER(ULA.NAME) ),'Y','"
						+ applName
						+ "',ULA.title "
						+ "from USER_LINKED_ACTION ULA  where UPPER(ULA.GROUP_ID)=?  and  ULA.ID in (select distinct id from USER_ACTION_LINKS where alias_name is null)   "
						+ "and ULA.USER_ID is null order by to_number(ULA.ID) ";
				toPrepStmt = userGroup.toUpperCase();
			} else {
				menuQry = "Select distinct ULA.ID,ULA.NAME,ULA.PID,"
						+ "(select action from USER_ACTION_LINKS where UPPER(NAME)=UPPER(ULA.NAME) ),'Y','"
						+ applName
						+ "',ULA.title "
						+ "from USER_LINKED_ACTION ULA  where UPPER(ULA.USER_ID)=?  and  "
						+ "ULA.ID in (select distinct id from USER_ACTION_LINKS where alias_name is null  ) "
						+ " order by to_number(ULA.ID) ";
				toPrepStmt = userid.toUpperCase();
			}

			logger.debug("After setting the query is  [" + menuQry + "] ");

			menuStmt.close();
			menuRS.close();

			menuStmt = null;
			menuRS = null;

			menuStmt = connection.prepareStatement(menuQry);
			menuStmt.setString(1, toPrepStmt);

			menuRS = menuStmt.executeQuery();
			logger.debug("After getting data from result set [" + menuRS + "]");

			while (menuRS.next()) {
				menuBean = new CevaCommonMenuBean();
				menuBean.setId(menuRS.getInt(1));
				menuBean.setMenuName(menuRS.getString(2));
				menuBean.setParentMenu(menuRS.getString(3));
				menuBean.setMenuaction(menuRS.getString(4));
				menuBean.setActionstatus(menuRS.getString(5));
				menuBean.setApplName(menuRS.getString(6));
				menuBean.setTitle(menuRS.getString(7));
				menuAl.add(menuBean);

				menuBean = null;
			}
			map.put("menudata", menuAl);

			menuQry = "select count(*) from user_linked_action where upper(user_id)=?";

			menuStmt.close();
			menuRS.close();

			menuStmt = null;
			menuRS = null;

			menuStmt = connection.prepareStatement(menuQry);
			menuStmt.setString(1, userid.toUpperCase());
			menuRS = menuStmt.executeQuery();

			if (menuRS.next()) {
				count = menuRS.getInt(1);
			}

			logger.debug("Count for checking links [" + count + "]");

			if (count > 0) {
				pidMenuQuery = "select (select alias_name from USER_ACTION_LINKS where name=ula.NAME ),ula.CHECKED "
						+ "from user_linked_action ula "
						+ "where upper(ula.pid)=? and upper(ula.user_id)=?";
			} else {
				pidMenuQuery = "select (select alias_name from USER_ACTION_LINKS where name=ula.NAME ),ula.CHECKED,ula.NAME,ula.pid from user_linked_action ula  "
						+ "where ula.pid in (?) and upper(ula.group_id) in (select upper(user_groups) from  user_information where "
						+ "common_id in (select common_id from user_login_credentials where  upper(login_user_id)= ?)) and ula.user_id is null order by id";

			}

			logger.debug("Inside  finally map [ " + map + "]");

			jres.putAll(map);
		} catch (Exception e) {
			logger.debug("GetMenuData got Exception [" + e.getMessage() + "]");
			jres.putAll(new HashMap<String, List<CevaCommonMenuBean>>());
		}

		finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(menuStmt);
			DBUtils.closeResultSet(menuRS);
			map = null;
			menuAl = null;
			menuBean = null;
			menuQry = null;
			toPrepStmt = null;
		}
		logger.debug("Result MenuJSON object from JRES[" + jres + "]");
		return jres;
	}

	public ResponseDTO changePassword(RequestDTO requestDTO) {

		HashMap<String, String> passwordMap = null;

		String userId = null;
		String password = null;
		String applName = null;

		String oldPasswordCheckQry = null;
		String prevPassword = null;
		String oldPasswordVal = null;

		CallableStatement callableStatement = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String repeatPwdStatus = null;

		String resetPwdProc = "{call changePasswordProc(?,?,?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();

			userId = requestJSON.getString(CevaCommonConstants.USER_ID);
			password = requestJSON.getString(CevaCommonConstants.PASSWORD);
			applName = requestJSON.getString(CevaCommonConstants.APPL_NAME);

			oldPasswordCheckQry = "SELECT PASSWORD,OLD_PASSWORDS from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID=?";

			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");

			pstmt = connection.prepareStatement(oldPasswordCheckQry);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				prevPassword = rs.getString(1);
				oldPasswordVal = rs.getString(2);
			}

			logger.debug("oldPasswordVal [" + oldPasswordVal
					+ "]  PrevPassword [" + prevPassword + "]");

			passwordMap = (HashMap<String, String>) PasswordValidation
					.maxCheck(password, prevPassword, oldPasswordVal);
			repeatPwdStatus = passwordMap.get("RESPCODE");
			logger.debug("RepeatPwdStatus [" + repeatPwdStatus + "]");

			if (repeatPwdStatus.equals("00")) {
				oldPasswordVal = passwordMap.get("OLDPWDS");
				callableStatement = connection.prepareCall(resetPwdProc);
				callableStatement.setString(1, userId);
				callableStatement.setString(2, password);
				callableStatement.setString(3, applName);
				callableStatement.setString(4, prevPassword);
				callableStatement.setString(5, oldPasswordVal);
				callableStatement.registerOutParameter(6,
						java.sql.Types.INTEGER);
				callableStatement.executeUpdate();
				int resCnt = callableStatement.getInt(6);
				if (resCnt == 1) {
					responseDTO
							.addMessages("Password Changed Successfully Completed. ");
				} else if (resCnt == -1) {
					responseDTO
							.addError("User Id Doen't Belongs to Current Admin. ");
				} else {
					responseDTO
							.addError("There is an issue in Reset Password ");
				}
			} else {
				responseDTO
						.addError("New Password should not be equal to one of the last 5 passwords. ");
			}

		} catch (SQLException e) {
			logger.debug("SQLException in changePassword [" + e.getMessage()
					+ "]");
			responseDTO
					.addError("Internal Error Occured At Change Password.Please re-check and try again.");
		} catch (Exception e) {
			logger.debug("Exception in changePassword  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closePreparedStatement(pstmt);

			passwordMap = null;

			userId = null;
			password = null;
			applName = null;

			oldPasswordCheckQry = null;
			prevPassword = null;
			oldPasswordVal = null;

			repeatPwdStatus = null;

			resetPwdProc = null;
		}

		return responseDTO;
	}

	private String updateLastLogin(String userid) {
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		String lastLogin = null;
		try {
			lastLogin = "SELECT NVL(to_char(LAST_LOGED_IN , 'DD-MM-YYYY HH:MI'),' ') FROM USER_LOGIN_CREDENTIALS WHERE LOGIN_USER_ID=?";
			if (con == null)
				con = DBConnector.getConnection();
			pstmt = con.prepareStatement(lastLogin);
			pstmt = con.prepareStatement(lastLogin);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next())
				lastLogin = rs.getString(1);
			pstmt.close();
			pstmt = con
					.prepareStatement("UPDATE USER_LOGIN_CREDENTIALS SET LAST_LOGED_IN=? WHERE LOGIN_USER_ID=?");
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, userid);
			pstmt.executeUpdate();
			pstmt.close();
			con.commit();
		} catch (Exception ex) {
			logger.debug("error while updating..:" + ex.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(con);
		}
		return lastLogin;
	}

	public void InsertAuditLog(JSONObject object) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String insQry = "insert into AUDIT_TRAIL(TRANS_CODE,DATETIME,NET_ID,CHANNEL_ID,DATA_1,TRANS_CODE_DESC,MAKER_ID,DATA_2)"
				+ " values (?,?,?,?,?,?,?,?)";
		try {
			logger.debug("Maker ID:" + object.getString("userid"));
			if (connection == null)
				connection = DBConnector.getConnection();
			pstmt = connection.prepareStatement(insQry);
			pstmt.setString(1, object.getString("trancode"));
			pstmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			pstmt.setString(3, object.getString("userid"));
			pstmt.setString(4, "WEB");
			pstmt.setString(5, object.getString("ip"));
			pstmt.setString(6, object.getString("action"));
			pstmt.setString(7, object.getString("userid"));
			// pstmt.setString(8, UserLocking.getSystemMac());
			pstmt.setString(8, " ");
			int i = pstmt.executeUpdate();
			connection.commit();
			logger.debug("Audit insertion Success with " + i + " insertion.");
		} catch (Exception ex) {
			logger.debug("Error Occured..!" + ex.getLocalizedMessage());
		} finally {
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}

	}
	
	public HashMap<String,String> getUserDetails(String userId) {

		logger.debug("Inside getUserDetails... "+userId);
		Connection connection = null;
		PreparedStatement pstmtdayseven = null;
		ResultSet rsdayseven = null;
		String[] name;
		HashMap<String,String> hm=new HashMap<String,String>();
		try{
			connection = DBConnector.getConnection();
			String dayseven1= "select user_name ,emp_id ,to_char(EXPIRY_DATE,'DD/MM/YYYY'),"
					+ "user_level,LOCATION,"
					+ "email,LOCAL_RES_NUM,LOCAL_OFF_NUM,mobile_no,fax,"
					+ "decode(USER_STATUS,'A','Active','L','De-Active','F','InActive',USER_STATUS),"
					+ "user_groups, (select ug.group_name from user_groups ug where ug.group_id=user_groups)"
					+ "from user_information where common_id = (select common_id from user_login_credentials where login_user_id=?)";
		
			pstmtdayseven = connection.prepareStatement(dayseven1);
			pstmtdayseven.setString(1, userId);
			rsdayseven = pstmtdayseven.executeQuery();
			while(rsdayseven.next()){
				name = rsdayseven.getString(1).split("\\ ");
				hm.put("CV0001", userId);
				hm.put("CV0003", name[0]);
				hm.put("CV0004", name[1]);
				hm.put("CV0002", rsdayseven.getString(2));
				hm.put("CV0011", rsdayseven.getString(3));
				hm.put("CV0009", rsdayseven.getString(4));
				hm.put("CV0010", rsdayseven.getString(5));
				hm.put("CV0012", rsdayseven.getString(6));
				hm.put("CV0005", rsdayseven.getString(7));
				hm.put("CV0006", rsdayseven.getString(8));
				hm.put("CV0007", rsdayseven.getString(9));
				hm.put("CV0008", rsdayseven.getString(10));
				hm.put("CV0013", rsdayseven.getString(11));
				hm.put("CV0014", rsdayseven.getString(12));
				hm.put("CV0015", rsdayseven.getString(13));
				}
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closePreparedStatement(pstmtdayseven);
			DBUtils.closeResultSet(rsdayseven);
			DBUtils.closeConnection(connection);
		}
		


		return hm;
	}
	
	
}
