package com.ceva.base.reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ceva.base.common.utils.DBConnector;

public class ExcelReportGeneration {
	

	public static boolean perform(String fname,Connection conn,String Query,String headval)
	{
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sheet sheet = null;
		boolean bool=false;
		ResourceBundle rb = ResourceBundle.getBundle("configurations");
		FileOutputStream outputStream =null;
		try{
			pstmt = conn.prepareStatement(Query);
			rs = pstmt.executeQuery();
			int  sheetcount= 0;
			int rowCount=1;
			int maxcount=900000;
			sheet=workbook.createSheet("Sheet-"+sheetcount++);
			//System.out.println("filename ::: "+rb.getString("OUT_PATH")+""+fname+".xlsx");
			outputStream = new FileOutputStream(rb.getString("OUT_PATH")+""+fname+".xlsx");
			//long st= System.currentTimeMillis();
			ResultSetMetaData rsmd = rs.getMetaData();
			Row	row =null;
		String Headerstr[]=headval.split(",");
		
		row = sheet.createRow(0);
		if(headval.equalsIgnoreCase("")){
			for (int j = 0; j < rsmd.getColumnCount(); j++) {
				row.createCell(j).setCellValue(""+rsmd.getColumnName((j+1)));
			}
		}else{
			for (int j = 0; j < Headerstr.length; j++) {
				row.createCell(j).setCellValue(Headerstr[j]);
				
			}
		}
		
		
		
			while(rs.next())
			{
				
				if(maxcount==rowCount)
				{
					row = sheet.createRow(0);
					if(headval.equalsIgnoreCase("")){
						for (int j = 0; j < rsmd.getColumnCount(); j++) {
							row.createCell(j).setCellValue(""+rsmd.getColumnName((j+1)));
						}
					}else{
						for (int j = 0; j < Headerstr.length; j++) {
							row.createCell(j).setCellValue(Headerstr[j]);
							
						}
					}
					
					sheet=workbook.createSheet("Sheet-"+sheetcount++);
					
					//System.out.println((maxcount*sheetcount)+" Records - Sheet"+sheetcount+" is done in["+(System.currentTimeMillis()-st)+"]ms");
					row = sheet.createRow(0);
					if(headval.equalsIgnoreCase("")){
						for (int j = 0; j < rsmd.getColumnCount(); j++) {
							row.createCell(j).setCellValue(""+rsmd.getColumnName((j+1)));
							
						}
					}else{
						for (int j = 0; j < Headerstr.length; j++) {
							row.createCell(j).setCellValue(Headerstr[j]);
							
						}
					}
					
					rowCount=1;
				}
				
				
				
				row = sheet.createRow(rowCount++);
					
				//ResultSetMetaData rsmd = rs.getMetaData();
				//int ccount =rsmd.getColumnCount();

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					row.createCell(i).setCellValue(""+rs.getString((i+1)));
				}
			}
			workbook.write(outputStream);
			bool=true;

		}catch(Exception e){
			e.printStackTrace();
			bool=false;
		}finally{
			if(outputStream !=null)
			{
				try {
					outputStream.close();
					outputStream=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try{
				if (rs != null) 
				{
					rs.close();
					rs=null;
				}
				if (pstmt != null) 
				{
					pstmt.close();
					pstmt=null;
				}
				
				

			}catch(Exception ee){
				ee.printStackTrace();
			}
		}

		return bool;

	}
	
	
	public static void main(String[] args) throws IOException {
		Connection connection = null;
		try{
			connection=DBConnector.getConnection(); 
			System.out.println(perform("JavaBooks",connection,"select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','N','Paritial Registion','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,MCF.AUTH_ID as AUTHID,decode(MCM.STATUS,'A','Using Mobile and USSD','U','Using Only USSD','N','Paritial Registion on USSD - Transaction pin not created','L','User Locked') as DETAILS,trunc(MCM.DATE_CREATED) as tdate  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P' AND TRUNC(MCM.DATE_CREATED) between to_date('01/01/2017','dd/mm/yyyy') and to_date('24/12/2017','dd/mm/yyyy') AND MCM.STATUS in ('A','U') AND UPPER(MCM.M_PRD_CODE) =UPPER('MOBPRD') ORDER BY tdate DESC","Customer Code,Name,User Id,Mobile Number,Mail Id,Status,Onboard Date,Product Code,Primary Account Number,Branch Code,registration,Details,tdate"));

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
