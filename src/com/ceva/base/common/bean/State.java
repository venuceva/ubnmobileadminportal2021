package com.ceva.base.common.bean;

public class State {
	private String stateCode;
	private String stateName;
	private String region;

	public State() {
		super();
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "State [stateCode=" + stateCode + ", stateName=" + stateName
				+ ", region=" + region + "]";
	}

}
