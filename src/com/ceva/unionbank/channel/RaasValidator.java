package com.ceva.unionbank.channel;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
/**
 * 
 * @author Rajkumar Pandey
 *
 */
public class RaasValidator{

	public static  Bean validateFile(String filePath)
	{
		List<String[]> response= null;
		List<String[]> errorlines= new ArrayList<>();
		List<String[]> correctlines= new ArrayList<>();
		BufferedReader br = null;
		Bean bean =new RaasValidator().new Bean();
		try {
			int i=0;
			boolean isProper=true;
			CSVReader reader = new CSVReader(new FileReader(filePath));
			List<String[]> records = reader.readAll();

			for (String [] record : records) {
				
				if(i==0)
				{
					//POSTDATE,VALDATE,raas_tx_ref,RRN,RRN 2,DETAILS, AMOUNT ,TNX TYPE
					String [] headers = record;

					if(     "POSTDATE".equals(removeSpace(headers[0].toUpperCase()))
							&& 	"VALDATE".equals(removeSpace(headers[1].toUpperCase()))
							&& 	"RAAS_TX_REF".equals(removeSpace(headers[2].toUpperCase()))
							&& 	"RRN".equals(removeSpace(headers[3].toUpperCase()))
							&& 	"RRN2".equals(removeSpace(headers[4].toUpperCase()))
							&&  "DETAILS".equals(removeSpace(headers[5].toUpperCase()))
							&&  "AMOUNT".equals(removeSpace(headers[6].toUpperCase()))
							&&  "TNXTYPE".equals(removeSpace(headers[7].toUpperCase()))
							)
					{
						//System.out.println("Header order and header validation is ok");
					}
					else
					{
						System.out.println("The Header should be in the below order and with the below naming convention"
								+ "\n[header are case insensitive and spaces are allowed in between]");
					//	System.out.println("POSTDATE,VALDATE,RAAS_TX_REF,RRN,RRN2,DETAILS, AMOUNT ,TNXTYPE");
						record[record.length]=""+i;
						errorlines.add(record);
						bean.setError(true);
						bean.setRecords(errorlines);
						return bean;
					}
				}
				else
				{
					if(record.length==8)
					{
						if(isProper)
						{
							/*StringBuilder sb = new StringBuilder();
							for (int j = 0; j < record.length; j++) {
								sb.append(record [j]).append(",");
							}*/
							//record[record.length]=""+i;
							correctlines.add(record);
							//System.out.println("correct records:"+sb.deleteCharAt(sb.length()-1));
						}
						else
						{
							if(correctlines!=null)
							{
								correctlines.clear();
								correctlines=null;
							}
						}
					}
					else
					{
						isProper=false;
						String []error= new String[record.length+1];
						error[0]=""+i;

						for (int j = 0; j < record.length; j++) {
							error[j+1]=record[j];
						}
						errorlines.add(error);
						/*StringBuilder sb = new StringBuilder();
						for (int j = 0; j < record.length; j++) {
							sb.append(record [j]).append(",");
						}
						System.out.println("issue line: "+sb.deleteCharAt(sb.length()-1));*/
					}
					/*if(i==20)
					{
						break;
					}*/
				}
				
				if(isProper)
				{
					response=correctlines;
				}
				else
				{
					response=errorlines;
				}
				reader.close();
				
				bean.setError(!isProper);
				bean.setRecords(response);
				i++;
			}


		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(br!=null)
				{
					br.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bean;
	}

	private static String removeSpace(String str)
	{
		return str.replaceAll(" ", "");
	}

	public static void main(String[] args) {
		Bean bean =validateFile("D://kailash//projects//UnionBank//janaki//RAAS.csv");
		List<String[]> records =bean.getRecords();
		//System.out.println("Number of Records :"+records.size());
		for(String [] record : records)
		{
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < record.length; j++) {
				sb.append(record [j]).append(",");
			}
			if(bean.isError)
			{
				
				System.out.println("issue line no:["+(Integer.parseInt(record[0])+1)+"] Expected column Count [8] Received column Count ["+(record.length-1)+"]\n"+sb.deleteCharAt(sb.length()-1));
			}
			else
			{
				System.out.println("Kailash "+record [5]);
				//System.out.println("correct records:"+sb.deleteCharAt(sb.length()-1));
			}
			
			sb=null;
		}
		
	}
	
	public static String getRecords(String filepath){
		String totcount="0";
		try{
			CSVReader reader = new CSVReader(new FileReader(filepath));
			reader.readAll();
			totcount=""+reader.getLinesRead();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return totcount;
	}
	
	
	public static String getFileInformation(String filepath){
		Bean bean =validateFile(filepath);
		List<String[]> records =bean.getRecords();
		StringBuilder sba = new StringBuilder();
		if(bean.isError){
			sba.append("<tr><td>Number of Records Failed :"+records.size()+"</td><input type='hidden' id='result' value='fail' /></tr>");	
		}else{
			sba.append("<tr><td>Number of Records Success :"+records.size()+"</td><input type='hidden' id='result' value='success' /></tr>");
		}
		sba.append('\n');
		for(String [] record : records)
		{
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < record.length; j++) {
				sb.append(record [j]).append(",");
			}
			if(bean.isError)
			{
				sba.append("<tr><td>issue line no:["+(Integer.parseInt(record[0])+1)+"] Expected column Count [8] Received column Count ["+(record.length-1)+"]\n"+sb.deleteCharAt(sb.length()-1)+"</td></tr>");
				
				//System.out.println("issue line no:["+(Integer.parseInt(record[0])+1)+"] Expected column Count [8] Received column Count ["+(record.length-1)+"]\n"+sb.deleteCharAt(sb.length()-1));
				bean.setStatus("File Processing Result : Fail");
			}else{
				bean.setStatus("File Processing Result : Success");	
			}
			
			sb=null;	
			
		}
		sba.append("<tr><td>"+bean.getStatus()+"</td></tr>");
		return sba.toString();
	}
	
	class Bean
	{
		private boolean isError;
		private List<String[]> records;
		private String status=null;
		public boolean isError() {
			return isError;
		}
		public void setError(boolean isError) {
			this.isError = isError;
		}
		public List<String[]> getRecords() {
			return records;
		}
		public void setRecords(List<String[]> records) {
			this.records = records;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}

}

