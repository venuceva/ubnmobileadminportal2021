package com.ceva.base.common.spec.dao;


import java.util.List;

import net.sf.json.JSONObject;

import com.ceva.base.common.bean.State;

public interface StateDAO {

	public List getAll();
	public State getState(String id);
	public JSONObject getStatesToSelectBox();

}
