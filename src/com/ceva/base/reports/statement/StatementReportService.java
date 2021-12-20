package com.ceva.base.reports.statement;

import com.ceva.base.reports.statement.model.Accountinfostmnt;

public class StatementReportService {

	
	public static String genarateStatement( String mobileno , String type , String fromdate , String todate )
	{
		
		try {

			Accountinfostmnt statementObj = new Accountinfostmnt();

			StatementReportDao.getStatementData( mobileno, type, fromdate, todate , statementObj);

			System.out.println("Accountinfostmnt object |" + statementObj.toString() );

			StatementReportGenaration.genarateStatement(statementObj , mobileno );

			System.out.println("After Genarating pdf");


		} catch (Exception e) {
			System.out.println("Error while converting HTML to PDF " + e.getMessage());
			e.printStackTrace();
		}
		
		return "success";
		
	}
	
}
