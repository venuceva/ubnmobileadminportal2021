package com.ceva.base.agent.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ceva.base.common.bean.LocalGovernment;
import com.ceva.base.common.bean.State;
import com.ceva.base.common.dao.AgentDAO;
import com.ceva.base.common.dao.FileUploadDAO;
import com.ceva.base.common.dao.POSDetailsDAO;
import com.ceva.base.common.dao.impl.LocalGovernmentDaoImpl;
import com.ceva.base.common.dao.impl.StateDaoImpl;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;
import com.ceva.base.common.spec.dao.LocalGovernmentDAO;
import com.ceva.base.common.spec.dao.StateDAO;
import com.ceva.base.common.utils.CevaCommonConstants;
import com.ceva.unionbank.channel.ExcelParsing;
import com.ceva.unionbank.channel.FileValidator;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class FileUploadAction extends ActionSupport implements ServletRequestAware{
	Logger logger = Logger.getLogger(FileUploadAction.class);
	
	private HttpSession session = null;	
	
	 JSONObject requestJSON = null;
	 JSONObject responseJSON = null;

	 RequestDTO requestDTO = null;
	 ResponseDTO responseDTO = null;
	 
	 private String customercode;
	 private String application;
	 
	 private String actiontype;
	 private String userid;
	 
	 private String PageDirection = null;
	 
	 
	 private String accountno = null;
	 private String walletaccountno = null;
	 private String onboarddate = null;
	 private String fullname = null;
	 private String lastname = null;
	 private String middlename = null;
	 private String branchcode = null;
	 private String dateofbirth = null;
	 private String email = null;
	 private String telephone = null;
	

	private String gender = null;
	 private String status = null;
	 
	 private String addressLine = null;
	 private String localGovernment = null;
	 private String state = null;
	 private String country = null;
	 
	 private String terminalmake = null;
	 private String modelnumber = null;
	 private String serialnumber = null;
	 
	 private String storeid;
	 private String storename;
	 private String terminalid;
	 
	 private String limitcode;
	 private String limitdesc;
	 
	 private String records;
	 private String refno;
	 
	 private String requesttype;
	 private String reason;
	 
	 private String filetype;
	 
	 
	 
	 
	 
	 public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getRequesttype() {
		return requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public String getLimitdesc() {
		return limitdesc;
	}

	public void setLimitdesc(String limitdesc) {
		this.limitdesc = limitdesc;
	}

	private File file;
	 private String fileContentType;
	 private String fileFileName;
	 private String filesPath;
	 
	 private String filename;
	 private String filerefno;
	 
	 
	 
	 
	 public String getFilerefno() {
		return filerefno;
	}

	public void setFilerefno(String filerefno) {
		this.filerefno = filerefno;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFilesPath() {
		return filesPath;
	}

	public void setFilesPath(String filesPath) {
		this.filesPath = filesPath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLimitcode() {
		return limitcode;
	}

	public void setLimitcode(String limitcode) {
		this.limitcode = limitcode;
	}

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	List<State> states;
	 List<LocalGovernment> localGovs;
	 
	 List<LocalGovernment> terminal;
	 List<LocalGovernment> model;

	 private String termilstatus = null;

	 public List<LocalGovernment> getTerminal() {
		return terminal;
	}

	public void setTerminal(List<LocalGovernment> terminal) {
		this.terminal = terminal;
	}

	public List<LocalGovernment> getModel() {
		return model;
	}

	public void setModel(List<LocalGovernment> model) {
		this.model = model;
	}

	public List<State> getStates() {
	 	return states;
	 }

	 public void setStates(List<State> states) {
	 	this.states = states;
	 }

	 public List<LocalGovernment> getLocalGovs() {
	 	return localGovs;
	 }

	 public void setLocalGovs(List<LocalGovernment> localGovs) {
	 	this.localGovs = localGovs;
	 }
	 
	 
	 public String getTelephone() {
			return telephone;
	 }

	 public void setTelephone(String telephone) {
			this.telephone = telephone;
	 }
	 
	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getWalletaccountno() {
		return walletaccountno;
	}

	public void setWalletaccountno(String walletaccountno) {
		this.walletaccountno = walletaccountno;
	}

	public String getOnboarddate() {
		return onboarddate;
	}

	public void setOnboarddate(String onboarddate) {
		this.onboarddate = onboarddate;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getLocalGovernment() {
		return localGovernment;
	}

	public void setLocalGovernment(String localGovernment) {
		this.localGovernment = localGovernment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTerminalmake() {
		return terminalmake;
	}

	public void setTerminalmake(String terminalmake) {
		this.terminalmake = terminalmake;
	}

	public String getModelnumber() {
		return modelnumber;
	}

	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getPageDirection() {
			return PageDirection;
	 }

	public void setPageDirection(String pageDirection) {
			PageDirection = pageDirection;
	}
	 
	 
	 
	 public String getActiontype() {
		return actiontype;
	}


	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getCustomercode() {
		return customercode;
	}


	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}


	public String getApplication() {
		return application;
	}


	public void setApplication(String application) {
		this.application = application;
	}


	public JSONObject getResponseJSON() {
			return responseJSON;
		}


		public void setResponseJSON(JSONObject responseJSON) {
			this.responseJSON = responseJSON;
		}
		
		private String result;
		
		
		private HttpServletRequest httpRequest = null;

		@Override
		public void setServletRequest(HttpServletRequest httpRequest) {
			this.httpRequest = httpRequest;
		}
		
		@SuppressWarnings("rawtypes")
		private JSONObject constructToResponseJson(HttpServletRequest httpRequest, JSONObject jsonObject) {

			Enumeration enumParams = null;
			logger.debug("Inside ConstructToResponseJson...");
			try {
				if (jsonObject == null)
					jsonObject = new JSONObject();
				
				enumParams = httpRequest.getParameterNames();
				while (enumParams.hasMoreElements()) {
					String key = (String) enumParams.nextElement();
					String val = httpRequest.getParameter(key);
					jsonObject.put(key, val);
				}

			} catch (Exception e) {
				logger.debug(" Exception in ConstructToResponseJson ["
						+ e.getMessage() + "]");
				e.printStackTrace();
			} finally {
				enumParams = null;
			}
			logger.debug(" jsonObject[" + jsonObject + "]");

			return jsonObject;
		}  
		
		public String fileUpload() {
			
			logger.debug("[FileUploadAction][fileUpload] Inside fileUpload... ");
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			ArrayList<String> messages = null;
			try {
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				  

					
					
					requestDTO.setRequestJSON(requestJSON);
				    logger.debug("[FileUploadAction][fileUpload]  Request DTO [" + requestDTO + "]");
				  
				    fudao = new FileUploadDAO();
					responseDTO = fudao.fileUpload(requestDTO);
				  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("VewDetails");
					logger.debug("[FileUploadAction][fileUpload]  Response JSON++++++++ [" + responseJSON + "]");
					
					
					
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
				logger.debug("[FileUploadAction][fileUpload] Exception in View details ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				if(fudao!=null)
					fudao = null;
			 		if(requestDTO!=null)
					 requestDTO = null;
			 		if(responseDTO!=null)
					 responseDTO = null;
			 		if(requestJSON!=null)
					 requestJSON = null;
			 		if(messages!=null)
					 messages = null;
			 		if(errors!=null)
					 errors = null;
			}

			return result;
		}

		public String fileUploadProcess()
		{
			 ArrayList<String> errors = null;
			 ArrayList<String> messages = null;
			 
			/*System.out.println("File Name is:"+getFileFileName());
	        System.out.println("File ContentType is:"+getFileContentType());
	        System.out.println("Files Directory is:"+filesPath);*/
	        ResourceBundle rb = null;
			try {
				//System.out.println("I am Here ...11.");
				addActionError("I don't know you, dont try to hack me!");
				//System.out.println("I am Here ....");
				
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode",limitcode);
				
				

				logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Request DTO [" + requestDTO + "]");

				rb = ResourceBundle.getBundle("auth");
				String filepath=rb.getString("STORE_FILE_PATH");
				
				
				if(saveFile(getFile(), getFileFileName(), filepath)){
					
					  responseJSON.put("limitcode", limitcode);
					  responseJSON.put("limitdesc", limitcode.split("-")[1]);
					  
					 
					  responseJSON.put("filename", getFileFileName());
					  responseJSON.put("records", FileValidator.getRecords(filepath+"/"+getFileFileName()));
					  
					  
				}
				
				result="success";	
				
			} catch (IOException e) {
				  result = "fail";
				  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
				  addActionError("Internal error occured.");
			} finally{
		 		
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
	  
			}
			
			return result;
		}
		
		
		
		public String fileUploadProcessconf()
		{
			 ArrayList<String> errors = null;
			 ArrayList<String> messages = null;
			 FileUploadDAO fudao=null;
			
	        ResourceBundle rb = null;
			try {
				//System.out.println("I am Here ...11.");
				addActionError("I don't know you, dont try to hack me!");
				//System.out.println("I am Here ....");
				
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				requestJSON.put("limitcode", limitcode);
				responseJSON.put("limitdesc", limitcode.split("-")[1]);
				requestJSON.put("filename", filename);
				requestJSON.put("records", records);
				
				rb = ResourceBundle.getBundle("auth");
				String filepath=rb.getString("STORE_FILE_PATH");
				requestJSON.put("filepath", filepath);
				
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				fudao=new FileUploadDAO();
				
				responseDTO= fudao.gtFileUploadData(requestDTO);
				
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
					logger.debug("Response JSON***************************************** [" + responseJSON + "]");
					
					
					responseJSON.put("limitcode", limitcode);
					responseJSON.put("limitdesc", limitcode.split("-")[1]);
					responseJSON.put("filename", filename);
					responseJSON.put("records", records);
					
					result = "success";
					
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}


				
				
				result="success";	
				
			} catch (Exception e) {
				  result = "fail";
				  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
				  addActionError("Internal error occured.");
			} finally{
		 		
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
	  
			}
			
			return result;
		}
		
		
		public String fileUploadProcessack()
		{
			 ArrayList<String> errors = null;
			 ArrayList<String> messages = null;
			 FileUploadDAO fudao=null;
			
	        ResourceBundle rb = null;
			try {
				System.out.println("I am Here ...11.");
				addActionError("I don't know you, dont try to hack me!");
				System.out.println("I am Here ....");
				
				requestJSON = new JSONObject();
				responseJSON = new JSONObject();
				requestDTO = new RequestDTO();
				responseDTO = new ResponseDTO();
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
						session.getAttribute(CevaCommonConstants.MAKER_ID));
				
				requestJSON.put("limitcode", limitcode);
				
				requestJSON.put("filename", filename);
				requestJSON.put("records", records);
				//requestJSON.put("jsondata", jsondata);
				rb = ResourceBundle.getBundle("auth");
				String filepath=rb.getString("STORE_FILE_PATH");
				requestJSON.put("filepath", filepath);
				
				
				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				fudao=new FileUploadDAO();
				
				responseDTO= fudao.fileUploadProcessack(requestDTO);
				
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("Files_List");
					logger.debug("Response JSON***************************************** [" + responseJSON + "]");
					
					responseJSON.put("limitcode", limitcode);
					
					responseJSON.put("filename", filename);
					responseJSON.put("records", records);
					
					result = "success";
					
				} else {
					errors = (ArrayList<String>) responseDTO.getErrors();
					for (int i = 0; i < errors.size(); i++) {
						addActionError(errors.get(i));
					}
					result = "fail";
				}


				
				
				result="success";	
				
			} catch (Exception e) {
				  result = "fail";
				  logger.debug("[SalaryParameterAction][getVerifyClientidDetails] Exception in search ["+e.getMessage()+"]");
				  addActionError("Internal error occured.");
			} finally{
		 		
		 		if(requestDTO!=null)
				 requestDTO = null;
		 		if(responseDTO!=null)
				 responseDTO = null;
		 		if(requestJSON!=null)
				 requestJSON = null;
		 		if(messages!=null)
				 messages = null;
		 		if(errors!=null)
				 errors = null;
	  
			}
			
			return result;
		}
		
		
		
		public String fileUploadApprovalView(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				
			
				
				fudao = new FileUploadDAO();
				responseDTO = fudao.fileUploadApprovalView(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					//responseJSON.put("trans",trans);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}
		
		
		public String fileUploadApprovaldetails(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("application",application);
				
				fudao = new FileUploadDAO();
				responseDTO = fudao.fileUploadApprovaldetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					responseJSON.put("application",application);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}
		
		
		public String fileuploadrequestapprovalinfo(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("refno",refno);
				requestJSON.put("application",application);
				
				
				 fudao = new FileUploadDAO();
				responseDTO = fudao.fileuploadrequestapprovalinfo(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					responseJSON.put("refno",refno);
					responseJSON.put("application",application);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}
		
		
		public String fileuploadrequestinfoApprovalAck() {
			 logger.debug("[AgentRegAck][AgentRegAck] Inside AgentRegAck ..");
			 
			 
			 FileUploadDAO fudao=null;
			 ArrayList<String> errors = null;
			 
			 try {
				 requestJSON = new JSONObject();
					responseJSON = new JSONObject();
					requestDTO = new RequestDTO();
					responseDTO = new ResponseDTO();
					session = ServletActionContext.getRequest().getSession();
					requestJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
					
					
					
					requestJSON.put("filerefno", filerefno);
					requestJSON.put("requesttype", requesttype);
					requestJSON.put("filetype", filetype);					
					requestJSON.put("reason", reason);
					
					
				System.out.println("kailash here :: "+requestJSON);	
					
			  requestDTO.setRequestJSON(requestJSON);

			  logger.debug("[AgentRegAck][AgentRegAck] Request DTO [" + requestDTO + "]");

			  fudao = new FileUploadDAO();
			  
			  if(filetype.equalsIgnoreCase("UNSETTLED")) {
				  responseDTO = fudao.fileuploadrequestinfoApprovalAck(requestDTO); 
			  }
			  
			  if(filetype.equalsIgnoreCase("REVSUCC")) {
				  responseDTO = fudao.fileuploadsuccrevApprovalAck(requestDTO); 
			  }
			  
			  if(filetype.equalsIgnoreCase("TERMINAL")) {
				  responseDTO = fudao.fileuploadterminalApprovalAck(requestDTO); 
			  }
			  
			  if(filetype.equalsIgnoreCase("PREENROLMENT")) {
				  responseDTO = fudao.fileuploadPreEnrollementApprovalAck(requestDTO); 
			  }
			  
			  logger.debug("Response DTO is ["+responseDTO+"]");
			  if (responseDTO != null && responseDTO.getErrors().size() == 0) {
				  responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
				  
				  responseJSON.put("filerefno", filerefno);
				  responseJSON.put("requesttype", requesttype);
					
					
			

			   result = "success";
			  } else {
			   errors = (ArrayList<String>) responseDTO
			     .getErrors();
			   for (int i = 0; i < errors.size(); i++) {
			    addActionError(errors.get(i));
			   }
			   result = "fail";
			  }
			 } catch (Exception e) {
			  result = "fail";
			  logger.debug("[SalaryParameterAction][DefineReturnCodesAck]Exception in search ["+e.getMessage()+"]");
			  addActionError("Internal error occured.");
			 } finally{
				 if(fudao!=null)
					 fudao = null;
				 if(requestDTO!=null)
					 requestDTO = null;
				 if(responseDTO!=null)
					 responseDTO = null;
				 if(requestJSON!=null)
					 requestJSON = null;
				 if(errors!=null)
					 errors = null;
			 }

			 return result;

			}
		
		public static boolean saveFile(File file, String fileName, String filesDirectory) throws IOException{
			FileInputStream in = null;
	        FileOutputStream out = null;
	        boolean result=false;
	        
	        File dir = new File (filesDirectory);
	        if ( !dir.exists() )
	           dir.mkdirs();
	        
	        String targetPath =  dir.getPath() + File.separator + fileName+"/";
	        System.out.println("source file path ::"+file.getAbsolutePath());
	        System.out.println("saving file to ::" + targetPath);
	        File destinationFile = new File ( targetPath);
	        try {
	            in = new FileInputStream( file );
	            out = new FileOutputStream( destinationFile );
	            int c;

	            while ((c = in.read()) != -1) {
	                out.write(c);
	            }
	            result=true;
	        }catch(Exception e){
	        	result=false;
	        	e.printStackTrace();
	        }finally {
	            if (in != null) {
	                in.close();
	            }
	            if (out != null) {
	                out.close();
	            }
	        }
	      return result;  
		}
		
		public String ConfirmDetails()
		{
			logger.debug("inside ApplicationCode.. ");
			AgentDAO agdao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();

				requestDTO.setRequestJSON(requestJSON);
				logger.debug("Request DTO [" + requestDTO + "]");
				agdao = new AgentDAO();
			  responseDTO = agdao.ApplicationCode(requestDTO);
				 
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					
					responseJSON = (JSONObject) responseDTO.getData().get(
							CevaCommonConstants.BIN_INFO);
					
					logger.debug("Response JSON [" + responseJSON + "]");
					
					responseJSON = constructToResponseJson(httpRequest, responseJSON);
					
					logger.debug("Response JSON Inside Construct To ResponseJson  [" + responseJSON + "]");
					
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
				logger.debug("Exception in Create NewProduct ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				agdao = null;
				errors = null;
			}
			return result;
		}
		
		
		public String fileUploadResult(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				
			
				
				fudao = new FileUploadDAO();
				responseDTO = fudao.fileUploadResult(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					//responseJSON.put("trans",trans);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}
		
		
		public String fileUploadResultdetails(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("application",application);
				
				fudao = new FileUploadDAO();
				responseDTO = fudao.fileUploadResultdetails(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					responseJSON.put("application",application);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}
		
		public String fileUploadResultdetailsview(){
			 
			FileUploadDAO fudao=null;
			ArrayList<String> errors = null;
			try {
				requestJSON = new JSONObject();
				requestDTO = new RequestDTO();
				requestDTO.setRequestJSON(requestJSON);
				
				
				session = ServletActionContext.getRequest().getSession();

				requestJSON.put(CevaCommonConstants.MAKER_ID,
									session.getAttribute(CevaCommonConstants.MAKER_ID));
				requestJSON.put("remoteip",session.getAttribute("REMOTE_IP"));
				
				requestJSON.put("application",application);
				requestJSON.put("refno",refno);
				
				fudao = new FileUploadDAO();
				responseDTO = fudao.fileUploadResultdetailsview(requestDTO);
				logger.debug("Response DTO [" + responseDTO + "]");
				if (responseDTO != null && responseDTO.getErrors().size() == 0) {
					responseJSON = (JSONObject) responseDTO.getData().get("LMT_FEE_INFO");
					logger.debug("Response JSON  [" + responseJSON + "]");
					
					
					responseJSON.put(CevaCommonConstants.MAKER_ID,
							session.getAttribute(CevaCommonConstants.MAKER_ID));
					responseJSON.put("application",application);
					
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
				logger.debug("Exception in binCommonScreen  ["
						+ e.getMessage() + "]");
				addActionError("Internal error occured.");
			} finally {
				requestDTO = null;
				responseDTO = null;
				requestJSON = null;
				fudao = null;
				errors = null;
			}

			return result;
		}

}
