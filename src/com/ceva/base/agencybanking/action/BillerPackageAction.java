package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.bean.BillerPackages;
import com.ceva.base.common.dao.BillerPackageDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BillerPackageAction extends ActionSupport implements
		ServletRequestAware, ModelDriven<BillerPackages> {

	Logger logger = Logger.getLogger(BillerPackageAction.class);

	@Autowired
	BillerPackages billerPackage;

	@Autowired
	BillerPackageDAO billerPackageDAO;

	HttpServletRequest request;

	public List<BillerPackages> packages = null;

	public BillerPackageAction() {

	}

	public String getPacks() {
		logger.info("getting listof packages for billers."+billerPackage.getBillerId());
		ResponseDTO responseDTO =  billerPackageDAO.getAll(billerPackage);
		packages =(List<BillerPackages>) responseDTO.getData().get(CevaCommonConstants.OBJECT);
		logger.info("packages..:"+packages.size());
		return SUCCESS;
	}

	public String update() {
		return SUCCESS;
	}
	public String save() {
		logger.info(" saving package...");
		logger.info(billerPackage.toString());
		String result = "";
		RequestDTO requestDTO = new RequestDTO();
		ResponseDTO responseDTO = null;
		JSONObject requestJSON = new JSONObject();
		List<String> errors  = null;
		try{
			billerPackage.setMaker(request.getSession().getAttribute(CevaCommonConstants.MAKER_ID)+"");
			billerPackage.setStatus("A");
			requestJSON.put(CevaCommonConstants.IP, request.getRemoteAddr());
			requestDTO.setBean(billerPackage);
			requestDTO.setRequestJSON(requestJSON);
			responseDTO = billerPackageDAO.save(requestDTO);
			if(responseDTO.getErrors().size()==0){
				result = SUCCESS;
				logger.info("Biller package created successfully..");
				addActionMessage("Biller Package Created Successfully.");
			}else{
				errors = responseDTO.getErrors();
				for(String error : errors){
					logger.info("Error While creating biller package.");
					addActionError(error);
				}
				result = ERROR;
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error..:"+e.getLocalizedMessage());
			logger.debug("Error..:"+e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	public String execute() {
		logger.info("loading Create Page...");
		logger.info(billerPackage.toString());

		return SUCCESS;
	}

	@Override
	public BillerPackages getModel() {
		return billerPackage;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setPackages(List<BillerPackages> packages) {
		this.packages = packages;
	}

	private List<BillerPackages> getStaticList(){
		BillerPackages pack=null;
		List<BillerPackages> packages = new ArrayList<BillerPackages>();
		for(int i=0; i<10; i++){
			pack = new BillerPackages();
			pack.setBillerId("123123");
			pack.setPackageId("123");
			pack.setPackageName("Utility :"+i);
			pack.setBillerId(billerPackage.getBillerId());
			pack.setAmount((10*(i+1))+"");
			pack.setStatus("Active");
			//logger.info(pack.toString());
			packages.add(pack);
			}
		return packages;
	}


}
