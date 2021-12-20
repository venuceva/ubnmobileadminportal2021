<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %> 
 

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
<script type="text/javascript">

$(function() {  
	
	$("#state").val("${responseJSON.state}".split("-")[1]);
	$("#state1").text("${responseJSON.state}".split("-")[1]);
  
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/storeRegistrationAck.action'; 
		$("#form1").submit();
		
		 $(this).prop('disabled', true);
			$("#btn-submit").hide();
		return true;
	}); 
});

</script> 
</head> 
<body>
<form name="form1" id="form1" method="post"> 
		
			<div id="content" class="span10">  
			    <div> 
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> Store Management <span class="divider"> &gt;&gt; </span> </li>
					   <li> <a href="#">Create Wallet Agent Store Confirmation </a>  </li> 
 					</ul>
				</div>  

				<table>
					<tr>
						<td colspan="3">
							<div class="messages" id="messages"><s:actionmessage /></div>
							<div class="errors" id="errors"><s:actionerror /></div>
						</td>
					</tr>
				</table>
			
  <div class="row-fluid sortable"> 
	<div class="box span12"> 
			<div class="box-header well" data-original-title>
					<i class="icon-edit"></i>Wallet Agent Details
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
			</div>  
				<div class="box-content">
					<fieldset> 
						 <table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " id="user-details">  
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Core Bank Account Number</strong></label></td>
							<td width="25%">${responseJSON.accountno}<input type="hidden" name="accountno"  id="accountno"   value="${responseJSON.accountno}"   /></td>
							<td width="25%"><strong>User Id</strong></td>
							<td width="25%">${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid"   value="${responseJSON.userid}"   />
							</td>
							</tr>
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="25%">${responseJSON.walletaccountno}<input type="hidden" name="walletaccountno"  id="walletaccountno"   value="${responseJSON.walletaccountno}"   /></td>
							<td ><strong>Agent Id</strong></td>
							<td >${responseJSON.id}
							<input type="hidden" name="id"  id="id"   value="${responseJSON.id}"   />
							</td>
							</tr>
					 	 <tr class="even">
							<td width="25%"><label for="From Date"><strong>Name</strong></label></td>
							<td width="25%">${responseJSON.fullname}<input type="hidden" name="fullname"  id="fullname"   value="${responseJSON.fullname}"   /></td>
							<td><label for="Product"><strong>Status</strong></label></td>
							<td>${responseJSON.status} <input type="hidden" name="status"  id="status"   value="${responseJSON.status}"   />  </td>
							</tr> 
									
				 </table>
				 <input type="hidden" name="telephone"  id="telephone"   value="${responseJSON.userid}"   />
				</fieldset> 
				
				<div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
										   <tr class="odd">
										   <td width="25%"><label for="Address Line 1"><strong>Store Name</strong></label></td>
												<td width="25%">${responseJSON.storename}
												<input  type="hidden"   name="storename" id="storename" class="field" value="${responseJSON.storename}" />
												</td>
										   <td width="25%"><label for="Address Line 1"><strong>Store Id</strong></label></td>
												<td width="25%">${responseJSON.storeid}
												<input  type="hidden"   name="storeid" id="storeid" class="field" value="${responseJSON.storeid}" /></td>
												
										     	
										   
												
											</tr>
											
										
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div>
				
				 <div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Store Communication Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
									</div>
								</div>

								<div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											
											<tr class="even">
												<td width="25%"><label for="Nationality"><strong>Store Manager Name</strong></label></td>
												<td width="25%">${responseJSON.boname}
												<input type="hidden" class="field" name="boname" id="boname"  value="${responseJSON.boname}" /></td>
												<td width="25%"><label for="Nationality"><strong>Email</strong></label></td>
												<td width="25%">${responseJSON.email}
												<input type="hidden" class="field" name="email" id="email"  value="${responseJSON.email}" /></td>
											</tr>
										
										<tr class="even">
												 <td><label for="Nationality"><strong>Mobile Number</strong></label></td>
												<td>234${responseJSON.mobileno}
												<input type="hidden" class="field" name="mobileno" id="mobileno"  value="234${responseJSON.mobileno}" /></td>
												 <td width="25%"><label for="Address Line 1"><strong>Address Line</strong></label></td>
												<td width="25%">${responseJSON.addressLine}
												<input  type="hidden"  maxlength="50" name="addressLine" id="addressLine" class="field" value="${responseJSON.addressLine}" /></td>
											</tr>
										    

										   <tr class="odd">
										  
												
												<td><label for="Nationality"><strong>Area</strong></label></td>
												<td>${responseJSON.area}
												<input type="hidden" class="field" name="area" id="area" value="${responseJSON.area}"  /></td>
												<td><label for="State"><strong>State</strong></label></td>
												<td><div id="state1"></div>
												 <input type="hidden" class="field" name="state" id="state"   /> </td>
												 
										     	
												
											</tr>
											<tr class="even">
											

												<td width="25%"><label for="Local Government"><strong>Local Government</strong></label></td>
												<td width="25%" >${responseJSON.localGovernment}
												<input type="hidden" class="field" name="localGovernment" id="localGovernment"  value="${responseJSON.localGovernment}"/></td>
												
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td>${responseJSON.country}
												<input type="hidden" class="field" name="country" id="country"  value="${responseJSON.country}" /></td>
											</tr>
											<tr class="even">
												<td><label for="Nationality"><strong>Latitude</strong></label></td>
												<td>${responseJSON.latitude}
												<input type="hidden" class="field" name="latitude" id="latitude"  value="${responseJSON.latitude}" /></td>
												<td><label for="Nationality"><strong>Longitude</strong></label></td>
												<td>${responseJSON.longitude}
												<input type="hidden" class="field" name="longitude" id="longitude" value="${responseJSON.longitude}"  /></td>
											</tr>
											

									</table>
								</fieldset>
							</div>
						</div>
				</div> 
				
				
				
				
				
				</div>  
				
	  </div>
	  </div> 
	  
	
										<input type="hidden" name="langugae"  id="langugae"   value="English"  />
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>

</body> 
</html>