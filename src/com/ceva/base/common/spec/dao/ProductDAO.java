package com.ceva.base.common.spec.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.bean.Product;
import com.ceva.base.common.dto.ResponseDTO;


public interface ProductDAO {
public ResponseDTO save(Product product);
public ResponseDTO update(Product product);
public ResponseDTO authorize(Product product);
public ResponseDTO delete(int id);
public ResponseDTO getById(int id);
public ResponseDTO getByName(String name);
public JSONArray getAll();
public JSONObject getProductsToSelectBox();

}



