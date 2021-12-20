package com.ceva.base.reports.statement.model;



public class Accountstatementdetails {

	private String postdate;
	private String transactiontype;
	private String transactiondetails;
	private String referencenumber;
	private String valuedate; 
	private String cr;
	private String dr;
	private String balance;

	public String getPosdate() {
		return postdate;
	}

	public void setPosdate(String postdate) {
		this.postdate = postdate;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getTransactiondetails() {
		return transactiondetails;
	}

	public void setTransactiondetails(String transactiondetails) {
		this.transactiondetails = transactiondetails;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}


	public String getValuedate() {
		return valuedate;
	}

	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}


	public String getCredit() {
		return cr;
	}

	public void setCredit(String cr) {
		this.cr = cr;
	}
	public String getDebit() {
		return dr;
	}

	public void setDebit(String dr) {
		this.dr = dr;
	}


	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	
	@Override
	public String toString() {
		return "Accountstatementdetails [postdate=" + postdate + ", transactiontype=" + transactiontype
				+ ", transactiondetails=" + transactiondetails + ", referencenumber=" + referencenumber + ", valuedate="
				+ valuedate + ", cr=" + cr + ", dr=" + dr + ", balance=" + balance + "]";
	}

	
}
