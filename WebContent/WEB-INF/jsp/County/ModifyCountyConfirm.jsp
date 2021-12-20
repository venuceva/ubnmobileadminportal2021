
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
<head>
<meta charset="utf-8">
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 <s:set value="responseJSON" var="respData"/>
 
<script type="text/javascript">

function createMerchant(){ 
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/updateCounty.action';
	$("#form1").submit();
	return true;
}
function getPreviousScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/merchantCreateBackAct.action';
	$("#form1").submit();
	return true;
}


</script>
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
</style > 
</head>

<body>
	<form name="form1" id="form1" method="post" action="">
				<div id="content" class="span10"> 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="generateMerchantAct.action">University Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Modify University Confirmation</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable" ><!--/span-->
					<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>University Primary Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						</div>
					</div>  
						
					<div class="box-content" id="primaryDetails"> 
						<fieldset> 
							<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable ">
								<tr class="even">
									<td width="20%"><label for="County Name"><strong>University Name</strong></label></td>
									<td width="30%"><s:property value="countyName"/><input name="countyName" type="hidden"  id="countyName" value="<s:property value='countyName' />" class="field" ></td>
									<td width="20%"><label for="County ID"><strong>University ID</strong></label></td>
									<td width="30%"><s:property value="countyID"/><input name="countyID" type="hidden" id="countyID" class="field" value="<s:property value='countyID' />"  ></td>
								</tr>
							</table>
						</fieldset> 
						</div>  	
					</div> 
				</div> 
				
				<div class="row-fluid sortable"><!--/span--> 
						<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Contact Details
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
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td ><s:property value="addressLine1"/><input name="addressLine1" id="addressLine1" class="field" type="hidden"  maxlength="50" value="<s:property value="addressLine1"/>"></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td ><s:property value="addressLine2"/><input name="addressLine2" type="hidden" class="field" id="addressLine2"   value="<s:property value="addressLine2"/>"/></td>
											</tr>
											<tr class="even">
												<%-- <td ><label for="Country"><strong>Country</strong></label></td>
											<td ><s:property value="country"/><input type="hidden" name="country" value='<s:property value="country"/>'>
											</td> --%>
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><s:property value="faxNumber"/><input name="faxNumber"  type="hidden" class="field" id="faxNumber" value="<s:property value="faxNumber"/>" /><span id="faxNumber_err" class="errmsg"></span></td>											 
											
											<td><label for="City"><strong>City/Town</strong></label></td>
												<td ><s:property value="city"/><input name="city" type="hidden" class="field" id="city"  value="<s:property value="city"/>"/></td>
											</tr>
											<tr class="odd">
											<td ><label for="P.O. Box Number "><strong>P.O. Box Number</strong></label></td>
												<td ><s:property value="poBoxNumber"/><input name="poBoxNumber" type="hidden" id="poBoxNumber" class="field" value="<s:property value="poBoxNumber"/>"/></td>
												<td ><label for="Postal Code"><strong>Postal Code</strong></label></td>
												<td ><s:property value="postalCode"/><input name="postalCode" type="hidden" id="postalCode" class="field" value="<s:property value="postalCode"/>"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
										   </tr>
											<%-- <tr class="even">
												<td ><label for="Address Line 3 "><strong>Area<font color="red">*</font></strong></label></td>
												<td ><s:property value="area"/><input name="area" type="hidden" class="field" id="area" value="<s:property value="#respData.area"/>"/></td> 
												<td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
												<td ><s:property value="mobileNumber"/><input name="mobileNumber" id="mobileNumber" class="field" type="hidden"  value="<s:property value="#respData.mobileNumber"/>"/><span id="mobileNumber_err" class="errmsg"></span></td>
										   </tr> --%>
										<%-- <tr class="even">
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><s:property value="faxNumber"/><input name="faxNumber"  type="hidden" class="field" id="faxNumber" value="<s:property value="faxNumber"/>" /><span id="faxNumber_err" class="errmsg"></span></td>											 
											<td><label for="L/R Number"><strong>L/R Number</strong></label></td>
											<td ><s:property value="lrNumber"/><input name="lrNumber" type="hidden" class="field" id="lrNumber" value="<s:property value="lrNumber"/>"/></td>
										</tr> --%>
										<tr class="odd">  
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person Name</strong></label></td>
											<td ><s:property value="prmContactPerson"/><input name="prmContactPerson" id="prmContactPerson" class="field" type="hidden" value="<s:property value="prmContactPerson"/>"></td>
											<td><label for="Primary Contact Number"><strong>Primary Contact Person Number</strong></label></td>
											<td ><s:property value="prmContactNumber"/><input name="prmContactNumber" type="hidden" class="field" id="prmContactNumber" value="<s:property value="prmContactNumber"/>"/><span id="prmContactNumber_err" class="errmsg"></span></td>
										</tr>
										<tr class="odd">  
											<td ><label for="Bank Account Number"><strong>Bank Account Number</strong></label></td>
											<td ><s:property value="bankaccountNumber"/><input name="bankaccountNumber" id="bankaccountNumber" class="field" type="hidden" value="<s:property value="bankaccountNumber"/>"></td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</fieldset> 
							</div> 
						</div> 
				</div> 
		 
		<div class="form-actions">
				<a  class="btn btn-danger" href="#" onClick="getPreviousScreen()">Back</a>
				<a  class="btn btn-success" href="#" onClick="createMerchant()">Confirm</a>
		</div> 
	</div> 
</form>
</body>
</html>
