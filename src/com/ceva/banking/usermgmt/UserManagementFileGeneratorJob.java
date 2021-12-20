package com.ceva.banking.usermgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.EncryptTransactionPin;
import com.ceva.util.DBUtils;
import com.ceva.util.TerminalFileData;

public class UserManagementFileGeneratorJob {
	protected static Logger logger = Logger
			.getLogger(UserManagementFileGeneratorJob.class);
	static String key = "97206B46CE46376894703ECE161F31F2";
	public static String generateCsvFile() {
		logger.debug("------- UserManagementFileGenerator Starts-------");
		String fileName = "";
		String eachFileData = null;
		// FileWriter writer = null;
		String merchantId = null;
		String sid = null;
		String terminalid = null;
		String serialNo = null;
		String generatedFileName = null;
		String sourcepath = null;
		String fileQry = "";
		String Username = null;
		String password = null;
		String admin = null;
		String supervisor = null;
		// String cryptedPassword = null;
		//String key = "97206B46CE46376894703ECE161F31F2";

		String tmknum = null;
		String eachData = "";
		String tmkinfo = "";

		ResourceBundle bundle = null;

		Connection connection = null;

		PreparedStatement terminalPstmt = null;
		PreparedStatement filePstmt = null;
		PreparedStatement tmkpsmt = null;
		PreparedStatement updatePstmt = null;
		PreparedStatement filePstmtservice = null;
		ResultSet fileRSservice = null;

		ResultSet terminalRS = null;
		ResultSet fileRS = null;
		ResultSet tmkRS = null;

		// File file = null;
		HashMap<String, String> users = null;

		String terminalInfo = null;
		String updateQry = "";
		StringBuffer location = null;
		String ServiceQry="";
		String serviceString="";

		try {
			logger.debug("Inside try block.");

			bundle = ResourceBundle.getBundle("pathinfo_config");

			sourcepath = bundle.getString("USER_FILE_PATH");

			logger.debug("sourcepath[" + sourcepath + "]");

			terminalInfo = "select mid,sid,tid,serial,admpwd,suppwd,makerid from ( "
					+ "select MERCHANT_ID mid,STORE_ID sid,TERMINAL_ID tid,SERIAL_NO serial,ADMIN_PWD admpwd, SUPERVISOR_PWD suppwd,"
					+ "MAKER_ID makerid from USER_TERMINAL_MAPPING UTM where ASSIGN_FALG= ?) "
					+ "group by mid,sid,tid,serial,admpwd,suppwd,makerid having (COUNT(tid)>0)";
			try {

				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");
				terminalPstmt = connection.prepareStatement(terminalInfo);
				terminalPstmt.setString(1, "I");
				terminalRS = terminalPstmt.executeQuery();

				if (terminalRS.next()) {
					merchantId = terminalRS.getString(1);
					sid = terminalRS.getString(2);
					terminalid = terminalRS.getString(3);
					serialNo = terminalRS.getString(4);
					admin = terminalRS.getString(5);
					//admin = "81A7A7488F6E901D";
					supervisor = terminalRS.getString(6);

					eachData = admin + supervisor;

					logger.debug("MerchantId[" + merchantId + "]" + " sid["
							+ sid + "]" + " terminalid[" + terminalid
							+ "]  serialNo[" + serialNo + "] admin[" + admin
							+ "]  supervisor[" + supervisor + "]");

					logger.debug("EachData [" + eachData + "]");

					eachFileData = "";
					fileQry = "select ULC.LOGIN_USER_ID,ULC.PIN from USER_LOGIN_CREDENTIALS_TEMP ULC,"
							+ "USER_TERMINAL_MAPPING UTM where ULC.LOGIN_USER_ID=UTM.USER_ID and"
							+ " UTM.MERCHANT_ID=? and UTM.STORE_ID=? and UTM.TERMINAL_ID=? and ASSIGN_FALG=? ";

					generatedFileName = sourcepath + serialNo + ".txt";

					logger.debug("generatedFileName[" + generatedFileName + "]");

					fileName = serialNo + ".txt";
					logger.debug("|generateCsvFile| fileName[" + fileName + "]");

					// file = new File(generatedFileName);
					users = new HashMap<String, String>();
					// writer = new FileWriter(file);

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, merchantId);
					filePstmt.setString(2, sid);
					filePstmt.setString(3, terminalid);
					filePstmt.setString(4, "I");

					fileRS = filePstmt.executeQuery();

					while (fileRS.next()) {
						Username = fileRS.getString(1);
						password = fileRS.getString(2);
						users.put(Username, password);

						logger.debug("Username[" + Username + "] Password["	+ password + "]");
					}

					logger.debug("serialNo [" + serialNo + "]");

					tmkinfo = "select TMK FROM TERMINAL_MASTER where MERCHANT_ID=? and "
							+ "STORE_ID=? and TERMINAL_ID=? and SERIAL_NO=? ";

					logger.debug("tmkinfo [" + tmkinfo + "]");

					tmkpsmt = connection.prepareStatement(tmkinfo);
					tmkpsmt.setString(1, merchantId);
					tmkpsmt.setString(2, sid);
					tmkpsmt.setString(3, terminalid);
					tmkpsmt.setString(4, serialNo);

					tmkRS = tmkpsmt.executeQuery();

					if (tmkRS.next()) {
						tmknum = tmkRS.getString(1);
					}

					logger.debug("Tmknum [" + tmknum + "]");

					filePstmt.close();
					fileRS.close();
					tmkpsmt.close();

					// fileQry = "select
					// nvl(merchant_name,' '),nvl(city,' '),'KEN','KE'
					// from merchant_master where merchant_id=?";
					fileQry = "select nvl(store_name,' '),(select nvl(OFFICE_NAME,' ') "
							+ "from BRANCH_MASTER where OFFICE_CODE=sm.location),'KEN','KE' "
							+ "from store_master sm where store_id=?";

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, sid.trim());
					fileRS = filePstmt.executeQuery();

					location = new StringBuffer(50);

					ServiceQry="select distinct '#BWD'|| ((select decode(IW.service_code,'IWT','I') from TERMINAL_SERVICE_MAPPING IW"
							  +"  where IW.terminal_id=TSM.terminal_id and IW.service_code='IWT'))|| "
							  + "((select decode(MY.service_code,'MYP','M') from TERMINAL_SERVICE_MAPPING MY where MY.terminal_id=TSM.terminal_id and "
							   +"MY.service_code='MYP'))||"
							   +" ((select decode(PU.service_code,'PUR','P') from TERMINAL_SERVICE_MAPPING PU where PU.terminal_id=TSM.terminal_id and"
							  +" PU.service_code='PUR')) "
							  +" from TERMINAL_SERVICE_MAPPING TSM"
							  +" where TSM.terminal_id=?";


					filePstmtservice = connection.prepareStatement(ServiceQry);
					filePstmtservice.setString(1,terminalid);
					fileRSservice = filePstmtservice.executeQuery();

					if (fileRSservice.next()) {
						serviceString = fileRSservice.getString(1);
					}


					if (fileRS.next()) {
						location.append(
								fileRS.getString(1) == null ? " " : fileRS
										.getString(1)).append("##");
						location.append(
								fileRS.getString(2) == null ? " " : fileRS
										.getString(2)).append("##");
						location.append(
								fileRS.getString(3) == null ? " " : fileRS
										.getString(3)).append("##");
						location.append(fileRS.getString(4) == null ? " "
								: fileRS.getString(4));
					}
					location.append(serviceString == null ? " "
							: serviceString);
					filePstmt.close();
					fileRS.close();
					filePstmtservice.close();
					fileRSservice.close();

					logger.debug("location [" + location.toString() + "]");
					try {
						/*eachFileData = TerminalFileData.CreateFileData(users,
								terminalid, merchantId, EncryptTransactionPin
										.add2Encrypt(key, supervisor, admin,
												'F'), tmknum, location
										.toString());*/
						eachFileData = TerminalFileData.CreateFileData(users,
								terminalid, merchantId, EncryptTransactionPin
										.add2Encrypt(key, supervisor, admin,
												'F'), tmknum, location
										.toString());
					} catch (Exception e) {
						logger.error(e);
					}

					logger.debug("Data Loading into file [" + generatedFileName
							+ "] Data [" + eachFileData + "]");

					updateQry = "update USER_TERMINAL_MAPPING set ASSIGN_FALG=?"
							+ " where MERCHANT_ID=? and STORE_ID=? and "
							+ "TERMINAL_ID=? and ASSIGN_FALG=? ";
					updatePstmt = connection.prepareStatement(updateQry);
					updatePstmt.setString(1, "C");
					updatePstmt.setString(2, merchantId);
					updatePstmt.setString(3, sid);
					updatePstmt.setString(4, terminalid);
					updatePstmt.setString(5, "I");
					int updateCnt = updatePstmt.executeUpdate();

					logger.debug("updateCnt [" + updateCnt + "]");

					fileQry = "select * from ftp_pos where serialno=trim(?)";
					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, serialNo);
					fileRS = filePstmt.executeQuery();

					if (fileRS.next()) {
						logger.debug("Inside ftp_pos if.");
						//tmkinfo = "update ftp_pos set data=? where serialno=? and TERMINALID=?";
						tmkinfo = "update ftp_pos set data=?,terminalid=?,MAKERDTTM=sysdate where serialno=trim(?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, eachFileData);
						tmkpsmt.setString(2, terminalid);
						tmkpsmt.setString(3, serialNo);
					} else {
						logger.debug("Inside ftp_pos else.");
						tmkinfo = "insert into ftp_pos "
								+ "(serialno,data,TERMINALID)"
								+ " values(?,?,?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, serialNo);
						tmkpsmt.setString(2, eachFileData);
						tmkpsmt.setString(3, terminalid);
					}

					updateCnt = tmkpsmt.executeUpdate();
					connection.commit();
					logger.debug("updateCnt[" + updateCnt + "]");
				}
			} catch (SQLException e) {

				logger.error(e);
			}

		} catch (Exception e) {
			logger.error(e);
			fileName = "dummy.txt";
		} finally {
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(updatePstmt);
			DBUtils.closePreparedStatement(filePstmt);
			DBUtils.closePreparedStatement(terminalPstmt);
			DBUtils.closePreparedStatement(tmkpsmt);

			DBUtils.closeResultSet(tmkRS);
			DBUtils.closeResultSet(fileRS);
			DBUtils.closeResultSet(terminalRS);
			eachFileData = null;
			merchantId = null;
			sid = null;
			terminalid = null;
			serialNo = null;
			generatedFileName = null;
			sourcepath = null;
			fileQry = null;
			Username = null;
			password = null;
			admin = null;
			supervisor = null;
			key = null;

			tmknum = null;
			eachData = null;
			tmkinfo = null;

			bundle = null;

			// file = null;
			users = null;
			location.delete(0, location.length());
			location = null;
			terminalInfo = null;
			logger.debug("------- UserManagementFileGenerator Ends-------");
		}

		return fileName;
	}
	
	
	public static String insertuserstring(){
		logger.debug("------- UserManagementFileGenerator Starts-------");
		String fileName = "";
		String eachFileData = null;
		// FileWriter writer = null;
		String merchantId = null;
		String sid = null;
		String terminalid = null;
		String serialNo = null;
		String generatedFileName = null;
		String sourcepath = null;
		String fileQry = "";
		String Username = null;
		String password = null;
		String admin = null;
		String supervisor = null;
		// String cryptedPassword = null;
		String key = "97206B46CE46376894703ECE161F31F2";

		String tmknum = null;
		String eachData = "";
		String tmkinfo = "";

		ResourceBundle bundle = null;

		Connection connection = null;

		PreparedStatement terminalPstmt = null;
		PreparedStatement filePstmt = null;
		PreparedStatement tmkpsmt = null;
		PreparedStatement updatePstmt = null;
		PreparedStatement filePstmtservice = null;
		ResultSet fileRSservice = null;

		ResultSet terminalRS = null;
		ResultSet fileRS = null;
		ResultSet tmkRS = null;

		// File file = null;
		HashMap<String, String> users = null;

		String terminalInfo = null;
		String updateQry = "";
		StringBuffer location = null;
		String ServiceQry="";
		String serviceString="";

		try {
			logger.debug("Inside try block.");

			bundle = ResourceBundle.getBundle("pathinfo_config");

			sourcepath = bundle.getString("USER_FILE_PATH");

			logger.debug("sourcepath[" + sourcepath + "]");

			terminalInfo = "select mid,sid,tid,serial,admpwd,suppwd,makerid from ( "
					+ "select MERCHANT_ID mid,STORE_ID sid,TERMINAL_ID tid,SERIAL_NO serial,ADMIN_PWD admpwd, SUPERVISOR_PWD suppwd,"
					+ "MAKER_ID makerid from USER_TERMINAL_MAPPING UTM where ASSIGN_FALG= ?) "
					+ "group by mid,sid,tid,serial,admpwd,suppwd,makerid having (COUNT(tid)>0)";
			try {

				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");
				terminalPstmt = connection.prepareStatement(terminalInfo);
				terminalPstmt.setString(1, "I");
				terminalRS = terminalPstmt.executeQuery();

				if (terminalRS.next()) {
					merchantId = terminalRS.getString(1);
					sid = terminalRS.getString(2);
					terminalid = terminalRS.getString(3);
					serialNo = terminalRS.getString(4);
					//admin = terminalRS.getString(5);
					admin = "81A7A7488F6E901D";
					supervisor = terminalRS.getString(6);

					eachData = admin + supervisor;

					logger.debug("MerchantId[" + merchantId + "]" + " sid["
							+ sid + "]" + " terminalid[" + terminalid
							+ "]  serialNo[" + serialNo + "] admin[" + admin
							+ "]  supervisor[" + supervisor + "]");

					logger.debug("EachData [" + eachData + "]");

					eachFileData = "";
					fileQry = "select ULC.LOGIN_USER_ID,ULC.PIN from USER_LOGIN_CREDENTIALS ULC,"
							+ "USER_TERMINAL_MAPPING UTM where ULC.LOGIN_USER_ID=UTM.USER_ID and"
							+ " UTM.MERCHANT_ID=? and UTM.STORE_ID=? and UTM.TERMINAL_ID=? and ASSIGN_FALG=? ";

					generatedFileName = sourcepath + serialNo + ".txt";

					logger.debug("generatedFileName[" + generatedFileName + "]");

					fileName = serialNo + ".txt";
					logger.debug("|generateCsvFile| fileName[" + fileName + "]");

					// file = new File(generatedFileName);
					users = new HashMap<String, String>();
					// writer = new FileWriter(file);

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, merchantId);
					filePstmt.setString(2, sid);
					filePstmt.setString(3, terminalid);
					filePstmt.setString(4, "I");

					fileRS = filePstmt.executeQuery();

					while (fileRS.next()) {
						Username = fileRS.getString(1);
						password = fileRS.getString(2);
						users.put(Username, password);

						logger.debug("Username[" + Username + "] Password["
								+ password + "]");
					}

					logger.debug("serialNo [" + serialNo + "]");

					tmkinfo = "select TMK FROM TERMINAL_MASTER where MERCHANT_ID=? and "
							+ "STORE_ID=? and TERMINAL_ID=? and SERIAL_NO=? ";

					logger.debug("tmkinfo [" + tmkinfo + "]");

					tmkpsmt = connection.prepareStatement(tmkinfo);
					tmkpsmt.setString(1, merchantId);
					tmkpsmt.setString(2, sid);
					tmkpsmt.setString(3, terminalid);
					tmkpsmt.setString(4, serialNo);

					tmkRS = tmkpsmt.executeQuery();

					if (tmkRS.next()) {
						tmknum = tmkRS.getString(1);
					}

					logger.debug("Tmknum [" + tmknum + "]");

					filePstmt.close();
					fileRS.close();
					tmkpsmt.close();

					// fileQry = "select
					// nvl(merchant_name,' '),nvl(city,' '),'KEN','KE'
					// from merchant_master where merchant_id=?";
					fileQry = "select nvl(store_name,' '),(select nvl(OFFICE_NAME,' ') "
							+ "from BRANCH_MASTER where OFFICE_CODE=sm.location),'KEN','KE' "
							+ "from store_master sm where store_id=?";

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, sid.trim());
					fileRS = filePstmt.executeQuery();

					location = new StringBuffer(50);

					ServiceQry="select distinct '#BWD'|| ((select decode(IW.service_code,'IWT','I') from TERMINAL_SERVICE_MAPPING IW"
							  +"  where IW.terminal_id=TSM.terminal_id and IW.service_code='IWT'))|| "
							  + "((select decode(MY.service_code,'MYP','M') from TERMINAL_SERVICE_MAPPING MY where MY.terminal_id=TSM.terminal_id and "
							   +"MY.service_code='MYP'))||"
							   +" ((select decode(PU.service_code,'PUR','P') from TERMINAL_SERVICE_MAPPING PU where PU.terminal_id=TSM.terminal_id and"
							  +" PU.service_code='PUR')) "
							  +" from TERMINAL_SERVICE_MAPPING TSM"
							  +" where TSM.terminal_id=?";


					filePstmtservice = connection.prepareStatement(ServiceQry);
					filePstmtservice.setString(1,terminalid);
					fileRSservice = filePstmtservice.executeQuery();

					if (fileRSservice.next()) {
						serviceString = fileRSservice.getString(1);
					}


					if (fileRS.next()) {
						location.append(
								fileRS.getString(1) == null ? " " : fileRS
										.getString(1)).append("##");
						location.append(
								fileRS.getString(2) == null ? " " : fileRS
										.getString(2)).append("##");
						location.append(
								fileRS.getString(3) == null ? " " : fileRS
										.getString(3)).append("##");
						location.append(fileRS.getString(4) == null ? " "
								: fileRS.getString(4));
					}
					location.append(serviceString == null ? " "
							: serviceString);
					filePstmt.close();
					fileRS.close();
					filePstmtservice.close();
					fileRSservice.close();

					logger.debug("location [" + location.toString() + "]");
					try {
						/*eachFileData = TerminalFileData.CreateFileData(users,
								terminalid, merchantId, EncryptTransactionPin
										.add2Encrypt(key, supervisor, admin,
												'F'), tmknum, location
										.toString());*/
						eachFileData = TerminalFileData.CreateFileData(users,
								terminalid, merchantId, EncryptTransactionPin
										.add2Encrypt(key, supervisor, admin,
												'F'), tmknum, location
										.toString());
					} catch (Exception e) {
						logger.error(e);
					}

					logger.debug("Data Loading into file [" + generatedFileName
							+ "] Data [" + eachFileData + "]");

					updateQry = "update USER_TERMINAL_MAPPING set ASSIGN_FALG=?"
							+ " where MERCHANT_ID=? and STORE_ID=? and "
							+ "TERMINAL_ID=? and ASSIGN_FALG=? ";
					updatePstmt = connection.prepareStatement(updateQry);
					updatePstmt.setString(1, "C");
					updatePstmt.setString(2, merchantId);
					updatePstmt.setString(3, sid);
					updatePstmt.setString(4, terminalid);
					updatePstmt.setString(5, "I");
					int updateCnt = updatePstmt.executeUpdate();

					logger.debug("updateCnt [" + updateCnt + "]");

					fileQry = "select * from ftp_pos where serialno=trim(?)";
					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, serialNo);
					fileRS = filePstmt.executeQuery();

					if (fileRS.next()) {
						logger.debug("Inside ftp_pos if.");
						//tmkinfo = "update ftp_pos set data=? where serialno=? and TERMINALID=?";
						tmkinfo = "update ftp_pos set data=?,terminalid=?,MAKERDTTM=sysdate where serialno=trim(?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, eachFileData);
						tmkpsmt.setString(2, terminalid);
						tmkpsmt.setString(3, serialNo);
					} else {
						logger.debug("Inside ftp_pos else.");
						tmkinfo = "insert into ftp_pos "
								+ "(serialno,data,TERMINALID)"
								+ " values(?,?,?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, serialNo);
						tmkpsmt.setString(2, eachFileData);
						tmkpsmt.setString(3, terminalid);
					}

					updateCnt = tmkpsmt.executeUpdate();
					connection.commit();
					logger.debug("updateCnt[" + updateCnt + "]");
				}
			} catch (SQLException e) {

				logger.error(e);
			}

		} catch (Exception e) {
			logger.error(e);
			fileName = "dummy.txt";
		} finally {
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(updatePstmt);
			DBUtils.closePreparedStatement(filePstmt);
			DBUtils.closePreparedStatement(terminalPstmt);
			DBUtils.closePreparedStatement(tmkpsmt);

			DBUtils.closeResultSet(tmkRS);
			DBUtils.closeResultSet(fileRS);
			DBUtils.closeResultSet(terminalRS);
			eachFileData = null;
			merchantId = null;
			sid = null;
			terminalid = null;
			serialNo = null;
			generatedFileName = null;
			sourcepath = null;
			fileQry = null;
			Username = null;
			password = null;
			admin = null;
			supervisor = null;
			key = null;

			tmknum = null;
			eachData = null;
			tmkinfo = null;

			bundle = null;

			// file = null;
			users = null;
			location.delete(0, location.length());
			location = null;
			terminalInfo = null;
			logger.debug("------- UserManagementFileGenerator Ends-------");
		}

		return fileName;
	}
	
	

	public static String generateCsvFile(String terminalId) {
		logger.debug("------- UserManagementFileGenerator Starts-------");
		String fileName = "";
		String eachFileData = null;
		// FileWriter writer = null;
		String merchantId = null;
		String sid = null;
		String terminalid = null;
		String serialNo = null;
		String generatedFileName = null;
		String sourcepath = null;
		String fileQry = "";
		String Username = null;
		String password = null;
		String admin = null;
		String supervisor = null;
		// String cryptedPassword = null;
		//String key = "97206B46CE46376894703ECE161F31F2";

		String tmknum = null;
		String eachData = "";
		String tmkinfo = "";
		String market_street=null;
		ResourceBundle bundle = null;

		Connection connection = null;

		PreparedStatement terminalPstmt = null;
		PreparedStatement filePstmt = null;
		PreparedStatement tmkpsmt = null;
		PreparedStatement updatePstmt = null;

		ResultSet terminalRS = null;
		ResultSet fileRS = null;
		ResultSet tmkRS = null;

		// File file = null;
		HashMap<String, String> users = null;

		String terminalInfo = null;
		String updateQry = "";
		StringBuffer location = null;
		String campusName = null;

		try {
			logger.debug("Inside try block.");

			bundle = ResourceBundle.getBundle("pathinfo_config");

			sourcepath = bundle.getString("USER_FILE_PATH");

			logger.debug("sourcepath[" + sourcepath + "]");

			terminalInfo = "select mid,sid,tid,serial,admpwd,suppwd,makerid, admin, supervisor from ( "
					+ "select MERCHANT_ID mid,STORE_ID sid,TERMINAL_ID tid,SERIAL_NO serial,ADMIN_PWD admpwd, SUPERVISOR_PWD suppwd,"
					+ "MAKER_ID makerid, admin, supervisor from USER_TERMINAL_MAPPING UTM where ASSIGN_FALG= ? and Utm.Terminal_Id ='"+terminalId+"') "
					+ "group by mid,sid,tid,serial,admpwd,suppwd,makerid, admin, supervisor having (COUNT(tid)>0)";
			try {

				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");
				terminalPstmt = connection.prepareStatement(terminalInfo);
				terminalPstmt.setString(1, "I");
				terminalRS = terminalPstmt.executeQuery();

				while (terminalRS.next()) {
					merchantId = terminalRS.getString(1);
					sid = terminalRS.getString(2);
					terminalid = terminalRS.getString(3);
					serialNo = terminalRS.getString(4);
					admin=GetPassword(terminalRS.getString(8));
					//admin = terminalRS.getString(5);
					supervisor = GetPassword(terminalRS.getString(9));
					//supervisor = terminalRS.getString(6);

					//eachData = supervisor +"^"+ admin + "^#" +terminalid+serialNo;
					eachData = supervisor +"^"+ admin + "^#" +terminalid + "#"+merchantId;

					logger.debug("MerchantId[" + merchantId + "]" + " sid["
							+ sid + "]" + " terminalid[" + terminalid
							+ "]  serialNo[" + serialNo + "] admin[" + admin
							+ "]  supervisor[" + supervisor + "]");

					logger.debug("EachData [" + eachData + "]");

					eachFileData = "";
					fileQry = "select ULC.LOGIN_USER_ID,ULC.PIN from USER_LOGIN_CREDENTIALS ULC,"
							+ "USER_TERMINAL_MAPPING UTM where ULC.LOGIN_USER_ID=UTM.USER_ID and"
							+ " UTM.MERCHANT_ID=? and UTM.STORE_ID=? and UTM.TERMINAL_ID=? and ASSIGN_FALG=? ";

					generatedFileName = sourcepath + serialNo + ".txt";

					logger.debug("generatedFileName[" + generatedFileName + "]");

					fileName = serialNo + ".txt";
					logger.debug("|generateCsvFile| fileName[" + fileName + "]");

					// file = new File(generatedFileName);
					users = new HashMap<String, String>();
					// writer = new FileWriter(file);

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, merchantId);
					filePstmt.setString(2, sid);
					filePstmt.setString(3, terminalid);
					filePstmt.setString(4, "I");

					fileRS = filePstmt.executeQuery();

					while (fileRS.next()) {
						Username = fileRS.getString(1);
						password = fileRS.getString(2);
						users.put(Username, password);

						logger.debug("Username[" + Username + "] Password["
								+ password + "]");
					}

					logger.debug("serialNo [" + serialNo + "]");


					tmkinfo = "select SERVICE_CODE FROM TERMINAL_SERVICE_MAPPING where TERMINAL_ID=?";

					logger.debug("tmkinfo [" + tmkinfo + "]");

					tmkpsmt = connection.prepareStatement(tmkinfo);
					tmkpsmt.setString(1, terminalid);

					tmkRS = tmkpsmt.executeQuery();
					String serviceCode = null;
					if (tmkRS.next()) {
						serviceCode = tmkRS.getString(1);
					}


					//university string asked by kishore
					//eachData=eachData +"#"+ market_street+"#"+sid+"#"+merchantId+"@"+tmknum;
					//eachData=eachData +"#"+serviceCode+"#"+sid+"#"+merchantId+"@";
					logger.debug("Tmknum [" + tmknum + "]");

					filePstmt.close();
					fileRS.close();
					tmkpsmt.close();

					 fileQry = "select nvl(merchant_name,' '),nvl(city,' '),'KEN','KE' from merchant_master where merchant_id=?";
					//fileQry = "select SUBCOUNTYNAME, SUBCOUNTYNAME ,'KEN','KE' from SUBCOUNTY sm where SUBCOUNTYID=?";

					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, merchantId.trim());
					fileRS = filePstmt.executeQuery();

					location = new StringBuffer(50);

					if (fileRS.next()) {
						location.append(fileRS.getString(1) == null ? " " : fileRS.getString(1)).append("##");
						location.append(fileRS.getString(2) == null ? " " : fileRS.getString(2)).append("##");
						location.append(fileRS.getString(3) == null ? " " : fileRS.getString(3)).append("##");
						location.append(fileRS.getString(4) == null ? " " : fileRS.getString(4));
						campusName = fileRS.getString(2);
					}

					filePstmt.close();
					fileRS.close();
					//by suresh requested by raymond as university name replacement by campusname
					eachData=eachData +"#"+serviceCode+"#"+sid+"#"+campusName+"@";
					logger.debug("location [" + location.toString() + "]");
					try {
						/*eachFileData = TerminalFileData.CreateFileData(users,
								terminalid, merchantId, EncryptTransactionPin
										.add2Encrypt(key, supervisor, admin,
												'F'), tmknum, location
										.toString());*/
						eachFileData = TerminalFileData.CreateFileData(users)+"^"+eachData;
					} catch (Exception e) {
						logger.error(e);
					}

					logger.debug("Data Loading into file [" + generatedFileName
							+ "] Data [" + eachFileData + "]");

					updateQry = "update USER_TERMINAL_MAPPING set ASSIGN_FALG=?"
							+ " where MERCHANT_ID=? and STORE_ID=? and "
							+ "TERMINAL_ID=? and ASSIGN_FALG=? ";
					updatePstmt = connection.prepareStatement(updateQry);
					updatePstmt.setString(1, "I");
					updatePstmt.setString(2, merchantId);
					updatePstmt.setString(3, sid);
					updatePstmt.setString(4, terminalid);
					updatePstmt.setString(5, "I");
					int updateCnt = updatePstmt.executeUpdate();

					logger.debug("updateCnt [" + updateCnt + "]");

					fileQry = "select * from ftp_pos where serialno=trim(?)";
					filePstmt = connection.prepareStatement(fileQry);
					filePstmt.setString(1, serialNo);
					fileRS = filePstmt.executeQuery();

					if (fileRS.next()) {
						logger.debug("Inside ftp_pos if.");
						//tmkinfo = "update ftp_pos set data=? where serialno=? and TERMINALID=?";
						tmkinfo = "update ftp_pos set data=?,terminalid=?,MAKERDTTM=sysdate where serialno=trim(?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, eachFileData);
						tmkpsmt.setString(2, terminalid);
						tmkpsmt.setString(3, serialNo);
					} else {
						logger.debug("Inside ftp_pos else.");
						tmkinfo = "insert into ftp_pos "
								+ "(serialno,data,TERMINALID)"
								+ " values(?,?,?)";
						tmkpsmt = connection.prepareStatement(tmkinfo);
						tmkpsmt.setString(1, serialNo);
						tmkpsmt.setString(2, eachFileData);
						tmkpsmt.setString(3, terminalid);
					}

					updateCnt = tmkpsmt.executeUpdate();
					connection.commit();
					logger.debug("updateCnt[" + updateCnt + "]");
				}
			} catch (SQLException e) {

				logger.error("Error Occured..!"+e.getLocalizedMessage());
			}

		} catch (Exception e) {
			logger.error("Error Occured..!"+e.getLocalizedMessage());
			fileName = "dummy.txt";
		} finally {
			DBUtils.closeConnection(connection);

			DBUtils.closePreparedStatement(updatePstmt);
			DBUtils.closePreparedStatement(filePstmt);
			DBUtils.closePreparedStatement(terminalPstmt);
			DBUtils.closePreparedStatement(tmkpsmt);

			DBUtils.closeResultSet(tmkRS);
			DBUtils.closeResultSet(fileRS);
			DBUtils.closeResultSet(terminalRS);
			eachFileData = null;
			merchantId = null;
			sid = null;
			terminalid = null;
			serialNo = null;
			generatedFileName = null;
			sourcepath = null;
			fileQry = null;
			Username = null;
			password = null;
			admin = null;
			supervisor = null;
			key = null;

			tmknum = null;
			eachData = null;
			tmkinfo = null;

			bundle = null;

			// file = null;
			users = null;
			location.delete(0, location.length());
			location = null;
			terminalInfo = null;
			logger.debug("------- UserManagementFileGenerator Ends-------");
		}

		return fileName;
	}

	private static String GetPassword(String userId){
		String uNamePin="";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet res =null;
		String pinQRY="select ulc.pin, ui.USER_NAME from user_information ui, user_login_credentials ulc where ui.common_id=ulc.common_id and ulc.login_user_id=?";
		try{
			if(conn==null)
				logger.debug("inside  get password");
				conn=DBConnector.getConnection();
			pstmt=conn.prepareStatement(pinQRY);
			pstmt.setString(1, userId);
			res=pstmt.executeQuery();
			while(res.next()){
				//uNamePin = res.getString("USER_NAME") + ","+EncryptTransactionPin.decrypt(key, res.getString("pin"), 4) ;
				uNamePin = res.getString("USER_NAME") + ","+res.getString("pin") ;
			}
		}catch(Exception ex){
			logger.debug("Error Occured..!"+ex.getMessage());
		}finally{
			logger.debug("Close Connection---------------------------");
			logger.debug("Error Occured..!");
			DBUtils.closeConnection(conn);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeResultSet(res);
			logger.debug("Close Connection--------------------end-------");
		}
		return uNamePin;
	}

}