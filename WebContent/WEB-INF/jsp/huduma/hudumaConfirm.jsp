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
 
     
 
<script type="text/javascript">
$(function(){
	 
	var val = '${responseJSON.CV0001}';
	val = val.toUpperCase();
	 if(val == 'KRA'){
		status = "FOUND";
		serialstatus ='NOTFOUND';
		$("#kra-information").show(); 
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'NCC'){  
		$("#kra-information").hide();
		$("#ncc-information").show(); 
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'NHIF'){ 
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").show();
	} else if(val == 'STATE_LAW'){ 
		 
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").hide();
		$("#state-law-information").show(); 
		$("#nhif-information").hide();
	} else if(val == 'LAND'){  
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").hide(); 
		$("#state-land-information").show();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	} else if(val == 'MOH'){  
		$("#kra-information").hide();
		$("#ncc-information").hide();
		$("#moh-information").show(); 
		$("#state-land-information").hide();
		$("#state-law-information").hide(); 
		$("#nhif-information").hide();
	}
	
	
});  

$('#btn-make-payment').live('click',function() { 
	var checkAction = "";
	var data = "${responseJSON.CV0013}";
	
	if(data.toUpperCase() == 'MPESA') {	
		//checkAction = "serviceAcknowledge";
		checkAction = "serviceTransactionPin";
	} else {
		checkAction = "serviceTransactionPin";
	} 
	$("#form1")[0].action="${pageContext.request.contextPath}/<%=appName %>/"+checkAction+".action";
	$('#form1').submit(); 
	
});  
</script> 
	 
<link rel="shortcut icon" href="images/favicon.ico">
		
