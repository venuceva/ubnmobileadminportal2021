package com.ceva.banking.usermgmt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.sms.mgate.PasswordProtectZipFile;
import com.ceva.util.DBUtils;

public class SettlementFileGen {
	protected static Logger logger = Logger.getLogger(SettlementFileGen.class);

	private static SimpleDateFormat dateformat = null;

	public static String SettleDailyCsvFile() {

		logger.debug("------- Settlement File Gen Starts-------");
		File file = null;

		String fileName = "";
		String eachFileData = null;
		FileWriter writer = null;
		FileWriter writer1 = null;

		String settlementfilename = null;
		String rawfilename = null;
		String sourcepath = null;

		ResourceBundle bundle = null;

		Connection connection = null;

		PreparedStatement bankPstmt = null;
		PreparedStatement tmkpsmt = null;
		PreparedStatement updatePstmt = null;

		CallableStatement callableStmt1 = null;
		CallableStatement callableStmt2 = null;

		ResultSet bankRS = null;
		ResultSet rawRS = null;
		ResultSet tmkRS = null;

		String bankcode = null;
		StringBuilder maindata = null;
		StringBuilder headerdata = null;

		String data = "";
		String bankInfo = "";

		String filesToZip[] = null;
		String arrData[] = null;

		PasswordProtectZipFile passwordProtectZipFile = null;
		Date now = null;

		try {

			filesToZip = new String[4];
			dateformat = new SimpleDateFormat("YYMMDD");

			logger.debug("Inside try block");

			bundle = ResourceBundle.getBundle("pathinfo_config");

			sourcepath = bundle.getString("SETTLE_FILE_PATH");

			logger.debug("Sourcepath is [" + sourcepath + "]");

			bankInfo = bundle.getString("SETTLE_FILE_QUERY");

			file = new File(sourcepath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file.setReadable(true);
			file.setExecutable(true);
			file.setWritable(true);

			try {
				headerdata = new StringBuilder(100);
				maindata = new StringBuilder(100);

				now = new Date();
				connection = DBConnector.getConnection();
				logger.debug("Connection is null [" + connection == null + "]");
				bankPstmt = connection.prepareStatement(bankInfo);
				bankRS = bankPstmt.executeQuery();
				while (bankRS.next()) {
					headerdata.append("");
					maindata.append("");

					bankcode = bankRS.getString(1);

					eachFileData = "";

					/* Raw File Generation Start */
					rawfilename = sourcepath + bankcode + "_"
							+ dateformat.format(now) + "_RAWDATA.txt";
					logger.debug("Rawfilename [" + rawfilename + "]");
					file = null;
					file = new File(rawfilename);
					writer = new FileWriter(file);
					filesToZip[0] = file.getName();

					try {
						logger.debug("Before Getting rights.");

						String rawquery = "{call SETTLEMENTRAWDATA.pRawData(?,?,?)}";
						callableStmt1 = connection.prepareCall(rawquery);
						callableStmt1.setString(1, bankcode);
						callableStmt1.registerOutParameter(2,
								OracleTypes.VARCHAR);
						callableStmt1.registerOutParameter(3,
								OracleTypes.CURSOR);

						callableStmt1.execute();
						logger.debug(" Rights block executed successfully "
								+ "with error_message["
								+ callableStmt1.getString(3) + "]");

						rawRS = (ResultSet) callableStmt1.getObject(3);

						int i = 0;
						while (rawRS.next()) {
							eachFileData = rawRS.getString(1);
							if (i == 0) {
								maindata.append(eachFileData);
							} else {
								maindata.append("\n").append(eachFileData);
							}
							i++;
						}
						/* Raw File Generation end */

						/* Settlement File Generation Start */
						settlementfilename = sourcepath + bankcode + "_"
								+ dateformat.format(now) + "_DAILYSETTLEMENT";
						logger.debug("Settlementfilename ["
								+ settlementfilename + "]");
						file = new File(settlementfilename + ".csv");
						writer1 = new FileWriter(file);
						filesToZip[1] = file.getName();

						headerdata
								.append("File Name,Type Of Account,Currency,Amount Of Settlement,Amount Of CashDeposit,");
						headerdata
								.append("Amount Of Cash Withdrawal,Commission Charged,Settlement Account Number,");
						headerdata.append("Settlement Identification Number");

						rawquery = "{call SETTLEMENTRAWDATA.pSettlementAmount(?,?,?)}";
						callableStmt2 = connection.prepareCall(rawquery);
						callableStmt2.setString(1, bankcode);
						callableStmt2.registerOutParameter(2,
								OracleTypes.VARCHAR);
						callableStmt2.registerOutParameter(3,
								OracleTypes.VARCHAR);

						callableStmt2.execute();

						logger.debug("Rights block executed successfully "
								+ "with error_message["
								+ callableStmt2.getString(2) + "] DATA["
								+ callableStmt2.getString(3) + "]");

						data = (callableStmt2.getString(3) == null ? " "
								: callableStmt2.getString(3));

						arrData = data.split("~");

						eachFileData = bankcode + "_" + dateformat.format(now)
								+ "_DAILYSETTLEMENT" + "," + arrData[1] + ","
								+ arrData[2] + "," + arrData[3] + ","
								+ arrData[4] + "," + arrData[5] + ","
								+ arrData[6] + "," + arrData[7] + "," + " ";
						headerdata.append("\n").append(eachFileData);

						/* Settlement File Generation end */
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
					}

					logger.debug("Data Loading in settlementfilename ["
							+ settlementfilename + " ]");
					logger.debug("EachFileData [" + maindata + "]");

					writer.append(maindata);
					writer.flush();
					writer.close();

					logger.debug("Headerdata [" + headerdata + "]");

					writer1.append(headerdata);
					writer1.flush();
					writer1.close();

					filesToZip[2] = sourcepath;
					filesToZip[3] = sourcepath + bankcode + ".zip";

					System.out.println(Arrays.asList(filesToZip));

					passwordProtectZipFile = new PasswordProtectZipFile();
					passwordProtectZipFile.setZip(filesToZip);

					maindata.delete(0, maindata.length());
					headerdata.delete(0, headerdata.length());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e);
			}

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(updatePstmt);
			DBUtils.closePreparedStatement(bankPstmt);
			DBUtils.closePreparedStatement(tmkpsmt);

			DBUtils.closeResultSet(tmkRS);
			DBUtils.closeResultSet(bankRS);

			try {
				if (writer != null) {
					writer.close();
				}
				if (writer1 != null) {
					writer1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			fileName = null;
			eachFileData = null;

			settlementfilename = null;
			rawfilename = null;
			sourcepath = null;
			bundle = null;
			bankcode = null;
			maindata = null;
			headerdata = null;
			file = null;
			arrData = null;
			data = null;
			filesToZip = null;
			passwordProtectZipFile = null;
			logger.debug("------- Settlement File Gen Ends-------");
		}
		return fileName;
	}

	public static void main(String[] args) {
		ResourceBundle bundle = ResourceBundle.getBundle("pathinfo_config");
		String sourcepath = bundle.getString("SETTLE_FILE_PATH");

		String xx = SettleDailyCsvFile();

		System.out.println(sourcepath);
	}
}