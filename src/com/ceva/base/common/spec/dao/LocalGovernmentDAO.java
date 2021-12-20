package com.ceva.base.common.spec.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.ceva.base.common.bean.LocalGovernment;

public interface LocalGovernmentDAO {

	public List getAll();

	public LocalGovernment getLocalGovById(String id);
	public JSONObject getGovToSelectBox();
	
	public List getTerminalMake();
	public List getModel();
	public List getProducts();
	public List getTerminalDetails();

}
