package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.Product;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.ProductDAO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class ProductDaoImpl implements ProductDAO {

	ResponseDTO responseDTO;


	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseDTO save(Product product) {

		String insertprod="{call products.insertProduct(?,?,?,?,?,?,?,?,?,?,?,?)}";
		logger.info("inside save..:" + product.toString());
		responseDTO=new ResponseDTO();
		Connection connection=null;
		PreparedStatement pstmt=null;
		CallableStatement cstmt=null;
		ResultSet rs;

		try {

			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			cstmt = connection.prepareCall(insertprod);
			cstmt.setString(1, product.getId()+"");
			cstmt.setString(2, product.getProductName());
			cstmt.setString(3, product.getTxnLimit()+"");
			cstmt.setString(4, product.getMonthlyTxnLimit()+"");
			cstmt.setString(5, product.getWeekTxnLimit()+"");
			cstmt.setString(6, product.getTXNCountLimit()+"");
			cstmt.setString(7, product.getStatus());
			cstmt.setString(8, product.getMaker());
			cstmt.setString(9, product.getRemoteAddr());
			cstmt.setString(10, product.getDayTxnLimit()+"");
			cstmt.setString(11, product.getAgentType()+"");
			cstmt.registerOutParameter(12, Types.VARCHAR);

			cstmt.executeUpdate();
			String result = cstmt.getString(12);
			if("SUCCESS".equals(result)){
				responseDTO.addMessages(result);
			}else{
				responseDTO.addMessages("ERROR");
				responseDTO.addError("Error Occured while inserting Product.");
				responseDTO.addError("Reason..:"+result);
			}

		} catch (Exception e) {
			logger.info("Error..:"+e.getLocalizedMessage());
			e.printStackTrace();
			responseDTO.addMessages("ERROR");
			responseDTO.addError("Error Occured..:");
			responseDTO.addError("Reason..:"+e.getLocalizedMessage());
		}finally{
			DBUtils.closeCallableStatement(cstmt);
			DBUtils.closeConnection(connection);
			product =null;
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO update(Product product) {
		return null;
	}

	@Override
	public ResponseDTO authorize(Product product) {
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
		Connection connection=null;
		PreparedStatement pstmt=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;

		try{
			String prodQry = "SELECT * FROM PRODUCT";
			connection = connection==null ? DBConnector.getConnection():connection;

			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			products= ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
			logger.info("products..:"+products);
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

	public boolean checkDuplicateFileName(String FileName){
		logger.debug("Inside Checking Duplicate File Name");
		logger.debug("File Name:::"+FileName);
		boolean found =false;
		Connection connection =null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String result = null;
		try
		{
			connection = connection == null?DBConnector.getConnection():connection;
			pstmt = connection.prepareStatement("SELECT COUNT(*)FROM FILE_UPLOAD_TBL WHERE FILENAME=?");
			pstmt.setString(1, FileName);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				if(resultSet.getInt(1)>0){
					found=true;
				}else{
					found = false;
				}

			}
		}catch(Exception e)
		{
			result = "fail";
			logger.debug("Exception in fetchFileRecords [" + e.getMessage()
					+ "]");
			found = true;
		}finally{
			DBUtils.closeResultSet(resultSet);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}
		return found;
	}

	@Override
	public JSONObject getProductsToSelectBox() {
		JSONArray products= null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			String prodQry = "Select ID, ID||'-'||PRODUCT_NAME from PRODUCT order by ID";
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
