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

	
	$('#managertype').change(function(e) {
		
		 $('#m3').css("display","none");
		 $('#m1').css("display","");
		 $('#m2').css("display","");
		
		if(this.value=="Payment_merchant"){
			
			 var queryString = "";
			 
				 $('#m3').css("display","");
				 $('#m2').css("display","none");
				 $('#m1').css("display","none");
			 
		$.getJSON("merchantajx.action", queryString, function(data) {
 			if(data.region!=""){
 				var mydata=data.region;
      			//var mydata=(data.region).substring(5,(data.region).length);
      			var mydata1=mydata.split(":");
      
       			$.each(mydata1, function(i, v) {
       				
       				var options = $('<option/>', {value: (mydata.split(":")[i]).split("@")[0], text: (mydata.split(":")[i]).split("@")[1]}).attr('data-id',i);
       				$('#managerName1').append(options);
       				$('#managerName1').trigger("liszt:updated");
       			});
       			
       			
      		} 
     	}); 
		}
	});
	
	
	var d = new Date();

	var year = d.getFullYear()-18;

	

	

	
	
	
	
	
	

	$('#btn-back').live('click', function () {
		$("form").validate().cancelSubmit = true;
		var url="${pageContext.request.contextPath}/<%=appName %>/superagent.action";
		$("#form1")[0].action=url;
		$("#form1").submit();

	});



	$('#btn-submit').live('click', function () {
		if($('#managertype').val()=="Lifestyle"){
			$('#managerName').val($('#managerName2').val());	
		}
		
		if($('#managertype').val()=="Payment_merchant"){
			$('#managerName').val(($('#managerName1').val()).split("-")[0]);	
		}

		$("#form1").validate(merchantCreateRules);
		if($("#form1").valid()){
			var url="${pageContext.request.contextPath}/<%=appName %>/merchantcreationconfirm.action";
			$("#form1")[0].action=url;
			$("#form1").submit();
		}

	});



	var merchantCreateRules= {
			rules : {
				'accountNumbers' : {required : true, number : true},
				'email' : {required : true},
				'accountName' : {required : true},
				'managerId' : {required : true},
				'mobile' : {required : true, number : true},
				'managertype' : {required : true},
		 		'managerName' : {required : true},
				'addressLine1' : {required : true},
				'addressLine2' : {required : true},
				'telephoneNumber1' : {required : true},
				'city'		: {required : true},
				'localGovernment' : {required : true},
				'mobileNumber' : {required : true , number : true},
		 		'country' : {required : true},
		 		'state' : {required : true},
				'merchantType' : {required : true},
				'productType' : {required : true},
				'dob' : {required : true},
				'gender' : {required : true},
				'IDType' : {required : true},
				'IDNumber' : {required : true},
				'telco' : {required : true},
				'nationality' : {required : true},

			},
			messages : {
				'accountNumbers' : { required : "Please Enter Account Number.",
							regex:"Please Enter Numbers Only" },
				'email' : { required : "Please Enter Email." },
				'accountName' : { required : "Merchant Name required." },
				'managerId' : { required : "Merchant Id required." },
				'mobile' : { required : "Please Enter Mobile Number.",
							regex:"Please Enter Numbers Only" },
				'managertype' : { required : "Please Select Merchant Type."},
				'managerName' : { required : "Please Enter Manager Name.",
								regex:"Please Enter Valid Manager Name" },
				'addressLine1' : { required : "Please Enter Address Line1."},
				'addressLine2' : { required : "Please Enter Address Line2."},
				'telephoneNumber1' : { required : "Please Enter TelephoneNumber1."},
				'city'		: { required : "Please Enter City/Town."},
				'localGovernment' : { required : "Please Select Local Government."},
				'mobileNumber' : {required : "Please Enter Mobile Number.",
										number : "Please Enter Numbers Only."},
				'country' : { required : "Please Select Country."},
				'state' : {required : "Please Select State."},
				'merchantType' : {required : "Please select Merchant Type."},
				'productType' : {required : "Please Select Product Type."},
				'dob' : {required : "Please Select Date Of Birth."},
				'gender' : {required : "Please Select Gender."},
				'IDType' : {required : "Pleas Select IDType."},
				'IDNumber' : {required : "Pleas Enter IDNumber."},
				'telco' : {required : "Pleas Select Telco Type."},
				'nationality' : {required : "Please Select Nationality."}

			}
		};
});



