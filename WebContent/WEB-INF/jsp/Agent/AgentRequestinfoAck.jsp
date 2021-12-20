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


$(document).ready(function() {
	$('#btn-submit').live('click', function () { 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentrequest.action';
	$("#form1").submit();
	return true;
	});
});



		 
function redirectAct(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action';
	$("#form1").submit();
	return true;
}




 
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
				<div class="box-header well" data-original-title>Wallet Transaction Reversal Request acknowledgement
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
							<td width="30%" >${responseJSON.paymentrefno}
							<input type="hidden" name="paymentrefno"  id="paymentrefno" value="${responseJSON.paymentrefno}" /></td>
							
							<td><label for="Account Number"><strong>User Id</strong></label></td>
							<td>${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid" value="${responseJSON.userid}" /></td>
							
							
							
						</tr>
						<tr >
							<td width="20%" ><label for="Customer Name"><strong>Amount</strong></label></td>
							<td width="30%" >${responseJSON.txnamt}
							<input type="hidden" name="txnamt"  id="txnamt" value="${responseJSON.txnamt}" /></td>
							<td><label for="Alias Name"><strong>Channel</strong></label></td>
							<td> ${responseJSON.channel}
							<input type="hidden" name="channel"  id="channel" value="${responseJSON.channel}" /></td>
							
						</tr>
						<tr >
							<td><label for="Account Type"><strong>Wallet Account Number</strong></label></td>
							<td>${responseJSON.frmacc}
							<input type="hidden" name="frmacc"  id="frmacc" value="${responseJSON.frmacc}" /></td>
							
							<td><label for="Date Created"><strong>Wallet Reference Number</strong></label></td>
								<td>${responseJSON.toacct}
								<input type="hidden" name="toacct"  id="toacct" value="${responseJSON.toacct}" /></td>
								
						</tr>

						 <tr >
						 
						 <td><label for="Institute"><strong>Transaction Date and Time</strong></label></td>
							<td>${responseJSON.txndatetime}
							<input type="hidden" name="txndatetime"  id="txndatetime" value="${responseJSON.txndatetime}" /></td>
							
								
								<td><label for="branchcode"><strong>Transaction Amount</strong></label></td>
								<td>${responseJSON.txnsamt}
								<input type="hidden" name="txnsamt"  id="txnsamt" value="${responseJSON.txnsamt}" /></td>
						 </tr>
						  <tr >
								<td><label for="Date Created"><strong>Transaction Fee</strong></label></td>
								<td>${responseJSON.txnfee}
								<input type="hidden" name="txnfee"  id="txnfee" value="${responseJSON.txnfee}" /></td>
								
								<td></td>
								<td></td>
						 </tr>
						 <tr >
								<td><label for="Date Created"><strong>Action Type</strong></label></td>
								<td>${responseJSON.actiontype}
								<input type="hidden" name="actiontype"  id="actiontype" value="${responseJSON.actiontype}" />
								</td>
								<td></td>
								<td></td>
						 </tr>
						
						 <tr >
								<td><label for="Date Created"><strong>Remarks</strong></label></td>
								<td>
								<textarea rows="10" cols="5" name="remarks"  id="remarks" readonly>${responseJSON.remarks}</textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						
				</table>
				
				<div>Successfully Process, Pending for Approval</div> 
				
				
					
						
				</div>
			</div>
		</div> 
	 <input type="hidden" id="refno" name="refno"/>
	<input type="hidden" id="requesttype" name="requesttype" value="${responseJSON.requesttype}" />
		 <div class="form-actions" align="left"> 
				<input type="button" id="non-printable" class="btn btn-info" onclick="redirectAct();" value="Home" />
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Next" width="100"  >
			</div> 
			 
		</div> 
	 

  
   
  
</form>
 
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery.dataTables.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/datatable-add-scr.min.js'></script> 
</body>
</html>
 