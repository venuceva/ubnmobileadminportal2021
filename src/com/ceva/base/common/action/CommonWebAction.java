package com.ceva.base.common.action;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.utils.AesUtil;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class CommonWebAction extends ActionSupport { 

	private static final long serialVersionUID = 1169823183008588660L;
	Logger logger=Logger.getLogger(CommonWebAction.class);
	private String result;
	private String task;
	private String applName;
	private String redirectPage;

	public String execute(){

		String randomVal= null;
		HttpSession session= null;
		try {
			logger.debug("Inside CommonWebAction..");
 			applName="banking";
			logger.debug("Appl Name ["+applName+"]");
			session=ServletActionContext.getRequest().getSession();  
			session.setAttribute(CevaCommonConstants.ACCESS_APPL_NAME, applName);
			randomVal=CommonUtil.getRandomInteger();
			logger.debug("Random Val ["+randomVal+"]");
			session.setAttribute(CevaCommonConstants.RANDOM_VAL, randomVal);
			session.setAttribute("PASSWORD", "P@()34Hjk29$#@bdeHYE!");
			session.setAttribute("SALT", AesUtil.md5(randomVal));
			session.setAttribute("IV", "01928374565648392012910388456912");
			if(applName==null){
				redirectPage="WEB-INF/jsp/InvalidURL.jsp";
				result="invalid";
			}
			else{
				redirectPage="WEB-INF/jsp/login.jsp";
				result="valid";
			}
			logger.debug("Result["+result+"]");

		} catch (Exception e) {
			 
		} finally{
			randomVal= null;
		} 
		return result;
	}

	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}


}
