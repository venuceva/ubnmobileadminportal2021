package com.ceva.base.common.spec.dao;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.dto.ResponseDTO;


public interface BranchDAO {
public ResponseDTO save(Map map);
public ResponseDTO update(Map map);
public ResponseDTO authorize(Map map);
public ResponseDTO delete(int id);
public ResponseDTO getById(int id);
public ResponseDTO getByName(String name);
public JSONArray getAll();
public JSONObject getBranchesToSelectBox();

}



