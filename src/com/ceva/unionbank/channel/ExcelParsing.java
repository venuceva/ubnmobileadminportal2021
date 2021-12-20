package com.ceva.unionbank.channel;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;

public class ExcelParsing {
	
	private static Logger logger = Logger.getLogger(ExcelParsing.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	
	public static JSONObject getData(String Filepath){
		JSONObject json = new JSONObject();
		
		try
		{
			FileInputStream file = new FileInputStream(new File(Filepath));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			
			int rows = sheet.getLastRowNum();
			
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				if(row.getCell(0)!=null && !(((row.getCell(0)).getStringCellValue()).trim()).equalsIgnoreCase("")){
					/*System.out.println(row.getRowNum()+"------"+((row.getCell(0)).getStringCellValue()).trim());
					json.put(row.getRowNum(), ((row.getCell(0)).getStringCellValue()).trim());*/
					
					
					JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getUserProfile(((row.getCell(0)).getStringCellValue()).trim()));
					json.put("userid",((row.getCell(0)).getStringCellValue()).trim());
					json.put("displayname",json1.getString("DisplayName"));
					json.put("empno",json1.getString("EmployeeId"));
					json.put("fname",json1.getString("FirstName"));
					json.put("lname",json1.getString("LastName"));
					json.put("jtitle",json1.getString("JobTitle"));
					json.put("mnumber",json1.getString("MobileNumber"));
					json.put("mailid",json1.getString("Email"));
					json.put("branchcode",json1.getString("BranchCode") +" - "+ json1.getString("BranchName"));
					
					
				}
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return json;
	}
	
	
	
public ResponseDTO getUserADData(RequestDTO requestDTO) {
		
		responseDTO=new ResponseDTO();
		requestJSON=new JSONObject();
		responseJSON=new JSONObject();
		
		logger.debug("inside [AgentDAO][AgentRegModifySearch]");

		logger.debug("Inside  AgentRegModifySearch.... ");

		HashMap<String, Object> serviceDataMap = null;
		JSONObject resultJson = null;
		JSONArray IncomeMTFilesJSONArray = null;
		
		PreparedStatement servicePstmt = null;
		ResultSet serviceRS = null;
		
		Connection connection = null;

		try {
			responseDTO = new ResponseDTO();
			responseJSON = new JSONObject();
			requestJSON = requestDTO.getRequestJSON();

			connection = DBConnector.getConnection();

			serviceDataMap = new HashMap<String, Object>();
			resultJson = new JSONObject();
			IncomeMTFilesJSONArray = new JSONArray();
			
			String userReport="";
			String filepath=requestJSON.getString("filepath");
			String filename=requestJSON.getString("filename");
			
			
			FileInputStream file = new FileInputStream(new File(filepath+"/"+filename));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			
			int rows = sheet.getLastRowNum();
			
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				
				
				Iterator<Cell> cellIterator = row.cellIterator();
				JSONObject json = new JSONObject();
				if(row.getCell(0)!=null && !(((row.getCell(0)).getStringCellValue()).trim()).equalsIgnoreCase("")){
					
					
					
					JSONObject json1 = JSONObject.fromObject(ServiceRequestClient.getUserProfile(((row.getCell(0)).getStringCellValue()).trim()));
					
					
					
					json.put("userid",((row.getCell(0)).getStringCellValue()).trim());
					json.put("displayname",json1.getString("DisplayName"));
					json.put("empno",json1.getString("EmployeeId"));
					json.put("fname",json1.getString("FirstName"));
					json.put("lname",json1.getString("LastName"));
					json.put("jtitle",json1.getString("JobTitle"));
					json.put("mnumber",json1.getString("MobileNumber"));
					json.put("mailid",json1.getString("Email"));
					json.put("branchcode",json1.getString("BranchCode") +" - "+ json1.getString("BranchName"));
					
					
					
					if((json1.getString("MobileNumber")).equalsIgnoreCase("") || (json1.getString("MobileNumber")).equalsIgnoreCase("+234")){
						json.put("status","Fail");	
						System.out.println("fail1");
					}else{
						
						servicePstmt = connection.prepareStatement("SELECT * FROM USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC WHERE UI.COMMON_ID=ULC.COMMON_ID AND ULC.LOGIN_USER_ID='"+(json1.getString("MobileNumber")).replaceAll("\\+", "")+"'");
						serviceRS = servicePstmt.executeQuery();
						
						 if(serviceRS.next())
							{
							 	json.put("status","Fail");
							 	
							}else{
								json.put("status","pass");
							}
						
						
					}
					
					IncomeMTFilesJSONArray.add(json);
					
				}
			}
			file.close();
			
			

			resultJson.put("Files_List", IncomeMTFilesJSONArray);

			serviceDataMap.put("Files_List", resultJson);

			//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
			responseDTO.setData(serviceDataMap);

		}catch (Exception e) {

			logger.debug("Exception in AgentRegModifySearch [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured While Executing.");
		} finally {
			try {

				
				if (servicePstmt != null)
					servicePstmt.close();
				if (serviceRS != null)
					serviceRS.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {

			}
			serviceDataMap = null;
			resultJson = null;
			IncomeMTFilesJSONArray.clear();
			IncomeMTFilesJSONArray = null;
		}

		return responseDTO;
	}



public ResponseDTO getRAASData(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	Connection connection = null;
	logger.debug("inside [AgentDAO][AgentRegModifySearch]");

	logger.debug("Inside  AgentRegModifySearch.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;
	
	

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();
		connection = DBConnector.getConnection();

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		
		String filepath=requestJSON.getString("filepath");
		String filename=requestJSON.getString("filename");
		
		JSONObject json = new JSONObject();
		json.put("status",RaasValidator.getFileInformation(filepath+"/"+filename));
		
		//RaasValidator.getFileInformation(filepath+"/"+filename)

		resultJson.put("Files_List", json);

		serviceDataMap.put("Files_List", resultJson);

		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	}catch (Exception e) {

		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			
			
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}
	
public ResponseDTO getUserADDataAck(RequestDTO requestDTO) {
	
	responseDTO=new ResponseDTO();
	requestJSON=new JSONObject();
	responseJSON=new JSONObject();
	
	logger.debug("inside [AgentDAO][AgentRegModifySearch]");

	logger.debug("Inside  AgentRegModifySearch.... ");

	HashMap<String, Object> serviceDataMap = null;
	JSONObject resultJson = null;
	JSONArray IncomeMTFilesJSONArray = null;

	Connection connection = null;
	PreparedStatement pstmt2 = null;
	Statement statement=null;

	try {
		responseDTO = new ResponseDTO();
		responseJSON = new JSONObject();
		requestJSON = requestDTO.getRequestJSON();

		connection = DBConnector.getConnection();
		statement = connection.createStatement();

		serviceDataMap = new HashMap<String, Object>();
		resultJson = new JSONObject();
		IncomeMTFilesJSONArray = new JSONArray();
		String limitcode=requestJSON.getString("limitcode");
		String filename=requestJSON.getString("filename");
		String records=requestJSON.getString("records");
		
		String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
		
		pstmt2 = connection.prepareStatement("INSERT INTO  file_upload_master(ref_num,f_type,f_name,num_of_record,upload_date,status,maker_id,maker_dt) values(SEQ_FILEUPLOAD.nextval,'"+limitcode+"','"+filename+"','"+records+"',sysdate,'P','"+makerid+"',sysdate)");
		pstmt2.executeUpdate();
		pstmt2.close();
		
		
			/*
			statement.addBatch("INSERT INTO  file_upload_master(ref_num,f_type,f_name,num_of_record,upload_date,status,maker_id,maker_dt) values(SEQ_FILEUPLOAD.nextval,'"+limitcode+"','"+filename+"','"+records+"',sysdate,'P','"+makerid+"',sysdate)");
		
	       
	       statement.executeBatch();
	       connection.commit();*/
	       
	       statement.close();
	       

		resultJson.put("Files_List", IncomeMTFilesJSONArray);

		serviceDataMap.put("Files_List", resultJson);
		connection.commit();
		
		//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
		responseDTO.setData(serviceDataMap);

	}catch (Exception e) {

		logger.debug("SQLException in AgentRegModifySearch [" + e.getMessage()
				+ "]");
		responseDTO.addError("Internal Error Occured While Executing.");
	} finally {
		try {

			
			if (pstmt2 != null)
				pstmt2.close();
			
			if (connection != null)
				connection.close();

		} catch (SQLException e) {

		}
		serviceDataMap = null;
		resultJson = null;
		IncomeMTFilesJSONArray.clear();
		IncomeMTFilesJSONArray = null;
	}

	return responseDTO;
}

	
	public static int getRecords(String Filepath){
		JSONObject json = new JSONObject();
		int i=0;
		try
		{
			FileInputStream file = new FileInputStream(new File(Filepath));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			
			int rows = sheet.getLastRowNum();
			
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				if(row.getCell(0)!=null && !(((row.getCell(0)).getStringCellValue()).trim()).equalsIgnoreCase("")){
					i=i+1;
				}
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return i;
	}
	
}
