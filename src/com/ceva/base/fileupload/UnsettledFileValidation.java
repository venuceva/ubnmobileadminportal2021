package com.ceva.base.fileupload;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ceva.unionbank.channel.SettlementServiceCall;
import com.ceva.util.CSVUtils;

public class UnsettledFileValidation {
	
	public static List<Map<String, String>> getData(String filename) {
		int line_no=0;
		String userid=null;
		String txnrefno=null;
		String extrefno=null;
		String servicecode=null;
		String txnamt=null;
		String channel=null;
		String action=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		try {
			
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			
        			userid = line.get(0).trim(); 
        			txnrefno = line.get(1).trim(); 
        			extrefno = line.get(2).trim(); 
        			servicecode = line.get(3).trim(); 
        			txnamt = line.get(4).trim(); 
        			channel = line.get(5).trim(); 
        			action = line.get(6).trim(); 
        			
        			map=new HashMap<String, String>();
        			
        			map.put("userid", (userid).replaceAll("\"", ""));
        			map.put("txnrefno", (txnrefno).replaceAll("\"", ""));
        			map.put("extrefno", (extrefno).replaceAll("\"", ""));
        			map.put("servicecode", (servicecode).replaceAll("\"", ""));
        			map.put("txnamt", (txnamt).replaceAll("\"", ""));
        			map.put("channel", (channel).replaceAll("\"", ""));
        			map.put("action", (action).replaceAll("\"", ""));
        			li.add(map);
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	public static List<Map<String, String>> getDataSuccrev(String filename) {
		int line_no=0;
		String userid=null;
		String txnrefno=null;
		String extrefno=null;
		String servicecode=null;
		String txnamt=null;
		String channel=null;
		String action=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		try {
			
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			
        			userid = line.get(0).trim(); 
        			txnrefno = line.get(1).trim(); 
        			txnamt = line.get(2).trim(); 
        			
        			
        			map=new HashMap<String, String>();
        			
        			map.put("userid", (userid).replaceAll("\"", ""));
        			map.put("txnrefno", (txnrefno).replaceAll("\"", ""));        			
        			map.put("txnamt", (txnamt).replaceAll("\"", ""));
        			
        			li.add(map);
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	public static List<Map<String, String>> getCampData(String filename) {
		int line_no=0;
		String userid=null;
		String txnrefno=null;
		String extrefno=null;
		String servicecode=null;
		String txnamt=null;
		String channel=null;
		String action=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		try {
			
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			
        			userid = line.get(0).trim(); 
        			
        			
        			map=new HashMap<String, String>();
        			
        			map.put("userid", (userid).replaceAll("\"", ""));
        			
        			li.add(map);
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	public static List<Map<String, String>> getDataPreEnrolment(String filename) {
		int line_no=0;
		String userid=null;
		String terminalmake=null;
		String extrefno=null;
		String servicecode=null;
		String modelno=null;
		String serailno=null;
		String action=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		try {
			
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			
        			userid = line.get(0).trim(); 
        			
        			
        			
        			map=new HashMap<String, String>();
        			
        			map.put("accountno", (userid).replaceAll("\"", ""));
        			
        			
        			li.add(map);
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	public static List<Map<String, String>> getDataTerminal(String filename) {
		int line_no=0;
		String userid=null;
		String terminalmake=null;
		String extrefno=null;
		String servicecode=null;
		String modelno=null;
		String serailno=null;
		String action=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		try {
			
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			
        			userid = line.get(0).trim(); 
        			terminalmake = line.get(1).trim(); 
        			modelno = line.get(2).trim(); 
        			serailno = line.get(3).trim();
        			
        			
        			map=new HashMap<String, String>();
        			
        			map.put("userid", (userid).replaceAll("\"", ""));
        			map.put("terminalmake", (terminalmake).replaceAll("\"", ""));        			
        			map.put("modelno", (modelno).replaceAll("\"", ""));
        			map.put("serailno", (serailno).replaceAll("\"", ""));
        			
        			li.add(map);
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	public static StringBuilder getUnsettlementDataValiadion(String filename,Connection conn) {
		int line_no=0;
		String userid=null;
		String txnrefno=null;
		String extrefno=null;
		String servicecode=null;
		String txnamt=null;
		String channel=null;
		String action=null;
		StringBuilder sb=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		
		
		try {
			sb=new StringBuilder();
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			boolean valdc=true;
        			userid = (line.get(0).trim()).replaceAll("\"", ""); 
        			txnrefno = (line.get(1).trim()).replaceAll("\"", "");  
        			extrefno = (line.get(2).trim()).replaceAll("\"", "");  
        			servicecode = (line.get(3).trim()).replaceAll("\"", ""); 
        			txnamt = (line.get(4).trim()).replaceAll("\"", "");  
        			channel = (line.get(5).trim()).replaceAll("\"", "");  
        			action = (line.get(6).trim()).replaceAll("\"", "");  
        			
        			if(userid.length()!=13) {
        				sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> Invalid User id <strong>"+userid+"</strong></td></tr>");
        				valdc=false;
        			}
        			if(!SettlementServiceCall.getTxnCodevalid(servicecode)) {
        				sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> Invalid Service Code <strong>"+servicecode+"</strong></td></tr>");
        				valdc=false;
        			}
        			
        			if(!SettlementServiceCall.getTxnCodeActionvalid(servicecode, action)) {
        				sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> Invalid Action Code <strong>"+action+"</strong> for Service <strong>"+servicecode+"</strong></td></tr>");
        				valdc=false;
        			}
        			
        			if(valdc) {
        				
        				boolean valdc1=true;
        			pstmt = conn.prepareStatement("select count(*) from FILE_UPLOAD_UNSETTLE where USER_ID=? and TXN_REFERNCE_NO=? and EXTREFERNCE_NO=?");
        			pstmt.setString(1, userid);
        			pstmt.setString(2, txnrefno);
        			pstmt.setString(3, extrefno);
        			rs = pstmt.executeQuery();
						if(rs.next()) {
							if(rs.getInt(1)!=0) {
								sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> <strong> already uploaded user id="+userid+",txn refno="+txnrefno+",ext refno="+extrefno+"</strong></td></tr>");
								valdc1=false;
							}
						}
					pstmt=null;
					rs=null;
					if(valdc1) {
						
					
					pstmt1 = conn.prepareStatement("select count(*)  from WALLET_UNSETTELMENT_TBL where EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL)  and EXTTXNREF not in ( select PAYMENTREFERENCE from WALLET_UNSETTLE_REQUEST)  and USERID=? AND TXNREF=? AND EXTTXNREF=? and SERVICECODE=?");
        			pstmt1.setString(1, userid);
        			pstmt1.setString(2, txnrefno);
        			pstmt1.setString(3, extrefno);
        			pstmt1.setString(4, servicecode);
        			rs1 = pstmt1.executeQuery();
						if(rs1.next()) {
							if(rs1.getInt(1)==0) {
								sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> <strong> this record not available for settlement : user id="+userid+",txn refno="+txnrefno+",ext refno="+extrefno+",servicecode="+servicecode+"</strong></td></tr>");
							}
						}
					pstmt1=null;
					rs1=null;
					}
					
        			}
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb;
		
	}
	
	
	public static StringBuilder getSuccessRevDataValiadion(String filename,Connection conn) {
		int line_no=0;
		String userid=null;
		String txnrefno=null;
		String extrefno=null;
		String servicecode=null;
		String txnamt=null;
		String channel=null;
		String action=null;
		StringBuilder sb=null;
		
		Map<String, String> map =null;
		List<Map<String, String>> li=null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		
		
		try {
			sb=new StringBuilder();
			li=new ArrayList<Map<String, String>>();
			
		Scanner scanner = new Scanner(new File(filename));				
		while (scanner.hasNext()) {	
			String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		
        		List<String> line = CSVUtils.parseLine(ldata,',','"');
        		line_no=line_no+1;
        		if(line_no > 1) {
        			boolean valdc=true;
        			userid = (line.get(0).trim()).replaceAll("\"", ""); 
        			txnrefno = (line.get(1).trim()).replaceAll("\"", "");  
        			txnamt = (line.get(2).trim()).replaceAll("\"", "");  
        			
        			if(userid.length()!=13) {
        				sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> Invalid User id <strong>"+userid+"</strong></td></tr>");
        				valdc=false;
        			}
        			
        			
        			if(valdc) {
        				
        				boolean valdc1=true;
        			pstmt = conn.prepareStatement("select count(*) from FILE_UPLOAD_SUCCREV where USER_ID=? and PAYMENT_REFERNCE_NO=?");
        			pstmt.setString(1, userid);
        			pstmt.setString(2, txnrefno);
        			rs = pstmt.executeQuery();
						if(rs.next()) {
							if(rs.getInt(1)!=0) {
								sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> <strong> already uploaded user id="+userid+",Payment Reference Number="+txnrefno+"</strong></td></tr>");
								valdc1=false;
							}
						}
					pstmt=null;
					rs=null;
					if(valdc1) {
						
					
					pstmt1 = conn.prepareStatement("select count(*)  from WALLET_FIN_TXN WFTP where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) AND USER_ID=? AND EXT_TXN_REF_NO=? AND TXN_REF_NO not like 'R%'");
        			pstmt1.setString(1, userid);
        			pstmt1.setString(2, txnrefno);
        			
        			rs1 = pstmt1.executeQuery();
						if(rs1.next()) {
							if(rs1.getInt(1)==0) {
								sb.append("<tr><td>Line Number <strong>"+line_no+"</strong> <strong> this record not available for  user id="+userid+",Payment Reference Number="+txnrefno+"</strong></td></tr>");
							}
						}
					pstmt1=null;
					rs1=null;
					}
					
        			}
        			
        		}
        		
        	}
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb;
		
	}
	
	public static void main(String[] args) {
		UnsettledFileValidation ufv=new UnsettledFileValidation();
		
		System.out.println(ufv.getData("D:\\Reports1\\15082019_Unsettlement2267.csv"));
	}

}
