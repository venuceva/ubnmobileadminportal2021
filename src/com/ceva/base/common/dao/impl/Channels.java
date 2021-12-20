package com.ceva.base.common.dao.impl;

import java.io.Serializable;

public class Channels implements Serializable {

	private String instituteId;
	private String instituteName;
	private String instituteDescription;

	public Channels() {
		super();
	}

	public Channels(String instituteId, String instituteName,
			String instituteDescription) {
		super();
		this.instituteId = instituteId;
		this.instituteName = instituteName;
		this.instituteDescription = instituteDescription;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getInstituteDescription() {
		return instituteDescription;
	}

	public void setInstituteDescription(String instituteDescription) {
		this.instituteDescription = instituteDescription;
	}

	@Override
	public String toString() {
		return "Channels [instituteId=" + instituteId + ", instituteName="
				+ instituteName + ", instituteDescription="
				+ instituteDescription + "]";
	}

}
