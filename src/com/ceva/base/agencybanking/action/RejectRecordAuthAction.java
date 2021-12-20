package com.ceva.base.agencybanking.action;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class RejectRecordAuthAction extends ActionSupport{
	
	private Logger logger=Logger.getLogger(RejectRecordAuthAction.class);
	
	ResponseDTO responseDTO = null;
	JSONObject requestJSON = null;
	JSONObject resonseJSON = null;
	JSONObject responseJSON = null;
	String result;
	private String RefNumber;
	private String serviceCode;
	private String serviceName;
	private String subServiceName;
	private HttpSession session = null;
	private String subServiceCode;
	private String FeeCodeslab;
	private String serviceType;
	private String multiSlabFeeDetails;
	private String acqdetails;
	private String feename;

	public String RejectServiceAuthconfm() {   
		logger.debug("Inside 	SearchSerialData... ");
		CallableStatement callableStatement = null;
		String queryConst = "{call REJECTRECORDPKG.SERVICERECORDREJECT(?,?,?,?)}";
		String message = "";
		Connection connection = null;

		try {
			connection = DBConnector.getConnection();
			
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, RefNumber);
			callableStatement.setString(2, serviceCode);
			callableStatement.setString(3, serviceName);
			callableStatement.registerOutParameter(4, Types.VARCHAR);
			
			callableStatement.execute();

			logger.debug(" 	After Executing callableStatement.");

			message = callableStatement.getString(4);
			//setStatus(callableStatement.getString(3));

			logger.debug(" 	 After Executing message[" + message + "] "
					+ "status[" + callableStatement.getString(4) + "]");

			logger.debug("After Executing query.");

		} catch (Exception e) {
		//	setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			queryConst = null;
			message = null;
		}

	

		return SUCCESS;
	}
	
	
	public String RejectSubServiceAuthconfm() {   
		logger.debug("Inside 	SearchSerialData... ");
		CallableStatement callableStatement = null;
		String queryConst = "{call REJECTRECORDPKG.SUBSERVICERECORDREJECT(?,?,?)}";
		String message = "";
		Connection connection = null;

		try {
			connection = DBConnector.getConnection();
	
			callableStatement = connection.prepareCall(queryConst);
			callableStatement.setString(1, RefNumber);
			callableStatement.setString(2, subServiceName);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			
			callableStatement.execute();

			logger.debug(" 	After Executing callableStatement.");

			message = callableStatement.getString(4);
		

			logger.debug(" 	 After Executing message[" + message + "] "
					+ "status[" + callableStatement.getString(4) + "]");

			logger.debug("After Executing query.");

		} catch (Exception e) {
		//	setStatus("NO");
			logger.debug(" exception is  : " + e.getMessage());
		} finally {
			DBUtils.closeConnection(connection);
			DBUtils.closeCallableStatement(callableStatement);
			queryConst = null;
			message = null;
		}

	

		return SUCCESS;
	}
	
	
	
	public ResponseDTO RejectFeeCodeAuthconfm(RequestDTO requestDTO) {

		logger.debug(" Inside Update FeeSlabDetails..... ");
		Connection connection = null;

		String userQry = "{call REJECTRECORDPKG.FEEREJECTSLABDETAILSPROC(?,?,?,?,?,?,?,?,?)}";

		String error = "";

		CallableStatement callable = null;

		try {
			responseDTO = new ResponseDTO();
			requestJSON = requestDTO.getRequestJSON();
			session = ServletActionContext.getRequest().getSession();
			logger.debug("Request JSON [" + requestJSON + "]");
			responseJSON = requestJSON;

			connection = DBConnector.getConnection();
			logger.debug("connection is null [" + connection == null + "]");
			session = ServletActionContext.getRequest().getSession();
			requestJSON.put(CevaCommonConstants.MAKER_ID,
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			callable = connection.prepareCall(userQry);
			callable.setString(1,RefNumber);
			callable.setString(2, serviceCode);
			callable.setString(3, subServiceCode);
			callable.setString(4, FeeCodeslab);
			callable.setString(5, serviceType);
			callable.setString(6,multiSlabFeeDetails);
			callable.setString(7,acqdetails);
			callable.registerOutParameter(8, OracleTypes.INTEGER);
			callable.setString(9,feename);
			callable.execute();
			error = callable.getString(8);

			logger.debug("Update Fee Details successfully "
					+ "with error_message[" + error + "]");

			if(error.equals("SUCCESS")){
 			} else {
				responseDTO.addError(error);
			}

		} catch (SQLException e) {
			logger.debug("SQLException in UpdateFee Ack ["
					+ e.getMessage() + "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception in MerchantFeeAssignAck  [" + e.getMessage()
					+ "]");
			responseDTO.addError("Internal Error Occured.");
			e.printStackTrace();
		} finally {
			DBUtils.closeCallableStatement(callable);
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}
			userQry = null;
		}
		return responseDTO;

	}
	public JSONObject getRequestJSON() {
		return requestJSON;
	}
	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
	}
	public JSONObject getResonseJSON() {
		return resonseJSON;
	}
	public void setResonseJSON(JSONObject resonseJSON) {
		this.resonseJSON = resonseJSON;
	}
	public JSONObject getResponseJSON() {
		return responseJSON;
	}
	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}
	public String getRefNumber() {
		return RefNumber;
	}
	public void setRefNumber(String refNumber) {
		RefNumber = refNumber;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getSubServiceName() {
		return subServiceName;
	}


	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}


	public HttpSession getSession() {
		return session;
	}


	public void setSession(HttpSession session) {
		this.session = session;
	}


	public String getSubServiceCode() {
		return subServiceCode;
	}


	public void setSubServiceCode(String subServiceCode) {
		this.subServiceCode = subServiceCode;
	}


	public String getFeeCodeslab() {
		return FeeCodeslab;
	}


	public void setFeeCodeslab(String feeCodeslab) {
		FeeCodeslab = feeCodeslab;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getMultiSlabFeeDetails() {
		return multiSlabFeeDetails;
	}


	public void setMultiSlabFeeDetails(String multiSlabFeeDetails) {
		this.multiSlabFeeDetails = multiSlabFeeDetails;
	}


	public String getAcqdetails() {
		return acqdetails;
	}


	public void setAcqdetails(String acqdetails) {
		this.acqdetails = acqdetails;
	}


	public String getFeename() {
		return feename;
	}


	public void setFeename(String feename) {
		this.feename = feename;
	}

}
