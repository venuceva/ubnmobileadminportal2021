package com.ceva.base.common.bean;

import net.sf.json.JSONObject;

public class AccountPropertiesBean {

	private String menuname = null;
	private String dispname = null;
	private String menucode = null;
	private String menulevel = null;
	private String seltext = null;
	private String selval = null;
	private String acType = null;
	private String channelID = null;
	private String customerType = null;
	private String customerSubType = null;
	private String businessUnit = null;
	private String custSegmentID = null;
	private String identificationType = null;
	private String identificationNo = null;
	private String branchCode = null;
	private String isCustSWIFTEnabled = null;
	private String subProductID = null;
	private String isChequeBookReq = null;
	private String chequeBookType = null;
	private String isCreditCardReq = null;
	private String isdebitCardReq = null;
	private String isEStatementReq = null;
	private String isInternetBankingReq = null;
	private String isBancassuranceReq = null;
	private String isSimpleBankingReq = null;
	private String restrictionFlag = null;
	private String accountId = null;
	private String productCode = null;
	private String action = null;
	private String description = null;
	private JSONObject responseJSON;
	private String multiData;
	private String multiData1;
	private String subproductData;
	private String limitData;
	private String productid;
	private String prddesc;
	private String institute;
	
	
	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getSubproductData() {
		return subproductData;
	}

	public void setSubproductData(String subproductData) {
		this.subproductData = subproductData;
	}

	public String getLimitData() {
		return limitData;
	}