$('#btn-details').live('click',function() {  
	
	

	vval=$('#accountNumbers').val();
	
	if(vval!=""){
	var queryString9 = "method=fetchAccDatafromCore&accNumber="+vval;
	
	$.getJSON("postJson.action", queryString9,function(data) {
		var message = data.message;
		if(message=="success"){
			var custid = data.custcode; 			
			var fullname = data.accname; 
			var mobile = data.mobile; 
			var v_message = data.service;
			var branchCode = data.brcode;
			var language = data.language;
			
			
			$("#accountName").val(fullname);
			$("#branchCode").val(branchCode);
			$("#acctCurrCode").val(v_message);
			$("#mobile").val(mobile);
			$("#managerId").val("MER"+language);
			$('#errormsg').text("");
			
		}else{
			$('#errormsg').text(message);
		}	
			
				
	});
	}else{
		$("#accountNumbersMsg").html("<b> Please Enter Account Number </b>");
	}


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
			 <div id="errormsg" class="errores"></div>
			<div class="row-fluid sortable"><!--/span-->
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<i class="icon-edit"></i>Merchant Creation
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
										<td width="20%"><label for="Super Agent Name"><strong>Merchant A/C Number</strong><font color="red">*</font></label></td>
										<td width="30%">
										<input type="text" name="accountNumbers" id="accountNumbers" >
										<input type="button" class="btn btn-danger" name="btn-details" id="btn-details" value="Get Details" />
										<span id="accountNumbersMsg"></span>
										</td>
										<td width="20%"><label for="Super Agent Name"><strong>Merchant Name</strong><font color="red">*</font></label></td>
										<td><input type="text" name="accountName" id="accountName" readonly></td>
									</tr>
									<tr class="even">
										<td><label for="Account Currency Code"><strong>Account Currency Code</strong></label></td>
										<td><input type="text" name="acctCurrCode" id="acctCurrCode" readonly></td>
										<td><label for="Branch Code"><strong>Branch Code</strong></label></td>
										<td><input name="branchCode" id="branchCode" type="text" readonly></td>
									</tr>
									<tr class="odd">
										<td><label for="Email"><strong>Email</strong><font color="red">*</font></label></td>
										<td><input type="text" name="email" id="email" ></td>
										<td ><label for="Mobile"><strong>Mobile</strong><font color="red">*</font></label></td>
										<td><input name="mobile" id="mobile" type="text"  ></td>
									</tr>
									<tr class="even">
										<td width="20%"><strong>Merchant Id</strong><font color="red">*</font></td>
										<td width="30%"><input name="managerId" type="text" id="managerId" class="field"  maxlength="50" required='true' readonly></td>											
										<td><label for="Branch Code"><strong>Merchant</strong><font color="red">*</font></label></td>
										<td><s:select cssClass="chosen-select" 
							         headerKey="" 
							         headerValue="Select"
							         list="#{'Lifestyle':'Life Style','Payment_merchant':'Payment merchant'}" 
							         name="managertype" 
							         id="managertype"
							         requiredLabel="true" 
							         theme="simple"
							         data-placeholder="Choose Account Type..." 
									/> 
										</td>
									</tr>
									
									<!-- ,'Payment Merchant':'Payment Merchant' -->

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
												<td width="20%"><label for="Business Owner"><strong>Business Owner<font color="red">*</font></strong></label></td>
												<td width="30%" id="m2"><input name="managerName2" type="text" id="managerName2" class="field"  maxlength="50" required='true' ></td>
												<td width="20%" id="m3" style="display:none">
												
												<select id="managerName1" name="managerName1" class="chosen-select-width" >
												 <option value="">Select</option>
												</select>
												</td>
												<td width="20%"  id="m1"></td>
												<td width="30%"><input name="managerName" type="hidden" id="managerName" class="field" value="<s:property value="managerName"/>" maxlength="50" required='true' ></td>
											</tr>
											<tr class="even">
											<td ><label for="Date Of Birth"><strong>Date Of Birth</strong><font color="red">*</font></label></td>
					                        <td ><input type="text" maxlength="10"  class="dob" id="dob" name="dob" required=true  readonly="readonly" value="<s:property value="dob"/>"/></td>

										  	    <td ><label for="Gender"><strong>Gender</strong><font color="red">*</font></label>	</td>
												<td ><select name="gender" id="gender" Class="chosen-select">
													<option value="">Select</option>
													<option value="Male">Male</option>
													<option value="Felame">Felame</option>
												</select></td>
										   </tr>
										   <tr class="even">
										   		<td ><label for="IDType"><strong>ID Type</strong><font color="red">*</font></label></td>
												<td><select name="IDType" id="IDType" Class="chosen-select">
													<option value="">Select</option>
													<option value="National ID">National ID</option>
													<option value="Driving license">Driving license</option>
													<option value="Voter Card">Voter Card</option>
													<option value="Passport">Passport</option>
												</select></td>
												<td ><label for="IDNumber"><strong>ID Number</strong><font color="red">*</font></label>	</td>
												<td ><input name="IDNumber" type="text" class="field" id="IDNumber"  value="<s:property value="IDNumber"/>"/></td>
										   </tr>
										    

										   <tr class="odd">
										     	<td ><label for="Telephone Number 2 "><strong>Telephone Number </strong></label>	</td>
												<td ><input name="telephoneNumber2" type="text" class="field" id="telephoneNumber2"  value="<s:property value="telephoneNumber2"/>" /></td>
										     	<td><label for="Nationality"><strong>Nationality<font color="red">*</font></strong></label></td>
												<td><select name="nationality" id="nationality" Class="chosen-select">
													<option value="">Select</option>
													<option value="Nigerian">Nigerian</option>
													<option value="Uganda">Uganda</option>
													<option value="Kenya">Kenya</option>
												</select>
												</td>
										    </tr>
											<tr class="odd">
												<td ><label for="Address Line 1"><strong>Address Line 1<font color="red">*</font></strong></label></td>
												<td ><input name="addressLine1" id="addressLine1" class="field" type="text"  maxlength="50" value="<s:property value="addressLine1"/>" required='true' ></td>
												<td ><label for="Address Line 2 "><strong>Address Line 2<font color="red">*</font> </strong></label></td>
												<td ><input name="addressLine2" type="text" class="field" id="addressLine2"   value="<s:property value="addressLine2"/>" required='true' /></td>
											</tr>
											<tr class="even">
											<td><label for="Local Government"><strong>Local Government<font color="red">*</font></strong></label></td>
												<td><s:select cssClass="chosen-select"
															headerKey=""
															headerValue="Select"
															list="localGovs"
															listKey="govId"
															listValue="govName"
															name="localGovernment"
															id="localGovernment" requiredLabel="true"
															theme="simple"
															data-placeholder="Choose State ..."/> 
													 </td>

												<td><label for="State"><strong>State<font color="red">*</font></strong></label></td>
												<td><s:select cssClass="chosen-select"
														headerKey=""
														headerValue="Select"
														list="states"
														name="state"
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
												<td><select name="country" id="country" Class="chosen-select">
													<option value="">Select</option>
													<option value="Nigeria">Nigeria</option>
												</select>
												</td>
												<td><label for="City"><strong>City/Town<font color="red">*</font></strong></label></td>
												<td ><input name="city" type="text" class="field" id="city"  value="<s:property value="city"/>" required='true' /></td>
										   </tr>
										   
										    <tr class="odd">
										   		<td ><label for="Longitude"><strong>Longitude</strong></label></td>
												<td><input name="langitude" type="text" id="langitude" class="field" value="<s:property value="agent.langitude"/>" /></td>
												<td ><label for="Latitude"><strong>Latitude</strong></label>	</td>
												<td ><input name="latitude" type="text" class="field" id="latitude"  value="<s:property value="agent.latitude"/>" /></td>
										   </tr>
										  
										  

									</table>
								</fieldset>
							</div>
						</div>
				</div>
			</div>
			
				<input type="button" class="btn btn-success" name="btn-submit" id="btn-submit" value="Submit" />&nbsp;
				<input type="button" class="btn btn-danger" name="btn-back" id="btn-back" value="Back" />
				
			
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
