package com.ceva.base.common.dao;

import net.sf.json.JSONArray;

import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;

public interface BillerDAO {
	public final String qry ="SELECT B.BILLERID billerId, B.BILLERNAME billerName, B.CATID catId,(SELECT BC.CAT_NAME FROM BILLER_CATEGORY BC WHERE BC.CAT_ID=B.CATID) catText, B.INSTID instId, (SELECT SI.INSTITUTION_NAME  FROM SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = B.INSTID ) instText, B.ACCOUNTNUMBER accountNumber, B.BILLERDESC billerDesc, B.SUPPORTCONTACT supportContact, B.SUPPORTEMAIL supportEmail, B.ADDRESS address, B.MAKER maker, B.MAKERDTTM makerDttm, B.STATUS status, B.REF_NUM refNum FROM BILLERS B WHERE 1=1 ";
	public ResponseDTO save(RequestDTO dto);
	public ResponseDTO auth(RequestDTO dto);
	public ResponseDTO update(RequestDTO dto);
	public ResponseDTO delete(RequestDTO dto);
	public ResponseDTO getById(String Id);
	public ResponseDTO getAll(String catId);
	public ResponseDTO getCategoryById(String billerId);
	public JSONArray getBillersToSelectBox(String catId);
	public Biller get(Biller biller);
}
