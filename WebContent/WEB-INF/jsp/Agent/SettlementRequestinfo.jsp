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
               requesttype : { required : true}
		   },  
		   messages : {
			   remarks : { 
		         required : "Please Enter Remarks "
		         },
		         requesttype : { 
			         required : "Please Select Request Type "
			         }  
		    } 
		 };


	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
	   }, ""); 
	

$(document).ready(function() {
	
	
	
	$('#${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}').css("display", "");
	
	if("${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}"=="AGENTFUND"){
		$('#tb2').css("display", "");
		if("${responseJSON.VIEW_LMT_DATA.bankecostatus}"=="1"){
			$('#tb21').css("background-color", "green");
			$('#tb21').css("color", "white");
			$('#tb21').css("cursor", "pointer");
			
		}
		if("${responseJSON.VIEW_LMT_DATA.walecostatus}"=="1"){
			$('#tb22').css("background-color", "green");
			$('#tb22').css("color", "white");
			$('#tb22').css("cursor", "pointer");
			
		}
	}else if("${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}"=="AGCASHDEP" || "${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}"=="AGCASHWTHD" || "${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}"=="WALAGNOWNBANK"){
		$('#tb1').css("display", "");
		$('#tb12').css("display", "none");
		if("${responseJSON.VIEW_LMT_DATA.walecostatus}"=="1"){
			$('#tb11').css("background-color", "green");
			$('#tb11').css("color", "white");
			$('#tb11').css("cursor", "pointer");
			
		}
		if("${responseJSON.VIEW_LMT_DATA.tpartystatus}"=="1"){
			$('#tb12').css("background-color", "green");
			$('#tb12').css("color", "white");
			$('#tb12').css("cursor", "pointer");
			
		}
		if("${responseJSON.VIEW_LMT_DATA.bankecostatus}"=="1"){
			$('#tb13').css("background-color", "green");
			$('#tb13').css("color", "white");
			$('#tb13').css("cursor", "pointer");
			
		}
	}else{
		$('#tb1').css("display", "");
		if("${responseJSON.VIEW_LMT_DATA.walecostatus}"=="1"){
			$('#tb11').css("background-color", "green");
			$('#tb11').css("color", "white");
			$('#tb11').css("cursor", "pointer");
			
		}
		if("${responseJSON.VIEW_LMT_DATA.tpartystatus}"=="1"){
			$('#tb12').css("background-color", "green");
			$('#tb12').css("color", "white");
			$('#tb12').css("cursor", "pointer");
			
		}
		if("${responseJSON.VIEW_LMT_DATA.bankecostatus}"=="1"){
			$('#tb13').css("background-color", "green");
			$('#tb13').css("color", "white");
			$('#tb13').css("cursor", "pointer");
			
		}
		
		if("${responseJSON.VIEW_LMT_DATA.tpartystatus}"=="2"){
			$('#tb12').css("background-color", "orange");
			$('#tb12').css("color", "white");
			$('#tb12').css("cursor", "pointer");
			
		}
	}
	
	$('#btn-submit').live('click', function () { 
		$("#form1").validate(createProductRules);
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/settlementrequestinfoconfirm.action';
	$("#form1").submit();
	return true;
	});
});

$(function() {
	$( "#dialog" ).dialog(
			{
			autoOpen: false,
			modal: true,
		    draggable: false,
		    resizable: false,
		    show: 'blind',
		    hide: 'blind',
			width: 700, 
		    height: 650,
		    buttons: {
		        "OK": function() {
		            $(this).dialog("close");
		        }
		    }
			}
		);
	});
	
