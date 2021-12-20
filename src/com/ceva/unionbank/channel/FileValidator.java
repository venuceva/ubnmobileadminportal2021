package com.ceva.unionbank.channel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ceva.base.fileupload.UnsettledFileValidation;
import com.opencsv.CSVReader;

import net.sf.json.JSONException;

public class FileValidator {


	public static Bean validateFile(String filePath, String fcode) {
		List<String[]> response = null;
		List<String[]> errorlines = new ArrayList<>();
		List<String[]> correctlines = new ArrayList<>();
		BufferedReader br = null;
		Bean bean = new FileValidator().new Bean();
		ResourceBundle rb = null;
		int flength = 0;
		boolean hflag = true;
		try {
			int i = 0;
			boolean isProper = true;
			CSVReader reader = new CSVReader(new FileReader(filePath));
			List<String[]> records = reader.readAll();
			rb = ResourceBundle.getBundle("configurations");
			System.out.println("Kailash here :: " + fcode);
			flength = Integer.parseInt(rb.getString(fcode + "_FIELD_CNT"));
			String[] cheader = rb.getString(fcode).split(",");
			System.out.println("cheader length->" + cheader.length);

			for (String[] record : records) {

				if (i == 0) {
					// POSTDATE,VALDATE,raas_tx_ref,RRN,RRN 2,DETAILS, AMOUNT
					// ,TNX TYPE
					String[] headers = record;

					/*
					 * if(
					 * "USERID".equals(removeSpace(headers[0].toUpperCase())) &&
					 * "GROUP_CODE".equals(removeSpace(headers[1].toUpperCase())
					 * ) &&
					 * "BRANCHCODE".equals(removeSpace(headers[2].toUpperCase())
					 * ) ) { System.out.
					 * println("Header order and header validation is ok"); }
					 * else { System.out.
					 * println("The Header should be in the below order and with the below naming convention"
					 * +
					 * "\n[header are case insensitive and spaces are allowed in between]"
					 * ); // System.out.
					 * println("POSTDATE,VALDATE,RAAS_TX_REF,RRN,RRN2,DETAILS, AMOUNT ,TNXTYPE"
					 * ); record[record.length]=""+i; errorlines.add(record);
					 * bean.setError(true); bean.setRecords(errorlines); return
					 * bean; }
					 */

					for (int j = 0; j < cheader.length; j++) {
						if (!cheader[j].equals(removeSpace(headers[j].toUpperCase()))) {
							System.out.println("Header order and header validation is false :: " + cheader[j]);
							hflag = false;
						}
					}

					if (!hflag) {
						/*
						 * System.out.
						 * println("The Header should be in the below order and with the below naming convention"
						 * +
						 * "\n[header are case insensitive and spaces are allowed in between]"
						 * );
						 * System.out.println("record length->"+record.length);
						 */
						// record[record.length-1]=""+i;
						record[0] = "The Header should be in the below order and with the below naming convention "
								+ "\\n[" + rb.getString(fcode) + "]";
						errorlines.add(record);
						bean.setError(true);
						bean.setRecords(errorlines);
						return bean;
					}

				} else {
					if (record.length == flength) {
						if (isProper) {
							/*
							 * StringBuilder sb = new StringBuilder(); for (int
							 * j = 0; j < record.length; j++) { sb.append(record
							 * [j]).append(","); }
							 */
							// record[record.length]=""+i;
							// System.out.println(record);
							/*
							 * System.out.println("Line "+i); for(int j = 0; j <
							 * record.length; j++) {
							 * System.out.println(record[j]); }
							 */
							correctlines.add(record);
							// System.out.println("correct
							// records:"+sb.deleteCharAt(sb.length()-1));
						} else {
							if (correctlines != null) {
								correctlines.clear();
								correctlines = null;
							}
						}
					} else {
						isProper = false;
						String[] error = new String[record.length + 1];
						error[0] = "issue line: " + i + " Expected column Count [" + flength
								+ "] Received column Count " + record.length;

						for (int j = 0; j < record.length; j++) {
							error[j + 1] = record[j];
						}
						errorlines.add(error);
						/*
						 * StringBuilder sb = new StringBuilder(); for (int j =
						 * 0; j < record.length; j++) { sb.append(record
						 * [j]).append(","); }
						 * System.out.println("issue line: "+sb.deleteCharAt(sb.
						 * length()-1));
						 */
					}
					/*
					 * if(i==20) { break; }
					 */
				}

				if (isProper) {
					response = correctlines;
				} else {
					response = errorlines;
				}
				reader.close();

				bean.setError(!isProper);
				bean.setRecords(response);
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bean;
	}

	private static String removeSpace(String str)
	{
		return str.replaceAll(" ", "");
	}

	public static void main(String[] args) throws FileNotFoundException, JSONException {

		// System.out.println(run(accountOpen()));

		Bean bean = validateFile("D://Reports1//20122018_RASSUNION2.csv", "UAMUSERS");
		List<String[]> records = bean.getRecords();
		// System.out.println("Number of Records :"+records.size());
		for (String[] record : records) {
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < record.length; j++) {
				sb.append(record[j]).append(",");
			}
			if (bean.isError) {

				// System.out.println("issue line
				// no:["+(Integer.parseInt(record[0])+1)+"] Expected column
				// Count [8] Received column Count
				// ["+(record.length-1)+"]\n"+sb.deleteCharAt(sb.length()-1));
				System.out.println(record[0]);
			} else {
				// System.out.println("Kailash "+record [5]);
				System.out.println("correct records:" + record[0] + "," + record[1] + "," + record[2]);
			}

			sb = null;
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
	
	
	public static String getFileInformation(String filepath, String fcode,Connection conn) {
		Bean bean = validateFile(filepath, fcode);
		List<String[]> records = bean.getRecords();
		StringBuilder sba = new StringBuilder();
		boolean valdf=true;
		if (bean.isError) {
			sba.append("<tr><td>Number of Records Failed :" + records.size()
					+ "</td><input type='hidden' id='result' value='fail' /></tr>");
		} else {
			sba.append("<tr><td>Number of Records Success :" + records.size()
					+ "</td><input type='hidden' id='result' value='success' /></tr>");
		}
		sba.append('\n');
		for (String[] record : records) {
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < record.length; j++) {
				sb.append(record[j]).append(",");
			}
			if (bean.isError) {
				sba.append("<tr><td>" + record[0] + "</td></tr>");

				// System.out.println("issue line
				// no:["+(Integer.parseInt(record[0])+1)+"] Expected column
				// Count [8] Received column Count
				// ["+(record.length-1)+"]\n"+sb.deleteCharAt(sb.length()-1));
				bean.setStatus("File Processing Result : Fail");
				valdf=false;
			} else {
				
				valdf=true;
				
			}

			sb = null;

		}
		
		if(valdf) {
			if(fcode.equalsIgnoreCase("UNSETTLED")) {
				
			
				sba=UnsettledFileValidation.getUnsettlementDataValiadion(filepath,conn);
				if(sba.length()==0) {
					bean.setStatus("File Processing Result : Success");
				}else {
					bean.setStatus("File Processing Result : Fail");
				}
			
			}
			
			if(fcode.equalsIgnoreCase("REVSUCC")) {
				
				
				sba=UnsettledFileValidation.getSuccessRevDataValiadion(filepath,conn);
				if(sba.length()==0) {
					bean.setStatus("File Processing Result : Success");
				}else {
					bean.setStatus("File Processing Result : Fail");
				}
			
			}
			
			if(fcode.equalsIgnoreCase("TERMINAL")) {
				
				
				/*sba=UnsettledFileValidation.getUnsettlementDataValiadion(filepath,conn);
				if(sba.length()==0) {
					bean.setStatus("File Processing Result : Success");
				}else {
					bean.setStatus("File Processing Result : Fail");
				}*/
				bean.setStatus("File Processing Result : Success");
			}
			if(fcode.equalsIgnoreCase("PREENROLMENT")) {
				
				
				/*sba=UnsettledFileValidation.getUnsettlementDataValiadion(filepath,conn);
				if(sba.length()==0) {
					bean.setStatus("File Processing Result : Success");
				}else {
					bean.setStatus("File Processing Result : Fail");
				}*/
				bean.setStatus("File Processing Result : Success");
			}
		}

		sba.append("<tr><td>" + bean.getStatus() + "</td></tr>");
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
