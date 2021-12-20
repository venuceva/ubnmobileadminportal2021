package com.ceva.base.common.bean;

import java.io.File;

import net.sf.json.JSONObject;

public class AccountBean {
	private String institute;

	private String operator;

	private String id;

	private String makerId;

	private String authId;

	private String makerDttm;

	private String authDttm;

	private String method;

	private JSONObject responseJSON;
	
	private JSONObject keyJson;


	private String customercode;

	private String accountid;

	private String productid;

	private String accountname;

	private String isocurrencycode;

	private String bookedbalance;

	private String clearedbalance;

	private String dormantstatus;

	private String closed;

	private String stopped;

	private String accrightsindicator;

	private String branchsortcode;

	private String customerId;

	private String newAccountData;

	private String fullname;

	private String branchcode;

	private String telephone;

	private String idnumber;

	private String customertype;

	private String customerstatus;

	private String salutation;

	private String email;

	private String langugae;

	private String multiData;

	private String multiData1;

	private String telco;

	private String isocode;

	private String accDetails;

	private String searchVal;

	private String searchDate;

	private String customerStatus;

	private String authStatus;

	private String status;

	private String status1;

	private String smsTemplateId;

	private String smsTemplate;
	
	private String mobno;
	
	private String message;
	
	private File uploadfile;
	
	private File bulkupload;

	private int totcount;
	
	private String campname;
	
	private String campdesc;
	
	private String campfields;
	
	private String campmsg;
	
	private String campstat;
	
	private String campintval;
	
	private String stdate;
	
	private String sttime;
	
	private String endtime;
	
	private String enddate;
	
	private String comment;
	
	private String product;
	private String prodesc;
	private String balance;
	private String topup;
	private String refund;
	private String apptype;
	private String gender;
	
	private String branchdetails;
	
	
	private String userid;
	private String ussdstatus;
	private String mobilestatus;
	
	private String tokenamt;
	private String pdaylimitamt;
	private String plimitchannel;
	
	private String custperdaylimit;
	private String climitchannel;
	
	private String newuserid;
	
	private String changeproduct;
	private String changenewuserid;
	
	private String smstoken;
	
	private String IDType;
	private String IDNumber;
	private String addressLine;
	private String nationality;
	private String localGovernment;
	private String state;
	private String country;
	private String accountno;
	
	private String dob;
	private String srchcriteria;
	private String mobileno;
	private String staffid;
	private String supercriteria;
	
	private String aledinagency;
	private String subaledinagency;
	
	private String agencyname;
	private String agencymobileno;
	private String agencycustid;
	private String secfalimit;
	
	private String middlename;
	private String lastname;
	private String rmcode;
	private String referenceno;
	
	private String agbranch;
	private String cluster;
	
	private String datecreation;
	private String branch;
	private String authid;
	private String makerdt;
	private String introducer;
	private String transdate;
	private String chnname;
	
	

	public String getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public String getMakerdt() {
		return makerdt;
	}

