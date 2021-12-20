package com.ceva.base.agencybanking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.dao.BillerDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BillerAction extends ActionSupport implements ServletRequestAware,
		ModelDriven<Biller> {
	Logger logger = Logger.getLogger(BillerAction.class);

	@Autowired
	private Biller biller;

	@Autowired
	private BillerDAO billerDAO;

	HttpServletRequest request;

	private List<Biller> billers = null;

	public String execute() {
		return SUCCESS;
	}

	public String create() {
		logger.info("create Biller Page");

		return SUCCESS;
	}

	public String save() {
		logger.info("save Biller Page");
		logger.info(biller.toString());
		String result = "";
		RequestDTO requestDTO = new RequestDTO();
		ResponseDTO responseDTO = null;
		JSONObject requestJSON = new JSONObject();
		List<String> errors  = null;
		try {
			biller.setMaker(request.getSession().getAttribute(CevaCommonConstants.MAKER_ID)+"");
			biller.setStatus("N");
			requestJSON.put(CevaCommonConstants.IP, request.getRemoteAddr());
			requestDTO.setBean(biller);
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = billerDAO.save(requestDTO);
			if(responseDTO.getErrors().size()==0){
				result = SUCCESS;
				addActionMessage("Biller Created Successfully, Authorization Required");
			}else{
				errors = responseDTO.getErrors();
				for(String error : errors){
					addActionError(error);
				}
				result = ERROR;
			}

		} catch (Exception e) {
			result = ERROR;
			logger.error("Error Occured.." + e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			requestDTO = null;
			responseDTO = null;
		}
		return result;
	}
	public String billers() {
		logger.info("billers list Page");
		logger.info(biller.toString());
		ResponseDTO responseDTO = null;
		try {
			responseDTO = billerDAO.getAll(biller.getCatId());
			billers = (List<Biller>) responseDTO.getData().get(CevaCommonConstants.OBJECT);
			if(biller != null){
				logger.info(""+billers.size());
			}else{
				logger.info("No Bilers found with category.");
			}
		} catch (Exception e) {
			logger.error("Error Occured.." + e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			responseDTO = null;
		}
		return SUCCESS;
	}

	public String auth() {
		logger.info("AUTH Biller");
		logger.info(biller.toString());
		String result = "";
		RequestDTO requestDTO = new RequestDTO();
		ResponseDTO responseDTO = null;
		JSONObject requestJSON = new JSONObject();
		List<String> errors  = null;
		try {
			biller.setAuth(request.getSession().getAttribute(CevaCommonConstants.MAKER_ID)+"");
			requestJSON.put(CevaCommonConstants.IP, request.getRemoteAddr());
			requestDTO.setBean(biller);
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = billerDAO.auth(requestDTO);
			if(responseDTO.getErrors().size()==0){
				result = SUCCESS;
				addActionMessage("Biller Authorization Completed Successfully.");
			}else{
				errors = responseDTO.getErrors();
				for(String error : errors){
					addActionError(error);
				}
				result = ERROR;
			}

		} catch (Exception e) {
			result = ERROR;
			logger.error("Error Occured.." + e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			requestDTO = null;
			responseDTO = null;
		}
		return result;
	}
	public String update() {
		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	@Override
	public Biller getModel() {
		return biller;
	}

	public List<Biller> getBillers() {
		return billers;
	}

	public void setBillers(List<Biller> billers) {
		this.billers = billers;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

}
