package com.ceva.interceptors;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ActionIdentificationInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;

	private Logger log = Logger.getLogger(getClass());
	private String result = null;
	private String insQry = "";

	private String actionName = "";
	private String className = "";
	private String method = "";
	private String userid = "";
	private String ip = "";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		CallableStatement cstmt = null;
		Connection connection = null;

		insQry = "{call AUDITPKG.INSERTAUDITTRAIL(?,?,?,?,?,?,?,?)}";

		try {

			session = ServletActionContext.getRequest().getSession();
			request = ServletActionContext.getRequest();

			log.debug("|ActionIdentificationInterceptor| Client Address     : "
					+ request.getRemoteAddr());
			log.debug("|ActionIdentificationInterceptor| Character Encoding : "
					+ request.getCharacterEncoding());
			log.debug("|ActionIdentificationInterceptor| Content Length     : "
					+ request.getContentLength());

			result = invocation.invoke();

			actionName = invocation.getProxy().getActionName();

			ip = request.getRemoteAddr();
			log.debug("Action name is [" + actionName + "]");

			className = invocation.getAction().getClass().getName();
			method = invocation.getProxy().getMethod();

			userid = (String) session.getAttribute("makerId");

			connection = DBConnector.getConnection();

			cstmt = connection.prepareCall(insQry);

			cstmt.setString(1, actionName);
			cstmt.setString(2, userid);
			cstmt.setString(3, "WEB");
			cstmt.setString(4, " Action View By ");
			cstmt.setString(5, ip);
			cstmt.setString(6, className);
			cstmt.setString(7, method);
			cstmt.setString(8, " ");

			int i = cstmt.executeUpdate();
			if (i == 1) {
				log.debug("Action Identification Interceptor -> Successfully Inserted "
						+ ip);
			} else {
				log.debug("Action Identification Interceptor -> insertion failed due to some error "
						+ ip);
			}
		} catch (SQLException e) {
			log.debug(" |ActionIdentificationInterceptor| Interceptor got SQLException ::: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("|ActionIdentificationInterceptor| Interceptor got Exception ::: "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			insQry = null;
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(cstmt);
		}
		return result;
	}

}
