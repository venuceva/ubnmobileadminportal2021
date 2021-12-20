
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<%@page import="com.ceva.base.common.utils.CevaCommonConstants"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<%String ctxstr = request.getContextPath(); %>
<% String appName= session.getAttribute(CevaCommonConstants.ACCESS_APPL_NAME).toString(); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css">
.messages {
  font-weight: bold;
  color: green;
  padding: 2px 8px;
  margin-top: 2px;
}

.errors{
  font-weight: bold;
  color: red;
  padding: 2px 8px;
  margin-top: 2px;
}
label.error {
	  font-weight: bold;
	  color: red;
	  padding: 2px 8px;
	  margin-top: 2px;
}
.errmsg {
color: red;
}
</style>

<script type="text/javascript">
var finalData="";
var listDate = "expiryDate".split(",");
var datepickerOptions = {
			showTime: false,
			showHour: false,
			showMinute: false,
  		dateFormat:'dd/mm/yy',
  		alwaysSetTime: false,
		changeMonth: true,
		changeYear: true
};

var toDisp = '${type}';
var validationRules = {
			rules : {
				CV0002 : { required : true },
				CV0003 : { required : true } ,
				CV0004 : { required : true } ,
				officeLocation : { required : true } ,
				CV0016 : { required : true },
				CV0011 : {required :true ,date : true} ,
				CV0007 : { required : true } ,
				CV0012 : { required : true , email : true}
			},
			messages : {
				CV0002 : {
					            required : "Please Enter Employee No."
				            },
				CV0003 : {
								required : "Please Enter First Name."
							},
		    	CV0004 : {
								required : "Please Enter Last Name."
							},
				officeLocation : {
								required : "Please Select Branch."
							}, 
				CV0016 : {
					            required : "Please Select Store Id."
				            },
				CV0011 : {
								required : "Please Select Expiry Date.",
								date  : "Expiry date, should be always greater than system date."
							},
				CV0007 : {
								required : "Please Enter Valid Mobile Number."
							},
				CV0012 : {
								required : "Please Enter Email Address.",
								CV0012 : "Please Enter A Valid Email Address."
						}
			}
		};
/* $(document).ready(function(){
	if('${responseJSON.CV0014}' == 'MERTADM' || '${responseJSON.CV0014}' == 'MERCHTUSR' || '${responseJSON.CV0014}' == 'MERCNTSUPE')
	{
		$('#idtype').empty();
		$('#idtype').text('ID');
	}
}); */

//For Closing Select Box Error Messages_Start
$(document).on('change','select',function(event) {

if($('#'+$(this).attr('id')).next('label').text().length > 0) {
 $('#'+$(this).attr('id')).next('label').text('');
 $('#'+$(this).attr('id')).next('label').remove();
}

});
//For Closing Select Box Error Messages_End

