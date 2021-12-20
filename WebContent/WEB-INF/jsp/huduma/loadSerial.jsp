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
var status = "";
var serialstatus = ""; 
var hudumaKraRules = {
		rules : {
			user :  { required : true  },
			serialnoFrom : { required : true , number : true},
			service : {  required :  true} 
		},		
		messages : {
			user :  "Please Select User.",
			serialnoFrom : {
						required : "Please Enter From Serial No."
					  } ,
			service :  "Please Select Service Type." 
		} 
	};  

 
var count = 0;
 
$(function() {  

	$(".chosen-select").chosen({no_results_text: "Oops, nothing found!"}); 
	
	$('#serialnoFrom').focusout(function() {  
		$("#error_dlno").text('');
		count++;
		var queryString="method=searchSerialData&serialNo="+$("#serialnoFrom").val()+"&user="+$("#user option:selected").val()+"&service="+$("#service option:selected").val(); 
 		$.getJSON("postJson.action", queryString,function(data) {  
			serialstatus = data.status;
			 
			if(serialstatus == "FOUND")  {
				$("#error_dlno").html("The entered serial number exists, please enter a new serial no.").show(); 
			} else {
				$("#error_dlno").text('');
				
				try{
					$("#serialnoTo").val(parseInt($("#serialnoFrom").val())+49);
				}catch(e){
					$("#error_dlno").html("Invalid Serial From.").show();
				}
				 
			}
 		}); 
	});
	
	var flag= true;
	var flag1= true;
	
	$('#btn-make-payment').live('click',function() {
			 
		$("#error_dlno").text('');
		$("#form1").validate(hudumaKraRules); 
		
		if($("#form1").valid() ) {    
			if( serialstatus=='NOTFOUND')  {
				$("#error_dlno").text('');
				$('#CV0002').val($('#service option:selected').text());
				$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/loadSerialConfirm.action";
				$('#form1').submit(); 
			}  else if(serialstatus!='NOTFOUND') { 
				$("#error_dlno").html("The Entered Serial Number Exists. Please enter a new Serial No.").show();	 
			} 
		} else { 
			return false;
		}	 
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
				<li><a href="#">Load Serial No</a> </li> 
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
				 <i class="icon-edit"></i>&nbsp;Load Serial No 
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
								<td width="20%"><label for="Serial No"><strong>User <font color="red">*</font></strong></label> </td>
								<td colspan="3"> 
									<s:select cssClass="chosen-select" 
										headerKey="" 
										headerValue="Select"
										list="#respData.USERS_DATA"
										name="user" 
 										id="user" 
										requiredLabel="true" 
										theme="simple"
										data-placeholder="Choose User..." 
										style="width: 320px;"
	 									 />   &nbsp;  
								</td>
							</tr>
							<tr class="even">
								<td><label for="select"><strong>Select KRA Services <font color="red">*</font></strong></label></td>
								<td colspan="3">
								
									<s:select cssClass="chosen-select" 
										headerKey="" 
										headerValue="Select"
										list="#{'DLR1':'Driving License Renewal(1YR)','DLR3':'Driving License Renewal(3YR)'}" 
										name="service" 
										value="#respData.service" 
										id="service" 
										requiredLabel="true" 
										theme="simple"
										data-placeholder="Choose Service..." 
										style="width: 320px;"
 									 />  &nbsp; <label id="service-id" class="errors" ></label>
									 
								</td>
							</tr>
							<tr class="odd"> 
								<td><label for="Serial No"><strong>Range From <font color="red">*</font></strong></label> </td>
								<td ><input name="serialnoFrom" autocomplete="off" type="text" class="field" id="serialnoFrom" style="width: 320px" required=true  /> </td>
								<td><label for="Serial No"><strong>Range To </strong></label> </td>
								<td><input name="serialnoTo" autocomplete="off" type="text" class="field" id="serialnoTo" style="width: 320px" readonly  /> </td>
							</tr>
						</table> 
				</fieldset>  
			</div>
		</div>
	</div>  
	<div class="form-actions"> 
		<input type="button" name="btn-make-payment" class="btn btn-success" id="btn-make-payment" value="Submit" /> &nbsp; 
		<input type="button" name="btn-home" class="btn btn-info" id="btn-home" value="Home" />&nbsp; 
		<span id ="error_dlno" class="errors"></span>		 	
	</div>  
	</div><!--/#content.span10-->  
	
	<input type="hidden" name="CV0014" id="agentType" value="kra"/> 
	<input type="hidden" name="CV0002" id="CV0002" value=""/>
	<input type="hidden" name="method" id="method" value="kra"/>  
</form>	
</body> 
</html>
