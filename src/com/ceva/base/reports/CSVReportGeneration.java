package com.ceva.base.reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CSVReportGeneration {
	
	private static int FLUSH_COUNT=2000;
	public static boolean perform(String fname,Connection conn,String Query,String headval)
	{
		boolean bool=false;
		ResourceBundle rb = ResourceBundle.getBundle("configurations");
		String [] hcolumns = null;
		try {
			com.opencsv.CSVWriter write = new com.opencsv.CSVWriter(new FileWriter(new File(rb.getString("OUT_PATH")+""+fname+".csv")));

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				long stime = System.currentTimeMillis();
				List<String[]> hrecords = new ArrayList<String[]>();
				//String Query = 	"select ' ' as primaryacc,TELEPHONE as mobno,firstname||' '||middlename||' '||lastname as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),' ' as custid,' ' as balance,RESPONSEMESSAGE,' ' as USERID ,' ' as sidate  from account_opeN  WHERE  ACCOUNT_TYPE='NEW' AND RESPONSECODE='0'  AND TRUNC(TRANS_DATE) between to_date('01/01/2017','dd/mm/yyyy') and to_date('02/12/2017','dd/mm/yyyy') ORDER BY TRANS_DATE DESC";
				pstmt = conn.prepareStatement(Query);
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				if(headval.equalsIgnoreCase("")){
					hcolumns = new String[rsmd.getColumnCount()];
					for (int j = 0; j <rsmd.getColumnCount(); j++ ) {
						hcolumns[j]=rsmd.getColumnName(j+1);
						System.out.println(rsmd.getColumnName(j+1));
						}
				}else{
					String Headerstr[]=headval.split(",");
					hcolumns = new String[Headerstr.length];
					for (int j = 0; j <Headerstr.length; j++ ) {
						System.out.println(Headerstr[j]);
						hcolumns[j]=Headerstr[j];
					}
				}
				hrecords.add(hcolumns);
				write.writeAll(hrecords);
				write.flush();
				List<String[]> records = new ArrayList<String[]>();
				int count=1;
				long tcount=0L;
				long cyclecount=0L;
				System.out.println("Total Number of records ["+rs.getFetchSize()+"|"+rs.getRow()+"]");
				
				
				while (rs.next()) {
					
					String [] columns = new String[rsmd.getColumnCount()];// here i have kept 3 because i have 3 columns;
					for (int i = 0; i <rsmd.getColumnCount(); i++ ) {
						columns[i]=rs.getString(rsmd.getColumnName(i+1));
					}
					
					records.add(columns);

					if(count==FLUSH_COUNT)
					{
						//System.out.println("FLUSH_COUNT :: "+count);
						cyclecount++;
						write.writeAll(records);
						write.flush();
						count=0;
						records.clear();
					}

					count++;
					tcount++;
				}
				if(count>0)
				{
					cyclecount++;
				}
				// this is for rest....
				write.writeAll(records);
				write.flush();
				count=0;
				if(records !=null)
				{
					records.clear();
					records=null;
				}
				
				System.out.println("Total Number of records created ["+tcount+"]\nNumber of Cycle taken ["+cyclecount+"]");
				System.out.println("Time take to generate File @ Flush Count "+FLUSH_COUNT+" is ["+(System.currentTimeMillis()-stime)+"]");
				bool=true;

			} catch (SQLException ex) {

				ex.printStackTrace();
			}
			finally
			{
				write.close();
				
				try 
				{
					
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
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bool;
	}

}