$(function() {



	var location ='${responseJSON.CV0018}';
	var entCount = 0;
	var mydata ='${responseJSON.CLUSTER_LIST}';
 	var json = jQuery.parseJSON(mydata);
 	  $('#officeLocation').append($('<option/>', {value: "", text: "Select"}).attr('data-id','0'));
 	$.each(json, function(i, v) {
	    // create option with value as index - makes it easier to access on change
	    var options = $('<option/>', {value: v.key+"-"+v.val, text: v.key+"-"+v.val}).attr('data-id',i);
	    // append the options to job selectbox
	    $('#officeLocation').append(options);
	});
 	$("#officeLocation > option").each(function() {
		var code = location.split("-")[0];
 		if(this.text == location){
 			 $('#officeLocation').val(this.value);
 		}
 	}); 
 	$('#officeLocation').append(location);

   var groupType='${responseJSON.GROUP_TYPE}';


	/* mydata ='${responseJSON.USER_DESIGNATION}';
	json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
		//if(groupType!="MERCHANTGRP" && v.val=="MA"){}else{
			options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
			$('#adminType').append(options);
		//}

	}); */
 	
 	
 	var userLevel = '${responseJSON.CV0009}';
 	$('#adminType').val(userLevel).prop('selected', true);
    $('#CV0009').val(userLevel);

	// To Iterate the Select box ,and setting the in-putted as selected
	$('#adminType').find('option').each(function( i, opt ) {

		if( opt.text === userLevel ) {
			$(opt).attr('selected', 'selected');
			$('#adminType').trigger("liszt:updated");

		}
	});
 	
	mydata ='${responseJSON.USER_GROUPS}';
	json = jQuery.parseJSON(mydata);
 	$.each(json, function(i, v) {
			options = $('<option/>', {value: v.val, text: v.key}).attr('data-id',i);
			$('#CV0016').append(options);

	});

		mydata ='${responseJSON.USERS_LIST}';
 		json = jQuery.parseJSON(mydata);
 		if(mydata != '' || mydata.indexOf(",") !=-1 ) {
			$.each(json, function(i, v) {
				options = $('<option/>', {value: v, text: v}).attr('data-id',i);
				$('#userId').append(options);
			});
		}

	//$(".chosen-select").chosen({no_results_text: "Oops, nothing found!"});
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

	$('#enableUserId').removeAttr('style');

	
	
	
	
	var userGroup = '${responseJSON.CV0019}';
	//alert(userGroup);
    $('#CV0016').val(userGroup);

	// To Iterate the Select box ,and setting the in-putted as selected
	$('#CV0016').find('option').each(function( i, opt ) {

		if( opt.value == userGroup ) {
			$(opt).attr('selected', 'selected');
			$('#CV0016').trigger("liszt:updated");

		}
	});
	
	/* $('#CV0016').find('option').each(function( i, opt ) {

		if( opt.text === userLevel ) {
			$(opt).attr('selected', 'selected');
			$('#adminType').trigger("liszt:updated");

		}
	}); */

	var userLocation = '${responseJSON.CV0010}';
	$('#officeLocation1').val(userLocation).prop('selected', true);
	 $('#CV0010').val(userLocation);
	 //alert(userLocation);
	// To Iterate the Select box ,and setting the in-putted as selected
	$('#officeLocation').find('option').each(function( i, opt ) {
		//var loc=opt.text.split('-');
		if( opt.value == userLocation ) {
			$(opt).attr('selected', 'selected');
			$('#officeLocation').trigger("liszt:updated");
			$('#CV0010').val=userLocation;
		}
	});

	$("#telephoneRes,#telephoneOff,#mobile,#fax").keypress(function (e) {
		 if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			$("#"+$(this).attr('id')+"_err").html("Digits Only").show().fadeOut("slow");
				   return false;
			}
	   });


	var actionLink = "";


	var userStatus = '${responseJSON.CV0013}';
	var text = "";

	if( userStatus == 'Active')
		text = "<a href='#' class='label label-success'   >"+userStatus+"</a>";
	else if( userStatus == 'De-Active')
		text = "<a href='#'  class='label label-warning'   >"+userStatus+"</a>";
	else if( userStatus == 'InActive')
		text = "<a href='#'  class='label label-info'  >"+userStatus+"</a>";
	else if( userStatus == 'Un-Authorize')
		text = "<a href='#'  class='label label-primary'   >"+userStatus+"</a>";

	$('#spn-user-status').append(text);

	$('#btn-submit').live('click',function() {
	
	$("#form1").validate(validationRules);
	if($("#form1").valid()) {
			$('#CV0014').val($('#officeLocation').val());
			$('#CV0010').val($('#officeLocation option:selected').text());
			$('#CV0009').val($('#adminType option:selected').text());
			$('#CV0012').val('');
			$('#CV0011').val('');
			
			if($('#makerid').val()==$('#userId').val()){
				
				$("#errors").text("Login User and Modify user same , so modify not allowed .");
				return false;	
			}else{
			$("#form1")[0].action="<%=request.getContextPath()%>/<%=appName %>/userModifyConfirm.action";
			$("#form1").submit();
			}
		} else {
			return false;
		}
	});

	function getUserScreen(){
		$("#form1")[0].action='<%=request.getContextPath()%>/<%=appName %>/userGrpCreation.action';
		$("#form1").submit();
		return true;
	}


});

</SCRIPT>


</head>

