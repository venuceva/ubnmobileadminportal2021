package com.ceva.base.reports.statement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.reports.statement.model.Accountinfostmnt;
import com.ceva.base.reports.statement.model.Accountstatementdetails;
import com.ceva.util.DBUtils;


/**
 * @author Sravana Kumar Bevara
 */

public class StatementReportDao {

	private static Logger logger = Logger.getLogger(StatementReportDao.class);

	public static String getCurrentDate()
	{

		String currentDate = "";

		try {

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			currentDate = sdf.format(cal.getTime());
			System.out.println("After formatting: " + currentDate);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return currentDate;

	}

	public static void getStatementData(String mobileno , String type , String fromdate , String todate , Accountinfostmnt statementObj)
	{

		Connection connection = null;
		String entityQry="";
		String accountno="";
		ResultSet searchRS = null;
		PreparedStatement pstmt = null;

		try
		{
			connection = DBConnector.getConnection();
			logger.info("Connection is null [" + connection == null + "]");
			logger.info("Mobileno |"+mobileno+"|from date|"+fromdate+"|to date|"+todate+"|wallet type|"+type);

			statementObj.setStartdate(fromdate);
			statementObj.setEnddate(todate);
			statementObj.setPrintdate( getCurrentDate() );

			entityQry = "select ACCT_NO,upper(ACM.FIRST_NAME),NVL(upper(MCI.ADDRESS),' '),NVL(upper(MCI.R_STATE),' '),NVL(upper(MCI.RL_LGA),' '),"
					+ "NVL(upper(MCI.COUNTRY),' '),(select CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID=MD.BRANCH_CODE) "
					+ "from WALLET_ACCT_DATA MD,AGENT_CONTACT_INFO MCI,AGENT_CUSTOMER_MASTER ACM where ACM.ID=MCI.CUST_ID AND"
					+ " MD.CUST_ID=MCI.CUST_ID AND PRIM_FLAG='P' AND MOBILE_NUMBER='234"+mobileno+"'";

			logger.info("STMT1|AGN-MOBNO|"+mobileno+"| Fetch Agent Details|"+entityQry);


			pstmt = connection.prepareStatement(entityQry);
			searchRS = pstmt.executeQuery();
			while (searchRS.next()) {

				accountno=searchRS.getString(1);

				statementObj.setAddress(searchRS.getString(2));
				statementObj.setAddress1( searchRS.getString(3)+" "+searchRS.getString(4) );
				statementObj.setAddress2( searchRS.getString(5)+" "+searchRS.getString(6)  );
				statementObj.setAccounttype( "Agent" );
				statementObj.setBranchname( searchRS.getString(7) );

			}

			statementObj.setAccountno(accountno);

			String maccountno=accountno;
			if(type.equalsIgnoreCase("AGENTCOMM")) {
				accountno="0"+accountno;
			}

			statementObj.setCurrency(  "NGN" );
			pstmt.close();
			searchRS.close();


			entityQry="select TO_CHAR(SUM(decode(DRCR_FLAG,'C',AMOUNT,0)),'999,999,999.99'),TO_CHAR(SUM(decode(DRCR_FLAG,'D',AMOUNT,0)),'999,999,999.99')"
					+ ",COUNT(decode(DRCR_FLAG,'D',AMOUNT)),COUNT(decode(DRCR_FLAG,'C',AMOUNT)),TO_CHAR((SUM(decode(DRCR_FLAG,'C',AMOUNT,0))-SUM(decode(DRCR_FLAG,'D',AMOUNT,0))),'999,999,999.99') "
					+ " as totbamt from WALLET_FIN_TXN_POSTING where ACCOUNT in ('"+accountno+"') AND TRUNC(TXN_STAMP) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') "
					+ "AND TXN_TYPE in ('T','F')";

			logger.info("STMT2|AGN-MOBNO|"+mobileno+"|Agent Cumilative trans Details Query|"+entityQry);

			pstmt = connection.prepareStatement(entityQry);
			searchRS = pstmt.executeQuery();

			while (searchRS.next()) {
				statementObj.setTotaldebit(  searchRS.getString(1) );
				statementObj.setTotalcredit( searchRS.getString(2) );
				statementObj.setDrcount( searchRS.getString(3) );
				statementObj.setCrcount( searchRS.getString(4) );
				statementObj.setTotbalamt(searchRS.getString(5));
			}

			pstmt.close();
			searchRS.close();


			pstmt = connection.prepareStatement("select TO_CHAR(sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)),'999,999,999.99'),"
					+ "to_char(sysdate,'dd/mm/yyyy') from wallet_fin_txn_posting wftp where wftp.ACCOUNT='"+maccountno+"' "
					+ "and TRUNC(TXN_STAMP)<=to_date('"+fromdate+"','dd/mm/yyyy')-1");

			searchRS=pstmt.executeQuery();

			if (searchRS.next()) {

				statementObj.setOpeningbalnce( searchRS.getString(1) );
				statementObj.setSelecteddate( searchRS.getString(2) );

			}

			pstmt.close();
			searchRS.close();

			pstmt = connection.prepareStatement("select TO_CHAR(sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)),'999,999,999.99') "
					+ "from wallet_fin_txn_posting wftp where wftp.ACCOUNT='"+maccountno+"' and TRUNC(TXN_STAMP)<=to_date('"+todate+"','dd/mm/yyyy')");
			searchRS=pstmt.executeQuery();

			if (searchRS.next()) {

				statementObj.setClosingbalance( searchRS.getString(1) );

			}

			pstmt.close();
			searchRS.close();

			statementObj.setTotalcommission("0");

			addTransDetails(connection, pstmt, searchRS, statementObj, maccountno, fromdate, todate);

			logger.info("STMT2|AGN-MOBNO|"+mobileno+"| Statement Obj|" + statementObj.toString() );


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			DBUtils.closeConnection(connection);
		}

	} 


	public static void addTransDetails ( Connection connection , PreparedStatement pstmt , ResultSet searchRS  ,
			Accountinfostmnt statementObj , String accountno ,String fromdate , String todate )
	{

		try {

			List<Accountstatementdetails> list = new ArrayList<Accountstatementdetails>();

			Accountstatementdetails actStmtDetilsObj = null ;

			String query = "select NARRATION"
					// + "||' '||(select NVL(json_value(JREQUEST,'$.jbody.extrainfo.cardinfo'),' ') from WALLET_FIN_TXN where TXN_REF_NO=SS.TXN_REF_NO)"
					+ " as NARRATION,EXT_TXN_REF_NO as TXN_REF_NO,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh24:mi:ss') as val_date "
					+ ",NVL(decode(DRCR_FLAG,'D',TO_CHAR(AMOUNT,'999,999,999.99')),' ') as debitamt,NVL(decode(DRCR_FLAG,'C',TO_CHAR(AMOUNT,'999,999,999.99')),' ') "
					+ "as creditamt,NVL(CUR_BALANCE,0) as balance,(select SERVICEDESC from BANK_SERVICE_CODE_MASTER where SERVICECODE=SS.SERVICECODE)||decode(TXN_TYPE,'F',"
					+ "' Commission',' ') as servicecode "
					+ "from WALLET_FIN_TXN_POSTING SS where ACCOUNT in ('"+accountno+ "') "
					+ " AND TRUNC(TXN_STAMP) between to_date('"+ fromdate +"','dd/mm/yyyy') "
					+ " and to_date('"+ todate +"','dd/mm/yyyy') order by TXNID ";


			logger.info( "STMT2|AGN-ACTNO|"+ accountno +"|fromdate|"+fromdate+"|todate|" + todate + "| Transction Query |" + query );

			pstmt = connection.prepareStatement( query );
			searchRS = pstmt.executeQuery();


			while (searchRS.next()) {

				actStmtDetilsObj = new Accountstatementdetails();
				actStmtDetilsObj.setTransactiondetails( searchRS.getString(1));
				actStmtDetilsObj.setReferencenumber( searchRS.getString(2) );
				actStmtDetilsObj.setValuedate( searchRS.getString(3) );
				actStmtDetilsObj.setDebit( searchRS.getString(4) );
				actStmtDetilsObj.setCredit(searchRS.getString(5)  );
				actStmtDetilsObj.setBalance( searchRS.getString(6) );
				actStmtDetilsObj.setTransactiontype(  searchRS.getString(7) );

				logger.info("actStmtDetilsObj Obj|" + actStmtDetilsObj.toString());
				list.add(actStmtDetilsObj);

			}

			statementObj.setList(list);



		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {

			try {

				pstmt.close();
				searchRS.close();

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}


	}


	public static void main(String[] args) {

		try {

			String mobileno = "8152190597" , type = "Agent" , fromdate= "01/01/2021" , todate= "30/09/2021" ;

			Accountinfostmnt statementObj = new Accountinfostmnt();

			//getStatementData( mobileno, type, fromdate, todate , statementObj);

			System.out.println(getCurrentDate());
			System.out.println("hi sravan");

		}catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();

		}

	}

}
