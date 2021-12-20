package com.ceva.base.ceva.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.bean.AccountBean;
import com.ceva.base.common.dao.AccountPropertiesDAO;
import com.ceva.base.common.dao.AjaxDAO;
import com.ceva.base.common.dao.AssignTerminalAjaxDAO;
import com.ceva.base.common.dao.ServiceMgmtAjaxDAO;
import com.ceva.base.common.dao.SwitchUIDAO;
import com.ceva.base.common.dao.UserAjaxDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;
import com.ceva.util.QueryactiveDirectory;
import com.opensymphony.xwork2.ActionSupport;

public class ServicesJsonAjaxAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(ServicesJsonAjaxAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private String hoffice = null;
	private String region = null;
	private String location = null;
	private String method = null;
	private String selectedSelBox = null;
	private String fillSelectBox = null;

	private String entity = null;
	private String groupId = null;
	private String userId = null;
	private String employeeNo = null;
	private String dlNo = null;
	private String status = null;

	private Map<String, String> details;
	private int finalCount = 0;


	private String brcode;
	private String prdid;
	

	private String refno;
	private String makerid;
	private String multidata;
	private String email;
	private String telco;
	private String isocode;
	private String telephone;
	private String auth_code;
	private String language;
	private String Institute;
	

	private HttpSession session = null;
	private HttpServletRequest request;
	private ResourceBundle rb = null;
	private String result;

	@Override
	public String execute() throws Exception {
		logger.debug("Inside Execute Method.");
		logger.debug("Execute method [" + method + "] ");
		String result = ERROR;

		try {
			if (method.equalsIgnoreCase("searchData")) {
				result = searchData();
			} else if (method.equalsIgnoreCase("searchEntity")) {
				result = checkCountGroup();
			} else if (method.equalsIgnoreCase("fetchProducts")) {
			result = fetchProducts();
		} 
			
		} catch (Exception e) {
			logger.debug("Inside execute [" + e.getMessage() + "]");
		} finally {

		}

		return result;
	}

	public String checkCountGroup() {
		logger.debug("Inside CheckCountGroup.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			connection = DBConnector.getConnection();
			queryConst = "select count(*) from user_groups where upper(group_id)=?";
			entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
			entityPstmt.setString(1, groupId);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}

	
	public String fetchproduct() {
		logger.debug("Inside CheckCountGroup.. ");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		String queryConst = "";
		Connection connection = null;
		try {
			connection = DBConnector.getConnection();
			queryConst = "select count(*) from user_groups where upper(group_id)=?";
			entityPstmt = connection.prepareStatement(queryConst.toUpperCase());
			entityPstmt.setString(1, groupId);
			entityRS = entityPstmt.executeQuery();

			if (entityRS.next()) {
				finalCount = entityRS.getInt(1);
			}

			logger.debug("Got Count [" + finalCount + "]");

		} catch (Exception e) {
			logger.debug("Got error [" + e.getMessage() + "]");
			finalCount = 0;
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
		}
		return SUCCESS;
	}
	

	public String searchData() {
		logger.debug("Inside SearchData.. ");
		String queryConst = "";
		logger.debug("Region [" + getRegion() + "]");
		logger.debug("Head Office [" + getHoffice() + "]");
		logger.debug("Location [" + getLocation() + "]");
		logger.debug("Selected Select Box [" + getSelectedSelBox() + "]");

		PreparedStatement entityPstmt = null;
		ResultSet entityRS = null;
		Connection connection = null;
		try {

			details = new HashMap<String, String>();
			connection = DBConnector.getConnection();

			if (getSelectedSelBox().equalsIgnoreCase("region")) {
				queryConst = "Select distinct HPO_DEPARTMENT_CODE,HPO_NAME from BRANCH_MASTER where HPO_FLAG='HEAD' and REGION_CODE in ("
						+ getRegion() + ") ORDER BY HPO_DEPARTMENT_CODE";
				// queryConst =
				// "Select distinct HPO_DEPARTMENT_CODE,HPO_NAME from IMP_BRANCH_MASTER where HPO_FLAG='HEAD' and REGION_CODE in ("+getRegion()+") ORDER BY HPO_DEPARTMENT_CODE";
				fillSelectBox = "headOffice";
			} else if (getSelectedSelBox().equalsIgnoreCase("headOffice")) {
				queryConst = "Select distinct OFFICE_CODE,OFFICE_NAME from BRANCH_MASTER where HPO_FLAG is null  and HPO_DEPARTMENT_CODE in ("
						+ getHoffice() + ") ORDER BY OFFICE_CODE";
				// queryConst =
				// "Select distinct OFFICE_CODE,OFFICE_NAME from IMP_BRANCH_MASTER where HPO_FLAG is null  and HPO_DEPARTMENT_CODE in ("+getHoffice()+") ORDER BY OFFICE_CODE";
				fillSelectBox = "Location";
			} else if (getSelectedSelBox().equalsIgnoreCase("Location")) {
				// queryConst =
				// "select distinct system_user_id,login_user_id from user_id_mapping where system_user_id in (select user_id from user_master where upper(branch_location) in ("
				// + getLocation().toUpperCase().trim() + "))";
				queryConst = "select LOGIN_USER_ID from USER_LOGIN_CREDENTIALS where COMMON_ID in "
						+ "(select COMMON_ID from USER_INFORMATION where upper(location) in ("
						+ getLocation() + ") and upper(USER_LEVEL)='USER')";
				fillSelectBox = "userid";
			}

			logger.debug("QueryConst [" + queryConst + "]");
			entityPstmt = connection.prepareStatement(queryConst);
			entityRS = entityPstmt.executeQuery();

			while (entityRS.next()) {

				if (fillSelectBox.equalsIgnoreCase("Location")) {

					details.put(entityRS.getString(1), entityRS.getString(1)
							+ "-" + entityRS.getString(2));
				} else if (fillSelectBox.equalsIgnoreCase("userid")) {

					details.put(entityRS.getString(1), entityRS.getString(1));
				} else {
					details.put(entityRS.getString(1), entityRS.getString(1)
							+ "-" + entityRS.getString(2));
				}
			}

		} catch (Exception e) {
			setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closePreparedStatement(entityPstmt);
			DBUtils.closeResultSet(entityRS);
			queryConst = null;
		}

		logger.debug("Details are : " + details);

		return SUCCESS;
	}
	
	public String fetchProducts() {
		logger.debug("Inside Get Products");
		ArrayList<String> errors = null;
		AccountPropertiesDAO accpropDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("into 1");
			//requestJSON.put(CevaCommonConstants.MAKER_ID,session.getAttribute(CevaCommonConstants.MAKER_ID));
			//logger.debug(session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("Institute",Institute);
			logger.debug("Request DTO [" + requestDTO + "]");
			accpropDAO = new AccountPropertiesDAO();
			responseDTO = accpropDAO.fetchProducts(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("PRODUCT_LIST");
				logger.debug("Response JSON [" + responseJSON + "]");
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in checkSwitchStaus [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
			result = "fail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			accpropDAO = null;
		}

		return SUCCESS;
	}
	

	public String getInstitute() {
		return Institute;
	}

	public void setInstitute(String institute) {
		Institute = institute;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHoffice() {
		return hoffice;
	}

	public void setHoffice(String hoffice) {
		this.hoffice = hoffice;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getFillSelectBox() {
		return fillSelectBox;
	}

	public void setFillSelectBox(String fillSelectBox) {
		this.fillSelectBox = fillSelectBox;
	}

	public String getSelectedSelBox() {
		return selectedSelBox;
	}

	public void setSelectedSelBox(String selectedSelBox) {
		this.selectedSelBox = selectedSelBox;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getFinalCount() {
		return finalCount;
	}

	public void setFinalCount(int finalCount) {
		this.finalCount = finalCount;
	}

	public String getDlNo() {
		return dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}


	public JSONObject getRequestJSON() {
		return requestJSON;
	}

	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
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

	

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}



	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getPrdid() {
		return prdid;
	}

	public void setPrdid(String prdid) {
		this.prdid = prdid;
	}
	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getMakerid() {
		return makerid;
	}

	public void setMakerid(String makerid) {
		this.makerid = makerid;
	}

	public String getMultidata() {
		return multidata;
	}

	public void setMultidata(String multidata) {
		this.multidata = multidata;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelco() {
		return telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public String getIsocode() {
		return isocode;
	}

	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	
}