<body>
	<form name="form1" id="form1" method="post">
	<!-- topbar ends -->


			<div id="content" class="span10">


		    <div>
				<ul class="breadcrumb">
				  <li> <a href="home.action">Home</a> <span class="divider"> &gt;&gt; </span> </li>
				  <li> <a href="userGrpCreation.action">User Management</a> <span class="divider"> &gt;&gt; </span></li>
					  <li><a href="#"> ${type} User </a></li>
				</ul>
			</div>
			<table height="3">
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
	<div class="row-fluid sortable">
		<div class="box span12">

		<div class="box-header well" data-original-title>
			 <i class="icon-edit"></i>User Details
			<div class="box-icon">
				<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>

		<div class="box-content">
			<fieldset>
				<table width="950"  border="0" cellpadding="5" cellspacing="1"
					class="table table-striped table-bordered bootstrap-datatable " >
						<tr class="odd" >
							<td width="25%" ><strong><label for="User Id"> User Id<font color="red">*</font></label></strong></td>
							<td><input name="CV0001" id="userId" value="${responseJSON.CV0001}"  type="text" class="field" required=true  readonly=true maxlength="50" ></td>
							<td width="25%" ><strong><label for="ID/Driving License">Employee No<font color="red">*</font></span></label></strong></td>
							<td><input name="CV0002" type="text" value="${responseJSON.CV0002}" class="field" id="empId"  required=true readonly=true maxlength="50" /></td>
						</tr>
						<tr class="odd">
 							<td><strong><label for="First Name"> First Name<font color="red">*</font></label></strong></td>
 							<td><input name="CV0003" id="firstName" value="${responseJSON.CV0003}"  type="text" class="field" required=true  readonly=true maxlength="50" ></td>
 							<td><strong><label for="Last Name"> Last Name<font color="red">*</font></label></strong></td>
 							<td><input name="CV0004" id="lastName" value="${responseJSON.CV0004}"  type="text" class="field" required=true  readonly=true maxlength="50" ></td>

						</tr>
						<tr class="odd">
 							<td><strong><label for="Telephone Res"> Telephone(Res)</label></strong></td>
							<td><input name="CV0005" type="text" value="${responseJSON.CV0005}" class="field" id="telephoneRes"  maxlength="30" size="30" /></td>
 					        <td><strong><label for="Telephone Off"> Telephone(Off)</label></strong></td>
					        <td><input name="CV0006" type="text" value="${responseJSON.CV0006}" class="field" id="telephoneOff"  maxlength="30" size="30" /></td>
				       </tr>
						<tr class="odd">
							<td><strong><label for="Mobile">Mobile<font color="red">*</font></label></strong></td>
							<td> <select id="code" name="code" onchange="countrycode()" style="width: 90px;"
										data-placeholder = "Choose countrycode..."
										class="chosen-select-width"  >
										<option value="234">234</option>
								</select> &nbsp;  <input name="CV0007" type="text" value="${responseJSON.CV0007}" class="field" id="mobile"  maxlength="30" size="30" required=true style="width:90px;"/></td>
							<td><strong><label for="E-Mail"> E-Mail<font color="red">*</font></label></strong></td>
							<td>
								<input name="CV0012" type="text" value="${responseJSON.CV0012}" class="field" id="email" required=true  maxlength="50" />
							</td>
						 </tr>
						<%-- <tr class="odd">
						  <td><label for="User Designation">User Level<font color="red">*</font></label></td>
					  		<td>
									<select id="adminType" name="adminType" data-placeholder="Choose User Designation..."
											class="chosen-select" style="width: 220px;" required=true  >
								         <option value="">Select</option>
									</select>
						 </td>
						 <td><strong><label for="Office Location"> Office Location<font color="red">*</font></label></strong></td>
						  <td>
							<select id="officeLocation" name="officeLocation" data-placeholder="Choose office location..."
											class="chosen-select-no-results" style="width: 220px; display: none;" required=true  >
							</select>
						   </td>
						</tr> --%>
						<%-- <tr class="odd">
							<td><strong><label for="E-Mail"> E-Mail<font color="red">*</font></label></strong></td>
							<td>
								<input name="CV0012" type="text" value="${responseJSON.CV0012}" class="field" id="email" required=true  maxlength="50" />
							</td>
							<td><strong><label for="Expiry Date"><font color="red"></font></label></strong></td>
							<td>
								<input name="CV0011" type="hidden" value="03/05/2044" class="field" id="expiryDate" required=true  maxlength="20" size="20" />
							</td>
						</tr> --%>
						<tr >
							<td><strong><label for="User Status">User Status</label></strong></td>
							<td>
								<span id="spn-user-status"></span> <input type="hidden" name="CV0013"  id="user_status" value="${responseJSON.CV0013}" />
							</td>
							<td><label for="Store Id">User Group<font color="red">*</font></label></td>
							<td>
								<select id="CV0016" name="CV0016" data-placeholder="Choose User Group ..."
												class="chosen-select-width" style="width: 220px;" required=true >
									<option value="">Select</option>
								</select>
							</td>
						</tr>
						
						<tr class="odd">
						
						 <td><strong><label for="Office Location">Branch<font color="red">*</font></label></strong></td>
						  <td>
							<select id="officeLocation" name="officeLocation" data-placeholder="Choose office location..."
											class="chosen-select-no-results" style="width: 220px; display: none;" required=true  >
							</select>
						   </td>
						     <td></td>
					  	<td></td>
						</tr>
						<%-- <tr class="odd" id="MerchantInfo">
							<td><label for="Merchant Id">Merchant Id<font color="red">*</font></label></td>
							<td>
								${responseJSON.CV0015}
								<input type="hidden" name="CV0015"  id="CV0015" value="${responseJSON.CV0015}" />
								<input type="hidden" name="CV0017"  id="CV0017" value="${responseJSON.CV0017}" /> 
							</td>
							<td><label for="Store Id">Store Id<font color="red">*</font></label></td>
							<td>
								<select id="CV0016" name="CV0016" data-placeholder="Choose office location..."
												class="chosen-select-width" style="width: 220px;" required=true >
									<option value="">Select</option>
								</select>
							</td>
						</tr> --%>
						 
				</table>
				<input type="hidden" name="makerid"  id="makerid"   value="<%=(String)session.getAttribute(CevaCommonConstants.MAKER_ID) %>"  />
				<input name="adminType" type="hidden" value="" class="field" id="adminType" required=true  maxlength="50" />
				
				<input type="hidden" name="type"  id="type" value="Modify" />
				<input type="hidden" name="CV0014"  id="CV0014" />
				<input type="hidden" name="CV0010"  id="CV0010" />
				<input type="hidden" name="CV0009"  id="CV0009" />
				<input type="hidden" name="CV0016"  id="CV0016" />
			</fieldset>

		</div>
		</div>
		</div>
		<div class="form-actions">
		   <input type="button" class="btn btn-success" type="text"  name="btn-submit" id="btn-submit" value="Submit" ></input>
		   &nbsp;<a class="btn " href="userGrpCreation.action" onClick="getUserScreen()">Cancel</a>
		</div>

</div><!--/#content.span10-->

 </form>
</body>
</html>

