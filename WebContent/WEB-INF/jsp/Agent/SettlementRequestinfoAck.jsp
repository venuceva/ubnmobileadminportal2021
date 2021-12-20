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
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/Settlementlink.action';
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
					 <li> <a href="#">Unsettlement Details </a>  </li>
				</ul>
			</div>
			
		
		<div class="row-fluid sortable">
			<div class="box span12" id="groupInfo">
				<div class="box-header well" data-original-title>Wallet Transaction ${responseJSON.requesttype} Request acknowledgement
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
							<td width="30%" >${responseJSON.paymentrefno}
							<input type="hidden" name="paymentrefno"  id="paymentrefno" value="${responseJSON.paymentrefno}" />
							<td width="20%" ><strong><label for="Customer Name"><strong>Amount</strong></label></strong></td>
							<td width="30%" >${responseJSON.txnamt}
							<input type="hidden" name="txnamt"  id="txnamt" value="${responseJSON.txnamt}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Number"><strong>User Id</strong></label></strong></td>
							<td>${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid" value="${responseJSON.userid}" /></td>
							<td><strong><label for="Alias Name"><strong>Channel</strong></label></strong></td>
							<td> ${responseJSON.channel}
							<input type="hidden" name="channel"  id="channel" value="${responseJSON.channel}" /></td>
							
						</tr>
						<tr >
							<td><strong><label for="Account Type"><strong>Transaction Type</strong></label></strong></td>
							<td>${responseJSON.txntype}
							<input type="hidden" name="txntype"  id="txntype" value="${responseJSON.txntype}" /></td>
							<td><strong><label for="Institute"><strong>Transaction Date and Time</strong></label></strong></td>
							<td>${responseJSON.txndatetime}
							<input type="hidden" name="txndatetime"  id="txndatetime" value="${responseJSON.txndatetime}" /></td>
						</tr>

						 <tr >
								<td><strong><label for="Date Created"><strong>Narration</strong></label></strong></td>
								<td>${responseJSON.Narration}
								<input type="hidden" name="Narration"  id="Narration" value="${responseJSON.Narration}" /></td>
								<td><strong><label for="branchcode"><strong>Ext Txnref</strong></label></strong></td>
								<td>${responseJSON.batchid}
								<input type="hidden" name="batchid"  id="batchid" value="${responseJSON.batchid}" /></td>
						 </tr>

						 <tr >
								<td><strong><label for="Date Created"><strong>Remarks</strong></label></strong></td>
								<td>
								<textarea rows="10" cols="5" name="remarks"  id="remarks" readonly>${responseJSON.remarks}</textarea>
								</td>
								<td></td>
								<td></td>
						 </tr>
						 <tr >
								<td><strong><label for="Date Created"><strong>Request Type</strong></label></strong></td>
								<td>
								${responseJSON.requesttype}
								</td>
								<td></td>
								<td></td>
						 </tr>
						
				</table>
				<div style="color:red"><strong> Transaction Type ${responseJSON.txntype} ${responseJSON.requesttype} Request Successfully, Pending for Authorization </strong></div> 
				
				
					
						
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
 