function funval(v){
	//alert(v);
	var rststus="0";
	var message="";
	if(v=="bankecostatus"){
		rststus="${responseJSON.VIEW_LMT_DATA.bankecostatus}";
		message="Settlement ";
	}
	if(v=="walecostatus"){
		rststus="${responseJSON.VIEW_LMT_DATA.walecostatus}";
		message="Wallet ";
	}
	if(v=="tpartystatus"){
		rststus="${responseJSON.VIEW_LMT_DATA.tpartystatus}";
		message="Third Party ";
	}
	
	if(rststus=="1" || rststus=="2"){
		var queryString="type="+$("#hashdata").val()+"&sid="+v;
		$.getJSON("settlementajx.action", queryString, function(data) {
			var htmlString1="";
			htmlString1 = htmlString1 + "<table class='table table-striped table-bordered bootstrap-datatable datatable'>";
			htmlString1 = htmlString1 +data.region;
			htmlString1 = htmlString1 + "<table>";
			$( "#dialog" ).dialog( "option", "title", message+" Transaction Details ");
			
			$( "#pie" ).html(htmlString1);
		   $("#dialog").dialog("open");
		   
		});
	}
}

		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/Settlementlink.action';
	$("#form1").submit();
	return true;
}


function fun(id){
	//alert(id);
	$("#requesttype").val(id);
}

 
</script> 
		
</head>

