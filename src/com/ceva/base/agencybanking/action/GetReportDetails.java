package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.ReportDetailsDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class GetReportDetails extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(GetReportDetails.class);

	private String result;
	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;
	private RequestDTO requestDTO = null;
	private ResponseDTO responseDTO = null;

	private String[] frequencies;
	private String[] dateTime;
	private String[] emailids;
	private String[] reports;

	private String userId;
	private String groupID;
	private String groupId;
	private String grpname;
	private String employeeNo;
	private String selectUsers;

	private HttpSession session = null;

	public String getAllReportDetails() {
		logger.debug("Inside GetAllReportDetails.. ");
		ReportDetailsDAO configMgmtDAO = null;
		String makerId = "";
		ArrayList<String> errors = null;
		try {

			responseJSON = new JSONObject();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			session = ServletActionContext.getRequest().getSession();

			makerId = session.getAttribute("makerId").toString();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					makerId == null ? "NO_VAL" : makerId);
			requestJSON.put("USER_ID", makerId == null ? "NO_VAL" : makerId);

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			configMgmtDAO = new ReportDetailsDAO();
			responseDTO = configMgmtDAO.getReportDetailsInfo(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("reportinfo");
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
			logger.debug("Exception in GetAllReportDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			configMgmtDAO = null;
			errors = null;
			makerId = null;
		}

		return result;
	}

	public String getScheduledReports() {

		logger.debug("Inside GetScheduledReports.... ");
		ReportDetailsDAO configMgmtDAO = null;
		ArrayList<String> errors = null;
		try {
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			logger.debug("Request DTO [" + requestDTO + "]");
			configMgmtDAO = new ReportDetailsDAO();
			responseDTO = configMgmtDAO.getScheduledReportDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"reportschinfo");
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
			logger.debug("Exception in getScheduledReports [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			configMgmtDAO = null;
			errors = null;
		}

		return result;
	}

	public String insertScheduledReports() {

		logger.debug("inside insertScheduledReports ... ");
		ReportDetailsDAO configMgmtDAO = null;
		ArrayList<String> errors = null;
		try {
			logger.debug("Frequencies 	==> " + Arrays.asList(getFrequencies()));
			logger.debug("dateTime 		==> " + Arrays.asList(getDateTime()));
			logger.debug("emailids 		==> " + Arrays.asList(getEmailids()));
			logger.debug("reports 		==> " + Arrays.asList(getReports()));

			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("freq", Arrays
					.asList(getFrequencies() == null ? new String[] { "" }
							: getFrequencies()));
			jsonObj.put("dateTime", Arrays
					.asList(getDateTime() == null ? new String[] { "" }
							: getDateTime()));
			jsonObj.put("emailIds", Arrays
					.asList(getEmailids() == null ? new String[] { "" }
							: getEmailids()));
			jsonObj.put("reports", Arrays
					.asList(getReports() == null ? new String[] { "" }
							: getReports()));
			;

			requestDTO.setRequestJSON(jsonObj);

			logger.debug("Request DTO  [" + requestDTO + "]");
			configMgmtDAO = new ReportDetailsDAO();
			responseDTO = configMgmtDAO.insertReportDetailsInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						"reportschinfo");
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
			logger.debug("Exception in insertScheduledReports ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			configMgmtDAO = null;
			errors = null;
		}

		return result;
	}

	public String getUserAssignedReports() {

		logger.debug("Inside GetUserAssignedReports...  ");
		ReportDetailsDAO reportDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestJSON.put("GROUP_ID", getGroupId() == null ? " "
					: getGroupId());
			requestJSON.put("GROUP_ID1", getGroupID() == null ? " "
					: getGroupID());
			requestJSON.put("USER_ID", getUserId() == null ? " " : getUserId());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Before setting to DAO.");

			reportDAO = new ReportDetailsDAO();
			responseDTO = reportDAO.getUserAssignedReports(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
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
			logger.debug("Exception in getUserAssignedReports ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			reportDAO = null;
			errors = null;
		}

		return result;
	}

	public String getCommonScreen() {
		logger.debug("Inside  GetCommonScreen.. ");
		try {
			responseJSON = new JSONObject();

			responseJSON.put("GROUP_ID", getGroupId() == null ? " "
					: getGroupId());
			responseJSON.put("GROUP_NAME", getGrpname() == null ? " "
					: getGrpname());
			responseJSON
					.put("USER_ID", getUserId() == null ? " " : getUserId());
			responseJSON.put("EMP_NO", getEmployeeNo() == null ? " "
					: getEmployeeNo());

			result = "success";
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in getCommonScreen [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {

		}

		return result;
	}

	public String insertUserAssignedReports() {
		logger.debug("Inside  InsertUserAssignedReports... ");
		String makerId = "";
		ReportDetailsDAO reportDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			session = ServletActionContext.getRequest().getSession(false);

			makerId = session.getAttribute("makerId").toString();

			requestJSON.put("GROUP_ID", getGroupId() == null ? " "
					: getGroupId());
			requestJSON.put("USER_ID", getUserId() == null ? " " : getUserId());

			requestJSON.put(CevaCommonConstants.TXN_DATA, selectUsers);
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					makerId == null ? "NO_VAL" : makerId);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");

			reportDAO = new ReportDetailsDAO();
			responseDTO = reportDAO.insertUserAssignedReports(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.RESPONSE_JSON);
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
			logger.debug("Exception in getUserAssignedReports ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			reportDAO = null;
			errors = null;
			makerId = null;
		}

		return result;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String[] getFrequencies() {
		return frequencies;
	}

	public void setFrequencies(String[] frequencies) {
		this.frequencies = frequencies;
	}

	public String[] getDateTime() {
		return dateTime;
	}

	public void setDateTime(String[] dateTime) {
		this.dateTime = dateTime;
	}

	public String[] getEmailids() {
		return emailids;
	}

	public void setEmailids(String[] emailids) {
		this.emailids = emailids;
	}

	public String[] getReports() {
		return reports;
	}

	public void setReports(String[] reports) {
		this.reports = reports;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGrpname() {
		return grpname;
	}

	public void setGrpname(String grpname) {
		this.grpname = grpname;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(String selectUsers) {
		this.selectUsers = selectUsers;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
