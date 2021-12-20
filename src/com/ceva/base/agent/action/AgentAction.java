package com.ceva.base.agent.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;







import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.dao.AgentDAO;
import com.ceva.base.common.dao.impl.LocalGovernmentDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.unionbank.channel.ExcelParsing;
import com.ceva.unionbank.channel.RaasValidator;
import com.opensymphony.xwork2.ActionSupport;

public class AgentAction extends ActionSupport   implements ServletRequestAware{
	
	Logger logger = Logger.getLogger(AgentAction.class);
	
	
	 private HttpSession session = null;	
	
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 
	 private String result;

	 private String userid;
	 private String empno;
	 private String fname;
	 private String lname;
	 private String dob;
	 private String mnumber;
	 private String mailid;
	 private String gender;
	 private String offaddress;
	 private String peraddress;
	 private String refno;
	 private String actiontype;
	 private String PageDirection = null;
	 private String status;
	 private String jtitle;
	 private String branchcode;
	 private String decision;
	 private String Narration;
	 private String damtlmt;
	 private String numoftran;
	 private String ptamt;
	 private String displayname;
	 
	 private String updatedamtlmt;
	 private String updateptamt;
	 private String updatenumoftran;
	 private String limitcode;
	 private String limitdesc;
	 private String updatelimitcode;
	 private String currlimitcode;
	 
	 
	 private File file;
	 private String fileContentType;
	 private String fileFileName;
	 private String filesPath;
	 
	 private String filename;
	 private String records;
	 private String jsondata;
	 
	 private String updatemobileno;
	 private String updatebranchdetails;
	 
	 private String finaljsonarray;
	 
	 private String productcode;	 
	 private String productdesc;
	 private String productcurr;
	 private String application;
	 
	 private String MERCHANT_ID;
	 
	 private String MERCHANT_NAME;	 
	 private String ACCOUNT_NUMBER;
	 private String EMAIL;
	 private String TELEPHONE_NO;
	 
	 private String SUPER_AGENT;
	 private String PRODUCT;
	 
	 private String rno;
		
	 private String vselcr;
	 private String vseldr;
	 private String vselbl;
	 
	 private String trans;
	 private String fromdate;
	 private String todate;
	 
	 private String limitCode;
	 private String limitDescription;
	 
	 
	 private String fraudcode;
	 private String frauddesc;
	 private String contcentermailid;
	 private String decisions;
	 private String Customersms;
	 private String Customeremail;
	 private String ruledesc;
	 private String rulecode;
	 private String ruletype;
	 private String fruadrules;
	 private String customercode;
	 
	 private String fullname;
	 private String telephone;
	 private String wapplication;
	 
	 private String customerId;
	 private String srchcriteria;
	 
	 
	 private String segments;
	 private String subsegments;
	 private String colors;
	 private String services;
	 private String campaigndis;
	 private String lifestyle;
	 private String dvPreviews;
	 private String tonev;
	 private String faqs;
	 private String rms;
	 
	 private String paymentrefno;
	 private String txnamt;
	 private String channel;
	 private String txntype;
	 private String txndatetime;
	 private String batchid;
	 private String remarks;
	 private String requesttype;
	 private String waccountno;
	 private String imeinumber;
	 
	 private String reason;
	 private String imeistatus;
	 private String servicecode;
	 
	 List<LocalGovernment> productdt;
	 
	 private String accountno;
	 private String walletaccountno;
	 private String onboarddate;
	 private String middlename;
	 private String lastname;
	 private String dateofbirth;
	 private String email;
	 private String superadmin;
	 private String product;
	 private String prodesc;
	 
	 private String frmacc;
	 private String toacct;
	 private String txnsamt;
	 private String txnfee;
	 
	 private String fulldata;
	 private String searchval;
	 
