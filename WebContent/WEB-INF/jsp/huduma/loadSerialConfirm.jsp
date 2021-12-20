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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Entity</title> 
<!-- jQuery -->

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
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript"> 
 
 
$(function() {  
	
	$('#btn-confirm').live('click',function() {  
		$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/loadSerialData.action";
		$('#form1').submit(); 
	});  
	
	$('#btn-home').live('click',function() { 
		$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/home.action";
		$('#form1').submit();  
	});  
});  

</script>  
</head>

<body> 
<form name="form1" id="form1" method="post" >  
	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="home.action">Home</a> <span class="divider">&gt;&gt;</span> </li>
				<li><a href="loadSerial.action">Load Serial No</a> <span class="divider">&gt;&gt;</span> </li> 
				<li><a href="#">Load Serial No Confirmation</a> </li> 
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
	 
	<div class="row-fluid sortable" id="kra-information"> 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;Load Serial No Confirmation
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 		
			<div id="acctDetails" class="box-content"> 
				<fieldset>  
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="odd"> 
								<td width="20%"><label for="Serial No"><strong>User </strong></label> </td>
								<td colspan="3"> 
									<s:property value='#respData.user' /> <input type="hidden" name="user" id="user" value="<s:property value="#respData.user"/>" />
								</td>
							</tr>
							<tr class="even">
								<td><label for="select"><strong>Select KRA Services </strong></label></td>
								<td colspan="3">
								 	<s:property value='#respData.service' /> <input type="hidden" name="service" id="service" value="<s:property value="#respData.service"/>" />
								</td>
							</tr>
							<tr class="odd"> 
								<td width="20%"><label for="Serial No"><strong>Range From </strong></label> </td>
								<td width="30%"><s:property value='#respData.serialnoFrom' /> <input type="hidden" name="serialnoFrom" id="serialnoFrom" value="<s:property value="#respData.serialnoFrom"/>" /></td>
								<td width="20%"><label for="Serial No"><strong>Range To </strong></label> </td>
								<td width="30%"><s:property value='#respData.serialnoTo' /> <input type="hidden" name="serialnoTo" id="serialnoTo" value="<s:property value="#respData.serialnoTo"/>" /></td>
							</tr>
						</table> 
				</fieldset>  
			</div>
		</div>
	</div> 
	<div class="form-actions"> 
		<input type="button" name="btn-confirm" class="btn btn-success" id="btn-confirm" value="Confirm" /> &nbsp; 
		<input type="button" name="btn-back" class="btn btn-info" id="btn-back" value="Back" />&nbsp; 
		<span id ="error_dlno" class="errors"></span>		 	
	</div>  
	</div><!--/#content.span10-->  
	
	<input type="hidden" name="CV0014" id="agentType" value="kra"/> 
	<input type="hidden" name="CV0002" id="CV0002" value=""/>
	<input type="hidden" name="method" id="method" value="kra"/>  
</form>	
</body> 
</html>
