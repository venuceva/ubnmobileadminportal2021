package com.ceva.base.agencybanking.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


import com.ceva.base.common.dao.DashBoardLinkDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DashBoardLinkAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DashBoardLinkAction.class);

	private String result;

	private JSONObject responseJSON = null;
	private JSONObject responseJSON2 = null;
	private JSONObject responseJSON1 = null;

	private JSONObject requestJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	// @Autowired
	// private DashboardBean dashboardBean = null;
	private HttpSession session = null;

	private String roleGroupId;

	private String location;

	private String groupId;

	private String selectUsers;
	private String userId;


	public String commonScreen() {
		logger.debug("inside GetCommonScreen... ");
		result = "success";
		return result;
	}

	public String groupDashBoard() {
		logger.debug("Inside getGroupDashBoard. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					roleGroupId == null ? " " : roleGroupId);
			requestJSON.put("GROUP_ID", groupId == null ? " " : groupId);
			requestJSON.put("USER_ID", userId == null ? " " : userId);

			logger.debug("Role Id [" + roleGroupId + "]");

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");
			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.getDashBoardLinks(requestDTO);
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
			logger.debug("Exception in getGroupDashBoard [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			DashDAO = null;
			errors = null;
		}

		return result;
	}

	public String insertDashBoardLinks() {
		logger.debug("inside insertDashBoardLinks. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession(false);

			String makerId = session.getAttribute("makerId").toString();
			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID, groupId);
			requestJSON.put("user_id", userId);
			requestJSON.put(CevaCommonConstants.TXN_DATA, selectUsers);
			requestJSON.put(CevaCommonConstants.MAKER_ID, makerId == null ? "NO_VAL" : makerId);
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request JSON [" + requestJSON + "]");
			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.insertDashBoardLinks(requestDTO);
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
			logger.debug("Exception in insertDashBoardLinks [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			DashDAO = null;
			errors = null;
		}

		return result;
	}

	public String assinedDashLinks() {
		logger.debug("Inside Get AssinedDashLinks. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					roleGroupId == null ? " " : roleGroupId);

			/*requestJSON.put(
					"loc_name",
					session.getAttribute("location") == null ? "NO" : session
							.getAttribute("location"));*/

			requestJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? " "
					: session.getAttribute(CevaCommonConstants.MAKER_ID));

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON In InsertDashBoardLinks [" + requestJSON
					+ "]");

			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.getAssinedDashLinks(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.DASHBOARD_LINKS);
				responseJSON1 = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.HALF_PAGE);

				logger.debug("Response JSON [" + responseJSON + "]");
				logger.debug("Response JSON1 [" + responseJSON1 + "]");

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
			logger.error("Exception in getAssinedDashLinks is", e);
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			DashDAO = null;
			errors = null;
		}

		return result;
	}

	/*public String userAssinedDashLinks() {
		logger.debug("Inside UserAssinedDashLinks. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession(false);

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					session.getAttribute("userGroup"));

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute("makerId"));
			requestJSON.put(CevaCommonConstants.IP,ServletActionContext.getRequest().getRemoteAddr());

			//requestJSON.put("loc_name", session.getAttribute("location"));

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");
			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.getAssinedDashLinks(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				setResponseJSON((JSONObject) responseDTO.getData().get(CevaCommonConstants.DASHBOARD_LINKS));
				setResponseJSON1((JSONObject) responseDTO.getData().get(CevaCommonConstants.HALF_PAGE));
				setResponseJSON2( (JSONObject) responseDTO.getData().get("gymcount"));

				logger.debug("Response JSON [" + responseJSON + "]");
				logger.debug("Response JSON1 [" + responseJSON1 + "]");
				logger.debug("Response JSON2 [" + responseJSON2 + "]");
				session.setAttribute("pid","1");
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
			logger.debug("Exception in UserAssinedDashLinks ["		+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
		
			DashDAO = null;
			errors = null;
		}

		return result;
	}*/
	
	
	public String userAssinedDashLinksHome() {
		logger.debug("Inside UserAssinedDashLinks. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession(false);

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					session.getAttribute("userGroup"));

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute("makerId"));
			requestJSON.put(CevaCommonConstants.IP,ServletActionContext.getRequest().getRemoteAddr());

			//requestJSON.put("loc_name", session.getAttribute("location"));

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");
			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.userAssinedDashLinksHome(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				
				session.setAttribute("pid","1");
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
			logger.debug("Exception in UserAssinedDashLinks ["		+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
		
			DashDAO = null;
			errors = null;
		}

		return result;
	}
	
	
	public String userAssinedDashLinks() {
		logger.debug("Inside UserAssinedDashLinks. ");
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession(false);

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					session.getAttribute("userGroup"));

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute("makerId"));
			requestJSON.put(CevaCommonConstants.IP,ServletActionContext.getRequest().getRemoteAddr());

			//requestJSON.put("loc_name", session.getAttribute("location"));

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON [" + requestJSON + "]");
			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.getAssinedDashLinks(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				
				session.setAttribute("pid","1");
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
			logger.debug("Exception in UserAssinedDashLinks ["		+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
		
			DashDAO = null;
			errors = null;
		}

		return result;
	}


	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;

	}

	public JSONObject getResponseJSON1() {
		return responseJSON1;
	}

	public void setResponseJSON1(JSONObject responseJSON1) {
		this.responseJSON1 = responseJSON1;
	}

	public JSONObject getResponseJSON2() {
		return responseJSON2;
	}

	public void setResponseJSON2(JSONObject responseJSON2) {
		this.responseJSON2 = responseJSON2;
	}

	/*
	 * public DashboardBean getDashboardBean() { return dashboardBean; }
	 *
	 * public void setDashboardBean(DashboardBean dashboardBean) {
	 * this.dashboardBean = dashboardBean; }
	 */

	public String getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;

	}

	public String getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(String selectUsers) {
		this.selectUsers = selectUsers;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * @Override public DashboardBean getModel() {
	 * System.out.println(dashboardBean.getResponseJSON()); return
	 * dashboardBean; }
	 */

}
