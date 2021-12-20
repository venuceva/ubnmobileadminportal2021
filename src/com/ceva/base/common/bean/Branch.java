package com.ceva.base.common.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Branch implements Serializable {

	private String code;
	private String branchName;
	private String state;
	private String status;
	private String maker;

	public Branch() {
		super();
	}

	public Branch(String str) {
		//String[] data = str.split("$%$");
		String[] data = StringUtils.split(str, "$%$");
		this.code=data[0];
		this.branchName = data[1];
		this.state = data[2];
		this.status = data[3];

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	@Override
	public String toString() {
		return "Branch [code=" + code + ", branchName=" + branchName
				+ ", state=" + state + ", status=" + status + ", maker="
				+ maker + "]";
	}

}
