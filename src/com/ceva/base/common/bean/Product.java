package com.ceva.base.common.bean;

import java.io.Serializable;

public class Product implements Serializable {

	private int id;
	private String productName;
	private String maker;
	private String makerDttm;
	private String checker;
	private String cherkerDttm;
	private String txnLimit;
	private String monthlyTxnLimit;
	private String dayTxnLimit;
	private String weekTxnLimit;
	private String TXNCountLimit;
	private String status;
	private String remoteAddr;
	private String  agentType;



	public Product() {
		super();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCherkerDttm() {
		return cherkerDttm;
	}

	public void setCherkerDttm(String cherkerDttm) {
		this.cherkerDttm = cherkerDttm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}


	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}


	public String getTxnLimit() {
		return txnLimit;
	}


	public void setTxnLimit(String txnLimit) {
		this.txnLimit = txnLimit;
	}


	public String getMonthlyTxnLimit() {
		return monthlyTxnLimit;
	}


	public void setMonthlyTxnLimit(String monthlyTxnLimit) {
		this.monthlyTxnLimit = monthlyTxnLimit;
	}


	public String getDayTxnLimit() {
		return dayTxnLimit;
	}


	public void setDayTxnLimit(String dayTxnLimit) {
		this.dayTxnLimit = dayTxnLimit;
	}


	public String getWeekTxnLimit() {
		return weekTxnLimit;
	}


	public void setWeekTxnLimit(String weekTxnLimit) {
		this.weekTxnLimit = weekTxnLimit;
	}


	public String getTXNCountLimit() {
		return TXNCountLimit;
	}


	public void setTXNCountLimit(String tXNCountLimit) {
		TXNCountLimit = tXNCountLimit;
	}


	public String getAgentType() {
		return agentType;
	}


	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName
				+ ", maker=" + maker + ", makerDttm=" + makerDttm
				+ ", checker=" + checker + ", cherkerDttm=" + cherkerDttm
				+ ", txnLimit=" + txnLimit + ", monthlyTxnLimit="
				+ monthlyTxnLimit + ", dayTxnLimit=" + dayTxnLimit
				+ ", weekTxnLimit=" + weekTxnLimit + ", TXNCountLimit="
				+ TXNCountLimit + ", status=" + status + ", remoteAddr="
				+ remoteAddr + ", agentType=" + agentType + "]";
	}

}
