package com.ceva.base.ceva.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dao.PagenationDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;


public class PagenationAction extends ActionSupport {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(PagenationAction.class);
	JSONObject requestJSON=null;
	RequestDTO requestDTO=null;
	ResponseDTO responseDTO=null;
	private int iDisplayStart;
	private int iDisplayLength;
	private int iSortCol_0;
	private int sSortDir_0;
	private String sSearch;
	private int sEcho;
	private String GID;
	private String userinfo;
	private HttpSession session = null;
	private JSONObject responseJSON=null;


	public String getPages() throws Exception{
		
		session = ServletActionContext.getRequest().getSession();
		logger.debug("inside execute....   ");
		try {
			logger.debug("inside gtPagination....   ");

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				requestDTO.setRequestJSON(requestJSON);
				JSONObject jsonResult = null;
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
				
			
				
				requestDTO=new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				jsonResult = new PagenationDAO().getPages(requestDTO);
		
					logger.debug("jsonResult:::"+jsonResult);
					HttpServletResponse response = ServletActionContext.getResponse();

				//responseJSON=jsonResult;
					PrintWriter out = response.getWriter();
					out.print(jsonResult);
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
	
public String getUserdata() throws Exception{
		
		session = ServletActionContext.getRequest().getSession();
		logger.debug("inside execute....   ");
		try {
			logger.debug("inside gtPagination....   ");

				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();

				requestDTO.setRequestJSON(requestJSON);
				JSONObject jsonResult = null;
				requestJSON.put(CevaCommonConstants.PAGE_SIZE, getiDisplayLength());
				requestJSON.put(CevaCommonConstants.PAGE_NO, getiDisplayStart());
				requestJSON.put(CevaCommonConstants.COL_INDEX, getiSortCol_0());
				requestJSON.put(CevaCommonConstants.SORT_DIR, getsSortDir_0());
				requestJSON.put(CevaCommonConstants.DIR, "asc");
				requestJSON.put(CevaCommonConstants.START, ServletActionContext.getRequest().getParameter("iDisplayStart"));
				requestJSON.put(CevaCommonConstants.END, new Integer(ServletActionContext.getRequest().getParameter("iDisplayStart"))+new Integer(ServletActionContext.getRequest().getParameter("iDisplayLength")));
				requestJSON.put(CevaCommonConstants.QUERY_ID, ServletActionContext.getRequest().getParameter("qid"));
				requestJSON.put(CevaCommonConstants.PAGE_SEARCH, ServletActionContext.getRequest().getParameter("sSearch"));
				logger.debug("GID::"+GID);
				//logger.debug("::::::::::::"+ServletActionContext.getRequest().getParameter("janaki"));
				requestDTO=new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				jsonResult = new PagenationDAO().getPages(requestDTO);
		
					logger.debug("jsonResult:::"+jsonResult);
					HttpServletResponse response = ServletActionContext.getResponse();

				//responseJSON=jsonResult;
					PrintWriter out = response.getWriter();
					out.print(jsonResult);
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

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
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

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public String getGID() {
		return GID;
	}

	public void setGID(String gID) {
		GID = gID;
	}

	public String getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}




}
