package com.ceva.base.limitfee.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.product.dao.LimitDao;
import com.ceva.base.common.product.dao.ProductManDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

public class LimitCreateAction  extends ActionSupport {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(LimitCreateAction.class);

	JSONObject requestJSON = null;
	JSONObject responseJSON = null;

	RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	String result=null;
	
	private String limitCode,limitDescription,finaljson;
	private String feefinaljson,feeCode,feeDescription;
	
	private HttpSession session = null;
	
	 private String limitDesc;
	 private String trrefno;
	 private String linkmode;
	 
	 
	 private String transaction;
	 private String frequency;
	 private String maxcount;
	 private String minamount;
	 private String maxamount;
	 private String seqno;
	 
	 private String flatpercentile;
	 private String fpValue;
	 private String criteria;
	 private String fromvalue;
	 private String tovalue;
	 
	 private String productcode;	 
	 private String productdesc;
	 private String productcurr;
	 private String application;
	 
	 private String agent;
	 private String ceva;
	 private String bank;
	 private String channel;
	 private String SuperAgent;
	 private String VAT;
	 private String subagent;
	 private String thirdparty;
	 private String qt;
	 
	 
	 
	 
	public String getQt() {
		return qt;
	}
	public void setQt(String qt) {
		this.qt = qt;
	}
	public String getThirdparty() {
		return thirdparty;
	}
	public void setThirdparty(String thirdparty) {
		this.thirdparty = thirdparty;
	}
	public String getSubagent() {
		return subagent;
	}
	public void setSubagent(String subagent) {
		this.subagent = subagent;
	}
	public String getSuperAgent() {
		return SuperAgent;
	}
	public void setSuperAgent(String superAgent) {
		SuperAgent = superAgent;
	}
	public String getVAT() {
		return VAT;
	}
	public void setVAT(String vAT) {
		VAT = vAT;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getCeva() {
		return ceva;
	}
	public void setCeva(String ceva) {
		this.ceva = ceva;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public String getProductcurr() {
		return productcurr;
	}
	public void setProductcurr(String productcurr) {
		this.productcurr = productcurr;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	

	public String getFlatpercentile() {
		return flatpercentile;
	}
	public void setFlatpercentile(String flatpercentile) {
		this.flatpercentile = flatpercentile;
	}
	public String getFpValue() {
		return fpValue;
	}
	public void setFpValue(String fpValue) {
		this.fpValue = fpValue;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getFromvalue() {
		return fromvalue;
	}
	public void setFromvalue(String fromvalue) {
		this.fromvalue = fromvalue;
	}
	public String getTovalue() {
		return tovalue;
	}
	public void setTovalue(String tovalue) {
		this.tovalue = tovalue;
	}
	public String getMaxcount() {
		return maxcount;
	}
	public void setMaxcount(String maxcount) {
		this.maxcount = maxcount;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getMinamount() {
		return minamount;
	}
	public void setMinamount(String minamount) {
		this.minamount = minamount;
	}
	public String getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(String maxamount) {
		this.maxamount = maxamount;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getLinkmode() {
		return linkmode;
	}
	public void setLinkmode(String linkmode) {
		this.linkmode = linkmode;
	}
	public String getLimitDesc() {
		return limitDesc;
	}
	public void setLimitDesc(String limitDesc) {
		this.limitDesc = limitDesc;
	}
	public String getTrrefno() {
		return trrefno;
	}
	public void setTrrefno(String trrefno) {
		this.trrefno = trrefno;
	}
	public String getFeefinaljson() {
		return feefinaljson;
	}
	public void setFeefinaljson(String feefinaljson) {
		this.feefinaljson = feefinaljson;
	}
	public String getLimitCode() {
		return limitCode;
	}
	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}
	public String getLimitDescription() {
		return limitDescription;
	}
	public void setLimitDescription(String limitDescription) {
		this.limitDescription = limitDescription;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getFeeDescription() {
		return feeDescription;
	}
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}
		
	public String getFinaljson() {
		return finaljson;
	}
	public void setFinaljson(String finaljson) {
		this.finaljson = finaljson;
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




	public String limitinfo() {

		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put("linkmode", linkmode);
			
			requestJSON.put("LimitCode", limitCode);
			requestJSON.put("LimitDescription", limitDescription);
			
			requestJSON.put("Feedescrption", feeDescription);
			requestJSON.put("FeeCode", feeCode);
			
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO.getRequestJSON() + "]");
			
			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.fetchLimitInfo(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
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
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			lmtDAO = null;
			errors = null;
		}

		return result;

	}


	public String lmtConfirmAct() {
		
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		requestJSON.put("productcode", productcode);
		requestJSON.put("productdesc", productdesc);
		requestJSON.put("productcurr", productcurr);
		requestJSON.put("application", application);

		
		responseJSON.put("productcode", productcode);
		responseJSON.put("productdesc", productdesc);
		responseJSON.put("productcurr", productcurr);
		responseJSON.put("application", application);


		logger.info("LimitCode ["+limitCode+"] LimitDescription ["+limitDescription+"] finaljson ["+finaljson+"]");
		
		logger.info("FeeCode ["+feeCode+"] Feedescrption ["+feeDescription+"] Feefinaljson ["+feefinaljson+"]");
		
		/*JSONObject lmtjson = JSONObject.fromObject(finaljson);
		JSONObject feejson = JSONObject.fromObject(feefinaljson);
		
		responseJSON.put("finaljson", lmtjson);
		responseJSON.put("feefinaljson", feejson);*/
		
		
		return "success";
		

	}
	
	
	public String lmtconfAck() {


		logger.info("lmtconfAck insaide >>>>>>>>>>>>>>>>> LimitCode ["+limitCode+"] LimitDescription ["+limitDescription+"] " +
				" finaljson ["+finaljson+"]");
		
		logger.info("FeeCode ["+feeCode+"] Feedescrption ["+feeDescription+"] Feefinaljson ["+feefinaljson+"]");
		
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;
		
		try {
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put("LimitCode", limitCode);
			requestJSON.put("LimitDescription", limitDescription);
			requestJSON.put("LimitJson", finaljson);
			
			requestJSON.put("productcode", productcode);
			requestJSON.put("productdesc", productdesc);
			requestJSON.put("productcurr", productcurr);
			requestJSON.put("application", application);

			
			
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.savelimitdata(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
				
				
				
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
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			lmtDAO = null;
			errors = null;
		}
		
		
		return "success";
		

	}
	
	
	
	public String feeconfAck() {


		logger.info("Feeconfirmm inside insaide >>>>>>>>>>>>>>>>> LimitCode ["+limitCode+"] LimitDescription ["+limitDescription+"] " +
				" finaljson ["+finaljson+"]");
		
		logger.info("FeeCode ["+feeCode+"] Feedescrption ["+feeDescription+"] Feefinaljson ["+feefinaljson+"]");
		
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;
		
		try {
			
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestJSON.put("Feedescrption", feeDescription);
			requestJSON.put("FeeCode", feeCode);
			requestJSON.put("FeeJson", feefinaljson);
			
			requestJSON.put("productcode", productcode);
			requestJSON.put("productdesc", productdesc);
			requestJSON.put("productcurr", productcurr);
			requestJSON.put("application", application);
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");

			lmtDAO = new LimitDao();
			responseDTO = lmtDAO.savefeedata(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				responseJSON = (JSONObject) responseDTO.getData().get("LIMIT_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
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
			logger.debug("Exception in binCommonScreen  [" + e.getMessage()
					+ "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			lmtDAO = null;
			errors = null;
		}
		return "success";
	}
	
	
	
	public String limitfeesettingsinfo(){
		 
		LimitDao lmtDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestDTO.setRequestJSON(requestJSON);
			lmtDAO = new LimitDao();
			
			responseDTO = lmtDAO.fetchLmtFeeGridInfo(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
				logger.debug("Response JSON  [" + responseJSON + "]");
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
			logger.debug("Exception in binCommonScreen  ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			lmtDAO = null;
			errors = null;
		}

		return result;
	}
	
 public String limitcodepopupDetails() {
		 
		 
		 ProductManDAO productDAO = null;
		 
			logger.debug("inside binCommonScreen.. ");
			
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
				requestDTO.setRequestJSON(requestJSON);				
				logger.debug("Request DTO [" + requestDTO + "]");
				productDAO = new ProductManDAO();				
				responseDTO = productDAO.limitcodeDetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					
					
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					//responseJSON = constructToResponseJson(httpRequest,responseJSON);
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
				logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
						+ "]");
				
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				
				errors = null;
			}

	
		return "success";

	 }
 
 public String branchpopupDetails() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.branchpopupDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

 }


 public String limitDetailsModify() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
			requestJSON.put("limitDesc", limitDescription);
			requestJSON.put("trrefno", trrefno);
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.limitDetailsModify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				
				responseJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
				responseJSON.put("limitDesc", limitDescription);
				responseJSON.put("trrefno", trrefno);
				responseJSON.put("productcode", productcode);
				responseJSON.put("application", application);
				
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

 }
 
 
public String limitDetailsModifyAck() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
			requestJSON.put("limitDesc", limitDesc);
			requestJSON.put("transaction", transaction);
			requestJSON.put("frequency", frequency);
			requestJSON.put("maxcount", maxcount);
			requestJSON.put("minamount", minamount);
			requestJSON.put("maxamount", maxamount);
			requestJSON.put("trrefno", seqno);
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);
			requestJSON.put("channel", channel);
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO.requestJSON + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.limitDetailsModifyAck(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				
				responseJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
				responseJSON.put("limitDesc", limitDesc);
				responseJSON.put("productcode", productcode);
				responseJSON.put("application", application);
				/*responseJSON.put("transaction", transaction);
				responseJSON.put("frequency", frequency);
				responseJSON.put("maxcount", maxcount);
				responseJSON.put("minamount", minamount);
				responseJSON.put("maxamount", maxamount);
				responseJSON.put("trrefno", seqno);*/
				
				
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

}
	 
 public String feecodepopupDetails() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.feecodeDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

 }
 
 
 public String feeDetailsModify() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
			requestJSON.put("feeDesc", feeDescription);
			requestJSON.put("trrefno", trrefno);
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.feeDetailsModify(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				responseJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
				responseJSON.put("feeDesc", feeDescription);
				responseJSON.put("productcode", productcode);
				responseJSON.put("application", application);
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

 }
 
 
public String feeDetailsModifyAck() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("productcode", productcode);
			requestJSON.put("application", application);
			requestJSON.put("feeCode", feeCode);
			requestJSON.put("feeDesc", feeDescription);
			requestJSON.put("transaction", transaction);
			requestJSON.put("frequency", frequency);
			requestJSON.put("flatpercentile", flatpercentile);
			requestJSON.put("fpValue", fpValue);
			requestJSON.put("criteria", criteria);
			requestJSON.put("fromvalue", fromvalue);
			requestJSON.put("tovalue", tovalue);
			requestJSON.put("trrefno", seqno);
			
			requestJSON.put("subagent", subagent);
			requestJSON.put("agent", agent);
			requestJSON.put("ceva", ceva);
			requestJSON.put("bank", bank);
			requestJSON.put("channel", channel);
			requestJSON.put("SuperAgent", SuperAgent);
			requestJSON.put("VAT", VAT);
			requestJSON.put("thirdparty", thirdparty);
			requestJSON.put("qt", qt);
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO.requestJSON + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.feeDetailsModifyAck(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				responseJSON.put("productcode", productcode);
				responseJSON.put("application", application);
				responseJSON.put("feeCode", feeCode);
				responseJSON.put("feeDesc", feeDescription);
			
				
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

}

public String feeDetailsActiveAck() {
	 
	 
	 ProductManDAO productDAO = null;
	 
		logger.debug("inside binCommonScreen.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("productcode", productcode);
			requestJSON.put("feeCode", feeCode);
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO.requestJSON + "]");
			productDAO = new ProductManDAO();				
			responseDTO = productDAO.feeDetailsActiveAck(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				responseJSON.put("productcode", productcode);
				responseJSON.put("feeCode", feeCode);
			
				
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
			logger.debug("Exception in feecodeCommonScreen  [" + e.getMessage()
					+ "]");
			
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			
			errors = null;
		}


	return "success";

}

public String parametersettingsinfo(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		responseDTO = lmtDAO.parametersettingsinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}


public String clustersettingsinfo(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		responseDTO = lmtDAO.clustersettingsinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}

public String ParameterCreationauthviews(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		requestJSON.put("limitCode", limitCode);
		requestJSON.put("limitDescription", limitDescription);
		
		responseDTO = lmtDAO.ParameterCreationauthviews(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}


public String fraudinfo(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		responseDTO = lmtDAO.fraudinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}

public String fraudinfodefault(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		responseDTO = lmtDAO.fraudinfodefault(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}

public String fraudinfodefaultAction(){
	 
	LimitDao lmtDAO = null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		lmtDAO = new LimitDao();
		
		responseDTO = lmtDAO.fraudinfodefaultAction(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		lmtDAO = null;
		errors = null;
	}

	return result;
}
 
}
