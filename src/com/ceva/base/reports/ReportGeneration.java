package com.ceva.base.reports;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class ReportGeneration {
	static Connection conn;
	public void GeneratePdfReportWithJasperReports(Connection con,Map<String, Object> parameters,String jaspername,String filename,String formats){
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("configurations");
		//System.out.println("Report Name :: "+jaspername);
		parameters.put("IMAGE_PATH", rb.getString("JRXMLSOURCE")+"logo.png");
		//System.out.println("kailash here ::"+parameters.get("RefNo"));
		//System.out.println("Image Path :: "+rb.getString("JRXMLSOURCE")+"logo.png");
System.out.println("parameters :: "+parameters);
		 System.out.println("Report Path :: "+rb.getString("JRXMLSOURCE")+jaspername+".jrxml");
		//System.out.println("format :: "+formats);
		System.out.println("Output Path :: "+rb.getString("OUT_PATH")+""+filename+".pdf");
		
			// compiles jrxml
			JasperCompileManager.compileReportToFile(rb.getString("JRXMLSOURCE")+jaspername+".jrxml");
			
			JasperPrint print = JasperFillManager.fillReport(rb.getString("JRXMLSOURCE")+jaspername+".jasper", parameters, con);

			if(formats.equalsIgnoreCase("pdf")){
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS,true); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(rb.getString("OUT_PATH")+"/"+filename+".pdf")); // your output goes here
			exporter.exportReport();
			}
			
			if(formats.equalsIgnoreCase("txt")){
			JRTextExporter txtExporter = new JRTextExporter();
		    txtExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		    txtExporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT ,"\f");  
		    txtExporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR ,"\r\n");
		    txtExporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT        , new Float(798));
		    txtExporter.setParameter(JRTextExporterParameter.PAGE_WIDTH         , new Float(581));
		    txtExporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH    , new Float(  7));
		    txtExporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT   , new Float( 14));
		    txtExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,rb.getString("OUT_PATH")+"/"+filename+".txt");
		    txtExporter.exportReport();
			}
			
			if(formats.equalsIgnoreCase("xlsx")){
			JRXlsxExporter docxExporter=new JRXlsxExporter();  
		    docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);  
		    docxExporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS,true); 
		    docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(rb.getString("OUT_PATH")+"/"+filename+".xlsx"));  
		    docxExporter.exportReport();  
			}
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		ReportGeneration rg=new ReportGeneration();
		Map<String, Object> parameters = new HashMap<String, Object>();
		try{
		conn = DBConnector.getConnection();
		System.out.println("kailash here :: "+conn.isClosed());
		
		/*PreparedStatement pstmt =  conn.prepareStatement("select 'sravan' from dual");
		
		ResultSet rs =  pstmt.executeQuery();
		
		while (rs.next()) {
			
			System.out.println(">>>> "+rs.getString(1));
			
		}*/
		
		
		parameters.put("QUERY", "SELECT ULC.LOGIN_USER_ID,UI.USER_NAME,UI.EMAIL,UI.MOBILE_NO,UI.USER_GROUPS,DECODE(UI.USER_STATUS,'A','Active','B','Blocked','L','Locked',USER_STATUS)USER_STATUS,DECODE(UI.USER_TYPE,'HOD','Head Of Department','JO','Junior Officer','MA','Merchant Admin','MU','Merchant User','MS','Merchant Supervisor','SM','Store Manager',USER_TYPE) USER_TYPE,ULC.MAKER_ID,ULC.MAKER_DTTM,ULC.APPL_CODE,ULC.AUTHID,ULC.AUTHDTTM,ULC.STATUS,To_Date(To_Char(ULC.LAST_LOGED_IN, 'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS') LAST_LOGED_IN FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE UI.COMMON_ID=ULC.COMMON_ID AND UI.COMMON_ID='Y928E000501'");
		rg.GeneratePdfReportWithJasperReports(conn,parameters,"report1","kailash","pdf");
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			DBUtils.closeConnection(conn);
		}
	}

}
