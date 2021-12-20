 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CEVA </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
	
	
<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
 
</style>    
<script type="text/javascript" >



var createProductRules = {
		   rules : {
			   remarks     : { required : true},
			   actiontype     : { required : true}
				
		   },  
		   messages : {
			   remarks : { 
		         required : "Please Enter Remarks "
		         },
		        actiontype : { 
			         required : "Please Select Action Type "
			     }  
		    } 
		 };


	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 
	

$(document).ready(function() {
	$('#btn-submit').live('click', function () { 
		$("#form1").validate(createProductRules);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequestinfoconfirm.action';
	$("#form1").submit();
	return true;
	});
});



		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequest.action';
	$("#form1").submit();
	return true;
}


$(function(){
	
	//var actiondet = ["Bank Posting Reversal","Wallet Reversal","Complete Reversal"];
	var actiondet = ["Wallet Reversal"];
	var option = '';
	for (var i=0;i<actiondet.length;i++){
	   option += '<option value="'+ actiondet[i] + '">' + actiondet[i] + '</option>';
	}
	
	$('#actiontype').append(option);
	$('#actiontype').trigger("liszt:updated");
	
	if($('#application').val()=="SUCCESS_REV"){
		
	
	
	
	if("${responseJSON.VIEW_LMT_DATA.WALLET_REV}"=="YES"){
		
		document.getElementById("actiontype").length=1;
		$('#actiontype').trigger("liszt:updated");
		
		
		var actiondet = ["Bank Posting Reversal"];
		var option = '';
		for (var i=0;i<actiondet.length;i++){
		   option += '<option value="'+ actiondet[i] + '">' + actiondet[i] + '</option>';
		}
		
		$('#actiontype').append(option);
		$('#actiontype').trigger("liszt:updated");
		
	}
	
	if("${responseJSON.VIEW_LMT_DATA.POST_REV}"=="YES"){
		
		document.getElementById("actiontype").length=1;
		$('#actiontype').trigger("liszt:updated");
		
		var actiondet = ["Wallet Reversal"];
		var option = '';
		for (var i=0;i<actiondet.length;i++){
		   option += '<option value="'+ actiondet[i] + '">' + actiondet[i] + '</option>';
		}
		
		$('#actiontype').append(option);
		$('#actiontype').trigger("liszt:updated");
		
	}
	
	if("${responseJSON.VIEW_LMT_DATA.WALLET_REV}"=="YES" && "${responseJSON.VIEW_LMT_DATA.POST_REV}"=="YES"){
		document.getElementById("actiontype").length=1;
		$('#actiontype').trigger("liszt:updated");
	}
		
	}
	
	
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

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="#">Wallet Transaction Reversal Request</a></li>
				</ul>
			</div>
			
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Wallet Transaction Information
					<div class="box-icon"> 
						<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> 
					</div>
				</div> 
				
				
				
				
				<div class="box-content"> 
				
				
				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
					  <tr >
							<td width="20%" ><label for="Customer ID"><strong>Payment Reference Number</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.PAYMENTREFERENCE}
							<input type="hidden" name="paymentrefno"  id="paymentrefno" value="${responseJSON.VIEW_LMT_DATA.PAYMENTREFERENCE}" /></td>
							
							<td><label for="Account Number"><strong>User Id</strong></label></td>
							<td>${responseJSON.VIEW_LMT_DATA.USERID}
							<input type="hidden" name="userid"  id="userid" value="${responseJSON.VIEW_LMT_DATA.USERID}" /></td>
							
							
							
						</tr>
						<tr >
							<td width="20%" ><label for="Customer Name"><strong>Amount</strong></label></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.TRNS_AMT}
							<input type="hidden" name="txnamt"  id="txnamt" value="${responseJSON.VIEW_LMT_DATA.TRNS_AMT}" /></td>
							<td><label for="Alias Name"><strong>Channel</strong></label></td>
							<td> ${responseJSON.VIEW_LMT_DATA.CHANNEL}
							<input type="hidden" name="channel"  id="channel" value="${responseJSON.VIEW_LMT_DATA.CHANNEL}" /></td>
							
						</tr>
						<tr >
							<td><label for="Account Type"><strong>Wallet Account Number</strong></label></td>
							<td>${responseJSON.VIEW_LMT_DATA.FROM_ACCOUNT}
							<input type="hidden" name="frmacc"  id="frmacc" value="${responseJSON.VIEW_LMT_DATA.FROM_ACCOUNT}" /></td>
							
							<td><label for="Date Created"><strong>Wallet Reference Number</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.TO_ACCOUNT}
								<input type="hidden" name="toacct"  id="toacct" value="${responseJSON.VIEW_LMT_DATA.TO_ACCOUNT}" /></td>
								
						</tr>

						 <tr >
						 
						 <td><label for="Institute"><strong>Transaction Date and Time</strong></label></td>
							<td>${responseJSON.VIEW_LMT_DATA.TRANS_DATE}
							<input type="hidden" name="txndatetime"  id="txndatetime" value="${responseJSON.VIEW_LMT_DATA.TRANS_DATE}" /></td>
							
								
								<td><label for="branchcode"><strong>Transaction Amount</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.TXN_AMT}
								<input type="hidden" name="txnsamt"  id="txnsamt" value="${responseJSON.VIEW_LMT_DATA.TXN_AMT}" /></td>
						 </tr>
						  <tr >
								<td><label for="Date Created"><strong>Transaction Fee</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.FEE_AMT}
								<input type="hidden" name="txnfee"  id="txnfee" value="${responseJSON.VIEW_LMT_DATA.FEE_AMT}" /></td>
								<td><label for="Date Created"><strong>Wallet Status</strong></label></td>
								<td><strong><font color="red">${responseJSON.VIEW_LMT_DATA.WAL_RESP}</font></strong>
								</td>
						 </tr>
						 
						 <tr >
								<td><label for="Date Created"><strong>${responseJSON.VIEW_LMT_DATA.BEN_NAME1}</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.BEN_NAME}
								<input type="hidden" name="bacname"  id="bacname" value="${responseJSON.VIEW_LMT_DATA.BEN_NAME}" /></td>
								<td><label for="Date Created"><strong>${responseJSON.VIEW_LMT_DATA.BEN_ACCT1}</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.BEN_ACCT}
								<input type="hidden" name="baccnum"  id="baccnum" value="${responseJSON.VIEW_LMT_DATA.BEN_ACCT}" /></td>
						 </tr>
						  <tr >
								<td><label for="Date Created"><strong>${responseJSON.VIEW_LMT_DATA.BANK_NAME1}</strong></label></td>
								<td>${responseJSON.VIEW_LMT_DATA.BANK_NAME}
								<input type="hidden" name="bacname"  id="bbankname" value="${responseJSON.VIEW_LMT_DATA.BANK_NAME}" /></td>
								<td><label for="Date Created"><strong>${responseJSON.VIEW_LMT_DATA.THIRDPT}</strong></label></td>
								<td><strong><font color="red">${responseJSON.VIEW_LMT_DATA.FTO_RESP_CODE}</font></strong>
								<input type="hidden" name="ftoresp"  id="ftoresp" value="${responseJSON.VIEW_LMT_DATA.FTO_RESP_CODE}" />
								</td>
						 </tr>
						 
						 <tr >
								<td><label for="Date Created"><strong>Wallet Reversal Status</strong></label></td>
								<td><strong><font color="red">${responseJSON.VIEW_LMT_DATA.WALLET_REV}</font></strong>
								</td>
								<td><label for="Date Created"><strong>Bank Posting Reversal Status</strong></label></td>
								<td><strong><font color="red">${responseJSON.VIEW_LMT_DATA.POST_REV}</font></strong>
								
								</td>
						 </tr>
						 
						  <tr >
								<td><label for="Date Created"><strong>Action Type<font color="red">*</font></strong></label></td>
								<td>
								<%-- <s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'Bank Posting Reversal':'Bank Posting Reversal','Wallet Reversal':'Wallet Reversal','Complete Reversal':'Complete Reversal'}" 
							         name="actiontype" 
							         id="actiontype"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
							           /> --%>
							           
							           
							           <select id="actiontype" name="actiontype" data-placeholder="Choose User Level..."
																		class="chosen-select" style="width: 220px;" required=true  >
															<option value="">Select</option>
															
							
														</select>
								</td>
								<td><strong>Transaction Type</strong></td>
								<td>${responseJSON.requesttype}</td>
						 </tr>
						 

						 <tr >
								<td><label for="Date Created"><strong>Remarks<font color="red">*</font></strong></label></td>
								<td>
								<textarea rows="10" cols="5" name="remarks"  id="remarks"></textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						
				</table>
				
				<input type="hidden" name="fulldata"  id="fulldata" value="${responseJSON.VIEW_LMT_DATA.FULLDET}" />
				
					
						
				</div>
			</div>
		</div> 
		<input type="hidden" id="txntype" name="txntype" value="${responseJSON.VIEW_LMT_DATA.SERVICECODE}" />
		<input type="hidden" id="requesttype" name="requesttype" value="${responseJSON.requesttype}" />
	    <input type="hidden" id="application" name="application"  value="${responseJSON.application}"/>
	
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Back" />
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" width="100"  >
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 