	 private String stDate;
	 private String initiondate;
	 private String terminalid;
	 private String reqapplication;
	 
	 
	 
	 
	public String getReqapplication() {
		return reqapplication;
	}
	public void setReqapplication(String reqapplication) {
		this.reqapplication = reqapplication;
	}
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	public String getStDate() {
		return stDate;
	}
	public void setStDate(String stDate) {
		this.stDate = stDate;
	}
	public String getInitiondate() {
		return initiondate;
	}
	public void setInitiondate(String initiondate) {
		this.initiondate = initiondate;
	}
	public String getSearchval() {
		return searchval;
	}
	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}
	public String getFulldata() {
		return fulldata;
	}
	public void setFulldata(String fulldata) {
		this.fulldata = fulldata;
	}
	public String getFrmacc() {
		return frmacc;
	}
	public void setFrmacc(String frmacc) {
		this.frmacc = frmacc;
	}
	public String getToacct() {
		return toacct;
	}
	public void setToacct(String toacct) {
		this.toacct = toacct;
	}
	public String getTxnsamt() {
		return txnsamt;
	}
	public void setTxnsamt(String txnsamt) {
		this.txnsamt = txnsamt;
	}
	public String getTxnfee() {
		return txnfee;
	}
	public void setTxnfee(String txnfee) {
		this.txnfee = txnfee;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getWalletaccountno() {
		return walletaccountno;
	}
	public void setWalletaccountno(String walletaccountno) {
		this.walletaccountno = walletaccountno;
	}
	public String getOnboarddate() {
		return onboarddate;
	}
	public void setOnboarddate(String onboarddate) {
		this.onboarddate = onboarddate;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSuperadmin() {
		return superadmin;
	}
	public void setSuperadmin(String superadmin) {
		this.superadmin = superadmin;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProdesc() {
		return prodesc;
	}
	public void setProdesc(String prodesc) {
		this.prodesc = prodesc;
	}
	public List<LocalGovernment> getProductdt() {
		return productdt;
	}
	public void setProductdt(List<LocalGovernment> productdt) {
		this.productdt = productdt;
	}
	
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
	public String getImeistatus() {
		return imeistatus;
	}
	public void setImeistatus(String imeistatus) {
		this.imeistatus = imeistatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getImeinumber() {
		return imeinumber;
	}
	public void setImeinumber(String imeinumber) {
		this.imeinumber = imeinumber;
	}
	public String getWaccountno() {
		return waccountno;
	}
	public void setWaccountno(String waccountno) {
		this.waccountno = waccountno;
	}
	public String getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}
	public String getPaymentrefno() {
		return paymentrefno;
	}
	public void setPaymentrefno(String paymentrefno) {
		this.paymentrefno = paymentrefno;
	}
	public String getTxnamt() {
		return txnamt;
	}
	public void setTxnamt(String txnamt) {
		this.txnamt = txnamt;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTxntype() {
		return txntype;
	}
	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}
	public String getTxndatetime() {
		return txndatetime;
	}
	public void setTxndatetime(String txndatetime) {
		this.txndatetime = txndatetime;
	}
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSegments() {
		return segments;
	}
	public void setSegments(String segments) {
		this.segments = segments;
	}
	public String getSubsegments() {
		return subsegments;
	}
	public void setSubsegments(String subsegments) {
		this.subsegments = subsegments;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getCampaigndis() {
		return campaigndis;
	}
	public void setCampaigndis(String campaigndis) {
		this.campaigndis = campaigndis;
	}
	public String getLifestyle() {
		return lifestyle;
	}
	public void setLifestyle(String lifestyle) {
		this.lifestyle = lifestyle;
	}
	public String getDvPreviews() {
		return dvPreviews;
	}
	public void setDvPreviews(String dvPreviews) {
		this.dvPreviews = dvPreviews;
	}
	public String getTonev() {
		return tonev;
	}
	public void setTonev(String tonev) {
		this.tonev = tonev;
	}
	public String getFaqs() {
		return faqs;
	}
	public void setFaqs(String faqs) {
		this.faqs = faqs;
	}
	public String getRms() {
		return rms;
	}
	public void setRms(String rms) {
		this.rms = rms;
	}
	public String getSrchcriteria() {
		return srchcriteria;
	}
	public void setSrchcriteria(String srchcriteria) {
		this.srchcriteria = srchcriteria;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getWapplication() {
		return wapplication;
	}
	public void setWapplication(String wapplication) {
		this.wapplication = wapplication;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getFruadrules() {
		return fruadrules;
	}
	public void setFruadrules(String fruadrules) {
		this.fruadrules = fruadrules;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getFraudcode() {
		return fraudcode;
	}
	public void setFraudcode(String fraudcode) {
		this.fraudcode = fraudcode;
	}
	public String getFrauddesc() {
		return frauddesc;
	}
	public void setFrauddesc(String frauddesc) {
		this.frauddesc = frauddesc;
	}
	public String getContcentermailid() {
		return contcentermailid;
	}
	public void setContcentermailid(String contcentermailid) {
		this.contcentermailid = contcentermailid;
	}
	public String getDecisions() {
		return decisions;
	}
	public void setDecisions(String decisions) {
		this.decisions = decisions;
	}
	public String getCustomersms() {
		return Customersms;
	}
	public void setCustomersms(String customersms) {
		Customersms = customersms;
	}
	public String getCustomeremail() {
		return Customeremail;
	}
	public void setCustomeremail(String customeremail) {
		Customeremail = customeremail;
	}
	public String getRuledesc() {
		return ruledesc;
	}
	public void setRuledesc(String ruledesc) {
		this.ruledesc = ruledesc;
	}
	public String getRulecode() {
		return rulecode;
	}
	public void setRulecode(String rulecode) {
		this.rulecode = rulecode;
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
	 
	 
	 
	 
	public String getTrans() {
		return trans;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public String getVselcr() {
		return vselcr;
	}
	public void setVselcr(String vselcr) {
		this.vselcr = vselcr;
	}
	public String getVseldr() {
		return vseldr;
	}
	public void setVseldr(String vseldr) {
		this.vseldr = vseldr;
	}
	public String getVselbl() {
		return vselbl;
	}
	public void setVselbl(String vselbl) {
		this.vselbl = vselbl;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getMERCHANT_NAME() {
		return MERCHANT_NAME;
	}
	public void setMERCHANT_NAME(String mERCHANT_NAME) {
		MERCHANT_NAME = mERCHANT_NAME;
	}
	public String getACCOUNT_NUMBER() {
		return ACCOUNT_NUMBER;
	}
	public void setACCOUNT_NUMBER(String aCCOUNT_NUMBER) {
		ACCOUNT_NUMBER = aCCOUNT_NUMBER;
	}
	public String getTELEPHONE_NO() {
		return TELEPHONE_NO;
	}
	public void setTELEPHONE_NO(String tELEPHONE_NO) {
		TELEPHONE_NO = tELEPHONE_NO;
	}
	public String getSUPER_AGENT() {
		return SUPER_AGENT;
	}
	public void setSUPER_AGENT(String sUPER_AGENT) {
		SUPER_AGENT = sUPER_AGENT;
	}
	public String getPRODUCT() {
		return PRODUCT;
	}
	public void setPRODUCT(String pRODUCT) {
		PRODUCT = pRODUCT;
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
	 
	public String getFinaljsonarray() {
		return finaljsonarray;
	}
	public void setFinaljsonarray(String finaljsonarray) {
		this.finaljsonarray = finaljsonarray;
	}
	 
	 public String getUpdatemobileno() {
		return updatemobileno;
	}

	public void setUpdatemobileno(String updatemobileno) {
		this.updatemobileno = updatemobileno;
	}

	public String getUpdatebranchdetails() {
		return updatebranchdetails;
	}

	public void setUpdatebranchdetails(String updatebranchdetails) {
		this.updatebranchdetails = updatebranchdetails;
	}

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFilesPath() {
		return filesPath;
	}

	public void setFilesPath(String filesPath) {
		this.filesPath = filesPath;
	}

	public String getCurrlimitcode() {
		return currlimitcode;
	}

	public void setCurrlimitcode(String currlimitcode) {
		this.currlimitcode = currlimitcode;
	}

	public String getUpdatelimitcode() {
		return updatelimitcode;
	}

	public void setUpdatelimitcode(String updatelimitcode) {
		this.updatelimitcode = updatelimitcode;
	}

	public String getLimitdesc() {
		return limitdesc;
	}

	public void setLimitdesc(String limitdesc) {
		this.limitdesc = limitdesc;
	}

	public String getLimitcode() {
		return limitcode;
	}

	public void setLimitcode(String limitcode) {
		this.limitcode = limitcode;
	}

	public String getUpdatedamtlmt() {
		return updatedamtlmt;
	}

	public void setUpdatedamtlmt(String updatedamtlmt) {
		this.updatedamtlmt = updatedamtlmt;
	}

	public String getUpdateptamt() {
		return updateptamt;
	}

	public void setUpdateptamt(String updateptamt) {
		this.updateptamt = updateptamt;
	}

	public String getUpdatenumoftran() {
		return updatenumoftran;
	}

	public void setUpdatenumoftran(String updatenumoftran) {
		this.updatenumoftran = updatenumoftran;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDamtlmt() {
		return damtlmt;
	}

	public void setDamtlmt(String damtlmt) {
		this.damtlmt = damtlmt;
	}

	public String getNumoftran() {
		return numoftran;
	}

	public void setNumoftran(String numoftran) {
		this.numoftran = numoftran;
	}

	public String getPtamt() {
		return ptamt;
	}

	public void setPtamt(String ptamt) {
		this.ptamt = ptamt;
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

	public String getJtitle() {
		return jtitle;
	}

	public void setJtitle(String jtitle) {
		this.jtitle = jtitle;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPageDirection() {
			return PageDirection;
	 }

	public void setPageDirection(String pageDirection) {
			PageDirection = pageDirection;
	}
	 

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getMnumber() {
		return mnumber;
	}

	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOffaddress() {
		return offaddress;
	}

	public void setOffaddress(String offaddress) {
		this.offaddress = offaddress;
	}

	public String getPeraddress() {
		return peraddress;
	}

	public void setPeraddress(String peraddress) {
		this.peraddress = peraddress;
	}

	public JSONObject getResponseJSON() {
				return responseJSON;
	}
	
	public void setResponseJSON(JSONObject responseJSON) {
				this.responseJSON = responseJSON;
	}
	
	private HttpServletRequest httpRequest = null;

	@Override
	public void setServletRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
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

	
	public String FirstPageView() {
			
			logger.debug(" FirstPageView ....");
	
			return "success";
	
	}
	
	
	public String ApplicationCode()
	{
		logger.debug("inside ApplicationCode.. ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();

			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
		  responseDTO = agdao.ApplicationCode(requestDTO);
			 
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
			agdao = null;
			errors = null;
		}
		return result;
	}
	
	
	
	public String AgentReg() {
		 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("dob", dob);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("gender", gender);
				responseJSON.put("offaddress", offaddress);
				responseJSON.put("peraddress", peraddress);
		 
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String AgentRegConfirm() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("dob", dob);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("gender", gender);
				requestJSON.put("offaddress", offaddress);
				requestJSON.put("peraddress", peraddress);
				
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("dob", dob);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("gender", gender);
				responseJSON.put("offaddress", offaddress);
				responseJSON.put("peraddress", peraddress);
		 
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String fraudcommon() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("rulecode", rulecode);
				
				
				responseJSON.put("rulecode", rulecode.split("-")[0]);
				responseJSON.put("ruledesc", rulecode.split("-")[1]);
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}

	public String AgentRegAck() {
		 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("dob", dob);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("gender", gender);
				requestJSON.put("offaddress", offaddress);
				requestJSON.put("peraddress", peraddress);
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.gtAgentRegistrtionack(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			  responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
			  
			  responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("dob", dob);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("gender", gender);
				responseJSON.put("offaddress", offaddress);
				responseJSON.put("peraddress", peraddress);
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	

	public String AgentRegback() {
		 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
	
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("limitcode", limitcode);
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("limitcode", limitcode);
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	
	public String AgentLimitback() {
		 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
	
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("updatelimitcode", updatelimitcode);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				
				
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("updatelimitcode", updatelimitcode);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	
	
	public String AgentRegModify() {
		logger.debug("[AgentAction][AgentRegModify] Inside AgentRegModify... ");
		AgentDAO agdao=null;
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

				requestJSON.put("userid", userid);
				requestJSON.put("actiontype", actiontype);
				
				
				
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.AgentRegModify(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][AgentRegModify]  Response JSON++++++++ kailash [" + responseJSON + "]");
				
				
				if(actiontype.equalsIgnoreCase("AGENTVIEW")){
					PageDirection = "agentregviewdetails";
				}
				if(actiontype.equalsIgnoreCase("AGENTBLOCK")){
					PageDirection = "agentblockdetails";
				}
				if(actiontype.equalsIgnoreCase("AGENTSTATUS")){
					PageDirection = "agentactivedetails";
				}
				if(actiontype.equalsIgnoreCase("AGENTMODIFY")){
					PageDirection = "agentmodifydetails";
				}
				if(actiontype.equalsIgnoreCase("AGENTPIN")){
					PageDirection = "agentpindetails";
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
			logger.debug("[AgentAction][AgentRegModify] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
	
	
	public String AgentRegPinSearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("fname", fname);
			requestJSON.put("mnumber", mnumber);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentRegPinSearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String AgentLimitUpdateSearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
								
			requestJSON.put("fname", fname);
			//requestJSON.put("mnumber", mnumber);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentLimitUpdateSearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String AgentRegLockSearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
								requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("customercode", customercode);
			requestJSON.put("application", application);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentRegPinSearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
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
			logger.debug("Exception in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String AgentRegModifySearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("TYPE", "PROCESS");
			
			/*requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentRegModifySearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
*/				
				result = "success";
				
			/*} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}*/
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in GetProcessingCodeViewDetails ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String AgentRegModifyConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("limitcode", limitcode.split("-")[0]);
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("limitcode", limitcode.split("-")[0]);
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	public String AgentProductUpdateConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("MERCHANT_ID", MERCHANT_ID);
				requestJSON.put("MERCHANT_NAME", MERCHANT_NAME);
				requestJSON.put("ACCOUNT_NUMBER", ACCOUNT_NUMBER);
				requestJSON.put("EMAIL", EMAIL);
				requestJSON.put("TELEPHONE_NO", TELEPHONE_NO);
				requestJSON.put("SUPER_AGENT", SUPER_AGENT);
				requestJSON.put("PRODUCT", PRODUCT);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("limitcode", limitcode.split("-")[0]);
				
				
				responseJSON.put("MERCHANT_ID", MERCHANT_ID);
				responseJSON.put("MERCHANT_NAME", MERCHANT_NAME);
				responseJSON.put("ACCOUNT_NUMBER", ACCOUNT_NUMBER);
				responseJSON.put("EMAIL", EMAIL);
				responseJSON.put("TELEPHONE_NO", TELEPHONE_NO);
				responseJSON.put("SUPER_AGENT", SUPER_AGENT);
				responseJSON.put("PRODUCT", PRODUCT);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("limitcode", limitcode.split("-")[0]);
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String AgentLimitUpdateConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("status", status);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("limitcode", limitcode.split("-")[0]);
				requestJSON.put("currlimitcode", currlimitcode);
				
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("status", status);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("limitcode", limitcode.split("-")[0]);
				responseJSON.put("currlimitcode", currlimitcode);
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String AgentRegApprovalConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("limitcode", limitcode);
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("limitcode", limitcode);
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	public String AgentRegApprovalLimitConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("updatelimitcode", updatelimitcode);
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("updatelimitcode", updatelimitcode);
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	public String AgentRegApprovalModifyConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("updatemobileno", updatemobileno);
				requestJSON.put("updatebranchdetails", updatebranchdetails);
				
				responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("updatemobileno", updatemobileno);
				responseJSON.put("updatebranchdetails", updatebranchdetails);
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	
	public String AgentRegModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("limitcode", limitcode);
				
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("displayname", displayname);
				responseJSON.put("limitcode", limitcode);
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	public String AgentLimitUpdateAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("status", status);
				requestJSON.put("refno", refno);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("currlimitcode", currlimitcode);
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentLimitUpdateAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("displayname", displayname);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("status", status);
				responseJSON.put("refno", refno);
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("currlimitcode", currlimitcode);
		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	
	
	public String AgentRegAppSearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("actiontype", actiontype);
			requestJSON.put("status", status);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentRegModifySearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
				if(status.equalsIgnoreCase("PENDING")){
					PageDirection = "agentregappifysearch";
				}else{
					if(status.equalsIgnoreCase("AUTHORIZED")){
						if(actiontype.equalsIgnoreCase("DSANEWREG")){
							responseJSON.put("status", "New Registration Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
							responseJSON.put("status", "Registration Modify Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
							responseJSON.put("status", "User Limit Update Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
							responseJSON.put("status", "User Status Active Authorized Redords");
						}
						
					}else if(status.equalsIgnoreCase("REJECTED")){
						if(actiontype.equalsIgnoreCase("DSANEWREG")){
							responseJSON.put("status", "New Registration Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
							responseJSON.put("status", "Registration Modify Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
							responseJSON.put("status", "User Limit Update Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
							responseJSON.put("status", "User Status Active Rejected Redords");
						}
						
					}else if(status.equalsIgnoreCase("DELETED")){
						if(actiontype.equalsIgnoreCase("DSANEWREG")){
							responseJSON.put("status", "New Registration Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("DSAREGMOD")){
							responseJSON.put("status", "Registration Modify Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("DSALMTUPT")){
							responseJSON.put("status", "User Limit Update Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("DSAUSRACT")){
							responseJSON.put("status", "User Status Active Deleted Redords");
						}
						
					}
					PageDirection = "agenthistorysearch";
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String AgentRegApprovalAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("displayname", displayname);
				requestJSON.put("limitcode", limitcode);
				
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegApprovalAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("displayname", displayname);
				responseJSON.put("limitcode", limitcode);
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentRegApprovalLimitUpdateAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("refno", refno);
				requestJSON.put("displayname", displayname);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("updatelimitcode", updatelimitcode);
				
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegApprovalLimitUpdateAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("refno", refno);
				responseJSON.put("displayname", displayname);
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("updatelimitcode", updatelimitcode);
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	public String AgentRegLockAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("refno", refno);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("remoteip","2312321321");
				requestJSON.put("status",status);
				if(status.equalsIgnoreCase("Deactive") || status.equalsIgnoreCase("active")){
				requestJSON.put("decision",decision);
				requestJSON.put("Narration",Narration);
				}
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegLockAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  responseJSON.put("refno", refno);
			  responseJSON.put("userid", userid);
			  responseJSON.put("empno", empno);
			  responseJSON.put("fname", fname);
			  responseJSON.put("lname", lname);
			  responseJSON.put("jtitle", jtitle);
			  responseJSON.put("mnumber", mnumber);
			  responseJSON.put("mailid", mailid);
			  responseJSON.put("branchcode", branchcode);
			  
			  if(status.equalsIgnoreCase("Deactive")){
			  responseJSON.put("decision",decision);
			  responseJSON.put("Narration",Narration);
			  }
			  
			  if(status.equalsIgnoreCase("UnLock")){
				  responseJSON.put("status","Lock");  
			  }else if(status.equalsIgnoreCase("Lock")){
				  responseJSON.put("status","UnLock");
			  }else if(status.equalsIgnoreCase("Deactive")){
				  responseJSON.put("status","Active");
			  }else if(status.equalsIgnoreCase("Active")){
				  responseJSON.put("status","Deactive");
			  }
			  
				
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	public String AgentPinResetAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("refno", refno);
				requestJSON.put("fullname", fullname);
				requestJSON.put("telephone", telephone);
				requestJSON.put("remoteip", ServletActionContext
						.getRequest().getRemoteAddr());
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentPinResetAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  responseJSON.put("refno", refno);
			  responseJSON.put("fullname", fullname);
			  responseJSON.put("telephone", telephone);
			 
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentStatusAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("refno", refno);
				requestJSON.put("fullname", fullname);
				requestJSON.put("telephone", telephone);
				requestJSON.put("remoteip", ServletActionContext
						.getRequest().getRemoteAddr());
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentStatusAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  responseJSON.put("refno", refno);
			  responseJSON.put("fullname", fullname);
			  responseJSON.put("telephone", telephone);
			 
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("refno", refno);
				requestJSON.put("fullname", fullname);
				requestJSON.put("telephone", telephone);
				requestJSON.put("remoteip", ServletActionContext
						.getRequest().getRemoteAddr());
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  responseJSON.put("refno", refno);
			  responseJSON.put("fullname", fullname);
			  responseJSON.put("telephone", telephone);
			 
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentBlockAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("refno", refno);
				requestJSON.put("fullname", fullname);
				requestJSON.put("telephone", telephone);
				requestJSON.put("remoteip", ServletActionContext
						.getRequest().getRemoteAddr());
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentBlockAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  responseJSON.put("refno", refno);
			  responseJSON.put("fullname", fullname);
			  responseJSON.put("telephone", telephone);
			 
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	
	
	public String DsaLimitmng() throws Exception
	{
		logger.debug("Inside DsaLimitmng..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.DsaLimitmng(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String LimitCodeSearch() {
		logger.debug("[AgentAction][LimitCodeSearch] Inside LimitCodeSearch... ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

				requestJSON.put("limitcode", limitcode);
				requestJSON.put("actiontype", actiontype);
				System.out.println("kailash ::: "+actiontype);
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][LimitCodeSearch]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.LimitCodeSearch(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][LimitCodeSearch]  Response JSON++++++++ [" + responseJSON + "]");
				
				
				if(actiontype.equalsIgnoreCase("NEW")){
					
					responseJSON.put("limitcode", limitcode.replaceAll("NO_DATA", ""));
					responseJSON.put("limitdesc", limitdesc);
					responseJSON.put("damtlmt", damtlmt);
					responseJSON.put("numoftran", numoftran);
					responseJSON.put("ptamt", ptamt);
					
					PageDirection = "dsanewlimitcodedetails";
				}else if(actiontype.equalsIgnoreCase("VIEW")){
					PageDirection = "dsanewlimitcodedetailsview";
				}else if(actiontype.equalsIgnoreCase("MODIFY")){
					
					
					
					responseJSON.put("updatedamtlmt", updatedamtlmt);
					responseJSON.put("updatenumoftran", updatenumoftran);
					responseJSON.put("updateptamt", updateptamt);
					
					PageDirection = "dsanewlimitcodedetailsmodify";
				}else if (actiontype.equalsIgnoreCase("HISTORY")) {
					PageDirection = "dsanewlimitcodedetailshistory";
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
			logger.debug("[AgentAction][LimitCodeSearch] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
	
	
	
	public String DsaLimitNewConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside DsaLimitNewConf ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String DsaLimitNewAck() {
		 logger.debug("[AgentRegAck][DsaLimitNewAck] Inside DsaLimitNewAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("remoteip","2312321321");
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][DsaLimitNewAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.DsaLimitNewAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			 responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DsaLimitNewAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentRegModifySrc() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			requestJSON.put("fname", fname);
			requestJSON.put("mnumber", mnumber);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.AgentRegModifySrc(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String Dsamanagement() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
		
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.Dsamanagement(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	
	
	public String LimitAppSearch() throws Exception
	{
		logger.debug("Inside AgentRegModifySearch..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			requestJSON.put("actiontype", actiontype);
			requestJSON.put("status", status);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.LimitAppSearch(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
				if(status.equalsIgnoreCase("PENDING")){
					PageDirection = "accopenapproval";
				}else{
					if(status.equalsIgnoreCase("AUTHORIZED")){
						if(actiontype.equalsIgnoreCase("NEWALT")){
							responseJSON.put("status", "New Account Limit Authorized Redords");
						}else if(actiontype.equalsIgnoreCase("ALMODY")){
							responseJSON.put("status", "Account Limit Modify Authorized Redords");	
						}
					}else if(status.equalsIgnoreCase("REJECTED")){
						if(actiontype.equalsIgnoreCase("NEWALT")){
							responseJSON.put("status", "New Account Limit Rejected Redords");
						}else if(actiontype.equalsIgnoreCase("ALMODY")){
							responseJSON.put("status", "Account Limit Modify Rejected Redords");	
						}
						
					}else if(status.equalsIgnoreCase("DELETED")){
						if(actiontype.equalsIgnoreCase("NEWALT")){
							responseJSON.put("status", "New Account Limit Deleted Redords");
						}else if(actiontype.equalsIgnoreCase("ALMODY")){
							responseJSON.put("status", "Account Limit Modify Deleted Redords");	
						}
						
					}
					PageDirection = "limithistorysearch";
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String LimitAppConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}	
	public String LimitAppAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				
				
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.LimitAppAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
			  responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	public String LimitAppModifyConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	public String LimitAppModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				
				
				
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.LimitAppModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				
				
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	public String LimitModifyConf() {
		 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
		 
		
		 try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				
				
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
				
				
				
				
		

		   result = "success";
		  
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			
			 if(requestDTO!=null)
				 requestDTO = null;
			 if(responseDTO!=null)
				 responseDTO = null;
			 if(requestJSON!=null)
				 requestJSON = null;
			
		 }

		 return result;

		}
	
	
	public String LimitModifyAck() {
		 logger.debug("[AgentRegAck][DsaLimitNewAck] Inside DsaLimitNewAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("limitdesc", limitdesc);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatedamtlmt", updatedamtlmt);
				requestJSON.put("updatenumoftran", updatenumoftran);
				requestJSON.put("updateptamt", updateptamt);
				requestJSON.put("remoteip","2312321321");
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][DsaLimitNewAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.LimitModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			 
			  	responseJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitdesc);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatedamtlmt", updatedamtlmt);
				responseJSON.put("updatenumoftran", updatenumoftran);
				responseJSON.put("updateptamt", updateptamt);
			  	

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DsaLimitNewAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	public String AgentRegModifyDetailsAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("displayname", displayname);
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("limitcode", limitcode);
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegModifyDetailsAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
			  responseJSON.put("displayname", displayname);
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("limitcode",limitcode);
		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	
	public String BulkDsaReg() {
		logger.debug("[AgentAction][BulkDsaReg] Inside BulkDsaReg... ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

				
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][BulkDsaReg]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.BulkDsaReg(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][BulkDsaReg]  Response JSON++++++++ [" + responseJSON + "]");
				
				
				
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
			logger.debug("[AgentAction][BulkDsaReg] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
	
	
	public String ResultSearch() {
		logger.debug("[AgentAction][BulkDsaReg] Inside BulkDsaReg... ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

				
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][BulkDsaReg]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.ResultSearch(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][BulkDsaReg]  Response JSON++++++++ [" + responseJSON + "]");
				
				
				
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
			logger.debug("[AgentAction][BulkDsaReg] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
	
	public String BulkDsaRegProcess()
	{
		 ArrayList<String> errors = null;
		 ArrayList<String> messages = null;
		 
		/*System.out.println("File Name is:"+getFileFileName());
        System.out.println("File ContentType is:"+getFileContentType());
        System.out.println("Files Directory is:"+filesPath);*/
        ResourceBundle rb = null;
		try {
			//System.out.println("I am Here ...11.");
			addActionError("I don't know you, dont try to hack me!");
			//System.out.println("I am Here ....");
			
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("limitcode",limitcode);
			

			logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Request DTO [" + requestDTO + "]");

			rb = ResourceBundle.getBundle("auth");
			String filepath=rb.getString("STORE_FILE_PATH");
			
			
			if(saveFile(getFile(), getFileFileName(), filepath)){
				
				  responseJSON.put("limitcode", limitcode);
				 
				  responseJSON.put("filename", getFileFileName());
				  responseJSON.put("records", RaasValidator.getRecords(filepath+"/"+getFileFileName()));
				  
				  
			}
			
			result="success";	
			
		} catch (IOException e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
		} finally{
	 		
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
	
	
	
	public String BulkDsaRegProcessconf()
	{
		 ArrayList<String> errors = null;
		 ArrayList<String> messages = null;
		 ExcelParsing ep=null;
		
        ResourceBundle rb = null;
		try {
			//System.out.println("I am Here ...11.");
			addActionError("I don't know you, dont try to hack me!");
			//System.out.println("I am Here ....");
			
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("limitcode", limitcode);
			
			requestJSON.put("filename", filename);
			requestJSON.put("records", records);
			
			rb = ResourceBundle.getBundle("auth");
			String filepath=rb.getString("STORE_FILE_PATH");
			requestJSON.put("filepath", filepath);
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ep=new ExcelParsing();
			
			responseDTO= ep.getRAASData(requestDTO);
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
				
				responseJSON.put("limitcode", limitcode);
				responseJSON.put("filename", filename);
				responseJSON.put("records", records);
				
				result = "success";
				
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}


			
			
			result="success";	
			
		} catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
		} finally{
	 		
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
	
	
	public String BulkDsaRegProcessack()
	{
		 ArrayList<String> errors = null;
		 ArrayList<String> messages = null;
		 ExcelParsing ep=null;
		
        ResourceBundle rb = null;
		try {
			System.out.println("I am Here ...11.");
			addActionError("I don't know you, dont try to hack me!");
			System.out.println("I am Here ....");
			
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestJSON.put("limitcode", limitcode);
			
			requestJSON.put("filename", filename);
			requestJSON.put("records", records);
			requestJSON.put("jsondata", jsondata);
			
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			ep=new ExcelParsing();
			
			responseDTO= ep.getUserADDataAck(requestDTO);
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
				responseJSON.put("limitcode", limitcode);
				
				responseJSON.put("filename", filename);
				responseJSON.put("records", records);
				
				result = "success";
				
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}


			
			
			result="success";	
			
		} catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
		} finally{
	 		
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
	
	
	
	
	public String AgentRegApprovalModifyAck() {
		 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
		 
		 
		 AgentDAO agdao=null;
		 ArrayList<String> errors = null;
		 
		 try {
			 requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("userid", userid);
				requestJSON.put("empno", empno);
				requestJSON.put("fname", fname);
				requestJSON.put("lname", lname);
				requestJSON.put("jtitle", jtitle);
				requestJSON.put("mnumber", mnumber);
				requestJSON.put("mailid", mailid);
				requestJSON.put("branchcode", branchcode);
				requestJSON.put("decision", decision);
				requestJSON.put("Narration", Narration);
				requestJSON.put("damtlmt", damtlmt);
				requestJSON.put("numoftran", numoftran);
				requestJSON.put("ptamt", ptamt);
				requestJSON.put("updatemobileno", updatemobileno);
				requestJSON.put("updatebranchdetails", updatebranchdetails);
				requestJSON.put("refno", refno);
				requestJSON.put("displayname", displayname);
				requestJSON.put("limitcode", limitcode);
				
				requestJSON.put("remoteip","2312321321");
				
				
		  requestDTO.setRequestJSON(requestJSON);

		  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

		  agdao = new AgentDAO();
		  responseDTO = agdao.AgentRegApprovalModifyAck(requestDTO);
		  logger.debug("Response DTO is ["+responseDTO+"]");
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			  
			 
				responseJSON.put("userid", userid);
				responseJSON.put("empno", empno);
				responseJSON.put("fname", fname);
				responseJSON.put("lname", lname);
				responseJSON.put("jtitle", jtitle);
				responseJSON.put("mnumber", mnumber);
				responseJSON.put("mailid", mailid);
				responseJSON.put("branchcode", branchcode);
				responseJSON.put("decision", decision);
				responseJSON.put("Narration", Narration);
				responseJSON.put("damtlmt", damtlmt);
				responseJSON.put("numoftran", numoftran);
				responseJSON.put("ptamt", ptamt);
				responseJSON.put("updatemobileno", updatemobileno);
				responseJSON.put("updatebranchdetails", updatebranchdetails);
				responseJSON.put("refno", refno);
				responseJSON.put("displayname", displayname);
				responseJSON.put("limitcode", limitcode);
		

		   result = "success";
		  } else {
		   errors = (ArrayList<String>) responseDTO
		     .getErrors();
		   for (int i = 0; i < errors.size(); i++) {
		    addActionError(errors.get(i));
		   }
		   result = "fail";
		  }
		 } catch (Exception e) {
		  result = "fail";
		  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
		  addActionError("Internal error occured.");
		 } finally{
			 if(agdao!=null)
				 agdao = null;
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
	
	
	public static boolean saveFile(File file, String fileName, String filesDirectory) throws IOException{
		FileInputStream in = null;
        FileOutputStream out = null;
        boolean result=false;
        
        File dir = new File (filesDirectory);
        if ( !dir.exists() )
           dir.mkdirs();
        
        String targetPath =  dir.getPath() + File.separator + fileName+"/";
        System.out.println("source file path ::"+file.getAbsolutePath());
        System.out.println("saving file to ::" + targetPath);
        File destinationFile = new File ( targetPath);
        try {
            in = new FileInputStream( file );
            out = new FileOutputStream( destinationFile );
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
            result=true;
        }catch(Exception e){
        	result=false;
        	e.printStackTrace();
        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
      return result;  
	}
	
	
	
	public String ApprovalAll() throws Exception
	{
		logger.debug("Inside ApprovalAll..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.ApprovalAll(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String ApprovalDetails() throws Exception
	{
		logger.debug("Inside ApprovalDetails..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			session = ServletActionContext.getRequest().getSession();
			
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestJSON.put("actiontype", actiontype);
			requestJSON.put("status", status);
			System.out.println("kailash :: "+status);
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.ApprovalDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String ChannelManagement() throws Exception
	{
		logger.debug("Inside ChannelManagement..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.ChannelManagement(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String FraudDetails() throws Exception
	{
		logger.debug("Inside ChannelManagement..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.FraudDetails(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	public String ChannelMapping() {
		logger.debug("[AgentAction][ChannelMapping] Inside ChannelMapping... ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

			
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][ChannelMapping]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.ChannelMapping(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][ChannelMapping]  Response JSON++++++++ [" + responseJSON + "]");
				
				
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
			logger.debug("[AgentAction][ChannelMapping] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
	
	public String ChannelMappingConf(){
		

		logger.debug("########################### ChannelMappingConf Data Started ###########################");



		try {

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
			
			logger.info("finaljsonarray >>> ["+finaljsonarray+"]");

			JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


			responseJSON.put("FINAL_JSON", jsonarray);



			logger.info("Response Json ["+ responseJSON+"]");

			result = "success";	


		} catch (Exception e) {

			result = "fail";
			e.printStackTrace();
			logger.debug("Exception in getRequest["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");

		} finally {

			requestJSON = null;

		}

		logger.debug("########################### ChannelMappingConf Data End ###########################");

		return result;
	}
	
	
	public String ChannelMappingAck(){

		logger.debug("########################### ChannelMappingAck Data Started ###########################");

		ArrayList<String> errors = null;
		AgentDAO agdao=null;
		try {
			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			
			session = ServletActionContext.getRequest().getSession();
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("productcode", productcode);
			requestJSON.put("productdesc", productdesc);
			requestJSON.put("productcurr", productcurr);
			requestJSON.put("application", application);

			
			responseJSON.put("productcode", productcode);
			responseJSON.put("productdesc", productdesc);
			responseJSON.put("productcurr", productcurr);
			responseJSON.put("application", application);
			
			
			agdao= new AgentDAO();

			logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


			JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


			responseJSON.put("FINAL_JSON", jsonarray);

			requestDTO.setRequestJSON(responseJSON);
			
			responseDTO = agdao.ChannelMappingAck(requestDTO);
			
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				result = "success";

			} else {

				errors = (ArrayList<String>) responseDTO.getErrors();


				for (int i = 0; i < errors.size(); i++) {

					addActionError(errors.get(i));

				}
				result = "fail";

			}
			

			logger.info("Response Json ["+ responseJSON+"]");


		} catch (Exception e) {

			result = "fail";
			e.printStackTrace();
			logger.debug("Exception in getRequest["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");

		} finally {

			requestJSON = null;

		}

		logger.debug("########################### ChannelMappingAck Data End ###########################");

		return result;
	}
	
	
	public String FinAccountMapping() throws Exception
	{
		logger.debug("Inside ChannelManagement..");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			
			
			requestDTO.setRequestJSON(requestJSON);
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();
			responseDTO= agdao.FinAccountMapping(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
				//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
				
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
			if(agdao!=null)
				 agdao = null;
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
	
	
	public String FinAccountMappingAdd() {
		logger.debug("[AgentAction][ChannelMapping] Inside ChannelMapping... ");
		AgentDAO agdao=null;
		ArrayList<String> errors = null;
		ArrayList<String> messages = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			  

			
				
				requestDTO.setRequestJSON(requestJSON);
			    logger.debug("[AgentAction][ChannelMapping]  Request DTO [" + requestDTO + "]");
			  
				agdao = new AgentDAO();
				responseDTO = agdao.FinAccountMappingAdd(requestDTO);
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
				logger.debug("[AgentAction][ChannelMapping]  Response JSON++++++++ [" + responseJSON + "]");
				
				
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
			logger.debug("[AgentAction][ChannelMapping] Exception in View details ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			if(agdao!=null)
				agdao = null;
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
public String FinAccountMappingConf(){
		

		logger.debug("########################### ChannelMappingConf Data Started ###########################");



		try {

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
			
			logger.info("finaljsonarray >>> ["+finaljsonarray+"]");

			JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


			responseJSON.put("FINAL_JSON", jsonarray);



			logger.info("Response Json ["+ responseJSON+"]");

			result = "success";	


		} catch (Exception e) {

			result = "fail";
			e.printStackTrace();
			logger.debug("Exception in getRequest["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");

		} finally {

			requestJSON = null;

		}

		logger.debug("########################### ChannelMappingConf Data End ###########################");

		return result;
	}	
	
public String FinAccountMappingAck(){

	logger.debug("########################### ChannelMappingAck Data Started ###########################");

	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		
		requestJSON.put("productcode", productcode);
		requestJSON.put("productdesc", productdesc);
		requestJSON.put("productcurr", productcurr);
		requestJSON.put("application", application);

		
		responseJSON.put("productcode", productcode);
		responseJSON.put("productdesc", productdesc);
		responseJSON.put("productcurr", productcurr);
		responseJSON.put("application", application);
		
		
		agdao= new AgentDAO();

		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.FinAccountMappingAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}


public String AgentProductUpdateSearch() throws Exception
{
	logger.debug("Inside AgentRegModifySearch..");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("fname", fname);
		//requestJSON.put("mnumber", mnumber);
		
		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		agdao = new AgentDAO();
		responseDTO= agdao.AgentProductUpdateSearch(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
			//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
			
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
		if(agdao!=null)
			 agdao = null;
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

public String AgentProductDetails() {
	logger.debug("[AgentAction][AgentRegModify] Inside AgentRegModify... ");
	AgentDAO agdao=null;
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
							
			requestJSON.put("userid", userid);
			requestJSON.put("actiontype", actiontype);
			System.out.println("actiontype  :: "+actiontype);
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[AgentAction][AgentRegModify]  Request DTO [" + requestDTO + "]");
		  
			agdao = new AgentDAO();
			responseDTO = agdao.AgentProductDetails(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][AgentRegModify]  Response JSON++++++++ [" + responseJSON + "]");
			
			if (actiontype.equalsIgnoreCase("LIMIT")) {
				PageDirection = "agentproductupdatedetails";
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
		logger.debug("[AgentAction][AgentRegModify] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
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



public String AgentProductUpdateAck() {
	 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			 requestJSON.put("MERCHANT_ID", MERCHANT_ID);
			requestJSON.put("MERCHANT_NAME", MERCHANT_NAME);
			requestJSON.put("ACCOUNT_NUMBER", ACCOUNT_NUMBER);
			requestJSON.put("EMAIL", EMAIL);
			requestJSON.put("TELEPHONE_NO", TELEPHONE_NO);
			requestJSON.put("SUPER_AGENT", SUPER_AGENT);
			requestJSON.put("PRODUCT", PRODUCT);
			requestJSON.put("updatedamtlmt", updatedamtlmt);
			requestJSON.put("updatenumoftran", updatenumoftran);
			requestJSON.put("updateptamt", updateptamt);
			requestJSON.put("status", status);
			requestJSON.put("refno", refno);
			requestJSON.put("limitcode", limitcode);
			requestJSON.put("remoteip","2312321321");
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.AgentProductUpdateAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		 
			responseJSON.put("MERCHANT_ID", MERCHANT_ID);
			responseJSON.put("MERCHANT_NAME", MERCHANT_NAME);
			responseJSON.put("ACCOUNT_NUMBER", ACCOUNT_NUMBER);
			responseJSON.put("EMAIL", EMAIL);
			responseJSON.put("TELEPHONE_NO", TELEPHONE_NO);
			responseJSON.put("SUPER_AGENT", SUPER_AGENT);
			responseJSON.put("PRODUCT", PRODUCT);
			responseJSON.put("updatedamtlmt", updatedamtlmt);
			responseJSON.put("updatenumoftran", updatenumoftran);
			responseJSON.put("updateptamt", updateptamt);
			responseJSON.put("status", status);
			responseJSON.put("refno", refno);
			responseJSON.put("limitcode", limitcode);
	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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
public String AgentRegDevice() {
	 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestJSON.put("refno", refno);
			requestJSON.put("userid", userid);
			requestJSON.put("empno", empno);
			requestJSON.put("fname", fname);
			requestJSON.put("lname", lname);
			requestJSON.put("jtitle", jtitle);
			requestJSON.put("mnumber", mnumber);
			requestJSON.put("mailid", mailid);
			requestJSON.put("branchcode", branchcode);
			requestJSON.put("remoteip","2312321321");
			requestJSON.put("status",status);
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.AgentRegDevice(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		 
		  responseJSON.put("refno", refno);
		  responseJSON.put("userid", userid);
		  responseJSON.put("empno", empno);
		  responseJSON.put("fname", fname);
		  responseJSON.put("lname", lname);
		  responseJSON.put("jtitle", jtitle);
		  responseJSON.put("mnumber", mnumber);
		  responseJSON.put("mailid", mailid);
		  responseJSON.put("branchcode", branchcode);
		  responseJSON.put("status",status);
		  	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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


public String dsaReconcilesearch() {
	 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.dsaReconcilesearch(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO.getMessages()+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  String str=(responseDTO.getMessages()).toString();
		  System.out.println("kailash ::: "+str);
		  if(str.equalsIgnoreCase("[Success]")){
			  result = "success";  
		  }else{
			  result = "fail";  
		  }
		
			
	
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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


public String dsaReconcileDetails() {
	 
	 
	  AgentDAO agdao=null;
	 
		logger.debug("inside dsaReconcileDetails.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("userid", userid);
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();			
			responseDTO = agdao.dsaReconcileDetails(requestDTO);
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


public String dsaReconcileConf() {
	 
	 
	  AgentDAO agdao=null;
	 
		logger.debug("inside dsaReconcileDetails.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("userid", userid);
			requestJSON.put("rno", rno);
			
			requestJSON.put("vselcr", vselcr);
			requestJSON.put("vseldr", vseldr);
			requestJSON.put("vselbl", vselbl);
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();			
			responseDTO = agdao.dsaReconcileConf(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				
				responseJSON.put("vselcr", vselcr);
				responseJSON.put("vseldr", vseldr);
				responseJSON.put("vselbl", vselbl);
				responseJSON.put("rno", rno);
				
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

public String dsaReconcileAck() {
	 
	 
	  AgentDAO agdao=null;
	 
		logger.debug("inside dsaReconcileDetails.. ");
		
		ArrayList<String> errors = null;
		try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
								session.getAttribute(CevaCommonConstants.MAKER_ID));
								requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("userid", userid);
			requestJSON.put("rno", rno);
			
			requestJSON.put("vselcr", vselcr);
			requestJSON.put("vseldr", vseldr);
			requestJSON.put("vselbl", vselbl);
			
			requestDTO.setRequestJSON(requestJSON);				
			logger.debug("Request DTO [" + requestDTO + "]");
			agdao = new AgentDAO();			
			responseDTO = agdao.dsaReconcileAck(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				
				
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BINGRP_INFO);
				logger.debug("Response JSON  [" + responseJSON + "]");
				//responseJSON = constructToResponseJson(httpRequest,responseJSON);
				
				responseJSON.put("vselcr", vselcr);
				responseJSON.put("vseldr", vseldr);
				responseJSON.put("vselbl", vselbl);
				responseJSON.put("rno", rno);
				
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

public String complaincedetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("trans",trans);
		requestJSON.put("application",application);
		requestJSON.put("fname",fname);
		requestJSON.put("fromdate",fromdate);
		requestJSON.put("todate",todate);
		requestJSON.put("searchval",searchval);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.complaincedetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String walletcomplaincedetails(){
	
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("trans",trans);
		requestJSON.put("wapplication",wapplication);
		requestJSON.put("fname",fname);
		requestJSON.put("fromdate",fromdate);
		requestJSON.put("todate",todate);
		requestJSON.put("searchval",searchval);
		requestJSON.put("terminalid",terminalid);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.complaincedetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String ParameterCofigConf(){
	

	logger.debug("########################### ChannelMappingConf Data Started ###########################");



	try {

		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
	
		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");

		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);



		logger.info("Response Json ["+ responseJSON+"]");

		result = "success";	


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingConf Data End ###########################");

	return result;
}

public String ParameterCofigAck(){

	logger.debug("########################### ChannelMappingAck Data Started ###########################");

	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
		
		agdao= new AgentDAO();

		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.ParameterCofigAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}

public String ParameterCofigAuthAck(){

	logger.debug("########################### ChannelMappingAck Data Started ###########################");

	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
		
		agdao= new AgentDAO();

		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.ParameterCofigAuthAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}

public String ClusterCreationAck(){

	logger.debug("########################### ChannelMappingAck Data Started ###########################");

	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
		
		agdao= new AgentDAO();

		logger.info("finaljsonarray >>> ["+finaljsonarray+"]");


		JSONArray jsonarray = JSONArray.fromObject(finaljsonarray);


		responseJSON.put("FINAL_JSON", jsonarray);

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.ClusterCreationAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}




public String ParameterCreationauthView() {
	 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			

			
			requestJSON.put("limitCode", limitCode);
			requestJSON.put("limitDescription", limitDescription);
			
			
			
			responseJSON.put("limitCode", limitCode);
			responseJSON.put("limitDescription", limitDescription);
			

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}


public String notification(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
							requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.notification(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
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
		e.printStackTrace();
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		agdao = null;
		errors = null;
	}

	return result;
}

public String NotificationView() {
	logger.debug("[AgentAction][NotificationView] Inside NotificationView... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		
			requestJSON.put("refno", refno);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new AgentDAO();
			responseDTO = agdao.NotificationView(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][NotificationView]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][NotificationView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
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

public String branchActivities() {
	logger.debug("[AgentAction][branchActivities] Inside branchActivities... ");
	AgentDAO agdao=null;
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
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[AgentAction][branchActivities]  Request DTO [" + requestDTO + "]");
		  
			agdao = new AgentDAO();
			responseDTO = agdao.branchActivities(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][branchActivities]  Response JSON++++++++ [" + responseJSON + "]");
			
			
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
		logger.debug("[AgentAction][branchActivities] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
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

public String CustomerQueries() throws Exception
{
	logger.debug("Inside CustomerQueries..");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		agdao = new AgentDAO();
		responseDTO= agdao.CustomerQueries(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
			//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
			
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
		if(agdao!=null)
			 agdao = null;
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

public String CustomerQueriesView() {
	logger.debug("[AgentAction][CustomerQueriesView] Inside CustomerQueriesView... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		
			requestJSON.put("refno", refno);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new AgentDAO();
			responseDTO = agdao.CustomerQueriesView(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][CustomerQueriesView]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][CustomerQueriesView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
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

public String CustomerQueriesReplay() {
	logger.debug("[AgentAction][CustomerQueriesReplay] Inside gtFileApprovalActionAck... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		  session = ServletActionContext.getRequest().getSession();
			requestJSON.put("refno", refno);
			requestJSON.put("userid", userid);
			requestJSON.put("filename", filename);
			requestJSON.put("Narration", Narration);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID).toString());
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP").toString());
			
			
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][CustomerQueriesReplay]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new AgentDAO();
			responseDTO = agdao.CustomerQueriesReplay(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][CustomerQueriesReplay]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][CustomerQueriesReplay] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
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

public String CustomerQueriesReplied() throws Exception
{
	logger.debug("Inside CustomerQueries..");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		agdao = new AgentDAO();
		responseDTO= agdao.CustomerQueriesReplied(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
			//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
			
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
		if(agdao!=null)
			 agdao = null;
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

public String FraudCreationconfigConfig() {
	 logger.debug("[AgentRegConfirm][AgentRegConfirm] Inside AgentRegConfirm ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("fraudcode", fraudcode);
			requestJSON.put("frauddesc", frauddesc);
			
			requestJSON.put("contcentermailid", contcentermailid);
			requestJSON.put("decisions", decisions);
			requestJSON.put("Customersms", Customersms);
			requestJSON.put("Customeremail", Customeremail);
			requestJSON.put("ruledesc", ruledesc);
			requestJSON.put("rulecode", rulecode);
			requestJSON.put("ruletype", ruletype);
			
			responseJSON.put("fraudcode", fraudcode);
			responseJSON.put("frauddesc", frauddesc);
			
			responseJSON.put("contcentermailid", contcentermailid);
			responseJSON.put("decisions", decisions);
			responseJSON.put("Customersms", Customersms);
			responseJSON.put("Customeremail", Customeremail);
			responseJSON.put("ruledesc", ruledesc);
			responseJSON.put("rulecode", rulecode);
			responseJSON.put("ruletype", ruletype);
			
			
	 
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentRegConfirm][AgentRegConfirm]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String FraudCreationconfigAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("fraudcode", fraudcode);
			requestJSON.put("frauddesc", frauddesc);
			
			requestJSON.put("contcentermailid", contcentermailid);
			requestJSON.put("decisions", decisions);
			requestJSON.put("Customersms", Customersms);
			requestJSON.put("Customeremail", Customeremail);
			requestJSON.put("ruledesc", ruledesc);
			requestJSON.put("rulecode", rulecode);
			requestJSON.put("ruletype", ruletype);
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.FraudCreationconfigAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON.put("fraudcode", fraudcode);
			responseJSON.put("frauddesc", frauddesc);
			
			responseJSON.put("contcentermailid", contcentermailid);
			responseJSON.put("decisions", decisions);
			responseJSON.put("Customersms", Customersms);
			responseJSON.put("Customeremail", Customeremail);
			responseJSON.put("ruledesc", ruledesc);
			responseJSON.put("rulecode", rulecode);
			responseJSON.put("ruletype", ruletype);
	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String ParameterCreationauthvw() {
	logger.debug("[AgentAction][CustomerQueriesView] Inside CustomerQueriesView... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		
			requestJSON.put("refno", refno);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new AgentDAO();
			responseDTO = agdao.ParameterCreationauthvw(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][CustomerQueriesView]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][CustomerQueriesView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
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


public String ParameterCreationauthactiveack() {
	logger.debug("[AgentAction][CustomerQueriesView] Inside CustomerQueriesView... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
		try {
		  requestJSON = new JSONObject();
		  requestDTO = new RequestDTO();
		  
		
			requestJSON.put("refno", refno);
			requestDTO.setRequestJSON(requestJSON);
			

		    logger.debug("[AgentAction][gtFileApprovalAction]  Request DTO [" + requestDTO + "]");
		  
		    agdao = new AgentDAO();
			responseDTO = agdao.ParameterCreationauthvw(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[SalaryParameterAction][CustomerQueriesView]  Response JSON++++++++ [" + responseJSON + "]");
			
		
		
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
		logger.debug("[AgentAction][CustomerQueriesView] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
		} finally {
		if(agdao!=null)
			agdao = null;
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


public String fraudAssign() throws Exception
{
	logger.debug("Inside ChannelManagement..");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		requestDTO.setRequestJSON(requestJSON);
		logger.debug("Request DTO [" + requestDTO + "]");
		agdao = new AgentDAO();
		responseDTO= agdao.fraudAssign(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
			//logger.debug("Response JSON***************************************** [" + responseJSON + "]");
			
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
		if(agdao!=null)
			 agdao = null;
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

public String fraudMapping() {
	logger.debug("[AgentAction][ChannelMapping] Inside ChannelMapping... ");
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> messages = null;
	try {
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestDTO = new RequestDTO();
		responseDTO = new ResponseDTO();
		  

		
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[AgentAction][ChannelMapping]  Request DTO [" + requestDTO + "]");
		  
			agdao = new AgentDAO();
			responseDTO = agdao.fraudMapping(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][ChannelMapping]  Response JSON++++++++ [" + responseJSON + "]");
			
			
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
		logger.debug("[AgentAction][ChannelMapping] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
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

public String fraudMappingConf(){
	

	logger.debug("########################### ChannelMappingConf Data Started ###########################");



	try {

		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		requestJSON.put("productcode", productcode);
		requestJSON.put("productdesc", productdesc);
		requestJSON.put("productcurr", productcurr);
		requestJSON.put("application", application);
		requestJSON.put("fruadrules", fruadrules);

		
		responseJSON.put("productcode", productcode);
		responseJSON.put("productdesc", productdesc);
		responseJSON.put("productcurr", productcurr);
		responseJSON.put("application", application);
		responseJSON.put("fruadrules", fruadrules);
		
		

		result = "success";	


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingConf Data End ###########################");

	return result;
}


public String fraudMappingAck(){

	logger.debug("########################### ChannelMappingAck Data Started ###########################");

	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("productcode", productcode);
		requestJSON.put("productdesc", productdesc);
		requestJSON.put("productcurr", productcurr);
		requestJSON.put("application", application);
		requestJSON.put("fruadrules", fruadrules);

		
		responseJSON.put("productcode", productcode);
		responseJSON.put("productdesc", productdesc);
		responseJSON.put("productcurr", productcurr);
		responseJSON.put("application", application);
		responseJSON.put("fruadrules", fruadrules);
		
		
		agdao= new AgentDAO();

		

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.fraudMappingAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}

public String registrationfaildetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("srchcriteria",srchcriteria);
		requestJSON.put("customerId",customerId);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.registrationfaildetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}



public String xpxdetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("srchcriteria",srchcriteria);
		requestJSON.put("customerId",customerId);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.xpxdetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String rassinformation(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("srchcriteria",srchcriteria);
		requestJSON.put("customerId",customercode);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.rassinformation(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String segmentconfirm() {
	 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			responseJSON.put("segments", segments);
			responseJSON.put("subsegments", subsegments);
			
			responseJSON.put("colors", colors);
			responseJSON.put("services", services);
			responseJSON.put("campaigndis", campaigndis);
			responseJSON.put("lifestyle", lifestyle);
			responseJSON.put("dvPreviews", dvPreviews);
			responseJSON.put("tonev", tonev);
			responseJSON.put("faqs", faqs);
			responseJSON.put("rms", rms);
	 
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String segmentAck(){


	ArrayList<String> errors = null;
	AgentDAO agdao=null;
	try {
		requestDTO = new RequestDTO();
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		
		session = ServletActionContext.getRequest().getSession();
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
		
		requestJSON.put("segments", segments);
		requestJSON.put("subsegments", subsegments);
		
		requestJSON.put("colors", colors);
		requestJSON.put("services", services);
		requestJSON.put("campaigndis", campaigndis);
		requestJSON.put("lifestyle", lifestyle);
		requestJSON.put("dvPreviews", dvPreviews);
		requestJSON.put("tonev", tonev);
		requestJSON.put("faqs", faqs);
		requestJSON.put("rms", rms);

		
		responseJSON.put("segments", segments);
		responseJSON.put("subsegments", subsegments);
		
		responseJSON.put("colors", colors);
		responseJSON.put("services", services);
		responseJSON.put("campaigndis", campaigndis);
		responseJSON.put("lifestyle", lifestyle);
		responseJSON.put("dvPreviews", dvPreviews);
		responseJSON.put("tonev", tonev);
		responseJSON.put("faqs", faqs);
		responseJSON.put("rms", rms);
		
		
		agdao= new AgentDAO();

		

		requestDTO.setRequestJSON(responseJSON);
		
		responseDTO = agdao.segmentAck(requestDTO);
		
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {

			result = "success";

		} else {

			errors = (ArrayList<String>) responseDTO.getErrors();


			for (int i = 0; i < errors.size(); i++) {

				addActionError(errors.get(i));

			}
			result = "fail";

		}
		

		logger.info("Response Json ["+ responseJSON+"]");


	} catch (Exception e) {

		result = "fail";
		e.printStackTrace();
		logger.debug("Exception in getRequest["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");

	} finally {

		requestJSON = null;

	}

	logger.debug("########################### ChannelMappingAck Data End ###########################");

	return result;
}

public String agentrequestdetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("mnumber",mnumber);
		requestJSON.put("application",application);
		
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.agentrequestdetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			responseJSON.put("application",application);
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String agentrequestinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("refno",refno);		
		requestJSON.put("requesttype",requesttype);
		requestJSON.put("application",application);
		
		 agdao = new AgentDAO();
		responseDTO = agdao.agentrequestinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			responseJSON.put("application",application);
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("requesttype",requesttype);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String agentrequestinfoconfirm() {
	 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("frmacc", frmacc);
			requestJSON.put("toacct", toacct);
			requestJSON.put("remarks", remarks);
			requestJSON.put("requesttype", requesttype);
			requestJSON.put("txnsamt", txnsamt);
			requestJSON.put("txnfee", txnfee);
			requestJSON.put("actiontype", actiontype);
			requestJSON.put("fulldata", fulldata);
			
			/*requestJSON.put("batchid", batchid);
			requestJSON.put("Narration", Narration);*/
			//requestJSON.put("waccountno", waccountno);
			
			responseJSON.put("paymentrefno", paymentrefno);
			responseJSON.put("txnamt", txnamt);
			
			responseJSON.put("batchid", batchid);
			responseJSON.put("Narration", Narration);
			
			responseJSON.put("userid", userid);
			responseJSON.put("channel", channel);
			responseJSON.put("txntype", txntype);
			responseJSON.put("txndatetime", txndatetime);
			responseJSON.put("frmacc", frmacc);
			responseJSON.put("toacct", toacct);
			responseJSON.put("remarks", remarks);
			responseJSON.put("requesttype", requesttype);
			
			responseJSON.put("txnsamt", txnsamt);
			responseJSON.put("txnfee", txnfee);
			responseJSON.put("actiontype", actiontype);
			responseJSON.put("fulldata", fulldata);
			
			responseJSON.put("application",application);
			
			//responseJSON.put("waccountno", waccountno);
			
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String agentrequestinfoAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("frmacc", frmacc);
			requestJSON.put("toacct", toacct);
			requestJSON.put("remarks", remarks);
			requestJSON.put("requesttype", requesttype);
			requestJSON.put("waccountno", waccountno);
			requestJSON.put("actiontype", actiontype);
			requestJSON.put("fulldata", fulldata);
			
			requestJSON.put("application",application);
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.agentrequestinfoAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON.put("paymentrefno", paymentrefno);
			responseJSON.put("txnamt", txnamt);
			
			responseJSON.put("userid", userid);
			responseJSON.put("channel", channel);
			responseJSON.put("txntype", txntype);
			responseJSON.put("txndatetime", txndatetime);
			responseJSON.put("frmacc", frmacc);
			responseJSON.put("toacct", toacct);
			responseJSON.put("remarks", remarks);
			responseJSON.put("txnsamt", txnsamt);
			responseJSON.put("txnfee", txnfee);
			responseJSON.put("actiontype", actiontype);
	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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
public String agentrequestApproval(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.agentrequestApproval(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String agentrequestapprovaldetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("application",application);
		
		 agdao = new AgentDAO();
		responseDTO = agdao.agentrequestapprovaldetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("application",application);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String agentrequestapprovalinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("refno",refno);
		requestJSON.put("application",application);
		
		
		 agdao = new AgentDAO();
		responseDTO = agdao.agentrequestapprovalinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("refno",refno);
			responseJSON.put("application",application);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String agentrequestinfoApprovalAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			/*requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("Narration", Narration);
			requestJSON.put("batchid", batchid);
			*/
			
			requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("Narration", Narration);
			requestJSON.put("batchid", batchid);
			requestJSON.put("remarks", remarks);
			requestJSON.put("application", application);
			requestJSON.put("waccountno", waccountno);
			requestJSON.put("actiontype", actiontype);
			
			requestJSON.put("refno", refno);
			requestJSON.put("reqapplication", reqapplication);
			requestJSON.put("trans", trans);
			
			
			
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.agentrequestinfoApprovalAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON.put("paymentrefno", paymentrefno);
			responseJSON.put("txnamt", txnamt);
			
			responseJSON.put("userid", userid);
			responseJSON.put("channel", channel);
			responseJSON.put("txntype", txntype);
			responseJSON.put("txndatetime", txndatetime);
			responseJSON.put("Narration", Narration);
			responseJSON.put("batchid", batchid);
			responseJSON.put("remarks", remarks);
	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String walletaccinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
		
		 agdao = new AgentDAO();
		responseDTO = agdao.walletaccinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String walletbalancesheet(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("fromdate",fromdate);
		
		
		
		 agdao = new AgentDAO();
		responseDTO = agdao.walletbalancesheet(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String imeidetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("imeinumber",imeinumber);
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.imeidetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("imeinumber",imeinumber);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String imeidetailsconf(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		responseJSON = new JSONObject();
		requestDTO = new RequestDTO();
		responseDTO = new ResponseDTO();
		
		
		
		requestJSON.put("imeinumber",imeinumber);
		responseJSON.put("imeinumber",imeinumber);
		
		requestJSON.put("imeistatus",imeistatus);
		responseJSON.put("imeistatus",imeistatus);
	
		result = "success";
		
	} catch (Exception e) {
		result = "fail";
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		agdao = null;
		errors = null;
	}

	return result;
}


public String imeiinserAck(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	ArrayList<String> succesmessage = null;
	try {
		
		
		responseJSON = new JSONObject();
		responseDTO = new ResponseDTO();
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("imeinumber",imeinumber);
		requestJSON.put("imeistatus",imeistatus);
		requestJSON.put("reason",reason);
		
		 agdao = new AgentDAO();
		responseDTO = agdao.imeiinserAck(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		responseJSON.put("imeistatus",imeistatus);
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			
			succesmessage = (ArrayList<String>) responseDTO.getMessages();
			for (int i = 0; i < succesmessage.size(); i++) {
				addActionMessage(succesmessage.get(i));
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
		logger.debug("Exception in binCommonScreen  ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		requestDTO = null;
		responseDTO = null;
		requestJSON = null;
		agdao = null;
		errors = null;
	}

	return result;
}

public String viewReconcileinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		agdao = new AgentDAO();
		
		responseDTO = agdao.viewReconcileinfo(requestDTO);
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String settlemetreportinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		requestJSON.put("fromdate",fromdate);
		requestJSON.put("todate",todate);
		requestJSON.put("servicecode",servicecode);
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
	    
	
	
	 agdao = new AgentDAO();
	responseDTO = agdao.settlemetreportinfo(requestDTO);
	logger.debug("Response DTO [" + responseDTO + "]");
	if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
		logger.debug("Response JSON  [" + responseJSON + "]");
		
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("trans",trans);
		
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String unsettlemetreportinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		
		
		/*requestJSON.put("fromdate",fromdate);
		requestJSON.put("todate",todate);*/
		requestJSON.put("servicecode",servicecode);
		
		requestDTO.setRequestJSON(requestJSON);
		
		//System.out.println(requestDTO.toString()+"Ranjit");
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
	    
	
	
	 agdao = new AgentDAO();
	responseDTO = agdao.unsettlemetreportinfo(requestDTO);
	logger.debug("Response DTO [" + responseDTO + "]");
	if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
		logger.debug("Response JSON  [" + responseJSON + "]");
		
		
		responseJSON.put(CevaCommonConstants.MAKER_ID,
				session.getAttribute(CevaCommonConstants.MAKER_ID));
		responseJSON.put("trans",trans);
		
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
		agdao = null;
		errors = null;
	}

	return result;
}


public String settlementrequestinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("refno",refno);		
		requestJSON.put("requesttype",requesttype);
		
		 agdao = new AgentDAO();
		responseDTO = agdao.settlementrequestinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("requesttype",requesttype);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String settlementrequestinfoAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("Narration", Narration);
			requestJSON.put("batchid", batchid);
			requestJSON.put("remarks", remarks);
			requestJSON.put("requesttype", requesttype);
			//requestJSON.put("waccountno", waccountno);
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.settlementrequestinfoAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		  
		  responseJSON.put("paymentrefno", paymentrefno);
			responseJSON.put("txnamt", txnamt);
			
			responseJSON.put("userid", userid);
			responseJSON.put("channel", channel);
			responseJSON.put("txntype", txntype);
			responseJSON.put("txndatetime", txndatetime);
			responseJSON.put("Narration", Narration);
			responseJSON.put("batchid", batchid);
			responseJSON.put("remarks", remarks);
			responseJSON.put("requesttype", requesttype);

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String settlemetrequestApproval(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		
	
		
		 agdao = new AgentDAO();
		responseDTO = agdao.settlemetrequestApproval(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("trans",trans);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String settlementrequestapprovaldetails(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("application",application);
		requestJSON.put("txntype",txntype);
		
		 agdao = new AgentDAO();
		responseDTO = agdao.settlementrequestapprovaldetails(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("application",application);
			responseJSON.put("txntype",txntype);
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String settlementrequestapprovalinfo(){
	 
	AgentDAO agdao=null;
	ArrayList<String> errors = null;
	try {
		requestJSON = new JSONObject();
		requestDTO = new RequestDTO();
		requestDTO.setRequestJSON(requestJSON);
		
		
		session = ServletActionContext.getRequest().getSession();

		requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
		requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
		
		requestJSON.put("refno",refno);
		requestJSON.put("application",application);
		requestJSON.put("txntype",txntype);
		
		
		 agdao = new AgentDAO();
		responseDTO = agdao.settlementrequestapprovalinfo(requestDTO);
		logger.debug("Response DTO [" + responseDTO + "]");
		if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
			logger.debug("Response JSON  [" + responseJSON + "]");
			
			
			responseJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			responseJSON.put("refno",refno);
			responseJSON.put("application",application);
			
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
		agdao = null;
		errors = null;
	}

	return result;
}

public String settlementrequestinfoApprovalAck() {
	 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
			
			/*requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("Narration", Narration);
			requestJSON.put("batchid", batchid);
			*/
			
			requestJSON.put("paymentrefno", paymentrefno);
			requestJSON.put("txnamt", txnamt);
			
			requestJSON.put("userid", userid);
			requestJSON.put("channel", channel);
			requestJSON.put("txntype", txntype);
			requestJSON.put("txndatetime", txndatetime);
			requestJSON.put("Narration", Narration);
			requestJSON.put("batchid", batchid);
			requestJSON.put("remarks", remarks);
			requestJSON.put("application", application);
			requestJSON.put("waccountno", waccountno);
			requestJSON.put("requesttype", requesttype);
			
			
			
			
			
			
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.settlementrequestinfoApprovalAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		 
		 /* responseJSON.put("paymentrefno", paymentrefno);
			responseJSON.put("txnamt", txnamt);
			
			responseJSON.put("userid", userid);
			responseJSON.put("channel", channel);
			responseJSON.put("txntype", txntype);
			responseJSON.put("txndatetime", txndatetime);
			responseJSON.put("Narration", Narration);
			responseJSON.put("batchid", batchid);
			responseJSON.put("remarks", remarks);
			responseJSON.put("waccountno", waccountno);
			responseJSON.put("requesttype", requesttype);*/

	   result = "success";
	  } else {
		 
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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


public String AgentRegModifyProduct() {
	logger.debug("[AgentAction][AgentRegModifyProduct] Inside AgentRegModifyProduct... ");
	AgentDAO agdao=null;
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

				requestJSON.put("customercode", customercode);
				requestJSON.put("application", application);
			
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[AgentAction][AgentRegModifyProduct]  Request DTO [" + requestDTO + "]");
		  
			agdao = new AgentDAO();
			responseDTO = agdao.AgentRegModifyProduct(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][AgentRegModifyProduct]  Response JSON++++++++  [" + responseJSON + "]");
			LocalGovernmentDAO governmentDAO = null;
			governmentDAO= new LocalGovernmentDaoImpl();
			productdt = governmentDAO.getProducts();
			System.out.println("kailash here :: "+productdt);
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
		logger.debug("[AgentAction][AgentRegModifyProduct] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
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

public String AgentRegModifyProductoconfirm() {
	 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			responseJSON.put("refno", refno);
			responseJSON.put("accountno", accountno);
			
			responseJSON.put("userid", userid);
			responseJSON.put("walletaccountno", walletaccountno);
			responseJSON.put("onboarddate", onboarddate);
			responseJSON.put("fullname", fullname);
			responseJSON.put("middlename", middlename);
			responseJSON.put("lastname", lastname);
			responseJSON.put("branchcode", branchcode);
			responseJSON.put("dateofbirth", dateofbirth);
			//requestJSON.put("waccountno", waccountno);
			
			responseJSON.put("email", email);
			responseJSON.put("telephone", telephone);
			
			responseJSON.put("gender", gender);
			responseJSON.put("superadmin", superadmin);
			responseJSON.put("product", product.split("-")[0]);
			responseJSON.put("prodesc", product.split("-")[1]);
			
			
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String AgentModifyProductAck() {
	 logger.debug("[AgentRegAck][AgentRegModifyAck] Inside AgentRegModifyAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestJSON.put("refno", refno);
			requestJSON.put("fullname", fullname);
			requestJSON.put("telephone", telephone);
			requestJSON.put("product", product);
			requestJSON.put("prodesc", prodesc);
			
			requestJSON.put("remoteip", ServletActionContext
					.getRequest().getRemoteAddr());
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentRegModifyAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.AgentModifyProductAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		 
		  responseJSON.put("refno", refno);
		  responseJSON.put("fullname", fullname);
		  responseJSON.put("telephone", telephone);
		  responseJSON.put("product", product);
		  responseJSON.put("prodesc", prodesc);
		 
		  	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

public String AgentSweepRequestDetails() {
	logger.debug("[AgentAction][AgentSweepRequestDetails] Inside AgentSweepRequestDetails... ");
	AgentDAO agdao=null;
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

				requestJSON.put("customercode", customercode);
				requestJSON.put("application", application);
			
			
			requestDTO.setRequestJSON(requestJSON);
		    logger.debug("[AgentAction][AgentSweepRequestDetails]  Request DTO [" + requestDTO + "]");
		  
			agdao = new AgentDAO();
			responseDTO = agdao.AgentSweepRequestDetails(requestDTO);
		  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
			responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
			logger.debug("[AgentAction][AgentSweepRequestDetails]  Response JSON++++++++  [" + responseJSON + "]");
			
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
		logger.debug("[AgentAction][AgentSweepRequestDetails] Exception in View details ["
				+ e.getMessage() + "]");
		addActionError("Internal error occured.");
	} finally {
		if(agdao!=null)
			agdao = null;
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

public String AgentSweepRequestDetailsconfirm() {
	 logger.debug("[AgentReg][AgentReg] Inside AgentReg ..");
	 
	
	 try {
			requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			responseJSON.put("refno", refno);
			responseJSON.put("accountno", accountno);
			
			responseJSON.put("userid", userid);
			responseJSON.put("walletaccountno", walletaccountno);
			responseJSON.put("onboarddate", onboarddate);
			responseJSON.put("fullname", fullname);
			responseJSON.put("middlename", middlename);
			responseJSON.put("lastname", lastname);
			responseJSON.put("branchcode", branchcode);
			responseJSON.put("dateofbirth", dateofbirth);
			
			responseJSON.put("email", email);
			responseJSON.put("telephone", telephone);
			
			responseJSON.put("gender", gender);
			
			responseJSON.put("stDate", stDate);
			responseJSON.put("initiondate", initiondate);
			responseJSON.put("reason", reason);
			
			
	

	   result = "success";
	  
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[AgentReg][AgentReg]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		
		 if(requestDTO!=null)
			 requestDTO = null;
		 if(responseDTO!=null)
			 responseDTO = null;
		 if(requestJSON!=null)
			 requestJSON = null;
		
	 }

	 return result;

	}

public String AgentSweepRequestDetailsAck() {
	 logger.debug("[AgentRegAck][AgentSweepRequestDetailsAck] Inside AgentSweepRequestDetailsAck ..");
	 
	 
	 AgentDAO agdao=null;
	 ArrayList<String> errors = null;
	 
	 try {
		 requestJSON = new JSONObject();
			responseJSON = new JSONObject();
			requestDTO = new RequestDTO();
			responseDTO = new ResponseDTO();
			
			session = ServletActionContext.getRequest().getSession();

			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			
			requestJSON.put("refno", refno);
			requestJSON.put("stDate", stDate);
			requestJSON.put("initiondate", initiondate);
			requestJSON.put("reason", reason);
			
			requestJSON.put("remoteip", ServletActionContext
					.getRequest().getRemoteAddr());
			
	  requestDTO.setRequestJSON(requestJSON);

	  logger.debug("[AgentRegAck][AgentSweepRequestDetailsAck] Request DTO [" + requestDTO + "]");

	  agdao = new AgentDAO();
	  responseDTO = agdao.AgentSweepRequestDetailsAck(requestDTO);
	  logger.debug("Response DTO is ["+responseDTO+"]");
	  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
		 
		  responseJSON.put("refno", refno);
		  responseJSON.put("fullname", fullname);
		  responseJSON.put("telephone", telephone);
		  responseJSON.put("product", product);
		  responseJSON.put("prodesc", prodesc);
		 
		  	

	   result = "success";
	  } else {
	   errors = (ArrayList<String>) responseDTO
	     .getErrors();
	   for (int i = 0; i < errors.size(); i++) {
	    addActionError(errors.get(i));
	   }
	   result = "fail";
	  }
	 } catch (Exception e) {
	  result = "fail";
	  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
	  addActionError("Internal error occured.");
	 } finally{
		 if(agdao!=null)
			 agdao = null;
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

}
