package com.ceva.base.common.bean;

public class BillerTransaction {

	private String txnId;
	/*
	 * channel describes POS(1), ANDROID-MOBILE(2), IPHONE(3), WEB(4), ETC
	 */
	private String channel;
	private String instId;
	private String instText;
	private String operator;
	private String operatorText;
	private String billerId;
	private String dateTime;
	private String status;
	private String amount;
	private String makerId;
	private String responseCode;

	public BillerTransaction() {

	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getInstText() {
		return instText;
	}

	public void setInstText(String instText) {
		this.instText = instText;
	}

	public String getOperatorText() {
		return operatorText;
	}

	public void setOperatorText(String operatorText) {
		this.operatorText = operatorText;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	@Override
	public String toString() {
		return "BillerTransaction [txnId=" + txnId + ", channel=" + channel
				+ ", instId=" + instId + ", instText=" + instText
				+ ", operator=" + operator + ", operatorText=" + operatorText
				+ ", billerId=" + billerId + ", dateTime=" + dateTime
				+ ", status=" + status + ", amount=" + amount + ", makerId="
				+ makerId + ", responseCode=" + responseCode + "]";
	}

}
