package com.ceva.base.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class LocalGovernmentDaoImpl implements LocalGovernmentDAO {
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<LocalGovernment> array = null;
		try {
			sql = "select GOVT_NAME as govId,GOVT_NAME as govName, STATE_CODE as state from LOCAL_GOVT_MASTER";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array= new BeanProcessor().toBeanList(rs, LocalGovernment.class);
			//array = ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return array;
	}

	@Override
	public LocalGovernment getLocalGovById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		LocalGovernment government= null;
		BeanProcessor bp= null;
		try {
			sql = "select GOV_CODE as govId, GOVT_NAME as govName, STATE_CODE as state from LOCAL_GOVT_MASTER where GOV_CODE=?";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			bp = new BeanProcessor();
			government=bp.toBean(rs, LocalGovernment.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
			bp = null;
		}
		return government;
	}

	@Override
	public JSONObject getGovToSelectBox() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			String prodQry = "SELECT GOV_CODE, GOV_CODE||'-'||GOVT_NAME from LOCAL_GOVT_MASTER order by GOV_CODE";
			connection = connection==null ? DBConnector.getConnection():connection;
			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToSelectBox(rs);
		}catch(Exception e){
			logger.error("Error occured.."+e.getLocalizedMessage());
			e.printStackTrace();

		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}
		return object;
	}
	
	@Override
	public List getTerminalMake() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<LocalGovernment> array = null;
		try {
			sql = "select KEY_VALUE as govId,KEY_VALUE as govName, KEY_VALUE as state from CONFIG_DATA where KEY_GROUP='POS' AND KEY_TYPE='TERMINAL'";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array= new BeanProcessor().toBeanList(rs, LocalGovernment.class);
			//array = ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return array;
	}
	
	@Override
	public List getModel() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<LocalGovernment> array = null;
		try {
			sql = "select KEY_VALUE as govId,KEY_VALUE as govName, KEY_VALUE as state from CONFIG_DATA where KEY_GROUP='POS' AND KEY_TYPE='MODEL'";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array= new BeanProcessor().toBeanList(rs, LocalGovernment.class);
			//array = ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return array;
	}
	
	@Override
	public List getProducts() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<LocalGovernment> array = null;
		try {
			sql = "select PRD_CODE||'-'||PRD_NAME as govId,PRD_CODE as govName, PRD_CODE as state from PRODUCT where APPLICATION='Agent' and STATUS='A'";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array= new BeanProcessor().toBeanList(rs, LocalGovernment.class);
			//array = ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return array;
	}
	
	@Override
	public List getTerminalDetails() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<LocalGovernment> array = null;
		try {
			sql = "select TERMINAL_ID||'-'||SERIAL_NO||'-'||MODEL_NO||'-'||TERMINAL_MAKE as govId,SERIAL_NO as govName, SERIAL_NO as state from terminal_master where STATUS='P' and USER_ID is null and STORE_ID is null";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array= new BeanProcessor().toBeanList(rs, LocalGovernment.class);
			//array = ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Getting States..:"
					+ e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
		return array;
	}
	
	
}
