package com.ceva.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ceva.base.common.utils.DBConnector;

/**
 * A very simple program that writes some data to an Excel file
 * using the Apache POI library.
 * @author www.codejava.net
 *
 */
public class SimpleExcelWriterExample {


	public static boolean perform(Connection conn,String fname,String Query)
	{
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sheet sheet = null;
		boolean bool=false;
		FileOutputStream outputStream =null;
		try{
			pstmt = conn.prepareStatement(Query);
			rs = pstmt.executeQuery();
			int  sheetcount= 0;
			int rowCount=0;
			int maxcount=25000;
			sheet=workbook.createSheet("Sheet-"+sheetcount++);
			outputStream = new FileOutputStream(fname);
			long st= System.currentTimeMillis();
			while(rs.next())
			{
				
				if(maxcount==rowCount)
				{
					sheet=workbook.createSheet("Sheet-"+sheetcount++);
					System.out.println((maxcount*sheetcount)+" Records - Sheet"+sheetcount+" is done in["+(System.currentTimeMillis()-st)+"]ms");
					rowCount=0;
				}

				Row	row = sheet.createRow(rowCount++);		
				ResultSetMetaData rsmd = rs.getMetaData();
				int ccount =rsmd.getColumnCount();

				for (int i = 0; i < ccount; i++) {
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
				if (conn != null) 
				{
					conn.close();
					conn=null;
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
			System.out.println(perform(connection,"F:\\JavaBooks.xlsx","select ID,BATCHID,DEBITACCCOUNTNUMBER,DEBITAMOUNT from FUND_TRANSFER_TBL_051217"));

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
