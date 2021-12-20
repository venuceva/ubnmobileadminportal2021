package com.ceva.base.common.dao;

import net.sf.json.JSONArray;

import com.ceva.base.common.bean.BillerPackages;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;

public interface BillerPackageDAO {
	public final String qry ="SELECT B.PACKID packageId, B.DISPLAYNAME packageName, B.PACKDESC description , B.CHARGE amount, B.BILLERID billerId, B.MAKER maker, B.MAKERDTTM makerDttm, B.STATUS status, B.COMMISION commission FROM BILLER_PACKAGES B WHERE 1=1 ";
	public ResponseDTO save(RequestDTO dto);
	public ResponseDTO auth(RequestDTO dto);
	public ResponseDTO update(RequestDTO dto);
	public ResponseDTO delete(RequestDTO dto);
	public ResponseDTO getById(String Id);
	public ResponseDTO getAll(BillerPackages biller);
	public ResponseDTO getCategoryById(String billerId);
	public JSONArray getBillerPackagesToSelectBox(String catId);
	public BillerPackages get(BillerPackages biller);
}