	public void setLimitData(String limitData) {
		this.limitData = limitData;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getPrddesc() {
		return prddesc;
	}

	public void setPrddesc(String prddesc) {
		this.prddesc = prddesc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the acType
	 */
	public String getAcType() {
		return acType;
	}

	/**
	 * @param acType
	 *            the acType to set
	 */
	public void setAcType(String acType) {
		this.acType = acType;
	}

	/**
	 * @return the channelID
	 */
	public String getChannelID() {
		return channelID;
	}

	/**
	 * @param channelID
	 *            the channelID to set
	 */
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the customerSubType
	 */
	public String getCustomerSubType() {
		return customerSubType;
	}

	/**
	 * @param customerSubType
	 *            the customerSubType to set
	 */
	public void setCustomerSubType(String customerSubType) {
		this.customerSubType = customerSubType;
	}

	/**
	 * @return the businessUnit
	 */
	public String getBusinessUnit() {
		return businessUnit;
	}

	/**
	 * @param businessUnit
	 *            the businessUnit to set
	 */
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	/**
	 * @return the custSegmentID
	 */
	public String getCustSegmentID() {
		return custSegmentID;
	}

	/**
	 * @param custSegmentID
	 *            the custSegmentID to set
	 */
	public void setCustSegmentID(String custSegmentID) {
		this.custSegmentID = custSegmentID;
	}

	/**
	 * @return the identificationType
	 */
	public String getIdentificationType() {
		return identificationType;
	}

	/**
	 * @param identificationType
	 *            the identificationType to set
	 */
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	/**
	 * @return the identificationNo
	 */
	public String getIdentificationNo() {
		return identificationNo;
	}

	/**
	 * @param identificationNo
	 *            the identificationNo to set
	 */
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the isCustSWIFTEnabled
	 */
	public String getIsCustSWIFTEnabled() {
		return isCustSWIFTEnabled;
	}

	/**
	 * @param isCustSWIFTEnabled
	 *            the isCustSWIFTEnabled to set
	 */
	public void setIsCustSWIFTEnabled(String isCustSWIFTEnabled) {
		this.isCustSWIFTEnabled = isCustSWIFTEnabled;
	}

	/**
	 * @return the subProductID
	 */
	public String getSubProductID() {
		return subProductID;
	}

	/**
	 * @param subProductID
	 *            the subProductID to set
	 */
	public void setSubProductID(String subProductID) {
		this.subProductID = subProductID;
	}

	/**
	 * @return the isChequeBookReq
	 */
	public String getIsChequeBookReq() {
		return isChequeBookReq;
	}

	/**
	 * @param isChequeBookReq
	 *            the isChequeBookReq to set
	 */
	public void setIsChequeBookReq(String isChequeBookReq) {
		this.isChequeBookReq = isChequeBookReq;
	}

	/**
	 * @return the chequeBookType
	 */
	public String getChequeBookType() {
		return chequeBookType;
	}

	/**
	 * @param chequeBookType
	 *            the chequeBookType to set
	 */
	public void setChequeBookType(String chequeBookType) {
		this.chequeBookType = chequeBookType;
	}

	/**
	 * @return the isCreditCardReq
	 */
	public String getIsCreditCardReq() {
		return isCreditCardReq;
	}

	/**
	 * @param isCreditCardReq
	 *            the isCreditCardReq to set
	 */
	public void setIsCreditCardReq(String isCreditCardReq) {
		this.isCreditCardReq = isCreditCardReq;
	}

	/**
	 * @return the isdebitCardReq
	 */
	public String getIsdebitCardReq() {
		return isdebitCardReq;
	}

	/**
	 * @param isdebitCardReq
	 *            the isdebitCardReq to set
	 */
	public void setIsdebitCardReq(String isdebitCardReq) {
		this.isdebitCardReq = isdebitCardReq;
	}

	/**
	 * @return the isEStatementReq
	 */
	public String getIsEStatementReq() {
		return isEStatementReq;
	}

	/**
	 * @param isEStatementReq
	 *            the isEStatementReq to set
	 */
	public void setIsEStatementReq(String isEStatementReq) {
		this.isEStatementReq = isEStatementReq;
	}

	/**
	 * @return the isInternetBankingReq
	 */
	public String getIsInternetBankingReq() {
		return isInternetBankingReq;
	}

	/**
	 * @param isInternetBankingReq
	 *            the isInternetBankingReq to set
	 */
	public void setIsInternetBankingReq(String isInternetBankingReq) {
		this.isInternetBankingReq = isInternetBankingReq;
	}

	/**
	 * @return the isBancassuranceReq
	 */
	public String getIsBancassuranceReq() {
		return isBancassuranceReq;
	}

	/**
	 * @param isBancassuranceReq
	 *            the isBancassuranceReq to set
	 */
	public void setIsBancassuranceReq(String isBancassuranceReq) {
		this.isBancassuranceReq = isBancassuranceReq;
	}

	/**
	 * @return the isSimpleBankingReq
	 */
	public String getIsSimpleBankingReq() {
		return isSimpleBankingReq;
	}

	/**
	 * @param isSimpleBankingReq
	 *            the isSimpleBankingReq to set
	 */
	public void setIsSimpleBankingReq(String isSimpleBankingReq) {
		this.isSimpleBankingReq = isSimpleBankingReq;
	}

	/**
	 * @return the restrictionFlag
	 */
	public String getRestrictionFlag() {
		return restrictionFlag;
	}

	/**
	 * @param restrictionFlag
	 *            the restrictionFlag to set
	 */
	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the responseJSON
	 */
	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	/**
	 * @param responseJSON
	 *            the responseJSON to set
	 */
	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getDispname() {
		return dispname;
	}

	public void setDispname(String dispname) {
		this.dispname = dispname;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public String getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}
	public String getSeltext() {
		return seltext;
	}

	public void setSeltext(String seltext) {
		this.seltext = seltext;
	}
	public String getSelval() {
		return selval;
	}

	public void setSelval(String selval) {
		this.selval = selval;
	}
	public String getMultiData() {
		return multiData;
	}

	public void setMultiData(String multiData) {
		this.multiData = multiData;
	}

	public String getMultiData1() {
		return multiData1;
	}

	public void setMultiData1(String multiData1) {
		this.multiData1 = multiData1;
	}



}
