package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.ICTAdminDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class ICTAdminAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(ICTAdminAction.class);

	private String result;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private String multiData;
	private String regionCode;
	private String region;
	private String regionalHq;
	private String headOfficeCode;
	private String headOfficeName;
	private String locationCode;
	private String locationName;

	private String userId;
	private String employeeNo;
	private String firstName;
	private String lastName;
	private String userLevel;
	private String mobile;
	private String email;

	private HttpSession session = null;

	public String getCommonScreen() {
		logger.debug("inside [getCommonScreen]");
		result = "success";
		return result;
	}

	public String insertRegionDetails() {

		logger.debug("Inside InsertRegionDetails... ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (multiData == null) {
				addActionError("Records Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MULTI_DATA, multiData);

				logger.debug("Request JSON [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.insertRegionDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					logger.debug("Response JSON [" + responseJSON + "]");
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors  [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in InsertRegionDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String getRegionInfo() {
		logger.debug("Inside GetRegionInfo.. ");

		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new ICTAdminDAO();
			responseDTO = ictAdminDAO.getRegionInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.REGION_INFO);
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
			logger.debug("Exception in getRegionInfo [" + e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String insertHeadOfficeDetails() {

		logger.debug("Inside insertHeadOfficeDetails.. ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (multiData == null) {
				addActionError(" Records Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.MULTI_DATA, multiData);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.insertHeadOfficeDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_INFO);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Getting messages from DB.");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_INFO);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Response JSON [" + responseJSON + "]");
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors  [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertHeadOfficeDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
			messages = null;
		}

		return result;
	}

	public String insertLocations() {

		logger.debug("Inside InsertLocations.. ");
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		ICTAdminDAO ictAdminDAO = null;
		try {

			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (multiData == null) {
				addActionError("Records Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));

				requestJSON.put(CevaCommonConstants.MULTI_DATA, multiData);
				requestDTO.setRequestJSON(requestJSON);
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.insertLocations(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_INFO);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_INFO);
					logger.debug("Response JSON [" + responseJSON + "]");
					logger.debug("Response JSON [" + responseJSON + "]");
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors  [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertHeadOfficeDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
			messages = null;
		}

		return result;
	}

	public String getRegionDetails() {

		logger.debug("inside getRegionDetails.. ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [::" + requestDTO + "]");
			ictAdminDAO = new ICTAdminDAO();
			responseDTO = ictAdminDAO.getRegionDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.REGION_DATA);
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
			logger.debug("Exception in getRegionDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String updateRegionInformartion() {

		logger.debug("inside updateRegionInformartion... ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (regionCode == null) {
				addActionError("Region Code Missing.");
				result = "fail";
			} else if (region == null) {
				addActionError("Region Missing.");
				result = "fail";
			} else if (regionalHq == null) {
				addActionError("Regional Hq Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION_CODE, regionCode);
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.REGIONAL_HQ, regionalHq);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.updateRegionInformartion(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in updateRegionInformartion ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String deleteRegionInformartion() {

		logger.debug("inside deleteRegionInformartion... ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;

		try {

			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (regionCode == null) {
				addActionError("Region Code Missing.");
				result = "fail";
			} else if (region == null) {
				addActionError("Region Missing");
				result = "fail";
			} else if (regionalHq == null) {
				addActionError(" Regional Hq Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION_CODE, regionCode);
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.REGIONAL_HQ, regionalHq);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.deleteRegionInformartion(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.REGION_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in deleteRegionInformartions ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String getHeadOfficeDetails() {

		logger.debug("inside GetHeadOfficeDetails.. ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new ICTAdminDAO();
			responseDTO = ictAdminDAO.getHeadOfficeDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.HEADOFFICE_DATA);
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
			logger.debug("Exception in getHeadOfficeDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}
		return result;
	}

	public String updateHeadOfficeDetails() {

		logger.debug("inside updateHeadOfficeDetails... ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (region == null) {
				addActionError("Region Missing");
				result = "fail";
			} else if (headOfficeCode == null) {
				addActionError("headOfficeCode Missing");
				result = "fail";
			} else if (headOfficeName == null) {
				addActionError("headOfficeName Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_CODE,
						headOfficeCode);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_NAME,
						headOfficeName);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.updateHeadOfficeDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.HEADOFFICE_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in updateHeadOfficeDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String deleteHeadOfficeInformartion() {

		logger.debug("inside deleteHeadOfficeInformartion... ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (region == null) {
				addActionError("Region Missing.");
				result = "fail";
			} else if (headOfficeCode == null) {
				addActionError("HeadOfficeCode Missing.");
				result = "fail";
			} else if (headOfficeName == null) {
				addActionError("HeadOfficeName Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_CODE,
						headOfficeCode);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_NAME,
						headOfficeName);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO
						.deleteHeadOfficeInformartion(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.HEADOFFICE_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}

		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in deleteHeadOfficeInformartion ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String getLocationDetails() {

		logger.debug("inside [getLocationDetails]");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ictAdminDAO = new ICTAdminDAO();
			responseDTO = ictAdminDAO.getLocationDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.LOCATION_DATA);
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
			logger.debug("Exception in getLocationDetails [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
		}

		return result;
	}

	public String updateLocationInformation() {

		logger.debug("inside updateLocationInformation.. ");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (region == null) {
				addActionError("Region Missing.");
				result = "fail";
			} else if (headOfficeCode == null) {
				addActionError("HeadOffice Code Missing.");
				result = "fail";
			} else if (locationCode == null) {
				addActionError("Location Code Missing.");
				result = "fail";
			} else if (locationName == null) {
				addActionError("Location Name Missing.");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_CODE,
						headOfficeCode);
				requestJSON
						.put(CevaCommonConstants.LOCATION_CODE, locationCode);
				requestJSON
						.put(CevaCommonConstants.LOCATION_NAME, locationName);

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.updateLocationInformation(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.HEADOFFICE_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in updateLocationInformation ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
			messages = null;
		}

		return result;
	}

	public String deleteLocationInformartion() {

		logger.debug("inside [deleteLocationInformation]");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (region == null) {
				addActionError("Region Missing");
				result = "fail";
			} else if (headOfficeCode == null) {
				addActionError("HeadOffice Code Missing");
				result = "fail";
			} else if (locationCode == null) {
				addActionError("Location Code Missing");
				result = "fail";
			} else if (locationName == null) {
				addActionError("Location Name Missing");
				result = "fail";
			} else {
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.REGION, region);
				requestJSON.put(CevaCommonConstants.HEADOFFICE_CODE,
						headOfficeCode);
				requestJSON
						.put(CevaCommonConstants.LOCATION_CODE, locationCode);
				requestJSON
						.put(CevaCommonConstants.LOCATION_NAME, locationName);

				logger.debug("Request JSON [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO
						.deleteLocationInformartion(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.HEADOFFICE_DATA);
					logger.debug("Response JSON [" + responseJSON + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}

					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in deleteLocationInformartion ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
			messages = null;
		}

		return result;
	}

	public String createBranchUser() {

		logger.debug("inside [createBranchUser]");
		ICTAdminDAO ictAdminDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();

			if (userId == null) {
				addActionError("User Id Missing");
				result = "fail";
			} else if (employeeNo == null) {
				addActionError("Employee No Missing");
				result = "fail";
			} else if (firstName == null) {
				addActionError("First Name Missing");
				result = "fail";
			} else if (lastName == null) {
				addActionError("Last Name Missing");
				result = "fail";
			} else if (userLevel == null) {
				addActionError("User Level Missing");
				result = "fail";
			} else if (mobile == null) {
				addActionError("Mobile Missing");
				result = "fail";
			} else if (email == null) {
				addActionError("E-Mail Missing");
				result = "fail";
			} else {

				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put(CevaCommonConstants.USER_ID, userId);
				requestJSON.put(CevaCommonConstants.EMPLOYEE_NO, employeeNo);
				requestJSON.put(CevaCommonConstants.F_NAME, firstName);
				requestJSON.put(CevaCommonConstants.L_NAME, lastName);
				requestJSON.put(CevaCommonConstants.USER_LEVEL, userLevel);
				requestJSON.put(CevaCommonConstants.MOBILE, mobile);
				requestJSON.put(CevaCommonConstants.EMAIL, email);

				logger.debug("Request JSON [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				ictAdminDAO = new ICTAdminDAO();
				responseDTO = ictAdminDAO.createBranchUser(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");

				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug(" Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					result = "success";
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in createBranchUser [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			ictAdminDAO = null;
			errors = null;
			messages = null;
		}

		return result;

	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getMultiData() {
		return multiData;
	}

	public void setMultiData(String multiData) {
		this.multiData = multiData;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionalHq() {
		return regionalHq;
	}

	public void setRegionalHq(String regionalHq) {
		this.regionalHq = regionalHq;
	}

	public String getHeadOfficeCode() {
		return headOfficeCode;
	}

	public void setHeadOfficeCode(String headOfficeCode) {
		this.headOfficeCode = headOfficeCode;
	}

	public String getHeadOfficeName() {
		return headOfficeName;
	}

	public void setHeadOfficeName(String headOfficeName) {
		this.headOfficeName = headOfficeName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
