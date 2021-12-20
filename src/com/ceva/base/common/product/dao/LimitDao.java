package com.ceva.base.common.product.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.common.utils.DBUtil;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.util.DBUtils;


public class LimitDao {


	private Logger logger = Logger.getLogger(LimitDao.class);

	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject responseJSON = null;


	public ResponseDTO fetchLimitInfo(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;
		JSONArray JsonArray = null;
		JSONObject resultJson = null;
		PreparedStatement getfrqPstmt = null;
		ResultSet getfrqRs = null;


		PreparedStatement gettrnsPstmt = null;
		ResultSet gettrnsRs = null;

		PreparedStatement getfeetrnsPstmt = null;
		ResultSet getfeetrnsRs = null;

		Connection connection = null;
		
		

		String frqQry = "select key_value,key_value from config_data where KEY_GROUP='TXN_FREQUENCY' and key_type='FREQ_PERIOD' order by key_id";

		logger.info("frqQry ["+frqQry+"]");
		
		String autoQrylimit = "select 'LT'||LPAD(count(*)+1,2, '0')||round(dbms_random.value(2,99)) from LIMIT_FEE_MASTER WHERE USAGE_TYPE='L'";

		logger.info("frqQry ["+autoQrylimit+"]");
		
		String autoQryfee = "select 'FE'||LPAD(count(*)+1,2, '0')||round(dbms_random.value(2,99)) from LIMIT_FEE_MASTER WHERE USAGE_TYPE='F'";

		logger.info("frqQry ["+autoQryfee+"]");


		//String transQuery ="select txn_code,txn_name from txn_master where txn_type='T' and  DISPLAY_FLAG is null order by txn_code";
		

		//String feetransQuery ="select txn_code,txn_name from txn_master where txn_type='F' and  DISPLAY_FLAG is null order by txn_code";
		
		
		
		String productQuerylimit ="select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT WHERE PRD_CODE not in (select distinct PRODUCT from LIMIT_FEE_MASTER where USAGE_TYPE='L')  order by PRD_CODE";
		String productQueryfee ="select PRD_CODE||'-'||PRD_NAME||'-'||PRD_CCY||'-'||APPLICATION,PRD_CODE from PRODUCT WHERE PRD_CODE not in (select distinct PRODUCT from LIMIT_FEE_MASTER where USAGE_TYPE='F')  order by PRD_CODE";



		JSONObject json = null;
		try {

			requestJSON = requestDTO.getRequestJSON();
			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();
			JsonArray = new JSONArray();
			resultJson = new JSONObject();

			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			if(connection!=null)
			{
				
				String linkmode = requestJSON.getString("linkmode");
				
				String limitcode = requestJSON.getString("LimitCode");
				String feecode = requestJSON.getString("FeeCode");
				String limitdesc = requestJSON.getString("LimitDescription");
				String feedesc = requestJSON.getString("Feedescrption");
				

				getfrqPstmt = connection.prepareStatement(frqQry);

				getfrqRs = getfrqPstmt.executeQuery();

				json = new JSONObject();
				while (getfrqRs.next()) {

					json.put(getfrqRs.getString(1), getfrqRs.getString(2));

				}

				resultJson.put("FREQ_PERIOD", json);

				JsonArray = new JSONArray();

				
					
					gettrnsPstmt = connection.prepareStatement(autoQrylimit);
	
					gettrnsRs = gettrnsPstmt.executeQuery();
	
					json = new JSONObject();
					while (gettrnsRs.next()) {
	
						if((gettrnsRs.getString(1)).length()==5){
							resultJson.put("LIMIT_CODE", gettrnsRs.getString(1)+"0");	
						}else{
						resultJson.put("LIMIT_CODE", gettrnsRs.getString(1));
						}
					}
					
					gettrnsPstmt.close();
					gettrnsRs.close();
					
					gettrnsPstmt = connection.prepareStatement(autoQryfee);
	
					gettrnsRs = gettrnsPstmt.executeQuery();
	
					json = new JSONObject();
					while (gettrnsRs.next()) {
	
						
						if((gettrnsRs.getString(1)).length()==5){
							resultJson.put("FEE_CODE", gettrnsRs.getString(1)+"0");	
						}else{
						resultJson.put("FEE_CODE", gettrnsRs.getString(1));
						}
	
					}
				
				JsonArray = new JSONArray();

				

				
				if(linkmode.equalsIgnoreCase("NEW")){
					resultJson.put("LIMIT_DESC", "");
					resultJson.put("FEE_DESC", "");
					
					gettrnsPstmt = connection.prepareStatement(productQuerylimit);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("PRODUCT_LIMIT", json);
					
					
					gettrnsPstmt = connection.prepareStatement(productQueryfee);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("PRODUCT_FEE", json);
					
					
						
					
						gettrnsPstmt.close();
						gettrnsRs.close();
					
					

				}else{
					
					String transQuery ="select SERVICE_CODE||'-'||SERVICE_NAME,SERVICE_CODE||'-'||SERVICE_NAME from MOB_CHANNEL_MAP "
							+ "where ASSIGN_LIMIT='YES' AND PRODUCT='"+requestJSON.getString("productcode")+"'  order by SERVICE_NAME ";

					logger.info("transQuery ["+transQuery+"]");
					
					gettrnsPstmt = connection.prepareStatement(transQuery);

					gettrnsRs = gettrnsPstmt.executeQuery();

					json = new JSONObject();
					while (gettrnsRs.next()) {

						json.put(gettrnsRs.getString(1), gettrnsRs.getString(2));

					}

					resultJson.put("TRANS_DATA", json);
					
					
						
					
						gettrnsPstmt.close();
						gettrnsRs.close();
						
					String feetransQuery ="select SERVICE_CODE||'-'||SERVICE_NAME,SERVICE_CODE||'-'||SERVICE_NAME from MOB_CHANNEL_MAP "
							+ "where ASSIGN_FEE='YES' AND PRODUCT='"+requestJSON.getString("productcode")+"'  order by SERVICE_NAME  ";

					logger.info("Fetch Trans Query ["+feetransQuery+"]");
						
						
						getfeetrnsPstmt = connection.prepareStatement(feetransQuery);

					getfeetrnsRs = getfeetrnsPstmt.executeQuery();

					json = new JSONObject();
					while (getfeetrnsRs.next()) {

						json.put(getfeetrnsRs.getString(1), getfeetrnsRs.getString(2));

					}

					resultJson.put("FEE_TRANS_DATA", json);
					
					
					
					/*resultJson.put("LIMIT_CODE", requestJSON.getString("LimitCode"));
					resultJson.put("FEE_CODE", requestJSON.getString("feeCode"));
					resultJson.put("LIMIT_DESC", requestJSON.getString("limitDescription"));
					resultJson.put("FEE_DESC", requestJSON.getString("feeDescription"));*/
					
					
					resultJson.put("PRODUCT_CODE", requestJSON.getString("productcode"));
					resultJson.put("APPLICATION", requestJSON.getString("application"));
					resultJson.put("LIMIT_CODE", limitcode);
					resultJson.put("FEE_CODE", feecode);
					resultJson.put("LIMIT_DESC", limitdesc);
					resultJson.put("FEE_DESC", feedesc);
					
					if(!limitcode.equalsIgnoreCase("")){
					String entityQry1 =  "select rownum,CHANNEL,(select SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER t where t.SERVICE_CODE=LD.TXN_CODE ),"
							+"LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT"
                            +" from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE= ? and LM.REF_NUM=LD.REF_NUM";
		    
					gettrnsPstmt = connection.prepareStatement(entityQry1);
					gettrnsPstmt.setString(1,limitcode);
					gettrnsRs = gettrnsPstmt.executeQuery();
					     
			
					    while (gettrnsRs.next()) {
					     
					     json.put("SNO", gettrnsRs.getString(1));
					     json.put("CHANNEL", gettrnsRs.getString(2));
					     json.put("TXNNAME", gettrnsRs.getString(3));
					     json.put("FREQ", gettrnsRs.getString(4));
					     json.put("MAX_CNT", gettrnsRs.getInt(5));
					     json.put("MIN_AMT", gettrnsRs.getInt(6));
					     json.put("MAX_AMT", gettrnsRs.getInt(7));
					     
					     JsonArray.add(json);
					     
					    }
		    
		    resultJson.put("limitcodedetails2", JsonArray);
					
					}
					
					
					if(!feecode.equalsIgnoreCase("")){
					String entityQry1 =  "select rownum SNO ,CHANNEL,(select SERVICE_CODE||'-'||SERVICE_NAME from MOB_SERVICE_MASTER t where t.SERVICE_CODE=LD.TXN_CODE ) TXNNAME,"
							+ "LD.FREQUENCY,LD.FEE_TYPE "
							+ "FLATPER,LD.VALUE FPVALUE,LD.CNT_AMT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
							+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.AGENT,LD.CEVA,LD.BANK from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where "
							+ "LM.LMT_FEE_CODE=? and LM.REF_NUM=LD.REF_NUM";
					    
					gettrnsPstmt = connection.prepareStatement(entityQry1);
					gettrnsPstmt.setString(1,feecode);
					gettrnsRs = gettrnsPstmt.executeQuery();
					     

					    while (gettrnsRs.next()) {
					     
					     json.put("SNO", gettrnsRs.getString(1));
					     json.put("CHANNEL", gettrnsRs.getString(2));
					     json.put("TXNNAME", gettrnsRs.getString(3));
					     json.put("FREQ", gettrnsRs.getString(4));
					     json.put("FLATPER", gettrnsRs.getString(5));
					     json.put("FPVALUE", gettrnsRs.getString(6));
					     json.put("CRT", gettrnsRs.getString(7));
					     json.put("FRMVAL", gettrnsRs.getInt(8));
					     json.put("TOVAL", gettrnsRs.getInt(9));
					     json.put("AGENT", gettrnsRs.getInt(10));
					     json.put("CEVA", gettrnsRs.getInt(11));
					     json.put("BANK", gettrnsRs.getInt(12));

					     
					     JsonArray.add(json);
					     
					    }
					    
					    resultJson.put("Feecodedetails2", JsonArray);
					
					
						}
				}

				lmtDataMap.put("LIMIT_INFO", resultJson);
				logger.debug("Limit DataMap   [" + lmtDataMap + "]");
				responseDTO.setData(lmtDataMap);



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {
			logger.debug("Got Exception in   [" + e.getMessage() + "]");
		} finally {

			

			try {

				if (gettrnsPstmt != null) {
					gettrnsPstmt.close();
				}

				if (gettrnsRs != null) {
					gettrnsRs.close();
				}


				if (getfrqRs != null) {
					getfrqRs.close();
				}

				if (getfrqPstmt != null) {
					getfrqPstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

	public ResponseDTO savelimitdata(RequestDTO requestDTO) {


		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;

		Connection connection = null;
		CallableStatement cs = null;

		JSONObject requestJSON = requestDTO.getRequestJSON();

		String retstr = null;
		int retval=0;

		try {

			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();


			// connection = DBConnector.getConnection();
			
			connection=DBUtil.getDBConnection();
			
			String lmtcode = requestJSON.getString("LimitCode");
			String lmtdesc = requestJSON.getString("LimitDescription");

			String productcode = requestJSON.getString("productcode");
			String application = requestJSON.getString("application");
			
			
			String limtjson =  requestJSON.getString("LimitJson");

			String initiatorID =  requestJSON.getString("makerId");

			logger.info("limtjson ["+limtjson+"] " +
					"lmtcode ["+lmtcode+"] lmtdesc ["+lmtdesc+"] makerId ["+initiatorID+"]");


			JSONArray limtjsonobj = JSONArray.fromObject(limtjson);


			String [] txndata = new String[limtjsonobj.size()];
			String []freqdata = new String[limtjsonobj.size()];
			String [] maxcountdata = new String[limtjsonobj.size()];
			String []minamt = new String[limtjsonobj.size()];
			String [] maxamt = new String[limtjsonobj.size()];
			String [] channel = new String[limtjsonobj.size()];
			String [] operators = new String[limtjsonobj.size()];

			for (int i = 0; i < limtjsonobj.size(); i++) {

				JSONObject ljson =  limtjsonobj.getJSONObject(i);

				txndata[i]=(ljson.getString("Transaction")).split("-")[0];
				freqdata[i]=ljson.getString("Frequency");
				maxcountdata[i]=ljson.getString("MaxCount");
				minamt[i]=ljson.getString("MinAmount");
				maxamt[i]=ljson.getString("MaxAmount");
				channel[i]=ljson.getString("channel");
				operators[i]=ljson.getString("operators").split("-")[0];

			}


			//logger.debug("connection is null [" + connection == null + "]");

			if(connection!=null)
			{


				ArrayDescriptor arraydescriptor = ArrayDescriptor.createDescriptor("ARRTYPE",connection);

				String confirmQuery = "{call LimitFeeConfigPKG.pLimitConfig(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

				cs = connection.prepareCall(confirmQuery);

				ARRAY artxndata = new ARRAY(arraydescriptor,connection,txndata);
				ARRAY freqdatadata = new ARRAY(arraydescriptor,connection,freqdata);
				ARRAY maxcountdatadata = new ARRAY(arraydescriptor,connection,maxcountdata);
				ARRAY minamtdata = new ARRAY(arraydescriptor,connection,minamt);
				ARRAY maxamtdata = new ARRAY(arraydescriptor,connection,maxamt);
				ARRAY channeldata = new ARRAY(arraydescriptor,connection,channel);
				ARRAY operatorsdata = new ARRAY(arraydescriptor,connection,operators);


				cs.setString(1,  lmtcode);	
				cs.setString(2, lmtdesc);

				cs.setArray(3, artxndata);
				cs.setArray(4, freqdatadata);
				cs.setArray(5, maxcountdatadata);
				cs.setArray(6, minamtdata);
				cs.setArray(7,maxamtdata);
				cs.setArray(8,channeldata);
				cs.setArray(9,operatorsdata);

				cs.setString(10, initiatorID);
				cs.setString(11, productcode);
				cs.setString(12, application);
				cs.registerOutParameter(13, java.sql.Types.VARCHAR);
				cs.registerOutParameter(14, java.sql.Types.NUMERIC);


				cs.execute();

				//System.out.println("FLAG VALUE : "+ cs.getString(16));
				retstr = cs.getString(13);
				retval= cs.getInt(14);

				/*retstr = cs.getString(5);
				int rretval= cs.getInt(6);*/
				
				
				JSONObject json = new JSONObject();
				json.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
				json.put("transCode", "productlimitsettings");
				json.put("channel", "WEB");
				json.put("message", "Acknowledgment :: Limit Code "+lmtcode+" Created for  product code is  "+productcode);
				json.put("ip", requestJSON.getString("remoteip"));
				json.put("det1", "");
				json.put("det2", "");
				json.put("det3", "");
				
				CommonServiceDao csd=new CommonServiceDao();
				csd.auditTrailInsert(json);

				if (retval == 1) {
					responseDTO.addMessages(retstr);
				} else
				{
					responseDTO.addError(retstr);
				}

				logger.info("call LimitFeeConfigPKG.pLimitConfig ********** "+retstr+" ************ "+retval);


			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {

			logger.debug("Got Exception in   [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error");
			e.printStackTrace();

		} finally {


			try {

				if (connection != null) {

					connection.close();

				}

				if(cs!=null)
				{
					cs.close();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			/*lmtDataMap = null;
			resultJson = null;*/
		}

		return responseDTO;
	
	}

	
	
	public ResponseDTO savefeedata(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;

		Connection connection = null;

		CallableStatement cs2 = null;

		JSONObject requestJSON = requestDTO.getRequestJSON();

		String retstr = null;
		int retval=0;

		try {

			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();


		//	connection = DBConnector.getConnection();
			connection=DBUtil.getDBConnection();
			String feecode = requestJSON.getString("FeeCode");
			String feedesc = requestJSON.getString("Feedescrption");
			
			String feejson =  requestJSON.getString("FeeJson");

			String initiatorID =  requestJSON.getString("makerId");
			String productcode = requestJSON.getString("productcode");
			String application = requestJSON.getString("application");

			logger.info("feecode ["+feecode+"] feedesc ["+feedesc+"]  feejson ["+feejson+"] " +
					" makerId ["+initiatorID+"]");

			JSONArray feejsonobj = JSONArray.fromObject(feejson);
			

			String []feetxndata = new String[feejsonobj.size()];
			String []feefreqdata = new String[feejsonobj.size()];
			String []feefp = new String[feejsonobj.size()];
			String []feefpval = new String[feejsonobj.size()];
			String []feecert = new String[feejsonobj.size()];
			String []feefromval = new String[feejsonobj.size()];
			String []feetoval = new String[feejsonobj.size()];
			String []agent = new String[feejsonobj.size()];
			String []subagent = new String[feejsonobj.size()];
			String []ceva = new String[feejsonobj.size()];
			String []bank = new String[feejsonobj.size()];
			String []channel = new String[feejsonobj.size()];
			String []operators = new String[feejsonobj.size()];
			String []superagent = new String[feejsonobj.size()];
			String []vat = new String[feejsonobj.size()];
			String []thirdparty = new String[feejsonobj.size()];

			for (int i = 0; i < feejsonobj.size(); i++) {

				JSONObject fjson =  feejsonobj.getJSONObject(i);

				feetxndata[i]=fjson.getString("FeeTransaction").split("-")[0];
				feefreqdata[i]=fjson.getString("FeeFrequency");
				feefp[i]=fjson.getString("FlatPercentile");
				feefpval[i]=fjson.getString("FPValue");
				feecert[i]=fjson.getString("Criteria");
				feefromval[i]=fjson.getString("FromValue");
				feetoval[i]=fjson.getString("ToValue");
				agent[i]=fjson.getString("Agent");
				subagent[i]=fjson.getString("subAgent");
				ceva[i]=fjson.getString("Ceva");
				bank[i]=fjson.getString("Bank");
				channel[i]=fjson.getString("channel");
				operators[i]=fjson.getString("operators").split("-")[0];
				superagent[i]=fjson.getString("SuperAgent");
				vat[i]=fjson.getString("VAT");
				thirdparty[i]=fjson.getString("thirdparty");

			}


			if(connection!=null)
			{


				ArrayDescriptor arraydescriptor = ArrayDescriptor.createDescriptor("ARRTYPE",connection);

				String feeconfirmQuery = "{call LimitFeeConfigPKG.pFeeConfig(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

				cs2 = connection.prepareCall(feeconfirmQuery);

				ARRAY arfeetxndata = new ARRAY(arraydescriptor,connection,feetxndata);
				ARRAY arfeefreqdata = new ARRAY(arraydescriptor,connection,feefreqdata);
				ARRAY arfeefp = new ARRAY(arraydescriptor,connection,feefp);
				ARRAY arfeefpval = new ARRAY(arraydescriptor,connection,feefpval);
				ARRAY arfeecert = new ARRAY(arraydescriptor,connection,feecert);
				ARRAY arfeefromval = new ARRAY(arraydescriptor,connection,feefromval);
				ARRAY arfeetoval = new ARRAY(arraydescriptor,connection,feetoval);
				ARRAY arfeeagent = new ARRAY(arraydescriptor,connection,agent);
				ARRAY arfeesubagent = new ARRAY(arraydescriptor,connection,subagent);
				ARRAY arfeeceva = new ARRAY(arraydescriptor,connection,ceva);
				ARRAY arfeebank = new ARRAY(arraydescriptor,connection,bank);
				ARRAY arfeechannel = new ARRAY(arraydescriptor,connection,channel);
				ARRAY arfeeoperators = new ARRAY(arraydescriptor,connection,operators);
				
				ARRAY arsuperagent = new ARRAY(arraydescriptor,connection,superagent);
				ARRAY arvat = new ARRAY(arraydescriptor,connection,vat);
				ARRAY arthirdparty = new ARRAY(arraydescriptor,connection,thirdparty);


				cs2.setString(1,  feecode);	
				cs2.setString(2, feedesc);

				cs2.setArray(3, arfeetxndata);
				cs2.setArray(4, arfeefreqdata);
				cs2.setArray(5, arfeefp);
				cs2.setArray(6, arfeefpval);
				cs2.setArray(7,arfeecert);
				cs2.setArray(8, arfeefromval);
				cs2.setArray(9,arfeetoval);
				
				cs2.setArray(10,arfeeagent);
				cs2.setArray(11,arfeeceva);
				cs2.setArray(12,arfeebank);
				cs2.setArray(13,arfeechannel);
				cs2.setArray(14,arfeeoperators);
				
				cs2.setArray(15,arsuperagent);
				cs2.setArray(16,arvat);
				cs2.setArray(17,arfeesubagent);
				cs2.setArray(18,arthirdparty);
				
				cs2.setString(19, initiatorID);
				cs2.setString(20, productcode);
				cs2.setString(21, application);
				cs2.registerOutParameter(22, java.sql.Types.VARCHAR);
				cs2.registerOutParameter(23, java.sql.Types.NUMERIC);


				cs2.execute();

				retstr = cs2.getString(22);
				retval= cs2.getInt(23);

				logger.info("call LimitFeeConfigPKG.pLimitConfig ********** "+retstr+" ************ "+retval);

				JSONObject json = new JSONObject();
				json.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
				json.put("transCode", "productfeesettings");
				json.put("channel", "WEB");
				json.put("message", "Acknowledgment :: Limit Code "+feecode+" Created for  product code is  "+productcode);
				json.put("ip", requestJSON.getString("remoteip"));
				json.put("det1", "");
				json.put("det2", "");
				json.put("det3", "");
				
				CommonServiceDao csd=new CommonServiceDao();
				csd.auditTrailInsert(json);
				
				if (retval == 1) {
					responseDTO.addMessages(retstr);
				} else{
					responseDTO.addError(retstr);
				}



			}else
			{

				logger.warn("Connection Null Plese Check once");

			}

		} catch (Exception e) {

			logger.debug("Got Exception in   [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error");
			e.printStackTrace();

		} finally {


			try {

				if (connection != null) {

					connection.close();

				}

				if(cs2!=null)
				{
					cs2.close();

				}

				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}

		return responseDTO;
	}
	
	
	

	public ResponseDTO fetchLmtFeeGridInfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select USAGE_TYPE,LMT_FEE_CODE,LMT_FEE_DESC,INITIATOR_ID,to_char(INITIATOR_DTTM,'dd-mm-yyyy'),PRODUCT,APPLICATION,STATUS from limit_fee_master";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				String usagetype = getlmtfeeRs.getString(1);
				json = new JSONObject();
				json.put("LMT_FEE_CODE", getlmtfeeRs.getString(2));
				json.put("LMT_FEE_DESC", getlmtfeeRs.getString(3));
				json.put("INITIATOR_ID", getlmtfeeRs.getString(4));
				json.put("INITIATOR_DTTM", getlmtfeeRs.getString(5));
				json.put("PRODUCT", getlmtfeeRs.getString(6));
				json.put("APPLICATION", getlmtfeeRs.getString(7));
				json.put("STATUS", getlmtfeeRs.getString(8));
				
				logger.info("usagetype ::::::: ["+usagetype+"]");
				
				if("L".equalsIgnoreCase(usagetype))
				{
					lmtJsonArray.add(json);

				}else if("F".equalsIgnoreCase(usagetype))
				{
					
					feeJsonArray.add(json);
					
				}else
				{
					logger.info("No Uasge Type defined");
				}

			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO superagentinfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray agntJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "SELECT UBNAGENTID,ACCOUNTNAME,TELCO_TYPE,PRODUCT,STATUS,REF_NUM,EMAIL FROM SUPER_AGENT";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			agntJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				json = new JSONObject();
				json.put("UBNAGENTID", getlmtfeeRs.getString(1));
				json.put("ACCOUNTNAME", getlmtfeeRs.getString(2));
				json.put("TELCO_TYPE", getlmtfeeRs.getString(3));
				json.put("PRODUCT", getlmtfeeRs.getString(4));
				json.put("STATUS", getlmtfeeRs.getString(5));
				json.put("REF_NO", getlmtfeeRs.getString(6));
				json.put("EMAIL", getlmtfeeRs.getString(7));
				
				agntJsonArray.add(json);
				

			}

			resultJson.put("VIEW_AGNT_DATA", agntJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("SUPER_AGENT_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}

	
	public ResponseDTO AgentView(RequestDTO requestDTO) {

		logger.debug("Inside Create ProductAuth .... ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String accountNumbers = "";

		try {

			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			accountNumbers = requestJSON.getString("accNumbers");

			merchantQry = " select SAT.ACCOUNT_NUMBER,SAT.ACCOUNT_NAME,SAT.ACCOUNT_CURRENCY_CODE,SAT.BRANCH_CODE,SAT.EMAIL,SAT.MOBILE,ORGANIGATION_ID,MERCHANT_TYPE,'',"
					+ " Decode(SAT.STATUS,'C','Active','L','De-Active','B','Inactive','N','Not Authorized'),SAT.ORGANIGATION_NAME,SAT.ADDRESS_LINE_ONE,SAT.ADDRESS_LINE_TWO,SAT.LOCAL_GOVERNMENT,"
					+ " SAT.COUNTRY,SAT.STATE,SAT.DATE_OF_BIRTH,SAT.GENDER,SAT.ID_TYPE,SAT.ID_NUMBER,'',SAT.TELEPHONE_NUMBER,SAT.TELEPHONE_NUMBER,SAT.NATIONALITY,'','','',SAT.MAKER_ID,SAT.MAKER_DT,SAT.CITY, "
					+ " '',LATITUDE,LONGITUDE from ORG_MASTER SAT where SAT.ORGANIGATION_ID=? ";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, accountNumbers);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("ACCOUNTNUMBER", merchantRS.getString(1));
				resultJson.put("ACCOUNTNAME", merchantRS.getString(2));
				resultJson.put("ACCTCURRCODE", merchantRS.getString(3));
				resultJson.put("BRANCHCODE", merchantRS.getString(4));
				resultJson.put("EMAIL", merchantRS.getString(5));
				resultJson.put("MOBILE", merchantRS.getString(6));
				resultJson.put("ORGANIGATION_ID", merchantRS.getString(7));
				resultJson.put("MERCHANT_TYPE", merchantRS.getString(8));
				resultJson.put("SUBPRODUCTCODE", merchantRS.getString(9));
				resultJson.put("STATUS", merchantRS.getString(10));
				resultJson.put("MANAGERNAME", merchantRS.getString(11));
				resultJson.put("ADDRESSLINE1", merchantRS.getString(12));
				resultJson.put("ADDRESSLINE2", merchantRS.getString(13));
				resultJson.put("LOCALGOVERNMENT", merchantRS.getString(14));
				resultJson.put("COUNTRY", merchantRS.getString(15));
				resultJson.put("STATE", merchantRS.getString(16));
				resultJson.put("DOB", merchantRS.getString(17));
				resultJson.put("GENDER", merchantRS.getString(18));
				resultJson.put("ID_TYPE", merchantRS.getString(19));
				resultJson.put("ID_NUMBER", merchantRS.getString(20));
				resultJson.put("TELCO_TYPE", merchantRS.getString(21));
				resultJson.put("TELEPHONE_NUM1", merchantRS.getString(22));
				resultJson.put("TELEPHONE_NUM2", merchantRS.getString(23));
				resultJson.put("NATIONALITY", merchantRS.getString(24));
				resultJson.put("LATITUDE", merchantRS.getString(25));
				resultJson.put("LONGITUDE", merchantRS.getString(26));
				resultJson.put("AUTH_FLAG", merchantRS.getString(27));
				resultJson.put("makerId", merchantRS.getString(28));
				resultJson.put("makerDttm", merchantRS.getString(29));
				resultJson.put("CITY", merchantRS.getString(30));
				resultJson.put("CBNAGENTID", merchantRS.getString(31));
				resultJson.put("LATITUDE", merchantRS.getString(32));
				resultJson.put("LONGITUDE", merchantRS.getString(33));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);

			logger.debug("MerchantMap [" + merchantMap + "]");

			responseDTO.setData(merchantMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} catch (Exception e) {

			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeConnection(connection);
			merchantQry = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO SuperAgentView(RequestDTO requestDTO) {

		logger.debug("Inside Create ProductAuth .... ");

		HashMap<String, Object> merchantMap = null;
		String merchantQry = "";
		JSONObject resultJson = null;

		Connection connection = null;
		PreparedStatement merchantPstmt = null;
		ResultSet merchantRS = null;
		String accountNumbers = "";

		try {

			responseDTO = new ResponseDTO();
			merchantMap = new HashMap<String, Object>();
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + (connection == null) + "]");

			accountNumbers = requestJSON.getString("accNumbers");

			merchantQry = " select SAT.ACCOUNTNUMBER,SAT.ACCOUNTNAME,SAT.ACCTCURRCODE,SAT.BRANCHCODE,SAT.EMAIL,SAT.MOBILE,SAT.SCHEMEDESC,SAT.SCHEMETYPE,SAT.SUBPRODUCTCODE,"
					+ " Decode(SAT.STATUS,'A','Active','L','De-Active','B','Inactive','N','Not Authorized'),SAT.MANAGERNAME,SAT.ADDRESSLINE1,SAT.ADDRESSLINE2,(SELECT S.GOVT_NAME FROM LOCAL_GOVT_MASTER S WHERE S.GOV_CODE = SAT.LOCALGOVERNMENT),"
					+ " Decode(SAT.COUNTRY,'1','Nigeria'),(SELECT S.STATE_NAME FROM STATE_MASTER S WHERE S.STATE_CODE = SAT.STATE),SAT.DOB,SAT.GENDER,SAT.ID_TYPE,SAT.ID_NUMBER,SAT.TELCO_TYPE,SAT.TELEPHONE_NUM1,SAT.TELEPHONE_NUM2,Decode(SAT.NATIONALITY,'1','Nigerian','2','Uganda','3','Kenya'),SAT.LATITUDE,SAT.LONGITUDE,SAT.AUTH_FLAG,SAT.MAKER_ID,SAT.MAKER_DTTM,SAT.CITY, "
					+ " UBNAGENTID,PRODUCT,PRODUCT_DESC from SUPER_AGENT SAT where SAT.UBNAGENTID=? ";

			merchantPstmt = connection.prepareStatement(merchantQry);

			merchantPstmt.setString(1, accountNumbers);
			merchantRS = merchantPstmt.executeQuery();

			if (merchantRS.next()) {

				resultJson.put("ACCOUNTNUMBER", merchantRS.getString(1));
				resultJson.put("ACCOUNTNAME", merchantRS.getString(2));
				resultJson.put("ACCTCURRCODE", merchantRS.getString(3));
				resultJson.put("BRANCHCODE", merchantRS.getString(4));
				resultJson.put("EMAIL", merchantRS.getString(5));
				resultJson.put("MOBILE", merchantRS.getString(6));
				resultJson.put("SCHEMEDESC", merchantRS.getString(7));
				resultJson.put("SCHEMETYPE", merchantRS.getString(8));
				resultJson.put("SUBPRODUCTCODE", merchantRS.getString(9));
				resultJson.put("STATUS", merchantRS.getString(10));
				resultJson.put("MANAGERNAME", merchantRS.getString(11));
				resultJson.put("ADDRESSLINE1", merchantRS.getString(12));
				resultJson.put("ADDRESSLINE2", merchantRS.getString(13));
				resultJson.put("LOCALGOVERNMENT", merchantRS.getString(14));
				resultJson.put("COUNTRY", merchantRS.getString(15));
				resultJson.put("STATE", merchantRS.getString(16));
				resultJson.put("DOB", merchantRS.getString(17));
				resultJson.put("GENDER", merchantRS.getString(18));
				resultJson.put("ID_TYPE", merchantRS.getString(19));
				resultJson.put("ID_NUMBER", merchantRS.getString(20));
				resultJson.put("TELCO_TYPE", merchantRS.getString(21));
				resultJson.put("TELEPHONE_NUM1", merchantRS.getString(22));
				resultJson.put("TELEPHONE_NUM2", merchantRS.getString(23));
				resultJson.put("NATIONALITY", merchantRS.getString(24));
				resultJson.put("LATITUDE", merchantRS.getString(25));
				resultJson.put("LONGITUDE", merchantRS.getString(26));
				resultJson.put("AUTH_FLAG", merchantRS.getString(27));
				resultJson.put("makerId", merchantRS.getString(28));
				resultJson.put("makerDttm", merchantRS.getString(29));
				resultJson.put("CITY", merchantRS.getString(30));
				resultJson.put("CBNAGENTID", merchantRS.getString(31));
				resultJson.put("PRODUCT", merchantRS.getString(32));
				resultJson.put("PRODUCT_DESC", merchantRS.getString(33));

			}

			merchantPstmt.close();
			merchantRS.close();

			merchantMap.put("user_rights", resultJson);

			logger.debug("MerchantMap [" + merchantMap + "]");

			responseDTO.setData(merchantMap);

			logger.debug("Response DTO [" + responseDTO + "]");

		} catch (SQLException e) {
			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("SQLException in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} catch (Exception e) {

			responseDTO.addError("Internal Error Occured While Executing.");
			logger.debug("Exception in  Get Biller ID Details ["
					+ e.getMessage() + "]");
		} finally {
			DBUtils.closeResultSet(merchantRS);
			DBUtils.closePreparedStatement(merchantPstmt);
			DBUtils.closeConnection(connection);
			merchantQry = null;
		}
		return responseDTO;
	}
	
	
	public ResponseDTO activeDeactive(RequestDTO requestDTO) {

		Connection connection = null;
		logger.debug("Inside inserProductDetails.. ");

		
		
		CallableStatement callableStatement = null;
		String insertProductDetailsProc = "{call MERCHANTMGMTPKG.activedeactive(?,?,?,?)}";
		String Msg = "Success";
		String accNumbers="";
		try {

			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			logger.debug("Request JSON  [" + requestJSON + "]");

			accNumbers= requestJSON.getString("accNumbers");
			
			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			callableStatement = connection
					.prepareCall(insertProductDetailsProc);

			callableStatement.setString(1, accNumbers);
			callableStatement.setString(2,requestJSON.getString(CevaCommonConstants.IP));
			callableStatement.setString(3,requestJSON.getString(CevaCommonConstants.MAKER_ID));
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStatement.executeUpdate();
			Msg = callableStatement.getString(4);

		
			logger.info("response from DB..:"+Msg);
			if("SUCCESS".equals(Msg)){
				responseDTO.addMessages(Msg);
			}else{
				responseDTO.addError("Error Occured while Deactivating SuperAgent.");
				responseDTO.addError(Msg);
			}

		} catch (Exception e) {
			logger.debug("Exception in inserProductDetails [" + e.getMessage()
					+ "]");
		}

		finally {

			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {

			}
			insertProductDetailsProc = null;
			Msg = null;
		}
		return responseDTO;
	
	}
	
	public ResponseDTO SuperactiveDeactive(RequestDTO requestDTO) {

		logger.debug("Inside fetchProductInfo.. ");
		HashMap<String, Object> lmtDataMap = null;

		Connection connection = null;
		CallableStatement cstmt=null;

		JSONObject requestJSON = requestDTO.getRequestJSON();

		String retstr = null;
		int retval=0;
		String insertprod="{call agents.activedeactive(?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();


			// connection = DBConnector.getConnection();
			
			connection=DBUtil.getDBConnection();
			
			String accNumbers = requestJSON.getString("accNumbers");


			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			cstmt = connection.prepareCall(insertprod);
			cstmt.setString(1, accNumbers);
			cstmt.setString(2, requestJSON.getString(CevaCommonConstants.IP)+"");
			cstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID)+"");
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

			logger.debug("Got Exception in   [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error");
			e.printStackTrace();

		} finally {


			try {

				if (connection != null) {

					connection.close();

				}

				if(cstmt!=null)
				{
					cstmt.close();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			/*lmtDataMap = null;
			resultJson = null;*/
		}

		return responseDTO;
	}

	
	
	/*public ResponseDTO activeDeactive(RequestDTO requestDTO) {

		logger.debug("Inside activeDeactive.. ");
		HashMap<String, Object> lmtDataMap = null;

		Connection connection = null;
		CallableStatement cstmt=null;

		JSONObject requestJSON = requestDTO.getRequestJSON();

		String retstr = null;
		int retval=0;
		String insertprod="{call MERCHANTMGMTPKG.activedeactive(?,?,?,?)}";
		try {

			responseDTO = new ResponseDTO();

			lmtDataMap = new HashMap<String, Object>();


			connection = DBConnector.getConnection();
			
			//connection=DBUtil.getDBConnection();
			
			String accNumbers = requestJSON.getString("accNumbers");


			connection = connection == null ? connection = DBConnector
					.getConnection() : connection;
			
			cstmt = connection.prepareCall(insertprod);
			cstmt.setString(1, accNumbers);
			cstmt.setString(2, requestJSON.getString(CevaCommonConstants.IP)+"");
			cstmt.setString(3, requestJSON.getString(CevaCommonConstants.MAKER_ID)+"");
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);

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

			logger.debug("Got Exception in   [" + e.getMessage() + "]");
			responseDTO.addError("Internal Error");
			e.printStackTrace();

		} finally {


			try {

				if (connection != null) {

					connection.close();

				}

				if(cstmt!=null)
				{
					cstmt.close();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
*/
	public ResponseDTO parametersettingsinfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select KEY_GROUP,KEY_TYPE,KEY_ID,KEY_VALUE,STATUS,MAKER_ID,MAKER_DTTM from CONFIG_DATA WHERE KEY_GROUP='USER_DESIGNATION' AND KEY_TYPE in ('BADM','SDM','UDM','ADM')";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				json.put("KEY_GROUP", getlmtfeeRs.getString(1));
				json.put("KEY_TYPE", getlmtfeeRs.getString(2));
				json.put("KEY_ID", getlmtfeeRs.getString(3));
				json.put("KEY_VALUE", getlmtfeeRs.getString(4));
				json.put("STATUS", getlmtfeeRs.getString(5));
				json.put("MAKER_ID", getlmtfeeRs.getString(6));
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	public ResponseDTO clustersettingsinfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select CLUSTER_ID,CLUSTER_NAME,MAKER_ID from CLUSTER_TBL";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				json.put("CLUSTER_ID", getlmtfeeRs.getString(1));
				json.put("CLUSTER_NAME", getlmtfeeRs.getString(2));
				json.put("MAKER_ID", getlmtfeeRs.getString(3));
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO ParameterCreationauthviews(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;
		
		




		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");
			
			String adm=requestJSON.getString("limitDescription");
			String admname=requestJSON.getString("limitCode");
			
			System.out.println(adm+"----"+admname);

			
			
			String lmtfeeQry = "select STATUS from CONFIG_DATA WHERE KEY_GROUP='USER_DESIGNATION' AND KEY_TYPE='"+admname+"'";
			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				json.put("KEY_GROUP", (getlmtfeeRs.getString(1)).split("-")[0]);
				json.put("KEY_TYPE", (getlmtfeeRs.getString(1)).split("-")[1]);
				
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO fraudinfo(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select FRAUD_ID,FRAUD_DESC,RULE_DESC,DECODE(STATUS,'A','Active','Disable') from FRAUD_MASTER";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				json.put("FRAUD_ID", getlmtfeeRs.getString(1));
				json.put("FRAUD_DESC", getlmtfeeRs.getString(2));
				json.put("RULE_DESC", getlmtfeeRs.getString(3));
				json.put("STATUS", getlmtfeeRs.getString(4));
				
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	public ResponseDTO fraudinfodefault(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select RULE_NAME,RULE_CODE from FRAUD_CONFIG WHERE ACTION='N'";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				
				json.put("RULE_DESC", getlmtfeeRs.getString(1));
				json.put("RULE_CODE", getlmtfeeRs.getString(2));
				
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	
	
	
	public ResponseDTO fraudinfodefaultAction(RequestDTO requestDTO) {

		logger.debug(" Inside fetchProductGridInfo.. ");

		HashMap<String, Object> lmtfeeDataMap = null;
		JSONArray lmtJsonArray = null;
		JSONArray feeJsonArray = null;
		
		
		JSONObject resultJson = null;
		JSONObject json = null;

		PreparedStatement getlmtfeePstmt = null;
		ResultSet getlmtfeeRs = null;

		Connection connection = null;

		String lmtfeeQry = "select RULE_NAME,RULE_CODE from FRAUD_CONFIG WHERE ACTION='S'";



		try {
			responseDTO = new ResponseDTO();

			lmtfeeDataMap = new HashMap<String, Object>();
			lmtJsonArray = new JSONArray();
			feeJsonArray = new JSONArray();
			
			resultJson = new JSONObject();

			requestJSON = requestDTO.getRequestJSON();
			connection = DBConnector.getConnection();

			logger.debug("connection is null [" + connection == null + "]");

			getlmtfeePstmt = connection.prepareStatement(lmtfeeQry);

			getlmtfeeRs = getlmtfeePstmt.executeQuery();

			while (getlmtfeeRs.next()) {

				
				json = new JSONObject();
				
				json.put("RULE_DESC", getlmtfeeRs.getString(1));
				json.put("RULE_CODE", getlmtfeeRs.getString(2));
				
							
				lmtJsonArray.add(json);
				feeJsonArray.add(json);
			}

			resultJson.put("VIEW_LMT_DATA", lmtJsonArray);
			resultJson.put("VIEW_FEE_DATA", feeJsonArray);
			
			logger.info("Final Json Object ["+resultJson+"]");
			
			lmtfeeDataMap.put("LMT_FEE_INFO", resultJson);
			logger.debug("Limit Fee DataMap    [" + lmtfeeDataMap + "]");
			responseDTO.setData(lmtfeeDataMap);


		} catch (Exception e) {
			logger.debug("Got Exception in View Product Details ["
					+ e.getMessage() + "]");
		} finally {
			
			try {

				if (getlmtfeeRs != null) {
					getlmtfeeRs.close();
				}

				if (getlmtfeePstmt != null) {
					getlmtfeePstmt.close();
				}

				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			lmtfeeDataMap = null;
			resultJson = null;
		}

		return responseDTO;
	}
	

	public static void main(String[] args) {


		try
		{

			//new LimitDao().fetchLimitInfo(new RequestDTO());

			//new LimitDao().savelimitfeedata(new RequestDTO());

			new LimitDao().fetchLmtFeeGridInfo(new RequestDTO());

		}catch(Exception e)
		{

			e.printStackTrace();

		}

	}

}