	public void setMakerdt(String makerdt) {
		this.makerdt = makerdt;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getChnname() {
		return chnname;
	}

	public void setChnname(String chnname) {
		this.chnname = chnname;
	}

	public String getAgbranch() {
		return agbranch;
	}

	public void setAgbranch(String agbranch) {
		this.agbranch = agbranch;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getRmcode() {
		return rmcode;
	}

	public void setRmcode(String rmcode) {
		this.rmcode = rmcode;
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

	public String getSecfalimit() {
		return secfalimit;
	}

	public void setSecfalimit(String secfalimit) {
		this.secfalimit = secfalimit;
	}

	public String getAgencyname() {
		return agencyname;
	}

	public void setAgencyname(String agencyname) {
		this.agencyname = agencyname;
	}

	public String getAgencymobileno() {
		return agencymobileno;
	}

	public void setAgencymobileno(String agencymobileno) {
		this.agencymobileno = agencymobileno;
	}

	public String getAgencycustid() {
		return agencycustid;
	}

	public void setAgencycustid(String agencycustid) {
		this.agencycustid = agencycustid;
	}

	public String getAledinagency() {
		return aledinagency;
	}

	public void setAledinagency(String aledinagency) {
		this.aledinagency = aledinagency;
	}

	public String getSubaledinagency() {
		return subaledinagency;
	}

	public void setSubaledinagency(String subaledinagency) {
		this.subaledinagency = subaledinagency;
	}

	public String getSupercriteria() {
		return supercriteria;
	}

	public void setSupercriteria(String supercriteria) {
		this.supercriteria = supercriteria;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getSrchcriteria() {
		return srchcriteria;
	}

	public void setSrchcriteria(String srchcriteria) {
		this.srchcriteria = srchcriteria;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getIDType() {
		return IDType;
	}

	public void setIDType(String iDType) {
		IDType = iDType;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getLocalGovernment() {
		return localGovernment;
	}

	public void setLocalGovernment(String localGovernment) {
		this.localGovernment = localGovernment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSmstoken() {
		return smstoken;
	}

	public void setSmstoken(String smstoken) {
		this.smstoken = smstoken;
	}

	public String getNewuserid() {
		return newuserid;
	}

	public void setNewuserid(String newuserid) {
		this.newuserid = newuserid;
	}

	public String getChangeproduct() {
		return changeproduct;
	}

	public void setChangeproduct(String changeproduct) {
		this.changeproduct = changeproduct;
	}

	public String getChangenewuserid() {
		return changenewuserid;
	}

	public void setChangenewuserid(String changenewuserid) {
		this.changenewuserid = changenewuserid;
	}

	public String getClimitchannel() {
		return climitchannel;
	}

	public void setClimitchannel(String climitchannel) {
		this.climitchannel = climitchannel;
	}

	public String getCustperdaylimit() {
		return custperdaylimit;
	}

	public void setCustperdaylimit(String custperdaylimit) {
		this.custperdaylimit = custperdaylimit;
	}

	public String getTokenamt() {
		return tokenamt;
	}

	public void setTokenamt(String tokenamt) {
		this.tokenamt = tokenamt;
	}

	public String getPdaylimitamt() {
		return pdaylimitamt;
	}

	public void setPdaylimitamt(String pdaylimitamt) {
		this.pdaylimitamt = pdaylimitamt;
	}

	public String getPlimitchannel() {
		return plimitchannel;
	}

	public void setPlimitchannel(String plimitchannel) {
		this.plimitchannel = plimitchannel;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUssdstatus() {
		return ussdstatus;
	}

	public void setUssdstatus(String ussdstatus) {
		this.ussdstatus = ussdstatus;
	}

	public String getMobilestatus() {
		return mobilestatus;
	}

	public void setMobilestatus(String mobilestatus) {
		this.mobilestatus = mobilestatus;
	}

	public String getBranchdetails() {
		return branchdetails;
	}

	public void setBranchdetails(String branchdetails) {
		this.branchdetails = branchdetails;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public String getTopup() {
		return topup;
	}

	public void setTopup(String topup) {
		this.topup = topup;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCampname() {
		return campname;
	}

	public void setCampname(String campname) {
		this.campname = campname;
	}

	public String getCampdesc() {
		return campdesc;
	}

	public void setCampdesc(String campdesc) {
		this.campdesc = campdesc;
	}

	public String getCampfields() {
		return campfields;
	}

	public void setCampfields(String campfields) {
		this.campfields = campfields;
	}

	public String getCampmsg() {
		return campmsg;
	}

	public void setCampmsg(String campmsg) {
		this.campmsg = campmsg;
	}

	public String getCampstat() {
		return campstat;
	}

	public void setCampstat(String campstat) {
		this.campstat = campstat;
	}

	public String getCampintval() {
		return campintval;
	}

	public void setCampintval(String campintval) {
		this.campintval = campintval;
	}

	public String getStdate() {
		return stdate;
	}

	public void setStdate(String stdate) {
		this.stdate = stdate;
	}

	public String getSttime() {
		return sttime;
	}

	public void setSttime(String sttime) {
		this.sttime = sttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	public JSONObject getKeyJson() {
		return keyJson;
	}

	public void setKeyJson(JSONObject keyJson) {
		this.keyJson = keyJson;
	}

	
	public String getMobno() {
		return mobno;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getTotcount() {
		return totcount;
	}

	public void setTotcount(int totcount) {
		this.totcount = totcount;
	}

	public File getBulkupload() {
		return bulkupload;
	}

	public void setBulkupload(File bulkupload) {
		this.bulkupload = bulkupload;
	}

	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getIsocode() {
		return isocode;
	}

	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}

	public String getTelco() {
		return telco;
	}

	public void setTelco(String telco) {
		this.telco = telco;
	}

	public String getMultiData() {
		return multiData;
	}

	public void setMultiData(String multiData) {
		this.multiData = multiData;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNewAccountData() {
		return newAccountData;
	}

	public void setNewAccountData(String newAccountData) {
		this.newAccountData = newAccountData;
	}

	public AccountBean() {
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(String makerDttm) {
		this.makerDttm = makerDttm;
	}

	public String getAuthDttm() {
		return authDttm;
	}

	public void setAuthDttm(String authDttm) {
		this.authDttm = authDttm;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCustomercode() {
		return customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getIsocurrencycode() {
		return isocurrencycode;
	}

	public void setIsocurrencycode(String isocurrencycode) {
		this.isocurrencycode = isocurrencycode;
	}

	public String getBookedbalance() {
		return bookedbalance;
	}

	public void setBookedbalance(String bookedbalance) {
		this.bookedbalance = bookedbalance;
	}

	public String getClearedbalance() {
		return clearedbalance;
	}

	public void setClearedbalance(String clearedbalance) {
		this.clearedbalance = clearedbalance;
	}

	public String getDormantstatus() {
		return dormantstatus;
	}

	public void setDormantstatus(String dormantstatus) {
		this.dormantstatus = dormantstatus;
	}

	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getStopped() {
		return stopped;
	}

	public void setStopped(String stopped) {
		this.stopped = stopped;
	}

	public String getAccrightsindicator() {
		return accrightsindicator;
	}

	public void setAccrightsindicator(String accrightsindicator) {
		this.accrightsindicator = accrightsindicator;
	}

	public String getBranchsortcode() {
		return branchsortcode;
	}

	public void setBranchsortcode(String branchsortcode) {
		this.branchsortcode = branchsortcode;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getCustomerstatus() {
		return customerstatus;
	}

	public void setCustomerstatus(String customerstatus) {
		this.customerstatus = customerstatus;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLangugae() {
		return langugae;
	}

	public void setLangugae(String langugae) {
		this.langugae = langugae;
	}

	public String getMultiData1() {
		return multiData1;
	}

	public void setMultiData1(String multiData1) {
		this.multiData1 = multiData1;
	}

	public String getAccDetails() {
		return accDetails;
	}

	public void setAccDetails(String accDetails) {
		this.accDetails = accDetails;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

}
