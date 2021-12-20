package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.Branch;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.BranchDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.DBUtils;

public class BranchDaoImpl implements BranchDAO {

	ResponseDTO responseDTO;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseDTO save(Map map) {
		Branch branch =(Branch) map.get(CevaCommonConstants.OBJECT);
		logger.info(branch.toString());
		Connection conn = null;
		CallableStatement cstmt = null;
		String result = null;
		String sql = "{call branchs.insertBranch(?,?,?,?,?,?,?)}";
		responseDTO = new ResponseDTO();
		try{
			conn = conn == null ? DBConnector.getConnection() : conn;
			cstmt= conn.prepareCall(sql);
			cstmt.setString(1, branch.getCode());
			cstmt.setString(2, branch.getBranchName());
			cstmt.setString(3, branch.getState());
			cstmt.setString(4, branch.getStatus());
			cstmt.setString(5, map.get(CevaCommonConstants.MAKER_ID)+"");
			cstmt.setString(6, map.get(CevaCommonConstants.IP)+"");
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getString(7);
			logger.info("Branch Result From PROC :"+result);
			if("SUCCESS".equals(result)){
				responseDTO.addMessages(result);
			}else{
				responseDTO.addError(result);
			}
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
			String prodQry = "SELECT * FROM BRANCH_MASTER";
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
	@Override
	public JSONObject getBranchesToSelectBox() {
		JSONArray products= null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			JSONObject jsonlmt = new JSONObject();
			/*String prodQry = "SELECT GOV_CODE, GOV_CODE||'-'||GOVT_NAME from LOCAL_GOVT_MASTER order by GOV_CODE";
			//String prodQry = "Select distinct BRANCH_CODE,BRANCH_CODE||'-'||BRANCH_NAME from BRANCH_MASTER order by to_number(BRANCH_CODE)";
			connection = connection==null ? DBConnector.getConnection():connection;
			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToSelectBox(rs);*/
			
			JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.branchList());
			JSONArray jsonarray =  JSONArray.fromObject(json2.get("branchList"));
			Iterator iterator = jsonarray.iterator();
		     while (iterator.hasNext()) {
		    	   JSONObject jsonobj=(JSONObject)iterator.next();
		    	   jsonlmt.put((jsonobj).get("branchCode")+"-"+(jsonobj).get("branchName"),(jsonobj).get("branchCode")+"-"+(jsonobj).get("branchName"));
					
		     }
		    // System.out.println("kailash here ::: "+jsonlmt);
		     object=jsonlmt;
			
			
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
	
	
	public static void main(String args[]){
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		int count[] = null;
		try{
		
		JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.branchList());
		JSONArray jsonarray =  JSONArray.fromObject(json2.get("branchList"));
		Iterator iterator = jsonarray.iterator();
		connection = connection == null?DBConnector.getConnection():connection;
	   	pstmt = connection.prepareStatement("INSERT INTO CLUSTER_TBL(CLUSTER_ID,CLUSTER_NAME,MAKER_ID,MAKER_DT) VALUES(?,?,'SYSTEM',sysdate)");

	     while (iterator.hasNext()) {
	    	   JSONObject jsonobj=(JSONObject)iterator.next();
	    	  // System.out.println((jsonobj).get("branchCode")+"-"+(jsonobj).get("branchName"));
	    	   
				pstmt.setString(1, ((jsonobj).get("branchCode")).toString());
				pstmt.setString(2, ((jsonobj).get("branchName")).toString());
				pstmt.addBatch();
				
				
	     }
	     count = pstmt.executeBatch();
			connection.commit();
			System.out.println("Inserted count is [" + count + "]");
	     pstmt.close();
	     connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
