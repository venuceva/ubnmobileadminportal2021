package com.ceva.base.common.bean;

import java.io.Serializable;

public class Biller implements Serializable {

	private static final long serialVersionUID = 1L;
	private String billerId;
	private String billerName;
	private String catId;
	private String instId;
	private String instText;
	private String catText;
	private String accountNumber;
	private String billerDesc;
	private String supportEmail;
	private String supportContact;
	private String address;
	private String maker;
	private String makerDttm;
	private String status;
	private String auth;
	private String authDttm;
	private String refNum;

	public Biller() {
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBillerDesc() {
		return billerDesc;
	}

	public void setBillerDesc(String billerDesc) {
		this.billerDesc = billerDesc;
	}

	public String getSupportEmail() {
		return supportEmail;
	}

	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}

	public String getSupportContact() {
		return supportContact;
	}

	public void setSupportContact(String supportContact) {
		this.supportContact = supportContact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getInstText() {
		return instText;
	}

	public void setInstText(String instText) {
		this.instText = instText;
	}

	public String getCatText() {
		return catText;
	}

	public void setCatText(String catText) {
		this.catText = catText;
	}


	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(String makerDttm) {
		this.makerDttm = makerDttm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getAuthDttm() {
		return authDttm;
	}

	public void setAuthDttm(String authDttm) {
		this.authDttm = authDttm;
	}


	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	@Override
	public String toString() {
		return "Biller [billerId=" + billerId + ", billerName=" + billerName
				+ ", catId=" + catId + ", instId=" + instId + ", instText="
				+ instText + ", catText=" + catText + ", accountNumber="
				+ accountNumber + ", billerDesc=" + billerDesc
				+ ", supportEmail=" + supportEmail + ", supportContact="
				+ supportContact + ", address=" + address + ", maker=" + maker
				+ ", makerDttm=" + makerDttm + ", refNum="+refNum+", status=" + status + ", auth="
				+ auth + ", authDttm=" + authDttm + "]";
	}

}
