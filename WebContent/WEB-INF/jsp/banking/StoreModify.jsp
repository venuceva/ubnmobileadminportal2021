
<!DOCTYPE html>
<html lang="en">
<%@taglib uri="/struts-tags" prefix="s"%> 
<head>
<meta charset="utf-8">
<title>NBK</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Another one from the CodeVinci">
<meta name="author" content="">
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>  

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>

<s:set value="responseJSON" var="respData"/>
<script type="text/javascript" >

var merchantCreateRules= {
		rules : {
			merchantName : { required : true, minlength :4, regex: /^[a-zA-Z0-9\/\ ]+$/},
			merchantID : {required : true, 	minlength :15, 	maxlength :15},
			storeId : {required : true},
			storeName : {required : true},
			location : {required : true},
			//kraPin : {required : true },//regex: /^([A-Z]{1}[0-9]{5,}[A-Z]{0,})+$/
	 		managerName : {required : true},
			addressLine1 : {required : true},
			addressLine2 : {required : true},
			city		: {required : true},
			poBoxNumber : {required : true},
			//telephoneNumber1 : {required : true},
			mobileNumber : {required : true , number : true},
			//faxNumber : {required : true , number : true},
			prmContactPerson : {required : true},
			prmContactNumber : {required : true , number : true},
			email : {required : true, email : true},
			merchantType : {required : true}, 
			memberType : {required : true},
			postalCode : {required : true, number : true},
			area : {required : true},
			lrNumber : { required : true },
			country : { required : true }
			

		},
		messages : {
			merchantName : { required : "Please Enter Merchant Name.",
									regex:"Please Enter Valid Manager Name"
			},
			merchantID : { required : "Merchant Id ."},
			storeId : { required : "Please Enter Merchant Name."},
			storeName : { required : "Please Enter Store Name."},
			location : { required : "Please Select NBK Branch Location."},
			//kraPin : { required : "Please Enter KRA Pin." , regex : "Please Enter valid KRA." },
			managerName : { required : "Please Enter Manager Name."},
			addressLine1 : { required : "Please Enter Address Line1."},
			addressLine2 : { required : "Please Enter Address Line2."},
			city		: { required : "Please Enter City/Town."},
			poBoxNumber : { required : "Please Enter P.O. Box Number."},
			//telephoneNumber1 : { required : "Please Enter Merchant Name."},
			mobileNumber : {required : "Please Enter Mobile Number."},
			//faxNumber : { required : "Please Enter Merchant Name."},
			prmContactPerson : { required : "Please Enter Primary Contact Person."},
			prmContactNumber : { required : "Please Enter Primary Contact Number."},
			email : { required : "Please Enter Email."},
			 merchantType : { required : "Please Select Merchant Type."},
			memberType : { required : "Please Select Member Type."},
			country : { required : "Please Select Country."},
			postalCode : {
					required : "Please Enter Postal Code.",
					number : "Please Enter Numbers Only."
			},
			area : {
					required : "Please Select County."
	 		},
			lrNumber : {
					required : "Please Enter L/R Number.",
					regex : "Please Enter valid L/R Number."

			}
		}
	}; 

 /* var merchantCreateRules= {
		rules : { 
			merchantName : {required : true}
			
		}, 
		messages : { 
			merchantName : { required : "Please Enter Merchant Name."},
			
		}
	};  */

function getGenerateMerchantScreen(){
	$("#form1")[0].action='<%=ctxstr%>/<%=appName %>/generateMerchantAct.action';
	$("#form1").submit();
	return true;
}
<%-- function updateStore(){
	$("#form1").validate(merchantCreateRules);
	try{
		if($("#form1").valid()){
		$("#form1")[0].action='<%=ctxstr%>/<%=appName %>/storeUpdateConfirmAct.action';
		$("#form1").submit();
		return true; 
		} else {
			return false;
		} 
	}catch(e){
		console.log("error..!"+e);
	}
}	 --%>

