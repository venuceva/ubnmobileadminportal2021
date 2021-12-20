package com.ceva.base.agencybanking.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.BarChatDAO;
import com.ceva.base.common.dao.CardOrderDAO;
import com.ceva.base.common.dao.CevaPowerAdminDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class BarChatAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(BarChatAction.class);

	String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private String messageText;
	private String typeOfData;
	private String typeOfDataVal;
	private String referenceNo;
	private String status;
	private String merchantid;
	
	private String reportType = null;
	private String reportName = null;
	private String reportDate = null;
	private String yearName = null;
	private String quarterName = null;
	private String monthName = null;
	private String weekName = null;
	private String groupType;
	
	private HttpSession session = null;
	

	public String CommonScreen() {
		logger.debug("inside [FinancialAccountingAction][CommonScreen]" );
		result = "success";
		return result;
	}



	public String fillStores() {
		//String result="fail";
		BarChatDAO barcharDAO = null;
	
		ArrayList<String> errors = null;
		try {
		
			logger.debug("inside [ReportsAction][fillStores][merchantid::"+merchantid+"]");
			requestJSON=new JSONObject();
			responseJSON=new JSONObject();
			requestDTO=new RequestDTO();
			responseDTO= new ResponseDTO();
		
			requestJSON.put("MERCHANT_ID", merchantid);
			requestDTO.setRequestJSON(requestJSON);	
	        barcharDAO = new BarChatDAO();
		   responseDTO = barcharDAO.fetchStores(requestDTO);
		   
			if (responseDTO != null && responseDTO.getErrors().size()==0) {		
				responseJSON=(JSONObject) responseDTO.getData().get("STORE_LIST");
				logger.debug("[ReportsAction][getReportParameters][responseJSON:::::"+responseJSON+"]");
			}else{			
				ArrayList<String> errors1=(ArrayList<String>) responseDTO.getErrors();
				for(int i=0;i<errors1.size();i++){
					addActionError(errors1.get(i));
				}
			}
	    	return SUCCESS;
		
		
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in Get BarChatAction  fillStores ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		barcharDAO = null;
		errors = null;
	}
	return result;
	}
	
	public String MerchantBarGraph() {
	
		    BarChatDAO barcharDAO = null;
			ArrayList<String> messages = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
               
				logger.debug("BarChatAction MerchantBarGraph  "+reportType+" "+reportDate+" "+yearName+" "+quarterName+" "+monthName+" "+weekName+" "+weekName+" "+merchantid);
				
		   //******//
				
				  requestJSON.put("MERCHANT_ID", getMerchantid());
				  requestJSON.put("REPORT_NAME", getReportName());
				 
		   
		   if(getReportType()!=null){
				  requestJSON.put("REPORT_TYPE", getReportType());
				if(getReportType().equals("D")){
					requestJSON.put("REPORT_DATE", getReportDate());
				}else if(getReportType().equals("W")){
					requestJSON.put("REPORT_YEAR", getYearName());
					requestJSON.put("REPORT_MONTH", getMonthName());
					requestJSON.put("REPORT_WEEK", getWeekName());
				}else if(getReportType().equals("M")){
					requestJSON.put("REPORT_YEAR", getYearName());
					requestJSON.put("REPORT_MONTH", getMonthName());
				}else if(getReportType().equals("Q")){
					requestJSON.put("REPORT_YEAR", getYearName());
					requestJSON.put("REPORT_QUARTER", getQuarterName());
				}else{
					addActionError("Please select Correct Report Type.");
				}
				
				if(getReportType().equals("D") || getReportType().equals("W") || getReportType().equals("M") || getReportType().equals("Q")){
					requestJSON.put("REPORT_NAME", getReportName());
					requestDTO.setRequestJSON(requestJSON);
					 barcharDAO = new BarChatDAO();
					 responseDTO = barcharDAO.fetchmerchantBarData(requestDTO);
					 if (responseDTO != null && responseDTO.getErrors().size()==0) {		
							responseJSON=(JSONObject) responseDTO.getData().get("GRP_DATA");
							logger.debug("[BarChatAction][MerchantBarGraph][responseJSON:::::"+responseJSON+"]");
						}else{			
							ArrayList<String> errors1=(ArrayList<String>) responseDTO.getErrors();
							for(int i=0;i<errors1.size();i++){
								addActionError(errors1.get(i));
							}
						}
				    	return SUCCESS;
				}
				
		   }
		   //********//
	
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in Get BarChatAction  MerchantBar ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		barcharDAO = null;
		errors = null;
	}
	return result;
	}

	public String filluserlevel() {
		//String result="fail";
		 BarChatDAO barcharDAO = null;
	
		ArrayList<String> errors = null;
		try {
		
			logger.debug("inside [BarChatAction][filluserlevel][groupType::"+groupType+"]");
			requestJSON=new JSONObject();
			responseJSON=new JSONObject();
			requestDTO=new RequestDTO();
			responseDTO= new ResponseDTO();
			requestJSON.put("groupType", groupType);
			requestJSON.put("group", "GRPLVL");
			requestDTO.setRequestJSON(requestJSON);	
			barcharDAO = new BarChatDAO();
		    responseDTO = barcharDAO.fetchuserlevel(requestDTO);
		   
			if (responseDTO != null && responseDTO.getErrors().size()==0) {		
				responseJSON=(JSONObject) responseDTO.getData().get("USER_LEVEL_LIST");
				logger.debug("[BarChatAction][filluserlevel][responseJSON:::::"+responseJSON+"]");
			}else{			
				ArrayList<String> errors1=(ArrayList<String>) responseDTO.getErrors();
				for(int i=0;i<errors1.size();i++){
					addActionError(errors1.get(i));
				}
			}
	    	return SUCCESS;
		
		
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in Get BarChatAction  filluserlevel ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		barcharDAO = null;
		errors = null;
	}
	return result;
	}
	
	
	
	
	public String grouptypelevel() {
		//String result="fail";
		 BarChatDAO barcharDAO = null;
	
		ArrayList<String> errors = null;
		try {
			logger.debug("inside [BarChatAction][grouptypelevel]");
			//logger.debug("inside [BarChatAction][filluserlevel][groupType::"+groupType+"]");
			requestJSON=new JSONObject();
			responseJSON=new JSONObject();
			requestDTO=new RequestDTO();
			responseDTO= new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put("makerid", session.getAttribute(CevaCommonConstants.MAKER_ID).toString());
			requestJSON.put("group", "GRPTYPE");
			
			requestDTO.setRequestJSON(requestJSON);	
			barcharDAO = new BarChatDAO();
		    responseDTO = barcharDAO.fetchuserlevel(requestDTO);
		   
			if (responseDTO != null && responseDTO.getErrors().size()==0) {		
				responseJSON=(JSONObject) responseDTO.getData().get("USER_LEVEL_LIST");
				logger.debug("[BarChatAction][filluserlevel][responseJSON:::::"+responseJSON+"]");
			}else{			
				ArrayList<String> errors1=(ArrayList<String>) responseDTO.getErrors();
				for(int i=0;i<errors1.size();i++){
					addActionError(errors1.get(i));
				}
			}
	    	return SUCCESS;
		
		
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in Get BarChatAction  filluserlevel ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		barcharDAO = null;
		errors = null;
	}
	return result;
	}
	

	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public JSONObject getRequestJSON() {
		return requestJSON;
	}

	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public ResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getTypeOfData() {
		return typeOfData;
	}

	public void setTypeOfData(String typeOfData) {
		this.typeOfData = typeOfData;
	}

	public String getTypeOfDataVal() {
		return typeOfDataVal;
	}

	public void setTypeOfDataVal(String typeOfDataVal) {
		this.typeOfDataVal = typeOfDataVal;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchantid() {
		return merchantid;
	}


	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	
	public String getReportType() {
		return reportType;
	}


	public void setReportType(String reportType) {
		this.reportType = reportType;
	}


	public String getReportName() {
		return reportName;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


   
    public String getReportDate() {
		return reportDate;
	}


	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}


	public String getYearName() {
		return yearName;
	}


	public void setYearName(String yearName) {
		this.yearName = yearName;
	}


	public String getQuarterName() {
		return quarterName;
	}


	public void setQuarterName(String quarterName) {
		this.quarterName = quarterName;
	}


	public String getMonthName() {
		return monthName;
	}


	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}


	public String getWeekName() {
		return weekName;
	}


	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	


	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	
	public String getStatReportsData() {
		
		return SUCCESS;
	}

	private int getLastDayofMonth(int month, int year) {
        Calendar dateCal = Calendar.getInstance();
       dateCal.set(year, month, 2);
       int maxDay = dateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
       return maxDay;
   }
 public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}
