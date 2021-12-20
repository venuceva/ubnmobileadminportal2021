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

public class FtpPosPasswordUpdater {
	protected static Logger logger = Logger
			.getLogger(FtpPosPasswordUpdater.class);
	Connection connection = null;

	PreparedStatement terminalPstmt = null;
	PreparedStatement filePstmt = null;
	PreparedStatement tmkpsmt = null;
	PreparedStatement updatePstmt = null;
	PreparedStatement filePstmtservice = null;
	ResultSet fileRSservice = null;
	PreparedStatement pstmt = null;

	ResultSet terminalRS = null;
	ResultSet fileRS = null;
	ResultSet tmkRS = null;
	ResultSet rs = null;
	String merchantId = null;
	String sid = null;
	String terminalid = null;
	String generatedFileName = null;
	String sourcepath = null;
	String fileQry = "";
	String Username = null;
	String password = null;
	String admin = null;
	String supervisor = null;
	String key = "97206B46CE46376894703ECE161F31F2";
	String fileName = "";
	String eachFileData = null;
	String tmknum = null;
	String eachData = "";
	String tmkinfo = "";
	ResourceBundle bundle = null;
	String storeInfo = null;
	String serviceInfo = null;
	String userString = null;
	public void generateCsvFile(String serialNo, String terminalId, String storeID, String merchantID) {
		logger.debug("------- Updating FTPPOS -------");
		this.merchantId = merchantID;
		this.sid = storeID;
		this.terminalid = terminalId;

		try {
			logger.debug("Inside try block.");

			bundle = ResourceBundle.getBundle("pathinfo_config");
			logger.debug("Generating FTPString.....!");
			
			sourcepath = bundle.getString("USER_FILE_PATH");
			connection = DBConnector.getConnection();
			logger.info("getting users info for serialNo..:"+serialNo);
			
			userString = getUSersToTerminal(serialNo, connection);
			logger.info("userString..:"+userString);
			logger.info("getting supervisor info for serialNo..:"+serialNo);
			
			supervisor= getSupervisorsToTerminal(serialNo, connection);
			logger.info("supervisor..:"+supervisor);
			
			admin = "81A7A7488F6E901D";
			tmknum = getTmkNum(serialNo, connection);
			logger.info("tmknum..:"+tmknum);
			
			storeInfo = getStoreInfo(serialNo, connection);
			logger.info("storeInfo..:"+storeInfo);
			serviceInfo = getserviceInfo(serialNo, connection);
			
			logger.info("serviceInfo..:"+serviceInfo);
			String data = userString+"*^#"+terminalId+"#"+merchantId+"#"+storeInfo+"#"+serviceInfo+"@"+EncryptTransactionPin.add2Encrypt(key, supervisor, admin,'F');
			
			logger.info("data..:"+data);
			updateFtpPos(data, terminalid, merchantId, connection, serialNo);
			
			logger.info("updated FTP_POS.");
			updateUserTerminalMapping(sid, terminalid, merchantId, connection);
			
			logger.info("updated Status in Table.");
			connection.commit();
			
		} catch (Exception e) {
			logger.error(e);
			fileName = "dummy.txt";
		} finally {
			DBUtils.closeConnection(connection);
			merchantId = null;
			sid = null;
			terminalid = null;
			admin = null;
			supervisor = null;
			key = null;
			tmknum = null;
			tmkinfo = null;
			logger.debug("------- FTP_POS String updation Ends-------");
		}

	}

