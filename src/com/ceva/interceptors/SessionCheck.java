package com.ceva.interceptors;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionCheck extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	Logger logger=Logger.getLogger(SessionCheck.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String userId = null;
		String appl = null;
		String act = null;

		logger.debug("|SessionCheck| Before Action Invocation" );
		act = invocation.invoke();
		logger.debug("|SessionCheck| After Action Invocation" );

		/*HttpServletResponse response = (HttpServletResponse)invocation.  
				getInvocationContext().get(StrutsStatics.HTTP_RESPONSE); 
		HttpServletRequest request = (HttpServletRequest)invocation.  
				getInvocationContext().get(StrutsStatics.HTTP_REQUEST);*/ 
		logger.debug("|SessionCheck| Inside." );
		// Get the session  
		Map<String, Object> session = ActionContext.getContext().getSession();  

		try {
			userId = (String) session.get("makerId");
			appl = (String) session.get("ACCESS_APPL_NAME");
		} catch (Exception e) {
			logger.debug("|SessionCheck| exception is : "+ e.getMessage());
			userId = null;
			appl = null;
		}
		logger.debug("|SessionCheck| userId ["+userId+"] appl["+appl+"]" );

		if(userId == null //||userId.equalsIgnoreCase("null") 
				|| appl == null)//|| appl.equalsIgnoreCase("null")) 
		{
			logger.debug("|SessionCheck| Inside null check." );
			//request.getRequestDispatcher("logout.action").forward(request, response);
			return "loginRedirect";
		} 

		return act;
	}

}
