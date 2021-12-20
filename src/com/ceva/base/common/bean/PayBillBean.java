package com.ceva.base.common.bean;


public class PayBillBean {
	private String institute;

	private String instituteText;

	private String operator;

	private String operatorText;

	private String billerType;

	private String billerTypeDescription;

	private String fixedamountcheckval;

	private String amount;

	private String bfubCreditAccount;

	private String status;

	private String selectBoxData;

	private String id;

	private String makerId;

	private String authId;

	private String makerDttm;

	private String authDttm;

	private String method;

	private String billerIdType;

	private String billerIdDescription;

	private String billerIdPrefix;

	private String refNum;


	public PayBillBean() {
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

	public String getBillerType() {
		return billerType;
	}

	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	public String getBillerTypeDescription() {
		return billerTypeDescription;
	}

	public void setBillerTypeDescription(String billerTypeDescription) {
		this.billerTypeDescription = billerTypeDescription;
	}

	public String getFixedamountcheckval() {
		return fixedamountcheckval;
	}

	public void setFixedamountcheckval(String fixedamountcheckval) {
		this.fixedamountcheckval = fixedamountcheckval;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBfubCreditAccount() {
		return bfubCreditAccount;
	}

	public void setBfubCreditAccount(String bfubCreditAccount) {
		this.bfubCreditAccount = bfubCreditAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSelectBoxData() {
		return selectBoxData;
	}

	public void setSelectBoxData(String selectBoxData) {
		this.selectBoxData = selectBoxData;
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

	public String getBillerIdType() {
		return billerIdType;
	}

	public void setBillerIdType(String billerIdType) {
		this.billerIdType = billerIdType;
	}

	public String getBillerIdDescription() {
		return billerIdDescription;
	}

	public void setBillerIdDescription(String billerIdDescription) {
		this.billerIdDescription = billerIdDescription;
	}

	public String getInstituteText() {
		return instituteText;
	}

	public void setInstituteText(String instituteText) {
		this.instituteText = instituteText;
	}

	public String getOperatorText() {
		return operatorText;
	}

	public void setOperatorText(String operatorText) {
		this.operatorText = operatorText;
	}


	public String getBillerIdPrefix() {
		return billerIdPrefix;
	}

	public void setBillerIdPrefix(String billerIdPrefix) {
		this.billerIdPrefix = billerIdPrefix;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	@Override
	public String toString() {
		return "PayBillBean [institute=" + institute + ", instituteText="
				+ instituteText + ", operator=" + operator + ", operatorText="
				+ operatorText + ", billerType=" + billerType
				+ ", billerTypeDescription=" + billerTypeDescription
				+ ", fixedamountcheckval=" + fixedamountcheckval + ", amount="
				+ amount + ", bfubCreditAccount=" + bfubCreditAccount
				+ ", status=" + status + ", selectBoxData=" + selectBoxData
				+ ", id=" + id + ", makerId=" + makerId + ", authId=" + authId
				+ ", makerDttm=" + makerDttm + ", authDttm=" + authDttm
				+ ", method=" + method + ", billerIdType=" + billerIdType
				+ ", billerIdPrefix=" + billerIdPrefix +"]";
	}

}
