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
	
	 
  
	$('#btn-back').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/home.action'; 
		$("#form1").submit();
		return true;
	});
	
	$('#btn-submit').on('click', function(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/agentstatusack.action'; 
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
					   <li> <a href="#">Wallet Agent Details</a>  </li> 
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
					<i class="icon-edit"></i>Wallet Agent Status Update Confirmation
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
							<td width="30%">${responseJSON.accountno}<input type="hidden" name="accountno"  id="accountno"   value="${responseJSON.accountno}"   /></td>
							<td ><strong>User Id</strong></td>
							<td >${responseJSON.userid}
							<input type="hidden" name="userid"  id="userid"   value="${responseJSON.userid}"   />
							</td>
							</tr>
							<tr class="even" id="accno" >
							<td width="25%"><label for="From Date"><strong>Wallet Account Number</strong></label></td>
							<td width="30%">${responseJSON.walletaccountno}<input type="hidden" name="walletaccountno"  id="walletaccountno"   value="${responseJSON.walletaccountno}"   /></td>
							<td ><strong>On-Boarded Date</strong></td>
							<td >${responseJSON.onboarddate}
							<input type="hidden" name="onboarddate"  id="onboarddate"   value="${responseJSON.onboarddate}"   />
							</td>
							</tr>
					 	 <tr class="even">
							<td width="25%"><label for="From Date"><strong>First Name</strong></label></td>
							<td width="30%">${responseJSON.fullname}<input type="hidden" name="fullname"  id="fullname"   value="${responseJSON.fullname}"   /></td>
							<td ><label for="From Date"><strong>Middle Name</strong></label></td>
							<td >
							${responseJSON.middlename} <input type="hidden" name="middlename"  id="middlename"   value="${responseJSON.middlename}"   />  </td>
							</tr> 
						<tr class="even">
						<td width="25%"><label for="To Date"><strong>Last Name</strong></label> </td>
							<td width="25%">${responseJSON.lastname} <input type="hidden" name="lastname"  id="lastname"   value="${responseJSON.lastname}"   />  </td>
						
							<td  width="25%"><label for="From Date"><strong>Branch Code</strong></label></td>
							<td  width="25%">
							${responseJSON.branchcode} <input type="hidden" name="branchcode"  id="branchcode"   value="${responseJSON.branchcode}"   />  </td>
							</tr> 
						<tr class="even">
							
							<td  ><label for="To Date"><strong>Date Of Birth</strong></label> </td>
							<td>${responseJSON.dateofbirth}<input type="hidden" name="dateofbirth"  id="dateofbirth"   value="${responseJSON.dateofbirth}"   />  </td>
							<td><label for="From Date"><strong>Email ID</strong></label></td>
							<td>${responseJSON.email} <input type="hidden" name="email"  id="email"   value="${responseJSON.email}"   />  </td>
						</tr>
						<tr class="even">
							
								
						   <td><label for="To Date"><strong>Mobile Number</strong></label> </td>
					       <td >${responseJSON.telephone} 
					    
								<input type="hidden" value='${responseJSON.telephone}' style="width:155px;" maxlength="13" name="telephone" id="telephone"  />
 							
						     
						   </td> 
						     <td><strong>Gender</strong></td>
						      <td>${responseJSON.gender} 
						      <input type="hidden" value='${responseJSON.gender}' style="width:155px;"  name="gender" id="gender"  />
						      </td>
						</tr>
						 <tr class="even" id="superadmin" name="superadmin" >
							<td><label for="Product"><strong>Super Admin</strong></label></td>
							<td>${responseJSON.superadmin} <input type="hidden" name="superadmin"  id="superadmin"   value="${responseJSON.superadmin}"   />  </td>
							<td><strong>Status</strong></td>
							<td>${responseJSON.status}</td>
						</tr> 
						<tr class="even" id="superadmin" name="superadmin" >
							<td><label for="Product"><strong>Product</strong></label></td>
							<td>${responseJSON.product} <input type="hidden" name="product"  id="product"   value="${responseJSON.product}"   />  </td>
							<td><strong>Product Description </strong></td>
							<td>${responseJSON.prodesc}</td>
						</tr> 	
						<tr class="even" id="superadmin" name="superadmin" >
							<td><label for="Product"><strong>Agent Type</strong></label></td>
							<td>${responseJSON.agenttype} <input type="hidden" name="agenttype"  id="agenttype"   value="${responseJSON.agenttype}"   />  </td>
							<td></td>
							<td></td>
						</tr> 					
				 </table>
				</fieldset> 
				
				
				 <div class="row-fluid sortable">
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Communication Details
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
										   		
										  <td><label for="Nationality"><strong>Nationality</strong></label></td>
										  <td>${responseJSON.nationality}<input  type="hidden"  maxlength="50" name="nationality" id="nationality" class="field" value="${responseJSON.nationality}" /></td>
										      <td ><label for="Address Line 1"><strong>Address Line</strong></label></td>
												<td >${responseJSON.address}<input  type="hidden"  maxlength="50" name="addressLine" id="addressLine" class="field" value="${responseJSON.address}" /></td>
											
										  
										   </tr>
										    

										   <tr class="odd">
										   <td width="25%"><label for="IDNumber"><strong>Area</strong></label>	</td>
												<td width="25%">${responseJSON.iddetails}<input type="hidden" class="field" name="IDNumber" id="IDNumber"  value="${responseJSON.iddetails}"/></td>
										   	<td><label for="Local Government"><strong>Local Government</strong></label></td>
												<td>${responseJSON.lga}<input type="hidden" class="field" name="localGovernment" id="localGovernment"  value="${responseJSON.lga}"/></td>
										   
										     	
											</tr>
											<tr class="even">
										
												<td><label for="State"><strong>State</strong></label></td>
												<td> ${responseJSON.state}<input type="hidden" class="field" name="state" id="state"  value="${responseJSON.state}" /> </td>
											
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td>${responseJSON.country}<input type="hidden" class="field" name="country" id="country"  value="${responseJSON.country}" /></td>
												
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
										<input type="hidden" name="refno"  id="refno"   value="${responseJSON.id}"  />
			
	
		<div >
			<a href="#" id="btn-back" class="btn btn-danger ajax-link">&nbsp;Home </a>&nbsp;
			<a href="#" id="btn-submit" class="btn btn-success ajax-link">&nbsp;Confirm</a>					 
		</div> 
	</div> 	 
 </form>

</body> 
</html>