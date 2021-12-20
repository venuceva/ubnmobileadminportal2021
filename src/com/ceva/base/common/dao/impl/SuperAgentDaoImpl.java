package com.ceva.base.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.bean.Agent;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.SuperAgentDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class SuperAgentDaoImpl implements SuperAgentDAO {
	ResponseDTO responseDTO = null;
	Logger logger = Logger.getLogger(this.getClass());
	BeanProcessor processor = null;

	@Override
	public ResponseDTO save(Map map) {

		String insertprod="{call agents.insertAgent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Agent agent = (Agent) map.get(CevaCommonConstants.OBJECT);
		logger.info("inside save..:" + agent.toString());
		responseDTO=new ResponseDTO();
		Connection connection=null;
		CallableStatement cstmt=null;

		try {
			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			cstmt = connection.prepareCall(insertprod);
			cstmt.setString(1, agent.getAccountNumbers());
			cstmt.setString(2, agent.getBranchCode());
			cstmt.setString(3, agent.getAccountName());
			cstmt.setString(4, agent.getDob());
			cstmt.setString(5, agent.getEmail());
			cstmt.setString(6, agent.getIDType());
			cstmt.setString(7, agent.getIDNumber());
			cstmt.setString(8, agent.getTelco());
			cstmt.setString(9, agent.getProduct());
			cstmt.setString(10, agent.getProdesc());
			cstmt.setString(11, agent.getTelephoneNumber2());
			cstmt.setString(12, agent.getAddressLine1());
			cstmt.setString(13, agent.getAddressLine2());
			cstmt.setString(14, agent.getLocalGovernment());
			cstmt.setString(15, agent.getState());
			cstmt.setString(16, agent.getCountry());
			cstmt.setString(17, agent.getCity());
			cstmt.setString(18, agent.getLangitude());
			cstmt.setString(19, agent.getLatitude());
			cstmt.setString(20, agent.getSrchcriteria());
			
			cstmt.setString(21, agent.getGender());
			cstmt.setString(22, agent.getMobile());
			cstmt.setString(23, agent.getNationality());
			cstmt.setString(24, map.get(CevaCommonConstants.IP)+"");
			cstmt.setString(25, map.get(CevaCommonConstants.MAKER_ID)+"");
			cstmt.setString(26,  agent.getCBNAgentId());
			cstmt.registerOutParameter(27, Types.VARCHAR);
			

			cstmt.executeUpdate();
			String result = cstmt.getString(27);
			logger.info("response from DB..:"+result);
			if("SUCCESS".equals(result.split("-")[0])){
				responseDTO.addMessages("SUCCESS");
				//responseDTO.addMessages("Wallet Account No Created : "+result.split("-")[1]);
			}else{
				responseDTO.addMessages("ERROR");
				responseDTO.addError("Error Occured while inserting Super Agent.");
				responseDTO.addError(result);
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
			agent =null;
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
	public ResponseDTO delete(Map map) {

		String insertprod="{call agents.activedeactive(?,?,?,?)}";
		Agent agent = (Agent) map.get(CevaCommonConstants.OBJECT);
		logger.info("inside activedeactive..:" + agent.toString());
		responseDTO=new ResponseDTO();
		Connection connection=null;
		CallableStatement cstmt=null;

		try {
			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			cstmt = connection.prepareCall(insertprod);
			cstmt.setString(1, agent.getAccountNumbers());
			cstmt.setString(2, map.get(CevaCommonConstants.IP)+"");
			cstmt.setString(3, map.get(CevaCommonConstants.MAKER_ID)+"");
			cstmt.registerOutParameter(4, Types.VARCHAR);

			cstmt.executeUpdate();
			String result = cstmt.getString(4);
			logger.info("response from DB..:"+result);
			if("SUCCESS".equals(result)){
				responseDTO.addMessages(result);
			}else{
				responseDTO.addError("Error Occured while Deactivating SuperAgent.");
				responseDTO.addError(result);
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
			agent =null;
		}
		return responseDTO;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getAgentToSelectBox() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONObject object=null;
		try{
			String prodQry = "SELECT ACCOUNTNUMBER, ACCOUNTNAME FROM SUPER_AGENT";
			connection = connection==null ? DBConnector.getConnection():connection;

			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToSelectBox(rs);
			logger.info("products..:"+object);
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

	public Agent getByObject(Agent agent) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.info(agent.toString());
		String sql = prepareQuery(agent);
		logger.info(sql);
		try {
			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				processor = new BeanProcessor();
				agent = processor.toBean(rs, Agent.class);
			}
			if (agent != null) {
				logger.info(agent.toString());
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
		return agent;
	}

	private String prepareQuery(Agent agent) {
		StringBuilder sql =null;

		try {
			sql = new StringBuilder(qry);

			if (agent.getAccountNumbers() != null && agent.getAccountNumbers().length()>0) {
				sql.append(" AND ACCOUNTNUMBER = '" + agent.getAccountNumbers()
						+ "'");
			}
			if (agent.getEmail() != null && agent.getEmail().length() > 0) {
				sql.append(" AND EMAIL = '" + agent.getEmail() + "'");
			}
			if (agent.getMobile() != null && agent.getMobile().length() > 0) {
				sql.append(" AND MOBILE = '" + agent.getMobile() + "'");
			}
			if (agent.getRefNum() != null && agent.getRefNum().length() > 0) {
				sql.append(" AND REF_NUM = '" + agent.getRefNum() + "'");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error" + e.getLocalizedMessage());
		}
		return sql.toString();
	}

}
