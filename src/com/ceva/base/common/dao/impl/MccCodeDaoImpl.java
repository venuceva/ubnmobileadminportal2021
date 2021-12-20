package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.Branch;
import com.ceva.base.common.bean.Category;
import com.ceva.base.common.dao.MCCCodeDAO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.BranchDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class MccCodeDaoImpl implements MCCCodeDAO {

	ResponseDTO responseDTO;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseDTO save(Map map) {
		Category category =(Category) map.get(CevaCommonConstants.OBJECT);
		logger.info(category.toString());
		Connection conn = null;
		CallableStatement cstmt = null;
		String result = null;
		String sql = "{call MCC.INSERTMCC(?,?,?,?,?)}";
		responseDTO = new ResponseDTO();
		try{
			conn = conn == null ? DBConnector.getConnection() : conn;
			cstmt= conn.prepareCall(sql);
			cstmt.setString(1, category.getCode());
			cstmt.setString(2, category.getDescription());
			cstmt.setString(3, category.getMaker());
			cstmt.setString(4, map.get(CevaCommonConstants.IP)+"");
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getString(5);
			logger.info("Category Result From PROC :"+result);
			if("SUCCESS".equals(result)){
				responseDTO.addMessages(result);
			}else{
				responseDTO.addError(result);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			responseDTO.addError("Error :"+sqle.getLocalizedMessage());
			responseDTO.addError("Error Cause :"+sqle.getCause());
		}catch (Exception e){
			e.printStackTrace();
			responseDTO.addError("Error :"+e.getLocalizedMessage());
			responseDTO.addError("Error Cause :"+e.getCause());
		}finally{
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(conn);
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO update(Map map) {
		return null;
	}

	@Override
	public ResponseDTO authorize(Map map) {
		return null;
	}

	@Override
	public ResponseDTO delete(int id) {
		return null;
	}

	@Override
	public ResponseDTO getById(int id) {
		return null;
	}

	@Override
	public ResponseDTO getByName(String name) {
		return null;
	}

	@Override
	public JSONArray getAll() {
		JSONArray products= null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			String prodQry = "SELECT * FROM CATEGORY_MASTER";
			connection = connection==null ? DBConnector.getConnection():connection;

			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			products= ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
			logger.info("categories..:"+products);
		}catch(Exception e){
			logger.error("Error occured.."+e.getLocalizedMessage());
			e.printStackTrace();

		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}
		return products;
	}

	@Override
	public JSONObject getCategoryToSelectBox() {
		JSONArray products= null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			String prodQry = "Select CATE_CODE, CATE_CODE||'-'||CATE_DESCRIPTION from CATEGORY_MASTER order by CATE_DESCRIPTION";
			connection = connection==null ? DBConnector.getConnection():connection;
			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToSelectBox(rs);
			logger.info("products..:"+products);
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
