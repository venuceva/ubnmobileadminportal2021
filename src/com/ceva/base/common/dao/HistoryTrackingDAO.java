package com.ceva.base.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ceva.base.common.utils.DBConnector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HistoryTrackingDAO
{

	private static Logger logger = Logger.getLogger(ReportsDAO.class);

	public JSONObject getClassNames()
	{
		JSONArray jarr = new JSONArray();
		JSONObject jres = new JSONObject();

		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String txnCntQuery = "";

		try
		{
			connection = DBConnector.getConnection();
			txnCntQuery = "select MENU_NAME,MENU_ACTION from ceva_menu_list where PARENT_MENU_ID='1' order by APPL_CODE,MENU_NAME";
			preStmt = connection.prepareStatement(txnCntQuery);
			rs = preStmt.executeQuery();

			JSONObject txn = null;
			while(rs.next())
			{
				txn = new JSONObject();

				txn.put("TEXT", rs.getString(1));
				txn.put("VALUE", rs.getString(2));

				jarr.add(txn);

				txn.clear();txn=null;
			}

			jres.put("ACTIONLIST", jarr);
			logger.debug("[HistoryTrackingDAO][getClassNames][Response JSON : "+jres+"]");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
				preStmt.close();
				rs.close();
				connection.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return jres;
	}
}
