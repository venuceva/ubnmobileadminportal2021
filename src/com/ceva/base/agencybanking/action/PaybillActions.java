package com.ceva.base.agencybanking.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceva.base.common.bean.PayBillBean;
import com.ceva.base.common.dao.PayBillDAO;
import com.ceva.base.common.dao.impl.Channels;
import com.ceva.base.common.dao.impl.PayBillDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PaybillActions extends ActionSupport implements
		ServletRequestAware, ModelDriven<PayBillBean> {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(PaybillActions.class);

	private String result;
	private RequestDTO requestDTO = null;
	private ResponseDTO responseDTO = null;

	private JSONObject requestJSON = null;
	private JSONObject responseJSON = null;

	private HttpServletRequest httpRequest;
	private HttpSession session = null;

	private List<Channels> channels=null;
	private List<PayBillBean> paybillServices=null;

	private String ip = null;

	@Autowired
	private PayBillBean payBillBean = null;

	@Autowired
	private PayBillDAO payBillDAO;

	public String commonScreen() {
		logger.debug("Inside CommonScreen .. ");
		logger.debug(getModel().toString());
		return SUCCESS;
	}

	public String viewServices() {
		logger.debug("Inside viewServices .. ");
		String channelId = payBillBean.getInstitute();
		logger.debug("channelId .. "+channelId);
		String response =null;
		try{
			responseDTO = payBillDAO.getChannelServices(channelId);
			if(responseDTO.getErrors().size()==0){
				paybillServices = (List<PayBillBean>) responseDTO.getData().get(CevaCommonConstants.OBJECT);
				logger.debug("paybillServices .. "+paybillServices.size());
				response= SUCCESS;
			}
			else{
				addActionError("Error while fetching services for category.");
				response= ERROR;
			}
		}catch(Exception e){
			addActionError("Error..:"+e.getLocalizedMessage());
			response= ERROR;
		}finally{
			channelId = null;
			//responseDTO= null;
		}

		return response;
	}
	public String viewChannelCategory() {
		logger.debug("Inside viewServices .. ");
		String channelId = payBillBean.getInstitute();
		logger.debug("channelId .. "+channelId);
		String response =null;
		try{
			responseDTO = payBillDAO.getBillerById(channelId);
			if(responseDTO.getErrors().size()==0){
				paybillServices = (List<PayBillBean>) responseDTO.getData().get(CevaCommonConstants.OBJECT);
				logger.debug("paybillServices .. "+paybillServices.size());
				response= SUCCESS;
			}
			else{
				addActionError("Error while fetching services for category.");
				response= ERROR;
			}
		}catch(Exception e){
			addActionError("Error..:"+e.getLocalizedMessage());
			response= ERROR;
		}finally{
			channelId = null;
			//responseDTO= null;
		}

		return response;
	}
	public String listPaybillAccounts() {
		logger.debug("Inside List Paybill Accounts .. ");
		ArrayList<String> errors = null;
		try {
			requestDTO = new RequestDTO();

			logger.debug("Request DTO [" + requestDTO + "]");
			responseDTO = payBillDAO.getDashboard(requestDTO);
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.MERCHANT_DASHBOARD);
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			responseJSON.put("error_flag", "error");
			logger.debug("Exception in getAdminCraeteInfo [" + e.getMessage()
					+ "]");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return result;
	}

	public String billerDetails() {
		logger.debug("Inside Biller Details .. ");
		ArrayList<String> errors = null;
		try {
			requestDTO = new RequestDTO();
			logger.debug("Request DTO [" + requestDTO + "]");
			//responseDTO = payBillDAO.getBillerTypeDetails(requestDTO);
			responseDTO = payBillDAO.getChannelById("");
			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(CevaCommonConstants.BILLER);
				logger.debug("Response JSON [" + responseJSON + "]");
				//payBillBean.setResponseJSON(responseJSON);
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			responseJSON.put("error_flag", "error");
			logger.debug("Exception in getAdminCraeteInfo [" + e.getMessage()
					+ "]");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return result;
	}

	public String listChannels() {
		logger.debug("Inside List Biller Type .. ");
		ArrayList<String> errors = null;
		try {

			responseDTO = payBillDAO.getChannels();

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				setPayBillBean((PayBillBean) responseDTO.getData().get("payBillData"));
				channels = (List<Channels>) responseDTO.getData().get(CevaCommonConstants.OBJECT);
				logger.info("channels ..:"+channels.size());
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			responseJSON.put("error_flag", "error");
			logger.debug("Exception in Biller Type [" + e.getMessage() + "]");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}

		return result;
	}

	/*public String modifyBillerDetails() {
		logger.debug("Inside Modify Biller Details .. ");
		ArrayList<String> errors = null;
		PayBillDAO cevaPowerDAO = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");
			cevaPowerDAO = new PayBillDAO();

			responseDTO = cevaPowerDAO.getBillerTypeDetails(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BILLER);

				logger.debug("Response JSON [" + responseJSON + "]");
				responseDTO = null;

				responseDTO = cevaPowerDAO.modifyListBillerType(requestDTO);

				setPayBillBean((PayBillBean) responseDTO.getData().get(
						"payBillData"));

				//payBillBean.setResponseJSON(responseJSON);
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			// responseJSON.put("error_flag", "error");
			logger.debug("Exception in modifyBillerDetails [" + e.getMessage()
					+ "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			cevaPowerDAO = null;
			errors = null;
		}

		return result;
	}*/

	/*public String viewBillerDetails() {
		logger.debug("Inside View Biller Details .. ");
		ArrayList<String> errors = null;
		PayBillDAO cevaPowerDAO = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			cevaPowerDAO = new PayBillDAO();
			responseDTO = cevaPowerDAO.listBillerType(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				setPayBillBean((PayBillBean) responseDTO.getData().get("payBillData"));
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in View Biller Details [" + e.getMessage()
					+ "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			cevaPowerDAO = null;
			errors = null;
		}

		return result;
	}
*/
	/*public String actDeactBillerDetails() {
		logger.debug("Inside Activate Deactivate Biller Details .. ");
		ArrayList<String> errors = null;
		PayBillDAO cevaPowerDAO = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			cevaPowerDAO = new PayBillDAO();
			responseDTO = cevaPowerDAO.listBillerType(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				setPayBillBean((PayBillBean) responseDTO.getData().get(
						"payBillData"));
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in View Biller Details [" + e.getMessage()
					+ "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			cevaPowerDAO = null;
			errors = null;
		}

		return result;
	}
*/

	/*public String actDeactBillerDetailsAck() {
		logger.debug("Inside Activate Deactivate Biller Details Ack .. ");
		ArrayList<String> errors = null;
		PayBillDAO cevaPowerDAO = null;

		try {
			session = ServletActionContext.getRequest().getSession();

			requestDTO = new RequestDTO();
			requestJSON = new JSONObject();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);

			logger.debug("Request DTO [" + requestDTO + "]");

			cevaPowerDAO = new PayBillDAO();
			responseDTO = cevaPowerDAO.billerTypeActDeact(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");

			if (responseDTO != null && responseDTO.getErrors().size() == 0) {

				setPayBillBean((PayBillBean) responseDTO.getData().get(
						"payBillData"));
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			addActionError("Internal Error Occured, Please try again.");
			logger.debug("Exception in View Biller Details [" + e.getMessage()
					+ "]");
			e.printStackTrace();
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			cevaPowerDAO = null;
			errors = null;
		}

		return result;
	}*/

	public String saveBiller() {
		logger.debug("Inside BillerType Ack.");
		ArrayList<String> errors = null;
		PayBillDAO billerDao = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			billerDao = new PayBillDaoImpl();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setBean(payBillBean);
			requestDTO.setRequestJSON(requestJSON);

			responseDTO = billerDao.insertBillerChannelMap(requestDTO);

			logger.debug("Response DTO [" + responseDTO.toString() + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}

	/*public String billerIdAck() {
		logger.debug("Inside BillerID Ack.");
		ArrayList<String> errors = null;
		PayBillDAO billerDao = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			billerDao = new PayBillDAO();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			responseDTO = billerDao.insertBillerId(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BILLER_REL_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}*/

	/*public String billerTypeUpdateAck() {
		logger.debug("Inside BillerType Update Ack.");
		ArrayList<String> errors = null;
		PayBillDAO billerDao = null;
		try {
			session = ServletActionContext.getRequest().getSession();
			requestJSON = new JSONObject();
			requestDTO = new RequestDTO();
			billerDao = new PayBillDAO();

			requestJSON.put("payBillBean", payBillBean);
			requestJSON.put("makerId",
					session.getAttribute(CevaCommonConstants.MAKER_ID));
			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
					.getRequest().getRemoteAddr());

			requestDTO.setRequestJSON(requestJSON);
			responseDTO = billerDao.updateBillerType(requestDTO);

			logger.debug("Response DTO [" + responseDTO + "]");
			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				responseJSON = (JSONObject) responseDTO.getData().get(
						CevaCommonConstants.BILLER_REL_INFO);
				logger.debug("Response JSON [" + responseJSON + "]");
				result = "success";
			} else {
				errors = (ArrayList<String>) responseDTO.getErrors();
				for (int i = 0; i < errors.size(); i++) {
					addActionError(errors.get(i));
				}
				result = "fail";
			}
		} catch (Exception e) {
			result = "fail";
			logger.debug("Exception in Get BillerRelated Info ["
					+ e.getMessage() + "]");
			addActionError("Internal error occured.");
		} finally {
			requestDTO = null;
			responseDTO = null;
			requestJSON = null;
			errors = null;
		}
		return result;
	}*/


//	public String listBillerTypeBack() {
//		logger.debug("Inside List Biller Type .. ");
//		ArrayList<String> errors = null;
//		PayBillDAO billerDao = null;
//		try {
//			session = ServletActionContext.getRequest().getSession();
//			requestJSON = new JSONObject();
//			requestDTO = new RequestDTO();
//
//			requestJSON.put("payBillBean", payBillBean);
//			requestDTO.setRequestJSON(requestJSON);
//
//			logger.debug("Request DTO [" + requestDTO + "]");
//			billerDao = new PayBillDAO();
//			responseDTO = billerDao.listBillerType(requestDTO);
//
//			logger.debug("Response DTO [" + responseDTO + "]");
//			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//
//				//setPayBillBean((PayBillBean) responseDTO.getData().get("payBillData"));
//				setPayBillBean(payBillBean);
//
//				// System.out.println(payBillBean.getBillerTypeDescription());
//				result = "success";
//			} else {
//				errors = (ArrayList<String>) responseDTO.getErrors();
//				for (int i = 0; i < errors.size(); i++) {
//					addActionError(errors.get(i));
//				}
//				result = "fail";
//			}
//		} catch (Exception e) {
//			result = "fail";
//			addActionError("Internal Error Occured, Please try again.");
//			responseJSON.put("error_flag", "error");
//			logger.debug("Exception in Biller Type [" + e.getMessage() + "]");
//		} finally {
//			requestDTO = null;
//			responseDTO = null;
//			requestJSON = null;
//			errors = null;
//		}
//
//		return result;
//	}
//
//
//
//	public String modifyBillerIdDetails(){
//
//		logger.debug("Inside Modify Biller ID Details .. ");
//		ArrayList<String> errors = null;
//		PayBillDAO cevaPowerDAO = null;
//
//		try {
//			session = ServletActionContext.getRequest().getSession();
//
//			requestDTO = new RequestDTO();
//			requestJSON = new JSONObject();
//
//			requestJSON.put("payBillBean", payBillBean);
//
//			requestJSON.put("makerId",
//					session.getAttribute(CevaCommonConstants.MAKER_ID));
//			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
//					.getRequest().getRemoteAddr());
//
//			requestDTO.setRequestJSON(requestJSON);
//
//			logger.debug("Request DTO [" + requestDTO + "]");
//			cevaPowerDAO = new PayBillDAO();
//
//			responseDTO = cevaPowerDAO.getBillerIDDetails(requestDTO);
//
//			logger.debug("Response DTO [" + responseDTO + "]");
//
//			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//
//			     setPayBillBean((PayBillBean) responseDTO.getData().get("modifyPayBillerIDData"));
//				 result = "success";
//
//			} else {
//				errors = (ArrayList<String>) responseDTO.getErrors();
//				for (int i = 0; i < errors.size(); i++) {
//					addActionError(errors.get(i));
//				}
//				result = "fail";
//			}
//		} catch (Exception e) {
//			result = "fail";
//			addActionError("Internal Error Occured, Please try again.");
//			logger.debug("Exception in Modify Biller Id Details [" + e.getMessage() + "]");
//			e.printStackTrace();
//		} finally {
//			requestDTO = null;
//			responseDTO = null;
//			requestJSON = null;
//			cevaPowerDAO = null;
//			errors = null;
//		}
//		return result;
//	}
//
//	public String modifyBillerIdDetailsCnf(){
//
//		result="success";
//		return result;
//
//	}
//
//	public String insertAddBillerId() {
//		logger.debug("Inside Insert Add  BillerID .");
//		ArrayList<String> errors = null;
//		PayBillDAO billerDao = null;
//		try {
//			session = ServletActionContext.getRequest().getSession();
//			requestJSON = new JSONObject();
//			requestDTO = new RequestDTO();
//			billerDao = new PayBillDAO();
//
//			requestJSON.put("payBillBean", payBillBean);
//			requestJSON.put("makerId",session.getAttribute(CevaCommonConstants.MAKER_ID));
//			requestJSON.put(CevaCommonConstants.IP, ServletActionContext.getRequest().getRemoteAddr());
//
//			requestDTO.setRequestJSON(requestJSON);
//			responseDTO = billerDao.insertAddBillerID(requestDTO);
//
//			logger.debug("Response DTO [" + responseDTO + "]");
//			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//				responseJSON = (JSONObject) responseDTO.getData().get(
//						CevaCommonConstants.BILLER_REL_INFO);
//				logger.debug("Response JSON [" + responseJSON + "]");
//				result = "success";
//			} else {
//				errors = (ArrayList<String>) responseDTO.getErrors();
//				for (int i = 0; i < errors.size(); i++) {
//					addActionError(errors.get(i));
//				}
//				result = "fail";
//			}
//		} catch (Exception e) {
//			result = "fail";
//			logger.debug("Exception in Get BillerRelated Info ["
//					+ e.getMessage() + "]");
//			addActionError("Internal error occured.");
//		} finally {
//			requestDTO = null;
//			responseDTO = null;
//			requestJSON = null;
//			errors = null;
//		}
//		return result;
//	}
//
//
//	public String updateModifyBillerIdDetails(){
//
//			logger.debug("Inside Update Modify BillerId Details.");
//			ArrayList<String> errors = null;
//			PayBillDAO billerDao = null;
//			try {
//				session = ServletActionContext.getRequest().getSession();
//				requestJSON = new JSONObject();
//				requestDTO = new RequestDTO();
//
//				billerDao = new PayBillDAO();
//
//				requestJSON.put("payBillBean", payBillBean);
//				requestJSON.put("makerId",
//						session.getAttribute(CevaCommonConstants.MAKER_ID));
//				requestJSON.put(CevaCommonConstants.IP, ServletActionContext
//						.getRequest().getRemoteAddr());
//
//				requestDTO.setRequestJSON(requestJSON);
//
//				responseDTO = billerDao.updateBillerID(requestDTO);
//
//				logger.debug("Response DTO [" + responseDTO + "]");
//				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//
//					/*setPayBillBean((PayBillBean) responseDTO.getData().get(
//							"payBillData"));*/
//					//logger.debug("Response JSON [" + responseJSON + "]");
//
//					result = "success";
//				} else {
//					errors = (ArrayList<String>) responseDTO.getErrors();
//					for (int i = 0; i < errors.size(); i++) {
//						addActionError(errors.get(i));
//					}
//					result = "fail";
//				}
//			} catch (Exception e) {
//				result = "fail";
//				logger.debug("Exception in Get BillerRelated Info ["
//						+ e.getMessage() + "]");
//				addActionError("Internal error occured.");
//			} finally {
//				requestDTO = null;
//				responseDTO = null;
//				requestJSON = null;
//				errors = null;
//			}
//			return result;
//		}
//
//
//   public String modifyBillerIdStatus() {
//
//		logger.debug("Inside Modify Biller ID Status .. ");
//		ArrayList<String> errors = null;
//		PayBillDAO cevaPowerDAO = null;
//
//		try {
//			session = ServletActionContext.getRequest().getSession();
//
//			requestDTO = new RequestDTO();
//			requestJSON = new JSONObject();
//
//			requestJSON.put("payBillBean", payBillBean);
//
//			requestJSON.put("makerId",
//					session.getAttribute(CevaCommonConstants.MAKER_ID));
//			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
//					.getRequest().getRemoteAddr());
//
//			requestDTO.setRequestJSON(requestJSON);
//
//			logger.debug("Request DTO [" + requestDTO + "]");
//			cevaPowerDAO = new PayBillDAO();
//
//			responseDTO = cevaPowerDAO.modifyBillerIDDetails(requestDTO);
//
//			logger.debug("Response DTO [" + responseDTO + "]");
//
//			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//
//			     setPayBillBean((PayBillBean) responseDTO.getData().get("modifyPayBillerIDData"));
//
//				result = "success";
//
//			} else {
//				errors = (ArrayList<String>) responseDTO.getErrors();
//				for (int i = 0; i < errors.size(); i++) {
//					addActionError(errors.get(i));
//				}
//				result = "fail";
//			}
//		} catch (Exception e) {
//			result = "fail";
//
//			addActionError("Internal Error Occured, Please try again.");
//
//			logger.debug("Exception in Modify Biller Id Details [" + e.getMessage()
//					+ "]");
//			e.printStackTrace();
//		} finally {
//			requestDTO = null;
//			responseDTO = null;
//			requestJSON = null;
//			cevaPowerDAO = null;
//			errors = null;
//		}
//
//		return result;
//	}
//
//
//
//   public String modifyBillerIdStatusAck() {
//		logger.debug("Inside Modify BillerId Status Ack .. ");
//		ArrayList<String> errors = null;
//		PayBillDAO cevaPowerDAO = null;
//
//		try {
//			session = ServletActionContext.getRequest().getSession();
//
//			requestDTO = new RequestDTO();
//			requestJSON = new JSONObject();
//
//			requestJSON.put("payBillBean", payBillBean);
//			requestJSON.put("makerId",
//					session.getAttribute(CevaCommonConstants.MAKER_ID));
//			requestJSON.put(CevaCommonConstants.IP, ServletActionContext
//					.getRequest().getRemoteAddr());
//
//			requestDTO.setRequestJSON(requestJSON);
//
//			logger.debug("Request DTO [" + requestDTO + "]");
//
//			cevaPowerDAO = new PayBillDAO();
//			responseDTO = cevaPowerDAO.billerIDStatusAck(requestDTO);
//
//			logger.debug("Response DTO [" + responseDTO + "]");
//
//			if (responseDTO != null && responseDTO.getErrors().size() == 0) {
//
//				 setPayBillBean((PayBillBean) responseDTO.getData().get(
//						  "payBillData"));
//
//				result = "success";
//
//			} else {
//				errors = (ArrayList<String>) responseDTO.getErrors();
//				for (int i = 0; i < errors.size(); i++) {
//					addActionError(errors.get(i));
//				}
//				result = "fail";
//			}
//		} catch (Exception e) {
//			result = "fail";
//			addActionError("Internal Error Occured, Please try again.");
//			logger.debug("Exception in Modify BillerId Status Ack [" + e.getMessage()
//					+ "]");
//			e.printStackTrace();
//		} finally {
//			requestDTO = null;
//			responseDTO = null;
//			requestJSON = null;
//			cevaPowerDAO = null;
//			errors = null;
//		}
//
//		return result;
//	}


	public JSONObject getRequestJSON() {
		return requestJSON;
	}

	public void setRequestJSON(JSONObject requestJSON) {
		this.requestJSON = requestJSON;
	}

	public JSONObject getResponseJSON() {
		return responseJSON;
	}

	public void setResponseJSON(JSONObject responseJSON) {
		this.responseJSON = responseJSON;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<Channels> getChannels() {
		return channels;
	}

	public void setChannels(List<Channels> channels) {
		this.channels = channels;
	}

	public List<PayBillBean> getPaybillServices() {
		return paybillServices;
	}

	public void setPaybillServices(List<PayBillBean> paybillServices) {
		this.paybillServices = paybillServices;
	}



}
