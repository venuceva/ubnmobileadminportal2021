<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 <link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/link/screen.css">
<link href="${pageContext.request.contextPath}/css/link/sticky.css" rel="stylesheet" type="text/css">

<style type="text/css">
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
.errors {
color: red;
}


.maxl{
  margin:25px ;
}
.inline{
  display: inline-block;
}
.inline + .inline{
  margin-left:10px;
}
.radio{
  color:#000000;
  font-size:12px;
  position:relative;
}
.radio span{
  position:relative;
   padding-left:20px;
}
.radio span:after{
  content:'';
  width:15px;
  height:15px;
  border:7px solid;
  position:absolute;
  left:0;
  top:1px;
  border-radius:100%;
  -ms-border-radius:100%;
  -moz-border-radius:100%;
  -webkit-border-radius:100%;
  box-sizing:border-box;
  -ms-box-sizing:border-box;
  -moz-box-sizing:border-box;
  -webkit-box-sizing:border-box;
}
.radio input[type="radio"]{
   cursor: pointer; 
  position:absolute;
  width:100%;
  height:100%;
  z-index: 1;
  opacity: 0;
  filter: alpha(opacity=0);
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)"
}
.radio input[type="radio"]:checked + span{
  color:#0987f7;  
}
.radio input[type="radio"]:checked + span:before{
    content:'';
  width:5px;
  height:5px;
  position:absolute;
  background:#f709af;
  left:5px;
  top:6px;
  border-radius:100%;
  -ms-border-radius:100%;
  -moz-border-radius:100%;
  -webkit-border-radius:100%;
}


</style>


<script type="text/javascript">

var fromMessage = "Enter from Date";
var toMessage = "Enter to Date";

var fromDateRules={required: true};
var toDateRules={required: true};

var fromDateMessages = {required: fromMessage};
var toDateMessages = {required: toMessage};

var userictadminrules = {
		
		rules :{
			date : { required : true},
			Report : { required : true}
		},
		messages :{
			
			date: { 
				required : "Please Enter Date."
			},
			Report: { 
			required : "Please Select Report."
			}
		}	
};