<body>
	<form name="form1" id="form1" method="post" action="">	
	 
	<div id="content" class="span10">   
		 
			  <div>
				 <ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					 <li> <a href="#">Unsettlement Details </a>  </li>
				</ul>
			</div>
			
			<div id="dialog" name="dialog" title="View" width="100" style="display:none">
  						<strong><div  id="result"><div id="pie"></div>
  						</div></strong>
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
							<td width="20%" ><strong><label for="Customer ID"><strong>Payment Reference Number</strong></label></strong></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.PAYMENTREFERENCE}
							<input type="hidden" name="paymentrefno"  id="paymentrefno" value="${responseJSON.VIEW_LMT_DATA.PAYMENTREFERENCE}" />
							<td width="20%" ><strong><label for="Customer Name"><strong>Amount</strong></label></strong></td>
							<td width="30%" >${responseJSON.VIEW_LMT_DATA.TRNS_AMT}
							<input type="hidden" name="txnamt"  id="txnamt" value="${responseJSON.VIEW_LMT_DATA.TRNS_AMT}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Number"><strong>User Id</strong></label></strong></td>
							<td>${responseJSON.VIEW_LMT_DATA.USERID}
							<input type="hidden" name="userid"  id="userid" value="${responseJSON.VIEW_LMT_DATA.USERID}" /></td>
							<td><strong><label for="Alias Name"><strong>Channel</strong></label></strong></td>
							<td> ${responseJSON.VIEW_LMT_DATA.CHANNEL}
							<input type="hidden" name="channel"  id="channel" value="${responseJSON.VIEW_LMT_DATA.CHANNEL}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Type"><strong>Transaction Type</strong></label></strong></td>
							<td>${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}
							<input type="hidden" name="txntype"  id="txntype" value="${responseJSON.VIEW_LMT_DATA.TRANS_TYPE}" /></td>
							<td><strong><label for="Institute"><strong>Transaction Date and Time</strong></label></strong></td>
							<td>${responseJSON.VIEW_LMT_DATA.TRANS_DATE}
							<input type="hidden" name="txndatetime"  id="txndatetime" value="${responseJSON.VIEW_LMT_DATA.TRANS_DATE}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Date Created"><strong>Narration</strong></label></strong></td>
								<td>${responseJSON.VIEW_LMT_DATA.DEBITNARRATION}
								<input type="hidden" name="Narration"  id="Narration" value="${responseJSON.VIEW_LMT_DATA.DEBITNARRATION}" /></td>
								<td><strong><label for="branchcode"><strong>Ext Txnref</strong></label></strong></td>
								<td>${responseJSON.VIEW_LMT_DATA.BATCHID}
								<input type="hidden" name="batchid"  id="batchid" value="${responseJSON.VIEW_LMT_DATA.BATCHID}" /></td>
						 </tr>
						 <%--  <tr >
								<td><strong><label for="Date Created">Wallet Account Number</label></strong></td>
								<td>${responseJSON.VIEW_LMT_DATA.ACCT_NO}
								<input type="hidden" name="waccountno"  id="waccountno" value="${responseJSON.VIEW_LMT_DATA.ACCT_NO}" /></td>
								<td></td>
								<td></td>
						 </tr> --%>
						 <tr style="display:none" id="tb1" >
								<td><strong><label for="Date Created"><strong>Status</strong></label></strong></td>
								<td colspan="3">
								<table width="700" >
								<tr>
								<td width="250" id="tb11" style="background-color:red;color:white;" title="walecostatus" onclick="funval(this.title)" >WALLET ECO SYSTEM </td>
								<td width="250" id="tb12" style="background-color:red;color:white;" title="tpartystatus" onclick="funval(this.title)" >THIRD PARTY </td>
								<td width="250" id="tb13" style="background-color:red;color:white;" title="bankecostatus" onclick="funval(this.title)" >SETTLMENT </td>
								</tr>
								</table>
								</td>
								
						 </tr>
						 <tr style="display:none" id="tb2" >
								<td><strong><label for="Date Created"><strong>Status</strong></label></strong></td>
								<td colspan="3">
								<table width="700" >
								<tr>
								<td width="250" id="tb21" style="background-color:red;color:white;" title="bankecostatus" onclick="funval(this.title)">BANK ECO SYSTEM </td>
								<td width="250" id="tb22" style="background-color:red;color:white;" title="walecostatus" onclick="funval(this.title)" >SETTLMENT </td>
								</tr>
								</table>
								</td>
								
						 </tr>
						 <tr >
								<td><strong><label for="Date Created"><strong>Remarks</strong><font color="red">*</font></label></strong></td>
								<td>
								<textarea rows="10" cols="5" name="remarks"  id="remarks"></textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						<tr style="display:none" id="AGENTFUND" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Funding" onclick="fun(this.id)"> <strong>Wallet Funding</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						 <tr style="display:none" id="WALLETOACC" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Reversal" onclick="fun(this.id)"> <strong>Wallet Reversal</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr style="display:none" id="WALAGNOTBANK" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Reversal" onclick="fun(this.id)"> <strong>Wallet Reversal</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr style="display:none" id="AGNPAYBILLAIRTIME" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Reversal" onclick="fun(this.id)"> <strong>Wallet Reversal</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						 <tr style="display:none" id="WALPAYBILLAGN" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Reversal" onclick="fun(this.id)"> <strong>Wallet Reversal</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						 <tr style="display:none" id="WALAGNOWNBANK" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Reversal" onclick="fun(this.id)"> <strong>Wallet Reversal</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						 
						 <tr style="display:none" id="AGNCRDFTXNOWN" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Posting" onclick="fun(this.id)"> <strong>Wallet Posting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="DisCard" onclick="fun(this.id)"> <strong>DisCard</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr style="display:none" id="AGNCRDCSHWTDOTH" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Posting" onclick="fun(this.id)"> <strong>Wallet Posting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="DisCard" onclick="fun(this.id)"> <strong>DisCard</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr style="display:none" id="AGNCRDFTXNOTH" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Posting" onclick="fun(this.id)"> <strong>Wallet Posting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="DisCard" onclick="fun(this.id)"> <strong>DisCard</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr style="display:none" id="AGNCRDCSHWTDOWN" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="Wallet Posting" onclick="fun(this.id)"> <strong>Wallet Posting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<input type="radio" name="request" id="DisCard" onclick="fun(this.id)"> <strong>DisCard</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 
						  <tr style="display:none" id="AGCASHWTHD" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						  <tr style="display:none" id="AGCASHDEP" >
								<td><strong><label for="Date Created"><strong>Request Type</strong><font color="red">*</font></label></strong></td>
								<td>
								<input type="radio" name="request" id="BankPosting" onclick="fun(this.id)"> <strong>BankPosting</strong>
								</td>
								<td></td>
								<td></td>
						 </tr>
						
				</table>
				<input type="hidden" id="requesttype" name="requesttype" />
				
				
					
						
				</div>
			</div>
		</div> 
		
	    <input type="hidden" id="hashdata" name="hashdata" value="${responseJSON.VIEW_LMT_DATA.hashdata}" />
	
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
 