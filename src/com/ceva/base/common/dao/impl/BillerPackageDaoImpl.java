package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.agencybanking.action.BillerPackageAction;
import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.bean.BillerPackages;
import com.ceva.base.common.dao.BillerPackageDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class BillerPackageDaoImpl implements BillerPackageDAO{
	Logger logger = Logger.getLogger(BillerPackageDaoImpl.class);

	public BillerPackageDaoImpl() {
	}

	@Override
	public ResponseDTO save(RequestDTO dto) {
		logger.info("Persisting Biller");
		BillerPackages biller =(BillerPackages) dto.getBean();
		logger.info(biller.toString());
		Connection conn = null;
		CallableStatement cstmt = null;
		String result = null;
		String sql = "{call BILLERSPKG.SAVEBILLERPACKAGE(?,?,?,?,?,?,?,?)}";
		ResponseDTO responseDTO = new ResponseDTO();
		JSONObject requestJSON = null;
		try{
			requestJSON =dto.getRequestJSON();
			conn = conn == null ? DBConnector.getConnection() : conn;
			cstmt= conn.prepareCall(sql);
			cstmt.setString(1, biller.getPackageName());
			cstmt.setString(2, biller.getDescription());
			cstmt.setString(3, biller.getAmount());
			cstmt.setString(4, biller.getCommission());
			cstmt.setString(5, biller.getBillerId());
			cstmt.setString(6, biller.getMaker());
			cstmt.setString(7, requestJSON.getString(CevaCommonConstants.IP));
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getString(8);
			logger.info("Biller Result From PROC :"+result);
			if("SUCCESS".equals(result)){
				responseDTO.addMessages(result);
			}else{
				responseDTO.addError(result);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("Error while creating biller, cause :"+e.getLocalizedMessage());
			responseDTO.addError("Error Cause :"+e.getCause());
		}finally{
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(conn);
			requestJSON= null;
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO auth(RequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseDTO update(RequestDTO dto) {
		return null;
	}

	@Override
	public ResponseDTO delete(RequestDTO dto) {
		return null;
	}

	@Override
	public ResponseDTO getById(String Id) {
		return null;
	}

	@Override
	public ResponseDTO getAll(BillerPackages billerPackage) {
		logger.info("getAll Packages Biller for ..:" + billerPackage.getBillerId());
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prepareQuery(billerPackage);
		BeanProcessor processor = new BeanProcessor();
		HashMap<String, Object> data = new HashMap<String, Object>();
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			conn = conn == null ? DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<BillerPackages> billers = processor.toBeanList(rs, BillerPackages.class);
			data.put(CevaCommonConstants.OBJECT, billers);
			logger.info("data :" + data.toString());
			responseDTO.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while creating biller, cause :"
					+ e.getLocalizedMessage());
			responseDTO.addError("Error Cause :" + e.getCause());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO getCategoryById(String billerId) {
		return null;
	}

	@Override
	public JSONArray getBillerPackagesToSelectBox(String catId) {
		return null;
	}

	@Override
	public BillerPackages get(BillerPackages biller) {
		return null;
	}

	private String prepareQuery(BillerPackages biller){
		StringBuilder sql =null;

		try {
				 sql = new StringBuilder(qry);

			if (biller.getBillerId() != null && biller.getBillerId().length()>0) {
				sql.append(" AND B.BILLERID = '" + biller.getBillerId()+ "'");
			}
			if (biller.getPackageId()!= null && biller.getPackageId().length()>0) {
				sql.append(" AND B.PACKID = '" + biller.getPackageId()+ "'");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error" + e.getLocalizedMessage());
		}finally{
			biller = null;
		}
		return sql.toString();
	}

}
