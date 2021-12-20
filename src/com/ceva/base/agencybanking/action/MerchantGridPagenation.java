package com.ceva.base.agencybanking.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.MerchantPageGridPagenationDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
public class MerchantGridPagenation extends ActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(MerchantGridPagenation.class);

	private JSONObject requestJSON=null;
	private RequestDTO requestDTO=null;
	private ResponseDTO responseDTO=null;
	private int iDisplayStart;
	private int iDisplayLength;
	private int iSortCol_0;
	private int sSortDir_0;
	private String sSearch;
	private int sEcho;
	private String userinfo;
	private JSONObject responseJSON=null;

public String getMerchantPages() throws Exception{

		logger.debug("inside execute....   ");
		try {
			logger.debug("inside gtPagination....   ");

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();

				requestDTO.setRequestJSON(requestJSON);
				//JSONObject jsonResult = null;
				requestJSON.put(CevaCommonConstants.PAGE_SIZE, getiDisplayLength());
				requestJSON.put(CevaCommonConstants.PAGE_NO, getiDisplayStart());
				requestJSON.put(CevaCommonConstants.COL_INDEX, getiSortCol_0());
				requestJSON.put(CevaCommonConstants.SORT_DIR, getsSortDir_0());
				requestJSON.put(CevaCommonConstants.DIR, "asc");
				requestJSON.put(CevaCommonConstants.START, ServletActionContext.getRequest().getParameter("iDisplayStart"));
				requestJSON.put(CevaCommonConstants.END, new Integer(ServletActionContext.getRequest().getParameter("iDisplayStart"))+new Integer(ServletActionContext.getRequest().getParameter("iDisplayLength")));
				requestJSON.put(CevaCommonConstants.QUERY_ID, ServletActionContext.getRequest().getParameter("qid"));
				requestJSON.put(CevaCommonConstants.PAGE_SEARCH, ServletActionContext.getRequest().getParameter("sSearch"));
				requestJSON.put(CevaCommonConstants.PAGE_SEARCH, ServletActionContext.getRequest().getParameter("sSearch"));
				String Service=ServletActionContext.getRequest().getParameter("service");
				logger.debug("Service  ::: 144  "+Service);

				switch(Service)
					{
					case "Merchant":
						requestJSON.put("Service", Service);
						break;

					case "Store":
						requestJSON.put("Service", Service);
						requestJSON.put("MERCHANT_ID", ServletActionContext
								.getRequest().getParameter("merchantId"));
						break;

					default:

						break;

					}

				requestDTO=new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				responseJSON = new MerchantPageGridPagenationDAO().getMerchantPages(requestDTO);

					logger.debug("responseJSON:::"+responseJSON);
					HttpServletResponse response = ServletActionContext.getResponse();

				//responseJSON=jsonResult;
					PrintWriter out = response.getWriter();
					out.print(responseJSON);
					out.flush();
					out.close();


			} catch (Exception e) {
				logger.debug("Exception in GetProcessingCodeViewDetails ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
			}
		return SUCCESS;
	}

public int getiDisplayStart() {
	return iDisplayStart;
}

public void setiDisplayStart(int iDisplayStart) {
	this.iDisplayStart = iDisplayStart;
}

public int getiDisplayLength() {
	return iDisplayLength;
}

public void setiDisplayLength(int iDisplayLength) {
	this.iDisplayLength = iDisplayLength;
}

public int getiSortCol_0() {
	return iSortCol_0;
}

public void setiSortCol_0(int iSortCol_0) {
	this.iSortCol_0 = iSortCol_0;
}

public int getsSortDir_0() {
	return sSortDir_0;
}

public void setsSortDir_0(int sSortDir_0) {
	this.sSortDir_0 = sSortDir_0;
}

public String getsSearch() {
	return sSearch;
}

public void setsSearch(String sSearch) {
	this.sSearch = sSearch;
}

public int getsEcho() {
	return sEcho;
}

public void setsEcho(int sEcho) {
	this.sEcho = sEcho;
}

public String getUserinfo() {
	return userinfo;
}

public void setUserinfo(String userinfo) {
	this.userinfo = userinfo;
}


public JSONObject getResponseJSON() {
	return responseJSON;
}

public void setResponseJSON(JSONObject responseJSON) {
	this.responseJSON = responseJSON;
}


}
