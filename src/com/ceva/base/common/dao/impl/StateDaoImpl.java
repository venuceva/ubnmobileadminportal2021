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
import com.ceva.base.common.bean.State;
import com.ceva.base.common.spec.dao.StateDAO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class StateDaoImpl implements StateDAO {
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<State> array = null;
		try {
			sql = "select  STATE_NAME as stateCode, STATE_NAME as stateName from STATE_MASTER";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			array = new BeanProcessor().toBeanList(rs, State.class);
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
	public State getState(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		State state = null;
		BeanProcessor bp= null;
		try {
			sql = "select  STATE_CODE as stateCode , STATE_NAME as stateName, REGION AS region from STATE_MASTER WHERE STATE_CODE = ?";
			conn = conn == null ? conn = DBConnector.getConnection() : conn;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			bp = new BeanProcessor();
			state=bp.toBean(rs, State.class);
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
		return state;
	}

	@Override
	public JSONObject getStatesToSelectBox() {
		JSONArray products= null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			String prodQry = "SELECT STATE_CODE, STATE_CODE||'-'||STATE_NAME,STATE_NAME from STATE_MASTER";
			connection = connection==null ? DBConnector.getConnection():connection;
			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToSelectBox(rs);
			logger.info("states..:"+products);
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

}
