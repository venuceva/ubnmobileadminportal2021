package com.ceva.base.agent.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.AgentDAO;
import com.ceva.base.common.dao.ReportsDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.unionbank.channel.ExcelParsing;
import com.opensymphony.xwork2.ActionSupport;

public class ReportAction  extends ActionSupport{

	
	Logger logger = Logger.getLogger(ReportAction.class);
	
	
	 private HttpSession session = null;	
	
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;
	 
	 JSONObject rptresponseJSON = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 private String Report;
	 private String fromdate;
	 private String todate;
	 private String reporttype;
	 
	 private String jrxmlname;
	 private String query;
	 private String format;
	 private String fieldsval;
	 
	 private String searchby;
	 private String offtype;
	 
	 
	 


	public String getOfftype() {
		return offtype;
	}


	public void setOfftype(String offtype) {
		this.offtype = offtype;
	}


	public String getFieldsval() {
		return fieldsval;
	}


	public void setFieldsval(String fieldsval) {
		this.fieldsval = fieldsval;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public String getSearchby() {
		return searchby;
	}


	public void setSearchby(String searchby) {
		this.searchby = searchby;
	}


	public String getJrxmlname() {
		return jrxmlname;
	}


	public void setJrxmlname(String jrxmlname) {
		this.jrxmlname = jrxmlname;
	}


	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public JSONObject getRptresponseJSON() {
		return rptresponseJSON;
	}


	public void setRptresponseJSON(JSONObject rptresponseJSON) {
		this.rptresponseJSON = rptresponseJSON;
	}


	public String getReporttype() {
		return reporttype;
	}


	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}


	public JSONObject getResponseJSON() {
		return responseJSON;
	}


	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}


	public String getReport() {
		return Report;
	}


	public void setReport(String report) {
		Report = report;
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


	private String result;

	 public String gtReportData() {
			logger.debug("Inside exceptionFiles()");
			ReportsDAO ReportsDAO=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			
			try {
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				requestJSON = new JSONObject();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("USER_LINKS",session.getAttribute("USER_LINKS"));
				
				requestDTO.setRequestJSON(requestJSON);

				logger.debug("Request DTO [" + requestDTO + "]");

				ReportsDAO = new ReportsDAO();
				responseDTO = ReportsDAO.getConfigData(requestDTO);
				logger.debug("Response DTO is ["+responseDTO+"]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("Files_List1");
					logger.debug("[getLocationList][responseJSON:::::"
							+ responseJSON + "]");
				
					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO
							.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in search ["+e.getMessage()+"]");
				addActionError("Internal error occured.");
			} finally{
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				
				messages = null;
				errors = null;
			}

			return result;

		}


		public String gtReportDataConf() {
			

			
			ReportsDAO ReportsDAO=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();
				
				requestJSON.put("jrxmlname", jrxmlname);
				requestJSON.put("query", query);
				requestJSON.put("searchby", searchby);
				requestJSON.put("format", format);
				requestJSON.put("fieldsval", fieldsval);
				
				requestDTO.setRequestJSON(requestJSON);

				logger.debug("Request DTO [" + requestDTO + "]");
				ReportsDAO = new ReportsDAO();
				responseDTO = ReportsDAO.getReportGeneration(requestDTO,query);
				logger.debug("Response DTO is ["+responseDTO+"]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.CLAIMS2_INFO);
					logger.debug("[getLocationList][responseJSON:::::"+ responseJSON + "]");

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO
							.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in search ["+e.getMessage()+"]");
				addActionError("Internal error occured.");
			} finally{
				ReportsDAO = null;
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				
				messages = null;
				errors = null;
			}

			return result;	
			

		}
		
		public String gtReportOfflineConf() {
			

			
			ReportsDAO ReportsDAO=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();
				
				requestJSON.put("jrxmlname", jrxmlname);
				requestJSON.put("query", query);
				requestJSON.put("searchby", searchby);
				requestJSON.put("format", format);
				requestJSON.put("fieldsval", fieldsval);
				requestJSON.put("offtype", offtype);
				
				requestDTO.setRequestJSON(requestJSON);

				logger.debug("Request DTO [" + requestDTO + "]");
				ReportsDAO = new ReportsDAO();
				responseDTO = ReportsDAO.gtReportOfflineConf(requestDTO,query);
				logger.debug("Response DTO is ["+responseDTO+"]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.CLAIMS2_INFO);
					logger.debug("[getLocationList][responseJSON:::::"+ responseJSON + "]");

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO
							.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in search ["+e.getMessage()+"]");
				addActionError("Internal error occured.");
			} finally{
				ReportsDAO = null;
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				
				messages = null;
				errors = null;
			}

			return result;	
			

		}
		
		
		
		
		public String gtOfflineReportDataConf() {
			

			
			ReportsDAO ReportsDAO=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();
				
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("jrxmlname", jrxmlname);
				requestJSON.put("query", query);
				requestJSON.put("searchby", searchby);
				
				requestDTO.setRequestJSON(requestJSON);

				logger.debug("Request DTO [" + requestDTO + "]");
				ReportsDAO = new ReportsDAO();
				responseDTO = ReportsDAO.gtOfflineReportDataConf(requestDTO,query);
				logger.debug("Response DTO is ["+responseDTO+"]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.CLAIMS2_INFO);
					logger.debug("[getLocationList][responseJSON:::::"+ responseJSON + "]");

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO
							.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in search ["+e.getMessage()+"]");
				addActionError("Internal error occured.");
			} finally{
				ReportsDAO = null;
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				
				messages = null;
				errors = null;
			}

			return result;	
			

		}
		

public String fetchData() {


	ArrayList<String> errors = null;

	ReportsDAO ReportsDAO= new ReportsDAO();
	
	try {
		
		logger.info("date ["+reporttype+"]");
		
		session = ServletActionContext.getRequest().getSession();

		
		responseDTO = ReportsDAO.fetchData(reporttype,(String)session.getAttribute(CevaCommonConstants.MAKER_ID));


		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			responseJSON = (JSONObject) responseDTO.getData().get("ACCOUNTNO");

			logger.info("Response Json Object ["+responseJSON+"]");

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		
		
	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} 

	return result;

}





public String SegmentData() {


	ArrayList<String> errors = null;

	ReportsDAO ReportsDAO= new ReportsDAO();
	
	try {
		
		logger.info("date ["+reporttype+"]");
		
		session = ServletActionContext.getRequest().getSession();

		
		responseDTO = ReportsDAO.SegmentData(reporttype,(String)session.getAttribute(CevaCommonConstants.MAKER_ID));


		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			responseJSON = (JSONObject) responseDTO.getData().get("ACCOUNTNO");

			logger.info("Response Json Object ["+responseJSON+"]");

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		
		
	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} 

	return result;

}

}
