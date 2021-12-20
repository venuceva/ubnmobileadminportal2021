package com.ceva.base.common.bean;

public class LocalGovernment {
	private String govName;
	private String govId;
	private String state;
	
	

	public LocalGovernment() {
		super();
	}

	public LocalGovernment(String govName, String govId, String state) {
		super();
		this.govName = govName;
		this.govId = govId;
		this.state = state;
	}

	

	

	public String getGovName() {
		return govName;
	}

	public void setGovName(String govName) {
		this.govName = govName;
	}

	public String getGovId() {
		return govId;
	}

	public void setGovId(String govId) {
		this.govId = govId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LocalGovernment [govName=" + govName + ", govId=" + govId
				+ ", state=" + state + "]";
	}

}