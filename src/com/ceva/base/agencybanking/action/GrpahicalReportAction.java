package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.AnnouncementDAO;
import com.ceva.base.common.dao.GraphicalReportDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
//import com.ceva.mombasa.county.graphs.GraphActionDAO;
import com.opensymphony.xwork2.ActionSupport;

public class GrpahicalReportAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String type;
	private Logger logger = Logger.getLogger(GrpahicalReportAction.class);

	String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	private String g1type=null;
	private String g2type=null;
	private String g3type=null;
	
	private HttpSession session = null;
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	
	public String fetchGraphicalReportsInfo() {
		logger.debug("Inside fetchGraphicalReportsInfo.. ");
		GraphicalReportDAO graphicalReportDAO = null;
		ArrayList<String> errors = null;
		try { 
			requestDTO = new RequestDTO();
			
			graphicalReportDAO = new GraphicalReportDAO();
			responseDTO = graphicalReportDAO.fetchGraphicalReportsInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("GRAPH_DATA");
				logger.debug("Response JSON [" + responseJSON + "]");
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
			logger.debug("Exception in fetchGraphicalReportsInfo [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			graphicalReportDAO = null;
		}

		return result;
	}
	
	
	public String graphProcessor()
	{
		logger.debug("[GraphAction][graphProcessor][Type : "+type+"]");
		
		if(type.equalsIgnoreCase("gymgr"))
		{
			getGraphData("GYM");
			return SUCCESS;
		}
		else if(type.equalsIgnoreCase("bookcentergr"))
		{
			getGraphData("BC");
			return SUCCESS;
		}
		else if(type.equalsIgnoreCase("librarygr"))
		{
			getGraphData("LIB");
			return SUCCESS;
		}
		else if(type.equalsIgnoreCase("cashofficegr"))
		{
			getGraphData("CASH");
			return SUCCESS;
		}
		else if(type.equalsIgnoreCase("seccheckgr"))
		{
			getGraphData("SECCHK");
			return SUCCESS;
		}
		/*else if(type.equalsIgnoreCase("seccheckoutgr"))
		{
			getGraphData("SCOUT");
			return SUCCESS;
		}*/
		else if(type.equalsIgnoreCase("cafinternalgr"))
		{
			getGraphData("CAFINT");
			return SUCCESS;
		}
		/*else if(type.equalsIgnoreCase("cafexternalgr"))
		{
			getGraphData("CAFEXT");
			return SUCCESS;
		}*/
		else if(type.equalsIgnoreCase("registergr"))
		{
			getGraphData("REG");
			return SUCCESS;
		}
		else if(type.equalsIgnoreCase("attendancegr"))
		{
			getGraphData("ATD");
			return SUCCESS;
		}
		
		return "fail";
	}
	

	
	public String fetchGraphicalDashboardInfo() {
		logger.debug("Inside fetchGraphicalDashboardInfo.. ");
		GraphicalReportDAO graphicalReportDAO = null;
		ArrayList<String> errors = null;
		try { 
			requestDTO = new RequestDTO();
			
			graphicalReportDAO = new GraphicalReportDAO();
			responseDTO = graphicalReportDAO.fetchGraphicalDashboardInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("GRAPH_DATA");
				logger.debug("Response JSON [" + responseJSON + "]");
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
			logger.debug("Exception in fetchGraphicalDashboardInfo [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			graphicalReportDAO = null;
		}

		return result;
	}
	
	
	
	public String fetchGraphicalReportsAjaxInfo() {
		logger.debug("Inside fetchGraphicalReportsAjaxInfo.. ");
		GraphicalReportDAO graphicalReportDAO = null;
		ArrayList<String> errors = null;
		try { 
			requestDTO = new RequestDTO();
			
			graphicalReportDAO = new GraphicalReportDAO();
			responseDTO = graphicalReportDAO.fetchGraphicalReportsInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			g1type = g1type == null ? "table" : g1type;
			g2type = g2type == null ? "table" : g2type;
			g3type = g3type == null ? "table" : g3type;
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("GRAPH_DATA");
				
				responseJSON.put("g1type", g1type);
				responseJSON.put("g2type", g2type);
				responseJSON.put("g2type", g2type);
				
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in fetchGraphicalReportsInfo [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			graphicalReportDAO = null;
		}

		return SUCCESS;
	}
	
	
	public String fetchGraphicalDashboardAjaxInfo() {
		logger.debug("Inside fetchGraphicalDashboardAjaxInfo.. ");
		GraphicalReportDAO graphicalReportDAO = null;
		ArrayList<String> errors = null;
		try { 
			requestDTO = new RequestDTO();
			
			graphicalReportDAO = new GraphicalReportDAO();
			responseDTO = graphicalReportDAO.fetchGraphicalDashboardInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("GRAPH_DATA");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("Exception in fetchGraphicalDashboardInfo [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			graphicalReportDAO = null;
		}

		return SUCCESS;
	}
	
	
	public JSONObject getGraphData(String serviceid)
	{
		//Pie chart
		//select count(*) from CURRENCY_TXN_INFO where EXTRACT(DAY from TXN_DATE)=EXTRACT(DAY from SYSDATE) and EXTRACT(MONTH from TXN_DATE)=EXTRACT(MONTH from SYSDATE) and 
		//EXTRACT(YEAR from TXN_DATE)=EXTRACT(YEAR from SYSDATE)  group by Payment_Type;
		
		//weekly amount
		//select nvl(trunc(TXN_DATE),null), sum(amount) from CURRENCY_TXN_INFO where (trunc(txn_date) between sysdate-7 and sysdate)
		//group by trunc(Txn_Date) ORDER by trunc(TXN_DATE)
		
		//Monthly
		//select to_char(TXN_DATE, 'MM-YYYY'), sum(amount)
		//from CURRENCY_TXN_INFO where extract(YEAR from TXN_DATE)=extract(YEAR from sysdate)
		//		group by to_char(TXN_DATE, 'MM-YYYY')
		//		order by 1
		
		ArrayList<String> errors = null;
		JSONObject json = new JSONObject();
		session = ServletActionContext.getRequest().getSession();
		requestDTO = new RequestDTO();
		json.put(CevaCommonConstants.MAKER_ID, session
				.getAttribute(CevaCommonConstants.MAKER_ID) == null ? " "
				: session.getAttribute(CevaCommonConstants.MAKER_ID));

		json.put("serviceid", serviceid);
		
		logger.debug("JSON Values ===>>" + json);
		
		requestDTO.setRequestJSON(json);
		
		GraphicalReportDAO dao = new GraphicalReportDAO();
		responseDTO = dao.getGraphData(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0)
		{
			responseJSON = (JSONObject) responseDTO.getData().get("RESULT");
			
			logger.debug("Response JSON [" + responseJSON + "]");
			result = SUCCESS;
		}
		else
		{
			errors = (ArrayList<String>) responseDTO.getErrors();
			for (int i = 0; i < errors.size(); i++) {
				addActionError(errors.get(i));
			}
			result = "fail";
		}
		
		return responseJSON;
	}
	
	
	public JSONObject getResponseJSON() {
		return responseJSON;
	}


	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}


	public String getG1type() {
		return g1type;
	}


	public void setG1type(String g1type) {
		this.g1type = g1type;
	}


	public String getG2type() {
		return g2type;
	}


	public void setG2type(String g2type) {
		this.g2type = g2type;
	}


	public String getG3type() {
		return g3type;
	}


	public void setG3type(String g3type) {
		this.g3type = g3type;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
