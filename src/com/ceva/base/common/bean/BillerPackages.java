package com.ceva.base.common.bean;

import java.io.Serializable;

public class BillerPackages implements Serializable {

	private String packageId;
	private String packageName;
	private String amount;
	private String description;
	private String status;
	private String commission;
	private String maker;
	private String makerDttm;
	private String auth;
	private String authDttm;
	private String billerId;

	public BillerPackages() {
		super();
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return "BillerPackages [packageId=" + packageId + ", packageName="
				+ packageName + ", amount=" + amount + ", description=" + description
				+ ", status=" + status + ", commission=" + commission
				+ ", maker=" + maker + ", makerDttm=" + makerDttm + ", auth="
				+ auth + ", authDttm=" + authDttm + ", billerId=" + billerId
				+ "]";
	}


}
