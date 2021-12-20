package com.ceva.base.common.spec.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.common.bean.Agent;
import com.ceva.base.common.dto.ResponseDTO;

public interface SuperAgentDAO {

	public static final String qry="SELECT ACCOUNTNUMBER accountNumbers, ACCOUNTNAME accountName, ACCTCURRCODE acctCurrCode, BRANCHCODE branchCode, EMAIL email, MOBILE mobile, SCHEMEDESC schemeDesc, SCHEMETYPE schemeType, SUBPRODUCTCODE subProductCode , MANAGERNAME managerName, ADDRESSLINE1 addressLine1, ADDRESSLINE2 addressLine2, CITY city, LOCALGOVERNMENT localGovernment , MOBILENUMBER mobileNumber , COUNTRY country , STATE state , MERCHANTTYPE merchantType , PRODUCTTYPE productType , to_char(DOB, 'dd/mm/yyyy') dob , GENDER gender, ID_TYPE IDType, ID_NUMBER IDNumber, TELCO_TYPE telco , TELEPHONE_NUM1 telephoneNumber1, TELEPHONE_NUM2 telephoneNumber2, NATIONALITY nationality, LATITUDE latitude, LONGITUDE langitude, CBNAGENTID CBNAgentId, STATUS status, REF_NUM refNum from SUPER_AGENT where 1=1 ";
	public static final String tmp_qry="SELECT ACCOUNTNUMBER accountNumbers, ACCOUNTNAME accountName, ACCTCURRCODE acctCurrCode, BRANCHCODE branchCode, EMAIL email, MOBILE mobile, SCHEMEDESC schemeDesc, SCHEMETYPE schemeType, SUBPRODUCTCODE subProductCode , MANAGERNAME managerName, ADDRESSLINE1 addressLine1, ADDRESSLINE2 addressLine2, CITY city, LOCALGOVERNMENT localGovernment , MOBILENUMBER mobileNumber , COUNTRY country , STATE state , MERCHANTTYPE merchantType , PRODUCTTYPE productType , to_char(DOB, 'dd/mm/yyyy') dob , GENDER gender, ID_TYPE IDType, ID_NUMBER IDNumber, TELCO_TYPE telco , TELEPHONE_NUM1 telephoneNumber1, TELEPHONE_NUM2 telephoneNumber2, NATIONALITY nationality, LATITUDE latitude, LONGITUDE langitude, CBNAGENTID CBNAgentId, STATUS status from SUPER_AGENT_TEMP where 1=1 ";
	public ResponseDTO save(Map map);
	public ResponseDTO update(Map map);
	public ResponseDTO authorize(Map map);
	public ResponseDTO delete(Map map);
	public ResponseDTO getById(int id);
	public ResponseDTO getByName(String name);
	public List<Agent> getAll();
	public JSONObject getAgentToSelectBox();

}
