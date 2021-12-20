<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
 <%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName = session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
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
<script type="text/javascript" >
$(document).ready(function() {

	
	
	if($('#srchcriteria').val()=="DIRECT"){
		
		$('#accno123').show();
	}else{
		$('#accno123').hide();
	}

	
	$('#btn-back').live('click', function () {
		var url="${pageContext.request.contextPath}/<%=appName %>/superagent.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});

	

	$('#btn-submit').live('click', function () {
		

		
			var url="${pageContext.request.contextPath}/<%=appName %>/agentSave.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
	

	});


});

</script>
<s:set name="command" value="cmd"/>
</head>
<body>
	<form name="form1" id="form1" method="post" action="" >
			<div class="span10" id="create">
			<div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="superagent.action">Super Agent Management</a> <span class="divider"> &gt;&gt; </span></li>
				</ul>
			</div>
			<s:actionerror cssClass="errors"/>
			<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Super Agent Creation
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						
						<input type="hidden" name="srchcriteria" id="srchcriteria" value='${agent.srchcriteria}'>
						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd" id="accno123" >
										<td width="25%"><strong><label for="Super Agent Name"><strong>Super Agent A/C Number</strong></label></strong></td>
										<td width="25%">
										<input type="hidden" name="agent.accountNumbers" id="accountNumbers" value="${agent.accountNumbers}">${agent.accountNumbers}</td>
										<td width="25%"><strong><label for="Branch Code">Branch Code</label></strong></td>
										<td width="25%">${agent.branchCode}<input name="agent.branchCode" name="branchCode" type="hidden" value='${agent.branchCode}'></td>
										
									</tr>
									<tr class="even">
									<td width="25%"><strong><label for="Super Agent Name"><strong>Business Owner Name</strong></label></strong></td>
									<td width="25%">${agent.accountName}<input type="hidden" name="agent.accountName" value='${agent.accountName}'></td>
									
										<td><label for="Account Currency Code"><strong>Super Agent Id</strong></label></td>
										<td>${agent.CBNAgentId}<input type="hidden" name="agent.CBNAgentId"value='${agent.CBNAgentId}'></td> 
										
									</tr>
									<tr class="even">
											<td ><label for="Date Of Birth"><strong>Date Of Birth</strong></label></td>
					                        <td ><s:property value="agent.dob"/><input type="hidden" maxlength="10"  class="dob" id="dob" name="agent.dob" required=true   value="<s:property value="agent.dob"/>"/></td>

										  	    <td ><label for="Gender"><strong>Gender</strong></label>	</td>
												<td ><s:property value="agent.gender"/>
												<input type="hidden"  class="dob" id="gender" name="agent.gender" required=true   value="<s:property value="agent.gender"/>"/>
												</td>
										   </tr>
									<tr class="odd">
										<td><strong><label for="Email"><strong>Email</strong></label></strong></td>
										<td>${agent.email}<input type="hidden" name="agent.email" id="email" value='${agent.email}'></td>
										<td ><strong><label for="Mobile"><strong>Mobile</strong></label></strong></td>
										<td>${agent.mobile}<input name="agent.mobile" type="hidden" id="mobile" value='${agent.mobile}'></td>
									</tr>
									
									<tr class="even">
										   		<td ><label for="IDType"><strong>ID Type</strong></label></td>
												<td><s:property value="agent.IDType"/>
												<input name="agent.IDType" type="hidden" id="IDType" value='${agent.IDType}'>
												</td>
												<td ><label for="IDNumber"><strong>ID Number</strong></label>	</td>
												<td ><s:property value="agent.IDNumber"/>
												<input name="agent.IDNumber" type="hidden" class="field" id="IDNumber"  value="<s:property value="agent.IDNumber"/>"/></td>
										   </tr>
										   
										     <tr class="odd">
										   		<td><label for="Telco Type"><strong>Telco Type</strong></label></td>
												<td><s:property value="agent.telco"/>
												<input name="agent.telco" type="hidden" class="field" id="telco"  value="<s:property value="agent.telco"/>"/>
											</td>
											 	<td ></td>
												<td></td>
										   </tr>
										   <tr class="even">
												<td width="25%"><label for="Product"><strong>Product</strong></label></td>
												<td width="25%"><s:property value="agent.product"/>
												<input type="hidden"  name="product" id="product"  value="<s:property value="agent.product"/>" />
												
												</td>
												 <td width="25%"><label for="Description"><strong>Description</strong></label></td>
										       <td width="25%"><s:property value="agent.prodesc"/>
										       <input type="hidden"  name="prodesc" id="prodesc"  value="<s:property value="agent.prodesc"/>" />
										       </td>  
											</tr> 
							
										
			

								</table>
							 </fieldset>
							</div>


						</div>
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
										
										   

										   <tr class="odd">
										     	<td width="25%"><label for="Telephone Number 2 "><strong>Telephone Number</strong></label>	</td>
												<td width="25%"><s:property value="agent.telephoneNumber2"/>
												<input name="agent.telephoneNumber2" type="hidden" class="field" id="telephoneNumber2"  value="<s:property value="agent.telephoneNumber2"/>" /></td>
										     	<td width="25%"><label for="Nationality"><strong>Nationality</strong></label></td>
												<td width="25%"><s:property value="agent.nationality"/>
												<input name="agent.nationality" type="hidden" class="field" id="nationality"  value="<s:property value="agent.nationality"/>" />
												
												</td>
										    </tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td ><s:property value="agent.addressLine1"/>
												<input name="agent.addressLine1" id="addressLine1" class="field" type="hidden"   value="<s:property value="agent.addressLine1"/>"  ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td ><s:property value="agent.addressLine2"/>
												<input name="agent.addressLine2" type="hidden" class="field" id="addressLine2"   value="<s:property value="agent.addressLine2"/>"  /></td>
											</tr>
											<tr class="even">
											<td><label for="Local Government"><strong>Local Government</strong></label></td>
												<td><s:property value="agent.localGovernment"/>
												<input name="agent.localGovernment" type="hidden" class="field" id="localGovernment"   value="<s:property value="agent.localGovernment"/>"  />
													 </td>

												<td><label for="State"><strong>State</strong></label></td>
												<td><s:property value="agent.state"/>
												<input name="agent.state" type="hidden" class="field" id="state"   value="<s:property value="agent.state"/>"  />
														 </td>
																</tr>
											<tr class="odd">
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td><s:property value="agent.country"/>
												<input name="agent.country" type="hidden" class="field" id="country"   value="<s:property value="agent.country"/>"  />
												</td>
												<td><label for="City"><strong>City/Town</strong></label></td>
												<td ><s:property value="agent.city"/>
												<input name="agent.city" type="hidden" class="field" id="city"  value="<s:property value="agent.city"/>" required='true' /></td>
										   </tr>
										   <tr class="odd">
										   		<td ><label for="Longitude"><strong>Longitude</strong></label></td>
												<td><s:property value="agent.langitude"/>
												<input name="agent.langitude" type="hidden" id="langitude" class="field" value="<s:property value="agent.langitude"/>" /></td>
												<td ><label for="Latitude"><strong>Latitude</strong></label>	</td>
												<td ><s:property value="agent.latitude"/>
												<input name="agent.latitude" type="hidden" class="field" id="latitude"  value="<s:property value="agent.latitude"/>" /></td>
										   </tr>

									</table>
								</fieldset>
							</div>
						</div>
				</div>
			</div>
			
		
			
			
			<s:if test="%{#command == 'VIEW'}">
				<div class="form-actions">
					<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back" />
				</div>
			</s:if>
			<s:elseif test="%{#command == 'ACTIVEDEACTIVE'}">
				<div class="form-actions">
					<s:if test="#status == A">
						<input type="button" class="btn btn-danger" name="btn-deactive" id="btn-deactive" value="Deactive" />
						<input type="button" class="btn btn-success" name="btn-deactive-cnf" id="btn-deactive-cnf" value="Confirm" style="display:none;"/>
					</s:if>
					<s:elseif test="#status == D">
						<input type="button" class="btn btn-success" name="btn-active" id="btn-active" value="Active" />
						<input type="button" class="btn btn-success" name="btn-active-cnf" id="btn-active-cnf" value="Confirm"  />
					</s:elseif>
				</div>
			</s:elseif>
			<s:else>
			    <div class="form-actions">
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" />&nbsp;
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back" />
				<input name="merchant.password" type="hidden" id="password" />
			 	<input name="merchant.encryptPassword" type="hidden" id="encryptPassword"  />
			 	<input name="merchant.otp" type="hidden" id="otp"  />
				&nbsp;<span id ="error_dlno" class="errors"></span>
			</div>
			</s:else>
	</div>
<input type="hidden" name="status" id="status"/>
</form>
</body>
<script>
 $(function() {
	 
		var config = {
	      '.chosen-select'           : {},
	      '.chosen-select-deselect'  : {allow_single_deselect:true},
	      '.chosen-select-no-single' : {disable_search_threshold:10},
	      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      '.chosen-select-width'     : {width:"95%"}
	    }
		
	    for (var selector in config) {
	      $(selector).chosen(config[selector]);
	    } 
	  	 
		 
		  
		/* $('#plasticCode').val(ses); 
		$('#plasticCode').trigger("liszt:updated");  */
	});
 </script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/manual-validation.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>

</html>
