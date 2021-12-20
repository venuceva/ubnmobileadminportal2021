package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ceva.base.ceva.action.PagenationAction;
import com.ceva.base.ceva.action.ResultSetConvertor;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class PagenationDAO {
	private static Logger logger = Logger.getLogger(PagenationDAO.class);

	private HttpSession session = null;
	public JSONObject getPages(RequestDTO requestDTO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONObject responseJSON = new JSONObject();
		JSONArray records = null;
		ResourceBundle rb = null;
		String countQry=null;
		String recordsQry=null;
		String search=null;
		
		try {
			JSONObject requestJSON = requestDTO.getRequestJSON();
			logger.info("requestJSON..:" + requestJSON);
			rb = ResourceBundle.getBundle("pagination");
			search=requestJSON.getString(CevaCommonConstants.PAGE_SEARCH);
			con = DBConnector.getConnection();
			if(search.length()>0){
				recordsQry=rb.getString(requestJSON.getString(CevaCommonConstants.QUERY_ID)+"_SEARCH_RECORD_QUERY");
				recordsQry=recordsQry.replaceAll(":srch", requestJSON.getString(CevaCommonConstants.PAGE_SEARCH));

				countQry=rb.getString(requestJSON.getString(CevaCommonConstants.QUERY_ID)+"_SEARCH_COUNT_QUERY").replaceAll(":srch",requestJSON.getString(CevaCommonConstants.PAGE_SEARCH));
			}else{
				recordsQry=rb.getString(requestJSON.getString(CevaCommonConstants.QUERY_ID)+"_RECORD_QUERY");
				countQry=rb.getString(requestJSON.getString(CevaCommonConstants.QUERY_ID)+"_COUNT_QUERY");

			}
			logger.debug(" beforeQuary::"+recordsQry);
			pstmt = con.prepareStatement(recordsQry);
			pstmt.setString(1, requestJSON.getString(CevaCommonConstants.START));
			pstmt.setString(2, requestJSON.getString(CevaCommonConstants.END));
			int cnt = getTotalRecordCount(countQry);
			rs = pstmt.executeQuery();
			records = ResultSetConvertor.convertResultSetIntoJSON(rs);
			responseJSON.put(CevaCommonConstants.MESSAGE,CevaCommonConstants.SUCCESS_RESULT);
			responseJSON.put(CevaCommonConstants.RESULT, records);
			responseJSON.put("iTotalDisplayRecords", cnt);
			responseJSON.put("iTotalRecords", cnt);
			/* responseJSON.put("sEcho", "1"); */

		} catch (Exception e) {
			responseJSON.put(CevaCommonConstants.MESSAGE,
					CevaCommonConstants.ERROR_RESULT);
			logger.info("Error..:"+e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(con);
		}
		logger.info("responseJSON..:" + responseJSON);
		return responseJSON;
	}
	
	
	private int getTotalRecordCount(String qry) {

		int totalRecords = -1;

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			con = DBConnector.getConnection();
			statement = con.prepareStatement(qry);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				totalRecords = resultSet.getInt("count");
				System.out.println("COUNT RECIEVDD JCCCC"+totalRecords);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeResultSet(resultSet);
			DBUtils.closePreparedStatement(statement);
			DBUtils.closeConnection(con);
		}
		return totalRecords;
	}

}
