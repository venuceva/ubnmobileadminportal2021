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

	

	

	$('#btn-back').live('click', function () {
		
		var url="${pageContext.request.contextPath}/<%=appName %>/superagent.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});



	$('#btn-submit').live('click', function () {
	

			var url="${pageContext.request.contextPath}/<%=appName %>/merchantcreationAck.action";
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
				  <li> <a href="#">Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
				</ul>
			</div>
			<s:actionerror cssClass="errors"/>
			<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Creation Confirmation
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
								<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div id="primaryDetails" class="box-content">
							<fieldset>
								<table width="950" border="0" cellpadding="5" cellspacing="1"
									class="table table-striped table-bordered bootstrap-datatable " id="bank-details">
									<tr class="odd">
										<td width="20%"><label for="Super Agent Name"><strong>Merchant A/C Number</strong></label></td>
										<td width="30%">${responseJSON.accountNumbers}
										<input type="hidden" name="accountNumbers" id="accountNumbers" value="${responseJSON.accountNumbers}"></td>
										<td width="20%"><label for="Super Agent Name"><strong>Merchant Name</strong></label></td>
										<td>${responseJSON.accountName}
										<input type="hidden" name="accountName" id="accountName" value="${responseJSON.accountName}" ></td>
									</tr>
									<tr class="even">
										<td><label for="Account Currency Code"><strong>Account Currency Code</strong></label></td>
										<td>${responseJSON.acctCurrCode}
										<input type="hidden" name="acctCurrCode" id="acctCurrCode" value="${responseJSON.acctCurrCode}" ></td>
										<td><label for="Branch Code"><strong>Branch Code</strong></label></td>
										<td>${responseJSON.branchCode}
										<input name="branchCode" id="branchCode" type="hidden" value="${responseJSON.branchCode}" ></td>
									</tr>
									<tr class="odd">
										<td><label for="Email"><strong>Email</strong></label></td>
										<td>${responseJSON.email}
										<input type="hidden" name="email" id="email" value="${responseJSON.email}" ></td>
										<td ><label for="Mobile"><strong>Mobile</strong></label></td>
										<td>${responseJSON.mobile}
										<input name="mobile" id="mobile" type="hidden"  value="${responseJSON.mobile}" ></td>
									</tr>
									<tr class="even">
										<td width="20%"><strong>Merchant Id</strong></td>
										<td width="30%">${responseJSON.managerId}
										<input name="managerId" type="hidden" id="managerId" class="field" value="${responseJSON.managerId}" >
										</td>											
										<td><label for="Branch Code"><strong>Merchant Type</strong></label></td>
										<td>${responseJSON.managertype}
										<input name="managertype" type="hidden" id="managertype" class="field" value="${responseJSON.managertype}"  >
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
											<tr class="even">
												<td width="20%"><label for="Business Owner"><strong>Business Owner</strong></label></td>
												<td width="30%">${responseJSON.managerName}
												<input name="managerName" type="hidden" id="managerName" class="field" value="${responseJSON.managerName}" maxlength="50" required='true' ></td>
												<td width="20%"></td>
												<td width="30%"></td>
											</tr>
											<tr class="even">
											<td ><label for="Date Of Birth"><strong>Date Of Birth</strong></label></td>
					                        <td >${responseJSON.dob}
					                        <input type="hidden" maxlength="10"  class="dob" id="dob" name="dob" required=true   value="${responseJSON.dob}" ></td>

										  	    <td ><label for="Gender"><strong>Gender</strong></label>	</td>
												<td >${responseJSON.gender}
												<input type="hidden" maxlength="10" class="field"  id="gender" name="gender" required=true   value="${responseJSON.gender}" ></td>
										   </tr>
										   <tr class="even">
										   		<td ><label for="IDType"><strong>ID Type</strong></label></td>
												<td>${responseJSON.IDType}
												<input type="hidden" maxlength="10" class="field"  id="IDType" name="IDType" required=true   value="${responseJSON.IDType}" ></td>
												<td ><label for="IDNumber"><strong>ID Number</strong></label>	</td>
												<td >${responseJSON.IDNumber}
												<input name="IDNumber" type="hidden" class="field" id="IDNumber"  value="${responseJSON.IDNumber}" ></td>
										   </tr>
										    

										   <tr class="odd">
										     	<td ><label for="Telephone Number 2 "><strong>Telephone Number </strong></label>	</td>
												<td >${responseJSON.telephoneNumber2}
												<input name="telephoneNumber2" type="hidden" class="field" id="telephoneNumber2"  value="${responseJSON.telephoneNumber2}" /></td>
										     	<td><label for="Nationality"><strong>Nationality</strong></label></td>
												<td>${responseJSON.nationality}
												<input name="nationality" type="hidden" class="field" id="nationality"  value="${responseJSON.nationality}" /></td>
										    </tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td >${responseJSON.addressLine1}<input name="addressLine1" id="addressLine1" class="field" type="hidden"  maxlength="50" value="${responseJSON.addressLine1}" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td >${responseJSON.addressLine2}<input name="addressLine2" type="hidden" class="field" id="addressLine2"   value="${responseJSON.addressLine2}" required='true' /></td>
											</tr>
											<tr class="even">
											<td><label for="Local Government"><strong>Local Government</strong></label></td>
												<td>${responseJSON.localGovernment}<input name="localGovernment" type="hidden" class="field" id="localGovernment"   value="${responseJSON.localGovernment}" required='true' />
													 </td>

												<td><label for="State"><strong>State</strong></label></td>
												<td>${responseJSON.state}<input name="state" type="hidden" class="field" id="state"   value="${responseJSON.state}" required='true' />
														 </td>
																</tr>
											<tr class="odd">
												<td><label for="Nationality"><strong>Country</strong></label></td>
												<td>${responseJSON.country}<input name="country" type="hidden" class="field" id="country"   value="${responseJSON.country}" required='true' />
												</td>
												<td><label for="City"><strong>City/Town</strong></label></td>
												<td >${responseJSON.city}<input name="city" type="hidden" class="field" id="city"  value="${responseJSON.city}" required='true' /></td>
										   </tr>
										   
										    <tr class="odd">
										   		<td ><label for="Longitude"><strong>Longitude</strong></label></td>
												<td>${responseJSON.langitude}<input name="langitude" type="hidden" id="langitude" class="field" value="${responseJSON.langitude}" /></td>
												<td ><label for="Latitude"><strong>Latitude</strong></label>	</td>
												<td >${responseJSON.latitude}<input name="latitude" type="hidden" class="field" id="latitude"  value="${responseJSON.latitude}" /></td>
										   </tr>

									</table>
								</fieldset>
							</div>
						</div>
				</div>
			</div>
			
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Confirm" />&nbsp;
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Home" />
				
			
	</div>
<input type="hidden" name="status" id="status"/>
</form>
</body>

<script language="Javascript" src="${pageContext.request.contextPath}/js/manual-validation.js"></script>
<script language="Javascript" src="${pageContext.request.contextPath}/js/authenticate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/sha256.js" ></script>

</html>
