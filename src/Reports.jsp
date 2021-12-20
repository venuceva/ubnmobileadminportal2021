<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 <link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css">


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
					QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','N','Paritial Registion','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,MCF.AUTH_ID as AUTHID,decode(MCM.STATUS,'A','Using Mobile and USSD','U','Using Only USSD','N','Paritial Registion on USSD - Transaction pin not created','L','User Locked') as DETAILS,trunc(MCM.DATE_CREATED) as tdate  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD where MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P'";
					
					
					
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
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'mm')=to_char(sysdate,'mm')";
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
					
					if($('#status11').val()!=""){
						if($('#status11').val()=="A"){
							QUERY=QUERY+" AND MCM.STATUS in ('A','U')";
							SEARCHBYDT=SEARCHBYDT+"And Status : Active";
						}
						if($('#status11').val()=="L"){
							QUERY=QUERY+" AND MCM.STATUS in ('L')";
							SEARCHBYDT=SEARCHBYDT+"And Status : Active";
						}
						if($('#status11').val()=="N"){
							QUERY=QUERY+" AND MCM.STATUS in ('N')";
							SEARCHBYDT=SEARCHBYDT+"And Status : Active";
						}
						
					}
					
					
					if($('#Product1').val()!=""){
							QUERY=QUERY+" AND MCM.M_PRD_CODE=UPPER('"+$("#Product1").val()+"')";
							SEARCHBYDT=SEARCHBYDT+"And Product : "+$("#Product1").val();
						
						
					}
					
					QUERY=QUERY+" ORDER BY tdate DESC";
					$("#query").val(QUERY);
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
						QUERY=QUERY+" AND to_char(MCM.DATE_CREATED,'mm')=to_char(sysdate,'mm')";
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
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
		
				
				if($("#Report").val()=="RM_Report"){
					SEARCHBYDT="Search By  ";
					QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','U','Active','N','Paritial Registion','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,RM.RMCODE,RM.MAKER_DT,MCM.AUTHID  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD,RMCODE_TBL RM where MCM.CUSTOMER_CODE=RM.CUSTOMER AND MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P' ";
					
					
					
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
						QUERY=QUERY+" AND to_char(RM.MAKER_DT,'mm')=to_char(sysdate,'mm')";
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
					
					QUERY=QUERY+" ORDER BY RM.RMCODE ";
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
						QUERY=QUERY+" WHERE to_char(MAKER_DT,'mm')=to_char(sysdate,'mm')";
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
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
				
				if($("#Report").val()=="Product_List_Report"){
					QUERY="SELECT PRD_CODE,PRD_NAME,PRD_CCY,APPLICATION from PRODUCT";
					SEARCHBYDT="Search By  List of Product";
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
					QUERY="SELECT  NVL(A.CHANNEL,'') CHANNEL,  NVL(A.NET_ID,' ') MAKERID,  TO_CHAR(A.TXNDATE, 'dd-mm-yyyy hh:mi:ss') DTTM,  NVL(A.TRANSCODE_DESC,' ') TRANSCODE,  NVL(A.IP,' ') IP,  NVL(A.MESSAGE,' ') MESSAGE FROM AUDIT_DATA A, CEVA_MENU_LIST CML  WHERE CML.MENU_ACTION=A.TRANSCODE ";
					SEARCHBYDT="Search By  ";
					
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
				
				
				
				if($("#Report").val()=="Users_Groups_Report"){
					QUERY="select GROUP_ID,GROUP_NAME,APPL_CODE,GROUP_TYPE,USER_LEVEL,MAKER_ID,MAKER_DTTM from USER_GROUPS";
					$("#query").val(QUERY);
				}
				
				if($("#Report").val()=="Users_Management_Report"){
					QUERY="SELECT ULC.LOGIN_USER_ID,UI.USER_NAME,UI.EMAIL,UI.MOBILE_NO,UI.USER_GROUPS,DECODE(UI.USER_STATUS,'A','Active','B','Blocked','L','Locked',USER_STATUS)USER_STATUS,DECODE(UI.USER_TYPE,'HOD','Head Of Department','JO','Junior Officer','MA','Merchant Admin','MU','Merchant User','MS','Merchant Supervisor','SM','Store Manager',USER_TYPE) USER_TYPE,ULC.MAKER_ID,ULC.MAKER_DTTM,ULC.APPL_CODE,ULC.AUTHID,ULC.AUTHDTTM,ULC.STATUS,To_Date(To_Char(ULC.LAST_LOGED_IN, 'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS') LAST_LOGED_IN FROM USER_LOGIN_CREDENTIALS ULC,USER_INFORMATION UI WHERE UI.COMMON_ID=ULC.COMMON_ID";
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
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
						QUERY=QUERY+" WHERE to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					if($("#status").val()=="SUCCESS"){					
					QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where RESPONSECODE='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
					QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where RESPONSECODE!='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else{
					QUERY="select ACCOUNTNO,TRNS_AMT,TRANS_TYPE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID,to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,RESPONSEMESSAGE,DEBITNARRATION,DEBITACCCOUNTNUMBER,CREDITACCCOUNTNUMBER,DECODE(RESPONSECODE,'00','Success','Failed') as status,NVL((SELECT BANK_NAME from BANKS_DATA where NIBSSCODE=BEN_PAYBILL_CODE),' ') as Ben_bank,NVL(BEN_CUST_NAME,' ') as Ben_name,NVL(BEN_PAYBILL_ACTNO,' ') as ben_acc,TRUNC(TRANS_DATE) as tdate,NVL(SUBSTR(SERVICE_ID,0,INSTR(SERVICE_ID,'|')-1),' ') as cltxnref,NVL(SUBSTR(SERVICE_ID,INSTR(SERVICE_ID,'|')+1),' ') as raastxnref from FUND_TRANSFER_TBL where (RESPONSECODE!='00' OR RESPONSECODE='00') ";
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
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					
					QUERY=QUERY+" ORDER BY tdate,USERID desc";
					//console.log(QUERY);
					
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
					
					$("#query").val(QUERY);
				}
				
				
				if($("#Report").val()=="Super_Agenct_Wise_Transaction_Report"){
					QUERY="select ACCOUNTNO,CREDITAMOUNT,BRANCHCODE,decode(CRDR_FLAG,'C','CREDIT','D','DEBIT') as CREDITCREDITINDICATOR,CREDITPAYMENTREFERENCE,BATCHID,CHANNEL,USERID from FUND_TRANSFER_TBL where RESPONSECODE='00'";
					$("#query").val(QUERY);
				}
				
				
			if($("#Report").val()=="All_Commission_Report"){
				SEARCHBYDT="Search By  ";
				/* if($("#status").val()=="SUCCESS"){		
				QUERY="select CREDITACCCOUNTNUMBER,CREDITAMOUNT,NVL(FEE,'0'),NVL(AGENT_COMM,'0'),NVL(CEVA_COMM,'0'),NVL(BANK_COMM,'0'),NVL(SUPER_AGENT_COMM,'0'),NVL(VAT_COMM,'0'),to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where RESPONSECODE='00'";
				SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
				}else if($("#status").val()=="FAIL"){
					QUERY="select CREDITACCCOUNTNUMBER,CREDITAMOUNT,NVL(FEE,'0'),NVL(AGENT_COMM,'0'),NVL(CEVA_COMM,'0'),NVL(BANK_COMM,'0'),NVL(SUPER_AGENT_COMM,'0'),NVL(VAT_COMM,'0'),to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where RESPONSECODE!='00'";
					SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";	
				}else{
					QUERY="select CREDITACCCOUNTNUMBER,CREDITAMOUNT,NVL(FEE,'0'),NVL(AGENT_COMM,'0'),NVL(CEVA_COMM,'0'),NVL(BANK_COMM,'0'),NVL(SUPER_AGENT_COMM,'0'),NVL(VAT_COMM,'0'),to_char(TRANS_DATE,'dd-mm-yyyy hh:mi:ss') as TRANS_DATE,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where (RESPONSECODE!='00' OR RESPONSECODE='00') ";
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
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					}  */
					
					
					//if($('#searchid').val()!=""){
						if($("#status").val()=="SUCCESS"){	
							
						 QUERY="select FROM_ACCOUNT,AMOUNT,NVL(FEE,'0'),NVL(AGENTCOMM,'0'),NVL(CEVACOMM,'0'),NVL(BANKCOMM,'0'),NVL(VATAMT,'0'),TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY') as TRANS_DATE,CHANNEL,'Fund Transfer to Other Banks' as trans_type from fto_transactions_tbl where FTO_RESP_CODE='00'";
							if($("#dateradio").val()=="betdate"){
								QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
								SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
							}else if($("#dateradio").val()=="curmnt"){
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
									QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
				
			
			if($("#Report").val()=="All_Commission_summary_Open"){
				SEARCHBYDT="Search By  ";
				/* if($("#status").val()=="SUCCESS"){		
				QUERY="select sum(CREDITAMOUNT) as CREDITAMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENT_COMM,'0')) as AGENT_COMM,sum(NVL(CEVA_COMM,'0')) as CEVA_COMM,sum(NVL(BANK_COMM,'0')) as BANK_COMM,sum(NVL(SUPER_AGENT_COMM,'0')) as SUPER_AGENT_COMM,sum(NVL(VAT_COMM,'0')) as VAT_COMM,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where RESPONSECODE='00' ";
				SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
				}else if($("#status").val()=="FAIL"){
					QUERY="select sum(CREDITAMOUNT) as CREDITAMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENT_COMM,'0')) as AGENT_COMM,sum(NVL(CEVA_COMM,'0')) as CEVA_COMM,sum(NVL(BANK_COMM,'0')) as BANK_COMM,sum(NVL(SUPER_AGENT_COMM,'0')) as SUPER_AGENT_COMM,sum(NVL(VAT_COMM,'0')) as VAT_COMM,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where RESPONSECODE!='00' ";
					SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";	
				}else{
					QUERY="select sum(CREDITAMOUNT) as CREDITAMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENT_COMM,'0')) as AGENT_COMM,sum(NVL(CEVA_COMM,'0')) as CEVA_COMM,sum(NVL(BANK_COMM,'0')) as BANK_COMM,sum(NVL(SUPER_AGENT_COMM,'0')) as SUPER_AGENT_COMM,sum(NVL(VAT_COMM,'0')) as VAT_COMM,TRANS_TYPE,CHANNEL from FUND_TRANSFER_TBL where (RESPONSECODE!='00' OR RESPONSECODE='00') ";
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
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					QUERY=QUERY+" group by TRANS_TYPE,CHANNEL";
					//console.log(QUERY);
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT); */
					
					
				if($("#status").val()=="SUCCESS"){	
					
					 QUERY="select sum(AMOUNT) as AMOUNT ,sum(NVL(FEE,'0')) as FEE,sum(NVL(AGENTCOMM,'0')) as AGENT_COMM,sum(NVL(CEVACOMM,'0')) as CEVA_COMM,sum(NVL(BANKCOMM,'0')) as BANK_COMM,sum(NVL(VATAMT,'0')) as VAT_COMM,'Fund Transfer to Other Banks' as TRANS_TYPE,CHANNEL from fto_transactions_tbl where FTO_RESP_CODE='00'";
						if($("#dateradio").val()=="betdate"){
							QUERY=QUERY+" AND TRUNC(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY')) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy')";
							SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
						}else if($("#dateradio").val()=="curmnt"){
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
							QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
								QUERY=QUERY+" AND to_char(TO_DATE(TO_CHAR(TRANS_DATE_TIME, 'dd/MM/yyyy'),'DD-MM-YYYY'),'mm')=to_char(sysdate,'mm')";
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
				
				$("#query").val(QUERY);
				$("#searchby").val(SEARCHBYDT);
					
				}
		
		
			if($("#Report").val()=="debit_card_validation_report"){
				SEARCHBYDT="Search By  ";
					
						QUERY="select MCM.CUSTOMER_CODE as CUSTOMER_CODE,MCM.FIRST_NAME as FIRST_NAME,NVL(MCM.USER_ID,' ') as USER_ID,MCF.MOBILE_NUMBER as MOBILE_NUMBER,NVL(MCM.EMAILID,' ') as EMAILID,decode(MCM.STATUS,'A','Active','deactive') as STATUS,MCM.DATE_CREATED as DATE_CREATED,MCM.M_PRD_CODE as PRD_CODE,MAD.ACCT_NO as ACC_NO,MAD.BRANCH_CODE as Branch,MCF.AUTH_ID,RM.TRANS_DTTM as TRANS_DTTM,RM.CHANNEL as DCHANNEL  from MOB_CUSTOMER_MASTER MCM,MOB_CONTACT_INFO MCF,MOB_ACCT_DATA MAD,DEBIT_CARD_INFO_TBL RM where MCM.CUSTOMER_CODE=RM.CUSTID AND MCM.ID=MCF.CUST_ID and MCF.CUST_ID=MAD.CUST_ID and MCF.APP_TYPE='MOBILE' and MAD.PRIM_FLAG='P' AND RM.CUSTID is not null AND RM.CHANNEL is not null ";
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
						QUERY=QUERY+" AND to_char(RM.TRANS_DTTM,'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="cardless_withdrawal"){
				SEARCHBYDT="Search By  ";
				
				if($("#status").val()=="SUCCESS"){					
							QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status from CARDLESS_DATA WHERE CARDLESSRESPCODE='00' ";
							SEARCHBYDT=SEARCHBYDT+"Status : SUCCESS ";
					}else if($("#status").val()=="FAIL"){
							QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status from CARDLESS_DATA WHERE CARDLESSRESPCODE!='00'";
							SEARCHBYDT=SEARCHBYDT+"Status : FAIL ";
					}else if($("#status").val()=="ALL"){
						QUERY="select ACCOUNTNO as accno,MOBILENO as mobno,NVL(BENMOBNO,' ') as benmobno,AMOUNT as amount,CHANNEL as channel,CARDLESSRESPMSG as status from CARDLESS_DATA WHERE (CARDLESSRESPCODE='00' OR CARDLESSRESPCODE!='00') ";
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
						QUERY=QUERY+" AND to_char(TRANSADATE,'mm')=to_char(sysdate,'mm')";
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
						QUERY=QUERY+" AND to_char(TRANSADATE,'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="ErrorReport"){
				SEARCHBYDT="Search By  ";
					
						QUERY="select to_char(TRANS_DATE,'dd-mm-yyyy') as tdt,channel,TRANS_TYPE,RESPONSECODE,RESPONSEMESSAGE,count(*) from FUND_TRANSFER_TBL WHERE (RESPONSECODE='00' OR RESPONSECODE!='00') ";
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
						QUERY=QUERY+" AND TRUNC(TRANS_DATE) between to_date('"+$("#fromdate").val()+"','dd/mm/yyyy') and to_date('"+$("#todate").val()+"','dd/mm/yyyy') ";
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY=QUERY+" AND to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			if($("#Report").val()=="MTNFeeReports"){
				SEARCHBYDT="Search By  ";
					
						//SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
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
						QUERY1="  to_char(TRANS_DTTM,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select to_char(TRANS_DTTM,'dd-mm-yyyy') as tdate,'Success' as succ,SERVICECODE,nvl(sum(case when respcode = '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_HIST   WHERE "+QUERY1+" AND otype='OPID4' group by to_char(TRANS_DTTM,'dd-mm-yyyy'),'Success',SERVICECODE )" +
					"union all "+
					"(select to_char(TRANS_DTTM,'dd-mm-yyyy') as tdate,'Failed' as succ,SERVICECODE, nvl(sum(case when respcode != '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_BLK   WHERE "+QUERY1+" AND otype='OPID4' group by to_char(TRANS_DTTM,'dd-mm-yyyy'),'Failed',SERVICECODE ) order by 1,2,3"
					
				//alert(QUERY);
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			
			if($("#Report").val()=="MTNFeeTotalReport"){
				SEARCHBYDT="Search By  ";
						//SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
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
						QUERY1="  to_char(TRANS_DTTM,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DTTM,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="(select 'Success' as succ,SERVICECODE,nvl(sum(case when respcode = '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_HIST   WHERE "+QUERY1+" AND otype='OPID4' group by 'Success',SERVICECODE )"+
					" union all "+
					" (select 'Failed' as succ,SERVICECODE, nvl(sum(case when respcode != '00' then 1 end),0) as CNT,sum(FEE) fe from CONDITIONAL_DEBIT_TBL_BLK   WHERE "+QUERY1+"  AND otype='OPID4' group by 'Failed',SERVICECODE ) order by 1,2";
					
					
				//alert(QUERY);
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="ReversalTimeReport"){
				SEARCHBYDT="Search By  ";
					
						//SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
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
						QUERY1="  to_char(f2.TRANS_DATE,'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			
			
			if($("#Report").val()=="ReversalSummary"){
				SEARCHBYDT="Search By  ";
					
						//SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
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
						QUERY2="  to_char(RRT.TRANS_DTTM,'mm')=to_char(sysdate,'mm')";
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
			if($("#Report").val()=="EstimateTimeReport"){
				SEARCHBYDT="Search By  ";
					
						//SEARCHBYDT=SEARCHBYDT+"Status : ALL ";
					
					
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
						SEARCHBYDT=SEARCHBYDT+"And Dates between : From Date "+$("#fromdate").val()+" To Date "+$("#todate").val()+" ";
					}else if($("#dateradio").val()=="curmnt"){
						QUERY1="  to_char(TRANS_DATE,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY1=" to_char(TRANS_DATE,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					QUERY="select BATCHID as batchid,PAYMENTREFERENCE as paymentref,SERVICE_ID as serviceid,ACCOUNTNO as acc_no,CHANNEL as channel,TRANS_TYPE as transtype,TXN_STIME as stime,TXN_ETIME as etime,TRUNUPTIME as trunuptime from fund_transfer_tbl where "+QUERY1+" AND TXN_STIME is not null";
				//alert(QUERY);
					
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
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'mm')=to_char(sysdate,'mm')";
						SEARCHBYDT=SEARCHBYDT+"And Current Month ";
					}else if($("#dateradio").val()=="quartly"){
						QUERY=QUERY+" WHERE to_char(DSA_DATE_TIME,'Q')=to_char(sysdate,'Q')";	
						SEARCHBYDT=SEARCHBYDT+"And Quarterly ";
					}
					
					
					
					QUERY=QUERY+" group by TO_CHAR(DSA_DATE_TIME,'DD-MM-YYYY') order by tdate desc";
					
					//console.log(QUERY);
					
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
					
					$("#query").val(QUERY);
					$("#searchby").val(SEARCHBYDT);
				}
		
			 var selValue = $('input[name=reportname]:checked').val(); 
			//alert(selValue);
			var url="";
				
			if($('input[name=reportname]:checked').val()=="Online"){
				
				$("#ps").css({"display":""}); 
				
					$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/reportconf.action'; 
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


var list = "fromdate,todate".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2020',
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

	

	
	
	
$("#Report").on('change',function(event){
	
	$("#jrxmlname").val($("#Report").val())
	
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
	
	$("#onboardcust1").css({"display":"none"});
	$("#onboardcust2").css({"display":"none"});
	

	if($("#Report").val()=="Account_Open" || $("#Report").val()=="Account_summary_Open"){
		/* $("#tabdate").css({"display":""}); */
		$("#refnodis").css({"display":"none"});
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
		$("#st3").css({"display":""});
		$("#st4").css({"display":""});
		$("#st1").css({"display":"none"});
		$("#st2").css({"display":"none"});
		
		if($("#Report").val()=="Account_summary_Open"){
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
		
		if($("#Report").val()=="Account_summary_Open"){
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
	
			if($("#Report").val()=="Account_summary_Open"){
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
	}else if($("#Report").val()=="Agent_Users" || $("#Report").val()=="Agent_List" || $("#Report").val()=="Store_List" || $("#Report").val()=="Super_Agenct_Wise_Transaction_Report" || $("#Report").val()=="Teller_Reconcile_Report" || $("#Report").val()=="Audit_Trail"  || $("#Report").val()=="Merchanr_Product_Report"  || $("#Report").val()=="Sale_Order_Report"){
		
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
	
	}else if($("#Report").val()=="Fee_Report"  || $("#Report").val()=="Limit_Report" || $("#Report").val()=="commission_Report"){
		
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		$("#st3").css({"display":"none"});
		$("#st4").css({"display":"none"});
		$("#st1").css({"display":""});
		$("#st2").css({"display":""});
		
		
	$('#searchagentuserid').html("Product <font color='red'>*</font>");
		
		$('#agentuserid').empty();
		$('#agentuserid').append(getOptionFor('Select Product',''));
		
		if($("#Report").val()=="Fee_Report" || $("#Report").val()=="commission_Report"){
			var formInput="reporttype=PRODUCT-FEE";
		}else if($("#Report").val()=="Limit_Report"){
			var formInput="reporttype=PRODUCT-LIMIT";
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
		
		if($("#Report").val()=="users_onboard"){
			$("#onboardcust1").css({"display":""});
			$("#onboardcust2").css({"display":""});
			
			
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
					
					$('#Product1').prop('selectedIndex',0);
					$('#Product1').trigger("liszt:updated");
					
					var i;
					
					for(i=0;i<userlist.length;i++)
					{
							$('#Product1').append(getOptionFor(userlist[i].accountno,userlist[i].accountno));
							$('#Product1').trigger("liszt:updated");
					
						
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
		
		
	}else if($("#Report").val()=="Transaction_Report" || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="EstimateTimeReport" || $("#Report").val()=="Transaction_summary_report" || $("#Report").val()=="All_Commission_Report"  || $("#Report").val()=="All_Commission_summary_Open" || $("#Report").val()=="cardless_withdrawal" || $("#Report").val()=="debit_card_validation_report" || $("#Report").val()=="sms_data"  || $("#Report").val()=="ErrorReport"  || $("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports"){
		$("#tabdate1").css({"display":""});
		$("#refnodis2").css({"display":""});
		$("#refnodis3").css({"display":""});
		
		$("#refnodis1").css({"display":""});
		$("#tab11").css({"display":""});
		$("#tab12").css({"display":""});
		$("#tab15").css({"display":""});
		$("#tab16").css({"display":""});
		
		if($("#Report").val()=="debit_card_validation_report" || $("#Report").val()=="sms_data"  || $("#Report").val()=="ErrorReport"  || $("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="EstimateTimeReport" || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary"){
			
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
		
		if($("#Report").val()=="AgentLocatorSummary"  || $("#Report").val()=="AgentLocatorReports" || $("#Report").val()=="MTNFeeReports" || $("#Report").val()=="MTNFeeTotalReport"  || $("#Report").val()=="EstimateTimeReport"  || $("#Report").val()=="ReversalTimeReport" || $("#Report").val()=="ReversalSummary"){
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


function datefunvalid(v){
	
	$("#dateradio").val(v);
	if(v=="betdate"){
		$("#tabdate").css({"display":""});
	}else{
		$("#tabdate").css({"display":"none"});	
	}
	
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
				 <table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
				<tr class="even"  >
					<td ><label><strong>Reports Type</strong><font color="red">*</font></label></td>
					<td > 
						<input type="radio" name="reportname" id="online"	value="Online" checked/>Online  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						<input type="radio" name="reportname" id="offline"	value="Offline" />Offline  
						
					</td>
					<td ></td>
					<td ></td>
					
				</tr> 
				<tr class="even">
					<td width="20%" ><label for="Report"><strong>Reports</strong> <font color="red">*</font></label></td>
					<td width="30%" >
						
						
						<s:select cssClass="chosen-select" headerKey=""
															headerValue="Select" list="#respData.REPORT_DETAILS"
															name="Report" id="Report" requiredLabel="true"
															theme="simple" data-placeholder="Choose Channel"
															required="true" /> 
						
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
				
				
				<tr  class="odd" id="onboardcust1" style="display:none">
				<td><strong><label for="Application"><strong>Status</strong></label></strong></td>
														<td>
															
														<select id="status11" name="status11" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
																<option value="A">Active</option>
																<option value="L">Locked</option>
																<option value="N">Paritial Registion</option>
															</select>
															
														</td>
				
														<td></td>
														<td></td>
				
				</tr> 
				
				<tr  class="odd" id="onboardcust2" style="display:none">
				<td><strong><label for="Application"><strong>Product</strong></label></strong></td>
														<td>
															
														<select id="Product1" name="Product1" class="chosen-select-width" style="width: 220px;">
																<option value="">Select</option>
															</select>
															
														</td>
				
														<td></td>
														<td></td>
				
				</tr> 
				
				
		 </table>
		 
		 <input type="hidden" id="dateradio" name="dateradio" />
		</fieldset> 
	 	  </div> 
	  </div>
	  </div> 
	  
	  <input type="hidden" id="jrxmlname" name="jrxmlname" />
	  <input type="hidden" id="query" name="query"  />
	 <input type="hidden" id="searchby" name="searchby"  />
	  
	  
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