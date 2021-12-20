<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Banking</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<% String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<%@taglib uri="/struts-tags" prefix="s"%> 

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

<s:set value="responseJSON" var="respData"/>
 
<script type="text/javascript" >
 
  
$(document).ready(function () {   
	 
	$('#btn-submit').live('click', function () { 
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/ackSettlementBank.action';
		$("#form1").submit(); 
	}); 
	
});  
</script>  
</head> 
<body>
<form name="form1" id="form1" method="post" >	
	<div id="content" class="span10"> 
	  <div>
		 <ul class="breadcrumb">
			<li><a href="home.action">Home </a> <span class="divider"> &gt;&gt; </span></li>
			<li><a href="#">Add Bank To Settlement Confirmation</a></li>
 		</ul>
	</div>			
	<table height="3">
		<tr>
			<td colspan="3">
				<div class="messages" id="messages">
					<s:actionmessage />
				</div>
				<div class="errors" id="errors">
					<s:actionerror />
				</div>
			</td>
		</tr>
	 </table>
      
    <div class="row-fluid sortable"><!--/span--> 
		<div class="box span12" id="groupInfo">
                  <div class="box-header well" data-original-title> Bank Details
					<div class="box-icon"> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					 <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a> </div>
				</div>
           		<div class="box-content">
				<fieldset> 
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable "  >  
						<tr class="odd">
							<td width="20%"><label for="Bank Id"><strong>Bank Id </strong></label></td>
							<td width="30%"><s:property  value="bankid" /> <input type="hidden" name="bankid" id="bankid" value="<s:property  value="bankid" />" />  </td>						 
 							<td width="20%"><label for="Bank Name"><strong>Bank Name </strong></label></td>
	                    	<td width="30%"><s:property  value="bankname" /> <input type="hidden" name="bankname" id="bankname" value="<s:property  value="bankname" />" /> </td>
						</tr> 
					</table>
					
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable "  >  
						<tr class="odd">
							<td colspan=4><strong>Note: UpOn Confirming .</strong></td> 
					</table>
				</fieldset> 
			</div>	 
		</div>
	</div> 
	<div class="form-actions"> 
		<input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Confirm" width="100" />
		&nbsp;<input type="button" class="btn" type="text"  name="btn-cancel" id="btn-cancel" value="Back" width="100" /> 
	</div>  
</div>  
</form>
 
</body>
</html>
