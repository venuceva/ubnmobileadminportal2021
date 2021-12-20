<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Team">

<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString();%>
<%@taglib uri="/struts-tags" prefix="s"%> 
<script type="text/javascript" >
function authmainpage(){
 	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/customeServicesAct.action';
	$("#form1").submit();
	return true;
}
$(document).ready(function(){   
	
	
	
	var custid=$('#customerstatus').val();
	
	if(custid=='Reset Pin')
		{
		$("#secondaryDetails").css({"display":""});
		$('#heading').text("Pin Reset");
		$('#heading1').text("Pin Reset Acknowledgement");
		}
	else if (custid=='Resend Pin')
		{
		$("#primaryDetails").css({"display":""});
		}
	else if (custid=='Unblock Pin')
	{
	$("#unblockDetails").css({"display":""});
	}
	else if (custid=='ENABLED' || custid=='DISABLED')
	{
		if (custid=='ENABLED'){
			$('#heading').text("Enable Customer");
			$('#heading1').text("Enable Customer Acknowledgement");
		}else{
			$('#heading').text("Disable Customer");
			$('#heading1').text("Disable Customer Acknowledgement");	
		}
		
	$("#blockedDetails").css({"display":""});
	}else if(custid=='USSD_DISABLED')
		{
		$("#USSD_DISABLED").css({"display":""});
		$('#heading').text("Disable USSD ");
		$('#heading1').text("Disable USSD Acknowledgement");
		}
	else if (custid=='USSD_ENABLED')
		{
		$("#USSD_ENABLED").css({"display":""});
		$('#heading').text("Enable USSD ");
		$('#heading1').text("Enable USSD Acknowledgement");
		}
	else if(custid=='MOBILE_DISABLED')
	{
	$("#MOBILE_DISABLED").css({"display":""});
	$('#heading').text("Disable MOBILE ");	
	$('#heading1').text("Disable MOBILE Acknowledgement");
	}
else if (custid=='MOBILE_ENABLED')
	{
	$("#MOBILE_ENABLED").css({"display":""});
	$('#heading').text("Enable MOBILE ");
	$('#heading1').text("Enable MOBILE Acknowledgement");
	} 
else if (custid=='PASSWORD_REQUEST')
{
$("#PASSWORD_RESET").css({"display":""});
$('#heading').text("Password Reset ");
$('#heading1').text("Password Reset Acknowledgement");
} 
		
 	$('#pinreset').click(function(){ 
		if($("#form1").valid()) {
			
			var custid1=$('#custcode').val();
			
			//alert(custid1);
			$('#accountid').val(custid1);
			$('#closed').val('resetpin');
			
			
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/resendpin.action'; 
			$('#form1').submit();
			 return true; 
		} else { 
			return false;
		}
});


	
 
});


</script>
</head>
<body>
<form name="form1" id="form1" method="post" action="">
			<div id="content" class="span10">
			    <div>
					<ul class="breadcrumb">
					<li><a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span></li>
					<li><a href="serviceMgmtAct.action">Customer Services</a> <span class="divider"> &gt;&gt; </span> </li>
					 <li> <a href="#"><span id="heading"></span></a>  </li> 
				</ul>
				</div>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12"> 
						<div class="box-header well" data-original-title>
								<i class="icon-edit"></i> <span id="heading1"></span>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
							</div>
						</div>

						<div id="primaryDetails" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> USER LOGIN PASSWORD SENT SUCCESSFULLY TO THE MOBILE NUMBER <font color="red"><s:property value='accBean.telephone' /> </font> </strong>
										
										
										<input type="hidden" id="telephone" name="telephone" value="<s:property value='accBean.telephone' />">
										<input type="hidden" id="customerstatus" name="customerstatus" value="<s:property value='accBean.customerstatus' />">
										
										</td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						<div id="secondaryDetails" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> PIN RESET WAS SUCCESSFULLY SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						<div id="unblockDetails" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> PIN WAS UN-BLOCKED SUCCESSFULLY </strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						<div id="blockedDetails" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> CUSTOMER HAD BEEN <font color="red"><s:property value='accBean.customerstatus' /></font> SUCCESSFULLY WITH ALL SERVICES SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						
						<div id="USSD_DISABLED" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> UPDATE USSD STATUS DISABLE WAS SUCCESSFULLY SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						<div id="USSD_ENABLED" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong>  UPDATE USSD STATUS ENABLE WAS SUCCESSFULLY SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						<div id="MOBILE_DISABLED" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong>  UPDATE MOBILE STATUS DISABLE WAS SUCCESSFULLY SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						<div id="MOBILE_ENABLED" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong>  UPDATE MOBILE STATUS ENABLE WAS SUCCESSFULLY SENT TO CHECKER AUTHORIZATION</strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
						<div id="PASSWORD_RESET" class="box-content" style="display:none">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="odd">
										<td ><strong> PASSWORD RESET SUCCESSFULLY SENT TO CHECKER AUTHORIZATION </strong></td> 
									</tr> 
								</table>
							<fieldset>
						</div>
						
      			    </div>
                </div>
		<div class="form-actions">
			<a  class="btn btn-primary" href="#" onClick="authmainpage()">Next</a>
		</div>
	</div>  
</form>
</body>
</html>