</head>
 <body>
 <form name="form1" id="form1" method="post" > 	
		
	<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">Home</a> <span class="divider">&gt;&gt;</span>
					</li> 
					<li><a href="#">Huduma Service Confirmation</a>
					</li>
				</ul>
			</div> 

			<table height="2">
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
	<div class="row-fluid sortable" id="customer-billing-info"> 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;Agency Details 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
					
				</div>
			</div> 
			<div class="box-content">
				<fieldset>  
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable ">
						<tr class="even">
							<td width="25%"><strong><label for="Select Agency">Selected Agency</label></strong></td>
							<td colspan="3"> ${responseJSON.CV0001} <input type="hidden" name="CV0001" id="CV0001" value=" ${responseJSON.CV0001}"/>
								 
							</td>
						</tr>
					</table>
 				</fieldset> 
			</div>
		</div>
	</div>  
	
	<div class="row-fluid sortable" id="ncc-information" > 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;NCC Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div> 		
			<div class="box-content">
				<fieldset> 
 						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="25%"><strong><label for="NCC Services">NCC Services</label></strong></td>
								<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0017" value="${responseJSON.CV0002}"/></td>
							</tr>
						</table> 
				</fieldset> 
			</div>
			<div class="box-content">
				<fieldset> 	 
					<table width="950"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable" id="check-details" >
						<tr class="odd">
							<td width="25%"><strong><label for="Number Plate">Number Plate</label></strong></td>
							<td width="25%">${responseJSON.CV0015} <input type="hidden" name="CV0015" value="${responseJSON.CV0015}"/></td> 
							<td width="25%"><strong><label for="Received Amount">Received Amount</label></strong></td>
							<td width="25%">${responseJSON.CV0016} <input type="hidden" name="CV0016" value="${responseJSON.CV0016}"/></td> 
						</tr> 
					</table> 
				</fieldset> 
			</div>
		</div>
	</div>
	
	<div class="row-fluid sortable" id="state-land-information" > 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;STATE LAND Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div> 		
			<div class="box-content">
				<fieldset> 
 						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="25%"><strong><label for="select">STATE LAND Service</label></strong></td>
								<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0019" value="${responseJSON.CV0002}"/></td>
							</tr>
						</table>
				</fieldset> 
			</div>
			<div  class="box-content">
				<fieldset> 
						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="check-details" >
							<tr class="odd">
								<td width="25%"><strong><label for="Received Amount">Received Amount</label></strong></td>
								<td width="25%">${responseJSON.CV0018} <input type="hidden" name="CV0018" value="${responseJSON.CV0018}"/></td> 
								<td width="25%">&nbsp;</td>
								<td width="25%">&nbsp;</td> 
							</tr> 
						</table>
				</fieldset> 
			</div>
		</div>
	</div> 
	 
	<div class="row-fluid sortable" id="kra-information" > 
		<div class="box span12">
								
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;KRA Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div> 		
			<div class="box-content">
				<fieldset> 
 						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="25%"><strong><label for="select">KRA Services</label></strong></td>
								<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0002" value="${responseJSON.CV0002}"/></td>
							</tr>
						</table>
				</fieldset> 
			</div>
			<div id="kra-custdetails" class="box-content">
				<fieldset> 		 
						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="check-details" >
							<tr class="odd">
								<td width="25%"><strong><label for="DL No">DL No</label></strong></td>
								<td width="25%">${responseJSON.CV0006} <input type="hidden" name="CV0006" value="${responseJSON.CV0006}"/></td> 
								<td width="25%"><strong><label for="Customer">Customer</label></strong></td>
								<td width="25%">${responseJSON.CV0007} <input type="hidden" name="CV0007" value="${responseJSON.CV0007}"/></td> 
							</tr>
							<tr class="odd">
								<td><strong><label for="ID Number"> ID Number</label></strong></td>
								<td>${responseJSON.CV0008}<input name="CV0008" type="hidden" class="field" value="${responseJSON.CV0008}" /></td>
								<td><strong><label for="Serial No">Serial No </label></strong></td>
								<td>${responseJSON.CV0009}<input name="CV0009" type="hidden" class="field" value="${responseJSON.CV0009}" /></td>
							</tr>
							<tr class="odd">
								<td><strong><label for="Payment  Date"> Payment  Date  </label></strong></td>
								<td>${responseJSON.CV0010}<input name="CV0010" type="hidden" class="field" value="${responseJSON.CV0010}"/></td>
								<td><strong><label for="Expiry Date">Expiry Date</label></strong></td>
								<td>${responseJSON.CV0011}<input name="CV0011" type="hidden" class="field" value="${responseJSON.CV0011}"  /></td>
							</tr>
							<tr class="odd">
								<td><strong><label for="Received Amount">Received Amount</label></strong></td>
								<td>${responseJSON.CV0012}<input name="CV0012" type="hidden" class="field" value="${responseJSON.CV0012}" /></td>
								<td><strong><label for="Mobile">Mobile</label></strong></td>
								<td>${responseJSON.CV0005} <input type="hidden" name="CV0005" value="${responseJSON.CV0005}"/></td> 
							</tr> 
							<tr class="odd">
								<td><strong><label for="select">Payment Mode</label></strong></td>
								<td>${responseJSON.CV0013}<input name="CV0013" type="hidden" class="field" value="${responseJSON.CV0013}" /></td> 
								<td> &nbsp;</td>
								<td>&nbsp;</td> 
							</tr>  
						</table>
				</fieldset> 
			</div>
			</div>
	</div> 
	<div class="row-fluid sortable" id="moh-information" > 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;MOH Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 		
			<div class="box-content">
				<fieldset> 
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><strong><label for="select">Select MOH Service<font color="red">*</font></label></strong></td>
								<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0020" value="${responseJSON.CV0002}"/> </td>
							</tr>
						</table>
				</fieldset> 
			</div>
			<div class="box-content">
				<fieldset>  
					<table width="950"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable" id="check-moh-details" >
						<tr class="odd">
							<td width="20%"><strong><label for="Number Plate"> Received Amount <font color="red">*</font></label></strong></td>
							<td colspan="3">${responseJSON.CV0021} <input type="hidden" name="CV0021" value="${responseJSON.CV0021}"/></td>
							 
						</tr> 
					</table>  
				</fieldset>  
			</div>
		</div> 
	</div>  
	<div class="row-fluid sortable" id="state-law-information" > 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;STATE LAW Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 		
			<div class="box-content">
				<fieldset>  
					<table width="950" border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable " >
						<tr class="even">
							<td width="20%"><strong><label for="select">Select STATE LAW Service<font color="red">*</font></label></strong></td>
							<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0022" value="${responseJSON.CV0002}"/>
							</td>
						</tr>
					</table>
				</fieldset>  
			</div>
				 
			<div  id="state-law-custdetails" class="box-content"> 
				<fieldset>  
					<table width="950"  border="0" cellpadding="5" cellspacing="1" 
						class="table table-striped table-bordered bootstrap-datatable" id="check-state-law-details" >
						<tr class="odd">
							<td width="20%"><strong><label for="Number Plate"> Received Amount <font color="red">*</font></label></strong></td>
							<td colspan="3">${responseJSON.CV0023}<input name="CV0023" type="hidden" class="field" value="${responseJSON.CV0023}" /></td> 
						</tr> 
					</table> 
				</fieldset>  
			</div>
			</div>
		</div> 
 
	<div class="row-fluid sortable" id="nhif-information"> 
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				 <i class="icon-edit"></i>&nbsp;NHIF Member Information 
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
				</div>
			</div> 		
			<div class="box-content">
				<fieldset>  
					<div id="nhif-details"> 
						<table width="950" border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable ">
							<tr class="even">
								<td width="20%"><strong><label for="select">Select NHIF Service<font color="red">*</font></label></strong></td>
								<td colspan="3">${responseJSON.CV0002} <input type="hidden" name="CV0024" value="${responseJSON.CV0002}"/>
								</td>
							</tr>
						</table>
					</div>
					<div  id="nhif-cust-details"> 
						<table width="950"  border="0" cellpadding="5" cellspacing="1" 
							class="table table-striped table-bordered bootstrap-datatable" id="check-nhif-details" >
							<tr class="odd">
								<td width="20%"><strong><label for="NHIF Member No">NHIF Member No<font color="red">*</font></label></strong></td>
								<td widht="30%">${responseJSON.CV0025}<input name="CV0025" type="hidden" class="field" value="${responseJSON.CV0025}" /></td> 
								<td width="20%"><strong><label for="Member No">Member No <font color="red">*</font></label></strong></td>
 								<td width="30%">${responseJSON.CV0026}<input name="CV0026" type="hidden" class="field" value="${responseJSON.CV0026}" /></td> 
							</tr>
							<tr class="odd">
								<td><strong><label for="From Date">From Date<font color="red">*</font></label></strong></td>
								<td>${responseJSON.CV0027}<input name="CV0027" type="hidden" class="field" value="${responseJSON.CV0027}" /></td> 
								<td><strong><label for="To Date">To Date<font color="red">*</font></label></strong></td>
								<td>${responseJSON.CV0028}<input name="CV0028" type="hidden" class="field" value="${responseJSON.CV0028}" /></td> 
							</tr> 
							<tr class="odd">
								<td><strong><label for="Received Amount">Received Amount<font color="red">*</font></label></strong></td>
								<td>${responseJSON.CV0029}<input name="CV0029" type="hidden" class="field" value="${responseJSON.CV0029}" /></td> 
								<td><strong>&nbsp;</td>
								<td>&nbsp;</td>	
							</tr>  
						</table> 
					</div> 
				</fieldset>  
			</div>
		</div> 
	</div> 
	 
	
	<div class="form-actions"> 
		<input type="button" name="btn-make-payment" class="btn btn-primary" id="btn-make-payment" value="Submit Payment" />
	   &nbsp; <input type="button" name="btn-cancel" class="btn" id="btn-cancel" value="Cancel" />
	</div> 
	<input type="hidden" name="CV0014" id="agentType" value="${responseJSON.CV0014}" />
	<input type="hidden" name="CV0030" id="CV0030" value="${responseJSON.CV0030}" />
	<input type="hidden" name="method" id="method" value="HUDUMA_PIN"/>
	  
	</form>
</div><!--/#content.span10--> 
</body> 
</html>
