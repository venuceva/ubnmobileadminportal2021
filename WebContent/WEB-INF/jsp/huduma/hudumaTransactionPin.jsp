<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<% String loginUser =  session.getAttribute(CevaCommonConstants.MAKER_ID).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 
<script type="text/javascript"> 

var hudumaKraRules = {
			rules : { 
				CV1116 : {  required :  true} 
			},		
			messages : { 
				CV1116 : { 
							required : "Please Input Transaction Pin."
						} 
			} 
		};  

$(function() {
		$('#userId,#transactionPin').live('keypress',function() {  
			 $('#messages').text('');
			 $('#errors').text('');
		});

		$('#btn-make-payment').live('click',function() {  
			$("#form1").validate(hudumaKraRules);
			if($("#form1").valid()) {  
				$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/serviceAcknowledge.action";
				$('#form1').submit();
			} else {
				return false;
			}
		}); 
}); 
</script>
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
 
		
</head>
 <body>
	 <form name="form1" id="form1" method="post" > 
		
		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">Home</a> <span class="divider">&gt;&gt;</span>
					</li>
					<li><a href="#">Billing</a> <span class="divider">&gt;&gt;</span>
					</li>
					<li><a href="#">Huduma Transaction Pin</a>
					</li>
				</ul>
			</div> 

			<table height="2" >
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


	 
	<div class="row-fluid sortable" id="kra-information" > 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;Transaction Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 	
			<div class="box-content"  id="custDetails">
				<fieldset>  
					 
						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="check-details" >
							<tr class="odd">
								<td width="25%"><strong><label for="DL No"> User Id <font color="red">*</font></label></strong></td>
								<td colspan="3"> <%=loginUser %><input name="CV1115" type="hidden" class="field" value="<%=loginUser %>"/></td> 
								 
							</tr> 
							<tr class="odd">
								<td width="25%"><strong><label for="DL No"> Transaction Pin<font color="red">*</font></label></strong></td>
								<td colspan="3">  <input name="CV1116" type="password" class="field" id="transactionPin" autocomplete="off" required=true /></td> 
							 
							</tr> 
						</table> 
				</fieldset>  
			</div>
		</div>
	</div> 

	<div class="form-actions"> 
	   <input type="button" name="btn-make-payment" class="btn btn-primary" id="btn-make-payment" value="Confirm Payment" />
	</div>  	
	<input type="hidden" name="method" id="method" value="${responseJSON.CV0001}" />
	<input type="hidden" name="CV0001" id="CV0001" value="${responseJSON.CV0001}" />
	<input type="hidden" name="CV0002" id="CV0002" value="${responseJSON.CV0002}" />
	<input type="hidden" name="CV0005" id="CV0005" value="${responseJSON.CV0005}" /> 
	<input type="hidden" name="CV0006" id="CV0006" value="${responseJSON.CV0006}" />
	<input type="hidden" name="CV0007" id="CV0007" value="${responseJSON.CV0007}" />
	<input type="hidden" name="CV0008" id="CV0008" value="${responseJSON.CV0008}" />
	<input type="hidden" name="CV0009" id="CV0009" value="${responseJSON.CV0009}" />
	<input type="hidden" name="CV0010" id="CV0010" value="${responseJSON.CV0010}" />
	<input type="hidden" name="CV0011" id="CV0011" value="${responseJSON.CV0011}" />
	<input type="hidden" name="CV0012" id="CV0012" value="${responseJSON.CV0012}" />
	<input type="hidden" name="CV0013" id="CV0013" value="${responseJSON.CV0013}" />
	<input type="hidden" name="CV0014" id="CV0014" value="${responseJSON.CV0014}" />
	<input type="hidden" name="CV0015" id="CV0015" value="${responseJSON.CV0015}" />
	<input type="hidden" name="CV0016" id="CV0016" value="${responseJSON.CV0016}" />
	<input type="hidden" name="CV0017" id="CV0017" value="${responseJSON.CV0017}" />
	<input type="hidden" name="CV0018" id="CV0018" value="${responseJSON.CV0018}" />
	<input type="hidden" name="CV0019" id="CV0019" value="${responseJSON.CV0019}" />
	<input type="hidden" name="CV0020" id="CV0020" value="${responseJSON.CV0020}" />
	<input type="hidden" name="CV0021" id="CV0021" value="${responseJSON.CV0021}" />
	<input type="hidden" name="CV0022" id="CV0022" value="${responseJSON.CV0022}" />
	<input type="hidden" name="CV0023" id="CV0023" value="${responseJSON.CV0023}" />
	<input type="hidden" name="CV0024" id="CV0024" value="${responseJSON.CV0024}" />
	<input type="hidden" name="CV0025" id="CV0025" value="${responseJSON.CV0025}" />
	<input type="hidden" name="CV0026" id="CV0026" value="${responseJSON.CV0026}" />
	<input type="hidden" name="CV0027" id="CV0027" value="${responseJSON.CV0027}" />
	<input type="hidden" name="CV0028" id="CV0028" value="${responseJSON.CV0028}" />
	<input type="hidden" name="CV0029" id="CV0029" value="${responseJSON.CV0029}" /> 
	<input type="hidden" name="CV0030" id="CV0030" value="${responseJSON.CV0030}" />
	
	</form>
</div><!--/#content.span10--> 
</body> 
</html>
