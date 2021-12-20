package com.ceva.base.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ceva.base.common.dao.ReportsDAO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.reports.statement.StatementReportGenaration;
import com.ceva.base.reports.statement.StatementReportService;
import com.ceva.util.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class StatementDownloadAction  extends ActionSupport{
	
	private static Logger logger = Logger.getLogger(DownloadAction.class);
 
	private InputStream inputStream;
    private String fileName;
    private String fromdate;
    private String todate;
    private String branchid;
    private String type;
    
    
    
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	private long contentLength;
    
    String result=SUCCESS;
    
	public String executetest()
	{
		Connection connection = null;
		String pdffilename="";
		StringBuilder sb=new StringBuilder();
		boolean reportresult=true;
		String entityQry="";
		String accountno="";
		ResultSet searchRS = null;
		PreparedStatement pstmt = null;
		ResourceBundle rb = ResourceBundle.getBundle("configurations");
		try
		{
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			System.out.println("  Statement Start  "+branchid);
			System.out.println("from date :: "+fromdate);
			System.out.println("to date :: "+todate);
			System.out.println("BranchCode :: "+branchid);
			System.out.println("wallet type :: "+type);
			
			ReportGeneration rg=new ReportGeneration();
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			/*if(type.equalsIgnoreCase("AGENT")) {*/
				entityQry = "select ACCT_NO,upper(ACM.FIRST_NAME),NVL(upper(MCI.ADDRESS),' '),NVL(upper(MCI.R_STATE),' '),NVL(upper(MCI.RL_LGA),' '),NVL(upper(MCI.COUNTRY),' '),(select CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID=MD.BRANCH_CODE) from WALLET_ACCT_DATA MD,AGENT_CONTACT_INFO MCI,AGENT_CUSTOMER_MASTER ACM where ACM.ID=MCI.CUST_ID AND MD.CUST_ID=MCI.CUST_ID AND PRIM_FLAG='P' AND MOBILE_NUMBER='234"+branchid+"'";
				System.out.println("  Statement Start entityQry1 "+entityQry);
				pstmt = connection.prepareStatement(entityQry);
				searchRS = pstmt.executeQuery();
				while (searchRS.next()) {
					accountno=searchRS.getString(1);
					parameters.put("Address", searchRS.getString(2));
			          parameters.put("Address1", searchRS.getString(3)+" "+searchRS.getString(4));
			          parameters.put("Address2", searchRS.getString(5)+" "+searchRS.getString(6));
			          parameters.put("account_type", "Agent");
			          parameters.put("branch_Name",searchRS.getString(7));
				}
			
			parameters.put("accountno", accountno);
			String maccountno=accountno;
			if(type.equalsIgnoreCase("AGENTCOMM")) {
				accountno="0"+accountno;
			}
			
			parameters.put("currency", "NGN");
			
			pstmt.close();
			searchRS.close();
			
			sb.append("select NARRATION||' '||(select NVL(json_value(JREQUEST,'$.jbody.extrainfo.cardinfo'),' ') from WALLET_FIN_TXN where TXN_REF_NO=SS.TXN_REF_NO) as NARRATION,EXT_TXN_REF_NO as TXN_REF_NO,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh24:mi:ss') as val_date,NVL(decode(DRCR_FLAG,'D',TO_CHAR(AMOUNT,'999,999,999.99')),' ') as debitamt,NVL(decode(DRCR_FLAG,'C',TO_CHAR(AMOUNT,'999,999,999.99')),' ') as creditamt,NVL(CUR_BALANCE,0) as balance,(select SERVICEDESC from BANK_SERVICE_CODE_MASTER where SERVICECODE=SS.SERVICECODE)||decode(TXN_TYPE,'F',' Commission',' ') as servicecode from WALLET_FIN_TXN_POSTING SS where ACCOUNT in ('"+accountno+"') ");
			sb.append(" AND TRUNC(TXN_STAMP) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') order by TXNID");
			System.out.println(sb.toString());
			parameters.put("QUERY",sb.toString());
			parameters.put("from_date", fromdate);
			parameters.put("to_date", todate);
			
			
			entityQry="select TO_CHAR(SUM(decode(DRCR_FLAG,'C',AMOUNT,0)),'999,999,999.99'),TO_CHAR(SUM(decode(DRCR_FLAG,'D',AMOUNT,0)),'999,999,999.99'),COUNT(decode(DRCR_FLAG,'D',AMOUNT)),COUNT(decode(DRCR_FLAG,'C',AMOUNT)),TO_CHAR((SUM(decode(DRCR_FLAG,'C',AMOUNT,0))-SUM(decode(DRCR_FLAG,'D',AMOUNT,0))),'999,999,999.99') as totbamt from WALLET_FIN_TXN_POSTING where ACCOUNT in ('"+accountno+"') AND TRUNC(TXN_STAMP) between to_date('"+fromdate+"','dd/mm/yyyy') and to_date('"+todate+"','dd/mm/yyyy') AND TXN_TYPE in ('T','F')";
			System.out.println("  Statement Start entityQry4 "+entityQry);
			pstmt = connection.prepareStatement(entityQry);
			searchRS = pstmt.executeQuery();
			while (searchRS.next()) {
				parameters.put("debittotal", searchRS.getString(1));
				parameters.put("credittotal", searchRS.getString(2));
				parameters.put("drcnt", searchRS.getString(3));
				parameters.put("crcnt", searchRS.getString(4));
				parameters.put("totbamt", searchRS.getString(5));
			}
			
			
			pstmt.close();
			searchRS.close();
			
			pstmt = connection.prepareStatement("select TO_CHAR(sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)),'999,999,999.99'),to_char(sysdate,'dd/mm/yyyy') from wallet_fin_txn_posting wftp where wftp.ACCOUNT='"+maccountno+"' and TRUNC(TXN_STAMP)<=to_date('"+fromdate+"','dd/mm/yyyy')-1");
			searchRS=pstmt.executeQuery();
			
			if (searchRS.next()) {
				parameters.put("open_balance", searchRS.getString(1));
				parameters.put("SELECT_DATE", searchRS.getString(2));
			}
			
			pstmt.close();
			searchRS.close();
			
			pstmt = connection.prepareStatement("select TO_CHAR(sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)),'999,999,999.99') from wallet_fin_txn_posting wftp where wftp.ACCOUNT='"+maccountno+"' and TRUNC(TXN_STAMP)<=to_date('"+todate+"','dd/mm/yyyy')");
			searchRS=pstmt.executeQuery();
			
			if (searchRS.next()) {
				parameters.put("closedamt", searchRS.getString(1));
				
			}
			
			pstmt.close();
			searchRS.close();
			parameters.put("totalcommission", "0");
			
			
			parameters.put("FOOTER_IMAGE",rb.getString("JRXMLSOURCE")+"btm.jpg");
			//parameters.put("branch_Name","009");
			
			rg.GeneratePdfReportWithJasperReports(connection,parameters,"wallet_account statement","wallet_account statement","pdf");
			
			
			String pathx = rb.getString("OUT_PATH");
			File fileToDownload = new File(pathx+"/wallet_account statement.pdf");
			System.out.println("  Statement end  ");
			
			if(fileToDownload.exists() && !fileToDownload.isDirectory() && reportresult) //&& fileToDownload.isFile())
			{
				try
				{
					inputStream = new FileInputStream(fileToDownload);
			        fileName = fileToDownload.getName();
			        contentLength = fileToDownload.length();

			        result = SUCCESS;
				}
				catch(Exception e)
				{
					result="fail";
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			result="fail";
			e.printStackTrace();
		}
		 finally {
				DBUtils.closeConnection(connection);
			}
		return result;
	}
	
	
	public String execute()
	{
		Connection connection = null;
		String pdffilename="";
		StringBuilder sb=new StringBuilder();
		boolean reportresult=true;
		String entityQry="";
		String accountno="";
		ResultSet searchRS = null;
		PreparedStatement pstmt = null;
		ResourceBundle rb = ResourceBundle.getBundle("configurations");
		try
		{
			connection = DBConnector.getConnection();
			logger.debug("Connection is null [" + connection == null + "]");
			System.out.println("  Statement Start  "+branchid);
			System.out.println("from date :: "+fromdate);
			System.out.println("to date :: "+todate);
			System.out.println("BranchCode :: "+branchid);
			System.out.println("wallet type :: "+type);
			
			StatementReportService.genarateStatement( branchid , "Agent", fromdate , todate );
			
			String pathx = rb.getString("OUT_PATH");
			File fileToDownload = new File(pathx+StatementReportGenaration.STATEMENT_TEMPL_NAME+"_"+ branchid +".pdf");
			System.out.println("  Statement end  ");
			
			if(fileToDownload.exists() && !fileToDownload.isDirectory() && reportresult) //&& fileToDownload.isFile())
			{
				try
				{
					inputStream = new FileInputStream(fileToDownload);
			        fileName = fileToDownload.getName();
			        contentLength = fileToDownload.length();

			        result = SUCCESS;
				}
				catch(Exception e)
				{
					result="fail";
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			result="fail";
			e.printStackTrace();
		}
		 finally {
				DBUtils.closeConnection(connection);
			}
		return result;
	}
	
}
