package com.ceva.base.product.action;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.product.dao.ProductManDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;

    public class ProductManAction extends ActionSupport  implements ServletRequestAware  {
    	
	private static final long serialVersionUID = 1L;


	//private static final HttpServletRequest httpRequest = null;
	

	Logger logger = Logger.getLogger(ProductManAction.class);
	
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;
    RequestDTO requestDTO = null;
	ResponseDTO responseDTO = null;
	JSONObject productJSON = null;
	JSONObject userJSON = null;
	
	private String  result;
	private String  bin;
	private String  binDesc;
	private String  binType;
	private String  binTypeData;
	private String  binTypeval;
	private String  plasticCodeText;
	private String  bingroupcode;
	private String  binGroupDescription;
	private String  plasticCode;
	private String  possiableCards;
	private String  productCode;
	private String  productDesc;
	private String  programCode;
	private String  binCurrency;
	private String  binGroup;
	private String  expPeriod;
	private String  issuanceTemlateId;
	private String  serviceCode;
	private String  issuingCountry;
	private String  plasticData;
	private String  issuingData;
	private String  binGroupData;
	private String  pCode;
	private String  productText;
	private String  binCodeVal;
	private String  binGroupCodeVal;
	private String  limitCode;
	private String  feeCode;
	private String tokenlimit;
	private String chdata;
	
	private String  newVal;
	private String  oldVal;
	private String  columnVal;
	
	private String  AUTH_CODE;
	private String  STATUS;
	private String actiontype;
	private String status;
	private String loyaltyCode;
	private String perdaylimit;
	private String branchdetails;
	
	private String ussdinilmt;
	private String ussdsecfalmt;
	private String ussdinitallmt;
	
	

	private String bvn;
	private String referenceno;
	private String custstatus;
	
	private String product;
	
	private String feename;
	

	public String getFeename() {
		return feename;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUssdinitallmt() {
		return ussdinitallmt;
	}

	public void setUssdinitallmt(String ussdinitallmt) {
		this.ussdinitallmt = ussdinitallmt;
	}
	
	
	public String getBvn() {
		return bvn;
	}

	public void setBvn(String bvn) {
		this.bvn = bvn;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getCuststatus() {
		return custstatus;
	}

	public void setCuststatus(String custstatus) {
		this.custstatus = custstatus;
	}
	
	
	public String getUssdinilmt() {
		return ussdinilmt;
	}

	public void setUssdinilmt(String ussdinilmt) {
		this.ussdinilmt = ussdinilmt;
	}

	public String getUssdsecfalmt() {
		return ussdsecfalmt;
	}

	public void setUssdsecfalmt(String ussdsecfalmt) {
		this.ussdsecfalmt = ussdsecfalmt;
	}

	public String getBranchdetails() {
		return branchdetails;
	}

	public void setBranchdetails(String branchdetails) {
		this.branchdetails = branchdetails;
	}

	private String finaljsonarray;
	
	
	
	
	public String getChdata() {
		return chdata;
	}

	public void setChdata(String chdata) {
		this.chdata = chdata;
	}

	public String getFinaljsonarray() {
		return finaljsonarray;
	}

	public void setFinaljsonarray(String finaljsonarray) {
		this.finaljsonarray = finaljsonarray;
	}

	public String getPerdaylimit() {
		return perdaylimit;
	}

	public void setPerdaylimit(String perdaylimit) {
		this.perdaylimit = perdaylimit;
	}

	public String getTokenlimit() {
		return tokenlimit;
	}

	public void setTokenlimit(String tokenlimit) {
		this.tokenlimit = tokenlimit;
	}

	public String getLoyaltyCode() {
		return loyaltyCode;
	}

	public void setLoyaltyCode(String loyaltyCode) {
		this.loyaltyCode = loyaltyCode;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String  PageDirection;
	
	private String  decision;
	private String  Narration;
	private String  refno;
	private String  application;
	private String  agent;
	
	
	
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getNarration() {
		return Narration;
	}

	public void setNarration(String narration) {
		Narration = narration;
	}

	public String getPageDirection() {
		return PageDirection;
	}

	public void setPageDirection(String pageDirection) {
		PageDirection = pageDirection;
	}

	public String getAUTH_CODE() {
		return AUTH_CODE;
	}

	public void setAUTH_CODE(String aUTH_CODE) {
		AUTH_CODE = aUTH_CODE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	private HttpSession session = null;
	
	private HttpServletRequest httpRequest = null;
	
	public String productDashBoard(){
		
		result="success";
		return result;
		
	}
	
	// Saving all input request params to JSONObject

		@SuppressWarnings("rawtypes")
		private JSONObject constructToResponseJson(HttpServletRequest httpRequest, JSONObject jsonObject) {

			Enumeration enumParams = null;
			logger.debug("Inside ConstructToResponseJson...");
			try {
				if (jsonObject == null)
					jsonObject = new JSONObject();
				System.out.println("444");
				enumParams = httpRequest.getParameterNames();
				while (enumParams.hasMoreElements()) {
					String key = (String) enumParams.nextElement();
					String val = httpRequest.getParameter(key);
					jsonObject.put(key, val);
				}

			} catch (Exception e) {
				logger.debug(" Exception in ConstructToResponseJson ["
						+ e.getMessage() + "]");
				e.printStackTrace();
			} finally {
				enumParams = null;
			}
			logger.debug(" jsonObject[" + jsonObject + "]");

			return jsonObject;
		}  
	
	public String createNewProduct()
	{
		logger.debug("inside createNewProduct.. ");
		ProductManDAO productDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();
			responseDTO = productDAO.fetchProductCreatePageInfo(requestDTO);
			 
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BIN_INFO);
				
				logger.debug("Response JSON [" + responseJSON + "]");
				
				responseJSON = constructToResponseJson(httpRequest, responseJSON);
				
				logger.debug("Response JSON Inside Construct To ResponseJson  [" + responseJSON + "]");
				
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
			logger.debug("Exception in Create NewProduct ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			productDAO = null;
			errors = null;
		}
		return result;
	}
	
	 
	
	
	
	public String prdConfirmScreen()
	{
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
	
		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");

		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);



		logger.info("Response Json ["+ responseJSON+"]");
		
		if(plasticCode!=null)
			plasticCode=plasticCode.replace(" ","");
		
		 result="success";
		 return result;
	}
	 
	public String insertProduct(){
		
		logger.debug("Inside insertProduct... ");
		ProductManDAO productDAO = null;
		ArrayList<String> messages = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			/*if (bin == null) {
				addActionError("Records Missing.");
				result = "fail";
			} else {*/

				session = ServletActionContext.getRequest().getSession();

				requestJSON.put( "productText", productText);
				requestJSON.put( "productDesc", productDesc);
			
				requestJSON.put( "programCode", programCode);
				requestJSON.put( "binCurrency", binCurrency);
				requestJSON.put( "binGroup", binGroup);
				requestJSON.put( "expPeriod", expPeriod);
				requestJSON.put( "plasticCode", plasticCode);
				requestJSON.put( "issuanceTemlateId", issuanceTemlateId);
				requestJSON.put( "serviceCode", serviceCode);
				requestJSON.put( "issuingCountry", issuingCountry);
				requestJSON.put( "limitCode", limitCode);
				requestJSON.put( "feeCode", feeCode);
				requestJSON.put( "application", application);
				requestJSON.put( "tokenlimit", tokenlimit);
				requestJSON.put( "perdaylimit", perdaylimit);
				requestJSON.put( "ussdinilmt", ussdinilmt);
				requestJSON.put( "ussdsecfalmt", ussdsecfalmt);
				requestJSON.put( "ussdinitallmt", ussdinitallmt);
				requestJSON.put( "agent", agent);
				requestJSON.put( "feename", feename);
				
				JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


				requestJSON.put("FINAL_JSON", jsonarray);
				
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestDTO.setRequestJSON(requestJSON);
				logger.debug(" RequestJSON  [" + requestJSON + "]");
				productDAO = new ProductManDAO();
				responseDTO = productDAO.inserProductDetails(requestDTO);
				
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ENTITY_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					 
					 messages = (ArrayList<String>) responseDTO.getMessages();
					logger.debug("Messages [" + messages + "]");
					for (int i = 0; i < messages.size(); i++) {
						addActionMessage(messages.get(i));
					} 
					result = "success";
				} else {
					logger.debug("Getting error from DB.");
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.ENTITY_LIST);
					logger.debug("Response JSON [" + responseJSON + "]");
					 errors = (ArrayList<String>) responseDTO.getErrors();
					logger.debug("Errors [" + errors + "]");
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					} 
					result = "fail";
				}
			//}
				
			logger.debug("Result [" + result + "]");
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in insertMerchantDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;

			errors = null;
			messages = null;
			productDAO = null;
		}
		return result;
	}
	
	
	public String authProductSearch() throws Exception
	{
		logger.debug("Inside authProductSearch..");
		ProductManDAO productDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			AUTH_CODE=actiontype;
			STATUS=status;
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put("actiontype", AUTH_CODE);
			requestJSON.put("status", STATUS);
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();
			responseDTO= productDAO.authProductSearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
				if(STATUS.equalsIgnoreCase("PENDING") || STATUS.equalsIgnoreCase("N")){
					
					if(actiontype.equalsIgnoreCase("PRDNEWAUTH")){
						PageDirection = "authnewproductsearch";
					}else if(actiontype.equalsIgnoreCase("PRDMODAUTH")){
						PageDirection = "authnewproductsearch";
					}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
						PageDirection = "authnewproductsearch";
					}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
						PageDirection = "authnewlimitsearch";
					}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
						PageDirection = "authnewlimitsearch";
					}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
						PageDirection = "authnewfeesearch";
					}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
						PageDirection = "authnewfeesearch";
					}else if(actiontype.equalsIgnoreCase("FEEACTAUTH")){
						PageDirection = "authnewfeesearch";
					}else if(actiontype.equalsIgnoreCase("LPOSTAUTH")){
						PageDirection = "authnewloyaltysearch";
					}else if(actiontype.equalsIgnoreCase("LPOSTMODAUTH")){
						PageDirection = "authnewloyaltysearch";
					}else if(actiontype.equalsIgnoreCase("LYLNEWAUTH")){
						PageDirection = "authnewloyaltyassignsearch";
					}else if(actiontype.equalsIgnoreCase("LYLMODAUTH")){
						PageDirection = "authnewloyaltyassignsearch";
					}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
						PageDirection = "accountopensearch";
					}
				
					
				}else{
					if(STATUS.equalsIgnoreCase("AUTHORIZED") || STATUS.equalsIgnoreCase("C")){
						if(AUTH_CODE.equalsIgnoreCase("PRDNEWAUTH")){
							responseJSON.put("status", "Authorized Redords");
						}else if(AUTH_CODE.equalsIgnoreCase("PRDMODAUTH")){
							responseJSON.put("status", "Modify Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
							responseJSON.put("status", "Active/Deactive Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
							responseJSON.put("status", "Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
							responseJSON.put("status", "Modify Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
							responseJSON.put("status", "Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
							responseJSON.put("status", "Modify Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
							responseJSON.put("status", "Account Open Authorized Redords");
						}
						
					}else if(STATUS.equalsIgnoreCase("REJECTED") || STATUS.equalsIgnoreCase("R")){
						if(AUTH_CODE.equalsIgnoreCase("PRDNEWAUTH")){
							responseJSON.put("status", "Rejected Redords");
						}else if(AUTH_CODE.equalsIgnoreCase("PRDMODAUTH")){
							responseJSON.put("status", "Modify Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
							responseJSON.put("status", "Active/Deactive Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
							responseJSON.put("status", "Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
							responseJSON.put("status", "Modify Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
							responseJSON.put("status", "Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
							responseJSON.put("status", "Modify Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
							responseJSON.put("status", "Account Open Rejected Redords");
						}
						
					}else if(STATUS.equalsIgnoreCase("DELETED") || STATUS.equalsIgnoreCase("D")){
						if(AUTH_CODE.equalsIgnoreCase("PRDNEWAUTH")){
							responseJSON.put("status", "Deleted Redords");
						}else if(AUTH_CODE.equalsIgnoreCase("PRDMODAUTH")){
							responseJSON.put("status", "Modify Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
							responseJSON.put("status", "Active/Deactive Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
							responseJSON.put("status", "Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
							responseJSON.put("status", "Modify Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
							responseJSON.put("status", "Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
							responseJSON.put("status", "Modify Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
							responseJSON.put("status", "Account Open Deleted Redords");
						}
						
					}
					PageDirection = "authistorysearch";
				}
				
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
			logger.debug("Exception in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(productDAO!=null)
				productDAO = null;
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			 if(errors!=null)
				 errors = null;
		}

		return result;
	}
	
	
	
	public String girdProduct(){
		 
		    ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.fetchProductGridInfo(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BIN_INFO);
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
				productDAO = null;
				errors = null;
			}

			return result;
		}
		
	 
	
	 public String fetchViewprd(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.PRODUCT_CODE,productCode);
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.fetchProductViewInfo(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BIN_INFO);
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
				productDAO = null;
				errors = null;
			}

			return result;
		}

	 
	 public String authfetchViewprd(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.PRODUCT_CODE,productCode);
				requestJSON.put("actiontype",actiontype);
				requestJSON.put("refno",refno);
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.authfetchViewprd(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BIN_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					responseJSON.put("actiontype",actiontype);
					responseJSON.put("refno",refno);
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
				productDAO = null;
				errors = null;
			}

			return result;
		}
	 
	 
	 public String authfetchViewlmt(){
		 
		 
		 ProductManDAO productDAO = null;
		 
			logger.debug("inside binCommonScreen.. ");
			
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.LIMIT_CODE, limitCode);
				requestJSON.put("refno", refno);
				requestJSON.put("actiontype", actiontype);
				requestDTO.setRequestJSON(requestJSON);				
				logger.debug("Request DTO [" + requestDTO + "]");
				productDAO = new ProductManDAO();				
				responseDTO = productDAO.authlimitcodeDetails(requestDTO);
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
	 
	 
	 public String authfetchViewLoyalty(){
		 
		 
		 ProductManDAO productDAO = null;
		 
			logger.debug("inside binCommonScreen.. ");
			
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put("loyaltyCode", loyaltyCode);
				requestJSON.put("actiontype", actiontype);
				
				requestDTO.setRequestJSON(requestJSON);				
				logger.debug("Request DTO [" + requestDTO + "]");
				productDAO = new ProductManDAO();				
				responseDTO = productDAO.authfetchViewLoyalty(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					
					
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					//responseJSON = constructToResponseJson(httpRequest,responseJSON);
					
					/*if(actiontype.equalsIgnoreCase("NEW_LOYALTY_CREATION")){
						PageDirection = "LoyalitydataAuth";
					}else if(actiontype.equalsIgnoreCase("MODIFY_LOYALTY_CREATION")){
						PageDirection = "LoyalitydataAuthModify";
					}*/
					
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
	 
	 
	 public String authfetchViewfe(){
		 
		 
		 ProductManDAO productDAO = null;
		 
			logger.debug("inside binCommonScreen.. ");
			
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.FEE_CODE, feeCode);
				requestJSON.put("refno", refno);
				requestJSON.put("actiontype", actiontype);
				requestDTO.setRequestJSON(requestJSON);				
				logger.debug("Request DTO [" + requestDTO + "]");
				productDAO = new ProductManDAO();				
				responseDTO = productDAO.authfeecodeDetails(requestDTO);
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
	 
	 
		public String authFetchAuthLoyalty() {
			logger.debug("[ProductManAction][authFetchAuthLoyalty] Inside authFetchAuthLoyalty... ");
			 ProductManDAO productDAO=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
						requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));

					requestJSON.put("loyaltyCode", loyaltyCode);
					requestJSON.put("actiontype", actiontype);
					
					
					
					
					requestDTO.setRequestJSON(requestJSON);
				    logger.debug("[AgentAction][authFetchAuthLoyalty]  Request DTO [" + requestDTO + "]");
				  
					productDAO = new ProductManDAO();
					responseDTO = productDAO.authFetchAuthLoyalty(requestDTO);
				  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
					logger.debug("[AgentAction][authFetchAuthLoyalty]  Response JSON++++++++ [" + responseJSON + "]");
					
					
					if(actiontype.equalsIgnoreCase("NEW_LOYALTY_SETTING")){
						PageDirection = "LoyalitysettingsAuth";
					}else if(actiontype.equalsIgnoreCase("MODIFY_LOYALTY_SETTING")){
						PageDirection = "LoyalitysettingsAuthModify";
					}
					
					
					
					
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
				logger.debug("[ProductManAction][authFetchAuthLoyalty] Exception in View details ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				if(productDAO!=null)
					productDAO = null;
			 		if(requestDTO!=null)
					 requestDTO = null;
			 		if(responseDTO!=null)
					 responseDTO = null;
			 		if(requestJSON!=null)
					 requestJSON = null;
			 		if(messages!=null)
					 messages = null;
			 		if(errors!=null)
					 errors = null;
			}

			return result;
		}
		
		 public String authAccountOpenDetails(){
			 
			 
			 ProductManDAO productDAO = null;
			 
				logger.debug("inside binCommonScreen.. ");
				
				ArrayList<String> errors = null;
				try {
					requestJSON = new JSONObject();
					requestDTO = new RequestDTO();
					requestJSON.put("loyaltyCode", loyaltyCode);
					requestJSON.put("actiontype", actiontype);
					
					requestDTO.setRequestJSON(requestJSON);				
					logger.debug("Request DTO [" + requestDTO + "]");
					productDAO = new ProductManDAO();				
					responseDTO = productDAO.authAccountOpenDetails(requestDTO);
					logger.debug("Response DTO [" + responseDTO + "]");
					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						
						
						responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
						logger.debug("Response JSON  [" + responseJSON + "]");
						//responseJSON = constructToResponseJson(httpRequest,responseJSON);
						PageDirection = "AccountDetailsAuth";
						/*if(actiontype.equalsIgnoreCase("NEW_LOYALTY_CREATION")){
							PageDirection = "LoyalitydataAuth";
						}else if(actiontype.equalsIgnoreCase("MODIFY_LOYALTY_CREATION")){
							PageDirection = "LoyalitydataAuthModify";
						}*/
						
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
	 
	 
	 public String authProductAck(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.PRODUCT_CODE,productCode);
				requestJSON.put("decision",decision);
				requestJSON.put("Narration",Narration);
				requestJSON.put("refno",refno);
				requestJSON.put("actiontype",actiontype);
				
				
				
				/*System.out.println("decision :: "+decision);
				System.out.println("Narration :: "+Narration);*/
				
				session = ServletActionContext.getRequest().getSession();
				
				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.authProductAck(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BIN_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					/*responseJSON.put("decision",decision);
					requestJSON.put("actiontype",actiontype);*/
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
				productDAO = null;
				errors = null;
			}

			return result;
		}
	 
	 public String authLimitAck(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put("product",product);
				requestJSON.put("limitCode",limitCode);
				requestJSON.put("decision",decision);
				requestJSON.put("Narration",Narration);
				requestJSON.put("refno",refno);
				
				/*System.out.println("decision :: "+decision);
				System.out.println("Narration :: "+Narration);*/
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.authLimitAck(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					responseJSON.put("decision",decision);
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
				productDAO = null;
				errors = null;
			}

			return result;
		}
	 
	 public String authFeeAck(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				
				requestJSON.put("product",product);
				requestJSON.put("feeCode",feeCode);
				requestJSON.put("decision",decision);
				requestJSON.put("Narration",Narration);
				requestJSON.put("refno",refno);
				
				/*System.out.println("decision :: "+decision);
				System.out.println("Narration :: "+Narration);*/
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.authFeeAck(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					responseJSON.put("decision",decision);
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
				productDAO = null;
				errors = null;
			}

			return result;
		}
	 
	 
 
	 
	 public String fetchModifyprd()
	 {
		 ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put(CevaCommonConstants.PRODUCT_CODE,productCode);
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestJSON + "]");
				productDAO = new ProductManDAO();
				responseDTO = productDAO.fetchProductModifyPageInfo(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("BIN_DETAILS");
					responseJSON = constructToResponseJson(httpRequest, responseJSON);
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
				logger.debug("Exception in createNewProduct ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				productDAO = null;
				errors = null;
			}

			return result;
		}
	
	 public String productModifyData(){
		 
		 result="success";
		 
		 logger.debug("*-*-*-"+newVal+"*-*-*"+oldVal+"*-*-*"+columnVal);
		 
		 return result;
	 }
	 
	 
	 public String insertProductModifyData()
	 {
		 logger.debug("Inside insertProductModifyData... ");
			ProductManDAO productDAO = null;
			ArrayList<String> messages = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				if (productCode == null) {
					addActionError("Records Missing");
					result = "fail";
				} else {

					session = ServletActionContext.getRequest().getSession();

					requestJSON.put( "productCode", productCode);
					requestJSON.put( "productDesc", productDesc);
				
					requestJSON.put( "programCode", application);
					requestJSON.put( "binCurrency", binCurrency);
					
					
					requestJSON.put( "tokenlimit", tokenlimit);
					requestJSON.put( "perdaylimit", perdaylimit);
					requestJSON.put( "chdata", chdata);
					 
					requestJSON.put( "binGroupCodeVal", binGroupCodeVal);
					requestJSON.put( "expPeriod", expPeriod);
					requestJSON.put( "plasticCode", plasticCode);
					requestJSON.put( "issuanceTemlateId", issuanceTemlateId);
					requestJSON.put( "serviceCode", serviceCode);
					requestJSON.put( "issuingCountry", issuingCountry);
					requestJSON.put( "limitCode", limitCode);
					requestJSON.put( "feeCode", feeCode);
					requestJSON.put( "feename", feename);
					//requestJSON.put( "ussdinilmt", ussdinilmt);
					requestJSON.put( "ussdsecfalmt", ussdsecfalmt);
					requestJSON.put( "ussdinitallmt", ussdinitallmt);
					 	
					requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestDTO.setRequestJSON(requestJSON);
					logger.debug(" RequestJSON [" + requestJSON + "]");
					productDAO = new ProductManDAO();
					responseDTO = productDAO.inserProductModifyDetails(requestDTO);
					 

					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						logger.debug("Getting messages from DB.");
						messages = (ArrayList<String>) responseDTO.getMessages();
						logger.debug("Messages [" + messages + "]");
						for (int i = 0; i < messages.size(); i++) {
							addActionMessage(messages.get(i));
						}
						result = "success";
					} else {
						logger.debug("Getting error from DB.");
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						errors = (ArrayList<String>) responseDTO.getErrors();
						logger.debug("Errors [" + errors + "]");
						for (int i = 0; i < errors.size(); i++) {
							addActionError(errors.get(i));
						}
						result = "fail";
					}
				}
				logger.debug("Result [" + result + "]");
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in insertMerchantDetails ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;

				errors = null;
				messages = null;
				productDAO = null;
			}
			return result;
		}
	 public String insertProductActiveData()
	 {
		 logger.debug("Inside insertProductModifyData... ");
			ProductManDAO productDAO = null;
			ArrayList<String> messages = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				if (productCode == null) {
					addActionError("Records Missing");
					result = "fail";
				} else {

					session = ServletActionContext.getRequest().getSession();

					requestJSON.put( "productCode", productCode);
					requestJSON.put( "productDesc", productDesc);
				
					requestJSON.put( "programCode", application);
					requestJSON.put( "binCurrency", binCurrency);
					
					requestJSON.put( "tokenlimit", tokenlimit);
					requestJSON.put( "perdaylimit", perdaylimit);
					requestJSON.put( "chdata", chdata);
					 
					requestJSON.put( "binGroupCodeVal", binGroupCodeVal);
					requestJSON.put( "expPeriod", expPeriod);
					requestJSON.put( "plasticCode", plasticCode);
					requestJSON.put( "issuanceTemlateId", issuanceTemlateId);
					requestJSON.put( "serviceCode", serviceCode);
					requestJSON.put( "issuingCountry", issuingCountry);
					requestJSON.put( "limitCode", limitCode);
					requestJSON.put( "feeCode", feeCode);
					 	
					requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestDTO.setRequestJSON(requestJSON);
					logger.debug(" RequestJSON [" + requestJSON + "]");
					productDAO = new ProductManDAO();
					responseDTO = productDAO.insertProductActiveData(requestDTO);
					 

					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						logger.debug("Getting messages from DB.");
						messages = (ArrayList<String>) responseDTO.getMessages();
						logger.debug("Messages [" + messages + "]");
						for (int i = 0; i < messages.size(); i++) {
							addActionMessage(messages.get(i));
						}
						result = "success";
					} else {
						logger.debug("Getting error from DB.");
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						errors = (ArrayList<String>) responseDTO.getErrors();
						logger.debug("Errors [" + errors + "]");
						for (int i = 0; i < errors.size(); i++) {
							addActionError(errors.get(i));
						}
						result = "fail";
					}
				}
				logger.debug("Result [" + result + "]");
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in insertMerchantDetails ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;

				errors = null;
				messages = null;
				productDAO = null;
			}
			return result;
		}

	 public String insertProductPermission()
	 {
		 logger.debug("Inside insertProductModifyData... ");
			ProductManDAO productDAO = null;
			ArrayList<String> messages = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				if (productCode == null) {
					addActionError("Records Missing");
					result = "fail";
				} else {

					session = ServletActionContext.getRequest().getSession();

					requestJSON.put( "productCode", productCode);
					requestJSON.put( "branchdetails", branchdetails);
					
					 	
					requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestDTO.setRequestJSON(requestJSON);
					logger.debug(" RequestJSON [" + requestJSON + "]");
					productDAO = new ProductManDAO();
					responseDTO = productDAO.insertProductPermission(requestDTO);
					 

					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						logger.debug("Getting messages from DB.");
						messages = (ArrayList<String>) responseDTO.getMessages();
						logger.debug("Messages [" + messages + "]");
						for (int i = 0; i < messages.size(); i++) {
							addActionMessage(messages.get(i));
						}
						result = "success";
					} else {
						logger.debug("Getting error from DB.");
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						errors = (ArrayList<String>) responseDTO.getErrors();
						logger.debug("Errors [" + errors + "]");
						for (int i = 0; i < errors.size(); i++) {
							addActionError(errors.get(i));
						}
						result = "fail";
					}
				}
				logger.debug("Result [" + result + "]");
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in insertMerchantDetails ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;

				errors = null;
				messages = null;
				productDAO = null;
			}
			return result;
		}
	 
	 public String insertProductServices()
	 {
		 logger.debug("Inside insertProductModifyData... ");
			ProductManDAO productDAO = null;
			ArrayList<String> messages = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				if (productCode == null) {
					addActionError("Records Missing");
					result = "fail";
				} else {

					session = ServletActionContext.getRequest().getSession();

					requestJSON.put( "productCode", productCode);
					requestJSON.put( "branchdetails", branchdetails);
					requestJSON.put( "application", application);
					
					
					 	
					requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestDTO.setRequestJSON(requestJSON);
					logger.debug(" RequestJSON [" + requestJSON + "]");
					productDAO = new ProductManDAO();
					responseDTO = productDAO.insertProductServices(requestDTO);
					 

					if (responseDTO != null && responseDTO.getErrors().size() == 0) {
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						logger.debug("Getting messages from DB.");
						messages = (ArrayList<String>) responseDTO.getMessages();
						logger.debug("Messages [" + messages + "]");
						for (int i = 0; i < messages.size(); i++) {
							addActionMessage(messages.get(i));
						}
						result = "success";
					} else {
						logger.debug("Getting error from DB.");
						responseJSON = (JSONObject) responseDTO.getData().get(
								CevaCommonConstants.ENTITY_LIST);
						logger.debug("Response JSON [" + responseJSON + "]");
						errors = (ArrayList<String>) responseDTO.getErrors();
						logger.debug("Errors [" + errors + "]");
						for (int i = 0; i < errors.size(); i++) {
							addActionError(errors.get(i));
						}
						result = "fail";
					}
				}
				logger.debug("Result [" + result + "]");
			} catch (Exception e) {
				result = "fail";
				logger.debug("Exception in insertMerchantDetails ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;

				errors = null;
				messages = null;
				productDAO = null;
			}
			return result;
		}
	 
public String fetchFeeData(){
	 
      logger.debug("inside Fetch FeeData.. ");
      
		ProductManDAO productDAO = null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
					 
			requestJSON.put("limitCode", limitCode);
			 
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			productDAO = new ProductManDAO();
			responseDTO = productDAO.fetchLimitInfo(requestDTO);
			 
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BIN_INFO);
				
				logger.debug("Response JSON [" + responseJSON + "]");
				
				responseJSON = constructToResponseJson(httpRequest, responseJSON);
				
				logger.debug("Response JSON Inside Construct To ResponseJson  [" + responseJSON + "]");
				
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
			logger.debug("Exception in Create NewProduct ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			productDAO = null;
			errors = null;
		}
		return result;
	}
			
	 
//fetching feecode data


	 public String popupDetails() {
		 
	 
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
	 
	//fetching limitcode details 

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
	 
	 public String authAccountOpenAck(){
		 
		   ProductManDAO productDAO = null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestJSON.put("bvn",bvn);
				requestJSON.put("decision",decision);
				requestJSON.put("referenceno",referenceno);
				requestJSON.put("custstatus",custstatus);
				requestJSON.put("refno",refno);
				
				/*System.out.println("decision :: "+decision);
				System.out.println("Narration :: "+Narration);*/
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				logger.debug("Request DTO  [" + requestJSON + "]");
				requestDTO.setRequestJSON(requestJSON);
				productDAO = new ProductManDAO();
				responseDTO = productDAO.authAccountOpenAck(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
					logger.debug("Response JSON  [" + responseJSON + "]");
					responseJSON.put("decision",decision);
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
				productDAO = null;
				errors = null;
			}

			return result;
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

	 

	public JSONObject getProductJSON() {
		return productJSON;
	}

	public void setProductJSON(JSONObject productJSON) {
		this.productJSON = productJSON;
	}

	public JSONObject getUserJSON() {
		return userJSON;
	}

	public void setUserJSON(JSONObject userJSON) {
		this.userJSON = userJSON;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBinDesc() {
		return binDesc;
	}

	public void setBinDesc(String binDesc) {
		this.binDesc = binDesc;
	}

	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	public String getBinTypeData() {
		return binTypeData;
	}

	public void setBinTypeData(String binTypeData) {
		this.binTypeData = binTypeData;
	}

	public String getBinTypeval() {
		return binTypeval;
	}

	public void setBinTypeval(String binTypeval) {
		this.binTypeval = binTypeval;
	}
 
	public String getPlasticCodeText() {
		return plasticCodeText;
	}

	public void setPlasticCodeText(String plasticCodeText) {
		this.plasticCodeText = plasticCodeText;
	}

	public String getBingroupcode() {
		return bingroupcode;
	}

	public void setBingroupcode(String bingroupcode) {
		this.bingroupcode = bingroupcode;
	}

	public String getBinGroupDescription() {
		return binGroupDescription;
	}

	public void setBinGroupDescription(String binGroupDescription) {
		this.binGroupDescription = binGroupDescription;
	}

	public String getPlasticCode() {
		return plasticCode;
	}

	public void setPlasticCode(String plasticCode) {
		this.plasticCode = plasticCode;
	}

	public String getPossiableCards() {
		return possiableCards;
	}

	public void setPossiableCards(String possiableCards) {
		this.possiableCards = possiableCards;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getBinCurrency() {
		return binCurrency;
	}

	public void setBinCurrency(String binCurrency) {
		this.binCurrency = binCurrency;
	}

	public String getBinGroup() {
		return binGroup;
	}

	public void setBinGroup(String binGroup) {
		this.binGroup = binGroup;
	}

	public String getExpPeriod() {
		return expPeriod;
	}

	public void setExpPeriod(String expPeriod) {
		this.expPeriod = expPeriod;
	}

	public String getIssuanceTemlateId() {
		return issuanceTemlateId;
	}

	public void setIssuanceTemlateId(String issuanceTemlateId) {
		this.issuanceTemlateId = issuanceTemlateId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getIssuingCountry() {
		return issuingCountry;
	}

	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}

	public String getPlasticData() {
		return plasticData;
	}

	public void setPlasticData(String plasticData) {
		this.plasticData = plasticData;
	}

	public String getIssuingData() {
		return issuingData;
	}

	public void setIssuingData(String issuingData) {
		this.issuingData = issuingData;
	}

	public String getBinGroupData() {
		return binGroupData;
	}

	public void setBinGroupData(String binGroupData) {
		this.binGroupData = binGroupData;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}


	public String getProductText() {
		return productText;
	}

	public void setProductText(String productText) {
		this.productText = productText;
	}
	
	public String getBinCodeVal() {
		return binCodeVal;
	}

	public void setBinCodeVal(String binCodeVal) {
		this.binCodeVal = binCodeVal;
	}

	public String getBinGroupCodeVal() {
		return binGroupCodeVal;
	}

	public void setBinGroupCodeVal(String binGroupCodeVal) {
		this.binGroupCodeVal = binGroupCodeVal;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public String getLimitCode() {
		return limitCode;
	}

	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getNewVal() {
		return newVal;
	}

	public void setNewVal(String newVal) {
		this.newVal = newVal;
	}

	public String getOldVal() {
		return oldVal;
	}

	public void setOldVal(String oldVal) {
		this.oldVal = oldVal;
	}

	public String getColumnVal() {
		return columnVal;
	}

	public void setColumnVal(String columnVal) {
		this.columnVal = columnVal;
	}
 
	
}
