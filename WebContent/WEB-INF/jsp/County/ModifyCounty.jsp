<html lang="en">
<head>
<meta charset="utf-8" />
<title>Ceva</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="Brian Kiptoo">
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%String ctxstr = request.getContextPath(); %>
<%String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
 
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
<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >
$(document).ready(function(){
	var governors='${responseJSON.governors}';
	console.log(governors);
	$.each(jQuery.parseJSON(governors), function(i, v) {
	    var options = $('<option/>', {value: v.key, text: v.value});
	    $('#governor').append(options);
	});
	var revdirs='${responseJSON.revdirs}';
	$.each(jQuery.parseJSON(revdirs), function(i, v) {
	    var options = $('<option/>', {value: v.key, text: v.value});
	    $('#revDir').append(options);
	});
	var cfos='${responseJSON.CFOS}';
	$.each(jQuery.parseJSON(cfos), function(i, v) {
	    var options = $('<option/>', {value: v.key, text: v.value});
	    $('#cfo').append(options);
	});
	var ictadmins='${responseJSON.ictadmins}';
	$.each(jQuery.parseJSON(ictadmins), function(i, v) {
	    var options = $('<option/>', {value: v.key, text: v.value});
	    $('#ictAdmin').append(options);
	});
	
});
function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
} 	
$.validator.addMethod("regex", function(value, element, regexpr) {          
	 return regexpr.test(value);
 }, ""); 
var merchantCreateRules= {
		rules : {
			countyName : { required : true,regex: /^[a-zA-Z]+$/, minlength :4},
			city		: {required : true,regex: /^[a-zA-Z]+$/},
			poBoxNumber : {required : true, number : true},
			prmContactPerson : {required : true, regex: /^[a-zA-Z]/},
			prmContactNumber : {required : true, number : true, minlength :9, maxlength :9 },
			postalCode : {required : true, number : true},
			country : {required : true},
			lrNumber : { required : true, regex: /^[a-zA-Z0-9\/\ ]+$/ }, 
			bankaccountNumber: {required : true, number : true},
		},
		messages : {
			countyName : { required : "Please Enter University Name.",regex : "Special characters are not allowed"},
			managerName : { required : "Please Enter Manager Name."},
			city		: { required : "Please Enter City/Town.",regex : "Special characters are not allowed"},
			poBoxNumber : { required : "Please Enter P.O. Box Number.",number : "Please Enter Numbers Only."},
			prmContactPerson : { required : "Please Enter Primary Contact Person Name.", regex : "Numbers and Special Characters are Not Allowed"
			},
		    prmContactNumber : { required : "Please Enter Primary Contact Person Number."
			},
			postalCode : {
					required : "Please Enter Postal Code."
					
			},
			country		: { required : "Please Enter A University."},
			lrNumber : {
					required : "Please Enter L/R Number.",
					regex : "Please Enter valid L/R Number."
			},
			bankaccountNumber : {required : "Please Enter Bank Account Number",number : "Please Enter Numbers Only."},
		}
	};

$('#btn-submit').live('click',function() { 
	$("#form1").validate(merchantCreateRules);
	if($("#form1").valid()){ 
			$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/countytmodifyConfirm.action';
			$("#form1").submit();
			return true;
	}else{
		return false;
	}
});
function getMerchantID(){
 	var formInput='merchantName='+$('#countyName').val();
    $.getJSON('generateMerchantIdAct.action', formInput,function(data) {
		var merchantId = data.responseJSON.merchantID;
		$('#countyID').val(merchantId);
    }); 
   return false;
}
	 
