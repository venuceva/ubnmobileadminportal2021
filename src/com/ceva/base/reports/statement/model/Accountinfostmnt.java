package com.ceva.base.reports.statement.model;


import java.time.LocalDate;
import java.util.List;

public class Accountinfostmnt {

	private String cusname;
	private String accountno;
	private String branchname;
	private String accounttype;
	private String currency;
	
	private String printdate; 
	private String startdate;
	private String enddate;

	private String name;
	private String address;
	
	private String address1;
	private String address2;
	
	private String openingbalnce;
	private String totaldebit;
	private String totalcredit;
	private String closingbalance;

	private String drcount;
	private String crcount;
	private String totbalamt;

	private String selecteddate;

	private String totalcommission;
	
	List<Accountstatementdetails> list;
	
	public String getTotalcommission() {
		return totalcommission;
	}

	public void setTotalcommission(String totalcommission) {
		this.totalcommission = totalcommission;
	}

	public String getSelecteddate() {
		return selecteddate;
	}

	public void setSelecteddate(String selecteddate) {
		this.selecteddate = selecteddate;
	}

	public String getOpeningbalnce() {
		return openingbalnce;
	}

	public void setOpeningbalnce(String openingbalnce) {
		this.openingbalnce = openingbalnce;
	}

	public String getDrcount() {
		return drcount;
	}

	public void setDrcount(String drcount) {
		this.drcount = drcount;
	}

	public String getCrcount() {
		return crcount;
	}

	public void setCrcount(String crcount) {
		this.crcount = crcount;
	}

	public String getTotbalamt() {
		return totbalamt;
	}

	public void setTotbalamt(String totbalamt) {
		this.totbalamt = totbalamt;
	}
	

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public List<Accountstatementdetails> getList() {
		return list;
	}

	public void setList(List<Accountstatementdetails> list) {
		this.list = list;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getTotaldebit() {
		return totaldebit;
	}

	public void setTotaldebit(String totaldebit) {
		this.totaldebit = totaldebit;
	}

	public String getTotalcredit() {
		return totalcredit;
	}

	public void setTotalcredit(String totalcredit) {
		this.totalcredit = totalcredit;
	}

	public String getClosingbalance() {
		return closingbalance;
	}

	public void setClosingbalance(String closingbalance) {
		this.closingbalance = closingbalance;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getAccountype() {
		return accounttype;
	}

	public void setAccountype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrintdate() {
		return printdate;
	}

	public void setPrintdate(String printdate) {
		this.printdate = printdate;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@Override
	public String toString() {
		return "Accountinfostmnt [cusname=" + cusname + ", accountno=" + accountno + ", branchname=" + branchname
				+ ", accounttype=" + accounttype + ", currency=" + currency + ", printdate=" + printdate
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", name=" + name + ", address=" + address
				+ ", address1=" + address1 + ", address2=" + address2 + ", openingbalnce=" + openingbalnce
				+ ", totaldebit=" + totaldebit + ", totalcredit=" + totalcredit + ", closingbalance=" + closingbalance
				+ ", drcount=" + drcount + ", crcount=" + crcount + ", totbalamt=" + totbalamt + ", selecteddate="
				+ selecteddate + ", totalcommission=" + totalcommission + ", list=" + list + "]";
	}
	
	
	
}