	private void updateUserTerminalMapping(String sid, String terminalid,
			String merchantId, Connection connection) {
		try{
			String updateQry = "update USER_TERMINAL_MAPPING set ASSIGN_FALG=?"
					+ " where MERCHANT_ID=? and STORE_ID=? and "
					+ "TERMINAL_ID=? and ASSIGN_FALG=? ";
			pstmt = connection.prepareStatement(updateQry);
			pstmt.setString(1, "C");
			pstmt.setString(2, merchantId);
			pstmt.setString(3, sid);
			pstmt.setString(4, terminalid);
			pstmt.setString(5, "I");
			int updateCnt = pstmt.executeUpdate();
			logger.debug("Updated ...!"+updateCnt);
		}catch(Exception ex){
			logger.error("Error occured..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
		}
	}

	private void updateFtpPos(String data, String terminalid,
			String merchantId, Connection cconnection, String serialNo) {
		try{

			String updateQRY = "update ftp_pos set data=?, terminalid=?, MAKERDTTM=sysdate where serialno=trim(?)";
			pstmt = connection.prepareStatement(updateQRY);
			pstmt.setString(1, data);
			pstmt.setString(2, terminalid);
			pstmt.setString(3, serialNo);
			int updated = pstmt.executeUpdate();
			connection.commit();
			logger.info("Serial No "+serialNo);
			logger.info("Rows Updated in FTP_POS..!"+updated);
		}catch(Exception ex){
			logger.error("Error Occured...!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
		}
	}

	private String getserviceInfo(String serialNo, Connection connection2) {
		String serviceInfo="";
		try{
			String serviceQry="select distinct '#BWD'|| ((select decode(IW.service_code,'IWT','I') from TERMINAL_SERVICE_MAPPING IW"
					  +"  where IW.terminal_id=TSM.terminal_id and IW.service_code='IWT'))|| "
					  + "((select decode(MY.service_code,'MYP','M') from TERMINAL_SERVICE_MAPPING MY where MY.terminal_id=TSM.terminal_id and "
					   +"MY.service_code='MYP'))||"
					   +" ((select decode(PU.service_code,'PUR','P') from TERMINAL_SERVICE_MAPPING PU where PU.terminal_id=TSM.terminal_id and"
					  +" PU.service_code='PUR')) "
					  +" from TERMINAL_SERVICE_MAPPING TSM"
					  +" where TSM.terminal_id=?";

			pstmt = connection.prepareStatement(serviceQry);
			pstmt.setString(1, terminalid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serviceInfo = rs.getString(1);
			}
		}catch(Exception ex){
			logger.error("Error Occured ..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
			rs = null;
		}
		logger.info("serviceInfo..:"+serviceInfo);
		return serviceInfo;
	}

	private String getStoreInfo(String serialNo, Connection connection) {
		String storeInfo="";
		try{
			String storeQRY = "select nvl(store_name,' '),(select nvl(OFFICE_NAME,' ') from BRANCH_MASTER where "
					+" OFFICE_CODE=sm.location),'KEN','KE' from store_master sm where store_id=?";

			logger.debug("tmkinfo [" + tmkinfo + "]");
			pstmt = connection.prepareStatement(storeQRY);
			pstmt.setString(1, sid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				storeInfo = rs.getString(1)+"#"+rs.getString(2)+"#"+rs.getString(3)+"#"+rs.getString(4);
			}
		}catch(Exception ex){
			logger.error("Error Occured ..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
			rs = null;
		}
		return storeInfo;
	}

	private String getTmkNum(String serialNo, Connection connection) {
		String tmknum= "";

		try{
			String tmkqry = "select TMK FROM TERMINAL_MASTER where MERCHANT_ID=? and "
					+ "STORE_ID=? and TERMINAL_ID=? and SERIAL_NO=? ";

			logger.debug("tmkinfo [" + tmkinfo + "]");
			pstmt = connection.prepareStatement(tmkqry);
			pstmt.setString(1, merchantId);
			pstmt.setString(2, sid);
			pstmt.setString(3, terminalid);
			pstmt.setString(4, serialNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				tmknum = rs.getString(1);
			}
		}catch(Exception ex){
			logger.error("Error Occured ..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
		}
		return tmknum;
	}

	private String getSupervisorsToTerminal(String serialNo, Connection connection) {
		String supervisorString= "";
		try{
			connection = DBConnector.getConnection();
			pstmt = connection.prepareStatement("SELECT ULC.PIN FROM USER_LOGIN_CREDENTIALS ULC, USER_TERMINAL_MAPPING UTM WHERE ULC.LOGIN_USER_ID= UTM.SUPERVISOR AND UTM.SERIAL_NO = ? and UTM.MERCHANT_ID = ? AND UTM.STORE_ID = ?");
			pstmt.setString(1, serialNo);
			pstmt.setString(2, merchantId);
			pstmt.setString(3, sid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				supervisorString = rs.getString(1);
			}
		}catch(Exception ex){
			logger.error("Error Occured ..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
			rs = null;
		}
		return supervisorString;
	}

	private String getUSersToTerminal(String serialNo, Connection connection) {
		String usersString= "";
		try{
			pstmt = connection.prepareStatement("SELECT replace(replace(WM_CONCAT(CONCAT(CONCAT(UTM.USER_ID, ':'), ULC.PIN)), ',', '*'), ':', ',') FROM USER_LOGIN_CREDENTIALS ULC, USER_TERMINAL_MAPPING UTM WHERE ULC.LOGIN_USER_ID= UTM.USER_ID AND UTM.SERIAL_NO = ? and UTM.MERCHANT_ID = ? AND UTM.STORE_ID = ?");
			pstmt.setString(1, serialNo);
			pstmt.setString(2, merchantId);
			pstmt.setString(3, sid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				usersString = rs.getString(1);
			}
		}catch(Exception ex){
			logger.error("Error Occured ..!"+ex.getLocalizedMessage());
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			pstmt = null;
			rs = null;
		}
		return usersString;
	}

}