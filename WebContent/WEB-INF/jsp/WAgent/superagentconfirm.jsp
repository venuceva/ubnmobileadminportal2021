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

var list = "dob".split(",");
var datepickerOptions = {
				showTime: true,
				showHour: true,
				showMinute: true,
	  		dateFormat:'dd/mm/yy',
	  		alwaysSetTime: false,
	  		yearRange: '1910:2020',
			changeMonth: true,
			changeYear: true
};

$(function() {
	$(list).each(function(i,val){
		$('.'+val).datepicker(datepickerOptions);
	});
});


$(document).ready(function() {
	
	
	$.getJSON("superagentCountJSONAct.action", queryString,function(data) { 
		var billerCount =data.responseJSON.BILLER_COUNT;
		//alert(billerCount);
		$('#CBNAgentId').val(billerCount);
	});

	$('#nationality').val('<s:property value="agent.nationality"/>');
	$('#gender').val('<s:property value="agent.gender"/>');
	$('#telco').val('<s:property value="agent.telco"/>');
	$('#IDType').val('<s:property value="agent.IDType"/>');
	$('#country').val('<s:property value="agent.country"/>');
	
	
	
	
	var queryString = "";
	
	
	
	 $('#product1').on('change', function() {
    	 // alert( this.value ); 
    	  $('#desc').text((this.value).split("-")[1]);
    	  //$('#product').val((this.value).split("-")[0]);
    	  $('#prodesc').val((this.value).split("-")[1]);
    	  
    	  
    	 
    	  
    	  
    	});
	
	 $('#telco').on('change', function() {	
	
		 $.getJSON("superproductajx.action", queryString, function(data) {
				if(data.region!=""){
					//alert($('#telco').val());
				//	alert(data.region);
					var mydata=data.region;
	  			//var mydata=(data.region).substring(5,(data.region).length);
	  			document.getElementById("product1").length=1;
	  			var mydata1=mydata.split(":");
	  
	   			$.each(mydata1, function(i, v) {
	   				if((mydata.split(":")[i]).split("@")[2]==$('#telco').val()){
	   				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[0]}).attr('data-id',i);
	   				}
	   				$('#product1').append(options);
	   				$('#product1').trigger("liszt:updated");
	   			});
	   			
	   			
	  		} 
	 	}); 
		 
	 });
	
	 if($('#srchcriteria').val()=="DIRECT"){
		
		$('#accno123').show();
		$('#mobile').attr('readonly', true);
	}else{
		$('#accno123').hide();
	} 

	

	$('#btn-back').live('click', function () {
		$("form").validate().cancelSubmit = true;
		var url="${pageContext.request.contextPath}/<%=appName %>/superagent.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});

	$('#btn-deactive').live('click', function () {
		$('#form1').find('input[type="text"], textarea, select').attr('disabled','disabled');
		$('#form1').find('.chosen-select').prop('readonly', true).trigger("liszt:updated");

		$('#btn-deactive-cnf').show();
		$('#btn-deactive').hide();
	});

$('#btn-deactive-cnf').live('click', function () {
		$('#status').val('D');
		$("form").validate().cancelSubmit = true;
		var url="${pageContext.request.contextPath}/<%=appName %>/activedeactive.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});

	$('#btn-active').live('click', function () {
		$('#status').val('A');
		$("form").validate().cancelSubmit = true;
		var url="${pageContext.request.contextPath}/<%=appName %>/activedeactive.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});

	$('#btn-submit').live('click', function () {
		encryptPassword();
	//	alert($('#product1').val());
		$('#product').val($('#product1').val());
		$("#form1").validate(merchantCreateRules);
		if($("#form1").valid()){
			if(($('#mobile').val()).length==13){
				if((($('#mobile').val()).substr(0,3))=="234"){
			var queryString = "method=superagentsearch&mnumber="+ $('#telco').val();	
			$.getJSON("postJson.action", queryString,function(data) { 
				
				
			if(data.message!="SUCCESS"){
				
				
				$('#errors').text(data.message);
			}else{ 
			var url="${pageContext.request.contextPath}/<%=appName %>/superagentconfirmAck.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
			 }
			});
				}else{
					$('#errors').text("Mobile Number must be starting 3 digit 234 .");
				}
			}else{
				$('#errors').text("Mobile Number must be 13 digit .");
			}
		}

	});

	function randomString1() {
		   var charSet = '0123456789';
		    var randomString2 = '';
		    for (var i = 0; i < 5; i++) {
		    	var randomPoz = Math.floor(Math.random() * charSet.length);
		    	randomString2 += charSet.substring(randomPoz,randomPoz+1);
		    }
		    return randomString2;
		}

	function randomString() {
		   var charSet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
		    var randomString = '';
		    for (var i = 0; i < 5; i++) {
		    	var randomPoz = Math.floor(Math.random() * charSet.length);
		    	randomString += charSet.substring(randomPoz,randomPoz+1);
		    }
		    return randomString;
		}

		function encryptPassword(){
			var encryptPass;

			var otpData = randomString1();
			$("#otp").val(otpData);
			var password=randomString();
			$("#password").val(password);
			encryptPass = b64_sha256(password);
			$("#encryptPassword").val(encryptPass);
		}

	var merchantCreateRules= {
			rules : {
		 		'agent.managerName' : {required : true},
				'agent.addressLine1' : {required : true},
				'agent.addressLine2' : {required : true},
				'agent.telephoneNumber1' : {required : true},
				'agent.city'		: {required : true},
				'agent.localGovernment' : {required : true},
				'agent.mobileNumber' : {required : true , number : true},
		 		'agent.country' : {required : true},
		 		'agent.state' : {required : true},
				'agent.merchantType' : {required : true},
				'agent.productType' : {required : true},
				'agent.dob' : {required : true},
				'agent.gender' : {required : true},
				'agent.IDType' : {required : true},
				'agent.IDNumber' : {required : true},
				'agent.telco' : {required : true},
				'agent.nationality' : {required : true},
				'product1' : {required : true},
				'agent.accountName' : {required : true},
				'agent.mobile' : {required : true},

			},
			messages : {
				'agent.managerName' : { required : "Please Enter Manager Name.",
								regex:"Please Enter Valid Manager Name" },
				'agent.addressLine1' : { required : "Please Enter Address Line1."},
				'agent.addressLine2' : { required : "Please Enter Address Line2."},
				'agent.telephoneNumber1' : { required : "Please Enter TelephoneNumber1."},
				'agent.city'		: { required : "Please Enter City/Town."},
				'agent.localGovernment' : { required : "Please Select Local Government."},
				'agent.mobileNumber' : {required : "Please Enter Mobile Number.",
										number : "Please Enter Numbers Only."},
				'agent.country' : { required : "Please Select Country."},
				'agent.state' : {required : "Please Select State."},
				'agent.merchantType' : {required : "Please select Merchant Type."},
				'agent.productType' : {required : "Please Select Product Type."},
				'agent.dob' : {required : "Please Select Date Of Birth."},
				'agent.gender' : {required : "Please Select Gender."},
				'agent.IDType' : {required : "Pleas Select IDType."},
				'agent.IDNumber' : {required : "Pleas Enter IDNumber."},
				'agent.telco' : {required : "Pleas Select Telco Type."},
				'agent.nationality' : {required : "Please Select Nationality."},
				'product1' : {required : "Please Select Product."},
				'agent.accountName' : {required : "Please Enter Bussiness Owner Name."},
				'agent.mobile' : {required : "Please Enter Mobile Number."}

			}
		};
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
			<div class="errors" id="errors"><s:actionerror /></div>
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
										<td width="25%"><strong><label for="Super Agent Name"><strong>Super Agent A/C Number</strong><font color="red">*</font></label></strong></td>
										<td width="25%">
										<input type="hidden" name="agent.accountNumbers" id="accountNumbers" value="${agent.accountNumbers}">${agent.accountNumbers}</td>
										<td width="25%"><strong><label for="Branch Code">Branch Code</label></strong></td>
										<td width="25%">${agent.branchCode}<input name="agent.branchCode" id="branchCode" type="hidden" value='${agent.branchCode}'></td>
										
									</tr>
									<tr class="even">
									<td width="25%"><strong><label for="Super Agent Name"><strong>Business Owner Name<font color="red">*</font></strong></label></strong></td>
									<td width="25%"><input type="text" name="agent.accountName" value='${agent.accountName}'></td>
								
										<td width="25%"><label for="Account Currency Code"><strong>Super Agent Id</strong><font color="red">*</font></label></td>
										<td width="25%">${agent.CBNAgentId}<input type="text" name="agent.CBNAgentId" id="CBNAgentId" value='${agent.CBNAgentId}' readonly></td> 
										
									</tr>
									<tr class="even">
											<td ><label for="Date Of Birth"><strong>Date Of Birth</strong><font color="red">*</font></label></td>
					                        <td ><input type="text" maxlength="10"  class="dob" id="dob" name="agent.dob" required=true  readonly="readonly" value="<s:property value="agent.dob"/>"/></td>

										  	    <td ><label for="Gender"><strong>Gender</strong><font color="red">*</font></label>	</td>
												<td ><select name="agent.gender" id="gender" Class="chosen-select">
													<option value="">Select</option>
													<option value="Male">Male</option>
													<option value="Felame">Felame</option>
												</select></td>
										   </tr>
									<tr class="odd">
										<td><strong><label for="Email"><strong>Email</strong></label></strong></td>
										<td>${agent.email}<input type="text" name="agent.email" value='${agent.email}'></td>
										<td ><strong><label for="Mobile"><strong>Mobile<font color="red">*</font></strong></label></strong></td>
										<td><input type="text" name="agent.mobile"  id="mobile"  value='${agent.mobile}'  ></td>
									</tr>
									
									<tr class="even">
										   		<td ><label for="IDType"><strong>ID Type</strong><font color="red">*</font></label></td>
												<td><select name="agent.IDType" id="IDType" Class="chosen-select">
													<option value="">Select</option>
													<option value="National ID">National ID</option>
													<option value="Driving license">Driving license</option>
													<option value="Voter Card">Voter Card</option>
													<option value="Passport">Passport</option>
												</select></td>
												<td ><label for="IDNumber"><strong>ID Number</strong><font color="red">*</font></label>	</td>
												<td ><input name="agent.IDNumber" type="text" class="field" id="IDNumber"  value="<s:property value="agent.IDNumber"/>"/></td>
										   </tr>
										   
										     <tr class="odd">
										   		<td><label for="Telco Type"><strong>Super Agent</strong><font color="red">*</font></label></td>
												<td><select name="agent.telco" id="telco" Class="chosen-select">
													<option value="">Select</option>
													<option value="AIRTEL">AIRTEL</option>
													<option value="UNION_BANK_CUSTOMER">UNION BANK</option>
													<option value="ALEDIN_AGENCY">ALEDIN AGENCY</option>
												</select></td>
											 	<td ></td>
												<td></td>
										   </tr>
										   <tr class="even">
												<td width="25%"><label for="Product"><strong>Product</strong><font color="red">*</font></label></td>
												<td width="25%">
												<select id="product1" name="product1" class="chosen-select-width" multiple="multiple">
												 <option value="">Select</option>
												</select>
												</td>
												 <td width="25%"><label for="Description"><%-- <strong>Description</strong> --%></label></td>
										       <td width="25%"><div id="desc" style="display:none"></div>
										       <input type="hidden"  name="agent.product" id="product"  value="" />
												<input type="hidden"  name="agent.prodesc" id="prodesc"  value="" />
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
												<td width="25%"><input name="agent.telephoneNumber2" type="text" class="field" id="telephoneNumber2"  value="<s:property value="agent.telephoneNumber2"/>" /></td>
										     	<td width="25%"><label for="Nationality"><strong>Nationality<font color="red">*</font></strong></label></td>
												<td width="25%"><select name="agent.nationality" id="nationality" Class="chosen-select">
													<option value="">Select</option>
													<option value="Nigerian">Nigerian</option>
												
												</select>
												</td>
										    </tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1<font color="red">*</font></strong></label></td>
												<td ><input name="agent.addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="<s:property value="agent.addressLine1"/>" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2<font color="red">*</font> </strong></label></td>
												<td ><input name="agent.addressLine2" type="text" class="field" id="addressLine2"   value="<s:property value="agent.addressLine2"/>" required='true' /></td>
											</tr>
											<tr class="even">
											<td><label for="Local Government"><strong>Local Government<font color="red">*</font></strong></label></td>
												<td><s:select cssClass="chosen-select"
															headerKey=""
															headerValue="Select"
															list="localGovs"
															listKey="govId"
															listValue="govName"
															name="agent.localGovernment"
															id="localGovernment" requiredLabel="true"
															theme="simple"
															data-placeholder="Choose State ..."/>
													 </td>

												<td><label for="State"><strong>State<font color="red">*</font></strong></label></td>
												<td><s:select cssClass="chosen-select"
														headerKey=""
														headerValue="Select"
														list="states"
														name="agent.state"
														listKey="stateCode"
														listValue="stateName"
														id="state"
														requiredLabel="true"
														theme="simple"
														data-placeholder="Choose Government ..."
														 />
														 </td>
																</tr>
											<tr class="odd">
												<td><label for="Nationality"><strong>Country<font color="red">*</font></strong></label></td>
												<td><select name="agent.country" id="country" Class="chosen-select">
													<option value="">Select</option>
													<option value="Nigeria">Nigeria</option>
												</select>
												</td>
												<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="agent.city" type="text" class="field" id="city"  value="<s:property value="agent.city"/>" required='true' /></td>
										   </tr>
										   <tr class="odd">
										   		<td ><label for="Longitude"><strong>Longitude</strong></label></td>
												<td><input name="agent.langitude" type="text" id="langitude" class="field" value="<s:property value="agent.langitude"/>" /></td>
												<td ><label for="Latitude"><strong>Latitude</strong></label>	</td>
												<td ><input name="agent.latitude" type="text" class="field" id="latitude"  value="<s:property value="agent.latitude"/>" /></td>
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
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
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