</script>
</head>
<body>
		<div id="content" class="span10"> 
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="generateMerchantAct.action">University Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Modify University</a></li>
					</ul>
				</div>
		<form name="form1" id="form1" method="post" action="">  
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
									<td width="20%"><label for="County Name"><strong>University Name<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="countyName" type="text"  id="countyName" class="field"  
														value="${responseJSON.county.countyName}" maxlength="50" readonly required='true' onChange="getMerchantID()" ></td>
									<td width="20%"><label for="County ID"><strong>University ID<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="countyID" type="text" id="countyID" class="field" value="${responseJSON.county.countyId}" maxlength="15" readonly ></td>
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
											<%--  <tr class="even">
												<td width="20%"><label for="Manager Name"><strong>Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="managerName" type="text" id="managerName" class="field" value="<s:property value="#respData.managerName"/>" maxlength="50" required='true' ></td>
												<td width="20%"><label for="Email"><strong>Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="email" type="text"  id="email" class="field"  value="<s:property value="#respData.email"/>" required='true' > </td>
											</tr> --%>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1</strong></label></td>
												<td ><input name="addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="${responseJSON.county.address1}" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2 </strong></label></td>
												<td ><input name="addressLine2" type="text" class="field" id="addressLine2"   value="${responseJSON.county.address2}" required='true' /></td>
											</tr>
											<tr class="even">
												<%-- <td ><label for="Country"><strong>Country<font color="red">*</font></strong></label></td>
											<td >
											 <select id="country" name="country"   required='true' data-placeholder="Choose country..."  value="${responseJSON.county.country}"
												class="chosen-select" style="width: 220px;">
												<!-- <option value="">Select</option> -->
												<option value="Kenya">Kenya</option>
												<option value="Uganda">Uganda</option>
											</select> 
											</td> --%>
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="${responseJSON.county.faxNumber}" /><span id="faxNumber_err" class="errmsg"></span></td>											 
											
											<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="city" type="text" class="field" id="city"  value="${responseJSON.county.city}" required='true' /></td>
											</tr>
											<tr class="odd">
											<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="${responseJSON.county.pobNum}" required='true' /></td>
												
												<td ><label for="Postal Code"><strong>Postal Code<font color="red">*</font></strong></label></td>
												<td ><input name="postalCode" type="text" id="postalCode" class="field" value="${responseJSON.county.postalCode}"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
										   </tr>
											<%-- <tr class="even">
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="<s:property value="#respData.poBoxNumber" required='true' /></td>
												<td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
												<td ><input name="mobileNumber" id="mobileNumber" class="field" type="text"  value="<s:property value="#respData.mobileNumber" required='true' /><span id="mobileNumber_err" class="errmsg"></span></td>
										   </tr> --%>
										<%-- <tr class="even">
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="${responseJSON.county.faxNumber}" /><span id="faxNumber_err" class="errmsg"></span></td>											 
											<td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="${responseJSON.county.lrNumber}" required='true' /></td>
										</tr> --%>
										<tr class="odd">  
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person Name<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactPerson" id="prmContactPerson" class="field" type="text" value="${responseJSON.county.prmContactperson}" required='true' ></td>
											<td><label for="Primary Contact Number"><strong>Primary Contact Person Number<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactNumber" type="text" class="field" id="prmContactNumber" value="${responseJSON.county.primaryContactNum}" required='true' /><span id="prmContactNumber_err" class="errmsg"></span></td>
										</tr>
										<tr class="odd">  
											<td ><label for="Bank Account Number"><strong>Bank Account Number<font color="red">*</font></strong></label></td>
											<td ><input name="bankaccountNumber" id="bankaccountNumber" class="field" type="text" value="${responseJSON.county.banAccountNumber}" required='true' ></td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</fieldset> 
							</div> 
						</div> 
				</div>
	</form>
		<div class="form-actions">
			<a  class="btn btn-danger" href="generateMerchantAct.action" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
  &nbsp;<input type="button" class="btn btn-primary" type="text"  name="btn-submit" id="btn-submit" value="Submit" width="100" ></input>
			
	<!--  	<a  class="btn btn-success" href="#" onClick="creteCounty()">Submit</a> -->
			<span id ="error_dlno" class="errors"></span>
		</div>  
	</div> 
<script type="text/javascript">
$(function(){
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
	});
	</script>
</body>
</html>