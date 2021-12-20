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

import com.ceva.base.common.bean.Agent;
import com.ceva.base.common.bean.Biller;
import com.ceva.base.common.bean.Branch;
import com.ceva.base.common.dao.BillerDAO;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class BillerDaoImpl implements BillerDAO {
	Logger logger = Logger.getLogger(BillerDaoImpl.class);

	public BillerDaoImpl() {
	}

	@Override
	public ResponseDTO save(RequestDTO dto) {
		logger.info("Persisting Biller");
		Biller biller =(Biller) dto.getBean();
		logger.info(biller.toString());
		Connection conn = null;
		CallableStatement cstmt = null;
		String result = null;
		String sql = "{call BILLERSPKG.SAVEBILLER(?,?,?,?,?,?,?,?,?,?,?)}";
		ResponseDTO responseDTO = new ResponseDTO();
		JSONObject requestJSON = null;
		try{
			requestJSON =dto.getRequestJSON();
			conn = conn == null ? DBConnector.getConnection() : conn;
			cstmt= conn.prepareCall(sql);
			cstmt.setString(1, biller.getBillerName());
			cstmt.setString(2, biller.getInstId());
			cstmt.setString(3, biller.getCatId());
			cstmt.setString(4, biller.getAccountNumber());
			cstmt.setString(5, biller.getAddress());
			cstmt.setString(6, biller.getSupportContact());
			cstmt.setString(7, biller.getSupportEmail());
			cstmt.setString(8, biller.getBillerDesc());
			cstmt.setString(9, biller.getMaker());
			cstmt.setString(10, requestJSON.getString(CevaCommonConstants.IP));
			cstmt.registerOutParameter(11, Types.VARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getString(11);
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
		logger.info("Persisting Biller");
		Biller biller =(Biller) dto.getBean();
		logger.info(biller.toString());
		Connection conn = null;
		CallableStatement cstmt = null;
		String result = null;
		String sql = "{call BILLERSPKG.authBiller(?,?,?,?,?)}";
		ResponseDTO responseDTO = new ResponseDTO();
		JSONObject requestJSON = null;
		try{
			requestJSON =dto.getRequestJSON();
			conn = conn == null ? DBConnector.getConnection() : conn;
			cstmt= conn.prepareCall(sql);
			cstmt.setString(1, biller.getRefNum());
			cstmt.setString(2, biller.getStatus());
			cstmt.setString(3, biller.getAuth());
			cstmt.setString(4, requestJSON.getString(CevaCommonConstants.IP));
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getString(5);
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
	public ResponseDTO getAll(String catId) {
		logger.info("getAll Biller for category..:" + catId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		String sql = "SELECT B.BILLERID billerId, B.BILLERNAME billerName, B.BILLERDESC billerDesc, B.ACCOUNTNUMBER accountNumber,SUPPORTEMAIL supportEmail, SUPPORTCONTACT supportContact, (SELECT BC.CAT_NAME FROM BILLER_CATEGORY BC WHERE BC.CAT_ID=B.CATID) AS catText,B.CATID as catId, (SELECT SI.INSTITUTION_NAME FROM SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID=B.INSTID) AS instText, B.INSTID as instid, B.STATUS status FROM BILLERS B WHERE B.CATID = ?";
		BeanProcessor processor = new BeanProcessor();
		HashMap<String, Object> data = new HashMap<String, Object>();
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			conn = conn == null ? DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catId);
			rs = pstmt.executeQuery();
			List<Biller> billers = processor.toBeanList(rs, Biller.class);
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
	public JSONArray getBillersToSelectBox(String catId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Biller get(Biller biller) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.info(biller.toString());
		String sql = prepareQuery(biller);
		logger.info(sql);
		BeanProcessor processor = null;
		try {
			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				processor = new BeanProcessor();
				biller = processor.toBean(rs, Biller.class);
			}
			if (biller != null) {
				logger.info(biller.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error" + e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			sql = null;
			processor = null;
		}
		return biller;
	}

	private String prepareQuery(Biller biller){
		StringBuilder sql =null;

		try {
				 sql = new StringBuilder(qry);

			if (biller.getBillerId() != null && biller.getBillerId().length()>0) {
				sql.append(" AND B.BILLERID = '" + biller.getBillerId()+ "'");
			}
			if (biller.getRefNum() != null && biller.getRefNum().length() > 0) {
				sql.append(" AND B.REF_NUM = '" + biller.getRefNum() + "'");
			}
			if (biller.getSupportContact() != null && biller.getSupportContact().length() > 0) {
				sql.append(" AND B.SUPPORTCONTACT = '" + biller.getSupportContact() + "'");
			}
			if (biller.getAccountNumber() != null && biller.getAccountNumber().length() > 0) {
				sql.append(" AND B.ACCOUNTNUMBER = '" + biller.getAccountNumber() + "'");
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
