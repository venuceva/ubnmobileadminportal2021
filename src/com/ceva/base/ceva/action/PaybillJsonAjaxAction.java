 package com.ceva.base.ceva.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.PayBillBean;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PaybillJsonAjaxAction extends ActionSupport implements
		ModelDriven<PayBillBean> {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(PaybillJsonAjaxAction.class);

	private JSONObject responseJSON = null;

	@Autowired
	private PayBillBean payBillBean;

	private HttpSession session;

	@Override
	public String execute() throws Exception {
		logger.debug("Inside Execute Method.");
		String result = ERROR;
		try {

			session = ServletActionContext.getRequest().getSession();
			logger.debug("Execute method [" + payBillBean.getMethod() + "] ");
			if (payBillBean.getMethod().equalsIgnoreCase("searchBfubAccounts")) {
				result = searchBfubAccounts();
			}
			else if (payBillBean.getMethod().equalsIgnoreCase("getInstCategories")) {
				result = getInstCategories();
			}
			else if(payBillBean.getMethod().equalsIgnoreCase("checkBfubAction")){
				result = checkBfubAction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception Inside Execute IS [" + e.getMessage() + "]");
		} finally {

		}
		return result;
	}

	private String getInstCategories() {

		try {
			logger.info("institute is ..:"+payBillBean.getInstitute());
			responseJSON = getBillerCategoriesToSelectBox(payBillBean.getInstitute());
			payBillBean.setSelectBoxData(responseJSON+"");
			logger.debug("After Executing query...:"+responseJSON);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
		}

		return SUCCESS;
	}
	public JSONObject getBillerCategoriesToSelectBox(String param) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		JSONArray object=null;
		try{
			responseJSON = new JSONObject();
			String prodQry = null;
			if(!"".equals(param)){
				prodQry = "Select distinct CAT_ID as key, CAT_ID||'-'||CAT_NAME as value from BILLER_CATEGORY WHERE upper(INST_ID)=upper('"+param +"') order by to_number(CAT_ID) ";
			}else{
				prodQry = "Select distinct CAT_IDas key, CAT_ID||'-'||CAT_NAME as value from BILLER_CATEGORY order by to_number(CAT_ID)";
			}
			//String prodQry = "Select distinct CAT_ID,CAT_ID||'-'||CAT_NAME from BILLER_CATEGORY order by to_number(CAT_ID) WHERE CAT_ID=?";
			connection = connection==null ? DBConnector.getConnection():connection;
			pstmt= connection.prepareStatement(prodQry);
			rs = pstmt.executeQuery();
			object= ResultSetConvertor.convertResultSetToJsonObjectArray(rs);
			responseJSON.put(CevaCommonConstants.BILLER_CATEGORY_LIST, object);

		}catch(Exception e){
			logger.error("Error occured.."+e.getLocalizedMessage());
			e.printStackTrace();

		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
		}
		//logger.info("BILLERS..:"+responseJSON);
		return responseJSON;
	}
	public String checkBfubAction(){
		CallableStatement callableStatement = null;
		String queryConst = "";
		Connection connection = null;

		try {

		    queryConst = "{call MPESAPAYBILLPKG.MODBIACCTCHECK(?,?,?)}";

			connection = DBConnector.getConnection();
			logger.debug("Query Const [" + queryConst + "]");
			callableStatement = connection.prepareCall(queryConst);

			callableStatement.setString(1, payBillBean.getBillerIdType());
			callableStatement.setString(2, payBillBean.getBfubCreditAccount());
			callableStatement.registerOutParameter(3, Types.VARCHAR);

			callableStatement.execute();

			logger.debug("After Executing callableStatement.");

			//payBillBean.setMessage(callableStatement.getString(3));

			logger.debug("After Executing query.");

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	public String searchBfubAccounts() {
		logger.debug("Inside Search Bfub Accounts.. ");
		CallableStatement callableStatement = null;
		String queryConst = "";
		Connection connection = null;

		try {
//
//			if (payBillBean.getType().contains("BILLERTYPE")) {
//				queryConst = "{call MPESAPAYBILLPKG.BFUBTYPEACCTCHECK(?,?,?,?,?,?,?,?,?)}";
//			} else {
//				queryConst = "{call MPESAPAYBILLPKG.BFUBIDACCTCHECK(?,?,?,?,?,?,?,?,?,?)}";
//			}
//
//			connection = DBConnector.getConnection();
//			logger.debug("Query Const [" + queryConst + "]");
//			callableStatement = connection.prepareCall(queryConst);
//			callableStatement.setString(1, payBillBean.getBfubCreditAccount());
//			callableStatement.setString(2, payBillBean.getBfubDebitAccount());
//			callableStatement.setString(3, (String) session.getAttribute(CevaCommonConstants.MAKER_ID));
//			callableStatement.setString(4, payBillBean.getOperator());
//			callableStatement.setString(5, payBillBean.getBillerType());
//			callableStatement.setString(6, payBillBean.getType());
//			callableStatement.registerOutParameter(7, Types.VARCHAR);
//			callableStatement.registerOutParameter(8, Types.VARCHAR);
//			callableStatement.registerOutParameter(9, Types.VARCHAR);
//
//			if (!payBillBean.getType().contains("BILLERTYPE")) {
//				callableStatement.setString(10, payBillBean.getBillerId());
//			}
//
//			callableStatement.execute();
//
//			logger.debug("After Executing callableStatement.");
//
//			logger.debug("After Executing Message ["
//					+ callableStatement.getString(8) + "] Status ["
//					+ callableStatement.getString(7) + "] Master Status["
//					+ callableStatement.getString(9) + "]");
//
//			payBillBean.setStatus(callableStatement.getString(7));
//			payBillBean.setMessage(callableStatement.getString(8));
//			payBillBean.setMasterStatus(callableStatement.getString(9));
//
//			logger.debug("After Executing query.");
//
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("	  exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeCallableStatement(callableStatement);
			DBUtils.closeConnection(connection);
			queryConst = null;
		}

		return SUCCESS;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	public PayBillBean getPayBillBean() {
		return payBillBean;
	}

	public void setPayBillBean(PayBillBean payBillBean) {
		this.payBillBean = payBillBean;
	}

	@Override
	public PayBillBean getModel() {
		return payBillBean;
	}

}
