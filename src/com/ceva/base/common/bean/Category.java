package com.ceva.base.common.bean;

import java.io.Serializable;

public class Category implements Serializable {

	private String code;
	private String description;
	private String maker;
	private String makerDttm;


	public Category() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
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

	@Override
	public String toString() {
		return "Category [code=" + code + ", description=" + description
				+ ", maker=" + maker + ", makerDttm=" + makerDttm + "]";
	}

}