$(document).ready(function() {  
	var country="${resposeJSON.country}";
	var locationName="${responseJSON.locationName}";
	$("#location").val(locationName);

	$("#merchantAdminDetails").hide();
	$("#modify").on('click',function(){
		$("#form1").validate(merchantCreateRules);
		if(true){
			$("#form1")[0].action='<%=ctxstr%>/<%=appName %>/storeUpdateConfirmAct.action';
			$("#form1").submit();
			return true; 
		} else {
			return false;
		} 
	});
	
});
$(document).ready(function() {  
	$.validator.addMethod("regex", function(value, element, regexpr) {          
		 return regexpr.test(value);
  }, "");  
});
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
<s:set value="responseJSON" var="respData"/>	 
<body>
	<form name="form1" id="form1" method="post" action="">	
		<div id="content" class="span10">  
			    <div>
					<ul class="breadcrumb">
					  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
					  <li> <a href="#"> Merchant Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#">Store Modify </a></li>
					</ul>
				</div>
					<table height="3">
						<tr>
							<td colspan="3">
								<div class="messages" id="messages"><s:actionmessage /></div>
								<div class="errors" id="errors"><s:actionerror /></div>
							</td>
						</tr>
					</table>
				<div class="row-fluid sortable"><!--/span--> 
					<div class="box span12">  
							<div class="box-header well" data-original-title>
									<i class="icon-edit"></i>Store Primary Details
								<div class="box-icon">
									<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
									<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>

								</div>
							</div>  
								
						<div class="box-content" id="primaryDetails"> 
							<fieldset>  
								<table width="950" border="0" cellpadding="5" cellspacing="1" 
									class="table table-striped table-bordered bootstrap-datatable " >
									<%-- <tr class="even">
										<td width="20%"><label for="Merchant Name"><strong>Merchant Name</strong></label></td>
										<td width="30%"> 
											<input name="merchantName" type="text" id="merchantName" class="field" value="${responseJSON.merchantName}" readonly >
										</td>
										<td width="20%"><label for="Merchant ID"><strong>Merchant ID</strong></label></td>
										<td width="30%"> 
											<input name="merchantID" type="text" id="merchantID" class="field" value="${responseJSON.merchantID}" readonly >
										</td>
									</tr> --%>
									<tr class="even">
									<td width="20%"><label for="Merchant Name"><strong>Merchant Name<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="merchantName" type="text"  id="merchantName" class="field"  
														value="<s:property value='#respData.merchantName' />" 
														maxlength="50" required='true'  readonly  /></td>
									<td width="20%"><label for="Merchant ID"><strong>Merchant ID<font color="red">*</font></strong></label></td>
									<td width="30%"><input name="merchantID" type="text" id="merchantID" class="field" value="<s:property value='#respData.merchantID' />" 
														maxlength="15" readonly ></td>
								    </tr>
									<%-- <tr class="odd">
										<td><label for="Store Name"><strong>Store Name</strong></label></td>
										<td> 
											<input name="storeName" type="text" id="storeName" class="field" value="${responseJSON.storeName}" >
										</td>
										<td ><label for="Store ID"><strong>Store ID</strong></label></td>
										<td > 	
											<input name="storeId" type="text" id="storeId" class="field" value="${responseJSON.storeId}" readonly >
										</td>	
									</tr> --%>
									<tr class="odd">
									<td><label for="Store Name"><strong>Store Name<font color="red">*</font></strong></label></td>
									<td><input name="storeName" type="text" class="field" id="storeName"  required='true' value="<s:property value='#respData.storeName' />" /></td>
									<td ><label for="Store ID"><strong>Store ID<font color="red">*</font></strong></label></td>
									<td ><input name="storeId"  type="text" id="storeId" class="field"  value="<s:property value='#respData.storeId' />" readonly /> </td>
									</tr>
									<%-- <tr class="even">
										<td ><label for="Location"><strong>Location</strong></label></td>
										<td >
											 <s:select cssClass="chosen-select" 
												headerKey="" 
												headerValue="Select"
												list="#respData.LOCATION_LIST" 
												name="location" 
												value="#respData.locationName" 
												id="location" requiredLabel="true" 
												theme="simple"
												data-placeholder="Choose Location..."  
												 />  
											<!-- <input name="LocationInfo" type="text" id="LocationInfo" class="field" value="${responseJSON.locationName}"  > -->
										</td>
										<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
										<td > 
											<input name="kraPin" type="text" id="kraPin" class="field" value="${responseJSON.KRA_PIN}"  >
										</td>	
									</tr>  --%> 
									<tr class="even">
									<td ><label for="NBK Branch Location"><strong>NBK Branch Location<font color="red">*</font></strong></label></td>
									<td > 
										 <s:select cssClass="chosen-select" 
											headerKey="" 
											headerValue="Select"
											list="#respData.LOCATION_LIST" 
											name="location" 
											value="#respData.LocationInfo" 
											id="location" requiredLabel="true" 
											theme="simple"
											data-placeholder="Choose Location..." 
											onchange="getLocation()"
											 />  
										<input type="hidden" name="locationVal" id="locationVal"  value="<s:property value='#respData.locationVal' />" />
									</td>
									<td ><label for="KRA PIN"><strong>KRA PIN</strong></label></td>
									<td ><input name="kraPin" type="text" class="field" id="kraPin" value="<s:property value='#respData.KRA_PIN' />" readonly="readonly"/></td>	
								    </tr>
									<tr class="even">
										<td ><label  for="Merchant Type"><strong>Merchant Type<font color="red">*</font></strong></label></td>
										<td >
											<input name="merchantTypeVal" type="text" id="merchantTypeVal" class="field" value="${responseJSON.merchantTypeVal}" readonly="readonly" >
										</td>
										<td ><label for="Member Type"><strong>Member Type<font color="red">*</font></strong></label></td>
										<td >
											<%-- <s:property value='#respData.MEMBER_TYPE' />  --%>
											<input name="memberType" type="text" class="field" id="memberType" value="<s:property value='#respData.MEMBER_TYPE' />"  readonly="readonly" />
										</td>	
									</tr>									
								</table>
								</fieldset>  
							</div> 
						</div> 
						</div>
						<div class="row-fluid sortable" id="merchantAdminDetails">					
					<div class="box span12"> 
					<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Admin Details
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
						</div>
					</div>  
						
					<div class="box-content" id="merchantAdminDetails"> 
						 <fieldset>   
								<table width="950" border="0" cellpadding="5" cellspacing="1" class="table table-striped table-bordered bootstrap-datatable ">
									<tr class="even">
										<td width="20%"><label for="Merchant Admin Id"><strong>Merchant Admin Id</strong></label></td>
										<td width="30%"><s:property value='#respData.merchantAdmin' /> 
											<input name="merchantAdmin" type="hidden" class="field" id="merchantAdmin" value="<s:property value='#respData.merchantAdmin' />"  />
										 
										</td>
										<td width="20%"></td>
										<td width="30%"></td>
									</tr>
									<tr class="even" id="userData1">
										<td><label for="User Name"><strong>User Name</strong></label></td>
										<td> <s:property value='#respData.userName' /> 
											<input name="userName" type="hidden"   id="userName" value="<s:property value='#respData.userName' />"  />
										</td>
										<td><label for="User Status"><strong>User Status</strong></label></td>
										<td> <s:property value='#respData.userStatus' />
											<input name="userStatus" type="hidden"   id="userStatus" value="<s:property value='#respData.userStatus' />"  />
										</td>
									</tr>
									<tr class="odd" id="userData2">
										<td><label for="Employee No"><strong>Employee No</strong></label></td>
										<td><s:property value='#respData.employeeNo' />
											<input name="employeeNo" type="hidden"   id="employeeNo" value="<s:property value='#respData.employeeNo' />"  />
										</td>
										<td><label for="Email Id"><strong>Email Id</strong></label></td>
										<td><s:property value='#respData.emailId' />
											<input name="emailId" type="hidden"  id="emailId" value="<s:property value='#respData.emailId' />"  />
										</td>
									</tr>
								</table> 
							 </fieldset> 
						</div> 
					</div> 
				</div>
						<div class="row-fluid sortable"><!--/span--> 
							<div class="box span12">
								<div class="box-header well" data-original-title>
										<i class="icon-edit"></i>Communication Details
									<div class="box-icon">
										<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> 
										<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> 
									</div>
								</div>   
							<%--  <div id="communicationDetails" class="box-content">
									<fieldset> 
										<table width="950" border="0" cellpadding="5" cellspacing="1" 
												class="table table-striped table-bordered bootstrap-datatable ">
											<tr class="even">	
												<td width="20%"><label for="Manager Name"><strong>Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="managerName" type="text" id="managerName" class="field" value="<s:property value='#respData.managerName' />" maxlength="50" required='true' ></td>
												<td width="20%"><label for="Email"><strong>Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="email" type="text"  id="email" class="field"  value="<s:property value='#respData.email' />" required='true' > </td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1<font color="red">*</font></strong></label></td>
												<td ><input name="addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="<s:property value='#respData.addressLine1' />" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2<font color="red">*</font> </strong></label></td>
												<td ><input name="addressLine2" type="text" class="field" id="addressLine2"   value="<s:property value='#respData.addressLine2' />" required='true' /></td>
											</tr>
											<tr class="even">
												<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
												<td ><input name="addressLine3" type="text" class="field" id="addressLine3" value="<s:property value='#respData.addressLine3' />" /></td>
												<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="city" type="text" class="field" id="city"  value="<s:property value='#respData.city' />" required='true' /></td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 3 "><strong>Area<font color="red">*</font></strong></label></td>
												<td ><input name="area" type="text" class="field" id="area" value="<s:property value='#respData.area' />" required='true' /></td> 
												<td ><label for="Postal Code"><strong>Postal Code<font color="red">*</font></strong></label></td>
												<td ><input name="postalCode" type="text" id="postalCode" class="field" value="<s:property value='#respData.postalcode' />"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
												
										   </tr>
											<tr class="even">
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="<s:property value='#respData.poBoxNumber' />" required='true' /></td>
												<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td><input name="telephoneNumber1" type="text" id="telephoneNumber1" class="field" value="<s:property value='#respData.telephoneNumber1' />" /><span id="telephoneNumber1_err" class="errmsg"></span></td>											 
												
										   </tr>
										   <tr class="odd">
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td ><input name="telephoneNumber2" type="text" class="field" id="telephoneNumber2"  value="<s:property value='#respData.telephoneNumber2' />" /><span id="telephoneNumber2_err" class="errmsg"></span></td>												
												<td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
												<td ><input name="mobileNumber" id="mobileNumber" class="field" type="text"  value="<s:property value='#respData.mobileNumber' />" required='true' /><span id="mobileNumber_err" class="errmsg"></span></td>
												
										   </tr>
										<tr class="even">
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="<s:property value='#respData.faxNumber' />" /><span id="faxNumber_err" class="errmsg"></span></td>											 
											<td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="<s:property value='#respData.lrnumber' />" required='true' /></td>
										</tr>
										<tr class="odd">  
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactPerson" id="prmContactPerson" class="field" type="text" value="<s:property value='#respData.PRIMARY_CONTACT_NAME' />" required='true' ></td>
											<td><label for="Primary Contact Number"><strong>Primary Contact Number<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactNumber" type="text" class="field" id="prmContactNumber" value="<s:property value='#respData.PRIMARY_CONTACT_NUMBER' />" required='true' /><span id="prmContactNumber_err" class="errmsg"></span></td>
										</tr>
										<tr class="even">  
											<td ><label for="Country"><strong>Country<font color="red">*</font></strong></label></td>
											<td >
												<select id="country" name="country"   required='true' data-placeholder="Choose country..." 
												class="chosen-select" style="width: 220px;">
												<option value="">Select</option>
												<option value="Kenya">Kenya</option>
												<option value="Uganda">Uganda</option>
											</select> 
											</td>
											<td><label for="County"><strong>County<font color="red">*</font></strong></label></td>
											<td ><input name="county" type="text" class="field" id="county" value="<s:property value="#respData.county"/>" required='true' /></td>
										</tr>
									</table>
								</fieldset> 
							</div>   --%>
							
							 <div id="communicationDetails" class="box-content">
									<fieldset>
										<table width="950" border="0" cellpadding="5" cellspacing="1"
												class="table table-striped table-bordered bootstrap-datatable ">
											<tr class="even">
												<td width="20%"><label for="Manager Name"><strong>Manager Name<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="managerName" type="text" id="managerName" class="field" value="<s:property value="#respData.managerName"/>" maxlength="50" required='true' ></td>
												<td width="20%"><label for="Email"><strong>Email<font color="red">*</font></strong></label></td>
												<td width="30%"><input name="email" type="text"  id="email" class="field"  value="<s:property value="#respData.email"/>" required='true' > </td>
											</tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1<font color="red">*</font></strong></label></td>
												<td ><input name="addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="<s:property value="#respData.addressLine1"/>" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2<font color="red">*</font> </strong></label></td>
												<td ><input name="addressLine2" type="text" class="field" id="addressLine2"   value="<s:property value="#respData.addressLine2"/>" required='true' /></td>
											</tr>
											<tr class="even">
												<td ><label for="Address Line 3 "><strong>Address Line 3</strong></label></td>
												<td ><input name="addressLine3" type="text" class="field" id="addressLine3" value="<s:property value="#respData.addressLine3"/>" /></td>
												<td ><label for="Country"><strong>Country<font color="red">*</font></strong></label></td>
												<td >
												 <select id="country" name="country"   required='true' data-placeholder="Choose country..."
													class="chosen-select" style="width: 220px;">
													<option value="">Select</option>
													<option value="Kenya">Kenya</option>
													<option value="Uganda">Uganda</option>
												</select>
												</td>
											</tr>
											<tr class="odd">

												<td ><label for="Address Line 3 "><strong>County<font color="red">*</font></strong></label></td>
												<td ><%-- <input name="area" type="text" class="field" id="area" value="<s:property value="#respData.area"/>" required='true' /> --%>
													 <select name="area" data-placeholder="Choose County..." class="chosen-select" id="area" value="<s:property value="#respData.area"/>" required='true'>
													 	<option value="">select</option>
													 	<option value="01-Mombasa">01-Mombasa  </option>
													 	<option value="01-Diani">01-Diani    </option>
													 	<option value="02-Malindi">02-Malindi  </option>
														<option value="02-Watamu">02-Watamu   </option>
													 	<option value="03-Kilifi">03-Kilifi   </option>
													 	<option value="27-Eldoret">27-Eldoret  </option>
													 	<option value="32-Nakuru">32-Nakuru   </option>
													 	<option value="47-Nairobi">47-Nairobi  </option>
														<option value="47-Thika">47-Thika    </option>
													 </select> </td>
												<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="city" type="text" class="field" id="city"  value="<s:property value="#respData.city"/>" required='true' /></td>
										   </tr>
											<tr class="even">
												<td ><label for="Postal Code"><strong>Postal Code<font color="red">*</font></strong></label></td>
												<td ><input name="postalCode" type="text" id="postalCode" class="field" value="<s:property value='#respData.postalcode' />"  required='true'/> <span id="postalCode_err" class="errmsg"></span></td>
												<td ><label for="P.O. Box Number "><strong>P.O. Box Number<font color="red">*</font></strong></label></td>
												<td ><input name="poBoxNumber" type="text" id="poBoxNumber" class="field" value="<s:property value="#respData.poBoxNumber"/>" required='true' /></td>

										   </tr>
										   <tr class="odd">
										   		<td ><label for="Telephone Number 1"><strong>Telephone Number 1</strong></label></td>
												<td><input name="telephoneNumber1" type="text" id="telephoneNumber1" class="field" value="<s:property value="#respData.telephoneNumber1"/>" /><span id="telephoneNumber1_err" class="errmsg"></span></td>
												<td ><label for="Telephone Number 2 "><strong>Telephone Number 2</strong></label>	</td>
												<td ><input name="telephoneNumber2" type="text" class="field" id="telephoneNumber2"  value="<s:property value="#respData.telephoneNumber2"/>" /><span id="telephoneNumber2_err" class="errmsg"></span></td>

										   </tr>
										<tr class="even">
											<td ><label for="Mobile Number"><strong>Mobile Number<font color="red">*</font></strong></label></td>
											<td ><input name="mobileNumber" id="mobileNumber" class="field" type="text"  value="<s:property value="#respData.mobileNumber"/>" required='true' /><span id="mobileNumber_err" class="errmsg"></span></td>
											<td ><label for="Fax Number"><strong>Fax Number </strong></label></td>
											<td ><input name="faxNumber"  type="text" class="field" id="faxNumber" value="<s:property value="#respData.faxNumber"/>" /><span id="faxNumber_err" class="errmsg"></span></td>
										</tr>
										<tr class="odd">
											<%-- <td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="<s:property value="#respData.lrNumber"/>" required='true' /></td> --%>
											<td><label for="L/R Number"><strong>L/R Number<font color="red">*</font></strong></label></td>
											<td ><input name="lrNumber" type="text" class="field" id="lrNumber" value="<s:property value='#respData.lrnumber' />" required='true' /></td>
											<%-- <td ><label for="Primary Contact Person"><strong>Primary Contact Person<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactPerson" id="prmContactPerson" class="field" type="text" value="<s:property value="#respData.prmContactPerson"/>" required='true' ></td> --%>
											<td ><label for="Primary Contact Person"><strong>Primary Contact Person<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactPerson" id="prmContactPerson" class="field" type="text" value="<s:property value='#respData.PRIMARY_CONTACT_NAME' />" required='true' ></td>
										</tr>
										<tr class="even">
											<%-- <td><label for="Primary Contact Number"><strong>Primary Contact Number<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactNumber" type="text" class="field" id="prmContactNumber" value="<s:property value="#respData.prmContactNumber"/>" required='true' /><span id="prmContactNumber_err" class="errmsg"></span></td> --%>
											<td><label for="Primary Contact Number"><strong>Primary Contact Number<font color="red">*</font></strong></label></td>
											<td ><input name="prmContactNumber" type="text" class="field" id="prmContactNumber" value="<s:property value='#respData.PRIMARY_CONTACT_NUMBER' />" required='true' /><span id="prmContactNumber_err" class="errmsg"></span></td>
											<td >&nbsp;</td >
											<td >&nbsp;</td >
										</tr>
									</table>
								</fieldset>
							</div> 
							
					</div> 
				</div> 
	<div class="form-actions">
			<a  class="btn btn-danger" href="#" onClick="getGenerateMerchantScreen()">Back</a> &nbsp;&nbsp;
			<a  class="btn btn-success" href="#" id="modify" >Modify</a>
	</div>

</div> 
<script >
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
	
	
	$('#country').val('<s:property value="#respData.country" />');
	$('#country').trigger("liszt:updated"); 
	
	
	$("#area > option").each(function() {
		var county =this.text;
		var area = county.split('-')[1];
		if('<s:property value="#respData.county" />' == area){
			$("#area").val(this.value);
			$('#area').trigger("liszt:updated"); 
		}
	});
	
});

$(document).on('change','select',function(event) { 

	if($('#'+$(this).attr('id')).next('label').text().length > 0) {
	 $('#'+$(this).attr('id')).next('label').text(''); 
	 $('#'+$(this).attr('id')).next('label').remove();
	}

	});
</script>
</form>
</body>
</html>
