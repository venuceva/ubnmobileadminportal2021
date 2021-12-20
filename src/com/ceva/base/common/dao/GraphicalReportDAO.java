package com.ceva.base.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class GraphicalReportDAO {

	Logger logger = Logger.getLogger(GraphicalReportDAO.class);

	ResponseDTO responseDTO = new ResponseDTO();
	JSONObject requestJSON = new JSONObject();
	JSONObject responseJSON = new JSONObject();
	
	
	public static String getFilters(String serviceID)
	{
		if(serviceID.equalsIgnoreCase("GYM"))
		{
			return " ";
		}
		
		else if(serviceID.equalsIgnoreCase("BC"))
		{
			return " ";
		}
		
		else if(serviceID.equalsIgnoreCase("LIB"))
		{
			return " ";
		}
		else if(serviceID.equalsIgnoreCase("CASH"))
		{
			return " ";
		}

		return null;
	}
	
	
	public ResponseDTO fetchGraphicalReportsInfo(RequestDTO requestDTO) {
		
		HashMap<String, Object> graphMap = null;
		Connection connection = null;
		logger.debug("Inside fetchGraphicalReportsInfo.. ");

		CallableStatement callableStatement = null;
		String getGraphicalReportDataProc = "{call getGraphicalReportDataProc(?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(getGraphicalReportDataProc);
			callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(1);
			
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				String dailyReportInfo = callableStatement.getString(2);
				String terminalReportInfo = callableStatement.getString(3);
				String pieChartInfo = callableStatement.getString(4);
				responseJSON = new JSONObject();
				responseJSON.put("DAILY_REPORT_DATA", dailyReportInfo);
				responseJSON.put("TERMINAL_REPORT_DATA", terminalReportInfo);
				responseJSON.put("PIE_CHART_DATA", pieChartInfo);
				logger.debug("[GraphicalReportDAO][fetchGraphicalReportsInfo]responseJSON::::"+responseJSON);
				graphMap = new HashMap<String, Object>();
				graphMap.put("GRAPH_DATA", responseJSON);
				responseDTO.setData(graphMap);
			} else if (resCnt == -1) {
				responseDTO.addError("There is an issue in getting Graphical Report Data");
			} else {
				responseDTO.addError("There is an issue in getting Graphical Report Data");
			}

		} catch (SQLException e) {
			logger.debug("Got SQLException [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} catch (Exception e) {
			logger.debug("Got Exception [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
					if (connection != null)
						connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return responseDTO;
	}
	
	
public ResponseDTO fetchGraphicalDashboardInfo(RequestDTO requestDTO) {
		
		HashMap<String, Object> graphMap = null;
		Connection connection = null;
		logger.debug("Inside fetchGraphicalDashboardInfo.. ");

		CallableStatement callableStatement = null;
		String getGraphicalDashboardDataProc = "{call getGraphicalDashBoardDataProc(?,?,?,?)}";

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("RequestJSON [" + requestJSON + "]");

			connection = DBConnector.getConnection();
			logger.debug(" connection is null [" + connection == null + "]");

			callableStatement = connection.prepareCall(getGraphicalDashboardDataProc);
			callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.executeUpdate();
			int resCnt = callableStatement.getInt(1);
			
			logger.debug("ResultCnt from DB [" + resCnt + "]");
			if (resCnt == 1) {
				String dailyReportInfo = callableStatement.getString(2);
				String marketServiceTime = callableStatement.getString(3);
				String parkingServiceTime = callableStatement.getString(4);
				responseJSON = new JSONObject();
				responseJSON.put("DAILY_REPORT_DATA", dailyReportInfo);
				responseJSON.put("MARKET_TIME", marketServiceTime);
				responseJSON.put("PARKING_TIME", parkingServiceTime);
				logger.debug("[GraphicalReportDAO][fetchGraphicalDashboardInfo]responseJSON::::"+responseJSON);
				System.out.println("Converted Data :::::"+splitGraphDataOne(dailyReportInfo).toString());
				graphMap = new HashMap<String, Object>();
				graphMap.put("GRAPH_DATA", responseJSON);
				responseDTO.setData(graphMap);
			} else if (resCnt == -1) {
				responseDTO.addError("There is an issue in getting Graphical DashBoard Data");
			} else {
				responseDTO.addError("There is an issue in getting Graphical DashBoard Data");
			}

		} catch (SQLException e) {
			logger.debug("Got SQLException [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} catch (Exception e) {
			logger.debug("Got Exception [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
		} finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
					if (connection != null)
						connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return responseDTO;
	}


public ResponseDTO getGraphData(RequestDTO requestDTO)
{
	logger.debug("Inside getGraphData.. ");

	JSONObject resultJson = null;

	HashMap<String, Object> graphMap = new HashMap<String,Object>();
	JSONArray jsonArray = new JSONArray();
	PreparedStatement graphPstmt = null;
	ResultSet graphRS = null;
	String finalResult="";
	Connection connection = null;

	requestJSON = requestDTO.getRequestJSON();

	if(requestJSON.getString("serviceid").equalsIgnoreCase("GYM"))
	{
		return getGymGraphicalReport();
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("BC"))
	{
		return getBookCenterGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("LIB"))
	{
		return getLibraryGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("CASH"))
	{
		return getCashOfficeGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("SECCHK"))
	{
		return getSecCheckGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("CAFINT"))
	{
		return getCafInternalGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("REG"))
	{
		return getRegistrationGrapReport(requestDTO);
	}
	else if(requestJSON.getString("serviceid").equalsIgnoreCase("ATD"))
	{
		return getAttendanceGrapReport(requestDTO);
	}

	try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
	try
	{
		logger.debug("[GraphActionDAO][getGraphData][Query : "+getQueries(1, requestJSON.getString("serviceid"))+getFilters(requestJSON.getString("serviceid"))+"]");

		graphPstmt = connection.prepareStatement(getQueries(1, requestJSON.getString("serviceid"))+getFilters(requestJSON.getString("serviceid")));

		graphRS = graphPstmt.executeQuery();

		while(graphRS.next())
		{
			JSONObject txn = new JSONObject();
			if(graphRS.getString(1) != null)
			{
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));
				jsonArray.add(txn);
			}
		}
		responseJSON.put("paymentModes", jsonArray);

		graphPstmt.close();
		graphRS.close();
	}
	catch(Exception e)
	{
		responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
		logger.debug("Got Exception While Exceuting getGraphData method ["
				+ e.getMessage() + "]");
		e.printStackTrace();
	}

	jsonArray.clear();

   try
	{
		graphPstmt = connection.prepareStatement(getQueries(2, requestJSON.getString("serviceid")));

		graphRS = graphPstmt.executeQuery();

		while(graphRS.next())
		{
			JSONObject txn = new JSONObject();
			txn.put("date", graphRS.getString(1));
			txn.put("amount", graphRS.getString(2));

			jsonArray.add(txn);
		}

		responseJSON.put("weekcollections", jsonArray);
		graphPstmt.close();
		graphRS.close();
	}
	catch(Exception e)
	{
		responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
		logger.debug("Got Exception While Exceuting getGraphData method ["
				+ e.getMessage() + "]");
		e.printStackTrace();
	}

	jsonArray.clear();

	try
	{
		graphPstmt = connection.prepareStatement(getQueries(3, requestJSON.getString("serviceid")));

		graphRS = graphPstmt.executeQuery();

		while(graphRS.next())
		{
			String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
			JSONObject txn = new JSONObject();
			txn.put("month", graphRS.getString(1));
			txn.put("amount", graphRS.getString(2));
			txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

			jsonArray.add(txn);
		}
		responseJSON.put("monthcollections", jsonArray);
	}
	catch(Exception e)
	{
		responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
		logger.debug("Got Exception While Exceuting getGraphData method ["
				+ e.getMessage() + "]");
		e.printStackTrace();
	}
	finally
	{
		DBUtils.closeConnection(connection);
	}

	graphMap.put("RESULT",responseJSON);
	responseDTO.setData(graphMap);

	return responseDTO;
}


public static String getQueries(int query,String serviceID)
{
	switch(query)
	{

		case 1:
				
					if(serviceID.equalsIgnoreCase("GYM") || serviceID.equalsIgnoreCase("BC") || serviceID.equalsIgnoreCase("LIB") || serviceID.equalsIgnoreCase("CASH"))
					{
						return "select nvl(sum(amount)/100,0),decode(TXNCODE,'950000','Gym Payment','940000','Library Borrow','942000','Library Return','960000','Book Center','980000','Cash Office','')TRANSTYPE from TBL_SERVICE_TXNS where TXNCODE in ('950000','940000','942000','960000','980000') and trunc(txndate) = trunc(sysdate) group by decode(TXNCODE,'950000','Gym Payment','940000','Library Borrow','942000','Library Return','960000','Book Center','980000','Cash Office')";
					}
					else if(serviceID.equalsIgnoreCase("CAFINT"))
					{
						return "select nvl(sum(amount),0)Amount,decode(CANTEENTYPE,'920000','Internal Cafeteria','920100','External Cafeteria')TRANSTYPE from TBL_CANTEEN_TXNS where CANTEENTYPE in ('920000','920100') and trunc(TXNDATE) = trunc(sysdate)  group by decode(CANTEENTYPE,'920000','Internal Cafeteria','920100','External Cafeteria')";
					}
					else if(serviceID.equalsIgnoreCase("SECCHK"))
					{
						return null;
					}
			break;
		case 2:
			        if(serviceID.equalsIgnoreCase("GYM") || serviceID.equalsIgnoreCase("BC") || serviceID.equalsIgnoreCase("LIB") || serviceID.equalsIgnoreCase("CASH"))
					{
						return "select nvl(trunc(to_date(SUBSTR(TXNDATE,1,9))),null), nvl(sum(amount),0) from TBL_SERVICE_TXNS where (trunc(to_date(SUBSTR(TXNDATE,1,9))) between sysdate-7 and sysdate) and TXNCODE in ('950000','940000','942000','960000','980000') group by trunc(to_date(SUBSTR(TXNDATE,1,9))) order by trunc(to_date(SUBSTR(TXNDATE,1,9)))";
					}
			        else if(serviceID.equalsIgnoreCase("CAFINT"))
					{
						return "select nvl(trunc(to_date(SUBSTR(TXNDATE,1,9))),null),nvl(sum(amount),0) from TBL_CANTEEN_TXNS where (trunc(to_date(SUBSTR(TXNDATE,1,9))) between sysdate-7 and sysdate) and CANTEENTYPE in ('920000','920100') group by trunc(to_date(SUBSTR(TXNDATE,1,9))) order by trunc(to_date(SUBSTR(TXNDATE,1,9)))";
					}
					else if(serviceID.equalsIgnoreCase("SECCHK"))
					{
						return null;
					}
			break;
		case 3:
			        if(serviceID.equalsIgnoreCase("GYM") || serviceID.equalsIgnoreCase("BC") || serviceID.equalsIgnoreCase("LIB") || serviceID.equalsIgnoreCase("CASH"))
					{
						return "select to_char(TXNDATE, 'MM-YYYY'), nvl(sum(amount),0) from TBL_SERVICE_TXNS where extract(YEAR from TXNDATE)=extract(YEAR from sysdate) and TXNCODE in ('950000','940000','942000','960000','980000') group by to_char(TXNDATE, 'MM-YYYY') order by 1";
					}
			        else if(serviceID.equalsIgnoreCase("CAFINT"))
					{
						return "select to_char(TXNDATE, 'MM-YYYY'), nvl(sum(amount),0) from TBL_CANTEEN_TXNS where extract(YEAR from TXNDATE) = extract(YEAR from sysdate) and CANTEENTYPE in ('920000','920100') group by to_char(TXNDATE,'MM-YYYY') order by 1";
					}
					else if(serviceID.equalsIgnoreCase("SECCHK"))
					{
						return null;
					}
			break;
	}
	return null;
}


	public JSONObject splitGraphData(String graphData){
		JSONObject returnJson =new JSONObject();
		JSONObject successJSON =new JSONObject();
		JSONObject declinedJSON =new JSONObject();
		JSONObject reversalJSON =new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		//String str="0-1,26#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0@0-1,0#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0@0-1,0#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0";
		String substr[]=graphData.split("@");
		String successStr=substr[0];
		String declinedStr=substr[1];
		String reversalStr=substr[2];
		
		String successsubStr[]=successStr.split("#");
		
		for(int i=0;i<successsubStr.length;i++){
			String eachSuccessStr=successsubStr[i];
			successJSON.put(eachSuccessStr.split(",")[0],eachSuccessStr.split(",")[1]);
		}
		
		String declinedsubStr[]=declinedStr.split("#");
		
		for(int i=0;i<declinedsubStr.length;i++){
			String eachDeclinedStr=declinedsubStr[i];
			declinedJSON.put(eachDeclinedStr.split(",")[0],eachDeclinedStr.split(",")[1]);
		}
		
		String reversalsubStr[]=reversalStr.split("#");
		
		for(int i=0;i<reversalsubStr.length;i++){
			String eachReversalStr=reversalsubStr[i];
			reversalJSON.put(eachReversalStr.split(",")[0],eachReversalStr.split(",")[1]);
		}
		
		jsonArray.add(successJSON);
		jsonArray.add(declinedJSON);
		jsonArray.add(reversalJSON);
		returnJson.put("DATA", jsonArray);
		return returnJson;
	}
	
	
	public JSONObject splitGraphDataOne(String graphData){
		JSONObject returnJson =new JSONObject();
		JSONObject successJSON =new JSONObject();
		JSONObject declinedJSON =new JSONObject();
		JSONObject reversalJSON =new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		//String str="0-1,26#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0@0-1,0#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0@0-1,0#1-2,0#2-3,0#3-4,0#4-5,0#5-6,0#6-7,0#7-8,0#8-9,0#9-10,0#10-11,0#11-12,0#12-13,0#13-14,0";
		String substr[]=graphData.split("@");
		String successStr=substr[0];
		String declinedStr=substr[1];
		String reversalStr=substr[2];
		
		String successsubStr[]=successStr.split("#");
		JSONObject json = null;
		for(int i=0;i<successsubStr.length;i++){
			json = new JSONObject();
			String eachSuccessStr=successsubStr[i];
			json.put(eachSuccessStr.split(",")[0],eachSuccessStr.split(",")[1]);
			jsonArray.add(json);
		}
		
		String declinedsubStr[]=declinedStr.split("#");
		
		for(int i=0;i<declinedsubStr.length;i++){
			String eachDeclinedStr=declinedsubStr[i];
			declinedJSON.put(eachDeclinedStr.split(",")[0],eachDeclinedStr.split(",")[1]);
		}
		
		String reversalsubStr[]=reversalStr.split("#");
		
		for(int i=0;i<reversalsubStr.length;i++){
			String eachReversalStr=reversalsubStr[i];
			reversalJSON.put(eachReversalStr.split(",")[0],eachReversalStr.split(",")[1]);
		}
		
		//returnJson.put("SUCCESS", successJSON);
		//returnJson.put("DECLINED", declinedJSON);
		//returnJson.put("REVERSAL", reversalJSON);
		
		//jsonArray.add(successJSON);
		//jsonArray.add(declinedJSON);
		//jsonArray.add(reversalJSON);
		returnJson.put("DATA", jsonArray);
		return returnJson;
	}
	
	public ResponseDTO getGymGraphicalReport()
	{
		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;
		
		String query="SELECT NVL(SUM(TXN_AMOUNT)/100,0),DECODE(INSTITUTE,'INSTID2','FCUBS',null,'OTHER') INSTITUTE,"
				+ "NVL(SUM(TXN_AMOUNT)/100,0) FROM TRAN_LOG WHERE extract(YEAR FROM TXN_TIME)=extract(YEAR FROM sysdate-300) GROUP BY "
				+ "DECODE(INSTITUTE,'INSTID2','FCUBS',null,'OTHER') ORDER BY 1";
		
	
		
		try{
			connection=DBConnector.getConnection();
		     }catch(Exception e){
		    	 e.printStackTrace();
		    	 }
		try
		{
			
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));
				txn.put("totcount", graphRS.getString(3));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query="SELECT TO_CHAR(TXN_TIME, 'MM-YYYY'),NVL(SUM(TXN_AMOUNT)/100,0) FROM TRAN_LOG WHERE extract(YEAR FROM TXN_TIME)=extract(YEAR FROM sysdate-300) GROUP BY TO_CHAR(TXN_TIME, 'MM-YYYY') ORDER BY 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		query="SELECT TO_CHAR(TXN_TIME, 'MM-YYYY'),NVL(SUM(TXN_AMOUNT)/100,0) FROM TRAN_LOG WHERE extract(YEAR FROM TXN_TIME)=extract(YEAR FROM sysdate-300) GROUP BY TO_CHAR(TXN_TIME, 'MM-YYYY') ORDER BY 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;
	}
	
	public ResponseDTO getBookCenterGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();

		String query = "select nvl(sum(amount)/100,0),decode(SECVICEDESC,'BC','Book Center')TRANSTYPE " +
				"from TBL_SERVICE_TXNS where SECVICEDESC in ('BC') and trunc(txndate) = trunc(sysdate) and RESPONSECODE = '00' " +
				"group by decode(SECVICEDESC,'BC','Book Center')";

		try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query = "select to_char(TXNDATE, 'DD-MON-YYYY'), nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where (to_char(TXNDATE, 'DD-MON-YYYY') between sysdate-7 and sysdate) and SECVICEDESC in ('BC') and RESPONSECODE = '00' " +
				"group by to_char(TXNDATE, 'DD-MON-YYYY') order by to_char(TXNDATE, 'DD-MON-YYYY')";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query = "select to_char(TXNDATE, 'MM-YYYY'), nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where extract(YEAR from TXNDATE)=extract(YEAR from sysdate) and SECVICEDESC in ('BC') and RESPONSECODE = '00' " +
				"group by to_char(TXNDATE, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;
	}
	
	public ResponseDTO getLibraryGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();
		
		String query = "select nvl(sum(amount)/100,0),decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine')TRANSTYPE" +
				" from TBL_SERVICE_TXNS where SECVICEDESC in ('LB','LR','LPF') and trunc(txndate) = trunc(sysdate) and RESPONSECODE = '00' " +
				"group by decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine')";

		try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		/*query = "select decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine')TRANSTYPE, nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS where (to_char(TXNDATE, 'DD-MON-YYYY') " +
				"between sysdate-7 and sysdate) and SECVICEDESC in ('LB','LR','LPF') and " +
				"RESPONSECODE = '00' group by decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine')"; */
				
	        query = "select SECVICEDESC, nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS where (to_char(TXNDATE, 'DD-MON-YYYY') " +
				"between sysdate-7 and sysdate) and SECVICEDESC in ('LB','LR','LPF') and " +
				"RESPONSECODE = '00' group by SECVICEDESC";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		/*query = "select decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine')TRANSTYPE, nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where extract(YEAR from TXNDATE)=extract(YEAR from sysdate) and SECVICEDESC in ('LB','LR','LPF') and RESPONSECODE = '00' " +
				"group by decode(SECVICEDESC,'LB','Library Borrow','LR','Library Return','LPF','Library Pay Fine') order by 1"; */
				
		query="select to_char(TXNDATE, 'MM-YYYY'), nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where extract(YEAR from TXNDATE)=extract(YEAR from sysdate) and SECVICEDESC in ('LB','LR','LPF') and RESPONSECODE = '00' " +
				"group by to_char(TXNDATE, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;
	}
	
	private ResponseDTO getCashOfficeGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();

		/*String query = "select nvl(sum(amount)/100,0),decode(TXNCODE,'980000','Cash Office')TRANSTYPE " +
				"from TBL_SERVICE_TXNS where TXNCODE in ('980000') and trunc(txndate) = trunc(sysdate) " +
				"group by decode(TXNCODE,'980000','Cash Office')";*/
		
		String query = "select nvl(sum(amount)/100,0),decode(SECVICEDESC,'CO','Cash Office')TRANSTYPE " +
				"from TBL_SERVICE_TXNS where SECVICEDESC in ('CO') and trunc(txndate) = trunc(sysdate) and RESPONSECODE = '00' " +
				"group by decode(SECVICEDESC,'CO','Cash Office')";

		try
		{
			connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();
			}
		
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query="select to_char(TXNDATE, 'DD-MON-YYYY'), nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where (to_char(TXNDATE, 'DD-MON-YYYY') between sysdate-7 and sysdate) and SECVICEDESC in ('CO') and RESPONSECODE = '00' " +
				"group by to_char(TXNDATE, 'DD-MON-YYYY') order by to_char(TXNDATE, 'DD-MON-YYYY')";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		query="select to_char(TXNDATE, 'MM-YYYY'), nvl(sum(amount)/100,0) from TBL_SERVICE_TXNS " +
				"where extract(YEAR from TXNDATE)=extract(YEAR from sysdate) and SECVICEDESC in ('CO') and RESPONSECODE = '00' " +
				"group by to_char(TXNDATE, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;

	}
	
	private ResponseDTO getSecCheckGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();
		
		String query = "select nvl(count(STATUS),'0'), status from tbl_student_inout where STATUS " +
				"in ('CheckIn','CheckOut') and trunc(CHECKIN) = trunc(sysdate) or " +
				"trunc(CHECKOUT) = trunc(sysdate) and STATUS in('CheckIn','CheckOut') group by status";

		try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
               jsonArray.clear();

		

		query = "select status, count(*)  from tbl_student_inout where ((to_char(CHECKIN, 'DD-MON-YYYY'))" +
		  		" between sysdate-7 and sysdate) and STATUS in('CheckIn','CheckOut') group by status";
		try
		{
			graphPstmt = connection.prepareStatement(query);
	graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		query = "select to_char(CHECKIN, 'MM-YYYY'), nvl(count(*),0) from tbl_student_inout" +
				" where extract(YEAR from CHECKIN)=extract(YEAR from sysdate) and STATUS in('CheckIn','CheckOut')" +
				"group by to_char(CHECKIN, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;

	}
	
	
	private ResponseDTO getCafInternalGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();
		
		String query = "select count(*),MEALTYPE from TBL_CANTEEN_TXNS where  trunc(TXNDATE) = trunc(sysdate) and RESPONSECODE='00' group by MEALTYPE";

		try
		{
			connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();
			}
		
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);
			
			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
                jsonArray.clear();

		
		
		query = "SELECT MEALTYPE, COUNT(*) FROM TBL_CANTEEN_TXNS WHERE (TO_CHAR(TXNDATE, 'DD-MON-YYYY') BETWEEN sysdate-7 AND sysdate) " +
				"AND CANTEENTYPE   IN ('920000','920100') and Responsecode='00' GROUP BY MEALTYPE";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				jsonArray.add(txn);

				
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		
		query = "select to_char(TXNDATE, 'MM-YYYY'), count(*) from TBL_CANTEEN_TXNS where " +
				"extract(YEAR from TXNDATE) = extract(YEAR from sysdate) and CANTEENTYPE " +
				"in ('920000','920100') group by to_char(TXNDATE, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			
		        //graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;

	}
	
	
	private ResponseDTO getRegistrationGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();

		String query = "select count(*) from STUDENT_WALLET_INFO where " +
				"EXTRACT(DAY FROM MAKERDTTM) = EXTRACT(DAY FROM SYSDATE) AND EXTRACT(MONTH FROM MAKERDTTM) = EXTRACT(MONTH FROM SYSDATE) " +
				"and EXTRACT(YEAR FROM MAKERDTTM) = EXTRACT(YEAR FROM SYSDATE) and STATUS = 'R'";

		try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", "Registration");

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);
			
			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query="select to_char(MAKERDTTM, 'DD-MON-YYYY'), nvl(count(*),0) from STUDENT_WALLET_INFO " +
				"where (to_char(MAKERDTTM, 'DD-MON-YYYY') between sysdate-7 and sysdate) and STATUS in ('R') " +
				"group by to_char(MAKERDTTM, 'DD-MON-YYYY') order by to_char(MAKERDTTM, 'DD-MON-YYYY')";

		try
		{
			
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		query="select to_char(MAKERDTTM, 'MM-YYYY'), nvl(count(*),0) from STUDENT_WALLET_INFO " +
				"where extract(YEAR from MAKERDTTM)=extract(YEAR from sysdate) and STATUS in ('R') " +
				"group by to_char(MAKERDTTM, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;

	}
	
	private ResponseDTO getAttendanceGrapReport(RequestDTO requestDTO)
	{
		logger.debug("Inside getGraphData.. ");

		JSONObject resultJson = null;

		HashMap<String, Object> graphMap = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		PreparedStatement graphPstmt = null;
		ResultSet graphRS = null;
		String finalResult="";
		Connection connection = null;

		requestJSON = requestDTO.getRequestJSON();

		String query = "select count(*) from ATTENDANCE where (L1='1' or L2='1' or  L3='1' or  L4='1' or  L5='1' or  L6='1' or  L7='1' or " +
				"L8='1' or  L9='1' or  L10='1' or  L11='1' or  L12='1' or  L13='1' or  L14='1' or  L15='1' or  L16='1' or  L17='1' or " +
				"L18='1' or  L19='1' or  L20='1' or  L21='1' or  L22='1' or  L23='1' or  L24='1' or  L25='1' or  L26='1' or  L27='1' or " +
				"L28='1' or  L29='1' or  L30='1' or  L31='1' or  L32='1' or  L33='1' or  L34='1' or  L35='1' or  L36='1') " ;
			

		try{connection=DBConnector.getConnection();}catch(Exception e){e.printStackTrace();}
		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("count", graphRS.getString(1));
				txn.put("paymentType", "Attendance");

				jsonArray.add(txn);
			}

			responseJSON.put("paymentModes", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();
		
		query="select to_char(AUDITDATE, 'DD-MON-YYYY'), count(*) from ATTENDANCE " +
				"where (L1='1' or L2='1' or  L3='1' or  L4='1' or  L5='1' or  L6='1' or  L7='1' or L8='1' or  L9='1' or  " +
				"L10='1' or  L11='1' or  L12='1' or  L13='1' or  L14='1' or  L15='1' or  L16='1' or  L17='1' or " +
				"L18='1' or  L19='1' or  L20='1' or  L21='1' or  L22='1' or  L23='1' or  L24='1' or  L25='1' or  L26='1' or  L27='1' or " +
				"L28='1' or  L29='1' or  L30='1' or  L31='1' or  L32='1' or  L33='1' or  L34='1' or  L35='1' or  L36='1') " +
				"group by to_char(AUDITDATE, 'DD-MON-YYYY') order by to_char(AUDITDATE, 'DD-MON-YYYY')";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				JSONObject txn = new JSONObject();
				txn.put("date", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));

				jsonArray.add(txn);
			}

			responseJSON.put("weekcollections", jsonArray);

			graphPstmt.close();
			graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}

		jsonArray.clear();

		query="select to_char(AUDITDATE, 'MM-YYYY'), count(*) from ATTENDANCE " +
				"where (L1='1' or L2='1' or  L3='1' or  L4='1' or  L5='1' or  L6='1' or  L7='1' or " +
				"L8='1' or  L9='1' or  L10='1' or  L11='1' or  L12='1' or  L13='1' or  L14='1' or  L15='1' or  L16='1' or  L17='1' or " +
				"L18='1' or  L19='1' or  L20='1' or  L21='1' or  L22='1' or  L23='1' or  L24='1' or  L25='1' or  L26='1' or  L27='1' or " +
				"L28='1' or  L29='1' or  L30='1' or  L31='1' or  L32='1' or  L33='1' or  L34='1' or  L35='1' or  L36='1') " +
				"group by to_char(AUDITDATE, 'MM-YYYY') order by 1";

		try
		{
			graphPstmt = connection.prepareStatement(query);

			graphRS = graphPstmt.executeQuery();

			while(graphRS.next())
			{
				String[] months={"ind","January","February","March","April","May","June","July","Agust","September","October","November","December"};
				JSONObject txn = new JSONObject();
				txn.put("month", graphRS.getString(1));
				txn.put("amount", graphRS.getString(2));
				txn.put("monthinwords", months[Integer.parseInt(graphRS.getString(1).split("-")[0])]);

				jsonArray.add(txn);
			}
			responseJSON.put("monthcollections", jsonArray);
			//graphPstmt.close();
			//graphRS.close();
		}
		catch(Exception e)
		{
			responseDTO.addError("Got Exception While Exceuting getGraphData method ["+ e.getMessage() + "]");
			logger.debug("Got Exception While Exceuting getGraphData method ["
					+ e.getMessage() + "]");
			e.printStackTrace();
		}
		finally
		{
			DBUtils.closeConnection(connection);
		}

		graphMap.put("RESULT",responseJSON);
		responseDTO.setData(graphMap);

		return responseDTO;

	}
}
