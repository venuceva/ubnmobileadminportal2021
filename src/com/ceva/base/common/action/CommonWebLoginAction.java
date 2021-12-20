package com.ceva.base.common.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.UserBean;
import com.ceva.base.common.dao.CommonLoginDAO;
import com.ceva.base.common.dao.DashBoardLinkDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.AesUtil;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CommonWebLoginAction extends ActionSupport implements
		ServletRequestAware,ServletContextAware, ModelDriven<UserBean> {

	private static final long serialVersionUID = 658735490187966840L;
	private Logger logger = Logger.getLogger(CommonWebLoginAction.class);

	private HttpServletRequest request = null;
	private String result = null;

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
	JSONObject responseJSON1 = null;
	JSONObject responseJSON2 = null;


	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;

	private HttpSession session = null;
	ServletContext context;

	@Autowired
	private UserBean userBean = null;
	private String redirectPage = null;
	private String randomValue = null;
	private String salt = null;
	private String iv = null;
	private String passkey = null;
	private String remoteIp = null;
	private String graphType = null;
	private String graphType2 = null;
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String webLogin() {
		logger.debug("Inside WebLogin User ID [" + userBean.getUserid() + "]");
		CommonLoginDAO prepaidLoginDAO = null;
		String applName = "";
		String userStatus = "";
		String getHiddenPassword="";
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			//logger.debug("Encrypted Password [" + userBean.getEncryptPassword()+ "]");
			//logger.debug("getHiddenPassword [" + userBean.getHiddenPassword()+ "]");
			getHiddenPassword=userBean.getHiddenPassword();
			//logger.debug("getHiddenPassword length> [" +getHiddenPassword.length()+ "]");
			userBean.setPassword(getHiddenPassword);
			if (userBean.getUserid() == null || userBean.getUserid().length() < 1){
				addActionError("Please enter User Name.");
				redirectPage = "WEB-INF/jsp/login.jsp";
				result = "loginfail";
			}
			else if(userBean.getPassword() == null || userBean.getPassword().length() < 1){
				addActionError("Please enter password.");
				redirectPage = "WEB-INF/jsp/login.jsp";
				result = "loginfail";
			}else if ( getHiddenPassword.length() <= 7) {
				addActionError("Password Minimum 8 digits required.");
				redirectPage = "WEB-INF/jsp/login.jsp";
				result = "loginfail";
			} else {
				session = ServletActionContext.getRequest().getSession(false);
				applName = "banking";

				logger.debug("Application Name [" + applName + "]");

				randomValue = (String) session.getAttribute(CevaCommonConstants.RANDOM_VAL);
				salt = (String) session.getAttribute("SALT");
				iv = (String) session.getAttribute("IV");
				passkey = (String) session.getAttribute("PASSWORD");
				remoteIp = request.getRemoteHost();


				requestJSON.put(CevaCommonConstants.USER_ID,userBean.getUserid());
				requestJSON.put(CevaCommonConstants.PASSWORD,userBean.getPassword());
				requestJSON.put("TOKENS",userBean.getToken());
				
				requestJSON.put(CevaCommonConstants.APPL_NAME, applName);
				requestJSON.put(CevaCommonConstants.RANDOM_VAL, randomValue);
				requestJSON.put("PASSWORDKEY", passkey);
				requestJSON.put("SALT", salt);
				requestJSON.put("IV", iv);
				requestJSON.put("REMOTE_ADDR", request.getRemoteAddr());
				requestJSON.put("REMOTE_IP", remoteIp);
				requestJSON.put("getHiddenPassword", getHiddenPassword);
				requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

				//logger.debug("Request JSON [" + requestJSON + "]");

				requestDTO.setRequestJSON(requestJSON);
				prepaidLoginDAO = new CommonLoginDAO();
				responseDTO = prepaidLoginDAO.validatLogin(requestDTO);
				logger.debug("Response DTO[" + responseDTO);
				//session.setAttribute("dashbord",getDetdDashBoard("Yes",userBean.getUserid()));
				logger.debug("Old Session Id is [" +session.getId() + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					session.invalidate();
					session = ServletActionContext.getRequest().getSession(true);
					
					logger.debug(" Session Id validate [" +session.isNew() + "]");
					logger.debug("New Session Id is [" +session.getId() + "]");
					
					applName="banking";
					logger.debug("Appl Name ["+applName+"]");
					session.setAttribute(CevaCommonConstants.ACCESS_APPL_NAME, applName);
					
					//session.setAttribute(CevaCommonConstants.MAKER_ID,userBean.getUserid());
					session.setAttribute("dashbord",getDetdDashBoard("Yes",(String) responseDTO.getData().get("userid")));
					session.setAttribute(CevaCommonConstants.MAKER_ID,(String) responseDTO.getData().get("userid"));
					session.setAttribute("REMOTE_IP",request.getRemoteAddr());
					//session.setAttribute(CevaCommonConstants.PAN_DESC,userBean.getPassword());
					session.setAttribute(CevaCommonConstants.PAN_DESC,"");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.MENU_DATA);

					logger.debug("Response JSON is [" + responseJSON + "]");
					userStatus = (String) responseDTO.getData().get(
							"userStatus");
					logger.debug("UserStatus [" + userStatus
							+ "] LoginEntity ["
							+ (String) responseDTO.getData().get("LoginEntity")
							+ "]");
//System.out.println("location ::: "+responseDTO.getData().get("Location"));
					// The Below are the user details that we are carrying.
					session.setAttribute("username", (String) responseDTO.getData().get("userid"));
					session.setAttribute("loginEntity", (String) responseDTO.getData().get("LoginEntity"));
					session.setAttribute("usertype", (String) responseDTO.getData().get("usertype"));
					session.setAttribute("userLevel", (String) responseDTO.getData().get("UserLevel"));
					session.setAttribute("location", (String) responseDTO.getData().get("Location"));
					session.setAttribute("links_pid_query",(String) responseDTO.getData().get("links_pid_query"));
					session.setAttribute("loginTime", (String) responseDTO.getData().get("LoginTime"));
					session.setAttribute("userGroup", (String) responseDTO.getData().get("userGroup"));
					session.setAttribute("lastLogin", (String) responseDTO.getData().get("lastLogin"));
					session.setAttribute("userLevelname", (String) responseDTO.getData().get("userLevelname"));
					session.setAttribute("pid","1");
					session.setAttribute("windowsize",userBean.getWindowsize()+"px");
					
					session.setAttribute("session_id",request.getSession().getId());
					session.setAttribute("session_refno", AesUtil.md5(CommonUtil.getRandomInteger()));
					
					logger.debug("Request Session Id is [" +request.getSession().getId() + "]");
					//System.out.println("kailash here ::: "+context.getAttribute((String) responseDTO.getData().get("userid")));
					context.removeAttribute((String) responseDTO.getData().get("userid"));
					context.setAttribute((String) responseDTO.getData().get("userid"),request.getSession().getId());
					session.setAttribute("SALT", AesUtil.md5(CommonUtil.getRandomInteger()));
					
					logger.debug("Response builded.");

					if (userStatus.equals("A")) {
						session.setAttribute("MENU_DATA", responseJSON);
						redirectPage = "WEB-INF/jsp/commonLogin.jsp";
						result = "success";
					}
					if (userStatus.equals("F")) {
						redirectPage = "WEB-INF/jsp/changePassword.jsp";
						result = "firstlogin";
					}

				} else {
					logger.debug("Error from DB.");
					redirectPage = "WEB-INF/jsp/login.jsp";
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
						if(errors.get(i).equalsIgnoreCase("Internal Error Occured At Login.Please re-check and try again.")){
							redirectPage = "jsp/index.jsp";	
						}
						
					}
					
					
					result = "loginfail";
					//responseDTO = prepaidLoginDAO.validatLogin(requestDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Got exception webLogin is [" + e.getMessage() + "]");
			redirectPage = "WEB-INF/jsp/login.jsp";
			result = "loginfail";
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			prepaidLoginDAO = null;
			applName = null;
			errors = null;
		}
		logger.debug("Result [" + result + "]");
		return SUCCESS;
	}
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	

	public String changePassword() {
		boolean checkFlag = false;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		CommonLoginDAO prepaidLoginDAO = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			logger.debug("ChangePassword :: " + userBean.getUserid() + "-"
					+ userBean.getNewPassword());
			if (userBean.getUserid() == null) {
				addActionError("User ID Missing.");
			} else if (userBean.getNewPassword() == null) {
				addActionError("New Password Missing.");
			} else if (userBean.getConfirmNewPassword() == null) {
				addActionError("Confirm Password Missing.");
			} else if (userBean.getNewPassword().equals(
					userBean.getConfirmNewPassword())) {
				session = ServletActionContext.getRequest().getSession(false);
				requestJSON.put(CevaCommonConstants.USER_ID, userBean.getUserid());
				requestJSON.put(CevaCommonConstants.PASSWORD, userBean.getNewPassword());
				requestJSON.put(CevaCommonConstants.APPL_NAME, session
						.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME));
				requestDTO.setRequestJSON(requestJSON);
				prepaidLoginDAO = new CommonLoginDAO();
				responseDTO = prepaidLoginDAO.changePassword(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					logger.debug("Response DTO [" + responseDTO + "]");
					messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					}
					redirectPage = "WEB-INF/jsp/ChangePasswordAck.jsp";
					result = "success";
				} else {
					logger.debug("Getting error from DB");
					errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					redirectPage = "WEB-INF/jsp/changePassword.jsp";
					result = "loginfail";
				}
				checkFlag = true;
			} else {
				addActionError("New Password and Confirm New Password Not Matching.");
			}

			if (!checkFlag) {
				result = "loginfail";
			}

		} catch (Exception e) {
			logger.debug("Got exception in changePassword [" + e.getMessage()
					+ "]");
			redirectPage = "WEB-INF/jsp/changePassword.jsp";
			result = "loginfail";
		} finally {
			prepaidLoginDAO = null;
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
			messages = null;
		}

		return result;
	}

	public String redirectLogin() {
		logger.debug("Inside RedirectLogin... ");
		graphType = graphType == null ? "table" : graphType;
		graphType2 = graphType2 == null ? "combo" : graphType2;
		logger.debug("graphType... "+graphType);
		logger.debug("graphType2... "+graphType2);
		DashBoardLinkDAO DashDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			session = ServletActionContext.getRequest().getSession(false);

			logger.debug("Session Maker Id ["
					+ session.getAttribute(CevaCommonConstants.MAKER_ID) + "]");

			requestJSON.put(CevaCommonConstants.ROLE_GRP_ID,
					session.getAttribute("userGroup"));

			requestJSON.put("loc_name", session.getAttribute("location"));

			requestJSON.put(CevaCommonConstants.MAKER_ID, session
					.getAttribute(CevaCommonConstants.MAKER_ID) == null ? " "
					: session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request JSON In Redirect Login [" + requestJSON + "]");

			DashDAO = new DashBoardLinkDAO();
			responseDTO = DashDAO.getAssinedDashLinks(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				logger.debug("Response JSON [ into JC]");
				responseJSON = (JSONObject) responseDTO.getData().get(	CevaCommonConstants.DASHBOARD_LINKS);
				responseJSON1 = (JSONObject) responseDTO.getData().get(CevaCommonConstants.HALF_PAGE);
				responseJSON2 = (JSONObject) responseDTO.getData().get("gymcount");
				//responseJSON.put("graphType", graphType);
				//responseJSON.put("graphType2", graphType2);
				logger.debug("Response JSON [" + responseJSON + "]");
				logger.debug("Response JSON [" + responseJSON1 + "]");
				logger.debug("Response JSON2 [" + responseJSON2 + "]");

				result = "success";
			} else {
				logger.debug("Response JSON in errors []");
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.debug("Got exception RedirectLogin [" + e.getMessage() + "]");
			redirectPage = "WEB-INF/jsp/changePassword.jsp";
			result = "loginfail";
		} finally {
			DashDAO = null;
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return "success";
	}

	public String webLogOut() {
		logger.debug("Inside WebLogOut... ");
		String applName = null;
		HttpServletResponse response = null;
		Cookie[] cookies = null;

		try {

			session = ServletActionContext.getRequest().getSession(false);
			response = ServletActionContext.getResponse();
			logger.debug("Old session [" + session + "]");

			session = ServletActionContext.getRequest().getSession(false);
			request = ServletActionContext.getRequest();
			JSONObject object = new JSONObject();
			object.put("ip", request.getRemoteAddr());
			object.put("action", "logout");
			object.put("trancode", "logout");
			object.put("userid", session.getAttribute(CevaCommonConstants.MAKER_ID));
			object.put("transCode", "logout");
			new CommonLoginDAO().InsertAuditLog(object);
			response = ServletActionContext.getResponse();
			logger.debug("Old session [" + session + "]");

			if (session != null) {
				applName = (String) session
						.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME);
				cookies = ServletActionContext.getRequest().getCookies();

				try {
					session.invalidate();
					logger.debug("Invalidating Session and setting ...");
					if (cookies != null) {
						for (Cookie cokie : cookies) {
							cokie.setValue("");
							cokie.setPath("/");
							cokie.setMaxAge(0);
							response.addCookie(cokie);
						}
					}
					response.setHeader("Pragma", "no-cache");
					response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Expires", "0");
				} catch (Exception e) {
					logger.debug("Inside  session invaldiate check exception :: "
							+ e.getMessage());
				}

				session = ServletActionContext.getRequest().getSession(true);

				logger.debug("New session [" + session + "]");

				session.setAttribute(CevaCommonConstants.ACCESS_APPL_NAME,
						applName == null ? "banking" : applName);
				session.setAttribute(CevaCommonConstants.RANDOM_VAL,
						CommonUtil.getRandomInteger());
			}

		} catch (Exception e) {
			logger.debug("Got exception in webLogOut [ " + e.getMessage() + "]");
			result = "loginfail";
		} finally {
			applName = null;
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			cookies = null;
		}

		return SUCCESS;
	}
	
	
	public String getDetdDashBoard(String dashbord,String userid) {

		logger.debug("Inside getDetdDashBoard... " +userid);
		CommonLoginDAO prepaidLoginDAO = null;
		prepaidLoginDAO = new CommonLoginDAO();
		StringBuilder sb=new StringBuilder();
		
			HashMap<String,String> hm=prepaidLoginDAO.getUserDetails(userid);
			
			
			sb.append("<div class='row-fluid sortable'>"); 
			sb.append("<div class='box span12'> ");
			sb.append("<div class='box-header well' data-original-title>");
			sb.append("<i class='icon-edit'></i>Login User Details");
			sb.append("<div class='box-icon'>");
			sb.append("<a href='#' class='btn btn-setting btn-round'><i class='icon-cog'></i></a>");
			sb.append("<a href='#' class='btn btn-minimize btn-round'><i class='icon-chevron-up'></i></a>");
			sb.append("</div>");
			sb.append("</div> ");
			sb.append("<div class='box-content'>");
			sb.append("<fieldset>"); 
			sb.append("<table width='100%'   border='0' cellpadding='5' cellspacing='1'"); 
			sb.append("class='table table-striped table-bordered bootstrap-datatable ' >");
			sb.append("<tr > ");
			sb.append("<td  ><strong><label for='User Id'><strong> User Id</strong></label></strong></td>");
			sb.append("<td  >"+hm.get("CV0001")+" </td>  ");
			sb.append("<td  ><strong><label for='Employee No'><strong> Employee No</strong></label></strong></td>");
			sb.append("<td  >"+hm.get("CV0002")+"</td>");
			sb.append("</tr>");
			sb.append("<tr > ");
			sb.append("<td><strong><label for='First Name'><strong> First Name</strong></label></strong></td>");
			sb.append("<td>"+hm.get("CV0003")+" </td> ");
			sb.append("<td><strong><label for='Last Name'><strong> Last Name</strong></label></strong></td>");
			sb.append("<td>"+hm.get("CV0004")+"</td>");
			sb.append("</tr>");
			sb.append("<tr> ");
			sb.append("<td><strong><label for='Mobile'><strong> Mobile</strong></label></strong></td>");
			sb.append("<td>"+hm.get("CV0007")+"</td> ");
			sb.append("<td><strong><label for='Office Location'><strong> Office Location</strong></label></strong></td>");
			sb.append("<td>"+hm.get("CV0010")+" </td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</fieldset> ");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
		

		return sb.toString();
	}

	
	

	public String changePwd() {
		redirectPage = "WEB-INF/jsp/changePassword.jsp";
		return "success";

	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	
	public JSONObject getResponseJSON2() {
		return responseJSON2;
	}

	public void setResponseJSON2(JSONObject responseJSON2) {
		this.responseJSON2 = responseJSON2;
	}

	public JSONObject getResponseJSON1() {
		return responseJSON1;
	}

	public void setResponseJSON1(JSONObject responseJSON1) {
		this.responseJSON1 = responseJSON1;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	@Override
	public UserBean getModel() {
		return userBean;
	}

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}

	public String getGraphType2() {
		return graphType2;
	}

	public void setGraphType2(String graphType2) {
		this.graphType2 = graphType2;
	}

}
