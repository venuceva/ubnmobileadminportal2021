package com.ceva.base.common.product.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.base.service.utils.dao.CommonServiceDao;
import com.ceva.unionbank.channel.ServiceRequestClient;
import com.ceva.util.DBUtils;

	public class ProductManDAO {

		private Logger logger = Logger.getLogger(ProductManDAO.class);

		ResponseDTO responseDTO = null;
		JSONObject requestJSON = null;
		JSONObject responseJSON = null;

		public ResponseDTO fetchBinGridDetails(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside fetchBinGridDetails.. ");

			HashMap<String, Object> binMap = null;
			JSONObject resultJson = null;

			JSONArray binCreJsonArray1 = null;

			ArrayList<String> binArray = null;
			ArrayList<String> binGrpArray = null;

			PreparedStatement binPstmt = null;
			ResultSet binRS = null;

			JSONObject json = null;

			String binQry = "select BIN,BIN_DESC from BIN order by BIN";
			try {

				responseDTO = new ResponseDTO();
				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");

				binMap = new HashMap<String, Object>();
				resultJson = new JSONObject();
				binCreJsonArray1 = new JSONArray();

				binArray = new ArrayList<String>();
				binGrpArray = new ArrayList<String>();
				binPstmt = connection.prepareStatement(binQry);
				binRS = binPstmt.executeQuery();

				json = new JSONObject();
				while (binRS.next()) {
					json.put(CevaCommonConstants.BIN, binRS.getString(1));
					json.put(CevaCommonConstants.BIN_DESC, binRS.getString(2));

					binArray.add(binRS.getString(1));
					binCreJsonArray1.add(json);
					json.clear();
				}

				binRS.close();
				binPstmt.close();

				resultJson.put(CevaCommonConstants.BIN_INFO, binCreJsonArray1);
				binCreJsonArray1.clear();

				for (int i = 0; i < binArray.size(); i++) {

					String binGrpQry = "select BIN_GRP_CODE,BIN_GRP_DESC from bin_group where  BIN=? order by BIN";

					binPstmt = connection.prepareStatement(binGrpQry);
					binPstmt.setString(1, binArray.get(i));
					binRS = binPstmt.executeQuery();

					while (binRS.next()) {
						json.put(CevaCommonConstants.BIN_GROUP_CODE,
								binRS.getString(1));
						json.put(CevaCommonConstants.BIN_GRP_DESC,
								binRS.getString(2));

						binGrpArray.add(binRS.getString(1));
						binCreJsonArray1.add(json);
						json.clear();
					}
					if (binArray != null && binArray.size() > 0) {
						resultJson.put(binArray.get(i) + "_BINGROUP",
								binCreJsonArray1);

					}

					binPstmt.close();
					binRS.close();
				}

				json.clear();
				binArray.clear();
				binCreJsonArray1.clear();

				String productQry = "select  PRD_CODE,PRD_NAME,PRD_CCY from  product  where BIN_GRP_CODE=? order by PRD_CODE";

				for (int i = 0; i < binGrpArray.size(); i++) {

					binPstmt = connection.prepareStatement(productQry);
					binPstmt.setString(1, binGrpArray.get(i));
					binRS = binPstmt.executeQuery();

					while (binRS.next()) {
						json.put("productCode", binRS.getString(1));
						json.put("productDesc", binRS.getString(2));
						
						json.put("binCurrency", binRS.getString(3));
					
						binCreJsonArray1.add(json);
						json.clear();
					}

					binPstmt.close();
					binRS.close();

					if (binGrpArray != null && binGrpArray.size() > 0) {
						resultJson.put(binGrpArray.get(i) + "_PRODUCTS",
								binCreJsonArray1);
						binCreJsonArray1.clear();
					}

				}

				binMap.put(CevaCommonConstants.BIN_LIST, resultJson);

				logger.debug("Bin Map  [" + binMap + "]");
				responseDTO.setData(binMap);

			} catch (Exception e) {
				logger.debug("Got Exception in fetchBinGridDetails ["
						+ e.getMessage() + "]");
				e.printStackTrace();
			} finally {

				DBUtils.closePreparedStatement(binPstmt);
				DBUtils.closeResultSet(binRS);
				DBUtils.closeConnection(connection);

				binMap = null;
				resultJson = null;
				binCreJsonArray1 = null;

				binArray = null;
				binGrpArray = null;
			}
			return responseDTO;
		}

		public ResponseDTO fetchProductCreatePageInfo(RequestDTO requestDTO) {

			logger.debug("Inside FetchProductCreatePageInfo.. ");
			HashMap<String, Object> binMap = null;
			JSONObject resultJson = null;
			JSONObject json = null;

			String productTextData;

			Connection connection = null;
			PreparedStatement binPstmt = null;
			PreparedStatement binPstmt1 = null;
			ResultSet binRs = null;
			ResultSet rs1 = null;
			ResultSet rs = null;


			ArrayList<String> binlist = new ArrayList<String>();
			ArrayList<String> bingrplist = new ArrayList<String>();

			JSONArray plasticArray = null;
			int resCount = 0;
			try {
				json = new JSONObject();

				responseDTO = new ResponseDTO();

				binMap = new HashMap<String, Object>();
				resultJson = new JSONObject();
				plasticArray = new JSONArray();

				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");

				
				
				String countryCodeQry = "select  COUNTRY_CODE,COUNTRY_CODE||'-'||COUNTRY_NAME from COUNTRY_MASTER";
				binPstmt = connection.prepareStatement(countryCodeQry);
				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("COUNTRY_CODE", json);
				json.clear();
				binPstmt.close();
				binRs.close();
				/*  */
				/* String limitCodeQry = "select LMT_FEE_CODE,LMT_FEE_DESC from limit_fee_master where USAGE_TYPE='L'";
				binPstmt = connection.prepareStatement(limitCodeQry);
				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("LIMIT_CODE", json);
				json.clear();
				binPstmt.close();
				binRs.close();
				
			    String feeCodeQry = "select LMT_FEE_CODE,LMT_FEE_DESC from limit_fee_master where USAGE_TYPE='F'";
				binPstmt = connection.prepareStatement(feeCodeQry);
				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("FEE_CODE", json);
				json.clear();
				binPstmt.close();
				binRs.close();*/
				
				/*  */
				
			/*	String issuanceTempId="select key_id,key_value from config_data where KEY_GROUP='TEMPLATE' and  KEY_TYPE='ISSUANCE'";
				binPstmt = connection.prepareStatement(issuanceTempId);
				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(2), binRs.getString(2));
				}
				resultJson.put("ISSUANCE_ID", json);
				json.clear();
				binPstmt.close();
				binRs.close();*/
				
				String currencyCodeQry = "select CRCY_CODE,CRCY_CODE||'-'||CRCY_DESC from CURRENCY_MASTER WHERE CRCY_CODE='NGN'";

				binPstmt = connection.prepareStatement(currencyCodeQry);

				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("CURRENCY_CODE", json);
				json.clear();
				
				
				String applicationCodeQry = "select KEY_VALUE,KEY_VALUE from CONFIG_DATA WHERE key_group='APPLICATION'";

				binPstmt = connection.prepareStatement(applicationCodeQry);

				binRs = binPstmt.executeQuery();
				while (binRs.next()) {
					json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("APPLICATION_CODE", json);
				json.clear();

				/*JSONObject bingroups = new JSONObject();
				String bingrpQry = "select BIN_GRP_CODE,BIN_GRP_DESC from bin_group where BIN=?";
				binPstmt = connection.prepareStatement(bingrpQry);

				for (String bin : binlist) {
					logger.debug("getting groups for Bin..:" + bin);
					binPstmt.setString(1, bin);
					rs = binPstmt.executeQuery();
					while (rs.next()) {
						plasticArray.add(rs.getString(1) + "-" + rs.getString(2));
						bingrplist.add(rs.getString(1));
					}
					bingroups.put(bin, plasticArray);
					plasticArray.clear();
				}

				binPstmt.close();
				rs.close();*/
				

				JSONObject plasticgroups = new JSONObject();

				String plasticQry = "select b.BIN_GRP_CODE,PLASTIC_CODE from bin_group b where b.BIN_GRP_CODE=?";
				String plasticChkQuery = " select substr(c.key_value,1,instr(c.key_value,'-',1,1)-1)||'-'||substr(c.key_value,instr(c.key_value,'-',-1,1)+1)"
						+ "from config_data c where (substr(c.key_value,1,instr(c.key_value,'-',1,1)-1))=?";
				binPstmt = connection.prepareStatement(plasticQry);

				for (String binGrpCode : bingrplist) {
					logger.debug("Getting groups for binGrpCode..:" + binGrpCode);

					binPstmt.setString(1, binGrpCode);
					rs = binPstmt.executeQuery();
					if (rs.next()) {
						for (String str : Arrays.asList(rs.getString(2).split(","))) {
							binPstmt1 = connection
									.prepareStatement(plasticChkQuery);
							binPstmt1.setString(1, str.trim());
							rs1 = binPstmt1.executeQuery();

							if (rs1.next()) {
								plasticArray.add(rs1.getString(1));
							}

							binPstmt1.close();
							rs1.close();
						}

						// array.add(object);
					}
					plasticgroups.put(binGrpCode, plasticArray);
					plasticArray.clear();
				}

				binPstmt.close();
			//	rs.close();
				// rs1.close();

				String bingrpQry1 = "select count(*) from product";

				binPstmt = connection.prepareStatement(bingrpQry1);
				rs = binPstmt.executeQuery();

				if (rs.next()) {
					resCount = rs.getInt(1);
				}

				logger.debug("Res Count is [" + resCount + "]");

				productTextData = "PRD"
						+ StringUtils.leftPad(String.valueOf(++resCount), 3, "0");
				logger.debug("Product Text [" + productTextData + "]");

				resultJson.put("productText", productTextData.trim());
				// resultJson.put("BINGROUPS", bingroups);
				resultJson.put("PLASTICGROUPS", plasticgroups);

				json.clear();
				binPstmt.close();
				binRs.close();

				binMap.put(CevaCommonConstants.BIN_INFO, resultJson);

				logger.debug("ProductManagement [" + binMap + "]");

				responseDTO.setData(binMap);

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Got Exception in ProductManDao [" + e.getMessage()
						+ "]");
			} finally {
				try {

					if (binRs != null) {
						binRs.close();
					}
					if (binPstmt != null) {
						binPstmt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					 
				}
				binMap = null;
				resultJson = null;
			}

			return responseDTO;
		}

		/* -- */

		 

		public ResponseDTO inserProductDetails(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside inserProductDetails.. ");

			String productText = null;
			String productDesc = null;
			
			String programCode = null;
			String binCurrency = null;
			String binGroup    = null;
			String expPeriod   = null;
			String plasticCode = null;
			String issuanceTemlateId = null;
			String serviceCode  = null;
			String issuingCountry = null;
			String limitCode   = null;
			String feeCode     = null;
			String application     = null;
			String tokenlimit     = null;
			String perdaylimit     = null;
			String feename     = null;
			String channelperdaylimit     = "";
			String ussdinilmt     = "";
			String ussdsecfalmt     = "0";
			String agenttype     = "";
			String ussdinitallmt     = "0";
		
			
			
			CallableStatement callableStatement = null;
			String insertProductDetailsProc = "{call ProductInsertProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			String Msg = "";
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON  [" + requestJSON + "]");
				
				JSONArray branchArr =  requestJSON.getJSONArray("FINAL_JSON");
				
				
				for (int i = 0; i < branchArr.size(); i++) {

					JSONObject reqData = branchArr.getJSONObject(i);
					channelperdaylimit=channelperdaylimit+""+reqData.getString("channellmt")+"#"+reqData.getString("cperdaylmt")+"|";
					
				}
				
				productText = requestJSON.getString("productText").trim();
				productDesc = requestJSON.getString("productDesc");
							
			
				programCode = requestJSON.containsKey("programCode")?requestJSON.getString("programCode"):"";
				binCurrency = requestJSON.containsKey("binCurrency")?requestJSON.getString("binCurrency"):"";
				binGroup = requestJSON.containsKey("binGroup")?requestJSON.getString("binGroup").trim():"";
				expPeriod = requestJSON.containsKey("expPeriod")?requestJSON.getString("expPeriod"):"";
				plasticCode = requestJSON.containsKey("plasticCode")?requestJSON.getString("plasticCode"):"";
				issuanceTemlateId = requestJSON.containsKey("issuanceTemlateId")?requestJSON.getString("issuanceTemlateId").split("-")[0]:"";
				serviceCode = requestJSON.containsKey("serviceCode")?requestJSON.getString("serviceCode"):"";
				issuingCountry = requestJSON.containsKey("issuingCountry")?requestJSON.getString("issuingCountry"):"";
				limitCode = requestJSON.containsKey("limitCode")?requestJSON.getString("limitCode"):"";
				feeCode = requestJSON.containsKey("feeCode")?requestJSON.getString("feeCode"):"";
				application = requestJSON.containsKey("application")?requestJSON.getString("application"):"";
				tokenlimit = requestJSON.containsKey("tokenlimit")?requestJSON.getString("tokenlimit"):"";
				perdaylimit = requestJSON.containsKey("perdaylimit")?requestJSON.getString("perdaylimit"):"";
				feename=requestJSON.containsKey("feename")?requestJSON.getString("feename"):"";
				
				ussdinilmt = requestJSON.containsKey("ussdinilmt")?requestJSON.getString("ussdinilmt"):"";
				if((requestJSON.getString("application")).equals("Mobile Banking")) {
					ussdsecfalmt = requestJSON.containsKey("ussdsecfalmt")?requestJSON.getString("ussdsecfalmt"):"";
					ussdinitallmt = requestJSON.containsKey("ussdinilmt")?requestJSON.getString("ussdinitallmt"):"";
					ussdinilmt ="0";
				}
				
				if((requestJSON.getString("application")).equals("Agent")) {
					ussdsecfalmt = requestJSON.containsKey("ussdsecfalmt")?requestJSON.getString("ussdsecfalmt"):"";
					ussdinitallmt = requestJSON.containsKey("ussdinilmt")?requestJSON.getString("ussdinitallmt"):"";
					ussdinilmt ="0";
				}
				
				
				agenttype = requestJSON.containsKey("agent")?requestJSON.getString("agent"):"";
				
				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				callableStatement = connection
						.prepareCall(insertProductDetailsProc);

				callableStatement.setString(1, productText);
				callableStatement.setString(2, productDesc);
				callableStatement.setString(3, programCode);
				callableStatement.setString(4, binCurrency);
				callableStatement.setString(5, binGroup);
				callableStatement.setString(6, expPeriod);
				callableStatement.setString(7, plasticCode);
				callableStatement.setString(8, issuanceTemlateId);
				callableStatement.setString(9, serviceCode);
				callableStatement.setString(10, issuingCountry);
				callableStatement.setString(11, limitCode);
				callableStatement.setString(12, feeCode);
				callableStatement.setString(13, tokenlimit);
				callableStatement.setString(14, perdaylimit);
				callableStatement.setString(15, channelperdaylimit);
				callableStatement.setString(16,
						requestJSON.getString(CevaCommonConstants.MAKER_ID));
				callableStatement.setString(17, application);
				callableStatement.setString(18, agenttype);
				callableStatement.setString(19, ussdinilmt);
				callableStatement.setString(20, ussdsecfalmt);
				callableStatement.setString(21, ussdinitallmt);
				callableStatement.setString(22, feename);
				
				callableStatement.registerOutParameter(23, java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(24, java.sql.Types.VARCHAR);

				callableStatement.executeUpdate();
				int resCnt = callableStatement.getInt(23);
				Msg = callableStatement.getString(24);

				logger.debug("ResultCnt from DB [" + resCnt + "]");
				logger.debug("Msg [" + Msg + "]");

				 
				if (resCnt == 1) {
					responseDTO.addMessages(Msg);
				} else if (resCnt == -1) {
					responseDTO.addError(Msg);
				} else {
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

		
		public ResponseDTO authProductSearch(RequestDTO requestDTO) {
			
			responseDTO=new ResponseDTO();
			requestJSON=new JSONObject();
			responseJSON=new JSONObject();
			
			logger.debug("inside [AgentDAO][authProductSearch]");

			logger.debug("Inside  authProductSearch.... ");

			HashMap<String, Object> serviceDataMap = null;
			JSONObject resultJson = null;
			JSONArray IncomeMTFilesJSONArray = null;

			Connection connection = null;

			PreparedStatement servicePstmt = null;

			ResultSet serviceIdRS = null;
			ResultSet serviceRS = null;

			try {
				responseDTO = new ResponseDTO();
				responseJSON = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();

				connection = DBConnector.getConnection();

				logger.debug("Connection is null [" + connection == null + "]");

				serviceDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();
				IncomeMTFilesJSONArray = new JSONArray();
				
				String userReport="";
				String branchid="";
				String actiontype=requestJSON.getString("actiontype");
				String status=requestJSON.getString("status");
				String makerid=requestJSON.getString(CevaCommonConstants.MAKER_ID);
				
				servicePstmt = connection.prepareStatement("SELECT A.CLUSTER_ID FROM USER_INFORMATION A,USER_LOGIN_CREDENTIALS B  WHERE A.common_id=B.common_id and B.LOGIN_USER_ID='"+makerid+"'");
				serviceRS = servicePstmt.executeQuery();
				while(serviceRS.next())
				{
					branchid=serviceRS.getString(1);
				}
				servicePstmt.close();
				serviceRS.close();
					
				if(status.equalsIgnoreCase("PENDING") || status.equalsIgnoreCase("N")){
					if(actiontype.equalsIgnoreCase("PRDNEWAUTH")){
						userReport ="select  REF_NO,PRD_CODE,PRD_NAME,'New Product' FROM PRODUCT_TEMP P,AUTH_PENDING AP WHERE P.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDNEWAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";
						
					}else if(actiontype.equalsIgnoreCase("PRDMODAUTH")){
						userReport =" select  REF_NO,PRD_CODE,PRD_NAME,'Modify Product' FROM PRODUCT_TEMP P,AUTH_PENDING AP WHERE P.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDMODAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";		
					}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
						userReport =" select  REF_NO,PRD_CODE,PRD_NAME,DECODE(P.STATUS,'A','Active','Deactive') FROM PRODUCT_TEMP P,AUTH_PENDING AP WHERE P.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDACTAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";		
					}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
						userReport =" select  REF_NO,LMT_FEE_CODE,LMT_FEE_DESC,'New Limit' FROM LIMIT_FEE_MASTER_TEMP LFM,AUTH_PENDING AP WHERE USAGE_TYPE='L' AND LFM.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTNEWAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";		
					}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
						userReport =" select  distinct LFD.REF_NO,LFM.LMT_FEE_CODE,LFM.LMT_FEE_DESC,'Modify Limit' FROM LIMIT_FEE_MASTER LFM,AUTH_PENDING AP,LIMIT_FEE_DETAILS_TEMP LFD WHERE USAGE_TYPE='L' AND LFD.REF_NUM=LFM.REF_NUM AND LFD.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTMODAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";	
					}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
						userReport =" select  REF_NO,LMT_FEE_CODE,LMT_FEE_DESC,'New Fee' FROM LIMIT_FEE_MASTER_TEMP LFM,AUTH_PENDING AP WHERE USAGE_TYPE='F' AND LFM.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEENEWAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";	
					}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
						userReport ="select  distinct LFD.REF_NO,LFM.LMT_FEE_CODE,LFM.LMT_FEE_DESC,'Modify Fee' FROM LIMIT_FEE_MASTER LFM,AUTH_PENDING AP,LIMIT_FEE_DETAILS_TEMP LFD WHERE USAGE_TYPE='F' AND LFD.REF_NUM=LFM.REF_NUM AND LFD.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEEMODAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";			
					}else if(actiontype.equalsIgnoreCase("FEEACTAUTH")){
						userReport =" select  REF_NO,LMT_FEE_CODE,LMT_FEE_DESC,'Change Status' FROM LIMIT_FEE_MASTER_TEMP LFM,AUTH_PENDING AP WHERE USAGE_TYPE='F' AND LFM.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEEACTAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";			
					}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
						userReport =" select  AP.REF_NUM,BVN,F_NAME,'Account Open' FROM ACCOUNT_OPEN_WEB AOB,AUTH_PENDING AP WHERE AOB.AUTH_REFERENCENO=AP.REF_NUM AND AP.AUTH_CODE='ACCOUNTAUTH' AND AP.STATUS='P' AND AP.MAKER_BRCODE='"+branchid+"'";			
					}else if(actiontype.equalsIgnoreCase("LPOSTAUTH")){
						userReport =" select  REF_NO,LOYALTY_CODE,LOYALTY_DESC,'New Loyalty Setting' FROM LOYALTY_MASTER_TEMP";		
					}else if(actiontype.equalsIgnoreCase("LPOSTMODAUTH")){
						userReport =" select  REF_NO,LOYALTY_CODE,LOYALTY_DESC,'Modify Loyalty Setting' FROM LOYALTY_MASTER_TEMP";		
					}else if(actiontype.equalsIgnoreCase("LYLNEWAUTH")){
						userReport =" select  REF_NO,LOYALTY_CODE,LOYALTY_DESC,'New Assign Loyalty' FROM LOYALTY_MASTER_TEMP";		
					}else if(actiontype.equalsIgnoreCase("LYLMODAUTH")){
						userReport =" select  REF_NO,LOYALTY_CODE,LOYALTY_DESC,'Modify Assign Loyalty' FROM LOYALTY_MASTER_TEMP";		
					}
					servicePstmt = connection.prepareStatement(userReport);
					serviceRS = servicePstmt.executeQuery();
					JSONObject json = new JSONObject();
					
					 while(serviceRS.next())
						{
						 json=new JSONObject();
						 json.put("REF_NO",serviceRS.getString(1));
						 json.put("PRD_CODE",serviceRS.getString(2));
						 json.put("PRD_NAME",serviceRS.getString(3));
						 json.put("REQUEST_TYPE",serviceRS.getString(4));
						 IncomeMTFilesJSONArray.add(json);
						}
						
					
				}else{
					if(status.equalsIgnoreCase("AUTHORIZED") || status.equalsIgnoreCase("C")){
					
					if(actiontype.equalsIgnoreCase("PRDNEWAUTH")){
						userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDNEWAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
						
					}else if(actiontype.equalsIgnoreCase("PRDMODAUTH")){
						userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDMODAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
		
					}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
						userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDACTAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
		
					}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
						userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTNEWAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
						
					}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
						userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTMODAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
		
					}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
						userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEENEWAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
						
					}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
						userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEEMODAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
		
					}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
						userReport =" select  URH.BVN,URH.CUST_STATUS||'-'||DECODE(URH.STATUS,'C','Success','F','Failed'),AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM ACCOUNT_OPEN_WEB URH,AUTH_PENDING AP WHERE URH.AUTH_REFERENCENO=AP.REF_NUM AND AP.AUTH_CODE='ACCOUNTAUTH' AND AP.STATUS='C' AND AP.MAKER_BRCODE='"+branchid+"'";
						
					}
					
					}else if(status.equalsIgnoreCase("REJECTED") || status.equalsIgnoreCase("R")){
						
						if(actiontype.equalsIgnoreCase("PRDNEWAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDNEWAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("PRDMODAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDMODAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDACTAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTNEWAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTMODAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEENEWAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEEMODAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
							userReport =" select  URH.BVN,URH.CUST_STATUS||'-'||DECODE(URH.STATUS,'C','Success','F','Failed'),AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM ACCOUNT_OPEN_WEB URH,AUTH_PENDING AP WHERE URH.AUTH_REFERENCENO=AP.REF_NUM AND AP.AUTH_CODE='ACCOUNTAUTH' AND AP.STATUS='R' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}
					}else if(status.equalsIgnoreCase("DELETED") || status.equalsIgnoreCase("D")){
						
						if(actiontype.equalsIgnoreCase("PRDNEWAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDNEWAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("PRDMODAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDMODAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("PRDACTAUTH")){
							userReport =" select  URH.PRD_CODE,URH.PRD_NAME,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM PRODUCT_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='PRDACTAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("LMTNEWAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTNEWAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("LMTMODAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='LMTMODAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("FEENEWAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEENEWAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}else if(actiontype.equalsIgnoreCase("FEEMODAUTH")){
							userReport =" select  URH.LMT_FEE_CODE,URH.LMT_FEE_DESC,AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,URH.PRD_CCY,AP.REF_NUM FROM LIMIT_FEE_MASTER_HIST URH,AUTH_PENDING AP WHERE URH.REF_NO=AP.REF_NUM AND AP.AUTH_CODE='FEEMODAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
			
						}else if(actiontype.equalsIgnoreCase("ACCOUNTAUTH")){
							userReport =" select  URH.BVN,URH.CUST_STATUS||'-'||DECODE(URH.STATUS,'C','Success','F','Failed'),AP.MAKER_ID,AP.MAKER_DTTM,AP.CHECKER_ID,AP.CHECKER_DTTM,'Approval',AP.REF_NUM FROM ACCOUNT_OPEN_WEB URH,AUTH_PENDING AP WHERE URH.AUTH_REFERENCENO=AP.REF_NUM AND AP.AUTH_CODE='ACCOUNTAUTH' AND AP.STATUS='D' AND AP.MAKER_BRCODE='"+branchid+"'";
							
						}
					}
					servicePstmt = connection.prepareStatement(userReport);
					serviceRS = servicePstmt.executeQuery();
					JSONObject json = new JSONObject();
					
					 while(serviceRS.next())
						{
						 json=new JSONObject();
						 json.put("USER_ID",serviceRS.getString(1));
						 json.put("MOBILE_NO",serviceRS.getString(2));
						 json.put("MAKER_ID",serviceRS.getString(3));
						 json.put("MAKER_DTTM",serviceRS.getString(4));
						 json.put("CHECKER_ID",serviceRS.getString(5));
						 json.put("CHECKER_DTTM",serviceRS.getString(6));
						 json.put("MESSAGE",serviceRS.getString(7));
						 json.put("REFNO",serviceRS.getString(8));
						 IncomeMTFilesJSONArray.add(json);
						}
				}
					
				
				

				

				resultJson.put("Files_List", IncomeMTFilesJSONArray);

				serviceDataMap.put("Files_List", resultJson);

				//logger.debug("ServiceDataMap [" + serviceDataMap + "]");
				responseDTO.setData(serviceDataMap);

			} catch (SQLException e) {
				logger.debug("SQLException in authProductSearch [" + e.getMessage()
						+ "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} catch (Exception e) {

				logger.debug("SQLException in authProductSearch [" + e.getMessage()
						+ "]");
				responseDTO.addError("Internal Error Occured While Executing.");
			} finally {
				try {

					if (servicePstmt != null)
						servicePstmt.close();
					if (serviceIdRS != null)
						serviceIdRS.close();
					if (serviceRS != null)
						serviceRS.close();
					if (connection != null)
						connection.close();

				} catch (SQLException e) {

				}
				serviceDataMap = null;
				resultJson = null;
				IncomeMTFilesJSONArray.clear();
				IncomeMTFilesJSONArray = null;
			}

			return responseDTO;
		}
		
		
		
		public ResponseDTO fetchProductGridInfo(RequestDTO requestDTO) {

			logger.debug(" Inside fetchProductGridInfo.. ");
			
			HashMap<String, Object> binDataMap = null;
			JSONArray binJsonArray = null;
			 
			JSONObject resultJson = null;
			JSONObject json = null;
			
			PreparedStatement getBinPstmt = null;
			ResultSet getBinRs = null;
			
			Connection connection = null;

			String entityQry = "select  p.PRD_CODE,p.PRD_NAME,p.PRD_CCY,p.APPLICATION,decode(p.STATUS,'A','Active','L','Deactivate') from  product p,PRD_DETAILS pd where p.PRD_CODE=pd.PRD_CODE  order by PRD_CODE";
			
			try {
				responseDTO = new ResponseDTO();

				binDataMap = new HashMap<String, Object>();
				binJsonArray = new JSONArray();
				resultJson = new JSONObject();
			 
				requestJSON = requestDTO.getRequestJSON();
				connection = DBConnector.getConnection();

				logger.debug("connection is null [" + connection == null + "]");

				getBinPstmt = connection.prepareStatement(entityQry);

				getBinRs = getBinPstmt.executeQuery();
				
				json = new JSONObject();

			 
				while (getBinRs.next()) {
					json.put("productCode", getBinRs.getString(1));
					json.put("productDesc", getBinRs.getString(2));
					json.put("binCurrency", getBinRs.getString(3));
					json.put("application", getBinRs.getString(4));
					json.put("prostatus", getBinRs.getString(5));
				
					
					binJsonArray.add(json);
				}
			    json.clear();
				getBinPstmt.close();
				getBinRs.close();
				resultJson.put("VIEW_PRODUCT", binJsonArray);
				binDataMap.put(CevaCommonConstants.BIN_INFO, resultJson);
				logger.debug("BinDataMap    [" + binDataMap + "]");
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				logger.debug("Got Exception in View Product Details ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (getBinRs != null) {
						getBinRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}
		
		

		public ResponseDTO fetchProductViewInfo(RequestDTO requestDTO) {

			logger.debug(" Inside fetchBinInfo.. ");
			HashMap<String, Object> binDataMap = null;
			 
			JSONObject resultJson = null;
			PreparedStatement getBinPstmt = null;
			ResultSet getBinRs = null;
			PreparedStatement getPstmt = null;
			ResultSet getRs = null;
			Connection connection = null;

			 // String entityQry ="select  PRD_CODE,PRD_NAME,PRD_CCY,PRG_CODE,BIN_GRP_CODE,ISSU_CNTRY_CODE,PLASTIC_CODE from  product  where PRD_CODE=? ";

			 /*String entityQry = "select b.PRD_CODE,b.PRD_NAME,b.PRD_CCY,b.PRG_CODE,b.BIN_GRP_CODE,b.ISSU_CNTRY_CODE,b.PLASTIC_CODE,substr(c.key_value,instr(c.key_value,'-',-1,1)+1)"
					+ "from product b, config_data c where b.PRD_CODE=?"
					+ "and b.PLASTIC_CODE=(substr(c.key_value,1,instr(c.key_value,'-',1,1)-1)) order by b.PRD_CODE";*/
			
			 //String entityQry = "select  PRD_CODE,PRD_NAME,PRD_CCY,PRG_CODE,BIN_GRP_CODE,ISSU_CNTRY_CODE,PLASTIC_CODE from product   where  PRD_CODE = ?";
			
			  //String entityQry = " select p.PRD_CODE,p.PRD_NAME,p.PRG_CODE,p.PRD_CCY,b.BIN,p.BIN_GRP_CODE,p.EXPIRY_PERIOD,p.PLASTIC_CODE,p.ISSU_TEMPLATE_ID,p.SERVICE_CODE,"
					            //+" p.ISSU_CNTRY_CODE  from product p,bin_group b  where  p.PRD_CODE =?  and instr(p.bin_grp_code,b.bin_grp_code)>0"; 
				
			/*String entityQry = "select p.PRD_CODE,p.PRD_NAME,(select g.prg_name from program g where g.prg_code=p.PRG_CODE),p.PRD_CCY,(select n.bin||' - '||n.bin_desc from bin n where n.bin= b.BIN)," 
					           +"p.BIN_GRP_CODE,p.EXPIRY_PERIOD,p.PLASTIC_CODE,p.ISSU_TEMPLATE_ID,p.TOKEN_VALUE,"
                               +"(select c.country_code||'-'||c.COUNTRY_NAME from country_master c where c.country_code= p.ISSU_CNTRY_CODE) "
                               +"from product p,bin_group b  where  p.PRD_CODE =?  and instr(p.bin_grp_code,b.bin_grp_code)>0";*/
			
			 String entityQry = "select p.PRD_CODE,p.PRD_NAME,p.PRD_CCY,APPLICATION,p.TOKEN_VALUE,p.PERDAY_LIMIT,p.CHANNEL_PERDAY_LIMIT,NVL(PRODUCT_TYPE,''),MAX_BALANCE,USSD_INI_AMT,USSD_SECOND_FA_AMT,FEE_CHARGE " 
                    +"from product p  where  p.PRD_CODE =? ";
			
			try {
				responseDTO = new ResponseDTO();
				binDataMap = new HashMap<String, Object>();
				 
				resultJson = new JSONObject();
				 
				requestJSON = requestDTO.getRequestJSON();
				connection = DBConnector.getConnection();

				logger.debug("connection is null [" + connection == null + "]");

				getBinPstmt = connection.prepareStatement(entityQry);

				getBinPstmt.setString(1,
						requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));

				getBinRs = getBinPstmt.executeQuery();
				 
				while (getBinRs.next()) {
					
					resultJson.put("productCode",  getBinRs.getString(1));
					resultJson.put("productDesc",  getBinRs.getString(2));
					//resultJson.put("programCode",  getBinRs.getString(3));
					resultJson.put("binCurrency",  getBinRs.getString(3));
					resultJson.put("application",  getBinRs.getString(4));
					/*resultJson.put("bin",          getBinRs.getString(5));
					resultJson.put("bingroupcode", getBinRs.getString(6));
					resultJson.put("expPeriod",    getBinRs.getString(7));
					resultJson.put("plasticCodeData",   getBinRs.getString(8));
					resultJson.put("issuanceTemlateId", getBinRs.getString(9));
					resultJson.put("serviceCode", getBinRs.getString(10));
					resultJson.put("issuingCountry",    getBinRs.getString(11));*/
					resultJson.put("tokenval",    getBinRs.getString(5));
					resultJson.put("perdaylimit",    getBinRs.getString(6));
					resultJson.put("channellimit",    getBinRs.getString(7));
					resultJson.put("agent",    getBinRs.getString(8));
					resultJson.put("ussdinilmt",    getBinRs.getString(9));
					
					resultJson.put("ussdinitallmt",    getBinRs.getString(10));
					resultJson.put("ussdsecfalmt",    getBinRs.getString(11));
					resultJson.put("feename",    getBinRs.getString(12));

				}
				getBinPstmt.close();
				getBinRs.close();
				
				 String custquery = "select COUNT(*) " 
		                    +"from MOB_CUSTOMER_MASTER  where  M_PRD_CODE =? ";
				 
				 getBinPstmt = connection.prepareStatement(custquery);

					getBinPstmt.setString(1,
							requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));

					getBinRs = getBinPstmt.executeQuery();
					 
					while (getBinRs.next()) {
						resultJson.put("custcnt",    getBinRs.getString(1));
					}
					getBinPstmt.close();
					getBinRs.close();
				/*logger.debug("PLASTIC CODE : "+resultJson.getString("plasticCodeData"));
				String htmlkey="";
				String  htmlValue="";
				String[] arr = resultJson.getString("plasticCodeData").split(",");
				
				for(int i=0;i<arr.length;i++)
				{
					String  entityQry5 ="select substr(key_value,1,instr(key_value,'-',1,1)-1)a,substr(key_value,instr(key_value,'-',-1,1)+1) b from config_data where key_group='PLASTIC' and key_type='PLASTIC_CODE' and KEY_VALUE LIKE '"+arr[i]+"%'"; 
					
					getBinPstmt = connection.prepareStatement(entityQry5);
					getBinRs = getBinPstmt.executeQuery();
					while (getBinRs.next()) 
					{
						htmlkey += getBinRs.getString(1) + ","; 
						htmlValue += getBinRs.getString(2) + ","; 
					}
				}
			 
				resultJson.put("plastickey", htmlkey.substring(0, htmlkey.length()-1));
				resultJson.put("plasticValue", htmlValue.substring(0, htmlValue.length()-1));
				getBinPstmt.close();
				getBinRs.close();
				     
				
				String htmlkey1="";
				String  htmlValue1="";
				String[] arr1 = resultJson.getString("bingroupcode").split(",");
				
				for(int i=0;i<arr1.length;i++)
				{
					//String  entityQry5 ="select substr(key_value,1,instr(key_value,'-',1,1)-1)a,substr(key_value,instr(key_value,'-',-1,1)
					//+1) b from config_data where key_group='PLASTIC' and key_type='PLASTIC_CODE' and KEY_VALUE LIKE '"+arr[i]+"%'"; 
					
					//String entityQry6 ="select BIN_GRP_CODE,BIN_GRP_DESC from bin_group  where bin_grp_code='"+arr1[i]+"%'";
					
					String entityQry6 ="select BIN_GRP_CODE,BIN_GRP_DESC from bin_group  where bin_grp_code LIKE '"+arr1[i]+"%'";
					
					getBinPstmt = connection.prepareStatement(entityQry6);
					getBinRs = getBinPstmt.executeQuery();
					while (getBinRs.next()) 
					{
						htmlkey1 += getBinRs.getString(1) + ","; 
						htmlValue1 += getBinRs.getString(1) +"-"+getBinRs.getString(2) + ","; 
					}
				}
			 
				resultJson.put("binGroupkey", htmlkey1);
				resultJson.put("binGroupValue", htmlValue1.substring(0, htmlValue1.length()-1));
				getBinPstmt.close();
				getBinRs.close();*/
				
				
				/*String feeCodeQry2 = "select l.LMT_FEE_CODE from  prd_details p, limit_fee_master l where  "
						+ "p.prd_code=? and  trunc(p.effective_date)=(select trunc(max(effective_date)) from prd_details pd "
						+ "where pd.prd_code=p.prd_code) and substr(p.usg_value,1,6)=l.LMT_FEE_CODE";
			    getBinPstmt = connection.prepareStatement(feeCodeQry2);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					resultJson.put("limitCodeVal",getBinRs.getString(1));
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				String feeCodeQry3 = "select l.LMT_FEE_CODE  from  prd_details p, limit_fee_master l where  p.prd_code=? "
						+ "and  trunc(p.effective_date)=(select trunc(max(effective_date)) from prd_details pd where pd.prd_code=p.prd_code) and "
						+ "substr(p.usg_value,instr(p.usg_value,'|',1,1)+1,6)=l.LMT_FEE_CODE";
			    getBinPstmt = connection.prepareStatement(feeCodeQry3);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					resultJson.put("feeCodeVal",getBinRs.getString(1));
				}
				getBinPstmt.close();
				getBinRs.close();*/
				
				
				String descr = "select LMT_FEE_CODE||'-'||LMT_FEE_DESC from  LIMIT_FEE_MASTER WHERE LMT_FEE_CODE=?";
				String feeCodeQry2 = "select nvl(LIMIT_CODE,'NO_DATA') from  prd_details WHERE PRD_CODE=?";
			    getBinPstmt = connection.prepareStatement(feeCodeQry2);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					if((getBinRs.getString(1)).equalsIgnoreCase("NO_DATA")){
						resultJson.put("limitCodeVal","Not yet Configure");
					}else{
						getPstmt = connection.prepareStatement(descr);
					    getPstmt.setString(1,getBinRs.getString(1));
					    getRs = getPstmt.executeQuery();
					    if(getRs.next()) {
					    	resultJson.put("limitCodeVal",getRs.getString(1));	
					    }
					    getPstmt.close();
						getRs.close();
					}
					
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				String feeCodeQry3 = "select nvl(FEE_CODE,'NO_DATA')  from  prd_details  where  prd_code=? ";
			    getBinPstmt = connection.prepareStatement(feeCodeQry3);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					if((getBinRs.getString(1)).equalsIgnoreCase("NO_DATA")){
					resultJson.put("feeCodeVal","Not yet Configure");
					}else{
						getPstmt = connection.prepareStatement(descr);
					    getPstmt.setString(1,getBinRs.getString(1));
					    getRs = getPstmt.executeQuery();
					    if(getRs.next()) {
					    	resultJson.put("feeCodeVal",getRs.getString(1));	
					    }
					    getPstmt.close();
						getRs.close();
					}
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				binDataMap.put(CevaCommonConstants.BIN_INFO, resultJson);
				logger.debug("binDataMap   [" + binDataMap + "]");
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Got Exception in View Product Details ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (getBinRs != null) {
						getBinRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}
		
		
		
		public ResponseDTO authfetchViewprd(RequestDTO requestDTO) {

			logger.debug(" Inside fetchBinInfo.. ");
			HashMap<String, Object> binDataMap = null;
			 
			JSONObject resultJson = null;
			PreparedStatement getBinPstmt = null;
			ResultSet getBinRs = null;
			Connection connection = null;

		
			
			String  entityQry = "select p.PRD_CODE,p.PRD_NAME,p.PRD_CCY,p.APPLICATION,p.REF_NO,p.TOKEN_VALUE,p.PERDAY_LIMIT,p.CHANNEL_PERDAY_LIMIT,p.FEE_CHARGE " 
                    +"from product_temp p  where  p.PRD_CODE =? AND p.REF_NO=?";
			
			try {
				responseDTO = new ResponseDTO();
				binDataMap = new HashMap<String, Object>();
				 
				resultJson = new JSONObject();
				 
				requestJSON = requestDTO.getRequestJSON();
				connection = DBConnector.getConnection();

				logger.debug("connection is null [" + connection == null + "]");

				getBinPstmt = connection.prepareStatement(entityQry);

				getBinPstmt.setString(1,
						requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
				getBinPstmt.setString(2,
						requestJSON.getString("refno"));

				getBinRs = getBinPstmt.executeQuery();
				 
				while (getBinRs.next()) {
					
					resultJson.put("productCode",  getBinRs.getString(1));
					resultJson.put("productDesc",  getBinRs.getString(2));
					resultJson.put("binCurrency",  getBinRs.getString(3));
					resultJson.put("application",  getBinRs.getString(4));
					resultJson.put("refno",  getBinRs.getString(5));
					resultJson.put("tokenval",  getBinRs.getString(6));
					resultJson.put("perdaylimit",  getBinRs.getString(7));
					resultJson.put("channellimit",  getBinRs.getString(8));
					resultJson.put("feename",  getBinRs.getString(9));
					
				
				}
				getBinPstmt.close();
				getBinRs.close();
			
				
				
				/*String feeCodeQry2 = "select l.LMT_FEE_CODE from  prd_details_temp p, limit_fee_master l where  "
						+ "p.prd_code=? and  trunc(p.effective_date)=(select trunc(max(effective_date)) from prd_details_temp pd "
						+ "where pd.prd_code=p.prd_code) and substr(p.usg_value,1,6)=l.LMT_FEE_CODE";
			    getBinPstmt = connection.prepareStatement(feeCodeQry2);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					resultJson.put("limitCodeVal",getBinRs.getString(1));
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				String feeCodeQry3 = "select l.LMT_FEE_CODE  from  prd_details_temp p, limit_fee_master l where  p.prd_code=? "
						+ "and  trunc(p.effective_date)=(select trunc(max(effective_date)) from prd_details_temp pd where pd.prd_code=p.prd_code) and "
						+ "substr(p.usg_value,instr(p.usg_value,'|',1,1)+1,6)=l.LMT_FEE_CODE";
			    getBinPstmt = connection.prepareStatement(feeCodeQry3);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					resultJson.put("feeCodeVal",getBinRs.getString(1));
				}
				getBinPstmt.close();
				getBinRs.close();*/
				
				
				binDataMap.put(CevaCommonConstants.BIN_INFO, resultJson);
				logger.debug("binDataMap   [" + binDataMap + "]");
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Got Exception in View Product Details ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (getBinRs != null) {
						getBinRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}
		
		
		
		public ResponseDTO authProductAck(RequestDTO requestDTO) {

			logger.debug(" Inside fetchBinInfo.. ");
			HashMap<String, Object> binDataMap = null;
			 
			JSONObject resultJson = null;
			PreparedStatement getBinPstmt = null;
			ResultSet getBinRs = null;
			Connection connection = null;
			String decision="";
			String Narration="";
			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			String prddesc="";
			String actiontype="";
			
		
			
			String  entityQry = "select p.PRD_CODE,p.PRD_NAME,p.PRD_CCY,p.APPLICATION,p.REF_NO,p.TOKEN_VALUE,p.PERDAY_LIMIT,p.CHANNEL_PERDAY_LIMIT,DECODE(p.STATUS,'A','Active','Deactive'),p.STATUS,NVL(USSD_INI_AMT,''),NVL(USSD_SECOND_FA_AMT,''),p.FEE_CHARGE " 
                    +" from product_temp p  where  p.PRD_CODE =? AND p.REF_NO=?";
			
			try {
				responseDTO = new ResponseDTO();
				binDataMap = new HashMap<String, Object>();
				 
				resultJson = new JSONObject();
				 
				requestJSON = requestDTO.getRequestJSON();
				connection = DBConnector.getConnection();
				decision=requestJSON.getString("decision");
				Narration=requestJSON.getString("Narration");
				actiontype=requestJSON.getString("actiontype");
				
				logger.debug("connection is null [" + connection == null + "]");

				getBinPstmt = connection.prepareStatement(entityQry);

				getBinPstmt.setString(1,
						requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
				getBinPstmt.setString(2,
						requestJSON.getString("refno"));

				getBinRs = getBinPstmt.executeQuery();
				 
				while (getBinRs.next()) {
					
					resultJson.put("productCode",  getBinRs.getString(1));
					resultJson.put("productDesc",  getBinRs.getString(2));
					resultJson.put("binCurrency",  getBinRs.getString(3));
					resultJson.put("application",  getBinRs.getString(4));
					resultJson.put("refno",  getBinRs.getString(5));
					
					resultJson.put("tokenamt",  getBinRs.getString(6));
					resultJson.put("perdaylmt",  getBinRs.getString(7));
					resultJson.put("chnlimit",  getBinRs.getString(8));
					resultJson.put("statusval",  getBinRs.getString(9));
					resultJson.put("statusval1",  getBinRs.getString(10));
					
					resultJson.put("ussdinilmt",  getBinRs.getString(11));
					resultJson.put("ussdsecfalmt",  getBinRs.getString(12));
					resultJson.put("feename",  getBinRs.getString(13));
					
					prddesc=getBinRs.getString(2);
				}
				getBinPstmt.close();
				getBinRs.close();
			
				
				
				
				
				if(decision.equalsIgnoreCase("Approval")){
					
					if(actiontype.equalsIgnoreCase("NEW_PRODUCTS")){
						
					
					
					pstmt = connection.prepareStatement("INSERT INTO PRD_DETAILS (PRD_CODE,PRD_NAME) values('"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"','"+prddesc+"')");
					pstmt.executeUpdate();
					pstmt.close();
					
					pstmt = connection.prepareStatement("INSERT INTO PRODUCT select * from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					pstmt = connection.prepareStatement("INSERT INTO PRODUCT_HIST select * from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					
					
					pstmt = connection.prepareStatement("DELETE from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product Successfully "+decision );
					}
					
					
					if(actiontype.equalsIgnoreCase("MODIFY_PRODUCTS")){
						
						
						if((resultJson.get("application")).equals("Mobile Banking")) {
							pstmt = connection.prepareStatement("UPDATE PRODUCT set TOKEN_VALUE='"+resultJson.get("tokenamt")+"',PERDAY_LIMIT='"+resultJson.get("perdaylmt")+"',CHANNEL_PERDAY_LIMIT='"+resultJson.get("chnlimit")+"',USSD_INI_AMT='"+resultJson.get("ussdinilmt")+"',USSD_SECOND_FA_AMT='"+resultJson.get("ussdsecfalmt")+"' WHERE PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"'");
							pstmt.executeUpdate();
							pstmt.close();
						}else {
							
							pstmt1 = connection.prepareStatement("UPDATE PRODUCT set TOKEN_VALUE='"+resultJson.get("tokenamt")+"',PERDAY_LIMIT='"+resultJson.get("perdaylmt")+"',CHANNEL_PERDAY_LIMIT='"+resultJson.get("chnlimit")+"'"
									+ ",FEE_CHARGE='"+resultJson.get("feename")+"'WHERE PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"'");
							pstmt1.executeUpdate();
							pstmt1.close();
						}
					
						
						
						
						pstmt = connection.prepareStatement("INSERT INTO PRODUCT_HIST select * from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						
						pstmt = connection.prepareStatement("DELETE from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product Modify Successfully "+decision );
						
					}
					
					if(actiontype.equalsIgnoreCase("ACTIVE_PRODUCTS")){
						
						
						
					
						
						pstmt = connection.prepareStatement("UPDATE PRODUCT set STATUS='"+resultJson.get("statusval1")+"'  where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("INSERT INTO PRODUCT_HIST select * from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						
						pstmt = connection.prepareStatement("DELETE from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product "+resultJson.get("statusval")+" Successfully "+decision );
						
					}
					
					pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,REASON='"+Narration+"' where REF_NUM='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					JSONObject json = new JSONObject();
					json.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
					json.put("transCode", "ApprovalAll");
					json.put("channel", "WEB");
					json.put("message", "Acknowledgment :: Product Code "+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Approval ");
					json.put("ip", requestJSON.getString("remoteip"));
					json.put("det1", "");
					json.put("det2", "");
					json.put("det3", "");
					
					CommonServiceDao csd=new CommonServiceDao();
					csd.auditTrailInsert(json);
					
					
				}else{
					if(actiontype.equalsIgnoreCase("NEW_PRODUCTS")){
						resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product Successfully "+decision );

					}
					if(actiontype.equalsIgnoreCase("MODIFY_PRODUCTS")){
						resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product Modify Successfully "+decision );
	
					}
					
					if(actiontype.equalsIgnoreCase("ACTIVE_PRODUCTS")){
						resultJson.put("decision", requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Product "+resultJson.get("statusval")+" Successfully "+decision );
	
					}
					
					pstmt = connection.prepareStatement("INSERT INTO PRODUCT_HIST select * from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					
					
					pstmt = connection.prepareStatement("DELETE from PRODUCT_TEMP where PRD_CODE='"+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+"' AND REF_NO='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
				
					
					pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='R',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,REASON='"+Narration+"' where REF_NUM='"+requestJSON.getString("refno")+"'");
					pstmt.executeUpdate();
					pstmt.close();
					
					
					
					JSONObject json = new JSONObject();
					json.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
					json.put("transCode", "ApprovalAll");
					json.put("channel", "WEB");
					json.put("message", "Acknowledgment :: Product Code "+requestJSON.getString(CevaCommonConstants.PRODUCT_CODE)+" Reject ");
					json.put("ip", requestJSON.getString("remoteip"));
					json.put("det1", "");
					json.put("det2", "");
					json.put("det3", "");
					
					CommonServiceDao csd=new CommonServiceDao();
					csd.auditTrailInsert(json);
					
				}
				
				connection.commit();
				
				
				
				
				
				binDataMap.put(CevaCommonConstants.BIN_INFO, resultJson);
				logger.debug("binDataMap   [" + binDataMap + "]");
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Got Exception in View Product Details ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (getBinRs != null) {
						getBinRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}

		public ResponseDTO authLimitAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;
			
			String  decision = "";
			String  productcode = "";
			String  Narration = "";
			String refno="";

			String  limitcode = "";
			
			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();
				productcode = requestJSON.getString("product");
				limitcode = requestJSON.getString(CevaCommonConstants. LIMIT_CODE);
				decision=requestJSON.getString("decision");
				Narration=requestJSON.getString("Narration");
				refno=requestJSON.getString("refno");

				    
				    
				    	if(decision.equalsIgnoreCase("Approval")){
				    		
				    		getlimitcodePstmt = connection.prepareStatement("select AUTH_CODE from AUTH_PENDING where REF_NUM='"+refno+"'");

							getlimitcodeRs = getlimitcodePstmt.executeQuery();
							if (getlimitcodeRs.next()) {
								if((getlimitcodeRs.getString(1)).equalsIgnoreCase("LMTNEWAUTH")){
									
									pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
									pstmt.executeUpdate();
									pstmt.close();
									
									pstmt = connection.prepareStatement("UPDATE  PRD_DETAILS set LIMIT_CODE='"+limitcode+"' WHERE PRD_CODE='"+productcode+"'");
									pstmt.executeUpdate();
									pstmt.close();
									
								}
							}
							pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_HIST select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();	
							
							pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_HIST select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();
				
							pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS where SEQ_NO in (select SEQ_NO from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"')");
							pstmt.executeUpdate();
							pstmt.close();	
								
							
							pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();
						
					
						
						
							pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();
							
							pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,ACTION='CHECKER',REASON='"+Narration+"' where REF_NUM='"+refno+"'");
							pstmt.executeUpdate();
							pstmt.close();
						
						
							JSONObject json1 = new JSONObject();
							json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
							json1.put("transCode", "ApprovalAll");
							json1.put("channel", "WEB");
							json1.put("message", "Acknowledgment :: Limit Code "+requestJSON.getString(CevaCommonConstants.LIMIT_CODE)+" Approval");
							json1.put("ip", requestJSON.getString("remoteip"));
							json1.put("det1", "");
							json1.put("det2", "");
							json1.put("det3", "");
							
							CommonServiceDao csd=new CommonServiceDao();
							csd.auditTrailInsert(json1);
						
						
					}else{
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_HIST select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_HIST select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='R',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,ACTION='CHECKER',REASON='"+Narration+"' where REF_NUM='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						JSONObject json1 = new JSONObject();
						json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
						json1.put("transCode", "ApprovalAll");
						json1.put("channel", "WEB");
						json1.put("message", "Acknowledgment :: Limit Code "+requestJSON.getString(CevaCommonConstants.LIMIT_CODE)+" Reject");
						json1.put("ip", requestJSON.getString("remoteip"));
						json1.put("det1", "");
						json1.put("det2", "");
						json1.put("det3", "");
						
						CommonServiceDao csd=new CommonServiceDao();
						csd.auditTrailInsert(json1);
						
						
					}
					
					connection.commit();
				    
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	
}

		
		public ResponseDTO authFeeAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchfeecodeInfo.. ");
			HashMap<String, Object>  feecodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getfeecodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getfeecodeRs = null;
			Connection connection = null;
			
			PreparedStatement getfeecodePstmt1 = null;
			ResultSet getfeecodeRs1 = null;
			
			String  decision = "";
			String  Narration = "";
			String refno="";
			String status="";
			String productcode="";
			
			String  feecode = "";

			

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				feecodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				 feecode = requestJSON.getString(CevaCommonConstants.FEE_CODE);
				 productcode = requestJSON.getString("product");
				 refno= requestJSON.getString("refno");
				decision=requestJSON.getString("decision");
				Narration=requestJSON.getString("Narration");

				
				    
				    
				    
				    if(decision.equalsIgnoreCase("Approval")){
				    	
				    	
				    	getfeecodePstmt = connection.prepareStatement("select AUTH_CODE from AUTH_PENDING where REF_NUM='"+refno+"'");

				    	getfeecodeRs = getfeecodePstmt.executeQuery();
						if (getfeecodeRs.next()) {
							if((getfeecodeRs.getString(1)).equalsIgnoreCase("FEENEWAUTH")){
								
								pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
								pstmt.executeUpdate();
								pstmt.close();
								
								
								pstmt = connection.prepareStatement("UPDATE  PRD_DETAILS set FEE_CODE='"+feecode+"' WHERE PRD_CODE='"+productcode+"'");
								pstmt.executeUpdate();
								pstmt.close();
								
								
								pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS where SEQ_NO in (select SEQ_NO from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"')");
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
								pstmt.executeUpdate();
								pstmt.close();
								
								
							}
							
							
							if((getfeecodeRs.getString(1)).equalsIgnoreCase("FEEMODAUTH")){
								
								pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS where SEQ_NO in (select SEQ_NO from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"')");
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
								pstmt.executeUpdate();
								pstmt.close();
								
							}
							
							if((getfeecodeRs.getString(1)).equalsIgnoreCase("FEEACTAUTH")){
								
							/*	pstmt = connection.prepareStatement("UPDATE  LIMIT_FEE_MASTER set STATUS='A' where REF_NO='"+refno+"' AND STATUS='L'");
								pstmt.executeUpdate();
								pstmt.close();*/
								
								getfeecodePstmt1 = connection.prepareStatement("select STATUS from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");

						    	getfeecodeRs1 = getfeecodePstmt1.executeQuery();
								if (getfeecodeRs1.next()) {
									
								
									
									if((getfeecodeRs1.getString(1)).equalsIgnoreCase("A")){
										pstmt = connection.prepareStatement("UPDATE  LIMIT_FEE_MASTER set STATUS='L' where LMT_FEE_CODE='"+requestJSON.getString(CevaCommonConstants.FEE_CODE)+"' AND STATUS='A'");
										pstmt.executeUpdate();
										pstmt.close();
									}
								   if((getfeecodeRs1.getString(1)).equalsIgnoreCase("L")){
										pstmt = connection.prepareStatement("UPDATE  LIMIT_FEE_MASTER set STATUS='A' where LMT_FEE_CODE='"+requestJSON.getString(CevaCommonConstants.FEE_CODE)+"'");
										pstmt.executeUpdate();
										pstmt.close();
									}
								
								}
								
							}
							
						}
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_HIST select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_HIST select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
					
					
					
						
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,REASON='"+Narration+"' where REF_NUM='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						JSONObject json1 = new JSONObject();
						json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
						json1.put("transCode", "ApprovalAll");
						json1.put("channel", "WEB");
						json1.put("message", "Acknowledgment :: Fee Code "+requestJSON.getString(CevaCommonConstants.FEE_CODE)+" Approval  ");
						json1.put("ip", requestJSON.getString("remoteip"));
						json1.put("det1", "");
						json1.put("det2", "");
						json1.put("det3", "");
						
						CommonServiceDao csd=new CommonServiceDao();
						csd.auditTrailInsert(json);
						
						
					}else{
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_HIST select * from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_HIST select * from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_MASTER_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("DELETE from LIMIT_FEE_DETAILS_TEMP where REF_NO='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='R',CHECKER_ID='"+requestJSON.getString("makerId")+"',CHECKER_DTTM=sysdate,REASON='"+Narration+"' where REF_NUM='"+refno+"'");
						pstmt.executeUpdate();
						pstmt.close();
						
						
						JSONObject json1 = new JSONObject();
						json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
						json1.put("transCode", "ApprovalAll");
						json1.put("channel", "WEB");
						json1.put("message", "Acknowledgment :: Fee Code "+requestJSON.getString(CevaCommonConstants.FEE_CODE)+" Reject  ");
						json1.put("ip", requestJSON.getString("remoteip"));
						json1.put("det1", "");
						json1.put("det2", "");
						json1.put("det3", "");
						
						CommonServiceDao csd=new CommonServiceDao();
						csd.auditTrailInsert(json);
						
						
					}
					
					connection.commit();
				    
				    
				    
				    resultJson.put("Feecodedetails2", JsonArray);
				    feecodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("feecodeDataMap[" + feecodeDataMap + "]");
				    responseDTO.setData(feecodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getfeecodeRs != null) {
						getfeecodeRs.close();
					}

					if (getfeecodePstmt != null) {
						getfeecodePstmt.close();
					}
					
					if (getfeecodeRs1 != null) {
						getfeecodeRs.close();
					}

					if (getfeecodePstmt1 != null) {
						getfeecodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				feecodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
		
			
	}


		public ResponseDTO fetchProductModifyPageInfo(RequestDTO requestDTO) {
			HashMap<String, Object> binDataMap = null;
			JSONObject resultJson = null;
			JSONObject Json = null;
			PreparedStatement getBinPstmt = null;
			ResultSet binRs = null;
			ResultSet getBinRs = null;
			ResultSet getRs = null;
			PreparedStatement getPstmt = null;

			Connection connection = null;

			
			
			String entityQry="select b.PRD_CODE,b.PRD_NAME,"
                             +"b.PRD_CCY,b.APPLICATION,b.TOKEN_VALUE,b.PERDAY_LIMIT,b.CHANNEL_PERDAY_LIMIT,MAX_BALANCE,USSD_SECOND_FA_AMT,USSD_INI_AMT,PRODUCT_TYPE,FEE_CHARGE from product b where  b.PRD_CODE=?";
			try {
				responseDTO = new ResponseDTO();
				binDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();
				
				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				
				getBinPstmt = connection.prepareStatement(entityQry);
				getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
				binRs = getBinPstmt.executeQuery();
				 
				while (binRs.next()) {

					resultJson.put("productCode", binRs.getString(1));
					resultJson.put("productDesc", binRs.getString(2));
					resultJson.put("binCurrency", binRs.getString(3));
					resultJson.put("application",  binRs.getString(4));
					
					resultJson.put("tokenval",    binRs.getString(5));
					resultJson.put("perdaylimit",    binRs.getString(6));
					resultJson.put("channellimit",    binRs.getString(7));
					resultJson.put("ussdinilmt",    binRs.getString(8));
					resultJson.put("ussdsecfalmt",    binRs.getString(9));
					resultJson.put("ussdinitallmt",    binRs.getString(10));
					resultJson.put("agent",    binRs.getString(11));
					resultJson.put("feename",    binRs.getString(12));

				}

				getBinPstmt.close();
				binRs.close();

				 
	

				
			    String limitCodeQry = "select LMT_FEE_CODE,LMT_FEE_DESC||'-'||LMT_FEE_CODE from limit_fee_master where USAGE_TYPE='L'";
			    getBinPstmt = connection.prepareStatement(limitCodeQry);
				binRs = getBinPstmt.executeQuery();
				Json = new JSONObject();
				while (binRs.next()) {
					Json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("LIMIT_CODE", Json);
				Json.clear();
				getBinPstmt.close();
				binRs.close();
				
			    String feeCodeQry = "select LMT_FEE_CODE,LMT_FEE_DESC||'-'||LMT_FEE_CODE from limit_fee_master where USAGE_TYPE='F'";
			    getBinPstmt = connection.prepareStatement(feeCodeQry);
				binRs = getBinPstmt.executeQuery();
				while (binRs.next()) {
					Json.put(binRs.getString(1), binRs.getString(2));
				}
				resultJson.put("FEE_CODE", Json);
				Json.clear();
				getBinPstmt.close();
				binRs.close();
				
			
				
				String descr = "select LMT_FEE_CODE||'-'||LMT_FEE_DESC from  LIMIT_FEE_MASTER WHERE LMT_FEE_CODE=?";
				String feeCodeQry2 = "select nvl(LIMIT_CODE,'NO_DATA') from  prd_details WHERE PRD_CODE=?";
			    getBinPstmt = connection.prepareStatement(feeCodeQry2);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					if((getBinRs.getString(1)).equalsIgnoreCase("NO_DATA")){
						resultJson.put("limitCodeVal","Not yet Configure");
					}else{
						getPstmt = connection.prepareStatement(descr);
					    getPstmt.setString(1,getBinRs.getString(1));
					    getRs = getPstmt.executeQuery();
					    if(getRs.next()) {
					    	resultJson.put("limitCodeVal",getRs.getString(1));	
					    }
					    getPstmt.close();
						getRs.close();
					}
					
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				String feeCodeQry3 = "select nvl(FEE_CODE,'NO_DATA')  from  prd_details  where  prd_code=? ";
			    getBinPstmt = connection.prepareStatement(feeCodeQry3);
			    getBinPstmt.setString(1,requestJSON.getString(CevaCommonConstants.PRODUCT_CODE));
			    getBinRs = getBinPstmt.executeQuery();
				while (getBinRs.next()) {
					if((getBinRs.getString(1)).equalsIgnoreCase("NO_DATA")){
					resultJson.put("feeCodeVal","Not yet Configure");
					}else{
						getPstmt = connection.prepareStatement(descr);
					    getPstmt.setString(1,getBinRs.getString(1));
					    getRs = getPstmt.executeQuery();
					    if(getRs.next()) {
					    	resultJson.put("feeCodeVal",getRs.getString(1));	
					    }
					    getPstmt.close();
						getRs.close();
					}
				}
				getBinPstmt.close();
				getBinRs.close();
				
				
				 
				binDataMap.put("BIN_DETAILS", resultJson);
				
				logger.debug("binDataMap[" + binDataMap + "]");
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Got Exception in fetchProductModifyPageInfo ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (binRs != null) {
						binRs.close();
					}
					if (getBinRs != null) {
						getBinRs.close();
					}
					if (getRs != null) {
						getRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}
					if (getPstmt != null) {
						getPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}

		/*
		 * public ResponseDTO fetchProductModifyPageInfo(RequestDTO requestDTO) {
		 * 
		 * 
		 * String programCodeQry =
		 * "select PRG_CODE,PRG_CODE||'-'||PRG_NAME from  PROGRAM_MASTER";
		 * 
		 * binPstmt = connection.prepareStatement(programCodeQry); binCreJsonArray1
		 * = new JSONArray(); binRs = binPstmt.executeQuery(); while (binRs.next())
		 * { json.put("key",binRs.getString(1));
		 * json.put("value",binRs.getString(2)); binCreJsonArray1.add(json); }
		 * resultJson.put("PROGRAM_CODE", binCreJsonArray1);
		 * 
		 * 
		 * String countryCodeQry =
		 * "select  COUNTRY_CODE,COUNTRY_CODE||'-'||COUNTRY_NAME from COUNTRY_MASTER"
		 * ;
		 * 
		 * binPstmt = connection.prepareStatement(countryCodeQry); binCreJsonArray1
		 * = new JSONArray(); binRs = binPstmt.executeQuery(); while (binRs.next())
		 * { json.put("key",binRs.getString(1));
		 * json.put("value",binRs.getString(2)); binCreJsonArray1.add(json); }
		 * resultJson.put("COUNTRY_CODE", binCreJsonArray1);
		 * 
		 * 
		 * JSONObject bingroups = new JSONObject(); String
		 * bingrpQry="select BIN_GRP_CODE,BIN_GRP_DESC from bin_group where BIN=?"
		 * ; binPstmt = connection.prepareStatement(bingrpQry); ResultSet rs=null;
		 * for(String bin : binlist ){
		 * logger.debug("getting groups for bin..:"+bin); JSONArray array = new
		 * JSONArray(); JSONObject object = null; binPstmt.setString(1, bin);
		 * rs=binPstmt.executeQuery(); while(rs.next()){ object = new JSONObject();
		 * object.put("key", rs.getString(1)); object.put("value", rs.getString(2));
		 * array.add(object); bingrplist.add(rs.getString(1)); } bingroups.put(bin,
		 * array); }
		 * 
		 * 
		 * 
		 * 
		 * JSONObject plasticgroups = new JSONObject(); //String plasticQry=
		 * "select BIN_GRP_CODE,PLASTIC_CODE from bin_group where BIN_GRP_CODE=?"
		 * ; String plasticQry=
		 * " select b.BIN_GRP_CODE,substr(c.key_value,1,instr(c.key_value,'-',1,1)-1)||'-'||substr(c.key_value,instr(c.key_value,'-',-1,1)+1)"
		 * +"from bin_group b, config_data c where b.BIN_GRP_CODE=?" +
		 * "and b.PLASTIC_CODE=(substr(c.key_value,1,instr(c.key_value,'-',1,1)-1)) order by b.bin"
		 * ; binPstmt = connection.prepareStatement(plasticQry); ResultSet rs1=null;
		 * for(String binGrpCode : bingrplist ){
		 * logger.debug("getting groups for binGrpCode..:"+binGrpCode); JSONArray
		 * array = new JSONArray(); JSONObject object = null; binPstmt.setString(1,
		 * binGrpCode); rs1=binPstmt.executeQuery(); while(rs1.next()){ object = new
		 * JSONObject(); object.put("key", rs1.getString(1)); object.put("value",
		 * rs1.getString(2)); array.add(object);
		 * 
		 * } plasticgroups.put(binGrpCode, array); }
		 * 
		 * resultJson.put("BINGRP_CODES", json); resultJson.put("BINGROUPS",
		 * bingroups); resultJson.put("PLASTICGROUPS", plasticgroups);
		 * 
		 * json.clear(); binPstmt.close(); binRs.close();
		 * 
		 * 
		 * binMap.put(CevaCommonConstants.BIN_INFO, resultJson);
		 * 
		 * logger.debug("BinManagement [" + binMap+ "]");
		 * 
		 * responseDTO.setData(binMap);
		 * 
		 * //transferCodeQry = null;
		 * 
		 * } catch (Exception e) { logger.debug("Got Exception in binManagement [" +
		 * e.getMessage() + "]"); } finally { try {
		 * 
		 * if (binRs != null) { binRs.close(); } if (binPstmt != null) {
		 * binPstmt.close(); } if (connection != null) { connection.close(); } }
		 * catch (Exception e) { e.printStackTrace(); } binMap = null; resultJson =
		 * null; }
		 * 
		 * return responseDTO; }
		 */

		public ResponseDTO inserProductModifyDetails(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside inserProductModifyDetails.. ");

			String productCode = null;
			String productDesc = null;
			String programCode = null;
			String binCurrency = null;
			// String bin=null;
			String binGroupCodeVal ="";
			String binGroupCodeVal1 = null;
			String expPeriod = null;
			String plasticCode = null;
			String issuanceTemlateId = null;
			String serviceCode = null;
			String issuingCountry = null;
			String limitCode = null;
			String feeCode = null;
			String feename = null;
			String bin1[]= null;
			String bin2[]= null;
			String ussdinilmt = "";
			String ussdsecfalmt = "";
			String ussdinitallmt = "";
			
			
			CallableStatement callableStatement = null;
			String insertProductDetailsProc = "{call PRODUCTPKGNEW.ProductModifyProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			//String insertProductDetailsProc = "{call ProductModifyProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			
			String Msg = "";
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON  [" + requestJSON + "]");

				productCode = requestJSON.getString("productCode");
				productDesc = requestJSON.getString("productDesc");
			
				
				programCode = requestJSON.containsKey("programCode")?requestJSON.getString("programCode").split("-")[0]:"";
				binCurrency = requestJSON.containsKey("binCurrency")?requestJSON.getString("binCurrency"):"";
				if(requestJSON.containsKey("binGroupCodeVal"))
					bin1= requestJSON.getString("binGroupCodeVal").split(",");
				
				/*for(int i=0;i<bin1.length;i++)
				{
					bin2=bin1[i].split("-");
					binGroupCodeVal+=bin2[0]+",";
				}*/
				if(binGroupCodeVal.length()>0)
					binGroupCodeVal1=binGroupCodeVal.substring(0, binGroupCodeVal.length()-1);
				expPeriod =requestJSON.containsKey("expPeriod")? requestJSON.getString("expPeriod"):"";
				plasticCode = requestJSON.containsKey("plasticCode")?requestJSON.getString("plasticCode"):"";
				issuanceTemlateId = requestJSON.containsKey("issuanceTemlateId")?requestJSON.getString("issuanceTemlateId"):"";
				serviceCode =requestJSON.containsKey("serviceCode")? requestJSON.getString("serviceCode"):"";
				if(requestJSON.containsKey("issuingCountry"))
					issuingCountry = requestJSON.getString("issuingCountry").split("-")[0];
				
				limitCode = requestJSON.containsKey("limitCode")?requestJSON.getString("limitCode"):"";
				feeCode = requestJSON.containsKey("feeCode")?requestJSON.getString("feeCode"):"";
				feename = requestJSON.getString("feename");
				//ussdinilmt = requestJSON.getString("ussdinilmt");
				ussdsecfalmt = requestJSON.getString("ussdsecfalmt");
				ussdinitallmt = requestJSON.getString("ussdinitallmt");

				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				callableStatement = connection.prepareCall(insertProductDetailsProc);

				callableStatement.setString(1, productCode);
				callableStatement.setString(2, productDesc);
				callableStatement.setString(3, programCode);
				callableStatement.setString(4, binCurrency);
				callableStatement.setString(5, binGroupCodeVal1);
				callableStatement.setString(6, expPeriod);
				callableStatement.setString(7, plasticCode);
				callableStatement.setString(8, issuanceTemlateId);
				callableStatement.setString(9, serviceCode);
				callableStatement.setString(10, issuingCountry);
				callableStatement.setString(11, limitCode);
				callableStatement.setString(12, feeCode);
				callableStatement.setString(13, feename);
				callableStatement.setString(14, requestJSON.getString("tokenlimit"));
				callableStatement.setString(15, requestJSON.getString("perdaylimit"));
				callableStatement.setString(16, requestJSON.getString("chdata"));
				callableStatement.setString(17,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
				callableStatement.setString(18, ussdinitallmt);
				callableStatement.setString(19, ussdsecfalmt);
				callableStatement.registerOutParameter(20, java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(21, java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
				System.out.println("HELLOOOOOOOOOO");
				//callableStatement.setString(13, feename);
				//callableStatement.setString(14, requestJSON.getString("tokenlimit"));
				//callableStatement.setString(15, requestJSON.getString("perdaylimit"));
				//callableStatement.setString(16, requestJSON.getString("chdata"));
				//callableStatement.setString(17,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
				//callableStatement.setString(18, ussdinitallmt);
				//callableStatement.setString(19, ussdsecfalmt);
				//callableStatement.registerOutParameter(20, java.sql.Types.INTEGER);
				//callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
				
			
				//callableStatement.setString(13, requestJSON.getString("tokenlimit"));
				//callableStatement.setString(14, requestJSON.getString("perdaylimit"));
				//callableStatement.setString(15, requestJSON.getString("chdata"));
		        //callableStatement.setString(16,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
		        //callableStatement.setString(17, ussdinitallmt);
		        //callableStatement.setString(18, ussdsecfalmt);
				//callableStatement.registerOutParameter(19, java.sql.Types.INTEGER);
				//callableStatement.registerOutParameter(20, java.sql.Types.VARCHAR);
				//callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
				//callableStatement.setString(13, requestJSON.getString("tokenlimit"));
				//callableStatement.setString(14, requestJSON.getString("perdaylimit"));
				//callableStatement.setString(15, requestJSON.getString("chdata"));
		        //callableStatement.setString(16,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
		        //callableStatement.setString(17, ussdinitallmt);
		        //callableStatement.setString(18, ussdsecfalmt);
				//callableStatement.registerOutParameter(19, java.sql.Types.INTEGER);
				//callableStatement.registerOutParameter(20, java.sql.Types.VARCHAR);
				//callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
				callableStatement.executeUpdate();
				System.out.println("HELLOOOOOOOOOO1111111111111");
				int resCnt = callableStatement.getInt(20);
				Msg = callableStatement.getString(21);

				logger.debug("ResultCnt from DB [" + resCnt + "]");
				logger.debug("Msg [" + Msg + "]");

				/*
				 * if (resCnt == 1) { responseDTO.addMessages(Msg); } else if
				 * (resCnt == -1) { responseDTO.addError(Msg); } else {
				 * responseDTO.addError(Msg); }
				 */


			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Exception in inserProductModifyDetails ["
						+ e.getMessage() + "]");
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
		
		public ResponseDTO insertProductActiveData(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside inserProductModifyDetails.. ");

			String productCode = null;
			String productDesc = null;
		
			String programCode = null;
			String binCurrency = null;
			// String bin=null;
			String binGroupCodeVal ="";
			String binGroupCodeVal1 = null;
			String expPeriod = null;
			String plasticCode = null;
			String issuanceTemlateId = null;
			String serviceCode = null;
			String issuingCountry = null;
			String limitCode = null;
			String feeCode = null;
			String bin1[]= null;
			String bin2[]= null;
			
			CallableStatement callableStatement = null;
			String insertProductDetailsProc = "{call PRODUCTACTIVATIONPROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			String Msg = "";
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON  [" + requestJSON + "]");

				productCode = requestJSON.getString("productCode");
				productDesc = requestJSON.getString("productDesc");
			
				programCode = requestJSON.containsKey("programCode")?requestJSON.getString("programCode").split("-")[0]:"";
				binCurrency = requestJSON.containsKey("binCurrency")?requestJSON.getString("binCurrency"):"";
				if(requestJSON.containsKey("binGroupCodeVal"))
					bin1= requestJSON.getString("binGroupCodeVal").split(",");
				
				/*for(int i=0;i<bin1.length;i++)
				{
					bin2=bin1[i].split("-");
					binGroupCodeVal+=bin2[0]+",";
				}*/
				if(binGroupCodeVal.length()>0)
					binGroupCodeVal1=binGroupCodeVal.substring(0, binGroupCodeVal.length()-1);
				expPeriod =requestJSON.containsKey("expPeriod")? requestJSON.getString("expPeriod"):"";
				plasticCode = requestJSON.containsKey("plasticCode")?requestJSON.getString("plasticCode"):"";
				issuanceTemlateId = requestJSON.containsKey("issuanceTemlateId")?requestJSON.getString("issuanceTemlateId"):"";
				serviceCode =requestJSON.containsKey("serviceCode")? requestJSON.getString("serviceCode"):"";
				if(requestJSON.containsKey("issuingCountry"))
					issuingCountry = requestJSON.getString("issuingCountry").split("-")[0];
				
				limitCode = requestJSON.containsKey("limitCode")?requestJSON.getString("limitCode"):"";
				feeCode = requestJSON.containsKey("feeCode")?requestJSON.getString("feeCode"):"";

				connection = DBConnector.getConnection();
				logger.debug("connection is null [" + connection == null + "]");
				callableStatement = connection.prepareCall(insertProductDetailsProc);

				callableStatement.setString(1, productCode);
				callableStatement.setString(2, productDesc);
				callableStatement.setString(3, programCode);
				callableStatement.setString(4, binCurrency);
				callableStatement.setString(5, binGroupCodeVal1);
				callableStatement.setString(6, expPeriod);
				callableStatement.setString(7, plasticCode);
				callableStatement.setString(8, issuanceTemlateId);
				callableStatement.setString(9, serviceCode);
				callableStatement.setString(10, issuingCountry);
				callableStatement.setString(11, limitCode);
				callableStatement.setString(12, feeCode);
				callableStatement.setString(13, requestJSON.getString("tokenlimit"));
				callableStatement.setString(14, requestJSON.getString("perdaylimit"));
				callableStatement.setString(15, requestJSON.getString("chdata"));
		        callableStatement.setString(16,	requestJSON.getString(CevaCommonConstants.MAKER_ID));
				callableStatement.registerOutParameter(17, java.sql.Types.INTEGER);
				callableStatement.registerOutParameter(18, java.sql.Types.VARCHAR);
			
				callableStatement.executeUpdate();
				int resCnt = callableStatement.getInt(17);
				Msg = callableStatement.getString(18);

				logger.debug("ResultCnt from DB [" + resCnt + "]");
				logger.debug("Msg [" + Msg + "]");

				/*
				 * if (resCnt == 1) { responseDTO.addMessages(Msg); } else if
				 * (resCnt == -1) { responseDTO.addError(Msg); } else {
				 * responseDTO.addError(Msg); }
				 */

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Exception in inserProductModifyDetails ["
						+ e.getMessage() + "]");
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
		
		public ResponseDTO insertProductPermission(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside inserProductModifyDetails.. ");

			String productCode = null;
			String branchdetails = null;
			
			PreparedStatement pstmt = null;
			Statement stmt= null;
			int resCnt=0;
			String Msg = "";
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON  [" + requestJSON + "]");

				productCode = requestJSON.getString("productCode");
				branchdetails = requestJSON.getString("branchdetails");
				
				System.out.println(":::: productCode :::"+productCode);
				System.out.println(":::: branchdetails :::"+branchdetails);
				String str1[]=branchdetails.split(",");
				//System.out.println(str[1]);
				connection = DBConnector.getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("DELETE FROM PRODUCT_PERMISSION WHERE PRODUCT_CODE='"+productCode+"'");
				pstmt.executeUpdate();
				pstmt.close();
				stmt=connection.createStatement(); 
				for(int i=0; i<str1.length; i++){
		             // System.out.println(str1[i]);
		              stmt.addBatch("insert into PRODUCT_PERMISSION values('"+productCode+"','"+str1[i]+"')");  
		         }
				stmt.executeBatch();//executing the batch  
				
				
				connection.commit();
				logger.debug("ResultCnt from DB [" + resCnt + "]");
				logger.debug("Msg [" + Msg + "]");

				/*
				 * if (resCnt == 1) { responseDTO.addMessages(Msg); } else if
				 * (resCnt == -1) { responseDTO.addError(Msg); } else {
				 * responseDTO.addError(Msg); }
				 */

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Exception in inserProductModifyDetails ["
						+ e.getMessage() + "]");
			}

			finally {

				try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {

				}
				Msg = null;

			}

			return responseDTO;
		}
			
		public ResponseDTO insertProductServices(RequestDTO requestDTO) {

			Connection connection = null;
			logger.debug("Inside inserProductModifyDetails.. ");

			String productCode = null;
			String application = null;
			String branchdetails = null;
			
			PreparedStatement pstmt = null;
			Statement stmt= null;
			int resCnt=0;
			String Msg = "";
			try {

				responseDTO = new ResponseDTO();
				requestJSON = requestDTO.getRequestJSON();
				logger.debug("Request JSON  [" + requestJSON + "]");

				productCode = requestJSON.getString("productCode");
				branchdetails = requestJSON.getString("branchdetails");
				application = requestJSON.getString("application");
				
				System.out.println(":::: productCode :::"+productCode);
				System.out.println(":::: branchdetails :::"+branchdetails);
				String str1[]=branchdetails.split(",");
				//System.out.println(str[1]);
				connection = DBConnector.getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement("DELETE FROM LIMIT_PERDAY_RESTRICTION WHERE PRODUCT_CODE='"+productCode+"'");
				pstmt.executeUpdate();
				pstmt.close();
				stmt=connection.createStatement(); 
				for(int i=0; i<str1.length; i++){
		             // System.out.println(str1[i]);
		              stmt.addBatch("insert into LIMIT_PERDAY_RESTRICTION values('"+productCode+"','"+str1[i]+"','"+application+"')");  
		         }
				stmt.executeBatch();//executing the batch  
				
				
				connection.commit();
				logger.debug("ResultCnt from DB [" + resCnt + "]");
				logger.debug("Msg [" + Msg + "]");

				/*
				 * if (resCnt == 1) { responseDTO.addMessages(Msg); } else if
				 * (resCnt == -1) { responseDTO.addError(Msg); } else {
				 * responseDTO.addError(Msg); }
				 */

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("Exception in inserProductModifyDetails ["
						+ e.getMessage() + "]");
			}

			finally {

				try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {

				}
				Msg = null;

			}

			return responseDTO;
		}
		
		public ResponseDTO fetchLimitInfo(RequestDTO requestDTO) {

			logger.debug(" Inside fetchLimitInfo.. ");
			
			HashMap<String, Object> binDataMap = null;
			JSONArray binJsonArray = null;
			 
			JSONObject resultJson = null;
			JSONObject json = null;
			
			PreparedStatement getBinPstmt = null;
			ResultSet getBinRs = null;
			
			Connection connection = null;

			String entityQry =  "select  LM.LMT_FEE_CODE,LM.LMT_FEE_DESC from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE=?"
                                +"and LM.REF_NUM=LD.REF_NUM";
			try {
				
				responseDTO = new ResponseDTO();

				binDataMap = new HashMap<String, Object>();
				binJsonArray = new JSONArray();
				resultJson = new JSONObject();
			 
				requestJSON = requestDTO.getRequestJSON();
				connection = DBConnector.getConnection();

				logger.debug("connection is null [" + connection == null + "]");

				getBinPstmt = connection.prepareStatement(entityQry);
				getBinPstmt.setString(1,requestJSON.getString("limitCode"));
				getBinRs = getBinPstmt.executeQuery();
				
				json = new JSONObject();

			 
				if (getBinRs.next()) {
					json.put("limitCode", getBinRs.getString(1));
					json.put("limitDesc", getBinRs.getString(2));
				}
				
				resultJson.put("limitData1", json);
				
			    json.clear();
				getBinPstmt.close();
				getBinRs.close();
				
				String entityQry1 =  "select rownum,(select txn_name from txn_master t where t.txn_code=LD.TXN_CODE ),"
									+"(select cfg.key_value from config_data cfg where cfg.key_id=LD.FREQUENCY),LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT"
                                    +" from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE= ? and LM.REF_NUM=LD.REF_NUM ";
				
				getBinPstmt = connection.prepareStatement(entityQry1);
				getBinPstmt.setString(1,requestJSON.getString("limitCode"));
				getBinRs = getBinPstmt.executeQuery();
				 

				while (getBinRs.next()) {
					
					json.put("sNo", getBinRs.getString(1));
					json.put("txnName", getBinRs.getString(2));
					json.put("criteria", getBinRs.getString(3));
					json.put("maxCount", getBinRs.getString(4));
					json.put("minAmount", getBinRs.getString(5));
					json.put("maxAmount", getBinRs.getString(6));
					
					binJsonArray.add(json);
					
				}
				
				resultJson.put("limitData2", binJsonArray);
				json.clear();
				
				binDataMap.put(CevaCommonConstants.BIN_INFO, resultJson);
				
				logger.debug("BinDataMap    [" + binDataMap + "]");
				
				responseDTO.setData(binDataMap);
				entityQry = null;

			} catch (Exception e) {
				logger.debug("Got Exception in View Product Details ["
						+ e.getMessage() + "]");
			} finally {
				try {

					if (getBinRs != null) {
						getBinRs.close();
					}

					if (getBinPstmt != null) {
						getBinPstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				binDataMap = null;
				resultJson = null;
			}

			return responseDTO;
		}
		
		public ResponseDTO feecodeDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchfeecodeInfo.. ");
			HashMap<String, Object>  feecodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getfeecodePstmt = null;
			ResultSet getfeecodeRs = null;
			Connection connection = null;

			String  feecode = "";
			String  feecodeCountQry = "select  LM.LMT_FEE_CODE ,LM.LMT_FEE_DESC ,LM.PRODUCT,LM.APPLICATION  from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE=? and LM.REF_NUM=LD.REF_NUM";

			

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				feecodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				 feecode = requestJSON.getString(CevaCommonConstants.FEE_CODE);

				getfeecodePstmt = connection.prepareStatement(feecodeCountQry);
				getfeecodePstmt.setString(1, feecode);

				getfeecodeRs = getfeecodePstmt.executeQuery();
				json = new JSONObject();

				if (getfeecodeRs.next()) {				
					json.put("feeCode", getfeecodeRs.getString(1));
				     json.put("feeDesc", getfeecodeRs.getString(2));
				    json.put("product", getfeecodeRs.getString(3));
				     json.put("application", getfeecodeRs.getString(4));
					
				}

				

				resultJson.put("Feecodedetails", json);	
				
				getfeecodePstmt.close();
				getfeecodeRs.close();
				
				
				String entityQry1 =  "select rownum SNO ,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE) TXNNAME,"
						+ "LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') "
						+ "FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
						+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.AGENT,LD.CEVA,LD.BANK,SUPERAGENT,VAT,NVL(LD.SUB_AGENT,0),NVL(LD.THIRD_PARTY,0),LD.SEQ_NO,FEE_CHARGE from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where "
						+ "LM.LMT_FEE_CODE=? and LM.REF_NUM=LD.REF_NUM";
				    
				getfeecodePstmt = connection.prepareStatement(entityQry1);
				getfeecodePstmt.setString(1,feecode);
				getfeecodeRs = getfeecodePstmt.executeQuery();
				     

				    while (getfeecodeRs.next()) {
				     
				     json.put("SNO", getfeecodeRs.getString(1));
				     json.put("TXNNAME", getfeecodeRs.getString(2));
				     json.put("FREQ", getfeecodeRs.getString(3));
				     json.put("FLATPER", getfeecodeRs.getString(4));
				     json.put("FPVALUE", getfeecodeRs.getString(5));
				     json.put("CRT", getfeecodeRs.getString(6));
				     json.put("FRMVAL", getfeecodeRs.getString(7));
				     json.put("TOVAL", getfeecodeRs.getString(8));
				     json.put("AGENT", getfeecodeRs.getString(9));
				     json.put("CEVA", getfeecodeRs.getString(10));
				     json.put("BANK", getfeecodeRs.getString(11));
				     json.put("SUPERAGENT", getfeecodeRs.getString(12));
				     json.put("VAT", getfeecodeRs.getString(13));
				     json.put("SUBAGENT", getfeecodeRs.getString(14));
				     json.put("THIRD_PARTY", getfeecodeRs.getString(15));
				     json.put("SEQ_NO", getfeecodeRs.getString(16));
				     json.put("FEE_CHARGE", getfeecodeRs.getString(17));

				     
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("Feecodedetails2", JsonArray);
				    feecodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("feecodeDataMap[" + feecodeDataMap + "]");
				    responseDTO.setData(feecodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getfeecodeRs != null) {
						getfeecodeRs.close();
					}

					if (getfeecodePstmt != null) {
						getfeecodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				feecodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
		
			
	}
		
		
		public ResponseDTO authfeecodeDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchfeecodeInfo.. ");
			HashMap<String, Object>  feecodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getfeecodePstmt = null;
			ResultSet getfeecodeRs = null;
			Connection connection = null;
			String  feecodeCountQry ="";
			String  feecode = "";
			
			

			

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				feecodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				 feecode = requestJSON.getString(CevaCommonConstants.FEE_CODE);
				
				 if(requestJSON.getString("actiontype").equalsIgnoreCase("MODIFY_FEE")){
						feecodeCountQry = "select  LM.LMT_FEE_CODE ,LM.LMT_FEE_DESC ,LM.PRODUCT,LM.APPLICATION,LD.REF_NO  from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE=? AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";

					}else if(requestJSON.getString("actiontype").equalsIgnoreCase("STATUS_FEE")){
						
						feecodeCountQry = "select  LM.LMT_FEE_CODE ,LM.LMT_FEE_DESC ,LM.PRODUCT,LM.APPLICATION,LM.REF_NO  from LIMIT_FEE_MASTER_TEMP LM  where LM.LMT_FEE_CODE=? AND LM.REF_NO=?";

						
					}else {
						feecodeCountQry = "select  LM.LMT_FEE_CODE ,LM.LMT_FEE_DESC ,LM.PRODUCT,LM.APPLICATION,LD.REF_NO  from LIMIT_FEE_MASTER_TEMP LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE=? AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
			
					}
				getfeecodePstmt = connection.prepareStatement(feecodeCountQry);
				getfeecodePstmt.setString(1, feecode);
				getfeecodePstmt.setString(2, requestJSON.getString("refno"));

				getfeecodeRs = getfeecodePstmt.executeQuery();
				json = new JSONObject();

				if (getfeecodeRs.next()) {				
					json.put("feeCode", getfeecodeRs.getString(1));
				     json.put("feeDesc", getfeecodeRs.getString(2));
				    json.put("product", getfeecodeRs.getString(3));
				     json.put("application", getfeecodeRs.getString(4));
				     json.put("refno", getfeecodeRs.getString(5));
					
				}

				resultJson.put("Feecodedetails", json);	
				
				getfeecodePstmt.close();
				getfeecodeRs.close();
				String entityQry1 = "";
				if(requestJSON.getString("actiontype").equalsIgnoreCase("MODIFY_FEE")){
					entityQry1 =  "select rownum SNO ,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE) TXNNAME,"
							+ "LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') "
							+ "FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
							+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.AGENT,LD.CEVA,LD.BANK,LD.SEQ_NO,SUPERAGENT,VAT from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS_TEMP LD where "
							+ "LM.LMT_FEE_CODE=? AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
				}else if(requestJSON.getString("actiontype").equalsIgnoreCase("STATUS_FEE")){
					entityQry1 =  "select rownum SNO ,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE) TXNNAME,"
							+ "LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') "
							+ "FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
							+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.AGENT,LD.CEVA,LD.BANK,LD.SEQ_NO,SUPERAGENT,VAT from LIMIT_FEE_MASTER_TEMP LM , LIMIT_FEE_DETAILS LD where "
							+ "LM.LMT_FEE_CODE=? AND LM.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
				}else {
				entityQry1 =  "select rownum SNO ,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE) TXNNAME,"
						+ "LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') "
						+ "FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
						+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.AGENT,LD.CEVA,LD.BANK,LD.SEQ_NO,SUPERAGENT,VAT from LIMIT_FEE_MASTER_TEMP LM , LIMIT_FEE_DETAILS_TEMP LD where "
						+ "LM.LMT_FEE_CODE=? AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
				}   
				getfeecodePstmt = connection.prepareStatement(entityQry1);
				getfeecodePstmt.setString(1,feecode);
				getfeecodePstmt.setString(2, requestJSON.getString("refno"));
				getfeecodeRs = getfeecodePstmt.executeQuery();
				     

				    while (getfeecodeRs.next()) {
				     
				     json.put("SNO", getfeecodeRs.getString(1));
				     json.put("TXNNAME", getfeecodeRs.getString(2));
				     json.put("FREQ", getfeecodeRs.getString(3));
				     json.put("FLATPER", getfeecodeRs.getString(4));
				     json.put("FPVALUE", getfeecodeRs.getString(5));
				     json.put("CRT", getfeecodeRs.getString(6));
				     json.put("FRMVAL", getfeecodeRs.getString(7));
				     json.put("TOVAL", getfeecodeRs.getString(8));
				     json.put("AGENT", getfeecodeRs.getString(9));
				     json.put("CEVA", getfeecodeRs.getString(10));
				     json.put("BANK", getfeecodeRs.getString(11));
				     json.put("SEQ_NO", getfeecodeRs.getString(12));
				     json.put("SUPERAGENT", getfeecodeRs.getString(13));
				     json.put("VAT", getfeecodeRs.getString(14));

				     
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("Feecodedetails2", JsonArray);
				    feecodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("feecodeDataMap[" + feecodeDataMap + "]");
				    responseDTO.setData(feecodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getfeecodeRs != null) {
						getfeecodeRs.close();
					}

					if (getfeecodePstmt != null) {
						getfeecodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				feecodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
		
			
	}		



		public ResponseDTO feeDetailsModify(RequestDTO requestDTO) {

			logger.debug("Inside fetchfeecodeInfo.. ");
			HashMap<String, Object>  feecodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getfeecodePstmt = null;
			ResultSet getfeecodeRs = null;
			Connection connection = null;

			String  feecode = "";
			String trrefno="";

			

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				feecodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				 feecode = requestJSON.getString(CevaCommonConstants.FEE_CODE);
				 trrefno = requestJSON.getString("trrefno");

				json = new JSONObject();
				
				
				String entityQry1 =  "select rownum SNO ,(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE ) TXNNAME,"
						+ "LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') "
						+ "FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) "
						+ "FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL,LD.SEQ_NO,LD.AGENT,LD.CEVA,LD.BANK,LD.CHANNEL,LD.SUPERAGENT,LD.VAT,NVL(LD.SUB_AGENT,0),NVL(LD.THIRD_PARTY,0),(SELECT OPERATORNAME from MN_OPERATORS where OPERATORID=LD.OPERATORS),NVL(LD.QT,0) ,FEE_CHARGE from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where "
						+ "LM.LMT_FEE_CODE=? and LD.SEQ_NO=? and LM.REF_NUM=LD.REF_NUM";
				    
				getfeecodePstmt = connection.prepareStatement(entityQry1);
				getfeecodePstmt.setString(1,feecode);
				getfeecodePstmt.setString(2,trrefno);
				getfeecodeRs = getfeecodePstmt.executeQuery();
				     

				    while (getfeecodeRs.next()) {
				     
				     json.put("SNO", getfeecodeRs.getString(1));
				     json.put("TXNNAME", getfeecodeRs.getString(2));
				     json.put("FREQ", getfeecodeRs.getString(3));
				     json.put("FLATPER", getfeecodeRs.getString(4));
				     json.put("FPVALUE", getfeecodeRs.getString(5));
				     json.put("CRT", getfeecodeRs.getString(6));
				     json.put("FRMVAL", getfeecodeRs.getString(7));
				     json.put("TOVAL", getfeecodeRs.getString(8));
				     json.put("SEQ_NO", getfeecodeRs.getString(9));
				     json.put("AGENT", getfeecodeRs.getString(10));
				     json.put("CEVA", getfeecodeRs.getString(11));
				     json.put("BANK", getfeecodeRs.getString(12));
				     json.put("CHANNEL", getfeecodeRs.getString(13));
				     json.put("SUPERAGENT", getfeecodeRs.getString(14));
				     json.put("VAT", getfeecodeRs.getString(15));
				     json.put("SUBAGENT", getfeecodeRs.getString(16));
				     json.put("THIRDPARTY", getfeecodeRs.getString(17));
				     json.put("OPERATORS", getfeecodeRs.getString(18));
				     json.put("QT", getfeecodeRs.getString(19));
				     json.put("FEE_CHARGE", getfeecodeRs.getString(20));
				     
				     
				    }
				    
				    resultJson.put("Feecodedetails", json);
				    feecodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("feecodeDataMap[" + feecodeDataMap + "]");
				    responseDTO.setData(feecodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getfeecodeRs != null) {
						getfeecodeRs.close();
					}

					if (getfeecodePstmt != null) {
						getfeecodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				feecodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
		
			
	}		


		
		
		public ResponseDTO limitcodeDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  limitcode = "";
			String  limitcodeCountQry = "select  LM.LMT_FEE_CODE,LM.LMT_FEE_DESC,PRODUCT,APPLICATION from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE=?"
                                +"and LM.REF_NUM=LD.REF_NUM";

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				limitcode = requestJSON.getString(CevaCommonConstants. LIMIT_CODE);

				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1,limitcode);

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("limitCode", getlimitcodeRs.getString(1));
				     json.put("limitDesc", getlimitcodeRs.getString(2));
				     json.put("product", getlimitcodeRs.getString(3));
				     json.put("application", getlimitcodeRs.getString(4));
					
				}

				resultJson.put("limitcodedetails", json);	
				
				getlimitcodePstmt.close();
				getlimitcodeRs.close();
				
				
				String entityQry1 =  "select rownum,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE ),"
									+"LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT,LD.SEQ_NO"
                                    +" from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.LMT_FEE_CODE= ? and LM.REF_NUM=LD.REF_NUM";
				    
				getlimitcodePstmt = connection.prepareStatement(entityQry1);
				getlimitcodePstmt.setString(1,limitcode);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     

				    while (getlimitcodeRs.next()) {
				     
				     json.put("SNO", getlimitcodeRs.getString(1));
				     json.put("TXNNAME", getlimitcodeRs.getString(2));
				     json.put("FREQ", getlimitcodeRs.getString(3));
				     json.put("MAX_CNT", getlimitcodeRs.getInt(4));
				     json.put("MIN_AMT", getlimitcodeRs.getInt(5));
				     json.put("MAX_AMT", getlimitcodeRs.getInt(6));
				     json.put("SEQ_NO", getlimitcodeRs.getString(7));
				     
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
		
		public ResponseDTO authlimitcodeDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  limitcode = "";
			String  limitcodeCountQry = "";
			String entityQry1 ="";

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				limitcode = requestJSON.getString(CevaCommonConstants. LIMIT_CODE);
				if(requestJSON.getString("actiontype").equalsIgnoreCase("MODIFY_LIMIT")){
					limitcodeCountQry = "select  LM.LMT_FEE_CODE,LM.LMT_FEE_DESC,PRODUCT,APPLICATION,LD.REF_NO from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE=? AND LD.REF_NO=?"
	                        +"and LM.REF_NUM=LD.REF_NUM";
				}else{
					limitcodeCountQry = "select  LM.LMT_FEE_CODE,LM.LMT_FEE_DESC,PRODUCT,APPLICATION,LD.REF_NO from LIMIT_FEE_MASTER_TEMP LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE=? AND LD.REF_NO=?"
	                        +"and LM.REF_NUM=LD.REF_NUM";
				}
				

				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1,limitcode);
				getlimitcodePstmt.setString(2,requestJSON.getString("refno"));

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("limitCode", getlimitcodeRs.getString(1));
				     json.put("limitDesc", getlimitcodeRs.getString(2));
				     json.put("product", getlimitcodeRs.getString(3));
				     json.put("application", getlimitcodeRs.getString(4));
				     json.put("refno", getlimitcodeRs.getString(5));
					
				}

				resultJson.put("limitcodedetails", json);	
				
				getlimitcodePstmt.close();
				getlimitcodeRs.close();
				
				if(requestJSON.getString("actiontype").equalsIgnoreCase("MODIFY_LIMIT")){
				entityQry1 =  "select rownum,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE),"
									+"LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT,LD.SEQ_NO"
                                    +" from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE= ?  AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
				}else{
					entityQry1 =  "select rownum,LD.CHANNEL||'-'||(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE),"
							+"LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT,LD.SEQ_NO"
                            +" from LIMIT_FEE_MASTER_TEMP LM , LIMIT_FEE_DETAILS_TEMP LD where LM.LMT_FEE_CODE= ?  AND LD.REF_NO=? and LM.REF_NUM=LD.REF_NUM";
				}
				    
				getlimitcodePstmt = connection.prepareStatement(entityQry1);
				getlimitcodePstmt.setString(1,limitcode);
				getlimitcodePstmt.setString(2,requestJSON.getString("refno"));
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     

				    while (getlimitcodeRs.next()) {
				     
				     json.put("SNO", getlimitcodeRs.getString(1));
				     json.put("TXNNAME", getlimitcodeRs.getString(2));
				     json.put("FREQ", getlimitcodeRs.getString(3));
				     json.put("MAX_CNT", getlimitcodeRs.getInt(4));
				     json.put("MIN_AMT", getlimitcodeRs.getInt(5));
				     json.put("MAX_AMT", getlimitcodeRs.getInt(6));
				     json.put("SEQ_NO", getlimitcodeRs.getString(7));
				     
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
			
		
		
		
		public ResponseDTO authfetchViewLoyalty(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  limitcode = "";
			String  limitcodeCountQry = "select  LOYALTY_CODE,LOYALTY_DESC,PRODUCT,APPLICATION,REF_NO from LOYALTY_MASTER_TEMP where LOYALTY_CODE=?";

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				limitcode = requestJSON.getString("loyaltyCode");

				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1,limitcode);

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("limitCode", getlimitcodeRs.getString(1));
				     json.put("limitDesc", getlimitcodeRs.getString(2));
				     json.put("product", getlimitcodeRs.getString(3));
				     json.put("application", getlimitcodeRs.getString(4));
				     json.put("refno", getlimitcodeRs.getString(5));
					
				}

				resultJson.put("limitcodedetails", json);	
				
				getlimitcodePstmt.close();
				getlimitcodeRs.close();
				
				
				String entityQry1 =  "select SERVICE_DESC,TXN_AMOUNT,NO_OF_POINT"
                                    +" from LOYALTY_DETAILS_TEMP where LOYALTY_CODE= ?";
				    
				getlimitcodePstmt = connection.prepareStatement(entityQry1);
				getlimitcodePstmt.setString(1,limitcode);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     

				    while (getlimitcodeRs.next()) {
				     
				     json.put("SERVICE_DESC", getlimitcodeRs.getString(1));
				     json.put("TXN_AMOUNT", getlimitcodeRs.getString(2));
				     json.put("NO_OF_POINT", getlimitcodeRs.getString(3));
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
			
		public ResponseDTO authAccountOpenDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  loyaltyCode = "";
			String  limitcodeCountQry = "select TXNREFNO,F_NAME,M_NAME,L_NAME,GENDER,DATEOFBIRTH,PRODUCTCODE,INITIATORID,BRANCH,RMCODE,MAKER_ID,MAKER_DT,STATUS,NVL(ACCOUNT_NO,' ') AS ACCTNO,AMOUNT,BVN,MOBILENO,CUST_STATUS from ACCOUNT_OPEN_WEB WHERE AUTH_REFERENCENO=?";

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				loyaltyCode = requestJSON.getString("loyaltyCode");

				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1,loyaltyCode);

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("TXNREFNO", getlimitcodeRs.getString("TXNREFNO"));
				     json.put("F_NAME", getlimitcodeRs.getString("F_NAME"));
				     json.put("M_NAME", getlimitcodeRs.getString("M_NAME"));
				     json.put("L_NAME", getlimitcodeRs.getString("L_NAME"));
				     json.put("GENDER", getlimitcodeRs.getString("GENDER"));
				     json.put("DATEOFBIRTH", getlimitcodeRs.getString("DATEOFBIRTH"));
				     json.put("PRODUCTCODE", getlimitcodeRs.getString("PRODUCTCODE"));
				     json.put("INITIATORID", getlimitcodeRs.getString("INITIATORID"));
				     json.put("BRANCH", getlimitcodeRs.getString("BRANCH"));
				     json.put("RMCODE", getlimitcodeRs.getString("RMCODE"));
				     json.put("MAKER_ID", getlimitcodeRs.getString("MAKER_ID"));
				     json.put("MAKER_DT", getlimitcodeRs.getString("MAKER_DT"));
				     json.put("STATUS", getlimitcodeRs.getString("STATUS"));
				     json.put("ACCOUNT_NO", getlimitcodeRs.getString("ACCTNO"));
				     json.put("AMOUNT", getlimitcodeRs.getString("AMOUNT"));
				     json.put("BVN", getlimitcodeRs.getString("BVN"));
				     json.put("MOBILENO", getlimitcodeRs.getString("MOBILENO"));
				     json.put("CUST_STATUS", getlimitcodeRs.getString("CUST_STATUS"));
				     json.put("AUTH_REFERENCENO", loyaltyCode);
					
				}

				resultJson.put("limitcodedetails", json);	
				
				
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
			
		
		public ResponseDTO limitDetailsModify(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  trrefno = "";
			
			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				trrefno = requestJSON.getString("trrefno");

				
				json = new JSONObject();

				String entityQry1 =  "select rownum,(select SERVICEDESC from BANK_SERVICE_CODE_MASTER t where t.SERVICECODE=LD.TXN_CODE ),"
									+"LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT,LD.SEQ_NO,LD.CHANNEL,(SELECT OPERATORNAME from MN_OPERATORS where OPERATORID=LD.OPERATORS)"
                                    +" from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LD.SEQ_NO= ? and LM.REF_NUM=LD.REF_NUM";
				    
				getlimitcodePstmt = connection.prepareStatement(entityQry1);
				getlimitcodePstmt.setString(1,trrefno);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     

				    while (getlimitcodeRs.next()) {
				     
				     json.put("SNO", getlimitcodeRs.getString(1));
				     json.put("TXNNAME", getlimitcodeRs.getString(2));
				     json.put("FREQ", getlimitcodeRs.getString(3));
				     json.put("MAX_CNT", getlimitcodeRs.getInt(4));
				     json.put("MIN_AMT", getlimitcodeRs.getInt(5));
				     json.put("MAX_AMT", getlimitcodeRs.getInt(6));
				     json.put("SEQ_NO", getlimitcodeRs.getString(7));
				     json.put("CHANNEL", getlimitcodeRs.getString(8));
				     json.put("OPERATORS", getlimitcodeRs.getString(9));
				  
				     
				    }
				    
				    resultJson.put("limitcodedetails", json);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
		
		
		
		public ResponseDTO limitDetailsModifyAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  trrefno = "";
			
			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				trrefno = requestJSON.getString("trrefno");
				
				getlimitcodePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),NVL(CLUSTER_ID,' ')  from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+requestJSON.getString("makerId")+"')");

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				while (getlimitcodeRs.next()) {
					
				pstmt = connection.prepareStatement("insert into auth_pending ( ref_num,maker_id,maker_dttm,auth_code,status,auth_flag,MAKER_BRCODE,ACTION,ACTION_DETAILS) values('"+getlimitcodeRs.getString(1)+"','"+requestJSON.getString("makerId")+"',sysdate,'LMTMODAUTH','P','LM','"+getlimitcodeRs.getString(2)+"','MAKER','Limit Code "+requestJSON.getString("limitCode")+" Modify')");
				pstmt.executeUpdate();
				pstmt.close();
					
				/*pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_TEMP select * from LIMIT_FEE_MASTER where LMT_FEE_CODE='"+requestJSON.getString("limitCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();*/
				
				/*pstmt = connection.prepareStatement("UPDATE LIMIT_FEE_MASTER_TEMP SET REF_NO='"+getlimitcodeRs.getString(1)+"' where LMT_FEE_CODE='"+requestJSON.getString("limitCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();*/
					
				pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_TEMP select * from LIMIT_FEE_DETAILS WHERE SEQ_NO="+requestJSON.getString("trrefno")+"");
				pstmt.executeUpdate();
				pstmt.close();					
					

				getlimitcodePstmt = connection.prepareStatement("UPDATE  LIMIT_FEE_DETAILS_TEMP set MAX_CNT=?, MIN_AMT=?, MAX_AMT=?,REF_NO='"+getlimitcodeRs.getString(1)+"'  WHERE SEQ_NO=?");
				getlimitcodePstmt.setInt(1, Integer.parseInt(requestJSON.getString("maxcount")));
				getlimitcodePstmt.setInt(2, Integer.parseInt(requestJSON.getString("minamount")));
				getlimitcodePstmt.setInt(3, Integer.parseInt(requestJSON.getString("maxamount")));
				getlimitcodePstmt.setInt(4, Integer.parseInt(requestJSON.getString("trrefno")));
				getlimitcodePstmt.executeUpdate();
				
				
				
				}
				
				
				JSONObject json1 = new JSONObject();
				json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
				json1.put("transCode", "productlimitsettings");
				json1.put("channel", "WEB");
				json1.put("message", "Acknowledgment :: Limit Code   "+requestJSON.getString("limitCode")+"  Modified ");
				json1.put("ip", requestJSON.getString("remoteip"));
				json1.put("det1", "");
				json1.put("det2", "");
				json1.put("det3", "");
				
				CommonServiceDao csd=new CommonServiceDao();
				csd.auditTrailInsert(json1);
				
				connection.commit();
				
				
				json = new JSONObject();

				
				     
				     json.put("TXNNAME", requestJSON.getString("transaction"));
				     json.put("FREQ", requestJSON.getString("frequency"));
				     json.put("MAX_CNT",requestJSON.getString("maxcount") );
				     json.put("MIN_AMT", requestJSON.getString("minamount"));
				     json.put("MAX_AMT", requestJSON.getString("maxamount"));
				     json.put("SEQ_NO", requestJSON.getString("trrefno"));
				     json.put("CHANNEL", requestJSON.getString("channel"));
				     
			
				    resultJson.put("limitcodedetails", json);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
		
		
		
		public ResponseDTO feeDetailsModifyAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  trrefno = "";
			
			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();
				getlimitcodePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),NVL(CLUSTER_ID,' ')  from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+requestJSON.getString("makerId")+"')");


				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				while (getlimitcodeRs.next()) {
					
				pstmt = connection.prepareStatement("insert into auth_pending ( ref_num,maker_id,maker_dttm,auth_code,status,auth_flag,MAKER_BRCODE,ACTION,ACTION_DETAILS) values('"+getlimitcodeRs.getString(1)+"','"+requestJSON.getString("makerId")+"',sysdate,'FEEMODAUTH','P','FM','"+getlimitcodeRs.getString(2)+"','MAKER','Fee Code "+requestJSON.getString("feeCode")+" Modify')");
				pstmt.executeUpdate();
				pstmt.close();
				
				/*pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_TEMP select * from LIMIT_FEE_MASTER where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE LIMIT_FEE_MASTER_TEMP SET REF_NO='"+getlimitcodeRs.getString(1)+"' where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();*/
				
				pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_TEMP select * from LIMIT_FEE_DETAILS WHERE SEQ_NO="+requestJSON.getString("trrefno")+"");
				pstmt.executeUpdate();
				pstmt.close();
				
				
				getlimitcodePstmt = connection.prepareStatement("UPDATE  LIMIT_FEE_DETAILS_TEMP set FEE_TYPE=?, VALUE=?, CNT_AMT=? ,AGENT=?,CEVA=?,BANK=?, MIN_CNT=?, MAX_CNT=?, MIN_AMT=?, MAX_AMT=?,REF_NO='"+getlimitcodeRs.getString(1)+"',SUB_AGENT=?,THIRD_PARTY=?,SUPERAGENT=?,FEE_CHARGE=? QT=? WHERE SEQ_NO=?");
				getlimitcodePstmt.setString(1, requestJSON.getString("flatpercentile"));
				getlimitcodePstmt.setString(2, requestJSON.getString("fpValue"));
				getlimitcodePstmt.setString(3, requestJSON.getString("criteria"));
			
				getlimitcodePstmt.setFloat(4, Float.parseFloat(requestJSON.getString("agent")));
				getlimitcodePstmt.setFloat(5, Float.parseFloat(requestJSON.getString("ceva")));
				getlimitcodePstmt.setFloat(6, Float.parseFloat(requestJSON.getString("bank")));
				if((requestJSON.getString("criteria")).equalsIgnoreCase("C")){
					getlimitcodePstmt.setFloat(7, Float.parseFloat(requestJSON.getString("fromvalue")));
					getlimitcodePstmt.setFloat(8, Float.parseFloat(requestJSON.getString("tovalue")));
					getlimitcodePstmt.setInt(9, 0);
					getlimitcodePstmt.setInt(10, 0);	
				}else{
					getlimitcodePstmt.setInt(7, 0);
					getlimitcodePstmt.setInt(8, 0);
					getlimitcodePstmt.setFloat(9, Float.parseFloat(requestJSON.getString("fromvalue")));
					getlimitcodePstmt.setFloat(10, Float.parseFloat(requestJSON.getString("tovalue")));
				}
				getlimitcodePstmt.setFloat(11, Float.parseFloat(requestJSON.getString("subagent")));
				getlimitcodePstmt.setFloat(12, Float.parseFloat(requestJSON.getString("thirdparty")));
				getlimitcodePstmt.setFloat(13, Float.parseFloat(requestJSON.getString("SuperAgent")));
				getlimitcodePstmt.setFloat(14, Float.parseFloat(requestJSON.getString("qt")));
	          getlimitcodePstmt.setString(15, requestJSON.getString("FEE_CHARGE"));
				getlimitcodePstmt.setFloat(16, Float.parseFloat(requestJSON.getString("trrefno")));
				getlimitcodePstmt.executeUpdate();
				
				}
				
				JSONObject json1 = new JSONObject();
				json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
				json1.put("transCode", "productfeesettings");
				json1.put("channel", "WEB");
				json1.put("message", "Acknowledgment :: Fee Code   "+requestJSON.getString("feeCode")+"  Modified ");
				json1.put("ip", requestJSON.getString("remoteip"));
				json1.put("det1", "");
				json1.put("det2", "");
				json1.put("det3", "");
				
				connection.commit();
				trrefno = requestJSON.getString("trrefno");

				
				json = new JSONObject();

			     json.put("TXNNAME", requestJSON.getString("transaction"));
			     json.put("FREQ", requestJSON.getString("frequency"));
			     json.put("FLATPER", requestJSON.getString("flatpercentile"));
			     json.put("FPVALUE", requestJSON.getString("fpValue"));
			     json.put("CRT", requestJSON.getString("criteria"));
			     json.put("FRMVAL", requestJSON.getString("fromvalue"));
			     json.put("TOVAL", requestJSON.getString("tovalue"));
			     json.put("SEQ_NO", requestJSON.getString("trrefno"));
			     
			     json.put("SUBAGENT", requestJSON.getString("subagent"));
			     json.put("AGENT", requestJSON.getString("agent"));
			     json.put("CEVA", requestJSON.getString("ceva"));
			     json.put("BANK", requestJSON.getString("bank"));
			     json.put("CHANNEL", requestJSON.getString("channel"));
			     json.put("SUPERAGENT", requestJSON.getString("SuperAgent"));
			     json.put("VAT", requestJSON.getString("VAT"));
			     json.put("THIRDPARTY", requestJSON.getString("thirdparty"));
			     json.put("FEE_CHARGE", requestJSON.getString("FEE_CHARGE"));  
				
			
				    resultJson.put("Feecodedetails", json);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}		
		
		public ResponseDTO feeDetailsActiveAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  trrefno = "";
			
			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();
				getlimitcodePstmt = connection.prepareStatement("select to_char(systimestamp,'DDISSSSSFF'),NVL(CLUSTER_ID,' ')  from USER_INFORMATION where COMMON_ID=(select common_id from USER_LOGIN_CREDENTIALS where LOGIN_USER_ID='"+requestJSON.getString("makerId")+"')");


				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				while (getlimitcodeRs.next()) {
					
				pstmt = connection.prepareStatement("insert into auth_pending ( ref_num,maker_id,maker_dttm,auth_code,status,auth_flag,MAKER_BRCODE,ACTION,ACTION_DETAILS) values('"+getlimitcodeRs.getString(1)+"','"+requestJSON.getString("makerId")+"',sysdate,'FEEACTAUTH','P','FM','"+getlimitcodeRs.getString(2)+"','MAKER','Fee Code "+requestJSON.getString("feeCode")+" Modify')");
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("DELETE FROM LIMIT_FEE_MASTER_TEMP  where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_MASTER_TEMP select * from LIMIT_FEE_MASTER where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = connection.prepareStatement("UPDATE LIMIT_FEE_MASTER_TEMP SET REF_NO='"+getlimitcodeRs.getString(1)+"' where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"'");
				pstmt.executeUpdate();
				pstmt.close();
				
				/*pstmt = connection.prepareStatement("INSERT INTO LIMIT_FEE_DETAILS_TEMP select * from LIMIT_FEE_DETAILS WHERE REF_NO in (select REF_NO from LIMIT_FEE_MASTER where LMT_FEE_CODE='"+requestJSON.getString("feeCode")+"')");
				pstmt.executeUpdate();
				pstmt.close();*/
				
				
				
				
				}
				
				JSONObject json1 = new JSONObject();
				json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
				json1.put("transCode", "productfeesettings");
				json1.put("channel", "WEB");
				json1.put("message", "Acknowledgment :: Fee Code   "+requestJSON.getString("feeCode")+"  Fee Active-Deactive ");
				json1.put("ip", requestJSON.getString("remoteip"));
				json1.put("det1", "");
				json1.put("det2", "");
				json1.put("det3", "");
				
				connection.commit();

				
				json = new JSONObject();

			     json.put("productcode", requestJSON.getString("productcode"));
			     json.put("feeCode", requestJSON.getString("feeCode"));
			     
				
			
				    resultJson.put("Feecodedetails", json);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}	
		
		public ResponseDTO branchpopupDetails(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;

			String  limitcode = "";
			String  limitcodeCountQry = "select  CLUSTER_ID,CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID=?";

			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				limitcode = requestJSON.getString(CevaCommonConstants. LIMIT_CODE);

				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1,limitcode);

				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("limitCode", getlimitcodeRs.getString(1));
				     json.put("limitDesc", getlimitcodeRs.getString(2));
				 
					
				}

				resultJson.put("limitcodedetails", json);	
				
				getlimitcodePstmt.close();
				getlimitcodeRs.close();
				
				String entityQry1 = "select user_name ,emp_id,NVL(location,' '),decode(USER_STATUS,'A','Active','L','De-Active','F','Active',USER_STATUS),NVL((SELECT GROUP_NAME from USER_GROUPS where GROUP_ID=user_groups),' '),"
						+ "NVL((select STATUS from CONFIG_DATA where key_group='USER_DESIGNATION' and key_value=user_level),' ') "
						+ "from user_information where CLUSTER_ID=? ";
				
				
				getlimitcodePstmt = connection.prepareStatement(entityQry1);
				getlimitcodePstmt.setString(1,limitcode);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				     

				    while (getlimitcodeRs.next()) {
				     
				     json.put("user_name", getlimitcodeRs.getString(1));
				     json.put("emp_id", getlimitcodeRs.getString(2));
				     json.put("location", getlimitcodeRs.getString(3));
				     json.put("USER_STATUS", getlimitcodeRs.getString(4));
				     json.put("GROUP_NAME", getlimitcodeRs.getString(5));
				     json.put("STATUS", getlimitcodeRs.getString(6));
				     
				     JsonArray.add(json);
				     
				    }
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	}
		
		
		
		public ResponseDTO authFetchAuthLoyalty(RequestDTO requestDTO) {
			
			
			
			
			logger.debug("inside [ProductManDao][authFetchAuthLoyalty]");

			
			
			HashMap<String, Object> viewDataMap = null;
			Connection connection = null;

			PreparedStatement servicePstmt = null;
			ResultSet serviceRS = null;

			try {
				responseDTO = new ResponseDTO();
				responseJSON = new JSONObject();
				requestJSON = requestDTO.getRequestJSON();
				
				connection = DBConnector.getConnection();
				viewDataMap = new HashMap<String, Object>();
				
				 JSONObject json = new JSONObject();
				 JSONObject jsonlmt = new JSONObject();
				 JSONArray JsonArray= new JSONArray();
				 String loyaltyCode=requestJSON.getString("loyaltyCode");
				 String actiontype=requestJSON.getString("actiontype");
				 
				 String makerId=requestJSON.getString(CevaCommonConstants.MAKER_ID);
				 String remoteip=requestJSON.getString("remoteip");
				 String Actionname="";
				 String Actiondesc="";
				 
				 String merchantQry = "Select PRODUCT,APPLICATION,LOYALTY_CODE,LOYALTY_DESC,R_MIN_POINTS,R_UNIT_SIZE,"
							+ "R_AMOUNT,R_MIN_POINTS "
							+ "from LOYALTY_MASTER_TEMP where LOYALTY_CODE=?";

				 if(actiontype.equalsIgnoreCase("NEW_LOYALTY_SETTING")){
						
					 servicePstmt = connection.prepareStatement(merchantQry);
						
					 servicePstmt.setString(1, loyaltyCode);
						
						serviceRS = servicePstmt.executeQuery();

						while (serviceRS.next()) {
							json.put("PRODUCT",serviceRS.getString(1));
							json.put("APPLICATION",serviceRS.getString(2));
							json.put("LOYALTY_CODE",serviceRS.getString(3));
							json.put("LOYALTY_DESC",serviceRS.getString(4));
							json.put("R_MIN_POINTS",serviceRS.getString(5));
							json.put("R_UNIT_SIZE", serviceRS.getString(6));
							json.put("R_AMOUNT",serviceRS.getString(7));
							json.put("R_MIN_POINTS",serviceRS.getString(8));
							
							
						}
						
						
							
						
					}else if(actiontype.equalsIgnoreCase("MODIFY_LOYALTY_SETTING")){
						
						servicePstmt = connection.prepareStatement(merchantQry);
						
						 servicePstmt.setString(1, loyaltyCode);
							
							serviceRS = servicePstmt.executeQuery();

							while (serviceRS.next()) {
								json.put("PRODUCT",serviceRS.getString(1));
								json.put("APPLICATION",serviceRS.getString(2));
								json.put("LOYALTY_CODE",serviceRS.getString(3));
								json.put("LOYALTY_DESC",serviceRS.getString(4));
								json.put("R_MIN_POINTS",serviceRS.getString(5));
								json.put("R_UNIT_SIZE", serviceRS.getString(6));
								json.put("R_AMOUNT",serviceRS.getString(7));
								json.put("R_MIN_POINTS",serviceRS.getString(8));
								
								
							}
							
						
						
					}
					
				 
				 	JSONObject jsonaudit = new JSONObject();
				 	jsonaudit.put(CevaCommonConstants.MAKER_ID, makerId);
				 	jsonaudit.put("transCode", Actionname);
				 	jsonaudit.put("channel", "WEB");
				 	jsonaudit.put("message", Actiondesc);
				 	jsonaudit.put("ip", remoteip);
				 	jsonaudit.put("det1", "");
				 	jsonaudit.put("det2", "");
				 	jsonaudit.put("det3", "");
					
					CommonServiceDao csd=new CommonServiceDao();
					csd.auditTrailInsert(jsonaudit);
					
					connection.commit();
							
				 viewDataMap.put("VewDetails", json);
				 responseDTO.setData(viewDataMap);
				
		       } catch (Exception e) {

				logger.debug("[ProductManDao][authFetchAuthLoyalty] SQLException in authFetchAuthLoyalty [" + e.getMessage()
						+ "]");
				responseDTO.addError("[ProductManDao][authFetchAuthLoyalty] Internal Error Occured While Executing.");
			} finally {
				try {

					
					if (servicePstmt != null)
						servicePstmt.close();
					if (serviceRS != null)
						serviceRS.close();
					if (connection != null)
						connection.close();

				} catch (SQLException e) {

				}
				viewDataMap = null;
				
				
			}

			return responseDTO;
		}	
		
		public ResponseDTO authAccountOpenAck(RequestDTO requestDTO) {

			logger.debug("Inside fetchlimitcodeInfo.. ");
			HashMap<String, Object> limitcodeDataMap = null;

			JSONObject resultJson = null;
			JSONObject json = null;
			JSONArray JsonArray= new JSONArray();
			PreparedStatement getlimitcodePstmt = null;
			PreparedStatement pstmt = null;
			ResultSet getlimitcodeRs = null;
			Connection connection = null;
			
			String  decision = "";
			String  productcode = "";
			String  Narration = "";
			String refno="";
			
			String  bvn = "";
			String  referenceno = "";
			String custstatus="";


			try {
				requestJSON = requestDTO.getRequestJSON();
				responseDTO = new ResponseDTO();
				limitcodeDataMap = new HashMap<String, Object>();
				resultJson = new JSONObject();

				connection = DBConnector.getConnection();

				decision=requestJSON.getString("decision");
				bvn=requestJSON.getString("bvn");
				referenceno=requestJSON.getString("referenceno");
				custstatus=requestJSON.getString("custstatus");
				refno=requestJSON.getString("refno");

				
				String  limitcodeCountQry = "select NEW_TXNREFNO,NVL(F_NAME,' ') as fname,NVL(M_NAME,' ') as mname,NVL(L_NAME,' ') as lname,GENDER,to_char(to_date(DATEOFBIRTH,'dd-mon-yyyy'),'dd-MON-yy') as DATEOFBIRTHS,PRODUCTCODE,INITIATORID,BRANCH,RMCODE,MAKER_ID,MAKER_DT,STATUS,NVL(ACCOUNT_NO,' ') AS ACCTNO,AMOUNT,BVN,MOBILENO,CUST_STATUS,SYSDATE from ACCOUNT_OPEN_WEB WHERE TXNREFNO=? AND BVN=? AND AUTH_REFERENCENO=?";
				getlimitcodePstmt = connection.prepareStatement(limitcodeCountQry);
				getlimitcodePstmt.setString(1, referenceno);
				getlimitcodePstmt.setString(2, bvn);
				getlimitcodePstmt.setString(3, refno);
				getlimitcodeRs = getlimitcodePstmt.executeQuery();
				json = new JSONObject();

				if (getlimitcodeRs.next()) {				
					json.put("TXNREFNO", getlimitcodeRs.getString("NEW_TXNREFNO"));
				     json.put("F_NAME", getlimitcodeRs.getString("fname"));
				     json.put("M_NAME", getlimitcodeRs.getString("mname"));
				     json.put("L_NAME", getlimitcodeRs.getString("lname"));
				     json.put("GENDER", getlimitcodeRs.getString("GENDER"));
				     json.put("DATEOFBIRTH", getlimitcodeRs.getString("DATEOFBIRTHS"));
				     json.put("PRODUCTCODE", getlimitcodeRs.getString("PRODUCTCODE"));
				     json.put("INITIATORID", getlimitcodeRs.getString("INITIATORID"));
				     json.put("BRANCH", getlimitcodeRs.getString("BRANCH"));
				     json.put("RMCODE", getlimitcodeRs.getString("RMCODE"));
				     json.put("MAKER_ID", getlimitcodeRs.getString("MAKER_ID"));
				     json.put("MAKER_DT", getlimitcodeRs.getString("MAKER_DT"));
				     json.put("STATUS", getlimitcodeRs.getString("STATUS"));
				     json.put("ACCOUNT_NO", getlimitcodeRs.getString("ACCTNO"));
				     json.put("AMOUNT", getlimitcodeRs.getString("AMOUNT"));
				     json.put("BVN", getlimitcodeRs.getString("BVN"));
				     json.put("MOBILENO", getlimitcodeRs.getString("MOBILENO"));
				     json.put("CUST_STATUS", getlimitcodeRs.getString("CUST_STATUS"));
				     json.put("SYSDATE", getlimitcodeRs.getString("SYSDATE"));
				    // json.put("PAYBILL_STATUS", getlimitcodeRs.getString("PAYBILL_STATUS"));
				     
				}
				    //System.out.println("kailash here ::: "+json.getString("PAYBILL_STATUS"));
				    if(decision.equalsIgnoreCase("Approval")){
				    	
				    	if((json.getString("CUST_STATUS")).equalsIgnoreCase("Account Credit Pending")){
				    		
				    		JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.AccountOpenFundTransfer(json.getString("ACCOUNT_NO"), json.getString("AMOUNT"), requestJSON.getString("makerId"), json.getString("TXNREFNO")));
					    	if(!(json2.getString("respcode")).equalsIgnoreCase("00")){
					    		
					    		if(json2.has("respdesc")) {
					    			resultJson.put("Message",json2.getString("respdesc"));
					    		}
					    		
					    		
								
								
								/*pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='F',STATUS='INIFUNDFAIL' WHERE  BVN=? AND NEW_TXNREFNO=?");
								pstmt.setString(1, json.getString("BVN"));
								pstmt.setString(2, json.getString("TXNREFNO"));
								pstmt.executeUpdate();*/
								
								pstmt = connection.prepareStatement("UPDATE ACCOUNT_OPEN_WEB SET STATUS='F',RESPONSE_CODE=?,RESPONSE_MSG=? WHERE AUTH_REFERENCENO=?");
								pstmt.setString(1, json2.getString("respcode"));
								pstmt.setString(2, json2.toString());
								pstmt.setString(3, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='F' WHERE  BVN=? AND NEW_TXNREFNO=?");
								pstmt.setString(1, json.getString("BVN"));
								pstmt.setString(2, json.getString("TXNREFNO"));
								pstmt.executeUpdate();
								
								pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_DTTM=SYSDATE,CHECKER_ID=? WHERE REF_NUM=?");
								pstmt.setString(1, requestJSON.getString(CevaCommonConstants.MAKER_ID));
								pstmt.setString(2, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
							}else{
								
								pstmt = connection.prepareStatement("UPDATE ACCOUNT_OPEN_WEB SET STATUS='C',RESPONSE_CODE=?,RESPONSE_MSG=? WHERE AUTH_REFERENCENO=?");
								pstmt.setString(1, json2.getString("respcode"));
								pstmt.setString(2, json2.getString("respdesc"));
								pstmt.setString(3, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='C',STATUS='SUCCESS' WHERE  BVN=? AND NEW_TXNREFNO=?");
								pstmt.setString(1, json.getString("BVN"));
								pstmt.setString(2, json.getString("TXNREFNO"));
								pstmt.executeUpdate();
								
								pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_DTTM=SYSDATE,CHECKER_ID=? WHERE REF_NUM=?");
								pstmt.setString(1, requestJSON.getString(CevaCommonConstants.MAKER_ID));
								pstmt.setString(2, refno);
								pstmt.executeUpdate();
								pstmt.close();
							}	
				    		
				    		
				    	
				    		
				    	
				    	
				    	}else{
				    		
				    		JSONObject json2 = JSONObject.fromObject(ServiceRequestClient.AccountOpen(
				    				json.getString("F_NAME"), json.getString("M_NAME"),
				    				json.getString("L_NAME"), json.getString("GENDER"),
				    				json.getString("DATEOFBIRTH"), json.getString("MOBILENO"),
				    				json.getString("PRODUCTCODE"), requestJSON.getString("makerId"),
				    				json.getString("BRANCH"), json.getString("RMCODE"),
				    				json.getString("BVN"), json.getString("AMOUNT"),
				    				json.getString("TXNREFNO"), json.getString("SYSDATE")));
				    		
					    	if((json2.getString("respcode")).equalsIgnoreCase("00")){
								resultJson.put("Message",json2.getString("respdesc"));
								pstmt = connection.prepareStatement("UPDATE ACCOUNT_OPEN_WEB SET STATUS='C',RESPONSE_CODE=?,RESPONSE_MSG=? WHERE AUTH_REFERENCENO=?");
								pstmt.setString(1, json2.getString("respcode"));
								pstmt.setString(2, json2.toString());
								pstmt.setString(3, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='C',STATUS='SUCCESS' WHERE  STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') AND BVN=? AND NEW_TXNREFNO=?");
								pstmt.setString(1, json.getString("BVN"));
								pstmt.setString(2, json.getString("TXNREFNO"));
								pstmt.executeUpdate();
								
								pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_DTTM=SYSDATE,CHECKER_ID=? WHERE REF_NUM=?");
								pstmt.setString(1, requestJSON.getString(CevaCommonConstants.MAKER_ID));
								pstmt.setString(2, refno);
								pstmt.executeUpdate();
								pstmt.close();
								
							}else{
								resultJson.put("Message",json2.getString("respdesc"));
								
								pstmt = connection.prepareStatement("UPDATE ACCOUNT_OPEN_WEB SET STATUS='F',RESPONSE_CODE=?,RESPONSE_MSG=? WHERE AUTH_REFERENCENO=?");
								pstmt.setString(1, json2.getString("respcode"));
								pstmt.setString(2, json2.toString());
								pstmt.setString(3, refno);

								pstmt.executeUpdate();
								pstmt.close();
								
								pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='F' WHERE  STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') AND BVN=? AND NEW_TXNREFNO=?");
								pstmt.setString(1, json.getString("BVN"));
								pstmt.setString(2, json.getString("TXNREFNO"));
								pstmt.executeUpdate();
								
								pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='C',CHECKER_DTTM=SYSDATE,CHECKER_ID=? WHERE REF_NUM=?");
								pstmt.setString(1, requestJSON.getString(CevaCommonConstants.MAKER_ID));
								pstmt.setString(2, refno);
								pstmt.executeUpdate();
								pstmt.close();
							}
				    		
				    	}
						
						JSONObject json1 = new JSONObject();
						json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
						json1.put("transCode", "ApprovalAll");
						json1.put("channel", "WEB");
						json1.put("message", "Acknowledgment :: Approval");
						json1.put("ip", requestJSON.getString("remoteip"));
						json1.put("det1", "");
						json1.put("det2", "");
						json1.put("det3", "");
						
						CommonServiceDao csd=new CommonServiceDao();
						csd.auditTrailInsert(json1);
						
						
					}else{
						
						pstmt = connection.prepareStatement("UPDATE ACCOUNT_OPEN_WEB SET STATUS='R' WHERE AUTH_REFERENCENO=?");
						pstmt.setString(1, refno);
						pstmt.executeUpdate();
						pstmt.close();
						
						pstmt = connection.prepareStatement("UPDATE PAYSTACK_TBL SET W_STATUS='R' WHERE  STATUS in ('DEBITSUCCESS','VERIFY','OTPVALFAIL') AND BVN=? AND NEW_TXNREFNO=?");
						pstmt.setString(1, json.getString("BVN"));
						pstmt.setString(2, json.getString("TXNREFNO"));
						pstmt.executeUpdate();
						
						pstmt = connection.prepareStatement("UPDATE AUTH_PENDING SET STATUS='R' WHERE REF_NUM=?");
						pstmt.setString(1, refno);
						pstmt.executeUpdate();
						pstmt.close();
						
						JSONObject json1 = new JSONObject();
						json1.put(CevaCommonConstants.MAKER_ID, requestJSON.getString("makerId"));
						json1.put("transCode", "ApprovalAll");
						json1.put("channel", "WEB");
						json1.put("message", "Acknowledgment :: Account Open Reject");
						json1.put("ip", requestJSON.getString("remoteip"));
						json1.put("det1", "");
						json1.put("det2", "");
						json1.put("det3", "");
						
						CommonServiceDao csd=new CommonServiceDao();
						csd.auditTrailInsert(json1);
						
						
					}
					
					connection.commit();
				    
				    
				    resultJson.put("limitcodedetails2", JsonArray);
				    limitcodeDataMap.put(CevaCommonConstants.BINGRP_INFO, resultJson);
				    logger.debug("limitcodeDataMap[" + limitcodeDataMap + "]");
				    responseDTO.setData(limitcodeDataMap);

			} catch (SQLException e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("SQLException in checkfeecode [" + e.getMessage() + "]");
			} catch (Exception e) {
				responseDTO.addError("Internal Error Occured While Executing.");
				logger.debug("Exception in checkfeecode [" + e.getMessage() + "]");
			} finally {
				try {

					if (getlimitcodeRs != null) {
						getlimitcodeRs.close();
					}

					if (getlimitcodePstmt != null) {
						getlimitcodePstmt.close();
					}

					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				limitcodeDataMap = null;
				resultJson = null;
			}
			return responseDTO;
	
}
		
		public static void main(String[] args) {
			
			
			RequestDTO requestDTO = new RequestDTO();

			JSONObject requestJSON = new  JSONObject();
			
			requestJSON.put("productCode","PRD011");
			requestDTO.setRequestJSON(requestJSON);
			
			ProductManDAO productDAO = new ProductManDAO();
			//productDAO.fetchProductCreatePageInfo(requestDTO);

			productDAO.fetchProductModifyPageInfo(requestDTO);
			
			/*requestJSON = JSONObject.fromObject("{'productCode':'PRD011','productDesc':'sravan product desc','binCurrency':'AUD','limitCode':'sdfsfd','feeCode':'ggggkk','makerId':'CEVABO'}");
			
			requestDTO.setRequestJSON(requestJSON);
			
			//productDAO.inserProductModifyDetails(requestDTO);
			
			productDAO.fetchProductViewInfo(requestDTO);*/
			
		}
		
		
	}


 