/* $(function() {
	$( "#fromdate" ).datepicker(
			
			{ 
				maxDate: '0', 
				beforeShow : function()
				{
					jQuery( this ).datepicker('option','maxDate', jQuery('#todate').val() );
				},
				altFormat: "dd/mm/yy", 
				dateFormat: 'dd/mm/yy'
				
			}
			
	);

	$( "#todate" ).datepicker( 
	
			{
				minDate: '0', 
				beforeShow : function()
				{
					jQuery( this ).datepicker('option','minDate', jQuery('#fromdate').val() );
				} , 
				altFormat: "dd/mm/yy", 
				dateFormat: 'dd/mm/yy'
				
			}
			
	);
}); */
function queryUser()
{
	/* if($("#Report").val()=="SIGNOFF REPORTS" && $('#referenceno').val()==""){
			$('#errormsg').text("Please select Reference Number");
	}else{
	
		$("#form1").validate(userictadminrules);
		if($("#form1").valid())
		{*/
				
				
				
				if($("#Report").val()=="users_onboard"){
					SEARCHBYDT="Search By  ";
					QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','N','Paritial Registion','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,MCF.AUTH_ID as AUTHID,decode(MCM.STATUS,'A','Using Mobile and USSD','U','Using Only USSD','N','Paritial Registion on USSD - Transaction pin not created','L','User Locked') as DETAILS,trunc(MCM.DATE_CREATED) as tdate,NVL((SELECT STAFF_ID from TELECLLERS_TBL TT where TT.CUST_ID=MCM.ID),' ')  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P'";
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(MCM.DATE_CREATED) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(MAD.BRANCH_CODE)=UPPER('"+($("#agentuserid").val()).split("-")[0]+"')";
						SEARCHBYDT=SEARCHBYDT+"And Branch : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And Branch : ALL";
					}
					
					if($('#status1').val()!=""){
						if($('#status1').val()=='A'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('A','U')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						if($('#status1').val()=='L'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('L')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						if($('#status1').val()=='N'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('N')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						}
					
					if($('#product1').val()!=""){
						
						QUERY=QUERY+" AND UPPER(MCM.M_PRD_CODE) =UPPER('"+$("#product1").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And product : "+$("#product1").val()+"";
						
					}
					
					QUERY=QUERY+" ORDER BY tdate DESC";
					$("#query").val(QUERY);
					$("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NUMBER,MAIL ID,STATUS,ONBOARD DATE,PRODUCT CODE,PRIMARY ACCOUNT NUMBER,BRANCH CODE,REGISTRATION,DETAILS,ONBOARD DATE,STAFF ID");
					$("#searchby").val(SEARCHBYDT);
				}
		
				if($("#Report").val()=="users_onboard_summary"){
					SEARCHBYDT="Search By  ";
					QUERY="select MAD.BRANCH_CODE,(select CLUSTER_NAME from CLUSTER_TBL where CLUSTER_ID=MAD.BRANCH_CODE) as Branch_name,Count(*)  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P'";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(MCM.DATE_CREATED) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(MAD.BRANCH_CODE)=UPPER('"+($("#agentuserid").val()).split("-")[0]+"')";
						SEARCHBYDT=SEARCHBYDT+"And Branch : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And Branch : ALL";
					}
					
					QUERY=QUERY+"  group by  MAD.BRANCH_CODE order by MAD.BRANCH_CODE ";
					$("#fieldsval").val("BRANCH CODE,BRANCH NAME,TOTAL USERS");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="WalletAccountOpenReport"){
					SEARCHBYDT="Search By  ";
					QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'F','Inactive','A','Active','U','Active','N','Paritial Registion','Blocked') as STATUS,MAD.DATE_CREATED as DATE_CREATED,MCM.W_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BALANCE as BALANCE,NVL(MCF.AUTH_ID,'USSD') as AUTHID,NVL(MAD.BY_CREATED,'CUSTOMER'),MAD.CREATED_BY "+ 
					"from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCM.W_APP_TYPE='WALLET' and MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='WALLET'";
					
					if($("#agentuserid").val()==""){
						
						$("#errormsg").text("Please Select Account Open By .");
						return false;
					}
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(MAD.DATE_CREATED) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(MAD.DATE_CREATED,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(MAD.DATE_CREATED,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					
					if($('#agentuserid').val()=="Agent"){
						//alert($('#agentuserid').val());
						QUERY=QUERY+" and NVL(MAD.BY_CREATED,'CUSTOMER')='AGENT'";
						$("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NO,EMAIL ID,STATUS,CREATED DATE,PRODUCT CODE,ACCOUNT NO,BALANCE,CHANNEL,CREATED TYPE,CREATED BY");
					}
					
					if($('#agentuserid').val()=="Self Registration"){
						//alert($('#agentuserid').val());
						QUERY=QUERY+" and NVL(MAD.BY_CREATED,'CUSTOMER')='CUSTOMER'";
						$("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NO,EMAIL ID,STATUS,CREATED DATE,PRODUCT CODE,ACCOUNT NO,BALANCE,CHANNEL,CREATED TYPE,CREATED BY");
					}
					
					if($('#agentuserid').val()=="ALL"){
						
						$("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NO,EMAIL ID,STATUS,CREATED DATE,PRODUCT CODE,ACCOUNT NO,BALANCE,CHANNEL,CREATED TYPE,CREATED BY");
					}
					
					/* if($('#searchid').val()!=""){
					//alert($('#searchid').val());
					
						if($('#searchval').val()==""){
							$("#errormsg").text("Please enter "+$('#searchop').text());
							return false;
						}else{
							if($('#searchop').text()=="Name"){
								QUERY=QUERY+" AND UPPER(MCM.FIRST_NAME)=UPPER('"+$('#searchval').val()+"')";
								
							}
							
							if($('#searchop').text()=="Account number"){
								QUERY=QUERY+" AND MAD.ACCT_NO=UPPER('"+$('#searchval').val()+"')";							
							}
							if($('#searchop').text()=="Agent Mobile No"){
								QUERY=QUERY+" AND MCF.CREATED_BY=UPPER('"+$('#searchval').val()+"')";	
							}
							if($('#searchop').text()=="Mobile number"){
								QUERY=QUERY+" AND MCF.MOBILE_NUMBER=UPPER('"+$('#searchval').val()+"')";	
							}

							
							
						}
					} */
					
					
					
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="WalletAgentReport"){
					SEARCHBYDT="Search By  ";
					QUERY="select MCM.ID as AGENTID,MCM.FIRST_NAME as FIRST_NAME,ACCOUNT_NO as CORE_BANK_ACC_NO,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID"+
						",NVL(MCM.GENDER,' '),MCM.W_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,NVL(MCF.AUTH_ID,'USSD') as AUTHID,"+
						"NVL(MCF.ADDRESS,' '),NVL(MCF.RL_LGA,' '),NVL(MCF.R_STATE,' '),MCF.BRANCH_CODE,decode(MCM.STATUS,'F','Inactive','A','Active','U','Active','N','Paritial Registion','Blocked') as STATUS,to_char(MCM.DATE_CREATED,'dd-mm-yyyy hh24:mi:ss') as DATE_CREATED,MCM.RM_CODE "+
						"from AGENT_CUSTOMER_MASTER MCM,AGENT_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and  MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='AGENT'";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
				if($("#agentuserid").val()==""){
						
						$("#errormsg").text("Please Select Super Agent .");
						return false;
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(MAD.DATE_CREATED) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(MAD.DATE_CREATED,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(MAD.DATE_CREATED,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()=="AIRTEL"){
						//alert($('#agentuserid').val());
						QUERY=QUERY+" AND SUPER_ADM='AIRTEL' ";
						
					}
					
					if($('#agentuserid').val()=="ALEDIN AGENCY"){
						//alert($('#agentuserid').val());
						QUERY=QUERY+" AND SUPER_ADM='ALEDIN_AGENCY' ";
						
					}
					
					if($('#agentuserid').val()=="UNION BANK CUSTOMER"){
						//alert($('#agentuserid').val());
						QUERY=QUERY+" AND SUPER_ADM='UNION_BANK_CUSTOMER' ";
						
					}
					
					$("#fieldsval").val("AGENT ID,NAME,CORE BANK ACCOUNT NO,USER ID,MOBILE NO,EMAIL ID,GENDER,PRODUCT CODE,WALLET ACCOUNT NO,CHANNEL,ADDRESS,LGA,STATE,BRANCH CODE,STATUS,CREATED DATE,INTRODUCER CODE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="WalletTransactionPosting_Report"){
					SEARCHBYDT="Search By  ";
					//QUERY="select ACCOUNT,AMOUNT,DECODE(DRCR_FLAG,'C','Credit','D','Debit') as CRDR,(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE AND APPLICATION='AGENT') as txn_type,TXN_REF_NO,CHANNEL,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NARRATION from WALLET_FIN_TXN_POSTING WFTP";
					
					if($('#report11').val()==""){
						$("#errormsg").text("Please select Report Type");
						return false;	
					}
					
					
					
					if($('#report11').val()=="Detailed report"){
						QUERY="select USER_ID,ACCOUNT,AMOUNT,DECODE(DRCR_FLAG,'C','Credit','D','Debit') as CRDR,WFTP.SERVICECODE as txn_type,TXN_REF_NO,CHANNEL,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NARRATION from WALLET_FIN_TXN_POSTING WFTP";

					
					
					

					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#report12").val()==""){
						
						$("#errormsg").text("Please Select Wallet Type .");
						return false;
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" where TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" where to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" where to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#report12').val()=="AGENT"){
						
						QUERY=QUERY+" AND USER_ID in (SELECT MOBILE_NUMBER FROM AGENT_CONTACT_INFO)";	
					}
					
					if($('#report12').val()=="WALLET"){
						
						QUERY=QUERY+" AND USER_ID in (SELECT MOBILE_NUMBER FROM MOB_CONTACT_INFO where CUST_TYPE in ('B','W'))";	
					}
					
					if($('#searchid').val()!=""){
						QUERY=QUERY+" AND SERVICECODE='"+$('#searchid').val()+"'";	
					}
					
					if($('#agentuserid').val()!=""){						
							QUERY=QUERY+" AND CHANNEL='"+$('#agentuserid').val()+"'";							
					}
					
					if($('#searchval').val()!=""){
						QUERY=QUERY+" AND USER_ID='"+$('#searchval').val()+"'";	
					}
					
					
					/* if($('#agentuserid').val()!=""){
						//alert(($('#agentuserid').val()).split("-")[0])
						if(($('#agentuserid').val()).split("-")[0]=="ALL"){
							SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];	
						}else{
						QUERY=QUERY+" AND WFTP.SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
						SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
						}
					} */
					
					/* if($('#searchid').val()!=""){
						
						
						if($('#searchval').val()==""){
							
								$("#errormsg").text("Please enter "+$('#searchop').text());
								return false;	
							
							
						}else{
							if($('#searchop').text()=="Name"){
								QUERY=QUERY+" AND WFTP.ACCOUNT in (select MAD.ACCT_NO  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCM.W_APP_TYPE='WALLET' and MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='WALLET' AND UPPER(MCM.FIRST_NAME)=UPPER('"+$('#searchval').val()+"'))";
								
							}
							
							if($('#searchop').text()=="Account number"){
								QUERY=QUERY+" AND WFTP.ACCOUNT=UPPER('"+$('#searchval').val()+"')";							
							}
							
							
							
							if($('#searchop').text()=="Customer Mobile number"){
								QUERY=QUERY+" AND WFTP.ACCOUNT in (select MAD.ACCT_NO  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCM.W_APP_TYPE='WALLET' and MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='WALLET' AND MCF.MOBILE_NUMBER=UPPER('"+$('#searchval').val()+"'))";
							}
							
							if($('#searchop').text()=="beneficiary account number"){
								QUERY=QUERY+" AND WFTP.ACCOUNT=UPPER('"+$('#searchval').val()+"')";	
							}
							
							if($('#searchop').text()=="Agent Mobile Number"){
								QUERY=QUERY+" AND WFTP.USER_ID=UPPER('"+$('#searchval').val()+"')";	
							}
							
							if($('#searchop').text()=="Payment reference no"){
								QUERY=QUERY+" AND WFTP.TXN_REF_NO=UPPER('"+$('#searchval').val()+"')";	
							}

							
							
						} 
					} */
					
					//alert(QUERY);
					$("#fieldsval").val("MOBILE NUMBER,ACCOUNT NUMBER,TXN AMOUNT,CREDIT/DEBIT,TRANSACTION TYPE,PAYMENT REFERENCE NO,CHANNEL,TRANSACTION DATE AND TIME,NARRATION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
					
					}
					
					if($('#report11').val()=="Summary"){
						QUERY="select CHANNEL,SERVICECODE,DECODE(DRCR_FLAG,'C','Credit','D','Debit'),SUM(AMOUNT),COUNT(*) from WALLET_FIN_TXN_POSTING WFTP";
						
						if($("#dateradio").val()==""){
							
							$("#errormsg").text("Please Select Time Period .");
							return false;
						}else if($("#dateradio").val()=="betdate"){
							if($("#fromdate").val()==""){
								$("#errormsg").text("Please enter From Date.");
								return false;
							}
							if($("#todate").val()==""){
								$("#errormsg").text("Please enter To Date.");
								return false;
							}
						}
						
						if($("#report12").val()==""){
							
							$("#errormsg").text("Please Select Wallet Type .");
							return false;
						}
						
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" where TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" where to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Current Month ";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" where to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
							SEARCHBYDT=SEARCHBYDT+" Quarterly ";
						}
						
						if($('#report12').val()=="AGENT"){
							
							QUERY=QUERY+" AND USER_ID in (SELECT MOBILE_NUMBER FROM AGENT_CONTACT_INFO)";	
						}
						
						if($('#report12').val()=="WALLET"){
							
							QUERY=QUERY+" AND USER_ID in (SELECT MOBILE_NUMBER FROM MOB_CONTACT_INFO where CUST_TYPE in ('B','W'))";	
						}
						
						if($('#searchid').val()!=""){
							QUERY=QUERY+" AND SERVICECODE='"+$('#searchid').val()+"'";	
						}
						
						if($('#agentuserid').val()!=""){						
								QUERY=QUERY+" AND CHANNEL='"+$('#agentuserid').val()+"'";							
						}
						
						if($('#searchval').val()!=""){
							QUERY=QUERY+" AND USER_ID='"+$('#searchval').val()+"'";	
						}
						
						/* if($('#agentuserid').val()!=""){
							//alert(($('#agentuserid').val()).split("-")[0])
							QUERY=QUERY+" AND WFTP.SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
							SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
							
						} */
						
						/* if($('#searchid').val()!=""){
							
							
							if($('#searchval').val()==""){
								
									$("#errormsg").text("Please enter "+$('#searchop').text());
									return false;	
								
								
							}else{
								if($('#searchop').text()=="Name"){
									QUERY=QUERY+" AND WFTP.ACCOUNT in (select MAD.ACCT_NO  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCM.W_APP_TYPE='WALLET' and MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='WALLET' AND UPPER(MCM.FIRST_NAME)=UPPER('"+$('#searchval').val()+"'))";
									
								}
								
								if($('#searchop').text()=="Account number"){
									QUERY=QUERY+" AND WFTP.ACCOUNT=UPPER('"+$('#searchval').val()+"')";							
								}
								
								
								
								if($('#searchop').text()=="Mobile number"){
									QUERY=QUERY+" AND WFTP.ACCOUNT in (select MAD.ACCT_NO  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,WALLET_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCM.W_APP_TYPE='WALLET' and MAD.PRIM_FLAG='P' and MAD.CUST_TYPE='WALLET' AND MCF.MOBILE_NUMBER=UPPER('"+$('#searchval').val()+"'))";
								}
								
								if($('#searchop').text()=="beneficiary account number"){
									QUERY=QUERY+" AND WFTP.ACCOUNT=UPPER('"+$('#searchval').val()+"')";	
								}
								
								if($('#searchop').text()=="Agent ID"){
									//QUERY=QUERY+" AND MCF.MOBILE_NUMBER=UPPER('"+$('#searchval').val()+"')";	
								}
								
								if($('#searchop').text()=="Payment reference no"){
									QUERY=QUERY+" AND WFTP.TXN_REF_NO=UPPER('"+$('#searchval').val()+"')";	
								}

								
								
							} 
						} */
						QUERY=QUERY+" group by CHANNEL,SERVICECODE,DECODE(DRCR_FLAG,'C','Credit','D','Debit') order by CHANNEL,SERVICECODE";	
						//alert(QUERY);
						$("#fieldsval").val("CHANNEL,TRANSACTION TYPE,CREDIT/DEBIT,TXN AMOUNT,COUNT");
						$("#query").val(QUERY);
						$("#searchby").val(SEARCHBYDT);

					}
					
					
					
				}
				
				if($("#Report").val()=="WalletTransaction_Report"){
					SEARCHBYDT="Search By  ";
					//QUERY="select ACCOUNT,AMOUNT,DECODE(DRCR_FLAG,'C','Credit','D','Debit') as CRDR,(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE AND APPLICATION='AGENT') as txn_type,TXN_REF_NO,CHANNEL,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NARRATION from WALLET_FIN_TXN_POSTING WFTP";
					
					if($('#report11').val()==""){
						$("#errormsg").text("Please select Report Type");
						return false;	
					}
					
					
					
					if($('#report11').val()=="Detailed report"){
						
						 if($('#report12').val()=="AGENT"){
						
							if($("#status").val()=="ALL"){
								QUERY=" select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION,NVL(json_value(JREQUEST,'$.jbody.extrainfo.cardinfo'),' ') from wallet_fin_txn WFTP,agent_customer_master acm,agent_contact_info aci where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";
							}
							
							if($("#status").val()=="SUCCESS"){
								
								 QUERY=" select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION,NVL(json_value(JREQUEST,'$.jbody.extrainfo.cardinfo'),' ') from wallet_fin_txn WFTP,agent_customer_master acm,agent_contact_info aci where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) and substr(WFTP.txn_ref_no,1,1)<>'R' and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";	
								}
							 
							 if($("#status").val()=="FAIL"){
									
								 QUERY="select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION,NVL(json_value(JREQUEST,'$.jbody.extrainfo.cardinfo'),' ') from wallet_fin_txn WFTP,agent_customer_master acm,agent_contact_info aci where txn_ref_no like 'R%' and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";	
								}
					
						 }
						 
						 if($('#report12').val()=="WALLET"){
							 
							 if($("#status").val()=="ALL"){
									QUERY=" select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION from wallet_fin_txn WFTP,mob_customer_master acm,mob_contact_info aci where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";
								}
								
								if($("#status").val()=="SUCCESS"){
									
									 QUERY=" select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION from wallet_fin_txn WFTP,mob_customer_master acm,mob_contact_info aci where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) and substr(WFTP.txn_ref_no,1,1)<>'R' and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";	
									}
								 
								 if($("#status").val()=="FAIL"){
										
									 QUERY="select WFTP.EXT_TXN_REF_NO,WFTP.USER_ID as userid,acm.first_name,WFTP.DR_ACCOUNT,WFTP.CR_ACCOUNT,NVL(WFTP.AMOUNT,0),(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE),WFTP.CHANNEL as cchannel,decode(substr(WFTP.txn_ref_no,1,1),'R','Failed','Success'),to_char(WFTP.TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE),ltrim(NVL(WFTP.TXN_AMT,0),0),NVL(WFTP.FEE_AMT,0),WFTP.DR_NARRATION from wallet_fin_txn WFTP,mob_customer_master acm,mob_contact_info aci where txn_ref_no like 'R%' and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id and aci.mobile_number=WFTP.USER_ID and acm.id=aci.cust_id";	
									}
						 }
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
				if($("#status").val()==""){
						
						$("#errormsg").text("Please Select Status .");
						return false;
					}
				
				if($("#report12").val()==""){
					
					$("#errormsg").text("Please Select Wallet Type .");
					return false;
				}
					
					
					 if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(WFTP.TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(WFTP.TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(WFTP.TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					 
					
					 
					 if($('#report12').val()=="AGENT"){
							
							QUERY=QUERY+" AND WFTP.SERVICECODE in (select SERVICECODE from BANK_SERVICE_CODE_MASTER where APPLICATION='AGENT')";	
						}
						
						if($('#report12').val()=="WALLET"){
							
							QUERY=QUERY+" AND WFTP.SERVICECODE in (select SERVICECODE from BANK_SERVICE_CODE_MASTER where APPLICATION='WALLET') ";	
						}
						
						 if($('#searchid').val()!=""){
							QUERY=QUERY+" AND WFTP.SERVICECODE='"+($('#searchid').val()).split("-")[1]+"'";	
						} 
						
						if($('#agentuserid').val()!="" && $('#agentuserid').val()!="ALL"){						
								QUERY=QUERY+" AND WFTP.CHANNEL='"+$('#agentuserid').val()+"' ";							
						}
						
						if($('#searchval').val()!=""){
							QUERY=QUERY+" AND WFTP.USER_ID='"+$('#searchval').val()+"'";	
						}
						
						
						
					 QUERY=QUERY+" order by TXN_STAMP desc";
					//alert(QUERY);
					$("#fieldsval").val("PAYMENT REFERENCE NO,USER ID,NAME,DEBIT ACCOUNT NO,CREDIT ACCOUNT NO,AMOUNT,DRCR FLAG,CHANNEL,RESPONSE MESSAGE,TRANSACTION DATE AND TIME,SERVICE NAME,TXN AMOUNT,FEE AMOUNT,NARRATION,PAN");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
					
					}
					
					if($('#report11').val()=="Summary"){
						
						
						if($("#status").val()=="ALL"){
							QUERY=" select (SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE) as SERVICECODE,(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE) as DRCRFLAG,CHANNEL as cchannel,decode(substr(txn_ref_no,1,1),'R','Failed','Success'),sum(NVL(AMOUNT,0)),count(*) from wallet_fin_txn WFTP where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) ";
						}
						
						if($("#status").val()=="SUCCESS"){
							
							 QUERY=" select (SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE) as SERVICECODE,(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE) as DRCRFLAG,CHANNEL as cchannel,decode(substr(txn_ref_no,1,1),'R','Failed','Success'),sum(NVL(AMOUNT,0)),count(*) from wallet_fin_txn WFTP where not exists (select 1 from wallet_fin_txn ft where ft.txn_ref_no='R'||WFTP.txn_ref_no) and substr(WFTP.txn_ref_no,1,1)<>'R' ";	
							}
						 
						 if($("#status").val()=="FAIL"){
								
							 QUERY="select (SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE) as SERVICECODE,(SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WFTP.SERVICECODE) as DRCRFLAG,CHANNEL as cchannel,decode(substr(txn_ref_no,1,1),'R','Failed','Success'),sum(NVL(AMOUNT,0)),count(*) from wallet_fin_txn WFTP where txn_ref_no like 'R%' ";	
							}
						
						 if($("#dateradio").val()==""){
								
								$("#errormsg").text("Please Select Time Period .");
								return false;
							}else if($("#dateradio").val()=="betdate"){
								if($("#fromdate").val()==""){
									$("#errormsg").text("Please enter From Date.");
									return false;
								}
								if($("#todate").val()==""){
									$("#errormsg").text("Please enter To Date.");
									return false;
								}
							}
							
						if($("#status").val()==""){
								
								$("#errormsg").text("Please Select Status .");
								return false;
							}
						
						if($("#report12").val()==""){
							
							$("#errormsg").text("Please Select Wallet Type .");
							return false;
						}
							
							
							 if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+" Current Month ";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
								SEARCHBYDT=SEARCHBYDT+" Quarterly ";
							}
							 
							
							 
							 if($('#report12').val()=="AGENT"){
									
									QUERY=QUERY+" AND SERVICECODE in (select SERVICECODE from BANK_SERVICE_CODE_MASTER where APPLICATION='AGENT')";	
								}
								
								if($('#report12').val()=="WALLET"){
									
									QUERY=QUERY+" AND SERVICECODE in (select SERVICECODE from BANK_SERVICE_CODE_MASTER where APPLICATION='WALLET') ";	
								}
								
								 if($('#searchid').val()!=""){
									QUERY=QUERY+" AND SERVICECODE='"+($('#searchid').val()).split("-")[1]+"'";	
								} 
								
								if($('#agentuserid').val()!="" && $('#agentuserid').val()!="ALL"){						
										QUERY=QUERY+" AND CHANNEL='"+$('#agentuserid').val()+"' ";							
								}
								
								if($('#searchval').val()!=""){
									QUERY=QUERY+" AND USER_ID='"+$('#searchval').val()+"'";	
								}
								
						 
						 
						 
						QUERY=QUERY+" group by SERVICECODE,CHANNEL,decode(substr(txn_ref_no,1,1),'R','Failed','Success') order by decode(substr(txn_ref_no,1,1),'R','Failed','Success'),CHANNEL,SERVICECODE";	
						//alert(QUERY);
						$("#fieldsval").val("TRANSACTION TYPE,DR/CR FLAG,CHANNEL,TXN STATUS,TXN AMOUNT,COUNT");
						$("#query").val(QUERY);
						$("#searchby").val(SEARCHBYDT);

					}
					
					
					
				}
				
				if($("#Report").val()=="WalletSuspense_Report"){
					
					if($('#report11').val()==""){
						$("#errormsg").text("Please select Report Type");
						return false;	
					}
					
					
					
					if($('#report11').val()=="Detailed report"){
					
					SEARCHBYDT="Search By  ";
					QUERY="select ACCOUNT,AMOUNT,DECODE(DRCR_FLAG,'C','Credit','D','Debit') as CRDR,WFTP.SERVICECODE as txn_type,TXN_REF_NO,CHANNEL,TO_CHAR(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),NARRATION from WALLET_FIN_TXN_POSTING WFTP";

					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" where TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" where to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" where to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!=""){
						//alert(($('#agentuserid').val()).split("-")[0])
						QUERY=QUERY+" AND ACCOUNT in (SELECT ACCT_NO from WALLET_SUSPENSION_DATA where ACCT_NAME='"+$('#agentuserid').val()+"')";
						SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
						
					   }
					QUERY=QUERY+" AND substr(ACCOUNT,1,1)='9' ";
					
					$("#fieldsval").val("ACCOUNT NUMBER,AMOUNT,CREDIT/DEBIT,TRANSACTION TYPE,PAYMENT REFERENCE NO,CHANNEL,TRANSACTION DATE AND TIME,NARRATION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
					}
					
					
					
						
					if($('#report11').val()=="Summary"){
						SEARCHBYDT="Search By  ";
							QUERY="select ACCOUNT,(select ACCT_NAME from WALLET_SUSPENSION_DATA where ACCT_NO=ACCOUNT and rownum=1) as acc_name,DECODE(DRCR_FLAG,'C','Credit','D','Debit') as DRCR_FLAG ,count(*),sum(AMOUNT) from WALLET_FIN_TXN_POSTING WFTP";
						
							if($("#dateradio").val()==""){
								
								$("#errormsg").text("Please Select Time Period .");
								return false;
							}else if($("#dateradio").val()=="betdate"){
								if($("#fromdate").val()==""){
									$("#errormsg").text("Please enter From Date.");
									return false;
								}
								if($("#todate").val()==""){
									$("#errormsg").text("Please enter To Date.");
									return false;
								}
							}
							
							
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" where TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" where to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+" Current Month ";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" where to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
								SEARCHBYDT=SEARCHBYDT+" Quarterly ";
							}
							
							if($('#agentuserid').val()!=""){
								//alert(($('#agentuserid').val()).split("-")[0])
								QUERY=QUERY+" AND ACCOUNT in (SELECT ACCT_NO from WALLET_SUSPENSION_DATA where ACCT_NAME='"+$('#agentuserid').val()+"')";
								SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
								
							   }
						
							QUERY=QUERY+" and substr(ACCOUNT,1,1)='9' group by ACCOUNT,DRCR_FLAG order by ACCOUNT";	
							//alert(QUERY);
							$("#fieldsval").val("ACCOUNT NO,SUSPENSE ACCOUNT NAME,CREDIT/DEBIT,COUNT,TXN AMOUNT");
						$("#query").val(QUERY);
						$("#searchby").val(SEARCHBYDT);
					}
				}
				
				if($("#Report").val()=="wallet_agent_commission_reports"){
					SEARCHBYDT="Search By  ";
					QUERY="select ACCT_NAME,wft.USER_ID,wft.account,UPPER(NVL(aci.area,' ')),UPPER(NVL(aci.cluster_name,' ')),UPPER(NVL(aci.branch_code,' ')),count(*),"+
						"nvl(sum(case when SERVICECODE='AGCASHDEP' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as CashDeposit,"+
						"nvl(sum(case when SERVICECODE='AGCASHWTHD' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as cashwithdrawal,"+
						"nvl(sum(case when SERVICECODE='AGENTFUND' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as Fund,"+
						"nvl(sum(case when SERVICECODE='AGNPAYBILLAIRTIME' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as AirtimeRecharge,"+
						"nvl(sum(case when SERVICECODE='WALAGNOTBANK' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as OtherBank,"+
						"nvl(sum(case when SERVICECODE='WALAGNOWNBANK' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as UnionBank,"+
						"nvl(sum(case when SERVICECODE='WALPAYBILLAGN' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as Paybill,"+
						"nvl(sum(case when SERVICECODE='AGNCRDCSHWTDOTH' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as CardOtherBankwt,"+
						"nvl(sum(case when SERVICECODE='AGNCRDCSHWTDOWN' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as CardUnionBankwt,"+
						"nvl(sum(case when SERVICECODE='AGNCRDFTXNOTH' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as CardOtherBank,"+
						"nvl(sum(case when SERVICECODE='AGNCRDFTXNOWN' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as CardUnionBank,"+
						"sum(decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT)) as tot"+
						" from wallet_fin_txn_posting wft,wallet_acct_data wad,agent_contact_info aci where TXN_TYPE='F' and wft.account=wad.acct_no and wad.cust_id=aci.cust_id and aci.MOBILE_NUMBER=wft.USER_ID ";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" and TRUNC(wft.TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" and to_char(wft.TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" and to_char(wft.TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
						QUERY=QUERY+" group by ACCT_NAME,wft.USER_ID,wft.account,UPPER(NVL(aci.area,' ')),UPPER(NVL(aci.cluster_name,' ')),UPPER(NVL(aci.branch_code,' ')) order by wft.account,wft.USER_ID";
					
					
					$("#fieldsval").val("NAME,MOBILE NUMBER,WALLET ACCOUNT NO,AREA,CLUSTER,BRANCH CODE,NUMBER OF COMMISSION TRANSACTION,CASH DEPOSIT,CASH WITHDRAWAL,AGENT FUND,PAYBILL AIRTIME RECHARGE,FUND TRANSFER OTHER BANK,FUND TRANSFER OWN BANK,PAY BILL,CASHWITHDRAWAL CARD OTHER BANK,CASHWITHDRAWAL CARD UNION BANK,FUNDTRANSFER CARD OTHERBANK,FUNDTRANSFER CARD UNION BANK,TOTAL COMMISSION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="wallet_commission_reports"){
					SEARCHBYDT="Search By  ";
					QUERY="select wad.acct_no,wad.acct_name,tp.user_id, UPPER(NVL(aci.r_state,' ')), UPPER(NVL(aci.area,' ')),UPPER(NVL(aci.cluster_name,' ')),UPPER(NVL(aci.branch_code,' ')),tp.commissioncnt,tp.agentcomm,tp.bankcommission,tp.cevacommission,tp.NIP,tp.Paybill,tp.superagent,tp.VAT"+
						" from "+
						"(select USER_ID,"+
						"sum(decode(DRCR_FLAG,'D',-1,'C',+1)) as commissioncnt,"+
						"nvl(sum(case when substr(ACCOUNT,1,1)='0' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as agentcomm,"+
						"nvl(sum(case when ACCOUNT='9866094003' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as bankcommission,"+
						"nvl(sum(case when ACCOUNT='9866094002' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as cevacommission,"+
						"nvl(sum(case when ACCOUNT='9866094019' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as NIP,"+
						"nvl(sum(case when ACCOUNT='9866094007' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as Paybill,"+
						"nvl(sum(case when ACCOUNT='9866094004' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as superagent,"+
						"nvl(sum(case when ACCOUNT='9866094934' then decode(DRCR_FLAG,'D',-wft.AMOUNT,'C',+wft.AMOUNT) end),0) as VAT "+
						"from wallet_fin_txn_posting wft where TXN_TYPE='F' ";
						
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" and TRUNC(wft.TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" and to_char(wft.TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" and to_char(wft.TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					QUERY=QUERY+"group by USER_ID) tp,wallet_acct_data wad,agent_contact_info aci ";
					QUERY=QUERY+"where tp.user_id=aci.mobile_number ";
					QUERY=QUERY+"and  aci.cust_id=wad.cust_id ";					
					
					$("#fieldsval").val("NAME,WALLET ACCOUNT NO,MOBILE NUMBER,STATE,AREA,CLUSTER,BRANCH CODE,NUMBER OF COMMISSION TRANSACTION,AGENT COMMISSION,BANK COMMISSION,CEVA COMMISSION,NIP COMMISSION,PAYBILL COMMISSION,SUPER AGENT,VAT");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="wallet_agent_settlement_reports"){
					
					if($('#report11').val()==""){
						$("#errormsg").text("Please select Report Type");
						return false;	
					}
					
					if($('#report11').val()=="Detailed report"){
					SEARCHBYDT="Search By  ";
					QUERY="select USERID,TXNREF,EXTTXNREF,(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WST.SERVICECODE) as SERVICECODE,TXNAMOUNT,to_char(TXNDATE,'dd-mm-yyyy hh24:mi:ss'),CHANNEL,DECODE((SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WST.SERVICECODE),'C','Credit','Debit') as SETCD  from WALLET_SETTELMENT_TBL WST ";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" where TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" where to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" where to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!=""){
						//alert(($('#agentuserid').val()).split("-")[0])
						QUERY=QUERY+" AND SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
						SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
						
					   }
					
					QUERY=QUERY+" order by TXNDATE desc";	
					
					$("#fieldsval").val("USER ID,TXN REFERENCE NUMBER,PAYMENT REFERENCE NUMBER,SERVICE CODE,AMOUNT,DATE AND TIME,CHANNEL,Credit/Debit");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
					}
					
					if($('#report11').val()=="Summary"){
					
						SEARCHBYDT="Search By  ";
						QUERY="select (SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WST.SERVICECODE) as SERVICECODE,DECODE((SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WST.SERVICECODE),'C','Credit','Debit') as  SETDRCR,CHANNEL,count(*),sum(TXNAMOUNT)  from WALLET_SETTELMENT_TBL WST";
						
						if($("#dateradio").val()==""){
							
							$("#errormsg").text("Please Select Time Period .");
							return false;
						}else if($("#dateradio").val()=="betdate"){
							if($("#fromdate").val()==""){
								$("#errormsg").text("Please enter From Date.");
								return false;
							}
							if($("#todate").val()==""){
								$("#errormsg").text("Please enter To Date.");
								return false;
							}
						}
						
						
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" where TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" where to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Current Month ";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" where to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
							SEARCHBYDT=SEARCHBYDT+" Quarterly ";
						}
						
						if($('#agentuserid').val()!=""){
							//alert(($('#agentuserid').val()).split("-")[0])
							QUERY=QUERY+" AND SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
							SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
							
						   }
						
						QUERY=QUERY+" group by SERVICECODE,CHANNEL,SETDRCR order by CHANNEL,SERVICECODE";	
						
						$("#fieldsval").val("SERVICE CODE,CR/DR FLAG,CHANNEL,NO OF TRANSACTION,AMOUNT");
						$("#query").val(QUERY);
						$("#searchby").val(SEARCHBYDT);
					}
					
				}
				
				if($("#Report").val()=="wallet_agent_unsettlement_reports"){
					
					if($('#report11').val()==""){
						$("#errormsg").text("Please select Report Type");
						return false;	
					}
					
					if($('#report11').val()=="Detailed report"){
					SEARCHBYDT="Search By  ";
					QUERY="select USERID,TXNREF,EXTTXNREF,(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WST.SERVICECODE) as SERVICECODE,TXNAMOUNT,to_char(TXNDATE,'dd-mm-yyyy hh24:mi:ss'),CHANNEL,DECODE((SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WST.SERVICECODE),'C','Credit','Debit') as SETCD  from WALLET_UNSETTELMENT_TBL WST ";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" where TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" where to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" where to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!=""){
						//alert(($('#agentuserid').val()).split("-")[0])
						QUERY=QUERY+" AND SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
						SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
						
					   }
					
					QUERY=QUERY+" and EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) order by TXNDATE desc";	
					
					$("#fieldsval").val("USER ID,TXN REFERENCE NUMBER,PAYMENT REFERENCE NUMBER,SERVICE CODE,AMOUNT,DATE AND TIME,CHANNEL,Credit/Debit");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
					}
					
					if($('#report11').val()=="Summary"){
					
						SEARCHBYDT="Search By  ";
						QUERY="select (SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WST.SERVICECODE) as SERVICECODE,DECODE((SELECT BSCM1.DRCRFLAG from BANK_SERVICE_CODE_MASTER BSCM1 where BSCM1.SERVICECODE=WST.SERVICECODE),'C','Credit','Debit') as  SETDRCR,CHANNEL,count(*),sum(TXNAMOUNT)  from WALLET_UNSETTELMENT_TBL WST";
						
						if($("#dateradio").val()==""){
							
							$("#errormsg").text("Please Select Time Period .");
							return false;
						}else if($("#dateradio").val()=="betdate"){
							if($("#fromdate").val()==""){
								$("#errormsg").text("Please enter From Date.");
								return false;
							}
							if($("#todate").val()==""){
								$("#errormsg").text("Please enter To Date.");
								return false;
							}
						}
						
						
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" where TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" where to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+" Current Month ";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" where to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
							SEARCHBYDT=SEARCHBYDT+" Quarterly ";
						}
						
						if($('#agentuserid').val()!=""){
							//alert(($('#agentuserid').val()).split("-")[0])
							QUERY=QUERY+" AND SERVICECODE='"+($('#agentuserid').val()).split("-")[0]+"'";
							SEARCHBYDT=SEARCHBYDT+" transaction type "+($('#agentuserid').val()).split("-")[1];
							
						   }
						
						QUERY=QUERY+" and EXTTXNREF not in ( select EXTTXNREF from WALLET_SETTELMENT_TBL) group by SERVICECODE,CHANNEL,SETDRCR order by CHANNEL,SERVICECODE";	
						
						$("#fieldsval").val("SERVICE CODE,CR/DR FLAG,CHANNEL,NO OF TRANSACTION,AMOUNT");
						$("#query").val(QUERY);
						$("#searchby").val(SEARCHBYDT);
					}
				}
		
				
				if($("#Report").val()=="RM_Report"){
					SEARCHBYDT="Search By  ";
					QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','N','Paritial Registion','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,RM.RMCODE,RM.MAKER_DT,MCM.AUTHID,NVL(RM.INTRODUCER,' ')  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD,RMCODE_TBL RM where MCM.CUSTOMER_CODE=RM.CUSTOMER AND MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P' ";
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(RM.MAKER_DT) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(RM.MAKER_DT,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(RM.MAKER_DT,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(RM.RMCODE)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And RM Code : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And RM Code : ALL";
					}
					
					
					if($('#status1').val()!=""){
						if($('#status1').val()=='A'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('A','U')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						if($('#status1').val()=='L'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('L')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						if($('#status1').val()=='N'){
						
								QUERY=QUERY+" AND MCM.STATUS in ('N')";
								SEARCHBYDT=SEARCHBYDT+"And Status : Active";
							}
						}
					
					if($('#product1').val()!=""){
						
						QUERY=QUERY+" AND UPPER(MCM.M_PRD_CODE) =UPPER('"+$("#product1").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And product : "+$("#product1").val()+"";
						
					}
					
					QUERY=QUERY+" ORDER BY RM.RMCODE ";
				   $("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NUMBER,MAIL ID,STATUS,ONBOARD DATE,PRODUCT CODE,PRIMARY ACCOUNT NUMBER,BRANCH CODE,DATA CAPTURE BRANCH NAME,CAPTURE DATE,CHANNEL,INTRODUCER CODE"); 

					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
		
				if($("#Report").val()=="Reg_Merchant_Report"){
					SEARCHBYDT="Search By  ";
					QUERY="select ORGANIGATION_ID,ACCOUNT_NUMBER,ACCOUNT_NAME,MOBILE,EMAIL,MERCHANT_TYPE,decode(STATUS,'C','Active','Deactive') as STATUS,BRANCH_CODE from ORG_MASTER";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(MAKER_DT) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(MAKER_DT,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+" Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(MAKER_DT,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+" Quarterly ";
					}
					
					if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(BRANCH_CODE)=UPPER('"+($("#agentuserid").val()).split("-")[0]+"')";
						SEARCHBYDT=SEARCHBYDT+"And Branch : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And Branch : ALL";
					}
					
					QUERY=QUERY+"  order by BRANCH_CODE ";
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
		
		
			if($("#Report").val()=="Transaction_summary_report"){
				SEARCHBYDT="Search By  ";
				
				if($("#newoldreport").val()=="new"){
					if($("#status").val()=="SUCCESS"){					
					QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL where RESPONSECODE='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
					QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL where RESPONSECODE!='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else{
						QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL where (RESPONSECODE!='00' OR RESPONSECODE='00')";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					}
				}
				
				if($("#newoldreport").val()=="old"){
					if($("#status").val()=="SUCCESS"){					
					QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL_HST where RESPONSECODE='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
					QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL_HST where RESPONSECODE!='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else{
						QUERY="select sum(TRNS_AMT) as SUMAMT,(select SERVICE_DESCRIPTION from MOB_TRANS_MASTER where SERVICE_CODE=TRANS_TYPE) as TRANS_TYPE,CHANNEL,Count(*) as CNT from FUND_TRANSFER_TBL_HST where (RESPONSECODE!='00' OR RESPONSECODE='00')";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					}
				}
					
					if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					if($('#searchid').val()!=""){
						QUERY=QUERY+" AND UPPER(TRANS_TYPE)=UPPER('"+$("#searchid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
					}
					QUERY=QUERY+" group by TRANS_TYPE,CHANNEL order by CHANNEL,TRANS_TYPE";
					//console.log(QUERY);
					
					$("#fieldsval").val("TOTAL VALUE OF TRANSACTION,TRANSACTION TYPE,CHANNEL,NO OF TRANSACTION"); 

					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
		
			
				if($("#Report").val()=="Teller_Reconcile_Report"){
					QUERY="SELECT TELLER_ID,TELLER_AMOUNT,TELLER_DRCR,TELLER_BRANCH,DSA_ID,DSA_AMOUNT,DSA_DRCR,DSA_BRANCH,TRANSACTION_DT,DSA_CASH_WITHDRAWAL,DSA_CASH_DEPOSIT FROM TELLER_TRANS  where 1=1";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select teller id .");
						return false;
					} 
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(to_date(TRANSACTION_DT)) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANSACTION_DT,'mm')=to_char(sysdate,'mm')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANSACTION_DT,'Q')=to_char(sysdate,'Q')";	
						
					}
					if($("#agentuserid").val()!="ALL"){
					QUERY=QUERY+" AND TELLER_ID='"+$("#agentuserid").val()+"'";
					}
					//console.log(QUERY)
					
					$("#query").val(QUERY);
					
				}
				
				if($("#Report").val()=="Agent_Users"){
					
					
					QUERY="SELECT ULC.LOGIN_USER_ID,UI.USER_NAME,UI.EMAIL,UI.MOBILE_NO,UI.USER_GROUPS,DECODE(UI.USER_STATUS,'A','Active','B','Blocked','L','Locked',USER_STATUS)USER_STATUS,DECODE(UI.USER_TYPE,'HOD','Head Of Department','JO','Junior Officer','MA','Agent Admin','MU','Agent User','MS','Agent Supervisor','SM','Store Manager',USER_TYPE) USER_TYPE,ULC.MAKER_ID,ULC.MAKER_DTTM,ULC.APPL_CODE,ULC.AUTHID,ULC.AUTHDTTM,ULC.STATUS,To_Date(To_Char(ULC.LAST_LOGED_IN, 'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS') LAST_LOGED_IN FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI" 
					+" WHERE UI.COMMON_ID=ULC.COMMON_ID AND ULC.COMMON_ID in (select common_id from AGENT_INFO_COMMON where AGENT_TYPE='EXTERNAL'";
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND AGENT_ID in (select MERCHANT_ID from MERCHANT_MASTER where Upper(SUPER_AGENT)=Upper(UPPER('"+$("#agentuserid").val()+"')))";	
						
					}
					
					if($('#searchid').val()!=""){
						QUERY=QUERY+" AND AGENT_ID='"+$('#searchid').val()+"'";	
					}
					
					QUERY=QUERY+")";
					
				
					
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Agent_List"){
					
					
					QUERY="select MERCHANT_ID,MERCHANT_NAME,AGENT_ID,B_OWNER_NAME,EMAIL,MOBILE_NO,MAKER_ID,MAKER_DTTM,AUTHID,AUTHDTTM,MERCHANT_TYPE,AGEN_OR_BILLER,TRANSACTION_LIMIT,MERCHANT_ADMIN from merchant_master ";
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+"WHERE SUPER_AGENT='"+$('#agentuserid').val()+"'";	
					}
					
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="success_reversal_reports"){
					
					SEARCHBYDT="Search By  ";
					QUERY="select PAYMENTREFERENCE,BATCHID,ACCT_NO,USER_ID,TRNS_AMT,CHANNEL,REQUEST_TYPE,ACTION_TYPE,REMARKS,MAKER_ID,MAKER_DT,NVL(CHECKER_ID,' '),CHECKER_DATE,decode(status,'P','Process','C','Success','F','Failed'),NVL(RESPENSE_MESSAGE,' ') from WALLET_SUCCREV_REQUEST";
					
					
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,WALLET PAYMENT REFERENCE NUMBER,ACCOUNT NUMBER,USER ID,TXN AMOUNT,CHANNEL,TXN TYPE,REQUEST TYPE,REMARKS,MAKER ID,MAKER DATE,CHECKER ID,CHECKER DATE,STATUS,RESPONSE"); 

					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Product_List_Report"){
					QUERY="SELECT PRD_CODE,PRD_NAME,PRD_CCY,APPLICATION from PRODUCT";
					SEARCHBYDT="Search By  List of Product";
					
					$("#fieldsval").val("PRODUCT CODE,PRODUCT NAME,CURRENCY,APPLICATION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Session_Fraud_Monitoring_Report"){
					QUERY="select SFR.FRAUD_DTTM,SFR.IP,SFR.ERROR_DESC,SFR.ERROR_CODE,SFR.IMEI_NO,DECODE (SFR.REQUEST_CHANNEL ,'1','ANDROID','5','IOS','11','WINDOWS','3','WEB'),NVL(SFR.DEVICE_INFO,' '),MID.USER_ID,MID.SESSIONID from  SESSION_FRAUD_MONITOR SFR,MOB_IMEI_DATA MID  where  SFR.IMEI_NO=MID.IMEI_NO";
					SEARCHBYDT="Search By  Session Fraud Monitoring";
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(FRAUD_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(FRAUD_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(FRAUD_DTTM,'Q')=to_char(sysdate,'Q')";	
						
					}
					
					$("#fieldsval").val("FRAUD DATE,IP,ERROR DESC,ERROR CODE,IMEI NO,CHANNEL,DEVICE INFO,USER ID,SESSION ID");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Wallet_Balance_Sheet_reports"){
					
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select Wallet Type .");
						return false;
					}
					
					if($("#balancedt").val()==""){
						$("#errormsg").text("Please Select Balance Date.");
						return false;
					}
					
					var v="";
					if($("#agentuserid").val()=="ALL"){
						 QUERY="select  wad.ACCT_NAME,acf.MOBILE_NUMBER,wad.acct_no,wad.CUST_TYPE,sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)) from wallet_acct_data wad,wallet_fin_txn_posting wftp,mob_contact_info acf where acf.cust_id=wad.cust_id and wad.acct_no=wftp.ACCOUNT and balance>0 and trunc(TXN_STAMP)<to_date('"+$("#balancedt").val()+"','dd/mm/yyyy') group by wad.ACCT_NAME,wad.acct_no,wad.CUST_TYPE,acf.MOBILE_NUMBER "
						 +" UNION ALL "
						 +"select  wad.ACCT_NAME,acf.MOBILE_NUMBER,wad.acct_no,wad.CUST_TYPE,sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)) from wallet_acct_data wad,wallet_fin_txn_posting wftp,agent_contact_info acf where acf.cust_id=wad.cust_id and wad.acct_no=wftp.ACCOUNT and balance>0 and trunc(TXN_STAMP)<to_date('"+$("#balancedt").val()+"','dd/mm/yyyy') group by wad.ACCT_NAME,wad.acct_no,wad.CUST_TYPE,acf.MOBILE_NUMBER ";

					}
					
					if($("#agentuserid").val()=="WALLET"){
						 QUERY="select  wad.ACCT_NAME,acf.MOBILE_NUMBER,wad.acct_no,wad.CUST_TYPE,sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)) from wallet_acct_data wad,wallet_fin_txn_posting wftp,mob_contact_info acf where acf.cust_id=wad.cust_id and wad.acct_no=wftp.ACCOUNT and balance>0 and trunc(TXN_STAMP)<to_date('"+$("#balancedt").val()+"','dd/mm/yyyy') group by wad.ACCT_NAME,wad.acct_no,wad.CUST_TYPE,acf.MOBILE_NUMBER ";

					}
					
					if($("#agentuserid").val()=="AGENT"){
						 QUERY="select  wad.ACCT_NAME,acf.MOBILE_NUMBER,wad.acct_no,wad.CUST_TYPE,sum(decode(wftp.DRCR_FLAG,'D',-wftp.AMOUNT,+wftp.AMOUNT)) from wallet_acct_data wad,wallet_fin_txn_posting wftp,agent_contact_info acf where acf.cust_id=wad.cust_id and wad.acct_no=wftp.ACCOUNT and balance>0 and trunc(TXN_STAMP)<to_date('"+$("#balancedt").val()+"','dd/mm/yyyy') group by wad.ACCT_NAME,wad.acct_no,wad.CUST_TYPE,acf.MOBILE_NUMBER ";

					}
					
					
					 SEARCHBYDT="Search By  Wallet Balance Sheet";
					
					$("#fieldsval").val("NAME,MOBILE NUMBER,ACCOUNT NO,WALLET TYPE,BALANCE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="inventory_reports"){
					
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select State .");
						return false;
					}
					
					
					
					QUERY="select aci.mobile_number,acm.first_name,NVL(aci.ADDRESS,' '),NVL(aci.R_STATE,' '),NVL(aci.RL_LGA,' '),tm.terminal_id,tm.MODEL_NO,tm.TERMINAL_MAKE,tm.serial_no,decode(tm.STATUS,'A','Active','D','Deactive','R','Retrival'),to_char(sm.MAKER_DTTM,'dd-mm-yyyy') from AGENT_CONTACT_INFO ACI,AGENT_CUSTOMER_MASTER ACM,STORE_MASTER SM,TERMINAL_MASTER TM WHERE aci.cust_id=acm.id and aci.cust_id=sm.cust_id and sm.STORE_ID=tm.STORE_ID";
					
					if(!$("#agentuserid").val()=="ALL"){
						QUERY=QUERY+" and UPPER(aci.R_STATE)=UPPER('"+($("#agentuserid").val()).split("-")[1]+"') ";
					}
					
					if(!$("#searchval").val()==""){
						QUERY=QUERY+" and tm.terminal_id='"+$("#searchval").val()+"' ";
					}
					
					if(!$("#searchval1").val()==""){
						QUERY=QUERY+" and tm.serial_no='"+$("#searchval1").val()+"' ";
					}
					
					if(!$("#status2").val()==""){
						QUERY=QUERY+" and tm.STATUS='"+$("#status2").val()+"' ";
					}
					
					QUERY=QUERY+" ORDER BY aci.mobile_number ";
					
					SEARCHBYDT="Search By  Inventory teport";
					
					$("#fieldsval").val("MOBILE NUMBER,NAME,ADDRESS,STATE,LOCAL GOVERNMENT,TERMINAL NUMBER,MODEL NO,TERMINAL MAKE,SERIAL NUMBER,STATUS,CREATED DATE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Unsettlement_69_Reports"){
					QUERY="select FTID, to_char(TRANS_DATE_TIME,'DD/MM/YYYY HH24:MI:SS') TRANS_DATE_TIME, FT_RESP_CODE, FT_RESP_DESC, FTO_RESP_CODE, FTO_RESP_DESC, USER_ID, CHANNEL, FTBATCHID, BANK_CODE, BANK_NAME  from AGENT_WALLET_FTO_TRANS_TBL AWF  where not exists  (select 1 from WALLET_FIN_TXN WFT where 'R'||AWF.FTID=WFT.TXN_REF_NO) and  AWF.FTO_RESP_CODE='69' and FTO_RESPONSE not like '{}'";
					SEARCHBYDT="Search By  Unsettlement Report";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE_TIME,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						
					}
					
					$("#fieldsval").val("Payment Reference no,Transaction Date,Response Code,Response Message,fto Response Code,fto Response Message,user Id,Channel,Batch Id,Bank Code,Bank Name");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="failed_card_transaction_reports"){
					QUERY="select APPROVEDBY,AMOUNT/100,POSRRN,INTERNALID,BANKRRN,STAN,BANKRESPONSECODE,NVL(BANKRESPONSEMSG,' '),TXNDATE,TERMINALNUMBER,NVL(TXNCODE,' ') from tbl_tranlog_all where BANKRESPONSECODE not in ('00','09')";
					SEARCHBYDT="Search By  Unsettlement Report";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
						
					}
					
					QUERY=QUERY+" order by TXNDATE,BANKRESPONSECODE";	
					
					$("#fieldsval").val("USER ID,AMOUNT,POS RRN,INTERNAL ID,BANK RRN,STAN,BANK RESPONSE CODE,BANK RESPONSE MESSAGE,TRANACTION DATE,TERMINAL NUMBER,TXN CODE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="card_transaction_09_reports"){
					QUERY="select APPROVEDBY,AMOUNT/100,POSRRN,INTERNALID,BANKRRN,STAN,BANKRESPONSECODE,NVL(BANKRESPONSEMSG,' '),TXNDATE,TERMINALNUMBER,NVL(TXNCODE,' ') from tbl_tranlog_all where BANKRESPONSECODE in ('09')";
					SEARCHBYDT="Search By  Unsettlement Report";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TXNDATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TXNDATE,'Q')=to_char(sysdate,'Q')";	
						
					}
					
					QUERY=QUERY+" order by TXNDATE,BANKRESPONSECODE";	
					
					$("#fieldsval").val("USER ID,AMOUNT,POS RRN,INTERNAL ID,BANK RRN,STAN,BANK RESPONSE CODE,BANK RESPONSE MESSAGE,TRANACTION DATE,TERMINAL NUMBER,TXN CODE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Store_List"){
					QUERY="SELECT MERCHANT_ID,STORE_ID,STORE_NAME,B_OWNER_NAME,EMAIL,TELEPHONE_NO,ADDRESS,DECODE(STATUS,'A','Active','B','Blocked','L','Locked',STATUS)STATUS,CITY,MAKER_ID,MAKER_DTTM,AUTHID,AUTHDTTM from STORE_MASTER";
					
					if($('#agentuserid').val()!=""){
							
						QUERY=QUERY+" WHERE MERCHANT_ID in (SELECT MERCHANT_ID FROM MERCHANT_MASTER WHERE UPPER(SUPER_AGENT)=UPPER('"+$('#agentuserid').val()+"')) ";	
					}
					
					if($('#searchid').val()!=""){
						QUERY=QUERY+" AND MERCHANT_ID='"+$('#searchid').val()+"'";
					}
					
					$("#query").val(QUERY);
					
				}
				
				if($("#Report").val()=="Super_Agent_List"){
					QUERY="select ACCOUNTNAME,ACCOUNTNUMBER,BRANCHCODE ,EMAIL,MOBILE,STATUS,DECODE(COUNTRY,'1','Nigeria',COUNTRY) COUNTYRY,(select ST.STATE_NAME from STATE_MASTER ST where ST.STATE_CODE=SA.STATE) STATE,MERCHANTTYPE,PRODUCTTYPE,MAKER_ID,MAKER_DTTM,AUTHORIZER,AUTH_DTTM from super_agent SA";
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Audit_Trail"){
					SEARCHBYDT="Search By  ";
					if($("#newoldreport").val()=="new"){
					   QUERY="SELECT  NVL(A.CHANNEL,'') CHANNEL,  NVL(A.NET_ID,' ') MAKERID,  TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mi:ss') DTTM,  NVL(A.TRANSCODE_DESC,' ') TRANSCODE,  NVL(A.IP,' ') IP,  NVL(A.MESSAGE,' ') MESSAGE FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE ";
					}
					
					if($("#newoldreport").val()=="old"){
						QUERY="SELECT  NVL(A.CHANNEL,'') CHANNEL,  NVL(A.NET_ID,' ') MAKERID,  TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mi:ss') DTTM,  NVL(A.TRANSCODE_DESC,' ') TRANSCODE,  NVL(A.IP,' ') IP,  NVL(A.MESSAGE,' ') MESSAGE FROM AUDIT_DATA_HST A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE ";
					}
					
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select Action Name .");
						return false;
					} 
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(A.TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(A.TXNDATE,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(A.TXNDATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					QUERY = QUERY + " AND (parent_menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_NAME='"+$("#agentuserid").val()+"') "
					+" or menu_id in (select MENU_ID from CEVA_MENU_LIST WHERE MENU_NAME='"+$("#agentuserid").val()+"') ) order by A.TXNDATE,A.NET_ID desc";
					
					SEARCHBYDT=SEARCHBYDT+"And Action Name "+$("#agentuserid").val();
					
					$("#fieldsval").val("CHANNEL,USER ID,DATE AND TIME,ACTION DESCRIPTION,IP,ACTION PERFORMED");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				
				if($("#Report").val()=="Merchanr_Product_Report"){
					QUERY="select OPM.MERCHANT_ID as MERCHANT_ID,OPM.MERCHANT_NAME  as MERCHANT_NAME,OPM.PRODUCT_ID  as PRODUCT_ID,OPM.PRODUCT_NAME as PRODUCT_NAME,OPM.PRODUCT_PRICE as PRODUCT_PRICE,OPM.QUANTITY as QUANTITY,OPOD.OFFER_TYPE as OFFER_TYPE,OPOD.TIME_LIMIT as TIME_LIMIT,OPOD.END_DATE as END_DATE,OPOD.DISCOUNT_CASHBACK as DISCOUNT_CASHBACK,OPOD.DIS_CASHBACK_PER as DIS_CASHBACK_PER from ONLINE_PRODUCTS_MASTER OPM FULL OUTER JOIN ONLINE_PRODUCT_OFFERS_DEALS OPOD on OPM.PRODUCT_ID=OPOD.PRODUCT_ID ";
					SEARCHBYDT="Search By  ";
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE OPM.MAKER_DTTM between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(OPM.MAKER_DTTM,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(OPM.MAKER_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#agentuserid").val()!=""){
						
						QUERY = QUERY + " AND OPM.MERCHANT_ID='"+($("#agentuserid").val()).split("-")[0]+"' ";
						
						SEARCHBYDT=SEARCHBYDT+"And Merchant Id "+($("#agentuserid").val()).split("-")[0];
					} 
					
					
					
					$("#query").val(QUERY+" order by OPM.PRODUCT_ID");
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Sale_Order_Report"){
					QUERY="select OPM.MERCHANT_ID as MERCHANT_ID,OPM.MERCHANT_NAME  as MERCHANT_NAME,OPM.PRODUCT_ID  as PRODUCT_ID,OPM.PRODUCT_NAME as PRODUCT_NAME,ORM.PRODUCT_PRICE,ORM.DISCOUNT_PRICE,ORM.FINAL_AMOUNT,ORM.TXN_REF_NO,ORM.ACC_NO,ORM.USER_NAME,ORM.DELIVERY_ADDRESS from ONLINE_PRODUCTS_MASTER OPM,ONLINE_PURCHASE_MASTER ORM where OPM.PRODUCT_ID=ORM.PRODUCT_ID";
					SEARCHBYDT="Search By  ";
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(ORM.TXN_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(ORM.TXN_DATE,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(ORM.TXN_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#agentuserid").val()!=""){
						
						QUERY = QUERY + " AND OPM.MERCHANT_ID='"+($("#agentuserid").val()).split("-")[0]+"' ";
						
						SEARCHBYDT=SEARCHBYDT+"And Merchant Id "+($("#agentuserid").val()).split("-")[0];
					} 
					
					
					
					$("#query").val(QUERY+" order by OPM.PRODUCT_ID");
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="WalletAccountOpenLocationReport"){
					QUERY="select ACM.FIRST_NAME,ACI.MOBILE_NUMBER,NVL(ACI.RL_LGA,' '),NVL(ACI.R_STATE,' '),(SELECT count(*) from WALLET_ACCT_DATA WHERE CREATED_BY = ACI.MOBILE_NUMBER ) from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO ACI WHERE ACM.ID=ACI.CUST_ID ORDER BY ACI.R_STATE ";
					
					$("#fieldsval").val("USER NAME,MOBILE NUMBER,LOCAL GOVERNMENT,STATE,NUMBER ACCOUNT OPEN"); 
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="WalletTransactionStatus_Report"){
					QUERY="select RESPCODE,RESPDESC,count(*) from WALLET_FIN_TXN_LOG  group by RESPCODE,RESPDESC ";
					
					$("#fieldsval").val("RESPONSE CODE,RESPONSE DESCRIPTION ,COUNT"); 
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="WalletAccountOpenLocationReport"){
					QUERY="select ACM.FIRST_NAME,ACI.MOBILE_NUMBER,NVL(ACI.RL_LGA,' '),NVL(ACI.R_STATE,' '),(SELECT count(*) from WALLET_ACCT_DATA WHERE CREATED_BY = ACI.MOBILE_NUMBER ) from AGENT_CUSTOMER_MASTER ACM,AGENT_CONTACT_INFO ACI WHERE ACM.ID=ACI.CUST_ID  ";
					
					$("#fieldsval").val("USER NAME,MOBILE NUMBER,LOCAL GOVERNMENT,STATE,NUMBER ACCOUNT OPEN"); 
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Users_Groups_Report"){
					QUERY="select GROUP_ID,GROUP_NAME,APPL_CODE,GROUP_TYPE,USER_LEVEL,MAKER_ID,MAKER_DTTM from USER_GROUPS";
					
					$("#fieldsval").val("USER ID,USER NAME,EMPLOYEE ID,BRANCH LOCATION,USER GROUP,STATUS"); 
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Users_Management_Report"){
					QUERY="SELECT ULC.LOGIN_USER_ID,UI.USER_NAME,UI.EMAIL,UI.MOBILE_NO,UI.USER_GROUPS,DECODE(UI.USER_STATUS,'A','Active','B','Blocked','L','Locked',USER_STATUS)USER_STATUS,DECODE(UI.USER_TYPE,'HOD','Head Of Department','JO','Junior Officer','MA','Merchant Admin','MU','Merchant User','MS','Merchant Supervisor','SM','Store Manager',USER_TYPE) USER_TYPE,ULC.MAKER_ID,ULC.MAKER_DTTM,ULC.APPL_CODE,ULC.AUTHID,ULC.AUTHDTTM,ULC.STATUS,To_Date(To_Char(ULC.LAST_LOGED_IN, 'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS') LAST_LOGED_IN FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE UI.COMMON_ID=ULC.COMMON_ID";
					
					$("#fieldsval").val("USER NAME,EMPLOYEE ID,MOBILE NO,E MAIL,BRANCH LOCATION,USER TYPE,USER GROUP,STATUS"); 

					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Fraud_Transactions_Report"){
					QUERY="SELECT ROWNUM SNO,FT.MOBNUM,FT.TXN_AMOUNT,FT.DATE_CREATED,FT.FRAUD_DTTM,FT.REQUEST_CHANNEL,FT.ERROR_DESC,FT.FRDPK,FT.MON_STATUS,FT.TRPK FROM FRAUD_TBL FT,TRAN_LOG TL WHERE   FT.TRPK=TL.TRANLOG_PK and (TRUNC(FT.FRAUD_DTTM)   between  to_date('08/06/2011','DD/MM/YYYY') AND  to_date('30/06/2016','DD/MM/YYYY'))   and  FT.MON_STATUS='C'";
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Fee_Report"){
					
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select Product .");
						return false;
					}
					
					QUERY="select LM.PRODUCT,lm.LMT_FEE_CODE,(select SERVICE_NAME from MOB_SERVICE_MASTER t where t.SERVICE_CODE=LD.TXN_CODE ) TXNNAME,LD.FREQUENCY,decode(LD.FEE_TYPE,'P','Percentile','Flat') FLATPER,LD.VALUE FPVALUE,decode(LD.CNT_AMT,'A','Amount','Count') CRT,decode(LD.CNT_AMT,'A',LD.MIN_AMT,LD.MIN_CNT) FRMVAL,decode(LD.CNT_AMT,'A',LD.MAX_AMT,LD.MAX_CNT) TOVAL from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.USAGE_TYPE='F' and LM.REF_NUM=LD.REF_NUM AND LM.PRODUCT='"+$("#agentuserid").val()+"'";
					SEARCHBYDT="Search By  Product "+$("#agentuserid").val();
					
					$("#fieldsval").val("PRODUCT CODE,FEE CODE,TRANSACTION,FREQUENCY,FEE/PERCENTILE,VALUE,CRITERIA,FROM VALUE,TO VALUE"); 
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="commission_Report"){
					
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select Product .");
						return false;
					}
					
					QUERY="select LM.PRODUCT,lm.LMT_FEE_CODE,(select SERVICE_NAME from MOB_SERVICE_MASTER t where t.SERVICE_CODE=LD.TXN_CODE ) TXNNAME,LD.AGENT,LD.CEVA,LD.BANK,SUPERAGENT,VAT from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.USAGE_TYPE='F' and LM.REF_NUM=LD.REF_NUM AND LM.PRODUCT='"+$("#agentuserid").val()+"'";
					
					SEARCHBYDT="Search By  Product "+$("#agentuserid").val();
					
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Limit_Report"){
					if($("#agentuserid").val()==""){
						$("#errormsg").text("Please Select Product .");
						return false;
					}
					
					QUERY="select LM.PRODUCT,lm.LMT_FEE_CODE,(select SERVICE_NAME from MOB_SERVICE_MASTER t where t.SERVICE_CODE=LD.TXN_CODE ) TXNNAME,LD.FREQUENCY,LD.MAX_CNT,LD.MIN_AMT,LD.MAX_AMT from LIMIT_FEE_MASTER LM , LIMIT_FEE_DETAILS LD where LM.USAGE_TYPE='L' and LM.REF_NUM=LD.REF_NUM AND LM.PRODUCT='"+$("#agentuserid").val()+"'";
					SEARCHBYDT="Search By  Product "+$("#agentuserid").val();
					
					$("#fieldsval").val("PRODUCT CODE,LIMIT CODE,TRANSACTION,FREQUENCY,MAXIMUM COUNT,MINIMUM AMOUNT,MAXIMUM AMOUNT"); 
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Users_Management_Report"){
					
					QUERY="select USER_NAME,EMP_ID,MOBILE_NO,EMAIL,LOCATION,(select CONFIG_DATA.STATUS from CONFIG_DATA where CONFIG_DATA.KEY_VALUE=USER_TYPE) as user_type,USER_GROUPS,decode(STATUS,'A','Active','deactive') as status from USER_INFORMATION ";
					SEARCHBYDT="Search By  ";
					
					if($("#agentuserid").val()!="" && $("#agentuserid").val()!="ALL"){
						QUERY=QUERY+"WHERE LOCATION='"+$("#agentuserid").val()+"'";
						SEARCHBYDT="Search By  Branch Location "+$("#agentuserid").val();
					}else if( $("#agentuserid").val()=="ALL"){
						SEARCHBYDT=SEARCHBYDT+" Branch : ALL";
					}
					
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Users_Groups_Report"){
					
					QUERY="select ULC.LOGIN_USER_ID as LOGIN_USER_ID,UI.USER_NAME as USER_NAME,UI.EMP_ID as EMP_ID,UI.LOCATION  as LOCATION,UI.USER_GROUPS  as USER_GROUPS,decode(UI.USER_STATUS,'A','Active','Deactive') as status from USER_INFORMATION UI,USER_LOGIN_CREDENTIALS ULC WHERE UI.COMMON_ID=ULC.COMMON_ID ";
					SEARCHBYDT="Search By  All Group";
					
					if($("#agentuserid").val()!=""){
						QUERY=QUERY+"AND UI.USER_GROUPS='"+($("#agentuserid").val()).split("-")[0]+"'";
						SEARCHBYDT="Search By  User Group "+($("#agentuserid").val()).split("-")[0];
					}
					
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Account_Open"){
					
					SEARCHBYDT="Search By  ";
					
					 if($('#agentuserid').val()==""){
							$("#errormsg").text("Please Select Account Type .");
							return false;
						} 
					 if($('#agentuserid').val()=="EXT"){
						if($("#status").val()=="SUCCESS"){
							
							QUERY="select NEW_ACCOUNT_NUMBER as primaryacc,TELEPHONE as mobno,replace(TITLE,'NA ') as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),sad.CUSTOMER_ID as custid,sad.SI_AMOUNT as balance,RESPONSEMESSAGE,USERID,MONTHLY_DEBIT_DATE as sidate  from account_opeN ao,si_account_det_tbl sad WHERE ao.NEW_ACCOUNT_NUMBER=sad.CREDIT_ACT and ACCOUNT_TYPE='EXT' AND RESPONSECODE='0' ";
							SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
						}else if($("#status").val()=="FAIL"){
							
							QUERY="select NEW_ACCOUNT_NUMBER as primaryacc,TELEPHONE as mobno,replace(TITLE,'NA ') as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),sad.CUSTOMER_ID as custid,sad.SI_AMOUNT as balance,RESPONSEMESSAGE,USERID,MONTHLY_DEBIT_DATE as sidate  from account_opeN ao,si_account_det_tbl sad WHERE ao.NEW_ACCOUNT_NUMBER=sad.CREDIT_ACT and ACCOUNT_TYPE='EXT' AND RESPONSECODE!='0' ";
							SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
						}else{
							
							QUERY="select NEW_ACCOUNT_NUMBER as primaryacc,TELEPHONE as mobno,replace(TITLE,'NA ') as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),sad.CUSTOMER_ID as custid,sad.SI_AMOUNT as balance,RESPONSEMESSAGE,USERID,MONTHLY_DEBIT_DATE as sidate  from account_opeN ao,si_account_det_tbl sad WHERE ao.NEW_ACCOUNT_NUMBER=sad.CREDIT_ACT and ACCOUNT_TYPE='EXT' AND (RESPONSECODE!='0' OR RESPONSECODE!='0') ";
							SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
						}
						SEARCHBYDT=SEARCHBYDT+"And Account type : Existing Account ";
					 }
					 
					 
					 if($('#agentuserid').val()=="NEW"){
							if($("#status").val()=="SUCCESS"){
								
								QUERY="select ' ' as primaryacc,TELEPHONE as mobno,firstname||' '||middlename||' '||lastname as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),' ' as custid,' ' as balance,RESPONSEMESSAGE,' ' as USERID ,' ' as sidate  from account_opeN  WHERE  ACCOUNT_TYPE='NEW' AND RESPONSECODE='0' ";
								SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
							}else if($("#status").val()=="FAIL"){
								
								QUERY="select ' ' as primaryacc,TELEPHONE as mobno,firstname||' '||middlename||' '||lastname as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),' ' as custid,' ' as balance,RESPONSEMESSAGE,' ' as USERID,' ' as sidate  from account_opeN  WHERE  ACCOUNT_TYPE='NEW' AND RESPONSECODE!='0' ";
								SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
							}else{
								
								QUERY="select ' ' as primaryacc,TELEPHONE as mobno,firstname||' '||middlename||' '||lastname as custname,decode(gender,'M','MALE','FEMALE') gender,ACT_BRANCHCODE as branccode,decode(ACCOUNT_TYPE,'NEW','New Account','Existing Account') as acctype,DECODE(PRODUCTCODE,'SA_006','UnionSave','SA_007','UnionSaveMore','SA_014','UnionSave') as product,accountnumber,channel,to_char(TRANS_DATE,'dd/MM/yyyy HH24:MI:SS'),' ' as custid,' ' as balance,RESPONSEMESSAGE,' ' as USERID,' ' as sidate  from account_opeN  WHERE  ACCOUNT_TYPE='NEW' AND (RESPONSECODE!='0' OR RESPONSECODE!='0') ";
								SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
							}
							
							SEARCHBYDT=SEARCHBYDT+"And Account type : New Account ";
						 }
					 
					if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					/* if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(BRANCH)=UPPER('"+($("#agentuserid").val()).split("-")[0]+"')";
						SEARCHBYDT=SEARCHBYDT+"And Branch : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And Branch : ALL";
					} */
					
					QUERY=QUERY+" ORDER BY TRANS_DATE DESC";
					$("#fieldsval").val("PRIMARY ACCOUNT NUMBER,MOBILE NUMBER,CUSTOMER NAME,GENDER,BRANCH CODE,ACCOUNT TYPE,PRODUCT,NEW ACCOUNT NUMBER,CHANNEL,TRANSACTION DATE AND TIME,CUSTOMER ID,BALANCE,RESPONSE MESSAGE,MAKER,SI DATE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Account_summary_Open"){
					
					SEARCHBYDT="Search By  ";
					
						
					QUERY="select distinct CHANNEL,to_char(TRANS_DATE,'dd-mm-yyyy') tdate,(select Count(*) from ACCOUNT_OPEN s1 where s1.RESPONSECODE='0' and s1.CHANNEL=ACCOUNT_OPEN.CHANNEL and to_char(s1.TRANS_DATE,'dd-mm-yyyy')=to_char(ACCOUNT_OPEN.TRANS_DATE,'dd-mm-yyyy')) Success,(select Count(*) from ACCOUNT_OPEN s1 where s1.RESPONSECODE!='0' and s1.CHANNEL=ACCOUNT_OPEN.CHANNEL and to_char(s1.TRANS_DATE,'dd-mm-yyyy')=to_char(ACCOUNT_OPEN.TRANS_DATE,'dd-mm-yyyy') ) fail from ACCOUNT_OPEN  ";
						
					
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					/* if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					} */
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"Quarterly ";
					}
					
					/* if($('#agentuserid').val()!="ALL"){
						QUERY=QUERY+" AND UPPER(BRANCH)=UPPER('"+($("#agentuserid").val()).split("-")[0]+"')";
						SEARCHBYDT=SEARCHBYDT+"And Branch : "+$("#agentuserid").val()+" ";
					}else{
						SEARCHBYDT=SEARCHBYDT+"And Branch : ALL";
					} */
					
					QUERY=QUERY+"  order by to_char(TRANS_DATE,'dd-mm-yyyy')";
					
					$("#fieldsval").val("CHANNEL,TRANSACTION DATE,SUCCESS,FAIL");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="WalletReconciliation_Report"){
					
					SEARCHBYDT="Search By  ";
					
						
					QUERY="select TXN_REF_NO,EXT_TXN_REF_NO,USER_ID,CHANNEL,ACCOUNT,DRCR_FLAG,AMOUNT,Decode(TXN_TYPE,'T','Transaction','F','Commission'),to_char(TXN_STAMP,'dd-mm-yyyy hh:mi:ss'),(SELECT BSCM.SERVICEDESC from BANK_SERVICE_CODE_MASTER BSCM where BSCM.SERVICECODE=WFTP.SERVICECODE) SERVICECODE,NARRATION from wallet_fin_txn_posting WFTP where substr(account,1,1) in  ('1','5','6','0')   ";
						
					
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TXN_STAMP) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TXN_STAMP,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TXN_STAMP,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"Quarterly ";
					}
					
					
					QUERY=QUERY+"  order by TXNID ";
					
					$("#fieldsval").val("WALLET REFERENCE NUMBER,PAYMENT REFERENCE NUMBER,USER ID,CHANNEL,ACCOUNT,DRCR FLAG,AMOUNT,TXN TYPE,DATE AND TIME,SERVICE DESCRIPTION,NARRATION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				
if($("#Report").val()=="balanceenquiry_Report"){
					
					SEARCHBYDT="Search By  ";
					
						
					QUERY="select PAYMET_REF_NO,ACCCOUNTNUMBER,BRANCH_CODE,USERNAME,SERVICECODE,FEE,CHANNEL,TRANS_DTTM,CALLERDESC,OTYPE from BALENQ_FEE_DETAILS   ";
						
					
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"Quarterly ";
					}
					
					
					QUERY=QUERY+"  order by trans_dttm desc";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,ACCOUNT NUMBER,BRANCH CODE,MOBILE NUMBER,SERVICE CODE,FEE,CHANNEL,TRANSACTION DATE,DESCRIPTION,OPERATOR TYPE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="debit_card_request_reports"){
					
					SEARCHBYDT="Search By  ";
					
						
					QUERY="select REQUEST_ID,ACCOUNT_NUMBER,CUSTOMER_NAME,PHONE_NUMBER,CARD_TYPE,CHANNEL,DATE_OF_REQUEST from DEBIT_CARD_REQUEST   ";
						
					
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(DATE_OF_REQUEST) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(DATE_OF_REQUEST,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(DATE_OF_REQUEST,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"Quarterly ";
					}
					
					
					QUERY=QUERY+"  order by DATE_OF_REQUEST desc";
					
					$("#fieldsval").val("REQUEST ID,ACCOUNT NUMBER,CUSTOMER NAME,PHONE NUMBER,CARD TYPE,CHANNEL,REQUEST DATE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Device_Report"){
					
					SEARCHBYDT="Search By  ";
					
						
					QUERY="select decode(OS_TYPE,'ios','IOS','Windows_10','Windows','Android') ,to_char(TRANS_DTTM,'dd-MON-yyyy'),count(*) from MOB_IMEI_DATA where STATUS='A' AND ";
						
					
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					/* if($('#agentuserid').val()==""){
						$("#errormsg").text("Please Select Branch .");
						return false;
					} */
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" TRUNC(to_date(TRANS_DTTM)) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" to_char(TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"Quarterly ";
					}
					
					
					QUERY=QUERY+"  group by decode(OS_TYPE,'ios','IOS','Windows_10','Windows','Android'),to_char(TRANS_DTTM,'dd-MON-yyyy') order by decode(OS_TYPE,'ios','IOS','Windows_10','Windows','Android'),to_char(TRANS_DTTM,'dd-MON-yyyy') ";
					
					$("#fieldsval").val("DEVICE TYPE,UPDATE DATE,COUNT");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				
				if($("#Report").val()=="Agent_Wise_Commission_Report"){
					
					QUERY="select CREDITACCCOUNTNUMBER,CREDITAMOUNT,CREDITBRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,to_number(NVL(AGENT_COMM,'0')) as AGENT_COMM,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE from FUND_TRANSFER_TBL where RESPONSECODE='00'";
	
					
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					if($("#application").val()==""){
						$("#errormsg").text("Please Select Application .");
						return false;
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
					}
					
					QUERY=QUERY+" AND USERID in (select USER_ID from AGENT_INFO_COMMON where AGENT_TYPE='"+$("#application").val()+"')";
					
					
					
					if($("#application").val()=="INTERNAL"){
						if($('#userid').val()!=""){
							QUERY=QUERY+" AND UPPER(USERID)=UPPER('"+$("#userid").val()+"')";	
						}
						
					}else if($("#application").val()=="EXTERNAL"){
						if($('#userid').val()!=""){
							QUERY=QUERY+" AND UPPER(USERID) in (select UPPER(USER_ID) from AGENT_INFO_COMMON where AGENT_ID in (select MERCHANT_ID from MERCHANT_MASTER where Upper(SUPER_AGENT)=Upper(UPPER('"+$("#userid").val()+"'))))";
							
						}
						
						if($('#agentuserid').val()!=""){
							QUERY=QUERY+" AND UPPER(USERID) in (select UPPER(USER_ID) from AGENT_INFO_COMMON where AGENT_ID in (select MERCHANT_ID from MERCHANT_MASTER where Upper(MERCHANT_ID)=Upper(UPPER('"+$("#agentuserid").val()+"'))))";
							
						}
						
						if($('#searchid').val()!=""){
							QUERY=QUERY+" AND UPPER(USERID)=UPPER('"+$("#searchid").val()+"')";							
						}
	
					}
					
					QUERY=QUERY+" ORDER BY USERID";	
					console.log(QUERY)
					
					
					$("#query").val(QUERY);
				}
				
				/* select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate from FUND_TRANSFER_TBL where RESPONSECODE='00'
					AND TRUNC(TRANS_DATE) between to_date('18/10/2017','dd/mm/yyyy') and to_date('18/10/2017','dd/mm/yyyy') AND ACCOUNTNO='0051727889'
				 */
				if($("#Report").val()=="Transaction_Report"){
					SEARCHBYDT="Search By  ";
					var UNCON="";
					
					if($("#newoldreport").val()=="new"){
						
					
						if($("#status").val()=="SUCCESS"){					
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where RESPONSECODE='00' AND TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
						
						}else if($("#status").val()=="FAIL"){
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where RESPONSECODE!='00'  AND TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
						}else{
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where  TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
						}
					
					}
					
					if($("#newoldreport").val()=="old"){
						
						
						if($("#status").val()=="SUCCESS"){					
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL_HST where RESPONSECODE='00' AND TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
						
						}else if($("#status").val()=="FAIL"){
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL_HST where RESPONSECODE!='00'  AND TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
						}else{
						QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss am') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL_HST where  TRANS_TYPE not in ('COND_FEE') ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
						}
					
					}
					
					if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					if($('#searchid').val()!=""){
						QUERY=QUERY+" AND UPPER(TRANS_TYPE)=UPPER('"+$("#searchid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
					}
					
					
					QUERY=QUERY+" ORDER BY tdate desc";
					//console.log(QUERY);
					
					$("#fieldsval").val("ACCOUNT NUMBER,AMOUNT,TRANSACTION DESCRIPTION,CUSTOMER ACCOUNT CR/DR,PAYMENT REFERENCE NUMBER,BATCH REFERENCE NUMBER,CHANNEL,USER ID/MOBILE NUMBER,TRANSACTION DATE AND TIME,RESPONSE MESSAGE,NARRATION,DEBIT ACCCOUNT NUMBER,CREDIT ACCCOUNT NUMBER,STATUS,BENEFICIARY,BENEFICIARY NAME,CREDIT ACCOUNT/BILLER NUMBER,TRANSACTION DATE AND TIME,CLIENT TXN REF,RAAS TXN REF"); 

					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#Report").val()=="Agent_Wise_Transaction_Report"){
					QUERY="select ACCOUNTNO,CREDITAMOUNT,BRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID from FUND_TRANSFER_TBL where RESPONSECODE='00'";
					
					/* if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					 */
					
					
					
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Daily_Transaction_Report"){
					
					
					if($("#status").val()=="SUCCESS"){					
						QUERY="select ACCOUNTNO,CREDITAMOUNT,BRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE from FUND_TRANSFER_TBL where RESPONSECODE='00' AND USERID in (SELECT USER_ID FROM AGENT_INFO_COMMON WHERE AGENT_TYPE='EXTERNAL')";
						}else if($("#status").val()=="FAIL"){
						QUERY="select ACCOUNTNO,CREDITAMOUNT,BRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE from FUND_TRANSFER_TBL where RESPONSECODE!='00' AND USERID in (SELECT USER_ID FROM AGENT_INFO_COMMON WHERE AGENT_TYPE='EXTERNAL')";
						}
						
						if($("#status").val()==""){
							$("#errormsg").text("Please Select Status .");
							return false;
						}
						
						if($("#dateradio").val()==""){
							
							$("#errormsg").text("Please Select Time Period .");
							return false;
						}else if($("#dateradio").val()=="betdate"){
							if($("#fromdate").val()==""){
								$("#errormsg").text("Please enter From Date.");
								return false;
							}
							if($("#todate").val()==""){
								$("#errormsg").text("Please enter To Date.");
								return false;
							}
						}
						if($("#application").val()==""){
							$("#errormsg").text("Please Select Application .");
							return false;
						}
						
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" AND to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						}
						
						QUERY=QUERY+" AND USERID in (select USER_ID from AGENT_INFO_COMMON where AGENT_TYPE='"+$("#application").val()+"')";
						
						
						
						if($("#application").val()=="INTERNAL"){
							if($('#userid').val()!=""){
								QUERY=QUERY+" AND UPPER(USERID)=UPPER('"+$("#userid").val()+"')";	
							}
							
						}else if($("#application").val()=="EXTERNAL"){
							if($('#userid').val()!=""){
								QUERY=QUERY+" AND UPPER(USERID) in (select UPPER(USER_ID) from AGENT_INFO_COMMON where AGENT_ID in (select MERCHANT_ID from MERCHANT_MASTER where Upper(SUPER_AGENT)=Upper(UPPER('"+$("#userid").val()+"'))))";
								
							}
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(USERID) in (select UPPER(USER_ID) from AGENT_INFO_COMMON where AGENT_ID in (select MERCHANT_ID from MERCHANT_MASTER where Upper(MERCHANT_ID)=Upper(UPPER('"+$("#agentuserid").val()+"'))))";
								
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(USERID)=UPPER('"+$("#searchid").val()+"')";							
							}
		
						}
					
					$("#query").val(QUERY);
				}
				
				
				if($("#Report").val()=="Super_Agenct_Wise_Transaction_Report"){
					QUERY="select ACCOUNTNO,CREDITAMOUNT,BRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID from FUND_TRANSFER_TBL where RESPONSECODE='00'";
					$("#query").val(QUERY);
				}
				
				
			if($("#Report").val()=="All_Commission_Report"){
				SEARCHBYDT="Search By  ";
				
					
					
					//if($('#searchid').val()!=""){
						if($("#status").val()=="SUCCESS"){	
							
						 QUERY="select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Fund Transfer to Other Banks' as trans_type from fto_transactions_tbl where FTO_RESP_CODE='00'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Current Month ";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
								SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
							}
							
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
							} 
							
						QUERY=QUERY+" UNION ALL ";
						QUERY=QUERY+"select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Pay Bills '  as trans_type from PAYBILL_TRANS_TBL where PB_RESP_CODE='00' and FEE!='0.0'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
							} 
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								
							} 
							
							SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
						}else if($("#status").val()=="FAIL"){
							 QUERY="select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Fund Transfer to Other Banks' as trans_type from fto_transactions_tbl where FTO_RESP_CODE!='00'";
								if($("#dateradio").val()=="betdate"){
									QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
									SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
								}else if($("#dateradio").val()=="curmnt"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
									SEARCHBYDT=SEARCHBYDT+"And Current Month ";
								}else if($("#dateradio").val()=="quartly"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
									SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
								}
								
								if($('#agentuserid').val()!=""){
									QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
									SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
								}
								
								if($('#searchid').val()!=""){
									QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
									SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
								} 
								
							QUERY=QUERY+" UNION ALL ";
							QUERY=QUERY+"select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Pay Bills '  as trans_type from PAYBILL_TRANS_TBL where PB_RESP_CODE!='00' and FEE!='0.0'";
								if($("#dateradio").val()=="betdate"){
									QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								}else if($("#dateradio").val()=="curmnt"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								}else if($("#dateradio").val()=="quartly"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
								} 
								
								if($('#agentuserid').val()!=""){
									QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
									
								}
								
								if($('#searchid').val()!=""){
									QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
									
								} 
								
								SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
						}else{

							 QUERY="select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Fund Transfer to Other Banks' as trans_type from fto_transactions_tbl where (FTO_RESP_CODE!='00' or FTO_RESP_CODE='00') ";
								if($("#dateradio").val()=="betdate"){
									QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
									SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
								}else if($("#dateradio").val()=="curmnt"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
									SEARCHBYDT=SEARCHBYDT+"And Current Month ";
								}else if($("#dateradio").val()=="quartly"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
									SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
								}
								
								if($('#agentuserid').val()!=""){
									QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
									SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
								}
								
								if($('#searchid').val()!=""){
									QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
									SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
								} 
								
							QUERY=QUERY+" UNION ALL ";
							QUERY=QUERY+"select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Pay Bills '  as trans_type from PAYBILL_TRANS_TBL where (PB_RESP_CODE!='00' or PB_RESP_CODE='00') and FEE!='0.0'";
								if($("#dateradio").val()=="betdate"){
									QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								}else if($("#dateradio").val()=="curmnt"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								}else if($("#dateradio").val()=="quartly"){
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
								} 
								
								if($('#agentuserid').val()!=""){
									QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
									
								}
								
								if($('#searchid').val()!=""){
									QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
									
								} 
								
								SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
						}
						
						if($("#status").val()==""){
							$("#errormsg").text("Please Select Status .");
							return false;
						}
						
						if($("#dateradio").val()==""){
							
							$("#errormsg").text("Please Select Time Period .");
							return false;
						}else if($("#dateradio").val()=="betdate"){
							if($("#fromdate").val()==""){
								$("#errormsg").text("Please enter From Date.");
								return false;
							}
							if($("#todate").val()==""){
								$("#errormsg").text("Please enter To Date.");
								return false;
							}
						}
						
					//}
					
					//console.log(QUERY);
					$("#fieldsval").val("ACCOUNT NUMBER,AMOUNT,FEE,AGENT COMMISSION,CEVA COMMISSION,BANK COMMISSION,VAT COMMISSION,TRANSACTION DATE AND TIME,CHANNEL,TRANSACTION TYPE"); 

					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
			
			if($("#Report").val()=="All_Commission_summary_Open"){
				SEARCHBYDT="Search By  ";
				
					
					
				if($("#status").val()=="SUCCESS"){	
					
					 QUERY="select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Fund Transfer to Other Banks' as TRANS_TYPE,CHANNEL from fto_transactions_tbl where FTO_RESP_CODE='00'";
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+"And Current Month ";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
							SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						}
						
						
						if($('#agentuserid').val()!=""){
							QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
							SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
						}
						
						if($('#searchid').val()!=""){
							QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
							SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
						} 
						QUERY=QUERY+" group by CHANNEL";	
					QUERY=QUERY+" UNION ALL ";
					QUERY=QUERY+"select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Pay Bills '  as TRANS_TYPE,CHANNEL from PAYBILL_TRANS_TBL where PB_RESP_CODE='00' and FEE!='0.0'";
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						}else if($("#dateradio").val()=="quartly"){
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
						} 
						
						if($('#agentuserid').val()!=""){
							QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
							
						}
						
						if($('#searchid').val()!=""){
							QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
							
						} 
						QUERY=QUERY+" group by CHANNEL";
						SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
						 QUERY="select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Fund Transfer to Other Banks' as TRANS_TYPE,CHANNEL from fto_transactions_tbl where FTO_RESP_CODE!='00'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Current Month ";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
								SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
							}
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
							} 
							QUERY=QUERY+" group by CHANNEL";	
						QUERY=QUERY+" UNION ALL ";
						QUERY=QUERY+"select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Pay Bills '  as TRANS_TYPE,CHANNEL from PAYBILL_TRANS_TBL where PB_RESP_CODE!='00' and FEE!='0.0'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
							} 
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								
							} 
							QUERY=QUERY+" group by CHANNEL";
							SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else{

						 QUERY="select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Fund Transfer to Other Banks' as TRANS_TYPE,CHANNEL from fto_transactions_tbl where (FTO_RESP_CODE!='00' or FTO_RESP_CODE='00') ";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Current Month ";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
								SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
							}
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								SEARCHBYDT=SEARCHBYDT+"And Transaction Type : "+$("#searchid").val()+" ";
							} 
							QUERY=QUERY+" group by CHANNEL";	
						QUERY=QUERY+" UNION ALL ";
						QUERY=QUERY+"select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Pay Bills '  as TRANS_TYPE,CHANNEL from PAYBILL_TRANS_TBL where (PB_RESP_CODE!='00' or PB_RESP_CODE='00') and FEE!='0.0'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
							}else if($("#dateradio").val()=="quartly"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'Q')=to_char(sysdate,'Q')";
							} 
							
							if($('#agentuserid').val()!=""){
								QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
								
							}
							
							if($('#searchid').val()!=""){
								QUERY=QUERY+" AND UPPER(trans_type)=UPPER('"+$("#searchid").val()+"')";
								
							} 
							QUERY=QUERY+" group by CHANNEL";
							SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					}
					
					if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
				//}
				
				//console.log(QUERY);
				$("#fieldsval").val("AMOUNT,FEE,AGENT COMMISSION,CEVA COMMISSION,BANK COMMISSION,VAT COMMISSION,TRANSACTION TYPE,CHANNEL"); 

				$("#query").val(QUERY);
				$("#searchby").val(SEARCHBYDT);
					
				}
		
		
			if($("#Report").val()=="debit_card_validation_report"){
				SEARCHBYDT="Search By  ";
					
						QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','L','Partial Rigistration','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,MCF.AUTH_ID,RM.TRANS_DTTM as TRANS_DTTM,RM.CHANNEL as DCHANNEL  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD,DEBIT_CARD_INFO_TBL RM where MCM.CUSTOMER_CODE=RM.CUSTID AND MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P' AND RM.CUSTID is not null AND RM.CHANNEL is not null ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(RM.TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(RM.TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(RM.TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(RM.CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					
					//console.log(QUERY);
					$("#fieldsval").val("CUSTOMER CODE,NAME,USER ID,MOBILE NUMBER,MAIL ID,STATUS,ONBOARD DATE,PRODUCT CODE,PRIMARY ACCOUNT NUMBER,BRANCH CODE,REGISTRATION,LIMIT INCREASE DATE,CHANNEL OF LIMIT INCREASE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="cardless_withdrawal"){
				SEARCHBYDT="Search By  ";
				
				if($("#status").val()=="SUCCESS"){					
							QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status,to_char(TRANSADATE,'dd-mm-yyyy') as tdate from CARDLESS_DATA WHERE CARDLESSRESPCODE='00' ";
							SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
							QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status,to_char(TRANSADATE,'dd-mm-yyyy') as tdate from CARDLESS_DATA WHERE CARDLESSRESPCODE!='00'";
							SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else if($("#status").val()=="ALL"){
						QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status,to_char(TRANSADATE,'dd-mm-yyyy') as tdate from CARDLESS_DATA WHERE (CARDLESSRESPCODE='00' OR CARDLESSRESPCODE!='00') ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
				}
					
					if($("#status").val()==""){
						$("#errormsg").text("Please Select Status .");
						return false;
					}
					
						
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANSADATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANSADATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANSADATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					
					//console.log(QUERY);
					$("#fieldsval").val("ACCOUNT NO ,MOBILE NO,BENEFICIARY MOBILE NO,AMOUNT,CHANNEL,PAYCODE STATUS,TRANSACTION DATE"); 
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			if($("#Report").val()=="sms_data"){
				SEARCHBYDT="Search By  ";
					
						QUERY="select NVL(TRANSTYPE,'OTHER_SMS'),decode(SMSRESPCODE,'00','success','fail') as SMSRESPCODE,count(*) from SMS_DATA WHERE SMSSTATUS='O' ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANSADATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANSADATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANSADATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					QUERY=QUERY+" GROUP BY NVL(TRANSTYPE,'OTHER_SMS'),decode(SMSRESPCODE,'00','success','fail')";
					
					//console.log(QUERY);
					$("#fieldsval").val("TRANSACTION TYPE,SMS RESPCODE,TOTAL SMS"); 
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="ErrorReport"){
				SEARCHBYDT="Search By  ";
				
				if($("#newoldreport").val()=="new"){	
						QUERY="select to_char(TRANS_DATE,'dd-mm-yyyy') as tdt,channel,TRANS_TYPE,RESPONSECODE,RESPONSEMESSAGE,count(*) from FUND_TRANSFER_TBL WHERE (RESPONSECODE='00' OR RESPONSECODE!='00') ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
				}
				
				if($("#newoldreport").val()=="old"){	
					QUERY="select to_char(TRANS_DATE,'dd-mm-yyyy') as tdt,channel,TRANS_TYPE,RESPONSECODE,RESPONSEMESSAGE,count(*) from FUND_TRANSFER_TBL_HST WHERE (RESPONSECODE='00' OR RESPONSECODE!='00') ";
					SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
			     }
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					if($('#agentuserid').val()!=""){
						QUERY=QUERY+" AND UPPER(CHANNEL)=UPPER('"+$("#agentuserid").val()+"')";
						SEARCHBYDT=SEARCHBYDT+"And Channel : "+$("#agentuserid").val()+" ";
					}
					
					QUERY=QUERY+" group by to_char(TRANS_DATE,'dd-mm-yyyy'),channel,TRANS_TYPE,RESPONSECODE,RESPONSEMESSAGE order by tdt,channel,TRANS_TYPE,RESPONSECODE,RESPONSEMESSAGE";
					
					//console.log(QUERY);
					$("#fieldsval").val("DATE,CHANNEL,TRANSACTION TYPE,RESPONSE CODE,RESPONSE MESSAGE,COUNT");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			if($("#Report").val()=="MTNFeeReports"){
				SEARCHBYDT="Search By  ";
					
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select to_char(TRANS_DTTM,'dd-mm-yyyy') as tdate,'Success' as succ,SERVICECODE,nvl(sum(case when respcode = '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_HIST   WHERE "+QUERY1+" AND otype='OPID4' group by to_char(TRANS_DTTM,'dd-mm-yyyy'),'Success',SERVICECODE )" +
					"union all "+
					"(select to_char(TRANS_DTTM,'dd-mm-yyyy') as tdate,'Failed' as succ,SERVICECODE, nvl(sum(case when respcode != '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_BLK   WHERE "+QUERY1+" AND otype='OPID4' and STATUS not in ('X','Y') group by to_char(TRANS_DTTM,'dd-mm-yyyy'),'Failed',SERVICECODE ) order by 1,2,3"
					
				//alert(QUERY);
					$("#fieldsval").val("TRANSACTION DATE,SUCCESS/FAILED,TRANSACTION TYPE,COUNT,TOTAL FEE"); 
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			
			if($("#Report").val()=="MTNFeeTotalReport"){
				SEARCHBYDT="Search By  ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select 'Success' as succ,SERVICECODE,nvl(sum(case when respcode = '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_HIST   WHERE "+QUERY1+" AND otype='OPID4' group by 'Success',SERVICECODE )"+
					" union all "+
					" (select 'Failed' as succ,SERVICECODE, nvl(sum(case when respcode != '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_BLK   WHERE "+QUERY1+"  AND otype='OPID4' and STATUS not in ('X','Y') group by 'Failed',SERVICECODE ) order by 1,2";
					
					
				//alert(QUERY);
					$("#fieldsval").val("SUCCESS/FAILED,TRANSACTION TYPE,COUNT,FEE"); 
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="ReversalTimeReport"){
				SEARCHBYDT="Search By  ";
					
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(f2.TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(f2.TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(f2.TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select f2.ACCOUNTNO as account_no,f2.TRNS_AMT as trans_amt,f2.CHANNEL as channel,f2.TRANS_TYPE as transaction_type,f2.PAYMENTREFERENCE as payment_ref,to_char(f2.TRANS_DATE,'dd-mm-yyyy hh24:mi:ss') as txn_date,f2.DEBITNARRATION as debit_naration,DECODE(f2.RESPONSECODE,00,'Success','Fail') as txnresponse,f1.TRNS_AMT as rev_amt,f1.TRANS_TYPE as rev_transaction_type,f1.PAYMENTREFERENCE as rev_payment_ref,to_char(f1.TRANS_DATE,'dd-mm-yyyy hh24:mi:ss') as rev_txn_date,f1.CREDITNARRATION as credit_naration,DECODE(f1.RESPONSECODE,00,'Success','Fail') as txnrevresponse,round((f1.TRANS_DATE-f2.TRANS_DATE)*60*60*24) as diff_time_second "+
					"from FUND_TRANSFER_TBL f1,FUND_TRANSFER_TBL f2 "+
					"where  f1.PAYMENTREFERENCE='R'||f2.PAYMENTREFERENCE "+
					"and f1.CHANNEL=f2.CHANNEL AND f1.ACCOUNTNO=f2.ACCOUNTNO "+
					"and f1.RESPONSECODE='00' and f2.RESPONSECODE='00' "+
					"AND "+QUERY1+" AND f2.TRANS_TYPE in ('RECHARGE','PAY_BILL')) "+
					"UNION ALL "+
					"(select f2.ACCOUNTNO as account_no,f2.TRNS_AMT as trans_amt,f2.CHANNEL as channel,f2.TRANS_TYPE as transaction_type,f2.DEBITPAYMENTREFERENCE as payment_ref,to_char(f2.TRANS_DATE,'dd-mm-yyyy hh24:mi:ss') as txn_date,f2.DEBITNARRATION as debit_naration,DECODE(f2.RESPONSECODE,00,'Success','Fail') as txnresponse,f1.TRNS_AMT as rev_amt,f1.TRANS_TYPE as rev_transaction_type,f1.DEBITPAYMENTREFERENCE as rev_payment_ref,to_char(f1.TRANS_DATE,'dd-mm-yyyy hh24:mi:ss') as rev_txn_date,f1.CREDITNARRATION as credit_naration,DECODE(f1.RESPONSECODE,00,'Success','Fail') as txnrevresponse,round((f1.TRANS_DATE-f2.TRANS_DATE)*60*60*24) as diff_time_second "+
					"from FUND_TRANSFER_TBL f1,FUND_TRANSFER_TBL f2 "+
					"where  f1.DEBITPAYMENTREFERENCE='R'||REPLACE(f2.DEBITPAYMENTREFERENCE,'~C','') and f1.PAYMENTREFERENCE like '%~C' "+
					"and f1.CHANNEL=f2.CHANNEL AND f1.ACCOUNTNO=f2.ACCOUNTNO "+
					"and f1.RESPONSECODE='00' and f2.RESPONSECODE='00' "+
					"AND "+QUERY1+" AND f2.TRANS_TYPE in ('FUND_TRNS_OTCR') "+
					")";
				//alert(QUERY);
					$("#fieldsval").val("ACCOUNT NO,TXN AMOUNT,TXN CHANNEL,TXN TYPE,PAYMENT REFERENCE NO,TXN DATE AND TIME,DEBIT NARATION,TXN RESPONSE,REV TXN AMOUNT,REVERSAL TXN TYPE,REV PAYMENT REFERENCE NO,REV TXN DATE AND TIME,CREDIT NARATION,REVERSAL TXN RESPONSE,TIME IN SECOND"); 

					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="Transaction_without_rev_Report"){
				SEARCHBYDT="Search By  ";
					
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(FTT1.TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(FTT1.TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(FTT1.TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL FTT1 "+
					"  where "+
					" not exists  (select 1 from FUND_TRANSFER_TBL FTT where FTT.PAYMENTREFERENCE='R'||FTT1.PAYMENTREFERENCE AND FTT.TRANS_TYPE in ('RECHARGE_REVERSAL','PAY_BILL_REVERSAL')) "+
					" AND FTT1.RESPONSECODE='00' AND FTT1.TRANS_TYPE not in('COND_FEE') "+
					" AND "+
					" "+QUERY1+" "+
					" AND FTT1.TRANS_TYPE in ('RECHARGE','PAY_BILL') "+
					" UNION "+
					" select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL FTT1 "+
					" where  "+
					" not exists  (select 1 from FUND_TRANSFER_TBL FTT where FTT.DEBITPAYMENTREFERENCE='R'||REPLACE(FTT1.DEBITPAYMENTREFERENCE,'~C','') AND FTT.TRANS_TYPE in ('FUND_OTHER_REVERSAL')) "+
					" AND FTT1.RESPONSECODE='00' AND FTT1.TRANS_TYPE not in('COND_FEE') "+
					" AND "+
					" "+QUERY1+" "+
					" AND FTT1.TRANS_TYPE in ('FUND_TRNS_OTCR') "+
					" UNION "+
					" select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL FTT1 "+
					" where FTT1.RESPONSECODE='00' AND FTT1.TRANS_TYPE not in('COND_FEE') "+
					" AND "+
					" "+QUERY1+" "+
					" AND FTT1.TRANS_TYPE in ('CUSTFUNDTRANSF','MERCHANTPAY') "+
					" ORDER BY tdate,USERID desc";
				//alert(QUERY);
				
					$("#fieldsval").val("ACCOUNT NUMBER,AMOUNT,TRANSACTION DESCRIPTION,CUSTOMER ACCOUNT CR/DR,PAYMENT REFERENCE NUMBER,BATCH REFERENCE NUMBER,CHANNEL,USER ID/MOBILE NUMBER,TRANSACTION DATE AND TIME,RESPONSE MESSAGE,NARRATION,DEBIT ACCCOUNT NUMBER,CREDIT ACCCOUNT NUMBER,STATUS,BENEFICIARY,BENEFICIARY NAME,CREDIT ACCOUNT/BILLER NUMBER,TRANSACTION DATE AND TIME,CLIENT TXN REF,RAAS TXN REF"); 

					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			if($("#Report").val()=="ReversalSummary"){
				SEARCHBYDT="Search By  ";
					
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						QUERY2=" TRUNC(RRT.TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
						QUERY2="  to_char(RRT.TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						QUERY2=" to_char(RRT.TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select TO_CHAR(TRANS_DATE,'DD-MM-YYYY') as tdate,TRANS_TYPE as trnstype,CHANNEL as channel,count(*) as cnt,nvl(sum(case when RESPONSECODE = '00' then 1 end),0) as SUCCESS_COUNT,nvl(sum(case when RESPONSECODE!= '00' or RESPONSECODE is null then 1 end),0) as FAILED_COUNT,count(*)-(nvl(sum(case when RESPONSECODE = '00' then 1 end),0)+nvl(sum(case when RESPONSECODE!= '00' or RESPONSECODE is null then 1 end),0)) as pending from "+
					"FUND_TRANSFER_TBL where "+QUERY1+"  "+
					"and TRANS_TYPE in ('FUND_OTHER_REVERSAL','RECHARGE_REVERSAL','PAY_BILL_REVERSAL') AND CHANNEL='MOBILE' "+
					"group by TO_CHAR(TRANS_DATE,'DD-MM-YYYY'),TRANS_TYPE,CHANNEL ) "+
					"UNION ALL "+
					"(select TO_CHAR(TRANS_DATE,'DD-MM-YYYY') as tdate,TRANS_TYPE as trnstype,CHANNEL as channel,count(*) as cnt,nvl(sum(case when RESPONSECODE = '00' then 1 end),0) as SUCCESS_COUNT,nvl(sum(case when RESPONSECODE!= '00' or RESPONSECODE is null then 1 end),0) as FAILED_COUNT,count(*)-(nvl(sum(case when RESPONSECODE = '00' then 1 end),0)+nvl(sum(case when RESPONSECODE!= '00' or RESPONSECODE is null then 1 end),0)) as pending from  "+
					"FUND_TRANSFER_TBL where "+QUERY1+"  "+
					"and TRANS_TYPE in ('FUND_OTHER_REVERSAL','PAY_BILL_REVERSAL') AND CHANNEL='USSD' "+
					"group by TO_CHAR(TRANS_DATE,'DD-MM-YYYY'),TRANS_TYPE,CHANNEL ) "+
					"UNION ALL "+
					"(select TO_CHAR(RRT.TRANS_DTTM,'DD-MM-YYYY') as tdate,FT.TRANS_TYPE as trnstype,FT.CHANNEL as channel,count(RRT.REV_PAYMENT_REF_NO) as cnt, "+
					"nvl(sum(case when FT.RESPONSECODE = '00' and RRT.STATUS='C' then 1 end),0) as SUCCESS_COUNT, "+
					"nvl(sum(case when (FT.RESPONSECODE!= '00' or FT.RESPONSECODE is null) and RRT.STATUS='C' then 1 end),0) as FAILED_COUNT, "+
					"nvl(sum(case when (RRT.STATUS= 'P' or RRT.STATUS='MP') then 1 end),0) as PENDING "+
					"from FUND_TRANSFER_TBL FT,REV_RECHARGE_TRANS RRT "+
					"where FT.PAYMENTREFERENCE=RRT.REV_PAYMENT_REF_NO AND "+QUERY2+" and FT.TRANS_TYPE in ('FUND_OTHER_REVERSAL','RECHARGE_REVERSAL','PAY_BILL_REVERSAL') "+
					"and RRT.STATUS in ('C','P','MP') "+
					"group by TO_CHAR(RRT.TRANS_DTTM,'DD-MM-YYYY'),FT.TRANS_TYPE,FT.CHANNEL) order by 3,2 ";
				//alert(QUERY);
				
					$("#fieldsval").val("TRANSACTION DATE,TRANSACTION TYPE,CHANNEL,TOTAL REVERSAL,SUCCESS,FAILED,PENDING"); 

					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			if($("#Report").val()=="EstimateTimeReport"){
				SEARCHBYDT="Search By  ";
					
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#status").val()==""){
							$("#errormsg").text("Please Select Status .");
							return false;
					}
						
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY1=" TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						QUERY2=" TRUNC(TRANS_DTTM) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(TRANS_DATE,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						QUERY2="  to_char(TRANS_DTTM,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						QUERY2=" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					if($("#status").val()=="SUCCESS"){
						QUERY="select BATCHID as batchid,PAYMENTREFERENCE as paymentref,SERVICE_ID as serviceid,ACCOUNTNO as acc_no,CHANNEL as channel,TRANS_TYPE as transtype,TXN_STIME as stime,TXN_ETIME as etime,TRUNUPTIME as trunuptime from fund_transfer_tbl where not exists (Select 1 from REV_RECHARGE_TRANS where "+QUERY2+" AND PAYMET_REF_NO=PAYMENTREFERENCE)"
						+" AND "+QUERY1+" AND TXN_STIME is not null";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY="select BATCHID as batchid,PAYMENTREFERENCE as paymentref,SERVICE_ID as serviceid,ACCOUNTNO as acc_no,CHANNEL as channel,TRANS_TYPE as transtype,TXN_STIME as stime,TXN_ETIME as etime,TRUNUPTIME as trunuptime from fund_transfer_tbl where "+QUERY1+" AND TXN_STIME is not null"
						+"  and PAYMENTREFERENCE  in (Select 'R'||PAYMET_REF_NO from REV_RECHARGE_TRANS where "+QUERY2+")";
					}
					
					if($("#status").val()=="ALL"){
						QUERY="select BATCHID as batchid,PAYMENTREFERENCE as paymentref,SERVICE_ID as serviceid,ACCOUNTNO as acc_no,CHANNEL as channel,TRANS_TYPE as transtype,TXN_STIME as stime,TXN_ETIME as etime,TRUNUPTIME as trunuptime from fund_transfer_tbl where "+QUERY1+" AND TXN_STIME is not null";
						
					}
					
					
				//alert(QUERY);
				
					$("#fieldsval").val("BATCH ID,PAYMENT REFERENCE NO,SERVICE ID,ACCOUNT NO,CHANNEL,TRANSACTION TYPE,STIME,ETIME,TRUNUPTIME");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			if($("#Report").val()=="AgentLocatorSummary"){
				SEARCHBYDT="Search By  ";
					
						QUERY="SELECT TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY') as tdate,nvl(sum(case when FINAL_STATUS in ('AU','CU','AA','COMPLETE') then 1 end),0) as TOT_REQUEST,nvl(sum(case when FINAL_STATUS in('AA','COMPLETE') then 1 end),0) as AGNT_ACCPT,nvl(sum(case when FINAL_STATUS = 'CU' then 1 end),0) as CANCEL_USER,nvl(sum(case when FINAL_STATUS = 'AU' then 1 end),0) as AGNT_NOT_ACCPT,count(*) FROM CUST_ACCEPTS ";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(DSA_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'mm/yyyy')=to_char(sysdate,'mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					
					QUERY=QUERY+" group by TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY') order by tdate desc";
					
					//console.log(QUERY);
					$("#fieldsval").val("DATE,TOTAL REQUEST,AGENT ACCEPTED,CANCELED USER,AGENT NOT ACCEPTED,TOTAL"); 
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			
			if($("#Report").val()=="AgentLocatorReports"){
				SEARCHBYDT="Search By  ";
					
						QUERY="select USER_NAME,CUSTOMER_ID,TITTLE,LATITUDE,LONGITUDE,TNX_DATE,NVL(CUSTOMER_ACCPET_AGENT,' ') as agent_acct,DECODE(FINAL_STATUS,'AU','Customer Request','AA','DSA Accepted','COMPLETE','DSA Accepted','Customer Call Canceled') as status from CUST_ACCEPTS";
						SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
					if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(DSA_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					
					QUERY=QUERY+"  order by DSA_DATE_TIME";
					
					//console.log(QUERY);
					$("#fieldsval").val("USER NAME,USER ID,MESSAGE,LATITUDE,LONGITUDE,REQUEST DATE,ACCEPTED DSA ID,STATUS");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="wallet_agent_service_reports"){
				
				
				
				if($("#servicedt1").val()==""){
					$("#errormsg").text("Please Select Service Types .");
					return false;
				} 
				
				if($("#status").val()==""){
					$("#errormsg").text("Please Select Status .");
					return false;
			    }
				
				if($("#servicedt1").val()=="Agent Fund"){
				
					QUERY="select AFT.PAYMENTREFERENCE,AFT.USERID,DEBITACCCOUNTNUMBER,DEBITBRANCHCODE,TO_CHAR(NVL(AFT.TRNS_AMT,0)),NVL(WFT.CR_ACCOUNT,' '),DECODE(NVL(WFT.CR_ACCOUNT,' '),' ','Failed','Success'),AFT.CHANNEL,AFT.RESPONSECODE,decode(AFT.RESPONSECODE,'00','Success',AFT.RESPONSEMESSAGE),to_char(AFT.TRANS_DATE,'dd-mm-yyyy hh24:mi:ss'),'Agent Fund' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),DEBITNARRATION   from AGENT_FUND_TRANSFER_TBL AFT LEFT JOIN WALLET_FIN_TXN WFT ON  AFT.PAYMENTREFERENCE=WFT.ext_txn_ref_no WHERE TRANS_TYPE='AGENTTOPUP'  ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(AFT.TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(AFT.TRANS_DATE,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(AFT.TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  AFT.RESPONSECODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  AFT.RESPONSECODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and aft.channel='"+$("#servicedt5").val()+"' ";
					}
					
					QUERY = QUERY + " ORDER BY AFT.TRANS_DATE ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,DEBIT ACCOUNT NO,BRANCH,TXN AMOUNT,CREDIT ACCOUNT NO,WALLET RESPONSE,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE,NARRATION");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#servicedt1").val()=="Fund Transafer Other Bank"){
					
					QUERY="select AWFT.FTBATCHID,AWFT.USER_ID,AWFT.FROM_ACCOUNT,AWFT.TO_ACCOUNT,LTRIM(TO_CHAR(NVL(AWFT.AMOUNT,0)),0),AWFT.CHANNEL,AWFT.FTO_RESP_CODE,decode(AWFT.FTO_RESP_CODE,'00','Success',AWFT.FTO_RESP_DESC),to_char(AWFT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),'Fund Transfer to Other Bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),(SELECT BANK_NAME FROM BANKS_DATA WHERE NIBSSCODE=AWFT.BANK_CODE) AS BANK_NAME,NVL(Substr(FTO_REQUEST,instr(FTO_REQUEST,'beneficiaryAccountName',1,1)+25,((instr(FTO_REQUEST, 'BeneficiaryAccountNumber',1,1)-3)-(instr(FTO_REQUEST,'beneficiaryAccountName',1,1)+25))),' ')   from AGENT_WALLET_FTO_TRANS_TBL AWFT LEFT JOIN WALLET_FIN_TXN WFT ON AWFT.FTBATCHID=WFT.EXT_TXN_REF_NO where  substr(TXN_REF_NO,1,1)!='R'  ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(AWFT.TRANS_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(AWFT.TRANS_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(AWFT.TRANS_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  AWFT.FTO_RESP_CODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  AWFT.FTO_RESP_CODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and AWFT.CHANNEL='"+$("#servicedt5").val()+"' ";
					}
					
					if($("#servicedt3").val()!=""){
						QUERY = QUERY + " and AWFT.BANK_CODE='"+$("#servicedt3").val()+"' ";
					}
					
					QUERY = QUERY + " ORDER BY AWFT.TRANS_DATE_TIME ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,DEBIT ACCOUNT NO,CREDIT ACCOUNT NO,TXN AMOUNT,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE,BENEFICIARY BANK NAME,BENEFICIARY NAME");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#servicedt1").val()=="Paybill"){
					
					QUERY="select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,AWPT.PB_RESP_CODE,decode(AWPT.PB_RESP_CODE,'00','Success',AWPT.PB_RESP_DESC),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)) from AGENT_WALLET_PB_TRANS_TBL AWPT LEFT JOIN WALLET_FIN_TXN WFT ON AWPT.FTBATCHID=WFT.ext_txn_ref_no where  substr(TXN_REF_NO,1,1)!='R' AND WFT.SERVICECODE='WALPAYBILLAGN'  ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(AWPT.TRANS_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(AWPT.TRANS_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(AWPT.TRANS_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  AWPT.PB_RESP_CODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  AWPT.PB_RESP_CODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and AWPT.CHANNEL='"+$("#servicedt5").val()+"' ";
					}
					
					QUERY = QUERY + " ORDER BY AWPT.TRANS_DATE_TIME ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,DEBIT ACCOUNT NO,TXN AMOUNT,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#servicedt1").val()=="Recharge"){
					
					QUERY="select AWPT.FTBATCHID,AWPT.USER_ID,AWPT.FROM_ACCOUNT,TO_CHAR(NVL(AWPT.AMOUNT,0)),AWPT.CHANNEL,AWPT.PB_RESP_CODE,decode(AWPT.PB_RESP_CODE,'00','Success',AWPT.PB_RESP_DESC),to_char(AWPT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),DECODE(WFT.SERVICECODE,'WALPAYBILLAGN','Pay Bill','AGNPAYBILLAIRTIME','Recharge') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)) from AGENT_WALLET_PB_TRANS_TBL AWPT LEFT JOIN WALLET_FIN_TXN WFT ON AWPT.FTBATCHID=WFT.ext_txn_ref_no where  substr(TXN_REF_NO,1,1)!='R' AND WFT.SERVICECODE='AGNPAYBILLAIRTIME'  ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(AWPT.TRANS_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(AWPT.TRANS_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(AWPT.TRANS_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  AWPT.PB_RESP_CODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  AWPT.PB_RESP_CODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and AWPT.CHANNEL='"+$("#servicedt5").val()+"' ";
					}
					
					QUERY = QUERY + " ORDER BY AWPT.TRANS_DATE_TIME ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,DEBIT ACCOUNT NO,TXN AMOUNT,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#servicedt1").val()=="Fund Transfer Own Bank"){
					
					QUERY="select AWTT.FTBATCHID,AWTT.USER_ID,AWTT.FROM_ACCOUNT,AWTT.TO_ACCOUNT,LTRIM(TO_CHAR(NVL(AWTT.AMOUNT,0)),0),AWTT.CHANNEL,AWTT.FTO_RESP_CODE,decode(AWTT.FTO_RESP_CODE,'00','Success',FTO_RESP_DESC),to_char(AWTT.TRANS_DATE_TIME,'dd-mm-yyyy hh:mi:ss'),'Fund Transfer Own bank' as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),NVL(Substr(FTO_REQUEST,instr(FTO_REQUEST,'beneficiaryAccountName',1,1)+25,((instr(FTO_REQUEST, 'BeneficiaryAccountNumber',1,1)-3)-(instr(FTO_REQUEST,'beneficiaryAccountName',1,1)+25))),' ') from AGENT_WALLET_TRANS_TBL AWTT LEFT JOIN WALLET_FIN_TXN WFT ON AWTT.FTBATCHID=WFT.ext_txn_ref_no where  substr(TXN_REF_NO,1,1)!='R' ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" AND TRUNC(AWTT.TRANS_DATE_TIME) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(AWTT.TRANS_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" AND to_char(AWTT.TRANS_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  AWTT.FTO_RESP_CODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  AWTT.FTO_RESP_CODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and AWTT.CHANNEL='"+$("#servicedt5").val()+"' ";
					}
					
					
					
					QUERY = QUERY + " ORDER BY AWTT.TRANS_DATE_TIME ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,DEBIT ACCOUNT NO,CREDIT ACCOUNT NO,TXN AMOUNT,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE,BENEFICIARY NAME");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
				if($("#servicedt1").val()=="Card Transaction"){
					
					QUERY="select TTA.BANKRRN,TTA.APPROVEDBY,TTA.PAN,TO_CHAR(NVL(TTA.AMOUNT/100,0)),NVL(WFT.CR_ACCOUNT,' '),DECODE(NVL(WFT.CR_ACCOUNT,' '),' ','Failed','Success'),NVL(WFT.CHANNEL,'POS'),TTA.BANKRESPONSECODE,decode(TTA.BANKRESPONSECODE,'00','Success','Failed'),to_char(TTA.TXNDATE,'dd-mm-yyyy hh:mi:ss'),DECODE(TTA.TXNCODE,'AGNCRDCSHWTDOTH','Cashwithdrawal Card Other bank','AGNCRDCSHWTDOWN','Cashwithdrawal Card Union bank','AGNCRDFTXNOTH','Fundtransfer Card Otherbank','AGNCRDFTXNOWN','Fundtransfer Card Union bank') as scode,TO_CHAR(NVL(WFT.AMOUNT,0)),TO_CHAR(NVL(WFT.FEE_AMT,0)),POSRRN,STAN,TERMINALNUMBER  from TBL_TRANLOG_ALL TTA LEFT JOIN WALLET_FIN_TXN WFT ON TTA.BANKRRN=WFT.ext_txn_ref_no   ";
					SEARCHBYDT="Search By  ";
					
				if($("#dateradio").val()==""){
						
						$("#errormsg").text("Please Select Time Period .");
						return false;
					}else if($("#dateradio").val()=="betdate"){
						if($("#fromdate").val()==""){
							$("#errormsg").text("Please enter From Date.");
							return false;
						}
						if($("#todate").val()==""){
							$("#errormsg").text("Please enter To Date.");
							return false;
						}
					}
					
					if($("#dateradio").val()=="betdate"){
						QUERY=QUERY+" WHERE TRUNC(TTA.TXNDATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" WHERE to_char(TTA.TXNDATE,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(TTA.TXNDATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
						
					}
					
					if($("#status").val()=="SUCCESS"){
					QUERY = QUERY + " AND  TTA.BANKRESPONSECODE='00' ";
					}
					
					if($("#status").val()=="FAIL"){
						QUERY = QUERY + " AND  TTA.BANKRESPONSECODE!='00' ";
					}
					
					if($("#servicedt5").val()!="" && $("#servicedt5").val()!="ALL"){
						QUERY = QUERY + " and WFT.CHANNEL='"+$("#servicedt5").val()+"' ";
					}
					
					if($("#servicedt4").val()=="Own Bank"){
						QUERY = QUERY + " and TTA.PAN LIKE '524289%' or TTA.PAN LIKE '536024%'";
					}
					
					if($("#servicedt4").val()=="Other Bank"){
						QUERY = QUERY + " and TTA.PAN NOT LIKE '524289%' or TTA.PAN NOT LIKE '536024%'";
					}
					
					QUERY = QUERY + " ORDER BY TTA.TXNDATE ";
					
					$("#fieldsval").val("PAYMENT REFERENCE NUMBER,USER ID,CARD NUMBER,TXN AMOUNT,CREDIT ACCOUNT NO,WALLET RESPONSE,CHANNEL,SERVICE RESPONSE CODE,SERVICE RESPONSE MESSAGE,TRANSACTION DATE,SERVICE NAME,AMOUNT,FEE,POS RRN,STAN,TERMINAL NUMBER");
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
			}
		
			 var selValue = $('input[name=reportname]:checked').val(); 
			//alert(selValue);
			var url="";
				
			if($('input[name=reportname]:checked').val()=="Instant"){
				
				$("#ps").css({"display":""}); 
				
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/reportconf.action'; 
					$("#form1").submit();
				
				
				
				return true;
			}
			
			if($('input[name=reportname]:checked').val()=="Requested"){
				
				$("#ps").css({"display":""}); 
				
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/requestreportconf.action'; 
					$("#form1").submit();
				
				
				
				return true;
			}
			
		if($('input[name=reportname]:checked').val()=="Offline"){
				
				$("#ps").css({"display":""}); 
				
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/offlinereportconf.action'; 
					$("#form1").submit();
				
				
				
				return true;
			}
				
				
		/* }
		else
		{
				return false;
		}
	} */
}


var list = "fromdate,todate,balancedt".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: true,
	  		yearRange: '1910:2050',
			changeMonth: true,
			changeYear: true
};	 
$(function() {
	$(list).each(function(i,val){
		$('#'+val).datepicker(datepickerOptions);
	}); 
	
	

	
	var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"95%"}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		    
		    
});


$.validator.addMethod("regex", function(value, element, regexpr) {          
	 return regexpr.test(value);
  }, ""); 
		
function fillaccountno(myval)
{
	if($("#Report").val()=="SIGNOFF REPORTS"){
		$("#refnodis").css({"display":""});
		
		$('#referenceno').empty();
		$('#referenceno').append(getOptionFor('Select Reference Number',''));
		
		

		var formInput="referenceno="+myval;
	
		
		$.ajax({
		
	     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
	     type: "POST",
	     data : formInput,
	     success: function(data, textStatus, jqXHR)
	     {
				
				console.log("data "+JSON.stringify(data.responseJSON));
				
				var userlist = data.responseJSON.ACCOUNTNO;
				
				alert("userlist ["+userlist+"]");
				
				$('#referenceno').prop('selectedIndex',0);
				$('#referenceno').trigger("liszt:updated");
				
				var i;
				for(i=0;i<userlist.length;i++)
				{
					
						$('#referenceno').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
						$('#referenceno').trigger("liszt:updated");
				
					
				}
	     }
		 
	 });
		

	}
	
	
}

function getOptionFor(thisValue,optionValue)
{
		return "<option value='"+optionValue+"'>"+thisValue+"</option>";
}

$(function(){

	
	$("#xlsx").prop("checked", true);
	
	
	
$("#Report").on('change',function(event){
	//alert($("#Report").val());
	
	$("#jrxmlname").val($("#Report").val())
	
	$("#newold").css({"display":"none"});
	$("#refnodis").css({"display":"none"});
	$("#refnodis1").css({"display":"none"});
	$("#refnodis2").css({"display":"none"});
	$("#refnodis3").css({"display":"none"});
	$("#tabdate").css({"display":"none"});
	$("#tabdate1").css({"display":"none"});
	
	$("#tab11").css({"display":"none"});
	$("#tab12").css({"display":"none"});
	$("#tab13").css({"display":"none"});
	$("#tab14").css({"display":"none"});
	$("#tab15").css({"display":"none"});
	$("#tab16").css({"display":"none"});
	
	$("#tab1").css({"display":"none"});
	$("#tab2").css({"display":"none"});
	$("#tab3").css({"display":"none"});
	$("#tab4").css({"display":"none"});
	
	$("#st3").css({"display":"none"});
	$("#st4").css({"display":"none"});
	$("#st1").css({"display":""});
	$("#st2").css({"display":""});
	
	$("#onboard1").css({"display":"none"});
	$("#onboard2").css({"display":"none"});
	
	$("#wst3").css({"display":"none"});
	$("#wst4").css({"display":"none"});
	
	$("#onboard3").css({"display":"none"});
	$("#onboard4").css({"display":"none"});
	$("#onboard5").css({"display":"none"});
	$("#tabbaldate").css({"display":"none"});
	$("#servicetr").css({"display":"none"});
	$("#servicetr1").css({"display":"none"});
	

	if($("#Report").val()=="Account_Open" || $("#Report").val()=="Account_summary_Open" || $("#Report").val()=="Device_Report" || $("#Report").val()=="WalletReconciliation_Report" || $("#Report").val()=="balanceenquiry_Report" || $("#Report").val()=="debit_card_request_reports"){
		/* $("#tabdate").css({"display":""}); */
		$("#refnodis").css({"display":"none"});
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
		$("#st3").css({"display":""});
		$("#st4").css({"display":""});
		$("#st1").css({"display":"none"});
		$("#st2").css({"display":"none"});
		
		if($("#Report").val()=="Account_summary_Open" || $("#Report").val()=="Device_Report" || $("#Report").val()=="WalletReconciliation_Report" || $("#Report").val()=="balanceenquiry_Report" || $("#Report").val()=="debit_card_request_reports"){
			$("#st3").css({"display":"none"});
			$("#st4").css({"display":"none"});
			$("#st1").css({"display":""});
			$("#st2").css({"display":""});
		}
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		if($("#Report").val()=="Account_summary_Open" || $("#Report").val()=="Device_Report" || $("#Report").val()=="WalletReconciliation_Report" || $("#Report").val()=="balanceenquiry_Report"  || $("#Report").val()=="debit_card_request_reports"){
			$("#refnodis1").css({"display":"none"});
		}
		
$('#searchagentuserid').html("Account Type <font color=\"red\">*</font>");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select','')); 
		
		
		var formInput="reporttype=APPLICATION-ACCOUNT";
		
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					
					for(i=0;i<userlist.length;i++)
					{
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		
		
	}
	
		if($("#Report").val()=="Daily_Transaction_Report" || $("#Report").val()=="Agent_Wise_Commission_Report"){
			
			/* $("#tabdate").css({"display":""}); */
			$("#refnodis").css({"display":"none"});
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			$("#st3").css({"display":""});
			$("#st4").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			
			if($("#Report").val()=="Agent_Wise_Commission_Report"){
				$("#st3").css({"display":"none"});
				$("#st4").css({"display":"none"});
				$("#st1").css({"display":""});
				$("#st2").css({"display":""});
			}
	
			if($("#Report").val()=="Account_summary_Open" || $("#Report").val()=="WalletReconciliation_Report" || $("#Report").val()=="balanceenquiry_Report"  || $("#Report").val()=="debit_card_request_reports"){
				$("#st3").css({"display":"none"});
				$("#st4").css({"display":"none"});
				$("#st1").css({"display":""});
				$("#st2").css({"display":""});
			}
			
			$("#application").on('change',function(event){
				$("#refnodis1").css({"display":"none"});
				
					
					if($("#application").val()=="INTERNAL"){
						$('#searchuser').text("User Id");
						$("#tab1").css({"display":""});
						$("#tab2").css({"display":""});
						$("#tab3").css({"display":"none"});
						$("#tab4").css({"display":"none"});
						
						$('#userid').empty();
						$('#userid').append(getOptionFor('Select User Id',''));
						
					}else{
						$("#tab1").css({"display":""});
						$("#tab2").css({"display":""});
						$("#tab3").css({"display":"none"});
						$("#tab4").css({"display":"none"});
						$('#searchuser').text("Super Agent");
						
						$('#userid').empty();
						$('#userid').append(getOptionFor('Select Super Agent',''));
					}
					
							
					
					
					var formInput="reporttype=APPLICATION-"+$("#application").val();
					
					$.ajax({
						
					     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
					     type: "POST",
					     data : formInput,
					     success: function(data, textStatus, jqXHR)
					     {
								
								console.log("data "+JSON.stringify(data.rptresponseJSON));
								
								var userlist = data.responseJSON.ACCOUNTNO;
								
								//alert("userlist ["+userlist+"]");
								
								$('#userid').prop('selectedIndex',0);
								$('#userid').trigger("liszt:updated");
								
								var i;
								for(i=0;i<userlist.length;i++)
								{
									
										$('#userid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
										$('#userid').trigger("liszt:updated");
								
									
								}
					     }
					 });
					
					
			
			});
			
			
			
		$("#userid").on('change',function(event){
				
				if($("#application").val()=="EXTERNAL"){
					$('#searchagentuserid').text("Agent Id");
					$("#refnodis1").css({"display":""});
					$("#tab11").css({"display":""});
					$("#tab12").css({"display":""});
					$("#tab15").css({"display":""});
					$("#tab16").css({"display":""});
							
					$('#agentuserid').empty();
					$('#agentuserid').append(getOptionFor('Select Agent Id',''));	
					
					
				var formInput="reporttype=AGENTIDS-"+$("#userid").val();
					
					$.ajax({
						
					     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
					     type: "POST",
					     data : formInput,
					     success: function(data, textStatus, jqXHR)
					     {
								
								console.log("data "+JSON.stringify(data.rptresponseJSON));
								
								var userlist = data.responseJSON.ACCOUNTNO;
								
								//alert("userlist ["+userlist+"]");
								
								$('#agentuserid').prop('selectedIndex',0);
								$('#agentuserid').trigger("liszt:updated");
								
								var i;
								for(i=0;i<userlist.length;i++)
								{
									
										$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
										$('#agentuserid').trigger("liszt:updated");
								
									
								}
					     }
					 });
					
					
					
				}
			});
			
			
		$("#agentuserid").on('change',function(event){
			
			if($("#application").val()=="EXTERNAL"){
				$('#search').text("Agent User Id");
				$("#refnodis1").css({"display":""});
				$("#tab11").css({"display":""});
				$("#tab12").css({"display":""});
				$("#tab13").css({"display":""});
				$("#tab14").css({"display":""});
				$("#tab15").css({"display":"none"});
				$("#tab16").css({"display":"none"});
						
				$('#searchid').empty();
				$('#searchid').append(getOptionFor('Select Agent Id',''));	
				
				
			var formInput="reporttype=AGENTUSERIDS-"+$("#agentuserid").val();
				
				$.ajax({
					
				     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
				     type: "POST",
				     data : formInput,
				     success: function(data, textStatus, jqXHR)
				     {
							
							console.log("data "+JSON.stringify(data.rptresponseJSON));
							
							var userlist = data.responseJSON.ACCOUNTNO;
							
							//alert("userlist ["+userlist+"]");
							
							$('#searchid').prop('selectedIndex',0);
							$('#searchid').trigger("liszt:updated");
							
							var i;
							for(i=0;i<userlist.length;i++)
							{
								
									$('#searchid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
									$('#searchid').trigger("liszt:updated");
							
								
							}
				     }
				 });
				
				
				
			}
		});
	}else if($("#Report").val()=="Agent_Wise_Transaction_Report"){
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
	}else if($("#Report").val()=="Agent_Users" || $("#Report").val()=="Agent_List" || $("#Report").val()=="Store_List" || $("#Report").val()=="Super_Agenct_Wise_Transaction_Report" || $("#Report").val()=="Teller_Reconcile_Report" || $("#Report").val()=="Audit_Trail"  || $("#Report").val()=="Merchanr_Product_Report"  || $("#Report").val()=="Sale_Order_Report" || $("#Report").val()=="Session_Fraud_Monitoring_Report" ){
		
		if($("#Report").val()=="Super_Agenct_Wise_Transaction_Report"){
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
		}
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		$("#newold").css({"display":"none"});
		
		if($("#Report").val()=="Audit_Trail"){
			$("#newold").css({"display":""});
			$("#newoldreport1").text("Audit Trail ");
			
		}
		
		if($("#Report").val()=="Teller_Reconcile_Report"){
			
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			
			$('#searchagentuserid').text("Teller Id");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select Teller Id',''));
			var formInput="reporttype=APPLICATION-TELLER";
		}else if($("#Report").val()=="Audit_Trail"){
			
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			
			$('#searchagentuserid').text("Action Name");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select Action Name',''));
			var formInput="reporttype=APPLICATION-AUDIT";
		}else if($("#Report").val()=="Session_Fraud_Monitoring_Report"){
			
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			
			$("#refnodis1").css({"display":"none"});
			
			$('#searchagentuserid').text("Action Name");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select Action Name',''));
			var formInput="reporttype=APPLICATION-AUDIT";
		}else if($("#Report").val()=="Merchanr_Product_Report"){
			
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			
			$('#searchagentuserid').text("Merchant Name");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
			var formInput="reporttype=APPLICATION-MERCHANT";
		}else if($("#Report").val()=="Sale_Order_Report"){
			
			$("#tabdate1").css({"display":""});
			$("#refnodis2").css({"display":""});
			$("#refnodis3").css({"display":""});
			
			$('#searchagentuserid').text("Merchant Name");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
			var formInput="reporttype=APPLICATION-MERCHANT";
		}else{
		$('#searchagentuserid').text("Super Agent");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select Super Agent',''));
		var formInput="reporttype=APPLICATION-EXTERNAL";
		}
		
		
		
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					for(i=0;i<userlist.length;i++)
					{
						
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		
		$("#agentuserid").on('change',function(event){
			
			if($("#Report").val()=="Agent_Users" || $("#Report").val()=="Store_List"){
				$('#search').text("Agent Id");
				$("#refnodis1").css({"display":""});
				$("#tab11").css({"display":""});
				$("#tab12").css({"display":""});
				$("#tab13").css({"display":""});
				$("#tab14").css({"display":""});
				$("#tab15").css({"display":"none"});
				$("#tab16").css({"display":"none"});
						
				$('#searchid').empty();
				$('#searchid').append(getOptionFor('Select Agent Id',''));	
				
				
			var formInput="reporttype=AGENTIDS-"+$("#agentuserid").val();
				
				$.ajax({
					
				     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
				     type: "POST",
				     data : formInput,
				     success: function(data, textStatus, jqXHR)
				     {
							
							console.log("data "+JSON.stringify(data.rptresponseJSON));
							
							var userlist = data.responseJSON.ACCOUNTNO;
							
							//alert("userlist ["+userlist+"]");
							
							$('#searchid').prop('selectedIndex',0);
							$('#searchid').trigger("liszt:updated");
							
							var i;
							for(i=0;i<userlist.length;i++)
							{
								
									$('#searchid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
									$('#searchid').trigger("liszt:updated");
							
								
							}
				     }
				 });
				
				
				
			}
		});
	
	}else if($("#Report").val()=="Fee_Report"  || $("#Report").val()=="Limit_Report" || $("#Report").val()=="commission_Report" || $("#Report").val()=="Wallet_Balance_Sheet_reports" || $("#Report").val()=="inventory_reports"){
		
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		$("#st3").css({"display":"none"});
		$("#st4").css({"display":"none"});
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		$("#onboard3").css({"display":"none"});
		$("#onboard4").css({"display":"none"});
		$("#onboard5").css({"display":"none"});
		
		$("#tabbaldate").css({"display":"none"});
		
		
	$('#searchagentuserid').html("Product <font color='red'>*</font>");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select Product',''));
		
		if($("#Report").val()=="Fee_Report" || $("#Report").val()=="commission_Report"){
			var formInput="reporttype=PRODUCT-FEE";
		}else if($("#Report").val()=="Limit_Report"){
			var formInput="reporttype=PRODUCT-LIMIT";
		}else if($("#Report").val()=="Wallet_Balance_Sheet_reports"){
			$("#tabbaldate").css({"display":""});
			$('#searchagentuserid').html("Wallet Type <font color='red'>*</font>");
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
			var formInput="reporttype=WALLET-TYPE";
		}else if($("#Report").val()=="inventory_reports"){
			
			$("#onboard3").css({"display":""});
			$("#onboard4").css({"display":""});
			$("#onboard5").css({"display":""});
			
			$("#searchop").text("Terminal Number ");
			$("#searchop1").text("Serial Number ");
			
			$('#searchagentuserid').html("State <font color='red'>*</font>");
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
			var formInput="reporttype=INVENTORY-REPORT";
		}
		
		
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					for(i=0;i<userlist.length;i++)
					{
						
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		
		
	}else if($("#Report").val()=="Users_Management_Report" || $("#Report").val()=="Users_Groups_Report"){
		
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		$("#st3").css({"display":"none"});
		$("#st4").css({"display":"none"});
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		
		
	
		
		if($("#Report").val()=="Users_Management_Report"){
			var formInput="reporttype=APPLICATION-LOCATION";
			$('#searchagentuserid').html("Branch Location ");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
		}else if($("#Report").val()=="Users_Groups_Report"){
			var formInput="reporttype=APPLICATION-GROUP";
			$('#searchagentuserid').html("User Group ");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select',''));
		}
		
		
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					for(i=0;i<userlist.length;i++)
					{
						
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		
		
	}else if($("#Report").val()=="users_onboard" || $("#Report").val()=="Reg_Merchant_Report" || $("#Report").val()=="users_onboard_summary" || $("#Report").val()=="RM_Report"){
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});

		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		$("#st3").css({"display":"none"});
		$("#st4").css({"display":"none"});
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		
		$("#onboard1").css({"display":"none"});
		$("#onboard2").css({"display":"none"});
		
		if($("#Report").val()=="users_onboard" || $("#Report").val()=="RM_Report"){
			$("#onboard1").css({"display":""});
			$("#onboard2").css({"display":""});
			
			var formInput="reporttype=PRODUCT-USER";
			
			$.ajax({
				
			     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
			     type: "POST",
			     data : formInput,
			     success: function(data, textStatus, jqXHR)
			     {
						
						console.log("data "+JSON.stringify(data.rptresponseJSON));
						
						var userlist = data.responseJSON.ACCOUNTNO;
						
						//alert("userlist ["+userlist+"]");
						
						$('#product1').prop('selectedIndex',0);
						$('#product1').trigger("liszt:updated");
						
						var i;
						for(i=0;i<userlist.length;i++)
						{
							
								$('#product1').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
								$('#product1').trigger("liszt:updated");
						
							
						}
			     }
			 });
			
			
		}
		
		if($("#Report").val()=="RM_Report"){
			
			$('#searchagentuserid').html("BRANCH DATA CAPTURE <font color=\"red\">*</font>");
			
			$('#agentuserid').empty();
			$('#agentuserid').append(getOptionFor('Select','')); 
			
			
			var formInput="reporttype=APPLICATION-RMCODE";
		}else{
	$('#searchagentuserid').html("Branch <font color=\"red\">*</font>");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select','')); 
		
		
		var formInput="reporttype=APPLICATION-BRANCH";
		}
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					
					for(i=0;i<userlist.length;i++)
					{
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		
	}else if($("#Report").val()=="WalletAccountOpenReport" || $("#Report").val()=="WalletTransactionPosting_Report" || $("#Report").val()=="WalletTransaction_Report" || $("#Report").val()=="WalletAgentReport" || $("#Report").val()=="WalletSuspense_Report" || $("#Report").val()=="wallet_agent_settlement_reports" || $("#Report").val()=="wallet_agent_unsettlement_reports" || $("#Report").val()=="wallet_agent_service_reports"){
		
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":"none"});
		$("#tab12").css({"display":"none"});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		$("#onboard3").css({"display":"none"});
		
		$("#wst3").css({"display":"none"});
		$("#wst4").css({"display":"none"});
		$("#servicetr").css({"display":"none"});
		$("#servicetr1").css({"display":"none"});
		
		
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		
		if($("#Report").val()=="WalletAccountOpenReport"){
			$("#tab11").css({"display":""});
			$("#tab12").css({"display":""});
		$('#searchagentuserid').text("Account Open Type");
		}
		
		if($("#Report").val()=="wallet_agent_service_reports"){
			$("#tab15").css({"display":"none"});
			$("#tab16").css({"display":"none"});
			$("#servicetr").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			$("#st3").css({"display":""});
			$("#st4").css({"display":""});
			$("#servicetr1").css({"display":""});
		}
		
		if($("#Report").val()=="WalletTransactionPosting_Report"){
			$("#wst3").css({"display":""});
			$("#wst4").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			$("#st3").css({"display":"none"});
			$("#st4").css({"display":"none"});
			
			/* $("#tab13").css({"display":""});
			$("#tab14").css({"display":""});
			$("#tab15").css({"display":"none"});
			$("#tab16").css({"display":"none"});
			
			$("#wsttab1").css({"display":""});
			$("#wsttab2").css({"display":""});
			$("#wsttab3").css({"display":"none"});
			$("#wsttab4").css({"display":"none"});
			
			
			$('#searchagentuserid').text("Channel");
			$('#search').text("Transaction Type"); */
			
			
		}
		
		if($("#Report").val()=="WalletTransaction_Report"){
			$("#wst3").css({"display":""});
			$("#wst4").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			$("#st3").css({"display":""});
			$("#st4").css({"display":""});
			/* $("#tab13").css({"display":""});
			$("#tab14").css({"display":""});
			$("#tab15").css({"display":"none"});
			$("#tab16").css({"display":"none"});
			
			$("#wsttab1").css({"display":""});
			$("#wsttab2").css({"display":""});
			$("#wsttab3").css({"display":"none"});
			$("#wsttab4").css({"display":"none"});
			$('#searchagentuserid').text("Channel");
			$('#search').text("Transaction Type"); */
		}
		
		if($("#Report").val()=="WalletSuspense_Report"){
			$("#tab11").css({"display":""});
			$("#tab12").css({"display":""});
			$("#wst3").css({"display":""});
			$("#wst4").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			$("#st3").css({"display":"none"});
			$("#st4").css({"display":"none"});
			$("#wsttab1").css({"display":"none"});
			$("#wsttab2").css({"display":"none"});
			$("#wsttab3").css({"display":""});
			$("#wsttab4").css({"display":""});
			
			$('#searchagentuserid').text("Suspense  Account Type");
		}
		
		if($("#Report").val()=="wallet_agent_settlement_reports" || $("#Report").val()=="wallet_agent_unsettlement_reports"){
			$("#tab11").css({"display":""});
			$("#tab12").css({"display":""});
			$("#wst3").css({"display":""});
			$("#wst4").css({"display":""});
			$("#st1").css({"display":"none"});
			$("#st2").css({"display":"none"});
			$("#st3").css({"display":"none"});
			$("#st4").css({"display":"none"});
			$("#wsttab1").css({"display":"none"});
			$("#wsttab2").css({"display":"none"});
			$("#wsttab3").css({"display":""});
			$("#wsttab4").css({"display":""});
			
			$('#searchagentuserid').text("Transaction Type");
		}
		
		if($("#Report").val()=="WalletAgentReport"){
			$("#tab11").css({"display":""});
			$("#tab12").css({"display":""});
			$('#searchagentuserid').text("Super Agent");
		}
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select','')); 
		
		if($("#Report").val()=="WalletSuspense_Report"){
			var formInput="reporttype=APPLICATION-SUSPENSE-"+$("#Report").val();
		}else if($("#Report").val()=="wallet_agent_settlement_reports" || $("#Report").val()=="wallet_agent_unsettlement_reports"){
			var formInput="reporttype=APPLICATION-WALLTRAN-AGENT";
		}else{
			var formInput="reporttype=APPLICATION-WACCOPEN-"+$("#Report").val();
		}
		
		
		if($("#Report").val()=="wallet_agent_service_reports"){
			var formInput="reporttype=APPLICATION-WCHANNEL-AGENT";
			$.ajax({
				
			     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
			     type: "POST",
			     data : formInput,
			     success: function(data, textStatus, jqXHR)
			     {
						
						console.log("data "+JSON.stringify(data.rptresponseJSON));
						
						var userlist = data.responseJSON.ACCOUNTNO;
						
						//alert("userlist ["+userlist+"]");
						
						$('#servicedt5').prop('selectedIndex',0);
						$('#servicedt5').trigger("liszt:updated");
						
						var i;
						for(i=0;i<userlist.length;i++)
						{
							
								$('#servicedt5').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
								$('#servicedt5').trigger("liszt:updated");
						
							
						}
			     }
			 });
			
		}else{
				
				$.ajax({
					
				     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
				     type: "POST",
				     data : formInput,
				     success: function(data, textStatus, jqXHR)
				     {
							
							console.log("data "+JSON.stringify(data.rptresponseJSON));
							
							var userlist = data.responseJSON.ACCOUNTNO;
							
							//alert("userlist ["+userlist+"]");
							
							$('#agentuserid').prop('selectedIndex',0);
							$('#agentuserid').trigger("liszt:updated");
							
							var i;
							for(i=0;i<userlist.length;i++)
							{
								
									$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
									$('#agentuserid').trigger("liszt:updated");
							
								
							}
				     }
				 });
				
		}
		
	}else if($("#Report").val()=="Transaction_Report" || $("#Report").val()=="Transaction_without_rev_Report" || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="EstimateTimeReport" || $("#Report").val()=="Transaction_summary_report" || $("#Report").val()=="All_Commission_Report"  || $("#Report").val()=="All_Commission_summary_Open" || $("#Report").val()=="cardless_withdrawal" || $("#Report").val()=="debit_card_validation_report" || $("#Report").val()=="sms_data"  || $("#Report").val()=="ErrorReport"  || $("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports" || $("#Report").val()=="Unsettlement_69_Reports" || $("#Report").val()=="failed_card_transaction_reports" || $("#Report").val()=="card_transaction_09_reports" || $("#Report").val()=="wallet_agent_commission_reports" || $("#Report").val()=="wallet_commission_reports"){
		
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		$("#newold").css({"display":"none"});
		
		if($("#Report").val()=="Transaction_Report"){
			$("#newold").css({"display":""});
			$("#newoldreport1").text("Transaction Report ");
			
		}
		
		if($("#Report").val()=="Transaction_summary_report"){
			$("#newold").css({"display":""});
			$("#newoldreport1").text("Transaction Summary Report ");
			
		}
		
		if($("#Report").val()=="ErrorReport"){
			$("#newold").css({"display":""});
			$("#newoldreport1").text("Error Response Reports ");
			
		}
		
		
		if($("#Report").val()=="Unsettlement_69_Reports" || $("#Report").val()=="failed_card_transaction_reports" || $("#Report").val()=="card_transaction_09_reports" || $("#Report").val()=="wallet_agent_commission_reports" || $("#Report").val()=="wallet_commission_reports"){
			$("#tab11").css({"display":"none"});
			$("#tab12").css({"display":"none"});
		}
		
		if($("#Report").val()=="debit_card_validation_report" || $("#Report").val()=="sms_data"  || $("#Report").val()=="ErrorReport"  || $("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="Transaction_without_rev_Report" || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary" || $("#Report").val()=="Unsettlement_69_Reports" || $("#Report").val()=="failed_card_transaction_reports" || $("#Report").val()=="card_transaction_09_reports" || $("#Report").val()=="wallet_agent_commission_reports" || $("#Report").val()=="wallet_commission_reports"){
			
			$("#st3").css({"display":"none"});
			$("#st4").css({"display":"none"});
			$("#st1").css({"display":""});
			$("#st2").css({"display":""});
			
		}else{
		
		$("#st3").css({"display":""});
		$("#st4").css({"display":""});
		$("#st1").css({"display":"none"});
		$("#st2").css({"display":"none"});
		}
		
		if($("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="EstimateTimeReport"  || $("#Report").val()=="Transaction_without_rev_Report" || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary"){
			$("#refnodis1").css({"display":"none"});
		}
		
		
		$('#searchagentuserid').text("Channel");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select','')); 
		
		
		var formInput="reporttype=APPLICATION-CHANNEL";
		
		$.ajax({
			
		     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
		     type: "POST",
		     data : formInput,
		     success: function(data, textStatus, jqXHR)
		     {
					
					console.log("data "+JSON.stringify(data.rptresponseJSON));
					
					var userlist = data.responseJSON.ACCOUNTNO;
					
					//alert("userlist ["+userlist+"]");
					
					$('#agentuserid').prop('selectedIndex',0);
					$('#agentuserid').trigger("liszt:updated");
					
					var i;
					for(i=0;i<userlist.length;i++)
					{
						
							$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#agentuserid').trigger("liszt:updated");
					
						
					}
		     }
		 });
		
		if($("#Report").val()=="Transaction_Report" || $("#Report").val()=="Transaction_summary_report" || $("#Report").val()=="All_Commission_Report"  || $("#Report").val()=="All_Commission_summary_Open"){
			
		
		
		$("#agentuserid").on('change',function(event){
			
			$('#search').text("Transaction Type");
			
			$("#tab13").css({"display":""});
			$("#tab14").css({"display":""});
			$("#tab15").css({"display":"none"});
			$("#tab16").css({"display":"none"});
					
			$('#searchid').empty();
			$('#searchid').append(getOptionFor('Select',''));	
			
		if($("#Report").val()=="All_Commission_Report"  || $("#Report").val()=="All_Commission_summary_Open" ){
			var formInput="reporttype=TRANSTYPE1-"+$("#agentuserid").val();
		}else if($("#Report").val()=="ReversalTimeReport" ){
			var formInput="reporttype=TRANSTYPE2-"+$("#agentuserid").val();
		}else{	
		var formInput="reporttype=TRANSTYPE-"+$("#agentuserid").val();
		}	
			$.ajax({
				
			     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
			     type: "POST",
			     data : formInput,
			     success: function(data, textStatus, jqXHR)
			     {
						
						console.log("data "+JSON.stringify(data.rptresponseJSON));
						
						var userlist = data.responseJSON.ACCOUNTNO;
						
						//alert("userlist ["+userlist+"]");
						
						$('#searchid').prop('selectedIndex',0);
						$('#searchid').trigger("liszt:updated");
						
						var i;
						for(i=0;i<userlist.length;i++)
						{
							
								$('#searchid').append(getOptionFor((userlist[i].accountno).split('-')[1],(userlist[i].accountno).split('-')[0]));
								$('#searchid').trigger("liszt:updated");
						
							
						}
			     }
			 });
			
			
			
		
	});
	
	}
		
	}
});	
		
});


$(function(){
	
	
	
	
	$("#report12").on('change',function(event){
	var formInput="reporttype=APPLICATION-WALLTRAN-"+$("#report12").val();
	var formInput1="reporttype=APPLICATION-WCHANNEL-"+$("#report12").val();
	
	$("#onboard3").css({"display":""});
	$("#searchop").text($("#report12").val()+" MOBILE NUMBER");
	$("#tab11").css({"display":""});
	$("#tab12").css({"display":""});
	$("#tab13").css({"display":""});
	$("#tab14").css({"display":""});
	$("#tab15").css({"display":"none"});
	$("#tab16").css({"display":"none"});
	
	$("#wsttab1").css({"display":""});
	$("#wsttab2").css({"display":""});
	$("#wsttab3").css({"display":"none"});
	$("#wsttab4").css({"display":"none"});
	
	
	$('#searchagentuserid').text("Channel");
	$('#search').text("Transaction Type"); 
	
	//alert($("#report12").val())
	$('#agentuserid').empty();
	$('#agentuserid').append(getOptionFor('Select',''));
	
	$('#searchid').empty();
	$('#searchid').append(getOptionFor('Select',''));
	
				$.ajax({
					
				     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
				     type: "POST",
				     data : formInput,
				     success: function(data, textStatus, jqXHR)
				     {
							
							console.log("data "+JSON.stringify(data.rptresponseJSON));
							
							var userlist = data.responseJSON.ACCOUNTNO;
							
							//alert("userlist ["+userlist+"]");
							
							$('#searchid').prop('selectedIndex',0);
							$('#searchid').trigger("liszt:updated");
							
							var i;
							for(i=0;i<userlist.length;i++)
							{
								
									$('#searchid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
									$('#searchid').trigger("liszt:updated");
							
								
							}
				     }
				 });
				
				
				$.ajax({
					
				     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
				     type: "POST",
				     data : formInput1,
				     success: function(data, textStatus, jqXHR)
				     {
							
							console.log("data "+JSON.stringify(data.rptresponseJSON));
							
							var userlist = data.responseJSON.ACCOUNTNO;
							
							//alert("userlist ["+userlist+"]");
							
							$('#agentuserid').prop('selectedIndex',0);
							$('#agentuserid').trigger("liszt:updated");
							
							var i;
							for(i=0;i<userlist.length;i++)
							{
								
									$('#agentuserid').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
									$('#agentuserid').trigger("liszt:updated");
							
								
							}
				     }
				 });
				
				
			 });
	
	
	$("#servicedt1").on('change',function(event){
		//alert($("#servicedt1").val())
		$("#sb1").css({"display":"none"});
		$("#sb2").css({"display":"none"});
		$("#sb5").css({"display":"none"});
		$("#sb6").css({"display":"none"});
		$("#sb3").css({"display":""});
		$("#sb4").css({"display":""});
		$("#refnodis1").css({"display":""});
		
		
		if($("#servicedt1").val()=="Fund Transafer Other Bank"){
			$("#sb1").css({"display":""});
			$("#sb2").css({"display":""});	
			$("#sb3").css({"display":"none"});
			$("#sb4").css({"display":"none"});
			$("#sb5").css({"display":"none"});
			$("#sb6").css({"display":"none"});
			var formInput1="reporttype=APPLICATION-BANKLIST";
			$.ajax({
				
			     url : '<%=request.getContextPath()%>'+'/<%=appName %>/fetchdata.action',
			     type: "POST",
			     data : formInput1,
			     success: function(data, textStatus, jqXHR)
			     {
						
						console.log("data "+JSON.stringify(data.rptresponseJSON));
						
						var userlist = data.responseJSON.ACCOUNTNO;
						
						//alert("userlist ["+userlist+"]");
						
						$('#servicedt3').prop('selectedIndex',0);
						$('#servicedt3').trigger("liszt:updated");
						
						var i;
						for(i=0;i<userlist.length;i++)
						{
							
								$('#servicedt3').append(getOptionFor((userlist[i].accountno).split('-')[1],(userlist[i].accountno).split('-')[0]));
								$('#servicedt3').trigger("liszt:updated");
						
							
						}
			     }
			 });
			
		}
		
		if($("#servicedt1").val()=="Card Transaction"){
			$("#sb5").css({"display":""});
			$("#sb6").css({"display":""});
			$("#sb1").css({"display":"none"});
			$("#sb2").css({"display":"none"});	
			$("#sb3").css({"display":"none"});
			$("#sb4").css({"display":"none"});
			
		}
		
	});
		});

function datefunvalid(v){
	
	$("#dateradio").val(v);
	if(v=="betdate"){
		$("#tabdate").css({"display":""});
	}else{
		$("#tabdate").css({"display":"none"});	
	}
	
}

function fun(v){
	$("#format").val(v);
}


function func(v){
	$("#mainf").css({"display":""});
	$("#st13").css({"display":"none"});
	$("#offrep").css({"display":"none"});
	
	
	if(v=="Offline"){
		
		$("#refnodis").css({"display":"none"});
		$("#refnodis1").css({"display":"none"});
		$("#refnodis2").css({"display":"none"});
		$("#refnodis3").css({"display":"none"});
		$("#tabdate").css({"display":"none"});
		$("#tabdate1").css({"display":"none"});
		
		$("#tab11").css({"display":"none"});
		$("#tab12").css({"display":"none"});
		$("#tab13").css({"display":"none"});
		$("#tab14").css({"display":"none"});
		$("#tab15").css({"display":"none"});
		$("#tab16").css({"display":"none"});
		
		$("#tab1").css({"display":"none"});
		$("#tab2").css({"display":"none"});
		$("#tab3").css({"display":"none"});
		$("#tab4").css({"display":"none"});
		
		$("#st3").css({"display":"none"});
		$("#st4").css({"display":"none"});
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		$("#st13").css({"display":""});
		
		$("#onboard1").css({"display":"none"});
		$("#onboard2").css({"display":"none"});
		$("#mainf").css({"display":"none"});
		$("#offrep").css({"display":""});

		
	}
	
}

function funccc(v){
	$("#offtype").val(v);
}

function funnewold(v){
	$("#newoldreport").val(v);
}



</script>
<s:set value="responseJSON" var="respData" />
</head>


<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#">Reports</a>  </li> 
 					</ul>
				</div>  

 <div id="errormsg" class="errores"></div>
			 <div  class="screenexit"></div>
			
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Reports
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

				</div>
			</div>  
		<div class="box-content">
			<fieldset> 
			<div class="section-inner">
									<div class="segmented-control" style="width: 900px; color: #00ADEF;font-weight: bold;font-size: 14px;">
					                    	<input type="radio" name="formats" id="xlsx"	value="xlsx" onclick="fun(this.value)"  />
					                    	<input type="radio" name="formats" id="csv"	value="csv" onclick="fun(this.value)" />
					                    	<input type="radio" name="formats" id="pdf"	value="pdf" onclick="fun(this.value)" />
<!-- 					                    	<input type="radio" name="formats" id="txt"	value="txt" onclick="fun(this.value)" />
 -->					                     	<label for="xlsx" data-value="xlsx" ><img src='${pageContext.request.contextPath}/images/xlsx.jpg' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">xlsx</span></img></label>
                    						<label for="csv" data-value="csv"  ><img src='${pageContext.request.contextPath}/images/csv.jpg' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">csv</span></img></label>
<%--                     						<label for="pdf" data-value="pdf"  ><img src='${pageContext.request.contextPath}/images/pdf.jpg' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">pdf</span></img></label>
 --%><%--                     						<label for="txt" data-value="txt"  ><img src='${pageContext.request.contextPath}/images/txt.jpg' align='left' style='width: 40px; height: 40px;'><span style="color: #000000;font-weight: bold;font-size: 12px;">txt</span></img></label>
 --%>					                  </div>
									</div>
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
				<tr class="even"  >
					<td width="20%"><label><strong>Reports Type</strong><font color="red">*</font></label></td>
					<td width="30%"> 
						<input type="radio" name="reportname" id="Instant"	value="Instant"  onclick=func("Instant") checked/><strong>Instant </strong> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					<%-- 	<input type="radio" name="reportname" id="Requested"	value="Requested" onclick=func("Requested") /><strong>Requested </strong> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						<input type="radio" name="reportname" id="Offline"	value="Offline" onclick=func("Offline") /> <strong> Offline </strong>
						 --%>
					</td>
					<td width="20%">
					
					</td>
					<td width="30%">
					
					</td>
					
				</tr> 
				<tr class="even" >
					<td width="20%" ><label for="Report"><strong>Reports</strong> <font color="red">*</font></label></td>
					<td width="30%" id="mainf" >
						
						
						<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.REPORT_DETAILS"
															name="Report" id="Report" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
						
					</td>
					<td width="20%" id="st13" style="display:none">
					
					</td>
					<td width="20%" id="st1"></td>
					<td width="30%" id="st2"></td> 
					
					<td id="st3" style="display:none"><strong><label for="status"><strong>Status</strong><font color="red">*</font></label></strong></td>
														<td id="st4" style="display:none">
															
														<select id="status" name="status" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="ALL">ALL</option>
																<option value="SUCCESS">SUCCESS</option>
																<option value="FAIL">FAIL</option>
															</select>
															
														</td>
				
				
				</tr> 
				<tr class="odd" id="newold" style="display:none" >
				<td><strong><span id="newoldreport1"></span> Information</strong><font color="red">*</font></td>
				<td><input type="radio" name="newoldreport2" id="new"	value="new"   onclick=funnewold(this.id) checked/> <strong> New</strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
				<input type="radio" name="newoldreport2" id="old"	value="old" onclick=funnewold(this.id) /> <strong> Old</strong>
				<input type="hidden"  id="newoldreport" name="newoldreport" value="new" /> 
				</td>
				<td></td>
				<td></td>
				</tr>
				<tr  class="odd" id="tabdate1" style="display:none">
				<td><input type="radio" name="datevalid" id="betdate" onclick="datefunvalid(this.id)">&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp<strong>Dates between</strong></td>
				<td></td>
				<td></td>
				<td></td>
				</tr>
				<tr class="even" id="tabdate" style="display:none" >
					<td ><label for="Date"><strong>From Date</strong><font color="red">*</font></label></td>
					<td > 
						<input type="text" maxlength="10"  class="fromDate" id="fromdate" name="fromdate" required=true  />  							
					</td>
					<td ><label for="Date"><strong>To Date</strong><font color="red">*</font></label></td>
					<td > 
						<input type="text" maxlength="10"  class="fromDate" id="todate" name="todate" required=true  />  							
					</td>
					
				</tr>
				
				<tr  class="odd" id="refnodis2" style="display:none">
				<td><input type="radio" name="datevalid" id="curmnt" onclick="datefunvalid(this.id)" >&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp<strong>Current Month</strong></td>
				<td></td>
				<td></td>
				<td></td>
				</tr>
				<tr  class="odd" id="refnodis3" style="display:none">
				<td><input type="radio" name="datevalid" id="quartly" onclick="datefunvalid(this.id)" > &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp</input><strong>Quarterly</strong></td>
				<td></td>
				<td></td>
				<td></td>
				</tr> 
				
				<tr  class="odd" id="refnodis" style="display:none">
				<td><strong><label for="Application"><strong>Application</strong><font color="red">*</font></label></strong></td>
														<td>
															
														<select id="application" name="application" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="INTERNAL">DSA</option>
																<option value="EXTERNAL">AGENT</option>
															</select>
															
														</td>
				<td id="tab1" style="display:none" ><strong><label for="User Id"><strong><span id="searchuser"></span></strong></label></strong></td>
														<td id="tab2" style="display:none">
															
														<select id="userid" name="userid" class="chosen-select-width" style="width: 220px;">
																<option value=""></option>
															</select>
															
														</td>
														<td id="tab3" > </td>
														<td id="tab4" > </td>
				
				</tr> 
				
				<tr id="wst3" style="display:none">
				<td  ><strong><label for="status"><strong>Report Type</strong><font color="red">*</font></label></strong></td>
														<td id="wst4" style="display:none">
															
														<select id="report11" name="report11" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="Detailed report">Detailed report</option>
																<option value="Summary">Summary</option> 
															</select>
															
														</td>
														<td id="wsttab1"><strong><label for="status"><strong>Wallet Type</strong><font color="red">*</font></label></strong></td>
														<td id="wsttab2">
														<select id="report12" name="report12" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="WALLET">Customer</option>
																<option value="AGENT">Agent</option>
															</select>
														</td>
														<td id="wsttab3" style="display:none" > </td>
														<td  id="wsttab4" style="display:none" > </td>
				</tr>
				
				<tr  class="odd" id="refnodis1" style="display:none">
				
				<td id="tab11" style="display:none" ><strong><label for="User Id"><strong><span id="searchagentuserid"></span></strong></label></strong></td>
														<td id="tab12" style="display:none" >
															
														<select id="agentuserid" name="agentuserid" class="chosen-select-width" style="width: 220px;">
																<option value=""></option>
															</select>
															
														</td>
														
				<td id="tab13" style="display:none" ><strong><label for="User Id"><strong><span id="search"></span></strong></label></strong></td>
														<td id="tab14" style="display:none">
															
														<select id="searchid" name="searchid" class="chosen-select-width" style="width: 220px;">
																<option value=""></option>
															</select>
															
														</td>
												
														<td id="tab15" style="display:none" > </td>
														<td  id="tab16" style="display:none" > </td>
				
				</tr>
				
				<tr  class="odd" id="onboard1" style="display:none">
				
				<td><strong><label for="User Id"><strong><span id="searchagentuserid">Status</span></strong></label></strong></td>
														<td>
															
														<select id="status1" name="status1" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="A">Active</option>
																<option value="L">Locked</option>
																<option value="N">Partial Registration</option>
															</select>
															
														</td>
														
			
												
														<td></td>
														<td></td>
				
				</tr>
				
				<tr  class="odd" id="onboard2" style="display:none">
				
				<td><strong><label for="User Id"><strong><span id="searchagentuserid">Product</span></strong></label></strong></td>
														<td>
															
														<select id="product1" name="product1" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select>
															
														</td>
														
			
												
														<td></td>
														<td></td>
				
				</tr>
				<tr  class="odd" id="onboard3" style="display:none">
				
														<td><strong><label for="User Id"><strong><span id="searchop"></span></strong></label></strong></td>
														<td><input type="text" id="searchval" name="searchval"/></td>
														<td></td>
														<td></td>
				
				</tr>
				<tr  class="odd" id="onboard4" style="display:none">
				
														<td><strong><label for="User Id"><strong><span id="searchop1"></span></strong></label></strong></td>
														<td><input type="text" id="searchval1" name="searchval1"/></td>
														<td></td>
														<td></td>
				
				</tr>
				<tr  class="odd" id="onboard5" style="display:none">
				
				<td><strong><label for="User Id"><strong><span id="searchagentuserid">Status</span></strong></label></strong></td>
														<td>
															
														<select id="status2" name="status2" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="A">Active</option>
																<option value="D">Deactive</option>
																<option value="R">Retrival</option>
															</select>
															
														</td>
														
			
												
														<td></td>
														<td></td>
				
				</tr>
				
				
				<tr class="even" id="tabbaldate" style="display:none" >
					<td ><label for="Date"><strong>Balance Date</strong><font color="red">*</font></label></td>
					<td > 
						<input type="text" maxlength="10"  class="fromDate" id="balancedt" name="balancedt" required=true  />  							
					</td>
					<td ></td>
					<td ></td>
					
				</tr>
				
				
				<tr  class="odd" id="servicetr" style="display:none">
				
				<td><label for="User Id"><strong><span >Service Types</span></strong><font color="red">*</font></label></td>
														
				<td><select   class="chosen-select-width" style="width: 220px;"  name="servicedt1" id="servicedt1" >
																<option value="">Select</option>
																<option value="Agent Fund">Agent Fund</option>
																<option value="Fund Transafer Other Bank">Fund Transafer Other Bank</option>
																<option value="Paybill">Paybill</option>
																<option value="Recharge">Recharge</option>
																<option value="Fund Transfer Own Bank">Fund Transfer Own Bank</option>
																<option value="Card Transaction">Card Transaction</option>
																
															</select>
															
														
				
				</td>
				<td id="sb1" style="display:none"><label for="User Id"><strong>Bank Name</strong></label></td>
				<td id="sb2" style="display:none">
															
														   <select id="servicedt3" name="servicedt3" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select>
															
														</td>
														
			<td id="sb5" style="display:none"><label for="User Id"><strong>Bank Type</strong></label></td>
				<td id="sb6" style="display:none">
															
														   <select id="servicedt4" name="servicedt4" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="Own Bank">Own Bank</option>
																<option value="Other Bank">Other Bank</option>
															</select>
															
														</td>
												
														<td id="sb3"></td>
														<td id="sb4"></td>
				
				</tr>
				<tr  class="odd" id="servicetr1" style="display:none">
				<td ><label for="User Id"><strong>Channel</strong></label></td>
				<td >
															
														   <select id="servicedt5" name="servicedt5" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																
															</select>
															
														</td>
												
														<td id="sb3"></td>
														<td id="sb4"></td>
				</tr>
				
				
				
		 </table>
		 
		
		 
		 <input type="hidden" id="dateradio" name="dateradio" />
		</fieldset> 
		 <div class="maxl" id="offrep" style="display:none">
					<label class="radio inline" onclick=funccc("D1")> 
				      <input type="radio" name="last" id="lastday"	value="D1" checked />
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><strong> Yesterday </strong></span> 
				   </label>
				   <br></br>
				   <label class="radio inline" onclick=funccc("D7")> 
				      <input type="radio" name="last" id="lastsevendays"	value="D7" />
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><strong> Last 7 Days </strong></span> 
				   </label>
				    <br></br>
				   <label class="radio inline" onclick=funccc("M1")> 
				      <input type="radio" name="last" id="lastmonth"	value="M1" />
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><strong> Last Month </strong></span> 
				   </label>
				    <br></br>
				   <label class="radio inline" onclick=funccc("M3")> 
				      <input type="radio" name="last" id="lastthreemonth"	value="M3" />
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><strong> Last 3 Months </strong></span> 
				   </label>
					
						
					
			</div>
	 	  </div> 
	  </div>
	  </div> 
	  
	  <input type="hidden" id="jrxmlname" name="jrxmlname" />
	  <input type="hidden" id="query" name="query"  />
	  <input type="hidden" id="fieldsval" name="fieldsval"  value=""/>
	   <input type="hidden" id="offtype" name="offtype"  value="D1"/>
	 <input type="hidden" id="searchby" name="searchby"  />
	  <input type="hidden" id="format" name="format"  value="xlsx"/>
	  
		<div class="form-actions">
				<input type="button"  class="btn btn-success"  name="save" id="save" value="Get Report" onClick="queryUser()"></input>				 
		</div> 
		
		
	
	<div align="center" style="display:none" id="ps">
	Please Wait Generating  ...
	<div><img src='${pageContext.request.contextPath}/images/ajax-loader.gif' alt='xlsx' width='170' height='120' ></img></div> 
	</div> 	
</div> 		
	
 </form>
</body> 
</html>